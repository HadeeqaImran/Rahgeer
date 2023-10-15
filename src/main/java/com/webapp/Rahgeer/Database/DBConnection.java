package com.webapp.Rahgeer.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Properties;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    @Autowired
    private Environment env;
    String url;
    String user;
    String password;
    Connection conn;

    public DBConnection() {
        conn = null;
        try {
            url = "jdbc:mysql://localhost:3306/rahgeer";
            user = "root";
            password = "MoNarch1EterNal";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the MYSQL server successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rs;
    }

    public void insertQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void openConnection(){
        try {
            url = "jdbc:mysql://localhost:3306/Rahgeer";
            user = "root";
            password = "my-secret-password";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the MYSQL server successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        String query = "SELECT * FROM Request WHERE acceptedBy IS NULL";
        ResultSet rs = db.executeQuery(query);
        try {
            while (rs.next()) {
                System.out.println(rs.getString("createdBy"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



}