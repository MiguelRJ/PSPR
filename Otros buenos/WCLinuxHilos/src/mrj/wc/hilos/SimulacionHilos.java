package mrj.wc.hilos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class DatosTotales {
	static int lineasTotal;
	static int palabrasTotal;
	static int caracteresTotal;
	
	private static final Object mLinea = new Object();
	private static final Object mPalabras = new Object();
	private static final Object mCaracteres = new Object();
	
	public static void incrementarLineas() {
		synchronized (mLinea) {
			lineasTotal++;
		}
	}
	public static void incrementarPalabras() {
		synchronized (mPalabras) {
			palabrasTotal++;
		}
	}
	public static void incrementarCaracteres() {
		synchronized (mCaracteres) {
			caracteresTotal++;
		}
	}
	
}

class ContadorWC extends Thread {
	
	int lineas;
	int palabras;
	int caracteres;
	File fichero;
	
	public ContadorWC(File fichero) {
		this.fichero = fichero;
		lineas = 0;
		palabras = 0;
		caracteres = 0;
	}
	
	public void contarCaracteres(String linea) {
		for (int i = 0; i < linea.length(); i++) {
			caracteres++;
			DatosTotales.incrementarCaracteres();
		}
	}
	
	public void contarPalabras(String linea) {
		for (int i = 0; i < linea.length(); i++) {
			if (linea.charAt(i) == ' ') {
				palabras++;
				DatosTotales.incrementarCaracteres();
			}
		}
	}
	
	public String leerFicheroCompleto(String fpath){

        BufferedReader br = null;
        String response = null;

        try {
            StringBuffer output = new StringBuffer();
            br = new BufferedReader(new FileReader(fpath));
            String linea = "";
            while ((linea = br.readLine()) != null) {
            	contarCaracteres(linea);
            	contarPalabras(linea);
                output.append(linea);
                lineas++;
                DatosTotales.incrementarLineas();
            }
            response = output.toString();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
	
	public void mostrarDatosFichero(String nombreFichero) {
		System.out.println("Fichero: "+nombreFichero+" L: "+lineas+" W: "+palabras+" C: "+caracteres+"\n\n");
		lineas=0;
		palabras=0;
		caracteres=0;
	}
	
	public void mostrarDatosTotales() {
		System.out.println("Datos Totales L: "+DatosTotales.lineasTotal+" W: "+DatosTotales.palabrasTotal+" C: "+DatosTotales.caracteresTotal);
		lineas=0;
		palabras=0;
		caracteres=0;
	}
	
	@Override
	public void run() {
		//System.out.println(leerFicheroCompleto(fichero.getAbsolutePath()));
		leerFicheroCompleto(fichero.getAbsolutePath());
		mostrarDatosFichero(fichero.getName());
		mostrarDatosTotales();
	}
}

public class SimulacionHilos {

	public static void main(String[] args) {
		
		long startNanoTime;
		long endNanoTime;
		long startTime;
		long endTime;
		ContadorWC[] contadores = new ContadorWC[args.length];
		
		startTime = System.currentTimeMillis();
		startNanoTime = System.nanoTime();
		for (int i = 0; i < args.length; i++) {
			File fichero = new File(args[i]);
			System.out.println("Fichero: "+i+" "+fichero);
			contadores[i] = new ContadorWC(fichero);
			contadores[i].start();
		}
		
		for (int i = 0; i < args.length; i++) {
			try {
				contadores[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		endNanoTime = System.nanoTime();
		endTime = System.currentTimeMillis();
		
		System.out.println("Took "+(endNanoTime - startNanoTime) + " ns"); 
		System.out.println("Took "+(endTime - startTime) + " sc"); 
	}

}
