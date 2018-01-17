package mashilos.mrj;

import java.util.concurrent.atomic.AtomicInteger;

class Dato {
	private AtomicInteger dato;
	
	public Dato() {
		this.dato = new AtomicInteger();
	}
	
	public void incDato() {
		dato.incrementAndGet();
	}
	
	public int getDato() {
		return dato.get();
	}
}

class Contador implements Runnable{
	
	Thread hilo;
	int veces;
	Dato dato;
	
	public Contador(int veces, Dato dato) {
		this.hilo = new Thread(this,"hilo contador");
		this.veces = veces;
		this.dato = dato;
	}
	
	public void start() {
		hilo.start();
	}
	
	public void join() {
		try {
			hilo.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		for (int i=0; i<veces; i++) {
			dato.incDato();
		}
	}
}

public class ContadorSimulacion {

	public static void main(String[] args) throws InterruptedException {
		int veces = 5000;
		Dato dato = new Dato();
		Contador c1 = new Contador(veces, dato);
		Contador c2 = new Contador(veces, dato);
		Contador c3 = new Contador(veces, dato);
		Contador c4 = new Contador(veces, dato);
		Contador c5 = new Contador(veces, dato);
		
		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
		
		c1.join();
		c2.join();
		c3.join();
		c4.join();
		c5.join();
		
		System.out.println(dato.getDato());
	}

}
