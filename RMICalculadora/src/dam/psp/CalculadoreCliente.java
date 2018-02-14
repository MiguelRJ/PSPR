package dam.psp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculadoreCliente {

	public static final String servidor = "192.168.1.132";  // casa -> 1.132; profe -> 3.57; clase -> 3.32
	public static final int puerto = 8889;

	public static void main(String[] args) throws RemoteException {

		ICalculadora calculadora = null;

		System.out.println("localizando en la red el objeto remoto...");

		Registry registry = LocateRegistry.getRegistry(servidor, puerto); // throws RemoteException

		try {
			System.out.println("Obteniendo el falso objeto <stub> del remoto");
			calculadora = (ICalculadora) registry.lookup("Calculadora");
			if (calculadora != null) {
				System.out.println("Suma 5 y 6: " + calculadora.suma(5, 6));
				System.out.println("resta 5 y 6: " + calculadora.resta(5, 6));
				System.out.println("producto 5 y 6: " + calculadora.producto(5, 6));
				System.out.println("division 5 y 6: " + calculadora.division(5, 6));
			}
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

}
