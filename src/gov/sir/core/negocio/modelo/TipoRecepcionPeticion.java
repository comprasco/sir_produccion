package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;
/**
/* Clase que modela la informacion de los tipos de recepcion de correcciones configurados en el sistema
 * @author dlopez
 */

public class TipoRecepcionPeticion implements TransferObject {

	private String idTipoRecepcionPeticion; 
   	private String nombre;
        private static final long serialVersionUID = 1L;
   	/** Metodo constructor por defecto  */
   	
	public TipoRecepcionPeticion() {
	}

	/** Retorna el identificador del tipo de recepcion 
	 * @return El identificador del tipo de recepción de la petición. 
	 */
	public String getIdTipoRecepcion() {
		return this.idTipoRecepcionPeticion;
	}

	/** Modifica el identificador del tipo de recepcion 
    * @param El identificador del tipo de recepción de la petición. 
    */
	public void setIdTipoRecepcion (String idTipo) {
		this.idTipoRecepcionPeticion = idTipo;
	}

	/** Retorna el nombre del tipo de recepcion 
	* @return el nombre del tipo de recepción de la petición. 
	*/
	public String getNombre() {
		return this.nombre;
	}
	
	/** Modifica el nombre del tipo de recepcion  
	* @param El nombre del tipo de recepción de la petición. 
	*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}