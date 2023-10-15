package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Trip;
import com.webapp.Rahgeer.Model.TripBooking;
import com.webapp.Rahgeer.Model.TripCost;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TripWrapper {
    DBConnection db;

    public TripWrapper() {
        db = new DBConnection();
    }

    public TripWrapper(DBConnection db) {
        this.db = db;
    }

    public void addTrip(String username, String source, String destination, String startDate, String endDate)
    {
        String query = "INSERT INTO Trip (tripBy, source, destination, startDate, endDate, isRated) VALUES ('" + username + "', '" + source + "', '" + destination + "', '" + startDate + "', '" + endDate + "', '0')";
        db.insertQuery(query);
    }

    public void deleteTrip(int tripID)
    {
        String query = "DELETE FROM Trip WHERE tripID = " + tripID;
        db.deleteQuery(query);
    }

    public void rateTrip(int tripID)
    {
        String query = "UPDATE Trip SET isRated = 1 WHERE tripID = " + tripID;
        db.updateQuery(query);
    }

    public Trip getTrip(int tripID) {
        String query = "SELECT * FROM Trip WHERE tripID = " + tripID;
        ResultSet rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                Trip trip = new Trip(rs.getInt("tripID"), rs.getString("tripBy"), rs.getString("source"), rs.getString("destination"), rs.getString("startDate"), rs.getString("endDate"), rs.getBoolean("isRated"));
                return trip;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Trip getTrip(String username, String source, String destination, String startDate, String endDate)
    {
        String query = "SELECT * FROM Trip WHERE tripBy = '" + username + "' AND source = '" + source + "' AND destination = '" + destination + "' AND startDate = '" + startDate + "' AND endDate = '" + endDate + "'";
        ResultSet rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                Trip trip = new Trip(rs.getInt("tripID"), rs.getString("tripBy"), rs.getString("source"), rs.getString("destination"), rs.getString("startDate"), rs.getString("endDate"), rs.getBoolean("isRated"));
                return trip;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Trip> getTripsofTourist(String username){
        String query = "SELECT * FROM Trip WHERE tripBy = '" + username + "'";
        ResultSet rs = db.executeQuery(query);
        List<Trip> trips = new ArrayList<>();
        try {
            while (rs.next()) {
                Trip trip = new Trip(rs.getInt("tripID"), rs.getString("tripBy"), rs.getString("source"), rs.getString("destination"), rs.getString("startDate"), rs.getString("endDate"), rs.getBoolean("isRated"));
                trips.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trips;
    }

    public boolean isFutureTrip(int tripID) {
        Trip trip = getTrip(tripID);
        return trip.getStartDate().compareTo(String.valueOf(java.time.LocalDate.now())) > 0;
    }

    public void addPlaceToTrip(int tripID, int placeID)
    {
        String query = "INSERT INTO TripPlace (tripID, placeID) VALUES ('" + tripID + "', '" + placeID + "')";
        db.insertQuery(query);
    }

    public List<Integer> getPlaceID(int tripID){
        String query = "SELECT placeID FROM TripPlace WHERE tripID = " + tripID;
        ResultSet rs = db.executeQuery(query);
        List<Integer> placeIDs = new ArrayList<>();
        try {
            while (rs.next()) {
                placeIDs.add(rs.getInt("placeID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return placeIDs;
    }

    public void deletePlaceFromTrip(int tripID, int placeID)
    {
        String query = "DELETE FROM TripPlace WHERE tripID = " + tripID + " AND placeID = " + placeID;
        db.deleteQuery(query);
    }

    public void deletePlaceFromTrip(int tripID)
    {
        String query = "DELETE FROM TripPlace WHERE tripID = " + tripID;
        db.deleteQuery(query);
    }

    public void addTripCost(int tripID, float total, int placeID, float mealCost, float bookingCost) {
        String query = "INSERT INTO TripCost (tripID, placeID, totalCost, mealCost, bookingCost) VALUES ('" + tripID + "', '" + placeID + "', '" + total + "', '" + mealCost + "', '" + bookingCost + "')";
        db.insertQuery(query);
    }

    public void deleteTripCost(int tripID){
        String query = "DELETE FROM TripCost WHERE tripID = " + tripID;
        db.deleteQuery(query);
    }

    public TripCost getTripCost(int tripID, int placeID) {
        String query = "SELECT * FROM TripCost WHERE tripID = " + tripID + " AND placeID = " + placeID;
        ResultSet rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                TripCost tripCost = new TripCost(rs.getInt("tripID"), rs.getInt("placeID"), rs.getFloat("totalCost"), rs.getFloat("mealCost"), rs.getFloat("bookingCost"));
                return tripCost;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTripBooking(int tripID, int placeID, float totalCost, String to, String from){
        String query = "INSERT INTO TripBooking (tripID, placeID, totalCost, toDate, fromDate) VALUES ('" + tripID + "', '" + placeID + "', '" + totalCost + "', '" + to + "', '" + from + "')";
        db.insertQuery(query);
    }

    public TripBooking getTripBooking(int tripID, int placeID) {
        String query = "SELECT * FROM TripBooking WHERE tripID = " + tripID + " AND placeID = " + placeID;
        ResultSet rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                TripBooking tripBooking = new TripBooking(rs.getInt("tripID"), rs.getInt("placeID"), rs.getString("fromDate"), rs.getString("toDate"), rs.getFloat("totalCost"));
                return tripBooking;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTripBooking(int tripID)
    {
        String query = "DELETE FROM TripBooking WHERE tripID = " + tripID;
        db.deleteQuery(query);
    }

    public List<String> getAllSourcesofTourist(String username) {
        String query = "SELECT DISTINCT source FROM Trip WHERE tripBy = '" + username + "'";
        ResultSet rs = db.executeQuery(query);
        List<String> sources = new ArrayList<>();
        try {
            while (rs.next()) {
                sources.add(rs.getString("source"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sources;
    }

    public List<String> getAllDestinationsofTourist(String username) {
        String query = "SELECT DISTINCT destination FROM Trip WHERE tripBy = '" + username + "'";
        ResultSet rs = db.executeQuery(query);
        List<String> destinations = new ArrayList<>();
        try {
            while (rs.next()) {
                destinations.add(rs.getString("destination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destinations;
    }

    public List<Trip> getTripsOfTouristBySourceDestination(String username, String source, String destination) {
        String query = "SELECT * FROM Trip WHERE tripBy = '" + username + "' AND source = '" + source + "' AND destination = '" + destination + "'";
        ResultSet rs = db.executeQuery(query);
        List<Trip> trips = new ArrayList<>();
        try {
            while (rs.next()) {
                Trip trip = new Trip(rs.getInt("tripID"), rs.getString("tripBy"), rs.getString("source"), rs.getString("destination"), rs.getString("startDate"), rs.getString("endDate"), rs.getBoolean("isRated"));
                trips.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trips;
    }
}
