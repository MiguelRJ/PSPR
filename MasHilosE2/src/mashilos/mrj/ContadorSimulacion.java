package mashilos.mrj;

class Dato {
	private int dato;
	
	public Dato() {
		this.dato = 0;
	}
	
	public void incDato() {
		dato++;
	}
	
	public int getDato() {
		return dato;
	}
}

class Contador extends Thread{
	
	int veces;
	Dato dato;
	
	public Contador(int veces, Dato dato) {
		this.veces = veces;
		this.dato = dato;
	}
	
	@Override
	public void run() {
		for (int i=0; i<veces; i++) {
			dato.incDato();
		}
	}
}

public class ContadorSimulacion {

	public static void main(String[] args) {
		int veces = 5000;
		Dato dato = new Dato();
		Contador c1 = new Contador(veces, dato);
		Contador c2 = new Contador(veces, dato);
		Contador c3 = new Contador(veces, dato);
		Contador c4 = new Contador(veces, dato);
		Contador c5 = new Contador(veces, dato);
		
		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
		
		try {
			c1.join();
			c2.join();
			c3.join();
			c4.join();
			c5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(dato.getDato());
	}

}
