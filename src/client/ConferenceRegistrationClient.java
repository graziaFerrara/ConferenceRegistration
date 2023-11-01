package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import common.ConferenceRegistration;

/**
 * Class representing the logic of the Client allowing the user to interact with
 * a conference registration server.
 * 
 * @author graziaferrara
 *
 */
public class ConferenceRegistrationClient {

	private Scanner scanner;
	private ConferenceRegistration cr;

	/**
	 * Creates an instance of the client, instantiating a Scanner to read the user's
	 * input.
	 * 
	 * @param cr
	 */
	public ConferenceRegistrationClient(ConferenceRegistration cr) {
		scanner = new Scanner(System.in);
		this.cr = cr;

	}

	/**
	 * Allows the user to choose which operation he wants to do.
	 * 
	 * @return
	 */
	private boolean chooseOperation() {
		System.out.print("What operation do you want to do? (sign, info, unsign, exit) ");
		String ans = scanner.nextLine();
		switch (ans.toLowerCase().strip()) {
		case "sign":
			sign();
			break;
		case "info":
			info();
			break;
		case "unsign":
			unsign();
			break;
		case "exit":
			return true;
		default:
			System.out.println("Invalid operation. Try again!");
			break;
		}
		return false;
	}

	/**
	 * Allows the user to ask for the registration to a given session of a given day
	 * of the conference.
	 */
	private void sign() {

		int numDays, numSessions, day, session;
		try {
			numDays = cr.getDays();
			numSessions = cr.getSessions();

			String speakerName;

			boolean complete = false;
			while (!complete) {
				System.out.println("Day?");
				try {
					day = Integer.parseInt(scanner.nextLine());
					if (validDay(day, numDays)) {
						System.out.println("Session?");
						session = Integer.parseInt(scanner.nextLine());
						if (validSession(session, numSessions)) {
							System.out.println("Speaker name?");
							speakerName = scanner.nextLine();
							try {
								complete = cr.register(speakerName, day, session);
							} catch (RemoteException e) {
								System.out.println("Cannot register. Retry!");
							}
							if (!complete)
								System.out.println("Not available session, try again!");
						} else {
							System.out.println("Invalid session, try again!");
						}
					} else {
						System.out.println("Invalid day, try again!");
					}

				} catch (NumberFormatException e) {
					System.out.println("Invalid fields, try again!");
				}
			}

		} catch (RemoteException e1) {
			System.out.println("Cannot register. Retry!");
		}

	}

	/**
	 * Allows the user to get information about the congress' program of a given
	 * day.
	 */
	private void info() {
		try {
			int numDays = cr.getDays(), numSessions = cr.getSessions(), maxSpeakers = cr.getMaxSpeakers(), day;
			String[][] information;
			boolean complete = false;
			while (!complete) {
				System.out.println("Day?");
				try {
					day = Integer.parseInt(scanner.nextLine());
					if (validDay(day, numDays)) {
						System.out.println("Program for the day " + day + " of the congress:");
						information = cr.getInformationByDay(day);
						for (int j = 0; j < numSessions; j++) {
							System.out.println("S" + (j + 1) + " session:");
							for (int k = 0; k < maxSpeakers; k++) {
								System.out.println("Intervention " + (k + 1) + ": " + information[j][k]);
							}
						}
						complete = true;
					} else {
						System.out.println("Invalid day, retry!");
					}

				} catch (NumberFormatException e) {
					System.out.println("Invalid number, try again!");
				}
			}

		} catch (RemoteException e) {
			System.out.println("Cannot get information. Retry!");
		}
	}

	/**
	 * Allows the user to cancel its subscription to an intervention of a given
	 * session of a given day of the conference.
	 */
	private void unsign() {

		int numDays, numSessions, maxSpeakers, day, session, intervention;
		try {
			numDays = cr.getDays();
			numSessions = cr.getSessions();
			maxSpeakers = cr.getMaxSpeakers();

			boolean complete = false;
			while (!complete) {
				System.out.println("Day?");
				try {
					day = Integer.parseInt(scanner.nextLine());
					if (validDay(day, numDays)) {
						System.out.println("Session?");
						session = Integer.parseInt(scanner.nextLine());
						if (validSession(session, numSessions)) {
							System.out.println("Intervention?");
							intervention = Integer.parseInt(scanner.nextLine());
							if (validIntervention(intervention, maxSpeakers)) {
								try {
									complete = cr.cancelRegistration(day, session, intervention);
								} catch (RemoteException e) {
									System.out.println("Cannot cancel the registration. Retry!");
								}
								if (!complete)
									System.out.println("Not valid intervention, try again!");
							} else {
								System.out.println("Invalid intervention, try again!");
							}
						} else {
							System.out.println("Invalid session, try again!");
						}
					} else {
						System.out.println("Invalid day, try again!");
					}

				} catch (NumberFormatException e) {
					System.out.println("Invalid fields, try again!");
				}
			}

		} catch (RemoteException e1) {
			System.out.println("Cannot cancel the registration. Retry!");
		}

	}

	/**
	 * Utility method to check the valifity of a session.s
	 */
	private boolean validSession(int session, int numSessions) {
		if (session >= 1 && session <= numSessions)
			return true;
		return false;
	}

	/**
	 * Utilty method to check the validity of a day.
	 * 
	 * @param day
	 * @param numDays
	 * @return
	 */
	private boolean validDay(int day, int numDays) {
		if (day >= 1 && day <= numDays)
			return true;
		return false;
	}

	/**
	 * Utility method to check the validity of a intervention.
	 * 
	 * @param intervention
	 * @param maxSpeakers
	 * @return
	 */
	private boolean validIntervention(int intervention, int maxSpeakers) {
		if (intervention >= 1 && intervention <= maxSpeakers)
			return true;
		return false;
	}

	/**
	 * Main method allowing the user to ask for operations' execution until he
	 * decides to quit.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 1099);
			common.ConferenceRegistration cr = (common.ConferenceRegistration) reg.lookup("rmi://localhost/CRServer");
			ConferenceRegistrationClient crc = new ConferenceRegistrationClient(cr);
			boolean exit = false;
			while (!exit)
				exit = crc.chooseOperation();
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Cannot connect to the remote.");
		}
		System.out.println("Exited");
	}

}
