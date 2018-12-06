package CoreFramework;

public class ExternalFileConfiguration {
	public static String defaultDirectory = "D:\\QuoineData\\";
	public static String seleniumDirectory = defaultDirectory + "SeleniumDriver\\";
	public static String testDataDirectory = defaultDirectory + "TestData\\";
	public static String testDataFile = testDataDirectory + "DemoLogin.xlsx";
	public static String testDataSheetName = "Possitive";
	public static int testdataheaderRowIndex = 1;
	public static String xmlresultDirectory = defaultDirectory + "test-output\\testng-results.xml";
	public static String defaultsuiteName = "demosuite";
	public static String defaulttestplanDirectory = defaultDirectory + "TestPlan\\";
	public static String testplanFile = defaulttestplanDirectory + "TestPlan.xlsx";
	public static String testplandefaultSheet = "Demo123";
	public static String testURL = "http://automationpractice.com/index.php";
	public static int globalWaitingTime = 15;
	public static String nullString = "N/A";
	public static String DateformatMMDDYYYY = "MM-DD-YYYY";
	public static String DateformatKKMMSS = "kk:mm:ss";
}
