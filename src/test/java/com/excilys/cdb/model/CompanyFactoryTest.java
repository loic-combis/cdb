package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.company.CompanyFactory;

public class CompanyFactoryTest {

    private CompanyFactory companyFactory;
    private static final Long ID = 21L;
    private static final String NAME = "Apple";

    @Before
    public void setUp() {
        companyFactory = CompanyFactory.getInstance();
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(companyFactory);
    }

    @Test
    public void sharedInstanceTest() {
        assertEquals(companyFactory, CompanyFactory.getInstance());
    }

    @Test
    public void createNotNullInstancesTest() {
        assertNotNull(companyFactory.create(ID));
    }

    @Test
    public void createNotNullWithNameTest() {
        assertNotNull(companyFactory.create(ID, NAME));
    }
}
