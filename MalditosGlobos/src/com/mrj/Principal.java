package com.mrj;

public class Principal {

	public static void main(String[] args) {
		AlmacenGlobos almacenGlobos = new AlmacenGlobos();
		
		HinchaGlobos HG1 = new HinchaGlobos(almacenGlobos, 1);
		HinchaGlobos HG2 = new HinchaGlobos(almacenGlobos, 2);
		HinchaGlobos HG3 = new HinchaGlobos(almacenGlobos, 3);
		HinchaGlobos HG4 = new HinchaGlobos(almacenGlobos, 4);
		HinchaGlobos HG5 = new HinchaGlobos(almacenGlobos, 5);
		
		
		HG1.start();
		HG2.start();
		HG3.start();
		HG4.start();
		HG5.start();
		
		
		try {
			HG1.join();
			HG2.join();
			HG3.join();
			HG4.join();
			HG5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Fin Principal.");
	}

}
