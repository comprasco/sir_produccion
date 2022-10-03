package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;

/**
 * @author ppabon
 * Excepción lanzada cuando se intenta realizar una devolución de un valor que no se debe.
 */
public class PagoDevolucionException extends ValidacionParametrosException {

	/**
	 * Constructor de la axcepción
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