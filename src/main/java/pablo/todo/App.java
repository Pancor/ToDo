package pablo.todo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ToDo");

        Parent root = new ToDoPane();
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static class ToDoPane extends StackPane {

        private ListView<String> tasksView = new ListView<>();
        private TextField contentView = new TextField();
        private ObservableList<String> tasks = FXCollections.observableArrayList(
                "Task 1", "Task 2", "Task 3"
        );

        ToDoPane() {
            super();
            setupScene();
        }

        private void setupScene() {
            BorderPane listPane = new BorderPane();

            tasksView.setId("tasksView");
            contentView.setId("contentView");
            tasksView.setItems(tasks);
            tasksView.setOnMouseClicked(event -> {
                String text = tasksView.getSelectionModel().getSelectedItem();
                contentView.setText(text);

            });
            listPane.setLeft(tasksView);
            listPane.setCenter(contentView);

            setupRightPane(listPane);

            getChildren().add(listPane);
        }

        private void setupRightPane(BorderPane root) {
            HBox rightPane = new HBox(2);

            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            spacer.setMinSize(100, 1);

            TextArea newTaskView = new TextArea();
            newTaskView.setId("newTaskView");
            newTaskView.setPrefHeight(50);
            newTaskView.setPrefWidth(50);

            Button addBtn = new Button();
            addBtn.setId("addBtn");
            addBtn.setText("Add task");
            addBtn.setOnAction(event -> {
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
            });

            Button deleteBtn = new Button();
            deleteBtn.setId("deleteBtn");
            deleteBtn.setText("Delete task");
            deleteBtn.setOnAction(event -> {
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
            });

            rightPane.getChildren().addAll(spacer, newTaskView, addBtn, deleteBtn);
            root.setRight(rightPane);
        }
    }

    static void run(String[] args) {
        launch(args);
    }
    }
