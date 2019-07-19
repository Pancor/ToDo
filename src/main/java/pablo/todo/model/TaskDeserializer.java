package pablo.todo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TaskDeserializer extends StdDeserializer<Task> {

    public TaskDeserializer() {
        this(null);
    }

    public TaskDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        int id = node.get("id").asInt();
        String name = node.get("name").asText();
        String content = node.get("content").asText();
        return new Task(id, name, content);
    }
}
