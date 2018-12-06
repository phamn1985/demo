/**
 * 
 */
package DataDriven;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;

import CoreFramework.ExcelFileReader;
import CoreFramework.ExternalFileConfiguration;

/**
 * @author anpham
 *
 */
public class ReadCell {

	private static Logger log = Logger.getLogger(ReadCell.class.getName());

	public static String cellValuesByColumnName(int index, String testcaseName, String columnName) {
		log.info("Get cell values by column name = " + columnName);

		List<String> result = new ArrayList<String>();
		ExcelFileReader reader = new ExcelFileReader();
		Vector alllines = new Vector();
		int testcasenameIndex = -1;
		int cellIndex = -1;
		try {
			alllines = reader.returnAllRowsCells(ExternalFileConfiguration.testDataFile,
					ExternalFileConfiguration.testDataSheetName);
			Hashtable<Integer, XSSFCell> columnNames = (Hashtable<Integer, XSSFCell>) alllines.get(0);
			for (int i = 0; i < columnNames.size(); i++) {
				XSSFCell col = columnNames.get(i);
				if (col.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
					cellIndex = col.getColumnIndex();
				}

				else if (col.getStringCellValue().trim().equalsIgnoreCase("TEST SCRIPT NAME")) {
					testcasenameIndex = col.getColumnIndex();
				} else if (col.getStringCellValue().trim().equalsIgnoreCase("_testdatafilter")) {
					testcasenameIndex = col.getColumnIndex();
				}
				if ((testcasenameIndex >= 0) && (cellIndex >= 0)) {
					break;
				}
			}
			if ((testcasenameIndex >= 0) && (cellIndex >= 0)) {
				// do nothing
			} else {
				log.error("There is error while getting cell values by column name: DATA ERROR!!!");
				throw new Exception("DATA ERROR!!!");
			}
			for (int x = 1; x < alllines.size(); x++) {
				Hashtable<Integer, XSSFCell> line = (Hashtable<Integer, XSSFCell>) alllines.get(x);
				// for (int x = 1; x<line.size();x++){
				if (line.get(testcasenameIndex).getStringCellValue().equalsIgnoreCase(testcaseName)) {
					XSSFCell cellabc = line.get(cellIndex);
					if ((cellabc == null) || (line.get(cellIndex).getStringCellValue().length() < 1)) {
						result.add(ExternalFileConfiguration.nullString);
					} else {
						result.add(cellabc.getStringCellValue());
					}

				} else {

				}
			}

		} catch (NullPointerException e) {
			log.error("There is error while getting cell values by column name: " + e.getMessage());
		} catch (Exception e) {
			log.error("There is error while getting cell values by column name: " + e.getMessage());
			e.printStackTrace();
		}
//		log.info("Value of column " + columnName + " is " + (String) result.get(index));
		return (String) result.get(index);

	}

	public static String cellCSVValuesByColumnName(int index, String testcaseName, String csvName, String columnName) {
		log.info("Get cell values by column name = " + columnName);
		List<String> result = new ArrayList<String>();
		ExcelFileReader reader = new ExcelFileReader();
		Vector alllines = new Vector();
		int testcasenameIndex = -1;
		int cellIndex = -1;
		try {

			alllines = reader.returnAllRowsCells(ExternalFileConfiguration.testDataFile,
					ExternalFileConfiguration.testDataSheetName);
			Hashtable<Integer, XSSFCell> columnNames = (Hashtable<Integer, XSSFCell>) alllines.get(0);
			for (int i = 0; i < columnNames.size(); i++) {
				XSSFCell col = columnNames.get(i);
				if (col.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
					cellIndex = col.getColumnIndex();
				}

				else if (col.getStringCellValue().trim().equalsIgnoreCase("TEST SCRIPT NAME")) {
					testcasenameIndex = col.getColumnIndex();
				}
				if ((testcasenameIndex >= 0) && (cellIndex >= 0)) {
					break;
				}
			}
			if ((testcasenameIndex >= 0) && (cellIndex >= 0)) {
				// do nothing
			} else {
				log.error("There is error while getting cell values by column name: DATA ERROR!!!");
				throw new Exception("DATA ERROR!!!");
			}
			for (int x = 1; x < alllines.size(); x++) {
				Hashtable<Integer, XSSFCell> line = (Hashtable<Integer, XSSFCell>) alllines.get(x);

				// for (int x = 1; x<line.size();x++){
				if (line.get(testcasenameIndex).getStringCellValue().equalsIgnoreCase(testcaseName)) {
					XSSFCell cellabc = line.get(cellIndex);
					if ((cellabc == null) || (line.get(cellIndex).getStringCellValue().length() < 1)) {
						result.add(ExternalFileConfiguration.nullString);
					} else {
						result.add(cellabc.getStringCellValue());
					}
				} else {

				}
			}

		} catch (NullPointerException e) {
			log.error("There is error while getting cell values by column name: " + e.getMessage());
		} catch (Exception e) {
			log.error("There is error while getting cell values by column name: " + e.getMessage());
			e.printStackTrace();
		}
//		log.info("Value of column " + columnName + " is " + (String) result.get(index));
		return (String) result.get(index);

	}
}
