package gov.sir.core.negocio.acciones.correccion;
 /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan referencias a las clases GeneralSIRException,ValidacionesSIR
        */
import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR;
/*
        * @author Carlos Torres Urina
        * @caso matis: 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
        * @changed: se agregan referencias a las clases TrasladoSIR
        */
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.correccion.EvnRespRevision;
import gov.sir.core.eventos.correccion.EvnRevision;
import gov.sir.core.negocio.acciones.excepciones.ANCorreccionException;
import gov.sir.core.negocio.acciones.excepciones.AvanzarSinInformacionTemporalException;
import gov.sir.core.negocio.acciones.excepciones.BloqueoFoliosCorreccionHTException;
import gov.sir.core.negocio.acciones.excepciones.ConfrontacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.EliminarTurnoCorrecionesException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaConfrontacionCorrecionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.registro.SerialIds;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
/*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan referencias a las clases Level,Logger
        */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnConfrontacion</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespConfrontacion</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow de la fase de confrontación en el proceso de correcciones y llamar a los servicios
 * que se requieren en cada fase del proceso.
 * @author ppabon, jvelez
 */
public class ANRevision extends SoporteAccionNegocio {
	private Object printJobs;
	private ServiceLocator service = null;

	private HermodServiceInterface hermod = null;
	private ForsetiServiceInterface forseti = null;

	/**
	 *
	 */
	public ANRevision() throws EventoException {
		service = ServiceLocator.getInstancia();

		//OBTENER HERMOD
		try {
			hermod =
				(HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se encontró el servicio hermod:" + ep.toString());
			throw new ServicioNoEncontradoException(
				"No se encontró el servicio hermod:" + ep.toString(),
				e);
		}

		//OBTENER FORSETI
		try {
			forseti =
				(ForsetiServiceInterface) service.getServicio(
					"gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se encontró el servicio forseti:" + ep.toString());
			throw new ServicioNoEncontradoException(
				"No se encontró el servicio forseti:" + ep.toString(),
				e);
		}

		try {
			printJobs =
				(PrintJobsInterface) service.getServicio("gov.sir.print");
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException(
				"Error instanciando el servicio PrintJobs",
				e);
		}


	}

	/**
	 *
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnRevision evento = (EvnRevision) e;

		if (evento.getTipoEvento().equals(EvnRevision.ASOCIAR_UNA_MATRICULA)) {
			return asociarUnaMatricula(evento);

		} else if (
			evento.getTipoEvento().equals(
				EvnRevision.ELIMINAR_UNA_MATRICULA)) {
			return eliminarUnaMatricula(evento);

		} else if (evento.getTipoEvento().equals(EvnRevision.AVANZAR)) {
			avanzarTurno(evento, CAvanzarTurno.AVANZAR_CUALQUIERA);
			if (evento.getRespuestaWF().equals(CRespuesta.NEGAR)) {
				negarRevisionAnalisis(evento);
			}
			return new EvnRespRevision(evento.getTurno());	
		} else if (
			evento.getTipoEvento().equals(EvnRevision.ASOCIAR_UN_RANGO)) {
			return asociarUnRango(evento);

		} else if (
			evento.getTipoEvento().equals(EvnRevision.REDIRECCIONAR_CASO)) {
			redireccionarCorreccion(evento);
			return new EvnRespRevision(evento.getTurno());

		} else if (
			evento.getTipoEvento().equals(EvnRevision.REDIRECCIONAR_CASO_ACT)) {
			redireccionarCorreccionAct(evento);
			return new EvnRespRevision(evento.getTurno());


		} else if (
			evento.getTipoEvento().equals(EvnRevision.AVANZAR_PERMISOS)) {
            
			//PQ Se deja el bloqueo para ANCorrecion 
			//bloquearFolios(evento);
			establecerPermisos(evento);
			validarEstadoFolios(evento);
			avanzarTurno(evento, CAvanzarTurno.AVANZAR_CUALQUIERA);
			return new EvnRespRevision(evento.getTurno());

		}
		return null;
	}

	private void validarEstadoFolios(EvnRevision evento) throws EventoException {

		List solFolios = evento.getTurno().getSolicitud().getSolicitudFolios();
		Iterator iter = solFolios.iterator();
		String lst_matriculas = "";

		try {
			while(iter.hasNext()){
				String matricula = ((SolicitudFolio)iter.next()).getIdMatricula();
				if(forseti.cerradoFolio(matricula)){
					lst_matriculas += matricula + " ";
				}
                                /**
                                * @author: David Panesso
                                * @change: 1248.ASOCIAR.FOLIOS.CERRADOS.FASE.REVISION.ANALISIS
                                * Se validan folios en estado anulado
                                **/
                                else if(forseti.anuladoFolio(matricula)){
                                    lst_matriculas += matricula + " ";
                                }
			}
		} catch (ForsetiException e) {
			throw new EventoException("No se pudo continuar con la solicitud");
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		if(!lst_matriculas.equals("")){
			throw new EventoException("No se puede avanzar debido a que los siguientes folios se encuentran cerrados y/o anulados: "
					+ lst_matriculas);
		}
	}


	/**
	 * @param evento
	 */
	private void establecerPermisos(EvnRevision evento)
		throws EventoException {
		List permisos = evento.getPermisos();
		Turno turno = evento.getTurno();
		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();

		try {
			hermod.asignarPermisosCorreccion(oid, permisos);
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException(
				"No se pudo avanzar el turno",
				e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

	}

	/**
	 * Permite eliminar las matrículas que vienen en el evento, del turno que se esta confrontando
	 * en la fase confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta eliminarUnaMatricula(EvnRevision evento)
		throws EventoException {

		//OBTENER PARAMETROS DEL EVENTO
		Turno turno = evento.getTurno();
		
		List folios = evento.getFolios();
		Iterator it = folios.iterator();
		String idMatricula = null;
		
		boolean temporalTurno = false;
		FolioPk idFolio = new FolioPk();
		Hashtable ht = new Hashtable();
		
//		OBTENER ID DEL TURNO Y DEL FOLIO
		TurnoPk id = new TurnoPk();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();
		
		try {
			
			while (it.hasNext()) {
				Folio folio = (Folio) it.next();
				idMatricula = folio.getIdMatricula();
				
				idFolio.idMatricula = folio.getIdMatricula();
				
				temporalTurno = forseti.hasDatosTemporalesTurno(id, idFolio);

				if(temporalTurno){
					ht.put(folio.getIdMatricula(), "No se puede desasociar el folio " + folio.getIdMatricula() + " porque tiene modificaciones temporales.");
				} 
			}

			turno = hermod.getTurno(id);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se pudo eliminar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException(
				"No se pudo eliminar la matrícula",
				e);
		}
		
//		SI HAY FOLIOS QUE NO PUEDEN DESASOCIARSE SE LANZA LA EXCEPCIÓN.
		if(ht.size()>0){
			EliminarTurnoCorrecionesException excepcion = new EliminarTurnoCorrecionesException(ht);
			throw excepcion;
		} else {
				
			try {
				/**
                                        * @Author Carlos Torres
                                        * @Mantis 13176
                                        * @Chaged 
                                        */
                                        
                                        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                        java.util.Map infoUsuario = new java.util.HashMap();
                                        if(evento.getInfoUsuario() !=null){
                                            infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                            infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                            infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                            infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                            infoUsuario.put("idTurno",turno.getIdWorkflow());
                                        }
                                        auditoria.guardarDatosTerminal(infoUsuario);
				idMatricula = null;
				//SE REMUEVEN LOS FOLIOS, SI LOS FOLIOS ESCOGIDOS PUEDEN SER DESASOCIADOS DEL TURNO
				it = folios.iterator();
				while (it.hasNext()) {
					Folio folio = (Folio)it.next();
					idMatricula = folio.getIdMatricula();
					idFolio.idMatricula = folio.getIdMatricula();
					hermod.removeFolioFromTurno(idMatricula, id);
					
					/**Para el caso de que existan datos temporales por otro turno
					 * se valida el bloqueo del folio para poderlo desbloquear*/
					
					if(forseti.isBloqueadoByTurno(id, idFolio)){
						forseti.desbloquearFolio(folio);
					}
					
				}
                                /**
                                        * @Author Carlos Torres
                                        * @Mantis 13176
                                        * @Chaged 
                                        */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
				turno = hermod.getTurno(id);
			}catch (Throwable e) {
				ExceptionPrinter ep = new ExceptionPrinter(e);
				Log.getInstance().error(ANRevision.class,"No se pudo eliminar la matrícula:" + ep.toString());
				throw new TomarTurnoRegistroException("No se pudo eliminar la matrícula",e);
			}
		}

		return new EvnRespRevision(turno);
	}

	/**
	 * Permite asociar un rango de matrículas al turno que se esta confrontando en la
	 * fase de confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta asociarUnRango(EvnRevision evento)
		throws EventoException {

		//EXCEPCION MULTIPLE
		String mensajes = "";

		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		String idMatRL = evento.getIdMatriculaRL();
		String idMatRR = evento.getIdMatriculaRR();
               
                String[] idMatRLO  = idMatRL.split("-");
                String[] idMatRRO  = idMatRR.split("-");
                
                int orderRL = Integer.parseInt(idMatRLO[1]);
                int orderRR = Integer.parseInt(idMatRRO[1]);
                
                if (orderRR < orderRL){
                    idMatRL = evento.getIdMatriculaRR();
                    idMatRR = evento.getIdMatriculaRL();
                }
		//SE DEFINE UNA LISTA PARA AGREGAR LOS ERRORES
		List errores = new ArrayList();
                
		try {
			SerialIds si = new SerialIds(idMatRL, idMatRR);
			if (si.isSerialExists()) {
                                /**
                                * @autor Edgar Lora
                                * @mantis 11987
                                */
                                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                                ValidacionParametrosException exception = new ValidacionParametrosException();
                                
                                /**
                                * @Autor: Carlos Torres Urina
                                * @Mantis: 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios                               * 
                                */
                                TrasladoSIR trasladoSir = new TrasladoSIR();
                                while(si.hasNext()){
                                    String matricula = si.nextValue();
                                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                                        exception.addError("La matricula " + matricula + " se encuentra en estado 'Bloqueado'.");
                                    }
                                    /**
                                    * @Autor: Carlos Torres Urina
                                    * @Mantis: 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios                                * 
                                    */
                                    try {
                                        if(trasladoSir.isBloqueDeTraslado(matricula)){
                                            exception.addError("El folio " + matricula + " esta pendiente por confirmar traslado.");
                                        }
                                    } catch (GeneralSIRException ex) {
                                        exception.addError(ex.getMessage());
                                    }
                                    
                                }
                                if(exception.getErrores().size() > 0){
                                    throw exception;
                                }
                                
				//CREAR ID DEL TURNO
				TurnoPk id = new TurnoPk();
				id.anio = turno.getAnio();
				id.idCirculo = turno.getIdCirculo();
				id.idProceso = turno.getIdProceso();
				id.idTurno = turno.getIdTurno();
                                 /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                auditoria.guardarDatosTerminal(infoUsuario);
				hermod.addRangoFoliosToSolicitudRegistro(
					idMatRL,
					idMatRR,
					turno.getSolicitud(),
					evento.getUsuarioSIR(),
					false);
				turno = hermod.getTurno(id);
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
				if (!mensajes.equals("")) {
					errores.add(mensajes);
				}

				if (errores.size() > 0) {
					throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
				}

			} else {
				errores.add("El rango de matrículas es inválido");
				throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
			}
		} catch (MatriculaInvalidaConfrontacionCorrecionException ev) {
			throw ev;
		} catch (HermodException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException(
				"No se pudo asociar la matrícula",
				e);
		} catch (Throwable e) {
                        /**
                        * @autor Edgar Lora
                        * @mantis 11987
                        */
                        if(!(e instanceof ValidacionParametrosException)){
                            ExceptionPrinter ep = new ExceptionPrinter(e);
                            Log.getInstance().error(ANRevision.class,"No se pudo asociar la matrícula:" + ep.toString());
                            throw new ConfrontacionNoEfectuadaException(
                                    "Confrontaciones fallidas" + e.getMessage(),
                                    e);                            
                        }else{
                            String error = "";
                            ValidacionParametrosException ex = (ValidacionParametrosException)e;                            
                            List listaErrores = ex.getErrores();
                            for(int i = 0; i < listaErrores.size(); i = i + 1){                                
                                if(!error.isEmpty()){
                                    error = error + "<br />";
                                }
                                error = error + listaErrores.get(i).toString();
                            }
                            ConfrontacionNoEfectuadaException exception = new ConfrontacionNoEfectuadaException(error);
                            throw exception;
                        }
		}

		return new EvnRespRevision(turno);
	}

	/**
	 * Permite asociar una matrícula al turno que se esta confrontando en la
	 * fase de confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EvnRespRevision asociarUnaMatricula(EvnRevision evento)
		throws EventoException {

		//SE DEFINE UNA LISTA PARA AGREGAR LOS ERRORES
		List errores = new ArrayList();

		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		Folio folio = evento.getFolio();

		try {
			//SI EL FOLIO EXISTE
			if (forseti.existeFolio(folio.getIdMatricula())) {

                                // bug 3593
                                // incluir validacion para folios cerrados
                                block_ValidateFolioCerrado: {
                                   String matricula = folio.getIdMatricula();
                                   MatriculaInvalidaConfrontacionCorrecionException exception = new MatriculaInvalidaConfrontacionCorrecionException();
                                   
                                   /**
                                    * @author: David Panesso
                                    * @change: 1248.ASOCIAR.FOLIOS.CERRADOS.FASE.REVISION.ANALISIS
                                    * Se pueden agregar folios en estado cerrado 
                                    **/
                                   /*
                                   boolean buzz_ValidateCerradoFolio = true;
                                   buzz_ValidateCerradoFolio = forseti.cerradoFolio( matricula );                                
                                   
                                   if( buzz_ValidateCerradoFolio ) {
                                	   exception.addError("El folio identificado con matrícula:" + matricula + " esta cerrado.");
                                   }
                                   */
                                   
                                   if(forseti.trasladadoFolio(matricula)) {
                                	   exception.addError("El folio identificado con matrícula:" + matricula + " ha sido trasladado.");
                                   }
                                   
                                   boolean buzz_ValidateInconsistenteFolio = isInconsistenteFolio( matricula );

                                   if( buzz_ValidateInconsistenteFolio ) {
                                	   exception.addError("El folio identificado con matrícula:" + matricula + " es inconsistente.");
                                   }
                                   
                                   if(exception.getErrores().size() > 0) {
                                	   throw exception;
                                   }


                                } // :block_ValidateFolioCerrado


				//ASOCIARLO
				TurnoPk id = new TurnoPk();
				id.anio = turno.getAnio();
				id.idCirculo = turno.getIdCirculo();
				id.idProceso = turno.getIdProceso();
				id.idTurno = turno.getIdTurno();

				Solicitud sol = turno.getSolicitud();
				if (sol != null) {
					List listaSolicitudes = sol.getSolicitudFolios();

					//Verificar si la matrícula que se va a añadir está en la lista
					//de solicitudes folio de la solicitud.  En ese caso se lanza una
					//excepción.
					if (listaSolicitudes != null) {
						for (int i = 0; i < listaSolicitudes.size(); i++) {
							SolicitudFolio solFolio =
								(SolicitudFolio) listaSolicitudes.get(i);
							if (solFolio != null) {
								if (solFolio
									.getIdMatricula()
									.equals(folio.getIdMatricula())) {
									errores.add(
										"La matricula que intenta ingresar ya está asociada a la solicitud.");
									throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
								}
							}
						}
					}
				}
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                auditoria.guardarDatosTerminal(infoUsuario);            
				SolicitudFolioPk sfId =
					hermod.addFolioToTurno(
						folio.getIdMatricula(),
						id,
						evento.getUsuarioSIR());
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
				//AÑADIRLO AL OBJETO TURNO
				SolicitudFolio sfolio = new SolicitudFolio();
				sfolio.setFolio(folio);
				sfolio.setSolicitud(turno.getSolicitud());
				turno.getSolicitud().addSolicitudFolio(sfolio);
                                 
			} else {
				errores = new ArrayList();
				errores.add(
					"La matrícula " + folio.getIdMatricula() + " no existe.");
				throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
			}

		} catch (MatriculaInvalidaConfrontacionCorrecionException ev) {
			throw ev;
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException(
				"No se pudo asociar la matrícula",
				e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException(
				"No se pudo asociar la matrícula",
				e);
		}

		return new EvnRespRevision(turno);
	}

	 /**
     * valida que una matrícula no este inconsistente.
     */
    private boolean isInconsistenteFolio(String matricula) throws EventoException {

           boolean buzz_ValidateInconsistenteFolio = true;
           try {

              buzz_ValidateInconsistenteFolio = forseti.inconsistenteFolio( matricula );

           }
           catch (ServiceLocatorException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANRevision.class,"No se pudo encontrar el servicio:" + ep.toString());
                  throw new EventoException("Servicio no encontrado", e);
           }
           catch (ForsetiException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANRevision.class,"Error en el servicio para validar la matrícula:" + ep.toString());
                  throw new EventoException("Error en el servicio para validar la matrícula", e);
           }
           catch (Throwable e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANRevision.class,"Se produjo un error validando la matrícula:" + ep.toString());
                  throw new EventoException("Se produjo un error validando la matrícula", e);

           } // try

           return buzz_ValidateInconsistenteFolio;

    }

    /**
	 * @param evento
	 * @throws EventoException
	 */
	private void bloquearFolios(EvnRevision evento) throws EventoException{
        Turno turno=evento.getTurno();
        Usuario usr=evento.getUsuarioSIR();
        //LlaveBloqueo llaveBloqueo = new LlaveBloqueo();

        TurnoPk turnoID = new TurnoPk();
        turnoID.idCirculo = turno.getIdCirculo();
        turnoID.idProceso = turno.getIdProceso();
        turnoID.idTurno = turno.getIdTurno();
        turnoID.anio = turno.getAnio();

        try {
            forseti.validarPrincipioPrioridadCorreccion(turnoID);
            forseti.delegarBloqueoFolios(turnoID, usr);
        } catch (ForsetiException e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
            /**
             * @author Cesar Ramírez
             * @change: 1156.111.USUARIOS.ROLES.INACTIVOS
             * Controla la excepción para visualizarla en pantalla.
             **/
            String mensaje = new String();
            if (!e.getErrores().isEmpty()) {
                for (Object str : e.getErrores()) {
                    mensaje += str.toString();
                }
            }
            BloqueoFoliosCorreccionHTException ex = new BloqueoFoliosCorreccionHTException(mensaje, e);
            throw ex;
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
    }
	
	/**
	 * @param evento
	 * @throws EventoException
	 */
	private void negarRevisionAnalisis(EvnRevision evento) throws EventoException{
        Turno turno = evento.getTurno();
        Usuario usr = evento.getUsuarioSIR();
        
        //Se deben desbloquear los folios
        
        TurnoPk turnoID = new TurnoPk();
        turnoID.idCirculo = turno.getIdCirculo();
        turnoID.idProceso = turno.getIdProceso();
        turnoID.idTurno = turno.getIdTurno();
        turnoID.anio = turno.getAnio();
        
        Solicitud solicitud = turno.getSolicitud();
        
		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		List folios = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			matriculas.add(sol.getFolio().getIdMatricula());

			Folio folio = new Folio();
			folio.setIdMatricula(sol.getFolio().getIdMatricula());
			folio.setZonaRegistral(sol.getFolio().getZonaRegistral());
			folios.add(folio);
		}

		Iterator it = null;


                // VERIFY: si se quiere implementar una opcion de deshacer,
                // se deben deshacer primero
                // algunos cambios en los folios padre o folios hijo
                // especificamente, las anotaciones temporales creadas
                // para especificar los folios derivados padre o folios derivados hijo (creados o a eliminar).


                it = folios.iterator();

        try {
			forseti.desbloquearFoliosTurno(turnoID,usr);
			forseti.bloquearFolios(matriculas, usr, turnoID);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		while (it.hasNext()) {
			Folio folio = (Folio) it.next();

			try {
				forseti.deshacerCambiosFolio(folio, usr);
			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}
                /*      
                 * @author Carlos Torres Urina
                 * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                 * @changed: instruciones para des hacer cambios en turnos de correccion de testamento 
                 */
                Turno turnoAnterior = solicitud.getTurnoAnterior();
                if(turnoAnterior != null && turnoAnterior.getIdProceso() == 6){
                    try
                    {
                        
                          TestamentoSIR testsir = new TestamentoSIR();
                          testsir.deshacerCambiosTestamento(turnoAnterior.getSolicitud().getIdSolicitud(), turno.getIdWorkflow());     
                        
                    }catch(GeneralSIRException GSirE)
                    {
                        Logger.getLogger(ANRevision.class.getName()).log(Level.SEVERE, null, GSirE);
                        throw new EventoException(GSirE.getMessage(), GSirE);
                        
                    }
                }

        try {
        	//dado un turno, desbloquear los folios que tiene asociados el turno siempre y cuando el bloqueo lo tenga el turno
        	forseti.desbloquearFoliosTurno(turnoID, usr);
        } catch (ForsetiException e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada desbloqueando los folios : " + printer.toString());
            throw new BloqueoFoliosCorreccionHTException(e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada desbloqueando los folios : " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
    }




	//ACTUALIZADO VALIDACION NOTAS: ENERO 17 2006
	/**
	 * Método que permite delegar el turno a otros roles.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta redireccionarCorreccion(EvnRevision evento)
		throws EventoException {
		EvnRespRevision evRespuesta = new EvnRespRevision(evento.getTurno());

        bloquearFolios(evento);
        
        // TFS 5353
        // No se puede cambiar de proceso si se hicieron cambios temporales
        TurnoPk turnoId = new TurnoPk(evento.getTurno().getIdWorkflow());
        
        try {
			List deltaFolios = forseti.getDeltaFolios(turnoId, evento.getUsuarioSIR());
			if (deltaFolios != null && !deltaFolios.isEmpty())
			{
				throw new AvanzarSinInformacionTemporalException("Existen cambios temporales creados con el turno");
			}
		} catch (Throwable e) {
			if (e instanceof AvanzarSinInformacionTemporalException)
			{
				throw (AvanzarSinInformacionTemporalException)e;
			}
			else {
				throw new EventoException("Error avanzando el turno",e);
			}
		}

		//Se incluye un bloque try / catch para capturar la excepción que pueda
		//presentarse en caso de que el turno no tenga la nota informativa necesaria
		//para avanzar en la fase y con la respuesta dada.
		if (evento.getRespuestaWF().equals(EvnRevision.ACTUACION_ADMINISTRATIVA))
		{
			try {

			       avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);
			       evRespuesta.setTipoEvento(EvnRespRevision.ACTOS_ADMIN);
			}
			catch (Throwable t){
				if (t instanceof TurnoNoAvanzadoException)
				{
					throw (TurnoNoAvanzadoException)t;
				}
				else 
				{
					throw new EventoException("Error avanzando el turno",t);
				}
			}

		}


         //Se incluye un bloque try / catch para capturar la excepción que pueda
		 //presentarse en caso de que el turno no tenga la nota informativa necesaria
		 //para avanzar en la fase y con la respuesta dada.
		 else if (evento.getRespuestaWF().equals(EvnRevision.ANTIGUO_SISTEMA))
			{

                try {
					Solicitud solicitud = evento.getTurno().getSolicitud();
					DatosAntiguoSistema datosAntiguoSsistema = evento.getDatosAntiguoSistema();
					hermod.setDatosAntiguoSistemaToSolicitud(solicitud, datosAntiguoSsistema);
					avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);
					evRespuesta.setTipoEvento(EvnRespRevision.ANTIGUO_SISTEMA);


                }

                catch (Throwable t) {

					evRespuesta.setTipoEvento(EvnRespRevision.ANTIGUO_SISTEMA);
					if (t instanceof TurnoNoAvanzadoException)
					{
						throw (TurnoNoAvanzadoException)t;
					}
					else 
					{
						throw new EventoException("Error avanzando el turno",t);
					}
                }



		}


		//Se incluye un bloque try / catch para capturar la excepción que pueda
		//presentarse en caso de que el turno no tenga la nota informativa necesaria
		//para avanzar en la fase y con la respuesta dada.
		else if (evento.getRespuestaWF().equals(EvnRevision.MAYOR_VALOR))
		{
		    try {
				avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);
				evRespuesta.setTipoEvento(EvnRespRevision.MAYOR_VALOR);
		    }
			catch (Throwable t) {

				evRespuesta.setTipoEvento(EvnRespRevision.ANTIGUO_SISTEMA);
				if (t instanceof TurnoNoAvanzadoException)
				{
					throw (TurnoNoAvanzadoException)t;
				}
				else 
				{
					throw new EventoException("Error avanzando el turno",t);
				}
			}


		}

		return evRespuesta;
	}




     //	ACTUALIZADO VALIDACION NOTAS: ENERO 17 2006
	/**
	 * Método que permite delegar el turno a otros roles.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta redireccionarCorreccionAct(EvnRevision evento)
		throws EventoException {
		EvnRespRevision evRespuesta = new EvnRespRevision(evento.getTurno());


		if (evento.getRespuestaWF().equals(EvnRevision.ANTIGUO_SISTEMA)) {

			Solicitud solicitud = evento.getTurno().getSolicitud();
			DatosAntiguoSistema datosAntiguoSsistema = evento.getDatosAntiguoSistema();

				try {

					hermod.setDatosAntiguoSistemaToSolicitud(solicitud, datosAntiguoSsistema);
					avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);
				} catch (Throwable t) {

					throw new EventoException("Error avanzando el turno",t);
				}

			evRespuesta.setTipoEvento(EvnRespRevision.ANTIGUO_SISTEMA);

		}

		//Colocar bloque try / catch para validar avance del turno.
		else if (evento.getRespuestaWF().equals(EvnRevision.MAYOR_VALOR)) {

			try {
				avanzarTurno(evento, CAvanzarTurno.AVANZAR_PUSH);
				evRespuesta.setTipoEvento(EvnRespRevision.MAYOR_VALOR);
			}

			catch (Throwable t) {

				throw new EventoException("Error avanzando el turno",t);
			}


		}

		return evRespuesta;
	}




     //	ACTUALIZADO VALIDACION NOTAS: ENERO 17 2006
	/**
	 * Este metodo llama el servicio Hermod para poder hacer el avance del turno.
	 * El método valida la exigencia de notas informativas, para la fase y respuesta dadas.
	 * @param evento EvnDevolucion
	 * @return EventoRespuesta
	 * @throws EventoException
	 */
	public void avanzarTurno(EvnRevision evento, int tipoAvance)
		throws EventoException {
		Hashtable tabla = new Hashtable();

		//Se obtienen los parámetros de avance desde el evento de parámetros.
		Turno turno = evento.getTurno();
                /*    
                     * @author Carlos Torres Urina
                    * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                    * @changed: se agrega variable turno anterior
                    */
                Turno turnoAnterior = turno.getSolicitud().getTurnoAnterior();
		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR,	evento.getUsuarioSIR().getUsername());



		//3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try
		{
		     Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
		     throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}
            /*      * @author Carlos Torres Urina
                    * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                    * @changed: se agregan validacion de prioridad de turnos de testamento
                    */
                if(turnoAnterior != null && turnoAnterior.getIdProceso() == 6){
                    try
                    {
                        if(!evento.getRespuestaWF().equals(CRespuesta.NEGAR)){
                            ValidacionesSIR validar = new ValidacionesSIR();
                            if(!validar.validarPrioridadTurno(turno.getIdWorkflow(), turnoAnterior.getIdWorkflow()))
                            {
                            throw  new EventoException("No es posible avanzar este turno. Existen turno(s) anteriores que tienen prioridad");
                            }
                        }
                    }catch(GeneralSIRException GSirE)
                    {
                        Logger.getLogger(ANRevision.class.getName()).log(Level.SEVERE, null, GSirE);
                    }
                }
                
		try {
			if (tipoAvance==CAvanzarTurno.AVANZAR_CUALQUIERA){
				hermod.avanzarTurnoNuevoCualquiera(turno,fase,tabla,evento.getUsuarioSIR());
			}else if (tipoAvance==CAvanzarTurno.AVANZAR_PUSH){
				hermod.avanzarTurnoNuevoPush(turno,fase,tabla,evento.getUsuarioSIR());
			}
			//hermod.avanzarTurno(turno, fase, tabla, tipoAvance);
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException(
				"No se pudo avanzar el turno",
				e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
	}

}
