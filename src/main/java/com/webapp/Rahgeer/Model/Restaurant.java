package com.webapp.Rahgeer.Model;

public class Restaurant {
    private int restaurantID;
    private int tableCount;
    private Place place;

    public Restaurant(int restaurantID, int tableCount) {
        this.restaurantID = restaurantID;
        this.tableCount = tableCount;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
