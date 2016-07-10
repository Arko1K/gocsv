import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

    public static final String API_SUGGEST_CITY = "http://api.goeuro.com/api/v2/position/suggest/en/";
    public static HashMap<String, String> JSON_CSV_MAPPING = new LinkedHashMap<>();
    public static HashMap<String, Integer> CSV_COLUMN_POSITION = new HashMap<>();

    static {
        // Set all required mappings here; columns will appear in the respective order
        // CSV column names can be different from their corresponding JSON field names
        JSON_CSV_MAPPING.put("_id", "_id");
        JSON_CSV_MAPPING.put("name", "name");
        JSON_CSV_MAPPING.put("type", "type");
        JSON_CSV_MAPPING.put("name", "name");
        JSON_CSV_MAPPING.put("geo_position.latitude", "latitude");
        JSON_CSV_MAPPING.put("geo_position.longitude", "longitude");

        int i = 0;
        for (Map.Entry<String, String> entry : JSON_CSV_MAPPING.entrySet())
            CSV_COLUMN_POSITION.put(entry.getValue(), i++);
    }
}