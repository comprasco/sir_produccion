/*
 * Clase para el manejo de las liquidaciones relacionadas
 * con los diferentes procesos de la aplicación.
 *
 */
package gov.sir.hermod.dao.impl.jdogenie;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionPk;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoCalculo;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AlcanceGeograficoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.dao.ForsetiFactory;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.LiquidacionesDAO;
import gov.sir.hermod.pagos.LiquidadorRegistro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.versant.core.jdo.VersantQuery;
/**
 * @Author: Santiago Vásquez
 * @Change: 2062.TARIFAS.REGISTRALES.2017
 */
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.*;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionConservacionEnhanced;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para el manejo de las liquidaciones relacionadas con los diferentes
 * procesos de la aplicación.
 *
 * @author mortiz, dlopez
 * @see gov.sir.core.negocio.modelo.Liquidacion
 */
public class JDOLiquidacionesDAO implements LiquidacionesDAO {

    /**
     * Crea a nueva instancia de JDOLiquidacionesDAO
     */
    public JDOLiquidacionesDAO() {
    }

    /**
     * Agrega una liquidacion de certificados en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoCertificado</code> con sus atributos,
     * exceptuando el identificador.
     * @param sID identificador de la Solicitud de Certificados a la que se debe
     * asociar la <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoCertificado</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     *
     *
     */
    public LiquidacionTurnoCertificado addLiquidacionToSolicitudCertificado(
            LiquidacionTurnoCertificado l, SolicitudPk sID)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        LiquidacionTurnoCertificadoEnhanced liqr = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            SolicitudCertificadoEnhanced s = (SolicitudCertificadoEnhanced) solicitudesDAO.getSolicitudByID(new SolicitudEnhancedPk(
                    sID), pm);

            if (s == null) {
                throw new DAOException("No se encontro la solicitud");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(s, pm);

            liqr = this.addLiquidacionToSolicitudCertificado(LiquidacionTurnoCertificadoEnhanced.enhance(
                    l), s, pm);
            pm.makePersistent(liqr);

            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = liqr.getIdLiquidacion();
            lId.idSolicitud = liqr.getIdSolicitud();
            liqr = (LiquidacionTurnoCertificadoEnhanced) this.getLiquidacionByID(lId,
                    pm);
            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (LiquidacionTurnoCertificado) liqr.toTransferObject();
    }

    /**
     * Agrega una liquidacion de certificados en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoCertificadoEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudCertificado</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return  <code>LiquidacionTurnoCertificadoEnhanced</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificado
     * @see gov.sir.core.negocio.modelo.SolicitudCertificadoEnhanced
     */
    protected LiquidacionTurnoCertificadoEnhanced addLiquidacionToSolicitudCertificado(
            LiquidacionTurnoCertificadoEnhanced l, SolicitudCertificadoEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionTurnoCertificadoEnhanced rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        try {
            TipoCertificadoEnhanced tipoCer = l.getTipoCertificado();
            TipoCertificadoEnhancedPk tId = new TipoCertificadoEnhancedPk();
            tId.idTipoCertificado = tipoCer.getIdTipoCertificado();
            TipoCertificadoEnhanced tipoCerr = variablesDAO.getTipoCertificadoByID(tId,
                    pm);
            if (tipoCerr == null) {
                throw new DAOException(
                        "No encontró el TipoCertificado con el ID: "
                        + tId.idTipoCertificado);
            }

            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);

            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);
            l.setSolicitud(s);
            l.setIdSolicitud(s.getIdSolicitud());
            l.setTipoCertificado(tipoCerr);

            if (s != null && s.getCirculo() != null && s.getCirculo().getIdCirculo() != null) {
                l.setCirculo(s.getCirculo().getIdCirculo());
            }

            if (l.getFecha() == null) {
                l.setFecha(new Date());
            }
            rta = l;
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de registro en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoRegistro</code> con sus atributos,
     * exceptuando el identificador.
     * @param sID identificador de la Solicitud de Registro a la que se debe
     * asociar la <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoRegistro</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     */
    public LiquidacionTurnoRegistro addLiquidacionToSolicitudRegistro(
            LiquidacionTurnoRegistro l, SolicitudPk sID)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        LiquidacionTurnoRegistroEnhanced liqr = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        LiquidacionTurnoRegistro liquidacionRegistro = null;

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            SolicitudRegistroEnhanced s = (SolicitudRegistroEnhanced) solicitudesDAO.getSolicitudByID(new SolicitudEnhancedPk(
                    sID), pm);

            if (s == null) {
                throw new DAOException("No se encontro la solicitud");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(s, pm);

            liqr = this.addLiquidacionToSolicitudRegistro(LiquidacionTurnoRegistroEnhanced.enhance(
                    l), s, pm);
            pm.makePersistent(liqr);

            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = liqr.getIdLiquidacion();
            lId.idSolicitud = liqr.getIdSolicitud();
            liqr = (LiquidacionTurnoRegistroEnhanced) this.getLiquidacionByID(lId,
                    pm);
            pm.currentTransaction().commit();

            int i = liqr.getActos().size();
            List listaActosLiquidacion = liqr.getActos();

            for (int j = 0; j < i; j++) {
                ActoEnhanced acto = (ActoEnhanced) listaActosLiquidacion.get(j);
                pm.makeTransient(acto);
            }

            i = liqr.getSolicitud().getSolicitudFolios().size();
            List listaSolicitudesFolio = liqr.getSolicitud().getSolicitudFolios();

            for (int j = 0; j < i; j++) {
                SolicitudFolioEnhanced solFolioEnh = (SolicitudFolioEnhanced) listaSolicitudesFolio.get(j);
                pm.makeTransient(solFolioEnh);
            }

            i = liqr.getSolicitud().getSolicitudesHijas().size();
            List listaSolicitudesHijas = liqr.getSolicitud().getSolicitudesHijas();

            for (int j = 0; j < i; j++) {
                SolicitudAsociadaEnhanced solAsociadaEnh = (SolicitudAsociadaEnhanced) listaSolicitudesHijas.get(j);
                pm.makeTransient(solAsociadaEnh);
            }

            pm.makeTransient(liqr.getSolicitud().getCirculo());
            pm.makeTransient(liqr.getSolicitud().getUsuario());

            SolicitudRegistroEnhanced solRegEnh = (SolicitudRegistroEnhanced) liqr.getSolicitud();
            pm.makeTransient(solRegEnh.getSubtipoSolicitud());
            pm.makeTransient(solRegEnh);
            pm.makeTransient(liqr);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (LiquidacionTurnoRegistro) liqr.toTransferObject();
    }

    /**
     * Agrega una liquidacion de registro en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoRegistroEnhanced</code> con sus atributos,
     * exceptuando el identificador.
     * @param s <code>SolicitudRegistro</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return  <code>LiquidacionTurnoRegistroEnhanced</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistroEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRegistro
     * @see gov.sir.core.negocio.modelo.SolicitudRegistroEnhanced
     */
    protected LiquidacionTurnoRegistroEnhanced addLiquidacionToSolicitudRegistro(
            LiquidacionTurnoRegistroEnhanced l, SolicitudRegistroEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionTurnoRegistroEnhanced rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));
        s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);
        l.setSolicitud(s);
        l.setLastIdActo(0);
        l.setIdSolicitud(s.getIdSolicitud());

        if (l.getUsuario() == null) {
            throw new DAOException(
                    "La liquidacion no esta asociada a un Usuario");
        }
        UsuarioEnhanced usuario = l.getUsuario();
        UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
        if (usuarior == null) {
            throw new DAOException(
                    "No encontró el Usuario con el login: "
                    + l.getUsuario());
        }
        l.setUsuario(usuarior);

        try {
            List act = l.getActos();
            Iterator it = act.iterator();

            while (it.hasNext()) {
                ActoEnhanced a = (ActoEnhanced) it.next();
                a.setIdActo(this.getLPAD(String.valueOf(l.getLastIdActo() + 1), 3));
                l.setLastIdActo(l.getLastIdActo() + 1);
                TipoActoEnhanced ta = a.getTipoActo();
                TipoActoEnhancedPk taId = new TipoActoEnhancedPk();
                taId.idTipoActo = ta.getIdTipoActo();
                TipoActoEnhanced tar = variablesDAO.getTipoActoByID(taId, pm);
                if (tar == null) {
                    throw new DAOException(
                            "No encontró el TipoActo con el ID: "
                            + taId.idTipoActo);
                }

                ta.getTiposDerechosRegistrales();
                TipoActoDerechoRegistralEnhanced td = a.getTipoDerechoReg();
                TipoActoDerechoRegistralEnhancedPk tdId = new TipoActoDerechoRegistralEnhancedPk();
                tdId.idTipoActo = td.getIdTipoActo();
                tdId.idTipoDerechoReg = td.getIdTipoDerechoReg();
                TipoActoDerechoRegistralEnhanced tdr = variablesDAO.getTipoActoDerechoByID(tdId,
                        pm);

                if (tdr == null) {
                    throw new DAOException(
                            "No encontró el TipoActoDerechoRegistral con el ID: "
                            + tdId.idTipoDerechoReg + " y el ID Acto: "
                            + tdId.idTipoActo);
                }

                if (a.isCobroImpuestos()) {
                    TipoImpuestoEnhanced ti = a.getTipoImpuesto();
                    TipoImpuestoEnhancedPk tiId = new TipoImpuestoEnhancedPk();
                    tiId.idTipoImpuesto = ti.getIdTipoImpuesto();

                    TipoImpuestoEnhanced tir = variablesDAO.getTipoImpuestoByID(tiId, pm);

                    if (tir == null) {
                        throw new DAOException(
                                "No encontró el TipoImpuesto con el ID: "
                                + tiId.idTipoImpuesto);
                    }
                    a.setTipoImpuesto(tir);
                } else {
                    /*TipoImpuestoEnhanced ti = a.getTipoImpuesto();
					TipoImpuestoEnhanced.ID tiId = new TipoImpuestoEnhanced.ID();
					tiId.idTipoImpuesto = ti.getIdTipoImpuesto();

					TipoImpuestoEnhanced tir = variablesDAO.getTipoImpuestoByID(tiId, pm);

					if (tir == null) {
						throw new DAOException(
							"No encontró el TipoImpuesto con el ID: " +
							tiId.idTipoImpuesto);
					}
                     */
                    a.setTipoImpuesto(null);
                }
                a.setTipoActo(tar);
                a.setFechaCreacion(new Date());
                a.setTipoDerechoReg(tdr);
                a.setLiquidacion(l);
                a.setCirculo(s.getCirculo() != null ? s.getCirculo().getIdCirculo() : null);
                if (l.getFecha() == null) {
                    l.setFecha(new Date());
                }
            }

            rta = l;
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        /**
         * @author : Ricardo Chivata
         * @change : Tarifas - varible porcentual del recibo.
         */
        try {
            List act = l.getActos();
            Iterator it = act.iterator();
            int i = 1;
            while (it.hasNext()) {
                ActoEnhanced a = (ActoEnhanced) it.next();
                try {
                    String ta = "";
                    if (s.getTurnoAnterior() == null) {
                        ta = "0";
                    } else {
                        ta = s.getTurnoAnterior().getIdWorkflow();
                    }
                    System.out.println("SOLICITUD A INSERTAR GERE: " + s.getIdSolicitud() + ta);
                    InsertarConservacion(a, "0", ta, i);
                    i++;
                } catch (Exception e) {
                    Log.getInstance().error("Error insertando datos a la tabla de conservacion documental", e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.getInstance().error("Error lectura de actos aosciados para conservacion", e.getMessage());
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de consultas en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoConsulta</code> con sus atributos,
     * exceptuando el identificador.
     * @param s <code>SolicitudConsulta</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoConsulta</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     */
    public LiquidacionTurnoConsulta addLiquidacionToSolicitudConsulta(
            LiquidacionTurnoConsulta l, SolicitudConsulta s)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        LiquidacionEnhancedPk liqPersId = null;
        LiquidacionTurnoConsultaEnhanced liqPers = null;
        LiquidacionTurnoConsulta liqFinal = null;
        SolicitudEnhancedPk sID = new SolicitudEnhancedPk();
        sID.idSolicitud = s.getIdSolicitud();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se obtiene la solicitud consulta persistente.
            SolicitudConsultaEnhanced sc = (SolicitudConsultaEnhanced) solicitudesDAO.getSolicitudByID(sID,
                    pm);

            if (sc == null) {
                throw new DAOException(
                        "No se encontro la solicitud consulta");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(sc, pm);

            liqPersId = this.addLiquidacionToSolicitudConsulta(LiquidacionTurnoConsultaEnhanced.enhance(
                    l), sc, pm);
            pm.currentTransaction().commit();
            /*
			liqPers = (LiquidacionTurnoConsultaEnhanced) pm.getObjectById(liqPersId,
					true);
             */
            LiquidacionEnhancedPk liqID = new LiquidacionEnhancedPk();
            liqID.idLiquidacion = liqPersId.idLiquidacion;
            liqID.idSolicitud = liqPersId.idSolicitud;

            //Se hace transiente la liquidación y todos sus elementos asociados.
            liqPers = (LiquidacionTurnoConsultaEnhanced) this.getLiquidacionEnhancedById(liqID);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } finally {

            pm.close();
        }

        liqFinal = (LiquidacionTurnoConsulta) liqPers.toTransferObject();
        return liqFinal;
    }

    /**
     * Agrega una liquidacion de consultas en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoConsultaEnhanced</code> con sus atributos,
     * exceptuando el identificador.
     * @param s <code>SolicitudConsulta</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return identificador de la <code>LiquidacionTurnoConsulta</code>
     * generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsultaEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudConsulta
     * @see gov.sir.core.negocio.modelo.SolicitudConsultaEnhanced
     */
    protected LiquidacionEnhancedPk addLiquidacionToSolicitudConsulta(
            LiquidacionTurnoConsultaEnhanced l, SolicitudConsultaEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionEnhancedPk rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        try {
            AlcanceGeograficoEnhanced g = l.getAlcanceGeografico();
            AlcanceGeograficoEnhancedPk gId = new AlcanceGeograficoEnhancedPk();
            gId.idAlcanceGeografico = g.getIdAlcanceGeografico();

            AlcanceGeograficoEnhanced ag = variablesDAO.getTipoAlcanceGeograficoByID(gId,
                    pm);

            if (ag == null) {
                throw new DAOException(
                        "No encontró el AlcanceGeografico con el ID: "
                        + gId.idAlcanceGeografico);
            }

            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);

            //Se asocia el id de la liquidación.
            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));

            //Se incrementa el atributo lastIdLiquidacion de la solicitud asociada.
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);

            //Se asignan los demás atributos de la liquidación.
            l.setSolicitud(s);
            l.setFecha(new Date());
            l.setIdSolicitud(s.getIdSolicitud());
            l.setAlcanceGeografico(ag);

            //Se hace persistente la liquidación.
            pm.makePersistent(l);
            rta = (LiquidacionEnhancedPk) pm.getObjectId(l);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de correcciones en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoCorreccion</code> con sus atributos,
     * exceptuando el identificador.
     * @param s <code>SolicitudCorreccion<code> a la que se debe
     * asociar la <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoCorreccion</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCorreccion
     */
    public LiquidacionTurnoCorreccion addLiquidacionToSolicitudCorreccion(
            LiquidacionTurnoCorreccion l, SolicitudCorreccion s)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        LiquidacionEnhancedPk liqPersId = null;
        LiquidacionTurnoCorreccionEnhanced liqPers = null;
        LiquidacionTurnoCorreccion liqFinal = null;
        SolicitudEnhancedPk sID = new SolicitudEnhancedPk();
        sID.idSolicitud = s.getIdSolicitud();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se obtiene la solicitud de corrección persistente.
            SolicitudCorreccionEnhanced sc = (SolicitudCorreccionEnhanced) solicitudesDAO.getSolicitudByID(sID,
                    pm);

            if (sc == null) {
                throw new DAOException(
                        "No se encontro la solicitud de correccion");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(sc, pm);

            liqPersId = this.addLiquidacionToSolicitudCorreccion(LiquidacionTurnoCorreccionEnhanced.enhance(
                    l), sc, pm);
            pm.currentTransaction().commit();
            liqPers = (LiquidacionTurnoCorreccionEnhanced) pm.getObjectById(liqPersId,
                    true);

            LiquidacionEnhancedPk liqID = new LiquidacionEnhancedPk();
            liqID = (LiquidacionEnhancedPk) pm.getObjectId(liqPers);

            //Se hace transiente la liquidación y todos sus elementos asociados.
            liqPers = (LiquidacionTurnoCorreccionEnhanced) this.getLiquidacionEnhancedById(liqID);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        liqFinal = (LiquidacionTurnoCorreccion) liqPers.toTransferObject();
        return liqFinal;
    }

    /**
     * Agrega una liquidacion de correcciones en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoCorreccionEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudCorreccion</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return identificador de la <code>LiquidacionTurnoCorreccion</code>
     * generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccionEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCorreccion
     * @see gov.sir.core.negocio.modelo.SolicitudCorreccionEnhanced
     */
    protected LiquidacionEnhancedPk addLiquidacionToSolicitudCorreccion(
            LiquidacionTurnoCorreccionEnhanced l, SolicitudCorreccionEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionEnhancedPk rta = null;

        try {
            //Se asocia el id de la liquidación.
            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));

            //Se incrementa el atributo lastIdLiquidacion de la solicitud asociada.
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);

            List act = l.getActos();
            Iterator it = act.iterator();
            JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);

            while (it.hasNext()) {
                ActoEnhanced a = (ActoEnhanced) it.next();
                a.setIdActo(String.valueOf(l.getLastIdActo()));
                a.setIdLiquidacion(l.getIdLiquidacion());
                a.setIdSolicitud(l.getIdSolicitud());
                l.setLastIdActo(l.getLastIdActo() + 1);
                TipoActoEnhanced ta = a.getTipoActo();
                TipoActoEnhancedPk taId = new TipoActoEnhancedPk();
                taId.idTipoActo = ta.getIdTipoActo();
                TipoActoEnhanced tar = variablesDAO.getTipoActoByID(taId, pm);
                if (tar == null) {
                    throw new DAOException(
                            "No encontró el TipoActo con el ID: "
                            + taId.idTipoActo);
                }

                ta.getTiposDerechosRegistrales();
                TipoActoDerechoRegistralEnhanced td = a.getTipoDerechoReg();
                TipoActoDerechoRegistralEnhancedPk tdId = new TipoActoDerechoRegistralEnhancedPk();
                tdId.idTipoActo = td.getIdTipoActo();
                tdId.idTipoDerechoReg = td.getIdTipoDerechoReg();
                TipoActoDerechoRegistralEnhanced tdr = variablesDAO.getTipoActoDerechoByID(tdId,
                        pm);

                if (tdr == null) {
                    throw new DAOException(
                            "No encontró el TipoActoDerechoRegistral con el ID: "
                            + tdId.idTipoDerechoReg + " y el ID Acto: "
                            + tdId.idTipoActo);
                }

                if (a.isCobroImpuestos()) {
                    TipoImpuestoEnhanced ti = a.getTipoImpuesto();
                    TipoImpuestoEnhancedPk tiId = new TipoImpuestoEnhancedPk();
                    tiId.idTipoImpuesto = ti.getIdTipoImpuesto();

                    TipoImpuestoEnhanced tir = variablesDAO.getTipoImpuestoByID(tiId, pm);

                    if (tir == null) {
                        throw new DAOException(
                                "No encontró el TipoImpuesto con el ID: "
                                + tiId.idTipoImpuesto);
                    }
                    a.setTipoImpuesto(tir);
                }
                a.setTipoActo(tar);
                a.setTipoDerechoReg(tdr);
                a.setCirculo(s.getCirculo() != null ? s.getCirculo().getIdCirculo() : null);
                //a.setLiquidacion(l);
            }

            //Se asignan los demás atributos de la liquidación.
            l.setSolicitud(s);
            l.setIdSolicitud(s.getIdSolicitud());
            l.setFecha(new Date());

            //Se hace persistente la liquidación.
            pm.makePersistent(l);
            rta = (LiquidacionEnhancedPk) pm.getObjectId(l);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de devoluciones en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoDevolucionEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudDevolucion</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return  <code>LiquidacionTurnoDevolucionEnhanced</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucionEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudDevolucion
     * @see gov.sir.core.negocio.modelo.SolicitudDevolucionEnhanced
     */
    protected LiquidacionTurnoDevolucionEnhanced addLiquidacionToSolicitudDevolucion(
            LiquidacionTurnoDevolucionEnhanced l, SolicitudDevolucionEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionTurnoDevolucionEnhanced rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        try {
            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);

            //OBTENER E INCREMENTAR SECUENCIA
            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);

            //ASOCIAR LIQUIDACION A LA SOLICITUD
            l.setSolicitud(s);
            l.setFecha(new Date());

            rta = l;
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de Devolución en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoDevolución</code> con sus atributos,
     * exceptuando el identificador.
     * @param sID identificador de la Solicitud de Devolución a la que se debe
     * asociar la <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoDevolución</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudDevolucion
     */
    public LiquidacionTurnoDevolucion addLiquidacionToSolicitudDevolucion(LiquidacionTurnoDevolucion ltd, SolicitudPk sdID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        LiquidacionTurnoDevolucionEnhanced liqr = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            SolicitudDevolucionEnhanced s = (SolicitudDevolucionEnhanced) solicitudesDAO.getSolicitudByID(new SolicitudEnhancedPk(
                    sdID), pm);

            if (s == null) {
                throw new DAOException("No se encontro la solicitud");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(s, pm);

            liqr = this.addLiquidacionToSolicitudDevolucion(LiquidacionTurnoDevolucionEnhanced.enhance(
                    ltd), s, pm);
            pm.makePersistent(liqr);

            LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
            lId.idLiquidacion = liqr.getIdLiquidacion();
            lId.idSolicitud = liqr.getIdSolicitud();
            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (LiquidacionTurnoDevolucion) liqr.toTransferObject();
    }

    /**
     * Agrega una liquidacion de reparto Notarial en el sistema y la asocia a
     * una solicitud
     *
     * @param l <code>LiquidacionTurnoRepartoNotarial</code> con sus atributos,
     * exceptuando el identificador.
     * @param s <code>SolicitudRepartoNotarial</code> a la que se debe asociar
     * la <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoRepartoNotarial</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     */
    public LiquidacionTurnoRepartoNotarial addLiquidacionToSolicitudRepartoNotarial(
            LiquidacionTurnoRepartoNotarial l, SolicitudRepartoNotarial s)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        LiquidacionEnhancedPk liqPersId = null;
        LiquidacionTurnoRepartoNotarialEnhanced liqPers = null;
        LiquidacionTurnoRepartoNotarial liqFinal = null;
        SolicitudEnhancedPk sID = new SolicitudEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        sID.idSolicitud = s.getIdSolicitud();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se obtiene la solicitud de reparto persistente.
            SolicitudRepartoNotarialEnhanced sc = (SolicitudRepartoNotarialEnhanced) solicitudesDAO.getSolicitudByID(sID,
                    pm);

            if (sc == null) {
                throw new DAOException(
                        "No se encontro la solicitud consulta");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(sc, pm);

            liqPersId = this.addLiquidacionToSolicitudRepartoNotarial(LiquidacionTurnoRepartoNotarialEnhanced.enhance(
                    l), sc, pm);
            pm.currentTransaction().commit();
            liqPers = (LiquidacionTurnoRepartoNotarialEnhanced) pm.getObjectById(liqPersId,
                    true);

            LiquidacionEnhancedPk liqID = new LiquidacionEnhancedPk();
            liqID = (LiquidacionEnhancedPk) pm.getObjectId(liqPers);

            //Se hace transiente la liquidación y todos sus elementos asociados.
            liqPers = (LiquidacionTurnoRepartoNotarialEnhanced) this.getLiquidacionEnhancedById(liqID);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        liqFinal = (LiquidacionTurnoRepartoNotarial) liqPers.toTransferObject();
        return liqFinal;
    }

    /**
     * Agrega una liquidacion de Reparto Notarial en el sistema y la asocia a
     * una solicitud
     *
     * @param l <code>LiquidacionTurnoRepartoNotarialEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudRepartoNotarial</code> a la que se debe asociar
     * la <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return identificador de la <code>LiquidacionTurnoRepartoNotarial</code>
     * generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarialEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
     * @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarialEnhanced
     */
    protected LiquidacionEnhancedPk addLiquidacionToSolicitudRepartoNotarial(
            LiquidacionTurnoRepartoNotarialEnhanced l, SolicitudRepartoNotarialEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionEnhancedPk rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        try {

            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);
            //Se asocia el id de la liquidación.
            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));

            //Se incrementa el atributo lastIdLiquidacion de la solicitud asociada.
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);

            //Se asignan los demás atributos de la liquidación.
            l.setSolicitud(s);
            l.setIdSolicitud(s.getIdSolicitud());
            l.setFecha(new Date());

            //Se hace persistente la liquidación.
            pm.makePersistent(l);
            rta = (LiquidacionEnhancedPk) pm.getObjectId(l);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de reparto Notarial en el sistema y la asocia a
     * una solicitud
     *
     * @param l <code>LiquidacionTurnoRestitucionReparto</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudRestitucionReparto</code> a la que se debe
     * asociar la <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoRestitucionReparto</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     */
    public LiquidacionTurnoRestitucionReparto addLiquidacionToSolicitudRestitucionReparto(
            LiquidacionTurnoRestitucionReparto l, SolicitudRestitucionReparto s)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        LiquidacionEnhancedPk liqPersId = null;
        LiquidacionTurnoRestitucionRepartoEnhanced liqPers = null;
        LiquidacionTurnoRestitucionReparto liqFinal = null;
        SolicitudEnhancedPk sID = new SolicitudEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        sID.idSolicitud = s.getIdSolicitud();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se obtiene la solicitud de reparto persistente.
            SolicitudRestitucionRepartoEnhanced sc = (SolicitudRestitucionRepartoEnhanced) solicitudesDAO.getSolicitudByID(sID,
                    pm);

            if (sc == null) {
                throw new DAOException(
                        "No se encontro la solicitud consulta");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(sc, pm);

            liqPersId = this.addLiquidacionToSolicitudRestitucionReparto(LiquidacionTurnoRestitucionRepartoEnhanced.enhance(
                    l), sc, pm);
            pm.currentTransaction().commit();
            liqPers = (LiquidacionTurnoRestitucionRepartoEnhanced) pm.getObjectById(liqPersId,
                    true);

            LiquidacionEnhancedPk liqID = new LiquidacionEnhancedPk();
            liqID = (LiquidacionEnhancedPk) pm.getObjectId(liqPers);

            //Se hace transiente la liquidación y todos sus elementos asociados.
            liqPers = (LiquidacionTurnoRestitucionRepartoEnhanced) this.getLiquidacionEnhancedById(liqID);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        liqFinal = (LiquidacionTurnoRestitucionReparto) liqPers.toTransferObject();
        return liqFinal;
    }

    /**
     * Agrega una liquidacion de restitucion de Reparto en el sistema y la
     * asocia a una solicitud
     *
     * @param l <code>LiquidacionTurnoRestitucionRepartoEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudRestitucionReparto</code> a la que se debe
     * asociar la <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return identificador de la
     * <code>LiquidacionTurnoRestitucionReparto</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
     * @see
     * gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionRepartoEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
     * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoEnhanced
     */
    protected LiquidacionEnhancedPk addLiquidacionToSolicitudRestitucionReparto(
            LiquidacionTurnoRestitucionRepartoEnhanced l, SolicitudRestitucionRepartoEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionEnhancedPk rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        try {

            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);

            //Se asocia el id de la liquidación.
            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));

            //Se incrementa el atributo lastIdLiquidacion de la solicitud asociada.
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);

            //Se asignan los demás atributos de la liquidación.
            l.setSolicitud(s);
            l.setIdSolicitud(s.getIdSolicitud());
            l.setFecha(new Date());

            //Se hace persistente la liquidación.
            pm.makePersistent(l);
            rta = (LiquidacionEnhancedPk) pm.getObjectId(l);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
        }

        return rta;
    }

    /**
     * Obtiene una liquidacion con todos sus atributos, dado su identificador.
     *
     * @param lID identificador de la <code>Liquidacion</code>
     * @return <code>Liquidacion</code> con sus atributos y jerarquia: Pago,
     * Solicitud, Círculo, Proceso, Turno.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
     */
    public Liquidacion getLiquidacionByID(LiquidacionPk lID)
            throws DAOException {
        LiquidacionEnhanced lr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            lr = this.getLiquidacionByID(new LiquidacionEnhancedPk(lID), pm);

            if (lr == null) {
                throw new DAOException(
                        "No se encontró una liquidación con el Id "
                        + lID.idLiquidacion + "-" + lID.idSolicitud);
            }

            if (lr instanceof LiquidacionTurnoCertificadoEnhanced) {
                LiquidacionTurnoCertificadoEnhanced l = (LiquidacionTurnoCertificadoEnhanced) lr;
                pm.makeTransient(l.getTipoCertificado());
            }

            if (lr instanceof LiquidacionTurnoConsultaEnhanced) {
                LiquidacionTurnoConsultaEnhanced l = (LiquidacionTurnoConsultaEnhanced) lr;
                pm.makeTransient(l.getAlcanceGeografico());
            }

            try {
                pm.makeTransient(lr.getPago());
            } catch (JDOObjectNotFoundException eJDO) {
                lr.setPago(null);
            }
            if (lr.getSolicitud() != null) {
                pm.makeTransient(lr.getSolicitud().getCirculo());
                pm.makeTransient(lr.getSolicitud().getProceso());
                pm.makeTransient(lr.getSolicitud().getTurno());
                pm.makeTransient(lr.getSolicitud());
            }

            pm.makeTransient(lr);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (Liquidacion) lr.toTransferObject();
    }

    /**
     * Obtiene una liquidacion con todos sus atributos, dado su identificador.
     * <p>
     * Método utilizado por transacciones.
     *
     * @param lID identificador de la <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return <code>Liquidacion</code> con sus atributos y jerarquia: Pago,
     * Solicitud, Círculo, Proceso, Turno.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
     */
    protected LiquidacionEnhanced getLiquidacionByID(
            LiquidacionEnhancedPk lID, PersistenceManager pm)
            throws DAOException {
        LiquidacionEnhanced rta = null;

        //Se coloca un tiempo de delay 
        JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
        boolean respuesta = jdoDAO.getObjectByLlavePrimaria(lID, 3, pm);

        if ((lID.idSolicitud != null) && (lID.idLiquidacion != null)) {
            try {
                rta = (LiquidacionEnhanced) pm.getObjectById(lID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().debug(JDOLiquidacionesDAO.class, e);
                Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene una liquidacionEnhanced dado su identificador, método utilizado
     * para transacciones.
     *
     * @param lID identificador de la liquidacion
     * @param pm PersistenceManager de la transaccion
     * @return Liquidacion con sus atributos y jerarquia: Pago, Solicitud,
     * Círculo, Proceso y Turno.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
     */
    protected LiquidacionEnhanced getLiquidacionEnhancedById(
            LiquidacionEnhancedPk lID) throws DAOException {

        LiquidacionEnhanced lr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            lr = this.getLiquidacionByID(lID, pm);

            //Hacer transientes todos los elementos de las liquidaciones de certificados.
            if (lr instanceof LiquidacionTurnoCertificadoEnhanced) {
                LiquidacionTurnoCertificadoEnhanced l = (LiquidacionTurnoCertificadoEnhanced) lr;
                pm.makeTransient(l.getTipoCertificado());
            }

            //Hacer transientes todos los elementos propios de las liquidaciones de consultas.
            if (lr instanceof LiquidacionTurnoConsultaEnhanced) {
                LiquidacionTurnoConsultaEnhanced l = (LiquidacionTurnoConsultaEnhanced) lr;

                pm.makeTransient(l.getAlcanceGeografico()); 	//Alcance Geográfico
                pm.makeTransient(lr.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(lr.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                SolicitudConsultaEnhanced sCons = (SolicitudConsultaEnhanced) l.getSolicitud();

                pm.makeTransient(sCons.getTipoConsulta()); //Tipo de Consulta
                pm.makeTransient(sCons.getCiudadano()); //Ciudadano

                //Se vuelve trasient todo lo del Documento
                if (sCons.getDocumento() != null) {
                    //pm.makeTransient(sCons.getDocumento().getOficinaInternacional());
                    pm.makeTransient(sCons.getDocumento().getOficinaOrigen());
                    pm.makeTransient(sCons.getDocumento().getTipoDocumento());
                    pm.makeTransient(sCons.getDocumento());
                }

                //Búsquedas
                List busc = sCons.getBusquedas();
                Iterator itl = busc.iterator();

                while (itl.hasNext()) {
                    BusquedaEnhanced b = (BusquedaEnhanced) itl.next();
                    pm.makeTransient(b);
                }

                pm.makeTransient(sCons);
                sCons.addLiquidacion(lr);
            }

            //Hacer transientes todos los elementos de las liquidaciones de correcciones.
            if (lr instanceof LiquidacionTurnoCorreccionEnhanced) {
                LiquidacionTurnoCorreccionEnhanced l = (LiquidacionTurnoCorreccionEnhanced) lr;
                pm.makeTransient(l.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(l.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                SolicitudCorreccionEnhanced sCons = (SolicitudCorreccionEnhanced) l.getSolicitud();
                pm.makeTransient(sCons.getCiudadano()); //Ciudadano
                pm.makeTransient(sCons);
                sCons.addLiquidacion(lr);
            }

            //Hacer transientes todos los elementos de las liquidaciones de devoluciones
            if (lr instanceof LiquidacionTurnoDevolucionEnhanced) {
                LiquidacionTurnoCorreccionEnhanced l = (LiquidacionTurnoCorreccionEnhanced) lr;
                pm.makeTransient(l.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(l.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                SolicitudDevolucionEnhanced sDev = (SolicitudDevolucionEnhanced) l.getSolicitud();
                pm.makeTransient(sDev.getCiudadano()); //Ciudadano
                pm.makeTransient(sDev);
                sDev.addLiquidacion(lr);
            }

            //Hacer transientes todos los elementos de las liquidaciones de fotocopias
            if (lr instanceof LiquidacionTurnoFotocopiaEnhanced) {

                LiquidacionTurnoFotocopiaEnhanced l = (LiquidacionTurnoFotocopiaEnhanced) lr;
                pm.makeTransient(l.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(l.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                SolicitudFotocopiaEnhanced sDev = (SolicitudFotocopiaEnhanced) l.getSolicitud();
                //Turno
                if (l.getSolicitud() != null) {
                    pm.makeTransient(l.getSolicitud().getTurno());
                }

                //Tipo de Fotocopias.
                //pm.makeTransient(l.getTipoFotocopia());
                pm.makeTransient(sDev);
                sDev.addLiquidacion(lr);
            }

            //Hacer transientes todos los elementos de las liquidaciones de Reparto Notarial
            if (lr instanceof LiquidacionTurnoRepartoNotarialEnhanced) {
                LiquidacionTurnoRepartoNotarialEnhanced l = (LiquidacionTurnoRepartoNotarialEnhanced) lr;
                pm.makeTransient(l.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(l.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                SolicitudRepartoNotarialEnhanced sReparto = (SolicitudRepartoNotarialEnhanced) l.getSolicitud();
                pm.makeTransient(sReparto);
                sReparto.addLiquidacion(lr);
            }

            //Hacer transientes todos los elementos de las liquidaciones de Restitucion Reparto
            if (lr instanceof LiquidacionTurnoRestitucionRepartoEnhanced) {
                LiquidacionTurnoRestitucionRepartoEnhanced l = (LiquidacionTurnoRestitucionRepartoEnhanced) lr;
                pm.makeTransient(l.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(l.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                SolicitudRestitucionRepartoEnhanced sReparto = (SolicitudRestitucionRepartoEnhanced) l.getSolicitud();
                pm.makeTransient(sReparto.getCausalRestitucion());
                pm.makeTransient(sReparto);
                sReparto.addLiquidacion(lr);
            }

            //Liquidacion Registro
            if (lr instanceof LiquidacionTurnoRegistroEnhanced) {
                LiquidacionTurnoRegistroEnhanced l = (LiquidacionTurnoRegistroEnhanced) lr;
                pm.makeTransient(l.getSolicitud().getCirculo()); //Círculo
                pm.makeTransient(l.getSolicitud().getProceso()); //Proceso
                pm.makeTransient(l.getSolicitud().getUsuario()); //Usuario
                List actos = l.getActos();
                Iterator it = actos.iterator();
                while (it.hasNext()) {
                    ActoEnhanced acto = (ActoEnhanced) it.next();
                    pm.makeTransient(acto.getTipoActo());
                    pm.makeTransient(acto);
                }
                SolicitudRestitucionRepartoEnhanced sReparto = (SolicitudRestitucionRepartoEnhanced) l.getSolicitud();
                pm.makeTransient(sReparto.getCausalRestitucion());
                pm.makeTransient(sReparto);
                sReparto.addLiquidacion(lr);
            }
            pm.makeTransient(lr.getUsuario());
            pm.makeTransient(lr);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return lr;
    }

    /**
     * Valida la cuantia de un acto con respecto a lo que se edita. Se tiene en
     * cuenta el id de la solicitud y el id tipo acto. No se tiene en cuenta el
     * id del acto ya que éste cambia cuando se adicionan o eliminan actos lo
     * que no asegura que ase esté trayendo el acto correspondiente
     *
     * @param acto objeto <code>Acto</code> con la información ingresada
     * @param i<code>int</code> posicion del acto
     * @return true o false dependiendo de la validacion.
     * @see gov.sir.core.negocio.modelo.Acto
     * @throws <code>DAOException</code>
     */
    public boolean validacionActo(Acto acto, int i) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        try {
            if (acto.getTipoActo().getIdTipoActo() != null && acto.getIdSolicitud() != null) {
                ActoEnhanced actoenhanced = null;
                pm.currentTransaction().begin();
                Query query = pm.newQuery(ActoEnhanced.class);
                query.declareParameters("String idTipoActo, String idSolicitud");
                query.setFilter("this.idSolicitud == idSolicitud && this.tipoActo.idTipoActo==idTipoActo");
                Collection col = (Collection) query.execute(acto.getTipoActo().getIdTipoActo(), acto.getIdSolicitud());
                boolean iguales = false;
                for (Iterator iter = col.iterator(); iter.hasNext();) {
                    actoenhanced = (ActoEnhanced) iter.next();
                    if (acto.getTipoDerechoReg() != null && actoenhanced.getTipoDerechoReg() != null) {
                        if (acto.getTipoDerechoReg().getIdTipoDerechoReg().equals(
                                actoenhanced.getTipoDerechoReg().getIdTipoDerechoReg())) {
                            iguales = true;
                        } else {
                            iguales = false;
                        }
                    } else if (acto.getTipoDerechoReg() == null && actoenhanced.getTipoDerechoReg() == null) {
                        iguales = true;
                    } else {
                        iguales = false;
                    }
                    if (iguales) {
                        if (acto.getTipoImpuesto() != null && actoenhanced.getTipoImpuesto() != null) {
                            if (acto.getTipoImpuesto().getIdTipoImpuesto().equals(
                                    actoenhanced.getTipoImpuesto().getIdTipoImpuesto())) {
                                iguales = true;
                            } else {
                                iguales = false;
                            }
                        } else if (acto.getTipoImpuesto() == null && actoenhanced.getTipoImpuesto() == null) {
                            iguales = true;
                        } else {
                            iguales = false;
                        }
                    }
                    if (iguales) {
                        if (acto.isCobroImpuestos() == actoenhanced.isCobroImpuestos()) {
                            iguales = true;
                            break;
                        } else {
                            iguales = false;
                        }
                    }
                }
                query.closeAll();
                pm.currentTransaction().commit();
                if (iguales) {
                    if (actoenhanced != null) {
                        if (acto.getCuantia() < actoenhanced.getCuantia()) {
                            if (i != 0) {
                                throw new DAOException("La cuantía ingresada en el acto # " + i + " no puede ser menor a la del acto del turno anterior. ");
                            } else {
                                throw new DAOException("La cuantía ingresada en el acto no puede ser menor a la del acto del turno anterior. ");
                            }
                        }
                    }
                }
                return true;
            }
        } catch (JDOException e) {
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return true;

    }

    /**
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     */
    private double obtenerTarifaRegistral(double cuantia, ArrayList tarifasRegistrales, double salarioMinimo) {
        double cuantiaEnSmmlv = cuantia / salarioMinimo;
        boolean isFound = false;
        TarifaRegistral tarifa = new TarifaRegistral();
        for (int i = 0; i < tarifasRegistrales.size() && !isFound; i++) {
            tarifa = (TarifaRegistral) tarifasRegistrales.get(i);
            if (tarifa.getInicioCuantia() == null) {
                isFound = comparacionFinal(cuantiaEnSmmlv, tarifa.getFinalCuantia(), tarifa.isFinalInclusive());
            } else if (tarifa.getFinalCuantia() == null) {
                isFound = comparacionInicio(cuantiaEnSmmlv, tarifa.getInicioCuantia(), tarifa.isInicioInclusive());
            } else {
                isFound = comparacionInicio(cuantiaEnSmmlv, tarifa.getInicioCuantia(), tarifa.isInicioInclusive())
                        && comparacionFinal(cuantiaEnSmmlv, tarifa.getFinalCuantia(), tarifa.isFinalInclusive());
            }
        }

        if (isFound) {
            if (tarifa.getTipoAplicacionTarifa().equals(TarifaRegistral.ABSOLUTO)) {
                return tarifa.getValorTarifa();
            } else if (tarifa.getTipoAplicacionTarifa().equals(TarifaRegistral.PORCENTUAL)) {
                // Por alguna razon, al multiplicar por 1000 no funcionaba y era como dividir por 100
                return cuantia * (tarifa.getValorTarifa() / 100) / 10;
            }
        }

        return 0;
    }
    

    private double obtenerTarifaRegistralUVT (double cuantia, ArrayList tarifasRegistralesUVT, double uvt) {
        double cuantiaUVT = cuantia / uvt;
        boolean match = false;
        TarifaRegistralUVT tarifa = new TarifaRegistralUVT();
        for (int i = 0; i < tarifasRegistralesUVT.size() && !match; i++) {
            tarifa = (TarifaRegistralUVT) tarifasRegistralesUVT.get(i);
            if (tarifa.getInicioCuantia() == null) {
                match = comparacionUVTFinal(cuantiaUVT, tarifa.getFinalCuantia(), tarifa.isFinalInclusive());
            } else if (tarifa.getFinalCuantia() == null) {
                match = comparacionUVTInicio(cuantiaUVT, tarifa.getInicioCuantia(), tarifa.isInicioInclusive());
            } else {
                match = comparacionUVTInicio(cuantiaUVT, tarifa.getInicioCuantia(), tarifa.isInicioInclusive())
                        && comparacionUVTFinal(cuantiaUVT, tarifa.getFinalCuantia(), tarifa.isFinalInclusive());
            }
        }

        if (match) {
            if (tarifa.getTipoAplicacionTarifa().equals(TarifaRegistral.ABSOLUTO)) {
                return tarifa.getValorTarifa();
            } else if (tarifa.getTipoAplicacionTarifa().equals(TarifaRegistral.PORCENTUAL)) {
                // Por alguna razon, al multiplicar por 1000 no funcionaba y era como dividir por 100
                return cuantia * (tarifa.getValorTarifa() / 100) / 10;
            }
        }

        return 0;
    }
    
    private boolean comparacionUVTInicio(double cuantia, Float valorInicio, boolean inclusive) {
        BigDecimal amount = new BigDecimal(cuantia);
        amount = amount.setScale(5, RoundingMode.HALF_UP);
        BigDecimal startValue = new BigDecimal(valorInicio);
        startValue = startValue.setScale(5, RoundingMode.HALF_UP);
        int result = amount.compareTo(startValue);
        
        if (inclusive) {
            
            switch(result){
                case 1:
                    return true;
                case 0:
                    return true;
                case -1:
                    return false;
                default:
                    return false;
            }
        } else {
             switch(result){
                case 1:
                    return true;
                case 0:
                    return false;
                case -1:
                    return true;
                default:
                    return false;
            }
        }
    }

    private boolean comparacionUVTFinal(double cuantia, Float valorFinal, boolean inclusive) {
        BigDecimal amount = new BigDecimal(cuantia);
        amount = amount.setScale(5, RoundingMode.FLOOR);
        BigDecimal endValue = new BigDecimal(valorFinal);
        endValue = endValue.setScale(2, RoundingMode.HALF_UP);
        int result = amount.compareTo(endValue);
      
        if (inclusive) {
            
            switch(result){
                case 1:
                    return false;
                case 0:
                    return true;
                case -1:
                    return true;
                default:
                    return false;
            }
        } else {
            if (cuantia < valorFinal) {
                return true;
            }
        }
        return false;
    }
    
    private boolean comparacionInicio(double cuantia, Integer valorInicio, boolean inclusive) {
        if (inclusive) {
            if (cuantia >= valorInicio.intValue()) {
                return true;
            }
        } else {
            if (cuantia > valorInicio.intValue()) {
                return true;
            }
        }
        return false;
    }

    private boolean comparacionFinal(double cuantia, Integer valorFinal, boolean inclusive) {
        if (inclusive) {
            if (cuantia <= valorFinal.intValue()) {
                return true;
            }
        } else {
            if (cuantia < valorFinal.intValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el valor a liquidar para un <code>Acto</code> de acuerdo al tipo
     * de <code>Acto</code> y la cuantia.
     *
     * @param acto objeto <code>Acto</code> con la información ingresada
     * @return un objeto <code>Acto</code> con los detalles del valor
     * @see gov.sir.core.negocio.modelo.Acto
     * @throws <code>DAOException</code>
     */
    public Acto getLiquidacionActo(Acto acto) throws DAOException {

        Acto rta = null;
        JDOTarifasDAO tarifasDAO = new JDOTarifasDAO();
        /**
         * @Autor: Santiago Vásquez
         * @Change: 2062.TARIFAS.REGISTRALES.2017
         */
        JDOTarifaRegistralDAO tarifaRegistalDAO = new JDOTarifaRegistralDAO();
        ForsetiFactory factForseti;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        double valorActo = 0;
        double valorImpuesto = 0;
        boolean multa = false;

        try {

            //Validaciones:
            if (acto == null) {
                throw new DAOException("No se puede liquidar el acto.  El acto es null");
            }
            if (acto.getTipoActo() == null) {
                throw new DAOException("No se puede liquidar el acto.  El tipo del acto es null");
            }
            if (acto.getTipoActo().getTipoCalculo() == null) {
                throw new DAOException("No se puede liquidar el acto.  El tipo de calculo es null");
            }

            /**
             * @Autor: Santiago Vásquez
             * @Change: 2062.TARIFAS.REGISTRALES.2017
             */
            /**
             * ****************************************************************
             */
            /*      CALCULO PARA ACTOS CUYO TIPO DE CALCULO ES PORCENTUAL      */
            /**
             * ****************************************************************
             */
            if (acto.getTipoActo().getTipoCalculo().getIdTipoCalculo().equals(CTipoCalculo.PORCENTUAL)) {

                //1. Cálculo del Valor del Acto.
                double cuantia = acto.getCuantia();
//			 	 TarifaEnhanced tarifaActo = tarifasDAO.getTarifa(CActo.TIPO_ACTO, acto.getTipoActo().getNombre(), pm); 
//			 	 //NO SE ENCONTRO VALOR  PARA EL TIPO DE ACTO.
//			 	 if (tarifaActo == null)
//			 	 {
//			 	 	throw new DAOException ("No se encontró valor para la llave: "+CActo.TIPO_ACTO+" - "+acto.getTipoActo().getNombre());
//			 	 }
//			 	 double porcentajeActo = tarifaActo.getValor();
//
//			 	 //NO SE ENCONTRO PORCENTAJE DERECHOS REGISTRALES.
                TarifaEnhanced tarifaDerechoRegistral = tarifasDAO.getTarifa(CActo.DERECHO_REGISTRAL, acto.getTipoDerechoReg().getTipoDerechoRegistral().getNombre(), pm);
                if (tarifaDerechoRegistral == null) {
                    throw new DAOException("No se encontró valor porcentual para la llave: " + CActo.DERECHO_REGISTRAL + " - " + acto.getTipoDerechoReg().getTipoDerechoRegistral().getNombre());
                }
//
//			 	//NO SE ENCONTRO EL VALOR DEL MINDER.
                double porcentajeTarifa = tarifaDerechoRegistral.getValor();
//
//
//			 	 //SE OBTIENE EL VALOR DEL MINDER PARA EL CIRCULO DADO.
//				TarifaEnhanced tarifaMinder = tarifasDAO.getTarifa(CTipoTarifa.REGISTRO_MINDER, acto.getLiquidacion().getSolicitud().getCirculo().getNombre()+'_'+acto.getLiquidacion().getSolicitud().getCirculo().getIdCirculo(), pm);
//				if (tarifaMinder == null)
//				{
//					throw new DAOException ("No se encontró valor del minder para la llave: "+CTipoTarifa.REGISTRO_MINDER+" - "+acto.getLiquidacion().getSolicitud().getCirculo().getNombre());
//				}
//			 	double minder = tarifaMinder.getValor();
//
//
//				 //Se obtiene el valor del acto por el porcentaje del tipo de acto.
//				 double calculoInicial = cuantia * porcentajeActo;
//
//				   //Si el valor anterior es menor que MINDER, se cobra el valor MINDER.
//				   if (calculoInicial < minder)
//				   {
//				   	   calculoInicial = minder;
//				   }
//
//				   //Se aplica el porcentaje de tarifa al valor obtenido anteriormente.
//				   valorActo = calculoInicial * porcentajeTarifa;

                /**
                 * @Autor: Santiago Vásquez
                 * @Change: 2062.TARIFAS.REGISTRALES.2017
                 */
                
                boolean isTarifaUVT = tarifasDAO.isTarifaUVT();
                TarifaEnhanced salarioMinimo = tarifasDAO.getTarifa(CTipoTarifa.CONFIGURACION, CTipoTarifa.SALARIO_MINIMO, pm);
                    if (salarioMinimo == null) {
                        throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.CONFIGURACION + "_" + CTipoTarifa.SALARIO_MINIMO);
                    }
                if(isTarifaUVT){
                    TarifaEnhanced uvt = tarifasDAO.getTarifa(CTipoTarifa.CONFIGURACION, CTipoTarifa.VALOR_UVT, pm);
                    if (uvt == null) {
                    throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.CONFIGURACION + "_" + CTipoTarifa.VALOR_UVT);
                    }
                    ArrayList tarifasRegistralesUVT = tarifaRegistalDAO.getTarifasRegistralesUVT();
                    valorActo = obtenerTarifaRegistralUVT(acto.getCuantia(), tarifasRegistralesUVT, uvt.getValor());
                } else{
                    ArrayList tarifasRegistrales = tarifaRegistalDAO.getTarifasRegistrales();
                    valorActo = obtenerTarifaRegistral(acto.getCuantia(), tarifasRegistrales, salarioMinimo.getValor());
                }
                

                valorActo = valorActo * porcentajeTarifa;

                //2. Cálculo de los Impuestos Asociados con el Acto
                //ACTO CON IMPUESTO BASADO EN CUANTIA
                if (acto.isCobroImpuestos()) {
                    if (acto.getTipoActo().isImpPorCuantia()) {
                        /**
                         * @author: Fernando Padilla Velez
                         * @change: Modificado para el caso MANTIS
                         * 135_141_Impuesto Meta, Se modifica la formula para el
                         * calculo del valor del impuesto cuando el acto sea el
                         * 38-VIVIENDA DE INTERES SOCIAL.
                         */
                        if (acto.getTipoActo() != null
                                && acto.getTipoActo().getIdTipoActo() != null
                                && "38".equals(acto.getTipoActo().getIdTipoActo())
                                && "IM".equals(acto.getTipoImpuesto().getIdTipoImpuesto())) {
                            TarifaEnhanced porcImpuesto = tarifasDAO.getTarifa(CTipoTarifa.PORCENTAJE_IMPUESTO, acto.getTipoImpuesto().getIdTipoImpuesto(), pm);
                            double porcentajeImpuesto = porcImpuesto.getValor();
                            valorImpuesto = cuantia * porcentajeImpuesto;
                        } else {
                            //SE  OBTIENE EL VALOR DEL IMPUESTO BASADO EN CUANTIA
                            TarifaEnhanced tarifaImpuesto = tarifasDAO.getTarifa(CTipoTarifa.TIPO_IMPUESTO_ACTO, acto.getTipoActo().getNombre() + "_" + acto.getLiquidacion().getSolicitud().getCirculo().getNombre(), pm);
                            if (tarifaImpuesto == null) {
                                throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.TIPO_IMPUESTO_ACTO + " - " + acto.getTipoActo().getNombre() + "_" + acto.getLiquidacion().getSolicitud().getCirculo().getNombre());
                            }
                            double impuesto = tarifaImpuesto.getValor();
                            //Se obtiene el tipo de impuesto y su factor de multiplicación (exento, medio, normal)
                            TarifaEnhanced porcImpuesto = tarifasDAO.getTarifa(CTipoTarifa.PORCENTAJE_IMPUESTO, acto.getTipoImpuesto().getIdTipoImpuesto(), pm);
                            double porcentajeImpuesto = porcImpuesto.getValor();

                            valorImpuesto = (impuesto * cuantia) * porcentajeImpuesto;
                        }
                    } //ACTO CON IMPUESTO NO BASADO EN CUANTIA
                    else {

                        //SE  OBTIENE EL VALOR DEL IMPUESTO NO BASADO EN CUANTIA
                        /**
                         * @Autor: Santiago Vásquez
                         * @Change: 2062.TARIFAS.REGISTRALES.2017
                         */
                        //1. Valor del salario mínimo.
//						   TarifaEnhanced salarioMinimo = tarifasDAO.getTarifa(CTipoTarifa.CONFIGURACION, CTipoTarifa.SALARIO_MINIMO, pm);
//						   if (salarioMinimo == null)
//						   {
//								throw new DAOException ("No se encontró valor de impuestos para la llave: "+CTipoTarifa.CONFIGURACION +"_"+ CTipoTarifa.SALARIO_MINIMO);
//						   }
                        //2. Número de salarios mínimos para el valimp en el círculo dado. 
                        TarifaEnhanced numSalariosValimpCirculo = tarifasDAO.getTarifa(CTipoTarifa.REGISTRO_VALIMP, acto.getLiquidacion().getSolicitud().getCirculo().getNombre(), pm);
                        if (numSalariosValimpCirculo == null) {
                            throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.REGISTRO_VALIMP + "_" + CTipoTarifa.SALARIO_MINIMO);
                        }

                        //Se obtiene el VALIMP realizando la multiplicación correspondiente entre el salario
                        //mínimo y el número de salarios que aplican para el círculo. 
                        double impuesto = salarioMinimo.getValor() * numSalariosValimpCirculo.getValor();
                        //Se obtiene el tipo de impuesto y su factor de multiplicación (exento, medio, normal)
                        TarifaEnhanced porcImpuesto = tarifasDAO.getTarifa(CTipoTarifa.PORCENTAJE_IMPUESTO, acto.getTipoImpuesto().getIdTipoImpuesto(), pm);
                        double porcentajeImpuesto = porcImpuesto.getValor();

                        valorImpuesto = impuesto * porcentajeImpuesto;
                    }
                }

            }

            /**
             * ****************************************************************
             */
            /*      CALCULO PARA ACTOS CUYO TIPO DE CALCULO ES CARGO FIJO      */
            /**
             * ****************************************************************
             */
            if (acto.getTipoActo().getTipoCalculo().getIdTipoCalculo().equals(CTipoCalculo.CARGO_FIJO)) {

                //	SE  OBTIENE EL VALOR DEL CARGO FIJO
                TarifaEnhanced tarifaCargoFijo = tarifasDAO.getTarifa(CActo.TIPO_ACTO, acto.getTipoActo().getNombre(), pm);
                if (tarifaCargoFijo == null) {
                    throw new DAOException("No se encontró valor de cargo fijo para la llave: " + CActo.TIPO_ACTO + " - " + acto.getTipoActo().getNombre());
                }
                double cargoFijo = tarifaCargoFijo.getValor();

                //	SE  OBTIENE EL VALOR DEL PORCENTAJE DE TARIFA
                TarifaEnhanced tarifaPorcentaje = tarifasDAO.getTarifa(CActo.DERECHO_REGISTRAL, acto.getTipoDerechoReg().getTipoDerechoRegistral().getNombre(), pm);
                if (tarifaPorcentaje == null) {
                    throw new DAOException("No se encontró valor del porcentaje de tarifa para la llave: " + CActo.DERECHO_REGISTRAL + " - " + acto.getTipoDerechoReg().getTipoDerechoRegistral().getNombre());
                }
                double porcentajeTarifa = tarifaPorcentaje.getValor();

                //Se aplica el porcentaje de tarifa al cargo fijo y se multiplica por el número de actos.
                /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                 * Para los tipos de acto con cargo fijo primero se redondea la tarifa y luego se calcula con el numero de actos.
                 */
                valorActo = roundValue(cargoFijo * porcentajeTarifa);
                valorActo = valorActo * acto.getCuantia();

                //2. Cálculo de los Impuestos Asociados con el Acto
                if (acto.isCobroImpuestos()) {

                    //SE  OBTIENE EL VALOR DEL IMPUESTO NO BASADO EN CUANTIA
                    //1. Valor del salario mínimo.
                    TarifaEnhanced salarioMinimo = tarifasDAO.getTarifa(CTipoTarifa.CONFIGURACION, CTipoTarifa.SALARIO_MINIMO, pm);
                    if (salarioMinimo == null) {
                        throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.CONFIGURACION + "_" + CTipoTarifa.SALARIO_MINIMO);
                    }

                    //2. Número de salarios mínimos para el valimp en el círculo dado. 
                    TarifaEnhanced numSalariosValimpCirculo = tarifasDAO.getTarifa(CTipoTarifa.REGISTRO_VALIMP, acto.getLiquidacion().getSolicitud().getCirculo().getNombre(), pm);
                    if (numSalariosValimpCirculo == null) {
                        throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.REGISTRO_VALIMP + "_" + CTipoTarifa.SALARIO_MINIMO);
                    }

                    //	Se obtiene el VALIMP realizando la multiplicación correspondiente entre el salario
                    //mínimo y el número de salarios que aplican para el círculo. 
                    double impuesto = salarioMinimo.getValor() * numSalariosValimpCirculo.getValor();

                    //Se obtiene el tipo de impuesto y su factor de multiplicación (exento, medio, normal)
                    TarifaEnhanced porcImpuesto = tarifasDAO.getTarifa(CTipoTarifa.PORCENTAJE_IMPUESTO, acto.getTipoImpuesto().getIdTipoImpuesto(), pm);
                    double porcentajeImpuesto = porcImpuesto.getValor();

                    valorImpuesto = (impuesto * acto.getCuantia()) * porcentajeImpuesto;
                }

            }

            /**
             * ****************************************************************
             */
            /*       CALCULO PARA ACTOS CUYO TIPO DE CALCULO ES MANUAL         */
            /**
             * ****************************************************************
             */
            if (acto.getTipoActo().getTipoCalculo().getIdTipoCalculo().equals(CTipoCalculo.MANUAL)) {
                valorActo = acto.getValor();

                //2. Cálculo de los Impuestos Asociados con el Acto
                if (acto.isCobroImpuestos()) {

                    //Se obtiene el tipo de impuesto y su factor de multiplicación (exento, medio, normal)
                    TarifaEnhanced porcImpuesto = tarifasDAO.getTarifa(CTipoTarifa.PORCENTAJE_IMPUESTO, acto.getTipoImpuesto().getIdTipoImpuesto(), pm);
                    double porcentajeImpuesto = porcImpuesto.getValor();
                    valorImpuesto = acto.getValorImpuestos() * porcentajeImpuesto;
                }

            }

            /**
             * @Autor: Santiago Vásquez
             * @Change: 2062.TARIFAS.REGISTRALES.2017
             */
            /**
             * ******************************************************************
             */
            /*       CALCULO PARA ACTOS QUE APLIQUEN INCENTIVO REGISTRAL         */
            /**
             * ******************************************************************
             */
            if (acto.isIncentivoRegistral() && acto.getTipoActo().isIncentivoRegistral()) {
                TarifaEnhanced tarifaDerechoRegistral = tarifasDAO.getTarifa(CActo.DERECHO_REGISTRAL, acto.getTipoDerechoReg().getTipoDerechoRegistral().getNombre(), pm);
                if (tarifaDerechoRegistral == null) {
                    throw new DAOException("No se encontró valor porcentual para la llave: " + CActo.DERECHO_REGISTRAL + " - " + acto.getTipoDerechoReg().getTipoDerechoRegistral().getNombre());
                }

                double porcentajeTarifa = tarifaDerechoRegistral.getValor();

                TarifaEnhanced tarifaIncentivo = tarifasDAO.getTarifa(CTipoTarifa.REGISTRO_DERECHOS, CTarifa.INCENTIVO_REGISTRAL, pm);
                if (tarifaIncentivo == null) {
                    throw new DAOException("No se encontró valor de impuestos para la llave: " + CTipoTarifa.TIPO_IMPUESTO_ACTO + " - " + acto.getTipoActo().getNombre() + "_" + acto.getLiquidacion().getSolicitud().getCirculo().getNombre());
                }
                double incentivo = tarifaIncentivo.getValor();

                valorActo = porcentajeTarifa * incentivo;
            }

            //REDONDEAR LOS VALORES DE REGISTRO Y DE IMPUESTOS A LA CENTENA. 
            //Redondeo de los derechos de registro.
            /**
             * @author Fernando Padilla
             * @change Caso Mantis 0003831: Acta - Requerimiento No 170 -
             * Tarifas Resolucion_81_2010-imm. Se aplica funcion de redondeo a
             * la centena mas próxima.
             */
            //valorActo = redondeoCentenaMasCercana(valorActo);
            valorActo = roundValue(valorActo);

            //Redondeo de los impuestos.
            valorImpuesto = roundValue(valorImpuesto);

            //ASIGNAR LOS ATRIBUTOS AL ACTO
            acto.setValor(valorActo);
            acto.setValorImpuestos(valorImpuesto);

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return acto;
    }

    /**
     * Agrega una liquidacion de certificados masivos en el sistema y la asocia
     * a una solicitud
     *
     * @param l <code>LiquidacionTurnoCertificadosMasivosEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudCertificadosMasivos</code> a la que se debe
     * asociar la <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return identificador de la
     * <code>LiquidacionTurnoCertificadoMasivo</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo
     * @see
     * gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivoEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo
     * @see gov.sir.core.negocio.modelo.SolicitudCertificadoMasivoEnhanced
     */
    protected LiquidacionTurnoCertificadoMasivoEnhanced addLiquidacionToSolicitudCertificadoMasivo(
            LiquidacionTurnoCertificadoMasivoEnhanced l, SolicitudCertificadoMasivoEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionTurnoCertificadoMasivoEnhanced rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        TipoCertificadoEnhanced t = l.getTipoCertificado();
        TipoCertificadoEnhancedPk tId = new TipoCertificadoEnhancedPk();
        tId.idTipoCertificado = t.getIdTipoCertificado();

        TipoCertificadoEnhanced tr = variablesDAO.getTipoCertificadoByID(tId,
                pm);

        if (tr == null) {
            throw new DAOException(
                    "No encontró el TipoCertificado con el ID: "
                    + tId.idTipoCertificado);
        }

        if (l.getUsuario() == null) {
            throw new DAOException(
                    "La liquidacion no esta asociada a un Usuario");
        }
        UsuarioEnhanced usuario = l.getUsuario();
        UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
        if (usuarior == null) {
            throw new DAOException(
                    "No encontró el Usuario con el login: "
                    + l.getUsuario());
        }
        l.setUsuario(usuarior);

        l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));
        s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);
        l.setSolicitud(s);
        l.setTipoCertificado(tr);
        l.setIdSolicitud(s.getIdSolicitud());
        l.setFecha(new Date());

        try {

            rta = l;
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Agrega una liquidacion de Fotocopias en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoFotocopia</code> con sus atributos,
     * exceptuando el identificador.
     * @param s <code>SolicitudFotocopia</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @return  <code>LiquidacionTurnoFotocopia</code> generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopia
     */
    public LiquidacionTurnoFotocopia addLiquidacionToSolicitudFotocopia(
            LiquidacionTurnoFotocopia l, SolicitudFotocopia s)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        LiquidacionEnhancedPk liqPersId = null;
        LiquidacionTurnoFotocopiaEnhanced liqPers = null;
        LiquidacionTurnoFotocopia liqFinal = null;
        SolicitudEnhancedPk sID = new SolicitudEnhancedPk();
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
        LiquidacionTurnoFotocopia liquidacionFinal = null;
        sID.idSolicitud = s.getIdSolicitud();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se obtiene la solicitud de fotocopia persistente.
            SolicitudFotocopiaEnhanced sc = (SolicitudFotocopiaEnhanced) solicitudesDAO.getSolicitudByID(sID, pm);

            if (sc == null) {
                throw new DAOException(
                        "No se encontro la solicitud de fotocopias");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(sc, pm);

            liqPersId = this.addLiquidacionToSolicitudFotocopia(LiquidacionTurnoFotocopiaEnhanced.enhance(
                    l), sc, pm);
            pm.currentTransaction().commit();
            liqPers = (LiquidacionTurnoFotocopiaEnhanced) pm.getObjectById(liqPersId,
                    true);

            LiquidacionEnhancedPk liqID = new LiquidacionEnhancedPk();
            liqID = (LiquidacionEnhancedPk) pm.getObjectId(liqPers);

            LiquidacionPk liquiID = new LiquidacionPk();
            liquiID.idLiquidacion = liqID.idLiquidacion;
            liquiID.idSolicitud = liqID.idSolicitud;

            //Se hace transiente la liquidación y todos sus elementos asociados.
            //liqPers = (LiquidacionTurnoFotocopiaEnhanced) this.getLiquidacionByID(liqID,pm);
            liquidacionFinal = (LiquidacionTurnoFotocopia) this.getLiquidacionByID(liquiID);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return liquidacionFinal;
    }

    /**
     * Agrega una liquidacion de Fotocopia en el sistema y la asocia a una
     * solicitud
     *
     * @param l <code>LiquidacionTurnoFotocopiaEnhanced</code> con sus
     * atributos, exceptuando el identificador.
     * @param s <code>SolicitudFotocopia</code> a la que se debe asociar la
     * <code>Liquidacion</code>
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return identificador de la <code>LiquidacionTurnoFotocopia</code>
     * generada.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Liquidacion
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
     * @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopiaEnhanced
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopia
     * @see gov.sir.core.negocio.modelo.SolicitudFotocopiaEnhanced
     */
    protected LiquidacionEnhancedPk addLiquidacionToSolicitudFotocopia(
            LiquidacionTurnoFotocopiaEnhanced l, SolicitudFotocopiaEnhanced s,
            PersistenceManager pm) throws DAOException {

        LiquidacionEnhancedPk rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        try {

            if (l.getUsuario() == null) {
                throw new DAOException(
                        "La liquidacion no esta asociada a un Usuario");
            }
            UsuarioEnhanced usuario = l.getUsuario();
            UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
            if (usuarior == null) {
                throw new DAOException(
                        "No encontró el Usuario con el login: "
                        + l.getUsuario());
            }
            l.setUsuario(usuarior);

            //Se asocia el id de la liquidación.
            l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));

            //Se incrementa el atributo lastIdLiquidacion de la solicitud asociada.
            s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);

            //Se asignan los demás atributos de la liquidación.
            l.setSolicitud(s);
            l.setIdSolicitud(s.getIdSolicitud());
            l.setFecha(new Date());

            //Se hace persistente la liquidación.
            pm.makePersistent(l);
            rta = (LiquidacionEnhancedPk) pm.getObjectId(l);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
     * anteriores.
     *
     * @param tID identificador del <code>Turno</code>
     * @return valor <code>long</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public double getValorByTurno(TurnoPk tId) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        double valor = 0;
        try {
            TurnoEnhanced t = turnosDAO.getTurnoByID(new TurnoEnhancedPk(tId), pm);
            if (t == null) {
                return 0;
            }

            SolicitudEnhanced sol = t.getSolicitud();

            LiquidacionEnhanced liq;
            PagoEnhanced pago;
            double valorAux;
            for (Iterator it = sol.getLiquidaciones().iterator(); it.hasNext();) {
                liq = (LiquidacionEnhanced) it.next();
                pago = null;
                valorAux = liq.getValor();
                try {
                    pago = liq.getPago();
                } catch (JDOObjectNotFoundException e) {
                    pago = null;
                }

                if (pago != null) {
                    valor = valor + valorAux;
                }
            }

            //Se calcula para los turnos anteriores
            if (sol.getTurnoAnterior() != null) {
                t = sol.getTurnoAnterior();
                TurnoPk tId2 = new TurnoPk();
                tId2.anio = t.getAnio();
                tId2.idCirculo = t.getIdCirculo();
                tId2.idProceso = t.getIdProceso();
                tId2.idTurno = t.getIdTurno();
                valor = valor + this.getValorByTurno(tId2);
            }

        } catch (Throwable e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return valor;
    }

    /**
     * Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
     * anteriores.
     *
     * @param tID identificador del <code>Turno</code>
     * @return valor <code>long</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public double getValorDerechosByTurno(TurnoPk tId) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        double valor = 0;
        try {
            TurnoEnhanced t = turnosDAO.getTurnoByID(new TurnoEnhancedPk(tId), pm);
            if (t == null) {
                return 0;
            }

            if (t.getIdProceso() != Integer.parseInt(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno no es de registro");
            }

            SolicitudRegistroEnhanced sol = (SolicitudRegistroEnhanced) t.getSolicitud();

            LiquidacionTurnoRegistroEnhanced liq;
            PagoEnhanced pago;
            double valorAux;
            for (Iterator it = sol.getLiquidaciones().iterator(); it.hasNext();) {
                liq = (LiquidacionTurnoRegistroEnhanced) it.next();
                pago = null;
                valorAux = liq.getValorDerechos();
                try {
                    pago = liq.getPago();
                } catch (JDOObjectNotFoundException e) {
                    pago = null;
                }

                if (pago != null) {
                    valor = valor + valorAux;
                }
            }

            //Se calcula para los turnos anteriores
            if (sol.getTurnoAnterior() != null) {
                t = sol.getTurnoAnterior();
                TurnoPk tId2 = new TurnoPk();
                tId2.anio = t.getAnio();
                tId2.idCirculo = t.getIdCirculo();
                tId2.idProceso = t.getIdProceso();
                tId2.idTurno = t.getIdTurno();
                valor = valor + this.getValorDerechosByTurno(tId2);
            }

        } catch (Throwable e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return valor;
    }

    /**
     * Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
     * anteriores.
     *
     * @param tID identificador del <code>Turno</code>
     * @return valor <code>long</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public double getValorImpuestosByTurno(TurnoPk tId) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        double valor = 0;
        try {
            TurnoEnhanced t = turnosDAO.getTurnoByID(new TurnoEnhancedPk(tId), pm);
            if (t == null) {
                return 0;
            }

            if (t.getIdProceso() != Integer.parseInt(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno no es de registro");
            }

            SolicitudRegistroEnhanced sol = (SolicitudRegistroEnhanced) t.getSolicitud();

            LiquidacionTurnoRegistroEnhanced liq;
            PagoEnhanced pago;
            double valorAux;
            for (Iterator it = sol.getLiquidaciones().iterator(); it.hasNext();) {
                liq = (LiquidacionTurnoRegistroEnhanced) it.next();
                pago = null;
                valorAux = liq.getValorImpuestos();
                try {
                    pago = liq.getPago();
                } catch (JDOObjectNotFoundException e) {
                    pago = null;
                }

                if (pago != null) {
                    valor = valor + valorAux;
                }
            }

            //Se calcula para los turnos anteriores
            if (sol.getTurnoAnterior() != null) {
                t = sol.getTurnoAnterior();
                TurnoPk tId2 = new TurnoPk();
                tId2.anio = t.getAnio();
                tId2.idCirculo = t.getIdCirculo();
                tId2.idProceso = t.getIdProceso();
                tId2.idTurno = t.getIdTurno();
                valor = valor + this.getValorImpuestosByTurno(tId2);
            }

        } catch (Throwable e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return valor;
    }

    /**
     * Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
     * anteriores.
     *
     * @param tID identificador del <code>Turno</code>
     * @return valor <code>long</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public double getValorOtroImpuestoByTurno(TurnoPk tId) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        double valor = 0;
        try {
            TurnoEnhanced t = turnosDAO.getTurnoByID(new TurnoEnhancedPk(tId), pm);
            if (t == null) {
                return 0;
            }

            if (t.getIdProceso() != Integer.parseInt(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno no es de registro");
            }

            SolicitudRegistroEnhanced sol = (SolicitudRegistroEnhanced) t.getSolicitud();

            LiquidacionTurnoRegistroEnhanced liq;
            PagoEnhanced pago;
            double valorAux;
            for (Iterator it = sol.getLiquidaciones().iterator(); it.hasNext();) {
                liq = (LiquidacionTurnoRegistroEnhanced) it.next();
                pago = null;
                valorAux = liq.getValorOtroImp();
                try {
                    pago = liq.getPago();
                } catch (JDOObjectNotFoundException e) {
                    pago = null;
                }

                if (pago != null) {
                    valor = valor + valorAux;
                }
            }

            //Se calcula para los turnos anteriores
            if (sol.getTurnoAnterior() != null) {
                t = sol.getTurnoAnterior();
                TurnoPk tId2 = new TurnoPk();
                tId2.anio = t.getAnio();
                tId2.idCirculo = t.getIdCirculo();
                tId2.idProceso = t.getIdProceso();
                tId2.idTurno = t.getIdTurno();
                valor = valor + this.getValorOtroImpuestoByTurno(tId2);
            }

        } catch (Throwable e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return valor;
    }

    /**
     * Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
     * anteriores.
     *
     * @param tID identificador del <code>Turno</code>
     * @return valor <code>long</code>
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Turno
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public double getValorMoraByTurno(TurnoPk tId) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
        double valor = 0;
        try {
            TurnoEnhanced t = turnosDAO.getTurnoByID(new TurnoEnhancedPk(tId), pm);
            if (t == null) {
                return 0;
            }

            if (t.getIdProceso() != Integer.parseInt(CProceso.PROCESO_REGISTRO)) {
                throw new DAOException("El turno no es de registro");
            }

            SolicitudRegistroEnhanced sol = (SolicitudRegistroEnhanced) t.getSolicitud();

            LiquidacionTurnoRegistroEnhanced liq;
            PagoEnhanced pago;
            double valorAux;
            for (Iterator it = sol.getLiquidaciones().iterator(); it.hasNext();) {
                liq = (LiquidacionTurnoRegistroEnhanced) it.next();
                pago = null;
                valorAux = liq.getValorMora();
                try {
                    pago = liq.getPago();
                } catch (JDOObjectNotFoundException e) {
                    pago = null;
                }

                if (pago != null) {
                    valor = valor + valorAux;
                }
            }

            //Se calcula para los turnos anteriores
            if (sol.getTurnoAnterior() != null) {
                t = sol.getTurnoAnterior();
                TurnoPk tId2 = new TurnoPk();
                tId2.anio = t.getAnio();
                tId2.idCirculo = t.getIdCirculo();
                tId2.idProceso = t.getIdProceso();
                tId2.idTurno = t.getIdTurno();
                valor = valor + this.getValorMoraByTurno(tId2);
            }

        } catch (Throwable e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return valor;
    }

    /**
     * Obtiene el listado de las Solicitudes sin turnos asociados dentro de un
     * rango de fechas dado.
     *
     * @param fechaInicial Fecha de inicio del rango
     * @param fechaFinal Fecha de finalización del rango
     * @return Lista con todas las solicitudes sin turnos asociados, dentro del
     * rango de fechas recibido como parámetro.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>DAOException</code>
     */
    public List getSolicitudesSinTurno(Date fechaInicial, Date fechaFinal) throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {

            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            Query query = pm.newQuery(SolicitudRegistroEnhanced.class);
            query.declareVariables(" TurnoEnhanced turno");
            query.declareParameters("Date fechaInicial, Date fechaFinal");
            query.setFilter("this.fecha>= fechaInicial && \n" + "this.fecha<= fechaFinal && 	\n"
                    + "!(turno.solicitud==this && turno.idCirculo==this.circulo)");
            Collection col = (Collection) query.execute(fechaInicial, fechaFinal);

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                SolicitudRegistroEnhanced solicitudRegistro = (SolicitudRegistroEnhanced) iter.next();
                pm.makeTransient(solicitudRegistro.getCiudadano());
                pm.makeTransient(solicitudRegistro.getDocumento().getTipoDocumento());
                pm.makeTransient(solicitudRegistro.getDocumento());
                pm.makeTransient(solicitudRegistro);
                lista.add(solicitudRegistro);
            }

            pm.currentTransaction().commit();
            query.close(col);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Elimina la <code>Solicitud</code> recibida como parámetro.
     *
     * @param solicitud La <code>Solicitud</code> que va a ser eliminada.
     * @return <code>true</code> o <code>false</code> dependiendo del resultado
     * de la operación.
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @throws <code>DAOException</code>
     */
    public boolean deleteSolicitud(Solicitud solicitud) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        SolicitudRegistroEnhanced eliminado = (SolicitudRegistroEnhanced) SolicitudRegistroEnhanced.enhance(solicitud);

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();


            /*
			VersantQuery query = (VersantQuery)pm.newQuery(CirculoFestivoEnhanced.class);
			query.declareParameters("String idCir, Date fechaFrom, Date fechaTo");
			query.setFilter("this.idCirculo == idCir &&\n"+
					"this.fechaFestivo> fechaFrom &&\n"+
					"this.fechaFestivo<= fechaTo");
			query.setResult("count(*)");
			rta = (Long)query.execute(cirVal.getIdCirculo(), fechaInicial, fechaFinal);
             */
            Query query = pm.newQuery(SolicitudVinculadaEnhanced.class);
            query.declareParameters("String idSol");
            query.setFilter("this.idSolicitud == idSol");
            Collection col = (Collection) query.execute(solicitud.getIdSolicitud());

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                SolicitudVinculadaEnhanced solVinculada = (SolicitudVinculadaEnhanced) iter.next();
                pm.deletePersistent(solVinculada);
            }

            //	Traer objeto Persistente
            SolicitudEnhancedPk idSol = new SolicitudEnhancedPk();
            idSol.idSolicitud = eliminado.getIdSolicitud();

            eliminado = null;

            try {
                eliminado = (SolicitudRegistroEnhanced) pm.getObjectById(idSol, true);
            } catch (JDOObjectNotFoundException e) {
                eliminado = null;
            }

            if (eliminado == null) {
                throw new DAOException(
                        "No existe la solicitud con el id " + idSol.getSolicitudID());
            }

            //Se mira si tiene solicitudes hijas asociadas para eliminarlas
            SolicitudEnhanced solAsociada;
            if (!eliminado.getSolicitudesHijas().isEmpty()) {
                for (Iterator it = eliminado.getSolicitudesHijas().iterator(); it.hasNext();) {
                    solAsociada = ((SolicitudAsociadaEnhanced) it.next()).getSolicitudHija();
                    pm.deletePersistent(solAsociada);
                }
            }

            SolicitudVinculadaEnhanced solVinculada = null;
            if (!eliminado.getSolicitudesVinculadas().isEmpty()) {
                for (Iterator it = eliminado.getSolicitudesVinculadas().iterator(); it.hasNext();) {
                    solVinculada = (SolicitudVinculadaEnhanced) it.next();
                    pm.deletePersistent(solVinculada);
                }
            }

            CiudadanoEnhanced ciu = eliminado.getCiudadano();
            if (ciu.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                pm.deletePersistent(ciu);
            }
            pm.deletePersistent(eliminado);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Este servicio permite agregar una liquidación, cuyo valor ha sido
     * ingresado por el usuario a una solicitud.
     *
     * @param solicitud La <code>Solicitud</code> a la cual se va a agregar la
     * liquidación.
     * @param liquidacion La <code>Liquidacion</code> que va a ser agregada a la
     * solicitud.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public boolean addLiquidacionToSolicitud(Solicitud solicitud, Liquidacion liquidacion) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        LiquidacionEnhanced liqPers = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        //SE OBTIENE EL IDENTIFICADOR DE LA SOLICITUD.
        SolicitudEnhancedPk solId = new SolicitudEnhancedPk();
        solId.idSolicitud = solicitud.getIdSolicitud();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            SolicitudEnhanced s = (SolicitudEnhanced) pm.getObjectById(solId, true);

            if (s == null) {
                throw new DAOException("No se encontro la solicitud con el identificador dado.");
            }

            //Se valida que todas las liquidaciones actuales tengan pago
            this.validarTodasLiquidacionesConPago(s, pm);

            //Se realizan las asociaciones entre solicitud y liquidación.
            liqPers = this.addLiquidacionToSolicitud(LiquidacionEnhanced.enhance(liquidacion), s, pm);

            //SE HACE PERSISTENTE LA LIQUIDACION.
            pm.makePersistent(liqPers);

            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Valida que todas las liquidaciones de la solicitud tengan un pago
     * asociado. En caso que exista una liquidación sin pago se lanza una
     * excepción.
     *
     * @param s
     * @param pm
     */
    private void validarTodasLiquidacionesConPago(SolicitudEnhanced s, PersistenceManager pm) throws DAOException {
        LiquidacionEnhanced liq;
        for (Iterator it = s.getLiquidaciones().iterator(); it.hasNext();) {
            liq = (LiquidacionEnhanced) it.next();
            try {
                PagoEnhanced pago = liq.getPago();
            } catch (JDOObjectNotFoundException e) {
                throw new DAOException("No todas las liquidaciones actuales tienen pago");
            }
        }
    }

    protected LiquidacionEnhanced addLiquidacionToSolicitud(
            LiquidacionEnhanced l, SolicitudEnhanced s,
            PersistenceManager pm) throws DAOException {
        LiquidacionEnhanced rta = null;
        JDOVariablesOperativasDAO variablesDAO = new JDOVariablesOperativasDAO();

        l.setIdLiquidacion(String.valueOf(s.getLastIdLiquidacion() + 1));
        s.setLastIdLiquidacion(s.getLastIdLiquidacion() + 1);
        l.setSolicitud(s);
        l.setIdSolicitud(s.getIdSolicitud());

        if (s != null && s.getCirculo() != null && s.getCirculo().getIdCirculo() != null) {
            l.setCirculo(s.getCirculo().getIdCirculo());
        }

        //ASOCIAR UN USUARIO A LA LIQUIDACION.
        if (l.getUsuario() == null) {
            throw new DAOException(
                    "La liquidacion no esta asociada a un Usuario");
        }
        UsuarioEnhanced usuario = l.getUsuario();
        UsuarioEnhanced usuarior = variablesDAO.getUsuarioByLogin(usuario.getUsername(), pm);
        if (usuarior == null) {
            throw new DAOException(
                    "No encontró el Usuario con el login: "
                    + l.getUsuario());
        }
        l.setUsuario(usuarior);

        try {

            rta = l;
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Cuenta los días NO hábiles configurados en el sistema desde la fecha
     * inicial excluyéndola hasta la fecha final incluyéndola. Número Días no
     * hábiles entre: (fehainicial, fechaFinal]
     *
     * @return long
     * @throws DAOException
     */
    public long countDiasNoHabiles(CirculoPk cirID, Date fechaInicial, Date fechaFinal) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        Long rta;
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        try {
            CirculoEnhanced cirVal = solDAO.getCirculoByID(new CirculoEnhancedPk(cirID), pm);

            if (cirVal == null) {
                throw new DAOException("El círculo especificado NO existe");
            }

            VersantQuery query = (VersantQuery) pm.newQuery(CirculoFestivoEnhanced.class);
            query.declareParameters("String idCir, Date fechaFrom, Date fechaTo");
            query.setFilter("this.idCirculo == idCir &&\n"
                    + "this.fechaFestivo> fechaFrom &&\n"
                    + "this.fechaFestivo<= fechaTo");
            query.setResult("count(*)");
            rta = (Long) query.execute(cirVal.getIdCirculo(), fechaInicial, fechaFinal);
        } catch (JDOException e) {
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        return rta.longValue();
    }
    
    public void deleteActosBySolicitud(String idSolicitud, PersistenceManager pm) throws DAOException{
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
    
    /**
     * Permite modificar los datos de una liquidación de registro de documentos.
     *
     * @author dlopez
     * @param nuevaLiquidacion Nueva liquidación que va a ser asociada a la
     * solicitud.
     * <p>
     * La nueva liquidación tiene asociada su respectiva solicitud.
     */
    public Liquidacion modificarLiquidacionRegistro(Liquidacion nuevaLiquidacion) throws DAOException {
        //Obtener y validar la solicitud asociada con la liquidación.
        SolicitudRegistro sol = (SolicitudRegistro) nuevaLiquidacion.getSolicitud();

        LiquidacionTurnoRegistro nuevaLiquidacionRegistro = null;

        if (sol == null) {
            throw new DAOException("La liquidación no tiene asociada una solicitud.");
        }
        
        PersistenceManager pm = AdministradorPM.getPM();

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            
            String idSolicitud = sol.getIdSolicitud();
            if(idSolicitud != null){
                deleteActosBySolicitud(idSolicitud, pm);
            }

            SolicitudEnhancedPk solId = new SolicitudEnhancedPk();
            solId.idSolicitud = sol.getIdSolicitud();

            SolicitudEnhanced sEnh = (SolicitudEnhanced) pm.getObjectById(solId, true);

            if (sEnh == null) {
                throw new DAOException("No se encontro la solicitud con el identificador dado. " + solId.getSolicitudID());
            }

            //Se eliminan los certificados asociados que esten en la nueva solicitud con el flag eliminar
            List listaAsociados = sol.getSolicitudesHijas();
            int tamLista = listaAsociados.size();
            for (int indice = 0; indice < tamLista; indice++) {
                SolicitudAsociada solAsociada = (SolicitudAsociada) listaAsociados.get(indice);
                SolicitudCertificado solCertTemp = (SolicitudCertificado) solAsociada.getSolicitudHija();

                if (solCertTemp.isEliminar()) {

                    SolicitudAsociadaEnhancedPk idsolAsociada = new SolicitudAsociadaEnhancedPk();
                    idsolAsociada.idSolicitud = solAsociada.getIdSolicitud();
                    idsolAsociada.idSolicitud1 = solAsociada.getIdSolicitud1();

                    SolicitudAsociadaEnhanced solAsocPers = (SolicitudAsociadaEnhanced) pm.getObjectById(idsolAsociada, true);
                    if (solAsocPers == null) {
                        throw new DAOException("No se encontro la solicitud asociada con el id : " + idsolAsociada.idSolicitud + "-" + idsolAsociada.idSolicitud1);
                    }
                    pm.deletePersistent(solAsocPers);

                }
            }

            //Se obtiene la liquidación que va a ser modificada.
            List listaLiquidaciones = sEnh.getLiquidaciones();

            int sizeLiquidaciones = listaLiquidaciones.size();

            if (listaLiquidaciones.size() > 0) {
                //Se obtiene la última liquidación y se elimina.
                LiquidacionTurnoRegistroEnhanced liqRegistro = (LiquidacionTurnoRegistroEnhanced) listaLiquidaciones.get(sizeLiquidaciones - 1);
                long lastIdLiquidacion = sEnh.getLastIdLiquidacion();
                sEnh.setLastIdLiquidacion(lastIdLiquidacion - 1);
                pm.deletePersistent(liqRegistro);

                pm.currentTransaction().commit();

            } else {
                throw new DAOException("La solicitud de registro no tiene liquidaciones asociadas. ");
            }

            //Se obtiene una nueva liquidación de registro.
            LiquidadorRegistro liqReg = new LiquidadorRegistro();

            if (sEnh != null && sEnh.getCirculo() != null && sEnh.getCirculo().getIdCirculo() != null) {
                nuevaLiquidacion.setCirculo(sEnh.getCirculo().getIdCirculo());
            }

            nuevaLiquidacionRegistro = (LiquidacionTurnoRegistro) liqReg.reliquidar(nuevaLiquidacion);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw e;
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return nuevaLiquidacionRegistro;
    }

    /**
     * Obtiene el LPAD de un número, es decir un número de n dígitos completado
     * con ceros a la izquierda
     *
     * @param num
     * @return
     * @throws DAOException
     */
    protected String getLPAD(String num, int digitos) throws DAOException {
        String rta = "";

        if (num.length() > digitos) {
            throw new DAOException("El número tiene más de 6 dígitos: " + num);
        }

        int numCerosToInsert = digitos - num.length();

        for (int i = 0; i < numCerosToInsert; i++) {
            rta = rta + "0";
        }

        return rta + num;
    }

    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio de redondeo a la centena mas cercana
     */
    public double roundValue(double valor) {
        double diferencia = valor % 100;

        //Redondeo hacia la decena anterior.
        if (diferencia < 50) {
            valor -= diferencia;
        } //Redondeo hacia la centena siguiente.
        else {
            valor -= diferencia;
            valor += 100;
        }

        return valor;
    }

    /**
     * @author Fernando Padilla
     * @change Caso Mantis 0003831: Acta - Requerimiento No 170 - Tarifas
     * Resolucion_81_2010-imm. Se crea esta funcion para el redondeo de valores
     * a la centena mas cercana.
     */
    /* private static double redondeoCentenaMasCercana (double valor)
        {
            double diferencia = valor%100;

            if (diferencia < 50){
                valor -= diferencia;
            }else{
                valor -= diferencia;
                valor +=100;
            }

            return valor;
        }*/
    /**
     * @param fechaInicial
     * @param fechaFinal
     * @return
     * @throws DAOException
     */
    public long getNumeroSolicitudesSinTurno(Circulo circulo, Date fechaInicial,
            Date fechaFinal) throws DAOException {

        long tamanioLista = 0;
        PersistenceManager pm = AdministradorPM.getPM();

        try {

            String idCirculo = circulo.getIdCirculo();
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            Query query = pm.newQuery(SolicitudRegistroEnhanced.class);
            VersantQuery pagingQuery = (VersantQuery) query;
            //pagingQuery.setRandomAccess(true);
            pagingQuery.declareVariables(" TurnoEnhanced turno; PagoEnhanced pago");
            pagingQuery.declareParameters("Date fechaInicial, Date fechaFinal, String idCirculo");
            pagingQuery.setFilter("this.fecha>= fechaInicial && \n" + "this.fecha<= fechaFinal &&  \n"
                    + "!(turno.solicitud==this && turno.idCirculo==idCirculo) && !(pago.idSolicitud==this.idSolicitud) "
                    + "&& this.circulo.idCirculo==idCirculo");
            pagingQuery.setCountStarOnSize(true);

            List resultados = (List) pagingQuery.execute(fechaInicial, fechaFinal, idCirculo);

            if (resultados == null || resultados.isEmpty()) {
                pagingQuery.closeAll();
                pm.currentTransaction().commit();
                return 0;
            }

            tamanioLista = resultados.size();

            pagingQuery.closeAll();
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return tamanioLista;
    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.LiquidacionesDAO#getSolicitudesSinTurno(java.util.Date, java.util.Date, int, int)
     */
    public Map getSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena,
            int indiceInicial, int numeroResultados) throws DAOException {

        List lista = new ArrayList();
        Map mapa = new HashMap();
        PersistenceManager pm = AdministradorPM.getPM();

        try {

            long tamanioResultados = getNumeroSolicitudesSinTurno(circulo, fechaInicial,
                    fechaFinal);
            String idCirculo = circulo.getIdCirculo();

            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            // Para evitar SQL Injection es necesario hacer escape de algunos
            // caracteres especiales. Se utiliza el lenguaje normal de 
            // expresiones regulares, en lugar del lenguaje de oracle, es decir,
            // se utiliza * en vez de %, y se utiliza ? en vez de _.
            if (cadena == null || cadena.equals("")) {
                cadena = "";
            } else {
                cadena.replaceAll("\\", "\\\\");
                cadena.replaceAll("'", "\\'");
                cadena.replaceAll("_", "\\_");
                cadena.replaceAll("%", "\\%");
                cadena.replaceAll("*", "%");
                cadena.replaceAll("?", "_");
            }

            Query query = pm.newQuery(SolicitudRegistroEnhanced.class);
            VersantQuery pagingQuery = (VersantQuery) query;
            pagingQuery.setRandomAccess(true);
            pagingQuery.declareVariables(" TurnoEnhanced turno; PagoEnhanced pago");
            pagingQuery.declareParameters("Date fechaInicial, Date fechaFinal, String idCirculo");
            pagingQuery.setFilter("this.fecha >= fechaInicial && \n" + "this.fecha <= fechaFinal &&  \n"
                    + "!(turno.solicitud==this && turno.idCirculo==idCirculo) && !(pago.idSolicitud==this.idSolicitud) && "
                    + " this.circulo.idCirculo==idCirculo "
                    + (cadena.equals("")
                    ? " && this.idSolicitud.sql(\"ID_SOLICITUD like '%" + cadena + "%'\")"
                    : ""));
            //"this.documento.idDocumento.sql(\"like '%" + cadena + "%\"))");
            pagingQuery.setOrdering("fecha ascending");
            pagingQuery.setCountStarOnSize(true);

            List resultados = (List) pagingQuery.execute(fechaInicial, fechaFinal, idCirculo);

            //long tamanioResultados = resultados.size();
            for (int i = indiceInicial; i < indiceInicial + numeroResultados && i < tamanioResultados; i++) {
                SolicitudRegistroEnhanced solicitudRegistro = (SolicitudRegistroEnhanced) resultados.get(i);
                pm.makeTransient(solicitudRegistro.getCiudadano());
                pm.makeTransient(solicitudRegistro.getDocumento().getTipoDocumento());
                pm.makeTransient(solicitudRegistro.getDocumento());
                pm.makeTransient(solicitudRegistro);
                lista.add(solicitudRegistro);
            }

            mapa.put("numeroResultados", new Long(tamanioResultados));

            pagingQuery.closeAll();
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        lista = TransferUtils.makeTransferAll(lista);
        mapa.put("resultados", lista);
        return mapa;
    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.LiquidacionesDAO#getSolicitudesSinTurno(java.util.Date, java.util.Date, int, int)
     */
    public boolean removeSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena,
            int indiceInicial, int numeroResultados) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();

        try {

            long tamanioResultados = getNumeroSolicitudesSinTurno(circulo, fechaInicial,
                    fechaFinal);
            String idCirculo = circulo.getIdCirculo();

            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            // Para evitar SQL Injection es necesario hacer escape de algunos
            // caracteres especiales. Se utiliza el lenguaje normal de 
            // expresiones regulares, en lugar del lenguaje de oracle, es decir,
            // se utiliza * en vez de %, y se utiliza ? en vez de _.
            if (cadena == null || cadena.equals("")) {
                cadena = "";
            } else {
                cadena.replaceAll("\\", "\\\\");
                cadena.replaceAll("'", "\\'");
                cadena.replaceAll("_", "\\_");
                cadena.replaceAll("%", "\\%");
                cadena.replaceAll("*", "%");
                cadena.replaceAll("?", "_");
            }

            Query query = pm.newQuery(SolicitudRegistroEnhanced.class);
            VersantQuery pagingQuery = (VersantQuery) query;
            pagingQuery.setRandomAccess(true);
            pagingQuery.declareVariables(" TurnoEnhanced turno; PagoEnhanced pago");
            pagingQuery.declareParameters("Date fechaInicial, Date fechaFinal");
            pagingQuery.setFilter("this.fecha >= fechaInicial && \n" + "this.fecha <= fechaFinal &&  \n"
                    + "!(turno.solicitud==this && turno.idCirculo==this.circulo) && !(pago.idSolicitud==this.idSolicitud) && "
                    + "this.circulo.idCirculo==\"" + idCirculo + "\""
                    + (cadena.equals("")
                    ? " && this.idSolicitud.sql(\"ID_SOLICITUD like '%" + cadena + "%'\")"
                    : ""));
            //"this.documento.idDocumento.sql(\"like '%" + cadena + "%\"))");
            pagingQuery.setOrdering("fecha ascending");
            pagingQuery.setCountStarOnSize(true);

            List resultados = (List) pagingQuery.execute(fechaInicial, fechaFinal);

            //long tamanioResultados = resultados.size();
            SolicitudEnhanced solAsociada;

            for (Iterator iter = resultados.iterator(); iter.hasNext();) {
                SolicitudRegistroEnhanced solicitudRegistro = (SolicitudRegistroEnhanced) iter.next();

                //Borrar solicitudes hijas si existen.
                if (!solicitudRegistro.getSolicitudesHijas().isEmpty()) {
                    for (Iterator it = solicitudRegistro.getSolicitudesHijas().iterator(); it.hasNext();) {
                        solAsociada = ((SolicitudAsociadaEnhanced) it.next()).getSolicitudHija();
                        pm.deletePersistent(solAsociada);
                    }
                }

                //Borrar ciudadano si tiene secuencia.
                CiudadanoEnhanced ciu = solicitudRegistro.getCiudadano();
                if (ciu.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                    pm.deletePersistent(ciu);
                }

                pm.deletePersistent(solicitudRegistro);
            }

            pagingQuery.closeAll();
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Este servicio permite modificar el valor de la última liquidación
     * asociada a un turno (Utilizado en el proceso de devoluciones).
     *
     * @param turno El identificador del turno sobre el cual se va a modificar
     * la última liquidación.
     * @param liquidacion La <code>Liquidacion</code> con los valores que van a
     * ser modificados.
     * @return <code>true </code> o <code>false </code> dependiendo del
     * resultado de la operación.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.Solicitud
     * @see gov.sir.core.negocio.modelo.Liquidacion
     */
    public boolean updateUltimaLiquidacion(TurnoPk turnoId, Liquidacion liquidacion) throws DAOException {
        JDOTurnosDAO daoTurno = new JDOTurnosDAO();
        PersistenceManager pm = AdministradorPM.getPM();
        LiquidacionEnhanced liqPers = null;
        JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();

        //SE OBTIENE LA ULTIMA LIQUIDACION DEL TURNO
        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            Turno turnoModificar = daoTurno.getTurnoByID(turnoId);
            Solicitud solTurno = turnoModificar.getSolicitud();
            List listaLiquidaciones = solTurno.getLiquidaciones();
            Liquidacion liqModificar = null;
            LiquidacionTurnoRegistroEnhanced liqRegistroEnh = null;
            LiquidacionTurnoCertificadoEnhanced liqCertificadoEnh = null;

            for (int i = 0; i < listaLiquidaciones.size(); i++) {
                liqModificar = (Liquidacion) listaLiquidaciones.get(i);
            }

            if (liqModificar == null) {
                throw new DAOException("El turno no tiene asociada ninguna liquidación");
            }

            LiquidacionEnhancedPk idLiquidacion = new LiquidacionEnhancedPk();
            idLiquidacion.idLiquidacion = liqModificar.getIdLiquidacion();
            idLiquidacion.idSolicitud = liqModificar.getIdSolicitud();

            //Caso liquidación de Registro.
            if (liqModificar instanceof LiquidacionTurnoRegistro) {
                liqRegistroEnh = (LiquidacionTurnoRegistroEnhanced) pm.getObjectById(idLiquidacion, true);
                LiquidacionTurnoRegistro liqRegistro = (LiquidacionTurnoRegistro) liquidacion;
                if (liqRegistroEnh == null) {
                    throw new DAOException("No pudo recuperarse la liquidación asociada al turno de registro");
                }
                liqRegistroEnh.setValorDerechos(liqRegistro.getValorDerechos());
                liqRegistroEnh.setValorImpuestos(liqRegistro.getValorImpuestos());
                liqRegistroEnh.setValor(liqRegistro.getValorDerechos() + liqRegistro.getValorImpuestos());
            }

            //Caso liquidación de Certificados.
            if (liqModificar instanceof LiquidacionTurnoCertificado) {
                liqCertificadoEnh = (LiquidacionTurnoCertificadoEnhanced) pm.getObjectById(idLiquidacion, true);
                LiquidacionTurnoCertificado liqCertificado = (LiquidacionTurnoCertificado) liquidacion;
                if (liqCertificadoEnh == null) {
                    throw new DAOException("No pudo recuperarse la liquidación asociada al turno de certificados");
                }
                liqCertificadoEnh.setValor(liqCertificado.getValor());
            }

            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Actualiza el valor de la liquidacion del turno de devoluciones
     *
     * @param liquidacion
     * @return
     * @throws DAOException
     */
    public boolean updateValorLiquidacionDevolucion(Liquidacion liquidacion) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        LiquidacionTurnoDevolucionEnhanced liqEnh = null;
        LiquidacionTurnoDevolucion devolucion = (LiquidacionTurnoDevolucion) liquidacion;

        Double valor = new Double(devolucion.getValor());
        Double valorImpuestos = new Double(devolucion.getValorImpuestos());
        Double valorDerechos = new Double(devolucion.getValorDerechos());
        Double valorImpuestosMayorValor = new Double(devolucion.getValorImpuestosMayorValor());
        Double valorDerechosMayorValor = new Double(devolucion.getValorDerechosMayorValor());
        Double valorMayorValor = new Double(devolucion.getValorMayorValor());
        Double valorSaldoFavor = new Double(devolucion.getValorSaldoFavor());
        Double valorConservacionDoc = new Double(devolucion.getValorConservacionDoc());

        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            LiquidacionEnhancedPk idLiquidacion = new LiquidacionEnhancedPk();
            idLiquidacion.idLiquidacion = liquidacion.getIdLiquidacion();
            idLiquidacion.idSolicitud = liquidacion.getIdSolicitud();

            liqEnh = (LiquidacionTurnoDevolucionEnhanced) pm.getObjectById(idLiquidacion, true);
            if (liqEnh == null) {
                throw new DAOException("No pudo recuperarse la liquidación del turno de devolución");
            }
            liqEnh.setValor(valor.doubleValue());
            liqEnh.setValorDerechos(valorDerechos.doubleValue());
            liqEnh.setValorImpuestos(valorImpuestos.doubleValue());
            liqEnh.setValorDerechosMayorValor(valorDerechosMayorValor.doubleValue());
            liqEnh.setValorImpuestosMayorValor(valorImpuestosMayorValor.doubleValue());
            liqEnh.setValorMayorValor(valorMayorValor.doubleValue());
            liqEnh.setValorSaldoFavor(valorSaldoFavor.doubleValue());
            liqEnh.setValorConservacionDoc(valorConservacionDoc.doubleValue());

            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            Log.getInstance().error(JDOLiquidacionesDAO.class, e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }
    
    /**
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     */
    public boolean isIncentivoRegistral(Date fechaDocumento) throws DAOException {
        JDOParametrosDAO parametrosDao = new JDOParametrosDAO();
        Date fechaIncentivoRegistral = parametrosDao.getFechaIncentivoRegistral();
        if (fechaIncentivoRegistral == null) {
            throw new DAOException("No se pudo obtener la fecha de incentivo registral");
        }

        if (fechaDocumento.compareTo(fechaIncentivoRegistral) < 0) {
            return true;
        }

        return false;
    }

    /**
     * @author : Ricardo Chivata
     * @change : Tarifas - varible porcentual del recibo.
     */
    public void InsertarConservacion(ActoEnhanced acto, String mayorvalor, String turnoAnterior, int consec) throws DAOException {
        double porcentajetarifa = 0.02;
        PersistenceManager pm = AdministradorPM.getPM();
        List lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta
                = " insert into"
                + " SIR_OP_LIQUIDA_CONSERVA_DOCU (ID,ID_TURNO,VALOR_PORCENTAJE,FECHA,TARIFA_PLENA,TARIFA_BASE,VALOR_CONSERVACION,TIPO_ACTO,ID_SOLICITUD,MAYORVALOR,TIPO_ACTO_CONSEC) "
                + " VALUES (SEC_SIR_OP_LIQUIDA_CONSERVA.NEXTVAL,?,?,?,?,?,?,?,?,?,?) ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, "0");
            ps.setString(2, String.valueOf((int) (porcentajetarifa * 100)));
            ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));

            if (turnoAnterior.equals("0")) {
                int temp = (int) roundValue(acto.getValor() * (1 + porcentajetarifa));
                ps.setString(4, String.valueOf(temp));
                ps.setString(5, String.valueOf(acto.getValor()));
                temp = (int) roundValue(acto.getValor() * porcentajetarifa);
                ps.setString(6, String.valueOf(temp));
            } else {
                Map infoLiquidacionAnterior = liquidacionAnterior(acto, turnoAnterior, consec);
                //System.out.println("TARIFA PLENA: " + infoLiquidacionAnterior.get("tarifa_plena") + " TARIFA BASE: " + infoLiquidacionAnterior.get("tarifa_base") + " CONSERVACION " + infoLiquidacionAnterior.get("valor_conservacion"));

                int temp = (int) roundValue(acto.getValor() * (1 + porcentajetarifa));
                Double tarifa_plena = (Double) infoLiquidacionAnterior.get("tarifa_plena");
                System.out.println("tarifa plena con la resta " + (temp - tarifa_plena));
                ps.setString(4, String.valueOf(temp - tarifa_plena));

                Double tarifa_base = (Double) infoLiquidacionAnterior.get("tarifa_base");
                System.out.println("tarifa base con la resta " + (acto.getValor() - tarifa_base));
                ps.setString(5, String.valueOf(acto.getValor() - tarifa_base));

                Double valor_conservacion = (Double) infoLiquidacionAnterior.get("valor_conservacion");
                temp = (int) roundValue(acto.getValor() * porcentajetarifa);
                System.out.println("valor conservacion con la resta " + (temp - valor_conservacion));
                ps.setString(6, String.valueOf(temp - valor_conservacion));
            }
            //
            ps.setString(7, acto.getTipoActo().getIdTipoActo());
            ps.setString(8, acto.getIdSolicitud());
            if (Integer.parseInt(mayorvalor) > 0 && mayorvalor != null && !mayorvalor.isEmpty()) {
                ps.setString(9, mayorvalor);
            } else {
                ps.setString(9, "0");
            }
            ps.setInt(10, consec);
            rs = ps.executeQuery();
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

    public Map liquidacionAnterior(ActoEnhanced acto, String turnoAnterior, int tipoActoConsec)
            throws DAOException {
        //LiquidacionConservacionEnhanced rta = null;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        Map infoLiquidacionAnterior = new HashMap();

        String consulta = "SELECT TARIFA_PLENA, TARIFA_BASE, VALOR_CONSERVACION "
                + "FROM SIR_OP_LIQUIDA_CONSERVA_DOCU "
                + "WHERE ID_TURNO = ? AND TIPO_ACTO = ? AND TIPO_ACTO_CONSEC = ?";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);

            //System.out.println("acto " + acto.getTipoActo().getIdTipoActo()+ " turnoAnterior "+turnoAnterior+" consecutivo "+tipoActoConsec);
            ps.setString(1, turnoAnterior);
            ps.setString(2, String.valueOf(acto.getTipoActo().getIdTipoActo()));
            ps.setInt(3, tipoActoConsec);

            rs = ps.executeQuery();

            Double tarifa_plena = 0.0;
            Double tarifa_base = 0.0;
            Double valor_conservacion = 0.0;

            while (rs.next()) {
//                infoLiquidacionAnterior.put("tarifa_plena", rs.getDouble("TARIFA_PLENA"));
//                infoLiquidacionAnterior.put("tarifa_base", rs.getDouble("TARIFA_BASE"));
//                infoLiquidacionAnterior.put("valor_conservacion", rs.getDouble("VALOR_CONSERVACION"));
                tarifa_plena = rs.getDouble("TARIFA_PLENA");
                tarifa_base = rs.getDouble("TARIFA_BASE");
                valor_conservacion = rs.getDouble("VALOR_CONSERVACION");

            }

            infoLiquidacionAnterior.put("tarifa_plena", tarifa_plena);
            infoLiquidacionAnterior.put("tarifa_base", tarifa_base);
            infoLiquidacionAnterior.put("valor_conservacion", valor_conservacion);

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

        return infoLiquidacionAnterior;
    }

    /**
     * @author : Ricardo Chivata
     * @change : Tarifas - varible porcentual del recibo.
     */
    public void AsociarTurnoSolicitudConservacion(String idSolicitud) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        Map resultado = new HashMap();
        ArrayList<Map> lista;
        lista = new ArrayList<Map>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta
                = " select  TRNO_ID_WORKFLOW "
                + " from SIR_OP_TURNO a "
                + " where a.ID_SOLICITUD = ? ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, idSolicitud);
            rs = ps.executeQuery();
            while (rs.next()) {
                resultado.put("TRNO_ID_WORKFLOW", rs.getString("TRNO_ID_WORKFLOW"));
                lista.add(resultado);
            }
            jdoPM.currentTransaction().commit();
            if (lista.size() > 0) {
                Log.getInstance().info("Turno para conservacion:", lista.get(0).get("TRNO_ID_WORKFLOW"));
            }
        } catch (SQLException e) {
            Log.getInstance().error("Turno para conservacion:", "Error en consulta de turno ára conservacion");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error("Turno para conservacion:", "Error en consulta de turno ára conservacion");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error("Turno para conservacion:", "Error en consulta de turno ára conservacion");
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
        pm = AdministradorPM.getPM();
        connection = null;
        ps = null;
        rs = null;
        jdoPM = null;
        consulta
                = " UPDATE SIR_OP_LIQUIDA_CONSERVA_DOCU SET ID_TURNO = ? "
                + " where SIR_OP_LIQUIDA_CONSERVA_DOCU.ID_SOLICITUD = ? ";
        Log.getInstance().error("consulta a realizar en tabla de conservacion", consulta);
        try {
            if (lista.get(0).get("TRNO_ID_WORKFLOW") != null && idSolicitud != null && lista.size() > 0) {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                ps = connection.prepareStatement(consulta);
                ps.setString(1, (String) lista.get(0).get("TRNO_ID_WORKFLOW"));
                ps.setString(2, idSolicitud);
                ps.executeQuery();
            }
            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            Log.getInstance().error("Turno para conservacion:", "Error asociando turno a conservacion");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error("Turno para conservacion:", "Error asociando turno a conservacion");
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOTurnosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error("Turno para conservacion:", "Error asociando turno a conservacion");
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

    /**
     * yeferson
     *
     * metodo para guarda liquidacion de devolucion por concepto de conservacion
     * documental
     *
     * @param turnoDevolucion
     * @param turnoAnterior
     * @param idSolicitud
     * @param valorBase
     * @param valorconserva
     * @throws gov.sir.hermod.dao.DAOException
     */
    public void insertDevolucionConservaDoc(String turnoDevolucion, String turnoAnterior, String idSolicitud, double valorBase, double valorconserva) throws DAOException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta
                = " insert into"
                + " SIR_OP_DEVOLUCION_CONSERVADOC (ID,ID_TURNO,FECHA,TARIFA_BASE,VALOR_DEV_CONSERVACION,ID_SOLICITUD,MAYORVALOR,ID_TURNO_ANT) "
                + " VALUES (SEC_SIR_OP_DEV_CONSERVADOC.NEXTVAL,?,?,?,?,?,?,?) ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, turnoDevolucion);
            ps.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            ps.setDouble(3, valorBase);
            ps.setDouble(4, valorconserva);
            ps.setString(5, idSolicitud);
            ps.setInt(6, 0);
            ps.setString(7, turnoAnterior);
            rs = ps.executeQuery();
            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
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
                Log.getInstance().error(JDOLiquidacionesDAO.class, e);
                Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    public double selectDevolucionConervaDoc(String turno, int mayorvalor) throws DAOException {

        double rt = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT VALOR_DEV_CONSERVACION \n"
                + "FROM SIR_OP_DEVOLUCION_CONSERVADOC\n"
                + "WHERE ID_TURNO = ? AND MAYORVALOR = ?";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, turno);
            ps.setInt(2, mayorvalor);
            rs = ps.executeQuery();

            while (rs.next()) {
                rt = rs.getDouble("VALOR_DEV_CONSERVACION");
            }

            jdoPM.currentTransaction().commit();

        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);

        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
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
                Log.getInstance().error(JDOLiquidacionesDAO.class, e);
                Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rt;

    }

    public double sinConserva(String turno, int mayor) throws DAOException {

        double vlr = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "SELECT TARIFA_BASE \n"
                + "FROM SIR_OP_DEVOLUCION_CONSERVADOC\n"
                + "WHERE ID_TURNO = ? AND MAYORVALOR = ? ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);

            ps.setString(1, turno);
            ps.setInt(2, mayor);
            rs = ps.executeQuery();

            while (rs.next()) {
                vlr = rs.getDouble("TARIFA_BASE");
            }

            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);

        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
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
                Log.getInstance().error(JDOLiquidacionesDAO.class, e);
                Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return vlr;

    }

    /**
     * @author : Ricardo Chivata
     * @change : Tarifas - varible porcentual del recibo.
     */
    public double TraerConservacionActo(Acto acto) throws DAOException {
        Acto temporal = (Acto) acto;
        PersistenceManager pm = AdministradorPM.getPM();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta
                = " select  VALOR_PORCENTAJE "
                + " from SIR_OP_CONSERVACION_DOCUMENTAL a "
                + " where a.ESTADO = ? and a.ID_TIPO_ACTO = ?";
//                        + " where a.ESTADO = ? and a.ID_TIPO_ACTO = ? and a.VERSION = ( SELECT MAX( VERSION )  FROM SIR_OP_CONSERVACION_DOCUMENTAL)";
        double valorConservacion = 0;
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setString(1, "1");
            ps.setString(2, acto.getTipoActo().getIdTipoActo());
            rs = ps.executeQuery();
            while (rs.next()) {
                valorConservacion = Double.parseDouble(rs.getString("VALOR_PORCENTAJE"));
            }
            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
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
                Log.getInstance().error(LiquidadorRegistro.class, e);
                Log.getInstance().error(LiquidadorRegistro.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        valorConservacion = valorConservacion / 100;
        return valorConservacion;
    }

    public void UpdateDevolucionConservaDoc(String turnoDevolucion, double valorBase, double valorconserva) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String consulta = "UPDATE SIR_OP_DEVOLUCION_CONSERVADOC \n"
                + "SET TARIFA_BASE = ? , VALOR_DEV_CONSERVACION = ? \n"
                + "WHERE ID_TURNO = ? ";
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);
            ps = connection.prepareStatement(consulta);
            ps.setDouble(1, valorBase);
            ps.setDouble(2, valorconserva);
            ps.setString(3, turnoDevolucion);
            ps.executeUpdate();
            jdoPM.currentTransaction().commit();
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
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
                Log.getInstance().error(JDOLiquidacionesDAO.class, e);
                Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
        public void deleteLiquidacionTurnoRegistro(String idLiquidacion, String idSolicitud) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
//        LiquidacionTurnoRegistroEnhanced eliminado = (LiquidacionTurnoRegistroEnhanced) LiquidacionTurnoRegistroEnhanced.enhance(liquidacion);
//        LiquidacionTurnoDevolucion devolucion = (LiquidacionTurnoDevolucion) liquidacion;
//        LiquidacionEnhancedPk lId = new LiquidacionEnhancedPk();
//            lId.idLiquidacion = liqr.getIdLiquidacion();
//            lId.idSolicitud = liqr.getIdSolicitud();
//            liqr = (LiquidacionTurnoRegistroEnhanced) this.getLiquidacionByID(lId,
//                    pm);
        
        try {

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            Query query = pm.newQuery(LiquidacionTurnoRegistroEnhanced.class);
            query.declareParameters("String idSol, String idLiq");
            query.setFilter("this.idSolicitud == idSol && this.idLiquidacion == idLiq ");
            Collection col = (Collection) query.execute(idSolicitud, idLiquidacion);

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                LiquidacionTurnoDevolucionEnhanced solVinculada = (LiquidacionTurnoDevolucionEnhanced) iter.next();
                pm.deletePersistent(solVinculada);
            }

            //	Traer objeto Persistente
            LiquidacionEnhancedPk idSol = new LiquidacionEnhancedPk();
            idSol.idSolicitud = idSolicitud;

            LiquidacionTurnoDevolucionEnhanced eliminado = null;

            try {
                eliminado = (LiquidacionTurnoDevolucionEnhanced) pm.getObjectById(idSol, true);
            } catch (JDOObjectNotFoundException e) {
                eliminado = null;
            }

            if (eliminado == null) {
                throw new DAOException(
                        "No existe la liquidacion con el id " + idSol.getLiquidacionID());
            }
            pm.deletePersistent(eliminado);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLiquidacionesDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
//
//        return true;
    }
}
