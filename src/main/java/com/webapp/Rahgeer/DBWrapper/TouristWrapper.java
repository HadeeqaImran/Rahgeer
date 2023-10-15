package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TouristWrapper {

    DBConnection db;
    ResultSet rs;

    public TouristWrapper(){
        db = new DBConnection();
        rs = null;
    }

    public TouristWrapper(DBConnection db){
        this.db = db;
        rs = null;
    }

    public void addTourist(String username, String passport, Float rating){
        String query = "INSERT INTO Tourist (username, passportNum, rating) VALUES ('" + username + "', '" + passport + "', '" + rating + "')";
        db.insertQuery(query);
    }

    public Tourist getTourist(String username){
        String query = "SELECT * FROM Tourist WHERE username = '" + username + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                Tourist tourist = new Tourist(rs.getString("username"), rs.getString("passportNum"), rs.getFloat("rating"));
                User user = new UserWrapper(db).getUser(username);
                tourist.setUser(user);
                return tourist;
            }
        }catch(Exception e){

        }
        return null;
    }

    public boolean validateTourist(String username, String password){
        User user = new UserWrapper(db).getUser(username);
        if(user == null){
            return false;
        }
        if(user.getPassword().equals(password)){
            if(getTourist(username) != null){
                return true;
            }
        }
        return false;
    }

    public List<Tourist> getAllTourists(){
        List<Tourist> tourists = new ArrayList<>();
        String query = "SELECT * FROM Tourist";
        rs = db.executeQuery(query);
        try{
            while(rs.next()){
                Tourist tourist = new Tourist(rs.getString("username"), rs.getString("passportNum"), rs.getFloat("rating"));
                User user = new UserWrapper(db).getUser(tourist.getUsername());
                tourist.setUser(user);
                tourists.add(tourist);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return tourists;
    }

    public Tourist getTouristByPassport(String passport){
        String query = "SELECT * FROM Tourist WHERE passportNum = '" + passport + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                Tourist tourist = new Tourist(rs.getString("username"), rs.getString("passportNum"), rs.getFloat("rating"));
                User user = new UserWrapper(db).getUser(tourist.getUsername());
                tourist.setUser(user);
                return tourist;
            }
        }catch(Exception e){

        }
        return null;
    }

    public Tourist getTouristByEmail(String email){
        User user = new UserWrapper(db).getUserByEmail(email);
        if(user == null){
            return null;
        }
        return getTourist(user.getUsername());
    }

    public boolean touristAgainstPassport(String passport){
        Tourist tourist = getTouristByPassport(passport);
        if(tourist == null){
            return false;
        }
        return true;
    }

    public boolean touristAgainstEmail(String email){
        Tourist tourist = getTouristByEmail(email);
        if(tourist == null){
            return false;
        }
        return true;
    }

    public boolean touristAgainstUsername(String username){
        Tourist tourist = getTourist(username);
        if(tourist == null){
            return false;
        }
        return true;
    }

    public void addRequest(String username, String city, String description, String createdOn){
        String query = "INSERT INTO Request (createdBy, city, description, createdOn) VALUES ('" + username + "', '" + city + "', '" + description + "', '" + createdOn + "')";
        db.insertQuery(query);
    }

    public Request getRequest(String createdBy, String createdOn){
        rs = null;
        String query = "SELECT * FROM Request WHERE createdBy = '" + createdBy + "' AND createdOn = '" + createdOn + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                Request request = new Request(rs.getString("createdBy"), rs.getString("city"), rs.getString("description"), rs.getTimestamp("createdOn").toString());
                return request;
            }
        }catch(Exception e){

        }
        return null;
    }

    public List<Request> getAllAvailableRequests() {
        String query = "SELECT * FROM Request WHERE acceptedBy IS NULL";
        ResultSet rs2 = db.executeQuery(query);
        List<Request> requests = new ArrayList<>();
        try {
            while (rs2.next()) {
                Request request = new Request(rs2.getString("createdBy"), rs2.getString("city"), rs2.getString("description"), rs2.getTimestamp("createdOn").toString());
                Tourist tourist = getTourist(rs2.getString("createdBy"));
                request.setTourist(tourist);
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<Request> getRequestsOfTourist(String username) {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT * FROM Request WHERE createdBy = '" + username + "'";
        rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                Request request = new Request(rs.getString("createdBy"), rs.getString("city"), rs.getString("description"), rs.getTimestamp("createdOn").toString());
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public void acceptRequest(String createdBy, String createdOn, String acceptedBy, String acceptedOn){
        String query = "UPDATE Request SET acceptedBy = '" + acceptedBy + "', acceptedOn = '" + acceptedOn + "' WHERE createdBy = '" + createdBy + "' AND createdOn = '" + createdOn + "'";
        db.updateQuery(query);
    }

    public List<Request> getAcceptedRequest(String createdBy)
    {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT * FROM Request WHERE createdBy = '" + createdBy + "' AND acceptedBy IS NOT NULL";
        ResultSet rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                Request request = new Request(rs.getString("createdBy"), rs.getString("city"), rs.getString("description"), rs.getTimestamp("createdOn").toString());
                Tourist tourist = getTourist(rs.getString("createdBy"));
                request.setTourist(tourist);
                request.setAcceptedBy(rs.getString("acceptedBy"));
                request.setAcceptedOn(rs.getTimestamp("acceptedOn").toString());
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public Request getAcceptedRequest(String createdBy, String createdOn) {
        String query = "SELECT * FROM Request WHERE createdBy = '" + createdBy + "' AND createdOn = '" + createdOn + "' AND acceptedBy IS NOT NULL";
        ResultSet rs = db.executeQuery(query);
        try {
            if (rs.next()) {
                Request request = new Request(rs.getString("createdBy"), rs.getString("city"), rs.getString("description"), rs.getTimestamp("createdOn").toString());
                Tourist tourist = getTourist(rs.getString("createdBy"));
                request.setTourist(tourist);
                request.setAcceptedBy(rs.getString("acceptedBy"));
                request.setAcceptedOn(rs.getTimestamp("acceptedOn").toString());
                return request;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Tourist> getAllUnblockedTourists() {
        rs = null;
        String query = "SELECT * FROM Tourist where username NOT IN (SELECT Tourist.username FROM Tourist JOIN BlockedUser on Tourist.username = BlockedUser.username)";
        rs = db.executeQuery(query);
        List<Tourist> tourists = new ArrayList<>();
        try {
            while (rs.next()) {
                Tourist tourist = new Tourist(rs.getString("username"), rs.getString("passportNum"), rs.getFloat("rating"));
                User user = new UserWrapper(db).getUser(rs.getString("username"));
                tourist.setUser(user);
                tourists.add(tourist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tourists;
    }

    public List<Tourist> getBlockedTourists() {
        rs = null;
        String query = "SELECT * FROM Tourist where username IN (SELECT Tourist.username FROM Tourist JOIN BlockedUser on Tourist.username = BlockedUser.username)";
        rs = db.executeQuery(query);
        List<Tourist> tourists = new ArrayList<>();
        try {
            while (rs.next()) {
                Tourist tourist = new Tourist(rs.getString("username"), rs.getString("passportNum"), rs.getFloat("rating"));
                User user = new UserWrapper(db).getUser(rs.getString("username"));
                tourist.setUser(user);
                tourists.add(tourist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tourists;
    }



}
