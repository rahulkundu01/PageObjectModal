# PageObjectModal
Page Object Model with Page Factory in Selenium (Facebook Login/Logout new ui)

What is Page Object Model Design Patten (POM):
Page Object Model is a Design Pattern which has become popular in Selenium Test Automation. It is widely used design pattern in Selenium for enhancing test maintenance and reducing code duplication. Page object model (POM) can be used in any kind of framework such as modular, data-driven, keyword driven, hybrid framework etc. A page object is an object-oriented class that serves as an interface to a page of your Application Under Test(AUT). The tests then use the methods of this page object class whenever they need to interact with the User Interface (UI) of that page. The benefit is that if the UI changes for the page, the tests themselves don’t need to change, only the code within the page object needs to change. Subsequently, all changes to support that new UI is located in one place.

What is Page Factory:
We have seen that ‘Page Object Model’ is a way of representing an application in a test framework. For every ‘page’ in the application, we create a Page Object to reference the ‘page’ whereas a ‘Page Factory’ is one way of implementing the ‘Page Object Model’.

What is the difference between Page Object Model (POM) and Page Factory:
Page Object is a class that represents a web page and hold the functionality and members.
Page Factory is a way to initialize the web elements you want to interact with within the page object when you create an instance of it.

Advantages of Page Object Model Framework:
Code reusability — We could achieve code reusability by writing the code once and use it in different tests.
Code maintainability — There is a clean separation between test code and page specific code such as locators and layout which becomes very easy to maintain code. Code changes only on Page Object Classes when a UI change occurs. It enhances test maintenance and reduces code duplication.
Object Repository — Each page will be defined as a java class. All the fields in the page will be defined in an interface as members. The class will then implement the interface.
Readability — Improves readability due to clean separation between test code and page specific code
Creating a Page Object Model with Page Factory in Selenium WebDriver:
Project Structure:


Step 1: Creating TestBase class. Here we create an object of WebDriver, maximize browser, implementing waits, launching URL and etc.,

In the below example program, I have taken chrome browser and set the System Property to launch chrome browser.

TestBase.java (BASE CLASS)

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
Step 2: Creating classes for each page (Eg., Facebook Login Page, Facebook Inbox Page) to hold element locators and their methods. Usually, we create page objects for all available pages in the AUT. For each page, we create a separate class with a constructor. Identify all the locators and keep them in one class. It allows us to reuse the locators in multiple methods. It allows us to do easy maintenance, if there is any change in the UI, we can simply change on one Page.

Here, I create java files (FacebookLoginPage.java and FacebookInboxPage.java) for the corresponding pages (Facebook Login Page, and Facebook Inbox Page) to hold element locators and their methods.

FBHomePage.java (Webpage 1)

package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
public class FBHomePage {
 WebDriver driver;
  
    public FBHomePage(WebDriver driver){
            this.driver=driver;
    }
    //Using FindBy for locating elements
    
    
@FindBy(how=How.CLASS_NAME, using="x3ajldb") WebElement profileDropdown;
@FindBy(how=How.XPATH, using="//span[contains(.,'Log Out')]") WebElement logoutLink;
@FindBy(how=How.XPATH, using="//a[@aria-label='Rahul Kundu'][contains(.,'Rahul Kundu')]") WebElement loggedInUserNameText;
    
//Defining all the user actions (Methods) that can be performed in the Facebook home page
    // This method to click on Profile Dropdown
public void clickOnProfileDropdown(){
//WebElement profileDropdown = driver.findElement(By.className("x3ajldb"));
profileDropdown.click();
}
//This method to click on Logout link
public void clickOnLogoutLink(){
 
logoutLink.click();
}
//This method to verify LoggedIn Username Text
public String verifyLoggedInUserNameText(){
String userName = loggedInUserNameText.getText();
return userName;
}
}
FBLoginPage.java (Webpage 2)

package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
public class FBLoginPage {
 WebDriver driver;
  
    public FBLoginPage(WebDriver driver){
             this.driver=driver;
    }
//Using FindBy for locating elements
@FindBy(how=How.XPATH, using="//input[contains(@name,'email')]") WebElement emailTextBox;
@FindBy(how=How.XPATH, using="//input[@type='password']") WebElement passwordTextBox;
@FindBy(how=How.XPATH, using="//button[@name='login']") WebElement signinButton;
    // Defining all the user actions (Methods) that can be performed in the Facebook home page
    // This method is to set Email in the email text box
public void setEmail(String strEmail){
emailTextBox.sendKeys(strEmail);
}
//This method is to set Password in the password text box
public void setPassword(String strPassword){
passwordTextBox.sendKeys(strPassword);
}
//This method is to click on Login Button
public void clickOnLoginButton(){
signinButton.click();
}
}
Step 3: Creating Test (Eg., FBLoginTest) based on above pages. As per my test scenario which was mentioned above, scripts run as follows.

Launch browser and open facebook.com
Enter user credentials and do signin
Verify the loggedIn user name and do logout
FBLoginTest.java (Test Case 1)

package tests;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.FBHomePage;
import pages.FBLoginPage;
public class FbLoginTest extends TestBase{
 
 @Test
 public void init() throws Exception{
   //driver.get("https://www.facebook.com");
   FBLoginPage loginpage = PageFactory.initElements(driver, FBLoginPage.class);
   loginpage.setEmail("rahul.******@gmail.com");
   loginpage.setPassword("*********");
   loginpage.clickOnLoginButton();
   
   FBHomePage homepage = PageFactory.initElements(driver, FBHomePage.class);
   homepage.clickOnProfileDropdown();
   homepage.verifyLoggedInUserNameText();
   homepage.clickOnLogoutLink(); 
  }
 
}
Step 4: Creating testng.xml file

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="POM Suite">
 
<test name="Page Object Model Project">
<classes>
<class name="tests.TestBase" />
<class name="tests.FbLoginTest" />
</classes>
</test>
</suite> <!-- Suite -->
Please check the pom.xml that i have used.

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>PageObjectModel</groupId>
  <artifactId>pomtest</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    
    <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.6.0</version>
</dependency>
<dependency>
  <groupId>org.testng</groupId>
  <artifactId>testng</artifactId>
  <version>7.6.0</version>
  <scope>test</scope>
</dependency>
</dependencies>
</project>
This is 100% working code and if you find any difficulty then you can post your queries.
RK







