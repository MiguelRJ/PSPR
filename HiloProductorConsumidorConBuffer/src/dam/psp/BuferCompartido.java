package dam.psp;

public class BuferCompartido implements Bufer {

	private int bufer = -1;
	private int contadorOcupado = 0;
	
	@Override
	public synchronized int leer() {
		contadorOcupado--;
		return contadorOcupado;
	}

	@Override
	public synchronized void escribir(int valor) {
		contadorOcupado++;
	}

	@Override
	public void mostrarEstado(String cadena) {
		StringBuffer linea = new StringBuffer(cadena);
		linea.setLength(80);
		linea.append(bufer + " " + contadorOcupado);
		System.out.println(linea);
		System.out.println();
	}

}