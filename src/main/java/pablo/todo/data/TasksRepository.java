package pablo.todo.data;

import javafx.collections.ObservableList;
import pablo.todo.model.Task;

import java.util.List;

public interface TasksRepository {

    void insertTask(Task task);

    void updateTask(int index, Task task);

    void deleteTask(int index);

    void createTasks();

    ObservableList<Task> getTasks();

    void cleanUp();
}
