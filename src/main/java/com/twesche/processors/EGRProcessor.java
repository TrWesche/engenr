package com.twesche.processors;

import com.twesche.annotation.EGRField;
import com.twesche.annotation.EGRType;
import com.twesche.enums.EGRFieldAccess;
import com.twesche.enums.EGRGenerate;
import com.twesche.enums.EGRTypeAccess;
import com.twesche.generators.java.EGRViewGenerator;
import com.twesche.model.EGRFieldData;
import com.twesche.model.EGRGenerateStatus;
import com.twesche.model.EGRTypeData;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@SupportedAnnotationTypes("com.twesche.annotation.EGRType")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class EGRProcessor extends AbstractProcessor {
    EGRViewGenerator egrViewGenerator = new EGRViewGenerator();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Only will get called once - This process only supports annotation com.twesche.annotation.EGREntity
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "EGRProcessor Started");
        for (TypeElement annotation : annotations) // Loop of a single TypeElement
        {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("EGREntityProcessor Running for Annotation %s", annotation.getQualifiedName()));

            // Getting all elements with the annotation EGREntity
            Set<TypeElement> typeElements = getAnnotatedTypes(roundEnv.getElementsAnnotatedWith(annotation));
            if (typeElements.isEmpty()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "EGREntityProcessor Finished - No Annotated Types Found");
                return true;
            }

            Set<EGRTypeData> egrTypeDataSet = new HashSet<>();
            for (TypeElement typeElement : typeElements) {
                EGRTypeData egrTypeData = generateTypeData(typeElement);
                if (egrTypeData != null) {
                    egrTypeDataSet.add(egrTypeData);
                }
            }

            for (EGRTypeData instance : egrTypeDataSet) {
                System.out.println(instance);
                EGRGenerateStatus status = egrViewGenerator.generate(instance);
                System.out.println(status);
            }
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

    private EGRFieldData generateFieldData(VariableElement variable, TypeElement type) {
        // Check if field is annotated and is the proper type
        EGRField annotation = variable.getAnnotation(EGRField.class);
        if (annotation == null || variable.getKind() != ElementKind.FIELD) {
            return null;
        }

        // Begin Generation
        EGRFieldData egrFieldData = new EGRFieldData();
        egrFieldData.setElement(variable);

        // Field Access Level
        Set<Modifier> modifiers = variable.getModifiers();
        EnumSet<EGRFieldAccess> requestedAccess = EnumSet.copyOf(Arrays.asList(annotation.access()));
        if (
            (modifiers.contains(Modifier.FINAL) || modifiers.contains(Modifier.STATIC)) &&
            requestedAccess.contains(EGRFieldAccess.WRITE)
        ) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.WARNING,
                    String.format("Field '%s' of Class '%s' has been marked for EGR write access generation but is a static and/or final field.  Access for this field will be set to read only.",
                            variable.getSimpleName(), type.getSimpleName()
                    )
            );
            egrFieldData.setAccessLevel(EnumSet.of(EGRFieldAccess.READ));
        }
        else {
            egrFieldData.setAccessLevel(requestedAccess);
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

    private EGRTypeData generateTypeData(TypeElement type)
    {
        // ==== Check if field is annotated and is the proper type
        EGRType annotation = type.getAnnotation(EGRType.class);
        if (annotation == null || type.getKind() != ElementKind.CLASS) {
            return null;
        }

        // ==== Begin Generation
        EGRTypeData egrTypeData = new EGRTypeData();
        egrTypeData.setType(type);

        // ==== Get Field Information
        Set<EGRFieldData> fieldDataSet = new HashSet<>();

        for (final Element element : type.getEnclosedElements()) {
            if (element instanceof final VariableElement variableElement) {
                EGRFieldData fieldData = generateFieldData(variableElement, type);
                if (fieldData != null) {
                    fieldDataSet.add(fieldData);
                }
            }
        }

        // ---- If there were no fields marked for generation skip the class
        if (fieldDataSet.isEmpty()) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.WARNING,
                    String.format("Class '%s' has been marked for EGR Generation but no fields were marked for generation, class will be skipped during generation.",
                            type.getSimpleName()
                    )
            );
            return null;
        }

        egrTypeData.setFields(fieldDataSet);

        // ==== Set Access Level
        EnumSet<EGRTypeAccess> requestedAccess = EnumSet.copyOf(Arrays.asList(annotation.access()));
        egrTypeData.setAccess(requestedAccess);

        // ==== Set Generation Configuration
        EnumSet<EGRGenerate> generationConfig = EnumSet.copyOf(Arrays.asList(annotation.generate()));
        egrTypeData.setGenerate(generationConfig);

        return egrTypeData;
    }
}
