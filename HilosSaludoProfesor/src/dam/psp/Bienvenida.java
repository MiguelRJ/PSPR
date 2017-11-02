package dam.psp;

public class Bienvenida {
	boolean comienzaClase;
	
	public Bienvenida() {
		comienzaClase = false;
	}
	
	public synchronized void saludarAlProfesor(String nombre) {
		System.out.println("El alumno " + nombre + " quiere saludar");
		while (!comienzaClase) {
			try {
				this.wait();
				//System.out.println("El alumno " + nombre + " dice: ¡Buenos dias!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		System.out.println("El alumno " + nombre + " dice: ¡Buenos dias!");
	}
	
	public synchronized void profesorSaluda() {
		System.out.println("El profesor dice: ¡Buenos dias!");
		this.comienzaClase=true;
		this.notifyAll();
	}
	
}