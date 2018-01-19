package mrj.com;

import java.util.Random;

import sun.nio.ch.DatagramSocketAdaptor;

class Datos {
	private int dato;
	
	Object mutex = new Object();
	
	public Datos() {
		this.dato = 0;
	}
	
	public synchronized void incDatoUno() {
		dato++;
		notifyAll();
	}
	
	public void incDato(int numPrimos) {
		dato += numPrimos;
	}
	
	public int getDato() {
		return dato;
	}
	
}

class BuscadorPrimos extends Thread {
	
	private Datos dato;
	private int[] primos;
	private int numPrimos;
	
	public BuscadorPrimos(int[] primos, Datos dato) {
		this.primos = primos;
		this.dato = dato;
	}
	
	private boolean isPrimo(int num) {
		boolean isPrimo = true;
		if (num<2) {
			return false;
		}
		for (int i = 2; i<num; i++) {
			if (num%i==0) {
				isPrimo = false;
				break;
			}
		}
		return isPrimo;
	}
	
	public void sumarDato() {
		//dato.incDato(numPrimos);
	}
	
	@Override
	public void run() {
		for (int i = 0; i<primos.length; i++) {
			if (isPrimo(primos[i])) {
				numPrimos++;
				dato.incDatoUno();
				//System.out.println("Numero: "+primos[i]+" primo");
			} else {
				//System.out.println("Numero: "+primos[i]);
			}
		}
		System.out.println(numPrimos);
	}
}

public class SimulacionPrimos {

	public static void main(String[] args) {
		
		final int MAX_LENGHT = 100000;
		Random random = new Random();
		int[] primos = new int[MAX_LENGHT];
		int[] primosCopias;
		BuscadorPrimos[] buscadorPrimos = new BuscadorPrimos[5];
		Datos datos = new Datos();
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i<MAX_LENGHT; i++) {
			primos[i] = random.nextInt(MAX_LENGHT) ;
		}
		
		//buscadorPrimos[1] = new BuscadorPrimos(primos); // solo un array de 100
		for (int i = 0; i<buscadorPrimos.length; i++) {
			primosCopias = new int[primos.length/buscadorPrimos.length];
			for (int j = 0; j<primos.length/buscadorPrimos.length; j++) {
				primosCopias[j] = primos[i*(primos.length/buscadorPrimos.length)+j];
			}
			buscadorPrimos[i] = new BuscadorPrimos(primosCopias,datos);
			
		}
		
		for (int i = 0; i<5; i++) {
			buscadorPrimos[i].start();
		}
		
		//buscadorPrimos[1].start(); // solo un array de 100
		
		try {
			for (int i = 0; i<5; i++) {
				buscadorPrimos[i].join();
			}
			buscadorPrimos[0].sumarDato();
			buscadorPrimos[1].sumarDato();
			buscadorPrimos[2].sumarDato();
			buscadorPrimos[3].sumarDato();
			buscadorPrimos[4].sumarDato();
			//buscadorPrimos[1].join(); // solo un array de 100
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("fin, tiempo: "+ (endTime-startTime) + " encontrados: "+datos.getDato());
	}

}
