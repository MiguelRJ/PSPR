package dam.psp;

public class Cena {

	Palillo[] palillos;
	int comensales;
	
	public Cena(int numComensales) {
		this.comensales = numComensales;
		palillos = new Palillo[numComensales];
		for (int i = 0; i < comensales; i++) {
			palillos[i] = new Palillo(i);
		}
	}
	
	public synchronized Palillo getPalillo (int numComensal) {
		return palillos[numComensal];
	}
	
	public int getPalilloIzquierda(int numComensal) {
		return palillos[(numComensal+1)%comensales].numero;
	}

	public int getPalilloDerecha(int numComensal) {
		return palillos[numComensal].numero;
	}

	
}
