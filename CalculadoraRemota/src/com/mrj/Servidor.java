package com.mrj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
	
	//Djava.net.preferIpv4Stack=true
	
	static final int PUERTO = 8888;
	
	public static void main(String[] args) throws RemoteException {
		final int puertoCalc = 8889;
		
		System.setProperty("java.rmi.server.hostname", "192.168.1.132");  // casa -> 1.132; profe -> 3.57; clase -> 3.32
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		Registry registry = LocateRegistry.createRegistry(puertoCalc);
		new CalculadoraServidor(registry);
		new Servidor();
	}
	
	public Servidor () {
		
		ServerSocket skSRV;
		
		try {
			skSRV = new ServerSocket(PUERTO);
			System.out.println("Servidor escuchando en: "+skSRV.getLocalSocketAddress().toString());
			int nCli = 0;
			
			while (true) {
				Socket skAtencion = skSRV.accept();
				nCli++;
				System.out.println("Atendiendo nuevo cliente: "+nCli);
				
				// Creamos un hilo para atender al cliente y asi liberar al socket principal
				new ServidorHilo(skAtencion,nCli).start();;
			}
			
		} catch (IOException e) {
			System.setProperty("java.net.preferIPv4Stack" , "true");
			e.printStackTrace();
		}
		
	}

}
