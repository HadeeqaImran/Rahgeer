package com.webapp.Rahgeer.Model;

import java.util.List;

public class Hostel {
    private int hostelID;
    private int totalRooms;
    private int availableRooms;
    private float pricePerNight;
    private int bedsPerRoom;
    private Place place;
    private List<Service> services;
    private MealPrice mealPrice;

    public Hostel(int hostelID, int totalRooms, int availableRooms, float pricePerNight, int bedsPerRoom) {
        this.hostelID = hostelID;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.pricePerNight = pricePerNight;
        this.bedsPerRoom = bedsPerRoom;
    }

    public int getHostelID() {
        return hostelID;
    }

    public void setHostelID(int hostelID) {
        this.hostelID = hostelID;
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

    public int getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(int bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
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
