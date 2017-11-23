package dam.psp;

class DatoCompartido {
	public static int c1 = 0;
	public static int c2 = 0;
	
	private static final Object mutex1 = new Object();// monitor semaforo binario 
	private static final Object mutex2 = new Object();

	public static void incrementarC1() {
		// Seccion critica 1
		synchronized (mutex1) {
		c1++;
		}
		// Fin de Seccion critica
	}

	public static void incrementarC2() {
		// Seccion critica 2
		synchronized (mutex2) {
		c2++;
		}
		// Fin de Seccion critica
	}
}

class HilosMutex extends Thread {
	@Override
	public void run() {
		DatoCompartido.incrementarC1();
		DatoCompartido.incrementarC2();
	}
}

public class HilosExclusionMutua {
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		HilosMutex hilos[];
		System.out.println("Creando " + N + " hilos");
		hilos = new HilosMutex[N];
		
		for (int i = 0; i < N; i++) {
			hilos[i] = new HilosMutex();
			hilos[i].start();
		}
		
		for (int i = 0; i < N; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("C1 = " + DatoCompartido.c1);
		System.out.println("C2 = " + DatoCompartido.c2);
	}
}
