package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.model.computer.Computer;

@RunWith(SpringRunner.class)
public class JdbcComputerDAOTest {

    private ComputerDAO dao;

    private AnnotationConfigApplicationContext ctx;

    @BeforeTest
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
        dao = ctx.getBean(ComputerDAO.class);
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(dao);
    }

    @Test
    public void listComputersTest() {
        List<Computer> computers;
        try {
            computers = dao.list(0, 10, null, null);
            assertTrue(computers.size() <= 10);

        } catch (EmptyNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteWithNonExistingIdTest() {
        assertFalse(dao.delete(-1L));
    }

    @AfterTest
    public void end() {
        ctx.close();
    }
}
