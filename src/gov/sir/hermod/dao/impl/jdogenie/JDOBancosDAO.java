/*
 * JDOBancosDAO.java
 * Clase para el manejo de los objetos de tipo Banco utilizados en la aplicacion.
 * Provee servicios para la consulta, modificación y adición de estos objetos.
 * Creado julio 12 de 2004, 9:10
 */
package gov.sir.hermod.dao.impl.jdogenie;

import com.versant.core.jdo.VersantPersistenceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoPk;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.BancosXCirculoPk;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.CanalesRecaudoPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoTipoPago;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.CuentasBancariasPk;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.SucursalBancoPk;
import gov.sir.core.negocio.modelo.TipoPago;

import gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancosXCirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.HermodProperties;

import gov.sir.hermod.dao.BancosDAO;
import gov.sir.hermod.dao.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dlopez,mortiz Clase para el manejo de los objetos de tipo
 * <code>Banco</code> utilizados en la aplicacion.<p>
 * <p>
 * Provee servicios para la consulta, modificación y adición de estos objetos.
 * @see gov.sir.core.negocio.modelo.Banco
 */
public class JDOBancosDAO implements BancosDAO {

    /**
     * Crea una nueva instancia de <code>JDOBancosDAO</code>
     * <p>
     * Método Constructor.
     */
    public JDOBancosDAO() {
    }

    /**
     * Obtiene una lista de objetos tipo <code>Banco </code>, organizada
     * ascendentemente según el id del banco.
     *
     * @return una lista de objetos <code>Banco</code>
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public List getBancos() throws DAOException {
        List lista = new ArrayList();

        try {
            PersistenceManager pm = AdministradorPM.getPM();

            Query q = pm.newQuery(BancoEnhanced.class);

            //Se establece como criterio de ordenamiento el identificador
            //del banco. (Orden ascendente).
            q.setOrdering("idBanco ascending");
            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();

            while (it.hasNext()) {
                BancoEnhanced obj = (BancoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            q.close(results);
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Obtiene un <code>Banco</code> dado su identificador.<p>
     * Retorna null, si no se encontró el <code>Banco</code> con el
     * identificador dado.
     *
     * @param bID identificador del banco.
     * @return <code>Banco</code> con todos sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public Banco getBancoByID(BancoPk bID) throws DAOException {
        BancoEnhanced br = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            br = this.getBancoByID(new BancoEnhancedPk(bID), pm);
            pm.makeTransientAll(br.getSucursales());
            pm.makeTransient(br);

        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        return (Banco) br.toTransferObject();
    }

    public Banco getBancoByID(String idBanco) throws DAOException {

        BancoPk banID = new BancoPk();
        banID.idBanco = idBanco;
        Banco banco;

        try {
            banco = this.getBancoByID(banID);
        } catch (DAOException e) {
            throw e;
        }

        return banco;
    }

    /**
     * Obtiene un <code>Banco</code> dado su identificador, método utilizado
     * para transacciones.
     * <p>
     * En caso de que el <code>Banco</code>con el identificador dado no se
     * encuentre en la Base de Datos se retorna <code>null</code>
     *
     * @param bID identificador del <code>Banco</code>
     * @param pm PersistenceManager de la transaccion
     * @return <code>Banco</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Banco
     */
    protected BancoEnhanced getBancoByID(
            BancoEnhancedPk bID,
            PersistenceManager pm)
            throws DAOException {
        BancoEnhanced rta = null;

        if (bID.idBanco != null) {
            try {
                rta = (BancoEnhanced) pm.getObjectById(bID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().debug(JDOBancosDAO.class, e);
                Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Adiciona un <code>Banco<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, un <code>Banco</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param datos objeto <code>Banco</code> con sus atributos, incluido el
     * identificador.
     * @return identificador del Banco generado.
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.Banco
     */
    public BancoPk addBanco(Banco datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        BancoEnhanced banco = BancoEnhanced.enhance(datos);

        try {

            //Validación del identificador del Banco.
            BancoEnhancedPk bancoId = new BancoEnhancedPk();
            bancoId.idBanco = banco.getIdBanco();

            BancoEnhanced valid = this.getBancoByID(bancoId, pm);

            //Se lanza una excepción en el caso en el que ya exista en
            //la base de datos, un banco con el identificador pasado dentro
            //del parámetro. 
            if (valid != null) {
                throw new DAOException(
                        "Ya existe un Banco con el identificador: "
                        + bancoId.idBanco);
            }

            pm.currentTransaction().begin();
            pm.makePersistent(banco);
            pm.currentTransaction().commit();

            BancoEnhancedPk rta = (BancoEnhancedPk) pm.getObjectId(banco);

            return rta.getBancoID();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Adiciona una <code>SucursalBanco</code> a un <code>Banco</code> dentro de
     * la configuración del sistema. Se lanza una excepción en el caso en el que
     * ya exista en la base de datos, una <code>SucursalBanco</code> con el
     * identificador pasado dentro del parámetro.
     *
     * @param sucursal objeto <code>SucursalBanco</code> con sus atributos
     * @return identificador de la <code>SucursalBanco</code> generada.
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    public SucursalBancoPk addSucursal(SucursalBanco sucursal)
            throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        SucursalBancoEnhanced sucBanco
                = SucursalBancoEnhanced.enhance(sucursal);

        try {
            //pm.currentTransaction().setOptimistic(false);

            //Validación del identificador de la sucursal.
            SucursalBancoEnhancedPk sucursalId
                    = new SucursalBancoEnhancedPk();
            sucursalId.idBanco = sucursal.getIdBanco();
            sucursalId.idSucursal = sucursal.getIdSucursal();

            SucursalBancoEnhanced valid
                    = this.getSucursalBancoByID(sucursalId, pm);

            //Se lanza una excepción en el caso en el que ya exista en
            //la base de datos, una sucursal de banco con el identificador pasado dentro
            //del parámetro. 			
            if (valid != null) {
                throw new DAOException(
                        "Ya existe una Sucursal con el identificador: "
                        + sucursalId.idBanco
                        + "-"
                        + sucursalId.idSucursal);
            }

            //Banco bancoSuc = sucursal.getBanco();
            pm.currentTransaction().begin();
            pm.makePersistent(sucBanco);
            pm.currentTransaction().commit();

            SucursalBancoEnhancedPk rta
                    = (SucursalBancoEnhancedPk) pm.getObjectId(sucBanco);

            return rta.getSucursalBancoID();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Obtiene una <code>SucursalBanco</code> dado su identificador, método
     * utilizado para transacciones
     * <p>
     * En caso de que no se encuentre en la Base de Datos una sucursal con el
     * identificador pasado como parámetro, se retorna <code>null</code>.
     *
     * @param sucID identificador de la <code>SucursalBanco</code>.
     * @param pm PersistenceManager de la transaccion
     * @return <code>SucursalBanco</code> con sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    protected SucursalBancoEnhanced getSucursalBancoByID(
            SucursalBancoEnhancedPk sucID,
            PersistenceManager pm)
            throws DAOException {
        SucursalBancoEnhanced rta = null;

        if (sucID.idBanco != null && sucID.idSucursal != null) {
            try {
                rta = (SucursalBancoEnhanced) pm.getObjectById(sucID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().debug(JDOBancosDAO.class, e);
                Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Retorna un listado de las <code>SucursalesBanco</code> asociadas con un
     * <code>Banco</code>.
     * <p>
     * Se establece como criterio de ordenamiento el nombre de la <code>SucursalBanco<p>
     * .
     * @param bancoId identificador del <code>Banco</code> cuyas
     * <code>SucursalesBanco</code> van a ser listadas.
     * @return Lista con las <code>SucursalesBanco</code> asociadas con un
     * <code>Banco</code>.
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    public List getSucursalesBanco(BancoPk bancoId) throws DAOException {
        List lista = new ArrayList();
        String BancoId = bancoId.idBanco;

        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query query = pm.newQuery(SucursalBancoEnhanced.class);
            query.declareParameters("String BancoId");
            query.setFilter("idBanco == BancoId");
            //Se establece como criterio de ordenamiento el nombre de la sucursal.
            query.setOrdering("nombre ascending");
            Collection col = (Collection) query.execute(BancoId);

            Iterator it = col.iterator();

            while (it.hasNext()) {
                SucursalBancoEnhanced obj = (SucursalBancoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            query.close(col);
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        lista = TransferUtils.makeTransferAll(lista);
        return lista;

    }

    /**
     * * Adiciona una relación banco-círculo (SIR_OP_BANCOS_X_CIRCULO)
     */
    public BancosXCirculoPk addBancoCirculo(BancosXCirculo datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        BancosXCirculoEnhanced bancoXCirculo = BancosXCirculoEnhanced.enhance(datos);
        try {
            BancosXCirculoEnhancedPk bancoXCirculoPk = new BancosXCirculoEnhancedPk();
            bancoXCirculoPk.idCirculo = bancoXCirculo.getIdCirculo();
            bancoXCirculoPk.idBanco = bancoXCirculo.getIdBanco();
            BancosXCirculoEnhanced valid = this.getrelacionBancoCirculoByID(bancoXCirculoPk, pm);
            if (valid != null) {
                throw new DAOException(
                        "Ya existe un Banco con el identificador: "
                        + bancoXCirculo.getIdBanco() + " para el círculo " + bancoXCirculo.getIdCirculo());
            }
            pm.currentTransaction().begin();
            pm.makePersistent(bancoXCirculo);
            if (bancoXCirculo.getPrincipal().trim().equals("1")) {
                activarBancoPrincipal(bancoXCirculo.getIdCirculo(), bancoXCirculo.getIdBanco(), pm);
            }
            pm.currentTransaction().commit();
            BancosXCirculoEnhancedPk rta = (BancosXCirculoEnhancedPk) pm.getObjectId(bancoXCirculo);
            return rta.getBancosXCirculoId();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Consulta los bancos configurados para un determinado círculo
     */
    public List getBancosXCirculo(String circulo) throws DAOException {
        List lista = new ArrayList();
        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(BancosXCirculoEnhanced.class);
            q.declareParameters("String circulo");
            q.setFilter("idCirculo==circulo");
            q.setOrdering("idCirculo ascending");
            Collection results = (Collection) q.execute(circulo);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                BancosXCirculoEnhanced obj = (BancosXCirculoEnhanced) it.next();
                pm.makeTransient(obj.getBanco());
                pm.makeTransient(obj);
                lista.add(obj);
            }
            q.close(results);
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Elimina un banco relacionado con un círculo (SIR_OP_BANCOS_X_CIRCULO)
     */
    public boolean eliminaBancoCirculo(BancosXCirculo datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        BancosXCirculoEnhanced bancoXCirculo = BancosXCirculoEnhanced.enhance(datos);
        try {
            pm.currentTransaction().begin();
            BancosXCirculoEnhancedPk idBancoEnh = new BancosXCirculoEnhancedPk();
            idBancoEnh.idBanco = bancoXCirculo.getIdBanco();
            idBancoEnh.idCirculo = bancoXCirculo.getIdCirculo();
            BancosXCirculoEnhanced bancoPers = (BancosXCirculoEnhanced) pm.getObjectById(idBancoEnh, true);
            if (bancoPers == null) {
                throw new DAOException("No existe el Banco con el id " + datos.getIdBanco());
            }
            pm.deletePersistent(bancoPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {

            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        return true;
    }

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     */
    public boolean activarBancoPrincipal(BancosXCirculo datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        pm.currentTransaction().begin();
        if (activarBancoPrincipal(datos.getIdCirculo(), datos.getIdBanco(), pm)) {
            pm.currentTransaction().commit();
            return true;
        }
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        return false;
    }

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     *
     * @param circulo
     * @param banco
     * @param pm
     * @return
     * @throws DAOException
     */
    public boolean activarBancoPrincipal(String circulo, String banco, PersistenceManager pm)
            throws DAOException {
        try {
            Query q = pm.newQuery(BancosXCirculoEnhanced.class);
            q.declareParameters("String circulo");
            q.setFilter("idCirculo==circulo");
            q.setOrdering("idCirculo ascending");
            Collection results = (Collection) q.execute(circulo);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                BancosXCirculoEnhanced obj = (BancosXCirculoEnhanced) it.next();
                if (!obj.getIdBanco().equals(banco)) {
                    obj.setPrincipal("0");
                    pm.makePersistent(obj);
                } else {
                    obj.setPrincipal("1");
                    pm.makePersistent(obj);
                }
            }
            q.close(results);
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        return true;
    }

    /**
     * Consulta relación bancos círculo (SIR_OP_BANCOS_X_CIRCULO) para ser
     * cargada en contexto. Con ésta lista se cargan los bancos en las
     * diferentes modalidades de pago
     */
    public List getRelacionBancosCirculo() throws DAOException {
        List lista = new ArrayList();
        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(BancosXCirculoEnhanced.class);
            q.setOrdering("idCirculo ascending");
            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();
            while (it.hasNext()) {
                BancosXCirculoEnhanced obj = (BancosXCirculoEnhanced) it.next();
                pm.makeTransient(obj.getBanco());
                pm.makeTransient(obj.getCirculo());
                pm.makeTransient(obj);
                lista.add(obj);
            }
            q.close(results);
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Consulta una relacion banco-círculo dado su identificador
     *
     * @param BancosXCirculoEnhancedPk bID
     * @param pm
     * @return
     * @throws DAOException
     */
    protected BancosXCirculoEnhanced getrelacionBancoCirculoByID(BancosXCirculoEnhancedPk bID, PersistenceManager pm)
            throws DAOException {

        BancosXCirculoEnhanced rta = null;
        if (bID.idCirculo != null && bID.idBanco != null) {
            try {
                rta = (BancosXCirculoEnhanced) pm.getObjectById(bID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOBancosDAO.class, e);
                Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * * Adiciona una relación círculo - banco - cuenta
     * (SIR_OP_CUENTAS_BANCARIAS)
     */
    public CuentasBancariasPk addCuentaBancaria(CuentasBancarias datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        CuentasBancariasEnhanced cuentaBancaria = CuentasBancariasEnhanced.enhance(datos);
        try {
            long id = getSecuencial("SIR_OP_CUENTAS_BANCARIAS", null);
            cuentaBancaria.setId((int) id);
            CuentasBancariasEnhancedPk cuentaBancariaPk = new CuentasBancariasEnhancedPk();
            cuentaBancariaPk.id = cuentaBancaria.getId();
            //cuentaBancariaPk.idBanco = cuentaBancaria.getIdBanco();
            //cuentaBancariaPk.nroCuenta = cuentaBancaria.getNroCuenta();

            CuentasBancariasEnhanced valid = this.getCuentaBancariaByID(cuentaBancariaPk, pm);

            if (valid != null) {
                throw new DAOException(
                        "Ya existe una Cuenta Bancaria con número: "
                        + cuentaBancaria.getNroCuenta() + " para el Círculo y Banco seleccionado");
            }
            pm.currentTransaction().begin();
            pm.makePersistent(cuentaBancaria);
            pm.currentTransaction().commit();

            CuentasBancariasEnhancedPk rta = (CuentasBancariasEnhancedPk) pm.getObjectId(cuentaBancaria);

            return rta.getCuentasBancariasID();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
    }

    /**
     * Consulta una relacion (circulo banco cuenta) dado su identificador
     *
     * @param CuentasBancariasEnhancedPk cbId
     * @param pm
     * @return
     * @throws DAOException
     */
    protected CuentasBancariasEnhanced getCuentaBancariaByID(CuentasBancariasEnhancedPk cbId, PersistenceManager pm)
            throws DAOException {

        CuentasBancariasEnhanced rta = null;
        if (cbId.id >= 0) {
            try {
                rta = (CuentasBancariasEnhanced) pm.getObjectById(cbId, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOBancosDAO.class, e);
                Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Consulta las cuentas bancarias para un círculo y banco en específico
     */
    public List getCuentasBancarias(String circulo, String banco) throws DAOException {
        System.out.println("putito 3 circulo: " + circulo + " banco: " + banco);
        List lista = new ArrayList();
        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(CuentasBancariasEnhanced.class);
            q.declareParameters("String circulo, String banco");
            q.setFilter("idCirculo == circulo && idBanco == banco");

            Collection results = (Collection) q.execute(circulo, banco);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                CuentasBancariasEnhanced obj = (CuentasBancariasEnhanced) it.next();
                pm.makeTransient(obj.getBanco());
                pm.makeTransient(obj.getCirculo());
                pm.makeTransient(obj);
                lista.add(obj);
            }
            q.close(results);
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    public void actualizarEstadoCtaBancaria(String circulo, String banco, String cuenta, boolean estado)
            throws DAOException {
        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(CuentasBancariasEnhanced.class);
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            q.declareParameters("String circulo, String banco, String cuenta");
            q.setFilter("idCirculo==circulo && idBanco == banco && nroCuenta == cuenta");
            Collection results = (Collection) q.execute(circulo, banco, cuenta);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                CuentasBancariasEnhanced obj = (CuentasBancariasEnhanced) it.next();
                obj.setActiva(estado);
                pm.makePersistent(obj);
                pm.currentTransaction().commit();
            }
            q.close(results);
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene una secuencia dado su identificador, método utilizado para
     * transacciones TOMADO de FORSETTI
     *
     * @param sID identificador de la secuencia
     * @param pm PersistenceManager de la transaccion
     * @return Secuencia con sus atributos
     * @throws DAOException
     */
    protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhancedPk sID, PersistenceManager pm)
            throws gov.sir.fenrir.dao.DAOException {
        SecuenciasEnhanced rta = null;

        if (sID.tableName != null) {
            try {
                rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new gov.sir.fenrir.dao.DAOException(e.getMessage(), e);
            }
        }
        return rta;
    }

    /**
     * Obtiene y avanza la secuencia de la tabla especificada (TOMADO de
     * FORSETTI)
     *
     * @param tableName Nombre de la tabla de la cual se quiere obtener el
     * secuencial.
     * @param pm Persistence Manager de la transacción.
     * @return El secuencial requerido.
     * @throws DAOException
     */
    protected long getSecuencial(String tableName, PersistenceManager pm) throws gov.sir.fenrir.dao.DAOException {
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
                    pm = gov.sir.fenrir.dao.impl.jdogenie.AdministradorPM.getPM();
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
                        throw new gov.sir.fenrir.dao.DAOException("No se encontró la secuencia");
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
                throw new gov.sir.fenrir.dao.DAOException("No se encontró el registro de la secuencia", e);
            } catch (JDOException e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }                
                throw new gov.sir.fenrir.dao.DAOException("Error obteniendo el registro de la secuencia", e);
            } catch (gov.sir.fenrir.dao.DAOException e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }                
                throw new gov.sir.fenrir.dao.DAOException("Error obteniendo el registro de la secuencia", e);
            } catch (Exception e) {
                pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                if (transaccionIndependiente) {
                    if ((pm != null) && (pm.currentTransaction().isActive())) {
                        pm.currentTransaction().rollback();
                    }
                }                
                throw new gov.sir.fenrir.dao.DAOException("Error obteniendo el registro de la secuencia", e);
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
     * Obtiene una lista de objetos tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     */
    public List getCanalesRecaudo(boolean flag) throws DAOException {
        List lista = new ArrayList();
        System.out.println("BUSCANDO CANALES DE RECAUDO");

        try {
            PersistenceManager pm = AdministradorPM.getPM();

            Query q = pm.newQuery(CanalesRecaudoEnhanced.class);
            q.setIgnoreCache(false);
            if (flag) {
                q.declareParameters("Boolean act");
                q.setFilter("activo==act");
            }

            //Se establece como criterio de ordenamiento el identificador
            //del canal. (Orden ascendente).
            q.setOrdering("idCanal ascending");
            Collection results;
            if (flag) {
                results = (Collection) q.execute(true);
            } else {
                results = (Collection) q.execute();
            }

//            
            Iterator it = results.iterator();

            while (it.hasNext()) {
                CanalesRecaudoEnhanced obj = (CanalesRecaudoEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            q.close(results);
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * Obtiene una lista de objetos tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     */
    public List getCanalesRecaudoXCirculo(Circulo circulo) throws DAOException {
        List lista = new ArrayList();
        System.out.println("BUSCANDO CANALES DE RECAUDO X CIRCULO");
        boolean activo = true;
        boolean canalSir = true;

        try {
            PersistenceManager pm = AdministradorPM.getPM();
            if (!pm.currentTransaction().isActive()) {
             pm.currentTransaction().begin();
            }
            Query q = pm.newQuery(CirculoTipoPagoEnhanced.class);
            /*pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();*/

            q.declareParameters("String circulo, boolean activo, boolean esCanalSir");
            q.setFilter("idCirculo==circulo && ctpActivo == activo && canalSir == esCanalSir");
            Collection results = (Collection) q.execute(circulo.getIdCirculo(), activo, canalSir);
            Iterator it = results.iterator();

            while (it.hasNext()) {
                CirculoTipoPagoEnhanced obj = (CirculoTipoPagoEnhanced) it.next();
                pm.makeTransient(obj.getTipoPago());
                pm.makeTransient(obj.getCirculo());
                pm.makeTransient(obj.getCanalesRecaudo());
                pm.makeTransient(obj.getCuentasBancarias());
                pm.makeTransient(obj);
                lista.add(obj);
            }
            
            q.close(results);
            pm.currentTransaction().commit();
            pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /**
     * * Adiciona un nuevo canal de recaudo (SIR_OP_CANALES_RECAUDO)
     */
    public boolean addCanalRecaudo(CanalesRecaudo datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        CanalesRecaudoEnhanced canalRecaudo = CanalesRecaudoEnhanced.enhance(datos);
        try {
            long id = getSecuencial("SIR_OP_CANALES_RECAUDO", null);
            canalRecaudo.setIdCanal((int) id);

            pm.currentTransaction().begin();
            pm.makePersistent(canalRecaudo);
            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOBancosDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Actualiza el estado de un canal de recaudo en especifico
     */
    public void actualizarEstadoCanalRecaudo(int idCanal, boolean estado)
            throws DAOException {
        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(CanalesRecaudoEnhanced.class);
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            q.declareParameters("int id");
            q.setFilter("idCanal==id");
            Collection results = (Collection) q.execute(idCanal);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                CanalesRecaudoEnhanced obj = (CanalesRecaudoEnhanced) it.next();
                obj.setActivo(estado);
                pm.makePersistent(obj);
                pm.currentTransaction().commit();
            }
            q.close(results);
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Consulta las cuentas bancarias para un círculo
     */
    public List getCuentasBancariasXCirculo(Circulo circulo) throws DAOException {

        List lista = new ArrayList();
        try {
            boolean activo = true;
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(CuentasBancariasEnhanced.class);
            q.declareParameters("String circulo, boolean activo");
            q.setFilter("idCirculo == circulo && activa == activo");

            Collection results = (Collection) q.execute(circulo.getIdCirculo(), activo);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                CuentasBancariasEnhanced obj = (CuentasBancariasEnhanced) it.next();
//                pm.makeTransient(obj.getBanco());
//                pm.makeTransient(obj.getCirculo());
//                pm.makeTransient(obj);
                lista.add(obj);
            }
            q.close(results);
            //pm.close();
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
        lista = TransferUtils.makeTransferAll(lista);
        return lista;
    }

    /*
    * Valida si para la forma de pago recibida el campo de banco franquicia esta activo o no
     */
    public boolean validaCampoBancoFranquicia(TipoPago tipoPago) throws DAOException {
        boolean existe = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String consulta = "SELECT * "
                + "FROM SIR_OP_REL_FPAGO_CAMPOS "
                + "WHERE SIR_OP_REL_FPAGO_CAMPOS.FPCC_CC_ID = '1' AND SIR_OP_REL_FPAGO_CAMPOS.FPCC_ESTADO = '1' AND SIR_OP_REL_FPAGO_CAMPOS.FPCC_FP_ID = ? ";

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
                existe = true;
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

        return existe;
    }

    /**
     * Consulta las cuentas bancarias para un círculo, canal y forma pago
     */
    public List getCuentasBancariasXCirculoCanalForma(Circulo circulo, String idCanalRecaudo, String idFormaPago) throws DAOException {

        List list = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;
        PersistenceManager pm = AdministradorPM.getPM();

        String consulta = "SELECT CB.CB_ID, CB.CB_ID_CIRCULO, CB.CB_ID_BANCO, CB.CB_NRO_CUENTA, CB.CB_ACTIVA FROM SIR_OP_CUENTAS_BANCARIAS CB "
                + "LEFT JOIN SIR_NE_CIRCULO_TIPO_PAGO CTP ON CTP.CB_ID = CB.CB_ID "
                + "WHERE CB.CB_ID_CIRCULO = ? AND CTP.CR_ID = ?  AND CTP.ID_TIPO_DOC_PAGO = ? AND CB.CB_ACTIVA = '1'";

        try {
            jdoPM = (VersantPersistenceManager) gov.sir.fenrir.dao.impl.jdogenie.AdministradorPM.getPM();
            jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(consulta);

            ps.setString(1, circulo.getIdCirculo());
            ps.setString(2, idCanalRecaudo);
            ps.setString(3, idFormaPago);

            rs = ps.executeQuery();            
            
            while (rs.next()) {

                CuentasBancarias cuentasBancarias = new CuentasBancarias();

                Banco banco = new Banco();
//                banco.setIdBanco(rs.getString(3));

                BancoEnhancedPk bPk = new BancoEnhancedPk();
                bPk.idBanco = rs.getString(3);

                BancoEnhanced rta = (BancoEnhanced) jdoPM.getObjectById(bPk, true);

                banco = (Banco) rta.toTransferObject();

                Circulo crlo = new Circulo();
                crlo.setIdCirculo(rs.getString(2));

                cuentasBancarias.setId(Integer.parseInt(rs.getString(1)));
                cuentasBancarias.setIdCirculo(rs.getString(2));
                cuentasBancarias.setIdBanco(rs.getString(3));
                cuentasBancarias.setNroCuenta(rs.getString(4));
                cuentasBancarias.setActiva(true);
                cuentasBancarias.setBanco(banco);
                cuentasBancarias.setCirculo(crlo);

                list.add(cuentasBancarias);
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
        return list;
    }

    /**
     * Actualiza el estado de un registro de la tabla SIR_NE_CIRCULO_TIPO_PAGO
     */
    public void actualizarEstadoCtp(String idCtp, boolean estado)
            throws DAOException {
        try {
            PersistenceManager pm = AdministradorPM.getPM();
            Query q = pm.newQuery(CirculoTipoPagoEnhanced.class);
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            q.declareParameters("String id");
            q.setFilter("idCirculoTipoPago==id");
            Collection results = (Collection) q.execute(idCtp);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                CirculoTipoPagoEnhanced obj = (CirculoTipoPagoEnhanced) it.next();
                obj.setCtpActivo(estado);
                pm.makePersistent(obj);
                pm.currentTransaction().commit();
            }
            q.close(results);
        } catch (Exception e) {
            Log.getInstance().error(JDOBancosDAO.class, e);
            Log.getInstance().error(JDOBancosDAO.class, e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }
    }

}
