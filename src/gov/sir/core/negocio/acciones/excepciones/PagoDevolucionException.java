package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;

/**
 * @author ppabon
 * Excepci�n lanzada cuando se intenta realizar una devoluci�n de un valor que no se debe.
 */
public class PagoDevolucionException extends ValidacionParametrosException {

	/**
	 * Constructor de la axcepci�n
	 */
	public PagoDevolucionException() {
		super();
	}

	/**
	 * Constructor para excepciones de tipo PagoDevolucionException.
	 * @param l Lista con todos los errores asociados. 
	 */
	public PagoDevolucionException(List l) {
		super(l);
	}

}