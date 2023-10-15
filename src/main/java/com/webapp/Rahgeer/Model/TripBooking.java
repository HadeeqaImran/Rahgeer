package com.webapp.Rahgeer.Model;

public class TripBooking {
    int tripID;
    int placeID;
    String startDate;
    String endDate;
    float totalCost;

    public TripBooking(int tripID, int placeID, String startDate, String endDate, float totalCost) {
        this.tripID = tripID;
        this.placeID = placeID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
