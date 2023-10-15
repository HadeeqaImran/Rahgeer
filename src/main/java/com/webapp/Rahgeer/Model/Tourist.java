package com.webapp.Rahgeer.Model;

public class Tourist {
    private String username;
    private String passport;
    private Float rating;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tourist(String username, String passport, Float rating) {
        this.username = username;
        this.passport = passport;
        this.rating = rating;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
