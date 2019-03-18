package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyFactory;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerFactory;

import java.util.LinkedList;

public class PersistenceFacadeTest {

	private PersistenceFacade persistence;
	
	private LinkedList<Computer> computers;
	private static final String COMPUTER_NAME = "Mackbook Pro";
	private static final long COMPUTER_ID = 21L;
	private LinkedList<Company> companies;
	private static final long COMPANY_ID = 1L;
	
	@Before
	public void setUp() {
		persistence = PersistenceFacade.getInstance();
		computers = new LinkedList<Computer>();
		computers.add(ComputerFactory.getInstance().createWithAll(COMPUTER_ID, COMPUTER_NAME, null, null, null));
		companies = new LinkedList<Company>();
		companies.add(CompanyFactory.getInstance().create(COMPANY_ID));
	}
	
	@Test
	public void notNullInstanceTest() {
		assertNotNull(persistence);
	}
	
	@Test
	public void sharedInstanceTest() {
		assertEquals(persistence, PersistenceFacade.getInstance());
	}
	
}
