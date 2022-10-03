/*
 * Created on 22-jul-2005
 *
 */
package gov.sir.hermod.dao;

import java.util.List;


/**
 * @author fceballos
 * Clase para el manejo de los objetos de tipo <code>Alerta</code> utilizados en la aplicacion.<p>
 * <p>
 * Provee servicios para la consulta, modificaci�n y adici�n de estos objetos.
 * @see gov.sir.core.negocio.modelo.Alerta
 */
public interface AlertasDAO {
	
	/**
	 * Obtiene la lista de alertas para la estaci�n determinada. Si no se tiene alertas
	 * se retorna una lista vac�a. Si existen alertas se rebaja el contador de alertas, si este
	 * llega a 0 se elimina la alerta.
	 * @param idEstacion
	 * @return
	 * @throws DAOException
	 */
	public List getAlertas(String idEstacion)throws DAOException;
	
}
