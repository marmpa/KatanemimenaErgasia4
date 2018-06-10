//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.rmi.*;

public interface RestInterface extends Remote {

    public String updateValues(String name, RestMessage msg) throws RemoteException;

    public String addFriend(RestMessage msg) throws RemoteException;

    public String showFriends(String name) throws RemoteException;

    public String deleteFriend(String name, String username) throws RemoteException;

    public void createPost(RestMessage msg) throws RemoteException;
    
    public String updatePost(String id, String msg) throws RemoteException;

    public String topPosts(String name) throws RemoteException;

    public String register(RestMessage msg) throws RemoteException;
    
    public String update() throws RemoteException;

    public String showPost(String name) throws RemoteException;
}
