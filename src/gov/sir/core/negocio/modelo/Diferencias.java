package gov.sir.core.negocio.modelo;

import java.io.Serializable;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase que sirve para mostrar las diferencias o cambios que realizó un corrector en el proceso de correcciones.
 * Sirve para mostrar como estaba la información antes y después de realizar la corrección.
 * @author ppabon
 */
public class Diferencias implements TransferObject, Serializable{
	private String id;
	private String antes;
	private String despues;
        private static final long serialVersionUID = 1L;
	/** Metodo constructor
	 * @param id
	 * @param antes
	 * @param despues
	 */
	public Diferencias(String id, String antes, String despues) {
		this.id = id;
		this.antes = antes;
		this.despues = despues;
	}

	/**
	 * @return
	 */
	public String getDespues() {
		return despues;
	}

	/**
	 * @param string
	 */
	public void setDespues(String string) {
		despues = string;
	}

	/**
	 * @return
	 */
	public String getAntes() {
		return antes;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param string
	 */
	public void setAntes(String string) {
		antes = string;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

}
