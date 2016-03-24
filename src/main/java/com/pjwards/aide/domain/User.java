package com.pjwards.aide.domain;

import java.util.Date;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date createdDate;
    private Date lastDate;
    private String company;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public User setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public User setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public User setLastDate(Date lastDate) {
        this.lastDate = lastDate;
        return this;
    }
}
