package com.yaro.jwtbackend.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "User_Credentials")
public class CredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "Login")
    private String login;
    @Column(name = "Password")
    private String password;
    @Column(name = "Name")
    private String name;
    @Column(name = "Role")
    private String role;
    public CredentialsEntity() {
    }
    public CredentialsEntity(long id, String login, String password, String name, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return login;
    }

    public void setUser(String user) {
        this.login = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialsEntity that = (CredentialsEntity) o;
        return id == that.id && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, role);
    }
}
