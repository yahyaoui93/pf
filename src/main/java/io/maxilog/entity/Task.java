package io.maxilog.entity;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="Task")
public class Task {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(unique=true)
    private String name;
    private String detail;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Task() {
        date = new Date();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "user_id",  insertable = false, updatable = false)
    @JsonProperty("user_id")
    public long getUserId() {
        return this.user.getId();
    }
}

