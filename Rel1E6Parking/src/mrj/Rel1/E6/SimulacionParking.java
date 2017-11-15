package mrj.Rel1.E6;

class Parking {
	
	int numPlazas;
	int numCoches;
	int numPlazasOcupadas;

	public Parking(int numPlazas, int numCoches) {
		this.numPlazas = numPlazas;
		this.numCoches = numCoches;
		this.numPlazasOcupadas = 0;
	}
	
	/**
	 * Comprueba que el numero de plazas totales del parking
	 * sea igual al numero de plazas ocupadas
	 * @return 
	 * FALSE si el numero de plazas totales NO es igual al numero de plazas ocupadas
	 * TRUE si el numero de plazas totales SI es igual al numero de plazas ocupadas
	 */
	boolean hayPlazaLibre() {
		return numPlazas == numPlazasOcupadas;
	}
	
	/**
	 * Mientras que NO haya plazas libres el coche esperara
	 * Cuando haya alguna plaza libre, el numero de plazas ocupada aumentara (numPlazasOcupadas++;)
	 */
	public synchronized void entrarParking() {
		while (hayPlazaLibre()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numPlazasOcupadas++;
	}
	
	/**
	 * El coche sale del parking, entonces deja libre una plaza (numPlazasOcupadas--;)
	 * y avisa de que ha salido notify();
	 */
	public synchronized void salirParking() {
		numPlazasOcupadas--;
		notify();
	}
	
}

class Coche extends Thread {
	
	Parking parking;
	int numCoche;
	int numRepeticionesEntrarParking;
	
	public Coche(Integer numCoche, Parking parking, int numRepeticionesEntrarParking) {
		this.numCoche = numCoche;
		this.parking = parking;
		this.numRepeticionesEntrarParking = numRepeticionesEntrarParking;
	}
	
	public void entrarParking() {
		System.out.println("Coche: "+numCoche+ " quiere entrar");
		parking.entrarParking();
	}
	
	public void esperarParking() {
		try {
			System.out.println("Coche: "+numCoche+ " esta aparcado...");
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void salirParking() {
		System.out.println("Coche: "+numCoche+ " esta saliendo");
		parking.salirParking();
	}
	
	@Override
	public void run() {
		if (numRepeticionesEntrarParking == 0) {
			while (true) {
				entrarParking();
				esperarParking();
				salirParking();
			}
		} else {
			for (int i = 0; i < numRepeticionesEntrarParking; i++) {
				entrarParking();
				esperarParking();
				salirParking();
			}
		}
	}
}

public class SimulacionParking {
	public static void main(String[] args) {
		
		int numPlazas = Integer.parseInt(args[0]);
		int numCoches = Integer.parseInt(args[1]);
		int numRepeticionesEntrarParking; // Para que la ejecucion pare alguna vez, si es 0 o null no parara
		int tiempoEsperaEjecucion = 2; // tiempo de espera antes de ejecutar y no especificar el args[2]
		try {
			numRepeticionesEntrarParking = Integer.parseInt(args[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("No se ha especificado cuantas veces un coche va a entrar en el parking. args[2]");
			System.out.println("Por defecto se pone a 0");
			System.out.println(tiempoEsperaEjecucion+" segundos y empieza ejecucion.");
			try {
				Thread.sleep(tiempoEsperaEjecucion* 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			numRepeticionesEntrarParking = 0;
		} 
		
		Parking parking = new Parking(numPlazas, numCoches);
		
		for (int i = 0; i < numCoches; i++) {
			new Coche(i, parking, numRepeticionesEntrarParking).start();
		}
	}
}
