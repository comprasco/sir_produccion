/*
 * Created on 10-feb-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.CiudadanoProhibicionPk;
import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.ProhibicionPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface CiudadanoDAO {



	/**
	* Adiciona una Prohibicion a la configuración del sistema
	* @param datos objeto Prohibicion con sus atributos exceptuando su identificador
	* el cual es generado por el sistema
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk addProhibicion(Prohibicion datos)
		throws DAOException;



	/**
	* Edita una Prohibicion en la configuración del sistema
	* @param datos objeto Prohibicion con sus atributos incluido su identificador
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk editarProhibicion(Prohibicion datos)
		throws DAOException;



	/**
	* Agrega una prohibición a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibición
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws DAOException
	*/
	public CiudadanoProhibicionPk addProhibicionToCiudadano(CiudadanoPk oid, ProhibicionPk pid, CirculoPk cirid, String comentario, Usuario usuario)
		throws DAOException;


	/**
	* Agrega una prohibición a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibición
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws DAOException
	*/
	public boolean desactivarProhibicionToCiudadano(CiudadanoProhibicionPk pid, Usuario usuario,String comentarioAnulacion)
		throws DAOException;


	/**
	 * Elimina una prohibición configurada en el sistema
	 * @param dato
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarProhibicion(Prohibicion dato)
		throws DAOException;



	/**
	* Obtiene una lista con todos las prohibiciones de ciudadano
	* configurados en el sistema
	* @return lista de objetos EstadoFolio
	* @see gov.sir.core.negocio.modelo.EstadoFolio
	* @throws DAOException
	*/
	public List getProhibiciones() throws DAOException;


	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @param idCirculo TODO
	 * @return
	 * @throws DAOException
	 */
	public Ciudadano getCiudadanoByDocumento(String tipodoc,
		String doc, String idCirculo) throws DAOException;
	
	/**
	 * Obtiene el ciudadanoTMP dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @param idCirculo TODO
	 * @return
	 * @throws DAOException
	 */
	public CiudadanoTMP getCiudadanoTmpByDocumento(String tipodoc,
		String doc, String idCirculo) throws DAOException;

  /**
   * Obtiene un Ciudadano dado su identificador
   * @param oid
   * @return objeto EstadoFolio
   * @throws DAOException
   */
  public Ciudadano getCiudadano(CiudadanoPk oid) throws DAOException;


	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @param idCirculo TODO
	 * @return
	 * @throws DAOException
	 */
	public Ciudadano getCiudadanoByDocumentoSolicitante(String tipodoc, String doc, String idCirculo)
		throws DAOException;
		


	/**
	 * Actualiza un ciudadano en el modelo temporal
	 * @param ciud
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCiudadano(Ciudadano ciud, Usuario usuario, String numRadicacion) 
	throws DAOException;
	
	/**
	 * Actualiza un ciudadano en el modelo
	 * @param ciud
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCiudadanoAdministrativa(Ciudadano ciud, Usuario usuario) throws DAOException;
	
  
	/**
	 * Obtiene un ciudadano por medio del identificador, si el ciudadano tiene cambios temporales devuelve
	 * estos cambios, si no, devuelve el ciudadano definitivo, si el ciudadano no existe retorna null
	 * @param oid identificador del ciudadno
	 * */
	public Ciudadano getCiudadanoByIdTMP( CiudadanoPk oid )   throws  DAOException;

	/**
	 * Crea un nuevo ciudadano en la base de datos
	 * @param ciudadano Objeto <code>Ciudadano</code> que contiene la información necesaria para crear el nuevo ciudadano.
	 * @return
	 * @throws DAOException
	 */
	public Ciudadano crearCiudadano(Ciudadano ciudadano) throws DAOException;
}
