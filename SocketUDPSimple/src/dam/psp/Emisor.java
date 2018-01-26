package dam.psp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Emisor {
	
	public static final int PuertoEmisor = 5555;
	public static final int PuertoReceptor = 4444;
	public static final String IPEmisor = "192.168.3.32"; // 3.57  // 3.32 // 2.27
	
	public Emisor() {
		
		DatagramSocket emisor;
		DatagramPacket dgp;
		InetAddress IPLocal;
		InetAddress IPRemota;
		
		// Construimos el contenido del mensaje a enviar
		byte[] contenido = new byte[144];
		//String mensaje = "GLOBO 1 PINCHADO POR PG3";
		DatoUDP mensaje = new DatoUDP("GLOBO 1 PINCHADO POR PG3",10);
		
		try {
			
			IPLocal = InetAddress.getByName("0.0.0.0"); // Coge la predeterminada que este levantada
			IPRemota = InetAddress.getByName(IPEmisor);
			
			emisor = new DatagramSocket(PuertoEmisor,IPLocal); // Por donde sale el mensaje
			
			//contenido = mensaje.getBytes();
			contenido = mensaje.ToByteArray();
			dgp = new DatagramPacket(contenido, contenido.length, IPRemota, PuertoReceptor);
			
			emisor.send(dgp);
			
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	public static void main(String[] args) {
		new Emisor();
	}

}
