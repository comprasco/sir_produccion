package gov.sir.core.negocio.acciones.excepciones;

/**
 * @author dlopez
 *
 */
public class ValidacionCategoriaException extends ValidacionParametrosHTException {

	/**
	 * 
	 */
	public ValidacionCategoriaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidacionCategoriaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ValidacionCategoriaException(String arg0) {
		super(arg0);
	}

}
