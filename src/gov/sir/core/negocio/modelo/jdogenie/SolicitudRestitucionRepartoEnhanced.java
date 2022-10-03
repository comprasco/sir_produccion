/*
 * Created on 02-nov-2004
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.Date;

import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;

/**
 * @author dlopez
 * Clase para el manejo de Solicitudes de Restituci�n de Reparto Notarial. 
 */
public class SolicitudRestitucionRepartoEnhanced extends SolicitudEnhanced {

	
	/**
	* El causal de restituci�n de un reparto notarial. 
	*/
	private CausalRestitucionEnhanced causalRestitucion;
	
	
	/**
	* Boolean que indica si la solicitud de restituci�n fue aceptada
	* (true) o rechazada (false).
	*/
	private boolean aceptacion;
	
	
	/**
	* String en el que se almacena la resoluci�n de restituci�n de 
	* Reparto Notarial. 
	*/
	private String  resolucion;
	
	
	
	/**
	* Indica los motivos por los cuales se aprob� o nego el an�lisis de la
	* solicitud de restituci�n de reparto notarial.
	*/
	private String observaciones;
	
	/**
	* Indica los motivos por los cuales se pidi� la
	* solicitud de restituci�n de reparto notarial.
	*/
	private String observacionesRestitucion;
	
	
	/**
	* Indica la fecha en la que se aprueba o niega la  solicitud de 
	* restituci�n de reparto notarial.
	*/
	private Date fechaResolucion;
	
	/**
	 * Retorna un objeto <code>CausalRestitucionEnhanced</code> asociado con la
	 * <code>SolicitudRestitucionRepartoEnhanced</code>
	 * @return <code>CausalRestitucionEnhanced</code> asociado con la
	 * <code>SolicitudRestitucionRepartoEnhanced</code>
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoEnhanced
	 */
	public CausalRestitucionEnhanced getCausalRestitucion() {
		return causalRestitucion;
	}



	/**
	 * Asigna al atributo causalRestitucion el valor recibido como
	 * par�metro. 
	 * @param causal <code>CausalRestitucionEnhanced</code> que va a ser
	 * asociado a la <code>SolicitudRestitucionRepartoEnhanced</code>
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoEnhanced
	 */
	public void setCausalRestitucion (CausalRestitucionEnhanced causal) {
		causalRestitucion = causal;
	}
	

	/**
	 * Retorna el valor del atributo aceptacion que indica si la
	 * <code>SolicitudRestitucionRepartoEnhanced</code> fue aceptada (true) o
	 * rechazada (false).
	 * @return El valor del atributo aceptacion que indica si la
	 * <code>SolicitudRestitucionRepartoEnhanced</code> fue aceptada (true) o
	 * rechazada (false).
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoEnhanced
	 */
	public boolean getAceptacion() {
		return aceptacion;
	}


	/**
	 * Asigna al atributo aceptacion el valor recibido como
	 * par�metro. 
	 * @param aceptacion Booleano que indica si la <code>SolicitudRestitucionRepartoEnhanced</code> fue aceptada (true) o
	 * rechazada (false).
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoEnhanced
	 */
	public void setAceptacion(boolean aceptacion) {
		this.aceptacion = aceptacion;
	}

	/**
	 * Retorna el valor del atributo resoluci�n en el que se almacena
	 * el texto en el que se guarda la decisi�n tomada respecto al resultado
	 * de la solicitud. 
	 * @return el valor del atributo resoluci�n en el que se almacena
	 * el texto en el que se guarda la decisi�n tomada respecto al resultado
	 * de la solicitud. 
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 */
	public String  getResolucion() {
		return this.resolucion;
	} 


	/**
	 * Asigna al atributo resoluci�n el valor recibido como
	 * par�metro. 
	 * @param texto el valor del atributo resoluci�n en el que se almacena
	 * el texto en el que se guarda la decisi�n tomada respecto al resultado
	 * de la solicitud. 
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 */
	public void setResolucion (String texto) {
		this.resolucion = texto;
	}


	/**
	* Convierte un objeto <code>SolicitudRestitucionReparto</code> en un objeto
	* <code>SolicitudRestitucionRepartoEnhanced</code>
	* @param solicitud objeto <code>SolicitudRestitucionReparto</code> que va a ser
	* convertido en un  objeto <code>SolicitudRestitucionRepartoEnhanced</code>
	* @return objeto <code>SolicitudRestitucionRepartoEnhanced</code> generado a partir
	* de un objeto <code>SolicitudRestitucionReparto</code>
	*/
	public static SolicitudRestitucionRepartoEnhanced enhance(SolicitudRestitucionReparto solicitud) {
		return (SolicitudRestitucionRepartoEnhanced) Enhanced.enhance(solicitud);
	}





	/**
	 * @return
	 */
	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	/**
	 * @return
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param date
	 */
	public void setFechaResolucion(Date date) {
		fechaResolucion = date;
	}

	/**
	 * @param string
	 */
	public void setObservaciones(String string) {
		observaciones = string;
	}



	public String getObservacionesRestitucion() {
		return observacionesRestitucion;
	}



	public void setObservacionesRestitucion(String observacionesRestitucion) {
		this.observacionesRestitucion = observacionesRestitucion;
	}

}
