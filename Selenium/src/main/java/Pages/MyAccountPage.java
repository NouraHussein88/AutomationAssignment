package Pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;

public class MyAccountPage {
	WebDriver browser;

	// constructor
	public MyAccountPage(WebDriver browser) {
		this.browser = browser;
	}

	// Element Locators
	private By sectionName(int requiredSection) {return By.xpath("//*[@class='nav-primary']/li[\" + requiredSection + \"]/a");}
	private By categoryName(String requiredcatagory) {return By.xpath("//li[\" + requiredcatagory + \"]/ul/li");}
	private By colorLocator = By.id("attribute92");
	private By sizeLocator = By.id("attribute180");
	private By productsGrid = By.xpath("//ul[contains(@class,'products-grid')]/li/div[@class='product-info']/h2/a");
	private By viewDetailsButton = By.xpath("//*[@class='actions']/a");
	private By addToCart=By.cssSelector("button[title='Add to Cart']");
	private By accountLink = By.className("skip-account");
	private By logOut = By.xpath("//a[contains(.,'Log Out')]");
	// Actions

	public void SelecyRequirdSectionAndCategory(int requiredSection, String requiredcatagory) {
		// Select Required Section
		WebElement Menue = browser.findElement(sectionName(requiredSection));
		Actions action = new Actions(browser);
		action.moveToElement(Menue).perform();
		System.out.println("Selected Section is [" + Menue.getText() + "]");
		// Select Required Category
		List<WebElement> allCategory = browser.findElements(categoryName(requiredcatagory));
		for (WebElement category : allCategory) {
			if (category.getText().trim().contains(requiredcatagory)) {
				System.out.println("Selected Category is [" + category.getText() + "]");
				category.click();
				break;
			}
		}

	}

	public void ClickOnViewDetailsForRequiredItem(String itemName) {
		// Select Required item
		List<WebElement> allitems = browser.findElements(productsGrid);
		for (WebElement item : allitems) {
			if (item.getText().contains(itemName)) {
				System.out.println("Selected Item is[" + item.getText() + "]");
				browser.findElement(RelativeLocator.with(viewDetailsButton).below(item)).click();
				break;
			}
		}
	}

	public void AddRequiredItemToShoppingCart(String color, String size) {
		// Select item color
		Select selectColor = new Select(browser.findElement(colorLocator));
		selectColor.selectByVisibleText(color);
		System.out.println("Item color is[" + color + "]");

		// Select item size
		Select selectSize = new Select(browser.findElement(sizeLocator));
		selectSize.selectByVisibleText(size);
		System.out.println("Item size is[" + size + "]");

		// Add item to cart
		browser.findElement(addToCart).click();
		System.out.println("Item added successfully to your shopping card.");
	}

	public boolean CheckTheGrandTotal(int customerBudget) {
		WebElement totalCard = browser
				.findElement(By.xpath("//*[@class='a-right'][contains(.,'Grand Total')]/following-sibling::td"));
		System.out.println("The grand total is [" + totalCard.getText() + "]");
		Integer totalAmmount = Integer
				.parseInt(totalCard.getText().trim().replace("$", " ").replace(".", " ").split(" ")[1]);
		if (totalAmmount < customerBudget)
			return true;
		else
			return false;
	}

	public void RemoveFromCard() {

	}

	public void logout() {
		browser.findElement(accountLink).click();
		browser.findElement(logOut).click();
	}
}
