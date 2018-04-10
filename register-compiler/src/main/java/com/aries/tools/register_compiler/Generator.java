package com.aries.tools.register_compiler;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * Created by wudaming on 2018/4/8.
 */

class Generator {

    static TypeSpec.Builder generateOutClass(String className) {
        return TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL);

    }

    static TypeSpec assembleInnerClass(Set<RegisteredClass> classes) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("activity")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.FINAL);
        addFields(classes, classBuilder);
        return classBuilder.build();
    }

    private static void addFields(Set<RegisteredClass> classes, TypeSpec.Builder classBuilder) {
        for (RegisteredClass r : classes) {
            FieldSpec fieldSpec = r.generateField();
            classBuilder.addField(fieldSpec);
        }
    }

    static void makeFile(String packageName, TypeSpec typeSpec, Filer filer) {
        makeFile(packageName, typeSpec, filer, null);
    }

    static void makeFile(String packageName, TypeSpec typeSpec, Filer filer, String comment) {
        try {
            JavaFile.Builder builder = JavaFile.builder(packageName, typeSpec);
            if (comment != null && comment.length() > 0) {
                builder.addFileComment(comment);
            }
            builder.build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
