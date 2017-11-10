package dam.psp;

public class Cena {

	Palillo[] palillos;
	int comensales;
	
	public Cena(int numComensales) {
		this.comensales = numComensales;
		for (int i = 0; i < comensales; i++) {
			palillos[i] = new Palillo(i);
		}
	}

	public Palillo getPalilloDerecha(int numComensal) {
		
		return palillos[numComensal];
	}

	public Palillo getPalilloIzquierda(int numComensal) {

		return palillos[(numComensal-1)%comensales];
	}
}
