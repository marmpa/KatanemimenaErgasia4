//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClient {

    public static void main(String[] args) {
        try {
            RestInterface look_op = (RestInterface) Naming.lookup("//localhost/RestServer");

            look_op.createPost(new RestMessage("001","client01", "client01", "Hello World!!!!"));
            look_op.createPost(new RestMessage("002","client02", "client02", "Hello World!!!!"));
            look_op.createPost(new RestMessage("003","client01", "client02", "Good Morning"));

            Date date = new Date();
            
            look_op.register(new RestMessage("tom", "lia", "showtime", date, "male", "hey there", "greece", "athens"));
            look_op.register(new RestMessage("ma", "ba", "legend", date, "male", "hey here", "greece", "athens"));
            
            look_op.updateValues("tom",new RestMessage("tom", "lia", "showtime", date, "male", "hello everyone", "greece", "athens"));
            
            look_op.addFriend(new RestMessage("client01","showtime"));
            look_op.addFriend(new RestMessage("client01","legend"));
            
            System.out.println(look_op.showFriends("client01"));
            look_op.deleteFriend(new RestMessage("client01","legend"));
            System.out.println(look_op.showFriends("client01"));
            
            System.out.println(look_op.update());
            look_op.updatePost("001","Are you there?");
            System.out.println(look_op.showPost("client01"));
            System.out.println(look_op.showPost("client02"));
            look_op.deletePost("003");
            System.out.println(look_op.topPosts("client01"));
            
            

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
