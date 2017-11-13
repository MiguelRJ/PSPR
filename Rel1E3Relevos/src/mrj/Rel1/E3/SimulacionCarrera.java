package mrj.Rel1.E3;

class Relevo {
	boolean relevo;
	
	public Relevo() {
		relevo = false;
	}
	
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
	
	public synchronized void soltarRelevo() {
		relevo = false;
		notify();
	}
}

class Corredor extends Thread {
	
	String nombreCorredor;
	Relevo relevo;
	
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

class Carrera extends Thread {
	
	Corredor[] corredor;
	
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
		
		int numCorredores = Integer.parseInt(args[0]);
		Corredor[] corredor = new Corredor[numCorredores];
		Carrera carrera = new Carrera(corredor);
		Relevo relevo = new Relevo();
		for (int i = 0; i < numCorredores ; i++) {
			corredor[i] = new Corredor(i,relevo);
		}
		carrera.start();
		try {
			carrera.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
