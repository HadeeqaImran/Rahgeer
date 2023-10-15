package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;

import java.sql.ResultSet;

public class AdminWrapper {

    DBConnection conn;
    ResultSet rs;

    public AdminWrapper() {
        conn = new DBConnection();
        rs = null;
    }

    public AdminWrapper(DBConnection conn) {
        this.conn = conn;
        rs = null;
    }

    public boolean validateAdmin(String username, String password) {
        User user = new UserWrapper(conn).getUser(username);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(password)) {
            if (getAdmin(username) != null) {
                return true;
            }
        }
        return false;
    }

    public Admin getAdmin(String username) {
        String query = "SELECT * FROM Admin WHERE username = '" + username + "'";
        rs = conn.executeQuery(query);
        try {
            if (rs.next()) {
                Admin admin = new Admin(rs.getString("username"));
                User user = new UserWrapper(conn).getUser(username);
                admin.setUser(user);
                return admin;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Admin getAdminByEmail(String email) {
        User user = new UserWrapper(conn).getUserByEmail(email);
        if (user == null) {
            return null;
        }
        return getAdmin(user.getUsername());
    }
    public boolean adminAgainstUsername(String username) {
        Admin admin = getAdmin(username);
        if(admin == null) {
            return false;
        }
        return true;
    }

    public void updateName(String username, String name) {
        String query = "UPDATE User SET name = '" + name + "' WHERE username = '" + username + "'";
        conn.updateQuery(query);
    }

}
