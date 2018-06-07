//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.io.Serializable;
import java.util.Date;

public class RestMessage implements Serializable {

    private String name;
    private String surname;
    private String username;
    private String gender;
    private String description;
    private String country;
    private String city;
    private String post;
    private String user_received;
    private Date date;

    public RestMessage(String name, String post, String user_received) {
        this.name = name;
        this.post = post;
        this.user_received = user_received;
        this.date = new Date();
    }

    public RestMessage(String name, String surname, String username, Date date, String gender, String description, String country, String city) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.date = date;
        this.gender = gender;
        this.description = description;
        this.country = country;
        this.city = city;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getGender() {
        return this.gender;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getPost() {
        return this.post;
    }

    public String getUserReceived() {
        return this.user_received;
    }

    public Date getDate() {
        return this.date;
    }
}
