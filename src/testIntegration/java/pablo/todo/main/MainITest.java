package pablo.todo.main;

import com.nowatel.javafxspring.GuiTest;
import javafx.application.Platform;
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextFlow;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.AbstractListViewAssert;
import org.testfx.assertions.api.ListViewAssert;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TextFlowMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.service.query.NodeQuery;
import org.xmlunit.diff.NodeMatcher;
import pablo.todo.data.TasksRepository;
import pablo.todo.model.Task;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;
import static org.testfx.assertions.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainITest extends GuiTest {

    @Autowired
    private TasksRepository tasksRepository;

    @PostConstruct
    public void initView() throws Exception {
        init(MainView.class);
    }

    @Before
    public void setupTasks() {
        CompletableFuture.runAsync(() -> {
            if (tasksRepository.getTasks().size() == 0)
                tasksRepository.createTasks();
             }, Platform::runLater).join();
    }

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

    @Test
    public void saveBtnShouldContainText() {
        verifyThat("#saveBtn", hasText("Save changes"));
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
        verifyThat("#tasksView", ListViewMatchers.hasItems(3));
    }

    @Test
    public void insertNewTaskThenVerifyIfNewTaskIsAdded() {
        Task newTask = new Task("New task", "");

        clickOn("#newTaskView").write(newTask.getName().get());
        clickOn("#addBtn");

        verifyThat("#tasksView", ListViewMatchers.hasItems(4));
        verifyThat("#tasksView", ListViewMatchers.hasListCell(newTask));
    }

    @Test
    public void deleteTaskThenVerifyIfTaskWasDeleted() {
        Task taskToDelete = new Task("Task 1", "");

        clickOn("Task 1");
        clickOn("#deleteBtn");

        verifyThat("#tasksView", ListViewMatchers.hasItems(2));
        verifyThat("#tasksView", not(ListViewMatchers.hasListCell(taskToDelete)));
    }

    @Test
    public void contentViewShouldContainTaskNameWhenTasksViewElementIsSelected() {
        clickOn("Task 1");

        verifyThat("#contentView", TextInputControlMatchers.hasText("Content 1"));
    }

    @Test
    public void tryToDeleteItemWithoutSelectingItThenShowAlertDialog() {
        clickOn("#deleteBtn");

        verifyThat("#tasksView", ListViewMatchers.hasItems(3));
    }

    @Test
    public void tryToAddItemWithoutSpecifyingNameThenShowAlertDialog(){
        clickOn("#addBtn");

        verifyThat("#tasksView", ListViewMatchers.hasItems(3));
    }

    @Test
    public void updateTaskContentThenCheckIfChangesWereSaved() throws InterruptedException {
        String updatedContent = " was updated.";
        String expectedContent = "Content 1" + updatedContent;

        clickOn("Task 1");
        clickOn("#contentView").write(updatedContent);
        clickOn("#saveBtn");
        clickOn("Task 2");
        clickOn("Task 1");

        verifyThat("#contentView", TextInputControlMatchers.hasText(expectedContent));
    }

    @After
    public void tearDown() throws TimeoutException {
        CompletableFuture.runAsync(() -> tasksRepository.cleanUp(), Platform::runLater).join();
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }
}
