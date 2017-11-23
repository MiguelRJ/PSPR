package mrj.Rel1.E2;

/*
 * hacer con monitor
 */

class Bufer {
	int[] buferDatos;
	final int tamanoBufer = 10000;
	int posLectura;
	int posEscritura;
	
	public Bufer() {
		buferDatos = new int[tamanoBufer];
		posLectura = 0;
		posEscritura = 0;
	}
	
	public synchronized void getDatos() {
		while (posLectura >= posEscritura) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (posLectura < buferDatos.length) {
			System.out.println("Dato leido: "+buferDatos[posLectura++]);
		}
		notify();
	}

	public synchronized void setDatos(int datos) {
		while (posEscritura > posLectura) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (posLectura < buferDatos.length) {
			System.out.println("Datos escrito: "+datos);
			buferDatos[posEscritura++] = datos;
		}
		notify();
	}
}

class Lector extends Thread {
	
	Bufer bufer;
	int maxDatos;
	
	public Lector(int hasta,Bufer bufer) {
		maxDatos = hasta;
		this.bufer = bufer;
	}
	
	public void leer() {
		try {
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bufer.getDatos();
	}
	
	@Override
	public void run() {
		for (int i = 0; i < maxDatos; i++) {
			leer();
		}
	}
	
}
class Escritor extends Thread {
	
	Bufer bufer;
	int maxDatos;
	int datoAEscribir;
	
	public Escritor(int hasta,Bufer bufer) {
		maxDatos = hasta;
		this.bufer = bufer;
		datoAEscribir = 0;
	}
	
	public void escribir() {
		try {
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bufer.setDatos(datoAEscribir++);
	}
	
	@Override
	public void run() {
		for (int i = 0; i < maxDatos; i++) {
			escribir();
		}
	}
}

class Check extends Thread{
		int maxDatos;
		Lector lector;
		Escritor escritor;
		
		public Check(int hasta,Lector cLector, Escritor cEscritor) {
			maxDatos = hasta;
			lector = cLector;
			escritor = cEscritor;
		}
		
		@Override
		public void run() {
			lector.start();
			escritor.start();
		}
		
}

public class MainCheck {
	public static void main(String[] args) {
		int hasta = Integer.parseInt(args[0]);
		Bufer bufer = new Bufer();
		Lector lector = new Lector(hasta, bufer);
		Escritor escritor = new Escritor(hasta, bufer);
		Check check = new Check(hasta, lector, escritor);
		
		check.start();
		try {
			check.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}