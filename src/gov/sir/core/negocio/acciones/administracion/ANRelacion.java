package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionImprimibleSIR;
import gov.sir.core.eventos.administracion.EvnRelacion;
import gov.sir.core.eventos.administracion.EvnRespRelacion;
import gov.sir.core.eventos.comun.EvnRespTurno;
import gov.sir.core.eventos.comun.EvnTurno;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.ListarNoEfectuadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.acciones.registro.ANCalificacion;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CConfrontacion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import java.util.Date;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CQueries;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFolio;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoRelacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleInscripcionTestamento;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.IDidTurnoComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.awt.image.BufferedImage;
import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacionA;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacionB;
import gov.sir.core.util.TLSHttpClientComponent;
import gov.sir.fenrir.FenrirException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.util.ExceptionPrinter;

/**
 * @author mnewball
 */
public class ANRelacion extends SoporteAccionNegocio {
        
        private boolean isREL = false;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

	/** Instancia de la intefaz de Fenrir  */
	private ForsetiServiceInterface forseti;
	
	 /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;

	private static ProcesadorEventosNegocioProxy proxy = null;

	/**
	 * Constructor encargado de inicializar los servicios a ser utilizados por la
	 * acción de Negocio
	 * @throws EventoException
	 */
	public ANRelacion() throws EventoException {
		super();
		ServiceLocator service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
		}
		
		try {
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

			if (printJobs == null) {
				throw new ServicioNoEncontradoException("Servicio printJobs no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio printJobs");
		}
		
		
	}
	 public ProcesadorEventosNegocioProxy getProcesadorEventosNegocioProxy() {
	    	if( null == proxy ) {
	    		proxy =  new ProcesadorEventosNegocioProxy();
	    	}
	    	return proxy; 
	 }
	    
	    

	public EventoRespuesta perform(Evento ev) throws EventoException {

		EvnRelacion evento = (EvnRelacion) ev;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}
		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnRelacion.CARGAR_DATOS)) {
			return cargarDatos(evento);
		} else if (tipoEvento.equals(EvnRelacion.SELECCIONAR_FASE)) {
			return seleccionarFase(evento);
		} else if (tipoEvento.equals(EvnRelacion.SELECCIONAR_PROCESO)) {
			return seleccionarProceso(evento);
		} else if (tipoEvento.equals(EvnRelacion.SELECCIONAR_RELACION)) {
			return seleccionarRelacion(evento);
		} else if (tipoEvento.equals(EvnRelacion.CREAR_RELACION)) {
			return crearRelacion(evento);
		} else if (tipoEvento.equals(EvnRelacion.IMPRIMIR)) {
			return imprimir(evento);
		} else if (tipoEvento.equals(EvnRelacion.INGRESAR_RELACION)) {
			return ingresarRelacion(evento);
		}else if (tipoEvento.equals(EvnRelacion.VER_DETALLE_RELACION)) {
			return consultarRelacion(evento);
		}

		return null;
	}
	/**
	 * Este método es llamado cuando se quiere obtener lista de turnos que pertenecen a una relación.
	 * @param request HttpServletRequest
	 * @return EvnTurno 
	 */
	private EvnRespRelacion consultarRelacion (EvnRelacion evento) throws EventoException{

		Hashtable datosTurnosCertificadosAsociados = null;		

		long idProceso = evento.getIdProceso();
		String idFase = evento.getIdFase();
		String idRelacion = evento.getIdRelacion();
		Map certificadosValidos=new Hashtable();

		Log.getInstance().debug(ANRelacion.class,"ENTRO CONSULTA RELACION an");
		EvnRespTurno evnRespuestaTurno = null;
		try {
			Proceso proceso = new Proceso();
			proceso.setIdProceso(idProceso);
			Fase fase = new Fase();
			fase.setID(idFase);
			
			EvnTurno evnTurno = new EvnTurno(EvnTurno.CONSULTAR_RELACION);

			evnTurno.setProceso(proceso);
			evnTurno.setFase(fase);
			evnTurno.setIdRelacion(idRelacion);
			evnTurno.setCirculo(evento.getCirculo());
			
			evnRespuestaTurno = (EvnRespTurno)( getProcesadorEventosNegocioProxy().manejarEvento( evnTurno ) );
			
		}catch(ValidacionParametrosException vpe){
			throw vpe;
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException("Error al consultar los turnos para la relación : " + idRelacion + th.getMessage());
			exception.addError("Error al consultar los turnos para la relación : " + idRelacion + th.getMessage());
			throw exception;
		}

		datosTurnosCertificadosAsociados = (Hashtable)evnRespuestaTurno.getPayload();
		certificadosValidos = evnRespuestaTurno.getTurnosCertificadosValidos();
		EvnRespRelacion eventoResp =new EvnRespRelacion(datosTurnosCertificadosAsociados, EvnRespTurno.CONSULTAR_RELACION);
		eventoResp.setTurnosCertificadosValidos(certificadosValidos);
		eventoResp.setRelacion(evnRespuestaTurno.getRelacion());
		
		return eventoResp;
		
	}
	
	
	private EventoRespuesta ingresarRelacion(EvnRelacion evento) throws EventoException {

		TipoRelacion tipoRelacion;
		boolean mostrarCodigo = false, mostrarVencimiento = false, mostrarNumeroDocumento = false;
		boolean mostrarDetalle = false;
		List turnos = new ArrayList();

		long idProceso = evento.getIdProceso();
		String idFase = evento.getIdFase();
		String idRelacion = evento.getIdRelacion();
		idRelacion = idRelacion.toUpperCase();
		Relacion respuestaRelacion;
		String respRelacionMesa = "";
		boolean mostrarLabelRelacion = false;
                boolean verimp = false;
                boolean verimp1 = false;
		try {
			TipoRelacionPk idTipoRelacion = new TipoRelacionPk();
			idTipoRelacion.idTipoRelacion = evento.getIdTipoRelacion();
			tipoRelacion = hermod.getTipoRelacion(idTipoRelacion);

            // Si la relación corresponde al proceso de registro, se debe mostrar su número
            // de documento
            String rtaRelacionV = "";
            String idProcesoString = "" + idProceso;
			if(idProcesoString.equals(CProceso.PROCESO_REGISTRO)) {
				mostrarNumeroDocumento = true;
			}

			if(tipoRelacion != null) {
				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_PARA_CUSTODIA_MAYOR_VALOR)) {
					mostrarVencimiento = true;
				} else{ 
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)) {
						mostrarCodigo = true;
						//Se valida que lo seleccionado es lo mismo de la relacion
						RelacionPk idRelacionRespuesta = new RelacionPk();
						idRelacionRespuesta.idRelacion = idRelacion;
						idRelacionRespuesta.idFase = CFase.REG_MESA_CONTROL;
						respuestaRelacion = hermod.getRelacion(idRelacionRespuesta);
						if (respuestaRelacion.getRespuesta() != null) {
							rtaRelacionV = respuestaRelacion.getRespuesta();
						}
						if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS)){
							if (!rtaRelacionV.equals(CRespuesta.OK)){
								//No es coherente con la que selecciono
								throw new Exception("La relación Seleccionada No es del mismo tipo del Número de Relación " + idRelacion);
							}
						} else {
							if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)){
								if (!rtaRelacionV.equals(CRespuesta.DEVOLUCION)){
									//No es coherente con la que selecciono
									throw new Exception("La relación Seleccionada No es del mismo tipo del Número de Relación " + idRelacion);
								}
							}else{
								if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)){
									if(!rtaRelacionV.equals(CRespuesta.INSCRIPCION_PARCIAL))
										throw new Exception("La relación seleccionada no es del mismo tipo del Número de relación "+idRelacion);
								}
							}
						}                                          
					} 
					// Se consulta la respuesta realizada en la fase de REG_MESA_CONTROL
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA)){
						RelacionPk idRelacionRespuesta = new RelacionPk();
						idRelacionRespuesta.idRelacion = idRelacion;
						idRelacionRespuesta.idFase = CFase.REG_MESA_CONTROL;
						respuestaRelacion = hermod.getRelacion(idRelacionRespuesta);
						if (respuestaRelacion!=null) {
							String rtaRelacion = "";
							if (respuestaRelacion.getRespuesta() != null) {
								rtaRelacion = respuestaRelacion.getRespuesta();
								mostrarLabelRelacion = true;
							}
							if (rtaRelacion.equals(CRespuesta.OK)){
								respRelacionMesa = "INSCRITOS";
							} else {
								if (rtaRelacion.equals(CRespuesta.DEVOLUCION)){
									respRelacionMesa = "DEVUELTOS";
								} else {
									if (rtaRelacion.equals(CRespuesta.MAYOR_VALOR)){
										respRelacionMesa = "de MAYOR VALOR";
									} 
								}
							}
						}
					}
				}
                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS) ||
                                tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)){
                                    verimp = true;
                               }
                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)){
                                     verimp1 = true;
                                }
			}

			if(mostrarCodigo){
				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA)) {
					mostrarDetalle = true;
				}
			}
			ProcesoPk procesoId = new ProcesoPk();
			procesoId.idProceso = idProceso;

			Proceso proceso = hermod.getProcesoById(procesoId);

			Fase fase = hermod.getFase(idFase);

			Circulo circulo = evento.getCirculo();
                        
			List turnos1 = hermod.getTurnosParaRelacion(proceso, fase, circulo, tipoRelacion, idRelacion);
                        Iterator Turno = turnos1.iterator();
                        int count = 0;
                        while(Turno.hasNext()){         
                              Turno tt = (Turno)Turno.next();
                              SolicitudRegistro solreg = (SolicitudRegistro)tt.getSolicitud();
                              solreg.setClasificacionRegistro(rtaRelacionV);
                              tt.setSolicitud(solreg);
                              turnos.add(count,tt);
                              count++;
                        }
                
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError("Error al obtener la relación: " + th.getMessage());
			throw exception;
		}

		EvnRespRelacion respuesta = new EvnRespRelacion(turnos, EvnRespRelacion.INGRESAR_RELACION_OK);

		respuesta.setMostrarIdRelacion(mostrarCodigo);
		respuesta.setMostrarDetalle(mostrarDetalle);
		respuesta.setMostrarVencimientoMayorValor(mostrarVencimiento);
		respuesta.setMostrarNumeroDocumento(mostrarNumeroDocumento);
		respuesta.setMostrarLabelRelacion(mostrarLabelRelacion);
                respuesta.setMostrarverimp(verimp);
                respuesta.setMostrarverimp2(verimp1);
		respuesta.setTipoRelacionAvanzar(respRelacionMesa);

		return respuesta;
	}

	private EventoRespuesta imprimir(EvnRelacion evento) throws EventoException {

		String idRelacionStr = evento.getIdRelacion();
		String idFase = evento.getIdFase();
		String nota = evento.getNota();
		long idProceso = evento.getIdProceso();

		Relacion relacion = null;

		try {
			RelacionPk idRelacion = new RelacionPk();
			idRelacion.idRelacion = idRelacionStr;
			idRelacion.idFase = idFase;

			hermod.setNotaToRelacion(idRelacion, nota);

			relacion = hermod.getRelacion(idRelacion);
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError("Error al imprimir la relación: " + th.getMessage());
			throw exception;
		}

		EvnRespRelacion respuesta = new EvnRespRelacion(relacion, EvnRespRelacion.IMPRIMIR_OK);
		respuesta.setIdProceso(idProceso);
		respuesta.setIdFase(relacion.getIdFase());
		respuesta.setIdRelacion(relacion.getIdRelacion());
		respuesta.setIdReporte(relacion.getTipoRelacion().getReporte());

		return respuesta;

	}

	private EventoRespuesta crearRelacion(EvnRelacion evento) throws EventoException {
		String[] turnosSeleccionados = evento.getTurnosRelacion();
		TipoRelacion tipoRelacion = new TipoRelacion();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = evento.getUsuario();
		Relacion relacion=null;

		String idRelacion = "";
		Usuario usuario = null;
		Circulo circulo = null;
		List turnos = null;
		String respuestaRelacion = "";
		Hashtable turnoProblemas = new Hashtable();
		Hashtable turnoProblemasAux = new Hashtable();
		Hashtable turnoAvanzadosOk = new Hashtable();

		try {
			TipoRelacionPk idTipoRelacion = new TipoRelacionPk();
			idTipoRelacion.idTipoRelacion = evento.getIdTipoRelacion();
			tipoRelacion = hermod.getTipoRelacion(idTipoRelacion);
			usuario = fenrir.getUsuario(usuarioAuriga);
			circulo = evento.getCirculo();
			idRelacion = evento.getIdRelacion();

			turnos = new Vector();
			if(evento.isAvanceTurnosAutomatico()){
				if(evento.getIdFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
					List turnosTemp = null; 
					Fase fase = new Fase();
					fase.setID(CFase.REG_CERTIFICADOS_ASOCIADOS);
					Proceso proceso = new Proceso();
					proceso.setIdProceso(new Long (evento.getIdProceso()).longValue());
					turnosTemp = hermod.getTurnosByRelacion(proceso, fase, circulo, idRelacion);
					if(turnosTemp!=null){
						Iterator itTemp = turnosTemp.iterator();
						while (itTemp.hasNext()){
							Turno turnoTemp = (Turno)itTemp.next();
							turnos.add(hermod.getTurnobyWF(turnoTemp.getIdWorkflow()));
						}
					}
				}				
			}else{
				for(int i = 0; i < turnosSeleccionados.length; i++) {
					Turno turnoEnv = hermod.getTurnobyWF(turnosSeleccionados[i]);
					SolicitudRegistro solicitud = (SolicitudRegistro)turnoEnv.getSolicitud();
					List folios= solicitud.getSolicitudFolios();
					String strEstaEnCorreccion = metodoGeneralTurnosCorreecion(folios,turnoEnv);
					if(strEstaEnCorreccion != null && !strEstaEnCorreccion.equals("")){
						turnoProblemasAux.put(turnoEnv.getIdWorkflow(),strEstaEnCorreccion);
					}else if (evento.getIdFase()!=null && !turnoEnv.getIdFase().equals(evento.getIdFase()) && 
							evento.getIdFase().equals(CFase.REG_MESA_CONTROL)){
						turnoProblemasAux.put(turnoEnv.getIdWorkflow(),"El turno ya avanzo a la siguiente fase antes " +
									"de crearse esta relacion");
					}else if (evento.getIdFase()!=null && !turnoEnv.getIdFase().equals(evento.getIdFase()) && 
							evento.getIdFase().equals(CFase.REG_FIRMAR)){
						turnoProblemasAux.put(turnoEnv.getIdWorkflow(),"El turno ya avanzo a la siguiente fase antes " +
									"de crearse esta relacion");
					}else{
						turnos.add(turnoEnv);
					}
				}
			}
			
			// Validar prioridad de turnos de firma, si el tipo de relación es de Firma.
			if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)) {

				turnos = reordenarTurnosPorFechaInicio(turnos);
				List idTurnos = new Vector();
				

				for(Iterator iteradorTurnos = turnos.iterator(); iteradorTurnos.hasNext();) {
					Turno turnoTemp = (Turno)iteradorTurnos.next();

					TurnoPk idTurno = new TurnoPk();
					idTurno.anio = turnoTemp.getAnio();
					idTurno.idCirculo = turnoTemp.getIdCirculo();
					idTurno.idProceso = turnoTemp.getIdProceso();
					idTurno.idTurno = turnoTemp.getIdTurno();

					idTurnos.add(idTurno);
					
				}
				
				turnoProblemas = forseti.validarPrincipioPrioridadFirmaRelacion(idTurnos);
				
				Enumeration e = turnoProblemas.keys();
				
				while (e.hasMoreElements()){
					String itemkey = (String)e.nextElement();
					int i = 0;
					for(Iterator iteradorTurnosLi = turnos.iterator(); iteradorTurnosLi.hasNext();) {
						Turno turnoTempLi = (Turno)iteradorTurnosLi.next();
						if (itemkey.equals(turnoTempLi.getIdWorkflow())){
							turnos.remove(i);
							break;
						}
						i++;
					}
				}
			}
		} catch(ForsetiException fe) {
//			ExceptionPrinter ep = new ExceptionPrinter(fe);
			List l;
			l = fe.getErrores();
			if(l.size() > 0){
				throw new ValidacionParametrosException(l);
			}
			if (fe.getHashErrores() != null) {
				//Hashtable ht = fe.getHashErrores();
				ValidacionParametrosHTException vpe = new ValidacionParametrosHTException(fe);
				throw vpe;
			}
			if(fe.getMessage() != null){
				ValidacionParametrosException vpe = new ValidacionParametrosException();
				vpe.addError(fe.getMessage());
				throw vpe;
			}
			//throw new ValidacionParametrosHTException(fe);
			
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError(th.getMessage() + (th.getCause() != null ? (" - " + th.getCause().getMessage()) : ""));
			throw exception;
		}

		try {
           		
			// Por último, si aplica, se avanzan los turnos
			if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_INSCRITOS_PARA_FIRMA)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DEVUELTOS_PARA_FIRMA)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_PARA_CUSTODIA_MAYOR_VALOR)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_DEVUELTO)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_DE_ENTREGA_DEVUELTO)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_EXPEDIDO)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_REPARTO)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_INSCRITOS)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_DEVUELTOS)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO)
					|| tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_CONFRONTACION)
                                        || tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_DOCUMENTOS_DEVUELTOS_A_NOTIFICAR)
                                        /**
                                         * @author      :   Carlos Torres
                                         * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                                         */ 
                                        || tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_REGISTRO)
                                        || tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_CORRESPONDIENCIA)) {
				Hashtable tabla = new Hashtable();
				tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

				Fase fase = new Fase();
				Turno turno = null;
				int tipoAvance;
				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {
					try {
						boolean isFirmarOk = true;
						
						turno = (Turno)itTurnos.next();
						fase.setID(turno.getIdFase());
						
						if (turnoProblemas.size() > 0) {
							if (fase.getID().equals(CFase.REG_FIRMAR) ) {
								try {
									List idTurnoVerificar = new Vector();
									
									TurnoPk idTurnov = new TurnoPk();
									idTurnov.anio = turno.getAnio();
									idTurnov.idCirculo = turno.getIdCirculo();
									idTurnov.idProceso = turno.getIdProceso();
									idTurnov.idTurno = turno.getIdTurno();
									idTurnoVerificar.add(idTurnov);
                                                                        if(!hermod.isTurnoDevuelto(turno.getIdWorkflow())){
									forseti.validarPrincipioPrioridadFirma(idTurnoVerificar);
                                                                        }
								} catch (Exception ee) {
									String mensajeError = " Existen turnos activos anteriores que comparten folios y no han sido firmados.";
									turnoProblemas.put(turno.getIdWorkflow(),mensajeError);
									isFirmarOk = false;
								}
							}
						}
						
						if (isFirmarOk) {
							
							if(fase.getID().equals(CFase.REG_MESA_CONTROL)) {
								tabla.put(Processor.RESULT, CRespuesta.CONFIRMAR);
							} else if(fase.getID().equals(CFase.REG_FIRMAR)){
                                                                if(hermod.isTurnoDevuelto(turno.getIdWorkflow())){
                                                                    tabla.put(Processor.RESULT, CRespuesta.NOTA_DEVOLUTIVA);
                                                                } else{
                                                                    tabla.put(Processor.RESULT, CRespuesta.CONFIRMAR);
                                                                }
                                                        }else if(fase.getID().equals(CFase.ANT_REVISION)) {
								tabla.put(Processor.RESULT, CRespuesta.CREADO);
								
								if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_EXPEDIDO)){
									//	SI ES DE CERTIFICADO, SE DEBE IMPRIMIR EL CERTIFICADO O LA NOTA DEVOLUTIVA.
									if (turno.getSolicitud() instanceof SolicitudCertificado){
										try {
											SolicitudCertificado solCert =(SolicitudCertificado)turno.getSolicitud();
											LiquidacionTurnoCertificado liq = (LiquidacionTurnoCertificado)solCert.getLiquidaciones().get(0);
								    		
											if(!(liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))){
												imprimirCertificado(turno,usuario,turno.getIdCirculo());
											}
										} catch (Throwable e) {
											throw new EventoException(e.getMessage());
										}			
									}
								}
							} else if(fase.getID().equals(CFase.REG_CONFRONTAR)){
								String idSubtipoSolicitud = ((SolicitudRegistro)turno.getSolicitud()).getSubtipoSolicitud().getIdSubtipoSol();
								if(idSubtipoSolicitud.equals(CSubtipoSolicitud.ANTIGUO_SISTEMA)){
									tabla.put(Processor.RESULT, CConfrontacion.ANTIGUO_SISTEMA);
								}else if(idSubtipoSolicitud.equals(CSubtipoSolicitud.TESTAMENTO)){
									tabla.put(Processor.RESULT, CConfrontacion.TESTAMENTO);
								}else if(idSubtipoSolicitud.equals(CSubtipoSolicitud.NORMAL)){
									tabla.put(Processor.RESULT, CConfrontacion.NO_ANTIGUO_SISTEMA);
								}
                                                         /**
                                                           * @author      :   Carlos Torres
                                                           * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                                                          **/
							}else if(fase.getID().equals(CFase.REG_ENTREGA)){
                                                            tabla.put(Processor.RESULT, CRespuesta.CONFIRMAR);
                                                        }else if(fase.getID().equals(CFase.REG_ENTREGA_EXTERNO)){
                                                            tabla.put(Processor.RESULT, CRespuesta.CONFIRMAR);
                                                        }else {
								if (fase.getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)) {
									// Se realizara la logica mas adelante
								} else {
									if (fase.getID().equals(CFase.CER_ENTREGAR)) {
										
									}else {
										continue;	
									}	
								}
								
							}
		
							if(fase.getID().equals(CFase.REG_MESA_CONTROL)) {
								tipoAvance = CAvanzarTurno.AVANZAR_CUALQUIERA;
							} else if(fase.getID().equals(CFase.ANT_REVISION)) {
								tipoAvance = CAvanzarTurno.AVANZAR_POP;
							} else {
								tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;
							}
		
							if(fase.getID().equals(CFase.REG_FIRMAR)) {
								delegarBloqueoFolios(turno, usuario);
								hacerFoliosDefinitivos(turno, usuario);
							}
							
							if(fase.getID().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
								
								Turno turnoTempCert = null;
								//SI LA RELACIÓN ES LA DE CERTIFICADOS ASOCIADOS
								//SE AVANZAN LOS TURNOS DE CERTIFICADOS ASOCIADOS							    
							    try {
							    	String idFaseCertificados=CFase.CER_ESPERA_IMPRIMIR;
							    	SolicitudRegistro solReg = (SolicitudRegistro) turno.getSolicitud();							    	
							    	List solCerts = solReg.getSolicitudesHijas();
							    	
							    	if (solCerts!=null){
									
							    		Iterator isol= solCerts.iterator();
									    for(;isol.hasNext();){
									        //logica para imprimir avanzar su turno respectivo
									        //obtener solicitud Certificado
									    	SolicitudAsociada solAsociada = (SolicitudAsociada) isol.next();
									        SolicitudCertificado solCerti = (SolicitudCertificado)solAsociada.getSolicitudHija();
									        
									        SolicitudPk sid = new SolicitudPk();
									        sid.idSolicitud = solCerti.getIdSolicitud();
							
									        //obtener turno , circulo y fase
									        turnoTempCert = hermod.getTurnoBySolicitud(sid);
									        String idFase= turnoTempCert.getIdFase();
									        String idCirculo = turnoTempCert.getIdCirculo();
							
									        //validar que el turno no haya sido ya impreso
									        if(idFase.equals(idFaseCertificados)){
									        	String idWorkflowCertificado = turnoTempCert.getIdWorkflow();
									        	turnoTempCert = hermod.getTurnobyWF(idWorkflowCertificado);

									        	Fase faseAvanceCertificado = new Fase();
												faseAvanceCertificado.setID(turnoTempCert.getIdFase());
												Hashtable parametrosAvanceCertificado = new Hashtable();
												parametrosAvanceCertificado.put(Processor.RESULT,CRespuesta.CONFIRMAR);
													             
												try {
													//hermod.avanzarTurnoNuevoNormal(turnoTempCert,faseAvanceCertificado,parametrosAvanceCertificado,evento.getUsuarioSIR());
													hermod.actualizarTurnoCertificadoAsociado(turnoTempCert,parametrosAvanceCertificado,evento.getUsuarioSIR());
												} catch (Throwable exception) {
													turnoProblemas.put(turno.getIdWorkflow(), "Error avanzando turnos asociados al turno." + " (Turno " + turnoTempCert.getIdWorkflow() +") "+ exception.getCause().getMessage());
												}
									        }
									    }
							    	}
							    } catch (HermodException e) {
									turnoProblemas.put(turno.getIdWorkflow(), "Error avanzando turnos asociados al turno. " +  e.getCause().getMessage());
								} catch (Throwable e) {
									turnoProblemas.put(turno.getIdWorkflow(), "Error avanzando turnos asociados al turno. " +  e.getCause().getMessage());
								}
								
								
								//Determinar si el turno de registro tiene asociado un acto cuyo tipo de acto es
								//EMBARGO, caso en el cual la respuesta es por correspondencia.
								//En los demás casos la respuesta es PERSONALMENTE.
								SolicitudRegistro solReg = (SolicitudRegistro) turno.getSolicitud();
								List listaLiquidaciones = solReg.getLiquidaciones();   
								String respuestaCer = CRespuesta.PERSONAL;
								
								
								if (listaLiquidaciones == null || listaLiquidaciones.size() == 0) {
									respuestaCer = CRespuesta.PERSONAL;
								} else {
									for (int i=0; i<listaLiquidaciones.size(); i++) {
										LiquidacionTurnoRegistro liqRegistro = (LiquidacionTurnoRegistro) listaLiquidaciones.get(i);
										List listaActos = liqRegistro.getActos();
										if (listaActos !=null && listaActos.size()>0) {
											for (int j=0; j<listaActos.size(); j++) {
												Acto acto = (Acto) listaActos.get(j);
												if (acto.getTipoActo()!=null) {
													if (acto.getTipoActo().getIdTipoActo().equals(CActo.EMBARGO)) {
														respuestaCer = CRespuesta.CORRESPONDENCIA;
														j= listaActos.size()+1;
														i= listaLiquidaciones.size()+1;
													}
												}
											}
											
										}
										
									}
								}
								tabla.put(Processor.RESULT, respuestaCer);
								
							}
							
							if (turno.getSolicitud() instanceof SolicitudRegistro && fase.getID().equals(CFase.REG_MESA_CONTROL)){
								String AJUSTAR_MESA_CONTROL = "AJUSTAR_MESA_CONTROL";
								String MAYOR_VALOR = "MAYOR_VALOR";
								String CONFIRMAR = "CONFIRMAR";
								String FASE_CALIFICACION ="CAL_CALIFICACION";
								
								//TODO SACAR LA RESPUESTA
								//hermod.updateClasificacionSolicitudRegistro(resp,turno);
		
								/*******************************************************/
						        /*      Obtener la respuesta adecuada, de acuerdo con  */
						        /*      la lógica anterior de decisores                */
						        /*******************************************************/
						        
						        //1. Decidir Si es de MAYOR VALOR.
						    
						    
						        String itemKey = turno.getIdWorkflow();
							    String respuesta ="";
							
							    //Obtener información del turno a través de los servicios.
							   try {
							
							      Turno turnoMayorValor =  hermod.getTurnobyWF(itemKey);
							
							      //Si no se recibió un turno se genera una excepción.
							      if (turno == null) {
								      throw new TurnoNoAvanzadoException("El turno recibido es nulo.  No puede generarse decisión.");
							      }
							   
							      //Ubicar si existe un turno historia, asociado con el turno que haya pasado por la
							      //fase: FASE_BUSCADA y con la respuesta RESPUESTA_ESPERADA
							      List listaHistorials = turno.getHistorials();
							   
							      //Si no se recibió una lista de historiales se genera una excepción.
								  if (listaHistorials == null) {
									  throw new EventoException("El historial del turno es vacío.  No puede generarse una decisión.");
								  }
								
								  int size = listaHistorials.size();
								
								  //Obtener el último turno historia que pasó por la fase de calificación. 
								  //Esto funciona porque se garantiza que el listado de turno historia está organizado por fecha
								  TurnoHistoria ultimoTurnoHistoria=null;
								  for (int i= 0; i<size; i++){
									   TurnoHistoria turnoHistoria = (TurnoHistoria) listaHistorials.get(i);
									   if (turnoHistoria.getFase().equals(FASE_CALIFICACION)) {
										   ultimoTurnoHistoria = turnoHistoria;
										   respuestaRelacion = ultimoTurnoHistoria.getRespuesta();
									   }
								  }
								
								  //No se ha pasado por la fase.
								   if (ultimoTurnoHistoria == null) {
									   respuesta = CONFIRMAR;
								   } else if (ultimoTurnoHistoria.getRespuesta().equals(MAYOR_VALOR) ) {
									    respuesta = MAYOR_VALOR;
								   } else {
									   respuesta = CONFIRMAR;
								   }
								   
								   //2. DECIDIR SI EXISTE SOLICITUD AJUSTE. ESTA RESPUESTA DEBE PRIMAR SOBRE LA DECISION
								   //DE MAYOR VALOR. (MIRAR FLAG QUE MARCA TURNOS AJUSTE).
								   	SolicitudRegistro solReg = (SolicitudRegistro) turno.getSolicitud();
								   	
									if (solReg == null){
										throw new EventoException("El turno no tiene una solicitud asociada.");
									}
								
									//Si la solicitud requiere ajuste, se genera la respuesta de AJUSTAR_MESA_CONTROL
									if (solReg.getAjuste()) {
										respuesta = AJUSTAR_MESA_CONTROL;
									}
									  
									  
									/*****************************************/
									/*        ELIMINAR SASS                  */
									/*****************************************/
									Hashtable parametrosAvance = new Hashtable();
									parametrosAvance.put(Processor.RESULT,respuesta);
										             
									try {
										hermod.avanzarTurnoNuevoCualquiera(turno,fase,parametrosAvance,evento.getUsuarioSIR());
										
									} catch (Throwable exception) {
										throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
									}
									    
							   	} catch (Throwable exception) {
								   throw new EventoException ("Error decidiendo navegación del turno. No se pudo obtener el historial adecuadamente.",exception);
							    }
							} else if (turno.getSolicitud() instanceof SolicitudCertificado || turno.getSolicitud() instanceof SolicitudCorreccion || turno.getSolicitud() instanceof SolicitudRegistro){
								if (tipoAvance == CAvanzarTurno.AVANZAR_NORMAL){
									hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioSIR());
                                                                    if(fase.getID().equals(CFase.REG_FIRMAR)){
                                                                            try {
                                                                            int saber = 4;
                                                                            try{
                                                                            Testamento testamento = hermod.getTestamentoByID(turno.getSolicitud().getIdSolicitud());
                                                                            if(testamento != null){
                                                                                this.imprimirTestamento(evento,testamento,turno);
                                                                                saber = 0;
                                                                            }    
                                                                            }catch (Throwable ex) {

                                                                            }  

                                                                            if(saber != 0){
                                                                                if(turno.getNotas() != null){
                                                                                if(turno.getNotas().size() > 0){
                                                                                    List notas = turno.getNotas();
                                                                                    Nota captura = null;
                                                                                    for(int i = 0 ; i < notas.size() ; i++){
                                                                                        Nota n = (Nota) notas.get(i);
                                                                                        if(n.getTiponota().isDevolutiva()){
                                                                                            captura = n;
                                                                                            saber=1;
                                                                                        }
                                                                                    }
                                                                                     if(saber == 1){
                                                                                        this.imprimirNotaDevolutiva(evento,captura,turno,null);
                                                                                        try{
                                                                                            SolicitudRegistro solReg1 = (SolicitudRegistro) turno.getSolicitud();
                                                                                            if(tipoRelacion.getNombre().equals("RELACION DE DOCUMENTOS A DESANOTACION INSCRITOS PARCIALMENTE") || "INSCRITO PARCIALMENTE".equals(solReg1.getClasificacionRegistro()) || "REGISTRO PARCIAL".equals(solReg1.getClasificacionRegistro())){
                                                                                                    this.imprimirFormularioCalificacionReg(evento,turno);
                                                                                            }
                                                                                        }catch(Throwable ex){

                                                                                        }
                                                                                     }
                                                                                }
                                                                            }      
                                                                                if(saber == 4 ){
                                                                                 this.imprimirFormularioCalificacionReg(evento,turno);
                                                                                }
                                                                            }
                                                                        } catch (Throwable ex) {
                                                                                    throw new EventoException ("Error imprimiento el Documento.");
                                                                        }  
                                                                    }
                                                               }else if (tipoAvance == CAvanzarTurno.AVANZAR_POP){
									hermod.avanzarTurnoNuevoPop(turno,fase,tabla,evento.getUsuarioSIR());
                                                                        if(fase.getID().equals(CFase.REG_FIRMAR)){
                                                                            try {
                                                                            int saber = 4;
                                                                            try{
                                                                            Testamento testamento = hermod.getTestamentoByID(turno.getSolicitud().getIdSolicitud());
                                                                            if(testamento != null){
                                                                                this.imprimirTestamento(evento,testamento,turno);
                                                                                saber = 0;
                                                                            }    
                                                                            }catch (Throwable ex) {

                                                                            }  

                                                                            if(saber != 0){
                                                                                if(turno.getNotas() != null){
                                                                                if(turno.getNotas().size() > 0){
                                                                                    List notas = turno.getNotas();
                                                                                    Nota captura = null;
                                                                                    for(int i = 0 ; i < notas.size() ; i++){
                                                                                        Nota n = (Nota) notas.get(i);
                                                                                        if(n.getTiponota().isDevolutiva()){
                                                                                            captura = n;
                                                                                            saber=1;
                                                                                        }
                                                                                    }
                                                                                     if(saber == 1){
                                                                                        this.imprimirNotaDevolutiva(evento,captura,turno,null);
                                                                                         try{
                                                                                            SolicitudRegistro solReg1 = (SolicitudRegistro) turno.getSolicitud();
                                                                                            if(tipoRelacion.getNombre().equals("RELACION DE DOCUMENTOS A DESANOTACION INSCRITOS PARCIALMENTE") || "INSCRITO PARCIALMENTE".equals(solReg1.getClasificacionRegistro()) || "REGISTRO PARCIAL".equals(solReg1.getClasificacionRegistro())){
                                                                                                    this.imprimirFormularioCalificacionReg(evento,turno);
                                                                                            }
                                                                                        }catch(Throwable ex){

                                                                                        }
                                                                                     }
                                                                                }
                                                                            }      
                                                                                if(saber == 4 ){
                                                                                 this.imprimirFormularioCalificacionReg(evento,turno);
                                                                                }
                                                                            }
                                                                        } catch (Throwable ex) {
                                                                                    throw new EventoException ("Error imprimiento el Documento.");
                                                                        }  
                                                                    }
                                                             }else if (tipoAvance == CAvanzarTurno.AVANZAR_CUALQUIERA){
									hermod.avanzarTurnoNuevoCualquiera(turno,fase,tabla,evento.getUsuarioSIR());
                                                                        if(fase.getID().equals(CFase.REG_FIRMAR)){
                                                                            try {
                                                                            int saber = 4;
                                                                            try{
                                                                            Testamento testamento = hermod.getTestamentoByID(turno.getSolicitud().getIdSolicitud());
                                                                            if(testamento != null){
                                                                                this.imprimirTestamento(evento,testamento,turno);
                                                                                saber = 0;
                                                                            }    
                                                                            }catch (Throwable ex) {

                                                                            }  

                                                                            if(saber != 0){
                                                                                if(turno.getNotas() != null){
                                                                                if(turno.getNotas().size() > 0){
                                                                                    List notas = turno.getNotas();
                                                                                    Nota captura = null;
                                                                                    for(int i = 0 ; i < notas.size() ; i++){
                                                                                        Nota n = (Nota) notas.get(i);
                                                                                        if(n.getTiponota().isDevolutiva()){
                                                                                            captura = n;
                                                                                            saber=1;
                                                                                        }
                                                                                    }
                                                                                     if(saber == 1){
                                                                                        this.imprimirNotaDevolutiva(evento,captura,turno,null);
                                                                                        try{
                                                                                            SolicitudRegistro solReg1 = (SolicitudRegistro) turno.getSolicitud();
                                                                                            if(tipoRelacion.getNombre().equals("RELACION DE DOCUMENTOS A DESANOTACION INSCRITOS PARCIALMENTE") || "INSCRITO PARCIALMENTE".equals(solReg1.getClasificacionRegistro()) || "REGISTRO PARCIAL".equals(solReg1.getClasificacionRegistro())){
                                                                                                    this.imprimirFormularioCalificacionReg(evento,turno);
                                                                                            }
                                                                                        }catch(Throwable ex){

                                                                                        }
                                                                                     }
                                                                                }
                                                                            }      
                                                                                if(saber == 4 ){
                                                                                 this.imprimirFormularioCalificacionReg(evento,turno);
                                                                                }
                                                                            }
                                                                        } catch (Throwable ex) {
                                                                                    throw new EventoException ("Error imprimiento el Documento.");
                                                                        }  
                                                                    }	
                                                             }
							}
							
							turnoAvanzadosOk.put(turno.getIdWorkflow(),CRespuesta.OK);
						}
					} catch (Exception ee) {
						Log.getInstance().error(ANRelacion.class,"No pudo avanzar el turno");
						turnoProblemas.put(turno.getIdWorkflow(), "Error Avanzando Turno, " + ee.getCause().getMessage());
					}
                                        
                                        
                                     if(fase.getID().equals("REG_FIRMAR")){
                                            try{
                                            Boolean turnoREL = hermod.isTurnoREL(turno.getIdWorkflow());
                                            if(turnoREL){
                                                TLSHttpClientComponent callerREL = new TLSHttpClientComponent();       
                                                String phase = hermod.getStringByQuery(CQueries.getRespFromCalificacion(turno.getIdWorkflow()));
                                                callerREL.setFase(CRespuesta.getRespuestaREL(phase));
                                                callerREL.sendMsgToREL(turno.getIdWorkflow());
                                                
                                            }
                                        } catch(Exception e){
                                            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, e);
                                             throw new EventoException("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
                                        } catch(Throwable th){
                                             throw new EventoException("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
                                        }      
                                     }  }
			}
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError(th.getMessage() + (th.getCause() != null ? (" - " + th.getCause().getMessage()) : ""));
			throw exception;
		}
		
		// Relaciones que se crean pero no avanzan el turno
		if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA) ||
		tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO) ||
		tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_CONFRONTACION)||
		tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_PARA_ANTIGUO_SISTEMA) ||
		tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_SOLICITUDES_DE_CORRECCIONES) ||
		tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CORRECCIONES_DEVUELTAS_AL_PUBLICO) ||
		tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CORRECCIONES_DESANOTADAS)) {
			for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {
				Turno turno = (Turno)itTurnos.next();
				turnoAvanzadosOk.put(turno.getIdWorkflow(),CRespuesta.OK);
			}
		}
		
		turnoProblemas.putAll(turnoProblemasAux);
		Enumeration ee = turnoProblemas.keys();
		
		while (ee.hasMoreElements()){
			String itemkey = (String)ee.nextElement();
			int i = 0;
			for(Iterator iteradorTurnosLi = turnos.iterator(); iteradorTurnosLi.hasNext();) {
				Turno turnoTempLi = (Turno)iteradorTurnosLi.next();
				if (itemkey.equals(turnoTempLi.getIdWorkflow())){
					turnos.remove(i);
					break;
				}
				i++;
			}
		}
		
		boolean isImprimir = false;
		
		if (turnoAvanzadosOk.size() > 0){
			isImprimir = true;
			//		 En vez de añadir cada turno individualmente en varias operaciones atómicas,
	        // todo se realiza en una sola operación atómica. De esta manera o se agregan
	        // todos los turnos a la relación, o no se crea la relación.
			RelacionPk idrelacionAnterior = new RelacionPk();
			idrelacionAnterior.idRelacion = idRelacion;
			idrelacionAnterior.idFase = tipoRelacion.getIdFase();
			
			try {
				try {
					Relacion rel = hermod.getRelacion(idrelacionAnterior);
					if (rel != null) {
						idRelacion = null;
					}
				} catch (Exception e) {
				}
				
				if(idRelacion == null)
					relacion = hermod.crearRelacionNuevo(tipoRelacion, usuario, circulo, turnos, respuestaRelacion);
				else
					relacion = hermod.crearRelacionNuevo(tipoRelacion, usuario, circulo, turnos, idRelacion, respuestaRelacion);
		
			
			 } catch(Throwable th) {
				th.printStackTrace();
				ValidacionParametrosException exception = new ValidacionParametrosException();
				exception.addError(th.getMessage() + (th.getCause() != null ? (" - " + th.getCause().getMessage()) : ""));
				throw exception;
			}
		} else {
			relacion = new Relacion ();
			relacion.setIdRelacion(idRelacion  + ": No Avanzo Ningun turno de la Relación");
		}
		
		//CASO ESPECIAL DE AVANCE AUTOMATICO, CONSULTA LOS TURNOS ASOCIADOS AL PROCESO FASE Y ESTACIÓN ACTUALES
		//LUEGO DEL AVANCE DE LA RELACIÓN.
        List turnosMenu = null;

        if(evento.isAvanceTurnosAutomatico()){
            try {
				Fase fase = new Fase();
				fase.setID(CFase.REG_CERTIFICADOS_ASOCIADOS);
				Proceso proceso = new Proceso();
				proceso.setIdProceso(new Long (evento.getIdProceso()).longValue());
				turnosMenu = hermod.getTurnos(evento.getEstacion(), evento.getRol(), usuario, proceso, fase, evento.getCirculo());
            } catch (HermodException e) {
                throw new ListarNoEfectuadoException(e.getMessage(),e);
            } catch (Throwable e) {
                throw new EventoException(e.getMessage(),e);
            }
        }

		EvnRespRelacion evResp = new EvnRespRelacion(relacion, EvnRespRelacion.CREAR_RELACION_OK);
		if(turnosMenu!=null){
			evResp.setTurnosMenu(turnosMenu);
		}
		evResp.setRespException(turnoProblemas);
		evResp.setRespTurnosOk(turnoAvanzadosOk);
		evResp.setMostrarBotonImprimir(isImprimir);
			
		return evResp;
	}
   private Pageable imprimirNotaDevolutiva(EvnRelacion evento, Nota nota,Turno turno, TurnoHistoria historiaaaa) throws EventoException {
		Hashtable tabla = new Hashtable();
                int rta = -1;
		Pageable peg = null;
		Vector notas1 = new Vector(turno.getNotas());
		if(turno==null){
			try{
				TurnoPk tid = new TurnoPk();
				tid.anio = nota.getAnio();
				tid.idCirculo = nota.getIdCirculo();
				tid.idProceso = nota.getIdProceso();
				tid.idTurno = nota.getIdTurno();
				
				turno = hermod.getTurno(tid);
			} catch (Throwable e) {
			
                        }
			
			
		}
		if(turno != null){
			for(int i=0; i<notas1.size();i++){
				((Nota)notas1.get(i)).setTurno(turno);
			}
		}
                List notasss = new ArrayList();
                for(int e=0; e<notas1.size();e++){
			Nota n =(Nota)notas1.get(e);
                        if(n.getTiponota().isDevolutiva()){
                            notasss.add(n);
                            n.setTurno(turno);
                        }
                }
                Vector notas = new Vector(notasss);
                Circulo circulo = null;
                CirculoPk tid = new CirculoPk();
		tid.idCirculo = turno.getIdCirculo();
                    try {
                        circulo = forseti.getCirculo(tid);
                    } catch (Throwable ex) {
                        
                    }
		String matricula = null;
		String idWorkflow = null;
		Usuario usuarioSIR = null;
                int i = 0;
                String impresion1 = "";
                List historia = turno.getHistorials();
                while (i < historia.size()) {
                    TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                    if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                        if (i != historia.size() - 1) {
                            usuarioSIR = historial.getUsuarioAtiende();
                            impresion1 =  historial.getNcopias();
                        }

                    }
                    i++;
                }
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		int maxIntentos;
		int espera;


		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 

		//INGRESO DE INTENTOS DE IMPRESION
		try{
		
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
			if (intentosImpresion != null){
				 tabla.put(INTENTOS,intentosImpresion);
			}else{
				tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}
			
			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null){
				tabla.put(ESPERA,esperaImpresion);
			}
			else{
				tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
		}catch (Throwable t){			
			tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);			
		}

		if (turno != null) {
			idWorkflow = turno.getIdWorkflow();
			List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
			if (solicitudesFolio != null && !solicitudesFolio.isEmpty()) {
				SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(0);
				matricula = solicitudFolio.getIdMatricula();
			}

		}
               SolicitudRegistro  solRegistro = null;
                try {
                    solRegistro = (SolicitudRegistro) hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());
                } catch (Throwable ex) {
                solRegistro = (SolicitudRegistro)turno.getSolicitud();
		
                }
		String certAsociados = this.obtenerTextoCertificadosAsociados(turno);
		String fechaImpresion = this.getFechaImpresion();
                
                ValidacionImprimibleSIR iR = new ValidacionImprimibleSIR();
                String clase = null;
                try {
                    clase = iR.buscarVersionImprimible("NOTA_DEVOLUTIVA",nota.getTime());
                    isREL = hermod.isTurnoREL(idWorkflow);
                } catch (Throwable ex) {
                
                }
                
                if("ImprimibleNotaDevolutivaCalificacionB".equals(clase)){
                    peg = imprimibleNotaDevolutivaCalificacionB(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla,impresion1);
                } else if("ImprimibleNotaDevolutivaCalificacionA".equals(clase)){
                    peg = imprimibleNotaDevolutivaCalificacionA(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla,impresion1);
                } else if("ImprimibleNotaDevolutivaCalificacion".equals(clase)){
                    peg = imprimibleNotaDevolutivaCalificacion(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla,impresion1);
                }
                
                return peg;
	}
        private Pageable imprimibleNotaDevolutivaCalificacionB(EvnRelacion evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,String impresion1) throws EventoException{
                    Pageable peg = null;
                    ImprimibleNotaDevolutivaCalificacionB impNota = new ImprimibleNotaDevolutivaCalificacionB(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setChangetextforregistrador(true);
		      ArrayList matriculasNoInscritas = new ArrayList();
                    List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			impNota.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			impNota.setMatriculasNoInscritas(matriculasNoInscritas);            
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
                    //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
                    
                    String nombre = "";
                    String archivo = "";
                    String cargoToPrint = "";
                    String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                    FirmaRegistrador firmaRegistrador = null;
                    CirculoPk cid = new CirculoPk();
                    cid.idCirculo = turno.getIdCirculo();
                    String rutaFisica = null;
                    String sNombre = "";
                    try {
                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }

                        rutaFisica = hermod.getPathFirmasRegistradores();

                        sNombre = firmaRegistrador.getNombreRegistrador();
                        archivo = firmaRegistrador.getIdArchivo();

                        if (turno.isNacional()) {
                            if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                    || archivo == null || archivo.equals("")) {
                                throw new Exception("No se permiten certificados de orden nacional sin firma");
                            }
                        }

                        //Se recupera el verdadero cargo para definir si es ENCARGADO o
                        //no lo es.
                        cargo = firmaRegistrador.getCargoRegistrador();

                        //Se saca el valor del cargo para imprimirlo en el certificado
                        List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                        cargoToPrint = "";
                        OPLookupCodes lookUp;
                        for (Iterator it = cargos.iterator(); it.hasNext();) {
                            lookUp = (OPLookupCodes) it.next();
                            if (lookUp.getCodigo().equals(cargo)) {
                                cargoToPrint = lookUp.getValor();
                            }
                        }
                    } catch (Exception e) {
                        
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                    if (sNombre == null) {
                        sNombre = "";
                    }

                    //+++
                    impNota.setCargoRegistrador(cargoToPrint);
                    impNota.setNombreRegistrador(sNombre);

                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                            impNota.setPixelesImagenFirmaRegistrador(pixeles);
                       
                    }		
		
		 int impl = 2;
                    if(impresion1 != null){
                        if(!impresion1.equals("") && !impresion1.equals("NONE")){
                        impresion1 = impresion1.trim();
                        impl = Integer.parseInt(impresion1);
                        }
                    }
                    tabla.put("Impresora", evento.getImpresoraSeleccionada());
                            int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), impl, false);
               
		
                    return impNota;
        }
        private Pageable imprimibleNotaDevolutivaCalificacionA(EvnRelacion evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,String impresion1) throws EventoException{
                    ImprimibleNotaDevolutivaCalificacionA impNota = new ImprimibleNotaDevolutivaCalificacionA(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    Pageable peg = null;
                    impNota.setChangetextforregistrador(true);
                     ArrayList matriculasNoInscritas = new ArrayList();
                    List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			impNota.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			impNota.setMatriculasNoInscritas(matriculasNoInscritas);
	             /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
                    //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
                    String nombre = "";
                    String archivo = "";
                    String cargoToPrint = "";
                    String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                    FirmaRegistrador firmaRegistrador = null;
                    CirculoPk cid = new CirculoPk();
                    cid.idCirculo = turno.getIdCirculo();
                    String rutaFisica = null;
                    String sNombre = "";
                    try {
                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }

                        rutaFisica = hermod.getPathFirmasRegistradores();

                        sNombre = firmaRegistrador.getNombreRegistrador();
                        archivo = firmaRegistrador.getIdArchivo();

                        if (turno.isNacional()) {
                            if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                    || archivo == null || archivo.equals("")) {
                                throw new Exception("No se permiten certificados de orden nacional sin firma");
                            }
                        }

                        //Se recupera el verdadero cargo para definir si es ENCARGADO o
                        //no lo es.
                        cargo = firmaRegistrador.getCargoRegistrador();

                        //Se saca el valor del cargo para imprimirlo en el certificado
                        List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                        cargoToPrint = "";
                        OPLookupCodes lookUp;
                        for (Iterator it = cargos.iterator(); it.hasNext();) {
                            lookUp = (OPLookupCodes) it.next();
                            if (lookUp.getCodigo().equals(cargo)) {
                                cargoToPrint = lookUp.getValor();
                            }
                        }
                    } catch (Exception e) {
                        
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                    if (sNombre == null) {
                        sNombre = "";
                    }

                    //+++
                    impNota.setCargoRegistrador(cargoToPrint);
                    impNota.setNombreRegistrador(sNombre);

                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                            impNota.setPixelesImagenFirmaRegistrador(pixeles);
                       
                    }		
		
		
                   
                     int impl = 2;
                    if(impresion1 != null){
                        if(!impresion1.equals("") && !impresion1.equals("NONE")){
                        impresion1 = impresion1.trim();
                        impl = Integer.parseInt(impresion1);
                        }
                    }
                    tabla.put("Impresora",evento.getImpresoraSeleccionada());
                   int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), impl, false);
               
                    return impNota;
        }
        
         private Pageable imprimibleNotaDevolutivaCalificacion(EvnRelacion evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,String impresion1) throws EventoException{
            ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setChangetextforregistrador(true);
		  ArrayList matriculasNoInscritas = new ArrayList();
                    List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			impNota.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			impNota.setMatriculasNoInscritas(matriculasNoInscritas);
                    Pageable peg = null;
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
                    //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
                    //PageFormat pageFormat = new PageFormat();
                    //imprimibleCertificado.generate(pageFormat);
                    String nombre = "";
                    String archivo = "";
                    String cargoToPrint = "";
                    String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                    FirmaRegistrador firmaRegistrador = null;
                    CirculoPk cid = new CirculoPk();
                    cid.idCirculo = turno.getIdCirculo();
                    String rutaFisica = null;
                    String sNombre = "";
                    try {
                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }

                        rutaFisica = hermod.getPathFirmasRegistradores();

                        sNombre = firmaRegistrador.getNombreRegistrador();
                        archivo = firmaRegistrador.getIdArchivo();

                        if (turno.isNacional()) {
                            if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                    || archivo == null || archivo.equals("")) {
                                throw new Exception("No se permiten certificados de orden nacional sin firma");
                            }
                        }

                        //Se recupera el verdadero cargo para definir si es ENCARGADO o
                        //no lo es.
                        cargo = firmaRegistrador.getCargoRegistrador();

                        //Se saca el valor del cargo para imprimirlo en el certificado
                        List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                        cargoToPrint = "";
                        OPLookupCodes lookUp;
                        for (Iterator it = cargos.iterator(); it.hasNext();) {
                            lookUp = (OPLookupCodes) it.next();
                            if (lookUp.getCodigo().equals(cargo)) {
                                cargoToPrint = lookUp.getValor();
                            }
                        }
                    } catch (Exception e) {
                        
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                    if (sNombre == null) {
                        sNombre = "";
                    }

                    //+++
                    impNota.setCargoRegistrador(cargoToPrint);
                    impNota.setNombreRegistrador(sNombre);

                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                            impNota.setPixelesImagenFirmaRegistrador(pixeles);                            
                            
                    }
                     int impl = 2;
                    if(impresion1 != null){
                        if(!impresion1.equals("") && !impresion1.equals("NONE")){
                        impresion1 = impresion1.trim();
                        impl = Integer.parseInt(impresion1);
                        }
                    }
                    tabla.put("Impresora", evento.getImpresoraSeleccionada());
                          int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), impl, false);
                    return impNota;
        }
         /**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno
	 * @param imprimible
	 * @param parametros
	 * @return
	 * @throws EventoException
	 */
	private int imprimirN(ImprimibleBase imprimible, Hashtable parametros, String ID, int numCopias, boolean lanzarExcepcion) throws EventoException {

		boolean impresion_ok = false;
		String mensaje_error = "";

		//CONSTANTES PARA LA IMPRESIÓN.
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";

		Bundle b = new Bundle(imprimible);
                if(isREL){
                    b.setNumeroCopias(0);		
                } else{
                    b.setNumeroCopias(numCopias);  
                }

		String numIntentos = (String) parametros.get(INTENTOS);
		String espera = (String) parametros.get(ESPERA);
                String Impresora = (String) parametros.get("Impresora");
                if(Impresora == null){
                    Impresora = "Microsoft Print to PDF";
                }else if(Impresora == ""){
                    Impresora = "Microsoft Print to PDF";
                }
		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();
		int idImprimible = 0;
                b.setNombreImpresora(Impresora);
		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario o id del circulo (caso especial CORRECCIONES)
			printJobs.enviar(ID, b, intentos, esperado);
			impresion_ok = true;
		}
		catch (PrintJobsException t) {
			idImprimible = t.getIdImpresion();
			if(idImprimible == 0){
				throw new EventoException("Se genero una excepción al imprimir la Nota. " + t.getMessage());
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
			mensaje_error = t.getMessage();
			
			if (lanzarExcepcion && !impresion_ok) {
				throw new EventoException(mensaje_error);
			}
			
		}


		return idImprimible;
	}
     /**
	 * @param string
	 */
	private String obtenerTextoCertificadosAsociados(Turno turno) {

		String retorno = " Y CERTIFICADO ASOCIADO: 0";
		String numeroCertificados = "";
		
		SolicitudAsociada solicitudAsociada = null;
		Solicitud solicitudHija = null;
		Turno turnoHijo = null; 							
		
		if(turno!=null){
			Solicitud solicitud = turno.getSolicitud();
			if(solicitud!=null){
				List solicitudesHijas = solicitud.getSolicitudesHijas();
				if(solicitudesHijas !=null && solicitudesHijas.size()>0){
					Iterator itSolHija = solicitudesHijas.iterator();
					while (itSolHija.hasNext()){
						solicitudAsociada = (SolicitudAsociada)itSolHija.next();
						solicitudHija = solicitudAsociada.getSolicitudHija();
						turnoHijo = solicitudHija.getTurno(); 		
						numeroCertificados = numeroCertificados + turnoHijo.getIdWorkflow() + ", ";					
					}					 
				}
			}			 
		}
		if(numeroCertificados.length()>0){
			numeroCertificados = numeroCertificados.substring(0,(numeroCertificados.length()-2));
			retorno = " Y CERTIFICADOS ASOCIADOS: " + numeroCertificados;
		}
		return retorno;
	}     
        
        
/**
	 * Accion que imprime una constancia de testamentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private Pageable imprimirTestamento(EvnRelacion evento, Testamento test, Turno turno) throws EventoException {
                Pageable peg = null;
		Hashtable tabla = new Hashtable();
		Circulo circulo = null;
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = turno.getIdCirculo();
                try {
                    circulo = forseti.getCirculo(cid);
                } catch (Throwable ex) {
                    circulo = turno.getSolicitud().getCirculo();
                }
		String idWorkflow = null;
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		List historia = turno.getHistorials();
                int i1 = 0;
                String impresion = "";
                TurnoHistoria Histo = null;
                Usuario usuario = null;
                while (i1 < historia.size()) {
                    TurnoHistoria historial = (TurnoHistoria) historia.get(i1);
                    if (historial.getFase().equals(CFase.REG_TESTAMENTO)) {
                        if (i1 != historia.size() - 1) {
                            usuario = historial.getUsuarioAtiende();
                            impresion = historial.getNcopias();
                            Histo = historial;
                        }
                    }
                    i1++;
                }
		int maxIntentos;
		int espera;
		List notas = turno.getNotas();
                Nota captura = null;
                
                int saber = 0;
                for(int i = 0 ; i < notas.size() ; i++){
                    Nota n = (Nota) notas.get(i);
                    if(n.getTiponota().isDevolutiva()){
                        captura = n;
                        saber=1;
                    }
                }
                 if(saber == 1){
                    this.imprimirNotaDevolutiva(evento,captura,turno,Histo); 
                    return peg;
                 }                                                                    
		SolicitudRegistro solicitud =  (SolicitudRegistro)turno.getSolicitud();  

		
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
			if (intentosImpresion != null)
			{
				 tabla.put(INTENTOS,intentosImpresion);
			}
			else
			{
				tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}
			
			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
				tabla.put(ESPERA,esperaImpresion);
			}
			else
			{
				tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
		}
		catch (Throwable t)
		{
			
			tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			
		}
		
		String fechaImpresion = this.getFechaImpresion();
		Date fechaRadicacion = null;
		if(solicitud.getFecha()!=null){
			fechaRadicacion = solicitud.getFecha(); 
		}
                
		ImprimibleInscripcionTestamento impTestamento = new ImprimibleInscripcionTestamento(test, solicitud.getDocumento(), turno.getIdWorkflow() , circulo.getNombre() , fechaImpresion , usuario , fechaRadicacion , CTipoImprimible.INSCRIPCION_TESTAMENTOS );
		impTestamento.setChangetextforregistrador(true);
		String nombre = "";
                String archivo = "";
                String cargoToPrint = "";
                String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                FirmaRegistrador firmaRegistrador = null;
                String rutaFisica = null;
                String sNombre = "";		
		
		try {
                                    firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                                    if (firmaRegistrador == null) {
                                        cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                                    }
                                    if (firmaRegistrador == null) {
                                        cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                                    }
                                    if (firmaRegistrador == null) {
                                        cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                                    }

                                    rutaFisica = hermod.getPathFirmasRegistradores();

                                    sNombre = firmaRegistrador.getNombreRegistrador();
                                    archivo = firmaRegistrador.getIdArchivo();

                                    if (turno.isNacional()) {
                                        if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                                || archivo == null || archivo.equals("")) {
                                            throw new Exception("No se permiten certificados de orden nacional sin firma");
                                        }
                                    }

                                    //Se recupera el verdadero cargo para definir si es ENCARGADO o
                                    //no lo es.
                                    cargo = firmaRegistrador.getCargoRegistrador();

                                    //Se saca el valor del cargo para imprimirlo en el certificado
                                    List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                                    cargoToPrint = "";
                                    OPLookupCodes lookUp;
                                    for (Iterator it = cargos.iterator(); it.hasNext();) {
                                        lookUp = (OPLookupCodes) it.next();
                                        if (lookUp.getCodigo().equals(cargo)) {
                                            cargoToPrint = lookUp.getValor();
                                        }
                                    }
                                } catch (Exception e) {
                                    
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }

                                if (sNombre == null) {
                                    sNombre = "";
                                }

                                //+++
                                impTestamento.setCargoRegistrador(cargoToPrint);
                                impTestamento.setNombreRegistrador(sNombre);

                if (rutaFisica != null) {
                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                     byte pixeles[] = null;
                    try {
                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                    } catch (Throwable e1) {

                        e1.printStackTrace();
                    }
                        impTestamento.setImpFirma(true);
                        impTestamento.setPixelesImagenFirmaRegistrador(pixeles);
                    
                }
		
		if (nombre==null){
                    
		  nombre="";
                }
		
		
		impTestamento.setCargoRegistrador(cargoToPrint);  
		impTestamento.setNombreRegistrador(nombre);
                int idImprimible = 0;
		   int impl = 1;
                    if(impresion != null){
                    if(!impresion.equals("") && !impresion.equals("NONE")){
                    impresion = impresion.trim();
                    impl = Integer.parseInt(impresion);
                    }
                }
                    tabla.put("Impresora", evento.getImpresoraSeleccionada());
                   try{
                   isREL = hermod.isTurnoREL(turno.getIdWorkflow());
                   } catch(Throwable th){
                   }                
                   idImprimible =  this.imprimir(impTestamento, tabla, circulo.getIdCirculo(), impl, false);
                  
               return peg;
	}
        /**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno
	 * @param imprimible
	 * @param parametros
	 * @return
	 * @throws EventoException
	 */
	private int imprimir(ImprimibleBase imprimible, Hashtable parametros, String ID, int numCopias, boolean lanzarExcepcion) throws EventoException {

		boolean impresion_ok = false;
		String mensaje_error = "";

		//CONSTANTES PARA LA IMPRESIÓN.
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";

		Bundle b = new Bundle(imprimible);
                if(isREL){
                    b.setNumeroCopias(0);		
                } else{
                    b.setNumeroCopias(numCopias);	
                }

		String numIntentos = (String) parametros.get(INTENTOS);
		String espera = (String) parametros.get(ESPERA);
                String Impresora = (String) parametros.get("Impresora");
                if(Impresora == null){
                    Impresora = "Microsoft Print to PDF";
                }else if(Impresora == ""){
                    Impresora = "Microsoft Print to PDF";
                }
                b.setNombreImpresora(Impresora);
		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();
		int idImprimible = 0;

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario o id del circulo (caso especial CORRECCIONES)
			printJobs.enviar(ID, b, intentos, esperado);
			impresion_ok = true;
		}
		catch (PrintJobsException t) {
			idImprimible = t.getIdImpresion();
			if(idImprimible == 0){
				throw new EventoException("Se genero una excepción al imprimir la Constancia de testamentos. " + t.getMessage());
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
			mensaje_error = t.getMessage();
			
			if (lanzarExcepcion && !impresion_ok) {
				throw new EventoException(mensaje_error);
			}
			
		}


		return idImprimible;
	}
	    private void imprimirFormularioCalificacionReg(EvnRelacion evento,Turno turno) throws EventoException
                {
                    String UID=evento.getCirculo().getIdCirculo();
                    Hashtable parametros = new Hashtable();
                    SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
                        List folios= solicitud.getSolicitudFolios();
                        TurnoPk oid=new TurnoPk();
                        oid.anio=turno.getAnio();
                        oid.idCirculo=turno.getIdCirculo();
                        oid.idProceso=turno.getIdProceso();
                        oid.idTurno=turno.getIdTurno();
                        Usuario u=evento.getUsuarioSIR();
                        Turno turnoTemp = null;        
                        Usuario usuario = null;
                        String impresion = "";
                        List historia = turno.getHistorials();
                        int ef = 0;
                        while (ef < historia.size()) {
                            TurnoHistoria historial = (TurnoHistoria) historia.get(ef);
                            if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                                if (ef != historia.size() - 1) {
                                    usuario = historial.getUsuarioAtiende();
                                    impresion = historial.getNcopias();
                                }

                            }
                            ef++;
                        }
                        try {
                                //actualizar los folios segun los modificados en la solicitud
                                try{
                                    turnoTemp = hermod.getTurno(oid);
                                    //FolioPk Llave = new FolioPk();
                                    //List ListaAnotacionDefinitovs = null;
                                    ArrayList<Anotacion> Anotacionescalificadas = new ArrayList<Anotacion>();
                                    List FoliosSolc = new ArrayList();
                                    List anotaciones = null;              
                                    if(!folios.isEmpty()){
                                        Iterator itSolFolios = folios.iterator();
                                        while(itSolFolios.hasNext()){         
                                              SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();
                                              Folio f = solFolio.getFolio();
                                              boolean ok = false;
                                              FolioPk Pkfolio = new FolioPk();
                                              Pkfolio.idMatricula = f.getIdMatricula();
                                              f = forseti.getFolioByID(Pkfolio);
                                              if(f == null){
                                                  f = forseti.getDeltaFolio(Pkfolio, usuario);
                                              }
                                              f.getAnotaciones().clear();
                                              int count = 0;
                                              anotaciones =forseti.getAnotacionesFolio(Pkfolio);    
                                              for(int i = 0 ; i < anotaciones.size(); i++){
                                                  Anotacion anotmp = (Anotacion) (Anotacion) anotaciones.get(i);
                                                  try{
                                                      if(anotmp.getNumRadicacion().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno)){
                                                      f.addAnotacion(count, anotmp);
                                                      count = count +1;
                                                      }
                                                  }catch(Throwable ex){

                                                  }
                                              }
                                               if(f.getAnotaciones().isEmpty()){
                                                
                                                }else{
                                                solFolio.setFolio(f);
                                                FoliosSolc.add(solFolio);
                                                anotaciones.clear();
                                                }
                                    }
                                    turnoTemp.getSolicitud().setSolicitudFolios(FoliosSolc);        
                                  }
                                }catch (Throwable e2){
                                        ExceptionPrinter ep=new ExceptionPrinter(e2);
                                        throw new EventoException("No se pudieron obtener los folios calificados:"+e2.getMessage(),e2);
                                }

        //			obtener textos base de los separadores
                                String tbase1 ="";
                                String tbase2 = "";
                                List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
                                for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
                                        OPLookupCodes op = (OPLookupCodes) j.next();
                                        if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
                                                tbase1= op.getValor();
                                        }
                                        if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
                                                tbase2 = op.getValor();
                                        }
                                }
                                
                                //crear el imprimible de formulario
                                String fechaImpresion = this.getFechaImpresion();
                                ImprimibleFormulario impFormulario = new ImprimibleFormulario(turnoTemp,usuario.getUsername(),CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
                                impFormulario.setIdUsuario(Long.toString(usuario.getIdUsuario()));
                                impFormulario.setPrintWatermarkEnabled(true);
                                impFormulario.setTurno(turno);
                                impFormulario.setTEXTO_BASE1(tbase1);
                                impFormulario.setTEXTO_BASE2(tbase2);
                                impFormulario.setChangetextforregistrador(true);
                                 
                                //PageFormat pageFormat = new PageFormat();
                                //imprimibleCertificado.generate(pageFormat);
                                String nombre = "";
                                String archivo = "";
                                String cargoToPrint = "";
                                String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                                FirmaRegistrador firmaRegistrador = null;
                                CirculoPk cid = new CirculoPk();
                                cid.idCirculo = turno.getIdCirculo();
                                String rutaFisica = null;
                                String sNombre = "";
                                try {
                                    firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                                    if (firmaRegistrador == null) {
                                        cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                                    }
                                    if (firmaRegistrador == null) {
                                        cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                                    }
                                    if (firmaRegistrador == null) {
                                        cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                                    }

                                    rutaFisica = hermod.getPathFirmasRegistradores();

                                    sNombre = firmaRegistrador.getNombreRegistrador();
                                    archivo = firmaRegistrador.getIdArchivo();

                                    if (turno.isNacional()) {
                                        if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                                || archivo == null || archivo.equals("")) {
                                            throw new Exception("No se permiten certificados de orden nacional sin firma");
                                        }
                                    }

                                    //Se recupera el verdadero cargo para definir si es ENCARGADO o
                                    //no lo es.
                                    cargo = firmaRegistrador.getCargoRegistrador();

                                    //Se saca el valor del cargo para imprimirlo en el certificado
                                    List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                                    cargoToPrint = "";
                                    OPLookupCodes lookUp;
                                    for (Iterator it = cargos.iterator(); it.hasNext();) {
                                        lookUp = (OPLookupCodes) it.next();
                                        if (lookUp.getCodigo().equals(cargo)) {
                                            cargoToPrint = lookUp.getValor();
                                        }
                                    }
                                } catch (Exception e) {
                                    throw e;
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }

                                if (sNombre == null) {
                                    sNombre = "";
                                }

                                //+++
                                impFormulario.setCargoRegistrador(cargoToPrint);
                                impFormulario.setNombreRegistrador(sNombre);

                                if (rutaFisica != null) {
                                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                                     byte pixeles[] = null;
                                    try {
                                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                                    } catch (Throwable e1) {

                                        e1.printStackTrace();
                                    }
                                        impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
                                }

                                //mandar a imprimir documento
                        try{
                                isREL = hermod.isTurnoREL(turno.getIdWorkflow());
                                 int impl = 1;
                                            
                                 if(impresion != null){
                                    if(!impresion.equals("") && !impresion.equals("NONE")){
                                    impresion = impresion.trim();
                                    impl = Integer.parseInt(impresion);
                                    }
                                }
                                for(int e =0; e < impl; e++){
                                    this.imprimir(impFormulario, UID,evento.getImpresoraSeleccionada());  
                                }

                        }catch(PrintJobsException exc){
                                int idImprimible = exc.getIdImpresion();
                                if(idImprimible!=0){
                                        throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
                                }
                        } 
                        } catch (Throwable e1) {
                                ExceptionPrinter ep=new ExceptionPrinter(e1);
                                Log.getInstance().error(ANRelacion.class,"No se pudo imprimir el formulario:"+ep.toString());
                        }
                     
                }
            
        /**
     * @param imprimible
     * @param uid
     */
    private void imprimir(ImprimibleBase imprimible, String uid,String Nameimpresora) throws PrintJobsException, EventoException {


		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
        int maxIntentos;
        int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null)
			{
				 Integer intentos = new Integer (intentosImpresion);
				 maxIntentos = intentos.intValue();
			}
			else
			{
				 Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
				 maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null)
			{
				 Integer esperaInt = new Integer(esperaImpresion);
				 espera = esperaInt.intValue();
			}
			else
			{
				Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		}
		catch (Throwable t)
		{
			Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();


		}


        Bundle bundle = new Bundle(imprimible);
        if(isREL){
        bundle.setNumeroCopias(0);
        }
        if(Nameimpresora == null){
            Nameimpresora = "Microsoft Print to PDF";
        }else if(Nameimpresora == ""){
            Nameimpresora = "Microsoft Print to PDF";
        }
        bundle.setNombreImpresora(Nameimpresora);
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(uid, bundle, maxIntentos, espera);
        }catch (PrintJobsException pe) {
			throw pe;
		}catch (Throwable t) {
            t.printStackTrace();
        }
    }
	private  String metodoGeneralTurnosCorreecion(List folios,Turno turno) throws EventoException {
		Iterator it=folios.iterator();
		List TurnosCorreccion = new ArrayList();
		String StrFolios = "";
		String listaTurnos = " y Turno(s): ";
		while(it.hasNext()){
				SolicitudFolio solF=(SolicitudFolio)it.next();
				try {
					List TurnosCorreccionAux = forseti.getTurnosCorreccionActivosFolio(solF.getFolio(), turno);
					if(TurnosCorreccionAux != null && TurnosCorreccionAux.size() > 0){
						Iterator iter = TurnosCorreccionAux.iterator();
						while(iter.hasNext()){
							Turno t = (Turno)iter.next();
							listaTurnos += t.getIdWorkflow() + ". ";
						}
						TurnosCorreccion.addAll(TurnosCorreccionAux);
						StrFolios += solF.getFolio().getIdMatricula(); 
					}
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new TomarTurnoRegistroException(e.getMessage());
				}
		}

		TurnoPk turnoID = new TurnoPk();
		turnoID.idCirculo = turno.getIdCirculo();
		turnoID.idProceso = turno.getIdProceso();
		turnoID.idTurno = turno.getIdTurno();
		turnoID.anio = turno.getAnio();
		
		Hashtable tablaAnotacionesTemporales = new Hashtable();
		String msgAnotaciones = "";
		try {
				tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(turnoID);
			
			//Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
			
			Iterator it2=folios.iterator();
			Boolean tieneAnotacionesTemporales = new Boolean(false);
			while(it2.hasNext() && !tieneAnotacionesTemporales.booleanValue()){
					SolicitudFolio solFTieneAnotaciones=(SolicitudFolio)it2.next();
					tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFTieneAnotaciones.getIdMatricula());
			}


			//String msgTurnosCorreccion = "\n ";
							
			/*if(TurnosCorreccion != null){
				Iterator iterCorrecciones  = TurnosCorreccion.iterator();
				while(iterCorrecciones.hasNext()){
					Turno turnoCorrec = (Turno)iterCorrecciones.next();
					if(turnoCorrec.getIdTurno() != null){
						msgTurnosCorreccion +=turnoCorrec.getIdWorkflow()+", "; 
					}
				}
			}*/
			
			if(tieneAnotacionesTemporales.booleanValue()  && TurnosCorreccion != null && TurnosCorreccion.size()>0){
				msgAnotaciones = "\n El turno contiene folios  los cuales tienen anotaciones temporales y debe" +
							     "\n borrar los temporales para darle prioridad a el turno de correccion." +
							     "\n Folio(s) Asociado(s): ";
				
				msgAnotaciones += StrFolios + listaTurnos;
				
				/*Throwable e1 = new Throwable(); 
				ExceptionPrinter ep=new ExceptionPrinter(e1);*/
				//logger.error("No fue posible tomar el turno:"+ep.toString());
				//throw new TomarTurnoRegistroException("el turno no puede seguir por que hay folio(s) relacionados con turno(s) de Correccion"+msgAnotaciones,e1);
				msgAnotaciones = " El turno no puede seguir por que hay turnos relacion que tienen folio(s) relacionados con turno(s) de Correccion. "+msgAnotaciones;
			}else if(TurnosCorreccion != null && TurnosCorreccion.size()>0){

				/*Throwable e1 = new Throwable(); 
				ExceptionPrinter ep=new ExceptionPrinter(e1);*/
				//logger.error("No fue posible tomar el turno:"+ep.toString());
				//throw new TomarTurnoRegistroException("el turno no es valido por que esta en la fase correccion. "+msgTurnosCorreccion,e1);
				msgAnotaciones = " el turno no puede seguir por que hay turnos relacion que tienen folio(s) relacionados con turno(s) de Correccion. "+
							     "\n Folio(s) Asociado(s): "+StrFolios + listaTurnos;
			}
		} catch (Throwable e) {				
			e.printStackTrace();
		}
		return msgAnotaciones;
		
	}

	private void delegarBloqueoFolios(Turno turno, Usuario usuario) throws EventoException {

	    TurnoPk turnoId = new TurnoPk();
	    turnoId.anio=turno.getAnio();
	    turnoId.idCirculo=turno.getIdCirculo();
	    turnoId.idProceso=turno.getIdProceso();
	    turnoId.idTurno=turno.getIdTurno();

		try{
			if(turno.getModoBloqueo()!=CModoBloqueo.DELEGAR_CUALQUIERA && turno.getIdFase().equals(CFase.REG_FIRMAR)){
				turno.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
				hermod.actualizarTurnoModoBloqueo(turno);
				turno = hermod.getTurno(turnoId);
			}
		}catch (Throwable e1) {
			throw new EventoException("No se pudo consultar el turno", e1);
		}

		SolicitudRegistro solicitudRegistro = (SolicitudRegistro)turno.getSolicitud();
		boolean delegarBloqueos = true;

		if(solicitudRegistro.getLiquidaciones().size()>0){
			LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solicitudRegistro.getLiquidaciones().get(0);
			List actos = liquidacion.getActos();
			delegarBloqueos = !tieneTestamentos(actos);
		}

		if(delegarBloqueos) {
			try {
				forseti.delegarBloqueoFolios(turnoId, usuario);
			} catch (Throwable e1) {
				throw new EventoException("No se pudo delegar el bloqueo de folios",e1);
			}
		}
	}

	private List reordenarTurnosPorFechaInicio(List turnos) {

		// Shell Sort
		int increment = 3, j;

		while(increment > 0) {
			for(int i = 0; i < turnos.size(); i++) {
				Turno turnoActual = (Turno)turnos.get(i);
				for(j = i; j >= increment && ((Turno)turnos.get(j - increment)).getFechaInicio().compareTo(turnoActual.getFechaInicio()) > 0; j -= increment) {
					turnos.set(j, turnos.get(j - increment));
				}
				turnos.set(j, turnoActual);
			}

			if(increment / 2 != 0)
				increment = increment / 2;
			else if(increment == 1)
				increment = 0;
			else
				increment = 1;
		}

		// Se devuelve una lista nueva
		//return turnos.subList(0, turnos.size());
		List temp = turnos.subList(0, turnos.size());
		List tempDefinitiva = new ArrayList();
		if(temp!=null){
			Iterator it = temp.iterator();
			while (it.hasNext()){
				tempDefinitiva.add(it.next());
			}
		}
		return tempDefinitiva;
	}

	private EventoRespuesta seleccionarRelacion(EvnRelacion evento) throws EventoException {

		TipoRelacion tipoRelacion;
		boolean mostrarCodigo = false, mostrarVencimiento = false, mostrarNumeroDocumento = false,  verimp = false,verimp1 = false;
		boolean mostrarDetalle = false;
		List turnos = null;

		long idProceso = evento.getIdProceso();
		String idFase = evento.getIdFase();

		try {
			TipoRelacionPk idTipoRelacion = new TipoRelacionPk();
			idTipoRelacion.idTipoRelacion = evento.getIdTipoRelacion();
			tipoRelacion = hermod.getTipoRelacion(idTipoRelacion);

            // Si la relación corresponde al proceso de registro, se debe mostrar su número
            // de documento
            String idProcesoString = "" + idProceso;
			if(idProcesoString.equals(CProceso.PROCESO_REGISTRO)) {
				mostrarNumeroDocumento = true;
			}

			if(tipoRelacion != null) {
                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DEVUELTOS_PARA_FIRMA) ||tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA) ||
                                        tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_INSCRITOS_PARA_FIRMA)){
                                    verimp = true;
                               }
                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS) ||
                                        tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)){
                                    verimp = true;
                               }
                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA)){
                                     verimp = false;
                                     verimp1 = true;
                                }
                                 if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)){
                                     verimp = false;
                                     verimp1 = true;
                                }
				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_PARA_CUSTODIA_MAYOR_VALOR)) {
					mostrarVencimiento = true;
				} else if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)||
                                                
                                                /**
                                                 * @author      :   Carlos Torres
                                                 * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                                                 **/        
                                                tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_REGISTRO)||
                                                tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_CORRESPONDIENCIA)) {
					mostrarCodigo = true;
				}
			}
			if(mostrarCodigo){
				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA)) {
					mostrarDetalle = true;
				}
			}
			if(!mostrarCodigo) {
				ProcesoPk procesoId = new ProcesoPk();

				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_DEVUELTO) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_EXPEDIDO) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_PARA_ANTIGUO_SISTEMA) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_DE_ENTREGA_DEVUELTO)) {
					
					procesoId.idProceso = 1;
					
				} else {
					if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_REPARTO)) {
						procesoId.idProceso = 6;
					} else  {
						procesoId.idProceso = idProceso;	
					}
				}
					

				Proceso proceso = hermod.getProcesoById(procesoId);

				Fase fase = hermod.getFase(idFase);

				Circulo circulo = evento.getCirculo();

				turnos = hermod.getTurnosParaRelacion(proceso, fase, circulo, tipoRelacion);
			}
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError("Error al obtener la relación: " + th.getMessage());
			throw exception;
		}
		
		try {
			Collections.sort(turnos, new IDidTurnoComparator());
		} catch (Exception e) {
			Log.getInstance().error(ANRelacion.class,"No se pudieron ordenar los Turnos");
		}
		EvnRespRelacion respuesta = new EvnRespRelacion(turnos, EvnRespRelacion.SELECCIONAR_RELACION_OK);

		// Se deben ordenar los turnos correctamente
		respuesta.setMostrarDetalle(mostrarDetalle);
                respuesta.setMostrarverimp(verimp);
                respuesta.setMostrarverimp2(verimp1);
		respuesta.setMostrarIdRelacion(mostrarCodigo);
		respuesta.setMostrarVencimientoMayorValor(mostrarVencimiento);
		respuesta.setMostrarNumeroDocumento(mostrarNumeroDocumento);

		return respuesta;
	}

	private EventoRespuesta seleccionarProceso(EvnRelacion evento) throws EventoException {

		Proceso proceso = new Proceso();
		proceso.setIdProceso(evento.getIdProceso());
		List fasesProceso = null;

		try {
			//fasesProceso = hermod.getFasesDeProceso(proceso);
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = evento.getUsuario();
			long idProceso = evento.getIdProceso();
			ProcesoPk procesoId = new ProcesoPk();
			procesoId.idProceso = idProceso;

			Usuario usuario = fenrir.getUsuario(usuarioAuriga);
			List roles = fenrir.darRolUsuario(usuario.getIdUsuario());

			ArrayList fases=new ArrayList();
			List fasesTemp;
			Rol rol;
		
			/*Comparadores para permitir relacionar revision y analisis al rol de cajero y 
			asegurar que no se repita la fase en la lista*/
			
			Fase comparador=hermod.getFase(CFase.COR_REVISION_ANALISIS);
			Fase comparador_2=hermod.getFase(CFase.COR_REVISION_ANALISIS);
			
			comparador.setEstacion(CRol.SIR_ROL_RESPONSABLE_CORRECCIONES);
			comparador_2.setEstacion(CRol.SIR_ROL_CAJERO_CORRECCIONES);
			
			for(Iterator itRoles = roles.iterator(); itRoles.hasNext();) {
				rol = (Rol)itRoles.next();
				fasesTemp = hermod.getFases(rol, proceso);
				if(String.valueOf(proceso.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)) {
					Proceso procesoCorreccionInterna = new Proceso();
					procesoCorreccionInterna.setIdProceso(Long.parseLong(CProceso.PROCESO_CORRECCION_CALIFICACION));
					fasesTemp.addAll(hermod.getFases(rol, procesoCorreccionInterna));      
				}
                                
				//Validar que la fase de revision y analisis solo este una vez
				if(fasesTemp.contains(comparador)&&fases.contains(comparador_2)){
					fasesTemp.remove(comparador);
				}
				
				fases.addAll(fasesTemp);
                                
//				Se debe relacionar la fase de entrega al proceso de Antiguo Sistema para cumplir con el requerimiento TFS 2740
				if (rol.getRolId().equals(CRol.SIR_ROL_ENCARGADO_ANTIGUO_SISTEMA)) {
					fases.add(new Fase(CFase.CER_ENTREGAR,"ENTREGA CERTIFICADOS","",CRol.SIR_ROL_ENCARGADO_ANTIGUO_SISTEMA));
				}
				
				//TFS2737 
				if (rol.getRolId().equals(CRol.SIR_ROL_MESA_CONTROL_CORRECCIONES) &&
					String.valueOf(proceso.getIdProceso()).equals(CProceso.CORRECCION_EXTERNA)) {
					fases.add(new Fase(CFase.COR_MESA_CONTROL,"MESA CONTROL CORRECIONES","",CRol.SIR_ROL_MESA_CONTROL_CORRECCIONES));
				}
				
				// TFS 3596: Relacionar la fase de revision y analisis al rol de cajero de correcciones
				if(rol.getRolId().equals(CRol.SIR_ROL_CAJERO_CORRECCIONES)&&(!(fases.contains(comparador))||(fases.contains(comparador_2)))&&
						String.valueOf(proceso.getIdProceso()).equals(CProceso.CORRECCION_EXTERNA)) 
				{
					fases.add(comparador_2);
				}
				
				fasesTemp.clear();
			}
			
			

			
			fasesProceso = new Vector();
			fasesProceso.addAll(fases);

			fases.clear();
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError("Error al obtener el listado de fases para el proceso: " + th.getMessage());
			throw exception;
		}

		return new EvnRespRelacion(fasesProceso, EvnRespRelacion.SELECCIONAR_PROCESO_OK);
	}

	private EventoRespuesta seleccionarFase(EvnRelacion evento) throws EventoException {

		List relacionesFase;

		try {
                            relacionesFase = hermod.getTiposRelacionesFase(evento.getIdFase());
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError("Error al obtener el listado de relaciones: " + th.getMessage());
			throw exception;
		}

		return new EvnRespRelacion(relacionesFase, EvnRespRelacion.SELECCIONAR_FASE_OK);
	}

	private EventoRespuesta cargarDatos(EvnRelacion evento) throws EventoException {

		List procesos;
		EvnRespRelacion evn1;
		EvnRespRelacion evn2;
		long idProceso = Long.parseLong(CProceso.PROCESO_REGISTRO);
		String idFase = CFase.REG_MESA_CONTROL;
		try {
			procesos = hermod.getListaProcesosRelacion();
			evento.setIdProceso(idProceso);
			evento.setIdFase(idFase);
			evn1 = (EvnRespRelacion)seleccionarProceso(evento);
			evn2 = (EvnRespRelacion)seleccionarFase(evento);
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError("Error al obtener el listado de procesos: " + th.getMessage());
			throw exception;
		}
                Map impresoras = null;
                Circulo cir = evento.getCirculo();
                try {
                    if (cir != null) {
                        impresoras = forseti.getConfiguracionImpresoras(cir.getIdCirculo());
                    }
                } catch (HermodException e1) {
                    throw new EventoException(e1.getMessage(), e1);
                } catch (FenrirException e) {
                    throw new EventoException(e.getMessage(), e);
                } catch (Throwable e) {
                    Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
                    throw new EventoException(e.getMessage(), e);
                }
		EvnRespRelacion respuesta = new EvnRespRelacion(procesos, EvnRespRelacion.CARGAR_DATOS_OK);
		respuesta.setFases((List)evn1.getPayload());
                respuesta.setImpresoras(impresoras);
                respuesta.setCirculo(cir);
		respuesta.setRelacionesFase((List)evn2.getPayload());
		return respuesta ;
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
				if((acto.getTipoActo().getIdTipoActo().equals(CActo.ID_TESTAMENTOS))||(acto.getTipoActo().getIdTipoActo().equals(CActo.ID_REVOCATORIA_TESTAMENTO))){
					tieneTestamento = true;
					break;
				}
			}
		}

		return tieneTestamento;
	}

	private void hacerFoliosDefinitivos(Turno turno, Usuario usuario) throws EventoException {

		TurnoPk turnoId = new TurnoPk();
		turnoId.anio = turno.getAnio();
		turnoId.idCirculo = turno.getIdCirculo();
		turnoId.idProceso = turno.getIdProceso();
		turnoId.idTurno = turno.getIdTurno();

		SolicitudRegistro solicitudRegistro = (SolicitudRegistro)turno.getSolicitud();


		if(solicitudRegistro.getLiquidaciones().size() > 0) {
			LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solicitudRegistro.getLiquidaciones().get(0);
			List actos = liquidacion.getActos();
			boolean testamento = tieneTestamentos(actos);

			if(!testamento) {
				List folios= solicitudRegistro.getSolicitudFolios();
				//List matriculas = new ArrayList();

				try{
					List solicitudesFoliosCalificados = null;
					solicitudesFoliosCalificados = this.getSolicitudesFolioCalificadas(forseti, turnoId, usuario);
					turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);

					for(Iterator itf = folios.iterator(); itf.hasNext();) {
						SolicitudFolio sf =(SolicitudFolio) itf.next();
						Folio f = sf.getFolio();
						forseti.hacerDefinitivoFolio(f, usuario, turnoId, true);
					}
				} catch (ForsetiException e1) {
					throw new EventoException("No se pudieron hacer definitivos los folios:"+e1.getMessage(),e1);
				} catch (Throwable e2) {
					throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
				}
			}
		}
	}

	private List getSolicitudesFolioCalificadas(ForsetiServiceInterface forseti,TurnoPk oid, Usuario u)
	throws Throwable {

		List deltaFolios = forseti.getDeltaFolios(oid, u);
		Vector solicitudesFoliosCalificados = new Vector();

		for (int i=0; i<deltaFolios.size();i++) {
			SolicitudFolio solFolioDelta =(SolicitudFolio)deltaFolios.get(i);
			Folio folioDelta = solFolioDelta.getFolio();

			FolioPk fid = new FolioPk();
			fid.idMatricula = solFolioDelta.getIdMatricula();

			Folio folioCal=forseti.getFolioByIDSinAnotaciones(fid, u);
			if(folioDelta.getCodCatastral()!=null){
				folioCal.setCodCatastral(folioDelta.getCodCatastral());
			}
			if(folioDelta.getTipoPredio()!=null){
				folioCal.setTipoPredio(folioDelta.getTipoPredio());
			}

			List anotaciones =folioDelta.getAnotaciones();
			for(int j=0; j<anotaciones.size();j++) {
				Anotacion anota = (Anotacion)anotaciones.get(j);
				if (anota.getNumRadicacion() != null && anota.getNumRadicacion().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno))
				{
					folioCal.addAnotacione(anota);
				}
			}

			SolicitudFolio solFolioCal = new SolicitudFolio();
			solFolioCal.setFolio(folioCal);
			solicitudesFoliosCalificados.add(solFolioCal);
		}
		return solicitudesFoliosCalificados;
	}
	
	/**
     * Imprimir el certificado asociado al turno.
     * @param turno el turno
     * @param parametros tabla de Hashing con los parametros de impresion (además de los del WorkFlow)
     * @return la tabla de hashing de parametros adicionando un registro dependiendo de si la impresion fue
     * o no exitosa.
     * @throws Throwable
     */
	private Hashtable imprimirCertificado(Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR, String UID ) throws Throwable
	{
		SolicitudCertificado solCerti = (SolicitudCertificado)turno.getSolicitud();
		List listaFolios = solCerti.getSolicitudFolios();
		int numCopias=solCerti.getNumeroCertificados();
		Hashtable parametros = new Hashtable();
		
		parametros.put("USUARIO_SIR", usuarioSIR.getUsername()  );
		parametros.put("CIRCULO_O_UID", UID  );
		
		Log.getInstance().info(ANRelacion.class,"\n*******************************************************");
		Log.getInstance().info(ANRelacion.class,"(ANTES METODO IMPRESION CERTIFICADO)");
		Log.getInstance().info(ANRelacion.class,"\n*******************************************************\n");
		
	    //Obtener los parámetros.
        //String notificationId = (String) parametros.get(Processor.NOT_ID);
        String intentosImpresion;
        String esperaImpresion;

		//INGRESO DE INTENTOS DE IMPRESION
		try{

			intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null){
				 parametros.put(Processor.INTENTOS, intentosImpresion);
			}
			else{
				 intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
				 parametros.put(Processor.INTENTOS, intentosImpresion);
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (esperaImpresion != null){
				parametros.put(Processor.ESPERA, esperaImpresion);
			}
			else{
				esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
				parametros.put(Processor.ESPERA, esperaImpresion);
			}
		}
		catch (Throwable t){
			intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
			parametros.put(Processor.INTENTOS, intentosImpresion);
			esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
			parametros.put(Processor.ESPERA, esperaImpresion);
		}		
		
		

		/*
		 * No hay un folio asociado y no hay nada que imprimir
		 * (Por ejemplo certificado de pertenencia sin matricula asociada).
		 */
		if (listaFolios.size()<1)
		  return parametros;

		SolicitudFolio solFolio = (SolicitudFolio)listaFolios.get(listaFolios.size()-1);
		//List foliosDerivados = new Vector();
		Folio folio=null;

		List liquidaciones = solCerti.getLiquidaciones();
		LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado)liquidaciones.get(liquidaciones.size()-1);
		String tipoTarifa =liquidacion.getTipoTarifa();

		folio=forseti.getFolioByMatricula( solFolio.getIdMatricula() );
		if(folio.getAnotaciones() == null || folio.getAnotaciones().isEmpty()){
			FolioPk folioPk = new FolioPk();
			folioPk.idMatricula = folio.getIdMatricula();
			List anotaciones = forseti.getAnotacionesFolio(folioPk);
			folio.setAnotaciones(anotaciones);
		}

		CirculoPk cid = new CirculoPk();
		cid.idCirculo = turno.getIdCirculo();

		FolioPk fid = new FolioPk();
		fid.idMatricula = folio.getIdMatricula();

		List padres=forseti.getFoliosPadre(fid);
		List hijos=forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                /**
                * @author: David Panesso
                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                * Nuevo listado de folios derivados
                **/
                List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

		String usuario = (String)parametros.get("USUARIO_SIR");
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = new gov.sir.core.negocio.modelo.Usuario();
		usuarioNeg.setUsername(usuario);
		String fechaImpresion= this.getFechaImpresion();
		String tipoImprimible=CTipoImprimible.CERTIFICADO_INMEDIATO;
		if (tipoTarifa!=null && tipoTarifa.equals(CHermod.EXENTO)){
			tipoImprimible=CTipoImprimible.CERTIFICADO_EXENTO;
		}
		
		if (!solCerti.getSolicitudesPadres().isEmpty()){
			if (((SolicitudAsociada)solCerti.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudCertificadoMasivo){
				tipoImprimible=CTipoImprimible.CERTIFICADOS_MASIVOS;
			}else{
				tipoImprimible=CTipoImprimible.CERTIFICADO_ASOCIADO;	
			}
			
		}
		
		if (forseti.mayorExtensionFolio(folio.getIdMatricula())){
			tipoImprimible=CTipoImprimible.CERTIFICADO_EXTENSO;
		}
		
		//obtener textos base de los separadores
		String tbase1 ="";
		String tbase2 = "";
		List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
		for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
			OPLookupCodes op = (OPLookupCodes) it.next();
			if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
				tbase1= op.getValor();
			}
			if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
				tbase2 = op.getValor();
			}
		}
		ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio,turno,padres,hijos, foliosDerivadoHijos, fechaImpresion,tipoImprimible, tbase1, tbase2);
		imprimibleCertificado.setPrintWatermarkEnabled(true);
		imprimibleCertificado.setUsuario(usuarioSIR);
                /**
                 * @author     : Carlos Torres
                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                 */
                Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                imprimibleCertificado.setInfoTraslado(traslado);

		String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

		FirmaRegistrador firmaRegistrador = null;

		String sNombre = "";
		String archivo = "";
		String cargoToPrint = "";
		String rutaFisica = null;

	    try{
			firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);

			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}

			rutaFisica =  hermod.getPathFirmasRegistradores();

			sNombre = firmaRegistrador.getNombreRegistrador();
			archivo = firmaRegistrador.getIdArchivo();

			//Se recupera el verdadero cargo para definir si es ENCARGADO o
			//no lo es.
			cargo = firmaRegistrador.getCargoRegistrador();

			//Se saca el valor del cargo para imprimirlo en el certificado
			List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

			cargoToPrint = "";
			OPLookupCodes lookUp;
			for(Iterator it = cargos.iterator(); it.hasNext();){
				lookUp = (OPLookupCodes) it.next();
				if(lookUp.getCodigo().equals(cargo)){
					cargoToPrint = lookUp.getValor();
				}
			}
	    }
	    catch(Throwable t){
	    	t.printStackTrace();
	    }

		if (sNombre==null)
		  sNombre="";


		imprimibleCertificado.setCargoRegistrador(cargoToPrint);
		imprimibleCertificado.setNombreRegistrador(sNombre);


		if(rutaFisica!=null){
			BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

			byte pixeles[]=null;
			try{
				pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
			}
			catch (Throwable e1) {
				e1.printStackTrace();
			}
		    imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
		}

		Log.getInstance().info(ANRelacion.class,"Tipo de Tarifa = "+tipoTarifa);
		if (tipoTarifa!=null && tipoTarifa.equals(CHermod.EXENTO)){
			imprimibleCertificado.setExento(true);
			String textoExento = hermod.getTextoExento();
			imprimibleCertificado.setTextoExento(textoExento);
		}else{
			imprimibleCertificado.setExento(false);
		}

   	    parametros = this.imprimirCertificadoImprimir(turno,imprimibleCertificado,parametros,numCopias);

   	    String resultado =(String)parametros.get(Processor.RESULT);
   	    boolean okImpresion = true;
   	    if (resultado!=null){
   	    	if ( resultado.equals("ERROR")){
				okImpresion = false;
   	    	}
   	    }

   	    if (okImpresion){
   	    	//actualizar el número de impresiones en el turno
			int numImpresiones = solCerti.getNumImpresiones();
   	    	solCerti.setNumImpresiones(numImpresiones+1);
   	    	hermod.updateSolicitudCertificado(solCerti);
   	    }
   	    
   	     Log.getInstance().info(ANRelacion.class,"\n*******************************************************");
   	     Log.getInstance().info(ANRelacion.class,"(DESPUES METODO IMPRESION CERTIFICADO)");
   	     Log.getInstance().info(ANRelacion.class,"\n*******************************************************\n");
   	    
		return parametros;
	}
	
	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
	protected String getFechaImpresion()
	{

		Calendar c = Calendar.getInstance();
		int dia, ano, hora;
		String min, seg, mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);
		if (hora > 12)
			hora -= 12;

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp =
			"Impreso el "
				+ dia
				+ " de "
				+ mes
				+ " de "
				+ ano
				+ " a las "
				+ formato(hora)
				+ ":"
				+ min
				+ ":"
				+ seg
				+ " "
				+ DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}
	
	public static BufferedImage getImage(String path, String nombreArchivo){
		String nombreCompleto = getNombreCompleto(path,nombreArchivo);
		BufferedImage buf = null;

		try{
			File file = new File(nombreCompleto);
			buf = ImageIO.read(file);
		}
		catch (IOException e){
			Log.getInstance().error(ANRelacion.class,e);
			Log.getInstance().error(ANRelacion.class,"Error imprimiendo el gráfico en la ruta: "+nombreCompleto);
		}

		return buf;
	}
	
	public static String getNombreCompleto(String path, String nombreArchivo){
		String nombreCompleto=null;

		if (!path.trim().equals(CHermod.CADENA_VACIA))
		  nombreCompleto = path + nombreArchivo;
		else
		  nombreCompleto = nombreArchivo;

	  return nombreCompleto;
	}
	
	/**
	 * Metodo que retorna un numero con un "0" antes en caso de ser menor
	 * que 10.
	 * @param i el numero.
	 * @return
	 */
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
		return (new Integer(i)).toString();
	}
	
	/**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno es el Turno con el que se creo la solicitud.
	 * @param imprimible es la representacion del documento que se desea imprimir
	 * (Certificado, Oficio de Pertenencia, Nota Devolutiva, Formulario de Calificacion,
	 *  Formulario de Correccion, etc).
	 * @param parametros tabla de Hashing con los parametros de impresión (además de los parametros asociados al
	 *  WorkFlow).
	 * @param numCopias es el número de copias que se desea imprimir.
	 * @return
	 */
	private Hashtable imprimirCertificadoImprimir(Turno turno, ImprimibleBase imprimible, Hashtable parametros, int numCopias) {

		String circulo = turno.getIdCirculo();
		//String impresora = (String)parametros.get(IMPRESORA);
		String imp = (String) parametros.get("CIRCULO_O_UID");

		//Opción para imprimir en local o en el applet administrativo de impresión
		if (imp != null) {
			circulo = imp;
		}

		//Bundle b = new Bundle(imprimible,impresora);
		Bundle b = new Bundle(imprimible);

		String numIntentos = (String) parametros.get(Processor.INTENTOS);
		String espera = (String) parametros.get(Processor.ESPERA);

		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();
		boolean resultadoImpresion = false;

		//settear el numero de impresiones
		b.setNumeroCopias(numCopias);

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
		try {
			printJobs.enviar(circulo, b, intentos, esperado);
			//si imprime exitosamente sale del ciclo.
			resultadoImpresion = true;
		} catch (Throwable t) {
			t.printStackTrace();
			resultadoImpresion = false;
		}

		if (resultadoImpresion) {
			parametros.put(Processor.RESULT, "CONFIRMAR");
		} else {
			parametros.put(Processor.RESULT, "ERROR");
		}
		return parametros;
	}
	
}
