package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Hotel;
import com.webapp.Rahgeer.Model.Place;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelWrapper {
    DBConnection db;
    ResultSet rs;
    PlaceWrapper placeWrapper = new PlaceWrapper(db);

    public HotelWrapper() {
        db = new DBConnection();
        rs = null;
    }

    public HotelWrapper(DBConnection db) {
        this.db = db;
        rs = null;
    }

    public void addHotel(int hotelID, int totalRooms, int availableRooms, float pricePerNight){
        String query = "INSERT INTO Hotel (hotelID, totalRooms, availableRooms, costPerNight) VALUES ('" + hotelID + "', '" + totalRooms + "', '" + availableRooms + "', '" + pricePerNight + "')";
        db.insertQuery(query);
    }

    public void deleteHotel(int hotelID){
        String query = "DELETE FROM Hotel WHERE hotelID = " + hotelID;
        db.deleteQuery(query);
    }

    public Hotel getHotel(int hotelID){
        String query = "SELECT * FROM Hotel WHERE hotelID = " + hotelID;
        ResultSet rs = db.executeQuery(query);
        try{
            while (rs.next()){
                Hotel hotel = new Hotel(rs.getInt("hotelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"));
                return hotel;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<Hotel> getAllHotels(){
        String query = "SELECT * FROM Hotel";
        ResultSet rs = db.executeQuery(query);
        List<Hotel> hotels = new ArrayList<>();
        try{
            while (rs.next()){
                Hotel hotel = new Hotel(rs.getInt("hotelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"));
                hotels.add(hotel);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return hotels;
    }

    public List<Hotel> getHotelsForBooking(String city, float maxBudget, Optional<String> expression)
    {
        String query = "";
        if (expression.isPresent()){
            query = "SELECT * FROM Hotel WHERE hotelID IN (SELECT placeID FROM Place WHERE city = '" + city + "' AND placeName LIKE '%" + expression.get() + "%') AND costPerNight <= " + maxBudget;
        }else{
            query = "SELECT * FROM Hotel WHERE hotelID IN (SELECT placeID FROM Place WHERE city = '" + city + "') AND costPerNight <= " + maxBudget;
        }
        ResultSet rs = db.executeQuery(query);
        List<Hotel> hotels = new ArrayList<>();
        try{
            while (rs.next()){
                Hotel hotel = new Hotel(rs.getInt("hotelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"));
                hotels.add(hotel);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return hotels;
    }
}
