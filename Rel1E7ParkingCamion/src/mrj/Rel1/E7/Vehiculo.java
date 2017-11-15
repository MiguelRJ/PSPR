package mrj.Rel1.E7;

public class Vehiculo extends Thread {
	
	Parking parking;
	String nombreVehiculo;
	int tamanoOcupado;
	int numVehiculo;
	int numRepeticionesEntrarParking;
	Plaza[] plaza; // array de plazas que ocupa el vehiculo
	
	public Vehiculo(Integer numVehiculo, Parking parking, int numRepeticionesEntrarParking,int tamano, String nombre) {
		this.numVehiculo = numVehiculo;
		this.parking = parking;
		this.numRepeticionesEntrarParking = numRepeticionesEntrarParking;
		this.tamanoOcupado = tamano;
		this.nombreVehiculo = nombre;
		this.plaza = new Plaza[tamanoOcupado];
	}
	
	public void entrarParking() {
		System.out.println(nombreVehiculo+": "+numVehiculo+ " quiere entrar");
		parking.entrarParking(this);
	}
	
	public void esperarParking() {
		try {
			System.out.println(nombreVehiculo+": "+numVehiculo+ " esta aparcado...");
			sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void salirParking() {
		System.out.println(nombreVehiculo+": "+numVehiculo+ " esta saliendo");
		parking.salirParking(this);
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

class Coche extends Vehiculo{

	public Coche(Integer numVehiculo, Parking parking, int numRepeticionesEntrarParking, int tamano, String nombre) {
		super(numVehiculo, parking, numRepeticionesEntrarParking, tamano, nombre);
	}
	
}

class Camion extends Vehiculo{

	public Camion(Integer numVehiculo, Parking parking, int numRepeticionesEntrarParking, int tamano, String nombre) {
		super(numVehiculo, parking, numRepeticionesEntrarParking, tamano, nombre);
	}
	
}
