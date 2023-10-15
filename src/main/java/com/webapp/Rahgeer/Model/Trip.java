package com.webapp.Rahgeer.Model;

public class Trip {
    int tripID;
    String tripBy;
    String source;
    String destination;
    String startDate;
    String endDate;
    boolean isRated;

    TripCost tripCost;

    public Trip()
    {
        tripID = 0;
        tripBy = "";
        source = "";
        destination = "";
        startDate = "";
        endDate = "";
        isRated = false;
    }

    public Trip(int tripID, String tripBy, String source, String destination, String startDate, String endDate, boolean isRated) {
        this.tripID = tripID;
        this.tripBy = tripBy;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRated = isRated;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getTripBy() {
        return tripBy;
    }

    public void setTripBy(String tripBy) {
        this.tripBy = tripBy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }

    public TripCost getTripCost() {
        return tripCost;
    }

    public void setTripCost(TripCost tripCost) {
        this.tripCost = tripCost;
    }
}
