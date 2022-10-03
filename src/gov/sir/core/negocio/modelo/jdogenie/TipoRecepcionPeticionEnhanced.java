package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;

/*
 * @author dlopez
 */
   
/**
 * 
 * @author dlopez
 *
 * Representa la forma como se recibi� una solicitud de correcci�n
 * 
 */
public class TipoRecepcionPeticionEnhanced  extends Enhanced
{

	private String idTipoRecepcionPeticion; 
   	private String nombre;

	public TipoRecepcionPeticionEnhanced() {
	}

	/**
	 * @return El identificador del tipo de recepci�n de la petici�n. 
	 */
	public String getIdTipoRecepcion() {
		return this.idTipoRecepcionPeticion;
	}

    /**
    * 
    * @param El identificador del tipo de recepci�n de la petici�n. 
    */
	public void setIdTipoRecepcion (String idTipo) {
		this.idTipoRecepcionPeticion = idTipo;
	}

	
	/**
	* @return el nombre del tipo de recepci�n de la petici�n. 
	*/
	public String getNombre() {
		return this.nombre;
	}
	
	
	/**
	* 
	* @param El nombre del tipo de recepci�n de la petici�n. 
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