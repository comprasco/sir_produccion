package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta excepción contiene la descripción de la causa por la cual un recibo
 * no pudo generarse.
 * @author eacosta
 */
public class ReciboNoGeneradoException extends AccionWebException {

	public ReciboNoGeneradoException() {
		super();
	}

	public ReciboNoGeneradoException(String arg0) {
		super(arg0);
	}

}
