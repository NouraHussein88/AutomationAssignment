package utils;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelFileManager {
	private FileInputStream fis;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private String excelFilePath;
	private String testDataColumnNamePrefix;

	public ExcelFileManager() {

		initializeVariables();
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "/TestingData/ExcelTestData.xlsx");
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (IOException e) {
			e.getMessage();
		} catch (OutOfMemoryError e) {
			e.getMessage();
		} catch (EmptyFileException e) {
			e.getMessage();
		}

		List<List<Object>> attachments = new ArrayList<>();
		List<Object> testDataFileAttachment = null;
		try {
			testDataFileAttachment = Arrays.asList("Test Data", "Excel", new FileInputStream(excelFilePath));
		} catch (FileNotFoundException e) {
		}
		attachments.add(testDataFileAttachment);
		
	}

	public String getCellData(String rowName) {
		return getCellData(getDefaultSheetName(), rowName, "");
	}

	public String getCellData(String rowName, String columnName) {
		return getCellData(getDefaultSheetName(), rowName, columnName);
	}

	public String getCellData(String sheetName, String rowName, String columnName) {
		try {
			int rowNum = getRowNumberFromRowName(sheetName, rowName);
			int colNum = getColumnNumberFromColumnName(sheetName, columnName);

			// get the desired row
			row = sheet.getRow(rowNum); // why use -1 here?
			// get the desired cell
			cell = row.getCell(colNum);

			// return cell value given the different cell types
			return getCellData();

		} catch (Exception e) {
			e.getMessage();
			return "";
		}
		
	}

	public int getLastColumnNumber() {
		return getLastColumnNumber(getDefaultSheetName());
	}

	public int getLastColumnNumber(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		int lastColumnNumber = 0;
		while (true) {
			try {
				cell = row.getCell(lastColumnNumber);
				if (cell.getCellType() == CellType.STRING) {
					lastColumnNumber++;
				} else {
					return lastColumnNumber - 1;
				}
			} catch (java.lang.NullPointerException e) {
				return lastColumnNumber - 1;
			}
		}
	}

	/**
	 * Looks for the column name that holds the cellData in the specified rowName
	 * and sheetName
	 *
	 * @param sheetName the name of the target Excel sheet
	 * @param rowName   the value of the first cell of the target row
	 * @param cellData  the value of the target cell within the target row
	 * @return the value of the first cell of the target column
	 */
	public String getColumnNameUsingRowNameAndCellData(String sheetName, String rowName, String cellData) {
		String columnName;
		for (int i = 1; i <= getLastColumnNumber(sheetName); i++) {
			columnName = testDataColumnNamePrefix + i;
			if (cellData.equals(getCellData(rowName, columnName))) {
				return columnName;
			}
		}
		
		return "";
	}

	/**
	 * Looks for the column name that holds the cellData in the specified rowName
	 * and the default sheet name
	 *
	 * @param rowName  the value of the first cell of the target row
	 * @param cellData the value of the target cell within the target row
	 * @return the value of the first cell of the target column
	 */
	public String getColumnNameUsingRowNameAndCellData(String rowName, String cellData) {
		return getColumnNameUsingRowNameAndCellData(getDefaultSheetName(), rowName, cellData);
	}

	private void initializeVariables() {
		fis = null;
		workbook = null;
		sheet = null;
		row = null;
		cell = null;
		excelFilePath = "";
		testDataColumnNamePrefix = System.getProperty("testDataColumnNamePrefix");
	}

	private int getRowNumberFromRowName(String sheetName, String rowName) {
		try {
			// get the row number that corresponds to the desired rowName within the first
			// column [0]
			sheet = workbook.getSheet(sheetName);

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);

				if (row != null && row.getCell(0).getStringCellValue().equals(rowName)) {
					return i;
				}
			}
			return -1; // in case of failure this line is unreachable
		} catch (Exception e) {
			e.getMessage();
			return -1;
		}
	}

	private int getColumnNumberFromColumnName(String sheetName, String columnName) {
		try {
			if (!columnName.equals("")) {
				row = sheet.getRow(0);
				for (int i = 0; i < row.getLastCellNum(); i++) {
					if (row.getCell(i).getStringCellValue().equals(columnName)) {
						return i;
					}
				}
			} else {
				return 1;
			}

			
			return -1; // in case of failure this line is unreachable
		} catch (Exception e) {
			e.getMessage();
			return -1;
		}
	}

	private String getCellData() {
		try {
			if (cell.getCellType() == CellType.STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if (cellValue.contains(".0")) {
					cellValue = cellValue.split("\\.")[0];
				}
				if (DateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			} else if (cell.getCellType() == CellType.BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Extracts the first sheet name from the desired workbook.
	 *
	 * @return the first sheet name for the current test data file
	 */
	private String getDefaultSheetName() {
		return workbook.getSheetName(0);
	}

}

