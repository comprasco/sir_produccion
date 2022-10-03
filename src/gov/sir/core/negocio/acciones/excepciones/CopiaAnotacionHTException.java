package gov.sir.core.negocio.acciones.excepciones;

import gov.sir.forseti.ForsetiException;


/**
 * Excepción lanzada cuando ocurren problemas en la copia de una anotación a otros folios.
 * @author ppabon
 */
public class CopiaAnotacionHTException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public CopiaAnotacionHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CopiaAnotacionHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CopiaAnotacionHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public CopiaAnotacionHTException(ForsetiException  e) {
		super(e);
	}

}
