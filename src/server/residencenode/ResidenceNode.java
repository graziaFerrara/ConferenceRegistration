package server.residencenode;

public class ResidenceNode {
	
	private String [][][] programs;
	private int numDays, numSessions, maxSpeakers;
	private int [][] slots;
	
	public ResidenceNode(int numDays, int numSessions, int maxSpeakers) {
		
		programs = new String [numDays][numSessions][maxSpeakers];
		slots = new int [numDays][numSessions];
		
		int i, j, k;
		
		for (i=0; i<numDays; i++)
			for (j=0; j<numSessions; j++)
				for (k=0; k<maxSpeakers; k++)
					programs[i][j][k] = "-";
		
		for (i=0; i<numDays; i++)
			for (j=0; j<numSessions; j++)
				slots[i][j] = 0;
		
		this.numDays = numDays;
		this.numSessions = numSessions;
		this.maxSpeakers = maxSpeakers;
		
	}

	public String [][][] getPrograms() {
		return programs;
	}

	public int getNumDays() {
		return numDays;
	}

	public int getNumSessions() {
		return numSessions;
	}

	public int getMaxSpeakers() {
		return maxSpeakers;
	}

	public boolean insertSpeaker(String name, int day, int session) {
		if (invalidDay(day) || invalidSession(session))
			return false;
		if (isBusy(slots[day-1][session-1]))
			return false;
		int intervention = searchAvailableSlot(day, session);
		if (intervention == -1)
			return false;
		programs[day-1][session-1][intervention] = name;
		slots[day-1][session-1] += 1;
		printPrograms();
		return true;
	}

	public boolean removeSpeaker(int day, int session, int intervention) {
		if (invalidDay(day) || invalidSession(session)) {
			return false;
		}
		if (isBusy(slots[day-1][session-1])) {
			return false;
		}
		System.out.println(programs[day-1][session-1][intervention-1]);
		if (programs[day-1][session-1][intervention-1].equals("-")) {
			return false;
		}
		
		programs[day-1][session-1][slots[day-1][session-1]-1] = "-";
		slots[day-1][session-1] -= 1;
		printPrograms();
		return true;
	}
	
	private boolean isBusy(int i) {
		return i >= maxSpeakers;
	}
	
	private boolean invalidSession(int session) {
		if (session < 1 || session > numSessions)
			return true;
		return false;
	}

	private boolean invalidDay(int day) {
		if (day < 1 || day > numDays)
			return true;
		return false;
	}
	
	private int searchAvailableSlot(int day, int session) {
		String [] sessionArray = programs[day-1][session-1];
		for (int i=0; i<maxSpeakers; i++)
			if (sessionArray[i] == "-")
				return i;
		return -1;
	}
	
	private void printPrograms() {
		
		int i,j,k;
		
		for(i=0; i<numDays; i++) {
			System.out.println("Day "+i);
			for(j=0; j<numSessions; j++) {
				System.out.print("S"+(j+1)+" ");
				for(k=0; k<maxSpeakers; k++) {
					System.out.print(programs[i][j][k] + " ");
				}
				System.out.print("\n");
			}
			
		}
		
	}
	
}
