package gov.sir.forseti.dao.impl.jdogenie;

import java.util.List;

import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoDocumentoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhancedPk;
import gov.sir.forseti.dao.DAOException;
import gov.sir.forseti.dao.MigracionIncrementalDAO;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

public class JDOGenieMigracionIncrementalDAO extends JDOGenieFolioDAO implements MigracionIncrementalDAO{
	
	
	/**
	 * Unica instancia de la clase, implementa el patron Singleton.
	 */
	private static JDOGenieMigracionIncrementalDAO instance = new JDOGenieMigracionIncrementalDAO();

	/**
	 * Constructor privado de la clase.
	 * Implementa el patron Singleton.
	 */
	private JDOGenieMigracionIncrementalDAO(){
	}
	
	
	
	public static JDOGenieMigracionIncrementalDAO getInstance() {
		return instance;
	}


/*
	public Folio migrarFolioByMatricula(String matricula) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
*/
	public Turno migrarTurnoNumero(String numTurno) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	  /**
	    * Obtiene un objeto Folio dado el número de matrícula
	    * @param matricula número de matrícula del folio
	    * @return Objeto Folio con sus objetos estado, complementacion, tipoPredio y ZonaRegistral.
	    * El objeto ZonaRegistral contiene la jerarquía circulo, vereda, municipio y departamento
	    * null si  no encuentra un folio que coincida con el identificador dado
	    * @throws DAOException
	    */
	    public String migrarFolioByMatricula(String matricula, Usuario usuario) throws DAOException {	
	    	
	    	DAOException exception = new DAOException();
	    	
	    	PersistenceManager pmOrigen  = AdministradorPMMigracionIncremental.getPM();
	    	boolean existeOrigen = this.existeMatricula(matricula,pmOrigen);
	    	if(!existeOrigen){
	    		exception.addError("La matrícula: "+matricula+" no existe en BD Temporal(SIR).");
	    		throw exception;
	    	}
	    		    	
	        Folio folioOrigen = this.getFolioDefinitivoByMatricula(matricula,pmOrigen);
	        if (folioOrigen.getAnotaciones() == null || folioOrigen.getAnotaciones().isEmpty())
	        {
		        FolioPk fpk = new FolioPk();
		        fpk.idMatricula = matricula;
		        List anotacionesFolio = this.getAnotacionesFolio(matricula);
		        folioOrigen.setAnotaciones(anotacionesFolio);
	        }
	        
	        PersistenceManager pmDestino = AdministradorPM.getPM();
	        boolean existeDestino = this.existeMatricula(matricula,pmDestino);

	        if(!existeDestino){
		       	this.validarCrearFolio(folioOrigen,false);       
		       	this.crearFolio(folioOrigen,usuario, null, false);	        	
	        }
	        else {
	        	this.updateFolioMigracionIncremental(folioOrigen,pmDestino);
	        }
	        
	        
	        return matricula;
	    }	

	    
	    
	private void updateFolioMigracionIncremental(Folio origen, PersistenceManager pm) throws DAOException{
				
		try
		{
			Transaction transaction =  pm.currentTransaction();
			transaction.setOptimistic(false);
			transaction.begin();
		
			
			FolioEnhancedPk fid = new FolioEnhancedPk();
			fid.idMatricula = origen.getIdMatricula();
			FolioEnhanced folio = (FolioEnhanced)pm.getObjectById(fid,true);
			
			//TODO COMPLETAR LOGICA DE ACTUALIZACION
			
			folio.setCodCatastral(origen.getCodCatastral());
			folio.setLindero(origen.getLindero());
			folio.setFechaApertura(origen.getFechaApertura());
			folio.setDefinitivo(origen.isDefinitivo());
			folio.setLastIdDireccion(origen.getLastIdDireccion());
			folio.setLastIdAnotacion(origen.getLastIdAnotacion());
			folio.setLastIdSalvedad(origen.getLastIdSalvedad());
			folio.setRadicacion(origen.getRadicacion());
			folio.setUsuarioGrabacion(origen.getUsuarioGrabacion());
			folio.setCodCatastralAnterior(origen.getCodCatastralAnterior());
			folio.setInconsistente(origen.isInconsistente());
			
			folio = this.getEstadoMigracionIncremental(origen,folio,pm);
			folio = this.getComplementacionMigracionIncremental(origen,folio,pm);
			folio = this.getTipoPredioMigracionIncremental(origen,folio,pm);
			folio = this.getZonaRegistralMigracionIncremental(origen,folio,pm);
			folio = this.getDocumentoMigracionIncremental(origen,folio,pm);
			folio = this.getUsuarioMigracionIncremental(origen,folio,pm);
			
			
			/*
			List anotaciones = folio.getAnotaciones();
			Anotacion anota;
			for(int i=0; i<anotaciones.size(); i++){
				anota = (Anotacion)anotaciones.get(i);
				
			}
			*/
/*			
			private List anotaciones = new ArrayList(); // contains Anotacion  inverse Anotacion.folio
*/
			
			
			
			
			transaction.commit();
		}
		catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			DAOException exc = new DAOException();
			exc.addError(e.getMessage());
            throw exc;
		}
		finally {
            pm.close();
        }
		
	}   
	
	/**
	 * Esta función actualiza el estado del Folio al mismo del objeto "origen".
	 * Intenta recuperar el estado de la Base de datos definitiva, en caso de no
	 * encontrarlo crea uno nuevo.
	 * @param origen Folio de la Base de Datos temporal de donde se obtiene la información para la
	 * actualizació del estado del Folio en la Base de datos definitiva.
	 * @param folio Objeto que representa un folio en la base de datos definitiva y en el cual se
	 * va a realizar la actualización del estado.
	 * @param pm PersistentManager de la Base de datos definitiva.
	 * @return El FolioEnhanced con el estado actualizado.
	 */
	private FolioEnhanced getEstadoMigracionIncremental(Folio origen, FolioEnhanced folio, PersistenceManager pm){
		

		
		EstadoFolioEnhanced estado = null;
		try
		{
			EstadoFolioEnhancedPk estId = new EstadoFolioEnhancedPk();
			estId.idEstado = origen.getEstado().getIdEstado();
			
			estado = (EstadoFolioEnhanced)pm.getObjectById(estId,true);
			estado.setComentario(origen.getEstado().getComentario());
		}
		catch (JDOObjectNotFoundException e){
			estado = EstadoFolioEnhanced.enhance(origen.getEstado());
		}
		catch (Exception e){
		}
		
		
		folio.setEstado(estado);
		
		return folio;
	}
	
	/**
	 * Esta función actualiza la complementacion del Folio al mismo del objeto "origen".
	 * Intenta recuperar la complementación de la Base de datos definitiva, en caso de no
	 * encontrarla crea una nueva.
	 * @param origen Folio de la Base de Datos temporal de donde se obtiene la información para la
	 * actualización de la complementacion del Folio en la Base de datos definitiva.
	 * @param folio Objeto que representa un folio en la base de datos definitiva y en el cual se
	 * va a realizar la actualización de la complementacion.
	 * @param pm PersistentManager de la Base de datos definitiva.
	 * @return El FolioEnhanced con la complementacion actualizada.
	 */
	private FolioEnhanced getComplementacionMigracionIncremental(Folio origen, FolioEnhanced folio, PersistenceManager pm){
				
		ComplementacionEnhanced complementacion = null;
		try
		{
			ComplementacionEnhancedPk compId = new ComplementacionEnhancedPk();
			compId.idComplementacion = origen.getComplementacion().getIdComplementacion();
			
			complementacion = (ComplementacionEnhanced)pm.getObjectById(compId,true);
			complementacion.setComplementacion(origen.getComplementacion().getComplementacion());
		}
		catch (JDOObjectNotFoundException e){
			complementacion = ComplementacionEnhanced.enhance(origen.getComplementacion());
		}
		catch (Exception e){
		}
		
		folio.setComplementacion(complementacion);
		
		return folio;
	}
	
	/**
	 * Esta función actualiza el tipo de Predio del Folio al mismo del objeto "origen".
	 * Intenta recuperar el tipo de Predio de la Base de datos definitiva, en caso de no
	 * encontrarlo crea uno nuevo.
	 * @param origen Folio de la Base de Datos temporal de donde se obtiene la información para la
	 * actualización del tipo de predio del Folio en la Base de datos definitiva.
	 * @param folio Objeto que representa un folio en la base de datos definitiva y en el cual se
	 * va a realizar la actualización del tipo de predio.
	 * @param pm PersistentManager de la Base de datos definitiva.
	 * @return El FolioEnhanced con el tipo de predio actualizado.
	 */
	private FolioEnhanced getTipoPredioMigracionIncremental(Folio origen, FolioEnhanced folio, PersistenceManager pm){
		
		
		TipoPredioEnhanced tipoPredio = null;
		try
		{
			TipoPredioEnhancedPk tpId = new TipoPredioEnhancedPk();
			tpId.idPredio = origen.getTipoPredio().getIdPredio();

			
			tipoPredio = (TipoPredioEnhanced)pm.getObjectById(tpId,true);
			tipoPredio.setDescripcion(origen.getTipoPredio().getDescripcion());
		}
		catch (JDOObjectNotFoundException e){
			tipoPredio = TipoPredioEnhanced.enhance(origen.getTipoPredio());
		}
		catch (Exception e){
		}
		
		folio.setTipoPredio(tipoPredio);
		
		return folio;
	}
	
	/**
	 * Esta función actualiza la zona registral del Folio al mismo del objeto "origen".
	 * Intenta recuperar la zona registral de la Base de datos definitiva, en caso de no
	 * encontrarla crea una nueva.
	 * @param origen Folio de la Base de Datos temporal de donde se obtiene la información para la
	 * actualización de la zona registral del Folio en la Base de datos definitiva.
	 * @param folio Objeto que representa un folio en la base de datos definitiva y en el cual se
	 * va a realizar la actualización de la zona registral.
	 * @param pm PersistentManager de la Base de datos definitiva.
	 * @return El FolioEnhanced con la zona registral actualizada.
	 */
	private FolioEnhanced getZonaRegistralMigracionIncremental(Folio origen, FolioEnhanced folio, PersistenceManager pm){
		
		ZonaRegistralEnhancedPk zrId = new ZonaRegistralEnhancedPk();
		zrId.idZonaRegistral = origen.getZonaRegistral().getIdZonaRegistral();
		
		ZonaRegistralEnhanced zonaRegistral = null;
		try
		{
			zonaRegistral = (ZonaRegistralEnhanced)pm.getObjectById(zrId,true);
		}
		catch (JDOObjectNotFoundException e){
			zonaRegistral = ZonaRegistralEnhanced.enhance(origen.getZonaRegistral());
		}
		
		folio.setZonaRegistral(zonaRegistral);
		
		return folio;
	}
	
	/**
	 * Esta función actualiza el documento del Folio al mismo del objeto "origen".
	 * Intenta recuperar el documento de la Base de datos definitiva, en caso de no
	 * encontrarlo crea uno nuevo.
	 * @param origen Folio de la Base de Datos temporal de donde se obtiene la información para la
	 * actualización del documento del Folio en la Base de datos definitiva.
	 * @param folio Objeto que representa un folio en la base de datos definitiva y en el cual se
	 * va a realizar la actualización del documento.
	 * @param pm PersistentManager de la Base de datos definitiva.
	 * @return El FolioEnhanced con el documento actualizado.
	 */
	private FolioEnhanced getDocumentoMigracionIncremental(Folio origen, FolioEnhanced folio, PersistenceManager pm){
		

		
		DocumentoEnhanced documento = null;
		Documento doc = origen.getDocumento();
		try
		{
			DocumentoEnhancedPk docId = new DocumentoEnhancedPk();
			docId.idDocumento = origen.getDocumento().getIdDocumento();
			
			documento = (DocumentoEnhanced)pm.getObjectById(docId,true);
			documento.setComentario(doc.getComentario());
		}
		catch (JDOObjectNotFoundException e){
			documento = DocumentoEnhanced.enhance(origen.getDocumento());
		}
		catch (Exception e){
		}
		
		if(doc!=null){
			TipoDocumentoEnhanced tipoDocumento = null;
			tipoDocumento = this.getTipoDocumento(doc.getTipoDocumento(),pm);
			documento.setTipoDocumento(tipoDocumento);
		}
		folio.setDocumento(documento);
		
		return folio;
	}
	
	
	private TipoDocumentoEnhanced getTipoDocumento(TipoDocumento tipoDoc, PersistenceManager pm){
		
		TipoDocumentoEnhanced tipoDocumento = null;
		try
		{
			TipoDocumentoEnhancedPk tdocId = new TipoDocumentoEnhancedPk();
			tdocId.idTipoDocumento = tipoDoc.getIdTipoDocumento();
			
			tipoDocumento = (TipoDocumentoEnhanced)pm.getObjectById(tdocId,true);
		}
		catch (JDOObjectNotFoundException e){
			tipoDocumento = TipoDocumentoEnhanced.enhance(tipoDoc);
		}
		catch (Exception e){
		}
		
		return tipoDocumento;
		
	}
	
	/**
	 * Esta función actualiza el usuario de creacion del Folio al mismo del objeto "origen".
	 * Intenta recuperar el usuario de creacion de la Base de datos definitiva, en caso de no
	 * encontrarlo crea uno nuevo.
	 * @param origen Folio de la Base de Datos temporal de donde se obtiene la información para la
	 * actualización del usuario de creacion del Folio en la Base de datos definitiva.
	 * @param folio Objeto que representa un folio en la base de datos definitiva y en el cual se
	 * va a realizar la actualización del usuario de creacion.
	 * @param pm PersistentManager de la Base de datos definitiva.
	 * @return El FolioEnhanced con el usuario de creacion actualizado.
	 */
	private FolioEnhanced getUsuarioMigracionIncremental(Folio origen, FolioEnhanced folio, PersistenceManager pm){
		
		UsuarioEnhanced usuario = null;

		try
		{
			UsuarioEnhancedPk usId = new UsuarioEnhancedPk();
			usId.idUsuario = origen.getUsuarioCreacion().getIdUsuario();
						
			usuario = (UsuarioEnhanced)pm.getObjectById(usId,true);
			usuario.setNombre(origen.getUsuarioCreacion().getNombre());
			usuario.setApellido1(origen.getUsuarioCreacion().getApellido1());
			usuario.setApellido2(origen.getUsuarioCreacion().getApellido2());
			usuario.setUsername(origen.getUsuarioCreacion().getUsername());
			
		}
		catch (JDOObjectNotFoundException e){
			usuario = UsuarioEnhanced.enhance(origen.getUsuarioCreacion());
		}
		catch (Exception e){
		}
		
		folio.setUsuarioCreacion(usuario);
		
		return folio;
	}		
	
	    
	private Folio getFolioDefinitivoByMatricula(String matricula, PersistenceManager pm) throws DAOException{
		
        FolioEnhanced rta = null;
        Folio folio = null;
		
	      try {
	            rta = this.getFolioByMatricula(matricula, pm);

	            if (rta != null) {
	                //Solo muestra la información del folio si éste
	                //es definitivo
	                if (rta.isDefinitivo()) {
	                    this.makeTransientFolio(rta, pm);
						//Revisar si el último estado tiene comentario para setearlo
						if(!rta.getHistorialEstados().isEmpty()){
							EstadoHistoriaEnhanced ultimoEstado = (EstadoHistoriaEnhanced)rta.getHistorialEstados().get(rta.getHistorialEstados().size()-1);
							EstadoFolioEnhanced estado = rta.getEstado();
							estado.setComentario(ultimoEstado.getComentario());
						}

	                } else {
	                    rta = null;
	                }
	            }
	        } catch (DAOException e) {
	        	e.addError(e.getMessage());
	            throw e;
	        } 

	        if (rta != null) {
	        	folio = (Folio) rta.toTransferObject();
				this.ordenarDirecciones(folio.getDirecciones());
	        }
		
		return folio;
	}    
	    
    /**
     * Especifica si existe una matrícula en el sistema
     * @param matricula
     * @return true: existe, false: No existe
     * @throws DAOException
     */
     private boolean existeMatricula(String matricula, PersistenceManager pm) throws DAOException {
         FolioEnhanced folio = null;
         boolean rta = false;
        
         try {
             folio = this.getFolioByMatricula(matricula, pm);

             if (folio != null) {
                 if (folio.isDefinitivo()) {
                     rta = true;
                 } else {
                     rta = false;
                 }
             }
         } catch (DAOException e) {
             e.addError(e.getMessage());
        	 throw e;
         } 

         return rta;
     }
     
     
   
      
      
	  
	

}
