package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

public class NegocioCorreccionesCorreccionSimpleException extends EventoException {

	/**
	 * 
	 */
	public NegocioCorreccionesCorreccionSimpleException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NegocioCorreccionesCorreccionSimpleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param str
	 */
	public NegocioCorreccionesCorreccionSimpleException(String str) {
		super(str);
	}
	

}
