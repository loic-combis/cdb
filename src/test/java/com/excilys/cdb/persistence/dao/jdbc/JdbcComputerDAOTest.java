package com.excilys.cdb.persistence.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Computer;

public class JdbcComputerDAOTest {

    private JdbcComputerDAO dao;
    private static final String NAME = "Macbook Air";
    private static final String NEW_NAME = "Macbook Pro";

    // TODO : Use In-Memory Database.
    @Before
    public void setUp() {
        dao = JdbcComputerDAO.getInstance();
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(dao);
    }

    @Test
    public void sharedInstanceTest() {
        assertEquals(dao, JdbcComputerDAO.getInstance());
    }

    @Test
    public void listComputersTest() {
        List<Computer> computers = dao.list(0, 10);
        assertTrue(computers.size() <= 10);
    }

    @Test
    public void createComputerTest() {
        Computer computer = new Computer(NAME);
        Optional<Computer> savedComputer = dao.create(computer);
        assertNotNull(savedComputer);
        assertTrue(dao.delete(savedComputer.get().getId()));
    }

    @Test
    public void createWithNullTest() {
        assertFalse(dao.create(null).isPresent());
    }

    @Test
    public void deleteWithNonExistingIdTest() {
        assertFalse(dao.delete(-1));
    }

    @Test
    public void updateTest() {
        Optional<Computer> computer = dao.create(new Computer(NAME));
        assertTrue(computer.isPresent());
        Computer c = computer.get();
        c.setName(NEW_NAME);
        assertTrue(dao.update(c));
        assertEquals(dao.get(c.getId()).get().getName(), NEW_NAME);
        assertTrue(dao.delete(c.getId()));

    }
}
