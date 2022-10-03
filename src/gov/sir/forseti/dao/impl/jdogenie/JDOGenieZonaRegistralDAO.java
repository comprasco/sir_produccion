/*
 * Created on 15-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao.impl.jdogenie;

import gov.sir.fenrir.dao.ConfiguracionPropiedadesException;
import gov.sir.fenrir.dao.ErrorConexionException;
import gov.sir.fenrir.dao.impl.jdogenie.OracleFenrirJDO;
import gov.sir.fenrir.dao.impl.oracle.OracleFenrirLDAP;
import gov.sir.forseti.dao.DAOException;
import gov.sir.forseti.dao.ZonaRegistralDAO;
import gov.sir.hermod.HermodProperties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.NivelDAO;
import org.auriga.core.modelo.transferObjects.Rol;

import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarial;
import gov.sir.core.negocio.modelo.ActoresRepartoNotarialPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.CirculoFestivoPk;
import gov.sir.core.negocio.modelo.CirculoImpresora;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DepartamentoPk;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EjePk;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.FirmaRegistradorPk;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.MunicipioPk;
import gov.sir.core.negocio.modelo.Texto;
import gov.sir.core.negocio.modelo.TextoPk;
import gov.sir.core.negocio.modelo.TipoImprimible;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioCirculo;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.ZonaRegistralPk;
import gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ActoresRepartoNotarialEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoFestivoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoImpresoraEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoTrasladoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DepartamentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EjeEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MunicipioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TextoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TextoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoImprimibleEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.VeredaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhancedPk;

import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;

/**
 * Clase que implementa los servicios de ubicación de Forseti
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * @author      :   Julio Alcazar
 * @change      :   se cambia el acceso de la clase de default a public
 * Caso Mantis  :   0007123
 */
public class JDOGenieZonaRegistralDAO implements ZonaRegistralDAO {
    private static final int ANIOS_CREACION_CIRCULO_PROCESO = 50;

    /**
     * Constructor por defecto
     *
     */
    public JDOGenieZonaRegistralDAO() {
    }

    /**
     * Obtiene una lista con los departamentos registrados en el sistema
     * @return lista con objetos tipo Departamento, cada departamento tiene su jerarquía de objetos de acuerdo
     * al círculo dado
     * @see gov.sir.core.negocio.modelo.Departamento
     * @throws DAOException
     */
    public List getDepartamentos() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = null;
        DepartamentoEnhanced dp;
        MunicipioEnhanced mn;
        VeredaEnhanced ver;

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            rta = this.getDepartamentos(pm);

            for (Iterator itr = rta.iterator(); itr.hasNext();) {
                dp = (DepartamentoEnhanced) itr.next();

                for (Iterator itr2 = dp.getMunicipios().iterator();
                        itr2.hasNext();) {
                    mn = (MunicipioEnhanced) itr2.next();

                    for (Iterator itr3 = mn.getVeredas().iterator();
                            itr3.hasNext();) {
                        ver = (VeredaEnhanced) itr3.next();
                        pm.makeTransient(ver);
                    }

                    pm.makeTransient(mn);
                }

                // pm.makeTransientAll(dp.getMunicipios());
            }

            pm.makeTransientAll(rta);
        } catch (DAOException e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
          finally {
	    	  if (pm.currentTransaction().isActive()) {
	    		  pm.currentTransaction().commit();
	    	  }
            pm.close();
        }

        /*
        DepartamentoEnhanced aux1 = (DepartamentoEnhanced) rta.get(1);
        SerialUtils.Serializar(aux1);

        DepartamentoEnhanced aux2 = (DepartamentoEnhanced) SerialUtils.deserializar();
        */
        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     *
     * @param pm
     * @return
     * @throws DAOException
     */
    protected List getDepartamentos(PersistenceManager pm)
        throws DAOException {
        try {
            Query query = pm.newQuery(DepartamentoEnhanced.class);
            query.setOrdering("nombre ascending, idDepartamento ascending");

            List rta = (List) query.execute();

            return rta;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene un objeto Departamento dado su identificador
     * @param oid identificador del departamento
     * @return objeto Departamento con sus atributos básicos y municipios
     * @throws DAOException
     */
    public Departamento getDepartamento(DepartamentoPk oid)
        throws DAOException {
        DepartamentoEnhanced rta = null;
        PersistenceManager pm = AdministradorPM.getPM();
        Departamento aux = null;

        try {
            rta = this.getDepartamento(new DepartamentoEnhancedPk(oid), pm);

            if (rta != null) {
                for (Iterator itr2 = rta.getMunicipios().iterator();
                        itr2.hasNext();) {
                    MunicipioEnhanced mn = (MunicipioEnhanced) itr2.next();

                    for (Iterator itr3 = mn.getVeredas().iterator();
                            itr3.hasNext();) {
                        VeredaEnhanced ver = (VeredaEnhanced) itr3.next();
                        pm.makeTransient(ver);
                    }

                    pm.makeTransient(mn);
                }

                pm.makeTransient(rta);
            }
        } catch (JDOObjectNotFoundException e) {
            rta = null;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        if (rta != null) {
            aux = (Departamento) rta.toTransferObject();
        }

        return aux;
    }

    /**
      * Obtiene un objeto Departamento dado su identificador
      * @param oid identificador del departamento
      * @return objeto Departamento con sus atributos básicos y municipios
      * @throws DAOException
      */
    protected DepartamentoEnhanced getDepartamento(
        DepartamentoEnhancedPk oid, PersistenceManager pm)
        throws DAOException {
        DepartamentoEnhanced rta = null;

        if (oid.idDepartamento != null) {
            try {
                rta = (DepartamentoEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Adiciona un departamento a la configuración del sistema
     * @param datos objeto Departamento con sus atributos exceptuando su identificador
     * el cual es generado por el sistema y el usuario que adiciona el departamento
     * @return identificador de departamento generado
     * @throws DAOException
     */
    public DepartamentoPk addDepartamento(Departamento datos, Usuario usuario)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        DepartamentoEnhanced dpto = DepartamentoEnhanced.enhance(datos);

        try {
            //Validación de identificador del departamento
            DepartamentoEnhancedPk vid = new DepartamentoEnhancedPk();
            vid.idDepartamento = datos.getIdDepartamento();

            DepartamentoEnhanced vcir = this.getDepartamento(vid, pm);
            
            UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
    		usuarioEnhId.idUsuario = usuario.getIdUsuario();
    		
    		UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
    		dpto.setUsuario(usuarioEnh);
            if (vcir != null) {
                throw new DAOException(
                    "Ya existe un departamento con el identificador: " +
                    vid.idDepartamento);
            }

            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            pm.makePersistent(dpto);
            pm.currentTransaction().commit();

            DepartamentoEnhancedPk rta = (DepartamentoEnhancedPk) pm.getObjectId(dpto);

            return rta.getDepartamentoID();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        }
         catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         finally {
            pm.close();
        }
    }

    /**
     * Obtiene la lista de municipios de un departamento
     * @param oid identificador del departamento
     * @return lista de objetos Municipio
     * @see gov.sir.core.negocio.modelo.Municipio
     * @throws DAOException
     */
    public List getMunicipiosDepartamento(DepartamentoPk oid)
        throws DAOException {
        return null;
    }

    /**
     * Obtiene un objeto municipio dado su identificador
     * @param oid identificador del municipio
     * @return objeto Municipio con sus atributos básicos y veredas
     * @throws DAOException
     */
    public Municipio getMunicipio(MunicipioPk oid) throws DAOException {
        return null;
    }

    /**
     * Adiciona un municipio a un departamento dado
     * @param datos objeto municipio con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param oid identificador del departamento
     * @param usuario que adiciona el municipio
     * @return identificación del municipio generada
     * @throws DAOException
     */
    public MunicipioPk addMunicipioToDepartamento(Municipio mun,
        DepartamentoPk oid, Usuario usuario) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        MunicipioEnhanced datos = MunicipioEnhanced.enhance(mun);
        MunicipioEnhancedPk rta = new MunicipioEnhancedPk();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Valida y asocia el departamento
            DepartamentoEnhanced depto = this.getDepartamento(new DepartamentoEnhancedPk(
                        oid), pm);

            if (depto == null) {
                throw new DAOException("El departamento no existe: " +
                    oid.idDepartamento);
            }
            UsuarioEnhancedPk usuarioEhnId= new UsuarioEnhancedPk();
            usuarioEhnId.idUsuario=usuario.getIdUsuario();
            UsuarioEnhanced usuarioEnh=(UsuarioEnhanced)pm.getObjectById(usuarioEhnId,true);
            datos.setDepartamento(depto);
            datos.setUsuario(usuarioEnh);

            //Valida que no exista un municipio con la misma llave primaria
            MunicipioEnhancedPk mId = new MunicipioEnhancedPk();
            mId.idDepartamento = datos.getIdDepartamento();
            mId.idMunicipio = datos.getIdMunicipio();

            MunicipioEnhanced vMun = new MunicipioEnhanced();
            vMun = this.getMunicipio(mId, pm);

            if (vMun != null) {
                throw new DAOException("Ya existe un municipio con el mismo ID");
            }

            pm.makePersistent(datos);
            depto.addMunicipio(datos);
            pm.currentTransaction().commit();

            rta = (MunicipioEnhancedPk) pm.getObjectId(datos);
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
          finally {
            pm.close();
        }

        return rta.getMunicipioID();
    }

    /**
     * Obtiene la lista de veredas del municipio dado
     * @param oid identificación del municipio
     * @return lista de objetos tipo Vereda
     * @see gov.sir.core.negocio.modelo.Vereda
     * @throws DAOException
     */
    public List getVeredasMunicipio(MunicipioPk oid) throws DAOException {
        return null;
    }

    /**
     * Obtiene un objeto Vereda dado su identificador
     * @param oid identificador de la vereda
     * @return objeto Vereda
     * @throws DAOException
     */
    public Vereda getVereda(VeredaPk oid) throws DAOException {
        return null;
    }

    /**
     * Obtiene un objeto Vereda dado su identificador de manera transaccional
     * @param oid identificador de la vereda
     * @return objeto Vereda
     * @throws DAOException
     */
    protected VeredaEnhanced getVereda(VeredaEnhancedPk oid,
        PersistenceManager pm) throws DAOException {
        VeredaEnhanced rta = null;

        if ((oid.idDepartamento != null) && (oid.idMunicipio != null) &&
                (oid.idVereda != null)) {
            try {
                rta = (VeredaEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    public List getCirculos() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();
        List aux = null;
        CirculoEnhanced circulo;

        try {
            aux = this.getCirculos(pm);

            for (Iterator itr = aux.iterator(); itr.hasNext();) {
                circulo = (CirculoEnhanced) itr.next();
                this.makeTransientCirculo(circulo, pm);
                if (circulo.isActivo()){
                	rta.add(circulo);	
                }
            }
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }
    
    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * Solo los circulos que operan en SIR y tienen firma activa
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    public List getCirculosFechaProd() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();
        List aux = null;
        CirculoEnhanced circulo;

        try {
            aux = this.getCirculosFechaProd(pm);

            for (Iterator itr = aux.iterator(); itr.hasNext();) {
                circulo = (CirculoEnhanced) itr.next();
                this.makeTransientCirculo(circulo, pm);
                if (circulo.isActivo()){
                	rta.add(circulo);	
                }
            }
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }
    
    /**
     * Obtiene una lista de círculos que tienen configurada
     * la firma del  registrador
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    public List getCirculosFirmaRegistrador() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = new ArrayList();
        List aux = null;
        CirculoEnhanced circulo;

        try {
            aux = this.getCirculosFirmaRegistrador(pm);

            for (Iterator itr = aux.iterator(); itr.hasNext();) {
                circulo = (CirculoEnhanced) itr.next();
                this.makeTransientCirculo(circulo, pm);
                if (circulo.isActivo()){
                	rta.add(circulo);	
                }
            }
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Vuelve trasiente un circulo
     * @param circulo
     * @param pm
     * @throws DAOException
     */
    protected void makeTransientCirculo(CirculoEnhanced circulo,
        PersistenceManager pm) throws DAOException {
        ZonaRegistralEnhanced zr = null;
        CirculoTipoPagoEnhanced ctipo = null;
        FirmaRegistradorEnhanced firma = null;

        if (circulo != null) {
            try {
                /*
                for (Iterator itr = circulo.getZonaRegistrales().iterator();
                        itr.hasNext();) {
                    zr = (ZonaRegistralEnhanced) itr.next();
                    pm.makeTransient(zr.getVereda().getMunicipio()
                                       .getDepartamento());
                    pm.makeTransient(zr.getVereda().getMunicipio());
                    pm.makeTransient(zr.getVereda());
                    pm.makeTransient(zr);
                }*/

                //pm.makeTransientAll(circulo.getZonaRegistrales());
                for (Iterator itr = circulo.getTiposPagos().iterator();
                        itr.hasNext();) {
                    ctipo = (CirculoTipoPagoEnhanced) itr.next();
                    pm.makeTransient(ctipo.getTipoPago());
                    pm.makeTransient(ctipo);
                }

                for (Iterator itr = circulo.getFirmas().iterator();
                        itr.hasNext();) {
                    firma = (FirmaRegistradorEnhanced) itr.next();
                    pm.makeTransient(firma);
                }
                
                JDOGenieFolioDAO folioDao = new JDOGenieFolioDAO();
                folioDao.makeTransientOficinaOrigen(circulo.getOficinaOrigen(), pm);
                //pm.makeTransient(circulo.getOficinaOrigen());
                pm.makeTransient(circulo);
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    protected List getCirculos(PersistenceManager pm) throws DAOException {
        try {
            Query query = pm.newQuery(CirculoEnhanced.class);
            query.setOrdering("nombre ascending");

            List rta = (List) query.execute();

            return rta;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * Solo los circulos que operan en SIR y tienen firma activa
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    protected List getCirculosFechaProd(PersistenceManager pm) throws DAOException {
        try {
            Query query = pm.newQuery(CirculoEnhanced.class);
            query.setFilter("fechaProd != null");
            query.setOrdering("nombre ascending");

            List rta = (List) query.execute();

            return rta;
        } catch (JDOException e) {
        	Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene una lista de círculos de la configuración del sistema
     * @return lista de objetos Circulo
     * @see gov.sir.core.negocio.modelo.Circulo
     * @throws DAOException
     */
    protected List getCirculosFirmaRegistrador(PersistenceManager pm) throws DAOException {
        try {
            Query query = pm.newQuery(CirculoEnhanced.class);
            query.declareVariables("FirmaRegistradorEnhanced firma");
            query.setFilter("this.idCirculo == firma.circulo "+
            		        "&& firma.activo == '1'");
            query.setOrdering("nombre ascending");

            List rta = (List) query.execute();

            return rta;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene un objeto Circulo dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public Circulo getCirculo(CirculoPk oid) throws DAOException {
        CirculoEnhanced ef = null;
        PersistenceManager pm = AdministradorPM.getPM();
        Circulo aux = null;

        try {
            ef = this.getCirculo(new CirculoEnhancedPk(oid), pm);
            this.makeTransientCirculo(ef, pm);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (ef != null) {
            aux = (Circulo) ef.toTransferObject();
        }

        return aux;
    }

    /**
     * Obtiene un objeto Circulo transaccional dado su identificador
     * @param oid identificador del circulo
     * @param pm PersistenceManager de la transaccion
     * @return objeto Circulo
     * @throws DAOException
     */
    protected CirculoEnhanced getCirculo(CirculoEnhancedPk oid,
        PersistenceManager pm) throws DAOException {
        CirculoEnhanced rta = null;

        if (oid.idCirculo != null) {
            try {
                rta = (CirculoEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }


    /**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public Texto getTexto(TextoPk oid) throws DAOException {
        TextoEnhanced ef = null;
        PersistenceManager pm = AdministradorPM.getPM();
        Texto aux = null;

        try {
            ef = this.getTexto(new TextoEnhancedPk(oid), pm);
            pm.makeTransient(ef);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (ef != null) {
            aux = (Texto) ef.toTransferObject();
        }

        return aux;
    }


    /**
     * Obtiene un objeto Texto transaccional dado su identificador
     * @param oid identificador del circulo
     * @param pm PersistenceManager de la transaccion
     * @return objeto Circulo
     * @throws DAOException
     */
    protected TextoEnhanced getTexto(TextoEnhancedPk oid,
        PersistenceManager pm) throws DAOException {
        TextoEnhanced rta = null;

        if ((oid.idCirculo != null)&&(oid.idLlave!=null)) {
            try {
                rta = (TextoEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Adiciona un círculo a la configuración del sistema
     * @param datos objeto Circulo con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param datos
     * @return identificación del círculo generado por el sistema
     * @throws DAOException
     */
    public CirculoPk addCirculo(Circulo circulo, Usuario usuario) throws DAOException {
    //public Circulo.ID addCirculo(Circulo circulo) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoProcesoEnhanced cp = null;

        CirculoEnhanced datos = CirculoEnhanced.enhance(circulo);
        UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
		usuarioEnhId.idUsuario = usuario.getIdUsuario();
		
		UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
        	
        int anioInicial = Calendar.getInstance().get(Calendar.YEAR);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Validación de identificador del círculo
            CirculoEnhancedPk vid = new CirculoEnhancedPk();
            vid.idCirculo = datos.getIdCirculo();

            CirculoEnhanced vcir = this.getCirculo(vid, pm);

            if (vcir != null) {
                throw new DAOException(
                    "Ya existe un círculo con el identificador: " +
                    vid.idCirculo);
            }

            //Validación y asociación de las zonas registrales asociadas

            /*
            ZonaRegistralEnhanced zona;
            List zonas = datos.getZonaRegistrales();

                    for (Iterator itr = zonas.iterator(); itr.hasNext();) {
                            zona = (ZonaRegistralEnhanced) itr.next();
                            this.addZonaRegistralToCirculo(datos, zona, pm);
                    }*/

            //Se inicializan los circulos-proceso del circulo para los proximos años
            List procesos = this.getProcesos(pm);

            ProcesoEnhanced procTMP;

            for (Iterator itr = procesos.iterator(); itr.hasNext();) {
                procTMP = (ProcesoEnhanced) itr.next();

                for (int anio = anioInicial;
                        anio < (anioInicial + ANIOS_CREACION_CIRCULO_PROCESO);
                        anio++) {
                    cp = new CirculoProcesoEnhanced();
                    cp.setProceso(procTMP);
                    cp.setLastIdTurno(0);
                    cp.setCirculo(datos);
                    cp.setAnio(String.valueOf(anio));
                    datos.setUsuarioModificacion(usuarioEnh);
                    datos.addCirculoProceso(cp);
                }
            }
            pm.makePersistent(datos);
            pm.currentTransaction().commit();

            CirculoEnhancedPk rta = (CirculoEnhancedPk) pm.getObjectId(datos);

            return rta.getCirculoID();
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        }  catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
          finally {
            pm.close();
        }
    }





    /**
     * Adiciona un texto a la configuración del sistema
     * @param datos objeto Circulo con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param datos
     * @return identificación del texto generado por el sistema
     * @throws DAOException
     */
    public TextoPk addTexto(Texto texto) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoProcesoEnhanced cp = null;

        TextoEnhanced datos = TextoEnhanced.enhance(texto);

        try {
            pm.currentTransaction().begin();

            //Validación de identificador del texto
            TextoEnhancedPk vid = new TextoEnhancedPk();
            vid.idCirculo = datos.getIdCirculo();

            TextoEnhanced vcir = this.getTexto(vid, pm);

            if (vcir != null) {
                throw new DAOException(
                    "Ya existe un texto con el identificador: Circulo: " +
                    vid.idCirculo +" y Llave: " +vid.idLlave);
            }

            pm.makePersistent(datos);
            pm.currentTransaction().commit();
            TextoEnhancedPk rta = (TextoEnhancedPk) pm.getObjectId(datos);

            return rta.getTextoID();
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         finally {
            pm.close();
        }
    }

    /**
     * Obtiene las ZonaRegistrales configuradas en el sistema
     * @return lista de objetos tipo ZonaRegistral, cada uno tiene sus objetos vereda y circulo
     * @see gov.sir.core.negocio.modelo.ZonaRegistral
     * @throws DAOException
     */
    public List getZonaRegistrales() throws DAOException {
        return null;
    }

    /**
     * Obtiene las ZonaRegistrales configuradas en el sistema para un círculo determinado
     * @return lista de objetos tipo ZonaRegistral, cada uno tiene sus objetos vereda y circulo
     * @see gov.sir.core.negocio.modelo.ZonaRegistral
     * @throws DAOException
     */
    public List getZonaRegistralesCirculo(CirculoPk oid)
        throws DAOException {
        List rta = null;
        CirculoEnhanced c = null;
        ZonaRegistralEnhanced zr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            c = this.getCirculo(new CirculoEnhancedPk(oid), pm);

            if (c == null) {
                throw new DAOException("No se encuentra el círculo con el ID: " +
                    oid.idCirculo);
            }

            rta = c.getZonaRegistrales();

            for (Iterator itr = rta.iterator(); itr.hasNext();) {
                zr = (ZonaRegistralEnhanced) itr.next();
                pm.makeTransient(zr.getVereda().getMunicipio().getDepartamento());
                pm.makeTransient(zr.getVereda().getMunicipio());
                pm.makeTransient(zr.getVereda());
            }

            pm.makeTransientAll(rta);
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
          finally {
        	  if (pm.currentTransaction().isActive()) {
        		  pm.currentTransaction().commit();
        	  }
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Obtiene una zona registral dado su identificador
     * @param oid identificador de la zona registral
     * @param pm PersistenceManager de la transaccion
     * @return zona registral con su jerarquía completa: circulo, vereda, municipio y departamento
     * @throws DAOException
     */
    public ZonaRegistral getZonaRegistral(ZonaRegistralPk oid)
        throws DAOException {
        ZonaRegistralEnhanced ef = null;
        PersistenceManager pm = AdministradorPM.getPM();
        ZonaRegistral aux = null;

        try {
            ef = this.getZonaRegistral(new ZonaRegistralEnhancedPk(oid), pm);
            pm.makeTransient(ef);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (ef != null) {
            aux = (ZonaRegistral) ef.toTransferObject();
        }

        return aux;
    }

    /**
     * Obtiene una zona registral dado su identificador, método utilizado para transacciones
     * debe darse el PersistenceManager
     * @param oid identificador de la zona registral
     * @return zona registral con su jerarquía completa: circulo, vereda, municipio y departamento
     * @throws DAOException
     */
    protected ZonaRegistralEnhanced getZonaRegistral(
        ZonaRegistralEnhancedPk oid, PersistenceManager pm)
        throws DAOException {
        ZonaRegistralEnhanced rta = null;

        if (oid.idZonaRegistral != null) {
            try {
                rta = (ZonaRegistralEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene una zona registral dado su circulo y vereda en una transacción
     * @param circulo
     * @param vereda
     * @param pm
     * @return
     * @throws DAOException
     */
    protected ZonaRegistralEnhanced getZonaRegistralByCirculoVereda(
        CirculoEnhanced circulo, VeredaEnhanced vereda, PersistenceManager pm)
        throws DAOException {
        ZonaRegistralEnhanced rta = null;
        String idCirculo = circulo.getIdCirculo();
        String idVereda = null;
        String idMunicipio = null;
        String idDepartamento = null;

        Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "ENTRA A VALIDACION DE NULLS EN getZonaRegistralByCirculoVereda() ");
        if(vereda.getIdVereda()!=null){
        	idVereda = vereda.getIdVereda();
        }

        if(vereda.getIdMunicipio()!=null){
        	idMunicipio = vereda.getIdMunicipio();
        }
        else{
        	if(vereda.getMunicipio()!=null){
        		idMunicipio = vereda.getMunicipio().getIdMunicipio();
        	}
        }

        if(vereda.getIdDepartamento()!=null){
        	idDepartamento = vereda.getIdDepartamento();
        }
        else{
           	if(vereda.getMunicipio()!=null){
        		if(vereda.getMunicipio().getDepartamento()!=null){
        			idDepartamento = vereda.getMunicipio().getDepartamento().getIdDepartamento();
        		}
        	}
        }

        Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "DESPUES DE VALIDACION DE NULLS EN getZonaRegistralByCirculoVereda(): ");
        Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "ID CIRCULO: " + idCirculo);
        Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "ID VEREDA: " + idVereda);
        Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "ID MUNICIPIO: " + idMunicipio);
        Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "ID DEPARTAMENTO: " + idDepartamento);

        try {
            Query query = pm.newQuery(ZonaRegistralEnhanced.class);
            query.declareParameters(
                "String idCirculo, String idVereda, String idMunicipio, String idDepartamento");
            query.setFilter(
                "circulo.idCirculo==idCirculo && vereda.idVereda==idVereda && vereda.idMunicipio==idMunicipio && vereda.idDepartamento==idDepartamento");

            Collection col = (Collection) query.executeWithArray(new Object[] {
                        idCirculo, idVereda, idMunicipio, idDepartamento
                    });
            Iterator iter = col.iterator();
            
            Log.getInstance().debug(JDOGenieZonaRegistralDAO.class, "SE EJECUTO LA CONSULTA");

            if (iter.hasNext()) {
                rta = (ZonaRegistralEnhanced) iter.next();
            } else {
                rta = null;
            }
        } catch (JDOException e) {
        	Log.getInstance().error(JDOGenieZonaRegistralDAO.class, "ERROR EN LA ZONA REGISTRAL: "+ e.getMessage());
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Adiciona una ubicación a la configuración del sistema, la ZonaRegistral
     * debe tener asociado una vereda y un círculo
     * @param datos objeto ZonaRegistral con objetos Vereda y Circulo
     * @return identificador de la ubicación generada
     * @throws DAOException
     */
    public ZonaRegistralPk addZonaRegistral(ZonaRegistral datos)
        throws DAOException {
        return null;
    }

    /**
     * Retorna la lista de procesos de manera transaccional, se debe
     * dar el PersitenceManager
     * @param pm
     * @return lista de procesos
     * @see gov.sir.core.negocio.modelo.Proceso
     * @throws DAOException
     */
    protected List getProcesos(PersistenceManager pm) throws DAOException {
        try {
            Query query = pm.newQuery(ProcesoEnhanced.class);
            query.setOrdering("nombre ascending");

            List rta = (List) query.execute();

            return rta;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param sID
     * @param pm
     * @return
     * @throws DAOException
     */
    protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhancedPk sID,
        PersistenceManager pm) throws DAOException {
        SecuenciasEnhanced rta = null;

        if (sID.tableName != null) {
            try {
                rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene una lista con todos los ejes configurados en el sistema
     * @return lista de objetos Eje
     * @see gov.sir.core.negocio.modelo.Eje
     * @throws DAOException
     */
    public List getEjes() throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        List rta = null;

        try {
            rta = this.getEjes(pm);
            pm.makeTransientAll(rta);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Obtiene una lista con todos los ejes configurados en el sistema
     * de manera transaccional
     * @return lista de objetos Eje
     * @see gov.sir.core.negocio.modelo.Eje
     * @throws DAOException
     */
    protected List getEjes(PersistenceManager pm) throws DAOException {
        try {
            Query query = pm.newQuery(EjeEnhanced.class);
            query.setOrdering("nombre ascending");

            List rta = (List) query.execute();

            return rta;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene un objeto Eje dado su identificador
     * @param oid identificador del eje
     * @return objeto Eje
     * @throws DAOException
     */
    public Eje getEje(EjePk oid) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EjeEnhanced rta = null;
        Eje aux = null;

        try {
            rta = this.getEje(new EjeEnhancedPk(oid), pm);
            pm.makeTransient(rta);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (rta != null) {
            aux = (Eje) rta.toTransferObject();
        }

        return aux;
    }

    /**
     * Obtiene un objeto Eje dado su identificador. Usado para transacciones
     * @param oid identificador del eje
     * @return objeto Eje
     * @throws DAOException
     */
    protected EjeEnhanced getEje(EjeEnhancedPk oid, PersistenceManager pm)
        throws DAOException {
        EjeEnhanced rta = null;

        if (oid.idEje != null) {
            try {
                rta = (EjeEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Adiciona un eje a la configuración del sistema
     * @param datos objeto Eje con sus atributos exceptuando su identificador
     * el cual es generado por el sistema
     * @param datos del usuario que adiciona el eje
     * @return identificador de Eje generado
     * @throws DAOException
     */
    public EjePk addEje(Eje datos, Usuario usuario) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EjeEnhanced eje = EjeEnhanced.enhance(datos);

        try {
        	UsuarioEnhancedPk usuarioEnhId=new UsuarioEnhancedPk();
        	usuarioEnhId.idUsuario=usuario.getIdUsuario();
        	UsuarioEnhanced usuarioEhn=(UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
            //Validación de identificador del eje
            EjeEnhancedPk vid = new EjeEnhancedPk();
            vid.idEje = datos.getIdEje();

            EjeEnhanced vcir = this.getEje(vid, pm);

            if (vcir != null) {
                throw new DAOException(
                    "Ya existe un eje con el identificador: " + vid.idEje);
            }
            eje.setUsuario(usuarioEhn);
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            pm.makePersistent(eje);
            pm.currentTransaction().commit();

            EjeEnhancedPk rta = (EjeEnhancedPk) pm.getObjectId(eje);

            return rta.getEjeID();
        } catch (JDOException e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
          finally {
            pm.close();
        }
    }

    /**
     * Obtiene una oficina origen dado su identificador en una transacción
     * @param oid identificador de la oficina origen
     * @param pm PersistenceManager de la transacción
     * @return OficinaOrigen persistente, null si no la encuentra
     * @throws DAOException
     */
    protected OficinaOrigenEnhanced getOficinaOrigen(
        OficinaOrigenEnhancedPk oid, PersistenceManager pm)
        throws DAOException {
        OficinaOrigenEnhanced rta = null;

        if (oid.idOficinaOrigen != null) {
            try {
                rta = (OficinaOrigenEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene un objeto municipio dado su identificador
     * @param oid identificador del municipio
     * @return objeto Municipio con sus atributos básicos y veredas
     * @throws DAOException
     */
    protected MunicipioEnhanced getMunicipio(MunicipioEnhancedPk oid,
        PersistenceManager pm) throws DAOException {
        MunicipioEnhanced rta = null;

        if ((oid.idDepartamento != null) && (oid.idMunicipio != null)) {
            try {
                rta = (MunicipioEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Adiciona una vereda a un municipio dado
     * @param datos objeto municipio con sus atributos exceptuando su identificación
     * el cual es generado por el sistema
     * @param oid identificador del municipio
     * @return identificación de la vereda generada
     * @throws DAOException
     */
    public VeredaPk addVeredaToMunicipio(Vereda ver, MunicipioPk oid)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        VeredaEnhanced datos = VeredaEnhanced.enhance(ver);
        VeredaEnhancedPk rta = new VeredaEnhancedPk();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Valida y asocia el municipio
            MunicipioEnhanced mun = this.getMunicipio(new MunicipioEnhancedPk(
                        oid), pm);

            if (mun == null) {
                throw new DAOException("El municipio no existe: " +
                    oid.idDepartamento + "-" + oid.idMunicipio);
            }

            datos.setMunicipio(mun);

            //Valida que no exista la vereda con la misma llave primaria
            VeredaEnhancedPk vId = new VeredaEnhancedPk();
            vId.idDepartamento = datos.getIdDepartamento();
            vId.idMunicipio = datos.getIdMunicipio();
            String idAux = getIDVereda(datos.getIdDepartamento(),datos.getIdMunicipio(),pm);
            vId.idVereda = idAux;

            VeredaEnhanced vMun = new VeredaEnhanced();
            vMun = this.getVereda(vId, pm);

            if (vMun != null) {
                throw new DAOException("Ya existe una vereda con el mismo ID");
            }
            
            if(existeNombre(datos,pm)){
            	throw new DAOException("Ya existe una vereda con el nombre: " + datos.getNombre());
            }

            datos.setIdVereda(idAux);
            pm.makePersistent(datos);
            mun.addVereda(datos);
            pm.currentTransaction().commit();

            rta = (VeredaEnhancedPk) pm.getObjectId(datos);
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         finally {
            pm.close();
        }

        return rta.getVeredaID();
    }

    /**
     *
     * @param oficina
     * @param zona
     * @param pm
     * @return
     * @throws DAOException
     */
    protected boolean addZonaRegistralToCirculo(CirculoEnhanced circulo,
        ZonaRegistralEnhanced zona, PersistenceManager pm)
        throws DAOException {
        boolean rta = false;

        try {
            //Validación de la vereda asociada
            VeredaEnhanced ver;
            VeredaEnhancedPk vId = new VeredaEnhancedPk();
            ver = zona.getVereda();

            if (ver == null) {
                throw new DAOException(
                    "La zona registral no tiene vereda asociada");
            }

            vId.idDepartamento = ver.getIdDepartamento();
            vId.idMunicipio = ver.getIdMunicipio();
            vId.idVereda = ver.getIdVereda();

            ver = this.getVereda(vId, pm);

            if (ver == null) {
                throw new DAOException(
                    "La vereda asociada de la zona registral no existe: " +
                    vId.idDepartamento + "-" + vId.idMunicipio + "-" +
                    vId.idVereda);
            }

            //Validación de unicidad  de vereda en la zona registral
            ZonaRegistralEnhanced vZona = this.getZonaRegistralByVereda(ver, pm);

            /*
            if (vZona != null) {
                    throw new DAOException("La vereda ya se encuentra asignada en una zona registral");
            }
            */
            zona.setVereda(ver);
            zona.setCirculo(circulo);
            rta = true;
        } catch (DAOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     *
     * @param circulo
     * @param zona
     * @param pm
     * @return
     * @throws DAOException
     */
    public ZonaRegistralPk addZonaRegistralToCirculo(CirculoPk oid,
        ZonaRegistral datos) throws DAOException {
        ZonaRegistralEnhancedPk rta = new ZonaRegistralEnhancedPk();
        CirculoEnhanced circulo = new CirculoEnhanced();
        PersistenceManager pm = AdministradorPM.getPM();
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();

        ZonaRegistralEnhanced zona = ZonaRegistralEnhanced.enhance(datos);
        JDOGenieFolioDAO folioDAO = new JDOGenieFolioDAO();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            /*AHERRENO 22/07/2013
              Se modifica la forma de obtener la secuencia de la Zona Registral
              antes se obtenia a partir de la tabla "SIR_SECUENCIAS" Campo "TABLE_NAME" = "SIR_NE_ZONA_REGISTRAL"*/
            //Se obtiene la posible llave de la zona registral
            //zona.setIdZonaRegistral(folioDAO.getAndUpdateLlavePrimaria(
            //        CSecuencias.ZONA_REGISTRAL, pm) + "");

            zona.setIdZonaRegistral(validacionesSIR.getIdZonaRegistral()+ "");
            circulo = this.getCirculo(new CirculoEnhancedPk(oid), pm);

            if (circulo == null) {
                throw new DAOException("El circulo especificado no existe");
            }

            this.addZonaRegistralToCirculo(circulo, zona, pm);

            pm.makePersistent(zona);
            circulo.addZonaRegistral(zona);
            pm.currentTransaction().commit();
            rta = (ZonaRegistralEnhancedPk) pm.getObjectId(zona);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } catch (JDOException e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
         finally {
            pm.close();
        }

        return rta.getZonaRegistralID();
    }

    /**
     * Obtiene una zona registral dado su circulo y vereda en una transacción
     * @param circulo
     * @param vereda
     * @param pm
     * @return
     * @throws DAOException
     */
    protected ZonaRegistralEnhanced getZonaRegistralByVereda(
        VeredaEnhanced vereda, PersistenceManager pm) throws DAOException {
        ZonaRegistralEnhanced rta = null;
        String idVereda = vereda.getIdVereda();
        String idMunicipio = vereda.getIdMunicipio();
        String idDepartamento = vereda.getIdDepartamento();

        try {
            Query query = pm.newQuery(ZonaRegistralEnhanced.class);
            query.declareParameters(
                "String idVereda, String idMunicipio, String idDepartamento");
            query.setFilter(
                "vereda.idVereda==idVereda && vereda.idMunicipio==idMunicipio && vereda.idDepartamento==idDepartamento");

            Collection col = (Collection) query.executeWithArray(new Object[] {
                        idVereda, idMunicipio, idDepartamento
                    });
            Iterator iter = col.iterator();

            if (iter.hasNext()) {
                rta = (ZonaRegistralEnhanced) iter.next();
            } else {
                rta = null;
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    /**
     * Adiciona un festivo a un circulo dado
     * @param datos objeto circulofestivo con sus atributos
     * @param oid identificador del circulo
     * @return identificación del circulofestivo generado
     * @throws DAOException
     */
    public CirculoFestivoPk addCirculoFestivo(CirculoPk oid,
        CirculoFestivo datos) throws DAOException {
        CirculoFestivoEnhancedPk rta = new CirculoFestivoEnhancedPk();
        CirculoFestivoEnhanced cirFestivo = new CirculoFestivoEnhanced();
        CirculoEnhanced circulo = new CirculoEnhanced();
        PersistenceManager pm = AdministradorPM.getPM();

        CirculoFestivoEnhanced festivo = CirculoFestivoEnhanced.enhance(datos);
        JDOGenieFolioDAO folioDAO = new JDOGenieFolioDAO();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            circulo = this.getCirculo(new CirculoEnhancedPk(oid), pm);

            if (circulo == null) {
                throw new DAOException("El circulo especificado no existe");
            }

            CirculoFestivoEnhancedPk festivoId = new CirculoFestivoEnhancedPk();
            festivoId.fechaFestivo = festivo.getFechaFestivo();
            festivoId.idCirculo = circulo.getIdCirculo();
            cirFestivo = this.getCirculoFestivo(festivoId, pm);

            if (cirFestivo != null) {
                throw new DAOException("El festivo ya existe para el " +
                    " circulo especificado");
            }

            festivo.setCirculo(circulo);
            festivo.setFechaCreacion(new Date());
            pm.makePersistent(festivo);
            circulo.addCirculoFestivo(festivo);
            pm.currentTransaction().commit();
            rta = (CirculoFestivoEnhancedPk) pm.getObjectId(festivo);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        } catch (JDOException e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
            pm.close();
        }

        return rta.getCirculoFestivoID();
    }

    /**
     * Obtiene un objeto Vereda dado su identificador de manera transaccional
     * @param oid identificador de la vereda
     * @return objeto Vereda
     * @throws DAOException
     */
    protected CirculoFestivoEnhanced getCirculoFestivo(
        CirculoFestivoEnhancedPk oid, PersistenceManager pm)
        throws DAOException {
        CirculoFestivoEnhanced rta = null;

        if ((oid.idCirculo != null) && (oid.fechaFestivo != null)) {
            try {
                rta = (CirculoFestivoEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Obtiene los festivo configurados en el sistema para un círculo determinado
     * @return lista de objetos tipo CirculoFestivo
     * @see gov.sir.core.negocio.modelo.CirculoFestivo
     * @throws DAOException
     */
    public List getFestivosCirculo(CirculoPk oid) throws DAOException {
        List rta = null;
        CirculoEnhanced c = null;
        CirculoFestivoEnhanced cr = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            c = this.getCirculo(new CirculoEnhancedPk(oid), pm);

            if (c == null) {
                throw new DAOException("No se encuentra el círculo con el ID: " +
                    oid.idCirculo);
            }

            rta = c.getCirculoFestivos();

            for (Iterator itr = rta.iterator(); itr.hasNext();) {
                cr = (CirculoFestivoEnhanced) itr.next();
                pm.makeTransient(cr.getCirculo());

                //pm.makeTransient(cr.getFechaFestivo());
                pm.makeTransient(cr);
            }

            pm.makeTransientAll(rta);
        } catch (DAOException e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
        	if (pm.currentTransaction().isActive()) {
        		pm.currentTransaction().commit();
        	}
            pm.close();
        }

        rta = TransferUtils.makeTransferAll(rta);

        return rta;
    }

    /**
     * Determina si la fecha ingresada esta configurada en el sistema como un
     * festivo para un círculo dado
     * @return boolean
     * @see gov.sir.core.negocio.modelo.CirculoFestivo
     * @throws DAOException
     */
    public boolean isFestivo(Date fecha, CirculoPk oid)
        throws DAOException {
        CirculoFestivoEnhancedPk festivoId = new CirculoFestivoEnhancedPk();
        CirculoFestivoEnhanced cirFestivo = new CirculoFestivoEnhanced();
        boolean rta = false;

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            festivoId.fechaFestivo = fecha;
            festivoId.idCirculo = oid.idCirculo;
            cirFestivo = this.getCirculoFestivo(festivoId, pm);

            if (cirFestivo != null) {
                rta = true;
            }

            this.getCirculoFestivo(festivoId, pm);
        } catch (DAOException e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
        	if (pm.currentTransaction().isActive()) {
        		pm.currentTransaction().commit();
        	}
            pm.close();
        }

        return rta;
    }

    /**
     * Añade una firma
     * @param archivo
     * @param oid
     * @return
     * @throws DAOException
     */
    public boolean addFirmaRegistradorToCirculo(FirmaRegistrador firmaReg,
        CirculoPk oid) throws DAOException {
        FirmaRegistradorEnhancedPk firmaId = new FirmaRegistradorEnhancedPk();
        FirmaRegistradorEnhanced firma = new FirmaRegistradorEnhanced();
        PersistenceManager pm = AdministradorPM.getPM();
        int activa = firmaReg.getActivo();
        //long consecutivoFirma=getSecuencial(CSecuencias.SECUENCIA_FIRMA_REGISTRADOR, pm);
        Long idFirmaRegistrador = firmaReg.getIdFirmaRegistrador();
        String archivo = firmaReg.getIdArchivo();
        String cargo = firmaReg.getCargoRegistrador();
        String nombre = firmaReg.getNombreRegistrador();
        if (archivo == null) {
            throw new DAOException("No puede insertar un archivo nulo");
        }
        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            
            
            //solo se puede repetir SINFIRMA.GIF
            //se consulta si ya hay registros con el nombre de la firma para el circulo
            if(!archivo.trim().toUpperCase().equals(CFirmaRegistrador.ARCHIVO_SIN_FIRMA) && 
            		!archivo.trim().toUpperCase().equals(CFirmaRegistrador.ARCHIVO_SIN_FIRMA_JPG)){
            	FirmaRegistrador firmaExiste=getFirmaRegistradorByCirculoArchivo(oid.idCirculo, archivo, pm);
            	if(firmaExiste!=null)
            		throw new DAOException("La firma ya existe");
            }
            
            CirculoEnhanced circulo = this.getCirculo(new CirculoEnhancedPk(oid), pm);
            if (circulo == null) {
                throw new DAOException("No se encontró el círculo con el ID " +
                    oid.idCirculo);
            }
            firmaId.idFirmaRegistrador = idFirmaRegistrador;
            //firmaId.idCirculo = oid.idCirculo;
            firma = this.getFirmaRegistrador(firmaId, pm);

            if (firma != null) {
                throw new DAOException(
                    "Ya existe una misma firma para el círculo");
            }

            firma = new FirmaRegistradorEnhanced();
            firma.setCargoRegistrador(cargo);
            firma.setNombreRegistrador(nombre);

            if ((activa == CFirmaRegistrador.ACTIVA) &&
                    ((cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL))||(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO)))) {
                FirmaRegistradorEnhanced faux;

                for (Iterator it = circulo.getFirmas().iterator();
                        it.hasNext();) {
                    faux = (FirmaRegistradorEnhanced) it.next();

                    if ((faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL))||(faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO))) {
                        if(faux.getActivo()!= CFirmaRegistrador.INACTIVA_DEFINITIVA){
                        	faux.setActivo(CFirmaRegistrador.INACTIVA);
                        }
                    }
                }

                firma.setActivo(CFirmaRegistrador.ACTIVA);
            } else if ((activa == CFirmaRegistrador.ACTIVA) &&
                    ((cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO))||(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO)))) {
                FirmaRegistradorEnhanced faux;

                for (Iterator it = circulo.getFirmas().iterator();
                        it.hasNext();) {
                    faux = (FirmaRegistradorEnhanced) it.next();

                    if ((faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO))||(faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO))) {
                    	if(faux.getActivo()!= CFirmaRegistrador.INACTIVA_DEFINITIVA){
                        	faux.setActivo(CFirmaRegistrador.INACTIVA);
                        }
                    }
                }

                firma.setActivo(CFirmaRegistrador.ACTIVA);
            } else if ((activa == CFirmaRegistrador.ACTIVA) &&
				((cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL))||(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO)))) {
				FirmaRegistradorEnhanced faux;

				for (Iterator it = circulo.getFirmas().iterator();
						it.hasNext();) {
					faux = (FirmaRegistradorEnhanced) it.next();

					if ((faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL))||(faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO))) {
						if(faux.getActivo()!= CFirmaRegistrador.INACTIVA_DEFINITIVA){
                        	faux.setActivo(CFirmaRegistrador.INACTIVA);
                        }
					}
				}

				firma.setActivo(CFirmaRegistrador.ACTIVA);
			} else if ((activa == CFirmaRegistrador.ACTIVA) &&
					((cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO))||(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO)))) {
				FirmaRegistradorEnhanced faux;

				for (Iterator it = circulo.getFirmas().iterator();
						it.hasNext();) {
					faux = (FirmaRegistradorEnhanced) it.next();

					if ((faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO))||(faux.getCargoRegistrador().equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO))) {
						if(faux.getActivo()!= CFirmaRegistrador.INACTIVA_DEFINITIVA){
                        	faux.setActivo(CFirmaRegistrador.INACTIVA);
                        }
					}
				}
			firma.setActivo(CFirmaRegistrador.ACTIVA);
			} else if(activa == CFirmaRegistrador.INACTIVA) {
	            firma.setActivo(CFirmaRegistrador.INACTIVA);
	        } else if(activa == CFirmaRegistrador.INACTIVA_DEFINITIVA){
	        	firma.setActivo(CFirmaRegistrador.INACTIVA_DEFINITIVA);
	        }
            firma.setIdFirmaRegistrador(new Long(getSecuencial(CSecuencias.SECUENCIA_FIRMA_REGISTRADOR, pm)));
            firma.setIdArchivo(archivo);
            firma.setCirculo(circulo);
            pm.makePersistent(firma);

            circulo.addFirma(firma);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
            pm.close();
        }

        return true;
    }

    /**
     * Obtiene una firmaRegistrador dado su ID
     * @param oid
     * @param pm
     * @return
     * @throws DAOException
     */
    protected FirmaRegistradorEnhanced getFirmaRegistrador(
        FirmaRegistradorEnhancedPk oid, PersistenceManager pm)
        throws DAOException {
        FirmaRegistradorEnhanced rta = null;

        if (oid.idFirmaRegistrador != null) {
            try {
                rta = (FirmaRegistradorEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * Setea el flag activa a una firma registrador
     * ACTIVA -> la firma esta activa
     * INACTIVA -> la firma esta inactiva, pero puede volverse a activar
     * INACTIVA_DEFINITIVA -> la firma esta inactiva y no se puede volver a activar.
     * @param oid
     * @param activa 0 -> INACTIVA , 1 -> ACTIVA, 2 -> INACTIVA_DEFINITIVA
     * @return
     * @throws DAOException
     */
    public boolean setActivoFirmaRegistrador(FirmaRegistradorPk oid,
        int activa) throws DAOException {
        FirmaRegistradorEnhancedPk firmaId = new FirmaRegistradorEnhancedPk();
        FirmaRegistradorEnhanced firma = new FirmaRegistradorEnhanced();
        boolean rta = false;

        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            firma = this.getFirmaRegistrador(new FirmaRegistradorEnhancedPk(
                        oid), pm);

            if (firma == null) {
                throw new DAOException(
                    "No existe la firma del registrador en el círculo ");
            }

            //Verificar que la firma no este inactiva indefinidamente
            if(firma.getActivo() == CFirmaRegistrador.INACTIVA_DEFINITIVA){
            	throw new DAOException("No se puede activar una firma que este en el estado de inactivo definitivo.");
            }

			//Si la están activando toca desactivar las otras que tengan el mismo
			//cargo y estén activas
            if (activa == CFirmaRegistrador.ACTIVA) {
                String cargo = firma.getCargoRegistrador();
                String cargoAux = "";

                if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO)){
                	cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO;
                }
                else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO)){
                	cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                }
				else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL)){
					cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO;
				}
				else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO)){
					cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
				}

				else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL)){
					cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
				}
				else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO)){
					cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO;
				}
				else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO)){
					cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
				}
				else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO)){
					cargoAux = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO;
				}

                FirmaRegistradorEnhanced faux;

                for (Iterator it = firma.getCirculo().getFirmas().iterator();
                        it.hasNext();) {
                    faux = (FirmaRegistradorEnhanced) it.next();

                    if ((faux.getCargoRegistrador().equals(cargo))||(faux.getCargoRegistrador().equals(cargoAux))) {
                    	if(faux.getActivo()!= CFirmaRegistrador.INACTIVA_DEFINITIVA){
                        	faux.setActivo(CFirmaRegistrador.INACTIVA);
                        }
                    }
                }
            }

            firma.setActivo(activa);
            pm.currentTransaction().commit();
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         finally {
            pm.close();
        }

        return true;
    }

    /**
    * Servicio que permite eliminar un objeto <code>Departamento</code> y el usuario que lo elimino
    * <p>Utilizado desde las pantallas administrativas.
    * @param acto Objeto <code>Departamento</code> que va a ser eliminado.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>DAOException</code>
    * @see gov.sir.core.negocio.modelo.Departamento
    */
    public boolean eliminarDepartamento(Departamento dato, Usuario usuario)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        DepartamentoEnhanced eliminada = DepartamentoEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            DepartamentoEnhancedPk idEnh = new DepartamentoEnhancedPk();
            idEnh.idDepartamento = eliminada.getIdDepartamento();

            DepartamentoEnhanced objPers = (DepartamentoEnhanced) pm.getObjectById(idEnh,
                    true);
            
            UsuarioEnhancedPk usuarioEhnId = new UsuarioEnhancedPk();
            usuarioEhnId.idUsuario = usuario.getIdUsuario();
            UsuarioEnhanced usuEhn=(UsuarioEnhanced) pm.getObjectById(usuarioEhnId, true);
            objPers.setUsuario(usuEhn);
            if (objPers == null) {
                throw new DAOException("No existe el departamento con el id " +
                    dato.getIdDepartamento());
            }

            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        }

        catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            if (e instanceof SQLException) {
            	throw new DAOException ("Fallo en la integridad referencial para eliminar el departamento",e);
            }

            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.dao.ZonaRegistralDAO#eliminarMunicipio(gov.sir.core.negocio.modelo.Municipio)
     */
    public boolean eliminarMunicipio(Municipio dato) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        MunicipioEnhanced eliminada = MunicipioEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            MunicipioEnhancedPk idEnh = new MunicipioEnhancedPk();
            idEnh.idDepartamento = eliminada.getIdDepartamento();
            idEnh.idMunicipio = eliminada.getIdMunicipio();

            MunicipioEnhanced objPers = (MunicipioEnhanced) pm.getObjectById(idEnh,
                    true);

            if (objPers == null) {
                throw new DAOException("No existe el Municipio con el id " +
                    dato.getIdDepartamento() + " - " + dato.getIdMunicipio());
            }

            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.dao.ZonaRegistralDAO#eliminarVereda(gov.sir.core.negocio.modelo.Vereda)
     */
    public boolean eliminarVereda(Vereda dato) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        VeredaEnhanced eliminada = VeredaEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            VeredaEnhancedPk idEnh = new VeredaEnhancedPk();
            idEnh.idDepartamento = eliminada.getIdDepartamento();
            idEnh.idMunicipio = eliminada.getIdMunicipio();
            idEnh.idVereda = eliminada.getIdVereda();

            VeredaEnhanced objPers = (VeredaEnhanced) pm.getObjectById(idEnh,
                    true);

            if (objPers == null) {
                throw new DAOException("No existe la vereda con el id " +
                    dato.getIdDepartamento() + " - " + dato.getIdMunicipio() +
                    " - " + dato.getIdVereda());
            }

            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.dao.ZonaRegistralDAO#eliminarCirculo(gov.sir.core.negocio.modelo.Circulo)
     */
    public boolean eliminarCirculo(Circulo dato, Usuario usuario) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoEnhanced eliminada = CirculoEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            CirculoEnhancedPk idEnh = new CirculoEnhancedPk();
            idEnh.idCirculo = eliminada.getIdCirculo();

            CirculoEnhanced objPers = (CirculoEnhanced) pm.getObjectById(idEnh,
                    true);
            UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
    		usuarioEnhId.idUsuario = usuario.getIdUsuario();
    		
    		UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
		    objPers.setUsuarioModificacion(usuarioEnh);		
            if (objPers == null) {
                throw new DAOException("No existe el círculo con el id " +
                    dato.getIdCirculo());
            }
            pm.deletePersistentAll(objPers.getCirculoProcesos());
            
            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.dao.ZonaRegistralDAO#eliminarZonaRegistral(gov.sir.core.negocio.modelo.ZonaRegistral)
     */
    public boolean eliminarZonaRegistral(ZonaRegistral dato)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        ZonaRegistralEnhanced eliminada = ZonaRegistralEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            ZonaRegistralEnhancedPk idEnh = new ZonaRegistralEnhancedPk();
            idEnh.idZonaRegistral = eliminada.getIdZonaRegistral();

            ZonaRegistralEnhanced objPers = (ZonaRegistralEnhanced) pm.getObjectById(idEnh,
                    true);

            if (objPers == null) {
                throw new DAOException("No existe la zona registral con el id " +
                    dato.getIdZonaRegistral());
            }

            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.dao.ZonaRegistralDAO#eliminarEje(gov.sir.core.negocio.modelo.Eje, Usuario usuario)
     */
    public boolean eliminarEje(Eje dato, Usuario usuario) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EjeEnhanced eliminada = EjeEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            EjeEnhancedPk idEnh = new EjeEnhancedPk();
            idEnh.idEje = eliminada.getIdEje();

            EjeEnhanced objPers = (EjeEnhanced) pm.getObjectById(idEnh, true);

            if (objPers == null) {
                throw new DAOException("No existe el eje con el id " +
                    dato.getIdEje());
            }

            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.forseti.dao.ZonaRegistralDAO#eliminarCirculoFestivo(gov.sir.core.negocio.modelo.CirculoFestivo)
     */
    public boolean eliminarCirculoFestivo(CirculoFestivo dato)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        CirculoFestivoEnhanced eliminada = CirculoFestivoEnhanced.enhance(dato);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            CirculoFestivoEnhancedPk idEnh = new CirculoFestivoEnhancedPk();
            idEnh.fechaFestivo = eliminada.getFechaFestivo();
            idEnh.idCirculo = eliminada.getIdCirculo();

            CirculoFestivoEnhanced objPers = (CirculoFestivoEnhanced) pm.getObjectById(idEnh,
                    true);

            if (objPers == null) {
                throw new DAOException(
                    "No existe el Circulo festivo con el id " +
                    dato.getIdCirculo() + " - " + eliminada.getIdCirculo());
            }

            pm.deletePersistent(objPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * Retorna el archivo de la firma activa del círculo especificado
     * @param oid
     * @return
     * @throws DAOException
     */
    public FirmaRegistrador getFirmaRegistradorActiva(CirculoPk oid,
        String cargo) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        FirmaRegistradorEnhanced rta = null;
        FirmaRegistrador firma = null;

        try {
            //	Traer Objeto Persistente
            CirculoEnhanced objPers = this.getCirculo(new CirculoEnhancedPk(
                        oid), pm);

            if (objPers == null) {
                throw new DAOException("No existe el circulo con el ID: " +
                    oid.idCirculo);
            }

            String idCir = oid.idCirculo;

            Query query = pm.newQuery(FirmaRegistradorEnhanced.class);
            query.declareParameters("String idCir");
            String filtro = "this.idCirculo==idCir && this.activo=="+CFirmaRegistrador.ACTIVA +" ";
			if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO+"') )";
			}else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO+"') )";
			}else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO+"') )";
			}else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO+"') )";
			}else{
				throw new DAOException("El cargo es inválido: "+cargo);
			}

            query.setFilter(filtro);

            Collection col = (Collection) query.execute(idCir);
            Iterator iter = col.iterator();

            if (iter.hasNext()) {
                rta = (FirmaRegistradorEnhanced) iter.next();
                pm.makeTransient(rta);
            }

            query.closeAll();
        } catch (JDOException e) {
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        if (rta != null) {
            firma = (FirmaRegistrador) rta.toTransferObject();
        }

        return firma;
    }

    /**
     * Actualiza los datos del círculo.
     * @param cid
     * @param dato
     * @return
     * @throws DAOException
     */
    public boolean updateCirculo(CirculoPk cid, Circulo dato, Usuario usuario)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            CirculoEnhanced objPers = this.getCirculo(new CirculoEnhancedPk(
                        cid), pm);
            //Usuario que modifica el circulo
            UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
    		usuarioEnhId.idUsuario = usuario.getIdUsuario();
    		
    		UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);

            if (objPers == null) {
                throw new DAOException("No existe el círculo con el id " +
                    dato.getIdCirculo());
            }

            if(dato.isToUpdateCobroImpuesto()){
            	objPers.setCobroImpuesto(dato.isCobroImpuesto());
            }

            if(dato.getNombre()!=null){
            	objPers.setNombre(dato.getNombre());
            }

            if (dato.getNit() != null) {
                objPers.setNit(dato.getNit());
            }
            objPers.setUsuarioModificacion(usuarioEnh);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }



    /**
     * Actualiza los datos del texto.
     * @param cid
     * @param dato
     * @return
     * @throws DAOException
     */
    public boolean updateTexto(TextoPk cid, String nuevoTexto)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            //	Traer Objeto Persistente
            TextoEnhanced objPers = this.getTexto(new TextoEnhancedPk(
                        cid), pm);

            if (objPers == null) {
                throw new DAOException("No existe el texto con el id dado");
            }
            if(nuevoTexto!=null){
            	objPers.setTexto(nuevoTexto);
            }

            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }





	/**
	 * Obtiene una lista con todos las impresoras del círculo
	 * de manera transaccional
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.Eje
	 * @throws DAOException
	 */
	protected List getCirculoImpresoras(String idCirculo, PersistenceManager pm) throws DAOException {
		try {
			Query query = pm.newQuery(CirculoImpresoraEnhanced.class);
			query.setFilter("idCirculo=='"+idCirculo+"'");
			query.setOrdering("idImpresora ascending");

			List rta = (List) query.execute();

			return rta;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}


	/**
	 * Obtiene una lista con todas las impresoras del círculo
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public List getCirculoImpresoras(String idCirculo) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = null;

		try {
			rta = this.getCirculoImpresoras(idCirculo, pm);
			pm.makeTransientAll(rta);
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		rta = TransferUtils.makeTransferAll(rta);
		return rta;
	}


	/**
	 * Elimina la lista de impresoras configuradas para el círculo
	 * @param idCirculo
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarCirculoImpresoras(String idCirculo) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = null;

		try {
			pm.currentTransaction().begin();
			rta = this.getCirculoImpresoras(idCirculo, pm);
			pm.deletePersistentAll(rta);
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw e;
		} catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
		finally {
			pm.close();
		}

		return true;
	}



	/**
	 * Adiciona una lista de impresoras a la lista actual de impresoras del círculo
	 * @return lista de objetos CirculoImpresora con la lista total de impresoras
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public List addListaCirculoImpresoras(String idCirculo, List impresoras) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = null;

		try {
			CirculoEnhancedPk cid = new CirculoEnhancedPk();
			cid.idCirculo = idCirculo;

			CirculoEnhanced cir = this.getCirculo(cid, pm);

			if(cir == null){
				throw new DAOException("El círculo con el ID "+idCirculo+" no existe");
			}

			pm.currentTransaction().begin();
			for(Iterator it = impresoras.iterator(); it.hasNext();){
				String impresora = (String)it.next();
				CirculoImpresoraEnhanced cirImp = new CirculoImpresoraEnhanced();
				cirImp.setCirculo(cir);
				cirImp.setIdImpresora(impresora);
				pm.makePersistent(cirImp);
			}

			pm.currentTransaction().commit();

			rta = this.getCirculoImpresoras(idCirculo, pm);
			pm.makeTransientAll(rta);

		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw e;
		} catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
		 finally {
			pm.close();
		}

		rta = TransferUtils.makeTransferAll(rta);
		return rta;
	}


	/**
	 * Obtiene una lista con todas los tipos de imprimibles
	 * @return lista de objetos TipoImprimible
	 * @see gov.sir.core.negocio.modelo.TipoImprimible
	 * @throws DAOException
	 */
	public List getTiposImprimible() throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = null;

		try {
			rta = this.getTiposImprimible(pm);
			pm.makeTransientAll(rta);
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		rta = TransferUtils.makeTransferAll(rta);
		return rta;
	}



	/**
	 * Retorna la lista de impresoras por círculo y tipo de imprimible
	 * @param idCirculo
	 * @param idTipoImprimible
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected List getCirculoImpresorasXTipoImprimible(String idCirculo, String idTipoImprimible, PersistenceManager pm) throws DAOException {
		try {
			Query query = pm.newQuery(CirculoImpresoraEnhanced.class);
			query.setFilter("idCirculo=='"+idCirculo+"' && idTipoImprimible=='"+idTipoImprimible+"'");
			query.setOrdering("idImpresora ascending");

			List rta = (List) query.execute();

			return rta;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}


	/**
	 * Retorna la lista de impresoras por círculo y tipo de imprimible
	 * @param idCirculo
	 * @param idTipoImprimible
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	public List getCirculoImpresorasXTipoImprimible(String idCirculo, String idTipoImprimible) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List rta = null;
		try {
			rta = this.getCirculoImpresorasXTipoImprimible(idCirculo, idTipoImprimible, pm);
			pm.makeTransientAll(rta);
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		rta = TransferUtils.makeTransferAll(rta);
		return rta;
	}


	/**
	 * Obtiene una lista con todas las impresoras del círculo
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public Hashtable getConfiguracionImpresoras(String idCirculo) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable rta = new Hashtable();
		List tipos = null;
		try {
			tipos = this.getTiposImprimible();
			for(Iterator it = tipos.iterator(); it.hasNext();){
				TipoImprimible tipo = (TipoImprimible) it.next();
				List impresoras = this.getCirculoImpresorasXTipoImprimible(idCirculo, tipo.getIdTipoImprimible());
				rta.put(tipo, impresoras);
			}
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}
		return rta;
	}


	/**
	 * Obtiene una lista con todas las impresoras del círculo
	 * @return lista de objetos CirculoImpresora
	 * @see gov.sir.core.negocio.modelo.CirculoImpresora
	 * @throws DAOException
	 */
	public boolean setConfiguracionImpresoras(Hashtable nuevaConfiguracion, String idCirculo) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		TipoImprimible tipo;
		List impresoras;
		CirculoImpresora impresora;
		CirculoImpresoraEnhanced impresoraToCreate;
		try {
			pm.currentTransaction().begin();

			CirculoEnhancedPk cid = new CirculoEnhancedPk();
			cid.idCirculo = idCirculo;

			CirculoEnhanced cir = this.getCirculo(cid, pm);

			if(cir == null){
				throw new DAOException("El círculo con el ID "+idCirculo+" no existe");
			}

			List impresorasCirculo = this.getCirculoImpresoras(idCirculo, pm);
			pm.deletePersistentAll(impresorasCirculo);

			VersantPersistenceManager pm2 = (VersantPersistenceManager)pm;
			pm2.flush();

			Enumeration llaves = (Enumeration) nuevaConfiguracion.keys();

            while (llaves.hasMoreElements()) {
                tipo = (TipoImprimible) llaves.nextElement();
                impresoras = (List) nuevaConfiguracion.get(tipo);

                for (Iterator itr2 = impresoras.iterator(); itr2.hasNext();) {
                   impresora = (CirculoImpresora) itr2.next();
                   impresoraToCreate = CirculoImpresoraEnhanced.enhance(impresora);
                   impresoraToCreate.setCirculo(cir);
                   impresoraToCreate.setIdTipoImprimible(tipo.getIdTipoImprimible());
                   pm.makePersistent(impresoraToCreate);
                }
            }
            pm.currentTransaction().commit();
		}catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw e;
        } catch (Exception e) {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
        finally {
            pm.close();
        }
		return true;
	}




	/**
	 *
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected List getTiposImprimible(PersistenceManager pm) throws DAOException {
		try {
			Query query = pm.newQuery(TipoImprimibleEnhanced.class);
			query.setOrdering("nombre ascending");
			List rta = (List) query.execute();

			return rta;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}

	/**
	 * Cuenta los días NO hábiles configurados en el sistema desde la fecha inicial incluyéndola
	 * hasta la fecha final excluyéndola. Número Días no hábiles entre: [fehainicial, fechaFinal)
	 * @return long
	 * @throws DAOException
	 */
	public long countDiasNoHabiles(CirculoPk cirID, Date fechaInicial, Date fechaFinal) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		Long rta;

		try {
			CirculoEnhanced cirVal = this.getCirculo(new CirculoEnhancedPk(cirID), pm);

			if(cirVal==null){
				throw new DAOException("El círculo especificado NO existe");
			}

			VersantQuery query = (VersantQuery)pm.newQuery(CirculoFestivoEnhanced.class);
			query.declareParameters("String idCir, Date fechaFrom, Date fechaTo");
			query.setFilter("this.idCirculo == idCir &&\n"+
					"this.fechaFestivo>= fechaFrom &&\n"+
					"this.fechaFestivo< fechaTo");
			query.setResult("count(*)");
			rta = (Long)query.execute(cirVal.getIdCirculo(), fechaInicial, fechaFinal);
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		return rta.longValue();
	}

       /**
        * getCirculosByUsuario
        * @see gov.sir.forseti.interfaz.ForsetiServiceInterface#eliminarDeltaSegunAnotacionDefinitiva( gov.sir.core.negocio.modelo.Usuario.UsuarioPk )
        * @param oid ID
        * @return List
        */
	public List getCirculosByUsuario( gov.sir.core.negocio.modelo.UsuarioPk oid) throws DAOException {

	  PersistenceManager pm = AdministradorPM.getPM();

	  List rta = new ArrayList();
	  List aux = null;


          Long param_IdUsuario = new Long( oid.idUsuario );

	  try {

              CirculoEnhanced circuloenhanced;

              pm.currentTransaction().begin();
              Query query = pm.newQuery( CirculoEnhanced.class );
              query.declareVariables( "UsuarioCirculoEnhanced usuarioCirculo;" );
              query.declareParameters( "long param_IdUsuario" );
              query.setFilter(
                   "usuarioCirculo.idUsuario == param_IdUsuario "
                 + "&& usuarioCirculo.circulo == this"
              );
              Collection col = (Collection)query.execute( param_IdUsuario );
              for( Iterator iter = col.iterator(); iter.hasNext(); ) {
                  circuloenhanced = (CirculoEnhanced)iter.next();
                  this.makeTransientCirculo( circuloenhanced, pm );
                  rta.add( circuloenhanced );
              }
              query.closeAll();
              pm.currentTransaction().commit();

	  }
	  catch( DAOException e ) {
		  if (pm.currentTransaction().isActive()) {
              pm.currentTransaction().rollback();
          }
		 throw e;
	  } catch (Exception e) {
      	if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
        throw new DAOException(e.getMessage(), e);
	  }
	  finally {
		 pm.close();
	  }

	  rta = TransferUtils.makeTransferAll( rta );

	  return rta;

	} // end-method: getCirculosByUsuario

	public void inhabilitarCirculo(Circulo circuloOrigen, Circulo circuloDestino, List zonasRegistrales, Map usuariosCrear, List usuariosTrasladar, List usuariosRolConsulta, Usuario usuarioInhabilita)
			throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		JDOGenieFolioDAO folioDAO = new JDOGenieFolioDAO();
		OracleFenrirJDO fenrirDAO = new OracleFenrirJDO();
		
		
		try {
			DAOFactory fact = DAOFactory.getFactory();
			NivelDAO nivelDAO = fact.getNivelDAO();
			OracleFenrirLDAP ldap=new OracleFenrirLDAP();
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//1. CREAR EL OBJETO CIRCULO-TRASLADO Y CREAR EL CIRCULO SI ES NECESARIO
			CirculoEnhancedPk oidOrig = new CirculoEnhancedPk();
			oidOrig.idCirculo = circuloOrigen.getIdCirculo();
			CirculoEnhanced circEnh = (CirculoEnhanced) pm.getObjectById(oidOrig, true);
			
			 //Usuario que inhabilita el circulo
            UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
    		usuarioEnhId.idUsuario = usuarioInhabilita.getIdUsuario();
    		
    		UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
    		
    		circEnh.setUsuarioModificacion(usuarioEnh);
			//setear el campo activo en false
			circEnh.setActivo(false);
			pm.makePersistent(circEnh);

			//crear el círculo si es necesario
			CirculoEnhanced datos = CirculoEnhanced.enhance(circuloDestino);
			boolean crearCirculo = true;
			int anioInicial = Calendar.getInstance().get(Calendar.YEAR);

			CirculoEnhancedPk vid = new CirculoEnhancedPk();
			vid.idCirculo = datos.getIdCirculo();

			CirculoEnhanced vcir = this.getCirculo(vid, pm);

			if (vcir != null) {
				crearCirculo = false;
			}

			if (crearCirculo) {
				CirculoProcesoEnhanced cp = null;
				List procesos = this.getProcesos(pm);

				ProcesoEnhanced procTMP;

				for (Iterator itr = procesos.iterator(); itr.hasNext();) {
					procTMP = (ProcesoEnhanced) itr.next();

					for (int anio = anioInicial; anio < (anioInicial + ANIOS_CREACION_CIRCULO_PROCESO); anio++) {
						cp = new CirculoProcesoEnhanced();
						cp.setProceso(procTMP);
						cp.setLastIdTurno(0);
						cp.setCirculo(datos);
						cp.setAnio(String.valueOf(anio));
						datos.setUsuarioModificacion(usuarioEnh);
						datos.addCirculoProceso(cp);
					}
				}

				//TODO: NO TRANSACCIONAL
				nivelDAO.crearNivel(circuloDestino.getIdCirculo(), circuloDestino.getNombre());
				
				pm.makePersistent(datos);
				vcir = datos;
				
			}

			//crear la relación entre el circulo origen y el destino
			CirculoTrasladoEnhanced tras = new CirculoTrasladoEnhanced();
			tras.setIdTraslado(folioDAO.getAndUpdateLlavePrimaria(
                    CSecuencias.CIRCULO_TRASLADO, pm) + "");
			tras.setCirculoDestino(vcir);
			tras.setCirculoOrigen(circEnh);
			
			pm.makePersistent(tras);
			
			//2. ASOCIAR ZONAS REGISTRALES
			
			//2.1 DEL CIRCULO A INHABILITAR
			
			//se obtienen las zonas registrales del círculo a inhabilitar
			Query query = pm.newQuery( ZonaRegistralEnhanced.class );
            query.declareParameters( "String param_IdCirculo" );
            query.setFilter("circulo.idCirculo == param_IdCirculo " );
            Collection col = (Collection)query.execute( circuloOrigen.getIdCirculo() );
            for( Iterator iter = col.iterator(); iter.hasNext(); ) {
            	ZonaRegistralEnhanced zona=(ZonaRegistralEnhanced)iter.next();
                //se cambiar el circulo de la zona por el del círculo destino
                zona.setCirculo(vcir);
                pm.makePersistent(zona);
            }
            query.closeAll();
			
			//2.2 NUEVAS
			
			if (zonasRegistrales!=null){
				
				Iterator itZonas=zonasRegistrales.iterator();
				CirculoPk oidCirc=new CirculoPk();
				oidCirc.idCirculo=circuloDestino.getIdCirculo();
				
				while(itZonas.hasNext()){
					ZonaRegistral zonaT=(ZonaRegistral)itZonas.next();
					ZonaRegistralEnhanced zona = ZonaRegistralEnhanced.enhance(zonaT);
			        
			            //Se obtiene la posible llave de la zona registral
			        zona.setIdZonaRegistral(folioDAO.getAndUpdateLlavePrimaria(
			                    CSecuencias.ZONA_REGISTRAL, pm) + "");
					this.addZonaRegistralToCirculo(vcir,zona,pm);
					pm.makePersistent(zona);
					vcir.addZonaRegistral(zona);
				}	
			}
			
			//3. CREAR USUARIOS SI HAY
			
			if (usuariosCrear!=null){
				Iterator itUsuarios=usuariosCrear.keySet().iterator();
				while(itUsuarios.hasNext()){
					Usuario usuario=(Usuario)itUsuarios.next();
					List roles=(List)usuariosCrear.get(usuario);
					UsuarioCirculo uCir = new UsuarioCirculo();
					uCir.setCirculo(circuloDestino);
					usuario.addUsuarioCirculo(uCir);
					try {
						//TODO: NO TRANASCCIONAL
						fenrirDAO.crearUsuario(usuario,roles, pm, usuarioInhabilita);
						ldap.crearUsuario(usuario);
					} catch (gov.sir.fenrir.dao.DAOException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					} catch (AuthenticationException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					} catch (OperationNotSupportedException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					} catch (NamingException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					}	
				}	
			}
			
			//4. TRASLADAR USUARIOS SI HAY
			
			if (usuariosTrasladar!=null){
				Iterator itUsuariosTras=usuariosTrasladar.iterator();
				while(itUsuariosTras.hasNext()){
					Usuario usuario=(Usuario)itUsuariosTras.next();
					try {
						//TODO: NO TRANSACCIONAL
						fenrirDAO.trasladarUsuarioCirculo(usuario,circuloDestino,pm);
					} catch (gov.sir.fenrir.dao.DAOException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					}
				}
			}
			
			//5. CREAR ROLES DE CONSULTA
			
			if (usuariosRolConsulta!=null){
				Rol rolConsulta=new Rol();
				rolConsulta.setRolId(CRoles.SIR_ROL_CONSULTA_CIRCULO_INHABILITADO);
				Iterator itRol=usuariosRolConsulta.iterator();
				while(itRol.hasNext()){
					Usuario usuario=(Usuario)itRol.next();
					org.auriga.core.modelo.transferObjects.Usuario user=new org.auriga.core.modelo.transferObjects.Usuario();
					user.setUsuarioId(usuario.getUsername());
					try{
						List roles=fenrirDAO.darRolUsuario(usuario.getIdUsuario());
						if (roles!=null&&!roles.isEmpty()){
							Iterator itRoles=roles.iterator();
							boolean yaTieneRol=false;
							while(itRoles.hasNext()&&!yaTieneRol){
								Rol rol=(Rol)itRoles.next();
								if (rol.getRolId().equals(CRoles.SIR_ROL_CONSULTA_CIRCULO_INHABILITADO)){
									yaTieneRol=true;
								}
							}
							if (!yaTieneRol){
								//TODO: NO TRANSACCIONAL
								fenrirDAO.agregarRolUsuario(rolConsulta,user, usuarioInhabilita);	
							}
						}
					}catch (gov.sir.fenrir.dao.DAOException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					}catch (ErrorConexionException e) {
						Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
						throw new DAOException(e.getMessage(),e);
					}
				}
			}
			        
			pm.currentTransaction().commit();

		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        }catch (JDOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		} catch (ConfiguracionPropiedadesException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		} catch (org.auriga.core.modelo.daoObjects.DAOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	}

	public List getCirculosInhabilitados(Circulo circuloDestino) throws DAOException {

		  PersistenceManager pm = AdministradorPM.getPM();

		  List rta = new ArrayList();
		  List aux = null;
		  
		  try {
              Query query = pm.newQuery( CirculoTrasladoEnhanced.class );
              CirculoEnhancedPk oidCirc=new CirculoEnhancedPk();
              oidCirc.idCirculo=circuloDestino.getIdCirculo();
              CirculoEnhanced circuloDest=(CirculoEnhanced)pm.getObjectById(oidCirc,true);
              query.declareParameters( "CirculoEnhanced circuloDest" );
              query.setFilter("circuloDestino==circuloDest");
              Collection col = (Collection)query.execute(circuloDest);
              for( Iterator iter = col.iterator(); iter.hasNext(); ) {
            	  CirculoTrasladoEnhanced circuloenhanced = (CirculoTrasladoEnhanced)iter.next();
            	  pm.makeTransient(circuloenhanced.getCirculoDestino());
            	  pm.makeTransient(circuloenhanced.getCirculoOrigen());
            	  pm.makeTransient(circuloenhanced);
            	  rta.add( circuloenhanced );
              }
              query.closeAll();
		  } finally {
			 pm.close();
		  }

		  rta = TransferUtils.makeTransferAll( rta );
		  return rta;
		}

	/* (non-Javadoc)
	 * @see gov.sir.forseti.dao.ZonaRegistralDAO#trasladarFolios(gov.sir.core.negocio.modelo.Circulo, gov.sir.core.negocio.modelo.Circulo, int, int)
	 */
	public void trasladarFolios(Circulo circuloOrigen, Circulo circuloDestino, int trasladoMasivoInicio, int trasladoMasivoFin) throws DAOException {
		  CallableStatement cs = null;
          Connection connection = null;
		  PersistenceManager pm = AdministradorPM.getPM();
		  try {
  			  pm.currentTransaction().setOptimistic(false);
	  		  pm.currentTransaction().begin();
			  VersantPersistenceManager jdoPM = (VersantPersistenceManager)pm;
			  connection = jdoPM.getJdbcConnection(null);
			  //parametros: circuloOrigen, circuloDestino, numMatriculaInicialOrigen, numMatriculaInicialDestino, cantidad, errores, huecos
			  CirculoEnhancedPk oid=new CirculoEnhancedPk();
			  oid.idCirculo=circuloDestino.getIdCirculo();
			  CirculoEnhanced circDest = (CirculoEnhanced)pm.getObjectById(oid,true);
			  cs = connection.prepareCall("{call px_Sir_TrasladarCirculoFlio.Sv_Folio_Clone_Range(?,?,?,?,?,?,?)}");
			  cs.setString(1, circuloOrigen.getIdCirculo());
			  cs.setString(2, circuloDestino.getIdCirculo());
			  cs.setInt(3,trasladoMasivoInicio);
			  cs.setLong(4,circDest.getLastNoMatricula()+1);
			  cs.setLong(5,trasladoMasivoFin-trasladoMasivoInicio+1);
			  // 1-ignora excepciones, 0-no ignora excepciones
			  cs.setInt(6,1);
			  cs.setInt(7,1);
			  
			  cs.execute();
			  
			  //el último número de matrícula del círculo ahora se actualiza en el procedimiento almacenado
		  	  /*long numMatriculas=circDest.getLastNoMatricula();
		  	  circDest.setLastNoMatricula(numMatriculas+trasladoMasivoFin-trasladoMasivoInicio+1);
			  pm.makePersistent(circDest);*/
			  pm.currentTransaction().commit();
			  
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		}catch (SQLException e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException(e.getMessage(), e);
		} finally{
                try {
                    if(cs != null){
                        cs.close();
                    }
                    if(connection != null){
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            
			pm.close();
		}
	}
	
	/**
     * Obtiene un objeto Texto dado su identificador
     * @param oid identificador del circulo
     * @return objeto Circulo
     * @throws DAOException
     */
    public ActoresRepartoNotarial getActoresRepartoNotarial(ActoresRepartoNotarialPk oid) throws DAOException {
    	ActoresRepartoNotarialEnhanced ef = null;
        PersistenceManager pm = AdministradorPM.getPM();
        ActoresRepartoNotarial aux = null;

        try {
            ef = this.getActoresRepartoNotarial(new ActoresRepartoNotarialEnhancedPk(oid), pm);
            pm.makeTransient(ef);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (ef != null) {
            aux = (ActoresRepartoNotarial) ef.toTransferObject();
        }

        return aux;
    }
	
    /**
     * Obtiene un objeto Texto transaccional dado su identificador
     * @param oid identificador del circulo
     * @param pm PersistenceManager de la transaccion
     * @return objeto Circulo
     * @throws DAOException
     */
    protected ActoresRepartoNotarialEnhanced getActoresRepartoNotarial(ActoresRepartoNotarialEnhancedPk oid,
        PersistenceManager pm) throws DAOException {
    	ActoresRepartoNotarialEnhanced rta = null;

        if ((oid.idCirculo != null)) {
            try {
                rta = (ActoresRepartoNotarialEnhanced) pm.getObjectById(oid, true);
            } catch (JDOObjectNotFoundException e) {
                rta = null;
            } catch (JDOException e) {
                Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

    /**
     * @author      :   Fernando Padilla
     * @change      :   Se realiza modificaciones sobre el codgio de el metodo, ya que,
     *                  al realizar la consulta generaba error.
     * Caso Mantis  :   0007875
     */
    private String getIDVereda(String departamento, String municipio, PersistenceManager pm) throws DAOException{
    	String idVereda = null;
    	int aux;
        Connection        con = null;
        PreparedStatement pst = null;
        ResultSet          rs = null;
    	
    	try{
                VersantPersistenceManager pm2 = (VersantPersistenceManager) pm;
                con = pm2.getJdbcConnection(null);
                pst = con.prepareStatement("SELECT max(V.ID_VEREDA) FROM SIR.SIR_NE_VEREDA V WHERE V.ID_MUNICIPIO = ? and v.ID_DEPARTAMENTO = ?");
                pst.setString(1, municipio);
                pst.setString(2, departamento);
                rs = pst.executeQuery();
                if(rs.next()){
                    idVereda = rs.getString(1);
                }
        	if (idVereda==null){
        		aux = 0;
            	idVereda = Integer.toString(aux);
        	}else{
        		aux = Integer.parseInt(idVereda)+1;
            	idVereda = Integer.toString(aux);
        	}
        	if(aux < 100){
        		if(aux < 10)
        			idVereda = "0" + idVereda;
        		idVereda = "0" + idVereda;
        	}
    	} catch (SQLException ex) {
            Logger.getLogger(JDOGenieZonaRegistralDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch (JDOException e) {
    		Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
    		throw new DAOException(e.getMessage(), e);
    	}finally{
                try {
                    if(rs != null){
                        rs.close();
                    }
                    if(pst != null){
                        pst.close();
                    }

                    if(con != null){
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }
 
    	return idVereda;
    }
    
    private boolean existeNombre(VeredaEnhanced datos,PersistenceManager pm) throws DAOException{
    	boolean existe = false;
    	try{
    		Query query = pm.newQuery(VeredaEnhanced.class);
        	query.setFilter("nombre == '" + datos.getNombre() + "' && idDepartamento == '" + datos.getIdDepartamento() + "' && idMunicipio == '" + datos.getIdMunicipio() + "'");

        	List rta = (List) query.execute();
        	if(rta != null && rta.size()>0)
        		existe = true;
    	} catch (JDOException e) {
    		Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
    		throw new DAOException(e.getMessage(), e);
    	}
    	return existe;
    }
    
    
    
    
    
    /**
	 * Obtiene y avanza la secuencia de la tabla especificada 
	 * (TOMADO de FORSETTI)
	 * @param tableName Nombre de la tabla de la cual se quiere obtener
	 * el secuencial.
	 * @param pm Persistence Manager de la transacción.
	 * @return El secuencial requerido. 
	 * @throws DAOException
	 */
	protected long getSecuencial(String tableName, PersistenceManager pm) throws DAOException {

        Connection        con = null;
        PreparedStatement pst = null;
        ResultSet          rs = null;

        long rta = -1;
		boolean transaccionIndependiente = false;
		VersantPersistenceManager pm2 = null;
		HermodProperties hprop = HermodProperties.getInstancia();
        String secuencias = hprop.getProperty(HermodProperties.SECUENCIALES_POR_SECUENCIA);
        boolean usarSecuencia = false;
        if (secuencias != null){
        	String []secuenciasSplit = secuencias.split(";");
        	for (int i= 0; i< secuenciasSplit.length; i++){
        		if (secuenciasSplit[i].equals(tableName)){
        			usarSecuencia = true;
        			break;
        		}
        	}
        }
		if(pm==null)
			transaccionIndependiente = true;
		if (tableName != null) {
			try {
				if(transaccionIndependiente){
					pm = AdministradorPM.getPM();
					pm.currentTransaction().setOptimistic(false);					
					pm.currentTransaction().begin();
				}
				if (usarSecuencia){
					String sql = "SELECT SEC_" + (tableName.length() > 26 ? tableName.substring(0, 26) : tableName) + ".nextval FROM DUAL";
		        	pm2 = (VersantPersistenceManager) pm;
		        	con = pm2.getJdbcConnection(null);
		        	pst = con.prepareStatement(sql);
		        	rs  = pst.executeQuery();
		        	if (rs.next()){
		        		rta = rs.getLong(1);
		        	}else{
		        		throw new DAOException("No se encontró la secuencia");
		        	}

		        } else{
					//Se hace el cambio de tipo de bloqueo pesimista para que se bloquee la tabla la cual  nos
					//provee el secuencial
					pm2 = (VersantPersistenceManager) pm;
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
					pm = pm2;
					SecuenciasEnhancedPk sid = new SecuenciasEnhancedPk();
					sid.tableName = tableName;
					SecuenciasEnhanced s = this.getSecuenciaByID(sid, pm);
					s.setLastUsedId(s.getLastUsedId() + 1);
					//Volvemos a setear el tipo de bloqueo pesimista para que no nos bloquee los siquientes 
					//registros consultados
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
					rta = s.getLastUsedId(); // + 1;
		        }
				if(transaccionIndependiente)
					pm.currentTransaction().commit();
			} catch (JDOObjectNotFoundException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("No se encontró el registro de la secuencia", e);
			} catch (JDOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			} catch (DAOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			}catch (Exception e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			}
			finally{
                            pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                
                try {
                    if(rs != null){
                        rs.close();
                    }
                    if(pst != null){
                        pst.close();
                    }

                    if(con != null){
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
				if(transaccionIndependiente){
					if(pm!=null){
						pm.close();
					}
				}
			}
		}
		return rta;
	}
	
    public FirmaRegistrador getFirmaRegistradorByCirculoArchivo(String idCirculo, String idArchivo,
    												   PersistenceManager pm) throws DAOException {
            FirmaRegistradorEnhanced rta = null;
            FirmaRegistrador firma = null;
            try {
                Query query = pm.newQuery(FirmaRegistradorEnhanced.class);
                query.declareParameters("String idCirculo,String idArchivo");
                query.setFilter("this.idCirculo==idCirculo && this.idArchivo==idArchivo");
                Collection col = (Collection) query.execute(idCirculo,idArchivo);
                Iterator iter = col.iterator();
                if (iter.hasNext()) {
                    rta = (FirmaRegistradorEnhanced) iter.next();
                    pm.makeTransient(rta);
                }
                query.closeAll();
            } catch (JDOException e) {
            	Log.getInstance().error(JDOGenieZonaRegistralDAO.class, e.getMessage());
                throw new DAOException(e.getMessage(), e);
            } 
            if (rta != null) 
                firma = (FirmaRegistrador) rta.toTransferObject();
            return firma;
        }
    
    
}
