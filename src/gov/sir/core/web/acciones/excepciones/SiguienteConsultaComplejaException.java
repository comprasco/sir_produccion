package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * Esta clase identifica una excepci�n ocurrida cuando se excede en n�mero
 * de cosultas que el usuario puede realizar
 * @author jmendez
 */
public class SiguienteConsultaComplejaException extends AccionWebException {
	/**
	 *
	 */
	public SiguienteConsultaComplejaException() {
		super("Se alcanz� el n�mero m�ximo de consultas para el usuario. Si desea seguir consultando debe solicitar un nuevo turno de consulta");
	}

	/**
	 * @param arg0
	 */
	public SiguienteConsultaComplejaException(String arg0) {
		super(arg0);
	}
}
