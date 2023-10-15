package com.webapp.Rahgeer.Model;

public class Report {

    private String placeName;
    private String placeOwner;
    private String city;
    private String placeRating;
    private Integer bookingsCount;

    public Report(String placeName, String placeOwner, String city, Integer bookingsCount) {

        this.placeName = placeName;
        this.city = city;
        this.placeOwner = placeOwner;
        this.bookingsCount = bookingsCount;
    }



    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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