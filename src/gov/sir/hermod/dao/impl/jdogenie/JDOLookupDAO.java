/*
 * OracleHermodService.java
 *
 * Created on 12 de julio de 2004, 9:10
 */

package gov.sir.hermod.dao.impl.jdogenie;

import com.versant.core.jdo.VersantPersistenceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupCodesPk;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.OPLookupTypesPk;

import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;

import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoFotocopiaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.LookupDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


/**
 *
 * @author  mrios, mortiz
 */
public class JDOLookupDAO implements LookupDAO {
    
    /** Crea una nueva instacia de JDOLookupDAO */
    public JDOLookupDAO() {
    }

	/**
	* Obtiene el valor de una variable, dado su lookupType y su LookupCode
	* @param tipo El LookupType de la variable. 
	* @param codigo El LookupCode de la variable. 
	* @return El valor de la variable.
	* @throws DAOException
	*/
    public String getValor(String tipo, String codigo) throws DAOException {
        String valor = null;
        PersistenceManager pm = AdministradorPM.getPM();
        
        try {
            pm.currentTransaction().begin();
            OPLookupCodesEnhancedPk lcId = new OPLookupCodesEnhancedPk();
            lcId.codigo = codigo;
            lcId.tipo = tipo;
            OPLookupCodesEnhanced lc = (OPLookupCodesEnhanced) pm.getObjectById(lcId, true);
            pm.makeTransient(lc);
            valor = lc.getValor();
            pm.currentTransaction().commit();
        
        } catch (JDOObjectNotFoundException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
			throw new DAOException("No se encontro un valor para el tipo:" 
									+tipo+ " y el codigo:" + codigo, e);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
            pm.close();
        }
        return valor;
    }
    
    public List getValorLookupCodesByTipo(String tipo) throws DAOException{
            List valores = new ArrayList();
            String attempt = "";
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            VersantPersistenceManager jdoPM = null;
            String consulta = "SELECT ID_CODIGO,LKCD_VALOR FROM SIR_OP_LOOKUP_CODES " +
                             " WHERE ID_TIPO = '"+tipo+"'";

            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                ps = connection.prepareStatement(consulta);
                rs = ps.executeQuery();

                while (rs.next()) {
                OPLookupCodes codes = new OPLookupCodes();

                codes.setCodigo(rs.getString(1));
                codes.setValor(rs.getString(2));
                valores.add(codes);
                }   

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

            return valores;
    }

    
    /**
     * Obtiene el valor de la tabla de LookupCodes
     * @param tipo el identificador del tipo
     * @param codigo el identificador del codigo
     * @return el valor correspondiente, tipo String
     */ 
    public List getLookupCodes(String tipo) throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();
        
        try {
            pm.currentTransaction().begin(); 
            Query q = pm.newQuery(OPLookupCodesEnhanced.class);
            q.setFilter("tipo=='"+tipo+"'");
        
            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();
            while ( it.hasNext() ) {
                OPLookupCodesEnhanced obj = (OPLookupCodesEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }
            pm.currentTransaction().commit();
            q.close(results);
        } catch (JDOException e) {
        	if (pm.currentTransaction().isActive()){
        		pm.currentTransaction().rollback();
        	}
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
            lista = TransferUtils.makeTransferAll(lista);
        }
        return lista;
    }
    
	/**
     * Obtiene los Tipos de Documentos de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
	public List getTiposDocumento() throws DAOException {
		ArrayList lista = new ArrayList();
		String tipo_doc = COPLookupTypes.DOCUMENTO_IDENTIDAD;
		
		lista = (ArrayList) this.getLookupCodes(tipo_doc);
		
		return lista;
	}
        
        /**
     * Obtiene el tipo de documento de una persona juridica de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
        public List getTipoDocJuridico() throws DAOException {
		ArrayList lista = new ArrayList();
		String tipo_docJuridico = COPLookupTypes.DOCUMENTOS_IDENTIDAD_JURIDICA;
		
		lista = (ArrayList) this.getLookupCodes(tipo_docJuridico);
		
		return lista;
	}
        
        /**
     * Obtiene el tipo de documento de una persona natural de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
        public List getTipoDocNatural() throws DAOException {
		ArrayList lista = new ArrayList();
		String tipo_docNatural = COPLookupTypes.DOCUMENTOS_IDENTIDAD_NATURAL;
		
		lista = (ArrayList) this.getLookupCodes(tipo_docNatural);
		
		return lista;
	}
        
        /**
     * Obtiene las modalidades de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
        public List getModalidad() throws DAOException {
		ArrayList lista = new ArrayList();
		String modalidad = COPLookupTypes.MODALIDAD;
		
		lista = (ArrayList) this.getLookupCodes(modalidad);
		
		return lista;
	}
        
        /**
     * Obtiene las determinaciones del inmueble de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
        public List getDeterminacionInm() throws DAOException {
		ArrayList lista = new ArrayList();
		String determinacionInm = COPLookupTypes.DETERMINACION_INMUEBLE;
		
		lista = (ArrayList) this.getLookupCodes(determinacionInm);
		
		return lista;
	}
        
        /**
     * Obtiene los tipos de persona de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
        public List getTipoPersona() throws DAOException {
		ArrayList lista = new ArrayList();
		String tipos_persona = COPLookupTypes.TIPOS_PERSONA;
		
		lista = (ArrayList) this.getLookupCodes(tipos_persona);
		
		return lista;
	}
        
        /**
     * Obtiene los tipos de sexo de la tabla de LookupCodes
     * @return una lista de objetos LookupCode
     */ 
        public List getTipoSexo() throws DAOException {
		ArrayList lista = new ArrayList();
		String tipos_sexo = COPLookupTypes.TIPOS_SEXO;
		
		lista = (ArrayList) this.getLookupCodes(tipos_sexo);
		
		return lista;
	}

	/**
	 * Obtiene un objeto TipoFotocopia dado su identificador
	 * @param oid identificador del TipoFotocopia
	 * @return Objeto TipoFotocopia con su ID, fecha de creación y nombre
	 * null si no encuentra un tipoFotocopia que coincida con el identificador dado
	 * @throws DAOException
	*/
	protected TipoFotocopiaEnhanced getTipoFotocopiaByID(TipoFotocopiaEnhancedPk oid,
		PersistenceManager pm) throws DAOException {
		TipoFotocopiaEnhanced rta = null;

		if (oid.idTipoFotocopia != null) {
			try {
				rta = (TipoFotocopiaEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	 * Obtiene la lista de Lookup Types disponibles 
	 * @return una lista de objetos OLookupType
	 */  
	public List getLookupTypes() throws DAOException {
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		        
		try {
			pm.currentTransaction().begin();	
			Query q = pm.newQuery(OPLookupTypesEnhanced.class);
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();
    
			while ( it.hasNext() ){
				OPLookupTypesEnhanced obj = (OPLookupTypesEnhanced) it.next();
				List codes = obj.getOPLookupCodes();
				for (Iterator iter = codes.iterator(); iter.hasNext();) {
					OPLookupCodesEnhanced element = (OPLookupCodesEnhanced) iter.next();
					pm.makeTransient(element); 
				}
				pm.makeTransient(obj);
				lista.add(obj);
			}
				
			pm.currentTransaction().commit();    
			q.close(results);

		} catch (Throwable e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		} finally {
			pm.close();
		}
		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}
	
	
	/**
	* Adiciona un Lookup Type a la configuración del sistema
	* @param tipo objeto OPLookupTypes con sus atributos 
	* @return identificador del OPLookupTypes generado
	*/
	public OPLookupTypesPk addLookupType(OPLookupTypes tipo) throws DAOException {
		OPLookupTypesEnhanced look = OPLookupTypesEnhanced.enhance(tipo);
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			OPLookupTypesEnhancedPk lId = new OPLookupTypesEnhancedPk();
			lId.tipo = look.getTipo();
			OPLookupTypesEnhanced loop = this.getOPLookupTypesById(lId, pm);
			if (loop != null){
				throw new DAOException(
				"Ya existe un Look Type con el identificador: " + lId.tipo);
			}
			look.setFecha_creacion(new Date());

			pm.currentTransaction().begin();
			pm.makePersistent(look);
			pm.currentTransaction().commit();
			OPLookupTypesEnhancedPk rta = (OPLookupTypesEnhancedPk) pm.getObjectId(look);
			return rta.getLookupTypesID();
			
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw e;
        }
		 catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}	
	}
	
	/**
	* Edita un Lookup Type a la configuración del sistema
	* @param tipo objeto OPLookupTypes con sus atributos 
	* @return identificador del OPLookupTypes generado
	*/
	public OPLookupTypesPk updateLookupType(OPLookupTypes datoAEditar, OPLookupTypes dato) throws DAOException {
		
		OPLookupTypesEnhanced lookAEditar = OPLookupTypesEnhanced.enhance(datoAEditar);
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			OPLookupTypesEnhancedPk lId = new OPLookupTypesEnhancedPk();
			lId.tipo = lookAEditar.getTipo();
			OPLookupTypesEnhanced loop =(OPLookupTypesEnhanced) pm.getObjectById(lId, true);
			if (loop == null){
				throw new DAOException(
				"No existe un Look Type con el identificador: " + lId.tipo);
			}
			
			List lookUpCodes = null;
			if(!datoAEditar.getTipo().equals(dato.getTipo())){
				
				lookUpCodes = this.getLookupCodes(loop.getTipo());
				if(lookUpCodes!=null){
					throw new JDOException("No puede modificar el nombre de un LookUp Type, si tiene codes asociados");
				}
				
				//crear el nuevo tipo con los datos nuevos
				OPLookupTypesEnhanced look = OPLookupTypesEnhanced.enhance(dato);
				look.setFecha_creacion(loop.getFecha_creacion());
				pm.makePersistent(look);
				pm.deletePersistent(loop);
			}else{
				loop.setDescripcion(dato.getDescripcion());
			}

			pm.currentTransaction().commit();
			
			OPLookupTypesEnhancedPk lIdRta = new OPLookupTypesEnhancedPk();
			lIdRta.tipo = dato.getTipo();
			OPLookupTypesEnhanced rta = this.getOPLookupTypesById(lIdRta, pm);
			
			return new OPLookupTypesPk(rta.getTipo());
			
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw e;
        }
		 catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}	
	}

	/**
	* Adiciona un Lookup Code a la configuración del sistema
	* @param tipo objeto OPLookupCodes con sus atributos 
	* @return identificador del OPLookupCodes generado
	*/
	public OPLookupCodesPk addLookupCode(OPLookupCodes lcp) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		OPLookupCodesEnhanced look = OPLookupCodesEnhanced.enhance(lcp);

		try {
			OPLookupCodesEnhancedPk lId = new OPLookupCodesEnhancedPk();
			lId.tipo = look.getTipo();
			lId.codigo = look.getCodigo();
			OPLookupCodesEnhanced loop = this.getOPLookupCodesById(lId, pm);	
			if (loop != null){
				throw new DAOException(
				"Ya existe un Look Code con el identificador: " + lId.codigo +
				" para el Look Type " + lId.tipo);
			}
			
			OPLookupTypesEnhancedPk ltId = new OPLookupTypesEnhancedPk();
			ltId.tipo = look.getTipo();
			OPLookupTypesEnhanced loopType = this.getOPLookupTypesById(ltId, pm);
			if (loopType == null){
				throw new DAOException(
				"No existe el Look Type Asociado con el identificador: " 
				+ lId.tipo);
			}
			pm.currentTransaction().begin();
			pm.makePersistent(look);
			pm.currentTransaction().commit();
			
			OPLookupCodesEnhancedPk rta = (OPLookupCodesEnhancedPk) pm.getObjectId(look);
			return rta.getOPLookupCodesID();
			
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw e;
        }
		 catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}	
	}
	
	/**
	* Edita un Lookup Code a la configuración del sistema
	* @param tipo objeto OPLookupCodes que se va editar.
	* @param tipo objeto OPLookupCodes con los datos editados.
	* @return identificador del OPLookupCodes generado
	*/
	public OPLookupCodesPk updateLookupCode(OPLookupCodes datoAEditar, OPLookupCodes dato) throws DAOException {
		OPLookupCodesEnhancedPk lId = new OPLookupCodesEnhancedPk();
		lId.tipo = datoAEditar.getTipo();
		lId.codigo = datoAEditar.getCodigo();
		
		PersistenceManager pm = AdministradorPM.getPM();
        
		try 
		{
		
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
            //Se obtiene el LookupCode persistente.
			OPLookupCodesEnhanced loop = (OPLookupCodesEnhanced) pm.getObjectById(lId,true);
			
			if (loop == null)
			{
				throw new DAOException("No existe la variable con el identificador: " + lId.codigo +
				" para el Look Type " + lId.tipo);
			}
			
			loop.setValor(dato.getValor());
			loop.setDescripcion(dato.getDescripcion());
			pm.currentTransaction().commit();
			
			OPLookupCodesEnhancedPk rta = (OPLookupCodesEnhancedPk) pm.getObjectId(loop);
			return rta.getOPLookupCodesID();
		}
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
			    pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		catch (DAOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
			   pm.currentTransaction().rollback();
		    }
		   throw (e);
		}
		
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
		}
		
		finally 
		{
			pm.close();
		}
		
		return null;
	}
	
	/**
	* Obtiene un Lookup Codes dado su identificador, método utilizado para transacciones
	* @param lID identificador del OPLookupCodes
	* @param pm PersistenceManager de la transaccion
	* @return OPLookupCodes con sus atributos
	* @throws DAOException
	*/
	protected OPLookupCodesEnhanced getOPLookupCodesById(OPLookupCodesEnhancedPk lID,
		PersistenceManager pm) throws DAOException {
		OPLookupCodesEnhanced rta = null;

		if (lID.codigo != null && lID.tipo != null) {
			try {
				rta = (OPLookupCodesEnhanced) pm.getObjectById(lID, true);
				pm.makeTransient(rta);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}      
		return rta;
	}
		
	/**
	* Obtiene un ProcesoReparto dado su identificador, método utilizado para transacciones
	* @param uID identificador del usuario
	* @param pm PersistenceManager de la transaccion
	* @return Usuario con sus atributos
	* @throws DAOException
	*/
	protected OPLookupTypesEnhanced getOPLookupTypesById(OPLookupTypesEnhancedPk lID,
		PersistenceManager pm) throws DAOException {
		OPLookupTypesEnhanced rta = null;
	
		if (lID.tipo != null) {
			try {
				rta = (OPLookupTypesEnhanced) pm.getObjectById(lID, true);
				pm.makeTransient(rta);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}      
		return rta;
	}
	

	/**
	* Modifica el valor de una variable, dado su LookupType y su LookupCode
	* @param tipo El LookupType de la variable. 
	 @param codigo El LookupCode de la variable.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado
	* de la operación. 
	* @see gov.sir.core.negocio.modelo.OPLookupTypes
	* @see gov.sir.core.negocio.modelo.OPLookupCodes
	* @throws <code>DAOException</code>
	*/
	public boolean updateLookupCode (String tipo, String codigo, String nuevoValor) throws DAOException
	{
		OPLookupCodesEnhancedPk lId = new OPLookupCodesEnhancedPk();
		lId.tipo = tipo;
		lId.codigo = codigo;
		
		PersistenceManager pm = AdministradorPM.getPM();
        
		try 
		{
		
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
            //Se obtiene el LookupCode persistente.
			OPLookupCodesEnhanced loop = (OPLookupCodesEnhanced) pm.getObjectById(lId,true);
			
			if (loop == null)
			{
				throw new DAOException("No existe la variable con el identificador: " + lId.codigo +
				" para el Look Type " + lId.tipo);
			}
			
			loop.setValor(nuevoValor);
			pm.currentTransaction().commit();
			
		}
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
			    pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		catch (DAOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
			   pm.currentTransaction().rollback();
		    }
		   throw (e);
		}
		
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
		}
		
		finally 
		{
			pm.close();
		}
		
		return true;
	}
public List getLookupCodes(String tipo, PersistenceManager pm) throws DAOException {
        List lista = new ArrayList();

        try {
            Query q = pm.newQuery(OPLookupCodesEnhanced.class);
            q.setFilter("tipo=='"+tipo+"'");

            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();
            while ( it.hasNext() ) {
                OPLookupCodesEnhanced obj = (OPLookupCodesEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }
            q.close(results);
        } catch (JDOException e) {
        	Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
        } finally {
            lista = TransferUtils.makeTransferAll(lista);
        }
        return lista;
    }
    
    public String getValorLookupCodes(String tipo, String idCodigo) throws DAOException{
            String attempt = "";
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            VersantPersistenceManager jdoPM = null;
            String consulta = "SELECT LKCD_VALOR FROM SIR_OP_LOOKUP_CODES " +
                             " WHERE ID_TIPO = '"+tipo+"' AND ID_CODIGO='"+idCodigo+"'";

            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                ps = connection.prepareStatement(consulta);
                rs = ps.executeQuery();

                if(rs.next()){
                    attempt = (String) rs.getString(1);
                }

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

            return attempt;
    }
    /**
     * Obtiene un objeto lookup dependiendo el tipo
     * @param tipo
     * @return
     * @throws DAOException 
     */
    public List getOPLookupCodesByTipo(String tipo) throws DAOException{
            List valores = new ArrayList();
            String attempt = "";
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            VersantPersistenceManager jdoPM = null;
            String consulta = "SELECT * FROM SIR_OP_LOOKUP_CODES " +
                             " WHERE ID_TIPO = '"+tipo+"'";

            try {
                jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

                jdoPM.currentTransaction().begin();
                connection = jdoPM.getJdbcConnection(null);
                ps = connection.prepareStatement(consulta);
                rs = ps.executeQuery();

                while (rs.next()) {
                OPLookupCodes codes = new OPLookupCodes();
                
                codes.setTipo(rs.getString("ID_TIPO"));
                codes.setCodigo(rs.getString("ID_CODIGO"));
                codes.setValor(rs.getString("LKCD_VALOR"));
                codes.setDescripcion(rs.getString("DESCRIPCION"));
                valores.add(codes);
                }   

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

            return valores;
    }
}


