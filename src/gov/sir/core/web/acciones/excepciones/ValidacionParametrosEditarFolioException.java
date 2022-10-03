package gov.sir.core.web.acciones.excepciones;

import java.util.Iterator;
import java.util.List;

/**
 * Excepcion para validar los campos en la forma de Edición de un Folio.
 * @author ppabon
 */
public class ValidacionParametrosEditarFolioException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase. 
	 */
	public ValidacionParametrosEditarFolioException() {
		super();
	}

	/**
	 * Constructor de la clase. Este constructor recibe una lista con excepciones.
	 * @param listErrores
	 */
	public ValidacionParametrosEditarFolioException(List listErrores) {
		super();
		Iterator it = listErrores.iterator();
		while (it.hasNext()) {
			addError((String) it.next());
		}
	}

}
