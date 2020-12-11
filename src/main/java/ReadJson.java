import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadJson {

    public String getString(String jsonPath) throws IOException {
        String jsonString;

        try (BufferedReader br = new BufferedReader(new FileReader(jsonPath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            jsonString = sb.toString();
        }

        return jsonString;
    }
}
