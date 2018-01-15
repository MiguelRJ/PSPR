package dam.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	static final String HOST = "192.168.3.57";
	static final int PUERTO = 5000;
	Scanner entrada;
	
	private String LeerMensaje() {
		System.out.println("Introduce mensaje: ");
		String mensaje = entrada.nextLine();
		return mensaje;
	}
	
	public Cliente() {
		try {
			Socket skCli = new Socket(HOST,PUERTO);
			InputStreamReader is = new InputStreamReader(skCli.getInputStream(),"utf-8");
			BufferedReader br = new BufferedReader(is);
			System.out.println(br.readLine()); // Recibimos el saludo desde el servidor
			// enviar mensaje pedido por consola al servidor
			skCli.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Cliente();	
	}
}
