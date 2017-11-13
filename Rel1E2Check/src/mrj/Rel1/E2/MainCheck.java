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
	
	public Lector(int hasta) {
		maxDatos = hasta;
	}
	
	public int leer() {
		try {
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Datos leido: "+Bufer.datos);
		return Bufer.datos;
	}
	
	/*@Override
	public void run() {
		for (int i = 0; i < maxDatos; i++) {
			leer();
		}
	}*/
}
class Escritor extends Thread {
	int maxDatos;
	
	public Escritor(int hasta) {
		maxDatos = hasta;
	}
	
	public int escribir() {
		try {
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Datos escrito: "+Bufer.datos);
		return Bufer.datos++;
	}
	
	/*@Override
	public void run() {
		for (int i = 0; i < maxDatos; i++) {
			escribir();
		}
	}*/
}

class Check extends Thread{
		int maxDatos;
		Semaphore sem;
		Lector lector;
		Escritor escritor;
		
		public Check(int hasta, Semaphore semaforo,Lector cLector, Escritor cEscritor) {
			maxDatos = hasta;
			sem = semaforo;
			lector = cLector;
			escritor = cEscritor;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < maxDatos; i++) {
				try {
					sem.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lector.leer();
				sem.release();
				try {
					sem.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				escritor.escribir();
				sem.release();
			}
		}
		
}

public class MainCheck {
	public static void main(String[] args) {
		int hasta = Integer.parseInt(args[0]);
		Semaphore semaphore = new Semaphore(1);
		Lector lector = new Lector(hasta);
		Escritor escritor = new Escritor(hasta);
		Check check = new Check(hasta, semaphore, lector, escritor);
		
		check.start();
		try {
			check.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}