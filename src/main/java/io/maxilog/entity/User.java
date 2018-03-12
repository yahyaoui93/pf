package io.maxilog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.maxilog.listener.UserListener;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by mossa on 05/11/2017.
 */
@Entity
@EntityListeners(value = UserListener.class)
@Table(name="User")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Access(javax.persistence.AccessType.PROPERTY)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotBlank
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotBlank
    @Size(min = 8)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @NotBlank
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @NotBlank
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotBlank
    @Email
    private String email;

    @Column(name = "PHOTO",columnDefinition="varchar(255) default 'profile.png'")
    @NotNull
    private  String photo = "profile.png";

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @JsonIgnore
    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @Column(name = "ROLE")
    @NotNull
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;


    public User() {
        enabled = true;
        lastPasswordResetDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
