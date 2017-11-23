package mrj.Rel1.E7;

class Plaza {
	
	int numPlaza; // numero de la plaza
	boolean ocupada; // si la plaza esta ocupada o no
	Vehiculo vehiculo; // el vehiculo que hay en la plaza

	public Plaza(int numPlaza) {
		this.numPlaza = numPlaza;
		ocupada = false;
		vehiculo = null;
	}

	public int getNumPlaza() {
		return numPlaza;
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
}

class Parking {
	
	Plaza[] plazas; // array de plazas de las que dispone el parking
	int numPlazas; // numero de plazas que tiene el parking

	public Parking(int numPlazas) {
		this.numPlazas = numPlazas;
		plazas = new Plaza[numPlazas];
		for (int i = 0; i < plazas.length; i++) { // inicializamos las plazas en el constructor del parking
			plazas[i] = new Plaza(i);
		}
	}

	/**
	 * recorro el array de plazas del parking
	 * si una plaza no esta ocupada
	 * aumento el hueco libre
	 * si la plaza esta ocupada entonces se vuelve a poner a 0
	 * si el hueco libre es mayor al mayor 
	 * guardo el nuevo hueco libre como mayor
	 * @param int tamanoVehiculo para buscar hueco para el tama�o de un vehiculo concreto
	 * @return true si el tamano del vehiculo es menor o igual al huecolibremayor, false si no
	 */
	boolean hayPlazaLibre(int tamanoVehiculo) { // deberia sincronizar?
		int huecoLibre = 0;
		int huecoLibreMayor = 0;
		for (int i = 0; i < plazas.length; i++) {
			if (!plazas[i].ocupada) {
				huecoLibre++;
			} else {
				huecoLibre = 0;
			}
			if (huecoLibre > huecoLibreMayor) {
				huecoLibreMayor = huecoLibre;
			}
		}
		return tamanoVehiculo <= huecoLibreMayor;
	}

	/**
	 * comprueba si hay plazas libres, si no hay espera, si hay sigue
	 * [comentario en el codigo]
	 * @param vehiculo que entra en el parking
	 */
	public synchronized void entrarParking(Vehiculo vehiculo) {
		while (!hayPlazaLibre(vehiculo.tamanoOcupado)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int tamanoVehiculo = vehiculo.tamanoOcupado;
		int huecoLibre = 0;
		int huecoLibreMayor = 0;
		Plaza[] plazasEncontradas = new Plaza[tamanoVehiculo]; // para saber las plazas que ocupa el vehiculo
		boolean isPlazaEncontrada = false; // si se ha encontrado una plaza libre

		for (int i = 0; i < plazas.length; i++) {
			if (!isPlazaEncontrada) {
				if (!plazas[i].ocupada) {
					huecoLibre++;
				} else {
					huecoLibre = 0;
				}
				if (huecoLibre > huecoLibreMayor) {
					huecoLibreMayor = huecoLibre;
				}
				if (huecoLibreMayor == tamanoVehiculo) { 
					// si el huecoLibreMayor es igual a tamanoVehiculo
					// hacemos otro for del tamano del vehiculo
					// y hacemos i - j porque si ha encontrado un huecoLibreMayor
					// es porque las plazas anteorioes a la actual (i) estan libres y nuestro vehiculo cabe ahi
					for (int j = 0; j < tamanoVehiculo; j++) {
						plazas[i - j].setOcupada(true); // esta plaza la marcamos como ocupada
						plazas[i - j].setVehiculo(vehiculo); // a esta plaza le asignamos el vehiculo que queria entrar
						plazasEncontradas[j] = plazas[i - j]; // y al array creado anterior mente le a�adimos la plaza
					}
					vehiculo.plaza = plazasEncontradas; // al array de plaza que ocupa el vehiculo le asignamos el creado aqui
					isPlazaEncontrada = true; // como ya hemos encontrado una plaza no necesitamos buscar mas
				}
			}
		}
	}

	/**
	 * Para salir del parking el vehiculo tiene que
	 * dejar plaza.ocupada a false
	 * y plaza.vehiculo a null
	 * como vehiculo tiene un array de plzas (para vehiculos grande)
	 * hay que recorrer el array y veciar todas las plazas
	 * se hace con la variable vehiculo.tamanoOcupado porque
	 * el tama�o que ocupe el vehiculo sera el tama�o del array
	 * y notifica
	 * @param vehiculo
	 */
	public synchronized void salirParking(Vehiculo vehiculo) {
		for (int i = 0; i < vehiculo.tamanoOcupado; i++) {
			vehiculo.plaza[i].setOcupada(false);
			vehiculo.plaza[i].setVehiculo(null);
		}
		notifyAll();
	}

	/**
	 * NO SE USA
	 * Metodo para calcular cuantas plazas libres hay en el parking
	 * @return devuelve el numero de plazas libres
	 */
	public int plazasLibres() {
		int plazasLibres = 0;
		for (int i = 0; i < plazas.length; i++) {
			if (!plazas[i].ocupada) {
				plazasLibres++;
			}
		}
		return plazasLibres;
	}

	/**
	 * La clase parking tiene un array de Plazas
	 * recorremos ese array y mostramos el numero de plaza y si esta ocupado true o false
	 * y escribimos por pantalla
	 */
	public void parkingPlazasOcupadas() {
		String frase = "Plazas ocupadas del parking: \n";
		for (int i = 0; i < plazas.length; i++) {
			frase += "[" + plazas[i].numPlaza + "-" + String.valueOf(plazas[i].ocupada) + "] ";
		}
		System.out.println(frase);
	}
}

public class SimulacionParkingCamion {
	public static void main(String[] args) {

		int numPlazas = Integer.parseInt(args[0]);
		int numCoches = Integer.parseInt(args[1]);
		int numCamiones = Integer.parseInt(args[2]);
		int numRepeticionesEntrarParking; // Para que la ejecucion pare alguna vez, si es 0 o null no parar�
		int tiempoEsperaEjecucion = 2; // tiempo de espera antes de ejecutar y no especificar el args[2]
		try {
			numRepeticionesEntrarParking = Integer.parseInt(args[3]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("No se ha especificado cuantas veces un coche va a entrar en el parking. args[2]");
			System.out.println("Por defecto se pone a 0");
			System.out.println(tiempoEsperaEjecucion + " segundos y empieza ejecucion.");
			try {
				Thread.sleep(tiempoEsperaEjecucion * 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			numRepeticionesEntrarParking = 0;
		}

		Parking parking = new Parking(numPlazas);

		for (int i = 0; i < numCamiones; i++) {
			new Camion(i, parking, numRepeticionesEntrarParking, 2, "Camion").start();
		}
		for (int i = 0; i < numCoches; i++) {
			new Coche(i, parking, numRepeticionesEntrarParking, 1, "Coche").start();
		}
		

	}
}
