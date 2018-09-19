package pageObjects;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GmailLogin {
	
	static public void verifyEmail(WebDriver driver, String username, String password) throws Exception {
		getUrl(driver);
		emailCard(driver, username);
		passwordCard(driver, password);
		composeMail(driver, username);
		verifyRecievedMail(driver);
		verifyRecievedMail(driver);
	}

	static public void getUrl(WebDriver driver) {
		 driver.get("https://mail.google.com/");
	     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	     driver.manage().timeouts().pageLoadTimeout(3000, TimeUnit.SECONDS);
	}
	
	static public void emailCard(WebDriver driver, String username) throws Exception {
        SoftAssert assertion= new SoftAssert();

		  // Enter Email Id
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(username);
        driver.findElement(By.id("identifierNext")).click();
        Thread.sleep(2000);
	}
        
    static public void passwordCard(WebDriver driver, String password) throws InterruptedException {
        SoftAssert assertion= new SoftAssert();

        // Enter Passward
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]")).click();
        Thread.sleep(5000);
    }
    
    static public void composeMail(WebDriver driver, String username) throws Exception {
        SoftAssert assertion= new SoftAssert();

    	// Click on compose button
        driver.findElement(By.cssSelector(".aic .z0 div")).click();
        
        Thread.sleep(2000);
        // Target account id
        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(username+"gmail.com");
        
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
    }
    
    static public void verifyRecievedMail(WebDriver driver) throws Exception {
        SoftAssert assertion= new SoftAssert();

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
