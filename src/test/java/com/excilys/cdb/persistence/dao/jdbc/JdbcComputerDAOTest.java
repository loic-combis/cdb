package com.excilys.cdb.persistence.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Computer;

public class JdbcComputerDAOTest {

	private JdbcComputerDAO dao;
	private static final String NAME = "Macbook Air";
	private static final String NEW_NAME = "Macbook Pro";
		
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
		Computer savedComputer = dao.create(computer);
		assertNotNull(savedComputer);
		assertTrue(dao.delete(savedComputer.getId()));
	}
	
	@Test
	public void createWithNullTest() {
		assertNull(dao.create(null));
	}
	
	@Test
	public void deleteWithNonExistingIdTest() {
		assertFalse(dao.delete(-1));
	}
	
	@Test
	public void updateTest() {
		Computer computer = dao.create(new Computer(NAME));
		assertNotNull(computer);
		computer.setName(NEW_NAME);
		assertTrue(dao.update(computer));
		assertEquals(dao.get(computer.getId()).getName(), NEW_NAME);
		assertTrue(dao.delete(computer.getId()));
		
	}
}
