package io.maxilog.service;

import io.maxilog.dao.ActivationDao;
import io.maxilog.dao.UserDao;
import io.maxilog.entity.Activation;
import io.maxilog.entity.User;
import io.maxilog.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by mossa on 05/11/2017.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivationDao activationDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setEnabled(false);
        return userDao.createUser(user);
    }

    @Override
    public void update(long id, User user) {
        userDao.updateUser(id,user);
    }

    @Override
    public void update(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User activation(String keyActivation) {
        Activation activation = activationDao.getActivation(keyActivation);
        User user = activation.getUser();
        user.setEnabled(true);
        userDao.updateUser(user);
        activationDao.deleteActivation(keyActivation);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
