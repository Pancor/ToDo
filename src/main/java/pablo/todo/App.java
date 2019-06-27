package pablo.todo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ToDo");

        ListView<String> tasksView = new ListView<>();
        ObservableList<String> tasks = FXCollections.observableArrayList(
                "Task 1", "Task 2", "Task 3"
        );
        tasksView.setItems(tasks);
        tasksView.setPrefHeight(100);
        tasksView.setPrefWidth(60);

        StackPane root = new StackPane();
        root.getChildren().add(tasksView);
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }

    static void run(String[] args) {
        launch(args);
    }
}
