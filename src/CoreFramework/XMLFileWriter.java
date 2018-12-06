package CoreFramework;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class XMLFileWriter {
	public static XmlSuite createXMLTestSuite(String parrallel) {
		String testnameCol = "TEST NAME";
		String testclassCol = "TEST SCRIPT NAME";
		String readytoExecuteCol = "EXECUTION REQUIRED";
		String browserCol = "Browser";
		String envCol = "TEST ENVIRONMENT";

		XmlSuite suite = new XmlSuite();

		suite.setName(ExternalFileConfiguration.defaultsuiteName);
		if (parrallel.trim().equalsIgnoreCase("yes")) {
			suite.setParallel(ParallelMode.TESTS);
		} else {
			suite.setParallel(ParallelMode.FALSE);
		}
		suite.setVerbose(2);
		ExcelFileReader exRead = new ExcelFileReader();
		List<XmlTest> tests = new ArrayList<XmlTest>();

		try {
			int browserColInd = -1;
			int testnameColInd = -1;
			int testclassColInd = -1;
			int readytoExecuteColInd = -1;
			int envColInd = -1;
			Vector alltestplanRows = exRead.returnAllRowsCells(ExternalFileConfiguration.testplanFile,
					ExternalFileConfiguration.testplandefaultSheet);
			Hashtable<Integer, XSSFCell> columnNames = (Hashtable<Integer, XSSFCell>) alltestplanRows.get(0);
			for (int i = 0; i < columnNames.size(); i++) {
				XSSFCell col = columnNames.get(i);
				if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(browserCol)) {
					browserColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(testnameCol)) {
					testnameColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(testclassCol)) {
					testclassColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(readytoExecuteCol)) {
					readytoExecuteColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(envCol)) {
					envColInd = col.getColumnIndex();
				}
			}

			for (int x = 1; x < alltestplanRows.size(); x++) {
				Hashtable<Integer, XSSFCell> columnValue = (Hashtable<Integer, XSSFCell>) alltestplanRows.get(x);
				XSSFCell cursorCell = (XSSFCell) columnValue.get(readytoExecuteColInd);
				String status = cursorCell.getStringCellValue();
				if (status.trim().equalsIgnoreCase("YES")) {
					XmlTest test = new XmlTest(suite);
					cursorCell = (XSSFCell) columnValue.get(testnameColInd);
					test.setName(cursorCell.getStringCellValue());
					cursorCell = (XSSFCell) columnValue.get(browserColInd);
					test.addParameter("browser", cursorCell.getStringCellValue().trim());
					cursorCell = (XSSFCell) columnValue.get(envColInd);
					test.addParameter("testURL", cursorCell.getStringCellValue().trim());
					List<XmlPackage> ps = new ArrayList<XmlPackage>();
					test.setPackages(ps);
					XmlClass testclass = new XmlClass();
					cursorCell = (XSSFCell) columnValue.get(testclassColInd);
					testclass.setName("TestCases" + "." + cursorCell.getStringCellValue());
					List<XmlClass> classes = new ArrayList<XmlClass>();
					classes.add(testclass);
					test.setClasses(classes);
					tests.add(test);
				}
			}
			suite.setTests(tests);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return suite;
	}
}
