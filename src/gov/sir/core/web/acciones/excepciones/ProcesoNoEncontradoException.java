package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;

/**
 * @author jmendez
 *
 */
public class ProcesoNoEncontradoException extends AccionWebException {

	public ProcesoNoEncontradoException(String string) {
		super(string);
	}

	public ProcesoNoEncontradoException() {
		super("No se Encontr� el proceso en la sesi�n");
	}

}
