package com.mrj;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraServidor implements iCalculadora {

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
	
	@Override
	public float raiz(float a) throws RemoteException {
		return (float) Math.sqrt(a);
	}

	@Override
	public float potencia(float a, float b) throws RemoteException {
		return (float) Math.pow(a, b);
	}

	@Override
	public float siguientePrimo(float a) throws RemoteException {
		boolean isPrimo = true;
        float num = a;
        if (num<2) {
            return 2;
        }
        do {
            isPrimo = true;
            num++;
            for (int i = 2; i<num ; i++) {
                if (num%i==0) {
                    isPrimo = false;
                    break;
                }
            }
        } while(!isPrimo && num > a );
        return num;
	}
	
	public CalculadoraServidor(Registry registro) {
		System.out.println("Creando objeto Circulo y su inscripcion en el registro");
			
		try {
			registro.bind("Calculadora", (iCalculadora) UnicastRemoteObject.exportObject(this, 0));
			// Publicito que dispongo de objeto al que se puede llamar, 0 exporta en el primer puerto disponible
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) throws RemoteException {
		final int puerto = 8889;
		
		System.setProperty("java.rmi.server.hostname", "192.168.1.132");  // casa -> 1.132; profe -> 3.57; clase -> 3.32
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		Registry registry = LocateRegistry.createRegistry(puerto);
		new CalculadoraServidor(registry);
	}


}
