package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Excepción lanzada en las validaciones de negocio para los recursos y revocatorias directas en turnos de Registro
 * de documentos. 
 */
public class ANRevocatoriaRegistroException extends EventoException {

	/**
	 * 
	 */
	public ANRevocatoriaRegistroException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANRevocatoriaRegistroException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANRevocatoriaRegistroException(String arg0) {
		super(arg0);
	}

}
