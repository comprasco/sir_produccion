package gov.sir.core.negocio.acciones.excepciones;

import java.util.Hashtable;

import org.auriga.smart.eventos.EventoException;

import gov.sir.forseti.ForsetiException;
import gov.sir.hermod.HermodException;

/**
 * @author mmunoz
 */
public class ValidacionParametrosHTException extends EventoException {

	private Hashtable hashErrores = new Hashtable();
	
	private String mensajeFormulario = null;

	/**
	 * 
	 */
	public ValidacionParametrosHTException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ValidacionParametrosHTException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidacionParametrosHTException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		if (arg1 instanceof ForsetiException) {
			hashErrores = ((ForsetiException) arg1).getHashErrores();
		}
		if (arg1 instanceof HermodException) {
			hashErrores = ((HermodException) arg1).getHashErrores();
		}		
	}

	/**
	 * @param e
	 */
	public ValidacionParametrosHTException(ForsetiException e) {
		hashErrores = e.getHashErrores();
	}
	
	/**
	 * @param e
	 */
	public ValidacionParametrosHTException(HermodException e) {
		hashErrores = e.getHashErrores();
	}

	/**
	 * @param e
	 */
	public ValidacionParametrosHTException(ForsetiException e, String mensajeFormulario) {
		hashErrores = e.getHashErrores();
		this.mensajeFormulario = mensajeFormulario;
	}
	
	/**
	 * @param e
	 */
	public ValidacionParametrosHTException(HermodException e, String mensajeFormulario) {
		hashErrores = e.getHashErrores();
		this.mensajeFormulario = mensajeFormulario;
	}

	/**
	 * @param e
	 */
	public ValidacionParametrosHTException(Hashtable ht) {
		hashErrores = ht;
	}


	/**
	 * @return
	 */
	public Hashtable getHashErrores() {
		return hashErrores;
	}

	/**
	 * @return
	 */
	public String getMensajeFormulario() {
		return mensajeFormulario;
	}

	/**
	 * @param string
	 */
	public void setMensajeFormulario(String string) {
		mensajeFormulario = string;
	}

}
