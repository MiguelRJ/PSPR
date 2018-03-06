package com.mrj;

import java.net.ServerSocket;
import java.net.Socket;

public class srv {
	
	public static int PUERTO = 8888;
	
	public static void main(String[] args) {
		new srv();
	}
	
	public srv() {
		ServerSocket socket;
		
		try {
			socket = new ServerSocket(PUERTO);
			System.out.println("Servidor escuchando en: "+socket.getLocalSocketAddress());
			int cliente = 0;
			while (true) {
				Socket atendiendo = socket.accept();
				System.out.println("Atendiendo nuevo cliente: "+ ++cliente);
				new srvThread(atendiendo, cliente).start();
			}
		} catch (Exception e) {

		}
	}
}
