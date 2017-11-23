package dam.psp;

public class PruebaBuferCompartido {

	public static void main(String[] args) {
		//Bufer bcompartido = new BuferCompartido();
		Bufer bcompartido = new BufferCompartidoCircular();
		
		Productor prod = new Productor(bcompartido, 10);
		Consumidor cons1 = new Consumidor(bcompartido, 6);
		Consumidor cons2 = new Consumidor(bcompartido, 4);
		
		StringBuffer encabezado = new StringBuffer("Operacion");
		encabezado.setLength(40);
		encabezado.append("Buffer       Contador ocupado");
		System.out.println(encabezado);
		System.out.println();
		
		bcompartido.mostrarEstado("Estado inicial");
		
		prod.start();
		cons1.start();
		cons2.start();
		
		try {
			prod.join();
			cons1.join();
			cons2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hilo principal ha finalizado");
	}

}