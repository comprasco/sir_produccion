package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepci�n lanzada en caso de haberse especificado
 * un par�metro de entrada de forma inv�lida.
 *  Ej.  Cadenas de texto en lugar de n�meros, n�meros fuera de rango, etc.
 * @author eacosta
 */
public class CrearTurnoException extends AccionWebException {

	/**
	 * Constructor que especifica el mensaje propio de la validaci�n fallida.
	 * @param arg0  mensaje
	 */
	public CrearTurnoException(String arg0) {
		super(arg0);
	}
}
