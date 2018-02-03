package dam.psp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente {
	
	public static final int PuertoEmisor = 5555;
	public static final int PuertoReceptor = 4444;
	public static final String IPEmisor = "192.168.3.32"; // 3.57  // 3.32 // 2.27
	
	public Cliente() {
		
		try {
			ICalculadora calculadora = (ICalculadora) Naming.lookup("//localhost/calc");
			System.out.println(calculadora.suma(5, 6));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new Cliente();
	}

}
