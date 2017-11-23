package mrj.wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class ContadorWC extends Thread {
	
	int lineasTotal;
	int palabrasTotal;
	int caracteresTotal;
	int lineas;
	int palabras;
	int caracteres;
	File[] ficheros;
	
	public ContadorWC(File[] ficheros) {
		this.ficheros = ficheros;
		lineasTotal = 0;
		palabrasTotal = 0;
		palabrasTotal = 0;
		lineas = 0;
		palabras = 0;
		caracteres = 0;
	}
	
	public void contarCaracteres(String linea) {
		for (int i = 0; i < linea.length(); i++) {
			caracteres++;
			caracteresTotal++;
		}
	}
	
	public void contarPalabras(String linea) {
		for (int i = 0; i < linea.length(); i++) {
			if (linea.charAt(i) == ' ') {
				palabras++;
				palabrasTotal++;
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
                lineasTotal++;
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
		System.out.println("Datos Totales L: "+lineasTotal+" W: "+palabrasTotal+" C: "+caracteresTotal);
		lineas=0;
		palabras=0;
		caracteres=0;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < ficheros.length; i++) {
			//System.out.println(leerFicheroCompleto(ficheros[i].getAbsolutePath()));
			leerFicheroCompleto(ficheros[i].getAbsolutePath());
			mostrarDatosFichero(ficheros[i].getName());
		}
		mostrarDatosTotales();
	}
}

public class Simulacion {

	public static void main(String[] args) {
		
		long startNanoTime;
		long endNanoTime;
		long startTime;
		long endTime;
		File[] ficheros = new File[args.length];
		
		startTime = System.currentTimeMillis();
		startNanoTime = System.nanoTime();
		for (int i = 0; i < ficheros.length; i++) {
			ficheros[i] = new File(args[i]);
			System.out.println("Fichero: "+i+" "+ficheros[i].getAbsolutePath());
		}
		
		ContadorWC contadorWC = new ContadorWC(ficheros);
		contadorWC.start();
		try {
			contadorWC.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		endNanoTime = System.nanoTime();
		endTime = System.currentTimeMillis();
		
		System.out.println("Took "+(endNanoTime - startNanoTime) + " ns"); 
		System.out.println("Took "+(endTime - startTime) + " sc"); 
	}

}
