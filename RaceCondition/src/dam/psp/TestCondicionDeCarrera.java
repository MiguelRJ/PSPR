package dam.psp;

import java.util.concurrent.atomic.AtomicInteger;

class Contador {
	// public static volatile int cuenta = 0;
	public final static AtomicInteger cuenta = new AtomicInteger(0);	
}

class Sumador extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 999999; i++) {
			// Contador.cuenta++;
			Contador.cuenta.incrementAndGet();
		}
	}
}
class Restador extends Thread{
	@Override
	public void run() {
		for (int i = 0; i < 5000; i++) {
			// Contador.cuenta--;
			Contador.cuenta.decrementAndGet();
		}
	}
}

public class TestCondicionDeCarrera {
	public static void main(String[] args) {
		Sumador s = new Sumador();
		Restador r = new Restador();
		s.start();
		r.start();
		try {
			s.join();
			r.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Valor final del contador: "+ Contador.cuenta);
	}
}
