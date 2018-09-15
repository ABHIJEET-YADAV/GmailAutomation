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
        properties.load(new FileReader(new File("test.properties")));
    }

	@AfterMethod
	public void tearDown() throws Exception {
        driver.quit();
    }
	

    @Test
	public void testSendEmail() throws Exception {
    	driver.manage().deleteAllCookies();
    	driver.manage().window().maximize();
        driver.get("https://mail.google.com/");
        SoftAssert assertion= new SoftAssert();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        // Login
        
        // Enter Email Id
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));
        driver.findElement(By.id("identifierNext")).click();

        Thread.sleep(2000);
        // Enter Passward
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(properties.getProperty("password"));
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]")).click();


        Thread.sleep(5000);
        // Click on compose button
        driver.findElement(By.cssSelector(".aic .z0 div")).click();
        
        Thread.sleep(2000);
        // Target account id
        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        
        Thread.sleep(2000);
        // subject input
        driver.findElement(By.name("subjectbox")).sendKeys("This Is Subject");
        
        Thread.sleep(2000);
        // body input
        driver.findElement(By.cssSelector(".Ar.Au div")).sendKeys("This Is Body");
        
        Thread.sleep(2000);
        // click on attach file button
        driver.findElement(By.cssSelector("tr.btC td:nth-of-type(4) div div:nth-of-type(1)")).click();
        
        Thread.sleep(3000);
        // select file using Autoit
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\AutoIt3\\FileUpload.exe");
        
        // wait to upload file completely
        Thread.sleep(10000);
        
        // click on send button
        driver.findElement(By.cssSelector("tr.btC td:nth-of-type(1) div div:nth-of-type(2)")).click();
        
        
        // Wait for the email to arrive in the Inbox
        Thread.sleep(4000);
        
        // Check email present or not and if present then click on it
        List<WebElement> a = driver.findElements(By.xpath("//*[@class='yW']/span"));
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getText().equals("me"))                    //to click on a specific mail which is send by me(Self)
                {             
                a.get(i).click();
                Thread.sleep(3000);
                
                //Email Verification
                
                //Verify Subject
                assertion.assertEquals(driver.findElement(By.cssSelector(".ha h2")).getText(), "This Is Subject", "Subject Is Invalid");
                
                //Verify Body
                assertion.assertEquals(driver.findElement(By.cssSelector(".ii.gt div div")).getText(), "This Is Body", "Body Is Invalid");
                
                //Verify Attachment Name
                Actions action1 = new Actions(driver);
                action1.moveToElement(driver.findElement(By.cssSelector(".aSH"))).build().perform();
                assertion.assertEquals(driver.findElement(By.cssSelector(".aYz div div span")).getText(), "Abhijeet Photo.jpg", "Attachment Is Invalid");
                
                break;                                              //In order to handle only recently received email and break the loop
            }
            else {
				assertTrue(false, "Email Not Found");
			}
        }
        
        assertion.assertAll();
        Thread.sleep(5000);
    }
}
