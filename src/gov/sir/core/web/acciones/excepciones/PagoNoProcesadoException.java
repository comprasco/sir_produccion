package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepción ocurrida cuando el pago no pudo ser procesado
 * @author eacosta
 */
public class PagoNoProcesadoException extends AccionWebException {

	/**
	 * 
	 */
	public PagoNoProcesadoException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public PagoNoProcesadoException(String arg0) {
		super(arg0);
	}
}
