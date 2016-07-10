import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class JsonReader {

    interface NodeHandler {
        void use(String fieldName, JsonNode fieldValue);
    }

    private HashSet<String> register;
    private NodeHandler nodeHandler;


    public JsonReader(HashSet<String> register, NodeHandler nodeHandler) {
        this.register = register;
        this.nodeHandler = nodeHandler;
    }


    public void read(JsonNode jsonNode) {
        read("", jsonNode);
    }

    private void read(String prefix, JsonNode jsonNode) {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String currentFieldName = prefix + field.getKey();
            if (register.contains(currentFieldName))
                nodeHandler.use(currentFieldName, field.getValue());
            read(currentFieldName + ".", field.getValue());
        }
    }
}