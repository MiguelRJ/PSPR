package mrj.stock;

import java.util.Random;

public class Retirada extends Thread {

	Almacen almacen;
	int diasTrancurridos;

	public Retirada(Almacen almacen) {
		this.almacen = almacen;
		diasTrancurridos = 0;
	}

	@Override
	public void run() {
		while (!almacen.isError()) {
			try {
				sleep(2400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			diasTrancurridos++;
			System.out.println("\nDia: "+diasTrancurridos);
			almacen.retirada((new Random().nextInt(500))+2000);
		}
		System.out.println("retirada fin");
	}
}
