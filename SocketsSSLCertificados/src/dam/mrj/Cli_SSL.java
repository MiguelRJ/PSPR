package dam.mrj;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cli_SSL {
	static final String DESTINO = "localhost";
	static final int PUERTO = 5555; // puerto destino del servidor al que conectar
	
	private void mostrarCifrado(SSLSocket socket) {
		String[] protocolos = socket.getEnabledProtocols();
		System.out.println("Protocolos habilitados: ");
		for (String protocolo : protocolos) {
			System.out.println(protocolo+", ");
		}
		
		System.out.println("Protocolos soportados: ");
		String[] protocolosSoportados = socket.getSupportedProtocols();
		for (String protocoloSoportado : protocolosSoportados) {
			System.out.println(protocoloSoportado+", ");
		}
		
		String[] protocoloDeseado = new String[1];
		protocoloDeseado[0] = "TLSv1.2"; // proporciona la maxima seguridad
		socket.setEnabledProtocols(protocoloDeseado);
		
		protocolos = socket.getEnabledProtocols();
		System.out.println("Protocolos habilitados: ");
		for (String protocolo : protocolos) {
			System.out.println(protocolo+", ");
		}
		
		
	}
	
	public Cli_SSL(String mensaje) throws UnknownHostException, IOException {
		System.out.println("Obteniendo factoria de socket del cliente...");
		SSLSocketFactory socketCLIFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		
		System.out.println("Creando el socket del cliente...");
		SSLSocket socketCLI = (SSLSocket) socketCLIFactory.createSocket(DESTINO, PUERTO);
		
		mostrarCifrado(socketCLI); // Mostramos los cifrados que actualmente tenemos disponibles y habilitados
		
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(socketCLI.getOutputStream()));
		pw.println(mensaje);
		pw.flush(); // por seguridad
		
		System.out.println("Mensaje enviado: "+mensaje);
		
		//Esperamos la repsuesta cifrada con hash desde el servidor y la mostramos por consola
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socketCLI.getInputStream()));
		System.out.println("Mesaje cifrado recibido: "+br.readLine());
		
		System.out.println("Cerrando conexion");
		pw.close();
		br.close();
		socketCLI.close();
		System.out.println("finalizando...");
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.setProperty("javax.net.ssl.keyStore", "./cert/AlmacenCLI");
		System.setProperty("javax.net.ssl.keyStorePassword", "0987654321");// esto no debe poner aqui 
		System.setProperty("javax.net.ssl.trustStore", "./cert/AlmacenCLI");
		System.setProperty("javax.net.ssl.trustStorePassword", "0987654321");
		new Cli_SSL("Hola mundo!");
	}
}
