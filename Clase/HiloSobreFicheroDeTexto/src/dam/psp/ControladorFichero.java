package dam.psp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ControladorFichero {
	private PrintWriter fichero;
	
	public ControladorFichero(String nombreFichero) {
		try {
			fichero = new PrintWriter(new FileWriter(nombreFichero));
		} catch (IOException e) {
			System.err.println("Error al crear el fichero: " + e.getMessage());
		}
	}
	
	public synchronized void print(String cadena) {
		for (int i = 0; i < cadena.length(); i++) {
			fichero.print(cadena.charAt(i));
		}
	}
	
	public synchronized void println(String cadena) {
		this.print(cadena);
		fichero.println();
	}
	
	public void close() {
		fichero.close();
	}
}
