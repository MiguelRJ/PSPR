package dam.psp;

public class Productor extends Thread {
	
	private Bufer compartido;
	private int veces; // Veces que tiene que iterar las producciones de enteros
	
	public Productor(Bufer elbufer, int nveces) {
		compartido = elbufer;
		veces = nveces;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < veces; i++) {
			try {
				Thread.sleep((int)(Math.random()*3001));
				compartido.escribir(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName() + " temino de producir datos.");
	}
}