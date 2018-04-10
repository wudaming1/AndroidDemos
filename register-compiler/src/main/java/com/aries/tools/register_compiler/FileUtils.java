package com.aries.tools.register_compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

/**
 * Created by wudaming on 2018/4/8.
 */

public class FileUtils {

    static void init(){
        File file = new File("/Users/wudaming/registerLog.txt");
        if (file.exists()){
            file.delete();
        }
        print("start!!");
    }

    static void print(String text) {
        File file = new File("/Users/wudaming/registerLog.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            fileWriter.write(text + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void print(Elements elements, Element element) {
        File file = new File("/Users/wudaming/registerLog.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            elements.printElements(fileWriter,element);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
