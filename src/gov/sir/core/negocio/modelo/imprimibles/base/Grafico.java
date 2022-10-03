/*
 * Created on 02-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.imprimibles.base;

import java.awt.image.BufferedImage;

/**
 * @author gvillal
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Grafico 
{
	/**Coordenada horizontal del texto en 1/72 de pulgada.**/
	private int x;

	/**Coordenada vertical del texto en 1/72 de pulgada.**/
	private int y;
	
	private BufferedImage buf;



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

	/**
	 * @return
	 */
	public BufferedImage getBuf() {
		return buf;
	}

	/**
	 * @param image
	 */
	public void setBuf(BufferedImage image) {
		buf = image;
	}

}
