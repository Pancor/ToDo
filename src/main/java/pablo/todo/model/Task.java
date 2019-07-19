package pablo.todo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.util.Objects;

@JsonDeserialize(using = TaskDeserializer.class)
@JsonSerialize(using = TaskSerializer.class)
public class Task {

    private int id;
    private final StringProperty name = new SimpleStringProperty();
    private StringProperty content = new SimpleStringProperty();

    public Task() {
        super();
    }

    public Task(String name) {
        this(0, name, "");
    }

    public Task(String name, String content) {
        this(0, name, content);
    }

    public Task(int id, String name, String content) {
        this.id = id;
        this.name.setValue(name);
        this.content.setValue(content);
    }

    public static Callback<Task, Observable[]> extractor() {
        return (Task task) -> new Observable[]{task.getName(), task.contentProperty()};
    }

    public int getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task that = (Task) obj;
        return Objects.equals(name.get(), that.name.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.get());
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name.get() + '\'' +
                ", content='" + content.get() + '\'' +
                '}';
    }
}
