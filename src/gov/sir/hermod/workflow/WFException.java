/*
 * WFException.java
 * Clase para el manejo de Excepciones relacionadas con el WorkFlow.
 * Created on 9 de agosto de 2004, 16:15
 */

package gov.sir.hermod.workflow;

import gov.sir.hermod.HermodException;

/**
 * Clase para el manejo de Excepciones relacionadas con el Workflow.
 * @author  mrios, dlopes
 */
public class WFException extends HermodException {
	
	
    /**
	* Mensaje generado cuando no es posible iniciar la instancia del Workflow. 
	*/
	public static String WF_NO_INICIADO = "No se pudo iniciar la instancia de workflow";
	
	
	/**
	* Mensaje generado cuando no es posible avanzar el Workflow. 
	*/
	public static String WF_SIN_AVANZAR = "No se pudo avanzar el workflow";
	
	
	/**
	* Mensaje generado cuando no es posible conectarse al contexto del Workflow. 
	*/
	public static String WF_SIN_CONTEXTO = "No se pudo conectar al contexto de workflow";
    
   /**
    * Crea una nueva instancia de <code>WFException</code>
    * sin ningun mensaje detallado
    */
    public WFException() {
        super();
    }
    
    /**
     * Crea una nueva instancia de <code>WFxception</code> 
     * con el mensaje detallado
     * @param msg el mensaje detallado
     */
    public WFException(String msg) {
        super(msg);
    }
    
    /**
     * Crea una nueva instancia de <code>WFException</code>
     * con el mensaje detallado, y el Throwable correspondiente a la excepción
     * @param msg el mensaje detallado
     * @param tr el throwable de la excepción
     */
    public WFException(String msg, Throwable tr) {
        super(msg, tr);
    }
    
}