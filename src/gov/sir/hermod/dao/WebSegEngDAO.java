/*
 * Created on 22-jul-2005
 *
 */
package gov.sir.hermod.dao;


import java.util.List;

import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegEngPk;


/**
 * @author fceballos
 * Clase para el manejo de los objetos de tipo <code>WebSegEng</code> utilizados en la aplicacion.<p>
 * <p>
 * Provee servicios para la consulta, modificaci�n y adici�n de estos objetos.
 * @see gov.sir.core.negocio.modelo.WebSegEng
 */
public interface WebSegEngDAO {

	/**
	 * Crea una operaci�n de englobe o segregaci�n en la solicitud indicada
	 * @param operacion
	 * @param solID
	 * @return
	 * @throws DAOException
	 */
	public WebSegEngPk crearWebSegEng(WebSegEng operacion, SolicitudPk solID) throws DAOException;
	
	/**
	 * Elimina una operaci�n de englobe o segregaci�n dado su identificador
	 * @param operacionID
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarWebSegEng(WebSegEngPk operacionID) throws DAOException;
	
	/**
	 * Actualiza una operaci�n de englobe o segregaci�n dado su identificador y nuevos datos
	 * @param operacionID
	 * @param operacion
	 * @return
	 * @throws DAOException
	 */
	public boolean actualizarWebSegEng(WebSegEngPk operacionID, WebSegEng operacion) throws DAOException;
	
	/**
	 * Consulta una operaci�n de englobe o segregaci�n dado su identificador
	 * @param operacionID
	 * @return
	 * @throws DAOException
	 */
	public WebSegEng consultarWebSegEng(WebSegEngPk operacionID)throws DAOException;
	
	
	/**
	 * Consulta una operaci�n de englobe o segregaci�n dada su solicitud
	 * @param solicitudID
	 * @return
	 * @throws DAOException
	 */
	public List getWebSegEngBySolicitud(SolicitudPk solicitudID)throws DAOException;	
	
}
