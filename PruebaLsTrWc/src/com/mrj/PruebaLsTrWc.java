package com.mrj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PruebaLsTrWc {
	public static void main(String[] args) {
		ProcessBuilder ls = new ProcessBuilder("/bin/ls","-l","/home/mrj");
		ProcessBuilder tr = new ProcessBuilder("/usr/bin/tr","a","@");
		ProcessBuilder wc = new ProcessBuilder("/usr/bin/wc","-l","-w","-c");
		
		try {
			Process pLs = ls.start();
			Process pTr = tr.start();
			Process pWc = wc.start();
			
			BufferedReader pLsSalida = new BufferedReader(new InputStreamReader(pLs.getInputStream(), "utf-8"));
			BufferedWriter pTrEntrada = new BufferedWriter(new OutputStreamWriter(pTr.getOutputStream(), "utf-8"));
			BufferedReader pTrSalida = new BufferedReader(new InputStreamReader(pTr.getInputStream(), "utf-8"));
			BufferedWriter pWcEntrada = new BufferedWriter(new OutputStreamWriter(pWc.getOutputStream(),"utf-8"));
			BufferedReader pWcSalida = new BufferedReader(new InputStreamReader(pWc.getInputStream(), "utf-8"));
			
			String pLsResultado;
			while ( (pLsResultado = pLsSalida.readLine()) != null) {
				pTrEntrada.write(pLsResultado);
				pTrEntrada.newLine();
			}
			pLsSalida.close();
			pTrEntrada.close();
			
			String pTrResultado;
			while ( (pTrResultado = pTrSalida.readLine()) != null) {
				System.out.println(pTrResultado);
				pWcEntrada.write(pTrResultado);
				pWcEntrada.newLine();
			}
			pTrSalida.close();
			pWcEntrada.close();
			
			String pWcResultado;
			while ( (pWcResultado = pWcSalida.readLine()) != null) {
				System.out.println(pWcResultado);
			}
			pWcSalida.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
