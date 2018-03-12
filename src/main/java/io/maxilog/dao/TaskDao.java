package io.maxilog.dao;

import io.maxilog.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mossa on 16/10/2017.
 */

public interface TaskDao {

    public List<Task> getTasks();
    public List<Task> getTasksByUserId(long id);
    public Task getTask(int id);
    public int createTask(Task task);
    public void updateTask(int id, Task task);
    public void deleteTask(int id);
}
