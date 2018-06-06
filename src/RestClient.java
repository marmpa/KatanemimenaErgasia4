//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClient {

    public static void main(String[] args) {
        try {
            RestInterface look_op = (RestInterface) Naming.lookup("//localhost/RestServer");

            look_op.sendMessage(new RestMessage("client01", "Hello World!!!!"));
            look_op.sendMessage(new RestMessage("client02", "Hello World!!!!"));
            look_op.sendMessage(new RestMessage("client01", "Good Morning"));

            System.out.println(look_op.update());
            System.out.println(look_op.update("client01"));
            System.out.println(look_op.update("client02"));

        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        RestGui a = new RestGui();
    }
}
