package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepci?n ocurrida cuando se excede en n?mero
 * de cosultas que el usuario puede realizar
 * @author jmendez
 */
public class MaximoNumeroConsultasException extends AccionWebException {
	/**
	 *
	 */
	public MaximoNumeroConsultasException() {
		super("Se alcanz? el n?mero m?ximo de consultas para el usuario. Si desea seguir consultando debe solicitar un nuevo turno de consulta");
	}

	/**
	 * @param arg0
	 */
	public MaximoNumeroConsultasException(String arg0) {
		super(arg0);
	}
}
