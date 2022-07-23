package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
	WebDriver browser;
	String url = "http://magento-demo.lexiconn.com/";

	// constructor
	public HomePage(WebDriver browser) {
		this.browser = browser;
	}

	// Element Locators
	private By accountLink = By.className("skip-account");
	private By loginLink = By.linkText("Log In");
	private By registerLink = By.linkText("Register");

	// Actions
	public void navigateToURL() {
		System.out.println("Navigate to [" + url + "]...");
		browser.navigate().to(url);
	}
	public void navigateToSignUpPage() {
		System.out.println("Go to the register page");
		browser.findElement(accountLink).click();
		browser.findElement(registerLink).click();
	}
	public void navigateToLogInPage() {
		System.out.println("Go To Login Page...");
		browser.findElement(accountLink).click();
		browser.findElement(loginLink).click();
	}
}
