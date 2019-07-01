package pablo.todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        ToDoPane() throws IOException {
            super();
            setupSceneFromFXML();
        }

        private void setupSceneFromFXML() throws IOException {
            System.out.println(getClass().toString());
            FXMLLoader loader = new FXMLLoader();
            getChildren().add(loader.load(new FileInputStream("/home/pawel/projekty/ToDo/src/main/resources/app_view.fxml")));
        }
    }

    static void run(String[] args) {
        launch(args);
    }
    }
