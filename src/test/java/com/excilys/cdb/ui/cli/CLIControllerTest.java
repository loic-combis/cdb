package com.excilys.cdb.ui.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.excilys.cdb.ui.PageProvider;
import com.excilys.cdb.ui.UIController;

@RunWith(SpringRunner.class)
public class CLIControllerTest {

    @Autowired
    private CLIController cli;

    private AnnotationConfigApplicationContext ctx;

    @BeforeTest
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
        cli = ctx.getBean(CLIController.class);
    }

    @Test
    public void instanceOfController() {
        assertTrue(cli instanceof UIController);
    }

    @Test
    public void instanceOfPageProvider() {
        assertTrue(cli instanceof PageProvider);
    }

    @AfterTest
    public void end() {
        ctx.close();
    }
}