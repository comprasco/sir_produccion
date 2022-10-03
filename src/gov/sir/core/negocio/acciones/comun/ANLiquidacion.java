package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.eventos.certificado.EvnRespValidacionMatricula;
import gov.sir.core.eventos.certificado.EvnValidacionMatricula;
import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnPago;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.acciones.excepciones.SolicitudConsultaNoCreadaException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiService;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Esta accion de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnLiquidacion</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespLiquidacion</CODE>
 * Esta accion de negocio se encarga de atender todas las solicitudes relacionadas
 * con efectuar lquidaciones de solicitudes, efectuando las validaciones
 * pertinenetes.
 * @author eacosta,mmunoz,dsalas
 */
public class ANLiquidacion extends SoporteAccionNegocio {




	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	private static ProcesadorEventosNegocioProxy proxy = null;

	/**
	 *Constructor de la clase ANLiquidacion.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANLiquidacion() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio(
					"gov.sir.hermod");

			if (hermod == null) {
				throw new ServicioNoEncontradoException(
					"Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException(
				"Error instanciando el servicio Hermod",e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException(
				"Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio(
					"gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException(
					"Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException(
				"Error instanciando el servicio forseti",e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException(
				"Servicio forseti no encontrado");
		}
	}

	/**
	* Recibe un evento de seguridad y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
	* @see gov.sir.core.eventos.comun.EvnLiquidacion
	* @see gov.sir.core.eventos.comun.EvnRespLiquidacion
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnLiquidacion evento = (EvnLiquidacion) e;

		if (evento==null || evento.getTipoEvento()==null){
			return null;
		}

		if (evento.getTipoEvento().equals(EvnLiquidacion.LIQUIDAR)) {
			return liquidar(evento);
		} else if (evento.getTipoEvento().equals(EvnLiquidacion.SOLICITAR_LIQUIDAR)) {
                    return solicitarLiquidar(evento);
		}else if (evento.getTipoEvento().equals(EvnLiquidacion.SOLICITAR_LIQUIDAR_SIMPLIFICADO)) {
            return solicitarLiquidarSimplificado(evento);
        }else if (evento.getTipoEvento().equals(EvnLiquidacion.LIQUIDAR_PAGO)) {
            return liquidarPago(evento);
        }


		return null;
	}

        private EventoRespuesta solicitarLiquidar (EvnLiquidacion evento) throws EventoException {
            try {
                    SolicitudConsulta solicitud = (SolicitudConsulta) hermod.crearSolicitud(evento.getLiquidacion().getSolicitud());
                    if (solicitud == null) {
                            throw new SolicitudConsultaNoCreadaException(
                                    "No se pudo actualizar la búsqueda en la solicitud de consulta",
                                    null);
                    }
                    evento.getLiquidacion().setSolicitud(solicitud);
            } catch (HermodException e) {
                    Log.getInstance().error(ANLiquidacion.class,e.getMessage(), e);
                    throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                    throw new EventoException(e.getMessage(), e);
            }

            return liquidar (evento);
        }

        
        private EventoRespuesta solicitarLiquidarSimplificado (EvnLiquidacion evento) throws EventoException {
            try {
                    SolicitudConsulta solicitud = (SolicitudConsulta) hermod.crearSolicitud(evento.getLiquidacion().getSolicitud());
                    if (solicitud == null) {
                            throw new SolicitudConsultaNoCreadaException(
                                    "No se pudo actualizar la búsqueda en la solicitud de consulta",
                                    null);
                    }
                    evento.getLiquidacion().setSolicitud(solicitud);
            } catch (HermodException e) {
                    Log.getInstance().error(ANLiquidacion.class,e.getMessage(), e);
                    throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                    throw new EventoException(e.getMessage(), e);
            }

            EvnRespLiquidacion evnTem = (EvnRespLiquidacion)this.liquidar (evento);
            /***************PAGO********************/
            ANPago anPago = new ANPago();
        	Pago pago = new Pago();
        	pago.setFecha(new Date());
        	Liquidacion liquidacion =evnTem.getLiquidacion();
        	DocumentoPago documentoEfectivo = new DocPagoEfectivo(0);
        	AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
        	pago.addAplicacionPago(aplicacionEfectivo);
        	pago.setLiquidacion(liquidacion);

            EvnPago local_TmpParameters = null;
            EvnRespPago local_TmpResult = null;
            		
           local_TmpParameters = new EvnPago(evento.getUsuario(), pago,evento.getProceso(),evento.getEstacion(), EvnPago.PROCESAR, evento.getUsuarioSIR()); 
           local_TmpParameters.setImpresora(evento.getImpresoraPredeterminada());
           local_TmpParameters.setRol(evento.getRol());
           local_TmpParameters.setSolicitud(liquidacion.getSolicitud());
           local_TmpParameters.setUID(evento.getUID());
           local_TmpParameters.setCirculo(evento.getCirculo());
           local_TmpParameters.setAsignarEstacion(true);
           local_TmpResult = (EvnRespPago)( getProcesadorEventosNegocioProxy().manejarEvento( local_TmpParameters ) );
             
           evnTem.setTipoEvento(EvnRespLiquidacion.LIQUIDACION_SIMPLIFICADO);
           evnTem.setTurno(local_TmpResult.getTurno());
           return evnTem;
        }
	/**
	 * Este metodo hace el llamado al negocio para que se haga la liquidacion de la solicitud
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
	 */
	private EventoRespuesta liquidar(EvnLiquidacion evento) throws EventoException {

		EvnRespLiquidacion evRespuesta = null;
                List turnoTramite = null;
                boolean isCertificadoTramite = false;
                boolean isCertificadoActuacion = false;

		Liquidacion liquidacion = evento.getLiquidacion();
		if (liquidacion==null){
			throw new LiquidacionNoEfectuadaException("No existe liquidación asociada");
		}
		Solicitud solicitud = liquidacion.getSolicitud();
		if (solicitud==null){
			throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
		}
                
                
		try {
                        List<String> issueCertificado = null;
			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();
                        String idMatricula = null;
			while(itSolFolio.hasNext()){
				SolicitudFolio sol = (SolicitudFolio)itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
                                idMatricula = sol.getFolio().getIdMatricula();
			}
			
                        if(!evento.isEspecial()){
			try {
				forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud),matriculas);
			} catch (ForsetiException e)
			{
				boolean lanzaError = true;
				
				if (solicitud instanceof SolicitudCertificado)
				{
					Hashtable ht = e.getHashErrores();
					if (matriculas != null && !matriculas.isEmpty())
					{
						String matricula = (String)matriculas.get(0);
						if (ht != null && ht.size() == 1)
						{
							List errores = (List)ht.get(matricula);
							if (errores != null && errores.size() == 1)
							{
								if (errores.get(0).equals(CValidacion.FIRMA_REG_EXISTE_MSG))
								{
									lanzaError = false;
								}
							}
						}
					}
				}
				if (lanzaError)
				{
					throw e;
				}
			}
                        } else{
                            ForsetiService fs = ForsetiService.getInstance();
                            issueCertificado = fs.validarCertificadoEspecial(hermod.getValidacionesSolicitud(solicitud), matriculas);
                            if(issueCertificado != null){
                                 for(String isInTramite:issueCertificado){
                                     if(isInTramite.equals(CValidacion.FOLIO_EN_TRAMITE_MSG)){
                                         isCertificadoTramite = true;
                                         turnoTramite = forseti.getTurnosTramiteFolio(idMatricula);
                                         break;
                                     } else if(isInTramite.equals(CValidacion.FOLIO_BLOQUEADO_MSG)){
                                         isCertificadoTramite = true;
                                         turnoTramite = forseti.getTurnosTramiteFolio(idMatricula);
                                         break;
                                     }
                                 }
                            }
                            
                            if(forseti.isActuacionAdministrativa(idMatricula)){
                                isCertificadoActuacion = true;
                            }
                            
                        }
                        /**
                        * @Author: Santiago Vásquez
                        * @Change: 2062.TARIFAS.REGISTRALES.2017
                        */
                        if (liquidacion instanceof LiquidacionTurnoCertificado && !matriculas.isEmpty()) {
                            LiquidacionTurnoCertificado liquidacionCertificado = (LiquidacionTurnoCertificado) liquidacion;
                            liquidacionCertificado.setEsFolioMayorExtension(forseti.mayorExtensionFolio((String)matriculas.get(0)));
                            liquidacion = liquidacionCertificado;
                        }
			liquidacion.setUsuario(evento.getUsuarioSIR());
 			liquidacion = hermod.liquidar(liquidacion);
			evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.LIQUIDACION);
                        if(evento.isEspecial()){
                        evRespuesta.setCertificadoEspecial(evento.isEspecial());
                        evRespuesta.setCertificadoTramite(isCertificadoTramite);
                        evRespuesta.setCertificadoActuacion(isCertificadoActuacion);
                        evRespuesta.setTurnoTramite(turnoTramite);
                        }

		} catch (HermodException e) {
			throw new LiquidacionNoEfectuadaException(e.getMessage(),e);
		} catch (ForsetiException e) {
			throw new ValidacionParametrosHTException(e);
		} catch (Throwable e){
			throw new EventoException(e.getMessage(),e);
		}
		
		if (solicitud instanceof SolicitudDevolucion){
			SolicitudDevolucion sd = (SolicitudDevolucion)solicitud;
			if((sd.getConsignaciones().size()== 0)&&(liquidacion.getValor() != 0)){
				return evRespuesta;
			}
		} else {
			if(liquidacion.getValor() != 0){
				return evRespuesta;
			}
		}
        if(evento.isHabilitarPago()){
        	ANPago anPago = new ANPago();
        	Pago pago = new Pago();
        	pago.setFecha(new Date());
                        DocumentoPago documentoEfectivo = new DocPagoEfectivo(0);
        	AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
        	pago.addAplicacionPago(aplicacionEfectivo);
        	pago.setLiquidacion(liquidacion);
        	EvnPago eventoPago= new EvnPago(evento.getUsuario(), pago, evento.getProceso(), evento.getEstacion(),EvnPago.PROCESAR, evento.getUsuarioSIR());
            eventoPago.setUID(evento.getUID());
            if(liquidacion instanceof LiquidacionTurnoCertificado && evento.getImpresoraPredeterminada()!=null){
            	eventoPago.setImpresora(evento.getImpresoraPredeterminada());	
            }            
            eventoPago.setIdCirculo(evento.getIdCirculo());
            eventoPago.setCirculo(evento.getCirculo());
            eventoPago.setOmitirRecibo(evento.isOmitirRecibo());
            eventoPago.setCertificadoEspecial(evento.isEspecial());
            eventoPago.setCertificadoTramite(isCertificadoTramite);
            eventoPago.setCertificadoActuacion(isCertificadoActuacion);
            eventoPago.setTurnoTramite(turnoTramite);

                    if (evento.isAsignarEstacion()) {
                        eventoPago.setAsignarEstacion(true);
                    }
            if (solicitud instanceof SolicitudDevolucion){
            	eventoPago.setNotasInformativas(evento.getListaNotasInformativas());
            	eventoPago.setSolicitud(solicitud);
            }
            return anPago.perform(eventoPago);

        }
		return evRespuesta;

	}
	
	 public ProcesadorEventosNegocioProxy getProcesadorEventosNegocioProxy() {
	    	if( null == proxy ) {
	    		proxy =  new ProcesadorEventosNegocioProxy();
	    	}
	    	return proxy; 
	 }
	
	/**
	 * Este metodo hace el llamado al negocio para que se haga la liquidacion de la solicitud
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
	 */
	private EventoRespuesta liquidarPago(EvnLiquidacion evento) throws EventoException {
		EvnValidacionMatricula evMatricula = null;
		EvnRespValidacionMatricula evRespuestaMatriula = null;
        
		/**Se realiza la validación de la matrícula*/		
		evMatricula =  new EvnValidacionMatricula(evento.getUsuario(), evento.getMatricula(), evento.getTipoCertificado()); 
		evRespuestaMatriula = (EvnRespValidacionMatricula)( getProcesadorEventosNegocioProxy().manejarEvento(evMatricula));
		
		EvnRespLiquidacion evRespuesta = null;

		
                
                

		try {
                    Liquidacion liquidacion = evento.getLiquidacion();

                    if (liquidacion == null) {
                        throw new LiquidacionNoEfectuadaException("No existe liquidación asociada");
                    }
                        /**
                        * @Author: Santiago Vásquez @Change:
                        * 2062.TARIFAS.REGISTRALES.2017
                        */
                        if (liquidacion instanceof LiquidacionTurnoCertificado && evento.getMatricula() != null) {
                            LiquidacionTurnoCertificado liquidacionCertificado = (LiquidacionTurnoCertificado) liquidacion;
                            liquidacionCertificado.setEsFolioMayorExtension(forseti.mayorExtensionFolio(evento.getMatricula()));
                            liquidacion = liquidacionCertificado;
                        }
                        Solicitud solicitud = liquidacion.getSolicitud();
                        if (solicitud == null) {
                            throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
                        }

			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();
			while(itSolFolio.hasNext()){
				SolicitudFolio sol = (SolicitudFolio)itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
			}

			forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud),matriculas);
			liquidacion.setUsuario(evento.getUsuarioSIR());
 			liquidacion = hermod.liquidar(liquidacion);
			//evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.LIQUIDACION);
/*
                     * if(liquidacion.getValor() != 0){ return evRespuesta;
		}
                     */
                    //if(evento.isHabilitarPago()){
                    ANPago anPago = new ANPago();
                    Pago pago = new Pago();
                    pago.setFecha(new Date());
                    DocumentoPago documentoEfectivo = new DocPagoEfectivo(0);
                    AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
                    pago.addAplicacionPago(aplicacionEfectivo);
                    pago.setLiquidacion(liquidacion);
                    EvnPago eventoPago = new EvnPago(evento.getUsuario(), pago, evento.getProceso(), evento.getEstacion(), EvnPago.PROCESAR, evento.getUsuarioSIR());
                    eventoPago.setUID(evento.getUID());
                    if (liquidacion instanceof LiquidacionTurnoCertificado && evento.getImpresoraPredeterminada() != null) {
                        eventoPago.setImpresora(evento.getImpresoraPredeterminada());
                    }
                    eventoPago.setIdCirculo(evento.getIdCirculo());
                    eventoPago.setCirculo(evento.getCirculo());
                    eventoPago.setOmitirRecibo(evento.isOmitirRecibo());

                    if (evento.isAsignarEstacion()) {
                        eventoPago.setAsignarEstacion(true);
                    }
                    EvnPago local_TmpParameters = null;
                    EvnRespPago local_TmpResult = null;

                    local_TmpParameters = new EvnPago(evento.getUsuario(), pago, evento.getProceso(), evento.getEstacion(), EvnPago.PROCESAR, evento.getUsuarioSIR());
                    local_TmpParameters.setImpresora(evento.getImpresoraPredeterminada());
                    local_TmpParameters.setRol(evento.getRol());
                    local_TmpParameters.setSolicitud(liquidacion.getSolicitud());
                    local_TmpParameters.setUID(evento.getUID());
                    local_TmpParameters.setCirculo(evento.getCirculo());
                    local_TmpResult = (EvnRespPago) (getProcesadorEventosNegocioProxy().manejarEvento(local_TmpParameters));
                    evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.RADICAR);
                    //daniel
                    evRespuesta.setValorSecuencial(local_TmpResult.getValorSecuencial());
                    evRespuesta.setTurno(local_TmpResult.getTurno());
                    return evRespuesta;

		} catch (HermodException e) {
			throw new LiquidacionNoEfectuadaException(e.getMessage(),e);
		} catch (ForsetiException e) {
			throw new ValidacionParametrosHTException(e);
		} catch (Throwable e){
			throw new EventoException(e.getMessage(),e);
		}

		
            		
           // return anPago.perform(eventoPago);

       //}
          

	}


}
