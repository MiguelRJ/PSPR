package dam.psp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraServidor extends UnicastRemoteObject implements ICalculadora {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CalculadoraServidor() throws RemoteException {
		
	}

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
		return a/b;
	}

}
