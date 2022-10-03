package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ANInitParametrosException extends EventoException {

	/**
	 * 
	 */
	public ANInitParametrosException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANInitParametrosException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANInitParametrosException(String arg0) {
		super(arg0);
	}
}
