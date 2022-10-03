package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * @author dlopez
 * Clase para representar los posibles tipos de Causales de Restitución
 * en el proceso de Restitución de Reparto Notarial. 
 */
public class CausalRestitucion implements TransferObject{

	
	/**
	 * Identificador del Causal de Restitución. 
	 */
	private String idCausalRestitucion; 
	private static final long serialVersionUID = 1L;
	
	/**
	* Nombre del Causal de Restitución
	*/
	private String nombre;
	
	
	/**
	* Descripción del Causal de Restitución
	*/
	private String descripcion;
    
    
    /**
     * Campo que indica si un causal de restitución se encuentra
     * actio o iniactivo.
     */    
    private boolean activo;
	
	/**
	* Método constructor de la Clase <code>CausalRestitucion</code>
	*/
	public CausalRestitucion () {
	}

	/**
	* Obtiene el identificador del causal de restitución
	* @return identificador del causal de restitución
	*/
	public String getIdCausalRestitucion() {
		return idCausalRestitucion;
	}

	/**
	* Asigna al atributo idCausalRestitucion el valor recibido como parámetro
	* @param idCausalRestitucion identificador del Causal de Restitución
	* que va a ser asignado al objeto
	*/
	public void setIdCausalRestitucion(String idCausalRestitucion) {
		this.idCausalRestitucion = idCausalRestitucion;
	}

	/**
	* Obtiene el nombre de un causal de restitución
	* @return el nombre del causal de restitución
	*/
	public String getNombre() {
		return nombre;
	}

	
	/**
	* Asigna al atributo nombre, el valor recibido como parámetro
	* @param nombre nombre que va a ser asignado al objeto
    */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    
	/**
	* Obtiene la descripción de un causal de restitución
	* @return la descripción del causal de restitución
	*/
	public String getDescripcion() {
		return descripcion;
	}

	
	/**
	* Asigna al atributo descripción, el valor recibido como parámetro
	* @param descripcion Descripción que va a ser asignada al objeto
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