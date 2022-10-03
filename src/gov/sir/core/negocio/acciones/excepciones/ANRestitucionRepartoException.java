package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta excepción es lanzada cuando ha ocurrido algún error en la acción de negocio de ANRestitucionReparto.
 * @author ppabon
 */
public class ANRestitucionRepartoException extends EventoException {

	/**
	 *	Constructor de la clase. 
	 */
	public ANRestitucionRepartoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ANRestitucionRepartoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ANRestitucionRepartoException(String arg0) {
		super(arg0);
	}

}
