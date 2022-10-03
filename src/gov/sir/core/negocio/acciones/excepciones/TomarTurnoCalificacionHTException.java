package gov.sir.core.negocio.acciones.excepciones;

import java.util.Hashtable;

import gov.sir.forseti.ForsetiException;

/**
 * @author jfrias
*/
public class TomarTurnoCalificacionHTException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public TomarTurnoCalificacionHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public TomarTurnoCalificacionHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TomarTurnoCalificacionHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param ht
	 */
	public TomarTurnoCalificacionHTException(Hashtable ht) {
		super(ht);
	}

	/**
	 * @param e
	 */
	public TomarTurnoCalificacionHTException(ForsetiException e) {
		super(e);
	}
	
	/**
	 * @param e
	 */
	public TomarTurnoCalificacionHTException(ForsetiException e, String mensajeFormulario) {
		super(e);
		this.setMensajeFormulario(mensajeFormulario);
	}	
	
	

}
