package io.maxilog.dao;

import io.maxilog.entity.Task;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mossa on 16/10/2017.
 */
@Repository
@Transactional
public class TaskDaoImpl implements TaskDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Task> getTasks() {
        Session session = sessionFactory.getCurrentSession();
        List<Task> tasks = new ArrayList<Task>();
        Query query = session.createQuery("from io.maxilog.entity.Task");
        query.setFirstResult(0);
        query.setMaxResults(10);
        tasks = query.list();
        return tasks;
    }

    @Override
    public List<Task> getTasksByUserId(long id) {
        Session session = sessionFactory.getCurrentSession();
        List<Task> tasks = new ArrayList<Task>();
        Query query = session.createQuery("from io.maxilog.entity.Task as task join fetch task.user user where user.id = :id").setLong("id",id);
        query.setFirstResult(0);
        query.setMaxResults(10);
        tasks = query.list();
        return tasks;
    }

    @Override
    public Task getTask(int id) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class,id);
        System.out.println(task.getUser().getEmail());
        return task;
    }

    @Override
    public int createTask(Task task) {
        Session session = sessionFactory.getCurrentSession();
        return (int)session.save(task);
    }

    @Override
    public void updateTask(int id, Task task) {
        Session session = sessionFactory.getCurrentSession();
        task.setId(id);
        session.update(task);
    }

    @Override
    public void deleteTask(int id) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.byId(Task.class).load(id);
        session.delete(task);
    }
}
