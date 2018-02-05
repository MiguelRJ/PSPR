package dam.psp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraServidor implements ICalculadora {

	@Override
	public float suma(float a, float b) throws RemoteException {
		return a+b;
	}

	@Override
	public float resta(float a, float b) throws RemoteException {
		return a-b;
	}

	@Override
	public float producto(float a, float b) throws RemoteException {
		return a*b;
	}

	@Override
	public float division(float a, float b) throws RemoteException {
		if (b == 0) {
			throw new RemoteException("Division por cero, atontao");
		} else {
			return a/b;
		}
	}
	
	public CalculadoraServidor(Registry registro) {
		System.out.println("Creando objeto Circulo y su inscripcion en el registro");
			
		try {
			registro.bind("Calculadora", (ICalculadora) UnicastRemoteObject.exportObject(this, 0));
			// Publicito que dispongo de objeto al que se puede llamar, 0 exporta en el primer puerto disponible
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) throws RemoteException {
		final int puerto = 8889;
		
		System.setProperty("java.rmi.server.hostname", "192.168.3.32"); // pasar a la maquina de java
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		Registry registry = LocateRegistry.createRegistry(puerto);
		new CalculadoraServidor(registry);
	}

}
