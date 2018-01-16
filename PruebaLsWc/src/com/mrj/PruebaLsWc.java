package com.mrj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class PruebaLsWc {

	public static void main(String[] args) {
		ProcessBuilder ls = new ProcessBuilder("/bin/ls","-l","/home/mrj");
		ProcessBuilder wc = new ProcessBuilder("/usr/bin/wc","-l","-w","-c");
		
		Process pLs = null;
		Process pWc = null;
		try {
			pLs = ls.start();
			pWc = wc.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader pLsSalida = null;
		BufferedWriter pWcEntrada = null;
		try {
			pLsSalida = new BufferedReader(
					new InputStreamReader(pLs.getInputStream(), "utf-8"));
			
			pWcEntrada = new BufferedWriter(
					new OutputStreamWriter(pWc.getOutputStream(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String pLsResultado;
		try {
			while ( (pLsResultado = pLsSalida.readLine()) != null) {
				pWcEntrada.write(pLsResultado);
				pWcEntrada.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			pLsSalida.close();
			pWcEntrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader pWcSalida = null;
		try {
			pWcSalida = new BufferedReader(new InputStreamReader(pWc.getInputStream(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String pWcResultado;
		try {
			while ( (pWcResultado = pWcSalida.readLine()) != null) {
				System.out.println(pWcResultado);
			}
			pWcSalida.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			int pLsInt = pLs.waitFor();
			int pWcInt = pWc.waitFor();
			System.out.println("ls: "+pLsInt+" wc: "+pWcInt);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
