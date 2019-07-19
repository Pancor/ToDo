package pablo.todo.model;

import java.util.List;

public class Result {

    private List<Task> result;

    public Result() {
    }

    public Result(List<Task> result) {
        this.result = result;
    }

    public List<Task> getResult() {
        return result;
    }
}
