package pablo.todo.model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.util.Objects;

public class Task {

    private final StringProperty name;
    private StringProperty content;

    public Task(String name, String content) {

        this.name = new SimpleStringProperty(name);
        this.content = new SimpleStringProperty(content);
    }

    public static Callback<Task, Observable[]> extractor() {
        return (Task task) -> new Observable[]{task.getName(), task.getContent()};
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.setValue(content);
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
