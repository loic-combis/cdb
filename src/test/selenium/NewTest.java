package selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class NewTest {
    
    private WebDriver driver;
    
    @Test               
    public void testEasy() {    
        driver.get("http://localhost:8080/cdb/list-computers");  
        String title = driver.getTitle();                
        Assert.assertTrue(title.contains("Computer Database"));      
    }   
    @BeforeTest
    public void beforeTest() {  
        driver = new FirefoxDriver();  
    }       
    @AfterTest
    public void afterTest() {
        driver.quit();          
    }   

}
