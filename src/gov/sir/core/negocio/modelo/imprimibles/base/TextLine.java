package gov.sir.core.negocio.modelo.imprimibles.base;

/**
 * @author gvillal
 * Clase que representa una linea de texto que se imprime en una HojaImprimible.
 */
public class TextLine {
	
	/**Constante que indica el formato del texto, Negrita,Normal, etc**/
	private int formato;
	
	/**Tamaño de la fuente del texto**/
	private int tam;
	
	/**El texto**/
	private String texto;
	
	/**Coordenada horizontal del texto en 1/72 de pulgada.**/
	private int x;

	/**Coordenada vertical del texto en 1/72 de pulgada.**/
	private int y;
	
	private int ancho = 0;

	/**
	 * @return
	 */
	public int getFormato() {
		return formato;
	}

	/**
	 * @return
	 */
	public int getTam() {
		return tam;
	}

	/**
	 * @return
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param i
	 */
	public void setFormato(int i) {
		formato = i;
	}

	/**
	 * @param i
	 */
	public void setTam(int i) {
		tam = i;
	}

	/**
	 * @param string
	 */
	public void setTexto(String string) {
		texto = string;
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
	public void setX(int i) {
		x = i;
	}

	/**
	 * @param i
	 */
	public void setY(int i) {
		y = i;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

}
