package dam.psp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

public class ServidorHilo extends Thread {
	
	private Socket elSocket;
	private int id;
	String mensaje;

	public ServidorHilo(Socket socket, int id) {	
		this.elSocket = socket;
		this.id = id;
		this.mensaje = "Binvenido/a a mi canal";
	}
	
	@Override
	public void run() {
		BufferedOutputStream bo;
		BufferedInputStream is;
		BufferedReader br;
		PrintWriter pw = null;
		
		try {
			bo = new BufferedOutputStream(elSocket.getOutputStream());
			pw = new PrintWriter(bo,true); // true limpia el buffer para que el canal de comunicacion quede limpio
			pw.println(id +":"+ this.mensaje);
			
			// y ahora espera una respuesta en forma de string desde el cliente
			//br = new BufferedReader(new InputStreamReader(System.in));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (pw != null) {
			pw.flush(); // Nunca deberia hacer falta esto 
			pw.close();
		}
	}
}
