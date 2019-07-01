package pablo.todo.main;

import com.nowatel.javafxspring.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pablo.todo.model.Task;

@FXMLController
public class MainController {

    @FXML private Button saveBtn;
    @FXML private TextArea contentView;
    @FXML private ListView<Task> tasksView;
    @FXML private Button deleteBtn;
    @FXML private TextField newTaskView;
    @FXML private Button addBtn;

    private ObservableList<Task> tasks = FXCollections.observableArrayList(
            new Task("Task 1", "Content 1"),
            new Task("Task 2", "Content 2"),
            new Task("Task 3", "Content 3")
    );

    public MainController() { }

    @FXML
    void initialize() {
        tasksView.setItems(tasks);
        tasksView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    @FXML
    private void addTask() {
        if (!newTaskView.getText().isEmpty()) {
            tasks.add(new Task(newTaskView.getText(), ""));
            newTaskView.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty task");
            alert.setHeaderText("Error");
            alert.setContentText("You can't add task without text.");
            alert.showAndWait();
        }
    }

    @FXML
    private void removeTask() {
        int index = tasksView.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            tasksView.getItems().remove(index);
            contentView.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select task");
            alert.setHeaderText("Error");
            alert.setContentText("You have to select task to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void saveChanges() {
        int taskIndex = tasksView.getSelectionModel().getSelectedIndex();
        Task task = tasksView.getSelectionModel().getSelectedItem();
        String updatedContent = contentView.getText();
        task.setContent(updatedContent);
        tasks.set(taskIndex, task);
    }

    @FXML
    private void setContentViewText() {
        Task task = tasksView.getSelectionModel().getSelectedItem();
        contentView.setText(task.getContent());
    }
}
