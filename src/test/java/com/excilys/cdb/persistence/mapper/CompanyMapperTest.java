package com.excilys.cdb.persistence.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.mapper.CompanySQLMapper;

@RunWith(SpringRunner.class)
public class CompanyMapperTest {

    @Autowired
    private CompanySQLMapper mapper;

    private AnnotationConfigApplicationContext ctx;

    @BeforeTest
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
        mapper = ctx.getBean(CompanySQLMapper.class);
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

    @AfterTest
    public void end() {
        ctx.close();
    }
}
