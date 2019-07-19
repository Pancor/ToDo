package pablo.todo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TaskSerializer extends StdSerializer<Task> {

    public TaskSerializer() {
        this(null);
    }

    public TaskSerializer(Class<Task> t) {
        super(t);
    }

    @Override
    public void serialize(Task task, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", task.getName().get());
        gen.writeStringField("content", task.getContent());
        gen.writeEndObject();
    }
}
