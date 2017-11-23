package dam.psp;

public class Profesor extends Thread{
	String nombre;
	Bienvenida saludo;
	
	public Profesor(String nombre,Bienvenida b) {
		this.nombre = nombre;
		this.saludo = b;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			saludo.profesorSaluda();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}