package server.residencenode;

public class ResidenceNode {
	
	private String [][][] programs;
	private int numDays, numSessions, maxSpeakers;
	private int [][] slots;
	
	public ResidenceNode(int numDays, int numSessions, int maxSpeakers) {
		
		programs = new String [numDays][numSessions][maxSpeakers];
		slots = new int [numDays][numSessions];
		
		int i = 0, j = 0, k = 0;
		
		for (; i<numDays; i++)
			for (; j<numSessions; j++)
				for (; k<maxSpeakers; k++)
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
		return true;
	}

	public boolean removeSpeaker(String name, int day, int session) {
		if (invalidDay(day) || invalidSession(session))
			return false;
		if (isBusy(slots[day-1][session-1]))
			return false;
		if (programs[day-1][session-1][slots[day-1][session-1]] != name)
			return false;
		programs[day-1][session-1][slots[day-1][session-1]] = "-";
		slots[day-1][session-1] -= 1;
		return true;
	}
	
	private boolean isBusy(int i) {
		return i < maxSpeakers-1;
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
	
}
