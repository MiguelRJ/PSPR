package com.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunProcessConectados2 {
	
	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("Se necesita como argumento un proceso a ejecutar");
			System.exit(-1);
		}
		
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.redirectErrorStream(true);
		
		try {
			Process proceso = pb.start(); // echa a correr niño 
			MostrarSalidaProceso(proceso);
			System.exit(0);
			
		}catch (IOException e) {
			System.err.println("Error de E/S");
			System.exit(-1);
		}
	}
	
	private static void MostrarSalidaProceso(Process proceso) {
		try {
			int num = 5;
			//int retorno = proceso.waitFor(); // has terminado de correr?
			//System.out.println("El proces hijo ha devuelto: " + retorno);
			
			InputStreamReader lector = new InputStreamReader(proceso.getInputStream(), "utf-8");
			BufferedReader br = new BufferedReader(lector);
			
			String linea;
			while( ((linea = br.readLine()) != null) && num!=0) {
				System.out.println(linea);
				num--;
			}
			
			if (proceso != null) {
				proceso.destroy();
				try {
					proceso.waitFor();
				}catch(InterruptedException e){
					System.out.println("No se pudo esperar al final del proceso hijo");
				}
				System.out.println("Salida del proceso hijo: " + proceso.exitValue());
				System.exit(0);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
/* Maim---
 * 
 * Process proceso = null;
 * try {
 * 		proceso = new ProcessBuilder("ping", args[0]).start();
 * 		BufferedReader in = new BufferedReader(
 * 				new InputStreamReader(proceso.getInputStream()));
 * 		for (int i = 0; 1<5; i++) {
 * 			System.out.println(in.readLine());
 * 		}
 * 		if (proceso != null) {
 * 			proceso.destroy();
 * 		}
 * 		}catch (IOexception e){
 * 			e.printStackTrace();
		}
	}
*/
