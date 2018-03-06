package dam.psp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
		//BufferedOutputStream bo;
		//BufferedInputStream is;
		BufferedReader br;
		PrintWriter pw = null;
		
		try {
			//bo = new BufferedOutputStream(elSocket.getOutputStream());
			pw = new PrintWriter(new OutputStreamWriter(elSocket.getOutputStream(),"utf-8"),true); // true limpia el buffer para que el canal de comunicacion quede limpio
			pw.println(id +":"+ this.mensaje);
			
			// y ahora espera una respuesta en forma de string desde el cliente
			br = new BufferedReader(new InputStreamReader(elSocket.getInputStream(),"utf-8"));
			System.out.print("Mensaje recibido desde el cliente: ");
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
