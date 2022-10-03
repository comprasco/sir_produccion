package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * @author jmendez
 *
 */
public class CirculoNoEncontradoException extends AccionWebException {

	/**
	 * Constructor de la clase, con un valor especifico
	 * @param string El mensaje de la excepcion
	 */
	public CirculoNoEncontradoException(String string) {
		super(string);
	}

	public CirculoNoEncontradoException() {
		super("No se Encontró el Círculo en la sesión");
	}

}
