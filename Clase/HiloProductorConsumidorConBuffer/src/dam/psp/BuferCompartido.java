package dam.psp;

public class BuferCompartido implements Bufer {

	private int bufer = -1;
	private int contadorOcupado = 0;
	
	@Override
	public synchronized int leer() {
		while (contadorOcupado == 0) {
			mostrarEstado(Thread.currentThread().getName() + " trata de leer: " + bufer);
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mostrarEstado(Thread.currentThread().getName() + " lee: " + bufer);
		contadorOcupado = 0;
		this.notify();
		return bufer;
	}

	@Override
	public synchronized void escribir(int valor) {
		while (contadorOcupado == 1) {
			mostrarEstado(Thread.currentThread().getName() + " trata de escribir: " + valor);
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		bufer=valor;
		contadorOcupado = 1;
		mostrarEstado(Thread.currentThread().getName() + " escribe: " + bufer);
		this.notify();
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