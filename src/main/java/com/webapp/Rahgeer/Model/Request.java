package com.webapp.Rahgeer.Model;

import java.time.LocalDateTime;

public class Request {
    private String createdBy;
    private String city;
    private String description;
    private String createdOn;
    private String acceptedBy;
    private String acceptedOn;
    private Host host;
    private Tourist tourist;

    public Request(String createdBy, String city, String description, String createdOn) {
        this.createdBy = createdBy;
        this.city = city;
        this.description = description;
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getAcceptedOn() {
        return acceptedOn;
    }

    public void setAcceptedOn(String acceptedOn) {
        this.acceptedOn = acceptedOn;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    /*
    public String getCreadtedOnTimeStamp(){
        // format YYYY-MM-DD HH:MM:SS
        return createdOn.getYear() + "-" + createdOn.getMonthValue() + "-" + createdOn.getDayOfMonth() + " " + createdOn.getHour() + ":" + createdOn.getMinute() + ":" + createdOn.getSecond();
    }

    public String getAcceptedOnTimeStamp(){
        // format YYYY-MM-DD HH:MM:SS
        return acceptedOn.getYear() + "-" + acceptedOn.getMonthValue() + "-" + acceptedOn.getDayOfMonth() + " " + acceptedOn.getHour() + ":" + acceptedOn.getMinute() + ":" + acceptedOn.getSecond();
    }
    */
}
