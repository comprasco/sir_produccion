package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
  * @author I.Siglo21
 */
public class ANConsultaException extends EventoException {

	/**
	 * 
	 */
	public ANConsultaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANConsultaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANConsultaException(String arg0) {
		super(arg0);
	}

}
