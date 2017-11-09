package dam.psp;

public class Campeonato {

	public static void main(String[] args) {
		Arbitro mallenco = new Arbitro(4);
		
		Jugador j0 = new Jugador(0, mallenco);
		Jugador j1 = new Jugador(1, mallenco);
		Jugador j2 = new Jugador(2, mallenco);
		Jugador j3 = new Jugador(3, mallenco);
		
		j0.start();
		j1.start();
		j2.start();
		j3.start();
		
		try {
			j0.join();
			j1.join();
			j2.join();
			j3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}

}
