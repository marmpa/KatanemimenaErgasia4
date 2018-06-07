//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import java.rmi.*;

public interface RestInterface extends Remote {

    public void sendMessage(RestMessage msg) throws RemoteException;

    public String update() throws RemoteException;

    public String update(String name) throws RemoteException;
    
    public String register(RestMessage msg) throws RemoteException;
    
    public String updateValues(String name, RestMessage msg) throws RemoteException;

}
