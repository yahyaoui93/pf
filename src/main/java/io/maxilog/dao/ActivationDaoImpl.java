package io.maxilog.dao;

import io.maxilog.entity.Activation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mossa on 01/12/2017.
 */
@Repository
@Transactional
public class ActivationDaoImpl implements ActivationDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Activation getActivation(String keyActivation) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Activation.class, keyActivation);
    }

    @Override
    public String createActivation(Activation activation) {
        Session session = sessionFactory.getCurrentSession();
        return (String)session.save(activation);
    }

    @Override
    public void deleteActivation(String keyActivation) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.byId(Activation.class).load(keyActivation));
    }
}
