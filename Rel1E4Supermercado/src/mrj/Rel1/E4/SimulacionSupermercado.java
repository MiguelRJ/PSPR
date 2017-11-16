package mrj.Rel1.E4;

import java.util.Random;

class Caja {

	int numCaja;
	boolean ocupada;
	int resultado;
	String factura;
	int[] colaClientes;
	int ultimaPosCola;
	int siguientePosCola;

	public Caja(int numCajas, int numClientes) {
		numCaja = numCajas;
		ocupada = false;
		resultado = 0;
		factura = "Caja: " + numCaja + "\n";
		colaClientes = new int[numClientes];
		ultimaPosCola = 0;
		siguientePosCola = 0;
	}

	public synchronized void usarCaja(int numCliente) {
		colaClientes[ultimaPosCola++] = numCliente;
		System.out.println("Nuevo en cola. Cliente: "+numCliente + " Caja: "+this.numCaja+ " Pos: "+(ultimaPosCola-1));
		while (ocupada || colaClientes[siguientePosCola] != numCliente ) {
			try {
				System.out.println("[PRE] cliente " + numCliente + " y es turno de " + colaClientes[siguientePosCola]);
				wait();
				System.out.println("[POST] cliente " + numCliente + " y es turno de " + colaClientes[siguientePosCola]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		siguientePosCola++;
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
	boolean compraTerminada;

	public Cliente(int numCliente, Caja cajas) {
		this.numCliente = numCliente;
		caja = cajas;
		compraTerminada = false;
	}

	public void comprar() {
		try {
			sleep((long) new Random().nextInt(2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pagarCaja() {
		caja.comprarCaja(new Random().nextInt(50), this);
		try {
			sleep((long) new Random().nextInt(2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void salirSupermercado() {
		compraTerminada = true;
	}

	@Override
	public void run() {
		comprar();
		caja.usarCaja(numCliente);
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

public class SimulacionSupermercado {

	public static void main(String[] args) {

		int numCajas = Integer.parseInt(args[0]);
		int numClientes = Integer.parseInt(args[1]);

		Caja[] caja = new Caja[numCajas];
		Cliente[] cliente = new Cliente[numClientes];

		for (int i = 0; i < numCajas; i++) {
			caja[i] = new Caja(i,numClientes);
		}
		for (int i = 0; i < numClientes; i++) {
			cliente[i] = new Cliente(i, caja[new Random().nextInt(caja.length)]);
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
