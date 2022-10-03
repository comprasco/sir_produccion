package gov.sir.hermod.dao.impl.jdogenie;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import com.versant.core.common.metadata.parser.JdoQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Random;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.ReproduccionSellos;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.NotaPk;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.PermisoCorreccion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Recurso;
import gov.sir.core.negocio.modelo.RecursoPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubAtencionSubSolicitud;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadano;
import gov.sir.core.negocio.modelo.TipoActoPk;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoHistoriaPk;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.ControlMatricula;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CIniciacionProcesos;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COficio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CReparto;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoRecepcion;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.constantes.CTurno;

import gov.sir.core.negocio.modelo.util.InfoLog;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemDevEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CheckItemEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ControlMatriculaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced;


import gov.sir.core.negocio.modelo.jdogenie.FaseEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NotaActuacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NotaActuacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.NotaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NotaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.PermisoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PermisoCorreccionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RecursoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RecursoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCheckedItemEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudPermisoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoCiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TestamentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoDerechoRegEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecepcionPeticionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRecursoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoDerivadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEjecucionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhancedPk;

import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicDateFormatComparator;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.TurnosDAO;
import gov.sir.hermod.workflow.HermodWFFactory;
import gov.sir.hermod.workflow.Message;
import gov.sir.hermod.workflow.Processor;
import gov.sir.hermod.workflow.WFException;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.EstacionDAO;
import org.auriga.core.modelo.daoObjects.RolDAO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.sas.SASKeys;
import org.auriga.smart.servicio.ServiceLocator;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.eventos.administracion.EvnTrasladoTurno;
import gov.sir.core.negocio.acciones.administracion.AnTrasladoTurno;
import gov.sir.core.negocio.acciones.correccion.ANCorreccion;
import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.negocio.modelo.ReproduccionSellosPk;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;

import gov.sir.core.negocio.modelo.constantes.*;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CTurnoPortal;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.hermod.HermodException;
/*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Se abilita el uso de la clase SolicitudesDAO
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 */
import gov.sir.hermod.dao.SolicitudesDAO;
import gov.sir.hermod.gdocumental.integracion.SGD;
import java.text.SimpleDateFormat;

/**
 * @autor HGOMEZ
 * @mantis 13176
 * @Requerimiento 061_453_Requerimiento_Auditoria
 * @descripcion Imports necesarios para auditar cambios en Minuta.
 */
import gov.sir.core.util.MinutaEnhancedAnteriorSingletonUtil;
import gov.sir.core.util.MinutaEnhancedNewSingletonUtil;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleCallableStatement;
import org.auriga.core.modelo.daoObjects.EstacionDAO;
import org.auriga.core.modelo.transferObjects.Jerarquia;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * Clase para el manejo de turnos dentro de los procesos de la aplicaci?n.
 *
 * @author mrios
 * @author mortiz
 * @author dlopez
 * @author dsalas
 * @author ppabon
 */
public class JDOTurnosDAO implements TurnosDAO {

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Constante jerarquia de circulos
     */
    private static final String JERARQUIA_CIRCULOS = "JER_CIRCULOS";

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Constante servicio FENRIR
     */
    private static final String SERVICIO_FENRIR = "gov.sir.fenrir";

    /**
     * @autor HGOMEZ
     * @mantis 13176
     * @Requerimiento 061_453_Requerimiento_Auditoria
     * @descripcion Mantiene la informaci?n del usuario.
     */
    static private java.util.Map infoUsuario;

    /**
     * Crea una nueva instancia de la clase JDOTurnosDAO.
     */
    public JDOTurnosDAO() {
    }

    /**
     * Crea un nuevo turno manual con la informaci?n suministrada en los
     * par?metros
     *
     * @param p El pago asociado al turno
     * @param sAnio El a?o del turno
     * @param sCirculo El c?rculo del turno
     * @param sProceso El proceso del turno
     * @param idTurno El identificador del turno
     * @param dFechaInicio
     * @param user
     * @return
     * @throws DAOException
     */

//    CARLOS
        public String encontrarBancoByIdBanco(String bancoId) throws DAOException{       
        System.out.print("El idTipoDocPago es encontrarBancoByIdBanco :" + bancoId);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT BNCO_NOMBRE FROM SIR_OP_BANCO WHERE ID_BANCO = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, bancoId);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("BNCO_NOMBRE");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
        public String encontrarIdBancoByCuentaBancaria(String cuentaBancaria) throws DAOException{       
        System.out.print("El idTipoDocPago es encontrarIdBancoByCuentaBancaria :" + cuentaBancaria);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT CB_ID_BANCO FROM SIR_OP_CUENTAS_BANCARIAS WHERE CB_NRO_CUENTA = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, cuentaBancaria);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("CB_ID_BANCO");
            }
           
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
        
        public void setStatusREL(String status, String url, String idWorkflow) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String sql = "UPDATE SIR_OP_TURNO SET TRNO_REL_STATUS = '"+status+"', TRNO_REL_ENDPOINT = '"+url+"' WHERE TRNO_ID_WORKFLOW = '"+idWorkflow+"'";
        
        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
    }
    
        public String encontrarDcpgNoConsignacionByIdDocumentoPago(String idDocPago) throws DAOException{       
        System.out.print("El idTipoDocPago es encontrarDcpgNoConsignacionByIdDocumentoPago :" + idDocPago);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT DCPG_NO_CONSIGNACION FROM SIR_OP_DOCUMENTO_PAGO WHERE ID_DOCUMENTO_PAGO = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocPago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("DCPG_NO_CONSIGNACION");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
        public String encontrarDcpgNoAprovacionByIdDocumentoPago(String idDocPago) throws DAOException{       
        System.out.print("El idTipoDocPago es encontrarDcpgNoAprovacionByIdDocumentoPago :" + idDocPago);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT DCPG_NO_APROBACION FROM SIR_OP_DOCUMENTO_PAGO WHERE ID_DOCUMENTO_PAGO = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocPago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("DCPG_NO_APROBACION");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
    
        public String encontrarTipoDocPagoByIdTipoDocPago(String idTipoDocPago) throws DAOException{       
        System.out.print("El idTipoDocPago es encontrarTipoDocPagoByIdTipoDocPago :" + idTipoDocPago);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT TPDP_NOMBRE FROM SIR_OP_TIPO_DOC_PAGO WHERE ID_TIPO_DOC_PAGO = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idTipoDocPago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("TPDP_NOMBRE");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
        public String encontrarIdTipoDocPagoByIdCtp(String idCtp) throws DAOException{       
        System.out.print("El idCtp es encontrarIdTipoDocPagoByIdCtp :" + idCtp);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT ID_TIPO_DOC_PAGO FROM SIR_NE_CIRCULO_TIPO_PAGO WHERE ID_CTP = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idCtp);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();

            while(rs.next()){
                rta = rs.getString("ID_TIPO_DOC_PAGO");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
        public String encontrarCanalRecaudoByIdCr(String idCr) throws DAOException{       
        System.out.print("El idCr es encontrarCanalRecaudoByIdCr :" + idCr);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT CR_NOMBRE_CANAL FROM SIR_OP_CANALES_RECAUDO WHERE CR_ID = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idCr);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("CR_NOMBRE_CANAL");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
        public String encontrarIdCrByIdCtp(String idCtp) throws DAOException{       
        System.out.print("El idCtp es encontrarIdCrByIdCtp :" + idCtp);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT CR_ID FROM SIR_NE_CIRCULO_TIPO_PAGO WHERE ID_CTP = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idCtp);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("CR_ID");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
        
    public String encontrarNumerosCuenta(String idCb) throws DAOException{       
        System.out.print("El id es encontrarNumerosCuenta :" + idCb);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT CB_NRO_CUENTA FROM SIR_OP_CUENTAS_BANCARIAS WHERE CB_ID = ?";        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, String.valueOf(idCb));
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("CB_NRO_CUENTA");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
        public String encontrarIdCbByIdCtp(String idCtp) throws DAOException{
        
        System.out.print("Entro en encontrarCuentaByIdCtp :" + idCtp);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = "";
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT CB_ID FROM SIR_NE_CIRCULO_TIPO_PAGO WHERE ID_CTP = ?";
        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idCtp);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("CB_ID");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
        public String encontrarIdCtpByIdDocumentoPago(String idDocumentoPago) throws DAOException{
        
        System.out.print("El idDocumentoPago en encontrarIdCtpByIdDocumentoPago es: " + idDocumentoPago);
        String rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT ID_CTP FROM SIR_OP_DOCUMENTO_PAGO WHERE ID_DOCUMENTO_PAGO = ?";
        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocumentoPago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("ID_CTP");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
    
    public List encontrarIdDocumentoPagoByIdSolicitud(String idSolicitud) throws DAOException{
        
        System.out.print("El idSolicitud encontrarIdDocumentoPagoByIdSolicitud es: " + idSolicitud);
        List lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT ID_DOCUMENTO_PAGO FROM SIR_OP_APLICACION_PAGO WHERE id_solicitud = ?";
        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idSolicitud);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                System.out.print("el idSolicitud es :" + rs.getString(1));
                lista.add(rs.getString(1));
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return lista;
    }
    
        public String encontrarIdSolicitudByTurno(String turno) throws DAOException{
        
        System.out.print("El turno en encontrarIdSolicitudByTurno es: " + turno);
        String rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT ID_SOLICITUD FROM SIR_OP_TURNO WHERE TRNO_ID_WORKFLOW = ?";
        
        try{
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, turno);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while(rs.next()){
                rta = rs.getString("ID_SOLICITUD");
            }
            
                    
        }catch (SQLException e){
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }catch (JDOObjectNotFoundException e) {
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
                Log.getInstance().error(JDOTurnosDAO.class, e);
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }            
        }
        return rta;
    }
 
    public Turno crearTurno(Pago p, String sAnio, String sCirculo,
            String sProceso, String idTurno, Date dFechaInicio, Usuario user) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        TurnoEnhancedPk tId = new TurnoEnhancedPk();
        PagoEnhancedPk pId = new PagoEnhancedPk();
        Turno t = null;
        TurnoEnhanced te = null;

        try {
            PagoEnhanced pe = PagoEnhanced.enhance(p);

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            pId = pagosDAO.crearPago(pe, user, pm);
            tId = crearTurno(pId, sAnio, sCirculo, sProceso, idTurno, dFechaInicio, user, pm);

            pm.currentTransaction().commit();

            te = this.getTurnoByID(tId, pm);
            this.makeTransientTurno(te, pm);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
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
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().commit();
            }
            pm.close();
        }

        if (te != null) {
            t = (Turno) te.toTransferObject();
        }

        return t;
    }

    /**
     * Crea un turno manual para certificados de acuerdo a la informaci?n
     * recibida en los par?metros
     *
     * @param cpe
     * @param pId
     * @param anio
     * @param idTurno
     * @param faseId
     * @param user
     * @param pm
     * @return ID del turno creado
     * @throws DAOException
     */
    protected TurnoEnhancedPk crearTurno(PagoEnhancedPk pId, String sAnio, String sCirculo,
            String sProceso, String idTurno, Date dFechaInicio, Usuario user,
            PersistenceManager pm) throws DAOException {

        //List notasInf = new ArrayList();
        TurnoEnhanced tr = new TurnoEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        Date fechaFin = new Date();

        try {
            //Encontrar el pago persistente asociado con la solicitud.
            PagoEnhanced p = pagosDAO.getPagoByID(pId, pm);

            if (p == null) {
                throw new DAOException(DAOException.PAGO_NO_ASOCIADO_SOLICITUD);
            }

            //Obtener la solicitud persistente que se va a asociar con el turno.
            SolicitudEnhancedPk sId = new SolicitudEnhancedPk();
            sId.idSolicitud = p.getLiquidacion().getIdSolicitud();

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(sId, pm);

            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA
                        + sId.idSolicitud);
            }

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            CirculoProcesoEnhancedPk cpID = new CirculoProcesoEnhancedPk();
            cpID.anio = sAnio;
            cpID.idCirculo = sCirculo;
            cpID.idProceso = Integer.parseInt(sProceso);

            CirculoProcesoEnhanced cpe = procesosDAO.getCirculoProcesoById(cpID, pm);

            // Se obtiene el objeto persistente correspondiente al usuario
            UsuarioEnhanced userEnhanced = variablesDAO.getUsuarioByLogin(user.getUsername(), pm);

            //Se asocian todos los atributos necesarios para la creaci?n del nuevo turno.
            tr.setIdFase("FINALIZADO");
            tr.setAnio(sAnio);
            tr.setFechaInicio(dFechaInicio);
            tr.setFechaFin(fechaFin);
            tr.setCirculoproceso(cpe);
            tr.setSolicitud(s);
            tr.setLastIdHistoria(0);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            tr.setIdTurno(idTurno);
            tr.setIdWorkflow(sAnio + "-" + cpe.getIdCirculo() + "-"
                    + String.valueOf(cpe.getIdProceso()) + "-"
                    + idTurno);
            tr.setUsuarioDestino(userEnhanced);
            tr.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            pm.makePersistent(tr);
            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Crea el turno de acuerdo a la informaci?n recibida en los par?metros.
     * <p>
     * M?todo privado llamado por este DAO y utilizado en forma transaccional.
     *
     * @return el identificador del objeto Turno creado.
     * @param cpe C?rculo Proceso asociado con el Turno que va a ser creado.
     * @param pId Identificador del Pago asociado con el Turno que va a ser
     * creado.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param user <code>Usuario</code> iniciador del <code>Proceso</code>
     * @param pm PersistenceManager de la Transacci?n.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    protected TurnoEnhancedPk crearTurno(CirculoProcesoEnhanced cpe,
            PagoEnhancedPk pId, String impresora, Usuario user,
            PersistenceManager pm) throws DAOException {
        //List notasInf = new ArrayList();
        TurnoEnhanced tr = new TurnoEnhanced();
        //TurnoHistoriaEnhanced thr = new TurnoHistoriaEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        //JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        /*try {
            EstacionDAO estacionDAO = DAOFactory.getFactory().getEstacionDAO();
            JerarquiaDAO jerarquiaDAO = DAOFactory.getFactory().getJerarquiaDAO();
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            throw new DAOException(e.getMessage());
        }*/
        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {
            /**
             *
             */
            UsuarioEnhanced userEnhanced = null;
            if (user != null && user.getUsername() != null) {
                JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
                userEnhanced = variablesDAO.getUsuarioByLogin(user.getUsername(), pm);
            }

            //Encontrar el pago persistente asociado con la solicitud.
            PagoEnhanced p = pagosDAO.getPagoByID(pId, pm);

            if (p == null) {
                throw new DAOException(DAOException.PAGO_NO_ASOCIADO_SOLICITUD);
            }

            //Obtener la solicitud persistente que se va a asociar con el turno.
            SolicitudEnhancedPk sId = new SolicitudEnhancedPk();
            sId.idSolicitud = p.getLiquidacion().getIdSolicitud();

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(sId, pm);

            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA
                        + sId.idSolicitud);
            }

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            CirculoProcesoEnhanced cpnal = null;
            if (s instanceof SolicitudCertificadoEnhanced) {
                List solFolio = s.getSolicitudFolios();
                Iterator itSolFolio = solFolio.iterator();
                String circulo = null;
                if (itSolFolio.hasNext()) {
                    SolicitudFolioEnhanced sol = (SolicitudFolioEnhanced) itSolFolio.next();
                    circulo = sol.getFolio().getIdMatricula();
                    String[] str = circulo.split("-");
                    circulo = str[0];
                }
                if (circulo != null && !circulo.equals(cp.getIdCirculo())) {
                    cpnal = pagosDAO.getCirculoProcesoById(circulo, s.getProceso().getIdProceso(), pm);
                }

            }

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos
                    = fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(), "", "", "");

            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creaci?n del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setFechaInicio(fecha);
            tr.setSolicitud(s);
            tr.setLastIdHistoria(0);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            if (cpnal != null) {
                tr.setAnio(cpnal.getAnio());
                tr.setCirculoproceso(cpnal);
                tr.setIdTurno(String.valueOf(cpnal.getLastIdTurno() + 1));
                tr.setIdWorkflow(anio + "-" + cpnal.getIdCirculo() + "-"
                        + String.valueOf(cpnal.getIdProceso()) + "-"
                        + String.valueOf(cpnal.getLastIdTurno() + 1));
                cpnal.setLastIdTurno(cpnal.getLastIdTurno() + 1);
            } else {
                tr.setAnio(cp.getAnio());
                tr.setCirculoproceso(cp);
                tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
                tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-"
                        + String.valueOf(cp.getIdProceso()) + "-"
                        + String.valueOf(cp.getLastIdTurno() + 1));
                cp.setLastIdTurno(cp.getLastIdTurno() + 1);
            }

            if (userEnhanced != null) {
                tr.setUsuarioDestino(userEnhanced);
            }
            tr.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            pm.makePersistent(tr);
            //Log.getInstance().debug(JDOTurnosDAO.class,"\n*******************************************************");
            //Log.getInstance().debug(JDOTurnosDAO.class,"(MO) Turno creado (crear turno L-307): "+tr.getIdWorkflow());
            //Log.getInstance().debug(JDOTurnosDAO.class,"*******************************************************\n");
            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Crea el turno de acuerdo a la informaci?n recibida en los par?metros.
     * <p>
     * M?todo privado llamado por este DAO y utilizado en forma transaccional.
     *
     * @return el identificador del objeto Turno creado.
     * @param cpe C?rculo Proceso asociado con el Turno que va a ser creado.
     * @param pId Identificador del Pago asociado con el Turno que va a ser
     * creado.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param user <code>Usuario</code> iniciador del <code>Proceso</code>
     * @param pm PersistenceManager de la Transacci?n.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    protected TurnoEnhancedPk crearTurnoNuevoRegistro(CirculoProcesoEnhanced cpe,
            PagoEnhancedPk pId, String impresora, Usuario user,
            PersistenceManager pm) throws DAOException {
        //List notasInf = new ArrayList();
        TurnoEnhanced tr = new TurnoEnhanced();
        //TurnoHistoriaEnhanced thr = new TurnoHistoriaEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        //JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        /*try {
            EstacionDAO estacionDAO = DAOFactory.getFactory().getEstacionDAO();
            JerarquiaDAO jerarquiaDAO = DAOFactory.getFactory().getJerarquiaDAO();
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            throw new DAOException(e.getMessage());
        }*/
        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {
            //Encontrar el pago persistente asociado con la solicitud.
            PagoEnhanced p = pagosDAO.getPagoByID(pId, pm);

            if (p == null) {
                throw new DAOException(DAOException.PAGO_NO_ASOCIADO_SOLICITUD);
            }

            //Obtener la solicitud persistente que se va a asociar con el turno.
            SolicitudEnhancedPk sId = new SolicitudEnhancedPk();
            sId.idSolicitud = p.getLiquidacion().getIdSolicitud();

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(sId, pm);

            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA
                        + sId.idSolicitud);
            }

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos
                    = fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(), "", "", "");

            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creaci?n del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setAnio(cp.getAnio());
            tr.setFechaInicio(fecha);
            tr.setCirculoproceso(cp);
            tr.setSolicitud(s);
            // Como se va a crear un turno historia se llena esta variable con el valor 1.
            tr.setLastIdHistoria(1);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
            tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-"
                    + String.valueOf(cp.getIdProceso()) + "-"
                    + String.valueOf(cp.getLastIdTurno() + 1));
            cp.setLastIdTurno(cp.getLastIdTurno() + 1);
            tr.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            // Insertar el turno historia correspondiente a la creaci?n del turno.
            TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();

            //Obtener el usuario persistente.
            JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(user.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontr? el Usuario con el login: " + user.getUsername());
            }

            tr.setUsuarioDestino(usuarior);

            //Se inicializa este atributo en false para la creaci?n porque en el paso 
            //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
            th.setActivo(false);
            th.setAnio(tr.getAnio());
            th.setFase("SOLICITUD");
            th.setFecha(new Date());
            th.setRespuesta("CREACION DEL TURNO");
            th.setIdCirculo(tr.getIdCirculo());
            th.setIdProceso(tr.getIdProceso());
            th.setIdTurno(tr.getIdTurno());
            th.setAnio(tr.getAnio());
            th.setUsuario(usuarior);
            th.setUsuarioAtiende(usuarior);
            //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
            th.setIdTurnoHistoria("1");

            tr.addHistorial(th);

            pm.makePersistent(tr);

            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Crea una instancia de workflow en el sistema.
     * <P>
     * Determina el nombre del programa concurrente iniciador de acuerdo con el
     * tipo de solicitud, y la respectiva respuesta para el mismo.
     * <p>
     * Se encarga de construir la tabla de par?metros necesaria para que SAS
     * procese la solicitud.
     *
     * @param turno <code>TurnoEnhanced</code> con sus atributos.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param user <code>Usuario</code> iniciador del proceso.
     * @param rol  <code>Rol</code>del usuario iniciador del proceso.
     * @param estacion Identificador de la estaci?n en la cual se desea crear el
     * turno (Par?metro opcional).
     * @throws <code>DAOException</code>
     */
    protected void crearInstanciaWF(TurnoEnhanced turno, String impresora,
            Usuario user, String rol, String estacion) throws DAOException {

        InfoLog.informarLogs(turno.getIdWorkflow(), InfoLog.VACIO, this, "crearInstanciaWF()", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "inicio.crearInstanciaWF", InfoLog.VACIO);

        if (turno == null) {
            throw new DAOException(DAOException.TURNO_NULO);
        }

        long tiempoInicial = System.currentTimeMillis();

        Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
        Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(INICIO):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
        Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

        TurnoEnhanced t = turno;
        //Processor pr;
        Hashtable solicitud = new Hashtable();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        String tipo = null;
        String iniciador = null;
        //String tipoAsociados;

        //Se obtiene de HermodProperties, la propiedad que indica el n?mero de anotaciones a partir
        //de las cuales un folio es de mayor extensi?n.
        //HermodProperties hp = HermodProperties.getInstancia();
        //int mayor_ext = Integer.parseInt(hp.getProperty(
        //            HermodProperties.HERMOD_MAYOR_EXTENSION));
        int mayor_ext = turno.getSolicitud().getCirculo().getMayorExtension();
        boolean isMayorExtension = false;
        Turno turnoTemp = null;

        JDOFasesDAO jdoFases = new JDOFasesDAO();
        InicioProcesos inicioProcesos = null;
        Hashtable parametrosInicio = new Hashtable();

        try {
            turnoTemp = (Turno) TransferUtils.makeTransfer(turno);

            if (!(t.getSolicitud() instanceof SolicitudCertificadoEnhanced)) {
                inicioProcesos
                        = jdoFases.obtenerFaseInicial(new Long(turnoTemp.getIdProceso()).toString());

                parametrosInicio.put(Processor.ROL, inicioProcesos.getIdRol());
                parametrosInicio.put(Processor.ITEM_KEY, turno.getIdWorkflow());
                parametrosInicio.put(Processor.ACTIVITY, inicioProcesos.getIdFase());
                parametrosInicio.put(Processor.NOT_ID, "1");
            }

            //turnoTemp =  this.getTurnoByID(idturno);
            InfoLog.informarLogs(turno.getIdWorkflow(), InfoLog.VACIO, this, "crearInstanciaWF()", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "HermodWFFactory.getFactory().getProcessor()", InfoLog.VACIO);
            /*pr = */
            HermodWFFactory.getFactory().getProcessor();
            InfoLog.informarLogs(turno.getIdWorkflow(), InfoLog.VACIO, this, "crearInstanciaWF()", InfoLog.TERMINA, InfoLog.LLAMADO_PROPIO, "HermodWFFactory.getFactory().getProcessor()", InfoLog.VACIO);
            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(INICIO 2):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

            //Crear una instancia del WorkFlow de Certificados Individuales.
            if (t.getSolicitud() instanceof SolicitudCertificadoEnhanced) {
                SolicitudCertificadoEnhanced sc = (SolicitudCertificadoEnhanced) t.getSolicitud();
                int i = (int) sc.getLastIdLiquidacion();

                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
                Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(SolicitudCertificadoEnhanced):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

                if (i > 0) {
                    //Se obtiene la ?ltima liquidaci?n asociada con la solicitud.
                    LiquidacionTurnoCertificadoEnhanced lc = (LiquidacionTurnoCertificadoEnhanced) sc.getLiquidaciones()
                            .get(0);
                    tipo = lc.getTipoCertificado().getNombre();
                    //tipoAsociados = tipo;

                    //TIPO DE CERTIFICADO INMEDIATO.
                    if (tipo.equals(CTipoCertificado.TIPO_INMEDIATO_NOMBRE)) {
                        if (sc.getSolicitudFolios().isEmpty()) {
                        } //El certificado tiene asociadas solicitudes de folio.
                        else {
                            SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) sc.getSolicitudFolios()
                                    .get(0);
                            FolioEnhanced f = sf.getFolio();

                            //Se determina el n?mero de anotaciones asociadas con el folio.
                            long num_anotaciones = variablesDAO.getCountAnotacionesFolio(f.getIdMatricula());

                            //Si el n?mero de anotaciones excede el valor de la variable configurada, se
                            //asigna el tipo del Certificado como mayor extensi?n, en el caso contrario
                            //continua siendo inmediato.
                            if (num_anotaciones > mayor_ext) {
                                tipo = CTipoCertificado.TIPO_MAYOR_EXTENSION_NOMBRE;
                                isMayorExtension = true;
                            } else {
                                tipo = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
                            }
                        }
                    } //TIPO DE CERTIFICADO EXENTO
                    else if (tipo.equals(CTipoCertificado.TIPO_EXENTO_NOMBRE)) {
                        tipo = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
                    }

                    if (sc.getSolicitudesPadres() != null) {
                        if (sc.getSolicitudesPadres().size() > 0) {
                            if (((SolicitudAsociadaEnhanced) sc.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudCertificadoMasivoEnhanced) {
                                tipo = CTipoCertificado.ASOCIADO_MASIVO;
                            } else if (((SolicitudAsociadaEnhanced) sc.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudRegistroEnhanced) {
                                tipo = CTipoCertificado.TIPO_ASOCIADO_NOMBRE;
                            } else {
                                tipo = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
                            }
                        }
                    }

                    //Si contiene en el nombre la palabra asociado se va con el tipo respectivo
                    /*
	if(tipoAsociados.indexOf(CTipoCertificado.TIPO_ASOCIADO_NOMBRE)!=-1){
	  tipo = CTipoCertificado.TIPO_ASOCIADO_NOMBRE;
	}*/
                    iniciador = CIniciacionProcesos.INICIADOR_CERTIFICADOS_INDIVIDUALES;
                }
                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
                Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(SolicitudCertificadoEnhanced 2):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");
            }

            //Solicitud de Consultas.
            if (t.getSolicitud() instanceof SolicitudConsultaEnhanced) {
                SolicitudConsultaEnhanced sc = (SolicitudConsultaEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.CONCATENACION_TIPO_CONSULTA
                        + sc.getTipoConsulta().getNombre();
                iniciador = CIniciacionProcesos.INICIADOR_CONSULTAS;
            }

            //Solicitud de Fotocopias.
            if (t.getSolicitud() instanceof SolicitudFotocopiaEnhanced) {
                //SolicitudFotocopiaEnhanced sc = (SolicitudFotocopiaEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.RESPUESTA_CONFIRMAR_INICIO;
                iniciador = CIniciacionProcesos.INICIADOR_FOTOCOPIAS;
            }

            //Solicitud de Devoluciones.
            if (t.getSolicitud() instanceof SolicitudDevolucionEnhanced) {
                //SolicitudDevolucionEnhanced sc = (SolicitudDevolucionEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.RESPUESTA_CONFIRMAR_INICIO;
                iniciador = CIniciacionProcesos.INICIADOR_DEVOLUCIONES;
            }

            //Solicitud de Correcciones
            if (t.getSolicitud() instanceof SolicitudCorreccionEnhanced) {
                //SolicitudCorreccionEnhanced sc = (SolicitudCorreccionEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.RESPUESTA_CONFIRMAR_INICIO;
                iniciador = CIniciacionProcesos.INICIADOR_CORRECCIONES;
            }

            //Solicitud de Registro
            if (t.getSolicitud() instanceof SolicitudRegistroEnhanced) {
                //SolicitudRegistroEnhanced sc = (SolicitudRegistroEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.RESPUESTA_CONFIRMAR_INICIO;
                iniciador = CIniciacionProcesos.INICIADOR_REGISTRO;
            }

            //Solicitud de Reparto Notarial
            if (t.getSolicitud() instanceof SolicitudRepartoNotarialEnhanced) {
                //SolicitudRepartoNotarialEnhanced sc = (SolicitudRepartoNotarialEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.RESPUESTA_CONFIRMAR_INICIO;
                iniciador = CIniciacionProcesos.INICIADOR_REPARTO_NOTARIAL;
            }

            //Solicitud de Restituci?n de Reparto Notarial
            if (t.getSolicitud() instanceof SolicitudRestitucionRepartoEnhanced) {
                //SolicitudRestitucionRepartoEnhanced sc = (SolicitudRestitucionRepartoEnhanced) t.getSolicitud();

                tipo = CIniciacionProcesos.RESPUESTA_CONFIRMAR_INICIO;
                iniciador = CIniciacionProcesos.INICIADOR_RESTITUCION_REPARTO_NOTARIAL;
            }

            //Solicitud de Certificados Masivos
            if (t.getSolicitud() instanceof SolicitudCertificadoMasivoEnhanced) {
                //SolicitudCertificadoMasivoEnhanced sc = (SolicitudCertificadoMasivoEnhanced) t.getSolicitud();

                tipo = rol;
                iniciador = CIniciacionProcesos.INICIADOR_CERTIFICADOS_MASIVOS;
            }

            ServiceLocator sl = ServiceLocator.getInstancia();

            //Se llena la tabla de par?metros que va a ser enviada a SAS.
            //ITEM KEY
            solicitud.put(Processor.ITEM_KEY, t.getIdWorkflow());

            //USUARIO
            solicitud.put(SASKeys.SAS_ID_USUARIO,
                    CIniciacionProcesos.USUARIO_SAS_SISTEMA_INICIO);

            //PROGRAMA CONCURRENTE INICIADOR
            solicitud.put(SASKeys.SAS_ID_SOLICITUD, iniciador);

            //TIPO DE PROCESO
            solicitud.put(CIniciacionProcesos.TIPO_PROCESO_INICIADOR,
                    Message.ACTUALIZAR_PROCESO);

            String administrador = CIniciacionProcesos.ADMINISTRADOR_AUTOMATICO_INICIO;
            if (turno != null && turno.getSolicitud() != null && turno.getSolicitud().getCirculo() != null && turno.getSolicitud().getCirculo().getIdCirculo() != null) {
                administrador = CIniciacionProcesos.ADMINISTRADOR_AUTOMATICO_INICIO + "-" + turno.getSolicitud().getCirculo().getIdCirculo();
            }
            if (turno != null && turno.getSolicitud() != null && turno.getSolicitud().getCirculo() != null && turno.getSolicitud().getCirculo().getIdCirculo() != null) {

                long proceso = turno.getIdProceso();
                long procesoCertificados = new Long(CProceso.PROCESO_CERTIFICADOS).longValue();

                if (proceso == procesoCertificados) {
                    administrador = CIniciacionProcesos.ADMINISTRADOR_AUTOMATICO_INICIO + "-" + turno.getSolicitud().getCirculo().getIdCirculo() + "-" + proceso;
                }

            }

            solicitud.put(SASKeys.SAS_ESTACION, administrador);

            //Par?metros Opcionales
            if (tipo != null) {
                solicitud.put(Processor.RESULT, tipo);
            }

            //IMPRESORA
            if (impresora != null) {
                solicitud.put(Processor.IMPRESORA, impresora);
            }

            //USUARIO INICIADOR
            if (user != null) {
                solicitud.put(Processor.USUARIO_INICIADOR, user.getUsername());
            }

            //ESTACION INICIADORA
            if (estacion != null) {
                solicitud.put(Processor.ESTACION_INICIADORA, estacion);
            }

            //LLAMADO A SAS PARA EL PROCESAMIENTO DE LA SOLICITUD.
            int isCrearWF = 1;

            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(SolicitudCertificadoEnhanced 3):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

            if (t.getSolicitud() instanceof SolicitudCertificadoEnhanced) {
                isCrearWF = 0;
                if (((tipo.equals(CTipoCertificado.TIPO_INMEDIATO_NOMBRE) || tipo.equals(CTipoCertificado.TIPO_INTERNET_NOMBRE) || tipo.equals(CTipoCertificado.TIPO_NACIONAL_NOMBRE))
                        && (t.getSolicitud().getSolicitudesPadres() == null || t.getSolicitud().getSolicitudesPadres().isEmpty()))
                        || (tipo.equals(CTipoCertificado.TIPO_ASOCIADO_NOMBRE))
                        || (tipo.equals(CTipoCertificado.ASOCIADO_MASIVO))) {
                    // No se va a crear wf
                    procesarTurnoEjecucionSIR(solicitud, t, user);
                } else {
                    JDOTurnosNuevosDAO jdoTurnosNuevos = new JDOTurnosNuevosDAO();

                    PersistenceManager pm = AdministradorPM.getPM();
                    try {
                        pm.currentTransaction().setOptimistic(false);
                        pm.currentTransaction().begin();
                        TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
                        //Se inicializa este atributo en false para la creaci?n porque en el paso 
                        //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
                        th.setActivo(false);
                        th.setAnio(turno.getAnio());
                        th.setFase("SOLICITUD");
                        th.setFecha(new Date());
                        th.setRespuesta("CREACION DEL TURNO");
                        th.setIdCirculo(turno.getIdCirculo());
                        th.setIdProceso(turno.getIdProceso());
                        th.setIdTurno(turno.getIdTurno());
                        UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                        uid.idUsuario = user.getIdUsuario();
                        UsuarioEnhanced userPers = (UsuarioEnhanced) pm.getObjectById(uid, true);
                        th.setUsuario(userPers);
                        th.setUsuarioAtiende(userPers);
                        //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
                        th.setIdTurnoHistoria("1");
                        pm.makePersistent(th);
                        TurnoEnhanced tenh = this.getTurnoByWFId(turno.getIdWorkflow(), pm);
                        tenh.setLastIdHistoria(1);
                        pm.currentTransaction().commit();
                    } catch (JDOException e) {
                        if (pm.currentTransaction().isActive()) {
                            pm.currentTransaction().rollback();
                        }
                        throw new DAOException(e.getMessage(), e);
                    } catch (Exception e) {
                        if (pm.currentTransaction().isActive()) {
                            pm.currentTransaction().rollback();
                        }
                        throw new DAOException(e.getMessage(), e);
                    } finally {
                        if (pm.currentTransaction().isActive()) {
                            pm.currentTransaction().commit();
                        }
                        pm.close();
                    }

                    //Se crea el registro en el TurnoEjecucion
                    inicioProcesos
                            = jdoFases.obtenerFaseInicial(new Long(turnoTemp.getIdProceso()).toString());

                    parametrosInicio.put(Processor.ROL, inicioProcesos.getIdRol());
                    parametrosInicio.put(Processor.ITEM_KEY, turno.getIdWorkflow());
                    parametrosInicio.put(Processor.ACTIVITY, inicioProcesos.getIdFase());
                    parametrosInicio.put(Processor.NOT_ID, "1");
                    parametrosInicio.put("MAYOR_EXTENSION", new Boolean(isMayorExtension));

                    jdoTurnosNuevos.guardarInfoTurnoEjecucion(parametrosInicio, estacion, turnoTemp, user);
                    Hashtable parametros = new Hashtable();
                    parametros.put(Processor.RESULT, tipo);
                    Fase fase = new Fase();
                    fase.setID(CFase.CER_SOLICITUD);
                    jdoTurnosNuevos.avanzarTurnoNuevoNormal(turnoTemp, fase, parametros, user);
                    //jdoTurnosNuevos.avanzarTurnoNuevoNormal((Turno)turno.toTransferObject(),fase,parametros,user);
                }

                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
                Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(SolicitudCertificadoEnhanced 4):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

            } else if (t.getSolicitud() instanceof SolicitudConsultaEnhanced) {

                //Se crea el turno en el workflow
                isCrearWF = 0;
                JDOTurnosNuevosDAO jdoTurnosNuevos = new JDOTurnosNuevosDAO();

                PersistenceManager pm = AdministradorPM.getPM();
                try {
                    pm.currentTransaction().setOptimistic(false);
                    pm.currentTransaction().begin();
                    TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
                    //Se inicializa este atributo en false para la creaci?n porque en el paso 
                    //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
                    th.setActivo(false);
                    th.setAnio(turno.getAnio());
                    th.setFase("SOLICITUD");
                    th.setFecha(new Date());
                    th.setRespuesta("CREACION DEL TURNO");
                    th.setIdCirculo(turno.getIdCirculo());
                    th.setIdProceso(turno.getIdProceso());
                    th.setIdTurno(turno.getIdTurno());
                    UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                    uid.idUsuario = user.getIdUsuario();
                    UsuarioEnhanced userPers = (UsuarioEnhanced) pm.getObjectById(uid, true);
                    th.setUsuario(userPers);
                    th.setUsuarioAtiende(userPers);
                    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
                    th.setIdTurnoHistoria("1");
                    pm.makePersistent(th);
                    TurnoEnhanced tenh = this.getTurnoByWFId(turno.getIdWorkflow(), pm);
                    tenh.setLastIdHistoria(1);
                    pm.currentTransaction().commit();
                } catch (JDOException e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } catch (Exception e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } finally {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().commit();
                    }
                    pm.close();
                }

                //Se crea el registro en el TurnoEjecucion
                jdoTurnosNuevos.guardarInfoTurnoEjecucion(parametrosInicio, estacion, turnoTemp, user);
                Hashtable parametros = new Hashtable();
                parametros.put(Processor.RESULT, tipo);
                if (((SolicitudConsultaEnhanced) turno.getSolicitud()).getTipoConsulta().getIdTipoConsulta().equals(TipoConsulta.TIPO_SIMPLE)) {
                    parametros.put(Processor.ESTACION, estacion);
                }

                Fase fase = new Fase();
                fase.setID(CFase.CON_CONSULTA);
                jdoTurnosNuevos.avanzarTurnoNuevoNormal(turnoTemp, fase, parametros, user);
                //jdoTurnosNuevos.avanzarTurnoNuevoNormal((Turno)turno.toTransferObject(),fase,parametros,user);
            } else if (t.getSolicitud() instanceof SolicitudCertificadoMasivoEnhanced) {

                //Se crea el turno en el workflow
                isCrearWF = 0;
                JDOTurnosNuevosDAO jdoTurnosNuevos = new JDOTurnosNuevosDAO();

                PersistenceManager pm = AdministradorPM.getPM();
                try {
                    pm.currentTransaction().setOptimistic(false);
                    pm.currentTransaction().begin();
                    TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
                    //Se inicializa este atributo en false para la creaci?n porque en el paso 
                    //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
                    th.setActivo(false);
                    th.setAnio(turno.getAnio());
                    th.setFase("SOLICITUD");
                    th.setFecha(new Date());
                    th.setRespuesta("CREACION DEL TURNO");
                    th.setIdCirculo(turno.getIdCirculo());
                    th.setIdProceso(turno.getIdProceso());
                    th.setIdTurno(turno.getIdTurno());
                    UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                    uid.idUsuario = user.getIdUsuario();
                    UsuarioEnhanced userPers = (UsuarioEnhanced) pm.getObjectById(uid, true);
                    th.setUsuario(userPers);
                    th.setUsuarioAtiende(userPers);
                    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
                    th.setIdTurnoHistoria("1");
                    pm.makePersistent(th);
                    TurnoEnhanced tenh = this.getTurnoByWFId(turno.getIdWorkflow(), pm);
                    tenh.setLastIdHistoria(1);
                    pm.currentTransaction().commit();
                } catch (JDOException e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } catch (Exception e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } finally {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().commit();
                    }
                    pm.close();
                }

                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
                Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(CINCO):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

                //Se crea el registro en el TurnoEjecucion
                jdoTurnosNuevos.guardarInfoTurnoEjecucion(parametrosInicio, estacion, turnoTemp, user);
                Hashtable parametros = new Hashtable();
                parametros.put(Processor.RESULT, tipo);
                Fase fase = new Fase();
                fase.setID(CFase.CER_DECIDIR_USUARIO);
                jdoTurnosNuevos.avanzarTurnoNuevoNormal(turnoTemp, fase, parametros, user);
                //jdoTurnosNuevos.avanzarTurnoNuevoNormal((Turno)turno.toTransferObject(),fase,parametros,user);

                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
                Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(SEIS):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
                Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

            } else if (t.getSolicitud() instanceof SolicitudCorreccionEnhanced) {

                //Se crea el turno en el workflow
                isCrearWF = 0;
                JDOTurnosNuevosDAO jdoTurnosNuevos = new JDOTurnosNuevosDAO();

                String estacionAsignada = jdoTurnosNuevos.obtenerEstacionTurno(parametrosInicio, turno.getIdCirculo());
                PersistenceManager pm = AdministradorPM.getPM();
                try {
                    pm.currentTransaction().setOptimistic(false);
                    pm.currentTransaction().begin();
                    TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
                    //Se inicializa este atributo en false para la creaci?n porque en el paso 
                    //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
                    th.setActivo(false);
                    th.setAnio(turno.getAnio());
                    th.setFase("SOLICITUD");
                    th.setFecha(new Date());
                    th.setRespuesta("CREACION DEL TURNO");
                    th.setIdCirculo(turno.getIdCirculo());
                    th.setIdProceso(turno.getIdProceso());
                    th.setIdTurno(turno.getIdTurno());
                    UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                    uid.idUsuario = user.getIdUsuario();
                    UsuarioEnhanced userPers = (UsuarioEnhanced) pm.getObjectById(uid, true);
                    th.setUsuario(userPers);
                    th.setUsuarioAtiende(userPers);
                    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
                    th.setIdTurnoHistoria("1");
                    pm.makePersistent(th);
                    TurnoEnhanced tenh = this.getTurnoByWFId(turno.getIdWorkflow(), pm);
                    tenh.setLastIdHistoria(1);
                    tenh.setIdFase(CFase.COR_REVISION_ANALISIS);
                    pm.currentTransaction().commit();
                } catch (JDOException e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } catch (Exception e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } finally {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().commit();
                    }
                    pm.close();
                }

                //Se crea el registro en el TurnoEjecucion
                jdoTurnosNuevos.guardarInfoTurnoEjecucion(parametrosInicio, estacionAsignada, turnoTemp, user);

            } else if (t.getSolicitud() instanceof SolicitudRestitucionRepartoEnhanced) {

                //Se crea el turno en el workflow
                isCrearWF = 0;
                JDOTurnosNuevosDAO jdoTurnosNuevos = new JDOTurnosNuevosDAO();

                String estacionAsignada = jdoTurnosNuevos.obtenerEstacionTurno(parametrosInicio, turno.getIdCirculo());
                PersistenceManager pm = AdministradorPM.getPM();
                try {
                    pm.currentTransaction().setOptimistic(false);
                    pm.currentTransaction().begin();
                    TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
                    //Se inicializa este atributo en false para la creaci?n porque en el paso 
                    //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
                    th.setActivo(false);
                    th.setAnio(turno.getAnio());
                    th.setFase("SOLICITUD");
                    th.setFecha(new Date());
                    th.setRespuesta("CREACION DEL TURNO");
                    th.setIdCirculo(turno.getIdCirculo());
                    th.setIdProceso(turno.getIdProceso());
                    th.setIdTurno(turno.getIdTurno());
                    UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                    uid.idUsuario = user.getIdUsuario();
                    UsuarioEnhanced userPers = (UsuarioEnhanced) pm.getObjectById(uid, true);
                    th.setUsuario(userPers);
                    th.setUsuarioAtiende(userPers);
                    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
                    th.setIdTurnoHistoria("1");
                    pm.makePersistent(th);
                    TurnoEnhanced tenh = this.getTurnoByWFId(turno.getIdWorkflow(), pm);
                    tenh.setLastIdHistoria(1);
                    tenh.setIdFase(CFase.RES_ANALISIS);
                    pm.currentTransaction().commit();
                } catch (JDOException e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } catch (Exception e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } finally {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().commit();
                    }
                    pm.close();
                }

                //Se crea el registro en el TurnoEjecucion
                jdoTurnosNuevos.guardarInfoTurnoEjecucion(parametrosInicio, estacionAsignada, turnoTemp, user);
            } else if (t.getSolicitud() instanceof SolicitudDevolucionEnhanced) {

                //Se crea el turno en el workflow
                isCrearWF = 0;
                JDOTurnosNuevosDAO jdoTurnosNuevos = new JDOTurnosNuevosDAO();

                String estacionAsignada = jdoTurnosNuevos.obtenerEstacionTurno(parametrosInicio, turno.getIdCirculo());
                PersistenceManager pm = AdministradorPM.getPM();
                try {
                    pm.currentTransaction().setOptimistic(false);
                    pm.currentTransaction().begin();
                    TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
                    //Se inicializa este atributo en false para la creaci?n porque en el paso 
                    //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
                    th.setActivo(false);
                    th.setAnio(turno.getAnio());
                    th.setFase("SOLICITUD");
                    th.setFecha(new Date());
                    th.setRespuesta("CREACION DEL TURNO");
                    th.setIdCirculo(turno.getIdCirculo());
                    th.setIdProceso(turno.getIdProceso());
                    th.setIdTurno(turno.getIdTurno());
                    UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
                    uid.idUsuario = user.getIdUsuario();
                    UsuarioEnhanced userPers = (UsuarioEnhanced) pm.getObjectById(uid, true);
                    th.setUsuario(userPers);
                    th.setUsuarioAtiende(userPers);
                    //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
                    th.setIdTurnoHistoria("1");
                    pm.makePersistent(th);
                    TurnoEnhanced tenh = this.getTurnoByWFId(turno.getIdWorkflow(), pm);
                    tenh.setLastIdHistoria(1);
                    tenh.setIdFase(CFase.DEV_ANALISIS);
                    pm.currentTransaction().commit();
                } catch (JDOException e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } catch (Exception e) {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().rollback();
                    }
                    throw new DAOException(e.getMessage(), e);
                } finally {
                    if (pm.currentTransaction().isActive()) {
                        pm.currentTransaction().commit();
                    }
                    pm.close();
                }

                //Se crea el registro en el TurnoEjecucion
                jdoTurnosNuevos.guardarInfoTurnoEjecucion(parametrosInicio, estacionAsignada, turnoTemp, user);
            }

            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(SIETE):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

            if (isCrearWF == 1) {
                InfoLog.informarLogs(turno.getIdWorkflow(), InfoLog.VACIO, this, "crearInstanciaWF()", InfoLog.INICIA, InfoLog.LLAMADO_OTROS, "sas.procesarSolicitud", InfoLog.VACIO);
                //org.auriga.util.InfoLog.a ++;
                //sas.procesarSolicitud(solicitud);
                InfoLog.informarLogs(turno.getIdWorkflow(), InfoLog.VACIO, this, "crearInstanciaWF()", InfoLog.TERMINA, InfoLog.LLAMADO_OTROS, "sas.procesarSolicitud", InfoLog.VACIO);

                InfoLog.informarLogs(turno.getIdWorkflow(), InfoLog.VACIO, this, "crearInstanciaWF()", InfoLog.TERMINA, InfoLog.LLAMADO_PROPIO, "termina.crearInstanciaWF", InfoLog.VACIO);
            }

            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************");
            Log.getInstance().debug(JDOTurnosDAO.class, "(crearInstanciaWF)(OCHO):" + ((turno != null && turno.getIdWorkflow() != null) ? turno.getIdWorkflow() : "") + "=" + ((System.currentTimeMillis() - tiempoInicial) / 1000.0d));
            Log.getInstance().debug(JDOTurnosDAO.class, "\n*******************************************************\n");

        } catch (WFException we) {
            Log.getInstance().error(JDOTurnosDAO.class, we.getMessage(), we);
            throw new DAOException(we.getMessage(), we);
        } catch (Exception e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    public void eliminarActos(String idSolicitud) throws DAOException{
        PersistenceManager pm = AdministradorPM.getPM();
        VersantPersistenceManager jdoPM = (VersantPersistenceManager) pm;
        Connection connection = null;
        connection = jdoPM.getJdbcConnection(null);

        String sql = "DELETE FROM SIR_OP_LIQUIDA_CONSERVA_DOCU WHERE ID_SOLICITUD = '"+ idSolicitud +"' ";
        Statement s = null;
        try {
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            }

        }
    }
    
    public void procesarTurnoEjecucionSIR(Hashtable solicitud, TurnoEnhanced t, Usuario user) throws DAOException {
        //  ITEM KEY
        String item_key = (String) solicitud.get(Processor.ITEM_KEY);
        //USUARIO
        String sasUsuario = (String) solicitud.get(SASKeys.SAS_ID_USUARIO);
        //PROGRAMA CONCURRENTE INICIADOR
        String solicitud1 = (String) solicitud.get(SASKeys.SAS_ID_SOLICITUD);
        //TIPO DE PROCESO
        String proceso = (String) solicitud.get(CIniciacionProcesos.TIPO_PROCESO_INICIADOR);
        //ESTACION
        String estacion = (String) solicitud.get(SASKeys.SAS_ESTACION);
        //Par?metros Opcionales
        //TIPO
        String tipo = (String) solicitud.get(Processor.RESULT);
        //IMPRESORA
        String impresora = (String) solicitud.get(Processor.IMPRESORA);
        //USUARIO INICIADOR
        String usuarioIniciador = (String) solicitud.get(Processor.USUARIO_INICIADOR);
        //ESTACION INICIADORA
        String estacionIniciadora = (String) solicitud.get(Processor.ESTACION_INICIADORA);

        Turno turno = null;
        //try {
        //turno = this.getTurnoByWFId(item_key);
        turno = (Turno) TransferUtils.makeTransfer(t);
        /*} catch (DAOException e)  {
    	throw new DAOException(e.getMessage(), e);
    	}*/

        if (t.getSolicitud() instanceof SolicitudCertificadoEnhanced) {
            SolicitudCertificadoEnhanced sc = (SolicitudCertificadoEnhanced) t.getSolicitud();
            if (tipo.equals(CTipoCertificado.TIPO_INMEDIATO_NOMBRE) || tipo.equals(CTipoCertificado.TIPO_INTERNET_NOMBRE) || tipo.equals(CTipoCertificado.TIPO_NACIONAL_NOMBRE)) {
                // Se genera el turno historia
                crearHistoriaTurnoCertificadoInmediato(turno, solicitud, user);
            }
            if (tipo.equals(CTipoCertificado.TIPO_ASOCIADO_NOMBRE)) {
                // Se genera el turno historia
                crearHistoriaTurnoCertificadoAsociado(turno, solicitud, user);
            }

            if (tipo.equals(CTipoCertificado.ASOCIADO_MASIVO)) {
                // Se genera el turno historia
                crearHistoriaTurnoCertificadoMasivo(turno, solicitud, user);
            }

        }
    }

    public void crearHistoriaTurnoCertificadoInmediato(Turno t, Hashtable solicitud, Usuario user) throws DAOException {

        //Se crea el historial de la creacion
        TurnoHistoria tHist;
        tHist = new TurnoHistoria();
        tHist.setActivo(false);
        tHist.setAnio(t.getAnio());
        tHist.setFecha(t.getFechaInicio());
        String usuarioIniciador = (String) solicitud.get(Processor.USUARIO_INICIADOR);
        String administrador1 = "X-DECISOR-" + t.getSolicitud().getCirculo().getIdCirculo();
        tHist.setIdAdministradorSAS(administrador1);
        tHist.setIdCirculo(t.getIdCirculo());
        tHist.setIdProceso(t.getIdProceso());
        tHist.setIdTurno(t.getIdWorkflow());
        String tipo = (String) solicitud.get(Processor.RESULT);
        tHist.setRespuesta(tipo);
        tHist.setUsuario(user);
        tHist.setUsuarioAtiende(user);
        tHist.setFase(CFase.CER_SOLICITUD);
        tHist.setFaseAnterior(CFase.CER_CREAR);
        addTurnoHistoria(t, tHist, new Integer(0));
        //	Actualiza

        t.setIdFase(CFase.CER_IMPRIMIR);
        t.setUltimaRespuesta("INMEDIATO");
        t.setLastIdHistoria(1);
        updateTurno(t);

        //Se crea el historial de la impresion
        TurnoHistoria tHist2;
        tHist2 = new TurnoHistoria();
        tHist2.setActivo(false);
        tHist2.setAnio(t.getAnio());
        tHist2.setFecha(t.getFechaInicio());
        String estacion2 = "X-ROBOT-" + t.getSolicitud().getCirculo().getIdCirculo();
        tHist2.setIdAdministradorSAS(estacion2);
        tHist2.setIdCirculo(t.getIdCirculo());
        tHist2.setIdProceso(t.getIdProceso());
        tHist2.setIdTurno(t.getIdWorkflow());
        tHist2.setRespuesta("CONFIRMAR");
        tHist2.setUsuario(user);
        tHist2.setUsuarioAtiende(user);
        tHist2.setFase(CFase.CER_IMPRIMIR);
        tHist2.setFaseAnterior(CFase.CER_SOLICITUD);

        //Se guarda la matrica impresa
        {
            Solicitud sol = t.getSolicitud();
            Folio folio = null;
            if (sol != null) {
                List solFolios = sol.getSolicitudFolios();
                for (Iterator solFItera = solFolios.iterator();
                        solFItera.hasNext();) {
                    SolicitudFolio solF = (SolicitudFolio) solFItera.next();
                    folio = solF.getFolio();
                    continue;
                }
            }
            if (folio != null) {
                tHist2.setIdMatriculaImpresa(folio.getIdMatricula());
            }
        }

        addTurnoHistoria(t, tHist2, new Integer(1));
        //Actualiza
        t.setIdFase(CFase.CER_ENTREGAR);
        t.setUltimaRespuesta("CONFIRMAR");
        t.setLastIdHistoria(2);
        updateTurno(t);

        //Se crea el historial de la entrega
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        String estacion3 = "X-ENTREGA.CER." + t.getSolicitud().getCirculo().getIdCirculo();
        tHist3.setIdAdministradorSAS(estacion3);
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdWorkflow());
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_ENTREGAR);
        tHist3.setFaseAnterior(CFase.CER_IMPRIMIR);
        addTurnoHistoria(t, tHist3, new Integer(0));

        //crear TurnoEjecucion
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            TurnoEjecucionEnhanced turnoEje = new TurnoEjecucionEnhanced();
            turnoEje.setEstacion("X-ENTREGA.CER." + t.getSolicitud().getCirculo().getIdCirculo());
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_ENTREGAR);
            turnoEje.setHasWF(false);
            turnoEje.setIdWorkflow(t.getIdWorkflow());
            pm.makePersistent(turnoEje);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * @param t
     * @param solicitud
     * @param user
     * @throws DAOException
     */
    public void crearHistoriaTurnoCertificadoAsociado(Turno t, Hashtable solicitud, Usuario user) throws DAOException {

        // Se crea el historial de la creacion
        TurnoHistoria tHist;
        tHist = new TurnoHistoria();
        tHist.setActivo(false);
        tHist.setAnio(t.getAnio());
        tHist.setFecha(t.getFechaInicio());
        String usuarioIniciador = (String) solicitud.get(Processor.USUARIO_INICIADOR);
        String administrador1 = "X-DECISOR-" + t.getIdCirculo();
        tHist.setIdAdministradorSAS(administrador1);
        tHist.setIdCirculo(t.getIdCirculo());
        tHist.setIdProceso(t.getIdProceso());
        tHist.setIdTurno(t.getIdWorkflow());
        String tipo = (String) solicitud.get(Processor.RESULT);
        tHist.setRespuesta(tipo);
        tHist.setUsuario(user);
        tHist.setUsuarioAtiende(user);
        tHist.setFase(CFase.CER_SOLICITUD);
        tHist.setFaseAnterior(CFase.CER_CREAR);
        addTurnoHistoria(t, tHist, new Integer(0));
        // Actualiza
        t.setIdFase(CFase.CER_ESPERA_IMPRIMIR);
        t.setUltimaRespuesta("ASOCIADO");
        t.setLastIdHistoria(1);
        updateTurno(t);

        // Se crea el historial de la entrega
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        String estacion3 = "X-MESA.CONTROL.REG." + t.getIdCirculo();
        tHist3.setIdAdministradorSAS(estacion3);
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdWorkflow());
        tHist.setRespuesta(tipo);
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_ESPERA_IMPRIMIR);
        tHist3.setFaseAnterior(CFase.CER_SOLICITUD);
        addTurnoHistoria(t, tHist3, new Integer(0));

        // crear TurnoEjecucion
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            TurnoEjecucionEnhanced turnoEje = new TurnoEjecucionEnhanced();
            turnoEje.setEstacion("X-MESA.CONTROL.REG." + t.getIdCirculo());
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_ESPERA_IMPRIMIR);
            turnoEje.setHasWF(false);
            turnoEje.setIdWorkflow(t.getIdWorkflow());
            pm.makePersistent(turnoEje);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * @param t
     * @param solicitud
     * @param user
     * @throws DAOException
     */
    public void crearHistoriaTurnoCertificadoMasivo(Turno t, Hashtable solicitud, Usuario user) throws DAOException {

        //	Se crea el historial de la creacion
        TurnoHistoria tHist;
        tHist = new TurnoHistoria();
        tHist.setActivo(false);
        tHist.setAnio(t.getAnio());
        tHist.setFecha(t.getFechaInicio());
        String usuarioIniciador = (String) solicitud.get(Processor.USUARIO_INICIADOR);
        tHist.setIdCirculo(t.getIdCirculo());
        tHist.setIdProceso(t.getIdProceso());
        tHist.setIdTurno(t.getIdWorkflow());
        String tipo = (String) solicitud.get(Processor.RESULT);
        tHist.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist.setUsuario(user);
        tHist.setUsuarioAtiende(user);
        tHist.setFase("SOLICITUD");
        //tHist.setFaseAnterior(CFase.CER_CREAR);
        addTurnoHistoria(t, tHist, new Integer(0));
        // Actualiza
        t.setIdFase(CFase.CER_ENTREGAR);
        t.setUltimaRespuesta(tipo);
        t.setLastIdHistoria(3);
        updateTurno(t);

        // Se crea el historial de la creacion
        TurnoHistoria tHist2;
        tHist2 = new TurnoHistoria();
        tHist2.setActivo(false);
        tHist2.setAnio(t.getAnio());
        tHist2.setFecha(t.getFechaInicio());
        tHist2.setIdCirculo(t.getIdCirculo());
        tHist2.setIdProceso(t.getIdProceso());
        tHist2.setIdTurno(t.getIdWorkflow());
        tHist2.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist2.setUsuario(user);
        tHist2.setUsuarioAtiende(user);
        tHist2.setFase(CFase.CER_SOLICITUD);
        tHist2.setRespuesta(CTipoCertificado.TIPO_INMEDIATO_NOMBRE);
        tHist2.setFaseAnterior("SOLICITUD");
        addTurnoHistoria(t, tHist2, new Integer(0));

        //Se debe consultar quien puede atender esta fase
        // Se crea el historial de la entrega
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        String estacion3 = "X-ENTREGA.CER." + t.getIdCirculo();
        tHist3.setIdAdministradorSAS(estacion3);
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdWorkflow());
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_ENTREGAR);
        tHist3.setFaseAnterior(CFase.CER_SOLICITUD);
        tHist3.setActivo(true);
        addTurnoHistoria(t, tHist3, new Integer(0));

        // crear TurnoEjecucion
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            TurnoEjecucionEnhanced turnoEje = new TurnoEjecucionEnhanced();
            turnoEje.setEstacion("X-ENTREGA.CER." + t.getIdCirculo());
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_ENTREGAR);
            turnoEje.setHasWF(false);
            turnoEje.setIdWorkflow(t.getIdWorkflow());
            pm.makePersistent(turnoEje);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Actualizar la informacion de un turno Historia Informacion Reimpresion
     *
     * @param turno
     * @throws <code>DAOException</code>
     */
    public void updateTurnoReimpresionCertificado(Turno turno, Usuario usuario, Folio folio) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhanced t = TurnoEnhanced.enhance(turno);

        try {
            pm.currentTransaction().begin();

            TurnoEnhancedPk tId = new TurnoEnhancedPk();
            tId.anio = t.getAnio();
            tId.idCirculo = t.getIdCirculo();
            tId.idProceso = t.getIdProceso();
            tId.idTurno = t.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(tId, pm);

            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = usuario.getIdUsuario();

            UsuarioEnhanced usu = this.getUsuarioByID(uId, pm);

            long lastIdturnoHistoria = tr.getLastIdHistoria();

            TurnoHistoriaEnhancedPk thid = new TurnoHistoriaEnhancedPk();
            thid.anio = t.getAnio();
            thid.idCirculo = t.getIdCirculo();
            thid.idProceso = t.getIdProceso();
            thid.idTurno = t.getIdTurno();
            thid.idTurnoHistoria = String.valueOf(t.getLastIdHistoria());

            TurnoHistoriaEnhanced turnoHistoriamax = this.getTurnoHistoriaByID(thid, pm);

            turnoHistoriamax.setActivo(false);
            turnoHistoriamax.setRespuesta(CRespuesta.OK);
            turnoHistoriamax.setUsuarioAtiende(usu);

            pm.makePersistent(turnoHistoriamax);

            TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();

            th.setActivo(false);
            th.setFaseAnterior(turnoHistoriamax.getFase());
            th.setAnio(turno.getAnio());
            th.setFase(CFase.CER_IMPRIMIR);
            th.setRespuesta(CRespuesta.CONFIRMAR);
            th.setFecha(new Date());
            th.setIdCirculo(turno.getIdCirculo());
            th.setIdProceso(turno.getIdProceso());
            th.setIdTurno(turno.getIdTurno());
            th.setUsuario(usu);
            th.setUsuarioAtiende(usu);
            th.setIdMatriculaImpresa(folio.getIdMatricula());
            th.setIdAdministradorSAS(turnoHistoriamax.getIdAdministradorSAS());
            th.setIdTurnoHistoria(String.valueOf(lastIdturnoHistoria + 1));
            th.setNumeroCopiasReimpresion(1);

            pm.makePersistent(th);

            th = new TurnoHistoriaEnhanced();

            th.setActivo(true);
            th.setFaseAnterior(CFase.CER_IMPRIMIR);
            th.setAnio(turno.getAnio());
            th.setFase(turnoHistoriamax.getFase());
            th.setFecha(new Date());
            th.setIdCirculo(turno.getIdCirculo());
            th.setIdProceso(turno.getIdProceso());
            th.setIdTurno(turno.getIdTurno());
            th.setUsuario(usu);
            th.setIdAdministradorSAS(turnoHistoriamax.getIdAdministradorSAS());
            th.setIdTurnoHistoria(String.valueOf(lastIdturnoHistoria + 2));

            tr.setLastIdHistoria(lastIdturnoHistoria + 2);

            pm.makePersistent(th);

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }
    
    public int getLastIndexNotaI(Nota nota) throws DAOException {
        int index = 0;
        String ans = "";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT MAX(ID_NOTA) FROM SIR_OP_NOTA " 
                  + "WHERE ANIO='"+nota.getAnio()+"' AND ID_CIRCULO='"+nota.getIdCirculo()+"' AND ID_PROCESO = '"
                +String.valueOf(nota.getIdProceso())+"' AND ID_TURNO='"+nota.getIdTurno()+"'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            ans = (String) rs.getString(1);
            }
            
            if(ans != null && !ans.isEmpty()){
                index = Integer.parseInt(ans);
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

        return index;

    }
    
    public UsuarioEnhanced getUsuarioEnhById(String idUsuario, PersistenceManager pm) throws DAOException {
        UsuarioEnhanced user = null;

        try {
             Query query = pm.newQuery(UsuarioEnhanced.class);
             Collection col = null;
             
             query.declareVariables("UsuarioEnhanced usuario");
             query.declareParameters("String idUsuario");
             query.setFilter("this.idUsuario==\""+idUsuario+"\"");
             col = (Collection) query.execute(idUsuario);
            
             Iterator itCol = col.iterator();
             while(itCol.hasNext()){
                 user = (UsuarioEnhanced) itCol.next();
             }
             query.closeAll();
         } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
        }
          return user;
    }
    
    public TipoNotaEnhanced getTipoNotaEnhById(String idTipoNota, PersistenceManager pm) throws DAOException {
        TipoNotaEnhanced note = null;

        try {
             Query query = pm.newQuery(TipoNotaEnhanced.class);
             Collection col = null;
             
             query.declareVariables("TipoNotaEnhanced tipoNota");
             query.declareParameters("String idTipoNota");
             query.setFilter("this.idTipoNota==idTipoNota");
             col = (Collection) query.execute(idTipoNota);
            
             Iterator itCol = col.iterator();
             while(itCol.hasNext()){
                 note = (TipoNotaEnhanced) itCol.next();
             }
             query.closeAll();
         } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
        }
          return note;
    }
    
    /**
     * Agrega un control de reasignación
     * @param turno
     * @param usuarioOrigen
     * @param usuarioDestino 
     * @throws DAOException 
     */
    public void addCtrlReasignacion(Turno turno, String usuarioOrigen, String usuarioDestino) throws DAOException {
        VersantPersistenceManager jdoPM = null;
        Connection connection = null;
        
        String sql = "INSERT INTO SIR_OP_CONTROL_REASIGNACION (CTRL_ID_WORKFLOW, CTRL_FASE, CTRL_USUARIO_ORIGEN, CTRL_USUARIO_DESTINO) "
                + " VALUES ('"+turno.getIdWorkflow()+"', '"+turno.getIdFase()+"', '"+usuarioOrigen+"', '"+usuarioDestino+"')";
                

        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
        
    }
    
    
    public List getNotaDevNotificada(String turnoWF) throws DAOException {
      List notified = new ArrayList();
      PersistenceManager pm = AdministradorPM.getPM();
      Connection connection = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      NotificacionNota notify = null;
      VersantPersistenceManager jdoPM = null;
      String consulta = " SELECT * FROM SIR_OP_NOT_NOTA_DEV  WHERE NOT_ID_WORKFLOW = '" + turnoWF + "' ";

      try {
         jdoPM = (VersantPersistenceManager)AdministradorPM.getPM();
         jdoPM.currentTransaction().begin();
         connection = jdoPM.getJdbcConnection((String)null);
         ps = connection.prepareStatement(consulta);
         rs = ps.executeQuery();

         while(rs.next()) {
            notify = new NotificacionNota();
            notify.setIdNotificacion(rs.getString("ID_NOTIFICACION"));
            notify.setTurnoWF(rs.getString("NOT_ID_WORKFLOW"));
            notify.setDestino(rs.getString("NOT_DESTINO"));
            notify.setTipo(rs.getString("NOT_TIPO"));
            notify.setApoderado(Integer.parseInt(rs.getString("NOT_APODERADO")));
            notify.setNombres(rs.getString("NOT_NOMBRES"));
            notify.setApellidos(rs.getString("NOT_APELLIDOS"));
            notify.setTipoDocumento(rs.getString("NOT_TIPO_DOCUMENTO"));
            notify.setDocumento(rs.getString("NOT_DOCUMENTO"));
            notify.setDireccion(rs.getString("NOT_DIRECCION"));
            notify.setTelefono(rs.getString("NOT_TELEFONO"));
            notify.setCorreo(rs.getString("NOT_CORREO"));
            String dateFormat = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date fechaNotificacion = null;
            Date fechaToParse = rs.getDate("FECHA_NOTIFICACION"); 
             
            /*
            fechaToParse = fechaToParse.replace("12:00:00.0", "");
            fechaToParse = fechaToParse.replace("00:00:00.0", "");
            fechaToParse = fechaToParse.replace("-", "/");
            fechaToParse = fechaToParse.trim();
            String[] sFecha = fechaToParse.split("/");
            String reOrder = sFecha[2] + "/" + sFecha[1] + "/" + sFecha[0]; */
            String reOrder = DateFormatUtil.format("dd/MM/yyyy", fechaToParse);

            try {
               fechaNotificacion = DateFormatUtil.parse(reOrder);
            } catch (ParseException var27) {
               System.out.println("ERROR" + var27.getMessage());
            }

            notify.setFechaNotificacion(fechaNotificacion);
            notify.setIdOficinaOrigen(rs.getString("ID_OFICINA_ORIGEN"));
            notified.add(notify);
         }

         jdoPM.currentTransaction().commit();
         return notified;
      } catch (SQLException var29) {
         if (jdoPM.currentTransaction().isActive()) {
            jdoPM.currentTransaction().rollback();
         }

         Log.getInstance().error(JDOTurnosDAO.class, var29.getMessage(), var29);
         throw new DAOException(var29.getMessage(), var29);
      } catch (JDOObjectNotFoundException var30) {
         if (jdoPM.currentTransaction().isActive()) {
            jdoPM.currentTransaction().rollback();
         }

         Log.getInstance().error(JDOTurnosDAO.class, var30.getMessage(), var30);
         throw new DAOException(var30.getMessage(), var30);
      } catch (JDOException var31) {
         if (jdoPM.currentTransaction().isActive()) {
            jdoPM.currentTransaction().rollback();
         }

         Log.getInstance().error(JDOTurnosDAO.class, var31.getMessage(), var31);
         throw new DAOException(var31.getMessage(), var31);
      } catch (Throwable var32) {
         if (jdoPM.currentTransaction().isActive()) {
            jdoPM.currentTransaction().rollback();
         }

         Log.getInstance().error(JDOTurnosDAO.class, var32.getMessage(), var32);
         throw new DAOException(var32.getMessage(), var32);
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
         } catch (SQLException var28) {
            Log.getInstance().error(JDOTurnosDAO.class, var28);
            Log.getInstance().error(JDOTurnosDAO.class, var28.getMessage(), var28);
            throw new DAOException(var28.getMessage(), var28);
         }

      }
   }
    
    public void updateRecurso(Recurso recurso, String turnoWF) throws DAOException{
        Connection connection = null;
        String[] idWorkflow = turnoWF.split("-");
        VersantPersistenceManager jdoPM = null;
        String fecha = DateFormatUtil.format(recurso.getFecha());
        String sql = " UPDATE SIR_OP_RECURSO " +
            " SET RCRS_TITULO_RECURSO = '"+recurso.getTitulo()+"', RCRS_FECHA_CREACION = TO_DATE('"+ fecha +"', 'DD/MM/YYYY'), " +
            " RCRS_TEXTO_USUARIO = '"+recurso.getTextoUsuario()+"', RCRS_TEXTO_RECURSO = '"+recurso.getTextoRecurso()+"' " +
            " WHERE ANIO='"+idWorkflow[0]+"' AND ID_CIRCULO='"+idWorkflow[1]+"' AND ID_PROCESO='"+idWorkflow[2]+"' AND ID_TURNO='"+idWorkflow[3]+"' AND ID_RECURSO = '"+recurso.getIdRecurso()+"'";
            
        Statement s = null;
            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                s = connection.createStatement();
                s.executeUpdate(sql);
                jdoPM.currentTransaction().commit();
                reorderRecursos(turnoWF);
            } catch (SQLException e) {
               throw new DAOException("Error SQL: " + e, e);
            } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
    }
    
    public int getLastIdRecurso (String turnoWF) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] idWorkflow = turnoWF.split("-");
        VersantPersistenceManager jdoPM = null;
        int id = 0;
        String resp = null;

        String consulta = " SELECT COUNT(ID_RECURSO) FROM SIR_OP_RECURSO " +
            " WHERE ANIO='"+idWorkflow[0]+"' AND ID_CIRCULO='"+idWorkflow[1]+"' AND ID_PROCESO='"+idWorkflow[2]+"' AND ID_TURNO='"+idWorkflow[3]+"'";
        
        try {

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            rs = ps.executeQuery();

            if (rs.next()) {
                resp = rs.getString(1);
            }
            
            if(resp != null){
                id = Integer.parseInt(resp);
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
        return id;
    }
    
    public void updateLastIdRecurso(String turnoWF) throws DAOException{
        Connection connection = null;
        String[] idWorkflow = turnoWF.split("-");
        VersantPersistenceManager jdoPM = null;
        int lastId = getLastIdRecurso(turnoWF);
        String sql = " UPDATE SIR_OP_TURNO_HISTORIA " +
            " SET TRHS_LAST_ID_RECURSO = '"+String.valueOf(lastId)+"' " +
            " WHERE ANIO='"+idWorkflow[0]+"' AND ID_CIRCULO='"+idWorkflow[1]+"' AND ID_PROCESO='"+idWorkflow[2]+"' AND ID_TURNO='"+idWorkflow[3]+"' AND TRHS_ID_FASE = 'NOT_RECURSOS_NOTA'";
            
        Statement s = null;
            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                s = connection.createStatement();
                s.executeUpdate(sql);
            } catch (SQLException e) {
               throw new DAOException("Error SQL: " + e, e);
            } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
    }
    
    public void deleteRecurso(String idRecurso, String turnoWF) throws DAOException{
        Connection connection = null;
        VersantPersistenceManager jdoPM = null;
        String[] idWorkflow = turnoWF.split("-");
        String sql = " DELETE FROM SIR_OP_RECURSO " +
            " WHERE ANIO='"+idWorkflow[0]+"' AND ID_CIRCULO='"+idWorkflow[1]+"' AND ID_PROCESO='"+idWorkflow[2]+"' AND ID_TURNO='"+idWorkflow[3]+"' AND ID_RECURSO = '"+idRecurso+"'";
            
        Statement s = null;
            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                s = connection.createStatement();
                s.executeUpdate(sql);
                reorderRecursos(turnoWF);
            } catch (SQLException e) {
               throw new DAOException("Error SQL: " + e, e);
            } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
    }
    
    public void executeDMLFromSQL(String sql) throws DAOException{
        Connection connection = null;
        VersantPersistenceManager jdoPM = null;
            
        Statement s = null;
            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                s = connection.createStatement();
                s.executeUpdate(sql);
            } catch (SQLException e) {
               throw new DAOException("Error SQL: " + e, e);
            } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
    }
    
    public String getStringByQuery(String sql) throws DAOException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String ans = null;
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            if(rs.next()){
            ans = rs.getString(1);
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

        return ans;
    }
    
    
    public int diasHabiles(String idCirculo, String fecha) throws DAOException{
        int diasHabiles = 0;
        int diasNo = 0;
        String resp = null; 
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = " SELECT COUNT(CRFS_FECHA_FESTIVO) FROM SIR_NE_CIRCULO_FESTIVO " +
                         "  WHERE ID_CIRCULO = '"+ idCirculo +"' AND "
                       + " (CRFS_FECHA_FESTIVO >= TO_DATE('"+ fecha +"', 'DD/MM/YYYY') AND CRFS_FECHA_FESTIVO <= SYSDATE)";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date dateObj = calendar.getTime();
        String formattedDate = dateFormat.format(dateObj);
        Date fechaInicial = null;
        Date fechaFinal = null;
        try{
            fechaInicial=dateFormat.parse(fecha);
            fechaFinal=dateFormat.parse(formattedDate);
        } catch(ParseException e){
            System.out.println("ERROR: " + e.getMessage());
        }
        
        int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

        System.out.println("Hay "+dias+" dias de diferencia");

            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                ps = connection.prepareStatement(consulta);
                rs = ps.executeQuery();

                if(rs.next()){
                resp = (String) rs.getString(1);
                }
                
                
                diasNo = Integer.parseInt(resp);
                jdoPM.currentTransaction().commit();

            } catch (SQLException e) {
                if (jdoPM.currentTransaction().isActive()) {
                    jdoPM.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOLookupDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } catch (JDOObjectNotFoundException e) {
                if (jdoPM.currentTransaction().isActive()) {
                    jdoPM.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOLookupDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } catch (JDOException e) {
                if (jdoPM.currentTransaction().isActive()) {
                    jdoPM.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOLookupDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } catch (Throwable e) {
                if (jdoPM.currentTransaction().isActive()) {
                    jdoPM.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOLookupDAO.class, e.getMessage(), e);
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
                    Log.getInstance().error(JDOLookupDAO.class, e);
                    Log.getInstance().error(JDOLookupDAO.class, e.getMessage(), e);
                    throw new DAOException(e.getMessage(), e);
                }
            }
        
        dias = dias - diasNo;
        return dias;
    }
    /**
     * Agrega la Notificación de una nota devolutiva
     * @param notify
     * @throws DAOException 
     */
    public void notificarNotaDevolutiva(NotificacionNota notify) throws DAOException {
        VersantPersistenceManager jdoPM = null;
        Connection connection = null;
        String destino = notify.getDestino();
        String tipo = notify.getTipo();
        String apoderado = String.valueOf(notify.getApoderado());
        String nombres = (notify.getNombres()!=null?"'"+notify.getNombres()+"'":"null");
        String apellidos = (notify.getApellidos()!=null?"'"+notify.getApellidos()+"'":"null");
        String tipoDoc = (notify.getTipoDocumento()!=null?"'"+notify.getTipoDocumento()+"'":"null");
        String direccion = (notify.getDireccion()!=null?"'"+notify.getDireccion()+"'":"null");
        String telefono = (notify.getTelefono()!=null?"'"+notify.getTelefono()+"'":"null");
        String turnoWF = notify.getTurnoWF();
        String correo = (notify.getCorreo()!=null?"'"+notify.getCorreo()+"'":"null");
        String numeroDoc = (notify.getDocumento()!=null?"'"+notify.getDocumento()+"'":"null");
        Date date = notify.getFechaNotificacion();
        String dateFormat = "dd/MM/yy hh:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        
        String sql = " INSERT INTO SIR_OP_NOT_NOTA_DEV (NOT_DESTINO, NOT_TIPO, NOT_APODERADO, NOT_NOMBRES, NOT_APELLIDOS, NOT_TIPO_DOCUMENTO, NOT_DIRECCION, NOT_TELEFONO, FECHA_NOTIFICACION, NOT_ID_WORKFLOW, NOT_CORREO, NOT_DOCUMENTO) " +
            " VALUES ('"+destino+"', '"+tipo+"', '"+apoderado+"', "+nombres+", "+apellidos+", "+tipoDoc+", "+direccion+", "+telefono+", TO_DATE('"+formatter.format(date)+"', 'DD/MM/YY HH:MI:SS'), '"+turnoWF+"', "+correo+", "+numeroDoc+")";
                

        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
        
    }
    
    /**
     *  Agrega una nota informativa
     * @param note
     * @throws DAOException 
     */
    public void addNotaInformativa(Nota note) throws DAOException {
        VersantPersistenceManager jdoPM = null;
        Connection connection = null;
        int idx = getLastIndexNotaI(note);
        idx++;
        Date date = note.getTime();
        String dateFormat = "dd/MM/yy hh:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String index = String.valueOf(idx);
        String idProcess = String.valueOf(note.getIdProceso());
        String user = String.valueOf(note.getUsuario().getIdUsuario());
        
        String sql = "INSERT INTO SIR_OP_NOTA (ANIO, NOTA_DESCRIPCION, ID_CIRCULO, NOTA_ID_FASE, ID_NOTA, ID_PROCESO, ID_TURNO, ID_TH_REGISTRO, NOTA_TIME, ID_TIPO_NOTA, VERSION, ID_USUARIO) "
                + " VALUES ('"+note.getAnio()+"', '"+note.getDescripcion()+"', '"+note.getIdCirculo()+"', "
                + "'"+(note.getTiponota().getIdTipoNota().equals("23177")?"ADMINISTRATIVO":note.getIdFase())+"',"
                + " '"+index+"', '"+idProcess+"', '"+note.getIdTurno()+"', '"+note.getIdProceso()+"', TO_DATE('"+formatter.format(date)+"','DD/MM/YY HH:MI:SS'), '"+note.getTiponota().getIdTipoNota()+"', '1', '"+user+"')";
                

        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
        
    }
    
    /**
     *  Agrega una lista de notas informativas
     * @param notasInformativas
     * @throws DAOException 
     */
    public void addNotasInformativas(List notasInformativas) throws DAOException {
      Iterator ItNota = notasInformativas.iterator();
      while(ItNota.hasNext()){
       Nota note = (Nota) ItNota.next();
       addNotaInformativa(note);
       updateLastIdNota(note);
      }
    }
    
    public void updateLastIdNota(Nota note) throws DAOException {
        VersantPersistenceManager jdoPM = null;
        Connection connection = null;
        int idx = getLastIndexNotaI(note);
        String index = String.valueOf(idx);
        
        String idWorkflow = "";
        idWorkflow += note.getAnio() + "-";
        idWorkflow += note.getIdCirculo() + "-";
        idWorkflow += String.valueOf(note.getIdProceso()) + "-";
        idWorkflow += note.getIdTurno();
        
        String sql = "UPDATE SIR_OP_TURNO SET TRNO_LAST_ID_NOTA = '"+index+"' WHERE TRNO_ID_WORKFLOW = '"+idWorkflow+"'";
                

        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
        
    }
    
    /**
     * Añade un registro en el control de matriculas
     *
     * @param idMatricula
     * @param accion
     * @param rol
     * @param idWorkflow
     * @throws <code>DAOException</code>
     */
    public void addCtrlMatricula(String idMatricula, String accion, String rol, String idWorkflow) throws DAOException {
        VersantPersistenceManager jdoPM = null;
        Connection connection = null;
  
        
        String sql = "INSERT INTO SIR_OP_CONTROL_MATRICULA (ID_MATRICULA,CTRL_ACCION,CTRL_ROL,CTRL_TURNO) " +
                     " VALUES('"+idMatricula+"','"+accion+"','"+rol+"','"+idWorkflow+"')";
                

        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
        
    }
    
    
    /**
     * Actualizar la informacion de un turno.
     * <p>
     * En particular actualiza los atributos del turno historia, la fecha de
     * finalizaci?n del turno (si aplica) y el identificador de la fase.
     *
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void updateTurno(Turno turno) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhanced t = TurnoEnhanced.enhance(turno);
        //JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {
            pm.currentTransaction().begin();

            TurnoEnhancedPk tId = new TurnoEnhancedPk();
            tId.anio = t.getAnio();
            tId.idCirculo = t.getIdCirculo();
            tId.idProceso = t.getIdProceso();
            tId.idTurno = t.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(tId, pm);

            if (t.getIdFase().equals("FINALIZADO")) {
                tr.setFechaFin(new Date());
            } else {
                if (t.getIdFase() != null) {
                    tr.setIdFase(t.getIdFase());
                }

                if (t.getDescripcion() != null) {
                    tr.setDescripcion(t.getDescripcion());
                }

                if (t.getUltimaRespuesta() != null) {
                    tr.setUltimaRespuesta(t.getUltimaRespuesta());
                }
            }

            if (t.getLastIdHistoria() > 0) {
                this.updateTurnoHistoria(t, pm);
            }

            /*if (t.getSolicitud()!= null){
                    if (t.getSolicitud().getSolicitudFolios() != null){
                            List folios = t.getSolicitud().getSolicitudFolios();
                            Iterator it = folios.iterator();
                            while (it.hasNext()){
                                    SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) it.next();
                                    SolicitudFolioEnhanced.ID sfId =  new SolicitudFolioEnhanced.ID();
                                    sfId.idMatricula = sf.getIdMatricula();
                                    sfId.idSolicitud = sf.getIdSolicitud();
                                    sfId.idZonaRegistral = sf.getIdZonaRegistral();
                                    SolicitudFolioEnhanced sfr = solicitudesDAO.g
                                    FolioEnhanced f = (FolioEnhanced) sf.getFolio();

                            }
                    }
            }*/
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public void actualizarTurnoModoBloqueo(Turno turno) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhanced t = TurnoEnhanced.enhance(turno);

        try {
            pm.currentTransaction().begin();

            TurnoEnhancedPk tId = new TurnoEnhancedPk();
            tId.anio = t.getAnio();
            tId.idCirculo = t.getIdCirculo();
            tId.idProceso = t.getIdProceso();
            tId.idTurno = t.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(tId, pm);
            tr.setModoBloqueo(turno.getModoBloqueo());

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Actualiza en el modelo operativo la informaci?n del turno, el cu?l debe
     * quedar en la fase de entrega.
     *
     * @param t
     * @param solicitud
     * @param user
     * @throws DAOException
     */
    public void actualizarTurnoCertificadoAsociado(Turno t, Hashtable solicitud, Usuario user) throws DAOException {

        // Se crea el historial de la fase de certificados  asociados
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        String estacion3 = "X-ENTREGA.CER." + t.getIdCirculo();
        String tipo = (String) solicitud.get(Processor.RESULT);
        tHist3.setIdAdministradorSAS(estacion3);
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdWorkflow());
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_ENTREGAR);
        tHist3.setFaseAnterior(CFase.CER_ESPERA_IMPRIMIR);
        addTurnoHistoria(t, tHist3, new Integer(0));

        //Se actualizan los datos del turno
        t.setIdFase(CFase.CER_ENTREGAR);
        t.setUltimaRespuesta(tipo);
        t.setLastIdHistoria(2);
        updateTurno(t);

        //Actualizar el TurnoEjecucion
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            Log.getInstance().debug(JDOTurnosDAO.class, "ACTUALIZA TURNO ASOCIADO " + t.getIdWorkflow());

            pm.currentTransaction().begin();

            TurnoEjecucionEnhancedPk idTurnoEjecucionEnh = new TurnoEjecucionEnhancedPk();
            idTurnoEjecucionEnh.idWorkflow = t.getIdWorkflow();
            TurnoEjecucionEnhanced turnoEje = (TurnoEjecucionEnhanced) pm.getObjectById(idTurnoEjecucionEnh, true);

            if (turnoEje == null) {
                throw new DAOException("No se encontr? el turno ejecucion con el id: " + t.getIdWorkflow());
            }

            turnoEje.setEstacion("X-ENTREGA.CER." + t.getIdCirculo());
            turnoEje.setNotificacionWF("0");
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_ENTREGAR);
            turnoEje.setHasWF(false);
            pm.makePersistent(turnoEje);
            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Agrega un turno historia en el sistema y lo asocia al turno
     *
     * @param t turno a la que se debe asociar el turno historia
     * @param turnoHistoria, <code>TurnoHistoria</code> que va a ser agregado al
     * turno.
     * @param marca La marca del stackPos que debe ser asignada al Turno
     * Historia
     * @return true si se inserta el turno historia, false de lo contrario
     * @throws DAOException
     */
    public boolean addTurnoHistoria(Turno turno, TurnoHistoria turnoHistoria, Integer numCopias) throws DAOException {
        TurnoEnhanced t = TurnoEnhanced.enhance(turno);
        //TurnoHistoriaEnhanced tr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            /*tr = */
            this.addTurnoHistoria(t, turnoHistoria, numCopias, pm);

//            pm.currentTransaction().commit();

            /**
             * @author Fernando Padilla Velez
             * @change 6760: Acta - Requerimiento No 191 - Pantalla
             * Administrativa SGD, Se comentan estan lineas, ya que se realiz?
             * refactoring al proceso y ya no son necesarias.
             */
            if (turno.getIdFase().indexOf("FINALIZADO") != -1
                    || turno.getIdFase().indexOf("ENTREGA") != -1) {
                if (turno.getIdProceso() == 1) {
                    if (turno.getSolicitud().getLiquidaciones() != null) {
                        Liquidacion liquidacion = (Liquidacion) turno.getSolicitud().
                                getLiquidaciones().get(turno.getSolicitud().getLiquidaciones().size() - 1);
                        LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
                        if (("PERTENENCIA").equals(liquidaCert.getTipoCertificado().getNombre())
                                || ("AMPLIACION_TRADICION").equals(liquidaCert.getTipoCertificado().getNombre())
                                || ("ANTIGUO_SISTEMA").equals(liquidaCert.getTipoCertificado().getNombre())) {

                            SGD sgd = new SGD(turno, turnoHistoria.getUsuario().getUsername());
                            sgd.enviarEstadoTurno();
                        }
                    }
                } else {
                    SGD sgd = new SGD(turno, turnoHistoria.getUsuario().getUsername());
                    sgd.enviarEstadoTurno();
                }

            }

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Throwable thr) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();

            }
            Log.getInstance().error(JDOTurnosDAO.class, thr.getMessage());
            throw new DAOException(thr.getMessage(), thr);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Actualiza el usuario que atendi? el ?ltimo turno historia de un proceso
     * dado.
     *
     * @param turno <code>Turno</code> sobre el cu?l se quiere actualizar el
     * turno historia.
     * @param nombreFase <code>String</code> sobre la cu?l se quiere actualizar
     * el turno historia.
     * @param usuarioAtiende <code>Usuario</code> que atendi? la fase dada.
     * @return <code>boolean</code> con la respuesta se se actualiz? o n? el
     * turno.
     * @throws <code>Throwable</code>
     */
    public boolean actualizarUsuarioAtiendeTurnoHistoria(Turno turno, String nombreFase, Usuario usuarioAtiende) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhancedPk tid = new TurnoEnhancedPk();
            tid.anio = turno.getAnio();
            tid.idCirculo = turno.getIdCirculo();
            tid.idProceso = turno.getIdProceso();
            tid.idTurno = turno.getIdTurno();

            UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
            userId.idUsuario = usuarioAtiende.getIdUsuario();

            UsuarioEnhanced userEnh = (UsuarioEnhanced) pm.getObjectById(userId, true);

            if (userEnh == null) {
                throw new DAOException("No se encontr? el usuario dado.");
            }

            this.updateUsuarioAtiendeLastTurnoHistoriaByFase(tid, nombreFase, userEnh, pm);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Agrega un turno historia en el sistema y lo asocia al turno
     *
     * @param t turno a la que se debe asociar el turno historia
     * @param faseAnterior faseAnterior en el workflow.
     * @param actividad Fase actual de workflow.
     * @return true si se inserta el turno historia, false de lo contrario
     * @throws DAOException
     */
    /*
    protected boolean addTurnoHistoria(TurnoEnhanced turno, String usuario,
        String marca, String faseAnterior, String actividad, String estacionSAS) throws DAOException {
        TurnoEnhanced t = turno;
        TurnoHistoriaEnhanced tr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            tr = this.addTurnoHistoria(t, usuario, marca, faseAnterior,
                    actividad, estacionSAS, pm);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } finally {
            pm.close();
        }

        return true;
    }
     */
    /**
     * Agrega un turno historia en el sistema y lo asocia al turno, metodo
     * utilizado para transacciones
     *
     * @param t turno a la que se debe asociar el turno historia
     * @param m message con los atributos de la instacia de wf asociada al turno
     * @param pm PersistenceManager de la transaccion
     * @return true si se inserta el turno historia, false de lo contrario
     * @throws DAOException
     */
    protected TurnoHistoriaEnhanced addTurnoHistoria(TurnoEnhanced t, TurnoHistoria turnoHistoria, Integer numCopias, PersistenceManager pm) throws DAOException {
        TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        UsuarioEnhanced us;

        if (t != null) {
            try {
                TurnoEnhancedPk tId = new TurnoEnhancedPk();
                tId.anio = t.getAnio();
                tId.idCirculo = t.getIdCirculo();
                tId.idProceso = t.getIdProceso();
                tId.idTurno = t.getIdTurno();

                TurnoEnhanced tr = this.getTurnoByID(tId, pm);

                if (tr == null) {
                    throw new DAOException("No se encontro el turno: "
                            + t.getIdTurno() + " para crear su historia");
                }

                //VALIDACION DEL USUARIO:
                String usuario = turnoHistoria.getUsuario().getUsername();

                if (usuario != null) {
                    us = variablesDAO.getUsuarioByLogin(usuario, pm);

                    if (us == null) {
                        throw new DAOException("No se encontro el usuario: "
                                + usuario);
                    }

                    th.setUsuario(us);
                } else {
                    throw new DAOException("NO ES POSIBLE CREAR EL TURNO HISTORIA, ES NECESARIO EL USUARIO");
                }

                th.setActivo(true);

                th.setTurno(tr);

                th.setIdTurnoHistoria(String.valueOf(tr.getLastIdHistoria()
                        + 1));
                tr.setLastIdHistoria(tr.getLastIdHistoria() + 1);
                th.setFecha(new Date());

                String respuesta = turnoHistoria.getRespuesta();
                if (respuesta != null) {
                    th.setRespuesta(turnoHistoria.getRespuesta());
                }

                th.setFase(turnoHistoria.getFase());
                th.setFaseAnterior(turnoHistoria.getFaseAnterior());
                th.setStackPos(turnoHistoria.getStackPos());
                th.setIdAdministradorSAS(turnoHistoria.getIdAdministradorSAS());
                if (numCopias == null) {
                    th.setNumeroCopiasReimpresion(turnoHistoria.getNumeroCopiasReimpresion());
                } else {
                    List listaHistorials = t.getHistorials();
                    if (listaHistorials != null && listaHistorials.size() > 0) {
                        /*TurnoHistoriaEnhanced thActualizado = */
                        this.updateNumCopiasLastTurnoHistoriaByFase(tId, th.getFaseAnterior(), numCopias, pm);
                    }
                }

                String idMatriculaReimpresa = turnoHistoria.getIdMatriculaImpresa();
                if (idMatriculaReimpresa != null) {
                    th.setIdMatriculaImpresa(turnoHistoria.getIdMatriculaImpresa());
                }

                //Explicar por que o no se comenta esta linea.
                //th.setRespuesta(t.getUltimaRespuesta());
                //th.setProceso(t.getCirculoproceso().getProceso());
                //ACTUALIZAR EL USUARIO DEL TURNO HISTORIA INMEDIATAMENTE ANTERIOR.
                if (turnoHistoria.getUsuarioAtiende() == null) {
                    List listaHistorials = t.getHistorials();
                    if (listaHistorials != null && listaHistorials.size() > 0) {
                        /*TurnoHistoriaEnhanced thActualizado = */
                        this.updateUsuarioAtiendeLastTurnoHistoriaByFase(tId, th.getFaseAnterior(), us, pm);
                    }
                } else {
                    UsuarioEnhanced usuAtiende = variablesDAO.getUsuarioByLogin(turnoHistoria.getUsuarioAtiende().getUsername(), pm);
                    /* Si hubo reasignacion de Turno en correccion de calificacion
                 * se asegura que quien va atender ese turno sea el nuevo dueno 
                 * del bloqueo
                     */

                    if (tr.getIdProceso() == 6L && tr.getModoBloqueo() == CModoBloqueo.DELEGAR_USUARIO
                            && tr.getIdFase().equals(CFase.CAL_CALIFICACION)
                            && tr.getUsuarioDestino().getIdUsuario() != us.getIdUsuario()) {
                        tr.setUsuarioDestino(us);
                    }

                    if (usuAtiende == null) {
                        throw new DAOException("No se encontro el usuario: "
                                + th.getUsuarioAtiende().getUsername());
                    }

                    th.setUsuarioAtiende(usuAtiende);
                }

                pm.makePersistent(th);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                th = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }finally{
                pm.currentTransaction().commit();
            }
        }

        return th;
    }

    /**
     * Modifica el turno historia actual de un turno, metodo utilizado para
     * transacciones
     *
     * @param t turno a la que se debe modificar su ultimo el turno historia
     * @param pm PersistenceManager de la transaccion
     * @return true si se modifica el turno historia, false de lo contrario
     * @throws DAOException
     */
    protected TurnoHistoriaEnhanced updateTurnoHistoria(TurnoEnhanced t,
            PersistenceManager pm) throws DAOException {
        TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
        //JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        if (t != null) {
            try {
                TurnoEnhancedPk tId = new TurnoEnhancedPk();
                tId.anio = t.getAnio();
                tId.idCirculo = t.getIdCirculo();
                tId.idProceso = t.getIdProceso();
                tId.idTurno = t.getIdTurno();

                TurnoEnhanced tr = this.getTurnoByID(tId, pm);

                if (tr == null) {
                    throw new DAOException("No se encontro el turno: "
                            + t.getIdTurno() + " para crear su historia");
                }

                TurnoHistoriaEnhancedPk thId = new TurnoHistoriaEnhancedPk();
                thId.anio = t.getAnio();
                thId.idCirculo = t.getIdCirculo();
                thId.idProceso = t.getIdProceso();
                thId.idTurno = t.getIdTurno();
                thId.idTurnoHistoria = String.valueOf(t.getLastIdHistoria());

                th = this.getTurnoHistoriaByID(thId, pm);

                if (th != null) {
                    th.setActivo(false);
                    th.setRespuesta(t.getUltimaRespuesta());
                }
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                th = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return th;
    }

    /**
     * Obtiene la lista de turnos asociados a una estacion
     *
     * @param id_estacion el identificador de la estacion
     * @param id_proceso el identificador del proceso
     * @param id_fase el identificador de la fase
     * @return una lista de objetos Turno
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws DAOException
     */
    public List
            getTurnos(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws DAOException {
        List local_Result;
        // local_Result = getTurnos_Impl1( estacion, rol, usuario, proceso, fase );
        //local_Result = getTurnos_Impl2( estacion, rol, usuario, proceso, fase );
        String procesoSt = proceso.getIdProceso() + "";
        //Caso nuevo para el proceso de Reparto Notarial.
        if (procesoSt.equals(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {
            local_Result = getTurnosRepartoNotarial(estacion, rol, usuario, proceso, fase, circulo);
        } else if (procesoSt.equals(CProceso.PROCESO_CONSULTAS)
                || procesoSt.equals(CProceso.PROCESO_FOTOCOPIAS)
                || procesoSt.equals(CProceso.PROCESO_ANTIGUO_SISTEMA)
                || procesoSt.equals(CProceso.PROCESO_REGISTRO)
                || procesoSt.equals(CProceso.PROCESO_CERTIFICADOS_MASIVOS)
                || procesoSt.equals(CProceso.PROCESO_CALIFICACIONES)
                || procesoSt.equals(CProceso.PROCESO_PAGO_MAYOR_VALOR)
                || procesoSt.equals(CProceso.PROCESO_CORRECCION)
                || procesoSt.equals(CProceso.PROCESO_CORRECCIONES)
                || procesoSt.equals(CProceso.PROCESO_CORRECCION_CALIFICACION)
                || procesoSt.equals(CProceso.PROCESO_REPARTO_NOTARIAL_RESTITUCION)
                || procesoSt.equals(CProceso.PROCESO_DEVOLUCIONES)
                || procesoSt.equals(CProceso.PROCESO_REVISION_CONFRONTACION)
                || procesoSt.equals(CProceso.PROCESO_NOT_NOTA_DEVOLUTIVA)
                || procesoSt.equals(CProceso.PROCESO_NOT_NOTA_NOTIFICADA)
                || procesoSt.equals(CProceso.PROCESO_NOT_RECURSOS_NOTA)){
            local_Result = getTurnosEjecucion(estacion, rol, usuario, proceso, fase, circulo);
        } else if (procesoSt.equals(CProceso.PROCESO_CERTIFICADOS)) {
            local_Result = getTurnosEjecucion(estacion, rol, usuario, proceso, fase, null);
        } else {
            local_Result = getTurnos_Impl3(estacion, rol, usuario, proceso, fase);
        }

        return local_Result;

    } // end-method:

    public List
            getTurnos_Impl3(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase)
            throws DAOException {
        return null;
    } // end-method: getTurnos_Impl2

    public List
            getTurnosRepartoNotarial(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo)
            throws DAOException {

        // Estrategia de cambios
        // 1. Se consultan los turnos en la tabla TurnoEjecucion, donde la estaci?n y la fase sean las recibidas
        //    como par?metros.
        // 2. Se valida la sincronizaci?n de estos turnos con el wf.
        PersistenceManager pm = AdministradorPM.getPM();

        pm.currentTransaction().begin();

        String procesoSt = String.valueOf(proceso.getIdProceso());
        String estacionSt = estacion.getEstacionId();
        String faseSt = fase.getID();
        List rta = new ArrayList();

        try {

            Query query = pm.newQuery(TurnoEnhanced.class);
            Collection col = null;

            if (circulo != null && circulo.getIdCirculo() != null) {

                query.declareVariables("TurnoEjecucionEnhanced turnoEjecucion");
                query.declareParameters("String estacion, String fase, String idCir");

                query.setFilter("this.idCirculo==idCir && "
                        + "turnoEjecucion.idWorkflow == this.idWorkflow && "
                        + "turnoEjecucion.fase == fase && "
                        + "turnoEjecucion.estacion == estacion");
                query.setOrdering("idTurno ascending");

                col = (Collection) query.execute(estacionSt, faseSt, circulo.getIdCirculo());

            } else {
                query.declareVariables("TurnoEjecucionEnhanced turnoEjecucion");
                query.declareParameters("String estacion, String fase");

                query.setFilter("turnoEjecucion.idWorkflow == this.idWorkflow && turnoEjecucion.fase == fase && turnoEjecucion.estacion == estacion");
                query.setOrdering("idTurno ascending");

                col = (Collection) query.execute(estacionSt, faseSt);
            }
            Iterator iter = col.iterator();
            TurnoEnhanced turno = null;

            while (iter.hasNext()) {
                turno = (TurnoEnhanced) iter.next();

                //Si es un turno de reparto notarial, debe hacerse transiente la minuta.
                if (procesoSt.equals(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {

                    SolicitudRepartoNotarialEnhanced solReparto = new SolicitudRepartoNotarialEnhanced();
                    solReparto = (SolicitudRepartoNotarialEnhanced) turno.getSolicitud();

                    if (solReparto != null) {
                        MinutaEnhanced minuta = solReparto.getMinuta();
                        pm.makeTransient(minuta);
                        pm.makeTransient(solReparto);
                    }
                }
                pm.makeTransient(turno);
                rta.add(turno);
            }

            pm.currentTransaction().commit();
            query.closeAll();
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }
            
    public List getTurnosPMY(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws DAOException{
        List rta = null;
        rta = this.getTurnosEjecucion(estacion, rol, usuario, proceso, fase, circulo);
        
        Iterator itRta = rta.iterator();
        while(itRta.hasNext()){
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            VersantPersistenceManager jdoPM = null;
            Turno turnoREL = new Turno();
            String sql;
            Turno turno = (Turno) itRta.next();
           sql = " SELECT TRNO_REL FROM SIR_OP_TURNO WHERE TRNO_ID_WORKFLOW='"+turno.getIdWorkflow()+"' AND TRNO_REL=1";
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
            turnoREL.setTurnoREL(rs.getString(1));
            }
            
            if(turnoREL.getTurnoREL() != null){
                itRta.remove();
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
            
        }
        
        return rta;
    }
    
    
    public String currentStateNotaNotificada(String idWorkflow) throws DAOException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String rtaStr = null;
        String[] turno  = idWorkflow.split("-");
        String currentState = "";
        
        String sql;
        sql = " SELECT TRHS_SENT_JUZGADO FROM SIR_OP_TURNO_HISTORIA " +
            " WHERE ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"' "
                + " AND TRHS_ID_FASE='NOT_NOTA_NOTIFICADA' AND "
                + " ID_TURNO_HISTORIA = (SELECT MAX(ID_TURNO_HISTORIA) FROM SIR_OP_TURNO_HISTORIA "
                + " WHERE ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"' AND TRHS_ID_FASE='NOT_NOTA_NOTIFICADA')";
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
            rtaStr = rs.getString(1);
            }
            
            if(rtaStr == null || rtaStr.equals("0")){
                currentState = CDevoluciones.START_NOTA_NOTIFICADA;
            } else if(rtaStr.equals("1")){
                currentState = CDevoluciones.JUZGADO_NOTA_NOTIFICADA;
            } else if(rtaStr.equals("2")){
                currentState = CDevoluciones.RECURSOS_NOTA_NOTIFICADA;
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
            
        return currentState;
    }
    
    public String getFechaTurnoJuzgado(String idWorkflow) throws DAOException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String rtaStr = null;
        String[] turno  = idWorkflow.split("-");
        
        String sql;
        sql = " SELECT TRHS_FECHA_JUZGADO FROM SIR_OP_TURNO_HISTORIA " +
            " WHERE ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"' "
                + " AND TRHS_ID_FASE='NOT_NOTA_NOTIFICADA' AND "
                + " TRHS_SENT_JUZGADO = '1'";
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
            rtaStr = rs.getString(1);
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
            
        return rtaStr;
    }
    
    public void setStateNotaNotificada(String state, String idWorkflow) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] turno  = idWorkflow.split("-");
        VersantPersistenceManager jdoPM = null;
        String upState = "";
        
        if(state.equals(CDevoluciones.START_NOTA_NOTIFICADA)){
            upState = "0";
        } else if(state.equals(CDevoluciones.JUZGADO_NOTA_NOTIFICADA)){
            upState = "1";
        } else if(state.equals(CDevoluciones.RECURSOS_NOTA_NOTIFICADA)){
            upState = "2";
        } else{
            throw new DAOException("El estado de la nota devolutiva notificada no es valido");
        }
 
        String sql = "UPDATE SIR_OP_TURNO_HISTORIA SET TRHS_SENT_JUZGADO = '"+upState+"' " + 
                 " WHERE ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"' "
                + " AND TRHS_ID_FASE='NOT_NOTA_NOTIFICADA' AND "
                + " ID_TURNO_HISTORIA = (SELECT MAX(ID_TURNO_HISTORIA) FROM SIR_OP_TURNO_HISTORIA "
                + " WHERE ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"' AND TRHS_ID_FASE='NOT_NOTA_NOTIFICADA')";
        
        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
    }
    
    public Boolean isTurnoDevuelto(String idWorkflow) throws DAOException{
        Boolean rta = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String rtaStr = null;
        String[] turno  = idWorkflow.split("-");
        
        String sql;
        sql = " SELECT TRHS_RESPUESTA FROM SIR_OP_TURNO_HISTORIA " +
            " WHERE TRHS_ID_FASE = 'CAL_CALIFICACION' AND TRHS_STACK_POS = (SELECT MAX(TRHS_STACK_POS) FROM SIR_OP_TURNO_HISTORIA WHERE ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"') AND " +
            " ANIO = '"+turno[0]+"' AND ID_CIRCULO = '"+turno[1]+"' AND ID_PROCESO = '"+turno[2]+"' AND ID_TURNO = '"+turno[3]+"'";
      
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
            rtaStr = rs.getString(1);
            }
            
            if(rtaStr != null && (rtaStr.equals("DEVOLUCION") || rtaStr.equals("INSCRIPCION_PARCIAL"))){
                rta = true;
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
    
    public Boolean isTurnoREL(String idWorkflow) throws DAOException{
        Boolean rta = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        Turno turnoREL = new Turno();
        String sql;
        sql = " SELECT TRNO_REL FROM SIR_OP_TURNO WHERE TRNO_ID_WORKFLOW='"+ idWorkflow +"'";
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
            turnoREL.setTurnoREL(rs.getString(1));
            }
            
            if(turnoREL.getTurnoREL() != null){
                rta = true;
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
        

    public List getTurnosEjecucion(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase, Circulo circulo) throws DAOException {

        // Estrategia de cambios
        // 1. Se consultan los turnos en la tabla TurnoEjecucion, donde la estaci?n y la fase sean las recibidas
        //    como par?metros.
        // 2. Se valida la sincronizaci?n de estos turnos con el wf.
        PersistenceManager pm = AdministradorPM.getPM();

        pm.currentTransaction().begin();

        //String procesoSt  = String.valueOf( proceso.getIdProceso() );
        String estacionSt = estacion.getEstacionId();
        String faseSt = fase.getID();
        List rta = new ArrayList();
        Query query = null;

        try {

            query = pm.newQuery(TurnoEnhanced.class);

            Collection col = null;

            if (circulo != null && circulo.getIdCirculo() != null) {
                query.declareVariables("TurnoEjecucionEnhanced turnoEjecucion");
                //query.declareParameters("String estacion, String fase, String idCir");
                query.declareParameters("String estacion, String fase, String circulo");

                //query.setFilter("this.idCirculo==idCir && " +
                query.setFilter(
                        "idCirculo == circulo && "
                        + "fechaFin == null && "
                        + "turnoEjecucion.idWorkflow == this.idWorkflow && "
                        + "turnoEjecucion.fase == fase && "
                        + "turnoEjecucion.estacion == estacion ");

                query.setOrdering("fechaInicio ascending");

                col = (Collection) query.execute(estacionSt, faseSt, circulo.getIdCirculo());

                Iterator iter = col.iterator();
                TurnoEnhanced turno = null;
                while (iter.hasNext()) {
                    turno = (TurnoEnhanced) iter.next();
                    pm.makeTransient(turno);
                    rta.add(turno);
                }

            } else {

                query.declareVariables("TurnoEjecucionEnhanced turnoEjecucion");
                query.declareParameters("String estacion, String fase");

                query.setFilter("this.idWorkflow == turnoEjecucion.idWorkflow && "
                        + "turnoEjecucion.fase == fase && "
                        + "turnoEjecucion.estacion == estacion");

                query.setOrdering("fechaInicio ascending");

                col = (Collection) query.execute(estacionSt, faseSt);

                Iterator iter = col.iterator();
                TurnoEnhanced turno = null;
                while (iter.hasNext()) {
                    turno = (TurnoEnhanced) iter.next();
                    List a = turno.getSolicitud().getSolicitudesPadres();
                    if (a.isEmpty() && turno.getFechaFin() == null) {
                        pm.makeTransient(turno);
                        rta.add(turno);
                    }
                }
            }

            pm.currentTransaction().commit();

        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            query.closeAll();
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    public List
            getTurnos_Impl2(Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase)
            throws DAOException {
        return null;
    } // end-method: getTurnos_Impl2

    public List getTurnos_Impl1(Estacion estacion, Rol rol, Usuario usuario,
            Proceso proceso, Fase fase) throws DAOException {
        return null;
    }

    /**
     * Verifica si un turno es v?lido entre los registros de ejecuci?n de SAS
     *
     * @param idWorkflow
     * @return
     * @throws DAOException
     */
    public boolean isValidTurnoSAS(String idWorkflow) throws DAOException {
        return false;
    }

    /**
     * Verifica si un turno es v?lido entre los registros de ejecuci?n de SAS
     *
     * @param idWorkflow
     * @return
     * @throws DAOException
     */
    public String lastAdministradorTurnoSAS(String idWorkflow) throws DAOException {
        return null;
    }

    /**
     * Obtiene un turno dado el identificador de su instancia en workflow,
     *
     * @param wfId Documento de pago con sus atributos numero de cheque y numero
     * de cuenta
     * @return Turno con todos sus atributos.
     * @throws DAOException
     */
    public Turno getTurnoByWFId(String wfId) throws DAOException {
        TurnoEnhanced tr = null;
        Turno rta = null;
        TurnoEnhancedPk turnoId = new TurnoEnhancedPk(wfId);
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            tr = this.getTurnoByID(turnoId, pm);

            if (tr == null) {
                throw new DAOException("El turno " + wfId + " no existe.");
            }

            this.makeTransientTurno(tr, pm);

            pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        if (tr != null) {
            rta = (Turno) tr.toTransferObject();
        }
        return rta;
    }

    /**
     * Obtiene un turno dado el identificador de su instancia en workflow,
     * m?todo utilizado para transacciones
     *
     * @param wfId Documento de pago con sus atributos numero de cheque y numero
     * de cuenta
     * @param pm PersistenceManager de la transaccion
     * @return Turno con todos sus atributos.
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoByWFId(String wfId, PersistenceManager pm)
            throws DAOException {
        TurnoEnhanced rta = null;
        String Wf = wfId;

        try {

            //SE INTENTA OBTENER EL CIRCULO PARA COLOCARLO COMO PARTE DE LA CONSULTA
            String wfTurno = wfId;
            boolean wfValido = false;
            String idCirculo = null;

            if (wfTurno.indexOf("-") != -1) {
                String[] partes = wfTurno.split("-");
                if (partes.length == 4) {
                    wfValido = true;
                    TurnoPk idTurno = new TurnoPk(wfTurno);
                    idCirculo = idTurno.idCirculo;
                }
            }

            Query query = pm.newQuery(TurnoEnhanced.class);

            if (wfValido) {
                //query.declareParameters("String Wf, String idCir");
                query.declareParameters("String Wf");
            } else {
                query.declareParameters("String Wf");
            }

            if (wfValido) {
                //query.setFilter("idWorkflow == Wf  && idCirculo ==idCir");
                query.setFilter("idWorkflow == Wf");
            } else {
                query.setFilter("idWorkflow == Wf");
            }

            Collection col = null;
            if (wfValido) {
                //col = (Collection) query.execute(Wf, idCirculo);	
                col = (Collection) query.execute(Wf);
            } else {
                col = (Collection) query.execute(Wf);
            }

            if (col.size() == 0) {
                rta = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    rta = (TurnoEnhanced) iter.next();
                }

                query.closeAll();
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Obtiene un turno dado el identificador de su solicitud, si no tiene turno
     * asociado retorna null
     *
     * @param solID
     * @return
     * @throws DAOException
     */
    public Turno getTurnoBySolicitud(SolicitudPk solID) throws DAOException {
        TurnoEnhanced tr = null;
        Turno rta = null;
        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        try {
            pm.currentTransaction().begin();
            SolicitudEnhanced sol = solDAO.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);
            if (sol == null) {
                throw new DAOException("No encontr? una solicitud con el ID: " + solID.idSolicitud);
            }

            tr = this.getTurnoBySolicitud(sol, pm);
            pm.makeTransient(tr);

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } finally {
            pm.close();
        }
        if (tr != null) {
            rta = (Turno) tr.toTransferObject();
        }

        return rta;
    }

    /**
     * Obtiene un turno dado el identificador de su solicitud asociada, m?todo
     * utilizado para transacciones
     *
     * @param wfId Documento de pago con sus atributos numero de cheque y numero
     * de cuenta
     * @param pm PersistenceManager de la transaccion
     * @return Turno con todos sus atributos.
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoBySolicitud(SolicitudEnhanced sol,
            PersistenceManager pm) throws DAOException {
        TurnoEnhanced rta = null;

        try {
            Query query = pm.newQuery(TurnoEnhanced.class);
            if (sol != null && sol.getCirculo() != null && sol.getCirculo().getIdCirculo() != null) {
                query.declareParameters("SolicitudEnhanced sol, String circulo");
            } else {
                query.declareParameters("SolicitudEnhanced sol");
            }
            if (sol != null && sol.getCirculo() != null && sol.getCirculo().getIdCirculo() != null) {
                query.setFilter("this.solicitud == sol && this.idCirculo == circulo");
            } else {
                query.setFilter("this.solicitud == sol");
            }

            Collection col = null;
            if (sol != null && sol.getCirculo() != null && sol.getCirculo().getIdCirculo() != null) {
                col = (Collection) query.execute(sol, sol.getCirculo().getIdCirculo());

            } else {
                col = (Collection) query.execute(sol);

            }

            if (col.size() == 0) {
                rta = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    rta = (TurnoEnhanced) iter.next();
                }

                query.closeAll();
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * @author Cesar Ram?rez
     * @change: 1607.AJUSTE.BANCO.FRANQUICIA.FORMAS.PAGO Funcion que obtiene el
     * TurnoEnhanced a trav?s del id de la Solicitud.
     * @param solId <code>String</code>
     * @param pm <code>PersistenceManager</code> de la transacci?n.
     * @return tr <code>TurnoEnhanced</code>
     * @throws DAOException
     *
     */
    protected TurnoEnhanced getTurnoBySolicitud(String solId, PersistenceManager pm) throws DAOException {
        TurnoEnhanced tr = null;
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        if (solId != null) {
            try {
                SolicitudPk solID = new SolicitudPk();
                solID.idSolicitud = solId;

                SolicitudEnhanced sol = solDAO.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);
                if (sol == null) {
                    throw new DAOException("No encontr? una solicitud con el ID: " + solID.idSolicitud);
                }

                Query query = pm.newQuery(TurnoEnhanced.class);
                query.declareParameters("SolicitudEnhanced sol");
                query.setFilter("this.solicitud == sol");
                Collection col = null;
                col = (Collection) query.execute(sol);

                if (col.isEmpty()) {
                    tr = null;
                } else {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        tr = (TurnoEnhanced) iter.next();
                    }
                    query.closeAll();
                }
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } catch (DAOException e) {
                throw e;
            }
        }

        return tr;
    }

    /**
     * Obtiene un Turno dado su identificador.
     * <p>
     * Retorna las anotaciones dependiendo del atributo pasado como par?metro.
     *
     * @param tID identificador del Turno
     * @return Turno con sus atributos y jerarquia: Circuloproceso, Solicitud,
     * Liquidacion, Pagos e Histotia, y las anotaciones dependiendo del atributo
     * pasado como par?metro.
     * @throws DAOException
     */
    protected void makeTransientTurno(TurnoEnhanced tr, PersistenceManager pm) throws DAOException {
        //Turno rta = null;
        //JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        //Turno turnoRetornado = null;
        List listaSolicitudesFueraRango = new ArrayList();

        try {
            if (tr != null) {
                pm.makeTransient(tr.getCirculoproceso());
                solicitudesDAO.makeTransientOficinaOrigen(tr.getSolicitud().getCirculo().getOficinaOrigen(), pm);
                pm.makeTransient(tr.getSolicitud().getCirculo());
                pm.makeTransient(tr.getSolicitud().getProceso());
                pm.makeTransient(tr.getSolicitud().getUsuario());
                this.makeTransientTurno(tr.getSolicitud().getTurnoAnterior(), pm);

                // Hacer transientes las solicitudes de folios
                //EN EL CASO DE REGISTRO, ESTE PROCESO SE HACE DE UNA MANERA DIFERENTE
                //PORQUE SE TRAEN UNICAMENTE LAS SOLICITUDES QUE NO ESTAN INCLUIDAS DENTRO DE
                //UN RANGO. PARA LOS DEMAS PROCESOS ES GENERICO.
                //EN EL CASO DE CONSULTAS TAMBIEN ES DIFERENTE PORQUE SE REQUIEREN TRAER LAS
                //DIRECCIONES DE LOS FOLIOS.
                if (tr.getSolicitud() instanceof SolicitudRegistroEnhanced
                        || tr.getSolicitud() instanceof SolicitudConsultaEnhanced) {
                    //Este c?digo se incluye dentro del instanceofSolicitudRegistro que
                    //se encuentra m?s adelante.
                } else {
                    //Folios
                    List folios = tr.getSolicitud().getSolicitudFolios();
                    Iterator itf = folios.iterator();

                    while (itf.hasNext()) {
                        SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) itf.next();
                        FolioEnhanced f = sf.getFolio();
                        pm.makeTransient(f.getZonaRegistral());
                        pm.makeTransient(f);
                        pm.makeTransient(sf);
                    }
                }

                //Solicitud Certificado
                if (tr.getSolicitud() instanceof SolicitudCertificadoEnhanced) {
                    SolicitudCertificadoEnhanced sc = (SolicitudCertificadoEnhanced) tr.getSolicitud();
                    pm.makeTransient(sc.getCiudadano());

                    if (sc.getDocumento() != null) {
                        if (sc.getDocumento().getOficinaOrigen() != null) {
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen()
                                    .getTipoOficina());

                            if (sc.getDocumento().getOficinaOrigen().getVereda() != null) {
                                if (sc.getDocumento().getOficinaOrigen()
                                        .getVereda().getMunicipio() != null) {
                                    pm.makeTransient(sc.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda()
                                            .getMunicipio()
                                            .getDepartamento());
                                    pm.makeTransient(sc.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda()
                                            .getMunicipio());
                                }

                                pm.makeTransient(sc.getDocumento()
                                        .getOficinaOrigen()
                                        .getVereda());
                            }

                            pm.makeTransient(sc.getDocumento().getOficinaOrigen());
                        }

                        pm.makeTransient(sc.getDocumento().getTipoDocumento());
                        pm.makeTransient(sc.getDocumento());
                    }

                    try {
                        //DatosAntiguoSistemaEnhanced da = sc.getDatosAntiguoSistema();
                        pm.makeTransient(sc.getDatosAntiguoSistema());
                    } catch (JDOObjectNotFoundException e) {
                        //DatosAntiguoSistemaEnhanced da = null;
                    }

                    List solAsocPadre = sc.getSolicitudesPadres();
                    Iterator its2 = solAsocPadre.iterator();

                    while (its2.hasNext()) {
                        SolicitudAsociadaEnhanced solAs = (SolicitudAsociadaEnhanced) its2.next();
                        SolicitudEnhanced sol = solAs.getSolicitudPadre();
                        TurnoEnhanced turnoAsoc = this.getTurnoBySolicitud(sol,
                                pm);
                        sol.setTurno(turnoAsoc);
                        pm.makeTransient(turnoAsoc);
                        pm.makeTransient(sol);
                        pm.makeTransient(solAs);
                    }

                    pm.makeTransientAll(sc.getSolicitudesPadres());

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoCertificadoEnhanced l = (LiquidacionTurnoCertificadoEnhanced) itl.next();
                        pm.makeTransient(l.getTipoCertificado());
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }
                }

                //Solicitud de fotocopias
                if (tr.getSolicitud() instanceof SolicitudFotocopiaEnhanced) {
                    SolicitudFotocopiaEnhanced sc = (SolicitudFotocopiaEnhanced) tr.getSolicitud();
                    pm.makeTransient(sc.getCiudadano());

                    //Documentos de fotocopia
                    DocumentoFotocopiaEnhanced docFot;

                    for (Iterator it = sc.getDocumentoFotocopia().iterator();
                            it.hasNext();) {
                        docFot = (DocumentoFotocopiaEnhanced) it.next();
                        pm.makeTransient(docFot.getTipoDocumento());
                        pm.makeTransient(docFot.getTipoFotocopia());
                        pm.makeTransient(docFot);
                    }

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoFotocopiaEnhanced l = (LiquidacionTurnoFotocopiaEnhanced) itl.next();
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }
                }

                //Solicitud de devoluciones
                if (tr.getSolicitud() instanceof SolicitudDevolucionEnhanced) {
                    SolicitudDevolucionEnhanced sc = (SolicitudDevolucionEnhanced) tr.getSolicitud();
                    pm.makeTransient(sc.getCiudadano());

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoDevolucionEnhanced l = (LiquidacionTurnoDevolucionEnhanced) itl.next();
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }

                    //Hacer transientes los items de Chequeo para las devoluciones
                    List listaDocumentos = sc.getCheckedItems();
                    if (listaDocumentos != null) {
                        for (int ck = 0; ck < listaDocumentos.size(); ck++) {
                            SolCheckedItemDevEnhanced itemSolDev = (SolCheckedItemDevEnhanced) listaDocumentos.get(ck);
                            if (itemSolDev != null) {
                                CheckItemDevEnhanced itemDev = itemSolDev.getCheckItem();
                                pm.makeTransient(itemDev);
                                pm.makeTransient(itemSolDev);
                            }
                        }

                    }

                    //Hacer transientes las consignaciones para las devoluciones
                    List listaConsignaciones = sc.getConsignaciones();
                    if (listaConsignaciones != null) {
                        for (int ck = 0; ck < listaConsignaciones.size(); ck++) {
                            ConsignacionEnhanced cons = (ConsignacionEnhanced) listaConsignaciones.get(ck);
                            if (cons != null) {
                                DocumentoPagoEnhanced doc = cons.getDocPago();
                                pm.makeTransient(doc);
                                pm.makeTransient(cons);
                            }
                        }

                    }

                    //Hacer transientes los solicitantes para las devoluciones
                    List listaSolicitantes = sc.getSolicitantes();
                    if (listaSolicitantes != null) {
                        for (int ck = 0; ck < listaSolicitantes.size(); ck++) {
                            SolicitanteEnhanced solte = (SolicitanteEnhanced) listaSolicitantes.get(ck);
                            if (solte != null) {
                                CiudadanoEnhanced ciu = solte.getCiudadano();
                                pm.makeTransient(ciu);
                                pm.makeTransient(solte);
                            }
                        }

                    }

                }

                //Solicitud de Consultas.
                if (tr.getSolicitud() instanceof SolicitudConsultaEnhanced) {
                    SolicitudConsultaEnhanced sc = (SolicitudConsultaEnhanced) tr.getSolicitud();

                    //Hacer transiente el ciudadano.
                    pm.makeTransient(sc.getCiudadano());

                    if (sc.getDocumento() != null) {
                        if (sc.getDocumento().getOficinaOrigen() != null) {
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen()
                                    .getTipoOficina());

                            if (sc.getDocumento().getOficinaOrigen().getVereda() != null) {
                                if (sc.getDocumento().getOficinaOrigen()
                                        .getVereda().getMunicipio() != null) {
                                    pm.makeTransient(sc.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda()
                                            .getMunicipio()
                                            .getDepartamento());
                                    pm.makeTransient(sc.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda()
                                            .getMunicipio());
                                }

                                pm.makeTransient(sc.getDocumento()
                                        .getOficinaOrigen()
                                        .getVereda());
                            }

                            pm.makeTransient(sc.getDocumento().getOficinaOrigen());
                        }

                        pm.makeTransient(sc.getDocumento().getTipoDocumento());
                        pm.makeTransient(sc.getDocumento());
                    }

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    //Hacer transiente las liquidaciones, los pagos y los documentos de pago.
                    while (itl.hasNext()) {
                        LiquidacionTurnoConsultaEnhanced l = (LiquidacionTurnoConsultaEnhanced) itl.next();
                        pm.makeTransient(l.getAlcanceGeografico());
                        pm.makeTransient(l.getUsuario());
                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }

                    //Hacer transientes las busquedas.
                    List busq = sc.getBusquedas();
                    Iterator it2 = busq.iterator();
                    BusquedaEnhanced bE = null;

                    //Hacer transiente las busquedas.
                    while (it2.hasNext()) {
                        bE = (BusquedaEnhanced) it2.next();
                        pm.makeTransient(bE);
                    }

                    //Folios
                    List folios = sc.getSolicitudFolios();
                    Iterator itf = folios.iterator();

                    while (itf.hasNext()) {
                        SolicitudFolioEnhanced sf = (SolicitudFolioEnhanced) itf.next();
                        FolioEnhanced f = sf.getFolio();
                        pm.makeTransientAll(f.getDirecciones());
                        pm.makeTransient(f.getZonaRegistral());
                        if (f.getDocumento() != null) {
                            if (f.getDocumento().getOficinaOrigen() != null) {
                                if (f.getDocumento().getOficinaOrigen().getVereda() != null) {
                                    if (f.getDocumento().getOficinaOrigen()
                                            .getVereda().getMunicipio() != null) {
                                        pm.makeTransient(f.getDocumento()
                                                .getOficinaOrigen()
                                                .getVereda()
                                                .getMunicipio());
                                    }
                                    pm.makeTransient(f.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda());
                                }
                                pm.makeTransient(f.getDocumento()
                                        .getOficinaOrigen());
                            }
                            pm.makeTransient(f.getDocumento());
                        }
                        pm.makeTransient(f);
                        pm.makeTransient(sf);
                    }

                    //Hacer transiente el tipo de Consulta
                    pm.makeTransient(sc.getTipoConsulta());
                }

                //Solicitud de Correcciones
                if (tr.getSolicitud() instanceof SolicitudCorreccionEnhanced) {
                    SolicitudCorreccionEnhanced scr = (SolicitudCorreccionEnhanced) tr.getSolicitud();

                    //Hacer transiente el ciudadano.
                    pm.makeTransient(scr.getCiudadano());

                    //Hacer transiente datos de antiguo sistema
                    DatosAntiguoSistemaEnhanced da = scr.getDatosAntiguoSistema();

                    if (da != null) {
                        if (da.getDocumento() != null) {
                            if (da.getDocumento() != null) {
                                pm.makeTransient(da.getDocumento()
                                        .getTipoDocumento());

                                if (da.getDocumento().getOficinaOrigen() != null) {
                                    pm.makeTransient(da.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda());
                                    pm.makeTransient(da.getDocumento()
                                            .getOficinaOrigen()
                                            .getTipoOficina());
                                    pm.makeTransient(da.getDocumento()
                                            .getOficinaOrigen());
                                }

                                pm.makeTransient(da.getDocumento());
                            }
                        }
                        pm.makeTransient(da);
                    }

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    //HAacer transientes las liquidaciones, los pagos y los documentos de pago.
                    while (itl.hasNext()) {
                        LiquidacionTurnoCorreccionEnhanced l = (LiquidacionTurnoCorreccionEnhanced) itl.next();
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }

                            pm.makeTransientAll(aps);
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }

                    //NOTAS DE ACTUACIONES ADMINISTRATIVAS
                    List notas = scr.getNotasActuaciones();
                    Iterator itNotas = notas.iterator();

                    while (itNotas.hasNext()) {
                        NotaActuacionEnhanced n = (NotaActuacionEnhanced) itNotas.next();
                        pm.makeTransient(n.getUsuario());
                        pm.makeTransient(n);
                    }

                    //Hacer transiente el tipo de Recepcion Petici?n
                    pm.makeTransient(scr.getTipoRecepcionPeticion());

                    SolicitudPermisoCorreccionEnhanced spc;
                    for (Iterator iter = scr.getPermisos().iterator(); iter.hasNext();) {
                        spc = (SolicitudPermisoCorreccionEnhanced) iter.next();
                        pm.makeTransient(spc.getPermiso());
                        pm.makeTransient(spc);
                    }
                    pm.makeTransientAll(scr.getPermisos());
                }

                //Solicitud Registro
                if (tr.getSolicitud() instanceof SolicitudRegistroEnhanced) {
                    SolicitudRegistroEnhanced sc = (SolicitudRegistroEnhanced) tr.getSolicitud();
                    pm.makeTransient(sc.getCiudadano());

                    DatosAntiguoSistemaEnhanced da = sc.getDatosAntiguoSistema();

                    if (da == null) {
                        sc.setDatosAntiguoSistema(null);
                    } else {
                        if (da.getDocumento() != null) {
                            if (da.getDocumento() != null) {
                                pm.makeTransient(da.getDocumento()
                                        .getTipoDocumento());

                                if (da.getDocumento().getOficinaOrigen() != null) {
                                    pm.makeTransient(da.getDocumento()
                                            .getOficinaOrigen()
                                            .getTipoOficina());

                                    if (da.getDocumento().getOficinaOrigen().getVereda() != null) {
                                        if (da.getDocumento().getOficinaOrigen()
                                                .getVereda().getMunicipio() != null) {
                                            pm.makeTransient(da.getDocumento()
                                                    .getOficinaOrigen()
                                                    .getVereda()
                                                    .getMunicipio()
                                                    .getDepartamento());
                                            pm.makeTransient(da.getDocumento()
                                                    .getOficinaOrigen()
                                                    .getVereda()
                                                    .getMunicipio());
                                        }

                                        pm.makeTransient(da.getDocumento()
                                                .getOficinaOrigen()
                                                .getVereda());
                                    }

                                    pm.makeTransient(da.getDocumento()
                                            .getOficinaOrigen());
                                }

                                pm.makeTransient(da.getDocumento());
                            }
                        }

                        pm.makeTransient(da);
                    }

                    if (sc.getDocumento() != null) {
                        pm.makeTransient(sc.getDocumento().getTipoDocumento());

                        if (sc.getDocumento().getOficinaOrigen() != null) {
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen()
                                    .getTipoOficina());

                            if (sc.getDocumento().getOficinaOrigen().getVereda() != null) {
                                if (sc.getDocumento().getOficinaOrigen()
                                        .getVereda().getMunicipio() != null) {
                                    pm.makeTransient(sc.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda()
                                            .getMunicipio()
                                            .getDepartamento());
                                    pm.makeTransient(sc.getDocumento()
                                            .getOficinaOrigen()
                                            .getVereda()
                                            .getMunicipio());
                                }

                                pm.makeTransient(sc.getDocumento()
                                        .getOficinaOrigen()
                                        .getVereda());
                            }

                            pm.makeTransient(sc.getDocumento().getOficinaOrigen());
                        }

                        pm.makeTransient(sc.getDocumento());
                    }

                    pm.makeTransient(sc.getSubtipoAtencion());
                    pm.makeTransient(sc.getSubtipoSolicitud());

                    if (sc.getSubtipoAtencion() != null) {
                        List subTipos = sc.getSubtipoAtencion()
                                .getSubtipoSolicitudes();
                        Iterator itSubTipos = subTipos.iterator();

                        while (itSubTipos.hasNext()) {
                            SubAtencionSubSolicitud subAt = (SubAtencionSubSolicitud) itSubTipos.next();
                            pm.makeTransient(subAt);
                        }
                    }

                    SolicitudPermisoCorreccionEnhanced spc;
                    for (Iterator iter = sc.getPermisos().iterator(); iter.hasNext();) {
                        spc = (SolicitudPermisoCorreccionEnhanced) iter.next();
                        pm.makeTransient(spc.getPermiso());
                        pm.makeTransient(spc);
                    }
                    pm.makeTransientAll(sc.getPermisos());

                    //Hacer transiente la lista de rangos.
                    pm.makeTransientAll(sc.getRangosFolios());

                    //Hacer transientes las Solicitudes de Folios que no est?n incluidas dentro de
                    //los rangos.
                    listaSolicitudesFueraRango = this.getSolicitudesFueraRango(sc.getRangosFolios(),
                            sc.getSolicitudFolios(), pm);

                    List checks = sc.getCheckedItems();
                    Iterator itc = checks.iterator();

                    while (itc.hasNext()) {
                        SolicitudCheckedItemEnhanced si = (SolicitudCheckedItemEnhanced) itc.next();
                        CheckItemEnhanced c = si.getCheckItem();
                        pm.makeTransient(c);
                        pm.makeTransient(si.getCheckItem());
                        pm.makeTransient(si);
                    }

                    //NOTAS DE ACTUACIONES ADMINISTRATIVAS
                    List notas = sc.getNotasActuaciones();
                    Iterator itNotas = notas.iterator();

                    while (itNotas.hasNext()) {
                        NotaActuacionEnhanced n = (NotaActuacionEnhanced) itNotas.next();
                        pm.makeTransient(n.getUsuario());
                        pm.makeTransient(n);
                    }

                    //SOLICITUDES VINCULADAS:
                    SolicitudVinculadaEnhanced sv;
                    for (Iterator iter = sc.getSolicitudesVinculadas().iterator(); iter.hasNext();) {
                        sv = (SolicitudVinculadaEnhanced) iter.next();
                        pm.makeTransient(sv.getSolicitudPadre());
                        pm.makeTransient(sv);
                    }
                    pm.makeTransientAll(sc.getSolicitudesVinculadas());

                    List solAsoc = sc.getSolicitudesHijas();
                    Iterator its = solAsoc.iterator();

                    while (its.hasNext()) {
                        SolicitudAsociadaEnhanced solAs = (SolicitudAsociadaEnhanced) its.next();
                        SolicitudCertificadoEnhanced solCer = (SolicitudCertificadoEnhanced) solAs.getSolicitudHija();
                        TurnoEnhanced turnoAsoc = this.getTurnoBySolicitud(solCer,
                                pm);
                        solCer.setTurno(turnoAsoc);
                        pm.makeTransient(turnoAsoc);
                        pm.makeTransient(solCer);
                        pm.makeTransient(solAs);
                    }

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoRegistroEnhanced l = (LiquidacionTurnoRegistroEnhanced) itl.next();
                        pm.makeTransient(l.getUsuario());
                        List actos = l.getActos();
                        Iterator ita = actos.iterator();

                        while (ita.hasNext()) {
                            ActoEnhanced a = (ActoEnhanced) ita.next();

                            if (a != null) {
                                if (a.getTipoActo() != null) {
                                    pm.makeTransient(a.getTipoActo()
                                            .getTipoCalculo());
                                }

                                pm.makeTransient(a.getTipoActo());

                                if (a.getTipoDerechoReg() != null) {
                                    pm.makeTransient(a.getTipoDerechoReg()
                                            .getTipoDerechoRegistral());
                                }

                                pm.makeTransient(a.getTipoDerechoReg());
                                pm.makeTransient(a.getTipoImpuesto());
                            }

                            pm.makeTransient(a);
                        }

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }
                }

                //Solicitud de certificados masivos
                if (tr.getSolicitud() instanceof SolicitudCertificadoMasivoEnhanced) {
                    SolicitudCertificadoMasivoEnhanced sc = (SolicitudCertificadoMasivoEnhanced) tr.getSolicitud();
                    pm.makeTransient(sc.getCiudadano());
                    /**
                     * @author : Julio Alc?zar Rivas
                     * @change : makeTransient al documento de la solicitud Caso
                     * Mantis : 000941
                     */
                    if (sc.getDocumento() != null) {
                        pm.makeTransient(sc.getDocumento().getTipoDocumento());

                        if (sc.getDocumento().getOficinaOrigen() != null) {
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento());
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda().getMunicipio());
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen().getVereda());
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen().getTipoOficina());
                            pm.makeTransient(sc.getDocumento().getOficinaOrigen());
                        }

                        pm.makeTransient(sc.getDocumento());
                    }

                    List solAsoc = sc.getSolicitudesHijas();
                    Iterator its = solAsoc.iterator();

                    while (its.hasNext()) {
                        SolicitudAsociadaEnhanced solAs = (SolicitudAsociadaEnhanced) its.next();
                        SolicitudCertificadoEnhanced solCer = (SolicitudCertificadoEnhanced) solAs.getSolicitudHija();
                        TurnoEnhanced turnoAsoc = this.getTurnoBySolicitud(solCer,
                                pm);
                        solCer.setTurno(turnoAsoc);
                        pm.makeTransient(turnoAsoc);
                        pm.makeTransient(solCer);
                        pm.makeTransient(solAs);
                    }

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoCertificadoMasivoEnhanced l = (LiquidacionTurnoCertificadoMasivoEnhanced) itl.next();
                        pm.makeTransient(l.getTipoCertificado());
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }
                }

                // Solicitud de Reparto Notarial
                if (tr.getSolicitud() instanceof SolicitudRepartoNotarialEnhanced) {
                    SolicitudRepartoNotarialEnhanced sc = (SolicitudRepartoNotarialEnhanced) tr.getSolicitud();

                    //Se hace transiente el Ciudadano.
                    pm.makeTransient(sc.getCiudadano());

                    //Se hace transiente el C?rculo
                    pm.makeTransient(sc.getCirculo());

                    //Se hace transiente la Minuta
                    //pm.makeTransient(sc.getMinuta().getAccionNotarial());
                    pm.makeTransient(sc.getMinuta().getCategoria());

                    if (sc.getMinuta().getOficinaCategoriaAsignada() != null) {
                        pm.makeTransient(sc.getMinuta()
                                .getOficinaCategoriaAsignada()
                                .getOficinaOrigen().getVereda()
                                .getMunicipio().getDepartamento());
                        pm.makeTransient(sc.getMinuta()
                                .getOficinaCategoriaAsignada()
                                .getOficinaOrigen().getVereda()
                                .getMunicipio());
                        pm.makeTransient(sc.getMinuta()
                                .getOficinaCategoriaAsignada()
                                .getOficinaOrigen().getVereda());
                        pm.makeTransient(sc.getMinuta()
                                .getOficinaCategoriaAsignada()
                                .getOficinaOrigen());
                        pm.makeTransient(sc.getMinuta()
                                .getOficinaCategoriaAsignada());
                    }

                    pm.makeTransient(sc.getMinuta().getRepartoNotarial());

                    //Hacer transientes los otorgantes P?blicos.
                    if (sc.getMinuta().getEntidadesPublicas() != null) {
                        List listaEntidades = sc.getMinuta().getEntidadesPublicas();
                        for (int j = 0; j < listaEntidades.size(); j++) {
                            MinutaEntidadPublicaEnhanced minutaEntidad = (MinutaEntidadPublicaEnhanced) listaEntidades.get(j);

                            pm.makeTransient(minutaEntidad.getEntidadPublica());
                            pm.makeTransient(minutaEntidad);

                        }
                        pm.makeTransientAll(sc.getMinuta().getEntidadesPublicas());
                    }

                    //Hacer transientes las acciones notariales
                    if (sc.getMinuta().getAccionesNotariales() != null) {
                        List listaAccionesNotariales = sc.getMinuta().getAccionesNotariales();
                        for (int j = 0; j < listaAccionesNotariales.size(); j++) {
                            MinutaAccionNotarialEnhanced minutaAccionNotarial
                                    = (MinutaAccionNotarialEnhanced) listaAccionesNotariales.get(j);

                            pm.makeTransient(minutaAccionNotarial.getAccionNotarial());
                            pm.makeTransient(minutaAccionNotarial);
                        }
                        pm.makeTransientAll(sc.getMinuta().getAccionesNotariales());
                    }

                    //	Hacer transientes los otorgantes Naturales
                    if (sc.getMinuta().getOtorgantesNaturales() != null) {
                        List listaNaturales = sc.getMinuta().getOtorgantesNaturales();
                        for (int k = 0; k < listaNaturales.size(); k++) {
                            OtorganteNaturalEnhanced otorgante = (OtorganteNaturalEnhanced) listaNaturales.get(k);

                            pm.makeTransient(otorgante);

                        }
                        pm.makeTransientAll(sc.getMinuta().getOtorgantesNaturales());
                    }

                    pm.makeTransient(sc.getMinuta());

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoRepartoNotarialEnhanced l = (LiquidacionTurnoRepartoNotarialEnhanced) itl.next();
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());

                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }
                }

                //	Solicitud de Restitucion Reparto Notarial
                if (tr.getSolicitud() instanceof SolicitudRestitucionRepartoEnhanced) {
                    SolicitudRestitucionRepartoEnhanced sc = (SolicitudRestitucionRepartoEnhanced) tr.getSolicitud();

                    //Se hace transiente el Ciudadano.
                    pm.makeTransient(sc.getCiudadano());

                    //Se hace transiente el C?rculo
                    pm.makeTransient(sc.getCirculo());

                    //Se hace transiente el Causal de Restitucion
                    pm.makeTransient(sc.getCausalRestitucion());
                    pm.makeTransient(sc.getTurnoAnterior());

                    List liq = tr.getSolicitud().getLiquidaciones();
                    Iterator itl = liq.iterator();
                    PagoEnhanced p = null;

                    while (itl.hasNext()) {
                        LiquidacionTurnoRestitucionRepartoEnhanced l = (LiquidacionTurnoRestitucionRepartoEnhanced) itl.next();
                        pm.makeTransient(l.getUsuario());

                        try {
                            p = l.getPago();
                            pm.makeTransient(p.getUsuario());
                            List aps = l.getPago().getAplicacionPagos();
                            Iterator itp = aps.iterator();

                            while (itp.hasNext()) {
                                AplicacionPagoEnhanced ap = (AplicacionPagoEnhanced) itp.next();
                                pagosDAO.makeTransientDocumentoPago(ap.getDocumentoPago(), pm);
                                pm.makeTransient(ap);
                            }
                        } catch (JDOObjectNotFoundException JDOe) {
                            p = null;
                        }

                        pm.makeTransient(p);
                        pm.makeTransient(l);
                    }

                }

                pm.makeTransient(tr.getSolicitud());

                List his = tr.getHistorials();
                Iterator it = his.iterator();

                JDOFasesDAO fasesDAO = new JDOFasesDAO();

                //Hacer transiente el historial del turno.
                while (it.hasNext()) {
                    TurnoHistoriaEnhanced th = (TurnoHistoriaEnhanced) it.next();

                    //Hacer transiente los recursos asociados al turno historia:
                    RecursoEnhanced recurso;
                    for (Iterator it2 = th.getRecursos().iterator(); it2.hasNext();) {
                        recurso = (RecursoEnhanced) it2.next();
                        pm.makeTransient(recurso.getTipoRecurso());
                        if (recurso.getOficioRespuesta() != null) {
                            pm.makeTransient(recurso.getOficioRespuesta().getTurnoHistoria());
                            pm.makeTransient(recurso.getOficioRespuesta());
                        }

                        pm.makeTransient(recurso);
                    }
                    pm.makeTransient(th.getUsuario());
                    pm.makeTransient(th.getUsuarioAtiende());
                    pm.makeTransient(th.getProceso());
                    pm.makeTransient(th);
                    th.setNombreFase((fasesDAO.getFaseById(th.getFase())).getNombre());

                }

                List ns = tr.getNotas();
                Iterator itn = ns.iterator();

                while (itn.hasNext()) {
                    NotaEnhanced n = (NotaEnhanced) itn.next();
                    pm.makeTransient(n.getTiponota());
                    pm.makeTransient(n.getUsuario());
                    pm.makeTransient(n);
                }

                pm.makeTransient(tr);

                //En el caso de las solicitudes de registro se debe asignar una lista temporal
                //que contiene solicitudes de folio que no esten dentro de los rangos.
                if (tr.getSolicitud() instanceof SolicitudRegistroEnhanced) {
                    if (listaSolicitudesFueraRango != null) {
                        tr.getSolicitud().setSolicitudFolios(listaSolicitudesFueraRango);
                    } else {
                        tr.getSolicitud().setSolicitudFolios(new ArrayList());
                    }
                }
            }
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return;
    }

    /**
     * Obtiene un Turno dado su identificador.
     * <p>
     * Retorna las anotaciones dependiendo del atributo pasado como par?metro.
     *
     * @param tID identificador del Turno
     * @return Turno con sus atributos y jerarquia: Circuloproceso, Solicitud,
     * Liquidacion, Pagos e Histotia, y las anotaciones dependiendo del atributo
     * pasado como par?metro.
     * @throws DAOException
     */
    public Turno getTurnoByID(TurnoPk tID) throws DAOException {
        TurnoEnhanced tr = null;
        Turno rta = null;
        PersistenceManager pm = AdministradorPM.getPM();
        //JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        //JDOPagosDAO pagosDAO = new JDOPagosDAO();
        //Turno turnoRetornado = null;
        //List listaSolicitudesFueraRango = new ArrayList();

        try {
            pm.currentTransaction().begin();
            tr = this.getTurnoByID(new TurnoEnhancedPk(tID), pm);

            if (tr != null) {
                this.makeTransientTurno(tr, pm);
            }

            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();

            //turnoRetornado = (Turno) tr.toTransferObject();
        }

        if (tr != null) {
            rta = (Turno) tr.toTransferObject(); //turnoRetornado;
        }

        return rta;
    }

    /**
     * Obtiene un Turno dado su identificador, m?todo utilizado para
     * transacciones
     *
     * @param tID identificador del Turno
     * @param pm PersistenceManager de la transaccion
     * @return Turno con sus atributos
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoByID(TurnoEnhancedPk tID,
            PersistenceManager pm) throws DAOException {
        TurnoEnhanced rta = null;

        //Se coloca un tiempo de delay
        JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
        /*boolean respuesta = */
        jdoDAO.getObjectByLlavePrimaria(tID, 3, pm);

        if ((tID.idTurno != null) && (tID.idCirculo != null)
                && (String.valueOf(tID.idProceso) != null)) {
            try {
                rta = (TurnoEnhanced) pm.getObjectById(tID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Agrega una Nota al Turno dado
     *
     * @param nota con sus atributos, exceptuando el identificador
     * @param turno al que debe asociarse la nota
     * @return Turno con sus atributos
     * @throws DAOException
     */
    public NotaPk addNotaToTurno(Nota nota, TurnoPk tID)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        NotaEnhanced notar = null;
        NotaEnhancedPk nId = null;

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            TurnoEnhanced tr = this.getTurnoByID(new TurnoEnhancedPk(tID), pm);

            if (tr == null) {
                throw new DAOException("No se encontro el Turno");
            }

            notar = this.addNotaToTurno(NotaEnhanced.enhance(nota), tr, pm);
            pm.makePersistent(notar);
            pm.currentTransaction().commit();
            nId = (NotaEnhancedPk) pm.getObjectId(notar);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOTurnosDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return nId.getNotaID();
    }

    /**
     * Crea un <code>Testamento</code> persistente y lo asocia a la solicitud de
     * un turno de registro de documentos.
     *
     * @param Turno El turno <code>Turno</code> al que se va a asociar el
     * <code>Testamento</code>.
     * @param testamento El <code>Testamento</code> que va a asociarse a la
     * solicitud de registro de documentos.
     * @return resultado<code>boolean</code> el resultado de el ingreso de el
     * Testamento.
     * @see gov.sir.core.negocio.modelo.Testamento
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>Throwable</code>
     */
    public boolean addTestamentoToSolicitudRegistro(TurnoPk tid, Testamento testamento) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(tid), pm);
            /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion para consultar usuario creacion
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */
            UsuarioEnhanced usuario = null;
            if (testamento.getUsuarioCreacion() != null) {
                JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
                UsuarioEnhancedPk pk = new UsuarioEnhancedPk();
                pk.idUsuario = testamento.getUsuarioCreacion().getIdUsuario();
                usuario = solicitudesDAO.getUsuarioByID(pk, pm);
            }
            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }
            //SE VERIFICA EL PROCESO DE CORRECCIONES O DE REGISTRO
            if (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno NO es del proceso de registro");
            }
            SolicitudRegistroEnhanced solReg = solReg = (SolicitudRegistroEnhanced) turno.getSolicitud();
            if (solReg != null) {
                TestamentoEnhanced testamentoEnhanced;//Pablo Quintana PQ
                /**
                 * S? el testamento existe en base de datos es porque est?
                 * siendo editado y no se vuelve a crear Pablo Quintana Junio 20
                 * 2008
                 */
                testamentoEnhanced = this.getTestamentoByID(solReg.getIdSolicitud(), pm);
                List lstTestamentoCiudadano = new ArrayList();
                TestamentoCiudadanoEnhanced testamentoCiudadanoEnhanced;
                TestamentoCiudadano testamentoCiudadano;
                Ciudadano ciudadano = null;
                CiudadanoEnhanced ciudadanoEnhanced;
                boolean testadoresCrea = false;
                if (testamento.getTestadores() != null) {
                    for (int i = 0; i < testamento.getTestadores().size(); i++) {
                        ciudadanoEnhanced = null;
                        ciudadano = null;
                        testamentoCiudadanoEnhanced = new TestamentoCiudadanoEnhanced();
                        testamentoCiudadano = (TestamentoCiudadano) testamento.getTestadores().get(i);
                        ciudadano = testamentoCiudadano.getCiudadano();
                        if (ciudadano.getIdCiudadano() == null || ciudadano.getIdCiudadano().equals("")) {
                            if (ciudadano == null) {
                                throw new DAOException("Debe tener un testador para el testamento.");
                            }
                            //SI SE TIENE EL ID DEL CIUDADANO, SE CONSULTA. Y SE VERIFICA QUE NO SEA SOLICITANTE
                            if (ciudadano.getIdCiudadano() != null) {
                                CiudadanoEnhancedPk idCiudadano = new CiudadanoEnhancedPk();
                                idCiudadano.idCiudadano = ciudadano.getIdCiudadano();
                                ciudadanoEnhanced = this.getCiudadanoByID(idCiudadano, pm);
                                //SI AL CONSULTAR POR EL IDENTIFICADOR SE ENCUENTRA EL CIUDADANO
                                //PERO ESTA COMO SOLICITANTE SE DEJA PARA QUE SEA INGRESADO COMO NO SOLICITANTE (ANOTACION)
                                if (ciudadanoEnhanced != null && ciudadanoEnhanced.isSolicitante()) {
                                    ciudadanoEnhanced = null;
                                    ciudadano.setIdCiudadano(null);
                                }
                            }
                            if (ciudadanoEnhanced == null && ciudadano.getTipoDoc() != null && ciudadano.getDocumento() != null) {
                                testadoresCrea = true;
                                if (ciudadano.getIdCirculo() == null) {
                                    throw new DAOException("Se debe asociar un circulo para el testador.");
                                }
                                ciudadanoEnhanced = this.getCiudadanoByDocumentoNoSolicitante(ciudadano.getTipoDoc(), ciudadano.getDocumento(), pm, ciudadano.getIdCirculo());
                            }
                            if (ciudadanoEnhanced == null) {
                                CiudadanoEnhanced ciudadanoCrear = new CiudadanoEnhanced();
                                ciudadanoCrear.setTipoDoc(ciudadano.getTipoDoc());
                                ciudadanoCrear.setDocumento(ciudadano.getDocumento());
                                ciudadanoCrear.setApellido1(ciudadano.getApellido1());
                                ciudadanoCrear.setApellido2(ciudadano.getApellido2());
                                ciudadanoCrear.setNombre(ciudadano.getNombre());
                                ciudadanoCrear.setTelefono(ciudadano.getTelefono());
                                ciudadanoCrear.setIdCirculo(ciudadano.getIdCirculo());
                                ciudadanoCrear.setSolicitante(false);
                                ciudadanoEnhanced = this.crearCiudadanoNoSolicitante(ciudadanoCrear, pm);
                                ciudadano.setIdCiudadano(ciudadanoEnhanced.getIdCiudadano());
                            }
                            if (testamentoEnhanced == null) {
                                testamentoEnhanced = new TestamentoEnhanced();
                                testamentoEnhanced.setIdTestamento(solReg.getIdSolicitud());
                                testamentoEnhanced.setNumeroRadicacion(tid.toString());
                            }
                            testamentoEnhanced.setObservacion(testamento.getObservacion());
                            testamentoEnhanced.setTomo(testamento.getTomo());
                            testamentoEnhanced.setNumeroAnotaciones(testamento.getNumeroAnotaciones());
                            testamentoEnhanced.setNumeroCopias(testamento.getNumeroCopias());
                            testamentoEnhanced.setRevocaEscritura(testamento.getRevocaEscritura());
                            /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion asignar el usuario que crea el testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                             */
                            testamentoEnhanced.setUsuarioCreacion(usuario);
                            if (ciudadano != null) {
                                ciudadanoEnhanced.setTipoDoc(ciudadano.getTipoDoc());
                                ciudadanoEnhanced.setApellido1(ciudadano.getApellido1());
                                ciudadanoEnhanced.setApellido2(ciudadano.getApellido2());
                                ciudadanoEnhanced.setNombre(ciudadano.getNombre());
                                ciudadanoEnhanced.setTelefono(ciudadano.getTelefono());
                                ciudadanoEnhanced.setIdCirculo(ciudadano.getIdCirculo());
                            }
                            testamentoCiudadanoEnhanced.setIdCiudadano(ciudadanoEnhanced.getIdCiudadano());
                            testamentoCiudadanoEnhanced.setIdTestamento(testamentoEnhanced.getIdTestamento());
                            testamentoCiudadanoEnhanced.setCiudadano(ciudadanoEnhanced);//
                            testamentoCiudadanoEnhanced.setTestamento(testamentoEnhanced);//
                            pm.makePersistent(testamentoCiudadanoEnhanced);
                            lstTestamentoCiudadano.add(testamentoCiudadanoEnhanced);
                        }
                    }
                    if (!testadoresCrea) {
                        testamentoEnhanced.setObservacion(testamento.getObservacion());
                        testamentoEnhanced.setTomo(testamento.getTomo());
                        testamentoEnhanced.setNumeroAnotaciones(testamento.getNumeroAnotaciones());
                        testamentoEnhanced.setNumeroCopias(testamento.getNumeroCopias());
                        testamentoEnhanced.setRevocaEscritura(testamento.getRevocaEscritura());
                        //pm.makePersistent(testamentoEnhanced);
                    }
                    testamentoEnhanced.setTestadores(lstTestamentoCiudadano);
                }
                solReg.setTestamento(testamentoEnhanced);
            }
            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
            return true;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Obtiene un <code>CiudadanoEnhanced</code> dado su identificador
     *
     * @param cID identificador del <code>Ciudadano</code>
     * @return <code>CiudadanoEnhanced</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @see gov.sir.core.negocio.modelo.jdoGenie.CiudadanoEnhanced
     */
    protected CiudadanoEnhanced getCiudadanoByID(CiudadanoEnhancedPk cID,
            PersistenceManager pm) throws DAOException {
        CiudadanoEnhanced rta = null;

        if (cID.idCiudadano != null) {
            try {
                rta = (CiudadanoEnhanced) pm.getObjectById(cID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene un ciudadano dado el n?mero y el tipo del documento de
     * identificacion.
     * <p>
     * M?todo utilizado por transacciones.
     *
     * @param tipodoc Tipo del documento de Identificacion
     * @param doc N?mero del documento de identificacion del
     * <code>Ciudadano</code>
     * @param pm Persistence Manager de la transacci?n.
     * @param idCirculo TODO
     * @return <code>Ciudadano</code> con sus atributos
     * @throws DAOException
     * @gov.sir.core.negocio.modelo.Ciudadano
     */
    protected CiudadanoEnhanced getCiudadanoByDocumentoNoSolicitante(String tipodoc,
            String doc, PersistenceManager pm, String idCirculo) throws DAOException {
        CiudadanoEnhanced rta = null;

        if ((tipodoc == null) && (doc == null)) {
            return null;
        }

        String Tipo = tipodoc;
        String Documento = doc;

        try {
            if (idCirculo == null) {
                throw new DAOException("No se puede obtener el ciudadano puesto que no tiene un c?rculo asociado");
            }

            //Se establece como filtro de la consulta el tipo y n?mero del
            //documento de identificaci?n del ciudadano.
            Query query = pm.newQuery(CiudadanoEnhanced.class);
            query.declareParameters("String Tipo, String Documento, String idCir");
            query.setFilter("tipoDoc == Tipo && documento == Documento && idCirculo==idCir && solicitante==false");

            Collection col = (Collection) query.execute(Tipo, Documento, idCirculo);

            if (col.size() == 0) {
                rta = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    rta = (CiudadanoEnhanced) iter.next();
                }
                query.closeAll();
            }
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Crea un <code>Ciudadano</code> en el sistema
     * <p>
     * M?todo utilizado para transacciones.
     *
     * @param c Ciudadano con sus atributos, exceptuando el identificador
     * @param pm PersistenceManager de la transaccion
     * @return un <code>CiudadanoEnhanced</code> persistente con su
     * identificador.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Ciudadano
     * @see gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced
     */
    protected CiudadanoEnhanced crearCiudadanoNoSolicitante(CiudadanoEnhanced c,
            PersistenceManager pm) throws DAOException {
        CiudadanoEnhanced rta = null;

        try {
            //Se valida que el ciudadano tenga c?rculo
            if (c.getIdCirculo() == null) {
                throw new DAOException("El ciudadano que se est? intentando insertar no tiene c?rculo asociado");
            }

            //Generar el n?mero de documento del ciudadano.
            JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
            long idCiudadano = solicitudesDAO.getSecuencial("SIR_NE_CIUDADANO", null);

            Long idCiud = new Long(idCiudadano);
            String secuencial = new String(idCiud.toString());
            c.setIdCiudadano(secuencial);
            c.setSolicitante(false);

            if (c.getTipoDoc().equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
                c.setDocumento(secuencial);

            }
            pm.makePersistent(c);
            rta = c;
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Agrega una Nota al Turno dado
     *
     * @param nota con sus atributos, exceptuando el identificador
     * @param turno al que debe asociarse la nota
     * @param pm PersistenceManager
     * @return Turno con sus atributos
     * @throws DAOException
     */
    protected NotaEnhanced addNotaToTurno(NotaEnhanced nota,
            TurnoEnhanced turno, PersistenceManager pm) throws DAOException {
        NotaEnhanced rta = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {
            TipoNotaEnhanced t = nota.getTiponota();
            TipoNotaEnhancedPk tId = new TipoNotaEnhancedPk();
            tId.idTipoNota = t.getIdTipoNota();
            /*
                @fecha 30/10/2012
                @author Carlos Torres
             *  @chage   se asigna el atributo version
                @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Hist?rico
             */
            tId.version = t.getVersion();

            TipoNotaEnhanced tr = this.getTipoNotaByID(tId, pm);

            if (tr == null) {
                throw new DAOException("No encontr? el TipoNota con el ID: "
                        + tId.idTipoNota);
            }

            UsuarioEnhanced u = nota.getUsuario();
            UsuarioEnhancedPk uId = new UsuarioEnhancedPk();
            uId.idUsuario = u.getIdUsuario();

            UsuarioEnhanced ur = solicitudesDAO.getUsuarioByID(uId, pm);

            if (ur == null) {
                throw new DAOException("No encontr? el Usuario con el ID: "
                        + uId.idUsuario);
            }

            nota.setIdNota(String.valueOf(turno.getLastIdNota() + 1));
            nota.setTiponota(tr);
            nota.setUsuario(ur);
            nota.setTurno(turno);
            nota.setTime(new Date());

            //nota.setUsuario(turno.getSolicitud().getUsuario());
            nota.setIdFase(turno.getIdFase());
            turno.setLastIdNota(turno.getLastIdNota() + 1);

            //Agregar al campo turno historia de la nota, el id del turno historia en el
            //que fue a?adida la nota informativa.
            List listaTurnoHistoria = turno.getHistorials();

            if (listaTurnoHistoria != null) {
                String idTurnoHistoria = listaTurnoHistoria.size() + "";
                nota.setIdTurnoHistoria(idTurnoHistoria);
            }

            rta = nota;
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Obtiene un Turno dado su identificador, m?todo utilizado para
     * transacciones
     *
     * @param tID identificador del Turno
     * @param pm PersistenceManager de la transaccion
     * @return Turno con sus atributos
     * @throws DAOException
     */
    protected TipoNotaEnhanced getTipoNotaByID(TipoNotaEnhancedPk tID,
            PersistenceManager pm) throws DAOException {
        TipoNotaEnhanced rta = null;

        if (tID.idTipoNota != null) {
            try {
                /**
                 * @author : HGOMEZ, FPADILLA
                 *** @change : Se valida si la version es cero para obtener la
                 * versi?n del tipo nota. ** Caso Mantis : 12621
                 */
                if (tID.version == 0) {
                    DatosNotas datosNotas = DatosNotas.getInstance();
                    tID.version = datosNotas.obtenerVersionTipoNota(tID.idTipoNota);
                }
                rta = (TipoNotaEnhanced) pm.getObjectById(tID, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Retorna el rol asociado a la fase
     *
     * @param idFase
     * @param pm
     * @return
     */
    protected String getRolByFase(String idFase, PersistenceManager pm) {
        String rta = null;
        Query query = pm.newQuery(Procesos_V.class);
        query.declareParameters("String idFase");
        query.setFilter("this.id_fase==idFase");
        Collection col = (Collection) query.execute(idFase);
        for (Iterator iter = col.iterator(); iter.hasNext();) {
            Procesos_V procesos_v = (Procesos_V) iter.next();
            rta = procesos_v.getRol();
        }
        return rta;
    }

    /**
     * Obtiene un objeto  <code>TurnoHistoriaEnhanced</code> asociado con un
     * <code>Turno</code> cuyo atributo stackPos sea mayor.
     *
     * @param tID identificador del Turno
     * @param pm PersistenceManager de la transaccion
     * @return Objeto <code>TurnoHistoriaEnhanced</code> con sus atributos
     * @throws DAOException
     */
    protected TurnoHistoriaEnhanced getMaxTurnoHistoria(TurnoHistoriaPk tID,
            PersistenceManager pm) throws DAOException {
        TurnoHistoriaEnhanced turnoHistMax = null;

        if ((tID.anio != null) && (tID.idCirculo != null)
                && (tID.idProceso != 0) && (tID.idTurno != null)) {
            String idAno = tID.anio;
            String idCirc = tID.idCirculo;
            Long idProc = new Long(tID.idProceso);
            String idTur = tID.idTurno;

            try {
                Query q = pm.newQuery(TurnoHistoriaEnhanced.class);

                /**
                 * @author: Fernando Padilla Velez
                 * @change: Se establece como criterio de ordenamiento el
                 * atributo idTurnoHistoria de mayor a menor.
                 *
                 */
                q.setOrdering("idTurnoHistoria descending");
                q.declareParameters(
                        "String idAno, String idCirc, String idTur, Long idProc");
                q.setFilter(
                        "anio == idAno && idCirculo == idCirc && idTurno == idTur && idProceso == idProc && stackPos != null");

                Collection results = (Collection) q.executeWithArray(new Object[]{
                    idAno, idCirc, idTur, idProc
                });
                Iterator it = results.iterator();

                //Se obtiene el primer elemento de la lista.
                //(Es el turno historia con mayor stackPos).
                if (it.hasNext()) {
                    turnoHistMax = (TurnoHistoriaEnhanced) it.next();
                    pm.makeTransient(turnoHistMax.getUsuario());
                    pm.makeTransient(turnoHistMax);
                }

                q.close(results);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                turnoHistMax = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return turnoHistMax;
    }

    /**
     * Devuelve un TipoRecurso consultado en la base de datos
     *
     * @param treid
     * @return
     * @throws DAOException
     */
    protected TipoRecursoEnhanced getTipoRecursoByID(
            TipoRecursoEnhancedPk treid, PersistenceManager pm)
            throws DAOException {
        TipoRecursoEnhanced rta = null;

        if (treid.idTipoRecurso != null) {
            try {
                rta = (TipoRecursoEnhanced) pm.getObjectById(treid, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene el tipo de oficio persistente por el ID
     *
     * @param treid
     * @param pm
     * @return
     * @throws DAOException
     */
    protected TipoOficioEnhanced getTipoOficioByID(
            TipoOficioEnhancedPk treid, PersistenceManager pm)
            throws DAOException {
        TipoOficioEnhanced rta = null;

        if (treid.idTipoOficio != null) {
            try {
                rta = (TipoOficioEnhanced) pm.getObjectById(treid, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     *
     * @param treid
     * @param pm
     * @return
     * @throws DAOException
     */
    protected RecursoEnhanced getRecursoByID(RecursoEnhancedPk reid,
            PersistenceManager pm) throws DAOException {
        RecursoEnhanced rta = null;

        if ((reid.anio != null) && (reid.idCirculo != null)
                && (reid.idTurno != null) && (reid.idTurnoHistoria != null)
                && (reid.idRecurso != null)) {
            try {
                rta = (RecursoEnhanced) pm.getObjectById(reid, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     *
     * @param treid
     * @param pm
     * @return
     * @throws DAOException
     */
    protected OficioEnhanced getOficioByID(OficioEnhancedPk reid,
            PersistenceManager pm) throws DAOException {
        OficioEnhanced rta = null;

        if (reid.idOficio != null) {
            try {
                rta = (OficioEnhanced) pm.getObjectById(reid, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene un TurnoHistoria dado su identificador, m?todo utilizado para
     * transacciones
     *
     * @param tID identificador del <code>TurnoHistoria</code>
     * @param pm PersistenceManager de la transaccion
     * @return <code>TurnoHistoria</code> con sus atributos
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.TurnoHistoria
     *
     */
    protected TurnoHistoriaEnhanced getTurnoHistoriaByID(
            TurnoHistoriaEnhancedPk tID, PersistenceManager pm)
            throws DAOException {
        TurnoHistoriaEnhanced rta = null;

        if ((tID.anio != null) && (tID.idCirculo != null)
                && (tID.idProceso != 0) && (tID.idTurno != null)
                && (tID.idTurnoHistoria != null || tID.idTurnoHistoria != "0")) {
            try {
                rta = (TurnoHistoriaEnhanced) pm.getObjectById(tID, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene un objeto <code>UsuarioEnhanced</code> dado su identificador.
     * <p>
     * Genera una excepci?n si no se encuentra el usuario con el identificador
     * dado.
     *
     * @param uID identificador del <code>Usuario</code>
     * @return <code>UsuarioEnhanced</code> con sus atributos.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced
     */
    protected UsuarioEnhanced getUsuarioByID(UsuarioEnhancedPk uID,
            PersistenceManager pm) throws DAOException {
        UsuarioEnhanced rta = null;

        if (String.valueOf(uID.idUsuario) != null) {
            try {
                rta = (UsuarioEnhanced) pm.getObjectById(uID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene una lista de objetos <code>SolicitudFolioEnhanced</code>
     * asociadas con una solicitud de Registro que no se encuentran dentro de la
     * lista de rangos de la solicitud.
     *
     * @param listaRangos Lista de objetos <code>RangoFolio</code> asociados con
     * la solicitud.
     * @return <code>UsuarioEnhanced</code> con sus atributos.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced
     */
    protected List getSolicitudesFueraRango(List listaRangos,
            List listaSolicitudes, PersistenceManager pm) {
        List listaRespuesta = new ArrayList();

        //Se recorre la lista que contiene las solicitudes folio y se determina
        //si estan dentro de alguno de los rangos.
        for (int k = 0; k < listaSolicitudes.size(); k++) {
            SolicitudFolioEnhanced soli = (SolicitudFolioEnhanced) listaSolicitudes.get(k);

            //Se determina el n?mero de la matr?cula.
            //Se elimina el token que contiene el n?mero del c?rculo.
            StringTokenizer st = new StringTokenizer(soli.getIdMatricula(), "-");
            String inicioRango = null;

            while (st.hasMoreTokens()) {
                inicioRango = (st.nextToken());
            }

            Integer IntInicialRango = new Integer(inicioRango);
            int intInicialRango = IntInicialRango.intValue();

            //Se determina si la matr?cula esta dentro de alguno de los rangos.
            int i = 0;
            int esta = 0;

            for (i = 0; i < listaRangos.size(); i++) {
                RangoFolioEnhanced rango = (RangoFolioEnhanced) listaRangos.get(i);

                //Se determina el n?mero para el rango inicial, eliminando el
                //token que contiene el c?rculo y el separador -
                StringTokenizer st2 = new StringTokenizer(rango.getMatriculaInicial(),
                        "-");
                String inicialRango = null;

                while (st2.hasMoreTokens()) {
                    inicialRango = (st2.nextToken());
                }

                Integer IntInicioRango = new Integer(inicialRango);
                int intInicialRangoFolio = IntInicioRango.intValue();

                // Se determina el n?mero para el rango final, eliminando el
                //token que contiene el c?rculo y el separador -
                StringTokenizer st3 = new StringTokenizer(rango.getMatriculaFinal(),
                        "-");
                String finalRango = null;

                while (st3.hasMoreTokens()) {
                    finalRango = (st3.nextToken());
                }

                Integer IntFinalRango = new Integer(finalRango);
                int intFinalRangoFolio = IntFinalRango.intValue();

                if ((intInicialRango >= intInicialRangoFolio)
                        && (intInicialRango <= intFinalRangoFolio)) {
                    i = listaRangos.size() + 1;
                    esta = 1;
                }
            }

            //El folio no pertenece a ning?n rango, entonces se agrega la solicitud
            //folio al listado.
            if (esta == 0) {

                ZonaRegistralEnhancedPk zrId = new ZonaRegistralEnhancedPk();

                zrId.idZonaRegistral = soli.getFolio().getZonaRegistral().getIdZonaRegistral();

                ZonaRegistralEnhanced zr = (ZonaRegistralEnhanced) pm.getObjectById(zrId,
                        true);
                pm.makeTransient(zr.getCirculo());
                pm.makeTransient(zr.getVereda().getMunicipio().getDepartamento());
                pm.makeTransient(zr.getVereda().getMunicipio());
                pm.makeTransient(zr.getVereda());
                pm.makeTransient(zr);

                soli.getFolio().setZonaRegistral(zr);

                pm.makeTransient(soli.getFolio());

                pm.makeTransient(soli);
                listaRespuesta.add(soli);
            }
        }

        if (listaRespuesta.size() == 0) {
            return null;
        }

        return listaRespuesta;
    }

    /**
     * Agrega un <code>Usuario</code> a un <code>Turno</code>
     *
     * @param user  <code>Usuario</code> que ser? asignado al <code>Turnoz</code>
     * @param turno <code>Turno</code> al que ser? asignado el
     * <code>Usuario</code>
     * @return true o false dependiendo del resultado de la operaci?n.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public boolean setUsuarioToTurno(Usuario user, Turno turno)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            TurnoEnhancedPk turnoId = new TurnoEnhancedPk();
            turnoId.anio = turno.getAnio();
            turnoId.idCirculo = turno.getIdCirculo();
            turnoId.idProceso = turno.getIdProceso();
            turnoId.idTurno = turno.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(turnoId, pm);

            if (tr == null) {
                throw new DAOException(
                        "No se encontr? el Turno con el identificador dado.");
            }

            //Obtener el usuario persistente.
            UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
            userId.idUsuario = user.getIdUsuario();

            UsuarioEnhanced userEnh = (UsuarioEnhanced) pm.getObjectById(userId,
                    true);

            if (userEnh == null) {
                throw new DAOException(
                        "No se encontr? el Usuario con el identificador dado.");
            }

            tr.setUsuarioDestino(userEnh);

            pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOTurnosDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Valida que el turno pueda ser anulado.
     * <p>
     * @param idTurno objeto <code>Turno.ID</code> con sus atributos
     * @return true = Si se puede anular, false = Si no se puede anular.
     * @throws <code>DAOException</code>.
     * @see gov.sir.core.negocio.modelo.TurnoPk
     */
    public boolean validarAnulacionTurno(TurnoPk idTurno) throws DAOException {

        boolean resultado = false;

        try {

            Turno t = this.getTurnoByID(idTurno);
            TurnoEnhanced tr = TurnoEnhanced.enhance(t);
            //--- VALIDAR TURNO QUE SE VA ANULAR --//

            //se valida que el turno no este finalizado
            if (tr.getFechaFin() != null) {
                throw new JDOException("El turno esta finalizado, No se puede Anular.");
            }

            /*
             * Reglas de validacion: El turno dependiendo de su naturaleza no puede superar las siguientes etapas.
             * 	Registro: Mesa control
             *  Correcciones: Revision y Analisis
             *  Certificados: En entrega
             *  Antiguo Sistema: Revision y Analisis
             *  Certificados Masivos: En entrega
             *  Super Doc: Entrega
             *  Devoluciones: En resolucion
             *  Consultas: Si no se han realizado consultas
             *  Fotocopias: Liquidacion
             *
             *  Mirar si el turno no tiene liquidacion exento.
             */
            long idProceso = tr.getIdProceso();
            //sin importar el proceso toca verificar que el turno no haya pasado en antiguo sistema la fase de REVISION Y ANALISIS
            if (this.hasFaseInTurnoHistoria(tr, CFase.ANT_REVISION_INICIAL, null)) {
                throw new JDOException("El turno ya supero la fase REVISION INICIAL y no es posible anularlo.");
            }

            if (idProceso == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                //TFS 3068: SE DEBEN PODER ANULAR LOS TURNOS QUE HAYAN PASADO POR MESA DE CONTROL Y/O FIRMA
                //Y SE HAYAN DEVUELTO A CALIFICACION
                if ((this.hasFaseInTurnoHistoria(tr, CFase.REG_MESA_CONTROL, null)
                        || this.hasFaseInTurnoHistoria(tr, CFase.REG_FIRMAR, null))
                        && tr.getIdFase().equals(CFase.CAL_CALIFICACION)) {
                } else if (this.hasFaseInTurnoHistoria(tr, CFase.REG_MESA_CONTROL, null)) {
                    throw new JDOException("El turno ya supero la fase MESA_CONTROL y no es posible anularlo.");
                }
                /**
                 * @author: Fernando Padilla
                 * @change: Mantis 2096: Acta - Requerimiento No 069 -
                 * Anulacion_Turnos_Exentos, por lo cual se deshabilita la
                 * validaci?n de exitencia de actos de tipo exento en el turno
                 * de registro.
                 *
                 *
                 */
                /*SolicitudRegistroEnhanced solReg = (SolicitudRegistroEnhanced) tr.getSolicitud();
            	  List liquidaciones = solReg.getLiquidaciones();
            	  Iterator itliquidaciones = liquidaciones.iterator();
    	  while (itliquidaciones.hasNext()) {
    	LiquidacionTurnoRegistroEnhanced lTReg  = (LiquidacionTurnoRegistroEnhanced) itliquidaciones.next();
    	List actos = lTReg.getActos();
                	Iterator itactos = actos.iterator();
                	while (itactos.hasNext()) {
                	ActoEnhanced acto  = (ActoEnhanced) itactos.next();
                	TipoActoDerechoRegistralEnhanced tadr = (TipoActoDerechoRegistralEnhanced) acto.getTipoDerechoReg();
                	TipoDerechoRegEnhanced tdr = (TipoDerechoRegEnhanced) tadr.getTipoDerechoRegistral();
                	if(tdr.getIdTipoDerechoReg().equals(CTipoTarifa.IMPUESTO_EXENTO)){
                    	throw new JDOException ("El turno tiene una Acto de tipo EXENTO.");
                    	}
                	}
    	}*/
            } else if (idProceso == Long.parseLong(CProceso.PROCESO_CORRECCION)) {
                if (this.hasFaseInTurnoHistoria(tr, CFase.COR_REVISION_ANALISIS, null)) {
                    throw new JDOException("El turno ya supero la fase REVISION Y ANALISIS y no es posible anularlo.");
                }
            } else if (idProceso == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                if (this.hasFaseInTurnoHistoria(tr, CFase.CER_ENTREGAR, null)) {
                    throw new JDOException("El turno ya supero la fase ENTREGA y no es posible anularlo.");
                }
                SolicitudCertificadoEnhanced solCert = (SolicitudCertificadoEnhanced) tr.getSolicitud();
                LiquidacionTurnoCertificadoEnhanced liq = (LiquidacionTurnoCertificadoEnhanced) solCert.getLiquidaciones().get(0);
                if (liq.getTipoTarifa().equals(CTipoTarifa.EXENTO)) {
                    throw new JDOException("El turno tiene una liquidacion tipo EXENTO.");
                }

            } else if (idProceso == Long.parseLong(CProceso.PROCESO_CERTIFICADOS_MASIVOS)) {
                if (this.hasFaseInTurnoHistoria(tr, CFase.CER_CERTIFICADOS_MASIVOS, null)) {
                    throw new JDOException("El turno ya supero la fase ENTREGA CERTIFICADOS MASIVOS y no es posible anularlo.");
                }
                if (this.hasFaseInTurnoHistoria(tr, CFase.CER_CERTIFICADOS_MASIVOS_2, null)) {
                    throw new JDOException("El turno ya supero la fase ENTREGA CERTIFICADOS MASIVOS INTERNA y no es posible anularlo.");
                }
                if (this.hasFaseInTurnoHistoria(tr, CFase.CER_CERTIFICADOS_MASIVOS_3, null)) {
                    throw new JDOException("El turno ya supero la fase ENTREGA CERTIFICADOS MASIVOS SUPERDOC  y no es posible anularlo.");
                }

                SolicitudCertificadoMasivoEnhanced solCertMasivo = (SolicitudCertificadoMasivoEnhanced) tr.getSolicitud();
                LiquidacionTurnoCertificadoMasivoEnhanced liqMasivo = (LiquidacionTurnoCertificadoMasivoEnhanced) solCertMasivo.getLiquidaciones().get(0);
                if (liqMasivo.getTipoTarifa().equals(CTipoTarifa.EXENTO)) {
                    throw new JDOException("El turno tiene una liquidacion tipo EXENTO.");
                }

            } else if (idProceso == Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                if (this.hasFaseInTurnoHistoria(tr, CFase.DEV_ANALISIS, null)) {
                    throw new JDOException("El turno ya supero la fase ANALISIS y no es posible anularlo.");
                }
            } else if (idProceso == Long.parseLong(CProceso.PROCESO_CONSULTAS)) {
                SolicitudConsultaEnhanced solCon = (SolicitudConsultaEnhanced) tr.getSolicitud();
                String idTipoConsulta = solCon.getTipoConsulta().getIdTipoConsulta();
                if (idTipoConsulta.equals(TipoConsulta.TIPO_EXENTO)) {
                    throw new JDOException("El turno es de tipo exento.");
                }
                if (solCon.getLastIdBusqueda() > 0) {
                    throw new JDOException("El turno ya fue utilizado para realizar una Busqueda.");
                }

                if (this.hasFaseInTurnoHistoria(tr, CFase.CON_ENTREGAR_COMPLEJA, null) || this.hasFaseInTurnoHistoria(tr, CFase.CON_ENTREGAR_SIMPLE, null)) {
                    throw new JDOException("El turno ya supero la fase ENTREGA y no es posible anularlo.");
                }

            } else if (idProceso == Long.parseLong(CProceso.PROCESO_FOTOCOPIAS)) {
                if (this.hasFaseInTurnoHistoria(tr, CFase.FOT_LIQUIDACION, null)) {
                    throw new JDOException("El turno ya supero la fase LIQUIDACION y no es posible anularlo.");
                }
            } else if (idProceso == Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {
                if (this.hasFaseInTurnoHistoria(tr, CFase.REP_REPARTO, null)) {
                    throw new JDOException("El turno ya supero la fase REPARTO y no es posible anularlo.");
                }
            } 
             
             
            //--- FIN VALIDAR TURNO QUE SE VA ANULAR --//

            resultado = true;

        } catch (JDOException e) {

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {

            throw (e);
        }

        return resultado;
    }

    /**
     * Actualizar la informacion de un turno.
     * <p>
     * En particular actualiza los atributos del turno historia, la fecha de
     * finalizaci?n del turno (si aplica) y el identificador de la fase.
     *
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void anularTurno(Turno turno) throws DAOException {
        this.anularTurno(turno, null);
    }

    /**
     * Actualizar la informacion de un turno.
     * <p>
     * En particular actualiza los atributos del turno historia, la fecha de
     * finalizaci?n del turno (si aplica) y el identificador de la fase.
     *
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void anularTurno(Turno turno, PersistenceManager npm) throws DAOException {
        PersistenceManager pm;
        if (npm == null) {
            pm = AdministradorPM.getPM();
        } else {
            pm = npm;
        }

        TurnoEnhanced t = TurnoEnhanced.enhance(turno);
        //JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {
            if (npm == null) {
                pm.currentTransaction().begin();
            }

            TurnoEnhancedPk tId = new TurnoEnhancedPk();
            tId.anio = t.getAnio();
            tId.idCirculo = t.getIdCirculo();
            tId.idProceso = t.getIdProceso();
            tId.idTurno = t.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(tId, pm);

            long idProceso = tr.getIdProceso();

            //--- ANULACION TURNO ---//
            if (t.getObservacionesAnulacion() != null) {
                tr.setObservacionesAnulacion(t.getObservacionesAnulacion());
            }
            tr.setAnulado(CTurno.TURNO_ANULADO);
            tr.setFechaFin(new Date());

            //Actualizaci?n de la tabla Turno
            UsuarioEnhancedPk userID = new UsuarioEnhancedPk();
            userID.idUsuario = t.getUsuarioAnulacion().getIdUsuario();
            UsuarioEnhanced user = this.getUsuarioByID(userID, pm);

            // Se actualiza el usuario anulador del Turno
            if (user != null) {
                tr.setUsuarioAnulacion(user);
            } else {
                throw new JDOException();
            }
            //---FIN ANULACION TURNO ---//

            //------SE DESBLOQUEAR TODOS LOS FOLIO ASOCIADOS AL TURNO --------------//
            //Se obtiene la lista de folios asociados al turno
            List folios = turno.getSolicitud().getSolicitudFolios();
            SolicitudFolio sf;
            Hashtable ht = new Hashtable();
            BloqueoFolioEnhanced bloqueo = null;

            //Se recorre la lista de matr?culas, se hacen las validaciones para cada una
            for (Iterator itr = folios.iterator(); itr.hasNext();) {
                sf = (SolicitudFolio) itr.next();
                String matricula = sf.getIdMatricula();

                //Se valida que la matr?cula se encuentre bloqueada y se obtiene
                //el objeto bloqueoFolio
                bloqueo = this.getBloqueoFolio(matricula, pm);

                if (bloqueo != null) {
                    if (bloqueo.getIdWorkflowBloqueo().equals(turno.getIdWorkflow())) {
                        bloqueo.setFechaDesbloqueo(new Date());
                    }
                }
            }
            // ----------FIN DESBLOQUEAR TODOS LOS FOLIO ASOCIADOS AL TURNO--------------//

            //-----SE ACTUALIZAN LOS PAGOS ASOCIADOS-----//
            this.updatePagoPorAnularTurno(tr, pm);
            this.updateConservacion(tr, 1);
           // this.liberarPagos(tr.getIdWorkflow());
            //-----FIN ACTUALIZAN LOS PAGOS ASOCIADOS-----//

            //Se debe anular la minuta del Turno de Reparto Notarial
            /**
             * @autor HGOMEZ @mantis 13176
             * @Requerimiento 061_453_Requerimiento_Auditoria
             * @descripcion Se audita minutas anuladas.
             */
            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
            MinutaEnhanced minutaNew = null;
            if (idProceso == Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {
                SolicitudRepartoNotarialEnhanced solReparto = (SolicitudRepartoNotarialEnhanced) tr.getSolicitud();

                MinutaEnhancedAnteriorSingletonUtil minutaEnhancedAnteriorSingletonUtil = MinutaEnhancedAnteriorSingletonUtil.getInstance();
                minutaEnhancedAnteriorSingletonUtil.setMinutaEnhanced((MinutaEnhanced) solReparto.getMinuta().clone());
                MinutaEnhanced minutaAnterior = minutaEnhancedAnteriorSingletonUtil.getMinutaEnhanced();

                MinutaEnhanced minutaE = solReparto.getMinuta();
                minutaE.setEstado(CReparto.MINUTA_ANULADA);

                MinutaEnhancedNewSingletonUtil minutaEnhancedNewSingletonUtil = MinutaEnhancedNewSingletonUtil.getInstance();
                minutaEnhancedNewSingletonUtil.setMinutaEnhanced(solReparto.getMinuta());
                minutaNew = minutaEnhancedNewSingletonUtil.getMinutaEnhanced();

                try {

                    if (infoUsuario != null) {
                        infoUsuario.put("idTurno", minutaNew.getIdMinuta());
                        auditoria.guardarDatosTerminal(infoUsuario);
                        auditoria.guardarAuditoriaMinuta(minutaNew, minutaAnterior, minutaNew.getIdMinuta());
                    }

                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                if (minutaNew != null) {
                    auditoria.borrarDatosTerminal(minutaNew.getIdMinuta());
                }
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (idProceso != Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)) {
                //----- ANULAR TURNOS HIJOS ------//
                /*
            	SolicitudEnhanced sol = tr.getSolicitud();
	            List solHijas = sol.getSolicitudesHijas();
	            Iterator it2 = solHijas.iterator();
	            while(it2.hasNext()){
	            	SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) it2.next();
	            	SolicitudEnhanced solHija = solAsoc.getSolicitudHija();
	            	TurnoEnhanced turnoHijo = this.getTurnoBySolicitud(solHija, pm);
	            	turnoHijo = this.getTurnoByWFId(turnoHijo.getIdWorkflow(), pm);
	            	//turnoHijo.setSolicitud(solHija);
	            	pm.makeTransient(turnoHijo.getSolicitud());
	            	pm.makeTransient(turnoHijo);
	            	Turno thijo = (Turno) turnoHijo.toTransferObject();

	            	//Settear el usuario y observacion de anulacion al turno a anular.
	            	UsuarioEnhanced uhijotr = tr.getUsuarioAnulacion();
	            	pm.makeTransient(uhijotr);
	            	Usuario uhijo = (Usuario) uhijotr.toTransferObject();
	            	thijo.setUsuarioAnulacion(uhijo);
	            	thijo.setObservacionesAnulacion(tr.getObservacionesAnulacion());

	            	this.anularTurno(thijo, pm);
	            }*/
                //----- FIN ANULAR TURNOS HIJOS ------//
            } else {
                //----- LIBERAR A LOS TURNOS ASOCIADOS ------//

                //----- FIN LIBERAR A LOS TURNOS ASOCIADOS ------//
            }

            //------ SE ACTUALIZA EL TURNO HISTORIA------//
            TurnoHistoriaEnhancedPk lastThId = new TurnoHistoriaEnhancedPk();
            lastThId.anio = t.getAnio();
            lastThId.idCirculo = t.getIdCirculo();
            lastThId.idProceso = t.getIdProceso();
            lastThId.idTurno = t.getIdTurno();
            lastThId.idTurnoHistoria = String.valueOf(t.getLastIdHistoria());

            TurnoHistoriaEnhanced lastTh = this.getTurnoHistoriaByID(lastThId, pm);

            TurnoHistoriaEnhanced turnoHistoria = new TurnoHistoriaEnhanced();

            if (lastTh != null) {
                turnoHistoria.setFaseAnterior(lastTh.getFase());
                turnoHistoria.setFase("ANULADO");
                turnoHistoria.setIdCirculo(lastTh.getIdCirculo());
                turnoHistoria.setAnio(lastTh.getAnio());
                turnoHistoria.setIdProceso(lastTh.getIdProceso());
                turnoHistoria.setIdTurno(lastTh.getIdTurno());
                turnoHistoria.setFecha(new Date());
                turnoHistoria.setNumeroCopiasReimpresion(0);
                turnoHistoria.setUsuarioAtiende(user);
                turnoHistoria.setUsuario(lastTh.getUsuario());
                turnoHistoria.setActivo(true);
                turnoHistoria.setIdTurnoHistoria(String.valueOf(turno.getLastIdHistoria() + 1));
                tr.setLastIdHistoria(tr.getLastIdHistoria() + 1);
                //lastTh.setUsuarioAtiende(user);
                lastTh.setActivo(false);

            }
            tr.addHistorial(turnoHistoria);
            //------ FIN ACTUALIZA EL TURNO HISTORIA------//

            //------INICIO ACTUALIZAR TURNO EN EJECUCION -----//
            TurnoEjecucionEnhancedPk TeID = new TurnoEjecucionEnhancedPk();
            TeID.idWorkflow = t.getIdWorkflow();

            TurnoEjecucionEnhanced turnoEjecucion = this.getTurnoEjecucionByID(TeID, pm);

            turnoEjecucion.setEstacion(CFase.FINALIZADO);
            turnoEjecucion.setFase(CFase.FINALIZADO);
            turnoEjecucion.setEstado(CTurno.EJECUCION_TERMINADA);
            turnoEjecucion.setHasWF(CTurno.EJECUCION_FINALIZADA);
            //-------FIN ACTUALIZAR TURNO EN EJECUCION -------//

            if (npm == null) {
                pm.currentTransaction().commit();
            }

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw new DAOException(e.getMessage(), e);
        } finally {
            if (npm == null) {
                pm.close();
            }
        }

        try {
            //--- SE DEBE SUSPENDER EN WF ----------//
            Hashtable parametros = new Hashtable();
            Processor p = HermodWFFactory.getFactory().getProcessor();
            long idParam = 0;
            String tipoMensajeParam = Message.AL_WORKFLOW;
            String tipoProcesoParam = Message.ANULAR_PROCESO;
            parametros.put(Processor.ITEM_KEY, turno.getIdWorkflow());
            //parametros.put(Processor.ACTIVITY, activity);
            //parametros.put(Processor.COMMAND, Message.SKIP);
            //parametros.put(Processor.RESULT, result);
            Message m = new Message(idParam, tipoMensajeParam, tipoProcesoParam, parametros);
            p.process(m);
            //--------------------------------------//
        } catch (WFException e) {
            if (!pm.isClosed()) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }
            }
            //throw new DAOException(e.getMessage(), e);
        }
    }

    /*
    *  @fecha 21/02/2013
    *  @author Carlos Torres
    *  @chage  En particular actualiza el historial del turno 
    *  @mantis 0014376
     */
    /**
     * Actualizar el historial del turno.
     * <p>
     * En particular actualiza el historial del turno
     *
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void addFaseRestitucionTurno(Turno turno, Usuario usuario) throws DAOException {
        PersistenceManager pm;

        pm = AdministradorPM.getPM();

        TurnoEnhanced t = TurnoEnhanced.enhance(turno);
        //JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {

            pm.currentTransaction().begin();

            TurnoEnhancedPk tId = new TurnoEnhancedPk();
            tId.anio = t.getAnio();
            tId.idCirculo = t.getIdCirculo();
            tId.idProceso = t.getIdProceso();
            tId.idTurno = t.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(tId, pm);

            long idProceso = tr.getIdProceso();

            //--- ANULACION TURNO ---//
            if (t.getObservacionesAnulacion() != null) {
                tr.setObservacionesAnulacion(t.getObservacionesAnulacion());
            }

            //Actualizaci?n de la tabla Turno
            UsuarioEnhancedPk userID = new UsuarioEnhancedPk();
            userID.idUsuario = usuario.getIdUsuario();
            UsuarioEnhanced user = this.getUsuarioByID(userID, pm);

            //------ SE ACTUALIZA EL TURNO HISTORIA------//
            TurnoHistoriaEnhancedPk lastThId = new TurnoHistoriaEnhancedPk();
            lastThId.anio = t.getAnio();
            lastThId.idCirculo = t.getIdCirculo();
            lastThId.idProceso = t.getIdProceso();
            lastThId.idTurno = t.getIdTurno();
            lastThId.idTurnoHistoria = String.valueOf(tr.getLastIdHistoria());

            TurnoHistoriaEnhanced lastTh = this.getTurnoHistoriaByID(lastThId, pm);

            TurnoHistoriaEnhanced turnoHistoria = new TurnoHistoriaEnhanced();

            if (lastTh != null) {
                turnoHistoria.setFaseAnterior(lastTh.getFase());
                turnoHistoria.setFase("RESTITUCION");
                turnoHistoria.setIdCirculo(lastTh.getIdCirculo());
                turnoHistoria.setAnio(lastTh.getAnio());
                turnoHistoria.setIdProceso(lastTh.getIdProceso());
                turnoHistoria.setIdTurno(lastTh.getIdTurno());
                turnoHistoria.setFecha(new Date());
                turnoHistoria.setNumeroCopiasReimpresion(0);
                turnoHistoria.setUsuarioAtiende(user);
                turnoHistoria.setUsuario(lastTh.getUsuario());
                turnoHistoria.setActivo(true);
                turnoHistoria.setIdTurnoHistoria(String.valueOf(tr.getLastIdHistoria() + 1));
                tr.setLastIdHistoria(tr.getLastIdHistoria() + 1);
                lastTh.setActivo(false);
                lastTh.setUsuarioAtiende(null);
            }
            tr.addHistorial(turnoHistoria);
            //------ FIN ACTUALIZA EL TURNO HISTORIA------//

            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw new DAOException(e.getMessage(), e);
        } finally {

            pm.close();

        }

        try {
            //--- SE DEBE SUSPENDER EN WF ----------//
            Hashtable parametros = new Hashtable();
            Processor p = HermodWFFactory.getFactory().getProcessor();
            long idParam = 0;
            String tipoMensajeParam = Message.AL_WORKFLOW;
            String tipoProcesoParam = Message.ANULAR_PROCESO;
            parametros.put(Processor.ITEM_KEY, turno.getIdWorkflow());
            //parametros.put(Processor.ACTIVITY, activity);
            //parametros.put(Processor.COMMAND, Message.SKIP);
            //parametros.put(Processor.RESULT, result);
            Message m = new Message(idParam, tipoMensajeParam, tipoProcesoParam, parametros);
            p.process(m);
            //--------------------------------------//
        } catch (WFException e) {
            if (!pm.isClosed()) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }
            }
            //throw new DAOException(e.getMessage(), e);
        }
    }

    protected void updatePagoPorAnularTurno(TurnoEnhanced tr, PersistenceManager pm) throws DAOException {
        
        for (Iterator iterLiquid = tr.getSolicitud().getLiquidaciones().iterator(); iterLiquid.hasNext();) {
            LiquidacionEnhanced liquidacion = (LiquidacionEnhanced) iterLiquid.next();

            for (Iterator iterApPagos = liquidacion.getPago().getAplicacionPagos().iterator(); iterApPagos.hasNext();) {
                AplicacionPagoEnhanced aplicacionPago = (AplicacionPagoEnhanced) iterApPagos.next();
                DocumentoPagoEnhanced dp = aplicacionPago.getDocumentoPago();
                
                
                
                if (dp instanceof DocPagoChequeEnhanced) {
                    DocPagoChequeEnhanced dpc = (DocPagoChequeEnhanced) dp;
                    double valorUsado = aplicacionPago.getValorAplicado();
                    double saldo = dpc.getSaldoDocumento();
                    dpc.setSaldoDocumentoAnulacion(saldo);
                    saldo = saldo + valorUsado;
                    /**
                     * @Autor: Santiago V?squez
                     * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS
                     * PAGO
                     */
                    aplicacionPago.setValorAplicadoAnulacion(aplicacionPago.getValorAplicado());
                    aplicacionPago.setValorAplicado(0);
                    dpc.setSaldoDocumento(saldo);
                
                    dp = dpc;
                }
                if (dp instanceof DocPagoConsignacionEnhanced) {
                    DocPagoConsignacionEnhanced dpc = (DocPagoConsignacionEnhanced) dp;
                    double valorUsado = aplicacionPago.getValorAplicado();
                    double saldo = dpc.getSaldoDocumento();
                    dpc.setSaldoDocumentoAnulacion(saldo);
                    
                    saldo = saldo + valorUsado;
                    /**
                     * @Autor: Santiago V?squez
                     * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS
                     * PAGO
                     */
                    aplicacionPago.setValorAplicadoAnulacion(aplicacionPago.getValorAplicado());
                    aplicacionPago.setValorAplicado(0);
                    dpc.setSaldoDocumento(saldo);
                    //liberar pagos
                    dp.setConsignacion_ant(dpc.getNoConsignacion());//guardar el anterior
                    dpc.setNoConsignacion(null);//blanquear el actual
                    
                    dp = dpc;
                }
                
                if(dp instanceof DocPagoGeneralEnhanced)
                {
                    DocPagoGeneralEnhanced dpc = (DocPagoGeneralEnhanced) dp;
                    if(dpc.getNoConsignacion()!= null ){
                        
                        dp.setConsignacion_ant(dpc.getNoConsignacion());//guardar el anterior
                        dpc.setNoConsignacion(null);//blanquear el actual
                    }
                    if(dpc.getNoAprobacion()!= null){
                        dp.setAprobacion_ant(dpc.getNoAprobacion());
                        dp.setTarjeta_ant(dpc.getNoDocumento());
                        dpc.setNoAprobacion(null);
                        dpc.setNoDocumento(null);
                    }
                    
                }
                
                //aplicacionPago.setDocumentoPago(dp);
            }
        }
    }
    
    public void liberarPagos(String idTurno) throws DAOException
    {
        
        double rta = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        
        VersantPersistenceManager jdoPM = null;
        
        String update = "UPDATE SIR_OP_DOCUMENTO_PAGO SET DCPG_ANT_NO_CONSIGNACION = DCPG_NO_CONSIGNACION, DCPG_ANT_NO_TARJETA=DCPG_NO_TARJETA, DCPG_ANT_NO_APROBACION=DCPG_NO_APROBACION, DCPG_NO_CONSIGNACION=null, DCPG_NO_TARJETA=null, DCPG_NO_APROBACION=null \n" +
        "WHERE ID_DOCUMENTO_PAGO = ?";
        
        String select = " SELECT SIR_OP_DOCUMENTO_PAGO.ID_DOCUMENTO_PAGO " +
        "FROM  SIR_OP_TURNO," +
        "	SIR_OP_SOLICITUD," +
        "	SIR_OP_LIQUIDACION," +
        "	SIR_OP_PAGO," +
        "	SIR_OP_APLICACION_PAGO," +
        "	SIR_OP_DOCUMENTO_PAGO" +
        " WHERE SIR_OP_DOCUMENTO_PAGO.id_documento_pago = SIR_OP_APLICACION_PAGO.id_documento_pago " +
        " AND SIR_OP_PAGO.id_liquidacion              = SIR_OP_APLICACION_PAGO.id_liquidacion " +
        " AND SIR_OP_PAGO.id_solicitud                = SIR_OP_APLICACION_PAGO.id_solicitud " +
        " AND SIR_OP_LIQUIDACION.id_liquidacion       = SIR_OP_PAGO.id_liquidacion(+) " +
        " AND SIR_OP_LIQUIDACION.id_solicitud         = SIR_OP_PAGO.id_solicitud(+) " +
        " AND SIR_OP_SOLICITUD.id_solicitud           = SIR_OP_LIQUIDACION.id_solicitud(+) " +
        " AND SIR_OP_SOLICITUD.id_solicitud           = SIR_OP_TURNO.id_solicitud " +
        " AND SIR_OP_TURNO.TRNO_ID_WORKFLOW=?";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(select);
            ps.setString(1, idTurno);
            
            ResultSet r = ps.executeQuery();
            
            while(r.next())
              {
                 
                  
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
        
    }
    
    /**
     * Actualizar la informacion de un turno.
     * <p>
     * En particular actualiza los atributos del turno historia, la fecha de
     * finalizaci?n del turno (si aplica) y el identificador de la fase.
     *
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void habilitarTurno(Turno turno) throws DAOException {
        this.habilitarTurno(turno, null);
    }

    /**
     * Actualizar la informacion de un turno.
     * <p>
     * En particular actualiza los atributos del turno historia, la fecha de
     * finalizaci?n del turno (si aplica) y el identificador de la fase.
     *
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void habilitarTurno(Turno turno, PersistenceManager npm) throws DAOException {
        PersistenceManager pm;
        if (npm == null) {
            pm = AdministradorPM.getPM();
        } else {
            pm = npm;
        }

        TurnoEnhanced t = TurnoEnhanced.enhance(turno);
        //JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {
            if (npm == null) {
                pm.currentTransaction().begin();
            }

            TurnoEnhancedPk tId = new TurnoEnhancedPk();
            tId.anio = t.getAnio();
            tId.idCirculo = t.getIdCirculo();
            tId.idProceso = t.getIdProceso();
            tId.idTurno = t.getIdTurno();

            TurnoEnhanced tr = this.getTurnoByID(tId, pm);

            long idProceso = tr.getIdProceso();

            //--- HABILITAR TURNO ---//
            if (t.getObservacionesHabilitar() != null) {
                tr.setObservacionesHabilitar(t.getObservacionesHabilitar());
            }
            tr.setAnulado(CTurno.TURNO_NO_ANULADO);
            tr.setFechaFin(null);

            //Actualizaci?n de la tabla Turno
            UsuarioEnhancedPk userID = new UsuarioEnhancedPk();
            userID.idUsuario = t.getUsuarioAnulacion().getIdUsuario();
            UsuarioEnhanced user = this.getUsuarioByID(userID, pm);

            // Se actualiza el usuario anulador del Turno
            if (user != null) {
                tr.setUsuarioAnulacion(null);
            } else {
                throw new JDOException();
            }
            //---FIN ANULACION TURNO ---//

            //-----SE ACTUALIZAN LOS PAGOS ASOCIADOS-----//
            this.updatePagoPorHabilitarTurno(tr, pm);
            this.updateConservacion(tr, 0);
            //-----FIN ACTUALIZAN LOS PAGOS ASOCIADOS-----//

            //Se debe habilitar la minuta del Turno de Reparto Notarial
            if (idProceso == Long.parseLong(CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS)) {
                SolicitudRepartoNotarialEnhanced solReparto = (SolicitudRepartoNotarialEnhanced) tr.getSolicitud();
                MinutaEnhanced minutaE = solReparto.getMinuta();
                minutaE.setEstado(CReparto.MINUTA_NO_REPARTIDA);
            }

            //------ SE ACTUALIZA EL TURNO HISTORIA------//
            TurnoHistoriaEnhancedPk lastThIdValido = new TurnoHistoriaEnhancedPk();
            lastThIdValido.anio = t.getAnio();
            lastThIdValido.idCirculo = t.getIdCirculo();
            lastThIdValido.idProceso = t.getIdProceso();
            lastThIdValido.idTurno = t.getIdTurno();
            lastThIdValido.idTurnoHistoria = String.valueOf(t.getLastIdHistoria() - 1);

            TurnoHistoriaEnhanced lastThValido = this.getTurnoHistoriaByID(lastThIdValido, pm);

            //ELORA: Mantis: 0014376
            lastThIdValido.idTurnoHistoria = String.valueOf(t.getLastIdHistoria());
            TurnoHistoriaEnhanced thAnulado = this.getTurnoHistoriaByID(lastThIdValido, pm);

            TurnoHistoriaEnhancedPk lastThId = new TurnoHistoriaEnhancedPk();
            lastThId.anio = t.getAnio();
            lastThId.idCirculo = t.getIdCirculo();
            lastThId.idProceso = t.getIdProceso();
            lastThId.idTurno = t.getIdTurno();
            lastThId.idTurnoHistoria = String.valueOf(t.getLastIdHistoria());

            TurnoHistoriaEnhanced lastTh = this.getTurnoHistoriaByID(lastThId, pm);

            TurnoHistoriaEnhanced turnoHistoria = new TurnoHistoriaEnhanced();
            //ELORA: Mantis: 0014376
            //------ SE TURNO HISTORIA HABILITAR---------//
            TurnoHistoriaEnhanced thHabilitar = new TurnoHistoriaEnhanced();
            if (lastTh != null) {
                thHabilitar.setFaseAnterior(thAnulado.getFase());
                thHabilitar.setFase("HABILITADO");
                thHabilitar.setIdCirculo(lastTh.getIdCirculo());
                thHabilitar.setAnio(lastTh.getAnio());
                thHabilitar.setIdProceso(lastTh.getIdProceso());
                thHabilitar.setIdTurno(lastTh.getIdTurno());
                thHabilitar.setFecha(new Date());
                thHabilitar.setNumeroCopiasReimpresion(0);
                thHabilitar.setUsuario(lastTh.getUsuario());
                thHabilitar.setUsuarioAtiende(user);
                thHabilitar.setIdTurnoHistoria(String.valueOf(turno.getLastIdHistoria() + 1));
                thHabilitar.setIdAdministradorSAS(lastThValido.getIdAdministradorSAS());
                lastTh.setActivo(false);
                tr.addHistorial(thHabilitar);

                turnoHistoria.setFaseAnterior(lastTh.getFase());
                turnoHistoria.setFase(lastThValido.getFase());
                turnoHistoria.setIdCirculo(lastTh.getIdCirculo());
                turnoHistoria.setAnio(lastTh.getAnio());
                turnoHistoria.setIdProceso(lastTh.getIdProceso());
                turnoHistoria.setIdTurno(lastTh.getIdTurno());
                turnoHistoria.setFecha(new Date());
                turnoHistoria.setNumeroCopiasReimpresion(0);
                turnoHistoria.setUsuario(lastTh.getUsuario());
                turnoHistoria.setActivo(true);
                turnoHistoria.setIdTurnoHistoria(String.valueOf(turno.getLastIdHistoria() + 2));
                turnoHistoria.setIdAdministradorSAS(lastThValido.getIdAdministradorSAS());
                tr.setLastIdHistoria(tr.getLastIdHistoria() + 2);
                //lastTh.setUsuarioAtiende(user);
                lastTh.setActivo(false);
                tr.addHistorial(turnoHistoria);

            }
            //------ FIN ACTUALIZA EL TURNO HISTORIA------//

            TurnoEjecucionEnhancedPk TeID = new TurnoEjecucionEnhancedPk();
            TeID.idWorkflow = t.getIdWorkflow();

            TurnoEjecucionEnhanced turnoEjecucion = this.getTurnoEjecucionByID(TeID, pm);

            turnoEjecucion.setEstacion(lastThValido.getIdAdministradorSAS());
            turnoEjecucion.setFase(lastThValido.getFase());
            turnoEjecucion.setEstado(CTurno.EJECUCION_HABILITADA);
            turnoEjecucion.setHasWF(CTurno.EJECUCION_ACTIVA);

            if (npm == null) {
                pm.currentTransaction().commit();
            }

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw new DAOException(e.getMessage(), e);
        } finally {
            if (npm == null) {
                pm.close();
            }
        }

        try {
            //--- SE DEBE HABILITAR EN WF ----------//
            Hashtable parametros = new Hashtable();
            Processor p = HermodWFFactory.getFactory().getProcessor();
            long idParam = 0;
            String tipoMensajeParam = Message.AL_WORKFLOW;
            String tipoProcesoParam = Message.REVIVIR_PROCESO;
            parametros.put(Processor.ITEM_KEY, turno.getIdWorkflow());
            //parametros.put(Processor.ACTIVITY, activity);
            //parametros.put(Processor.COMMAND, Message.SKIP);
            //parametros.put(Processor.RESULT, result);
            Message m = new Message(idParam, tipoMensajeParam, tipoProcesoParam, parametros);
            p.process(m);
            //--------------------------------------//
        } catch (WFException e) {
            if (!pm.isClosed()) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }
            }
            //throw new DAOException(e.getMessage(), e);
        }
    }

    protected void updatePagoPorHabilitarTurno(TurnoEnhanced tr, PersistenceManager pm) throws DAOException {
        
        for (Iterator iterLiquid = tr.getSolicitud().getLiquidaciones().iterator(); iterLiquid.hasNext();) {
            LiquidacionEnhanced liquidacion = (LiquidacionEnhanced) iterLiquid.next();

            for (Iterator iterApPagos = liquidacion.getPago().getAplicacionPagos().iterator(); iterApPagos.hasNext();) {
                AplicacionPagoEnhanced aplicacionPago = (AplicacionPagoEnhanced) iterApPagos.next();
                DocumentoPagoEnhanced dp = aplicacionPago.getDocumentoPago();
                String turnos = this.getTurnosRadicadosConPago(tr.getIdWorkflow(), dp.getIdDocumentoPago(), pm);
                /*JAlcazar 11/09/2009 Caso MANTIS 0002358 se agrega en el if la siguiente validaci?n:
                     * tr.getSolicitud().getSolicitudesHijas()== null && tr.getSolicitud().getSolicitudesPadres() == null 
                     * debido a que los turnos de registro y sus hijos comparten el mismo recibo
                     * */
                if (turnos != null && !turnos.trim().equals("") && dp.getSaldoDocumento() < 1000
                        && dp.getSaldoDocumentoAnulacion() < 1000
                        && tr.getSolicitud().getSolicitudesHijas() == null && tr.getSolicitud().getSolicitudesPadres() == null) {
                    throw new DAOException("Existen consignaciones y/o cheques que se usaron en la radicacion de nuevos turnos:  " + turnos);
                }

                
                if(dp instanceof DocPagoGeneralEnhanced)
                {
                         
                            DocPagoGeneralEnhanced dpc = (DocPagoGeneralEnhanced) dp;
                        if(dp.getConsignacion_ant()!=null){
                            dpc.setNoConsignacion(dp.getConsignacion_ant());
                            dp.setConsignacion_ant(null);
                        }
                        if(dp.getAprobacion_ant()!=null){
                            dpc.setNoAprobacion(dp.getAprobacion_ant());
                            dpc.setNoDocumento(dp.getTarjeta_ant());
                            dp.setAprobacion_ant(null);
                            dp.setTarjeta_ant(null);

                        }
                }
               
                
                if (dp instanceof DocPagoChequeEnhanced) {
                    DocPagoChequeEnhanced dpc = (DocPagoChequeEnhanced) dp;
                    double valorUsadoAnt = dpc.getSaldoDocumento() - dpc.getSaldoDocumentoAnulacion();
                    aplicacionPago.setValorAplicado(valorUsadoAnt);
                    dpc.setSaldoDocumentoAnulacion(0);
                    dpc.setSaldoDocumento(dpc.getSaldoDocumentoAnulacion());

                    dp = dpc;
                }
                
                
                
                if (dp instanceof DocPagoConsignacionEnhanced) {
                    DocPagoConsignacionEnhanced dpc = (DocPagoConsignacionEnhanced) dp;
                    double valorUsadoAnt = dpc.getSaldoDocumento() - dpc.getSaldoDocumentoAnulacion();
                    aplicacionPago.setValorAplicado(valorUsadoAnt);
                    dpc.setSaldoDocumentoAnulacion(0);
                    dpc.setSaldoDocumento(dpc.getSaldoDocumentoAnulacion());
                    dpc.setNoConsignacion(dp.getConsignacion_ant());
                    dp.setConsignacion_ant(null);
                    dp = dpc;
                }
            
               
            
            }
        }
    }

    /**
     * Metodo que consulta si un turno ya paso en una fase consultando su turno
     * historia.
     *
     * @param tr
     * @param fase
     * @param respuesta
     * @return true Si el turno ya paso por esa fase con la respuesta dada en
     * turno historia false Si el turno no ha pasado por esa fase. O se
     * encuentra en fase sin dar respuesta todavia
     * @throws DAOException
     */
    protected boolean hasFaseInTurnoHistoria(TurnoEnhanced tr, String fase, String respuesta) throws DAOException {
        boolean esta = false;
        //Buscar en el turno historia si el turno estuvo en esa fase.
        List turnoHistoria = tr.getHistorials();
        Iterator it = turnoHistoria.iterator();
        while (it.hasNext()) {
            TurnoHistoriaEnhanced th = (TurnoHistoriaEnhanced) it.next();
            /*if(th.getFase().equals(fase))	{
    	if(th.getRespuesta()== null || th.getRespuesta().equals("")){
	    	esta = false;
    	}else{
    	if(respuesta == null){
	    	esta = true;
	    	}else{
	    	if(th.getRespuesta().equals(respuesta)){
	    	esta = true;
	    	}
	    	}
    	}
    	}else{*/
            //Caso especial si esta como fase anterior tambien se dice que paso por ahi
            if (th.getFaseAnterior() != null && th.getFaseAnterior().equals(fase)) {
                esta = true;
            }
            //}
        }
        return esta;

    }

    /**
     * Retorna el bloqueo de la matricula en caso que est? bloqueada, si no est?
     * bloqueada retorna null
     *
     * @param matricula
     * @param pm
     * @return
     * @throws DAOException
     */
    protected BloqueoFolioEnhanced getBloqueoFolio(String matricula,
            PersistenceManager pm) throws DAOException {
        BloqueoFolioEnhanced rta = null;

        if (matricula != null) {
            try {
                Query query = pm.newQuery(BloqueoFolioEnhanced.class);
                query.declareParameters("String matricula");
                query.setFilter(
                        "this.idMatricula==matricula && this.fechaDesbloqueo==null");

                Collection col = (Collection) query.execute(matricula);
                Iterator iter = col.iterator();

                if (iter.hasNext()) {
                    rta = (BloqueoFolioEnhanced) iter.next();
                }
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene una lista de objetos <code>Turno</code> que estan en el
     * <code>Proceso</code> la <code>Fase</code> y el <code>Circulo</code>
     * ingresado
     *
     * @param proceso <code>Proceso</code>
     * @param fase <code>Fase</code>
     * @param circulo <code>Circulo</code>
     * @return una lista de objetos <code>Turno</code>.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Proceso
     * @see gov.sir.core.negocio.modelo.Fase
     * @see gov.sir.core.negocio.modelo.Circulo
     */
    public List getTurnosFase(Proceso proceso, Fase fase, Circulo circulo)
            throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().begin();
            Query q = pm.newQuery(TurnoEnhanced.class);
            q.setFilter("idFase=='" + fase.getID() + "' && idCirculo=='"
                    + circulo.getIdCirculo() + "' && idProceso=="
                    + proceso.getIdProceso());

            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();

            while (it.hasNext()) {
                TurnoEnhanced obj = (TurnoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            q.close(results);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            //pm.close();
            lista = TransferUtils.makeTransferAll(lista);
        }

        return lista;
    }

    /**
     * Obtiene una lista de objetos <code>Turno</code> que estan sociados con el
     * <code>Folio</code> correspondiente a la matricula ingresada
     *
     * @param matricula <code>Matricula</code>
     * @return una lista de objetos <code>Turno</code>.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Folio
     */
    public List getTurnosByMatricula(String matricula)
            throws DAOException {
        List lista = new ArrayList();
        List listaSol = new ArrayList();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            listaSol = solicitudesDAO.getSolicitudesByMatricula(matricula, pm);

            //pm.currentTransaction().begin();
            Iterator it = listaSol.iterator();

            while (it.hasNext()) {
                SolicitudEnhanced sol = (SolicitudEnhanced) it.next();
                TurnoEnhanced turno = this.getTurnoBySolicitud(sol, pm);

                if (turno != null) {
                    pm.makeTransient(turno);
                    lista.add(turno);
                }
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        lista = TransferUtils.makeTransferAll(lista);

        return lista;
    }

    /**
     * Obtiene los turnos anteriores asociados con el turno con identificador
     * ingresado como par?metro.
     *
     * @param idTurno Identificador del turno del cual se consultar?n los turnos
     * anteriores.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    public List getTurnosAnteriores(String idTurno) throws DAOException {
        List listaTurnos = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            TurnoEnhanced turnoActual = this.getTurnoByWFId(idTurno, pm);
            TurnoEnhanced turnoAnterior;
            SolicitudEnhanced solActual;

            if (turnoActual == null) {
                throw new DAOException("El turno con el identificador "
                        + idTurno + "no existe");
            }

            boolean condicion = true;

            while (condicion) {
                solActual = turnoActual.getSolicitud();

                if (solActual != null) {
                    turnoAnterior = solActual.getTurnoAnterior();

                    if (turnoAnterior != null) {
                        pm.makeTransient(turnoAnterior);
                        listaTurnos.add(turnoAnterior);
                        turnoActual = this.getTurnoByWFId(turnoAnterior.getIdWorkflow(),
                                pm);
                    } else {
                        condicion = false;
                    }
                } else {
                    condicion = false;
                }
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        List listaTransfer = TransferUtils.makeTransferAll(listaTurnos);

        return listaTransfer;
    }

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como par?metro.
     *
     * @param idTurno Identificador del turno del cual se consultar?n los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    public List getTurnosSiguientes(String idTurno) throws DAOException {
        List listaTurnos = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            TurnoEnhanced turnoActual = this.getTurnoByWFId(idTurno, pm);

            if (turnoActual == null) {
                throw new DAOException("El turno con el identificador "
                        + idTurno + "no existe");
            }

            boolean condicion = true;

            while (condicion) {
                TurnoEnhanced turnoSiguiente = this.getTurnoSiguiente(turnoActual.getIdWorkflow(), turnoActual.getIdCirculo(),
                        pm);

                if (turnoSiguiente != null) {
                    pm.makeTransient(turnoSiguiente);
                    listaTurnos.add(turnoSiguiente);
                    turnoActual = this.getTurnoByWFId(turnoSiguiente.getIdWorkflow(),
                            pm);
                } else {
                    condicion = false;
                }
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        List listaTransfer = TransferUtils.makeTransferAll(listaTurnos);

        return listaTransfer;
    }

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como par?metro.
     *
     * @param idTurno Identificador del turno del cual se consultar?n los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    public List getTurnosSiguientesTestamento(String idTurno) throws DAOException {
        List listaTurnos = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            TurnoEnhanced turnoActual = this.getTurnoByWFId(idTurno, pm);

            if (turnoActual == null) {
                throw new DAOException("El turno con el identificador "
                        + idTurno + "no existe");
            }

            listaTurnos = this.getTurnoSiguienteTestamento(turnoActual.getIdWorkflow(), turnoActual.getIdCirculo(), pm);
            if (listaTurnos == null) {
                listaTurnos = new ArrayList();
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        List listaTransfer = TransferUtils.makeTransferAll(listaTurnos);

        return listaTransfer;
    }

    /**
     * Obtiene los turnos siguientes asociados con el turno con identificador
     * ingresado como par?metro.
     *
     * @param idTurno Identificador del turno del cual se consultar?n los turnos
     * siguientes.
     * @return lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    public List getTurnosSiguientesDevolucion(String idTurno) throws DAOException {
        List listaTurnos = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            TurnoEnhanced turnoActual = this.getTurnoByWFId(idTurno, pm);

            if (turnoActual == null) {
                throw new DAOException("El turno con el identificador "
                        + idTurno + "no existe");
            }

            boolean condicion = true;

            while (condicion) {
                TurnoEnhanced turnoSiguiente = this.getTurnoSiguienteDevolucion(turnoActual.getIdWorkflow(), turnoActual.getIdCirculo(),
                        pm);

                if (turnoSiguiente != null) {
                    pm.makeTransient(turnoSiguiente);
                    listaTurnos.add(turnoSiguiente);
                    turnoActual = this.getTurnoByWFId(turnoSiguiente.getIdWorkflow(),
                            pm);
                } else {
                    condicion = false;
                }
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        List listaTransfer = TransferUtils.makeTransferAll(listaTurnos);

        return listaTransfer;
    }

    /**
     * Obtiene (si existe) el <code>Turno </code> del cual un turno es
     * turnoAnterior
     *
     * @param idWork identificador de workflow del Turno.
     * @param pm PersistenceManager de la transaccion
     * @return Objeto <code>TurnoEnhanced</code> con sus atributos, del cual es
     * turno anterior el turno con idWorkflow pasado como par?metro.
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoSiguiente(String idWork, String idCirculo,
            PersistenceManager pm) throws DAOException {
        TurnoEnhanced turnoSiguiente = null;

        try {
            Query query = pm.newQuery(TurnoEnhanced.class);

            String idAnulado = CTurno.TURNO_ANULADO;

            //Se realiza la b?squeda para obtener el turno del cual el turno dado es turno anterior.
            query.declareParameters("String idWork, String idAnulado, String idCir");
            //query.setFilter("this.idCirculo==idCir &&\n" +
            query.setFilter("this.solicitud.turnoAnterior.anulado!=idAnulado &&\n"
                    + "this.solicitud.turnoAnterior.idWorkflow==idWork &&\n"
                    + "this.solicitud.turnoAnterior.idCirculo==idCir");
            /**
             * @author: GUILLERMO CABRERA 25-02-2010
             * @change: Se adiciona la linea 5795 para definir el ordenamiento
             * del query en forma descendente "de mayor a menor", para que en la
             * consulta traiga el turno siguiente mayor.
             * @request: No 051, caso mantis3980.
             *
             */

            query.setOrdering("anio descending, idCirculo descending, idProceso descending, idTurno descending");

            Collection col = (Collection) query.execute(idWork, idAnulado, idCirculo);

            Iterator it = col.iterator();

            //Se obtiene el primer elemento de la lista (si existe).
            //Un turno solo puede ser turno anterior de un solo turno.
            if (it.hasNext()) {
                turnoSiguiente = (TurnoEnhanced) it.next();
                pm.makeTransient(turnoSiguiente);
            }

            query.close(col);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            turnoSiguiente = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return turnoSiguiente;
    }

    /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        **/
    /**
     * Obtiene (si existe) el <code>Turno </code> del cual un turno es
     * turnoAnterior
     *
     * @param idWork identificador de workflow del Turno.
     * @param pm PersistenceManager de la transaccion
     * @return Objeto <code>TurnoEnhanced</code> con sus atributos, del cual es
     * turno anterior el turno con idWorkflow pasado como par?metro.
     * @throws DAOException
     */
    protected List getTurnoSiguienteTestamento(String idWork, String idCirculo,
            PersistenceManager pm) throws DAOException {
        TurnoEnhanced turnoSiguiente = null;
        List turnos = new ArrayList();

        try {
            Query query = pm.newQuery(TurnoEnhanced.class);

            String idAnulado = CTurno.TURNO_ANULADO;

            //Se realiza la b?squeda para obtener el turno del cual el turno dado es turno anterior.
            query.declareParameters("String idWork, String idAnulado, String idCir");
            //query.setFilter("this.idCirculo==idCir &&\n" +
            query.setFilter("this.solicitud.turnoAnterior.anulado!=idAnulado &&\n"
                    + "this.solicitud.turnoAnterior.idWorkflow==idWork &&\n"
                    + "this.solicitud.turnoAnterior.idCirculo==idCir");

            query.setOrdering("anio ascending, idCirculo ascending, idProceso ascending, idTurno ascending");

            Collection col = (Collection) query.execute(idWork, idAnulado, idCirculo);

            Iterator it = col.iterator();

            //Se obtiene el primer elemento de la lista (si existe).
            //Un turno solo puede ser turno anterior de un solo turno.
            while (it.hasNext()) {
                turnoSiguiente = (TurnoEnhanced) it.next();
                pm.makeTransient(turnoSiguiente);
                turnos.add(turnoSiguiente);
            }

            query.close(col);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            turnoSiguiente = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return turnos;
    }

    /**
     * Obtiene (si existe) el <code>Turno </code> del cual un turno es
     * turnoAnterior
     *
     * @param idWork identificador de workflow del Turno.
     * @param pm PersistenceManager de la transaccion
     * @return Objeto <code>TurnoEnhanced</code> con sus atributos, del cual es
     * turno anterior el turno con idWorkflow pasado como par?metro.
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoSiguienteDevolucion(String idWork, String idCirculo,
            PersistenceManager pm) throws DAOException {
        TurnoEnhanced turnoSiguiente = null;

        try {
            Query query = pm.newQuery(TurnoEnhanced.class);
            query.setOrdering("fechaInicio ascending");

            //Se realiza la b?squeda para obtener el turno del cual el turno dado es turno anterior.
            query.declareParameters("String idWork, String idCir");
            //query.setFilter("this.idCirculo==idCir &&\n" +
            /**
             * @author: Fernando Padilla Velez
             * @change: Caso Mantis 4275: error al ingresar nueva entrada, Se
             * modifica la consulta para que no obtenga los turnos anulados.
             *
             */
            query.setFilter("this.solicitud.turnoAnterior.idWorkflow==idWork &&\n"
                    + "this.solicitud.turnoAnterior.idCirculo==idCir && this.anulado == 'N'");

            Collection col = (Collection) query.execute(idWork, idCirculo);

            Iterator it = col.iterator();

            //Se obtiene el primer elemento de la lista (si existe).
            //Un turno solo puede ser turno anterior de un solo turno.
            if (it.hasNext()) {
                turnoSiguiente = (TurnoEnhanced) it.next();
                pm.makeTransient(turnoSiguiente);
            }

            query.close(col);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            turnoSiguiente = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return turnoSiguiente;
    }
    
     /**
     * Captura Datos de Calificador para registrador
     *
     * @param idTurno El turno <code>Turno</code> Workflow
     * @param Circulo El <code>Circulo</code> Su id
     * @param Activo Si paso por registrador en numero
     * @return Lista de <code>ReproduccionSellos</code> 
     * @see gov.sir.core.negocio.modelo.ReproduccionSellos
     * @throws DAOException
     */
        public List getListReproduccionSellos(String idTurno, String Circulo, int Activo)throws DAOException{
        
                List Reproduccion = new ArrayList();
                Connection connection = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                VersantPersistenceManager jdoPM = null;

                StringBuffer sqlStatement;
                sqlStatement = new StringBuffer(8192);

                sqlStatement.append("SELECT * ");
                sqlStatement.append("FROM SIR_OP_REPRODUCCION_SELLOS RSEL ");
                sqlStatement.append("WHERE RSEL.ID_TURNO_RAIZ  = :1 AND  ");
                sqlStatement.append("RSEL.ID_CIRCULO = :2 AND ");
                sqlStatement.append("RSEL.FASE_REGISTRO = :3 ");

                String result = null;
                try {
                    jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                    jdoPM.currentTransaction().begin();
                    connection = jdoPM.getJdbcConnection(null);

                    ps = connection.prepareStatement(sqlStatement.toString());
                    ps.setString(1, idTurno);
                    ps.setString(2, Circulo);
                    ps.setInt(3, Activo);

                    rs = ps.executeQuery();
                    while (rs.next()) {
                        ReproduccionSellos r = new ReproduccionSellos();
                        r.setCirculo(rs.getString("ID_CIRCULO"));
                        r.setCode(rs.getString("CODEC_WK_MT"));
                        r.setDesde(rs.getInt("DESDE"));
                        r.setFase_reg(rs.getInt("FASE_REGISTRO"));
                        r.setHasta(rs.getInt("HASTA"));
                        r.setIdReproduccionSellos(rs.getInt("ID_REPRODUCCION_SELLOS"));
                        r.setIdTurnoRaiz(rs.getString("ID_TURNO_RAIZ"));
                        r.setNcopiasSello(rs.getString("NCOPIAS"));
                        r.setOpcion(rs.getInt("OPCION"));
                        r.setUsuariosir(rs.getString("USUARIO_SIR"));
                        Reproduccion.add(r);
                    }

                    jdoPM.currentTransaction().commit();
                } catch (SQLException e) {
                        if (jdoPM.currentTransaction().isActive()) {
                            jdoPM.currentTransaction().rollback();
                        }

                        Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                        throw new DAOException(e.getMessage(), e);
                    } catch (JDOObjectNotFoundException e) {
                        if (jdoPM.currentTransaction().isActive()) {
                            jdoPM.currentTransaction().rollback();
                        }

                        Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                        throw new DAOException(e.getMessage(), e);
                    } catch (JDOException e) {
                        if (jdoPM.currentTransaction().isActive()) {
                            jdoPM.currentTransaction().rollback();
                        }

                        Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                        throw new DAOException(e.getMessage(), e);
                    } catch (Throwable e) {
                        if (jdoPM.currentTransaction().isActive()) {
                            jdoPM.currentTransaction().rollback();
                        }

                        Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
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
                            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                            throw new DAOException(e.getMessage(), e);
                        }
                    }
                return Reproduccion;
        }
        /**
	* @param reproduccion <code>String</code> 
	* @return bool si fue exitosamente guardado
	* @see gov.sir.core.negocio.modelo.ReproduccionSellos
	*/
        public boolean CreateReproduccionSellosReg(ReproduccionSellos reproduccion) throws DAOException{
            PersistenceManager pm = AdministradorPM.getPM();
            boolean rta = true;
            try{    
                ReproduccionSellosEnhanced Reproduccion = (ReproduccionSellosEnhanced)ReproduccionSellosEnhanced.enhance(reproduccion);
                JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
                long secuenciaSellos = solicitudesDAO.getSecuencial("SIR_OP_REPRODUCCION_SELLOS", null);
                pm.currentTransaction().begin();
                Reproduccion.setIdReproduccionSellos((int)secuenciaSellos);
                pm.makePersistent(Reproduccion);
                pm.currentTransaction().commit();
            } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            rta = false;
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
            } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            rta = false;
            throw new DAOException(e.getMessage(), e);            
        } finally {
            pm.close();
        }
         return rta;
        }
    /**
     * Obtiene una lista de turnos dentro de un rango dado, que han pasado por
     * una fase dada.
     *
     * @param idFase identificador de la fase por la cual debe haber pasado el
     * turno.
     * @param turnoInicial identificador del turno inicial dentro del rango en
     * el que se va a realizar la consulta.
     * @param turnoFinal identificado del turno final dentro del ranqo en el que
     * se va a realizar la consulta
     * @return Lista de todos los turnos que han pasado por la fase dada y que
     * est?n comprendidos entre el rango de turnos dado.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     */
    public List getRangoTurnosByFase(String idFase, String turnoInicial,
            String turnoFinal) throws DAOException {

        if (turnoInicial.split("-").length != 4 || turnoFinal.split("-").length != 4) {
            throw new DAOException("El formato del rango es invalido");
        }
        int intFinalRango = 0;
        int intInicialRango = 0;
        //String prefijoTurno = "";
        //String turnoObtenido = "";
        //Turno turnoValido;

        //1. VALIDAR QUE EL RANGO INICIAL SEA MENOR QUE EL RANGO FINAL.
        // Se determina el n?mero del turno inicial.
        //Se elimina el token que contiene el a?o.
        int intAnioInicial = 0;
        int intAnioFinal = 0;
        try {
            StringTokenizer st = new StringTokenizer(turnoInicial, "-");
            String inicioRango = null;
            String inicioAnio = null;
            String inicioCirc = null;

            inicioAnio = st.nextToken();
            inicioCirc = st.nextToken();

            while (st.hasMoreTokens()) {
                inicioRango = (st.nextToken());
            }

            Integer IntInicialRango = new Integer(inicioRango);
            intInicialRango = IntInicialRango.intValue();

            intAnioInicial = Integer.parseInt(inicioAnio);

            //Se determina el n?mero del turno final
            //Se elimina el token que contiene el a?o.
            StringTokenizer st2 = new StringTokenizer(turnoFinal, "-");
            String finRango = null;
            String finAnio = null;
            String finCirc = null;

            finAnio = st2.nextToken();
            finCirc = st2.nextToken();

            if (!inicioCirc.equals(finCirc)) {
                throw new DAOException(
                        "Los circulos del rango no coinciden");
            }

            while (st2.hasMoreTokens()) {
                finRango = (st2.nextToken());
            }

            Integer IntFinalRango = new Integer(finRango);
            intFinalRango = IntFinalRango.intValue();

            intAnioFinal = Integer.parseInt(finAnio);

            //2. VALIDAR QUE EL RANGO INICIAL SEA MENOR QUE EL RANGO FINAL.
            if (intAnioInicial > intAnioFinal) {
                throw new DAOException(
                        "El valor inicial del rango es mayor que el valor final ");
            } else if (intAnioInicial == intAnioFinal) {
                if (intInicialRango > intFinalRango) {
                    throw new DAOException(
                            "El valor inicial del rango es mayor que el valor final ");
                }
            }
        } catch (NumberFormatException nfe) {
            Log.getInstance().error(JDOTurnosDAO.class, nfe.getMessage(), nfe);
            throw new DAOException("Los rangos de los turnos no tienen formato num?rico ",
                    nfe);
        }

        //Obtener el listado de los turnos que cumplen con los requisitos dados.
        //Obtener prefijo compuesto por a?o - circulo - proceso -
        StringTokenizer st3 = new StringTokenizer(turnoFinal, "-");
        String year = st3.nextToken();
        String circulo = st3.nextToken();
        String proceso = st3.nextToken();

        //prefijoTurno = year + "-" + circulo + "-" + proceso + "-";
        List listaTurnos = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            boolean endFound = false;
            int intFinalRangoAux = intFinalRango;
            for (int a = intAnioInicial; a <= intAnioFinal; a++) {
                if (a < intAnioFinal) {
                    intFinalRango = Integer.MAX_VALUE;
                } else {
                    intFinalRango = intFinalRangoAux;
                }
                for (int i = intInicialRango; i <= intFinalRango && !endFound; i++) {
                    //Obtener el turno persistente.
                    TurnoEnhancedPk idTurno = new TurnoEnhancedPk();
                    idTurno.anio = "" + a;
                    idTurno.idCirculo = circulo;
                    idTurno.idProceso = new Long(proceso).longValue();
                    idTurno.idTurno = i + "";

                    TurnoEnhanced turnoActual = this.getTurnoByID(idTurno, pm);
                    if (turnoActual == null) {
                        endFound = true;
                    } else {
                        if (idFase.equals(CFase.REG_MESA_CONTROL)) {
                            //cuando voy a comparar por fase mesa control no tomo en cuenta el historial
                            // del turno sino la fase acutal
                            if (turnoActual.getIdFase() != null && turnoActual.getIdFase().equals(idFase)) {
                                pm.makeTransientAll(turnoActual.getHistorials());
                                pm.makeTransient(turnoActual.getSolicitud());
                                pm.makeTransient(turnoActual);
                                listaTurnos.add(turnoActual);
                            }
                        } else {
                            //Determinar si el turno tiene un turno historia con la fase recibida
                            //como par?metro.
                            List listaHistorials = turnoActual.getHistorials();

                            for (int j = 0; j < listaHistorials.size(); j++) {
                                TurnoHistoriaEnhanced turnoHistoria = (TurnoHistoriaEnhanced) listaHistorials.get(j);
                                if (turnoHistoria.getFase().equals(idFase)) {
                                    pm.makeTransientAll(turnoActual.getHistorials());
                                    pm.makeTransient(turnoActual.getSolicitud());
                                    pm.makeTransient(turnoActual);
                                    listaTurnos.add(turnoActual);
                                    j = listaHistorials.size() + 1;
                                }
                            }
                        }
                    }
                }
                intInicialRango = 1;
                endFound = false;
            }

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        List listaTransfer = TransferUtils.makeTransferAll(listaTurnos);

        return listaTransfer;
    }

    /**
     * Modifica la categor?a de clasificaci?n de una solicitud de registro.
     *
     * @param clasificacion Valor que va a ser asignado al atributo categor?a de
     * clasificaci?n de la solicitud de registro.
     * @param turnoActualizado
     * @return <code> true </code> o <code>false </code> dependiendo del
     * resultado de la operaci?n.
     * @throws <code>DAOException </code>
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public boolean updateClasificacionSolicitudRegistro(String clasificacion,
            Turno turnoActualizado) throws DAOException {
        SolicitudEnhancedPk solRegistroId = new SolicitudEnhancedPk();
        SolicitudRegistroEnhanced solRegEnh = new SolicitudRegistroEnhanced();
        PersistenceManager pm = AdministradorPM.getPM();
        //pm.currentTransaction().setOptimistic(false);
        pm.currentTransaction().begin();

        //VALIDAR  LA SOLICITUD RECIBIDA
        if (turnoActualizado.getSolicitud() == null) {
            throw new DAOException("La solicitud Registro recibida es nula");
        }

        if (turnoActualizado.getSolicitud().getIdSolicitud() == null) {
            throw new DAOException(
                    "El identificador de la solicitud Registro recibida es nulo");
        }

        try {
            //SE OBTIENE LA SOLICITUD REGISTRO PERSISTENTE.
            solRegistroId.idSolicitud = turnoActualizado.getSolicitud()
                    .getIdSolicitud();
            solRegEnh = (SolicitudRegistroEnhanced) pm.getObjectById(solRegistroId,
                    true);

            if (solRegEnh == null) {
                throw new DAOException("No se encontro la solcitud con el id: "
                        + solRegistroId);
            }

            // SE ASIGNA EL VALOR DEL NUEVO ATRIBUTO
            solRegEnh.setClasificacionRegistro(clasificacion);

            //SE HACE COMMIT SOBRE LA TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Modifica el atributo ajuste de una <code>SolicitudRegistro</code>
     *
     * @param turno <code>Turno</code> que tiene asociada la Solicitud.
     * @param valor el boolenao que va a ser asignado al atributo ajuste de la
     * <code>SolicitudRegistro</code>
     * @return Turno actualizado.
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    public boolean updateAjusteInTurnoRegistro(Turno turno, boolean ajuste)
            throws DAOException {
        SolicitudEnhancedPk solRegistroId = new SolicitudEnhancedPk();
        SolicitudRegistroEnhanced solRegEnh = new SolicitudRegistroEnhanced();
        PersistenceManager pm = AdministradorPM.getPM();
        //pm.currentTransaction().setOptimistic(false);
        pm.currentTransaction().begin();

        //VALIDAR  LA SOLICITUD RECIBIDA
        if (turno.getSolicitud() == null) {
            throw new DAOException("La solicitud Registro recibida es nula");
        }

        if (turno.getSolicitud().getIdSolicitud() == null) {
            throw new DAOException(
                    "El identificador de la solicitud Registro recibida es nulo");
        }

        try {
            //SE OBTIENE LA SOLICITUD REGISTRO PERSISTENTE.
            solRegistroId.idSolicitud = turno.getSolicitud().getIdSolicitud();
            solRegEnh = (SolicitudRegistroEnhanced) pm.getObjectById(solRegistroId,
                    true);

            if (solRegEnh == null) {
                throw new DAOException("No se encontro la solcitud con el id: "
                        + solRegistroId);
            }

            // SE ASIGNA EL VALOR DEL NUEVO ATRIBUTO
            solRegEnh.setAjuste(ajuste);

            //SE HACE COMMIT SOBRE LA TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Crear un turno de registro asociado a la solicitud recibida como
     * par?metro.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser asociada al
     * turno.
     * @param usuarioSir <code>Usuario</code> que crea el turno.
     * @return el turno creado.
     * @throws <code>DAOException </code>
     */
    public Turno crearTurnoRegistro(SolicitudRegistro solicitud, Usuario usuarioSir)
            throws DAOException {
        //CREAR TURNO DE PROCESOS: 6
        long currentMilliseconds = 0;
        long antes = 0;
        long despues = 0;
        currentMilliseconds = System.currentTimeMillis();
        List listaTurnosCertificados = new ArrayList();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        pm.currentTransaction().setOptimistic(false);
        pm.currentTransaction().begin();

        PagoEnhanced pagoEnh = null;
        TurnoEnhanced t = null;
        String uid = null;

        try {
            //1. Obtener liquidaci?n asociada a la solicitud.
            LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) solicitud.getLiquidaciones()
                    .get(0);

            if (liq == null) {
                throw new DAOException(
                        "La solicitud no tiene asociada una liquidaci?n");
            }

            //2. Obtener el pago asociado con la liquidaci?n.
            Pago pago = liq.getPago();
            uid = liq.getUid();

            if (pago == null) {
                throw new DAOException(
                        "La Liquidaci?n no tiene un pago asociado");
            }

            PagoEnhancedPk pagoEnhId = new PagoEnhancedPk();
            pagoEnhId.idLiquidacion = liq.getIdLiquidacion();
            pagoEnhId.idSolicitud = liq.getIdSolicitud();

            pagoEnh = (PagoEnhanced) pm.getObjectById(pagoEnhId, true);

            //Se obtiene un circulo proceso enhanced.
            //Esta consulta efectua un select for update para garantizar sincronizaci?n.
            CirculoProcesoEnhanced cpe = pagosDAO.getCirculoProcesoByPago(pagoEnh, pm);

            //Se crea el turno
            //1. Crear el turno en el modelo operativo.
            TurnoEnhancedPk turnoCreado = this.crearTurnoNuevoRegistro(cpe, pagoEnhId, null, usuarioSir, pm);

            if (turnoCreado == null) {
                throw new DAOException("Error en la creaci?n del turno.");
            }

            /**
             * *************************************************************
             */
            /*      CREACION DE LOS TURNOS DE CERTIFICADOS ASOCIADOS        */
            /**
             * *************************************************************
             */
            List listaAsociados = new ArrayList();
            listaAsociados = solicitud.getSolicitudesHijas();
            int tamAsociadas = listaAsociados.size();

            for (int i = 0; i < tamAsociadas; i++) {
                SolicitudAsociada solAsociada = (SolicitudAsociada) listaAsociados.get(i);
                SolicitudCertificado solCert = (SolicitudCertificado) solAsociada.getSolicitudHija();
                //1. Obtener la liquidaci?n asociada con la solicitud
                Liquidacion liqCertificados = (LiquidacionTurnoCertificado) solCert.getLiquidaciones().get(0);

                //2. Obtener el pago asociado con la liquidaci?n.
                //Pago pagoCertificados = liqCertificados.getPago();
                PagoEnhancedPk pagoCertEnhId = new PagoEnhancedPk();
                pagoCertEnhId.idLiquidacion = liqCertificados.getIdLiquidacion();
                pagoCertEnhId.idSolicitud = liqCertificados.getIdSolicitud();

                pagoEnh = pagosDAO.getPagoByID(pagoCertEnhId, pm);

                if (pagoEnh == null) {
                    throw new DAOException("La Liquidaci?n del certificado asociado no tiene un pago");
                }

                //Se setea el UID para impresi?n:
                LiquidacionTurnoCertificadoEnhanced liqAuxCert = (LiquidacionTurnoCertificadoEnhanced) pagoEnh.getLiquidacion();
                liqAuxCert.setUid(uid);

                //3. Se obtiene un circulo proceso enhanced.
                CirculoProcesoEnhanced cpeCert = pagosDAO.getCirculoProcesoByPago(pagoEnh, pm);

                //4. Se crea el turno
                /**
                 *
                 */
                TurnoEnhancedPk turnoCreadoCertificados = this.crearTurno(cpeCert, pagoCertEnhId, null, usuarioSir, pm);
                listaTurnosCertificados.add(turnoCreadoCertificados);
            }

            InfoLog.informarLogs(solicitud.getIdSolicitud(), InfoLog.VACIO, this, "crearTurnoRegistro()", InfoLog.TERMINA, InfoLog.LLAMADO_PROPIO, "despuesCreacionCertificadosAsociados", InfoLog.VACIO);

            //SE HACE COMMIT SOBRE LA TRANSACCION
            pm.currentTransaction().commit();

            t = this.getTurnoByID(turnoCreado, pm);

            this.makeTransientTurno(t, pm);

            //SE CREA LA INSTANCIA DEL WORKFLOW DE REGISTRO
            /**
             * ******************************************
             */
            /*            ELIMINACION SAS                */
            /**
             * ******************************************
             */
            if (t.getIdProceso() == 6) {
                //No se crea instancia de WF (Esto se realizar? en la acci?n de negocio ANLiquidacionRegistro.
            } else {

                this.crearInstanciaWF(t, null, usuarioSir, null, null);

            }
            //SE CREA LA INSTANCIA DE LOS WORKFLOWS DE CERTIFICADOS ASOCIADOS.

            InfoLog.informarLogs(solicitud.getIdSolicitud(), InfoLog.VACIO, this, "crearTurnoRegistro()", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "this.crearInstanciaWF en Certificados Asociados", InfoLog.VACIO);
            for (int i = 0; i < listaTurnosCertificados.size(); i++) {
                TurnoEnhancedPk turnoIdCertificados = (TurnoEnhancedPk) listaTurnosCertificados.get(i);
                TurnoEnhanced turnoCertificados = this.getTurnoByID(turnoIdCertificados, pm);
                this.makeTransientTurno(turnoCertificados, pm);
                this.crearInstanciaWF(turnoCertificados, null, usuarioSir, null, null);
            }
            InfoLog.informarLogs(solicitud.getIdSolicitud(), InfoLog.VACIO, this, "crearTurnoRegistro()", InfoLog.TERMINA, InfoLog.LLAMADO_PROPIO, "this.crearInstanciaWF en Certificados Asociados", InfoLog.VACIO);

        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        InfoLog.informarLogs(solicitud.getIdSolicitud(), InfoLog.VACIO, this, "crearTurnoRegistro()", InfoLog.TERMINA, InfoLog.LLAMADO_PROPIO, "TerminaCrearTurnoRegistro", InfoLog.VACIO);
        /**
         * @author : Ricardo Chivata
         * @change : Tarifas - varible porcentual del recibo.
         */
        JDOLiquidacionesDAO liquidaJDO = new JDOLiquidacionesDAO();
        liquidaJDO.AsociarTurnoSolicitudConservacion(solicitud.getIdSolicitud());
        return (Turno) t.toTransferObject();
    }

    /**
     * @see
     * gov.sir.hermod.dao.TurnosDAO#setRespuestaRecurso(gov.sir.core.negocio.modelo.Recurso)
     */
    public void setRespuestaRecurso(Recurso recurso) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //CREAR ID RECURSO
            RecursoPk rid = new RecursoPk();
            rid.anio = recurso.getAnio();
            rid.idCirculo = recurso.getIdCirculo();
            rid.idProceso = recurso.getIdProceso();
            rid.idTurno = recurso.getIdTurno();
            rid.idTurnoHistoria = recurso.getIdTurnoHistoria();
            rid.idRecurso = recurso.getIdRecurso();

            //BUSCAR PARAMETROS
            RecursoEnhanced recursoE = getRecursoByID(new RecursoEnhancedPk(
                    rid), pm);

            //EXISTEN LOS PARAMETROS SOLICITADOS?
            if (recursoE == null) {
                throw new DAOException("No se encontr? el Recurso");
            }

            //CREAR OBJETO RECURSO
            recursoE.setRespuestaRecurso(recurso.getRespuestaRecurso());

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * @see
     * gov.sir.hermod.dao.TurnosDAO#crearRecurso(gov.sir.core.negocio.modelo.TurnoHistoria.TurnoHistoriaPk,
     * gov.sir.core.negocio.modelo.TipoRecurso.TipoRecursoPk, java.lang.String)
     */
    public RecursoPk crearRecurso(Recurso recurso) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //VALIDAR LLAVES
            if (recurso.getTurnoHistoria() == null) {
                throw new DAOException("Para crear un recurso es necesario especificar el TurnoHistoria",
                        null);
            }

            /* Esta validaci?n ya no se realiza, el tipo de recurso llega como texto.
            if (recurso.getTipoRecurso() == null) {
                throw new DAOException("Para crear un recurso es necesario especificar el TipoRecurso", null);
            }
             */
            //CREAR ID TURNO HISTORIA
            TurnoHistoriaPk thid = new TurnoHistoriaPk();
            thid.anio = recurso.getTurnoHistoria().getAnio();
            thid.idCirculo = recurso.getTurnoHistoria().getIdCirculo();
            thid.idProceso = recurso.getTurnoHistoria().getIdProceso();
            thid.idTurno = recurso.getTurnoHistoria().getIdTurno();
            thid.idTurnoHistoria = recurso.getTurnoHistoria()
                    .getIdTurnoHistoria();

            //CREAR ID TIPO RECURSO
            /* Esta validaci?n ya no se realiza, el tipo de recurso llega como texto.
            TipoRecurso.ID trid = new TipoRecurso.ID();
            trid.idTipoRecurso = recurso.getTipoRecurso().getIdTipoRecurso();
             */
            //BUSCAR PARAMETROS
            TurnoHistoriaEnhanced the = getTurnoHistoriaByID(new TurnoHistoriaEnhancedPk(
                    thid), pm);
            the.setLastIdRecurso(the.getLastIdRecurso() + 1);

            /* Esta validaci?n ya no se realiza, el tipo de recurso llega como texto.
            TipoRecursoEnhanced tre = getTipoRecursoByID(new TipoRecursoEnhanced.ID(trid), pm);
             */
            //EXISTEN LOS PARAMETROS SOLICITADOS?
            if (the == null) {
                throw new DAOException("No se encontr? el TurnoHistoria");
            }


            /* Esta validaci?n ya no se realiza, el tipo de recurso llega como texto.
            if (tre == null) {
                throw new DAOException("No se encontr? el TipoRecurso");
            }
             */
            //CREAR OBJETO RECURSO
            recurso.setAnio(the.getAnio());
            recurso.setIdCirculo(the.getIdCirculo());
            recurso.setIdProceso(the.getIdProceso());
            recurso.setIdRecurso(the.getLastIdRecurso() + "");
            recurso.setIdTurno(the.getIdTurno());
            recurso.setIdTurnoHistoria(the.getIdTurnoHistoria());
            //Date fechaRecurso = new Date();
            //recurso.setFecha(fechaRecurso);

            RecursoEnhanced recursoE = RecursoEnhanced.enhance(recurso);

            /* Esta asignaci?n ya no se realiza, el identificador se toma del lastRecursoTurnoHistoria.
            recursoE.setIdRecurso("1"); //TODO Crear secuencia en turnohistoria cuando haya mas de un recurso por turnohistoria
             */
            recursoE.setTurnoHistoria(the);

            /* Esta asignaci?n ya no se realiza, el tipo de recurso se toma del atributo t?tulo
            recursoE.setTipoRecurso(tre);
             */
            pm.makePersistent(recursoE);

            //CREAR OBJETO RESPUESTA
            RecursoEnhancedPk rta = (RecursoEnhancedPk) pm.getObjectId(recursoE);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
            String concat = the.getAnio() + "-" + the.getIdCirculo() + "-" + String.valueOf(the.getIdProceso()) + "-" + the.getIdTurno();
            reorderRecursos(concat);

            return rta.getRecursoID();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public void updateRecurso(RecursoPk rid, String datoAmpliacion) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            RecursoEnhancedPk rEid = new RecursoEnhancedPk();
            rEid.anio = rid.anio;
            rEid.idCirculo = rid.idCirculo;
            rEid.idProceso = rid.idProceso;
            rEid.idTurno = rid.idTurno;
            rEid.idTurnoHistoria = rid.idTurnoHistoria;
            rEid.idRecurso = rid.idRecurso;

            RecursoEnhanced recursoEnh = (RecursoEnhanced) pm.getObjectById(rEid, true);

            String ampliacion = recursoEnh.getTextoRecurso() + ". " + datoAmpliacion;

            recursoEnh.setTextoRecurso(ampliacion);

            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Obtiene la lista de turnos de fotocopias que se encuentren en la fase de
     * 'FOT_PAGO' durante m?s de n d?as (n es pasado por par?metros)
     *
     * @param circulo C?rculo del cual se desea buscar los turnos
     * @param dias N?mero de dias
     * @return
     * @throws HermodException
     */
    public List getTurnosFotocopiasConPagoVencido(Circulo circulo, double dias)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();

        try {
            String idCir = circulo.getIdCirculo();
            String fase = CFase.FOT_PAGO;
            Date fechaHoy = new Date();
            long milisegundosARestar = (long) (dias * 24 * 60 * 60 * 1000);

            Date fechaComparada = new Date(fechaHoy.getTime()
                    - milisegundosARestar);

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("TurnoHistoriaEnhanced th");
            query.setOrdering("fechaInicio ascending");
            query.declareParameters(
                    "String fase, Date fechaComparada, String idCir");
            query.setFilter("this.idProceso == 5 &&\n"
                    + "this.idFase==fase &&\n" + "this.idCirculo==idCir &&\n"
                    + "this.fechaFin==null &&\n"
                    + "this.historial.contains(th) &&\n" + "th.fase==fase &&\n"
                    + "th.fecha<fechaComparada");

            Collection col = (Collection) query.execute(fase, fechaComparada,
                    idCir);
            Iterator iter = col.iterator();
            TurnoEnhanced turno;

            while (iter.hasNext()) {
                turno = (TurnoEnhanced) iter.next();
                pm.makeTransient(turno);
                rta.add(turno);
            }

            query.closeAll();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Obtiene la lista de turnos que se encuentren en la fase de
     * 'PMY_REGISTRAR' durante m?s de 2 meses
     *
     * @param circulo C?rculo del cual se desea buscar los turnos
     * @return
     * @throws HermodException
     */
    public List getTurnosConPagoMayorValorVencido(Circulo circulo)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();

        try {
            int meses = 2;
            String idCir = circulo.getIdCirculo();
            String fase = CFase.PMY_CUSTODIA;
            String fasePago = CFase.PMY_REGISTRAR;
            String faseNotificar = CFase.PMY_NOTIFICAR_CIUDADANO;

            Calendar fechaComp = Calendar.getInstance();
            fechaComp.add(Calendar.MONTH, -meses);
            fechaComp.set(Calendar.HOUR, 0);
            fechaComp.set(Calendar.AM_PM, 0);
            fechaComp.set(Calendar.MINUTE, 0);
            fechaComp.set(Calendar.SECOND, 0);
            fechaComp.set(Calendar.MILLISECOND, 0);
            Date fechaComparada = fechaComp.getTime();

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("TurnoHistoriaEnhanced th; TurnoHistoriaEnhanced thf");
            query.setOrdering("fechaInicio ascending");
            query.declareParameters(
                    "String fase, String fasePago, String faseNotificar,  Date fechaComparada, String idCir");
            query.setFilter("this.idCirculo==idCir && this.fechaInicio<fechaComparada &&\n"
                    + "this.fechaFin==null &&\n"
                    + "(this.idFase == fase || this.idFase == fasePago || this.idFase == faseNotificar) &&\n"
                    + "(this.historial.contains(th) && th.fase==fase) ");/* &&" +
	"!(this.historial.contains(thf) && thf.fase==fasePago && thf.respuesta == \"EXITO\") ");*/

            Object[] arreglo = new Object[5];
            arreglo[0] = fase;
            arreglo[1] = fasePago;
            arreglo[2] = faseNotificar;
            arreglo[3] = fechaComparada;
            arreglo[4] = idCir;

            Collection col = (Collection) query.executeWithArray(arreglo);
            Iterator iter = col.iterator();
            TurnoEnhanced turno;

            while (iter.hasNext()) {
                turno = (TurnoEnhanced) iter.next();
                pm.makeTransientAll(turno.getSolicitud().getSolicitudFolios());
                pm.makeTransient(turno.getSolicitud());
                pm.makeTransient(turno);
                rta.add(turno);
            }

            query.closeAll();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Obtiene la lista de turnos que se encuentren en la fase de
     * 'PMY_REGISTRAR', es decir, que est?n pendientes de pago
     *
     * @param circulo C?rculo del cual se desea buscar los turnos
     * @return
     * @throws HermodException
     */
    public List getTurnosConPagoMayorValorPendiente(Circulo circulo)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();

        try {
            String idCir = circulo.getIdCirculo();

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("TurnoHistoriaEnhanced th1;TurnoHistoriaEnhanced th2;TurnoHistoriaEnhanced th3");
            query.setOrdering("fechaInicio ascending");
            query.declareParameters("String fase1, String fase2, String fase3, String idCir");
            query.setFilter("((this.idFase == fase1 && (this.historial.contains(th1) && th1.fase == fase1)) ||"
                    + " (this.idFase == fase2 && (this.historial.contains(th2) && th2.fase == fase2)) ||"
                    + " (this.idFase == fase3 && (this.historial.contains(th3) && th3.fase == fase3))) &&"
                    + " this.idCirculo == idCir && this.fechaFin == null");

            Object[] args = {CFase.PMY_REGISTRAR, CFase.PMY_NOTIFICAR_CIUDADANO,
                CFase.PMY_CUSTODIA, idCir};
            Collection col = (Collection) query.executeWithArray(args);
            Iterator iter = col.iterator();
            TurnoEnhanced turno;

            while (iter.hasNext()) {
                turno = (TurnoEnhanced) iter.next();
                pm.makeTransient(turno);
                rta.add(turno);
            }

            query.closeAll();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Crear un turno de fotocopias asociado a la solicitud recibida como
     * par?metro.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser asociada al
     * turno.
     * @param usuarioSir <code>Usuario</code> que realiza el proceso.
     * @return el turno creado.
     * @throws <code>DAOException </code>
     */
    public Turno crearTurnoFotocopias(SolicitudFotocopia solicitud, Usuario usuarioSir) throws DAOException {
        //CREAR TURNO DE PROCESOS: 5
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        pm.currentTransaction().setOptimistic(false);
        pm.currentTransaction().begin();
        TurnoEnhanced t = null;

        try {

            //Se crea pago temporal para obtener el circulo proceso.
            PagoEnhanced pago = new PagoEnhanced();
            pago.setIdSolicitud(solicitud.getIdSolicitud());

            //Se obtiene un circulo proceso enhanced.
            //Esta consulta efectua un select for update para garantizar sincronizaci?n.
            CirculoProcesoEnhanced cpe = pagosDAO.getCirculoProcesoByPago(pago, pm);

            //Se crea el turno
            SolicitudEnhancedPk solId = new SolicitudEnhancedPk();
            solId.idSolicitud = solicitud.getIdSolicitud();

            /**
             * **********************************************************
             */
            /*                  ELIMINACION  SASS                        */
            /**
             * **********************************************************
             */
            //1. Crear Turno Modelo Operativo.
            TurnoEnhancedPk turnoCreado = this.crearTurnoFotocopias(cpe, solId, null, usuarioSir, pm);
            TurnoEnhanced turnoEnh = (TurnoEnhanced) pm.getObjectById(turnoCreado, true);

            if (turnoCreado == null) {
                throw new DAOException("Error en la creaci?n del turno.");
            }

            //2. Crear Turno Historia asociado a la creaci?n.
            //	Insertar el turno historia correspondiente a la creaci?n del turno.
            TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();

            //Se inicializa este atributo en false para la creaci?n porque en el paso 
            //siguiente, (Creaci?n del siguiente turno historia se dejar? activo).
            //Usuario
            UsuarioEnhancedPk idUsuario = new UsuarioEnhancedPk();
            idUsuario.idUsuario = usuarioSir.getIdUsuario();
            UsuarioEnhanced usuarioSirEnh = (UsuarioEnhanced) pm.getObjectById(idUsuario, true);

            th.setActivo(false);
            th.setAnio(turnoEnh.getAnio());
            th.setFase("SOLICITUD");
            th.setFecha(new Date());
            th.setRespuesta("CREACION DEL TURNO");
            th.setIdCirculo(turnoEnh.getIdCirculo());
            th.setIdProceso(turnoEnh.getIdProceso());
            th.setIdTurno(turnoEnh.getIdTurno());
            th.setAnio(turnoEnh.getAnio());
            th.setUsuario(usuarioSirEnh);
            th.setUsuarioAtiende(usuarioSirEnh);
            //Se asocia el valor 1 al id del turno historia, porque corresponde al primer historial.
            th.setIdTurnoHistoria("1");

            turnoEnh.setLastIdHistoria(1);
            turnoEnh.addHistorial(th);

            //SE HACE COMMIT SOBRE LA TRANSACCION
            pm.currentTransaction().commit();

            t = this.getTurnoByID(turnoCreado, pm);

            this.makeTransientTurno(t, pm);

        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (Turno) t.toTransferObject();

    }

    /**
     * Crea el turno de acuerdo a la informaci?n recibida en los par?metros.
     * <p>
     * M?todo privado llamado por este DAO y utilizado en forma transaccional.
     *
     * @return el identificador del objeto Turno creado.
     * @param cpe C?rculo Proceso asociado con el Turno que va a ser creado.
     * @param pId Identificador del Pago asociado con el Turno que va a ser
     * creado.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param user <code>Usuario</code> iniciador del <code>Proceso</code>
     * @param pm PersistenceManager de la Transacci?n.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    protected TurnoEnhancedPk crearTurno(CirculoProcesoEnhanced cpe,
            SolicitudEnhancedPk solId, String impresora, Usuario user, PersistenceManager pm) throws DAOException {
        //ESTA ES LA QUE NO SE USA
        //List notasInf = new ArrayList();
        TurnoEnhanced tr = new TurnoEnhanced();
        //TurnoHistoriaEnhanced thr = new TurnoHistoriaEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        //JDOPagosDAO pagosDAO = new JDOPagosDAO();
        //JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        /*try
	{
	EstacionDAO estacionDAO = DAOFactory.getFactory().getEstacionDAO();
	JerarquiaDAO jerarquiaDAO = DAOFactory.getFactory().getJerarquiaDAO();
	}
	catch (org.auriga.core.modelo.daoObjects.DAOException e)
	{
	throw new DAOException(e.getMessage());
	}*/
        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {
            /**
             *
             */
            UsuarioEnhanced userEnhanced = null;
            if (user != null && user.getUsername() != null) {
                JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
                userEnhanced = variablesDAO.getUsuarioByLogin(user.getUsername(), pm);
            }

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(solId, pm);
            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA + solId.idSolicitud);
            }

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos
                    = fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(), "", "", "");

            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creaci?n del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setAnio(cp.getAnio());
            tr.setFechaInicio(fecha);
            tr.setCirculoproceso(cp);
            tr.setSolicitud(s);
            tr.setLastIdHistoria(0);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
            tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-"
                    + String.valueOf(cp.getIdProceso()) + "-"
                    + String.valueOf(cp.getLastIdTurno() + 1));
            cp.setLastIdTurno(cp.getLastIdTurno() + 1);
            if (userEnhanced != null) {
                tr.setUsuarioDestino(userEnhanced);
            }
            tr.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            pm.makePersistent(tr);
            //Log.getInstance().debug(JDOTurnosDAO.class,"\n*******************************************************");
            //Log.getInstance().debug(JDOTurnosDAO.class,"(MO) Turno creado (crear turno L-4778): "+tr.getIdWorkflow());
            //Log.getInstance().debug(JDOTurnosDAO.class,"*******************************************************\n");
            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Crea un oficio, el oficio debe tener sus atributos b?sicos y una
     * asociaci?n con un turno historia existente.
     *
     * @param oficio
     * @return
     * @throws DAOException
     */
    public OficioPk crearOficio(Oficio oficio) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //VALIDAR LLAVES
            if (oficio.getTurnoHistoria() == null) {
                throw new DAOException("Para crear un recurso es necesario especificar el TurnoHistoria",
                        null);
            }

            //CREAR ID TURNO HISTORIA
            TurnoHistoriaPk thid = new TurnoHistoriaPk();
            thid.anio = oficio.getTurnoHistoria().getAnio();
            thid.idCirculo = oficio.getTurnoHistoria().getIdCirculo();
            thid.idProceso = oficio.getTurnoHistoria().getIdProceso();
            thid.idTurno = oficio.getTurnoHistoria().getIdTurno();
            thid.idTurnoHistoria = oficio.getTurnoHistoria()
                    .getIdTurnoHistoria();

            //BUSCAR PARAMETROS
            TurnoHistoriaEnhanced the = getTurnoHistoriaByID(new TurnoHistoriaEnhancedPk(
                    thid), pm);

            //EXISTEN LOS PARAMETROS SOLICITADOS?
            if (the == null) {
                throw new DAOException("No se encontr? el TurnoHistoria");
            }

            //VALIDACION DE TIPO DE OFICIO:
            TipoOficioEnhanced tipoE = null;
            if (oficio.getTipo() != null) {
                TipoOficioEnhancedPk tipoID = new TipoOficioEnhancedPk();
                tipoID.idTipoOficio = oficio.getTipo().getIdTipoOficio();

                tipoE = this.getTipoOficioByID(tipoID, pm);

                if (tipoE == null) {
                    throw new DAOException("No se encontr? el Tipo de oficio con el ID " + oficio.getTipo().getIdTipoOficio());
                }
            }

            //CREAR OBJETO OFICIO
            OficioEnhanced oficioE = OficioEnhanced.enhance(oficio);
            oficioE.setIdOficio(String.valueOf(solDAO.getSecuencial(COficio.TABLE_NAME, null)));

            oficioE.setTurnoHistoria(the);
            if (oficioE.getFechaCreacion() == null) {
                oficioE.setFechaCreacion(new Date());
            }
            oficioE.setTipo(tipoE);
            pm.makePersistent(oficioE);

            //CREAR OBJETO RESPUESTA
            OficioEnhancedPk rta = (OficioEnhancedPk) pm.getObjectId(oficioE);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return rta.getOficioID();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Elimina oficios, el oficio debe tener sus atributos b?sicos y una
     * asociaci?n con un turno historia existente.
     *
     * @param oficios
     * @return
     * @throws DAOException
     */
    public void eliminarOficios(List oficios) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            for (int i = 0; i < oficios.size(); i++) {
                Oficio oficio = (Oficio) oficios.get(i);
                OficioEnhancedPk oid = new OficioEnhancedPk();
                oid.idOficio = oficio.getIdOficio();

                OficioEnhanced oficioE = getOficioByID(oid, pm);

                pm.deletePersistent(oficioE);
            }
            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Actualiza la firma del oficio con el ID especificado en el flag indicado
     *
     * @param oficioID
     * @param firmado
     * @return
     * @throws DAOException
     */
    public boolean actualizarFirmaOficio(OficioPk oficioID, boolean firmado) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //BUSCAR PARAMETROS
            OficioEnhanced oficioE = getOficioByID(new OficioEnhancedPk(
                    oficioID), pm);

            //EXISTEN LOS PARAMETROS SOLICITADOS?
            if (oficioE == null) {
                throw new DAOException("No se encontr? el Oficio con el ID: " + oficioID.idOficio);
            }

            oficioE.setFirmado(firmado);
            if (firmado) {
                oficioE.setFechaFirma(new Date());
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }
        return true;
    }

    /**
     * Asocia un oficio respuesta a un recurso. Ambos deben existir en la base
     * de datos. El objeto queda en el atributo oficioRespuesta del recurso
     *
     * @param recursoID
     * @param oficioID
     * @return
     * @throws DAOException
     */
    public boolean setOficioRespuestaToRecurso(RecursoPk recursoID, OficioPk oficioID) throws DAOException {
        //TODO IMPLEMENTAR ESTE metodo
        return true;
    }

    /**
     * Retorna la lista de los identificadores de las estaciones SAS a donde se
     * despacho un turno dado.
     *
     * @param fase Fase a partir de la cual se quiere saber las estaciones donde
     * se despacho el turno.
     * @param tID Identificador del turno que se quiere averiguar.
     * @return Collection de objetos String
     * @throws <code>DAOException</code>
     */
    public List getEstacionesActuales(String fase, String idWF) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        Turno trn = this.getTurnoByWFId(idWF);
        TurnoPk tID = new TurnoPk();
        tID.idTurno = trn.getIdTurno();
        Query query = pm.newQuery(TurnoHistoriaEnhanced.class);
        query.declareParameters("String idTurno, String fase");
        query.setFilter("this.idTurno==idTurno &&\n"
                + "this.fase==fase");
        List col = (List) query.execute(tID.idTurno, fase);
        Iterator iter = col.iterator();
        List estacionesSAS = new ArrayList();
        while (iter.hasNext()) {
            //Se agrega a la lista de los ids del administrador de sas el identificador en String
            //estacionesSAS((String)((TurnoHistoriaEnhanced)iter.next()).getIdAdministradorSAS());
            String idAdmin = "";
            idAdmin = ((TurnoHistoriaEnhanced) iter.next()).getIdAdministradorSAS();
            estacionesSAS.add(idAdmin);

        }
        return estacionesSAS;
    }

    /**
     * Obtiene los oficios asociados al turno. Cada oficio tiene el turno
     * historia en el que fue creado
     *
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getOficiosTurno(TurnoPk oid) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();
        try {
            TurnoEnhanced tur = this.getTurnoByID(new TurnoEnhancedPk(oid), pm);
            if (tur == null) {
                throw new DAOException("EL turno especificado no existe");
            }

            Query query = pm.newQuery(OficioEnhanced.class);
            query.declareParameters("TurnoEnhanced tur");
            query.setFilter("this.turnoHistoria.turno == tur");
            Collection col = (Collection) query.execute(tur);
            OficioEnhanced oficio;
            for (Iterator iter = col.iterator(); iter.hasNext();) {
                oficio = (OficioEnhanced) iter.next();
                pm.makeTransient(oficio.getTurnoHistoria());
                pm.makeTransient(oficio.getTipo());
                pm.makeTransient(oficio);
                rta.add(oficio);
            }
            query.closeAll();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return TransferUtils.makeTransferAll(rta);
    }

    /**
     * Actualiza el n?mero del oficio con el ID especificado
     *
     * @param oficioID
     * @param firmado
     * @return
     * @throws DAOException
     */
    public boolean actualizarNumeroOficio(OficioPk oficioID, String nuevoNumero) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //BUSCAR PARAMETROS
            OficioEnhanced oficioE = getOficioByID(new OficioEnhancedPk(
                    oficioID), pm);

            //EXISTEN LOS PARAMETROS SOLICITADOS?
            if (oficioE == null) {
                throw new DAOException("No se encontr? el Oficio con el ID: " + oficioID.idOficio);
            }

            oficioE.setNumero(nuevoNumero);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return true;
    }

    /**
     * Agrega la fecha de firma al oficio con el ID especificado
     *
     * @param oficioID
     * @param fechaFirma
     * @return
     * @throws DAOException
     */
    public boolean agregarFechaFirmaOficio(OficioPk oficioID, Date fechaFirma) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //BUSCAR PARAMETROS
            OficioEnhanced oficioE = getOficioByID(new OficioEnhancedPk(
                    oficioID), pm);

            //EXISTEN LOS PARAMETROS SOLICITADOS?
            if (oficioE == null) {
                throw new DAOException("No se encontr? el Oficio con el ID: " + oficioID.idOficio);
            }

            oficioE.setFechaFirma(fechaFirma);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return true;
    }

    /**
     * Obtiene el listado de turnos calificados por un <code>Usuario</code> y
     * que no han pasado por la fase de firma del registrador.
     *
     * @param usuario El identificador del usuario del cual se est?n consultando
     * sus turnos calificados.
     * @param Circulo circulo al que pertenece el calificador
     * @return Lista con los turnos calificados por el <code>Usuario</code> dado
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>HermodException</code>
     */
    public List getTurnosByUsuarioCalificador(long usuario, Circulo circulo) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List listadoTurnos = new ArrayList();

        try {
            pm.currentTransaction().begin();

            //Obtener usuario persistente.
            UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
            userId.idUsuario = usuario;
            UsuarioEnhanced userEnh = (UsuarioEnhanced) pm.getObjectById(userId, true);

            //VALIDAR EL IDENTIFICADOR DEL USUARIO.
            if (userEnh == null) {
                throw new DAOException("No se encontr? el usuraio ID: " + usuario);
            }
            if (circulo == null) {
                throw new DAOException("El circulo enviado es inv?lido");
            }

            //Obtener usuario persistente.
            CirculoEnhancedPk circuloId = new CirculoEnhancedPk();
            circuloId.idCirculo = circulo.getIdCirculo();
            CirculoEnhanced cirEnh = (CirculoEnhanced) pm.getObjectById(circuloId, true);

            //VALIDAR EL IDENTIFICADOR DEL CIRCU.
            if (cirEnh == null) {
                throw new DAOException("No se encontr? el CIRCULO : " + circulo.getIdCirculo());
            }

            listadoTurnos = this.getTurnosEnFasesCalificadosByUsuario(userEnh, cirEnh, pm);
            pm.makeTransientAll(listadoTurnos);
            //pm.makeTransient(listadoTurnos);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return TransferUtils.makeTransferAll(listadoTurnos);
    }
       
    public String encontrarTipoPagoByIdDocPago(String idDocPago) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String rta = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT DCPG_TIPO_PAGO FROM SIR_OP_DOCUMENTO_PAGO WHERE ID_DOCUMENTO_PAGO = ?";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idDocPago);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
            while (rs.next()) {
                rta = rs.getString("DCPG_TIPO_PAGO");
            }

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
    /**
     * Obtiene el listado de turnos radicados en este dia y que se encuentren
     * actualmente en confrontaci?n.
     *
     * @return Lista con los turnos validos
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    public List listarTurnosRegistroParaAgregarCertificadosAsociados(Circulo cir) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List listadoTurnos = new ArrayList();

        try {
            pm.currentTransaction().begin();

            listadoTurnos = this.getTurnosValidorAgregarCertificadosAsociados(cir, pm);
            pm.makeTransientAll(listadoTurnos);
            //pm.makeTransient(listadoTurnos);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return TransferUtils.makeTransferAll(listadoTurnos);
    }

    /**
     * Actualiza el n?mero de copias del ?ltimo turno historia que tenga la fase
     * indicada.
     *
     * @param turnoID
     * @param nombreFase
     * @param numCopias
     * @param pm
     * @return
     * @throws DAOException
     */
    protected TurnoHistoriaEnhanced updateNumCopiasLastTurnoHistoriaByFase(TurnoEnhancedPk turnoID, String nombreFase, Integer numCopias, PersistenceManager pm) throws DAOException {
        TurnoHistoriaEnhanced turnoHistoria = null;

        try {
            String an = turnoID.anio;
            String idCir = turnoID.idCirculo;
            Long idProc = new Long(turnoID.idProceso);
            String idTur = turnoID.idTurno;

            Query query = pm.newQuery(TurnoHistoriaEnhanced.class);
            /**
             * @author: Fernando Padilla Velez
             * @change: Se establece como criterio de ordenamiento el atributo
             * idTurnoHistoria de mayor a menor.
             *
             */
            query.setOrdering("idTurnoHistoria descending");
            query.declareParameters("String an, String idCir, long idProc, String idTur, String faseBuscar");
            query.setFilter("this.anio==an &&\n"
                    + "this.idCirculo == idCir &&\n"
                    + "this.idProceso == idProc &&\n"
                    + "this.idTurno == idTur &&\n"
                    + "this.fase == faseBuscar");
            Collection col = (Collection) query.executeWithArray(new Object[]{an, idCir, idProc, idTur, nombreFase});
            Iterator iter = col.iterator();
            if (iter.hasNext()) {
                turnoHistoria = (TurnoHistoriaEnhanced) iter.next();
                turnoHistoria.setNumeroCopiasReimpresion(numCopias.intValue());
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return turnoHistoria;
    }

    /**
     * Actualiza el usuario que atiende el ?ltimo turno historia que tenga la
     * fase indicada. El usuario ingresado por par?metros debe ser un objeto
     * persistente
     *
     * @param turnoID
     * @param nombreFase
     * @param usuarioAtiende
     * @param pm
     * @return
     * @throws DAOException
     */
    protected TurnoHistoriaEnhanced updateUsuarioAtiendeLastTurnoHistoriaByFase(TurnoEnhancedPk turnoID, String nombreFase, UsuarioEnhanced usuarioAtiende, PersistenceManager pm) throws DAOException {
        TurnoHistoriaEnhanced turnoHistoria = null;

        try {
            String an = turnoID.anio;
            String idCir = turnoID.idCirculo;
            Long idProc = new Long(turnoID.idProceso);
            String idTur = turnoID.idTurno;

            Query query = pm.newQuery(TurnoHistoriaEnhanced.class);
            /**
             * @author: Fernando Padilla Velez
             * @change: Se establece como criterio de ordenamiento el atributo
             * idTurnoHistoria de mayor a menor.
             *
             */
            query.setOrdering("idTurnoHistoria descending");
            query.declareParameters("String an, String idCir, long idProc, String idTur, String faseBuscar");
            query.setFilter("this.anio==an &&\n"
                    + "this.idCirculo == idCir &&\n"
                    + "this.idProceso == idProc &&\n"
                    + "this.idTurno == idTur &&\n"
                    + "this.fase == faseBuscar");
            Collection col = (Collection) query.executeWithArray(new Object[]{an, idCir, idProc, idTur, nombreFase});
            Iterator iter = col.iterator();
            if (iter.hasNext()) {
                turnoHistoria = (TurnoHistoriaEnhanced) iter.next();
                turnoHistoria.setUsuarioAtiende(usuarioAtiende);
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return turnoHistoria;
    }

    /**
     * Obtiene una lista de turnos que est?n en las fases de
     * REG_REVISION_CALIFICACION, REG_MESA_CONTROL o REG_FIRMAR que hayan sido
     * calificados por el usuario especificado. La lista retornada es
     * persistente, se pueden hacer modificaciones sobre ella en la transacci?n
     * o se pueden hacer transientes
     *
     * @param usuarioAtiende
     * @param pm
     * @return
     * @throws DAOException
     */
    protected List getTurnosEnFasesCalificadosByUsuario(UsuarioEnhanced usuarioAtiende, CirculoEnhanced circulo, PersistenceManager pm) throws DAOException {
        List rta = new ArrayList();

        try {
            if (usuarioAtiende == null) {
                throw new DAOException("El usuario no puede cer nulo");
            }

            String fase1 = CFase.REG_REVISION_CALIFICACION;
            String fase2 = CFase.REG_MESA_CONTROL;
            String fase3 = CFase.REG_FIRMAR;
            String faseCalificacion = CFase.CAL_CALIFICACION;

            Long idProc = new Long(CProceso.PROCESO_REGISTRO);
            Long idUsu = new Long(usuarioAtiende.getIdUsuario());

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("TurnoHistoriaEnhanced th");
            query.declareParameters("long idProc, String idCir,  String fase1, String fase2, String fase3, long idUsu, String faseCalificacion");
            query.setFilter("this.idProceso== idProc &&\n"
                    + "this.idCirculo== idCir &&\n"
                    + "((this.idFase==fase1)||(this.idFase==fase2)\n"
                    + " ||(this.idFase==fase3) ) &&\n"
                    + "this.historial.contains(th) &&\n"
                    + "th.fase==faseCalificacion &&\n"
                    + "th.usuarioAtiende.idUsuario == idUsu");
            rta = (List) query.executeWithArray(new Object[]{idProc, circulo.getIdCirculo(), fase1, fase2, fase3, idUsu, faseCalificacion});

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Obtiene una lista de turnos que est?n en las fases de REG_CONFRONTACION y
     * que hayan sido radicados hoy
     *
     * @param pm
     * @return
     * @throws DAOException
     */
    protected List getTurnosValidorAgregarCertificadosAsociados(Circulo cir, PersistenceManager pm) throws DAOException {
        List rta = new ArrayList();

        try {

            String fase1 = CFase.REG_CONFRONTAR;
            String idCirculo = cir.getIdCirculo();

            Long idProc = new Long(CProceso.PROCESO_REGISTRO);

            Calendar cal = Calendar.getInstance();
            Date fechaHoy = BasicDateFormatComparator.fixDateAccordingFormat(cal.getTime(), "yyyy.MM.dd");

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareParameters("long idProc, String fase1, String idCirculo, Date fechaHoy");
            query.setFilter("this.idProceso== idProc &&\n"
                    + "this.idFase== fase1 &&\n"
                    + "this.idCirculo == idCirculo &&\n"
                    + "this.fechaInicio >= fechaHoy ");
            rta = (List) query.executeWithArray(new Object[]{idProc, fase1, idCirculo, fechaHoy});

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Obtiene el ?ltimo Reparto persistente de un turno en calificaci?n con un
     * folio asociado correspondiente al identificador pasado por par?metros. Si
     * no existe un reparto de de este tipo retorna null
     *
     * @param folioID Identificador del folio asociado
     * @param pm
     * @return
     * @throws DAOException
     */
    protected RepartoEnhanced getUltimoRepartoDeTurnoEnCalificacionConFolioAsociado(FolioEnhancedPk folioID, PersistenceManager pm) throws DAOException {
        RepartoEnhanced rta = null;
        try {
            String idMat = folioID.idMatricula;
            Long idProcReg = new Long(CProceso.PROCESO_REGISTRO);
            String faseCal = CFase.CAL_CALIFICACION;

            Query query = pm.newQuery(RepartoEnhanced.class);
            query.declareVariables("SolicitudFolioEnhanced sf");
            query.setOrdering("fechaCreacion descending");
            query.declareParameters("String idMat, Long idProcReg, String faseCal");
            query.setFilter("this.turno.idProceso == idProcReg &&\n"
                    + "this.turno.idFase == faseCal &&\n "
                    + "this.turno.idCirculo==this.turno.solicitud.circulo &&\n "
                    + "this.turno.solicitud.solicitudFolios.contains(sf) &&\n"
                    + "sf.idMatricula == idMat");
            Collection col = (Collection) query.executeWithArray(new Object[]{idMat, idProcReg, faseCal});

            Iterator iter = col.iterator();
            if (iter.hasNext()) {
                rta = (RepartoEnhanced) iter.next();
            }

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Devuelve el ?ltimo usuario asignado a un turno en calificaci?n que tenga
     * el folio con el ID folioID asociado
     *
     * @param folioID
     * @return
     * @throws DAOException
     */
    public Usuario getUsuarioConTurnoEnCalificacionConFolioAsociado(FolioPk folioID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        Usuario rta = null;
        UsuarioEnhanced aux = null;
        try {
            pm.currentTransaction().begin();

            RepartoEnhanced reparto = this.getUltimoRepartoDeTurnoEnCalificacionConFolioAsociado(new FolioEnhancedPk(folioID), pm);
            if (reparto != null) {
                aux = reparto.getUsuario();
                pm.makeTransient(aux);
            }
            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        if (aux != null) {
            rta = (Usuario) aux.toTransferObject();
        }

        return rta;
    }

    /**
     * Setea el conjunto de permisos configurados de un turno
     *
     * @param turnoID
     * @param permisos
     * @return
     * @throws DAOException
     */
    public boolean asignarPermisosCorreccion(TurnoPk turnoID, List permisos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        //Usuario rta = null;
        //UsuarioEnhanced aux = null;
        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL PROCESO DE CORRECCIONES O DE REGISTRO
            if (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_CORRECCIONES) && turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno NO es del proceso correcciones ni registro");
            }

            SolicitudEnhanced sol = turno.getSolicitud();

            //SE INSTANCIA UNA VARIABLE SEGUN EL TIPO DE SOLICITUD
            if (sol instanceof SolicitudCorreccionEnhanced) {

                //SE ELIMINA LOS PERMISOS ACTUALES
                SolicitudCorreccionEnhanced solCor = (SolicitudCorreccionEnhanced) turno.getSolicitud();
                pm.deletePersistentAll(solCor.getPermisos());
            } else {
                //SE ELIMINA LOS PERMISOS ACTUALES
                SolicitudRegistroEnhanced solReg = (SolicitudRegistroEnhanced) turno.getSolicitud();
                pm.deletePersistentAll(solReg.getPermisos());
            }

            //Ejecutamos el borrado de los resultados
            VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
            pm2.flush();

            //SE ASIGNAN LOS NUEVOS PERMISOS
            PermisoCorreccion per;
            for (Iterator it = permisos.iterator(); it.hasNext();) {
                per = (PermisoCorreccion) it.next();

                //SE VALIDA QUE EXISTA EL PERMISO:
                PermisoCorreccionEnhancedPk perID = new PermisoCorreccionEnhancedPk();
                perID.idPermiso = per.getIdPermiso();

                PermisoCorreccionEnhanced perCor = this.getPermisoCorreccionByID(perID, pm);

                if (perCor == null) {
                    throw new DAOException("El permiso especificado no existe: " + per.getIdPermiso());
                }

                SolicitudPermisoCorreccionEnhanced solPerCor = new SolicitudPermisoCorreccionEnhanced();
                solPerCor.setPermiso(perCor);
                solPerCor.setSolicitud(sol);
                solPerCor.setFechaCreacion(new Date());
                pm.makePersistent(solPerCor);

            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Obtiene un permiso correcci?n persistente por el ID
     *
     * @param tID
     * @param pm
     * @return
     * @throws DAOException
     */
    protected PermisoCorreccionEnhanced getPermisoCorreccionByID(PermisoCorreccionEnhancedPk tID,
            PersistenceManager pm) throws DAOException {
        PermisoCorreccionEnhanced rta = null;

        if (tID.idPermiso != null) {
            try {
                rta = (PermisoCorreccionEnhanced) pm.getObjectById(tID, true);
            } catch (JDOObjectNotFoundException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Refresca el subtipo de atenci?n del turno dependiendo de las nuevas
     * caracter?sticas de ?ste
     *
     * @param turnoID
     * @param permisos
     * @return
     * @throws DAOException
     */
    public boolean refrescarSubtipoAtencionTurno(TurnoPk turnoID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        //Usuario rta = null;
        //UsuarioEnhanced aux = null;
        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL PROCESO DE REGISTRO
            if (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno NO es del proceso registro");
            }

            JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

            SolicitudRegistroEnhanced sol = (SolicitudRegistroEnhanced) turno.getSolicitud();

            if (sol == null || sol.getSubtipoSolicitud() == null) {
                throw new DAOException("El subtipo de solicitud no es v?lido para el turno " + (turno != null ? turno.getIdWorkflow() : ""));
            }

            SubtipoAtencionEnhanced nuevoSubtipo = solDAO.seleccionarSubtipoAtencion(sol, pm);

            if (nuevoSubtipo != null) {
                sol.setSubtipoAtencion(nuevoSubtipo);
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Asigna un estado a la solicitud folio del turno y folio determinado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws DAOException
     */
    public boolean updateEstadoSolicitudFolio(SolicitudFolio solFolio) throws DAOException {
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();

        SolicitudFolioEnhanced temp = SolicitudFolioEnhanced.enhance(solFolio);

        SolicitudFolioEnhancedPk sid = new SolicitudFolioEnhancedPk();
        sid.idMatricula = temp.getIdMatricula();
        sid.idSolicitud = temp.getIdSolicitud();

        try {
            pm.currentTransaction().begin();

            //SE VERIFICA LA SOLICITUD FOLIO
            SolicitudFolioEnhanced solFol = (SolicitudFolioEnhanced) pm.getObjectById(sid, true);

            if (solFol == null) {
                throw new DAOException("No se encontr? la solicitud folio con el ID indicado");
            }

            solFol.setEstado(solFolio.getEstado());

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;

    }

    /**
     * Asigna una marca al folio dentro del turno especificado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws DAOException
     */
    public boolean marcarFolioInTurno(TurnoPk turnoID, FolioPk folioID) throws DAOException {
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL FOLIO
            FolioEnhanced fol = solDAO.getFolioByID(new FolioEnhancedPk(folioID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el folio con el ID indicado");
            }

            SolicitudFolioEnhanced solFol = solDAO.getSolicitudFolio(turno.getSolicitud(), fol, pm);

            solFol.setMarcado(true);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;

    }

    /**
     * Desmarca todos los folios asociados a un turno
     *
     * @param turnoID
     * @return
     * @throws DAOException
     */
    public boolean desmarcarFoliosInTurno(TurnoPk turnoID) throws DAOException {
        //JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            for (Iterator it = turno.getSolicitud().getSolicitudFolios().iterator(); it.hasNext();) {
                SolicitudFolioEnhanced solFol = (SolicitudFolioEnhanced) it.next();
                solFol.setMarcado(false);
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;

    }

    /**
     * Indica si el turno tiene por lo menos un acto del tipo indicado
     *
     * @param turnoID
     * @param tipoActoID
     * @return
     * @throws DAOException
     */
    public boolean hasActoTurnoRegistro(TurnoPk turnoID, TipoActoPk tipoActoID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        boolean rta = false;
        try {

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL PROCESO DE REGISTRO
            if (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno NO es del proceso registro");
            }

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("LiquidacionTurnoRegistroEnhanced liq; ActoEnhanced acto");
            query.declareParameters("String idWork, String idCir, String idTipoAct");
            query.setFilter("this.idWorkflow==idWork &&\n "
                    + " this.idCirculo==idCir &&\n "
                    + "this.solicitud.liquidaciones.contains(liq) &&\n"
                    + "liq.actos.contains(acto) && \n"
                    + "acto.tipoActo.idTipoActo == idTipoAct");
            Collection col = (Collection) query.execute(turno.getIdWorkflow(), turno.getIdCirculo(), tipoActoID.idTipoActo);

            Iterator iter = col.iterator();
            if (iter.hasNext()) {
                rta = true;
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }

        return rta;

    }

    /**
     * Elimina los modos de bloqueo y stack pos ingresados al avanzar push, de
     * acuerdo con el n?mero de avances ingresado como par?metro.
     *
     * @return <code> true </code> o <code> false </code> dependiendo del
     * resultado de la operaci?n.
     * @throws <code>DAOException </code>
     * @param turno El <code>Turno</code> sobre el cual se va a realizar la
     * actualizaci?n.
     * @param cantidad El n?mero de operaciones avanzar push que debe
     * deshacerse.
     */
    public boolean deshacerAvancesPush(Turno turno, int cantidad) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        //boolean respuesta = false;

        try {
            // pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se eliminan los atributos ingresados con el avanzar push, tantas veces como
            //lo indique el par?metro.
            for (int i = 0; i < cantidad; i++) {

                //Se intenta obtener el Turno Historia asociado con el turno, con el atributo
                // stackPos que tenga el valor mayor.
                TurnoHistoriaPk idTurno = new TurnoHistoriaPk();
                idTurno.anio = turno.getAnio();
                idTurno.idCirculo = turno.getIdCirculo();
                idTurno.idProceso = turno.getIdProceso();
                idTurno.idTurno = turno.getIdTurno();
                TurnoHistoriaEnhanced maxTurnoHistoria = this.getMaxTurnoHistoria(idTurno, pm);

                TurnoHistoriaEnhancedPk turnoHistID = new TurnoHistoriaEnhancedPk();
                turnoHistID.anio = maxTurnoHistoria.getAnio();
                turnoHistID.idCirculo = maxTurnoHistoria.getIdCirculo();
                turnoHistID.idProceso = maxTurnoHistoria.getIdProceso();
                turnoHistID.idTurno = maxTurnoHistoria.getIdTurno();
                turnoHistID.idTurnoHistoria = maxTurnoHistoria.getIdTurnoHistoria();

                //Actualizaci?n de la tabla Turno Historia
                TurnoHistoriaEnhanced maxTurnoHistoria2 = this.getTurnoHistoriaByID(turnoHistID, pm);

                if (maxTurnoHistoria2 != null) {
                    maxTurnoHistoria2.setStackPos(0);
                }

            }

            pm.currentTransaction().commit();

        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Asigna una marca al folio reci?n creado en antiguo sistema dentro del
     * turno especificado
     *
     * @param turnoID
     * @param folioID
     * @return
     * @throws DAOException
     */
    public boolean marcarFolioRecienCreadoASInTurno(TurnoPk turnoID, FolioPk folioID) throws DAOException {
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL FOLIO
            FolioEnhanced fol = solDAO.getFolioByID(new FolioEnhancedPk(folioID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el folio con el ID indicado");
            }

            SolicitudFolioEnhanced solFol = solDAO.getSolicitudFolio(turno.getSolicitud(), fol, pm);

            solFol.setRecienCreadoAntiguoSistema(true);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;

    }

    /**
     * Desmarca todos los folios reci?n creados en antiguo sistema asociados a
     * un turno
     *
     * @param turnoID
     * @return
     * @throws DAOException
     */
    public boolean desmarcarFoliosRecienCreadoASInTurno(TurnoPk turnoID) throws DAOException {
        //JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            for (Iterator it = turno.getSolicitud().getSolicitudFolios().iterator(); it.hasNext();) {
                SolicitudFolioEnhanced solFol = (SolicitudFolioEnhanced) it.next();
                solFol.setRecienCreadoAntiguoSistema(false);
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;

    }

    /**
     * Indica si el turno tiene una nota informativa en la ?ltima fase
     *
     * @param turnoID
     * @return
     * @throws DAOException
     */
    public boolean hasNotaInLastFase(TurnoPk turnoID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        boolean rta = false;
        try {
            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            Query query = pm.newQuery(NotaEnhanced.class);
            query.setOrdering("time descending");
            query.declareParameters("TurnoEnhanced tur");
            query.setFilter("this.turno==tur");
            Collection col = (Collection) query.execute(turno);
            Iterator iter = col.iterator();
            if (iter.hasNext()) {
                NotaEnhanced nota = (NotaEnhanced) iter.next();

                if (nota.getIdFase() != null) {
                    if (nota.getIdFase().equals(turno.getIdFase())) {
                        Long longNota = new Long(nota.getIdTurnoHistoria());
                        Long longTurno = new Long(turno.getLastIdHistoria());

                        if (longNota.equals(longTurno)) {
                            rta = true;
                        }
                    }
                }

            }

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }

        return rta;

    }

    /**
     * Retorna las notas del turno correspondientes a la ?ltima fase
     *
     * @param turnoID
     * @return
     * @throws DAOException
     */
    public List getNotasInLastFase(TurnoPk turnoID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = null;
        List notasEnh = null;
        try {
            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            Query query = pm.newQuery(NotaEnhanced.class);
            query.setOrdering("time descending");
            query.declareParameters("TurnoEnhanced tur");
            query.setFilter("this.turno==tur");
            Collection col = (Collection) query.execute(turno);
            Iterator iter = col.iterator();
            while (iter.hasNext()) {
                if (notasEnh == null) {
                    notasEnh = new ArrayList();
                }
                NotaEnhanced nota = (NotaEnhanced) iter.next();

                if (nota.getIdFase() != null) {
                    if (nota.getIdFase().equals(turno.getIdFase())) {
                        Long longNota = new Long(nota.getIdTurnoHistoria());
                        Long longTurno = new Long(turno.getLastIdHistoria());

                        if (longNota.equals(longTurno)) {
                            TipoNotaEnhanced tipoNotaEnh = nota.getTiponota();
                            nota.setTiponota(tipoNotaEnh);
                            notasEnh.add(nota);
                        }
                    }
                }

            }
            if (notasEnh != null && notasEnh.size() > 0) {
                pm.makeTransientAll(notasEnh);
                rta = new ArrayList();
                for (Iterator i = notasEnh.iterator(); i.hasNext();) {
                    NotaEnhanced notaEnh = (NotaEnhanced) i.next();
                    Nota nota = (Nota) notaEnh.toTransferObject();
                    rta.add(nota);
                }
            }

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }

        return rta;

    }

    public boolean crearWF(String turno) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhanced t;

        TurnoEnhancedPk turnoCreado = new TurnoEnhancedPk(turno);
        try {
            t = this.getTurnoByID(turnoCreado, pm);

            if (t == null) {
                throw new DAOException("El turno no existe: " + turno);
            }

            this.makeTransientTurno(t, pm);
            UsuarioEnhanced usuEnh = t.getSolicitud().getUsuario();

            Usuario usu = new Usuario();
            usu.setIdUsuario(usuEnh.getIdUsuario());
            usu.setUsername(usuEnh.getUsername());

            //SE CREA LA INSTANCIA DEL WORKFLOW DE REGISTRO
            this.crearInstanciaWF(t, null, usu, null, null);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnosByFechaYCirculo(gov.sir.core.negocio.modelo.Proceso, gov.sir.core.negocio.modelo.Fase, java.util.Date, gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosByFechaYCirculo(Proceso proceso, Fase fase,
            Date fecha, Circulo circulo) throws DAOException {

        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();
        String idFase = fase.getID();
        Date fechaIni = fecha;
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.setTime(fecha);
        fechaFin.add(Calendar.DATE, 1);

        Date fechaFinal = fechaFin.getTime();
        Long idProc = new Long(proceso.getIdProceso());
        String idCirculo = circulo.getIdCirculo();

        try {
            Query q = pm.newQuery(TurnoEnhanced.class);
            q.declareParameters("String idFase, Date fechaIni, Date fechaFinal");
            q.setFilter(
                    "(this.fechaInicio>=fechaIni && this.fechaInicio<fechaFinal) &&\n"
                    + "this.idFase==idFase && this.fechaFin == null && this.idProceso=="
                    + idProc + " && this.idCirculo == '" + idCirculo + "'");

            Collection results = (Collection) q.execute(idFase, fechaIni,
                    fechaFinal);
            Iterator it = results.iterator();

            while (it.hasNext()) {
                TurnoEnhanced obj = (TurnoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            q.close(results);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
            lista = TransferUtils.makeTransferAll(lista);
        }

        return lista;
    }

    public List getTurnosByFechaAndCirculoMinusMasivos(Proceso proceso, Fase fase,
            Date fecha, Circulo circulo) throws DAOException {

        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {

            Query q = pm.newQuery(TurnoEnhanced.class);
            q.declareParameters("String fase, String circulo");

            q.setFilter("this.idFase == fase && "
                    + "this.fechaFin ==  null && "
                    + "this.idCirculo ==  circulo && "
                    + "this.ultimaRespuesta !=  'ASOCIADO_MASIVO' && "
                    + "this.idProceso == " + proceso.getIdProceso());

            Collection results = (Collection) q.execute(fase.getID(), circulo.getIdCirculo());
            Iterator it = results.iterator();

            TurnoEnhanced obj = null;

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            while (it.hasNext()) {
                obj = (TurnoEnhanced) it.next();
                if (obj.getFechaInicio() != null) {
                    if (formato.format(obj.getFechaInicio()).equals(formato.format(fecha))) {
                        pm.makeTransient(obj);
                        lista.add(obj);
                    }
                }

            }

            q.close(results);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
            lista = TransferUtils.makeTransferAll(lista);
        }

        return lista;
    }
    
    public boolean isEstacionActivaCalificador(String estacionId) throws DAOException{
        boolean rta = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String response = "";
        String sql;
        sql = " SELECT ACTIVA FROM AURIGA.CMN_REL_ROL_ESTACION  " +
              " WHERE ESTACION_ID = '"+ estacionId +"' AND ROL_ID = 'SIR_ROL_CALIFICADOR'";
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
                response = rs.getString(1);
            }
            
            if(response != null && !response.equals("0")){
                rta = true;
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
    
    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnosCirculo(gov.sir.core.negocio.modelo.Proceso, gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosCirculo(Proceso proceso, Circulo circulo, String p_IdMatricula) throws DAOException {

        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        Long p_IdProceso = null;
        String p_IdCirculo = null;

        if (proceso != null) {
            p_IdProceso = new Long(proceso.getIdProceso());
        }

        if (circulo != null) {
            p_IdCirculo = circulo.getIdCirculo();
        }

        if (p_IdProceso.toString().equals(CProceso.PROCESO_PAGO_MAYOR_VALOR)
                || p_IdProceso.toString().equals(CProceso.PROCESO_PAGO_MAYOR_CORRECCION_CALIFICACION)
                || p_IdProceso.toString().equals(CProceso.PROCESO_PAGO_MAYOR_VALOR_REGISTRO)) {
            return getTurnosMayorValor(proceso, circulo, p_IdMatricula);
        }

        try {
            pm.currentTransaction().begin();
            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("SolicitudFolioEnhanced sf");
            query.setOrdering("idWorkflow ascending");
            query.declareParameters("String p_IdMatricula, String p_IdCirculo, long p_IdProceso");

            query.setFilter("this.idCirculo== p_IdCirculo\n"
                    + "&& this.idProceso == p_IdProceso\n"
                    + "&& this.solicitud == sf.solicitud\n"
                    + "&& sf.idMatricula== p_IdMatricula");

            Collection col = (Collection) query.execute(p_IdMatricula, p_IdCirculo, p_IdProceso);

            Iterator it = col.iterator();

            while (it.hasNext()) {
                TurnoEnhanced obj = (TurnoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            Log.getInstance().debug(JDOTurnosDAO.class, "TAMANO ES :" + lista.size());

            query.closeAll();
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            lista = TransferUtils.makeTransferAll(lista);
        }

        return lista;

    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnos(java.lang.String)
     */
    public List getTurnos(String idMatricula) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        List lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta
                = " select  a.anio, a.id_circulo, a.id_proceso, a.id_turno, a.trno_id_workflow, a.id_solicitud, "
                + "   a.trno_consistencia_wf, a.trno_anulado, "
                + "	a.trno_descripcion, a.trno_error, a.trno_fecha_fin, "
                + "	a.trno_fecha_inicio, a.trno_id_fase, a.trno_id_fase_relacion, "
                + " 	a.trno_relacion,  a.trno_last_id_historia, "
                + " 	a.trno_last_id_nota, a.trno_modo_bloqueo, "
                + " 	a.trno_observaciones_anulacion, "
                + " 	a.trno_ultima_respuesta, a.id_usuario_anulacion, a.id_usuario "
                + " from sir_op_solicitud_folio c, "
                + "  	sir_op_solicitud b, "
                + "  	sir_op_turno a"
                + " where b.id_solicitud = a.id_solicitud "
                + "	and b.id_solicitud = c.id_solicitud "
                + "	and c.id_matricula = ? ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, idMatricula);

            rs = ps.executeQuery();

            while (rs.next()) {
                Turno turno = new Turno();
                turno.setAnio(rs.getString(1));
                turno.setIdCirculo(rs.getString(2));
                turno.setIdProceso(rs.getLong(3));
                turno.setIdTurno(rs.getString(4));
                turno.setIdWorkflow(rs.getString(5));
                Solicitud sol = new Solicitud();
                sol.setIdSolicitud(rs.getString(6));
                turno.setSolicitud(sol);
                lista.add(turno);
            }

            /*if (idWorkflow!=null){
	turno = getTurnoByWFId(idWorkflow);
	}*/
            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
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
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return lista;

    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnosCirculo(gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosCirculo(Circulo circulo, String idMatricula) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        List lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String idCirculo = "";
        if (circulo != null) {
            idCirculo = circulo.getIdCirculo();
        }

        String consulta
                = " select  a.anio, a.id_circulo, a.id_proceso, a.id_turno, a.trno_id_workflow, a.id_solicitud, "
                + "   a.trno_consistencia_wf, a.trno_anulado, "
                + "	a.trno_descripcion, a.trno_error, a.trno_fecha_fin, "
                + "	a.trno_fecha_inicio, a.trno_id_fase, a.trno_id_fase_relacion, "
                + " 	a.trno_relacion,  a.trno_last_id_historia, "
                + " 	a.trno_last_id_nota, a.trno_modo_bloqueo, "
                + " 	a.trno_observaciones_anulacion, "
                + " 	a.trno_ultima_respuesta, a.id_usuario_anulacion, a.id_usuario "
                + " from sir_op_solicitud_folio c, "
                + "  	sir_op_solicitud b, "
                + "  	sir_op_turno a"
                + " where a.id_circulo = ? "
                + "	and b.id_solicitud = a.id_solicitud "
                + "	and b.id_solicitud = c.id_solicitud "
                + "	and c.id_matricula = ? ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, idCirculo);
            ps.setString(2, idMatricula);

            rs = ps.executeQuery();

            while (rs.next()) {
                Turno turno = new Turno();
                turno.setAnio(rs.getString(1));
                turno.setIdCirculo(rs.getString(2));
                turno.setIdProceso(rs.getLong(3));
                turno.setIdTurno(rs.getString(4));
                turno.setIdWorkflow(rs.getString(5));
                Solicitud sol = new Solicitud();
                sol.setIdSolicitud(rs.getString(6));
                turno.setSolicitud(sol);
                lista.add(turno);
            }

            /*if (idWorkflow!=null){
	turno = getTurnoByWFId(idWorkflow);
	}*/
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

        return lista;

    }

    /* Devuelve los turnos que son del proceso de Mayor Valor
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnosCirculo(gov.sir.core.negocio.modelo.Proceso, gov.sir.core.negocio.modelo.Circulo)
     */
    public List getTurnosMayorValor(Proceso proceso, Circulo circulo, String p_IdMatricula) throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        Long p_IdProceso = null;
        String p_IdCirculo = null;

        if (proceso != null) {
            p_IdProceso = new Long(proceso.getIdProceso());
        }

        if (circulo != null) {
            p_IdCirculo = circulo.getIdCirculo();
        }

        try {
            pm.currentTransaction().begin();
            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareImports("import gov.sir.hermod.dao.impl.jdogenie.*");
            query.declareVariables("Procesos_V proceso; SolicitudFolioEnhanced solicitudfolioenhanced");
            query.declareParameters("String idMatricula, String idProceso, String idCirculo");
            query.setFilter("this.idFase == proceso.id_fase && this.solicitud.idSolicitud == solicitudfolioenhanced.idSolicitud && solicitudfolioenhanced.idMatricula == idMatricula && proceso.id_proceso == idProceso");
            Collection col = (Collection) query.execute(p_IdMatricula, p_IdProceso.toString(), p_IdCirculo);

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                TurnoEnhanced turnoenhanced = (TurnoEnhanced) iter.next();
                pm.makeTransient(turnoenhanced);
                lista.add(turnoenhanced);

            }
            query.closeAll();
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            lista = TransferUtils.makeTransferAll(lista);
        }

        return lista;
    }

    /**
     * Elimina del sistema las notas devolutivas asociadas con un turno.
     *
     * @param turno identificador del turno al cual se le van a eliminar las
     * notas devolutivas.
     * @throws DAOException
     */
    public void removeDevolutivasFromTurno(TurnoPk turnoID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE OBTIENE EL LISTADO DE NOTAS DEL TURNO.
            List listaNotas = new ArrayList();
            listaNotas = turno.getNotas();

            //Se recorre el listado y se eliminan de este las notas devolutivas.
            for (int i = 0; i < listaNotas.size(); i++) {
                NotaEnhanced nota = (NotaEnhanced) listaNotas.get(i);
                //Si la nota es devolutiva se elimina del listado de notas del turno.
                if (nota.getTiponota().isDevolutiva()) {
                    pm.deletePersistent(nota);
                }
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    public void addCertificadoAsociado(Turno turnoRegistro, Turno turnoCertificado) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhancedPk turnoRegistroID = new TurnoEnhancedPk();
        turnoRegistroID.anio = turnoRegistro.getAnio();
        turnoRegistroID.idCirculo = turnoRegistro.getIdCirculo();
        turnoRegistroID.idProceso = turnoRegistro.getIdProceso();
        turnoRegistroID.idTurno = turnoRegistro.getIdTurno();

        TurnoEnhancedPk turnoCertificadoID = new TurnoEnhancedPk();
        turnoCertificadoID.anio = turnoCertificado.getAnio();
        turnoCertificadoID.idCirculo = turnoCertificado.getIdCirculo();
        turnoCertificadoID.idProceso = turnoCertificado.getIdProceso();
        turnoCertificadoID.idTurno = turnoCertificado.getIdTurno();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turnoRegistroEnh = this.getTurnoByID(turnoRegistroID, pm);
            TurnoEnhanced turnoCertificadoEnh = this.getTurnoByID(turnoCertificadoID, pm);

            if (turnoRegistroEnh == null) {
                throw new DAOException("No fue posible encontrar el turno de registro con el ID " + turnoRegistroID);
            }

            if (turnoCertificadoEnh == null) {
                throw new DAOException("No fue posible encontrar el turno de certificado con el ID " + turnoCertificadoID);
            }

            SolicitudAsociadaEnhanced solicitudAsociada = new SolicitudAsociadaEnhanced();
            solicitudAsociada.setSolicitudHija(turnoCertificadoEnh.getSolicitud());
            solicitudAsociada.setSolicitudPadre(turnoRegistroEnh.getSolicitud());
            SolicitudEnhanced solicitud = turnoRegistroEnh.getSolicitud();
            solicitud.addSolicitudesHija(solicitudAsociada);

            pm.makePersistent(solicitud);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public void removeLiquidacionesSinPagoFromTurno(TurnoPk turnoID)
            throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhancedPk turnoEnhID = new TurnoEnhancedPk(turnoID);

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turnoEnh = this.getTurnoByID(turnoEnhID, pm);

            SolicitudEnhanced solicitud = turnoEnh.getSolicitud();
            List liquidaciones = solicitud.getLiquidaciones();
            LiquidacionEnhanced liquidacion = null;
            boolean tienePago = true;

            for (Iterator iterLiquidaciones = liquidaciones.iterator(); iterLiquidaciones.hasNext();) {
                liquidacion = (LiquidacionEnhanced) iterLiquidaciones.next();

                // Se elimina la liquidaci?n que no tenga pago. Se asume que s?lo existe
                // una liquidaci?n sin pago, ya que el sistema valida que s?lo pueda existir
                // una liquidaci?n sin pago.
                try {
                    tienePago = liquidacion.getPago() != null;
                } catch (JDOObjectNotFoundException joe) {
                    tienePago = false;
                }

                if (!tienePago) {
                    pm.deletePersistent(liquidacion);
                    break;
                }
            }

            ((VersantPersistenceManager) pm).flush();
            //pm.makePersistent(turnoEnh);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public void eliminarRelacionTurno(TurnoPk turnoID)
            throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhancedPk turnoEnhID = new TurnoEnhancedPk(turnoID);

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turnoEnh = this.getTurnoByID(turnoEnhID, pm);

            turnoEnh.setIdRelacionActual(null);
            turnoEnh.setIdFaseRelacionActual(null);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public TipoNota getTipoNota(gov.sir.core.negocio.modelo.TipoNotaPk tid) throws DAOException {

        TipoNota rta = null;
        PersistenceManager pm = AdministradorPM.getPM();
        TipoNotaEnhancedPk tidEnh = new TipoNotaEnhancedPk(tid);

        try {
            TipoNotaEnhanced tr = this.getTipoNotaByID(tidEnh, pm);

            if (tr == null) {
                throw new DAOException("No encontr? el TipoNota con el ID: " + tid.idTipoNota);
            }

            pm.makeTransient(tr);

            rta = (TipoNota) tr.toTransferObject();
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return rta;
    }

    /**
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnoDependiente(TurnoPk)
     */
    public Turno getTurnoPadre(TurnoPk id) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhanced turno = null;

        try {
            pm.currentTransaction().begin();

            // Se debe obtener el turno que tenga como padre el id que se suministra como par?metro,
            // y cuyo estado no est? finalizado.
            Query query = pm.newQuery(TurnoDerivadoEnhanced.class);
            query.declareParameters("Long proceso, String anioTurno, String circulo, String turno");

            query.setFilter("turnoHijo.idProceso == proceso && "
                    + "turnoHijo.anio == anioTurno && "
                    + "turnoHijo.idCirculo == circulo && "
                    + "turnoHijo.idTurno == turno");

            Collection col = (Collection) query.executeWithArray(new Object[]{new Long(id.idProceso),
                id.anio, id.idCirculo, id.idTurno});

            TurnoDerivadoEnhanced turnoDerivado;
            for (Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
                turnoDerivado = (TurnoDerivadoEnhanced) itTurnos.next();
                turno = turnoDerivado.getTurnoPadre();
            }

            if (turno != null) {
                makeTransientTurno(turno, pm);
            }

            query.closeAll();

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }

        if (turno == null) {
            return null;
        }

        return (Turno) turno.toTransferObject();
    }

    /**
     * @see gov.sir.hermod.dao.TurnosDAO#getTurnoDependiente(TurnoPk)
     */
    public Turno getTurnoDependiente(TurnoPk id) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        TurnoEnhanced turno = null;

        try {
            pm.currentTransaction().begin();

            // Se debe obtener el turno que tenga como padre el id que se suministra como par?metro,
            // y cuyo estado no est? finalizado.
            Query query = pm.newQuery(TurnoDerivadoEnhanced.class);
            query.declareParameters("Long proceso, String anioTurno, String circulo, String turno");

            query.setFilter("turnoPadre.idProceso == proceso && "
                    + "turnoPadre.anio == anioTurno && "
                    + "turnoPadre.idCirculo == circulo && "
                    + "turnoPadre.idTurno == turno && "
                    + "turnoHijo.idFase != \"FINALIZADO\" && "
                    + "turnoHijo.idFase != \"" + CFase.COS_ENTREGAR_ASOCIADOS + "\"");

            Collection col = (Collection) query.executeWithArray(new Object[]{new Long(id.idProceso),
                id.anio, id.idCirculo, id.idTurno});

            TurnoDerivadoEnhanced turnoDerivado;
            for (Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
                turnoDerivado = (TurnoDerivadoEnhanced) itTurnos.next();
                turno = turnoDerivado.getTurnoHijo();
            }

            if (turno != null) {
                makeTransientTurno(turno, pm);
            }

            query.closeAll();

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }

        if (turno == null) {
            return null;
        }

        return (Turno) turno.toTransferObject();
    }

    /**
     * @see gov.sir.hermod.dao.TurnosDAO#crearTurnoDependiente(Turno, Usuario,
     * long)
     */
    public Turno crearTurnoDependiente(Turno turnoPadre, Usuario usuario, long idProceso) throws DAOException {

        // List notasInf = new ArrayList();
        Turno turnoHijo = null;
        TurnoEnhanced turnoNuevo = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        JDOLiquidacionesDAO liquidacionesDAO = new JDOLiquidacionesDAO();
        JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            // Se obtiene el objeto persistente correspondiente al usuario
            UsuarioEnhanced usuarioEnh = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);

            // Creo una nueva solicitud con una liquidaci?n con sus pagos y valores en 0.
            PagoEnhanced pagoNuevo = new PagoEnhanced();
            pagoNuevo.setUsuario(usuarioEnh);
            pagoNuevo.setFecha(new Date());

            LiquidacionEnhanced liquidacionNueva = new LiquidacionTurnoCorreccionEnhanced();
            liquidacionNueva.setUsuario(usuarioEnh);

            SolicitudCorreccionEnhanced solicitudNueva = new SolicitudCorreccionEnhanced();
            //solicitudNueva.addLiquidacion(liquidacionNueva);

            ProcesoEnhancedPk procesoArranqueId = new ProcesoEnhancedPk();
            procesoArranqueId.idProceso = idProceso;

            ProcesoEnhancedPk procesoEnhId = new ProcesoEnhancedPk();

            if (idProceso == Long.parseLong(CProceso.PROCESO_CORRECCION) || idProceso == Long.parseLong(CProceso.PROCESO_CORRECCION_CALIFICACION)) {
                procesoEnhId.idProceso = Long.parseLong(CProceso.PROCESO_CORRECCIONES);
            } else {
                procesoEnhId.idProceso = idProceso;
            }

            ProcesoEnhanced procesoEnh = (ProcesoEnhanced) pm.getObjectById(procesoEnhId, true);
            ProcesoEnhanced procesoArranque = (ProcesoEnhanced) pm.getObjectById(procesoArranqueId, true);;

            procesoEnhId.idProceso = idProceso;

            CirculoEnhancedPk circuloEnhId = new CirculoEnhancedPk();
            circuloEnhId.idCirculo = turnoPadre.getIdCirculo();

            CirculoEnhanced circuloEnh = (CirculoEnhanced) pm.getObjectById(circuloEnhId, true);

            // Se copian los datos de antiguo sistema y los folios asociados de la solicitud del
            // folio padre a la nueva solicitud
            Solicitud solicitud = turnoPadre.getSolicitud();

            CiudadanoEnhancedPk ciudadanoEnhId = new CiudadanoEnhancedPk();
            ciudadanoEnhId.idCiudadano = solicitud.getCiudadano().getIdCiudadano();

            CiudadanoEnhanced ciudadanoEnh = (CiudadanoEnhanced) pm.getObjectById(ciudadanoEnhId, true);

            TipoRecepcionPeticionEnhancedPk tipoRecepcionEnhId = new TipoRecepcionPeticionEnhancedPk();
            tipoRecepcionEnhId.idTipoRecepcionPeticion = CTipoRecepcion.IDSOLICITUDPERSONAL;

            TipoRecepcionPeticionEnhanced tipoRecepcionEnh
                    = (TipoRecepcionPeticionEnhanced) pm.getObjectById(tipoRecepcionEnhId, true);

            SolicitudEnhancedPk solicitudEnhId = new SolicitudEnhancedPk();
            solicitudEnhId.idSolicitud = solicitud.getIdSolicitud();

            SolicitudEnhanced solicitudEnh = solicitudesDAO.getSolicitudByID(solicitudEnhId, pm);

            DatosAntiguoSistemaEnhanced datosAntiguoSistemaEnh = solicitudEnh.getDatosAntiguoSistema();
            DatosAntiguoSistemaEnhanced datosAntiguoSistemaNuevo = null;
            if (datosAntiguoSistemaEnh != null) {
                datosAntiguoSistemaNuevo = new DatosAntiguoSistemaEnhanced();

                datosAntiguoSistemaNuevo.setComentario(datosAntiguoSistemaEnh.getComentario());
                if (datosAntiguoSistemaNuevo != null && datosAntiguoSistemaNuevo.getDocumento() != null
                        && solicitudEnh.getCirculo() != null) {
                    datosAntiguoSistemaEnh.getDocumento().setCirculo(solicitudEnh.getCirculo().getIdCirculo());
                }
                datosAntiguoSistemaNuevo.setDocumento(datosAntiguoSistemaEnh.getDocumento());
                datosAntiguoSistemaNuevo.setFechaCreacion(datosAntiguoSistemaEnh.getFechaCreacion());
                datosAntiguoSistemaNuevo.setLibroAnio(datosAntiguoSistemaEnh.getLibroAnio());
                datosAntiguoSistemaNuevo.setLibroNumero(datosAntiguoSistemaEnh.getLibroNumero());
                datosAntiguoSistemaNuevo.setLibroPagina(datosAntiguoSistemaEnh.getLibroPagina());
                datosAntiguoSistemaNuevo.setLibroTipo(datosAntiguoSistemaEnh.getLibroTipo());
                datosAntiguoSistemaNuevo.setTomoAnio(datosAntiguoSistemaEnh.getTomoAnio());
                datosAntiguoSistemaNuevo.setTomoMunicipio(datosAntiguoSistemaEnh.getTomoMunicipio());
                datosAntiguoSistemaNuevo.setTomoNumero(datosAntiguoSistemaEnh.getTomoNumero());
                datosAntiguoSistemaNuevo.setTomoPagina(datosAntiguoSistemaEnh.getTomoPagina());
            }

            List solicitudesFoliosEnh = solicitudEnh.getSolicitudFolios();
            List solicitudesFolios = new ArrayList();
            for (Iterator iteradorSolicitudes = solicitudesFoliosEnh.iterator(); iteradorSolicitudes.hasNext();) {
                SolicitudFolioEnhanced solicitudFolio = (SolicitudFolioEnhanced) iteradorSolicitudes.next();
                SolicitudFolioEnhanced solicitudFolioNueva = new SolicitudFolioEnhanced();
                solicitudFolioNueva.setFolio(solicitudFolio.getFolio());
                solicitudFolioNueva.setIdMatricula(solicitudFolio.getIdMatricula());
                solicitudFolioNueva.setIdBusquedaConsulta(solicitudFolio.getIdBusquedaConsulta());
                solicitudFolioNueva.setMarcado(solicitudFolio.isMarcado());
                solicitudFolioNueva.setOficio(solicitudFolio.getOficio());
                solicitudFolioNueva.setRecienCreadoAntiguoSistema(solicitudFolio.isRecienCreadoAntiguoSistema());
                solicitudFolioNueva.setUsuario(solicitudFolio.getUsuario());
                solicitudesFolios.add(solicitudFolioNueva);
            }

            solicitudNueva.setSolicitudFolios(solicitudesFolios);
            solicitudNueva.setDatosAntiguoSistema(datosAntiguoSistemaNuevo);
            solicitudNueva.setProceso(procesoEnh);
            solicitudNueva.setCirculo(circuloEnh);
            solicitudNueva.setCiudadano(ciudadanoEnh);
            solicitudNueva.setFecha(new Date());
            solicitudNueva.setUsuario(usuarioEnh);
            solicitudNueva.setTipoRecepcionPeticion(tipoRecepcionEnh);
            solicitudEnh = solicitudesDAO.crearSolicitudCorreccion(solicitudNueva, pm);

            liquidacionNueva = liquidacionesDAO.addLiquidacionToSolicitud(liquidacionNueva, solicitudEnh, pm);

            pagoNuevo.setIdSolicitud(solicitudEnh.getIdSolicitud());
            pagoNuevo.setLiquidacion(liquidacionNueva);
            PagoEnhancedPk pagoEnhId = pagosDAO.crearPagoNulo(pagoNuevo, usuario, pm);
            pagoNuevo = (PagoEnhanced) pm.getObjectById(pagoEnhId, true);
            if (liquidacionNueva != null && liquidacionNueva.getCirculo() != null) {
                pagoNuevo.setCirculo(liquidacionNueva.getCirculo());
            }

            liquidacionNueva.setPago(pagoNuevo);
            pm.makePersistent(liquidacionNueva);

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            CirculoProcesoEnhancedPk circuloProcesoId = new CirculoProcesoEnhancedPk();
            circuloProcesoId.anio = String.valueOf(calendar.get(Calendar.YEAR));
            circuloProcesoId.idCirculo = turnoPadre.getIdCirculo();

            if (idProceso == Long.parseLong(CProceso.PROCESO_CORRECCION) || idProceso == Long.parseLong(CProceso.PROCESO_CORRECCION_CALIFICACION)) {
                circuloProcesoId.idProceso = Long.parseLong(CProceso.PROCESO_CORRECCIONES);
            } else {
                circuloProcesoId.idProceso = idProceso;
            }

            CirculoProcesoEnhanced circuloProceso = procesosDAO.getCirculoProcesoById(circuloProcesoId, pm);

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos
                    = fasesDAO.obtenerFaseInicial(new Long(circuloProceso.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(), "", "", "");

            FaseEnhanced faseArranque = FaseEnhanced.enhance(faseInicial);

            // Obtengo el secuencial para el turno
            String idTurno = "" + (circuloProceso.getLastIdTurno() + 1);
            circuloProceso.setLastIdTurno(circuloProceso.getLastIdTurno() + 1);

            // Creo el turno dependiente
            turnoNuevo = new TurnoEnhanced();
            turnoNuevo.setIdFase(faseArranque.getID());
            turnoNuevo.setAnio(circuloProcesoId.anio);
            turnoNuevo.setFechaInicio(new Date());
            turnoNuevo.setCirculoproceso(circuloProceso);
            turnoNuevo.setSolicitud(solicitudNueva);
            turnoNuevo.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            turnoNuevo.setIdTurno(idTurno);
            turnoNuevo.setIdWorkflow(circuloProceso.getAnio() + "-" + circuloProceso.getIdCirculo() + "-"
                    + circuloProceso.getIdProceso() + "-" + idTurno);
            turnoNuevo.setUsuarioDestino(usuarioEnh);
            turnoNuevo.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);

            turnoNuevo.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            pm.makePersistent(turnoNuevo);

            // Creo la dependencia del turno
            TurnoEnhancedPk turnoPadreId = new TurnoEnhancedPk();
            turnoPadreId.anio = turnoPadre.getAnio();
            turnoPadreId.idCirculo = turnoPadre.getIdCirculo();
            turnoPadreId.idProceso = turnoPadre.getIdProceso();
            turnoPadreId.idTurno = turnoPadre.getIdTurno();

            TurnoEnhanced turnoPadreEnh = (TurnoEnhanced) pm.getObjectById(turnoPadreId, false);

            TurnoDerivadoEnhanced turnoDerivado = new TurnoDerivadoEnhanced();
            turnoDerivado.setTurnoPadre(turnoPadreEnh);
            turnoDerivado.setTurnoHijo(turnoNuevo);

            pm.makePersistent(turnoDerivado);

            pm.currentTransaction().commit();

            // Se crea la instancia dentro del Workflow
            makeTransientTurno(turnoNuevo, pm);

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        //Se hace por fuera del try/catch para que se ejecute cuando el pm este cerrado porque dentro
        //del metodo crearInstanciaWF se llama un toTransferObject de turnoNuevo
        crearInstanciaWF(turnoNuevo, null, usuario, null, null);

        if (turnoNuevo != null) {
            turnoHijo = (Turno) turnoNuevo.toTransferObject();
        }
        return turnoHijo;
    }

    /**
     * Agrega una Nota de actuaciones al turno dado.
     *
     * @param Identificador del turno <code>Turno.ID</code> a la que se va a
     * asociar la <code>NotaActuacion</code>
     * @param nota actuaci?n la <code>NotaActuacion</code> que va a ser asociada
     * al turno.
     * @return El resultado de la adici?n de la nota de actuaciones
     * administrativas.
     * @see gov.sir.core.negocio.modelo.TurnoPk
     * @see gov.sir.core.negocio.modelo.NotaActuacion
     * @throws <code>Throwable</code>
     */
    public boolean agregarNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL PROCESO DE CORRECCIONES O DE REGISTRO
            if (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_CORRECCIONES) && turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno NO es del proceso correcciones ni registro");
            }

            SolicitudEnhanced sol = turno.getSolicitud();

            SolicitudCorreccionEnhanced solCor = null;
            SolicitudRegistroEnhanced solReg = null;
            //SE INSTANCIA UNA VARIABLE SEGUN EL TIPO DE SOLICITUD
            if (sol instanceof SolicitudCorreccionEnhanced) {
                solCor = (SolicitudCorreccionEnhanced) turno.getSolicitud();
            } else {
                solReg = (SolicitudRegistroEnhanced) turno.getSolicitud();
            }

            if (solCor != null) {
                NotaActuacionEnhanced notaActuacionEnhanced = NotaActuacionEnhanced.enhance(notaActuacion);

                UsuarioEnhanced usuarioEnhanced = this.getUsuarioByID(new UsuarioEnhancedPk("" + notaActuacion.getUsuario().getIdUsuario()), pm);
                int idNota = solCor.getLastIdNotaActuacion() + 1;
                notaActuacionEnhanced.setIdNotaActuacion("" + idNota);
                notaActuacionEnhanced.setUsuario(usuarioEnhanced);
                notaActuacionEnhanced.setSolicitud(solCor);
                pm.makePersistent(notaActuacionEnhanced);
                solCor.setLastIdNotaActuacion(idNota);
                //solCor.addNotasActuacione(notaActuacionEnhanced);
            }

            if (solReg != null) {
                NotaActuacionEnhanced notaActuacionEnhanced = NotaActuacionEnhanced.enhance(notaActuacion);
                UsuarioEnhanced usuarioEnhanced = this.getUsuarioByID(new UsuarioEnhancedPk("" + notaActuacion.getUsuario().getIdUsuario()), pm);
                int idNota = solReg.getLastIdNotaActuacion() + 1;
                notaActuacionEnhanced.setIdNotaActuacion("" + idNota);
                notaActuacionEnhanced.setUsuario(usuarioEnhanced);
                notaActuacionEnhanced.setSolicitud(solReg);
                pm.makePersistent(notaActuacionEnhanced);
                solReg.setLastIdNotaActuacion(idNota);
                //solReg.addNotasActuacione(notaActuacionEnhanced);
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Actualiza una Nota de actuaciones al turno dado.
     *
     * @param Identificador del turno <code>Turno.ID</code> al que se va a
     * actualizar la <code>NotaActuacion</code>
     * @param nota actuaci?n la <code>NotaActuacion</code> que va a ser
     * actualizada al turno.
     * @return El resultado de la actualizaci?n de la nota de actuaciones
     * administrativas.
     * @see gov.sir.core.negocio.modelo.TurnoPk
     * @see gov.sir.core.negocio.modelo.NotaActuacion
     * @throws <code>Throwable</code>
     */
    public boolean actualizarNotaActuacion(TurnoPk turnoID, NotaActuacion notaActuacion) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            //SE VERIFICA EL PROCESO DE CORRECCIONES O DE REGISTRO
            if (turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_CORRECCIONES) && turno.getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno NO es del proceso correcciones ni registro");
            }

            SolicitudEnhanced sol = turno.getSolicitud();

            SolicitudCorreccionEnhanced solCor = null;
            SolicitudRegistroEnhanced solReg = null;
            //SE INSTANCIA UNA VARIABLE SEGUN EL TIPO DE SOLICITUD
            if (sol instanceof SolicitudCorreccionEnhanced) {
                solCor = (SolicitudCorreccionEnhanced) turno.getSolicitud();
            } else {
                solReg = (SolicitudRegistroEnhanced) turno.getSolicitud();
            }

            NotaActuacionEnhancedPk naID = new NotaActuacionEnhancedPk();
            naID.idNotaActuacion = notaActuacion.getIdNotaActuacion();

            if (solCor != null) {
                naID.idSolicitud = solCor.getIdSolicitud();
            }

            if (solReg != null) {
                naID.idSolicitud = solReg.getIdSolicitud();
            }

            NotaActuacionEnhanced notaActuacionEnhanced = getNotaActuacion(naID, pm);

            if (notaActuacionEnhanced == null) {
                throw new DAOException("No se encontr? la nota que se quiere actualizar.");
            } else {
                notaActuacionEnhanced.setEstado(notaActuacion.getEstado());
                notaActuacionEnhanced.setNota(notaActuacion.getNota());
            }

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, t.getMessage());
            throw new DAOException(t.getMessage(), t);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.close();
            }

        }

    }

    /**
     * Obtiene una Nota de Actuaciones Administrativas
     *
     * @param naID Identificador de la Nota Actuaci?n.
     * @param pm PersistenceManager de la transaccion
     * @return Nota Actuaci?n
     * @throws DAOException
     */
    protected NotaActuacionEnhanced getNotaActuacion(NotaActuacionEnhancedPk naID,
            PersistenceManager pm) throws DAOException {
        NotaActuacionEnhanced rta = null;

        if ((naID.idNotaActuacion != null) && (naID.idSolicitud != null)) {
            try {
                rta = (NotaActuacionEnhanced) pm.getObjectById(naID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }
    
    
    public String getUltimaSolicitudLiquidacion(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, CirculoPk idCirculo) throws DAOException {
       
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        StringBuffer sqlStatement;
        sqlStatement = new StringBuffer(8192);

        sqlStatement.append("SELECT DISTINCT MAX(LIQ.ID_SOLICITUD) ");
        sqlStatement.append("FROM SIR_OP_SOL_ASOCIADA SOL_ASOC ");
        sqlStatement.append("INNER JOIN SIR_OP_LIQUIDACION LIQ ON SOL_ASOC.ID_SOLICITUD = LIQ.ID_SOLICITUD ");
        sqlStatement.append("WHERE to_date(SOL_ASOC.SLSC_FECHA_CREACION,'DD/MM/YY') = to_date(:1,'DD/MM/YY') AND ");
        sqlStatement.append("LIQ.ID_USUARIO = :2 AND ");
        sqlStatement.append("LIQ.ID_CIRCULO = :3 ");

        String solicitud = null;
        String result = null;
        int anio = Calendar.getInstance().get(Calendar.YEAR),
                mes = Calendar.getInstance().get(Calendar.MONTH),
                dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        try {

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            String fechaActual = dia + "/" + (mes + 1) + "/" + anio;
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(sqlStatement.toString());
            ps.setString(1, fechaActual);
            ps.setLong(2, idUsuario.idUsuario);
            ps.setString(3, idCirculo.idCirculo);

            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString(1);
            }

            if (result != null) {
                solicitud = result;
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
        return solicitud;
    }
    
    
    
    
    /**
     * @see
     * gov.sir.hermod.dao.TurnosDAO#getUltimoTurnoProcesoUsuario(gov.sir.core.negocio.modelo.Usuario.UsuarioPk,
     * gov.sir.core.negocio.modelo.Proceso.ProcesoPk)
     */
    public Turno getUltimoTurnoProcesoUsuario(gov.sir.core.negocio.modelo.UsuarioPk idUsuario, gov.sir.core.negocio.modelo.ProcesoPk idProceso, CirculoPk idCirculo) throws DAOException {
        long currentMilliseconds = 0;
        long antes = 0;
        long despues = 0;
        currentMilliseconds = System.currentTimeMillis();
        Log.getInstance().debug(JDOTurnosDAO.class, "INICIALIZANDO EL METODO getUltimoTurnoProcesoUsuario: " + System.currentTimeMillis());
        PersistenceManager pm = AdministradorPM.getPM();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psUltimo = null;
        ResultSet rsUltimo = null;
        VersantPersistenceManager jdoPM = null;

        StringBuffer sqlStatement;
        sqlStatement = new StringBuffer(8192);

        sqlStatement.append(" SELECT MAX(TO_NUMBER (th.id_turno)) AS TRNO_ID_WORKFLOW ");
        sqlStatement.append(" FROM sir_op_turno_historia th ");
        sqlStatement.append(" WHERE ");
        sqlStatement.append(" th.id_circulo = :1 ");
        sqlStatement.append(" AND th.anio = :2 ");
        sqlStatement.append(" AND th.id_proceso = :3 ");
        /**
         * Caso Mantis: 7114. Acta Requerimiento: 218 - Pantallas
         * administrativas - Reimpresion Constancia y Recibos Fotocopias.
         *
         * @author: Ellery David Robles G?mez.
         * @change: Se comentarea la linea que recibe el parametro
         * id_turno_historia, presenta conflicto en la busqueda del ultimo turno
         * generado en la tabla SIR_OP_TURNO_HISTORIA.
         */
//            sqlStatement.append(" AND th.id_turno_historia = '1' ");
        sqlStatement.append(" AND th.id_usuario = :4 ");

        StringBuffer sqlStatement1;
        sqlStatement1 = new StringBuffer(8192);
        /**
         * Caso Mantis: 7114. Acta Requerimiento: 218 - Pantallas
         * administrativas - Reimpresion Constancia y Recibos Fotocopias.
         *
         * @author: Ellery David Robles G?mez.
         * @change: Se corrige la selecci?n del campo id_turnos en la consulta
         * de la tabla SIR_OP_ULTIMO_TURNO_USUARIO para que nos arroje el turno
         * de mayor valor en la secuencia generada.
         */
        sqlStatement1.append(" SELECT MAX(TO_NUMBER (ut.id_turno)) AS TRNO_ID_WORKFLOW ");
        sqlStatement1.append(" FROM SIR_OP_ULTIMO_TURNO_USUARIO ut ");
        sqlStatement1.append(" WHERE ");
        sqlStatement1.append(" ut.id_circulo = :1 ");
        sqlStatement1.append(" AND ut.anio = :2 ");
        sqlStatement1.append(" AND ut.id_proceso = :3 ");
        sqlStatement1.append(" AND ut.id_usuario = :4 ");

        Turno turno = null;
        String idWorkflow = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            antes = System.currentTimeMillis();
            Log.getInstance().debug(JDOTurnosDAO.class, "ANTES DE OBTENER REALIZAR LA CONSULTA: " + antes);

            String anio = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
            String itemKey = "";

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            /**
             * Primero se verifica si existe el registro en tabla
             * SIR_OP_ULTIMO_TURNO_USUARIO Si no existe se hace la consulta
             * sobre sir_op_turno_historia
             */
            /**
             * Consulta sobre SIR_OP_ULTIMO_TURNO_USUARIO
             */
            psUltimo = connection.prepareStatement(sqlStatement1.toString());
            psUltimo.setString(1, idCirculo.idCirculo);
            psUltimo.setInt(2, Calendar.getInstance().get(Calendar.YEAR));
            psUltimo.setLong(3, idProceso.idProceso);
            psUltimo.setLong(4, idUsuario.idUsuario);

            rsUltimo = psUltimo.executeQuery();

            if (rsUltimo.next()) {
                idWorkflow = rsUltimo.getString(1);
            }

            if (idWorkflow == null) {

                /**
                 * No se encontr? registro, se hace consulta antigua
                 */
                Log.getInstance().debug(JDOTurnosDAO.class, "==> [Hermod.JDOTurnosDAO][getUltimoTurnoProcesoUsuario]"
                        + " NO ENCONTRO REGISTRO EN TABLA "
                        + "SIR_OP_ULTIMO_TURNO_USUARIO "
                        + "- Realizando consulta ANTIGUA. <==");
                ps = connection.prepareStatement(sqlStatement.toString());

                ps.setString(1, idCirculo.idCirculo);
                ps.setString(2, anio);
                ps.setLong(3, idProceso.idProceso);
                ps.setLong(4, idUsuario.idUsuario);

                rs = ps.executeQuery();

                while (rs.next()) {
                    idWorkflow = rs.getString(1);
                    //itemKey = anio+"-"+idCirculo.idCirculo+"-"+idProceso.idProceso+"-"+idWorkflow;
                }
            }

            itemKey = anio + "-" + idCirculo.idCirculo + "-" + idProceso.idProceso + "-" + idWorkflow;

            Log.getInstance().debug(JDOTurnosDAO.class, "[JDOTurnosDAO][getUltimo] ITEMKEY  "
                    + ": " + itemKey);

            if (itemKey != null) {
                turno = getTurnoByWFId(itemKey);
                
            }
            jdoPM.currentTransaction().commit();
            despues = System.currentTimeMillis() - antes;
            Log.getInstance().debug(JDOTurnosDAO.class, "DESPUES DE REALIZAR LA CONSULTA: " + despues);
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
                if (rsUltimo != null) {
                    rsUltimo.close();
                }

                if (rs != null) {
                    rs.close();
                }

                if (psUltimo != null) {
                    psUltimo.close();
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
        Log.getInstance().debug(JDOTurnosDAO.class, "EL TOTAL DE TIEMPO TOMADO PARA EL METODO getUltimoTurnoProcesoUsuario: " + (currentMilliseconds - System.currentTimeMillis()));
        return turno;
    }

    /**
     * Obtiene el ultimo turno por usuario que haya registrado el pago de mayor
     * valor
     *
     * @param idUsuario
     * @param idProceso
     * @param idCirculo
     * @return Turno
     * @throws DAOException
     */
    public Turno getUltimoTurnoMayorValorUsuario(
            gov.sir.core.negocio.modelo.UsuarioPk idUsuario,
            gov.sir.core.negocio.modelo.ProcesoPk idProceso,
            CirculoPk idCirculo) throws DAOException {
        Turno turno = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = null;
        VersantPersistenceManager jdoPM = null;
        StringBuffer consultaSql = new StringBuffer();
        String itemKey = null;
        String idTurno = null;
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            consultaSql.append(" SELECT MAX(TO_NUMBER (th.id_turno)) AS TRNO_ID_WORKFLOW ");
            consultaSql.append(" FROM sir_op_turno_historia th ");
            consultaSql.append(" WHERE ");
            consultaSql.append(" th.id_circulo = :1 ");
            consultaSql.append(" AND th.anio = :2 ");
            consultaSql.append(" AND th.id_proceso = :3 ");
            consultaSql.append(" AND th.trhs_id_fase = :4 ");
            consultaSql.append(" AND th.id_usuario = :5 ");

            ps = connection.prepareStatement(consultaSql.toString());
            ps.setString(1, idCirculo.idCirculo);
            ps.setInt(2, anio);
            ps.setLong(3, idProceso.idProceso);
            ps.setString(4, CFase.PMY_NOTIFICAR_FUNCIONARIO);
            ps.setLong(5, idUsuario.idUsuario);

            rs = ps.executeQuery();
            if (rs.next()) {
                idTurno = rs.getString(1);
                if (idTurno != null) {
                    itemKey = anio + "-" + idCirculo.idCirculo + "-" + idProceso.idProceso + "-" + idTurno;
                }
            }
            if (itemKey != null) {
                turno = getTurnoByWFId(itemKey);
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
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (connection != null) {
                    connection.close();
                }
                if (jdoPM != null) {
                    jdoPM.close();
                    jdoPM = null;
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return turno;
    }

    /**
     * @see
     * gov.sir.hermod.interfaz.HermodServiceInterface#getImprimiblesPendientesByWfId(
     * java.lang.String ,java.lang.String )
     */
    public List getImprimiblesPendientesByWfId(String turno_WfId, String tipoImprimibleId) throws DAOException {

        // TODO: modificar, no se esta teniendo en cuenta el tipo de imprimible
        List local_Result;
        local_Result = new ArrayList();

        PersistenceManager pm; //PersistenceManager
        pm = AdministradorPM.getPM();

        try {
            String p_IdWorkflow = turno_WfId;
            String p_TipoImprimible = tipoImprimibleId;

            pm.currentTransaction().begin();
            Query query = pm.newQuery(ImprimibleEnhanced.class);
            query.declareVariables("TurnoEnhanced turnoEnhanced;");
            query.setOrdering("fechaCreacion descending, tipoImprimible descending");

            query.declareParameters("String p_IdWorkflow");

            StringBuffer local_Filter;
            local_Filter = new StringBuffer(1024);
            local_Filter.append("this.turno ==  p_IdWorkflow ");
            //local_Filter.append( "&& turnoEnhanced.idWorkflow == this.turno " );
            local_Filter.append("&& this.numeroImpresiones == 0 ");

            if (null != tipoImprimibleId) {
                local_Filter.append("&& this.tipoImprimible == " + "'" + tipoImprimibleId + "'");
            }

            query.setFilter(
                    local_Filter.toString()
            );

            Collection col = (Collection) query.execute(p_IdWorkflow);
            ImprimibleEnhanced imprimibleEnhanced;
            //Imprimible imprimible;
            if (null != col) {

                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    imprimibleEnhanced = (ImprimibleEnhanced) iter.next();
                    pm.makeTransient(imprimibleEnhanced);
                    //imprimible = (Imprimible)imprimibleEnhanced.toTransferObject();
                    local_Result.add(imprimibleEnhanced);
                    //	    Log.getInstance().debug(JDOTurnosDAO.class,imprimibleenhanced);

                } // for

            } // if
            query.closeAll();
            pm.currentTransaction().commit();

        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            t.printStackTrace();
            Log.getInstance().error(JDOTurnosDAO.class, t.getMessage(), t);
            throw new DAOException(t.getMessage(), t);

        } finally {
            pm.close();
        }

        local_Result = TransferUtils.makeTransferAll(local_Result);
        return local_Result;

    }	// end-method

    /**
     * Permite marcar o desmarcar el turno para indicar que al turno se le
     * interpuso un recurso o revocatoria directa.
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean actualizarMarcaRevocatoriaTurno(Turno turno) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turnoEnhanced = this.getTurnoByID(new TurnoEnhancedPk(turno.getIdWorkflow()), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            turnoEnhanced.setRevocatoria(turno.isRevocatoria());
            turnoEnhanced.setModoBloqueo(turno.getModoBloqueo());
            turnoEnhanced.setFechaFin(turno.getFechaFin());

            pm.makePersistent(turnoEnhanced);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }

    }

    /**
     * Permite marcar o desmarcar el turno como turno de certificados nacional
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean actualizarMarcaTurnoNacional(Turno turno) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turnoEnhanced = this.getTurnoByID(new TurnoEnhancedPk(turno.getIdWorkflow()), pm);

            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }

            turnoEnhanced.setNacional(turno.isNacional());

            pm.makePersistent(turnoEnhanced);

            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }

    }
    
    public void reorderRecursos(String turnoWF) throws DAOException {
        String[] turno = turnoWF.split("-");
        VersantPersistenceManager pm = (VersantPersistenceManager) AdministradorPM.getPM();
        Connection con = null;
        OracleCallableStatement cst = null;

        try {
            pm.currentTransaction().begin();
            con = pm.getJdbcConnection(null);

            //Lógica de invocación al pl
            cst = (OracleCallableStatement) con.prepareCall("{call SP_REORDER_ID_RECURSO(?,?,?,?)}");
            cst.setString(1, turno[0]);
            cst.setString(2, turno[1]);
            cst.setString(3, turno[2]);
            cst.setString(4, turno[3]);

            // ejecución del PL en la BD
            cst.execute();

            pm.currentTransaction().commit();

        } catch (SQLException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage());
         } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }

            pm.close();
        }

    }
    
    /**
     * Realiza la consulta de los turnos que fueron bloqueados y que est?n para
     * ser reanotados.
     *
     * @param circulo
     * @return
     * @throws Throwable
     */
    public List consultarTurnosAReanotar(Circulo circulo) throws DAOException {

        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        if (circulo == null || circulo.getIdCirculo() == null) {
            throw new DAOException("Debe ingresar un circulo.");
        }

        String idCirculo = circulo.getIdCirculo();

        try {
            Turno t = null;
            pm.currentTransaction().begin();
            Query query = pm.newQuery(TurnoEnhanced.class);
            query.setOrdering("idWorkflow ascending");
            query.setFilter("this.idCirculo==\"" + idCirculo + "\" && this.revocatoria == true");

            Collection results = (Collection) query.execute();
            Iterator it = results.iterator();

            while (it.hasNext()) {
                TurnoEnhanced obj = (TurnoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            query.closeAll();
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e);

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            lista = TransferUtils.makeTransferAll(lista);
        }

        return lista;

    }

    /**
     * Permite colocar en la fase calificaci?n, un turno que ya se encuentra
     * finalizado.
     *
     * @param turno
     * @return
     * @throws Throwable
     */
    public boolean reanotarTurno(Turno turno, Hashtable params) throws DAOException {
        boolean rta = false;

        Hashtable parametros = new Hashtable();
        PersistenceManager pm; //PersistenceManager
        pm = AdministradorPM.getPM();
        String activity = (String) params.get(Processor.ACTIVITY);
        String result = (String) params.get(Processor.RESULT);

        if (turno != null) {
            try {
                TurnoEnhancedPk tid = new TurnoEnhancedPk();
                tid.anio = turno.getAnio();
                tid.idCirculo = turno.getIdCirculo();
                tid.idProceso = turno.getIdProceso();
                tid.idTurno = turno.getIdTurno();
                TurnoEnhanced turnoEnhanced = (TurnoEnhanced) pm.getObjectById(new TurnoEnhancedPk(turno.getIdWorkflow()), true);

                if (turnoEnhanced == null) {
                    throw new DAOException("No se encontr? el turno.");
                }

                Processor p = HermodWFFactory.getFactory().getProcessor();
                long idParam = 0;
                String tipoMensajeParam = Message.AL_WORKFLOW;
                String tipoProcesoParam = Message.REVIVIR_PROCESO;
                parametros.put(Processor.ITEM_KEY, turno.getIdWorkflow());
                parametros.put(Processor.ACTIVITY, activity);
                parametros.put(Processor.COMMAND, Message.SKIP);
                parametros.put(Processor.RESULT, result);
                Message m = new Message(idParam, tipoMensajeParam, tipoProcesoParam, parametros);
                p.process(m);

            } catch (WFException e) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            } catch (JDOObjectNotFoundException e) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }
                rta = false;
            } catch (JDOException e) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }
                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } catch (DAOException e) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                throw e;
            } catch (Throwable e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);

                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            } finally {
                pm.close();
            }
        }

        return rta;
    }

    /**
     * Obtiene la lista de turnos a partir de una estaci?n y una fase. Este
     * m?todo devuelve el turno con un turno historia en d?nde se tiene la
     * informaci?n de la fase y la estaci?n asignada.
     *
     * @param estacion <code>Estacion</code> sobre la cual se buscan los turnos.
     * @param fase <code>Fase</code> sobre la cual se buscan los turnos.
     * @return una lista de objetos <code>Turno</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Circulo
     * @see gov.sir.core.negocio.modelo.Usuario
     * @throws <code>Throwable</code>
     */
    public List getTurnosAReasignar(Estacion estacion, Fase fase) throws DAOException {

        //PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String estacionSt = estacion.getEstacionId();
        String faseSt = fase.getID();

        String consulta
                = " select  trej_item_key, trej_fase, trej_estacion "
                + " from sir_op_turno a, "
                + "  	sir_op_turno_ejecucion b "
                + " where trno_id_workflow = trej_item_key "
                + "	and trej_fase = ? "
                + "   and trej_estacion = ? "
                + "   and a.trno_anulado = 'N' "
                + " order by trno_fecha_inicio, trej_item_key ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, faseSt);
            ps.setString(2, estacionSt);

            rs = ps.executeQuery();

            while (rs.next()) {
                Turno turno = new Turno();
                turno.setIdWorkflow(rs.getString(1));
                TurnoHistoria th = new TurnoHistoria();
                th.setFase(rs.getString(2));
                th.setNombreFase(th.getFase());
                th.setIdAdministradorSAS(rs.getString(3));
                turno.addHistorial(th);
                rta.add(turno);
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
    
    public boolean addReasignacion(Turno turno) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        int limit = this.getLimiteReasignacion(turno);
        if(limit < 2){
        limit++;
        String sql = "UPDATE SIR_OP_TURNO SET TRNO_REASIGNACION = '"+limit+"' WHERE TRNO_ID_WORKFLOW = '"+turno.getIdWorkflow()+"'";
        
        Statement s = null;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DAOException("Error SQL: " + e, e);
        } finally {

            try {
                if (s != null) {
                    s.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(JDOTurnosDAO.class, e);
            }

        }
            return true;
        }else{
            return false;  
        }
        
    }
    
    public int getLimiteReasignacion(Turno turno) throws DAOException {
        String attempt = "";
        int limit = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT COUNT(*) FROM SIR_OP_CONTROL_REASIGNACION" 
                  + " WHERE CTRL_FASE = '"+turno.getIdFase()+"' AND CTRL_ID_WORKFLOW = '"+turno.getIdWorkflow()+"'"
                  + " AND CTRL_ACTIVO = '1'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            attempt = (String) rs.getString(1);
            }
            
            if(attempt != null && !attempt.isEmpty()){
                limit = Integer.parseInt(attempt);
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

        return limit;
    }
    
    public int getReasignacion() throws DAOException {
        String attempt = "";
        int limit = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT LKCD_VALOR FROM SIR_OP_LOOKUP_CODES " +
                         " WHERE ID_TIPO = 'LIMITE_REASIGNACION'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            attempt = (String) rs.getString(1);
            }
            
            if(attempt != null && !attempt.isEmpty()){
                limit = Integer.parseInt(attempt);
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

        return limit;
    }
    
    public String getCopiaImp(String idCirculo) throws DAOException {
        String attempt = "";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT CRCL_COPIA_IMP FROM SIR_NE_CIRCULO " +
                         " WHERE ID_CIRCULO = '"+idCirculo+"'";
       
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            attempt = (String) rs.getString(1);
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

        return attempt;
    }
    
    public int getNumNotasInformativas(Turno turno, String tipoNota) throws DAOException {
        String attempt = "";
        int numNotas = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT COUNT(*) FROM SIR_OP_NOTA " +
                " WHERE ANIO='"+turno.getAnio()+"' AND ID_CIRCULO='"+turno.getIdCirculo()+"' AND ID_PROCESO = '"+turno.getIdProceso()+"' AND ID_TURNO='"+turno.getIdTurno()+"' AND ID_TIPO_NOTA='"+tipoNota+"'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            attempt = (String) rs.getString(1);
            }
            
            if(attempt != null && !attempt.isEmpty()){
                numNotas = Integer.parseInt(attempt);
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

        return numNotas;
    }
    
    public List isMatriculaNotificacionDev(String idMatricula) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        List turnos = new ArrayList();
        String sql;
        sql = "   SELECT TRNO_ID_WORKFLOW FROM SIR_OP_SOLICITUD_FOLIO F " +
            " INNER JOIN SIR_OP_TURNO T ON T.ID_SOLICITUD = F.ID_SOLICITUD " +
            " WHERE ID_MATRICULA = '"+idMatricula+"' " +
            " AND TRNO_ID_FASE IN ('NOT_NOTA_DEVOLUTIVA','NOT_NOTA_NOTIFICADA','NOT_RECURSOS_NOTA')";
            try{
            
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();

            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            
            while(rs.next()){
                turnos.add(rs.getString(1));
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
        return turnos;
    }
    
     public String getEstacionFromRevisor(Turno turno) throws DAOException {
        String estacion = "";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM (" +
                "SELECT TRHS_ID_ADMINISTRADOR_SAS FROM SIR_OP_TURNO_HISTORIA" +
                " WHERE ANIO='"+turno.getAnio()+"' AND ID_CIRCULO='"+turno.getIdCirculo()+"' AND ID_PROCESO='"+turno.getIdProceso()+"' AND ID_TURNO='"+turno.getIdTurno()+"'" +
                " AND TRHS_ID_FASE='CAL_CALIFICACION'" +
                " AND TRHS_RESPUESTA='CONFRONTACION_CORRECTIVA')" +
                " WHERE ROWNUM <= 1";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
                estacion = (String) rs.getString(1);
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

        return estacion;
    }
     
      public String getEstacionFromRecursosNota(Turno turno) throws DAOException {
        String estacion = "";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM (" +
                "SELECT TRHS_ID_ADMINISTRADOR_SAS FROM SIR_OP_TURNO_HISTORIA" +
                " WHERE ANIO='"+turno.getAnio()+"' AND ID_CIRCULO='"+turno.getIdCirculo()+"' AND ID_PROCESO='"+turno.getIdProceso()+"' AND ID_TURNO='"+turno.getIdTurno()+"'" +
                " AND TRHS_ID_FASE='CAL_CALIFICACION'" +
                " AND TRHS_RESPUESTA='DEVOLUCION')" +
                " WHERE ROWNUM <= 1";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
                estacion = (String) rs.getString(1);
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

        return estacion;
    }
    
    
        public int getNotasNecesarias(Turno turno) throws DAOException {
        String attempt = "";
        int notas = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT COUNT(TRHS_ID_FASE) FROM SIR_OP_TURNO_HISTORIA " +
                " WHERE ANIO='"+turno.getAnio()+"' AND ID_CIRCULO='"+turno.getIdCirculo()+"' AND ID_PROCESO='"+turno.getIdProceso()+"' AND ID_TURNO='"+turno.getIdTurno()+"' "
              + " AND TRHS_ID_FASE='CAL_CONFRONTACION_CORRECTIVA'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            attempt = (String) rs.getString(1);
            }
            
            if(attempt != null && !attempt.isEmpty()){
                notas = Integer.parseInt(attempt);
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

        return notas;
    }
        
    /**
     * Actualiza la estaci?n que tiene un turno por otra nueva estaci?n.
     *
     * @param turno <code>Turno</code> sobre el cual se va a cambiar de estaci?n
     * que lo tiene.
     * @param estacionDestino <code>Estacion</code> a la que va a quedar
     * asignado un turno.
     * @return una lista de objetos <code>Turno</code>
     * @throws <code>Throwable</code>
     */
    public boolean reasignarTurno(Turno turno, Estacion estacionDestino) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();

            if (turno == null || turno.getIdWorkflow() == null) {
                throw new DAOException("No se especifico un turno a reasignar.");
            }

            //SE CAPTURA EL TURNO
            TurnoEjecucionEnhancedPk tid = new TurnoEjecucionEnhancedPk();
            tid.idWorkflow = turno.getIdWorkflow();
            TurnoEjecucionEnhanced turnoEjecucionEnhanced = (TurnoEjecucionEnhanced) pm.getObjectById(tid, true);

            if (turnoEjecucionEnhanced == null) {
                throw new DAOException("No se encontr? el turno.");
            }

            turnoEjecucionEnhanced.setEstacion(estacionDestino.getEstacionId());

            pm.makePersistent(turnoEjecucionEnhanced);
            
            
            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
            

            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } finally {
            pm.close();
        }
        

    }

    /**
     * Crea el turno de acuerdo a la informaci?n recibida en los par?metros.
     * <p>
     * M?todo privado llamado por este DAO y utilizado en forma transaccional.
     *
     * @return el identificador del objeto Turno creado.
     * @param cpe C?rculo Proceso asociado con el Turno que va a ser creado.
     * @param pId Identificador del Pago asociado con el Turno que va a ser
     * creado.
     * @param impresora Impresora en la que debe imprimirse el documento
     * generado.
     * @param user <code>Usuario</code> iniciador del <code>Proceso</code>
     * @param pm PersistenceManager de la Transacci?n.
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Turno
     * @throws <code>DAOException</code>
     */
    protected TurnoEnhancedPk crearTurnoFotocopias(CirculoProcesoEnhanced cpe,
            SolicitudEnhancedPk solId, String impresora, Usuario user, PersistenceManager pm) throws DAOException {

        TurnoEnhanced tr = new TurnoEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(solId, pm);
            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA + solId.idSolicitud);
            }

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            //TFS 7416: ELIMINACION DEL WORKFLOW
            //Se determina la fase de arranque del Proceso.
            InicioProcesos inicioProcesos
                    = fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(), "", "", "");

            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creaci?n del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setAnio(cp.getAnio());
            tr.setFechaInicio(fecha);
            tr.setCirculoproceso(cp);
            tr.setSolicitud(s);
            tr.setLastIdHistoria(0);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
            tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-"
                    + String.valueOf(cp.getIdProceso()) + "-"
                    + String.valueOf(cp.getLastIdTurno() + 1));
            cp.setLastIdTurno(cp.getLastIdTurno() + 1);
            tr.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            //Asociar el usuario Persistente.
            UsuarioEnhancedPk idUsuarioEnh = new UsuarioEnhancedPk();
            idUsuarioEnh.idUsuario = user.getIdUsuario();
            UsuarioEnhanced usuarioEnhanced = this.getUsuarioByID(idUsuarioEnh, pm);
            tr.setUsuarioDestino(usuarioEnhanced);

            pm.makePersistent(tr);

            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }


    /* public List
	    getTurnosFotocopias( Estacion estacion, Rol rol, Usuario usuario, Proceso proceso, Fase fase )
	    throws DAOException {

	      // Estrategia de cambios
	      // 1. Se consultan los turnos en la tabla TurnoEjecucion, donde la estaci?n y la fase sean las recibidas
	      //    como par?metros.
	      // 2. Se valida la sincronizaci?n de estos turnos con el wf.
	    	PersistenceManager pm = AdministradorPM.getPM();
	
	pm.currentTransaction().begin();
	
	
	 String procesoSt  = String.valueOf( proceso.getIdProceso() );
	         String estacionSt = estacion.getEstacionId();
	         String faseSt     = fase.getID();
	         List rta = new ArrayList();
	
	    	 try {
	  
	    	 
	    	 Query query = pm.newQuery(TurnoEnhanced.class);
	    	 query.declareVariables("TurnoEjecucionEnhanced turnoEjecucion");
	    	 query.declareParameters("String fase, String estacion");
	    	 query.declareParameters("String estacion, String fase");
	    	 
	    	 query.setFilter("turnoEjecucion.idWorkflow == this.idWorkflow && turnoEjecucion.fase == fase && turnoEjecucion.estacion == estacion");
	             query.setOrdering("idTurno ascending");
	            
	             Collection col = (Collection) query.execute(estacionSt, faseSt);
	             Iterator iter = col.iterator();
	             TurnoEnhanced turno=null;

	             while (iter.hasNext()) {
	                 turno = (TurnoEnhanced) iter.next();
	                 
	                 pm.makeTransient(turno);
	                 rta.add(turno);
	             }
	             
	             pm.currentTransaction().commit();
	 	 query.closeAll();
	         } catch (Throwable e) {
	             Log.getInstance().error(JDOTurnosDAO.class,e.getMessage(), e);
	             throw new DAOException(e.getMessage(), e);
	         } finally {
	             pm.close();
	         }

	         rta = TransferUtils.makeTransferAll(rta);

	         return rta;
	    }
	      	
     */
    /**
     * Permite cambiar la respuesta en el historial de turnos del ?ltimo turno
     * que tenga la fase especificada.
     *
     * @param turno
     * @fase fase del turno a cambiar
     * @respueta nuevo valor de la respuesta del turno
     * @return
     * @throws Throwable
     */
    public boolean modificarRespuestaUltimaFase(Turno turno, Fase fase, String respuesta)
            throws DAOException {
        boolean rta = false;

        PersistenceManager pm; //PersistenceManager
        pm = AdministradorPM.getPM();
        pm.currentTransaction().begin();

        if (turno != null) {
            try {
                TurnoEnhancedPk tid = new TurnoEnhancedPk();
                tid.anio = turno.getAnio();
                tid.idCirculo = turno.getIdCirculo();
                tid.idProceso = turno.getIdProceso();
                tid.idTurno = turno.getIdTurno();
                TurnoEnhanced turnoEnhanced = (TurnoEnhanced) pm.getObjectById(new TurnoEnhancedPk(turno.getIdWorkflow()), true);

                if (turnoEnhanced == null) {
                    throw new DAOException("No se encontr? el turno.");
                }

                List turnoHistoria = turnoEnhanced.getHistorials();
                TurnoHistoriaEnhanced turnoHistoriaEnFase = null;
                if (turnoHistoria != null) {
                    for (Iterator i = turnoHistoria.iterator(); i.hasNext();) {
                        TurnoHistoriaEnhanced turnohEnh = (TurnoHistoriaEnhanced) i.next();
                        if (turnohEnh.getFase().equals(fase.getID())) {
                            turnoHistoriaEnFase = turnohEnh;
                        }
                    }
                }

                if (turnoHistoriaEnFase != null) {
                    //pm.makeTransient(turnoHistoriaEnFase);
                    turnoHistoriaEnFase.setRespuesta(respuesta);
                    pm.currentTransaction().commit();
                } else {
                    throw new DAOException("El turno no tiene la fase especificada en historia");
                }
            } catch (Throwable t) {
                if (pm.currentTransaction().isActive()) {
                    pm.currentTransaction().rollback();
                }

                Log.getInstance().error(JDOTurnosDAO.class, t.getMessage());
                throw new DAOException(t.getMessage(), t);
            }
        }

        return rta;
    }

    /**
     * Elimina las notas informativas asociadas al turno, solo elimina la ?ltima
     * nota de la fase en donde se encuentra el turno en estos momentos
     *
     * @param turnoID
     * @throws DAOException
     */
    public void eliminarNotasInformativasUltimaFaseTurno(TurnoPk turnoID)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //SE CAPTURA EL TURNO
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);

            if (turno == null) {
                throw new DAOException("No se encontr? el turno con el ID indicado");
            }

            //SE OBTIENE EL LISTADO DE NOTAS DEL TURNO.
            List listaNotas = new ArrayList();
            listaNotas = turno.getNotas();

            //Se elimina del turno la ?ltima nota informativa que
            //est? en la fase actual del turno
            if (listaNotas != null && listaNotas.size() > 0) {
                NotaEnhanced nota = (NotaEnhanced) listaNotas.get(listaNotas.size() - 1);

                if (!nota.getTiponota().isDevolutiva()
                        && turno.getIdFase().equals(nota.getIdFase())) {
                    pm.deletePersistent(nota);
                }

            }
            //TERMINAR TRANSACCION
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    /**
     * Permite actualizar el Historial al reasignar el turno sin modificar su
     * contenido
     *
     * @param turno
     * @param turnoHistoria
     * @return
     * @throws DAOException
     */
    public boolean addTurnoHistoriaReasignacion(Turno turno, TurnoHistoria turnoHistoria) throws DAOException {
        TurnoEnhanced t = TurnoEnhanced.enhance(turno);
        //TurnoHistoriaEnhanced tr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            TurnoHistoriaEnhanced th = new TurnoHistoriaEnhanced();
            JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
            UsuarioEnhanced us;

            if (t != null) {
                try {
                    TurnoEnhancedPk tId = new TurnoEnhancedPk();
                    tId.anio = t.getAnio();
                    tId.idCirculo = t.getIdCirculo();
                    tId.idProceso = t.getIdProceso();
                    tId.idTurno = t.getIdTurno();

                    TurnoEnhanced tr = this.getTurnoByID(tId, pm);

                    if (tr == null) {
                        throw new DAOException("No se encontro el turno: "
                                + t.getIdTurno() + " para crear su historia");
                    }

                    //VALIDACION DEL USUARIO:
                    String usuario = turnoHistoria.getUsuario().getUsername();

                    if (usuario != null) {
                        us = variablesDAO.getUsuarioByLogin(usuario, pm);

                        if (us == null) {
                            throw new DAOException("No se encontro el usuario: "
                                    + usuario);
                        }

                        th.setUsuario(us);
                    } else {
                        throw new DAOException("NO ES POSIBLE CREAR EL TURNO HISTORIA, ES NECESARIO EL USUARIO");
                    }

                    //ACTUALIZAR EL USUARIO DEL TURNO HISTORIA INMEDIATAMENTE ANTERIOR.
                    TurnoHistoriaEnhancedPk thID = new TurnoHistoriaEnhancedPk();
                    thID.anio = turno.getAnio();
                    thID.idCirculo = turno.getIdCirculo();
                    thID.idProceso = turno.getIdProceso();
                    thID.idTurno = turno.getIdTurno();
                    thID.idTurnoHistoria = Long.toString(turno.getLastIdHistoria());

                    TurnoHistoriaEnhanced thLast = this.getTurnoHistoriaByID(thID, pm);

                    UsuarioEnhanced usuAtiende = variablesDAO.getUsuarioByLogin(turnoHistoria.getUsuarioAtiende().getUsername(), pm);

                    if (usuAtiende == null) {
                        throw new DAOException("No se encontro el usuario: "
                                + th.getUsuarioAtiende().getUsername());
                    }

                    thLast.setUsuarioAtiende(usuAtiende);
                    //end ACTUALIZAR

                    th.setActivo(true);

                    th.setTurno(tr);

                    th.setIdTurnoHistoria(String.valueOf(tr.getLastIdHistoria()
                            + 1));
                    tr.setLastIdHistoria(tr.getLastIdHistoria() + 1);
                    th.setFecha(new Date());

                    String respuesta = turnoHistoria.getRespuesta();
                    if (respuesta != null) {
                        th.setRespuesta(turnoHistoria.getRespuesta());
                    }

                    th.setFase(turnoHistoria.getFase());
                    th.setFaseAnterior(turnoHistoria.getFaseAnterior());
                    th.setStackPos(turnoHistoria.getStackPos());
                    th.setIdAdministradorSAS(turnoHistoria.getIdAdministradorSAS());
                    th.setNumeroCopiasReimpresion(turnoHistoria.getNumeroCopiasReimpresion());

                    String idMatriculaReimpresa = turnoHistoria.getIdMatriculaImpresa();
                    if (idMatriculaReimpresa != null) {
                        th.setIdMatriculaImpresa(turnoHistoria.getIdMatriculaImpresa());
                    }

                    /* Si hubo reasignacion de Turno en correccion de calificacion
	                     * se asegura que quien va atender ese turno sea el nuevo dueno 
	                     * del bloqueo
                     */
                    if (tr.getIdProceso() == 6L && tr.getModoBloqueo() == CModoBloqueo.DELEGAR_USUARIO
                            && tr.getIdFase().equals(CFase.CAL_CALIFICACION)
                            && tr.getUsuarioDestino().getIdUsuario() != us.getIdUsuario()) {
                        tr.setUsuarioDestino(us);
                    }

                    pm.makePersistent(th);
                } catch (JDOObjectNotFoundException e) {
                    Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                    th = null;
                } catch (JDOException e) {
                    Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
                    throw new DAOException(e.getMessage(), e);
                }
            }

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw (e);
        } catch (Throwable thr) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();

            }
            Log.getInstance().error(JDOTurnosDAO.class, thr.getMessage());
            throw new DAOException(thr.getMessage(), thr);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Obtiene un testamento seg?n el idSolicitud
     *
     * @param idSolicitud
     * @param pm
     * @return TestamentoEnhanced
     * @throws DAOException
     */
    private TestamentoEnhanced getTestamentoByID(String idSolicitud, PersistenceManager pm) throws DAOException {
        TestamentoEnhancedPk tID = new TestamentoEnhancedPk();
        tID.idTestamento = idSolicitud;
        TestamentoEnhanced testa = null;
        try {
            testa = (TestamentoEnhanced) pm.getObjectById(tID, true);
        } catch (JDOObjectNotFoundException e) {
            testa = null;
        } catch (JDOException e) {
            Log.getInstance().debug(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return testa;
    }

    /**
     * Obtirne un turno en ejecuci?n
     *
     * @param TeID
     * @param pm
     * @return TurnoEjecucionEnhanced
     * @throws DAOException
     */
    private TurnoEjecucionEnhanced getTurnoEjecucionByID(TurnoEjecucionEnhancedPk TeID, PersistenceManager pm) throws DAOException {

        TurnoEjecucionEnhanced turnoEnh = null;
        try {
            turnoEnh = (TurnoEjecucionEnhanced) pm.getObjectById(TeID, true);
        } catch (JDOObjectNotFoundException e) {
            turnoEnh = null;
        } catch (JDOException e) {
            Log.getInstance().debug(JDOTurnosDAO.class, e);
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return turnoEnh;
    }

    /**
     * Funcion que determina si los pagos de un turno fueron ya utilizados en la
     * radicacion de otros turnos
     *
     * @param idDocPago
     * @param pm
     * @return
     * @throws DAOException
     */
    private String getTurnosRadicadosConPago(String idTurno, String idDocPago, PersistenceManager pm) throws DAOException {
        String turnos = "";
        try {
            Query query = pm.newQuery(AplicacionPagoEnhanced.class);
            query.setFilter("idDocumentoPago == '" + idDocPago + "'");

            List rta = (List) query.execute();
            if (rta != null && rta.size() > 0) {
                if (rta.size() > 1) {
                    Iterator iter = rta.iterator();
                    while (iter.hasNext()) {
                        AplicacionPagoEnhanced aPago = (AplicacionPagoEnhanced) iter.next();
                        SolicitudPk solId = new SolicitudPk();
                        solId.idSolicitud = aPago.getIdSolicitud();
                        TurnoEnhanced turno = this.getTurnoBySolicitud(solId, pm);
                        if (turno != null && !turno.getAnulado().equals(CTurno.TURNO_ANULADO) && !turno.getIdWorkflow().equals(idTurno)) {
                            turnos += turno.getIdWorkflow() + "  ";
                        }
                    }
                }
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return turnos.trim();
    }

    public TurnoEnhanced getTurnoBySolicitud(SolicitudPk solID, PersistenceManager pm) throws DAOException {
        TurnoEnhanced tr = null;
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
        try {
            SolicitudEnhanced sol = solDAO.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);
            if (sol == null) {
                throw new DAOException("No encontr? una solicitud con el ID: " + solID.idSolicitud);
            }
            tr = this.getTurnoBySolicitud(sol, pm);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            throw e;
        }
        return tr;
    }

    public void removeNotaDevolutivaFromTurno(TurnoPk turnoID, Nota nota) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);
            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }
            NotaEnhancedPk id = new NotaEnhancedPk();
            id.anio = turnoID.anio;
            id.idCirculo = turnoID.idCirculo;
            id.idNota = nota.getIdNota();
            id.idProceso = turnoID.idProceso;
            id.idTurno = turnoID.idTurno;
            NotaEnhanced notaenh = (NotaEnhanced) pm.getObjectById(id, true);
            pm.deletePersistent(notaenh);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public void actualizaNotaDevolutiva(TurnoPk tID, Nota nota, Nota notaOld) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        NotaEnhanced notar = null;
        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            TurnoEnhanced tr = this.getTurnoByID(new TurnoEnhancedPk(tID), pm);
            if (tr == null) {
                throw new DAOException("No se encontro el Turno");
            }
            notar = this.addNotaToTurno(NotaEnhanced.enhance(nota), tr, pm);
            pm.makePersistent(notar);
            if (notaOld != null) {
                removeNotaDevolutivaFromTurno(tID, notaOld, pm);
            }
            pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOTurnosDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    public void removeNotaDevolutivaFromTurno(TurnoPk turnoID, Nota nota, PersistenceManager pm) throws DAOException {
        try {
            TurnoEnhanced turno = this.getTurnoByID(new TurnoEnhancedPk(turnoID), pm);
            if (turno == null) {
                throw new DAOException("No encontr? el turno con el ID indicado");
            }
            NotaEnhancedPk id = new NotaEnhancedPk();
            id.anio = turnoID.anio;
            id.idCirculo = turnoID.idCirculo;
            id.idNota = nota.getIdNota();
            id.idProceso = turnoID.idProceso;
            id.idTurno = turnoID.idTurno;
            NotaEnhanced notaenh = (NotaEnhanced) pm.getObjectById(id, true);
            pm.deletePersistent(notaenh);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    public List getTurnosCertificadoPosteriores(String idMatricula, Turno turno) throws DAOException {
        List rta = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();
        String matricula = new String(idMatricula);
        Date fechaInicio = turno.getFechaInicio();
        String idTurno = turno.getIdTurno();
        try {
            pm.currentTransaction().begin();
            /**
             * Autor: Carlos Torres Mantis: 13898
             */
            Query q1 = pm.newQuery(SolicitudAsociadaEnhanced.class);
            q1.declareParameters("String idSol");
            q1.setFilter("this.idSolicitud==idSol");
            Collection result = (Collection) q1.execute(turno.getSolicitud().getIdSolicitud());
            List hijas = new ArrayList();
            for (Object obj : result) {
                SolicitudAsociadaEnhanced solAso = (SolicitudAsociadaEnhanced) obj;
                hijas.add(solAso.getIdSolicitud1());
            }
            /**
             * Autor: Carlos Torres Mantis: 13898
             */
            if (hijas.isEmpty()) {
                hijas.add("");
            }
            q1.closeAll();

            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("SolicitudFolioEnhanced solFol");
            /**
             * Autor: Carlos Torres Mantis: 13898
             */
            query.declareParameters("Date fechaInicio, String matricula, String idTurno,List solHijas");
            query.setFilter("(this.solicitud.turnoAnterior == null || "
                    + "this.solicitud.turnoAnterior.idTurno != idTurno) && "
                    + /**
                     * Autor: Carlos Torres Mantis: 13898
                     */
                    "!solHijas.contains(this.solicitud.idSolicitud) && "
                    + "this.idProceso == 1 && "
                    + "this.fechaInicio > fechaInicio && "
                    + "solFol.idMatricula == matricula && "
                    + "solFol.idSolicitud == this.solicitud.idSolicitud");
            /**
             * Autor: Carlos Torres Mantis: 13898
             */
            Collection res = (Collection) query.executeWithArray(new Object[]{fechaInicio, matricula, idTurno, hijas});
            if (res != null && res.size() > 0) {
                Iterator iter = res.iterator();
                while (iter.hasNext()) {
                    TurnoEnhanced turnoEnh = (TurnoEnhanced) iter.next();
                    rta.add(turnoEnh.getIdWorkflow());
                }
            }
            query.closeAll();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().commit();
            }
            pm.close();
        }

        return rta;
    }

    public boolean validarFolioTurnoReanotacion(String idMatricula, Turno turno) throws DAOException {
        boolean valido = true;
        PersistenceManager pm = AdministradorPM.getPM();
        String matricula = new String(idMatricula);
        Date fechaInicio = turno.getFechaInicio();
        String idTurno = turno.getIdTurno();
        try {
            pm.currentTransaction().begin();
            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareVariables("SolicitudFolioEnhanced solFol");
            query.declareParameters("Date fechaInicio, String matricula, String idTurno");
            query.setFilter("(this.solicitud.turnoAnterior == null || "
                    + "this.solicitud.turnoAnterior.idTurno != idTurno) && "
                    + "this.idProceso != 2 && "
                    + "this.idProceso != 5 && "
                    + "this.idProceso != 1 && "
                    + "this.fechaInicio > fechaInicio && "
                    + "solFol.idMatricula == matricula && "
                    + "solFol.idSolicitud == this.solicitud.idSolicitud");
            Collection res = (Collection) query.execute(fechaInicio, matricula, idTurno);
            if (res.size() > 0) {
                valido = false;
            }
            query.closeAll();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().commit();
            }
            pm.close();
        }

        return valido;
    }

    public void reanotarTurno(Turno turno, Nota nota, Usuario calificador, Usuario usuario, Estacion estacion) throws DAOException {
        TurnoHistoriaEnhanced reanotacion = null;
        TurnoHistoriaEnhanced nuevaEstacion = null;
        TurnoEjecucionEnhanced turnoEjecucion = null;
        UsuarioEnhanced calificadorEnh = null;
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            pm.currentTransaction().begin();

            TurnoEnhancedPk tid = new TurnoEnhancedPk();
            tid.anio = turno.getAnio();
            tid.idCirculo = turno.getIdCirculo();
            tid.idProceso = turno.getIdProceso();
            tid.idTurno = turno.getIdTurno();

            TurnoEnhanced turnoEnh = (TurnoEnhanced) this.getTurnoByID(tid, pm);

            NotaEnhanced notaEnh = this.addNotaToTurno(NotaEnhanced.enhance(nota), turnoEnh, pm);

            pm.makePersistent(notaEnh);

            UsuarioEnhancedPk uid = new UsuarioEnhancedPk(Long.toString(usuario.getIdUsuario()));
            UsuarioEnhanced userEnh = this.getUsuarioByID(uid, pm);

            if (userEnh == null) {
                throw new DAOException("No encontr? el Usuario con el ID: "
                        + uid.idUsuario);
            }

            if (calificador != null) {
                UsuarioEnhancedPk uid2 = new UsuarioEnhancedPk(Long.toString(calificador.getIdUsuario()));
                calificadorEnh = this.getUsuarioByID(uid2, pm);

                if (calificadorEnh == null) {
                    throw new DAOException("No encontr? el usuario calificador con el ID: "
                            + uid.idUsuario);
                }
            }

            String proceso = Long.toString(turnoEnh.getIdProceso());
            if (proceso.equals(CProceso.PROCESO_CERTIFICADOS)
                    || (proceso.equals(CProceso.PROCESO_REGISTRO) && turnoEnh.getIdFase().equals(CFase.FINALIZADO))) {
                TurnoHistoriaEnhanced ultimaHistoria = (TurnoHistoriaEnhanced) turnoEnh.getHistorials().get(turnoEnh.getHistorials().size() - 1);
                TurnoHistoriaEnhanced entrega = (TurnoHistoriaEnhanced) turnoEnh.getHistorials().get(turnoEnh.getHistorials().size() - 2);
                ultimaHistoria.setFaseAnterior(proceso.equals(CProceso.PROCESO_CERTIFICADOS) ? CFase.CER_ENTREGAR : entrega.getFase());

                /**
                 * @author Daniel Forero
                 * @change REQ 1156 - Verificar que la estaci?n seleccionada
                 * tenga el rol activo.
                 */
                String estacionId = obtenerEstacionActiva(
                        entrega.getIdAdministradorSAS(),
                        turno.getIdCirculo(),
                        getRolByFase(entrega.getFase(), pm));

                /**
                 * @author : CTORRES
                 * @casoMantis : 13898.
                 * @actaRequerimiento : No 075_453_Reanotaci?n_Turnos_Registro
                 * @change : Se comenta la linea para no colocar el usuario en
                 * la face anterior
                 */
                //ultimaHistoria.setUsuarioAtiende(userEnh);
                reanotacion = new TurnoHistoriaEnhanced();
                reanotacion.setUsuario(userEnh);
                reanotacion.setUsuarioAtiende(userEnh);
                reanotacion.setRespuesta(CRespuesta.REANOTACION);
                reanotacion.setIdTurnoHistoria(Long.toString(turnoEnh.getLastIdHistoria() + 1));
                reanotacion.setActivo(true);
                reanotacion.setTurno(turnoEnh);
                reanotacion.setFecha(new Date());
                reanotacion.setFase(CFase.REANOTACION);
                reanotacion.setFaseAnterior(ultimaHistoria.getFase());
                reanotacion.setStackPos(0);
                reanotacion.setIdAdministradorSAS(ultimaHistoria.getIdAdministradorSAS());
                reanotacion.setNumeroCopiasReimpresion(ultimaHistoria.getNumeroCopiasReimpresion());

                nuevaEstacion = new TurnoHistoriaEnhanced();
                nuevaEstacion.setUsuario(userEnh);
                nuevaEstacion.setIdTurnoHistoria(Long.toString(turnoEnh.getLastIdHistoria() + 2));
                nuevaEstacion.setActivo(true);
                nuevaEstacion.setTurno(turnoEnh);
                nuevaEstacion.setFecha(new Date());
                nuevaEstacion.setFase(entrega.getFase());
                nuevaEstacion.setFaseAnterior(CFase.REANOTACION);
                nuevaEstacion.setStackPos(0);
                /**
                 * @author Daniel Forero
                 * @change REQ 1156 ? Asignar estacionId.
                 */
                nuevaEstacion.setIdAdministradorSAS(estacionId);
                nuevaEstacion.setNumeroCopiasReimpresion(ultimaHistoria.getNumeroCopiasReimpresion());

                TurnoEjecucionEnhancedPk TeID = new TurnoEjecucionEnhancedPk();
                TeID.idWorkflow = turno.getIdWorkflow();

                turnoEjecucion = this.getTurnoEjecucionByID(TeID, pm);

                /**
                 * @author Daniel Forero
                 * @change REQ 1156 ? Asignar estacionId.
                 */
                turnoEjecucion.setEstacion(estacionId);
                turnoEjecucion.setFase(entrega.getFase());
                turnoEjecucion.setEstado(CTurno.EJECUCION_HABILITADA);
                turnoEjecucion.setHasWF(CTurno.EJECUCION_ACTIVA);

                turnoEnh.setIdFase(entrega.getFase());
            } else {
                TurnoHistoriaEnhanced ultimaHistoria = (TurnoHistoriaEnhanced) turnoEnh.getHistorials().get(turnoEnh.getHistorials().size() - 1);
                TurnoHistoriaEnhanced entrega = (TurnoHistoriaEnhanced) turnoEnh.getHistorials().get(turnoEnh.getHistorials().size() - 2);
                ultimaHistoria.setFaseAnterior(entrega.getFase());
                /**
                 * @author : CTORRES
                 * @casoMantis : 13898.
                 * @actaRequerimiento : No 075_453_Reanotaci?n_Turnos_Registro
                 * @change : Se comenta la linea para no colocar el usuario en
                 * la face anterior
                 */
                //ultimaHistoria.setUsuarioAtiende(userEnh);
                reanotacion = new TurnoHistoriaEnhanced();
                reanotacion.setUsuario(userEnh);
                reanotacion.setUsuarioAtiende(userEnh);
                reanotacion.setRespuesta(CRespuesta.REANOTACION);
                reanotacion.setIdTurnoHistoria(Long.toString(turnoEnh.getLastIdHistoria() + 1));
                reanotacion.setActivo(true);
                reanotacion.setTurno(turnoEnh);
                reanotacion.setFecha(new Date());
                reanotacion.setFase(CFase.REANOTACION);
                reanotacion.setFaseAnterior(ultimaHistoria.getFase());
                reanotacion.setStackPos(0);
                reanotacion.setIdAdministradorSAS(ultimaHistoria.getIdAdministradorSAS());
                reanotacion.setNumeroCopiasReimpresion(ultimaHistoria.getNumeroCopiasReimpresion());

                nuevaEstacion = new TurnoHistoriaEnhanced();
                nuevaEstacion.setUsuario(calificadorEnh);
                nuevaEstacion.setIdTurnoHistoria(Long.toString(turnoEnh.getLastIdHistoria() + 2));
                nuevaEstacion.setActivo(true);
                nuevaEstacion.setTurno(turnoEnh);
                nuevaEstacion.setFecha(new Date());
                nuevaEstacion.setFase(CFase.CAL_CALIFICACION);
                nuevaEstacion.setFaseAnterior(CFase.REANOTACION);
                nuevaEstacion.setStackPos(0);
                nuevaEstacion.setIdAdministradorSAS(estacion.getEstacionId());
                nuevaEstacion.setNumeroCopiasReimpresion(ultimaHistoria.getNumeroCopiasReimpresion());

                TurnoEjecucionEnhancedPk TeID = new TurnoEjecucionEnhancedPk();
                TeID.idWorkflow = turno.getIdWorkflow();

                turnoEjecucion = this.getTurnoEjecucionByID(TeID, pm);

                turnoEjecucion.setEstacion(estacion.getEstacionId());
                turnoEjecucion.setFase(CFase.CAL_CALIFICACION);

                turnoEnh.setIdFase(CFase.CAL_CALIFICACION);
                turnoEnh.setUsuarioDestino(calificadorEnh);
                turnoEnh.setModoBloqueo(CModoBloqueo.DELEGAR_USUARIO);
            }

            turnoEnh.setLastIdHistoria(turnoEnh.getLastIdHistoria() + 2);
            turnoEnh.setFechaFin(null);

            pm.makePersistent(reanotacion);
            pm.makePersistent(nuevaEstacion);
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

    }

    protected TurnoEnhancedPk crearTurnoPortal(CirculoProcesoEnhanced cpe,
            PagoEnhancedPk pId, String impresora, Usuario user,
            PersistenceManager pm) throws DAOException {
        //List notasInf = new ArrayList();
        TurnoEnhanced tr = new TurnoEnhanced();
        //TurnoHistoriaEnhanced thr = new TurnoHistoriaEnhanced();
        TurnoEnhancedPk trId = new TurnoEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        JDOPagosDAO pagosDAO = new JDOPagosDAO();
        //JDOProcesosDAO procesosDAO = new JDOProcesosDAO();
        JDOFasesDAO fasesDAO = new JDOFasesDAO();

        /*try {
                EstacionDAO estacionDAO = DAOFactory.getFactory().getEstacionDAO();
                JerarquiaDAO jerarquiaDAO = DAOFactory.getFactory().getJerarquiaDAO();
            } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
                throw new DAOException(e.getMessage());
            }*/
        Date fecha = new Date();
        Calendar calendario = Calendar.getInstance();
        String anio = String.valueOf(calendario.get(Calendar.YEAR));

        try {
            /**
             *
             */
            UsuarioEnhanced userEnhanced = null;
            if (user != null && user.getUsername() != null) {
                JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();
                userEnhanced = variablesDAO.getUsuarioByLogin(user.getUsername(), pm);
            }

            //Encontrar el pago persistente asociado con la solicitud.
            PagoEnhanced p = pagosDAO.getPagoByID(pId, pm);

            if (p == null) {
                throw new DAOException(DAOException.PAGO_NO_ASOCIADO_SOLICITUD);
            }

            //Obtener la solicitud persistente que se va a asociar con el turno.
            SolicitudEnhancedPk sId = new SolicitudEnhancedPk();
            sId.idSolicitud = p.getLiquidacion().getIdSolicitud();

            SolicitudEnhanced s = solicitudesDAO.getSolicitudByID(sId, pm);

            if (s == null) {
                throw new DAOException(DAOException.SOLICITUD_PERSISTENTE_NO_ENCONTRADA
                        + sId.idSolicitud);
            }

            //El C?rculo Proceso se obtiene de uno de los par?metros recibidos.
            CirculoProcesoEnhanced cp = cpe;

            CirculoProcesoEnhanced cpnal = null;
            if (s instanceof SolicitudCertificadoEnhanced) {
                List solFolio = s.getSolicitudFolios();
                Iterator itSolFolio = solFolio.iterator();
                String circulo = null;
                if (itSolFolio.hasNext()) {
                    SolicitudFolioEnhanced sol = (SolicitudFolioEnhanced) itSolFolio.next();
                    circulo = sol.getFolio().getIdMatricula();
                    String[] str = circulo.split("-");
                    circulo = str[0];
                }
                if (circulo != null && !circulo.equals(cp.getIdCirculo())) {
                    cpnal = pagosDAO.getCirculoProcesoById(circulo, s.getProceso().getIdProceso(), pm);
                }

            }

            InicioProcesos inicioProcesos
                    = fasesDAO.obtenerFaseInicial(new Long(cp.getProceso().getIdProceso()).toString());
            Fase faseInicial = new Fase(inicioProcesos.getIdFase(), "", "", "");

            FaseEnhanced f = FaseEnhanced.enhance(faseInicial);

            //Se asocian todos los atributos necesarios para la creaci?n del nuevo turno.
            tr.setIdFase(f.getID());
            tr.setFechaInicio(fecha);
            tr.setSolicitud(s);
            tr.setLastIdHistoria(0);
            tr.setLastIdNota(0);
            tr.setConsistenciaWF(CTurno.CON_WF_CONSISTENTE);
            if (cpnal != null) {
                tr.setAnio(cpnal.getAnio());
                tr.setCirculoproceso(cpnal);
                tr.setIdTurno(String.valueOf(cpnal.getLastIdTurno() + 1));
                tr.setIdWorkflow(anio + "-" + cpnal.getIdCirculo() + "-"
                        + String.valueOf(cpnal.getIdProceso()) + "-"
                        + String.valueOf(cpnal.getLastIdTurno() + 1));
                cpnal.setLastIdTurno(cpnal.getLastIdTurno() + 1);
            } else {
                tr.setAnio(cp.getAnio());
                tr.setCirculoproceso(cp);
                tr.setIdTurno(String.valueOf(cp.getLastIdTurno() + 1));
                tr.setIdWorkflow(anio + "-" + cp.getIdCirculo() + "-"
                        + String.valueOf(cp.getIdProceso()) + "-"
                        + String.valueOf(cp.getLastIdTurno() + 1));
                cp.setLastIdTurno(cp.getLastIdTurno() + 1);
            }

            if (userEnhanced != null) {
                tr.setUsuarioDestino(userEnhanced);
            }
            tr.setAnulado(CTurno.CAMPO_ANULADO_DEFECTO);

            pm.makePersistent(tr);
            trId = (TurnoEnhancedPk) pm.getObjectId(tr);

            return trId;
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void crearHistoriaTurnoCertificadoMasivoPortal(TurnoEnhanced t, Usuario user, PersistenceManager pm) throws DAOException {

        //	Se crea el historial de la creacion
        TurnoHistoria tHist;
        tHist = new TurnoHistoria();
        tHist.setActivo(false);
        tHist.setAnio(t.getAnio());
        tHist.setFecha(t.getFechaInicio());
        tHist.setIdCirculo(t.getIdCirculo());
        tHist.setIdProceso(t.getIdProceso());
        tHist.setIdTurno(t.getIdWorkflow());
        String tipo = CTipoCertificado.ASOCIADO_MASIVO;
        tHist.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist.setUsuario(user);
        tHist.setUsuarioAtiende(user);
        tHist.setFase("SOLICITUD");
        //tHist.setFaseAnterior(CFase.CER_CREAR);
        addTurnoHistoria(t, tHist, new Integer(0), pm);
        // Actualiza
        t.setIdFase(CFase.CER_ENTREGAR);
        t.setUltimaRespuesta(tipo);
        t.setLastIdHistoria(3);

        // Se crea el historial de la creacion
        TurnoHistoria tHist2;
        tHist2 = new TurnoHistoria();
        tHist2.setActivo(false);
        tHist2.setAnio(t.getAnio());
        tHist2.setFecha(t.getFechaInicio());
        tHist2.setIdCirculo(t.getIdCirculo());
        tHist2.setIdProceso(t.getIdProceso());
        tHist2.setIdTurno(t.getIdWorkflow());
        tHist2.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist2.setUsuario(user);
        tHist2.setUsuarioAtiende(user);
        tHist2.setFase(CFase.CER_SOLICITUD);
        tHist2.setRespuesta(CTipoCertificado.TIPO_INMEDIATO_NOMBRE);
        tHist2.setFaseAnterior("SOLICITUD");
        addTurnoHistoria(t, tHist2, new Integer(0), pm);

        //Se debe consultar quien puede atender esta fase
        // Se crea el historial de la entrega
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        String estacion3 = "X-ENTREGA.CER." + CTurnoPortal.CIRCULO_PORTAL;
        tHist3.setIdAdministradorSAS(estacion3);
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdWorkflow());
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_ENTREGAR);
        tHist3.setFaseAnterior(CFase.CER_SOLICITUD);
        tHist3.setActivo(true);
        addTurnoHistoria(t, tHist3, new Integer(0), pm);

        try {
            TurnoEjecucionEnhanced turnoEje = new TurnoEjecucionEnhanced();
            turnoEje.setEstacion("X-ENTREGA.CER." + CTurnoPortal.CIRCULO_PORTAL);
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_ENTREGAR);
            turnoEje.setHasWF(false);
            turnoEje.setIdWorkflow(t.getIdWorkflow());
            pm.makePersistent(turnoEje);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void crearHistoriaTurnoMasivoPortal(TurnoEnhanced t, Usuario user, Estacion estacion, PersistenceManager pm) throws DAOException {

        //	Se crea el historial de la creacion
        TurnoHistoria tHist = new TurnoHistoria();
        tHist.setActivo(false);
        tHist.setAnio(t.getAnio());
        tHist.setFase("SOLICITUD");
        tHist.setFecha(new Date());
        tHist.setRespuesta("CREACION DEL TURNO");
        tHist.setIdCirculo(t.getIdCirculo());
        tHist.setIdProceso(t.getIdProceso());
        tHist.setIdTurno(t.getIdTurno());
        UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
        uid.idUsuario = user.getIdUsuario();
        tHist.setUsuario(user);
        tHist.setUsuarioAtiende(user);

        //tHist.setFaseAnterior(CFase.CER_CREAR);
        addTurnoHistoria(t, tHist, new Integer(0), pm);
        // Actualiza
        t.setIdFase(CFase.CER_CERTIFICADOS_MASIVOS);
        t.setUltimaRespuesta(CRol.SIR_ROL_CAJERO_CERT_MASIVOS);
        t.setLastIdHistoria(3);

        // Se crea el historial de la creacion
        TurnoHistoria tHist2;
        tHist2 = new TurnoHistoria();
        tHist2.setActivo(false);
        tHist2.setAnio(t.getAnio());
        tHist2.setFecha(t.getFechaInicio());
        tHist2.setIdCirculo(t.getIdCirculo());
        tHist2.setIdProceso(t.getIdProceso());
        tHist2.setIdTurno(t.getIdTurno());
        tHist2.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist2.setUsuario(user);
        tHist2.setUsuarioAtiende(user);
        tHist2.setFase(CFase.CER_DECIDIR_USUARIO);
        tHist2.setRespuesta(CRol.SIR_ROL_CAJERO_CERT_MASIVOS);
        tHist2.setFaseAnterior("SOLICITUD");
        addTurnoHistoria(t, tHist2, new Integer(0), pm);

        //Se debe consultar quien puede atender esta fase
        // Se crea el historial de la entrega
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        tHist3.setIdAdministradorSAS(estacion.getEstacionId());
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdTurno());
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_CERTIFICADOS_MASIVOS);
        tHist3.setFaseAnterior(CFase.CER_DECIDIR_USUARIO);
        tHist3.setActivo(true);
        addTurnoHistoria(t, tHist3, new Integer(0), pm);

        try {
            TurnoEjecucionEnhanced turnoEje = new TurnoEjecucionEnhanced();
            turnoEje.setEstacion(estacion.getEstacionId());
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_CERTIFICADOS_MASIVOS);
            turnoEje.setHasWF(false);
            turnoEje.setIdWorkflow(t.getIdWorkflow());
            pm.makePersistent(turnoEje);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene un turno dado el identificador de su solicitud, si no tiene turno
     * asociado retorna null
     *
     * @param solID
     * @return
     * @throws DAOException
     */
    public Turno getTurnoBySolicitudPortal(SolicitudPk solID) throws DAOException {
        TurnoEnhanced tr = null;
        Turno rta = null;
        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        try {
            pm.currentTransaction().begin();
            SolicitudEnhanced sol = solDAO.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);
            if (sol == null) {
                throw new DAOException("No encontr? una solicitud con el ID: " + solID.idSolicitud);
            }

            tr = this.getTurnoBySolicitudPortal(sol, pm);
            pm.makeTransient(tr);

            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } finally {
            pm.close();
        }
        if (tr != null) {
            rta = (Turno) tr.toTransferObject();
        }

        return rta;
    }

    public Turno getTurnoBySolicitudPortal(SolicitudPk solID, PersistenceManager pm) throws DAOException {
        TurnoEnhanced tr = null;
        Turno rta = null;
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        try {
            SolicitudEnhanced sol = solDAO.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);
            if (sol == null) {
                throw new DAOException("No encontr? una solicitud con el ID: " + solID.idSolicitud);
            }

            tr = this.getTurnoBySolicitudPortal(sol, pm);
            this.makeTransientTurno(tr, pm);

        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            throw e;
        }
        if (tr != null) {
            rta = (Turno) tr.toTransferObject();
        }
        return rta;
    }

    /**
     * Obtiene un turno dado el identificador de su solicitud asociada, m?todo
     * utilizado para transacciones
     *
     * @param wfId Documento de pago con sus atributos numero de cheque y numero
     * de cuenta
     * @param pm PersistenceManager de la transaccion
     * @return Turno con todos sus atributos.
     * @throws DAOException
     */
    protected TurnoEnhanced getTurnoBySolicitudPortal(SolicitudEnhanced sol,
            PersistenceManager pm) throws DAOException {
        TurnoEnhanced rta = null;

        try {
            Query query = pm.newQuery(TurnoEnhanced.class);
            query.declareParameters("SolicitudEnhanced sol");
            query.setFilter("this.solicitud == sol");

            Collection col = null;
            col = (Collection) query.execute(sol);

            if (col.size() == 0) {
                rta = null;
            } else {
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    rta = (TurnoEnhanced) iter.next();
                }

                query.closeAll();
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * @param t
     * @param solicitud
     * @param user
     * @throws DAOException
     */
    public void crearHistoriaTurnoCertificadoVUR(TurnoEnhanced t, Usuario user, PersistenceManager pm) throws DAOException {

        //	Se crea el historial de la creacion
        TurnoHistoria tHist;
        tHist = new TurnoHistoria();
        tHist.setActivo(false);
        tHist.setAnio(t.getAnio());
        tHist.setFecha(t.getFechaInicio());
        tHist.setIdCirculo(t.getIdCirculo());
        tHist.setIdProceso(t.getIdProceso());
        tHist.setIdTurno(t.getIdWorkflow());
        String tipo = CTipoCertificado.TIPO_NACIONAL_ID;
        tHist.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist.setUsuario(user);
        tHist.setUsuarioAtiende(user);
        tHist.setFase("SOLICITUD");
        //tHist.setFaseAnterior(CFase.CER_CREAR);
        addTurnoHistoria(t, tHist, new Integer(0), pm);
        // Actualiza
        t.setIdFase(CFase.CER_ENTREGAR);
        t.setUltimaRespuesta(tipo);
        t.setLastIdHistoria(3);

        // Se crea el historial de la creacion
        TurnoHistoria tHist2;
        tHist2 = new TurnoHistoria();
        tHist2.setActivo(false);
        tHist2.setAnio(t.getAnio());
        tHist2.setFecha(t.getFechaInicio());
        tHist2.setIdCirculo(t.getIdCirculo());
        tHist2.setIdProceso(t.getIdProceso());
        tHist2.setIdTurno(t.getIdWorkflow());
        tHist2.setRespuesta(CRespuesta.CREACION_DEL_TURNO);
        tHist2.setUsuario(user);
        tHist2.setUsuarioAtiende(user);
        tHist2.setFase(CFase.CER_SOLICITUD);
        tHist2.setRespuesta(CTipoCertificado.TIPO_INMEDIATO_NOMBRE);
        tHist2.setFaseAnterior("SOLICITUD");
        addTurnoHistoria(t, tHist2, new Integer(0), pm);

        //Se debe consultar quien puede atender esta fase
        // Se crea el historial de la entrega
        TurnoHistoria tHist3;
        tHist3 = new TurnoHistoria();
        tHist3.setActivo(true);
        tHist3.setAnio(t.getAnio());
        tHist3.setFecha(t.getFechaInicio());
        String estacion3 = "X-ENTREGA.CER." + CTurnoPortal.CIRCULO_VUR;
        tHist3.setIdAdministradorSAS(estacion3);
        tHist3.setIdCirculo(t.getIdCirculo());
        tHist3.setIdProceso(t.getIdProceso());
        tHist3.setIdTurno(t.getIdWorkflow());
        tHist3.setUsuario(user);
        tHist3.setFase(CFase.CER_ENTREGAR);
        tHist3.setFaseAnterior(CFase.CER_SOLICITUD);
        tHist3.setActivo(true);
        addTurnoHistoria(t, tHist3, new Integer(0), pm);

        try {
            TurnoEjecucionEnhanced turnoEje = new TurnoEjecucionEnhanced();
            turnoEje.setEstacion("X-ENTREGA.CER." + CTurnoPortal.CIRCULO_VUR);
            turnoEje.setEstado("1");
            turnoEje.setFase(CFase.CER_ENTREGAR);
            turnoEje.setHasWF(false);
            turnoEje.setIdWorkflow(t.getIdWorkflow());
            pm.makePersistent(turnoEje);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene (si existe) el <code>Turno </code> del cual un turno es
     * turnoAnterior
     *
     * @param Turno.
     * @param pm PersistenceManager de la transaccion
     * @return Objeto <code>TurnoEnhanced</code> con sus atributos, del cual es
     * turno anterior el turno con idWorkflow pasado como par?metro.
     * @throws DAOException Obtiene los turnos de devolucion asociados con el
     * turno ingresado como par?metro.
     * @author: Julio Alcazar
     * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno -
     * Turnos Devolucion Negados
     */
    public List getTurnosDevolucion(Turno turno) throws DAOException {
        TurnoEnhanced turnoSiguiente = null;
        List listaTurnos = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {

            Query query = pm.newQuery(TurnoEnhanced.class);

            query.declareParameters("String idWork, String idCir");

            query.setFilter("this.solicitud.turnoAnterior.idWorkflow==idWork &&\n"
                    + "this.solicitud.turnoAnterior.idCirculo==idCir && this.anulado == 'N'");

            Collection col = (Collection) query.execute(turno.getIdWorkflow(), turno.getIdCirculo());

            Iterator it = col.iterator();

            while (it.hasNext()) {
                turnoSiguiente = (TurnoEnhanced) it.next();
                if (Long.parseLong(CProceso.PROCESO_DEVOLUCIONES) == turnoSiguiente.getIdProceso()) {
                    pm.makeTransient(turnoSiguiente);
                    listaTurnos.add(turnoSiguiente);
                }

            }

            query.close(col);

        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        List listaTransfer = TransferUtils.makeTransferAll(listaTurnos);

        return listaTransfer;
    }

    /**
     * @autor HGOMEZ
     * @mantis 13176
     * @Requerimiento 061_453_Requerimiento_Auditoria
     * @descripcion Mantiene la informaci?n del usuario.
     */
    public void setDatosTerminal(java.util.Map pInfoUsuario) {
        infoUsuario = pInfoUsuario;
    }

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Verificar que la estaci?n seleccionada tenga el rol
     * activo.
     *
     * Permite seleccionar una estacion que cumpla con dos condiciones: tener el
     * rol activo, y tener al menos un usuario activo asociado a la estaci?n.
     *
     * @param estacionId identificador de la estacion que ser? verificada. Si la
     * estaci?n no existe, es indefinida, o no cumple con las condiciones, se
     * retorna una nueva estaci?n segun el rol y el circulo.
     * @param circuloId identificador del circulo al cual pertenece debe
     * pertener el rol.
     * @param rolId identificador del rol con el cual se debe realizar la
     * seleccion de la estacion.
     * @return Retorna el identificador de la estaci?n que cumple con las
     * siguientes condiciones: tener el rol activo, y tener al menos un usuario
     * activo asociado a la estaci?n.
     * @throws DAOException Si ocurre un error inesperado durante la
     * verificaci?n de la estaci?n.
     */
    private String obtenerEstacionActiva(String estacionId, String circuloId, String rolId) throws DAOException {

        Estacion estacion = new Estacion();
        estacion.setEstacionId(estacionId);

        Rol rol = new Rol();
        rol.setRolId(rolId);

        try {
            ServiceLocator sl = ServiceLocator.getInstancia();
            FenrirServiceInterface fenrir = (FenrirServiceInterface) sl.getServicio(SERVICIO_FENRIR);

            RolDAO rolDAO = DAOFactory.getFactory().getRolDAO();

            // Se verifica primero la estacion que se pasa por parametro
            if (estacionId != null && !estacionId.trim().isEmpty() && rolDAO.estadoRelRolEstacion(rol, estacion)) {
                try {
                    // Si al menos uno de los usuarios se encuentra activo, la estaci?n se agrega a la 
                    // la lista de estaciones potenciales.
                    List listaUsuarios = fenrir.getUsuariosEstacion(estacion);
                    for (int i = 0; i < listaUsuarios.size(); i++) {
                        if (((gov.sir.core.negocio.modelo.Usuario) listaUsuarios.get(i)).isActivo()) {
                            return estacionId;
                        }
                    }
                } catch (Throwable t) {
                    throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones " + t.getMessage());
                }
            }

            // Si la estacion por defecto no cumple las condiciones, se consultan otras estaciones
            List estacionesPotenciales = new ArrayList();
            Jerarquia jerarquia = new Jerarquia();
            jerarquia.setJerarquiaId(JERARQUIA_CIRCULOS);

            Nivel nivel = new Nivel();
            nivel.setAtributo1(circuloId);

            EstacionDAO estacionDAO = DAOFactory.getFactory().getEstacionDAO();
            List estacionesRolActivo = estacionDAO.obtenerEstaciones(jerarquia, nivel, false, rol);

            if (estacionesRolActivo != null && !estacionesRolActivo.isEmpty()) {

                //Obtener la lista de estaciones con rol activo, teniendo ahora en cuenta el atributo
                //que indica si el usuario est? o no activo en el esquema de SIR.
                for (int i = 0; i < estacionesRolActivo.size(); i++) {
                    //Se obtiene la estaci?n y se determina si sus usuarios asociados est?n activos.
                    //En caso de que los usuarios se encuentren activos, se inserta en una nueva lista.
                    Estacion estacionTmp = (Estacion) estacionesRolActivo.get(i);

                    try {
                        // Si al menos uno de los usuarios se encuentra activo, la estaci?n se agrega a la 
                        // la lista de estaciones potenciales.
                        List listaUsuarios = fenrir.getUsuariosEstacion(estacionTmp);
                        boolean encontro = false;

                        for (int k = 0; k < listaUsuarios.size() && !encontro; k++) {
                            encontro = ((gov.sir.core.negocio.modelo.Usuario) listaUsuarios.get(k)).isActivo();
                            if (encontro) {
                                estacionesPotenciales.add(estacionTmp);
                            }
                        }
                    } catch (Throwable t) {
                        throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones " + t.getMessage());
                    }
                }
            }

            //Se escoge aleatoriamente una estaci?n, de la lista de estaciones potenciales 
            if (!estacionesPotenciales.isEmpty()) {
                Random ran = new Random();
                int indice = ran.nextInt(estacionesPotenciales.size());
                estacion = (Estacion) estacionesPotenciales.get(indice);
                return estacion.getEstacionId();
            } else {
                throw new DAOException("No se encontro ninguna estacion para el circulo " + circuloId + " y el rol " + rolId);
            }

        } catch (ServiceLocatorException e) {
            throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones con el rol " + rolId + " activo: " + e.getMessage());
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            throw new DAOException("Ocurrio un error recuperando la lista de posibles estaciones con el rol " + rolId + " activo:" + e.getMessage());
        }
    }

    /**
     * @author YEFERSON MARTINEZ
     * @change REQ 317097 PERMITE LA CONSULTA A LA BD SIR_OP_OFICIO PARA TRAER
     * EL TEXTO DE OFICIO DE PERTENENCIA DE UN TURNO
     *
     *
     * @param turno identificador deL la TURNO que ser? CONSULTADO. * RETORNA EL
     * TEXO GUARDADO EN LA BD EN EL CAMPO FCIO_TEXTO_OFICIO.
     * @param anioObs identificador del A?O a cual pertenece debe pertener EL
     * TURNO.
     * @param circuloObs identificador del CIRCULO a cual pertenece debe
     * pertener EL TURNO.
     * @param procesoObs identificador del PROCESO a cual pertenece debe
     * pertener EL TURNO.
     */
    public String obsPertenencia(String turno, String anioObs, String circuloObs, String procesoObs) throws DAOException {

        String rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta
                = "SELECT FCIO_TEXTO_OFICIO FROM SIR_OP_OFICIO WHERE ID_TURNO = ? AND ID_CIRCULO = ? AND ANIO = ? AND ID_PROCESO = ?";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, turno);
            ps.setString(2, circuloObs);
            ps.setString(3, anioObs);
            ps.setString(4, procesoObs);

            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getString("FCIO_TEXTO_OFICIO");
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

    /**
     * @autor Yeferson Martinez caso 341155 METODO PARA CONSULTAR LA SUMA DE
     * CONSERVACIION DOCUMENTAL POR TURNO
     * @param turno
     * @param anioObs
     * @param circuloObs
     * @param procesoObs
     * @return
     * @throws DAOException
     */
    public String SumaConservacion(String turno, String anioObs, String circuloObs, String procesoObs) throws DAOException {

        String rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String turnoG = anioObs + "-" + circuloObs + "-" + procesoObs + "-" + turno;
        String consulta = "SELECT SUM(VALOR_CONSERVACION) AS VALOR \n"
                + "FROM SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                + "WHERE ID_TURNO = ? AND MAYORVALOR = 0 ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, turnoG);

            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getString("VALOR");
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

    /**
     * @autor Yeferson Martinez caso 341155 METODO PARA CONSULTAR LA SUMA DE
     * CONSERVACIION DOCUMENTAL POR MAYOR VALOR
     * @param turno
     * @return
     * @throws DAOException
     */
    public String liquidaConservaMayorValor(String turno, String anioObs, String circuloObs, String procesoObs) throws DAOException {
        String rtas = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT SUM(VALOR_CONSERVACION) AS SUMA\n"
                + "FROM SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                + "where ID_TURNO = ? AND MAYORVALOR = 1 ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            String turnoG = anioObs + "-" + circuloObs + "-" + procesoObs + "-" + turno;
            ps.setString(1, turnoG);
            rs = ps.executeQuery();

            while (rs.next()) {
                rtas = rs.getString("SUMA");
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
        return rtas;

    }

    public double SumaConservacion(String turno) throws DAOException {

        double rta = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT SUM(VALOR_CONSERVACION) AS VALOR \n"
                + "FROM SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                + "WHERE ID_TURNO = ?  ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, turno);

            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getDouble("VALOR");
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

    /**
     * yeferson
     */
    public String liquidaConservaMayorValor(String turno) throws DAOException {
        String rtas = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT SUM(VALOR_CONSERVACION) AS SUMA\n"
                + "FROM SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                + "where ID_TURNO = ? AND MAYORVALOR = 1 ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, turno);
            rs = ps.executeQuery();

            while (rs.next()) {
                rtas = rs.getString("SUMA");
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
        return rtas;

    }

    /**
     * yeferson
     */
    public double valorsin(String turno, String solicitud, int mayorvalor) throws DAOException {
        double valor = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT SUM(TARIFA_BASE) AS SUMA \n"
                + " FROM SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                + " where ID_TURNO = ? AND ID_SOLICITUD = ? AND MAYORVALOR = ?";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, turno);
            ps.setString(2, solicitud);
            ps.setInt(3, mayorvalor);
            rs = ps.executeQuery();

            while (rs.next()) {
                valor = rs.getDouble("SUMA");
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
        return valor;

    }

    public void updateConservacion(TurnoEnhanced turno, int evento) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        long proceso = turno.getIdProceso();
        String turnof = turno.getAnio() + "-" + turno.getIdCirculo() + "-" + turno.getIdProceso() + "-" + turno.getIdTurno();
        String consulta = null;
        if (proceso == 4) {
            consulta = "UPDATE SIR_OP_DEVOLUCION_CONSERVADOC \n"
                    + " SET ANULACION = ? \n"
                    + " WHERE ID_TURNO = ? ";
        } else {
            consulta = "UPDATE SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                    + " SET ANULACION = ? \n"
                    + " WHERE ID_TURNO = ? ";
        }
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setInt(1, evento);
            ps.setString(2, turnof);
            ps.executeUpdate();
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

    }

    public double SumaConservacionMV(String turno, int mayorvalor) throws DAOException {

        double rta = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT SUM(VALOR_CONSERVACION) AS VALOR \n"
                + "FROM SIR_OP_LIQUIDA_CONSERVA_DOCU \n"
                + "WHERE ID_TURNO = ? AND MAYORVALOR = ? ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, turno);
            ps.setInt(2, mayorvalor);
            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getDouble("VALOR");
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

    public String getActoSolicitud(String solicitud) throws DAOException {

        String rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta
                = "SELECT ID_TIPO_ACTO FROM SIR_OP_ACTO WHERE ID_SOLICITUD = ? AND ID_TIPO_ACTO = '10'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, solicitud);

            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getString("ID_TIPO_ACTO");
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

    public String getAnotacionFolio(String folio, String turno) throws DAOException {

        String rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta
                = "SELECT ID_MATRICULA FROM SIR_OP_SOLICITUD_FOLIO WHERE ID_MATRICULA = ? AND ID_SOLICITUD = ? AND SLFL_ESTADO IN ('3','4') AND ROWNUM = 1";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, folio);
            ps.setString(2, turno);

            rs = ps.executeQuery();

            while (rs.next()) {
                rta = rs.getString("ID_MATRICULA");
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

    public List getControlMatriculaTurno(String turnoID) throws DAOException {
        List controlMatricula = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT * FROM SIR_OP_CONTROL_MATRICULA " 
                  + "WHERE CTRL_TURNO = '"+turnoID+"'";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while(rs.next()){
            ControlMatricula ctrl = new ControlMatricula();
            ctrl.setIdMatricula((String) rs.getString(1));
            ctrl.setAccion((String) rs.getString(2));
            ctrl.setRol((String) rs.getString(3));
            ctrl.setTurno((String) rs.getString(4));
            controlMatricula.add(ctrl);
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

        return controlMatricula;

        }
    
        /**
     * Actualizar la informacion de un turno.
     * <p>
     * En particular actualiza los atributos del turno status y url
     * @param turno turno a modificar
     * @throws <code>DAOException</code>
     */
    public void updateStatusRel(Turno turno) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        
        String consulta = null;
        System.out.println("1 jdoturno: "+turno.getRelStat()+","+ turno.getIdWorkflow()+","+ turno.getRelEndpoint());
        consulta = "UPDATE SIR_OP_TURNO \n"
                 + "   SET TRNO_REL_STATUS   = ?,\n"
                 + "       TRNO_REL_ENDPOINT = ? \n"
                 + " WHERE TRNO_ID_WORKFLOW = ?";
        
        System.out.println("2.jul"+consulta);
        try {
            
            System.out.println("1 jdoturno: "+turno.getRelStat()+","+ turno.getIdWorkflow()+","+ turno.getRelEndpoint());
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, turno.getRelStat());
            ps.setString(2, turno.getRelEndpoint());
            ps.setString(3, turno.getIdWorkflow());
            ps.executeUpdate();
            
            System.out.println("2.jul"+consulta);
            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            System.out.println("allo1");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            System.out.println("allo2");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            System.out.println("allo3");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            System.out.println("allo4");
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
    }
}
