package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DAOFactoryTest {

    private DAOFactory factory;

    private Connection connection;

    @BeforeTest
    public void setUp() {
        factory = DAOFactory.getInstance();
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(factory);
    }

    @Test
    public void sharedInstanceTest() {
        assertEquals(factory, DAOFactory.getInstance());
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
        } catch (ClassNotFoundException cnfe) {
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
    }
}
