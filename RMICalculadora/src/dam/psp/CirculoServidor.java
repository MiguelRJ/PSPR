package dam.psp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CirculoServidor implements ICirculo {
	
	private final double PI = Math.PI;
	private double _radio;

	@Override
	public void set_radio(double _radio) throws RemoteException {
		this._radio = _radio;
	}

	@Override
	public double area() throws RemoteException {
		return PI * _radio * _radio;
	}

	@Override
	public double longitud() throws RemoteException {
		return 2 * PI * _radio;
	}
	
	public CirculoServidor(Registry registro) {
		System.out.println("Creando objeto Circulo y su inscripcion en el registro");
		try {
			registro.bind("Circulo", (ICirculo) UnicastRemoteObject.exportObject(this, 0)); 
			// Publicito que dispongo de objeto al que se puede llamar, 0 exporta en el primer puerto disponible
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) throws RemoteException {
		final int puerto = 8888;
		
		System.setProperty("java.rmi.server.hostname", "192.168.3.32"); // pasar a la maquina de java
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		Registry registry = LocateRegistry.createRegistry(puerto);
		new CirculoServidor(registry);
	}

}
