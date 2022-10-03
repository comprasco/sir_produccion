package gov.sir.core.negocio.acciones.excepciones;

import java.util.List;
import java.util.Vector;

import org.auriga.smart.eventos.EventoException;

/**
 * @author mmunoz
 */
public class ValidacionParametrosException extends EventoException {

	/**
	 * Lista que contiene las excepciones generadas dentro
	 * de ForsetiException.
	 */
	private List errores = new Vector();

	public ValidacionParametrosException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ValidacionParametrosException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidacionParametrosException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	* @param e Lista con las excepciones generadas en Forseti. 
	*/
	public ValidacionParametrosException(List e) {
		this.errores = e;
	}

	/**
	 * @return errores
	 */
	public List getErrores() {
		return errores;
	}

	public void addError(String error) {
		errores.add(error);
	}

}
