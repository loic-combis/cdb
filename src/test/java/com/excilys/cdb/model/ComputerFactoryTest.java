package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerFactory;

public class ComputerFactoryTest {

    private ComputerFactory computerFactory;
    private static final String NAME = "Macbook Pro";
    private static final Long ID = 21L;
    private static final Long COMPANY_ID = 1L;
    private static final String COMPANY_NAME = "Apple";

    private static final LocalDate NOW = LocalDate.now();
    private static LocalDate TOMORROW;

    @Before
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

    @Test(expected = EmptyNameException.class)
    public void createWithNullNameTest() {
        computerFactory.createWithName(null);
    }

    @Test(expected = EmptyNameException.class)
    public void createWithEmptyNameTest() {
        computerFactory.createWithName("");
    }

    @Test
    public void createWithValidNameTest() {
        Computer computer = computerFactory.createWithName(NAME);
        assertNotNull(computer);
    }

    @Test(expected = EmptyNameException.class)
    public void createWithAllToNullTest() {
        try {
            computerFactory.createWithAll(ID, null, null, null, null);
        } catch (UnconsistentDatesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void createWithAllToNullExceptNameTest() {
        Computer computer;
        try {
            computer = computerFactory.createWithAll(ID, NAME, null, null, null);
            assertNotNull(computer);

        } catch (EmptyNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnconsistentDatesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void createWithAllTest() {
        Computer computer;
        try {
            computer = computerFactory.createWithAll(ID, NAME, NOW, TOMORROW,
                    CompanyFactory.getInstance().create(COMPANY_ID, COMPANY_NAME));
            assertNotNull(computer);
        } catch (EmptyNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnconsistentDatesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
