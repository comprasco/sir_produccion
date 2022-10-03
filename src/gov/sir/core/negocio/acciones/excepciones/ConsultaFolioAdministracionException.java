package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
  * @author ppabon
 */
public class ConsultaFolioAdministracionException extends EventoException {

	/**
	 * 
	 */
	public ConsultaFolioAdministracionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConsultaFolioAdministracionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConsultaFolioAdministracionException(String arg0) {
		super(arg0);
	}

}
