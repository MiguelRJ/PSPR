package com.mrj;

import java.util.Random;

public class AlmacenGlobos {
	
	private int maxGlobos = 10;
	private int[] globos = new int[maxGlobos];
	private int posSiguienteGlobo;
	private int globosOcupados;
	private Random random = new Random();
		
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
	 * Dice la pos del siguiente globo a entregar y antes de entregar lo hincha a 1
	 * @return
	 */
	public synchronized int entregarGloboParaHinchar(String nombre) {
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
			globos[posSiguienteGlobo] = 1 ;
			globosOcupados++;
			return posSiguienteGlobo++;
		} else {
			return -1;
		}
	}
	
	/**
	 * La entrega de globos a pinchar se hara mediante un Ramdom 
	 * pudiendo enviar un globo que ya este roto o otro que no este hinchado
	 * tambien podria hacer un while donde compruebe si el globo esta hinchado o no para no enviar uno roto (no recuerdo como era en el examen)
	 * @param nombre
	 * @return
	 */
	public synchronized int entregarGloboParaPinchar(String nombre) {
		return random.nextInt(maxGlobos);
	}
	
	/**
	 * Hincha un globo, el globo viene a 0 y desde el metodo entregarGloboParaHinchar se infla la primera vez
	 * se pone el ++ antes para mostrar el valor actual,
	 * si el ++ estuviera al reves pondria que va por el inflado 1 pero en realidad seria un 2 al hacer el ++
	 * @param pos
	 */
	public void hincharGlobo(int pos, String nombre) {
		//globos[pos]++;
		System.out.println("GLOBO "+pos+" VOLUMEN "+(globos[pos]++)+" POR "+nombre);
	}
	
	public synchronized void pincharGlobo(int pos, String nombre) {
		System.out.println("GLOBO "+pos+" PINCHADO POR "+nombre);
		globosOcupados--;
		globos[pos] = -1;
		notifyAll();
	}
	
	public synchronized void explotarGlobo(int pos, String nombre) {
		System.out.println("GLOBO "+pos+" EXPLOTADO POR "+nombre);
		globosOcupados--;
		globos[pos] = -1;
		notifyAll();
	}
}
