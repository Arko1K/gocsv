import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Use this class to pass a set of JSON keys to be observed and implement an observer to use the corresponding values of these keys.
 */
public class JsonReader {

    interface NodeObserver {
        void useField(String fieldName, JsonNode fieldValue);
    }

    private HashSet<String> observables;
    private NodeObserver nodeObserver;


    public JsonReader(HashSet<String> observables, NodeObserver nodeObserver) {
        this.observables = observables;
        this.nodeObserver = nodeObserver;
    }


    public void read(JsonNode jsonNode) {
        read("", jsonNode);
    }

    // Recursively iterates over the JSON node and calls the handler whenever an observable field is found
    private void read(String prefix, JsonNode jsonNode) {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String currentFieldName = prefix + field.getKey();
            if (observables.contains(currentFieldName))
                nodeObserver.useField(currentFieldName, field.getValue());
            read(currentFieldName + ".", field.getValue());
        }
    }
}