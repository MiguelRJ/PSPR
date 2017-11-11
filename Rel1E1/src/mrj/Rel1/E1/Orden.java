package mrj.Rel1.E1;

public class Orden {

	public static void main(String[] args) {
		Saludar saludo1 = new Saludar("Hilo 1");
		Saludar saludo2 = new Saludar("Hilo 2");

		saludo2.start();
		try {
			saludo2.join();
		}catch(Exception e) {
			
		}
		
		saludo1.start();
		try {
		
		}catch(Exception e) {
			
		}
		
		
	}

}

class Saludar extends Thread {
	
	String name;
	 
	public Saludar(String name) {
		this.name = name;
	}
	
	@Override
	public synchronized void start() {
		System.out.println("Hola, soy el hilo"+ name);
	}
	 
 }