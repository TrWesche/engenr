package com.twesche.processors;

import com.twesche.annotation.EGRField;
import com.twesche.annotation.EGRMethod;
import com.twesche.enums.EGRFieldAccess;
import com.twesche.model.EGRFieldData;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.*;

@SupportedAnnotationTypes("com.twesche.annotation.EGREntity")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class EGRProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Only will get called once - This process only supports annotation com.twesche.annotation.EGREntity
        for (TypeElement annotation : annotations) // Loop of a single TypeElement
        {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("EGREntityProcessor Running for Annotation %s", annotation.getQualifiedName()));

            // Getting all elements with the annotation EGREntity
            Set<TypeElement> typeElements = getAnnotatedTypes(roundEnv.getElementsAnnotatedWith(annotation));
            if (typeElements.isEmpty()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "EGREntityProcessor Finished - No Annotated Types Found");
                return true;
            }

            Map<TypeElement, EGRTypeProperties> typeElementSetMap = getAnnotatedElements(typeElements);
        }

        return true;
    }

    private Set<TypeElement> getAnnotatedTypes(Set<? extends Element> annotatedElements)
    {
        Set<TypeElement> typeElements = new HashSet<>();
        for (var element : annotatedElements) {
            if (element instanceof final TypeElement typeElement) {
                typeElements.add(typeElement);
            }
        }
        return typeElements;
    }

    private Map<TypeElement, EGRTypeProperties> getAnnotatedElements(Set<TypeElement> typeElements)
    {
        Map<TypeElement, EGRTypeProperties> typeElementSetMap = new HashMap<>();

        for (TypeElement typeElement : typeElements) {
            EGRTypeProperties egrTypeProperties = categorizeByAnnotation(typeElement.getEnclosedElements());
            typeElementSetMap.put(typeElement, egrTypeProperties);
        }

        return typeElementSetMap;
    }

    private EGRTypeProperties categorizeByAnnotation(List<? extends Element> elements) {
        Set<VariableElement> variableElements = new HashSet<>();
        Set<ExecutableElement> executableElements = new HashSet<>();

        for (final Element element : elements)
        {
            if (element instanceof final VariableElement variableElement) {
                if (variableElement.getAnnotationsByType(EGRField.class).length != 0) {
                    if (variableElement.getKind() != ElementKind.FIELD) {
                        System.out.println("Unsupported Element Type for EGRColumn");
                        continue;
                    }
                    variableElements.add(variableElement);
                    System.out.println("\tVariable " + variableElement.getSimpleName() + " is annotated with " + EGRField.class.getName());
                }
                continue;
            }

            if (element instanceof final ExecutableElement executableElement) {
                if (executableElement.getAnnotationsByType(EGRMethod.class).length != 0) {
                    if (executableElement.getKind() != ElementKind.METHOD) {
                        System.out.println("Unsupported Element Type for EGRMethod");
                        continue;
                    }

                    executableElements.add(executableElement);
                    System.out.println("\tMethod " + executableElement.getSimpleName() + " is annotated with " + EGRMethod.class.getName());
                }
            }
        }
        return new EGRTypeProperties(
                variableElements,
                executableElements
        );
    }

    private static class EGRTypeProperties {
        Set<VariableElement> variableElements;
        Set<ExecutableElement> executableElements;

        public EGRTypeProperties(Set<VariableElement> variableElements, Set<ExecutableElement> executableElements) {
            this.variableElements = variableElements;
            this.executableElements = executableElements;
        }
    }

    private EGRFieldData generateFieldData(VariableElement variable, TypeElement type) {
        // Check if field is annotated and is the proper type
        EGRField annotation = variable.getAnnotation(EGRField.class);
        if (annotation == null || variable.getKind() != ElementKind.FIELD || annotation.access() == EGRFieldAccess.NONE) {
            return null;
        }

        // Begin Generation
        EGRFieldData egrFieldData = new EGRFieldData();
        egrFieldData.setElement(variable);

        // Field Access Level
        Set<Modifier> modifiers = variable.getModifiers();
        if (
            (modifiers.contains(Modifier.FINAL) || modifiers.contains(Modifier.STATIC)) &&
            annotation.access() != EGRFieldAccess.READ_ONLY
        ) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.WARNING,
                    String.format("Field '%s' of Class '%s' has been marked for EGR generation but is a static and/or final field.  Access for this field will be set to read only.",
                            variable.getSimpleName(), type.getSimpleName()
                    )
            );
            egrFieldData.setAccessLevel(EGRFieldAccess.READ_ONLY);
        }
        else {
            egrFieldData.setAccessLevel(annotation.access());
        }

        // Field Name & Metadata Setup
        if (annotation.fieldName().isEmpty()) {
            egrFieldData.setFieldName(variable.getSimpleName().toString());
        } else {
            egrFieldData.setFieldName(annotation.fieldName());
        }

        egrFieldData.setFieldDescription(annotation.fieldDescription());

        // Java View Configuration(s)
        egrFieldData.setViewAddToModel(annotation.viewAddToModel());

        // UI Model & Display Configuration(s)
        egrFieldData.setUiAddToModel(annotation.uiAddToModel());
        egrFieldData.setUiHide(annotation.uiHide());

        return egrFieldData;
    }
}
