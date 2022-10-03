package gov.sir.hermod.workflow;

import java.util.Hashtable;

/**
 * Clase para el manejo de mensajes.
 * <p>
 * Los mensajes se encargan del paso de información desde y hacia el
 * Workflow, para determinar las actividades que deben realizarse. 
 * @author  dlopez, mrios
 */
public class Message {
	
	
	/**
	* Constante para el tipo de mensaje
	*/
	public final static String TIPO_MENSAJE = "TIPO_MENSAJE";
	
	/**
	* Constante para indicar que el mensaje es hacia el Workflow
	*/
	public final static String AL_WORKFLOW = "AL_WORKFLOW";
	
	
	/**
	* Constante para indicar que el mensaje es desde el Workflow
	*/
	public final static String DEL_WORKFLOW = "DEL_WORKFLOW";
    
    
	/**
	* Constante para el tipo de mensaje
	*/
	public final static String TIPO_PROCESO = "TIPO_PROCESO";
	
	
	/**
	* Constante para iniciar un proceso
	*/
	public final static String INICIAR_PROCESO = "INICIAR_PROCESO";
	
	
	/**
	* Constante para actualizar un proceso 
	*/
	public final static String ACTUALIZAR_PROCESO = "ACTUALIZAR_PROCESO";
	
	
	/**
    * Constante para consultar un proceso
	*/
	public final static String CONSULTAR_PROCESO ="CONSULTAR_PROCESO";
	
	
	/**
	* Constante para Revivir un turno
	*/
	public final static String REVIVIR_PROCESO ="REVIVIR_PROCESO";	
	
	/**
	* Constante para Revivir un turno
	*/
	public final static String HABILITAR_PROCESO ="HABILITAR_PROCESO";	

	/**
	* Constante para anular un turno
	*/
	public final static String ANULAR_PROCESO ="ANULAR_PROCESO";	
	
	/**
	* Constante para Realizar avances forzosos en el workflow
	*/
	public final static String SKIP ="SKIP";
	
	/**
	* Constante para Realizar avances forzosos en el workflow, a partir de una fase dada
	*/
	public final static String RETRY ="RETRY";		


	/**
	* Constante para permite a la consulta de un mensaje asegurar que se consulte el último Notification Id.
	*/
	public final static String CONSULTAR_ULTIMO_ID_NOTIFICATION ="CONSULTAR_ULTIMO_ID_NOTIFICATION";	
	
	


	private long id;
	private String tipoMensaje;
	private String tipoProceso;
	private Hashtable parametros;
	private boolean procesado;

    
    /**
     * Método constructor de la clase Mensaje
     * <p>El método lanza una excepción si se recibe un tipo de mensaje o
     * un proceso inválido. 
     * @param id Identificador del mensaje
     * @param tipoMensaje Tipo del mensaje
     * @param tipoProceso Tipo de Proceso
     * @param parametros Hashtable con parámetros para la actividad que deba
     * realizarse sobre el Workflow.
     * @throws <code>RuntimeException</code>
     */
    public Message(long idParam, String tipoMensajeParam, String tipoProcesoParam, Hashtable parametrosParam) {
        this.id = idParam;
        this.tipoMensaje = tipoMensajeParam;
        this.tipoProceso = tipoProcesoParam;
        this.parametros = parametrosParam;
        this.procesado = false;

        
        if ( !tipoMensaje.equals(AL_WORKFLOW) && 
             !tipoMensaje.equals(DEL_WORKFLOW) ) {
            throw new RuntimeException("tipo de mensaje invalido. Debe ser "+AL_WORKFLOW+
            " o "+DEL_WORKFLOW);
        }
        if (!tipoProceso.equals(INICIAR_PROCESO) && 
            !tipoProceso.equals(ACTUALIZAR_PROCESO) &&
            !tipoProceso.equals(CONSULTAR_PROCESO)&&
            !tipoProceso.equals(ANULAR_PROCESO)&&
			!tipoProceso.equals(REVIVIR_PROCESO)){
            throw new RuntimeException("proceso del mensaje invÁlido. Debe ser "+INICIAR_PROCESO+
            " o " +ACTUALIZAR_PROCESO + " o " + ANULAR_PROCESO + " o " + CONSULTAR_PROCESO + " o " + REVIVIR_PROCESO);
        }
    }

    /**
     * Retorna el identificador de un mensaje
     * @return el identificador de un mensaje
     */
    final public long getId(){
        return id;
    }

	/**
	* Retorna el tipo de mensaje de un mensaje
	* @return el tipo de mensaje de un mensaje
	*/
    final public String getTipoMensaje(){
        return tipoMensaje;
    }
    
	/**
	* Retorna el tipo de proceso de un mensaje
	* @return el tipo de proceso de un mensaje
	*/
    final public String getTipoProceso(){
        return tipoProceso;
    }

    
	/**
	* Retorna la tabla de parámetros asociada a un mensaje
	* @return la tabla de parámetros asociada a un mensaje
	*/
    final public Hashtable getParametros(){
        return parametros;
    }

	/**
	* Indica si un mensaje ha sido procesado.
	* @return el estado de un mensaje. 
	*/
    final public boolean isProcesado(){
        return procesado;
    }

    /**
     * Cambia a procesado el estado de un mensaje
     */
    public void procesar(){
        this.procesado = true;
    }
    

}