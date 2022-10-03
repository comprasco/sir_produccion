package gov.sir.core.negocio.acciones.antiguosistema;

import gov.sir.core.eventos.antiguosistema.EvnAntiguoSistema;
import gov.sir.core.eventos.antiguosistema.EvnRespAntiguoSistema;
import gov.sir.core.eventos.antiguosistema.EvnResp_AntigSistHojaRutaSaveInfo;
import gov.sir.core.eventos.antiguosistema.Evn_AntigSistHojaRutaSaveInfo;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.is21.Encriptador;
import gov.sir.core.negocio.acciones.excepciones.AvanzarCalificacionException;
import gov.sir.core.negocio.acciones.excepciones.CancelarAnotacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaFolioException;
import gov.sir.core.negocio.acciones.excepciones.ErrorHacerDefinitivoFolioException;
import gov.sir.core.negocio.acciones.excepciones.ErrorHojaRutaException;
import gov.sir.core.negocio.acciones.excepciones.ErrorRevisionFinalException;
import gov.sir.core.negocio.acciones.excepciones.ErrorRevisionInicialException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.NotaNoAgregadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CNota_Visibilidad;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleHojaDeRuta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutiva;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoTMP;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.common.Imprimible;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.print.server.PrintJobsProperties;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.util.ExceptionPrinter;

/**
 * @author jfrias
 *
 */
public class ANAntiguoSistema extends SoporteAccionNegocio {
    /**
     * Instancia del ServiceLocator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de la interfaz de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de la intefaz de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;

    /**  */
    public ANAntiguoSistema() throws EventoException{
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

        if (printJobs == null) {
            throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
        }
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
     */
    public EventoRespuesta perform(Evento ev) throws EventoException {
        if (ev instanceof EvnAntiguoSistema){
            EvnAntiguoSistema evento=(EvnAntiguoSistema)ev;
            if (evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_INICIAL_CONFIRMAR)){
                return avanzarRevisionInicial(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_INICIAL_NEGAR)){
                return avanzarRevisionInicialNegar(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_INICIAL_EXISTE)){
				return avanzarRevisionInicialExiste(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_ASOCIAR_MATRICULA)){
                return asociarMatricula(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_ELIMINAR_FOLIO)){
                return eliminarMatricula(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_DESASOCIAR_FOLIO)){
                return desasociarMatricula(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_IMPRIMIR_FOLIO)){
                return imprimirMatricula(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_EDITAR_FOLIO)){
                return editarFolio(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_CONSULTAR_FOLIO)){
                return consultarFolio(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_CONSULTAR_FOLIO_EDICION_ANOTACIONES)){
                return consultarFolioEdicionAnotaciones(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO)){
                editarFolio(evento);
                return consultarFolio(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_CREADO)){
            	avanzarHojaRuta(evento);
			} else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_REANALIZAR)){
                ErrorHojaRutaException exception = new ErrorHojaRutaException("No se pudo avanzar el turno");
                //quitarMarcas(evento);
				crearNotaInformativa(evento);
                return avanzarTurno(evento, exception, CAvanzarTurno.AVANZAR_POP);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_MAS_DOCUMENTOS)||evento.getTipoEvento().equals(EvnAntiguoSistema.HOJA_RUTA_RECHAZAR)){
                ErrorHojaRutaException exception = new ErrorHojaRutaException("No se pudo avanzar el turno");
                //quitarMarcas(evento);
                crearNotaInformativa(evento);
                deshacerFoliosTemporales(evento);
                return avanzarTurno(evento, exception, CAvanzarTurno.AVANZAR_PUSH);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.AVANZAR_CORRECCIONES)) {
            	return avanzarCorrecciones(evento);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.CREACION_FOLIO_APROBAR)){
                ErrorHacerDefinitivoFolioException exception = new ErrorHacerDefinitivoFolioException("No se pudo avanzar el turno");
                hacerFoliosDefinitivos(evento);
                return avanzarTurno(evento, exception, CAvanzarTurno.AVANZAR_NORMAL);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.CREACION_FOLIO_NEGAR)){
                ErrorHacerDefinitivoFolioException exception = new ErrorHacerDefinitivoFolioException("No se pudo avanzar el turno");
                return avanzarTurno(evento, exception, CAvanzarTurno.AVANZAR_POP);
            } else if (evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_FINAL_HOJA_RUTA)){
                ErrorRevisionFinalException exception=new ErrorRevisionFinalException("No se pudo avanzar el turno");
                return avanzarTurno(evento, exception, CAvanzarTurno.AVANZAR_POP);
			} else if (evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_FINAL_CREADO)||evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_FINAL_EXISTE)||evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_FINAL_MAS_DOCS)||evento.getTipoEvento().equals(EvnAntiguoSistema.REVISION_FINAL_RECHAZADO)){
				hacerFoliosDefinitivos(evento);
				return avanzarRevision(evento);
			} else if (evento.getTipoEvento().equals(EvnAntiguoSistema.CANCELAR_ANOTACION)){
				return cancelarAnotacionTemporal(evento);
			} else if (evento.getTipoEvento().equals(EvnFolio.CONSULTA)){
				return consultarFolioCancelacionAnotaciones(evento);
			} else if (evento.getTipoEvento().equals(EvnAntiguoSistema.AVANZAR_MAYOR_VALOR)) {
				return avanzarMayorValor(evento);
			}/* else if(evento.getTipoEvento().equals(EvnEnglobe.AGREGAR_CIUDADANO_ANOTACION)) {
				return validarCiudadanos(evento);
			}*/
            
        } // if
        
        
        
        if( ev instanceof Evn_AntigSistHojaRutaSaveInfo ) {
        	Evn_AntigSistHojaRutaSaveInfo evento;
        	evento = (Evn_AntigSistHojaRutaSaveInfo)ev; 
        	
        	String local_TipoEvento;
        	local_TipoEvento = ev.getTipoEvento();
        	
        	if( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP0__EVENT.equals( local_TipoEvento ) ) {
        		return antigSistHojaRutaSaveInfo_IniciarProceso( evento );
        	}
        	if( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP1__EVENT.equals( local_TipoEvento ) ) {
        		return antigSistHojaRutaSaveInfo_ProcesarFolio( evento );
        	}
        	if( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP2__EVENT.equals( local_TipoEvento ) ) {
        		return antigSistHojaRutaSaveInfo_ProcesarAnotacion( evento );
        	}
        	if( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP3__EVENT.equals( local_TipoEvento ) ) {
        		return antigSistHojaRutaSaveInfo_FinalizarProceso( evento );
        	}
        	
        } // if
        return null;
    }
    
	private EventoRespuesta consultarFolioEdicionAnotaciones(EvnAntiguoSistema evento) throws EventoException {
		try {
			List matriculas = new Vector();
			matriculas.add(evento.getFolio().getIdMatricula());
			
			TurnoPk turnoId = new TurnoPk();
			turnoId.anio = evento.getTurno().getAnio();
			turnoId.idCirculo = evento.getTurno().getIdCirculo();
			turnoId.idProceso = evento.getTurno().getIdProceso();
			turnoId.idTurno = evento.getTurno().getIdTurno();
			
			forseti.bloquearFolios(matriculas, evento.getUsuarioNeg(), turnoId);
		} catch(Throwable th) {
			th.printStackTrace();
			ValidacionParametrosException exception = new ValidacionParametrosException();
			exception.addError(th.getMessage() + " - " + th.getCause().getMessage());
			throw exception;
		}
		
		return consultarFolio(evento);
	}    
    
    
    public ProcesadorEventosNegocioProxy getProcesadorEventosNegocioProxy() {
    	if( null == proxy ) {
    		proxy =  new ProcesadorEventosNegocioProxy();
    	}
    	return proxy; 
    }
    
    private static ProcesadorEventosNegocioProxy proxy = null;
    
    // @decorator
	private EventoRespuesta antigSistHojaRutaSaveInfo_IniciarProceso(Evn_AntigSistHojaRutaSaveInfo evento) throws EventoException {
		
		
		// REVISAR: Se necesita consultar aca el ultimo editado ?
		// (No porque no habria forma de editar uno nuevo.)
		
		
		// --------------------------------------------------
        // wrap ------------------------------------
		EvnResp_AntigSistHojaRutaSaveInfo local_Result;
		local_Result = new EvnResp_AntigSistHojaRutaSaveInfo( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP0__EVENTRESP_OK );

        // set results

       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
       // -----------------------------------------
	}

	private EventoRespuesta antigSistHojaRutaSaveInfo_ProcesarFolio(Evn_AntigSistHojaRutaSaveInfo evento) throws EventoException {
        // unwrap ---------------------------------
        gov.sir.core.negocio.modelo.Usuario            local_ParamUsuarioSir;
        gov.sir.core.negocio.modelo.Turno              local_ParamTurno;
        gov.sir.core.negocio.modelo.Folio              local_ParamFolioEditado;
        org.auriga.core.modelo.transferObjects.Usuario local_ParamUsuarioAuriga;
        

        // get params
        local_ParamUsuarioSir    = evento.getUsuarioSir();
        local_ParamTurno         = evento.getTurno();
        local_ParamUsuarioAuriga = evento.getUsuarioAuriga();
        local_ParamFolioEditado  = evento.getFolioEditado();
        // -----------------------------------------
        
        
        
        // 1: revisar si el folio esta creado o se necesita actualizar.
        // por el momento no esta creado o no hay forma de traerlo de nuevo.
        
        
		
		// -------------------------------------
        
        EvnFolio        local_TmpParameters  = null;
        EvnRespFolio    local_TmpResult      = null;
		
		local_TmpParameters = new EvnFolio( local_ParamUsuarioAuriga, EvnFolio.CREACION_FOLIO_CREADO, local_ParamTurno, (gov.sir.core.negocio.modelo.Fase)null, EvnFolio.CREACION_FOLIO_CREADO, local_ParamFolioEditado , local_ParamUsuarioSir ); 
		
		local_TmpResult = (EvnRespFolio)( getProcesadorEventosNegocioProxy().manejarEvento( local_TmpParameters ) );
		
		
		// ¿se necesita consultar al folio ?
		// 
		// folio = forseti.getFolioByIDSinAnotaciones(fid, null);
		
		
		// --------------------------------------------------
        // wrap ------------------------------------
		EvnResp_AntigSistHojaRutaSaveInfo local_Result;
		local_Result = new EvnResp_AntigSistHojaRutaSaveInfo( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP1__EVENTRESP_OK );

        // set results
		local_Result.setFolioEditado( local_TmpResult.getFolio() );
		local_Result.setTurno( local_TmpResult.getTurno() );
		
       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
	}
	
	// EvnRespConsultaFolio
	
	private EventoRespuesta antigSistHojaRutaSaveInfo_ProcesarAnotacion(Evn_AntigSistHojaRutaSaveInfo evento) throws EventoException {
		
		
		// unwrap ---------------------------------------
		
        gov.sir.core.negocio.modelo.Usuario            local_ParamUsuarioSir;
        gov.sir.core.negocio.modelo.Turno              local_ParamTurno;
        gov.sir.core.negocio.modelo.Folio              local_ParamFolioEditado;
        org.auriga.core.modelo.transferObjects.Usuario local_ParamUsuarioAuriga;
        

        // get params
        local_ParamUsuarioSir    = evento.getUsuarioSir();
        local_ParamTurno         = evento.getTurno();
        local_ParamUsuarioAuriga = evento.getUsuarioAuriga();
        local_ParamFolioEditado  = evento.getFolioEditado();
        
        Vector anotaciones = evento.getAnotaciones();
        // -----------------------------------------
        
        
        
        // 1: revisar si el folio esta creado o se necesita actualizar.
        // por el momento no esta creado o no hay forma de traerlo de nuevo.
		
		// -------------------------------------
        List listaAnotaciones = local_ParamFolioEditado.getAnotaciones();
        
        process: {
        	
        	//Se debe validar que los ciudadanos cumplan con los requerimientos, es decir que los ciudadanos que se van
        	//a ingresar no esten en temporal
        	        	
        	Iterator itt = listaAnotaciones.iterator();
			while(itt.hasNext()){
				Anotacion anota = (Anotacion) itt.next();
				List anotacionCiudadanos = anota.getAnotacionesCiudadanos();
				Iterator it = anotacionCiudadanos.iterator();
				while(it.hasNext()){
					AnotacionCiudadano anotaCiudadano = (AnotacionCiudadano)it.next();
					Ciudadano ciu = anotaCiudadano.getCiudadano();
					CiudadanoTMP ciudTemp = null;
					try {
						ciudTemp = forseti.getCiudadanoTmpByDocumento(ciu.getTipoDoc(), ciu.getDocumento(), ciu.getIdCirculo());	
					} catch (Throwable ee) {}
					
					if (ciudTemp!=null) {
						if (local_ParamTurno!=null) {
							if (local_ParamTurno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES) ) {
								if (ciudTemp.getNumeroRadicacion()!=null && !ciudTemp.getNumeroRadicacion().equals(local_ParamTurno.getIdWorkflow())) {
									throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
								} 
							} else {
								if (ciudTemp.getNumeroRadicacion()!=null) {
									throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
								} else {
									throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
								}
							}
						}
					}					
				}
				
					List lstAnotaHijos = new ArrayList();
					List lstAnotaPadres = new ArrayList();
										
	            	AnotacionTMP anotacionTMP = new AnotacionTMP();
	            	
					lstAnotaHijos = anota.getAnotacionesHijos();
					lstAnotaPadres = anota.getAnotacionesPadre();
					
					Iterator itAH = lstAnotaHijos.iterator();	
					while(itAH.hasNext()){
						FolioDerivado folioDerivado = (FolioDerivado)itAH.next();					
						
						Folio folioAux = null;
						try {
							folioAux = forseti.getFolioByMatricula(folioDerivado.getIdMatricula1());
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						if(folioAux != null){
							if(folioAux.isDefinitivo()){
								FolioPk fid= new FolioPk();
								fid.idMatricula = folioDerivado.getIdMatricula1(); 
								int lstAnotatemp = 0;
								try {
									lstAnotatemp = forseti.getAnotacionesFolioTMPCount(fid);
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(lstAnotatemp == 0){
									List lstAnotaFolioHijo = new ArrayList();
									try {
										lstAnotaFolioHijo = forseti.getAnotacionesFolio(fid);
									} catch (Throwable e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									Iterator itAFH = lstAnotaFolioHijo.iterator();
									boolean swEncontro = false;
									while(itAFH.hasNext() && !swEncontro){
										Anotacion anotaFH = (Anotacion) itAFH.next();
										if(anotaFH != null && anotaFH.getIdAnotacion() != null && 
											anotaFH.getIdAnotacion().equals(folioDerivado.getIdAnotacion1())){
											if(anotaFH.isTemporal()){
												throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion1() + " No es Definitiva ");
											}
											swEncontro = true;
										}
									}
									if(!swEncontro){
										throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion1() + " No pertenece a La Matricula " + folioDerivado.getIdMatricula1());
									}
								}
								else
									throw new EventoException("La Matricula " + fid.idMatricula + " - " + " contiene anotaciones temporales");
							}else{
								throw new EventoException("La Matricula " + folioDerivado.getIdMatricula1() + " - " + " No es Definitivo");
							}
						}else{
							throw new EventoException("La Matricula " + folioDerivado.getIdMatricula1() + " - " + " No EXISTE");
						}
					}
					
					
					Iterator itAP = lstAnotaPadres.iterator();	
					while(itAP.hasNext()){
						FolioDerivado folioDerivado = (FolioDerivado)itAP.next();
						
						Folio folioAux = null;
						try {
							folioAux = forseti.getFolioByMatricula(folioDerivado.getIdMatricula());
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(folioAux != null){
							if(folioAux.isDefinitivo()){
								FolioPk fid= new FolioPk();
								fid.idMatricula = folioDerivado.getIdMatricula(); 
								List lstAnotaFolioPadre = new ArrayList();
								try {
									lstAnotaFolioPadre = forseti.getAnotacionesFolio(fid);
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}								
								Iterator itAFH = lstAnotaFolioPadre.iterator();
								boolean swEncontro = false;
								int lstAnotatemp = 0;
								try {
									lstAnotatemp = forseti.getAnotacionesFolioTMPCount(fid);
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(lstAnotatemp == 0){
									while(itAFH.hasNext() && !swEncontro){
										Anotacion anotaFH = (Anotacion) itAFH.next();
										if(anotaFH != null && anotaFH.getIdAnotacion() != null && 
											anotaFH.getIdAnotacion().equals(folioDerivado.getIdAnotacion())){
											if(anotaFH.isTemporal()){
												throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion() + " No es Definitiva ");
											}
											swEncontro = true;
										}
									}
									if(!swEncontro){
										throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion() + " No pertenece a La Matricula " + folioDerivado.getIdMatricula1());
									}
								}else
									throw new EventoException("La Matricula " + fid.idMatricula + " - " + " Se Encuantra bloqueada");
							}else
								throw new EventoException("La Matricula " + folioDerivado.getIdMatricula() + " - " + " No es Definitivo");
						}else
							throw new EventoException("La Matricula " + folioDerivado.getIdMatricula() + " - " + " No EXISTE");
					}
				
			}
        	
        	// realizar actualizacion
        	
	        try {
	            //forseti.updateFolio( local_ParamFolioEditado, local_ParamUsuarioSir, null, false );
	        	forseti.updateFolioFD( local_ParamFolioEditado, local_ParamUsuarioSir, null, false, new ArrayList(), new ArrayList(), null , null, null);
	        	
	        	anotaciones.removeAllElements();
				List lstAnotaComplex = forseti.getAnotacionesFolioTMP(new FolioPk(local_ParamFolioEditado.getIdMatricula()));
				for(Iterator iter = lstAnotaComplex.iterator() ; iter.hasNext() ; ){
		        	Anotacion anota = (Anotacion)iter.next();
		        	anotaciones.add(anota);
				}
	        } 
	        catch (ForsetiException e) {
	            ExceptionPrinter printer = new ExceptionPrinter(e);
	            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo editar " + printer.toString());
	            throw new ErrorHojaRutaException(e.getMessage());
	        } 
	        catch (Throwable e){
	            ExceptionPrinter printer = new ExceptionPrinter(e);
	            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
	            throw new EventoException("No se pudo editar el folio");
	        }
        	
	        
	        // obtener las anotaciones actuales
	        
	        
	        
	        
        	
        } // :process
        
        
        /** Se adiciona la anotacion nueva en la lista de anotacioens agregadas del folio */
        /*for(Iterator iter = listaAnotaciones.iterator() ; iter.hasNext() ; ){
        	Anotacion anota = (Anotacion) iter.next();
        	anotaciones.add(anota);
        }*/
		// -------------------------------------
        // se usa la misma consulta que al editar los folios
        
        EvnAntiguoSistema        local_TmpParameters  = null;
        EvnRespAntiguoSistema    local_TmpResult      = null;
		
		local_TmpParameters = new EvnAntiguoSistema( local_ParamUsuarioAuriga, EvnAntiguoSistema.HOJA_RUTA_CONSULTAR_FOLIO_EDICION_ANOTACIONES, local_ParamFolioEditado );
		local_TmpParameters.setTurno( local_ParamTurno );
		local_TmpParameters.setUsuarioNeg( local_ParamUsuarioSir );
		
		// llamar al orq.
		//   como es la misma accion de negocio, se delega;
		// local_TmpResult = (EvnRespFolio)( getProcesadorEventosNegocioProxy().manejarEvento( local_TmpParameters ) );
		// local_TmpResult = (EvnRespFolio)( this.procesar( local_TmpParameters ) );
		local_TmpResult = (EvnRespAntiguoSistema)( this.consultarFolioEdicionAnotaciones( local_TmpParameters ) );
        
		// --------------------------------------------------
		
		
		// solucion a el problema de la sanotaciones por que estas se estan guardando el session sin parametros impo para 
		// la edicion  de las mismas
		Folio folioComplex = null;
		if(local_TmpResult != null && local_TmpResult.getPayload() != null){
			folioComplex = (Folio)local_TmpResult.getPayload();
			if(folioComplex.getAnotaciones() != null && !folioComplex.getAnotaciones().isEmpty()){
				//cmparacion de anotaciones
				List lstAnotaComplex = folioComplex.getAnotaciones();
				
				for(int j=0; j<anotaciones.size(); j++){
					Anotacion anota = (Anotacion)anotaciones.get(j);
					if(anota != null && anota.getIdAnotacion() == null || anota.getIdAnotacion().equals("")){
						for(int i = 0; i<lstAnotaComplex.size(); i++){
							Anotacion anotaComplex = (Anotacion)lstAnotaComplex.get(i);
							if(anota != null && anota.getOrden()!= null && anotaComplex != null &&
								anotaComplex.getOrden() != null && anota.getOrden().equals(anotaComplex.getOrden())){
								anota.setIdAnotacion(anotaComplex.getIdAnotacion());
							}
						}
					}
				}
			}
		}
		
		
		// --------------------------------------------------
        // wrap ------------------------------------
		EvnResp_AntigSistHojaRutaSaveInfo local_Result;
		local_Result = new EvnResp_AntigSistHojaRutaSaveInfo( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP2__EVENTRESP_OK );
		
        // set results
		// se obtiene el folio con las anotaciones
		local_Result.setFolioEditado( (Folio)local_TmpResult.getPayload() );
		
		local_Result.setAnotaciones(anotaciones);

		if (evento.isCrearFolio()){
			local_Result.setCrearFolio(true);
		}
       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
		
		
		// TODO Auto-generated method stub
		//return null;
	}
	
	private EventoRespuesta antigSistHojaRutaSaveInfo_FinalizarProceso(Evn_AntigSistHojaRutaSaveInfo evento) throws EventoException {

		// --------------------------------------------------
        // wrap ------------------------------------
		EvnResp_AntigSistHojaRutaSaveInfo local_Result;
		local_Result = new EvnResp_AntigSistHojaRutaSaveInfo( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP3__EVENTRESP_OK );

        // set results
		
       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
		
		
		// TODO Auto-generated method stub
		// return null;
	}	
	
	private EventoRespuesta avanzarMayorValor(EvnAntiguoSistema evento) throws EventoException {
		
		Turno turno = evento.getTurno();
	    Liquidacion liq = evento.getLiquidacion();
	    Fase fase = evento.getFase();

		Hashtable parametros = new Hashtable();

		//inicializar tabla hashing
		parametros.put(Processor.RESULT, evento.getRespuestaWF());
		parametros.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
			Hashtable tablaAvance = new Hashtable(2);
			tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		} catch(Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		try {
			//se añade la liquidacion al turno
			SolicitudPk solID = new SolicitudPk();
			solID.idSolicitud = turno.getSolicitud().getIdSolicitud();
		    hermod.addLiquidacionToSolicitudCertificado(solID, (LiquidacionTurnoCertificado)liq);
		    //se avanza el turno
		    //hermod.avanzarTurno(turno, fase, parametros, CAvanzarTurno.AVANZAR_PUSH);
		    avanzarTurno(evento,null,CAvanzarTurno.AVANZAR_PUSH);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANAntiguoSistema.class,"No se pudo avanzar el turno:"+ep.toString());
			throw new AvanzarCalificacionException("No se pudo avanzar el turno. ",e1);
		}

		return null;
	}

	private EventoRespuesta avanzarCorrecciones(EvnAntiguoSistema evento) throws EventoException {
		
		// Primero debo obtener los datos del turno para el que quiero crear un hijo en
		// correcciones.
		Turno turno = evento.getTurno();
		
		// Creo una nueva solicitud con una liquidación con sus pagos y valores en 0.
		//Pago pagoNuevo = new Pago();
		//pagoNuevo.setUsuario(evento.getUsuarioNeg());
		//pagoNuevo.setFecha(new Date());
		
		//Liquidacion liquidacionNueva = new LiquidacionTurnoCorreccion();
		//liquidacionNueva.setPago(pagoNuevo);
		//pagoNuevo.setLiquidacion(liquidacionNueva);
		
		//Solicitud solicitudNueva = new SolicitudCorreccion();
		//solicitudNueva.addLiquidacion(liquidacionNueva);
		
		// Se copian los datos de antiguo sistema y los folios asociados de la solicitud del
		// folio padre a la nueva solicitud
		//Solicitud solicitud = turno.getSolicitud();
		//solicitudNueva.setSolicitudFolios(solicitud.getSolicitudFolios());
		//solicitudNueva.setDatosAntiguoSistema(solicitud.getDatosAntiguoSistema());
		
		// Se crea el nuevo turno
		Turno turnoNuevo;
		
		try {
			Hashtable parametros = new Hashtable();
			parametros.put(Processor.RESULT,"CORRECION");
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(),parametros,turno);
			
			turnoNuevo = hermod.crearTurnoDependiente(turno, evento.getUsuarioNeg(), Long.parseLong(CProceso.PROCESO_CORRECCION));
			/*Fase fase = hermod.getFase(turnoNuevo.getIdFase());
			Hashtable tabla = new Hashtable();
			tabla.put(Processor.RESULT, CRespuesta.CONFIRMAR);
	        tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
			hermod.avanzarTurno(turnoNuevo, fase, tabla, CAvanzarTurno.AVANZAR_NORMAL);*/
		} catch(HermodException t){
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para crear Turno de Correciones");
		} catch(Throwable th) {
			throw new ValidacionParametrosException(th.getMessage());
		}
		
		EvnRespAntiguoSistema respuesta = new EvnRespAntiguoSistema(turnoNuevo, 
				EvnRespAntiguoSistema.AVANZAR_CORRECCIONES);
		respuesta.setTurno(turno);
		
		return respuesta;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarFolioCancelacionAnotaciones(EvnAntiguoSistema evento) throws EventoException {
		Folio folio = evento.getFolio();
		Usuario usuarioNeg=evento.getUsuarioNeg();
		FolioPk id = new FolioPk();
		id.idMatricula = folio.getIdMatricula();
		Circulo circulo = evento.getCirculo();
		TurnoPk idT = new TurnoPk();
		idT.anio = evento.getTurno().getAnio();
		idT.idCirculo = evento.getTurno().getIdCirculo();
		idT.idProceso = evento.getTurno().getIdProceso();
		idT.idTurno = evento.getTurno().getIdTurno();
		String anho = "";
		Folio nFolio = new Folio();
		try {
			anho = hermod.getValor(COPLookupTypes.PLAZO_CANCELACION_ANOTACIONES_ANT_SIS,circulo.getIdCirculo());
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		try {
			if (folio != null && folio.getIdMatricula() != null) {
				nFolio = this.forseti.getFolioByMatricula(folio.getIdMatricula());
			}
			List matriculas = new ArrayList();
			matriculas.add(nFolio.getIdMatricula());
			forseti.bloquearFolios(matriculas,usuarioNeg,idT);
						
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new ConsultaFolioException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			if(nFolio == null){
				Log.getInstance().error(ANAntiguoSistema.class,"El folio que trato de consultar no pudo ser obtenido " + printer.toString());
			} else{
				Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());	
			}
			
			throw new EventoException(e.getMessage(), e);
		}
		
		
		
		return new EvnRespAntiguoSistema(nFolio,EvnRespAntiguoSistema.CONSULTA_FOLIO,anho);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta cancelarAnotacionTemporal(EvnAntiguoSistema evento) throws EventoException {
		ForsetiServiceInterface forseti=null;
		HermodServiceInterface hermod=null;

		Folio devuelve=null;
		List listaATemp=null;
		try {
			forseti= (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"No se encontró el servicio forseti:"+ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio forseti:"+ep.toString(),e);
		}

		Usuario usuarioNeg=evento.getUsuarioNeg();
		Folio nuevo=new Folio();
		Turno turno = evento.getTurno();


		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
		Anotacion anotacion= evento.getAnotacion();
		nuevo.addAnotacione(anotacion);
		try {
			if(forseti.updateFolio(nuevo,usuarioNeg, null, false)){
			FolioPk id=new FolioPk();
			id.idMatricula=evento.getFolio().getIdMatricula();
			devuelve = forseti.getFolioByIDSinAnotaciones(id);
			listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);

			if(turno!=null){
				TurnoPk idTurno = new TurnoPk();
				idTurno.anio = turno.getAnio();
				idTurno.idCirculo = turno.getIdCirculo();
				idTurno.idProceso = turno.getIdProceso();
				idTurno.idTurno = turno.getIdTurno();

				turno = hermod.getTurno(idTurno);
			}
		}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANAntiguoSistema.class,"No se pudo cancelar la anotacion:"+ep.toString());
			throw new CancelarAnotacionNoEfectuadaException("No se pudo cancelar la anotacion",e1);
		}


		//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
		try {
			if(nuevo!=null && nuevo.getIdMatricula()!=null){

				FolioPk id=new FolioPk();
				id.idMatricula=nuevo.getIdMatricula();

				nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);

				boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

				long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

				List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
				List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);

				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
				List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg, true);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

				List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg);
				List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg);

				/*EvnRespCalificacion evnResp = new EvnRespCalificacion (nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp);
				evnResp.setTurno(turno);
				return evnResp;*/
				return null;
			}
			throw new EventoException();
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}


			//return new EvnRespCalificacion(devuelve, listaATemp);
	}
	
	/**
	 * @param evento
	 * @return
	 */
	/*private EventoRespuesta validarCiudadanos(EvnAntiguoSistema evento) throws EventoException {
	
		List anotacionCiudadanos = new ArrayList();
		anotacionCiudadanos = evento.getAnotacionCiudadanos();
		Turno turno = evento.getTurno();
		
		try {
			Iterator itt = anotacionCiudadanos.iterator();
			while(itt.hasNext()){
				AnotacionCiudadano anotaCiudadano = (AnotacionCiudadano)itt.next();
				Ciudadano ciu = anotaCiudadano.getCiudadano();
				CiudadanoTMP ciudTemp = null;
				try {
					ciudTemp = forseti.getCiudadanoTmpByDocumento(ciu.getTipoDoc(), ciu.getDocumento(), ciu.getIdCirculo());	
				} catch (Throwable ee) {}
				
				if (ciudTemp!=null) {
					if (turno!=null) {
						if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES) ) {
							if (ciudTemp.getNumeroRadicacion()!=null && !ciudTemp.getNumeroRadicacion().equals(turno.getIdWorkflow())) {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
							} 
						} else {
							if (ciudTemp.getNumeroRadicacion()!=null) {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
							} else {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
							}
						}
					}
				}
				
			}
			
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANAntiguoSistema.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);

		}
		
		EvnRespAntiguoSistema evn = new EvnRespAntiguoSistema(EvnRespAntiguoSistema.VOLVER_AGREGAR_CIUDADANOS);
		evn.setTipoEvento(EvnRespSegregacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setListaCompletaCiudadanos(evento.getListaCompletaCiudadanos());
		return evn;
	}*/


    /**
     * @param evento
     */
    private void hacerFoliosDefinitivos(EvnAntiguoSistema evento) throws EventoException{
        Turno turno = evento.getTurno();
        gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNeg();
        
        Solicitud sol = turno.getSolicitud();
        TurnoPk id = new TurnoPk();
        id.anio = turno.getAnio();
        id.idCirculo = turno.getIdCirculo();
        id.idProceso = turno.getIdProceso();
        id.idTurno = turno.getIdTurno();
        
        Iterator itSolFolios = sol.getSolicitudFolios().iterator();
        while (itSolFolios.hasNext()) {
			SolicitudFolio sf = (SolicitudFolio) itSolFolios.next();
            Folio folio = sf.getFolio();
            try {
            	if(sf.isRecienCreadoAntiguoSistema()){
            		FolioPk fid = new FolioPk();
            		fid.idMatricula = folio.getIdMatricula();
					forseti.delegarBloqueoFolio(id, usuario, fid);
					forseti.hacerDefinitivoFolio(folio, evento.getUsuarioNeg(), id, true);
            	}
            } catch (ForsetiException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANAntiguoSistema.class,"No se pudo hacer definitivo " + printer.toString());
                throw new ErrorHacerDefinitivoFolioException(e);
            } catch (Throwable e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e.getMessage(), e);
            }
        }
    }
    
    /**
     * @param evento
     */
    private void deshacerFoliosTemporales(EvnAntiguoSistema evento) throws EventoException{
        Turno turno = evento.getTurno();
        gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNeg();
        
        Solicitud sol = turno.getSolicitud();
        TurnoPk id = new TurnoPk();
        id.anio = turno.getAnio();
        id.idCirculo = turno.getIdCirculo();
        id.idProceso = turno.getIdProceso();
        id.idTurno = turno.getIdTurno();
        
		for (Iterator iter = sol.getSolicitudFolios().iterator(); iter.hasNext();) 
		{
			SolicitudFolio element = (SolicitudFolio) iter.next();
			
			if (!element.getFolio().isDefinitivo() && element.isRecienCreadoAntiguoSistema())
			{
				List matriculas = new ArrayList();
				matriculas.add(element.getFolio().getIdMatricula());
				FolioPk folioid = new FolioPk();
				folioid.idMatricula = element.getFolio().getIdMatricula();
				try {
					forseti.delegarBloqueoFolio(id, evento.getUsuarioNeg(), folioid );
					forseti.deleteFolio(folioid, evento.getUsuarioNeg());
				} catch (ForsetiException e) {
	                ExceptionPrinter printer = new ExceptionPrinter(e);
	                Log.getInstance().error(ANAntiguoSistema.class,"No se pudo deshacer temporal " + printer.toString());
	                throw new ErrorHacerDefinitivoFolioException(e);
	            } catch (Throwable e) {
	                ExceptionPrinter printer = new ExceptionPrinter(e);
	                Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
	                throw new EventoException(e.getMessage(), e);
	            }
			}
		}
    }

    /**
     * @param evento
     */
    private void crearNotaInformativa(EvnAntiguoSistema evento) throws EventoException{

        Turno turno=evento.getTurno();
        ProcesoPk procesoId = new ProcesoPk();
        Long longProceso = new Long(CProceso.PROCESO_ANTIGUO_SISTEMA);
        procesoId.idProceso = longProceso.longValue();
        TipoNota tipoNota = null;
        List listaInformativas;
        try {
            listaInformativas = hermod.getTiposNotaProceso(procesoId, CFase.ANT_HOJA_RUTA);
            if (listaInformativas!=null){
                for (int i=0; i<listaInformativas.size(); i++){
                    tipoNota = (TipoNota) listaInformativas.get(i);
                    if (tipoNota.getNombre().equals(CNota.INFORMATIVA_HOJA_RUTA)){
                        i = listaInformativas.size()+1;
                    }
                }
            }else{
                throw new ErrorHojaRutaException("No se encontraron notas informativas");
            }
        } catch (HermodException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo crear la nota informativa " + printer.toString());
            throw new ErrorHojaRutaException(e.getMessage());
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("No se pudo crear la nota informativa");
        }
        
        if (tipoNota==null){
            throw new ErrorHojaRutaException("No se encontró el tipo de nota informativa");
        }
        Nota nuevaNota = new Nota();
        nuevaNota.setTiponota(tipoNota);
        nuevaNota.setDescripcion(evento.getDescripcionNota());
        nuevaNota.setUsuario(evento.getUsuarioNeg());
        nuevaNota.setIdCirculo(evento.getCirculo().getIdCirculo());
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        nuevaNota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
        nuevaNota.setIdFase(evento.getFase().getID());
        nuevaNota.setIdProceso(longProceso.longValue());
        nuevaNota.setTime(calendar.getTime());
        nuevaNota.setTurno(evento.getTurno());
        
        TurnoPk turnoId = new TurnoPk();
        turnoId.anio = turno.getAnio();
        turnoId.idCirculo = turno.getIdCirculo();
        turnoId.idProceso = turno.getIdProceso();
        turnoId.idTurno = turno.getIdTurno();
                                
        try {
            hermod.addNotaToTurno(nuevaNota,turnoId);
        }catch (HermodException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo adicionar la nota informativa " + printer.toString());
            throw new ErrorHojaRutaException(e.getMessage());
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("No se pudo editar el folio");
        }
        turno.addNota(nuevaNota);
    
    }

    /**
     * @param evento
     */
    private void quitarMarcas(EvnAntiguoSistema evento) throws EventoException{
        try {
            TurnoPk tid=new TurnoPk();
            tid.anio=evento.getTurno().getAnio();
            tid.idCirculo=evento.getTurno().getIdCirculo();
            tid.idProceso=evento.getTurno().getIdProceso();
            tid.idTurno=evento.getTurno().getIdTurno();
            hermod.desmarcarFoliosInTurno(tid);
            hermod.desmarcarFoliosRecienCreadoASInTurno(tid);
        } catch (HermodException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo editar " + printer.toString());
            throw new ErrorHojaRutaException(e.getMessage());
        } catch (Throwable e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("No se pudo editar el folio");
        }
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta consultarFolio(EvnAntiguoSistema evento) throws EventoException{
        FolioPk fid=new FolioPk();
        fid.idMatricula=evento.getFolio().getIdMatricula();
        Folio folio=null;
        try {
            folio = forseti.getFolioByID(fid,evento.getUsuarioNeg());
    		if(folio.getAnotaciones() == null || folio.getAnotaciones().isEmpty()){
    			FolioPk folioPk = new FolioPk ();
    			folioPk.idMatricula = folio.getIdMatricula();
    			folio.setAnotaciones(forseti.getAnotacionesFolioTMPFD(folioPk));
    		}
        } catch (ForsetiException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo editar " + printer.toString());
            throw new ErrorHojaRutaException(e.getMessage());
        } catch (Throwable e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("No se pudo editar el folio");
        }
        return new EvnRespAntiguoSistema(folio,EvnRespAntiguoSistema.CONSULTAR_MATRICULA);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta editarFolio(EvnAntiguoSistema evento) throws EventoException{
        
    	
    	//Se debe hacer la validaciones de los ciudadanos
    	Vector anotaciones = evento.getAnotaciones();
    	if(anotaciones==null) anotaciones = new Vector();
    	Folio delta = evento.getFolio();
    	List listaAnotaciones = delta.getAnotaciones();
    	Turno turno = evento.getTurno();    	
        try {
        	
        	List lstAnotaComplex = forseti.getAnotacionesFolioTMP(new FolioPk(delta.getIdMatricula()));			
			for(int j=0; j<listaAnotaciones.size(); j++){
				Anotacion anota = (Anotacion)listaAnotaciones.get(j);
				if(anota != null && anota.getIdAnotacion() == null || anota.getIdAnotacion().equals("")){
					for(int i = 0; i<lstAnotaComplex.size(); i++){
						Anotacion anotaComplex = (Anotacion)lstAnotaComplex.get(i);
						if(anota != null && anota.getOrden()!= null && anotaComplex != null &&
							anotaComplex.getOrden() != null && anota.getOrden().equals(anotaComplex.getOrden()) && anotaComplex.isToDelete() != false){
							anota.setIdAnotacion(anotaComplex.getIdAnotacion());
						}
					}
				}
			}
        	
        	Iterator itt = listaAnotaciones.iterator();
			while(itt.hasNext()){
				Anotacion anota = (Anotacion) itt.next();
				List anotacionCiudadanos = anota.getAnotacionesCiudadanos();
				Iterator it = anotacionCiudadanos.iterator();
				while(it.hasNext()){
					AnotacionCiudadano anotaCiudadano = (AnotacionCiudadano)it.next();
					Ciudadano ciu = anotaCiudadano.getCiudadano();
					CiudadanoTMP ciudTemp = null;
					try {
						ciudTemp = forseti.getCiudadanoTmpByDocumento(ciu.getTipoDoc(), ciu.getDocumento(), ciu.getIdCirculo());	
					} catch (Throwable ee) {}
					
					if (ciudTemp!=null) {
						if (turno!=null) {
							if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES) ) {
								if (ciudTemp.getNumeroRadicacion()!=null && !ciudTemp.getNumeroRadicacion().equals(turno.getIdWorkflow())) {
									throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
								} 
							} else {
								if (ciudTemp.getNumeroRadicacion()!=null) {
									throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
								} else {
									throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
								}
							}
						}
					}
					
				}
				
				
				if(evento.getTipoEvento().equals("HOJA_RUTA_EDITAR_FOLIO") || (evento.getAnotacionUpdate() != null && evento.getMatruculaUpdate() != null )){
					List lstAnotaHijos = new ArrayList();
					List lstAnotaPadres = new ArrayList();
					
					AnotacionTMPPk anotacionTMPPk = new AnotacionTMPPk();       
	            	anotacionTMPPk.idAnotacionTmp = evento.getAnotacionUpdate();
	            	anotacionTMPPk.idMatricula = evento.getMatruculaUpdate();
	            	AnotacionTMP anotacionTMP = new AnotacionTMP();
	            	
					if(evento.getTipoEvento().equals("HOJA_RUTA_EDITAR_FOLIO")){
						lstAnotaHijos = anota.getAnotacionesHijos();
						lstAnotaPadres = anota.getAnotacionesPadre();
					}else{
						lstAnotaHijos = evento.getLstAnotaFolioHijo();
						lstAnotaPadres = evento.getLstAnotaFolioPadre();
						anotacionTMP = forseti.getAnotacionTMPByIDSinPersitence(anotacionTMPPk);
					}
					
					Iterator itAH = lstAnotaHijos.iterator();	
					while(itAH.hasNext()){
						FolioDerivado folioDerivado = (FolioDerivado)itAH.next();
					
						boolean entraAValidar = true;
						if(anotacionTMP != null && anotacionTMP.getAnotacionesHijosTMPs() != null 
							&& anotacionTMP.getAnotacionesHijosTMPs().size() > 0){
							List lstAnotafloioHijo = anotacionTMP.getAnotacionesHijosTMPs();
							Iterator iteratorAnotafolioHijo = lstAnotafloioHijo.iterator(); 
							while(iteratorAnotafolioHijo.hasNext()){
								FolioDerivadoTMP folioDerivadoTMP = (FolioDerivadoTMP)iteratorAnotafolioHijo.next();
								if(folioDerivadoTMP.getIdMatricula1().equals(folioDerivado.getIdMatricula1()) &&
									folioDerivadoTMP.getIdAnotacion1Tmp().equals(folioDerivado.getIdAnotacion1())){
									entraAValidar = false;
								}
							}
						}
						
						if(entraAValidar){	
							Folio folioAux = forseti.getFolioByMatricula(folioDerivado.getIdMatricula1());	
							if(folioAux != null){
								if(folioAux.isDefinitivo()){
									FolioPk fid= new FolioPk();
									fid.idMatricula = folioDerivado.getIdMatricula1(); 
									int lstAnotatemp = forseti.getAnotacionesFolioTMPCount(fid);
									if(lstAnotatemp == 0){
										List lstAnotaFolioHijo = forseti.getAnotacionesFolio(fid);
										
										Iterator itAFH = lstAnotaFolioHijo.iterator();
										boolean swEncontro = false;
										while(itAFH.hasNext() && !swEncontro){
											Anotacion anotaFH = (Anotacion) itAFH.next();
											if(anotaFH != null && anotaFH.getIdAnotacion() != null && 
												anotaFH.getIdAnotacion().equals(folioDerivado.getIdAnotacion1())){
												if(anotaFH.isTemporal()){
													throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion1() + " No es Definitiva ");
												}
												swEncontro = true;
											}
										}
										if(!swEncontro){
											throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion1() + " No pertenece a La Matricula " + folioDerivado.getIdMatricula1());
										}
									}
									else
										throw new EventoException("La Matricula " + fid.idMatricula + " - " + " contiene anotaciones temporales");
								}else{
									throw new EventoException("La Matricula " + folioDerivado.getIdMatricula1() + " - " + " No es Definitivo");
								}
							}else{
								throw new EventoException("La Matricula " + folioDerivado.getIdMatricula1() + " - " + " No EXISTE");
							}
						}
					}
					
					
					Iterator itAP = lstAnotaPadres.iterator();	
					while(itAP.hasNext()){
						FolioDerivado folioDerivado = (FolioDerivado)itAP.next();
						boolean entraAValidar = true;
						if(anotacionTMP != null && anotacionTMP.getAnotacionesPadreTMPs() != null 
							&& anotacionTMP.getAnotacionesPadreTMPs().size() > 0){
							List lstAnotaflolioPadre = anotacionTMP.getAnotacionesPadreTMPs();
							Iterator iteratorAnotafolioPadre = lstAnotaflolioPadre.iterator(); 
							while(iteratorAnotafolioPadre.hasNext()){
								FolioDerivadoTMP folioDerivadoTMP = (FolioDerivadoTMP)iteratorAnotafolioPadre.next();
								if(folioDerivadoTMP.getIdMatricula().equals(folioDerivado.getIdMatricula()) &&
									folioDerivadoTMP.getIdAnotacionTmp().equals(folioDerivado.getIdAnotacion())){
									entraAValidar = false;
								}
							}
						}
						
						if(entraAValidar){	
							Folio folioAux = forseti.getFolioByMatricula(folioDerivado.getIdMatricula());
							if(folioAux != null){
								if(folioAux.isDefinitivo()){
									FolioPk fid= new FolioPk();
									fid.idMatricula = folioDerivado.getIdMatricula(); 
									List lstAnotaFolioPadre = forseti.getAnotacionesFolio(fid);								
									Iterator itAFH = lstAnotaFolioPadre.iterator();
									boolean swEncontro = false;
									int lstAnotatemp = forseti.getAnotacionesFolioTMPCount(fid);
									if(lstAnotatemp == 0){
										while(itAFH.hasNext() && !swEncontro){
											Anotacion anotaFH = (Anotacion) itAFH.next();
											if(anotaFH != null && anotaFH.getIdAnotacion() != null && 
												anotaFH.getIdAnotacion().equals(folioDerivado.getIdAnotacion())){
												if(anotaFH.isTemporal()){
													throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion() + " No es Definitiva ");
												}
												swEncontro = true;
											}
										}
										if(!swEncontro){
											throw new EventoException("La Anotacion " + folioDerivado.getIdAnotacion() + " No pertenece a La Matricula " + folioDerivado.getIdMatricula1());
										}
									}else
										throw new EventoException("La Matricula " + fid.idMatricula + " - " + " Se Encuantra bloqueada");
								}else
									throw new EventoException("La Matricula " + folioDerivado.getIdMatricula() + " - " + " No es Definitivo");
							}else
								throw new EventoException("La Matricula " + folioDerivado.getIdMatricula() + " - " + " No EXISTE");
						}
					}
				}
			}
			if(evento.getTipoEvento().equals("HOJA_RUTA_EDITAR_FOLIO") || (evento.getLstAnotaFolioHijoRemove() != null && !evento.getLstAnotaFolioHijoRemove().isEmpty()) 
				|| (evento.getAnotacionUpdate() != null && evento.getMatruculaUpdate() != null ))
				if(evento.getMatruculaUpdate() != null){
					List lstAnotaFolioHijo = new ArrayList();
					List lstAnotaFolioPadre = new ArrayList();
					if(evento.getLstAnotaFolioHijo() != null)
						lstAnotaFolioHijo = evento.getLstAnotaFolioHijo();
					if(evento.getLstAnotaFolioPadre() != null)
						lstAnotaFolioPadre = evento.getLstAnotaFolioPadre();
					forseti.updateFolioFD( evento.getFolio(), evento.getUsuarioNeg(), null, false,lstAnotaFolioHijo,lstAnotaFolioPadre,evento.getAnotacionUpdate(), evento.getMatruculaUpdate(),evento.getLstAnotaFolioHijoRemove());
				}
				else
					forseti.updateFolioFD( evento.getFolio(), evento.getUsuarioNeg(), null, false, new ArrayList(), new ArrayList(), null , null,evento.getLstAnotaFolioHijoRemove());
					
			else 
				forseti.updateFolio( evento.getFolio(), evento.getUsuarioNeg(), null, false);
			 
			
			
			anotaciones.removeAllElements();
			lstAnotaComplex = forseti.getAnotacionesFolioTMP(new FolioPk(delta.getIdMatricula()));
			for(Iterator iter = lstAnotaComplex.iterator() ; iter.hasNext() ; ){
	        	Anotacion anota = (Anotacion)iter.next();
	        	anotaciones.add(anota);
			}
			
        } catch (ForsetiException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo editar " + printer.toString());
            throw new ErrorHojaRutaException(e.getMessage());
        } catch (Throwable e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("No se pudo editar el folio: " + e.getMessage());
        }
        /*for(Iterator iter = listaAnotaciones.iterator() ; iter.hasNext() ; ){
        	Anotacion anota = (Anotacion)iter.next();
        	anotaciones.add(anota);
        }*/
        /*for(int j=0; j<anotaciones.size(); j++){
			Anotacion anota = (Anotacion)anotaciones.get(j);
			if(anota != null && anota.getIdAnotacion() == null || anota.getIdAnotacion().equals("")){
				for(int i = 0; i<lstAnotaComplex.size(); i++){
					Anotacion anotaComplex = (Anotacion)lstAnotaComplex.get(i);
					if(anota != null && anota.getOrden()!= null && anotaComplex != null &&
						anotaComplex.getOrden() != null && anota.getOrden().equals(anotaComplex.getOrden())){						
						anota.setIdAnotacion(anotaComplex.getIdAnotacion());
						anota.setIdMatricula(anotaComplex.getIdMatricula());
					}
				}
			}
		}*/
        
        
        
        EvnRespAntiguoSistema evn = new EvnRespAntiguoSistema(turno,EvnRespAntiguoSistema.VOLVER_AGREGAR_CIUDADANOS);
        evn.setAnotaciones(anotaciones);
        return evn;
    }

    /**
     * @param evento
     * @return
     * @throws EventoException
     */
    private EventoRespuesta imprimirMatricula(EvnAntiguoSistema evento) throws EventoException{
        SolicitudFolio solFol=evento.getSolicitudFolio();
        Folio folio = null;
        
        try{
            FolioPk oid=new FolioPk();
            oid.idMatricula = solFol.getIdMatricula();
            folio = forseti.getFolioByID(oid, evento.getUsuarioNeg());   
            if(folio!=null){
	            if(folio.getAnotaciones()==null || folio.getAnotaciones().isEmpty()){
	        		folio.setAnotaciones(forseti.getAnotacionesFolioTMP(oid));
	        	}
            }
        }catch(ForsetiException e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"No se pudo imprimir " + printer.toString());
            throw new ErrorHojaRutaException(e.getMessage());
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException("No se pudo imprimir la hoja de ruta");
        }
        
        if (folio!=null){
            CirculoPk idCirc = new CirculoPk();
            idCirc.idCirculo = folio.getZonaRegistral().getCirculo().getIdCirculo();
            List deptos = new ArrayList();
            try {
                deptos = forseti.getDepartamentos(idCirc);
            } catch (ForsetiException e) {
                throw new ErrorHojaRutaException(e.getMessage());
            } catch (Throwable e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e.getMessage(), e);
            }
            Iterator iter = deptos.iterator();
            while (iter.hasNext()) {
                Departamento depto = (Departamento) iter.next();
                if(depto.getIdDepartamento().equals(folio.getZonaRegistral().getVereda().getIdDepartamento())){
                	folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().setNombre(depto.getNombre());
                    Iterator iterMunc = depto.getMunicipios().iterator();
                    while(iterMunc.hasNext()){
                        Municipio munic = (Municipio)iterMunc.next();
                        if(munic.getIdMunicipio().equals(folio.getZonaRegistral().getVereda().getIdMunicipio())){
                            folio.getZonaRegistral().getVereda().getMunicipio().setNombre(munic.getNombre());
                            Iterator iterVereda = munic.getVeredas().iterator();
                            while(iterVereda.hasNext()){
                                Vereda vereda = (Vereda)iterVereda.next();
                                if(vereda.getIdVereda().equals(folio.getZonaRegistral().getVereda().getIdVereda())){
                                    folio.getZonaRegistral().getVereda().setNombre(vereda.getNombre());
                                }

                            }
                        }
                    }
                }
            }

            //CONSTANTES PARA LA IMPRESIÓN.
            String INTENTOS = "INTENTOS";
            String ESPERA = "ESPERA";

            //COLOCAR LOS PARAMETROS INICIALES
            Hashtable parametros = new Hashtable();

            //OBTENER INFORMACIÓN A PARTIR DEL TURNO
            Turno turno = evento.getTurno();
            String UID = evento.getUid();

            //IMPRIMIR LOS CERTIFICADOS SOLICITADOS
            try {

                    //INGRESO DE INTENTOS DE IMPRESION
                    String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
                    if (intentosImpresion != null)
                    {
                        parametros.put(INTENTOS, intentosImpresion);
                    }
                    else
                    {
                        intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
                        parametros.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
                    }

                    //INGRESO TIEMPO DE ESPERA IMPRESION
                    String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
                    if (intentosImpresion != null)
                    {
                        parametros.put(ESPERA, esperaImpresion);
                      
                    }
                    else
                    {
                        esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
                        parametros.put(ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
                    }

                if ((UID == null) || (UID.length() == 0) || (intentosImpresion == null) || (intentosImpresion.length() == 0) || (esperaImpresion == null) || (esperaImpresion.length() == 0)) {
                    throw new EventoException("Se requiere especificar el circulo, el número de intentos y la espera.");
                }

                if (folio == null) {
                    throw new EventoException("Se requiere especificar la huja de ruta a imprimir");
                }
                
                gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
                String fechaImpresion = this.getFechaImpresion();
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
                ImprimibleHojaDeRuta imprimibleHojaRuta = new ImprimibleHojaDeRuta(folio, turno, usuarioNeg, fechaImpresion,CTipoImprimible.HOJA_RUTA,tbase1, tbase2);
				
				
				try{
					parametros = this.imprimir(imprimibleHojaRuta, parametros, UID);
				}catch(PrintJobsException exc){
					int idImprimible = exc.getIdImpresion();
					if(idImprimible!=0){
						throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
					}
				}				
            }catch(ImpresionException exc){
				throw exc;
            } catch (Throwable t2) {
                throw new EventoException("No se pudo imprimir");
            }

            return new EvnRespCertificado(EvnRespCertificado.IMPRIMIR_HOJA_RUTA);
        }
        return null;
    }

    /**
     * @param imprimible
     * @param parametros
     * @param UID
     * @return
     */
    private Hashtable imprimir(ImprimibleBase imprimible, Hashtable parametros, String UID)throws PrintJobsException {

        //CONSTANTES PARA LA IMPRESIÓN.
        String INTENTOS = "INTENTOS";
        String ESPERA = "ESPERA";

        Bundle b = new Bundle(imprimible);

        String numIntentos = (String) parametros.get(INTENTOS);
        String espera = (String) parametros.get(ESPERA);

        Integer intentosInt = new Integer(numIntentos);
        int intentos = intentosInt.intValue();
        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(UID, b, intentos, esperado);
        } catch (PrintJobsException pe) {
			throw pe;
		}catch (Throwable t) {
            t.printStackTrace();
        }

        return parametros;
    }
    
    /**
     * @param evento
     * @return
     */
    private EventoRespuesta desasociarMatricula(EvnAntiguoSistema evento) throws EventoException{
        String matricula=evento.getNumMatricula();
        Turno turno = evento.getTurno();
        
        
        try {
                
            TurnoPk tid=new TurnoPk();
            tid.anio=turno.getAnio();
            tid.idCirculo=turno.getIdCirculo();
            tid.idProceso=turno.getIdProceso();
            tid.idTurno=turno.getIdTurno();
            hermod.removeFolioFromTurno(matricula, tid);
            Turno nuevo = hermod.getTurno(tid);
            return new EvnRespAntiguoSistema(nuevo,EvnRespAntiguoSistema.ELIMINAR_MATRICULAS);
           
        } catch(ForsetiException e){
            throw new ErrorHojaRutaException(e);
        } catch(HermodException e){
            throw new ErrorHojaRutaException("Error asociando la matrícula");
        } catch (Throwable e) {
            throw new EventoException("Error asociando la matrícula");
        }
    }
    
    /**
     * @param evento
     * @return
     */
    private EventoRespuesta eliminarMatricula(EvnAntiguoSistema evento) throws EventoException{
        String matricula=evento.getNumMatricula();
        Turno turno = evento.getTurno();
        
        
        try {
                
            TurnoPk tid=new TurnoPk();
            tid.anio=turno.getAnio();
            tid.idCirculo=turno.getIdCirculo();
            tid.idProceso=turno.getIdProceso();
            tid.idTurno=turno.getIdTurno();
            
            FolioPk fid=new FolioPk();
            
            Iterator itFolios=turno.getSolicitud().getSolicitudFolios().iterator();
            SolicitudFolio solic=null;
            while(itFolios.hasNext()){
                SolicitudFolio solFo=(SolicitudFolio)itFolios.next(); 
                if (solFo.getIdMatricula().equals(matricula)){
                    solic=solFo;
                }
            }
            if (solic!=null){
                fid.idMatricula=solic.getIdMatricula();
            }
            
            if (fid!=null){
                forseti.deleteFolio(fid,evento.getUsuarioNeg());
            }
            
            //hermod.removeFolioFromTurno(matricula, tid);
            Turno nuevo = hermod.getTurno(tid);
            return new EvnRespAntiguoSistema(nuevo,EvnRespAntiguoSistema.ELIMINAR_MATRICULAS);
           
        } catch(ForsetiException e){
            throw new ErrorHojaRutaException(e);
        } catch(HermodException e){
            throw new ErrorHojaRutaException("Error asociando la matrícula");
        } catch (Throwable e) {
            throw new EventoException("Error asociando la matrícula");
        }
    }

    /**
     * @param evento
     * @return
     */
    private EvnRespAntiguoSistema asociarMatricula(EvnAntiguoSistema evento) throws EventoException{
        String matricula=evento.getNumMatricula();
        Turno turno = evento.getTurno();
        
        List validaciones = new ArrayList();
        Validacion val = new Validacion();
        Validacion val2 =  new Validacion();
        Validacion val3 =  new Validacion();
        val.setIdValidacion(CValidacion.FOLIO_EXISTE);
        val.setNombre("Folio Existe");
        validaciones.add(val);
        val2.setIdValidacion(CValidacion.FOLIO_BLOQUEADO);
        val2.setNombre("Folio Bloqueado");
        validaciones.add(val2);
        val3.setIdValidacion(CValidacion.FOLIO_INCONSISTENTE);
        val3.setNombre("Folio Inconsistente");
        validaciones.add(val3);
        /*
        * @author      :   Julio Alcázar Rivas
        * @change      :   Nuevo validación CValidacion.FOLIO_TRASLADADO
        * Caso Mantis  :   07123
        */
        Validacion val4 =  new Validacion();
        val4.setIdValidacion(CValidacion.FOLIO_TRASLADADO);
        val4.setNombre("Folio Trasladado");
        validaciones.add(val4);
        List matriculas = new ArrayList();
        matriculas.add(matricula);
        
        try {
            if (forseti.validarMatriculas(validaciones, matriculas)){
                
                TurnoPk tid=new TurnoPk();
                tid.anio=turno.getAnio();
                tid.idCirculo=turno.getIdCirculo();
                tid.idProceso=turno.getIdProceso();
                tid.idTurno=turno.getIdTurno();
                hermod.addFolioToTurno(matricula, tid, evento.getUsuarioNeg());
                Folio folio=forseti.getFolioByMatriculaSinAnotaciones(matricula);
                FolioPk fid=new FolioPk();
                fid.idMatricula=folio.getIdMatricula();
                hermod.marcarFolioInTurno(tid, fid);
                Turno nuevo = hermod.getTurno(tid);
                return new EvnRespAntiguoSistema(nuevo,EvnRespAntiguoSistema.ASOCIAR_MATRICULAS);
            }
            throw new ErrorHojaRutaException("La matrícula no existe");
        } catch(ForsetiException e){
            throw new ErrorHojaRutaException(e);
        } catch(HermodException e){
            throw new ErrorHojaRutaException("Error asociando la matrícula");
        } catch (Throwable e) {
            throw new EventoException("Error asociando la matrícula");
        }
    }

    /**
     * @param evento
     */
/*    private EvnRespAntiguoSistema avanzarRevisionNegar(EvnAntiguoSistema evento) throws EventoException{
        Hashtable tabla = new Hashtable();

        Turno turno = evento.getTurno();

        Fase fase = evento.getFase();
        Estacion estacion = evento.getEstacion();
        String idCirculo = turno.getIdCirculo();
        tabla.put(Processor.ESTACION, estacion.getEstacionId());
        tabla.put(Processor.CIRCULO, idCirculo);
        tabla.put(Processor.RESULT, evento.getRespuestaWF());
        tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

        Vector notasPublicas = this.getNotasPublicas(turno);

        String nombreCirculo="";
        String turnoId = turno.getIdWorkflow();

        String matricula = "";
        try{
            nombreCirculo = turno.getSolicitud().getCirculo().getNombre();

            List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
            SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(solicitudesFolio.size()-1);
            matricula=solicitudFolio.getIdMatricula();
        }
        catch (Throwable t){
            t.printStackTrace();
        }

        String fechaImpresion= this.getFechaImpresion();
        ImprimibleNotaDevolutiva impNota = new ImprimibleNotaDevolutiva(notasPublicas,nombreCirculo,turnoId,matricula, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
        tabla.put(Processor.IMPRIMIBLE, impNota);
        try{
             //INGRESO DE INTENTOS DE IMPRESION
            String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null){
                 tabla.put(Processor.INTENTOS, intentosImpresion);
            }
            else{
                tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null){
                 tabla.put(Processor.ESPERA, esperaImpresion);
            }
            else{
                tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
            }

            //AVANZAR EL TURNO
            hermod.avanzarTurno(turno, fase, tabla,CAvanzarTurno.AVANZAR_NORMAL);

        } catch (HermodException e) {
            throw new ImpresionNoEfectuadaException("No se pudo avanzar el turno", e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        return null;
    }*/

    /**
     * @param turno
     * @return
     */
    private Vector getNotasPublicas(Turno turno) {

        Vector notasPublicas = new Vector();
        List notas = turno.getNotas();

        for (int i = 0; i < notas.size(); i++) {
            Nota nota = (Nota) notas.get(i);
            String visibilidad = nota.getTiponota().getVisibilidad();
            if (visibilidad.equalsIgnoreCase(CNota_Visibilidad.PUBLICO)) {
                notasPublicas.add(nota);
            }

        }
        return notasPublicas;
    }

    /**
     * @param evento
     */
    private EvnRespAntiguoSistema avanzarRevisionInicial(EvnAntiguoSistema evento) throws EventoException{
        ErrorRevisionInicialException exception = new ErrorRevisionInicialException("No se pudo avanzar el turno");
        
	
		int tipoAvance = CAvanzarTurno.AVANZAR_PUSH;		
		String respuestaWF = EvnAntiguoSistema.REVISION_INICIAL_CONFIRMAR_WF;
		EvnAntiguoSistema eventoCertificado=new EvnAntiguoSistema(evento.getUsuario(), evento.getTipoEvento(), evento.getTurno(), evento.getFase(), evento.getUsuarioNeg(), respuestaWF);
		
		//LA ÚNICA FORMA PARA QUE SE VAYA POR EXISTE CUANDO SE LE DA APROBAR ES CUANDO EN UN TURNO DE CERTIFICADOS 
		//YA TIENE UNA MATRICULA ASOCIADA. EN CUALQUIER OTRO CASO SE VA CON CONFIRMAR Y CON CON AVANZAR PUSH.
        if ((evento.getTurno().getSolicitud() instanceof SolicitudCertificado)){
        	SolicitudCertificado solCert = (SolicitudCertificado)evento.getTurno().getSolicitud();
        	
			if(solCert.getLiquidaciones().isEmpty()){
				throw new EventoException("El turno no tiene liquidaciones");
			}
			
    		LiquidacionTurnoCertificado liq = (LiquidacionTurnoCertificado)solCert.getLiquidaciones().get(0);
    		
			if((liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))&&(evento.getTurno().getSolicitud().getSolicitudFolios().size()>0)){
				
				
				tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;
				respuestaWF = EvnAntiguoSistema.ANTIGUO_SISTEMA_PERTENENCIA_WF;
				eventoCertificado.setRespuestaWF(respuestaWF);
				
			}
			if( (liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_AMPLIACION_TRADICION_ID)) && ((evento.getTurno().getSolicitud().getSolicitudFolios().size()>0)) ){
				tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;
				respuestaWF = CRespuesta.AMPLIACION_TRADICION;
				eventoCertificado.setRespuestaWF(respuestaWF);
			}

        }
		
		avanzarTurno(eventoCertificado , exception , tipoAvance);
		return null;
    }
    
    
	/**
	 * @param evento
	 */
	private EvnRespAntiguoSistema avanzarRevisionInicialNegar(EvnAntiguoSistema evento) throws EventoException{
		ErrorRevisionInicialException exception = new ErrorRevisionInicialException("No se pudo avanzar el turno");
		
		int tipoAvance = determinarTipoAvanceSalidaAntiguoSistema(evento.getTurno());		
		evento.setRespuestaWF(EvnAntiguoSistema.REVISION_INICIAL_NEGAR_WF);
		String respuestaWF = determinarRespuesta(evento);
		respuestaWF = determinarRespuestaSiCalificacion(evento,respuestaWF);
		
		EvnAntiguoSistema eventoCertificado=new EvnAntiguoSistema(evento.getUsuario(), evento.getTipoEvento(), evento.getTurno(), evento.getFase(), evento.getUsuarioNeg(), respuestaWF);
		
		TurnoPk turnoid = new TurnoPk(evento.getTurno().getIdWorkflow());
		Nota nota = evento.getNota();
		
		List tiposNota = null;
		
		Solicitud sol = evento.getTurno().getSolicitud();
		
		
		
		try{
			boolean devolutiva = evento.getTurno().getSolicitud() instanceof SolicitudCertificado;
			String idp = hermod.getProcesoByIdFase(nota.getIdFase());
			ProcesoPk proc = new ProcesoPk(idp);
			tiposNota = hermod.getTiposNotaProceso(proc, 
				nota.getIdFase(), devolutiva);
			
			if (tiposNota == null || tiposNota.size() == 0)
			{
				throw new EventoException("no existen tipos de nota válidos");
			}
			
			for (Iterator iter = sol.getSolicitudFolios().iterator(); iter.hasNext();) 
			{
				SolicitudFolio element = (SolicitudFolio) iter.next();
				
				if (!element.getFolio().isDefinitivo() && element.isRecienCreadoAntiguoSistema())
				{
					List matriculas = new ArrayList();
					matriculas.add(element.getFolio().getIdMatricula());
					FolioPk id=new FolioPk();
					id.idMatricula = element.getFolio().getIdMatricula();
					forseti.delegarBloqueoFolio(turnoid, evento.getUsuarioNeg(), id );
					forseti.deleteFolio(id, evento.getUsuarioNeg());
				}
			}
			
			TipoNota tn = (TipoNota) tiposNota.get(0);			

			nota.setTiponota(tn);	
			nota.setIdProceso(Long.parseLong(idp));	
			hermod.addNotaToTurno(nota, turnoid);	
		} catch (HermodException e) {
			new NotaNoAgregadaException("La nota no pudo ser agregada", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		Vector notas = new Vector();
		notas.add(nota);
		
		String fechaImpresion = this.getFechaImpresion();
		ImprimibleNotaDevolutiva imp = new ImprimibleNotaDevolutiva(notas, 
			evento.getCirculo().getNombre(), 
			evento.getTurno().getIdWorkflow(), "", fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
		imp.setPrintWatermarkEnabled(true);
			
		imp.setImprimirMatricula(false);		
		
		quitarMarcas(evento);
		
		avanzarTurnoImp(eventoCertificado , exception ,tipoAvance, imp);
		
		//IMPRIMIR LA NOTA DEVOLUTIVA TODO: SOLO PARA CERTIFICADOS
		Hashtable parametros = new Hashtable();
		String intentosImpresion=null;
		String esperaImpresion=null;
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
		try {
			imprimir(imp,parametros,evento.getTurno().getIdCirculo());
		} catch (PrintJobsException e) {
			throw new EventoException(e.getMessage());
		}
		
		return null;
	}    
	

	

	/**
	 * @param evento
	 */
	private EvnRespAntiguoSistema avanzarRevisionInicialExiste(EvnAntiguoSistema evento) throws EventoException{
		ErrorRevisionInicialException exception = new ErrorRevisionInicialException("No se pudo avanzar el turno");
		
		int tipoAvance = determinarTipoAvanceSalidaAntiguoSistema(evento.getTurno());		
		evento.setRespuestaWF(EvnAntiguoSistema.REVISION_INICIAL_NEGAR_WF);
		String respuestaWF = determinarRespuesta(evento);
		respuestaWF = determinarRespuestaSiCalificacion(evento,respuestaWF);
		EvnAntiguoSistema eventoCertificado=new EvnAntiguoSistema(evento.getUsuario(), evento.getTipoEvento(), evento.getTurno(), evento.getFase(), evento.getUsuarioNeg(), respuestaWF);		
		validarConfrontacionMatricula(evento.getTurno());
		quitarMarcas(evento);
		avanzarTurno(eventoCertificado , exception ,tipoAvance);
		return null;
	}    	
    
    
    /**
     * Valida que si el avance es por folio existente debe haber algún folio asociado
     * @param turno
     * @throws EventoException
     */
	private void validarConfrontacionMatricula(Turno turno) throws EventoException{
		if(turno==null){
			throw new ErrorRevisionInicialException("Error el turno es nulo");
		}
		
		if(turno.getSolicitud()==null){
			throw new ErrorRevisionInicialException("Error la solicitud del turno es nula");
		}
		
		if(turno.getSolicitud().getSolicitudFolios().isEmpty()){
			throw new ErrorRevisionInicialException("No puede avanzar el turno como existente si no tiene folios asociados");
		}
		
		SolicitudFolio sf;
		boolean hayFoliosRecienCreados = false;
		for(Iterator it = turno.getSolicitud().getSolicitudFolios().iterator(); it.hasNext();){
			sf = (SolicitudFolio)it.next();
			if(sf.isRecienCreadoAntiguoSistema()){
				hayFoliosRecienCreados = true;
			}
		}
		
		if(hayFoliosRecienCreados){
			throw new ErrorRevisionInicialException("No puede avanzar el turno como existente. Hay una hoja de ruta en curso que debe resolverse");
		}
	}

	/**
	  * @param evento
	  */
	 private EvnRespAntiguoSistema avanzarHojaRuta(EvnAntiguoSistema evento) throws EventoException{
        
		ErrorHojaRutaException exception = new ErrorHojaRutaException("No se pudo avanzar el turno");
		String respuestaWF = EvnAntiguoSistema.HOJA_RUTA_EXISTE_WF;

		Turno turno = evento.getTurno();
		
		try {
			TurnoPk tid=new TurnoPk();
			tid.anio=turno.getAnio();
			tid.idCirculo=turno.getIdCirculo();
			tid.idProceso=turno.getIdProceso();
			tid.idTurno=turno.getIdTurno();
			Turno turnoTemp = hermod.getTurno(tid);
			
			Solicitud solicitud = turnoTemp.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();
			
			Iterator it = solFolios.iterator();
			while(it.hasNext()){
				SolicitudFolio solFolio = (SolicitudFolio)it.next();
				if(solFolio.isRecienCreadoAntiguoSistema()){
					respuestaWF = EvnAntiguoSistema.HOJA_RUTA_CREADO_WF;				
				}
			}			 
		} catch(HermodException e){
			throw new ErrorHojaRutaException("Error asociando la matrícula");
		} catch (Throwable e) {
			throw new EventoException("Error asociando la matrícula");
		}
		
		evento.setRespuestaWF(respuestaWF);
				
		return avanzarTurno(evento, exception, CAvanzarTurno.AVANZAR_PUSH);		 
		
	 }
        
    
	/**
	 * @param evento
	 */
	private EvnRespAntiguoSistema avanzarRevision(EvnAntiguoSistema evento) throws EventoException{
		ErrorRevisionInicialException exception = new ErrorRevisionInicialException("No se pudo avanzar el turno");
		
		int tipoAvance = determinarTipoAvanceSalidaAntiguoSistema(evento.getTurno());
		//ESTA VARIABLE ESTA INICIALIZADA EN 2, PORQUE SEGÚN EL WORKFLOW SE REQUIRE ELIMINAR 
		//2 TURNOS QUE SE HABÍAN AVANZADO CON PUSH.
		int cantidadADeshacer = 2;		
		
		try {
			hermod.deshacerAvancesPush(evento.getTurno(), cantidadADeshacer);
		} catch (HermodException e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"No se pudo deshacer el registro de usuarios que intervinieron en antiguo sistema. " + printer.toString());
			throw new ErrorHojaRutaException("No se pudo deshacer el registro de usuarios que intervinieron en antiguo sistema. "  + e.getMessage() );
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"No se pudo deshacer el registro de usuarios que intervinieron en antiguo sistema. " + printer.toString());
			throw new EventoException("No se pudo deshacer el registro de usuarios que intervinieron en antiguo sistema.");
		}		
		
		//QUITAR MARCAS DE ASOCIACIÓN DE FOLIOS
		//QUITAR MARCAS FOLIOS GENERADOS EN ANTIGUO SISTEMA
		quitarMarcas(evento);
		String respuestaWF = determinarRespuesta(evento);
		respuestaWF = determinarRespuestaSiCalificacion(evento,respuestaWF);
		evento.setRespuestaWF(respuestaWF);
		if (evento.getTurno().getSolicitud() instanceof SolicitudCertificado){
			SolicitudCertificado solCert=(SolicitudCertificado)evento.getTurno().getSolicitud();
			LiquidacionTurnoCertificado liq = (LiquidacionTurnoCertificado)solCert.getLiquidaciones().get(0);
    		
			if((liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))){
				evento.setRespuestaWF(EvnAntiguoSistema.ANTIGUO_SISTEMA_PERTENENCIA_WF);
			}
		}
		
		avanzarTurno(evento , exception ,tipoAvance);

		//SI ES DE CERTIFICADO, SE DEBE IMPRIMIR EL CERTIFICADO O LA NOTA DEVOLUTIVA.
		if (evento.getTurno().getSolicitud() instanceof SolicitudCertificado){
			try {
				SolicitudCertificado solCert =(SolicitudCertificado)evento.getTurno().getSolicitud();
				LiquidacionTurnoCertificado liq = (LiquidacionTurnoCertificado)solCert.getLiquidaciones().get(0);
	    		
				if(!(liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))){
					imprimirCertificado(evento.getTurno(),evento.getUsuarioNeg(),evento.getTurno().getIdCirculo());
				}
			} catch (Throwable e) {
				throw new EventoException(e.getMessage());
			}			
		}
		return null;
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
		
		Log.getInstance().debug(ANAntiguoSistema.class,"\n*******************************************************");
		Log.getInstance().debug(ANAntiguoSistema.class,"(ANTES METODO IMPRESION CERTIFICADO)");
		Log.getInstance().debug(ANAntiguoSistema.class,"\n*******************************************************\n");
		
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
			FolioPk folioPk = new FolioPk ();
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
		/**
                 * @author     : Carlos Torres
                 * @change     : Set propiedad NIS en el imprimible.
                 * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas Impresión certificados
                 */
                String text = turno.getIdWorkflow() +"/"+ turno.getSolicitud().getIdSolicitud();
                byte [] key  = new byte [8];
                key[0] = 5;
                imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
                PrintJobsProperties prop = PrintJobsProperties.getInstancia();
                imprimibleCertificado.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));
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

		Log.getInstance().debug(ANAntiguoSistema.class,"Tipo de Tarifa = "+tipoTarifa);
		if (tipoTarifa!=null && tipoTarifa.equals(CHermod.EXENTO)){
			imprimibleCertificado.setExento(true);
			String textoExento = hermod.getTextoExento();
			imprimibleCertificado.setTextoExento(textoExento);
		}else{
			imprimibleCertificado.setExento(false);
		}

   	    parametros = this.imprimir(turno,imprimibleCertificado,parametros,numCopias);

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
   	    
		Log.getInstance().debug(ANAntiguoSistema.class,"\n*******************************************************");
		Log.getInstance().debug(ANAntiguoSistema.class,"(DESPUES METODO IMPRESION CERTIFICADO)");
		Log.getInstance().debug(ANAntiguoSistema.class,"\n*******************************************************\n");
   	    
		return parametros;
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
	private Hashtable imprimir(Turno turno, ImprimibleBase imprimible, Hashtable parametros, int numCopias) {

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
	
	public static BufferedImage getImage(String path, String nombreArchivo){
		String nombreCompleto = getNombreCompleto(path,nombreArchivo);
		BufferedImage buf = null;

		try{
			File file = new File(nombreCompleto);
			buf = ImageIO.read(file);
		}
		catch (IOException e){
			Log.getInstance().error(ANAntiguoSistema.class,e);
			Log.getInstance().debug(ANAntiguoSistema.class,"Error imprimiendo el gráfico en la ruta: "+nombreCompleto);
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
	 *  Permite determinar la respuesta para el avance, si el proceso de pago mayor valor fue llamado desde actuaciones administrativas.
	 * Si es así la respuesta siempre será ACTUACION_ADMINISTRATIVA, sino devuelve la respuesta requerida y que viene en el evento.
	 * @param evento
	 * @return
	 */
	private String determinarRespuesta(EvnAntiguoSistema evento) throws EventoException {
		
		String WF_ACTUACION_ADMINISTRATIVA = "ACTUACION_ADMINISTRATIVA";
		 
		String rta = null;
		boolean esActuacion = false;

		Turno turno = evento.getTurno();		
		List turnosHistoria = turno.getHistorials();

		TurnoHistoria thUltimo = null;
		if(turnosHistoria!=null && turnosHistoria.size()>0){
			Iterator it = turnosHistoria.iterator();
			
			while(it.hasNext()){
				thUltimo = (TurnoHistoria)it.next();
	
				if(thUltimo.getFase()!=null&&thUltimo.getFase().equals(CFase.ANT_REVISION_INICIAL)){
					if(thUltimo.getFaseAnterior()!=null && thUltimo.getFaseAnterior().equals(CFase.COR_ACT_ADMIN)){
						esActuacion = true;
					}
					else{
						esActuacion = false;
					}
				
				}
			}
	
		}
		
		if(esActuacion){
			rta = WF_ACTUACION_ADMINISTRATIVA;
		}else{
			rta = evento.getRespuestaWF();
		}
		
		return rta;
	}	
	
	/**
	 * Permite determinar la respuesta para el avance, si el proceso de antiguo sistema es llamado de Calificacion
	 * @param evento
	 * @return
	 */
	private String determinarRespuestaSiCalificacion(EvnAntiguoSistema evento, String respuesta) throws EventoException {
		
		String rta = null;
		boolean tieneCalificacion = false;

		Turno turno = evento.getTurno();		
		List turnosHistoria = turno.getHistorials();

		TurnoHistoria thUltimo = null;
		if(turnosHistoria!=null && turnosHistoria.size()>0){
			Iterator it = turnosHistoria.iterator();
			
			while(it.hasNext()){
				thUltimo = (TurnoHistoria)it.next();
	
				if(thUltimo.getFase()!=null&&thUltimo.getFase().equals(CFase.CAL_CALIFICACION)){
					tieneCalificacion = true;
				}
			}
	
		}
		
		if(tieneCalificacion){
			rta = respuesta + "_CAL";
		}else{
			rta = respuesta;
		}
		
		return rta;
	}	

    

    /**
     * @param ev
     */
    private EvnRespAntiguoSistema avanzarTurno(EvnAntiguoSistema evento, ValidacionParametrosHTException exception, int tipoAvance) throws EventoException{
        Hashtable tabla = new Hashtable();

        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        tabla.put(Processor.RESULT, evento.getRespuestaWF());
        tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());

        try {
        	if (  (turno.getSolicitud() instanceof SolicitudCertificado)||(turno.getSolicitud() instanceof SolicitudRegistro)||(turno.getSolicitud() instanceof SolicitudCorreccion)){
        		if (tipoAvance == CAvanzarTurno.AVANZAR_PUSH){
        			hermod.avanzarTurnoNuevoPush(turno,fase,tabla,evento.getUsuarioNeg());
        		}else if (tipoAvance == CAvanzarTurno.AVANZAR_POP){
        			hermod.avanzarTurnoNuevoPop(turno,fase,tabla,evento.getUsuarioNeg());
        		}else if (tipoAvance == CAvanzarTurno.AVANZAR_ELIMINANDO_PUSH){
        			hermod.avanzarTurnoNuevoEliminandoPush(turno,fase,tabla,evento.getUsuarioNeg());
        		}else if (tipoAvance == CAvanzarTurno.AVANZAR_NORMAL){
        			hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNeg());
        		}
        	}
            
        } catch (HermodException e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }        
        return null;
    }
    
    
	/**
	 * @param ev
	 */
	private EvnRespAntiguoSistema avanzarTurnoImp(EvnAntiguoSistema evento, ValidacionParametrosHTException exception, int tipoAvance, Imprimible imp) throws EventoException{
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
		
		tabla.put(Processor.IMPRIMIBLE, imp);

		try {
    		if (tipoAvance == CAvanzarTurno.AVANZAR_PUSH){
    			hermod.avanzarTurnoNuevoPush(turno,fase,tabla,evento.getUsuarioNeg());
    		}else if (tipoAvance == CAvanzarTurno.AVANZAR_POP){
    			hermod.avanzarTurnoNuevoPop(turno,fase,tabla,evento.getUsuarioNeg());
    		}else if (tipoAvance == CAvanzarTurno.AVANZAR_ELIMINANDO_PUSH){
    			hermod.avanzarTurnoNuevoEliminandoPush(turno,fase,tabla,evento.getUsuarioNeg());
    		}else if (tipoAvance == CAvanzarTurno.AVANZAR_NORMAL){
    			hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNeg());
    		}
        	
		} catch (HermodException e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAntiguoSistema.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}        
		return null;
	}



	private int determinarTipoAvanceSalidaAntiguoSistema(Turno turno) throws EventoException{

		int tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;		
		Solicitud solicitud = turno.getSolicitud();
		
		if(solicitud instanceof SolicitudCertificado){
			tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;
		}else if(solicitud instanceof SolicitudCorreccion){
			tipoAvance = CAvanzarTurno.AVANZAR_POP;
		}else if(solicitud instanceof SolicitudRegistro){
			List historial = turno.getHistorials();
			Iterator it = historial.iterator();
			
			//LA ÚNICA PARTE DE REGISTRO QUE NO AVANZA CON POP ES CUANDO EL TURNO FUE CREADO COMO ANTIGUO SISTEMA,
			//PORQUE EN ESTE CASO EL TURNO NUNCA SE DEVUELVE AL MISMO USUARIO SINO QUE AVANZA A OTRO.
			//EN CUALQUIER OTRO CASO COMO EL TURNO SIEMPRE PASA POR REPARTO EL AVANCE DEL TURNO SERA CON POP
			//PARA QUE QUEDE EN EL MISMO USUARIO. APLICA TAMBIÉN EN LOS CASOS EN QUE EL TURNO DE REGISTRO
			//FUE ENVIADO A CORRECCIÓN INTERNA DÓNDE SI SE ENVIA A ANTIGUO SISTEMA EL TURNO DEBE QUEDAR 
			//CON EL MISMO USUARIO. 
			while(it.hasNext()){
				TurnoHistoria th = (TurnoHistoria) it.next();
				if(th.getFase().equals(CFase.REG_REPARTO)){
					tipoAvance = CAvanzarTurno.AVANZAR_POP;
				}
			}
		}
		
		return tipoAvance;
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


}
