package gov.sir.hermod.dao.impl.jdogenie;

// Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
// Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
// Importación no utilizaada
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Vector;

// Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
// Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
// Importación no utilizaada
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CValidacion;

import gov.sir.core.negocio.modelo.util.Log;

import org.auriga.core.modelo.transferObjects.Rol;

import org.auriga.smart.servicio.ServiceLocator;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.CamposCaptura;
import gov.sir.core.negocio.modelo.CanalesRecaudo;

import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CirculoTipoPagoPk;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;

/**
 * @autor HGOMEZ
 * @mantis 12422
 * @Requerimiento 049_453
 * @descripcion Se importa las siguientes clases para dar solución al
 * requerimiento.
 */
import gov.sir.core.negocio.modelo.DocPagoTarjetaCredito;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoEnhanced;

import gov.sir.core.negocio.modelo.DocPagoTarjetaDebito;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoEnhanced;

import gov.sir.core.negocio.modelo.DocPagoConvenio;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced;

import gov.sir.core.negocio.modelo.DocPagoElectronicoPSE;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced;

import gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced;

// Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
// Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
// Importación agregada para utilizar la clase DocPagoNotaDebito
import gov.sir.core.negocio.modelo.DocPagoNotaDebito;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocumentoPagoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

import gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSECorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoNotaDebitoCorreccionEnhanced;
// Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
// Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
// Importación agregada para utilizar la clase DocPagoNotaDebitoEnhanced
import gov.sir.core.negocio.modelo.jdogenie.DocPagoNotaDebitoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.util.CirculoCanalTipoPago;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.PagosDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

// Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
// Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
// Importación no utilizaada
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
/**
 * Clase para el manejo de los Pagos asociados con los diferentes procesos de la
 * aplicación.
 *
 * @author mrios, mortiz, dlopez
 */
public class JDOPagosDAO implements PagosDAO {

    /**
     * Crear una nueva instancia de JDOPagosDAO
     */
    public JDOPagosDAO() {
    }

    /**
     * Crea un <code>Turno</code> y un <code>Pago</code> en el sistema,
     * <p>
     * Método utilizado para transacciones
     *
     * @param p <code>PagoEnhanced</code> con sus atributos, exceptuando el
     * identificador.
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param estacion Estación asociada con la creación del <code>Pago</code>
     * @param user Usuario iniciador del proceso
     * @return un identificador del <code>Turno</code> generado.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     * @see gov.sir.core.negocio.modelo.Turno
     */
    protected PagoEnhancedPk crearPago(PagoEnhanced p, Usuario user, PersistenceManager pm)
            throws DAOException {

        if (p.getLiquidacion() == null) {
            throw new DAOException("La liquidación recilbida es null");
        }
        double valor = p.getLiquidacion().getValor();

        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        PagoEnhancedPk pId = new PagoEnhancedPk();
        CirculoProcesoEnhanced circuloProcesoEnhanced = null;

        try {

            String idLiquidacion = p.getIdLiquidacion();
            String idSolicitud = p.getIdSolicitud();

            //p.setNumRecibo(sRecibo);
            //Se obtiene un circulo proceso enhanced.
            //Esta consulta efectua un select for update para garantizar sincronización.
            CirculoProcesoEnhanced cpe = this.getCirculoProcesoByPago(p, pm);

            if (p.getUsuario() == null) {
                throw new DAOException("El pago no esta asociada a un Usuario");
            }

            UsuarioEnhanced usuario = p.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException("No encontró el Usuario con el login: " + p.getUsuario());
            }
            p.setUsuario(usuarior);

            LiquidacionEnhanced l = p.getLiquidacion();
            if (l == null) {
                throw new DAOException("La liquidacion recibida para procesar el pago es nula");
            }
            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = l.getIdLiquidacion();
            lId.idSolicitud = l.getIdSolicitud();
            p.setIdLiquidacion(l.getIdLiquidacion());
            p.setIdSolicitud(l.getIdSolicitud());
            p.setLiquidacion(l);
            LiquidacionEnhanced lr = liquidacionesDAO.getLiquidacionByID(lId, pm);
            if (lr == null) {
                throw new DAOException("No encontró la liquidación con el ID: " + lId.idLiquidacion);
            }

            if (lr != null && lr.getCirculo() != null) {
                p.setCirculo(lr.getCirculo());
            }

            p.setLiquidacion(lr);

            List aps = p.getAplicacionPagos();
            if (aps == null) {
                throw new DAOException("La lista de aplicaciones pago es nula");
            }

            Iterator ita = aps.iterator();
            while (ita.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) ita.next();
                if (ap == null) {
                    throw new DAOException("La aplicacion de Pago es nula");
                }

                DocumentoPagoEnhanced dp = ap.getDocumentoPago();
                if (dp == null) {
                    throw new DAOException("El documento de pago es nulo");
                }

                if (lr != null && lr.getCirculo() != null) {
                    ap.setCirculo(lr.getCirculo());
                }

                DocumentoPagoEnhancedPk dpId = new DocumentoPagoEnhancedPk();
                if (dp.getIdDocumentoPago() != null) {
                    dpId.idDocumentoPago = dp.getIdDocumentoPago();
                    DocumentoPagoEnhanced dpr = this.getDocumentosPagoById(dpId, pm);
                    // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                    // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
                    if (dpr instanceof DocPagoConsignacionEnhanced || dpr instanceof DocPagoChequeEnhanced || dpr instanceof DocPagoNotaDebitoEnhanced || dpr instanceof DocPagoGeneralEnhanced) {
                        Date fechaDate = dpr.getFechaCreacion();
                        Calendar calendarDocPago = new GregorianCalendar();
                        calendarDocPago.setTime(fechaDate);
                        Calendar calendarHoy = new GregorianCalendar();
                        calendarHoy.setTime(new Date());
                        /*if ((calendarDocPago.get(Calendar.YEAR) < calendarHoy.get(Calendar.YEAR)) ||
							(calendarDocPago.get(Calendar.DAY_OF_YEAR) < calendarHoy.get(Calendar.DAY_OF_YEAR))) {
					
							throw new DAOException("El saldo del documento solo puede utilizarse el primer día que ingresa  al sistema");
						}*/

                    }

                    double saldo = dpr.getSaldoDocumento() - ap.getValorAplicado();

                    if (saldo < 0) {
                        throw new DAOException("Saldo insuficiente en el documento de pago");
                    }

                    dpr.setSaldoDocumento(saldo);
                    ap.setDocumentoPago(dpr);
                    ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                    ap.setPago(p);

                    if (dpr == null) {
                        if (dp != null) {
                            dp.setCirculo(p.getCirculo());
                        }
                        ap.setDocumentoPago(dp);
                    } else {
                        dpr.setCirculo(p.getCirculo());
                        ap.setDocumentoPago(dpr);
                    }
                } else {
                    double saldo = dp.getValorDocumento() - ap.getValorAplicado();
                    dp.setSaldoDocumento(saldo);
                    DocumentoPagoEnhanced dpr = this.crearDocumentoPago(dp, pm);
                    dp.setCirculo(p.getCirculo());
                    ap.setDocumentoPago(dp);
                    ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                    ap.setPago(p);
                }
            }

            pm.makePersistent(p);
            pId = (PagoEnhancedPk) pm.getObjectId(p);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOPagosDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException de) {
            Log.getInstance().error(JDOPagosDAO.class, de.getMessage(), de);
            throw new DAOException(de.getMessage(), de);
        }

        return pId;
    }

    /**
     * Crea un <code>Turno</code> y un <code>Pago</code> en el sistema,
     * <p>
     * Método utilizado para transacciones
     *
     * @param p <code>PagoEnhanced</code> con sus atributos, exceptuando el
     * identificador.
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param estacion Estación asociada con la creación del <code>Pago</code>
     * @param user Usuario iniciador del proceso
     * @return un identificador del <code>Turno</code> generado.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     * @see gov.sir.core.negocio.modelo.Turno
     */
    protected PagoEnhancedPk crearPagoNulo(PagoEnhanced p, Usuario user, PersistenceManager pm)
            throws DAOException {

        if (p.getLiquidacion() == null) {
            throw new DAOException("La liquidación recilbida es null");
        }

        //double valor = p.getLiquidacion().getValor();
        //JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        PagoEnhancedPk pId = new PagoEnhancedPk();
        //CirculoProcesoEnhanced circuloProcesoEnhanced = null;

        try {

            //String idLiquidacion = p.getIdLiquidacion();
            //String idSolicitud = p.getIdSolicitud();
            //p.setNumRecibo(sRecibo);
            if (p.getUsuario() == null) {
                throw new DAOException("El pago no esta asociada a un Usuario");
            }

            UsuarioEnhanced usuario = p.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException("No encontró el Usuario con el login: " + p.getUsuario());
            }
            p.setUsuario(usuarior);

            LiquidacionEnhanced l = p.getLiquidacion();
            if (l == null) {
                throw new DAOException("La liquidacion recibida para procesar el pago es nula");
            }
            //LiquidacionEnhanced.ID lId = new LiquidacionEnhanced.ID();
            //lId.idLiquidacion = l.getIdLiquidacion();
            //lId.idSolicitud = l.getIdSolicitud();
            p.setIdLiquidacion(l.getIdLiquidacion());
            p.setIdSolicitud(l.getIdSolicitud());
            p.setLiquidacion(l);
            //LiquidacionEnhanced lr = (LiquidacionEnhanced)pm.getObjectById(lId, true);
            //if (lr == null) {
            //	throw new DAOException("No encontró la liquidación con el ID: " + lId.idLiquidacion);
            //}

            //p.setLiquidacion(lr);
            pm.makePersistent(p);
            pId = (PagoEnhancedPk) pm.getObjectId(p);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOPagosDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException de) {
            Log.getInstance().error(JDOPagosDAO.class, de.getMessage(), de);
            throw new DAOException(de.getMessage(), de);
        }

        return pId;
    }

    /**
     * Crea un <code>Turno y un <code>Pago</code> en el sistema, y crea una
     * instacia de Workflow de acuerdo con el <code>Proceso</code> determinado.
     * <p>
     * Como el proceso es transaccional, se genera un log en caso de que se
     * presente error.
     *
     * @param p Pago con sus atributos, exceptuando el identificador
     * @param estacion Estación asociada con la fase del proceso.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param user Usuario Usuario iniciador del proceso.
     * @param delegarUsuario indica si el turno debe ser creado o no en la
     * estación recibida como parámetro.
     * @return Turno Generado luego de procesar el pago.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Pago
     */
    @Override
    public Turno procesarPago(Pago p, String estacion, String impresora, Usuario user, Rol rol, boolean delegarUsuario) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhancedPk tId = new TurnoEnhancedPk();
        TurnoEnhanced t = null;
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        int logError = 0;
        String estacionSiguiente = null;

        try {
            //CREAR TURNO DE PROCESOS: 1, 2, 3, 4, 7, 16, 20

            //Inicio de la transacción.
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Determinar si debe crearse el turno en una estación específica.
            if (delegarUsuario) {
                estacionSiguiente = estacion;
            }

            if (p.getLiquidacion() != null) {

                if (p.getLiquidacion().getSolicitud() != null) {

                    if (p.getLiquidacion().getSolicitud().getTurno() != null) {
                        tId.anio = p.getLiquidacion().getSolicitud().getTurno().getAnio();
                        tId.idCirculo = p.getLiquidacion().getSolicitud().getTurno().getIdCirculo();
                        tId.idProceso = p.getLiquidacion().getSolicitud().getTurno().getIdProceso();
                        tId.idTurno = p.getLiquidacion().getSolicitud().getTurno().getIdTurno();
                        t = turnosDAO.getTurnoByID(tId, pm);
                        PagoEnhancedPk pagoId
                                = this.procesarPago(PagoEnhanced.enhance(p), estacion, pm, t, rol);
                        pm.currentTransaction().commit();

                        logError++;

                        //Avanzar Turno
                        ServiceLocator sl = ServiceLocator.getInstancia();
                        HermodServiceInterface hermod
                                = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
                        Turno turnoAvance = new Turno();
                        turnoAvance.setIdWorkflow(t.getIdWorkflow());
                        turnosDAO.getTurnoByWFId(t.getIdWorkflow(), pm);
                        Fase fase = new Fase();
                        fase.setID(t.getIdFase());
                        String respuesta = null;
                        String procesoId
                                = String.valueOf(
                                        p.getLiquidacion().getSolicitud().getProceso().getIdProceso());
                        if (procesoId.equals(CProceso.PROCESO_CORRECCIONES)) {
                            respuesta = "EXITO";
                        } else if (procesoId.equals(CProceso.PROCESO_FOTOCOPIAS)) {
                            respuesta = "CONFIRMAR";
                        } else if (procesoId.equals(CProceso.PROCESO_CERTIFICADOS)) {
                            respuesta = "EXITO";
                        }

                        Hashtable parametros = new Hashtable();
                        parametros.put(Processor.RESULT, respuesta);
                        //hermod.avanzarTurno(turnoAvance, fase, parametros,CAvanzarTurno.AVANZAR_NORMAL);
                        //se repite la llamada a getTurnoByID
                        //t = turnosDAO.getTurnoByID(tId, pm);
                        turnosDAO.makeTransientTurno(t, pm);

                    } else {

                        tId = this.procesarPago(PagoEnhanced.enhance(p), estacion, impresora, user, pm);
                        pm.currentTransaction().commit();
                        logError++;

                        //Obtener el turno.
                        t = turnosDAO.getTurnoByID(tId, pm);
                        if (t == null) {
                            throw new DAOException("No se encontró el turno");
                        }

                        // Solicitudes Asociadas
                        if (t.getSolicitud().getSolicitudesHijas() != null) {
                            if (t.getSolicitud().getSolicitudesHijas().size() > 0) {
                                if (t.getSolicitud() instanceof SolicitudRegistroEnhanced) {
                                    SolicitudRegistroEnhanced solReg
                                            = (SolicitudRegistroEnhanced) t.getSolicitud();
                                    List certAsocs = solReg.getSolicitudesHijas();
                                    Iterator it2 = certAsocs.iterator();
                                    while (it2.hasNext()) {
                                        SolicitudAsociadaEnhanced solAsoc
                                                = (SolicitudAsociadaEnhanced) it2.next();
                                        SolicitudCertificadoEnhanced solCert
                                                = (SolicitudCertificadoEnhanced) solAsoc.getSolicitudHija();
                                        TurnoEnhancedPk turnoAsocId = new TurnoEnhancedPk();
                                        turnoAsocId.anio = solCert.getTurno().getAnio();
                                        turnoAsocId.idCirculo = solCert.getTurno().getIdCirculo();
                                        turnoAsocId.idProceso = solCert.getTurno().getIdProceso();
                                        turnoAsocId.idTurno = solCert.getTurno().getIdTurno();
                                        TurnoEnhanced turnoAsoc = turnosDAO.getTurnoByID(turnoAsocId, pm);
                                        if (turnoAsoc == null) {
                                            throw new DAOException("No se encontró el turno con el id dado");
                                        }
                                        turnosDAO.makeTransientTurno(turnoAsoc, pm);
                                        turnosDAO.crearInstanciaWF(turnoAsoc, null, null, null, estacionSiguiente);
                                    }
                                }
                            }
                        }

                        /*
						if (t.getSolicitud().getSolicitudesHijas() != null) {
							if (t.getSolicitud().getSolicitudesHijas().size() > 0) {
								if (t.getSolicitud() instanceof SolicitudCertificadoMasivoEnhanced) {
									SolicitudCertificadoMasivoEnhanced solCer =
										(SolicitudCertificadoMasivoEnhanced) t.getSolicitud();
									List certAsocs = solCer.getSolicitudesHijas();
									Iterator it2 = certAsocs.iterator();
									while (it2.hasNext()) {
										SolicitudAsociadaEnhanced solAsoc =
											(SolicitudAsociadaEnhanced) it2.next();
										SolicitudCertificadoEnhanced solCert =
											(SolicitudCertificadoEnhanced) solAsoc.getSolicitudHija();
										TurnoEnhanced.ID turnoAsocId = new TurnoEnhanced.ID();
										turnoAsocId.anio = solCert.getTurno().getAnio();
										turnoAsocId.idCirculo = solCert.getTurno().getIdCirculo();
										turnoAsocId.idProceso = solCert.getTurno().getIdProceso();
										turnoAsocId.idTurno = solCert.getTurno().getIdTurno();
										TurnoEnhanced turnoAsoc =
											TurnoEnhanced.enhance(
												turnosDAO.getTurnoByID(turnoAsocId.getTurnoID(), true));
										if (turnoAsoc == null) {
											throw new DAOException("No se encontró el turno con el id dado");
										}
										turnosDAO.crearInstanciaWF(turnoAsoc,null,null,null);
									}
								}
							}
						}

                         */
                        if (t == null) {
                            throw new DAOException("No se encontró el turno con el id dado");
                        }

                        turnosDAO.makeTransientTurno(t, pm);

                        if (rol == null) {
                            turnosDAO.crearInstanciaWF(t, impresora, user, null, estacionSiguiente);
                        } else {
                            turnosDAO.crearInstanciaWF(t, impresora, user, rol.getRolId(), estacionSiguiente);
                        }

                    }
                } else {
                    throw new DAOException("No existe una solicitud para la liquidacion");
                }
            } else {
                throw new DAOException("No existe una liquidación asociada al pago");
            }

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            String logString = "";
            //El error se presento justamente en AvanzarTurno.
            if (logError >= 0) {
                logString = "SE PRESENTO ERROR AL AVANZAR TURNO EN PROCESO"
                        + "TRANSACCIONAL ";
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage() + logString);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw (e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            String logString = "";
            //El error se presento justamente en AvanzarTurno.
            if (logError >= 0) {
                logString = "SE PRESENTO ERROR AL AVANZAR TURNO EN PROCESO"
                        + "TRANSACCIONAL ";
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage() + logString);
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().commit();
            }
            pm.close();
        }
        return (Turno) t.toTransferObject();
    }

    /**
     * Crea un <code>Turno y un <code>Pago</code> en el sistema, y crea una
     * instacia de Workflow de acuerdo con el <code>Proceso</code> determinado.
     *
     * @param p Pago con sus atributos, exceptuando el identificador
     * @param parametros Hashtable parametros para crear el turno.
     * @return Turno Generado luego de procesar el pago.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Turno procesarPago(Pago p, Hashtable parametros) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhancedPk tId = new TurnoEnhancedPk();
        TurnoEnhanced t = null;
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        String estacion = (String) parametros.get(CEstacion.ESTACION_ID);
        String impresora = (String) parametros.get(CImpresion.IMPRESORA);
        Usuario user = (Usuario) parametros.get(CInfoUsuario.USUARIO_SIR);
        String rol = (String) parametros.get(CRol.ID_ROL);
        List validaciones = (List) parametros.get(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS);

        //Parámetro que indica si se desea crear un turno en una estación en particular.
        String siguienteEstacion = (String) parametros.get(Processor.SIGUIENTE_ESTACION);

        long tiempoInicial = System.currentTimeMillis();

        Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
        Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(INICIO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
        Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

        try {

            //Inicio de la transacción.
            ServiceLocator sl = ServiceLocator.getInstancia();
            HermodServiceInterface hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            if (p.getLiquidacion() == null) {
                throw new DAOException("No existe una liquidación asociada al pago");
            }

            if (p.getLiquidacion().getSolicitud() == null) {
                throw new DAOException("No existe una solicitud para la liquidacion");
            }

            //Se crea el turno, y el documento de pago y se hacen persistentes.
            tId = this.procesarPago(PagoEnhanced.enhance(p), estacion, impresora, user, pm);
            pm.currentTransaction().commit();

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(PROCESAR PAGO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            //Obtener el turno.
            t = turnosDAO.getTurnoByID(tId, pm);

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(CONSULTAR TURNO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            if (t == null) {
                throw new DAOException("No se encontró el turno con el id dado");
            }

            turnosDAO.makeTransientTurno(t, pm);

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(HACER TRASIENTE EL TURNO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            //Se crea la instancia de Workflow
            //Si se presenta algún problema, se lanza una excepción.
            try {
                turnosDAO.crearInstanciaWF(t, impresora, user, rol, siguienteEstacion);
            } catch (DAOException de) {
                throw new DAOException("Se presentó un error al crear la instancia de Workflow");
            }

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(CREAR INSTANCIA WF):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            //Se revisa si el turno tiene problemas de validación y se crean las
            //respectivas notas devolutivas.
            if (validaciones != null) {
                for (int i = 0; i < validaciones.size(); i++) {
                    String error = (String) validaciones.get(i);

                    //Se crea un tipo de Nota devolutiva
                    TipoNota tipo = new TipoNota();

                    // FOLIO ANULADO
                    if (error.equals(CValidacion.FOLIO_ANULADO_MSG)) {
                        tipo.setIdTipoNota(CTipoNota.FOLIO_CERRADO);
                        tipo.setDescripcion("EL FOLIO SE ENCUENTRA ANULADO");
                    }

                    //FOLIO BLOQUEADO
                    if (error.equals(CValidacion.FOLIO_BLOQUEADO_MSG)) {

                        tipo.setIdTipoNota(CTipoNota.FOLIO_EN_CUSTODIA);
                        tipo.setDescripcion("EL FOLIO SE ENCUENTRA BLOQUEADO");
                    }

                    //FOLIO CERRADO
                    if (error.equals(CValidacion.FOLIO_CERRADO_MSG)) {
                        tipo.setIdTipoNota(CTipoNota.FOLIO_CERRADO);
                        tipo.setDescripcion("EL FOLIO SE ENCUENTRA CERRADO");
                    }

                    //FOLIO DEBE DINERO
                    if (error.equals(CValidacion.FOLIO_DEBE_DINERO_MSG)) {

                        tipo.setIdTipoNota(CTipoNota.FOLIO_DEBE_DINERO);
                        tipo.setDescripcion("EL FOLIO DEBE DINERO");
                    }

                    //FOLIO EN CUSTODIA
                    if (error.equals(CValidacion.FOLIO_EN_CUSTODIA_MSG)) {
                        tipo.setIdTipoNota(CTipoNota.FOLIO_EN_CUSTODIA);
                        tipo.setDescripcion("EL FOLIO SE ENCUENTRA EN CUSTODIA");
                    }

                    //FOLIO EN TRAMITE
                    if (error.equals(CValidacion.FOLIO_EN_TRAMITE_MSG)) {

                        tipo.setIdTipoNota(CTipoNota.FOLIO_EN_TRAMITE);
                        tipo.setDescripcion("EL FOLIO SE ENCUENTRA EN TRAMITE");
                    }

                    //FOLIO MAYOR EXTENSION
                    if (error.equals(CValidacion.FOLIO_MAYOR_EXT_MSG)) {

                        tipo.setIdTipoNota(CTipoNota.FOLIO_MAYOR_EXTENSION);
                        tipo.setDescripcion("EL FOLIO ES DE MAYOR EXTENSION");
                    }

                    //FOLIO NO EXISTE
                    if (error.equals(CValidacion.FOLIO_EXISTE_MSG)) {
                        tipo.setIdTipoNota(CTipoNota.FOLIO_NO_EXISTE);
                        tipo.setDescripcion("EL FOLIO NO EXISTE");
                    }

                    //FOLIO INCONSISTENTE
                    if (error.equals(CValidacion.FOLIO_INCONSISTENTE_MSG)) {
                        tipo.setIdTipoNota(CTipoNota.FOLIO_INCONSISTENTE);
                        tipo.setDescripcion("EL FOLIO ES INCONSISTENTE");
                    }

                    if (tipo.getIdTipoNota() != null) {
                        Nota nuevaNota = new Nota();
                        nuevaNota.setTiponota(tipo);
                        nuevaNota.setUsuario(user);
                        nuevaNota.setDescripcion(tipo.getDescripcion());
                        //Turno.ID idTurno = new Turno.ID();
                        hermod.addNotaToTurno(nuevaNota, tId.getTurnoID());

                    }

                }
            }

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(FINAL):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw (e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().commit();
            }
            pm.close();
        }
        return (Turno) t.toTransferObject();
    }

    /**
     * Crea un <code>Turno</code> y un <code>Pago</code> en el sistema,
     * <p>
     * Método utilizado para transacciones
     *
     * @param p <code>PagoEnhanced</code> con sus atributos, exceptuando el
     * identificador.
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param estacion Estación asociada con la creación del <code>Pago</code>
     * @param user Usuario iniciador del proceso
     * @return un identificador del <code>Turno</code> generado.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     * @see gov.sir.core.negocio.modelo.Turno
     */
    protected TurnoEnhancedPk procesarPago(PagoEnhanced p, String estacion, String impresora, Usuario user, PersistenceManager pm)
            throws DAOException {

        if (p.getLiquidacion() == null) {
            throw new DAOException("La liquidación recilbida es null");
        }
        //double valor = p.getLiquidacion().getValor();

        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        JDOTurnosNuevosDAO turnosNuevosDao = new JDOTurnosNuevosDAO();
        //JDOBancosDAO bancosDAO = new JDOBancosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        //JDORecibosDAO recibosDAO = new JDORecibosDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        TurnoEnhancedPk tId = new TurnoEnhancedPk();
        //CirculoProcesoEnhanced circuloProcesoEnhanced = null;

        try {

            //String idLiquidacion = p.getIdLiquidacion();
            //String idSolicitud = p.getIdSolicitud();
            //Se obtiene un circulo proceso enhanced.
            //Esta consulta efectua un select for update para garantizar sincronización.
            CirculoProcesoEnhanced cpe = this.getCirculoProcesoByPago(p, pm);

            if (p.getUsuario() == null) {
                throw new DAOException(
                        "El pago no esta asociada a un Usuario");
            }

            UsuarioEnhanced usuario = p.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + p.getUsuario());
            }
            p.setUsuario(usuarior);

            LiquidacionEnhanced l = p.getLiquidacion();
            if (l == null) {
                throw new DAOException("La liquidacion recibida para procesar el pago es nula");
            }
            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = l.getIdLiquidacion();
            lId.idSolicitud = l.getIdSolicitud();
            p.setIdLiquidacion(l.getIdLiquidacion());
            p.setIdSolicitud(l.getIdSolicitud());
            p.setLiquidacion(l);
            LiquidacionEnhanced lr = liquidacionesDAO.getLiquidacionByID(lId, pm);
            if (lr == null) {
                throw new DAOException("No encontró la liquidación con el ID: " + lId.idLiquidacion);
            }
            p.setLiquidacion(lr);

            if (lr != null && lr.getCirculo() != null) {
                p.setCirculo(lr.getCirculo());
            } else {
                Log.getInstance().debug(JDOPagosDAO.class, "(PAGO)NO SE PUDO GUARDAR EL IDENTIFICADOR DEL CIRCULO " + (l != null ? l.getIdSolicitud() : ""));
            }

            /*if(!(lr instanceof LiquidacionTurnoConsultaEnhanced) && !(lr instanceof LiquidacionTurnoCorreccionEnhanced) && estacion != null) {
				EstacionReciboEnhanced.ID estRecId = new EstacionReciboEnhanced.ID();
				estRecId.idEstacion = estacion;
				long recibo = recibosDAO.getNextNumeroRecibo(estRecId, pm);
				p.setNumRecibo(String.valueOf(recibo));
			}*/
            // Crear pagos y turnos de Certificados Asociados
            if (lr instanceof LiquidacionTurnoRegistroEnhanced) {
                if (lr.getSolicitud() != null) {
                    SolicitudRegistroEnhanced solReg = (SolicitudRegistroEnhanced) lr.getSolicitud();
                    List certAsocs = solReg.getSolicitudesHijas();
                    Iterator it2 = certAsocs.iterator();
                    while (it2.hasNext()) {
                        DocPagoEfectivoEnhanced docPagoAsoc = new DocPagoEfectivoEnhanced(0);
                        List aplicaciones = new ArrayList();
                        aplicaciones.add(new AplicacionPagoEnhanced(docPagoAsoc, 0));
                        SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) it2.next();
                        SolicitudCertificadoEnhanced solCert
                                = (SolicitudCertificadoEnhanced) solAsoc.getSolicitudHija();
                        LiquidacionTurnoCertificadoEnhanced liqCert
                                = (LiquidacionTurnoCertificadoEnhanced) solCert.getLiquidaciones().get(0);
                        PagoEnhanced pagoAsoc = new PagoEnhanced(liqCert, aplicaciones);
                        pagoAsoc.setIdSolicitud(solCert.getIdSolicitud());
                        pagoAsoc.setUsuario(p.getUsuario());
                        /*TurnoEnhanced.ID turnoAsocId = */
                        this.procesarPago(pagoAsoc, estacion, null, null, pm);
                    }
                }

            }

            List aps = p.getAplicacionPagos();
            if (aps == null) {
                throw new DAOException("La lista de aplicaciones pago es nula");
            }
            Iterator ita = aps.iterator();
            while (ita.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) ita.next();
                if (ap == null) {
                    throw new DAOException("La aplicacion de Pago es nula");
                }

                if (lr != null && lr.getCirculo() != null) {
                    ap.setCirculo(lr.getCirculo());
                }

                DocumentoPagoEnhanced dp = ap.getDocumentoPago();
                if (dp == null) {
                    throw new DAOException("El documento de pago es nulo");
                }
                DocumentoPagoEnhancedPk dpId = new DocumentoPagoEnhancedPk();
                if (dp.getIdDocumentoPago() != null) {
                    dpId.idDocumentoPago = dp.getIdDocumentoPago();
                    DocumentoPagoEnhanced dpr = this.getDocumentosPagoById(dpId, pm);
                    // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                    // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
                    if (dpr instanceof DocPagoConsignacionEnhanced || dpr instanceof DocPagoChequeEnhanced || dpr instanceof DocPagoNotaDebitoEnhanced || dpr instanceof DocPagoGeneralEnhanced) {
                        Date fechaDate = dpr.getFechaCreacion();
                        Calendar calendarDocPago = new GregorianCalendar();
                        calendarDocPago.setTime(fechaDate);
                        Calendar calendarHoy = new GregorianCalendar();
                        calendarHoy.setTime(new Date());
                        /*if ((calendarDocPago.get(Calendar.YEAR) < calendarHoy.get(Calendar.YEAR)) ||
                                                    (calendarDocPago.get(Calendar.DAY_OF_YEAR) < calendarHoy.get(Calendar.DAY_OF_YEAR))) {
                                                    throw new DAOException("El saldo del documento solo puede utilizarse el primer día que ingresa  al sistema");
                                                }*/
//                                            } catch (java.text.ParseException pe) {
//                                                Log.getInstance().error(JDOPagosDAO.class,"Error de formato en fecha: " + fechaString + " : " + pe.getMessage(), pe);
//                                            }
                    }

                    double saldo = dpr.getSaldoDocumento() - ap.getValorAplicado();

                    if (saldo < 0) {
                        throw new DAOException("Saldo insuficiente en el documento de pago");
                    }

                    dpr.setSaldoDocumento(saldo);
                    ap.setDocumentoPago(dpr);
                    ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                    ap.setPago(p);
                    if (dpr == null) {
                        if (dp != null) {
                            dp.setCirculo(p.getCirculo());
                        }
                        ap.setDocumentoPago(dp);
                    } else {
                        dpr.setCirculo(p.getCirculo());
                        ap.setDocumentoPago(dpr);
                    }
                } else {
                    double saldo = dp.getValorDocumento() - ap.getValorAplicado();
                    dp.setSaldoDocumento(saldo);
                    DocumentoPagoEnhanced dpr = this.crearDocumentoPago(dp, pm);
                    dp.setCirculo(p.getCirculo());
                    ap.setDocumentoPago(dp);
                    ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                    ap.setPago(p);
                }
            }
            if (p.getFecha() == null) {
                p.setFecha(new Date());
            }
            pm.makePersistent(p);
            PagoEnhancedPk pId = (PagoEnhancedPk) pm.getObjectId(p);
            if (String.valueOf(p.getLiquidacion().getSolicitud().getProceso().getIdProceso()).compareTo(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS) == 0) {
                SolicitudRepartoNotarialEnhanced se = (SolicitudRepartoNotarialEnhanced) p.getLiquidacion().getSolicitud();
                tId = turnosNuevosDao.crearTurnoReparto(se, cpe, usuarior, pm);
            } else {
                tId = turnosDAO.crearTurno(cpe, pId, impresora, user, pm);
            }

            if (tId == null) {
                throw new DAOException("No se pudo crear el turno");
            }

        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOPagosDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException de) {
            Log.getInstance().error(JDOPagosDAO.class, de.getMessage(), de);
            throw new DAOException(de.getMessage(), de);
        }

        return tId;
    }

    /**
     * Crea un <code>Pago</code> en el sistema.
     * <p>
     * Método utilizado para transacciones.
     *
     * @param p <code>PagoEnhanced</code> con sus atributos, exceptuando el
     * identificador.
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param estacion Estación asociada con la creación del <code>Pago</code>
     * @param turno Turno asociado con la creación del <code> Pago </code>
     * @return un identificador del <code>Pago</code> generado.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     *
     */
    protected PagoEnhancedPk procesarPago(
            PagoEnhanced p,
            String estacion,
            PersistenceManager pm,
            TurnoEnhanced turno,
            Rol rol)
            throws DAOException {

        if (p.getLiquidacion() == null) {
            throw new DAOException("La liquidación recilbida es null");
        }
        //double valor = p.getLiquidacion().getValor();

        //JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        //JDOBancosDAO bancosDAO = new JDOBancosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        //JDORecibosDAO recibosDAO = new JDORecibosDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        PagoEnhancedPk pId = null;
        //CirculoProcesoEnhanced circuloProcesoEnhanced = null;

        try {

            //String idLiquidacion = p.getIdLiquidacion();
            //String idSolicitud = p.getIdSolicitud();
            if (p.getUsuario() == null) {
                throw new DAOException(
                        "El pago no esta asociada a un Usuario");
            }

            UsuarioEnhanced usuario = p.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + p.getUsuario());
            }
            p.setUsuario(usuarior);

            //Se obtiene un circulo proceso enhanced.
            //Esta consulta efectua un select for update para garantizar sincronización.
            //CirculoProcesoEnhanced cpe = this.getCirculoProcesoByPago(p, pm);
            LiquidacionEnhanced l = p.getLiquidacion();
            if (l == null) {
                throw new DAOException("La liquidacion recibida para procesar el pago es nula");
            }
            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = l.getIdLiquidacion();
            lId.idSolicitud = l.getIdSolicitud();
            p.setIdLiquidacion(l.getIdLiquidacion());
            p.setIdSolicitud(l.getIdSolicitud());
            p.setLiquidacion(l);
            /*if (estacion != null) {
				EstacionReciboEnhanced.ID estRecId = new EstacionReciboEnhanced.ID();
				estRecId.idEstacion = estacion;
				long recibo = recibosDAO.getNextNumeroRecibo(estRecId, pm);
				p.setNumRecibo(String.valueOf(recibo));
			}*/
            LiquidacionEnhanced lr = liquidacionesDAO.getLiquidacionByID(lId, pm);
            if (lr == null) {
                throw new DAOException("No encontró la liquidación con el ID: " + lId.idLiquidacion);
            }
            p.setLiquidacion(lr);

            if (lr != null && lr.getCirculo() != null) {
                p.setCirculo(lr.getCirculo());
            } else {
                Log.getInstance().debug(JDOPagosDAO.class, "(PAGO)NO SE PUDO GUARDAR EL IDENTIFICADOR DEL CIRCULO " + (l != null ? l.getIdSolicitud() : ""));
            }

            List aps = p.getAplicacionPagos();
            if (aps == null) {
                throw new DAOException("La lista de aplicaciones pago es nula");
            }
            Iterator ita = aps.iterator();
            while (ita.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) ita.next();
                if (ap == null) {
                    throw new DAOException("La aplicacion de Pago es nula");
                }

                if (lr != null && lr.getCirculo() != null) {
                    ap.setCirculo(lr.getCirculo());
                }

                DocumentoPagoEnhanced dp = ap.getDocumentoPago();
                if (dp == null) {
                    throw new DAOException("El documento de pago es nulo");
                }
                DocumentoPagoEnhancedPk dpId = new DocumentoPagoEnhancedPk();
                if (dp.getIdDocumentoPago() != null) {
                    dpId.idDocumentoPago = dp.getIdDocumentoPago();
                    DocumentoPagoEnhanced dpr = this.getDocumentosPagoById(dpId, pm);
                    ap.setDocumentoPago(dpr);
                    ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                    ap.setPago(p);
                    if (dpr == null) {
                        if (dp != null) {
                            dp.setCirculo(p.getCirculo());
                        }
                        ap.setDocumentoPago(dp);
                    } else {
                        dpr.setCirculo(p.getCirculo());
                        ap.setDocumentoPago(dpr);
                    }
                } else {
                    DocumentoPagoEnhanced dpr = this.crearDocumentoPago(dp, pm);
                    dp.setCirculo(p.getCirculo());
                    ap.setDocumentoPago(dp);
                    ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                    ap.setPago(p);
                }
            }

            pm.makePersistent(p);
            pId = (PagoEnhancedPk) pm.getObjectId(p);

        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOPagosDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException de) {
            Log.getInstance().error(JDOPagosDAO.class, de.getMessage(), de);
            throw new DAOException(de.getMessage(), de);
        } catch (Throwable e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return pId;
    }

    /**
     * Obtiene la informacion existente en la base de datos, de los documentos
     * de pago asociados a un <code>Pago</code> por crearse
	 * @param p <code>Pago</code> con sus atributos, exceptuando el identificador
	 * @return <code>Pago</code> con toda la informacion de sus documentos de pago
     * @throws DAOException
     * @gov.sir.core.negocio.modelo.Pago
     * @gov.sir.core.negocio.modelo.DocumentoPago
     */
    public Pago validarPago(Pago pago) throws DAOException {

        //double valor = 0;
        PersistenceManager pm = AdministradorPM.getPM();
        PagoEnhanced p = PagoEnhanced.enhance(pago);
        DocumentoPagoEnhanced dpr = null;
        List aps = p.getAplicacionPagos();
        Iterator it = aps.iterator();

        try {
            while (it.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) it.next();
                DocumentoPagoEnhanced dp = ap.getDocumentoPago();
				dpr=null;

                // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.

                if (dp instanceof DocPagoChequeEnhanced) {
                    DocPagoChequeEnhanced dpc = (DocPagoChequeEnhanced) dp;
                    dpc = this.getDocumentosPagoByCheque(dpc, pm);
                    dpr = dpc;
                } else if (dp instanceof DocPagoNotaDebitoEnhanced) {
                    DocPagoNotaDebitoEnhanced dpc = (DocPagoNotaDebitoEnhanced) dp;
                    dpc = this.getDocumentosPagoByNotaDebito(dpc, pm);
                    dpr = dpc;
                } else if (dp instanceof DocPagoConsignacionEnhanced) {
                    DocPagoConsignacionEnhanced dpo = (DocPagoConsignacionEnhanced) dp;
                    dpo = this.getDocumentosPagoByConsignacion(dpo, pm);
                    dpr = dpo;
                } 
                /**
                * @autor HGOMEZ 
                * @mantis 12422 
                * @Requerimiento 049_453 
                * @descripcion Se validan los pagos Tarjeta Credito, Tarjeta Debito y
                * PSE.
                */
                else if (dp instanceof DocPagoTarjetaCreditoEnhanced) {
                    DocPagoTarjetaCreditoEnhanced dpo = (DocPagoTarjetaCreditoEnhanced) dp;
                    dpo = this.getDocumentosPagoByTarjetaCredito(dpo, pm);
                    //Edgar Lora: Mantis: 0012422
                    //dpr = dpo;
                    if(dpo != null){
                        throw new Exception("El numero de aprobacion " + dpo.getNumeroAprobacion() + " ingresado ya fue utilizado.");
                    }
                } else if (dp instanceof DocPagoTarjetaDebitoEnhanced) {
                    DocPagoTarjetaDebitoEnhanced dpo = (DocPagoTarjetaDebitoEnhanced) dp;
                    dpo = this.getDocumentosPagoByTarjetaDebito(dpo, pm);
                    //Edgar Lora: Mantis: 0012422
                    //dpr = dpo;
                    if(dpo != null){
                        throw new Exception("El numero de aprobacion " + dpo.getNumeroAprobacion() + " ingresado ya fue utilizado.");
                    }
                } 
//                else if (dp instanceof DocPagoConvenioEnhanced) {
                //                    DocPagoConvenioEnhanced dpo = (DocPagoConvenioEnhanced) dp;
                //                    dpo = this.getDocumentosPagoByConvenio(dpo, pm);
                //                    dpr = dpo;
                //                } 
                else if (dp instanceof DocPagoElectronicoPSEEnhanced) {
                    DocPagoElectronicoPSEEnhanced dpo = (DocPagoElectronicoPSEEnhanced) dp;
                    dpo = this.getDocumentosByPse(dpo, pm);
                    //Edgar Lora: Mantis: 0012422
                    //dpr = dpo;
                    /**
                     * @author Cesar Ramírez
                     * @change: 1607.AJUSTE.BANCO.FRANQUICIA.FORMAS.PAGO
                     * Se obtiene el turnoIdWorkFlow poer medio del idDocumentoPago-Solicitud-Turno.
                     **/
                    if(dpo != null){
                        String idDocumentoPago = dpo.getIdDocumentoPago();
                        String turnoIdWorkFlow = this.getTurnobyIdDocumentoPago(idDocumentoPago, pm);
                        throw new Exception("El número de aprobación " + dpo.getNumeroAprobacion() + " ingresado ya fue utilizado en el turno " + turnoIdWorkFlow + ".");
                    }
                }
                else if (dp instanceof DocPagoGeneralEnhanced){
                    DocPagoGeneralEnhanced dpo = (DocPagoGeneralEnhanced) dp;
                    String noAprobacion = null;
                    noAprobacion = this.getNoAprobacion(dpo);
                    if(dpo.getSaldoDocumento() == 0d && dpo.getIdDocumentoPago() == null){
                        if(noAprobacion != null){
                            String idDocumentoPago = dpo.getIdDocumentoPago();
                            String turnoIdWorkFlow = this.getTurnobyIdDocumentoPago(idDocumentoPago, pm);
                            throw new Exception("El número de aprobación " + dpo.getNoAprobacion() + " ingresado ya fue utilizado en el turno " + turnoIdWorkFlow + ".");
                        }
                    }
                }

                if (dpr == null) {
                    dp.setSaldoDocumento(dp.getValorDocumento() - ap.getValorAplicado());
                //Edgar Lora: Mantis: 0012422
                } /*else {
                    dp.setSaldoDocumento(dpr.getSaldoDocumento() - ap.getValorAplicado());
                    dp.setValorDocumento(dpr.getValorDocumento());
                    dp.setIdDocumentoPago(dpr.getIdDocumentoPago());
                }*/
                if (dp != null && p != null) {
                    dp.setCirculo(p.getCirculo());
                }
                ap.setDocumentoPago(dp);
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }

        return (Pago) p.toTransferObject();
    }

    /**
     * @author Cesar Ramírez
     * @change: 1607.AJUSTE.BANCO.FRANQUICIA.FORMAS.PAGO Funcion que obtiene el
     * turnoIdWorkFlow de acuerdo a su idDocumentoPago
     * @param idDocumentoPago <code>String</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return turnoId <code>String</code>
     * @throws DAOException
     */
    private String getTurnobyIdDocumentoPago(String idDocumentoPago, PersistenceManager pm) throws DAOException {
        String turnoId = "";
        TurnoEnhanced turnofinal = null;

        try {
            Query query = pm.newQuery(AplicacionPagoEnhanced.class);
            query.setFilter("idDocumentoPago == '" + idDocumentoPago + "'");

            List rta = (List) query.execute();
            if (rta != null && rta.size() > 0) {
                Iterator iter = rta.iterator();
                AplicacionPagoEnhanced aPago = (AplicacionPagoEnhanced) iter.next();
                gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO jdo_turno = new gov.sir.hermod.dao.impl.jdogenie.JDOTurnosDAO();
                TurnoEnhanced turnob = jdo_turno.getTurnoBySolicitud(aPago.getIdSolicitud(), pm);
                TurnoEnhancedPk toid = new TurnoEnhancedPk();
                if (turnob != null) {
                    toid.anio = turnob.getAnio();
                    toid.idCirculo = turnob.getIdCirculo();
                    toid.idProceso = turnob.getIdProceso();
                    toid.idTurno = turnob.getIdTurno();
                    turnofinal = jdo_turno.getTurnoByID(toid, pm);
                    turnoId = turnofinal.getIdWorkflow();
                }
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return turnoId;
    }

    /**
     * Crea un <code>DocumentoPago</code> en el sistema
     *
     * @param dp <code>DocumentoPago</code> con sus atributos, exceptuando el
     * identificador
     * @return <code>DocumentoPago</code> generado, con su identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @see gov.sir.core.negocio.modelo.DocumentoPagoEnhanced
     */
    public DocumentoPago crearDocumentoPago(DocumentoPago dp) throws DAOException {
        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            dpr = this.crearDocumentoPago(DocumentoPagoEnhanced.enhance(dp), pm);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            dpr = null;
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocumentoPago) dpr.toTransferObject();
    }

    /**
     * Crea un <code>DocumentoPago</code> en el sistema.
     * <p>
     * Método utilizado para transacciones.
     *
     * @param dp <code>DocumentoPagoEnhanced</code> con sus atributos,
     * exceptuando el identificador.
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return <code>DocumentoPago</code> generado, con su identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     * @see gov.sir.core.negocio.modelo.DocumentoPagoEnhanced
     */
    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Se refactoriza el método.
     */
    protected DocumentoPagoEnhanced crearDocumentoPago(DocumentoPagoEnhanced dp, PersistenceManager pm)
            throws DAOException {
        JDOBancosDAO BancosDAO = new JDOBancosDAO();
        JDOBancosFranquiciasDAO BancosFranquiciasDAO = new JDOBancosFranquiciasDAO();   //HGOMEZ 12422
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        DocumentoPagoEnhanced rta = null;
        BancoEnhanced b = null;
        BancoEnhancedPk bId = new BancoEnhancedPk();

        BancoFranquiciaEnhanced bancoFranquiciaEnhanced = null;
        BancoFranquiciaEnhancedPk bancoFranquiciaEnhancedPk = new BancoFranquiciaEnhancedPk();

        System.out.println("LLEGO A CREAR DOCUMENTO");
        try {
            System.out.println("TRY CATCH  CREAR DOCUMENTO");
            //Asignación de llave:
            dp.setIdDocumentoPago(String.valueOf(solDAO.getSecuencial(CSecuencias.SIR_OP_DOCUMENTO_PAGO, null)));
            System.out.println("SERIAL PAGO " + String.valueOf(solDAO.getSecuencial(CSecuencias.SIR_OP_DOCUMENTO_PAGO, null)));

            dp.setFechaCreacion(new Date());

            // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
            // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
            //Documento de Pago Cheque
            System.out.println("ANTES INSTANCIAS CREAR DOCUMENTO");
            if (dp instanceof DocPagoChequeEnhanced) {
                DocPagoChequeEnhanced dpc = (DocPagoChequeEnhanced) dp;
                bId.idBanco = dpc.getBanco().getIdBanco();
                b = BancosDAO.getBancoByID(bId, pm);
                dpc.setBanco(b);
            } else if (dp instanceof DocPagoNotaDebitoEnhanced) { //Documento de Pago Nota Debito
                DocPagoNotaDebitoEnhanced dpc = (DocPagoNotaDebitoEnhanced) dp;
                bId.idBanco = dpc.getBanco().getIdBanco();
                b = BancosDAO.getBancoByID(bId, pm);
                dpc.setBanco(b);
            } else if (dp instanceof DocPagoConsignacionEnhanced) { //Documento de Pago Consignación
                DocPagoConsignacionEnhanced dpo = (DocPagoConsignacionEnhanced) dp;
                bId.idBanco = dpo.getBanco().getIdBanco();
                b = BancosDAO.getBancoByID(bId, pm);
                dpo.setBanco(b);
            } else if (dp instanceof DocPagoTarjetaCreditoEnhanced) { //Documento de Pago Tarjeta Credito
                DocPagoTarjetaCreditoEnhanced dpo = (DocPagoTarjetaCreditoEnhanced) dp;
                bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                dpo.setBancoFranquicia(bancoFranquiciaEnhanced);
            } else if (dp instanceof DocPagoTarjetaDebitoEnhanced) { //Documento de Pago Tarjeta Debito
                DocPagoTarjetaDebitoEnhanced dpo = (DocPagoTarjetaDebitoEnhanced) dp;
                bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                dpo.setBancoFranquicia(bancoFranquiciaEnhanced);
                //Edgar Lora: Mantis: 0012422
            } else if (dp instanceof DocPagoElectronicoPSEEnhanced) { // Documento de Pago PSE
                DocPagoElectronicoPSEEnhanced dpo = (DocPagoElectronicoPSEEnhanced) dp;
                bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                dpo.setBancoFranquicia(bancoFranquiciaEnhanced);
            } else if (dp instanceof DocPagoGeneralEnhanced) {
                System.out.println("instanceof DocPagoGeneralEnhanced");
                DocPagoGeneralEnhanced dpo = (DocPagoGeneralEnhanced) dp;
                if(dpo.getBancoFranquicia() != null){
                    bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                    bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                    bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                    dpo.setBancoFranquicia(bancoFranquiciaEnhanced);
                }
                if(dpo.getBanco() != null){
                    bId.idBanco = dpo.getBanco().getIdBanco();
                    b = BancosDAO.getBancoByID(bId, pm);
                    dpo.setBanco(b);
                }
                             
//                System.out.println("ANTES INSTANCIAS CREAR DOCUMENTO" +dpo.getValorDocumento());
//                dp.setValorDocumento(dpo.getValorDocumento());
            }
//            System.out.println("DESPUES INSTANCIAS CREAR DOCUMENTO" +dp.getValorDocumento());
//            else if (dp instanceof DocPagoConvenioEnhanced) { // Documento de Pago Convenio
////                DocPagoConvenioEnhanced dpo = (DocPagoConvenioEnhanced) dp;
////                dpo.setNumeroAprobacion(dpo.getNumeroAprobacion());
//            }
            pm.makePersistent(dp);

            rta = dp;
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Obtiene un <code>DocPagoCheque</code> dados su número de cheque y número
     * de cuenta.
     * <p>
     * Método utilizado para transacciones
     *
     * @param dp Documento de pago con sus atributos número de cheque y número
     * de cuenta.
     * @param pm  <code>PersistenceManager</code> de la transacción.
     * @return <code>DocPagoChequeEnhanced</code> con todos sus atributos,
     * incluyendo el identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocPagoCheque
     * @see gov.sir.core.negocio.modelo.DocPagoChequeEnhanced
     */
    protected DocPagoChequeEnhanced getDocumentosPagoByCheque(
            DocPagoChequeEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoChequeEnhanced rta = null;

        if (dp.getNoCheque() != null && dp.getNoCuenta() != null) {
            try {
                /**
                 * @author: Fernando Padilla
                 * @change: Se modifica la consulta para que no tenga en cuenta
                 * los turnos anulados Acta - Requerimiento No 072
                 *
                 */
                Query query = pm.newQuery(DocPagoChequeEnhanced.class);
                query.declareParameters("String cheque, String bancoId");
                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");

                /**
                 * @autor HGOMEZ
                 * @mantis 12422
                 * @Requerimiento 049_453
                 * @descripcion Se comenta la siguiente línea para que se tengan
                 * en cuenta los documentos anulados.
                 */
                query.setFilter("noCheque == cheque && banco.idBanco == bancoId && "
                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
                        + "ap.idSolicitud == sol.idSolicitud && "
                        //+ "t.anulado == \"N\" && "
                        + "sol.idSolicitud == t.solicitud.idSolicitud");

                Collection col = (Collection) query.execute(dp.getNoCheque(), dp.getBanco().getIdBanco());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoChequeEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Método creado por OSBERT LINERO - Iridium Telecomunicaciones e
     * informática Ltda. Cambio para agregar nota débito requerimiento 108 -
     * Incidencia Mantis 02360.
     *
     * Obtiene un <code>DocPagoNotaDebito</code> dado su número de nota debito.
     * Método utilizado para transacciones
     *
     * @param dp Documento de pago con el atributo número de nota debito.
     * @param pm  <code>PersistenceManager</code> de la transacción.
     * @return <code>DocPagoNotaDebitoEnhanced</code> con todos sus atributos.
     * @throws gov.sir.hermod.dao.DAOException
     * @see gov.sir.core.negocio.modelo.DocPagoNotaDebito
     * @see gov.sir.core.negocio.modelo.DocPagoNotaDebitohanced
     */
    protected DocPagoNotaDebitoEnhanced getDocumentosPagoByNotaDebito(
            DocPagoNotaDebitoEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoNotaDebitoEnhanced rta = null;

        if (dp.getNoNotaDebito() != null && dp.getNoCuenta() != null) {
            try {
                Query query = pm.newQuery(DocPagoNotaDebitoEnhanced.class);
                //String cuenta = dp.getNoCuenta();
                String notaDebito = dp.getNoNotaDebito();
                BancoEnhanced banco = dp.getBanco();
                query.declareParameters("String notaDebito, String bancoId");
                //query.declareVariables("BancoEnhanced banco");
                query.setFilter("noNotaDebito == notaDebito && banco.idBanco == bancoId");
                Collection col = (Collection) query.execute(notaDebito, banco.getIdBanco());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoNotaDebitoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocPagoConsignacion</code> dados su numero de
     * consignación e identificador de <code>Banco</code>.
     * <p>
     * Método utilizado para transacciones
     *
     * @param dp Documento de pago con sus atributos numero de consignacion y
     * banco
     * @param pm  <code>PersistenceManager</code> de la transacción.
     * @return <code>DocPagoConsignacionEnhanced</code> con todos sus atributos,
     * incluyendo el identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocPagoConsignacion
     * @see gov.sir.core.negocio.modelo.DocPagoConsignacionEnhanced
     */
    protected DocPagoConsignacionEnhanced getDocumentosPagoByConsignacion(
            DocPagoConsignacionEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        Query query = null;

        DocPagoConsignacionEnhanced rta = null;

        if (dp.getBanco() != null) {
            try {
                /**
                 * @author: Fernando Padilla @change: Se modifica la consulta
                 * para que no tenga en cuenta los turnos anulados Acta -
                 * Requerimiento No 072
                 *
                 */
                /**
                 * @autor HGOMEZ
                 * @mantis 12422
                 * @Requerimiento 049_453
                 * @descripcion Se comenta la siguiente línea para que se tengan
                 * en cuenta los documentos anulados.
                 */
                query = pm.newQuery(DocPagoConsignacionEnhanced.class);
                query.declareParameters("String idbanco, String consignacion");
                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
                query.setFilter("banco.idBanco == idbanco && noConsignacion == consignacion && "
                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
                        + "ap.idSolicitud == sol.idSolicitud && " //+ "t.anulado == \"N\" && "
                        + "sol.idSolicitud == t.solicitud.idSolicitud");

                Collection col = (Collection) query.execute(dp.getBanco().getIdBanco(), dp.getNoConsignacion());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoConsignacionEnhanced) iter.next();
                    }
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } finally {
                query.closeAll();
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoTarjetaCreditoEnhanced getDocumentosPagoByTarjetaCredito(
            DocPagoTarjetaCreditoEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        Query query = null;

        DocPagoTarjetaCreditoEnhanced rta = null;

        if (dp.getBancoFranquicia() != null && dp.getNumeroTarjeta() != null) {
            try {
                /**
                 * @author: Fernando Padilla @change: Se modifica la consulta
                 * para que no tenga en cuenta los turnos anulados Acta -
                 * Requerimiento No 072
                 */
                query = pm.newQuery(DocPagoTarjetaCreditoEnhanced.class);
                //Edgar Lora: Mantis: 0012422
                query.declareParameters("String numeroaprobacion, String idbanco");
                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
                query.setFilter("this.numeroAprobacion == numeroaprobacion && "
                        + "this.bancoFranquicia.idBanco == idbanco && "
                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
                        + "ap.idSolicitud == sol.idSolicitud && "
                        + "t.anulado == \"N\" && "
                        + "sol.idSolicitud == t.solicitud.idSolicitud");
                Collection col = (Collection) query.execute(dp.getNumeroAprobacion(), dp.getBancoFranquicia().getIdBanco());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoTarjetaCreditoEnhanced) iter.next();
                        if (!rta.getAnio().equals(dp.getAnio())) {
                            rta = null;
                        } else {
                            return rta;
                        }
                    }
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } finally {
                query.closeAll();
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoTarjetaDebitoEnhanced getDocumentosPagoByTarjetaDebito(
            DocPagoTarjetaDebitoEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        Query query = null;

        DocPagoTarjetaDebitoEnhanced rta = null;

        if (dp.getBancoFranquicia() != null && dp.getNumeroTarjeta() != null) {
            try {
                /**
                 * @author: Fernando Padilla @change: Se modifica la consulta
                 * para que no tenga en cuenta los turnos anulados Acta -
                 * Requerimiento No 072
                 *
                 */
                //Edgar Lora: Mantis: 0012422
                query = pm.newQuery(DocPagoTarjetaDebitoEnhanced.class);
                query.declareParameters("String numeroaprobacion, String idbanco");
                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
                query.setFilter("this.numeroAprobacion == numeroaprobacion && "
                        + "this.bancoFranquicia.idBanco == idbanco && "
                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
                        + "ap.idSolicitud == sol.idSolicitud && "
                        + "t.anulado == \"N\" && "
                        + "sol.idSolicitud == t.solicitud.idSolicitud");
                Collection col = (Collection) query.execute(dp.getNumeroAprobacion(), dp.getBancoFranquicia().getIdBanco());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoTarjetaDebitoEnhanced) iter.next();
                        if (!rta.getAnio().equals(dp.getAnio())) {
                            rta = null;
                        } else {
                            return rta;
                        }
                    }
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } finally {
                query.closeAll();
            }
        }
        return rta;
    }

    //HGOMEZ
    protected DocPagoConvenioEnhanced getDocumentosPagoByConvenio(
            DocPagoConvenioEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        Query query = null;

        DocPagoConvenioEnhanced rta = null;

//            try {
//                /**
//                 * @author: Fernando Padilla @change: Se modifica la consulta
//                 * para que no tenga en cuenta los turnos anulados Acta -
//                 * Requerimiento No 072
//                                *
//                 */
//                query = pm.newQuery(DocPagoConvenioEnhanced.class);
//                query.declareParameters("String idbanco, int idfranquicia, String numerotarjeta");
//                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
//                query.setFilter("bancoFranquicia.idBanco == idbanco && bancoFranquicia.idTipoFranquicia == idfranquicia && numeroTarjeta == numerotarjeta && "
//                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
//                        + "ap.idSolicitud == sol.idSolicitud && "
//                        + "t.anulado == \"N\" && "
//                        + "sol.idSolicitud == t.solicitud.idSolicitud");
//
//                Collection col = (Collection) query.execute(dp.getBancoFranquicia().getIdBanco(), dp.getBancoFranquicia().getIdTipoFranquicia(), dp.getNumeroTarjeta());
//
//                if (col.size() == 0) {
//                    rta = null;
//                } else {
//                    for (Iterator iter = col.iterator(); iter.hasNext();) {
//                        rta = (DocPagoTarjetaDebitoEnhanced) iter.next();
//                    }
//                }
//            } catch (JDOObjectNotFoundException e) {
//                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
//                rta = null;
//            } catch (JDOException e) {
//                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
//                throw new DAOException(e.getMessage(), e);
//            } finally {
//                query.closeAll();
//            }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoElectronicoPSEEnhanced getDocumentosByPse(
            DocPagoElectronicoPSEEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        Query query = null;

        DocPagoElectronicoPSEEnhanced rta = null;

        try {
            /**
             * @author: Fernando Padilla @change: Se modifica la consulta para
             * que no tenga en cuenta los turnos anulados Acta - Requerimiento
             * No 072
             *
             */
            //Edgar Lora: Mantis: 0012422
            query = pm.newQuery(DocPagoElectronicoPSEEnhanced.class);
            query.declareParameters("String numeroaprobacion, String idbanco");
            query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
            query.setFilter("this.numeroAprobacion == numeroaprobacion && "
                    + "this.bancoFranquicia.idBanco == idbanco && "
                    + "ap.idDocumentoPago == this.idDocumentoPago &&  "
                    + "ap.idSolicitud == sol.idSolicitud && "
                    + "t.anulado == \"N\" && "
                    + "sol.idSolicitud == t.solicitud.idSolicitud");

            Collection col = (Collection) query.execute(dp.getNumeroAprobacion(), dp.getBancoFranquicia().getIdBanco());

            if (col.size() == 0) {
                rta = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    rta = (DocPagoElectronicoPSEEnhanced) iter.next();
                    if (!rta.getAnio().equals(dp.getAnio())) {
                        rta = null;
                    } else {
                        return rta;
                    }
                }
            }
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            query.closeAll();
        }
        return rta;
    }

//    //HGOMEZ
//    protected DocPagoConvenioEnhanced getDocumentosConvenio(
//            DocPagoPseEnhanced dp,
//            PersistenceManager pm)
//            throws DAOException {
//
//        Query query = null;
//
//        DocPagoPseEnhanced rta = null;
//
////            try {
////                /**
////                 * @author: Fernando Padilla @change: Se modifica la consulta
////                 * para que no tenga en cuenta los turnos anulados Acta -
////                 * Requerimiento No 072
////                                *
////                 */
////                query = pm.newQuery(DocPagoPseEnhanced.class);
////                query.declareParameters("String numeroaprobacion");
////                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
////                query.setFilter("numeroAprobacion == numeroaprobacion && "
////                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
////                        + "ap.idSolicitud == sol.idSolicitud && "
////                        + "t.anulado == \"N\" && "
////                        + "sol.idSolicitud == t.solicitud.idSolicitud");
////
////                Collection col = (Collection) query.execute(dp.getNumeroAprobacion());
////
////                if (col.size() == 0) {
////                    rta = null;
////                } else {
////                    for (Iterator iter = col.iterator(); iter.hasNext();) {
////                        rta = (DocPagoPseEnhanced) iter.next();
////                    }
////                }
////            } catch (JDOObjectNotFoundException e) {
////                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
////                rta = null;
////            } catch (JDOException e) {
////                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
////                throw new DAOException(e.getMessage(), e);
////            } finally {
////                query.closeAll();
////            }
//        return rta;
//    }
    /**
     * Valida si un documento de pago fue usado en un turno anulado.
     * <p>
     *
     * @param String aIdDocumentoPago es el id del documento de pago.
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return <code>boolean</code>
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocPagoConsignacion
     * @see gov.sir.core.negocio.modelo.DocPagoConsignacionEnhanced
     */
    protected boolean isDocPagoInTurnoAnulado(String aIdDocumentoPago, PersistenceManager pm) throws DAOException {

        Query query = null;

        try {
            query = pm.newQuery(AplicacionPagoEnhanced.class);

            query.declareVariables("SolicitudEnhanced sol; TurnoEnhanced t");

            query.declareParameters("String idDocumentoPago");

            query.setFilter("this.idDocumentoPago == idDocumentoPago &&  "
                    + "this.idSolicitud == sol.idSolicitud && "
                    + "t.anulado == \"S\" && "
                    + "sol.idSolicitud == t.solicitud.idSolicitud");

            Collection col = (Collection) query.execute(aIdDocumentoPago);

            if (col.size() > 0) {
                return true;
            }

        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            query.closeAll();
        }

        return false;
    }

    /**
     * Obtiene un <code>DocumentoPago</code> dado su identificador,
     * <p>
     * método utilizado para transacciones
     *
     * @param dpID identificador del documento de pago
     * @param pm <code>PersistenceManager</code> de la transacción
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocumentoPago getDocumentosPagoById(DocumentoPagoPk dpID) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocumentosPagoById(new DocumentoPagoEnhancedPk(dpID), pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocumentoPago) dpr.toTransferObject();
    }
     /**
     * Obtiene un <code>DocumentoPago</code> dado su identificador,
     * <p>
     * método utilizado para transacciones
     *
     * @param dpID identificador del documento de pago
     * @param pm <code>PersistenceManager</code> de la transacción
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected DocumentoPagoEnhanced getDocumentosPagoByIdEnhanced(DocumentoPagoPk dpID) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocumentosPagoById(new DocumentoPagoEnhancedPk(dpID), pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return dpr;
    }
    /**
     * Crea un <code>DocumentoPagoCorreccion</code> en el sistema.
     * <p>
     * Método utilizado para transacciones.
     *
     * @param dp <code>DocumentoPagoEnhanced</code> con sus atributos,
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return <code>DocumentoPagoCorreccionEnhanced</code> generado, con su identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPagoCorreccion
     * @see gov.sir.core.negocio.modelo.DocumentoPagoCorreccionEnhanced
     */
   protected DocumentoPagoCorreccionEnhanced crearDocumentoPagoCorreccion(DocumentoPagoEnhanced dp, PersistenceManager pm, String iduser)
            throws DAOException {
        JDOBancosDAO BancosDAO = new JDOBancosDAO();
        JDOBancosFranquiciasDAO BancosFranquiciasDAO = new JDOBancosFranquiciasDAO();   //HGOMEZ 12422
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        DocumentoPagoCorreccionEnhanced rta = null;
        BancoEnhanced b = null;
        BancoEnhancedPk bId = new BancoEnhancedPk();
        
        BancoFranquiciaEnhanced bancoFranquiciaEnhanced = null;
        BancoFranquiciaEnhancedPk bancoFranquiciaEnhancedPk = new BancoFranquiciaEnhancedPk();
        DocumentoPagoCorreccionEnhanced c = new DocumentoPagoCorreccionEnhanced();
      
        try {
            //Asignación de llave:
            long secuenciapagoCorr = solDAO.getSecuencial("SIR_OP_CORR_FORMA_PAGO", null);
            c.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
            c.setIdDocumentoPago(dp.getIdDocumentoPago());
            c.setFechaCreacion(new Date());
            c.setNombreCanal(dp.getNombreCanal());
            c.setSaldoDocumento(dp.getSaldoDocumento());
            c.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
            c.setTipoPago(dp.getTipoPago());
            c.setValorDocumento(dp.getValorDocumento());
            c.setCirculo(dp.getCirculo());
            c.setEstadocorreccion(dp.getEstadocorreccion());
            if (dp instanceof DocPagoChequeEnhanced) {
                DocPagoChequeEnhanced dpc = (DocPagoChequeEnhanced) dp;
                bId.idBanco = dpc.getBanco().getIdBanco();
                b = BancosDAO.getBancoByID(bId, pm);
                DocPagoChequeCorreccionEnhanced ce1 = new DocPagoChequeCorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                ce1.setNoCheque(dpc.getNoCheque());
                ce1.setNoCuenta(dpc.getNoCuenta());
                ce1.setCodSucursalBanco(dpc.getCodSucursalBanco());
                ce1.setBanco(b);
                c = (DocPagoChequeCorreccionEnhanced) ce1;
            } else if (dp instanceof DocPagoNotaDebitoEnhanced) { //Documento de Pago Nota Debito
                DocPagoNotaDebitoEnhanced dpc = (DocPagoNotaDebitoEnhanced) dp;
                bId.idBanco = dpc.getBanco().getIdBanco();
                b = BancosDAO.getBancoByID(bId, pm);
                DocPagoNotaDebitoCorreccionEnhanced ce1 = new DocPagoNotaDebitoCorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                ce1.setNoNotaDebito(dpc.getNoNotaDebito());
                ce1.setNoCuenta(dpc.getNoCuenta());
                ce1.setCodSucursalBanco(dpc.getCodSucursalBanco());
                ce1.setBanco(b);
                c = (DocPagoNotaDebitoCorreccionEnhanced) ce1;
            } else if (dp instanceof DocPagoConsignacionEnhanced) { //Documento de Pago Consignación
                DocPagoConsignacionEnhanced dpo = (DocPagoConsignacionEnhanced) dp;
                bId.idBanco = dpo.getBanco().getIdBanco();
                b = BancosDAO.getBancoByID(bId, pm);
                DocPagoConsignacionCorreccionEnhanced ce1 = new DocPagoConsignacionCorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                ce1.setNoConsignacion(dpo.getNoConsignacion());
                ce1.setNoCuenta(dpo.getNoCuenta());
                ce1.setCodSucursal(dpo.getCodSucursal());
                ce1.setBanco(b);
                c = (DocPagoConsignacionCorreccionEnhanced) ce1;
            } else if (dp instanceof DocPagoTarjetaCreditoEnhanced) { //Documento de Pago Tarjeta Credito
                DocPagoTarjetaCreditoEnhanced dpo = (DocPagoTarjetaCreditoEnhanced) dp;
                bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                DocPagoTarjetaCreditoCorreccionEnhanced ce1 = new DocPagoTarjetaCreditoCorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                ce1.setNumeroAprobacion(dpo.getNumeroAprobacion());
                ce1.setNumeroTarjeta(dpo.getNumeroTarjeta());
                ce1.setBancoFranquicia(bancoFranquiciaEnhanced);
                c = (DocPagoTarjetaCreditoCorreccionEnhanced) ce1;
            } else if (dp instanceof DocPagoTarjetaDebitoEnhanced) { //Documento de Pago Tarjeta Debito
                DocPagoTarjetaDebitoEnhanced dpo = (DocPagoTarjetaDebitoEnhanced) dp;
                bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                DocPagoTarjetaDebitoCorreccionEnhanced ce1 = new DocPagoTarjetaDebitoCorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                ce1.setNumeroTarjeta(dpo.getNumeroTarjeta());
                ce1.setNumeroAprobacion(dpo.getNumeroAprobacion());
                ce1.setBancoFranquicia(bancoFranquiciaEnhanced);
                c = (DocPagoTarjetaDebitoCorreccionEnhanced) ce1;
                //Edgar Lora: Mantis: 0012422
            } else if (dp instanceof DocPagoElectronicoPSEEnhanced) { // Documento de Pago PSE
                DocPagoElectronicoPSEEnhanced dpo = (DocPagoElectronicoPSEEnhanced) dp;
                bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                DocPagoElectronicoPSECorreccionEnhanced ce1 = new DocPagoElectronicoPSECorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                ce1.setNumeroAprobacion(dpo.getNumeroAprobacion());
                ce1.setBancoFranquicia(bancoFranquiciaEnhanced);
                c = (DocPagoElectronicoPSECorreccionEnhanced) ce1;
            } else if (dp instanceof DocPagoGeneralEnhanced) {
                DocPagoGeneralEnhanced dpo = (DocPagoGeneralEnhanced) dp;
                DocPagoGeneralCorreccionEnhanced ce1 = new DocPagoGeneralCorreccionEnhanced();
                ce1.setIdDocumentoPagoCorreccion(String.valueOf(secuenciapagoCorr));
                ce1.setIdDocumentoPago(dp.getIdDocumentoPago());
                ce1.setFechaCreacion(new Date());
                ce1.setNombreCanal(dp.getNombreCanal());
                ce1.setSaldoDocumento(dp.getSaldoDocumento());
                ce1.setSaldoDocumentoAnulacion(dp.getSaldoDocumentoAnulacion());
                ce1.setTipoPago(dp.getTipoPago());
                ce1.setValorDocumento(dp.getValorDocumento());
                ce1.setCirculo(dp.getCirculo());
                ce1.setEstadocorreccion(dp.getEstadocorreccion());
                //DocPagoGeneralCorreccionEnhanced ce = (DocPagoGeneralCorreccionEnhanced) c;
                if(dpo.getBancoFranquicia() != null){
                    bancoFranquiciaEnhancedPk.idBanco = dpo.getBancoFranquicia().getIdBanco();
                    bancoFranquiciaEnhancedPk.idTipoFranquicia = dpo.getBancoFranquicia().getIdTipoFranquicia();
                    bancoFranquiciaEnhanced = BancosFranquiciasDAO.getBancoByID(bancoFranquiciaEnhancedPk, pm);
                    ce1.setBancoFranquicia(bancoFranquiciaEnhanced);
                }
                if(dpo.getBanco() != null){
                    bId.idBanco = dpo.getBanco().getIdBanco();
                    b = BancosDAO.getBancoByID(bId, pm);
                    ce1.setBanco(b);
                }
                ce1.setCodSucursal(dpo.getCodSucursal());
                ce1.setFechaDocu(dpo.getFechaDocu());
                ce1.setIdCtp(dpo.getIdCtp());
                ce1.setIdTipoDocPago(dpo.getIdTipoDocPago());
                ce1.setNoAprobacion(dpo.getNoAprobacion());
                ce1.setNoConsignacion(dpo.getNoConsignacion());
                ce1.setNoCuenta(dpo.getNoCuenta());
                ce1.setNoDocumento(dpo.getNoDocumento());
                c = (DocumentoPagoCorreccionEnhanced) ce1;
            }
            String usercreacion = this.idUsuarioCreacionfromidDocumento(dp.getIdDocumentoPago());
            c.setUsuariocreacion(usercreacion);
            c.setUsuariomodifica(iduser);
            Date date = new Date();
            String pattern = "dd/MM/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String todayAsString = df.format(date);
            c.setFechamodificacion(todayAsString);
            pm.makePersistent(c);

            rta = c;
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }
    protected String idUsuarioCreacionfromidDocumento(String docupago) throws DAOException{
        String iduser = "";
        DocumentoPago rta = new DocumentoPago();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
           
            
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            String consulta = "SELECT SIR_OP_PAGO.ID_USUARIO FROM SIR_OP_DOCUMENTO_PAGO INNER JOIN SIR_OP_APLICACION_PAGO ON SIR_OP_DOCUMENTO_PAGO.ID_DOCUMENTO_PAGO = SIR_OP_APLICACION_PAGO.ID_DOCUMENTO_PAGO INNER JOIN SIR_OP_PAGO ON SIR_OP_APLICACION_PAGO.ID_LIQUIDACION = SIR_OP_PAGO.ID_LIQUIDACION AND SIR_OP_APLICACION_PAGO.ID_SOLICITUD = SIR_OP_PAGO.ID_SOLICITUD WHERE SIR_OP_DOCUMENTO_PAGO.ID_DOCUMENTO_PAGO\n" +
            "= ?";
            ps = connection.prepareStatement(consulta);
             ps.setString(1, docupago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
        
            while (rs.next()) {
                iduser = rs.getString("id_usuario");
            }
        } catch (DAOException e) {
            rta = null;
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            throw e;
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            jdoPM.close();
        }
         return iduser;   
    }
    public String getNoCuentabyIdCtp(String idctp) throws DAOException{
         Connection connection = null;
        PreparedStatement ps = null;
        VersantPersistenceManager jdoPM = null;
        ResultSet rs = null;
        String cuentadestino ="";
        try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                String consulta = "SELECT CB_NRO_CUENTA, sir_op_banco.bnco_nombre FROM sir_ne_circulo_tipo_pago inner join sir_op_cuentas_bancarias on sir_ne_circulo_tipo_pago.cb_id = sir_op_cuentas_bancarias.cb_id inner join SIR_OP_BANCO on sir_op_cuentas_bancarias.CB_ID_BANCO = sir_op_banco.id_banco where sir_ne_circulo_tipo_pago.id_ctp = ?";
                ps = connection.prepareStatement(consulta);
                ps.setLong(1, Long.valueOf(idctp));
                rs = ps.executeQuery();
                jdoPM.currentTransaction().commit();
                while (rs.next()) {
                    cuentadestino = ""+rs.getString("bnco_nombre")+ "-"+ rs.getString("CB_NRO_CUENTA");
                }
                
                
        } catch (DAOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            jdoPM.close();
        }
        return cuentadestino;
    }
    public String getNoCuentabyDocumentoPago(String documentopago) throws DAOException{
         Connection connection = null;
        PreparedStatement ps = null;
        VersantPersistenceManager jdoPM = null;
        ResultSet rs = null;
        String cuentadestino ="";
        String id_ctp ="";
        try {
               jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                String consulta = "SELECT ID_CTP from SIR_OP_DOCUMENTO_PAGO LEFT JOIN SIR_OP_TIPO_FRANQUICIA ON SIR_OP_DOCUMENTO_PAGO.ID_TIPO_FRANQUICIA = SIR_OP_TIPO_FRANQUICIA.ID_TIPO_FRANQUICIA  WHERE SIR_OP_DOCUMENTO_PAGO.ID_DOCUMENTO_PAGO = ?";
                ps = connection.prepareStatement(consulta);
                 ps.setLong(1, Long.valueOf(documentopago));
                rs = ps.executeQuery();
                jdoPM.currentTransaction().commit();
                while (rs.next()) {
                    id_ctp = rs.getString("ID_CTP");
                }
                if(id_ctp != null && !id_ctp.equals("")){
                    cuentadestino = getNoCuentabyIdCtp(id_ctp);
                }
                
        } catch (DAOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            jdoPM.close();
        }
        return cuentadestino;
    }
    public List getDocumentoCorregido(String idDocumentoPago)throws DAOException {
        DocumentoPagoCorreccionEnhanced documentoCorre = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String idDocumentoPaogCorregido="";
        VersantPersistenceManager jdoPM = null;
        String idbanco = "";
        String franquicia ="";
        List a = new ArrayList();
        String id_ctp="";
        String cuentadestino ="";
        String bancoes = "";
        try {
           
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                String consulta = "SELECT TPFR_NOMBRE,ID_CTP, ID_CORRECCION_FORMA_PAGO, SIR_OP_CORRECCION_FORMA_PAGO.ID_TIPO_FRANQUICIA, ID_BANCO from SIR_OP_CORRECCION_FORMA_PAGO LEFT JOIN SIR_OP_TIPO_FRANQUICIA ON SIR_OP_CORRECCION_FORMA_PAGO.ID_TIPO_FRANQUICIA = SIR_OP_TIPO_FRANQUICIA.ID_TIPO_FRANQUICIA  WHERE SIR_OP_CORRECCION_FORMA_PAGO.ID_DOCUMENTO_PAGO = ?";
                ps = connection.prepareStatement(consulta);
                 ps.setLong(1, Long.valueOf(idDocumentoPago));
                rs = ps.executeQuery();
                jdoPM.currentTransaction().commit();
                while (rs.next()) {
                    idDocumentoPaogCorregido = rs.getString("ID_CORRECCION_FORMA_PAGO");
                    franquicia = rs.getString("TPFR_NOMBRE");
                    idbanco = rs.getString("ID_BANCO");
                    id_ctp = rs.getString("ID_CTP");
                }
                if(!id_ctp.equals("")){
                    cuentadestino = this.getNoCuentabyIdCtp(id_ctp);
                }
                
                if(idDocumentoPaogCorregido != ""){
                    DocumentoPagoCorreccionEnhancedPk llave = new DocumentoPagoCorreccionEnhancedPk();
                    llave.idDocumentoPagoCorreccion = idDocumentoPaogCorregido;
                    documentoCorre = getDocumentoPagoAntesdeCorreccionbyId(llave,jdoPM);
                }
                a.add(documentoCorre);
                if(franquicia != null){
                    if(!franquicia.equals("")){
                    a.add(franquicia);
                    }else{
                         a.add("");
                     }
                }else{
                     a.add("");
                }
                
                if(!cuentadestino.equals("")){
                    a.add(cuentadestino);
                }else{
                     a.add("");
                 }
                if(idbanco!= null){
                   JDOBancosDAO BancosDAO = new JDOBancosDAO();
                    if(!idbanco.equals("")){
                        Banco es = BancosDAO.getBancoByID(idbanco);
                        bancoes = es.getNombre();
                        a.add(bancoes);
                    }else{
                        a.add(bancoes);
                    } 
                }else{
                     a.add("");
                }
                
                
                
        } catch (DAOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            jdoPM.close();
        }
        
        
        
        return a;
    }
      /**
     * Actualiza un <code>DocumentoPagoCorreccion</code> en el sistema.
     * <p>
     * Método utilizado para transacciones.
     *
     * @param dp <code>DocumentoPagoEnhanced</code> con sus atributos,
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return <code>DocumentoPagoEnhanced</code> generado, con su identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPagoCorreccion
     */
   protected DocumentoPagoEnhanced Actualizardocumento(DocumentoPagoEnhanced dp,DocumentoPago cor, PersistenceManager pm)
            throws DAOException {
        JDOBancosDAO BancosDAO = new JDOBancosDAO();
        JDOBancosFranquiciasDAO BancosFranquiciasDAO = new JDOBancosFranquiciasDAO();   //HGOMEZ 12422
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        DocumentoPagoEnhanced rta = null;
        DocPagoGeneral correccion = (DocPagoGeneral) cor;
        BancoEnhanced b = null;
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BancoEnhancedPk bId = new BancoEnhancedPk();
        VersantPersistenceManager jdoPM = null;
        BancoFranquiciaEnhanced bancoFranquiciaEnhanced = null;
        BancoFranquiciaEnhancedPk bancoFranquiciaEnhancedPk = new BancoFranquiciaEnhancedPk();
        try{

            String consulta = "UPDATE SIR_OP_DOCUMENTO_PAGO SET DCPG_TIPO_PAGO = ? , ESTADO_CORRECCION = 1, ID_CTP = ? , NOMBRE_CANAL = ?,ID_BANCO = ? ,ID_TIPO_FRANQUICIA = ?"
                    + " ,  DCPG_COD_SUCURSAL_BANCO  = ?  ,ID_TIPO_DOC_PAGO = ? , DCPG_NO_APROBACION = ? , DCPG_NO_CONSIGNACION = ? , DCPG_NO_CUENTA = ? , DCPG_NO_TARJETA = ? , DCPG_FECHA_DOCUMENTO = ? WHERE ID_DOCUMENTO_PAGO = ?";
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, correccion.getNombreFormaPago());
            
            if(correccion.getIdCtp() != 0){
                ps.setInt(2, correccion.getIdCtp());
            }else{
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            
            ps.setString(3, correccion.getNombreCanal());
            if(correccion.getBancoFranquicia().getIdTipoFranquicia() != 0){
                ps.setInt(5, correccion.getBancoFranquicia().getIdTipoFranquicia());
            }else{
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            if(correccion.getBanco().getIdBanco() != null){
                ps.setString(4, correccion.getBanco().getIdBanco());
            }else{
                ps.setNull(4, java.sql.Types.VARCHAR);
            }
            if(correccion.getBancoFranquicia().getIdBanco() != null){
                ps.setString(4, correccion.getBancoFranquicia().getIdBanco());
            }else{
                ps.setNull(4, java.sql.Types.VARCHAR);
            }
            if(correccion.getCodSucursal() != null){
                ps.setString(6, correccion.getCodSucursal());
            }else{
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            if(correccion.getTipoPago() != null){
                ps.setInt(7, correccion.getIdTipoDocPago());
            }else{
                ps.setNull(7, java.sql.Types.VARCHAR);
            }
            if(correccion.getNoAprobacion() != null){
                ps.setString(8, correccion.getNoAprobacion());
            }else{
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            if(correccion.getNoConsignacion() != null){
                ps.setString(9, correccion.getNoConsignacion());
            }else{
                ps.setNull(9, java.sql.Types.VARCHAR);
            }
            if(correccion.getNoCuenta() != null){
               ps.setString(10, correccion.getNoCuenta());
            }else{
                ps.setNull(10, java.sql.Types.VARCHAR);
            }
            if(correccion.getNoDocumento() != null){
                ps.setString(11, correccion.getNoDocumento());
            }else{
                ps.setNull(11, java.sql.Types.VARCHAR);
            }
            if(correccion.getFechaDocu() != null){
                ps.setString(12, correccion.getFechaDocu());
            }else{
                ps.setNull(12, java.sql.Types.VARCHAR);
            }
            if(dp.getIdDocumentoPago() != null){
                ps.setString(13, dp.getIdDocumentoPago());
            }else{
                ps.setNull(13, java.sql.Types.VARCHAR);
            }
            
            
            //ps.setString(6, correccion.getCodSucursal());
            //ps.setString(7, correccion.getTipoPago());
            //ps.setString(8, correccion.getNoAprobacion());
            //ps.setString(9, correccion.getNoConsignacion());
            //ps.setString(10, correccion.getNoCuenta());
            //ps.setString(11, correccion.getNoDocumento());
            //ps.setString(12, dp.getIdDocumentoPago());
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while (rs.next()) {
                
            }
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (SQLException ex) {
            Log.getInstance().error(JDOPagosDAO.class, ex.getMessage(), ex);
        }
        return rta;
    }
      public boolean  actualizarEstadoDocumento(DocumentoPago Documento) throws DAOException{
        boolean resp = true;
        PersistenceManager pm = AdministradorPM.getPM();
        DocumentoPagoEnhanced docu = null;
        DocumentoPagoEnhanced finals = null;
        try {  
            DocumentoPagoPk uid = new DocumentoPagoPk();
            uid.idDocumentoPago = Documento.getIdDocumentoPago();
            docu = this.getDocumentosPagoByIdEnhanced(uid);
            finals = this.Actualizardocumento(docu,Documento ,pm);
        } catch (DAOException e) {
            resp = false;
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            resp = false;
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return resp;
    }
    public boolean  guardardocumentopagoantesdecorreccion(String idDocumento,String iduser) throws DAOException{
        boolean resp = true;
        PersistenceManager pm = AdministradorPM.getPM();
        DocumentoPagoEnhanced docu = null;
        DocumentoPagoCorreccionEnhanced corr = null;
        try {  
            DocumentoPagoPk uid = new DocumentoPagoPk();
            uid.idDocumentoPago = idDocumento;
            docu = this.getDocumentosPagoByIdEnhanced(uid);
            pm.currentTransaction().begin();
            corr = this.crearDocumentoPagoCorreccion(docu, pm,iduser);
            pm.currentTransaction().commit();    
        } catch (DAOException e) {
            resp = false;
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            resp = false;
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return resp;
    }
    /**
     * Obtiene un <code>DocumentoPago</code> dado su identificador,
     * <p>
     * método utilizado para transacciones
     *
     * @param solicitud identificador de la solicitud del documento de pago
     * @param pm <code>PersistenceManager</code> de la transacción
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public List getDocumentoPagoBySolicitud(String solicitud) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();

        try {
            List listaDocumentos = this.getDocumentoPagoBySolicitud(solicitud, pm);
            for (int i = 0; i < listaDocumentos.size(); i++) {
                dpr = (DocumentoPagoEnhanced) listaDocumentos.get(i);
                this.makeTransientDocumentoPago(dpr, pm);
                rta.add((DocumentoPago) dpr.toTransferObject());
            }
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocPagoConsignacion</code> dado el identificador de
     * <code>DocumentoPago</code>,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador <code>DocumentoPago</code> del documento de pago
     * @return <code>DocPagoConsignacion</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocPagoConsignacion getDocPagoConsignacion(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoConsignacion(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoConsignacion) dpr.toTransferObject();
        } else {
            return null;
        }

    }

    /**
     * Obtiene un <code>DocPagoCheque</code> dado el identificador de
     * <code>DocumentoPago</code>,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador <code>DocumentoPago</code> del documento de pago
     * @return <code>DocPagoCheque</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocPagoCheque getDocPagoCheque(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoCheque(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoCheque) dpr.toTransferObject();
        } else {
            return null;
        }
    }

    /**
     * Método creado por OSBERT LINERO - Iridium Telecomunicaciones e
     * informática Ltda. Cambio para agregar nota débito requerimiento 108 -
     * Incidencia Mantis 02360.
     *
     * Obtiene un <code>DocPagoNotaDebito</code> dado el identificador de
     * <code>DocumentoPago</code>,
     * <p>
     * método utilizado para transacciones
     *
     * @param docPago <code>DocumentoPago</code> del documento de pago
     * @return <code>DocPagoNotaDebito</code> con sus atributos
     * @throws gov.sir.hermod.dao.DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocPagoNotaDebito getDocPagoNotaDebito(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoNotaDebito(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoNotaDebito) dpr.toTransferObject();
        } else {
            return null;
        }
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoTarjetaCredito getDocPagoTarjetaCredito(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoTarjetaCreditoEnhanced(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoTarjetaCredito) dpr.toTransferObject();
        } else {
            return null;
        }
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoTarjetaDebito getDocPagoTarjetaDebito(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoTarjetaDebitoEnhanced(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoTarjetaDebito) dpr.toTransferObject();
        } else {
            return null;
        }
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoElectronicoPSE getDocPagoPse(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoPseEnhanced(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoElectronicoPSE) dpr.toTransferObject();
        } else {
            return null;
        }
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoConvenio getDocPagoConvenio(DocumentoPago docPago) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoConvenioEnhanced(docPago, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            return (DocPagoConvenio) dpr.toTransferObject();
        } else {
            return null;
        }
    }

    /**
     * Obtiene un <code>DocPagoConsignacion</code> dado el identificador de la
     * consignacion,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador de la consignacion
     * @return <code>DocPagoConsignacion</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocPagoConsignacion getDocPagoConsignacion(String noConsignacion) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoConsignacion(noConsignacion, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocPagoConsignacion) dpr.toTransferObject();
    }

    /**
     * Obtiene un <code>DocPagoCheque</code> dado el identificador del cheque,
     * <p>
     * método utilizado para transacciones
     *
     * @param identificador del cheque
     * @return <code>DocPagoCheque</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocPagoCheque getDocPagoCheque(String noCheque) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoCheque(noCheque, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocPagoCheque) dpr.toTransferObject();
    }

    /**
     * Método creado por OSBERT LINERO - Iridium Telecomunicaciones e
     * informática Ltda. Cambio para agregar nota débito requerimiento 108 -
     * Incidencia Mantis 02360.
     *
     * Obtiene un <code>DocPagoNotaDebito</code> dado el número de la nota
     * débito,
     * <p>
     * método utilizado para transacciones
     *
     * @param noNotaDebito número de la nota débito
     * @return <code>DocPagoNotaDebito</code> con sus atributos
     * @throws gov.sir.hermod.dao.DAOException
     */
    public DocPagoNotaDebito getDocPagoNotaDebito(String noNotaDebito) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoNotaDebito(noNotaDebito, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocPagoNotaDebito) dpr.toTransferObject();
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoTarjetaCredito getDocPagoTarjetaCredito(String noTarjetaCredito) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoTarjetaCreditoEnhanced(noTarjetaCredito, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocPagoTarjetaCredito) dpr.toTransferObject();
    }
     /**
     * Obtiene un <code>DocumentoPago</code> de la base de datos
     *
     * @param idDocPago Codigo de documentoPago
     * @return <code>DocumentoPago</code>  Objeto con todos sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public DocumentoPago getDocumentobyIdDocPago(String idDocPago) throws DAOException{
        DocumentoPago rta = new DocumentoPago();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM SIR_OP_DOCUMENTO_PAGO WHERE ID_DOCUMENTO_PAGO = ?";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocPago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while (rs.next()) {
                if(rs.getString("ESTADO_CORRECCION") != null && !rs.getString("ESTADO_CORRECCION").equals("")){
                        rta.setEstadocorreccion(Integer.valueOf(rs.getString("ESTADO_CORRECCION")));
                }else{
                    rta.setEstadocorreccion(0);
                }
                java.sql.Date date = rs.getDate("DCPG_FECHA_CREACION");
                Date datefinal = new Date(date.getTime());
                rta.setFechaCreacion(datefinal);
                rta.setIdDocumentoPago(idDocPago);
                rta.setAprobacion_ant(rs.getString("DCPG_ANT_NO_APROBACION"));
                rta.setTarjeta_ant(rs.getString("DCPG_ANT_NO_TARJETA"));
                rta.setConsignacion_ant(rs.getString("DCPG_ANT_NO_CONSIGNACION"));
                rta.setTipoPago(""+rs.getInt("ID_TIPO_DOC_PAGO"));
            }

        }catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            rta = null;
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            throw e;
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            jdoPM.close();
        }
        
        
        return rta;
    }
    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoTarjetaDebito getDocPagoTarjetaDebito(String noTarjetaDebito) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoTarjetaDebitoEnhanced(noTarjetaDebito, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocPagoTarjetaDebito) dpr.toTransferObject();
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoElectronicoPSE getDocPagoPse(String numeroAprobacion) throws DAOException {

        DocumentoPagoEnhanced dpr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            dpr = this.getDocPagoPseEnhanced(numeroAprobacion, pm);
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (DocPagoElectronicoPSE) dpr.toTransferObject();
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    public DocPagoConvenio getDocPagoConvenio(String numeroAprobacion) throws DAOException {

//        DocumentoPagoEnhanced dpr = null;
//        PersistenceManager pm = AdministradorPM.getPM();
//
//        try {
//            dpr = this.getDocPagoPseEnhanced(numeroAprobacion, pm);
//            this.makeTransientDocumentoPago(dpr, pm);
//        } catch (DAOException e) {
//            if (pm.currentTransaction().isActive()) {
//                pm.currentTransaction().rollback();
//            }
//            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
//            throw new DAOException(e.getMessage(), e);
//        } catch (Throwable e) {
//            if (pm.currentTransaction().isActive()) {
//                pm.currentTransaction().rollback();
//            }
//            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
//            throw new DAOException(e.getMessage(), e);
//        } finally {
//            pm.close();
//        }
//        return (DocPagoPse) dpr.toTransferObject();
        return null;
    }
    
    public String getIdBanco(String banco) throws DAOException{
        PersistenceManager pm = AdministradorPM.getPM();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String idBanco = "";

        VersantPersistenceManager jdoPM = null;

        String consulta = " SELECT CB_ID_BANCO FROM SIR_OP_CUENTAS_BANCARIAS  " +
                          " WHERE CB_ID = '"+ banco +"'";
        try {

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            rs = ps.executeQuery();
            
            
            
            if(rs.next()){
                idBanco = rs.getString(1);
            }

            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }

                if (jdoPM != null) {
                    jdoPM.close();
                }

            } catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return idBanco;
    }
    
    
    /*
    * Metodo que retorna el campo donde es almacenada la forma de pago
    */
    public String getCanalPago (String canal) throws DAOException{
        PersistenceManager pm = AdministradorPM.getPM();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String campoCaptura = null;

        VersantPersistenceManager jdoPM = null;

        String consulta = " SELECT FNC_GET_CANAL('"+canal+"') FROM DUAL";
        try {

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            rs = ps.executeQuery();

            if(rs.next()){
                campoCaptura = rs.getString(1);
            }

            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }

                if (jdoPM != null) {
                    jdoPM.close();
                }

            } catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return campoCaptura;
    }
    public boolean restringirAddPago(String idBanco, String canal, String numero) throws DAOException{
        PersistenceManager pm = AdministradorPM.getPM();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean stopPayment = false;

        VersantPersistenceManager jdoPM = null;

        String consulta = " SELECT "+canal+", ID_BANCO FROM SIR_OP_DOCUMENTO_PAGO\n" +
                          " WHERE ID_BANCO = '"+idBanco+"' AND "+canal+" = '"+numero+"'";
        try {

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            rs = ps.executeQuery();

            if(rs.next()){
                stopPayment = true;
            }

            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }

                if (jdoPM != null) {
                    jdoPM.close();
                }

            } catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return stopPayment;
    }
    
    public boolean restringirAddPagoByFP(String formaPago, String canal, String numero) throws DAOException{
        PersistenceManager pm = AdministradorPM.getPM();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean stopPayment = false;

        VersantPersistenceManager jdoPM = null;
        
        String consulta = "  SELECT "+canal+" FROM SIR_OP_DOCUMENTO_PAGO D" +
                        "    INNER JOIN SIR_OP_TIPO_DOC_PAGO T " +
                        "    ON  D.DCPG_TIPO_PAGO = T.TPDP_NOMBRE " +
                        "    WHERE DCPG_NO_APROBACION = '"+numero+"' " +
                        "    AND T.ID_TIPO_DOC_PAGO = '"+formaPago+"' " +
                        "";
        try {

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            rs = ps.executeQuery();

            if(rs.next()){
                stopPayment = true;
            }

            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }

                if (jdoPM != null) {
                    jdoPM.close();
                }

            } catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return stopPayment;
    }
    /**
     * Retorna un documento de pago registrado en la base de datos. El documento
     * de pago debe ser un cheque, nota débito o una consignación. Si no
     * encuentra un documento de pago correspondiente retorna null
     *
     * @param doc
     * @return
     * @throws DAOException
     */
    public DocumentoPago getDocumentosPagoExistente(DocumentoPago doc) throws DAOException {
        DocumentoPagoEnhanced dpr = null;
        DocumentoPago rta = null;
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            
            // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
            // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
            if (doc instanceof DocPagoCheque) {
                dpr = this.getDocumentosPagoByCheque((DocPagoChequeEnhanced) DocPagoChequeEnhanced.enhance(doc), pm);
            } else if (doc instanceof DocPagoNotaDebito) {
                dpr = this.getDocumentosPagoByNotaDebito((DocPagoNotaDebitoEnhanced) DocPagoNotaDebitoEnhanced.enhance(doc), pm);
            } else if (doc instanceof DocPagoConsignacion) {
                dpr = this.getDocumentosPagoByConsignacion((DocPagoConsignacionEnhanced) DocPagoConsignacionEnhanced.enhance(doc), pm);
            } /**
             * @autor HGOMEZ
             * @mantis 12422
             * @Requerimiento 049_453
             * @descripcion El documento de pago debe ser una tarjeta credito,
             * tarjeta debito o un PSE.
             */
            else if (doc instanceof DocPagoTarjetaCredito) {
                dpr = this.getDocumentosPagoByTarjetaCredito((DocPagoTarjetaCreditoEnhanced) DocPagoTarjetaCreditoEnhanced.enhance(doc), pm);
            } else if (doc instanceof DocPagoTarjetaDebito) {
                dpr = this.getDocumentosPagoByTarjetaDebito((DocPagoTarjetaDebitoEnhanced) DocPagoTarjetaDebitoEnhanced.enhance(doc), pm);
            } else if (doc instanceof DocPagoElectronicoPSE) {
                dpr = this.getDocumentosByPse((DocPagoElectronicoPSEEnhanced) DocPagoElectronicoPSEEnhanced.enhance(doc), pm);
            } else if (doc instanceof DocPagoConvenio) {
                dpr = this.getDocumentosPagoByConvenio((DocPagoConvenioEnhanced) DocPagoConvenioEnhanced.enhance(doc), pm);
            } else if (doc instanceof DocPagoGeneral) {
                dpr = this.getDocumentosPagoGeneralByConsignacion((DocPagoGeneralEnhanced) DocPagoGeneralEnhanced.enhance(doc), pm);
            }
            this.makeTransientDocumentoPago(dpr, pm);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (dpr != null) {
            rta = (DocumentoPago) dpr.toTransferObject();
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocumentoPago</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el <code>Pago</code>
     * con el identificador dado.
     *
     * @param dpID identificador del documento de pago
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected DocumentoPagoEnhanced getDocumentosPagoById(
            DocumentoPagoEnhancedPk dpID,
            PersistenceManager pm)
            throws DAOException {

        DocumentoPagoEnhanced rta = null;

        if (dpID.idDocumentoPago != null) {
            try {
                rta = (DocumentoPagoEnhanced) pm.getObjectById(dpID, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }
       protected DocumentoPagoCorreccionEnhanced getDocumentoPagoAntesdeCorreccionbyId(
            DocumentoPagoCorreccionEnhancedPk dpID,
            PersistenceManager pm)
            throws DAOException {

        DocumentoPagoCorreccionEnhanced rta = null;

        if (dpID.idDocumentoPagoCorreccion != null) {
            try {
                rta = (DocumentoPagoCorreccionEnhanced) pm.getObjectById(dpID, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocumentoPago</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el <code>Pago</code>
     * con el identificador dado.
     *
     * @param solicitud identificador de la solicitud del documento de pago
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocumentoPago</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected List getDocumentoPagoBySolicitud(
            String solicitud,
            PersistenceManager pm)
            throws DAOException {

        AplicacionPagoEnhanced ap = null;
        DocumentoPagoEnhanced docPago = null;
        List listDocumentos = new ArrayList();

        try {
            Query query = pm.newQuery(AplicacionPagoEnhanced.class);
            query.declareParameters("String solicitud");
            query.setFilter("idSolicitud == solicitud");
            Collection col = (Collection) query.execute(solicitud);

            if (col.size() == 0) {
                listDocumentos = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    ap = (AplicacionPagoEnhanced) iter.next();
                    if (ap != null) {
                        if (ap.getIdSolicitud() != null) {
                            DocumentoPagoPk dpID = new DocumentoPagoPk();
                            dpID.idDocumentoPago = ap.getIdDocumentoPago();
                                docPago = (DocumentoPagoEnhanced) pm.getObjectById(new DocumentoPagoEnhancedPk(dpID), true);
                            listDocumentos.add(docPago);
                        }
                    }
                }
                query.closeAll();
            }
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            listDocumentos = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return listDocumentos;
    }

    /**
     * Obtiene un <code>DocPagoConsignacion</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el
     * <code>DocPagoConsignacion</code> con el identificador dado.
     *
     * @param <code>DocumentoPago</code> identificador del documento de pago
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocPagoConsignacion</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected DocPagoConsignacionEnhanced getDocPagoConsignacion(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoConsignacionEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoConsignacionEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoConsignacionEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocPagoCheque</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el
     * <code>DocPagoCheque</code> con el identificador dado.
     *
     * @param <code>DocumentoPago</code> identificador del documento de pago
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocPagoCheque</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected DocPagoChequeEnhanced getDocPagoCheque(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoChequeEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoChequeEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoChequeEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Método creado por OSBERT LINERO - Iridium Telecomunicaciones e
     * informática Ltda. Cambio para agregar nota débito requerimiento 108 -
     * Incidencia Mantis 02360.
     *
     * Obtiene un <code>DocPagoNotaDebito</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el
     * <code>DocPagoNotaDebito</code> con el identificador dado.
     *
     * @param <code>DocumentoPago</code> identificador del documento de pago
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocPagoNotaDebito</code> con sus atributos
     * @throws gov.sir.hermod.dao.DAOException
     */
    protected DocPagoNotaDebitoEnhanced getDocPagoNotaDebito(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoNotaDebitoEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoNotaDebitoEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoNotaDebitoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoTarjetaCreditoEnhanced getDocPagoTarjetaCreditoEnhanced(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoTarjetaCreditoEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoTarjetaCreditoEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoTarjetaCreditoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoTarjetaDebitoEnhanced getDocPagoTarjetaDebitoEnhanced(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoTarjetaDebitoEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoTarjetaDebitoEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoTarjetaDebitoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoElectronicoPSEEnhanced getDocPagoPseEnhanced(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoElectronicoPSEEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoElectronicoPSEEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoElectronicoPSEEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoConvenioEnhanced getDocPagoConvenioEnhanced(
            DocumentoPago dp,
            PersistenceManager pm)
            throws DAOException {

        DocPagoConvenioEnhanced rta = null;

        if (dp.getIdDocumentoPago() != null) {
            try {
                Query query = pm.newQuery(DocPagoConvenioEnhanced.class);
                String idDocPago = dp.getIdDocumentoPago();
                query.declareParameters("String idDocPago");
                query.setFilter("idDocumentoPago == idDocPago");
                Collection col = (Collection) query.execute(idDocPago);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoConvenioEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocPagoConsignacion</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el
     * <code>DocPagoConsignacion</code> con el identificador dado.
     *
     * @param <code>DocumentoPago</code> identificador del documento de pago
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocPagoConsignacion</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected DocPagoConsignacionEnhanced getDocPagoConsignacion(
            String noCon,
            PersistenceManager pm)
            throws DAOException {

        DocPagoConsignacionEnhanced rta = null;

        if (noCon != null) {
            try {
                Query query = pm.newQuery(DocPagoConsignacionEnhanced.class);
                query.declareParameters("String noCon");
                query.setFilter("noConsignacion == noCon");
                Collection col = (Collection) query.execute(noCon);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoConsignacionEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene un <code>DocPagoCheque</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el
     * <code>DocPagoCheque</code> con el identificador dado.
     *
     * @param identificador del cheque
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocPagoCheque</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    protected DocPagoChequeEnhanced getDocPagoCheque(
            String noCheq,
            PersistenceManager pm)
            throws DAOException {

        DocPagoChequeEnhanced rta = null;

        if (noCheq != null) {
            try {
                Query query = pm.newQuery(DocPagoChequeEnhanced.class);
                query.declareParameters("String noCheq");
                query.setFilter("noCheque == noCheq");
                Collection col = (Collection) query.execute(noCheq);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoChequeEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Método creado por OSBERT LINERO - Iridium Telecomunicaciones e
     * informática Ltda. Cambio para agregar nota débito requerimiento 108 -
     * Incidencia Mantis 02360.
     *
     * Obtiene un <code>DocPagoNotaDebito</code> dado su identificador.
     * <p>
     * Método utilizado para transacciones.
     * <p>
     * El método retorna null, si no es posible encontrar el
     * <code>DocPagoNotaDebito</code> con el identificador dado.
     *
     * @param noNotaDebito - número de nota débito.
     * @param pm PersistenceManager de la transaccion
     * @return <code>DocPagoNotaDebito</code> con sus atributos
     * @throws gov.sir.hermod.dao.DAOException
     */
    protected DocPagoNotaDebitoEnhanced getDocPagoNotaDebito(
            String noNotaDebito,
            PersistenceManager pm)
            throws DAOException {

        DocPagoNotaDebitoEnhanced rta = null;

        if (noNotaDebito != null) {
            try {
                Query query = pm.newQuery(DocPagoNotaDebitoEnhanced.class);
                query.declareParameters("String noNota");
                query.setFilter("noNotaDebito == noNota");
                Collection col = (Collection) query.execute(noNotaDebito);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoNotaDebitoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoTarjetaCreditoEnhanced getDocPagoTarjetaCreditoEnhanced(
            String noTarjetaCredito,
            PersistenceManager pm)
            throws DAOException {

        DocPagoTarjetaCreditoEnhanced rta = null;

        if (noTarjetaCredito != null) {
            try {
                Query query = pm.newQuery(DocPagoTarjetaCreditoEnhanced.class);
                query.declareParameters("String noTarjeta");
                query.setFilter("noTarjetaCredito == noTarjeta");
                Collection col = (Collection) query.execute(noTarjetaCredito);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoTarjetaCreditoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoTarjetaDebitoEnhanced getDocPagoTarjetaDebitoEnhanced(
            String noTarjetaDebito,
            PersistenceManager pm)
            throws DAOException {

        DocPagoTarjetaDebitoEnhanced rta = null;

        if (noTarjetaDebito != null) {
            try {
                Query query = pm.newQuery(DocPagoTarjetaDebitoEnhanced.class);
                query.declareParameters("String noTarjeta");
                query.setFilter("noTarjetaDebito == noTarjeta");
                Collection col = (Collection) query.execute(noTarjetaDebito);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoTarjetaDebitoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoElectronicoPSEEnhanced getDocPagoPseEnhanced(
            String noAprobacionPse,
            PersistenceManager pm)
            throws DAOException {

        DocPagoElectronicoPSEEnhanced rta = null;

        if (noAprobacionPse != null) {
            try {
                Query query = pm.newQuery(DocPagoElectronicoPSEEnhanced.class);
                query.declareParameters("String noTarjeta");
                query.setFilter("noAprobacionPse == numeroAprobacion");
                Collection col = (Collection) query.execute(noAprobacionPse);

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoElectronicoPSEEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Método nuevo.
     */
    protected DocPagoConvenioEnhanced getDocPagoConvenioEnhanced(
            String noAprobacionPse,
            PersistenceManager pm)
            throws DAOException {

        DocPagoConvenioEnhanced rta = null;

//        if (noAprobacionPse != null) {
//            try {
//                Query query = pm.newQuery(DocPagoPseEnhanced.class);
//                query.declareParameters("String noTarjeta");
//                query.setFilter("noAprobacionPse == numeroAprobacion");
//                Collection col = (Collection) query.execute(noAprobacionPse);
//
//                if (col.size() == 0) {
//                    rta = null;
//                } else {
//                    for (Iterator iter = col.iterator(); iter.hasNext();) {
//                        rta = (DocPagoPseEnhanced) iter.next();
//                    }
//                    query.closeAll();
//                }
//            } catch (JDOObjectNotFoundException e) {
//                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
//                rta = null;
//            } catch (JDOException e) {
//                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
//                throw new DAOException(e.getMessage(), e);
//            }
//        }
        return rta;
    }

    /**
     * Obtiene una lista de los objetos <code>DocumentoPago</code> existentens
     * en el sistema.
     *
     * @return una lista de objetos <code>DocumentoPago</code> existentes en el
     * sistema.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.DocumentoPago
     */
    public List getTiposPago() throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            Query q = pm.newQuery(TipoPagoEnhanced.class);
            q.setOrdering("idTipoDocPago ascending");
            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();

            while (it.hasNext()) {
                TipoPagoEnhanced obj = (TipoPagoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }
            pm.currentTransaction().commit();
            q.close(results);

        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Obtiene un <code>List</code> de los tipos de pago disponibles para un
     * círculo específico.
     *
     * @return <code>List</code> con los DocumentoPago disponibles en el sistema
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    public List getCirculoTiposPagoOld(CirculoPk cirID) throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            String idCirc = cirID.idCirculo;
            Query query = pm.newQuery(CirculoTipoPagoEnhanced.class);
            query.declareParameters("String idCirc");
            query.setFilter("idCirculo == idCirc ");

            Collection col = (Collection) query.execute(idCirc);
            for (Iterator iter = col.iterator(); iter.hasNext();) {
                CirculoTipoPagoEnhanced obj = (CirculoTipoPagoEnhanced) iter.next();
                pm.makeTransient(obj.getTipoPago());
                pm.makeTransient(obj.getCirculo());
                pm.makeTransient(obj.getCanalesRecaudo());
                pm.makeTransient(obj.getCuentasBancarias().getBanco());
                pm.makeTransient(obj);
                lista.add(obj);
            }

            pm.currentTransaction().commit();
            query.close(col);

        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Obtiene el numero de recibo de un pago dado su identificador //Caso de
     * certificados asociados
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>String</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public String getNumReciboPagoByID(PagoPk pID) throws DAOException {

        PagoEnhanced rta = null;
        //PagoEnhanced pr = null;
        PersistenceManager pm = AdministradorPM.getPM();
        if (pID.idLiquidacion != null && pID.idSolicitud != null) {
            try {
                rta = this.getPagoByID(new PagoEnhancedPk(pID), pm);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta.getNumRecibo();
    }

    /**
     * Obtiene un objeto <code>Pago</code> dado su identificador.
     *
     * @param pID identificador del <code>Pago</code>
     * @return <code>Pago</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago getPagoByID(PagoPk pID) throws DAOException {

        PagoEnhanced pr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pr = this.getPagoByID(new PagoEnhancedPk(pID), pm);

            List aps = pr.getAplicacionPagos();
            Iterator it = aps.iterator();

            while (it.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) it.next();
                this.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                pm.makeTransient(ap);
            }

            pm.makeTransient(pr.getLiquidacion());
            pm.makeTransient(pr.getLiquidacion().getPago());
            pm.makeTransient(pr.getLiquidacion().getSolicitud());

            pm.makeTransient(pr);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return (Pago) pr.toTransferObject();
    }

    /**
     * Obtiene un pago dado su identificador.
     * <p>
     * Método utilizado para transacciones
     * <p>
     * El método retorna null si no se encuentra el <code>Pago</code> con el
     * identificador dado.
     *
     * @param pID identificador del pago
     * @param pm <code>PersistenceManager</code> de la transacción
     * @return <code>Pago</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     */
    protected PagoEnhanced getPagoByID(PagoEnhancedPk pID, PersistenceManager pm) throws DAOException {

        PagoEnhanced rta = null;

        if (pID.idLiquidacion != null && pID.idSolicitud != null) {
            System.out.println("getPagoByID 19 febrero");
            try {
                System.out.println("getPagoByID 19 febrero try catch");
                rta = (PagoEnhanced) pm.getObjectById(pID, true);
                System.out.println("getPagoByID 19 febrero try catch despues");
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene el <code>CirculoProceso</code> asociado con un <code>Pago</code>.
     * <p>
     * Método utilizado para transacciones.
     *
     * @param pago <code>PagoEnhanced</code> del cual se va a obtener el
     * <code>CirculoProcesoEnhanced</code>
     * @param pm PersistenceManager de la transaccion
     * @return <code>CirculoProceso</code> con sus atributos
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     * @see gov.sir.core.negocio.modelo.CirculoProceso
     * @see gov.sir.core.negocio.modelo.CirculoProcesoEnhanced
     */
	protected CirculoProcesoEnhanced getCirculoProcesoByPago(PagoEnhanced pago, PersistenceManager pm)
		throws DAOException {

		//Se hace el cambio de tipo de bloqueo pesimista para
		//que se bloquee la tabla la cual  nos
		//provee el secuencial
		VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
                CirculoProcesoEnhanced circuloprocesoenhanced = null;
                // victor comento aca
                Collection col = new ArrayList<CirculoProcesoEnhanced>();
                try{
                        pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
                        pm = pm2;

                        //Date fecha = new Date();
                        Calendar calendario = Calendar.getInstance();
                        String year = String.valueOf(calendario.get(Calendar.YEAR));

                        String idSolicitud = pago.getIdSolicitud();

                        Query query = pm.newQuery(CirculoProcesoEnhanced.class);
                        query.declareVariables("SolicitudEnhanced sol");

                        query.declareParameters("String idSol, String year");

                        query.setFilter(
                                "this.anio==year && (sol.circulo == circulo && sol.proceso == proceso && \n"
                                        + "sol.idSolicitud==idSol)");

                        col = (Collection) query.execute(idSolicitud, year);
//                        pm.currentTransaction().commit()
                                                
                        //pm.currentTransaction().commit();                                   
                        pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);   
                        //Volvemos a setear el tipo de bloqueo pesimista
                        //para que no nos bloquee los siquientes registros
                        //consultados		
		}catch (Exception e) {
                    
                        pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE); 
			if (pm.currentTransaction().isActive()) {   
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOPagosDAO.class,e.getMessage());
			//throw new DAOException(e.getMessage(), e);
                } 		
                
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                        circuloprocesoenhanced = (CirculoProcesoEnhanced) iter.next();
                }
		
		if (circuloprocesoenhanced == null) {
			throw new DAOException(
				"No se encontró un circulo: "
					+ pago.getLiquidacion().getSolicitud().getCirculo().getIdCirculo()
					+ ", proceso: "
					+ pago.getLiquidacion().getSolicitud().getProceso().getIdProceso()
					+ " para el pago dado: "
					+ pago.getNumRecibo());
		}							  
		return circuloprocesoenhanced;
	}

    protected CirculoProcesoEnhanced getCirculoProcesoById(String circulo, long proceso, PersistenceManager pm)
            throws DAOException {

        CirculoProcesoEnhanced circuloprocesoenhanced = null;

        CirculoProcesoEnhancedPk circuloId = new CirculoProcesoEnhancedPk();
        circuloId.idCirculo = circulo;
        circuloId.idProceso = proceso;

        //Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String year = String.valueOf(calendario.get(Calendar.YEAR));
        circuloId.anio = year;

        circuloprocesoenhanced = getCirculoProcesoByID(circuloId, pm);
        if (circuloprocesoenhanced == null) {
            throw new DAOException(
                    "No se encontró un circulo: "
                    + circulo
                    + ", proceso: "
                    + proceso);
        }

        return circuloprocesoenhanced;
    }

    /**
     * Adiciona un <code>CirculoTipoPago<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, un <code>CirculoTipoPago</code> con el identificador pasado dentro
     * del parámetro.
     *
     * @param datos objeto <code>CirculoTipoPago</code> con sus atributos
     * @return identificador del <code>CirculoTipoPago</code> generado.
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    public CirculoTipoPagoPk addCirculoTipoPago(CirculoTipoPago dato) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoTipoPagoEnhanced circuloTipoPago = CirculoTipoPagoEnhanced.enhance(dato);

        CirculoTipoPagoEnhancedPk rta = null;
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            long circuloTipoPagoId = solDAO.getSecuencial("SIR_NE_CIRCULO_TIPO_PAGO", null);

            //Validación del identificador del Banco.
            CirculoTipoPagoEnhancedPk circuloTipoPagoEnhancedPk = new CirculoTipoPagoEnhancedPk();
            circuloTipoPagoEnhancedPk.idCirculoTipoPago = circuloTipoPagoId + "";

            //circuloTipoPagoId.idTipoDocPago = circuloTipoPago.getIdTipoDocPago();
            CirculoTipoPagoEnhanced valid = getCirculoTipoPagoByID(circuloTipoPagoEnhancedPk, pm);

            //validar si tiene círculo
            /*if (dato.getIdCirculo() == null) {
                throw new DAOException("Debe Proporcionar un círculo ");
            }*/
            //Traer Circulo Persistente
            CirculoEnhancedPk idCircEnh = new CirculoEnhancedPk();
            idCircEnh.idCirculo = dato.getIdCirculo();
            CirculoEnhanced circPers = (CirculoEnhanced) pm.getObjectById(idCircEnh, true);
            if (circPers == null) {
                throw new DAOException("No existe el Circulo con el id " + dato.getIdCirculo());
            }

            //validar si tiene tipo de pago
            /*if (dato.getTipoPago() == null) {
                throw new DAOException("Debe Proporcionar un Tipo de Pago ");
            }*/
            //Traer TipoPago Persistente
            TipoPagoEnhancedPk idTipoPagoEnh = new TipoPagoEnhancedPk();
            idTipoPagoEnh.idTipoDocPago = dato.getIdTipoDocPago();
            TipoPagoEnhanced tipoPagoPers = (TipoPagoEnhanced) pm.getObjectById(idTipoPagoEnh, true);
            if (tipoPagoPers == null) {
                throw new DAOException("No existe el Tipo de Pago con el id " + dato.getIdTipoDocPago());
            }

            CanalesRecaudoEnhancedPk idCanalesRecaudoEnh = new CanalesRecaudoEnhancedPk();
            idCanalesRecaudoEnh.idCanal = Integer.parseInt(dato.getIdCanalRecaudo());
            CanalesRecaudoEnhanced canalesRecaudoPers = (CanalesRecaudoEnhanced) pm.getObjectById(idCanalesRecaudoEnh, true);
            if (canalesRecaudoPers == null) {
                throw new DAOException("No existe el Canal de Recaudo con el id " + dato.getIdCanalRecaudo());
            }

            CuentasBancariasEnhanced cuentasBancariasPers = new CuentasBancariasEnhanced();

            if (dato.getIdCuentaBancaria() != null) {
                CuentasBancariasEnhancedPk idCuentaBancariaEnh = new CuentasBancariasEnhancedPk();
                idCuentaBancariaEnh.id = Integer.parseInt(dato.getIdCuentaBancaria());
                cuentasBancariasPers = (CuentasBancariasEnhanced) pm.getObjectById(idCuentaBancariaEnh, true);
                if (cuentasBancariasPers == null) {
                    throw new DAOException("No existe una Cuenta Bancaria con el id " + dato.getIdCuentaBancaria());
                }
            } else {
                cuentasBancariasPers = null;
            }

            //Se lanza una excepción en el caso en el que ya exista en
            //la base de datos,
            if (valid != null) {
                throw new DAOException("Ya existe un Tipo de Pago de identificador: " + tipoPagoPers.getNombre() + ",  para el círculo : " + circPers.getNombre());
            }

            circuloTipoPago.setIdCirculoTipoPago(circuloTipoPagoId + "");
            circuloTipoPago.setCirculo(circPers);
            circuloTipoPago.setTipoPago(tipoPagoPers);
            circuloTipoPago.setCanalesRecaudo(canalesRecaudoPers);
            circuloTipoPago.setCuentasBancarias(cuentasBancariasPers);
            circPers.addTiposPago(circuloTipoPago);
            circuloTipoPago.setFechaCreacion(new Date());
            circuloTipoPago.setCanalSir(dato.isCanalSir());
            pm.makePersistent(circuloTipoPago);

            rta = (CirculoTipoPagoEnhancedPk) pm.getObjectId(circuloTipoPago);
            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return rta.getCirculoTipoPagoID();

    }

    /**
     * Elimina un <code>CirculoTipoPago<code> a la configuración del sistema.<p>
     * Se lanza una excepción en el caso en el que el
     * <code>CirculoTipoPago</code> a ser eliminado esté relacionado con otros
     * objetos del sistema (Violación de integridad referencial)
     *
     * @param datos objeto <code>CirculoTipoPago</code> con sus atributos
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    public void removeCirculoTipoPago(CirculoTipoPago dato) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoTipoPagoEnhanced circuloTipoPago = CirculoTipoPagoEnhanced.enhance(dato);

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            //	Traer Circulo Persistente
            CirculoEnhancedPk idCircEnh = new CirculoEnhancedPk();
            idCircEnh.idCirculo = dato.getIdCirculo();
            CirculoEnhanced circPers = (CirculoEnhanced) pm.getObjectById(idCircEnh, true);
            if (circPers == null) {
                throw new DAOException("No existe el Circulo con el id " + dato.getIdCirculo());
            }

            CirculoTipoPagoEnhancedPk cTipPerID = new CirculoTipoPagoEnhancedPk();
            cTipPerID.idCirculoTipoPago = dato.getIdCirculoTipoPago();
            //cTipPerID.idTipoDocPago = dato.getIdTipoDocPago();

            CirculoTipoPagoEnhanced circTipoPers = (CirculoTipoPagoEnhanced) pm.getObjectById(cTipPerID, true);
            if (circTipoPers == null) {
                throw new DAOException("No existe el Tipo de pago con el identificador: " + dato.getIdCirculoTipoPago() + " asociado al Circulo con el id " + dato.getIdCirculo());
            }
            circPers.removeTiposPago(circTipoPers);
            pm.deletePersistent(circTipoPers);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException("No se pudo eliminar el Tipo de Pago del Círculo por restricciones de integridad referencial", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException("No se pudo eliminar el Tipo de Pago del Círculo por restricciones de integridad referencial", e);
        } finally {
            pm.close();
        }
    }

    /**
     * Obtiene un <code>CirculoTipoPago</code> dado su identificador, método
     * utilizado para transacciones.
     * <p>
     * En caso de que el <code>CirculoTipoPago</code>con el identificador dado
     * no se encuentre en la Base de Datos se retorna <code>null</code>
     *
     * @param bID identificador del <code>Banco</code>
     * @param pm PersistenceManager de la transaccion
     * @return <code>Banco</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    protected CirculoTipoPagoEnhanced getCirculoTipoPagoByID(
            CirculoTipoPagoEnhancedPk cID,
            PersistenceManager pm)
            throws DAOException {
        CirculoTipoPagoEnhanced rta = null;

        if (cID.idCirculoTipoPago != null) {
            try {
                rta = (CirculoTipoPagoEnhanced) pm.getObjectById(cID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().debug(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    protected CirculoProcesoEnhanced getCirculoProcesoByID(
            CirculoProcesoEnhancedPk cID,
            PersistenceManager pm)
            throws DAOException {
        CirculoProcesoEnhanced rta = null;

        if (cID.idCirculo != null) {
            try {
                rta = (CirculoProcesoEnhanced) pm.getObjectById(cID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().debug(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Hace persistente la información de un Pago, y lo asocia a una solicitud.
     * <p>
     * Método desarrollado para cumplir con los requerimientos específicos del
     * proceso de registro de documentos.
     *
     * @param pago El <code>Pago</code> que se va a hacer persistente.
     * @param estacion El identificador de la estación desde la cual se va a
     * asociar el <code>Pago</code>
     * @return <code> Pago </code> que se ha hecho persistente.
     * @throws <code>DAOException </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public Pago registrarPago(Pago pago, String estacion) throws DAOException {
        SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
        PersistenceManager pm = AdministradorPM.getPM();
        SolicitudEnhanced solEnh = new SolicitudEnhanced();
        PagoEnhanced pagoEnh = new PagoEnhanced();

        System.out.println("REGISTRAR PAGO FEBRERO 19");

        //VALIDAR LA EXISTENCIA DE UNA LIQUIDACION ASOCIADA AL PAGO
        if (pago.getLiquidacion() == null) {
            throw new DAOException("El Pago recibido no tiene asociada una liquidación");
        }

        //VALIDAR LA EXISTENCIA DE LA SOLICITUD ASOCIADA CON LA LIQUIDACION
        if (pago.getLiquidacion().getSolicitud() == null) {
            throw new DAOException("El Pago recibido no tiene asociada una solicitud");
        }

        //VALIDAR LA EXISTENCIA DE UN IDENTIFICADOR PARA LA SOLICITUD
        if (pago.getLiquidacion().getSolicitud().getIdSolicitud() == null) {
            throw new DAOException("El Pago recibido no tiene asociada una solicitud con un identificador");
        }

        try {
            System.out.println("REGISTRAR PAGO try cath FEBRERO 19");

            //Inicio de la transacción.
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Obtener la Solicitud Peristente.
            solEnhId.idSolicitud = pago.getLiquidacion().getSolicitud().getIdSolicitud();

            SolicitudEnhanced solEnhanced = (SolicitudEnhanced) pm.getObjectById(solEnhId, true);

            System.out.println("REGISTRAR PAGO ANTES DE");
            pagoEnh = this.registrarPago(PagoEnhanced.enhance(pago), solEnhanced, estacion, pm);
            System.out.println("REGISTRAR PAGO DESPUES DE GERE " + pagoEnh);
            System.out.println("REGISTRAR PAGO DESPUES DE " + pagoEnh.getAplicacionPagos().size());

            pm.currentTransaction().commit();
            pm.makeTransient(pagoEnh);

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw (e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (Pago) pagoEnh.toTransferObject();

    }

    /**
     * Registra un <code>Pago</code> en el sistema.
     * <p>
     * Método utilizado para transacciones.
     *
     * @param p <code>PagoEnhanced</code> con sus atributos, exceptuando el
     * identificador.
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param solicitud <code>Soliitud </code> asociada con la creación del
     * <code>Pago</code>
     * @param estacion El identificador de la estación desde la cual se va a
     * asociar el <code>Pago</code>
     * @return solicitud <code>SolicitudEnhanced </code> asociada con la
     * creación del <code>Pago</code>
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     */
    protected PagoEnhanced registrarPago(PagoEnhanced p, SolicitudEnhanced solicitud, String estacion, PersistenceManager pm)
            throws DAOException {

        System.out.println("REGISTRAR PAGO ENHANCED");

        double valor = p.getLiquidacion().getValor();

        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        /**
         * @autor HGOMEZ
         * @mantis 12422
         * @Requerimiento 049_453
         * @descripcion Se comenta la siguiente línea dado que no se usa en este
         * método.
         */
        //JDOBancosDAO bancosDAO = new JDOBancosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        JDORecibosDAO recibosDAO = new JDORecibosDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        PagoEnhancedPk pId = null;
        CirculoProcesoEnhanced circuloProcesoEnhanced = null;
        PagoEnhanced pagoEnh = null;

        try {
            System.out.println("REGISTRAR PAGO ENHANCED TRY CATCH");
            String idLiquidacion = p.getIdLiquidacion();
            String idSolicitud = p.getIdSolicitud();
            UsuarioEnhanced usuario = p.getUsuario();
            LiquidacionEnhanced l = p.getLiquidacion();
            List aps = p.getAplicacionPagos();
            String concepto = null;
            String numRecibo = null;

            if (p.getConcepto() != null && !p.getConcepto().trim().equals("")) {
                concepto = new String(p.getConcepto());
                System.out.println("REGISTRAR PAGO ENHANCED CONCEPTO " + concepto);
                numRecibo = new String(p.getNumRecibo() != null ? p.getNumRecibo() : "");
                System.out.println("REGISTRAR PAGO ENHANCED RECIBO " + numRecibo);
            }

            PagoEnhancedPk idPago = new PagoEnhancedPk();
            idPago.idLiquidacion = idLiquidacion;
            idPago.idSolicitud = idSolicitud;
            PagoEnhanced pago;
            System.out.println("REGISTRAR PAGO ENHANCED ANTES getPagoByID");
            pago = this.getPagoByID(idPago, pm);
            System.out.println("REGISTRAR PAGO ENHANCED DESPUES getPagoByID");

            if (pago == null) {
                System.out.println("REGISTRAR PAGO ENHANCED PAGO NULL ");
                pago = p;
            }

            if (concepto != null) {
                pago.setConcepto(concepto);
            }
            if (numRecibo != null && !numRecibo.trim().equals("")) {
                pago.setNumRecibo(numRecibo);
            }

            if (p.getUsuario() == null) {
                throw new DAOException(
                        "El pago no esta asociada a un Usuario");
            }

            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + p.getUsuario());
            }
            pago.setUsuario(usuarior);
            System.out.println("REGISTRAR PAGO ENHANCED SET USUARIO ");

            //Se obtiene un circulo proceso enhanced.
            //Esta consulta efectua un select for update para garantizar sincronización.
            CirculoProcesoEnhanced cpe = this.getCirculoProcesoByPago(pago, pm);
            System.out.println("REGISTRAR PAGO ENHANCED DESPUES CirculoProcesoEnhanced ");

            if (l == null) {
                throw new DAOException("La liquidacion recibida para procesar el pago es nula");
            }
            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = l.getIdLiquidacion();
            lId.idSolicitud = l.getIdSolicitud();
            pago.setIdLiquidacion(l.getIdLiquidacion());
            pago.setIdSolicitud(l.getIdSolicitud());
            pago.setLiquidacion(l);
            pago.setFecha(new Date());
            /*if (estacion != null) {
				EstacionReciboEnhanced.ID estRecId = new EstacionReciboEnhanced.ID();
				estRecId.idEstacion = estacion;
				long recibo = recibosDAO.getNextNumeroRecibo(estRecId, pm);
				p.setNumRecibo(String.valueOf(recibo));
			}*/
            System.out.println("REGISTRAR PAGO ENHANCED ANTES LiquidacionEnhanced ");
            LiquidacionEnhanced lr = liquidacionesDAO.getLiquidacionByID(lId, pm);
            System.out.println("REGISTRAR PAGO ENHANCED DESPUES LiquidacionEnhanced ");
            if (lr == null) {
                throw new DAOException("No encontró la liquidación con el ID: " + lId.idLiquidacion);
            }
            pago.setLiquidacion(lr);

            String idCirculo = null;

            if (lr != null && lr.getCirculo() != null) {
                System.out.println("REGISTRAR PAGO ENHANCED LiquidacionEnhanced NO NULO ");
                pago.setCirculo(lr.getCirculo());
                idCirculo = lr.getCirculo();
            } else {
                System.out.println("REGISTRAR PAGO ENHANCED LiquidacionEnhanced NULO ");
                Log.getInstance().debug(JDOPagosDAO.class, "(PAGO)NO SE PUDO GUARDAR EL IDENTIFICADOR DEL CIRCULO " + (l != null ? l.getIdSolicitud() : ""));
            }

            List listaAsociados = new ArrayList();
            listaAsociados = solicitud.getSolicitudesHijas();
            int tamAsociadas = listaAsociados.size();

            if (aps == null) {
                throw new DAOException("La lista de aplicaciones pago es nula");
            }

            /**
             * SE HACE EL PAGO NORMALMENTE EN CASO DE QUE LA LIQUIDACION SEA
             * NEGATIVA (BUG QUE SE DEBE ARREGLAR) O QUE EL TURNO NO SEA DE
             * REGISTRO. EN CASO DE QUE LA LIQUIDACION SEA NEGATIVA, LUEGO DE
             * REGISTRAR LAS LIQUIDACIONES DE CERTIFICADOS ASOCIADOS SE ADICIONA
             * EL VALOR DE LOS CERTIFICADOS A LA LIQUIDACION DE REGISTRO
             */
            System.out.println("REGISTRAR PAGO ENHANCED ANTES BUG ");
            if (((solicitud.getProceso().getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO))) || lr.getValor() < 0) {

                System.out.println("REGISTRAR PAGO ENHANCED DESPUES BUG ");
                Iterator ita = aps.iterator();
                double valorCertificadosAsociados = 0;
                while (ita.hasNext()) {
                    AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) ita.next();
                    if (ap == null) {
                        throw new DAOException("La aplicacion de Pago es nula");
                    }

                    if (lr != null && lr.getCirculo() != null) {
                        ap.setCirculo(lr.getCirculo());
                    }

                    DocumentoPagoEnhanced dp = ap.getDocumentoPago();
                    if (dp == null) {
                        throw new DAOException("El documento de pago es nulo");
                    }
                    DocumentoPagoEnhancedPk dpId = new DocumentoPagoEnhancedPk();
                    if (dp.getIdDocumentoPago() != null) {
                        dpId.idDocumentoPago = dp.getIdDocumentoPago();
                        DocumentoPagoEnhanced dpr = this.getDocumentosPagoById(dpId, pm);
                        dpr.setSaldoDocumento(dpr.getSaldoDocumento() - ap.getValorAplicado());
                        ap.setDocumentoPago(dpr);
                        ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                        ap.setPago(pago);
                        if (dpr == null) {
                            if (dp != null) {
                                dp.setCirculo(p.getCirculo());
                            }
                            ap.setDocumentoPago(dp);
                        } else {
                            dpr.setCirculo(p.getCirculo());
                            ap.setDocumentoPago(dpr);
                        }
                    } else {
                        DocumentoPagoEnhanced dpr = this.crearDocumentoPago(dp, pm);
                        //dp.setSaldoDocumento(dp.getSaldoDocumento()-ap.getValorAplicado());
                        dp.setCirculo(p.getCirculo());
                        ap.setDocumentoPago(dp);
                        ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                        ap.setPago(pago);
                    }
                }
                if (concepto != null && solicitud.getProceso().getIdProceso() == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                    pago.setAplicacionesPagos(aps);
                }
                /**
                 * CASO CERTIFICADOS ASOCIADOS DE REGISTRO Crear pagos para cada
                 * uno de los certificados asociados:
                 */

                for (int i = 0; i < tamAsociadas; i++) {
                    SolicitudAsociadaEnhanced solAsociada = (SolicitudAsociadaEnhanced) listaAsociados.get(i);
                    SolicitudCertificadoEnhanced solCert = (SolicitudCertificadoEnhanced) solAsociada.getSolicitudHija();
                    PagoEnhanced pagoCertificado = new PagoEnhanced();

                    //Atributos del pago para los certificados.
                    //USUARIO
                    UsuarioEnhanced usuarioEnh = p.getUsuario();
                    UsuarioEnhanced otroUsuarioEnh = variablesDAO.getUsuarioByLogin(usuarioEnh.getUsername(), pm);
                    if (usuarior == null) {
                        throw new DAOException("No encontró el Usuario con el login: " + p.getUsuario());
                    }
                    pagoCertificado.setUsuario(otroUsuarioEnh);
                    pagoCertificado.setFecha(new Date());

                    //LIQUIDACION
                    LiquidacionEnhanced liqCert = null;
                    liqCert = (LiquidacionEnhanced) solCert.getLiquidaciones().get(0);
                    LiquidacionEnhancedPk liqCertId = new LiquidacionEnhancedPk();
                    liqCertId.idLiquidacion = liqCert.getIdLiquidacion();
                    liqCertId.idSolicitud = liqCert.getIdSolicitud();
                    pagoCertificado.setIdSolicitud(liqCert.getIdSolicitud());
                    pagoCertificado.setIdLiquidacion(liqCert.getIdLiquidacion());

                    /*if (estacion != null)
  				  {
						EstacionReciboEnhanced.ID estRecId = new EstacionReciboEnhanced.ID();
						estRecId.idEstacion = estacion;
						long recibo = recibosDAO.getNextNumeroRecibo(estRecId, pm);
						pagoCertificado.setNumRecibo(String.valueOf(recibo));
				  }*/
                    LiquidacionEnhanced liqCertPers = liquidacionesDAO.getLiquidacionByID(liqCertId, pm);
                    valorCertificadosAsociados += liqCertPers.getValor();
                    if (liqCertPers == null) {
                        throw new DAOException("No encontró la liquidación de Certificados Asociados con el ID: " + liqCertId.idLiquidacion);
                    }

                    //Se verifica que la liquidación no haya sido pagada
                    //ESTO SUCEDE CUANDO ES UN PAGO POR MAYOR VALOR. En este caso no se deben
                    //hacer persistentes los pagos de los certificados asociados.
                    PagoEnhanced pagoMasivos = null;
                    try {
                        pagoMasivos = liqCertPers.getPago();
                    } catch (JDOObjectNotFoundException jdoException) {
                        pagoMasivos = null;
                    }
                    if (liqCertPers == null || pagoMasivos == null) {
                        pagoCertificado.setLiquidacion(liqCertPers);

                        //SE CREA EL DOCUMENTO DE PAGO.
                        DocPagoHeredadoEnhanced docPago = new DocPagoHeredadoEnhanced(0);
                        DocumentoPagoEnhanced docPagoHeredado = this.crearDocumentoPago(docPago, pm);

                        //SE ASOCIA EL DOCUMENTO PAGO CON UNA APLICACION DE PAGO
                        AplicacionPagoEnhanced apPago = new AplicacionPagoEnhanced();
                        if (docPagoHeredado != null) {
                            docPagoHeredado.setCirculo(p.getCirculo());
                        }
                        apPago.setDocumentoPago(docPagoHeredado);
                        apPago.setIdDocumentoPago(docPagoHeredado.getIdDocumentoPago());
                        apPago.setPago(pagoCertificado);
                        apPago.setCirculo(idCirculo);
                        pagoCertificado.addAplicacionPago(apPago);
                        pagoCertificado.setCirculo(idCirculo);
                        pm.makePersistent(pagoCertificado);
                    }

                }
                if (valorCertificadosAsociados != 0) {
                    lr.setValor(lr.getValor() + valorCertificadosAsociados);
                }
                pm.makePersistent(pago);
            }// FIN DEL IF
            else {
                System.out.println("ANTES registrarPagoRegistroYCertificadosAsociados");
                registrarPagoRegistroYCertificadosAsociados(pago, solicitud, lr, pm);
                System.out.println("DESPUES registrarPagoRegistroYCertificadosAsociados");
            }

            PagoEnhancedPk pagoId = (PagoEnhancedPk) pm.getObjectId(pago);
            if (pagoId != null) {
                System.out.println(" egistrarPagoRegistroYCertificadosAsociados pagoId " + pagoId);
                pagoEnh = (PagoEnhanced) pm.getObjectById(pagoId, true);
            } else {
                System.out.println(" egistrarPagoRegistroYCertificadosAsociados pagoId null " + pagoId);
                pagoEnh = pago;
            }

        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOPagosDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException de) {
            Log.getInstance().error(JDOPagosDAO.class, de.getMessage(), de);
            throw new DAOException(de.getMessage(), de);
        } catch (Throwable e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return pagoEnh;
    }

    /**
     * Registra un <code>Pago</code> en el sistema.
     * <p>
     * Este metodo es usado para el registo del pago de turnos de registro. En
     * el caso especial de que el turno tenga certificados asociados, los
     * valores que se le aplican a cada documento pago se debe repartir entre
     * todos los turnos de certificado y el de registro. Se tiene como prioridad
     * los turnos de certificado.
     *
     * @param p <code>PagoEnhanced</code> con sus atributos,
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param liquidacion <code>Liquidacion </code> asociada con la creación de
     * la <code>Solicitud</code>
     * @return solicitud <code>SolicitudEnhanced </code> asociada con la
     * creación del <code>Pago</code>
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Pago
     * @see gov.sir.core.negocio.modelo.PagoEnhanced
     */
    protected void registrarPagoRegistroYCertificadosAsociados(PagoEnhanced p, SolicitudEnhanced solicitud, LiquidacionEnhanced lr, PersistenceManager pm)
            throws DAOException {
        System.out.println("ENTRO registrarPagoRegistroYCertificadosAsociados");

        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();

        List listaAsociados = solicitud.getSolicitudesHijas();
        int tamAsociadas = listaAsociados.size();

        List aps = p.getAplicacionPagos();
        if (aps == null) {
            throw new DAOException("La lista de aplicaciones pago es nula");
        }

        Iterator ita = aps.iterator();

        List datosOrdenados = new ArrayList();

        List documentosExistentes = new ArrayList();
        List documentosCreados = new ArrayList();

        /**
         * Se ordenan los documentos: 1. Efectivo, 2. consignacion, 3. cheque,
         * 4. pago convenio 5. timbre los documentos que ya existen se colocan
         * en las primeras posiciones para que al repartir sean los primeros en
         * usar
         */
        while (ita.hasNext()) {
            System.out.println("ITER registrarPagoRegistroYCertificadosAsociados");
            AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) ita.next();
            if (ap == null) {
                throw new DAOException("La aplicacion de Pago es nula");
            }
            DocumentoPagoEnhanced dp = ap.getDocumentoPago();
            if (dp == null) {
                throw new DAOException("El documento de pago es nulo");
            }

            if (dp.getIdDocumentoPago() != null) {
                System.out.println("getIdDocumentoPago NO NULL registrarPagoRegistroYCertificadosAsociados");
                /**
                 * Se coloca el valor del documento en 0 para identificar que el
                 * documento ya existe y para poder actualizar su saldo en el
                 * momento de realizar la reparticion de dinero entre los
                 * certificados asociados. El valor que se aplica se guarda como
                 * el saldo del documento antiguo para luego restarlo al saldo
                 * del documento y tenerlo como valor a repartir entre los
                 * certificados
                 */
                dp.setValorDocumento(0);
                dp.setSaldoDocumento(ap.getValorAplicado());
                documentosExistentes.add(dp);

            } else {
                System.out.println("ANTES crearDocumentoPago");
                dp = this.crearDocumentoPago(dp, pm);
                System.out.println("DESPUES crearDocumentoPago");
                dp.setCirculo(p.getCirculo());
                documentosCreados.add(dp);
            }

        }

        /**
         * Se toma primero los documentos existentes y luego los creados
         */
        datosOrdenados = ordenarDocumentos(documentosExistentes);
        datosOrdenados.addAll(ordenarDocumentos(documentosCreados));

        double valorAplicadoDocumento = 0;	// Indica que cantidad de dinero se le ha aplicado al documento
        int posicionDoc = 0;				// Posicion del documento	
        List posicionYvalorAp = new ArrayList();
        posicionYvalorAp.add(new Integer(posicionDoc));
        posicionYvalorAp.add(new Double(valorAplicadoDocumento));

        /**
         * SE CREAN LAS APLICACIONES Y DOCUMENTOS PAGO PARA LOS CERTIFICADOS
         * ASOCIADOS. SE REPARTE LOS VALORES DE LAS APLICACIONES ENTRE LOS
         * TURNOS DE REGISTRO Y CERTIFICADOS ASOCIADOS
         */

        /*Si la solicitud tiene mas de una liquidacion solo genera aplicaciones pago
		para el turno de registro (Pago mayor Valor)*/
        if (solicitud.getLiquidaciones().size() == 1) {

            // Se crean las aplicaciones pago para los certificados asociados
            for (int i = 0; i < tamAsociadas; i++) {
                SolicitudAsociadaEnhanced solAsociada = (SolicitudAsociadaEnhanced) listaAsociados.get(i);
                SolicitudCertificadoEnhanced solCert = (SolicitudCertificadoEnhanced) solAsociada.getSolicitudHija();

                LiquidacionEnhanced liqCert = null;
                liqCert = (LiquidacionEnhanced) solCert.getLiquidaciones().get(0);
                LiquidacionEnhancedPk liqCertId = new LiquidacionEnhancedPk();
                liqCertId.idLiquidacion = liqCert.getIdLiquidacion();
                liqCertId.idSolicitud = liqCert.getIdSolicitud();

                LiquidacionEnhanced liqCertPers = liquidacionesDAO.getLiquidacionByID(liqCertId, pm);
                if (liqCertPers == null) {
                    throw new DAOException("No encontró la liquidación de Certificados Asociados con el ID: " + liqCertId.idLiquidacion);
                }
                posicionDoc = ((Integer) posicionYvalorAp.get(0)).intValue();
                valorAplicadoDocumento = ((Double) posicionYvalorAp.get(1)).doubleValue();
                posicionYvalorAp = this.repartirValoresDocumento(datosOrdenados, p, liqCertPers, posicionDoc, valorAplicadoDocumento,
                        true, pm);

            }
        }
        //lr.setValor(lr.getValor()+valorCertificadosAsociados);
        posicionDoc = ((Integer) posicionYvalorAp.get(0)).intValue();
        valorAplicadoDocumento = ((Double) posicionYvalorAp.get(1)).doubleValue();
        // Se crean las aplicaciones pago para el turno de registro.
        this.repartirValoresDocumento(datosOrdenados, p, lr, posicionDoc, valorAplicadoDocumento,
                false, pm);

    }

    /**
     * Reparte los valores aplicados al documento entre los certificados
     * asociados y el turno de registro.
     *
     * @param datosOrdenados <code>Vector</code>,
     * @param p <code>PagoEnhanced</code> con sus atributos,
     * @param pm <code>PersistenceManager</code> de la transaccion.
     * @param liquidacion <code>Liquidacion </code>
     * @param posicionDoc <code>int </code>,
     * @param valorAplicadoDocumento <code>double</code>,
     * @param isCertificado <code>boolean</code>
     * @throws DAOException
     */
    protected List repartirValoresDocumento(List datosOrdenados, PagoEnhanced p,
            LiquidacionEnhanced liquidacion, int posicionDoc, double valorAplicadoDocumento,
            boolean isCertificado, PersistenceManager pm) throws DAOException {

        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        double diferencia = 0;	//Indica el saldo disponible para repartir de un documento
        double valorSaldoDocumento = 0; //Indica si un documento recien creado tiene saldo
        List posicionYvalorAp = new ArrayList();

        /**
         * Valor real disponible para repartir (Solo aplica cuando se utilizan
         * documentos existentes y no recienemente creados)
         */
        double valorDocumentoExistente = 0;

        AplicacionPagoEnhanced apPago = new AplicacionPagoEnhanced();

        //Cuando la liquidacion de registro o certificado es 0 y se haya incrementado posicionDoc
        if (liquidacion.getValor() == 0 && posicionDoc >= (datosOrdenados.size())) {
            posicionDoc--;
        }
        DocumentoPagoEnhanced documento = (DocumentoPagoEnhanced) datosOrdenados.get(posicionDoc);

        if (documento == null) {
            throw new DAOException("El documento de pago es nulo");
        }

        if ((documento.getSaldoDocumento() != 0 && (documento.getValorDocumento() != 0))
                || (documento instanceof DocPagoTarjetaCreditoEnhanced)
                || (documento instanceof DocPagoTarjetaDebitoEnhanced)
                || (documento instanceof DocPagoElectronicoPSEEnhanced)) {
            /**
             * @autor HGOMEZ
             * @mantis 12422
             * @Requerimiento 049_453
             * @descripcion Se comenta la siguiente línea para dar solución al
             * requerimiento.
             */
            //|| (documento instanceof DocPagoConvenioEnhanced)) {
            /**
             * Si el documento es recien creado y tiene saldo, la diferencia se
             * hace con respecto al valor aplicado y no por el valor total del
             * documento
             */
            valorSaldoDocumento = documento.getValorDocumento() - documento.getSaldoDocumento();
            diferencia = valorSaldoDocumento - valorAplicadoDocumento;
        } else if (documento.getValorDocumento() == 0) {
            /**
             * En caso de que el valor del pago se haya hecho con respecto a un
             * documento existente se debe actualizar el saldo y la variable
             * diferencia
             */
            DocumentoPagoEnhancedPk dpId = new DocumentoPagoEnhancedPk();
            dpId.idDocumentoPago = documento.getIdDocumentoPago();
            DocumentoPagoEnhanced dpr = this.getDocumentosPagoById(dpId, pm);
            dpr.setCirculo(p.getCirculo());
            // Se actualiza el saldo del documento con el valor aplicado solo por una vez
            // El saldo se modifica solo cuando la liquidacion  tiene valor diferente a 0
            if ((valorAplicadoDocumento == 0) && (liquidacion.getValor() != 0)) {
                dpr.setSaldoDocumento(dpr.getSaldoDocumento() - documento.getSaldoDocumento());
            }
            valorDocumentoExistente = documento.getSaldoDocumento();
            diferencia = valorDocumentoExistente - valorAplicadoDocumento;
            documento = dpr;
        } else {
            diferencia = documento.getValorDocumento() - valorAplicadoDocumento;
        }

        PagoEnhanced pagoCertificado = new PagoEnhanced();
        if (!isCertificado) {
            /**
             * En el caso de registro se almacena el pago con solo las
             * aplicaciones nuevas
             */
            p.setAplicacionesPagos(new ArrayList());
        } else {
            UsuarioEnhanced usuarioEnh = p.getUsuario();
            UsuarioEnhanced otroUsuarioEnh = variablesDAO.getUsuarioByLogin(usuarioEnh.getUsername(), pm);
            pagoCertificado.setUsuario(otroUsuarioEnh);
            pagoCertificado.setFecha(new Date());
            pagoCertificado.setIdSolicitud(liquidacion.getIdSolicitud());
            pagoCertificado.setIdLiquidacion(liquidacion.getIdLiquidacion());
        }
        /**
         * El documento cubre la totalidad del certificado Asociado
         */
        if (liquidacion.getValor() <= diferencia) {
            valorAplicadoDocumento += liquidacion.getValor();
            PagoEnhanced pagoMasivos = null;
            if (isCertificado) {
                try {
                    pagoMasivos = liquidacion.getPago();
                } catch (JDOObjectNotFoundException jdoException) {
                    pagoMasivos = null;
                }
            }
            if (liquidacion == null || pagoMasivos == null) {
                if (isCertificado) {
                    if (valorDocumentoExistente != 0) {
                        if (valorAplicadoDocumento == valorDocumentoExistente) {
                            valorDocumentoExistente = 0;
                            valorAplicadoDocumento = 0;
                            posicionDoc++;
                        }
                    }
                    if (valorSaldoDocumento != 0) {
                        if (valorAplicadoDocumento == valorSaldoDocumento) {
                            valorSaldoDocumento = 0;
                            valorAplicadoDocumento = 0;
                            posicionDoc++;
                        }
                    } else {
                        if (valorAplicadoDocumento == documento.getValorDocumento()) {
                            valorAplicadoDocumento = 0;
                            posicionDoc++;
                        }
                    }
                    pagoCertificado.setLiquidacion(liquidacion);
                }

                //SE ASOCIA EL DOCUMENTO PAGO CON UNA APLICACION DE PAGO
                apPago = new AplicacionPagoEnhanced();
                apPago.setDocumentoPago(documento);
                apPago.setIdDocumentoPago(documento.getIdDocumentoPago());
                apPago.setValorAplicado(liquidacion.getValor());
                if (isCertificado) {
                    apPago.setCirculo(documento.getCirculo());
                    apPago.setPago(pagoCertificado);
                    pagoCertificado.addAplicacionPago(apPago);
                    pagoCertificado.setCirculo(apPago.getCirculo());
                    pm.makePersistent(pagoCertificado);
                } else {
                    if (liquidacion != null && liquidacion.getCirculo() != null) {
                        apPago.setCirculo(liquidacion.getCirculo());
                    }
                    apPago.setPago(p);
                    p.addAplicacionPago(apPago);
                    pm.makePersistent(p);
                }
            }
        } else {

            /**
             * En caso de que quede un saldo de la aplicacion se abona a la
             * liquidacion
             */
            if (diferencia > 0) {
                double valorLiquidacion = 0;
                double valorPagado = 0;
                while (valorPagado != liquidacion.getValor()) {
                    //Solo se consulta una vez el documento que no se ha creado recientemente
                    if (valorPagado != 0 && valorDocumentoExistente == 0) {
                        documento = (DocumentoPagoEnhanced) datosOrdenados.get(posicionDoc);
                    }
                    if ((documento.getValorDocumento() == 0) && (valorPagado != 0)) {

                        /**
                         * En caso de que el valor del pago se haya hecho con
                         * respecto a un documento que no se creo recientemente
                         * se debe actulizar el saldo y la variable diferencia
                         */
                        DocumentoPagoEnhancedPk dpId = new DocumentoPagoEnhancedPk();
                        dpId.idDocumentoPago = documento.getIdDocumentoPago();
                        DocumentoPagoEnhanced dpr = this.getDocumentosPagoById(dpId, pm);
                        dpr.setCirculo(p.getCirculo());
                        // Se actualiza el saldo del documento con el valor aplicado solo por una vez
                        // El saldo se modifica solo cuando la liquidacion  tiene valor diferente a 0
                        if ((valorAplicadoDocumento == 0) && (liquidacion.getValor() != 0)) {
                            dpr.setSaldoDocumento(dpr.getSaldoDocumento() - documento.getSaldoDocumento());
                        }
                        valorDocumentoExistente = documento.getSaldoDocumento();
                        diferencia = valorDocumentoExistente - valorAplicadoDocumento;
                        documento = dpr;
                    } else if (valorDocumentoExistente != 0) {
                        /**
                         * Si es un documento que no se creo recientemente pero
                         * que ya se consulto de la base de datos y se actulizo
                         * el saldo, se cambia la variable diferencia con
                         * respecto al valor aplicado al documento
                         */
                        diferencia = valorDocumentoExistente - valorAplicadoDocumento;
                    } else {
                        if (documento.getSaldoDocumento() == 0) {
                            diferencia = documento.getValorDocumento() - valorAplicadoDocumento;
                        } else {
                            /**
                             * Si es un documento recien creado con saldo se
                             * actualiza la variable diferencia con respecto al
                             * valor aplicado al documento
                             */
                            valorSaldoDocumento = documento.getValorDocumento() - documento.getSaldoDocumento();
                            diferencia = valorSaldoDocumento - valorAplicadoDocumento;
                        }
                    }

                    valorLiquidacion = liquidacion.getValor() - valorPagado;
                    if (diferencia > valorLiquidacion) {
                        diferencia = liquidacion.getValor() - valorPagado;
                    }
                    if (diferencia > 0) {
                        valorAplicadoDocumento += diferencia;
                        valorPagado += diferencia;
                        PagoEnhanced pagoMasivos = null;
                        if (isCertificado) {
                            try {
                                pagoMasivos = liquidacion.getPago();
                            } catch (JDOObjectNotFoundException jdoException) {
                                pagoMasivos = null;
                            }
                        }
                        if (liquidacion == null || pagoMasivos == null) {
                            if (valorDocumentoExistente != 0) {
                                if (valorAplicadoDocumento == valorDocumentoExistente) {
                                    valorDocumentoExistente = 0;
                                    valorAplicadoDocumento = 0;
                                    posicionDoc++;
                                }
                            } else {
                                if (valorSaldoDocumento != 0) {
                                    if (valorAplicadoDocumento == valorSaldoDocumento) {
                                        valorSaldoDocumento = 0;
                                        valorAplicadoDocumento = 0;
                                        posicionDoc++;
                                    }
                                    //Edgar Lora: Mantis: 0012422
                                } else {
                                    if (valorAplicadoDocumento == documento.getValorDocumento()) {
                                        valorAplicadoDocumento = 0;
                                        posicionDoc++;
                                    }
                                }
                            }
                            if (isCertificado) {
                                pagoCertificado.setLiquidacion(liquidacion);
                            }

                            //SE ASOCIA EL DOCUMENTO PAGO CON UNA APLICACION DE PAGO
                            apPago = new AplicacionPagoEnhanced();
                            apPago.setDocumentoPago(documento);
                            apPago.setIdDocumentoPago(documento.getIdDocumentoPago());
                            apPago.setValorAplicado(diferencia);
                            if (isCertificado) {
                                pagoCertificado.addAplicacionPago(apPago);
                                apPago.setCirculo(documento.getCirculo());
                                pagoCertificado.setCirculo(apPago.getCirculo());
                                apPago.setPago(pagoCertificado);
                                pm.makePersistent(pagoCertificado);
                            } else {
                                if (liquidacion != null && liquidacion.getCirculo() != null) {
                                    apPago.setCirculo(liquidacion.getCirculo());
                                }
                                apPago.setPago(p);
                                p.addAplicacionPago(apPago);
                                pm.makePersistent(p);
                            }
                        }
                    }
                }
            }
        }
        posicionYvalorAp.add(new Integer(posicionDoc));
        posicionYvalorAp.add(new Double(valorAplicadoDocumento));

        return posicionYvalorAp;
    }

    /**
     * Ordena los documentos de la aplicacion pago
     *
     * Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática
     * Ltda. Cambio para agregar nota débito requerimiento 108 - Incidencia
     * Mantis 02360.
     *
     * @param ordenados <code>List</code> lista donde se encuentra los
     * documentos ordenados
     */
    /**
     * @autor HGOMEZ
     * @mantis 12422
     * @Requerimiento 049_453
     * @descripcion Se refactoriza el método.
     */
    protected Vector ordenarDocumentos(List documentos) {
        Vector datosOrdenados = new Vector();
        Vector pagoEfectivo = new Vector();
        Vector pagoConsignacion = new Vector();
        Vector pagoTarjetaCredito = new Vector();
        Vector pagoTarjetaDebito = new Vector();
        Vector pagoPse = new Vector();
        Vector pagoCheque = new Vector();
        Vector pagoNotaDebito = new Vector();
        Vector pagoConvenio = new Vector();
        Vector timbre = new Vector();
        Vector general = new Vector();
        for (Iterator iter = documentos.iterator(); iter.hasNext();) {
            DocumentoPagoEnhanced dp = (DocumentoPagoEnhanced) iter.next();
            if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_EFECTIVO)) {
                pagoEfectivo.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_CONSIGNACION)) {
                pagoConsignacion.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_TARJETA_CREDITO)) {
                pagoTarjetaCredito.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_TARJETA_DEBITO)) {
                pagoTarjetaDebito.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_PSE)) {
                pagoPse.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_CHEQUE)) {
                pagoCheque.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_NOTA_DEBITO)) {
                pagoNotaDebito.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.PAGO_CONVENIO)) {
                pagoConvenio.add(dp);
            } else if (dp.getTipoPago().equals(DocumentoPagoEnhanced.TIMBRE_CONSTANCIA_LIQUIDACION)) {
                timbre.add(dp);
            } else {
                general.add(dp);
            }
        }
        datosOrdenados.addAll(pagoEfectivo);
        datosOrdenados.addAll(pagoConsignacion);
        datosOrdenados.addAll(pagoTarjetaCredito);
        datosOrdenados.addAll(pagoTarjetaDebito);
        datosOrdenados.addAll(pagoPse);
        datosOrdenados.addAll(pagoCheque);
        datosOrdenados.addAll(pagoNotaDebito);
        datosOrdenados.addAll(pagoConvenio);
        datosOrdenados.addAll(timbre);
        datosOrdenados.addAll(general);
        return datosOrdenados;

    }
  
    /**
     * Hace transiente un documento de pago
     *
     * @param docPago
     * @param pm
     * @throws DAOException
     */
    protected void makeTransientDocumentoPago(DocumentoPagoEnhanced docPago, PersistenceManager pm) throws DAOException {
        if (docPago != null) {
            try {

                // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
                // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
                if (docPago instanceof DocPagoChequeEnhanced) {
                    DocPagoChequeEnhanced docPagoCheque = (DocPagoChequeEnhanced) docPago;
                    pm.makeTransient(docPagoCheque.getBanco());
                } else if (docPago instanceof DocPagoNotaDebitoEnhanced) {
                    DocPagoNotaDebitoEnhanced docPagoNotaDebito = (DocPagoNotaDebitoEnhanced) docPago;
                    pm.makeTransient(docPagoNotaDebito.getBanco());
                } else if (docPago instanceof DocPagoConsignacionEnhanced) {
                    DocPagoConsignacionEnhanced docPagoCons = (DocPagoConsignacionEnhanced) docPago;
                    pm.makeTransient(docPagoCons.getBanco());
                } /**
                 * Author: Santiago Vásquez Change:
                 * 1242.VER.DETALLES.TURNO.VISUALIZACION.FORMAS.PAGO
                 */
                else if (docPago instanceof DocPagoTarjetaCreditoEnhanced) {
                    DocPagoTarjetaCreditoEnhanced docPagoCons = (DocPagoTarjetaCreditoEnhanced) docPago;
                    pm.makeTransient(docPagoCons.getBancoFranquicia());
                } else if (docPago instanceof DocPagoTarjetaDebitoEnhanced) {
                    DocPagoTarjetaDebitoEnhanced docPagoCons = (DocPagoTarjetaDebitoEnhanced) docPago;
                    pm.makeTransient(docPagoCons.getBancoFranquicia());
                } else if (docPago instanceof DocPagoElectronicoPSEEnhanced) {
                    DocPagoElectronicoPSEEnhanced docPagoCons = (DocPagoElectronicoPSEEnhanced) docPago;
                    pm.makeTransient(docPagoCons.getBancoFranquicia());
                }   else if (docPago instanceof DocPagoGeneralEnhanced) {
                    DocPagoGeneralEnhanced docPagoGeneral = (DocPagoGeneralEnhanced) docPago;
                    pm.makeTransient(docPagoGeneral.getBancoFranquicia());
                    pm.makeTransient(docPagoGeneral.getBanco());
                }

                pm.makeTransient(docPago);
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.PagosDAO#setNumeroReciboPago(gov.sir.core.negocio.modelo.Pago.ID, java.lang.String)
     */
    public void setNumeroReciboPago(PagoPk pagoID, String numRecibo) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            PagoEnhancedPk pagoEnhID = new PagoEnhancedPk();
            pagoEnhID.idLiquidacion = pagoID.idLiquidacion;
            pagoEnhID.idSolicitud = pagoID.idSolicitud;
            PagoEnhanced pago = (PagoEnhanced) pm.getObjectById(pagoEnhID, true);

            //Primer número de recibo generado
            if (pago.getNumRecibo() == null) {
                pago.setNumRecibo(numRecibo);
            }

            //Rastro de último número de recibo
            pago.setLastNumRecibo(numRecibo);

            pm.makePersistent(pago);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.PagosDAO#getPagoByNumeroRecibo(java.lang.String)
     */
    public Pago getUltimoPagoByNumeroRecibo(String numRecibo) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solicitudesDao = new JDOSolicitudesDAO();
        PagoEnhanced pagoEnh = null;

        try {
            pm.currentTransaction().begin();

            Query query = pm.newQuery(PagoEnhanced.class);
            query.setOrdering("fechaImpresion descending, fecha descending");
            query.declareParameters("String num");
            query.setFilter("this.numRecibo==num");
            List col = (List) query.execute(numRecibo);

            if (col.size() > 0) {
                pagoEnh = (PagoEnhanced) col.get(0);

                SolicitudEnhanced solicitud = pagoEnh.getLiquidacion().getSolicitud();
                for (Iterator iter = pagoEnh.getAplicacionPagos().iterator(); iter.hasNext();) {
                    AplicacionPagoEnhanced aplicacion = (AplicacionPagoEnhanced) iter.next();
                    if (aplicacion != null) {
                        if (aplicacion.getDocumentoPago() != null) {
                            makeTransientDocumentoPago(aplicacion.getDocumentoPago(), pm);
                        }
                        pm.makeTransient(aplicacion);
                    }
                }
                pm.makeTransient(solicitud.getTurno());
                solicitudesDao.makeTransientSolicitud(solicitud, pm);
                pm.makeTransient(pagoEnh.getLiquidacion());
                pm.makeTransient(pagoEnh);
            }
            query.closeAll();
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }

        Pago ultimoPago = (Pago) pagoEnh.toTransferObject();

        return ultimoPago;
    }

    public boolean existeConsignacionDevolucion(String idDocumentoPago) throws DAOException {
        ConsignacionEnhanced consigEnh = null;
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            Query query = pm.newQuery(ConsignacionEnhanced.class);
            query.declareParameters("String idDocumentoPago");
            query.setFilter("idDocPago==idDocumentoPago");
            Collection col = (Collection) query.execute(idDocumentoPago);
            if (col.size() == 0) {
                consigEnh = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    consigEnh = (ConsignacionEnhanced) iter.next();
                }
                query.closeAll();
            }
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            consigEnh = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (consigEnh != null) {
            return true;
        }
        return false;
    }

    /**
     * Valida si un documento pago (consignacion, cheque o nota débito) es
     * valido en devolución.
     *
     * Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática
     * Ltda. Cambio para agregar nota débito requerimiento 108 - Incidencia
     * Mantis 02360.
     *
     * @param dp
     * @return
     * @throws DAOException
     */
    public boolean getDocsPagosDevolucion(DocumentoPago dp)
            throws DAOException {
        List rta = new ArrayList();
        String idBanco;
        if (dp == null) {
            return false;
        }
        PersistenceManager pm = AdministradorPM.getPM();
        if (dp instanceof DocPagoConsignacion) {
            String consignacion = ((DocPagoConsignacion) dp).getNoConsignacion();
            if (((DocPagoConsignacion) dp).getBanco() != null) {
                idBanco = ((DocPagoConsignacion) dp).getBanco().getIdBanco();
                try {
                    Query query = pm.newQuery(DocPagoConsignacionEnhanced.class);
                    query.declareParameters("String idbanco, String consignacion");
                    query.setFilter("banco.idBanco == idbanco && noConsignacion == consignacion ");
                    query.setOrdering("idDocumentoPago ascending");
                    Collection col = (Collection) query.execute(idBanco, consignacion);
                    if (col.size() == 0) {
                        rta = null;
                    } else {
                        for (Iterator iter = col.iterator(); iter.hasNext();) {
                            rta.add(((DocPagoConsignacionEnhanced) iter.next()).getIdDocumentoPago());
                        }
                        query.closeAll();
                    }
                } catch (JDOObjectNotFoundException e) {
                    Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                    rta = null;
                } catch (JDOException e) {
                    Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                    throw new DAOException(e.getMessage(), e);
                }
            }
        } else if (dp instanceof DocPagoCheque) {
            String cheque = ((DocPagoCheque) dp).getNoCheque();
            if (((DocPagoCheque) dp).getBanco() != null) {
                idBanco = ((DocPagoCheque) dp).getBanco().getIdBanco();
                try {
                    Query query = pm.newQuery(DocPagoChequeEnhanced.class);
                    query.declareParameters("String cheque, String bancoId");
                    query.setFilter("noCheque == cheque && banco.idBanco == bancoId");
                    query.setOrdering("idDocumentoPago ascending");
                    Collection col = (Collection) query.execute(cheque, idBanco);
                    if (col.size() == 0) {
                        rta = null;
                    } else {
                        for (Iterator iter = col.iterator(); iter.hasNext();) {
                            rta.add(((DocPagoChequeEnhanced) iter.next()).getIdDocumentoPago());
                        }
                        query.closeAll();
                    }
                } catch (JDOObjectNotFoundException e) {
                    Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                    rta = null;
                } catch (JDOException e) {
                    Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                    throw new DAOException(e.getMessage(), e);
                }
            }
        } else if (dp instanceof DocPagoNotaDebito) {
            String notaDebito = ((DocPagoNotaDebito) dp).getNoNotaDebito();
            if (((DocPagoNotaDebito) dp).getBanco() != null) {
                idBanco = ((DocPagoNotaDebito) dp).getBanco().getIdBanco();
                try {
                    Query query = pm.newQuery(DocPagoNotaDebitoEnhanced.class);
                    query.declareParameters("String notaDebito, String bancoId");
                    query.setFilter("noNotaDebito == notaDebito && banco.idBanco == bancoId");
                    query.setOrdering("idDocumentoPago ascending");
                    Collection col = (Collection) query.execute(notaDebito, idBanco);
                    if (col.size() == 0) {
                        rta = null;
                    } else {
                        for (Iterator iter = col.iterator(); iter.hasNext();) {
                            rta.add(((DocPagoNotaDebitoEnhanced) iter.next()).getIdDocumentoPago());
                        }
                        query.closeAll();
                    }
                } catch (JDOObjectNotFoundException e) {
                    Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                    rta = null;
                } catch (JDOException e) {
                    Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                    throw new DAOException(e.getMessage(), e);
                }
            }
        }
        pm.close();
        if (rta != null && rta.size() > 1) {
            if (dp.getIdDocumentoPago() != null && dp.getIdDocumentoPago().compareTo((String) rta.get(0)) <= 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Crea un <code>Turno y un <code>Pago</code> en el sistema, y crea una
     * instacia de Workflow de acuerdo con el <code>Proceso</code> determinado.
     *
     * @param p Pago con sus atributos, exceptuando el identificador
     * @param parametros Hashtable parametros para crear el turno.
     * @return Turno Generado luego de procesar el pago.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Pago
     */
    public TurnoEnhanced procesarPagoPortal(PagoEnhanced p, Hashtable parametros, PersistenceManager pm) throws DAOException {

        TurnoEnhancedPk tId = new TurnoEnhancedPk();
        TurnoEnhanced t = null;
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        String estacion = (String) parametros.get(CEstacion.ESTACION_ID);
        String impresora = (String) parametros.get(CImpresion.IMPRESORA);
        UsuarioEnhanced user = (UsuarioEnhanced) parametros.get(CInfoUsuario.USUARIO_SIR);

        long tiempoInicial = System.currentTimeMillis();

        Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
        Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(INICIO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
        Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

        try {

            if (p.getLiquidacion() == null) {
                throw new DAOException("No existe una liquidación asociada al pago");
            }

            if (p.getLiquidacion().getSolicitud() == null) {
                throw new DAOException("No existe una solicitud para la liquidacion");
            }

            //Se crea el turno, y el documento de pago y se hacen persistentes.
            tId = this.procesarPagoPortal(p, estacion, impresora, user, pm);

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(PROCESAR PAGO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            //Obtener el turno.
            t = turnosDAO.getTurnoByID(tId, pm);

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(CONSULTAR TURNO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            if (t == null) {
                throw new DAOException("No se encontró el turno con el id dado");
            }

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(HACER TRASIENTE EL TURNO):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

            //Se crea la instancia de Workflow
            //Si se presenta algún problema, se lanza una excepción.
            try {
                turnosDAO.crearHistoriaTurnoCertificadoMasivoPortal(t, (Usuario) user.toTransferObject(), pm);
            } catch (DAOException de) {
                throw new DAOException("Se presentó un error al crear la instancia de Workflow");
            }

            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOPagosDAO.class, "(procesarPago)(CREAR INSTANCIA WF):" + ((p != null && p.getIdSolicitud() != null) ? p.getIdSolicitud() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOPagosDAO.class, "\n*******************************************************\n");

        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            throw (e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return t;
    }

    protected TurnoEnhancedPk procesarPagoPortal(PagoEnhanced p, String estacion, String impresora, UsuarioEnhanced user, PersistenceManager pm)
            throws DAOException {

        if (p.getLiquidacion() == null) {
            throw new DAOException("La liquidación recilbida es null");
        }

        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        TurnoEnhancedPk tId = null;

        try {
            CirculoProcesoEnhanced cpe = this.getCirculoProcesoByPago(p, pm);

            if (p.getUsuario() == null) {
                throw new DAOException(
                        "El pago no esta asociada a un Usuario");
            }

            LiquidacionEnhanced lr = p.getLiquidacion();
            if (lr == null) {
                throw new DAOException("La liquidacion recibida para procesar el pago es nula");
            }

            p.setIdLiquidacion(lr.getIdLiquidacion());
            p.setIdSolicitud(lr.getIdSolicitud());
            p.setLiquidacion(lr);

            if (lr != null && lr.getCirculo() != null) {
                p.setCirculo(lr.getCirculo());
            } else {
                Log.getInstance().debug(JDOPagosDAO.class, "(PAGO)NO SE PUDO GUARDAR EL IDENTIFICADOR DEL CIRCULO " + (lr != null ? lr.getIdSolicitud() : ""));
            }

            List aps = p.getAplicacionPagos();
            if (aps == null) {
                throw new DAOException("La lista de aplicaciones pago es nula");
            }
            Iterator ita = aps.iterator();
            while (ita.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) ita.next();
                if (ap == null) {
                    throw new DAOException("La aplicacion de Pago es nula");
                }

                if (lr != null && lr.getCirculo() != null) {
                    ap.setCirculo(lr.getCirculo());
                }

                DocumentoPagoEnhanced dp = ap.getDocumentoPago();
                if (dp == null) {
                    throw new DAOException("El documento de pago es nulo");
                }
                double saldo = dp.getValorDocumento() - ap.getValorAplicado();
                dp.setSaldoDocumento(saldo);
                DocumentoPagoEnhanced dpr = this.crearDocumentoPago(dp, pm);
                dp.setCirculo(p.getCirculo());
                ap.setDocumentoPago(dp);
                ap.setIdDocumentoPago(dpr.getIdDocumentoPago());
                ap.setPago(p);
            }
            if (p.getFecha() == null) {
                p.setFecha(new Date());
            }
            pm.makePersistent(p);
            PagoEnhancedPk pId = (PagoEnhancedPk) pm.getObjectId(p);
            tId = turnosDAO.crearTurnoPortal(cpe, pId, impresora, (Usuario) user.toTransferObject(), pm);

            if (tId == null) {
                throw new DAOException("No se pudo crear el turno");
            }

        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOPagosDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException de) {
            Log.getInstance().error(JDOPagosDAO.class, de.getMessage(), de);
            throw new DAOException(de.getMessage(), de);
        }

        return tId;
    }

    public Pago getPagoByIDPortal(PagoPk pID, PersistenceManager pm) throws DAOException {

        PagoEnhanced pr = null;

        try {
            pr = this.getPagoByID(new PagoEnhancedPk(pID), pm);

            List aps = pr.getAplicacionPagos();
            Iterator it = aps.iterator();

            while (it.hasNext()) {
                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) it.next();
                this.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                pm.makeTransient(ap);
            }

            pm.makeTransient(pr.getLiquidacion());
            pm.makeTransient(pr.getLiquidacion().getPago());
            pm.makeTransient(pr.getLiquidacion().getSolicitud());

            pm.makeTransient(pr);

        } catch (DAOException e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return (Pago) pr.toTransferObject();
    }

    /**
     * Obtiene un <code>List</code> de los tipos de pago disponibles para un
     * círculo específico.
     *
     * @return <code>List</code> con los DocumentoPago disponibles en el sistema
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.CirculoTipoPago
     */
    public List getCirculoTiposPago(CirculoPk cirID) throws DAOException {
        List circuloCanalTipoPagoList = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String idCirc = cirID.idCirculo;

        String consulta = "SELECT CTP.ID_CTP, CTP.ID_CIRCULO, C.CRCL_NOMBRE, CR.CR_NOMBRE_CANAL, TDP.TPDP_NOMBRE, CB.CB_NRO_CUENTA, B.BNCO_NOMBRE, CTP.CTP_ACTIVO, CTP.CTP_CANAL_SIR "
                + "FROM SIR_NE_CIRCULO_TIPO_PAGO CTP "
                + "INNER JOIN SIR_NE_CIRCULO C "
                + "ON C.ID_CIRCULO = CTP.ID_CIRCULO "
                + "AND C.ID_CIRCULO = ? "
                + "INNER JOIN SIR_OP_CANALES_RECAUDO CR "
                + "ON CR.CR_ID = CTP.CR_ID "
                + "INNER JOIN SIR_OP_TIPO_DOC_PAGO TDP "
                + "ON TDP.ID_TIPO_DOC_PAGO = CTP.ID_TIPO_DOC_PAGO "
                + "INNER JOIN SIR_OP_CUENTAS_BANCARIAS CB "
                + "ON CB.CB_ID = CTP.CB_ID "
                + "AND CB.CB_ACTIVA = '1' "
                + "INNER JOIN SIR_OP_BANCO B "
                + "ON B.ID_BANCO = CB.CB_ID_BANCO";

        try {
            jdoPM = (VersantPersistenceManager) gov.sir.fenrir.dao.impl.jdogenie.AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);

            ps.setString(1, idCirc);

            rs = ps.executeQuery();

            while (rs.next()) {
                boolean state = true;
                boolean canalSir = true;
                CirculoCanalTipoPago circuloCanalTipoPago = new CirculoCanalTipoPago();
                circuloCanalTipoPago.setIdCtp(rs.getString(1));
                circuloCanalTipoPago.setIdCirculo(rs.getString(2));
                circuloCanalTipoPago.setNombreCirculo(rs.getString(3));
                circuloCanalTipoPago.setNombreCanal(rs.getString(4));
                circuloCanalTipoPago.setNombreFormaPago(rs.getString(5));
                circuloCanalTipoPago.setNroCuenta(rs.getString(6));
                circuloCanalTipoPago.setNombreBanco(rs.getString(7));
                if (!rs.getString(8).equals("1")) {
                    state = false;
                }
                if (!rs.getString(9).equals("1")) {
                    canalSir = false;
                }
                circuloCanalTipoPago.setCtpActivo(state);
                circuloCanalTipoPago.setCanalSir(canalSir);
                circuloCanalTipoPagoList.add(circuloCanalTipoPago);
            }
            rs.close();
            ps.close();

            jdoPM.currentTransaction().commit();

        } catch (NullPointerException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(this.getClass(), e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return circuloCanalTipoPagoList;
    }

    /**
     * Obtiene un <code>List</code> de los campos de captura disponibles para un
     * forma de pago específico.
     *
     * @return <code>List</code> con los CamposCaptura disponibles en el sistema
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.CamposCaptura
     */
    public List camposCapturaXFormaPago(String formaPagoId) throws DAOException {
        List camposCapturaXFormaPagoList = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT CC.CC_ID, CC.CC_NOMBRE_CAMPO, CC.CC_CAMPO_DESTINO, CC.CC_FORM_LABEL, CC.CC_FORM_NAME "
                + "FROM SIR_NE_CAMPOS_CAPTURA CC "
                + "INNER JOIN SIR_OP_REL_FPAGO_CAMPOS RFC "
                + "ON RFC.FPCC_CC_ID = CC.CC_ID "
                + "AND RFC.FPCC_ESTADO = '1' "
                + "AND RFC.FPCC_FP_ID = ?";

        try {
            jdoPM = (VersantPersistenceManager) gov.sir.fenrir.dao.impl.jdogenie.AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);

            ps.setString(1, formaPagoId);

            rs = ps.executeQuery();

            while (rs.next()) {
                CamposCaptura camposCaptura = new CamposCaptura();
                camposCaptura.setIdCamposCaptura(Integer.parseInt(rs.getString(1)));
                camposCaptura.setNombreCampo(rs.getString(2));
                camposCaptura.setCampoDestino(rs.getString(3));
                camposCaptura.setFormLabel(rs.getString(4));
                camposCaptura.setFormName(rs.getString(5));
                camposCapturaXFormaPagoList.add(camposCaptura);
            }
            rs.close();
            ps.close();

            jdoPM.currentTransaction().commit();

        } catch (NullPointerException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(this.getClass(), e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return camposCapturaXFormaPagoList;
    }

    public CanalesRecaudo getCanalRecaudoByID(int canalRecaudoId)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CanalesRecaudoEnhanced rta = null;

        CanalesRecaudoEnhancedPk crPk = new CanalesRecaudoEnhancedPk();
        crPk.idCanal = canalRecaudoId;

        if (canalRecaudoId > 0) {
            try {
                rta = (CanalesRecaudoEnhanced) pm.getObjectById(crPk, true);
                return (CanalesRecaudo) rta.toTransferObject();
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(this.getClass(), e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return null;
    }

    public TipoPago getTipoPagoByID(int tipoPagoId)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        TipoPagoEnhanced rta = null;

        TipoPagoEnhancedPk tpPk = new TipoPagoEnhancedPk();
        tpPk.idTipoDocPago = tipoPagoId;

        if (tipoPagoId > 0) {
            try {
                rta = (TipoPagoEnhanced) pm.getObjectById(tpPk, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOVariablesOperativasDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return (TipoPago) rta.toTransferObject();
    }

    public CuentasBancarias getCuentasBancariasByID(int cuentaBancariaId)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CuentasBancariasEnhanced rta = null;

        CuentasBancariasEnhancedPk cbPk = new CuentasBancariasEnhancedPk();
        cbPk.id = cuentaBancariaId;

        if (cuentaBancariaId > 0) {
            try {
                rta = (CuentasBancariasEnhanced) pm.getObjectById(cbPk, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOVariablesOperativasDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return (CuentasBancarias) rta.toTransferObject();
    }
//.................................................CARLOS TEST.......................................    

    public String getIdCtpByParamenters(String idTipoDocPago, String idCirculo, int idCanal, String idCb) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta;
        if(idCb != null ){
            consulta = "SELECT ID_CTP FROM SIR_NE_CIRCULO_TIPO_PAGO WHERE ID_TIPO_DOC_PAGO = ? AND ID_CIRCULO = ? AND CR_ID = ? AND CB_ID = ?";  
        }else{
            consulta = "SELECT ID_CTP FROM SIR_NE_CIRCULO_TIPO_PAGO WHERE ID_TIPO_DOC_PAGO = ? AND ID_CIRCULO = ? AND CR_ID = ?";  
        }
              
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, idTipoDocPago);
            ps.setString(2, idCirculo);
            ps.setString(3, String.valueOf(idCanal));
            if(idCb != null ){
                ps.setString(4, idCb);
            }            
            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getString("ID_CTP");
            }
            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }
//.................................................CARLOS TEST.......................................        

    public CirculoTipoPago getCirculoTipoPagoByID(int idCtp)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoTipoPagoEnhanced rta = null;

        CirculoTipoPagoEnhancedPk ctpPk = new CirculoTipoPagoEnhancedPk();
        ctpPk.idCirculoTipoPago = idCtp + "";

        if (idCtp > 0) {
            try {
                rta = (CirculoTipoPagoEnhanced) pm.getObjectById(ctpPk, true);
                return (CirculoTipoPago) rta.toTransferObject();
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                Log.getInstance().error(JDOPagosDAO.class, e);
                return null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOVariablesOperativasDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return null;
    }
    public boolean validacionCorreccion(String idDocumento) throws DAOException {       
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean rta = false;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM SIR_OP_CORRECCION_FORMA_PAGO WHERE ID_DOCUMENTO_PAGO = ? ";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocumento);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = true;
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }finally{
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            }catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    protected String getNoAprobacion(DocPagoGeneralEnhanced dp) throws DAOException {       
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT DCPG_NO_APROBACION FROM SIR_OP_DOCUMENTO_PAGO WHERE DCPG_NO_APROBACION = ? AND ID_BANCO = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, dp.getNoAprobacion());
            ps.setString(2, dp.getBanco().getIdBanco());
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("DCPG_NO_APROBACION");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }finally{
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            }catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
        protected DocPagoGeneralEnhanced getDocumentosPagoGeneralByConsignacion(
            DocPagoGeneralEnhanced dp,
            PersistenceManager pm)
            throws DAOException {

        Query query = null;

        DocPagoGeneralEnhanced rta = null;

        if (dp.getBanco() != null && dp.getNoConsignacion() != null && dp.getIdTipoDocPago() != 0) {
            try {
                query = pm.newQuery(DocPagoGeneralEnhanced.class);
                query.declareParameters("String idbanco, String consignacion, String idDocPago");
                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
                query.setFilter("banco.idBanco == idbanco && "
                        + "bancoFranquicia.idTipoFranquicia == " + dp.getBancoFranquicia().getIdTipoFranquicia() + " &&"
                        + "noConsignacion == consignacion && "
                        + "idTipoDocPago == idDocPago && "
                        + "ap.idDocumentoPago == this.idDocumentoPago && "
                        + "ap.idSolicitud == sol.idSolicitud && " //+ "t.anulado == \"N\" && "
                        + "sol.idSolicitud == t.solicitud.idSolicitud");

                Collection col = (Collection) query.execute(dp.getBanco().getIdBanco(), dp.getNoConsignacion(), dp.getIdTipoDocPago());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoGeneralEnhanced) iter.next();
                    }
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } finally {
                query.closeAll();
            }
        }else if (dp.getBancoFranquicia() != null && dp.getNoAprobacion() != null && dp.getIdTipoDocPago() != 0) {
            try {
                query = pm.newQuery(DocPagoGeneralEnhanced.class);
                query.declareParameters("String numeroaprobacion, String idbanco, String idDocPago");
                query.declareVariables("AplicacionPagoEnhanced ap; SolicitudEnhanced sol; TurnoEnhanced t");
                query.setFilter("this.noAprobacion == numeroaprobacion && "
                        + "this.bancoFranquicia.idBanco == idbanco && "
                        + "bancoFranquicia.idTipoFranquicia == " + dp.getBancoFranquicia().getIdTipoFranquicia() + " &&"
                        + "this.idTipoDocPago == idDocPago && "
                        + "ap.idDocumentoPago == this.idDocumentoPago &&  "
                        + "ap.idSolicitud == sol.idSolicitud && "
                        + "t.anulado == \"N\" && "
                        + "sol.idSolicitud == t.solicitud.idSolicitud");

            Collection col = (Collection) query.execute(dp.getNoAprobacion(), dp.getBancoFranquicia().getIdBanco(), dp.getIdTipoDocPago());

                if (col.size() == 0) {
                    rta = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        rta = (DocPagoGeneralEnhanced) iter.next();
                    }
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } finally {
                query.closeAll();
            }
        }
        return rta;
    }

    /**
     * 
     * @param idTurnoWorkFlow
     * @return
     * @throws DAOException 
     * @author DNilson Olaya Gómez - desarrollo3@tsg.net.co
     */    
    public boolean validarSiTurnoFueRadicadoXREL(String idTurnoWorkFlow) throws DAOException {       
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean rta = false;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT TRNO_REL FROM SIR_OP_TURNO WHERE TRNO_ID_WORKFLOW='"+idTurnoWorkFlow+"'";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                Turno turnoREL = new Turno();
                turnoREL.setTurnoREL(rs.getString(1));
                if(turnoREL.getTurnoREL() != null){
                    rta = true;
                }
            }
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }finally{
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            }catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }    

    
    public boolean numeroDeConsignacionEnUso(String idConsignacion, String idDocumentoPago) throws DAOException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean rta = false;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM SIR_OP_DOCUMENTO_PAGO DP, SIR_OP_APLICACION_PAGO AP WHERE AP.ID_DOCUMENTO_PAGO=DP.ID_DOCUMENTO_PAGO AND AP.ID_DOCUMENTO_PAGO<>? AND DP.DCPG_NO_CONSIGNACION=?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocumentoPago);
            ps.setString(2, idConsignacion);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            if(rs.next()){
                rta=true;
            }
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }finally{
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            }catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }

    
    public boolean datosTarjetaEnUso(String noTarjeta, String noAprobacion, String idDocumentoPago) throws DAOException {
         Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean rta = false;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM SIR_OP_DOCUMENTO_PAGO DP, SIR_OP_APLICACION_PAGO AP WHERE AP.ID_DOCUMENTO_PAGO=DP.ID_DOCUMENTO_PAGO AND AP.ID_DOCUMENTO_PAGO<>? AND DP.DCPG_NO_TARJETA=? AND DP.DCPG_NO_APROBACION=?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocumentoPago);
            ps.setString(2, noTarjeta);
            ps.setString(3, noAprobacion);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            if(rs.next()){
                rta=true;
            }
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }finally{
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            }catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
    public boolean numeroDeAprobacionEnUso(String noAprobacion, String idDocumentoPago) throws DAOException {
         Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean rta = false;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM SIR_OP_DOCUMENTO_PAGO DP, SIR_OP_APLICACION_PAGO AP WHERE AP.ID_DOCUMENTO_PAGO=DP.ID_DOCUMENTO_PAGO AND AP.ID_DOCUMENTO_PAGO<>? AND  DP.DCPG_NO_APROBACION=?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocumentoPago);
            ps.setString(2, noAprobacion);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            if(rs.next()){
                rta=true;
            }
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }finally{
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                }
            }catch (SQLException e) {
                Log.getInstance().error(JDOPagosDAO.class, e);
                Log.getInstance().error(JDOPagosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
}



