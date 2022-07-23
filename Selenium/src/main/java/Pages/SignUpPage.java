package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {

	WebDriver browser;

	// constructor
	public SignUpPage(WebDriver browser) {
		this.browser = browser;
	}

	// Element Locators
	private By firstName = By.id("firstname");
	private By lastName = By.id("lastname");
	private By emailAddress = By.id("email_address");
	private By password = By.id("password");
	private By confirmationPassword = By.id("confirmation");
	private By signupNews = By.id("is_subscribed");
	private By registerButton = By.xpath("//button[@title='Register']");
	private By hiMessage=By.xpath("//li[@class='success-msg']//li/span"); 
	// Actions
	public void fillRegistrationData(String firstname, String lastname, String email, int randomInt, String Password) {
		System.out.println("Fill all the registration data");
		browser.findElement(firstName).sendKeys(firstname);
		browser.findElement(lastName).sendKeys(lastname);
		browser.findElement(emailAddress).sendKeys(email + randomInt + "@gmail.com");
		browser.findElement(password).sendKeys(Password);
		browser.findElement(confirmationPassword).sendKeys(Password);
		browser.findElement(signupNews).click();
		browser.findElement(registerButton).click();
	}
	public String getHiMessageText() {
		return browser.findElement(hiMessage).getText();
	}
}
