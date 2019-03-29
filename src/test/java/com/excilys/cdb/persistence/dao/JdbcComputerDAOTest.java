package com.excilys.cdb.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.computer.Computer;

public class JdbcComputerDAOTest {

    private ComputerDAO dao;
    private static final String NAME = "Macbook Air";
    private static final String NEW_NAME = "Macbook Pro";

    // TODO : Use In-Memory Database.
    @BeforeTest
    public void setUp() {
        dao = ComputerDAO.getInstance();
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(dao);
    }

    @Test
    public void sharedInstanceTest() {
        assertEquals(dao, ComputerDAO.getInstance());
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
        } catch (UnconsistentDatesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
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
        assertFalse(dao.delete(-1L));
    }

    @Test
    public void updateTest() {
        Optional<Computer> computer = dao.create(new Computer(NAME));
        assertTrue(computer.isPresent());
        Computer c = computer.get();
        c.setName(NEW_NAME);
        assertTrue(dao.update(c));
        try {
            assertEquals(dao.get(c.getId()).get().getName(), NEW_NAME);
        } catch (EmptyNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnconsistentDatesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(dao.delete(c.getId()));

    }
}
