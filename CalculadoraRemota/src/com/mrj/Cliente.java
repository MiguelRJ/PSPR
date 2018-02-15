package com.mrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
	
	static final String HOST = "192.168.1.132";  // casa -> 1.132; profe -> 3.57; clase -> 3.32
	static final int PUERTO = 8888;
	
	static final String salida = "q";
	static final String RESULTADO = "Resultado operacion";
	static final String ERROR = "ERROR";

	String mensajeTeclado;
	String mensajeServer;
	
	public Cliente() {
		
		try {
			
			Socket skCli = new Socket(HOST,PUERTO);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(skCli.getOutputStream(),"utf-8"), true); // escribir socket
			BufferedReader br = new BufferedReader(new InputStreamReader(skCli.getInputStream(),"utf-8"));
			System.out.println(br.readLine()); // recibir bienvenida
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in,"utf-8"));
			
			do {
				
				System.out.print("\nIndique operacion a realizar: ");
				mensajeTeclado = teclado.readLine();
				pw.println(mensajeTeclado); // enviar opcion a calcular
				
				if (!mensajeTeclado.equals(salida)) {
					
					do {
						
						mensajeServer = br.readLine(); // recibir instruncion a realizar desde el servidor
						
						if (mensajeServer.contains(RESULTADO) || mensajeServer.contains(ERROR)) {
							
							System.out.println(mensajeServer);
							
						} else {
							
							System.out.println(mensajeServer);
							String respuestaServer = teclado.readLine(); // enviar algo al servere que ha pedido
							pw.println(respuestaServer);
							
						}
						
					} while (!mensajeServer.contains(RESULTADO) && ! mensajeServer.contains(ERROR));
					
				}
				
			} while (!mensajeTeclado.equals(salida));
			
			System.out.println("Desconectado");
			skCli.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Cliente();	
	}
}