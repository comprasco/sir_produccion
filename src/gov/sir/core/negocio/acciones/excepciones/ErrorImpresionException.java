package gov.sir.core.negocio.acciones.excepciones;

import org.auriga.smart.eventos.EventoException;

/**
 * @author jfrias
*/
public class ErrorImpresionException extends EventoException {

	private String idTurno;

	/**
	 * 
	 */
	public ErrorImpresionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ErrorImpresionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ErrorImpresionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ErrorImpresionException(String arg0, String idTurno) {
		super(arg0);
		this.idTurno = idTurno;
	}

	/**
	 * @return
	 */
	public String getIdTurno() {
		return idTurno;
	}

}
