package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.residencenode.ResidenceNode;

public class ConferenceRegistrationImpl extends UnicastRemoteObject implements common.ConferenceRegistration{

	private static final long serialVersionUID = 1L;
	private ResidenceNode residenceNode;

	public ConferenceRegistrationImpl(int numDays, int numSessions, int maxSpeakers) throws RemoteException {
		super();
		this.residenceNode = new ResidenceNode(numDays, numSessions, maxSpeakers);
	}

	@Override
	public boolean register(String name, int day, int session) throws RemoteException {
		return residenceNode.insertSpeaker(name, day, session);
	}

	@Override
	public String [][][] getInformation() throws RemoteException {
		return residenceNode.getPrograms();
	}

	@Override
	public boolean cancelRegistration(String name, int day, int session) throws RemoteException {
		return residenceNode.removeSpeaker(name, day, session);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Registry reg = LocateRegistry.createRegistry(1099);
			common.ConferenceRegistration cr = new ConferenceRegistrationImpl(3, 12, 5);
			reg.rebind("rmi://localhost/CRServer", cr);
			System.out.println("Server is running...");
			
		} catch (RemoteException e) {
			System.out.println("Error creating the server!");
		}
	}

}
