package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Excepción lanzada cuando no se pudo agergar un día festivo a un circulo.
 */
public class AdicionCirculoFestivoException extends EventoException {

	/**
	 * 
	 */
	public AdicionCirculoFestivoException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AdicionCirculoFestivoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public AdicionCirculoFestivoException(String arg0) {
		super(arg0);
	}

}
