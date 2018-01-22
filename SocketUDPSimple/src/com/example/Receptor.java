package com.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Receptor {
	
	public static final int Puerto = 4444;
	public static final String IPEmisor = "0.0.0.0";
	DatagramSocket socket;
	
	public Receptor() {
		
		try {
			
			socket = new DatagramSocket(Puerto, InetAddress.getByName(IPEmisor)); // con ip 0.0.0.0 usa otra predeterminada
			
			
			System.out.println("Emisor conectado al socket: "+socket.getLocalAddress());
			
			while (true) {
				DatagramPacket dato = new DatagramPacket(new byte[144], 144); // no usar numeros magicos
				// Se recibe un dato y se escribe en pantalla 
				socket.receive(dato); // Esto es bloqueante no avanza hasta que no lleguem datos
				System.out.println("\nRecibo un paquete de "+dato.getAddress().getHostName()+": ");
				byte[] contenido = dato.getData();
				System.out.println("de longitud: "+dato.getLength());
				
				System.out.println(new String(contenido)); // Deserializamos a String
				
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) { // objeto ipv4 no esta bien costruido, el host no es valido
			e.printStackTrace();
		} catch (IOException e) { // socket.receive(dato);
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		
	}

	public static void main(String[] args) {
		new Receptor();
	}

}
