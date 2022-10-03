package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class SolicitudInvalidaRegistroException extends EventoException {

	/**
	 * 
	 */
	public SolicitudInvalidaRegistroException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SolicitudInvalidaRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public SolicitudInvalidaRegistroException(String str) {
		super(str);
	}

}
