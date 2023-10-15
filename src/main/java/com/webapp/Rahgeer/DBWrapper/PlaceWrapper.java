package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Host;
import com.webapp.Rahgeer.Model.MealPrice;
import com.webapp.Rahgeer.Model.Place;
import com.webapp.Rahgeer.Model.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlaceWrapper {
    DBConnection conn;
    ResultSet rs;

    public PlaceWrapper() {
        conn = new DBConnection();
        rs = null;
    }

    public PlaceWrapper(DBConnection conn) {
        this.conn = conn;
        rs = null;
    }

    public void addPlace(String name, String description, String address, String owner, float rating, String city) {
        String query = "INSERT INTO Place (placeName, placeDescription, placeAddress, placeOwner, placeRating, city) VALUES ('" + name + "', '" + description + "', '" + address + "', '" + owner + "', '" + rating + "', '" + city + "')";
        conn.insertQuery(query);
    }

    public void deletePlace(int id) {
        String query = "DELETE FROM Place WHERE placeID = " + id;
        conn.deleteQuery(query);
    }

    public Place getPlace(int id) {
        String query = "SELECT * FROM Place WHERE placeID = " + id;
        ResultSet rs = conn.executeQuery(query);
        try {
            if (rs.next()) {
                Place place = new Place(rs.getInt("placeID"), rs.getString("placeName"), rs.getString("placeDescription"), rs.getString("placeAddress"), rs.getFloat("placeRating"), rs.getString("placeOwner"), rs.getString("city"));
                Host host = new HostWrapper(conn).getHost(place.getPlaceOwner());
                place.setHost(host);
                return place;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Place> getPlacesOfOwner(String owner) {
        String query = "SELECT * FROM Place WHERE placeOwner = '" + owner + "'";
        rs = conn.executeQuery(query);
        List<Place> places = new ArrayList<>();
        try {
            while (rs.next()) {
                Place place = new Place(rs.getInt("placeID"), rs.getString("placeName"), rs.getString("placeDescription"), rs.getString("placeAddress"), rs.getFloat("placeRating"), rs.getString("placeOwner"), rs.getString("city"));
                Host host = new HostWrapper(conn).getHost(rs.getString("placeOwner"));
                place.setHost(host);
                places.add(place);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    public List<Place> getAllPlaces(){
        rs = null;
        String query = "SELECT * FROM Place";
        rs = conn.executeQuery(query);
        List<Place> places = new ArrayList<>();
        try {
            while (rs.next()) {
                Place place = new Place(rs.getInt("placeID"), rs.getString("placeName"), rs.getString("placeDescription"), rs.getString("placeAddress"), rs.getFloat("placeRating"), rs.getString("placeOwner"), rs.getString("city"));
                Host host = new HostWrapper(conn).getHost(rs.getString("placeOwner"));
                place.setHost(host);
                places.add(place);
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        return places;
    }

    public Place getPlace(String owner, String name, String city){
        String query = "SELECT * FROM Place WHERE placeOwner = '" + owner + "' AND placeName = '" + name + "' AND city = '" + city + "'";
        rs = conn.executeQuery(query);
        try {
            if (rs.next()) {
                Place place = new Place(rs.getInt("placeID"), rs.getString("placeName"), rs.getString("placeDescription"), rs.getString("placeAddress"), rs.getFloat("placeRating"), rs.getString("placeOwner"), rs.getString("city"));
                Host host = new HostWrapper(conn).getHost(rs.getString("placeOwner"));
                place.setHost(host);
                return place;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPlaceService(int placeID, int serviceID){
        String query = "INSERT INTO PlaceService (placeID, serviceID) VALUES ('" + placeID + "', '" + serviceID + "')";
        conn.insertQuery(query);
    }

    public Service getService(int serviceID){
        String query = "SELECT * FROM Service WHERE serviceID = " + serviceID;
        rs = conn.executeQuery(query);
        try {
            if (rs.next()) {
                Service service = new Service(rs.getInt("serviceID"), rs.getString("serviceName"));
                return service;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPlaceMealPrices(int placeID, float breakfastcost, float lunchcost, float dinnercost) {
        String query = "INSERT INTO MealPrice (placeID, breakfastPrice, lunchPrice, dinnerPrice) VALUES ('" + placeID + "', '" + breakfastcost + "', '" + lunchcost + "', '" + dinnercost + "')";
        conn.insertQuery(query);
    }

    public void deletePlaceService(int placeID){
        String query = "DELETE FROM PlaceService WHERE placeID = " + placeID + "";
        conn.deleteQuery(query);
    }

    public void deletePlaceMealPrices(int placeID){
        String query = "DELETE FROM MealPrice WHERE placeID = " + placeID + "";
        conn.deleteQuery(query);
    }

    public MealPrice getMealprice(int placeID) {
        String query = "SELECT * FROM MealPrice WHERE placeID = " + placeID;
        rs = conn.executeQuery(query);
        try {
            if (rs.next()) {
                MealPrice mealPrice = new MealPrice(rs.getFloat("breakfastPrice"), rs.getFloat("lunchPrice"), rs.getFloat("dinnerPrice"));
                return mealPrice;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Service> getPlaceServices(int placeID){
        String query = "SELECT * FROM PlaceService WHERE placeID = " + placeID;
        rs = conn.executeQuery(query);
        List<Service> services = new ArrayList<>();
        try {
            while (rs.next()) {
                Service service = getService(rs.getInt("serviceID"));
                services.add(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }
}
