package gov.sir.core.negocio.modelo;

import java.io.Serializable;



/**
 * @author dsalas, ppabon
 * Clase en dónde se guardará un identificador de inicio y uno de fin con respecto a anotaciones.
 * La idea es poder tener un rango de anotaciones.
 */
public class RangoAnotacion implements Serializable{

	private Folio folio;
	private int anotacionInicio;
	private int anotacionFin;
	private static final long serialVersionUID = 1L;
	/** Metodo constructor por defecto  */
	public RangoAnotacion() {
	}


	/**
	 * @return
	 */
	public int getAnotacionFin() {
		return anotacionFin;
	}

	/**
	 * @return
	 */
	public int getAnotacionInicio() {
		return anotacionInicio;
	}


	/**
	 * @param i
	 */
	public void setAnotacionFin(int i) {
		anotacionFin = i;
	}

	/**
	 * @param i
	 */
	public void setAnotacionInicio(int i) {
		anotacionInicio = i;
	}


	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

}
