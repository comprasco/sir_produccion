/*
 * HermodWFFactory.java
 * Obtiene la implementación de la fábrica
 * Created on 9 de agosto de 2004, 16:06
 */

package gov.sir.hermod.workflow;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.HermodProperties;


/**
 * @author  mrios, dcantor
 */
public abstract class HermodWFFactory {
    
    /**
     * Obtiene la implementación de la fábrica
     * @return la fábrica concreta
     * @throws <code>WFException</code>
     */    
    public static HermodWFFactory getFactory() throws WFException {
        HermodProperties prop = HermodProperties.getInstancia();
        String clase = prop.getProperty(HermodProperties.HERMOD_WF_FACTORY);
        
        try{
            return (HermodWFFactory) Class.forName(clase).newInstance();
        }
        catch (Exception e) {
        	Log.getInstance().error(HermodWFFactory.class,e.getMessage(), e);
            throw new WFException(WFException.WF_NO_INICIADO); 
        }
    }

    /**
     * Devuelve un <CODE>Processor</CODE>
     * @return un objeto que implementa la interfaz <CODE>Processor</CODE>
     * @throws <code>WFException</code>
     */    
    public abstract Processor getProcessor() throws WFException;
    
    
	/**
	 * Devuelve un <code>Workflow</code>
	 * @return un objeto que implementa la interfaz <code>Workflow</code>
	 * @throws WFException cuando no se pueda obtener el objeto
	 */
	public abstract Workflow getWorkflow() throws WFException;
    
        
}