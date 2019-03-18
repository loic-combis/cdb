package com.excilys.cdb.persistence.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class ComputerMapperTest {

	
	private ComputerMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new ComputerMapper();
	}
	
	@Test
	public void resultToObjectTest() {
		try {
			assertNull(mapper.queryResultToObject(null));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Not handling null parameter.");
		}
	} 
}
