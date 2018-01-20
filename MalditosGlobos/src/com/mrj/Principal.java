package com.mrj;

public class Principal {

	public static void main(String[] args) {
		AlmacenGlobos almacenGlobos = new AlmacenGlobos();
		
		HinchaGlobos HG1 = new HinchaGlobos(almacenGlobos, 1);
		PinchaGlobos PG1 = new PinchaGlobos(almacenGlobos, 1);
		HinchaGlobos HG2 = new HinchaGlobos(almacenGlobos, 2);
		PinchaGlobos PG2 = new PinchaGlobos(almacenGlobos, 2);
		HinchaGlobos HG3 = new HinchaGlobos(almacenGlobos, 3);
		PinchaGlobos PG3 = new PinchaGlobos(almacenGlobos, 3);
		HinchaGlobos HG4 = new HinchaGlobos(almacenGlobos, 4);
		PinchaGlobos PG4 = new PinchaGlobos(almacenGlobos, 4);
		HinchaGlobos HG5 = new HinchaGlobos(almacenGlobos, 5);
		PinchaGlobos PG5 = new PinchaGlobos(almacenGlobos, 5);
		
		
		HG1.start();
		PG1.start();
		HG2.start();
		PG2.start();
		HG3.start();
		PG3.start();
		HG4.start();
		PG4.start();
		HG5.start();
		PG5.start();
		
		
		try {
			HG1.join();
			PG1.join();
			HG2.join();
			PG2.join();
			HG3.join();
			PG3.join();
			HG4.join();
			PG4.join();
			HG5.join();
			PG5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Fin Principal.");
	}

}
