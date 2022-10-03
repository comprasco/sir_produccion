package gov.sir.hermod.workflow.impl.oracle;

import java.math.BigDecimal;

import javax.jdo.PersistenceManager;

import oracle.apps.fnd.wf.WFContext;
import oracle.apps.fnd.wf.WFTwoDArray;
import oracle.apps.fnd.wf.engine.WFEngineAPI;
import oracle.apps.fnd.wf.engine.WFNotificationAPI;
import org.apache.log4j.Logger;

import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.workflow.Message;
import gov.sir.hermod.workflow.Processor;
import gov.sir.hermod.workflow.WFException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Hashtable;

/**
 *
 * @author  mrios
 */

public class OracleProcessor implements Processor {
	
	private Connection connection;
	private PersistenceManager pm = null;
	private WFContext ctx;
	private int status = Processor.CREADO;
	private static Logger logHermod = Logger.getLogger(OracleProcessor.class);  
	
	/**
	* Constante definida para consultar el resultado de una actividad.
	*/
	private final String ACTIVITY_RESULT   = "ACTIVITY_RESULT";
    
    
    /**
    * Crea una instancia del OracleProcessor
    */
    public OracleProcessor() {
    	//logger.debug("OracleProcesor CREADO");
    }
    
    
    /**
     * Método que intenta obtener la conexión con el WFContext.
     * @throws <code>WFException</code>
     */
    public void start() throws WFException {
        String charSet = System.getProperty("CHARSET");
        
		//logger.debug("METODO START ORACLEPROCESSOR");        
        
        if ( charSet == null ) {
            charSet = "UTF8";
        }
        try {
        	pm = AdministradorConexiones.getInstancia().getPM();
			pm.currentTransaction().begin();
            connection = AdministradorConexiones.getInstancia().obtenerConexion(pm);
            
			//logger.debug("SE SUBE EL PERSISTENCE MANAGER");
            //connection.setAutoCommit(false);
            
            try {
                ctx = new WFContext(charSet);
                ctx.setJDBCConnection(connection);
                status = Processor.INICIADO;
            }
            catch (Exception e) {
                logHermod.error(e);
                logHermod.error(e.getMessage(), e);
                throw new WFException("No se pudo obtener WFContext", e);
            }
        }
        catch (SQLException e) {
			logHermod.error(e);
			logHermod.error(e.getMessage(), e);
 			if(pm.currentTransaction().isActive()){
 				pm.currentTransaction().rollback();
 			}
 			pm.close();
  
            throw new WFException("Conexion invalida", e);
        } catch (Throwable e) {
			logHermod.error(e);
			logHermod.error(e.getMessage(), e);
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
			throw new WFException("Conexion invalida", e);
		}
    }
    
    
	/**
	 * Método que intenta liberar la conexión.
	 * @throws <code>WFException</code>
	 */
    public void end() throws WFException {
        try {
			//logger.debug("METODO END ORACLEPROCESSOR");         	
            if ( connection != null ) {
                AdministradorConexiones.getInstancia().liberarConexion(pm);
            }
            status = Processor.TERMINADO;
        }
        catch (Exception e) {
            logHermod.error(e);
            logHermod.error(e.getMessage(), e);
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
    public Message process(Message m) throws WFException 
    {
        
        //Se intenta iniciar el procesor. 
        if ( status != Processor.INICIADO ) 
        {
            start();
    
            if ( status != Processor.INICIADO ) 
            {
                logHermod.debug("No se pudo iniciar el processor");
                throw new WFException("No se pudo iniciar el processor");
            }
        }
        
        //Se determina el tipo de actividad recibida en los parámetros. 
        String tipo = m.getTipoProceso();
		String idWorkflow =(String)m.getParametros().get(ITEM_KEY);
        try 
        {
			//logger.debug("(WF) Comienza llamado process WF: "+idWorkflow);
            //Crear un Workflow
			//logger.debug("@@@tipo: "+tipo);
            if ( tipo.equals(Message.INICIAR_PROCESO) ) 
            {
                return crearWorkflow(m);
            }
             
            //Avanzar un Workflow
            else if ( tipo.equals(Message.ACTUALIZAR_PROCESO) ) 
            {
                return avanzarWorkflow(m);
            }
            
            //Consultar un Workflow
            else if (tipo.equals(Message.CONSULTAR_PROCESO))
            {
            	return consultarWorkflow(m);
            }
            
			//Revivir un Turno
			else if (tipo.equals(Message.REVIVIR_PROCESO))
			{
				return revivirWorkflow(m);
			}    
            
            //          Revivir un Turno
			else if (tipo.equals(Message.HABILITAR_PROCESO))
			{
				return habilitarWorkflow(m);
			}   
			
            //Consultar un Workflow
            else if (tipo.equals(Message.ANULAR_PROCESO))
            {
            	return anularWorkflow(m);
            }
            
            //Se obtuvo un tipo de actividad inválido.
            else 
            {
                end();
                logHermod.debug("Debe indicar tipo de actividad");
                throw new WFException("Debe indicar tipo de actividad CREAR/AVANZAR/CONSULTAR/ANULAR/REVIVIR");
            }
        }
        catch (HermodException he) 
        {
            end();
            logHermod.error(he);
            logHermod.error(he.getMessage(), he);
            throw new WFException(he.getMessage(), he);
        }
        finally{
			//logger.debug("(WF) Termina llamado process WF: "+idWorkflow);
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
    private Message crearWorkflow(Message m) throws WFException {
        HermodProperties hp = HermodProperties.getInstancia();
        String iType, iKey, pr, owner;
        Message m_resultado = null;
        WFTwoDArray dataSource = null;
        
        iType  = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        owner  = hp.getProperty(OracleKeys.ORACLE_USER);
        iKey   = (String) m.getParametros().get(ITEM_KEY);
        pr     = (String) m.getParametros().get(PROCESS);
        
        try {
            if ( WFEngineAPI.createProcess(ctx, iType, iKey, pr) ) {
                logHermod.debug("crearWorkflow(): created item...");
                
                logHermod.debug("Item Attributes...");
                dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
                this.setAttributes(ctx, m, dataSource);
                    
                if ( WFEngineAPI.setItemOwner(ctx, iType, iKey, owner) ) {
                    logHermod.debug("crearWorkflow(): created owner...");
                        
                    if ( WFEngineAPI.startProcess(ctx, iType, iKey) ) {
                        logHermod.debug("crearWorkflow(): started process...");
                        m_resultado = new Message(1, Message.DEL_WORKFLOW, Message.INICIAR_PROCESO,this.getInfoProcess(iType, iKey, ctx));
                        pm.currentTransaction().commit();
                    }
                    else {
                        logHermod.debug("crearWorkflow(): error while starting the process...");
						if(pm.currentTransaction().isActive()){
							pm.currentTransaction().rollback();
						}
                        WFEngineAPI.showError(ctx);
                        
                    }
                }
                else {
                    logHermod.debug("crearWorkflow(): error while setting the owner...");
					if(pm.currentTransaction().isActive()){
						pm.currentTransaction().rollback();
					}
                    WFEngineAPI.showError(ctx);
                }
            }
            else {
                logHermod.debug("crearWorkflow(): error while creating the process...");
				if(pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
                WFEngineAPI.showError(ctx);
            }
        }
        catch (Exception e) {
            logHermod.error(e);
            logHermod.error(e.getMessage(), e);
            
            if(pm.currentTransaction().isActive()){
            	pm.currentTransaction().rollback();
            }
            
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
	 * @see gov.sir.hermod.workflow.Message
	 */
    private Message avanzarWorkflow(Message m) throws WFException 
    {
        
        HermodProperties hp = HermodProperties.getInstancia();
        String iType, iKey, nid, valor;
        WFTwoDArray dataSource = null;
        Message m_resultado = null;
        
        //Se obtienen los parámetros necesarios del mensaje. 
        iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        iKey  = (String) m.getParametros().get(ITEM_KEY);
        nid   = (String) m.getParametros().get(NOT_ID);
        valor = (String) m.getParametros().get(RESULT);
        
        try 
        {
            BigDecimal notid = new BigDecimal(nid);
            
            logHermod.debug("Item Attributes...");
            dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
            this.setAttributes(ctx, m, dataSource);
            
            if ( WFNotificationAPI.setAttrText(ctx, notid, "RESULT", valor) ) {
                if ( WFNotificationAPI.respond(ctx, notid, "AVANCE POR WFAPI", null) ) {
                    logHermod.debug("avanzarWorkflow(): item moved...");
                    
                    
                    m_resultado = new Message(2, Message.DEL_WORKFLOW,Message.ACTUALIZAR_PROCESO, this.getInfoProcess(iType, iKey, ctx));
                    pm.currentTransaction().commit();
                }else{
					if(pm.currentTransaction().isActive()){
						pm.currentTransaction().rollback();
					}
                }
            }else{
				if(pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
            }
        }
        catch (Exception e) {
            logHermod.error(e);
            logHermod.error(e.getMessage(), e);
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
            throw new WFException(e.getMessage(), e);
        }
        finally {
            AdministradorConexiones.getInstancia().liberarConexion(pm);    
        }
        
        return m_resultado;
    }
    
    /**
	 * Método utilizado para anular los procesos de Workflow.
	 * @param m Mensaje con los parámetros necesarios para anular un
	 * Workflow.
	 * @return Mensaje con la respuesta obtenida luego de anular un 
	 * Workflow.
	 * @throws <code>WFException</code>
	 * @see gov.sir.hermod.workflow.Message
	 */
    private Message anularWorkflow(Message m) throws WFException 
    {
        
        HermodProperties hp = HermodProperties.getInstancia();
        String iType, iKey, nid, valor;
        WFTwoDArray dataSource = null;
        Message m_resultado = null;
        
        //Se obtienen los parámetros necesarios del mensaje. 
        iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        iKey  = (String) m.getParametros().get(ITEM_KEY);
        valor = (String) m.getParametros().get(RESULT);
        
        try 
        {
            logHermod.debug("Item Attributes...");
            dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
            this.setAttributes(ctx, m, dataSource);
            
            //if (WFEngineAPI.abortProcess(ctx, iType, iKey, null, valor))
            if (WFEngineAPI.suspendProcess(ctx, iType, iKey, null))
            {
            	//logHermod.debug("anularWorkflow(): item aborted...");
            	logHermod.debug("anularWorkflow(): item suspended...");
                m_resultado = new Message(2, Message.DEL_WORKFLOW,Message.ANULAR_PROCESO, this.getInfoProcess(iType, iKey, ctx));
                pm.currentTransaction().commit();
            }
            else
            {
                if(pm.currentTransaction().isActive())
                {
    				pm.currentTransaction().rollback();
    			}
            }
        }
        catch (Exception e) {
            logHermod.error(e);
            logHermod.error(e.getMessage(), e);
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
            throw new WFException(e.getMessage(), e);
        }
        finally {
            AdministradorConexiones.getInstancia().liberarConexion(pm);    
        }
        
        return m_resultado;
    }
    
    /**
	 * Método utilizado para anular los procesos de Workflow.
	 * @param m Mensaje con los parámetros necesarios para anular un
	 * Workflow.
	 * @return Mensaje con la respuesta obtenida luego de anular un 
	 * Workflow.
	 * @throws <code>WFException</code>
	 * @see gov.sir.hermod.workflow.Message
	 */
    private Message habilitarWorkflow(Message m) throws WFException 
    {
        
        HermodProperties hp = HermodProperties.getInstancia();
        String iType, iKey, nid, valor;
        WFTwoDArray dataSource = null;
        Message m_resultado = null;
        
        //Se obtienen los parámetros necesarios del mensaje. 
        iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        iKey  = (String) m.getParametros().get(ITEM_KEY);
        valor = (String) m.getParametros().get(RESULT);
        
        try 
        {
            logHermod.debug("Item Attributes...");
            dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
            this.setAttributes(ctx, m, dataSource);
            
            //if (WFEngineAPI.abortProcess(ctx, iType, iKey, null, valor))
            if (WFEngineAPI.resumeProcess(ctx, iType, iKey, null))
            {
            	//logHermod.debug("anularWorkflow(): item aborted...");
            	logHermod.debug("habilitarWorkflow(): item resume...");
                m_resultado = new Message(2, Message.DEL_WORKFLOW,Message.HABILITAR_PROCESO, this.getInfoProcess(iType, iKey, ctx));
                pm.currentTransaction().commit();
            }
            else
            {
                if(pm.currentTransaction().isActive())
                {
    				pm.currentTransaction().rollback();
    			}
            }
        }
        catch (Exception e) {
            logHermod.error(e);
            logHermod.error(e.getMessage(), e);
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
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
    * @see gov.sir.hermod.workflow.Message
    */
    public Message consultarWorkflow(Message m)
    {
    	
		Message m_resultado = null;
    	try{
	    	Hashtable ht = m.getParametros();			
			
			//Se determina el tipo de Consulta que se desea ejecutar.
			String tipoConsulta = (String) ht.get("TIPO_CONSULTA");
			
			String lastIdNotification = null;
			try{
				lastIdNotification = (String) ht.get(Message.CONSULTAR_ULTIMO_ID_NOTIFICATION);
			}catch(Exception e){				
			}
							
			//Consultar el Resultado de una actividad dado el itemKey del proceso
			//y el tipo de mensaje generado por la actividad.
			if (tipoConsulta.equals(this.ACTIVITY_RESULT)){
				//Se obtienen los parámetros necesarios.
				String itemKey = (String) ht.get("ITEM_KEY");
				String mensaje = (String) ht.get("TIPO_RESPUESTA");
				
							
				//Se obtiene el notificationId de la actividad.
				String notId = null;
				if(lastIdNotification==null){
					notId = this.getActivityNotId(itemKey,mensaje);	
				}else{
					notId = this.getActivityLastNotId(itemKey,mensaje);
				}				
				
				//Se obtiene el result de la actividad.
				
				if (notId != null)
				{
					String resultado = this.getActivityResult(notId);
										
					//Se genera el mensaje de respuesta.
					
					Hashtable htRespuesta = new Hashtable(5);
					htRespuesta.put("NOT_ID", notId);
					htRespuesta.put("RESULTADO", resultado);
					htRespuesta.put("TIPO_CONSULTA", this.ACTIVITY_RESULT);
					htRespuesta.put("RESULTADO_CONSULTA", "EXITO");
									
					m_resultado = new Message(3, Message.DEL_WORKFLOW,Message.CONSULTAR_PROCESO,htRespuesta);
					return m_resultado;
				}
	            Hashtable htError = new Hashtable(5);
				 htError.put("RESULTADO_CONSULTA","FRACASO");
				 m_resultado = new Message(3, Message.DEL_WORKFLOW,Message.CONSULTAR_PROCESO,htError);
				 
				 return m_resultado;
			}
			
			Hashtable htError2 = new Hashtable();
			htError2.put("RESULTADO_CONSULTA","FRACASO");
			Message m_resultado2 = new Message(3, Message.DEL_WORKFLOW,Message.CONSULTAR_PROCESO,htError2);
			return m_resultado2;
		
    	}catch(Throwable t){
			t.printStackTrace();
			logHermod.error(t.getMessage(), t);
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
    	}
		finally {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().commit();
			}
			AdministradorConexiones.getInstancia().liberarConexion(pm);
		}   
		return m_resultado;
		
	}
	

	/**
	 * Método utilizado para revivir los procesos de Workflow.
	 * @param m Mensaje con los parámetros necesarios para revivir el proceso.
	 * @return Mensaje con la respuesta obtenida luego de revivir un Workflow.
	 * @throws <code>WFException</code>
	 * @see gov.sir.hermod.workflow.Message
	 */
	private Message revivirWorkflow(Message m) throws WFException 
	{
        
		HermodProperties hp = HermodProperties.getInstancia();
		String iType, iKey, activity , command, valor;
		WFTwoDArray dataSource = null;
		Message m_resultado = null;
        
		//Se obtienen los parámetros necesarios del mensaje. 
		iType = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
		iKey  = (String) m.getParametros().get(ITEM_KEY);
		activity = command = (String) m.getParametros().get(ACTIVITY);
		command = (String) m.getParametros().get(COMMAND);
		valor = (String) m.getParametros().get(RESULT);
        
		try 
		{           
			logHermod.debug("Item Attributes...");
			dataSource = WFEngineAPI.getItemAttributes(ctx, iType, iKey);
			this.setAttributes(ctx, m, dataSource);
            
			if (WFEngineAPI.handleError(ctx ,iType , iKey , activity , command, valor) ) {
				pm.currentTransaction().commit();
			}else{
				if(pm.currentTransaction().isActive()){
					pm.currentTransaction().rollback();
				}
			}
		}
		catch (Exception e) {
			logHermod.error(e);
			logHermod.error(e.getMessage(), e);
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			throw new WFException(e.getMessage(), e);
		}
		finally {
			AdministradorConexiones.getInstancia().liberarConexion(pm);    
		}
        
		return m_resultado;
	}	
		
		    		 
    /**
     * Ejecuta un procedimiento almacenado que recupera la información de las
     * actividades cuyo estado sea NOTIFIED.
     * @param iType
     * @param iKey
     * @param ctx
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
                logHermod.debug("No se obtuvo ningún resultado al realizar la ejecución ");
            }

            notif = call.getString(3);
            activ = call.getString(4);
            rol   = call.getString(5);

            if (notif!=null && activ!=null && rol!=null)
            {
                ht.put(ITEM_KEY, iKey);
                ht.put(NOT_ID, notif);
                ht.put(ACTIVITY, activ);
                ht.put(ROL, rol);
            }else
            {
                ht.put(ITEM_KEY, iKey);
                ht.put(NOT_ID, "");
                ht.put(ACTIVITY, "");
                ht.put(ROL, "");
            }
        }catch(Exception e){
           throw new Exception(e.getMessage());
        }finally{
           if(call != null){
              call.close();
            }
        }
 
        return ht;
    }
    
    private void setAttributes(WFContext ctxParam, Message m, WFTwoDArray dataSource) throws Exception {
        String iType, iKey, attr, tipoAttr, attrValue, attrValueAnt;
        BigDecimal num = null;
        HermodProperties hp = HermodProperties.getInstancia();

        iType  = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
        iKey   = (String) m.getParametros().get(ITEM_KEY);
        
        for ( int j = 0; j < dataSource.getRowCount(); j++ ) {
            attr = (String) dataSource.getItem(j);
            tipoAttr = (String) dataSource.getData(1, j);
            attrValueAnt = (String) dataSource.getData(2, j);
            
            if ( tipoAttr.equals(CHermod.VARCHAR2) ) 
            {
            	
            	attrValue = (String	) m.getParametros().get(attr);
                if ( attrValue  != null ) {
                    if ( attrValue.equals(CHermod.CADENA_VACIA) ) {
                        attrValue = "NA";
                    
                        if ( attrValueAnt == null ) {
                            if ( WFEngineAPI.setItemAttrText(ctxParam, iType, iKey, attr, attrValue) )
                                logHermod.debug("setAttributes(): item attribute "+attr+" = "+attrValue);
                        }
                    }
                    else {
                        if ( WFEngineAPI.setItemAttrText(ctxParam, iType, iKey, attr, attrValue) )
                            logHermod.debug("setAttributes(): item attribute "+attr+" = "+attrValue);
                    }
                }
                else {
                    if ( attrValueAnt == null ) {
                        if ( WFEngineAPI.setItemAttrText(ctxParam, iType, iKey, attr, "NA") )
                            logHermod.debug("setAttributes(): default item attribute "+attr+" = NA");
                    }
                }
            }
            else if ( tipoAttr.equals(CHermod.NUMBER) ) 
            {
            
				attrValue = (String) m.getParametros().get(attr);
                if ( attrValue != null ) {
                    try {
                        num = new BigDecimal(attrValue);
                        
                        if ( WFEngineAPI.setItemAttrNumber(ctxParam, iType, iKey, attr, num) )
                            logHermod.debug("setAttributes(): item attribute "+attr+" = "+attrValue);
                    }
                    catch (Exception e) {
                    logHermod.debug("Se presentó un error en Oracle Processor  "+ e.getMessage());
                    }
                }
                else {
                    if ( attrValueAnt == null ) {
                        if ( WFEngineAPI.setItemAttrNumber(ctxParam, iType, iKey, attr, new BigDecimal(0)) )
                            logHermod.debug("setAttributes(): default item attribute "+attr+" = 0");
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
    /*
    private void displayDataSource(WFContext wCtx, WFTwoDArray dataSource) {
        int c, column, r, row;
        Object data;

        if (dataSource == null)
            return;

        row = dataSource.getRowCount();
        column = dataSource.getColumnCount();

        for ( r = 0; r < row; r++ ) {
            for ( c = 0; c < column; c++ ) {
                if (c > 0)
                    System.out.print("\t");

                data = dataSource.getData(c, r);
                if (data != null)
                    System.out.print(data);
            }
            System.out.print("\n");
        }
    }
    */
     
    
    /**
    * Retorna el WorkFlowContext
    * @return El workFlowContext
    */
    private WFContext getWFContext ()
    {
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
    private String getActivityNotId (String itemKey, String message)
    {
    	
		HermodProperties hp = HermodProperties.getInstancia();
		String iType  = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
		//DataSource que contiene todos los NotificationId de las actividades del
		//proceso con el itemKey dado. 
		WFTwoDArray dataSource = WFNotificationAPI.getNotifications(this.getWFContext(),iType,itemKey);
		
		//Se crea un nuevo dataSource con la información de las notificaciones
		//que se encuentran en el dataSource anterior.
		WFTwoDArray dataSource2;
		BigDecimal notId;
		String result=null;
				
		
		boolean found = false;
		int tamIteracion = dataSource.getRowCount();
		for (int i=0; i<tamIteracion && !found; i++)
		{
			notId = new BigDecimal ((String)dataSource.getData(0,i));
			
			dataSource2 = WFNotificationAPI.getInfo(ctx,notId);
			//Se obtiene el tipo de mensaje. 
			result = (String)dataSource2.getData(2,0);
			
			//Se compara con el tipo de mensaje recibido como parámetro.
			if (result.equals(message))
			{
				
				found = true;
				
				return notId.toString();
			}
		}
		
	    return null;
   	
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
	private String getActivityLastNotId (String itemKey, String message)
	{
    	
		HermodProperties hp = HermodProperties.getInstancia();
		String iType  = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
		//DataSource que contiene todos los NotificationId de las actividades del
		//proceso con el itemKey dado. 
		WFTwoDArray dataSource = WFNotificationAPI.getNotifications(this.getWFContext(),iType,itemKey);
		
		//Se crea un nuevo dataSource con la información de las notificaciones
		//que se encuentran en el dataSource anterior.
		WFTwoDArray dataSource2;
		BigDecimal notId;
		String result=null;
				
		String rta = null;
		
		boolean found = false;
		int tamIteracion = dataSource.getRowCount();
		for (int i=0; i<tamIteracion && !found; i++)
		{
			notId = new BigDecimal ((String)dataSource.getData(0,i));
			
			dataSource2 = WFNotificationAPI.getInfo(ctx,notId);
			//Se obtiene el tipo de mensaje. 
			result = (String)dataSource2.getData(2,0);
			
			//Se compara con el tipo de mensaje recibido como parámetro.
			if (result.equals(message))
			{				
				rta = notId.toString();
			}
		}
		
		return rta;
   	
	}    
    
	    /**
		* Retorna el Resultado de una actividad, dado el ItemKey del proceso
		* y el notificationId de una actividad.
		* @param notId el notificationId de la actividad.
		* @return El Result de la actividad con el notificationId
		* recibido como parámetro. 
		*/
		private String getActivityResult (String notId)
		{
    	
			HermodProperties hp = HermodProperties.getInstancia();
			//String iType  = hp.getProperty(OracleKeys.ORACLE_WORKFLOW);
		    BigDecimal actId = new BigDecimal (notId);
		    
			//DataSource 
			WFTwoDArray dataSource = WFNotificationAPI.getNotificationAttributes(this.getWFContext(),actId);
			
			return (String) dataSource.getData (1,0);
   	
		}
    
}