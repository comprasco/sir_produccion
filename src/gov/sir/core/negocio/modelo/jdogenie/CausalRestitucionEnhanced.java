package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CausalRestitucion;


/**
 * @author dlopez
 * Clase para representar los posibles tipos de Causales de Restituci�n
 * en el proceso de Restituci�n de Reparto Notarial. 
 */
public class CausalRestitucionEnhanced extends Enhanced{

	
	/**
	* Identificador del Causal de Restituci�n. 
	*/
	private String idCausalRestitucion; 
	
	
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
	 * activo o inactivo.
	 */    
	private boolean activo;
    
    
	/**
	* M�todo constructor de la Clase <code>CausalRestitucionEnhanced</code>
	*/
	public CausalRestitucionEnhanced() {
	}

	/**
	* Obtiene el identificador del causal de restituci�n.
	* @return identificador del causal de restituci�n.
	*/
	public String getIdCausalRestitucion() {
		return idCausalRestitucion;
	}

	
	/**
	* Asigna al atributo idCausalRestitucion el valor recibido como par�metro.
	* @param idCausalRestitucion identificador del Causal de Restituci�n
	* que va a ser asignado al objeto.
	* 
	*/
	public void setIdCausalRestitucion(String idCausalRestitucion) {
		this.idCausalRestitucion = idCausalRestitucion;
	}

	
	/**
	* Obtiene el nombre de un causal de restituci�n. 
	* @return el nombre del causal de restituci�n.
	*/
	public String getNombre() {
		return nombre;
	}


	/**
	* Asigna al atributo nombre, el valor recibido como par�metro.
	* @param nombre nombre que va a ser asignado al objeto
	*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	* Obtiene la descripci�n de un causal de restituci�n. 
	* @return la descripci�n del causal de restituci�n.
	*/
	public String getDescripcion() {
		return descripcion;
	}

	
	/**
	* Asigna al atributo descripci�n, el valor recibido como par�metro.
	* @param descripcion Descripci�n que va a ser asignada al objeto.
	*/
	public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
        

/**
 * Convierte un objeto <code>CausalRestitucion</code> en un objeto
 * <code>CausalRestitucionEnhanced</code>
 * @param causal objeto <code>CausalRestitucion</code> que va a ser
 * convertido en un  objeto <code>CausalRestitucionEnhanced</code>
 * @return objeto <code>CausalRestitucionEnhanced</code> generado a partir
 * de un objeto <code>CausalRestitucion</code>
 */
	public static CausalRestitucionEnhanced enhance(CausalRestitucion causal) {
		return (CausalRestitucionEnhanced) Enhanced.enhance(causal);
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