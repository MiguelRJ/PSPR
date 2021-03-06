package com.psp;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;

public class RunProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// La clase recibe como parametro el nombre del proceso a lanzar desde la maquina virtual
		if (args.length <= 0) {
			System.err.println("Falta el nombre del ejecutable");
			System.exit(-1);
		}
		
		ProcessBuilder pb = new ProcessBuilder(args);
		
		String pid; // El PID del proceso dado como argumento
		pid = ManagementFactory.getRuntimeMXBean().getName();
		
		System.out.println("El PId del padre es " + pid);
		
		try {
			Process proceso = pb.start();
			int retorno = proceso.waitFor();
			System.out.println("La ejecucion de " + Arrays.toString(args) + " devuelve " + retorno);
		} catch (IOException | InterruptedException e) {
			System.err.println("Excepcion de E/S o proceso interrumpido");
		}
		
	}

}
