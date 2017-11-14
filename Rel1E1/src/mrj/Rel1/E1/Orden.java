package mrj.Rel1.E1;

class Saludar extends Thread {
	
	String name;
	 
	public Saludar(String name) {
		this.name = name;
	}
	
	@Override
	public void start() {
		try {
			sleep((long)(Math.random()));// Tiempo de espera aleatorio
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hola, soy el hilo "+ name);
	}
	 
 }

public class Orden {

	public static void main(String[] args) {
		Saludar saludo1 = new Saludar("numero 1");
		Saludar saludo2 = new Saludar("numero 2");
		
		/* Se fuerza poniendolo antes? 
		 * Habra que hacer el .join()?*/
		saludo2.start();
		try {
			saludo2.join();// Me aseguro que saludo 2 acabe antes de seguir.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saludo1.start();
		System.out.println("Hilo principal acabado.");
	}

}