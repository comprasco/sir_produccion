package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta es la excepción lanzada en caso de que ocurra una excepción en las validaciones de negocio de reportes
 * @author ppabon
 */
public class ANReportesException extends EventoException {

	/**
	 * Constructor de la clase.
	 */
	public ANReportesException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANReportesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANReportesException(String arg0) {
		super(arg0);
	}

}
