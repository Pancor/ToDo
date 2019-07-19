package pablo.todo.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pablo.todo.model.Result;
import pablo.todo.model.Task;
import pablo.todo.utils.RequestResponseLoggingInterceptor;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
@Profile("remote")
public class RemoteTasksRepositoryImpl implements TasksRepository {

    private RestTemplate restTemplate;
    private ObservableList<Task> tasks;

    public RemoteTasksRepositoryImpl() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        restTemplate = new RestTemplate(factory);

        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
    }

    @Override
    public void insertTask(Task task) {
        HttpHeaders header = getAuthorizationHeaderForUser("admin", "@dmin");
        HttpEntity entity = null;
        try {
            entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(task), header);
        } catch (JsonProcessingException e) { e.printStackTrace(); }
        ResponseEntity<Result> result = restTemplate.exchange("http://127.0.0.1:8093/api/task", HttpMethod.POST, entity, Result.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            tasks.add(result.getBody().getResult().get(0));
        }
    }

    @Override
    public void updateTask(int index, Task task) {
        HttpHeaders header = getAuthorizationHeaderForUser("admin", "@dmin");
        HttpEntity entity = new HttpEntity<>(task.contentProperty().get(), header);
        ResponseEntity<?> result = restTemplate.exchange("http://127.0.0.1:8093/api/task/" + tasks.get(0).getId(), HttpMethod.POST, entity, Result.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            tasks.set(index, task);
        }
    }

    @Override
    public void deleteTask(int index) {
        HttpHeaders header = getAuthorizationHeaderForUser("admin", "@dmin");
        HttpEntity entity = new HttpEntity<>("", header);
        ResponseEntity<?> result = restTemplate.exchange("http://127.0.0.1:8093/api/task/" + tasks.get(index).getId(), HttpMethod.DELETE, entity, Object.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            tasks.remove(index);
        }
    }

    @Override
    public void createTasks() {

    }

    @Override
    public ObservableList<Task> getTasks() {
        HttpHeaders header = getAuthorizationHeaderForUser("admin", "@dmin");
        HttpEntity entity = new HttpEntity<>("", header);
        ResponseEntity<Result> result = restTemplate.exchange("http://127.0.0.1:8093/api/tasks", HttpMethod.GET, entity, Result.class);
        List<Task> resultTasks = result.getBody().getResult();
        tasks = FXCollections.observableArrayList(resultTasks);
        return tasks;
    }

    @Override
    public void cleanUp() {

    }

    private HttpHeaders getAuthorizationHeaderForUser(String username, String plainPassword) {
        String plainCredentials = username + ":" + plainPassword;
        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}
