package com.mrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class srvThread extends Thread {
	
	private Socket socket;
	private int id;
	String msg;
	KeyPair clave;
	KeyPairGenerator keygen;
	PublicKey pk;
	
	public srvThread(Socket socket, int id) {
		this.socket = socket;
		this.id = id;
		this.msg = "Hola nuevo cliente";
		try {
			keygen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		};
		clave = keygen.generateKeyPair();
		pk = clave.getPublic();
	}
	
	public static void mostrarInfloClave(KeyPair clave) throws Exception {
		KeyFactory factory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec partePublica = factory.getKeySpec(clave.getPublic(), RSAPublicKeySpec.class);
		RSAPrivateKeySpec partePrivada = factory.getKeySpec(clave.getPrivate(), RSAPrivateKeySpec.class);
		System.out.println("Publica: ");
		System.out.println("	Modulos: "+partePublica.getModulus());
		System.out.println("	Exponentus:"+ partePublica.getPublicExponent());
		
		System.out.println("Privada: ");
		System.out.println("	Modulos: "+partePrivada.getModulus());
		System.out.println("	Exponentus:"+ partePrivada.getPrivateExponent());
	}
	
	public static String desencriptar(String criptograma, KeyPair clave) {
		Cipher cifradorRSA;
		try {
			cifradorRSA = Cipher.getInstance("RSA");
			cifradorRSA.init(Cipher.DECRYPT_MODE, clave.getPrivate());
			byte[] mensajeEnBytes = Base64.getDecoder().decode(criptograma.getBytes("utf-8")); // deserializo aqui
			return new String(cifradorRSA.doFinal(mensajeEnBytes)); // descifrar aqui
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "null";
	}
	
	@Override
	public void run() {
		BufferedReader br;
		PrintWriter pw = null;
		String msgCli;
		String msgCliDesencriptado;
		
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			pw.println(id+":"+this.msg);
			pw.println(new String(Base64.getEncoder().encodeToString(pk.getEncoded()))); // Enviar clave publica al cliente
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			do {
			msgCli = br.readLine();
			msgCliDesencriptado = desencriptar(msgCli, clave);
			System.out.println("mensaje del cliente "+id+":");
			System.out.println("encriptado: "+msgCli);
			System.out.println("desencriptado: "+msgCliDesencriptado);
			} while(!msgCliDesencriptado.equals("cerrar"));
			
			System.out.println("socket cerrado con cliente: "+id);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (pw!=null) {
			pw.flush();
			pw.close();
		}
	}
}
