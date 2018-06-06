//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.io.Serializable;
import java.util.Date;

public class RestMessage implements Serializable {

    private String name;
    private String msg;
    private Date date;

    public RestMessage(String name, String msg) {
        this.name = name;
        this.msg = msg;
        this.date = new Date();
    }

    public String getName() {
        return this.name;
    }

    public String getMessage() {
        return this.msg;
    }

    public Date getDate() {
        return this.date;
    }
}
