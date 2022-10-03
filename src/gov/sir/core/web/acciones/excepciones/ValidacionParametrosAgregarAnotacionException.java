package gov.sir.core.web.acciones.excepciones;

import java.util.Iterator;
import java.util.List;

/**
 * Excepcion para validar los campos en la forma de Agregar una anotación a un Folio.
 * @author ppabon
 */
public class ValidacionParametrosAgregarAnotacionException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase.
	 */
	public ValidacionParametrosAgregarAnotacionException() {
		super();
	}

	/**
	 * Constructor de la clase. Recibe una lista con errores.
	 * @param listErrores
	 */
	public ValidacionParametrosAgregarAnotacionException(List listErrores) {
		super();
		Iterator it = listErrores.iterator();
		while (it.hasNext()) {
			addError((String) it.next());
		}
	}
}
