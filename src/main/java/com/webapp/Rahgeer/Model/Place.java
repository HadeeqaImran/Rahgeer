package com.webapp.Rahgeer.Model;

public class Place {
    private int placeID;
    private String placeName;
    private String placeDescription;
    private String placeAddress;
    private float placeRating;
    private String placeOwner;
    private String city;

    private Host host;

    public Place(int placeID, String placeName, String placeDescription, String placeAddress, float placeRating,
            String placeOwner, String city) {
        this.placeID = placeID;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.placeOwner = placeOwner;
        this.city = city;
    }

    public int getPlaceID() {
        return placeID;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public float getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(float placeRating) {
        this.placeRating = placeRating;
    }

    public String getPlaceOwner() {
        return placeOwner;
    }

    public void setPlaceOwner(String placeOwner) {
        this.placeOwner = placeOwner;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
