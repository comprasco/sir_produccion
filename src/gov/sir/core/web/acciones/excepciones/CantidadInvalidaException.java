package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepci�n lanzada en caso de haberse especificado
 * un par�metro de entrada num�rico, de forma inv�lida.
 *  Ej.  Cantidades no concordantes, valores negativos no aceptables, etc
 * @author eacosta
 */
public class CantidadInvalidaException extends AccionWebException {

	/**
	 * Constructor que especifica el mensaje propio de la validaci�n fallida.
	 * @param arg0  mensaje
	 */
	public CantidadInvalidaException(String arg0) {
		super(arg0);
	}
}
