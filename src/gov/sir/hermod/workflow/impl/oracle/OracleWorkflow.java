/*
 * Created on 18-mar-2005
 *
 */
package gov.sir.hermod.workflow.impl.oracle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;

import javax.jdo.PersistenceManager;

import oracle.apps.fnd.wf.WFContext;
import oracle.apps.fnd.wf.WFTwoDArray;
import oracle.apps.fnd.wf.engine.WFEngineAPI;
import oracle.apps.fnd.wf.engine.WFNotificationAPI;

import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.workflow.AbstractWorkflow;
import gov.sir.hermod.workflow.Mensaje;
import gov.sir.hermod.workflow.WFException;
import gov.sir.hermod.workflow.Workflow;

/**
 * Esta clase es la implementacion para oracle del concepto de workflow
 * en hermod.
 * 
 * El workflow debe segun su contrato poder procesar mensajes
 * En el mensaje se envia el identificador del proceso y de la instancia
 * con la que se quiere trabajar asi como el tipo de avance.
 * 
 * @see AbstractWorkflow
 * @author Diego Cantor
 *
 */
public class OracleWorkflow extends AbstractWorkflow implements Workflow {

    private Connection connection;
	private PersistenceManager pm = null;    
    private WFContext ctx;
    private int status = Workflow.CREADO;
    
    /**
    * Constante definida para consultar el resultado de una actividad.
    */
    private final String ACTIVITY_RESULT = "ACTIVITY_RESULT";

    /**
    * Crea una instancia del OracleProcessor
    */
    public OracleWorkflow() {
    	 
    }

    /**
     * Método que intenta obtener la conexión con el WFContext.
     * @throws <code>WFException</code>
     */
    public void start() throws WFException {
        String charSet = System.getProperty("CHARSET");

        if (charSet == null) {
            charSet = "UTF8";
        }
		PersistenceManager pm = null;
        try {
			pm = AdministradorConexiones.getInstancia().getPM();
            connection = AdministradorConexiones.getInstancia().obtenerConexion(pm);
            //connection.setAutoCommit(false);

            try {
                ctx = new WFContext(charSet);
                ctx.setJDBCConnection(connection);
                status = Workflow.INICIADO;
            }
            catch (Exception e) {
                Log.getInstance().error(this.getClass(),e);
                Log.getInstance().error(this.getClass(),e.getMessage(), e);
                throw new WFException("No se pudo obtener WFContext", e);
            }
        }
        catch (SQLException e) {
            Log.getInstance().error(this.getClass(),e);
            Log.getInstance().error(this.getClass(),e.getMessage(), e);
            throw new WFException("Conexion invalida", e);
        } catch (Throwable e) {
			Log.getInstance().error(this.getClass(),e);
			 Log.getInstance().error(this.getClass(),e.getMessage(), e);
			 throw new WFException("Conexion invalida", e);
		}
    }

    /**
     * Método que intenta liberar la conexión.
     * @throws <code>WFException</code>
     */
    public void end() throws WFException {
        try {
            if (connection != null) {
                AdministradorConexiones.getInstancia().liberarConexion(pm);
            }
            status = Workflow.TERMINADO;
        }
        catch (Exception e) {
            Log.getInstance().error(this.getClass(),e);
            Log.getInstance().error(this.getClass(),e.getMessage(), e);
            throw new WFException("Errores retornando la conexion al pool", e);
        }
    }

    /**
     * Método que devuelve el estado de la conexión. 
     */
    final public int getStatus() {
        return status;
    }

    /**
     * Determina el tipo de actividad (Creación, Avance o Consulta)
     * que debe realizarse sobre el workflow, e invoca el método necesario
     * para ejecutarla.
     * @param m Mensaje con los parámetros necesarios para determinar
     * la actividad y ejecutarla.
     * @return Un mensaje con la respuesta obtenida desde el workflow.
     * @see gov.sir.hermod.workflow.Message
     * @throws <code>WFException</code>
     */
    public Mensaje procesarMensaje(Mensaje m) throws WFException {

        if (status != Workflow.INICIADO) {
            start();
            if (status != Workflow.INICIADO) {
                Log.getInstance().debug(OracleWorkflow.class,"No se pudo iniciar el Workflow");
                throw new WFException("No se pudo iniciar el Workflow");
            }
        }

        String accion = m.getAccion();

        try {
            if (accion.equals(Mensaje.INICIAR)) {
                return crearWorkflow(m);
            }
            else if (accion.equals(Mensaje.AVANZAR)) {
                return avanzarWorkflow(m);
            }
            else if (accion.equals(Mensaje.CONSULTAR)) {
                return consultarWorkflow(m);
            }
            else {
                end();
                Log.getInstance().debug(OracleWorkflow.class,"Debe indicar tipo de actividad");
                throw new WFException("Debe indicar tipo de actividad CREAR/AVANZAR/CONSULTAR");
            }
        }
        catch (HermodException he) {
            end();
            Log.getInstance().error(this.getClass(),he);
            Log.getInstance().error(this.getClass(),he.getMessage(), he);
            throw new WFException(he.getMessage(), he);
        }
    }

    /**
     * Método utilizado para la creación de procesos de Workflow.
     * @param m Mensaje con los parámetros necesarios para la creación
     * de un Workflow.
     * @return Mensaje con la respuesta obtenida luego de la creación de 
     * un Workflow.
     * @throws <code>WFException</code>
     * @see gov.sir.hermod.workflow.Message
     */
    private Mensaje crearWorkflow(Mensaje m) throws WFException {
        HermodProperties hp = HermodProperties.getInstancia();
        String iType, iKey, pr, owner;
        Mensaje m_resultado = null;
        WFTwoDArray dataSource = null;

        iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        owner = hp.getProperty(OracleKeys.ORACLE_USER);
        iKey = (String)m.getParametros().get(OracleKeys.ITEM_KEY);
        pr = (String)m.getParametros().get(OracleKeys.PROCESS);
        try {
            if (WFEngineAPI.createProcess(ctx, iType, iKey, pr)) {
                Log.getInstance().debug(OracleWorkflow.class,"crearWorkflow(): created item...");

                Log.getInstance().debug(OracleWorkflow.class,"Item Attributes...");
                dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
                this.setAttributes(ctx, m, dataSource);

                if (WFEngineAPI.setItemOwner(ctx, iType, iKey, owner)) {
                    Log.getInstance().debug(OracleWorkflow.class,"crearWorkflow(): created owner...");

                    if (WFEngineAPI.startProcess(ctx, iType, iKey)) {
                        Log.getInstance().debug(OracleWorkflow.class,"crearWorkflow(): started process...");
                        m_resultado = new Mensaje(1, pr, iKey, Mensaje.SALIDA, Mensaje.INICIAR, this.getInfoProcess(iType, iKey, ctx));
                        ctx.getDB().getConnection().commit();
                    }
                    else {
                        Log.getInstance().debug(OracleWorkflow.class,"crearWorkflow(): error while starting the process...");
                        WFEngineAPI.showError(ctx);
                    }
                }
                else {
                    Log.getInstance().debug(OracleWorkflow.class,"crearWorkflow(): error while setting the owner...");
                    WFEngineAPI.showError(ctx);
                }
            }
            else {
                Log.getInstance().debug(OracleWorkflow.class,"crearWorkflow(): error while creating the process...");
                WFEngineAPI.showError(ctx);
            }
        }
        catch (Exception e) {
            Log.getInstance().error(this.getClass(),e);
            Log.getInstance().error(this.getClass(),e.getMessage(), e);
            throw new WFException(e.getMessage(), e);
        }
        finally {
            AdministradorConexiones.getInstancia().liberarConexion(pm);
        }

        return m_resultado;
    }

    /**
     * Método utilizado para avanzar sobre los procesos de Workflow.
     * @param m Mensaje con los parámetros necesarios para avanzar sobre un
     * Workflow.
     * @return Mensaje con la respuesta obtenida luego de avanzar sobre un 
     * Workflow.
     * @throws <code>WFException</code>
     * @see gov.sir.hermod.workflow.Mensaje
     */
    private Mensaje avanzarWorkflow(Mensaje m) throws WFException {

        HermodProperties hp = HermodProperties.getInstancia();
        String iType, iKey, nid, valor, pr;
        WFTwoDArray dataSource = null;
        Mensaje m_resultado = null;

        //Se obtienen los parámetros necesarios del mensaje. 
        iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        iKey = (String)m.getParametros().get(OracleKeys.ITEM_KEY);
        nid = (String)m.getParametros().get(OracleKeys.NOTIFICATION_ID);
        valor = (String)m.getParametros().get(OracleKeys.RESULT);
        pr = (String)m.getParametros().get(OracleKeys.PROCESS);

        try {
            BigDecimal notid = new BigDecimal(nid);

            Log.getInstance().debug(OracleWorkflow.class,"Item Attributes...");
            dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
            this.setAttributes(ctx, m, dataSource);

            if (WFNotificationAPI.setAttrText(ctx, notid, "RESULT", valor)) {
                if (WFNotificationAPI.respond(ctx, notid, "AVANCE POR WFAPI", null)) {
                    Log.getInstance().debug(OracleWorkflow.class,"avanzarWorkflow(): item moved...");

                    m_resultado = new Mensaje(2, pr, iKey, Mensaje.SALIDA, Mensaje.AVANZAR, this.getInfoProcess(iType, iKey, ctx));
                    ctx.getDB().getConnection().commit();
                }
            }
        }
        catch (Exception e) {
            Log.getInstance().error(this.getClass(),e);
            Log.getInstance().error(this.getClass(),e.getMessage(), e);
            throw new WFException(e.getMessage(), e);
        }
        finally {
            AdministradorConexiones.getInstancia().liberarConexion(pm);
        }

        return m_resultado;
    }

    /**
    * Método que se encarga de realizar consultas sobre los estados del Workflow,
    * basado en los parámetros recibidos.
    * @param m Mensaje que contiene toda la información para realizar la consulta.
    * @return Un mensaje con el resultado de la consulta.
    * @throws <code>WFException</code>
    * @see gov.sir.hermod.workflow.Mensaje
    */
    public Mensaje consultarWorkflow(Mensaje m) {
        Hashtable ht = m.getParametros();
        Mensaje m_resultado = null;

        String pr = (String)m.getParametros().get(OracleKeys.PROCESS);
        String itemKey = (String)m.getParametros().get(OracleKeys.ITEM_KEY);
        String tipoConsulta = (String)ht.get("TIPO_CONSULTA");

        //Consultar el Resultado de una actividad dado el itemKey del proceso
        //y el tipo de mensaje generado por la actividad.
        if (tipoConsulta.equals(this.ACTIVITY_RESULT)) {

            String mensaje = (String)ht.get("TIPO_RESPUESTA");
            String notId = this.getActivityNotId(itemKey, mensaje);
            if (notId != null) {
                String resultado = this.getActivityResult(notId);
                Hashtable htRespuesta = new Hashtable(5);
                htRespuesta.put(OracleKeys.NOTIFICATION_ID, notId);
                htRespuesta.put("RESULTADO", resultado);
                htRespuesta.put(OracleKeys.TIPO_CONSULTA, this.ACTIVITY_RESULT);
                htRespuesta.put(OracleKeys.RESULT, OracleKeys.CONSULTA_OK);
                m_resultado = new Mensaje(3, pr, itemKey, Mensaje.SALIDA, Mensaje.CONSULTAR, htRespuesta);
                return m_resultado;
            }
            //Mensaje de error
            Hashtable htError = new Hashtable(5);
            htError.put(OracleKeys.RESULT, OracleKeys.CONSULTA_ERROR);
            m_resultado = new Mensaje(3, pr, itemKey, Mensaje.SALIDA, Mensaje.CONSULTAR, htError);
            return m_resultado;
        }

        Hashtable htError2 = new Hashtable(5);
        htError2.put(OracleKeys.RESULT, OracleKeys.CONSULTA_ERROR);
        Mensaje m_resultado2 = new Mensaje(3, pr, itemKey, Mensaje.SALIDA, Mensaje.CONSULTAR, htError2);
        return m_resultado2;
    }

    /**
     * Ejecuta un procedimiento almacenado que recupera la información de las
     * actividades cuyo estado sea NOTIFIED.
     * @param iType
     * @param iKey
     * @param ctx1
     * @return Hashtable con la información de todas las actividades cuyo estado sea
     * NOTIFIED.
     * @throws <code>Exception</code>
     */
    private Hashtable getInfoProcess(String iType, String iKey, WFContext ctx1) throws Exception {

        CallableStatement call = null;
        Hashtable ht = new Hashtable(5);

        String notif       = null;
        String activ       = null;
        String rol         = null;
        String GET_PROCESS = null;

        boolean resultadoEjecucion = true;
        
        try{

            GET_PROCESS = "begin SIR_UTILIDADES.UltimaNotificacion(?,?,?,?,?); end;";
            
            call = ctx1.getDB().getConnection().prepareCall(GET_PROCESS);
            call.setString(1, iType);
            call.setString(2, iKey);
            call.registerOutParameter(3, Types.VARCHAR);
            call.registerOutParameter(4, Types.VARCHAR);
            call.registerOutParameter(5, Types.VARCHAR);

            resultadoEjecucion = call.execute();

            if (resultadoEjecucion == false)
            {
                Log.getInstance().debug(OracleWorkflow.class,"No se obtuvo ningún resultado al realizar la ejecución ");
            }
            notif = call.getString(3);
            activ = call.getString(4);
            rol   = call.getString(5);

            if (notif != null && activ != null && rol != null) {
                ht.put(OracleKeys.ITEM_KEY, iKey);
                ht.put(OracleKeys.NOTIFICATION_ID, notif);
                ht.put(OracleKeys.ACTIVITY, activ);
                ht.put(OracleKeys.ROL, rol);
            }else {
                ht.put(OracleKeys.ITEM_KEY, iKey);
                ht.put(OracleKeys.NOTIFICATION_ID, "");
                ht.put(OracleKeys.ACTIVITY, "");
                ht.put(OracleKeys.ROL, "");
            }
        }catch(Exception e){
            throw new Exception(e);
        }finally{
            if(call != null){
                call.close();
            }
        }
        return ht;
    }

    private void setAttributes(WFContext ctxParam, Mensaje m, WFTwoDArray dataSource) throws Exception {
        String iType, iKey, attr, tipoAttr, attrValue, attrValueAnt;
        BigDecimal num = null;
        HermodProperties hp = HermodProperties.getInstancia();

        iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        iKey = (String)m.getParametros().get(OracleKeys.ITEM_KEY);

        
        int tamIteracion = dataSource.getRowCount();
        for (int j = 0; j < tamIteracion; j++) {
            attr = (String)dataSource.getItem(j);
            tipoAttr = (String)dataSource.getData(1, j);
            attrValueAnt = (String)dataSource.getData(2, j);

            if (tipoAttr.equals(CHermod.VARCHAR2)) 
            {
            
				attrValue = (String)m.getParametros().get(attr);
                if (attrValue  != null) {
                    if (attrValue.equals(CHermod.CADENA_VACIA)) {
                        attrValue = "NA";

                        if (attrValueAnt == null) {
                            if (WFEngineAPI.setItemAttrText(ctxParam, iType, iKey, attr, attrValue))
                                Log.getInstance().debug(OracleWorkflow.class,"setAttributes(): item attribute " + attr + " = " + attrValue);
                        }
                    }
                    else {
                        if (WFEngineAPI.setItemAttrText(ctxParam, iType, iKey, attr, attrValue))
                            Log.getInstance().debug(OracleWorkflow.class,"setAttributes(): item attribute " + attr + " = " + attrValue);
                    }
                }
                else {
                    if (attrValueAnt == null) {
                        if (WFEngineAPI.setItemAttrText(ctxParam, iType, iKey, attr, "NA"))
                            Log.getInstance().debug(OracleWorkflow.class,"setAttributes(): default item attribute " + attr + " = NA");
                    }
                }
            }
            else if (tipoAttr.equals(CHermod.NUMBER)) 
            {
            	
				attrValue = (String)m.getParametros().get(attr);
                if (attrValue != null) {
                    try {
                        num = new BigDecimal(attrValue);

                        if (WFEngineAPI.setItemAttrNumber(ctxParam, iType, iKey, attr, num))
                            Log.getInstance().debug(OracleWorkflow.class,"setAttributes(): item attribute " + attr + " = " + attrValue);
                    }
                    catch (Exception e) {
						Log.getInstance().debug(OracleWorkflow.class,"Se presentó un error en Oracle Workflow  "+ e.getMessage());
                    }
                }
                else {
                    if (attrValueAnt == null) {
                        if (WFEngineAPI.setItemAttrNumber(ctxParam, iType, iKey, attr, new BigDecimal(0)))
                            Log.getInstance().debug(OracleWorkflow.class,"setAttributes(): default item attribute " + attr + " = 0");
                    }
                }
            }
        }
    }

    /**
    * Método utilizado para imprimir toda la información contenida en un 
    * DataSource.
    * @param wCtx El WorkFlow context
    * @param dataSource El DataSource que contiene la información que se 
    * va a imprimir. 
    */
    private void displayDataSource(WFContext wCtx, WFTwoDArray dataSource) {
        int c, column, r, row;
        Object data;

        if (dataSource == null)
            return;

        row = dataSource.getRowCount();
        column = dataSource.getColumnCount();

        for (r = 0; r < row; r++) {
            for (c = 0; c < column; c++) {
                if (c > 0)
                    System.out.print("\t");

                data = dataSource.getData(c, r);
                if (data != null)
                    System.out.print(data);
            }
            System.out.print("\n");
        }
    }

    /**
    * Retorna el WorkFlowContext
    * @return El workFlowContext
    */
    private WFContext getWFContext() {
        return this.ctx;
    }

    /**
    * Retorna el NotificationId de una actividad, dado el ItemKey del proceso
    * y el tipo de Mensaje generado por la actividad.
    * @param itemKey el ItemKey del proceso.
    * @param message el tipo de mensaje de la actividad.
    * @return El NotificationId de la actividad que genera un mensaje del tipo
    * recibido y que tiene como itemKey el valor recibido como parámetro. 
    * @author dlopez
    */
    private String getActivityNotId(String itemKey, String message) {

        HermodProperties hp = HermodProperties.getInstancia();
        String iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        //DataSource que contiene todos los NotificationId de las actividades del
        //proceso con el itemKey dado. 
        WFTwoDArray dataSource = WFNotificationAPI.getNotifications(this.getWFContext(), iType, itemKey);

        //Se crea un nuevo dataSource con la información de las notificaciones
        //que se encuentran en el dataSource anterior.
        WFTwoDArray dataSource2;
        BigDecimal notId;
        String result = null;

        
        boolean found = false;
        int tamIteracion = dataSource.getRowCount();
        for (int i = 0; i < tamIteracion && !found; i++) {
            notId = new BigDecimal((String)dataSource.getData(0, i));

            dataSource2 = WFNotificationAPI.getInfo(ctx, notId);
            //Se obtiene el tipo de mensaje. 
            result = (String)dataSource2.getData(2, 0);

            //Se compara con el tipo de mensaje recibido como parámetro.
            if (result.equals(message)) {

                found = true;

                return notId.toString();
            }
        }

        return null;

    }

    /**
    * Retorna el Resultado de una actividad, dado el ItemKey del proceso
    * y el notificationId de una actividad.
    * @param notId el notificationId de la actividad.
    * @return El Result de la actividad con el notificationId
    * recibido como parámetro. 
    */
    private String getActivityResult(String notId) {

        HermodProperties hp = HermodProperties.getInstancia();
        //String iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        BigDecimal actId = new BigDecimal(notId);

        //DataSource 
        WFTwoDArray dataSource = WFNotificationAPI.getNotificationAttributes(this.getWFContext(), actId);

        return (String)dataSource.getData(1, 0);

    }
}