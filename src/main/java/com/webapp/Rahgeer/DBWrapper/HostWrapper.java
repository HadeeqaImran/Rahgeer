package com.webapp.Rahgeer.DBWrapper;


import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.*;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HostWrapper {

        DBConnection conn;
        ResultSet rs;

        public HostWrapper() {
                conn = new DBConnection();
                rs = null;
        }

        public HostWrapper(DBConnection conn) {
                this.conn = conn;
                rs = null;
        }

        public boolean validateHost(String username, String password) {
                User user = new UserWrapper(conn).getUser(username);
                if (user == null) {
                        return false;
                }
                if (user.getPassword().equals(password)) {
                        if (getHost(username) != null) {
                                return true;
                        }
                }
                return false;
        }

        public Host getHost(String username) {
                String query = "SELECT * FROM Host WHERE username = '" + username + "'";
                ResultSet rs = conn.executeQuery(query);
                try {
                        if (rs.next()) {
                                Host host = new Host(rs.getString("username"), rs.getString("CNIC"), rs.getFloat("rating"));
                                User user = new UserWrapper(conn).getUser(username);
                                host.setUser(user);
                                return host;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public void addHost(String username, String cnic, Float rating) {
                String query = "INSERT INTO Host (username, CNIC, rating) VALUES ('" + username + "', '" + cnic + "', '" + rating + "')";
                conn.insertQuery(query);
        }

        public List<Host> getAllHosts() {
                rs = null;
                String query = "SELECT * FROM Host";
                rs = conn.executeQuery(query);
                List<Host> hosts = new ArrayList<Host>();
                try {
                        if (rs.next()) {
                                Host host = new Host(rs.getString("username"), rs.getString("CNIC"), rs.getFloat("rating"));
                                User user = new UserWrapper(conn).getUser(rs.getString("username"));
                                host.setUser(user);
                                hosts.add(host);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return hosts;
        }

        public Host getHostByEmail(String email) {
                User user = new UserWrapper(conn).getUserByEmail(email);
                if (user == null) {
                        return null;
                }
                return getHost(user.getUsername());
        }

        public Host getHostByCNIC(String cnic) {
                rs = null;
                String query = "SELECT * FROM Host WHERE CNIC = '" + cnic + "'";
                rs = conn.executeQuery(query);
                try {
                        if (rs.next()) {
                                Host host = new Host(rs.getString("username"), rs.getString("CNIC"), rs.getFloat("rating"));
                                User user = new UserWrapper(conn).getUser(rs.getString("username"));
                                host.setUser(user);
                                return host;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public boolean hostAgainstCNIC(String cnic) {
               Host host = getHostByCNIC(cnic);
                if(host == null) {
                          return false;
                }
                return true;
        }

        public boolean hostAgainstEmail(String email) {
                Host host = getHostByEmail(email);
                if(host == null) {
                        return false;
                }
                return true;
        }

        public boolean hostAgainstUsername(String username) {
                Host host = getHost(username);
                if(host == null) {
                        return false;
                }
                return true;
        }

        public void updateName(String username, String name) {
                String query = "UPDATE User SET name = '" + name + "' WHERE username = '" + username + "'";
                conn.updateQuery(query);
        }

        public void addRating(String username, Float rating) {
                Host host = getHost(username);
                Float newRating = (host.getRating() + rating) / 2;
                String query = "UPDATE Host SET rating = '" + newRating + "' WHERE username = '" + username + "'";
                conn.updateQuery(query);
        }

        public List<Host> getBlockedHosts() {
                rs = null;
                String query = "SELECT * FROM Host where username IN (SELECT Host.username FROM Host JOIN BlockedUser on Host.username = BlockedUser.username)";
                rs = conn.executeQuery(query);
                List<Host> hosts = new ArrayList<>();
                try {
                        while (rs.next()) {
                                Host host = new Host(rs.getString("username"), rs.getString("CNIC"), rs.getFloat("rating"));
                                User user = new UserWrapper(conn).getUser(rs.getString("username"));
                                host.setUser(user);
                                hosts.add(host);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return hosts;
        }
        public List<Host> getAllUnblockedHosts() {
                rs = null;
                String query = "SELECT * FROM Host where username NOT IN (SELECT Host.username FROM Host JOIN BlockedUser on Host.username = BlockedUser.username)";
                rs = conn.executeQuery(query);
                List<Host> hosts = new ArrayList<>();
                try {
                        while (rs.next()) {
                                Host host = new Host(rs.getString("username"), rs.getString("CNIC"), rs.getFloat("rating"));
                                User user = new UserWrapper(conn).getUser(rs.getString("username"));
                                host.setUser(user);
                                hosts.add(host);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return hosts;
        }
}
