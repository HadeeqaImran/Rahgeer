package com.webapp.Rahgeer.Model;

public class TripCost {
    private int tripID;
    private int placeID;
    private float totalCost;
    private float bookingCost;
    private float mealCost;

    private Place place;
    private TripBooking tripBooking;

    public TripCost(int tripID, int placeID, float totalCost, float bookingCost, float mealCost) {
        this.tripID = tripID;
        this.placeID = placeID;
        this.totalCost = totalCost;
        this.bookingCost = bookingCost;
        this.mealCost = mealCost;
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(float bookingCost) {
        this.bookingCost = bookingCost;
    }

    public float getMealCost() {
        return mealCost;
    }

    public void setMealCost(float mealCost) {
        this.mealCost = mealCost;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TripBooking getTripBooking() {
        return tripBooking;
    }

    public void setTripBooking(TripBooking tripBooking) {
        this.tripBooking = tripBooking;
    }
}
