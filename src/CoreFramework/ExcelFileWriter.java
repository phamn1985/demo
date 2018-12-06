package CoreFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileWriter {
	public static void updateTestSuiteResultToExcelFile(String excelfilepath, String sheetname, String resultxmlPath,
			String suitename) {
		String testnameColHeader = "TEST NAME";
		String teststatusColHeader = "EXECUTION STATUS";
		String teststartdateColHeader = "EXECUTION START DATE";
		String testenddateColHeader = "EXECUTION END DATE";
		String teststarttimeColHeader = "EXECUTION START TIME";
		String testendtimeColHeader = "EXECUTION END TIME";
		String durationColHeader = "TIME ELAPESED";

		ExcelFileReader exRead = new ExcelFileReader();
		try {
			int testnameColInd = -1;// exRead.returnColumnIndexByValue(excelfilepath,
									// sheetname, 0, testnameColHeader);
			int teststatusColInd = -1; // exRead.returnColumnIndexByValue(excelfilepath,
										// sheetname, 0, teststatusColHeader);
			int teststartdateColInd = -1; // exRead.returnColumnIndexByValue(excelfilepath,
											// sheetname, 0,
											// teststartdateColHeader);
			int testenddateColInd = -1;// exRead.returnColumnIndexByValue(excelfilepath,
										// sheetname, 0, testenddateColHeader);
			int teststarttimeColInd = -1;// exRead.returnColumnIndexByValue(excelfilepath,
											// sheetname, 0,
											// teststarttimeColHeader);
			int testendtimeColInd = -1;// exRead.returnColumnIndexByValue(excelfilepath,
										// sheetname, 0, testendtimeColHeader);
			int durationColInd = -1; // exRead.returnColumnIndexByValue(excelfilepath,
										// sheetname, 0, durationColHeader);

			Vector alltestplanRows = exRead.returnAllRowsCells(ExternalFileConfiguration.testplanFile,
					ExternalFileConfiguration.testplandefaultSheet);
			Hashtable<Integer, XSSFCell> columnNames = (Hashtable<Integer, XSSFCell>) alltestplanRows.get(0);
			for (int i = 0; i < columnNames.size(); i++) {
				XSSFCell col = columnNames.get(i);
				if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(testnameColHeader)) {
					testnameColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(teststatusColHeader)) {
					teststatusColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(teststartdateColHeader)) {
					teststartdateColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(testenddateColHeader)) {
					testenddateColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(teststarttimeColHeader)) {
					teststarttimeColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(testendtimeColHeader)) {
					testendtimeColInd = col.getColumnIndex();
				} else if (columnNames.get(i).getStringCellValue().trim().equalsIgnoreCase(durationColHeader)) {
					durationColInd = col.getColumnIndex();
				}
			}

			// exRead.returnedFilteredRowsCells(filePath, sheetname, columnName,
			// filterValue)
			List xmlResults = TestNGResultReader.returnTestSuiteResultFromXML(resultxmlPath, suitename);
			Iterator it = xmlResults.iterator();

			for (int i = 0; i < xmlResults.size(); i++) {
				ResultObject reob = new ResultObject();
				reob = (ResultObject) xmlResults.get(i);
				int searchtestnamerow = exRead.returnRowIndexByValue(excelfilepath, sheetname, reob.testname);
				FileInputStream file = new FileInputStream(excelfilepath);
				XSSFWorkbook wb = new XSSFWorkbook(file);
				XSSFSheet sheet = wb.getSheet(sheetname);
				if (searchtestnamerow > 0) {

					Cell cell = null;

					// cell =
					// sheet.getRow(searchtestnamerow).getCell(teststatusColInd
					// );
					XSSFRow sheetrow = sheet.getRow(searchtestnamerow);
					cell = sheetrow.createCell(teststatusColInd);
					cell.setCellValue(reob.result);

					cell = sheetrow.createCell(teststartdateColInd);
					cell.setCellValue(reob.startdate);
					cell = sheetrow.createCell(testenddateColInd);
					cell.setCellValue(reob.enddate);

					cell = sheetrow.createCell(teststarttimeColInd);
					cell.setCellValue(reob.starttime);

					cell = sheetrow.createCell(testendtimeColInd);
					cell.setCellValue(reob.endtime);

					cell = sheetrow.createCell(durationColInd);
					cell.setCellValue(reob.duration);

				}

				FileOutputStream outFile = new FileOutputStream(new File(excelfilepath));
				wb.write(outFile);
				outFile.close();
			}

		} catch (Exception e) {
			// System.err.println("There are some errors: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
