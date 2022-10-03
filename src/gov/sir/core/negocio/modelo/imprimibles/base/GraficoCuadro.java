package gov.sir.core.negocio.modelo.imprimibles.base;

/**
 * @author ppabon
 * Clase que representa un cuadro para imprimir en una HojaImprimible.
 */
public class GraficoCuadro {
	
	/**Coordenada horizontal del texto en 1/72 de pulgada.**/
	private int x;

	/**Coordenada vertical del texto en 1/72 de pulgada.**/
	private int y;
	
	/**Valor del alto del cuadro.**/
	private int h;

	/**Valor del ancho del cuadro.**/
	private int w;


	/**
	 * @return
	 */
	public int getH() {
		return h;
	}

	/**
	 * @return
	 */
	public int getW() {
		return w;
	}

	/**
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param i
	 */
	public void setH(int i) {
		h = i;
	}

	/**
	 * @param i
	 */
	public void setW(int i) {
		w = i;
	}

	/**
	 * @param i
	 */
	public void setX(int i) {
		x = i;
	}

	/**
	 * @param i
	 */
	public void setY(int i) {
		y = i;
	}

}
