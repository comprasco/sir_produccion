package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 * Clase que se lanza cuando se genera una excepci�n de negocio, en la administraci�n de naturalezas jur�dicas de reparto.
 *
 */
public class AdministracionNaturalezaJuridicaRepartoException extends EventoException {
	/**
	 * Constructor de la clase, con un valor especifico
	 * @param string El mensaje de la excepcion
	 */
	public AdministracionNaturalezaJuridicaRepartoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * @param arg0
	 */
	public AdministracionNaturalezaJuridicaRepartoException(String arg0) {
		super(arg0);
	}	
	
}
