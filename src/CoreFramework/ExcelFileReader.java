package CoreFramework;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {

	private int returnColumnIndexByValue(String orgfilePath, String sheetname, int rowIndex, String filterValue)
			throws Exception {
		int result = -1;
		String filePath = orgfilePath.replace("\\", "/");
		Vector<Hashtable<Integer, XSSFCell>> allrows = returnAllRowsCells(filePath, sheetname);
		Hashtable<Integer, XSSFCell> currentRow = (Hashtable<Integer, XSSFCell>) allrows.get(rowIndex);
		for (int i = 0; i < currentRow.size(); i++) {
			XSSFCell resultCells = currentRow.get(i);
			if (currentRow.get(i).toString().trim().equalsIgnoreCase(filterValue)) {
				result = resultCells.getColumnIndex();
				break;
			}
		}
		if (result == -1) {
			throw new Exception("Column not found");
		}
		return result;
	}

	public int returnRowIndexByValue(String filePath, String sheetname, String filterValue) throws Exception {
		int result = -1;
		Vector allrows = returnAllRowsCells(filePath, sheetname);
		for (int rowIndex = 0; rowIndex < allrows.size(); rowIndex++) {
			Hashtable<Integer, XSSFCell> currentRow = (Hashtable<Integer, XSSFCell>) allrows.get(rowIndex);
			for (int i = 0; i < 3; i++) {
				XSSFCell resultCells = currentRow.get(i);
				if (resultCells != null) {
					if (resultCells.toString().trim().equalsIgnoreCase(filterValue)) {
						result = resultCells.getRowIndex();
						break;
					}
				}
			}
		}

		if (result == -1) {
			throw new Exception("Row not found");
		}
		return result;
	}

	public static Vector returnAllRowsCells(String orgfilePath, String sheetname) throws Exception {
		Vector rows = new Vector();
		String filePath = orgfilePath.replace("\\", "/");
		try {
			XSSFWorkbook myWorkBook = new XSSFWorkbook(filePath);
			XSSFSheet mySheet = myWorkBook.getSheet(sheetname);
			Iterator rowIter = mySheet.rowIterator();

			while (rowIter.hasNext()) {
				XSSFRow myRow = (XSSFRow) rowIter.next();
				Iterator cellIter = myRow.cellIterator();
				Hashtable<Integer, XSSFCell> cellStoreVector = new Hashtable<Integer, XSSFCell>();
				while (cellIter.hasNext()) {
					XSSFCell myCell = (XSSFCell) cellIter.next();
					cellStoreVector.put(myCell.getColumnIndex(), myCell);
				}
				rows.addElement(cellStoreVector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rows;
	}

	private Vector returnFilteredRowsCells(String filePath, String sheetname, String columnName, String filterValue) {
		Vector result = new Vector();
		try {
			int colIndex = returnColumnIndexByValue(filePath, sheetname,
					ExternalFileConfiguration.testdataheaderRowIndex, columnName);
			Vector allRows = returnAllRowsCells(filePath, sheetname);
			for (int i = allRows.size() - ExternalFileConfiguration.testdataheaderRowIndex; i <= allRows.size(); i++) {
				Vector currentRow = new Vector();
				currentRow = (Vector) allRows.get(i);
				XSSFCell filterCell = (XSSFCell) currentRow.get(colIndex);
				if (filterCell.getStringCellValue().equalsIgnoreCase(filterValue)) {
					result.add(currentRow);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList returnDataByTestCaseName(String filePath, String sheetname, String testcaseName,
			String columnName) {
		String testcaseColumnName = "TEST SCRIPT NAME";
		ArrayList resultList = new ArrayList();
		try {
			Vector testcasedata = returnFilteredRowsCells(filePath, sheetname, testcaseColumnName, testcaseName);
			Vector headerdata = (Vector) returnAllRowsCells(filePath, sheetname).get(0);// element
			headerdata.iterator();
			Iterator<Vector> rows = testcasedata.iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
}
