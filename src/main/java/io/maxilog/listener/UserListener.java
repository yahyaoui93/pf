package io.maxilog.listener;

import io.maxilog.config.BeanUtil;
import io.maxilog.dao.ActivationDao;
import io.maxilog.entity.Activation;
import io.maxilog.entity.User;
import io.maxilog.service.NotificationService;
import io.maxilog.utils.RandomString;
import org.dom4j.tree.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

/**
 * Created by mossa on 27/11/2017.
 */
public class UserListener {

    @PostPersist
    public void postPersist(final User user) throws MessagingException {
        NotificationService notificationService = BeanUtil.getBean(NotificationService.class);
        ActivationDao activationDao = BeanUtil.getBean(ActivationDao.class);
        RandomString randomString = new RandomString(50);
        Activation activation = new Activation(randomString.nextString(),user);
        activationDao.createActivation(activation);
        //notificationService.sendActivationEmail(user,activation);
        System.out.println("postPersist");
    }

    @PrePersist
    public void prePersist(final User user) {
        System.out.println("prePersist");
    }

    @PostUpdate
    public void postUpdate(final User user) {
        System.out.println("postUpdate");
    }

    @PostLoad
    public void postLoad(final User user) {
        System.out.println("postLoad");
    }
}