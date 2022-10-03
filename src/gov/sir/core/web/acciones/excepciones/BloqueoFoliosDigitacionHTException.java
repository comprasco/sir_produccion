package gov.sir.core.web.acciones.excepciones;

import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.forseti.ForsetiException;

/**
 * Excepcion para capturar las excepciones que ocurrieron al tratar de bloquear los folios en proceso de correcciones 
 * por parte del usuario digitador. El hashtable tiene una lista en donde tiene el id de la matrícula y
 * el error que ocurrió al tratar de bloquearla.
 * @author ppabon
 */
public class BloqueoFoliosDigitacionHTException extends ValidacionParametrosHTException {

	/**
	 * Constructor de la clase
	 */
	public BloqueoFoliosDigitacionHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public BloqueoFoliosDigitacionHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BloqueoFoliosDigitacionHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public BloqueoFoliosDigitacionHTException(ForsetiException e) {
		super(e);
	}

}
