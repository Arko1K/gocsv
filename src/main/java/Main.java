import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                URL url = new URL(Constants.API_SUGGEST_CITY + args[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                JsonNode jsonNode = new ObjectMapper().readValue(httpURLConnection.getInputStream(), JsonNode.class);

                if (jsonNode.size() > 0) {
                    List<String> csv = JsonUtils.getCsvFromJsonArray(jsonNode, Constants.JSON_CSV_MAPPING,
                            Constants.CSV_COLUMN_POSITION);
                    if (args.length > 1 && args[1].equalsIgnoreCase("createfile")) {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(args[0].toLowerCase() + ".csv"));
                        for (String row : csv) {
                            bufferedWriter.write(row);
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.close();
                    } else
                        for (String row : csv)
                            System.out.println(row);
                }
            } else
                System.out.println(Strings.MESSAGE_NOT_ENOUGH_ARGUMENTS);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}