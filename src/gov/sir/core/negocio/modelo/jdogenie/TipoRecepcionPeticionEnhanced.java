package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;

/*
 * @author dlopez
 */
   
/**
 * 
 * @author dlopez
 *
 * Representa la forma como se recibió una solicitud de corrección
 * 
 */
public class TipoRecepcionPeticionEnhanced  extends Enhanced
{

	private String idTipoRecepcionPeticion; 
   	private String nombre;

	public TipoRecepcionPeticionEnhanced() {
	}

	/**
	 * @return El identificador del tipo de recepción de la petición. 
	 */
	public String getIdTipoRecepcion() {
		return this.idTipoRecepcionPeticion;
	}

    /**
    * 
    * @param El identificador del tipo de recepción de la petición. 
    */
	public void setIdTipoRecepcion (String idTipo) {
		this.idTipoRecepcionPeticion = idTipo;
	}

	
	/**
	* @return el nombre del tipo de recepción de la petición. 
	*/
	public String getNombre() {
		return this.nombre;
	}
	
	
	/**
	* 
	* @param El nombre del tipo de recepción de la petición. 
	*/
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


    
	public static TipoRecepcionPeticionEnhanced enhance (
	TipoRecepcionPeticion tipoRecepcionPeticion)
	{
		return (TipoRecepcionPeticionEnhanced)Enhanced.enhance(tipoRecepcionPeticion);
	}
}