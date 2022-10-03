/*
 * Interfaz que permite el manejo de las tarifas asociadas con las
 * liquidaciones de los procesos de la aplicaci�n. 
 */
package gov.sir.hermod.dao;

import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TarifaPk;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.TipoTarifaPk;

import java.util.Date;
import java.util.List;

/**
 * Interfaz que permite el manejo de las tarifas asociadas con las
 * liquidaciones de los procesos de la aplicaci�n. 
 * @author  mortiz, dlopez
 */
public interface TarifasDAO {
    
    
	/**
	* Obtiene el valor de una <code>Tarifa</code> de acuerdo al tipo y 
	* c�digo recibidos como par�metros. 
	* @param tipo el identificador del tipo
	* @param codigo el identificador del codigo
	* @return La <code>Tarifa</code> correspondiente a la 
	* asociaci�n tipo - c�digo 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	*/    
    Tarifa getTarifa(String tipo, String codigo) throws DAOException;
	
	
	
	/**
	* Obtiene el valor de una <code>Tarifa</code> anterior, de acuerdo al tipo y 
	* c�digo recibidos como par�metros. 
	* @param tipo el identificador del tipo
	* @param codigo el identificador del codigo
	* @param fecha Fecha en que fue ingresada la Tarifa
	* @return el valor de la <code>Tarifa</code> anterior, correspondiente a la 
	* asociaci�n tipo - c�digo - fecha
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	*/    
	double getTarifaAnterior(String tipo, String codigo, Date fecha) throws DAOException;
	
	
	
	/**
	 * Obtiene una lista de objetos de tipo <code>Tarifa</code> del tipo 
	 * ingresado como par�metro, de acuerdo con los datos existentes en la
	 * tabla de Tarifas
	 * @param tipoTarifa tipo de la tarifa del cual se quiere obtener el listado
	 * de tarifas existentes. 
	 * @return una lista de objetos <code>Tarifa</code> asociados con el tipo
	 * dado.
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @throws <code>DAOException</code>
	 */ 
    List getTarifas(TipoTarifa tipoTarifa) throws DAOException;
    
    
    
	/**
	* Obtiene una lista de los objetos <code>TipoTarifa</code> existentes en
	* la Base de Datos. 
	* <p>
	* Se establece como criterio de ordenamiento el id del tipo de tarifa. 
	* @return una lista de objetos <code>TipoTarifa</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	* @see gov.sir.core.negocio.modelo.TipoTarifa
	*/ 
	List getTiposTarifas() throws DAOException;
    
    
    
	/**
	* Agrega un tipo de  <code>Tarifa</code> a la tabla de Tarifas.
	* <p> La versi�n asignada a la tarifa es la 1.
	* <p> El m�todo lanza una excepci�n si ya existe un tipo de tarifa con
	* el identificador del objeto pasado como par�metro. 
	* @param tarifa Objeto <code>Tarifa</code> con todos atributos.
	* @return identificador del <code>TipoTarifa</code> generado.
	* @throws <code>DAOExeption</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	* @see gov.sir.core.negocio.modelo.TipoTarifa
	*/ 
    TipoTarifaPk addTipoTarifa(Tarifa tarifa) throws DAOException;
    
    
    
	/**
	* Agrega una <code>Tarifa</code> a la configuraci�n del sistema. 
	* <p> La versi�n asignada a la tarifa es la 1.
	* <p> El m�todo lanza una excepci�n si no existe un tipo de tarifa con
	* el identificador del objeto pasado como par�metro. 
	* @param tarifa Objeto <code>Tarifa</code> con todos atributos.
	* @return identificador de la <code>Tarifa</code> generada.
	* @throws <code>DAOExeption</code>
	* @see gov.sir.core.negocio.modelo.Tarifa
	*/ 
	TarifaPk addTarifa(Tarifa tarifa) throws DAOException;
	

	/**
	 * Obtiene una lista de los objetos <code>TipoTarifa</code> existentes en
	 * la Base de Datos que son configurables por c�rculo 
	 * <p>
	 * Se establece como criterio de ordenamiento el id del tipo de tarifa. 
	 * @return una lista de objetos <code>TipoTarifa</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 * @see gov.sir.core.negocio.modelo.TipoTarifa
	 */ 
	List getTiposTarifasConfiguradasPorCirculo() throws DAOException;
	

	/**
	 * Obtiene el valor de una <code>Tarifa</code> de acuerdo al tipo y 
	 * c�digo recibidos como par�metros. 
	 * @param tipo el identificador del tipo
	 * @param codigo el identificador del codigo
	 * @return el valor de la <code>Tarifa</code> correspondiente a la 
	 * asociaci�n tipo - c�digo 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Tarifa
	 */    
	Tarifa getObjetoTarifa(String tipo, String codigo) throws DAOException;
}