package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerFactory;
import com.excilys.cdb.model.computer.EmptyNameException;

public class ComputerTest {

    private Computer computerWithName;
    private Computer computerWithAll;

    private static final String NAME = "Macbook Air";
    private static final String NEW_NAME = "Macbook Pro";
    private static final Long ID = 21L;
    private static final Long NEW_ID = 10L;
    private static final Long COMPANY_ID = 1L;
    private static final String COMPANY_NAME = "Apple";

    private static final Date NOW = new Date();
    private static Date TOMORROW;

    @Before
    public void setUp() {
        computerWithName = new Computer(NAME);
        Calendar cal = Calendar.getInstance();
        cal.setTime(NOW);
        cal.add(Calendar.DATE, 1);
        TOMORROW = cal.getTime();

        computerWithAll = ComputerFactory.getInstance().createWithAll(ID, NAME, NOW, TOMORROW,
                CompanyFactory.getInstance().create(COMPANY_ID, COMPANY_NAME));
    }

    @Test
    public void getIdTest() {
        assertEquals(computerWithAll.getId(), ID);
    }

    @Test
    public void getIdWhenNoIdTest() {
        assertNull(computerWithName.getId());
    }

    @Test
    public void setId() {
        computerWithName.setId(NEW_ID);
        assertEquals(computerWithName.getId(), NEW_ID);
    }

    @Test
    public void getNameTest() {
        assertEquals(computerWithName.getName(), NAME);
    }

    @Test
    public void setNameTest() {
        computerWithAll.setName(NEW_NAME);
        assertEquals(computerWithAll.getName(), NEW_NAME);
    }

    @Test
    public void setNullNameTest() {
        try {
            computerWithName.setName(null);
            fail("Null name should throw an EmptyNameException.");
        } catch (EmptyNameException ene) {
            assertNotNull(computerWithName.getName());
        }
    }

    @Test
    public void getIntroductionDateWhenNullTest() {
        assertNull(computerWithName.getIntroductionDate());
    }

    @Test
    public void getIntroductionDateTest() {
        assertEquals(computerWithAll.getIntroductionDate(), NOW);
    }

    @Test
    public void getDiscontinuationDateWhenNullTest() {
        assertNull(computerWithName.getDiscontinuationDate());
    }

    @Test
    public void getDiscontinuationDateTest() {
        assertEquals(computerWithAll.getDiscontinuationDate(), TOMORROW);
    }

    @Test
    public void setIntroductionDateTest() {
        computerWithName.setIntroductionDate(TOMORROW);
        assertEquals(computerWithName.getIntroductionDate(), TOMORROW);
        computerWithName.setIntroductionDate(null);
    }

    @Test
    public void setIntroductionDateWithNullTest() {
        computerWithAll.setIntroductionDate(null);
        assertNull(computerWithAll.getIntroductionDate());
    }

    @Test
    public void setDiscontinuationDateWithNullTest() {
        computerWithAll.setIntroductionDate(null);
        assertNull(computerWithAll.getIntroductionDate());
    }

    @Test
    public void setDiscontinuationDateTest() {
        computerWithName.setDiscontinuationDate(NOW);
        assertEquals(computerWithName.getDiscontinuationDate(), NOW);
        computerWithName.setDiscontinuationDate(null);
    }

    @Test
    public void setDiscoDateOlderThanIntroTest() {
        computerWithName.setIntroductionDate(TOMORROW);
        computerWithName.setDiscontinuationDate(NOW);
        assertNull(computerWithName.getDiscontinuationDate());
        computerWithName.setIntroductionDate(null);
        computerWithName.setDiscontinuationDate(null);
    }

    @Test
    public void setIntroDateYoungerThanDiscoTest() {
        computerWithName.setDiscontinuationDate(NOW);
        computerWithName.setIntroductionDate(TOMORROW);
        assertNull(computerWithName.getIntroductionDate());
    }

    @Test
    public void getCompanyTest() {
        assertNotNull(computerWithAll.getCompany());
    }

    @Test
    public void getCompanyIdTest() {
        assertEquals(computerWithAll.getCompany().getId(), COMPANY_ID);
    }

    @Test
    public void getCompanyNameTest() {
        assertEquals(computerWithAll.getCompany().getName(), COMPANY_NAME);
    }

    @Test
    public void setCompanyWithNullTest() {
        computerWithAll.setCompany(null);
        assertNull(computerWithAll.getCompany());
        computerWithAll.setCompany(CompanyFactory.getInstance().create(COMPANY_ID, COMPANY_NAME));
    }
}