package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jmendez
 *
 */
public class CreacionDepartamentoException extends EventoException {

	/**
	 * 
	 */
	public CreacionDepartamentoException() {
		super("No se pudo crear el departamento ");
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CreacionDepartamentoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public CreacionDepartamentoException(String arg0) {
		super(arg0);
	}

}
