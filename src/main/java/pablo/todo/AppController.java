package pablo.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class AppController {

    @FXML private TextArea contentView;
    @FXML private ListView tasksView;
    @FXML private Button deleteBtn;
    @FXML private TextField newTaskView;
    @FXML private Button addBtn;

    private ObservableList<String> tasks = FXCollections.observableArrayList(
            "Task 1", "Task 2", "Task 3"
    );

    public AppController() { }

    @FXML
    void initialize() {
        tasksView.setItems(tasks);
    }

    @FXML
    private void addTask(ActionEvent actionEvent) {
        if (!newTaskView.getText().isEmpty()) {
            tasks.add(newTaskView.getText());
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
    private void setContentViewText(ActionEvent actionEvent) {
        String text = (String) tasksView.getSelectionModel().getSelectedItem();
        contentView.setText(text);
    }
}
