package mrj.stock;

public class Almacen {

	int MAXPIEZAS;
	int piezas;
	boolean error;
	int numEnvios;
	boolean fabricaPuedePedir;

	public Almacen(int MAXPIEZAS, int piezas) {
		this.MAXPIEZAS = MAXPIEZAS;
		this.piezas = piezas;
		error = false;
		numEnvios = 0;
		fabricaPuedePedir = true;
	}

	public boolean isError() {
		return error;
	}

	public synchronized void envio(int numPiezas) {
		while (fabricaPuedePedir) {// es necesario para que no envio no empiece antes que retirada
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!error) {
			System.out.println("Llegan " + numPiezas + " al almacen");
			if ((piezas + numPiezas) > MAXPIEZAS) {
				System.out.println("Error: El nuevo cargamento no cabe en el almacen.");
				System.out.println("Almacen tiene "+piezas+" Llegan "+numPiezas);
				error = true;
				fabricaPuedePedir = true;
			} else {
				piezas += numPiezas;
				System.out.println("Hay " + piezas + " en el almacen");
				numEnvios++;
				
			}
			if (numEnvios == 3) {
				numEnvios = 0;
				fabricaPuedePedir = true;
			}
		}
		notify();
	}

	public synchronized void retirada(int numPiezas) {
		while (!fabricaPuedePedir) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!error) {
			if ( (piezas - numPiezas) <= 0 ) {
				System.out.println("Error: Almacen no dispone de piezas suficientes.");
				System.out.println("Fabrica ha pedido "+numPiezas+" Alamcen tiene "+piezas);
				error = true;
			} else {
				System.out.println("Pedido de " + numPiezas + " piezas");
				System.out.println("Hay " + piezas + " en el almacen");
				piezas -= numPiezas;
			}
			fabricaPuedePedir = false;
		}
		notify();
	}

}
