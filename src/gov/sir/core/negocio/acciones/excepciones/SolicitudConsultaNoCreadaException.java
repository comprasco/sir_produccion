package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class SolicitudConsultaNoCreadaException extends EventoException {
	/**
	 * 
	 */
	public SolicitudConsultaNoCreadaException() {
		super("No se pudo crear la solicitud de consulta");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SolicitudConsultaNoCreadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public SolicitudConsultaNoCreadaException(String arg0) {
		super(arg0);
	}

}
