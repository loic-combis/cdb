package com.excilys.cdb.persistence.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.mapper.CompanySQLMapper;

public class CompanyMapperTest {

    private CompanySQLMapper mapper;

    @BeforeTest
    public void setUp() {
        mapper = new CompanySQLMapper();
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
