/*
 * Created on 23-feb-2005
 *
 */
package gov.sir.core.eventos.certificado;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author gvillal
 * Clase que representa el evento de respuesta de la modificacion
 * de la matricula del folio asociado al turno.
 */
public class EvnRespCambioMatricula extends EvnSIRRespuesta {
	
	/**
	 * El número de anotaciones del folio asociado a la matrícula.
	 */
	private String numeroAnotaciones;
	
	/**
	 * Indica si el folio es de mayor extensión.
	 */
	private String mayorExtension;
	
	private Folio folio;
	
	private Turno turno;
	
	/** Creates a new instance of EvnRespCambioMatricula */
	public EvnRespCambioMatricula(String matricula,Folio folio) {
		super(matricula);
		this.folio = folio;
	}
    
	/**
	 * @param matriculaNueva
	 * @param folio2
	 * @param turno
	 */
	public EvnRespCambioMatricula(String matricula, Folio folio, Turno turno) {
		
		super(matricula);
		this.folio = folio;
		this.turno = turno;
	}

	public String getMatricula(){
		return (String)getPayload();
	}	

	/**
	 * @return
	 */
	public String getMayorExtension() {
		return mayorExtension;
	}

	/**
	 * @return
	 */
	public String getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/**
	 * @param string
	 */
	public void setMayorExtension(String string) {
		mayorExtension = string;
	}

	/**
	 * @param string
	 */
	public void setNumeroAnotaciones(String string) {
		numeroAnotaciones = string;
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

	/**
	 * @return Returns the turno.
	 */
	public Turno getTurno() {
		return turno;
	}
	/**
	 * @param turno The turno to set.
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
}
