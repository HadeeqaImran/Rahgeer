package com.webapp.Rahgeer.Model;

public class Host {
    private String username;
    private String cnic;
    private Float rating;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Host(String username, String cnic, Float rating) {
        this.username = username;
        this.cnic = cnic;
        this.rating = rating;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
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
