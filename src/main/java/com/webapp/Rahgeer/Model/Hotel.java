package com.webapp.Rahgeer.Model;

import java.util.List;

public class Hotel{
    private int hotelID;
    private int totalRooms;
    private int availableRooms;
    private float pricePerNight;
    private Place place;
    private List<Service> services;
    private MealPrice mealPrice;

    public Hotel(int hotelID, int totalRooms, int availableRooms, float pricePerNight) {
        this.hotelID = hotelID;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.pricePerNight = pricePerNight;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public MealPrice getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(MealPrice mealPrice) {
        this.mealPrice = mealPrice;
    }
}
