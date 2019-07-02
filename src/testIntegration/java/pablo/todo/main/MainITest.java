package pablo.todo.main;

import com.nowatel.javafxspring.GuiTest;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.ListViewMatchers;
import pablo.todo.data.TasksRepository;
import pablo.todo.main.MainController;
import pablo.todo.main.MainView;
import pablo.todo.model.Task;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainITest extends GuiTest {

    @Autowired
    private MainView mainView;

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeClass
    public static void setupTestEnvironment() {
        String headlessProperty = System.getProperty("javaFX_headless", "false");
        Boolean headless = Boolean.valueOf(headlessProperty);
        if (headless) {
            System.out.println("HEADLESS tests");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
        } else {
            System.setProperty("testfx.headless", "false");
        }
    }

    @PostConstruct
    public void initView() throws Exception {
        init(mainView);
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
        ListView<Task> tasksView = lookup("#tasksView").query();
        TextField newTaskView = lookup("#newTaskView").query();
        Task newTask = new Task("New task", "");

        newTaskView.setText(newTask.getName());
        clickOn("#addBtn");
        int itemsCount = tasksView.getItems().size();

        assertThat("TasksView does not contain new task", tasksView, ListViewMatchers.hasListCell(newTask));
        assertEquals("TasksView should contain 4 elements given: " + itemsCount, 4, itemsCount);
    }

    @Test
    public void deleteTaskThenVerifyIfTaskWasDeleted() {
        ListView<Task> tasksView = lookup("#tasksView").query();
        Task task = new Task("Task 1", "");

        clickOn("Task 1");
        clickOn("#deleteBtn");
        boolean isTaskDeleted = !tasksView.getItems().contains(task);
        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 2 elements given: " + itemsCount, 2, itemsCount);
        assertTrue("Task 1 was not deleted", isTaskDeleted);
    }

    @Test
    public void contentViewShouldContainTaskNameWhenTasksViewElementIsSelected() {
        ListView<Task> tasksView = lookup("#tasksView").query();
        TextArea contentView = lookup("#contentView").query();

        clickOn("Task 1");

        String expected = tasksView.getItems().get(0).getContent();
        String actual = contentView.getText();
        assertEquals("After selecting tasksView item, contentView should show: " + expected + " but showed: " + actual, expected, actual);
    }

  //  @Test
    public void tryToDeleteItemWithoutSelectingItThenShowAlertDialog() {
        ListView<Task> tasksView = lookup("#tasksView").query();

        tasksView.getSelectionModel().clearSelection();
        clickOn("#deleteBtn");
        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 3 elements given: " + itemsCount, 3, itemsCount);
    }

    @Test
    public void tryToAddItemWithoutSpecifyingNameThenShowAlertDialog() {
        ListView<Task> tasksView = lookup("#tasksView").query();
        TextField newTaskView = lookup("#newTaskView").query();

        newTaskView.clear();
        clickOn("#addBtn");
        int itemsCount = tasksView.getItems().size();

        assertEquals("TasksView should contain 3 elements given: " + itemsCount, 3, itemsCount);
    }

    @Test
    public void updateTaskContentThenCheckIfChangesWereSaved() {
        String updatedContent = "Task 1 was updated.";
        TextArea contentView = lookup("#contentView").query();

        clickOn("Task 1");
        contentView.setText(updatedContent);
        clickOn("#saveBtn");
        clickOn("Task 2");
        clickOn("Task 1");

        assertEquals("Updated content of Task 1 was not saved", updatedContent, contentView.getText());
    }

    @After
    public void tearDown() throws TimeoutException {
        tasksRepository.cleanUp();
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }
}
