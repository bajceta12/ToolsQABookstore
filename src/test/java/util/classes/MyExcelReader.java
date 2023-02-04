package util.classes;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class MyExcelReader implements AutoCloseable{
    private final XSSFWorkbook wb;
    private final String filename;
    private HashMap<Integer, HashMap<String, Integer>> sheetColumnHeaders;
    public MyExcelReader(String filename) throws IOException {
        this.filename = filename;
        this.wb = initWorkbook(filename);
        initSheetColumnHeaders();
    }

    public String getData(int sheetIndex, int rowIndex, int columnIndex) {
        try {
            XSSFCell cell = wb.getSheetAt(sheetIndex).getRow(rowIndex).getCell(columnIndex);
            return readCellStringData(cell);
        } catch (NullPointerException e) {
            throw new ExcelReadingException("Error reading cell[row:" + (rowIndex + 1) +
                    ", column:" + encodeColum(columnIndex) + "] in Excel file: " + filename);
        }
    }

    public String getData(String sheetName, int rowIndex, int columnIndex) {
        return getData(wb.getSheetIndex(sheetName), rowIndex, columnIndex);
    }

    public String getData(int sheetIndex, String rowName, String columnName) {
        return getData(sheetIndex, Integer.parseInt(rowName) - 1, decodeColumn(columnName));
    }

    public String getData(String sheetName, String rowName, String columnName) {
        return getData(wb.getSheetIndex(sheetName), Integer.parseInt(rowName) - 1, decodeColumn(columnName));
    }

    public String readFirstFromColumn(int sheetIndex, String columnHeader) {
        int columnIndex = getColumnIndex(sheetIndex, columnHeader);
        return getData(sheetIndex, 1, columnIndex);
    }

    public String readFirstFromColumn(String sheetName, String columnHeader) {
        validateSheetName(sheetName);
        return readFirstFromColumn(wb.getSheetIndex(sheetName), columnHeader);
    }
    public String[] readColumn(int sheetIndex, String columnHeader) {
        int columnIndex = getColumnIndex(sheetIndex, columnHeader);
        String[] columnInfo = new String[getLastRowNumber(sheetIndex, columnIndex)];
        for (int i = 0; i < columnInfo.length; i++) {
            columnInfo[i] = getData(sheetIndex, i + 1, columnIndex);
        }
        return columnInfo;
    }

    public String[] readColumn(String sheetName, String columnHeader) {
        validateSheetName(sheetName);
        return readColumn(wb.getSheetIndex(sheetName), columnHeader);
    }

    public int getLastRowNumber(int sheetIndex) {
        return wb.getSheetAt(sheetIndex).getLastRowNum();
    }

    public int getLastRowNumber(String sheetName) {
        return getLastRowNumber(wb.getSheetIndex(sheetName));
    }

    public int getLastRowNumber(int sheetIndex, int columnIndex) {
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        int lastRowNumber = sheet.getLastRowNum();
        int columnLastRowNumber = 0;
        for (int i = lastRowNumber; i >= 0; i--) {
            if (sheet.getRow(i).getCell(columnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) != null) {
                columnLastRowNumber = i;
                break;
            }
        }
        return columnLastRowNumber;
    }

    public int getLastRowNumber(String sheetName, int columnIndex) {
        return getLastRowNumber(wb.getSheetIndex(sheetName), columnIndex);
    }

    public int getLastRowNumber(int sheetIndex, String columnName) {
        return getLastRowNumber(sheetIndex, decodeColumn(columnName));
    }

    public int getLastRowNumber(String sheetName, String columnName) {
        return getLastRowNumber(wb.getSheetIndex(sheetName), decodeColumn(columnName));
    }

    private XSSFWorkbook initWorkbook(String filename) throws IOException {
        try (FileInputStream fin = new FileInputStream(filename);
             XSSFWorkbook wb = new XSSFWorkbook(fin)) {
            return wb;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(filename + " file not found.");
        }
    }

    private void initSheetColumnHeaders() {
        sheetColumnHeaders = new HashMap<>();
        int numSheets = wb.getNumberOfSheets();
        for (int i = 0; i < numSheets; i++) {
            HashMap<String, Integer> currSheetColumnHeaders = new HashMap<>();
            XSSFRow row = wb.getSheetAt(i).getRow(0);
            int currColumn = 0;
            for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); currColumn++) {
                currSheetColumnHeaders.put(readCellStringData(it.next()), currColumn);
            }
            sheetColumnHeaders.put(i, currSheetColumnHeaders);
        }
    }

    private static int decodeColumn(String columnName) {
        columnName = columnName.toUpperCase();
        int col = 0;
        for (int i = columnName.length() - 1, j = 0; i >= 0; i--, j++) {
            col += (columnName.charAt(i) - 64) * (int) Math.pow(26, j);
        }
        return col - 1;
    }

    private static String encodeColum(int columnIndex) {
        columnIndex++;
        StringBuilder columnName = new StringBuilder();
        int remain;

        while (columnIndex > 0) {
            remain = (columnIndex - 1) % 26;
            columnName.append((char) ('A' + remain));
            columnIndex = (columnIndex - remain) / 26;
        }
        return columnName.reverse().toString();
    }

    @Override
    public void close() throws IOException {
        if (wb != null) {
            wb.close();
        }
    }

    private static class ExcelReadingException extends RuntimeException {
        public ExcelReadingException() {
            super();
        }
        public ExcelReadingException(String message) {
            super(message);
        }
    }

    private int getColumnIndex(int sheetIndex, String columnHeader) {
        Integer columnIndex = sheetColumnHeaders.get(sheetIndex).get(columnHeader);
        if (columnIndex == null) {
            throw new ExcelReadingException("There is no header[" + columnHeader + "] in sheet["
                    + wb.getSheetName(sheetIndex) + "] of Excel file[" + filename + "]");
        }
        return columnIndex;
    }

    private String readCellStringData(Cell cell) {
        CellType cellType = cell.getCellType();
        return switch (cellType) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                double data = cell.getNumericCellValue();
                if (data % 1 == 0.0) {
                    yield String.valueOf((long) data);
                } else {
                    yield String.valueOf(data);
                }
            }
            case _NONE, BLANK -> "";
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> throw new NullPointerException();
        };
    }

    private void validateSheetName(String sheetName) {
        if (wb.getSheetIndex(sheetName) == -1) {
            throw new ExcelReadingException("There is no sheet[" + sheetName + "] in Excel file[" + filename + "]");
        }
    }
}
