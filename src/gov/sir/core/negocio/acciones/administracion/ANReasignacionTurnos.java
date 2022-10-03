package gov.sir.core.negocio.acciones.administracion;
 /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  importar generalSir
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnReasignacionTurnos;
import gov.sir.core.eventos.administracion.EvnRespReasignacionTurnos;
import gov.sir.core.negocio.acciones.excepciones.ANReasignacionTurnosException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaTurnoExceptionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.BloqueoFolio;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;


/**
 * Acción de negocio encargada de manejar los eventos de tipo 
 * <code>EvnReasignacionTurnos</code> destinados a manejar 
 * la reasignación de turnos de un funcionario a otro.  
 * 
 * @author ppabon
 *
 */
public class ANReasignacionTurnos extends SoporteAccionNegocio {

	/**
	* Usuario SAS de Auriga
	*/ 	
	public static final String USUARIO_ADMINISTRATIVO = "SAS_SISTEMA";	
	
	/**
	* El tipo de mensaje de la actividad sobre la cual se va a
	* consultar el resultado.
	*/ 
	private final String ACTIVITY_RESULT   = "MSG_CAL_CALIFICACION";
	
		

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;
	
	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;	
	

	/**
	 *Constructor de la clase <code>ANRecursosRevocatorias</code>.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANReasignacionTurnos() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
			}
			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
			
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

	}

	/**
	 * Manejador de eventos de tipo <code>EvnReasignacionTurnos</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento 
	 * que llega como parámetro.
	 * 
	 * @param evento <code>EvnReasignacionTurnos</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 * 
	 * @return <code>EventoRespuesta</code> con la información resultante de la 
	 * ejecución de la acción sobre los servicios
	 * 
	 * @throws <code>EventoException</code> 
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnReasignacionTurnos evento = (EvnReasignacionTurnos) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnReasignacionTurnos.CARGAR_USUARIOS)) {
			return cargarUsuarios(evento);
		} else if (tipoEvento.equals(EvnReasignacionTurnos.CARGAR_ROLES)) {
			return cargarRoles(evento);
		} else if (tipoEvento.equals(EvnReasignacionTurnos.CARGAR_TURNOS)) {
			return cargarTurnos(evento);
		} else if (tipoEvento.equals(EvnReasignacionTurnos.CARGAR_ESTACIONES)) {
			return cargarEstaciones(evento);
		} else if (tipoEvento.equals(EvnReasignacionTurnos.REASIGNAR_TURNOS)) {
			return reasignarTurnos(evento);
		} else if (tipoEvento.equals(EvnReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO)) {
			return reasignarTurnosForzoso(evento);
		} else if (tipoEvento.equals(EvnReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES)) {
			return reasignarTurnosTemporales(evento);
		}			

		return null;
	}

	/**
	 * Este método se encarga consultar los usuario del circulo para permitir seleccionar a cuál
	 * se le quiere reasignar sus turnos.
	 * 
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> con la información del circulo al que se le quiere consultar los usuarios.
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información de los usuarios del circulo.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos cargarUsuarios(EvnReasignacionTurnos evento) throws EventoException {

		Circulo circulo = evento.getCirculo();
		List rta = null;
                List listaNotasInformativas = null;
                Proceso proceso = new Proceso();
                proceso.setIdProceso(40);
                ProcesoPk procesoId = new ProcesoPk();
                procesoId.idProceso = proceso.getIdProceso();
                String nombreFase = "ADMINISTRATIVO";
                
                
		try {
			rta = fenrir.consultarUsuariosPorCirculo(circulo);
                        listaNotasInformativas = hermod.getTiposNotaProceso(procesoId,nombreFase);
                        proceso = hermod.getProcesoById(procesoId);
		} catch (HermodException e) {
			throw new ConsultaTurnoExceptionException("No se pudo obtener la información de los usuarios del circulo", e);
		} catch (Throwable e) {
			throw new ConsultaTurnoExceptionException(e.getMessage(), e);
		}
                
		EvnRespReasignacionTurnos evn = new EvnRespReasignacionTurnos(rta, EvnRespReasignacionTurnos.CARGAR_USUARIOS);
                evn.setListaNotasInformativas(listaNotasInformativas);
                evn.setProceso(proceso);
		return evn;
	}
	
	
	/**
	 * Este método se encarga consultar los roles que tiene un usuario para luego poder consultar los turnos que tiene asignados
	 * 
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> con la información del usuario al que se le quiere consultar los roles.
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información de los roles del usuario.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos cargarRoles(EvnReasignacionTurnos evento) throws EventoException {

		Circulo circulo = evento.getCirculo();
		Usuario usuarioOrigen = evento.getUsuarioOrigen();
		List rta = null;

		try {
			rta = fenrir.darRolUsuario(usuarioOrigen.getIdUsuario());
		} catch (HermodException e) {
			throw new ConsultaTurnoExceptionException("No se pudo obtener la información de los roles del usuario.", e);
		} catch (Throwable e) {
			throw new ConsultaTurnoExceptionException(e.getMessage(), e);
		}

		EvnRespReasignacionTurnos evn = new EvnRespReasignacionTurnos(rta, EvnRespReasignacionTurnos.CARGAR_ROLES);
		return evn;
	}
	
	
	/**
	 * Este método se encarga consultar los turnos que tiene un usuario en un rol
	 * 
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> con la información del usuario al que se le quiere consultar los roles.
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información de los turnos que tiene en el rol.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos cargarTurnos(EvnReasignacionTurnos evento) throws EventoException {

		Circulo circulo = evento.getCirculo();
		Usuario usuarioOrigen = evento.getUsuarioOrigen();
		Rol rol = evento.getRol();
		List turnos = null;
		List posiblesUsuarios = null;
		List posiblesEstaciones = null;
		
		/*Estacion estacion = new Estacion();
		estacion.setEstacionId("X-DANIEL.DIAZ");
		
		Fase fase = new Fase ();
		fase.setID("CAL_CALIFICACION");*/
		
		Proceso proceso = new Proceso();
		proceso.setIdProceso(new Long (CProceso.PROCESO_REGISTRO).longValue());

		try {
			UsuarioPk uid = new UsuarioPk();
			uid.idUsuario = usuarioOrigen.getIdUsuario();
			Usuario user = fenrir.getUsuarioById(uid);
			
			//01. SE CARGAN LOS TURNOS QUE TIENE EL USUARIO ASIGNADOS A UN ROL DETERMINADO
			//turnos = hermod.getTurnosByUsuarioRol(circulo, user, rol);
			turnos = determinarTurnosUsuario(circulo, user, rol);
			
			//02. SE CARGAN LOS USUARIOS QUE PODRÍAN RECIBIR DICHOS TURNOS
                        /**
                        * @author Daniel Forero
                        * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
                        */
			posiblesUsuarios = fenrir.consultarUsuariosPorCirculoRolByEstado(circulo , rol, true);
			
			if(posiblesUsuarios!=null && posiblesUsuarios.size()==1){
				uid.idUsuario = ((Usuario)posiblesUsuarios.get(0)).getIdUsuario();
                                posiblesEstaciones = fenrir.getEstacionesUsuarioByEstadoRol(fenrir.getUsuarioById(uid),rol,true);
				if(posiblesEstaciones!=null&&posiblesEstaciones.isEmpty()){
					posiblesEstaciones =null;
				}
			}
			
		} catch (HermodException e) {
			throw new ConsultaTurnoExceptionException("No se pudo obtener la información de los turnos asignados al usuario para este rol.", e);
		} catch (ANReasignacionTurnosException re) {
			throw re;
		}catch (Throwable e) {
			throw new ConsultaTurnoExceptionException(e.getMessage(), e);
		}

		EvnRespReasignacionTurnos evn = new EvnRespReasignacionTurnos(turnos, EvnRespReasignacionTurnos.CARGAR_TURNOS);
		evn.setListaPosiblesUsuarios(posiblesUsuarios);
		evn.setListaPosiblesEstaciones(posiblesEstaciones);
		return evn;
	}
	
	/**
	 * Método que determina los turnos que tiene un usuario en un rol determinado.
	 */
	private List determinarTurnosUsuario(Circulo circulo, Usuario user, Rol rol)throws ANReasignacionTurnosException{
		List turnos = new ArrayList();
		
		try{
			//DETERMINAR LAS ESTACIONES QUE TIENE UN USUARIO EN UN ROL
			//DETERMINAR LOS PROCESOS EN DONDE PUEDEN HABER TURNOS PARA EL ROL
			List estaciones = fenrir.darEstacionUsuario(user.getIdUsuario() , rol);
			List procesos = hermod.getProcesos(rol.getRolId());
			List fases = new ArrayList();
			
			//SE OBTIENEN TODAS LAS FASES DE CADA PROCESO DONDE ESTE ASOCIADO EL ROL
			boolean agregar = true;
			Iterator itProcesos = procesos.iterator();
			while (itProcesos.hasNext()){
				Proceso proceso = (Proceso)itProcesos.next();
				List fasesTemp = hermod.getFases(rol , proceso);
				for(Iterator iterAux=fasesTemp.iterator();iterAux.hasNext();){
					Fase faseAux=(Fase)iterAux.next();
					agregar = true;
					for(Iterator iterAux2=fases.iterator();iterAux2.hasNext();){
						Fase faseAux2=(Fase)iterAux2.next();
						if(faseAux.getID().equals(faseAux2.getID())){
							agregar = false;
							break;
						}
					}
					if(agregar){
						agregar = true;
						fases.add(faseAux);
					}
				}
			}
			
			//PARA CADA FASE CON CADA ESTACIÓN SE DETERMINAN LOS TURNOS Y SE AGREGAN A LA LISTA.
			Iterator itFases = fases.iterator();
			Iterator itEstaciones = null;
			Proceso p = new Proceso();
			p.setIdProceso(6);
			
			while(itFases.hasNext()){
				
				Fase fase = (Fase)itFases.next();
				
				itEstaciones = estaciones.iterator();
				while(itEstaciones.hasNext()){

					Estacion estacion = (Estacion)itEstaciones.next();
					
					List turnosTemp =  hermod.getTurnosAReasignar(estacion, fase);
					if(turnosTemp!=null){
						turnos.addAll(turnosTemp);
					}
					
				}
				
			}
			
		}catch(Throwable t){
			throw new ANReasignacionTurnosException("No fue posible determinar los turnos que tiene el funcionario.");
		}
		
		//SE OBTIENE LA DESCRIPCIÓN DE LA FASE PARA NO MOSTRAR AL USUARIO EL ID.
		Iterator itTurnos = turnos.iterator();
		while (itTurnos.hasNext()){

			Turno turno = (Turno)itTurnos.next();			
			if(turno!=null &&turno.getHistorials().size()==1){
				TurnoHistoria th = (TurnoHistoria)turno.getHistorials().get(0);
				try{
					Fase fase = hermod.getFase(th.getFase());
					String nombreFase = fase.getNombre();   
					th.setNombreFase(nombreFase);
				}catch(Throwable e){					
				}
			}
			
		}
		
		return turnos;
	}
	
	
	/**
	 * Este método se encarga consultar las estaciones que tiene un usuario en un rol determinado
	 * 
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> con el usuario y rol a consultar.
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información de las estaciones que tiene asignadas un usuario en un rol.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos cargarEstaciones(EvnReasignacionTurnos evento) throws EventoException {

		Usuario usuarioDestino = evento.getUsuarioDestino();
		Rol rol = evento.getRol();
		List rta = null;

		try {
			UsuarioPk idUsuario = new UsuarioPk();
			idUsuario.idUsuario = usuarioDestino.getIdUsuario();
			Usuario uDestino = fenrir.getUsuarioById(idUsuario);
                        /**
                        * @author Daniel Forero
                        * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
                        */
                        rta = fenrir.getEstacionesUsuarioByEstadoRol(uDestino,rol,true);
			
			if(rol.getRolId().equals(CRoles.SIR_ROL_CALIFICADOR)){
				List aux = new ArrayList();
				for(int i=0;i<rta.size();i++){
					Estacion estacion = (Estacion)rta.get(i);
					if(estacion.getEstacionId().substring(2).equals(uDestino.getUsername())){
						aux.add(estacion);
						break;
					}
				}
				rta = aux;
			}
		} catch (HermodException e) {
			throw new ConsultaTurnoExceptionException("No se pudo obtener la información de las estaciones que tiene el usuario a asignar los turnos para el rol.", e);
		} catch (Throwable e) {
			throw new ConsultaTurnoExceptionException(e.getMessage(), e);
		}

		EvnRespReasignacionTurnos evn = new EvnRespReasignacionTurnos(rta, EvnRespReasignacionTurnos.CARGAR_ESTACIONES);
		return evn;
	}	
	
		
	/**
	 * Este método se encarga de reasignar los turnos que tiene un usuario en un rol determinado.
	 * 
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> 
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información concertiente 
	 * a si se pudo reanotar o no los recursos y revocatorias directas.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos reasignarTurnos(EvnReasignacionTurnos evento) throws EventoException {

		Estacion estacion = evento.getEstacion();
		Usuario usuario = evento.getUsuarioSIR();
		Usuario usuarioDestino = evento.getUsuarioDestino();
		Usuario usuarioOrigen = evento.getUsuarioOrigen();
		List turnosAReasignar =  evento.getTurnos();
		Hashtable turnosNoReasignados = new Hashtable();
		List turnosReasignados = new ArrayList();
		
		if(turnosAReasignar==null){
			turnosAReasignar = new ArrayList();
		}

		try {
			
			Iterator itTurnos = turnosAReasignar.iterator();			
			while(itTurnos.hasNext()){
				 
				//01. SE VERIFICA QUE LAS MATRÍCULAS ASOCIADAS NO ESTÉN BLOQUEADAS.
				String idWorkflow = (String) itTurnos.next();
				Turno turno = hermod.getTurnobyWF(idWorkflow);				 
				Solicitud solicitud = turno.getSolicitud();
				
				List solicitudesFolio = solicitud.getSolicitudFolios();
				
				if(solicitudesFolio==null){
					solicitudesFolio = new ArrayList();					
				}
				
				Iterator it = solicitudesFolio.iterator();
				boolean bloqueado = false;
				boolean hasDatosTMP = false;				
				
				while(it.hasNext() ){
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					Folio folio = solFolio.getFolio();
					FolioPk fid = new FolioPk();
					fid.idMatricula = folio.getIdMatricula();
	
					try{
						Usuario usuarioBloqueo = forseti.getBloqueoFolio(fid);

						if(usuarioBloqueo!=null){
							if(usuarioBloqueo.getIdUsuario()== usuarioOrigen.getIdUsuario() || turno.getIdFase().equals(CFase.COR_REVISAR_APROBAR)
									|| turno.getIdFase().equals(CFase.COR_REVISION_ANALISIS)){								
								
								if(!bloqueado){
									bloqueado = true;	
								}
								
								if(forseti.hasDatosTemporalesFolio(fid) ){
									//SI EL TURNO TIENE DATOS TEMPORALES SE MARCA EL FLAG Y SE SALE DEL CICLO
									hasDatosTMP = true;
									continue;									
								}
								
							}
							else if (usuarioBloqueo.getLlavesBloqueos() != null && usuarioBloqueo.getLlavesBloqueos().size() == 1){
								List bloqueosFolio = ((LlaveBloqueo)usuarioBloqueo.getLlavesBloqueos().get(0)).getBloqueoFolios();
								if (bloqueosFolio != null && bloqueosFolio.size() > 0)
								{
									BloqueoFolio bloqueoFolio = (BloqueoFolio)bloqueosFolio.get(0);
									if (bloqueoFolio.getIdWorkflowBloqueo().equals(turno.getIdWorkflow()))
									{
										bloqueado = true;
									}
								}
						} 
						}
					}catch(Throwable e){
					}
				}
				
				//SI EL TURNO TIENE DATOS TEMPORALES EN ALGUNO DE LOS FOLIOS SE SIGUE CON EL SIGUIENTE
				//TURNO Y EL ACTUAL NO SE REASIGNA.
/*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion para comprovar si el turno de testamento tiene datos temporales
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/                                
                                ValidacionesSIR valSir = new ValidacionesSIR();
                                Turno turnoAnterior =solicitud.getTurnoAnterior();
                                if(turno.getIdProceso()==3 && turnoAnterior!=null && turnoAnterior.getIdProceso()==6){
                                    if(valSir.isTestamentoTMP(turnoAnterior.getSolicitud().getIdSolicitud()))
                                    {
                                        hasDatosTMP = true;
                                    }
                                }
				if(hasDatosTMP){
					turnosNoReasignados.put(turno.getIdWorkflow(), "Uno o Varios de los Folios Asociados al Turno Tienen Datos Temporales que impiden su reasignacion a otro Usuario");
					continue;
				}
                                
                                if(hermod.getLimiteReasignacion(turno) >= hermod.getReasignacion()){
                                        turnosNoReasignados.put(turno.getIdWorkflow(),"El turno ha alcanzado el limite máximo de reasignaciones ("+hermod.getReasignacion()+")"); 
                                        continue;
                                }
				
				//SI EL TURNO TENÍA BLOQUEADOS FOLIOS PERO ESTOS NO TENÍAN DATOS TEMPORALES SE INTENTA BLOQUEAR
				//LOS FOLIOS CON EL NUEVO USUARIO. SI FALLA SE COLOCA EN LA LISTA DE TURNOS NO REASIGNADOS
				if(bloqueado){
					
					boolean continuarReasignacionTurno = true;
					
					it = solicitudesFolio.iterator();
					while (it.hasNext()) {
						SolicitudFolio solFolio = (SolicitudFolio) it.next();
						Folio folio = solFolio.getFolio();
						FolioPk fid = new FolioPk();
						fid.idMatricula = folio.getIdMatricula();
						
						Usuario usuarioBloqueo = null;

						try {
							usuarioBloqueo = forseti.getBloqueoFolio(fid);

							if (usuarioBloqueo != null) {
								// SI EL USUARIO ORIGEN ES EL DUEÑO DEL BLOQUEO SE INTENTA DESBLOQUEAR
								// SINO ES, NO SE DEBE CONTINUAR CON LA REASIGNACIÓN DEL TURNO
								boolean reglasBloqueosOk = false;
								if (usuarioBloqueo.getIdUsuario() == usuarioOrigen.getIdUsuario()) {
									forseti.desbloquearFolio(folio);
									//folio
									List idMatriculas = new ArrayList();
									idMatriculas.add(folio.getIdMatricula());
									TurnoPk turnoID = new TurnoPk();
									turnoID.anio = turno.getAnio();
									turnoID.idCirculo = turno.getIdCirculo();
									turnoID.idProceso = turno.getIdProceso();
									turnoID.idTurno = turno.getIdTurno();
									forseti.bloquearFolios(idMatriculas, usuarioDestino, turnoID);
									reglasBloqueosOk = true;
								} else if (usuarioBloqueo.getLlavesBloqueos() != null 
										&& usuarioBloqueo.getLlavesBloqueos().size() == 1)
								{
									List bloqueosFolio = ((LlaveBloqueo)usuarioBloqueo.getLlavesBloqueos().get(0)).getBloqueoFolios();
									if (bloqueosFolio != null && bloqueosFolio.size() > 0)
									{
										BloqueoFolio bloqueoFolio = (BloqueoFolio)bloqueosFolio.get(0);
										if (bloqueoFolio.getIdWorkflowBloqueo().equals(turno.getIdWorkflow()))
										{
											turno.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
											hermod.actualizarTurnoModoBloqueo(turno);
											reglasBloqueosOk = true;
										}
									}
								}if (!reglasBloqueosOk) {
									turnosNoReasignados.put(turno.getIdWorkflow(),
													"Error al desbloquear los folios, el usuario "
															+ (usuarioOrigen != null && usuarioOrigen.getUsername() != null ? usuarioOrigen.getUsername() : "origen")
															+ " no es el dueño del bloqueo. ");
									continuarReasignacionTurno = false;
									break;
								}
							}
						} catch (Throwable e) {
							//OCURRIÓ ALGÚN PROBLEMA AL QUITAR EL BLOQUEO DEL FOLIO,
							//NO SE DEBE CONTINUAR CON LA REASIGNACIÓN DEL TURNO
							turnosNoReasignados.put(turno.getIdWorkflow(), "Error al desbloquear los folios. "+ e.getMessage());
							continuarReasignacionTurno = false;
							break;
						}
					}								

					//SI OCURRIÓ ALGÚN ERROR INTENTANDO DESBLOQUEAR EL FOLIO SE SALE DE LA OPERACIÓN
					//DE REASIGNACIÓN DEL TURNO ACTUAL.
					if (!continuarReasignacionTurno){
						continue;
					}

				}

				// 02. SE ACTUALIZA LA ESTACIÓN QUE TIENE ASIGNADO UN TURNO.
				hermod.reasignarTurno(turno, estacion);

				// 03. SE ACTUALIZA EL TURNO HISTORIA.
				List historial = turno.getHistorials();
				Iterator itHistoria = historial.iterator();
				
				UsuarioPk uid = new UsuarioPk();
				uid.idUsuario = usuarioDestino.getIdUsuario();
				Usuario user = fenrir.getUsuarioById(uid);
				
				UsuarioPk uid2 = new UsuarioPk();
				uid2.idUsuario = usuarioOrigen.getIdUsuario();
				Usuario user2 = fenrir.getUsuarioById(uid2);				
                                
                                hermod.addCtrlReasignacion(turno, user2.getUsername(), user.getUsername());
				hermod.updateLastTurnoHistoria(turno);
				
				if(turno==null && turno.getHistorials().size()==0){
					throw new ANReasignacionTurnosException("No se pudo consultar consultar la histaria del turno.");
				}				
	
				while(itHistoria.hasNext()){
					TurnoHistoria th = (TurnoHistoria)itHistoria.next();
					if((new Long(th.getIdTurnoHistoria()).longValue()) == turno.getLastIdHistoria()){
						th.setIdTurnoHistoria(null);
						th.setUsuario(user);
						th.setTurno(turno);
						th.setIdAdministradorSAS(estacion.getEstacionId());
						th.setFaseAnterior(th.getFase());
						th.setUsuarioAtiende(user2);
						hermod.addTurnoHistoriaToTurnoReasignacion(turno, th);
					}
				}	
				turnosReasignados.add(turno.getIdWorkflow());
			}
                        
                        /**Se agregan las notas informativas */
                         hermod.addNotasInformativas(evento.getNotasInformativas());
			
		} catch (ANReasignacionTurnosException e) {
			throw e;
		} catch (HermodException e) {
			throw new ANReasignacionTurnosException("No se pudo consultar los turnos marcados para ser reanotados.", e);
		} catch (Throwable e) {
			throw new ANReasignacionTurnosException("No se pudo consultar los turnos marcados para ser reanotados. " + e.getMessage(), e);
		}

		EvnRespReasignacionTurnos rta = new EvnRespReasignacionTurnos(EvnRespReasignacionTurnos.REASIGNAR_TURNOS, EvnRespReasignacionTurnos.REASIGNAR_TURNOS);
		if(turnosNoReasignados.size()>0){
			rta.setListaTurnosNoReasignados(turnosNoReasignados);
		}
		rta.setListaTurnosReasignados(turnosReasignados);
		return rta;

	}
	
	/**
	* Permite reasignar los turnos de un usuario en un determinado rol a otro usuario que tenga el mismo rol asignado.
	* Cuande el usuario que tenía el turno tenía folios con datos temporales y el bloqueo sobre los mismos
	 * Se conservan los datos temporales
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> 
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información concertiente 
	 * a si se pudo reanotar o no los recursos y revocatorias directas.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos reasignarTurnosTemporales(EvnReasignacionTurnos evento) throws EventoException {

		Estacion estacion = evento.getEstacion();
		Usuario usuario = evento.getUsuarioSIR();
		Usuario usuarioDestino = evento.getUsuarioDestino();
		Usuario usuarioOrigen = evento.getUsuarioOrigen();
		List turnosAReasignar =  evento.getTurnos();
		
		if(!evento.getRol().getRolId().equals(CRol.SIR_ROL_RESPONSABLE_CORRECCIONES)){
			throw new ANReasignacionTurnosException("Esta opción puede ejecutarse únicamente para el rol: RESPONSABLE DE CORRECCIONES.");
		}
		
		Hashtable turnosNoReasignados = new Hashtable();
		if(turnosAReasignar==null){
			turnosAReasignar = new ArrayList();
		}

		try {
			
			Iterator itTurnos = turnosAReasignar.iterator();			
			while(itTurnos.hasNext()){
				 
				//01. SE VERIFICA QUE LAS MATRÍCULAS ASOCIADAS NO ESTÉN BLOQUEADAS.
				String idWorkflow = (String) itTurnos.next();
				Turno turno = hermod.getTurnobyWF(idWorkflow);				 
				Solicitud solicitud = turno.getSolicitud();
				
				List solicitudesFolio = solicitud.getSolicitudFolios();
				
				if(solicitudesFolio==null){
					solicitudesFolio = new ArrayList();					
				}
				
				Iterator it = solicitudesFolio.iterator();
				boolean bloqueado = false;
				boolean hasDatosTMP = false;
				
				while(it.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					Folio folio = solFolio.getFolio();
					FolioPk fid = new FolioPk();
					fid.idMatricula = folio.getIdMatricula();
	
					try{
						Usuario usuarioBloqueo = forseti.getBloqueoFolio(fid);

						if(usuarioBloqueo!=null){
							if(usuarioBloqueo.getIdUsuario()== usuarioOrigen.getIdUsuario() || turno.getIdFase().equals(CFase.COR_REVISAR_APROBAR)
									|| turno.getIdFase().equals(CFase.COR_REVISION_ANALISIS)){
								
								if(!bloqueado){
									bloqueado = true;	
								}
								
								if(forseti.hasDatosTemporalesFolio(fid) ){
									//SI EL TURNO TIENE DATOS TEMPORALES SE MARCA EL FLAG Y SE SALE DEL CICLO
									hasDatosTMP = true;
									continue;
								}
								
							}
						}
					}catch(Exception e){
					}
				}
                                /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion para comprovar si el turno de testamento tiene datos temporales
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
                                ValidacionesSIR valSir = new ValidacionesSIR();
                                Turno turnoAnterior =solicitud.getTurnoAnterior();
                                if(turno.getIdProceso()==3 && turnoAnterior!=null && turnoAnterior.getIdProceso()==6){
                                    if(valSir.isTestamentoTMP(turnoAnterior.getSolicitud().getIdSolicitud()))
                                    {
                                        hasDatosTMP = true;
                                    }
                                }
				//SE VALIDA QUE SE AVANCE EL TURNO ÚNICAMENTE SI EL TURNO TIENE DATOS TEMPORALES.
				if(!hasDatosTMP){
					turnosNoReasignados.put(turno.getIdWorkflow(),"Esta opción debe ejecutarse únicamente cuando el turno tiene datos temporales.");
					continue;					
				}
				
				//02. SE ACTUALIZA LA ESTACIÓN QUE TIENE ASIGNADO UN TURNO.
				hermod.reasignarTurno(turno , estacion);

				//03. SE ACTUALIZA EL TURNO HISTORIA.
				List historial = turno.getHistorials();
				Iterator itHistoria = historial.iterator();
				
				UsuarioPk uid = new UsuarioPk();
				uid.idUsuario = usuarioDestino.getIdUsuario();
				Usuario user = fenrir.getUsuarioById(uid);
				
				UsuarioPk uid2 = new UsuarioPk();
				uid2.idUsuario = usuarioOrigen.getIdUsuario();
				Usuario user2 = fenrir.getUsuarioById(uid2);				
	
				hermod.updateLastTurnoHistoria(turno);
				
				if(turno==null && turno.getHistorials().size()==0){
					throw new ANReasignacionTurnosException("No se pudo consultar la histaria del turno.");
				}				
	
				while(itHistoria.hasNext()){
					TurnoHistoria th = (TurnoHistoria)itHistoria.next();
					if((new Long(th.getIdTurnoHistoria()).longValue()) == turno.getLastIdHistoria()){
						th.setIdTurnoHistoria(null);
						th.setUsuario(user);
						th.setTurno(turno);
						th.setIdAdministradorSAS(estacion.getEstacionId());
						th.setUsuarioAtiende(user2);
						hermod.addTurnoHistoriaToTurnoReasignacion(turno, th);
					}
				}	
				
			}
			
		} catch (ANReasignacionTurnosException e) {
			throw e;
		} catch (HermodException e) {
			throw new ANReasignacionTurnosException("No se pudo consultar los turnos marcados para ser reanotados.", e);
		} catch (Throwable e) {
			throw new ANReasignacionTurnosException("No se pudo consultar los turnos marcados para ser reanotados. " + e.getMessage(), e);
		}

		Enumeration enumeration = turnosNoReasignados.keys();
		while (enumeration.hasMoreElements() ){
			String key = (String)enumeration.nextElement();
			String mensaje = (String)turnosNoReasignados.get(key);
		}
		
		EvnRespReasignacionTurnos rta = new EvnRespReasignacionTurnos(EvnRespReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES, EvnRespReasignacionTurnos.REASIGNAR_TURNOS_CON_TEMPORALES);
		if(turnosNoReasignados.size()>0){
			rta.setListaTurnosNoReasignados(turnosNoReasignados);
		}
		return rta;

	}	
	
	/**
	* Permite reasignar los turnos de un usuario en un determinado rol a otro usuario que tenga el mismo rol asignado.
	* Cuande el usuario que tenía el turno tenía folios con datos temporales y el bloqueo sobre los mismos
	 * 
	 * @param evento de tipo <code>EvnReasignacionTurnos</code> 
	 * 
	 * @return <code>EvnRespReasignacionTurnos</code> con la información concertiente 
	 * a si se pudo reanotar o no los recursos y revocatorias directas.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespReasignacionTurnos reasignarTurnosForzoso(EvnReasignacionTurnos evento) throws EventoException {

		Estacion estacion = evento.getEstacion();
		Usuario usuario = evento.getUsuarioSIR();
		Usuario usuarioDestino = evento.getUsuarioDestino();
		Usuario usuarioOrigen = evento.getUsuarioOrigen();
		List turnosAReasignar =  evento.getTurnos();
		
		Hashtable turnosNoReasignados = new Hashtable();
		if(turnosAReasignar==null){
			turnosAReasignar = new ArrayList();
		}

		try {
			
			Iterator itTurnos = turnosAReasignar.iterator();			
			while(itTurnos.hasNext()){
				 
				//01. SE VERIFICA QUE LAS MATRÍCULAS ASOCIADAS NO ESTÉN BLOQUEADAS.
				String idWorkflow = (String) itTurnos.next();
				Turno turno = hermod.getTurnobyWF(idWorkflow);				 
				Solicitud solicitud = turno.getSolicitud();
				
				List solicitudesFolio = solicitud.getSolicitudFolios();
				
				if(solicitudesFolio==null){
					solicitudesFolio = new ArrayList();					
				}
				
				Iterator it = solicitudesFolio.iterator();
				boolean bloqueado = false;
				boolean hasDatosTMP = false;
				
				while(it.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					Folio folio = solFolio.getFolio();
					FolioPk fid = new FolioPk();
					fid.idMatricula = folio.getIdMatricula();
	
					try{
						Usuario usuarioBloqueo = forseti.getBloqueoFolio(fid);

						if(usuarioBloqueo!=null){
							if(usuarioBloqueo.getIdUsuario()== usuarioOrigen.getIdUsuario() || turno.getIdFase().equals(CFase.COR_REVISAR_APROBAR)
									|| turno.getIdFase().equals(CFase.COR_REVISION_ANALISIS)){
								
								if(!bloqueado){
									bloqueado = true;	
								}
								
								if(forseti.hasDatosTemporalesFolio(fid) ){
									//SI EL TURNO TIENE DATOS TEMPORALES SE MARCA EL FLAG Y SE SALE DEL CICLO
									hasDatosTMP = true;
									continue;
								}
								
							}
						}
					}catch(Exception e){
					}
				}
				/*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion para comprovar si el turno de testamento tiene datos temporales
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
                                ValidacionesSIR valSir = new ValidacionesSIR();
                                Turno turnoAnterior =solicitud.getTurnoAnterior();
                                if(turno.getIdProceso()==3 && turnoAnterior!=null && turnoAnterior.getIdProceso()==6){
                                    if(valSir.isTestamentoTMP(turnoAnterior.getSolicitud().getIdSolicitud()))
                                    {
                                        hasDatosTMP = true;
                                    }
                                }
				//SE VALIDA QUE SE AVANCE EL TURNO ÚNICAMENTE SI EL TURNO TIENE DATOS TEMPORALES.
				if(!hasDatosTMP){
					turnosNoReasignados.put(turno.getIdWorkflow(),"Esta opción debe ejecutarse únicamente cuando el turno tiene datos temporales.");
					continue;					
				}
				
				//SI EL TURNO TIENE DATOS TEMPORALES SE INTENTARA DESHACER LOS CAMBIOS REALIZADOS.
				//LOS FOLIOS QUEDARAN EN ESTADO DESBLOQUEADO PORQUE ASÍ SE LE INDICA AL SERVICIO.
				//SI NO HAY PROBLEMAS CON LA ELIMINACIÓN DE LOS DATOS TEMPORALES SE PROCEDE A REASIGNAR EL TURNO  
				//DE LO CONTRARIO SE COLOCA EL TURNO EN LA LISTA DE TURNOS NO REASGNADOS.       
				if(hasDatosTMP){
                                    try{
                                        /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion para desaser cambios en el testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
                                    if(turno.getIdProceso()==3 && turnoAnterior!=null && turnoAnterior.getIdProceso()==6){
                                        TestamentoSIR testamentoSir = new TestamentoSIR();
                                        testamentoSir.deshacerCambiosTestamento(turno.getIdWorkflow(), turnoAnterior.getSolicitud().getIdSolicitud());
                                    }else{
                                        
						TurnoPk idTurno = new TurnoPk();
						idTurno.anio = turno.getAnio();
						idTurno.idCirculo = turno.getIdCirculo();
						idTurno.idProceso = turno.getIdProceso();
						idTurno.idTurno = turno.getIdTurno();						
						forseti.deshacerCambiosTurno(idTurno, usuarioOrigen, true);
                                    }
                                    }catch(GeneralSIRException gse)
                                    {
                                        turnosNoReasignados.put(turno.getIdWorkflow(),"Error al deshacer los cambios. " + gse.getMessage());
                                        continue;
                                    }catch(Throwable e){
						turnosNoReasignados.put(turno.getIdWorkflow(),"Error al deshacer los cambios. " + e.getMessage());
						continue;
				    }
				}
				
				//02. SE ACTUALIZA LA ESTACIÓN QUE TIENE ASIGNADO UN TURNO.
				hermod.reasignarTurno(turno , estacion);

				//03. SE ACTUALIZA EL TURNO HISTORIA.
				List historial = turno.getHistorials();
				Iterator itHistoria = historial.iterator();
				
				UsuarioPk uid = new UsuarioPk();
				uid.idUsuario = usuarioDestino.getIdUsuario();
				Usuario user = fenrir.getUsuarioById(uid);
				
				UsuarioPk uid2 = new UsuarioPk();
				uid2.idUsuario = usuarioOrigen.getIdUsuario();
				Usuario user2 = fenrir.getUsuarioById(uid2);
	
				hermod.updateLastTurnoHistoria(turno);
				
				if(turno==null && turno.getHistorials().size()==0){
					throw new ANReasignacionTurnosException("No se pudo consultar consultar la historia del turno.");
				}				
	
				while(itHistoria.hasNext()){
					TurnoHistoria th = (TurnoHistoria)itHistoria.next();
					if((new Long(th.getIdTurnoHistoria()).longValue()) == turno.getLastIdHistoria()){
						th.setIdTurnoHistoria(null);
						th.setUsuario(user);
						th.setTurno(turno);
						th.setIdAdministradorSAS(estacion.getEstacionId());
						th.setUsuarioAtiende(user2);
						hermod.addTurnoHistoriaToTurnoReasignacion(turno, th);
					}
				}	
				
			}
			
		} catch (ANReasignacionTurnosException e) {
			throw e;
		} catch (HermodException e) {
			throw new ANReasignacionTurnosException("No se pudo consultar los turnos marcados para ser reanotados.", e);
		} catch (Throwable e) {
			throw new ANReasignacionTurnosException("No se pudo consultar los turnos marcados para ser reanotados. " + e.getMessage(), e);
		}

		Enumeration enumeration = turnosNoReasignados.keys();
		while (enumeration.hasMoreElements() ){
			String key = (String)enumeration.nextElement();
			String mensaje = (String)turnosNoReasignados.get(key);
		}
		
		EvnRespReasignacionTurnos rta = new EvnRespReasignacionTurnos(EvnRespReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO, EvnRespReasignacionTurnos.REASIGNAR_TURNOS_FORZOSO);
		if(turnosNoReasignados.size()>0){
			rta.setListaTurnosNoReasignados(turnosNoReasignados);
		}
		return rta;

	}	


}
