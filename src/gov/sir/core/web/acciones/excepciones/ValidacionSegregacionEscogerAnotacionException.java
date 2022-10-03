package gov.sir.core.web.acciones.excepciones;

/**
 * Clase utilizada para validar que el usuario escoja por lo menos una anotación cuando el usuario
 * no ingreso ninguna en la primera etapa de la segregación.
 * @author ppabon
 *
 */
public class ValidacionSegregacionEscogerAnotacionException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public ValidacionSegregacionEscogerAnotacionException() {
		super();
	}

}
