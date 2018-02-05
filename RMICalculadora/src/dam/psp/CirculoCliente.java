package dam.psp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CirculoCliente {
	
	public static final String servidor = "192.168.3.57";
	public static final int puerto = 8888; 

	public static void main(String[] args) throws RemoteException {
		
		ICirculo circulo = null;
		
		System.out.println("localizando en la red el objeto remoto...");
		
		Registry registry = LocateRegistry.getRegistry(servidor, puerto); // throws RemoteException
		
		try {
			System.out.println("Obteniendo el falso objeto <stub> del remoto");
			circulo = (ICirculo) registry.lookup("Circulo");
			if (circulo != null) {
				circulo.set_radio(20);
				System.out.println("Longitud circunferencia: " + circulo.longitud());
				System.out.println("Area circunferencia: " + circulo.area());
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

}
