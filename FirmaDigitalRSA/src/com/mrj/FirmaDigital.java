package com.mrj;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;

public class FirmaDigital {
	
	//private static final String ALGDSA = "DSA"; // faster for signing, can only be used for signing/verification
	private static final String ALGRSA = "RSA"; // faster at verifying, can be used for encryption/decryption as well
	
	//private static final String PROTODSA = "DSA";
	private static final String PROTORSA = "SHA256WithRSA";
	
	private static String firmar(String mensaje, KeyPair clave) throws Exception {
		System.out.println("creando un obejto de tipo Signature...");
		Signature firmaContenedor = Signature.getInstance(PROTORSA);
		
		System.out.println("Firmando el mensaje con la parte PRIVADA de la clave asimetrica...");
		firmaContenedor.initSign(clave.getPrivate());
		
		firmaContenedor.update(mensaje.getBytes("utf8"));
		byte[] firma = firmaContenedor.sign();
		
		return Base64.getEncoder().encodeToString(firma);
		//return new String(firma); // no funciona no rellena los multiplos
	}
	
	private static boolean esFirmaValida(String mensajeEnClaro, String firma, KeyPair clave) throws Exception {
		System.out.println("creando un obejto de tipo Signature...");
		Signature firmaContenedor = Signature.getInstance(PROTORSA);
		
		byte[] mensajeBytes = Base64.getDecoder().decode(firma.getBytes("utf8")); // deshacemos el padding 64 bits
		//byte[] mensajeBytes = firma.getBytes("utf8"); // no funciona no rellena los multiplos
		
		System.out.println("Verificando la firma con la parte PUBLICA de la clave asimetrica");
		firmaContenedor.initVerify(clave.getPublic());
		
		firmaContenedor.update(mensajeEnClaro.getBytes("utf8"));
		
		return firmaContenedor.verify(mensajeBytes);

	}
	
	public static void main(String[] args) throws Exception {
		
		String mensaje = "En un lugar de la Mancha, de cuyo nombre ni me acuerdo";
		System.out.println("Obteniendo el generador de claves : "+ALGRSA);
		
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGRSA);
		KeyPair clave = keygen.generateKeyPair();
		
		System.out.println("Firmando el mensaje '"+mensaje+"' con la parte privada de la clave");
		
		String firma = firmar(mensaje, clave);
		
		System.out.println("Resultado de la firma: "+firma);
		
		System.out.println("\nComprobando la validez de esa firma...");
		
		if (esFirmaValida(mensaje, firma, clave)) {
			System.out.println("VERIFICADO OK");
		} else {
			System.out.println("Firma NO verificada.");
		}
		
		System.out.println("\nAhora manipulo el texto y debe invalidar la firma");
		String mensajeAlterado = mensaje+"\n";
		
		if (esFirmaValida(mensajeAlterado, firma, clave)) {
			System.out.println("VERIFICADO OK");
		} else {
			System.out.println("Firma NO verificada.");
		}
	}
	
}
