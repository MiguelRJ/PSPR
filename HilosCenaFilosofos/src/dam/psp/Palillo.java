package dam.psp;

public class Palillo {
	
	int numero;
	boolean enUso;
	
	public Palillo(int numeroPalillo) {
		numero = numeroPalillo;
		enUso = false;
	}
	
	public synchronized void coger() {
		while (enUso) {
			System.out.println("palillo ["+numero+"] en uso, espera");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.enUso = true;
		System.out.println("palillo ["+numero+"] ocupado por "+Thread.currentThread().getName());
	}
	
	public synchronized void soltar() {
		this.enUso = false;
		notify();
		System.out.println("palillo ["+numero+"] soltado");
	}

}
