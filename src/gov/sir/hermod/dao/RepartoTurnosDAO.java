/*
 * Created on 07-feb-2005
 *
 */
package gov.sir.hermod.dao;

import java.util.List;
import java.util.Map;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.ProcesoReparto;
import gov.sir.core.negocio.modelo.Reparto;
import gov.sir.core.negocio.modelo.TurnoPk;

/**
 * 
 * @author dcantor
 */
public interface RepartoTurnosDAO {

    /**
    * Obtiene el último <code>Reparto</code> asociado con un <code>Turno</code>. 
    * @return <code>Reparto</code> con sus atributos.
    * @param turnoId Identificador del <code>Turno</code> del que se busca el <code>Reparto</code>
    * @throws DAOException
    * @see gov.sir.core.negocio.modelo.Reparto
    * @see gov.sir.core.negocio.modelo.Turno
    */
    public Reparto getLastReparto(TurnoPk turnoId) throws DAOException;

    /**
    * Adiciona un <code>ProcesoReparto</code> a la configuración del sistema.
    * <p>
    * Se genera una excepción en el caso en el que el <code>ProcesoReparto</code>
    * recibido como parámetro sea <code>null</code> o si el <code>Circulo</code> asociado
    * con el <code>Proceso</code> no exista en la Base de Datos.   
    * @param datos El objeto de tipo <code>ProcesoReparto</code> con sus atributos que va 
    * a ser agregado a la configuración del sistema. 
    * @return identificador del  <code>ProcesoReparto</code> generado. 
    * @see gov.sir.core.negocio.modelo.ProcesoReparto
    * @throws DAOException
    */
    public ProcesoReparto addProcesoReparto(ProcesoReparto datos) throws DAOException;

    /** 
    * Obtiene un mapa, cuyas llaves son objetos de tipo
   	* <code>SubtipoAtencion</code> y sus valores son objetos de tipo
   	* <code>Usuario</code> asociados.  
   	* @return Mapa con la asociación <code>SubtipoAtencion</code>, <code>Usuario</code>.    	
   	* 
   	* @param Lista de los Usuarios de los cuales se desean obtener sus subtipos de atención
    * asociados. 
    * @throws DAOException
    * @see gov.sir.core.negocio.modelo.SubtipoAtencion
    * @see gov.sir.core.negocio.modelo.Usuario
    * 
    */
    public Map getUsuariosBySubtipoAtencion(List usuario) throws DAOException;

    /**
     * Retorna una lista de los usuarios que pertenecen a un <code>Circulo</code>
     * con sus subtipos de atención asociados. 
     * @param logins Lista con los logins de los usuarios. 
   	 * @param idCirculo identificador del <code>Circulo</code>, utilizado como filtro. 
   	 * @return una lista de los usuarios que pertenecen al <code>Circulo</code> con id
   	 * dado, con sus respectivos subtipos de atención asociados.
   	 * @see gov.sir.core.negocio.modelo.Circulo
   	 * @see gov.sir.core.negocio.modelo.Usuario
   	 * @see gov.sir.core.negocio.modelo.SubtipoAtencion 
   	 * @throws DAOException
   	 */
    public List getSubTipoAtencionUsuario(List usuarios, String circuloId) throws DAOException;

    /**
     * Retorna una lista de los usuarios que pertenecen a un círculo
     * con sus subtipos de atención asociados. 
     * @param usuarios Lista con objetos de tipo <code>Usuario</code>
     * @param circulo <code>Circulo</code> utilizado como filtro. 
     * @return una lista de los usuarios que pertenecen al <code>Circulo</code> dado, con sus
     * respectivos subtipos de atención asociados. 
     * @see gov.sir.core.negocio.modelo.Circulo
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion
     * @throws DAOException
     */
    public List getSubTipoAtencionByUsuario(List usuarios, Circulo circulo) throws DAOException;
    
    
	/**
	* Actualiza el atributo orden para todas las relaciones Usuario - SubTipo de Atención
	* definidas dentro del Circulo recibido como parámetro.
	* <p>El usuario que tenía el orden 1 pasa al final de la lista, y en los demás casos, el orden
	* se reduce en una unidad.  
	* @throws <code>DAOException</code>
	* @param circulo El <code>Circulo </code> en el que se debe actualizar el orden  para las
	* relaciones Usuario - SubtipoAtencion.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado del proceso. 
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public boolean actualizarRotacionReparto (Circulo circulo) throws DAOException;
	

	/**
	 * Retorno un mapa en donde las llaves son los subtipos de atención y los valores
	 * corresponden a la lista de los usuarios del círculo especificado rotados
	 * @param circulo Identificador del círculo
	 * @return Mapa [SubtipoAtencion, Lista usuarios en orden]
	 * @throws DAOException
	 */
	public Map getUsuariosPorSubtiposDeAtencionRotados(Circulo circulo) throws DAOException;

}