package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase que modela los datos que corresponden a la definición de los items del certificado
 */

public class ItemCertificado implements TransferObject{
    private static final long serialVersionUID = 1L;
    /**
     * 
     *@link aggregationByValue
     *@associates <{gov.sir.core.negocio.modelo.Anotacion}>
     *@supplierCardinality 1
     *@clientCardinality 1..*
     */
    private gov.sir.core.negocio.modelo.Anotacion anotacion;

    /** Descripcion de uno de los items de la solicitud */
    private String descripcion;

	/**
	 * Identificador unico para cada item del certificado
	 */
	private long idItem;	
	
	/**
	 * Método constructor con valores especificos para la clase ItemCertificado
	 * @param anotacion
	 * @param descripcion
	 */
    public ItemCertificado(long idItem, gov.sir.core.negocio.modelo.Anotacion anotacion, String descripcion) {
        this.anotacion = anotacion;
        this.descripcion = descripcion;
    }
	
	/**
	 * Este método retorna 
	 * @param idItem
	 */
	public void setId(long idItem){
		this.idItem = idItem;
	}
	
	/**
	 * Este método retorna el valor actual del identificador del item del certificado
	 * @return número (long) mayor que cero (0)
	 */
	public long getId(){
		return idItem;
	}
	
	/**
	 * Este método cambia el valor de la anotacion del item certificado
	 * @param anotacion variable tipo Anotacion que contiene la informacion 
	 * de la nueva anotacion
	 */
    public void setAnotacion(gov.sir.core.negocio.modelo.Anotacion anotacion) {
        this.anotacion = anotacion;
    }

	/**
	 * Este método retorna la anotacion del item del certificado
	 * @return un objeto de tipo Anotacion, que deber ser diferente de nulo
	 */
    public gov.sir.core.negocio.modelo.Anotacion getAnotacion() {
        return anotacion;
    }
    
    /**
     * Este método cambia la descripcion de item del certificado
     * @param descripcion variable con la nueva descripcion para el item del certificado
     */
    public void setDescripcion(String descripcion){
    	this.descripcion=descripcion;
    }
    
    /**
     * Este método retorna el valor de la descripcion que tiene el item del certificado
     * @return un String que no es nulo
     */
    public String getDescripcion(){
    	return descripcion;
    }
}
