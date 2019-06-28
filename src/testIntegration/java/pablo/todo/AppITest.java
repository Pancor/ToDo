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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class AppITest extends ApplicationTest {



    @Override
    public void start(Stage stage) throws IOException {
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
        verifyThat("#deleteBtn", hasText("Remove task"));
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
        TextField newTaskView = lookup("#newTaskView").query();
        String newTask = "New task";

        newTaskView.setText(newTask);
        clickOn("#addBtn");
        boolean isNewTaskCorrectlyAdded = tasksView.getItems().contains(newTask);
        int itemsCount = tasksView.getItems().size();

        assertTrue("Task: " + newTask + "was not added correctly", isNewTaskCorrectlyAdded);
        assertEquals("TasksView should contain 4 elements given: " + itemsCount, 4, itemsCount);
    }

    @Test
    public void deleteTaskThenVerifyIfTaskWasDeleted() {
        ListView<String> tasksView = lookup("#tasksView").query();

        tasksView.getSelectionModel().selectFirst();
        clickOn("#deleteBtn");
        boolean isTask1Deleted = !tasksView.getItems().contains("Task 1");
        int itemsCount = tasksView.getItems().size();

        assertTrue("Task 1 was not deleted", isTask1Deleted);
        assertEquals("TasksView should contain 2 elements given: " + itemsCount, 2, itemsCount);
    }

    @Test
    public void contentViewShouldContainTaskNameWhenTasksViewElementIsSelected() {
        ListView<String> tasksView = lookup("#tasksView").query();
        TextArea contentView = lookup("#contentView").query();

        clickOn("Task 1");

        String expected = tasksView.getItems().get(0);
        String actual = contentView.getText();
        assertEquals("After selecting tasksView item, contentView should show: " + expected + " but showed: " + actual, expected, actual);
    }

    @Test
    public void tryToDeleteItemWithoutSelectingItThenShowAlertDialog() {
        ListView<String> tasksView = lookup("#tasksView").query();

        clickOn("#deleteBtn");
        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 3 elements given: " + itemsCount, 3, itemsCount);
    }

    @Test
    public void tryToAddItemWithoutSpecifyingNameThenShowAlertDialog() {
        ListView<String> tasksView = lookup("#tasksView").query();

        clickOn("#addBtn");
        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 3 elements given: " + itemsCount, 3, itemsCount);
    }

    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }
}
