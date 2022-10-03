package gov.sir.core.web.acciones.excepciones;

import java.util.Iterator;
import java.util.List;

/**
 * Excepción para validar los campos en la forma de Edición de un Folio de un digitador.
 * @author ppabon
 */
public class ValidacionParametrosModificarFolioDigitacionException extends ValidacionParametrosException {

	/**
	 * Constructor de la clase. 
	 */
	public ValidacionParametrosModificarFolioDigitacionException() {
		super();
	}

	/**
	 * Constructor de la clase. Este constructor recibe una lista con excepciones.
	 * @param listErrores
	 */
	public ValidacionParametrosModificarFolioDigitacionException(List listErrores) {
		super();
		Iterator it = listErrores.iterator();
		while (it.hasNext()) {
			addError((String) it.next());
		}
	}

}
