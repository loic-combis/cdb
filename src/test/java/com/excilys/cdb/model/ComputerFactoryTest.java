package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ComputerFactoryTest {

    private ComputerFactory computerFactory;
    private static final String NAME = "Macbook Pro";
    private static final long ID = 21L;
    private static final long COMPANY_ID = 1L;
    private static final String COMPANY_NAME = "Apple";

    private static final Date NOW = new Date();
    private static Date TOMORROW;

    @Before
    public void setUp() {
        computerFactory = ComputerFactory.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(NOW);
        cal.add(Calendar.DATE, 1);
        TOMORROW = cal.getTime();
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(computerFactory);
    }

    @Test
    public void sharedInstanceTest() {
        assertEquals(computerFactory, ComputerFactory.getInstance());
    }

    @Test
    public void createWithNullNameTest() {
        Computer computer = computerFactory.createWithName(null);
        assertNull(computer);
    }

    @Test
    public void createWithEmptyNameTest() {
        Computer computer = computerFactory.createWithName("");
        assertNull(computer);
    }

    @Test
    public void createWithValidNameTest() {
        Computer computer = computerFactory.createWithName(NAME);
        assertNotNull(computer);
    }

    @Test
    public void createWithAllToNullTest() {
        Computer computer = computerFactory.createWithAll(ID, null, null, null, null);
        assertNull(computer);
    }

    @Test
    public void createWithAllToNullExceptNameTest() {
        Computer computer = computerFactory.createWithAll(ID, NAME, null, null, null);
        assertNotNull(computer);
    }

    @Test
    public void createWithAllTest() {
        Computer computer = computerFactory.createWithAll(ID, NAME, NOW, TOMORROW,
                new Company(COMPANY_ID, COMPANY_NAME));
        assertNotNull(computer);
    }
}