package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.TestConfiguration;

public class OpenMRSLoginPage {

    private WebDriver driver;
    private TestConfiguration testConfiguration;
    private String portNumber;
    public OpenMRSLoginPage() {
        driver = DefaultPage.getInstance();
        testConfiguration = TestConfiguration.instance();
        portNumber = testConfiguration.portNumber();
    }

    public void getOpenMRSDashBoard() {
        driver.get("http://localhost:"+portNumber+"/openmrs/module/motechmodule/index.htm");
        WebElement userName = driver.findElement(By.id("username"));;
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.xpath("//input[@value = 'Log In']"));
      
        userName.sendKeys("admin");
        password.sendKeys("Openmr5tw");
        login.click();
    }

    public void close() {
        driver.close();
    }
}
