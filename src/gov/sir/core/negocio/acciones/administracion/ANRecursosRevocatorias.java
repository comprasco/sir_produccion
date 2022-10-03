package gov.sir.core.negocio.acciones.administracion;
/*
 * @author      :   Carlos Mario Torres Urina
 * @change      :   Se abilita el uso de la clase SimpleDateFormat,
 * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnRecursosRevocatorias;
import gov.sir.core.eventos.administracion.EvnRespRecursosRevocatorias;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.negocio.acciones.excepciones.ANRevocatoriaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaTurnoExceptionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoCalificacionHTException;
import gov.sir.core.negocio.modelo.BloqueoFolio;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

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
 * <code>EvnRecursosRevocatorias</code> destinados a manejar 
 * la administración de los recursos y revocatorias.  
 * 
 * @author ppabon
 *
 */
public class ANRecursosRevocatorias extends SoporteAccionNegocio {

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
	public ANRecursosRevocatorias() throws EventoException {
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
	 * Manejador de eventos de tipo <code>EvnrecursosRevocatorias</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento 
	 * que llega como parámetro.
	 * 
	 * @param evento <code>EvnRecursosRevocatorias</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 * 
	 * @return <code>EventoRespuesta</code> con la información resultante de la 
	 * ejecución de la acción sobre los servicios
	 * 
	 * @throws <code>EventoException</code> 
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnRecursosRevocatorias evento = (EvnRecursosRevocatorias) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnRecursosRevocatorias.SELECCIONAR_TURNO_BLOQUEO)) {
			return seleccionarTurno(evento);
		} else if (tipoEvento.equals(EvnRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO)) {
			return agregarMatricula(evento);
		}  else if (tipoEvento.equals(EvnRecursosRevocatorias.BLOQUEAR_RECURSOS)) {
			return bloquearRecursos(evento);
		} else if (tipoEvento.equals(EvnRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS)) {
			return consultarTurnosBloqueados(evento);
		} else if (tipoEvento.equals(EvnRecursosRevocatorias.REANOTAR_RECURSOS)) {
			return reanotarRecursosRevocatorias(evento);
		} else if (tipoEvento.equals(EvnRecursosRevocatorias.RECHAZAR_RECURSOS)) {
			return rechazarRecursosRevocatorias(evento);
		}

		return null;
	}

	/**
	 * Este método se encarga consultar el turno que desea reanotarse.
	 * 
	 * @param evento de tipo <code>EvnRecursosRevocatorias</code> con la información
	 * del turno y la justificación para su bloqueo.
	 * 
	 * @return <code>EvnRespRecursosRevocatorias</code> con la información del turno que desea reanotarse.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespRecursosRevocatorias seleccionarTurno(EvnRecursosRevocatorias evento) throws EventoException {

		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		Turno rta = null;

		try {
			rta = hermod.getTurnobyWF(turno.getIdWorkflow());
		} catch (HermodException e) {
			throw new ConsultaTurnoExceptionException("No se pudo obtener la informacion del turno", e);
		} catch (Throwable e) {
			throw new ConsultaTurnoExceptionException(e.getMessage(), e);
		}

		if (rta == null) {
			throw new ConsultaTurnoExceptionException("El turno " + turno.getIdWorkflow() + " no existe.");
		} else {
			if (!rta.getIdCirculo().equals(circulo.getIdCirculo())) {
				throw new ConsultaTurnoExceptionException("No se puede consultar turnos de otros circulos.");
			}			
			
			if (!rta.getIdCirculo().equals(circulo.getIdCirculo())) {
				throw new ConsultaTurnoExceptionException("No se puede consultar turnos de otros circulos.");
			}
			String proceso = "" + rta.getIdProceso();
			if (!proceso.equals(CProceso.PROCESO_REGISTRO)) {
				throw new ConsultaTurnoExceptionException("El turno debe ser de Registro de Documentos.");
			}
			if (rta.getFechaFin() == null) {
				throw new ConsultaTurnoExceptionException("El turno debe estar finalizado.");
			}
			if (rta.isRevocatoria()) {
				throw new ConsultaTurnoExceptionException("El turno ya se encuentra bloqueado.");
			}		
			List historia = rta.getHistorials();
			boolean isValido= false;
			if(historia==null || historia.size()==0){
				throw new ConsultaTurnoExceptionException("No se pudo determinar si el turno fue devuelto o inscrito parcialmente.");
			}else{
				Iterator it = historia.iterator();
				while(it.hasNext()){
					
					TurnoHistoria th = (TurnoHistoria)it.next();
					if(th.getFase().equals(CFase.CAL_CALIFICACION)){
						isValido = false;
						if(th.getRespuesta().equals(EvnCalificacion.WF_DEVOLUCION) || th.getRespuesta().equals(EvnCalificacion.WF_INSCRIPCION_PARCIAL)){
							isValido = true;
						}
					}
                                        /*
                                        * @author      : Julio Alcázar Rivas
                                        * @change      : Se agrega la validacion para turnos que pasaron por el rol_testamentos
                                        * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                                        */
                                        else if(th.getFase().equals(CFase.REG_TESTAMENTO)){
						isValido = false;
						if(th.getRespuesta().equals(CRespuesta.NEGAR) ){
							isValido = true;
						}
					}
					
				}
				
				if(!isValido){
					throw new ConsultaTurnoExceptionException("El turno debe estar devuelto o inscrito parcialemente.");
				}
				
			}
		}

		EvnRespRecursosRevocatorias evn = new EvnRespRecursosRevocatorias(rta, EvnRespRecursosRevocatorias.BLOQUEAR_RECURSOS);
		return evn;
	}
	
	
	/**
	 * @param evento
	 * @return
	 */
	private EvnRespRecursosRevocatorias agregarMatricula(EvnRecursosRevocatorias evento) throws EventoException {

		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		Folio folio = evento.getFolio();
		Folio rta = new Folio();
		rta.setIdMatricula(folio.getIdMatricula());

		try {
			//SI EL FOLIO EXISTE
			if (forseti.existeFolio(folio.getIdMatricula())) {

				TurnoPk id = new TurnoPk();
				id.anio = turno.getAnio();
				id.idCirculo = turno.getIdCirculo();
				id.idProceso = turno.getIdProceso();
				id.idTurno = turno.getIdTurno();

				Solicitud sol = turno.getSolicitud();

				if (sol != null) {
					List listaSolicitudes = null;
					listaSolicitudes = sol.getSolicitudFolios();

					//Verificar si la matrícula que se va a añadir está en la lista
					//de solicitudes folio de la solicitud.  En ese caso se lanza una
					//excepción. 
					if (listaSolicitudes != null) {
						for (int i = 0; i < listaSolicitudes.size(); i++) {
							SolicitudFolio solFolio = (SolicitudFolio) listaSolicitudes.get(i);
							if (solFolio != null) {
								if (solFolio.getIdMatricula().equals(folio.getIdMatricula())) {
									throw new ANRevocatoriaRegistroException("La matrícula que intenta ingresar ya está asociada a la solicitud.");
								}
							}
						}
					}
				}

			} else {
				throw new ANRevocatoriaRegistroException("La matrícula que intenta asociar no existe.");
			}

		} catch (ANRevocatoriaRegistroException ev) {
			throw ev;
		} catch (Throwable e) {
			throw new ANRevocatoriaRegistroException("No se pudo asociar la matrícula al turno.", e);
		}

		return new EvnRespRecursosRevocatorias(rta , EvnRespRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO);

	}
	
	
	/**
	 * Este método se encarga de bloquear un <code>Turno</code>.
	 * 
	 * @param evento de tipo <code>EvnRecursosRevocatorias</code> con la información
	 * del turno y la justificación para su bloqueo.
	 * 
	 * @return <code>EvnRespRecursosRevocatorias</code> con la información concertiente 
	 * a si se pudo bloquear o no los folios del turno.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespRecursosRevocatorias bloquearRecursos(EvnRecursosRevocatorias evento) throws EventoException {

		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		List foliosAAdicionar = evento.getFolios();
		Usuario usuarioSIR = evento.getUsuarioSIR();
		String justificacion = evento.getJustificacion();
		Turno rta = null;

		//SE REALIZAN LAS VALIDACIONES DE NEGOCIO NECESARIAS
		//LA JUSTIFICACION DEL BLOQUEO ES OBLIGATORIA
		if (justificacion == null) {
			throw new ANRevocatoriaRegistroException("Debe existir una justificación para el bloqueo de los folios");
		}
		//EL TURNO DEBE SER DEL MISMO CIRCULO
		if (!turno.getIdCirculo().equals(circulo.getIdCirculo())) {
			throw new ANRevocatoriaRegistroException("No se puede bloquear turnos de otros circulos.");
		}
		//EL TURNO DEBE SER DEL PROCESO REGISTRO DE DOCUMENTOS
		String proceso = "" + turno.getIdProceso();
		if (!proceso.equals(CProceso.PROCESO_REGISTRO)) {
			throw new ANRevocatoriaRegistroException("El turno a bloquear debe ser de registro de documentos.");
		}
		//EL TURNO DEBE ESTAR FINALIZADO		
		if (turno.getFechaFin() == null) {
			throw new ANRevocatoriaRegistroException("El turno debe estar finalizado.");
		}

		try {
			//01. SE MARCA EL TURNO PARA INDICAR QUE TIENE UN PROCESO DE REVOCATORIA EN TRAMITE
			turno.setRevocatoria(true);
			hermod.actualizarMarcaRevocatoriaTurno(turno);

			//02. SE ASOCIAN LOS NUEVOS FOLIOS AL TURNO
			TurnoPk tid = new TurnoPk(turno.getIdWorkflow());
			if (foliosAAdicionar != null && !foliosAAdicionar.isEmpty()) {
				Iterator it = foliosAAdicionar.iterator();
				while (it.hasNext()) {
					String matricula = (String) it.next();
					SolicitudFolioPk sfId = hermod.addFolioToTurno(matricula, tid, usuarioSIR);
				}
			}

			//03. SE AGREGA LA NOTA INFORMATIVA
			ProcesoPk procesoId = new ProcesoPk();
			Long longProceso = new Long(CProceso.PROCESO_REGISTRO);
			procesoId.idProceso = longProceso.longValue();
			List listaInformativas = hermod.getTiposNotaProcesoByTpnaDevolutiva(procesoId, false);
			Nota nota = crearNota(listaInformativas, evento);
			if (nota == null) {
				throw new ANRevocatoriaRegistroException("No se pudo agregar la nota informativa al turno");
			}
			hermod.addNotaToTurno(nota, tid);
			turno.addNota(nota);

			//04. SE BLOQUEAN LOS FOLIOS ASOCIADOS
			forseti.delegarBloqueoFolios(tid, usuarioSIR);

		} catch (ANRevocatoriaRegistroException e) {
			throw e;
		} catch (HermodException e) {
			throw new ANRevocatoriaRegistroException("No se pudo realizar el bloqueo de los folios.", e);
		} catch (ForsetiException e) {

			Hashtable errores = e.getHashErrores();
			if (errores != null && !errores.isEmpty()) {
				
				String mensaje = "El turno fue marcado para ser reanotado, sin embargo no fue posible bloquear las matrículas.\n";
				Enumeration enumeration = errores.keys();
				while (enumeration.hasMoreElements()) {
					String matricula = (String) enumeration.nextElement();
					String razon = (String) errores.get(matricula);
					mensaje.concat(matricula + " : " + razon + "\n");
				}
				TomarTurnoCalificacionHTException exc = new TomarTurnoCalificacionHTException(e, mensaje);
				throw exc;
			}

			throw new ANRevocatoriaRegistroException("El turno fue marcado para ser reanotado, sin embargo no fue posible bloquear las matrículas.", e);
			
		} catch (Throwable e) {
			throw new ANRevocatoriaRegistroException(e.getMessage(), e);
		}

		EvnRespRecursosRevocatorias evn = new EvnRespRecursosRevocatorias(rta, EvnRespRecursosRevocatorias.BLOQUEAR_RECURSOS);
		return evn;
	}
	
	/**
	 * @param listaInformativas
	 * @param evento
	 * @return
	 */
	private Nota crearNota(List listaInformativas, EvnRecursosRevocatorias evento) {
		Nota rta = null;
		TipoNota tipoNota = null;
		boolean condicionNota = false;
		
		if (listaInformativas != null) {
			
			for (int i = 0; i < listaInformativas.size(); i++) {
				tipoNota = (TipoNota) listaInformativas.get(i);
				if (tipoNota.getNombre().equals(CNota.NOTA_INFORMATIVA_TURNO_FINALIZADO_REGISTRO)) {
					condicionNota = true;
					i = listaInformativas.size() + 1;
				}

			}
			
			if (condicionNota) {
				rta = new Nota();
				rta.setAnio(evento.getTurno().getAnio());
				rta.setIdProceso(evento.getTurno().getIdProceso());
				rta.setIdCirculo(evento.getTurno().getIdCirculo());
				rta.setIdTurno(evento.getTurno().getIdTurno());
				rta.setIdFase(null);
				rta.setTiponota(tipoNota);
				rta.setDescripcion(evento.getJustificacion());
				rta.setUsuario(evento.getUsuarioSIR());						
				Date fecha = new Date();
				Calendar calendar = Calendar.getInstance();		
				calendar.setTime(fecha);
				rta.setTime(calendar.getTime());
				rta.setTurno(evento.getTurno());
			}
		}
		return rta;
	}

	
	
	

	/**
	 * Este método se encarga de consultar los turnos bloqueados para por recursos y revocatorias directas.
	 * 
	 * @param evento de tipo <code>EvnRecursosRevocatorias</code> 
	 * 
	 * @return <code>EvnRespRecursosRevocatorias</code> con los turnos bloqueados por recursos y revocatorias directas. 
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespRecursosRevocatorias consultarTurnosBloqueados(EvnRecursosRevocatorias evento) throws EventoException {
		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		List turnosAReanotar = null;
		List calificadores = null;
                /*
                * @author      : Julio Alcázar Rivas
                * @change      : nueva valriable para manejar los usuarios con  el rol testamentos
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                List caliTestamentos = null;
		
		try {
			//01. SE CONSULTAN LOS TURNOS A REANOTAR.
			turnosAReanotar = hermod.consultarTurnosAReanotar(circulo);
			
			//02. SE CONSULTAN LOS CALIFICADORES.
			Rol rol = new Rol();
			rol.setRolId(CRoles.SIR_ROL_CALIFICADOR);			
			//calificadores = fenrir.getEstacionesRolByCirculo(rol , circulo);
			calificadores = fenrir.obtenerEstacionesUsuarioByEstadoRol(rol , circulo, true);

                        /*
                        * @author      : Julio Alcázar Rivas
                        * @change      : se obtienen los usuarios con  el rol testamentos
                        * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                        */
                        rol.setRolId(CRol.SIR_ROL_TESTAMENTOS);
                        caliTestamentos = fenrir.obtenerEstacionesUsuarioByEstadoRol(rol , circulo, true);
		}catch (HermodException e) {
			throw new ANRevocatoriaRegistroException("No se pudo consultar los turnos marcados para ser reanotados.", e);
		} catch (Throwable e) {
			throw new ANRevocatoriaRegistroException("No se pudo consultar los turnos marcados para ser reanotados. " + e.getMessage(), e);
		}	
		
		if(turnosAReanotar==null){
			turnosAReanotar = new ArrayList();	
		}
		if(calificadores==null){
			calificadores = new ArrayList();	
		}
                /*
                * @author      : Julio Alcázar Rivas
                * @change      : validacion para la variable que maneja los usuarios con  el rol testamentos
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                if(caliTestamentos==null){
			caliTestamentos = new ArrayList();
		}
		

		EvnRespRecursosRevocatorias rta = new EvnRespRecursosRevocatorias(turnosAReanotar ,EvnRespRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS );
		rta.setListaCalificadores(calificadores);
                /*
                * @author      : Julio Alcázar Rivas
                * @change      : set en la respuesta los usuarios con  el rol testamentos
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                rta.setListaCalTestamentos(caliTestamentos);
		return rta;

	}

	/**
	 * Este método se encarga de reanotar recursos y revocatorias directas.
	 * 
	 * @param evento de tipo <code>EvnRecursosRevocatorias</code> 
	 * 
	 * @return <code>EvnRespRecursosRevocatorias</code> con la información concertiente 
	 * a si se pudo reanotar o no los recursos y revocatorias directas.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespRecursosRevocatorias reanotarRecursosRevocatorias(EvnRecursosRevocatorias evento) throws EventoException {
		Turno turno = evento.getTurno();
		Estacion estacion = evento.getEstacion();
		Usuario usuario = evento.getUsuarioSIR();
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   se agrega nueva variable para manejar las estacioes con rol testamento
                 * Caso Mantis  :   0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                 */
                Estacion estacionTest = evento.getEstacionTest();

		try {
				/*
				 * @author      :   Carlos Mario Torres Urina
				 * @change      :   Se valida si tiene folios trasladados
				 * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
				 */
                    TrasladoSIR validacion = new TrasladoSIR();
                    
                    if(validacion.tieneFoliosTraslado(turno.getIdWorkflow()))
                    {
                        throw new ANRevocatoriaRegistroException("El turno tiene folios asociados en estado trasladados");
                    }
                    
                    
                    //00. SE CONSULTA EL TURNO QUE DESEA REANOTARSE
			turno = hermod.getTurnobyWF(turno.getIdWorkflow());
                        /**
                        * @author      :   Carlos Torres
                        * @change      :   Se agrega fase en el historial del turno
                        * @Caso Mantis :   0014376
                        */                      
                        hermod.addFaseRestitucionTurno(turno,usuario);
			//01. SE RETROCEDE EL WORKFLOW.
			Hashtable ht = new Hashtable();
			ht.put(Processor.ACTIVITY, CFase.REG_REPARTO);
			ht.put(Processor.RESULT, CRespuesta.CONFIRMAR);
			//hermod.reanotarTurno(turno, ht);

			//02. SE ACTUALIZAN LAS TABLAS DEL MODELO OPERATIVO.
			
			//02.1 SE CONSULTA EL NOTIFICATION_ID DEL TURNO
			/*Processor p = HermodWFFactory.getFactory().getProcessor();

			String tipoActividad = "ACTIVITY_RESULT";

			Hashtable htConsulta = new Hashtable(10);
			htConsulta.put("ITEM_KEY", turno.getIdWorkflow());
			htConsulta.put("TIPO_RESPUESTA", this.ACTIVITY_RESULT);
			htConsulta.put("TIPO_CONSULTA", tipoActividad);
			htConsulta.put(Message.CONSULTAR_ULTIMO_ID_NOTIFICATION , Message.CONSULTAR_ULTIMO_ID_NOTIFICATION );
			 */
			long idParam = 3;
			/*String tipoMensajeParam = Message.AL_WORKFLOW;
			String tipoProcesoParam = Message.CONSULTAR_PROCESO;

			Message m = new Message(idParam, tipoMensajeParam, tipoProcesoParam, htConsulta);
			Message rta = p.process(m);

			Hashtable htRta = rta.getParametros();
			String idNotification = (String) htRta.get("NOT_ID");*/
			
			//02.2 SE ACTUALIZA LA TABLA SIR_OP_TURNO_EJECUCION, LA TABLA SIR_OP_TURNO Y LA TABLA SIR_OP_TURNO_HISTORIA. 
			
			/*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   se selecciona el metodo reanotarTurnoModeloOperativo() con diferentes parametros dependiendo de la estacion.
                         * Caso Mantis  :   0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                         */
                        if(estacionTest == null){
                            hermod.reanotarTurnoModeloOperativo(turno.getIdWorkflow() , "1" , CFase.CAL_CALIFICACION, CRespuesta.CONFIRMAR,estacion.getEstacionId() , usuario );
                        }else {
                            hermod.reanotarTurnoModeloOperativo(turno.getIdWorkflow() , "1" , CFase.REG_TESTAMENTO, CRespuesta.CONFIRMAR,estacionTest.getEstacionId() , usuario );
                        }

			//03. SE AGREGA LA NOTA INFORMATIVA
			ProcesoPk procesoId = new ProcesoPk();
			Long longProceso = new Long(CProceso.PROCESO_REGISTRO);
			procesoId.idProceso = longProceso.longValue();
			List listaInformativas = hermod.getTiposNotaProcesoByTpnaDevolutiva(procesoId, false);
			Nota nota = crearNota(listaInformativas, evento);
			if (nota == null) {
				throw new ANRevocatoriaRegistroException("No se pudo agregar la nota informativa al turno");
			}
			hermod.addNotaToTurno(nota, new TurnoPk(turno.getIdWorkflow()));
			turno.addNota(nota);
			
			//04. SE QUITA LA MARCA DE TURNO PARA REANOTAR. Y SE DEJA EN MODO BLOQUEO CUALQUIERA
			Turno turnoActualizar = new Turno();
			turnoActualizar.setAnio(turno.getAnio());
			turnoActualizar.setIdCirculo(turno.getIdCirculo());
			turnoActualizar.setIdProceso(turno.getIdProceso());
			turnoActualizar.setIdTurno(turno.getIdTurno());
			turnoActualizar.setIdWorkflow(turno.getIdWorkflow());			
			turnoActualizar.setRevocatoria(false);
			turnoActualizar.setFechaFin(null);			
			turnoActualizar.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
			hermod.actualizarMarcaRevocatoriaTurno(turnoActualizar);
			
			List lista = turno.getSolicitud().getSolicitudesHijas();
			
			if (lista != null) {
				Iterator iterator = lista.iterator();
				
			    while (iterator.hasNext()) {
			    	SolicitudAsociada solAsociada = (SolicitudAsociada) iterator.next();
			    	Solicitud solHija = solAsociada.getSolicitudHija();
			    	Turno turnoHijo = solHija.getTurno();
			    	SolicitudCertificado solCerti = (SolicitudCertificado) solHija;
			    	int a = solCerti.getNumImpresiones();
			    	if(a>0){
			    		// No se hace nada
			    	}else {
			    		hermod.reanotarTurnoModeloOperativo(turnoHijo.getIdWorkflow() ,  "1" , CFase.CER_ESPERA_IMPRIMIR, CRespuesta.CONFIRMAR, "X-MESA.CONTROL.REG." + turnoHijo.getIdCirculo(), usuario );
			    	}
			    	
			    }
		    }
			
		} catch (ANRevocatoriaRegistroException e) {
			throw e;
		} catch (HermodException e) {
			throw new ANRevocatoriaRegistroException("No se pudo restituir los turnos.", e);
		} catch (Throwable e) {
			throw new ANRevocatoriaRegistroException("No se pudo restituir los turnos. " + e.getMessage(), e);
		}

		EvnRespRecursosRevocatorias rta = new EvnRespRecursosRevocatorias(EvnRespRecursosRevocatorias.REANOTAR_RECURSOS, EvnRespRecursosRevocatorias.REANOTAR_RECURSOS);
		return rta;

	}

	/**
	 * Este método se encarga de rechazar recursos y revocatorias directas.
	 * 
	 * @param evento de tipo <code>EvnRecursosRevocatorias</code> 
	 * 
	 * @return <code>EvnRespRecursosRevocatorias</code> con la información concertiente 
	 * a si se pudo rechazar o no los recursos y revocatorias directas.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespRecursosRevocatorias rechazarRecursosRevocatorias(EvnRecursosRevocatorias evento) throws EventoException {
		Turno turno = evento.getTurno();
		Usuario usuario = evento.getUsuarioSIR();

		try {
			//SE CONSULTA EL TURNO QUE DESEA REANOTARSE
			turno = hermod.getTurnobyWF(turno.getIdWorkflow());

			//01. SE AGREGA LA NOTA INFORMATIVA
			ProcesoPk procesoId = new ProcesoPk();
			Long longProceso = new Long(CProceso.PROCESO_REGISTRO);
			procesoId.idProceso = longProceso.longValue();
			List listaInformativas = hermod.getTiposNotaProcesoByTpnaDevolutiva(procesoId, false);
			Nota nota = crearNota(listaInformativas, evento);
			if (nota == null) {
				throw new ANRevocatoriaRegistroException("No se pudo agregar la nota informativa al turno");
			}
			hermod.addNotaToTurno(nota, new TurnoPk(turno.getIdWorkflow()));
			turno.addNota(nota);
			
			//02. SE QUITA LA MARCA DE TURNO PARA REANOTAR. Y SE DEJA EN MODO BLOQUEO CUALQUIERA
			Turno turnoActualizar = new Turno();
			turnoActualizar.setAnio(turno.getAnio());
			turnoActualizar.setIdCirculo(turno.getIdCirculo());
			turnoActualizar.setIdProceso(turno.getIdProceso());
			turnoActualizar.setIdTurno(turno.getIdTurno());
			turnoActualizar.setIdWorkflow(turno.getIdWorkflow());			
			turnoActualizar.setRevocatoria(false);
			turnoActualizar.setFechaFin(turno.getFechaFin());			
			hermod.actualizarMarcaRevocatoriaTurno(turnoActualizar);
			
			
			Solicitud solicitud = turno.getSolicitud();
			List foliosAsociados = null;
			if(solicitud !=null){
				foliosAsociados = solicitud.getSolicitudFolios(); 
			}
			
			//03. SE DESBLOQUEAN LOS FOLIOS SI ESTABAN ASIGNADOS AL TURNO.
			if(foliosAsociados!=null){
				Iterator it =  foliosAsociados.iterator();
				while(it.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					Folio folio = solFolio.getFolio();
					FolioPk fid = new FolioPk();
					fid.idMatricula = folio.getIdMatricula();
					
					try{
						Usuario usuarioBloqueo = forseti.getBloqueoFolio(fid);

						if(usuarioBloqueo!=null){
							//Se desbloquea el folio si estaba bloqueado.
							List llavesBloqueos= usuarioBloqueo.getLlavesBloqueos();
							LlaveBloqueo lb= (LlaveBloqueo)llavesBloqueos.get(0);
							List bloqueoFolios= lb.getBloqueoFolios();
							BloqueoFolio bf= (BloqueoFolio) bloqueoFolios.get(0);
							if(bf!=null&&bf.getIdWorkflowBloqueo()!=null){
								if(bf.getIdWorkflowBloqueo().equals(turno.getIdWorkflow())){
									forseti.desbloquearFolio(folio);	
								}								
							}
						}
					}catch(Exception e){
					}
				}				
			}			

		} catch (ANRevocatoriaRegistroException e) {
			throw e;
		} catch (HermodException e) {
			throw new ANRevocatoriaRegistroException("No se pudo consultar los turnos marcados para ser reanotados.", e);
		} catch (Throwable e) {
			throw new ANRevocatoriaRegistroException("No se pudo consultar los turnos marcados para ser reanotados. " + e.getMessage(), e);
		}

		EvnRespRecursosRevocatorias rta = new EvnRespRecursosRevocatorias(EvnRespRecursosRevocatorias.REANOTAR_RECURSOS, EvnRespRecursosRevocatorias.RECHAZAR_RECURSOS);
		return rta;

	}
}
