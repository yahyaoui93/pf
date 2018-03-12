package io.maxilog.dao;

import io.maxilog.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mossa on 05/11/2017.
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from io.maxilog.entity.User as u where u.email = :email").setString("email", email);
        User user = (User)query.uniqueResult();
        return user;
    }

    @Override
    public User createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        User u = user;
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        long id = (long)session.save(u);
        return session.get(User.class, id);
    }

    @Override
    public void updateUser(long id, User user) {
        Session session = sessionFactory.getCurrentSession();
        User u = user;
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setId(id);
        session.update(u);
    }

    @Override
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        session.update(user);
    }

    @Override
    public void deleteUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.byId(User.class).load(id);
        session.delete(user);
    }
}
