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
			loginpage.setEmail("rahul.kundu1@gmail.com");
			loginpage.setPassword("Rk@1991987");
			loginpage.clickOnLoginButton();
			
			FBHomePage homepage = PageFactory.initElements(driver, FBHomePage.class);
			homepage.clickOnProfileDropdown();
			homepage.verifyLoggedInUserNameText();
			homepage.clickOnLogoutLink();	
		}
	
}