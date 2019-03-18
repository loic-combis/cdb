package com.excilys.cdb.persistence.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class CompanyMapperTest {

	private CompanyMapper mapper;

	@Before
	public void setUp() {
		mapper = new CompanyMapper();
	}

	@Test
	public void mapFromNullTest() {
		try {
			assertNull(mapper.queryResultToObject(null));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("Not handling null result.");
			e.printStackTrace();
		}
	}
}
