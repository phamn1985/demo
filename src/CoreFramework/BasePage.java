package CoreFramework;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage extends BaseComponent {

	public By pageVerifyElementLocator;
	public String pageURL;
	private static WebDriver driver;
	private static Logger logger = Logger.getLogger("BasePage");

	public BasePage(WebDriver driver, By pageVerifyElementLocator, String pageURL) {
		super(driver);
		this.driver = driver;
		this.pageVerifyElementLocator = pageVerifyElementLocator;
		this.pageURL = this.getBaseURL() + pageURL;
	}

	public void checkPage() {
		this.waitForElement(pageVerifyElementLocator);
		this.verifyElementExist(pageVerifyElementLocator);
	}

	public void navigateTo() {
		driver.navigate().to(pageURL);
	}
}
