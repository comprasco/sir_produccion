package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;


/**
 * Excepción lanzada cuando ocurren problemas en la copia de una anotación a otros folios.
 * @author ppabon
 */
public class CopiaAnotacionException extends ValidacionParametrosException {


	/**
	 * 
	 */
	public CopiaAnotacionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CopiaAnotacionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CopiaAnotacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param e
	 */
	public CopiaAnotacionException(List e) {
		super(e);
	}

}
