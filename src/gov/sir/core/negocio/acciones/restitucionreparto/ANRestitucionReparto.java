package gov.sir.core.negocio.acciones.restitucionreparto;

import gov.sir.core.eventos.restitucionreparto.EvnRespRestitucionReparto;
import gov.sir.core.eventos.restitucionreparto.EvnRestitucionReparto;
import gov.sir.core.negocio.acciones.excepciones.ANRestitucionRepartoException;
import gov.sir.core.negocio.acciones.excepciones.ANRevocatoriaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleResolucionRestitucion;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.HermodException;
import gov.sir.hermod.gdocumental.integracion.SGD;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import org.auriga.util.ExceptionPrinter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnRestitucionReparto</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespRestitucionReparto</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow del proceso de restitución de reparto notarial y llamar a los servicios
 * que se requieren en cada fase del proceso.
 * @author ppabon
 */
public class ANRestitucionReparto extends SoporteAccionNegocio {

	/**
	* Instancia de Forseti
	*/
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de PrintJobsInterface
	 */
	private PrintJobsInterface printJobs;


	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 *Constructor de la clase ANCorrección.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANRestitucionReparto() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod",e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio forseti",e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
	}

	/**
	* Recibe un evento de seguridad y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnRestitucionReparto</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespRestitucionReparto</CODE>
	* @see gov.sir.core.eventos.comun.EvnRestitucionReparto
	* @see gov.sir.core.eventos.comun.EvnRespRestitucionReparto
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnRestitucionReparto evento = (EvnRestitucionReparto) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnRestitucionReparto.CREAR_SOLICITUD)) {
			return crearSolicitud(evento);
		} else if (evento.getTipoEvento().equals(EvnRestitucionReparto.CONSULTAR_MINUTA)) {
			return consultarMinutas(evento);
		} else if (evento.getTipoEvento().equals(EvnRestitucionReparto.ANALISIS_RESTITUCION)) {
			return analizarRestitucion(evento);
		} else if (evento.getTipoEvento().equals(EvnRestitucionReparto.NOTIFICAR_CIUDADANO)) {
			return notificarCiudadano(evento);
		} else if (evento.getTipoEvento().equals(EvnRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION)) {
			return consultarTurnosAnalisisRestitucion(evento);
		}

		return null;
	}


	/**
	 * Crea una instancia del proceso de restitución de reparto notarial.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespRestitucionReparto crearSolicitud(EvnRestitucionReparto evento) throws EventoException {

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Estacion estacion = evento.getEstacion();
		Rol rol = evento.getRol();
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = evento.getUsuarioSIR();

		Solicitud solicitud = null;
		Turno turno = null;
		LiquidacionTurnoRestitucionReparto liquidacion = new LiquidacionTurnoRestitucionReparto();

		try {
			
			/** inicia veriricar si el turno ya fue entregado **/
			Turno turnoARestituir = hermod.getTurnobyWF(evento.getSolicitudRestitucionReparto().getTurnoAnterior().getIdWorkflow());
			
			if (CTurno.TURNO_ANULADO.equals(turnoARestituir.getAnulado()))
			{
				throw new LiquidacionNoEfectuadaException("El turno se encuentra anulado");
			}
			
			List th = turnoARestituir.getHistorials();
			
			boolean turnoEntregado = turnoARestituir.getFechaFin() != null;
			
			
			
			if (!turnoEntregado)
			{
				throw new LiquidacionNoEfectuadaException("El turno no ha sido entregado");
			}
			
			/** termina veriricar si el turno ya fue entregado **/
			
			/** inicia verificar si el turno ya fue restituido**/
			List solicitudesTurno = hermod.getSolicitudesByTurnoAnterior(evento.getSolicitudRestitucionReparto().getTurnoAnterior());
		
			if (solicitudesTurno != null)
			{
				boolean tieneRestitucion = false;
				for (Iterator i = solicitudesTurno.iterator(); i.hasNext() && !tieneRestitucion;)
				{
					Solicitud solTurno = (Solicitud)i.next();
					if (Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_RESTITUCION) == solTurno.getProceso().getIdProceso())
					{
						SolicitudPk solID = new SolicitudPk();
						solID.idSolicitud = solTurno.getIdSolicitud();
						Turno turnoSoltmp = hermod.getTurnoBySolicitud(solID);
						Turno turnoAsociado = hermod.getTurnobyWF(turnoSoltmp.getIdWorkflow());
												
						th = turnoAsociado.getHistorials();
						
						if (th != null)
						{
							for (Iterator j = th.iterator(); j.hasNext();)
							{
								TurnoHistoria historia = (TurnoHistoria)j.next();
								if (CFase.RES_ENTREGA.equals(historia.getFase()))
								{
									tieneRestitucion = true;
								}
							}
						}
					}
				}
				if (tieneRestitucion)
				{
					throw new LiquidacionNoEfectuadaException("El turno ya fue restituido");
				}
			}
			/** termina verificar si el turno ya fue restituido**/
			
			
			solicitud = (Solicitud) hermod.crearSolicitud(evento.getSolicitudRestitucionReparto());

			if (solicitud == null) {
				throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
			}

			SolicitudRestitucionReparto solicitudRestitucionReparto = (SolicitudRestitucionReparto) solicitud;

			liquidacion.setSolicitud(solicitudRestitucionReparto);
			liquidacion.setUsuario(evento.getUsuarioSIR());
			LiquidacionTurnoRestitucionReparto auxLiquidacion = (LiquidacionTurnoRestitucionReparto) hermod.liquidar(liquidacion);

			DocPagoEfectivo docPago = new DocPagoEfectivo(liquidacion.getValor());

			AplicacionPago appEfectivo = new AplicacionPago();
			appEfectivo.setValorAplicado(liquidacion.getValor());
			appEfectivo.setDocumentoPago(docPago);

			Pago pago = new Pago(auxLiquidacion, null);
			pago.addAplicacionPago(appEfectivo);
			pago.setLiquidacion(auxLiquidacion);
			pago.setUsuario(evento.getUsuarioSIR());

			try {
				if (validarPago(pago, hermod.getRangoAceptacionPago(solicitudRestitucionReparto.getProceso().getNombre()))) {
					pago = hermod.validarPago(pago);
				}
			} catch (HermodException e) {
				throw new PagoInvalidoException(e.getMessage() ,e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANRestitucionReparto.class,"Ha ocurrido una excepcion inesperada, al validar el pago " + printer.toString());
				throw new EventoException(e.getMessage(),e);
			}

			try {
				turno = hermod.procesarPago(pago, estacion.getEstacionId(), null, usuarioSIR, null , false );
				//turno = hermod.procesarPago(pago, null);

				List liquidaciones = turno.getSolicitud().getLiquidaciones();
				//String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
                //EstacionRecibo.ID estacionReciboID = new EstacionRecibo.ID();
                //estacionReciboID.idEstacion = estacion.getEstacionId();
                //String numRecibo = String.valueOf(hermod.getNextNumeroReciboSinAvanzar(estacionReciboID));

				//logger.debug("SIZE LIQU :" + liquidaciones.size());
				//pago.setNumRecibo(numRecibo);

                //Pago.ID pagoID = new Pago.ID();
                //pagoID.idLiquidacion = pago.getIdLiquidacion();
                //pagoID.idSolicitud = pago.getIdSolicitud();
                //hermod.setNumeroReciboPago(pagoID, numRecibo);

				pago.getLiquidacion().getSolicitud().setTurno(turno);

				Circulo circulo = evento.getCirculo();

				String fechaImpresion = this.getFechaImpresion();
				ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion,CTipoImprimible.RECIBO);



                                // Bug 3479
                                // :: ProcId: RepartoNotarial
                                // (usuario que genera la solicitud)
                                // jxpath-search:.

                                ImprimibleRecibo imprimible;
                                imprimible = impRec;

                                // realizar busqueda de usuario
                                String local_UsuarioGeneraRecibo = "";


                                local_SearchImpl_jx: {
                                   // Cambio, no es el usuario de solicitud sino el usuario
                                   // que se envia para afectar el wf
                                   //

                                   // Bug 5223
                                   gov.sir.core.negocio.modelo.Usuario local_User = usuarioSIR;
                                   local_UsuarioGeneraRecibo = print_FootUtils_BuildUserName( local_User );

                                } // :searchImpl_jx


                                imprimible.setUsuarioGeneraRecibo( local_UsuarioGeneraRecibo );






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
                                String copyActive = hermod.getCopiaImp(circulo.getIdCirculo());
                                if(copyActive.equals(CHermod.ACTIVE)){
                                     bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                                }
				try {
					if (evento.isImprimible())
						//se manda a imprimir el recibo por el identificador unico de usuario
						printJobs.enviar(uid, bundle, maxIntentos, espera);
				} catch (Throwable t) {
					t.printStackTrace();
				}
                            /**
                              * @author Fernando Padilla Velez
                              * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa SGD,
                              *         Se borran estan lineas, ya que se realizó refactoring al proceso y ya no son necesarias.
                              */
                            SGD sgd = new SGD(turno, usuarioSIR.getUsername());
                            sgd.enviarTurnoReparto();

			} catch (HermodException e1) {
				throw new PagoNoProcesadoException(e1.getMessage(), e1);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANRestitucionReparto.class,"Ha ocurrido una excepcion inesperada, al procesar pago " + printer.toString());
				throw new EventoException(e.getMessage(),e);
			}
		} catch (HermodException e) {
			e.printStackTrace(System.out);
			throw new LiquidacionNoEfectuadaException(e.getMessage(),e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRestitucionReparto.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new LiquidacionNoEfectuadaException("Error creando solicitud",e);
		}

		EvnRespRestitucionReparto eventoRespuesta = new EvnRespRestitucionReparto(evento.getUsuario(), turno, EvnRespRestitucionReparto.CREAR_SOLICITUD);

		return eventoRespuesta;
	}

   // -----------------------------------------------------------------------------
   private String
   print_FootUtils_BuildUserName( long userId ) {
      return "" + userId;
   } // end method

   private String
   print_FootUtils_BuildUserName( Long userId ) {
      if( null == userId ) {
         return getNullableString( true );
      }
      return print_FootUtils_BuildUserName( userId.longValue() ) ;
   } // end method

   private String
   print_FootUtils_BuildUserName( gov.sir.core.negocio.modelo.Usuario user ) {
      if( null == user ) {
         return getNullableString( true );
      }
      return print_FootUtils_BuildUserName( user.getIdUsuario() ) ;
   } // end method


   public static String
   Nvl( String string , String replaceIfNull ) {
      return ( null == string )?( replaceIfNull ):( string );
   } // end-method: Nvl

   public static String
   getNullableString( boolean treatBlankAsNull ) {
      return( ( treatBlankAsNull )?( "" ):( null ) );
   } // end-method: Nvl

// -----------------------------------------------------------------------------

	/**
	 * Crea una instancia del proceso de restitución de reparto notarial.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespRestitucionReparto consultarMinutas (EvnRestitucionReparto evento) throws EventoException {

		List minutas = null;
		try {
			minutas = hermod.getMinutasByTurnoWF(evento.getTurno().getIdWorkflow());
		} catch (HermodException e) {
			Log.getInstance().error(ANRestitucionReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANRestitucionReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		if (minutas!=null){
			Iterator itMinutas=minutas.iterator();
			while(itMinutas.hasNext()){
				Minuta minuta=(Minuta)itMinutas.next();
				try {
					SolicitudPk oid=new SolicitudPk();
					oid.idSolicitud=minuta.getIdMinuta();
					Turno turno=hermod.getTurnoBySolicitud(oid);
					Solicitud sol=hermod.getSolicitudById(minuta.getIdMinuta());
					if (sol!=null && turno!=null){
						minuta.setSolicitud(sol);
						sol.setTurno(turno);
					}
				} catch (Throwable e) {
					Log.getInstance().error(ANRestitucionReparto.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				}
			}
		}
		EvnRespRestitucionReparto respuesta = new EvnRespRestitucionReparto(armarTabla(minutas), EvnRespRestitucionReparto.TABLA_RESTITUCION_REPARTO);
		return respuesta;

	}


	/**
	 * Este método valida que todas las aplicaciones de pago tengan saldo válido
	 * @param pago EL pago que se va a validar
	 * @param precision El rango de tolerancia
	 * @return boolean true si el pago es válido
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

		if ((valorPagado + precision) < valorLiquidado) {
			throw new PagoInvalidoException("El valor a pagar no coincide con el valor Liquidado");
		}

		return true;
	}


	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
	protected String getFechaImpresion() {

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

		String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano + " a las " + formato(hora) + ":" + min + ":" + seg + " " + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

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



	/**
	 * Avanza la fase de análisis de restitución ya sea aprobandola o negandola.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta analizarRestitucion(EvnRestitucionReparto evento) throws EventoException {

		List turnosidWorkflow = evento.getTurnoRestitucion();
		Iterator itturnosidWorkflow = turnosidWorkflow.iterator();
		List datosTurnoImprimible = new ArrayList();
		try {
			while (itturnosidWorkflow.hasNext()) {
				String idwf = (String)itturnosidWorkflow.next();
				Turno turnoRestitucion = hermod.getTurnobyWF(idwf);
				analizarRestitucionByTurno(evento,turnoRestitucion);
				List dTurno = new ArrayList();
				
				SolicitudRestitucionReparto solRest = (SolicitudRestitucionReparto)turnoRestitucion.getSolicitud();
				
				dTurno.add((String) solRest.getTurnoAnterior().getIdWorkflow());
				dTurno.add((String) turnoRestitucion.getIdWorkflow());
				
				Turno turnoAnterior = null;
				turnoAnterior = hermod.getTurnobyWF(solRest.getTurnoAnterior().getIdWorkflow());
				
				dTurno.add((Turno) turnoAnterior);
				dTurno.add((String) solRest.getCausalRestitucion().getNombre());
				
				datosTurnoImprimible.add((List) dTurno );
	
			}
		} catch (HermodException e) {
			throw new ANRestitucionRepartoException("No se pudo procesar el análisis de restitución ", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRestitucionReparto.class,"No se pudo procesar el análisis de restitución " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
		
		String uid = evento.getUID();
		Circulo circulo = evento.getCirculo();
		Calendar c = Calendar.getInstance();

		int maxIntentos;
		int espera;
		//SE OBTIENE EL NÚMERO DE INTENTOS DE IMPRESIÓN
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

		//SE IMPRIME EL ACTA, LOS PARAMETROS DE CIUDAD, DIRECTOR Y COORDINADOR DEBEN CAMBIARSE POR LOS DE LOS NOMBRES REALES.
		 
	    String RESOLUCION_AFIRMATIVA = "LA RESTITUCIÓN DE REPARTO NOTARIAL HA SIDO APROBADA Y LE SERA ASIGNADO UN NUEVO TURNO EN EL PRÓXIMO REPARTO.";
     	String RESOLUCION_NEGATIVA =   "LA RESTITUCIÓN DE REPARTO NOTARIAL HA SIDO NEGADA.";

		String resumenResolucion = "";
		if( evento.getRespuestaWF().equals(EvnRestitucionReparto.CONFIRMAR)){
			resumenResolucion = RESOLUCION_AFIRMATIVA;
		}else{
			resumenResolucion = RESOLUCION_NEGATIVA;
		}

		ImprimibleResolucionRestitucion impResolucion = new ImprimibleResolucionRestitucion(datosTurnoImprimible, circulo, c.getTime(),  evento.getSolicitudRestitucionReparto().getResolucion(), evento.getSolicitudRestitucionReparto().getFechaResolucion(), evento.getSolicitudRestitucionReparto().getObservaciones(), resumenResolucion,CTipoImprimible.RESOLUCION);
		Bundle bundleClasificacion = new Bundle(impResolucion);

		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundleClasificacion, maxIntentos, espera);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		Turno turno = (Turno) evento.getTurno();
		EvnRespRestitucionReparto evRespuesta = null;
		evRespuesta = new EvnRespRestitucionReparto(evento.getUsuario(), turno, EvnRespRestitucionReparto.NOTIFICAR_CIUDADANO);

		return evRespuesta;
	}
	

	/**
	 * Avanza la fase de análisis de restitución ya sea aprobandola o negandola.
	 * @param evento
	 * @return
	 */
	private boolean analizarRestitucionByTurno(EvnRestitucionReparto evento, Turno turnoRestitucion) throws EventoException {

		Turno turno = (Turno) turnoRestitucion;
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = turno.getSolicitud().getUsuario();
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		Solicitud solicitud = (Solicitud) turno.getSolicitud();


		if (solicitud == null) {
			throw new ANRestitucionRepartoException("No existe solicitud asociada");
		}

		try {
			boolean resultResolucion = hermod.addResolucionToSolicitudRestitucion((SolicitudRestitucionReparto)solicitud , evento.getSolicitudRestitucionReparto().getResolucion(),evento.getSolicitudRestitucionReparto().getObservaciones(), evento.getSolicitudRestitucionReparto().getFechaResolucion());

			if(resultResolucion && evento.getRespuestaWF().equals(EvnRestitucionReparto.CONFIRMAR)){
				hermod.realizarRestitucionRepartoNotarial(solicitud.getIdSolicitud());
			}
			if(resultResolucion && evento.getRespuestaWF().equals(EvnRestitucionReparto.NEGAR)){
				hermod.rechazarSolicitudRestitucion((SolicitudRestitucionReparto)solicitud);
			}

		} catch (HermodException e) {
			throw new ANRestitucionRepartoException("No se pudo procesar el análisis de restitución ", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRestitucionReparto.class,"No se pudo procesar el análisis de restitución " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		try {
			hermod.avanzarTurnoNuevoNormal(turno, fase, tabla,evento.getUsuarioSIR());
		} catch (HermodException e) {
			throw new ANRestitucionRepartoException("No se pudo procesar el análisis de restitución ", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRestitucionReparto.class,"No se pudo procesar el análisis de restitución " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}


		


		//evRespuesta = new EvnRespRestitucionReparto(evento.getUsuario(), turno, EvnRespRestitucionReparto.NOTIFICAR_CIUDADANO);

		return true;
	}

	/**
	 * Avanza la fase de notificar al ciudadano ya sea con éxito o con fracaso dependiendo
	 * de la información que tiene el evento.
	 * @param evento
	 * @return
	 */

	private EventoRespuesta notificarCiudadano(EvnRestitucionReparto evento) throws EventoException {

		EvnRespRestitucionReparto evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = turno.getSolicitud().getUsuario();
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		Solicitud solicitud = (Solicitud) turno.getSolicitud();

		if (solicitud == null) {
			throw new ANRestitucionRepartoException("No existe solicitud asociada");
		}

		try {
			hermod.avanzarTurnoNuevoNormal(turno, fase, tabla,evento.getUsuarioSIR());
		} catch (HermodException e) {
			throw new ANRestitucionRepartoException("No se pudo notificar al ciudadano ", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRestitucionReparto.class,"No se pudo notificar al ciudadano " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		evRespuesta = new EvnRespRestitucionReparto(evento.getUsuario(), turno, EvnRespRestitucionReparto.NOTIFICAR_CIUDADANO);

		return evRespuesta;
	}

	/**
	 * Método que devuelve una hashtable que tiene como llave el id del turno y el objeto es
	 * la minuta
	 * @param minutas
	 * @return Hashtable que llave el id del turno y el objeto es la minuta, no es nulo.
	 */
	private Hashtable armarTabla(List minutas) throws EventoException{
		Hashtable reparto = new Hashtable();
		String idWf = null;
		try {
			if(minutas!=null){
				Iterator itMinutas = minutas.iterator();

				while(itMinutas.hasNext()){
					Minuta minuta = (Minuta)itMinutas.next();
					idWf = hermod.getIdWorkflowByIdMinuta(minuta.getIdMinuta());
					if (idWf != null){
						reparto.put(idWf,minuta);
					}
				}
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANRestitucionReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANRestitucionReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		return reparto;
	}
	
	/**
	 * Este método se encarga de consultar los turnos bloqueados para por recursos y revocatorias directas.
	 * 
	 * @param evento de tipo <code>EvnRestitucionNotarial</code> 
	 * 
	 * @return <code>EvnRestitucionNotarial</code> con los turnos bloqueados por recursos y revocatorias directas. 
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EventoRespuesta consultarTurnosAnalisisRestitucion(EvnRestitucionReparto evento) throws EventoException {
		List turnosRestitucionNotarial = evento.getListaturnos();
		List turnosRestitucionNotarialInfo = new ArrayList();
		try {
			Iterator itEstaciones = turnosRestitucionNotarial.iterator();
			while (itEstaciones.hasNext()) {
				Turno turno = (Turno) itEstaciones.next();
				Turno turnoInfo = hermod.getTurnobyWF(turno.getIdWorkflow());
				turnosRestitucionNotarialInfo.add((Turno)turnoInfo);
			}
			
		}catch (HermodException e) {
			throw new ANRevocatoriaRegistroException("No se pudo consultar los turnos marcados para ser reanotados.", e);
		} catch (Throwable e) {
			throw new ANRevocatoriaRegistroException("No se pudo consultar los turnos marcados para ser reanotados. " + e.getMessage(), e);
		}	
		
		if(turnosRestitucionNotarial == null){
			turnosRestitucionNotarial = new ArrayList();	
		}		
		
		if(turnosRestitucionNotarialInfo == null){
			turnosRestitucionNotarialInfo = new ArrayList();	
		}		
		

		EvnRespRestitucionReparto evRespuesta = new EvnRespRestitucionReparto(turnosRestitucionNotarial ,EvnRespRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION );
		evRespuesta.setListaTurnos(turnosRestitucionNotarialInfo);
		return evRespuesta;

	}

}
