package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
  * @author ppabon
  * Excepción lanzada cuando no es posible bloquear un folio migrado con un turno en trámite
  * en el sistema FOLIO.
 */
public class ConflictoComplementacionException extends EventoException {

	/**
	 * 
	 */
	public ConflictoComplementacionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConflictoComplementacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConflictoComplementacionException(String arg0) {
		super(arg0);
	}

}
