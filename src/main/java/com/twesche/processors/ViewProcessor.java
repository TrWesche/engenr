package com.twesche.processors;

import com.twesche.annotation.EGRColumn;
import com.twesche.annotation.EGRMethod;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.*;

@SupportedAnnotationTypes("com.twesche.annotation.EGREntity")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ViewProcessor extends AbstractProcessor {
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
                if (variableElement.getAnnotationsByType(EGRColumn.class).length != 0) {
                    variableElements.add(variableElement);
                    System.out.println("\tVariable " + variableElement.getSimpleName() + " is annotated with " + EGRColumn.class.getName());
                }
                continue;
            }

            if (element instanceof final ExecutableElement executableElement) {
                if (executableElement.getAnnotationsByType(EGRMethod.class).length != 0) {
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
}
