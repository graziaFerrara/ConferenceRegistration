package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConferenceRegistration extends Remote{
	
	public boolean register(String name, int day, int session) throws RemoteException;
	
	public String [][][] getInformation() throws RemoteException;
	
	public boolean cancelRegistration(String name, int day, int session) throws RemoteException;

}
