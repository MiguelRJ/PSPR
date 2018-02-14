package com.mrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sun.javafx.geom.transform.BaseTransform;

public class ServidorHilo extends Thread {
	
	private Socket socket;
	private int idCliente;
	String bienvenida;
	String instrucciones;
	static final String HOST = "192.168.1.132";  // casa -> 1.132; profe -> 3.57; clase -> 3.32
	static final int PUERTOCALC = 8889;
	iCalculadora calculadora = null;
	
	static final String SUMA = "s";
	static final String RESTA = "r";
	static final String PRODUCTO = "p";
	static final String DIVISION = "d";
	static final String RAIZ = "ra";
	static final String POTENCIA = "po";
	static final String SIGUIENTEPRIMO = "sp";
	float a = 0;
	float b = 0;

	public ServidorHilo(Socket socket, int idCliente) {	
		this.socket = socket;
		this.idCliente = idCliente;
		this.bienvenida = "Binvenido/a a mi canal";
		this.instrucciones = "Opciones a calcular: "
				+ "-Suma("+SUMA+") -Resta("+RESTA+") -Producto("+PRODUCTO+") -Division("+DIVISION+") "
						+ "-Raiz("+RAIZ+") -Potencia("+POTENCIA+") -SiguientePrimo("+SIGUIENTEPRIMO+")";
	}
	
	@Override
	public void run() {
		//BufferedOutputStream bo;
		//BufferedInputStream is;
		BufferedReader br;
		PrintWriter pw = null;
		Registry registry = null;

		try {
			registry = LocateRegistry.getRegistry(HOST, PUERTOCALC);
			calculadora = (iCalculadora) registry.lookup("Calculadora");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		try {
			//bo = new BufferedOutputStream(elSocket.getOutputStream());
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true); // true limpia el buffer para que el canal de comunicacion quede limpio
			pw.println("Cliente " +idCliente +","+ this.bienvenida+". "+this.instrucciones);
			
			// y ahora espera una respuesta en forma de string desde el cliente
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			String mensaje = br.readLine();
			System.out.print("cliente "+idCliente+" quiere: "+mensaje);
			
			if (mensaje.equals(SUMA)) {
				pw.print("Dime primer operador: ");
				a = Float.parseFloat(br.readLine());
				pw.print("Dime segundo operador: ");
				b = Float.parseFloat(br.readLine());
				pw.println("Resultado de "+a+" + "+b+" = "+calculadora.suma(a, b));
			}
			
			//System.out.println(br.readLine()); // muestra el mensaje del cliente
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (pw != null) {
			pw.flush(); // Nunca deberia hacer falta esto 
			pw.close();
		}
	}
}
