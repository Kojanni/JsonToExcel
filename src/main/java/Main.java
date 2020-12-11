import com.google.gson.Gson;
import dadata.CreatorExcel;
import dadata.Dadata;
import dadata.Suggestions;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static final String JSONDIR = "data/данные.txt";

    public static void main(String[] args) throws IOException {

        String json = (new ReadJson()).getString(JSONDIR);

        Gson gson = new Gson();
        Suggestions dadataList = gson.fromJson(json, Suggestions.class);

        CreatorExcel creatorExcel = new CreatorExcel();
        creatorExcel.createExcelFromDadata(dadataList);
    }

}
