package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.User;

import java.sql.ResultSet;

public class UserWrapper {

    DBConnection db;
    ResultSet rs;
    public UserWrapper(){
        db = new DBConnection();
        rs = null;
    }

    public UserWrapper(DBConnection db){
        this.db = db;
        rs = null;
    }

    public boolean validateUser(String username, String password){
        String query = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                return true;
            }
        }catch(Exception e){

        }
        return false;
    }

    public void addUser(String username, String name, String password, String email){
        String query = "INSERT INTO User (username, name, password, email) VALUES ('" + username + "', '" + name + "', '" + password + "', '" + email + "')";
        db.insertQuery(query);
    }

    public User getUser(String username){
        String query = "SELECT * FROM User WHERE username = '" + username + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("name"));
                return user;
            }
        }catch(Exception e){

        }
        return null;
    }

    public User getUserByEmail(String email){
        String query = "SELECT * FROM User WHERE email = '" + email + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("name"));
                return user;
            }
        }catch(Exception e){

        }
        return null;
    }

    public Integer generateOTP(){
        String otp = "";
        for(int i = 0; i < 6; i++){
            otp += (int)(Math.random() * 10);
        }
        return Integer.parseInt(otp);
    }

    public void updatePassword(String username, String password){
        String query = "UPDATE User SET password = '" + password + "' WHERE username = '" + username + "'";
        db.updateQuery(query);
    }

    public boolean isBlocked(String username){
        String query = "SELECT * FROM BlockedUser WHERE username = '" + username + "'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                return true;
            }
        }catch(Exception e){

        }
        return false;
    }

    public void blockUser(String username){
        String query = "INSERT INTO BlockedUser (username) VALUES ('" + username + "')";
        db.insertQuery(query);
    }

   //Added
    public String getUsernamebyEmail(String email)
    {
        String query = "SELECT username FROM User where email = '" + email +"'";
        rs = db.executeQuery(query);
        try{
            if(rs.next()){
                String username = rs.getString("username");
                return username;
            }
        }catch(Exception e){

        }
        return null;

    }

    public void removeBlockedUser(String username){
        String query = "DELETE FROM BlockedUser where username = '" + username +"'";
        db.deleteQuery(query);
    }
}
