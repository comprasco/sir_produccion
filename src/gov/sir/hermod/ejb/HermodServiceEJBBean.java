
package gov.sir.hermod.ejb;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoNotarialPk;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocumentoPagoCorreccion;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PagoPk;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoReparto;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.RecursoPk;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.Reparto;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.RepartoNotarialPk;
import gov.sir.core.negocio.modelo.ReproduccionSellos;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.TextoImprimible;
import gov.sir.core.negocio.modelo.TextoImprimiblePk;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.TipoCalculo;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoCertificadoValidacion;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoDerechoReg;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;
import gov.sir.core.negocio.modelo.TipoRelacion;
import gov.sir.core.negocio.modelo.TipoTarifa;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoEjecucion;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.ValidacionNota;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegEngPk;
import gov.sir.core.negocio.modelo.ZonaNotarial;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
/**
 * @author : Carlos Mario Torre Urina
 * @casoMantis :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 * @change :Se abilita el uso de la clase
 */

import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Message;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.portal.modelo.Transaccion;

/**
 * @author Mc'Carthy Newball
 *
 * Clase cascaron EJB para el servicio Hermod HermodServiceBean
 *
 * @ejb:bean generate="true" name="ejb/HermodServiceEJB"
 * display-name="HermodService Session Bean" description="HermodService Session
 * Bean" type="Stateless" transaction-type="Bean"
 * jndi-name="ejb/HermodServiceEJB" view-type="both"
 *
 * @ejb.transaction type="Never"
 *
 * @oc4j:bean
 *
 * @ejb.security-identity use-caller-identity="true"
 */
public class HermodServiceEJBBean implements SessionBean, HermodServiceInterface {

    private static HermodService hermod = null;

    public HermodServiceEJBBean() {
        try {
            hermod = HermodService.getInstance();
        } catch (HermodException ex) {
            Logger.getLogger(HermodServiceEJBBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ejbCreate() throws CreateException, EJBException {
        // Write your code here
    }

    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext arg0) throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException {
        // TODO Auto-generated method stub

    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearSolicitud(gov.sir.core.negocio.modelo.Solicitud)
     * @ejb:interface-method view-type="both"
     */
    public Solicitud crearSolicitud(Solicitud sol) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Creando Solicitud");
        return hermod.crearSolicitud(sol);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#liquidar(gov.sir.core.negocio.modelo.Liquidacion)
     * @ejb:interface-method view-type="both"
     */
    public Liquidacion liquidar(Liquidacion liquidacion) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Liquidando");
        return hermod.liquidar(liquidacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#procesarPago(gov.sir.core.negocio.modelo.Pago,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Turno procesarPago(Pago pago, String estacion) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Procesando Pago");
        return hermod.procesarPago(pago, estacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#procesarPago(gov.sir.core.negocio.modelo.Pago,
     * java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     * @ejb:interface-method view-type="both"
     */
    public Turno procesarPago(Pago pago, String estacion, String impresora, Usuario user, Rol rol) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Procesando Pago");
        return hermod.procesarPago(pago, estacion, impresora, user, rol);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#procesarPago(gov.sir.core.negocio.modelo.Pago,
     * java.lang.String, java.lang.String, gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol, boolean)
     * @ejb:interface-method view-type="both"
     */
    public Turno procesarPago(Pago pago, String estacion, String impresora, Usuario user, Rol rol, boolean delegarUsuario) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Procesando Pago");
        return hermod.procesarPago(pago, estacion, impresora, user, rol, delegarUsuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarPago(gov.sir.core.negocio.modelo.Pago)
     * @ejb:interface-method view-type="both"
     */
    public Pago validarPago(Pago pago) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Validando Pago");
        return hermod.validarPago(pago);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumReciboPagoByID(gov.sir.core.negocio.modelo.Pago.PagoPk)
     * @ejb:interface-method view-type="both"
     */
    public String getNumReciboPagoByID(PagoPk pID) throws HermodException {
        return hermod.getNumReciboPagoByID(pID);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getStringByQuery(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public String getStringByQuery(String sql) throws HermodException {
        return hermod.getStringByQuery(sql);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getRespuestasSiguientes(gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public List getRespuestasSiguientes(Turno turno) throws HermodException {
        return hermod.getRespuestasSiguientes(turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getFasesSiguientes(gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public List getFasesSiguientes(Turno turno) throws HermodException {
        return hermod.getFasesSiguientes(turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getProcesosQueInicia(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getProcesosQueInicia(String id_rol) throws HermodException {
        return hermod.getProcesosQueInicia(id_rol);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnos(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol,
     * gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Fase, gov.sir.core.negocio.modelo.Circulo )
     * @ejb:interface-method view-type="both"
     */
    public List getTurnos(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Obteniendo Turnos");
        return hermod.getTurnos(estacion, rol, usuario, proceso, fase, circulo);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnos(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol,
     * gov.sir.core.negocio.modelo.Usuario, gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Fase, gov.sir.core.negocio.modelo.Circulo )
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosPMY(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Obteniendo Turnos");
        return hermod.getTurnosPMY(estacion, rol, usuario, proceso, fase, circulo);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isTurnoREL(String idWorkflow)
     * @ejb:interface-method view-type="both"
     */
    public Boolean isTurnoREL(String idWorkflow) throws HermodException{
        Log.getInstance().debug(HermodServiceEJBBean.class, "Validando Turno REL");
        return hermod.isTurnoREL(idWorkflow);
    }
    
      /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#currentStateNotaNotificada(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public String currentStateNotaNotificada(String idWorkflow) throws HermodException{
        return hermod.currentStateNotaNotificada(idWorkflow);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getFechaTurnoJuzgado(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public String getFechaTurnoJuzgado(String idWorkflow) throws HermodException{
        return hermod.getFechaTurnoJuzgado(idWorkflow);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isTurnoDevuelto(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public Boolean isTurnoDevuelto(String idWorkflow) throws HermodException{
        Log.getInstance().debug(HermodServiceEJBBean.class, "Validando Turno Devuelto");
        return hermod.isTurnoDevuelto(idWorkflow);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getFases(org.auriga.core.modelo.transferObjects.Rol,
     * gov.sir.core.negocio.modelo.Proceso)
     * @ejb:interface-method view-type="both"
     */
    public List getFases(Rol rol, Proceso proceso) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Obteniendo Fases");
        return hermod.getFases(rol, proceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getProcesos(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getProcesos(String id_rol) throws HermodException {

        Log.getInstance().debug(HermodServiceEJBBean.class, "Obteniendo Procesos");
        return hermod.getProcesos(id_rol);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurno(gov.sir.core.negocio.modelo.TurnoPk turnoId) throws HermodException {
        return hermod.getTurno(turnoId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnobyWF(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnobyWF(String turnoIdWf) throws HermodException {
        return hermod.getTurnobyWF(turnoIdWf);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocumentoPagoBySolicitud(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getDocumentoPagoBySolicitud(String solicitud) throws HermodException {
        return hermod.getDocumentoPagoBySolicitud(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocPagoConsignacion(gov.sir.core.negocio.modelo.DocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public DocPagoConsignacion getDocPagoConsignacion(DocumentoPago docPago) throws HermodException {
        return hermod.getDocPagoConsignacion(docPago);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocPagoCheque(gov.sir.core.negocio.modelo.DocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public DocPagoCheque getDocPagoCheque(DocumentoPago docPago) throws HermodException {
        return hermod.getDocPagoCheque(docPago);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocPagoConsignacion(String)
     * @ejb:interface-method view-type="both"
     */
    public DocPagoConsignacion getDocPagoConsignacion(String noConsignacion) throws HermodException {
        return hermod.getDocPagoConsignacion(noConsignacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocPagoCheque(String)
     * @ejb:interface-method view-type="both"
     */
    public DocPagoCheque getDocPagoCheque(String noCheque) throws HermodException {
        return hermod.getDocPagoCheque(noCheque);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearDocumentoPago(gov.sir.core.negocio.modelo.DocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public DocumentoPago crearDocumentoPago(DocumentoPago dp) throws HermodException {
        return hermod.crearDocumentoPago(dp);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addNotaToTurno(gov.sir.core.negocio.modelo.Nota,
     * gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.NotaPk addNotaToTurno(Nota nota, gov.sir.core.negocio.modelo.TurnoPk tId) throws HermodException {
        return hermod.addNotaToTurno(nota, tId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTestamentoToSolicitudRegistro(TurnoPk
     * tid, Testamento testamento)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTestamentoToSolicitudRegistro(TurnoPk tid, Testamento testamento) throws HermodException {
        return hermod.addTestamentoToSolicitudRegistro(tid, testamento);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumeroMaximoImpresiones(org.auriga.core.modelo.transferObjects.Rol,
     * gov.sir.core.negocio.modelo.Proceso)
     * @ejb:interface-method view-type="both"
     */
    public int getNumeroMaximoImpresiones(Rol rol, Proceso p) throws HermodException {
        return hermod.getNumeroMaximoImpresiones(rol, p);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateBusquedaInSolicitudConsulta(gov.sir.core.negocio.modelo.SolicitudConsulta,
     * gov.sir.core.negocio.modelo.Busqueda)
     * @ejb:interface-method view-type="both"
     */
    public SolicitudConsulta updateBusquedaInSolicitudConsulta(SolicitudConsulta solConsulta, Busqueda b) throws HermodException {
        return hermod.updateBusquedaInSolicitudConsulta(solConsulta, b);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addFolioToSolicitudConsulta(gov.sir.core.negocio.modelo.SolicitudConsulta,
     * gov.sir.core.negocio.modelo.SolicitudFolio)
     * @ejb:interface-method view-type="both"
     */
    public SolicitudConsulta addFolioToSolicitudConsulta(SolicitudConsulta solConsulta, SolicitudFolio solFolio) throws HermodException {
        return hermod.addFolioToSolicitudConsulta(solConsulta, solFolio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addBusquedaToSolicitudConsulta(gov.sir.core.negocio.modelo.SolicitudConsulta,
     * gov.sir.core.negocio.modelo.Busqueda)
     * @ejb:interface-method view-type="both"
     */
    public Solicitud addBusquedaToSolicitudConsulta(SolicitudConsulta solConsulta, Busqueda busc) throws HermodException {
        return hermod.addBusquedaToSolicitudConsulta(solConsulta, busc);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCiudadanoToSolicitudConsulta(gov.sir.core.negocio.modelo.SolicitudConsulta,
     * gov.sir.core.negocio.modelo.Ciudadano)
     * @ejb:interface-method view-type="both"
     */
    public Solicitud addCiudadanoToSolicitudConsulta(SolicitudConsulta solConsulta, Ciudadano ciud) throws HermodException {
        return hermod.addCiudadanoToSolicitudConsulta(solConsulta, ciud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSubtiposAtencionByUsuarios(java.util.List,
     * gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getSubtiposAtencionByUsuarios(List logins, Circulo circulo) throws HermodException {
        return hermod.getSubtiposAtencionByUsuarios(logins, circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addProcesoReparto(gov.sir.core.negocio.modelo.ProcesoReparto)
     * @ejb:interface-method view-type="both"
     */
    public ProcesoReparto addProcesoReparto(ProcesoReparto prR) throws HermodException {
        return hermod.addProcesoReparto(prR);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getLastReparto(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public Reparto getLastReparto(gov.sir.core.negocio.modelo.TurnoPk turnoId) throws HermodException {
        return hermod.getLastReparto(turnoId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUsuariosBySubtipoAtencion(java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public Map getUsuariosBySubtipoAtencion(List usuarios) throws HermodException {
        return hermod.getUsuariosBySubtipoAtencion(usuarios);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCategoriaClasificacionMinuta(gov.sir.core.negocio.modelo.Minuta)
     * @ejb:interface-method view-type="both"
     */
    public Categoria getCategoriaClasificacionMinuta(Minuta minuta) throws HermodException {
        return hermod.getCategoriaClasificacionMinuta(minuta);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addMinutaToSolicitudReparto(gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.Minuta)
     * @ejb:interface-method view-type="both"
     */
    public SolicitudRepartoNotarial addMinutaToSolicitudReparto(Solicitud solicitud, Minuta minuta) throws HermodException {
        return hermod.addMinutaToSolicitudReparto(solicitud, minuta);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#anularMinuta(gov.sir.core.negocio.modelo.Minuta,
     * gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean anularMinuta(Minuta min, Usuario usuario) throws HermodException {
        return hermod.anularMinuta(min, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#modificarMinuta(gov.sir.core.negocio.modelo.Minuta,
     * boolean, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Minuta modificarMinuta(Minuta min, boolean generarAuditoria, Usuario usuario) throws HermodException {
        return hermod.modificarMinuta(min, generarAuditoria, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasNoAsignadasByVereda(gov.sir.core.negocio.modelo.Vereda.VeredaPk)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasNoAsignadasByVereda(VeredaPk vereda) throws HermodException {
        return hermod.getMinutasNoAsignadasByVereda(vereda);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addOficinaOrigenToCategoria(gov.sir.core.negocio.modelo.Categoria.CategoriaPk,
     * gov.sir.core.negocio.modelo.OficinaOrigen)
     * @ejb:interface-method view-type="both"
     */
    public boolean addOficinaOrigenToCategoria(gov.sir.core.negocio.modelo.CategoriaPk cat, OficinaOrigen oficina) throws HermodException {
        return hermod.addOficinaOrigenToCategoria(cat, oficina);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateCategoria(gov.sir.core.negocio.modelo.Categoria.CategoriaPk,
     * gov.sir.core.negocio.modelo.Categoria)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateCategoria(gov.sir.core.negocio.modelo.CategoriaPk cat, Categoria nuevosDatos) throws HermodException {
        return hermod.updateCategoria(cat, nuevosDatos);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutaByTurnoWF(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Minuta getMinutaByTurnoWF(String turnoIdWf) throws HermodException {
        return hermod.getMinutaByTurnoWF(turnoIdWf);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasNoAsignadasByVereda(gov.sir.core.negocio.modelo.Vereda.VeredaPk)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasByTurnoWF(String turnoIdWf) throws HermodException {
        return hermod.getMinutasByTurnoWF(turnoIdWf);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#realizarRepartoCirculo(gov.sir.core.negocio.modelo.Circulo,
     * gov.sir.core.negocio.modelo.Usuario, boolean, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String idExtraordinario) throws HermodException {
        return hermod.realizarRepartoCirculo(circ, usuario, tipo, idExtraordinario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposAlcanceGeografico()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposAlcanceGeografico() throws HermodException {
        return hermod.getTiposAlcanceGeografico();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoAlcanceGeografico(gov.sir.core.negocio.modelo.AlcanceGeografico)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoAlcanceGeografico(AlcanceGeografico ag) throws HermodException {
        return hermod.addTipoAlcanceGeografico(ag);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposAccionNotarial()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposAccionNotarial() throws HermodException {
        return hermod.getTiposAccionNotarial();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addAccionNotarial(gov.sir.core.negocio.modelo.AccionNotarial,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean addAccionNotarial(AccionNotarial an, Usuario usuario) throws HermodException {
        return hermod.addAccionNotarial(an, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#editarAccionNotarial(gov.sir.core.negocio.modelo.AccionNotarial,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean editarAccionNotarial(AccionNotarial an, Usuario usuario) throws HermodException {
        return hermod.editarAccionNotarial(an, usuario);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getBancos()
     * @ejb:interface-method view-type="both"
     */
    public List getBancos() throws HermodException {
        return hermod.getBancos();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getBancoByID(java.lang.String
     * idBanco))
     * @ejb:interface-method view-type="both"
     */
    public Banco getBancoByID(String idBanco) throws HermodException {
        return hermod.getBancoByID(idBanco);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addBanco(gov.sir.core.negocio.modelo.Banco)
     * @ejb:interface-method view-type="both"
     */
    public boolean addBanco(Banco banco) throws HermodException {
        return hermod.addBanco(banco);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCausalesReimpresion()
     * @ejb:interface-method view-type="both"
     */
    public List getCausalesReimpresion() throws HermodException {
        return hermod.getCausalesReimpresion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCausalesRestitucion()
     * @ejb:interface-method view-type="both"
     */
    public List getCausalesRestitucion() throws HermodException {
        return hermod.getCausalesRestitucion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCausalRestitucion(gov.sir.core.negocio.modelo.CausalRestitucion)
     * @ejb:interface-method view-type="both"
     */
    public boolean addCausalRestitucion(CausalRestitucion cr) throws HermodException {
        return hermod.addCausalRestitucion(cr);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarCausalRestitucion(gov.sir.core.negocio.modelo.CausalRestitucion)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarCausalRestitucion(CausalRestitucion valNota) throws HermodException {
        return hermod.eliminarCausalRestitucion(valNota);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getOPLookupCodes(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getOPLookupCodes(String tipo) throws HermodException {
        return hermod.getOPLookupCodes(tipo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addOPLookupCode(gov.sir.core.negocio.modelo.OPLookupCodes)
     * @ejb:interface-method view-type="both"
     */
    public boolean addOPLookupCode(OPLookupCodes luc) throws HermodException {
        return hermod.addOPLookupCode(luc);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateOPLookupCode(gov.sir.core.negocio.modelo.OPLookupCodes,
     * gov.sir.core.negocio.modelo.OPLookupCodes)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateOPLookupCode(OPLookupCodes datoAEditar, OPLookupCodes dato) throws HermodException {
        return hermod.updateOPLookupCode(datoAEditar, dato);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getOPLookupTypes()
     * @ejb:interface-method view-type="both"
     */
    public List getOPLookupTypes() throws HermodException {
        return hermod.getOPLookupTypes();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addOPLookupType(gov.sir.core.negocio.modelo.OPLookupTypes)
     * @ejb:interface-method view-type="both"
     */
    public boolean addOPLookupType(OPLookupTypes lut) throws HermodException {
        return hermod.addOPLookupType(lut);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateOPLookupType(gov.sir.core.negocio.modelo.OPLookupTypes)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateOPLookupType(OPLookupTypes datoAEditar, OPLookupTypes dato) throws HermodException {
        return hermod.updateOPLookupType(datoAEditar, dato);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposActo()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposActo() throws HermodException {
        return hermod.getTiposActo();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoActo(TipoActoPk
     * tid)
     * @ejb:interface-method view-type="both"
     */
    public TipoActo getTipoActo(TipoActoPk tid) throws HermodException {
        return hermod.getTipoActo(tid);
    }

    /*
    * @autor          : JATENCIA 
    * @mantis        : 0015082 
    * @Requerimiento : 027_589_Acto_liquidación_copias 
    * @descripcion   : Se declara el metodo en interfacez
     */
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposActoDos()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposActoDos() throws HermodException {
        return hermod.getTiposActoDos();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoActoDos(TipoActoPk
     * tid)
     * @ejb:interface-method view-type="both"
     */
    public TipoActo getTipoActoDos(TipoActoPk tid) throws HermodException {
        return hermod.getTipoActoDos(tid);
    }

    /* - Fin del bloque - */
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoActo(gov.sir.core.negocio.modelo.TipoActo,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoActo(TipoActo tac, Usuario usuario) throws HermodException {
        return hermod.addTipoActo(tac, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateTipoActo(gov.sir.core.negocio.modelo.TipoActo,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateTipoActo(TipoActo tac, Usuario usuario) throws HermodException {
        return hermod.updateTipoActo(tac, usuario);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposCalculo()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposCalculo() throws HermodException {
        return hermod.getTiposCalculo();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoCalculo(gov.sir.core.negocio.modelo.TipoCalculo)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoCalculo(TipoCalculo tc) throws HermodException {
        return hermod.addTipoCalculo(tc);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposFotocopia()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposFotocopia() throws HermodException {
        return hermod.getTiposFotocopia();
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposFotocopia()
     * @ejb:interface-method view-type="both"
     */
    public List getCategoriasNotarias() throws HermodException {
        return hermod.getCategoriasNotarias();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoFotocopia(gov.sir.core.negocio.modelo.TipoFotocopia)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoFotocopia(TipoFotocopia fot) throws HermodException {
        return hermod.addTipoFotocopia(fot);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoFotocopia(gov.sir.core.negocio.modelo.ReproduccionSellos)
     * @ejb:interface-method view-type="both"
     */
    public boolean CreateReproduccionSellosReg(ReproduccionSellos reproduccion) throws HermodException {
        return hermod.CreateReproduccionSellosReg(reproduccion);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoFotocopia(gov.sir.core.negocio.modelo.ReproduccionSellos)
     * @ejb:interface-method view-type="both"
     */
    public List getListReproduccionSellos(String idTurno, String Circulo, int Activo)throws HermodException {
        
        Log.getInstance().debug(HermodServiceEJBBean.class, "Obteniendo Datos");
        
        return hermod.getListReproduccionSellos(idTurno, Circulo, Activo);
    }
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposImpuesto()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposImpuesto() throws HermodException {
        return hermod.getTiposImpuesto();
    }

    /**
     * @author Fernando Padilla Velez
     * @change Modificado para el caso MANTIS 135_141_Impuesto Meta.
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposImpuestoCirculo()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposImpuestoCirculo() throws HermodException {
        return hermod.getTiposImpuestoCirculo();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoImpuesto(gov.sir.core.negocio.modelo.TipoImpuesto)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoImpuesto(TipoImpuesto timp) throws HermodException {
        return hermod.addTipoImpuesto(timp);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getSubTiposAtencion()
     * @ejb:interface-method view-type="both"
     */
    public List getSubTiposAtencion() throws HermodException {
        return hermod.getSubTiposAtencion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSubTiposAtencionCompleto()
     * @ejb:interface-method view-type="both"
     */
    public List getSubTiposAtencionCompleto() throws HermodException {
        return hermod.getSubTiposAtencionCompleto();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addSubTipoAtencion(gov.sir.core.negocio.modelo.SubtipoAtencion)
     * @ejb:interface-method view-type="both"
     */
    public boolean addSubTipoAtencion(SubtipoAtencion sat) throws HermodException {
        return hermod.addSubTipoAtencion(sat);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateSubTipoAtencion(gov.sir.core.negocio.modelo.SubtipoAtencion)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateSubTipoAtencion(SubtipoAtencion sat) throws HermodException {
        return hermod.updateSubTipoAtencion(sat);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCalificadores(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getCalificadoresSubtipoAtencion(Circulo cir, SubtipoAtencion sub) throws HermodException {
        return hermod.getCalificadoresSubtipoAtencion(cir, sub);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addOrdenSubtipoAtencion(gov.sir.core.negocio.modelo.Usuario,
     * gov.sir.core.negocio.modelo.SubtipoAtencion)
     * @ejb:interface-method view-type="both"
     */
    public boolean addOrdenSubtipoAtencion(SubtipoAtencion sub, Usuario usu, String orden, Circulo cir) throws HermodException {
        return hermod.addOrdenSubtipoAtencion(sub, usu, orden, cir);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeOrdenSubtipoAtencion(gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion)
     * @ejb:interface-method view-type="both"
     */
    public boolean removeOrdenSubtipoAtencion(UsuarioSubtipoAtencion usuSubtipoAtencion, Circulo cir) throws HermodException {
        return hermod.removeOrdenSubtipoAtencion(usuSubtipoAtencion, cir);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSubTiposSolicitud()
     * @ejb:interface-method view-type="both"
     */
    public List getSubTiposSolicitud() throws HermodException {
        return hermod.getSubTiposSolicitud();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addSubTipoSolicitud(gov.sir.core.negocio.modelo.SubtipoSolicitud)
     * @ejb:interface-method view-type="both"
     */
    public boolean addSubTipoSolicitud(SubtipoSolicitud sts) throws HermodException {
        return hermod.addSubTipoSolicitud(sts);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateSubTipoSolicitud(gov.sir.core.negocio.modelo.SubtipoSolicitud)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateSubTipoSolicitud(SubtipoSolicitud sts) throws HermodException {
        return hermod.updateSubTipoSolicitud(sts);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposCertificado()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposCertificado() throws HermodException {
        return hermod.getTiposCertificado();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposCertificadosIndividuales()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposCertificadosIndividuales() throws HermodException {
        return hermod.getTiposCertificadosIndividuales();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoCertificado(gov.sir.core.negocio.modelo.TipoCertificado)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoCertificado(TipoCertificado tcert) throws HermodException {
        return hermod.addTipoCertificado(tcert);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposConsulta()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposConsulta() throws HermodException {
        return hermod.getTiposConsulta();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoConsulta(gov.sir.core.negocio.modelo.TipoConsulta)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoConsulta(TipoConsulta tcons) throws HermodException {
        return hermod.addTipoConsulta(tcons);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposRecepcionPeticion()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposRecepcionPeticion() throws HermodException {
        return hermod.getTiposRecepcionPeticion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoRecepcionPeticion(gov.sir.core.negocio.modelo.TipoRecepcionPeticion)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoRecepcionPeticion(TipoRecepcionPeticion trp) throws HermodException {
        return hermod.addTipoRecepcionPeticion(trp);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposTarifas()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposTarifas() throws HermodException {
        return hermod.getTiposTarifas();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoTarifa(gov.sir.core.negocio.modelo.Tarifa)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoTarifa(Tarifa ttar) throws HermodException {
        return hermod.addTipoTarifa(ttar);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTarifas(gov.sir.core.negocio.modelo.TipoTarifa)
     * @ejb:interface-method view-type="both"
     */
    public List getTarifas(TipoTarifa tipoTar) throws HermodException {
        return hermod.getTarifas(tipoTar);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTarifa(gov.sir.core.negocio.modelo.Tarifa)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTarifa(Tarifa tar) throws HermodException {
        return hermod.addTarifa(tar);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValidacionesSolicitud(gov.sir.core.negocio.modelo.Solicitud)
     * @ejb:interface-method view-type="both"
     */
    public List getValidacionesSolicitud(Solicitud solicitud) throws HermodException {
        return hermod.getValidacionesSolicitud(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getRangoAceptacionPago(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public double getRangoAceptacionPago(String tipo) throws HermodException {
        return hermod.getRangoAceptacionPago(tipo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addFolioToTurno(java.lang.String,
     * gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.SolicitudFolioPk addFolioToTurno(String matricula, gov.sir.core.negocio.modelo.TurnoPk tID, Usuario user) throws HermodException {
        return hermod.addFolioToTurno(matricula, tID, user);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeFolioFromTurno(java.lang.String,
     * gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean removeFolioFromTurno(String matricula, gov.sir.core.negocio.modelo.TurnoPk tID) throws HermodException {
        return hermod.removeFolioFromTurno(matricula, tID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#registrarMatriculaEliminadaTurno(java.lang.String,
     * gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean registrarMatriculaEliminadaTurno(String matricula, gov.sir.core.negocio.modelo.TurnoPk tID) throws HermodException {
        return hermod.registrarMatriculaEliminadaTurno(matricula, tID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCambioMatriculaAuditoria(java.lang.String,
     * java.lang.String, gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean addCambioMatriculaAuditoria(String folioViejo, String folionuevo, TurnoPk tID, Usuario user) throws HermodException {
        return hermod.addCambioMatriculaAuditoria(folioViejo, folionuevo, tID, user);
    }

    /**
     * @author : Julio Alcázar Rivas Caso Mantis : 02359
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCambioMatriculaAuditoria(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean getCambioMatriculaAuditoria(TurnoPk tID) throws HermodException {
        return hermod.getCambioMatriculaAuditoria(tID);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposDocumento()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposDocumento() throws HermodException {
        return hermod.getTiposDocumento();
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTipoDocNatural() 
     * @ejb:interface-method view-type="both"
     */
    public List getTipoDocNatural() throws HermodException {
        return hermod.getTipoDocNatural();
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getControlMatriculaTurno(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List getControlMatriculaTurno(String turnoID) throws HermodException {
        return hermod.getControlMatriculaTurno(turnoID);
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTipoDocJuridico() 
     * @ejb:interface-method view-type="both"
     */
    public List getTipoDocJuridico() throws HermodException {
        return hermod.getTipoDocJuridico();
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getModalidad()
     * @ejb:interface-method view-type="both"
     */
    public List getModalidad() throws HermodException {
        return hermod.getModalidad();
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getDeterminacionInm()
     * @ejb:interface-method view-type="both"
     */
    public List getDeterminacionInm() throws HermodException {
        return hermod.getDeterminacionInm();
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTipoPersona()
     * @ejb:interface-method view-type="both"
     */
    public List getTipoPersona() throws HermodException {
        return hermod.getTipoPersona();
    }
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTipoSexo()
     * @ejb:interface-method view-type="both"
     */
    public List getTipoSexo() throws HermodException {
        return hermod.getTipoSexo();
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSucursalesBanco(gov.sir.core.negocio.modelo.Banco.BancoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getSucursalesBanco(gov.sir.core.negocio.modelo.BancoPk idBanco) throws HermodException {
        return hermod.getSucursalesBanco(idBanco);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addSucursalBanco(gov.sir.core.negocio.modelo.SucursalBanco)
     * @ejb:interface-method view-type="both"
     */
    public boolean addSucursalBanco(SucursalBanco sucursal) throws HermodException {
        return hermod.addSucursalBanco(sucursal);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoDerechoRegistral()
     * @ejb:interface-method view-type="both"
     */
    public List getTipoDerechoRegistral() throws HermodException {
        return hermod.getTipoDerechoRegistral();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoDerechoRegistral(gov.sir.core.negocio.modelo.TipoDerechoReg)
     * @ejb:interface-method view-type="both"
     */
    public boolean addTipoDerechoRegistral(TipoDerechoReg tipo) throws HermodException {
        return hermod.addTipoDerechoRegistral(tipo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCategoriasReparto(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getCategoriasReparto(String orden) throws HermodException {
        return hermod.getCategoriasReparto(orden);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCategoriaReparto(gov.sir.core.negocio.modelo.Categoria,
     * Usuario usario)
     * @ejb:interface-method view-type="both"
     */
    public boolean addCategoriaReparto(Categoria categoria, Usuario usuario) throws HermodException {
        return hermod.addCategoriaReparto(categoria, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateCategoriaReparto(gov.sir.core.negocio.modelo.Categoria,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateCategoriaReparto(Categoria categoria, Usuario usuario) throws HermodException {
        return hermod.updateCategoriaReparto(categoria, usuario);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposNotas()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposNotas() throws HermodException {
        return hermod.getTiposNotas();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposNotasProceso(gov.sir.core.negocio.modelo.Proceso.ProcesoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposNotasProceso(gov.sir.core.negocio.modelo.ProcesoPk proceso) throws HermodException {
        return hermod.getTiposNotasProceso(proceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getLiquidacionActo(gov.sir.core.negocio.modelo.Acto)
     * @ejb:interface-method view-type="both"
     */
    public Acto getLiquidacionActo(Acto acto) throws HermodException {
        return hermod.getLiquidacionActo(acto);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validacionActo(gov.sir.core.negocio.modelo.Acto,
     * int i)
     * @ejb:interface-method view-type="both"
     */
    public boolean validacionActo(Acto acto, int i) throws HermodException {
        return hermod.validacionActo(acto, i);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getEstacionesRecibo()
     * @ejb:interface-method view-type="both"
     */
    public List getEstacionesRecibo() throws HermodException {
        return hermod.getEstacionesRecibo();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEstacionRecibo(gov.sir.core.negocio.modelo.EstacionRecibo.EstacionReciboPk)
     * @ejb:interface-method view-type="both"
     */
    public EstacionRecibo getEstacionRecibo(gov.sir.core.negocio.modelo.EstacionReciboPk oid) throws HermodException {
        return hermod.getEstacionRecibo(oid);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEstacionRecibo(gov.sir.core.negocio.modelo.EstacionRecibo.EstacionReciboPk,
     * long)
     * @ejb:interface-method view-type="both"
     */
    public EstacionRecibo getEstacionRecibo(gov.sir.core.negocio.modelo.EstacionReciboPk oid, long numeroProceso) throws HermodException {
        return hermod.getEstacionRecibo(oid, numeroProceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setEstacionRecibo(gov.sir.core.negocio.modelo.Circulo.CirculoPk,
     * gov.sir.core.negocio.modelo.EstacionRecibo,
     * gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void setEstacionRecibo(gov.sir.core.negocio.modelo.CirculoPk circuloID, EstacionRecibo sRecibo, Usuario user) throws HermodException {
        hermod.setEstacionRecibo(circuloID, sRecibo, user);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#resetUltimoNumeroEstacionRecibo(gov.sir.core.negocio.modelo.EstacionRecibo.EstacionReciboPk,
     * long)
     * @ejb:interface-method view-type="both"
     */
    public boolean resetUltimoNumeroEstacionRecibo(gov.sir.core.negocio.modelo.EstacionReciboPk oid, long ultimoNumeroActualizado) throws HermodException {
        return hermod.resetUltimoNumeroEstacionRecibo(oid, ultimoNumeroActualizado);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNextNumeroRecibo(gov.sir.core.negocio.modelo.EstacionRecibo.EstacionReciboPk,
     * gov.sir.core.negocio.modelo.Usuario, long )
     * @ejb:interface-method view-type="both"
     */
    public long getNextNumeroRecibo(gov.sir.core.negocio.modelo.EstacionReciboPk oid, Usuario user, long idProceso) throws HermodException {
        return hermod.getNextNumeroRecibo(oid, user, idProceso);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#finalizar()
     * @ejb:interface-method view-type="both"
     */
    public void finalizar() throws HermodException {
        hermod.finalizar();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setAnotacionestoSolicitud(long,
     * gov.sir.core.negocio.modelo.Solicitud)
     * @ejb:interface-method view-type="both"
     */
    public boolean setAnotacionestoSolicitud(long numAnotaciones, Solicitud solicitud) throws HermodException {
        return hermod.setAnotacionestoSolicitud(numAnotaciones, solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValidacionesCertificado(gov.sir.core.negocio.modelo.TipoCertificado.TipoCertificadoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getValidacionesCertificado(gov.sir.core.negocio.modelo.TipoCertificadoPk tipoId) throws HermodException {
        return hermod.getValidacionesCertificado(tipoId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarMatriculas(java.util.List,java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarMatriculas(List validaciones, List matriculas) throws HermodException {
        return hermod.validarMatriculas(validaciones, matriculas);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValidacionesRegistro()
     * @ejb:interface-method view-type="both"
     */
    public List getValidacionesRegistro() throws HermodException {
        return hermod.getValidacionesRegistro();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValidacionesRegistroTurnoManual()
     * @ejb:interface-method view-type="both"
     */
    public List getValidacionesRegistroTurnoManual() throws HermodException {
        return hermod.getValidacionesRegistroTurnoManual();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnoSinAnotaciones(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoSinAnotaciones(gov.sir.core.negocio.modelo.TurnoPk turnoId) throws HermodException {
        return hermod.getTurnoSinAnotaciones(turnoId);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposPago()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposPago() throws HermodException {
        return hermod.getTiposPago();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCirculoTiposPago(gov.sir.core.negocio.modelo.Circulo.CirculoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getCirculoTiposPago(gov.sir.core.negocio.modelo.CirculoPk cirID) throws HermodException {
        return hermod.getCirculoTiposPago(cirID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCirculoTipoPago(gov.sir.core.negocio.modelo.CirculoTipoPago)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.CirculoTipoPagoPk addCirculoTipoPago(CirculoTipoPago dato) throws HermodException {
        return hermod.addCirculoTipoPago(dato);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeCirculoTipoPago(gov.sir.core.negocio.modelo.CirculoTipoPago)
     * @ejb:interface-method view-type="both"
     */
    public void removeCirculoTipoPago(CirculoTipoPago dato) throws HermodException {
        hermod.removeCirculoTipoPago(dato);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCirculoProceso(gov.sir.core.negocio.modelo.CirculoProceso.CirculoProcesoPk)
     * @ejb:interface-method view-type="both"
     */
    public CirculoProceso getCirculoProceso(gov.sir.core.negocio.modelo.CirculoProcesoPk cpID) throws HermodException {
        return hermod.getCirculoProceso(cpID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#realizarRestitucionRepartoNotarial(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Hashtable realizarRestitucionRepartoNotarial(String idSolicitud) throws HermodException {
        return hermod.realizarRestitucionRepartoNotarial(idSolicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setNumMaxBusquedasToSolicitudConsulta(gov.sir.core.negocio.modelo.SolicitudConsulta,
     * int)
     * @ejb:interface-method view-type="both"
     */
    public boolean setNumMaxBusquedasToSolicitudConsulta(SolicitudConsulta solicitud, int numMaximo) throws HermodException {
        return hermod.setNumMaxBusquedasToSolicitudConsulta(solicitud, numMaximo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addResolucionToSolicitudRestitucion(gov.sir.core.negocio.modelo.SolicitudRestitucionReparto,
     * java.lang.String, java.lang.String, java.util.Date)
     * @ejb:interface-method view-type="both"
     */
    public boolean addResolucionToSolicitudRestitucion(SolicitudRestitucionReparto solicitud, String resolucion, String observaciones, Date fechaResolucion) throws HermodException {
        return hermod.addResolucionToSolicitudRestitucion(solicitud, resolucion, observaciones, fechaResolucion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#rechazarSolicitudRestitucion(gov.sir.core.negocio.modelo.SolicitudRestitucionReparto)
     * @ejb:interface-method view-type="both"
     */
    public boolean rechazarSolicitudRestitucion(SolicitudRestitucionReparto solicitud) throws HermodException {
        return hermod.rechazarSolicitudRestitucion(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarCategoria(gov.sir.core.negocio.modelo.Categoria,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarCategoria(Categoria categoria, Usuario usuario) throws HermodException {
        return hermod.eliminarCategoria(categoria, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarAlcanceGeografico(gov.sir.core.negocio.modelo.AlcanceGeografico)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarAlcanceGeografico(AlcanceGeografico alcance) throws HermodException {
        return hermod.eliminarAlcanceGeografico(alcance);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoFotocopia(gov.sir.core.negocio.modelo.TipoFotocopia)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoFotocopia(TipoFotocopia tipoFot) throws HermodException {
        return hermod.eliminarTipoFotocopia(tipoFot);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoCalculo(gov.sir.core.negocio.modelo.TipoCalculo)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoCalculo(TipoCalculo tipoCalc) throws HermodException {
        return hermod.eliminarTipoCalculo(tipoCalc);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoDerechoRegistral(gov.sir.core.negocio.modelo.TipoDerechoReg)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoDerechoRegistral(TipoDerechoReg tipoDerecho) throws HermodException {
        return hermod.eliminarTipoDerechoRegistral(tipoDerecho);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarAccionNotarial(gov.sir.core.negocio.modelo.AccionNotarial,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarAccionNotarial(AccionNotarial accion, Usuario usuario) throws HermodException {
        return hermod.eliminarAccionNotarial(accion, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addRangoFoliosToSolicitudRegistro(java.lang.String,
     * java.lang.String, gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.Usuario, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List addRangoFoliosToSolicitudRegistro(String matIncial, String matFinal, Solicitud solicitud, Usuario user, boolean validarAsociar) throws HermodException {
        return hermod.addRangoFoliosToSolicitudRegistro(matIncial, matFinal, solicitud, user, validarAsociar);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateEncabezadoInSolicitud(gov.sir.core.negocio.modelo.SolicitudRegistro)
     * @ejb:interface-method view-type="both"
     * @author Cesar Ramírez
     * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO Se recibe el usuario
     * como parámetro.
     *
     */
    public SolicitudRegistro updateEncabezadoInSolicitud(SolicitudRegistro solReg, Usuario usuario) throws HermodException {
        return hermod.updateEncabezadoInSolicitud(solReg, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarBanco(gov.sir.core.negocio.modelo.Banco)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarBanco(Banco banco) throws HermodException {
        return hermod.eliminarBanco(banco);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarSucursalBanco(gov.sir.core.negocio.modelo.SucursalBanco)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarSucursalBanco(SucursalBanco sucursal) throws HermodException {
        return hermod.eliminarSucursalBanco(sucursal);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTarifa(gov.sir.core.negocio.modelo.Tarifa)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTarifa(Tarifa tarifa) throws HermodException {
        return hermod.eliminarTarifa(tarifa);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoTarifa(gov.sir.core.negocio.modelo.TipoTarifa)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoTarifa(TipoTarifa tipoTarifa) throws HermodException {
        return hermod.eliminarTipoTarifa(tipoTarifa);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarSubtipoSolicitud(gov.sir.core.negocio.modelo.SubtipoSolicitud)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarSubtipoSolicitud(SubtipoSolicitud subtipoSolicitud) throws HermodException {
        return hermod.eliminarSubtipoSolicitud(subtipoSolicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarLookupType(gov.sir.core.negocio.modelo.OPLookupTypes)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarLookupType(OPLookupTypes type) throws HermodException {
        return hermod.eliminarLookupType(type);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarLookupCode(gov.sir.core.negocio.modelo.OPLookupCodes)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarLookupCode(OPLookupCodes code) throws HermodException {
        return hermod.eliminarLookupCode(code);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoImpuesto(gov.sir.core.negocio.modelo.TipoImpuesto)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoImpuesto(TipoImpuesto impuesto) throws HermodException {
        return hermod.eliminarTipoImpuesto(impuesto);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarSubtipoAtencion(gov.sir.core.negocio.modelo.SubtipoAtencion)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarSubtipoAtencion(SubtipoAtencion atencion) throws HermodException {
        return hermod.eliminarSubtipoAtencion(atencion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoActo(gov.sir.core.negocio.modelo.TipoActo,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoActo(TipoActo acto, Usuario usuario) throws HermodException {
        return hermod.eliminarTipoActo(acto, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#consultarEstacionesReciboPorCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List consultarEstacionesReciboPorCirculo(Circulo circulo) throws HermodException {
        return hermod.consultarEstacionesReciboPorCirculo(circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarEstacionRecibo(gov.sir.core.negocio.modelo.EstacionRecibo)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarEstacionRecibo(EstacionRecibo estacionRecibo) throws HermodException {
        return hermod.eliminarEstacionRecibo(estacionRecibo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addValidacionNota(gov.sir.core.negocio.modelo.ValidacionNota)
     * @ejb:interface-method view-type="both"
     */
    public boolean addValidacionNota(ValidacionNota valNota) throws HermodException {
        return hermod.addValidacionNota(valNota);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarValidacionNota(gov.sir.core.negocio.modelo.ValidacionNota)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarValidacionNota(ValidacionNota valNota) throws HermodException {
        return hermod.eliminarValidacionNota(valNota);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getValidacionNotas()
     * @ejb:interface-method view-type="both"
     */
    public List getValidacionNotas() throws HermodException {
        return hermod.getValidacionNotas();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateSubtiposAtencionEnUsuario(gov.sir.core.negocio.modelo.Usuario,
     * java.util.List, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateSubtiposAtencionEnUsuario(Usuario usuario, List listaUsuarios, Circulo circulo) throws HermodException {
        return hermod.updateSubtiposAtencionEnUsuario(usuario, listaUsuarios, circulo);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getListaProcesos()
     * @ejb:interface-method view-type="both"
     */
    public List getListaProcesos() throws HermodException {
        return hermod.getListaProcesos();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getFasesDeProceso(gov.sir.core.negocio.modelo.Proceso)
     * @ejb:interface-method view-type="both"
     */
    public List getFasesDeProceso(Proceso proceso) throws HermodException {
        return hermod.getFasesDeProceso(proceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoCertificadoValidacion(gov.sir.core.negocio.modelo.TipoCertificadoValidacion)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.TipoCertificadoValidacionPk addTipoCertificadoValidacion(TipoCertificadoValidacion valNota) throws HermodException {
        return hermod.addTipoCertificadoValidacion(valNota);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoCertificadoValidacion(gov.sir.core.negocio.modelo.TipoCertificadoValidacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoCertificadoValidacion(TipoCertificadoValidacion valNota) throws HermodException {
        return hermod.eliminarTipoCertificadoValidacion(valNota);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposCertificadosValidacionByTipoCertificado(gov.sir.core.negocio.modelo.TipoCertificado)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposCertificadosValidacionByTipoCertificado(TipoCertificado tipoCertificado) throws HermodException {
        return hermod.getTiposCertificadosValidacionByTipoCertificado(tipoCertificado);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getValidaciones()
     * @ejb:interface-method view-type="both"
     */
    public List getValidaciones() throws HermodException {
        return hermod.getValidaciones();
    }
    
         /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isEstacionActivaCalificador(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public boolean isEstacionActivaCalificador(String estacionId) throws HermodException {
        return hermod.isEstacionActivaCalificador(estacionId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTipoNota(gov.sir.core.negocio.modelo.TipoNota,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.TipoNotaPk addTipoNota(TipoNota tipoNota, Usuario usuario) throws HermodException {
        return hermod.addTipoNota(tipoNota, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateTipoNota(gov.sir.core.negocio.modelo.TipoNota,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.TipoNotaPk updateTipoNota(TipoNota tipoNota, Usuario usuario) throws HermodException {
        return hermod.updateTipoNota(tipoNota, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarTipoNota(gov.sir.core.negocio.modelo.TipoNota,
     * Usuario usuario)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarTipoNota(TipoNota tipoNota, Usuario usuario) throws HermodException {
        return hermod.eliminarTipoNota(tipoNota, usuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCheckItem(gov.sir.core.negocio.modelo.CheckItem)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.CheckItemPk addCheckItem(CheckItem item) throws HermodException {
        return hermod.addCheckItem(item);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateCheckItem(gov.sir.core.negocio.modelo.CheckItem)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.CheckItemPk updateCheckItem(CheckItem item) throws HermodException {
        return hermod.updateCheckItem(item);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarCheckItem(gov.sir.core.negocio.modelo.CheckItem)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarCheckItem(CheckItem item) throws HermodException {
        return hermod.eliminarCheckItem(item);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCheckItemsBySubtipoSolicitud(gov.sir.core.negocio.modelo.SubtipoSolicitud)
     * @ejb:interface-method view-type="both"
     */
    public List getCheckItemsBySubtipoSolicitud(SubtipoSolicitud subtipo) throws HermodException {
        return hermod.getCheckItemsBySubtipoSolicitud(subtipo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setUsuarioToTurno(gov.sir.core.negocio.modelo.Usuario,
     * gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public boolean setUsuarioToTurno(Usuario user, Turno turno) throws HermodException {
        return hermod.setUsuarioToTurno(user, turno);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTiposVisibilidad()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposVisibilidad() throws HermodException {
        return hermod.getTiposVisibilidad();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosFase(gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Fase, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosFase(Proceso proceso, Fase fase, Circulo circulo) throws HermodException {
        return hermod.getTurnosFase(proceso, fase, circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateSolicitudCertificado(gov.sir.core.negocio.modelo.SolicitudCertificado)
     * @ejb:interface-method view-type="both"
     */
    public SolicitudCertificado updateSolicitudCertificado(SolicitudCertificado solCer) throws HermodException {
        return hermod.updateSolicitudCertificado(solCer);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposNotaProceso(gov.sir.core.negocio.modelo.Proceso.ProcesoPk,
     * java.lang.String, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposNotaProceso(gov.sir.core.negocio.modelo.ProcesoPk proceso, String fase, boolean devolutiva) throws HermodException {
        return hermod.getTiposNotaProceso(proceso, fase, devolutiva);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#guardardocumentopagoantesdecorreccion(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean guardardocumentopagoantesdecorreccion(String idDocumento, String iduser) throws HermodException {
        return hermod.guardardocumentopagoantesdecorreccion(idDocumento,iduser);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposNotaProcesoByTpnaDevolutiva(gov.sir.core.negocio.modelo.Proceso.ProcesoPk,
     * java.lang.String, boolean)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposNotaProcesoByTpnaDevolutiva(gov.sir.core.negocio.modelo.ProcesoPk proceso, boolean devolutiva) throws HermodException {
        return hermod.getTiposNotaProcesoByTpnaDevolutiva(proceso, devolutiva);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposTarifaCertificados()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposTarifaCertificados() throws HermodException {
        return hermod.getTiposTarifaCertificados();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposNotaProceso(gov.sir.core.negocio.modelo.Proceso.ProcesoPk,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposNotaProceso(gov.sir.core.negocio.modelo.ProcesoPk proceso, String fase) throws HermodException {
        return hermod.getTiposNotaProceso(proceso, fase);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarRotacionReparto(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarRotacionReparto(Circulo circulo) throws HermodException {
        return hermod.actualizarRotacionReparto(circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNextNumeroReciboSinAvanzar(gov.sir.core.negocio.modelo.EstacionRecibo.EstacionReciboPk,
     * gov.sir.core.negocio.modelo.Usuario, long)
     * @ejb:interface-method view-type="both"
     */
    public long getNextNumeroReciboSinAvanzar(gov.sir.core.negocio.modelo.EstacionReciboPk oid, Usuario user, long idProceso) throws HermodException {
        return hermod.getNextNumeroReciboSinAvanzar(oid, user, idProceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#avanzarNumeroRecibo(gov.sir.core.negocio.modelo.EstacionRecibo.EstacionReciboPk,
     * long, long)
     * @ejb:interface-method view-type="both"
     */
    public long avanzarNumeroRecibo(gov.sir.core.negocio.modelo.EstacionReciboPk oid, long avance, long idProceso) throws HermodException {
        return hermod.avanzarNumeroRecibo(oid, avance, idProceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#trasladarTurnoSAS(gov.sir.core.negocio.modelo.Turno,
     * org.auriga.core.modelo.transferObjects.Estacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean trasladarTurnoSAS(Turno turno, Estacion estacion) throws HermodException {
        return hermod.trasladarTurnoSAS(turno, estacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByMatricula(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosByMatricula(String matricula) throws HermodException {
        return hermod.getTurnosByMatricula(matricula);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#almacenarMotivoIncrementoSecuencial(gov.sir.core.negocio.modelo.Usuario,
     * long, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean almacenarMotivoIncrementoSecuencial(Usuario usuario, long secuencial, String motivo) throws HermodException {
        return hermod.almacenarMotivoIncrementoSecuencial(usuario, secuencial, motivo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getFase(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Fase getFase(String idFase) throws HermodException {
        return hermod.getFase(idFase);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosAnteriores(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosAnteriores(String idTurno) throws HermodException {
        return hermod.getTurnosAnteriores(idTurno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosSiguientes(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosSiguientes(String idTurno) throws HermodException {
        return hermod.getTurnosSiguientes(idTurno);
    }

    /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        **/
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosSiguientes(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosSiguientesTestamento(String idTurno) throws HermodException {
        return hermod.getTurnosSiguientesTestamento(idTurno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosSiguientesDevolucion(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosSiguientesDevolucion(String idTurno) throws HermodException {
        return hermod.getTurnosSiguientesDevolucion(idTurno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#procesarPagoMasivos(gov.sir.core.negocio.modelo.Pago,
     * java.util.Hashtable)
     * @ejb:interface-method view-type="both"
     */
    public Turno procesarPagoMasivos(Pago pago, Hashtable parametros) throws HermodException {
        return hermod.procesarPagoMasivos(pago, parametros);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getProbabilidadRevisionCalificacion()
     * @ejb:interface-method view-type="both"
     */
    public String getProbabilidadRevisionCalificacion() throws HermodException {
        return hermod.getProbabilidadRevisionCalificacion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumeroImpresionesCertificadosEnCorrecciones()
     * @ejb:interface-method view-type="both"
     */
    public String getNumeroImpresionesCertificadosEnCorrecciones() throws HermodException {
        return hermod.getNumeroImpresionesCertificadosEnCorrecciones();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateProbabilidadRevisionCalificacion(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateProbabilidadRevisionCalificacion(String nuevoValor) throws HermodException {
        return hermod.updateProbabilidadRevisionCalificacion(nuevoValor);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSolicitudesSinTurno(gov.sir.core.negocio.modelo.Circulo,
     * java.util.Date, java.util.Date, java.lang.String, int, int)
     * @ejb:interface-method view-type="both"
     */
    public Map getSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena, int indiceInicial, int numeroResultados) throws HermodException {
        return hermod.getSolicitudesSinTurno(circulo, fechaInicial, fechaFinal, cadena, indiceInicial, numeroResultados);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeSolicitudesSinTurno(gov.sir.core.negocio.modelo.Circulo,
     * java.util.Date, java.util.Date, java.lang.String, int, int)
     * @ejb:interface-method view-type="both"
     */
    public boolean removeSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena, int indiceInicial, int numeroResultados) throws HermodException {
        return hermod.removeSolicitudesSinTurno(circulo, fechaInicial, fechaFinal, cadena, indiceInicial, numeroResultados);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumeroSolicitudesSinTurno(gov.sir.core.negocio.modelo.Circulo,
     * java.util.Date, java.util.Date)
     * @ejb:interface-method view-type="both"
     */
    public long getNumeroSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal) throws HermodException {
        return hermod.getNumeroSolicitudesSinTurno(circulo, fechaInicial, fechaFinal);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#deleteSolicitud(gov.sir.core.negocio.modelo.Solicitud)
     * @ejb:interface-method view-type="both"
     */
    public boolean deleteSolicitud(Solicitud solicitud) throws HermodException {
        return hermod.deleteSolicitud(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#deleteSolicitudAsociadas(gov.sir.core.negocio.modelo.Solicitud.SolicitudPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean deleteTurnoAnterior(SolicitudPk sid) throws HermodException {
        return hermod.deleteTurnoAnterior(sid);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposTarifasConfiguradasPorCirculo()
     * @ejb:interface-method view-type="both"
     */
    public List getTiposTarifasConfiguradasPorCirculo() throws HermodException {
        return hermod.getTiposTarifasConfiguradasPorCirculo();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposTarifasPorCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposTarifasPorCirculo(Circulo c) throws HermodException {
        return hermod.getTiposTarifasPorCirculo(c);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoFase(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getTipoFase(String fase_id) throws HermodException {
        return hermod.getTipoFase(fase_id);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSolicitudById(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Solicitud getSolicitudById(String solicitud_id) throws HermodException {
        return hermod.getSolicitudById(solicitud_id);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getRangoTurnosByFase(java.lang.String,
     * java.lang.String, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getRangoTurnosByFase(String idFase, String turnoInicial, String turnoFinal) throws HermodException {
        return hermod.getRangoTurnosByFase(idFase, turnoInicial, turnoFinal);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateClasificacionSolicitudRegistro(java.lang.String,
     * gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateClasificacionSolicitudRegistro(String clasificacion, Turno turnoActualizado) throws HermodException {
        return hermod.updateClasificacionSolicitudRegistro(clasificacion, turnoActualizado);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#registrarPago(gov.sir.core.negocio.modelo.Pago,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Pago registrarPago(Pago pago, String estacion) throws HermodException {
        return hermod.registrarPago(pago, estacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarMatriculaMesaControl(gov.sir.core.negocio.modelo.SolicitudCertificado,
     * gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public void validarMatriculaMesaControl(SolicitudCertificado solCer, gov.sir.core.negocio.modelo.FolioPk folioID) throws HermodException {
        hermod.validarMatriculaMesaControl(solCer, folioID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumeroIntentosImpresion(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getNumeroIntentosImpresion(String proceso) throws HermodException {
        return hermod.getNumeroIntentosImpresion(proceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateNumeroIntentosImpresion(java.lang.String,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateNumeroIntentosImpresion(String proceso, String valor) throws HermodException {
        return hermod.updateNumeroIntentosImpresion(proceso, valor);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiempoEsperaImpresion(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getTiempoEsperaImpresion(String proceso) throws HermodException {
        return hermod.getTiempoEsperaImpresion(proceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateTiempoEsperaImpresion(java.lang.String,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateTiempoEsperaImpresion(String proceso, String valor) throws HermodException {
        return hermod.updateTiempoEsperaImpresion(proceso, valor);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEstadoPagoSolicitud(gov.sir.core.negocio.modelo.Solicitud)
     * @ejb:interface-method view-type="both"
     */
    public boolean getEstadoPagoSolicitud(Solicitud solicitud) throws HermodException {
        return hermod.getEstadoPagoSolicitud(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearTurnoRegistro(gov.sir.core.negocio.modelo.SolicitudRegistro,
     * gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Turno crearTurnoRegistro(SolicitudRegistro solicitud, Usuario usuarioSir) throws HermodException {
        return hermod.crearTurnoRegistro(solicitud, usuarioSir);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearTurnoManual(gov.sir.core.negocio.modelo.Pago,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.util.Date, gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Turno crearTurnoManual(Pago pPago, String sAnio, String sCirculo, String sProceso, String sIdTurno, Date dFechaInicio, Usuario uUsuario) throws HermodException {
        return hermod.crearTurnoManual(pPago, sAnio, sCirculo, sProceso, sIdTurno, dFechaInicio, uUsuario);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByUsuarioCalificador(long,
     * Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosByUsuarioCalificador(long usuario, Circulo circulo) throws HermodException {
        return hermod.getTurnosByUsuarioCalificador(usuario, circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#listarTurnosRegistroParaAgregarCertificadosAsociados()
     * @ejb:interface-method view-type="both"
     */
    public List listarTurnosRegistroParaAgregarCertificadosAsociados(Circulo cir) throws HermodException {
        return hermod.listarTurnosRegistroParaAgregarCertificadosAsociados(cir);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addSolicitudFolioToSolicitudHija(gov.sir.core.negocio.modelo.Turno,
     * gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.SolicitudFolio)
     * @ejb:interface-method view-type="both"
     */
    public Turno addSolicitudFolioToSolicitudHija(Turno turno, Solicitud solcitud, SolicitudFolio solFolio) throws HermodException {
        return hermod.addSolicitudFolioToSolicitudHija(turno, solcitud, solFolio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addSolicitudHija(gov.sir.core.negocio.modelo.Solicitud)
     * @ejb:interface-method view-type="both"
     */
    public Solicitud addSolicitudHija(Solicitud solPadre, Solicitud solHija) throws HermodException {
        return hermod.addSolicitudHija(solPadre, solHija);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeSolicitudFolioFromSolicitudHija(gov.sir.core.negocio.modelo.Turno,
     * gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.SolicitudFolio)
     * @ejb:interface-method view-type="both"
     */
    public Turno removeSolicitudFolioFromSolicitudHija(Turno turno, Solicitud solcitud, SolicitudFolio solFolio) throws HermodException {
        return hermod.removeSolicitudFolioFromSolicitudHija(turno, solcitud, solFolio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateAjusteInTurnoRegistro(gov.sir.core.negocio.modelo.Turno,
     * boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateAjusteInTurnoRegistro(Turno turno, boolean ajuste) throws HermodException {
        return hermod.updateAjusteInTurnoRegistro(turno, ajuste);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUsuariosPorSubtiposDeAtencionRotados(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public Map getUsuariosPorSubtiposDeAtencionRotados(Circulo circulo) throws HermodException {
        return hermod.getUsuariosPorSubtiposDeAtencionRotados(circulo);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validacionCorreccion(String)
     * @ejb:interface-method view-type="both"
     */
    public boolean validacionCorreccion(String idDocumento) throws HermodException {
        return hermod.validacionCorreccion(idDocumento);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setRespuestaRecurso(gov.sir.core.negocio.modelo.Recurso)
     * @ejb:interface-method view-type="both"
     */
    public void setRespuestaRecurso(Recurso recurso) throws HermodException {
        hermod.setRespuestaRecurso(recurso);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCtrlMatricula(java.lang.String, java.lang.String, java.lang.String, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void addCtrlMatricula(String idMatricula, String accion, String rol, String idWorkflow) throws HermodException {
        hermod.addCtrlMatricula(idMatricula, accion, rol, idWorkflow);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCtrlReasignacion(gov.sir.core.negocio.modelo.Turno, java.lang.String, java.lang.String)  
     * @ejb:interface-method view-type="both"
     */
    public void addCtrlReasignacion(Turno turno, String usuarioOrigen, String usuarioDestino) throws HermodException {
        hermod.addCtrlReasignacion(turno, usuarioOrigen, usuarioDestino);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#notificarNotaDevolutiva(gov.sir.core.negocio.modelo.NotificacionNota) 
     * @ejb:interface-method view-type="both"
     */
    public void notificarNotaDevolutiva(NotificacionNota notify) throws HermodException {
        hermod.notificarNotaDevolutiva(notify);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateRecurso(gov.sir.core.negocio.modelo.Recurso, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void updateRecurso(Recurso recurso, String turnoWF) throws HermodException{
        hermod.updateRecurso(recurso, turnoWF);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#deleteRecurso(java.lang.String, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void deleteRecurso(String idRecurso, String turnoWF) throws HermodException{
        hermod.deleteRecurso(idRecurso, turnoWF);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#executeDMLFromSQL(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void executeDMLFromSQL(String sql) throws HermodException{
        hermod.executeDMLFromSQL(sql);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNotaDevNotificada(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getNotaDevNotificada(String turnoWF) throws HermodException {
       return hermod.getNotaDevNotificada(turnoWF);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#diasHabiles(java.lang.String, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public int diasHabiles(String idCirculo, String fecha) throws HermodException {
       return hermod.diasHabiles(idCirculo, fecha);
    }
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addNotasInformativas(java.util.List) 
     * @ejb:interface-method view-type="both"
     */
    public void addNotasInformativas(List notasInformativas) throws HermodException {
        hermod.addNotasInformativas(notasInformativas);
    }
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setStateNotaNotificada(java.lang.String, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void setStateNotaNotificada(String state, String idWorkflow) throws HermodException {
        hermod.setStateNotaNotificada(state, idWorkflow);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setStatusREL(java.lang.String, java.lang.String, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void setStatusREL(String status, String url, String idWorkflow) throws HermodException {
        hermod.setStatusREL(status, url, idWorkflow);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addRecurso(gov.sir.core.negocio.modelo.Recurso)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.RecursoPk addRecurso(Recurso recurso) throws HermodException {
        return hermod.addRecurso(recurso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateRecurso(gov.sir.core.negocio.modelo.RecursoPk,
     * String)
     * @ejb:interface-method view-type="both"
     */
    public void updateRecurso(RecursoPk rid, String datoAmpliacion) throws HermodException {
        hermod.updateRecurso(rid, datoAmpliacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addLiquidacionToSolicitud(gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.Liquidacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean addLiquidacionToSolicitud(Solicitud solicitud, Liquidacion liquidacion) throws HermodException {
        return hermod.addLiquidacionToSolicitud(solicitud, liquidacion);
    }
    
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getReasignacion() 
     * @ejb:interface-method view-type="both"
     */
    public int getReasignacion() throws HermodException {
        return hermod.getReasignacion();
    }
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValorLookupCodes(java.lang.String, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public String getValorLookupCodes(String tipo, String idCodigo) throws HermodException{
        return hermod.getValorLookupCodes(tipo, idCodigo);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getOPLookupCodesByTipo(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List getOPLookupCodesByTipo (String tipo) throws HermodException{
        return hermod.getOPLookupCodesByTipo(tipo);
    }
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValorLookupCodesByTipo(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List getValorLookupCodesByTipo (String tipo) throws HermodException{
        return hermod.getValorLookupCodesByTipo(tipo);
    } 
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCopiaImp(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public String getCopiaImp(String idCirculo) throws HermodException {
        return hermod.getCopiaImp(idCirculo);
    }
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getLimiteReasignacion(gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public int getLimiteReasignacion(Turno turno) throws HermodException {
        return hermod.getLimiteReasignacion(turno);
    }
    
        /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumNotasInformativas(gov.sir.core.negocio.modelo.Turno, java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public int getNumNotasInformativas(Turno turno, String tipoNota)throws HermodException {
        return hermod.getNumNotasInformativas(turno, tipoNota);
    }
    
       /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEstacionFromRevisor(gov.sir.core.negocio.modelo.Turno)  
     * @ejb:interface-method view-type="both"
     */
    public String getEstacionFromRevisor(Turno turno) throws HermodException {
        return hermod.getEstacionFromRevisor(turno);
    }
    
      /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEstacionFromRecursosNota(gov.sir.core.negocio.modelo.Turno) 
     * @ejb:interface-method view-type="both"
     */
    public String getEstacionFromRecursosNota(Turno turno) throws HermodException {
        return hermod.getEstacionFromRecursosNota(turno);
    }
    
    
     /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isMatriculaNotificacionDev(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public List isMatriculaNotificacionDev(String idMatricula) throws HermodException {
        return hermod.isMatriculaNotificacionDev(idMatricula);
    }
    
       /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNotasNecesarias(gov.sir.core.negocio.modelo.Turno) 
     * @ejb:interface-method view-type="both"
     */
    public int getNotasNecesarias(Turno turno) throws HermodException {
        return hermod.getNotasNecesarias(turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosFotocopiasConPagoVencido(gov.sir.core.negocio.modelo.Circulo,
     * double)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosFotocopiasConPagoVencido(Circulo circulo, double dias) throws HermodException {
        return hermod.getTurnosFotocopiasConPagoVencido(circulo, dias);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateDocumentosFotocopia(gov.sir.core.negocio.modelo.SolicitudFotocopia)
     * @ejb:interface-method view-type="both"
     */
    public SolicitudFotocopia updateDocumentosFotocopia(SolicitudFotocopia sol) throws HermodException {
        return hermod.updateDocumentosFotocopia(sol);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#deleteActosBySolicitud(java.lang.String) 
     * @ejb:interface-method view-type="both"
     */
    public void deleteActosBySolicitud(String idSolicitud) throws HermodException{
        hermod.deleteActosBySolicitud(idSolicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#forceUnFolio(gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.Folio)
     * @ejb:interface-method view-type="both"
     */
    public void forceUnFolio(Solicitud solicitud, Folio folio) throws HermodException {
        hermod.forceUnFolio(solicitud, folio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getProcesoByIdFase(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getProcesoByIdFase(String idFase) throws HermodException {
        return hermod.getProcesoByIdFase(idFase);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearTurnoFotocopias(gov.sir.core.negocio.modelo.SolicitudFotocopia,
     * gov.sir.core.negocio.modelo.Usuario)
     * @ejb:interface-method view-type="both"
     */
    public Turno crearTurnoFotocopias(SolicitudFotocopia solicitud, Usuario usuarioSir) throws HermodException {
        return hermod.crearTurnoFotocopias(solicitud, usuarioSir);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setAprobacionSolicitud(gov.sir.core.negocio.modelo.Solicitud.SolicitudPk,
     * boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean setAprobacionSolicitud(gov.sir.core.negocio.modelo.SolicitudPk solID, boolean aprobada) throws HermodException {
        return hermod.setAprobacionSolicitud(solID, aprobada);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnoBySolicitud(gov.sir.core.negocio.modelo.Solicitud.SolicitudPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoBySolicitud(gov.sir.core.negocio.modelo.SolicitudPk solID) throws HermodException {
        return hermod.getTurnoBySolicitud(solID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearOficio(gov.sir.core.negocio.modelo.Oficio)
     * @ejb:interface-method view-type="both"
     */
    public gov.sir.core.negocio.modelo.OficioPk crearOficio(Oficio oficio) throws HermodException {
        return hermod.crearOficio(oficio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarOficio(gov.sir.core.negocio.modelo.Oficio)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarOficios(List oficios) throws HermodException {
        hermod.eliminarOficios(oficios);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarFirmaOficio(gov.sir.core.negocio.modelo.Oficio.OficioPk,
     * boolean)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarFirmaOficio(gov.sir.core.negocio.modelo.OficioPk oficioID, boolean firmado) throws HermodException {
        return hermod.actualizarFirmaOficio(oficioID, firmado);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setOficioRespuestaToRecurso(gov.sir.core.negocio.modelo.Recurso.RecursoPk,
     * gov.sir.core.negocio.modelo.Oficio.OficioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean setOficioRespuestaToRecurso(gov.sir.core.negocio.modelo.RecursoPk recursoID, gov.sir.core.negocio.modelo.OficioPk oficioID) throws HermodException {
        return hermod.setOficioRespuestaToRecurso(recursoID, oficioID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEstacionesActuales(java.lang.String,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getEstacionesActuales(String fase, String idWF) throws HermodException {
        return hermod.getEstacionesActuales(fase, idWF);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getValor(java.lang.String,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getValor(String tipo, String codigo) throws HermodException {
        return hermod.getValor(tipo, codigo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getOficiosTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public List getOficiosTurno(gov.sir.core.negocio.modelo.TurnoPk oid) throws HermodException {
        return hermod.getOficiosTurno(oid);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarNumeroOficio(gov.sir.core.negocio.modelo.Oficio.OficioPk,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarNumeroOficio(gov.sir.core.negocio.modelo.OficioPk oficioID, String nuevoNumero) throws HermodException {
        return hermod.actualizarNumeroOficio(oficioID, nuevoNumero);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#agregarFechaFirmaOficio(gov.sir.core.negocio.modelo.OficioPk
     * oficioID, java.util.Date fechaFirma)
     * @ejb:interface-method view-type="both"
     */
    public boolean agregarFechaFirmaOficio(gov.sir.core.negocio.modelo.OficioPk oficioID, Date fechaFirma) throws HermodException {
        return hermod.agregarFechaFirmaOficio(oficioID, fechaFirma);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUsuarioConTurnoEnCalificacionConFolioAsociado(gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public Usuario getUsuarioConTurnoEnCalificacionConFolioAsociado(gov.sir.core.negocio.modelo.FolioPk folioID) throws HermodException {
        return hermod.getUsuarioConTurnoEnCalificacionConFolioAsociado(folioID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isValidTurnoSAS(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean isValidTurnoSAS(String idWorkflow) throws HermodException {
        return hermod.isValidTurnoSAS(idWorkflow);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isValidTurnoSAS(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String lastAdministradorTurnoSAS(String idWorkflow) throws HermodException {
        return hermod.lastAdministradorTurnoSAS(idWorkflow);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getEntidadesReparto()
     * @ejb:interface-method view-type="both"
     */
    public List getEntidadesReparto() throws HermodException {
        return hermod.getEntidadesReparto();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getEntidadesRepartoByNaturaleza(NaturalezaJuridicaEntidad
     * naturalezaJuridicaReparto)
     * @ejb:interface-method view-type="both"
     */
    public List getEntidadesRepartoByNaturaleza(NaturalezaJuridicaEntidad naturalezaJuridicaReparto) throws HermodException {
        return hermod.getEntidadesRepartoByNaturaleza(naturalezaJuridicaReparto);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNaturalezasJuridicasEntidades()
     * @ejb:interface-method view-type="both"
     */
    public List getNaturalezasJuridicasEntidades() throws HermodException {
        return hermod.getNaturalezasJuridicasEntidades();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getPermisosCorreccion()
     * @ejb:interface-method view-type="both"
     */
    public List getPermisosCorreccion() throws HermodException {
        return hermod.getPermisosCorreccion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#asignarPermisosCorreccion(gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public boolean asignarPermisosCorreccion(gov.sir.core.negocio.modelo.TurnoPk turnoID, List permisos) throws HermodException {
        return hermod.asignarPermisosCorreccion(turnoID, permisos);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#refrescarSubtipoAtencionTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean refrescarSubtipoAtencionTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID) throws HermodException {
        return hermod.refrescarSubtipoAtencionTurno(turnoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasNoRepartidasByCirculoRegistral(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasNoRepartidasByCirculoRegistral(Circulo circuloRegistral) throws HermodException {
        return hermod.getMinutasNoRepartidasByCirculoRegistral(circuloRegistral);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasByOtorganteNatural(java.lang.String,
     * long, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasByOtorganteNatural(String otorganteNatural, long estado, Circulo circuloRegistral) throws HermodException {
        return hermod.getMinutasByOtorganteNatural(otorganteNatural, estado, circuloRegistral);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasByOtorgantePublico(java.lang.String,
     * long, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasByOtorgantePublico(String otorganteNatural, long estado, Circulo circuloRegistral) throws HermodException {
        return hermod.getMinutasByOtorgantePublico(otorganteNatural, estado, circuloRegistral);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasRadicadasByRangoFecha(java.util.Date,
     * java.util.Date)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasRadicadasByRangoFecha(Date fechaInicial, Date fechaFinal) throws HermodException {
        return hermod.getMinutasRadicadasByRangoFecha(fechaInicial, fechaFinal);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getMinutasRepartidasByRangoFecha(java.util.Date,
     * java.util.Date, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getMinutasRepartidasByRangoFecha(Date fechaInicial, Date fechaFinal, Circulo circuloRegistral) throws HermodException {
        return hermod.getMinutasRepartidasByRangoFecha(fechaInicial, fechaFinal, circuloRegistral);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCirculosNotarialesByCirculoRegistral(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getCirculosNotarialesByCirculoRegistral(Circulo circulo) throws HermodException {
        return hermod.getCirculosNotarialesByCirculoRegistral(circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setDatosAntiguoSistemaToSolicitud(gov.sir.core.negocio.modelo.Solicitud,
     * gov.sir.core.negocio.modelo.DatosAntiguoSistema)
     * @ejb:interface-method view-type="both"
     */
    public boolean setDatosAntiguoSistemaToSolicitud(Solicitud sol, DatosAntiguoSistema datosAntiguoSistema) throws HermodException {
        return hermod.setDatosAntiguoSistemaToSolicitud(sol, datosAntiguoSistema);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getColasRepartoByCategoria()
     * @ejb:interface-method view-type="both"
     */
    public Hashtable getColasRepartoByCategoria() throws HermodException {
        return hermod.getColasRepartoByCategoria();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#agregarEntidadPublica(gov.sir.core.negocio.modelo.EntidadPublica)
     * @ejb:interface-method view-type="both"
     */
    public boolean agregarEntidadPublica(EntidadPublica entidadPublica) throws HermodException {
        return hermod.agregarEntidadPublica(entidadPublica);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarEntidadPublica(gov.sir.core.negocio.modelo.EntidadPublica)
     * @ejb:interface-method view-type="both"
     */
    public boolean editarEntidadPublica(EntidadPublica entidadPublica) throws HermodException {
        return hermod.editarEntidadPublica(entidadPublica);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarEntidadPublica(gov.sir.core.negocio.modelo.EntidadPublica)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarEntidadPublica(EntidadPublica entidadPublica) throws HermodException {
        return hermod.eliminarEntidadPublica(entidadPublica);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#agregarNaturalezaJuridicaEntidadPublica(gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)
     * @ejb:interface-method view-type="both"
     */
    public boolean agregarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {
        return hermod.agregarNaturalezaJuridicaEntidadPublica(naturaleza);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#editarNaturalezaJuridicaEntidadPublica(gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)
     * @ejb:interface-method view-type="both"
     */
    public boolean editarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {
        return hermod.editarNaturalezaJuridicaEntidadPublica(naturaleza);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarEstadoNaturalezaJuridicaEntidadPublica(gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarEstadoNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {
        return hermod.actualizarEstadoNaturalezaJuridicaEntidadPublica(naturaleza);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarNaturalezaJuridicaEntidadPublica(gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarNaturalezaJuridicaEntidadPublica(NaturalezaJuridicaEntidad naturaleza) throws HermodException {
        return hermod.eliminarNaturalezaJuridicaEntidadPublica(naturaleza);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getIdWorkflowByIdMinuta(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public String getIdWorkflowByIdMinuta(String idMinuta) throws HermodException {
        return hermod.getIdWorkflowByIdMinuta(idMinuta);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getActosQueVencen()
     * @ejb:interface-method view-type="both"
     */
    public List getActosQueVencen() throws HermodException {
        return hermod.getActosQueVencen();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getPlazoVencimientoRegistroActos()
     * @ejb:interface-method view-type="both"
     */
    public String getPlazoVencimientoRegistroActos() throws HermodException {
        return hermod.getPlazoVencimientoRegistroActos();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateEstadoSolicitudFolio(gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateEstadoSolicitudFolio(SolicitudFolio solFolio) throws HermodException {
        return hermod.updateEstadoSolicitudFolio(solFolio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#marcarFolioInTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean marcarFolioInTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID, gov.sir.core.negocio.modelo.FolioPk folioID) throws HermodException {
        return hermod.marcarFolioInTurno(turnoID, folioID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#desmarcarFoliosInTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean desmarcarFoliosInTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID) throws HermodException {
        return hermod.desmarcarFoliosInTurno(turnoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getAlertas(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getAlertas(String idEstacion) throws HermodException {
        return hermod.getAlertas(idEstacion);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#modificarLiquidacionRegistro(gov.sir.core.negocio.modelo.Liquidacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarEstadoDocumento(DocumentoPago Documento) throws HermodException {
        return hermod.actualizarEstadoDocumento(Documento);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#modificarLiquidacionRegistro(gov.sir.core.negocio.modelo.Liquidacion)
     * @ejb:interface-method view-type="both"
     */
    public Liquidacion modificarLiquidacionRegistro(Liquidacion nuevaLiquidacion) throws HermodException {
        return hermod.modificarLiquidacionRegistro(nuevaLiquidacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocumentosPagoExistente(gov.sir.core.negocio.modelo.DocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public DocumentoPago getDocumentosPagoExistente(DocumentoPago doc) throws HermodException {
        return hermod.getDocumentosPagoExistente(doc);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#hasActoTurnoRegistro(gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * gov.sir.core.negocio.modelo.TipoActo.TipoActoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean hasActoTurnoRegistro(gov.sir.core.negocio.modelo.TurnoPk turnoID, gov.sir.core.negocio.modelo.TipoActoPk tipoActoID) throws HermodException {
        return hermod.hasActoTurnoRegistro(turnoID, tipoActoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarSolicitudVinculada(gov.sir.core.negocio.modelo.Solicitud.SolicitudPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarSolicitudVinculada(gov.sir.core.negocio.modelo.SolicitudPk solicitudID) throws HermodException {
        return hermod.validarSolicitudVinculada(solicitudID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getInteresesJuridicos()
     * @ejb:interface-method view-type="both"
     */
    public List getInteresesJuridicos() throws HermodException {
        return hermod.getInteresesJuridicos();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getPathFirmasRegistradores()
     * @ejb:interface-method view-type="both"
     */
    public String getPathFirmasRegistradores() throws HermodException {
        return hermod.getPathFirmasRegistradores();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getPathFirmasRegistradoresAAsociar()
     * @ejb:interface-method view-type="both"
     */
    public String getPathFirmasRegistradoresAAsociar() throws HermodException {
        return hermod.getPathFirmasRegistradoresAAsociar();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#deshacerAvancesPush(gov.sir.core.negocio.modelo.Turno,
     * int)
     * @ejb:interface-method view-type="both"
     */
    public boolean deshacerAvancesPush(Turno turno, int cantidad) throws HermodException {
        return hermod.deshacerAvancesPush(turno, cantidad);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getFirmasContentType()
     * @ejb:interface-method view-type="both"
     */
    public String getFirmasContentType() throws HermodException {
        return hermod.getFirmasContentType();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUrlServletReportes()
     * @ejb:interface-method view-type="both"
     */
    public String getUrlServletReportes() throws HermodException {
        return hermod.getUrlServletReportes();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumeroMaximoImpresionesCertificados()
     * @ejb:interface-method view-type="both"
     */
    public String getNumeroMaximoImpresionesCertificados() throws HermodException {
        return hermod.getNumeroMaximoImpresionesCertificados();
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getTextoExento()
     * @ejb:interface-method view-type="both"
     */
    public String getTextoExento() throws HermodException {
        return hermod.getTextoExento();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#marcarFolioRecienCreadoASInTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk,
     * gov.sir.core.negocio.modelo.Folio.FolioPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean marcarFolioRecienCreadoASInTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID, gov.sir.core.negocio.modelo.FolioPk folioID) throws HermodException {
        return hermod.marcarFolioRecienCreadoASInTurno(turnoID, folioID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#desmarcarFoliosRecienCreadoASInTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean desmarcarFoliosRecienCreadoASInTurno(gov.sir.core.negocio.modelo.TurnoPk turnoID) throws HermodException {
        return hermod.desmarcarFoliosRecienCreadoASInTurno(turnoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isImpresionRecibosCertificadosImpresoraCajero(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public boolean isImpresionRecibosCertificadosImpresoraCajero(String nombreCirculo) throws HermodException {
        return hermod.isImpresionRecibosCertificadosImpresoraCajero(nombreCirculo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarNotaInformativaAvanceTurno(gov.sir.core.negocio.modelo.Fase,
     * java.util.Hashtable, gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public void validarNotaInformativaAvanceTurno(Fase fase, Hashtable parametros, Turno turno) throws HermodException {
        hermod.validarNotaInformativaAvanceTurno(fase, parametros, turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#obtenerAdministrativasPorRol(java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public List obtenerAdministrativasPorRol(List roles) throws HermodException {
        return hermod.obtenerAdministrativasPorRol(roles);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#obtenerPantallasPaginaReportesPorRol(java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public List obtenerPantallasPaginaReportesPorRol(List roles) throws HermodException {
        return hermod.obtenerPantallasPaginaReportesPorRol(roles);
    } // end method

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#obtenerTiposPantallasAdministrativas()
     * @ejb:interface-method view-type="both"
     */
    public List obtenerTiposPantallasAdministrativas() throws HermodException {
        return hermod.obtenerTiposPantallasAdministrativas();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByFechaYCirculo(gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Fase, java.util.Date,
     * gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws HermodException {
        return hermod.getTurnosByFechaYCirculo(proceso, fase, fecha, circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosConPagoMayorValorVencido(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosConPagoMayorValorVencido(Circulo circulo) throws HermodException {
        return hermod.getTurnosConPagoMayorValorVencido(circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setNumeroReciboPago(gov.sir.core.negocio.modelo.Pago.PagoPk,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public void setNumeroReciboPago(gov.sir.core.negocio.modelo.PagoPk pagoID, String numRecibo) throws HermodException {
        hermod.setNumeroReciboPago(pagoID, numRecibo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getPagoByNumeroRecibo(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public Pago getPagoByNumeroRecibo(String numRecibo) throws HermodException {
        return hermod.getPagoByNumeroRecibo(numRecibo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getPagoByID(gov.sir.core.negocio.modelo.Pago.PagoPk)
     * @ejb:interface-method view-type="both"
     */
    public Pago getPagoByID(PagoPk pID) throws HermodException {
        return hermod.getPagoByID(pID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTurnoHistoriaToTurno(gov.sir.core.negocio.modelo.Turno,
     * gov.sir.core.negocio.modelo.TurnoHistoria)
     * @ejb:interface-method view-type="both"
     */
    public void addTurnoHistoriaToTurno(Turno turno, TurnoHistoria turnoHistoria) throws HermodException {
        hermod.addTurnoHistoriaToTurno(turno, turnoHistoria);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarTurnoCertificadoAsociado(gov.sir.core.negocio.modelo.Turno,
     * Hashtable, Usuario)
     * @ejb:interface-method view-type="both"
     */
    public void actualizarTurnoCertificadoAsociado(Turno t, Hashtable solicitud, Usuario user) throws HermodException {
        hermod.actualizarTurnoCertificadoAsociado(t, solicitud, user);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getProcesoById(gov.sir.core.negocio.modelo.Proceso.ProcesoPk)
     * @ejb:interface-method view-type="both"
     */
    public Proceso getProcesoById(gov.sir.core.negocio.modelo.ProcesoPk oid) throws HermodException {
        return hermod.getProcesoById(oid);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNumModificacionesMinutas()
     * @ejb:interface-method view-type="both"
     */
    public int getNumModificacionesMinutas() throws HermodException {
        return hermod.getNumModificacionesMinutas();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateNumModificacionesMinuta(gov.sir.core.negocio.modelo.Minuta)
     * @ejb:interface-method view-type="both"
     */
    public void updateNumModificacionesMinuta(Minuta minuta) throws HermodException {
        hermod.updateNumModificacionesMinuta(minuta);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosCirculo(gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Circulo, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosCirculo(Proceso proceso, Circulo circulo, String idMatricula) throws HermodException {
        return hermod.getTurnosCirculo(proceso, circulo, idMatricula);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnos(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnos(String idMatricula) throws HermodException {
        return hermod.getTurnos(idMatricula);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosCirculo(gov.sir.core.negocio.modelo.Circulo,
     * java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosCirculo(Circulo circulo, String idMatricula) throws HermodException {
        return hermod.getTurnosCirculo(circulo, idMatricula);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeDevolutivasFromTurno()
     * @ejb:interface-method view-type="both"
     */
    public void removeDevolutivasFromTurno(TurnoPk turnoID) throws HermodException {
        hermod.removeDevolutivasFromTurno(turnoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeDevolutivasFromTurno()
     * @ejb:interface-method view-type="both"
     */
    public double getValorOtroImpuestoTurnosAnteriores(TurnoPk turnoID) throws HermodException {
        return hermod.getValorOtroImpuestoTurnosAnteriores(turnoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCertificadoAsociadoToTurno(gov.sir.core.modelo.Turno,
     * gov.sir.core.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public void addCertificadoAsociadoToTurno(Turno turnoRegistro, Turno turnoCertificado) throws HermodException {
        hermod.addCertificadoAsociadoToTurno(turnoRegistro, turnoCertificado);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosConPagoMayorValorPendiente(gov.sir.core.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosConPagoMayorValorPendiente(Circulo circulo) throws HermodException {
        return hermod.getTurnosConPagoMayorValorPendiente(circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeLiquidacionesSinPagoFromTurno(gov.sir.core.negocio.modelo.Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public void removeLiquidacionesSinPagoFromTurno(gov.sir.core.negocio.modelo.TurnoPk tid) throws HermodException {
        hermod.removeLiquidacionesSinPagoFromTurno(tid);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public void eliminarRelacionTurno(gov.sir.core.negocio.modelo.TurnoPk tid) throws HermodException {
        hermod.eliminarRelacionTurno(tid);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoNota(gov.sir.core.negocio.modelo.TipoNota.TipoNotaPk)
     * @ejb:interface-method view-type="both"
     */
    public TipoNota getTipoNota(gov.sir.core.negocio.modelo.TipoNotaPk tid) throws HermodException {
        return hermod.getTipoNota(tid);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTiposRelacionesFase(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getTiposRelacionesFase(String idFase) throws HermodException {
        return hermod.getTiposRelacionesFase(idFase);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#buscarFasesRelacionadasPorRelacionId(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List buscarFasesRelacionadasPorRelacionId(String relacionId) throws HermodException {
        return hermod.buscarFasesRelacionadasPorRelacionId(relacionId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosParaRelacion(gov.sir.core.negocio.modelo.Proceso,
     * gov.sir.core.negocio.modelo.Fase, gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion) throws HermodException {
        return hermod.getTurnosParaRelacion(proceso, fase, circulo, tipoRelacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByRelacion(Proceso
     * proceso, Fase fase, Circulo circulo, String idRelacion)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosByRelacion(Proceso proceso, Fase fase, Circulo circulo, String idRelacion) throws HermodException {
        return hermod.getTurnosByRelacion(proceso, fase, circulo, idRelacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario, Circulo)
     * @ejb:interface-method view-type="both"
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo) throws HermodException {
        return hermod.crearRelacion(tipoRelacion, usuario, circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario, Circulo, List)
     * @ejb:interface-method view-type="both"
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos) throws HermodException {
        return hermod.crearRelacion(tipoRelacion, usuario, circulo, turnos);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacionNuevo(TipoRelacion,
     * Usuario, Circulo, List, String)
     * @ejb:interface-method view-type="both"
     */
    public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String respuesta) throws HermodException {
        return hermod.crearRelacionNuevo(tipoRelacion, usuario, circulo, turnos, respuesta);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTurnoToRelacion(Relacion,
     * Turno)
     * @ejb:interface-method view-type="both"
     */
    public Relacion addTurnoToRelacion(Relacion relacion, Turno turno) throws HermodException {
        return hermod.addTurnoToRelacion(relacion, turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoRelacion(TipoRelacion.TipoRelacionPk)
     * @ejb:interface-method view-type="both"
     */
    public TipoRelacion getTipoRelacion(gov.sir.core.negocio.modelo.TipoRelacionPk idTipoRelacion) throws HermodException {
        return hermod.getTipoRelacion(idTipoRelacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#setNotaToRelacion(Relacion.RelacionPk,
     * String)
     * @ejb:interface-method view-type="both"
     */
    public void setNotaToRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion, String nota) throws HermodException {
        hermod.setNotaToRelacion(idRelacion, nota);
    }
   
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateAjusteInTurnoRegistro(gov.sir.core.negocio.modelo.DocumentoPago,
     * boolean)
     * @ejb:interface-method view-type="both"
     */
    public DocumentoPago getDocumentobyIdDocPago(String idDocpago) throws HermodException{
        return hermod.getDocumentobyIdDocPago(idDocpago);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getRelacion(gov.sir.core.negocio.modelo.Relacion.RelacionPk)
     * @ejb:interface-method view-type="both"
     */
    public Relacion getRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion) throws HermodException {
        return hermod.getRelacion(idRelacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacion(TipoRelacion,
     * Usuario, Circulo, List, String)
     * @ejb:interface-method view-type="both"
     */
    public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion) throws HermodException {
        return hermod.crearRelacion(tipoRelacion, usuario, circulo, turnos, idRelacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearRelacionNuevo(TipoRelacion,
     * Usuario, Circulo, List, String, String)
     * @ejb:interface-method view-type="both"
     */
    public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion, String respuesta) throws HermodException {
        return hermod.crearRelacionNuevo(tipoRelacion, usuario, circulo, turnos, idRelacion, respuesta);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#isValidarSesionImpresion()
     * @ejb:interface-method view-type="both"
     */
    public boolean isValidarSesionImpresion() throws HermodException {
        return hermod.isValidarSesionImpresion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosParaRelacion(Proceso,
     * Fase, Circulo, TipoRelacion, String)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion, String idRelacion) throws HermodException {
        return hermod.getTurnosParaRelacion(proceso, fase, circulo, tipoRelacion, idRelacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosParaRelacion(Proceso,
     * Fase, Circulo, TipoRelacion, String)
     * @ejb:interface-method view-type="both"
     */
    public long getSecuencialDevolucion(String circuloId, String year) throws HermodException {
        return hermod.getSecuencialDevolucion(circuloId, year);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnoDependiente(VeredaPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoDependiente(gov.sir.core.negocio.modelo.TurnoPk id) throws HermodException {
        return hermod.getTurnoDependiente(id);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#crearTurnoDependiente(Turno,
     * Usuario, long)
     * @ejb:interface-method view-type="both"
     */
    public Turno crearTurnoDependiente(Turno turno, Usuario usuarioNeg, long idProceso) throws HermodException {
        return hermod.crearTurnoDependiente(turno, usuarioNeg, idProceso);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addNotaActuacionToTurno(TurnoPk
     * turnoID, NotaActuacion notaActuacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean addNotaActuacionToTurno(TurnoPk turnoID, NotaActuacion notaActuacion) throws HermodException {
        return hermod.addNotaActuacionToTurno(turnoID, notaActuacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSecuencialMasivos(String,
     * String)
     * @ejb:interface-method view-type="both"
     */
    public long getSecuencialMasivos(String circuloId, String year) throws HermodException {
        return hermod.getSecuencialDevolucion(circuloId, year);
    }

    /**
     * @author : Diana Lora
     * @change : Obtiene la secuencia de los recibos de reparto notarial, de
     * acuerdo a los parametros recibidos.
     * @see
     * gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioOrigenTraslado(java.lang.String
     * matricula)
     * @ejb:interface-method view-type="both"
     * @Caso Mantis : 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     * @param year El año en el que se va a expedir el recibo de reparto
     * notarial.
     * @return El secuencial requerido.
     * @throws HermodException
     */
    public long getSecuencialRepartoNotarial(String circuloId, String year) throws HermodException {
        return hermod.getSecuencialRepartoNotarial(circuloId, year);
    }

    /**
     * @author : Julio Alcázar Rivas
     * @change : Metodo para obtener el secuencial masivo
     * @Caso Mantis : 000941
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getSecuencialMasivosExento(String,
     * String)
     * @ejb:interface-method view-type="both"
     */
    public long getSecuencialMasivosExento(String circuloId, String year) throws HermodException {
        return hermod.getSecuencialMasivos(circuloId, year);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public List getItemsChequeoDevoluciones() throws HermodException {
        return hermod.getItemsChequeoDevoluciones();
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public boolean editCausalRestitucion(CausalRestitucion causal) throws HermodException {
        return hermod.editCausalRestitucion(causal);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUltimoTurnoProcesoUsuario(UsuarioPk
     * idUsuario, ProcesoPk idProceso, CirculoPk idCirculo)
     * @ejb:interface-method view-type="both"
     */
    public Turno getUltimoTurnoProcesoUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws HermodException {
        return hermod.getUltimoTurnoProcesoUsuario(idUsuario, idProceso, idCirculo);
    }
        
    //
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUltimoTurnoProcesoUsuario(UsuarioPk
     * idUsuario, ProcesoPk idProceso, CirculoPk idCirculo)
     * @ejb:interface-method view-type="both"
     */
    public String getUltimaSolicitudLiquidacion(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, CirculoPk idCirculo) throws HermodException {
        return hermod.getUltimaSolicitudLiquidacion(idUsuario, idCirculo);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getUltimoTurnoMayorValorUsuario(UsuarioPk
     * idUsuario, ProcesoPk idProceso, CirculoPk idCirculo)
     * @ejb:interface-method view-type="both"
     */
    public Turno getUltimoTurnoMayorValorUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws HermodException {
        return hermod.getUltimoTurnoMayorValorUsuario(idUsuario, idProceso, idCirculo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#incrementarIntentoReimpresionRecibo(SolicitudPk
     * idSolicitud)
     * @ejb:interface-method view-type="both"
     */
    public void incrementarIntentoReimpresionRecibo(SolicitudPk idSolicitud) throws HermodException {
        hermod.incrementarIntentoReimpresionRecibo(idSolicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateNotaActuacion(TurnoPk
     * turnoID, NotaActuacion notaActuacion)
     * @ejb:interface-method view-type="both"
     */
    public boolean updateNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion) throws HermodException {
        return hermod.updateNotaActuacion(turnoID, notaActuacion);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public boolean updateUltimaLiquidacion(TurnoPk turnoId, Liquidacion liquidacion) throws HermodException {
        return hermod.updateUltimaLiquidacion(turnoId, liquidacion);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public List getListadoTurnosRestitucionMinutas(String idCir, String idMin) throws HermodException {
        return hermod.getListadoTurnosRestitucionMinutas(idCir, idMin);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#agregarCirculoNotarial(CirculoNotarial)
     * @ejb:interface-method view-type="both"
     */
    public void agregarCirculoNotarial(CirculoNotarial circuloNotarial) throws HermodException {
        hermod.agregarCirculoNotarial(circuloNotarial);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarCirculoNotarial(CirculoNotarial)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarCirculoNotarial(CirculoNotarial circuloNotarial) throws HermodException {
        hermod.eliminarCirculoNotarial(circuloNotarial);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#editarCirculoNotarial(CirculoNotarial)
     * @ejb:interface-method view-type="both"
     */
    public void editarCirculoNotarial(CirculoNotarial circuloNotarial) throws HermodException {
        hermod.editarCirculoNotarial(circuloNotarial);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#consultarCirculoNotarial(CirculoNotarial.CirculoNotarialPk)
     * @ejb:interface-method view-type="both"
     */
    public CirculoNotarial consultarCirculoNotarial(CirculoNotarialPk idCirculo) throws HermodException {
        return hermod.consultarCirculoNotarial(idCirculo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#agregarZonaNotarial(ZonaNotarial)
     * @ejb:interface-method view-type="both"
     */
    public void agregarZonaNotarial(ZonaNotarial zonaNotarial) throws HermodException {
        hermod.agregarZonaNotarial(zonaNotarial);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarZonaNotarial(ZonaNotarial)
     * @ejb:interface-method view-type="both"
     */
    public void eliminarZonaNotarial(ZonaNotarial zonaNotarial) throws HermodException {
        hermod.eliminarZonaNotarial(zonaNotarial);
    }

    /**
     * Crea una operación de englobe o segregación en la solicitud indicada
     *
     * @ejb:interface-method view-type="both"
     */
    public WebSegEngPk crearWebSegEng(WebSegEng operacion, SolicitudPk solID) throws HermodException {
        return hermod.crearWebSegEng(operacion, solID);
    }

    /**
     * Elimina una operación de englobe o segregación dado su identificador
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminarWebSegEng(WebSegEngPk operacionID) throws HermodException {
        return hermod.eliminarWebSegEng(operacionID);
    }

    /**
     * Actualiza una operación de englobe o segregación dado su identificador y
     * nuevos datos
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarWebSegEng(WebSegEngPk operacionID, WebSegEng operacion) throws HermodException {
        return hermod.actualizarWebSegEng(operacionID, operacion);
    }

    /**
     * Consulta una operación de englobe o segregación dado su identificador
     *
     * @ejb:interface-method view-type="both"
     */
    public WebSegEng consultarWebSegEng(WebSegEngPk operacionID) throws HermodException {
        return hermod.consultarWebSegEng(operacionID);
    }

    /**
     * Consulta una operación de englobe o segregación dada la solicitud
     *
     * @ejb:interface-method view-type="both"
     */
    public List getWebSegEngBySolicitud(SolicitudPk solicitudID) throws HermodException {
        return hermod.getWebSegEngBySolicitud(solicitudID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#agregarNotariaReparto(OficinaOrigen)
     * @ejb:interface-method view-type="both"
     */
    public void agregarNotariaReparto(OficinaOrigen notaria) throws HermodException {
        hermod.agregarNotariaReparto(notaria);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarAnulacionTurno(Turno.TurnoPk)
     * @ejb:interface-method view-type="both"
     */
    public boolean validarAnulacionTurno(TurnoPk idTurno) throws HermodException {
        return hermod.validarAnulacionTurno(idTurno);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#anularTurno(Turno)
     * @ejb:interface-method view-type="both"
     */
    public void anularTurno(Turno turno) throws HermodException {
        hermod.anularTurno(turno);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#habilitarTurno(Turno)
     * @ejb:interface-method view-type="both"
     */
    public void habilitarTurno(Turno turno) throws HermodException {
        hermod.habilitarTurno(turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTextoImprimible(TextoImprimible.TextoImprimiblePk)
     * @ejb:interface-method view-type="both"
     */
    public TextoImprimible getTextoImprimible(TextoImprimiblePk idTexto) throws HermodException {
        return hermod.getTextoImprimible(idTexto);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addLiquidacionToSolicitudCertificado(SolicitudCertificado.SolicitudPk,
     * LiquidacionTurnoCertificado)
     * @ejb:interface-method view_type="both"
     */
    public LiquidacionTurnoCertificado addLiquidacionToSolicitudCertificado(gov.sir.core.negocio.modelo.SolicitudPk solicitudId, LiquidacionTurnoCertificado liquidacion) throws HermodException {
        return hermod.addLiquidacionToSolicitudCertificado(solicitudId, liquidacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnoPadre(Turno.TurnoPk)
     * @ejb:interface-method view_type="both"
     */
    public Turno getTurnoPadre(gov.sir.core.negocio.modelo.TurnoPk turnoID) throws HermodException {
        return hermod.getTurnoPadre(turnoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getImprimiblesPendientesByWfId(java.lang.String,
     * java.lang.String )
     * @ejb:interface-method view_type="both"
     */
    public List getImprimiblesPendientesByWfId(String turno_WfId, String tipoImprimibleId) throws HermodException {
        return hermod.getImprimiblesPendientesByWfId(turno_WfId, tipoImprimibleId);
    } // end-method
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#eliminarActos(java.lang.String) 
     * @ejb:interface-method view_type="both"
     */
    public void eliminarActos(String idSolicitud) throws HermodException{
        hermod.eliminarActos(idSolicitud);
    }
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getListaProcesosRelacion()
     * @ejb:interface-method view-type="both"
     */
    public List getListaProcesosRelacion() throws HermodException {
        return hermod.getListaProcesosRelacion();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarMarcaRevocatoriaTurno(Turno
     * turno)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarMarcaRevocatoriaTurno(Turno turno) throws HermodException {
        return hermod.actualizarMarcaRevocatoriaTurno(turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#consultarTurnosAReanotar()
     * @ejb:interface-method view-type="both"
     */
    public List consultarTurnosAReanotar(Circulo circulo) throws HermodException {
        return hermod.consultarTurnosAReanotar(circulo);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#reanotarTurno(Turno
     * turno, HashTable parametros)
     * @ejb:interface-method view-type="both"
     */
    public boolean reanotarTurno(Turno turno, Hashtable parametros) throws HermodException {
        return hermod.reanotarTurno(turno, parametros);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateLastTurnoHistoria(Turno
     * turno)
     * @ejb:interface-method view-type="both"
     */
    public void updateLastTurnoHistoria(Turno turno) throws HermodException {
        hermod.updateLastTurnoHistoria(turno);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateTurnoReimpresionCertificado(Turno
     * turno, Usuario, gov.sir.core.negocio.modelo.Folio)
     * @ejb:interface-method view-type="both"
     */
    public void updateTurnoReimpresionCertificado(Turno turno, Usuario usuario, Folio folio) throws HermodException {
        hermod.updateTurnoReimpresionCertificado(turno, usuario, folio);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosAReasignar(Estacion
     * estacion, Fase fase)
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosAReasignar(Estacion estacion, Fase fase) throws HermodException {
        return hermod.getTurnosAReasignar(estacion, fase);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#reasignarTurno(Turno
     * turno , Estacion estacionDestino)
     * @ejb:interface-method view-type="both"
     */
    public boolean reasignarTurno(Turno turno, Estacion estacionDestino) throws HermodException {
        return hermod.reasignarTurno(turno, estacionDestino);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarTurnoEjecucion(TurnoEjecucion
     * turnoEjecucion)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarTurnoEjecucion(TurnoEjecucion turnoEjecucion) throws HermodException {
        return hermod.actualizarTurnoEjecucion(turnoEjecucion);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public boolean getTurnoEjecucionTurnoIndividual(Estacion estacion, Fase fase, Circulo circulo, String idworkflow) throws HermodException {
        return hermod.getTurnoEjecucionTurnoIndividual(estacion, fase, circulo, idworkflow);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#reanotarTurnoModeloOperativo(String
     * idTurno, String notificationId, String fase, String resultado, String
     * estacionAsignada, Usuario usuarioSir)
     * @ejb:interface-method view-type="both"
     */
    public boolean reanotarTurnoModeloOperativo(String idTurno, String notificationId, String fase, String resultado, String estacionAsignada, Usuario usuarioSir) throws HermodException {
        return hermod.reanotarTurnoModeloOperativo(idTurno, notificationId, fase, resultado, estacionAsignada, usuarioSir);
    }
    /**.
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocumentoCorregido(idDocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public List getDocumentoCorregido(String idDocumentoPago) throws HermodException {
        return hermod.getDocumentoCorregido(idDocumentoPago);
    }
    /**.
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getNoCuentabyDocumentoPago(idDocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public String getNoCuentabyDocumentoPago(String idDocumentoPago) throws HermodException {
        return hermod.getNoCuentabyDocumentoPago(idDocumentoPago);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarUsuarioAtiendeTurnoHistoria(Turno
     * turno, String nombreFase, Usuario usuarioAtiende)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarUsuarioAtiendeTurnoHistoria(Turno turno, String nombreFase, Usuario usuarioAtiende) throws HermodException {
        return hermod.actualizarUsuarioAtiendeTurnoHistoria(turno, nombreFase, usuarioAtiende);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosByUsuarioRol(Circulo
     * circulo, Usuario usuario, Rol rol)
     * @ejb:interface-method view-type="both"
     */
    public Turno crearTurnoRepartoNotarial(SolicitudRepartoNotarial solicitud, String idEstacion, Usuario usuarioSir) throws HermodException {
        return hermod.crearTurnoRepartoNotarial(solicitud, idEstacion, usuarioSir);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public Message crearWFTurno(Turno turno) throws HermodException {
        return hermod.crearWFTurno(turno);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public String obtenerEstacionTurno(Hashtable m, String idCirculo) throws HermodException {
        return hermod.obtenerEstacionTurno(m, idCirculo);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public void guardarInfoTurnoEjecucion(Hashtable m, String estacionAsignada, Turno turno, Usuario usuarioSir) throws HermodException {
        hermod.guardarInfoTurnoEjecucion(m, estacionAsignada, turno, usuarioSir);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean avanzarTurnoNuevoNormal(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        return hermod.avanzarTurnoNuevoNormal(turno, fase, parametros, usuario);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosNuevoByFechaYCirculo(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws HermodException {
        return hermod.getTurnosNuevoByFechaYCirculo(proceso, fase, fecha, circulo);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean avanzarMasivoTurnosNuevo(List turnos, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        return hermod.avanzarMasivoTurnosNuevo(turnos, fase, parametros, usuario);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean avanzarTurnoNuevoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        return hermod.avanzarTurnoNuevoPush(turno, fase, parametros, usuario);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean avanzarTurnoNuevoPop(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        return hermod.avanzarTurnoNuevoPop(turno, fase, parametros, usuario);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean avanzarTurnoNuevoEliminandoPush(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        return hermod.avanzarTurnoNuevoEliminandoPush(turno, fase, parametros, usuario);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean avanzarTurnoNuevoCualquiera(Turno turno, Fase fase, Hashtable parametros, Usuario usuario) throws HermodException {
        return hermod.avanzarTurnoNuevoCualquiera(turno, fase, parametros, usuario);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public Hashtable realizarRepartoExtraordinarioCirculo(Circulo circ, Usuario usuario, boolean tipo, String[] idsExtraordinarios) throws HermodException {
        return hermod.realizarRepartoExtraordinarioCirculo(circ, usuario, tipo, idsExtraordinarios);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public RepartoNotarial getRepartoNotarialById(RepartoNotarialPk repartoNotarial) throws HermodException {
        return hermod.getRepartoNotarialById(repartoNotarial);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosFolioByTurno(Turno turno) throws HermodException {
        return hermod.getTurnosFolioByTurno(turno);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getSolicitudFolioMigBySolicitud(Solicitud solicitud) throws HermodException {
        return hermod.getSolicitudFolioMigBySolicitud(solicitud);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosFolioNoMigrados(Turno turno) throws HermodException {
        return hermod.getTurnosFolioNoMigrados(turno);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosFolioTramite(Turno turno) throws HermodException {
        return hermod.getTurnosFolioNoMigrados(turno);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosFolioTramite(Folio folio) throws HermodException {
        return hermod.getTurnosFolioTramite(folio);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean addTurnosFolioMigToTurno(Turno turno, List turnosFolioMig) throws HermodException {
        return hermod.addTurnosFolioMigToTurno(turno, turnosFolioMig);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean addTramiteTurnosFolioMigToTurno(List turnosFolioMig) throws HermodException {
        return hermod.addTramiteTurnosFolioMigToTurno(turnosFolioMig);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean addSolicitudFolioMigToTurno(Solicitud solicitud, List solicitudFolioMig) throws HermodException {
        return hermod.addSolicitudFolioMigToTurno(solicitud, solicitudFolioMig);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean removeSolicitudFolioMig(Solicitud solicitud) throws HermodException {
        return hermod.removeSolicitudFolioMig(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarTurnoModoBloqueo(gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     */
    public void actualizarTurnoModoBloqueo(Turno turno) throws HermodException {
        hermod.actualizarTurnoModoBloqueo(turno);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosSirMig(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws HermodException {
        return hermod.getTurnosSirMig(estacion, rol, usuario, proceso, fase, circulo);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean isFolioSirMigTurnoFolio(Folio folio) throws HermodException {
        return hermod.isFolioSirMigTurnoFolio(folio);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean isFolioValidoSirMig(Folio folio) throws HermodException {
        return hermod.isFolioValidoSirMig(folio);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean isFolioSirMigTurnoFolioTramite(Folio folio) throws HermodException {
        return hermod.isFolioSirMigTurnoFolioTramite(folio);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public Hashtable realizarRepartoCirculoNotarialExtraordinario(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo, String[] idsExtraordinaro) throws HermodException {
        return hermod.realizarRepartoCirculoNotarialExtraordinario(circuloNotarial, usuario, tipo, idsExtraordinaro);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public Hashtable realizarRepartoCirculoNotarialOrdinario(CirculoNotarial circuloNotarial, Usuario usuario) throws HermodException {
        return hermod.realizarRepartoCirculoNotarialOrdinario(circuloNotarial, usuario);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public String consultarLastSecuencialCirculoNotarial(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo) throws HermodException {
        return hermod.consultarLastSecuencialCirculoNotarial(circuloNotarial, usuario, tipo);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public List getSolicitudesByTurnoAnterior(Turno turno) throws HermodException {
        return hermod.getSolicitudesByTurnoAnterior(turno);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public List getPantallasAdministrativasByPagina(String pagina)
            throws HermodException {
        return hermod.getPantallasAdministrativasByPagina(pagina);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public RolPantalla addRolPantallasAdministrativa(RolPantalla rolPantalla)
            throws HermodException {
        return hermod.addRolPantallasAdministrativa(rolPantalla);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public boolean deleteRolPantallasAdministrativa(RolPantalla rolPantalla)
            throws HermodException {
        return hermod.deleteRolPantallasAdministrativa(rolPantalla);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public void addRolesPantallasAdministrativas(List rolesPantallas) throws HermodException {
        hermod.addRolesPantallasAdministrativas(rolesPantallas);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public boolean deleteRolPantallasAdministrativa(List rolesPantallas) throws HermodException {
        return hermod.deleteRolPantallasAdministrativa(rolesPantallas);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public List obtenerRolPantallasAdministrativasPorRolPagina(List roles, String pagina)
            throws HermodException {
        return hermod.obtenerRolPantallasAdministrativasPorRolPagina(roles, pagina);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public boolean modificarRespuestaUltimaFase(Turno turno, Fase fase, String respuesta)
            throws HermodException {
        return hermod.modificarRespuestaUltimaFase(turno, fase, respuesta);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public List getListaPorRangos(String idListado, Object[] parametros, int rangoInf, int rangoSup) throws HermodException {
        return hermod.getListaPorRangos(idListado, parametros, rangoInf, rangoSup);
    }

    /**
     * @ejb:interface-method view-type="both"
     */
    public int getTamanioLista(String idListado, Object[] parametros) throws HermodException {
        return hermod.getTamanioLista(idListado, parametros);
    }

    /**
     *
     * @ejb:interface-method view-type="both"
     */
    public boolean updateValorLiquidacionDevolucion(Liquidacion liquidacion) throws HermodException {
        return hermod.updateValorLiquidacionDevolucion(liquidacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminarNotasInformativasUltimaFaseTurno()
     * @ejb:interface-method view-type="both"
     */
    public void eliminarNotasInformativasUltimaFaseTurno(TurnoPk turnoID) throws HermodException {
        hermod.eliminarNotasInformativasUltimaFaseTurno(turnoID);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarMarcaTurnoNacional(Turno
     * turno)
     * @ejb:interface-method view-type="both"
     */
    public boolean actualizarMarcaTurnoNacional(Turno turno) throws HermodException {
        return hermod.actualizarMarcaTurnoNacional(turno);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getCirculosPortal()
     * @ejb:interface-method view-type="both"
     */
    public Circulo getCirculoPortal() throws HermodException {
        return hermod.getCirculoPortal();
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#updateSolicitud()
     * @ejb:interface-method view-type="both"
     */
    public Solicitud updateSolicitud(Solicitud solicitud) throws HermodException {
        return hermod.updateSolicitud(solicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#obtenerFaseInicial(String
     * idProceso)
     * @ejb:interface-method view-type="both"
     */
    public InicioProcesos obtenerFaseInicial(String idProceso) throws HermodException {
        return hermod.obtenerFaseInicial(idProceso);
    }

    /**
     * @see
     * ggov.sir.hermod.interfaz.HermodServiceInterface#generarReporteJasper(String,
     * String, HashMap)
     * @ejb:interface-method view-type="both"
     */
    public byte[] generarReporteJasper(String nombreReporte,
            String rutaReportes, HashMap parametrosJasper) throws HermodException {
        return hermod.generarReporteJasper(nombreReporte, rutaReportes, parametrosJasper);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addTurnoHistoriaToTurnoReasignacion(gov.sir.core.negocio.modelo.Turno,
     * gov.sir.core.negocio.modelo.TurnoHistoria)
     * @ejb:interface-method view-type="both"
     */
    public void addTurnoHistoriaToTurnoReasignacion(Turno turno, TurnoHistoria turnoHistoria) throws HermodException {
        hermod.addTurnoHistoriaToTurnoReasignacion(turno, turnoHistoria);
    }

    /**
     * @see
     * ggov.sir.hermod.interfaz.HermodServiceInterface#getTestamentoByID(String)
     * @ejb:interface-method view-type="both"
     */
    public Testamento getTestamentoByID(String idSolicitud) throws HermodException {
        return hermod.getTestamentoByID(idSolicitud);
    }

    /**
     * @see
     * ggov.sir.hermod.interfaz.HermodServiceInterface#removeTestadorFromTestamento(gov.sir.core.negocio.modelo.TestamentoCiudadanoPk)
     * @ejb:interface-method view-type="both"
     */
    public void removeTestadorFromTestamento(TestamentoCiudadanoPk testamentoCiudadanoID) throws HermodException {
        hermod.removeTestadorFromTestamento(testamentoCiudadanoID);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#existeConsignacionDevolucion(gov.sir.core.negocio.modelo.DocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public boolean existeConsignacionDevolucion(String idDocumentoPago) throws HermodException {
        return hermod.existeConsignacionDevolucion(idDocumentoPago);
    }

    /**
     * @see
     * ggov.sir.hermod.interfaz.HermodServiceInterface#actualizaSolitiud(gov.sir.core.negocio.modelo.SolicitudPk,java.util.List)
     * @ejb:interface-method view-type="both"
     */
    public void actualizaSolicitud(Solicitud solicitud, List consignaciones) throws HermodException {
        hermod.actualizaSolicitud(solicitud, consignaciones);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getDocsPagosDevolucion(gov.sir.core.negocio.modelo.DocumentoPago)
     * @ejb:interface-method view-type="both"
     */
    public boolean getDocsPagosDevolucion(DocumentoPago doc) throws HermodException {
        return hermod.getDocsPagosDevolucion(doc);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addBancoCirculo(gov.sir.core.negocio.modelo.BancosXCirculo)
     * @ejb:interface-method view-type="both"
     */
    public boolean addBancoCirculo(BancosXCirculo bancoXCirculo) throws HermodException {
        return hermod.addBancoCirculo(bancoXCirculo);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getBancosXCirculo()
     * @ejb:interface-method view-type="both"
     */
    public List getBancosXCirculo(String idCirculo) throws HermodException {
        return hermod.getBancosXCirculo(idCirculo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#eliminaBancoCirculo(gov.sir.core.negocio.modelo.BancosXCirculo)
     * @ejb:interface-method view-type="both"
     */
    public boolean eliminaBancoCirculo(BancosXCirculo bancoXCirculo) throws HermodException {
        return hermod.eliminaBancoCirculo(bancoXCirculo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#activarBancoPrincipal(gov.sir.core.negocio.modelo.BancosXCirculo)
     * @ejb:interface-method view-type="both"
     */
    public boolean activarBancoPrincipal(BancosXCirculo bancoXCirculo) throws HermodException {
        return hermod.activarBancoPrincipal(bancoXCirculo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getRelacionBancosCirculo()
     * @ejb:interface-method view-type="both"
     */
    public List getRelacionBancosCirculo() throws HermodException {
        return hermod.getRelacionBancosCirculo();
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarFolioTurnoReanotacion()
     * @ejb:interface-method view-type="both"
     */
    public boolean validarFolioTurnoReanotacion(String idMatricula, Turno turno) throws HermodException {
        return hermod.validarFolioTurnoReanotacion(idMatricula, turno);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#reanotarTurno()
     * @ejb:interface-method view-type="both"
     */
    public void reanotarTurno(Turno turno, Nota nota, Usuario calificador, Usuario usuario, Estacion estacion) throws HermodException {
        hermod.reanotarTurno(turno, nota, calificador, usuario, estacion);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#removeNotaDevolutivaFromTurno()
     * @ejb:interface-method view-type="both"
     */
    public void removeNotaDevolutivaFromTurno(TurnoPk turnoID, Nota nota) throws HermodException {
        hermod.removeNotaDevolutivaFromTurno(turnoID, nota);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizaNotaDevolutiva()
     * @ejb:interface-method view-type="both"
     */
    public void actualizaNotaDevolutiva(TurnoPk turnoID, Nota nota, Nota notaOld) throws HermodException {
        hermod.actualizaNotaDevolutiva(turnoID, nota, notaOld);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#updateSubtipoSolicitud()
     * @ejb:interface-method view-type="both"
     */
    public void updateSubtipoSolicitud(String idSubtipoSolicitud, String idSolicitud) throws HermodException {
        hermod.updateSubtipoSolicitud(idSubtipoSolicitud, idSolicitud);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosCertificadoPosteriores()
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosCertificadoPosteriores(String idMatricula, Turno turno) throws HermodException {
        return hermod.getTurnosCertificadoPosteriores(idMatricula, turno);
    }


    /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
     */
    /**
     * @ejb:interface-method view-type="both"
     */
    public Hashtable crearTurnoTransaccion(Transaccion transaccion, Ciudadano solicitante, String idUsuario, String idBanco) throws HermodException {
        return hermod.crearTurnoTransaccion(transaccion, solicitante, idUsuario, idBanco);
    }

    /**
     * * Modificacion en: 2010/02/23 by jvenegas
     *
     * @ejb:interface-method view-type="both"
     */
    public Hashtable procesarPagoVUR(Transaccion transaccion, Ciudadano solicitante, String idBanco, String idUsuarioSIR, String cedula) throws HermodException {
        return hermod.procesarPagoVUR(transaccion, solicitante, idBanco, idUsuarioSIR, cedula);
    }

    /**
     * * Modificacion en: 2010/02/23 by jvenegas
     *
     * @ejb:interface-method view-type="both"
     */
    public ImprimibleCertificado getImprimible(Integer idImprimible) throws HermodException {
        return hermod.getImprimible(idImprimible);
    }

    /**
     * * Modificacion en: 2010/02/23 by jvenegas
     *
     * @ejb:interface-method view-type="both"
     */
    public ImprimibleRecibo getRecibo(Integer idImprimible) throws HermodException {
        return hermod.getRecibo(idImprimible);
    }

    /**
     * * Modificacion en: 2010/02/23 by jvenegas
     *
     * @ejb:interface-method view-type="both"
     */
    public List getTurnosByFechaAndCirculoMinusMasivos(Proceso proceso, Fase fase, Date fecha, Circulo circulo) throws HermodException {
        return hermod.getTurnosByFechaAndCirculoMinusMasivos(proceso, fase, fecha, circulo);
    }

    /**
     * * Modificacion en: 2010/02/23 by jvenegas
     *
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnoBySolicitudPortal(gov.sir.core.negocio.modelo.Solicitud.SolicitudPk)
     * @ejb:interface-method view-type="both"
     */
    public Turno getTurnoBySolicitudPortal(gov.sir.core.negocio.modelo.SolicitudPk solID) throws HermodException {
        return hermod.getTurnoBySolicitudPortal(solID);
    }

    /**
     * * Modificacion en: 2010/02/23 by jvenegas
     *
     * @ejb:interface-method view-type="both"
     */
    public byte[] getDatosImprimible(Integer idImprimible) throws HermodException {
        return hermod.getDatosImprimible(idImprimible);
    }

    /*Final Adiciona Funcionalidad Boton de Pago*/
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTurnosDevolucion(gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both" Obtiene los turnos de devolución
     * asociados con el turno ingresado como parámetro.
     * @author: Julio Alcazar
     * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno -
     * Turnos Devolucion Negados
     */
    public List getTurnosDevolucion(Turno turno) throws HermodException {
        return hermod.getTurnosDevolucion(turno);
    }

    /**
     * @see
     * gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioDestinoTraslado(java.lang.String
     * matricula)
     * @ejb:interface-method view-type="both"
     * @author : Julio Alcazar
     * @change : Se consulta la informacion destino de una matricula trasladada
     * Caso Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de Folios
     * V2
     */
    public Traslado getFolioDestinoTraslado(String matricula) throws HermodException {
        return hermod.getFolioDestinoTraslado(matricula);
    }

    /**
     * @see
     * gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioOrigenTraslado(java.lang.String
     * matricula)
     * @ejb:interface-method view-type="both"
     * @author : Julio Alcazar
     * @change : Se consulta la informacion destino de una matricula trasladada
     * Caso Mantis : 0007676: Acta - Requerimiento No 247 - Traslado de Folios
     * V2
     */
    public Traslado getFolioOrigenTraslado(String matricula) throws HermodException {
        return hermod.getFolioOrigenTraslado(matricula);
    }

    /**
     * @see
     * gov.sir.forseti.interfaz.ForsetiServiceInterface#getFolioOrigenTraslado(java.lang.String
     * matricula)
     * @ejb:interface-method view-type="both"
     * @author : Edgar Lora
     * @change : Obtiene la secuencia de los recibos de reparto notarial, de
     * acuerdo a los parametros recibidos.
     * @Caso Mantis : 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     */
    public long getSecuencialRepartoNotarialRecibo(String circuloId, String year) throws HermodException {
        return hermod.getSecuencialRepartoNotarialRecibo(circuloId, year);
    }

    /**
     * @ejb:interface-method view-type="both"
     * @author : Carlos Torres
     * @change : Obtiene el Objeto SolicitudFolio identificado por sfid
     * @Caso Mantis : 11309: Acta - Requerimiento No 023_453 -
     * Traslado_Masivo_Folios
     */
    public SolicitudFolio getSolicitudFolio(SolicitudFolioPk sfid) throws HermodException {
        return hermod.getSolicitudFolio(sfid);
    }

    /**
     * @ejb:interface-method view-type="both"
     * @author : Carlos Torres
     * @change : En particular actualiza el historial del turno
     * @Caso Mantis : 0014376
     */
    public void addFaseRestitucionTurno(Turno turno, Usuario usuario) throws HermodException {
        hermod.addFaseRestitucionTurno(turno, usuario);
    }

    /**
     * @ejb:interface-method view-type="both"
     * @author : Carlos Torres
     * @change : Obtener pantallas administrativas del usuario
     * @Caso Mantis : 14371: Acta - Requerimiento No
     * 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
     */
    public List obtenerAdministrativasPorRol(Usuario usuario) throws HermodException {
        return hermod.obtenerAdministrativasPorRol(usuario);
    }

    /**
     * @ejb:interface-method view-type="both"
     * @author : Carlos Torres
     * @change : Obtener pantallas administrativas del usuario
     * @Caso Mantis : 14371: Acta - Requerimiento No
     * 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
     */
    public List obtenerPantallasPaginaReportesPorRol(Usuario usuario) throws HermodException {
        return hermod.obtenerPantallasPaginaReportesPorRol(usuario);
    }

    /**
     * @seegov.sir.hermod.interfaz.HermodServiceInterface#desbloquearFolios(gov.sir.core.negocio.modelo.Turno)
     * @ejb:interface-method view-type="both"
     * @author: Daniel Forero @change:
     * 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS.
     */
    public int desbloquearFolios(Turno turno) throws HermodException {
        return hermod.desbloquearFolios(turno);
    }

    /**
     *
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     * @ejb:interface-method view-type="both"
     */
    public boolean isIncentivoRegistral(Date fechaDocumento) throws HermodException {
        return hermod.isIncentivoRegistral(fechaDocumento);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#addCuentaBancaria(gov.sir.core.negocio.modelo.CuentasBancarias)
     * @ejb:interface-method view-type="both"
     */
    public boolean addCuentaBancaria(CuentasBancarias cuentaBancaria) throws HermodException {
        return hermod.addCuentaBancaria(cuentaBancaria);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getCuentasBancarias()
     * @ejb:interface-method view-type="both"
     */
    public List getCuentasBancarias(String idCirculo, String idBanco) throws HermodException {
        return hermod.getCuentasBancarias(idCirculo, idBanco);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCuentasBancariasXCirculo(gov.sir.core.negocio.modelo.Circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getCuentasBancariasXCirculo(Circulo circulo) throws HermodException {
        return hermod.getCuentasBancariasXCirculo(circulo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarEstadoCtaBancaria()
     * @ejb:interface-method view-type="both"
     */
    public void actualizarEstadoCtaBancaria(String idCirculo, String idBanco, String nroCuenta, boolean estado) throws HermodException {
        hermod.actualizarEstadoCtaBancaria(idCirculo, idBanco, nroCuenta, estado);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#getCanalesRecaudo()
     * @ejb:interface-method view-type="both"
     */
    public List getCanalesRecaudo(boolean flag) throws HermodException {
        return hermod.getCanalesRecaudo(flag);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCanalesRecaudoXCirculo(Circulo
     * circulo)
     * @ejb:interface-method view-type="both"
     */
    public List getCanalesRecaudoXCirculo(Circulo circulo) throws HermodException {
        return hermod.getCanalesRecaudoXCirculo(circulo);
    }

    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#addCanalRecaudo()
     * @ejb:interface-method view-type="both"
     */
    public boolean addCanalRecaudo(CanalesRecaudo canalesRecaudo) throws HermodException {
        return hermod.addCanalRecaudo(canalesRecaudo);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarEstadoCanalRecaudo()
     * @ejb:interface-method view-type="both"
     */
    public void actualizarEstadoCanalRecaudo(int idCanal, boolean estado) throws HermodException {
        hermod.actualizarEstadoCanalRecaudo(idCanal, estado);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validaCampoBancoFranquicia(gov.sir.core.negocio.modelo.TipoPago)
     * @ejb:interface-method view-type="both"
     */
    public boolean validaCampoBancoFranquicia(TipoPago tipoPago) throws HermodException {
        return hermod.validaCampoBancoFranquicia(tipoPago);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#camposCapturaXFormaPago(java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List camposCapturaXFormaPago(String formaPagoId) throws HermodException {
        return hermod.camposCapturaXFormaPago(formaPagoId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCanalRecaudoByID(java.lang.Integer)
     * @ejb:interface-method view-type="both"
     */
    public CanalesRecaudo getCanalRecaudoByID(int canalRecaudoId) throws HermodException {
        return hermod.getCanalRecaudoByID(canalRecaudoId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getTipoPagoByID(java.lang.Integer)
     * @ejb:interface-method view-type="both"
     */
    public TipoPago getTipoPagoByID(int tipoPagoId) throws HermodException {
        return hermod.getTipoPagoByID(tipoPagoId);
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCuentasBancariasByID(java.lang.Integer)
     * @ejb:interface-method view-type="both"
     */
    public CuentasBancarias getCuentasBancariasByID(int cuentaBancariaId) throws HermodException {
        return hermod.getCuentasBancariasByID(cuentaBancariaId);
    }

    
    public String getIdCtpByParamenters(String formaPagoId, String idCirculo, int idCanal, String idCb) throws HermodException {
        return hermod.getIdCtpByParamenters(formaPagoId, idCirculo, idCanal, idCb);
}
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCirculoTipoPagoByID(java.lang.Integer)
     * @ejb:interface-method view-type="both"
     */
    public CirculoTipoPago getCirculoTipoPagoByID(int idCtp) throws HermodException {
        return hermod.getCirculoTipoPagoByID(idCtp);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCuentasBancariasXCirculoCanalForma(gov.sir.core.negocio.modelo.Circulo, java.lang.String, java.lang.String)
     * @ejb:interface-method view-type="both"
     */
    public List getCuentasBancariasXCirculoCanalForma(Circulo circulo, String idCanalRecaudo, String idFormaPago) throws HermodException {
        return hermod.getCuentasBancariasXCirculoCanalForma(circulo, idCanalRecaudo, idFormaPago);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#actualizarEstadoCtp()
     * @ejb:interface-method view-type="both"
     */
    public void actualizarEstadoCtp(String idCtp, boolean estado) throws HermodException {
        hermod.actualizarEstadoCtp(idCtp, estado);
    }
    
    
    public void deleteLiquidacionTurnoRegistro(String idLiquidacion, String idSolicitud) throws HermodException {
        hermod.deleteLiquidacionTurnoRegistro(idLiquidacion, idSolicitud);
    }
    
    
    
    /**
     * 
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#validarSiTurnoFueRadicadoXREL()
     *  @ejb:interface-method view-type="both"
     */
    public boolean validarSiTurnoFueRadicadoXREL(String trnoIdWorkFlow) throws HermodException{
        return false;
    }
    
  /**
     * 
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getIdBanco(java.lang.String)
     *  @ejb:interface-method view-type="both"
     */
    public String getIdBanco(String banco) throws HermodException{
        return hermod.getIdBanco(banco);
    }
    
     /**
     * 
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getCanalPago(java.lang.String)
     *  @ejb:interface-method view-type="both"
     */
    public String getCanalPago (String canal) throws HermodException{
        return hermod.getCanalPago(canal);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#restringirAddPago(java.lang.String, java.lang.String, java.lang.String) 
     *  @ejb:interface-method view-type="both"
     */
    public boolean restringirAddPago(String idBanco, String canal, String numero) throws HermodException{
        return hermod.restringirAddPago(idBanco, canal, numero);
    }
    
    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#restringirAddPagoByFP(java.lang.String, java.lang.String, java.lang.String) 
     *  @ejb:interface-method view-type="both"
     */
    public boolean restringirAddPagoByFP(String formaPago, String canal, String numero) throws HermodException{
        return hermod.restringirAddPagoByFP(formaPago, canal, numero);
    }
    
    
    /**
     * @see gov.sir.hermod.interfaz.HermodServiceInterface#updateStatusRel(Turno)
     * @ejb:interface-method view-type="both"
     */
    public void updateStatusRel(Turno turno) throws HermodException {
        hermod.updateStatusRel(turno);
    }
        
}
