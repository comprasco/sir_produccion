/*
 * Created on 10-feb-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.forseti.dao.impl.jdogenie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.CiudadanoProhibicionPk;
import gov.sir.core.negocio.modelo.ProhibicionPk;
import gov.sir.core.negocio.modelo.Usuario; 
import gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoTMP;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoProhibicionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProhibicionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.dao.CiudadanoDAO;
import gov.sir.forseti.dao.DAOException;
import gov.sir.hermod.HermodProperties;

import com.versant.core.jdo.VersantPersistenceManager;
import java.sql.SQLException;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JDOGenieCiudadanoDAO implements CiudadanoDAO{
	/**
	* Obtiene un ciudadano dado su documento de identificacion y el tipo de documento,
	* método utilizado para transacciones
	* @param tipodoc Tipo del documento de Identificacion
	 * @param doc Documento de identificacion del ciudadano
	 * @param pm  PersistenceManager de la trnsaccion
	 * @param idCirculo TODO
	* @return Ciudadano con sus atributos
	* @throws DAOException
	*/
	protected CiudadanoEnhanced getCiudadanoByDocumento(String tipodoc,
		String doc, boolean solicitante, PersistenceManager pm, String idCirculo) throws DAOException {
		CiudadanoEnhanced rta = null;
		String Tipo = tipodoc;
		String Documento = doc;
		Boolean sol = new Boolean(solicitante);

		try {
			Query query = pm.newQuery(CiudadanoEnhanced.class);

			//El query debe tener en cuenta lo que hay en cache
			query.setIgnoreCache(false);
			query.declareParameters("String Tipo, String Documento, Boolean sol, String idCir");
			String filtro = "tipoDoc == Tipo && documento == Documento && idCirculo==idCir";

			if(!solicitante){
				filtro+=" && (solicitante==sol || solicitante==null)";
			}
			else{
				filtro+=" && solicitante==sol";
			}
			query.setFilter(filtro);
			Collection col = (Collection) query.executeWithArray(new Object[]{Tipo, Documento, sol, idCirculo});

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
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}
	
	/**
	* Obtiene un ciudadano TMP dado su documento de identificacion y el tipo de documento,
	* método utilizado para transacciones
	* @param tipodoc Tipo del documento de Identificacion
	 * @param doc Documento de identificacion del ciudadano
	 * @param pm  PersistenceManager de la trnsaccion
	 * @param idCirculo TODO
	* @return Ciudadano con sus atributos
	* @throws DAOException
	*/
	protected CiudadanoTMP getCiudadanoTmpByDocumento(String tipodoc,
		String doc, boolean solicitante, PersistenceManager pm, String idCirculo) throws DAOException {
		CiudadanoTMP rta = null;
		String Tipo = tipodoc;
		String Documento = doc;
		Boolean sol = new Boolean(solicitante);

		try {
			Query query = pm.newQuery(CiudadanoTMP.class);

			//El query debe tener en cuenta lo que hay en cache
			query.setIgnoreCache(false);
			query.declareParameters("String Tipo, String Documento, Boolean sol, String idCir");
			String filtro = "tipoDocumento == Tipo && documento == Documento && idCirculo==idCir";

			if(!solicitante){
				filtro+=" && (solicitante==sol || solicitante==null)";
			}
			else{
				filtro+=" && solicitante==sol";
			}
			query.setFilter(filtro);
			Collection col = (Collection) query.executeWithArray(new Object[]{Tipo, Documento, sol, idCirculo});

			if (col.size() == 0) {
				rta = null;
			} else {
				for (Iterator iter = col.iterator(); iter.hasNext();) {
					rta = (CiudadanoTMP) iter.next();
				}

				query.closeAll();
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}




	/**
	* Obtiene un ciudadano dado su documento de identificacion y el tipo de documento,
	* método utilizado para transacciones
	* @param tipodoc Tipo del documento de Identificacion
	* @param doc Documento de identificacion del ciudadano
	* @param pm  PersistenceManager de la trnsaccion
	* @return Ciudadano con sus atributos
	* @throws DAOException
	*/
	protected CiudadanoTMP getCiudadanoByDocumentoTMP(String tipodoc,
		String doc, boolean solicitante, PersistenceManager pm, String idCirculo) throws DAOException {
		CiudadanoTMP rta = null;
		String Tipo = tipodoc;
		String Documento = doc;
		Boolean sol = new Boolean(solicitante);

		try {
			if(idCirculo==null){
				throw new DAOException("No se puede obtener el ciudadano puesto que no tiene un círculo asociado");
			}
			
			Query query = pm.newQuery(CiudadanoTMP.class);

			//El query debe tener en cuenta lo que hay en cache
			query.setIgnoreCache(false);
			query.declareParameters("String Tipo, String Documento, Boolean sol, String idCir");
			String filtro = "tipoDocumento == Tipo && documento == Documento && idCirculo==idCir";

			if(!solicitante){
				filtro+="&& (solicitante==sol || solicitante==null)";
			}
			else{
				filtro+="&& solicitante==sol";
			}
			query.setFilter(filtro);
			Collection col = (Collection) query.executeWithArray(new Object[]{Tipo, Documento, sol, idCirculo});

			if (col.size() == 0) {
				rta = null;
			} else {
				for (Iterator iter = col.iterator(); iter.hasNext();) {
					rta = (CiudadanoTMP) iter.next();
				}

				query.closeAll();
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}
	
	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @return
	 * @throws DAOException
	 */
	public CiudadanoTMP getCiudadanoTmpByDocumento(String tipodoc, String doc, String idCirculo)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		CiudadanoTMP rta = null;
		//String Tipo = tipodoc;
		//String Documento = doc;
		//CiudadanoEnhanced ciud = null;

		try {
			rta = this.getCiudadanoTmpByDocumento(tipodoc, doc, false, pm, idCirculo);

			if (rta != null) {
				pm.makeTransient(rta);
			}
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		} finally {
			pm.close();
		}

		/*if (ciud != null) {
			rta = (CiudadanoTMP) ciud.toTransferObject();
		}*/

		return rta;
	}



	/**
	* Busca una prohibición en una transacción
	* @param oid identificador de la complementación
	* @param pm PersistenceManager
	* @return objeto ProhibicionEnhanced, si no existe retorna null
	* @throws DAOException
	*/
	protected ProhibicionEnhanced getProhibicion(
		ProhibicionEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		ProhibicionEnhanced rta = null;

		if (oid.idProhibicion != null) {
			try {
				rta = (ProhibicionEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
	* Obtiene una lista de prohibiciones de la configuración del sistema
	* @return lista de objetos EstadoAnotacion
	* @see gov.sir.core.negocio.modelo.EstadoAnotacion
	* @throws DAOException
	*/
	protected List getProhibiciones(PersistenceManager pm)
		throws DAOException {
		try {
			Query query = pm.newQuery(ProhibicionEnhanced.class);
			query.setOrdering("idProhibicion ascending");

			List rta = (List) query.execute();

			return rta;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}



	/**
	* Obtiene una lista con todos las prohibiciones de ciudadano
	* configurados en el sistema
	* @return lista de objetos EstadoFolio
	* @see gov.sir.core.negocio.modelo.EstadoFolio
	* @throws DAOException
	*/
	public List getProhibiciones() throws DAOException {
		List rta = null;
		PersistenceManager pm = AdministradorPM.getPM();

		try {
			rta = this.getProhibiciones(pm);
			pm.makeTransientAll(rta);
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		return TransferUtils.makeTransferAll(rta);
	}



	/**
	* Adiciona una Prohibicion a la configuración del sistema
	* @param datos objeto Prohibicion con sus atributos exceptuando su identificador
	* el cual es generado por el sistema
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk addProhibicion(Prohibicion datos)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		ProhibicionEnhanced estado = ProhibicionEnhanced.enhance(datos);

		try {
			//Validación de identificador del EstadoAnotacion
			ProhibicionEnhancedPk eid = new ProhibicionEnhancedPk();
			eid.idProhibicion = estado.getIdProhibicion();

			ProhibicionEnhanced vcir = this.getProhibicion(eid, pm);

			if (vcir != null) {
				throw new DAOException(
					"Ya existe una prohibición con el identificador: " +
					eid.idProhibicion);
			}

			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			pm.makePersistent(estado);
			pm.currentTransaction().commit();

			ProhibicionEnhancedPk rta = (ProhibicionEnhancedPk) pm.getObjectId(estado);

			return rta.getProhibicionID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}




	/**
	* Edita una Prohibicion en la configuración del sistema
	* @param datos objeto Prohibicion con sus atributos incluido su identificador
	* @return identificador de Prohibicion generado
	*/
	public ProhibicionPk editarProhibicion(Prohibicion datos)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		ProhibicionEnhanced estado = ProhibicionEnhanced.enhance(datos);

		try {
			
			//Obtener la prohibición persistente.
			ProhibicionEnhancedPk eid = new ProhibicionEnhancedPk();
			eid.idProhibicion = estado.getIdProhibicion();

			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			ProhibicionEnhanced vcir = this.getProhibicion(eid, pm);

			
			//Si no se encuentra la prohibición con el identificador dado, se genera 
			//una excepción. 
			if (vcir == null) {
				throw new DAOException(
					"No existe una prohibición con el identificador: " +
					eid.idProhibicion);
			}

            //Se actualizan los atributos modificados en el objeto persistente.
            vcir.setDescripcion(estado.getDescripcion());
            vcir.setNombre(estado.getNombre());
           
			pm.currentTransaction().commit();

			ProhibicionEnhancedPk rta = (ProhibicionEnhancedPk) pm.getObjectId(vcir);

			return rta.getProhibicionID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
            throw e;
        }
		 catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}





	/**
	* Agrega una prohibición a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibición
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws DAOException
	*/
	public CiudadanoProhibicionPk addProhibicionToCiudadano(CiudadanoPk oid, ProhibicionPk pid, CirculoPk cirid, String comentario, Usuario usuario)
		throws DAOException {
		CiudadanoProhibicionEnhancedPk rta = new CiudadanoProhibicionEnhancedPk();
		CiudadanoEnhanced ciud = new CiudadanoEnhanced();
		CirculoEnhanced cir = new CirculoEnhanced();
		ProhibicionEnhanced proh = new ProhibicionEnhanced();
		PersistenceManager pm = AdministradorPM.getPM();


		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			ciud = this.getCiudadano(new CiudadanoEnhancedPk(oid), pm);
			cir = this.getCirculo(new CirculoEnhancedPk(cirid),pm);
			if (ciud == null) {
				throw new DAOException("El ciudadano especificado no existe");
			}

			proh = this.getProhibicion(new ProhibicionEnhancedPk(pid), pm);

			if(proh==null) {
				throw new DAOException("La prohibición especificada no existe");
			}

			if(this.hasProhibicionActiva(ciud, proh.getIdProhibicion(), pm)){
				throw new DAOException("El ciudadano ya tiene activa la prohibición especificada");
			}

			CiudadanoProhibicionEnhanced ciudProh = new CiudadanoProhibicionEnhanced();
			
			UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario = usuario.getIdUsuario();
			
			UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);
			ciudProh.setIdCirculo(cir.getIdCirculo());
			ciudProh.setFechaInicial(new Date());
			ciudProh.setComentario(comentario);
			ciudProh.setCiudadano(ciud);
			ciudProh.setProhibicion(proh);
			ciudProh.setFechaCreacion(new Date());
			ciudProh.setUsuarioCreacion(usuarioEnh);
			pm.makePersistent(ciudProh);

			//Se notifica a la lista
			ciud.addProhibicione(ciudProh);
			pm.currentTransaction().commit();
			rta = (CiudadanoProhibicionEnhancedPk) pm.getObjectId(ciudProh);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw e;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		finally {
			pm.close();
		}

		return rta.getCiudadanoProhibicionID();
	}




	/**
	* Obtiene un EstadoFolio dado su identificador, método usado en transacciones
	* se debe dar el PersistenceManager
	* @param oid
	* @return objeto EstadoFolio
	* @throws DAOException
	*/
	protected CiudadanoEnhanced getCiudadano(CiudadanoEnhancedPk oid,
		PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced rta = null;

		if (oid.idCiudadano != null) {
			try {
				rta = (CiudadanoEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un Circulo dado su identificador, método usado en transacciones
	* se debe dar el PersistenceManager
	* @param oid
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
				Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	/**
	* Obtiene un EstadoFolio dado su identificador, método usado en transacciones
	* se debe dar el PersistenceManager
	* @param oid
	* @return objeto EstadoFolio
	* @throws DAOException
	*/
	protected CiudadanoTMP getCiudadanoTMP(CiudadanoTMPPk oid,
		PersistenceManager pm) throws DAOException {
		CiudadanoTMP rta = null;

		if (oid.idCiudadanoTmp != null) {
			try {
				rta = (CiudadanoTMP) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	/**
	* Obtiene un Ciudadano dado su identificador
	* @param oid
	* @return objeto EstadoFolio
	* @throws DAOException
	*/
	public Ciudadano getCiudadano(CiudadanoPk oid)
		throws DAOException {
		CiudadanoEnhanced ef = null;
		PersistenceManager pm = AdministradorPM.getPM();
		Ciudadano aux = null;

		try {
			ef = this.getCiudadano(new CiudadanoEnhancedPk(oid), pm);
			pm.makeTransient(ef);
		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		if (ef != null) {
			aux = (Ciudadano) ef.toTransferObject();
		}

		return aux;
	}



	/**
	* Obtiene un Ciudadano dado su identificador
	* @param oid
	* @return objeto EstadoFolio
	* @throws DAOException
	*/
	public Ciudadano getCiudadano(CiudadanoPk oid, Usuario usuario)
		throws DAOException{
		return null;
	}



	/**
	 * Elimina una prohibición configurada en el sistema
	 * @param dato
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarProhibicion(Prohibicion dato)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		//ProhibicionEnhanced eliminada = ProhibicionEnhanced.enhance(dato);

		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			//	Traer Objeto Persistente
			ProhibicionEnhancedPk idEnh = new ProhibicionEnhancedPk();
			idEnh.idProhibicion = dato.getIdProhibicion();

			ProhibicionEnhanced objPers = (ProhibicionEnhanced) pm.getObjectById(idEnh,
					true);

			if (objPers == null) {
				throw new DAOException(
					"No existe la prohibicion con el id " +
					dato.getIdProhibicion());
			}

			pm.deletePersistent(objPers);
			pm.currentTransaction().commit();
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return true;
	}



	/**
	* Agrega una prohibición a un ciudadano
	* @param oid Identificador del ciudadano
	* @param pid Identificador de la prohibición
	* @param pm PersistenceManager
	* @return identificador del CiudadanoProhibicion
	* @throws DAOException
	*/
	public boolean desactivarProhibicionToCiudadano(CiudadanoProhibicionPk pid, Usuario usuario,String comentarioAnulacion)
		throws DAOException {
		boolean rta = true;
		CiudadanoProhibicionEnhanced ciudProh = new CiudadanoProhibicionEnhanced();
		PersistenceManager pm = AdministradorPM.getPM();


		try {
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();

			ciudProh = this.getCiudadanoProhibicion(new CiudadanoProhibicionEnhancedPk(pid), pm);


			if(ciudProh==null) {
				throw new DAOException("No encontró la relación del ciudadano con la prohibición");
			}
			
			UsuarioEnhancedPk usuarioEnhId = new UsuarioEnhancedPk();
			usuarioEnhId.idUsuario = usuario.getIdUsuario();
			
			UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(usuarioEnhId, true);

			ciudProh.setFechaFinal(new Date());
			ciudProh.setUsuarioEliminacion(usuarioEnh);
			ciudProh.setComentarioAnulacion(comentarioAnulacion);
			pm.currentTransaction().commit();
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw e;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}

		return rta;
	}



	/**
	* Obtiene un EstadoFolio dado su identificador, método usado en transacciones
	* se debe dar el PersistenceManager
	* @param oid
	* @return objeto EstadoFolio
	* @throws DAOException
	*/
	protected CiudadanoProhibicionEnhanced getCiudadanoProhibicion(CiudadanoProhibicionEnhancedPk oid,
		PersistenceManager pm) throws DAOException {
		CiudadanoProhibicionEnhanced rta = null;

		if ((oid.idCiudadano != null)&&(oid.idProhibicion!=null)&&(oid.fechaInicial!=null)) {
			try {
				rta = (CiudadanoProhibicionEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	/**
	 * Valida si el usuario persistente insertado tiene una prohibicion de tipo
	 * idProh activa
	 * @param ciud Ciudadano persistente
	 * @param idProh identificador de la prohibicion a validar
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected boolean hasProhibicionActiva(CiudadanoEnhanced ciud, String idProh, PersistenceManager pm)
		throws DAOException {
		boolean rta = false;
		try {
			Query query = pm.newQuery(CiudadanoProhibicionEnhanced.class);
			query.setIgnoreCache(true);
			query.declareParameters("CiudadanoEnhanced ciud, String idProh");
			query.setFilter("this.ciudadano==ciud &&\n"+
					"this.idProhibicion==idProh &&\n"+
					"this.fechaFinal==null");
			Collection col = (Collection)query.execute(ciud, idProh);
			Iterator iter = col.iterator();
			if (iter.hasNext()) {
				rta = true;
			}
			query.closeAll();
			return rta;
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}



	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @return
	 * @throws DAOException
	 */
	public Ciudadano getCiudadanoByDocumento(String tipodoc, String doc, String idCirculo)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		Ciudadano rta = null;
		//String Tipo = tipodoc;
		//String Documento = doc;
		CiudadanoEnhanced ciud = null;

		try {
			ciud = this.getCiudadanoByDocumento(tipodoc, doc, false, pm, idCirculo);

			if (ciud != null) {
				CiudadanoProhibicionEnhanced cpe;

				for (Iterator it = ciud.getProhibiciones().iterator();
						it.hasNext();) {
					cpe = (CiudadanoProhibicionEnhanced) it.next();
					pm.makeTransient(cpe.getProhibicion());
					pm.makeTransient(cpe);
				}

				pm.makeTransientAll(ciud.getProhibiciones());
				pm.makeTransient(ciud);
			}
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		} finally {
			pm.close();
		}

		if (ciud != null) {
			rta = (Ciudadano) ciud.toTransferObject();
		}

		return rta;
	}




	/**
	 * Obtiene el ciudadano dado su tipo y numero de documento.
	 * Si el ciudadano no existe en el sistema retorna null
	 * @param tipodoc
	 * @param doc
	 * @return
	 * @throws DAOException
	 */
	public Ciudadano getCiudadanoByDocumentoSolicitante(String tipodoc, String doc, String idCirculo)
		throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		Ciudadano rta = null;
		//String Tipo = tipodoc;
		//String Documento = doc;
		CiudadanoEnhanced ciud = null;

		try {
			//Se busca el ciudadano dentro de los ciudadanos oficiales
			//en anotaciones
			ciud = this.getCiudadanoByDocumento(tipodoc, doc, false, pm, idCirculo);

			//Si no lo encuentra se busca dentro de los ciudadanos
			//solicitantes
			if(ciud==null){
				ciud = this.getCiudadanoByDocumento(tipodoc, doc, true, pm, idCirculo);
			}

			if (ciud != null) {
				CiudadanoProhibicionEnhanced cpe;

				for (Iterator it = ciud.getProhibiciones().iterator();
						it.hasNext();) {
					cpe = (CiudadanoProhibicionEnhanced) it.next();
					pm.makeTransient(cpe.getProhibicion());
					pm.makeTransient(cpe);
				}

				pm.makeTransientAll(ciud.getProhibiciones());
				pm.makeTransient(ciud);
			}
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		} finally {
			pm.close();
		}

		if (ciud != null) {
			rta = (Ciudadano) ciud.toTransferObject();
		}

		return rta;
	}



	/**
	 * Crea un ciudadano en el sistema, método utilizado para transacciones
	 * @param  c Ciudadano con sus atributos, exceptuando el identificador
	 * @param pm PersistenceManager de la transaccion
	 * @return un Ciudadano persistente con su identificador.
	 * @throws DAOException
	 */
	protected CiudadanoEnhanced crearCiudadano(CiudadanoEnhanced c,
		PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced rta = null;

		try {
			//Se valida que el ciudadano tenga círculo
			if(c.getIdCirculo()==null){
				throw new DAOException("El ciudadano que se está intentando insertar no tiene círculo asociado");
			}
			
			//Generar el número de documento del ciudadano.
			long idCiudadano = this.getSecuencial("SIR_NE_CIUDADANO", null);

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
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
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
		long rta = -1;
		
		boolean transaccionIndependiente = false;
		VersantPersistenceManager pm2 = null;
		
        PreparedStatement pst = null;
        ResultSet          rs = null;
        Connection        con = null;

        HermodProperties hprop = HermodProperties.getInstancia();
        String secuencias = hprop.getProperty(
                    HermodProperties.SECUENCIALES_POR_SECUENCIA);
        boolean usarSecuencia = false;
        
        if (secuencias != null)
        {
        	String []secuenciasSplit = secuencias.split(";");
        	for (int i= 0; i< secuenciasSplit.length; i++)
        	{
        		if (secuenciasSplit[i].equals(tableName))
        		{
        			usarSecuencia = true;
        			break;
        		}
        	}
        }

        
		if(pm==null){
			transaccionIndependiente = true;
		}
		
		if (tableName != null) {
			try {
				if(transaccionIndependiente){
					pm = AdministradorPM.getPM();
					pm.currentTransaction().setOptimistic(false);					
					pm.currentTransaction().begin();
				}
				
				if (usarSecuencia)
		        {
					String sql = "SELECT SEC_" + (tableName.length() > 26 ? tableName.substring(0, 26) : tableName) + ".nextval FROM DUAL";
		        	
		        	pm2 = (VersantPersistenceManager) pm;
		        	con = pm2.getJdbcConnection(null);
		        	
		        	pst = con.prepareStatement(sql);
		        	
		        	rs = pst.executeQuery();
                    
		        	if (rs.next())
		        	{
		        		rta = rs.getLong(1);
		        	} else
		        	{
		        		throw new DAOException("No se encontró la secuencia");
		        	}
		        	
		        } else 
			        {
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

				if(transaccionIndependiente){
					pm.currentTransaction().commit();
				}
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
			} catch (Exception e) {
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
                try{
                    if(rs != null){
                        rs.close();
                    }
                    if(pst != null){
                        pst.close();
                    }
                    if(con != null){
                        con.close();
                    }
                }catch(SQLException e){
                    
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


	/**
	* Obtiene una secuencia dado su identificador, método utilizado para transacciones
	* @param sID identificador de la secuencia
	* @param pm PersistenceManager de la transaccion
	* @return Secuencia con sus atributos
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
				Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}




	/**
	 * Actualiza un ciudadano en el modelo temporal
	 * @param ciud
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCiudadano(Ciudadano ciud, Usuario usuario, String numRadicacion)
	throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		try {
			pm.currentTransaction().begin();
			CiudadanoEnhancedPk cID = new CiudadanoEnhancedPk();
			cID.idCiudadano = ciud.getIdCiudadano();

			//Se busca el ciudadano con el ID dado
			CiudadanoEnhanced ciudDefToUpdate = this.getCiudadano(cID, pm);

			if(ciudDefToUpdate==null){
				throw new DAOException("El ciudadano que se intenta modificar NO existe, ID: "+ciud.getIdCiudadano());
			}

			//Se revisa los folios que tengan asociado al ciudadano y si se encuentran bloqueados
			//por el usuario especificado
			List anotas = folDAO.getAnotacionesQueRelacionanCiudadanoDefinitivas(ciudDefToUpdate, pm);

			//FolioEnhanced fol;
			AnotacionCiudadanoEnhanced anotaCiud;
			List folios = new ArrayList();
			String mat = null;
			for(Iterator it = anotas.iterator(); it.hasNext();){
				anotaCiud = (AnotacionCiudadanoEnhanced) it.next();
				if(!anotaCiud.getIdMatricula().equals(mat)){
					folios.add(anotaCiud.getAnotacion().getFolio());
					mat = anotaCiud.getIdMatricula();
				}
			}

			//Se validan los bloqueos de los folio
			for(Iterator it = folios.iterator(); it.hasNext();){
				folDAO.validarBloqueoFolio((FolioEnhanced)it.next(), usuario, pm);
			}

			//Se valida el caso que se cambie el número y tipo de identificación
			this.validarTipoNumeroDocumentoCiudadano(ciudDefToUpdate, ciud, pm);

			//Una vez se valida el bloqueo de todas la matrículas que tengan
			//asociada la matrícula se procede a editar el ciudadano

			//Se revisa si ya existe una anotación temporal correspondiente al ciudadano:
			CiudadanoTMPPk cIDTMP = new CiudadanoTMPPk();
			cIDTMP.idCiudadanoTmp = ciud.getIdCiudadano();

			CiudadanoTMP ciudToUpdateTMP = this.getCiudadanoTMP(cIDTMP, pm);

			if(ciudToUpdateTMP!=null){
				//Ya existe un registro temporal para el ciudadano, se procede a modificarlo
				ciudToUpdateTMP.setNombre(ciud.getNombre());
				ciudToUpdateTMP.setApellido1(ciud.getApellido1());
				ciudToUpdateTMP.setApellido2(ciud.getApellido2());
				ciudToUpdateTMP.setDocumento(ciud.getDocumento());
				ciudToUpdateTMP.setTipoDocumento(ciud.getTipoDoc());
				ciudToUpdateTMP.setTelefono(ciud.getTelefono());
				ciudToUpdateTMP.setNumeroRadicacion(numRadicacion);
				
			}
			else{
				//No existe un registro temporal para el ciudadano, se procede a crear uno
				//y a insertarlo en la base de datos con la nueva información
				CiudadanoTMP ciudToCrearTMP = new CiudadanoTMP(CiudadanoEnhanced.enhance(ciud));
				ciudToCrearTMP.setNumeroRadicacion(numRadicacion);
				ciudToCrearTMP.setSolicitante(false);
				pm.makePersistent(ciudToCrearTMP);
			}

			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}

		return true;
	}
	
	/**
	 * Actualiza un ciudadano en el modelo
	 * @param ciud
	 * @return
	 * @throws Throwable
	 */
	public boolean updateCiudadanoAdministrativa(Ciudadano ciud, Usuario usuario) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		try {
			pm.currentTransaction().begin();
			CiudadanoEnhancedPk cID = new CiudadanoEnhancedPk();
			cID.idCiudadano = ciud.getIdCiudadano();

			//Se busca el ciudadano con el ID dado
			CiudadanoEnhanced ciudDefToUpdate = this.getCiudadano(cID, pm);

			if(ciudDefToUpdate==null){
				throw new DAOException("El ciudadano que se intenta modificar NO existe, ID: "+ciud.getIdCiudadano());
			}

			//Se valida el caso que se cambie el número y tipo de identificación
			this.validarTipoNumeroDocumentoCiudadano(ciudDefToUpdate, ciud, pm);

			//Se procede a realizar el cambio
			ciudDefToUpdate.setTipoDoc(ciud.getTipoDoc());
			ciudDefToUpdate.setDocumento(ciud.getDocumento());
			ciudDefToUpdate.setNombre(ciud.getNombre());
			ciudDefToUpdate.setApellido1(ciud.getApellido1());
			ciudDefToUpdate.setApellido2(ciud.getApellido2());
			ciudDefToUpdate.setTelefono(ciud.getTelefono());
			
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}

		return true;
	}
	
	




	/**
	 * @param ciudDefToUpdate
	 * @param ciud
	 * @param pm
	 */
	private void validarTipoNumeroDocumentoCiudadano(CiudadanoEnhanced ciudDefToUpdate, Ciudadano ciud, PersistenceManager pm) throws DAOException {
		//Se valida si se quiere modificar la identificación, como debe ser única
		//no puede existir otro registro con la misma identificación entre los
		//ciudadanos definitivos y temporales
		CiudadanoEnhanced valida = this.getCiudadanoByDocumento(ciud.getTipoDoc(), ciud.getDocumento(), false, pm, ciudDefToUpdate.getIdCirculo());
		if(valida!=null){
			if(!ciudDefToUpdate.getIdCiudadano().equals(valida.getIdCiudadano())){
				throw new DAOException("Ya existe un ciudadano con el tipo y número de identificación especificado");
			}
		}

		CiudadanoTMP validaTMP = this.getCiudadanoByDocumentoTMP(ciud.getTipoDoc(), ciud.getDocumento(), false, pm, ciudDefToUpdate.getIdCirculo());
		if(validaTMP!=null){

                  if( null != valida  ) {

                    if(!ciudDefToUpdate.getIdCiudadano().equals(valida.getIdCiudadano())){
                            throw new DAOException("Ya existe un ciudadano con el tipo y número de identificación especificado");
                    } // :if

                  } // :if

		} // :if

	}




	/**
	 * Reemplaza el ciudadano de la anotación ciudadano si existe un en temporal con el mismo ID
	 * @param anCiudAux
	 */
	public void reemplazarCiudadanoPorTemporal(AnotacionCiudadanoEnhanced anCiudAux, PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced ciudEnh=null;
		//Ciudadano ciud;
		try {
			//Se busca si el ciudadano existe entre los ciudadanos temporales
			CiudadanoTMPPk cIDTMP = new CiudadanoTMPPk();
			cIDTMP.idCiudadanoTmp = anCiudAux.getCiudadano().getIdCiudadano();

			CiudadanoTMP ciudTMP = this.getCiudadanoTMP(cIDTMP, pm);

			if(ciudTMP!=null){
				ciudEnh = ciudTMP.getDefinitivo();
				anCiudAux.setCiudadano(ciudEnh);
                                /*JALCAZAR 20/10/2009 Caso Mantis 0002852
                                 * Marca como true los cuidadanos que fueron Actualizados */
                                anCiudAux.setToUpdate(true);
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		}

	}




	/**
	 * Reemplaza el ciudadano de la anotación ciudadano si existe un en temporal con el mismo ID
	 * @param anCiudAux
	 */
	public void reemplazarCiudadanoPorTemporal(AnotacionCiudadanoTMP anCiudAux, PersistenceManager pm) throws DAOException {
		CiudadanoEnhanced ciudEnh=null;
		//Ciudadano ciud;
		try {
			//Se busca si el ciudadano existe entre los ciudadanos temporales
			CiudadanoTMPPk cIDTMP = new CiudadanoTMPPk();
			cIDTMP.idCiudadanoTmp = anCiudAux.getCiudadano().getIdCiudadano();

			CiudadanoTMP ciudTMP = this.getCiudadanoTMP(cIDTMP, pm);

			if(ciudTMP!=null){
				ciudEnh = ciudTMP.getDefinitivo();
				anCiudAux.setCiudadano(ciudEnh);
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw e;
		}

	}


	/**
	 * Obtiene los ciudadanos temporales asociados a un folio. los objetos retornados son persistentes
	 * @param fol
	 * @param pm
	 * @return
	 */
	protected List getCiudadanosTemporalesFolio(FolioEnhanced fol, PersistenceManager pm){
		Query query = pm.newQuery(CiudadanoTMP.class);
		query.declareVariables("AnotacionCiudadanoEnhanced anotaCiud");
		query.declareParameters("String mat");
		query.setFilter("this.idCiudadanoTmp==anotaCiud.idCiudadano &&\n"+
				"anotaCiud.idMatricula == mat");
		List col = (List)query.execute(fol.getIdMatricula());
		return col;
	}



	/**
	 * Obtiene un ciudadano por medio del identificador, si el ciudadano tiene cambios temporales devuelve
	 * estos cambios, si no, devuelve el ciudadano definitivo, si el ciudadano no existe retorna null
	 * @param oid identificador del ciudadno
	 * */
	public Ciudadano getCiudadanoByIdTMP( CiudadanoPk oid )   throws  DAOException{
		CiudadanoEnhanced ef = null;
		PersistenceManager pm = AdministradorPM.getPM();
		Ciudadano aux = null;
		CiudadanoTMP efTMP = null;
		try {
			CiudadanoTMPPk ciudTMPID = new CiudadanoTMPPk();
			ciudTMPID.idCiudadanoTmp = oid.idCiudadano;
			efTMP = this.getCiudadanoTMP(ciudTMPID, pm);
			if(efTMP!=null){
				pm.makeTransient(efTMP);
				ef = efTMP.getDefinitivo();
			}
			else{
				ef = this.getCiudadano(new CiudadanoEnhancedPk(oid), pm);
				pm.makeTransient(ef);
			}

		} catch (DAOException e) {
			throw e;
		} finally {
			pm.close();
		}

		if (ef != null) {
			aux = (Ciudadano) ef.toTransferObject();
		}

		return aux;
	}




	public Ciudadano crearCiudadano(Ciudadano ciudadano) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		
		try {
			pm.currentTransaction().begin();
			
			CiudadanoEnhanced ciudadanoEnh = crearCiudadano(CiudadanoEnhanced.enhance(ciudadano), pm);
			
			pm.currentTransaction().commit();
			
			pm.makeTransient(ciudadanoEnh);
			
			return (Ciudadano)ciudadanoEnh.toTransferObject();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOGenieCiudadanoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		 finally {
			pm.close();
		}
	}

}
