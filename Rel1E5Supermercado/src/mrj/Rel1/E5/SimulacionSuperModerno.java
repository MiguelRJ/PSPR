package mrj.Rel1.E5;

import java.util.Random;

/*
 * Como muestro el caja.resultado, 
 * como espero a que todos los clientes se hayan ido - Resuelto en metodo hayClientes() de supermercado
 * 
 * Los clientes no van por cola, el notify avisa a que alguien nuevo entre
 */

class Cola {
	
	int numCola;
	int siguienteCola;
	Cliente[] clientes;
	
	public Cola(int numClientes) {
		clientes = new Cliente[numClientes];
		numCola = 0;
		siguienteCola = 0;
	}
	
	public void ponerseEnCola(Cliente cliente) {
		clientes[numCola] = cliente;
		System.out.println("Cliente: "+cliente.numCliente + " Pos cola: "+numCola);
		numCola++;
	}
	
	public synchronized Cliente siguienteCola() {
		//System.out.println("Siguiente en cola: "+clientes[siguienteCola].numCliente);
		return clientes[siguienteCola++];
	}
}

class Caja {

	int numCaja;
	boolean ocupada;
	int resultado;
	String factura;

	public Caja(int numCajas) {
		numCaja = numCajas;
		ocupada = false;
		resultado = 0;
		factura = "Caja: " + numCaja + "\n";
	}

	public synchronized void usarCaja() {
		while (ocupada) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ocupada = true;
	}

	public synchronized void dejarCaja() {
		ocupada = false;
		notify();
	}

	public void comprarCaja(int valorCompra, Cliente cliente) {
		resultado += valorCompra;
		factura += cliente.numCliente + " ha gastado: " + valorCompra + "\n";
		System.out.println("Caja:" + numCaja + " cliente " + cliente.numCliente + " paga: " + valorCompra);
	}
}

class Cliente extends Thread {

	int numCliente;
	Caja caja;
	Cola cola;
	boolean compraTerminada;

	public Cliente(int numClientes, Caja cajas, Cola colas) {
		numCliente = numClientes;
		caja = cajas;
		compraTerminada = false;
		cola = colas;
	}

	public void comprar() {
		try {
			Thread.sleep((long) Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pagarCaja() {
		caja.comprarCaja(new Random().nextInt(50), this);
	}

	public void salirSupermercado() {
		compraTerminada = true;
	}

	@Override
	public void run() {
		comprar();
		cola.ponerseEnCola(this);
		while (cola.siguienteCola() != Cliente.this) {
			
		}
		caja.usarCaja();
		pagarCaja();
		caja.dejarCaja();
		salirSupermercado();
	}

}

class Supermercado extends Thread {

	Caja[] caja;
	Cliente[] cliente;
	boolean clienteComprando; //Para saber si hay algun cliente comprando en el super

	public Supermercado(Caja[] cajas, Cliente[] clientes) {
		caja = cajas;
		cliente = clientes;
	}

	/**
	 * Comprueba que no queden clientes en el super
	 * inicializo [clienteComprando] a false y si ya no cambia entonces es que no hay clientes comprando
	 * 
	 * podria hacer que cuando encuentre un cliente comprando haga un return true, pero no haria el for completo
	 * @return 
	 * false si todos los clientes han acabado de comprar
	 * true si cualquier cliente sigue comprando
	 */
	public boolean hayClientes() {
		clienteComprando = false;
		for (int i = 0; i < cliente.length; i++) {
			if (!cliente[i].compraTerminada) { // con que haya un solo cliente comprando
				clienteComprando = true; // lo pondra a false
			}
		}
		return clienteComprando;
	}

	@Override
	public void run() {
		for (int i = 0; i < cliente.length; i++) {
			cliente[i].start();
		}
		while (hayClientes()) {
			//System.out.println(".. hay clientes"); // cuando este mensaje deja de aparecer es cuando hace las facturas
		}
	}
}

public class SimulacionSuperModerno {

	public static void main(String[] args) {

		int numCajas = Integer.parseInt(args[0]);
		int numClientes = Integer.parseInt(args[1]);

		Cola cola = new Cola(numClientes);
		Caja[] caja = new Caja[numCajas];
		Cliente[] cliente = new Cliente[numClientes];

		for (int i = 0; i < numCajas; i++) {
			caja[i] = new Caja(i);
		}
		for (int i = 0; i < numClientes; i++) {
			cliente[i] = new Cliente(i, caja[new Random().nextInt(caja.length)],cola);
		}

		Supermercado supermercado = new Supermercado(caja, cliente);

		supermercado.start();
		try {
			supermercado.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < caja.length; i++) {
			System.out.println("Factura de caja " + caja[i].numCaja + ": " + caja[i].resultado);
		}

	}

}
