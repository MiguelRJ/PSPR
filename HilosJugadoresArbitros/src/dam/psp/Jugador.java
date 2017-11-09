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
		while (!arbitro.seAcabo()) {
			arbitro.jugar(this.dorsal, 1 + (int) (arbitro.maximo * Math.random()));
		}
		System.out.println("El jugador " + dorsal + "ha dejado de jugar");
	}

}
