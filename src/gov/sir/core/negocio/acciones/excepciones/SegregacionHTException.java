package gov.sir.core.negocio.acciones.excepciones;

import java.util.Hashtable;

import gov.sir.forseti.ForsetiException;

/**
 * Excepción para ver los problemas que ocurrieron al intentar guardar las anotaciones
 * heredadas en el proceso de segregación.
 * @author ppabon
*/
public class SegregacionHTException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public SegregacionHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public SegregacionHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SegregacionHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param ht
	 */
	public SegregacionHTException(Hashtable ht) {
		super(ht);
	}

	/**
	 * @param e
	 */
	public SegregacionHTException(ForsetiException e) {
		super(e);
	}
	
	

}
