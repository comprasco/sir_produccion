package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * @author dlopez
 * Clase para representar los posibles tipos de Causales de Restituci�n
 * en el proceso de Restituci�n de Reparto Notarial. 
 */
public class CausalRestitucion implements TransferObject{

	
	/**
	 * Identificador del Causal de Restituci�n. 
	 */
	private String idCausalRestitucion; 
	private static final long serialVersionUID = 1L;
	
	/**
	* Nombre del Causal de Restituci�n
	*/
	private String nombre;
	
	
	/**
	* Descripci�n del Causal de Restituci�n
	*/
	private String descripcion;
    
    
    /**
     * Campo que indica si un causal de restituci�n se encuentra
     * actio o iniactivo.
     */    
    private boolean activo;
	
	/**
	* M�todo constructor de la Clase <code>CausalRestitucion</code>
	*/
	public CausalRestitucion () {
	}

	/**
	* Obtiene el identificador del causal de restituci�n
	* @return identificador del causal de restituci�n
	*/
	public String getIdCausalRestitucion() {
		return idCausalRestitucion;
	}

	/**
	* Asigna al atributo idCausalRestitucion el valor recibido como par�metro
	* @param idCausalRestitucion identificador del Causal de Restituci�n
	* que va a ser asignado al objeto
	*/
	public void setIdCausalRestitucion(String idCausalRestitucion) {
		this.idCausalRestitucion = idCausalRestitucion;
	}

	/**
	* Obtiene el nombre de un causal de restituci�n
	* @return el nombre del causal de restituci�n
	*/
	public String getNombre() {
		return nombre;
	}

	
	/**
	* Asigna al atributo nombre, el valor recibido como par�metro
	* @param nombre nombre que va a ser asignado al objeto
    */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    
	/**
	* Obtiene la descripci�n de un causal de restituci�n
	* @return la descripci�n del causal de restituci�n
	*/
	public String getDescripcion() {
		return descripcion;
	}

	
	/**
	* Asigna al atributo descripci�n, el valor recibido como par�metro
	* @param descripcion Descripci�n que va a ser asignada al objeto
	*/
	public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
    
    
	/**
	 * @return
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param b
	 */
	public void setActivo(boolean b) {
		activo = b;
	}

}