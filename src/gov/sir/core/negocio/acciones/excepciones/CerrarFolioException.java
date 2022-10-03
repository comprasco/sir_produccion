package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Clase que se lanza cuando se intenta crear un folio.
 *
 */
public class CerrarFolioException extends EventoException {
	/**
	 * Constructor de la clase, con un valor especifico
	 * @param string El mensaje de la excepcion
	 */
	public CerrarFolioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
