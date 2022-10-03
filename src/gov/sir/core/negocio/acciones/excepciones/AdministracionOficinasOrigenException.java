package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author ppabon
 *
 * Excepción lanzada cuando no es posible ejecutar alguna actualización, inserción o eliminación
 * de alguna de las oficinas origen existentes en el sistema.
 */
public class AdministracionOficinasOrigenException extends EventoException {

	/**
	 * 
	 */
	public AdministracionOficinasOrigenException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AdministracionOficinasOrigenException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public AdministracionOficinasOrigenException(String arg0) {
		super(arg0);
	}

}
