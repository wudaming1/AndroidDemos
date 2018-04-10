package com.aries.tools.register_compiler;

import com.aries.tools.register_annotation.RegisterPage;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by wudaming on 2018/4/4.
 */

@AutoService(Processor.class)
public class RegisterProcessor extends AbstractProcessor {

    private Elements elementUtil;
    private Messager messager;
    private Filer filer;

    private final String PACKAGE_NAME = "com.aries.android.demo";
    private final String OUT_CLASS_NAME = "Page";
    private TypeSpec.Builder out;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        FileUtils.init();
        elementUtil = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        out = Generator.generateOutClass(OUT_CLASS_NAME);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(RegisterPage.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        FileUtils.print("process!!");
        Map<PageType, Set<RegisteredClass>> targetMap = new HashMap<>();
        sortElement(targetMap, roundEnvironment);
        for (Map.Entry<PageType, Set<RegisteredClass>> entry : targetMap.entrySet()) {
            out.addType(Generator.assembleInnerClass(entry.getValue()));
        }
        generateClass();
        return true;
    }

    private void sortElement(Map<PageType, Set<RegisteredClass>> targetMap, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(RegisterPage.class)) {
            TypeElement parent = (TypeElement) element;

            RegisteredClass registeredClass = new RegisteredClass(
                    PageType.ACTIVITY, parent);

            Set<RegisteredClass> relevantSet = getRelevantSet(targetMap, registeredClass);
            if (!relevantSet.add(registeredClass)) {
                error(element, "Duplicated Annotation value @%s", registeredClass.getName());
            }
        }

    }

    private Set<RegisteredClass> getRelevantSet(Map<PageType, Set<RegisteredClass>> targetMap, RegisteredClass registeredClass) {
        Set<RegisteredClass> registeredClassSet = targetMap.get(registeredClass.getPageType());
        if (registeredClassSet == null) {
            registeredClassSet = new HashSet<>();
            targetMap.put(registeredClass.getPageType(), registeredClassSet);
        }
        return registeredClassSet;
    }

    private void generateClass() {

        Generator.makeFile(PACKAGE_NAME, out.build(), filer, "Register Page Annotation auto generated!");
    }


    private void error(Element element, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), element);
    }

}
