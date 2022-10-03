/*
 * Created on 28-oct-2004
 * Clase para el manejo de solicitudes de restituci�n en el proceso 
 * de reparto notarial. 
 */
package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase para el manejo de solicitudes de restituci�n en el proceso 
 * de reparto notarial. 
 * @author dlopez
 */
public class SolicitudRestitucionReparto extends Solicitud implements TransferObject 
{

	/**
	* El causal de restituci�n de un reparto notarial. 
	*/
	private CausalRestitucion causalRestitucion;
	private static final long serialVersionUID = 1L;
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
	* Indica los motivos por los cuales se pide la
	* solicitud de restituci�n de reparto notarial.
	*/
	private String observacionesRestitucion;
	
	
	/**
	* Indica la fecha en la que se aprueba o niega la  solicitud de 
	* restituci�n de reparto notarial.
	*/
	private Date fechaResolucion;
	
	/**
	 * Retorna un objeto <code>CausalRestitucion</code> asociado con la
	 * <code>SolicitudRestitucionReparto</code>
	 * @return <code>CausalRestitucion</code> asociado con la
	 * <code>SolicitudRestitucionReparto</code>
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 */
	public CausalRestitucion getCausalRestitucion() {
		return causalRestitucion;
	}

	/**
	 * Asigna al atributo causalRestitucion el valor recibido como
	 * par�metro. 
	 * @param causal <code>CausalRestitucion</code> que va a ser
	 * asociado a la <code>SolicitudRestitucionReparto</code>
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 */
	public void setCausalRestitucion(CausalRestitucion causal) {
		causalRestitucion = causal;
	}
	
	
	/**
	 * Retorna el valor del atributo aceptacion que indica si la
	 * <code>SolicitudRestitucionReparto</code> fue aceptada (true) o
	 * rechazada (false).
	 * @return El valor del atributo aceptacion que indica si la
	 * <code>SolicitudRestitucionReparto</code> fue aceptada (true) o
	 * rechazada (false).
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	 */
	public boolean getAceptacion() {
		return aceptacion;
	}


	/**
	 * Asigna al atributo aceptacion el valor recibido como
	 * par�metro. 
	 * @param aceptacion Booleano que indica si la <code>SolicitudRestitucionReparto</code> fue aceptada (true) o
	 * rechazada (false).
	 * @see gov.sir.core.negocio.modelo.CausalRestitucion
	 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
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


	/** Retorna la fecha en la que se aprueba o niega 
	 * la solicitud de restitucion de reparto notarial
	 * @return
	 */
	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	/** Retorna los motivos por los cuales se aprobo o nego 
	 * el analisis de la solicitud de restitucion de reparto notarial
	 * @return
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/** Cambia la fecha en la que se aprueba o niega 
	 * la solicitud de restitucion de reparto notarial
	 * @param date
	 */
	public void setFechaResolucion(Date date) {
		fechaResolucion = date;
	}

	/** Modifica los motivos por los cuales se aprobo o nego 
	 * el analisis de la solicitud de restitucion de reparto notarial
	 * @param string
	 */
	public void setObservaciones(String string) {
		observaciones = string;
	}

	public String getObservacionesRestitucion() {
		return observacionesRestitucion;
	}

	public void setObservacionesRestitucion(String observacionesRestituci�n) {
		this.observacionesRestitucion = observacionesRestituci�n;
	}

}
