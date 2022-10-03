package gov.sir.core.negocio.acciones.excepciones;

import java.util.Hashtable;

import gov.sir.forseti.ForsetiException;

/**
 * Excepción para ver los problemas que ocurrieron al intentar guardar las anotaciones
 * heredadas en el proceso de englobe.
 * @author ppabon
*/
public class EnglobeHTException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public EnglobeHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public EnglobeHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EnglobeHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param ht
	 */
	public EnglobeHTException(Hashtable ht) {
		super(ht);
	}

	/**
	 * @param e
	 */
	public EnglobeHTException(ForsetiException e) {
		super(e);
	}
	
	

}
