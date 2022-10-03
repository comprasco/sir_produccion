package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Clase que se lanza cuando se intenta cambiar la matrícula a un turno de certificados
 * y no es posible realizar este cambio por validación de negocio.
 *
 */
public class CambiarMatriculaException extends EventoException {
	
	/**
	 * Constructor de la clase, con un valor especifico
	 * @param string El mensaje de la excepcion
	 */
	public CambiarMatriculaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
