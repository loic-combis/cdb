package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TitleTest {

    private WebDriver driver;

    /*
     * @Test public void testTitle() {
     * driver.get("http://localhost:8080/computers"); String title =
     * driver.getTitle(); Assert.assertTrue(title.contains("Computer Database")); }
     */

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
