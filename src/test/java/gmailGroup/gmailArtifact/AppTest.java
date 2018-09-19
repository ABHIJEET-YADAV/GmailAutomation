package gmailGroup.gmailArtifact;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


@Test
public class AppTest {
    private WebDriver driver;
    private Properties properties = new Properties();

	@BeforeMethod
	public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "F:\\Library\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
    	driver.manage().window().maximize();
        properties.load(new FileReader(new File("test.properties")));
    }

	@AfterMethod
	public void tearDown() throws Exception {
        driver.quit();
    }
	

    @Test
	public void testSendEmail() throws Exception {
    	pageObjects.GmailLogin.verifyEmail(driver, properties.getProperty("username"), properties.getProperty("password"));
    }
}
