package client;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ConferenceRegistrationClient {
	
	private Scanner scanner;
	
	public ConferenceRegistrationClient() {
		scanner = new Scanner(System.in);
	}
	
	private boolean chooseOperation(common.ConferenceRegistration cr) {
		System.out.print("What operation do you want to do? (sign, info, unsign, exit) ");
		String ans = scanner.nextLine();
		switch(ans.toLowerCase().strip()) {
		case "sign":
			sign(cr);
			break;
		case "info":
			info(cr);
			break;
		case "unsign":
			unsign(cr);
			break;
		case "exit":
			return true;
		default:
			System.out.println("Invalid operation. Try again!");
			break;
		}
		return false;
	}
	
	private void sign(common.ConferenceRegistration cr) {
		
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
				if (validDay(day,numDays)) {
					System.out.println("Session");
					session = Integer.parseInt(scanner.nextLine());
					if(validSession(session, numSessions)) {
						System.out.println("Speaker name?");
						speakerName = scanner.nextLine();
						try {
							complete = cr.register(speakerName, day, session);
						} catch (RemoteException e) {
							
						}
						if(!complete)
							System.out.println("Not available session, try again!");
					}else {
						System.out.println("Invalid session, try again!");
					}
				}else {
					System.out.println("Invalid day, try again!");
				}
				
			}catch(NumberFormatException e) {
				System.out.println("Invalid fields, try again!");
			}
		}
		
		
		} catch (RemoteException e1) {
			
		}
		
	}
	
	private boolean validSession(int session, int numSessions) {
		if(session >= 1 && session <= numSessions)
			return true;
		return false;
	}

	private boolean validDay(int day, int numDays) {
		if(day >= 1 && day <= numDays)
			return true;
		return false;
	}
	
	private boolean validIntervention(int intervention, int maxSpeakers) {
		if (intervention >= 1 && intervention <= maxSpeakers)
			return true;
		return false;
	}

	private void info(common.ConferenceRegistration cr) {
		try {
			String [][][] information = cr.getInformation();
			int numDays = cr.getDays(), numSessions = cr.getSessions(),
					maxSpeakers = cr.getMaxSpeakers(), day;
			
			boolean complete = false;
			while (!complete) {
				System.out.println("Day?");
				try {
					day = Integer.parseInt(scanner.nextLine());
					if (validDay(day, numDays)) {
						System.out.println("Program for the day " + day + " of the congress:");
						for (int j=0; j<numSessions; j++) {
							System.out.println("S" + (j+1) + " session:");
							for(int k=0; k<maxSpeakers; k++) {
								System.out.println("Intervention " + (k+1) + ": " + information[day-1][j][k]);
							}
						}
						complete = true;
					}else {
						System.out.println("Invalid day, retry!");
					}
					
				}catch(NumberFormatException e) {
					System.out.println("Invalid number, try again!");
				}
			}
						
		} catch (RemoteException e) {
			System.out.println("Cannot get information. Retry!");
		}
	}
	
	private void unsign(common.ConferenceRegistration cr) {

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
				if (validDay(day,numDays)) {
					System.out.println("Session");
					session = Integer.parseInt(scanner.nextLine());
					if(validSession(session, numSessions)) {
						System.out.println("Intervention?");
						intervention = Integer.parseInt(scanner.nextLine());
						if (validIntervention(intervention, maxSpeakers)) {
							try {
								complete = cr.cancelRegistration(day, session, intervention);
							} catch (RemoteException e) {
								
							}
							if(!complete)
								System.out.println("Not valid intervention, try again!");
						}
					}else {
						System.out.println("Invalid session, try again!");
					}
				}else {
					System.out.println("Invalid day, try again!");
				}
				
			}catch(NumberFormatException e) {
				System.out.println("Invalid fields, try again!");
			}
		}
		
		
		} catch (RemoteException e1) {
			
		}
		
	}

	public static void main(String[] args) {

		ConferenceRegistrationClient crc = new ConferenceRegistrationClient();
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 1099);
			common.ConferenceRegistration cr = (common.ConferenceRegistration) reg.lookup("rmi://localhost/CRServer");
			boolean exit = false;
			while(!exit) {
				exit = crc.chooseOperation(cr);
			}
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Cannot connect to the remote.");
		}
		System.out.println("Exited");
	}

}
