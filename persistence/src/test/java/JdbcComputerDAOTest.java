
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.excilys.cdb.core.computer.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;

@RunWith(SpringRunner.class)
public class JdbcComputerDAOTest {

    private ComputerDAO dao;

    private AnnotationConfigApplicationContext ctx;

    @BeforeTest
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
        dao = ctx.getBean(ComputerDAO.class);
    }

    @Test
    public void notNullInstanceTest() {
        assertNotNull(dao);
    }

    @Test
    public void listComputersTest() {
        List<Computer> computers;
        computers = dao.list(0, 10, null, null);
        assertTrue(computers.size() <= 10);
    }

    @Test
    public void deleteWithNonExistingIdTest() {
        assertFalse(dao.delete(-1L));
    }

    @AfterTest
    public void end() {
        ctx.close();
    }
}
