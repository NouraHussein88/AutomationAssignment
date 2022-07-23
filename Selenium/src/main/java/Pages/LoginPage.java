package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	WebDriver browser;

	// constructor
	public LoginPage(WebDriver browser) {
		this.browser = browser;
	}

	// Element Locators
	By email = By.id("email");
	By password = By.id("pass");
	By loginButton = By.id("send2");

	// Actions
	public void loginToMyAccount(String Email, int randomInt, String Password) {
		System.out.println(
				"Login by this credential [email:" + Email + randomInt + "@gmail.com] Password[" + Password + "]...");
		browser.findElement(email).sendKeys(Email + randomInt + "@gmail.com");
		browser.findElement(password).sendKeys(Password);
		browser.findElement(loginButton).click();
	}
}
