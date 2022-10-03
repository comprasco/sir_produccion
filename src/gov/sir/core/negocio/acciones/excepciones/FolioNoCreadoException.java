package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;

/**
 * @author dlopez
 * Excepción generada cuando se presentan situaciones inconsistentes en la acción
 * de negocio para crear un folio. 
 */
public class FolioNoCreadoException extends ValidacionParametrosException {

	/**
	 * 
	 */
	public FolioNoCreadoException() {
		super();
	}

	/**
	 * Constructor para excepciones de tipo FolioNoCreadoException.
	 * @param l Lista con todos los errores asociados. 
	 */
	public FolioNoCreadoException(List l) {
		super(l);
	}

	/**
	 * Constructor para excepciones de tipo FolioNoCreadoException.
	 * @param l Lista con todos los errores asociados. 
	 */
	public FolioNoCreadoException(String mensaje, Throwable throwable) {
		super(mensaje, throwable);
	}
}