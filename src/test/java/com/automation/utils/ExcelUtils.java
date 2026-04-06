package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * ExcelUtils - Apache POI utility for reading Excel files
 * Provides methods to read test data from Excel files for data-driven testing
 */
public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;

    /**
     * Constructor - Initialize workbook and sheet
     */
    public ExcelUtils(String filePath, String sheetName) {
        try (FileInputStream file = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading Excel file: " + filePath, e);
        }
    }

    /**
     * Read all data from Excel sheet
     * Returns List of Maps where each map represents a row with column headers as keys
     */
    public List<Map<String, String>> readAllData() {
        List<Map<String, String>> data = new ArrayList<>();
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            return data;
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                Cell headerCell = headerRow.getCell(j);
                Cell valueCell = row.getCell(j);

                String header = getCellValue(headerCell);
                String value = getCellValue(valueCell);

                rowData.put(header, value);
            }
            data.add(rowData);
        }
        return data;
    }

    /**
     * Get value from specific cell
     */
    public String getCellValue(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";

        Cell cell = row.getCell(colIndex);
        return getCellValue(cell);
    }

    /**
     * Get value from cell
     */
    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * Get total number of rows
     */
    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    /**
     * Get total number of columns
     */
    public int getColumnCount() {
        return sheet.getRow(0) != null ? sheet.getRow(0).getLastCellNum() : 0;
    }

    /**
     * Close workbook resources
     */
    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing workbook", e);
        }
    }
}

