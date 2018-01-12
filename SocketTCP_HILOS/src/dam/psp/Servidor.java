package dam.psp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	static final int PUERTO = 5000;
	
	public static void main(String[] args) {
		new Servidor();
	}
	
	public Servidor () {
		
		ServerSocket skSRV;
		
		try {
			skSRV = new ServerSocket(PUERTO);
			System.out.println("Servidor escuchando en: "+skSRV.getLocalSocketAddress().toString());
			int nCli = 0;
			while (true) {
				Socket skAtencion = skSRV.accept();
				nCli++;
				System.out.println("Atendiendo nuevo cliente: "+nCli);
				
				// Creamos un hilo para atender al cliente y asi liberar al socket principal
				new ServidorHilo(skAtencion,nCli);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
