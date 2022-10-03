package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class CreacionMunicipioException extends EventoException {

	/**
	 * 
	 */
	public CreacionMunicipioException() {
		super("No se pudo crear el municipio ");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CreacionMunicipioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public CreacionMunicipioException(String arg0) {
		super(arg0);
	}

}
