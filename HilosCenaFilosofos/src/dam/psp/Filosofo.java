package dam.psp;

public class Filosofo implements Runnable {
	
	int palilloIzquierda, palilloDerecha; // Su palillo de izquierda o derecha
	int numeroDeFisolofo; // Su numero en la mesa
	int veces; // Veces que va a intentar comer
	Cena cena;
	
	public Filosofo(int num, int numVeces, Cena cenaCompartida) {
		numeroDeFisolofo = num;
		veces = numVeces;
		cena = cenaCompartida;
	}

	public void pensar() {
		System.out.println("El filosofo "+numeroDeFisolofo+" esta pensando...");
	}

	public void cogerPalillos() {
		palilloIzquierda = cena.getPalilloIzquierda(numeroDeFisolofo).numero;
		palilloDerecha = cena.getPalilloDerecha(numeroDeFisolofo).numero;
	}

	public void soltarPalillos() {
		
	}

	public void comer() {
		System.out.println("El filosofo "+numeroDeFisolofo+" esta comiendo...");
	}

	@Override
	public void run() {
		for (int i = 0; i < veces; i++) {
			pensar();
			cogerPalillos();
			comer();
			soltarPalillos();
		}
	}
}