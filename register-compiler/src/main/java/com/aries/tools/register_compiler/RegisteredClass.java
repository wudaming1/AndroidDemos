package com.aries.tools.register_compiler;

import com.aries.tools.register_annotation.RegisterPage;
import com.squareup.javapoet.FieldSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by wudaming on 2018/4/8.
 */

class RegisteredClass {

    private String name;

    private String className;

    private PageType pageType;



    RegisteredClass(PageType pageType, TypeElement element) {

        this.className = element.getQualifiedName().toString();
        this.pageType = pageType;
        RegisterPage page = element.getAnnotation(RegisterPage.class);
        name = page.name();
        if (name.length() <= 0) {
            throw new IllegalArgumentException("Name of the RegisterPage is null!");
        }

    }


    FieldSpec generateField() {
        return FieldSpec.builder(String.class, name)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.FINAL)
                .initializer("$S", className)
                .build();
    }

    PageType getPageType() {
        return pageType;
    }

    String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof RegisteredClass) {
            RegisteredClass other = (RegisteredClass) o;
            return other.getName().equals(name);
        } else {
            return false;
        }
    }
}
