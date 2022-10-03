/*
 * Created on 14-jul-2005
 *
 */
package gov.sir.print.server.dao.impl.jdogenie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.print.common.Bundle;
import gov.sir.print.server.dao.DAOException;
import gov.sir.print.server.dao.ImpresionDAO;
import gov.sir.hermod.HermodProperties;

import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;

import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.util.PdfCreator;
import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.ImpresionPk;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.ImprimiblePk;
import gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ImpresionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ImprimiblePdfEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ImprimiblePdfEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ObsoletoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ObsoletoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.PrefabricadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PrefabricadoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.PrefabricadoPk;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.forseti.ForsetiException;
import java.sql.Blob;

/**
 * @author fceballos
 *
 */
public class JDOGenieImpresionDAO implements ImpresionDAO {
	
	/**
	* Constructor por defecto de JDOGenieFolioDerivadoDAO
	*/
	public JDOGenieImpresionDAO() {
	}

	

	
	/**
	 * Añade una sesión de impresión.
	 * @param sesionImpresion
	 * @return
	 * @throws DAOException
	 */
	public boolean addSesionImpresion(Impresion sesionImpresion) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			 pm.currentTransaction().begin();
			 ImpresionEnhancedPk impID = new ImpresionEnhancedPk();
			 impID.idSesion = sesionImpresion.getIdSesion();
			 
			 ImpresionEnhanced impresionVal = this.getImpresion(impID, pm);
			 
			 if(impresionVal!=null){
			 	//Se revisa si ya existe un registro con la misma IP:
			 	/*
			 	ImpresionEnhanced impMismaIP = this.getImpresionByIP(sesionImpresion.getDireccionIP(), pm);
			 	
			 	if(impMismaIP!=null){
			 		pm.deletePersistent(impMismaIP);
			 	}*/
			 	
			 	//impresionVal.setAdministrador(sesionImpresion.isAdministrador());
			 	impresionVal.setDireccionIP(sesionImpresion.getDireccionIP());
			 	impresionVal.setPuerto(sesionImpresion.getPuerto());
			 	impresionVal.setCirculo(sesionImpresion.getCirculo());
				impresionVal.setFechaCreacion(new Date());
			 }else{
				ImpresionEnhanced impToInsert = ImpresionEnhanced.enhance(sesionImpresion);
				impToInsert.setFechaCreacion(new Date());
			 	pm.makePersistent(impToInsert);
			 }
			 
			 pm.currentTransaction().commit();
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		}catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		return true;
	}
	

	/**
	 * Añade una sesión de impresión.
	 * @param sesionImpresion
	 * @return
	 * @throws DAOException
	 */
	public boolean deleteSesionImpresion(Impresion sesionImpresion) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			 pm.currentTransaction().begin();
			 ImpresionEnhancedPk impID = new ImpresionEnhancedPk();
			 impID.idSesion = sesionImpresion.getIdSesion();
			 
			 ImpresionEnhanced impresionVal = this.getImpresion(impID, pm);
			 
			 if(impresionVal!=null){
				//Se revisa si ya existe un registro con la misma IP:
				pm.deletePersistent(impresionVal);
			 }
			 
			 pm.currentTransaction().commit();
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		}catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		return true;
	}	
	
	
	/**
	 * Retorna la sesión de impresión dado su uid. Si no existe retorna null
	 * @param uid
	 * @return
	 * @throws DAOException
	 */
	public Impresion getSesionImpresion(ImpresionPk uid) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		Impresion rta = null;
		ImpresionEnhanced impresionVal = null;
		 try{	 
			 impresionVal = this.getImpresion(new ImpresionEnhancedPk(uid), pm);
				 
		 } catch (JDOException e) {	
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		if(impresionVal!=null){
			rta = (Impresion)impresionVal.toTransferObject();
		}
		
		return rta;
	}
	
	/**
	 * Obtiene un objeto impresión por ID
	 * @param oid
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected ImpresionEnhanced getImpresion(
		ImpresionEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
			ImpresionEnhanced rta = null;

		if (oid.idSesion != null) {
			try {
				rta = (ImpresionEnhanced) pm.getObjectById(oid, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(this.getClass(),e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
	
	/**
	 * Obtiene un objeto impresión dada la dirección IP
	 * @param ip
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected ImpresionEnhanced getImpresionByIP(
		String ip, PersistenceManager pm)
		throws DAOException {
		ImpresionEnhanced rta = null;
		
		if (ip != null) {
			try {
				Query query = pm.newQuery(ImpresionEnhanced.class);
				query.declareParameters("String dirIP");
				query.setFilter("this.direccionIP == dirIP");
				Collection col = (Collection)query.execute(ip);
				Iterator iter = col.iterator();
				if (iter.hasNext()) {
					rta = (ImpresionEnhanced)iter.next();
				}
			} catch (JDOException e) {
				Log.getInstance().error(this.getClass(),e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
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
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new DAOException("No se encontro un valor para el tipo:" 
									+tipo+ " y el codigo:" + codigo, e);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return valor;
	}





	/**
	 * Guarda el imprimible en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int guardarImprimible(Imprimible imprimible) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		int idBundle = 0;
		try{
			pm.currentTransaction().begin();
	
			ImprimibleEnhanced imp = new ImprimibleEnhanced();
			
			//Asignacion de llave primaria:
			imp.setIdImprimible((int)this.getSecuencial(CSecuencias.SIR_OP_IMPRIMIBLE, null));
			
			imp.setDatosImprimible(imprimible.getDatosImprimible());
			imp.setIP(imprimible.getIP());
			imp.setUID(imprimible.getUID());
			imp.setTipoImprimible(imprimible.getTipoImprimible());
			imp.setTurno(imprimible.getTurno());			
			imp.setNumeroBytes(imprimible.getNumeroBytes());
			imp.setFolio(imprimible.getFolio());
			imp.setCirculo(imprimible.getCirculo());
			imp.setImprimibleExtenso(imprimible.isImprimibleExtenso());
			imp.setNumeroImpresiones(imprimible.getNumeroImpresiones());			
			imp.setFechaCreacion(new Date());			
	
			pm.makePersistent(imp);
			pm.currentTransaction().commit();
			 
			ImprimibleEnhancedPk id = new ImprimibleEnhancedPk();
			id = (ImprimibleEnhancedPk)pm.getObjectId(imp);
			idBundle = id.idImprimible; 
			 
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		return idBundle;
	}
	
	public void guardarPrefabricado(ImprimibleCertificado imprimible) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();	
		
		try{				
			PrefabricadoEnhancedPk id = new PrefabricadoEnhancedPk();
			
			id.idMatricula = imprimible.getFolio().getIdMatricula();
			id.idCirculo = imprimible.getFolio().getCirculo();
			
			// actualizamos el objeto prefabricado
			PrefabricadoEnhanced prefabricado = this.getPrefabricado(id, pm);
			
			pm.currentTransaction().begin();
			if (prefabricado == null) {
				prefabricado = new PrefabricadoEnhanced();
				
				//Asignacion de llave primaria:
				prefabricado.setIdMatricula(id.idMatricula);
				prefabricado.setIdCirculo(id.idCirculo);	
			}
			prefabricado.setDatosPrefabricado(makeBundle(imprimible).getDatosImprimible());
			
			pm.makePersistent(prefabricado);
			
			// eliminamos el estado obsoleto de la matricula
			ObsoletoEnhanced obsoleto = this.getObsoleto(id.idMatricula, pm);
			if (obsoleto != null) {
				pm.deletePersistent(obsoleto);	
			}
			
			pm.currentTransaction().commit();		
			
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
	}
	
	/**
	 * Crea un objeto de tipo Bundle con base al ImprimibleCertificado.
	 * @param imprimible
	 * @return
	 */
    private Imprimible makeBundle (ImprimibleCertificado imprimible) {
    	Imprimible imp = null;
    	Bundle bundle = new Bundle(imprimible);
	 	
	 	ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
	 	ObjectOutputStream oos;
	 	
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(bundle);
			
		 	imp = new Imprimible();
			imp.setDatosImprimible(baos.toByteArray());
			
		 	oos.close();
		 	baos.close();
		} catch (IOException e) {
			imp = null;
			e.printStackTrace();
		}
	 	
	 	return imp;
    }

    protected PrefabricadoEnhanced getPrefabricado(PrefabricadoEnhancedPk oid, PersistenceManager pm) throws DAOException {
    	PrefabricadoEnhanced rta = null;

        if (oid.idCirculo != null && oid.idMatricula != null ) {
			try {
	
				Query query = pm.newQuery(PrefabricadoEnhanced.class);
				query.declareParameters("String idCirculo, String idMatricula");
				
				query.setFilter("this.idCirculo == idCirculo && this.idMatricula == idMatricula");
				Collection col = (Collection)query.execute(oid.idCirculo, oid.idMatricula);
				boolean first = true;
				
				for (Iterator iter = col.iterator(); iter.hasNext();) {
				    if (first) {
				    	 rta = (PrefabricadoEnhanced)iter.next();
				    	 first = false;
				    	 break;
				    } 
				}
				query.closeAll();
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOGenieImpresionDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
        }
        
		return rta;    
	}
    
    protected ObsoletoEnhanced getObsoleto(String idMatricula, PersistenceManager pm) throws DAOException {
    	ObsoletoEnhanced rta = null;

        if (idMatricula != null ) {
			try {
	
				Query query = pm.newQuery(ObsoletoEnhanced.class);
				query.declareParameters("String idMatricula");
				
				query.setFilter("this.idMatricula == idMatricula");
				Collection col = (Collection)query.execute(idMatricula);
				boolean first = true;
				
				for (Iterator iter = col.iterator(); iter.hasNext();) {
				    if (first) {
				    	 rta = (ObsoletoEnhanced)iter.next();
				    	 first = false;
				    	 break;
				    } 
				}
				query.closeAll();
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOGenieImpresionDAO.class, e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
        }
        
		return rta;    
	}
    
	public void registrarObsoleto(String idCirculo, String idMatricula) {
		PersistenceManager pm = null;
		ObsoletoEnhanced obs;
		
		ObsoletoEnhancedPk id = new ObsoletoEnhancedPk();
		id.idMatricula = idMatricula;
		id.idCirculo = idCirculo;
		
		try
		{
			pm = AdministradorPM.getPM();
			pm.currentTransaction().begin();
		
			try{
				obs = (ObsoletoEnhanced) pm.getObjectById(id, true);
			} catch (JDOObjectNotFoundException e) {
				obs = new ObsoletoEnhanced(id);
				obs.setFecha(new Date());

				pm.makePersistent(obs);
				pm.currentTransaction().commit();
			}
			
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			e.printStackTrace();
		}finally{
			pm.close();
		}
	}

	/**
	 * Guarda el imprimible en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int guardarImprimiblePDF(Imprimible imprimible, gov.sir.print.common.Imprimible imprimibleTemp, boolean guardarPDF, byte[] bytesFormulario) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		int idBundle = 0;
		
		Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF JDOGenierImpresion  se va a guardar el imprmuible pdf");
		try{
			//byte[] bytesFormulario = pdfArray.toByteArray();
			
			PdfCreator pdf = new PdfCreator();
			//ByteArrayOutputStream pdfFormulario = pdf.generarImprimibleAPIPDDocument(imprimibleTemp, imp.getTurno(), imp.getTipoImprimible());
			ByteArrayOutputStream pdfFormulario = pdf.generar(imprimibleTemp);
		    bytesFormulario = pdfFormulario.toByteArray();
		    
			
			pm.currentTransaction().begin();
	
			ImprimiblePdfEnhanced imp = new ImprimiblePdfEnhanced();
			
			//Asignacion de llave primaria:
			imp.setIdImprimible((int)this.getSecuencial(CSecuencias.SIR_OP_IMPRIMIBLE_PDF, null));
			imp.setDatosImprimible(bytesFormulario);
			imp.setTipoImprimible(imprimible.getTipoImprimible());
			imp.setTurno(imprimible.getTurno());			
			imp.setNumeroBytes(imprimible.getNumeroBytes());
			imp.setCirculo(imprimible.getCirculo());
			imp.setNumeroImpresiones(imprimible.getNumeroImpresiones());			
			imp.setFechaCreacion(new Date());			
	
			pm.makePersistent(imp);
			pm.currentTransaction().commit();
			 
			ImprimiblePdfEnhancedPk id = new ImprimiblePdfEnhancedPk();
			id = (ImprimiblePdfEnhancedPk)pm.getObjectId(imp);
			idBundle = id.idImprimible; 
		 
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		return idBundle;
	}

	/**
	 * Elimina el bundle de la base de datos
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarImprimible(int  id) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			 pm.currentTransaction().begin();
			 ImprimibleEnhancedPk impID = new ImprimibleEnhancedPk();
			 impID.idImprimible = id;
			 
			 ImprimibleEnhanced impresionVal = this.getBundle(impID, pm);
			 
			 if(impresionVal!=null){
				//Se revisa si ya existe un registro con la misma IP:
				pm.deletePersistent(impresionVal);
			 }
			 
			 pm.currentTransaction().commit();
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw e;
		}catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		return true;
	}	
	
	
	/**
	 * Obtiene el bundle de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */

/* Modificacion 2010-03-16 por jvnegas para Boton de Pago */


public Imprimible getImprimible(ImprimiblePk id) throws DAOException{

  PersistenceManager pm = AdministradorPM.getPM();
  Imprimible rta = null;
  ImprimibleEnhanced impresionVal = null;

  ImprimibleEnhancedPk idEnhanced = new ImprimibleEnhancedPk();
  idEnhanced.idImprimible = id.idImprimible;

  byte[] arreglo = null;

   try{
    impresionVal = this.getBundle(idEnhanced, pm);

    if(impresionVal!=null){
    pm.makeTransient(impresionVal);
    arreglo = impresionVal.getDatosImprimible();
    }

   } catch (JDOException e) {
   Log.getInstance().error(this.getClass(),e.getMessage());
   throw new DAOException(e.getMessage(), e);
  } catch (Throwable e) {
   if (pm.currentTransaction().isActive()) {
    pm.currentTransaction().rollback();
   }
   throw new DAOException(e.getMessage(), e);
  }finally{
   pm.close();
  }
  if(impresionVal!=null){
   rta = (Imprimible)impresionVal.toTransferObject();
   rta.setDatosImprimible(arreglo);
  }
  return rta;
 }

/*
        public Imprimible getImprimible(ImprimiblePk id) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		Imprimible rta = null;
		ImprimibleEnhanced impresionVal = null;
		
		ImprimibleEnhancedPk idEnhanced = new ImprimibleEnhancedPk();
		idEnhanced.idImprimible = id.idImprimible;
		
		byte[] arreglo = null;
		
		 try{	 
			 impresionVal = this.getBundle(idEnhanced, pm);
			 if(impresionVal!=null){
				pm.makeTransient(impresionVal);
				arreglo = impresionVal.getDatosImprimible();				
			 }

		 } catch (JDOException e) {	
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		if(impresionVal!=null){
			rta = (Imprimible)impresionVal.toTransferObject();
			rta.setDatosImprimible(arreglo);
		}
		
		
		return rta;
	}


/**
     * Obtiene una lista con las matriculas que han sido marcadas como obsoletas
     * @param circulo circulo a actualizar
     * @param cantidad numero de folios obsoletos a consultar
     *  
     * @return lista con las matriculas obsoletas
     * @see gov.sir.core.negocio.modelo.jdogenie.ObsoletoEnhanced
     * @throws ForsetiException cuando ocurre un error al consultar las matriculas obsoletas.
     */
	public List getObsoletos(String circulo, int cantidad) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		
		List rta = null;
		
		try {
			VersantQuery query = (VersantQuery)pm.newQuery(ObsoletoEnhanced.class);
			// fija la cantidad de registros que seran retornados por la sentencia
			if (cantidad > 0) { query.setMaxRows(cantidad); }
			
			if (circulo != null && !circulo.equals("000") ) {
				query.declareParameters("String idCirculo");
				query.setFilter("this.idCirculo == idCirculo");
				rta = (List)query.execute(circulo);
			} else {
				rta = (List)query.execute();
			}
			
			pm.retrieveAll(rta);
			pm.makeTransientAll(rta);
			//query.closeAll();
			
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}	
		
		return rta;
	}
	
	/**
	 * Obtiene el bundle de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Imprimible getPrefabricadoById(PrefabricadoPk oid) throws DAOException{
		byte[] arreglo = null;
		Imprimible rta = null;
		
		PersistenceManager pm = AdministradorPM.getPM();
		
	    if (oid.idCirculo != null && oid.idMatricula != null ) {
			try {
				Query query = pm.newQuery(PrefabricadoEnhanced.class);
				query.declareParameters("String idCirculo, String idMatricula");
				
				query.setFilter("this.idCirculo == idCirculo && this.idMatricula == idMatricula");
				Collection col = (Collection)query.execute(oid.idCirculo, oid.idMatricula);
				boolean first = true;
				
				for (Iterator iter = col.iterator(); iter.hasNext();) {
				    if (first) {
				    	rta = new Imprimible();
				    	arreglo = ((PrefabricadoEnhanced)iter.next()).getDatosPrefabricado();
						
						rta.setDatosImprimible(arreglo);
				    	
						first = false;
				    	break;
				    } 
				}
				query.closeAll();
							
			} catch (Exception e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
	
				e.printStackTrace();
			}finally{
				pm.close();
			}	
        }		
		
	    if (rta == null)
	    	throw new DAOException("Error al obtener el imprimible prefabricado");
	    
		return rta;
	}
	
	/**
	 * Obtiene el imprimible si este no ha sido impreso, si no habia sido impreso, se actualiza
	 * el numero de impresiones a 1
	 */
	public Imprimible getImprimibleNoImpreso(ImprimiblePk id) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		Imprimible rta = null;
		ImprimibleEnhanced impresionVal = null;
		VersantPersistenceManager pm2 = null;
		boolean isImpresoAntes = true;
		
		ImprimibleEnhancedPk idEnhanced = new ImprimibleEnhancedPk();
		idEnhanced.idImprimible = id.idImprimible;
		
		byte[] arreglo = null;
		
		 try{	 
			 pm.currentTransaction().setOptimistic(true);
			 pm.currentTransaction().begin();
			 
			 pm2 = (VersantPersistenceManager) pm;
			 pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
			 pm = pm2;
			 
			 impresionVal = this.getBundle(idEnhanced, pm);
			 if(impresionVal!=null){
				arreglo = impresionVal.getDatosImprimible();
				
				//Si el imprimible no ha sido impreso, el número de impresiones se actualiza en 1
				if(impresionVal.getNumeroImpresiones() == 0){
					impresionVal.setNumeroImpresiones(impresionVal.getNumeroImpresiones()+1);
					isImpresoAntes = false;
				}
				//pm.makeTransient(impresionVal);
			 }
			 
			 pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 pm.currentTransaction().commit();
             
                        if(!isImpresoAntes){
                            rta = (Imprimible)impresionVal.toTransferObject();
                            rta.setDatosImprimible(arreglo);
                        }
		 } catch (JDOException e) {	
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			pm.close();
            impresionVal = null;
            arreglo      = null;
		}

		
		
		return rta;
	}
	
	/**
	 * Obtiene el bundle de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public ImprimiblePdf getImprimiblePdf(String idWorkflow) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		ImprimiblePdf rta = null;
		ImprimiblePdfEnhanced impresionVal = null;
		
		/*ImprimiblePdf.ID impId = new ImprimiblePdf.ID();
		impId.idImprimible = 5;
		
		ImprimiblePdfEnhanced.ID idEnhanced = new ImprimiblePdfEnhanced.ID();
		idEnhanced.idImprimible = impId.idImprimible;*/
		
		byte[] arreglo = null;
		
		 try{	 
			 impresionVal = this.getBundlePdf(idWorkflow, pm);
			 if(impresionVal!=null){
				pm.makeTransient(impresionVal);
				arreglo = impresionVal.getDatosImprimible();				
			 }
		 } catch (JDOException e) {	
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		if(impresionVal!=null){
			rta = (ImprimiblePdf)impresionVal.toTransferObject();
			rta.setDatosImprimible(arreglo);
		}
		
		
		return rta;
	}
	

	/**
	 * Actualiza el imprimible en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	 public boolean updateImprimible(Imprimible imprimible) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta = false;
		
		try{
			pm.currentTransaction().begin();
			
			ImprimibleEnhancedPk idImprimible = new ImprimibleEnhancedPk();
			idImprimible.idImprimible = imprimible.getIdImprimible(); 
			
			ImprimibleEnhanced impEnh = null;		
			impEnh = this.getBundle(idImprimible, pm);
			
			if(impEnh!=null){
			
				impEnh.setNumeroImpresiones(imprimible.getNumeroImpresiones());
				
			}else{
				rta = false;
			}
			
			pm.currentTransaction().commit();
	 
		 } catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		}finally{
			pm.close();
		}
		
		return rta;
	}	
	
	
	/**
	 * Obtiene la Lista con los Identificadores de las impresiones fallidas para
	 * un UID.
	 * 
	 * @param List.
	 *            Lista con los Identificadores de las impresiones fallidas
	 * @return
	 * @throws DAOException
	 */
	public List getImpresionesFallidas(Imprimible imprimible, boolean isAdministrador) throws DAOException {
		
		if(isAdministrador){
			return this.getImpresionesAdministrador(imprimible, isAdministrador);	
		}else{			
			return this.getImpresionesLocales(imprimible, isAdministrador);
		}
			 
	}	
	
	
	/**
	 * Obtiene la Lista con los Identificadores de las impresiones fallidas para
	 * un UID.
	 * 
	 * @param List.
	 *            Lista con los Identificadores de las impresiones fallidas
	 * @return
	 * @throws DAOException
	 */
	public List getImpresionesAdministrador(Imprimible imprimible, boolean isAdministrador) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		List idFallidas = new ArrayList();
		ImprimibleEnhanced rta = null;
		
		boolean txBegined = pm.currentTransaction().isActive();

        Connection           con = null;
        PreparedStatement pstSel = null;
        PreparedStatement pstUpd = null;
        PreparedStatement pstIns = null;
        ResultSet             rs = null;
		try
		{
			if (!txBegined)
			{
				pm.currentTransaction().begin();
			}
			VersantPersistenceManager vpm = (VersantPersistenceManager)pm;
			con = vpm.getJdbcConnection(null);
			String sqlSel = "SELECT * FROM SIR_OP_REIMPRESION_FALLIDA WHERE ID_IMPRESORA = ?";
			String sqlUpd = "UPDATE SIR_OP_REIMPRESION_FALLIDA SET RIMPF_FECHA = ? WHERE ID_IMPRESORA  = ?";
			String sqlIns = "INSERT INTO SIR_OP_REIMPRESION_FALLIDA (\"ID_IMPRESORA\", \"RIMPF_FECHA\") VALUES (?, ?)";
			
			pstSel = con.prepareStatement(sqlSel);
			pstSel.setString(1,imprimible.getUID());
			rs = pstSel.executeQuery();

            if (rs != null && rs.next())
			{
				pstUpd = con.prepareStatement(sqlUpd);
				pstUpd.setTimestamp(1,new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				pstUpd.setString(2, imprimible.getUID());
				pstUpd.executeUpdate();
			}else
			{
				pstIns = con.prepareStatement(sqlIns);
				pstIns.setString(1, imprimible.getUID());
				pstIns.setTimestamp(2,new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				pstIns.executeUpdate();
			}
			
		} catch (Exception e)
		{
			Log.getInstance().error(this.getClass(),"error insertando en SIR_OP_REIMPRESION_FALLIDA");
			if (!txBegined && pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
		} finally
		{
			try {

                if(rs != null){
                    rs.close();
                }

                if(pstIns != null){
                    pstIns.close();
                }

                if(pstUpd != null){
                    pstUpd.close();
                }

                if(pstSel != null){
                    pstSel.close();
                }
                
				if (con != null && !con.isClosed()){
					con.close();
				}
			} catch (SQLException e) {
				Log.getInstance().warn(this.getClass(),"error cerrando conexion");
			}
			if (!txBegined && pm.currentTransaction().isActive())
			{
				pm.currentTransaction().commit();
			}			
		}

		try {

			try {
				Calendar fechaIni = Calendar.getInstance();
				fechaIni.set(Calendar.HOUR_OF_DAY, 0);
				fechaIni.set(Calendar.MINUTE, 0);
				fechaIni.set(Calendar.SECOND, 1);

				Calendar fechaFin = Calendar.getInstance();
				fechaFin.set(Calendar.HOUR_OF_DAY, 23);
				fechaFin.set(Calendar.MINUTE, 59);
				fechaFin.set(Calendar.SECOND, 59);

				Date fechaInicio = fechaIni.getTime();
				Date fechaFinal = fechaFin.getTime();

				Query query = pm.newQuery(ImprimibleEnhanced.class);
				query.declareParameters("Date fechaInicio, Date fechaFinal");
				// query.setFilter("UID==\""+imprimible.getUID()+"\" &&
				// numeroImpresiones==0 && this.fechaCreacion >= fechaInicio &&
				// this.fechaCreacion <= fechaFinal ");
				query.setFilter("UID==\"" + imprimible.getUID() + "\" && numeroImpresiones==0 && this.fechaCreacion >= fechaInicio && this.fechaCreacion <= fechaFinal ");
				query.setOrdering("idImprimible ascending");

				Collection col = (Collection) query.execute(fechaInicio, fechaFinal);
				for (Iterator iter = col.iterator(); iter.hasNext();) {

					rta = (ImprimibleEnhanced) iter.next();

					if (isAdministrador) {
						if (rta.getUID() != null && rta.getUID().trim().length() <= 3) {
							idFallidas.add("" + rta.getIdImprimible());
						}
					} else {
						if (rta.getUID() != null && rta.getUID().trim().length() > 3) {
							idFallidas.add("" + rta.getIdImprimible());
						}
					}

				}
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(this.getClass(),e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}

		} catch (JDOException e) {
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		
		return idFallidas;
	}		

	
	/**
	 * Obtiene la Lista con los Identificadores de las impresiones fallidas para
	 * un UID.
	 * 
	 * @param List.
	 *            Lista con los Identificadores de las impresiones fallidas
	 * @return
	 * @throws DAOException
	 */
	public List getImpresionesLocales(Imprimible imprimible, boolean isAdministrador) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();

		List idFallidas = new ArrayList();
		ImprimibleEnhanced rta = null;

		try {

			try {
				Calendar fechaIni = Calendar.getInstance();
				fechaIni.set(Calendar.HOUR_OF_DAY, 0);
				fechaIni.set(Calendar.MINUTE, 0);
				fechaIni.set(Calendar.SECOND, 1);

				Calendar fechaFin = Calendar.getInstance();
				fechaFin.set(Calendar.HOUR_OF_DAY, 23);
				fechaFin.set(Calendar.MINUTE, 59);
				fechaFin.set(Calendar.SECOND, 59);

				Date fechaInicio = fechaIni.getTime();
				Date fechaFinal = fechaFin.getTime();

				Query query = pm.newQuery(ImprimibleEnhanced.class);
				query.declareParameters("Date fechaInicio, Date fechaFinal");
				// query.setFilter("UID==\""+imprimible.getUID()+"\" &&
				// numeroImpresiones==0 && this.fechaCreacion >= fechaInicio &&
				// this.fechaCreacion <= fechaFinal ");
				query.setFilter("IP==\"" + imprimible.getIP() + "\" && numeroImpresiones==0 && this.fechaCreacion >= fechaInicio && this.fechaCreacion <= fechaFinal ");
				query.setOrdering("idImprimible ascending");

				Collection col = (Collection) query.execute(fechaInicio, fechaFinal);
				for (Iterator iter = col.iterator(); iter.hasNext();) {

					rta = (ImprimibleEnhanced) iter.next();

					if (isAdministrador) {
						if (rta.getUID() != null && rta.getUID().trim().length() <= 3) {
							idFallidas.add("" + rta.getIdImprimible());
						}
					} else {
						if (rta.getUID() != null && rta.getUID().trim().length() > 3) {
							idFallidas.add("" + rta.getIdImprimible());
						}
					}

				}
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(this.getClass(),e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}

		} catch (JDOException e) {
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		return idFallidas;
	}		
	
	/**
	 * Obtiene un objeto imprimible por ID
	 * 
	 * @param oid
	 * @param pm
	 * @return
	 * @throws DAOException
	 */

        /* Modificado 2010-03-15 po jvenegas para Boton de Pago */

protected ImprimibleEnhanced getBundle(
  ImprimibleEnhancedPk oid, PersistenceManager pm)throws DAOException {

  ImprimibleEnhanced rta = null;

        Query    query = null;
        Collection col = null;

        try {
   query = pm.newQuery(ImprimibleEnhanced.class);

   query.setFilter("idImprimible=="+oid.idImprimible);

   col = (Collection)query.execute();
            Iterator iter = col.iterator();
            while(iter.hasNext()){
      rta = (ImprimibleEnhanced)iter.next();
   }
  } catch (JDOObjectNotFoundException e) {
   rta = null;
  } catch (JDOException e) {
   Log.getInstance().error(this.getClass(),e.getMessage());
   throw new DAOException(e.getMessage(), e);
  }finally{
            query.closeAll();
            col = null;
        }

//  Log.getInstance().error(this.getClass(),"getBundle valor rta "+(rta==null?"nulo":rta.toString()));

  return rta;
 }


/*        protected ImprimibleEnhanced getBundle(
		ImprimibleEnhancedPk oid, PersistenceManager pm)throws DAOException {
        
		ImprimibleEnhanced rta = null;
        
        Query    query = null;
        Collection col = null;

        try {
			query = pm.newQuery(ImprimibleEnhanced.class);
			query.setFilter("idImprimible=="+oid.idImprimible);

			col = (Collection)query.execute();
            Iterator iter = col.iterator();
            while(iter.hasNext()){
			   rta = (ImprimibleEnhanced)iter.next();
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}finally{
            query.closeAll();
            col = null;
        }

		return rta;
	}   
	
	/**
	 * Obtiene un objeto imprimible por ID
	 * 
	 * @param oid
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected ImprimiblePdfEnhanced getBundlePdf(
		String idWorkflow, PersistenceManager pm)
		throws DAOException {
		ImprimiblePdfEnhanced rta = null;

		try {

			Query query = pm.newQuery(ImprimiblePdfEnhanced.class);
			query.setOrdering("fechaCreacion descending");
			query.declareParameters("String idWorflow");
			query.setFilter("this.turno == idWorflow");
			Collection col = (Collection)query.execute(idWorkflow);
			boolean first = true;
			for (Iterator iter = col.iterator(); iter.hasNext();) {
			    if (first) {
			    	 rta = (ImprimiblePdfEnhanced)iter.next();
			    	 byte[] arreglo = rta.getDatosImprimible();
			    	 first = false;
			    	 break;
			    } 
			}
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}
	
	

	/**
	* Finaliza el servicio impresion
	*/
	public void finalizar() throws DAOException {
		AdministradorPM.shutdown();
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

        Connection        con = null;
        PreparedStatement pst = null;
        ResultSet          rs = null;
		
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
		        	rs  = pst.executeQuery();
                    
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
                try {
                    if(rs != null){
                        rs.close();
                    }

                    if(pst != null){
                        pst.close();
                    }

                    if (con != null){
                        con.close();
                    }
                }catch (SQLException ex) {
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

	
	/**
	* Obtiene una secuencia dado su identificador, método utilizado para transacciones
	* TOMADO de FORSETTI
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
				Log.getInstance().error(this.getClass(),e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}

	
	
	/**
	 * Consulta la fecha de la última reimpresión del administrado de impresión
	 * identificado con UID
	 * @param UID
	 * @return
	 * @throws DAOException
	 */
	public Date getUltimaConsultaReimpresión(String UID) throws DAOException {

        Date ultimaConsulta = null;

        VersantPersistenceManager pm = null;

        String            sql = null;
        Connection        con = null;
		PreparedStatement pst = null;
        ResultSet          rs = null;
        
		try {

            pm = (VersantPersistenceManager) AdministradorPM.getPM();
            pm.currentTransaction().begin();

            sql = "SELECT RIMPF_FECHA FROM SIR_OP_REIMPRESION_FALLIDA WHERE ID_IMPRESORA = ?";
            con = pm.getJdbcConnection(null);
			pst = con.prepareStatement(sql);

            pst.setString(1, UID);
			rs = pst.executeQuery();

            if (rs != null && rs.next())
			{
				ultimaConsulta = new Date(rs.getTimestamp(1).getTime());
			}
			pm.currentTransaction().commit();
		} catch (SQLException e) {
			Log.getInstance().error(this.getClass(),"error consultando la última reimpresion",e);
		} finally
		{
			try {
                if(rs != null){
                    rs.close();
                }

                if(pst != null){
                    pst.close();
                }
                
				if (con != null && !con.isClosed()){
					con.close();
				}
			} catch (SQLException e) {
				Log.getInstance().warn(this.getClass(),"error cerrando la conexion");
			}
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
		return ultimaConsulta;
	}
	
	

	/**
	* Obtiene la lista de variables, dado su lookupType
	* @param tipo El LookupType de la variable. 
	* @return Lista de objetos OPLookupCcodes.
	* @throws DAOException
	*/
	public List getValor(String tipo) throws DAOException {
		PersistenceManager pm = AdministradorPM.getPM();
		List respuesta = new ArrayList();
        
		try {
			pm.currentTransaction().begin();
			OPLookupCodesEnhancedPk lcId = new OPLookupCodesEnhancedPk();
			lcId.tipo = tipo;
			String lookupType = "ROLES_ALERTA_ADM_IMP_CAIDO";

			Query query = pm.newQuery(OPLookupCodesEnhanced.class);
			query.declareParameters("String lookupType");
			query.setFilter("this.tipo == lookupType");
			Collection col = (Collection)query.execute(lookupType);
			pm.makeTransientAll(col);
			
			for (Iterator lookItera  = col.iterator(); lookItera.hasNext();)
			{
				OPLookupCodesEnhanced lookEnh = (OPLookupCodesEnhanced)lookItera.next();
				respuesta.add(lookEnh.toTransferObject());
			}
			
			query.closeAll();
			pm.currentTransaction().commit();
        
		} catch (JDOObjectNotFoundException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new DAOException("No se encontro un valor para el tipo:" 
									+tipo, e);
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			e.printStackTrace();
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}
		return respuesta;
	}


        public byte[] getImprimibleBytes(ImprimiblePk id) throws DAOException {
  byte[] rta = null;

  VersantPersistenceManager pm = null;

        String            sql = null;
        Connection        con = null;
  PreparedStatement pst = null;
        ResultSet          rs = null;

  try {
            pm = (VersantPersistenceManager) AdministradorPM.getPM();
            pm.currentTransaction().begin();

            sql = "select MPRM_DATOS from SIR_OP_IMPRIMIBLE where id_imprimible = ?";
            con = pm.getJdbcConnection(null);
   pst = con.prepareStatement(sql);

            pst.setInt(1, id.idImprimible);
   rs = pst.executeQuery();

   Blob blob = null;

            if (rs != null && rs.next())
   {
             blob = rs.getBlob("MPRM_DATOS");
   }

            if(blob != null){
             rta = blob.getBytes(1, (int) blob.length());
            }

   pm.currentTransaction().commit();
  } catch (SQLException e) {
   Log.getInstance().error(this.getClass(),"error cargando SIR_OP_IMPRIMIBLE ",e);
  } finally{
   try {
                if(rs != null){
                    rs.close();
                }

                if(pst != null){
                    pst.close();
                }

    if (con != null && !con.isClosed()){
     con.close();
    }
   } catch (SQLException e) {
    Log.getInstance().warn(this.getClass(),"error cerrando la conexion");
   }
   if (pm.currentTransaction().isActive()){
    pm.currentTransaction().rollback();
   }
   pm.close();
  }

  return rta;
 }
	
}
