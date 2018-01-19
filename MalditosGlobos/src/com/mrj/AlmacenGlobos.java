package com.mrj;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class AlmacenGlobos {
	
	private int maxGlobos = 5;
	private int[] globos = new int[maxGlobos];
	private int posSiguienteGlobo;
	private int globosOcupados;
	
	private Object mutex = new Object();
	
	public AlmacenGlobos() {
		for (int i = 0; i<maxGlobos; i++) {
			globos[i] = 0;
		}
		this.posSiguienteGlobo = 0;
		this.globosOcupados = 0;
	}
	
	public int getGloboVolumen(int pos) {
		return globos[pos];
	}

	/**
	 * Comprueba si quedan globos 
	 * @return
	 */
	public boolean quedanGlobos() {
		int globosDisponibles = 0;
		for (int i = 0; i<maxGlobos; i++) {
			if (globos[i] != -1) {
				globosDisponibles++;
			}
		}
		if (globosDisponibles>0) {
			return true;
		}
		return false;
	}

	
	/**
	 * Dice la pos del siguiente globo a entregar
	 * @return
	 */
	public synchronized int entregarGlobo(String nombre) {
		while (globosOcupados >= 3) {
			System.out.println(nombre+ " Esperando globo...");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (posSiguienteGlobo < maxGlobos) {
			System.out.println("GLOBO "+posSiguienteGlobo+" ENTREGADO A "+nombre);
			globos[posSiguienteGlobo] = 1;
			globosOcupados++;
			return posSiguienteGlobo++;
		} else {
			return -1;
		}
	}
	
	/**
	 * Hincha un globo
	 * @param pos
	 */
	public void hincharGlobo(int pos, String nombre) {
		System.out.println("GLOBO "+pos+" VOLUMEN "+(globos[pos]++)+" POR "+nombre);
	}
	
	public synchronized void explotarGlobo(int pos, String nombre) {
		System.out.println("GLOBO "+pos+" EXPLOTADO POR "+nombre);
		globosOcupados--;
		globos[pos] = -1;
		notifyAll();
	}
}
