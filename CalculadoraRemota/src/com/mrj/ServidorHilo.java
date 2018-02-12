package com.mrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServidorHilo extends Thread {
	
	private Socket socket;
	private int idCliente;
	String bienvenida;
	String instrucciones;

	public ServidorHilo(Socket socket, int idCliente) {	
		this.socket = socket;
		this.idCliente = idCliente;
		this.bienvenida = "Binvenido/a a mi canal";
		this.instrucciones = "Opciones a calcular: 1.suma 2.resta";
	}
	
	@Override
	public void run() {
		//BufferedOutputStream bo;
		//BufferedInputStream is;
		BufferedReader br;
		PrintWriter pw = null;
		
		try {
			//bo = new BufferedOutputStream(elSocket.getOutputStream());
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true); // true limpia el buffer para que el canal de comunicacion quede limpio
			pw.println(idCliente +":"+ this.bienvenida+this.instrucciones);
			
			// y ahora espera una respuesta en forma de string desde el cliente
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			System.out.print("Mensaje recibido desde el cliente "+idCliente+": ");
			System.out.println(br.readLine());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (pw != null) {
			pw.flush(); // Nunca deberia hacer falta esto 
			pw.close();
		}
	}
}
