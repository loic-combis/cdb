package com.excilys.cdb.console;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.console.cli.CLIController;
import com.excilys.cdb.console.config.Config;

public class Launch {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        CLIController cli = ctx.getBean(CLIController.class);
        cli.start();
        ctx.close();
    }
}