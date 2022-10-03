/*
 * LookupDAO.java
 *
 * Created on 9 de agosto de 2004, 16:19
 */

package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupCodesPk;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OPLookupTypesPk;

import java.util.List;

/**
 *
 * @author  mrios, dsalas, dlopez
 */
public interface LookupDAO {
    
    /**
     * Obtiene el valor de una variable, dado su lookupType y su LookupCode
     * @param tipo El LookupType de la variable. 
     * @param codigo El LookupCode de la variable. 
     * @return El valor de la variable.
     * @throws DAOException
     */
     String getValor(String tipo, String codigo) throws DAOException;
     
     
	/**
	* Modifica el valor de una variable, dado su lookupType y su LookupCode
	* @param tipo El LookupType de la variable. 
    * @param codigo El LookupCode de la variable.
    * @param nuevoValor El nuevo valor asignado a la variable.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado
	* de la operación. 
	* @see gov.sir.core.negocio.modelo.OPLookupTypes
	* @see gov.sir.core.negocio.modelo.OPLookupCodes
	* @throws <code>DAOException</code>
	*/
	public boolean updateLookupCode (String tipo, String codigo, String nuevoValor) throws DAOException;
	
	
	/**
	 * Obtiene la lista con los LookupTypes existentes en
	 * el sistema.
	 * @return Lista con objetos de tipo <code>OPLookupTypes</code>
	 * @see gov.sir.core.negocio.modelo.OPLookupTypes</code>
	 * @throws <code>DAOException</code>
	 */
	List getLookupTypes() throws DAOException;
	
	
    List getLookupCodes(String tipo) throws DAOException;
    List getTiposDocumento() throws DAOException;
    List getTipoDocJuridico() throws DAOException;
    List getTipoDocNatural() throws DAOException;
    List getModalidad() throws DAOException;
    List getDeterminacionInm() throws DAOException;
    List getTipoPersona() throws DAOException;
    List getTipoSexo() throws DAOException;
    String getValorLookupCodes(String tipo, String idCodigo) throws DAOException;
    List getOPLookupCodesByTipo(String tipo) throws DAOException;
    List getValorLookupCodesByTipo(String tipo) throws DAOException;
    
    /**
	 * Agrega un LookupType en el sistema.
	 * @param lut El <code>OPLookupTypes</code> que se va a agregar.
	 * @returnidentificador del OPLookupTypes generado
	 * @see gov.sir.core.negocio.modelo.OPLookupTypes
	 * @throws <code>DAOException</code>
	 */
	OPLookupTypesPk addLookupType (OPLookupTypes tipo) throws DAOException;
	
	/**
	* Edita un LookupType en el sistema.
	* @param datoAEditar El <code>OPLookupTypes</code> que se va a editar.
	* @param dato 	El <code>OPLookupTypes</code> que tiene los nuevos datos.	
	* @return identificador del OPLookupTypes generado
	* @see gov.sir.core.negocio.modelo.OPLookupTypes
	* @throws <code>DAOException</code>
	*/
	OPLookupTypesPk updateLookupType (OPLookupTypes datoAEditar, OPLookupTypes dato) throws DAOException;
	
	
	/**
	 * Agrega un LookupCode en el sistema.
	 * @param luc El <code>OPLookupCodes</code> que se va a agregar.
	 * @return identificador del OPLookupCodes generado
	 * @see gov.sir.core.negocio.modelo.OPLookupCodes
	 * @throws <code>DAOException</code>
	 */
	OPLookupCodesPk addLookupCode (OPLookupCodes lcp)  throws DAOException;
	
	/**
	* Edita un LookupCode en el sistema.
	* @param datoAEditar El <code>OPLookupCodes</code> que se va a editar.
	* @param dato El <code>OPLookupCodes</code> con los nuevos datos.
	* @return identificador del OPLookupCodes editado
	* @see gov.sir.core.negocio.modelo.OPLookupCodes
	* @throws <code>DAOException</code>
	*/
	OPLookupCodesPk updateLookupCode (OPLookupCodes datoAEditar, OPLookupCodes dato)  throws DAOException;
       
}
