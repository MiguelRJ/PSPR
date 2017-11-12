package mrj.Rel1.E3;

class Corredor extends Thread {
	
	String nombreCorredor;
	
	public Corredor(int numCorredor) {
		nombreCorredor = "Corredor numero: " + numCorredor;
	}
}

public class Carrera {
	public static void main(String[] args) {
		
		int numCorredores = Integer.parseInt(args[0]);
		
		for (int i = 0; i < numCorredores ; i++) {
			Thread hilo = new Thread(new Corredor(i));
			hilo.start();
		}
		
	}
}
