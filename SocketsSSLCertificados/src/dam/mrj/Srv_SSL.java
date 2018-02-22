package dam.mrj;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

class SrvSSLHilo extends Thread {

	SSLSocket miSocket;
	
	public SrvSSLHilo(SSLSocket socketAtencion) {
		this.miSocket = socketAtencion;
	}
	
	@Override
	public void run() {
		InputStreamReader is;
		try {
			is = new InputStreamReader(miSocket.getInputStream(),"utf8");
			BufferedReader br = new BufferedReader(is);
			String mensajeRecibido = br.readLine();
			System.out.println("Mensaje recibido desde el cliente: "+mensajeRecibido);
			
			// Enviamos com respuesto el mensaje en hash SHA-256
			PrintWriter pw = new PrintWriter(new BufferedOutputStream(miSocket.getOutputStream()),true); // true para el flush del buffer
			byte[] mensajeEnBytes = mensajeRecibido.getBytes("utf8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			pw.print(sha.digest(mensajeEnBytes)); // muestra y envia por el canal el sha256 del mensaje recibido
			
			pw.flush();
			pw.close();
			System.out.println("Cerrando socket y el hilo de atencion");
			miSocket.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
}

public class Srv_SSL {
	
	static int PUERTO = 5555;
	
	public Srv_SSL() throws IOException {
		System.out.println("Obteniendo factoria del socket para el servidor");
		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault(); //instanciar socket
		
		System.out.println("Creando el socket...");
		SSLServerSocket socketSRV = (SSLServerSocket) serverSocketFactory.createServerSocket(PUERTO);
		
		while (true) {
			System.out.println("Aceptando conexciones...");
			SSLSocket socketAtencion = (SSLSocket) socketSRV.accept();
			System.out.println("Atendiendo nueva conexcion con hilo dedicado...");
			new SrvSSLHilo(socketAtencion).start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Srv_SSL();
	}

}
