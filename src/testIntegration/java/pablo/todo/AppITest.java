package pablo.todo;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class AppITest extends ApplicationTest {



    @Override
    public void start(Stage stage){
        Parent root = new App.ToDoPane();
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Test
    public void addBtnShouldContainText() {
        verifyThat("#addBtn", hasText("Add task"));
    }

    @Test
    public void deleteBtnShouldContainText() {
        verifyThat("#deleteBtn", hasText("Delete task"));
    }

    @Test
    public void tasksListShouldContainThreeTasks() {
        ListView<String> tasksView = lookup("#tasksView").query();

        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 3 elements given: " + itemsCount, 3, itemsCount);
    }

    @Test
    public void insertNewTaskThenVerifyIfNewTaskIsAdded() {
        ListView<String> tasksView = lookup("#tasksView").query();
        TextArea newTaskView = lookup("#newTaskView").query();

        newTaskView.setText("Test");
        clickOn("#addBtn");
        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 4 elements given: " + itemsCount, 4, itemsCount);
    }

    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }
}
