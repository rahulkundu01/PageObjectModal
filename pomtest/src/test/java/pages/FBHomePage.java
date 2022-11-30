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
