package com.yaro.jwtconsumer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "User_Data")
public class UserDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "Login")
    private String login;
    @Column(name = "Mobile_Phone")
    private String mobilePhone;
    @Column(name = "Balance")
    private Double balance;
    @Column(name = "Name")
    private String name;
    public UserDataEntity() {
    }

    public UserDataEntity(long id, String login, String mobilePhone, Double balance, String name) {
        this.id = id;
        this.login = login;
        this.mobilePhone = mobilePhone;
        this.balance = balance;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

