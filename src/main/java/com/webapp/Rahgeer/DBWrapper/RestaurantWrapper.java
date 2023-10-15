package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Place;
import com.webapp.Rahgeer.Model.Restaurant;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantWrapper {
    DBConnection conn;
    ResultSet rs;

    public RestaurantWrapper() {
        conn = new DBConnection();
        rs = null;
    }

    public RestaurantWrapper(DBConnection conn) {
        this.conn = conn;
        rs = null;
    }

    public void addRestaurant(int restaurantId, int tableCount){
        String query = "INSERT INTO Restaurant (restaurantID, tableCount) VALUES ('" + restaurantId + "', '" + tableCount + "')";
        conn.insertQuery(query);
    }

    public void deleteRestaurant(int restaurantId){
        String query = "DELETE FROM Restaurant WHERE restaurantID = " + restaurantId;
        conn.deleteQuery(query);
    }

    public Restaurant getRestaurant(int restaurantId) {
        String query = "SELECT * FROM Restaurant WHERE restaurantID = " + restaurantId;
        ResultSet rs = conn.executeQuery(query);
        try {
            if (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurantID"), rs.getInt("tableCount"));
                Place place = new PlaceWrapper(conn).getPlace(rs.getInt("restaurantID"));
                restaurant.setPlace(place);
                return restaurant;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Restaurant> getAllRestaurants() {
        String query = "SELECT * FROM Restaurant";
        rs = conn.executeQuery(query);
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            while (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurantID"), rs.getInt("tableCount"));
                Place place = new PlaceWrapper(conn).getPlace(rs.getInt("restaurantID"));
                restaurant.setPlace(place);
                restaurants.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public List<Restaurant> getAllRestaurantsByPlace(String city, Optional<String> expression){
        // expression should be in name
        String query = "";
        if (expression.isPresent()){
            query = "SELECT * FROM Restaurant WHERE restaurantID IN (SELECT placeID FROM Place WHERE city = '" + city + "' AND placeName LIKE '%" + expression.get() + "%')";
        }else{
            query = "SELECT * FROM Restaurant WHERE restaurantID IN (SELECT placeID FROM Place WHERE city = '" + city + "')";
        }
        rs = conn.executeQuery(query);
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            while (rs.next()) {
                Restaurant restaurant = new Restaurant(rs.getInt("restaurantID"), rs.getInt("tableCount"));
                Place place = new PlaceWrapper(conn).getPlace(rs.getInt("restaurantID"));
                restaurant.setPlace(place);
                restaurants.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
