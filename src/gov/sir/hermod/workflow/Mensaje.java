/*
 * Created on 18-mar-2005
 *
 */
package gov.sir.hermod.workflow;

import java.util.Hashtable;

/**
 * 
 * Lleva la informacion hacia el workflow para avanzar un proceso y 
 * de regreso para informar el estado del proceso en el workflow
 * 
 * @author Dcantor
 */
public class Mensaje {
	
	/**
	* Constante para indicar que el mensaje es hacia el Workflow
	*/
	public final static String ENTRADA = "MENSAJE_ENTRADA";
	
	/**
	* Constante para indicar que el mensaje es desde el Workflow
	*/
	public final static String SALIDA = "MENSAJE_SALIDA";
	
	/**
	 * Constante para identificar el proceso al que se le envia el mensaje
	 */
	public final static String PROCESO_ID = "PROCESO_ID";
    
	/**
	 * Constante para identificar la instancia a la que se envia le mensaje
	 */
	public final static String INSTANCIA_ID ="INSTANCIA_ID";
    
	/**
	* Constante para iniciar un proceso
	*/
	public final static String INICIAR = "INICIAR_WORKFLOW";
	
	/**
	* Constante para actualizar un proceso 
	*/
	public final static String AVANZAR = "AVANZAR_WORKFLOW";
	
	/**
	* Constante para consultar un proceso
	*/
	public final static String CONSULTAR ="CONSULTAR_WORKFLOW";

    
	private long id;
	private String proceso;
	private String instancia;
	private String tipo;
	private String accion;
	private Hashtable parametros;
	private boolean procesado;

    
	/**
	 * Método constructor de la clase Mensaje
	 * <p>El método lanza una excepción si se recibe un tipo de mensaje o
	 * un proceso inválido. 
	 */
	public Mensaje(long idParam, String procesoParam, String instanciaParam, String tipoParam, String accionParam, Hashtable parametrosParam) {
		procesado = false;
		this.id = idParam;
		this.proceso = procesoParam;
		this.instancia = instanciaParam;
		this.tipo = tipoParam;
		this.accion = accionParam;
		this.parametros = parametrosParam;
		
        
		if ( !tipo.equals(ENTRADA) && 
			 !tipo.equals(SALIDA) ) {
			throw new IllegalArgumentException("tipo de mensaje invalido. Debe ser "+ENTRADA+
			" o "+SALIDA);
		}
		
		if (!accion.equals(INICIAR) && 
			!accion.equals(AVANZAR) &&
			!accion.equals(CONSULTAR)){
			throw new IllegalArgumentException("proceso del mensaje invalido. Debe ser "+INICIAR+
			" o " +AVANZAR + " o " + CONSULTAR);
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
	final public String getTipo(){
		return tipo;
	}
    
	/**
	* Retorna el proceso al que pertenece el mensaje
	* @return el proceso al que pertenece el mensaje
	*/
	final public String getProceso(){
		return proceso;
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
    
    /**
     * Indica la accion asociada a este mensaje
     * @return la accion asociada a este mensaje
     */
    final public String getAccion() {
        return accion;
    }

    /**
     * Indica el nombre de la instancia de workflow asociada a este mensaje
     * @return la instancia asociada a este mensaje
     */
    final public String getInstancia() {
        return instancia;
    }
    
    public Object getParametro(String llave){
    	if (parametros != null){
    		return parametros.get(llave);
    	}
    	else{
    		return null;
    	}
    }

}
