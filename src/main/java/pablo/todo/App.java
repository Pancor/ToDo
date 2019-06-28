package pablo.todo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
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

        ToDoPane() throws IOException {
            super();
            setupSceneFromFXML();
        }

        private void setupSceneFromFXML() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            getChildren().add(loader.load(new FileInputStream("/home/pawel/projekty/ToDo/src/main/resources/app_view.fxml")));
        }
    }

    static void run(String[] args) {
        launch(args);
    }
    }
