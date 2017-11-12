package mrj.Rel1.E2;

import java.util.concurrent.Semaphore;
/*
 * hacer que el que haga las repeticiones sea el main
 * y en el main hacer leer.start .join y escribir.start
 */
class Bufer {
	public static int datos = 0;
}

class Lector extends Thread {
	int maxDatos;
	Semaphore semaforo;
	
	public Lector(int hasta, Semaphore sem) {
		maxDatos = hasta;
		semaforo = sem;
	}
	
	public int leer() {
		System.out.println("Datos leido: "+Bufer.datos);
		return Bufer.datos;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < maxDatos; i++) {
			try {
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			leer();
			semaforo.release();
		}
		
	}
}
class Escritor extends Thread {
	int maxDatos;
	Semaphore semaforo;
	
	public Escritor(int hasta, Semaphore sem) {
		maxDatos = hasta;
		semaforo = sem;
	}
	
	public int escribir() {
		System.out.println("Datos escrito: "+Bufer.datos);
		return Bufer.datos++;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < maxDatos; i++) {
			try {
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			escribir();
			semaforo.release();
		}
		
	}
}

public class Check {
	public static void main(String[] args) {
		int hasta = Integer.parseInt(args[0]);
		Semaphore sem = new Semaphore(1);
		Lector lector = new Lector(hasta, sem);
		Escritor escritor = new Escritor(hasta, sem);
		
		lector.start();
		escritor.start();
	}
}














/*
 * Tengo la clase que lee y la que escribe
 * pero escribe y lee todo seguido
 * como puedo hacer que escriba y lea una vez alternadas una a una?
 */
/*
public class Check {
	public static void main(String[] args) {
		Bufer bufer = new Bufer(10);
		Escritor escritor = new Escritor(bufer);
		Lector lector = new Lector(bufer);
		
		lector.start();
		escritor.start();
		
		
	}
}

class Lector extends Thread{
	
	Bufer bufer;
	
	public Lector(Bufer buferCompartido) {
		bufer = buferCompartido;
	}
	public void leerBufer() {
		System.out.println("Dato leido: " +  bufer.leer());
	}
	
	@Override
	public void run() {
		while (!bufer.maximoAlcanzado()) {
			leerBufer();
		}
	}
}

class Escritor extends Thread{
	
	Bufer bufer;
	
	public Escritor(Bufer buferCompartido) {
		bufer = buferCompartido;
	}
	
	public void escribirBufer() {
		System.out.println("Dato escrito: " +  bufer.escribir());
	}
	
	@Override
	public void run() {
		while (!bufer.maximoAlcanzado()) {
			escribirBufer();
		}
	}
}

class Bufer {
	
	int buferDatos;
	int buferDatosMAX;
	
	public Bufer(int numDatosMAX) {
		buferDatos = 0;
		buferDatosMAX = numDatosMAX;
	}
	
	public int escribir() {
		return buferDatos++;
	}
	
	public int leer() {
		return buferDatos;
	}
	
	public boolean maximoAlcanzado() {
		if(leer() == buferDatosMAX) {
			return true;
		}
		return false;
	}
}
*/