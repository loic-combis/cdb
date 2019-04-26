
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.core.company.CompanyFactory;
import com.excilys.cdb.core.computer.Computer;
import com.excilys.cdb.core.computer.ComputerFactory;

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
    public void getIntroductionDateWhenNullTest() {
        assertNull(computerWithName.getIntroduced());
    }

    @Test
    public void getIntroductionDateTest() {
        assertEquals(computerWithAll.getIntroduced(), NOW);
    }

    @Test
    public void getDiscontinuationDateWhenNullTest() {
        assertNull(computerWithName.getDiscontinued());
    }

    @Test
    public void getDiscontinuationDateTest() {
        assertEquals(computerWithAll.getDiscontinued(), TOMORROW);
    }

    @Test
    public void setIntroductionDateTest() {
        computerWithName.setIntroduced(TOMORROW);
        assertEquals(computerWithName.getIntroduced(), TOMORROW);
        computerWithName.setIntroduced(null);
    }

    @Test
    public void setIntroductionDateWithNullTest() {
        computerWithAll.setIntroduced(null);
        assertNull(computerWithAll.getIntroduced());
    }

    @Test
    public void setDiscontinuationDateWithNullTest() {
        computerWithAll.setIntroduced(null);
        assertNull(computerWithAll.getIntroduced());
    }

    @Test
    public void setDiscontinuationDateTest() {
        computerWithName.setDiscontinued(NOW);
        assertEquals(computerWithName.getDiscontinued(), NOW);
        computerWithName.setDiscontinued(null);
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