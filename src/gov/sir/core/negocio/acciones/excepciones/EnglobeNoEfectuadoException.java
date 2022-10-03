package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class EnglobeNoEfectuadoException extends EventoException {

	/**
	 * 
	 */
	public EnglobeNoEfectuadoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EnglobeNoEfectuadoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public EnglobeNoEfectuadoException(String str) {
		super(str);
	}

}
