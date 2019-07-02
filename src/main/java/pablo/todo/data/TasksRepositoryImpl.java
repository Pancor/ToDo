package pablo.todo.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import pablo.todo.model.Task;

import java.util.List;

@Service
public class TasksRepositoryImpl implements TasksRepository {

    private ObservableList<Task> tasks;

    public TasksRepositoryImpl() {
        initTasks();
    }

    @Override
    public void insertTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void updateTask(int index, Task task) {
        tasks.set(index, task);
    }

    @Override
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    @Override
    public ObservableList<Task> getTasks() {
        return tasks;
    }

    @Override
    public void cleanUp() {
        initTasks();
    }

    private void initTasks() {
        tasks = FXCollections.observableArrayList(
                new Task("Task 1", "Content 1"),
                new Task("Task 2", "Content 2"),
                new Task("Task 3", "Content 3"));
    }
}
