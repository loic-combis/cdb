package selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TitleTest {

    private WebDriver driver;

    @Test
    public void testTitle() {
        driver.get("http://localhost:8080/cdb/list-computers");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Computer Database"));
    }

    @BeforeTest
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "/home/excilys/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

}
