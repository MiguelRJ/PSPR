package dam.psp;

import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
		byte[] miClaveSha256 = Arrays.copyOf(DigestUtils.sha256Hex(miClaveEnBytes), 16);
		return new SecretKeySpec(miClaveSha256, cifrado);
	}
	
	public static void main(String[] args) {
		
	}
}
