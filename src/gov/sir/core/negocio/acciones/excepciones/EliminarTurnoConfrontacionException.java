package gov.sir.core.negocio.acciones.excepciones;

import java.util.Hashtable;

import gov.sir.forseti.ForsetiException;

/**
 * @author jfrias
*/
public class EliminarTurnoConfrontacionException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public EliminarTurnoConfrontacionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public EliminarTurnoConfrontacionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EliminarTurnoConfrontacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param ht
	 */
	public EliminarTurnoConfrontacionException(Hashtable ht) {
		super(ht);
	}

	/**
	 * @param e
	 */
	public EliminarTurnoConfrontacionException(ForsetiException e) {
		super(e);
	}
	
	

}
