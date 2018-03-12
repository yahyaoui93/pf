package io.maxilog.entity;

import javax.persistence.*;

/**
 * Created by mossa on 01/12/2017.
 */
@Entity
@Table(name = "Activation")
public class Activation {

    @Id
    private String keyActivation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Activation() {}

    public Activation(String keyActivation, User user) {
        this.keyActivation = keyActivation;
        this.user = user;
    }

    public String getKeyActivation() {
        return keyActivation;
    }

    public void setKeyActivation(String keyActivation) {
        this.keyActivation = keyActivation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
