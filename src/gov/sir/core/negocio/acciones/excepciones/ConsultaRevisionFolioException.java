package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta es la excepción lanzada en caso de que ocurra una excepción en la Consulta de un Folio en el proceso de Calificación de un folio.
 * @author ppabon
 */
public class ConsultaRevisionFolioException extends EventoException {

	/**
	 * Constructor de la clase.
	 */
	public ConsultaRevisionFolioException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConsultaRevisionFolioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConsultaRevisionFolioException(String arg0) {
		super(arg0);
	}

}
