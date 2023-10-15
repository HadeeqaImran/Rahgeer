package com.webapp.Rahgeer.DBWrapper;

import com.webapp.Rahgeer.Database.DBConnection;
import com.webapp.Rahgeer.Model.Host;
import com.webapp.Rahgeer.Model.Message;
import com.webapp.Rahgeer.Model.Tourist;
import com.webapp.Rahgeer.Model.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageWrapper {
    DBConnection conn;

    public MessageWrapper() {
        conn = new DBConnection();
    }

    public MessageWrapper(DBConnection conn) {
        this.conn = conn;
    }

    public void addMessage(String sender, String receiver, String message, String time) {
        String query = "INSERT INTO Message (sentBy, sentOn, sentTo, messageBody) VALUES ('" + sender + "', '" + time + "', '" + receiver + "', '" + message + "')";
        conn.insertQuery(query);
    }

    public List<User> getChatUsers(String username) {
        String query = "SELECT DISTINCT sentTo FROM Message WHERE sentBy = '" + username + "'";
        ResultSet rs = conn.executeQuery(query);
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new UserWrapper(conn).getUser(rs.getString("sentTo"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Tourist> getChatUsersTourist(String username) {
        String query = "SELECT DISTINCT sentTo FROM Message WHERE sentBy = '" + username + "'";
        ResultSet rs = conn.executeQuery(query);
        List<Tourist> users = new ArrayList<>();
        try {
            while (rs.next()) {
                Tourist user = new TouristWrapper(conn).getTourist(rs.getString("sentTo"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Host> getChatUsersHost(String username) {
        String query = "SELECT DISTINCT sentBy FROM Message WHERE sentTo = '" + username + "'";
        ResultSet rs = conn.executeQuery(query);
        List<Host> users = new ArrayList<>();
        try {
            while (rs.next()) {
                Host user = new HostWrapper(conn).getHost(rs.getString("sentBy"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Message> getAllMessages(String sender, String receiver) {
        String query = "SELECT * FROM Message WHERE (sentBy = '" + sender + "' AND sentTo = '" + receiver + "') OR (sentBy = '" + receiver + "' AND sentTo = '" + sender + "') ORDER BY sentOn DESC";
        ResultSet rs = conn.executeQuery(query);
        List<Message> messages = new ArrayList<>();
        try {
            while (rs.next()) {
                Message message = new Message(rs.getString("sentBy"), rs.getString("sentTo"), rs.getString("messageBody"), rs.getString("sentOn"));
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public String checkForOffensiveWords(String message) {
        String[] offensiveWords = new String[]{"asshole", "bastard", "fuck", "f*ck", "fu*k"};
        String[] words = message.split(" ");
        for (String word : words) {
            for (String offensiveWord : offensiveWords) {
                if (word.equalsIgnoreCase(offensiveWord)) {
                    return "This message was blocked because it contains offensive words.";
                }
            }
        }
        return message;
    }
}
