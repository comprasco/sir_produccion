package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.negocio.validaciones.CertificadosSIR;
import gov.sir.core.eventos.administracion.EvnImprimirFolio;
import gov.sir.core.eventos.administracion.EvnRespImprimirFolio;
import gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioCirculo;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaNoPropiedad;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFolioSimple;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleOficioPertenencia;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaPago_SolicitudData;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaCrearSolicitud;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.helpers.comun.ElementoLista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.tareas.TareasSIR;
import gov.sir.core.is21.Encriptador;
/**
* @Autor: Edgar Lora
* @Mantis: 0006493
*/import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaLiquidacion;

import gov.sir.print.server.PrintJobsProperties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Acción de negocio encargada de manejar los eventos de tipo
 * <code>EvnTrasladoTurno</code> destinados a manejar
 * el traslado de turnos entre usuarios
 * @author jmendez
 */
public class ANImprimirFolio extends SoporteAccionNegocio {

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

	/** Instancia de la intefaz de PrntJobs  */
	private PrintJobsInterface printJobs;

	/**
	 * Constructor encargado de inicializar los servicios a ser utilizados por la
	 * acción de Negocio
	 * @throws EventoException
	 */
	public ANImprimirFolio() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
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

		try {
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

			if (printJobs == null) {
				throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}
	}

    /**
     * Manejador de eventos de tipo <code>EvnTrasladoTurno</code>.
     * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
     * al tipo de evento que llegue a la acción de negocio.  Este método redirige
     * la acción a otros métodos en la clase de acuerdo al tipo de evento
     * que llega como parámetro.
     *
     * @param evento <code>EvnRespTrasladoTurno</code> evento con los parámetros
     * de la acción a realizar utilizando los servicios disponibles en la clase.
     *
     * @return <code>EventoRespuesta</code> con la información resultante de la
     * ejecución de la acción sobre los servicios
     *
     * @throws <code>EventoException</code>
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnImprimirFolio evento = (EvnImprimirFolio) e;

        if (evento == null || evento.getTipoEvento() == null) {
            return null;
        }
        String tipoEvento = evento.getTipoEvento();

        if (tipoEvento.equals(EvnImprimirFolio.IMPRIMIR_FOLIO)) {
            return imprimirFolio(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.IMPRIMIR_CERTIFICADO)) {
            return imprimirCertificado(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA)) {
            return imprimirCertificado(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.REIMPRIMIR_RECIBO)) {
            return reImprimirRecibo(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.REIMPRIMIR_CONSULTA)) {
            return reImprimirConsulta(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.OBTENER_IMPRESORAS_CIRCULO)) {
            return obtenerImpresorasCirculo(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.OBTENER_IMPRESORAS_CIRCULO_PERTENENCIA)) {
            return obtenerImpresorasCirculo(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.OBTENER_ULTIMO_TURNO_IMPRESO)) {
            return obtenerUltimoTurnoImpreso(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.OBTENER_ULTIMO_TURNO_IMPRESO_PROCESO)) {
            return obtenerUltimoTurnoImpresoProceso(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.OBTENER_ULTIMA_SOLICITUD_LIQUIDACION)) {
            return obtenerUltimaSolicitudLiquidacion(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.REIMPRIMIR_SOLICITUD_LIQUIDACION)) {
            return reImprimirSolicitudLiquidacion(evento);
        }else if (tipoEvento.equals(EvnImprimirFolio.REIMPRIMIR_RECIBOS)) {
            return reImprimirRecibos(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.AGREGAR_DE_ARCHIVO)) {
            return UploadFile(evento);
        } else if (tipoEvento.equals(EvnImprimirFolio.PROGRAMAR_TAREA)) {
            return programarTarea(evento);
        }

        return null;
    }
    
    
    /**
     * @param evento
     * @return
     */
    private EventoRespuesta reImprimirSolicitudLiquidacion(EvnImprimirFolio evento) throws EventoException {

        List usuarios = null;
        String[] idSolicitudes = evento.getTurnosReimprimir();
        int maxIntentos;
        int espera;        

        for (int i = 0; i < idSolicitudes.length; i++) {
            try {

                SolicitudRegistro solicitud = (SolicitudRegistro) hermod.getSolicitudById(idSolicitudes[i]);
                             
                int intentosImpresionRecibo = Integer.parseInt(hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO));
                if (intentosImpresionRecibo - 1 <= solicitud.getNumReimpresionesRecibo()) {
                    throw new EventoException("Se ha excedido el número de intentos de impresión para los recibos");
                }
               
                String fechaImpresion = this.getFechaImpresion(new Date());
                List liquidaciones = solicitud.getLiquidaciones();
                Liquidacion liquidacion;
                
                liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
                
                ImprimibleConstanciaLiquidacion impRec = new ImprimibleConstanciaLiquidacion(liquidacion, fechaImpresion, CTipoImprimible.RECIBO);
                impRec.setReimpresion(true);
                Bundle bundle = new Bundle(impRec);
                
                String uid = evento.getUid();
               
                try {
                    String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
                    if (intentosImpresion != null) {
                        Integer intentos = new Integer(intentosImpresion);
                        maxIntentos = intentos;
                    } else {
                        Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                        maxIntentos = intentosDefault;
                    }

                    String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_FOLIO);
                    if (intentosImpresion != null) {
                        Integer esperaInt = new Integer(esperaImpresion);
                        espera = esperaInt;
                    } else {
                        Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                        espera = esperaDefault;
                    }
                } catch (Throwable t) {
                    Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                    maxIntentos = intentosDefault;

                    Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                    espera = esperaDefault;
                }

                try {
                    printJobs.enviar(uid, bundle, maxIntentos, espera);
                    SolicitudPk idSolicitud = new SolicitudPk();
                    idSolicitud.idSolicitud = solicitud.getIdSolicitud();
                    hermod.incrementarIntentoReimpresionRecibo(idSolicitud);
                    
                } catch (Throwable t) {
                    t.printStackTrace();
                }

            } catch (ImpresionException ie) {
                throw ie;
            } catch (Throwable t) {
                t.printStackTrace();
                throw new ImpresionException("Error al imprimir el folio: " + t.getMessage());
            }
        }
        
        EvnRespImprimirFolio evRespuesta = new EvnRespImprimirFolio(usuarios, EvnRespImprimirFolio.IMPRESION_FOLIO_OK);
        return evRespuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta reImprimirRecibos(EvnImprimirFolio evento) throws EventoException {

        List usuarios = null;
        Turno turno = null;
        String[] idTurnosWF = evento.getTurnosReimprimir();
        Pago pago = null;
        int maxIntentos;
        int espera;
        Bundle bundle = null;
        Usuario user = evento.getUsuarioNeg();

        for (int i = 0; i < idTurnosWF.length; i++) {
            try {

                turno = hermod.getTurnobyWF(idTurnosWF[i]);

                //comprobar que el turno no esta anulado
                if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                    throw new EventoException("El turno esta anulado");
                }

                /* JAlcazar caso Mantis 05014 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
                                 * turno.getSolicitud() tipo de solictud para reimpresion Recibo Fotocopia.
                 */
                SolicitudFotocopia solicitud_fotocopia = null;
                if (turno.getSolicitud() instanceof SolicitudFotocopia) {
                    solicitud_fotocopia = (SolicitudFotocopia) turno.getSolicitud();
                }
                Solicitud solicitud = turno.getSolicitud();
                List liquidaciones = solicitud.getLiquidaciones();
                Liquidacion liquidacion = null;

                int intentosImpresionRecibo = Integer.parseInt(hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO));
                if (intentosImpresionRecibo - 1 <= solicitud.getNumReimpresionesRecibo()
                        && turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                    throw new EventoException("Se ha excedido el número de intentos de impresión para los recibos");
                }

                if (liquidaciones.size() > 0) {
                    Liquidacion liqInicial = (Liquidacion) liquidaciones.get(0);
                    Pago pagoInicial = liqInicial.getPago();

                    //Si es reimpresion de recibo de mayor valor, no valida que el usuario fue el que creo el turno
                    if (!evento.isMayorValor()) {
                        if (!pagoInicial.getUsuario().getUsername().equals(user.getUsername())) {
                            throw new ImpresionException("Únicamente el usuario " + pagoInicial.getUsuario().getUsername() + " puede realizar la reimpresión, ya que él fue quién creo el turno.");
                        }
                    }

                    Liquidacion lTemp = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
                    pago = lTemp.getPago();
                    pago.setLiquidacion(lTemp);
                    pago.getLiquidacion().setSolicitud(solicitud);
                    pago.getLiquidacion().getSolicitud().setTurno(turno);

                    liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);

                } else {
                    long procesoFotocopias = new Long(CProceso.PROCESO_FOTOCOPIAS);
                    if (solicitud.getProceso().getIdProceso() == Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {
                        pago = new Pago();
                        solicitud.setTurno(turno);
                        LiquidacionTurnoRepartoNotarial liqAux = new LiquidacionTurnoRepartoNotarial();
                        liqAux.setSolicitud(solicitud);
                        pago.setLiquidacion(liqAux);

                        liquidacion = liqAux;
                    } else if (solicitud.getProceso().getIdProceso() != procesoFotocopias) {
                        throw new ImpresionException("No fue posible determinar quién creo el turno.");
                    }
                }

                Circulo circulo = solicitud.getCirculo();

                /* JAlcazar caso Mantis 05014 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
                                 * reorganizacion del metodo de la informacion comun en el proceso de recibo de fotocopia y los demas tipos de recibo.
                 */
                Circulo circuloPortal = hermod.getCirculoPortal();
                String idCirculoPortal = null;
                if (circuloPortal != null) {
                    idCirculoPortal = circuloPortal.getIdCirculo();
                }
                // Bug 5253 -------------------------------------------------
                gov.sir.core.negocio.modelo.Usuario local_UsuarioGeneraRecibo;
                String local_UsuarioGeneraReciboMsg;
                local_UsuarioGeneraRecibo = user;
                local_UsuarioGeneraReciboMsg = print_FootUtils_BuildUserName(local_UsuarioGeneraRecibo);

                String fechaImpresion = this.getFechaImpresion(new Date());
                /* JAlcazar caso Mantis 05014 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
                                 * Se divide el procedo de recibo de fotocopia y los demas tipos de recibo.
                 */
                if (!(turno.getIdProceso() == Long.valueOf(CProceso.PROCESO_FOTOCOPIAS))) {
                    ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
                    //TFS 4066: SE DEBEN PODER REIMPRIMIR LOS RECIBOS DE MAYOR VALOR
                    //DEPENDIENDO DEL TIPO DE TURNO, SE LE DICE AL IMPRIMIBLE QUE ES DE MAYOR VALOR
                    if (evento.isMayorValor()) {

                        if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCION)) {
                            impRec.setEsMayorValorCorrecciones(true);
                            impRec.setReimpresionPagoMayorValor(true);
                        } else if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                            impRec.setJustificacionMayorValor(((LiquidacionTurnoRegistro) liquidaciones.get(liquidaciones.size() - 1)).getJustificacionMayorValor());
                            impRec.setEsReciboPagoMayorValorRegistro(true);
                            impRec.setReimpresionPagoMayorValor(true);
                        } else if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                            impRec.setEsReciboCertificadoMayorValor(true);
                            impRec.setReimpresionPagoMayorValor(true);
                        }
                    }
                    /**
                     * @author : FPADILLA,HGOMEZ
                     *** @change : Se valida que viene una reimpresion
                     * correspondiente al proceso certificado ** y se setea el
                     * nombre del certificado. ** Caso Mantis : 11598
                     */
                    if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                        impRec.setTipoCertificadoNombre(getNombreTipoCertificado(turno));
                    }

                    impRec.setReimpresion(true);

                    if (idCirculoPortal != null && idCirculoPortal.equals(turno.getIdCirculo())) {
                        impRec.setTurnoInternet(true);
                    }

                    impRec.setUsuarioGeneraRecibo(local_UsuarioGeneraReciboMsg);

                    // -----------------------------------------------------------
                    if (liquidacion instanceof LiquidacionTurnoCertificado) {
                        LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
                        String idTipoCertificado = liquidaCert.getTipoCertificado().getIdTipoCertificado();
                        impRec.setTipoCertificadoId(idTipoCertificado);
                    }

                    //verifica si el certificado es de mayor extension.
                    if (solicitud instanceof SolicitudCertificado) {
                        boolean esMayorExtension = false;
                        long numAnota = -1;
                        FolioPk fid = this.getFolio_ID(solicitud);
                        if (fid != null) {
                            numAnota = forseti.getCountAnotacionesFolio(fid, CCriterio.TODAS_LAS_ANOTACIONES, null);
                            esMayorExtension = forseti.mayorExtensionFolio(fid.idMatricula);
                        } else {
                            Log.getInstance().error(ANImprimirFolio.class, "NO FUE POSIBLE DETERMINAR SI EL FOLIO ES DE MAYOR EXTENSION");
                            //se asume false, pero no se sabe.
                            esMayorExtension = false;
                        }
                        impRec.setMayorExtension(esMayorExtension);
                    }

                    // bug 3942
                    /**
                     * @author : Julio Alcázar Rivas
                     * @change : se cambia la reimpresion de la solicitud de
                     * certificados masivos exento a tamaño carta Caso Mantis :
                     * 000941
                     */
                    if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudDevolucion
                            || (solicitud instanceof SolicitudCertificadoMasivo && ((LiquidacionTurnoCertificadoMasivo) liquidacion).getTipoTarifa().equals(CTipoTarifa.EXENTO))) {
                        impRec.setTamanoCarta(true);
                    } // if

                    /* JAlcazar caso Mantis 00514 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
                                 * else-> proceso de construcion del objeto imprimible de recibo de fotocopia.
                     */
                    bundle = new Bundle(impRec);
                    String copyActive = hermod.getCopiaImp(impRec.getCirculo().getIdCirculo());
                    if(copyActive.equals(CHermod.ACTIVE)){
                         bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                    }
                } else {
                    /**
                     * @author : Ellery David Robles Gómez.
                     * @casoMantis : 07114.
                     * @actaRequerimietno : 218. Pantallas administrativas -
                     * Reimpresion Constancia y Recibos.
                     * @change : Condicional IF para identificar la fase en la
                     * que se encuentra el turno y poder redireccionar la
                     * reimpresion deseada.
                     */
                    if (turno.getIdFase().equals(CFase.FOT_LIQUIDACION)) {
                        ImprimibleFotocopiaCrearSolicitud impRec = new ImprimibleFotocopiaCrearSolicitud(turno, solicitud_fotocopia, circulo, CTipoImprimible.RECIBO);
                        impRec.setTamanoCarta(false);
                        impRec.setFechaImpresionServidorFormatted(fechaImpresion);
                        impRec.setUsuario(user);
                        impRec.setReimpresion(true);
                        bundle = new Bundle(impRec);
                        String copyActive = hermod.getCopiaImp(circulo.getIdCirculo());
                        if(copyActive.equals(CHermod.ACTIVE)){
                             bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                        }
                    }

                    if (turno.getIdFase().equals(CFase.FOT_FOTOCOPIA)) {
                        ImprimibleFotocopiaPago_SolicitudData impRec = new ImprimibleFotocopiaPago_SolicitudData(turno, solicitud_fotocopia, circulo, liquidacion, pago, null, CTipoImprimible.RECIBO);
                        impRec.setReimpresion(true);
                        impRec.setUsuarioGeneraRecibo(local_UsuarioGeneraReciboMsg);
                        impRec.setFechaImpresionServidorFormatted(fechaImpresion);
                        bundle = new Bundle(impRec);
                        String copyActive = hermod.getCopiaImp(circulo.getIdCirculo());
                        if(copyActive.equals(CHermod.ACTIVE)){
                             bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                        }
                    }
                }

                String uid = evento.getUid();
                /*
					 boolean ok= this.imprimirRecibo(impRec,turno,uid);
					if (!ok)
					  throw new EventoException("Número de intentos agotados, No se pudo generar el recibo");
                 */

                //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
                //INGRESO DE INTENTOS DE IMPRESION
                try {
                    String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
                    if (intentosImpresion != null) {
                        Integer intentos = new Integer(intentosImpresion);
                        maxIntentos = intentos.intValue();
                    } else {
                        Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                        maxIntentos = intentosDefault.intValue();
                    }

                    //INGRESO TIEMPO DE ESPERA IMPRESION
                    String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_FOLIO);
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

                try {
                    printJobs.enviar(uid, bundle, maxIntentos, espera);
                    SolicitudPk idSolicitud = new SolicitudPk();
                    idSolicitud.idSolicitud = solicitud.getIdSolicitud();
                    hermod.incrementarIntentoReimpresionRecibo(idSolicitud);
//					se manda a imprimir el recibo por el identificador unico de usuario
                } catch (Throwable t) {
                    t.printStackTrace();
                }

            } catch (ImpresionException ie) {
                throw ie;
            } catch (Throwable t) {
                t.printStackTrace();
                throw new ImpresionException("Error al imprimir el folio: " + t.getMessage());
            }
        }

        EvnRespImprimirFolio evRespuesta = new EvnRespImprimirFolio(usuarios, EvnRespImprimirFolio.IMPRESION_FOLIO_OK);
        return evRespuesta;
    }

    /** @author : FPADILLA, HGOMEZ
     *** @change : Retorna el nombre del tipo de certificado.
     *** Caso Mantis : 11598
     */
    private String getNombreTipoCertificado(Turno turno) {
        if(turno.getSolicitud() != null && 
                turno.getSolicitud().getLiquidaciones()!=null&&
                turno.getSolicitud().getLiquidaciones().size()>0){
                
            LiquidacionTurnoCertificado certificado = 
                    (LiquidacionTurnoCertificado) turno.getSolicitud().getLiquidaciones().get(0);
            return certificado.getTipoCertificado().getNombre();
        }
        return "";
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

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta obtenerUltimaSolicitudLiquidacion(EvnImprimirFolio evento) throws EventoException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = evento.getUsuario();
        String solicitud = null;
        try {
            long idUsuario = fenrir.darIdUsuario(usuario.getUsuarioId());
            UsuarioPk oidUsu = new UsuarioPk();
            oidUsu.idUsuario = idUsuario;
            CirculoPk oidCirc = new CirculoPk();
            oidCirc.idCirculo = evento.getCirculo().getIdCirculo();
            
            solicitud = hermod.getUltimaSolicitudLiquidacion(oidUsu, oidCirc);
            
            EvnRespImprimirFolio resp = new EvnRespImprimirFolio(solicitud, EvnRespImprimirFolio.ULTIMA_SOLICITUD_LIQUIDACION);
            return resp;

        } catch (Throwable e) {
            Log.getInstance().error(ANImprimirFolio.class, e);
            throw new EventoException("Error al obtener el último turno impreso: " + e.getMessage());
        }
    }
   
// -----------------------------------------------------------------------------

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta obtenerUltimoTurnoImpresoProceso(EvnImprimirFolio evento) throws EventoException {
        org.auriga.core.modelo.transferObjects.Usuario usuario = evento.getUsuario();
        long proceso = evento.getProceso();
        Turno turno = null;
        try {
            long idUsuario = fenrir.darIdUsuario(usuario.getUsuarioId());
            UsuarioPk oidUsu = new UsuarioPk();
            oidUsu.idUsuario = idUsuario;
            ProcesoPk oidProc = new ProcesoPk();
            oidProc.idProceso = proceso;
            CirculoPk oidCirc = new CirculoPk();
            oidCirc.idCirculo = evento.getCirculo().getIdCirculo();

            //TFS 4406: se debe poder reimprimir el ultimo turno de pago de mayor valor
            if (evento.isMayorValor()) {
                turno = hermod.getUltimoTurnoMayorValorUsuario(oidUsu, oidProc, oidCirc);
            } else {
                //-*-*-*-*-*-*-*-*
                turno = hermod.getUltimoTurnoProcesoUsuario(oidUsu, oidProc, oidCirc);
            }

            List turnosSolicitudesAsociadas = new ArrayList();
            if (turno != null) {
                if (proceso == Long.parseLong(CProceso.PROCESO_CERTIFICADOS_MASIVOS) || proceso == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                    Solicitud solicitud = turno.getSolicitud();
                    if (solicitud instanceof SolicitudCertificadoMasivo) {
                        turnosSolicitudesAsociadas = new ArrayList();
                        SolicitudCertificadoMasivo solCert = (SolicitudCertificadoMasivo) solicitud;
                        List solHijos = solCert.getSolicitudesHijas();
                        if (!solHijos.isEmpty()) {
                            for (Iterator iter = solHijos.iterator(); iter.hasNext();) {
                                SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                                SolicitudPk solid = new SolicitudPk();
                                solid.idSolicitud = solAsoc.getIdSolicitud1();
                                Turno turnoAsoc = forseti.getTurnoBySolicitud(solid);
                                if (turnoAsoc != null) {
                                    turnosSolicitudesAsociadas.add(turnoAsoc.getIdWorkflow());
                                }
                            }
                        }
                    } else if (solicitud instanceof SolicitudRegistro) {
                        turnosSolicitudesAsociadas = new ArrayList();
                        SolicitudRegistro solCert = (SolicitudRegistro) solicitud;
                        List solHijos = solCert.getSolicitudesHijas();
                        if (!solHijos.isEmpty()) {
                            for (Iterator iter = solHijos.iterator(); iter.hasNext();) {
                                SolicitudAsociada solAsoc = (SolicitudAsociada) iter.next();
                                SolicitudPk solid = new SolicitudPk();
                                solid.idSolicitud = solAsoc.getIdSolicitud1();
                                Turno turnoAsoc = forseti.getTurnoBySolicitud(solid);
                                if (turnoAsoc != null) {
                                    turnosSolicitudesAsociadas.add(turnoAsoc.getIdWorkflow());
                                }
                            }
                        }
                    }
                }
            } else {
                throw new ImpresionException("No hay recibos para reimprimir de ese proceso");
            }

            EvnRespImprimirFolio resp = new EvnRespImprimirFolio(turno.getIdWorkflow(), EvnRespImprimirFolio.ULTIMO_TURNO_PROCESO);
            resp.setTurnosAsociados(turnosSolicitudesAsociadas);
            return resp;
        } catch (Throwable e) {
            Log.getInstance().error(ANImprimirFolio.class, e);
            throw new EventoException("Error al obtener el último turno impreso: " + e.getMessage());
        }
    }

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un folio específico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirFolio imprimirFolio(EvnImprimirFolio evento) throws   EventoException {

		List usuarios = null;
                /**
                * @author     : Julio Alcazar
                * @change     : nuevas variables para el flujo.
                * Caso Mantis : 0009044: Acta - Requerimiento No 036_151 - Impresión de Folios
                */      
                Rol  rol = null;
                boolean folioimpresioespecial = false;
		ImprimibleFolioSimple impFolioSimple = null;
		try {
			Folio folio = evento.getFolio();

			String matricula = folio.getIdMatricula();
			//String zonaReg = forseti.getZonaRegistral(matricula);
			FolioPk fid = new FolioPk();
			fid.idMatricula = matricula;
			Folio folioSimple = forseti.getFolioByID(fid);

                        /*
                        * @author     : Julio Alcazar
                        * @change     : Se determina si el usuario tiene el rol SIR_ROL_IMPRESION_FOLIO_ESPECIAL para permitirle o no, imprimir
                        *               folios de otros circulos.
                        * Caso Mantis : 0009044: Acta - Requerimiento No 036_151 - Impresión de Folios
                        */
                        List roles = evento.getRoles();
			Iterator itRoles = roles.iterator();
                        while(itRoles.hasNext()){
                            rol = (Rol)itRoles.next();
                            if(rol.getRolId().equals(CRoles.SIR_ROL_IMPRESION_FOLIO_ESPECIAL)){
                                folioimpresioespecial = true;
                                break;
		            }
			}
                        if(!folioimpresioespecial){
                            Circulo circuloactual = evento.getCirculo();
                            if(!circuloactual.getIdCirculo().equals(folioSimple.getCirculo())){
                                throw new EventoException("No se puede Imprimir Folios de otros circulos.");
                            }
                        }
                        /*
                         * @author      : Julio Alcázar Rivas
                         * @change      : Bloque la posibilidad de imprimir folios en estado trasladado
                         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                         */
                        if (forseti.trasladadoFolio(matricula)) {
                            throw new EventoException("El folio identificado con matrícula:" + matricula + " ha sido trasladado.");
                        }
			
			Usuario usuario = evento.getUsuarioNeg();
			
			if (folioSimple == null) {
				folioSimple = forseti.getFolioByID(fid,usuario);
//				TFS 5362: SI EL FOLIO ES TEMPORAL SE DEBEN CARGAR LAS ANOTACIONES
				if(folioSimple.getAnotaciones()==null || folioSimple.getAnotaciones().isEmpty()){
					folioSimple.setAnotaciones(forseti.getAnotacionesFolioTMP(fid));
				}
				
			}else{
//				TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
				if(folioSimple.getAnotaciones()==null || folioSimple.getAnotaciones().isEmpty()){
					folioSimple.setAnotaciones(forseti.getAnotacionesFolio(fid));
				}
			}
			
			List padres=forseti.getFoliosPadre(fid);
			List hijos=forseti.getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(fid);
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Nuevo listado de folios derivados
                        **/
                        List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

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
			impFolioSimple = new ImprimibleFolioSimple(folioSimple, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.FOLIO_SIMPLE, tbase1, tbase2);
                        /* @autor          : JATENCIA 
                         * @mantis         : 0014985 
                         * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                         */
                        Traslado traslado = hermod.getFolioDestinoTraslado(matricula);
                        impFolioSimple.setInfoTraslado(traslado);
                        /* Fin del Bloque */
			//se manda a imprimir el recibo por el identificador unico de usuario
		} catch (ForsetiException t) {
			throw new ImpresionException(t.getCause().getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new EventoException("Error al imprimir el folio: " + t.getMessage());

		}

		String uid = evento.getUid();

		if (impFolioSimple == null)
			throw new EventoException("Error al imprimir el folio: " + "el imprimible es null");

        this.imprimir(impFolioSimple,uid);

		EvnRespImprimirFolio evRespuesta =
			new EvnRespImprimirFolio(usuarios, EvnRespImprimirFolio.IMPRESION_FOLIO_OK);
		return evRespuesta;
	}

	private EvnRespImprimirFolio obtenerUltimoTurnoImpreso(EvnImprimirFolio evento) throws EventoException {

        Pago pago=null;
        int maxIntentos;
        int espera;
        Turno turno = null;
        Usuario usuNeg=evento.getUsuarioNeg();
        try {

            org.auriga.core.modelo.transferObjects.Usuario usuario = evento.getUsuario();

            long idUsuario = fenrir.darIdUsuario(usuario.getUsuarioId());
            List rolesUsuario = fenrir.darRolUsuario(idUsuario);

            if(rolesUsuario != null) {
            	
            	List estacionesUsuario = fenrir.darEstacionUsuario(idUsuario, (Rol)rolesUsuario.get(0));
                Estacion estacion = (Estacion)estacionesUsuario.get(0);
                EstacionReciboPk estacionID = new EstacionReciboPk();
                estacionID.idEstacion = estacion.getEstacionId();
                EstacionRecibo estacionRecibo = hermod.getEstacionRecibo(estacionID);
                long recibo = hermod.getNextNumeroReciboSinAvanzar(estacionID,usuNeg, turno.getIdProceso());
                if(recibo == estacionRecibo.getNumeroInicial())
                    recibo = estacionRecibo.getNumeroFinal();
                else
                    recibo--;
                String numRecibo = String.valueOf(recibo);
                pago = hermod.getPagoByNumeroRecibo(numRecibo);
                
                Solicitud solicitud = pago.getLiquidacion().getSolicitud();
                SolicitudPk solicitudId = new SolicitudPk();
                solicitudId.idSolicitud = solicitud.getIdSolicitud();
                turno = hermod.getTurnoBySolicitud(solicitudId);

                
            }


        } catch (Throwable t) {
            t.printStackTrace();
            throw new EventoException("Error al obtener el último turno impreso: " + t.getMessage());
        }

        EvnRespImprimirFolio evRespuesta =
            new EvnRespImprimirFolio(turno, EvnRespImprimirFolio.OBTENER_ULTIMO_TURNO_IMPRESO_OK);
        return evRespuesta;
    }

	private void imprimir(ImprimibleBase imprimible, String uid)throws EventoException
	{
		int maxIntentos;
		int espera;

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
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
		    String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_FOLIO);
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
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundle, maxIntentos, espera);

		} catch (PrintJobsException t) {
			t.printStackTrace();
			if(t.getIdImpresion()!=0){
				throw new EventoException("Se presentaron problemas de comunicación al realizar la impresión del folio, coloque el número ("+t.getIdImpresion()+") en el aplicativo de impresión SIR, para realizar esta impresión",t);
			}
		}catch(Throwable t){
		}

	}

//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un certificado específico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirFolio imprimirCertificado(EvnImprimirFolio evento) throws EventoException
	{
		//HAY QUE COLOCAR ESTA VARIABLE SI SE DESEA QUE EL SISTEMA VALIDE QUE SE PUEDA REIMPRIMIR
		//SOLO SI SE HAN AGOTA LOS INTENTOS DEL USUARIO SIR_ROL_CAJERO Y DEL USUARIO SIR_ROL_IMPRESION_ESPECIAL
		boolean validarImpresionesAnteriores = false;
		boolean         esUsuarioRegistrador = false;

        Rol         rol = null;
        Turno     turno = null;
        Circulo circulo = null;
        Usuario usuario = null;
        ProcesoPk   oid = null;
        Proceso proceso = null;
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se definio el objeto liquidacion y folio global al metodo y define la variable numReimpresionesCert
         * Caso Mantis  :   02359
         */
        LiquidacionTurnoCertificado liquidacion = null;
        Folio folio = null;
        
        Turno        turnoPersistente = null;
        UsuarioCirculo usuarioCirculo = null;

        SolicitudCertificado solicitudCert = null;

        String idCirculoUsuario = null;
        String   idCirculoTurno = null;

        int numImpresionesSolicitadas = 0;
        int        numImpresionesCert = 0;
        int            maxImprUsuario = 0;
        int         maxNumImpresiones = 0;
        
        Long idProceso = null;

        List roles    = null;
        List usuarios = null;
        
        Iterator itRoles = null;

        

		try {
			turno   = evento.getTurno();
			circulo = evento.getCirculo();
			usuario = fenrir.getUsuario(evento.getUsuario());
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se define la variable circuloPk
                         * Caso Mantis  :   02359
                         */
                        CirculoPk circuloPk = new CirculoPk();
                        circuloPk.idCirculo = circulo.getIdCirculo();
                        idCirculoUsuario = null;

			if(!usuario.getUsuarioCirculos().isEmpty()){
				usuarioCirculo   = (UsuarioCirculo)usuario.getUsuarioCirculos().get(0);
				idCirculoUsuario = usuarioCirculo.getIdCirculo();
			}
			else{
				throw new ImpresionException("El usuario no tiene círculos asociados");
			}

			turnoPersistente = hermod.getTurnobyWF(turno.getIdWorkflow());
			
			//comprobar que el turno no esta anulado
			if (turnoPersistente.getAnulado()!= null && turnoPersistente.getAnulado().equals(CTurno.TURNO_ANULADO)){
				throw new EventoException("El turno esta anulado");
			}
			evento.setTurno(turnoPersistente);
			idCirculoTurno = turnoPersistente.getIdCirculo();

			//Restricciones:
			//Se debe validar que el turno ingresado, pertenece al circulo del usuario, y es un turno de certificado
			if(!idCirculoUsuario.equals(idCirculoTurno) && !idCirculoUsuario.equals(turnoPersistente.getSolicitud().getCirculo().getIdCirculo())){
				throw new ImpresionException("El círculo del usuario no corresponde con el círculo del turno.");
			}
			//SIR-98
			//validar que el turno no sea de orden nacional
			if(!turnoPersistente.getIdCirculo().equals(turnoPersistente.getSolicitud().getCirculo().getIdCirculo())){
				throw new ImpresionException("No se permiten reimpresion de certificados de orden nacional");
			}

			//Restricciones:
			//debe ser un turno de certificado
			Solicitud solicitud = turnoPersistente.getSolicitud();
			numImpresionesSolicitadas = evento.getNumeroImpresiones();


			if(!(solicitud instanceof SolicitudCertificado)){
				throw new ImpresionException("El turno no es de certificados.");
			}

			solicitudCert = (SolicitudCertificado)solicitud;

			numImpresionesCert = solicitudCert.getNumImpresiones();

			idProceso = new Long(CProceso.PROCESO_CERTIFICADOS);
			oid = new ProcesoPk();
			oid.idProceso = idProceso.longValue();

			proceso = hermod.getProcesoById(oid);
			
			roles   = evento.getRoles();
			itRoles = roles.iterator();

			maxImprUsuario    = 0;
            maxNumImpresiones = 0;

            while(itRoles.hasNext()){
				rol = (Rol)itRoles.next();
				//TFS 4588: EL USUARIO CON ROL REGISTRADOR PUEDE REIMPRIMIR LAS VECES QUE DESEE
				if(rol.getRolId().equals(CRol.SIR_ROL_REGISTRADOR)){
					esUsuarioRegistrador = true;
				}
				maxNumImpresiones = hermod.getNumeroMaximoImpresiones(rol,proceso);
                
				if (maxImprUsuario<maxNumImpresiones){
					maxImprUsuario=maxNumImpresiones;
				}
			}

                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   se elimino del if la validacion de esUsuarioRegistrador para restringir el numero de impresiones a los usuarios
                         *  que posean el rol CRol.SIR_ROL_REGISTRADOR
                         * Caso Mantis  :   02359
                         */
                        if(numImpresionesSolicitadas >	solicitudCert.getNumeroCertificados()){
				throw new ImpresionException("No se puede reimprimir más veces del número de certificados solicitados.");
			}

                        //Validacion que el certificado a imprimir no sea EXENTO
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se definio el objeto liquidacion global al metodo
                         * Caso Mantis  :   02359
                         */
			liquidacion = new LiquidacionTurnoCertificado();
			List liquidaciones = solicitud.getLiquidaciones();
			for(int i=0;i<liquidaciones.size();i++){
				double id = new Double(((LiquidacionTurnoCertificado)liquidaciones.get(i)).getIdLiquidacion()).doubleValue();
				if(id==solicitud.getLastIdLiquidacion()){
					liquidacion = (LiquidacionTurnoCertificado)liquidaciones.get(i);
				}
			}
			
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Modificar la validacion de tipos de certificados
                         * Inmediatos EvnImprimirFolio.IMPRIMIR_CERTIFICADO y Pertenecia EvnImprimirFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA
                         * Caso Mantis  :   02359
                         */
                        if(evento.getTipoEvento().equals(EvnImprimirFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA)){
                                if(!(liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))){
					throw new ImpresionException("No se puede reimprimir el oficio de pertenencia. El certificado no es de Pertenencia");
                                }
			}
                        
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se valida que el turno se encuentre en la fase de entrega CFase.CER_ENTREGAR o CFase.FINALIZADO
                         * Caso Mantis  :   02359
                         */
                        if(!turnoPersistente.getIdFase().equals(CFase.CER_ENTREGAR) && !turnoPersistente.getIdFase().equals(CFase.FINALIZADO)){
                             throw new ImpresionException("El Turno debe estar en Fase de ENTREGA CERTIFICADOS o FINALIZADO");
                        }


                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se elimina la variable  boolean certPertenenciaSinFolio
                         * Caso Mantis  :   02359
                         */
			folio = new Folio();
			/******** En caso de que el certificado sea de pertencia y no tenga asociado
			 * un matricula no se imprime el certificado y no se lanza la excepcion ********/
			try{
				folio = this.getFolioFromTurno(turnoPersistente);
				if (folio == null){
					throw new ImpresionException("Error al imprimir el folio: el folio no ha podido cargarse.");
				}
			}catch(EventoException excepcion){
                            if(!evento.getTipoEvento().equals(EvnImprimirFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA)){
                                throw excepcion;
                            }
			}
               TurnoPk idTurno = new TurnoPk();
               idTurno.anio = turnoPersistente.getAnio();
               idTurno.idCirculo = turnoPersistente.getIdCirculo();
               idTurno.idProceso = turnoPersistente.getIdProceso();
               idTurno.idTurno = turnoPersistente.getIdTurno();
                /*
               * @author      :   Julio Alcázar Rivas
               * @change      :   Se agrega if para solo reimprimir los certificados inmediatos y los oficios de pertencia
               * Caso Mantis  :   02359
               */
              if(!evento.getTipoEvento().equals(EvnImprimirFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA)){

			/*
                        * @author      :   Julio Alcázar Rivas
                        * @change      :   Se verifican los dias habiles para la reimpresion de un certificado
                        * Caso Mantis  :   02359
                        */                        
                        if((liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID))){
                                /* @author      :   Julio Alcázar Rivas
                                 * @change      :   Se agrega nuevo parametro a la signatura del metodo int imprCert
                                 * Caso Mantis  :   07647
                                 */
                                this.DiasHabilesCertificado(turnoPersistente, circuloPk, CFase.CER_SOLICITUD, idTurno, maxImprUsuario, numImpresionesCert);
                        }else{
                                /* @author      :   Julio Alcázar Rivas
                                 * @change      :   Se agrega nuevo parametro a la signatura del metodo int imprCert
                                 * Caso Mantis  :   07647
                                 */
                                this.DiasHabilesCertificado(turnoPersistente, circuloPk, turnoPersistente.getIdFase(),idTurno, maxImprUsuario, numImpresionesCert);
                        }

                        List solicitudesPadres = solicitudCert.getSolicitudesPadres();
			SolicitudAsociada solAsociada = null;
			Solicitud solPadre = null;
			
			if (solicitudesPadres != null && solicitudesPadres.size() > 0) {
				solAsociada = (SolicitudAsociada) solicitudesPadres.get(0);
				solPadre = solAsociada.getSolicitudPadre();
			}
			
			boolean validarExento = true;
			
			// El turno pertenece a un turno de Registro 
			if( solPadre != null && solPadre instanceof SolicitudRegistro){
				validarExento = false;
			}
			

			if (liquidacion.getTipoTarifa()!=null && validarExento) {
				if(liquidacion.getTipoTarifa().equals(CTipoTarifa.EXENTO)){
					throw new ImpresionException("No se puede reimprimir un certificado con tipo de tarifa EXENTO.");
				}
			}

                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   validaciones de tipos de certificados permitidos
                         * Caso Mantis  :   02359
                         */
                        if ((!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID))&&
                            (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ASOCIADO_ID))&&
                            (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))&&
                            (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_INMEDIATO_ID))&&
                            (!liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_AMPLIACION_TRADICION_ID))) {

                            throw new ImpresionException("no se permite reimpresion de certificados diferentes a los tipos: "
                                    + "Inmediatos, Pertenencia, Asociados, Antiguo Sistema y Ampliacion de Tradición.");
		        }

                       /*
                        * @author      : Julio Alcázar Rivas
                        * @change      : Validaciones para realizar reimpresiones de turno de certificados
                        * Caso Mantis  :   07647
                        */
                        List turnosHist = turnoPersistente.getHistorials();
			for (Iterator iter = turnosHist.iterator(); iter.hasNext();) {
				TurnoHistoria turnoHistoria = (TurnoHistoria) iter.next();

                                if ( ((liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)       ||
                                       liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_AMPLIACION_TRADICION_ID)  ||
                                       liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID)))  &&
                                     (turnoHistoria.getFase().equals(CFase.ANT_REVISION_INICIAL) && turnoHistoria.getRespuesta().equals(CRespuesta.NEGAR)))  {

                                   throw new ImpresionException("El certificado de tipo: "+liquidacion.getTipoCertificado().getNombre() +" fue negado por lo tanto no se puede realizar reimpresión.");
		                }

                         }

                         if (liquidacion.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_ASOCIADO_ID)) {
                             CertificadosSIR certificadosSIR = new CertificadosSIR();
                             if (!certificadosSIR.CertificadoAsociadoVal(turno.getIdWorkflow())){
                                   throw new ImpresionException("no se permite reimpresion de certificados asociados a registro con resolución devuelto");

                             }

                          
		         }

			/*if(solicitudCert.getNumImpresiones() > solicitudCert.getNumeroCertificados()){
				throw new ImpresionException("El certificado ya fue impreso el número de copias solicitadas. No puede reimprimirse.");
			}*/



			if(validarImpresionesAnteriores){
				Rol cajero = new Rol();
				cajero.setRolId(CRoles.CAJERO_CERTIFICADOS);
				cajero.setNombre(CRoles.CAJERO_CERTIFICADOS);

				Rol impEspecial = new Rol();
				impEspecial.setRolId(CRoles.REIMPRESION_ESPECIAL);
				impEspecial.setNombre(CRoles.REIMPRESION_ESPECIAL);

				Proceso procesoCert = new Proceso();
				procesoCert.setIdProceso(new Long(CProceso.PROCESO_CERTIFICADOS).longValue());
				procesoCert.setNombre(CProceso.NOMBRE_PROCESO_CERTIFICADOS);

				int numeroImpresionesCajero = getNumMaxImpresiones(cajero,procesoCert);
				int numeroImpresionesImpEsp = getNumMaxImpresiones(impEspecial,procesoCert);
				int totalImpAnteriores = numeroImpresionesCajero + numeroImpresionesImpEsp;

				if( solicitudCert.getNumImpresiones() < totalImpAnteriores ){
					throw new ImpresionException("No se puede reimprimir el certificado, porque no se han terminado los intentos de reimpresión en el cajero y el usuario reimpresión especial.");
				}
			}

                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   se elimino del if la validacion de esUsuarioRegistrador para restringir el numero de impresiones a los usuarios
                         *  que posean el rol CRol.SIR_ROL_REGISTRADOR y se agrega la validacion para la fase CFase.FINALIZADO
                         * Caso Mantis  :   02359
                         */
			if(!validarImpresionesAnteriores){
				
                                if (maxImprUsuario<=numImpresionesCert-1 && 
                                        (turnoPersistente.getIdFase().equals(CFase.CER_ENTREGAR)|| turnoPersistente.getIdFase().equals(CFase.FINALIZADO))){
					throw new ImpresionException("Se ha alcanzado el número máximo("+maxImprUsuario+") de impresiones para esa solicitud");
				}			       
			}

			String uid = evento.getUid();
                        List turnoTramite = null;
                        boolean isCertificadoTramite = false;
                        boolean isCertificadoEspecial = false;
                        boolean isCertificadoActuacion = false;
			
			//}
					
	                /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se elimina el uso de la variable  boolean certPertenenciaSinFolio
                         * Caso Mantis  :   02359
                         */
			//gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
			if(!evento.isCertificadoEspecial()){
                            if (forseti.enTramiteFolio(folio.getIdMatricula()) && forseti.bloqueadoFolio(folio.getIdMatricula())) {
                                    throw new ImpresionException("El folio esta en tramite y se encuentra bloqueado.");
                            }
                            if (forseti.enTramiteFolio(folio.getIdMatricula())) {
                                    throw new ImpresionException("El folio esta en tramite.");
                            }
                            if (forseti.bloqueadoFolio(folio.getIdMatricula())){
                                    throw new ImpresionException("El folio esta bloqueado.");
                            }
                        } else{
                            if (forseti.enTramiteFolio(folio.getIdMatricula())) {
                                turnoTramite = forseti.getTurnosTramiteFolio((folio.getIdMatricula()));
                                isCertificadoTramite = true;
                            }
                            if (forseti.bloqueadoFolio(folio.getIdMatricula())) {
                                turnoTramite = forseti.getTurnosTramiteFolio((folio.getIdMatricula()));
                                isCertificadoTramite = true;
                            }
                            if (forseti.isActuacionAdministrativa(folio.getIdMatricula())) {
                                isCertificadoActuacion = true;
                            }
                            isCertificadoEspecial = true;
                        }
                        
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = turno.getIdCirculo();


			FolioPk fid = new FolioPk();
			fid.idMatricula = folio.getIdMatricula();

			List padres=forseti.getFoliosPadre(fid);

			List anotacionesHijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Nuevo listado de folios derivados
                        **/
                        List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

			String fechaImpresion= this.getFechaImpresion();
			//obtener textos base de los separadores
			String tbase1 ="";
			String tbase2 = "";
			List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
			for(Iterator i= variablesImprimibles.iterator(); i.hasNext();){
				OPLookupCodes op = (OPLookupCodes) i.next();
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
					tbase1= op.getValor();
				}
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
					tbase2 = op.getValor();
				}
			}
			ImprimibleCertificado impCerti = new ImprimibleCertificado(folio, turnoPersistente, padres, anotacionesHijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.CERTIFICADO_INMEDIATO, tbase1, tbase2);
			impCerti.setPrintWatermarkEnabled(true);
			impCerti.setUsuario(usuario);
                        impCerti.setIsCertificadoEspecial(isCertificadoEspecial);
                        impCerti.setIsCertificadoTramite(isCertificadoTramite);
                        impCerti.setCertificadoActuacion(isCertificadoActuacion);
                        impCerti.setTurnoTramite(turnoTramite);
                          /**
                         * @author     : Carlos Torres
                         * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                         */
                        Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                        impCerti.setInfoTraslado(traslado);
                        /**
                        * @Autor: Edgar Lora
                        * @Mantis: 0006493
                        */
                        String text = turno.getIdWorkflow() +"/"+ liquidacion.getIdSolicitud();
                        byte [] key  = new byte [8];
                        key[0] = 5;
                        impCerti.setNis(Encriptador.encriptar(text, key, "DES"));
                        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
                        impCerti.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));

			String nombre = "";
			String archivo = "";

			String idCirculoFolio = folio.getCirculo();
			impCerti = this.getImprimibleCertificadoConFirmaDigital(impCerti,idCirculoFolio);
			//llenar parametros hashtable
			Hashtable tabla= new Hashtable();
			tabla.put(Processor.IMPRIMIBLE,impCerti);
			if(evento.getImpresoraSeleccionada()!=null){
				tabla.put(Processor.IMPRESORA_SELECCIONADA, evento.getImpresoraSeleccionada());
			}
			tabla.put(Processor.NUM_COPIAS_IMPRIMIBLE, new Integer(evento.getNumeroImpresiones()));
			tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
			tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(Processor.IMPRIMIBLE,impCerti);

			//obtener imprimible base
			ImprimibleBase imprimibleb = (ImprimibleBase)tabla.get(Processor.IMPRIMIBLE);
			boolean resultadoImpr=imprimirAdministrador(circulo.getIdCirculo(),imprimibleb,tabla);

			if (resultadoImpr){
				TurnoHistoria turnoHist=new TurnoHistoria();
				turnoHist.setUsuario(usuario);
				turnoHist.setUsuarioAtiende(usuario);
				turnoHist.setFase(CFase.CER_IMPRIMIR);
				turnoHist.setIdTurno(turno.getIdWorkflow());
				turnoHist.setNumeroCopiasReimpresion(evento.getNumeroImpresiones());
				turnoHist.setIdMatriculaImpresa(folio.getIdMatricula());
				hermod.addTurnoHistoriaToTurno(turnoPersistente,turnoHist);

                               /*
                                * @author      :   Julio Alcázar Rivas
                                * @change      :   si numImpresionesCert es igual a 0 se incrementa el numero para mantener la logica de reimpresion
                                * Caso Mantis  :   02359
                                */
                                if(numImpresionesCert == 0){
                                   numImpresionesCert++; 
                                }
                                solicitudCert.setNumImpresiones(numImpresionesCert+1);
                                Solicitud resultadoSolicitud = null;
	   	    	resultadoSolicitud = hermod.updateSolicitudCertificado(solicitudCert);
	   	    	if (resultadoSolicitud == null){
	   	    		Log.getInstance().error(ANImprimirFolio.class,"Error en el método que actualiza la solicitud");
	   	    	}
			}
	        
	      /*
               * @author      :   Julio Alcázar Rivas
               * @change      :   ELSE en el cual se realiza la reimpresion de los oficios de pertenencia
               * Caso Mantis  :   02359
               */
              }else {
	        imprimirOficio(evento, folio, turnoPersistente, solicitudCert);
	      }

			//this.imprimir(impCerti,uid);

			//se manda a imprimir el recibo por el identificador unico de usuario

			//Se agrega la nota informativa al turno.
			String notaId = evento.getTipoNota();
			String notaDescripcion = evento.getDescripcionNota();


			TipoNota tipoNota = new TipoNota();
			tipoNota.setIdTipoNota(notaId);

			Nota notaAgregada = new Nota();
			notaAgregada.setTiponota(tipoNota);
			notaAgregada.setDescripcion(notaDescripcion);
			notaAgregada.setAnio(turnoPersistente.getAnio());
			notaAgregada.setIdFase("CER_REIMPRESION_CERTIFICADOS");
			notaAgregada.setIdCirculo(idCirculoUsuario);
			notaAgregada.setIdProceso(1);
			notaAgregada.setTime(new Date());
			notaAgregada.setUsuario(usuario);


			hermod.addNotaToTurno(notaAgregada, idTurno);

		} catch (Throwable t) {
			t.printStackTrace();
			throw new ImpresionException("Error al imprimir el folio: " + t.getMessage());

		}


		EvnRespImprimirFolio evRespuesta =
			new EvnRespImprimirFolio(usuarios, EvnRespImprimirFolio.IMPRESION_FOLIO_OK);
		return evRespuesta;
	}


        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   DiasHabilesCertificado metodo que verifica los dias habilies por tipo de certificado
         * Caso Mantis  :   02359
         -------------------------------------------------------------------------------------------------------
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega nuevo parametro a la signatura del metodo int imprCert
         * Caso Mantis  :   07647
         */
        private void DiasHabilesCertificado(Turno turno, CirculoPk circuloPk, String fase, TurnoPk idTurno, int imprUsuario, int imprCert) throws EventoException, Throwable{
            int numDiasHabiles = 0;
                List turnosHistoria = turno.getHistorials();
			for (Iterator iter = turnosHistoria.iterator(); iter.hasNext();) {
				TurnoHistoria turnoHistoria = (TurnoHistoria) iter.next();
                                if (turnoHistoria.getFase().equals(fase)) {
                                    Calendar calendarsol = Calendar.getInstance();
                                    calendarsol.setTime(turnoHistoria.getFecha());
                                    calendarsol.add(Calendar.DATE, 1);
                                    int dia = calendarsol.get(Calendar.DAY_OF_MONTH);
                                    int mes = calendarsol.get(Calendar.MONTH);
                                    int anio = calendarsol.get(Calendar.YEAR);
                                    GregorianCalendar fechasol = new GregorianCalendar(anio, mes, dia);
                                    Calendar calendaractual = Calendar.getInstance();
                                    int dia1 = calendaractual.get(Calendar.DAY_OF_MONTH);
                                    int mes1 = calendaractual.get(Calendar.MONTH);
                                    int anio1 = calendaractual.get(Calendar.YEAR);
                                    GregorianCalendar fechaactual = new GregorianCalendar(anio1, mes1, dia1);


                                    while((fechasol.getTime().compareTo(fechaactual.getTime()) <= 0)){
                                        try {
                                                if(!forseti.isFestivo(fechasol.getTime(),circuloPk )){
                                                        numDiasHabiles++;
                                                }
                                        } catch (ForsetiException e) {
                                                Log.getInstance().error(AnTrasladoTurno.class,e.getMessage(), e);
                                                throw new EventoException(e.getMessage(), e);
                                        } catch (Throwable e) {
                                                Log.getInstance().error(AnTrasladoTurno.class,e.getMessage(), e);
                                                throw new EventoException(e.getMessage(), e);
                                        }
                                        fechasol.add(GregorianCalendar.DATE, 1);
                                    }

                                    break;

                                }

                    	}
              if (hermod.getCambioMatriculaAuditoria(idTurno)){
                    if(numDiasHabiles>4){
                        /*
                        * @author      : Julio Alcázar Rivas
                        * @change      : Validaciones para realizar reimpresiones de turno de certificados
                        * Caso Mantis  :   07647
                        */
                        TurnoPk turnoId = new TurnoPk();
                        turnoId.anio = turno.getAnio();
                        turnoId.idCirculo = turno.getIdCirculo();
                        turnoId.idProceso = turno.getIdProceso();
                        turnoId.idTurno = turno.getIdTurno();
                        if (hermod.getCambioMatriculaAuditoria(turnoId) && (imprUsuario<=imprCert-1)){
                            throw new ImpresionException("Se ha alcanzado el número máximo ("+imprUsuario+") de reimpresiones, "
                                    + "se realizo cambio de matrícula y se venció el termino establecido para la reimpresión");
                        }
                        throw new ImpresionException("El certificado solo puede ser impreso los 5 primeros dias habiles siguientes a la fecha de la fase: "+fase );
                       }
              }else
              {
                    if(numDiasHabiles>1){
                          throw new ImpresionException("El certificado solo puede ser impreso hasta el siguiente dia habil apartir de la fase: "+fase );
                    }
              }
        }

	
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un certificado de pertenencia
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un certificado de pertenencia
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   se cambia la signatura del metodo para que reciba Folio, Turno y SolicitudCertificado.
         * Ademas se realizaron diferente modificaciones al comportamiento del metodo como:
         * a) Pertimir reimpresiones en la fase finalizado CFase.FINALIZADO
         * b) Agregar la reimpresion en el historial del turno
         * Caso Mantis  :   02359
         */
	private boolean imprimirOficio(EvnImprimirFolio evento, Folio folio, Turno turnoPersistente, SolicitudCertificado solicitudCert)throws ImpresionException{
		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		TurnoHistoria turnoHistoria;
		List oficios;
                Usuario usuario = null;
                boolean resultadoImpr = false;
		try{
			usuario = fenrir.getUsuario(evento.getUsuario());
			TurnoPk turnoPk = new TurnoPk();
			turnoPk.anio = turno.getAnio();
			turnoPk.idCirculo = turno.getIdCirculo();
			turnoPk.idProceso = turno.getIdProceso();
			turnoPk.idTurno = turno.getIdTurno();
			oficios = hermod.getOficiosTurno(turnoPk);
			turnoHistoria = obtenerTurnoHistoriaActual(evento);
		
		boolean existeOficio = false;
		Oficio oficio = new Oficio();
		for(Iterator iter = oficios.iterator() ; iter.hasNext() ;){
			oficio = (Oficio)iter.next();    
			if(oficio.getTurnoHistoria().getFase().equals(turnoHistoria.getFaseAnterior()) || turnoHistoria.getFase().equals(CFase.FINALIZADO) ){
				existeOficio = true;
				break;
			}
		}
		if(!existeOficio){
			throw new ImpresionException("No existe un oficio para imprimir");
		}
                List historia = turno.getHistorials();
                int i1 = 0;
                boolean Saber =false;
                while (i1 < historia.size()) {
                    TurnoHistoria historial = (TurnoHistoria) historia.get(i1);
                    if (historial.getFase().equals(CFase.CER_OFICIO_PERTENENCIA)) {
                        if (i1 != historia.size() - 1) {
                        Saber = true;    
                        }
                    }
                    i1++;
                }
		ImprimibleOficioPertenencia imprimible = new ImprimibleOficioPertenencia(oficio.getTextoOficio(),CTipoImprimible.OFICIO);
                if(Saber){
                String nombre = "";
                String archivo = "";
                String cargoToPrint = "";
                String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                FirmaRegistrador firmaRegistrador = null;
                String rutaFisica = null;
                String sNombre = "";		
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = turno.getIdCirculo();
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

                                    cargo = firmaRegistrador.getCargoRegistrador();
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
                                imprimible.setCargoRegistrador(cargoToPrint);
                                imprimible.setNombreRegistrador(sNombre);

                if (rutaFisica != null) {
                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                     byte pixeles[] = null;
                    try {
                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                    } catch (Throwable e1) {

                        e1.printStackTrace();
                    }
                    imprimible.setPixelesImagenFirmaRegistrador(pixeles);
                    
                }
		
		if (nombre==null){
                    
		  nombre="";
                }
		
		
		imprimible.setCargoRegistrador(cargoToPrint);  
		imprimible.setNombreRegistrador(nombre);
	
                }
		Hashtable tabla= new Hashtable();
		tabla.put(Processor.IMPRIMIBLE,imprimible);
		if(evento.getImpresoraSeleccionada()!=null){
			tabla.put(Processor.IMPRESORA_SELECCIONADA, evento.getImpresoraSeleccionada());
		}
		tabla.put(Processor.NUM_COPIAS_IMPRIMIBLE, new Integer(evento.getNumeroImpresiones()));
		tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
		tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
		tabla.put(Processor.IMPRIMIBLE,imprimible);

                resultadoImpr=imprimirAdministrador(circulo.getIdCirculo(),imprimible,tabla);

		if (resultadoImpr){
			TurnoHistoria turnoHist=new TurnoHistoria();
			turnoHist.setUsuario(usuario);
			turnoHist.setUsuarioAtiende(usuario);
                        /*
                        * @author      : Julio Alcázar Rivas
                        * @change      : Cambio de Fase de reimpresion de Oficio de Pertenencia de CER_IMPRIMIR por CER_OFICIO_PERTENENCIA
                        * Caso Mantis  :   07647
                        */
			turnoHist.setFase(CFase.CER_OFICIO_PERTENENCIA);
			turnoHist.setIdTurno(turno.getIdWorkflow());
			turnoHist.setNumeroCopiasReimpresion(evento.getNumeroImpresiones());
			turnoHist.setIdMatriculaImpresa(folio.getIdMatricula());
			hermod.addTurnoHistoriaToTurno(turnoPersistente,turnoHist);


			Solicitud resultadoSolicitud = null;
	   	    	resultadoSolicitud = hermod.updateSolicitudCertificado(solicitudCert);
	   	    	if (resultadoSolicitud == null){
	   	    		Log.getInstance().error(ANImprimirFolio.class,"Error en el método que actualiza la solicitud");
	   	    	}
			}
                }catch(Throwable e){
			throw new ImpresionException(e.getMessage());
		}
                return resultadoImpr;

	}

	private TurnoHistoria obtenerTurnoHistoriaActual(EvnImprimirFolio evento) throws EventoException {
		Turno turno = evento.getTurno();

		List tHistorias = turno.getHistorials();
		ListIterator iter = tHistorias.listIterator();

		String faseActual = turno.getIdFase();

		TurnoHistoria activo = null;
		while (iter.hasNext()) {
			TurnoHistoria tH = (TurnoHistoria) iter.next();
			if (tH.isActivo() && tH.getFase().equals(faseActual)) {
				activo = tH;
				break;
			}
		}
		if (activo == null){
			throw new EventoException("El turno historia actual es nulo");
		}

		return activo;
	}
	
	private int getNumMaxImpresiones(Rol rol, Proceso proceso) throws EventoException {
		int maxImpresiones = 0;
		try {
			maxImpresiones = hermod.getNumeroMaximoImpresiones(rol, proceso);
		} catch (HermodException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new EventoException(e.getMessage(), e);
		}

		return maxImpresiones;
	}


	private Folio getFolioFromTurno(Turno turno) throws Throwable
	{
		List solicitudesFolio=turno.getSolicitud().getSolicitudFolios();
		if (solicitudesFolio.isEmpty()){
			throw new EventoException("El turno no tiene folio asociado");
		}
		SolicitudFolio solFolio = (SolicitudFolio) solicitudesFolio.get(solicitudesFolio.size()-1);
		String matriculaFolio=solFolio.getIdMatricula();
		FolioPk fid = new FolioPk();
		fid.idMatricula = matriculaFolio;
		Folio folio =forseti.getFolioByID(fid);
		//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
		if(folio.getAnotaciones()==null || folio.getAnotaciones().isEmpty()){
			folio.setAnotaciones(forseti.getAnotacionesFolio(fid));
		}

		return folio;

	}


	private  ImprimibleCertificado getImprimibleCertificadoConFirmaDigital(ImprimibleCertificado impCertificado, String idCirculo) throws Throwable
	{
	    String nombre = "";
		String archivo = "";

		FirmaRegistrador firmaRegistrador = null;
		String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

		CirculoPk cid = new CirculoPk();
		cid.idCirculo = idCirculo;

		firmaRegistrador =  this.forseti.getFirmaRegistradorActiva(cid,cargo);

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

		if (firmaRegistrador==null)
		  return impCertificado;

		String cargoReg = firmaRegistrador.getCargoRegistrador();
		String nombreReg = firmaRegistrador.getNombreRegistrador();

		nombre = firmaRegistrador.getNombreRegistrador();
		archivo = firmaRegistrador.getIdArchivo();
		String rutaFisica = null;

		try{
			rutaFisica =  hermod.getPathFirmasRegistradores();
		}catch(HermodException t){
			Log.getInstance().debug(ANImprimirFolio.class,"No se pudo cargar la firma de los registradores");
		}

		if(rutaFisica!=null)
		{
			BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

			byte pixeles[]=null;
			try
			{
				pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
			}
			catch(Throwable t)
			{
				t.printStackTrace();
			}
			impCertificado.setPixelesImagenFirmaRegistrador(pixeles);
			impCertificado.setCargoRegistrador(cargoReg);
			impCertificado.setNombreRegistrador(nombreReg);

		}
                impCertificado.setChangetextforregistrador(true);
		return impCertificado;
	}


	public static BufferedImage getImage(String path, String nombreArchivo)
	{
		String nombreCompleto = getNombreCompleto(path,nombreArchivo);
		BufferedImage buf = null;

		try
		{
			File file = new File(nombreCompleto);
			buf = ImageIO.read(file);
		}
		catch (IOException e)
		{
			Log.getInstance().error(ANImprimirFolio.class,e);
		}

		return buf;
	}

	public static String getNombreCompleto(String path, String nombreArchivo)
	{

		String nombreCompleto=null;

		if (!path.trim().equals(""))
		  nombreCompleto = path + nombreArchivo;
		else
		  nombreCompleto = nombreArchivo;


	  return nombreCompleto;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * reimpresión de un recibo segun idTurno especifico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirFolio reImprimirRecibo(EvnImprimirFolio evento) throws EventoException {

		List usuarios = null;
		List estaciones = null;
		Turno turno = null;
		String idTurnoWF = evento.getIdTurno();
		Estacion estacion = evento.getEstacion();
		Pago pago = null;
		int maxIntentos;
		int espera;
		Usuario user = evento.getUsuarioNeg();

		try {

			/*EstacionRecibo.ID estacionID = new EstacionRecibo.ID();
			estacionID.idEstacion = estacion.getEstacionId();
			String numRecibo = String.valueOf(hermod.getNextNumeroReciboSinAvanzar(estacionID));
			pago = hermod.getPagoByNumeroRecibo(numRecibo);

			Solicitud solicitud = pago.getLiquidacion().getSolicitud();
			List liquidaciones = solicitud.getLiquidaciones();*/

			turno = hermod.getTurnobyWF(idTurnoWF);

			//comprobar que el turno no esta anulado
			if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)){
				throw new EventoException("El turno esta anulado");
			}
			
			//obtener el pago
			Solicitud solicitud = turno.getSolicitud();
			int intentosImpresionRecibo=Integer.parseInt(hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO));
			if (intentosImpresionRecibo-1<=solicitud.getNumReimpresionesRecibo()){
				throw new EventoException("Se ha excedido el número de intentos de impresión para los recibos");
			}
			List liquidaciones = solicitud.getLiquidaciones();

			if (liquidaciones.size() > 0) {
				Liquidacion liqInicial = (Liquidacion) liquidaciones.get(0);
				Pago pagoInicial = liqInicial.getPago();

				//Si es la reimpresion de recibos de pago de mayor valor, no valida que sea el usuario quien creo el turno
				if(!evento.isMayorValor()){
					if (!pagoInicial.getUsuario().getUsername().equals(user.getUsername())) {
						throw new ImpresionException("Únicamente el usuario " + pagoInicial.getUsuario().getUsername() + " puede realizar la reimpresión, ya que él fue quién creo el turno.");
					}
				}

			} else {
				long procesoFotocopias = new Long(CProceso.PROCESO_FOTOCOPIAS).longValue();
				if (solicitud.getProceso().getIdProceso() != procesoFotocopias) {
					throw new ImpresionException("No fue posible determinar quién creo el turno.");
				}
			}

			Liquidacion lTemp = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
			pago = lTemp.getPago();
			pago.setLiquidacion(lTemp);
			pago.getLiquidacion().setSolicitud(solicitud);
			pago.getLiquidacion().getSolicitud().setTurno(turno);

			// Se obtiene el consecutivo de recibo
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = evento.getUsuario();

			if (estacion != null) {

				String numReciboOriginal = pago.getNumRecibo();
				Estacion estacionUsuario = estacion;
				EstacionReciboPk estacionID = new EstacionReciboPk();
				estacionID.idEstacion = estacionUsuario.getEstacionId();
				String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionID,user, turno.getIdProceso()));
				pago.setNumRecibo(numRecibo);

				PagoPk pagoID = new PagoPk();
				pagoID.idLiquidacion = pago.getIdLiquidacion();
				pagoID.idSolicitud = pago.getIdSolicitud();
				hermod.setNumeroReciboPago(pagoID, numRecibo);

				pago.setNumRecibo(numRecibo);
				pago.setLastNumRecibo(numReciboOriginal);

			} else {
				long idUsuario = fenrir.darIdUsuario(usuarioAuriga.getUsuarioId());

				List estacionesUsuario = fenrir.darEstacionesUsuario(idUsuario);

				//SI EL USUARIO NO TIENE ESTACIONES ASIGNADAS NO SE PUEDE REIMPRIMIR
				if (estacionesUsuario == null || estacionesUsuario.size() == 0) {
					throw new ImpresionException("No se puede reimprimir el recibo porque no tiene estaciones configuradas.");
				}

				//SI EL USUARIO TIENE UNA ESTACIÓN SE INTENTA REIMPRIMIR
				if (estacionesUsuario != null && estacionesUsuario.size() == 1) {
					String numReciboOriginal = pago.getNumRecibo();
					Estacion estacionUsuario = (Estacion) estacionesUsuario.get(0);
					EstacionReciboPk estacionID = new EstacionReciboPk();
					estacionID.idEstacion = estacionUsuario.getEstacionId();
					String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionID,user,turno.getIdProceso()));
					pago.setNumRecibo(numRecibo);

					PagoPk pagoID = new PagoPk();
					pagoID.idLiquidacion = pago.getIdLiquidacion();
					pagoID.idSolicitud = pago.getIdSolicitud();
					hermod.setNumeroReciboPago(pagoID, numRecibo);

					pago.setNumRecibo(numRecibo);
					pago.setLastNumRecibo(numReciboOriginal);
				}

				//SI EL USUARIO NO TIENE MÁS DE UNA ESTACIÓN SE LE DEBE PREGUNTAR AL USUARIO CON CUÁL
				//CONSECUTIVOS DE RECIBOS SE QUIERE REIMPRIMIR
				if (estacionesUsuario.size() > 1) {

					Iterator it = estacionesUsuario.iterator();
					int numEstacionesConRecibo = 0;
					long numeroRecibo = 0;
					Estacion estacionSeleccionada = null;

					while (it.hasNext()) {
						Estacion est = (Estacion) it.next();

						EstacionReciboPk estacionID = new EstacionReciboPk();
						estacionID.idEstacion = est.getEstacionId();

						try {
							numeroRecibo = hermod.getNextNumeroReciboSinAvanzar(estacionID,user,turno.getIdProceso());
							estacionSeleccionada = est;
							numEstacionesConRecibo++;
						} catch (Exception e) {
						}
					}

					//SI NINGUNA DE LAS ESTACIONES TIENE CONFIGURADOS RECIBOS NO SE PUEDE REIMPRIMIR
					if (numEstacionesConRecibo == 0) {
						throw new ImpresionException("Ninguna de las estaciones tiene configuradas secuencias de recibos.");
					}

					//UNA DE LAS ESTACIONES TIENE CONFIGURADOS RECIBOS SE REIMPRIME
					if (numEstacionesConRecibo == 1) {
						String numReciboOriginal = pago.getNumRecibo();
						EstacionReciboPk estacionID = new EstacionReciboPk();
						estacionID.idEstacion = estacionSeleccionada.getEstacionId();
						String numRecibo = String.valueOf(hermod.getNextNumeroRecibo(estacionID,user, turno.getIdProceso()));
						pago.setNumRecibo(numRecibo);

						PagoPk pagoID = new PagoPk();
						pagoID.idLiquidacion = pago.getIdLiquidacion();
						pagoID.idSolicitud = pago.getIdSolicitud();
						hermod.setNumeroReciboPago(pagoID, numRecibo);

						pago.setNumRecibo(numRecibo);
						pago.setLastNumRecibo(numReciboOriginal);
					}

					//SI DEFINITIVAMENTE EL USUARIO TIENE MÁS DE UNA ESTACIÓN CON RECIBOS CONFIGURADOS LE PREGUNTA
					//AL USUARIO CON CUÁL CONSECUTIVO DE RECIBOS SE DEBE REIMPRIMIR
					if (numEstacionesConRecibo > 1) {
						estaciones = new ArrayList();

						Iterator itTemp = estacionesUsuario.iterator();
						numeroRecibo = 0;
						while (itTemp.hasNext()) {
							Estacion est = (Estacion) itTemp.next();
							EstacionReciboPk estacionID = new EstacionReciboPk();
							estacionID.idEstacion = est.getEstacionId();
							try {
								numeroRecibo = hermod.getNextNumeroReciboSinAvanzar(estacionID,user, turno.getIdProceso());
								ElementoLista el = new ElementoLista();
								el.setId(est.getEstacionId());
								el.setValor("" + numeroRecibo);
								estaciones.add(el);

							} catch (Exception e) {
							}
						}
						return new EvnRespImprimirFolio(estaciones, EvnRespImprimirFolio.DETERMINAR_ESTACION);
					}

				}
			}

			//hacer la rutina de imprimir el recibo

			/*String numRecibo = ((Liquidacion)liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();

			pago.setNumRecibo(numRecibo);
			pago.getLiquidacion().getSolicitud().setTurno(turno);*/

			Circulo circulo = solicitud.getCirculo();
			//Obtener ultima liquidacion
			Liquidacion liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
			String fechaImpresion = this.getFechaImpresion(liquidacion.getFecha());
			ImprimibleRecibo impRec = new ImprimibleRecibo(pago, circulo, fechaImpresion, CTipoImprimible.RECIBO);
			impRec.setReimpresion(true);
			if (liquidacion instanceof LiquidacionTurnoCertificado) {
				LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
				String idTipoCertificado = liquidaCert.getTipoCertificado().getIdTipoCertificado();
				impRec.setTipoCertificadoId(idTipoCertificado);
			}

			//verifica si el certificado es de mayor extension.
			if (solicitud instanceof SolicitudCertificado) {
				boolean esMayorExtension = false;
				long numAnota = -1;
				FolioPk fid = this.getFolio_ID(solicitud);
				if (fid != null) {
					numAnota = forseti.getCountAnotacionesFolio(fid, CCriterio.TODAS_LAS_ANOTACIONES, null);
					esMayorExtension = forseti.mayorExtensionFolio(fid.idMatricula);
				} else {
					Log.getInstance().error(ANImprimirFolio.class,"NO FUE POSIBLE DETERMINAR SI EL FOLIO ES DE MAYOR EXTENSION");
					//se asume false, pero no se sabe.
					esMayorExtension = false;
				}
				impRec.setMayorExtension(esMayorExtension);
			}

			String uid = evento.getUid();
			/*
			 boolean ok= this.imprimirRecibo(impRec,turno,uid);
			if (!ok)
			  throw new EventoException("Número de intentos agotados, No se pudo generar el recibo");
			*/

			//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.

			//INGRESO DE INTENTOS DE IMPRESION
			try {
				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
				if (intentosImpresion != null) {
					Integer intentos = new Integer(intentosImpresion);
					maxIntentos = intentos.intValue();
				} else {
					Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
					maxIntentos = intentosDefault.intValue();
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_FOLIO);
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
				printJobs.enviar(uid, bundle, maxIntentos, espera);
				SolicitudPk idSolicitud=new SolicitudPk();
				idSolicitud.idSolicitud=solicitud.getIdSolicitud();
				hermod.incrementarIntentoReimpresionRecibo(idSolicitud);
			} catch (Throwable t) {
				t.printStackTrace();
			}

			//se manda a imprimir el recibo por el identificador unico de usuario
		} catch (ImpresionException ie) {
			throw ie;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new ImpresionException("Error al imprimir el folio: " + t.getMessage());
		}

		EvnRespImprimirFolio evRespuesta = new EvnRespImprimirFolio(usuarios, EvnRespImprimirFolio.IMPRESION_FOLIO_OK);
		return evRespuesta;
	}


//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * reimpresión de un recibo segun idTurno especifico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirFolio reImprimirConsulta(EvnImprimirFolio evento) throws EventoException {

		String idTurno = evento.getIdTurno();
		Turno turno;

		try {

			turno = hermod.getTurnobyWF(idTurno);
			if(turno==null){
				throw new Exception("El turno no existe");
			}
			if(turno.getIdProceso()!= Long.parseLong(CProceso.PROCESO_CONSULTAS)){
				throw new Exception("El turno no es del proceso de consultas");
			}
			
			//comprobar que el turno no esta anulado
			if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)){
				throw new Exception("El turno esta anulado");
			}

			//Comprobar que el turno ya fue consultado
			boolean paso = false;
			List historia = turno.getHistorials();
			for(Iterator itHistoria = historia.iterator(); itHistoria.hasNext();){
				TurnoHistoria hist = (TurnoHistoria) itHistoria.next();
				if(hist.getFase().equals(CFase.CON_ENTREGAR_COMPLEJA) || hist.getFase().equals(CFase.FINALIZADO)){
					paso = true;
				}
			}
			if(!paso){
				throw new Exception("El turno todavia no esta habilitado para reimpresion.");
			}

			SolicitudConsulta solConsulta = (SolicitudConsulta)turno.getSolicitud();
			
			if(!solConsulta.getTipoConsulta().getNombre().equals(TipoConsulta.CONSTANCIA)){
				String fechaImpresion = this.getFechaImpresion();
				ImprimibleConsulta iRec = new ImprimibleConsulta(turno, fechaImpresion,CTipoImprimible.CONSULTA);
				iRec.setTamanoCarta(false);
				if(turno.isNacional()){
					iRec.setIdUsuario(evento.getUsuario().getUsuarioId());
					iRec.setCirculo(evento.getCirculo());
				}
				try{
					this.imprimir(iRec, evento.getUid());

				} catch(Exception e){
					throw new ErrorImpresionException("No se pudo imprimir el resultado de la consulta: " + e.getMessage(),turno.getIdWorkflow());
				}
			}else{
				SolicitudConsulta solicitudConsulta = (SolicitudConsulta)hermod.getSolicitudById(solConsulta.getIdSolicitud());
				ImprimibleConstanciaNoPropiedad iNop = new ImprimibleConstanciaNoPropiedad(turno,solicitudConsulta,CTipoImprimible.CONSULTA);
				iNop.setUsuario(evento.getUsuario().getUsuarioId());


                                /**
                                 * @author: Fernando Padilla Velez
                                 * @change: Caso mantis 2803: Acta - Requerimiento No 134 - Error al reimprimir,
                                 *          Se comenta la linea 1678, ya que, la variable busqueda ya no será usada;
                                 *          Se crea el ciudadano con los valores que posee solConsulta.getCiudadano().
                                 * @author: Diana Lora
                                 * @change: Caso Mantis 8463: Acta - Requerimiento No 023_151 - Error en los parametros 
                                 *          de busqueda para la reimpresión. Cancela el comentario de Fernando Padilla.
                                 * 
                                 **/
				Busqueda busqueda = (Busqueda)solConsulta.getBusquedas().get(0);
				
				Ciudadano ciudadano = new Ciudadano();
				ciudadano.setTipoDoc(busqueda.getTipoDocCiudadano());
				ciudadano.setDocumento(busqueda.getNumeroDocCiudadano());
				ciudadano.setNombre(busqueda.getNombreCiudadano());
				ciudadano.setApellido1(busqueda.getApellido1Ciudadano());
				ciudadano.setApellido2(busqueda.getApellido2Ciudadano());
				
				iNop.setCiudadano(ciudadano);
				
				iNop.setEsPropietario(esPropietario(turno, ciudadano));
				
				String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

				FirmaRegistrador firmaRegistrador = null;

				String sNombre = "";
				String archivo = "";
				String cargoToPrint = "";
				String rutaFisica = null;

			    try
			    {
			    	CirculoPk cid = new CirculoPk();
			    	
			    	if (turno.isNacional()&& solicitudConsulta.getSolicitudFolios()!=null && solicitudConsulta.getSolicitudFolios().size()>0)
			    	{
			    		cid.idCirculo = ((SolicitudFolio)solicitudConsulta.getSolicitudFolios().get(0)).getFolio().getCirculo(); 
			    	}else{
			    		cid.idCirculo = turno.getIdCirculo();
			    	}
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
					
					if(turno.isNacional()){
						if(firmaRegistrador==null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
								|| archivo==null || archivo.equals("")){
							throw new Exception("No se permite la impresion de constancia de no propiedad de orden nacional sin firma");
						}
					}
					
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
			    catch(Exception e)
			    {
			    	throw e;
			    }
			    catch(Throwable t)
			    {
			    	t.printStackTrace();
			    }

				if (sNombre==null)
				  sNombre="";


				iNop.setCargoRegistrador(cargoToPrint);
				iNop.setNombreRegistrador(sNombre);


				if(rutaFisica!=null)
				{
					BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

					byte pixeles[]=null;
					try
					{
						pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
					}
					catch (Throwable e1) {

						e1.printStackTrace();
					}
					iNop.setPixelesImagenFirmaRegistrador(pixeles);
				}
				try{
					this.imprimir(iNop, evento.getUid());
				}catch(Exception e){
					throw new ErrorImpresionException("No se pudo imprimir el resultado de la consulta: " + e.getMessage(),turno.getIdWorkflow());
				}
			}

		} catch (ImpresionException ie) {
			throw ie;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new ImpresionException("Error al imprimir el folio: " + t.getMessage());
		}

		EvnRespImprimirFolio evRespuesta = new EvnRespImprimirFolio(null ,EvnRespImprimirFolio.REIMPRIMIR_CONSULTA_OK);
		return evRespuesta;
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
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
	protected String getFechaImpresion(Date fecha)
	{
                if( null == fecha ) {
                   return getNullableString( true );
                } // if

		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
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

	/**
	 * Retorna el ID de la primera matrícula
	 * asociada a la solicitud. Si es un certificado solo
	 * hay una matrícula.
	 * @param solicitud
	 * @return
	 */
	private FolioPk getFolio_ID(Solicitud solicitud) {
		List solFolio = solicitud.getSolicitudFolios();

		Iterator itSolFolio = solFolio.iterator();
		List matriculas = new Vector();

		FolioPk fid;
		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio)itSolFolio.next();
			fid = new FolioPk();
			fid.idMatricula = sol.getFolio().getIdMatricula();
			matriculas.add(fid);

		}
		//String idMatricula = (String)matriculas.get(0);
		try {
			fid = (FolioPk)matriculas.get(0);
		}
		catch (Throwable e) {
			Log.getInstance().error(ANImprimirFolio.class,e);
			fid = null;
		}

		return fid;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta obtenerImpresorasCirculo(EvnImprimirFolio evento) throws EventoException {
		Map impresoras = null;
		List notas = null;
		Circulo cir=evento.getCirculo();

		try {
            if(cir!=null){
				impresoras = forseti.getConfiguracionImpresoras(cir.getIdCirculo());
            }

            //Obtener las notas informativas.
            ProcesoPk idProceso = new ProcesoPk();
            idProceso.idProceso = 1;
            String fase = "CER_REIMPRESION_ADMINISTRATIVA";
            notas = hermod.getTiposNotaProceso(idProceso,fase);



        }
        catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        }
        catch (FenrirException e) {
            throw new EventoException(e.getMessage(), e);
        }
        catch (Throwable e) {
            Log.getInstance().error(ANImprimirFolio.class,"Excepción obteniendo notas informativas o impresoras del círculo ", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespImprimirFolio respuesta = new EvnRespImprimirFolio(impresoras,EvnRespImprimirFolio.OBTENER_IMPRESORAS_CIRCULO);
        respuesta.setCirculo(cir);
        respuesta.setListaNotasReimpresion(notas);
        return respuesta;
	}

	/**
	  * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	  * con los parametros asignados.
	  * @param turno
	  * @param imprimible
	  * @param parametros
	  * @return
	  */
	 private boolean imprimirAdministrador(String circulo, ImprimibleBase imprimible, Hashtable parametros)
	 {


		Bundle b = new Bundle(imprimible);

		String intentosImpresion;
		String esperaImpresion;

		ServiceLocator sl = ServiceLocator.getInstancia();


		//INGRESO DE INTENTOS DE IMPRESION
		 try
		  {

			  hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
			  printJobs = (PrintJobsInterface) sl.getServicio("gov.sir.print");

			  intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			  if (intentosImpresion != null)
			  {
				   parametros.put(Processor.INTENTOS, intentosImpresion);
			  }
			  else
			  {
				   intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
				   parametros.put(Processor.INTENTOS, intentosImpresion);
			  }

			  //INGRESO TIEMPO DE ESPERA IMPRESION
			  esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			  if (intentosImpresion != null)
			  {
				  parametros.put(Processor.ESPERA, esperaImpresion);
			  }
			  else
			  {
				  esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
				  parametros.put(Processor.ESPERA, esperaImpresion);
			  }
		  }
		  catch (Throwable t)
		  {
			  intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
			  parametros.put(Processor.INTENTOS, intentosImpresion);
			  esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
			  parametros.put(Processor.ESPERA, esperaImpresion);
		  }

		 Integer intIntentos = new Integer (intentosImpresion);
		 int intentos = intIntentos.intValue();

		 Long longEspera = new Long (esperaImpresion);
		 int espera = longEspera.intValue();

		 ////////////////////Número de copias
		 Integer nCopias = (Integer)parametros.get(Processor.NUM_COPIAS_IMPRIMIBLE);
		 int numCopias = 1;
		 if (nCopias != null){
			numCopias = nCopias.intValue();
		 }

		 boolean resultadoImpresion = false;
		 b.setNumeroCopias(numCopias);

       String impresoraSeleccionada = (String)parametros.get(Processor.IMPRESORA_SELECCIONADA);
       if(impresoraSeleccionada!=null){
       	b.setNombreImpresora(impresoraSeleccionada);
       }

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
		try
		{
			printJobs.enviar(circulo, b, intentos, espera);
			//si imprime exitosamente sale del ciclo.
			resultadoImpresion = true;
			parametros=new Hashtable();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			resultadoImpresion = false;

		}
		return resultadoImpresion;
	 }
	 
	 private boolean esPropietario(Turno turno, Ciudadano ciud) throws EventoException{
			List solfolios = turno.getSolicitud().getSolicitudFolios();
			try{
				Iterator iter = solfolios.iterator();
				while(iter.hasNext()){
					String matricula = (String)((SolicitudFolio)iter.next()).getIdMatricula();
					FolioPk fid = new FolioPk();
					fid.idMatricula = matricula;
					
					List anotaciones = forseti.getAnotacionesFolio(fid);
					Iterator it = anotaciones.iterator();
					while (it.hasNext()){
						List anotaCiudadanos = ((Anotacion)it.next()).getAnotacionesCiudadanos();
						for(int i=0; i<anotaCiudadanos.size();i++){
							AnotacionCiudadano aCiudadano = (AnotacionCiudadano)anotaCiudadanos.get(i);
							if (estaCiudadano(aCiudadano,ciud)){
								if(forseti.isUltimoPropietario(aCiudadano)){
									return true;
								}
							}
						}
					}
				}
			} catch (ForsetiException e) {
				Log.getInstance().error(ANImprimirFolio.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				Log.getInstance().error(ANImprimirFolio.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			}
			return false;
		}
	 
	 private boolean estaCiudadano(AnotacionCiudadano aCiud, Ciudadano ciud){
			Ciudadano aux = aCiud.getCiudadano();
			
			if(aux.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA) && aux.getApellido1().equals(ciud.getApellido1()))
				return true;
			
			if(aux.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_NIT) && aux.getApellido1().equals(ciud.getApellido1()))
				return true;
			
			
			if(aux.getTipoDoc().equals(ciud.getTipoDoc()) && aux.getDocumento().equals(ciud.getDocumento())
					&& aux.getNombre().equals(ciud.getNombre()) && aux.getApellido1().equals(ciud.getApellido1()))
				return true;
			
			if(aux.getApellido1().equals(ciud.getApellido1())
					&& aux.getNombre().equals(ciud.getNombre()))
				return true;
			
			return false;
		}
    
    private EvnRespImprimirFolio UploadFile(EvnImprimirFolio evento) throws EventoException {
        String nombreArchivo = evento.getNombreArchivo();
        StringBuffer listaFolios = evento.getListaFolios();
        
        EvnRespImprimirFolio evRespuesta = new EvnRespImprimirFolio(null, EvnRespImprimirFolio.AGREGAR_DE_ARCHIVO);
        evRespuesta.setNombreArchivo(nombreArchivo);
        evRespuesta.setListaFolios(listaFolios);
        return evRespuesta;
    }
         
    private EvnRespImprimirFolio programarTarea(EvnImprimirFolio evento) throws EventoException {
        TareasSIR tarea = new TareasSIR();
        String exito = null;
        try {
            exito = tarea.programarTareas(evento.getNombreTarea(), evento.getFechaTarea(), evento.getListaFolios(), evento.getTipoTarea());
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANImprimirFolio.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
        EvnRespImprimirFolio evRespuesta = new EvnRespImprimirFolio(null, EvnRespImprimirFolio.PROGRAMAR_TAREA_OK);
        evRespuesta.setExito(exito);
        evRespuesta.setImp_masiva_simple_folio_ftp(prop.getProperty(PrintJobsProperties.IMP_MASIVA_SIMPLE_FOLIO_FTP)+evento.getNombreTarea()+"/");
        return evRespuesta;
    }
}
