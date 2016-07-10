import com.fasterxml.jackson.databind.JsonNode;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class JsonUtils {

    public static List<String> getCsvFromJsonArray(JsonNode jsonArray, final HashMap<String, String> mapping,
                                                   final HashMap<String, Integer> columnPosition)
            throws InvalidParameterException {
        if (jsonArray.isArray()) {
            List<String> csv = new ArrayList<>();
            csv.add(String.join(",", mapping.values())); // Header
            HashSet<String> register = new HashSet<>(mapping.keySet());
            for (JsonNode jsonNode : jsonArray) {
                final String[] rowElements = new String[mapping.size()];

                // Each JSON node is passed to an instance of JsonReader and the list of elements in the corresponding
                // CSV row is updated using a handler
                new JsonReader(register, (String fieldName, JsonNode fieldValue) -> {
                    // Using lambda expression to create anonymous class to implement the JsonReader.NodeHandler interface.
                    rowElements[columnPosition.get(mapping.get(fieldName))] = fieldValue.asText();
                }).read(jsonNode);

                csv.add(String.join(",", rowElements));
            }
            return csv;
        } else
            throw new InvalidParameterException(Strings.MESSAGE_NOT_JSON_ARRAY);
    }
}