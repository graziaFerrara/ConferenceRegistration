package server.residencenode;

public class Registration {
	
	private int day, session;
	
	public Registration (int day, int session) {
		this.day = day;
		this.session = session;
	}

	public int getDay() {
		return day;
	}

	public int getSession() {
		return session;
	}

}
