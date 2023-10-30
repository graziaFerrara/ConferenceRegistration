package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConferenceRegistrationClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 1099);
			common.ConferenceRegistration cr = (common.ConferenceRegistration) reg.lookup("rmi://localhost/CRServer");
			while(true) {
				
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
