/*
 * Created on 01-mar-2005
 */
package gov.sir.core.negocio.acciones.excepciones;

/**
 * @author gvillal
 * Excepcion que ocurre cuando no se puede realizar la reimpresion especial.
 */
public class ReimpresionEspecialNoEfectuadaException extends ImpresionNoEfectuadaException{
	/**
	 * 
	 */
	public ReimpresionEspecialNoEfectuadaException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ReimpresionEspecialNoEfectuadaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ReimpresionEspecialNoEfectuadaException(String arg0) {
		super(arg0);
	}

}
