package gov.sir.core.negocio.acciones.registro;

import co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.eventos.registro.EvnConfrontacion;
import gov.sir.core.eventos.registro.EvnRespConfrontacion;
import gov.sir.core.negocio.acciones.excepciones.ConfrontacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaConfrontacionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.EliminarTurnoConfrontacionException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;

import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CConfrontacion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;



/**
 * @author jfrias, dsalas, ppabon
 */
public class ANConfrontacion extends SoporteAccionNegocio {
	private ServiceLocator service = null;
	
	private ForsetiServiceInterface forseti;

	private HermodServiceInterface hermod = null;

	/**
	 * 
	 */
	public ANConfrontacion() throws EventoException {
		service = ServiceLocator.getInstancia();
		
		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
		
		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio hermod no encontrado");
		}

		
	}

	/**
	 *
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnConfrontacion evento = (EvnConfrontacion) e;

		if (evento.getTipoEvento().equals(EvnConfrontacion.ASOCIAR_UNA_MATRICULA)) {
			return asociarUnaMatricula(evento);
		} else if (evento.getTipoEvento().equals(EvnConfrontacion.ELIMINAR_UNA_MATRICULA)) {
			return eliminarUnaMatricula(evento);
		} else if (evento.getTipoEvento().equals(EvnConfrontacion.CONFIRMAR)) {
			return confirmar(evento);
		} else if (evento.getTipoEvento().equals(EvnConfrontacion.ASOCIAR_UN_RANGO)) {
			return asociarUnRango(evento);
		}else if (evento.getTipoEvento().equals(EvnConfrontacion.ACTUALIZAR_DATOS_ANTIGUO_SISTEMA)) {
			return actualizarDatosAntiguoSistema(evento);
		}else if (evento.getTipoEvento().equals(EvnConfrontacion.GURDAR_CAMBIOS_CONFRONTACION)){
			return guardarCambiosConfrontacion(evento);
		}
		return null;
	}

	private EventoRespuesta guardarCambiosConfrontacion(EvnConfrontacion evento) throws EventoException {
		Solicitud solicitud = evento.getTurno().getSolicitud();
		String idSubtipoSolicitud = evento.getSubTipoAtencion();
		try{
			hermod.updateSubtipoSolicitud(idSubtipoSolicitud, solicitud.getIdSolicitud());
			SolicitudRegistro sol = (SolicitudRegistro)hermod.getSolicitudById(evento.getTurno().getSolicitud().getIdSolicitud());
			((SolicitudRegistro)evento.getTurno().getSolicitud()).setSubtipoSolicitud(sol.getSubtipoSolicitud());
		}catch(HermodException h){
			throw new TurnoNoAvanzadoException(h.getMessage());
		}catch(Throwable t){
			throw new TurnoNoAvanzadoException(t.getMessage());
		}
		return new EvnRespConfrontacion(evento.getTurno());
	}

	/**
	 * Permite eliminar las matrículas que vienen en el evento, del turno que se esta confrontando
	 * en la fase confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta eliminarUnaMatricula(EvnConfrontacion evento) throws EventoException {

		//OBTENER PARAMETROS DEL EVENTO
		Turno turno = evento.getTurno();
		
		boolean temporalTurno = false;
		FolioPk idFolio = new FolioPk();
		Hashtable ht = new Hashtable();
		List matruculasNoEliminables = null;
		
		/*try {
			//SE OBTIENE LA INFORMACIÓN PARA DETERMINAR SI SE PUEDE O NO DESASOCIAR LAS MATRÍCULAS.
			//NO SE PUEDE CUANDO EL FOLIO TIENE COSAS TEMPORALES.
			idFolio.idMatricula = folio.getIdMatricula();
			idFolio.idZonaRegistral = forseti.getZonaRegistral(folio.getIdMatricula());
			temporalTurno = false;

			temporalTurno = forseti.hasDatosTemporalesTurno(id, idFolio);

			if(temporalTurno){
				ht.put(folio.getIdMatricula(), "No se puede desasociar el folio " + folio.getIdMatricula() + " porque tiene modificaciones temporales.");
			}

		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			logger.error("No se pudo desasociar la matrícula" + ep.toString());
			throw new TomarTurnoRegistroException("No se pudo desasociar la matrícula",e);
		}*/
		
		TurnoPk id = new TurnoPk();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();
		
		List folios = evento.getFolios();
		Iterator it = folios.iterator();
		String idMatricula = null;
		
		try {
			//OBTENER ID DEL TURNO Y DEL FOLIO
			
			Turno turnoDependiente = hermod.getTurnoDependiente(id);
			if (turnoDependiente!=null){
				matruculasNoEliminables = new ArrayList();
				Iterator iter = turnoDependiente.getSolicitud().getSolicitudFolios().iterator();
				while (iter.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)iter.next();
					if(solFolio.getIdMatricula()!=null) matruculasNoEliminables.add(solFolio.getIdMatricula());
				}
			}
			
			while (it.hasNext()) {
				Folio folio = (Folio)it.next();
				idMatricula = folio.getIdMatricula();
				boolean error = false;
				idFolio.idMatricula = folio.getIdMatricula();
				
				temporalTurno = forseti.hasDatosTemporalesTurno(id, idFolio);
                                
                                
				if(temporalTurno){
					ht.put(folio.getIdMatricula(), "No se puede desasociar el folio " + folio.getIdMatricula() + " porque tiene modificaciones temporales.");
                                        error = true;
				}
				if(matruculasNoEliminables!=null && matruculasNoEliminables.contains(idMatricula)){
					ht.put(folio.getIdMatricula(), "No se puede desasociar el folio " + folio.getIdMatricula() + " porque esta en el turno de correccion " + turnoDependiente.getIdWorkflow() +
							", del cual depende éste turno");
                                        error = true;
				}
                            
			}
			 
			turno = hermod.getTurno(id);	
			//TFS 5351: Registrar la ultima matricula desasociada del turno
			hermod.registrarMatriculaEliminadaTurno(idMatricula, id);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo eliminar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo eliminar la matrícula",e);
		}
		
//		SI HAY FOLIOS QUE NO PUEDEN DESASOCIARSE SE LANZA LA EXCEPCIÓN.
		if(ht.size()>0){
			EliminarTurnoConfrontacionException excepcion = new EliminarTurnoConfrontacionException(ht);
			throw excepcion;
		} else {
				
			try {
				
				idMatricula = null;
				//SE REMUEVEN LOS FOLIOS, SI LOS FOLIOS ESCOGIDOS PUEDEN SER DESASOCIADOS DEL TURNO
				it = folios.iterator();
				while (it.hasNext()) {
					Folio folio = (Folio)it.next();
					idMatricula = folio.getIdMatricula();
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
                                        
                                        if(evento.getFase().getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
                                            hermod.addCtrlMatricula(idMatricula, "0", "CORRECTIVO", turno.getIdWorkflow());
                                        } else{
                                            hermod.addCtrlMatricula(idMatricula, "0", "CONFRONTADOR", turno.getIdWorkflow());
                                        }
                                        
                                        
                                       	hermod.removeFolioFromTurno(idMatricula, id);
					forseti.desbloquearFolio(folio);
                                        /**
                                        * @Author Carlos Torres
                                        * @Mantis 13176
                                        * @Chaged 
                                        */
                                        auditoria.borrarDatosTerminal(turno.getIdWorkflow());
				}
				turno = hermod.getTurno(id);
			}catch (Throwable e) {
				ExceptionPrinter ep = new ExceptionPrinter(e);
				Log.getInstance().error(ANConfrontacion.class,"No se pudo eliminar la matrícula:" + ep.toString());
				throw new TomarTurnoRegistroException("No se pudo eliminar la matrícula",e);
			}
		}
		
		return new EvnRespConfrontacion(turno);
	}
	
	

	/**
	 * @param evento
	 * @return
	 */
    private EvnRespConfrontacion confirmar(EvnConfrontacion evento) throws EventoException {
        //OBTENER PARAMETROS DEL EVENTO
      
        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        int tipoAvance = evento.getTipoAvance();
        TurnoPk idTurno = new TurnoPk();
        idTurno.idCirculo = turno.getIdCirculo();
        idTurno.idProceso = turno.getIdProceso();
        idTurno.idTurno = turno.getIdTurno();
        idTurno.anio = turno.getAnio();
        Solicitud sol = turno.getSolicitud();

        List nuevosFolios = new ArrayList();
        Turno turnoDependiente = null;
        String idSubTipoAtencion = evento.getSubTipoAtencion();
        Usuario usuarioSIR = evento.getUsuarioSir();
        Hashtable tabla = new Hashtable();
        String USUARIO_INICIADOR = (null != evento.getUsuarioSir()) ? ("" + evento.getUsuarioSir().getUsername()) : ("");
        if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
            throw new TurnoNoAvanzadoException("El usuario no se ha registrado en la capa web." + this.getClass().getName());
        }
        String resultadoAvance = null;
        boolean isTestamento = idSubTipoAtencion.equals(CSubtipoSolicitud.TESTAMENTO);
        try {
            //DELEGAR EL TURNO A CONFRONTACION CORRECTIVA.
            try {
                turnoDependiente = hermod.getTurnoDependiente(idTurno);
                if (turnoDependiente != null && isTestamento) {
                    throw new TurnoNoAvanzadoException("Este turno depende del turno No.: " + turnoDependiente.getIdWorkflow()
                            + ". Por lo tanto no puede ser llevado a Testamento sino a Calificacion");
                }
            } catch (HermodException h) {
                throw new TurnoNoAvanzadoException(h.getMessage());
            } catch (Throwable t) {
                throw new TurnoNoAvanzadoException(t.getMessage());
            }

            if (fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA) && !isTestamento) {
                
                String revisionConfrontacionCorrectiva = CFase.REV_REVISION_CONFRONTACION;
                // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
                //del turno desde esta fase. 
                try {
                    Hashtable tablaAvance = new Hashtable(2);
                    tablaAvance.put(Processor.RESULT, CConfrontacion.EXITO);
                    tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                    hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
                } catch (Throwable t) {
                    throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                }
                /**
                 * *****************************************************
                 */
                /*            CAMBIO ELIMINAR SAS                       */
                /**
                 * *****************************************************
                 */
                Fase faseAvance = evento.getFase();
                gov.sir.core.negocio.modelo.Usuario usuarioSIRAvance = evento.getUsuarioSir();
                Hashtable tablaAvanzar = new Hashtable();
                tablaAvanzar.put(Processor.RESULT, CConfrontacion.EXITO);
                try {
                    forseti.bloquearFolios(nuevosFolios, usuarioSIR, idTurno);
                    hermod.avanzarTurnoNuevoNormal(turno, faseAvance, tablaAvanzar, usuarioSIRAvance);
                } catch (Throwable e) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", e);
                }
            } else {
                // DELEGAR TURNO CONFRONTACION NO ANTIGUO SISTEMA
                if ((idSubTipoAtencion.equals(CSubtipoSolicitud.NORMAL) || idSubTipoAtencion.equals(CSubtipoSolicitud.MAS_MATRICULAS)) && !isTestamento) {
                    tabla.put(Processor.RESULT, CConfrontacion.NO_ANTIGUO_SISTEMA);
                    resultadoAvance = CConfrontacion.NO_ANTIGUO_SISTEMA;
                  
                    // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
                    //del turno desde esta fase. 
                    try {
                        Hashtable tablaAvance = new Hashtable(2);
                        tablaAvance.put(Processor.RESULT, CConfrontacion.NO_ANTIGUO_SISTEMA);
                        tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                        hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
                    } catch (Throwable t) {
                        throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                    }
                } else {                    
                    // Se debe determinar si la siguiente fase será o no Antiguo Sistema de acuerdo
                    // a los datos suministrados en el formulario para Antiguo Sistema.
                    SolicitudRegistro sSolicitudRegistro = (SolicitudRegistro) sol;
                    DatosAntiguoSistema dasDatos = sSolicitudRegistro.getDatosAntiguoSistema();

                    // Si no hay datos de antiguo sistema, el turno no debe ir a Antiguo Sistema
                    // DELEGAR TURNO CONFRONTACION NO ANTIGUO SISTEMA
                    if (dasDatos == null) {
                        resultadoAvance = CConfrontacion.NO_ANTIGUO_SISTEMA;
                        
                        // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
                        //del turno desde esta fase. 
                        try {
                            Hashtable tablaAvance = new Hashtable(2);
                            tablaAvance.put(Processor.RESULT, CConfrontacion.NO_ANTIGUO_SISTEMA);
                            tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
                        } catch (Throwable t) {
                            throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                        }

                    } else {
                        // Si hay datos de antiguo sistema, el turno  debe ir a Antiguo Sistema
                        // DELEGAR TURNO CONFRONTACION NO ANTIGUO SISTEMA
                        if (dasDatos.getComentario() != null || dasDatos.getDocumento() != null
                                || dasDatos.getFechaCreacion() != null || dasDatos.getLibroAnio() != null
                                || dasDatos.getLibroNumero() != null || dasDatos.getLibroPagina() != null
                                || dasDatos.getLibroTipo() != null || dasDatos.getTomoAnio() != null
                                || dasDatos.getTomoMunicipio() != null || dasDatos.getTomoNumero() != null
                                || dasDatos.getTomoPagina() != null) {

                            // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
                            //del turno desde esta fase. 
                            try {
                                Hashtable tablaAvance = new Hashtable(2);
                                tablaAvance.put(Processor.RESULT, CConfrontacion.ANTIGUO_SISTEMA);
                                tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
                            } catch (Throwable t) {
                                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                            }
                            resultadoAvance = CConfrontacion.ANTIGUO_SISTEMA;
                        } else {

                            //	3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
                            //del turno desde esta fase. 
                            try {
                                Hashtable tablaAvance = new Hashtable(2);
                                tablaAvance.put(Processor.RESULT, CConfrontacion.NO_ANTIGUO_SISTEMA);
                                tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                                hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
                            } catch (Throwable t) {
                                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
                            }
                            resultadoAvance = CConfrontacion.NO_ANTIGUO_SISTEMA;
                        }
                    }
                }

                boolean testamento = idSubTipoAtencion.
                        equals(CSubtipoSolicitud.TESTAMENTO);
                if (testamento) {
                    resultadoAvance = CConfrontacion.TESTAMENTO;

                }
                /*
                                * @author : CTORRES
                                * @change : Se agregan intruciones para actualizar el sub tipo de solicitud
                                * Caso Mantis : 12291
                                * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                 */
                if (!idSubTipoAtencion.equals(((SolicitudRegistro) sol).getSubtipoSolicitud().getIdSubtipoSol())) {
                    try {
                        hermod.updateSubtipoSolicitud(idSubTipoAtencion, sol.getIdSolicitud());
                        SolicitudRegistro sololicitud = (SolicitudRegistro) hermod.getSolicitudById(evento.getTurno().getSolicitud().getIdSolicitud());
                        ((SolicitudRegistro) evento.getTurno().getSolicitud()).setSubtipoSolicitud(sololicitud.getSubtipoSolicitud());
                    } catch (HermodException h) {
                        throw new TurnoNoAvanzadoException(h.getMessage());
                    } catch (Throwable t) {
                        throw new TurnoNoAvanzadoException(t.getMessage());
                    }
                }
                /**
                 * *****************************************************
                 */
                /*            CAMBIO ELIMINAR SAS                       */
                /**
                 * *****************************************************
                 */
                Fase faseAvance = evento.getFase();
                gov.sir.core.negocio.modelo.Usuario usuarioSIRAvance = evento.getUsuarioSir();

                Hashtable tablaAvanzar = new Hashtable();
                tablaAvanzar.put(Processor.RESULT, resultadoAvance);

                try {
                    hermod.avanzarTurnoNuevoNormal(turno, faseAvance, tablaAvanzar, usuarioSIRAvance);

                } catch (Throwable e) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", e);
                }

            }
        } catch (Throwable e1) {
            throw new ConfrontacionNoEfectuadaException("No se pudo confirmar la confrontacion", e1);
        }
        return null;
    }

	/**
	 * @param actos
	 * @return
	 */
	private boolean tieneTestamentos(List actos){
		boolean tieneTestamento = false;
		if(actos !=null){
			Iterator it = actos.iterator();
			while(it.hasNext()){
				Acto acto = (Acto)it.next();
				if(acto.getTipoActo().getIdTipoActo().equals(CActo.ID_TESTAMENTOS)||acto.getTipoActo().getIdTipoActo().equals(CActo.ID_REVOCATORIA_TESTAMENTO)){
					tieneTestamento = true;
					break;
				}
			}
		}
		
		return tieneTestamento;
	}


	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta asociarUnRango(EvnConfrontacion evento) throws EventoException {

		//EXCEPCION MULTIPLE
		String mensajes = "";

		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		String idMatRL = evento.getIdMatriculaRL();
		String idMatRR = evento.getIdMatriculaRR();
		gov.sir.core.negocio.modelo.Usuario userSir = evento.getUsuarioSir();

		//SE DEFINE UNA LISTA PARA AGREGAR LOS ERRORES
		
		boolean validarAsociar = false;

		try {
			
			if (turno.getIdFase().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				validarAsociar = true;
			}
			
			SerialIds si = new SerialIds(idMatRL, idMatRR);
                        /**
                        * @autor Edgar Lora
                        * @mantis 11987
                        */
			if (si.isSerialExists()) {				
                                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                                ValidacionParametrosException exception = new ValidacionParametrosException();
                                while(si.hasNext()){
                                    String matricula = si.nextValue();
                                    
                                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                                        exception.addError("La matricula " + matricula + " se encuentra en estado 'Bloqueado'.");
                                    }                                    
                                }
                                if(exception.getErrores().size() > 0){
                                    throw exception;
                                }
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
				hermod.addRangoFoliosToSolicitudRegistro(idMatRL, idMatRR, turno.getSolicitud(), userSir, validarAsociar); 
				turno = hermod.getTurno(id);
                                
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
/*
				if (!mensajes.equals("")) {
						errores.add(mensajes);
				}

				if (errores.size() > 0) {
					throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
				}
				
			} 
			else {
				errores.add("El rango de matrículas es inválido");
				throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
			}*/
                            
                        si = new SerialIds(idMatRL, idMatRR);

                        if (si.isSerialExists()) {				

                                while(si.hasNext()){
                                    String matricula = si.nextValue();
                        
                                            if(evento.getFase().getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
                                                hermod.addCtrlMatricula(matricula, "1", "CORRECTIVO", turno.getIdWorkflow());
                                            } else{
                                                hermod.addCtrlMatricula(matricula, "1", "CONFRONTADOR", turno.getIdWorkflow());
                                            }
                                }
                        }
				
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo asociar la matrícula",e);
		} catch (Throwable e) {
                        /**
                        * @autor Edgar Lora
                        * @mantis 11987
                        */
                        if(!(e instanceof ValidacionParametrosException)){
                            ExceptionPrinter ep = new ExceptionPrinter(e);
                            Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
                            throw new ConfrontacionNoEfectuadaException("Confrontaciones fallidas: " + e.getMessage(),e);
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

		return new EvnRespConfrontacion(turno);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EvnRespConfrontacion asociarUnaMatricula(EvnConfrontacion evento) throws EventoException {

        //SE DEFINE UNA LISTA PARA AGREGAR LOS ERRORES
		List errores = new ArrayList();
		
		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		Folio folio = evento.getFolio();
		Usuario userSir = evento.getUsuarioSir();
		
		boolean validarAsociar = false;

		try {
			
			if (turno.getIdFase().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				validarAsociar = true;
			}
			
			//SI EL FOLIO EXISTE
			if (forseti.existeFolio(folio.getIdMatricula())) {

                                /*
                                * @author      :   Julio Alcázar Rivas
                                * @change      :   Se valida que el folio no este en estado Trasladado
                                * Caso Mantis  :   07123
                                */
                                if(forseti.trasladadoFolio(folio.getIdMatricula())){
                                    errores.add("El folio identificado con matrícula:" + folio.getIdMatricula() + " ha sido trasladado.");
				    throw new MatriculaInvalidaConfrontacionException(errores);
			        }
				//ASOCIARLO
				TurnoPk id = new TurnoPk();
				id.anio = turno.getAnio();
				id.idCirculo = turno.getIdCirculo();
				id.idProceso = turno.getIdProceso();
				id.idTurno = turno.getIdTurno();
				
				Solicitud sol = turno.getSolicitud();
				
				if (sol != null)
				{
					List listaSolicitudes = sol.getSolicitudFolios();
					
					//Verificar si la matrícula que se va a añadir está en la lista
					//de solicitudes folio de la solicitud.  En ese caso se lanza una
					//excepción. 
					if (listaSolicitudes !=null)
					{
						for (int i=0; i<listaSolicitudes.size();i++)
						{
							SolicitudFolio solFolio = (SolicitudFolio) listaSolicitudes.get(i);
							if (solFolio!=null)
							{
								if (solFolio.getIdMatricula().equals(folio.getIdMatricula()))
								{
									errores.add("La matricula que intenta ingresar ya está asociada a la solicitud.");
									throw new MatriculaInvalidaConfrontacionException(errores);
								}
							}
						}
					} 
				}
				
				List listaVal = hermod.getValidacionesSolicitud(sol);
				List matriculas = new ArrayList();
				matriculas.add(folio.getIdMatricula());
				forseti.validarMatriculas(listaVal, matriculas);

				if (validarAsociar){
					// SE VALIDA QUE LA MATRICULA NO ESTE BLOQUEADA
					FolioPk folioid = new FolioPk();
					folioid.idMatricula = folio.getIdMatricula();
					
					Usuario usuarioBloqueo = forseti.getBloqueoFolio(folioid);
					
					if (usuarioBloqueo != null){
						errores.add("La matricula que intenta ingresar es esta bloqueada por otro turno.");
						throw new MatriculaInvalidaConfrontacionException(errores);
					}
				}
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
                               
				SolicitudFolioPk sfId = hermod.addFolioToTurno(folio.getIdMatricula(), id, userSir);
				//AÑADIRLO AL OBJETO TURNO
				SolicitudFolio sfolio = new SolicitudFolio();
				sfolio.setFolio(folio);
				sfolio.setSolicitud(turno.getSolicitud());
				turno.getSolicitud().addSolicitudFolio(sfolio);
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
			} else {
				
				errores.add("La matricula que intenta ingresar es inválida.");
				throw new MatriculaInvalidaConfrontacionException(errores);
			}
                        
                        if(evento.getFase().getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
                            hermod.addCtrlMatricula(folio.getIdMatricula(), "1", "CORRECTIVO", turno.getIdWorkflow());
                        } else{
                            hermod.addCtrlMatricula(folio.getIdMatricula(), "1", "CONFRONTADOR", turno.getIdWorkflow());
                        }

		} catch (MatriculaInvalidaConfrontacionException ev) {
			throw ev;
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula." + ep.toString());
			String msm = ep.toString();
			if(!e.getHashErrores().isEmpty()){
				Enumeration llaves = (Enumeration) e.getHashErrores().keys();
				while (llaves.hasMoreElements()) {
					String llave = (String) llaves.nextElement();
					msm = llave+": ";
					errores = (List) e.getHashErrores().get(llave);
					for (Iterator itr2 = errores.iterator(); itr2.hasNext();) {
						msm = msm + (String) itr2.next()+". ";
					}
				}
			}
			throw new ConfrontacionNoEfectuadaException(msm);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo asociar la matrícula",e);
		}

		return new EvnRespConfrontacion(turno);

	}
	
	/**
	 * Método que actualiza los datos de antiguo sistema 
	* @param evento
	* @return
	*/
	private EvnRespConfrontacion actualizarDatosAntiguoSistema(EvnConfrontacion evento) throws EventoException {

		Solicitud solicitud = evento.getTurno().getSolicitud();
		DatosAntiguoSistema datosAntiguoSistema = evento.getDatosAntiguoSistema();
		Turno turno = evento.getTurno();		
		try{
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
						
			hermod.setDatosAntiguoSistemaToSolicitud(solicitud, datosAntiguoSistema);
			turno = hermod.getTurno(tid);	
		}catch(Throwable t){
			throw new ConfrontacionNoEfectuadaException("Se presento el siguiente error al actualizar los datos de antiguo sistema",t);
		}
		
		return new EvnRespConfrontacion(turno);
	}
	
}
