package gov.sir.core.web.acciones.excepciones;

import java.util.List;
import java.util.Vector;

import org.auriga.smart.eventos.EventoException;

public class MigracionIncrementalException extends EventoException{
	
	/**
	 * Lista que contiene las excepciones generadas dentro
	 * de ForsetiException.
	 */
	private List errores = new Vector();
	
	/**
	 *
	 */
	public MigracionIncrementalException() {
		super("Error en la Migracion Incremental.");
	}

	/**
	 * @param arg0
	 */
	public MigracionIncrementalException(String arg0) {
		super(arg0);
	}
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public MigracionIncrementalException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	
	/**
	* @param e Lista con las excepciones generadas en Forseti. 
	*/
	public MigracionIncrementalException(List e) {
		super("Los siguientes errores se presentaron al intentar realizar la Migracion Incremental:");
		this.errores = e;
	}
	


	/**
	 * @return errores
	 */
	public List getErrores() {
		return errores;
	}

	
	
	public void setErrores(List errores) {
		this.errores = errores;
	}

	public void addError(String error) {
		errores.add(error);
	}

}
