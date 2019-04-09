package com.excilys.cdb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.ui.cli.CLIController;

/**
 * Entry point meant to instantiate and start the application.
 *
 */
public class App {

    /**
     * Main method.
     *
     * @param args String[]
     */
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();

        CLIController cli = ctx.getBean(CLIController.class);
        cli.start();
        ctx.close();
    }
}
