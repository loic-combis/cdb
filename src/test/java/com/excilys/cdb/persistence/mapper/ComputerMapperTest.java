package com.excilys.cdb.persistence.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.mapper.ComputerSQLMapper;

public class ComputerMapperTest {

    private ComputerSQLMapper mapper;

    @Before
    public void setUp() {
        mapper = new ComputerSQLMapper();
    }

    @Test
    public void resultToObjectTest() {
        try {

            System.out.println(mapper.queryResultToObject(null));
            assertNull(mapper.queryResultToObject(null));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Not handling null parameter.");
        }
    }
}
