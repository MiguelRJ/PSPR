package dam.psp;

public class Jugador extends Thread {

	Arbitro arbitro;
	private int dorsal;

	public Jugador(int numDorsal, Arbitro elArbitro) {
		this.arbitro = elArbitro;
		this.dorsal = numDorsal;
	}

	@Override
	public void run() {
		while (!arbitro.seAcabo()) { // hilo puede comprobar un valor sucio, seAcabo tiene que estar sincronizado
			arbitro.jugar(this.dorsal, 1 + (int) (arbitro.maximo * Math.random()));
		}
		System.out.println("El jugador " + dorsal + " ha dejado de jugar");
	}

}
