package TestCases;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import CoreFramework.ExcelFileReader;
import CoreFramework.ExternalFileConfiguration;
import CoreFramework.TestCase;
import DataDriven.ReadCell;
import Pages.LoginPage;
import Pages.MyAccountPage;

public class LoginSuccessfullTestCase extends TestCase {
	private static Logger logger = Logger.getLogger(LoginSuccessfullTestCase.class);
	private LoginPage loginPage;
	private MyAccountPage myAccountPage;
	String testcasename = "Login Successful Chrome";

	@Test
	public void executeTestCase() throws Exception {
		loginPage = new LoginPage(TestCase.driver);
		myAccountPage = new MyAccountPage(TestCase.driver);
		String filter = "possitive";
		for (int i = 0; i < ExcelFileReader
				.returnAllRowsCells(ExternalFileConfiguration.testDataFile, ExternalFileConfiguration.testDataSheetName)
				.size() - 1; i++) {
			String username = ReadCell.cellValuesByColumnName(i, filter, "username");
			String password = ReadCell.cellValuesByColumnName(i, filter, "password");
			logger.info("Navigate to Login Page");
			loginPage.navigateTo();
			loginPage.checkPage();
			logger.info("Login in with username: " + username + " and password: " + password);
			loginPage.login(username, password);
			myAccountPage.checkPage();
		}

	}
}
