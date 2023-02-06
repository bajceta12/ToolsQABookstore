package tests.login_page;

import org.testng.annotations.DataProvider;
import util.classes.MyExcelReader;

import java.io.IOException;
import java.util.HashMap;


public class LoginDataProvider {
    private static final String excelFile = "src/test/java/data/bookstore_data.xlsx";
    private static final String sheetName = "LoginData";

    private static final HashMap<DataFor[], Object[][]> preparedData = new HashMap<>();

    public enum DataFor {
        INVALID_USERNAMES("Invalid Usernames", null),
        INVALID_PASSWORDS("Invalid Passwords", null),
        VALID_USERNAMES("Valid Usernames", null),
        VALID_PASSWORDS("Valid Passwords", null);

        private final String columnHeader;
        private String[] data;

        DataFor(String columnHeader, String[] data) {
            this.columnHeader = columnHeader;
            this.data = data;
        }

        public String[] getData() {
            if (data == null) {
                try (MyExcelReader excelReader = new MyExcelReader(excelFile)) {
                    data = excelReader.readColumn(sheetName, columnHeader);
                } catch (IOException e) {
                    throw new IllegalStateException("Couldn't read from excel file: " + excelFile);
                }
            }
            return data;
        }
    }

    @DataProvider (name = "invalid usernames and invalid passwords")
    public static Object[][] invUnInvPw() {
        return getPreparedData(DataFor.INVALID_PASSWORDS, DataFor.INVALID_PASSWORDS);
    }

    @DataProvider (name = "valid usernames and invalid passwords")
    public static Object[][] valUnInvPw() {
        return getPreparedData(DataFor.VALID_USERNAMES, DataFor.INVALID_PASSWORDS);
    }

    @DataProvider (name = "invalid usernames and valid passwords")
    public static Object[][] invUnValPw() {
        return getPreparedData(DataFor.INVALID_USERNAMES, DataFor.VALID_PASSWORDS);
    }

    @DataProvider (name = "valid usernames and valid passwords")
    public static Object[][] valUnValPw() {
        return getPreparedData(DataFor.VALID_USERNAMES, DataFor.VALID_PASSWORDS);
    }

    private static Object[][] getData(DataFor data1, DataFor data2) {
        Object[][] combinedData = new Object[data1.getData().length * data2.getData().length][2];
        int i = 0;
        for (String d1 : data1.getData()) {
            for (String d2 : data2.getData()) {
                combinedData[i++] = new Object[] {d1, d2};
            }
        }
        return combinedData;
    }

    private static Object[][] getPreparedData(DataFor data1, DataFor data2) {
        DataFor[] key = new DataFor[] {data1, data2};
        if (preparedData.get(key) == null) {
            preparedData.put(key, getData(data1, data2));
        }
        return preparedData.get(key);
    }
}
