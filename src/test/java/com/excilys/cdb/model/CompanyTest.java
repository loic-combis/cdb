package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.company.CompanyFactory;

public class CompanyTest {

    private Company company;
    private Company companyWithoutId;
    private Company companyWithoutName;

    private static final String NAME = "Apple";
    private static final String NEW_NAME = "Microsoft";
    private static final Long ID = 21L;

    @BeforeTest
    public void setUp() {
        company = CompanyFactory.getInstance().create(ID, NAME);
        companyWithoutId = CompanyFactory.getInstance().create(NAME);
        companyWithoutName = CompanyFactory.getInstance().create(ID);
    }

    @Test
    public void getIdTest() {
        assertEquals(company.getId(), ID);
    }

    @Test
    public void getIdWhenNoIdTest() {
        assertNull(companyWithoutId.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals(company.getName(), NAME);
    }

    @Test
    public void getNameWhenNoName() {
        assertNull(companyWithoutName.getName());
    }

    @Test
    public void setNameTest() {
        String newName = NEW_NAME;
        company.setName(newName);
        assertEquals(company.getName(), NEW_NAME);
    }

    @Test
    public void setNullNameTest() {
        company.setName(null);
        assertNull(company.getName());
    }
}
