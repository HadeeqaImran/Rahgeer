package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Report;
import com.webapp.Rahgeer.Model.Place;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportWrapper {
    DBConnection db;
    ResultSet rs;

    public ReportWrapper(){
        db = new DBConnection();
        rs = null;
    }

    public ReportWrapper(DBConnection db){
        this.db = db;
        rs = null;
    }

    public void addReport(String placeName, String placeOwner, String city, Integer bookingsCount) {
        String query = "INSERT INTO Report (placeName, placeOwner, city, bookingsCount) VALUES ('" + placeName + "', '" + placeOwner + "', '" + city + "', '" + bookingsCount + "')";
        db.insertQuery(query);
    }

    public List<Report> getHotelDetails(int month, int year) {
        String query = "SELECT placeName, placeOwner, city, count(placeID) as bookingsCount FROM Place join HotelBooking on HotelID = placeID LEFT OUTER JOIN TripBooking on Place.placeID=TripBooking.tripID group by hotelID having fromDate.getMonth() = ' "+ month +" ' AND fromDate.getYear() = ' "+ year+" '";
        rs = db.executeQuery(query);
        List<Report> reports = new ArrayList<>();
        try {
            while (rs.next()) {
                Report report = new Report(rs.getString("placeName"), rs.getString("placeOwner"),  rs.getString("city"), rs.getInt("bookingsCount"));
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<Report> getHostelDetails(int month, int year) {
        String query = "SELECT placeName, placeOwner, city, count(placeID) as bookingsCount FROM Place join HostelBooking on HostelID = placeID LEFT OUTER JOIN TripBooking on Place.placeID=TripBooking.tripID group by hostelID having fromDate.getMonth() = ' "+ month +" ' AND fromDate.getYear() = ' "+ year+" '";
        rs = db.executeQuery(query);
        List<Report> reports = new ArrayList<>();
        try {
            while (rs.next()) {
                Report report = new Report(rs.getString("placeName"), rs.getString("placeOwner"),  rs.getString("city"), rs.getInt("bookingsCount"));
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<Report> getRestaurantDetails(int month, int year) {
        String query = "SELECT placeName, placeOwner, city, count(placeID) as bookingsCount FROM Place join HostelBooking on RestaurantID = placeID LEFT OUTER JOIN TripBooking on Place.placeID=TripBooking.tripID group by restaurantID having fromDate.getMonth() = ' "+ month +" ' AND fromDate.getYear() = ' "+ year+" '";
        rs = db.executeQuery(query);
        List<Report> reports = new ArrayList<>();
        try {
            while (rs.next()) {
                Report report = new Report(rs.getString("placeName"), rs.getString("placeOwner"),  rs.getString("city"), rs.getInt("bookingsCount"));
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

}