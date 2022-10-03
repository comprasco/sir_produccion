/*
 * Created on 18-mar-2005
 *
 */
package gov.sir.hermod.workflow;

/**
 * @author Dcantor
 *
 */
public abstract class AbstractWorkflow implements Workflow {
	
	private WorkflowActivityListener listener;
	public abstract void start() throws WFException;     
    public abstract void end() throws WFException;
    public abstract int getStatus();

 
    
    public final Mensaje procesar(Mensaje m) throws WFException {
    	listener.nuevoMensajeEntrada(m);
    	Mensaje s = procesarMensaje(m);
    	listener.nuevoMensajeSalida(m,s);
    	return s;
    }
    
    public abstract Mensaje procesarMensaje(Mensaje m) throws WFException;

   
	public void setListener(WorkflowActivityListener listenerParam) {
		this.listener = listenerParam;

	}

	public void removeListener() {
		this.listener = null;
	}
	
	final public WorkflowActivityListener getListener()
	{
		return this.listener;
	}
}