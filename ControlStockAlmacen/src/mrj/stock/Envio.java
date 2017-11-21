package mrj.stock;

import java.util.Random;

public class Envio extends Thread {
	
	Almacen almacen;

	public Envio(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public void run() {
		while (!almacen.isError()) {
			try {
				sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			almacen.envio((new Random().nextInt(600))+400);
		}
		System.out.println("envio fin");
	}

}
