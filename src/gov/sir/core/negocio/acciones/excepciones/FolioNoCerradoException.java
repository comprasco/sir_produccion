package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Excepción lanzada cuando se intenta reabrir un folio que no esta cerrado.
  * @author ppabon
 */
public class FolioNoCerradoException extends EventoException {

	/**
	 * 
	 */
	public FolioNoCerradoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public FolioNoCerradoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public FolioNoCerradoException(String arg0) {
		super(arg0);
	}

}
