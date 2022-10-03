package gov.sir.core.negocio.acciones.repartonotarial;

import gov.sir.core.eventos.repartonotarial.EvnRepartoNotarial;
import gov.sir.core.eventos.repartonotarial.EvnRespRepartoNotarial;
import gov.sir.core.negocio.acciones.excepciones.ANRepartoNotarialException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.NotaNoAgregadaException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarial;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Texto;
import gov.sir.core.negocio.modelo.TextoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CReparto;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleActaRepartoMinutas;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaRepartoMinuta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRepartoMinutas;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.IDidworkflowComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.HermodException;
import gov.sir.hermod.gdocumental.integracion.SGD;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import org.auriga.core.modelo.transferObjects.Estacion;

import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import org.auriga.util.ExceptionPrinter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnRepartoNotarial</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespRepartoNotarial</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow del proceso de reparto notarial y llamar a los servicios
 * que se requieren en cada fase del proceso.
 * @author ppabon
 */
public class ANRepartoNotarial extends SoporteAccionNegocio {

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
	 *Constructor de la clase ANRepartoNotarial.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANRepartoNotarial() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

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
				throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio forseti", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
	}

	/**
	* Recibe un evento de reparto notarial y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnRepartoNotarial</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespRepartoNotarial</CODE>
	* @see gov.sir.core.eventos.comun.EvnRepartoNotarial
	* @see gov.sir.core.eventos.comun.EvnRespRepartoNotarial
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnRepartoNotarial evento = (EvnRepartoNotarial) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnRepartoNotarial.CARGAR_CIRCULOS_NOTARIALES)) {
			return cargarCirculosNotariales(evento);
		} else if (evento.getTipoEvento().equals(EvnRepartoNotarial.CREAR_SOLICITUD)) {
			return crearSolicitud(evento);
		} else if (evento.getTipoEvento().equals(EvnRepartoNotarial.OBTENER_CATEGORIA)) {
			return obtenerCategoria(evento);
		}else if (evento.getTipoEvento().equals(EvnRepartoNotarial.EJECUTAR_REPARTO)) {
			return ejecutarReparto(evento);
		} else if (evento.getTipoEvento().equals(EvnRepartoNotarial.NOTIFICAR_CIUDADANO)) {
			return notificarCiudadano(evento);
		} else if (evento.getTipoEvento().equals(EvnRepartoNotarial.NOTIFICAR_CIUDADANO_MASIVA)) {
			return notificarCiudadanoMasiva(evento);
		}

		//Cargar listado de turnos de restitución asociados a una minuta.
		else if (evento.getTipoEvento().equals(EvnRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA)){
			return cargarTurnosRestitucionMinuta(evento);
		}

		return null;
	}

	/**
	 * Este método carga los circulos notariales a partir de un circulo registral.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta cargarCirculosNotariales(EvnRepartoNotarial evento) throws EventoException {

		EvnRespRepartoNotarial evRespuesta = null;
		List circulosNotariales = null;
		Circulo circulo = (Circulo) evento.getCirculo();

		try {
			circulosNotariales = hermod.getCirculosNotarialesByCirculoRegistral(circulo);
		} catch (HermodException e) {
			throw new ANRepartoNotarialException("No se pudo cargar ", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		if (circulosNotariales == null) {
			circulosNotariales = new ArrayList();
		}

		evRespuesta = new EvnRespRepartoNotarial(circulosNotariales, EvnRespRepartoNotarial.CARGAR_CIRCULOS_NOTARIALES);
		return evRespuesta;
	}




	/**
	 * Este método carga los turnos de Restitución que tengan asociada una Minuta.
	 * @return
	 */
	private EventoRespuesta cargarTurnosRestitucionMinuta(EvnRepartoNotarial evento) throws EventoException {

		EvnRespRepartoNotarial evRespuesta = null;
		List turnosRestitucion = null;
		String idMinuta = evento.getIdMinutaConsultaRestitucion();
		String idCirculo = evento.getIdCirculoMinutaConsultaRestitucion();

		try {

			turnosRestitucion = hermod.getListadoTurnosRestitucionMinutas(idCirculo,idMinuta);
		}

		catch (Throwable e)
		{
			throw new EventoException(e.getMessage(), e);
		}


		evRespuesta = new EvnRespRepartoNotarial(turnosRestitucion, EvnRespRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA);
		return evRespuesta;
	}





	/**
	 * Crea una instancia del  proceso de reparto notarial de minutas.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespRepartoNotarial crearSolicitud(EvnRepartoNotarial evento) throws EventoException {

		Estacion estacion = evento.getEstacion();
    	gov.sir.core.negocio.modelo.Usuario usuarioSIR = evento.getUsuarioSIR();
    	String estacionAsignada = null;
        Pago pago = null;
        
		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Solicitud solicitud = null;
		Turno turno = null;
		
		boolean imprimirConstancia = evento.isImprimirConstancia();

		try 
		{
			/*HorarioNotarial horarioNotarial = new HorarioNotarial();
			horarioNotarial.setIdCirculo(evento.getSolicitudRepartoNotarial().getCirculo().getIdCirculo());
			
			if(evento.getMinuta().isNormal())
				horarioNotarial.setIdTipoHorarioNotarial(CHorarioNotarial.RADICACION_MINUTAS_ORDINARIAS);
			else
				horarioNotarial.setIdTipoHorarioNotarial(CHorarioNotarial.RADICACION_MINUTAS_EXTRAORDINARIAS);
			
			Calendar cal = new GregorianCalendar();
		    
		    // Calcular dia actual
		    int idDia = cal.get(Calendar.DAY_OF_WEEK); // 1=Lunes, 2=Martes,
		    if(idDia == 2)
		    	horarioNotarial.setIdDia(CHorarioNotarial.LUNES);
			if(idDia == 3)
				horarioNotarial.setIdDia(CHorarioNotarial.MARTES);
			if(idDia == 4)
				horarioNotarial.setIdDia(CHorarioNotarial.MIERCOLES);
			if(idDia == 5)
				horarioNotarial.setIdDia(CHorarioNotarial.JUEVES);
			if(idDia == 6)
				horarioNotarial.setIdDia(CHorarioNotarial.VIERNES);
			
		    String strHora = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));     // 0..23
		    
		    if(cal.get(Calendar.MINUTE)<10)   // 0..59
		    	strHora = strHora  + "0" + Integer.toString(cal.get(Calendar.MINUTE));          
		    else
		    	strHora = strHora + Integer.toString(cal.get(Calendar.MINUTE));
		    
		    boolean invalido = true;
		    
		    List horariosNotariales = hermod.getHorariosNotarialesCirculoByTipo(horarioNotarial);
		    
		    if(horariosNotariales == null)
		    {
		    	horariosNotariales = new ArrayList();
		    }
			
			for(Iterator horariosIter = horariosNotariales.iterator(); horariosIter.hasNext();)
            {
        			HorarioNotarial horarioNotarialPersisted = (HorarioNotarial)horariosIter.next();
        			String strHoraInicioPersisted = horarioNotarialPersisted.getHoraInicio();
        			strHoraInicioPersisted = strHoraInicioPersisted + horarioNotarialPersisted.getMinInicio();
        			String strHoraFinPersisted = horarioNotarialPersisted.getHoraFin();
        			strHoraFinPersisted = strHoraFinPersisted + horarioNotarialPersisted.getMinFin();
        			
        			if(Integer.parseInt(strHoraInicioPersisted) <= Integer.parseInt(strHora) &&
        					Integer.parseInt(strHora) <= Integer.parseInt(strHoraFinPersisted))
        				invalido = false;
            }
			
			if(invalido)
				throw new LiquidacionNoEfectuadaException("No puede crear la solicitud pues en este horario no es permitido.");

			*/
                        /**
                        * @autor: Edgar Lora
                        * @mantis: 0010397
                        * @requerimiento: 063_151
                        */
                        LiquidacionTurnoRepartoNotarial liquidacion = new LiquidacionTurnoRepartoNotarial();
			solicitud = (Solicitud) hermod.crearSolicitud(evento.getSolicitudRepartoNotarial());

			if (solicitud == null) {
				throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
			}
                        
			SolicitudRepartoNotarial solicitudRepartoNotarial = (SolicitudRepartoNotarial) solicitud;
			solicitudRepartoNotarial = hermod.addMinutaToSolicitudReparto(solicitudRepartoNotarial, evento.getMinuta());
				
			Usuario usuarioSir = evento.getUsuarioSIR();
			String idEstacion = estacion.getEstacionId();
                        
                        liquidacion.setSolicitud(solicitud);
                        liquidacion.setUsuario(usuarioSir);
                        LiquidacionTurnoRepartoNotarial auxLiquidacion = (LiquidacionTurnoRepartoNotarial)hermod.liquidar(liquidacion);
                        DocPagoEfectivo docPago = new DocPagoEfectivo(liquidacion.getValor());
                        AplicacionPago appEfectivo = new AplicacionPago();
                        appEfectivo.setIdLiquidacion(liquidacion.getIdLiquidacion());
                        appEfectivo.setIdSolicitud(solicitud.getIdSolicitud());
                        appEfectivo.setValorAplicado(liquidacion.getValor());
                        appEfectivo.setDocumentoPago(docPago);

                        pago = new Pago(auxLiquidacion, null);
                        pago.addAplicacionPago(appEfectivo);
                        pago.setIdLiquidacion(liquidacion.getIdLiquidacion());
                        pago.setIdSolicitud(solicitud.getIdSolicitud());
                        pago.setLiquidacion(auxLiquidacion);
                        pago.setUsuario(usuarioSir);
                        auxLiquidacion.setSolicitud(solicitud);

                        //String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
                        EstacionReciboPk estacionReciboID = new EstacionReciboPk();
                        estacionReciboID.idEstacion = idEstacion;

                        Circulo circulo = evento.getCirculo();
                        String fechaImpresion = this.getFechaImpresion();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        long reciboMinutaRepartoNotarial = hermod.getSecuencialRepartoNotarialRecibo(circulo.getIdCirculo(), String.valueOf(calendar.get(Calendar.YEAR)));
                        pago.setNumRecibo(String.valueOf(reciboMinutaRepartoNotarial));

                        if (validarPago(pago,
                                hermod.getRangoAceptacionPago(
                                        solicitud.getProceso().getNombre()))) {
                                pago = hermod.validarPago(pago);
                        }
                        
			try {
				
				//1. Crear el turno
				turno = hermod.procesarPago(pago, estacion.getEstacionId(), "imp", pago.getUsuario(), null, false);                                
                                pago.getLiquidacion().getSolicitud().setTurno(turno);
								
				InicioProcesos inicioProcesos = hermod.obtenerFaseInicial(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
				
				Hashtable parametrosInicio = new Hashtable();
				parametrosInicio.put(Processor.ROL, inicioProcesos.getIdRol());
				parametrosInicio.put(Processor.ITEM_KEY, turno.getIdWorkflow());
				parametrosInicio.put(Processor.ACTIVITY, inicioProcesos.getIdFase());
				parametrosInicio.put(Processor.NOT_ID, "1");
				
				//3. Obtener estación a la que se asocia el turno. 
				estacionAsignada = hermod.obtenerEstacionTurno(parametrosInicio, turno.getIdCirculo());
				
				//4. Guardar información del turno: ITEMKEY, NOTIFICATION ID, ESTACION ASIGNADA, etc, 
				hermod.guardarInfoTurnoEjecucion (parametrosInicio,estacionAsignada,turno,usuarioSir);
				
				//Agregar las notas informativas
				if (turno != null) {
					List listaNotas = evento.getNotasInformativas();
					if (listaNotas != null) {

						TurnoPk idTurno = new TurnoPk();
						idTurno.anio = turno.getAnio();
						idTurno.idCirculo = turno.getIdCirculo();
						idTurno.idProceso = turno.getIdProceso();
						idTurno.idTurno = turno.getIdTurno();

						for (int j = 0; j < listaNotas.size(); j++) {
							Nota notaInformativa = (Nota) listaNotas.get(j);
							hermod.addNotaToTurno(notaInformativa, idTurno);
						}
					}
				}
                                
                                
                                ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion,CTipoImprimible.RECIBO);
                                // Bug 3479
                                // :: ProcId: RepartoNotarial
                                // (usuario que genera la solicitud)
                                // jxpath-search:.

                                ImprimibleRecibo imprimible;
                                imprimible = impRec;

                                // Bug 5223
                                // realizar busqueda de usuario
                                String local_UsuarioGeneraRecibo = "";


                                local_SearchImpl_jx: {
                                   // Cambio, no es el usuario de solicitud sino el usuario
                                   // que se envia para afectar el wf
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
					//se manda a imprimir el recibo por el identificador unico de usuario
					if (imprimirConstancia){
						printJobs.enviar(uid, bundle, maxIntentos, espera);
					}
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
				Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		} catch (HermodException e) {
			e.printStackTrace(System.out);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespRepartoNotarial eventoRespuesta = new EvnRespRepartoNotarial(evento.getUsuario(), turno, EvnRespRepartoNotarial.CREAR_SOLICITUD);

		return eventoRespuesta;
	}

	/**
	 * Crea una instancia del  proceso de reparto notarial de minutas.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespRepartoNotarial obtenerCategoria(EvnRepartoNotarial evento) throws EventoException {
		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}
		Categoria categoria = null;
		try 
		{
			categoria = hermod.getCategoriaClasificacionMinuta(evento.getMinuta());
		} catch (HermodException e) {
			e.printStackTrace(System.out);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespRepartoNotarial eventoRespuesta = new EvnRespRepartoNotarial(null,EvnRespRepartoNotarial.OBTENER_CATEGORIA_OK);
		eventoRespuesta.setCategoria(categoria);
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
	 * Este método se encarga de llamar a los métodos responsables de realizar el reparto notarial de minutas
	 * y asignar los turnos entre las diferentes notarias, de acuerdo a los criterios preestablecidos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespRepartoNotarial ejecutarReparto(EvnRepartoNotarial evento) throws EventoException {
                
		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Hashtable hashTable = new Hashtable();
		
		Hashtable repartos = new Hashtable();

		try {
			
			/*HorarioNotarial horarioNotarial = new HorarioNotarial();
			horarioNotarial.setIdCirculo(evento.getCirculo().getIdCirculo());
			
			if(evento.getTipoReparto().equals(CMinuta.ORDINARIO))
				horarioNotarial.setIdTipoHorarioNotarial(CHorarioNotarial.REPARTO_ORDINARIO);
			else
				horarioNotarial.setIdTipoHorarioNotarial(CHorarioNotarial.REPARTO_EXTRAORDINARIO);
			
			Calendar cal = new GregorianCalendar();
		    
		    // Calcular dia actual
		    int idDia = cal.get(Calendar.DAY_OF_WEEK); // 1=Domingo, 2=Lunes,
		    if(idDia == 2)
		    	horarioNotarial.setIdDia(CHorarioNotarial.LUNES);
			if(idDia == 3)
				horarioNotarial.setIdDia(CHorarioNotarial.MARTES);
			if(idDia == 4)
				horarioNotarial.setIdDia(CHorarioNotarial.MIERCOLES);
			if(idDia == 5)
				horarioNotarial.setIdDia(CHorarioNotarial.JUEVES);
			if(idDia == 6)
				horarioNotarial.setIdDia(CHorarioNotarial.VIERNES);
			
		    String strHora = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));     // 0..23
		    
		    if(cal.get(Calendar.MINUTE)<10)   // 0..59
		    	strHora = strHora  + "0" + Integer.toString(cal.get(Calendar.MINUTE));          
		    else
		    	strHora = strHora + Integer.toString(cal.get(Calendar.MINUTE));
		    
		    boolean invalido = true;
		    
		    List horariosNotariales = hermod.getHorariosNotarialesCirculoByTipo(horarioNotarial);
		    
		    if(horariosNotariales == null)
		    {
		    	horariosNotariales = new ArrayList();
		    }
			
			for(Iterator horariosIter = horariosNotariales.iterator(); horariosIter.hasNext();)
            {
        			HorarioNotarial horarioNotarialPersisted = (HorarioNotarial)horariosIter.next();
        			String strHoraInicioPersisted = horarioNotarialPersisted.getHoraInicio();
        			strHoraInicioPersisted = strHoraInicioPersisted + horarioNotarialPersisted.getMinInicio();
        			String strHoraFinPersisted = horarioNotarialPersisted.getHoraFin();
        			strHoraFinPersisted = strHoraFinPersisted + horarioNotarialPersisted.getMinFin();
        			
        			if(Integer.parseInt(strHoraInicioPersisted) <= Integer.parseInt(strHora) &&
        					Integer.parseInt(strHora) <= Integer.parseInt(strHoraFinPersisted))
        				invalido = false;
            }
			
			if(invalido)
			{
				EvnRespRepartoNotarial eventoRespuesta = new EvnRespRepartoNotarial(evento.getUsuario(), hashTable, EvnRespRepartoNotarial.EJECUTAR_REPARTO_FAILED);
				return eventoRespuesta;
			}
			*/
			

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


                        /**
                         * @author: Diana Lora
                         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
                         * Se guardan las notas informativas asociadas al turno extraordinario repartido.
                         */
                        if (!evento.getTipoReparto().equals(CMinuta.ORDINARIO)) {

                            String[] turnosExtraordinarios = evento.getTurnosExtraordinarios();
                            for (int j = 0; j < turnosExtraordinarios.length; j++) {
                                // ID_WORKFLOW: 1. ANIO 2. CIRCULO 3. PROCESO 4. CONSECUTIVO
                                String[] turnoExtraordinario = turnosExtraordinarios[j].split("-");

                                TurnoPk id = new TurnoPk();
                                id.anio = turnoExtraordinario[0];
                                id.idCirculo = turnoExtraordinario[1];
                                id.idProceso = Integer.parseInt(turnoExtraordinario[2]);
                                id.idTurno = turnoExtraordinario[3];

                                List notasInformativas = evento.getNotasInformativas();
                                if(notasInformativas != null){
                                    for (int k = 0; k < notasInformativas.size(); k++) {
                                        Nota nota = (Nota) notasInformativas.get(k);

                                        try {
                                                hermod.addNotaToTurno(nota, id);
                                        } catch (HermodException e) {
                                                throw new NotaNoAgregadaException("La nota no pudo ser agregada", e);
                                        } catch (Throwable e) {
                                                ExceptionPrinter printer = new ExceptionPrinter(e);
                                                Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                                                throw new EventoException(e.getMessage(), e);
                                        }
                                    }
                                }
                            }
                        }

			List circulosNotariales = hermod.getCirculosNotarialesByCirculoRegistral(evento.getCirculo());
			if (circulosNotariales != null)
			{
				for (Iterator i = circulosNotariales.iterator(); i.hasNext();)
				{
					CirculoNotarial circuloNotarial = (CirculoNotarial)i.next();
					if (!circuloNotarial.isIncluirEnReparto())
					{
						continue;
					}
					if (evento.getTipoReparto().equals(CMinuta.ORDINARIO)) 
					{
						hashTable = hermod.realizarRepartoCirculoNotarialOrdinario(circuloNotarial, evento.getUsuarioSIR());
					} else {
						hashTable = hermod.realizarRepartoCirculoNotarialExtraordinario(circuloNotarial, evento.getUsuarioSIR(), true, evento.getTurnosExtraordinarios());
					}
					
					if (hashTable != null && hashTable.size() > 0)
					{
						repartos.put(circuloNotarial.getNombre(), hashTable);
					}
					
//					SE REALIZAN LAS IMPRESIONES DEL REPARTO
					try {
						//String uid = evento.getUID();
						String uid = evento.getCirculo().getIdCirculo();
						Circulo circulo = evento.getCirculo();
						String fechaImpresion = this.getFechaImpresion();
						
						ArrayList minutasOrden = new ArrayList();
						
						Hashtable minutas = new Hashtable();
						Enumeration enumeration = hashTable.keys();

						boolean repartidaAlguna = false;
						
						while (enumeration.hasMoreElements()) {
							String idWorkflow = (String) enumeration.nextElement();

							Log.getInstance().debug(ANRepartoNotarial.class,"LLAVE1" + idWorkflow);
							Minuta min = hermod.getMinutaByTurnoWF(idWorkflow);
							if (min != null) {
								if (min.getRepartoNotarial()!=null){
									repartidaAlguna = true;
									minutas.put(idWorkflow, min);
									minutasOrden.add(idWorkflow);
								}
							}
						}
						
						//Se debe ordenar la lista de minutas
						if (minutasOrden !=null) {
							try {
								Collections.sort(minutasOrden, new IDidworkflowComparator());
							} catch (Exception e) {
								Log.getInstance().error(ANRepartoNotarial.class,"No se pudieron ordenar las Minutas");
							}
						}

						Minuta temp = new Minuta();
						if (minutas != null) {
							Enumeration en = minutas.keys();
							if (en.hasMoreElements()) {
								String key = (String) en.nextElement();
								temp = (Minuta) minutas.get(key);
							}
						}
						
						Calendar c = Calendar.getInstance();

						// Se deberia consultar el ultimo id_repartoConsumido
						String idMinutaSinRepartir = null;
						String tipoSinRepartir = null;
						
						if (minutas == null || minutas.size() == 0 || !repartidaAlguna) {
							if (evento.getTipoReparto().equals(CMinuta.ORDINARIO)) {
								idMinutaSinRepartir = hermod.consultarLastSecuencialCirculoNotarial(circuloNotarial, evento.getUsuarioSIR(),false);
								tipoSinRepartir = CMinuta.ORDINARIO;
							} else {
								idMinutaSinRepartir = hermod.consultarLastSecuencialCirculoNotarial(circuloNotarial, evento.getUsuarioSIR(),true);
								tipoSinRepartir = CMinuta.EXTRAORDINARIO;
							}	
						}
						
						//SE IMPRIME EL RESUMEN DE LOS TURNOS REPARTIDOS
						ImprimibleRepartoMinutas impResumenReparto = new ImprimibleRepartoMinutas(minutas, circulo, fechaImpresion, c.getTime(),CTipoImprimible.REPARTO);
						impResumenReparto.setIdMinutaSinRepartir(idMinutaSinRepartir);
						impResumenReparto.setTipoSinRepartir(tipoSinRepartir);
						impResumenReparto.setMinutasOrden(minutasOrden);
						impResumenReparto.setNombreCirculoRegistral(circuloNotarial.getNombre());
						
						Bundle bundleResumen = new Bundle(impResumenReparto);

						int veces = new Integer(evento.getNumeroResumenes()).intValue();
						bundleResumen.setNumeroCopias(veces);
						//int contador = 0;


						try {
							//se manda a imprimir el recibo por el identificador unico de usuario
							printJobs.enviar(uid, bundleResumen, maxIntentos, espera);
						} catch (Throwable t) {
							t.printStackTrace();
						}


						//SE IMPRIME EL ACTA, LOS PARAMETROS DE CIUDAD, DIRECTOR Y COORDINADOR DEBEN CAMBIARSE POR LOS DE LOS NOMBRES REALES.
						CirculoPk cid = new CirculoPk();
						cid.idCirculo = circulo.getIdCirculo();
						
						TextoPk id = new TextoPk();
						id.idCirculo = circulo.getIdCirculo();
						id.idLlave = CReparto.TEXTO_ACTA_REP_NOTARIAL;
						Texto texto = forseti.getTexto(id);
						
						ActoresRepartoNotarialPk idActores = new ActoresRepartoNotarialPk();
						idActores.idCirculo = circulo.getIdCirculo();
						ActoresRepartoNotarial actoresRepartoNotarial = forseti.getActoresRepartoNotarial(idActores);
						
						String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
						FirmaRegistrador firmaRegistrador = null;

						firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);

						if(firmaRegistrador==null)
						{
							cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
							firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
						}
						
						if(firmaRegistrador==null)
						{
							cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
							firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
						}
						
						if(firmaRegistrador==null)
						{
							cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
							firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
						}
						
						String nombreOficina = "";
						if (circulo.getOficinaOrigen() != null
								&& circulo.getOficinaOrigen().getVereda() != null
								&& circulo.getOficinaOrigen().getVereda().getMunicipio() != null
								&& circulo.getOficinaOrigen().getVereda().getMunicipio().getNombre() != null)
						{
							nombreOficina = circulo.getOficinaOrigen().getVereda().getMunicipio().getNombre();
						}
						
						Turno turno = null;
						
						if(temp.getIdMinuta()!=null && !temp.getIdMinuta().trim().equals("")){
							SolicitudPk solID = new SolicitudPk();
							solID.idSolicitud = temp.getIdMinuta();
							
							turno = hermod.getTurnoBySolicitud(solID);
							
							temp = hermod.getMinutaByTurnoWF(turno.getIdWorkflow());
						}
						
						ImprimibleActaRepartoMinutas impActaReparto = new ImprimibleActaRepartoMinutas(temp, circulo, c.getTime(), nombreOficina, null, null,CTipoImprimible.REPARTO);
						impActaReparto.setIdMinutaSinRepartir(idMinutaSinRepartir);
						impActaReparto.setTipoSinRepartir(tipoSinRepartir);
						
						if(texto != null)
							impActaReparto.setTextoReparto(texto.getTexto());
						
						Usuario usuario = evento.getUsuarioSIR();
						
						String funcionarioReparto = usuario.getNombre();
						funcionarioReparto += usuario.getApellido1()!=null?(" "+ usuario.getApellido1()):"";
						funcionarioReparto += usuario.getApellido2()!=null?(" "+ usuario.getApellido2()):"";
						
						if(actoresRepartoNotarial != null)
						{
							impActaReparto.setCoordinadorReparto(actoresRepartoNotarial.getCoordinadorReparto());
						}
						
						impActaReparto.setDirectorReparto(funcionarioReparto);
						impActaReparto.setRegistradorReparto(firmaRegistrador.getNombreRegistrador());
						
						if(minutas == null || minutas.size() == 0 || !repartidaAlguna )
							impActaReparto.setTextoObservaciones("No hubo minutas para repartir para el día " + impActaReparto.getFecha() + ".");
						
						Bundle bundleActa = new Bundle(impActaReparto);

						veces = new Integer(evento.getNumeroActas()).intValue();
						bundleActa.setNumeroCopias(veces);

						try {
							//se manda a imprimir el recibo por el identificador unico de usuario
							printJobs.enviar(uid, bundleActa, maxIntentos, espera);
						} catch (Throwable t) {
							t.printStackTrace();
						}


//			HAY QUE DESCOMENTAREAR EL SIGUIENTE BLOQUE CUANDO EL SERVICIO hermod.getColasRepartoByCategoria
//			ESTE BIEN HECHO.
			/*
						Hashtable clasificacion = new Hashtable();
						try {
							clasificacion = hermod.getColasRepartoByCategoria();
						} catch (HermodException e) {
							e.printStackTrace(System.out);
							throw new EventoException(e.getMessage(), e);
						} catch (Throwable e) {
							ExceptionPrinter printer = new ExceptionPrinter(e);
							Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
							throw new EventoException(e.getMessage(), e);
						}

						Log.getInstance().error(ANRepartoNotarial.class,"SE TERMINO DE CONSULTAR LA CLASIFICACIÓN DE LOS PRÓXIMOS TURNOS");

						//SE IMPRIME LA CLASIFICACIÓN DE LOS PRÓXIMAS NOTARIAS DE REPARTO.
						ImprimibleClasificacionRepartoNotarias impClasificacionReparto = new ImprimibleClasificacionRepartoNotarias(temp, circulo, c.getTime(), clasificacion);
						Log.getInstance().error(ANRepartoNotarial.class,"CALIFICACION 1");
						Bundle bundleClasificacion = new Bundle(impClasificacionReparto);
						Log.getInstance().error(ANRepartoNotarial.class,"CALIFICACION 2");

						for (int i = 0; i < maxIntentos; i++) {
							Log.getInstance().error(ANRepartoNotarial.class,"CALIFICACION INICIO FOR");
							try {
								//se manda a imprimir el recibo por el identificador unico de usuario
								Log.getInstance().error(ANRepartoNotarial.class,"ANTES IMPRIMIR");
								printJobs.enviar(uid, bundleClasificacion);
								Log.getInstance().error(ANRepartoNotarial.class,"DESPUES IMPRIMIR");
								break;
							} catch (Throwable t) {
								Log.getInstance().error(ANRepartoNotarial.class,"EXCEPCION IMPRIMIR");
								t.printStackTrace();
								Log.getInstance().error(ANRepartoNotarial.class,"ANTES THREAD IMPRIMIR");
								Thread.sleep(espera);
								Log.getInstance().error(ANRepartoNotarial.class,"DESPUES THREAD IMPRIMIR");
							}
							Log.getInstance().error(ANRepartoNotarial.class,"CALIFICACION FIN FOR");
						}

						Log.getInstance().error(ANRepartoNotarial.class,"SE IMPRIMIO LA CLASIFICACIÓN DE LOS PRÓXIMOS REPARTOS");
			*/
						//IMPRIMIR EL RECIBO DEL REPARTO POR CADA MINUTA REPARTIDA. SE IMPRIMEN DOS VECES.
						Iterator itOrden = minutasOrden.iterator();

						veces = 2;
						while (itOrden.hasNext()) {

							String idWorkflow = (String) itOrden.next();
							Log.getInstance().debug(ANRepartoNotarial.class,"GENERANDO CONSTANCIA PARA " + idWorkflow);
							Minuta minuta = (Minuta) minutas.get(idWorkflow);
							
							if (minuta.getRepartoNotarial()!=null){
								ImprimibleConstanciaRepartoMinuta impReparto = new ImprimibleConstanciaRepartoMinuta(minuta, circulo, fechaImpresion, idWorkflow,CTipoImprimible.REPARTO);

								Bundle bundle = new Bundle(impReparto);
								bundle.setNumeroCopias(veces);

								//SE REQUIREN IMPRIMIR DOS COPIAS

								try {
									//se manda a imprimir el recibo por el identificador unico de usuario
									printJobs.enviar(circulo.getIdCirculo(), bundle, maxIntentos, espera);
								} catch (Throwable t) {
									t.printStackTrace();
								}
							}
						}

					} catch (HermodException e) {
						Log.getInstance().error(ANRepartoNotarial.class,e);
						//throw new EventoException(e.getMessage(), e);
					} catch (Throwable e) {
						ExceptionPrinter printer = new ExceptionPrinter(e);
						Log.getInstance().error(ANRepartoNotarial.class,e);
						Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
						//throw new EventoException(e.getMessage(), e);
					}
				}
			}
		} catch (HermodException e) {
			e.printStackTrace(System.out);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		
		

		if (repartos == null) {
			repartos = new Hashtable();
		}
		EvnRespRepartoNotarial eventoRespuesta = new EvnRespRepartoNotarial(evento.getUsuario(), repartos, EvnRespRepartoNotarial.EJECUTAR_REPARTO);

		return eventoRespuesta;
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
	 * Este método permite avanzar el workflow en la fase de notificar al funcionario,
	 * ya sea con la respuesta de éxito o de fracaso.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta notificarCiudadano(EvnRepartoNotarial evento) throws EventoException {

		EvnRespRepartoNotarial evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		Fase fase = evento.getFase();

		gov.sir.core.negocio.modelo.Usuario usuarioSIR = evento.getUsuarioSIR();

		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, usuarioSIR.getUsername());

		Solicitud solicitud = (Solicitud) turno.getSolicitud();

		if (solicitud == null) {
			throw new ANRepartoNotarialException("No existe solicitud asociada");
		}
		
		try {
			hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,usuarioSIR);
                        /**
                          * @author Fernando Padilla Velez
                          * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa SGD,
                          *         Se borran estan lineas, ya que se realizó refactoring al proceso y ya no son necesarias.
                          */
                        SGD sgd = new SGD(turno, usuarioSIR.getUsername());
                        sgd.enviarEstadoTurnoReparto();
		} catch (HermodException e) {
			throw new ANRepartoNotarialException("No se pudo entregar el reparto notarial", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespRepartoNotarial(evento.getUsuario(), turno, EvnRespRepartoNotarial.NOTIFICAR_CIUDADANO);

		return evRespuesta;
	}
	
	/**
	 * Este método permite avanzar el workflow en la fase de notificar al funcionario,
	 * ya sea con la respuesta de éxito o de fracaso.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta notificarCiudadanoMasiva(EvnRepartoNotarial evento) throws EventoException {

		EvnRespRepartoNotarial evRespuesta = null;
		Map turnos = (Map) evento.getTurnos();
		Fase fase = evento.getFase();

		gov.sir.core.negocio.modelo.Usuario usuarioSIR = evento.getUsuarioSIR();

		Iterator it = turnos.keySet().iterator();
	    while (it.hasNext()) 
	    {
	    	Turno turno = (Turno)it.next();
			Hashtable tabla = new Hashtable();
			tabla.put("RESULT", evento.getRespuestaWF());
			tabla.put(CInfoUsuario.USUARIO_SIR, usuarioSIR.getUsername());

			Solicitud solicitud = (Solicitud) turno.getSolicitud();

			if (solicitud == null) {
				throw new ANRepartoNotarialException("No existe solicitud asociada");
			}
			
			try {
				hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,usuarioSIR);
			} catch (HermodException e) {
				throw new ANRepartoNotarialException("No se pudo entregar el reparto notarial", e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANRepartoNotarial.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
	    }

		evRespuesta = new EvnRespRepartoNotarial(evento.getUsuario(), turnos, EvnRespRepartoNotarial.NOTIFICAR_CIUDADANO_MASIVA);

		return evRespuesta;
	}

}
