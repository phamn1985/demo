package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import CoreFramework.BasePage;

public class LoginPage extends BasePage {

	private static By txtEmail = By.xpath("//*[@class='is_required validate account_input form-control'][@id='email']");
	private static By txtPassword = By
			.xpath("//*[@class='is_required validate account_input form-control'][@id='passwd']");
	private static By btnLogin = By.xpath("//*[@id='SubmitLogin']");
	private static String pageURL = "?controller=authentication&back=my-account";
	private static WebDriver driver;

	public LoginPage(WebDriver driver) {
		super(driver, txtPassword, pageURL);
		this.driver = driver;
	}

	public void login(String username, String password) {
		this.sendQuery(txtEmail, username);
		this.sendQuery(txtPassword, password);
		this.nativeClick(btnLogin);
	}

}
