package dam.psp;

class Inutil {
	private int a = 0;
	private int b = 0;

	public synchronized void marcar_5() {
		a = 5;
		System.out.println("Marcando... no me metas bulla!");
		try {
			Thread.sleep(1000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		b = 5;
	}

	public synchronized boolean esVerdad() {
		System.out.println("Preguntando... no me metas bulla!");
		try {
			Thread.sleep(1000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return a == 0 || b == 5;
	}
}

class HiloA extends Thread {
	private Inutil i;

	public HiloA(Inutil in) {
		this.i = in;
	}
	
	@Override
	public void run() {
		i.marcar_5();
	}
}

class HiloB extends Thread {
	private Inutil i;

	public HiloB(Inutil in) {
		this.i= in;
	}
	
	@Override
	public void run() {
		if(i.esVerdad()) {
			System.out.println("Verdadero");
		} else {
			System.out.println("Mas falso que judas");
		}
	}
}

public class PruebaHilosSincronizadosInutil {

	public static void main(String[] args) {
		Inutil in = new Inutil();
		
		HiloA ha = new HiloA(in);// Los dos trabajan con el mismo objeto inutil
		HiloB hb = new HiloB(in);
		
		ha.start();
		hb.start();
		
		try {
			ha.join();
			hb.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
