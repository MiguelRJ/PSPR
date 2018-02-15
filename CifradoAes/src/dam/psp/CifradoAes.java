package dam.psp;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CifradoAes {
	
	private static String cifrado = "AES";
	
	public static SecretKey obtenerClaveOpaca(int longitud) throws Exception {
		KeyGenerator claveInstancia = KeyGenerator.getInstance(cifrado);
		claveInstancia.init(longitud);
		return claveInstancia.generateKey();
	}
	
	public static SecretKeySpec obtenerClaveTransparente(String miClave) throws Exception {
		byte[] miClaveEnBytes = miClave.getBytes("utf8"); // Serializamos la frase de paso
		System.out.println("El hash SHA2 de la clave es: "+DigestUtils.sha256Hex(miClaveEnBytes));
		byte[] miClaveSha256 = Arrays.copyOf(DigestUtils.sha256(miClaveEnBytes), 16); // los 16 primeros bytes, ncesita bloques de 16
		return new SecretKeySpec(miClaveSha256, cifrado);
	}
	
	public static String encriptar(String mensaje, SecretKey clave) throws Exception {
		Cipher c = Cipher.getInstance(cifrado); // AES
		c.init(Cipher.ENCRYPT_MODE, clave);
		byte[] encVal = c.doFinal(mensaje.getBytes("utf8"));
		byte[] criptogramaEnBytes = Base64.encodeBase64(encVal); // para mostrarlo bien
		return new String(criptogramaEnBytes);
	}
	
	public static String desencriptar(String criptograma, SecretKey clave) throws Exception {
		Cipher c = Cipher.getInstance(cifrado); // AES
		c.init(Cipher.DECRYPT_MODE, clave);
		byte[] decVal = Base64.decodeBase64(criptograma.getBytes("utf8"));
		byte[] decryptedValue = c.doFinal(decVal);
		return new String(decryptedValue);
	}
	
	public static void main(String[] args) {
		String mensaje = "Vaya melón que tiene Cicerón un viernes por la tarde en el Tívolii";
		String miClave = "123;abc";
		
		try {
			SecretKey miClaveOpaca = CifradoAes.obtenerClaveOpaca(128); // por defecto seria 128
			System.out.println("Mensaje en claro: "+ mensaje);
			String criptograma = CifradoAes.encriptar(mensaje, miClaveOpaca);
			System.out.println("Criptograma: "+criptograma);
			System.out.println("Desencriptado: "+CifradoAes.desencriptar(criptograma, miClaveOpaca));
			
			System.out.println("---");
			// Ahora repetimos usando una clave transparente
			SecretKeySpec miClaveTransparente = CifradoAes.obtenerClaveTransparente(miClave);
			criptograma = CifradoAes.encriptar(mensaje, miClaveTransparente);
			System.out.println("Criptograma: "+criptograma);
			System.out.println("Desencriptado: "+CifradoAes.desencriptar(criptograma, miClaveTransparente));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
