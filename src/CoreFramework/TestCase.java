package CoreFramework;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestCase {
	public static WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public static void setupDriver(String browser) throws Exception {

		if (browser.trim().equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.trim().equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					ExternalFileConfiguration.seleniumDirectory + "chromedriver.exe");
			System.out.println(ExternalFileConfiguration.seleniumDirectory + "chromedriver.exe");
			driver = new ChromeDriver();
		}
		// other driver browser goes here
		else {
			throw new Exception("Browser is not correct");

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(ExternalFileConfiguration.globalWaitingTime, TimeUnit.SECONDS);
		driver.navigate().to(ExternalFileConfiguration.testURL);
	}

	@AfterTest
	public void closeupDriver() throws InterruptedException {
		Thread.sleep(2000);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
				.format(new Timestamp(System.currentTimeMillis()));
		try {
			FileUtils.copyFile(scrFile, new File(ExternalFileConfiguration.defaultDirectory + "Screenshot\\"
					+ this.getClass().getName() + timeStamp + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.close();
	}
}
