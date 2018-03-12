package io.maxilog.service;

import io.maxilog.dao.TaskDao;
import io.maxilog.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mossa on 17/10/2017.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDao taskDao;

    @Override
    public List<Task> findAll() {
        return taskDao.getTasks();
    }

    @Override
    public List<Task> findAllByUserId(Long id) {
        return taskDao.getTasksByUserId(id);
    }

    @Override
    public Task findById(int id) {
        return taskDao.getTask(id);
    }

    @Override
    public int save(Task task) {
        return taskDao.createTask(task);
    }

    @Override
    public void update(int id, Task task) {
        taskDao.updateTask(id,task);
    }

    @Override
    public void delete(int id) {
        taskDao.deleteTask(id);
    }
}
