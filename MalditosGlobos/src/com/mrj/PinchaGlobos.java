package com.mrj;

public class PinchaGlobos extends Thread {

	AlmacenGlobos almacen;
	String nombre;
	int MAXTIEMPO = 10000; // tiempo maximo de espera para pinchar en milisegundos, es decir, 10 segundos
	int MINTIEMPO = 1000; // tiempo minimo de espera para pinchar en milisegundos, es decir, 1 segundo

	public PinchaGlobos(AlmacenGlobos almacen, int numeroPG) {
		this.almacen = almacen;
		this.nombre = "PG" + numeroPG;
	}

	@Override
	public void run() {
		while (almacen.quedanGlobos()) {
			try {
				sleep((long) (Math.random() * (MAXTIEMPO - MINTIEMPO) + MINTIEMPO)); // paro el hilo durante un tiempo random entre 1 y 10 segundos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int posGlobo = almacen.entregarGloboParaPinchar(nombre); // obtengo un globo aleatorio del almacen
			if (almacen.getGloboVolumen(posGlobo) > 1) { // si el valoumen del globo es mayor o igual a 1 es que se esta inflando;
				almacen.pincharGlobo(posGlobo, nombre);  // como se esta inflando lo pincho
			} else {
				// System.out.println("GLOBO "+posGlobo+" YA ESTA ROTO "+nombre);
			}
		}
		System.out.println(nombre + " No hay mas globos");
	}
}
