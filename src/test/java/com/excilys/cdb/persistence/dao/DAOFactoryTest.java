package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
public class DAOFactoryTest {

    private AnnotationConfigApplicationContext ctx;

    @Autowired
    private DAOFactory factory;

    private Connection connection;

    @BeforeTest
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
        factory = ctx.getBean(DAOFactory.class);
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(factory);
    }

    @Test
    public void notNullComputerDAOTest() {
        assertNotNull(factory.getComputerDAO());
    }

    @Test
    public void validInstanceTest() {
        assertTrue(factory.getComputerDAO() instanceof ComputerDAO);
    }

    @Test
    public void notNullCompanyDAOTest() {
        assertNotNull(factory.getCompanyDAO());
    }

    @Test
    public void validInstanceCompanyDAOTest() {
        assertTrue(factory.getCompanyDAO() instanceof CompanyDAO);
    }

    @Test
    public void getConnectionTest() {
        try {
            connection = DAOFactory.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            fail("Couldn't connect to the DB.");
        }
    }

    @AfterTest
    public void terminate() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        ctx.close();
    }
}
