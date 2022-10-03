package gov.sir.core.negocio.acciones.certificadosmasivos;

import gov.sir.core.eventos.certificadosmasivos.EvnCertificadoMasivo;
import gov.sir.core.eventos.certificadosmasivos.EvnRespCertificadoMasivo;
import gov.sir.core.negocio.acciones.excepciones.ANSeguridadException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionMatriculaCertificadoHTException;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.util.Log;
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
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author ppabon
 */
public class ANCertificadoMasivo extends SoporteAccionNegocio {

	/**Esta constante define el tipo de entrega confirmar luego de la impresión de los certificados*/
	public static final String ENTREGAR_CONFIRMAR = "ENTREGAR_CONFIRMAR";

	/**Esta constante define el tipo de entrega confirmar luego de la impresión de los certificados*/
	public static final String ENTREGAR_NEGAR = "ENTREGAR_NEGAR";
	
	/**
	 * Constante que identifica que se desea agregar una
	 * nueva matrícula a la solicitud
	 */
	public final static String AGREGAR_VERIFICADO = "AGREGAR_VERIFICADO";
	
	/**
	 * Constante que identifica que se van agregar matriculas desde archivo
	 * daniel 
	 */
	public final static String AGREGAR_DE_ARCHIVO = "AGREGAR_DE_ARCHIVO";

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

	/**
	 *Constructor de la clase ANCertificado.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 * @throws EventoException 
	 */
	public ANCertificadoMasivo() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}
		try {
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

			if (printJobs == null) {
				throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs", e);
		}

	}

	/**
	 * Recibe un evento de certificado y devuelve un evento de respuesta
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @throws ANSeguridadException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @see gov.sir.core.eventos.certificadosmasivos.EvnCertificadoMasivo
	 * @see gov.sir.core.eventos.certificadosmasivos.EvnRespCertificadoMasivo
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnCertificadoMasivo evento = (EvnCertificadoMasivo) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnCertificadoMasivo.ENTREGAR_CONFIRMAR)) {
			return avanzarTurnoEntrega(evento);
		} else if (evento.getTipoEvento().equals(EvnCertificadoMasivo.ENTREGAR_NEGAR)) {
			return avanzarTurnoEntrega(evento);
		} else if (evento.getTipoEvento().equals(EvnCertificadoMasivo.LIQUIDAR)) {
		    return liquidar(evento);
	    }else if (evento.getTipoEvento().equals(ANCertificadoMasivo.AGREGAR_VERIFICADO)) {
			return validarMatriculaCertificadosMasivos(evento);
		}else if (evento.getTipoEvento().equals(ANCertificadoMasivo.AGREGAR_DE_ARCHIVO)) {
			return validarMatriculasCertificadosMasivos(evento);
		}
		
		return null;
	}

	/**
	 * Este metodo hace el llamado al negocio para que avance el turno
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificadoMasivo</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificadoMasivo</CODE>
	 * @throws EventoException 
	 */
	private EventoRespuesta avanzarTurnoEntrega(EvnCertificadoMasivo evento) throws EventoException {
		EvnRespCertificadoMasivo evRespuesta = null;

		Turno turno = evento.getTurno();
		Hashtable ht = new Hashtable();
		Turno turnoCertificado = null;
		
		//SE AVANZA LOS TURNOS DE CERTIFICADOS INDIVIDUALES
		SolicitudCertificadoMasivo solicitudCertificadoMasivo = (SolicitudCertificadoMasivo)turno.getSolicitud();
				
		if(solicitudCertificadoMasivo!=null && !solicitudCertificadoMasivo.getSolicitudesHijas().isEmpty()){
			
			Fase fase = new Fase();
			fase.setID(CFase.CER_ENTREGAR);

			Iterator it = solicitudCertificadoMasivo.getSolicitudesHijas().iterator();
			while(it.hasNext()){
						
				ht = new Hashtable();
				ht.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				ht.put(Processor.RESULT,CRespuesta.OK);
						
				SolicitudAsociada solicitudAsociada = (SolicitudAsociada)it.next();
				SolicitudCertificado solicitudCertificado = (SolicitudCertificado)solicitudAsociada.getSolicitudHija();
						
				turnoCertificado = solicitudCertificado.getTurno();
						
				try {
					hermod.avanzarTurnoNuevoNormal(turnoCertificado, fase, ht,evento.getUsuarioNec());
				} catch (HermodException e) {
					throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
				} catch (Throwable e) {
					ExceptionPrinter printer = new ExceptionPrinter(e);
					Log.getInstance().error(ANCertificadoMasivo.class,"No se pudo avanzar el turno" + printer.toString());
					throw new EventoException(e.getMessage(), e);
				}
			}
		}		
				

		//SE AVANZA EL TURNO DE CERTIFICADOS MASIVOS
		Fase fase = evento.getFase();

		Hashtable tabla = new Hashtable();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		Solicitud sol = turno.getSolicitud();


		try {
			hermod.avanzarTurnoNuevoNormal(turno, fase, tabla, evento.getUsuarioNec());
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificadoMasivo.class,"No se pudo avanzar el turno" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return evRespuesta;
	}
	
	
	/**
	 * 
	 * @param evento
	 * @return  Evento respuesta de liquidacion
	 * @throws EventoException
	 */
	private EventoRespuesta liquidar(EvnCertificadoMasivo  evento) throws EventoException {
		EvnRespCertificadoMasivo evRespuesta = null;

		LiquidacionTurnoCertificadoMasivo liquidacion = evento.getLiquidacion();
		if (liquidacion==null){
			throw new LiquidacionNoEfectuadaException("No existe liquidación asociada");
		}
		Solicitud solicitud = liquidacion.getSolicitud();
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Determina si la solicitud es de tipo SolicitudCertificadoMasivo
                 * Caso Mantis  :   000941
                 */
                SolicitudCertificadoMasivo solicitudcertmasivo = null;
                if ( liquidacion.getSolicitud() instanceof  SolicitudCertificadoMasivo){
                    solicitudcertmasivo = (SolicitudCertificadoMasivo) liquidacion.getSolicitud();
                }
		if (solicitud==null){
			throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
		}
		String uid = evento.getUID();
		
		String cid = evento.getCirculo().getIdCirculo();
		try {
			//Recorremos la lista de solicitudes asociadas
			//para sacar los folios con el fin de validarlos
			List solicitudesAsociadas = solicitud.getSolicitudesHijas();
			List matriculas = new Vector();
			Iterator itSolFolio = solicitudesAsociadas.iterator();
			while(itSolFolio.hasNext()){
				SolicitudAsociada solAsociada = (SolicitudAsociada)itSolFolio.next();
				if(solAsociada.getSolicitudHija()==null){
					throw new LiquidacionNoEfectuadaException("La solicitud asociada NO tiene solicitud hija");
				}
				List folios = solAsociada.getSolicitudHija().getSolicitudFolios();
				Iterator itFol = folios.iterator();	
				while(itFol.hasNext()){
					SolicitudFolio solFol = (SolicitudFolio)itFol.next();
					matriculas.add(solFol.getFolio().getIdMatricula());				
				}
			}
			
			Hashtable ht = new Hashtable();
			try{
				forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud),matriculas);
			}
			catch (ForsetiException ex){
				ht = ex.getHashErrores();
			}
			
			List liquidacionesCertificadoCreadas = new ArrayList();
			
			//Recorremos de nuevo la lista de solicitudes asociadas para
			//crearlas de acuerdo con las validaciones hechas
			solicitudesAsociadas = solicitud.getSolicitudesHijas();
			itSolFolio = solicitudesAsociadas.iterator();
			boolean limpiarSolFolios;
			while(itSolFolio.hasNext()){
				limpiarSolFolios = false;
				SolicitudAsociada solAsociada = (SolicitudAsociada)itSolFolio.next();
				SolicitudCertificado solicitudACrear = (SolicitudCertificado)solAsociada.getSolicitudHija();
				
				if(solicitudACrear==null) {
					throw new LiquidacionNoEfectuadaException("La solicitud asociada NO tiene solicitud hija");
				}
				
				LiquidacionTurnoCertificado liquidacionACrear = new LiquidacionTurnoCertificado();
				liquidacionACrear.setSolicitud(solicitudACrear);
				liquidacionACrear.setTipoCertificado(liquidacion.getTipoCertificado());
				liquidacionACrear.setTipoTarifa(liquidacion.getTipoTarifa());
				liquidacionACrear.setUsuario(evento.getUsuarioNec());
				liquidacionACrear.setUid(uid);
                                
                                /**
                                * @Author: Santiago Vásquez
                                * @Change: 2062.TARIFAS.REGISTRALES.2017
                                */
                                if (solicitudACrear.getSolicitudFolios().get(0) instanceof SolicitudFolio) {
                                    SolicitudFolio solFolio = (SolicitudFolio) solicitudACrear.getSolicitudFolios().get(0);
                                    liquidacionACrear.setEsFolioMayorExtension(forseti.mayorExtensionFolio(solFolio.getIdMatricula()));
                                }
				solicitudACrear.addLiquidacion(liquidacionACrear);
				
				
				List folios = solicitudACrear.getSolicitudFolios();
				Iterator itFol = folios.iterator();
				while(itFol.hasNext()){
					SolicitudFolio solFol = (SolicitudFolio)itFol.next();
					if(ht.containsKey(solFol.getFolio().getIdMatricula())){
						liquidacionACrear.setTipoTarifa(CTipoTarifa.NO_EXPEDIDO);
						List errores = (List) ht.get(solFol.getFolio().getIdMatricula());
						if(errores.contains(CValidacion.FOLIO_EXISTE_MSG)){
							limpiarSolFolios = true;	
							solicitudACrear.setMatriculaNoExistente(solFol.getFolio().getIdMatricula());	
						}
					}
				}
				
				if(limpiarSolFolios){
					solicitudACrear.setSolicitudFolios(new ArrayList());
				}
			
				liquidacionesCertificadoCreadas.add(hermod.liquidar(liquidacionACrear));
				
			}
			
			if(liquidacionesCertificadoCreadas.size()!= solicitudesAsociadas.size()){
				throw new LiquidacionNoEfectuadaException("No se hicieron TODAS las liquidaciones de certificados");
			}
			
			SolicitudCertificadoMasivo scm = new SolicitudCertificadoMasivo();
			scm.setProceso(solicitud.getProceso());
			scm.setCirculo(solicitud.getCirculo());
			scm.setCiudadano(solicitud.getCiudadano());
			scm.setComentario(solicitud.getComentario());
                        scm.setUsuario(evento.getUsuarioNec());
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se existe un documento, se adjunta a la solicitud SolicitudCertificadoMasivo
                         * Caso Mantis  :   000941
                         */
                        if (solicitudcertmasivo != null && solicitudcertmasivo.getDocumento()!= null){
                            scm.setDocumento(solicitudcertmasivo.getDocumento());
                        }
                        
			LiquidacionTurnoCertificadoMasivo lcm = new LiquidacionTurnoCertificadoMasivo();
			lcm.setTipoCertificado(liquidacion.getTipoCertificado());
			lcm.setTipoTarifa(liquidacion.getTipoTarifa());
			lcm.setUsuario(evento.getUsuarioNec());
			
			lcm.setSolicitud(scm);
			scm.addLiquidacion(lcm);
			
			Iterator it2 = liquidacionesCertificadoCreadas.iterator();
			LiquidacionTurnoCertificado liq;
			SolicitudAsociada solAso;
			while(it2.hasNext()){
				liq = (LiquidacionTurnoCertificado)it2.next();
				solAso = new SolicitudAsociada();
				solAso.setSolicitudHija(liq.getSolicitud());
				scm.addSolicitudesHija(solAso);
			}
			
			
			Liquidacion liquidacionRta = hermod.liquidar(lcm);
			evRespuesta = new EvnRespCertificadoMasivo(EvnRespCertificadoMasivo.LIQUIDAR, liquidacionRta);
			if(ht.size()>0){
				evRespuesta.setValidacionesMasivos(ht);
			}
			
			
		} catch (HermodException e) {
			throw new LiquidacionNoEfectuadaException(e.getMessage(),e);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificadoMasivo.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		return evRespuesta;
		
	}
	
	/**
	 * Valida una matrícula para certificados masivos
	 * @param evento evento que tiene la información de la matrícula a validar
	 * @return retorna un objeto de EvnRespoCertificadoMasivo
	 * @throws EventoException 
	 */
	private EvnRespCertificadoMasivo validarMatriculaCertificadosMasivos(EvnCertificadoMasivo evento) throws EventoException {

		
		List validaciones = null;
		List matriculas = new ArrayList();
		String matricula = evento.getMatricula();
		matriculas.add(matricula);
		List listaCer= new Vector();
		//		creacion de la solicitud de certificados masivos
	
		  SolicitudCertificadoMasivo sol = new SolicitudCertificadoMasivo();
	
		  LiquidacionTurnoCertificadoMasivo l = new LiquidacionTurnoCertificadoMasivo();
	
		
	
		  TipoCertificado tipoCert = new TipoCertificado();
		  try{
		  	 listaCer = hermod.getTiposCertificado();
		  }catch (Throwable t) {
			throw new EventoException("Error obteniendo los tipos de certificado", t);
		  }
		  Iterator itlc = listaCer.iterator();
		  while (itlc.hasNext()) {
			TipoCertificado el = (TipoCertificado) itlc.next();
			  if (el.getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID)) {
				  tipoCert=el;
			  }
		  }
		  l.setTipoCertificado(tipoCert);
	
		sol.addLiquidacion(l);

		try {
			validaciones = hermod.getValidacionesSolicitud(sol);
		} catch (Throwable t) {
			throw new EventoException("Error recuperando la lista de validaciones para " + "tipo de certificado " + "INMEDIATO", t);
		}
		try {
			if (forseti.validarMatriculas(validaciones, matriculas)) {

				long numAnota = this.getNumeroAnotacionesFolio(matricula);

				boolean esMayorExt = false;
				esMayorExt = forseti.mayorExtensionFolio(matricula);
		
				 //this.esMayorExtension(matricula);
				/*
				if (numAnota > 150)
					esMayorExt = true;
				*/
		
				if (esMayorExt)
					Log.getInstance().debug(ANCertificadoMasivo.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::validarMatricula: el folio es de mayor extension");
				else
					Log.getInstance().debug(ANCertificadoMasivo.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::validarMatricula: el folio NO es de mayor extension");

				EvnRespCertificadoMasivo eventoRespuesta = new EvnRespCertificadoMasivo(matricula, EvnRespCertificadoMasivo.AGREGAR_VERIFICADO);

				if (esMayorExt)
					eventoRespuesta.setMayorExtension(CFolio.MAYOR_EXTENSION);
				else
					eventoRespuesta.setMayorExtension(CFolio.NO_MAYOR_EXTENSION);

				eventoRespuesta.setNumeroAnotaciones(String.valueOf(numAnota));
				

				
				return eventoRespuesta;
			}
		} catch (Throwable t) {
			throw new ValidacionMatriculaCertificadoHTException("Error validando la matricula " + evento.getMatricula(), t);
		}
		return null;
	}
	
	private long getNumeroAnotacionesFolio(String matricula) throws EventoException {

		Folio folio;
		long numAnota = -1;
	
		try {
			//folio = forseti.getFolioByMatricula(matricula);
			//String zonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();
	
			Log.getInstance().debug(ANCertificadoMasivo.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado::getNumeroAnotacionesFolio]: matricula=" + matricula);
			//Log.getInstance().debug(ANCertificadoMasivo.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado::getNumeroAnotacionesFolio]: zonaRegistral="+zonaRegistral);
	
			/*
			 Folio.ID fid = new Folio.ID();
			 fid.idMatricula = matricula;
			 fid.idZonaRegistral = zonaRegistral; 
			*/
			numAnota = forseti.getCountAnotacionesFolio(matricula);
			Log.getInstance().debug(ANCertificadoMasivo.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::getNumeroAnotacionesFolio: numero de anotaciones = " + numAnota);
	
		} catch (Throwable e) {
			Log.getInstance().error(ANCertificadoMasivo.class,e);
			throw new EventoException(e.getMessage(), e);
		}
	
		return numAnota;
	
	}
	
	//daniel
	private EvnRespCertificadoMasivo validarMatriculasCertificadosMasivos(EvnCertificadoMasivo evento) throws EventoException{

		List validaciones = null;
		List matriculas = evento.getMatriculasArchivo();
		String matricula = null;
		List listaCer= new Vector();
		//		creacion de la solicitud de certificados masivos
	
		  SolicitudCertificadoMasivo sol = new SolicitudCertificadoMasivo();
	
		  LiquidacionTurnoCertificadoMasivo l = new LiquidacionTurnoCertificadoMasivo();
	
		  TipoCertificado tipoCert = new TipoCertificado();
		  try{
		  	 listaCer = hermod.getTiposCertificado();
		  }catch (Throwable t) {
			throw new EventoException("Error obteniendo los tipos de certificado", t);
		  }
		  Iterator itlc = listaCer.iterator();
		  while (itlc.hasNext()) {
			TipoCertificado el = (TipoCertificado) itlc.next();
			  if (el.getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID)) {
				  tipoCert=el;
			  }
		  }
		  l.setTipoCertificado(tipoCert);
	
		sol.addLiquidacion(l);

		try {
			validaciones = hermod.getValidacionesSolicitud(sol);
		} catch (Throwable t) {
			throw new EventoException("Error recuperando la lista de validaciones para " + "tipo de certificado " + "INMEDIATO", t);
		}
		try {
			if (forseti.validarMatriculas(validaciones, matriculas)) {

				List numAnotas = new ArrayList();
				for(int i=0; i<matriculas.size(); i++){
					matricula = (String)matriculas.get(i);
					numAnotas.add(Long.toString(this.getNumeroAnotacionesFolio(matricula)));
				}

				EvnRespCertificadoMasivo eventoRespuesta = new EvnRespCertificadoMasivo(matriculas, evento.getCopiasMatriculas(),EvnRespCertificadoMasivo.AGREGAR_DE_ARCHIVO);

				return eventoRespuesta;
			}
		} catch (Throwable t) {
			throw new ValidacionMatriculaCertificadoHTException("Error validando las matriculas del archivo ", t);
		}
		return null;
	}


}