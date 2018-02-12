package com.mrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	static final String HOST = "192.168.1.132";  // casa -> 1.132; profe -> 3.57; clase -> 3.32
	static final int PUERTO = 8888;
	Scanner entrada;
	
	public Cliente() {
		try {
			Socket skCli = new Socket(HOST,PUERTO);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(skCli.getOutputStream(),"utf-8"), true); // escribir socket
			
			BufferedReader br = new BufferedReader(new InputStreamReader(skCli.getInputStream(),"utf-8"));
			System.out.println(br.readLine()); // Recibimos el saludo desde el servidor
			
			// enviar mensaje pedido por consola al servidor
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in,"utf-8"));
			System.out.println("Mensaje para enviar?");
			String mensaje = teclado.readLine();
			System.out.println("Enviando mensaje al servidor el mensaje: "+mensaje);
			pw.println(mensaje);
			
			skCli.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Cliente();	
	}
}