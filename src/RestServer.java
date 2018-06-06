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
            stat.executeUpdate("DROP table if exists messages;");
            stat.executeUpdate("CREATE table messages (current_date varchar(50),client_name varchar(50), message varchar(200));");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(RestMessage msg) throws RemoteException {
        try {
            String message = "INSERT INTO messages (current_date, client_name,message) VALUES ('" + msg.getDate() + "','" + msg.getName() + "','" + msg.getMessage() + "')";
            System.out.println("Query executed : " + message);
            stat.executeUpdate(message);

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String update() throws RemoteException {
        ResultSet records = null;
        try {
            records = stat.executeQuery("SELECT * from messages");

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String d, n, m;
        String str = "";
        try {
            while (records.next()) {
                d = records.getString("current_date");
                n = records.getString("client_name");
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
            records = stat.executeQuery("SELECT * from messages WHERE client_name='" + name + "'");

        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        String d, n, m;
        String str = "";
        try {
            while (records.next()) {
                d = records.getString("current_date");
                n = records.getString("client_name");
                m = records.getString("message");
                str = str + d + " " + n + " " + m + "\n";

            }
        } catch (SQLException ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
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
