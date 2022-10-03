/*
 * Created on 22-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRespTurnoManualCertificadoPago;
import gov.sir.core.eventos.administracion.EvnTurnoManualCertificadoPago;
import gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.ForsetiException;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ANTurnoManualCertificadoPago extends SoporteAccionNegocio {

	/**
	 * Instancia de PrintJobs
	 */
	private PrintJobsInterface printJobs;

	/**
	 * Instancia de Forseti
	 */
	//private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Fenrir
	 */
	//private FenrirServiceInterface fenrir;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 *Constructor de la clase ANPago.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 * @throws EventoException
	 */
	public ANTurnoManualCertificadoPago() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			//forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			//fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio.", e);
		}

	}

	/**
	* Recibe un evento de seguridad y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnSeguridad</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespSeguridad</CODE>
	* @see gov.sir.core.eventos.comun.EvnSeguridad
	* @see gov.sir.core.eventos.comun.EvnRespSeguridad
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnTurnoManualCertificadoPago evento = (EvnTurnoManualCertificadoPago) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnTurnoManualCertificadoPago.VALIDAR)) {
			return validarPago(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualCertificadoPago.PROCESAR)) {
			return procesarPago(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualCertificadoPago.VERIFICAR_APLICACION)) {
			return verificarAplicacion(evento);
		}

		return null;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta verificarAplicacion(EvnTurnoManualCertificadoPago evento) throws EventoException {
		AplicacionPago aplicacionPago = evento.getAplicacionPago();
		DocumentoPago docPago = null;
		try {
			docPago = hermod.getDocumentosPagoExistente(aplicacionPago.getDocumentoPago());
		} catch (HermodException e) {
			Log.getInstance().error(ANTurnoManualCertificadoPago.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANTurnoManualCertificadoPago.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		if(docPago == null){
			return new EvnRespTurnoManualCertificadoPago(evento.getAplicacionPago(),true);
		}
		aplicacionPago.setDocumentoPago(docPago);
		return new EvnRespTurnoManualCertificadoPago(aplicacionPago,false);
	}

	/**
	 * Este metodo hace el llamado al negocio para que se procese el pago de una liquidacion
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnOficinas</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespOficinas</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta procesarPago(EvnTurnoManualCertificadoPago evento) throws EventoException {
		Pago pago = evento.getPago();
		Liquidacion liquidacionTemp = null;
		Solicitud solicitudTemp = null;

		//SE VALIDA QUE SI SE QUIERE PAGAR UN PAGO DE SOLICITUDES MASIVAS DE CERTIFICADOS o una solicitud
		//de registro con certificados asociados.
		//SE ENTRE AL MÉTODO procesarPagoMasivos PARA QUE GENERE UN RECIBO DE CERTIFICADO INDIVIDUAL,
		//YA QUE SI NO SÓLO IMPRIMIRÍA EL RECIBO DE UN TURNO DE CERTIFICADOS MASIVOS
		if (pago != null) {
			liquidacionTemp = pago.getLiquidacion();
		}

		if (liquidacionTemp.getSolicitud() != null) {
			solicitudTemp = liquidacionTemp.getSolicitud();
		}

		if (solicitudTemp instanceof SolicitudCertificadoMasivo) {
			return (procesarPagoMasivos(evento));
		}

		//SI ES UN PAGO DIFERENTE DE UN TURNO DE SOLICITUD MASIVA DE CERTIFICADOS CONTINUA EL SIGUIENTE PROCESO.

		pago = ((EvnRespTurnoManualCertificadoPago) validarPago(evento)).getPago();

		//Estacion estacion = evento.getEstacion();
		Turno turno = null;

		Usuario user = evento.getUsuarioSIR();
		pago.setUsuario(user);

		//String impresora = evento.getImpresora();

		EvnRespTurnoManualCertificadoPago evRespuesta = null;

		try {
			
			// Se crea el turno
			turno = hermod.crearTurnoManual(pago, evento.getAnio(), evento.getIdCirculo(), 
				"" + evento.getProceso().getIdProceso(), evento.getIdTurno(), evento.getFechaInicio(), user);
			
			// Asociar al turno las notas informativas definidas antes de la creación del turno
			// (Si existen).
			List listaNotasInformativas = evento.getListaNotasSinTurno();

			if (listaNotasInformativas != null) {

				//turno = evento.getPago().getLiquidacion().getSolicitud().getTurno();
				TurnoPk idTurno = new TurnoPk();
				idTurno.anio = turno.getAnio();
				idTurno.idCirculo = turno.getIdCirculo();
				idTurno.idTurno = turno.getIdTurno();
				idTurno.idProceso = turno.getIdProceso();

				for (int i = 0; i < listaNotasInformativas.size(); i++) {
					Nota notaTurno = (Nota) listaNotasInformativas.get(i);
					hermod.addNotaToTurno(notaTurno, idTurno);
				}
			}
			
			// Se añaden los TurnoHistoria de creación y de cierre automáticamente
			TurnoHistoria tHist;
			
			tHist = new TurnoHistoria();
			tHist.setActivo(false);
			tHist.setAnio(turno.getAnio());
			tHist.setFecha(turno.getFechaInicio());
			tHist.setIdAdministradorSAS(user.getUsername());
			tHist.setIdCirculo(turno.getIdCirculo());
			tHist.setIdProceso(turno.getIdProceso());
			tHist.setIdTurno(turno.getIdWorkflow());
			tHist.setUsuario(user);
			tHist.setUsuarioAtiende(user);
			
			if(solicitudTemp instanceof SolicitudRegistro) {
				tHist.setFase(CFase.REG_TMAN_CREACION);
			} else {
				tHist.setFase(CFase.CER_TMAN_CREACION);
			}
			
			hermod.addTurnoHistoriaToTurno(turno, tHist);
			
			//Si es un turno de Registro 
			if(solicitudTemp instanceof SolicitudRegistro) {
				String repuestaCalificacion = evento.getRespuestaCalificacion();
				tHist = new TurnoHistoria();
				tHist.setActivo(false);
				tHist.setAnio(turno.getAnio());
				tHist.setFecha(turno.getFechaInicio());
				tHist.setIdAdministradorSAS(user.getUsername());
				tHist.setIdCirculo(turno.getIdCirculo());
				tHist.setIdProceso(turno.getIdProceso());
				tHist.setIdTurno(turno.getIdWorkflow());
				tHist.setUsuario(user);
				tHist.setUsuarioAtiende(user);
				tHist.setFase(CFase.CAL_CALIFICACION);
				tHist.setFaseAnterior(CFase.REG_TMAN_CREACION);
				tHist.setRespuesta(repuestaCalificacion);
				hermod.addTurnoHistoriaToTurno(turno, tHist);
			}
			
			tHist = new TurnoHistoria();
			tHist.setActivo(false);
			tHist.setAnio(turno.getAnio());
			tHist.setFecha(turno.getFechaInicio());
			tHist.setIdAdministradorSAS(user.getUsername());
			tHist.setIdCirculo(turno.getIdCirculo());
			tHist.setIdProceso(turno.getIdProceso());
			tHist.setIdTurno(turno.getIdWorkflow());
			tHist.setUsuario(user);
			tHist.setUsuarioAtiende(user);
			
			if(solicitudTemp instanceof SolicitudRegistro) {
				tHist.setFase(CFase.REG_TMAN_CIERRE);
				tHist.setFaseAnterior(CFase.CAL_CALIFICACION);
			} else {
				tHist.setFase(CFase.CER_TMAN_CIERRE);
				tHist.setFaseAnterior(CFase.CER_TMAN_CREACION);
			}
			
			hermod.addTurnoHistoriaToTurno(turno, tHist);
			
			if(evento.getTurnoAnterior() != null && solicitudTemp instanceof SolicitudCertificado) {
				hermod.addCertificadoAsociadoToTurno(evento.getTurnoAnterior(), turno);
			}
			
			evRespuesta = new EvnRespTurnoManualCertificadoPago(turno);
			
		} catch (PagoInvalidoException e) {
			throw e;
		} catch (HermodException e) {
			throw new PagoNoProcesadoException("Ocurrio un error procesando el pago ", e);
		} catch (ForsetiException e) {
			throw new PagoNoProcesadoException("Ocurrio un error procesando el pago ", e);
		} catch (PrintJobsException e) {
			if (turno != null) {
				throw new ErrorImpresionException("No se pudo imprimir el recibo:" + e.getMessage(), turno.getIdWorkflow());
			}
		} catch (Throwable e) {
			Log.getInstance().error(ANTurnoManualCertificadoPago.class,"Ha ocurrido una excepcion inesperada ", e);
			throw new EventoException("Excepcion inesperada", e);
		}

		return evRespuesta;
	}

	/**
	 * Procesa el pago generando un turno y el imprimible
	 *
	 * @param evento
	 * @param pago
	 * @param estacion
	 * @param user
	 * @param impresora
	 * @return Turno generado del procesamiento del pago
	 * @throws Throwable
	 */
	/*private Turno generarPagoConTurno(EvnTurnoManualCertificadoPago evento, Pago pago, Estacion estacion, Usuario user, String impresora) throws Throwable {

		Turno turno = hermod.crearTurnoManual(pago, evento.getAnio(), evento.getIdCirculo(), 
			"" + evento.getProceso().getIdProceso(), evento.getIdTurno(), evento.getFechaInicio(), user);
		
		Solicitud solicitud = turno.getSolicitud();
		List liquidaciones = solicitud.getLiquidaciones();
		//String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
        
        EstacionRecibo.ID estacionID = new EstacionRecibo.ID();
        estacionID.idEstacion = estacion.getEstacionId();
        String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionID));
        pago.setNumRecibo(numRecibo);
        
        Pago.ID pagoID = new Pago.ID();
        pagoID.idLiquidacion = pago.getIdLiquidacion();
        pagoID.idSolicitud = pago.getIdSolicitud();
        hermod.setNumeroReciboPago(pagoID, numRecibo);
        
		pago.getLiquidacion().getSolicitud().setTurno(turno);

		Circulo circulo = evento.getCirculo();
		String fechaImpresion = this.getFechaImpresion();
		ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion);
		Liquidacion liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);

		if (liquidacion instanceof LiquidacionTurnoCertificado) {
			LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
			String idTipoCertificado = liquidaCert.getTipoCertificado().getIdTipoCertificado();
			impRec.setTipoCertificadoId(idTipoCertificado);
		}

		//verifica si el certificado es de mayor extension.
		if (solicitud instanceof SolicitudCertificado) {
			boolean esMayorExtension = false;
			long numAnota = -1;
			Folio.ID fid = this.getFolio_ID(solicitud);

			if (fid != null) {
				numAnota = forseti.getCountAnotacionesFolio(fid, CCriterio.TODAS_LAS_ANOTACIONES, null);
				esMayorExtension = forseti.mayorExtensionFolio(fid.idMatricula);
			} else {
				Log.getInstance().error(ANTurnoManualCertificadoPago.class,"NO FUE POSIBLE DETERMINAR SI EL FOLIO ES DE MAYOR EXTENSION");

				//se asume false, pero no se sabe.
				esMayorExtension = false;
			}

			impRec.setMayorExtension(esMayorExtension);
		}

		return turno;
	}*/

	/*private void pagoFocotopia_imprimibleRecibo(gov.sir.core.negocio.modelo.Circulo circulo, gov.sir.core.negocio.modelo.Pago pago, gov.sir.core.negocio.modelo.Turno turno, gov.sir.core.negocio.modelo.Liquidacion liquidacion, gov.sir.core.negocio.modelo.SolicitudFotocopia solicitud, List pagoAplicaciones, String sessionId) throws Throwable {
		// TODO: TEST: pago.getAplicacionPagos()

		String fechaImpresion = this.getFechaImpresion();

		//Pago pago = new gov.sir.core.negocio.modelo.imprimibles.util.DefaultPago( liquidacion, turno );

		//ImprimibleRecibo impRec;
		//impRec = new ImprimibleFotocopiaComprobanteSolicitud( pago, circulo, fechaImpresion );
		// impRec = new ImprimibleRecibo( evento.getTurno(), circulo, fechaImpresion );

		// ImprimibleRecibo impRec;
		// impRec = new ImprimibleRecibo( turno, liquidacion, pago, solicitud, circulo, fechaImpresion );

		Vector tempListOfAplicaciones = new java.util.Vector();
		Iterator iterator = pagoAplicaciones.iterator();

		// solo wrapping para enviarlos al cliente
		for (; iterator.hasNext();) {
			tempListOfAplicaciones.add(iterator.next());
		}

		ImprimibleFotocopiaPago_SolicitudData impRec;
		impRec = new ImprimibleFotocopiaPago_SolicitudData(turno, solicitud, circulo, liquidacion, pago, tempListOfAplicaciones);
		impRec.setFechaImpresionServidorFormatted(fechaImpresion);

		String uid = sessionId;

		//IMPRIMIR EL RECIBO

		String[] legals = null;
		impRec.setFootLegendText(legals);

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			} else {
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer esperaInt = new Integer(esperaImpresion);
				espera = esperaInt.intValue();
			} else {
				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} catch (Throwable t) {
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();

		}

		Bundle bundle = new Bundle(impRec);

		for (int i = 0; i < maxIntentos; i++) {
			try {
				Thread.sleep(espera);

				//se manda a imprimir el recibo por el identificador unico de usuario
				printJobs.enviar(uid, bundle);

				//si ok-->termina el ciclo.
				i = maxIntentos + 1;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}*/
	// block:eof

	/**
	 * Este metodo hace el llamado al negocio para que se procese el pago de una liquidacion
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnOficinas</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespOficinas</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta procesarPagoMasivos(EvnTurnoManualCertificadoPago evento) throws EventoException {
		validarPago(evento);


		Turno turno = null;
		//Turno turnoIndividuales = null;
		Estacion estacion = evento.getEstacion();
		Usuario user = evento.getUsuarioSIR();
		Rol rol = evento.getRol();
		String impresora = evento.getImpresora();
		Pago pagoCertificado = null;
		String id_matricula = "";
		Hashtable validaciones = evento.getValidacionesMasivos();

		if (validaciones == null) {
			validaciones = new Hashtable();
		}

		//EL PAGO ES UTILIZADO PARA LIQUIDAR EL PROCESO DE CERTIFICADOS MASIVOS.
		Pago pago = evento.getPago();
		pago.setUsuario(user);

		Liquidacion liquidacion = pago.getLiquidacion();

		Solicitud sol = liquidacion.getSolicitud();

		pago.setLiquidacion(liquidacion);
		pago.setIdSolicitud(sol.getIdSolicitud());

		Hashtable ht = new Hashtable();

		try {
			validarPago(evento);
			pago.setUsuario(user);

			turno = hermod.procesarPago(pago, estacion.getEstacionId(), impresora, user, rol);

			try {
				//Asociar al turno las notas informativas definidas antes de la creación del turno. (Si existen).
				List listaNotasInformativas = evento.getListaNotasSinTurno();

				if (listaNotasInformativas != null) {
					TurnoPk idTurno = new TurnoPk();
					idTurno.anio = turno.getAnio();
					idTurno.idCirculo = turno.getIdCirculo();
					idTurno.idTurno = turno.getIdTurno();
					idTurno.idProceso = turno.getIdProceso();

					for (int i = 0; i < listaNotasInformativas.size(); i++) {
						Nota notaTurno = (Nota) listaNotasInformativas.get(i);
						hermod.addNotaToTurno(notaTurno, idTurno);
					}
				}

				//Solicitud solicitud = turno.getSolicitud();
				//List liquidaciones = solicitud.getLiquidaciones();
				//String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
                EstacionReciboPk estacionID = new EstacionReciboPk();
                estacionID.idEstacion = estacion.getEstacionId();
                String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionID,user,turno.getIdProceso()));
				pago.setNumRecibo(numRecibo);
				pago.getLiquidacion().getSolicitud().setTurno(turno);
                
                PagoPk pagoID = new PagoPk();
                pagoID.idLiquidacion = pago.getIdLiquidacion();
                pagoID.idSolicitud = pago.getIdSolicitud();
                hermod.setNumeroReciboPago(pagoID, numRecibo);

				Circulo circulo = evento.getCirculo();
				String fechaImpresion = this.getFechaImpresion();
				ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
				impRec.setValidaciones(evento.getValidacionesMasivos());

				String uid = evento.getUID();

				//IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
				//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
				int maxIntentos;
				int espera;

				//INGRESO DE INTENTOS DE IMPRESION
				try {
					String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);

					if (intentosImpresion != null) {
						Integer intentos = new Integer(intentosImpresion);
						maxIntentos = intentos.intValue();
					} else {
						Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
						maxIntentos = intentosDefault.intValue();
					}

					//INGRESO TIEMPO DE ESPERA IMPRESION
					String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);

					if (intentosImpresion != null) {
						Integer esperaInt = new Integer(esperaImpresion);
						espera = esperaInt.intValue();
					} else {
						Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
						espera = esperaDefault.intValue();
					}
				} catch (Throwable t) {
					Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
					maxIntentos = intentosDefault.intValue();

					Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
					espera = esperaDefault.intValue();
				}

				Bundle bundle = new Bundle(impRec);
                                String copyActive = hermod.getCopiaImp(impRec.getCirculo().getIdCirculo());
                                if(copyActive.equals(CHermod.ACTIVE)){
                                     bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                                }

				try {

					//se manda a imprimir el recibo por el identificador unico de usuario
					printJobs.enviar(uid, bundle, maxIntentos, espera);

				} catch (Throwable t) {
					t.printStackTrace();
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}

			if ((liquidacion != null) && liquidacion instanceof LiquidacionTurnoCertificadoMasivo) {
				SolicitudCertificadoMasivo solicitudCertificadoMasivo = (SolicitudCertificadoMasivo) liquidacion.getSolicitud();

				if ((solicitudCertificadoMasivo != null) && !solicitudCertificadoMasivo.getSolicitudesHijas().isEmpty()) {
					Iterator it = solicitudCertificadoMasivo.getSolicitudesHijas().iterator();

					while (it.hasNext()) {
						id_matricula = "";
						ht = new Hashtable();
						ht.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioSIR());
						ht.put(CEstacion.ESTACION_ID, evento.getEstacion().getEstacionId());

						if (rol != null) {
							ht.put(CRol.ID_ROL, rol.getRolId());
						}

						SolicitudAsociada solicitudAsociada = (SolicitudAsociada) it.next();
						SolicitudCertificado solicitudCertificado = (SolicitudCertificado) solicitudAsociada.getSolicitudHija();

						id_matricula = solicitudCertificado.getMatriculaNoExistente();

						if (((id_matricula == null) || id_matricula.equals("")) && (solicitudCertificado != null) && !solicitudCertificado.getSolicitudFolios().isEmpty()) {
							SolicitudFolio solFolio = (SolicitudFolio) solicitudCertificado.getSolicitudFolios().get(0);
							id_matricula = solFolio.getIdMatricula();
						}

						if ((solicitudCertificado != null) && !solicitudCertificado.getLiquidaciones().isEmpty()) {
							LiquidacionTurnoCertificado liquidacionCertificado = (LiquidacionTurnoCertificado) solicitudCertificado.getLiquidaciones().get(0);

							//SE SACA LA LIQUIDACION DEL CERTIFICADO INDIVIDUAL Y SE LLAMA AL MÉTODO PROCESAR PAGO
							DocPagoEfectivo docPago = new DocPagoEfectivo(liquidacionCertificado.getValor());

							AplicacionPago appEfectivo = new AplicacionPago();
							appEfectivo.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
							appEfectivo.setIdSolicitud(solicitudCertificado.getIdSolicitud());
							appEfectivo.setValorAplicado(liquidacionCertificado.getValor());
							appEfectivo.setDocumentoPago(docPago);

							pagoCertificado = new Pago(liquidacionCertificado, null);
							pagoCertificado.addAplicacionPago(appEfectivo);
							pagoCertificado.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
							pagoCertificado.setIdSolicitud(solicitudCertificado.getIdSolicitud());
							pagoCertificado.setLiquidacion(liquidacionCertificado);
							pagoCertificado.setUsuario(evento.getUsuarioSIR());

							if ((id_matricula != null) && !id_matricula.equals("")) {
								List valid = (List) validaciones.get(id_matricula);

								if ((valid != null) && (valid.size() > 0)) {
									//ht.put(id_matricula, valid);
									ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, valid);
								}

								if (valid == null) {
									valid = new ArrayList();
									ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, valid);
								}
							}

							try {
								/*turnoIndividuales = */hermod.procesarPagoMasivos(pagoCertificado, ht);
							} catch (Throwable t) {
								Log.getInstance().error(ANTurnoManualCertificadoPago.class,"ERROR AL CREAR EL TURNO" + t.getMessage());
							}
						}
					}
				}
			}
		}
		/*catch (HermodException e) {
				throw new PagoNoProcesadoException("Ocurrio un error procesando el pago (hermod)", e);
		}
		catch (ForsetiException e) {
				throw new PagoNoProcesadoException("Ocurrio un error procesando el pago (forseti)", e);
		}
		catch (PrintJobsException e) {
				if (turno != null) {
						throw new ErrorImpresionException("No se pudo imprimir el recibo:" + e.getMessage(), turno.getIdWorkflow());
				}
		}*/
		catch (Throwable e) {
			Log.getInstance().error(ANTurnoManualCertificadoPago.class,"Ha ocurrido una excepcion inesperada ", e);
			throw new EventoException("Excepcion inesperada", e);
		}

		EvnRespTurnoManualCertificadoPago evRespuesta = new EvnRespTurnoManualCertificadoPago(turno);

		return evRespuesta;
	}

	/**
	  * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	  * con los parametros asignados.
	  * @param turno
	  * @param impRecibo
	  * @param uid
	  * @return Indica si la impresion fue exitosa o no
	  */
	/*private boolean imprimirRecibo(ImprimibleRecibo impRecibo, Turno turno, String uid) {
		//String circulo = turno.getIdCirculo();
		Bundle b = new Bundle(impRecibo);

		//IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);

			if (intentosImpresion != null) {
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			} else {
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);

			if (intentosImpresion != null) {
				Integer esperaInt = new Integer(esperaImpresion);
				espera = esperaInt.intValue();
			} else {
				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} catch (Throwable t) {
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();
		}

		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos
		boolean ok = false;
		int i = 0;

		while ((i < maxIntentos) && !ok) {
			try {
				printJobs.enviar(uid, b);
				logger.debug("UID=" + uid + " Intento: " + i);

				//Se hace una espera de acuerdo con el tiempo de espera recibido en esperado.
				Thread.sleep(esperado);
				ok = true;
			} catch (Throwable t) {
				t.printStackTrace();
				ok = false;
			}

			i++;
		}

		return ok;
	}*/

	/**
	 * Este metodo hace el llamado al negocio para que se validen las aplicaciones de pagoS
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnOficinas</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespOficinas</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta validarPago(EvnTurnoManualCertificadoPago evento) throws EventoException {
		EvnRespTurnoManualCertificadoPago evRespuesta = null;
		Pago pago = evento.getPago();
		String tipo = evento.getProceso().getNombre();
		tipo = (tipo != null) ? tipo.replaceAll(" ", "_") : null;

		try {
			if (validarPago(pago, hermod.getRangoAceptacionPago(tipo))) {
				pago = hermod.validarPago(evento.getPago());
				evRespuesta = new EvnRespTurnoManualCertificadoPago(pago, hermod.getRangoAceptacionPago(tipo));
			}
		} catch (PagoInvalidoException e) {
			throw e;
		} catch (HermodException e) {
			throw new PagoInvalidoException("Pago Invalido", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualCertificadoPago.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException("Excepcion inesperada", e);
		}

		return evRespuesta;
	}

	/**
	 * Este metodo valida que todas las aplicaciones de pago tengan saldo valido
	 * @param pago EL pago que se va a validar
	 * @param precision El rango de tolerancia
	 * @return boolean true si el pago es valido
	 * @throws PagoInvalidoException si el pago no es valido
	 */
	private boolean validarPago(Pago pago, double precision) throws PagoInvalidoException {
		List pagos = pago.getAplicacionPagos();
		Liquidacion liquidacion = pago.getLiquidacion();
		double valorLiquidado = liquidacion.getValor();
		double valorPagado = 0;
	
		Iterator it = pagos.iterator();

		while (it.hasNext()) {
			AplicacionPago apl = (AplicacionPago) it.next();
			valorPagado += apl.getValorAplicado();
		}
	
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Liquidado" + valorLiquidado);
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Pagado" + valorPagado);
	
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Liquidado" + NumberFormat.getInstance().format(valorLiquidado) );
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Pagado" + NumberFormat.getInstance().format(valorPagado) );
	
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Liquidado" + NumberFormat.getInstance().format(valorLiquidado) );
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Pagado" + NumberFormat.getInstance().format(valorPagado) );
	
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.00");


		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Liquidado" + df.format(valorLiquidado));
		Log.getInstance().info(ANTurnoManualCertificadoPago.class,"Valor Pagado" + df.format(valorPagado));
				
  

			//SI ESTAN HACIENDO UNA EVALUACION CONTRA UN NIVEL DE PRECISION TIENEN QUE
		//EVALUAR UN RANGO !
		/*
		if ((valorPagado + precision) != valorLiquidado) {
			throw new PagoInvalidoException(
				"El valor a pagar no coincide con el valor Liquidado");
		}*/
		if ((valorPagado >= (valorLiquidado - precision)) && (valorPagado <= (valorLiquidado + precision)))
			Log.getInstance().debug(ANTurnoManualCertificadoPago.class,"PAGO OK");
		else
			throw new PagoInvalidoException("El valor a pagar no coincide con el valor Liquidado");

		return true;
	}

	/**
	 * Retorna el ID de la primera matrícula
	 * asociada a la solicitud. Si es un certificado solo
	 * hay una matrícula.
	 * @param solicitud
	 * @return Matricula asociada a la solicitud
	 */
	/*private String getMatricula(Solicitud solicitud) {
		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			matriculas.add(sol.getFolio().getIdMatricula());
		}

		String idMatricula = (String) matriculas.get(0);

		return idMatricula;
	}*/

	/**
	 * Retorna el ID del folio asociado a la solicitud
	 * @param solicitud
	 * @return Llave primaria del Folio
	 */
	/*private Folio.ID getFolio_ID(Solicitud solicitud) {
		List solFolio = solicitud.getSolicitudFolios();

		Iterator itSolFolio = solFolio.iterator();
		List matriculas = new Vector();

		Folio.ID fid;

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			fid = new Folio.ID();
			fid.idMatricula = sol.getFolio().getIdMatricula();
			fid.idZonaRegistral = sol.getFolio().getIdZonaRegistral();
			matriculas.add(fid);
		}

		//String idMatricula = (String)matriculas.get(0);
		try {
			fid = (Folio.ID) matriculas.get(0);
		} catch (Throwable e) {
			Log.getInstance().error(ANTurnoManualCertificadoPago.class,e);
			fid = null;
		}

		return fid;
	}*/

	/**
	 * Metodo que retorna un numero con un "0" antes en caso de ser menor
	 * que 10.
	 * @param i el numero.
	 * @return Cadena con el número en dos digitos
	 */
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}

		return (new Integer(i)).toString();
	}

	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return Cadena con la fecha de impresión
	 */
	protected String getFechaImpresion() {
		Calendar c = Calendar.getInstance();
		int dia;
		int ano;
		int hora;
		String min;
		String seg;
		String mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);

		if (hora > 12) {
			hora -= 12;
		}

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano + " a las " + formato(hora) + ":" + min + ":" + seg + " " + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}
}
