package dam.psp;

import java.net.DatagramSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
	
	public static final int Puerto = 4444;
	public static final String IPEmisor = "0.0.0.0";
	DatagramSocket socket;
	
	public Servidor() {
		
		
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("calc", new CalculadoraServidor());
		} catch (Exception e) {
			System.out.println("server not connected " + e);
		}
		

		
	}

	public static void main(String[] args) {
		new Servidor();
	}

}