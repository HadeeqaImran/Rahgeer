package com.webapp.Rahgeer.Model;

public class Admin {

    private String username;
    private User user;
    public Admin(String username) {
        this.username = username;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
