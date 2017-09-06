import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;

public class DataAndPropertyProvider {

    private static Object[][] readFile(File file) {
        String text = null;
        Logger log = LogManager.getRootLogger();

        try {
            text = FileUtils.readFileToString(file);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        String[] row = text.split(";");

        int rowNum = row.length;
        int colNum = row[0].split(",").length;

        Object[][] data = new String[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            String[] cols = row[i].split(",");

            for (int j = 0; j < colNum; j++) {
                String string = cols[j];
                data[i][j] = String.valueOf(string);
            }
        }
        return data;
    }

    private static Object[][] getDataSets(String filePath) {
        File fileWithCreds = new File(filePath);
        return readFile(fileWithCreds);
    }

    @DataProvider(name = "IncorrectDataSets")
    public static Object[][] incorrectDataSets() {
        Object[][] returnObjArray = getDataSets("src/test/resources/IncorrectDataSets.txt");
        return returnObjArray;
    }

    @DataProvider(name = "IncorrectTestUsers")
    public static Object[][] incorrectTestUsers() {
        Object[][] returnObjArray = getDataSets("src/test/resources/IncorrectTestUsers.txt");
        return returnObjArray;
    }

    @DataProvider(name = "CorrectTestUser")
    public static Object[][] correctTestUser() {
        Object[][] returnObjArray = getDataSets("src/test/resources/CorrectTestUser.txt");
        return returnObjArray;
    }


}
