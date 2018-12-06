package CoreFramework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseComponent {
	private static Logger logger = Logger.getLogger(BaseComponent.class.getName());

	private WebDriver driver;
	WebDriverWait wait;

	public BaseComponent(WebDriver driver) {
		this.driver = driver;

		wait = (WebDriverWait) new WebDriverWait(driver, ExternalFileConfiguration.globalWaitingTime);
	}

	public void verifyElementExist(By elementLocator) {
		try {
			driver.findElement(elementLocator).isDisplayed();
		} catch (StaleElementReferenceException se) {
			this.waitForElement(elementLocator);
		}
		if (driver.findElement(elementLocator).isDisplayed()) {
			Assert.assertEquals(true, true, "");
		} else {
			Assert.assertEquals(true, false, "Element is not Exist");
		}
	}

	public void sleep(int Second) {
		try {
			Thread.sleep(Second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getBaseURL() {
		return ExternalFileConfiguration.testURL;
	}

	public String getcurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return (dateFormat.format(date));
	}

	public void scrolltoview(By elementLocator) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(elementLocator));
		} catch (StaleElementReferenceException e) {
			this.sleep(1000);
			this.waitForElement(elementLocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(elementLocator));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("waitForElement has error !!!!!!");
		}
	}

	public void nativeClick(By elementLocator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(elementLocator)));
			driver.findElement(elementLocator).click();

		} catch (StaleElementReferenceException se) {
			this.waitForElement(elementLocator);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(elementLocator)));
			driver.findElement(elementLocator).click();
		} catch (Exception e1) {
			logger.error(e1);
		}
	}

	public void sendQuery(By elementLocator, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementLocator)));
			driver.findElement(elementLocator).sendKeys(text);

		} catch (StaleElementReferenceException se) {
			this.waitForElement(elementLocator);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementLocator)));
			driver.findElement(elementLocator).sendKeys(text);
		} catch (TimeoutException te1) {
			this.scrolltoview(elementLocator);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementLocator)));
			driver.findElement(elementLocator).sendKeys(text);
		} catch (Exception e1) {
			logger.error(e1);
		}
	}

	public void waitForElement(By elementLocator) {
		int attempts = 0;
		this.sleep(500);
		try {
			while (attempts < ExternalFileConfiguration.globalWaitingTime * 2) {
				try {
					this.sleep(500);
					new Actions(driver).moveToElement(driver.findElement(elementLocator));
					break;
				} catch (StaleElementReferenceException e) {
					this.sleep(500);
					attempts++;
					System.out.println("Stale detected @" + driver.findElement(elementLocator).toString());
				} catch (NoSuchElementException ne) {
					this.sleep(500);
					attempts++;
					System.out.println("No Element detected @" + driver.findElement(elementLocator).toString());
				}

			}
		} catch (TimeoutException te) {
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("waitForElement has error !!!!!!");
		}
	}

}
