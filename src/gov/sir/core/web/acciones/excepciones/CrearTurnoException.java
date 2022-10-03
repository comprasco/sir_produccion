package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepción lanzada en caso de haberse especificado
 * un parámetro de entrada de forma inválida.
 *  Ej.  Cadenas de texto en lugar de números, números fuera de rango, etc.
 * @author eacosta
 */
public class CrearTurnoException extends AccionWebException {

	/**
	 * Constructor que especifica el mensaje propio de la validación fallida.
	 * @param arg0  mensaje
	 */
	public CrearTurnoException(String arg0) {
		super(arg0);
	}
}
