package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta es la excepci�n lanzada en caso de que ocurra una excepci�n en la Consulta de un Folio en el proceso de Calificaci�n de un folio.
 * @author ppabon
 */
public class ConsultaFolioEspecializadoException extends EventoException {

	/**
	 * Constructor de la clase.
	 */
	public ConsultaFolioEspecializadoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConsultaFolioEspecializadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConsultaFolioEspecializadoException(String arg0) {
		super(arg0);
	}

}
