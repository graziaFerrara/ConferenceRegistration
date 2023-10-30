package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConferenceRegistration extends Remote{
	
	public boolean register(String name, int day, int session) throws RemoteException;
	
	public String [][][] getInformation() throws RemoteException;
	
	public int getDays() throws RemoteException;
	
	public int getSessions() throws RemoteException;
	
	public int getMaxSpeakers() throws RemoteException;
	
	public boolean cancelRegistration(int day, int session, int intervention) throws RemoteException;

}
