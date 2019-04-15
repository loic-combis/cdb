package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerFactory;

public class ComputerTest {

	private Computer computerWithName;
	private Computer computerWithAll;

	private static final String NAME = "Macbook Air";
	private static final String NEW_NAME = "Macbook Pro";
	private static final Long ID = 21L;
	private static final Long NEW_ID = 10L;
	private static final Long COMPANY_ID = 1L;
	private static final String COMPANY_NAME = "Apple";

	private static final LocalDate NOW = LocalDate.now();
	private static LocalDate TOMORROW;

	@BeforeTest
	public void setUp() {
		computerWithName = new Computer(NAME);
		TOMORROW = NOW.plusDays(1);

		try {
			computerWithAll = ComputerFactory.getInstance().createWithAll(ID, NAME, NOW, TOMORROW,
					CompanyFactory.getInstance().create(COMPANY_ID, COMPANY_NAME));
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
		try {
			computerWithName.setIntroductionDate(TOMORROW);
			assertEquals(computerWithName.getIntroductionDate(), TOMORROW);
			computerWithName.setIntroductionDate(null);

		} catch (UnconsistentDatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void setIntroductionDateWithNullTest() {
		try {
			computerWithAll.setIntroductionDate(null);
			assertNull(computerWithAll.getIntroductionDate());

		} catch (UnconsistentDatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void setDiscontinuationDateWithNullTest() {
		try {
			computerWithAll.setIntroductionDate(null);
			assertNull(computerWithAll.getIntroductionDate());

		} catch (UnconsistentDatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void setDiscontinuationDateTest() {
		try {
			computerWithName.setDiscontinuationDate(NOW);
			assertEquals(computerWithName.getDiscontinuationDate(), NOW);
			computerWithName.setDiscontinuationDate(null);
		} catch (UnconsistentDatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
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