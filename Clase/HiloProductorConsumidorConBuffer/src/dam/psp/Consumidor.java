package dam.psp;

public class Consumidor extends Thread{
	
	private Bufer compartido;
	private int veces; // Veces que tiene que iterar el consumo de enteros
	
	public Consumidor(Bufer elbufer, int nveces) {
		compartido = elbufer;
		veces = nveces;
	}
	
	@Override
	public void run() {
		int suma = 0;
		int valor = 0;
		for (int i = 0; i < veces; i++) {
			try {
				Thread.sleep((int)(Math.random()*3001));
				valor = compartido.leer();
				suma += valor;
				//System.out.println(getName() + " valor: " + valor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName() + " temino de leer un total de " + suma + " datos.");
	}
}