package gov.sir.print.server;

/**
 * Permite determinar en cuál es el orden de ejecución actual. para llevar un orden 
 * en el momento de realizar varias impresiones simultaneamente. 
 * @author ppabon
 *
 */
public class OrdenImpresion {
	
	private int ordenImpresion = 1;

	public int getOrdenImpresion() {
		return ordenImpresion;
	}

	public void setOrdenImpresion(int ordenImpresion) {
		this.ordenImpresion = ordenImpresion;
	}
}
