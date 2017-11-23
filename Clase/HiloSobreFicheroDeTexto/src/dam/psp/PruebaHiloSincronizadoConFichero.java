package dam.psp;

public class PruebaHiloSincronizadoConFichero {

	public static void main(String[] args) throws InterruptedException {
		ControladorFichero cf = new ControladorFichero("Poema.txt");// Lo crea como hermano de la carpeta src
		
		String parrafo1 = "¡Ser o no ser, esa es la cuestión! ¿Qué debe más dignamente optar...";
		String parrafo2 = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme...";
		
		Escritor cervantes = new Escritor(cf);
		Escritor shakespeare = new Escritor(cf);
		
		shakespeare.fraseAdd(parrafo1);
		cervantes.fraseAdd(parrafo2);
		
		shakespeare.start();
		cervantes.start();
		
		shakespeare.join();// throws InterruptedException 
		cervantes.join();// throws InterruptedException 
		
		cf.close();
		System.out.println("Los datos han sido escritos correctamente?");
	}

}
