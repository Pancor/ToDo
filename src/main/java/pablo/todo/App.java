package pablo.todo;

import com.nowatel.javafxspring.JavaFXApplication;
import com.nowatel.javafxspring.NowatelSplashScreen;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import pablo.todo.main.MainView;

@Component
public class App extends JavaFXApplication {

    @Override
    protected void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
        super.beforeInitialView(stage, ctx);
        stage.setTitle("TODO");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    static void run(String[] args) {
        launch(Main.class, App.class, MainView.class, new NowatelSplashScreen() ,args);
    }
}
