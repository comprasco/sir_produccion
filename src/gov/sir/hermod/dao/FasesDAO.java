/*
 * Clase para el manejo de las fases (actividades) asociadas con los flujos
 * de trabajo de los procesos de la aplicación. 
 * Created on 12 de julio de 2004, 9:10
 */
 
package gov.sir.hermod.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Fase;
import org.auriga.core.modelo.transferObjects.Rol;

/**
 * Clase para el manejo de las fases (actividades) asociadas con los flujos
 * de trabajo de los procesos de la aplicación. 
 * @author  mrios, mortiz, dlopez
 */
public interface FasesDAO {
    
    
	/**
	* Obtiene la lista de fases (actividades) asociadas a un proceso y una estacion
	* @param rol objeto Rol del cual se desean obtener las fases asociadas.
	* @param proceso objeto <code>Proceso</code> del cual se desean obtener las fases asociadas.
	* @return la lista de fases asociadas al proceso y al rol.
	* @see gov.sir.core.negocio.modelo.Fase
	* @see gov.sir.core.negocio.modelo.Proceso
	* @throws <code>DAOException</code>
	*/  
    List getFases(Rol rol, Proceso proceso) throws DAOException;
    
    
	/**
	* Obtiene la lista de fases (actividades) a las que puede avanzarse un turno
	* recibido como parámetro  
	* @param turno objeto <code>Turno</code> 
	* @return la lista de fases sigiuientes a la fase actual del turno recibido.
	* @see gov.sir.core.negocio.modelo.Fase
	* @throws <code>DAOException</code>
	*/  
    List getFasesSiguientes(Turno turno) throws DAOException;
    

    
	/**
	* Obtiene la lista de respuestas que puede utilizar un turno dado para avanzar. 
	* @param turno objeto Turno 
	* @return la lista de respuestas posibles en la fase actual del turno recibido.
	* @see gov.sir.core.negocio.modelo.Fase
	* @throws <code>DAOException</code>
	*/  
    List getRespuestasSiguientes(Turno turno) throws DAOException;
    
    
    
    
	/**
	* Obtiene la fase con que arranca el <code>Proceso</code> recibido. 
	* @param proceso objeto <code>Proceso del cual se quiere conocer su fase de 
	* arranque. 
	* @return la <code>Fase</code> de arranque para el <code>Proceso</code> recibido
	* como parámetro. 
	* @throws <code>DAOException</code>
	*/
    Fase gerFaseArranqueProceso(Proceso proceso) throws DAOException;
    
	/**
	* Obtiene la <code>Fase</code> identificada  con el id recibido. 
	* @param id_fase objeto <code>Fase</code>.
	* @return la <code>Fase</code> del Id recibido
	* como parámetro. 
	* @throws <code>DAOException</code>
	*/
	Fase getFaseById(String idFase) throws DAOException;
    
    
	/**
	 * Obtiene el tipo de una fase.  
	 * <p>
	 * El tipo de una fase puede ser Automático o Manual.
	 * @param fase_id el identificador de la fase de la cual se desea obtener su tipo.
	 * @return el tipo de fase (Automático o Manual) asociado con la fase. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.constantes.CFase;
	 */ 
	 String getTipoFase(String fase_id) throws DAOException;
	 
	 
	 
	 /**
	 * Obtiene el proceso dado el id de una fase.  
	 * @param fase_id el identificador de la fase de la cual se desea obtener su proceso.
	 * @return el proceso asociado a la fase dada. 
	 * @throws <code>DAOException</code>
	 */ 
	 String getProcesoByIdFase(String fase_id) throws DAOException;
	 
	 
	 /**
     * Obiene el iniciador de un proceso
     * @param idProceso El id del proceso
     * @return
     */
    InicioProcesos obtenerFaseInicial(String idProceso) throws DAOException;
	
}