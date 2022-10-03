package gov.sir.hermod.dao.impl.jdogenie;


import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoEjecucion;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.TurnosNuevosDAO;
import gov.sir.hermod.workflow.HermodWFFactory;
import gov.sir.hermod.workflow.Message;
import gov.sir.hermod.workflow.Processor;
import gov.sir.hermod.workflow.WFException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.EstacionDAO;
import org.auriga.core.modelo.daoObjects.UsuarioDAO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Jerarquia;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.acciones.excepciones.ConsultaCalificacionFolioException;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.hermod.gdocumental.integracion.SGD;

import gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced;
import org.auriga.core.modelo.daoObjects.RolDAO;
/**
 * @author dlopez
 * Clase para el nuevo manejo de creación y avance de turnos a través 
 * del aplicativo.
 * <p>
 * Provee servicios para la consulta, modificación y adición de los objetos 
 * que intervienen en este proeceso. 
 */
public class JDOTurnosNuevosDAO implements TurnosNuevosDAO{
	
	/* Jerarquía de Círculo */
	private static final String JERARQUIA_CIRCULOS = "JER_CIRCULOS";
	
	/* Servicio SAS */
	private static final String SERVICIO_SAS = "org.auriga.sas.SAS";
	
	/*Servicio FENRIR */
	private static final String SERVICIO_FENRIR ="gov.sir.fenrir";
	
	/** 
	* Crea una nueva instancia de <code>JDOTurnosDAO</code>
	* <p>
	* Método Constructor.  
	*/
	public JDOTurnosNuevosDAO() {
	}
	

	
	
	/**
	 * Crea un <code>Turno de reparto notarial en el sistema,
	 * y crea una instacia de Workflow de acuerdo con el <code>Proceso</code>
	 * determinado.
	 * @param estacion Estación asociada con la fase del proceso.
	 * @param user Usuario Usuario iniciador del proceso.
	 * @return Turno Generado 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Turno
	 * @see gov.sir.core.negocio.modelo.Proceso
	 */
	public Turno crearTurnoRepartoNotarial(SolicitudRepartoNotarial solicitud, String estacion,Usuario user) throws DAOException {
		
		PersistenceManager pm = AdministradorPM.getPM();
		TurnoEnhancedPk tId = new TurnoEnhancedPk();
		TurnoEnhanced t = null;
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();

		try {
			
			//Inicio de la transacción.
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			if (solicitud != null) {

				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"»»» «JDOTURNOSNUEVOSDAO»«crearTurnoRepartoNotarial» user: ««« " + user);
			
				tId = this.crearTurnoRepartoModeloOperativo(solicitud, estacion, user, pm);
				
				pm.currentTransaction().commit();
				
				//Obtener el turno.
				t = turnosDAO.getTurnoByID(tId, pm);
				
				if(t==null){
					throw new DAOException("El turno creado no pudo ser recuperado");
				}

				turnosDAO.makeTransientTurno(t, pm);
			} else {
				throw new DAOException("No existe una solicitud para el turno que se va a crear");
			}
			

		} catch (JDOException e) {
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,"»»»»»»««««««««««««««");
			Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,"»»»»»»»«««««««««««»»");
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().commit();
			}
			pm.close();
		}
		return (Turno) t.toTransferObject();
	}	
	   

	
	
	
	/**
	 * Crea un <code>Turno</code> en el modelo operativo.  Llama al método crearTurnoReparto
	 * que se encarga de la persistencia.
	 * <p>
	 * Método utilizado para transacciones
	 * @param pm <code>PersistenceManager</code> de la transaccion.
	 * @param estacion Identificador de la estación asociada con la creación del <code>Turno</code>
	 * @param user Usuario iniciador del proceso
	 * @return un identificador del <code>Turno</code> generado.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Pago
	 * @see gov.sir.core.negocio.modelo.PagoEnhanced
	 * @see gov.sir.core.negocio.modelo.Turno
	 */
	protected TurnoEnhancedPk crearTurnoRepartoModeloOperativo(SolicitudRepartoNotarial solicitud, String estacion, Usuario user, PersistenceManager pm)
		throws DAOException {

		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		TurnoEnhancedPk tId = new TurnoEnhancedPk();

		try {


			CirculoProcesoEnhanced cpe = this.getCirculoProcesoBySolicitud(solicitud, pm);

					
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(user.getUsername(), pm);
			if (usuarior == null) {
				throw new DAOException(
					"No encontró el Usuario con el login: " + user.getUsername());
			}
			


			tId = this.crearTurnoReparto(solicitud, cpe, usuarior, pm);

			if (tId == null) {
				throw new DAOException("No se pudo crear el turno");
			}

		} catch (JDOObjectNotFoundException e) {
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
			Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException de) {
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,de);
			throw new DAOException(de.getMessage(), de);
		}

		return tId;
	}
	
	
	
	 /**
     * Hace persistente el turno recibido como parámetro.
     * <p>
     * Método privado llamado por este DAO.
     * @return el identificador del objeto Turno creado.
     * @param cpe Círculo Proceso asociado con el Turno que va a ser creado.
     * @param user <code>Usuario</code> iniciador del <code>Proceso</code>
     * @param pm PersistenceManager de la Transacción.
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    protected TurnoEnhancedPk crearTurnoReparto(SolicitudRepartoNotarial solicitud, CirculoProcesoEnhanced cpe,
        UsuarioEnhanced user,
        PersistenceManager pm) throws DAOException {
        
        TurnoEnhanced tr = new TurnoEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {
            
            //Obtener la solicitud persistente que se va a asociar con el turno.
            SolicitudEnhancedPk sId = new SolicitudEnhancedPk();
            sId.idSolicitud=solicitud.getIdSolicitud();

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(sId, pm);

            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA +
                    sId.idSolicitud);
            }

            //El Círculo Proceso se obtiene de uno de los parámetros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos =
            	fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(),"","","");
            
            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creación del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setAnio(cp.getAnio());
            tr.setFechaInicio(fecha);
            tr.setCirculoproceso(cp);
            tr.setSolicitud(s);
            //Como se va a crear un turno historia se llena esta variable con el valor 1.
            tr.setLastIdHistoria(1);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
            tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-" +
                String.valueOf(cp.getIdProceso()) + "-" +
                String.valueOf(cp.getLastIdTurno() + 1));
            tr.setUsuarioDestino(user);
            tr.setAnulado( CTurno.CAMPO_ANULADO_DEFECTO );
            
            cp.setLastIdTurno(cp.getLastIdTurno() + 1);

            
            //Insertar el turno historia correspondiente a la creación del turno.
			TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
				
			//Se inicializa este atributo en false para la creación porque en el paso 
			//siguiente, (Creación del siguiente turno historia se dejará activo).
			th.setActivo(false);
			th.setAnio(tr.getAnio());
			th.setFase("SOLICITUD");
			th.setFecha(new Date());
			th.setRespuesta("CREACION DEL TURNO");
			th.setIdCirculo(tr.getIdCirculo());
			th.setIdProceso(tr.getIdProceso());
		    th.setIdTurno(tr.getIdTurno());
		    th.setAnio(tr.getAnio());
		    th.setUsuario(user);
		    th.setUsuarioAtiende(user);
		    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
		    th.setIdTurnoHistoria("1");
			
			tr.addHistorial(th);
            pm.makePersistent(tr);
            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;
        } catch (JDOException e) {
            Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
        	Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    /**
     * Hace persistente el turno recibido como parámetro.
     * <p>
     * Método privado llamado por este DAO.
     * @return el identificador del objeto Turno creado.
     * @param cpe Círculo Proceso asociado con el Turno que va a ser creado.
     * @param user <code>Usuario</code> iniciador del <code>Proceso</code>
     * @param pm PersistenceManager de la Transacción.
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    protected TurnoEnhancedPk crearTurnoReparto(SolicitudRepartoNotarialEnhanced solicitud, CirculoProcesoEnhanced cpe,
        UsuarioEnhanced user,
        PersistenceManager pm) throws DAOException {
        
        TurnoEnhanced tr = new TurnoEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {
            
            //Obtener la solicitud persistente que se va a asociar con el turno.
            SolicitudEnhancedPk sId = new SolicitudEnhancedPk();
            sId.idSolicitud=solicitud.getIdSolicitud();

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(sId, pm);

            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA +
                    sId.idSolicitud);
            }

            //El Círculo Proceso se obtiene de uno de los parámetros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos =
            	fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(),"","","");
            
            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creación del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setAnio(cp.getAnio());
            tr.setFechaInicio(fecha);
            tr.setCirculoproceso(cp);
            tr.setSolicitud(s);
            //Como se va a crear un turno historia se llena esta variable con el valor 1.
            tr.setLastIdHistoria(1);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
            tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-" +
                String.valueOf(cp.getIdProceso()) + "-" +
                String.valueOf(cp.getLastIdTurno() + 1));
            tr.setUsuarioDestino(user);
            tr.setAnulado( CTurno.CAMPO_ANULADO_DEFECTO );
            
            cp.setLastIdTurno(cp.getLastIdTurno() + 1);

            
            //Insertar el turno historia correspondiente a la creación del turno.
			TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
				
			//Se inicializa este atributo en false para la creación porque en el paso 
			//siguiente, (Creación del siguiente turno historia se dejará activo).
			th.setActivo(false);
			th.setAnio(tr.getAnio());
			th.setFase("SOLICITUD");
			th.setFecha(new Date());
			th.setRespuesta("CREACION DEL TURNO");
			th.setIdCirculo(tr.getIdCirculo());
			th.setIdProceso(tr.getIdProceso());
		    th.setIdTurno(tr.getIdTurno());
		    th.setAnio(tr.getAnio());
		    th.setUsuario(user);
		    th.setUsuarioAtiende(user);
		    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
		    th.setIdTurnoHistoria("1");
			
			tr.addHistorial(th);
            pm.makePersistent(tr);
            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;
        } catch (JDOException e) {
            Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
        	Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    
    
	/**
	 * Obtiene el <code>CirculoProceso</code> asociado con una <code>Solicitud</code>,
	 * este método se utiliza para obtener el secuencial que va a ser utilizado como
	 * identificador del turno que va a ser creado. 
	 * <p>
	 * Método utilizado para transacciones.
	 * @param pm PersistenceManager de la transaccion
	 * @return <code>CirculoProceso</code> con sus atributos
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Pago
	 * @see gov.sir.core.negocio.modelo.PagoEnhanced
	 * @see gov.sir.core.negocio.modelo.CirculoProceso
	 * @see gov.sir.core.negocio.modelo.CirculoProcesoEnhanced
	 */
	protected CirculoProcesoEnhanced getCirculoProcesoBySolicitud(SolicitudRepartoNotarial solicitud, PersistenceManager pm)
		throws DAOException {


		//Se hace el cambio de tipo de bloqueo pesimista para
		//que se bloquee la tabla la cual  nos
		//provee el secuencial
                VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
                CirculoProcesoEnhanced circuloprocesoenhanced = null;
                Collection col = new ArrayList<CirculoProcesoEnhanced>();
                try{                                 
                    
                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
                    pm = pm2;

                    //Date fecha = new Date();
                    Calendar calendario = Calendar.getInstance();
                    String year = String.valueOf(calendario.get(Calendar.YEAR));

                    String idSolicitud = solicitud.getIdSolicitud();
                   
                    Query query = pm.newQuery(CirculoProcesoEnhanced.class);
                    query.declareVariables("SolicitudEnhanced sol");

                    query.declareParameters("String idSol, String year");

                    query.setFilter(
                            "anio==year && sol.circulo == circulo && sol.proceso == proceso && \n"
                                    + "sol.idSolicitud==idSol");

                    col = (Collection) query.execute(idSolicitud, year);

                    //Volvemos a setear el tipo de bloqueo pesimista
                    //para que no nos bloquee los siquientes registros
                    //consultados
                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

                    if (circuloprocesoenhanced == null) {
                            throw new DAOException(
                                    "No se pudo generar el círculo proceso para el reparto notarial ");
                    }
                                       
                }catch (Exception e) {	
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage());
//			throw new DAOException(e.getMessage(), e);
		}
                
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                            circuloprocesoenhanced = (CirculoProcesoEnhanced) iter.next();
                }

		return circuloprocesoenhanced;
	}
	
	
	
	
    /**
     * Crea una instancia de workflow en el sistema, Retorna el mensaje de respuesta 
     * generado por el workflow, es necesario indicar el tipo de proceso del cual se
     * va a generar el Workflow.
     * @param  turno <code>Turno</code> con sus atributos.
     * @param user <code>Usuario</code> iniciador del proceso.
     * @throws <code>DAOException</code>
     */
    public Message crearWFTurno(Turno turno) throws DAOException {

    	
    	Message respuestaRespuestaWf=null;
    	
    	if (turno == null) {
            throw new DAOException(DAOException.TURNO_NULO);
        }

        Turno t = turno;
               
        //Tabla en la que se pasan los parámetros para la creación del workflow.
        Hashtable solicitud = new Hashtable();
        solicitud.put(Processor.ITEM_KEY, t.getIdWorkflow());
        
            //Asociar a la tabla de parámetros el valor del proceso de reparto notarial.
               	
        	solicitud.put(Processor.PROCESS,t.getIdProceso()+"");
  
        
        try {

        	HermodWFFactory.getFactory().getProcessor();
            
        	//Creación del WorkFlow correspondiente.
        	Processor processor = HermodWFFactory.getFactory().getProcessor();
        	String tipoProceso = Message.INICIAR_PROCESO;
        	Message mensaje = new Message(1, Message.AL_WORKFLOW, tipoProceso, solicitud);
        	respuestaRespuestaWf = processor.process(mensaje);

         } catch (WFException we) {
            Log.getInstance().debug(JDOTurnosNuevosDAO.class,we);
            throw new DAOException(we.getMessage(), we);
        } catch (Exception e) {
        	Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
        	Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
            
            throw new DAOException(e.getMessage(), e);
        }
        return respuestaRespuestaWf;
    }
    
    
    
	/**
	*  Obtiene la estación a la cual va a ser asignado un turno.
	*  @param m Message que contiene información del turno al cual se va a asociar la estación.
	*  @param idCirculo identificador del turno al cual se va a asociar la estación.
	*  @return El identificador de la estación a la cual debe ser asociado el turno. 
	*/
	public String obtenerEstacionTurno(Hashtable paramsRespuesta, String idTurno) throws DAOException{
		
        String rols   = (String) paramsRespuesta.get(Processor.ROL);
        
       	//Se asigna la Jerarquía asociada
        Jerarquia j = new Jerarquia();
        j.setJerarquiaId(JERARQUIA_CIRCULOS);
        
        //Se obtiene el Rol del usuario al que se va a asignar el turno.
        Rol r = new Rol();
        r.setRolId(rols);
        
        Nivel criterio = new Nivel();
        Estacion estacion = new Estacion();
        List listaPotenciales = new ArrayList();
        ServiceLocator sl = ServiceLocator.getInstancia();
        
        //Se intenta obtener la estación a la cual se asignará el Turno.
        try 
        {
              FenrirServiceInterface fenrir = (FenrirServiceInterface) sl.getServicio(SERVICIO_FENRIR);
            
              //Se obtiene círculo del turno.
              String circulo = idTurno;
              criterio.setAtributo1(circulo);
              
              //Se llama al Servicio de SAS que obtiene las estaciones posibles. 
              //sas.seleccionarEstacion(j,criterio,false,r);
              EstacionDAO eDAO = DAOFactory.getFactory().getEstacionDAO();
              List estacionesPosibles = eDAO.obtenerEstaciones(j, criterio, false, r);
			
			  //Se escoge una estación del listado de posibles estaciones.
			  //Se está usando un criterio aleatorio para obtenerla.  Debe tenerse también en cuenta
			  //que el usuario esté activo dentro del esquema de SIR. 
			  if (estacionesPosibles.size() > 0)
			  {
              
                //Obtener una nueva lista de Estaciones Posibles, teniendo ahora en cuenta el atributo
                //que indica si el usuario está o no activo en el esquema de SIR.
                for (int i=0; i< estacionesPosibles.size(); i++){
                	//Se obtiene la estación y se determina si sus usuarios asociados están activos.
                	//En caso de que los usuarios se encuentren activos, se inserta en una nueva lista.
                	Estacion estacionAux = (Estacion) estacionesPosibles.get(i);
                	
                	try{
						
						List listaUsuarios = fenrir.getUsuariosEstacion(estacionAux);
						//Determinar si hay usuarios activos dentro de la lista.  En dicho caso se agrega
						//la estación al listado de temporales.
						for (int k=0; k<listaUsuarios.size(); k++){
							gov.sir.core.negocio.modelo.Usuario userSir = (gov.sir.core.negocio.modelo.Usuario) listaUsuarios.get(k);
							if (userSir.isActivo()){
									listaPotenciales.add(estacionAux);
							}
						}
					}
						
                	catch (Throwable t)
                	{
                		throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones "+t.getMessage());
                	}
                }
			  }
              
              
              //Se escoge aleatoriamente una estación, de la lista de potenciales estaciones obtenidas
              //en el paso anterior.  
              if (listaPotenciales.size()>0){ 
                  Random ran = new Random();
                  int i = ran.nextInt(estacionesPosibles.size());
                  estacion = (Estacion) estacionesPosibles.get(i);
				  return estacion.getEstacionId();
              }
              
              //No se encontró ninguna estación para asignar el turno. 
              else {
                throw new DAOException("No se encontro ninguna estacion para el circulo "+ circulo+" y el rol "+rols);
              }
        }
        
        catch (ServiceLocatorException e) {
			
            throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones "+e.getMessage());
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
        	throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones "+e.getMessage());
		}
        
	}
	
	
	
	
	
	
	/**
	*  Guarda la información de un turno en la tabla de Turno Ejecución.
	*  @param m Message que contiene información del turno del cual se va a guardar la información.
	*  @param idTurno identificador del turno del cual se va a guardar la información.
	*  @param  usuarioSir usuario que realiza el avance o creación del turno 
	*  @return El identificador de la estación a la cual debe ser asociado el turno. 
	*/
	public void guardarInfoTurnoEjecucion (Hashtable paramsRespuesta, String estacionAsignada, Turno turno, Usuario usuarioSir) throws DAOException
	{
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		TurnoEnhanced turnoPersistente = null;
		boolean certificadoEspecial = false;
		
        String itemKey = (String) paramsRespuesta.get(Processor.ITEM_KEY);
        String notificationId = (String) paramsRespuesta.get(Processor.NOT_ID);
        String fase = (String) paramsRespuesta.get(Processor.ACTIVITY);
        String resultado = (String) paramsRespuesta.get(Processor.RESULT);
        String Ncopias = (String) paramsRespuesta.get(Processor.NCOPIAS);
        boolean isMayorExtension = false;
        
        try {
        	Boolean isMayor = (Boolean) paramsRespuesta.get("MAYOR_EXTENSION");	
        	if (isMayor.booleanValue()) {
        		isMayorExtension = true;
        	}
        } catch(Exception e) {
        	isMayorExtension = false;
        }
        
        
		TurnoEjecucion turnoEjecucion = new TurnoEjecucion();
		turnoEjecucion.setEstacion(estacionAsignada);
		turnoEjecucion.setNotificacionWF(notificationId);
		turnoEjecucion.setIdWorkflow(itemKey);
		turnoEjecucion.setEstado("1");
		turnoEjecucion.setFase(fase);
		turnoEjecucion.setHasWF(true);
		
		//Creación del objeto turno historia para la fase en la que queda asociado el turno
		//Turno turno = turnosDAO.getTurnoByWFId(idTurno);
		
		TurnoHistoria nuevoTurnoHistoria = new TurnoHistoria();
		nuevoTurnoHistoria.setIdCirculo(turno.getIdCirculo());
		nuevoTurnoHistoria.setAnio(turno.getAnio());
		nuevoTurnoHistoria.setIdTurno(turno.getIdTurno());
		nuevoTurnoHistoria.setIdProceso(turno.getIdProceso());
		nuevoTurnoHistoria.setNcopias(Ncopias);
		if(turno.getSolicitud() instanceof SolicitudCertificado){
			SolicitudCertificado sol = new SolicitudCertificado();
			sol = (SolicitudCertificado)turno.getSolicitud();
			LiquidacionTurnoCertificado lq = 
				(LiquidacionTurnoCertificado)sol.getLiquidaciones().get(0);
			String tipo = lq.getTipoCertificado().getNombre();
			if(tipo.equals(CTipoCertificado.TIPO_PERTENENCIA_NOMBRE) || 
					tipo.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_NOMBRE)
					|| tipo.equals(CTipoCertificado.AMPLIACION_TRADICION)){
				certificadoEspecial = true;
			}
			if (isMayorExtension){
				certificadoEspecial = true;
			}
		}
		
		if(turno.getSolicitud() instanceof SolicitudCorreccion || 
				turno.getSolicitud() instanceof SolicitudDevolucion ||
				turno.getSolicitud() instanceof SolicitudCertificadoMasivo ||
				turno.getSolicitud() instanceof SolicitudConsulta ||
				turno.getSolicitud() instanceof SolicitudRestitucionReparto ||
				certificadoEspecial){
			nuevoTurnoHistoria.
				setIdTurnoHistoria((turno.getLastIdHistoria()+2)+"");
		}else{
			nuevoTurnoHistoria.setIdTurnoHistoria( (turno.getLastIdHistoria()+1)+"");
		}
		nuevoTurnoHistoria.setFase(fase);
		nuevoTurnoHistoria.setIdAdministradorSAS(estacionAsignada);
		nuevoTurnoHistoria.setFecha(new Date());
		nuevoTurnoHistoria.setActivo(true);
		nuevoTurnoHistoria.setRespuesta(resultado);
		
		TurnoHistoriaEnhanced nuevoTurnoHistoriaEnhanced = TurnoHistoriaEnhanced.enhance(nuevoTurnoHistoria);
		
		//Construcción del Turno.ID para obtener el turno persistente.
		TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
		idTurnoEnh.anio = turno.getAnio();
		idTurnoEnh.idCirculo = turno.getIdCirculo();
		idTurnoEnh.idProceso = turno.getIdProceso();
		idTurnoEnh.idTurno = turno.getIdTurno();
		
		
		PersistenceManager pm = AdministradorPM.getPM();
		TurnoEjecucionEnhanced turnoEjecucionEnhanced = (TurnoEjecucionEnhanced)TurnoEjecucionEnhanced.enhance(turnoEjecucion);

		try {

			pm.currentTransaction().begin();
			
			 UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuarioSir.getUsername(), pm);
				if (usuarior == null) {
					throw new DAOException(
						"No encontró el Usuario con el login: " + usuarioSir.getUsername());
				}
			
				nuevoTurnoHistoriaEnhanced.setUsuario(usuarior);
			
			try {
				 turnoPersistente = (TurnoEnhanced) pm.getObjectById(idTurnoEnh, true);
		     }catch (JDOObjectNotFoundException e) {
		          throw new DAOException(
							"No encontró el Turno con el identificador: " + turno.getIdWorkflow());
		     } 
			
	        turnoPersistente.setLastIdHistoria(turnoPersistente.getLastIdHistoria()+1);   
	        turnoPersistente.addHistorial(nuevoTurnoHistoriaEnhanced);
	        turnoPersistente.setUltimaRespuesta(resultado);
				
            //Hacer persistente y hacer commit
			pm.makePersistent(turnoEjecucionEnhanced);
			pm.currentTransaction().commit();
			

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
			throw new DAOException(e.getMessage(), e);
		} 
		
		catch (Throwable t) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().debug(JDOTurnosNuevosDAO.class,t);
			throw new DAOException(t.getMessage(), t);
		} finally {
			pm.close();
		}
	}
		
  	
	
	   /**
     * Obtiene un Turno dado su identificador, método utilizado para transacciones
     * @param tID identificador del Turno
     * @param pm PersistenceManager de la transaccion
     * @return Turno con sus atributos
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoByID(TurnoEnhancedPk tID,
        PersistenceManager pm) throws DAOException {
        TurnoEnhanced rta = null;

		//Se coloca un tiempo de delay
		JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
		/*boolean respuesta = */jdoDAO.getObjectByLlavePrimaria(tID, 3, pm);

        if ((tID.idTurno != null) && (tID.idCirculo != null) &&
                (String.valueOf(tID.idProceso) != null)) {
            try {
                rta = (TurnoEnhanced) pm.getObjectById(tID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }
    
    /**
     * Metodo encargado de traer las valores necesarios para avanzar un turno.
     */
    protected Hashtable getDatosAvanceTurno(Turno turno, String respuesta, Usuario usuario, PersistenceManager pm ) throws DAOException {
    	
        Hashtable rta  = new Hashtable();
    	Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		String faseDestino = "";
		String idRol = "";
		
		VersantPersistenceManager jdoPM = null;

        try {     		
 			 String consulta =
					"SELECT ID_FASE_FINAL, ID_ROL FROM SIR_WF_PROCESOS "+
				 	"WHERE ID_PROCESO = ? " +
				 	"AND ID_FASE_INICIAL = ? " +
				 	"AND RESPUESTA = ? " ;
				 	
				jdoPM = (VersantPersistenceManager) pm;

				connection = jdoPM.getJdbcConnection(null);
				ps = connection.prepareStatement(consulta);

				ps.setLong(1, turno.getIdProceso());
				ps.setString(2, turno.getIdFase());
				ps.setString(3, respuesta);

				rs = ps.executeQuery();

				boolean encontroCamino = false;
				
				while (rs.next()) {
					faseDestino = rs.getString(1);
					idRol = rs.getString(2);
					encontroCamino = true;
				}
				
				if (encontroCamino){
                                    /**
                                     * @author              : Ellery David Robles Gómez.
                                     * @casoMantis          : 08539.
                                     * @actaRequerimiento   : 151. Proceso Certificados Error al enviar Ampliacion Tradicion.
                                     * @change              : Se crea if que identifique si el tipo de certificado es "AMPLIACION_TRADICION", si su fase es "CER_SOLICITUD"
                                     *                        y si tiene al menos un folio asociado, entonces que avace a AMPLIACION_TRADICION, si estas condiciones no se cumplen
                                     *                        entonces que avence a ANTIGUO_SISTEMA, para que le creen o asignen una matricula, para luego avanzar a AMPLIACION_TRADICION.
                                     */
                                    if(turno.getIdProceso() == 1){
                                        LiquidacionTurnoCertificado ltc = (LiquidacionTurnoCertificado) turno.getSolicitud().getLiquidaciones().get(0);
                                        if((turno.getSolicitud().getSolicitudFolios().size() >= 1) &&
                                           (ltc.getTipoCertificado().getNombre().equals(CFase.AMPLIACION_TRADICION)) &&
                                           (turno.getIdFase().equals(CFase.CER_SOLICITUD))){
                                                rta.put(Processor.FASE_DESTINO, faseDestino);
                                                rta.put(Processor.ROL_DESTINO, idRol);

                                        } else {
                                            if((turno.getIdProceso() == 1) &&
                                               (ltc.getTipoCertificado().getNombre().equals(CFase.AMPLIACION_TRADICION)) &&
                                               (turno.getIdFase().equals(CFase.ANT_REVISION))){
                                                    int thTam = turno.getHistorials().size();
                                                    TurnoHistoria th = (TurnoHistoria) turno.getHistorials().get(thTam - 1);
                                                    if(th.getFaseAnterior().equals(CFase.ANT_HOJA_RUTA)){
                                                        rta.put(Processor.FASE_DESTINO, CFase.CER_AMPLIACION_TRADICION);
                                                        rta.put(Processor.ROL_DESTINO, CRol.SIR_ROL_AMPLIACION_TRADICION);
                                                    } else {
                                                        rta.put(Processor.FASE_DESTINO, faseDestino);
                                                        rta.put(Processor.ROL_DESTINO, idRol);
                                                    }
                                            } else {
                                                rta.put(Processor.FASE_DESTINO, faseDestino);
                                                rta.put(Processor.ROL_DESTINO, idRol);
                                            }
                                        }
                                    } else {
                                        rta.put(Processor.FASE_DESTINO, faseDestino);
                                        rta.put(Processor.ROL_DESTINO, idRol);
                                    }
				} else {
		 			 String consulta2 =
							"SELECT ID_FASE_FINAL, ID_ROL FROM SIR_WF_PROCESOS "+
						 	"WHERE ID_PROCESO = ? " +
						 	"AND ID_FASE_INICIAL = ? " +
						 	"AND RESPUESTA = ? " ;

		 			ps2 = connection.prepareStatement(consulta2);

					ps2.setLong(1, turno.getIdProceso());
					ps2.setString(2, turno.getIdFase());
					ps2.setString(3, "*");

					rs2 = ps2.executeQuery();

					boolean encontroCamino2 = false;
					
					while (rs2.next()) {
						faseDestino = rs2.getString(1);
						idRol = rs2.getString(2);
						encontroCamino2 = true;
					}
					
					if (encontroCamino2){
						rta.put(Processor.FASE_DESTINO, faseDestino);
						rta.put(Processor.ROL_DESTINO, idRol);	
					} else {
						rta.put(Processor.FASE_DESTINO, "END");
						rta.put(Processor.ROL_DESTINO, "");	
					}
				}
				
				
			} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}


			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
                if(connection != null){
                    connection.close();
                }
			} catch (SQLException e) {
				throw new DAOException(e.getMessage(), e);
			}
		}
		
    	return rta;
    	
    }
    
    
    /**
     * Avanza un turno a la siguiente fase de acuerdo con los parámetros recibidos.
     * Como el modo bloqueo queda en normal, cualquier usuario en cualquier turno,
     * puede coger el turno.
     * <p>Debe realizar además las siguientes funcionalidades:
     * <p> 1. Setear en CMODOBLOQUEO.NORMAL el atributo modoBloqueo.
     * <p> 2. Avanzar el <code>Turno</code>
     * @param turno El <code>Turno</code> que va a ser avanzado.
     * @param fase La <code>Fase</code> en la que se encuentra el <code>Turno</code>.
     * @param parametros Hashtable con los parametros necesarios para realizar el avance.
     * @return true o false dependiendo del resultado de la operación.
     * @param usuario Usuario que está realizando el avance.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     */
     public boolean avanzarTurnoNuevoNormal(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException {
         
         
    	 PersistenceManager pm = AdministradorPM.getPM();
         //boolean respuesta = false;
         //String notificationRespuesta = null;
         
         //JDOTurnosDAO turnosDao = new JDOTurnosDAO();
           /**
            * @author      :   Carlos Torres
            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
            */ 
         boolean avanzo = true;      
         
         String idWorkflow = turno.getIdWorkflow();
    	 
    	TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
        idTurnoEnh.anio=turno.getAnio();
        idTurnoEnh.idCirculo=turno.getIdCirculo();
        idTurnoEnh.idProceso = turno.getIdProceso();
        idTurnoEnh.idTurno = turno.getIdTurno();
        TurnoEnhanced turnoEnh = (TurnoEnhanced)pm.getObjectById(idTurnoEnh,true);
        
        Hashtable resultadoAvance = new Hashtable();                
         
         try {
        	 if (fase.getID().equals(turnoEnh.getIdFase()) 
        			 || (turno.getIdFase().equals(CFase.CER_ESPERA_IMPRIMIR) && fase.getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS))
        			 || (turno.getIdFase().equals(CFase.CER_ENTREGAR) && (fase.getID().equals(CFase.REG_ENTREGA)
                     ||fase.getID().equals(CFase.REG_ENTREGA_EXTERNO)))){
                     
        		 pm.currentTransaction().begin();
            	 //Obtener de la tabla TurnoEjecucion los parametros necesarios para el avance.
        		 
        		 if(turno.getHistorials()!=null && turno.getHistorials().size()>0 && ((TurnoHistoria)turno.getHistorials().get(turno.getHistorials().size()-1)).getAccion()!=null && 
        	        		turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)){
        	        	((TurnoHistoriaEnhanced)turnoEnh.getHistorials().get(turnoEnh.getHistorials().size()-1)).setAccion( 
        	        		((TurnoHistoria)turno.getHistorials().get(turno.getHistorials().size()-1)).getAccion());
        	        }
            	
//               Se actualiza la relación actual del turno
                 if (turnoEnh != null) {
                	TurnoHistoriaEnhanced turnoHistoriaEnh = null;
                 	List historial = turnoEnh.getHistorials();
                 	Iterator itHist=historial.iterator();
                 	while(itHist.hasNext()){
                 		TurnoHistoriaEnhanced temp=(TurnoHistoriaEnhanced)itHist.next();
                 		if (turnoHistoriaEnh==null){
                 			turnoHistoriaEnh=temp;
                 		}else{
                 			int idTurno = Integer.parseInt(turnoHistoriaEnh.getIdTurnoHistoria());
                 			int idTemp = Integer.parseInt(temp.getIdTurnoHistoria());
                 			if (idTurno<idTemp){
                 				turnoHistoriaEnh=temp;
                 			}
                 		}
                 	}
                 	String idRelacion = turnoHistoriaEnh.getIdRelacionSiguiente();
                 	if(idRelacion != null) {
                 		turnoEnh.setIdRelacionActual(idRelacion);
                 	}
                 }
            	 
            	 TurnoEjecucionEnhancedPk idTurnoEjecucion = new TurnoEjecucionEnhancedPk();
            	 idTurnoEjecucion.idWorkflow = idWorkflow;
            	 
            	 TurnoEjecucionEnhanced turnoEjecEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucion,true);
                 if (turnoEjecEnh == null)
                 {
                	 throw new DAOException ("No se encontró el Turno ejecucion con el id: "+idWorkflow);
                 }
                  
                 String itemKey = turnoEjecEnh.getIdWorkflow();
                 String notification = turnoEjecEnh.getNotificacionWF();
                 String result = (String) parametros.get(Processor.RESULT);
                 String Ncopias = (String) parametros.get(Processor.NCOPIAS);
                 String estacion = (String)parametros.get(Processor.ESTACION);
                 
                 
                 Hashtable solicitud = new Hashtable();
                 solicitud.put(Processor.ITEM_KEY,itemKey);
                 solicitud.put(Processor.RESULT,result);
                 if(Ncopias == null){
                     Ncopias = "NONE";
                 }
                 solicitud.put(Processor.NCOPIAS,Ncopias);
                 if (turnoEjecEnh.isHasWF()){
                	 solicitud.put(Processor.NOT_ID,notification);	 
                	 
                	 //1. Avanzar el turno en el WF.
                	 //Message mensajeRespuesta = this.avanzarWFTurno(solicitud);
                	 //Se tiene que preguntar de donde a donde va
                	 resultadoAvance = getDatosAvanceTurno(turno,result,usuario,pm);
                	 resultadoAvance.put(Processor.ITEM_KEY, turno.getIdWorkflow());
                	 resultadoAvance.put(Processor.NOT_ID, "1");
                	 resultadoAvance.put(Processor.RESULT, result);
                	 resultadoAvance.put(Processor.NCOPIAS, Ncopias);
                	 //Si es calificacion antiguo sistema tiene su caso.
                	 String faseDestino = (String)resultadoAvance.get(Processor.FASE_DESTINO);
                	 String rolDestino = (String)resultadoAvance.get(Processor.ROL_DESTINO);
                	 
                	 resultadoAvance.put(Processor.ACTIVITY, faseDestino);
                	 resultadoAvance.put(Processor.ROL, rolDestino);
                	 
                	    //si la respuesta es END se finaliza, sino se busca la estacion con el rol y la fase.
                     if (faseDestino.equals("END")) {
                         this.actualizarInfoTurnoFinalizacion(resultadoAvance, turno.getIdWorkflow(), usuario, pm);
                     } /**
                      * ******************************************
                      */
                     /*   CASO BASICO: NO ES FASE DE FINALIZACION */ /**
                      * ******************************************
                      */
                     else {
                         //  2. Obtener estacion a la cual se asignará el turno:
                         String estacionAsignada = null;
                         if (estacion == null) {
                             estacionAsignada = this.obtenerEstacionTurno(resultadoAvance, turno.getIdCirculo());
                         } else {
                             estacionAsignada = estacion;
                         }

                         //3. Actualizar Modelo Operativo.
                         //3.1 Actualizar tabla de Turno Ejecucion.
                         //3.2 Actualizar tabla Turno.
                         //3.3.Crear nuevo turno historia.
                         //3.4.Actualizar turno historia anteriro.
                         turno.setLastIdHistoria(turnoEnh.getLastIdHistoria());
                         this.actualizarInfoTurnoEjecucion(resultadoAvance, estacionAsignada, turno, usuario, pm);

                     }
                 }else{
                	 this.actualizarInfoTurnoFinalizacionSinWF(solicitud,itemKey,usuario,pm);
                 }
                 
//             	Se actualiza el usuario del Círculo.
                  if (turnoEnh != null) {
                      turnoEnh.setUsuarioDestino(null);
                  }

                  //Se actualiza el modo de bloqueo del Turno.
                  if (turnoEnh != null) {
                      turnoEnh.setModoBloqueo(CModoBloqueo.NORMAL);
                  }
                 pm.currentTransaction().commit();
            	 
             
             	
             }else{
             	return false;
             }
         } catch (Throwable t) {
        	 t.printStackTrace();
                 
                /**
                * @author      :   Carlos Torres
                * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                */ 
                 avanzo = false;
             if (pm.currentTransaction().isActive()) {
                 pm.currentTransaction().rollback();
             }
             
                          
             throw new DAOException(t.getMessage());
          } finally {
 			pm.close();
                          /**
                            * @author      :   Carlos Torres
                            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                            */ 
                        
                        
 		}
                    if(!avanzo){
                        return false;
                    }
 		return true;
 	}

     
     
     /**
      * Avanza una instancia del workflow en el sistema, Retorna el mensaje de respuesta 
      * generado por el workflow
      * @param  parametros <code>Hashtable</code> con los parámetros necesarios para el avance. 
      * @param user <code>Usuario</code> iniciador del proceso.
      * @throws <code>DAOException</code>
      */
     /*public Message avanzarWFTurno(Hashtable parametros) throws DAOException {
                
     	
     	Message respuestaRespuestaWf=null;
     	
     	//Garantizar que dentro de los parámetros vayan: ITEM KEY, NOTIFICATION ID, y RESULT.
     	String itemKey = (String) parametros.get(Processor.ITEM_KEY);
     	String notification = (String) parametros.get(Processor.NOT_ID);
     	String result = (String) parametros.get(Processor.RESULT);
     	
     	if(itemKey == null)
     	{
     		throw new DAOException("El item key proporcionado para el avance es nulo.");
     	}
     	if(notification == null)
     	{
     		throw new DAOException("El notification id proporcionado para el avance es nulo.");
     	}
     	if(result == null)
     	{
     		throw new DAOException("El result proporcionado para el avance es nulo.");
     	}
     	
     	//Crear Hashtable para el paso de los parámetros correspondientes.
     	Hashtable solicitud = new Hashtable();
     	solicitud.put(Processor.ITEM_KEY, itemKey);
     	solicitud.put(Processor.NOT_ID, notification);
     	solicitud.put(Processor.RESULT, result);
     	              
         try {

         	HermodWFFactory.getFactory().getProcessor();
             
         	//Avance del WorkFlow correspondiente.
         	Processor processor = HermodWFFactory.getFactory().getProcessor();
         	String tipoProceso = Message.ACTUALIZAR_PROCESO;
         	Message mensaje = new Message(1, Message.AL_WORKFLOW, tipoProceso, solicitud);
         	respuestaRespuestaWf = processor.process(mensaje);

          } catch (WFException we) {
             Log.getInstance().debug(JDOTurnosNuevosDAO.class,we);
             throw new DAOException(we.getMessage(), we);
         } catch (Exception e) {
         	Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
         	Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
             
             throw new DAOException(e.getMessage(), e);
         }
         return respuestaRespuestaWf;
     }*/
 
     
     
     
 	
 	/**
 	*  Actualiza la información de un turno en la tabla de Turno Ejecución.
 	*  @param m Message que contiene información del turno del cual se va a guardar la información.
 	*  @param idTurno identificador del turno del cual se va a guardar la información.
 	*  @param  usuarioSir usuario que realiza el avance o creación del turno 
 	*  @return El identificador de la estación a la cual debe ser asociado el turno. 
 	*/
 	private void actualizarInfoTurnoEjecucion (Hashtable paramsRespuesta, String estacionAsignada, Turno turno, Usuario usuarioSir, PersistenceManager pm) throws DAOException
 	{
 		
 		//PersistenceManager pm = AdministradorPM.getPM();
 		
 		//Obtener del mensaje los atributos necesarios para la creación del registro:
 		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
 		TurnoEnhanced turnoPersistente = null;
 		TurnoEjecucionEnhanced turnoEjecucionEnh =null;
 		TurnoHistoriaEnhanced turnoHistoriaEnh =null;
 		

         String itemKey = (String) paramsRespuesta.get(Processor.ITEM_KEY);
         String notificationId = (String) paramsRespuesta.get(Processor.NOT_ID);
         String fase = (String) paramsRespuesta.get(Processor.ACTIVITY);
         String resultado = (String) paramsRespuesta.get(Processor.RESULT);
         String Ncopias = (String) paramsRespuesta.get(Processor.NCOPIAS);
         if(Ncopias == null){
            Ncopias = "NONE";
          }

 		try {

 			//Obtener usuario persistente.
 			UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuarioSir.getUsername(), pm);
				if (usuarior == null) {
					throw new DAOException(
						"No encontró el Usuario con el login: " + usuarioSir.getUsername());
				}
 			
 			//Obtener el turno ejecucion asociado.
 			TurnoEjecucionEnhancedPk idTurnoEjecucionEnh = new TurnoEjecucionEnhancedPk();
            idTurnoEjecucionEnh.idWorkflow = itemKey;
            
            try{
            	turnoEjecucionEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucionEnh,true);
            }
            
            catch (JDOObjectNotFoundException e) {
            	Log.getInstance().error(JDOTurnosNuevosDAO.class,e);  
            	throw new DAOException(
							"No encontró el Turno con el identificador: " + itemKey);
		     } 
            
            
            if (turnoEjecucionEnh == null){
            	throw new DAOException ("No se encontró el turno ejecucion con el id: "+itemKey);
            }
 	 		
                        /*
                         * @author      :   Henry Gómez Rocha
                         * @change      :   Se verifica que al momento de hacer la revisión final de un
                                            turno desde el rol Encargado Antiguo Sistema, dicho turno
                                            retorne al usuario que lo envió a Solicitar información de
                                            folios a otras dependencias (ANTIGUO SISTEMA).
                         * Caso Mantis  :   0002484
                         */
                        if(CFase.ANT_REVISION.equals(turno.getIdFase()) && CFase.COR_REVISION_ANALISIS.equals(fase)){
                            /**
                            * @author Daniel Forero
                            * @change REQ 1156 - Verificar que la estación seleccionada tenga el rol activo.
                            */
                            List turnoHistorial = turno.getHistorials();
                            Iterator itr = turnoHistorial.iterator();
                            TurnoHistoria turnoHistoria = null;
                            TurnoHistoria itrTurno = null;
                            
                            /*
                             * @author      :   Julio Alcazar
                             * @change      :   se actualiza el codigo que retorna el turno al usuario que lo envió a Solicitar información de
                                                folios a otras dependencias (ANTIGUO SISTEMA). Teniendo en cuenta si el turno fue reasignado antes de
                                                iniciar el proceso de revision y analisis CFase.COR_REVISION_ANALISIS.
                             * Caso Mantis  :   0006176
                             */
                            while (itr.hasNext()) {
                                itrTurno = (TurnoHistoria) itr.next();
                                if (CFase.COR_REVISION_ANALISIS.equals(itrTurno.getFase())) {
                                    turnoHistoria = itrTurno;
                                }
                            }
                            
                            if(turnoHistoria != null){
                                Hashtable resultadosAvance = new Hashtable();
                                resultadosAvance.put(Processor.ROL, getRolByFase(turnoHistoria.getFase(), pm));
                                
                                estacionAsignada = obtenerEstacionActiva(
                                        turnoHistoria.getIdAdministradorSAS(),
                                        turno.getIdCirculo(),
                                        paramsRespuesta);
                            }
                        }

                        //Actualizar los atributos del turno ejecucion.
                        turnoEjecucionEnh.setEstacion(estacionAsignada);
 	 		turnoEjecucionEnh.setNotificacionWF(notificationId);
 	 		turnoEjecucionEnh.setEstado("1");
 	 		turnoEjecucionEnh.setFase(fase);
 	 
 	 		
 	 		//Actualización del Turno Historia Anterior.
 	 		//Turno turno = turnosDAO.getTurnoByWFId(idTurno);
 	 		TurnoHistoriaEnhancedPk idAnterior = new TurnoHistoriaEnhancedPk();
 	 		idAnterior.anio = turno.getAnio();
 	 		idAnterior.idCirculo = turno.getIdCirculo();
 	 		idAnterior.idProceso = turno.getIdProceso();
 	 		idAnterior.idTurno = turno.getIdTurno();
 	 		idAnterior.idTurnoHistoria= turno.getLastIdHistoria()+"";
 	 		
 	 		try{
        		turnoHistoriaEnh = (TurnoHistoriaEnhanced) pm.getObjectById(idAnterior,true);
            }
        
             catch (JDOObjectNotFoundException e) {
            	 	Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
	                throw new DAOException("No encontró el último turno historia para el turno con el identificador: " + itemKey);
             } 
            if (turnoEjecucionEnh == null){
        	      throw new DAOException ("No se encontró el último turno historia para el turno con el id: "+itemKey);
            }
 	 		turnoHistoriaEnh.setActivo(false);
 	 		turnoHistoriaEnh.setRespuesta(resultado);
 	 		turnoHistoriaEnh.setUsuarioAtiende(usuarior);
                        
 	 		turnoHistoriaEnh.setNcopias(Ncopias);
 	 		String faseAnterior=turnoHistoriaEnh.getFase();
            
 	 		//Creación del objeto turno historia para la fase en la que queda asociado el turno
 	 		
 	 		TurnoHistoria nuevoTurnoHistoria = new TurnoHistoria();
 	 		nuevoTurnoHistoria.setIdCirculo(turno.getIdCirculo());
 	 		nuevoTurnoHistoria.setAnio(turno.getAnio());
 	 		nuevoTurnoHistoria.setIdTurno(turno.getIdTurno());
                        nuevoTurnoHistoria.setNcopias(Ncopias);
 	 		nuevoTurnoHistoria.setIdProceso(turno.getIdProceso());
 	 		nuevoTurnoHistoria.setIdTurnoHistoria((turno.getLastIdHistoria()+1)+"");
 	 		nuevoTurnoHistoria.setFase(fase);
 	 		nuevoTurnoHistoria.setIdAdministradorSAS(estacionAsignada);
 	 		nuevoTurnoHistoria.setFecha(new Date());
 	 		nuevoTurnoHistoria.setActivo(true);
 	 		//nuevoTurnoHistoria.setRespuesta(resultado);
 	 		nuevoTurnoHistoria.setFaseAnterior(faseAnterior);
 	 		
 	 		TurnoHistoriaEnhanced nuevoTurnoHistoriaEnhanced = TurnoHistoriaEnhanced.enhance(nuevoTurnoHistoria);
 	 		nuevoTurnoHistoriaEnhanced.setUsuario(usuarior);
 	 		
 	 		//Construcción del Turno.ID para obtener el turno persistente.
 	 		TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
 	 		idTurnoEnh.anio = turno.getAnio();
 	 		idTurnoEnh.idCirculo = turno.getIdCirculo();
 	 		idTurnoEnh.idProceso = turno.getIdProceso();
 	 		idTurnoEnh.idTurno = turno.getIdTurno();
 	 		
 			try {
 				 turnoPersistente = (TurnoEnhanced) pm.getObjectById(idTurnoEnh, true);
 		     }catch (JDOObjectNotFoundException e) {
 		    	  Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
 		          throw new DAOException(
 							"No encontró el Turno con el identificador: " + turno.getIdWorkflow());
 		     } 
 			
 	        turnoPersistente.setLastIdHistoria(turnoPersistente.getLastIdHistoria()+1);   
 	        turnoPersistente.setIdFase(fase);
 	        turnoPersistente.setUsuarioDestino(usuarior);
 	        turnoPersistente.addHistorial(nuevoTurnoHistoriaEnhanced);
 	        turnoPersistente.setUltimaRespuesta(resultado);

 		} catch (JDOException e) {
 			Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
 			throw new DAOException(e.getMessage(), e);
 		} 
 		
 		catch (Throwable t) {
 			t.printStackTrace();
 			throw new DAOException(t.getMessage(), t);
 		}
 	}
 	
 	
 	
 	
 	/**
 	*  Actualiza la información de un turno en la tabla de Turno Ejecución cuando llegue a una fase de finalización.
 	*  @param m Message que contiene información del turno del cual se va a guardar la información.
 	*  @param idTurno identificador del turno del cual se va a guardar la información.
 	*  @param  usuarioSir usuario que realiza el avance o creación del turno 
 	*  @return El identificador de la estación a la cual debe ser asociado el turno. 
 	*/
 	private void actualizarInfoTurnoFinalizacion (Hashtable paramsRespuesta, String idTurno, Usuario usuarioSir, PersistenceManager pm) throws DAOException
 	{
 		
 		//PersistenceManager pm = AdministradorPM.getPM();
 		
 		//Obtener del mensaje los atributos necesarios para la creación del registro:
 		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
 		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
 		TurnoEnhanced turnoPersistente = null;
 		TurnoEjecucionEnhanced turnoEjecucionEnh =null;
 		TurnoHistoriaEnhanced turnoHistoriaEnh =null;
 		
         String itemKey = (String) paramsRespuesta.get(Processor.ITEM_KEY);
         String resultado = (String) paramsRespuesta.get(Processor.RESULT);
         String Ncopias = (String) paramsRespuesta.get(Processor.NCOPIAS);
         if(Ncopias == null){
             Ncopias = "NONE";
         }
 		try {
 			
 			//Obtener usuario persistente.
 			UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuarioSir.getUsername(), pm);
				if (usuarior == null) {
					throw new DAOException(
						"No encontró el Usuario con el login: " + usuarioSir.getUsername());
				}
 			
 			//Obtener el turno ejecucion asociado.
 			TurnoEjecucionEnhancedPk idTurnoEjecucionEnh = new TurnoEjecucionEnhancedPk();
            idTurnoEjecucionEnh.idWorkflow = itemKey;
            
            try{
            		turnoEjecucionEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucionEnh,true);
            }
            
            catch (JDOObjectNotFoundException e) {
		          throw new DAOException(
							"No encontró el Turno con el identificador: " + itemKey);
		     } 
            
            
            if (turnoEjecucionEnh == null)
            {
            	throw new DAOException ("No se encontró el turno ejecucion con el id: "+itemKey);
            }
 	 		
            //Actualizar los atributos del turno ejecucion.
            turnoEjecucionEnh.setEstacion("FINALIZADO");
 	 		turnoEjecucionEnh.setNotificacionWF("0");
 	 		turnoEjecucionEnh.setEstado("2");
 	 		turnoEjecucionEnh.setFase("FINALIZADO");
 	 		
 	 		
 	 		//Actualización del Turno Historia Anterior.
 	 		Turno turno = turnosDAO.getTurnoByWFId(idTurno);
                        turno.setIdFase("FINALIZADO");
 	 		TurnoHistoriaEnhancedPk idAnterior = new TurnoHistoriaEnhancedPk();
 	 		idAnterior.anio = turno.getAnio();
 	 		idAnterior.idCirculo = turno.getIdCirculo();
 	 		idAnterior.idProceso = turno.getIdProceso();
 	 		idAnterior.idTurno = turno.getIdTurno();
 	 		idAnterior.idTurnoHistoria= turno.getLastIdHistoria()+"";
 	 		
 	 		try{
        		turnoHistoriaEnh = (TurnoHistoriaEnhanced) pm.getObjectById(idAnterior,true);
            }
        
             catch (JDOObjectNotFoundException e) {
	                throw new DAOException("No encontró el último turno historia para el turno con el identificador: " + itemKey);
	        } 
            if (turnoEjecucionEnh == null)
            {
        	      throw new DAOException ("No se encontró el último turno historia para el turno con el id: "+itemKey);
            }
 	 		turnoHistoriaEnh.setActivo(false);
 	 		turnoHistoriaEnh.setRespuesta(resultado);
 	 		turnoHistoriaEnh.setUsuarioAtiende(usuarior);
                        turnoHistoriaEnh.setNcopias(Ncopias);
 	 		//Creación del objeto turno historia para la fase en la que queda asociado el turno
 	 		
 	 		TurnoHistoria nuevoTurnoHistoria = new TurnoHistoria();
 	 		nuevoTurnoHistoria.setIdCirculo(turno.getIdCirculo());
 	 		nuevoTurnoHistoria.setAnio(turno.getAnio());
 	 		nuevoTurnoHistoria.setIdTurno(turno.getIdTurno());
 	 		nuevoTurnoHistoria.setIdProceso(turno.getIdProceso());
                        nuevoTurnoHistoria.setNcopias(Ncopias);
 	 		nuevoTurnoHistoria.setIdTurnoHistoria( (turno.getLastIdHistoria()+1)+"");
 	 		nuevoTurnoHistoria.setFase("FINALIZADO");
 	 		nuevoTurnoHistoria.setIdAdministradorSAS("FINALIZADO");
 	 		nuevoTurnoHistoria.setFecha(new Date());
 	 		nuevoTurnoHistoria.setActivo(true);
 	 		
 	 		TurnoHistoriaEnhanced nuevoTurnoHistoriaEnhanced = TurnoHistoriaEnhanced.enhance(nuevoTurnoHistoria);
 	 		nuevoTurnoHistoriaEnhanced.setUsuario(usuarior);
 	 		
 	 		//Construcción del Turno.ID para obtener el turno persistente.
 	 		TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
 	 		idTurnoEnh.anio = turno.getAnio();
 	 		idTurnoEnh.idCirculo = turno.getIdCirculo();
 	 		idTurnoEnh.idProceso = turno.getIdProceso();
 	 		idTurnoEnh.idTurno = turno.getIdTurno();
 	 			
 			try {
 				 turnoPersistente = (TurnoEnhanced) pm.getObjectById(idTurnoEnh, true);
 		     }catch (JDOObjectNotFoundException e) {
 		          throw new DAOException(
 							"No encontró el Turno con el identificador: " + turno.getIdWorkflow());
 		     } 
 			
 	        turnoPersistente.setLastIdHistoria(turnoPersistente.getLastIdHistoria()+1);   
 	        turnoPersistente.setIdFase("FINALIZADO");
 	        turnoPersistente.setUsuarioDestino(usuarior);
 	        turnoPersistente.setFechaFin(new Date());
 	        turnoPersistente.addHistorial(nuevoTurnoHistoriaEnhanced);


                /**
                  * @author Fernando Padilla Velez
                  * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa SGD,
                  *         Se comentan estan lineas, ya que se realizó refactoring al proceso y ya no son necesarias.
                  */
                SGD sgd = new SGD(turno, usuarior.getUsername());
                sgd.enviarEstadoTurno();
                boolean consultaSimple = false;
                if(turno.getSolicitud() instanceof SolicitudConsulta){
                    if(((SolicitudConsulta)turno.getSolicitud()).getTipoConsulta().getNombre().equals("SIMPLE")){
                        consultaSimple = true;
                    }
                }
                /*if ((turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) && (!consultaSimple ) &&
                    (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_FOTOCOPIAS))){
                    PublisherIntegracion  clienteJMS = new PublisherIntegracion();
                    clienteJMS.setUsuario(usuarior.getUsername());
                    clienteJMS.setTurno(turno);
                    clienteJMS.enviar();
                }*/

             //Hacer persistente y hacer commit
 			
 		} catch (JDOException e) {
 			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
 			throw new DAOException(e.getMessage(), e);
 		} 
 		
 		catch (Throwable t) {
 			Log.getInstance().debug(JDOTurnosNuevosDAO.class,t);
 			throw new DAOException(t.getMessage(), t);
 		}
 	}



 	/**
 	*  Actualiza la información de un turno en la tabla de Turno Ejecución cuando llegue a una fase de finalización.
 	*  @param m Message que contiene información del turno del cual se va a guardar la información.
 	*  @param idTurno identificador del turno del cual se va a guardar la información.
 	*  @param  usuarioSir usuario que realiza el avance o creación del turno 
 	*  @return El identificador de la estación a la cual debe ser asociado el turno. 
 	*/
 	public void actualizarInfoTurnoFinalizacionSinWF (Hashtable paramsRespuesta, String idTurno, Usuario usuarioSir, PersistenceManager pm) throws DAOException
 	{
 		//PersistenceManager pm = AdministradorPM.getPM();
 		
 		//Obtener del mensaje los atributos necesarios para la creación del registro:
 		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
 		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
 		TurnoEnhanced turnoPersistente = null;
 		TurnoEjecucionEnhanced turnoEjecucionEnh =null;
 		TurnoHistoriaEnhanced turnoHistoriaEnh =null;
 		         
         String itemKey = (String) paramsRespuesta.get(Processor.ITEM_KEY);
         String resultado = (String) paramsRespuesta.get(Processor.RESULT);
         String Ncopias = (String) paramsRespuesta.get(Processor.NCOPIAS);
 		try {
 			
 			//Obtener usuario persistente.
 			UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuarioSir.getUsername(), pm);
				if (usuarior == null) {
					throw new DAOException(
						"No encontró el Usuario con el login: " + usuarioSir.getUsername());
				}
 			
 			//Obtener el turno ejecucion asociado.
 			TurnoEjecucionEnhancedPk idTurnoEjecucionEnh = new TurnoEjecucionEnhancedPk();
            idTurnoEjecucionEnh.idWorkflow = itemKey;
            
            try{
            		turnoEjecucionEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucionEnh,true);
            }
            
            catch (JDOObjectNotFoundException e) {
		          throw new DAOException(
							"No encontró el Turno con el identificador: " + itemKey);
		     } 
            
            
            if (turnoEjecucionEnh == null){
            	throw new DAOException ("No se encontró el turno ejecucion con el id: "+itemKey);
            }
            
 	    /**
            * @author: Julio Alcazar
            * @change: 5680: Acta - Requerimiento No 212 - Pantalla de administración - Ver detalles de turno,
            *                Se asigna a la variable estacion la ultima estacion en que se encuentra el turno
            */
            String estacion = turnoEjecucionEnh.getEstacion();
            //Actualizar los atributos del turno ejecucion.
            turnoEjecucionEnh.setEstacion("FINALIZADO");
 	 		turnoEjecucionEnh.setNotificacionWF("0");
 	 		turnoEjecucionEnh.setEstado("2");
 	 		turnoEjecucionEnh.setFase("FINALIZADO");
 	 		
 	 		
 	 		//Actualización del Turno Historia Anterior.
 	 		Turno turno = turnosDAO.getTurnoByWFId(idTurno);
 	 		TurnoHistoriaEnhancedPk idAnterior = new TurnoHistoriaEnhancedPk();
 	 		idAnterior.anio = turno.getAnio();
 	 		idAnterior.idCirculo = turno.getIdCirculo();
 	 		idAnterior.idProceso = turno.getIdProceso();
 	 		idAnterior.idTurno = turno.getIdTurno();
 	 		idAnterior.idTurnoHistoria= turno.getLastIdHistoria()+"";
                        
 	 		/**
                        * @author: Julio Alcazar
                        * @change: 5680: Acta - Requerimiento No 212 - Pantalla de administración - Ver detalles de turno,
                        *                Basado en la estacion actual del turnoEjecucion se obtiene el idTurnoHistoria de esta estacion en el historial del turno
                        */
                        if (turno.getSolicitud() instanceof SolicitudCertificado && !turno.getSolicitud().getSolicitudesPadres().isEmpty()){
                            SolicitudAsociada  sa = ( SolicitudAsociada) turno.getSolicitud().getSolicitudesPadres().get(0);
                            if (sa.getSolicitudPadre() instanceof SolicitudRegistro || sa.getSolicitudPadre() instanceof SolicitudCertificadoMasivo){
                                List historial = turno.getHistorials();
                                Iterator iter = historial.iterator();
                                while(iter.hasNext()){
                                    TurnoHistoria turnohistoria = (TurnoHistoria) iter.next();
                                    if(estacion.equals(turnohistoria.getIdAdministradorSAS())){
                                        idAnterior.idTurnoHistoria = turnohistoria.getIdTurnoHistoria();
                                    }
                                }
                            }
                        }
 	 		try{
        		turnoHistoriaEnh = (TurnoHistoriaEnhanced) pm.getObjectById(idAnterior,true);
            }
        
             catch (JDOObjectNotFoundException e) {
	                throw new DAOException("No encontró el último turno historia para el turno con el identificador: " + itemKey);
	        } 
            if (turnoEjecucionEnh == null){
        	      throw new DAOException ("No se encontró el último turno historia para el turno con el id: "+itemKey);
            }
 	 		turnoHistoriaEnh.setActivo(false);
 	 		turnoHistoriaEnh.setRespuesta(resultado);
                        turnoHistoriaEnh.setNcopias(Ncopias);
 	 		turnoHistoriaEnh.setUsuarioAtiende(usuarior);
            
 	 		//Creación del objeto turno historia para la fase en la que queda asociado el turno
 	 		
 	 		TurnoHistoria nuevoTurnoHistoria = new TurnoHistoria();
 	 		nuevoTurnoHistoria.setIdCirculo(turno.getIdCirculo());
 	 		nuevoTurnoHistoria.setAnio(turno.getAnio());
 	 		nuevoTurnoHistoria.setIdTurno(turno.getIdTurno());
 	 		nuevoTurnoHistoria.setIdProceso(turno.getIdProceso());
 	 		nuevoTurnoHistoria.setIdTurnoHistoria( (turno.getLastIdHistoria()+1)+"");
 	 		nuevoTurnoHistoria.setFase("FINALIZADO");
 	 		nuevoTurnoHistoria.setIdAdministradorSAS("FINALIZADO");
 	 		nuevoTurnoHistoria.setFecha(new Date());
 	 		nuevoTurnoHistoria.setActivo(true);
 	 		
 	 		TurnoHistoriaEnhanced nuevoTurnoHistoriaEnhanced = TurnoHistoriaEnhanced.enhance(nuevoTurnoHistoria);
 	 		nuevoTurnoHistoriaEnhanced.setUsuario(usuarior);
 	 		
 	 		//Construcción del Turno.ID para obtener el turno persistente.
 	 		TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
 	 		idTurnoEnh.anio = turno.getAnio();
 	 		idTurnoEnh.idCirculo = turno.getIdCirculo();
 	 		idTurnoEnh.idProceso = turno.getIdProceso();
 	 		idTurnoEnh.idTurno = turno.getIdTurno();
 	 			
 			
 			try {
 				 turnoPersistente = (TurnoEnhanced) pm.getObjectById(idTurnoEnh, true);
 		     }catch (JDOObjectNotFoundException e) {
 		          throw new DAOException(
 							"No encontró el Turno con el identificador: " + turno.getIdWorkflow());
 		     } 
 			
 	        turnoPersistente.setLastIdHistoria(turnoPersistente.getLastIdHistoria()+1);   
 	        turnoPersistente.setIdFase("FINALIZADO");
 	        turnoPersistente.setUsuarioDestino(usuarior);
 	        turnoPersistente.setFechaFin(new Date());
 	        turnoPersistente.addHistorial(nuevoTurnoHistoriaEnhanced);
 				

 		} catch (JDOException e) {
 			Log.getInstance().debug(JDOTurnosNuevosDAO.class,e);
 			throw new DAOException(e.getMessage(), e);
 		} 
 		
 		catch (Throwable t) {
 			Log.getInstance().debug(JDOTurnosNuevosDAO.class,t);
 			throw new DAOException(t.getMessage(), t);
 		} 
 	}
 	

	public List getTurnosByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws DAOException {
		return null;
	}

	public boolean avanzarTurnoNuevoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException {
        
   	 PersistenceManager pm = AdministradorPM.getPM();
        String notificationRespuesta = null;
        boolean avanza = true;
       
        try {
        	
       	
       	 //Obtener de la tabla TurnoEjecucion los parametros necesarios para el avance.
       	 String idWorkflow = turno.getIdWorkflow();
       	 TurnoEjecucionEnhancedPk idTurnoEjecucion = new TurnoEjecucionEnhancedPk();
       	 idTurnoEjecucion.idWorkflow = idWorkflow;
       	 
       	 TurnoEjecucionEnhanced turnoEjecEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucion,true);
       	 if (turnoEjecEnh == null){
       		 throw new DAOException ("No se encontró el Turno ejecucion con el id: "+idWorkflow);
       		 }
       	 
        String itemKey = turnoEjecEnh.getIdWorkflow();
        String notification = turnoEjecEnh.getNotificacionWF();
        String result = (String) parametros.get(Processor.RESULT);
        String estacion = (String)parametros.get(Processor.ESTACION);
        String Ncopias = (String)parametros.get(Processor.NCOPIAS);
        Hashtable solicitud = new Hashtable();
        solicitud.put(Processor.ITEM_KEY,itemKey);
        solicitud.put(Processor.NOT_ID,notification);
        solicitud.put(Processor.RESULT,result);
        if(Ncopias == null){
            Ncopias = "NONE";
        }
        solicitud.put(Processor.NCOPIAS, Ncopias);
        Hashtable resultadoAvance = new Hashtable();
        
        //0. Agregar en el último turno historia del turno el Stak Pos Mayor+1
        TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
        idTurnoEnh.anio=turno.getAnio();
        idTurnoEnh.idCirculo=turno.getIdCirculo();
        idTurnoEnh.idProceso = turno.getIdProceso();
        idTurnoEnh.idTurno = turno.getIdTurno();
        TurnoEnhanced turnoEnh = (TurnoEnhanced)pm.getObjectById(idTurnoEnh,true);
        if (fase.getID().equals(turnoEnh.getIdFase())){
        	 pm.currentTransaction().begin();
	        Iterator itTurnoHist = turnoEnh.getHistorials().iterator();
	        TurnoHistoriaEnhanced ultimo=null;
	        int mayorStackPos = 0;
	        //0.1 Obtener el último turno historia y mayor StackPos
	        while (itTurnoHist.hasNext()){
	        	TurnoHistoriaEnhanced turnoHist = (TurnoHistoriaEnhanced)itTurnoHist.next();
	        	if (ultimo==null){
	        		ultimo = turnoHist;
	        	}else{
	        		int idActual=Integer.parseInt(turnoHist.getIdTurnoHistoria());
	        		int idUltimo=Integer.parseInt(ultimo.getIdTurnoHistoria());
	        		if (idActual>idUltimo){
	        			ultimo = turnoHist;
	        		}
	        	}
	        	if (turnoHist.getStackPos()>mayorStackPos){
	        		mayorStackPos = turnoHist.getStackPos();
	        	}
	        }
	        
	        ultimo.setStackPos(mayorStackPos+1);
	   	 
	        
	   	 //1. Avanzar el turno en el WF.
	   	 //Message mensajeRespuesta = this.avanzarWFTurno(solicitud);
	   	resultadoAvance = getDatosAvanceTurno(turno,result,usuario,pm);
	   	resultadoAvance.put(Processor.ITEM_KEY, turno.getIdWorkflow());
	   	resultadoAvance.put(Processor.NOT_ID, "1");
	   	resultadoAvance.put(Processor.RESULT, result);
                resultadoAvance.put(Processor.NCOPIAS, Ncopias);
   	 
	   	//Si es calificacion antiguo sistema tiene su caso.
	   	String faseDestino = (String)resultadoAvance.get(Processor.FASE_DESTINO);
	   	String rolDestino = (String)resultadoAvance.get(Processor.ROL_DESTINO);
   	 
	   	resultadoAvance.put(Processor.ACTIVITY, faseDestino);
	   	resultadoAvance.put(Processor.ROL, rolDestino);
   	 
	   	//si la respuesta es END se finaliza, sino se busca la estacion con el rol y la fase.
	   	if (faseDestino.equals("END")) {
	   		this.actualizarInfoTurnoFinalizacion (resultadoAvance,turno.getIdWorkflow(),usuario,pm);
	   	}
	   	    
	   	    
	   	 /*********************************************/
	   	 /*   CASO BASICO: NO ES FASE DE FINALIZACION */
	   	 /*********************************************/
	   	    
	   	 else
	   	 {
	           //  2. Obtener estacion a la cual se asignará el turno:
	   		 String estacionAsignada=null; 
	   		 if (estacion==null){
	   			 estacionAsignada = this.obtenerEstacionTurno(resultadoAvance, turno.getIdCirculo());	 
	   		 }else{
	   			 estacionAsignada = estacion;
	   		 }
	       	 
	       	 
	       	 //3. Actualizar Modelo Operativo.
	       	 //3.1 Actualizar tabla de Turno Ejecucion.
	       	 //3.2 Actualizar tabla Turno.
	       	 //3.3.Crear nuevo turno historia.
	       	 //3.4.Actualizar turno historia anteriro.
	   		 turno.setLastIdHistoria(turnoEnh.getLastIdHistoria());
	       	 this.actualizarInfoTurnoEjecucion (resultadoAvance,estacionAsignada,turno,usuario,pm);
	   		 
	   	 }
	
	        //0.2 Modo bloqueo del turno en Delegar Cualquiera
	        turnoEnh.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
	        turnoEnh.setUsuarioDestino(null);
	   	    pm.currentTransaction().commit();
   	  
	    }else{
	    	return false;
	    }
        }
    	catch (Throwable t) {
    	t.printStackTrace();
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        
       avanza = false;
       throw new DAOException(t.getMessage());
     } finally {
		pm.close();
	}
		return avanza;
	}




	public boolean avanzarTurnoNuevoPop(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException {
        
	   	PersistenceManager pm = AdministradorPM.getPM();
	        String notificationRespuesta = null;
	        Hashtable resultadoAvance = new Hashtable();
	        
	        long tiempoInicial =  System.currentTimeMillis();
			
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(INICIO):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	        
	        
	        try {
	       	 
	       	 //Obtener de la tabla TurnoEjecucion los parametros necesarios para el avance.
	       	 String idWorkflow = turno.getIdWorkflow();
	       	 TurnoEjecucionEnhancedPk idTurnoEjecucion = new TurnoEjecucionEnhancedPk();
	       	 idTurnoEjecucion.idWorkflow = idWorkflow;
	       	 
	       	 TurnoEjecucionEnhanced turnoEjecEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucion,true);
	            if (turnoEjecEnh == null)
	            {
	           	 throw new DAOException ("No se encontró el Turno ejecucion con el id: "+idWorkflow);
	            }
	            String itemKey = turnoEjecEnh.getIdWorkflow();
	            String notification = turnoEjecEnh.getNotificacionWF();
	            String result = (String) parametros.get(Processor.RESULT);
	            String Ncopias =(String) parametros.get(Processor.NCOPIAS);
	            Hashtable solicitud = new Hashtable();
	            solicitud.put(Processor.ITEM_KEY,itemKey);
	            solicitud.put(Processor.NOT_ID,notification);
	            solicitud.put(Processor.RESULT,result);
                    if(Ncopias == null){
                        Ncopias = "NONE";
                    }
	            solicitud.put(Processor.NCOPIAS,Ncopias);
	            //0. Obtener el turno historia con el stackpos mayor
	            TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
	            idTurnoEnh.anio=turno.getAnio();
	            idTurnoEnh.idCirculo=turno.getIdCirculo();
	            idTurnoEnh.idProceso = turno.getIdProceso();
	            idTurnoEnh.idTurno = turno.getIdTurno();
	            TurnoEnhanced turnoEnh = (TurnoEnhanced)pm.getObjectById(idTurnoEnh,true);
	            
	            if (fase.getID().equals(turnoEnh.getIdFase())){
	            	pm.currentTransaction().begin();
	            Iterator itTurnoHist = turnoEnh.getHistorials().iterator();
	            TurnoHistoriaEnhanced stackPos=null;
                    /*
                     * @author      :   Julio Alcazar
                     * @change      :   variable utilizada para guardar un historial temporal
                     * Caso Mantis  :   0006176
                     */
                    TurnoHistoriaEnhanced stackPos2=null;
	            //0.1 Obtener el último turno historia y mayor StackPos
                    /*
                     * @author      :   Henry Gómez Rocha
                     * @change      :   Se verifica que al momento de hacer la revisión final de un
                                        turno desde el rol Encargado Antiguo Sistema, dicho turno
                                        retorne el bloqueo al usuario que lo envió a Solicitar información de
                                        folios a otras dependencias (ANTIGUO SISTEMA).
                     * Caso Mantis  :   0002484
                     */
                    TurnoHistoriaEnhanced turnoHist = null;
	            while (itTurnoHist.hasNext()){
                        turnoHist = (TurnoHistoriaEnhanced)itTurnoHist.next();
                        /*
                         * @author      :   Julio Alcazar
                         * @change      :   se actualiza el codigo que retorna al usuario los turnos de registro que han sido enviado a Digitacion masiva
                                            una vez estos finalicen el proceso. Teniendo en cuenta si el turno fue reasignado antes de
                                            iniciar el proceso Calificacion CFase.CAL_CALIFICACION.
                         * Caso Mantis  :   0006176
                         */
                        if((CFase.CAL_CALIFICACION.equals(turnoHist.getFase()) && CFase.REG_REPARTO.equals(turnoHist.getFaseAnterior())) ||
                            (CFase.CAL_CALIFICACION.equals(turnoHist.getFase()) && CFase.CAL_CALIFICACION.equals(turnoHist.getFaseAnterior()))    ){
                            stackPos2 = turnoHist;
                            
                        }
                        else{
                            if (stackPos==null){
                                    stackPos = turnoHist;
                            }else if (turnoHist.getStackPos()>stackPos.getStackPos()){
                                    stackPos = turnoHist;
                            }
                        }
	            }
                    
                    /**
                     * @autor Edgar Lora
                     * @mantis 0009842
                     * @Requerimiento 053_151
                     */
                    if (!CFase.CAL_CALIFICACION.equals(stackPos.getFase()) 
                            && !CFase.REG_APROBAR_AJUSTE.equals(stackPos.getFaseAnterior()) 
                            /*
                             * @author      :   Henry Gómez Rocha
                             * @change      :   se asigana la variable temporal stackPos2 a stackPos en el caso qe sea diferente de null.
                             * Caso Mantis  :   0002484
                             */
                            && stackPos2 != null){
                        stackPos = stackPos2;
                    }
	            //UsuarioEnhanced usuarioAsig = stackPos.getUsuario();
	            UsuarioEnhanced usuarioAsig = stackPos.getUsuarioAtiende();
	            boolean isAsignarMismoUsuario = getUsuarioByUsuarioCirculo(usuarioAsig.getUsername(), turno.getIdCirculo(),pm);
	            //Se valida que el usuario este en el circulo, sino esta se pasa de fase asignandoselo a cualquiera

	            String idFase = stackPos.getFase();
	            stackPos.setStackPos(0);
	            
	            //Se avanza a cualquiera que lo pueda atender
	            if (!isAsignarMismoUsuario){
	            	Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
					Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(Determinar usuario del stack) (Pero asignado a cualquiera ya que el usuario cambio de circulo):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
					Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	        
					avanzarTurnoNuevoCualquierabyAvanzarPop(turno, fase, parametros, usuario,pm);
					return true;
	            }
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(Determinar usuario del stack):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	        
	            
	            DAOFactory fact = DAOFactory.getFactory();
	            UsuarioDAO udao = fact.getUsuarioDAO();
	            org.auriga.core.modelo.transferObjects.Usuario userAuriga = udao.consultarUsuario(usuarioAsig.getUsername());
	            EstacionDAO edao = fact.getEstacionDAO();
	            
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(consultarUsuario):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	        

	            
	            String rol = this.getRolByFase(idFase, pm);
	            org.auriga.core.modelo.transferObjects.Rol rolAuriga = new org.auriga.core.modelo.transferObjects.Rol();
	            rolAuriga.setRolId(rol);
	            List estacionesValidasUsuarioRol = new ArrayList();
	            /**
                    * @author Daniel Forero
                    * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
                    */
                    estacionesValidasUsuarioRol = edao.obtenerEstacionesUsuarioByEstadoRol(userAuriga, rolAuriga, true);

				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(obtenerEstacionesUsuario):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	            
	            
	            // Se mira que en la lista este la misma estacion con la que se califico
	            int flag = 0;
//	          Actualización de la tabla Turno Historia
	            /*TurnoHistoriaEnhanced.ID turnoHistAnterior = new TurnoHistoriaEnhanced.ID();
	            turnoHistAnterior.anio=stackPos.getAnio();
	            turnoHistAnterior.idCirculo=stackPos.getIdCirculo();
	            turnoHistAnterior.idProceso=stackPos.getIdProceso();
	            turnoHistAnterior.idTurno=stackPos.getIdTurno();
	            int indexHistoria =Integer.parseInt(stackPos.getIdTurnoHistoria()) - 1;
	            turnoHistAnterior.idTurnoHistoria = indexHistoria + "";

	            TurnoHistoriaEnhanced maxTurnoHistoria3 = (TurnoHistoriaEnhanced)pm.getObjectById(turnoHistAnterior,true);
	            */
	            Estacion estacionParaAsignar = null;

	            //Busca la estacion para asignarla al turno.
	            for (int i = 0 ; i < estacionesValidasUsuarioRol.size() && flag == 0; i++ ) {
	            	estacionParaAsignar = (Estacion) estacionesValidasUsuarioRol.get(i);
	            	if (estacionParaAsignar.getEstacionId().equals(stackPos.getIdAdministradorSAS())) {
	            		flag = 1;
	            	}
	            }

	            if (flag == 0) {
	            	//Log.getInstance().debug(JDOTurnosNuevosDAO.class,"Turno " +  turno.getIdWorkflow() + " Va a avanzar con cualquier estacion que atienda el usuario ");
                        /**
                        * @author Daniel Forero
                        * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
                        */
	            	estacionParaAsignar = edao.getEstacionByEstado(userAuriga, rolAuriga, true);
	            }
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(antes wf):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	            
	            
	            
	       	 //1. Avanzar el turno en el WF.
	       	 //Message mensajeRespuesta = this.avanzarWFTurno(solicitud);

				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(avanzar wf):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	            
	       	 
		 resultadoAvance = getDatosAvanceTurno(turno,result,usuario,pm);
           	 resultadoAvance.put(Processor.ITEM_KEY, turno.getIdWorkflow());
           	 resultadoAvance.put(Processor.NOT_ID, "1");
           	 resultadoAvance.put(Processor.RESULT, result);
                 resultadoAvance.put(Processor.NCOPIAS, Ncopias);
           	 
           	 //Si es calificacion antiguo sistema tiene su caso.
           	 String faseDestino = (String)resultadoAvance.get(Processor.FASE_DESTINO);
           	 String rolDestino = (String)resultadoAvance.get(Processor.ROL_DESTINO);
           	 
           	 resultadoAvance.put(Processor.ACTIVITY, faseDestino);
           	 resultadoAvance.put(Processor.ROL, rolDestino);
           	 
           	 //si la respuesta es END se finaliza, sino se busca la estacion con el rol y la fase.
                 String estacionAsignada=null;
           	 if (faseDestino.equals("END")) {
           		 this.actualizarInfoTurnoFinalizacion (resultadoAvance,turno.getIdWorkflow(),usuario,pm);
           	 }
	       	    
	       	    
	       	 /*********************************************/
	       	 /*   CASO BASICO: NO ES FASE DE FINALIZACION */
	       	 /*********************************************/

	       	 else
	       	 {
	               //  2. Obtener estacion a la cual se asignará el turno:
	       		 
	       		 if (estacionParaAsignar==null){
	       			 
	       			 /**
	       			  * Modifica Pablo Junio 17 2008
	       			  * SE asigna estación según paramatro Processor.SIGUIENTE_ESTACION
	       			  */
	       			 estacionAsignada=(String) parametros.get(Processor.SIGUIENTE_ESTACION);
                             /**
                              * @author Daniel Forero 
                              * @change REQ 1156 - Verificar que la estación seleccionada tenga el rol activo.
                              */
                             estacionAsignada = obtenerEstacionActiva(estacionAsignada, turno.getIdCirculo(), resultadoAvance);
	       		 }else{
	       			 estacionAsignada = estacionParaAsignar.getEstacionId();
	       		 }
	           	 
	           	 
	           	 //3. Actualizar Modelo Operativo.
	           	 //3.1 Actualizar tabla de Turno Ejecucion.
	           	 //3.2 Actualizar tabla Turno.
	           	 //3.3.Crear nuevo turno historia.
	           	 //3.4.Actualizar turno historia anteriro.
	       		 turno.setLastIdHistoria(turnoEnh.getLastIdHistoria());
	           	 this.actualizarInfoTurnoEjecucion (resultadoAvance,estacionAsignada,turno,usuario,pm);
	       		 
	       	 }
	       	    turnoEnh.setUsuarioDestino(usuarioAsig);
                    turnoEnh.setModoBloqueo(CModoBloqueo.DELEGAR_USUARIO);
	       	    pm.currentTransaction().commit();
	       	  
	        } else{
	        	return false;
	        }
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************");
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"(avanzarTurnoNuevoPop)(actualizar Modelo operativo FINAL):"+turno.getIdWorkflow()+ "=" +((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(JDOTurnosNuevosDAO.class,"\n*******************************************************\n");	            	            
	        }
	        	catch (Throwable t) {
	        		t.printStackTrace();
	            if (pm.currentTransaction().isActive()) {
	                pm.currentTransaction().rollback();
	            }
	            return false;
	         } finally {
				pm.close();
			}
			
			return true;
		}
	
	/**
     * Retorna el rol asociado a la fase
     * @param idFase
     * @param pm
     * @return
     */
    protected String getRolByFase(String idFase, PersistenceManager pm){
    	String rta=null;
    	Query query = pm.newQuery(Procesos_V.class);
    	query.declareParameters("String idFase");
    	query.setFilter("this.id_fase==idFase");
    	Collection col = (Collection)query.execute(idFase);
    	for (Iterator iter = col.iterator(); iter.hasNext();) {
    	    Procesos_V procesos_v = (Procesos_V)iter.next();
    	    rta = procesos_v.getRol();
    	}
    	return rta;
    }

    /**
     * Retorna el rol asociado a la fase
     * @param idFase
     * @param pm
     * @return
     */
    protected boolean getUsuarioByUsuarioCirculo(String nombreUsuario, String idCirculo, PersistenceManager pm){
    	boolean rta = false;
    	
    	JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

		try {
			//Obtener usuario persistente.
			UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(nombreUsuario,idCirculo, pm);
			if (usuarior != null) {
				rta = true;
			}
		}
		catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
    	return rta;
    }
    
	public boolean avanzarTurnoNuevoEliminandoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException {
        
	   	 PersistenceManager pm = AdministradorPM.getPM();
	        String notificationRespuesta = null;
	        Hashtable resultadoAvance = new Hashtable();
	        	       
	        try {
	       	 
	       	 //Obtener de la tabla TurnoEjecucion los parametros necesarios para el avance.
	       	 String idWorkflow = turno.getIdWorkflow();
	       	 TurnoEjecucionEnhancedPk idTurnoEjecucion = new TurnoEjecucionEnhancedPk();
	       	 idTurnoEjecucion.idWorkflow = idWorkflow;
	       	 
	       	 TurnoEjecucionEnhanced turnoEjecEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucion,true);
	            if (turnoEjecEnh == null)
	            {
	           	 throw new DAOException ("No se encontró el Turno ejecucion con el id: "+idWorkflow);
	            }
	            String itemKey = turnoEjecEnh.getIdWorkflow();
	            String notification = turnoEjecEnh.getNotificacionWF();
	            String result = (String) parametros.get(Processor.RESULT);
	            
	            Hashtable solicitud = new Hashtable();
	            solicitud.put(Processor.ITEM_KEY,itemKey);
	            solicitud.put(Processor.NOT_ID,notification);
	            solicitud.put(Processor.RESULT,result);
	            
	            //0. Obtener el turno historia con el stackpos mayor
	            TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
	            idTurnoEnh.anio=turno.getAnio();
	            idTurnoEnh.idCirculo=turno.getIdCirculo();
	            idTurnoEnh.idProceso = turno.getIdProceso();
	            idTurnoEnh.idTurno = turno.getIdTurno();
	            
	            TurnoEnhanced turnoEnh = (TurnoEnhanced)pm.getObjectById(idTurnoEnh,true);
	            if (fase.getID().equals(turnoEnh.getIdFase())){
	            	pm.currentTransaction().begin();
	            Iterator itTurnoHist = turnoEnh.getHistorials().iterator();
	            TurnoHistoriaEnhanced stackPos=null;
	            //0.1 Obtener el último turno historia y mayor StackPos
	            while (itTurnoHist.hasNext()){
	            	TurnoHistoriaEnhanced turnoHist = (TurnoHistoriaEnhanced)itTurnoHist.next();
	            	if (stackPos==null){
	            		stackPos = turnoHist;
	            	}else if (turnoHist.getStackPos()>stackPos.getStackPos()){
	            		stackPos = turnoHist;
	            	}
	            }
	            
	            UsuarioEnhanced usuarioAsig = stackPos.getUsuario();
	            stackPos.setStackPos(0);
	            
	            	            
	       	 //1. Avanzar el turno en el WF.
	       	 //Message mensajeRespuesta = this.avanzarWFTurno(solicitud);
	         resultadoAvance = getDatosAvanceTurno(turno,result,usuario,pm);
           	 resultadoAvance.put(Processor.ITEM_KEY, turno.getIdWorkflow());
           	 resultadoAvance.put(Processor.NOT_ID, "1");
           	 resultadoAvance.put(Processor.RESULT, result);
           	 
           	 //Si es calificacion antiguo sistema tiene su caso.
           	 String faseDestino = (String)resultadoAvance.get(Processor.FASE_DESTINO);
           	 String rolDestino = (String)resultadoAvance.get(Processor.ROL_DESTINO);
           	 
           	 resultadoAvance.put(Processor.ACTIVITY, faseDestino);
           	 resultadoAvance.put(Processor.ROL, rolDestino);
           	 
           	 //si la respuesta es END se finaliza, sino se busca la estacion con el rol y la fase.
           	 if (faseDestino.equals("END")) {
           		 this.actualizarInfoTurnoFinalizacion (resultadoAvance,turno.getIdWorkflow(),usuario,pm);
           	 } 
	       	 /*********************************************/
	       	 /*   CASO BASICO: NO ES FASE DE FINALIZACION */
	       	 /*********************************************/
	       	    
	       	 else
	       	 {
	               //  2. Obtener estacion a la cual se asignará el turno:
	       		 String estacionAsignada=this.obtenerEstacionTurno(resultadoAvance, turno.getIdCirculo());	 
	       		 	           	 
	           	 
	           	 //3. Actualizar Modelo Operativo.
	           	 //3.1 Actualizar tabla de Turno Ejecucion.
	           	 //3.2 Actualizar tabla Turno.
	           	 //3.3.Crear nuevo turno historia.
	           	 //3.4.Actualizar turno historia anteriro.
	       		  turno.setLastIdHistoria(turnoEnh.getLastIdHistoria());
	           	 this.actualizarInfoTurnoEjecucion (resultadoAvance,estacionAsignada,turno,usuario,pm);
	       		 
	       	 }
	       	    turnoEnh.setUsuarioDestino(usuarioAsig);
	            turnoEnh.setModoBloqueo(CModoBloqueo.DELEGAR_USUARIO);
	       	    pm.currentTransaction().commit();
	       	  
	        } else{
	        	return false;
	        }
	        }
	            catch (Throwable t) {
	            	t.printStackTrace();
	            if (pm.currentTransaction().isActive()) {
	                pm.currentTransaction().rollback();
	            }
	            return false;

	         } finally {
				pm.close();
			}
			
			return true;
		}
	
	
     public boolean avanzarTurnoNuevoCualquiera(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws DAOException {
        
	   	 PersistenceManager pm = AdministradorPM.getPM();
	        String notificationRespuesta = null;
	        Hashtable resultadoAvance = new Hashtable();
	        	       
	        try {
	       	 
	       	 //Obtener de la tabla TurnoEjecucion los parametros necesarios para el avance.
	       	 String idWorkflow = turno.getIdWorkflow();
	       	 TurnoEjecucionEnhancedPk idTurnoEjecucion = new TurnoEjecucionEnhancedPk();
	       	 idTurnoEjecucion.idWorkflow = idWorkflow;
	       	 
	       	 TurnoEjecucionEnhanced turnoEjecEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucion,true);
	            if (turnoEjecEnh == null)
	            {
	           	 throw new DAOException ("No se encontró el Turno ejecucion con el id: "+idWorkflow);
	            }
	            String itemKey = turnoEjecEnh.getIdWorkflow();
	            String notification = turnoEjecEnh.getNotificacionWF();
	            String result = (String) parametros.get(Processor.RESULT);
	            String estacionAsignada = (String)parametros.get(Processor.ESTACION);
	            String condicionAvance = (String)parametros.get(Processor.CONDICION_AVANCE);
	            
	            Hashtable solicitud = new Hashtable();
	            solicitud.put(Processor.ITEM_KEY,itemKey);
	            solicitud.put(Processor.NOT_ID,notification);
	            solicitud.put(Processor.RESULT,result);
	            
	            //0. Obtener el turno historia con el stackpos mayor
	            TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
	            idTurnoEnh.anio=turno.getAnio();
	            idTurnoEnh.idCirculo=turno.getIdCirculo();
	            idTurnoEnh.idProceso = turno.getIdProceso();
	            idTurnoEnh.idTurno = turno.getIdTurno();
	            TurnoEnhanced turnoEnh = (TurnoEnhanced)pm.getObjectById(idTurnoEnh,true);
	            
	            if (fase.getID().equals(turnoEnh.getIdFase())){
	            	pm.currentTransaction().begin();
	            
	       	 //1. Avanzar el turno en el WF.
	       	// Message mensajeRespuesta = this.avanzarWFTurno(solicitud);
	       	resultadoAvance = getDatosAvanceTurno(turno,result,usuario,pm);
	       	resultadoAvance.put(Processor.ITEM_KEY, turno.getIdWorkflow());
	       	resultadoAvance.put(Processor.NOT_ID, "1");
	       	resultadoAvance.put(Processor.RESULT, result);
       	 
	       	//Si es calificacion antiguo sistema tiene su caso.
	       	String faseDestino = (String)resultadoAvance.get(Processor.FASE_DESTINO);
	       	String rolDestino = (String)resultadoAvance.get(Processor.ROL_DESTINO);
       	 
	       	resultadoAvance.put(Processor.ACTIVITY, faseDestino);
	       	resultadoAvance.put(Processor.ROL, rolDestino);
       	 
	       	//si la respuesta es END se finaliza, sino se busca la estacion con el rol y la fase.
	       	if (faseDestino.equals("END")) {
	       		this.actualizarInfoTurnoFinalizacion (resultadoAvance,turno.getIdWorkflow(),usuario,pm);
	       	}
	       	
	       	/*********************************************/
	       	 /*   CASO BASICO: NO ES FASE DE FINALIZACION */
	       	 /*********************************************/
	       	    
	       	 else
	       	 {
                     
                     /**
                      * @author Daniel Forero @change REQ 1156 - Filtro de
                      * estaciones activas por rol y usuario.
                      */
                     if(estacionAsignada != null && condicionAvance != null) {
                         parametros.put(Processor.ROL, getRolByFase(condicionAvance, pm));
                         estacionAsignada = obtenerEstacionActiva(estacionAsignada, turno.getIdCirculo(), parametros);
                     }
                     
	             //  2. Obtener estacion a la cual se asignará el turno:
	       		 //La búsqueda se realiza si no viene el parámetro estación dentro del turno.
	       		 //Se valida esta condicion ademas para EL CASO REVISION ANALISIS --->REVISAR APROBAR
	       		 //En este caso condicion avance es diferente de null y no entra a la condición.
	       		 //No se pudo hacer genérico porque al parecer afecta otros procesos.
	       		 if (estacionAsignada == null && condicionAvance == null )
	       		 {
	       			estacionAsignada=this.obtenerEstacionTurno(resultadoAvance, turno.getIdCirculo());	 
	       		 }
	       		 
	       		 	           	 
	           	 
	           	 //3. Actualizar Modelo Operativo.
	           	 //3.1 Actualizar tabla de Turno Ejecucion.
	           	 //3.2 Actualizar tabla Turno.
	           	 //3.3.Crear nuevo turno historia.
	           	 //3.4.Actualizar turno historia anteriro.
	       		 turno.setLastIdHistoria(turnoEnh.getLastIdHistoria());
	           	 this.actualizarInfoTurnoEjecucion (resultadoAvance,estacionAsignada,turno,usuario,pm);
	       		 
	       	 }
	       	    turnoEnh.setUsuarioDestino(null);
	            turnoEnh.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
	       	    pm.currentTransaction().commit();
	       	  
	        } else{
	        	return false;
	        }
	        }
	            catch (Throwable t) {
	            	t.printStackTrace();
	            if (pm.currentTransaction().isActive()) {
	                pm.currentTransaction().rollback();
	            }
	            return false;
	         } finally {
				pm.close();
			}
			return true;
		}
     
     public boolean avanzarTurnoNuevoCualquierabyAvanzarPop(Turno turno, Fase fase, Hashtable parametros, Usuario usuario, PersistenceManager pm) throws DAOException {
         
	   	    //PersistenceManager pm = AdministradorPM.getPM();
	        String notificationRespuesta = null;
	        Hashtable resultadoAvance = new Hashtable();
	        	       
	        try {
	       	 
	       	 //Obtener de la tabla TurnoEjecucion los parametros necesarios para el avance.
	       	 String idWorkflow = turno.getIdWorkflow();
	       	 TurnoEjecucionEnhancedPk idTurnoEjecucion = new TurnoEjecucionEnhancedPk();
	       	 idTurnoEjecucion.idWorkflow = idWorkflow;
	       	 
	       	 TurnoEjecucionEnhanced turnoEjecEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucion,true);
	            if (turnoEjecEnh == null)
	            {
	           	 throw new DAOException ("No se encontró el Turno ejecucion con el id: "+idWorkflow);
	            }
	            String itemKey = turnoEjecEnh.getIdWorkflow();
	            String notification = turnoEjecEnh.getNotificacionWF();
	            String result = (String) parametros.get(Processor.RESULT);
	            String Ncopias = (String) parametros.get(Processor.NCOPIAS);
	            Hashtable solicitud = new Hashtable();
	            solicitud.put(Processor.ITEM_KEY,itemKey);
	            solicitud.put(Processor.NOT_ID,notification);
	            solicitud.put(Processor.RESULT,result);
                    if(Ncopias == null){
                        Ncopias = "NONE";
                    }
	            solicitud.put(Processor.NCOPIAS,Ncopias);
	            //0. Obtener el turno historia con el stackpos mayor
	            TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
	            idTurnoEnh.anio=turno.getAnio();
	            idTurnoEnh.idCirculo=turno.getIdCirculo();
	            idTurnoEnh.idProceso = turno.getIdProceso();
	            idTurnoEnh.idTurno = turno.getIdTurno();
	            TurnoEnhanced turnoEnh = (TurnoEnhanced)pm.getObjectById(idTurnoEnh,true);
	            
	            if (fase.getID().equals(turnoEnh.getIdFase())){
//	            	pm.currentTransaction().begin();
	            
	       	 //1. Avanzar el turno en el WF.
	       	 //Message mensajeRespuesta = this.avanzarWFTurno(solicitud);
	       	resultadoAvance = getDatosAvanceTurno(turno,result,usuario,pm);
	       	resultadoAvance.put(Processor.ITEM_KEY, turno.getIdWorkflow());
	       	resultadoAvance.put(Processor.NOT_ID, "1");
	       	resultadoAvance.put(Processor.RESULT, result);
         	resultadoAvance.put(Processor.NCOPIAS,Ncopias);
	       	//Si es calificacion antiguo sistema tiene su caso.
	       	String faseDestino = (String)resultadoAvance.get(Processor.FASE_DESTINO);
	       	String rolDestino = (String)resultadoAvance.get(Processor.ROL_DESTINO);
       	 
	       	resultadoAvance.put(Processor.ACTIVITY, faseDestino);
	       	resultadoAvance.put(Processor.ROL, rolDestino);
       	 
	       	//si la respuesta es END se finaliza, sino se busca la estacion con el rol y la fase.
	       	if (faseDestino.equals("END")) {
	       		this.actualizarInfoTurnoFinalizacion (resultadoAvance,turno.getIdWorkflow(),usuario,pm);
	       	}
	       	    
	       	 /*********************************************/
	       	 /*   CASO BASICO: NO ES FASE DE FINALIZACION */
	       	 /*********************************************/
	       	    
	       	 else
	       	 {
	               //  2. Obtener estacion a la cual se asignará el turno:
	       		 String estacionAsignada=this.obtenerEstacionTurno(resultadoAvance, turno.getIdCirculo());	 
	       		 	           	 
	           	 
	           	 //3. Actualizar Modelo Operativo.
	           	 //3.1 Actualizar tabla de Turno Ejecucion.
	           	 //3.2 Actualizar tabla Turno.
	           	 //3.3.Crear nuevo turno historia.
	           	 //3.4.Actualizar turno historia anteriro.
	       		 turno.setLastIdHistoria(turnoEnh.getLastIdHistoria());
	           	 this.actualizarInfoTurnoEjecucion (resultadoAvance,estacionAsignada,turno,usuario,pm);
	       		 
	       	 }
	       	    turnoEnh.setUsuarioDestino(null);
	            turnoEnh.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
	       	    pm.currentTransaction().commit();
	       	  
	        } else{
	        	return false;
	        }
	        }
	            catch (Throwable t) {
	            	t.printStackTrace();
	            if (pm.currentTransaction().isActive()) {
	                pm.currentTransaction().rollback();
	            }
	            return false;
	         } finally {
				pm.close();
			}
			return true;
		}
     
	/* (non-Javadoc)
	 * @see gov.sir.hermod.dao.TurnosNuevosDAO#actualizarTurnoEjecucion(gov.sir.core.negocio.modelo.TurnoEjecucion)
	 */
	public boolean actualizarTurnoEjecucion(TurnoEjecucion turnoEjecucion) throws DAOException {

		
		PersistenceManager pm = AdministradorPM.getPM();

		try{
			pm.currentTransaction().begin();

			if(turnoEjecucion==null||turnoEjecucion.getIdWorkflow()==null){
				throw new DAOException("No se especifico un turno para actualizar.");
			}

			
			//SE CAPTURA EL TURNO
			TurnoEjecucionEnhancedPk tid = new TurnoEjecucionEnhancedPk();
			tid.idWorkflow = turnoEjecucion.getIdWorkflow();
			TurnoEjecucionEnhanced turnoEjecucionEnhanced = (TurnoEjecucionEnhanced) pm.getObjectById( tid, true);

			if (turnoEjecucionEnhanced == null) {
				throw new DAOException("No se encontró el turno.");
			}			

			if(turnoEjecucion.getEstacion()!=null) {
				turnoEjecucionEnhanced.setEstacion(turnoEjecucion.getEstacion());	
			}
			if(turnoEjecucion.getFase()!=null) {
				turnoEjecucionEnhanced.setFase(turnoEjecucion.getFase());	
			}
			if(turnoEjecucion.getNotificacionWF()!=null) {
				turnoEjecucionEnhanced.setNotificacionWF(turnoEjecucion.getNotificacionWF());	
			}			
			if(turnoEjecucion.getEstado()!=null) {
				turnoEjecucionEnhanced.setEstado(turnoEjecucion.getEstado());	
			}

			pm.makePersistent(turnoEjecucionEnhanced);

			//TERMINAR TRANSACCION
			pm.currentTransaction().commit();

			return true;
		 }

		 catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		 }catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		}catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		 }
		 finally {
			pm.close();
		 }

	 }	
	
	   public boolean getTurnoEjecucionTurnoIndividual(Estacion estacion, Fase fase , Circulo circulo, String idworkflow)   throws DAOException {
	        // Estrategia de cambios
	        // 1. Se consultan los turnos en la tabla TurnoEjecucion, donde la estación y la fase sean las recibidas
	        //    como parámetros.
	        // 2. Se valida la sincronización de estos turnos con el wf.
	      	PersistenceManager pm = AdministradorPM.getPM();
	  		
	  		pm.currentTransaction().begin();
	  		
	  		
	           String estacionSt = estacion.getEstacionId();
	           String faseSt     = fase.getID();
	           boolean rta = false;
	  		
	      	 try {
	 
	      		 Query query = pm.newQuery(TurnoEjecucionEnhanced.class);
	      		 Collection col = null;    		 
	       		 query.declareParameters("String estacion, String fase, String idWork");
	       		 query.setFilter("this.idWorkflow == idWork && " +
	          		 		"this.fase == fase && " +
	          		 		"this.estacion == estacion");
	              col = (Collection) query.execute(estacionSt, faseSt, idworkflow);
	      			 
	      		 Iterator iter = col.iterator();

	             while (iter.hasNext()) {
	            	 TurnoEjecucionEnhanced TurnoEjecucionEnhanced = (TurnoEjecucionEnhanced)iter.next();
	            	 rta = true;
	             }
	             
	             pm.currentTransaction().commit();
	   			 query.closeAll();
	           } catch (Throwable e) {
	        	   if (pm.currentTransaction().isActive()) {
	                   pm.currentTransaction().rollback();
	               }
	               throw new DAOException(e.getMessage(), e);
	           } finally {
	               pm.close();
	           }
	           return rta;
	    }

	

 	/**
 	 * Método que permite actualizar la información en las tablas del modelo operativo cuando se requiere 
 	 * ejecutar la reanotación de un turno de registro de documentos.
 	 * @param idTurno
 	 * @param notificationId
 	 * @param fase
 	 * @param resultado
 	 * @param estacionAsignada
 	 * @param usuarioSir
 	 * @return
 	 * @throws DAOException
 	 */
 	public boolean reanotarTurnoModeloOperativo(String idTurno, String notificationId, String fase, String resultado, String estacionAsignada, Usuario usuarioSir) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();

		//Obtener del mensaje los atributos necesarios para la creación del registro:
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
		TurnoEnhanced turnoPersistente = null;
		TurnoEjecucionEnhanced turnoEjecucionEnh = null;
		TurnoHistoriaEnhanced turnoHistoriaEnh = null;

		try {
			pm.currentTransaction().begin();

			//Obtener usuario persistente.
			UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuarioSir.getUsername(), pm);
			if (usuarior == null) {
				throw new DAOException("No encontró el Usuario con el login: " + usuarioSir.getUsername());
			}

			//Obtener el turno ejecucion asociado.
			TurnoEjecucionEnhancedPk idTurnoEjecucionEnh = new TurnoEjecucionEnhancedPk();
			idTurnoEjecucionEnh.idWorkflow = idTurno;

			try {
				turnoEjecucionEnh = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucionEnh, true);
			}

			catch (JDOObjectNotFoundException e) {
				throw new DAOException("No se encontró el turno con el identificador: " + idTurno);
			}

			if (turnoEjecucionEnh == null) {
				throw new DAOException("No se encontró el turno ejecucion con el id: " + idTurno);
			}

			//Actualizar los atributos del turno ejecucion.
			turnoEjecucionEnh.setEstacion(estacionAsignada);
			turnoEjecucionEnh.setNotificacionWF(notificationId);
			turnoEjecucionEnh.setEstado("1");
			turnoEjecucionEnh.setFase(fase);

			//Actualización del Turno Historia Anterior.
			Turno turno = turnosDAO.getTurnoByWFId(idTurno);
			TurnoHistoriaEnhancedPk idAnterior = new TurnoHistoriaEnhancedPk();
			idAnterior.anio = turno.getAnio();
			idAnterior.idCirculo = turno.getIdCirculo();
			idAnterior.idProceso = turno.getIdProceso();
			idAnterior.idTurno = turno.getIdTurno();
			idAnterior.idTurnoHistoria = turno.getLastIdHistoria() + "";

			try {
				turnoHistoriaEnh = (TurnoHistoriaEnhanced) pm.getObjectById(idAnterior, true);
			}

			catch (JDOObjectNotFoundException e) {
				throw new DAOException("No encontró el último turno historia para el turno con el identificador: " + idTurno);
			}
			if (turnoEjecucionEnh == null) {
				throw new DAOException("No se encontró el último turno historia para el turno con el id: " + idTurno);
			}
			turnoHistoriaEnh.setActivo(false);
			turnoHistoriaEnh.setRespuesta(resultado);
			turnoHistoriaEnh.setUsuarioAtiende(usuarior);

			String faseAnterior = turnoHistoriaEnh.getFase();

			//Creación del objeto turno historia para la fase en la que queda asociado el turno

			TurnoHistoria nuevoTurnoHistoria = new TurnoHistoria();
			nuevoTurnoHistoria.setIdCirculo(turno.getIdCirculo());
			nuevoTurnoHistoria.setAnio(turno.getAnio());
			nuevoTurnoHistoria.setIdTurno(turno.getIdTurno());
			nuevoTurnoHistoria.setIdProceso(turno.getIdProceso());
			nuevoTurnoHistoria.setIdTurnoHistoria((turno.getLastIdHistoria() + 1) + "");
			nuevoTurnoHistoria.setFase(fase);
			nuevoTurnoHistoria.setIdAdministradorSAS(estacionAsignada);
			nuevoTurnoHistoria.setFecha(new Date());
			nuevoTurnoHistoria.setActivo(true);
			nuevoTurnoHistoria.setRespuesta(resultado);
			nuevoTurnoHistoria.setFaseAnterior(faseAnterior);

			TurnoHistoriaEnhanced nuevoTurnoHistoriaEnhanced = TurnoHistoriaEnhanced.enhance(nuevoTurnoHistoria);
			nuevoTurnoHistoriaEnhanced.setUsuario(usuarior);

			//Construcción del Turno.ID para obtener el turno persistente.
			TurnoEnhancedPk idTurnoEnh = new TurnoEnhancedPk();
			idTurnoEnh.anio = turno.getAnio();
			idTurnoEnh.idCirculo = turno.getIdCirculo();
			idTurnoEnh.idProceso = turno.getIdProceso();
			idTurnoEnh.idTurno = turno.getIdTurno();

			try {
				turnoPersistente = (TurnoEnhanced) pm.getObjectById(idTurnoEnh, true);
			} catch (JDOObjectNotFoundException e) {
				throw new DAOException("No encontró el Turno con el identificador: " + turno.getIdWorkflow());
			}

			turnoPersistente.setLastIdHistoria(turnoPersistente.getLastIdHistoria() + 1);
			turnoPersistente.setIdFase(fase);
			turnoPersistente.setUsuarioDestino(usuarior);
			turnoPersistente.addHistorial(nuevoTurnoHistoriaEnhanced);
			turnoPersistente.setUltimaRespuesta(resultado);
			
			if (fase.equals(CFase.CER_ESPERA_IMPRIMIR)) {
				turnoPersistente.setFechaFin(null);
			}

			//TERMINAR TRANSACCION
			pm.currentTransaction().commit();

			return true;

		}

		catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		}catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}	
	
    /**
     * @author: Daniel Forero
     * @change: 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS. 
     * 
     * @see gov.sir.hermod.dao.TurnosNuevosDAO#desbloquearFolios(gov.sir.core.negocio.modelo)
     */
    public int desbloquearFolios(Turno turno) throws DAOException {
        int foliosLiberados = 0;
        
        PersistenceManager pm = null;
        Transaction tx = null;
        
        try {
            pm = AdministradorPM.getPM();
            tx = pm.currentTransaction();
            tx.setOptimistic(false);
            
            tx.begin();
            foliosLiberados = desbloquearFolios(turno, pm);
            tx.commit();
            
            return foliosLiberados;
        } finally {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            if(pm != null && !pm.isClosed()){
                pm.close();
            }
        }
    }

    /**
     * 
     * @author: Daniel Forero
     * @change: 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS. 
     * 
     * Permite desbloquear todos los folios asociados a un turno. La liberación
     * de folios no tiene en cuenta el estado del turno, su fase actual, o el
     * usuario al que le pertenencen los bloqueos.
     *
     * @param turno El <code>Turno</code> cuyos folios van a ser liberados.
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @return El número de folios del turno liberados.
     * @throws DAOException Si ocurre algún error durante la actualización de
     * los bloqueos.
     */
    private int desbloquearFolios(Turno turno, PersistenceManager pm) throws DAOException {
        String sqlUpdate = "UPDATE SIR_REL_BLOQUEO_FOLIO SET blfl_fecha_desbloqueo = SYSDATE WHERE blfl_fecha_desbloqueo IS NULL AND blfl_id_workflow = ?";
        VersantPersistenceManager vpm = (VersantPersistenceManager) pm;
        Connection connection = vpm.getJdbcConnection(null);
        PreparedStatement ps = null;
        
        try {

            ps = connection.prepareStatement(sqlUpdate);
            ps.setString(1, turno.getIdWorkflow());
            
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if(connection != null){
                    if(ps != null){
                        ps.close();
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosNuevosDAO.class,e);
            }
        }
    }     
        
    /**
     * @author Daniel Forero
     * @change REQ 1156 - Verificar que la estación seleccionada tenga el rol activo.
     *
     * Permite seleccionar una estacion que cumpla con dos condiciones: tener el rol activo, 
     * y tener al menos un usuario activo asociado a la estación.
     * 
     * @param estacionId identificador de la estacion que será verificada. Si la estación no existe, 
     * es indefinida, o no cumple con las condiciones, se retorna una nueva estación segun el rol y 
     * el circulo.
     * @param circuloId identificador del circulo al cual pertenece debe pertener el rol.
     * @param parametros contiene la información necesaria para obtener la estación.
     * @return Retorna el identificador de la estación que cumple con las siguientes condiciones: tener 
     * el rol activo, y tener al menos un usuario activo asociado a la estación.
     * @throws DAOException Si ocurre un error inesperado durante la verificación de la estación.
     */
    private String obtenerEstacionActiva(String estacionId, String circuloId, Hashtable parametros) throws DAOException {

        String rolId = (String) parametros.get(Processor.ROL);
        
        Estacion estacion = new Estacion();
        estacion.setEstacionId(estacionId);
        
        Rol rol = new Rol();
        rol.setRolId(rolId);
        
        try {
            RolDAO rolDAO = DAOFactory.getFactory().getRolDAO();

            if (estacionId == null || estacionId.trim().isEmpty() || !rolDAO.estadoRelRolEstacion(rol, estacion)) {
                return obtenerEstacionTurno(parametros, circuloId);
            } else {
                return estacionId;
            }
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones con el rol " + rolId + " activo: " + e.getMessage());
        }
    }
}
