package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface provided by the server.
 * 
 * @author graziaferrara
 *
 */
public interface ConferenceRegistration extends Remote {

	/**
	 * Allows the client to ask for a registration to a given session of a given
	 * day, with the specified name.
	 * 
	 * @param name
	 * @param day
	 * @param session
	 * @return
	 * @throws RemoteException
	 */
	public boolean register(String name, int day, int session) throws RemoteException;

	/**
	 * Allows the client to get the information about all the days of the congress.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String[][][] getInformation() throws RemoteException;

	/**
	 * Allows the client to get the information about a given day of the congress.
	 * 
	 * @param day
	 * @return
	 * @throws RemoteException
	 */
	public String[][] getInformationByDay(int day) throws RemoteException;

	/**
	 * Allows the client to get the number of days which the congress lasts for.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getDays() throws RemoteException;

	/**
	 * Allows the client to get the number of session which each day of the congress
	 * provides.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getSessions() throws RemoteException;

	/**
	 * Allows the client to get the maximum number of speakers of a session of the
	 * congress.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getMaxSpeakers() throws RemoteException;

	/**
	 * Allows the client to ask for the cancelation of its intervention in a session
	 * of a given day of the congress.
	 * 
	 * @param day
	 * @param session
	 * @param intervention
	 * @return
	 * @throws RemoteException
	 */
	public boolean cancelRegistration(int day, int session, int intervention) throws RemoteException;

}
