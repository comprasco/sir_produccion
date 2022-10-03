package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConsultaProcesosNoEfectuadaException extends EventoException {

	/**
	 * 
	 */
	public ConsultaProcesosNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConsultaProcesosNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ConsultaProcesosNoEfectuadaException(String arg0) {
		super(arg0);
	}
}
