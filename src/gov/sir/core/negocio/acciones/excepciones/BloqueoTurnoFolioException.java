package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
  * @author ppabon
  * Excepci�n lanzada cuando no es posible bloquear un folio migrado con un turno en tr�mite
  * en el sistema FOLIO.
 */
public class BloqueoTurnoFolioException extends EventoException {

	/**
	 * 
	 */
	public BloqueoTurnoFolioException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BloqueoTurnoFolioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BloqueoTurnoFolioException(String arg0) {
		super(arg0);
	}

}
