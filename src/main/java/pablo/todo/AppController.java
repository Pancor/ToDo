package pablo.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import pablo.todo.model.Task;


public class AppController {

    @FXML private TextArea contentView;
    @FXML private ListView tasksView;
    @FXML private Button deleteBtn;
    @FXML private TextField newTaskView;
    @FXML private Button addBtn;

    private ObservableList<Task> tasks = FXCollections.observableArrayList(
            new Task("Task 1", "Content 1"),
            new Task("Task 2", "Content 2"),
            new Task("Task 3", "Content 3")
    );

    public AppController() { }

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
    private void addTask(ActionEvent actionEvent) {
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
    private void removeTask(ActionEvent actionEvent) {
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
    private void setContentViewText(MouseEvent arg0) {
        Task task = (Task) tasksView.getSelectionModel().getSelectedItem();
        contentView.setText(task.getContent());
    }
}
