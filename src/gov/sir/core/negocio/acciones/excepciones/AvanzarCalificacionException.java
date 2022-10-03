package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ddiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AvanzarCalificacionException extends EventoException {
	/**
	 * Constructor de la clase, con un valor especifico
	 * @param string El mensaje de la excepcion
	 */
	public AvanzarCalificacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
