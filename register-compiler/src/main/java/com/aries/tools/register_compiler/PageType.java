package com.aries.tools.register_compiler;

/**
 * Created by wudaming on 2018/4/8.
 */

public enum PageType {
    ACTIVITY("activity"),FRAGMENT("fragment");
    String name;

    PageType(String name) {
        this.name = name;
    }
}
