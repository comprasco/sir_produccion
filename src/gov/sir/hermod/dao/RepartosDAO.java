/*
 * RepartosDAO.java
 *
 * Created on 29 de octubre de 2004, 10:45 
 */

package gov.sir.hermod.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CausalRestitucionPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoNotarialPk;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.RepartoNotarialPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.ZonaNotarial;

/**
 *
 * @author  dlopez
 */
public interface RepartosDAO 
{  
	
   /**
	* Obtiene la lista de los Causales de Restituci�n existentes en
	* la configuraci�n del sistema. 
	* <p>
	* El ordenamiento de los resultados se realiza alfab�ticamente, utilizando
	* como criterio de ordenamiento el nombre del <code>CausalRestitucion</code>
	* @return una lista de objetos <code>CausalRestitucion</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	*/    
	public List getCausalesRestitucion () throws DAOException;
	

   /**
	* Adiciona un Causal de Restituci�n a la configuraci�n del sistema.
	* <p>
	* El m�todo genera una excepci�n si ya existe un Causal de restituci�n con el
	* identificador del objeto pasado como par�metro. 
	* @param datos objeto <code>CausalRestitucion</code> con sus atributos. 
	* @return identificador del Causal de Restituci�n generado. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	* 
	*/
	public CausalRestitucionPk addCausalRestitucion (CausalRestitucion datos) throws DAOException;
	
	
	/**
	* Retorna la <code>Categoria</code> en la cual se debe clasificar una <code>Minuta</code>,
	* teniendo como primer criterio de clasificaci�n el valor  y como
	* segundo criterio el n�mero de unidades.  
	* <p> El m�todo lanza una excepci�n si la cuant�a y el n�mero de unidades de 
	* la <code>Minuta</code> son inv�lidos. 
	* @param minuta, la <code>Minuta</code> que se va a clasificar. 
	* @return la <code>Categoria</code> en la cual se debe clasificar la <code>Minuta</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public Categoria getCategoriaClasificacionMinuta (Minuta minuta) throws DAOException;
	
	
	
	/**
	* Crea una <code>Minuta</code> persistente y la asocia a una solicitud de reparto notarial
	* de minutas.
	* @param sol La <code>SolicitudRepartoNotarial</code> a la cual se va a asociar la 
	* <code>Minuta</code> recibida como par�metro.
	* @param min La <code>Minuta</code> que va a ser asociada a la 
	* <code>SolicitudRepartoNotarial</code> recibida como par�metro.
	* @return Una <code>SolicitudRepartoNotarial</code> con la <code>Minuta</code> recibida
	* como par�metro asociada.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
	* @see gov.sir.core.negocio.modelo.OficinaOrigen
	*/
	public SolicitudRepartoNotarial addMinutaToSolicitudReparto (Solicitud solReparto, Minuta minuta) throws DAOException;
	
	
	
	/**
	* Obtiene la lista de minutas que no tienen asignado un <code>RepartoNotarial</code>.
	* @return Lista con las minutas que no tienen asignado un <code>RepartoNotarial</code>. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.RepartoNotarial
	*/
	public List getMinutasNoAsignadas () throws DAOException;
	
	
	
	/** 
	 * Anula la <code>Minuta</code> recibida como par�metro y hace persistente
	 * la modificaci�n.
	 * <p>
	 * El m�todo genera una excepci�n si no se encuentra la <code>Minuta </code> con el
	 * identificador pasado como par�metro.  
	 * @param minuta La <code>Minuta</code> que va a ser anulada.
	 * @return true o false dependiendo del resultado de la anulaci�n de la <code>Minuta</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public boolean anularMinutaRepartoNotarial (Minuta minuta, Usuario usuario) throws DAOException;
	
	
	
	/** 
	* Modifica la <code>Minuta</code> recibida como par�metro y hace persistente
	* la modificaci�n.
	* <p>
	* El m�todo genera una excepci�n si no se encuentra la <code>Minuta </code> con el
	* identificador pasado como par�metro.  
	* @param min La <code>Minuta</code> que va a ser modificada. 
	* @param generarAuditoria flag que indica si se debe generar auditor�a de la modificaci�n.
	* @param usuario Usuario que realiza la modificaicion.
	* @return true o false dependiendo del resultado de la anulaci�n de la <code>Minuta</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	*/
	public Minuta modificarMinuta (Minuta min,boolean generarAuditoria, Usuario usuario) throws DAOException;
	
	
	
	/** 
	* Obtiene el listado de minutas no asignadas dentro de una Vereda.
	* @param vereda La <code>Vereda</code> en la cual se van a buscar las Minutas
	* no asignadas.
	* @return Listado con objetos de tipo <code>Minuta</code> que no tienen asignado
	* un <code>RepartoNotarial</code> dentro de la <code>Vereda</code> pasada como
	* par�metro. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.RepartoNotarial
	* @see gov.sir.core.negocio.modelo.Vereda
	*/
	public List getMinutasNoAsignadasByVereda (VeredaPk vereda) throws DAOException;
	
	
	
	/** 
	 * Retorna una <code>Minuta</code> persistente, dado el id del workflow,
	 * asociado con su <code>turno</code> de creaci�n.
	 * @param wfId El id del workflow asociado con la creaci�n de la <code>Minuta</code>.
	 * @return La <code>Minuta</code> con toda su informaci�n persistente. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public Minuta getMinutaByWFId (String wfId) throws DAOException;
	
	/** 
	 * Retorna una <code>Minuta</code> persistente, dado el id del workflow,
	 * asociado con su <code>turno</code> de creaci�n.
	 * @param wfId El id del workflow asociado con la creaci�n de la <code>Minuta</code>.
	 * @return La <code>Minuta</code> con toda su informaci�n persistente. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getMinutasByWFId (String wfId) throws DAOException;
	
	

	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Registral. 
	 * @param circ El <code>Circulo</code> en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, n�mero de Turno y notar�a asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String idExtraordinario) throws DAOException;
			
			
	/**
	* Realiza la Restituci�n de Reparto Notarial para la Notar�a que la solicita.
	* El proceso de Restituci�n realiza las siguientes acciones:
	* 1. Marca la <code>Solicitud</code> como aceptada.
	* 2. Anula la <code>Minuta</code> asociada con la <code>Solicitud</code> 
	* 3. Coloca la Notar�a que realiz� la solicitud como primera, dentro de la
	* cola de Notar�as para la categor�a a la que pertenec�a la <code>Minuta</code>
	* @param idSolicitud Identificador de la solicitud
	* de restituci�n. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoNotarial
	* @see gov.sir.core.negocio.modelo.Minuta
	*/
	public Hashtable realizarRestitucionRepartoNotarial(String idSolicitud)
		   throws DAOException;
		   
		   
	/**
	* Servicio que permite adicionar el texto correspondiente a una resoluci�n 
	* de restituci�n de reparto notarial a una <code>SolicitudRestitucionReparto</code>
	* @param resolucion Resoluci�n que va a ser asociada a la <code>Solicitud</code>
	* @param solicitud La <code>SolicitudRestitucionReparto</code> a la que se va  a
	* asignar la resoluci�n.
    * @param observaciones Comentario que explica por qu� fue aceptada o rechazada una solicitud de restituci�n
    * @param fechaResolucion fecha en la que fue creada la resoluci�n de restituci�n.
	* @return <code>true</code> (�xito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operaci�n. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	*/
	public boolean addResolucionToSolicitudRestitucion (SolicitudRestitucionReparto solicitud, String resolucion, String observaciones, Date fechaResolucion)
		throws DAOException;
				
	
	/**
	* Servicio que permite marcar como rechazada  una <code>SolicitudRestitucionReparto</code>
	* @param solicitud La <code>SolicitudRestitucionReparto</code> que va a ser marcada como
	* rechazada. 
	* @return <code>true</code> (�xito) o <code>false</code> (fracaso) dependiendo del
	* resultado de la operaci�n. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	*/
	public boolean rechazarSolicitudRestitucion (SolicitudRestitucionReparto solicitud)
		throws DAOException;
		

	/**
	 * Obtiene el listado de entidades p�blicas que intervienen como otorgantes, en el proceso de reparto
	 * notarial 
	 * @return Listado de entidades p�blicas que intervienen como otorgantes en el proceso de reparto
	 * notarial.
	 * @throws DAOException
	 */
	public List getEntidadesReparto () throws DAOException;
	
	
	/**
	 * Obtiene el listado de entidades p�blicas que intervienen como otorgantes, en el proceso de reparto con una 
	 * naturaleza juridica determinada.
	 * @return Listado de entidades p�blicas que intervienen como otorgantes en el proceso de reparto
	 * notarial, que contienen la naturaleza jur�dica dada.
	 * @throws Throwable
	 */	
	public List getEntidadesRepartoByNaturaleza (NaturalezaJuridicaEntidad naturalezaJuridicaReparto) throws DAOException;	
	

	/**
	 * Obtiene el listado de naturalezas jur�dicas de entidades p�blicas que intervienen como otorgantes, en el proceso de reparto
	 * notarial 
	 * @return Listado de entidades p�blicas que intervienen como otorgantes en el proceso de reparto
	 * notarial.
	 * @throws DAOException
	 */
	public List getNaturalezasJuridicasEntidades () throws DAOException;
	
	
	/**
	* Obtiene el listado de Minutas pendientes por repartir dentro de un C�rculo
	* Registral.
	* @param circuloRegistral el <code>Circulo</code> en el cual se van a buscar
	* las minutas pendientes de reparto.
	* @return Lista de minutas por repartir dentro del <code>C�rculo</code> recibido
	* como par�metro.
	* @throws <code>DAOException</code>
	*/
    public List getMinutasNoRepartidasByCirculoRegistral(Circulo circuloRegistral) throws DAOException;
    
    
	/**
	* Obtiene el listado de C�rculos Notariales, asociados con un C�rculo Registral.
	* @param circulo <code>Circulo</code> del cual se van a obtener los circulos notariales.
	* @return Lista de C�rculos Notariales, asociados con un C�rculo Registral.
	* @throws DAOException
	*/
	public List getCirculosNotarialesByCirculoRegistral (Circulo circulo) throws DAOException;
	
	

	/**
	 * Obtiene una hashtable con el estado de la cola de las notar�as
	 * por categor�a. La llave es la categor�a y el valor es la lista de
	 * oficinas origen.
	 * @return
	 * @throws DAOException
	 */
	public Hashtable getColasRepartoByCategoria() throws DAOException;
	
	
	/**
	* Obtiene el listado de Minutas en las que aparece como otorgante una persona
	* natural.
	* @param otorgante, nombre del otorgante que se quiere consultar dentro del listado 
	* de minutas.
	* @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
	* @param circulo regiatrl al c ual pertenece el usuario en sesion
	* @return Lista de minutas en las que aparece el otorgante recibido como par�metro.
	* @see gov.sir.core.negocio.modelo.constantes.CReparto
	* @throws <code>DAOException</code>
	*/
	public List getMinutasByOtorganteNatural(String otorganteNatural, long estado, Circulo circuloRegistral) throws DAOException;
		
		
	/**
	* Obtiene el listado de Minutas en las que aparece como otorgante una entidad p�blica.
	* @param otorgantePublico, nombre del otorgante p�blico que se quiere consultar dentro del listado 
	* de minutas.
	* @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
	* @param circulo regiatral al cual pertenece el usuario en sesion
	* @return Lista de minutas en las que aparece el otorgante recibido como par�metro.
	* @see gov.sir.core.negocio.modelo.constantes.CReparto
	* @throws <code>DAOException</code>
	*/
	public List getMinutasByOtorgantePublico(String otorgantePublico, long estado, Circulo circuloRegistral) throws DAOException;
	
	
	/**
	 * Obtiene el listado de Minutas radicadas dentro de un rango de fechas dado.
	 * @param fechaInicial fecha de inicio para el rango.
	 * @param fechaFinal fecha de finalizaci�n para el rango.
	 * @return Lista de minutas radicadas dentro del rango dado 
	 * @see gov.sir.core.negocio.modelo.constantes.CReparto
	 * @throws <code>HermodException</code>
	 */
	 public List getMinutasRadicadasByRangoFecha (Date fechaInicial, Date fechaFinal) throws DAOException;
	 
	 

	/**
	* Obtiene el listado de Minutas repartidas dentro de un rango de fechas dado.
	* @param fechaInicial fecha de inicio para el rango.
	* @param fechaFinal fecha de finalizaci�n para el rango.
	* @param Circulo circulo registral al cual opertenece el usuario
	* @return Lista de minutas repartidas dentro del rango dado 
	* @throws <code>HermodException</code>
	*/
	public List getMinutasRepartidasByRangoFecha (Date fechaInicial, Date fechaFinal, Circulo circulo) throws DAOException;
	
	
	/**
	* Permite agregar una Naturaleza Jur�dica de Entidad P�blica a la configuraci�n del Sistema.
	* @param naturaleza Naturaleza Jur�dica de Entidad que va a ser adicionada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>DAOException</code>
	*/
	public boolean agregarNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException;


	/**
	* Permite editar una Naturaleza Jur�dica de Entidad P�blica para el reparto notarial.
	* @param naturaleza Naturaleza Jur�dica de Entidad que va a ser editada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>Throwable</code>
	*/
	public boolean editarNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException;
	
	
	
	/**
	* Permite actualizar el estado de una Naturaleza Jur�dica de Entidad P�blica para el reparto notarial.
	* @param naturaleza Naturaleza Jur�dica de Entidad que va a ser actualizada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>Throwable</code>
	*/
	public boolean actualizarEstadoNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException;
	
	   
	/**
	* Permite obtener el identificador de workflow de un turno asociado con una minuta con id pasado como
	* par�metro.
	* @param idMinuta identificador de la minuta de la cual se desea obtener su turno asociado.
	* @return  identificador de workflow de un turno asociado con una minuta con id pasado como  par�metro.
	* @throws <code>DAOException </code>
	*/
	public String getIdWorkflowByIdMinuta(String idMinuta) throws DAOException;
	
	

	/**
	* Permite agregar una Entidad P�blica a la configuraci�n del Sistema.
	* @param entidadPublica <code>EntidadPublica</code> que va a ser adicionada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>DAOException</code>
	*/
	public boolean addEntidadPublica (EntidadPublica entidadPublica) throws DAOException;
	

	/**
	* Permite actualizar una Entidad P�blica a la configuraci�n del Sistema.
	* @param entidadPublica <code>EntidadPublica</code> que va a ser actualizado.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>Throwable</code>
	*/
	public boolean editarEntidadPublica (EntidadPublica entidadPublica) throws DAOException;
	

	/**
	* Permite eliminar una Entidad P�blica de la configuraci�n del Sistema.
	* @param entidadPublica <code>EntidadPublica</code> que va a ser eliminada
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>DAOException</code>
	*/
	public boolean eliminarEntidadPublica (EntidadPublica entidadPublica) throws DAOException;
	
	
	/**
	* Permite eliminar una Naturaleza Jur�dica de Entidad P�blica de la configuraci�n del Sistema
	* @param naturaleza Naturaleza Jur�dica de Entidad que va a ser eliminada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operaci�n.
	* @throws <code>DAOException</code>
	*/
	public boolean eliminarNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException;
	   
	   
	/**
	* Obtiene el n�mero de modificaciones permitidas en la edici�n de minutas de reparto notarial
	* @return el n�mero de modificaciones permitidas en la edici�n de minutas de reparto notarial
	* @throws <code>DAOException</code>
	*/
	public int getNumModificacionesMinutas () throws DAOException;
    
    
	/**
	* Modifica el atributo n�mero de ediciones realizadas a una minuta
	* @param minuta La minuta en la que se va a modificar el atributo.
	* @throws <code>DAOException</code>
	*/
	public void updateNumModificacionesMinuta (Minuta minuta) throws DAOException;
	
	
	/**
	* Obtiene el listado de turnos de restituci�n asociados con una minuta. 
	* @return listado de turnos de restituci�n asociados con una minuta. 
	* @param idCir C�rculo Registral asociado con la minuta
	* @param idMin Identificador de la minuta
	* @throws <code>DAOException</code>
	*/
	public List getListadoTurnosRestitucionMinutas (String idCir, String idMin) throws DAOException;
	
	/**
	 * Agrega un circulo notarial
	 * @param circuloNotarial circulo notarial a agregar
	 * @throws DAOException
	 */
	public void agregarCirculoNotarial(CirculoNotarial circuloNotarial) throws DAOException;
	
	/**
	 * Elimina un circulo notarial
	 * @param circuloNotarial circulo notarial a eliminar
	 * @throws DAOException
	 */
	public void eliminarCirculoNotarial(CirculoNotarial circuloNotarial) throws DAOException;
	
	/**
	 * Edita un circulo notarial
	 * @param circuloNotarial circulo notarial a editar
	 * @throws Throwable
	 */
	public void editarCirculoNotarial(CirculoNotarial circuloNotarial) throws DAOException;
	
	/**
	 * Consulta un circulo notarial
	 * @param circuloNotarial circulo notarial a consultar
	 * @throws Throwable
	 */
	public CirculoNotarial consultarCirculoNotarial(CirculoNotarialPk idCirculo) throws DAOException;
	
	/**
	 * Agrega un zona notarial
	 * @param circuloNotarial zona notarial a agregar
	 * @throws DAOException
	 */
	public void agregarZonaNotarial(ZonaNotarial zonaNotarial) throws DAOException;
	
	/**
	 * Elimina una zona notarial
	 * @param zonaNotarial zona notarial a eliminar
	 * @throws DAOException
	 */
	public void eliminarZonaNotarial(ZonaNotarial zonaNotarial) throws DAOException;
	
	/**
	 * Agrega una notaria a la cola de reparto notarial
	 * @param notaria
	 * @return
	 * @throws Throwable
	 */
	public void agregarNotariaReparto(OficinaOrigen notaria)throws DAOException;
	
	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Registral. 
	 * @param circ El <code>Circulo</code> en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, n�mero de Turno y notar�a asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String[] idsExtraordinarios) throws DAOException;
	
	public RepartoNotarial getRepartoNotarialByID (RepartoNotarialPk repartoNotarial) throws DAOException;

	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Notarial. 
	 * @param circuloNotarial El <code>CirculoNotarial</code> Notarial en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 *  
	 * @return Hashtable con las asociaciones, n�mero de Turno y notar�a asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculoNotarialOrdinario(CirculoNotarial circuloNotarial, Usuario usuario) throws DAOException;
	
	/** 
	 * Realiza el proceso de consumo de secuencial si no se realziar ninguna reparto
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public String consultarLastSecuencialCirculoNotarial(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo) throws DAOException;
	
	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Notarial. 
	 * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, n�mero de Turno y notar�a asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculoNotarialExtraOrdinario(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo, String[] idsExtraordinaro) throws DAOException;

}