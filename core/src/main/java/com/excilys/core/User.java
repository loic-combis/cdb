package com.excilys.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    /**
     * Getter.
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter.
     *
     * @param login String
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter.
     *
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Setter.
     *
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }

    public static User ofCreateForm(CreateUserFormData userForm) {
        User user = new User();
        user.setLogin(userForm.getLogin().trim());
        user.setPassword(userForm.getPassword().trim());
        user.setRole(ROLE_USER);
        return user;
    }
}
