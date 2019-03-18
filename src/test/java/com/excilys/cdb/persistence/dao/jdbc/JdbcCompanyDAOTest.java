package com.excilys.cdb.persistence.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;

import java.util.List;

public class JdbcCompanyDAOTest {

	private JdbcCompanyDAO jdbcCompanyDAO;

	@Before
	public void setUp() {
		jdbcCompanyDAO = JdbcCompanyDAO.getInstance();
	}

	@Test
	public void notNullInstanceTest() {
		assertNotNull(jdbcCompanyDAO);
	}

	@Test
	public void sharedInstanceTest() {
		assertEquals(jdbcCompanyDAO, JdbcCompanyDAO.getInstance());
	}

	@Test
	public void listCompaniesTest() {
		List<Company> companies = jdbcCompanyDAO.list(0, 10);
		assertTrue(companies.size() <= 10);
	}

	@Test
	public void getCompanyTest() {
		Company company = jdbcCompanyDAO.get(10);
		assertTrue(company == null || company.getId() == 10);
	}
}
