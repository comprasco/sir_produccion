package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import gov.sir.core.eventos.administracion.EvnConsultasReparto;
import gov.sir.core.eventos.administracion.EvnRespConsultasReparto;
import gov.sir.core.negocio.acciones.correccion.ANCorreccion;
import gov.sir.core.negocio.acciones.excepciones.ConsultaMinutaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CReparto;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.IDMinutaComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * @author mmunoz
 */
public class ANConsultasReparto extends SoporteAccionNegocio {
	
	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;
	
	private PrintJobsInterface printJobs;
        
        /**
        * @autor HGOMEZ 
        * @mantis 13176 
        * @Requerimiento 061_453_Requerimiento_Auditoria 
        * @descripcion Almacenará los valores de la minuta antes de ser modificada.
        */
        private Minuta minutaAnterior;

	/**
	 *Constructor de la clase <code>ANAdministracionHermod</code>.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 * @throws EventoException 
	 */
	public ANConsultasReparto() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
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
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
		}

		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}

	}

	/**
	 * Manejador de eventos de tipo <code>EvnConsultasReparto</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento 
	 * que llega como parámetro.
	 * 
	 * @param e <code>EvnConsultasReparto</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 * 
	 * @return <code>EventoRespuesta</code> con la información resultante de la 
	 * ejecución de la acción sobre los servicios
	 * 
	 * @throws EventoException 
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnConsultasReparto evento = (EvnConsultasReparto) e;
		String tipoEvento = evento.getTipoEvento();
		if (evento == null || tipoEvento == null) {
			return null;
		}
		if (tipoEvento.equals(EvnConsultasReparto.LISTAR_POR_FECHAS)) {
			return listarPorFecha(evento);
		} else if (tipoEvento.equals(EvnConsultasReparto.MINUTA_RADICACION)) {
			return consultaRadicacion(evento);			
		} else if (tipoEvento.equals(EvnConsultasReparto.LISTAR_POR_OTORGANTE)) {
			return listarPorOtorgante(evento);
		} else if (tipoEvento.equals(EvnConsultasReparto.LISTAR_PENDIENTES)) {
			return listarPendientes(evento);
		} else if (tipoEvento.equals(EvnConsultasReparto.ANULAR_MINUTA)) {
			return anularMinuta(evento);						
		} else if (tipoEvento.equals(EvnConsultasReparto.ENVIAR_MINUTA_EDICION)) {
		   return editarMinuta(evento);						
		} else if (tipoEvento.equals(EvnConsultasReparto.IMPRIMIR_SOLICITUD_MINUTA)){
			return imprimirSolicitudMinuta(evento);
		}
		
		return null;
	}
	
	/**
	 * Metodo que retorna una liquidacion ficticia
	 * @param evento
	 * @param turno
	 * @return
	 */
	private LiquidacionTurnoRepartoNotarial generarLiquidacionFicticia(EvnConsultasReparto evento, Turno turno)
	{
		Date hoy = new Date();
		Pago pago = new Pago();
		pago.setCirculo("500");
		pago.setFecha(null);
		pago.setFechaImpresion(hoy);
		pago.setIdLiquidacion("1");
		pago.setIdSolicitud(turno.getSolicitud().getIdSolicitud());
		pago.setLastNumRecibo("0");
		//pago.setNumRecibo("1");
		pago.setUsuario(null);
		
		LiquidacionTurnoRepartoNotarial liquidacion = new LiquidacionTurnoRepartoNotarial();
		liquidacion.setCirculo("500");
		liquidacion.setFecha(null);
		liquidacion.setIdLiquidacion("1");
		liquidacion.setIdSolicitud(turno.getSolicitud().getIdSolicitud());
		liquidacion.setPago(pago);
		liquidacion.setSolicitud(turno.getSolicitud());
		liquidacion.setUsuario(null);
		liquidacion.setValor(10000);
		
		return liquidacion;
	}


	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta imprimirSolicitudMinuta(EvnConsultasReparto evento) throws EventoException{
		SolicitudPk idSol=new SolicitudPk();
		idSol.idSolicitud=evento.getMinuta().getIdMinuta();
		try {
			Turno turno = hermod.getTurnoBySolicitud(idSol);
			Turno turnoMin=hermod.getTurnobyWF(turno.getIdWorkflow());
			LiquidacionTurnoRepartoNotarial liquidacion= null;
			//En el caso de que no se haya generado una liquidacion ej. reparto notarial,
			//se genera una liquidacion con datos ficticios
			if(turnoMin.getSolicitud().getLiquidaciones().size() == 0){
				liquidacion = this.generarLiquidacionFicticia(evento, turnoMin);
			}
			else
			{
				liquidacion=(LiquidacionTurnoRepartoNotarial)turnoMin.getSolicitud().getLiquidaciones().get(0);
			}
			 
			   Pago pago=liquidacion.getPago();
			   pago.setLiquidacion(liquidacion);
			   pago.getLiquidacion().setSolicitud(turnoMin.getSolicitud());
			   pago.getLiquidacion().getSolicitud().setTurno(turnoMin);
			   Circulo circulo = evento.getCirculo();
			   String fechaImpresion = this.getFechaImpresion();
			   ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion,CTipoImprimible.RECIBO);
			   impRec.setReimpresion(true);
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
		}  catch (HermodException e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		return null;
	}

    /**
    * @param evento
    * @return
    */
    /**
    * @autor HGOMEZ 
    * @mantis 13176 
    * @Requerimiento 061_453_Requerimiento_Auditoria 
    * @descripcion Modificaciones varias para permitir hacer auditoria a las modificaciones
    * que sufra la minuta.
    */
    private EventoRespuesta editarMinuta(EvnConsultasReparto evento) throws EventoException {
        Minuta minuta = new Minuta();
        try {
            Usuario usuario = fenrir.getUsuario(evento.getUsuario());

            //Obtener número máximo de ediciones permitido para la minuta y compararlo con el valor actual.
            int numeroPermitido = hermod.getNumModificacionesMinutas();
            Minuta minutaOld = null;

            int numeroActual = evento.getMinuta().getNumModificaciones();

            if (numeroActual >= numeroPermitido) {
                throw new EventoException("Se superó el número de ediciones permitidas para la minuta");
            }

            if (minutaAnterior != null) {
                minutaOld = minutaAnterior;
            }

            //Modificar la minuta
            minuta = hermod.modificarMinuta(evento.getMinuta(), true, usuario);

            java.util.Map infoUsuario = new java.util.HashMap();
            if (evento.getInfoUsuario() != null) {
                infoUsuario.put("user", evento.getInfoUsuario().getUser());
                infoUsuario.put("host", evento.getInfoUsuario().getHost());
                infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
                infoUsuario.put("address", evento.getInfoUsuario().getAddress());
                infoUsuario.put("idTurno", minuta.getIdMinuta());
            }
            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            try {
                auditoria.guardarDatosTerminal(infoUsuario);
                if (minutaOld != null) {
                    /**
                    * @autor CTORRES 
                    * @mantis 13176 
                    * @Requerimiento 061_453_Requerimiento_Auditoria 
                    */
                    minuta.setCirculoNotarial(evento.getMinuta().getCirculoNotarial());
                    auditoria.guardarAuditoriaMinuta(minuta, minutaOld, minuta.getIdMinuta());
                }

            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Actualizar el valor de modificaciones en la minuta
            hermod.updateNumModificacionesMinuta(minuta);

            SolicitudPk idSol = new SolicitudPk();
            idSol.idSolicitud = evento.getMinuta().getIdMinuta();
            Turno turnoMin = hermod.getTurnoBySolicitud(idSol);

            if (evento.getNotaInformativa() != null && !evento.getNotaInformativa().trim().equals("")) {
                Nota nota = new Nota();
                nota.setDescripcion(evento.getNotaInformativa());
                TipoNota tipoNota = new TipoNota();
                String descNota = evento.getNotaInformativa();
                ProcesoPk poid = new ProcesoPk();
                poid.idProceso = Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
                List tipos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, false);
                Iterator itTipos = tipos.iterator();
                TipoNota tipo = null;
                while (itTipos.hasNext()) {
                    TipoNota temp = (TipoNota) itTipos.next();
                    if (temp.getFase().equals(CFase.REP_REPARTO)) {
                        tipo = temp;
                        break;
                    }
                }
                nota.setTiponota(tipo);

                nota.setUsuario(usuario);

                TurnoPk turnoId = new TurnoPk();
                turnoId.anio = turnoMin.getAnio();
                turnoId.idCirculo = turnoMin.getIdCirculo();
                turnoId.idProceso = turnoMin.getIdProceso();
                turnoId.idTurno = turnoMin.getIdTurno();

                hermod.addNotaToTurno(nota, turnoId);

            }

            try {
                auditoria.borrarDatosTerminal(minuta.getIdMinuta());
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANConsultasReparto.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANConsultasReparto.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespConsultasReparto respuesta = new EvnRespConsultasReparto(minuta, EvnRespConsultasReparto.MINUTA_EDITADA);
        return respuesta;
    }
	
	private String getFechaImpresion() {

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
	private String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
		return (new Integer(i)).toString();
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta anularMinuta(EvnConsultasReparto evento) throws EventoException {
		try {
			Nota nota=new Nota();
			nota.setDescripcion(evento.getNotaInformativa());
			TipoNota tipoNota=new TipoNota();
			String descNota = evento.getNotaInformativa();
			ProcesoPk poid = new ProcesoPk();
			poid.idProceso=Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
			List tipos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, false);
			Iterator itTipos = tipos.iterator();
			TipoNota tipo=null;
			while(itTipos.hasNext()){
				TipoNota temp=(TipoNota)itTipos.next();
				if (temp.getFase().equals(CFase.REP_REPARTO)){
					tipo=temp;
					break;
				}
			}
			nota.setTiponota(tipo);
			org.auriga.core.modelo.transferObjects.Usuario usu=evento.getUsuario();
			Usuario usuario = fenrir.getUsuario(usu);
			nota.setUsuario(usuario);
			
			Minuta minuta = evento.getMinuta();
			SolicitudPk idSol=new SolicitudPk();
			idSol.idSolicitud=minuta.getSolicitud().getIdSolicitud();
			Turno turnoMin=hermod.getTurnoBySolicitud(idSol);
			TurnoPk turnoId=new TurnoPk();
			turnoId.anio=turnoMin.getAnio();
			turnoId.idCirculo=turnoMin.getIdCirculo();
			turnoId.idProceso=turnoMin.getIdProceso();
			turnoId.idTurno=turnoMin.getIdTurno();
			
			if (hermod.anularMinuta(evento.getMinuta(), usuario)){
				hermod.addNotaToTurno(nota,turnoId);
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta listarPendientes(EvnConsultasReparto evento) throws EventoException {
		List minutas;
		try {
			
			Circulo circuloRegistral = evento.getCirculo();
			minutas = hermod.getMinutasNoRepartidasByCirculoRegistral(circuloRegistral);
		} catch (HermodException e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		
		if (minutas !=null) {
			try {
				Collections.sort(minutas, new IDMinutaComparator());
			} catch (Exception e) {
				Log.getInstance().error(ANConsultasReparto.class,"No se pudieron ordenar las Minutas");
			}
		}
		
		EvnRespConsultasReparto respuesta = new EvnRespConsultasReparto(armarTabla(minutas), EvnRespConsultasReparto.TABLA_MINUTAS_PENDIENTES_REPARTO); 
		return respuesta;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta listarPorOtorgante(EvnConsultasReparto evento) throws EventoException {
		List minutas1 = new ArrayList();
		List minutas2 = new ArrayList();
		List minutas  = new ArrayList();
		try {
			Circulo circuloRegistral = evento.getCirculo();
			if(evento.isEsPersonaNatural()){
				//Obtener minutas no repartidas
				minutas1 = hermod.getMinutasByOtorganteNatural(evento.getOtorgante(),CReparto.MINUTA_NO_REPARTIDA, circuloRegistral);
				//Obtener minutas repartidas
				minutas2 = hermod.getMinutasByOtorganteNatural(evento.getOtorgante(),CReparto.MINUTA_REPARTIDA,circuloRegistral);
				
				//Unificar el listado de respuestas.
				if (minutas1 !=null)
				{
					for (int i=0; i<minutas1.size(); i++)
					{
						Minuta minutaAux = (Minuta) minutas1.get(i);
						minutas.add(minutaAux);
					}
				}
				//Unificar el listado de respuestas.
				if (minutas2 !=null)
				{
					for (int i=0; i<minutas2.size(); i++)
					{
						Minuta minutaAux = (Minuta) minutas2.get(i);
						minutas.add(minutaAux);
					}
				}				
				
				
			} 
			
			else 
			{
				//Obtener minutas no repartidas.
				minutas1 = hermod.getMinutasByOtorgantePublico(evento.getOtorgante(),CReparto.MINUTA_NO_REPARTIDA, circuloRegistral);
				//Obtener minutas repartidas.
				minutas2 = hermod.getMinutasByOtorgantePublico(evento.getOtorgante(),CReparto.MINUTA_REPARTIDA, circuloRegistral);
				
                 //Unificar el listado de respuestas.
				 if (minutas1 !=null)
				 {
				     for (int i=0; i<minutas1.size(); i++)
					 {
						 Minuta minutaAux = (Minuta) minutas1.get(i);
					     minutas.add(minutaAux);
					 }
				}
				//Unificar el listado de respuestas.
				if (minutas2 !=null)
				{
				    for (int i=0; i<minutas2.size(); i++)
				    {
				   	    Minuta minutaAux = (Minuta) minutas2.get(i);
						minutas.add(minutaAux);
					}
				}				
			}
			
			
			
			
		} catch (HermodException e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
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
					Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				}
			}
		}
		EvnRespConsultasReparto respuesta = new EvnRespConsultasReparto(armarTabla(minutas), EvnRespConsultasReparto.TABLA_REPARTO_OTORGANTE); 
		return respuesta;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultaRadicacion(EvnConsultasReparto evento) throws EventoException {
		Minuta minuta = new Minuta();
		List circulos = new ArrayList();
		Turno turno = null;
		List turnosRestitucion = new ArrayList();
		try {
			Circulo circuloRegistral = evento.getCirculo();
			minuta = hermod.getMinutaByTurnoWF(evento.getIdTurno());
                        /**
                        * @autor HGOMEZ 
                        * @mantis 13176 
                        * @Requerimiento 061_453_Requerimiento_Auditoria 
                        * @descripcion Almacena el valor anterior de la minuta.
                        */
                        minutaAnterior = minuta;
			circulos = hermod.getCirculosNotarialesByCirculoRegistral(evento.getCirculo());	
			SolicitudPk oid=new SolicitudPk();
			oid.idSolicitud=minuta.getIdMinuta();
			turno=hermod.getTurnoBySolicitud(oid);
			if(!circuloRegistral.getIdCirculo().equals(turno.getIdCirculo()))
				throw new ConsultaMinutaException("El número de radicación debe pertenecer al circulo registral: "+circuloRegistral.getIdCirculo());
			Solicitud sol=hermod.getSolicitudById(minuta.getIdMinuta());
			if (sol!=null && turno!=null){
				minuta.setSolicitud(sol);
				sol.setTurno(turno);
			}
						
			List solicitudesTurnoAnt = hermod.getSolicitudesByTurnoAnterior(turno);
			if (solicitudesTurnoAnt != null)
			{
				for (Iterator i = solicitudesTurnoAnt.iterator(); i.hasNext();)
				{
					Solicitud solicitud = (Solicitud)i.next();
					if (solicitud instanceof SolicitudRestitucionReparto)
					{
						SolicitudPk solID = new SolicitudPk();
						solID.idSolicitud = solicitud.getIdSolicitud();
						Turno turnoRestTmp = hermod.getTurnoBySolicitud(solID);
						Turno turnoRest = hermod.getTurnobyWF(turnoRestTmp.getIdWorkflow());
						turnosRestitucion.add(turnoRest);
					}
				}
			}
			
		} catch (HermodException e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new ConsultaMinutaException("No se pudo consultar la minuta.", e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new ConsultaMinutaException("No se pudo consultar la minuta. ", e);
		}
		EvnRespConsultasReparto respuesta = new EvnRespConsultasReparto(minuta, EvnRespConsultasReparto.MINUTA_REPARTO);
		respuesta.setCirculosNotariales(circulos);
		respuesta.setTurnosRestitucion(turnosRestitucion);
		return respuesta;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta listarPorFecha(EvnConsultasReparto evento) throws EventoException {
		List minutas = new ArrayList();
		try {
			Circulo circuloRegistral= evento.getCirculo();
			minutas = hermod.getMinutasRepartidasByRangoFecha(evento.getFechaInicio(),evento.getFechaFin(), circuloRegistral);
		} catch (HermodException e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
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
					Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				}
			}
		}
		
		EvnRespConsultasReparto respuesta = new EvnRespConsultasReparto(armarTabla(minutas), EvnRespConsultasReparto.TABLA_REPARTO_FECHA); 
		return respuesta;
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
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsultasReparto.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		return reparto;
	}

}
