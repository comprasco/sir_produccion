/*
 * Interfaz para el manejo de los procesos existentes en la aplicación.
*/
package gov.sir.hermod.dao;

import java.util.List;

import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoProcesoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;

/**
 * Interfaz para el manejo de los procesos existentes en la aplicación. 
 * @author  mrios, mortiz, dlopez
 */
public interface ProcesosDAO 
{
       
    
	/**
	* Obtiene la lista de procesos en los que participa un
	* determinado rol.
	* @return una lista de objetos <code>Proceso</code> que están asociados
	* con el rol recibido como parámetro.
	* @param id_rol el identificador del rol del cual se desean obtener los
	* procesos asociados.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
    List getProcesos(String id_estacion) throws DAOException;
    
    
    
	/**
	* Obtiene la lista de los procesos que inicia un determinado rol.
	* @param id_rol Identificador del rol del cual se desean obtener los
	* procesos de los cuales es iniciador.
	* @return Lista con los procesos que inicia el rol.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
    List getProcesosQueInicia(String id_estacion) throws DAOException;
    
    
    
	/** 
	* Obtiene un objeto de tipo <code>Proceso</code>, dado su identificador.
	* @param pID Identificador del <code>Proceso</code> que se quiere recuperar.
	* @return El <code>Proceso</code> con identificador pasado como parámetro.  
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
    Proceso getProcesoByID(ProcesoPk pID) throws DAOException;
    
    
    
	/**
	* Obtiene un <code>CirculoProceso</code> dado su identificador.
	* @param cpID identificador del <code>CirculoProceso</code> que se quiere
	* recuperar. 
	* @return <code>CirculoProceso</code> con sus atributos 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/	
    CirculoProceso getCirculoProcesoById(CirculoProcesoPk cpID) throws DAOException;
    
	/**
	* Obtiene la lista de procesos existentes en el sistema
	* @return una lista de objetos <code>Proceso</code> que están disponibles en el sistema
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	List getListaProcesos() throws DAOException;
	
	/**
	* Obtiene la lista de objetos de tipo <code>Procesos_V</code> con las fases relacionadas
	* con el <code>Proceso</code> pasado como parámetro
	* @return una lista de objetos <code>Procesos_V</code> que con las fases del proceso pasado como parámetro
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	List getFasesDeProceso(Proceso proceso) throws DAOException;
	

	/**
	* Obtiene la lista de procesos existentes en el sistema
	* @return una lista de objetos <code>Proceso</code> que están disponibles en el sistema
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Proceso
	*/
	Fase getFaseInicialProceso(ProcesoPk procId) throws DAOException;
	
	
	
	/**
	* Obtiene y avanza la secuencia de las constancias de devoluciones, de acuerdo 
	* a los parametros recibidos. 
	* @param circuloId El identificador del <code>Circulo</code> en el que se va a 
	* expedir la constancia de devolución.
	* @param year El año en el que se va a expedir la constancia de devolución. 
	* @param pm Persistence Manager de la transacción.
	* @return El secuencial requerido. 
	* @throws DAOException
	*/
   public long getSecuencialDevolucion (String circuloId, String year)
	   throws DAOException;
   
   
   /**
	* Obtiene y avanza la secuencia de los recibos de certificados masivos, de acuerdo 
	* a los parametros recibidos. 
	* @param circuloId El identificador del <code>Circulo</code> en el que se va a 
	* expedir el recibo de certificados masivos.
	* @param year El año en el que se va a expedir el recibo de certificados masivos. 
	* @return El secuencial requerido. 
	* @throws DAOException
	*/
  public long getSecuencialMasivos (String circuloId, String year)
	   throws DAOException;

  /**
    * @author      :   Julio Alcázar Rivas
    * @change      :   Obtiene y avanza la secuencia de los recibos de certificados masivos, de acuerdo
    * a los parametros recibidos.
    * @Caso Mantis :   000941
    * @param circuloId El identificador del <code>Circulo</code> en el que se va a
    * expedir el recibo de certificados masivos.
    * @param year El año en el que se va a expedir el recibo de certificados masivos.
    * @return El secuencial requerido.
    * @throws HermodException
    */
  public long getSecuencialMasivosExento (String circuloId, String year)
	   throws DAOException;
  
  public List getListaProcesosRelacion() throws DAOException;
  /**
    * @author      :   Diana Lora
    * @change      :   Obtiene la secuencia de los recibos de reparto notarial, de acuerdo
    * a los parametros recibidos.
    * @Caso Mantis :   0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
    * @param year El año en el que se va a expedir el recibo de reparto notarial.
    * @return El secuencial requerido.
    * @throws HermodException
    */
    public long getSecuencialRepartoNotarial(String circuloId, String year) throws DAOException ;
    
    /**
    * @author      :   Edgar Lora
    * @change      :   Obtiene la secuencia de los recibos de reparto notarial, de acuerdo
    * a los parametros recibidos.
    * @Caso Mantis :   0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
    * @param year El año en el que se va a expedir el recibo de reparto notarial.
    * @return El secuencial requerido.
    * @throws HermodException
    */
    public long getSecuencialRepartoNotarialRecibo(String circuloId, String year) throws DAOException ;
}
