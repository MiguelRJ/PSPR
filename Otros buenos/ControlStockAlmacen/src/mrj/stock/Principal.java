package mrj.stock;

public class Principal {

	public static void main(String[] args) {
		Almacen almacen = new Almacen(20000, 8000);
		
		Envio envio = new Envio(almacen);
		Retirada retirada = new Retirada(almacen);
		
		envio.start();
		retirada.start();
		
		try {
			envio.join();
			retirada.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nPRINCIPAL ACABADO");

	}

}
