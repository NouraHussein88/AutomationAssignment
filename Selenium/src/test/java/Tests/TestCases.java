package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import Pages.SignUpPage;
import Pages.MyAccountPage;
import Pages.HomePage;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelFileManager;

public class TestCases {
	WebDriver browser;
	int randomInt;
	Random randomGenerator;
	SignUpPage signUpPage;
	LoginPage loginpage;
	MyAccountPage myAccountPage;
	HomePage homePage;
	String reportPath;
	ExtentReports extentreport;
	ExcelFileManager testData;

	@Test()
	public void Registration() {
		extentreport.createTest("Check Registration");
		homePage.navigateToSignUpPage();

		signUpPage.fillRegistrationData(testData.getCellData("firstName"), testData.getCellData("lastName"),
				testData.getCellData("email"), randomInt, testData.getCellData("password"));
		assertEquals(signUpPage.getHiMessageText(), testData.getCellData("expectedMessage"));
		System.out.println("Asserting that Customer has Registered successfully.");
		myAccountPage.logout();
		extentreport.flush();
	}

	@Test(dependsOnMethods = { "Registration" })
	public void AddItemToShoppingcart() throws InterruptedException {
		extentreport.createTest("Add Item To Shopping cart");
		homePage.navigateToLogInPage();
		loginpage.loginToMyAccount(testData.getCellData("email"), randomInt, testData.getCellData("password"));
		myAccountPage.SelecyRequirdSectionAndCategory(Integer.parseInt(testData.getCellData("requiredSection")),
				testData.getCellData("requiredcatagory"));
		myAccountPage.ClickOnViewDetailsForRequiredItem(testData.getCellData("itemName"));
		myAccountPage.AddRequiredItemToShoppingCart(testData.getCellData("itemColor"),
				testData.getCellData("itemsize"));
		assertTrue(myAccountPage.CheckTheGrandTotal(Integer.parseInt(testData.getCellData("customerBudget"))));
		System.out.println("Asserting that Grand Total is less than you budget of $350.");
		extentreport.flush();
	}

	@BeforeClass
	public void beforeClase() {
		WebDriverManager.chromedriver().setup();
		browser = new ChromeDriver();
		testData = new ExcelFileManager();
		randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(1000);
		homePage = new HomePage(browser);
		signUpPage = new SignUpPage(browser);
		loginpage = new LoginPage(browser);
		myAccountPage = new MyAccountPage(browser);

		// Extent Reporting
		reportPath = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter configReport = new ExtentSparkReporter(reportPath);
		configReport.config().setReportName("Test Automation Result");
		configReport.config().setDocumentTitle("Test Results");
		extentreport = new ExtentReports();
		extentreport.attachReporter(configReport);
	}

	@BeforeMethod
	public void beforMethod() {
		homePage.navigateToURL();
	}

	@AfterClass
	public void afterClase() {
		browser.quit();
	}

}
