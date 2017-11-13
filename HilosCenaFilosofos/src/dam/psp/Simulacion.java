package dam.psp;

public class Simulacion {

	public static void main(String[] args) {
		int comensales = Integer.parseInt(args[0]);
		int almuerzos = Integer.parseInt(args[1]);
		Cena cena = new Cena(comensales);
		
		for (int i = 0; i < comensales; i++) {
			Thread hilo = new Thread(new Filosofo(i, almuerzos, cena));
			hilo.start();
		}
		
	}

}
