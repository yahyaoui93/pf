package io.maxilog.dao;

import io.maxilog.entity.User;

import java.util.List;

/**
 * Created by mossa on 16/10/2017.
 */

public interface UserDao {

    public List<User> getUsers();
    public User getUser(long id);
    public User getUserByEmail(String email);
    public User createUser(User user);
    public void updateUser(long id, User user);
    public void updateUser(User user);
    public void deleteUser(long id);
}
