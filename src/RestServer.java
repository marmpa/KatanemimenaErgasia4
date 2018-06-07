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

public class RestServer extends UnicastRemoteObject implements RestInterface {

    private Statement stat;

    public RestServer() throws RemoteException {
        super();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
            stat = conn.createStatement();
            System.out.println("Database connection established");
            stat.executeUpdate("DROP table if exists users;");
            stat.executeUpdate("DROP table if exists posts;");
            stat.executeUpdate("CREATE table users (name varchar(50),surname varchar(50),username varchar(50),birthday date,gender varchar(10) ,description varchar(200),country varchar(30), city varchar(30));");
            stat.executeUpdate("CREATE table posts (current_date varchar(50),user_posted varchar(50),user_received varchar(50), message varchar(200));");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(RestMessage msg) throws RemoteException {
        try {
            String message = "INSERT INTO posts (current_date, user_posted, user_received,message) VALUES ('" + msg.getDate() + "','" + msg.getName() + "','" + msg.getUserReceived() + "','" + msg.getPost() + "')";
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

    public String update() throws RemoteException {
        ResultSet records = null;
        try {
            records = stat.executeQuery("SELECT * from posts");

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String d, n, m, r;
        String str = "";
        try {
            while (records.next()) {
                d = records.getString("current_date");
                n = records.getString("user_posted");
                r = records.getString("user_received");
                m = records.getString("message");
                str = str + d + " " + n + " " + m + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public String update(String name) throws RemoteException {
        ResultSet records = null;
        try {
            records = stat.executeQuery("SELECT * from posts WHERE user_posted='" + name + "'");

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String d, n, m, r;
        String str = "";
        try {
            while (records.next()) {
                d = records.getString("current_date");
                n = records.getString("user_posted");
                r = records.getString("user_received");
                m = records.getString("message");
                str = str + d + " " + n + " " + m + "\n";

            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public String updateValues(String name, RestMessage msg) throws RemoteException {
        try {
            String message = "UPDATE users SET name =" + msg.getName() + ", surname=" + msg.getSurname() + ", username=" + msg.getUsername() + ", date=" + msg.getDate() + ", gender=" + msg.getGender() + ", description=" + msg.getDescription() + ", country=" + msg.getCountry() + ", city=" + msg.getCity() + " WHERE name=" + msg.getName();
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);
            return "O xristis allage epitixos";
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
