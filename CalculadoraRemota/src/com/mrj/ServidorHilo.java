package com.mrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorHilo extends Thread {

	private Socket socket;
	// private int idCliente; // no no neceisto porque ya teno clienteName
	private String ClienteName; // para mostrar el nombre del cliente con formato concreto
	private String bienvenida;
	private String instrucciones;
	private iCalculadora calculadora = null;
	private float a = 0;
	private float b = 0;

	static final String HOST = "192.168.1.132"; // casa -> 1.132; profe -> 3.57; clase -> 3.32
	static final int PUERTOCALC = 8889;
	BufferedReader br;
	PrintWriter pw = null;

	// instrucciones que realiza la calculadora
	static final String SUMA = "s";
	static final String RESTA = "r";
	static final String PRODUCTO = "p";
	static final String DIVISION = "d";
	static final String RAIZ = "ra";
	static final String POTENCIA = "po";
	static final String SIGUIENTEPRIMO = "sp";

	// strings usados a la hora de comprobar mensajes entre cliente y servidor
	static final String salida = "q";
	static final String RESULTADO = "Resultado operacion";
	static final String ERROR = "ERROR";

	public ServidorHilo(Socket socket, int idCliente) {
		this.socket = socket;
		// this.idCliente = idCliente;
		this.ClienteName = "Cliente[" + idCliente + "]";
		this.bienvenida = "Binvenido/a a mi canal. ";
		this.instrucciones = "Opciones a calcular: " + "-Suma(" + SUMA + ") " + "-Resta(" + RESTA + ") " + "-Producto("
				+ PRODUCTO + ") " + "-Division(" + DIVISION + ") " + "-Raiz(" + RAIZ + ") " + "-Potencia(" + POTENCIA
				+ ") " + "-SiguientePrimo(" + SIGUIENTEPRIMO + ")";
	}

	@Override
	public void run() {
		Registry registry = null;
		String mensajeCliente;

		try {
			registry = LocateRegistry.getRegistry(HOST, PUERTOCALC);
			calculadora = (iCalculadora) registry.lookup("Calculadora");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); // true limpia el
																									// buffer
			pw.println(ClienteName + ", " + this.bienvenida + this.instrucciones);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			do {

				System.out.println(ClienteName + " esperando respuesta... ");
				mensajeCliente = br.readLine();

				if (!mensajeCliente.equals(salida)) {

					System.out.println(ClienteName + " quiere: " + mensajeCliente);

					if (mensajeCliente.equals(SUMA)) {
						String clienteInstruccion = ClienteName + " suma(" + SUMA + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime primer operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar primer operador: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						System.out.println(clienteInstruccion + " esperando b");
						pw.println("	" + clienteInstruccion + " dime segundo operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar segundo operador: ");
							mensajeCliente = br.readLine();
						}
						b = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de " + a + " + " + b + " = " + calculadora.suma(a, b));
						System.out.println(clienteInstruccion + " fin.");

					}
					if (mensajeCliente.equals(RESTA)) {
						String clienteInstruccion = ClienteName + " resta(" + RESTA + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime primer operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar primer operador: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						System.out.println(clienteInstruccion + " esperando b");
						pw.println("	" + clienteInstruccion + " dime segundo operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar segundo operador: ");
							mensajeCliente = br.readLine();
						}
						b = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de " + a + " - " + b + " = " + calculadora.resta(a, b));
						System.out.println(clienteInstruccion + " fin.");

					}
					if (mensajeCliente.equals(PRODUCTO)) {
						String clienteInstruccion = ClienteName + " producto(" + PRODUCTO + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime primer operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar primer operador: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						System.out.println(clienteInstruccion + " esperando b");
						pw.println("	" + clienteInstruccion + " dime segundo operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar segundo operador: ");
							mensajeCliente = br.readLine();
						}
						b = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de " + a + " * " + b + " = " + calculadora.producto(a, b));
						System.out.println(clienteInstruccion + " fin.");

					}
					if (mensajeCliente.equals(DIVISION)) {
						String clienteInstruccion = ClienteName + " division(" + DIVISION + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime primer operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar primer operador: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						System.out.println(clienteInstruccion + " esperando b");
						pw.println("	" + clienteInstruccion + " dime segundo operador: ");
						mensajeCliente = br.readLine();
						if (canFloatTryParse(mensajeCliente)) { // si se puede hacer float 
							if (Float.parseFloat(mensajeCliente) == 0) { // y al hacer float es = 0
								mensajeCliente = "Division por cero, atontao"; // cambio el mensaje para que entre en el while
							}
						}
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar segundo operador: ");
							mensajeCliente = br.readLine();
							if (canFloatTryParse(mensajeCliente)) {
								if (Float.parseFloat(mensajeCliente) == 0) {
									mensajeCliente = "Division por cero, atontao";
								}
							}
						}
						b = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de " + a + " * " + b + " = " + calculadora.division(a, b));
						System.out.println(clienteInstruccion + " fin.");

					}
					if (mensajeCliente.equals(RAIZ)) {
						String clienteInstruccion = ClienteName + " raiz(" + RAIZ + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar operador: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de " + a + " * " + b + " = " + calculadora.raiz(a));
						System.out.println(clienteInstruccion + " fin.");

					}
					if (mensajeCliente.equals(POTENCIA)) {
						String clienteInstruccion = ClienteName + " potencia(" + POTENCIA + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime primer operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar primer operador: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						System.out.println(clienteInstruccion + " esperando b");
						pw.println("	" + clienteInstruccion + " dime segundo operador: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar segundo operador: ");
							mensajeCliente = br.readLine();
						}
						b = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de " + a + " ^ " + b + " = " + calculadora.potencia(a, b));
						System.out.println(clienteInstruccion + " fin.");

					}
					if (mensajeCliente.equals(SIGUIENTEPRIMO)) {
						String clienteInstruccion = ClienteName + " siguiente primo(" + SIGUIENTEPRIMO + ")";

						System.out.println(clienteInstruccion + " esperando a");
						pw.println("	" + clienteInstruccion + " dime numero: ");
						mensajeCliente = br.readLine();
						while (!canFloatTryParse(mensajeCliente)) {
							System.out.println(clienteInstruccion + " esperando, otra vez, a");
							pw.println("	" + clienteInstruccion + " no numero, VUELVE a indicar un numero: ");
							mensajeCliente = br.readLine();
						}
						a = Float.parseFloat(mensajeCliente);

						pw.println("	" + RESULTADO + " de siguiente primo de " + a + " = " + calculadora.siguientePrimo(a));
						System.out.println(clienteInstruccion + " fin.");

					} else {
						pw.println(ERROR + ": Opcion " + mensajeCliente + " no disponible.");
					}

				}

			} while (!mensajeCliente.equals(salida));

			System.out.println(ClienteName + " se ha desconectado.");

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (pw != null) {
			pw.flush(); // Nunca deberia hacer falta esto
			pw.close();
		}
	}

	/**
	 * Comprueba si un string puede pasarse a float
	 * 
	 * @param str,
	 *            el string que vamos a comprobar
	 * @return true si el string SI se puede pasar a float false si NO se puede
	 *         pasar
	 */
	public boolean canFloatTryParse(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
