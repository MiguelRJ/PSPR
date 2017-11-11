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
		palilloIzquierda = cena.getPalilloIzquierda(numeroDeFisolofo);
		palilloDerecha = cena.getPalilloDerecha(numeroDeFisolofo);
	}

	public void pensar() {
		System.out.println("El filosofo " + numeroDeFisolofo + " esta pensando...");
		try {
			Thread.sleep((long) Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void cogerPalillos() {
		cena.getPalillo(palilloIzquierda).coger();
		cena.getPalillo(palilloDerecha).coger();
		System.out.println("Filosofo: " + numeroDeFisolofo + " cojo Palillos [" + palilloIzquierda + "] [" + palilloDerecha + "]");
	}

	public void soltarPalillos() {
		cena.getPalillo(palilloIzquierda).soltar();
		cena.getPalillo(palilloDerecha).soltar();
		System.out.println("Filosofo: " + numeroDeFisolofo + " suelto Palillos [" + palilloIzquierda + "] [" + palilloDerecha + "]");

	}

	public void comer() {
		System.out.println("El filosofo " + numeroDeFisolofo + " esta comiendo... Palillos [" + palilloIzquierda + "] ["
				+ palilloDerecha + "]");
		try {
			Thread.sleep((long) Math.random());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < veces; i++) {
			pensar();				
				/*if (cena.getPalilloIzquierda(numeroDeFisolofo).enUso) {
					System.out.println("El filosofo " + numeroDeFisolofo + " Espera PalilloI [" + palilloIzquierda + "]");
				}
				if (cena.getPalilloDerecha(numeroDeFisolofo).enUso) {
					System.out.println("El filosofo " + numeroDeFisolofo + " Espera PalilloD [" + palilloDerecha + "]");
				}*/
				//System.out.println("El filosofo " + numeroDeFisolofo + " Espera Palillos [" +palilloIzquierda + "] [" + palilloDerecha + "]");
			while ( cena.getPalillo(palilloIzquierda).enUso || cena.getPalillo(palilloDerecha).enUso ) {
				
			}
			cogerPalillos();
			comer();
			soltarPalillos();
		}
	}
}