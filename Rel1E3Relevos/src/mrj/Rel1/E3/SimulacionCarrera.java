package mrj.Rel1.E3;

/**
 * Clase relevo que para coger y soltar un objeto bool de forma sincronizada
 * @author Miguel Rodriguez Jimenez
 */
class Relevo {
	
	boolean relevo;
	
	public Relevo() {
		relevo = false;
	}
	
	/**
	 * El corredor coge el relevo, 
	 * si el relevo ya lo tiene otro entonces
	 * espera hasta que le notifiquen que ha quedado libre
	 */
	public synchronized void cogerRelevo() {
		while (relevo) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		relevo = true;
	}
	
	/**
	 * El corredor suelta el relevo
	 * y notifica que lo ha soltado
	 */
	public synchronized void soltarRelevo() {
		relevo = false;
		notify();
	}
}

/**
 * Clase corredor que extiende de Thread
 * Acciones del corredor:
 * 		cogerRelevo()
 * 		correr()
 * 		soltarRelevo()
 * @author Miguel Rodriguez Jimenez
 *
 */
class Corredor extends Thread {
	
	String nombreCorredor;
	Relevo relevo; // Instancia del relevo de la carrera, es el mismo para todos los corredores
	
	public Corredor(int numCorredor, Relevo relevoCarrera) {
		nombreCorredor = "Corredor numero: " + numCorredor;
		relevo = relevoCarrera;
	}
	
	public void cogerRelevo() {
		relevo.cogerRelevo();
		System.out.println(nombreCorredor + " ha cogido el relevo");
	}
	
	public void correr() {
		System.out.println(nombreCorredor + " esta corriendo...");
		try {
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void soltarRelevo() {
		System.out.println(nombreCorredor + " ha soltado el relevo");
		relevo.soltarRelevo();
	}
	
	@Override
	public void run() {
		cogerRelevo();
		correr();
		soltarRelevo();
	}
}

/**
 * Carrera tiene un array de corredores que echan a correr
 * @author Miguel Rodriguez Jimenez
 * 
 */
class Carrera extends Thread {
	
	Corredor[] corredor; // La carrare tiene un array de corredores
	
	public Carrera(Corredor[] corredores) {
		corredor = corredores;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < corredor.length; i++) {
			corredor[i].start();
		}
	}
}

public class SimulacionCarrera {
	public static void main(String[] args) {
		
		int numCorredores = Integer.parseInt(args[0]); // El numero de corredores que tendra la carrera
		Corredor[] corredor = new Corredor[numCorredores]; // El array de corredores
		Carrera carrera = new Carrera(corredor); // La carrera
		Relevo relevo = new Relevo(); // El relevo
		
		for (int i = 0; i < numCorredores ; i++) { 
			corredor[i] = new Corredor(i,relevo);
		}
		carrera.start();
		
	}
}
