package gov.sir.hermod.dao.impl.jdogenie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.WebSegEngDAO;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.WebAnotaHereda;
import gov.sir.core.negocio.modelo.WebAnotacion;
import gov.sir.core.negocio.modelo.WebCiudadano;
import gov.sir.core.negocio.modelo.WebDireccion;
import gov.sir.core.negocio.modelo.WebDocumento;
import gov.sir.core.negocio.modelo.WebEnglobe;
import gov.sir.core.negocio.modelo.WebFolioDerivado;
import gov.sir.core.negocio.modelo.WebFolioEnglobe;
import gov.sir.core.negocio.modelo.WebFolioHeredado;
import gov.sir.core.negocio.modelo.WebFolioNuevo;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegEngPk;
import gov.sir.core.negocio.modelo.WebSegregacion;

import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.WebAnotaHeredaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebCiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebDocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebFolioDerivadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebSegEngEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.WebSegEngEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.WebSegregacionEnhanced;

/**
 * @author fceballos
 * Clase para el manejo de los objetos de tipo <code>WebSegEng</code> utilizados en la aplicacion.<p>
 * <p>
 * Provee servicios para la consulta, modificación y adición de estos objetos.
 * @see gov.sir.core.negocio.modelo.WebSegEng
 */
public class JDOWebSegEngDAO implements WebSegEngDAO{
	
	/** 
	* Crea una nueva instancia de <code>JDOAlertasDAO</code>
	* <p>
	* Método Constructor.  
	*/
	public JDOWebSegEngDAO() {
	}
	
	
	public static void main(String[] args) {
		JDOWebSegEngDAO test = new JDOWebSegEngDAO();
		//test.testCrearWebSegregacion();
		//test.testCrearWebEnglobe();
		//test.testEliminarWebSegEng();
		//test.testActualizarWebSegEng();
		test.testConsultarWebSegEng();
	}
	
	private void testCrearWebSegregacion(){
		Log.getInstance().debug(JDOWebSegEngDAO.class,"PRUEBA CREAR SEGREGACION");
		
		SolicitudPk solID = new SolicitudPk();
		solID.idSolicitud = "2005-400-6-10";
		
		WebSegregacion segregacion = new WebSegregacion();
		
		WebAnotacion anota = new WebAnotacion();
		WebCiudadano ciudadano = new WebCiudadano();
		ciudadano.setApellido1("CEBALLOS");
		ciudadano.setApellido2("ACOSTA");
		ciudadano.setNombre("FABIAN");
		ciudadano.setNumDocumento("80150367");
		ciudadano.setTipoDocumento("CC");
		anota.addCiudadano(ciudadano);
		
		WebDocumento doc = new WebDocumento();
		doc.setComentario("Comentario de este documento");
		doc.setOficinaInternacional("Hosuton, texas USA");
		doc.setIdTipoDocumento("1");
		anota.setDocumento(doc);
		
		anota.setComentario("Dueño de todo");
		anota.setIdDocumento("32432");
		anota.setValor("4534");
		
		WebFolioHeredado folioHeredado = new WebFolioHeredado();
		folioHeredado.setIdMatricula("400-1000");
		
		WebAnotaHereda anotacion = new WebAnotaHereda();
		anotacion.setIdAnotacion("4");
		
		folioHeredado.addAnotacionesHeredada(anotacion);
		
		segregacion.addFoliosHeredado(folioHeredado);
		
		//Lo adicional que está en segregación:
		
		WebFolioDerivado folioDerivado = new WebFolioDerivado();
		folioDerivado.setArea("50 mts");
		folioDerivado.setInmueble("LOTE 1");
		folioDerivado.setPorcentaje("40%");
		
		WebFolioDerivado folioDerivado2 = new WebFolioDerivado();
		folioDerivado2.setArea("50 mts");
		folioDerivado2.setInmueble("LOTE 1");
		folioDerivado2.setPorcentaje("40%");
		
		segregacion.addFoliosDerivado(folioDerivado);
		segregacion.addFoliosDerivado(folioDerivado2);
		
		segregacion.setAnotacion(anota);
		
		
		try {
			this.crearWebSegEng(segregacion, solID);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			Log.getInstance().error(JDOWebSegEngDAO.class,e);
		}
	}
	
	
	private void testCrearWebEnglobe(){
		Log.getInstance().debug(JDOWebSegEngDAO.class,"PRUEBA CREAR ENGLOBE");
		
		SolicitudPk solID = new SolicitudPk();
		solID.idSolicitud = "2005-400-6-10";
		
		WebEnglobe englobe = new WebEnglobe();
		
		WebAnotacion anota = new WebAnotacion();
		WebCiudadano ciudadano = new WebCiudadano();
		ciudadano.setApellido1("CEBALLOS");
		ciudadano.setApellido2("ACOSTA");
		ciudadano.setNombre("FABIAN");
		ciudadano.setNumDocumento("80150367");
		ciudadano.setTipoDocumento("CC");
		anota.addCiudadano(ciudadano);
		
		WebDocumento doc = new WebDocumento();
		doc.setComentario("Comentario de este documento");
		doc.setOficinaInternacional("Hosuton, texas USA");
		doc.setIdTipoDocumento("1");
		anota.setDocumento(doc);
		
		anota.setComentario("Dueño de todo");
		anota.setIdDocumento("32432");
		anota.setValor("4534");
		
		WebFolioHeredado folioHeredado = new WebFolioHeredado();
		folioHeredado.setIdMatricula("400-1000");
		
		WebAnotaHereda anotacion = new WebAnotaHereda();
		anotacion.setIdAnotacion("4");
		
		folioHeredado.addAnotacionesHeredada(anotacion);
		
		englobe.addFoliosHeredado(folioHeredado);
		
		englobe.setAnotacion(anota);
		
		//Ahora lo de englobe:
		englobe.setIdMatriculaUbicacion("400-200");

		
		WebFolioEnglobe folioEnglobe1 = new WebFolioEnglobe();
		folioEnglobe1.setIdMatricula("400-1");

		
		WebFolioEnglobe folioEnglobe2 = new WebFolioEnglobe();
		folioEnglobe2.setIdMatricula("400-2");
		
		englobe.addFoliosEnglobar(folioEnglobe1);
		englobe.addFoliosEnglobar(folioEnglobe2);
		
		//FolioNuevo
		
		WebFolioNuevo folioNuevo = new WebFolioNuevo();
		folioNuevo.setArea("66 mts2");
		folioNuevo.setDescripcion("Finca grande");
		folioNuevo.setNombre("La macarena");
		
		WebDireccion direccion = new WebDireccion();
		direccion.setIdEje1("A");
		direccion.setIdEje2("#");
		direccion.setValorEje1("Autopiesta norte");
		direccion.setValorEje2("56-45");
		
		WebDireccion direccion2 = new WebDireccion();
		direccion2.setIdEje1("A");
		direccion2.setIdEje2("#");
		direccion2.setValorEje1("Autopiesta norte");
		direccion2.setValorEje2("33-33");
		
		folioNuevo.addDireccione(direccion);
		folioNuevo.addDireccione(direccion2);
		
		englobe.setFolioNuevo(folioNuevo);
		
		
		try {
			this.crearWebSegEng(englobe, solID);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			Log.getInstance().error(JDOWebSegEngDAO.class,e);
		}
	}
	
	
	private void testEliminarWebSegEng(){
		Log.getInstance().debug(JDOWebSegEngDAO.class,"PRUEBA ELIMINAR SEGENG");
		WebSegEngPk operacionID = new WebSegEngPk();
		operacionID.idSolicitud = "2005-400-6-10";
		operacionID.idWebSegeng = "88";
		
		
		try {
			this.eliminarWebSegEng(operacionID);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			Log.getInstance().error(JDOWebSegEngDAO.class,e);
		}
	}
	
	private void testActualizarWebSegEng(){
		Log.getInstance().debug(JDOWebSegEngDAO.class,"PRUEBA ELIMINAR SEGENG");
		WebSegEngPk operacionID = new WebSegEngPk();
		operacionID.idSolicitud = "2005-400-6-10";
		operacionID.idWebSegeng = "2";
		
		WebEnglobe englobe = new WebEnglobe();
		
		WebAnotacion anota = new WebAnotacion();
		WebCiudadano ciudadano = new WebCiudadano();
		ciudadano.setApellido1("CEBALLOS");
		ciudadano.setApellido2("ACOSTA");
		ciudadano.setNombre("FABIAN");
		ciudadano.setNumDocumento("80150367");
		ciudadano.setTipoDocumento("CC");
		anota.addCiudadano(ciudadano);
		
		WebDocumento doc = new WebDocumento();
		doc.setComentario("Comentario de este documento");
		doc.setOficinaInternacional("Hosuton, texas USA");
		doc.setIdTipoDocumento("1");
		anota.setDocumento(doc);
		
		anota.setComentario("Dueño de todo");
		anota.setIdDocumento("32432");
		anota.setValor("4534");
		
		WebFolioHeredado folioHeredado = new WebFolioHeredado();
		folioHeredado.setIdMatricula("400-1000");
		
		WebAnotaHereda anotacion = new WebAnotaHereda();
		anotacion.setIdAnotacion("4");
		
		folioHeredado.addAnotacionesHeredada(anotacion);
		
		englobe.addFoliosHeredado(folioHeredado);
		
		englobe.setAnotacion(anota);
		
		//Ahora lo de englobe:
		englobe.setIdMatriculaUbicacion("400-200");
		
		WebFolioEnglobe folioEnglobe1 = new WebFolioEnglobe();
		folioEnglobe1.setIdMatricula("400-1");
		
		WebFolioEnglobe folioEnglobe2 = new WebFolioEnglobe();
		folioEnglobe2.setIdMatricula("400-2");
		
		englobe.addFoliosEnglobar(folioEnglobe1);
		englobe.addFoliosEnglobar(folioEnglobe2);
		
		//FolioNuevo
		
		WebFolioNuevo folioNuevo = new WebFolioNuevo();
		folioNuevo.setArea("66 mts2");
		folioNuevo.setDescripcion("Finca grande");
		folioNuevo.setNombre("La macarena");
		
		WebDireccion direccion = new WebDireccion();
		direccion.setIdEje1("A");
		direccion.setIdEje2("#");
		direccion.setValorEje1("Autopiesta norte");
		direccion.setValorEje2("56-45");
		
		WebDireccion direccion2 = new WebDireccion();
		direccion2.setIdEje1("A");
		direccion2.setIdEje2("#");
		direccion2.setValorEje1("Autopiesta norte");
		direccion2.setValorEje2("33-33");
		
		folioNuevo.addDireccione(direccion);
		folioNuevo.addDireccione(direccion2);
		
		englobe.setFolioNuevo(folioNuevo);
		
		
		try {
			this.actualizarWebSegEng(operacionID, englobe);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			Log.getInstance().error(JDOWebSegEngDAO.class,e);
		}
	}
	

	private void testConsultarWebSegEng(){
		Log.getInstance().debug(JDOWebSegEngDAO.class,"PRUEBA ELIMINAR SEGENG");
		WebSegEngPk operacionID = new WebSegEngPk();
		operacionID.idSolicitud = "2005-400-6-10";
		operacionID.idWebSegeng = "5";
		
		
		try {
			WebSegEng operacion = this.consultarWebSegEng(operacionID);
			Log.getInstance().debug(JDOWebSegEngDAO.class,"Operacion Obtenida de tipo: "+operacion.getClass().getName());
			//WebEnglobe eng = (WebEnglobe)operacion;
			//Log.getInstance().debug(JDOWebSegEngDAO.class,"Descripcion del folio nuevo: "+eng.getFolioNuevo().getDescripcion());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			Log.getInstance().error(JDOWebSegEngDAO.class,e);
		}
	}
	 

	/**
	 * @see gov.sir.hermod.dao.WebSegEngDAO#crearWebSegEng(WebSegEng, Solicitud.SolicitudPk)
	 */
	public WebSegEngPk crearWebSegEng(WebSegEng operacion, SolicitudPk solID) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();

        WebSegEngEnhanced datos = WebSegEngEnhanced.enhance(operacion);
        WebSegEngPk rta = null;

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            SolicitudEnhanced solicitud = solDAO.getSolicitudByID(new SolicitudEnhancedPk(solID), pm);
            
            if(solicitud==null){
            	throw new DAOException("La solicitud no existe: "+solID.idSolicitud);
            }
            
        	//1 Asignación de llave primaria
     	    datos.setIdWebSegeng(String.valueOf(solicitud.getLastIdWebSegEng()+1));
     	    solicitud.setLastIdWebSegEng(solicitud.getLastIdWebSegEng()+1);
     	    datos.setSolicitud(solicitud);
     	   
            datos = this.crearWebSegEng(datos, solicitud, pm);
           
 
            pm.currentTransaction().commit();
            
            rta = new WebSegEngPk();
            rta.idSolicitud = datos.getIdSolicitud();
            rta.idWebSegeng = datos.getIdWebSegeng();
            
            return rta;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         finally {
            pm.close();
        }
	}
	
	/**
	 * Crea una operación de segregación o englobe en una transacción
	 * @param operacion
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected WebSegEngEnhanced crearWebSegEng(WebSegEngEnhanced operacion, SolicitudEnhanced sol, PersistenceManager pm) throws DAOException{
		JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
		try {
    	   //1. Datos comunes de englobe y segregación
    	   
    	   //1.1 Usuario
    	   if(operacion.getUsuario()!=null){
    		   UsuarioEnhancedPk usuarioID = new UsuarioEnhancedPk();
    		   usuarioID.idUsuario = operacion.getUsuario().getIdUsuario();
    		   
    		   UsuarioEnhanced usu = solDAO.getUsuarioByID(usuarioID, pm);
    		   
    		   if(usu==null){
    			   throw new DAOException("El usuario con ID "+usuarioID.idUsuario+ " no existe");
    		   }
    	   }
    	   
    	   
    	   //1.2 Anotacion de englobe o segregacion
    	   WebAnotacionEnhanced anota = operacion.getAnotacion();
    	   
    	   if(anota!=null){
    		   this.setAnotacionToWebSegEng(operacion, anota, pm);
    	   }
    	   
    	   //1.3 Folios y anotaciones que se heredan
    	   for(Iterator it = operacion.getFoliosHeredados().iterator(); it.hasNext();){
    		   WebFolioHeredadoEnhanced folioHeredado = (WebFolioHeredadoEnhanced)it.next();
    		   if(folioHeredado.getIdMatricula()==null){
    			   throw new DAOException("El folio heredado debe tener idMatricula");
    		   }
    		 
    		   folioHeredado.setWebSegEng(operacion);
    		   
    		   for(Iterator it2 = folioHeredado.getAnotacionesHeredadas().iterator(); it2.hasNext();){
    			   WebAnotaHeredaEnhanced anotaHeredada = (WebAnotaHeredaEnhanced)it2.next();
    			   if(anotaHeredada.getIdAnotacion()==null){
    				   throw new DAOException("La anotación heredada debe tener idAnotacion");
    			   }
    			   anotaHeredada.setFolio(folioHeredado);
    		   }
    	   }
    	   
    	   
    	   //2. Datos de segregación
    	   if(operacion instanceof WebSegregacionEnhanced){
    		   WebSegregacionEnhanced segregacion = (WebSegregacionEnhanced)operacion;
    		   int j = 1;
    		   for(Iterator it = segregacion.getFoliosDerivados().iterator();it.hasNext();){
    			   WebFolioDerivadoEnhanced folioDerivado = (WebFolioDerivadoEnhanced)it.next();
    			   folioDerivado.setSegregacion(segregacion);
    			   folioDerivado.setIdWebFolioDerivado(String.valueOf(j));
    			   folioDerivado.setOrden(j);
    			   j++;
    		   }
    	   }
    	   
    	   //3. Datos de englobe
    	   else if(operacion instanceof WebEnglobeEnhanced){
    		   WebEnglobeEnhanced englobe = (WebEnglobeEnhanced)operacion;
    		   
    		   for(Iterator it = englobe.getFoliosEnglobars().iterator();it.hasNext();){
    			   WebFolioEnglobeEnhanced folioEnglobe = (WebFolioEnglobeEnhanced)it.next();
    			   if(folioEnglobe.getIdMatricula()==null){
    				   throw new DAOException("El folio englobado debe tener idMatricula");
    			   }
    			       			   
    			   folioEnglobe.setEnglobe(englobe);
    		   }
    		   
    		   if(englobe.getFolioNuevo()!=null){
    			   this.setFolioNuevoToEnglobe(englobe, englobe.getFolioNuevo(), pm);
    			   
    		   }
    	   }

    	   pm.makePersistent(operacion);

           return operacion;
        } catch (JDOException e) {
            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
	}

	/**
	 * Setea el folio nuevo de un englobe para que este se pueda hacer persistente
	 * @param englobe
	 * @param folioNuevo
	 * @param pm
	 */
	private void setFolioNuevoToEnglobe(WebEnglobeEnhanced englobe, WebFolioNuevoEnhanced folioNuevo, PersistenceManager pm) {
		folioNuevo.setEnglobe(englobe);
		
		//direcciones
		int i = 1;
		for(Iterator it = folioNuevo.getDirecciones().iterator(); it.hasNext();){
			WebDireccionEnhanced direccion = (WebDireccionEnhanced) it.next();
			direccion.setFolioNuevo(folioNuevo);
			direccion.setIdWebDireccion(String.valueOf(i));
			i++;
		}
	}

	/**
	 * Setea la anotacion a la operación de englobe o segregación para que esta se pueda hacer persistente
	 * @param operacion
	 * @param anota
	 * @param pm
	 * @throws DAOException 
	 */
	private void setAnotacionToWebSegEng(WebSegEngEnhanced operacion, WebAnotacionEnhanced anota, PersistenceManager pm) throws DAOException {
		 anota.setWebSegEng(operacion);
		 
		 //Ciudadanos:
		 int i = 1;
		 for(Iterator it = anota.getCiudadanos().iterator(); it.hasNext();){
			 WebCiudadanoEnhanced ciudadano = (WebCiudadanoEnhanced)it.next();
			 ciudadano.setAnotacion(anota);
			 ciudadano.setIdWebCiudadano(String.valueOf(i));
			 i++;
		 }
		 
		 //Documento
		 if(anota.getDocumento()!=null){
			 WebDocumentoEnhanced doc = anota.getDocumento();
			 if(doc.getIdTipoDocumento()==null){
				 throw new DAOException("El documento debe tener IdTipoDocumento");
			 }
			 doc.setAnotacion(anota);
		 }
		 

	}

	/**
	 * @see gov.sir.hermod.dao.WebSegEngDAO#eliminarWebSegEng(WebSegEng.WebSegEngPk)
	 */
	public boolean eliminarWebSegEng(WebSegEngPk operacionID) throws DAOException {
       PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            WebSegEngEnhanced operacionToDelete = this.getWebSegEngByID(new WebSegEngEnhancedPk(operacionID), pm);
            
            if(operacionToDelete==null){
            	throw new DAOException("La operación de englobe o segregación no existe");
            }
            
            this.eliminarWebSegEng(operacionToDelete, pm);

            pm.currentTransaction().commit();
    
            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
        finally {
            pm.close();
        }
	}
	
	/**
	 * Elimina una operación de englobe o segregación en una transacción
	 * @param operacionToDelete
	 * @param pm
	 * @throws DAOException
	 */
	protected void eliminarWebSegEng(WebSegEngEnhanced operacionToDelete, PersistenceManager pm) throws DAOException{
		try {
    	   pm.deletePersistent(operacionToDelete);
        } catch (JDOException e) {
            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
	}
	
	/**
	 * Obtiene un objeto WebSehEngEnhanced persistente dado su ID
	 * @param cID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected WebSegEngEnhanced getWebSegEngByID(WebSegEngEnhancedPk cID,
			 PersistenceManager pm) throws DAOException {
		WebSegEngEnhanced rta = null;

		if ((cID.idSolicitud != null)&&(cID.idWebSegeng!=null)) {
			try {
				rta = (WebSegEngEnhanced) pm.getObjectById(cID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	/**
	 * @see gov.sir.hermod.dao.WebSegEngDAO#actualizarWebSegEng(WebSegEng.WebSegEngPk, WebSegEng)
	 */
	public boolean actualizarWebSegEng(WebSegEngPk operacionID, WebSegEng operacion) throws DAOException {
	    PersistenceManager pm = AdministradorPM.getPM();

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();
            
            //1. Se obtiene la operación persistente
            WebSegEngEnhanced operacionToDelete = this.getWebSegEngByID(new WebSegEngEnhancedPk(operacionID), pm);
            
            if(operacionToDelete==null){
            	throw new DAOException("La operación de englobe o segregación no existe");
            }
            
            SolicitudEnhanced solicitud = operacionToDelete.getSolicitud();
            
            //2. Se elimina y se retrocede el secuencial
            //solicitud.setLastIdWebSegEng(solicitud.getLastIdWebSegEng()-1);
            this.eliminarWebSegEng(operacionToDelete, pm);
            
            //3. Se mandan las sentencias a la base de datos
            VersantPersistenceManager pm2 = (VersantPersistenceManager)pm;
            pm2.flush();
            
            WebSegEngEnhanced datos = WebSegEngEnhanced.enhance(operacion);
            
            //4. Se crea de nuevo la operación
            datos.setSolicitud(solicitud);
            datos.setIdWebSegeng(operacionID.idWebSegeng);
            
            
            this.crearWebSegEng(datos, solicitud, pm);
            

            pm.currentTransaction().commit();
    
            return true;
        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw e;
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } 
         finally {
            pm.close();
        }
	}


	/**
	 * @see gov.sir.hermod.dao.WebSegEngDAO#consultarWebSegEng(WebSegEng.WebSegEngPk)
	 */
	public WebSegEng consultarWebSegEng(WebSegEngPk operacionID) throws DAOException {
	    PersistenceManager pm = AdministradorPM.getPM();
	    WebSegEng rta = null;
	    WebSegEngEnhanced operacion = null;
        try {
            //1. Se obtiene la operación persistente
            operacion = this.getWebSegEngByID(new WebSegEngEnhancedPk(operacionID), pm);
            
            this.makeTransientWebSegEng(operacion, pm);
            
        } catch (JDOException e) {
            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw e;
        } finally {
            pm.close();
        }
        
        if(operacion!=null){
        	rta = (WebSegEng)operacion.toTransferObject();
        }
        return rta;
	}

	
	/**
	 * Hace transiente la operqación de englobe o segregación
	 * @param operacion
	 * @param pm
	 */
	private void makeTransientWebSegEng(WebSegEngEnhanced operacion, PersistenceManager pm) {
		if(operacion!=null){
			pm.makeTransient(operacion.getUsuario());
			this.makeTransientWebAnotacion(operacion.getAnotacion(), pm);
			
			for(Iterator it = operacion.getFoliosHeredados().iterator(); it.hasNext();){
				WebFolioHeredadoEnhanced folioHeredado = (WebFolioHeredadoEnhanced)it.next();
				this.makeTransienteFolioHeredado(folioHeredado, pm);
			}
		
	   	   //2. Datos de segregación
    	   if(operacion instanceof WebSegregacionEnhanced){
    		   WebSegregacionEnhanced segregacion = (WebSegregacionEnhanced)operacion;
    		   
    		   for(Iterator it = segregacion.getFoliosDerivados().iterator();it.hasNext();){
    			   WebFolioDerivadoEnhanced folioDerivado = (WebFolioDerivadoEnhanced)it.next();
    			   pm.makeTransient(folioDerivado);
    		   }
    	   }
    	   
    	   //3. Datos de englobe
    	   else if(operacion instanceof WebEnglobeEnhanced){
    		   WebEnglobeEnhanced englobe = (WebEnglobeEnhanced)operacion;
    		   
    		   for(Iterator it = englobe.getFoliosEnglobars().iterator();it.hasNext();){
    			   WebFolioEnglobeEnhanced folioEnglobe = (WebFolioEnglobeEnhanced)it.next();
    			   pm.makeTransient(folioEnglobe);
    		   }
 
    		 this.makeTransientFolioNuevo(englobe.getFolioNuevo(), pm);
    		   
    	   }

    	   pm.makeTransient(operacion);	
		}
		
	}
	
	/**
	 * Hace transiente el folio nuevo
	 * @param folioNuevo
	 * @param pm
	 */
	private void makeTransientFolioNuevo(WebFolioNuevoEnhanced folioNuevo, PersistenceManager pm) {
		if(folioNuevo!=null){
			for(Iterator it = folioNuevo.getDirecciones().iterator(); it.hasNext();){
				WebDireccionEnhanced direccion = (WebDireccionEnhanced)it.next();
				pm.makeTransient(direccion);
			}
			pm.makeTransient(folioNuevo);
		}
	}


	/**
	 * Hace transiente el folio heredado
	 * @param folioHeredado
	 * @param pm
	 */
	private void makeTransienteFolioHeredado(WebFolioHeredadoEnhanced folioHeredado, PersistenceManager pm) {
		if(folioHeredado!=null){
			for(Iterator it = folioHeredado.getAnotacionesHeredadas().iterator(); it.hasNext();){
				WebAnotaHeredaEnhanced anotaHereda = (WebAnotaHeredaEnhanced)it.next();
				pm.makeTransient(anotaHereda);
			}
			pm.makeTransient(folioHeredado);
		}
	}


	/**
	 * Hace transiente la anotacion
	 * @param anotacion
	 * @param pm
	 */
	private void makeTransientWebAnotacion(WebAnotacionEnhanced anotacion, PersistenceManager pm) {
		if(anotacion!=null){
			for(Iterator it = anotacion.getCiudadanos().iterator(); it.hasNext();){
				WebCiudadanoEnhanced ciudadano = (WebCiudadanoEnhanced)it.next();
				pm.makeTransient(ciudadano);
			}
			
			if(anotacion.getDocumento()!=null){
				pm.makeTransient(anotacion.getDocumento());
			}
			pm.makeTransient(anotacion);
		}
	}
	
	/**
	 * 
	 * @param sol
	 * @return
	 * @throws DAOException
	 */
	public List getWebSegEngBySolicitud(SolicitudPk sol) throws DAOException{
	    PersistenceManager pm = AdministradorPM.getPM();
	    List rta = new ArrayList();
        try {
        	Query query = pm.newQuery(WebSegEngEnhanced.class);
        	query.declareParameters("String idSol");
        	query.setFilter("this.idSolicitud==idSol");
        	Collection col = (Collection)query.execute(sol.idSolicitud);
        	for (Iterator iter = col.iterator(); iter.hasNext();) {
        	    WebSegEngEnhanced websegengenhanced = (WebSegEngEnhanced)iter.next(); 
        	    this.makeTransientWebSegEng(websegengenhanced, pm);
        	    rta.add(websegengenhanced);
        	}
        	query.closeAll();

        } catch (JDOException e) {
            Log.getInstance().error(JDOWebSegEngDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }
        
        rta = TransferUtils.makeTransferAll(rta);
        
        return rta;
	}
	
	
	


}
