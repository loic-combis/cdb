package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DAOFactoryTest {

    private DAOFactory factory;

    private Connection connection;

    @Before
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

    @After
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
