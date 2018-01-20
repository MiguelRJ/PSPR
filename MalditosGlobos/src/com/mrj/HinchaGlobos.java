package com.mrj;

public class HinchaGlobos extends Thread {

	AlmacenGlobos almacen;
	String nombre;

	public HinchaGlobos(AlmacenGlobos almacen, int numeroHG) {
		this.almacen = almacen;
		this.nombre = "HG" + numeroHG;
	}

	@Override
	public void run() {
		while (almacen.quedanGlobos()) {
			int posGlobo = almacen.entregarGloboParaHinchar(nombre); // obtengo el siguiente globo del almacen y se pone su volumen a 1
			if (posGlobo != -1) { // en el almacen si posSiguienteGlobo < maxGlobos devuelve -1 por eso esta guarda

				while (almacen.getGloboVolumen(posGlobo) < 5 && almacen.getGloboVolumen(posGlobo) > 0) { // mientras que el volumen este entre 0 y 5
					almacen.hincharGlobo(posGlobo, nombre); // hincho el globo
					try {
						sleep(1000); // espero 1 segundo antes de volver a hinchar
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (almacen.getGloboVolumen(posGlobo) == 5) { // si el volumen es 5 quiere decir que el globo explota
						almacen.explotarGlobo(posGlobo, nombre);
					}
				}

			}
		}
		System.out.println(nombre + " No hay mas globos");
	}
}
