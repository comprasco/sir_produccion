package gov.sir.core.negocio.acciones.excepciones;

import java.util.Hashtable;

import gov.sir.forseti.ForsetiException;

/**
 * @author jfrias
*/
public class EliminarTurnoCorrecionesException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public EliminarTurnoCorrecionesException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public EliminarTurnoCorrecionesException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EliminarTurnoCorrecionesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param ht
	 */
	public EliminarTurnoCorrecionesException(Hashtable ht) {
		super(ht);
	}

	/**
	 * @param e
	 */
	public EliminarTurnoCorrecionesException(ForsetiException e) {
		super(e);
	}
	
	

}
