package gov.sir.fenrir.dao.impl.jdogenie;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.InfoUsuario;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CJerarquia;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AuditoriaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.dao.DAOException;
import gov.sir.fenrir.dao.ErrorConexionException;
import gov.sir.fenrir.dao.FenrirDAO;
import gov.sir.hermod.HermodProperties;

import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.EstacionDAO;
import org.auriga.core.modelo.daoObjects.NivelDAO;
import org.auriga.core.modelo.daoObjects.RolDAO;
import org.auriga.core.modelo.daoObjects.UsuarioDAO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Jerarquia;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.RelEstacionNivel;
import org.auriga.core.modelo.transferObjects.RelRolEstacion;
import org.auriga.core.modelo.transferObjects.RelUsuRol;
import org.auriga.core.modelo.transferObjects.RelUsuRolEst;
import org.auriga.core.modelo.transferObjects.Rol;

import org.auriga.util.ExceptionPrinter;

import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;
import gov.sir.core.negocio.modelo.ArchivosJustifica;
import gov.sir.core.negocio.modelo.CamposCaptura;
import gov.sir.core.negocio.modelo.FormaPagoCampos;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced;
import gov.sir.core.negocio.modelo.util.CamposCapturaEstados;

import gov.sir.fenrir.dao.UsuarioDuplicadoException;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.*;

import javax.jdo.*;

/**
 * Implementaci�n concreta de FenrirDAO para JDO Genie
 *
 * @author jfrias
 * @author jmendez
 * @author dsalas
 */
public class OracleFenrirJDO implements FenrirDAO {

    /**
     *
     */
    public OracleFenrirJDO() {
        super();
        FenrirProperties p = FenrirProperties.getInstancia();

    }

    /**
     * Se hace la auditoria del login el usuario
     *
     * @param user el usuario al que se le va a agregar la auditoria
     * @return un numero entero que identifica al usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     * @throws UsuarioDuplicadoException cuando ya hay un usuario registrado con
     * el mismo nombre de usuario
     */
    public int agregarAuditoria(long id, InfoUsuario info) throws ErrorConexionException {
        AuditoriaEnhanced audit = new AuditoriaEnhanced();
        audit.setDireccion(info.getAddress());
        audit.setHost(info.getHost());
        audit.setUsuarioWeb(info.getUser());

        UsuarioEnhancedPk oid = new UsuarioEnhancedPk();
        oid.idUsuario = id;

        int idAuditoria = -1;
        PersistenceManager pm = null;

        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");

            Query query = pm.newQuery(AuditoriaEnhanced.class);
            query.setFilter("fechaLogout==null && usuario==" + id);

            Collection col = (Collection) query.execute();

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                AuditoriaEnhanced auditoria = (AuditoriaEnhanced) iter.next();
                Date fecha = new Date(System.currentTimeMillis());
                auditoria.setFechaLogout(fecha);
                pm.makePersistent(auditoria);
            }

            query.closeAll();
            Log.getInstance().info(this.getClass(), "Consultas cerradas");

            UsuarioEnhanced user = (UsuarioEnhanced) pm.getObjectById(oid, true);
            audit.setUsuario(user);

            long milis = System.currentTimeMillis();
            Date fecha = new Date(milis);
            audit.setFechaLogin(fecha);

            synchronized (OracleFenrirJDO.class) {
                pm.makePersistent(audit);
                pm.currentTransaction().commit();
            }

            Log.getInstance().info(this.getClass(), "Commit ejecutado");

            AuditoriaEnhancedPk oidAuditoria = (AuditoriaEnhancedPk) pm.getObjectId(audit);
            idAuditoria = oidAuditoria.idAuditoria;
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            idAuditoria = -1;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error generando la auditor�a " + printer.toString());
            throw new ErrorConexionException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new ErrorConexionException(e.getMessage(), e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new ErrorConexionException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        return idAuditoria;
    }

    /**
     * Se obtienen las estaciones a las que pertenece el usuario
     *
     * @param user el usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darRolUsuario(long id) throws ErrorConexionException {
        UsuarioEnhancedPk oid = new UsuarioEnhancedPk();
        oid.idUsuario = id;

        PersistenceManager pm = null;
        List roles = new Vector();

        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");

            //SE VA A OBTENER EL USUARIO DE AURIGA A PARTIR DEL USUARIO SIR
            UsuarioEnhanced usir = (UsuarioEnhanced) pm.getObjectById(oid, true);
            pm.makeTransient(usir);
            DAOFactory fact = DAOFactory.getFactory();
            UsuarioDAO udao = fact.getUsuarioDAO();
            org.auriga.core.modelo.transferObjects.Usuario user
                    = udao.consultarUsuario(usir.getUsername());
            //pm.makeTransient(user);

            RolDAO rolUsu = fact.getRolDAO();
            roles = rolUsu.obtenerRolesUsuario(user);

            pm.currentTransaction().commit();
            Log.getInstance().info(this.getClass(), "Commit ejecutado");
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
            roles = null;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando roles:" + printer.toString());
            throw new ErrorConexionException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new ErrorConexionException(e.getMessage(), e);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error en el DAO:" + printer.toString());
            throw new ErrorConexionException("Error consultando roles", e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new ErrorConexionException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        return roles;
    }

    /**
     * Se finaliza todo el DAO
     *
     */
    public void finalizar() {
        AdministradorPM.shutdown();
    }

    /**
     * Se registra el logout del usuario
     *
     * @param idLogin el identificador del usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public void hacerLogout(Integer idLogin) throws ErrorConexionException {
        AuditoriaEnhancedPk oid = new AuditoriaEnhancedPk();
        oid.idAuditoria = idLogin.intValue();

        PersistenceManager pm = null;

        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");

            AuditoriaEnhanced au = (AuditoriaEnhanced) pm.getObjectById(oid, true);
            Date logout = new Date(System.currentTimeMillis());
            au.setFechaLogout(logout);
            pm.currentTransaction().commit();
            Log.getInstance().info(this.getClass(), "Commit ejecutado");
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
            throw new ErrorConexionException("No se pudo hacer logout", e);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error haciendo logout:" + printer.toString());
            throw new ErrorConexionException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new ErrorConexionException(e.getMessage(), e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new ErrorConexionException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }
    }

    /**
     * Se obtiene el id del usuario ingresado
     *
     * @param username el Username del usuario
     * @return el id del usuario
     */
    public long getIdUsuario(String user) throws ErrorConexionException {
        long id = -1;
        PersistenceManager pm = null;

        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");

            Query query = pm.newQuery(UsuarioEnhanced.class);
            query.declareParameters("String user");
            query.setFilter("username==user");

            Collection col = (Collection) query.execute(user);

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                UsuarioEnhanced usuario = (UsuarioEnhanced) iter.next();
                id = usuario.getIdUsuario();
            }

            query.closeAll();
            Log.getInstance().info(this.getClass(), "Consultas cerradas");
            pm.currentTransaction().commit();
            Log.getInstance().info(this.getClass(), "Commit ejecutado");
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
            id = -1;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando estaciones:" + printer.toString());
            throw new ErrorConexionException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new ErrorConexionException(e.getMessage(), e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new ErrorConexionException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        return id;
    }

    /**
     * Se obtienen las estaciones a las que pertenece un usuario
     *
     * @param id el usuario del que se quieren sus roles
     * @return una lista con los roles a los que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darEstacionUsuario(long id, Rol rol) throws ErrorConexionException {
        UsuarioEnhancedPk oid = new UsuarioEnhancedPk();
        oid.idUsuario = id;

        PersistenceManager pm = null;
        List esta = new Vector();
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");
            //			SE VA A OBTENER EL USUARIO DE AURIGA A PARTIR DEL USUARIO SIR
            UsuarioEnhanced usir = (UsuarioEnhanced) pm.getObjectById(oid, true);
            pm.makeTransient(usir);
            DAOFactory fact = DAOFactory.getFactory();
            UsuarioDAO udao = fact.getUsuarioDAO();
            org.auriga.core.modelo.transferObjects.Usuario user
                    = udao.consultarUsuario(usir.getUsername());
            //pm.makeTransient(user);

            EstacionDAO estUsu = fact.getEstacionDAO();
            //	estUsu.obtenerEstaciones()
            esta = estUsu.obtenerEstacionesUsuario(user, rol);

            pm.currentTransaction().commit();
            Log.getInstance().info(this.getClass(), "Commit ejecutado");
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
            esta = null;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando estaciones:" + printer.toString());
            throw new ErrorConexionException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new ErrorConexionException(e.getMessage(), e);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando estaciones:" + printer.toString());
            /**
             * @Autor: Edgar Lora
             * @Mantis: 003_589
             */
            //throw new ErrorConexionException("Error consultando estaciones", e);
            throw new ErrorConexionException("Sesion cerrada por logueo con otro usuario", e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new ErrorConexionException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        return esta;
    }

    /**
     * Se obtienen las estaciones a las que pertenece un usuario
     *
     * @param id el usuario del que se quieren sus roles
     * @return una lista con los roles a los que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darEstacionesUsuario(long id) throws ErrorConexionException {
        UsuarioEnhancedPk oid = new UsuarioEnhancedPk();
        oid.idUsuario = id;

        PersistenceManager pm = null;
        List esta = new Vector();
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");
            //			SE VA A OBTENER EL USUARIO DE AURIGA A PARTIR DEL USUARIO SIR
            UsuarioEnhanced usir = (UsuarioEnhanced) pm.getObjectById(oid, true);
            pm.makeTransient(usir);
            DAOFactory fact = DAOFactory.getFactory();
            UsuarioDAO udao = fact.getUsuarioDAO();
            org.auriga.core.modelo.transferObjects.Usuario user
                    = udao.consultarUsuario(usir.getUsername());
            //pm.makeTransient(user);

            EstacionDAO estUsu = fact.getEstacionDAO();
            //	estUsu.obtenerEstaciones()
            esta = estUsu.obtenerEstacionesUsuario(user);

            pm.currentTransaction().commit();
            Log.getInstance().info(this.getClass(), "Commit ejecutado");
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
            esta = null;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando estaciones:" + printer.toString());
            throw new ErrorConexionException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new ErrorConexionException(e.getMessage(), e);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando estaciones:" + printer.toString());
            //throw new ErrorConexionException("Error consultando estaciones", e);
            throw new ErrorConexionException("Sesion cerrada por logueo con otro usuario", e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new ErrorConexionException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        return esta;
    }

    /**
     * M�todo para consultar el c�rculo asociado a una <code>Estaci�n</code>
     * determinada
     *
     * @param estaci�n
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public Circulo darCirculoEstacion(Estacion estacion) throws ErrorConexionException {
        CirculoEnhanced ef = null;
        PersistenceManager pm = null;
        try {
            pm = AdministradorPM.getPM();
            DAOFactory fact = DAOFactory.getFactory();
            NivelDAO nivelDao = fact.getNivelDAO();
            List niveles = nivelDao.obtenerNivelesEstacion(estacion);
            Nivel nivel = (Nivel) niveles.get(0);
            String idCirculo = nivel.getAtributo1();

            CirculoEnhancedPk oid = new CirculoEnhancedPk();
            oid.idCirculo = idCirculo;
            ef = this.getCirculo(oid, pm);
            pm.makeTransient(ef);
        } catch (DAOException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando los circulos de la estacion:" + printer.toString());
            throw new ErrorConexionException(
                    "Error consultando los circulos de la estacion: " + estacion.getEstacionId(),
                    e);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando los circulos de la estacion:" + printer.toString());
            throw new ErrorConexionException(
                    "Error consultando los circulos de la estacion: " + estacion.getEstacionId(),
                    e);
        } finally {
            pm.close();
        }
        if (ef != null) {
            Circulo cir = (Circulo) ef.toTransferObject();
            return cir;
        } else {
            throw new ErrorConexionException(
                    "Error consultando los circulos de la estacion: "
                    + estacion.getEstacionId()
                    + ": Circulo nulo");
        }

    }

    /**
     * Consulta un c�rculo por su identificador
     *
     * @param oid identificador del c�rculo
     * @param pm
     * @return
     */
    protected CirculoEnhanced getCirculo(CirculoEnhancedPk oid, PersistenceManager pm) {
        CirculoEnhanced rta = null;

        if (oid.idCirculo != null) {
            try {
                rta = (CirculoEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            }
        }
        return rta;
    }

    /**
     * Consulta los usuarios Asociados con un Rol
     *
     * @param rol
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darUsuariosRol(Rol rol) throws ErrorConexionException {
        List usuarios = new ArrayList();
        List usuariosTrans = new ArrayList();
        PersistenceManager pm = null;
        Collection col = null;
        try {
            pm = AdministradorPM.getPM();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");
            DAOFactory fact = DAOFactory.getFactory();
            UsuarioDAO uDao = fact.getUsuarioDAO();
            List l = uDao.obtenerUsuariosRol(rol);
            Iterator it = l.iterator();
            while (it.hasNext()) {
                org.auriga.core.modelo.transferObjects.Usuario usuarioAu
                        = (org.auriga.core.modelo.transferObjects.Usuario) it.next();
                String id = usuarioAu.getUsuarioId();
                Query query = pm.newQuery(UsuarioEnhanced.class);
                query.declareParameters("String id");
                query.setFilter("username==id");

                col = (Collection) query.execute(id);
                if (col != null) {
                    for (Iterator iter = col.iterator(); iter.hasNext();) {
                        UsuarioEnhanced usuEn = (UsuarioEnhanced) iter.next();
                        pm.makeTransient(usuEn);
                        usuariosTrans.add(usuEn);
                    }
                }
                query.closeAll();
                Log.getInstance().info(this.getClass(), "Consultas cerradas");
            }
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error consultando usuarios del rol " + rol.getRolId() + ":" + printer.toString());
            throw new ErrorConexionException("Error consultando los usuarios del rol", e);
        } catch (DAOException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error consultando usuarios del rol " + rol.getRolId() + ":" + printer.toString());
            throw new ErrorConexionException("Error consultando los usuarios del rol", e);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        Iterator itTrans = usuariosTrans.iterator();
        while (itTrans.hasNext()) {
            UsuarioEnhanced uE = (UsuarioEnhanced) itTrans.next();
            gov.sir.core.negocio.modelo.Usuario usuario
                    = (gov.sir.core.negocio.modelo.Usuario) uE.toTransferObject();
            usuarios.add(usuario);
        }
        return usuarios;
    }

    /**
     * Consulta los roles existentes en el sistema
     *
     * @return la lista de los roles existentes en el sistema
     * @throws DAOException
     */
    public List consultarRoles() throws DAOException {

        List lista = new ArrayList();
        FenrirProperties hp = FenrirProperties.getInstancia();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        /**
         * @Autor: Santiago V�squez
         * @Change: 2079.INACTIVAR.PROCESO.CONSULTAS.INDIVIDUALES
         */
        String consulta = "SELECT R.ROL_ID, R.NOMBRE,R.DESCRIPCION FROM AURIGA.CMN_ROL R WHERE R.ROL_ID NOT IN (SELECT RI.ROL_ID FROM AURIGA.CMN_ROL_INACTIVO RI)";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setRolId(rs.getString(1));
                rol.setNombre(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
                lista.add(rol);
            }
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
        return lista;
    }

    /**
     * Consulta las estaciones existentes en el sistema
     *
     * @return la lista de las estaciones existentes en el sistema
     * @throws DAOException
     */
    public List consultarEstaciones() throws DAOException {

        List lista = new ArrayList();
        FenrirProperties hp = FenrirProperties.getInstancia();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT ESTACION_ID, NOMBRE,DESCRIPCION FROM AURIGA.CMN_ESTACION";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();

            while (rs.next()) {
                Estacion res = new Estacion();
                res.setEstacionId(rs.getString(1));
                res.setNombre(rs.getString(2));
                res.setDescripcion(rs.getString(3));
                lista.add(res);
            }
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

                if (jdoPM != null) {
                    jdoPM.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(this.getClass(), e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return lista;
    }

    /**
     * Obtiene una Lista, con los usuarios disponibles en el sistema
     * <code>Usuario</code> asociados.
     *
     * @return Lista con los usuarios disponibles en el sistema
     * @param nombreUsuario del usuario a ser consultado. Si este par�metro es
     * null retorna una lista con todos los usuarios
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public List consultarUsuarios(String nombreUsuario) throws DAOException {

        PersistenceManager pm = null;

        List listaUsuariosEnhanced = new ArrayList();
        List listaUsuarios = new ArrayList();

        try {
            pm = AdministradorPM.getPM();
            VersantQuery query = (VersantQuery) pm.newQuery(UsuarioEnhanced.class);
            Collection col = null;
            if (nombreUsuario == null || nombreUsuario.trim().equals("")) {
                col = (Collection) query.execute();
            } else {
                query.declareParameters("String nombreUsuario");
                query.setFilter("nombre == nombreUsuario");
                query.setOrdering("nombre ascending");
                col = (Collection) query.execute(nombreUsuario);
            }

            Iterator iter = col.iterator();
            UsuarioEnhanced usuario = null;

            while (iter.hasNext()) {
                usuario = (UsuarioEnhanced) iter.next();
                pm.makeTransient(usuario);
                listaUsuariosEnhanced.add(usuario);
            }
            query.closeAll();

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
            Iterator itMap = listaUsuariosEnhanced.iterator();
            //Se recorre cada una de las entradas de la lista para hacer 
            //transfer sobre cada uno de las asociaciones que se encuentran en el mismo. 
            while (itMap.hasNext()) {
                UsuarioEnhanced dato = (UsuarioEnhanced) itMap.next();
                gov.sir.core.negocio.modelo.Usuario user
                        = (gov.sir.core.negocio.modelo.Usuario) dato.toTransferObject();

                listaUsuarios.add(user);
            }
        }
        return listaUsuarios;
    }
    
    public void eliminarTramiteSuspensionTurno(int idUsuario, String turno) throws DAOException {
        PersistenceManager pm = null;
        VersantPersistenceManager pm2 = null;
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try {

            pm = AdministradorPM.getPM();
            pm2 = (VersantPersistenceManager) pm;
            pm2.currentTransaction().begin();
            con = pm2.getJdbcConnection(null);

            VersantQuery query = (VersantQuery) pm.newQuery(TramiteSuspensionEnhanced.class);
            Collection col = null;

            if (idUsuario <= 0) {
                col = (Collection) query.execute();
            } else {
                String ultimoTramiteAgregado = "DELETE FROM SIR_OP_TRAMITE_SUSPENSION "
                        + "WHERE ID_TRAMITE_SUSP = (SELECT MAX(ID_TRAMITE_SUSP) "
                        + "FROM SIR_OP_TRAMITE_SUSPENSION WHERE TRAMS_ID_USUARIO = " + idUsuario + " AND TRAMS_TURNO = '" + turno + "')";
                ps = con.prepareStatement(ultimoTramiteAgregado);
                ps.executeQuery();
            }

            pm2.currentTransaction().commit();
            query.closeAll();
            
        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            if (pm2.currentTransaction().isActive()) {
                pm2.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null) {
                pm.close();
            }

            if (pm2 != null) {
                pm2.close();
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    public List consultarRespuestasUsuarios(int idUsuario, String turno) throws DAOException {

        PersistenceManager pm = null;
        VersantPersistenceManager pm2 = null;
        
        List listaRespUsuarioEnhanced = new ArrayList();
        List listaRespuestas = new ArrayList();
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        
        try {
            pm = AdministradorPM.getPM();
            pm2 = (VersantPersistenceManager) pm;
            pm2.currentTransaction().begin();
            con = pm2.getJdbcConnection(null);

            VersantQuery query = (VersantQuery) pm.newQuery(TramiteSuspensionEnhanced.class);
            Collection col = null;
            
            if (idUsuario <= 0) {
                col = (Collection) query.execute();
            } else {
                String respPorUsuario = "SELECT ID_TRAMITE_SUSP, TRAMS_NOMBRE_FASE, TRAMS_FECHA, TRAMS_USUARIO, TRAMS_ID_USUARIO,"
                        + "TRAMS_DESCRIPCION, TRAMS_ID_ARCHIVO_JUSTIFICA, TRAMS_TURNO FROM SIR_OP_TRAMITE_SUSPENSION "
                        + "WHERE TRAMS_ID_USUARIO = " + idUsuario + " AND TRAMS_TURNO='" + turno + "' ORDER BY ID_TRAMITE_SUSP DESC";
                ps = con.prepareStatement(respPorUsuario);
                rs = ps.executeQuery();
            }
            
            pm2.currentTransaction().commit();
            TramiteSuspensionEnhanced tramiteSuspensionEnhanced = null;
            while (rs.next()) {
                tramiteSuspensionEnhanced = new TramiteSuspensionEnhanced();
                tramiteSuspensionEnhanced.setTramsId(Integer.parseInt(rs.getString("ID_TRAMITE_SUSP")));
                
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date admFecha = df.parse(rs.getString("TRAMS_FECHA"));

                tramiteSuspensionEnhanced.setTramsFecha(admFecha);
                tramiteSuspensionEnhanced.setTramsDescripcion(rs.getString("TRAMS_DESCRIPCION"));

                ArchivosJustificaEnhancedPk ajId = new ArchivosJustificaEnhancedPk();

                if (rs.getString("TRAMS_ID_ARCHIVO_JUSTIFICA") != null && !rs.getString("TRAMS_ID_ARCHIVO_JUSTIFICA").equals("")) {
                    ajId.jusIdArchivo = Integer.parseInt(rs.getString("TRAMS_ID_ARCHIVO_JUSTIFICA"));
                    tramiteSuspensionEnhanced.setTramsIdArchivoJustifica(getArchivosJustificaByID(ajId, pm));
                } else {
                    tramiteSuspensionEnhanced.setTramsIdArchivoJustifica(null);
                }
                               
                tramiteSuspensionEnhanced.setTramsTurno(rs.getString("TRAMS_TURNO"));
                tramiteSuspensionEnhanced.setTramsIdUsuario(Integer.parseInt(rs.getString("TRAMS_ID_USUARIO")));
                tramiteSuspensionEnhanced.setTramsNombreFase(rs.getString("TRAMS_NOMBRE_FASE"));
                tramiteSuspensionEnhanced.setTramsUsuario(rs.getString("TRAMS_USUARIO"));
                
                pm.makeTransient(tramiteSuspensionEnhanced);
                listaRespUsuarioEnhanced.add(tramiteSuspensionEnhanced);
            }
            
            query.closeAll(); 
            
        }catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            if (pm2.currentTransaction().isActive()) {
                pm2.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null) {
                pm.close();
            }

            if (pm2 != null) {
                pm2.close();
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Iterator itMap = listaRespUsuarioEnhanced.iterator();
            //Se recorre cada una de las entradas de la lista para hacer 
            //transfer sobre cada uno de las asociaciones que se encuentran en el mismo. 
            while (itMap.hasNext()) {
                TramiteSuspensionEnhanced dato = (TramiteSuspensionEnhanced) itMap.next();

                gov.sir.core.negocio.modelo.TramiteSuspension tramSuspension = (gov.sir.core.negocio.modelo.TramiteSuspension) dato.toTransferObject();
                listaRespuestas.add(tramSuspension);
            }
        }
        return listaRespuestas;
    }

    

    /**
     * Obtiene una Lista, con lss justificaciones por usuario disponibles en el
     * sistema <code>JustificaAdm</code> asociados.
     *
     * @return Lista con las justificaciones por usuario disponibles en el
     * sistema
     * @param idUsuario del usuario a ser consultado. Si este par�metro es null
     * @param fechaIni
     * @param fechaFin retorna una lista con todos los usuarios
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.JustificaAdm
     */
    public List consultarJustificacionesUsuarios(int idUsuario, String fechaIni, String fechaFin) throws DAOException {
        //System.out.println("ID DE USUARIO JUSTIFICACIONES: " + idUsuario);
        PersistenceManager pm = null;
        VersantPersistenceManager pm2 = null;

        List listaJustificaAdmEnhanced = new ArrayList();
        List listaJustificaciones = new ArrayList();
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            pm = AdministradorPM.getPM();
            pm2 = (VersantPersistenceManager) pm;
            pm2.currentTransaction().begin();
            con = pm2.getJdbcConnection(null);

            VersantQuery query = (VersantQuery) pm.newQuery(JustificaAdmEnhanced.class);
            Collection col = null;
            if ((Object) idUsuario == null || idUsuario <= 0) {
                col = (Collection) query.execute();
            } else {
                String sinRangoFechas = "SELECT ADM_ID, ADM_FECHA, ADM_DESCRIPCION, ADM_TIP_ID_TIPO, "
                        + "ADM_JUS_ID_ARCHIVO, ADM_IP_PC_FUNC_LOG, ADM_ID_USUARIO, "
                        + "ADM_USUARIO_MODIFICA, ADM_CIRCULO_USR_MODIFICADO  FROM SIR_OP_JUSTIFICA_ADM "
                        + "WHERE ADM_ID_USUARIO = " + idUsuario + " ORDER BY ADM_ID DESC";

                String conRangoFechas = "SELECT ADM_ID, ADM_FECHA, ADM_DESCRIPCION, ADM_TIP_ID_TIPO, "
                        + "ADM_JUS_ID_ARCHIVO, ADM_IP_PC_FUNC_LOG, ADM_ID_USUARIO, "
                        + "ADM_USUARIO_MODIFICA, ADM_CIRCULO_USR_MODIFICADO  FROM SIR_OP_JUSTIFICA_ADM "
                        + "WHERE ADM_ID_USUARIO = " + idUsuario + " "
                        + "AND ADM_FECHA >= TO_DATE('" + fechaIni + " 00:00:00','dd-mm-yyyy hh24:mi:ss') "
                        + "AND ADM_FECHA <= TO_DATE('" + fechaFin + " 23:59:00','dd-mm-yyyy hh24:mi:ss') "
                        + "ORDER BY ADM_ID DESC";

//                query.declareParameters("String idUsuario");
                System.out.println("fecha de Inicio: " + fechaIni);
                System.out.println("fecha de Fin: " + fechaFin);
                if ((fechaIni != null && !fechaIni.equals("")) && (fechaFin != null && !fechaFin.equals(""))) {
                    System.out.println("Entro con fechas rango");
                    System.out.println("QUERY A EJECUTAR :" + conRangoFechas);
                    ps = con.prepareStatement(conRangoFechas);
//                    query.setFilter("admIdUsuario == idUsuario && admFecha >= " + fechaIni + " && admFecha <= " + fechaFin + " ");
                } else {
                    System.out.println("Entro sin fechas rango");
                    System.out.println("QUERY A EJECUTAR :" + sinRangoFechas);
                    ps = con.prepareStatement(sinRangoFechas);
//                    query.setFilter("admIdUsuario == idUsuario");
                }

//                query.setOrdering("admId descending");
//                col = (Collection) query.execute(idUsuario);
                rs = ps.executeQuery();
            }
            pm2.currentTransaction().commit();
//            Iterator iter = col.iterator();
            JustificaAdmEnhanced justificaAdmEnhanced = null;

//            while (iter.hasNext()) {
//                justificacion = (JustificaAdmEnhanced) iter.next();
//                justificacion.setArchivosJustifica(justificacion.getArchivosJustifica());
//                justificacion.setJustificaTipos(justificacion.getJustificaTipos());
//                pm.makeTransient(justificacion);
//                listaJustificaAdmEnhanced.add(justificacion);
//
//            }
            while (rs.next()) {
                System.out.println("ID ARCHIVO: " + rs.getString("ADM_JUS_ID_ARCHIVO"));
                System.out.println("ID TIPO JUST: " + rs.getString("ADM_TIP_ID_TIPO"));

                justificaAdmEnhanced = new JustificaAdmEnhanced();

                justificaAdmEnhanced.setAdmId(Integer.parseInt(rs.getString("ADM_ID")));
                System.out.println("FECHA JUST: " + rs.getString("ADM_FECHA"));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date admFecha = df.parse(rs.getString("ADM_FECHA"));

                System.out.println("FECHA JUST2: " + admFecha);

                justificaAdmEnhanced.setAdmFecha(admFecha);
                justificaAdmEnhanced.setAdmDescripcion(rs.getString("ADM_DESCRIPCION"));

                ArchivosJustificaEnhancedPk ajId = new ArchivosJustificaEnhancedPk();

                if (rs.getString("ADM_JUS_ID_ARCHIVO") != null && !rs.getString("ADM_JUS_ID_ARCHIVO").equals("")) {
                    ajId.jusIdArchivo = Integer.parseInt(rs.getString("ADM_JUS_ID_ARCHIVO"));
                    justificaAdmEnhanced.setArchivosJustifica(getArchivosJustificaByID(ajId, pm));
                } else {
                    System.out.println("NULO EL VALOR DE ARCHIVO");
                    justificaAdmEnhanced.setArchivosJustifica(null);
                }

                JustificaTiposEnhancedPk jtId = new JustificaTiposEnhancedPk();

                if (rs.getString("ADM_TIP_ID_TIPO") != null && !rs.getString("ADM_TIP_ID_TIPO").equals("")) {
                    jtId.tipIdTipo = Integer.parseInt(rs.getString("ADM_TIP_ID_TIPO"));
                    justificaAdmEnhanced.setJustificaTipos(getJustificaTiposByID(jtId, pm));
                } else {
                    System.out.println("NULO EL VALOR DE TIPO");
                    justificaAdmEnhanced.setJustificaTipos(null);
                }

                justificaAdmEnhanced.setAdmIpPcFuncLog(rs.getString("ADM_IP_PC_FUNC_LOG"));
                justificaAdmEnhanced.setAdmIdUsuario(Integer.parseInt(rs.getString("ADM_ID_USUARIO")));
                justificaAdmEnhanced.setAdmUsuarioModifica(rs.getString("ADM_USUARIO_MODIFICA"));
                justificaAdmEnhanced.setAdmCirculoUsrModificado(rs.getString("ADM_CIRCULO_USR_MODIFICADO"));

                pm.makeTransient(justificaAdmEnhanced);
                listaJustificaAdmEnhanced.add(justificaAdmEnhanced);
            }

            query.closeAll();

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            if (pm2.currentTransaction().isActive()) {
                pm2.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null) {
                pm.close();
            }

            if (pm2 != null) {
                pm2.close();
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Iterator itMap = listaJustificaAdmEnhanced.iterator();
            //Se recorre cada una de las entradas de la lista para hacer 
            //transfer sobre cada uno de las asociaciones que se encuentran en el mismo. 
            while (itMap.hasNext()) {
                JustificaAdmEnhanced dato = (JustificaAdmEnhanced) itMap.next();

                gov.sir.core.negocio.modelo.JustificaAdm justificacion = (gov.sir.core.negocio.modelo.JustificaAdm) dato.toTransferObject();
                listaJustificaciones.add(justificacion);
            }
        }
        return listaJustificaciones;
    }

    /**
     * Obtiene una Lista, con los tipos de justificaciones
     * <code>JustificaTipos</code> asociados.
     *
     * @return Lista con los tipos de justificaciones
     *
     * @param tipoJust
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.JustificaTipos
     */
    public List consultarTiposJustificaciones(String tipoJust) throws DAOException {

        PersistenceManager pm = null;
        VersantPersistenceManager pm2 = null;

        List listaJustificaTiposEnhanced = new ArrayList();
        List listaTiposJustificaciones = new ArrayList();

        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            pm = AdministradorPM.getPM();
            pm2 = (VersantPersistenceManager) pm;
            pm2.currentTransaction().begin();
            con = pm2.getJdbcConnection(null);

            String query = "SELECT TIP_ID_TIPO, TIP_NOMBRE_NOVEDAD, TIP_DESCRIPCION, TIP_VERSION "
                    + " FROM SIR_OP_JUSTIFICA_TIPOS "
                    + " WHERE TIP_NOMBRE_NOVEDAD = '" + tipoJust + "' ORDER BY TIP_ID_TIPO DESC";

            System.out.println("Query tipos justificaciones " + query);

            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            pm2.currentTransaction().commit();

            JustificaTiposEnhanced justificaTiposEnhanced = null;

            while (rs.next()) {

                justificaTiposEnhanced = new JustificaTiposEnhanced();
                justificaTiposEnhanced.setTipIdTipo(Integer.parseInt(rs.getString("TIP_ID_TIPO")));
                justificaTiposEnhanced.setTipNombreNovedad(rs.getString("TIP_NOMBRE_NOVEDAD"));
                justificaTiposEnhanced.setTipDescripcion(rs.getString("TIP_DESCRIPCION"));
                justificaTiposEnhanced.setTipVersion(Integer.parseInt(rs.getString("TIP_VERSION")));

                pm.makeTransient(justificaTiposEnhanced);
                listaJustificaTiposEnhanced.add(justificaTiposEnhanced);
            }

        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            if (pm2.currentTransaction().isActive()) {
                pm2.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            if (pm != null) {
                pm.close();
            }

            if (pm2 != null) {
                pm2.close();
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Iterator itMap = listaJustificaTiposEnhanced.iterator();
            //Se recorre cada una de las entradas de la lista para hacer 
            //transfer sobre cada uno de las asociaciones que se encuentran en el mismo. 
            while (itMap.hasNext()) {
                JustificaTiposEnhanced dato = (JustificaTiposEnhanced) itMap.next();

                gov.sir.core.negocio.modelo.JustificaTipos tipoJustificacion = (gov.sir.core.negocio.modelo.JustificaTipos) dato.toTransferObject();

                System.out.println("TIPO JUSTIFICACION: " + tipoJustificacion.getTipDescripcion());
                listaTiposJustificaciones.add(tipoJustificacion);
            }
        }
        return listaTiposJustificaciones;
    }

    /**
     * Obtiene un <code>Usuario</code> dado su identificador, m�todo utilizado
     * para transacciones
     * <p>
     * En caso de que no se encuentre en la Base de Datos una sucursal con el
     * identificador pasado como par�metro, se retorna <code>null</code>.
     *
     * @param sucID identificador de la <code>SucursalBanco</code>.
     * @param pm PersistenceManager de la transaccion
     * @return <code>SucursalBanco</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    private UsuarioEnhanced getUsuarioByID(UsuarioEnhancedPk usuarioId, PersistenceManager pm)
            throws DAOException {
        UsuarioEnhanced rta = null;

        //if (usuarioId.idUsuario != null) {
        try {
            rta = (UsuarioEnhanced) pm.getObjectById(usuarioId, true);
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().debug(this.getClass(), e);
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        //}
        return rta;
    }

    /**
     * Obtiene y avanza la secuencia de la tabla especificada (TOMADO de
     * FORSETTI)
     *
     * @param tableName Nombre de la tabla de la cual se quiere obtener el
     * secuencial.
     * @param pm Persistence Manager de la transacci�n.
     * @return El secuencial requerido.
     * @throws DAOException
     */
    protected long getSecuencial(String tableName, PersistenceManager pm) throws DAOException {
        long rta = -1;

        boolean transaccionIndependiente = false;
        VersantPersistenceManager pm2 = null;

        HermodProperties hprop = HermodProperties.getInstancia();
        String secuencias = hprop.getProperty(
                HermodProperties.SECUENCIALES_POR_SECUENCIA);
        boolean usarSecuencia = false;

        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;

        if (secuencias != null) {
            String[] secuenciasSplit = secuencias.split(";");
            for (int i = 0; i < secuenciasSplit.length; i++) {
                if (secuenciasSplit[i].equals(tableName)) {
                    usarSecuencia = true;
                    break;
                }
            }
        }

        if (pm == null) {
            transaccionIndependiente = true;
        }

        if (tableName != null) {
            try {
                if (transaccionIndependiente) {
                    pm = AdministradorPM.getPM();
                    pm.currentTransaction().begin();
                }

                if (usarSecuencia) {
                    String sql = "SELECT SEC_" + (tableName.length() > 26 ? tableName.substring(0, 26) : tableName) + ".nextval FROM DUAL";

                    pm2 = (VersantPersistenceManager) pm;

                    con = pm2.getJdbcConnection(null);

                    pst = con.prepareStatement(sql);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        rta = rs.getLong(1);
                    } else {
                        throw new DAOException("No se encontr� la secuencia");
                    }

                } else {
                    //Se hace el cambio de tipo de bloqueo pesimista para
                    //que se bloquee la tabla la cual  nos
                    //provee el secuencial
                    pm2 = (VersantPersistenceManager) pm;
                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
                    pm = pm2;

                    SecuenciasEnhancedPk sid = new SecuenciasEnhancedPk();
                    sid.tableName = tableName;

                    SecuenciasEnhanced s = this.getSecuenciaByID(sid, pm);
                    s.setLastUsedId(s.getLastUsedId() + 1);

                    //Volvemos a setear el tipo de bloqueo pesimista
                    //para que no nos bloquee los siquientes registros
                    //consultados
                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

                    rta = s.getLastUsedId(); // + 1;
                }

                if (transaccionIndependiente) {
                    pm.currentTransaction().commit();
                }

            } catch (JDOObjectNotFoundException e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }                
                throw new DAOException("No se encontr� el registro de la secuencia", e);
            } catch (JDOException e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }                
                throw new DAOException("Error obteniendo el registro de la secuencia", e);
            } catch (DAOException e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }                
                throw new DAOException("Error obteniendo el registro de la secuencia", e);
            } catch (Exception e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }
                
                throw new DAOException("Error obteniendo el registro de la secuencia", e);
            } finally {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if (pm != null) {
                        pm.close();
                    }
                }
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return rta;
    }

    /**
     * Obtiene una secuencia dado su identificador, m�todo utilizado para
     * transacciones TOMADO de FORSETTI
     *
     * @param sID identificador de la secuencia
     * @param pm PersistenceManager de la transaccion
     * @return Secuencia con sus atributos
     * @throws DAOException
     */
   protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhancedPk sID, PersistenceManager pm)
            throws DAOException {
        SecuenciasEnhanced rta = null;

        if (sID.tableName != null) {
            try {
                rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene un <code>ArchivosJustifica</code> dado su identificador, m�todo
     * utilizado para transacciones
     * <p>
     * En caso de que no se encuentre en la Base de Datos una sucursal con el
     * identificador pasado como par�metro, se retorna <code>null</code>.
     *
     * @param ajID identificador de la <code>ArchivosJustifica</code>.
     * @param pm PersistenceManager de la transaccion
     * @return <code>ArchivosJustificaEnhanced</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    private ArchivosJustificaEnhanced getArchivosJustificaByID(ArchivosJustificaEnhancedPk ajID, PersistenceManager pm)
            throws DAOException {
        ArchivosJustificaEnhanced rta = null;

        try {
            rta = (ArchivosJustificaEnhanced) pm.getObjectById(ajID, true);
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().debug(this.getClass(), e);
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        return rta;
    }

    /**
     * Obtiene un <code>JustificaTipos</code> dado su identificador, m�todo
     * utilizado para transacciones
     * <p>
     * En caso de que no se encuentre en la Base de Datos una sucursal con el
     * identificador pasado como par�metro, se retorna <code>null</code>.
     *
     * @param jtID identificador de la <code>ArchivosJustifica</code>.
     * @param pm PersistenceManager de la transaccion
     * @return <code>ArchivosJustificaEnhanced</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    private JustificaTiposEnhanced getJustificaTiposByID(JustificaTiposEnhancedPk jtID, PersistenceManager pm)
            throws DAOException {
        JustificaTiposEnhanced rta = null;

        try {
            rta = (JustificaTiposEnhanced) pm.getObjectById(jtID, true);
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().debug(this.getClass(), e);
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        return rta;
    }

    //	//////////////////////////////////////////////////////////
    /**
     * Crea un <code>Rol</code> en el sistema
     *
     * @param rol  <code>Rol</code> a ser creado
     * @param usuario que crea el rol
     * @return lista actualizada de los roles existentes en el sistema
     * @throws DAOException
     */
    public List crearRol(Rol rol, Usuario usuario) throws DAOException {
        DAOFactory fact = null;
        RolDAO rdao = null;

        try {
            org.auriga.core.modelo.transferObjects.Usuario usuarioTO = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioTO = usuario.getUsuarioAuriga();
            fact = DAOFactory.getFactory();
            rdao = fact.getRolDAO();
            rdao.crearRol(rol, usuarioTO);
            return consultarRoles();
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    //	//////////////////////////////////////////////////////////
    /**
     * Consulta los niveles existentes en el sistema
     *
     * @return la lista de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List consultarNiveles() throws DAOException {
        List lista = new ArrayList();
        FenrirProperties hp = FenrirProperties.getInstancia();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta
                = "SELECT NIVEL_ID, NIVEL_SUPERIOR_ID , NOMBRE, ATRIBUTO1, ATRIBUTO2, ATRIBUTO3, ATRIBUTO4, ATRIBUTO5 FROM AURIGA.CMN_NIVEL";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            rs = ps.executeQuery();

            while (rs.next()) {
                Nivel dato = new Nivel();
                dato.setNivelId(rs.getString(1));
                Nivel padre = new Nivel();
                padre.setNivelId(rs.getString(2));
                dato.setParent(padre);
                dato.setNombre(rs.getString(3));
                dato.setAtributo1(rs.getString(4));
                dato.setAtributo2(rs.getString(5));
                dato.setAtributo3(rs.getString(6));
                dato.setAtributo4(rs.getString(7));
                dato.setAtributo5(rs.getString(8));
                lista.add(dato);
            }
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
        return lista;
    }

    /**
     * Crea un <code>Nivel</code> en el sistema
     *
     * @param rol  <code>Nivel</code> a ser creado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List crearNivel(Nivel nivel) throws DAOException {
        DAOFactory fact = null;
        NivelDAO rdao = null;

        try {
            fact = DAOFactory.getFactory();
            rdao = fact.getNivelDAO();
            rdao.crearNivel(nivel);
            return consultarNiveles();
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

    }

    /**
     * Elimina un <code>Nivel</code> del sistema
     *
     * @param rol  <code>Nivel</code> a ser eliminado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List eliminarNivel(Nivel nivel) throws DAOException {
        DAOFactory fact = null;
        NivelDAO rdao = null;

        try {
            fact = DAOFactory.getFactory();
            rdao = fact.getNivelDAO();
            rdao.eliminarNivel(nivel);
            return consultarNiveles();
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Consulta las estaciones relacionadas con un <code>Circulo</code>
     * determinado
     *
     * @param circulo el <code>Circulo</code> del cual se requiere consultar sus
     * estaciones
     * @return la lista de las estaciones relacionadas con el
     * <code>Circulo</code> dado como par�metro
     * @throws DAOException
     */
    public List consultarEstacionesPorCirculo(Circulo circulo) throws DAOException {
        DAOFactory fact = null;
        EstacionDAO rdao = null;

        try {
            fact = DAOFactory.getFactory();
            rdao = fact.getEstacionDAO();

            Jerarquia jer = new Jerarquia();
            jer.setJerarquiaId(CJerarquia.JER_CIRCULOS);

            if (circulo.getIdCirculo() == null) {
                throw new DAOException("Debe proporcionar un identificador de c�rculo para realizar la consulta.");
            }

            Nivel nivel = new Nivel();
            nivel.setAtributo1(circulo.getIdCirculo());

            return rdao.obtenerEstaciones(jer, nivel, true);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    //	//////////////////////////////////////////////////////////
    /**
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @param rol  <code>Rol</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculoRol(Circulo circulo, Rol rol) throws DAOException {
        List listaUsuariosEnh = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta
                = "SELECT  DISTINCT SU.ID_USUARIO "
                + "FROM  SIR_OP_USUARIO SU, AURIGA.CMN_JERARQUIA CJ, AURIGA.CMN_NIVEL CN, "
                + "AURIGA.CMN_REL_ESTACION_NIVEL CREN,  AURIGA.CMN_REL_USU_ROL_EST    CRUR "
                + "WHERE   CJ.JERARQUIA_ID = ? AND "
                + "CJ.NIVEL_INICIO = CN.NIVEL_SUPERIOR_ID AND CN.ATRIBUTO1 = ?  AND "
                + "CN.NIVEL_ID = CREN.NIVEL_ID AND  CREN.ESTACION_ID = CRUR.ESTACION_ID AND "
                + "CRUR.ROL_ID = ?  AND 	CRUR.USUARIO_ID = SU.SRIO_NOMBRE_DE_USUARIO ";

        try {
            if (circulo == null || circulo.getIdCirculo() == null) {
                throw new DAOException("Debe proporcionar un identificador de C�rculo");
            }
            if (rol == null || rol.getRolId() == null) {
                throw new DAOException("Debe proporcionar un identificador de Rol");
            }
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            ps.setString(1, CJerarquia.JER_CIRCULOS);
            ps.setString(2, circulo.getIdCirculo());
            ps.setString(3, rol.getRolId());

            rs = ps.executeQuery();

            List ids = new ArrayList();

            while (rs.next()) {
                UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
                usuarioId.idUsuario = rs.getLong(1);
                ids.add(usuarioId);
            }
            rs.close();
            ps.close();

            for (Iterator iter = ids.iterator(); iter.hasNext();) {
                //UsuarioEnhanced usuarioEnh = getUsuarioByID((UsuarioEnhanced.ID)iter.next(), jdoPM);
                UsuarioEnhanced usuarioEnh
                        = (UsuarioEnhanced) jdoPM.getObjectById((UsuarioEnhancedPk) iter.next(), true);
                jdoPM.makeTransientAll(usuarioEnh.getSubtiposAtencions());
                jdoPM.makeTransient(usuarioEnh);
                listaUsuariosEnh.add(usuarioEnh);
            }
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

        listaUsuariosEnh = TransferUtils.makeTransferAll(listaUsuariosEnh);
        return listaUsuariosEnh;
    }

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
     *
     * (non-Javadoc)
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#consultarUsuariosPorCirculoRolByEstado(gov.sir.core.negocio.modelo.Circulo,
     * org.auriga.core.modelo.transferObjects.Rol, boolean)
     */
    public List consultarUsuariosPorCirculoRolByEstado(Circulo circulo, Rol rol, boolean estado) throws DAOException {
        List listaUsuariosEnh = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta
                = "SELECT DISTINCT SOU.ID_USUARIO "
                + "FROM  AURIGA.CMN_JERARQUIA CJ,"
                + "AURIGA.CMN_NIVEL CN,"
                + "AURIGA.CMN_REL_ESTACION_NIVEL CREN,"
                + "AURIGA.CMN_REL_USU_ROL_EST CRURE,"
                + "AURIGA.CMN_REL_ROL_ESTACION CRRE,"
                + "SIR_OP_USUARIO SOU "
                + "WHERE CJ.JERARQUIA_ID = ? "
                + "AND CJ.NIVEL_INICIO = CN.NIVEL_SUPERIOR_ID "
                + "AND CN.NIVEL_ID = CREN.NIVEL_ID "
                + "AND CREN.ESTACION_ID = CRURE.ESTACION_ID "
                + "AND CRURE.ESTACION_ID = CRRE.ESTACION_ID "
                + "AND CRURE.ROL_ID = CRRE.ROL_ID "
                + "AND CRURE.USUARIO_ID = SOU.SRIO_NOMBRE_DE_USUARIO "
                + "AND CRURE.ROL_ID = ? "
                + "AND CN.ATRIBUTO1 = ? "
                + "AND CRRE.ACTIVA = ? ";

        try {
            if (circulo == null || circulo.getIdCirculo() == null) {
                throw new DAOException("Debe proporcionar un identificador de C�rculo");
            }
            if (rol == null || rol.getRolId() == null) {
                throw new DAOException("Debe proporcionar un identificador de Rol");
            }
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            ps.setString(1, CJerarquia.JER_CIRCULOS);
            ps.setString(2, rol.getRolId());
            ps.setString(3, circulo.getIdCirculo());
            ps.setInt(4, estado ? 1 : 0);

            rs = ps.executeQuery();

            List ids = new ArrayList();

            while (rs.next()) {
                UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
                usuarioId.idUsuario = rs.getLong(1);
                ids.add(usuarioId);
            }
            rs.close();
            ps.close();

            for (Iterator iter = ids.iterator(); iter.hasNext();) {
                UsuarioEnhanced usuarioEnh = (UsuarioEnhanced) jdoPM.getObjectById((UsuarioEnhancedPk) iter.next(), true);
                jdoPM.makeTransientAll(usuarioEnh.getSubtiposAtencions());
                jdoPM.makeTransient(usuarioEnh);
                listaUsuariosEnh.add(usuarioEnh);
            }
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

        listaUsuariosEnh = TransferUtils.makeTransferAll(listaUsuariosEnh);
        return listaUsuariosEnh;
    }

    public List consultarUsuariosPorCirculoRoles(Circulo circulo, List rolesCirculo) throws DAOException {
        List listaUsuariosEnh = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        String roles = "'";
        if (rolesCirculo != null && rolesCirculo.size() > 0) {
            for (int i = 0; i < rolesCirculo.size(); i++) {
                roles = roles + rolesCirculo.get(i) + "'";
                if (i < (rolesCirculo.size() - 1)) {
                    roles = roles + ",'";
                }
            }
        }

        String consulta
                = "SELECT  DISTINCT SU.ID_USUARIO "
                + "FROM  SIR_OP_USUARIO SU, AURIGA.CMN_JERARQUIA CJ, AURIGA.CMN_NIVEL CN, "
                + "AURIGA.CMN_REL_ESTACION_NIVEL CREN,  AURIGA.CMN_REL_USU_ROL_EST    CRUR "
                + "WHERE   CJ.JERARQUIA_ID = ? AND "
                + "CJ.NIVEL_INICIO = CN.NIVEL_SUPERIOR_ID AND CN.ATRIBUTO1 = ?  AND "
                + "CN.NIVEL_ID = CREN.NIVEL_ID AND  CREN.ESTACION_ID = CRUR.ESTACION_ID AND "
                + "CRUR.ROL_ID in (" + roles + ")  AND 	CRUR.USUARIO_ID = SU.SRIO_NOMBRE_DE_USUARIO ";

        try {
            if (circulo == null || circulo.getIdCirculo() == null) {
                throw new DAOException("Debe proporcionar un identificador de C�rculo");
            }
            if (roles == null || roles.equals("")) {
                throw new DAOException("Debe proporcionar un identificador de Rol");
            }
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            ps.setString(1, CJerarquia.JER_CIRCULOS);
            ps.setString(2, circulo.getIdCirculo());
            //ps.setString(3, roles);

            rs = ps.executeQuery();

            List ids = new ArrayList();

            while (rs.next()) {
                UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
                usuarioId.idUsuario = rs.getLong(1);
                ids.add(usuarioId);
            }
            rs.close();
            ps.close();

            for (Iterator iter = ids.iterator(); iter.hasNext();) {
                //UsuarioEnhanced usuarioEnh = getUsuarioByID((UsuarioEnhanced.ID)iter.next(), jdoPM);
                UsuarioEnhanced usuarioEnh
                        = (UsuarioEnhanced) jdoPM.getObjectById((UsuarioEnhancedPk) iter.next(), true);
                jdoPM.makeTransientAll(usuarioEnh.getSubtiposAtencions());
                jdoPM.makeTransient(usuarioEnh);
                listaUsuariosEnh.add(usuarioEnh);
            }
            jdoPM.currentTransaction().commit();
        } catch (NullPointerException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(OracleFenrirJDO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(OracleFenrirJDO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(OracleFenrirJDO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(OracleFenrirJDO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(OracleFenrirJDO.class, e.getMessage(), e);
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
                Log.getInstance().error(OracleFenrirJDO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        listaUsuariosEnh = TransferUtils.makeTransferAll(listaUsuariosEnh);
        return listaUsuariosEnh;
    }

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculo(Circulo circulo) throws DAOException {
        List listaUsuariosEnh = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT DISTINCT USUARIO_SIR.ID_USUARIO, "
                + "USUARIO_SIR.SRIO_NOMBRE "
                + "FROM SIR_OP_USUARIO USUARIO_SIR, "
                + "SIR_REL_USUARIO_CIRCULO USUARIO_SIR_CIRCULO, "
                + "AURIGA.CMN_USUARIO USUARIO_AURIGA "
                + "WHERE USUARIO_SIR.ID_USUARIO = USUARIO_SIR_CIRCULO.ID_USUARIO "
                + "AND USUARIO_AURIGA.USUARIO_ID = USUARIO_SIR.SRIO_NOMBRE_DE_USUARIO "
                + "AND USUARIO_SIR_CIRCULO.ID_CIRCULO = ? ";

        try {
            if (circulo == null || circulo.getIdCirculo() == null) {
                throw new DAOException("Debe proporcionar un identificador de C�rculo");
            }

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);
            //ps.setString(1, CJerarquia.JER_CIRCULOS);
            ps.setString(1, circulo.getIdCirculo());

            rs = ps.executeQuery();

            List ids = new ArrayList();

            while (rs.next()) {
                UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
                usuarioId.idUsuario = rs.getLong(1);
                ids.add(usuarioId);
            }
            rs.close();
            ps.close();

            for (Iterator iter = ids.iterator(); iter.hasNext();) {
                //UsuarioEnhanced usuarioEnh = getUsuarioByID((UsuarioEnhanced.ID)iter.next(), jdoPM);
                UsuarioEnhanced usuarioEnh
                        = (UsuarioEnhanced) jdoPM.getObjectById((UsuarioEnhancedPk) iter.next(), true);
                jdoPM.makeTransientAll(usuarioEnh.getSubtiposAtencions());
                jdoPM.makeTransient(usuarioEnh);
                listaUsuariosEnh.add(usuarioEnh);
            }
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

        listaUsuariosEnh = TransferUtils.makeTransferAll(listaUsuariosEnh);
        return listaUsuariosEnh;
    }

    /**
     * Este m�todo se encarga de consultar el nivel con el que est� relacionado
     * un c�rculo
     *
     * @param idCirculo identificador del c�rculo
     * @param jdoPm manejador de persistencia
     * @param fact factory para acceder a servicios de auriga
     * @return
     * @throws DAOException
     */
    private Nivel getNivelDeCirculo(String idCirculo, VersantPersistenceManager jdoPm, DAOFactory fact)
            throws DAOException {

        Nivel nivel = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = jdoPm.getJdbcConnection(null);
            /////////////////////////
            Jerarquia jerarquia = fact.getJerarquiaDAO().consultarJerarquia(CJerarquia.JER_CIRCULOS);
            Nivel nivelDeLaJerarquia = fact.getNivelDAO().consultarNivel(jerarquia.getnivel().getNivelId());

            //String consultaNivSupId = "SELECT NIVEL_SUPERIOR_ID FROM AURIGA.CMN_NIVEL WHERE NIVEL_ID = ? ";
            //PreparedStatement psId = conn.prepareStatement(consultaNivSupId);
            //psId.setString(1, nivelDeLaJerarquia.getNivelId());
            //ResultSet rsId = psId.executeQuery();
            //rsId.next();
            //String idNivelPadre = rsId.getString(1);
            /////////////////////////
            //String consulta = "SELECT N.NIVEL_ID FROM AURIGA.CMN_NIVEL N WHERE N.NIVEL_SUPERIOR_ID = ? AND N.ATRIBUTO1 = ? ";			
            String consulta = "SELECT N.NIVEL_ID FROM AURIGA.CMN_NIVEL N WHERE N.ATRIBUTO1 = ? ";

            ps = conn.prepareStatement(consulta);
            //ps.setString(1, idNivelPadre);
            //ps.setString(2, idCirculo);
            ps.setString(1, idCirculo);

            rs = ps.executeQuery();

            if (rs.next()) {
                String idNivelCirculo = rs.getString(1);
                nivel = new Nivel();
                nivel.setNivelId(idNivelCirculo);
            }

        } catch (SQLException e) {
            Log.getInstance().debug(OracleFenrirJDO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().debug(OracleFenrirJDO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return nivel;
    }

    /**
     * Habilita o deshabilita un  <code>Usuario</code> seg�n si su propiedad
     * activo es true o false.
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws DAOException
     */
    public void habilitarUsuario(gov.sir.core.negocio.modelo.Usuario usuario) throws DAOException {
        VersantPersistenceManager jdoPM = null;

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();

            UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
            uid.idUsuario = usuario.getIdUsuario();

            UsuarioEnhanced usuarioEnh = getUsuarioByID(uid, jdoPM);
            if (usuarioEnh == null) {
                throw new DAOException("No se encontr� el usuario con el id " + uid.idUsuario);
            }

            usuarioEnh.setActivo(usuario.isActivo());
            jdoPM.makePersistent(usuarioEnh);

            Rol rol = new Rol();
            rol.setRolId(CRoles.SIR_ROL_CALIFICADOR);
            Estacion estacion = this.getEstacion(usuario, rol);

            if (estacion != null) {
                RelRolEstacion rolEstacion = new RelRolEstacion();
                rolEstacion.setActiva(usuario.isActivo());
                rolEstacion.setEstacion(estacion);
                rolEstacion.setRol(rol);
                this.actualizarRelRolEstacion(rolEstacion);
            }

            jdoPM.currentTransaction().commit();

        } catch (NullPointerException e) {
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
            if (jdoPM != null) {
                jdoPM.close();
            }
        }
    }

    /**
     * Habilita o deshabilita un  <code>Usuario</code> seg�n si su propiedad
     * activo es true o false.
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws DAOException
     */
    public void actualizarRelRolEstacion(RelRolEstacion rolEstacion) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            fact.getRolDAO().actualizarRolEstacion(rolEstacion);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("Error actualizando la relaci�n Rol Estaci�n", e);
        }
    }

    /**
     * Actualiza un  <code>Usuario</code> en el modelo operativo, esta
     * actualizaci�n incluye el nombre, apellido1 y apellido2
     *
     * @param usuario  <code>Usuario</code> con los nuevos atributos del mismo
     * @return void
     * @throws DAOException
     */
    public void actualizarUsuario(gov.sir.core.negocio.modelo.Usuario usuario) throws DAOException {
        VersantPersistenceManager jdoPM = null;

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();

            UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
            uid.idUsuario = usuario.getIdUsuario();
            System.out.println("OracleFenrirJDO id_usuario: " + usuario.getIdUsuario());
            System.out.println("OracleFenrirJDO nombre: " + usuario.getNombre());
            System.out.println("OracleFenrirJDO apellido1: " + usuario.getApellido1());
            System.out.println("OracleFenrirJDO apellido2: " + usuario.getApellido2());

            UsuarioEnhanced usuarioEnh = getUsuarioByID(uid, jdoPM);
            if (usuarioEnh == null) {
                throw new DAOException("No se encontr� el usuario con el id " + uid.idUsuario);
            }

            usuarioEnh.setNombre(usuario.getNombre());
            usuarioEnh.setApellido1(usuario.getApellido1());
            usuarioEnh.setApellido2(usuario.getApellido2());

            jdoPM.makePersistent(usuarioEnh);
            jdoPM.currentTransaction().commit();
        } catch (NullPointerException e) {
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
            if (jdoPM != null) {
                jdoPM.close();
            }
        }
    }

    /**
     * Crea un nuevo registro de archivo en el sistema
     *
     * @param archivo el <code>ArchivosJustifica</code> a ser creado
     * @return <code>ArchivosJustifica</code> con todos sus atributos
     * @throws DAOException
     */
    public ArchivosJustifica nuevoArchivo(gov.sir.core.negocio.modelo.ArchivosJustifica archivo) throws DAOException {
        PersistenceManager pm = null;
        ArchivosJustificaEnhanced archivoEnh = null;
        ArchivosJustificaEnhanced ajEnh = null;
        
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            long jusIdArchivo = getSecuencial("SIR_OP_ARCHIVOS_JUSTIFICA", pm);            
            
            System.out.println("secuencia generada: " + jusIdArchivo);
            archivo.setJusIdArchivo((int) jusIdArchivo);
            archivo.setJusRutaFisica(archivo.getJusRutaFisica() + jusIdArchivo + "." + archivo.getJusTipoArchivo());

            System.out.println("despues de secuencia FORMATO archivo " + archivo.getJusTipoArchivo());
            System.out.println("despues de secuencia NOMBRE archivo " + archivo.getJusNombreOriginal());
            System.out.println("despues de secuencia PROCESO archivo " + archivo.getJusNombreProceso());
            System.out.println("despues de secuencia RUTA archivo " + archivo.getJusRutaFisica());
            System.out.println("despues de secuencia ID archivo " + archivo.getJusIdArchivo());
            System.out.println("despues de secuencia TAMANO archivo " + archivo.getJusTamanoArchivo());

            archivoEnh = ArchivosJustificaEnhanced.enhance(archivo);
            System.out.println("Informacion de archivoEnh: " + archivoEnh.getJusNombreOriginal());

            

            pm.makePersistent(archivoEnh);
            pm.currentTransaction().commit();

            ArchivosJustificaEnhancedPk ajId = new ArchivosJustificaEnhancedPk();
            ajId.jusIdArchivo = archivo.getJusIdArchivo();

            ajEnh = getArchivosJustificaByID(ajId, pm);
            
            pm.makeTransient(ajEnh);

            //pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (ArchivosJustifica) ajEnh.toTransferObject();
    }
   
    /**
     * Crea un nuevo registro de termite suspension en el sistema
     *
     * @param tramSuspension el <code>TramiteSuspension</code> a ser creado
     * @throws DAOException
     */
    public void crearRespuestasUsuarios(gov.sir.core.negocio.modelo.TramiteSuspension tramSuspension, gov.sir.core.negocio.modelo.ArchivosJustifica infoArchivo) throws DAOException {

        PersistenceManager pm = null;        
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            
            long tramsId = getSecuencial("SIR_OP_TRAMITE_SUSPENSION", pm);
            tramSuspension.setTramsId((int) tramsId);

            TramiteSuspensionEnhanced tramiteSuspensionEnhanced = TramiteSuspensionEnhanced.enhance(tramSuspension);

            if (infoArchivo != null) {
                ArchivosJustificaEnhancedPk archivoEnhId = new ArchivosJustificaEnhancedPk();
                archivoEnhId.jusIdArchivo = infoArchivo.getJusIdArchivo();
                ArchivosJustificaEnhanced archivoEnh = (ArchivosJustificaEnhanced) pm.getObjectById(archivoEnhId, true);
                tramiteSuspensionEnhanced.setTramsIdArchivoJustifica(archivoEnh);
            } else {
                tramiteSuspensionEnhanced.setTramsIdArchivoJustifica(null);
            }            
                    
            pm.makePersistent(tramiteSuspensionEnhanced);
            pm.currentTransaction().commit();

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
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
     * Crea un nuevo registro de justificacion en el sistema
     *
     * @param justificaAdm el <code>JustificaAdm</code> a ser creado
     * @throws DAOException
     */
    public void crearJustificaAdm(gov.sir.core.negocio.modelo.JustificaAdm justificaAdm, gov.sir.core.negocio.modelo.ArchivosJustifica infoArchivo, gov.sir.core.negocio.modelo.JustificaTipos justificaTipos) throws DAOException {
        PersistenceManager pm = null;
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            long admId = getSecuencial("SIR_OP_JUSTIFICA_ADM", pm);
            justificaAdm.setAdmId((int) admId);

            JustificaAdmEnhanced justificaAdmEnh = JustificaAdmEnhanced.enhance(justificaAdm);

            if (infoArchivo != null) {
                ArchivosJustificaEnhancedPk archivoEnhId = new ArchivosJustificaEnhancedPk();
                archivoEnhId.jusIdArchivo = infoArchivo.getJusIdArchivo();
                ArchivosJustificaEnhanced archivoEnh = (ArchivosJustificaEnhanced) pm.getObjectById(archivoEnhId, true);
                justificaAdmEnh.setArchivosJustifica(archivoEnh);
            } else {
                justificaAdmEnh.setArchivosJustifica(null);
            }

            JustificaTiposEnhancedPk justificaTiposEnhId = new JustificaTiposEnhancedPk();
            justificaTiposEnhId.tipIdTipo = justificaTipos.getTipIdTipo();
            JustificaTiposEnhanced justificaTiposEnh = (JustificaTiposEnhanced) pm.getObjectById(justificaTiposEnhId, true);

            justificaAdmEnh.setJustificaTipos(justificaTiposEnh);
            pm.makePersistent(justificaAdmEnh);
            pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
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
     * @see gov.sir.fenrir.dao.FenrirDAO#consultarHorarios()
     */
    public List consultarHorarios() throws DAOException {
        //TODO: FALTA IMPLEMENTAR EN AURIGA
        return null;
    }

    /**
     * Consulta la informaci�n de un <code>Usuario</code> almacenada en la base
     * de datos partiendo de un Usuario de Auriga
     *
     * @param usuario  <code>Usuario</code>
     * @return
     * @throws DAOException
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuario(        
            org.auriga.core.modelo.transferObjects.Usuario usuario) throws DAOException {
        gov.sir.core.negocio.modelo.Usuario usuarioCargado = null;
        UsuarioEnhanced usuarioPers = null;
        PersistenceManager pm = null;

        try {
            String user = usuario.getUsuarioId();

            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();

            Query query = pm.newQuery(UsuarioEnhanced.class);
            query.declareParameters("String user");
            query.setFilter("username==user");

            Collection col = (Collection) query.execute(user);

            if (col.size() <= 0) {
                throw new DAOException("No se encontr� el objeto");
            }
            for (Iterator iter = col.iterator(); iter.hasNext();) {
                usuarioPers = (UsuarioEnhanced) iter.next();
                List circulos = usuarioPers.getUsuarioCirculos();
                for (Iterator iter2 = circulos.iterator(); iter2.hasNext();) {
                    pm.makeTransient(iter2.next());
                }
                pm.makeTransient(usuarioPers);
            }
            query.closeAll();
            pm.currentTransaction().commit();
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
            throw new DAOException("No se encontr� el objeto", e);
            //	id = -1;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consultando estaciones:" + printer.toString());
            throw new DAOException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new DAOException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }

        usuarioCargado = (gov.sir.core.negocio.modelo.Usuario) TransferUtils.makeTransfer(usuarioPers);
        return usuarioCargado;
    }

    /**
     * Obtiene un <code>Usuario</code> dado su identificador.<p>
     * Retorna null, si no se encontr� el <code>Usuario</code> con el
     * identificador dado.
     *
     * @param bID identificador del banco.
     * @return <code>Usuario</code> con todos sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioByID(gov.sir.core.negocio.modelo.UsuarioPk bID) throws DAOException {
        UsuarioEnhanced br = null;
        PersistenceManager pm = null;

        try {
            pm = AdministradorPM.getPM();
            UsuarioEnhancedPk ueid = new UsuarioEnhancedPk();
            ueid.idUsuario = bID.idUsuario;

            br = this.getUsuarioByID(ueid, pm);
            List usuarioCirculos = br.getUsuarioCirculos();
            for (Iterator iter = usuarioCirculos.iterator(); iter.hasNext();) {
                UsuarioCirculoEnhanced uehn = (UsuarioCirculoEnhanced) iter.next();
                pm.makeTransient(uehn.getCirculo());
            }
            pm.makeTransientAll(br.getUsuarioCirculos());
            pm.makeTransient(br);

        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return (gov.sir.core.negocio.modelo.Usuario) br.toTransferObject();
    }

    /**
     * Obtiene un objeto <code>EstacionRecibo</code> estaci�n recibo dado su
     * identificador.
     * <p>
     * M�todo utilizado en transacciones.
     *
     * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
     * recuperada.
     * @param pm <code>PersistenceManager</code> de la transacci�n.
     * @return La <code>EstacionRecibo</code> asociada con el identificador
     * pasado como par�metro.
     * @throws <code>DAOException</code>
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    protected EstacionReciboEnhanced getEstacionReciboEnhanced(
            EstacionReciboEnhancedPk oid, PersistenceManager pm)
            throws DAOException {
        EstacionReciboEnhanced rta = null;

        if (oid.idEstacion != null) {
            try {
                rta = (EstacionReciboEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getRecibosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public EstacionRecibo getRecibosEstacion(Estacion estacion) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EstacionReciboEnhanced aux = null;
        EstacionRecibo rta = null;

        try {
            EstacionReciboEnhancedPk idEstacion = new EstacionReciboEnhancedPk();
            idEstacion.idEstacion = estacion.getEstacionId();
            aux = this.getEstacionReciboEnhanced(idEstacion, pm);
            pm.makeTransient(aux);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (aux == null) {
            rta = (EstacionRecibo) ((new EstacionReciboEnhanced()).toTransferObject());
        } else {
            rta = (EstacionRecibo) aux.toTransferObject();
        }

        return rta;
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getRolesEstacion(Estacion estacion) throws ErrorConexionException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            List roles = fact.getRolDAO().obtenerRolesEstacion(estacion);
            return roles;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            return new Vector();
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#loadRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public void loadRolesEstacion(Estacion estacion) throws ErrorConexionException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            List roles = fact.getRolDAO().obtenerRolesEstacion(estacion);
            Iterator it = roles.iterator();
            while (it.hasNext()) {
                RelRolEstacion rre = new RelRolEstacion();
                Rol rol = (Rol) it.next();

                rre.setEstacion(estacion);
                rre.setRol(rol);

                estacion.addRelRolEstacion(rre);
            }
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#loadUsuariosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getUsuariosEstacion(Estacion estacion) throws ErrorConexionException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            List ussAur = fact.getUsuarioDAO().obtenerUsuariosEstacion(estacion);
            List ussSir = new Vector();
            Iterator it = ussAur.iterator();
            while (it.hasNext()) {
                org.auriga.core.modelo.transferObjects.Usuario usAur;
                gov.sir.core.negocio.modelo.Usuario usSir;
                usAur = (org.auriga.core.modelo.transferObjects.Usuario) it.next();
                try {
                    usSir = this.getUsuario(usAur);
                    ussSir.add(usSir);
                } catch (DAOException e) {
                }
            }
            return ussSir;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            return new Vector();
        } catch (Throwable e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            return new Vector();
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getUsuariosPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getUsuariosPotencialesEstacion(Estacion estacion) throws ErrorConexionException {

        try {
            Circulo circulo = darCirculoEstacion(estacion);
            DAOFactory fact = DAOFactory.getFactory();
            List ussAur = fact.getUsuarioDAO().obtenerUsuariosPotencialesEstacion(estacion);
            List ussSir = new Vector();
            Iterator it = ussAur.iterator();
            while (it.hasNext()) {
                org.auriga.core.modelo.transferObjects.Usuario usAur;
                gov.sir.core.negocio.modelo.Usuario usSir;
                usAur = (org.auriga.core.modelo.transferObjects.Usuario) it.next();
                try {
                    usSir = this.getUsuario(usAur);
                    if (isUsuarioInCirculo(usSir, circulo)) {
                        ussSir.add(usSir);
                    }
                } catch (DAOException e) {
                }
            }
            return ussSir;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            return new Vector();
        } catch (Throwable e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            return new Vector();
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getRolesPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getRolesPotencialesEstacion(Estacion estacion) throws ErrorConexionException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            List roles = fact.getRolDAO().obtenerRolesPotencialesEstacion(estacion);
            return roles;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            return new Vector();
        }

    }

    /**
     * Indica si el usuario est� en el c�rculo especificado
     *
     * @param usuario
     * @param circulo
     * @return
     * @throws DAOException
     */
    private boolean isUsuarioInCirculo(gov.sir.core.negocio.modelo.Usuario usuario, Circulo circulo)
            throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        boolean rta = false;
        try {
            Long idUsu = new Long(usuario.getIdUsuario());
            String idCir = circulo.getIdCirculo();

            Query query = pm.newQuery(UsuarioCirculoEnhanced.class);
            query.declareParameters("Long idUsu, String idCir");
            query.setFilter("this.idCirculo == idCir && this.idUsuario == idUsu");
            Collection col = (Collection) query.execute(idUsu, idCir);
            Iterator iter = col.iterator();
            if (iter.hasNext()) {
                rta = true;
            }
            query.closeAll();
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return rta;
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#addRolEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol, Usuario usuario)
     */
    public void addRolEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioAuriga = usuario.getUsuarioAuriga();
            fact.getEstacionDAO().addRolEstacion(estacion, rol, usuarioAuriga);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            //throw new DAOException(	"No se pudo a�adir el rol " + rol.getRolId() + " " +
            //						"a la estaci�n " + estacion.getEstacionId() + " " +
            //						"\n"+e.getMessage(),e);
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#removeRolEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public void removeRolEstacion(Estacion estacion, Rol rol) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            fact.getEstacionDAO().removeRolEstacion(estacion, rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo eliminar el rol " + rol.getRolId() + " "
                    + "a la estaci�n " + estacion.getEstacionId() + " "
                    + "\n" + e.getMessage(), e);
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#addRolUsuarioEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol,
     * gov.sir.core.negocio.modelo.Usuario)
     */
    public void addRolUsuarioEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        try {
            org.auriga.core.modelo.transferObjects.Usuario usAur
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usAur.setUsuarioId(usuario.getUsername());
            DAOFactory fact = DAOFactory.getFactory();
            fact.getEstacionDAO().removeUsuarioEstacion(estacion, usAur, rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo eliminar el rol " + rol.getRolId() + " "
                    + "a la estaci�n " + estacion.getEstacionId() + " "
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#removeRolUsuarioEstacion(org.auriga.core.modelo.transferObjects.Estacion,
     * org.auriga.core.modelo.transferObjects.Rol,
     * gov.sir.core.negocio.modelo.Usuario)
     */
    public void removeRolUsuarioEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        try {
            org.auriga.core.modelo.transferObjects.Usuario usAur
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usAur.setUsuarioId(usuario.getUsername());
            DAOFactory fact = DAOFactory.getFactory();
            fact.getEstacionDAO().removeUsuarioEstacion(estacion, usAur, rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo eliminar el rol " + rol.getRolId() + " "
                    + "a la estaci�n " + estacion.getEstacionId() + " "
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public Estacion getEstacion(Usuario usuario, Rol rol) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usAur
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usAur.setUsuarioId(usuario.getUsername());
            return fact.getEstacionDAO().getEstacion(usAur, rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo consultar la estacion", e);
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstaciones(Usuario usuario, Rol rol) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usAur
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usAur.setUsuarioId(usuario.getUsername());
            return fact.getEstacionDAO().obtenerEstacionesUsuario(usAur, rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("Imposible consultar las estaciones", e);
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacionesUsuarioByEstadoRol(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol, boolean)
     */
    public List getEstacionesUsuarioByEstadoRol(Usuario usuario, Rol rol, boolean estado) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usAur
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usAur.setUsuarioId(usuario.getUsername());
            return fact.getEstacionDAO().obtenerEstacionesUsuarioByEstadoRol(usAur, rol, estado);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("Imposible consultar las estaciones activas ", e);
        }

    }

    /**
     * @see gov.sir.fenrir.dao.FenrirDAO#obtenerEstacionesUsuarioByEstadoRol(Rol
     * rol, Circulo circulo, boolean activo)
     */
    public List obtenerEstacionesUsuarioByEstadoRol(Rol rol, Circulo circulo, boolean activo) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            NivelDAO nivelDao = fact.getNivelDAO();
            Nivel nivel = (Nivel) nivelDao.consultarNivelByCirculo(circulo.getIdCirculo());
            return fact.getEstacionDAO().obtenerEstacionesActivasRolByNivel(rol, nivel);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener la lista de estaciones del rol por nivel" + rol.getRolId()
                    + "\n" + e.getMessage(), e);
        }

    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacion(gov.sir.core.negocio.modelo.Usuario,
     * org.auriga.core.modelo.transferObjects.Rol)
     */
    public Estacion getEstacionByEstado(Usuario usuario, Rol rol, boolean estado) throws DAOException {

        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usAur
                    = new org.auriga.core.modelo.transferObjects.Usuario();
            usAur.setUsuarioId(usuario.getUsername());
            return fact.getEstacionDAO().getEstacionByEstado(usAur, rol, estado);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo consultar la estacion", e);
        }

    }

    /**
     * @see gov.sir.fenrir.dao.FenrirDAO#getRolByID(java.lang.String)
     */
    public Rol getRolByID(String idRol) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            Rol rol = fact.getRolDAO().consultarRol(idRol);
            return rol;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo consultar el rol " + idRol
                    + "\n" + e.getMessage(), e);
        }
    }

    public void actualizarRol(Rol rol, Usuario usuario) throws DAOException {
        try {
            org.auriga.core.modelo.transferObjects.Usuario usuarioTO = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioTO = usuario.getUsuarioAuriga();
            DAOFactory fact = DAOFactory.getFactory();
            fact.getRolDAO().actualizarRol(rol, usuarioTO);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo actualizar el rol " + rol.getRolId()
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see gov.sir.fenrir.dao.FenrirDAO#getUsuarioByUsername(java.lang.String)
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioByUsername(String username) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuario = fact.getUsuarioDAO().consultarUsuario(username);
            List usuRols = usuario.getRelUsuRols();
            Iterator itUsuRol = usuRols.iterator();
            while (itUsuRol.hasNext()) {
                RelUsuRol usuRol = (RelUsuRol) itUsuRol.next();
                Rol rol = new Rol();
                rol.setRolId(usuRol.getRolId());
                List estaciones = fact.getEstacionDAO().obtenerEstacionesUsuario(usuario, rol);
                Iterator itEstaciones = estaciones.iterator();
                while (itEstaciones.hasNext()) {
                    Estacion estacion = (Estacion) itEstaciones.next();
                    RelUsuRolEst rel = new RelUsuRolEst();
                    rel.setEstacionId(estacion.getEstacionId());
                    rel.setRolId(rol.getRolId());
                    rel.setUsuarioId(usuario.getUsuarioId());
                    RelRolEstacion relRolEst = new RelRolEstacion();
                    relRolEst.setRol(usuRol.getRol());
                    relRolEst.setEstacion(estacion);
                    rel.setRelRolEstacion(relRolEst);
                    usuRol.addRelUsuRolEst(rel);
                }
            }
            return usuario;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener el usuario " + username
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesRol(Rol rol) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            return fact.getEstacionDAO().obtenerEstacionesRol(rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener la lista de estaciones del rol" + rol.getRolId()
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesCirculo(Circulo circulo) throws DAOException {
        Nivel nivel = null;
        try {
            DAOFactory fact = DAOFactory.getFactory();
            NivelDAO nivelDao = fact.getNivelDAO();
            nivel = (Nivel) nivelDao.consultarNivelByCirculo(circulo.getIdCirculo());
            return fact.getEstacionDAO().obtenerEstacionesNivel(nivel);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener la lista de estaciones del nivel" + nivel.getNivelId()
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesRolByCirculo(Rol rol, Circulo circulo) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            NivelDAO nivelDao = fact.getNivelDAO();
            Nivel nivel = (Nivel) nivelDao.consultarNivelByCirculo(circulo.getIdCirculo());
            return fact.getEstacionDAO().obtenerEstacionesRolByNivel(rol, nivel);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener la lista de estaciones del rol por nivel" + rol.getRolId()
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#getEstacionesRol(org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesActivasRolByCirculo(Rol rol, Circulo circulo) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            NivelDAO nivelDao = fact.getNivelDAO();
            Nivel nivel = (Nivel) nivelDao.consultarNivelByCirculo(circulo.getIdCirculo());
            return fact.getEstacionDAO().obtenerEstacionesActivasRolByNivel(rol, nivel);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener la lista de estaciones del rol por nivel" + rol.getRolId()
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#agregarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Usuario usuario)
     */
    public void agregarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioAuriga = usuarioSistema.getUsuarioAuriga();
            fact.getRolDAO().agregarUsuarioRolEstacion(usuario, rol, estacion, usuarioAuriga);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo agregar la estacion al usuario \n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#eliminarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * List turnos)
     */
    public void eliminarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            fact.getRolDAO().eliminarUsuarioRolEstacion(usuario, rol, estacion);

        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo eliminar la estacion al usuario \n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#eliminarEstacionRolUsuario(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario)
     */
    public void actualizarEstadoEstacionRol(Estacion estacion, Rol rol, Circulo circulo, boolean estado, Usuario usuario) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioAuriga = usuario.getUsuarioAuriga();
            fact.getEstacionDAO().actualizarEstadoEstacionRol(estacion, rol, estado, usuarioAuriga);

        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo actualizar la estacion al usuario \n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#agregarRolUsuario(org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Usuario,
     * Usuario usuario)
     */
    public void agregarRolUsuario(Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioAuriga = usuarioSistema.getUsuarioAuriga();
            fact.getRolDAO().agregarRolUsuario(rol, usuario, usuarioAuriga);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo agregar la estacion al rol \n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#agregarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol)
     */
    public void agregarEstacionRol(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
            usuarioAuriga = usuario.getUsuarioAuriga();
            fact.getEstacionDAO().addRolEstacion(estacion, rol, usuarioAuriga);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo agregar la estacion al rol \n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#eliminarEstacionRol(org.auriga.core.modelo.transferObjects.Estacion,org.auriga.core.modelo.transferObjects.Rol)
     */
    public void eliminarEstacionRol(Estacion estacion, Rol rol) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            fact.getEstacionDAO().removeRolEstacion(estacion, rol);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo eliminar la estacion del rol \n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#consultarUsuariosRolEstacion(org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List consultarUsuariosRolEstacion(Rol rol, Estacion estacion) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            return fact.getUsuarioDAO().obtenerUsuarioRolEstacion(rol, estacion);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo consultar los usuarios \n" + e.getMessage(), e);
        }
    }

    /**
     * @see gov.sir.fenrir.dao.FenrirDAO#getUsuarioByUsername(java.lang.String)
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioNRelationsByUsername(String username) throws DAOException {
        try {
            DAOFactory fact = DAOFactory.getFactory();
            org.auriga.core.modelo.transferObjects.Usuario usuario = fact.getUsuarioDAO().consultarUsuarioConRelaciones(username);
            List usuRols = usuario.getRelUsuRols();
            Iterator itUsuRol = usuRols.iterator();
            while (itUsuRol.hasNext()) {
                RelUsuRol usuRol = (RelUsuRol) itUsuRol.next();
                Rol rol = new Rol();
                rol.setRolId(usuRol.getRolId());
                List estaciones = fact.getEstacionDAO().obtenerEstacionesUsuario(usuario, rol);
                Iterator itEstaciones = estaciones.iterator();
                while (itEstaciones.hasNext()) {
                    Estacion estacion = (Estacion) itEstaciones.next();
                    RelUsuRolEst rel = new RelUsuRolEst();
                    rel.setEstacionId(estacion.getEstacionId());
                    rel.setRolId(rol.getRolId());
                    rel.setUsuarioId(usuario.getUsuarioId());
                    RelRolEstacion relRolEst = new RelRolEstacion();
                    relRolEst.setRol(usuRol.getRol());
                    relRolEst.setEstacion(estacion);
                    relRolEst.setActiva(fact.getRolDAO().estadoRelRolEstacion(rol, estacion));
                    rel.setRelRolEstacion(relRolEst);
                    usuRol.addRelUsuRolEst(rel);
                }
            }
            return usuario;
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException("No se pudo obtener el usuario " + username
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#consultarTurnosRolEstacion(org.auriga.core.modelo.transferObjects.Rol,org.auriga.core.modelo.transferObjects.Estacion)
     */
    /*public List consultarTurnosRolEstacion(Rol rol, Estacion estacion) throws DAOException {
		List listaTurnos = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VersantPersistenceManager jdoPM = null;

		String consulta = "SELECT distinct(a.ITEM_KEY) " +
		"FROM auriga.sas_registro_ejecucion A , SIR_OP_PROCESOS_V B "+
		"WHERE FASE = 'EN EJECUCION' AND ESTADO = 'ESPERA EJECUCION' "+
		"AND A.ADMINISTRADOR_ID = ? "+
		"AND A.SOLICITUD_ID = B.ID_FASE "+
		"AND B.ROL = ? ";
		

		try {
			if (estacion == null || estacion.getEstacionId() == null) {
				throw new DAOException("Debe proporcionar un identificador de la estaci�n");
			}
			
			if (rol == null || rol.getRolId() == null) {
				throw new DAOException("Debe proporcionar un identificador de Rol");
			}
			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);

			ps = connection.prepareStatement(consulta);
			ps.setString(1, estacion.getEstacionId());
			ps.setString(2, rol.getRolId());
			rs = ps.executeQuery();
		
			while (rs.next()) {
				listaTurnos.add(rs.getString(1));
			}
			rs.close();
			ps.close();

			jdoPM.currentTransaction().commit();
		} catch (SQLException e) {
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

				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				Log.getInstance().error(this.getClass(), e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}
		
		return listaTurnos;
	}*/
    /**
     * Crea un usuario en el sistema
     *
     * @param usuario el <code>Usuario</code> a ser creado
     * @param roles  <code>List</code> con la lista de roles asignados al usuario
     * @param responsabilidad  <code>Responsabilidad</code> que puede tener el
     * usuario en el sistema
     * @return boolean <code>true</code> si pudo crear satisfactoriamente el
     * usuario
     * @throws DAOException
     */
    public boolean crearUsuario(
            gov.sir.core.negocio.modelo.Usuario usuario,
            List roles, Usuario usuarioSistema)
            throws DAOException {
        boolean resp;
        PersistenceManager pm = null;
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            resp = this.crearUsuario(usuario, roles, pm, usuarioSistema);
            pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return resp;
    }

    /**
     * Crea un usuario en el sistema
     *
     * @param usuario el <code>Usuario</code> a ser creado
     * @param roles  <code>List</code> con la lista de roles asignados al usuario
     * @param responsabilidad  <code>Responsabilidad</code> que puede tener el
     * usuario en el sistema
     * @param usuarioSistema  <code>Usuario</code> que adiciona el usuario
     * @return boolean <code>true</code> si pudo crear satisfactoriamente el
     * usuario
     * @throws DAOException
     */
    public boolean crearUsuario(
            gov.sir.core.negocio.modelo.Usuario usuario,
            List roles, PersistenceManager pm, Usuario usuarioSistema)
            throws DAOException {
        VersantPersistenceManager jdoPM = null;

        boolean creacionUsuario = false;

        DAOFactory fact = null;
        UsuarioDAO udao = null;
        EstacionDAO edao = null;
        org.auriga.core.modelo.transferObjects.Usuario usrAuriga = null;
        org.auriga.core.modelo.transferObjects.Usuario usrAurigaSistema = null;
        usrAurigaSistema = usuarioSistema.getUsuarioAuriga();
        Estacion estacion = null;

        try {

            fact = DAOFactory.getFactory();

            jdoPM = (VersantPersistenceManager) pm;

            //GENERACI�N DEL N�MERO DE SECUENCIA PARA EL USUARIO
            long idUsuario = getSecuencial("SIR_OP_USUARIO", pm);
            usuario.setIdUsuario(idUsuario);

            //////////////////////////////////////////////////////////////
            //CREAR Usuario EN SIR
            UsuarioEnhanced usuarioEnh = UsuarioEnhanced.enhance(usuario);

            //VALIDAR SI YA EXISTE UN USUARIO CON ESE ID
            UsuarioEnhancedPk usuarioId = new UsuarioEnhancedPk();
            usuarioId.idUsuario = usuarioEnh.getIdUsuario();
            UsuarioEnhanced valid = getUsuarioByID(usuarioId, pm);
            if (valid != null) {
                throw new DAOException("Ya existe un usuario con el id " + usuarioEnh.getIdUsuario());
            }

            org.auriga.core.modelo.transferObjects.Usuario usuarioExistente
                    = getUsuarioByUsername(usuario.getUsername());
            if (usuarioExistente != null && usuarioExistente.getUsuarioId() != null && usuarioExistente.getNombre() != null) {
                throw new DAOException("Ya existe un usuario con el nombre de usuario " + usuario.getUsername());
            }

            CirculoEnhancedPk idCirculo = null;
            List usuarioCirculos = usuarioEnh.getUsuarioCirculos();
            if (usuarioCirculos != null) {
                for (Iterator iter = usuarioCirculos.iterator(); iter.hasNext();) {
                    UsuarioCirculoEnhanced ucirEnh = (UsuarioCirculoEnhanced) iter.next();

                    if (ucirEnh.getCirculo() == null) {
                        throw new DAOException("Debe proporcionar un c�rculo.");
                    }

                    CirculoEnhancedPk cid = new CirculoEnhancedPk();
                    cid.idCirculo = ucirEnh.getCirculo().getIdCirculo();
                    // Para la consulta del Nivel del c�rculo
                    idCirculo = cid;
                    //
                    CirculoEnhanced circuloEnh = null;

                    try {
                        circuloEnh = (CirculoEnhanced) pm.getObjectById(cid, true);
                    } catch (JDOObjectNotFoundException e) {
                        Log.getInstance().debug(this.getClass(), e);
                        Log.getInstance().error(this.getClass(), e.getMessage(), e);
                        circuloEnh = null;
                    } catch (JDOException e) {
                        Log.getInstance().debug(this.getClass(), e);
                        Log.getInstance().error(this.getClass(), e.getMessage(), e);
                        circuloEnh = null;
                    }

                    if (circuloEnh == null) {
                        throw new DAOException("No se encontr� el c�rculo con el id " + cid.idCirculo);
                    }

                    ucirEnh.setIdUsuario(usuarioEnh.getIdUsuario());
                    ucirEnh.setIdCirculo(circuloEnh.getIdCirculo());
                    ucirEnh.setCirculo(circuloEnh);
                }
            } else {
                throw new DAOException("Debe proporcionar un c�rculo.");
            }

            pm.makePersistent(usuarioEnh);
            ////////////////////////////////////////////////////////////
            //			Estaci�n auriga
            edao = fact.getEstacionDAO();
            estacion = new Estacion();
            estacion.setDescripcion("Estacion " + usuario.getUsername());
            estacion.setEstacionId("X-" + usuario.getUsername());
            estacion.setNombre("X-" + usuario.getUsername());

            //		ESTACION NIVEL AURIGA
            Nivel nivel = getNivelDeCirculo(idCirculo.idCirculo, jdoPM, fact);
            /*if (nivel==null){
				Nivel niv=new Nivel();
				fact.getNivelDAO().crearNivel(nivel);
			}*/
            if (nivel != null) {
                RelEstacionNivel relEstNiv = new RelEstacionNivel();
                relEstNiv.setNivel(nivel);
                relEstNiv.setEstacion(estacion);
                relEstNiv.setEstacionId(estacion.getEstacionId());
                estacion.addRelEstacionNivel(relEstNiv);
            }

            for (Iterator iter = roles.iterator(); iter.hasNext();) {
                Rol rol = (Rol) iter.next();
                RelRolEstacion relRolEstacion = new RelRolEstacion();
                relRolEstacion.setEstacion(estacion);
                relRolEstacion.setRol(rol);
                estacion.addRelRolEstacion(relRolEstacion);
            }

            edao.crearEstacion(estacion, usrAurigaSistema);

            ////////////////////////////////////////////////////////////
            //Usuario auriga
            udao = fact.getUsuarioDAO();
            usrAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
            usrAuriga.setNombre(usuario.getNombre());
            usrAuriga.setUsuarioId(usuario.getLoginID());

            //	Roles del Usuario
            for (Iterator iter = roles.iterator(); iter.hasNext();) {
                Rol rol = (Rol) iter.next();
                RelUsuRol relUsuRol = new RelUsuRol();
                relUsuRol.setRol(rol);
                relUsuRol.setUsuario(usrAuriga);
                relUsuRol.setUsuarioId(usrAuriga.getUsuarioId());

                RelUsuRolEst relUsuRolEst = new RelUsuRolEst();
                relUsuRolEst.setEstacionId(estacion.getEstacionId());
                relUsuRolEst.setRelUsuRol(relUsuRol);
                relUsuRolEst.setUsuarioId(usrAuriga.getUsuarioId());
                relUsuRolEst.setRolId(rol.getRolId());

                relUsuRol.addRelUsuRolEst(relUsuRolEst);
                usrAuriga.addRelUsuRol(relUsuRol);
            }

            udao.crearUsuario(usrAuriga, usrAurigaSistema);
            ////////////////////////////////////////////////////////////

            creacionUsuario = true;
        } catch (DAOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            if (edao != null && estacion != null) {
                try {
                    edao.eliminarEstacion(estacion);
                } catch (org.auriga.core.modelo.daoObjects.DAOException e2) {
                    Log.getInstance().error(this.getClass(), e2.getMessage(), e2);
                }
            }

            if (udao != null && usrAuriga != null) {
                try {
                    udao.eliminarUsuario(usrAuriga);
                } catch (org.auriga.core.modelo.daoObjects.DAOException e2) {
                    Log.getInstance().error(this.getClass(), e2.getMessage(), e2);
                }
            }
            throw new DAOException(e.getMessage(), e);
        }

        return creacionUsuario;
    }

    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo) throws DAOException {
        PersistenceManager pm = null;

        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            Log.getInstance().info(this.getClass(), "Transaccion iniciada");
            this.trasladarUsuarioCirculo(usuario, circulo, pm);
            pm.currentTransaction().commit();
            Log.getInstance().info(this.getClass(), "Commit ejecutado");
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), "Objeto no encontrado");
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error cambiando usuario de circulo:" + printer.toString());
            throw new DAOException("No se pudo hacer la conexion con JDO", e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(),
                    "Error en la configuracion de las propiedades de JDO:" + printer.toString());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            ExceptionPrinter printer = new ExceptionPrinter(t);
            Log.getInstance().error(this.getClass(), "Error inesperado:" + printer.toString());
            throw new DAOException("Error inesperado", t);
        } finally {
            pm.close();
            Log.getInstance().info(this.getClass(), "Cerrado el PersistenceManager de Fenrir");
        }
    }

    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo, PersistenceManager pm) throws DAOException {

        try {
            Query query = pm.newQuery(UsuarioCirculoEnhanced.class);
            query.declareParameters("Long usuario");
            query.setFilter("idUsuario == usuario");

            Collection col = (Collection) query.execute(new Long(usuario.getIdUsuario()));

            pm.deletePersistentAll(col);

            if (pm.currentTransaction().isActive()) {
                VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
                pm2.flush();
            }

            CirculoEnhancedPk oidCirc = new CirculoEnhancedPk();
            oidCirc.idCirculo = circulo.getIdCirculo();
            CirculoEnhanced circEnh = (CirculoEnhanced) pm.getObjectById(oidCirc, true);

            UsuarioEnhancedPk oidUsu = new UsuarioEnhancedPk();
            oidUsu.idUsuario = usuario.getIdUsuario();
            UsuarioEnhanced usuEnh = (UsuarioEnhanced) pm.getObjectById(oidUsu, true);

            UsuarioCirculoEnhanced usuCirc = new UsuarioCirculoEnhanced();
            usuCirc.setCirculo(circEnh);
            usuCirc.setUsuario(usuEnh);
            usuCirc.setIdCirculo(circEnh.getIdCirculo());
            usuCirc.setIdUsuario(usuEnh.getIdUsuario());

            pm.makePersistent(usuCirc);

            //cambiar la estacion privada del usuario de nivel
            NivelDAO nivelDao = DAOFactory.getFactory().getNivelDAO();
            Nivel nivel = nivelDao.consultarNivelByCirculo(circulo.getIdCirculo());

            if (nivel != null && nivel.getNivelId() != null) {
                EstacionDAO estacionDao = DAOFactory.getFactory().getEstacionDAO();

                org.auriga.core.modelo.transferObjects.Usuario usuAuriga = new org.auriga.core.modelo.transferObjects.Usuario();
                usuAuriga.setUsuarioId(usuario.getUsername());

                List estaciones = estacionDao.obtenerEstacionesUsuario(usuAuriga);
                Iterator itEstaciones = estaciones.iterator();
                Estacion privada = null;
                while (itEstaciones.hasNext() && privada == null) {
                    Estacion estacion = (Estacion) itEstaciones.next();
                    if (estacion.getEstacionId().indexOf(usuario.getUsername()) != -1) {
                        privada = estacion;
                    }
                }

                estacionDao.cambiarEstacionNivel(privada, nivel);
            }

        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            throw new DAOException(e.getMessage(), e);
        }

    }

    /**
     * Author: Santiago V�squez Change: 1156.111.USUARIOS.ROLES.INACTIVOS
     * Validaci�n de roles no configurados
     */
    /**
     * Debe existir por lo menos una estaci�n activa del respectivo rol en el
     * c�rculo
     *
     * @param circulo
     * @param rol
     * @param estacion
     * @return true existe por lo menos una estaci�n activa del respectivo rol
     * en el c�rculo
     * @throws DAOException
     */
    @Override
    public boolean hayEstacionAsociadoARolPorCirculo(Circulo circulo, Rol rol, Estacion estacion) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PersistenceManager pm = null;
        Transaction tx = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ")
                .append("FROM  AURIGA.CMN_JERARQUIA CJ, ")
                .append("      AURIGA.CMN_NIVEL CN, ")
                .append("      AURIGA.CMN_REL_ESTACION_NIVEL CREN, ")
                .append("      AURIGA.CMN_REL_ROL_ESTACION CRRE ")
                .append("WHERE CJ.jerarquia_id = ? ")
                .append("      AND CJ.nivel_inicio = CN.nivel_superior_id ")
                .append("      AND CN.nivel_id = CREN.nivel_id ")
                .append("      AND CREN.estacion_id = CRRE.estacion_id ")
                .append("      AND CN.atributo1 = ? ")
                .append("      AND CRRE.rol_id = ? ")
                .append("      AND CREN.estacion_id != ? ")
                .append("      AND CRRE.ACTIVA = 1 ");

        try {
            pm = AdministradorPM.getPM();
            tx = pm.currentTransaction();
            tx.setOptimistic(false);
            tx.begin();
            connection = ((VersantPersistenceManager) pm).getJdbcConnection(null);

            ps = connection.prepareStatement(sql.toString());
            ps.setString(1, CJerarquia.JER_CIRCULOS);
            ps.setString(2, circulo.getIdCirculo());
            ps.setString(3, rol.getRolId());
            ps.setString(4, estacion.getEstacionId());
            rs = ps.executeQuery();
            if (rs.next()) {
                int estacionesActivas = rs.getInt(1);
                if (estacionesActivas != 0) {
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (connection != null) {
                    if (ps != null) {
                        ps.close();
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                Log.getInstance().error(this.getClass(), e);
            }

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            if (pm != null && !pm.isClosed()) {
                pm.close();
            }
        }
        return false;
    }

    /**
     * Consulta la lista de campos de captura para una forma de pago
     *
     * @param TipoPago  <code>TipoPago</code> a ser consultado
     * @return lista de campos de captura
     * @throws DAOException
     */
    public List consultarCamposCaptura(TipoPago tipoPago) throws DAOException {
        List campoCapturaEstadosList = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT SIR_NE_CAMPOS_CAPTURA.CC_ID, SIR_NE_CAMPOS_CAPTURA.CC_NOMBRE_CAMPO, SIR_OP_REL_FPAGO_CAMPOS.FPCC_ID, SIR_OP_REL_FPAGO_CAMPOS.FPCC_ESTADO "
                + "FROM SIR_NE_CAMPOS_CAPTURA "
                + "LEFT JOIN SIR_OP_REL_FPAGO_CAMPOS "
                + "ON SIR_OP_REL_FPAGO_CAMPOS.FPCC_CC_ID = SIR_NE_CAMPOS_CAPTURA.CC_ID "
                + "AND SIR_OP_REL_FPAGO_CAMPOS.FPCC_FP_ID = ? ";

        try {
            if (tipoPago == null) {
                throw new DAOException("Debe proporcionar un identificador de Tipo de Pago");
            }

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);

            ps.setString(1, "" + tipoPago.getIdTipoDocPago());

            rs = ps.executeQuery();

            while (rs.next()) {
                CamposCapturaEstados camposCapturaEstados = new CamposCapturaEstados();
                camposCapturaEstados.setIdRelFormaCampo(rs.getInt(3));
                camposCapturaEstados.setIdCampo(rs.getInt(1));
                camposCapturaEstados.setNombreCampo(rs.getString(2));
                camposCapturaEstados.setEstado(rs.getInt(4));
                campoCapturaEstadosList.add(camposCapturaEstados);
            }
            rs.close();
            ps.close();

            /*for (Iterator iter = ids.iterator(); iter.hasNext();) {
                //UsuarioEnhanced usuarioEnh = getUsuarioByID((UsuarioEnhanced.ID)iter.next(), jdoPM);
                UsuarioEnhanced usuarioEnh
                        = (UsuarioEnhanced) jdoPM.getObjectById((UsuarioEnhancedPk) iter.next(), true);
                jdoPM.makeTransientAll(usuarioEnh.getSubtiposAtencions());
                jdoPM.makeTransient(usuarioEnh);
                listaUsuariosEnh.add(usuarioEnh);
            }*/
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

        //listaUsuariosEnh = TransferUtils.makeTransferAll(listaUsuariosEnh);
        return campoCapturaEstadosList;
    }

    /**
     * Actualiza la realacion campos captura y forma pago en la tabla
     * SIR_OP_REL_FPAGO_CAMPOS
     *
     * @param formaPagoCampos  <code>FormaPagoCampos</code> a ser actualizado
     * @return true o false dependiendo el estado de la operaci�n
     * @throws DAOException
     */
    public boolean actualizarCamposCaptura(FormaPagoCampos formaPagoCampos) throws DAOException {
       
        VersantPersistenceManager jdoPM = null;
        boolean actualizado = false;
        FormaPagoCamposEnhanced formaPagoCamposEnh = FormaPagoCamposEnhanced.enhance(null);
        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            
            if (formaPagoCampos.getIdFormaPagoCampos() > 0) {
                FormaPagoCamposEnhancedPk fpid = new FormaPagoCamposEnhancedPk();
                fpid.idFormaPagoCampos = formaPagoCampos.getIdFormaPagoCampos();

                formaPagoCamposEnh = getFormaPagoCampoByID(fpid, jdoPM);
                if (formaPagoCamposEnh == null) {
                    throw new DAOException("No se encontr� la relacion campos captura y forma pago con el id " + fpid.idFormaPagoCampos);
                }

                formaPagoCamposEnh.setIdFormaPago(formaPagoCampos.getIdFormaPago());
                formaPagoCamposEnh.setIdCamposCaptura(formaPagoCampos.getIdCamposCaptura());
                formaPagoCamposEnh.setEstado(formaPagoCampos.isEstado());

            } else {
                //Generaci�n del n�mero de secuencia para el nuevo registro
                long idFormaPagoCampos = getSecuencial("SIR_OP_REL_FPAGO_CAMPOS", null);
                formaPagoCampos.setIdFormaPagoCampos((int) idFormaPagoCampos);
                formaPagoCamposEnh = FormaPagoCamposEnhanced.enhance(formaPagoCampos);

            }

            jdoPM.makePersistent(formaPagoCamposEnh);

            jdoPM.currentTransaction().commit();
            actualizado = true;
        } catch (NullPointerException e) {
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
            if (jdoPM != null) {
                jdoPM.close();
            }
        }

        return actualizado;
    }

    /**
     * Obtiene un <code>FormaPagoCampos</code> dado su identificador, m�todo
     * utilizado para transacciones
     * <p>
     * En caso de que no se encuentre en la Base de Datos una relacion campo -
     * forma pago con el identificador pasado como par�metro, se retorna
     * <code>null</code>.
     *
     * @param idFormaPagoCampo identificador de la <code>FormaPagoCampos</code>.
     * @param pm PersistenceManager de la transaccion
     * @return <code>FormaPagoCampos</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.FormaPagoCamposEnhanced
     */
    private FormaPagoCamposEnhanced getFormaPagoCampoByID(FormaPagoCamposEnhancedPk idFormaPagoCampo, PersistenceManager pm)
            throws DAOException {
        FormaPagoCamposEnhanced rta = null;

        //if (usuarioId.idUsuario != null) {
        try {
            rta = (FormaPagoCamposEnhanced) pm.getObjectById(idFormaPagoCampo, true);
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().debug(this.getClass(), e);
            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        //}
        return rta;
    }
    
    /**
     * Consulta la lista de campos de captura del sistema
     *
     * @return lista de campos de captura del sistema
     * @throws DAOException
     */
    public List cargarCamposCaptura() throws DAOException {
        List camposCapturaList = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT CC_ID, CC_NOMBRE_CAMPO "
                + "FROM SIR_NE_CAMPOS_CAPTURA ";

        try {
            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);

            rs = ps.executeQuery();

            while (rs.next()) {
                CamposCaptura camposCaptura = new CamposCaptura();
                camposCaptura.setIdCamposCaptura(rs.getInt(1));
                camposCaptura.setNombreCampo(rs.getString(2));
                camposCapturaList.add(camposCaptura);
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

        return camposCapturaList;
    }
    
    /**
     * Crea un nuevo registro de forma de pago
     *
     * @param nombreFormaPago el <code>String</code> a ser creado
     * @throws DAOException
     */
    public TipoPago crearFormaPago(String nombreFormaPago) throws DAOException {
        PersistenceManager pm = null;
        boolean creado = false;
        TipoPago tipoPago = new TipoPago();
        try {
            pm = AdministradorPM.getPM();
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            long idTipoDocPago = getSecuencial("SIR_OP_TIPO_DOC_PAGO", null);
            tipoPago.setIdTipoDocPago(idTipoDocPago);
            tipoPago.setNombre(nombreFormaPago);
            Date fecha = new Date(System.currentTimeMillis());
            tipoPago.setFechaCreacion(fecha);

            TipoPagoEnhanced tipoPagoEnh = TipoPagoEnhanced.enhance(tipoPago);
            
            pm.makePersistent(tipoPagoEnh);
            pm.currentTransaction().commit();
            
            //creado = true;
                    
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return tipoPago;
    }

}
