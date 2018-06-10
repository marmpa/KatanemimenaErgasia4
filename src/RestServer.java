//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class RestServer extends UnicastRemoteObject implements RestInterface {

    private Statement stat;
    private Statement statNew;

    public RestServer() throws RemoteException {
        super();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
            stat = conn.createStatement();
            statNew = conn.createStatement();
            System.out.println("Database connection established");
            stat.executeUpdate("DROP table if exists users;");
            stat.executeUpdate("DROP table if exists posts;");
            stat.executeUpdate("DROP table if exists friends;");
            stat.executeUpdate("CREATE table users (name varchar(50),surname varchar(50),username varchar(50),birthday date,gender varchar(10) ,description varchar(200),country varchar(30), city varchar(30));");
            stat.executeUpdate("CREATE table posts (id varchar(50),date varchar(50),user_posted varchar(50),user_received varchar(50), message varchar(200));");
            stat.executeUpdate("CREATE table friends (friend_send varchar(50),friends_username varchar(50));");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createPost(RestMessage msg) throws RemoteException {
        try {
            String message = "INSERT INTO posts (id, date, user_posted, user_received, message) VALUES ('" + msg.getID() + "','" + msg.getDate() + "','" + msg.getName() + "','" + msg.getUserReceived() + "','" + msg.getPost() + "')";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String register(RestMessage msg) throws RemoteException {
        try {
            String message = "INSERT INTO users (name ,surname ,username ,birthday ,gender ,description ,country , city) VALUES ('" + msg.getName() + "','" + msg.getSurname() + "','" + msg.getUsername() + "','" + msg.getDate() + "','" + msg.getGender() + "','" + msg.getDescription() + "','" + msg.getCountry() + "','" + msg.getCity() + "')";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "O xristis eggrafike epitixos";
    }

    public String addFriend(RestMessage msg) throws RemoteException {
        try {
            String message = "INSERT INTO friends (friend_send ,friends_username) VALUES ('" + msg.getName() + "','" + msg.getUserReceived() + "')";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "O xristis ekane add filo epitixos";
    }

    public String showFriends(String name) throws RemoteException {
        ResultSet records = null;
        ResultSet friends = null;
        String str = "";
        String fr_us, na, su, us, bi, de, ci, co;
        try {
            records = stat.executeQuery("Select friends_username from friends WHERE friend_send='" + name + "'");
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (records.next()) 
            {
                fr_us = records.getString("friends_username");
                friends = statNew.executeQuery("Select * from users WHERE username='" + fr_us + "'");
                na = friends.getString("name");
                su = friends.getString("surname");
                us = friends.getString("username");
                bi = friends.getString("birthday");
                de = friends.getString("description");
                co = friends.getString("country");
                ci = friends.getString("city");
                str = str + na + " " + su + " " + us + " " + bi + " " + de + " " + co + " " + ci + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return str;
    }

    public String update() throws RemoteException {
        ResultSet records = null;
        try {
            records = stat.executeQuery("SELECT * from posts");

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String i, d, n, m, r;
        String str = "";
        try {
            while (records.next()) {
                i = records.getString("id");
                d = records.getString("date");
                n = records.getString("user_posted");
                r = records.getString("user_received");
                m = records.getString("message");
                str = str + i + " " + d + " " + n + " " + m + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public String showPost(String name) throws RemoteException {
        ResultSet records = null;
        try {
            records = stat.executeQuery("SELECT * from posts WHERE user_posted='" + name + "'");

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String i, d, n, m, r;
        String str = "";
        try {
            while (records.next()) {
                i = records.getString("id");
                d = records.getString("date");
                n = records.getString("user_posted");
                r = records.getString("user_received");
                m = records.getString("message");
                str = str + i + " " + d + " " + n + " " + m + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public String topPosts(String name) throws RemoteException {
        ResultSet records = null;
        String i, d, n, m, r;
        String str = "";
        try {
            records = stat.executeQuery("SELECT * from posts WHERE user_posted='" + name + "' LIMIT 10");
            try {
                while (records.next()) {
                    i = records.getString("id");
                    d = records.getString("date");
                    n = records.getString("user_posted");
                    r = records.getString("user_received");
                    m = records.getString("message");
                    str = str + i + " " + d + " " + n + " " + m + "\n";
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return str;
    }

    public String deleteFriend(RestMessage rest) throws RemoteException {
        try {
            String message = "Delete from friends where friend_send=" + rest.getName() + ", friends_username=" + rest.getUserReceived();
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Diagrafike o filos";
    }

    public String updateValues(String name, RestMessage msg) throws RemoteException {
        try {
            String message = "UPDATE users SET name='" + msg.getName() + "', surname='" + msg.getSurname() + "', username='" + msg.getUsername() + "', birthday='" + msg.getDate() + "', gender='" + msg.getGender() + "', description='" + msg.getDescription() + "', country='" + msg.getCountry() + "', city='" + msg.getCity() + "' WHERE name='" + name + "'";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);
            return "O xristis allage epitixos";
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String updatePost(String id, String post) throws RemoteException {
        try {
            Date date = new Date();
            String message = "UPDATE posts SET date='" + date + "', message='" + post + "' WHERE id='" + id + "'";
            stat.executeUpdate(message);
            return "To post allage epitixos";
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static void main(String[] args) throws InterruptedException {

        RestServer server;
        try {
            server = new RestServer();
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/RestServer", server);
            System.out.println("Waiting new Messages");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
}
