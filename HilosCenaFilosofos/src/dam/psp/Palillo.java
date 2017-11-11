package dam.psp;

public class Palillo {
	
	int numero;
	boolean enUso;
	
	public Palillo(int numeroPalillo) {
		numero = numeroPalillo;
		enUso = false;
	}
	
	public void coger() {
		if(enUso) {
			System.out.println("palillo ["+numero+"] ocupado");
		} else {
			this.enUso = true;
		}
		
	}
	
	public void soltar() {
		this.enUso = false;
		System.out.println("palillo ["+numero+"] soltado");
	}

}
