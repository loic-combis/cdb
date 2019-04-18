package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerFactory;

@RunWith(SpringRunner.class)
public class ComputerFactoryTest {

    private ComputerFactory computerFactory;
    private static final String NAME = "Macbook Pro";
    private static final Long ID = 21L;
    private static final Long COMPANY_ID = 1L;
    private static final String COMPANY_NAME = "Apple";

    private static final LocalDate NOW = LocalDate.now();
    private static LocalDate TOMORROW;

    @BeforeTest
    public void setUp() {
        computerFactory = ComputerFactory.getInstance();
        TOMORROW = NOW.plusDays(1);
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
    public void createWithValidNameTest() {
        Computer computer = computerFactory.createWithName(NAME);
        assertNotNull(computer);
    }

    @Test
    public void createWithAllToNullExceptNameTest() {
        Computer computer;
        computer = computerFactory.createWithAll(ID, NAME, null, null, null);
        assertNotNull(computer);
    }

    @Test
    public void createWithAllTest() {
        Computer computer;
        computer = computerFactory.createWithAll(ID, NAME, NOW, TOMORROW,
                CompanyFactory.getInstance().create(COMPANY_ID, COMPANY_NAME));
        assertNotNull(computer);
    }
}
