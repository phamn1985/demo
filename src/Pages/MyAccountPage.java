package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import CoreFramework.BasePage;

public class MyAccountPage extends BasePage {

	private static By containerMyAccount = By.xpath("//*[@id='my-account']//*[@class='info-account']");
	private static String pageURL = "?controller=my-account";
	private static WebDriver driver;

	public MyAccountPage(WebDriver driver) {
		super(driver, containerMyAccount, pageURL);
		this.driver = driver;
	}

}
