/*
 * Created on 18-mar-2005
 *
 */
package gov.sir.hermod.workflow;

/**
 * Esta interfaz define el contrato que deben implementar las clases
 * que escuchen por eventos sobre el workflow
 * @author dcantor
 *
 */
public interface WorkflowActivityListener {
	void nuevoMensajeEntrada(Mensaje m) throws WFException;
	void nuevoMensajeSalida(Mensaje entrada, Mensaje salida) throws WFException;
}
