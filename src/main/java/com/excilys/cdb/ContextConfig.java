package com.excilys.cdb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextConfig {

    private static AnnotationConfigApplicationContext ctx;

    static {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
    }

    public static boolean isLoaded() {
        return ctx != null;
    }
}
