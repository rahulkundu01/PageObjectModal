package tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestBase {
	public static WebDriver driver = null;
	@BeforeSuite
	public void initialize() throws IOException{
	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\java\\drivers\\chromedriver.exe");
	
	ChromeOptions coptions = new ChromeOptions();
	coptions.addArguments("--disable-notifications");
	
	driver = new ChromeDriver(coptions);
	//To maximize browser
	                driver.manage().window().maximize();
	        //Implicit wait
	         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	//To open facebook
	                driver.get("https://www.facebook.com/login");
	}
	@AfterSuite
	//Test cleanup
	public void TeardownTest()
	    {
	        TestBase.driver.quit();
	    }
}

