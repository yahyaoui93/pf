package io.maxilog.service;

import io.maxilog.entity.User;

/**
 * Created by mossa on 05/11/2017.
 */
public interface UserService {

    public User findByEmail(String email);
    public User save(User user);
    public void update(long id, User user);
    public void update(User user);
    public User activation(String keyActivation);
}
