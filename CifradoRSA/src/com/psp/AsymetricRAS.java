package com.psp;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class AsymetricRAS {
	
	private static final String ALG = "RSA";
	
	public static String encriptar(String mensaje, KeyPair clave) throws Exception {
		Cipher cifradorRSA = Cipher.getInstance(ALG);
		cifradorRSA.init(Cipher.ENCRYPT_MODE, clave.getPublic());
		
		byte[] criptogramaEnBytes = Base64.getEncoder().encode(cifradorRSA.doFinal(mensaje.getBytes("utf8")));
		return new String(criptogramaEnBytes);
	}
	
	public static String desencriptar(String criptograma, KeyPair clave) throws Exception {
		Cipher cifradorRSA = Cipher.getInstance(ALG);
		cifradorRSA.init(Cipher.DECRYPT_MODE, clave.getPrivate());
		
		byte[] mensajeEnBytes = Base64.getDecoder().decode(criptograma.getBytes("utf-8")); // deserializo aqui
		return new String(cifradorRSA.doFinal(mensajeEnBytes)); // descifrar aqui
	}
	
	public static void mostrarInfloClave(KeyPair clave) throws Exception {
		KeyFactory factory = KeyFactory.getInstance(ALG);
		RSAPublicKeySpec partePublica = factory.getKeySpec(clave.getPublic(), RSAPublicKeySpec.class);
		RSAPrivateKeySpec partePrivada = factory.getKeySpec(clave.getPrivate(), RSAPrivateKeySpec.class);
		System.out.println("Publica: ");
		System.out.println("	Modulos: "+partePublica.getModulus());
		System.out.println("	Exponentus:"+ partePublica.getPublicExponent());
		
		System.out.println("Privada: ");
		System.out.println("	Modulos: "+partePrivada.getModulus());
		System.out.println("	Exponentus:"+ partePrivada.getPrivateExponent());
	}

	public static void main(String[] args) throws Exception {
		String mensaje = "Anda que la que va a liar Paco, va a ser parda";
		
		System.out.println("Obteniendo el generador de claves RAS");
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALG);
		
		System.out.println("Generando la clave RSA");;
		KeyPair clave = keygen.generateKeyPair();
		
		System.out.println("Mostrar informacion de la clave generada: ");
		mostrarInfloClave(clave);
		
		String criptograma = encriptar(mensaje, clave);
		System.out.println("Mensaje cifrado: "+criptograma);
		
		System.out.println("Mensaje descifrado: "+ desencriptar(criptograma, clave));
	}

}
