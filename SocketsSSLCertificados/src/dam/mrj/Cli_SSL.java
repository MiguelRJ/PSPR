package dam.mrj;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cli_SSL {
	static final String DESTINO = "localhost";
	static final int PUERTO = 5555; // puerto destino del servidor al que conectar
	
	public Cli_SSL(String mensaje) throws UnknownHostException, IOException {
		System.out.println("Obteniendo factoria de socket del cliente...");
		SSLSocketFactory socketCLIFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		
		System.out.println("Creando el socket del cliente...");
		SSLSocket socketCLI = (SSLSocket) socketCLIFactory.createSocket(DESTINO, PUERTO);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Cli_SSL("Hola mundo!");
	}
}
