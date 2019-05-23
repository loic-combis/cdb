
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.core.company.CompanyFactory;

@RunWith(SpringRunner.class)
public class CompanyFactoryTest {

    private CompanyFactory companyFactory;
    private static final Long ID = 21L;
    private static final String NAME = "Apple";

    @BeforeTest
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
