package gov.sir.hermod.workflow.impl.oracle;

import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.dao.TurnosDAO;
import gov.sir.hermod.workflow.Mensaje;
import gov.sir.hermod.workflow.WFException;
import gov.sir.hermod.workflow.WorkflowActivityListener;

import org.auriga.core.modelo.transferObjects.Estacion;


import org.auriga.smart.servicio.ServiceLocator;


/**
 * El coordinador es el componente que escucha por eventos sobre el workflow de 
 * Oracle.
 *
 * Es responsable de  actualizar la historia del turno y
 * mantener la ejecucion de solicitudes concurrentes a medida que
 * se avanza el workflow.
 * 
 * Ejemplo:
 * 
 * <code>
 * Mensaje entrada = new Mensaje(....);
 * Workflow wf = HermodWFFactory.getFactory().getWorkflow();
 * Mensaje salida = wf.procesar(entrada);
 * </code>
 * 
 * En este ejemplo la fabrica concreta (OracleHermodWFFactory) devuelve 
 * un objeto OracleWorkflow, el cual ya tiene un CoordinadorWFSAS asociado.
 * 
 * Al llamar a <code>wf.procesar</code> ntes de que el mensaje sea procesado 
 * por la implementacion (en este caso OracleWorkflow)
 * se llama al metodo nuevoMensajeEntrada, el cual se encuentra definido en la interfaz
 * <code>WorkflowActivityListener</code> y es llamado por <code>AbstractWorkflow</code>.
 * 
 * Una vez que el workflow ha procesado el mensaje, se llama a <code>nuevoMensajeSalida</code> 
 * siguiendo el mismo mecanismo.
 *
 * @see gov.sir.hermod.workflow.AbstractWorkflow
 * @see gov.sir.hermod.workflow.WorkflowActivityListener
 * @see gov.sir.hermod.workflow.impl.oracle.OracleWorkflow
 * @author dcantor
 * 
 */
public class CoordinadorWFSAS implements WorkflowActivityListener {
    
	
    /** Usuario que tiene privilegios para ejecutar los programas concurrentes
     * de SAS */
    public static final String USUARIO_ADMINISTRATIVO = "SAS_SISTEMA";
    private TurnosDAO turnosDAO;
    private ServiceLocator sl;

    /**
     * Crea un nuevo coordinador Workflow - SAS. Aqui se obtiene una
     * referencia al DAO de turnos de hermod para consultar y actualizar los turnos.
     * Se obtiene tambien, una instancia de SAS para coordinar la ejecucion
     * de los programas concurrentes correspondientes a cada una de las
     * fases de un workflow.
     *
     */
    public CoordinadorWFSAS() {
        try {
            turnosDAO = HermodDAOFactory.getFactory().getTurnosDAO();
            sl = ServiceLocator.getInstancia();
        }
        catch (Exception e) {
            Log.getInstance().error(CoordinadorWFSAS.class,e);
        }
    }

    /**
     * Es llamado ANTES de enviar un mensaje de entrada al workflow.
     * 
     * Si el mensaje es del tipo Mensaje.AVANZAR se ejecuta la solicitud
     * concurrente asociada a la fase actual.
     */
    public void nuevoMensajeEntrada(Mensaje entrada) throws WFException {
    	
    }

    /**
     * 
     * Este metodo es invocado DESPUES de que se envia un mensaje al workflow y
     * el workflow ha respondido con un mensaje de salida.
     * 
     * Si el mensaje es del tipo Mensaje.AVANZAR se genera la nueva solicitud
     * concurrente de sas que refleja la nueva fase en la que queda el workflow.
     *
     * Por otra parte, si el mensaje es del tipo Mensaje.INICIAR, se crea
     * la primera solicitud concurrente asociada a la instancia recien creada.
     * Dicha solicitud concurrente debe ser creada despues de creado el workflow,
     * es decir, cuando se obtiene el primer mensaje de salida ya que
     * este mensaje lleva el item_key que fue asignado por Oracle Workflow.
     *
     * @param mensaje mensaje original enviado al workflow
     * @param mensaje respuesta mensaje obtenido como respuesta del workflow
     * @throws WFException si ocurre cualquier problema
     */
    public void nuevoMensajeSalida(Mensaje entrada, Mensaje salida) throws WFException {
    	
    }

    /**
     * Encargado de actualizar el modelo operativo. Este metodo consulta el turno
     * por el item key y actualiza la fase y la ultima respuesta del turno.
     * Tambien adiciona un nuevo registro en la historia del turno
     * Arroja WFException si ocurre cualquier problema.
     *
     * @param item_key identificador de la instancia del workflow
     * @param activity actividad nueva
     * @param result resultado de la actividad anterior
     * @param usuario usuario
     * @param marcaDelegacion marca de delegacion (? heredado de programa hermod)
     * @throws WFException si ocurre algun problema
     */
    private void actualizarModeloOperativo(String item_key, String activity, String result, String usuario, String marcaDelegacion) throws WFException {
        Turno turno = null;

        try {
            turno = turnosDAO.getTurnoByWFId(item_key);
            turno.setIdFase(activity);
            turno.setUltimaRespuesta(result);
            turnosDAO.updateTurno(turno);
            
        }
        catch (DAOException e) {
			Log.getInstance().error(CoordinadorWFSAS.class,e);
            throw new WFException("Error actualizando el modelo operativo",e);
        }
        Log.getInstance().debug(CoordinadorWFSAS.class,"MODELO OPERATIVO ACTUALIZADO [item key:" + item_key + ", fase:" + activity + ", respuesta:" + result + ']');
    }

    /**
     * Crea una nueva solicitud concurrente para el workflow (item key) y
     * la actividad (activity) indicadas. El mensaje de entrada y de salida
     * de la interaccion con el workflow viajan como parametros de la solicitud.
     *
     * @param item_key instancia del workflow para la cual se creara una
     * solicitud concurrente
     * @param activity fase o actividad nueva en el workflow
     * @param result resultado de la actividad anterior
     * @param notification_id identificador de la notificacion de oracle
     * @param roles rol o roles asociados a la nueva actividad
     * @param entrada mensaje que causo el avance en el workflow
     * @param salida mensaje de respuesta del workflow
     */
    private void nuevaSolicitudConcurrente(String item_key, String activity, String result, String notification_id, String roles, Mensaje entrada, Mensaje salida) throws WFException {
    	
    }

    /**
            * Consulta la estacion siguiente para la instancia en curso
            * @return la estacion a la que se despachara la siguiente solicitud
            * concurrente que se construya
            */
    protected Estacion obtenerSiguienteEstacion(String item_key, String rol) throws WFException {
    	return null;
    }
}
