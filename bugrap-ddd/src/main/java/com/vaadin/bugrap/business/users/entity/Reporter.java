package com.vaadin.bugrap.business.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.vaadin.bugrap.business.AbstractEntity;

@Entity
public class Reporter extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String username;
    public static String PROPERTY_USERNAME = "username";

    private String name;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }
}
