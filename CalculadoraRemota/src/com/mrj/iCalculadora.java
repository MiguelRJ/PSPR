package com.mrj;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iCalculadora extends Remote {
	public float suma(float a, float b) throws RemoteException;
	public float resta(float a, float b) throws RemoteException;
	public float producto(float a, float b) throws RemoteException;
	public float division(float a, float b) throws RemoteException;
	public float raiz(float a) throws RemoteException;
	public float potencia(float a, float b) throws RemoteException;
	public float siguientePrimo(float a) throws RemoteException;
}
