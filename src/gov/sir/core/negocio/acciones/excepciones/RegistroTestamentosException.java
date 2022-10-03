package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * Esta excepción es lanzada cuando ha ocurrido algún error en la acción de negocio de ANTestamento al
 * realizar una inscripción de testamentos.
 * @author ppabon
 */
public class RegistroTestamentosException extends EventoException {

	/**
	 *	Constructor de la clase. 
	 */
	public RegistroTestamentosException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RegistroTestamentosException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public RegistroTestamentosException(String arg0) {
		super(arg0);
	}

}
