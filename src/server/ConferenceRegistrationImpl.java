package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.residencenode.ResidenceNode;

/**
 * Class representing the logic of the server. The contained methods are the
 * ones of the interface, the documentation is reported there.
 * 
 * @author graziaferrara
 *
 */
public class ConferenceRegistrationImpl extends UnicastRemoteObject implements common.ConferenceRegistration {

	private static final long serialVersionUID = 1L;
	private ResidenceNode residenceNode;

	/**
	 * Creates an instance of the server.
	 * @param numDays
	 * @param numSessions
	 * @param maxSpeakers
	 * @throws RemoteException
	 */
	public ConferenceRegistrationImpl(int numDays, int numSessions, int maxSpeakers) throws RemoteException {
		super();
		this.residenceNode = new ResidenceNode(numDays, numSessions, maxSpeakers);
	}

	@Override
	public boolean register(String name, int day, int session) throws RemoteException {
		return residenceNode.insertSpeaker(name.strip(), day, session);
	}

	@Override
	public String[][][] getInformation() throws RemoteException {
		return residenceNode.getPrograms();
	}

	@Override
	public String[][] getInformationByDay(int day) throws RemoteException {
		return residenceNode.getPrograms()[day - 1];
	}

	@Override
	public boolean cancelRegistration(int day, int session, int intervention) throws RemoteException {
		return residenceNode.removeSpeaker(day, session, intervention);
	}

	@Override
	public int getDays() throws RemoteException {
		return residenceNode.getNumDays();
	}

	@Override
	public int getSessions() throws RemoteException {
		return residenceNode.getNumSessions();
	}

	@Override
	public int getMaxSpeakers() throws RemoteException {
		return residenceNode.getMaxSpeakers();
	}

	/**
	 * Creates a registry and binds a remote object.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.createRegistry(1099);
			ConferenceRegistrationImpl cr = new ConferenceRegistrationImpl(3, 12, 5);
			reg.rebind("rmi://localhost/CRServer", cr);

		} catch (RemoteException e) {
			System.out.println("Error creating the server!");
		}
	}

}
