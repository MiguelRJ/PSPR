package com.mrj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class cli {
	
	static final String HOST = "192.168.1.132";
	static final int PUERTO = 8888;
	PublicKey publicKey;
	
	public static String encriptar(String mensaje, PublicKey pk) {
		Cipher cifradorRSA;
		try {
			cifradorRSA = Cipher.getInstance("RSA");
			cifradorRSA.init(Cipher.ENCRYPT_MODE, pk);
			byte[] criptogramaEnBytes = Base64.getEncoder().encode(cifradorRSA.doFinal(mensaje.getBytes("utf-8")));
		return new String(criptogramaEnBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public cli() {
		try {
			Socket skCli = new Socket(HOST, PUERTO);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(skCli.getOutputStream(),"utf-8"),true);
			BufferedReader br = new BufferedReader(new InputStreamReader(skCli.getInputStream(), "utf-8"));
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
			System.out.println(br.readLine());//saludo cliente
			
			// Generar una clave publica de un string recibido por el server
			byte[] publicBytes = Base64.getDecoder().decode(br.readLine());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory;
			keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			
			String msg;
			String msgCifrado;
		
			do {
			System.out.println("Mensaje para servidor: ");
			msg = teclado.readLine();
			msgCifrado = encriptar(msg, publicKey);
			System.out.println("enviando: "+msg);
			System.out.println("enviando cifrado: "+msgCifrado);
			pw.println(msgCifrado);
			} while(!msg.equals("cerrar"));
			
			skCli.close();
			System.out.println("socket cerrado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new cli();
	}
}
