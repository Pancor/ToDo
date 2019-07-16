package pablo.todo.main;

import com.nowatel.javafxspring.FXMLController;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import pablo.todo.data.TasksRepository;
import pablo.todo.model.Task;

@FXMLController
public class MainController {

    @Autowired
    private TasksRepository tasksRepository;

    @FXML private Button saveBtn;
    @FXML private TextArea contentView;
    @FXML private ListView<Task> tasksView;
    @FXML private Button deleteBtn;
    @FXML private TextField newTaskView;
    @FXML private Button addBtn;

    public MainController() { }

    @FXML
    void initialize() {
        tasksRepository.createTasks();
        tasksView.setItems(tasksRepository.getTasks());
        tasksView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(item.getName().get());
                }
            }
        });
        //contentView.textProperty().bind(Bindings.createStringBinding(() -> tasksView.getSelectionModel().getSelectedItem().getContent(), tasksView.getSelectionModel().selectedItemProperty()));
        tasksView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                contentView.setText(newValue.getContent());
        });
    }

    @FXML
    private void addTask() {
        if (!newTaskView.getText().isEmpty()) {
            tasksRepository.insertTask(new Task(newTaskView.getText(), ""));
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
            tasksRepository.deleteTask(index);
            contentView.clear();
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
        if (updatedContent != null) {
            task.setContent(updatedContent);
            tasksRepository.updateTask(taskIndex, task);
        }
    }
}
