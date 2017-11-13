package mrj.Rel1.E4;

import java.util.Random;

/*
 * Como muestro el caja.resultado, como espero a que todos los clientes e hayan ido
 */

class Caja {
	
	int numCaja;
	boolean ocupada;
	int resultado;
	String factura;
	
	public Caja(int numCajas) {
		numCaja = numCajas;
		ocupada = false;
		resultado = 0;
		factura =  "Caja: " + numCaja + "\n";
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
		System.out.println("Caja:"+numCaja+" cliente "+cliente.numCliente+" paga: "+valorCompra);
	}
}

class Cliente extends Thread {
	
	int numCliente;
	Caja caja;
	
	public Cliente(int numClientes, Caja cajas) {
		numCliente = numClientes;
		caja = cajas;
	}
	
	public void comprar() {
		try {
			Thread.sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pagarCaja() {
		caja.comprarCaja(new Random().nextInt(50), this);
	}
	
	@Override
	public void run() {
		comprar();
		caja.usarCaja();
		pagarCaja();
		caja.dejarCaja();
	}
	
}

class Supermercado extends Thread {
	
	Caja[] caja;
	Cliente[] cliente;
	
	public Supermercado(Caja[] cajas, Cliente[] clientes) {
		caja = cajas;
		cliente = clientes;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < cliente.length; i++) {
			cliente[i].start();
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
			caja[i] = new Caja(i);
		}
		for (int i = 0; i < numClientes; i++) {
			cliente[i] = new Cliente(i,caja[new Random().nextInt(caja.length)]);
		}
		
		Supermercado supermercado = new Supermercado(caja, cliente);
		
		supermercado.start();
		try {
			supermercado.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < caja.length; i++) {
			System.out.println(caja[i].resultado);
		}
		
	}

}
