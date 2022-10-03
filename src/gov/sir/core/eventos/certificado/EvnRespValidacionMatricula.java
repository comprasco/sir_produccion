/*
 * EvnRespValidacionMatricula.java
 *
 * Created on 3 de enero de 2005, 19:16
 */

package gov.sir.core.eventos.certificado;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
/**
 *
 * @author  dcantor
 */
public class EvnRespValidacionMatricula extends EvnSIRRespuesta
{
	/**
	 * El número de anotaciones del folio asociado a la matrícula.
	 */
	private String numeroAnotaciones;
	
	/**
	 * Indica si el folio es de mayor extensión.
	 */
	private String mayorExtension;
    
    /** Creates a new instance of EvnRespValidacionMatricula */
    public EvnRespValidacionMatricula(String matricula) {
        super(matricula);
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

}