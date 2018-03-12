package io.maxilog.service;

import io.maxilog.entity.Task;

import java.util.List;

/**
 * Created by mossa on 17/10/2017.
 */
public interface TaskService {

    public List<Task> findAll();
    public List<Task> findAllByUserId(Long id);
    public Task findById(int id);
    public int save(Task task);
    public void update(int id, Task task);
    public void delete(int id);
}
