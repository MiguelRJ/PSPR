package com.mrj;

public class HinchaGlobos extends Thread {
	
	AlmacenGlobos almacen;
	String nombre;
	
	public HinchaGlobos (AlmacenGlobos almacen, int numeroHG) {
		this.almacen = almacen;
		this.nombre = "HG"+numeroHG;
	}
	
	@Override
	public void run() {
		while (almacen.quedanGlobos()) {
			int globoCogido = almacen.entregarGlobo(nombre);
			if (globoCogido != -1) {
				while (almacen.getGloboVolumen(globoCogido) < 5) {
					almacen.hincharGlobo(globoCogido,nombre);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				almacen.explotarGlobo(globoCogido, nombre);
			}
		}
		System.out.println(nombre+" No hay mas globos");
	}
}
