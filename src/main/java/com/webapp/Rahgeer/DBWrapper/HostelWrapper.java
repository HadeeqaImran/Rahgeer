package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Hostel;
import com.webapp.Rahgeer.Model.Place;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HostelWrapper {
    DBConnection db;
    ResultSet rs;
    PlaceWrapper placeWrapper = new PlaceWrapper(db);

    public HostelWrapper() {
        db = new DBConnection();
        rs = null;
    }

    public HostelWrapper(DBConnection db) {
        this.db = db;
        rs = null;
    }

    public void addHostel(int hostelID, int totalRooms, int availableRooms, float pricePerNight, int beds){
        String query = "INSERT INTO Hostel (hostelID, totalRooms, availableRooms, costPerNight, BedsPerRoom) VALUES ('" + hostelID + "', '" + totalRooms + "', '" + availableRooms + "', '" + pricePerNight + "', '" + beds + "')";
        db.insertQuery(query);
    }

    public void deleteHostel(int hostelID){
        String query = "DELETE FROM Hostel WHERE hostelID = " + hostelID;
        db.deleteQuery(query);
    }

    public Hostel getHostel(int hostelID){
        String query = "SELECT * FROM Hostel WHERE hostelID = " + hostelID;
        ResultSet rs = db.executeQuery(query);
        try{
            while (rs.next()){
                Hostel hostel = new Hostel(rs.getInt("hostelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"), rs.getInt("BedsPerRoom"));
                return hostel;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<Hostel> getAllHostels(){
        String query = "SELECT * FROM Hostel";
        ResultSet rs = db.executeQuery(query);
        List<Hostel> hostels = new ArrayList<>();
        try{
            while(rs.next()){
                Hostel hostel = new Hostel(rs.getInt("hostelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"), rs.getInt("BedsPerRoom"));
                hostels.add(hostel);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return hostels;
    }

    public List<Hostel> getHostelsByCity(String city){
        String query = "SELECT * FROM Hostel WHERE hostelID IN (SELECT placeID FROM Place WHERE city = '" + city + "')";
        ResultSet rs = db.executeQuery(query);
        List<Hostel> hostels = new ArrayList<>();
        try{
            while(rs.next()){
                Hostel hostel = new Hostel(rs.getInt("hostelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"), rs.getInt("BedsPerRoom"));
                Place place = placeWrapper.getPlace(rs.getInt("hostelID"));
                hostel.setPlace(place);
                hostels.add(hostel);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return hostels;
    }

    public List<Hostel> getHostelForBooking(String city, float maxCost, Optional<String> expression){
        // expression should be in name of the hostel
        String query = "";
        if (expression.isPresent()){
            query = "SELECT * FROM Hostel WHERE hostelID IN (SELECT placeID FROM Place WHERE city = '" + city + "' AND placeName LIKE '%" + expression.get() + "%') AND costPerNight <= " + maxCost;
        }else{
            query = "SELECT * FROM Hostel WHERE hostelID IN (SELECT placeID FROM Place WHERE city = '" + city + "') AND costPerNight <= " + maxCost;
        }
        ResultSet rs = db.executeQuery(query);
        List<Hostel> hostels = new ArrayList<>();
        try{
            while(rs.next()){
                Hostel hostel = new Hostel(rs.getInt("hostelID"), rs.getInt("totalRooms"), rs.getInt("availableRooms"), rs.getFloat("costPerNight"), rs.getInt("BedsPerRoom"));
                hostels.add(hostel);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return hostels;
    }

}
