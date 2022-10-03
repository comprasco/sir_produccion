package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Excepción lanzada cuando se intenta imprimir y por reglas del negocio no se puede.
  */
public class ImpresionException extends EventoException {

	/**
	 * 
	 */
	public ImpresionException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ImpresionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ImpresionException(String arg0) {
		super(arg0);
	}

}