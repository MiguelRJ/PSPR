package dam.psp;

public class Arbitro {

	int numJugadores;
	int turno;
	int objetivo;
	boolean acertado;
	public int maximo = 1000;

	public Arbitro(int numJugador) {
		numJugadores = numJugador;
		turno = (int) (numJugadores * Math.random());
		objetivo = 1 + (int) (maximo * Math.random());
		acertado = false;
		System.out.println("Numero a acertar: " + objetivo);
	}

	public int esTurnoDe() {
		System.out.println("Turno del jugador " + turno);
		return turno;
	}

	public synchronized boolean seAcabo() {
		return acertado;
	}

	public synchronized void jugar(int jugador, int jugada) {
		while (jugador != turno && !acertado) {
			try {
				System.out.println("[PRE] jugador " + jugador + " y es turno de " + turno);
				wait();
				System.out.println("[POST] jugador " + jugador + " y es turno de " + turno);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!acertado) {
			System.out.println("El jugador " + turno + " prueba con " + jugada);
			if (jugada == objetivo) {
				acertado = true;
				System.out.println("Jugador " + turno + " WINS!");
			} else {
				System.out.println("El jugador " + turno + " ha fallado con el numero: " + jugada);
				turno = (turno + 1) % numJugadores;
			}
		}
		notifyAll();

	}

}
