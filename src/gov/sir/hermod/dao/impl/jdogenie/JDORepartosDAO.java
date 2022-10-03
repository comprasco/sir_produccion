/*
 * JDORepartosDAO.java
 * Clase que se encarga de proveer las funcionalidades para la
 * Creación, Consulta y Modificación de objetos en los procesos de Reparto
 * de Abogados, Reparto Notarial Y Restitución de Reparto Notarial.  
 * Creado  29 de octubre de 2004, 9:10
 */
 
package gov.sir.hermod.dao.impl.jdogenie;

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

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import gov.sir.hermod.interfaz.HermodServiceInterface;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CReparto;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.constantes.CTipoOficina;
import gov.sir.core.negocio.modelo.constantes.CCategoria;
import gov.sir.core.negocio.modelo.constantes.CAccionNotarial;

import gov.sir.core.web.acciones.repartonotarial.AWRepartoNotarial;

import org.auriga.smart.servicio.ServiceLocator;

import com.versant.core.jdo.VersantPersistenceManager;
import com.versant.core.jdo.VersantQuery;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.CausalRestitucionPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoNotarialPk;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.MinutaAccionNotarial;
import gov.sir.core.negocio.modelo.MinutaEntidadPublica;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.OficinaCategoria;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OtorganteNatural;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.RepartoNotarialPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.ZonaNotarial;

import gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CausalRestitucionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EntidadPublicaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.MinutaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEntidadEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.VeredaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ZonaNotarialEnhancedPk;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.dao.RepartosDAO;

/**
 * JDORepartosDAO.java
 * Clase que se encarga de proveer las funcionalidades para la
 * Creación, Consulta y Modificación de objetos en los procesos de Reparto
 * de Abogados, Reparto Notarial y Restitución Reparto Notarial. 
 * @author dlopez 
 */
public class JDORepartosDAO implements RepartosDAO {
    

	private JDOAuditoriaDAO auditoria = new JDOAuditoriaDAO();

	/**
	* Crea una nueva instacia de JDORepartosDAO 
	*/
	public JDORepartosDAO() {
	}

    
    
    /**********************************************************************/
    /*                                                                    */
    /*                  PROCESO REPARTO NOTARIAL                          */
    /*                                                                    */
    /**********************************************************************/
    

	/**
	* Obtiene una <code>Solicitud</code> dado su identificador.
	* <p>Método utilizado para transacciones
	* <p>El método retorna null si no se encuentra la <code>Solicitud</code>
	* con el identificador pasado como parámetro. 
	* @param sID identificador de la <code>Solicitud</code>
	* @param pm PersistenceManager de la transaccion
	* @return <code>Solicitud</code> con sus atributos
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Solicitud
	*/
	protected SolicitudEnhanced getSolicitudByID(SolicitudEnhancedPk sID,
		PersistenceManager pm) throws DAOException {
		SolicitudEnhanced rta = null;

		if (sID.idSolicitud != null) {
			try {
				rta = (SolicitudEnhanced) pm.getObjectById(sID, true);
				pm.makeTransient(rta.getCirculo());
				pm.makeTransient(rta.getProceso());
				
			} 
			
			//Se retorna null, si no se encuentra una solicitud con el id pasado
			//como parámetro. 
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().debug(JDORepartosDAO.class,e);
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/**
   * Obtiene una <code>LiquidacionEnhanced</code> dado su identificador.
   * <p>Método utilizado para transacciones
   * @param lID identificador de la <code>Liquidacion</code>
   * @param pm PersistenceManager de la transaccion
   * @return <code>Liquidacion</code> con sus atributos y jerarquia:
   * <code>Pago</code>, <code>Solicitud</code> <code>Círculo</code>
   * <code>Proceso</code> <code>Usuario</code>   
   * @throws DAOException
   * @see gov.sir.core.negocio.modelo.Liquidacion
   * @see gov.sir.core.negocio.modelo.LiquidacionEnhanced
   */
	protected LiquidacionEnhanced getLiquidacionEnhancedById(
			LiquidacionEnhancedPk lID) throws DAOException {
			
			LiquidacionEnhanced lr = null;
			PersistenceManager pm = AdministradorPM.getPM();

			try {
				lr = this.getLiquidacionByID(lID, pm);

			//Hacer transientes todos los elementos de la liquidacion de reparto notarial. 
			LiquidacionTurnoRepartoNotarialEnhanced l = (LiquidacionTurnoRepartoNotarialEnhanced) lr;

			//Círculo
			pm.makeTransient(l.getSolicitud().getCirculo());

			//Proceso
			pm.makeTransient(l.getSolicitud().getProceso());

			//Usuario
			pm.makeTransient(l.getSolicitud().getUsuario());

			SolicitudRepartoNotarialEnhanced sReparto = (SolicitudRepartoNotarialEnhanced) l.getSolicitud();

			pm.makeTransient(sReparto);
			sReparto.addLiquidacion(lr);
			pm.makeTransient(lr);
			}

			catch (DAOException e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}

				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw e;
			}
			 catch (Throwable e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}

				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
			 finally {
				pm.close();
			}

			return lr;
		}





	/**
   * Obtiene una <code>LiquidacionEnhanced</code> dado su identificador.
   * <p>Método utilizado para transacciones
   * @param lID identificador de la <code>Liquidacion</code>
   * @param pm PersistenceManager de la transaccion
   * @return <code>Liquidacion</code> con sus atributos y sin hacer 
   * transiente su jerarquia.
   * @throws DAOException
   * @see gov.sir.core.negocio.modelo.Liquidacion
   * @see gov.sir.core.negocio.modelo.LiquidacionEnhanced
   */
	protected LiquidacionEnhanced getLiquidacionByID(
			LiquidacionEnhancedPk lID, PersistenceManager pm)
			throws DAOException {
			LiquidacionEnhanced rta = null;

			if ((lID.idSolicitud != null) && (lID.idLiquidacion != null)) {
				try {
					rta = (LiquidacionEnhanced) pm.getObjectById(lID, true);
				} catch (JDOObjectNotFoundException e) {
					rta = null;
				} catch (JDOException e) {
					Log.getInstance().debug(JDORepartosDAO.class,e);
					Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
					throw new DAOException(e.getMessage(), e);
				}
			}

			return rta;
		}



	/** 
	 * Retorna una <code>Minuta</code> persistente, dado el id del workflow,
	 * asociado con su <code>turno</code> de creación.
	 * <p>
	 * El método retorna null, si no se encuentra la minuta con el identificador
	 * de workflow pasado como parámetro. 
	 * @param wfId El id del workflow asociado con la creación de la <code>Minuta</code>.
	 * @return La <code>Minuta</code> con toda su información persistente. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
	 */
	public Minuta getMinutaByWFId(String wfId) throws DAOException {
		
		PersistenceManager pm = AdministradorPM.getPM();
		
		Minuta minuta = null;

		try {
		   
			pm.currentTransaction().begin();
			 
			//Obtener el id de la solicitud de reparto notarial, dado
			//el id de wf del turno.
			
			minuta = this.getMinutaByWFId(wfId, pm);
			pm.currentTransaction().commit(); 
		    				
		}
		
		catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		}
		catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
		finally {
			pm.close();
		}
			
			//Se retorna la Minuta y se hace transfer sobre la misma. 
			return minuta;
		
	}
	
	
	private Minuta getMinutaByWFId(String wfId, PersistenceManager pm) throws DAOException {
		
		MinutaEnhanced minEnh = new MinutaEnhanced();

		try {
		   
			//Obtener el id de la solicitud de reparto notarial, dado
			//el id de wf del turno.
			
			JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
			TurnoEnhanced turno = turnosDAO.getTurnoByWFId(wfId, pm);
			
			if (turno == null)
			{
				throw new DAOException("No se encontró ningún turno asociado.");
			}
			
			
			String idSolicitudMinuta = turno.getSolicitud().getIdSolicitud();
		
			//Obtener la minuta persistente dado el id de la solicitud asociada.
			VersantQuery query = (VersantQuery)pm.newQuery(MinutaEnhanced.class);
			query.declareParameters("String idSolicitudMinuta");
			query.setFilter("this.idMinuta == idSolicitudMinuta");
		    
			Collection col = (Collection)query.execute(idSolicitudMinuta);
		
			Iterator it = col.iterator();
			
			int size = col.size();
			
			//Se retorna null si no se encuentra la Minuta con el id de 
			//Workflow dado. 
			if (size ==0)
			{
				throw new DAOException("Turno inválido para la consulta de minutas.");
			}
			
			//Se hace transiente cada uno de los objetos asociados con la Minuta.     
			if ( it.hasNext() )
			{
				minEnh = (MinutaEnhanced) it.next();
				this.makeTransientMinuta(minEnh, pm);
			}
				/*
				//Hacer transiente la Acción Notarial (Tipo de Contrato).
				pm.makeTransient(minEnh.getAccionNotarial());
								
				
				//Hacer transiente la categoria asociada.
				pm.makeTransient(minEnh.getCategoria());
				
				//Hacer transiete la Notaría Asignada.
				if (minEnh.getOficinaCategoriaAsignada() != null)
				{
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio().getDepartamento());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada());
				}
				
				//Hacer transiente el reparto notarial.
				pm.makeTransient(minEnh.getRepartoNotarial());
				
						
				//Hacer transientes las Entidades Públicas.
				if (minEnh.getEntidadesPublicas() != null)
				{
					List listaEntidades = minEnh.getEntidadesPublicas();
					for (int j=0; j<listaEntidades.size(); j++ )
					{
						MinutaEntidadPublicaEnhanced minutaEntidad = (MinutaEntidadPublicaEnhanced)listaEntidades.get(j);
                    		
						pm.makeTransient(minutaEntidad.getEntidadPublica());
						pm.makeTransient(minutaEntidad);
                    		
					}
					pm.makeTransientAll( minEnh.getEntidadesPublicas());
				}
                    
					
				//Hacer transientes los otorgantes naturales.
				if (minEnh.getOtorgantesNaturales() != null)
				{
					List listaNaturales = minEnh.getOtorgantesNaturales();
					for (int k=0; k<listaNaturales.size(); k++ )
					{
						OtorganteNaturalEnhanced otorgante = (OtorganteNaturalEnhanced)listaNaturales.get(k);
                    		
						pm.makeTransient(otorgante);
											  
				    }
					pm.makeTransientAll (minEnh.getOtorgantesNaturales());
				}
												
				
              //Hacer transiente la minuta.
              pm.makeTransient(minEnh);
		    */
				
			query.close(col);

		    				
		}
		
		catch (JDOException e) {
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		catch (DAOException e) {
			throw (e);
		}
		catch (Throwable e) {
		}
			
			//Se retorna la Minuta y se hace transfer sobre la misma. 
			return (Minuta) minEnh.toTransferObject();
		
	}
	
	/** 
	 * Retorna una <code>Minuta</code> persistente, dado el id del workflow,
	 * asociado con su <code>turno</code> de creación.
	 * <p>
	 * El método retorna null, si no se encuentra la minuta con el identificador
	 * de workflow pasado como parámetro. 
	 * @param wfId El id del workflow asociado con la creación de la <code>Minuta</code>.
	 * @return La <code>Minuta</code> con toda su información persistente. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
     * @see gov.sir.core.negocio.modelo.Turno
	 */
	public List getMinutasByWFId(String wfId) throws DAOException {
		
		PersistenceManager pm = AdministradorPM.getPM();
		MinutaEnhanced minEnh = new MinutaEnhanced();
		List lista = new ArrayList();

		try {
		   
			pm.currentTransaction().begin();
			 
			//Obtener el id de la solicitud de reparto notarial, dado
			//el id de wf del turno.
			
			JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
			TurnoEnhanced turno = turnosDAO.getTurnoByWFId(wfId, pm);
			
			if (turno == null)
			{
				throw new DAOException("No se encontró ningún turno asociado.");
			}
			
			
			String idSolicitudMinuta = turno.getSolicitud().getIdSolicitud();
		
			//Obtener la minuta persistente dado el id de la solicitud asociada.
			VersantQuery query = (VersantQuery)pm.newQuery(MinutaEnhanced.class);
			query.declareParameters("String idSolicitudMinuta");
			query.setFilter("this.idMinuta == idSolicitudMinuta");
		    
			Collection col = (Collection)query.execute(idSolicitudMinuta);
		
			Iterator it = col.iterator();
			
			int size = col.size();
			
			//Se retorna null si no se encuentra la Minuta con el id de 
			//Workflow dado. 
			if (size ==0)
			{
				throw new DAOException("Turno inválido para la consulta de minutas.");
			}
			
			//Se hace transiente cada uno de los objetos asociados con la Minuta.     
			if ( it.hasNext() )
			{
				minEnh = (MinutaEnhanced) it.next();
				this.makeTransientMinuta(minEnh, pm);
			}
				/*
				//Hacer transiente la Acción Notarial (Tipo de Contrato).
				pm.makeTransient(minEnh.getAccionNotarial());
								
				
				//Hacer transiente la categoria asociada.
				pm.makeTransient(minEnh.getCategoria());
				
				//Hacer transiete la Notaría Asignada.
				if (minEnh.getOficinaCategoriaAsignada() != null)
				{
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio().getDepartamento());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada().getOficinaOrigen());
					pm.makeTransient(minEnh.getOficinaCategoriaAsignada());
				}
				
				//Hacer transiente el reparto notarial.
				pm.makeTransient(minEnh.getRepartoNotarial());
				
						
				//Hacer transientes las Entidades Públicas.
				if (minEnh.getEntidadesPublicas() != null)
				{
					List listaEntidades = minEnh.getEntidadesPublicas();
					for (int j=0; j<listaEntidades.size(); j++ )
					{
						MinutaEntidadPublicaEnhanced minutaEntidad = (MinutaEntidadPublicaEnhanced)listaEntidades.get(j);
                    		
						pm.makeTransient(minutaEntidad.getEntidadPublica());
						pm.makeTransient(minutaEntidad);
                    		
					}
					pm.makeTransientAll( minEnh.getEntidadesPublicas());
				}
                    
					
				//Hacer transientes los otorgantes naturales.
				if (minEnh.getOtorgantesNaturales() != null)
				{
					List listaNaturales = minEnh.getOtorgantesNaturales();
					for (int k=0; k<listaNaturales.size(); k++ )
					{
						OtorganteNaturalEnhanced otorgante = (OtorganteNaturalEnhanced)listaNaturales.get(k);
                    		
						pm.makeTransient(otorgante);
											  
				    }
					pm.makeTransientAll (minEnh.getOtorgantesNaturales());
				}
												
				
              //Hacer transiente la minuta.
              pm.makeTransient(minEnh);
		    */
			lista.add(minEnh);	
			query.close(col);
			pm.currentTransaction().commit(); 
		    				
		}
		
		catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			throw (e);
		}
		catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
		finally {
			pm.close();
		}
			
			//Se retorna la Minuta y se hace transfer sobre la misma. 
			lista = TransferUtils.makeTransferAll(lista);
			return lista;
	}




	/**
	* Retorna la <code>Categoria</code> en la cual se debe clasificar una <code>Minuta</code>,
	* teniendo como primer criterio de clasificación el valor  y como
	* segundo criterio el número de unidades.  
	* <p> El método lanza una excepción si la cuantía y el número de unidades de 
	* la <code>Minuta</code> son inválidos. 
	* @param minuta, la <code>Minuta</code> que se va a clasificar. 
	* @return la <code>Categoria</code> en la cual se debe clasificar la <code>Minuta</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.Categoria
	*/
	public Categoria getCategoriaClasificacionMinuta(Minuta minuta) throws DAOException {
  	
	  CategoriaEnhanced categoria = null;
	  PersistenceManager pm = AdministradorPM.getPM();
	  boolean repartida = false;
	
	  try {
		  
//		  double valorMaximo = 0;
//		  double unidadesMaximo = 0;
		  
		  // Ya que son varias acciones notariales las que se asocian a la minuta se valida de la lista de acciones que tengan 
		  //acciones tipo REGLAMENTO PROPIEDAD HORIZONTAL o URBANIZACIONES PARCELACIONES
		  boolean tieneAccionesPropiedadHorizontalOParcelaciones = false;
		  boolean marcadosSinCuantia = true;
		  boolean propiedadHorizonalLimite = false; //Que tenga hasta 100 unidades y sea de REGLAMENTO DE PROEIDAD HORIZONAL
		  boolean categoriaSeptima = false;
		  
		  
		  List accionesNotariales = minuta.getAccionesNotariales();
		  Iterator it = accionesNotariales.iterator();
		  while(it.hasNext()) {
			  MinutaAccionNotarial minutaAccionNotarial = (MinutaAccionNotarial)it.next();
			  AccionNotarial accionNotarial = minutaAccionNotarial.getAccionNotarial();
			  if(accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL)
					  || accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.URBANIZACIONES_PARCELACIONES)) {
				  tieneAccionesPropiedadHorizontalOParcelaciones = true;
			  }

			  if(accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.CATEGORIA_DECLARATORIA_DE_PRESCRIPCION_ADQUISITIVA)
					  || accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.DECLARACION_DE_LA_POSESION_REGULAR)) {
				  categoriaSeptima = true;
			  }

			  if(accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL)){
				  if (minutaAccionNotarial.getUnidades() <= CCategoria.LIMITE_REGLAMENTO_PROPIEDAD){
					  propiedadHorizonalLimite = true;
				  }
			  }

			  if (String.valueOf(minutaAccionNotarial.getConCuantia()).equals(CAccionNotarial.CON_CUANTIA)) {
				  marcadosSinCuantia = false;
			  }
//			  valorMaximo = valorMaximo + minutaAccionNotarial.getValor();
//			  unidadesMaximo = unidadesMaximo + minutaAccionNotarial.getUnidades();
		  }
		  
		  double valorMaximo = minuta.getValor();
		  double unidadesMaximo = minuta.getUnidades();
		  
		  
		  //CASO 1
		  //SI TODOS LOS OTORGANTES DE LA MINUTA SON EXENTOS, SE CLASIFICA EN LA
		  //CATEGORIA SEXTA.
		  List otorgantesPublicos = minuta.getEntidadesPublicas();
		  List otorgantesNaturales = minuta.getOtorgantesNaturales();
		  if (otorgantesPublicos!=null && otorgantesPublicos.size()>0 && (otorgantesNaturales!=null || (otorgantesNaturales.size()==0))) {
			boolean exenta= true;
			boolean condicion = true;
			if (otorgantesNaturales.size()!=0){
				condicion = false;
				exenta = false;
			}
		  	for (int i =0; i<otorgantesPublicos.size() && condicion; i++){
				MinutaEntidadPublica minEntidad = (MinutaEntidadPublica) otorgantesPublicos.get(i);
				EntidadPublica entidad = minEntidad.getEntidadPublica();
				if (entidad == null){
					throw new DAOException ("La entidad pública ingresada es incorrecta"); 
				}
				/*NaturalezaJuridicaEntidad natEntidad = entidad.getNaturalezaJuridica();
				if (natEntidad == null){
					throw new DAOException ("La entidad pública ingresada no tiene naturaleza juridica"); 
				}
				*/				
				//La entidad no es exenta, no se clasifica en sexta categoría.
				if (!entidad.isExento()){
					i = otorgantesPublicos.size()+1;
					exenta = false;
				}
				
			}
			//Si todas las entidades son exentas, se clasifica automaticamente en sexta categoria.
			if (exenta)	{
				CategoriaEnhancedPk idCategoria = new CategoriaEnhancedPk();
				idCategoria.idCategoria= CCategoria.CATEGORIA_EXENTA;
				categoria = (CategoriaEnhanced) pm.getObjectById(idCategoria,true);
				repartida = true;
			}
		  }
		  
		  /*boolean exentoVal = false;
		  boolean NoExentoVal = false;
		  
		  for (int i =0; i<otorgantesPublicos.size(); i++){
			  MinutaEntidadPublica minEntidad = (MinutaEntidadPublica) otorgantesPublicos.get(i);
			  EntidadPublica entidad = minEntidad.getEntidadPublica();
			  if (entidad == null){
				throw new DAOException ("La entidad pública ingresada es incorrecta"); 
			  }
			  NaturalezaJuridicaEntidad natEntidad = entidad.getNaturalezaJuridica();
			  if (natEntidad == null){
				  throw new DAOException ("La entidad pública ingresada no tiene naturaleza juridica"); 
			  }
			  //La entidad no es exenta, no se clasifica en sexta categoría.
			  if (natEntidad.isExento()){
				  exentoVal = true;
			  } else {
				  NoExentoVal = true;
			  }
		  }
		  
		  boolean exentoVal1 = false;
		  boolean NoExentoVal1 = false;
		  for (int i =0; i<otorgantesNaturales.size(); i++){
			  OtorganteNatural otorganteNatural = (OtorganteNatural) otorgantesNaturales.get(i);
			  //La entidad no es exenta, no se clasifica en sexta categoría.
			  if (otorganteNatural.isExento()){
				  exentoVal1 = true;
			  } else {
				  NoExentoVal1 = true;
			  }
		  }
		  
		  //SI UNO DE LOS OTORGANTES ES UN ENTE EXENTO DEL PAGO DE DERECHOS NOTARIALES
		  //Y OTRO NO EXENTO SE CLASIFICA EN CUARTA CATEGORIA
		  if((exentoVal || exentoVal1) && (NoExentoVal || NoExentoVal1)){
			  CategoriaEnhanced.ID idCategoria = new CategoriaEnhanced.ID();
			  idCategoria.idCategoria= CCategoria.CATEGORIA_CUARTA;
			  categoria = (CategoriaEnhanced) pm.getObjectById(idCategoria,true);
			  repartida = true;
		  }
		  
		 */
		  //CASO: LA ACCION NOTARIAL ES UN REGLAMENTO DE PROPIEDAD HORIZONTAL O
		  //UNA PARCELACION.
		  
		  if (repartida != true && (tieneAccionesPropiedadHorizontalOParcelaciones)) {
			    categoria = this.getCategoriaByUnidades(unidadesMaximo,pm);
				repartida = true;
		  }
		  
		  //CASO 2
		  //SI EL VALOR DE LA CUANTIA ES CERO, A LA MINUTA SE LE ASIGNA LA QUINTA
		  //CATEGORIA.
		  // SI TODOS LOS ACTOS ESTAN MARCADOS COMO SIN CUANTIA SE ASIGNA A LA QUINTA
		  if (repartida != true  && marcadosSinCuantia) {
			CategoriaEnhancedPk idCategoria = new CategoriaEnhancedPk();
			idCategoria.idCategoria= CCategoria.CATEGORIA_SIN_CUANTIA;
			categoria = (CategoriaEnhanced) pm.getObjectById(idCategoria,true);
			repartida = true;
		  	
		  }
		  
		  /*
		   * Cuando la accion notarial es CATEGORIA_DECLARATORIA_DE_PRESCRIPCION_ADQUISITIVA
		   * se asigna la septima categoria
		   */
		  
		  if(categoriaSeptima){
			  CategoriaEnhancedPk idCategoria = new CategoriaEnhancedPk();
			  idCategoria.idCategoria= CCategoria.CATEGORIA_SEPTIMA;
			  categoria = (CategoriaEnhanced) pm.getObjectById(idCategoria,true);
			  repartida = true;
		  }
		  
		  //CASO 3
		  //SE CLASIFICAN LAS MINUTAS DE ACUERDO CON EL VALOR DEL ACTO, Y CON LOS VALORES
		  //CONFIGURADOS EN LA TABLA DE CATEGORIAS.
		  if (repartida!= true)  {
			categoria = this.getCategoriaByMonto(valorMaximo,pm);
			repartida = true;
		  }
		  
		  
		  
		  //CON EL NUEVO LEVANTAMIENTO, NO SE TIENEN EN CUENTA LAS UNIDADES PARA LA
		  //CLASIFICACION. 
		  //Si la Minuta no tiene una cuantía, se determina la categoría de clasificación
		  //de acuerdo con el número de unidades.  
		  /*
		  else if (unidadesMaximo >0)
		  {
			categoria = this.getCategoriaByUnidades(unidadesMaximo,pm);
		  }
		  */
		  
		  
		  //Si la minuta no fue repartida, se genera una excepción. 
		  if (repartida != true) {
			throw new DAOException ("La minuta no pudo ser clasificada dentro de ninguna de las categorías."); 
		  }
		  
		  pm.makeTransient(categoria);
	  } catch (DAOException e) {
		  if (pm.currentTransaction().isActive()) {
			  pm.currentTransaction().rollback();
		  }

		  Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		  throw new DAOException(e.getMessage(), e);
	  } catch (Throwable e) {
		  if (pm.currentTransaction().isActive()) {
			  pm.currentTransaction().rollback();
		  }

		  Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		  throw new DAOException(e.getMessage(), e);
	  } finally {
		  pm.close();
	  }

	  return (Categoria) categoria.toTransferObject();

	}	
	
	/**
	* Retorna la <code>Categoria</code> en la cual se debe clasificar una <code>Minuta</code>
	* si el criterio de clasificación es por dinero. Método utilizado por
	* transacciones. 
	* <p>El método lanza una excepción si no se encuentra una <code>Categoria</code> cuyo 
	* rango coincida con el monto recibido como parámetro. 
	* @param monto, el monto de la <code>Minuta</code>.
	* @param pm El Persistence Manager de la transacción. 
	* @return la <code>Categoria</code> en la cual se debe clasificar la <code>Minuta</code>.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	* @see gov.sir.core.negocio.modelo.Minuta
	*/
	protected CategoriaEnhanced getCategoriaByMonto (double monto, PersistenceManager pm) throws DAOException
	{
		JDOVariablesOperativasDAO variablesOperativasDAO = new JDOVariablesOperativasDAO();
		List listaCategorias = new ArrayList();
    	
		try{
    	
		//Se obtiene el listado de Categorías. 
		listaCategorias = variablesOperativasDAO.getCategoriasReparto(CReparto.ORDEN_MONTO);
    	
		//Se realiza un ciclo sobre el listado de las Categorías, hasta encontrar una en la
		//cual el monto de la minuta quede dentro del rango de la Categoria. 
		for (int i= 0; i< listaCategorias.size(); i++)
		{
			Categoria cat = (Categoria) listaCategorias.get(i);
			double valor = cat.getValorMax();
			if (valor >= monto)
			{
				CategoriaEnhancedPk catId = new CategoriaEnhancedPk();
				CategoriaEnhanced catEnh = CategoriaEnhanced.enhance(cat);
				catId.idCategoria = cat.getIdCategoria();
				CategoriaEnhanced catPers = (CategoriaEnhanced) pm.getObjectById(catId,true);
				
				
				return catPers;
    			
			}
		}
		}
		catch(DAOException e)
		{
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);
		}
		catch (Throwable e){
				
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		}
		
		throw new DAOException ("No se encontró una categoría para los valores dados");
	}
    
    
    

	
	/**
	* Retorna la <code>Categoria</code> en la cual se debe clasificar una <code>Minuta</code>
	* si el criterio de clasificación es el número de unidades. Método utilizado por
	* transacciones. 
	* <p>El método lanza una excepción si no se encuentra una <code>Categoria</code> cuyo 
	* rango coincida con el número de unidades recibido como parámetro. 
	* @param unidades, el número de unidades de la <code>Minuta</code>.
	* @param pm El Persistence Manager de la transacción. 
	* @return la <code>Categoria</code> en la cual se debe clasificar la <code>Minuta</code>.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Categoria
	* @see gov.sir.core.negocio.modelo.Minuta
	*/
	protected CategoriaEnhanced getCategoriaByUnidades (double unidades, PersistenceManager pm) throws DAOException
	{
		JDOVariablesOperativasDAO variablesOperativasDAO = new JDOVariablesOperativasDAO();
		List listaCategorias = new ArrayList();
    	
		try{
    	
		listaCategorias = variablesOperativasDAO.getCategoriasReparto(CReparto.ORDEN_UNIDADES);
    	
		for (int i= 0; i< listaCategorias.size(); i++)
		{
			Categoria cat = (Categoria) listaCategorias.get(i);
			double valor = cat.getUnidadMax();
			if (valor >= unidades)
			{
				CategoriaEnhancedPk catId = new CategoriaEnhancedPk();
				CategoriaEnhanced catEnh = CategoriaEnhanced.enhance(cat);
				catId.idCategoria = cat.getIdCategoria();
				CategoriaEnhanced catPers = (CategoriaEnhanced) pm.getObjectById(catId,true);
	
				return catPers;
    			
			}
		}
		}
		catch(DAOException e)
		{
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);
		}
		catch (Throwable e){
				
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		}
		
		return null;	
	}




	/**
	* Crea una <code>Minuta</code> persistente y la asocia a una solicitud de reparto notarial
	* de minutas.
	* @param sol La <code>SolicitudRepartoNotarial</code> a la cual se va a asociar la 
	* <code>Minuta</code> recibida como parámetro.
	* @param min La <code>Minuta</code> que va a ser asociada a la 
	* <code>SolicitudRepartoNotarial</code> recibida como parámetro.
	* @return Una <code>SolicitudRepartoNotarial</code> con la <code>Minuta</code> recibida
	* como parámetro asociada.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
	* @see gov.sir.core.negocio.modelo.OficinaOrigen
	*/
	public SolicitudRepartoNotarial addMinutaToSolicitudReparto(Solicitud sol, Minuta min)
	throws DAOException 
	{
  	
	  PersistenceManager pm = AdministradorPM.getPM();
	  SolicitudRepartoNotarialEnhanced srn = null;
	  MinutaEnhanced minEnh = new MinutaEnhanced();

	  try {
		  pm.currentTransaction().setOptimistic(false);
		  pm.currentTransaction().begin();

		  //Se obtiene la solicitud reparto notarial persistente. 
		  SolicitudEnhancedPk srnId = new SolicitudEnhancedPk();
		  SolicitudRepartoNotarialEnhanced solEnh = (SolicitudRepartoNotarialEnhanced)SolicitudRepartoNotarialEnhanced.enhance(sol);
		  srnId.idSolicitud = solEnh.getIdSolicitud();

		  SolicitudRepartoNotarialEnhanced solPers = (SolicitudRepartoNotarialEnhanced) this.getSolicitudByID(srnId, pm);
		  if (solPers == null) {
			  throw new DAOException("No se encontró la solicitud de reparto notarial");
		  }
			
		  //Garantizar que la minuta tenga id de solicitud.
		  String idSolicitud = solPers.getIdSolicitud();
		  if 	(idSolicitud == null)
		  {
			  throw new DAOException("La solicitud de reparto notarial no tiene identificador");
		  }
		  minEnh.setIdMinuta(idSolicitud);
			
		  //Asignar  al atributo estado el valor no repartido.
		  minEnh.setEstado(CReparto.MINUTA_NO_REPARTIDA);
		
		  //Garantizar que el número de unidades o el valor de la minuta tengan un 
		  //valor diferente de cero.
		  //if (min.getUnidades() == 0 && min.getValor() ==0)
		  //{
		  //  throw new DAOException ("Deben asignarse el valor de la minuta o las unidades de la minuta");
		  //}
		  
		  //Asignar a la minuta las unidades y el valor.
		  minEnh.setUnidades(min.getUnidades());
		  minEnh.setValor(min.getValor());
		
		
		  //En este estado la minuta no debe tener oficina de categoria asociada.
		  minEnh.setOficinaCategoriaAsignada(null);
		
		  
		  //En este momento  la minuta no tiene ningún reparto notarial asociado.    
		  minEnh.setRepartoNotarial(null);
		
			
		  //Se valida que exista por lo menos una Acción Notarial asociada a la minuta.
		  //AccionNotarial accionNot = min.getAccionNotarial();
		  /*List accionesNotariales = min.getAccionesNotariales();
		  if (accionesNotariales.size() == 0 || accionesNotariales == null)
		  {
			  throw new DAOException ("La minuta debe tener asociada una acción notarial");
		  }*/
		  /*else
		  {
			  AccionNotarialEnhanced accNotEnh = new AccionNotarialEnhanced();
			  
			  accNotEnh = AccionNotarialEnhanced.enhance(accionNot);
			  AccionNotarialEnhanced.ID accId = new AccionNotarialEnhanced.ID();
			  accId.idAccionNotarial = accNotEnh.getIdAccionNotarial();
			  accNotEnh = this.getAccionNotarialById (accId,pm);
			
			  if (accNotEnh == null)
			  {
				  throw new DAOException ("La acción notarial con el id dado no existe");
			  }
			  minEnh.setAccionNotarial(accNotEnh);
			
		  }*/	
	
	
		  //Obtener la Categoria de la minuta. 
		  Categoria cat = new Categoria();	
		  
		  cat = this.getCategoriaClasificacionMinuta(min);
		  if (cat == null)
		  {
		  	throw new DAOException ("La Minuta no pudo ser clasificada dentro de una Categoria");
		  }else if (!cat.isActivo()){
			throw new DAOException ("La Minuta debe ser clasificada en la categoria " + cat.getNombre().toUpperCase() + ", pero esta se encuentra deshabilitada.");
		  }
		  CategoriaEnhancedPk idCat = new CategoriaEnhancedPk();
		  idCat.idCategoria = cat.getIdCategoria();
		  CategoriaEnhanced catEnh = (CategoriaEnhanced) pm.getObjectById(idCat,true);
		 
		  minEnh.setCategoria(catEnh);
		  
		  //Asignar los atributos tipoReparto, número de folios y observaciones.
		  minEnh.setComentario(min.getComentario());
		  minEnh.setNumeroFolios(min.getNumeroFolios());
		  minEnh.setNormal(min.isNormal());
	    
	    
	     //Se asocia el Circulo Notarial
	     CirculoNotarialEnhancedPk idNotarialEnh = new CirculoNotarialEnhancedPk();
	     idNotarialEnh.idCirculo = min.getCirculoNotarial().getIdCirculo();
	     
	     CirculoNotarialEnhanced notarialEnh = new CirculoNotarialEnhanced();
	     notarialEnh = (CirculoNotarialEnhanced) pm.getObjectById(idNotarialEnh,true);
	     minEnh.setCirculoNotarial(notarialEnh);
	     
         //Se asocia a la minuta, la fecha del sistema.
         minEnh.setFechaCreacion(new Date());

         //Se hace persistente la minuta y la relacion solicitud minuta. 
		 solPers.setMinuta(minEnh);
		 pm.makePersistent(minEnh);
		 
		 //SE HACEN PERSISTENTES LAS ASOCIACIONES ENTRE LOS OTORGANTES NATURALES Y LA MINUTA.
		 List listaNaturales = min.getOtorgantesNaturales();
		 if (listaNaturales != null )
		 {
		 	for (int i =0; i< listaNaturales.size(); i++)
		 	{
		 		OtorganteNatural otorganteNatural = (OtorganteNatural)listaNaturales.get(i);
		 		OtorganteNaturalEnhanced  otNatEnh = new OtorganteNaturalEnhanced();
		 		otNatEnh.setIdMinuta(minEnh.getIdMinuta());
		 		otNatEnh.setIdOtorgante(i+"");
		 		otNatEnh.setMinuta(minEnh);
		 		otNatEnh.setExento(otorganteNatural.isExento());
		 		otNatEnh.setNombre(otorganteNatural.getNombre());
		 		otNatEnh.setFechaCreacion(new Date());
		 		pm.makePersistent(otNatEnh);
		 		
		 	}
		 }
		 
		 //SE HACEN PERSISTENTES LAS ASOCIACIONES ENTRE LOS OTORGANTES PUBLICOS Y LA MINUTA.
		 List listaPublicas = min.getEntidadesPublicas();
		 if (listaPublicas != null )
		 {
			for (int j =0; j< listaPublicas.size(); j++)
			{
				MinutaEntidadPublica otorgantePublico = (MinutaEntidadPublica)listaPublicas.get(j);
				MinutaEntidadPublicaEnhanced  entPublicaEnh = new MinutaEntidadPublicaEnhanced();
				EntidadPublica entidad = otorgantePublico.getEntidadPublica();
				if (entidad == null)
				{
					throw new DAOException ("No se ingresó entidad pública otorgante.");
				}
				entPublicaEnh.setIdMinuta(minEnh.getIdMinuta());
				entPublicaEnh.setMinuta(minEnh);
				
				//Recuperar Entidad Publica Persistente.
				EntidadPublicaEnhancedPk idEntidad = new EntidadPublicaEnhancedPk ();
				idEntidad.idEntidadPublica = entidad.getIdEntidadPublica();
				EntidadPublicaEnhanced entidadEnhanced = (EntidadPublicaEnhanced)pm.getObjectById(idEntidad,true);
				if (entidadEnhanced == null)
				{
					throw new DAOException ("No se encontró entidad pública otorgante con id "+idEntidad.idEntidadPublica);
				}
				entPublicaEnh.setEntidadPublica(entidadEnhanced);
				entPublicaEnh.setIdEntidadPublica(entidadEnhanced.getIdEntidadPublica());
				entPublicaEnh.setFechaCreacion(new Date());
				entPublicaEnh.setExento(entidad.isExento());
				pm.makePersistent(entPublicaEnh);
		 		
			}
		 }
		 //	SE HACEN PERSISTENTES LAS ASOCIACIONES ENTRE LAS ACCIONES NOTARIALES Y LA MINUTA
		 long unidadesTotal = 0;
		 double valortotal = 0;
		 long conCuantia = 0;
		 
		 List listaAcciones = min.getAccionesNotariales();
		 if (listaAcciones != null)
		 {
			 for(int i = 0; i < listaAcciones.size(); i++)
			 {
				 MinutaAccionNotarial accionNotarial = (MinutaAccionNotarial)listaAcciones.get(i);
				 MinutaAccionNotarialEnhanced accionNotarialEnh = new MinutaAccionNotarialEnhanced();
				 AccionNotarial accion = accionNotarial.getAccionNotarial();
				 if(accion == null)
				 {
					 throw new DAOException ("No se ingreso ninguna accion notarial");
				 }
				 accionNotarialEnh.setIdMinuta(minEnh.getIdMinuta());
				 accionNotarialEnh.setMinuta(minEnh);
				 
				//Recuperar Accion Notarial Persistente.
			 	AccionNotarialEnhancedPk idAccion = new AccionNotarialEnhancedPk();
			 	idAccion.idAccionNotarial = accion.getIdAccionNotarial();
			 	AccionNotarialEnhanced accionEnhanced = (AccionNotarialEnhanced)pm.getObjectById(idAccion,true);
			 	if(accionEnhanced == null)
			 	{
			 		throw new DAOException ("No se encontró accion notarial con id "+idAccion.idAccionNotarial);
			 	}
			 	accionNotarialEnh.setAccionNotarial(accionEnhanced);
			 	accionNotarialEnh.setIdAccionNotarial(accionEnhanced.getIdAccionNotarial());
			 	accionNotarialEnh.setValor(accionNotarial.getValor());
			 	accionNotarialEnh.setUnidades(accionNotarial.getUnidades());
			 	accionNotarialEnh.setConCuantia(accionNotarial.getConCuantia());
			 	unidadesTotal = unidadesTotal + accionNotarial.getUnidades();
			 	valortotal = valortotal + accionNotarial.getValor();
			 	pm.makePersistent(accionNotarialEnh);
			 }
		 }	 

		 SolicitudEnhancedPk sid = new SolicitudEnhancedPk();
		 sid.idSolicitud = solPers.getIdSolicitud();

		 
		 pm.currentTransaction().commit();
		 
		 JDOSolicitudesDAO jdoSolicitudesDao = new JDOSolicitudesDAO();
		 srn = (SolicitudRepartoNotarialEnhanced) jdoSolicitudesDao.getSolicitudByID(sid,pm);
		 pm.makeTransient(srn.getMinuta());
		 			
		  }
		  
		 catch (JDOException e) {
			 if (pm.currentTransaction().isActive()) {
			  pm.currentTransaction().rollback();
			 }
			 Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			 throw new DAOException(e.getMessage(), e);
		  }
			 
		  catch (DAOException e) {
			  if (pm.currentTransaction().isActive()) {
			  pm.currentTransaction().rollback();
			  }
			  throw (e);
		  } 
		
		  catch (Throwable e) {
				  if (pm.currentTransaction().isActive()) {
					  pm.currentTransaction().rollback();
				  }
			  } 
			
		  finally {
				  pm.close();
			  }

		  return (SolicitudRepartoNotarial) srn.toTransferObject();
	}




	/**
	* Obtiene la lista de minutas que no tienen asignado un <code>RepartoNotarial</code>.
	* @return Lista con las minutas que no tienen asignado un <code>RepartoNotarial</code>. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.RepartoNotarial
	*/
	public List getMinutasNoAsignadas() throws DAOException {
	  RepartoEnhanced rta = null;
	  JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
	  PersistenceManager pm = AdministradorPM.getPM();
	
	  List lista = new ArrayList();
		        
	   try {
	 	
		
		  //pm.currentTransaction().setOptimistic(false);
		  pm.currentTransaction().begin();	
		  
		  //Se realiza la búsqueda sobre las minutas.  Para obtener las minutas que no han
		  //sido asignadas, se buscan aquellas que no tienen asociado un reparto Notarial y
		  //cuyo estado no sea anulado. 
		  Query query = pm.newQuery(MinutaEnhanced.class);
		  query.setFilter("repartoNotarial == "+null+
						  "&& anulado =="+false);
		  Collection col = (Collection)query.execute();
		
		
		  Iterator it = col.iterator();
    
		  //Se hacen transientes cada uno de los objetos asociados con la minuta. 
		  while ( it.hasNext() ){
			  MinutaEnhanced obj = (MinutaEnhanced) it.next();
			  this.makeTransientMinuta(obj, pm);
			  lista.add(obj);
				}
				
		  query.close(col);
		  pm.currentTransaction().commit();    
			
			} catch (Throwable e){
				if (pm.currentTransaction().isActive())
				{
					pm.currentTransaction().rollback();
				}
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(),e);			
			} finally {
		     
				pm.close();
			}
			lista = TransferUtils.makeTransferAll(lista);
			return lista;
	  }




	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Registral. 
	 * @param circ El <code>Circulo</code> Registral en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, número de Turno y notaría asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String idExtraordinario) throws DAOException{
		
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable tablaResultados = new Hashtable();
		OficinaCategoriaEnhanced notariaClasificacion=null;

		List circulosNotariales = new ArrayList();

		try { 


			//1. En este llamado se crea el proceso de Reparto Notarial que se está
			//iniciando.
			String idReparto = this.crearProcesoRepartoNotarial(circ, tipo);
			if(idReparto == null)
			{
				throw new DAOException ("No fue posible crear el proceso de Reparto Notarial," +
				"ninguna minuta fue repartida");
			}
			//Realizar el reparto extraordinario
			if (tipo)
			{
				tablaResultados.putAll(
						this.realizarRepartoExtraordinario(idExtraordinario, idReparto, usuario)
				);

			}
			//REALIZAR EL REPARTO ORDINARIO.
			else
			{
				// 2. Se obtiene el listado de los Circulos Notariales asociados con el
				// Circulo Registral Recibido. 
				String idCirculoNotarial = circ.getIdCirculo();
				circulosNotariales = this.getCirculosNotarialesByCirculoRegistral(idCirculoNotarial);
				if (circulosNotariales.size()==0)
				{
					throw new DAOException ("No fue posible crear el proceso de Reparto Notarial," +
					"No hay Círculos Notariales asociados con el Círculo Registral");
				}
				/*****************************************************************************/
				/*            REPARTO PARA CADA UNO DE LOS CIRCULOS NOTARIALES               */
				/*****************************************************************************/
				for (int i = 0; i< circulosNotariales.size(); i++)
				{
					CirculoNotarial circuloNotarial = (CirculoNotarial) circulosNotariales.get(i);
					tablaResultados.putAll(
							this.realizarRepartoOrdinario(circuloNotarial, idReparto, usuario)
					);
				}
			}
		}
		catch (Throwable t)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException ("No fue posible completar el proceso de Reparto Notarial," +
					"Es posible que no todas las minutas hayan sido repartidas.",t);
		}
		finally {
			pm.close();
		}				


		return tablaResultados;		
	}
	
			


        /**********************************************************************/
        /*                                                                    */
        /*                   PROCESO RESTITUCION REPARTO                      */
        /*                                                                    */
        /**********************************************************************/
        

	/**
	* Adiciona un Causal de Restitución a la configuración del sistema.
	* <p>
	* El método genera una excepción si ya existe un Causal de restitución con el
	* identificador del objeto pasado como parámetro. 
	* @param datos objeto <code>CausalRestitucion</code> con sus atributos. 
	* @return identificador del Causal de Restitución generado. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.CausalRestitucion
	* 
	*/
	public CausalRestitucionPk addCausalRestitucion (CausalRestitucion datos) throws DAOException
	{
		PersistenceManager pm = AdministradorPM.getPM();
		CausalRestitucionEnhanced causal = CausalRestitucionEnhanced.enhance(datos);

		try {
		
		//Validación del identificador del causal de restitución. 
		CausalRestitucionEnhancedPk causalId = new CausalRestitucionEnhancedPk();
		causalId.idCausalRestitucion = causal.getIdCausalRestitucion();

		CausalRestitucionEnhanced valid = this.getCausalRestitucion (causalId, pm);

		
		// El método genera una excepción si ya existe un Causal de restitución con el
		// identificador del objeto pasado como parámetro. 
		if (valid != null) 
		{
			throw new DAOException(
			"Ya existe un causal de restitución con el identificador: " +
			causalId.idCausalRestitucion);
		}

		pm.currentTransaction().begin();
		pm.makePersistent(causal);
		pm.currentTransaction().commit();

		CausalRestitucionEnhancedPk rta = (CausalRestitucionEnhancedPk) pm.getObjectId(causal);

		return rta.getCausalRestitucionID();
		} catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
            throw e;
        }
		 catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		 finally {
			pm.close();
		}
		}
	
	

  /**
  * Obtiene un objeto de tipo <code>CausalRestitución</code> dado su identificador.
  * <p>Método usado en transacciones.
  * <p>El método retorna null si no se encuentra el causal de restitución con el
  * identificador pasado como parámetro. 
  * @param oid Identificador del causal de restitución 
  * @param pm Persistence Manager de la transacción. 
  * @return objeto <code>CausalRestitucionEnhanced</code> 
  * @throws DAOException
  * @see gov.sir.core.negocio.modelo.CausalRestitucion
  */
  protected CausalRestitucionEnhanced getCausalRestitucion (CausalRestitucionEnhancedPk oid,
	PersistenceManager pm) throws DAOException {
	CausalRestitucionEnhanced rta = null;

	if (oid.idCausalRestitucion != null) {
		try {
			rta = (CausalRestitucionEnhanced) pm.getObjectById(oid, true);
		} 
		
		//El método retorna null si no se encuentra el causal de restitución con el
		//identificador pasado como parámetro. 
		catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}

	return rta;
  }


  /**
   * Obtiene un objeto de tipo <code>Entidad</code> dado su identificador.
   * <p>Método usado en transacciones.
   * <p>El método retorna null si no se encuentra la naturaleza juridica con el
   * identificador pasado como parámetro. 
   * @param oid Identificador de la naturaleza juridica de entidad.
   * @param pm Persistence Manager de la transacción. 
   * @return objeto <code>NaturalezaJuridicaEntidadEnhanced</code> 
   * @throws DAOException
   * @see gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad
   */
   protected NaturalezaJuridicaEntidadEnhanced getNaturalezaJuridicaEntidad (NaturalezaJuridicaEntidadEnhancedPk oid,
	 PersistenceManager pm) throws DAOException {
	 NaturalezaJuridicaEntidadEnhanced rta = null;

	 if (oid.idNatJuridicaEntidad != null) {
		 try {
			 rta = (NaturalezaJuridicaEntidadEnhanced) pm.getObjectById(oid, true);
		 } 
		
		 //El método retorna null si no se encuentra la naturaleza juridica de entidad con el
		 //identificador pasado como parámetro. 
		 catch (JDOObjectNotFoundException e) {
			 rta = null;
		 } catch (JDOException e) {
			 Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			 throw new DAOException(e.getMessage(), e);
		 }
	 }

	 return rta;
   }


  /**
  * Obtiene la lista de los Causales de Restitución existentes en
  * la configuración del sistema. 
  * <p>
  * El ordenamiento de los resultados se realiza alfabéticamente, utilizando
  * como criterio de ordenamiento el nombre del <code>CausalRestitucion</code>
  * @return una lista de objetos <code>CausalRestitucion</code>
  * @throws DAOException
  * @see gov.sir.core.negocio.modelo.CausalRestitucion
  */    
  public List getCausalesRestitucion() throws DAOException 
  {
	  List lista = new ArrayList();
	  PersistenceManager pm = AdministradorPM.getPM();
		        
	  try {
		  
		  //Se intenta obtener el listado de Causales de Restitución.
		  pm.currentTransaction().begin();	
		  Query q = pm.newQuery(CausalRestitucionEnhanced.class);
		  
		  //Se establece como criterio de ordenamiento el nombre del causal de
		  //Restitucion (Alfabeticamente)
		  q.setOrdering("nombre ascending");
		  Collection results = (Collection) q.execute();
		  Iterator it = results.iterator();
    
		  //Se hace transiente cada uno de los objetos que se ingresan en la lista. 
		  while ( it.hasNext() ){
			  CausalRestitucionEnhanced obj = (CausalRestitucionEnhanced) it.next();
			  pm.makeTransient(obj);
			  lista.add(obj);
		  }
				
		  pm.currentTransaction().commit();    
		  q.close(results);

	  } catch (Throwable e){
		  if (pm.currentTransaction().isActive())
		  {
			  pm.currentTransaction().rollback();
		  }
		  Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		  throw new DAOException(e.getMessage(),e);			
	  } finally {
		  pm.close();
	  }
	  lista = TransferUtils.makeTransferAll(lista);
	  return lista;
  }


  /**
  * Obtiene una <code>OficinaOrigenEnhanced</code> dado su identificador. 
  * <p>Método usado para transacciones.
  * <p>El método retorna null, si no se encuentra la <code>OficinaOrigen</code>
  * con el identificador pasado como parámetro. 
  * @param oid identificador de la oficina de origen.
  * @param pm Persistence Manager de la transacción.
  * @return objeto <code>OficinaOrigenEnhanced</code>
  * @throws DAOException
  * @see gov.sir.core.negocio.modelo.OficinaOrigen
  * @see gov.sir.core.negocio.modelo.OficinaOrigenEnhanced
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
			  Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			  throw new DAOException(e.getMessage(), e);
		  }
	  }

	  return rta;
  }



  /**
  * Obtiene una <code>OficinaCategoriaEnhanced</code> dado su identificador. 
  * <p>Método usado para transacciones.
  * <p>El método retorna null, si no se encuentra la <code>OficinaCategoria</code>
  * con el identificador pasado como parámetro. 
  * @param oid identificador de la oficina de la Categoria.
  * @param pm Persistence Manager de la transacción.
  * @return objeto <code>OficinaCategoriaEnhanced</code>
  * @throws DAOException
  * @see gov.sir.core.negocio.modelo.OficinaCategoria
  * @see gov.sir.core.negocio.modelo.OficinaCategoriaEnhanced
  */
  protected OficinaCategoriaEnhanced getOficinaCatgoriaById(
	  OficinaCategoriaEnhancedPk oid, PersistenceManager pm)
	  throws DAOException {
	  OficinaCategoriaEnhanced rta = null;

	  if (oid.idCategoria != null && oid.idOficinaOrigen !=null) {
		  try {
			  rta = (OficinaCategoriaEnhanced) pm.getObjectById(oid, true);
		  } catch (JDOObjectNotFoundException e) {
			  rta = null;
		  } catch (JDOException e) {
			  Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			  throw new DAOException(e.getMessage(), e);
		  }
	  }

	  return rta;
  }


  /**
  * Obtiene un objeto <code>Vereda</code> dado su identificador de manera transaccional.
  * <p>El método genera una excepción si no se encuentra la <code>Vereda</code> con el
  * identificador recibido como parámetro. 
  * @param oid identificador de la <code>Vereda</code>
  * @return objeto <code>Vereda</code> 
  * @throws DAOException
  * @see gov.sir.core.negocio.modelo.VeredaEnhanced
  */
  protected VeredaEnhanced getVereda(VeredaEnhancedPk oid,
	   PersistenceManager pm) throws DAOException {
	   VeredaEnhanced rta = null;

	   if ((oid.idDepartamento != null) && (oid.idMunicipio != null) &&
			   (oid.idVereda != null)) {
		   try {
			   rta = (VeredaEnhanced) pm.getObjectById(oid, true);
		   } 
		   
		   //Se lanza una excepción si no se encuentra una vereda con el identificador
		   //recibido como parámetro. 
		   catch (JDOObjectNotFoundException e) {
			   rta = null;
		   } catch (JDOException e) {
			   Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			   throw new DAOException(e.getMessage(), e);
		   }
	   }

	   return rta;
   }
   

   /**
   * Obtiene una <code>AccionNotarial</code> dado su identificador. 
   * <p>Método usado para transacciones.
   * @param oid identificador de la <code>AccionNotarial</code>
   * @param pm Persistence Manager de la transacción.
   * @return objeto <code>AccionNotarial</code>
   * @throws DAOException
   * @see gov.sir.core.negocio.modelo.AccionNotarialEnhanced
   */
   protected AccionNotarialEnhanced getAccionNotarialById(
	   AccionNotarialEnhancedPk oid, PersistenceManager pm)
	   throws DAOException {
	   AccionNotarialEnhanced rta = null;

	   if (oid.idAccionNotarial != null) {
		   try {
			   rta = (AccionNotarialEnhanced) pm.getObjectById(oid, true);
		   } 
		   
		   //Se lanza una excepción si no se encuentra una Acción Notarial con el identificador
		   //recibido como parámetro.
		   catch (JDOObjectNotFoundException e) {
			   rta = null;
		   } catch (JDOException e) {
			   Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			   throw new DAOException(e.getMessage(), e);
		   }
	   }

	   return rta;
   }






	/**
	* Obtiene una Minuta dado su identificador. 
	* Método usado para transacciones.
	* @param id identificador de la minuta.
	* @param pm Persistence Manager de la transacción.
	* @return objeto MinutaEnhanced.
	* @throws DAOException
	*/
	protected MinutaEnhanced getMinutaById(
		MinutaEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		MinutaEnhanced rta = null;

		if (oid.idMinuta != null) {
			try {
				rta = (MinutaEnhanced) pm.getObjectById(oid, true);
				
				
			} 
			
			//Se lanza una excepción si no se encuentra una Minuta con el identificador
			//recibido como parámetro.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
	
	/**
	 * Obtiene una categoría por el id
	 * @param oid
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected CategoriaEnhanced getCategoriaById(
		CategoriaEnhancedPk oid, PersistenceManager pm)throws DAOException {
		CategoriaEnhanced rta = null;

		if (oid.idCategoria != null) {
			try {
				rta = (CategoriaEnhanced) pm.getObjectById(oid, true);
			} 
			
			//Se lanza una excepción si no se encuentra una Minuta con el identificador
			//recibido como parámetro.
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}







	/** 
	 * Anula la <code>Minuta</code> recibida como parámetro y hace persistente
	 * la modificación.
	 * <p>
	 * El método genera una excepción si no se encuentra la <code>Minuta </code> con el
	 * identificador pasado como parámetro.  
	 * @param minuta La <code>Minuta</code> que va a ser anulada.
	 * @return true o false dependiendo del resultado de la anulación de la <code>Minuta</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public boolean anularMinutaRepartoNotarial(Minuta minuta, Usuario usuario) throws DAOException {
		
		PersistenceManager pm = AdministradorPM.getPM();
		MinutaEnhanced minEnh = new MinutaEnhanced();
		UsuarioEnhancedPk usuId = new UsuarioEnhancedPk();
		usuId.idUsuario = usuario.getIdUsuario();
		try {
		   
		   pm.currentTransaction().setOptimistic(false);
		   pm.currentTransaction().begin();
			
		   
		   UsuarioEnhanced usuEnh = (UsuarioEnhanced)pm.getObjectById(usuId,true);
		   MinutaEnhancedPk minId = new MinutaEnhancedPk();
		   minId.idMinuta = minuta.getIdMinuta();
			
		   minEnh = this.getMinutaById(minId,pm);
				
		   //Se lanza una excepción si no se encuentra una Minuta con el identificador
		   //recibido como parámetro.
		   if (minEnh == null) {
				throw new DAOException("No se encontró la minuta que se quiere anular.");
			}
									
		   minEnh.setEstado(CReparto.MINUTA_ANULADA);
		   minEnh.setUsuarioAnula(usuEnh);
		   minEnh.setFechaAnulada(Calendar.getInstance().getTime());
		   pm.currentTransaction().commit();
		   
		}
		
		catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
		}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
			}
		 catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
					}
			throw (e);
		}
		catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}
		 finally {
			pm.close();
			}
        return true;
	}



	/** 
	 * Modifica la <code>Minuta</code> recibida como parámetro y hace persistente
	 * la modificación.
	 * <p>
	 * El método genera una excepción si no se encuentra la <code>Minuta </code> con el
	 * identificador pasado como parámetro.  
	 * @param min La <code>Minuta</code> que va a ser modificada. 
	 * @return true o false dependiendo del resultado de la anulación de la <code>Minuta</code>
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Minuta modificarMinuta(Minuta min, boolean generarAuditoria, Usuario usuario) throws DAOException {
		
		PersistenceManager pm = AdministradorPM.getPM();
		MinutaEnhanced minEnh = new MinutaEnhanced();

		try {
			
            		   
		    pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			 
			
			MinutaEnhancedPk minId = new MinutaEnhancedPk();
			MinutaEnhanced minuta = MinutaEnhanced.enhance(min);
			minId.idMinuta = minuta.getIdMinuta();
			
			minEnh = this.getMinutaById(minId,pm);
			
			//Se lanza una excepción si no se encuentra una Minuta con el identificador
			//recibido como parámetro.
			if (minEnh == null) {
				throw new DAOException("No se encontró la minuta que se quiere modificar.");
			}
			
			MinutaEnhanced oldMinuta=(MinutaEnhanced)auditoria.clonarEnhanced(minEnh);
			
            //Garantizar que el número de unidades o el valor de la minuta tengan un 
			//valor diferente de cero.
			if (min.getUnidades()  < 0 && min.getValor() < 0)
			{
				throw new DAOException ("Deben asignarse el valor de la minuta o las unidades de la minuta");
			}
			minEnh.setEstado(minuta.getEstado());
			minEnh.setUnidades(minuta.getUnidades());
			minEnh.setValor(minuta.getValor());
			minEnh.setNumeroFolios(minuta.getNumeroFolios());
			minEnh.setNormal(minuta.isNormal());
			minEnh.setComentario(minuta.getComentario());
			
			//Se asocia el Circulo Notarial
			CirculoNotarialEnhancedPk idNotarialEnh = new CirculoNotarialEnhancedPk();
			idNotarialEnh.idCirculo = minuta.getCirculoNotarial().getIdCirculo();
	     
			CirculoNotarialEnhanced notarialEnh = new CirculoNotarialEnhanced();
			notarialEnh = (CirculoNotarialEnhanced) pm.getObjectById(idNotarialEnh,true);
			minEnh.setCirculoNotarial(notarialEnh);
	     
			
            //Obtener la Categoria de la minuta. 
			Categoria cat = new Categoria();	
			cat = this.getCategoriaClasificacionMinuta(min);
			
			CategoriaEnhancedPk catID = new CategoriaEnhancedPk();
			catID.idCategoria = cat.getIdCategoria();
		
			CategoriaEnhanced newCat = this.getCategoriaById(catID, pm);
			minEnh.setCategoria(newCat);
		
					
			//Se intenta asignar la Acción Notarial.
			/*AccionNotarialEnhanced.ID accId = new AccionNotarialEnhanced.ID();
			accId.idAccionNotarial = min.getAccionNotarial().getIdAccionNotarial();
			AccionNotarialEnhanced accionEnhanced = this.getAccionNotarialById(accId,pm);
			
			if (accionEnhanced ==null){
					throw new DAOException ("No pudo recuperarse la Acción Notarial asociada a la Minuta");
			}
			minEnh.setAccionNotarial(accionEnhanced);*/
			
			
			//La minuta no tiene en este momento reparto, y su estado es por repartir. 
			minEnh.setEstado(CReparto.MINUTA_NO_REPARTIDA);
			minEnh.setRepartoNotarial(null);
			minEnh.setOficinaCategoriaAsignada(null);
			
			
			//Se asocian a la nueva minuta, las listas de otorgantes públicos y otorgantes naturales.
			List listaNaturales = min.getOtorgantesNaturales();
			List listaPublicas  = min.getEntidadesPublicas();
			List listaAccionesNotariales = min.getAccionesNotariales();
			
			   //Se elimina la información de la minuta anterior.
			   pm.deletePersistentAll(minEnh.getEntidadesPublicas());
			   pm.deletePersistentAll(minEnh.getOtorgantesNaturales());
			   pm.deletePersistentAll(minEnh.getAccionesNotariales());
			
			   VersantPersistenceManager pm2 = (VersantPersistenceManager)pm;
			   pm2.flush();
			   
			   //Se insertan las nuevas entradas.
			        
			        //Entidades Publicas Asociadas.
			        if (listaPublicas != null )
					{
					   for (int j =0; j< listaPublicas.size(); j++)
					   {
						   MinutaEntidadPublica otorgantePublico = (MinutaEntidadPublica)listaPublicas.get(j);
						   MinutaEntidadPublicaEnhanced  entPublicaEnh = new MinutaEntidadPublicaEnhanced();
						   EntidadPublica entidad = otorgantePublico.getEntidadPublica();
						   if (entidad == null)
						   {
							   throw new DAOException ("No se ingresó entidad pública otorgante.");
						   }
						   entPublicaEnh.setIdMinuta(minEnh.getIdMinuta());
						   entPublicaEnh.setMinuta(minEnh);
				
						   //Recuperar Entidad Publica Persistente.
						   EntidadPublicaEnhancedPk idEntidad = new EntidadPublicaEnhancedPk ();
						   idEntidad.idEntidadPublica = entidad.getIdEntidadPublica();
						   EntidadPublicaEnhanced entidadEnhanced = (EntidadPublicaEnhanced)pm.getObjectById(idEntidad,true);
						   if (entidadEnhanced == null)
						   {
							   throw new DAOException ("No se encontró entidad pública otorgante con id "+idEntidad.idEntidadPublica);
						   }
						   entPublicaEnh.setEntidadPublica(entidadEnhanced);
						   entPublicaEnh.setIdEntidadPublica(entidadEnhanced.getIdEntidadPublica());
						   entPublicaEnh.setExento(entidad.isExento());
						   pm.makePersistent(entPublicaEnh);
		 		
					   }
					}
			        
			        long unidadesTotal = 0;
					double valortotal = 0;
					long conCuantia = 0;
					if(listaAccionesNotariales != null)
					{
						for (int j=0; j<listaAccionesNotariales.size(); j++)
						{
						   MinutaAccionNotarial accionNotarial = (MinutaAccionNotarial)listaAccionesNotariales.get(j);
						   MinutaAccionNotarialEnhanced  accNotarialEnh = new MinutaAccionNotarialEnhanced();
						   AccionNotarial accion = accionNotarial.getAccionNotarial();
						   if (accion == null)
						   {
							   throw new DAOException ("No se ingresó accion notarial.");
						   }
						   accNotarialEnh.setIdMinuta(minEnh.getIdMinuta());
						   accNotarialEnh.setMinuta(minEnh);
				
						   //Recuperar Entidad Publica Persistente.
						   AccionNotarialEnhancedPk idAccion = new AccionNotarialEnhancedPk ();
						   idAccion.idAccionNotarial = accion.getIdAccionNotarial();
						   AccionNotarialEnhanced accionEnhanced = (AccionNotarialEnhanced)pm.getObjectById(idAccion,true);
						   if (accionEnhanced == null)
						   {
							   throw new DAOException ("No se encontró accion notarial con id "+idAccion.idAccionNotarial);
						   }
						   accNotarialEnh.setAccionNotarial(accionEnhanced);
						   accNotarialEnh.setIdAccionNotarial(accionEnhanced.getIdAccionNotarial());
						   accNotarialEnh.setValor(accionNotarial.getValor());
						   accNotarialEnh.setUnidades(accionNotarial.getUnidades());
						   accNotarialEnh.setConCuantia(accionNotarial.getConCuantia());
						 	unidadesTotal = unidadesTotal + accionNotarial.getUnidades();
						 	valortotal = valortotal + accionNotarial.getValor();
						   pm.makePersistent(accNotarialEnh);
						}
					}
			        /*if (listaAccionesNotariales != null )
					{
					   for (int j =0; j< listaPublicas.size(); j++)
					   {
						   MinutaEntidadPublica otorgantePublico = (MinutaEntidadPublica)listaPublicas.get(j);
						   MinutaEntidadPublicaEnhanced  entPublicaEnh = new MinutaEntidadPublicaEnhanced();
						   EntidadPublica entidad = otorgantePublico.getEntidadPublica();
						   if (entidad == null)
						   {
							   throw new DAOException ("No se ingresó entidad pública otorgante.");
						   }
						   entPublicaEnh.setIdMinuta(minEnh.getIdMinuta());
						   entPublicaEnh.setMinuta(minEnh);
				
						   //Recuperar Entidad Publica Persistente.
						   EntidadPublicaEnhanced.ID idEntidad = new EntidadPublicaEnhanced.ID ();
						   idEntidad.idEntidadPublica = entidad.getIdEntidadPublica();
						   EntidadPublicaEnhanced entidadEnhanced = (EntidadPublicaEnhanced)pm.getObjectById(idEntidad,true);
						   if (entidadEnhanced == null)
						   {
							   throw new DAOException ("No se encontró entidad pública otorgante con id "+idEntidad.idEntidadPublica);
						   }
						   entPublicaEnh.setEntidadPublica(entidadEnhanced);
						   entPublicaEnh.setIdEntidadPublica(entidadEnhanced.getIdEntidadPublica());
						   pm.makePersistent(entPublicaEnh);
		 		
					   }
					}*/
			        
			
			        //SE HACEN PERSISTENTES LAS ASOCIACIONES ENTRE LOS OTORGANTES NATURALES Y LA MINUTA.
			        if (listaNaturales != null )
			        {
			              for (int i =0; i< listaNaturales.size(); i++)
			              {
				   				OtorganteNatural otorganteNatural = (OtorganteNatural)listaNaturales.get(i);
				   				OtorganteNaturalEnhanced  otNatEnh = new OtorganteNaturalEnhanced();
				   				otNatEnh.setIdMinuta(minEnh.getIdMinuta());
				   				otNatEnh.setIdOtorgante(i+"");
				   				otNatEnh.setMinuta(minEnh);
				   				otNatEnh.setNombre(otorganteNatural.getNombre());
				   				otNatEnh.setExento(otorganteNatural.isExento());
				   				pm.makePersistent(otNatEnh);
		 		
			   				}
					}					
			        
		        if (generarAuditoria){
		        	UsuarioEnhancedPk usuId = new UsuarioEnhancedPk();
		    		usuId.idUsuario = usuario.getIdUsuario();
		    		UsuarioEnhanced usuEnh = (UsuarioEnhanced)pm.getObjectById(usuId,true);
					auditoria.addAuditoria(minEnh,oldMinuta,usuEnh,pm);
				}
			
			pm.currentTransaction().commit();
			MinutaEnhancedPk idMinEnh = new MinutaEnhancedPk();
			idMinEnh.idMinuta = minEnh.getIdMinuta();
			minEnh = this.getMinutaTransienteById(idMinEnh,pm);
		   
		}
		
		catch (JDOException e) {
			if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().rollback();
		    }
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				}
				throw (e);
			}
		catch (Throwable e) {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
				}
				 finally {
					pm.close();
					}
				return (Minuta) minEnh.toTransferObject();
	}


	/**
	* Obtiene una <code>MinutaEnhanced</code> con todos sus elementos transientes, dado su identificador. 
	* <p>Método usado para transacciones.
	* <p>El método retorna null si no se encuentra una <code>Minuta </code> con el
	* identificador dado. 
	* @param oid identificador de la <code>Minuta</code>
	* @param pm Persistence Manager de la transacción.
	* @return objeto <code>MinutaEnhanced</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.MinutaEnhanced
	*/
	protected MinutaEnhanced getMinutaTransienteById(
		MinutaEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		MinutaEnhanced rta = null;

		if (oid.idMinuta != null) {
			try {
				
				//Se obtiene la Minuta y se hacen transientes cada uno de los
				//objetos asociados con la misma. 
				rta = (MinutaEnhanced) pm.getObjectById(oid, true);
				//pm.makeTransient( rta.getAccionNotarial());
				pm.makeTransient(rta.getCategoria());
				pm.makeTransient(rta.getOficinaCategoriaAsignada());
				//pm.makeTransient(rta.getOtorgante1Oficina());
				//pm.makeTransient(rta.getOtorgante2Ciudadano());
				//pm.makeTransient(rta.getOtorgante2Oficina());
				pm.makeTransient(rta.getRepartoNotarial());

				pm.makeTransient (rta);
				
			} 
			
			//Se retorna null, si no se encuentra una minuta con el identificador recibido
			//como parámetro. 
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}


	





	/**
	* Obtiene un <code>RepartoNotarialEnhanced</code> dado su identificador. 
	* <code>Método usado para transacciones</code>
	* <p>
	* El método retorna null si no se encuentra un <code>RepartoNotarial</code>
	* con el identificador pasado como parámetro. 
	* @param oid identificador del <code>RepartoNotarial</code>
	* @param pm Persistence Manager de la transacción.
	* @return objeto <code>RepartoNotarialEnhanced</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.RepartoNotarial
	* @see gov.sir.core.negocio.modelo.RepartoNotarialEnhanced
	*/
	protected RepartoNotarialEnhanced getRepartoNotarialById(
		RepartoNotarialEnhancedPk oid, PersistenceManager pm)
		throws DAOException {
		RepartoNotarialEnhanced rta = null;

		if (oid.idRepartoNotarial != null) {
			try {
				rta = (RepartoNotarialEnhanced) pm.getObjectById(oid, true);
				
				
			} 
			
			// El método retorna null si no se encuentra un RepartoNotarial
			// con el identificador pasado como parámetro
			catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}



	/** 
	 * Obtiene el listado de minutas no asignadas dentro de una Vereda.
	 * @param vereda Identificador de la <code>Vereda</code> en la cual se van a buscar las Minutas
	 * no asignadas.
	 * @return Listado con objetos de tipo <code>Minuta</code> que no tienen asignado
	 * un <code>RepartoNotarial</code> dentro de la <code>Vereda</code> pasada como
	 * parámetro. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.Minuta
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Vereda
	 */
	public List getMinutasNoAsignadasByVereda(VeredaPk vereda) throws DAOException {
		
		RepartoEnhanced rta = null;
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();
		PersistenceManager pm = AdministradorPM.getPM();
	
		List lista = new ArrayList();
		        
		try {
	 	
		
		//pm.currentTransaction().setOptimistic(false);
		pm.currentTransaction().begin();	
		  
		//Se realiza la búsqueda sobre las minutas.  Para obtener las minutas que no han
		//sido asignadas, se buscan aquellas que no tienen asociado un reparto Notarial y
		//cuyo estado no sea anulado. 
		//Se busca que las minutas estén dentro de la vereda pasada como parámetro.
		String idMun = vereda.idMunicipio;
		String idDep = vereda.idDepartamento;
		String idVer = vereda.idVereda;
		Query query = pm.newQuery(MinutaEnhanced.class);
		query.declareParameters("String idMun, String idDep, String idVer");
		query.setFilter("repartoNotarial == "+null+
		"&& anulado ==0" +" && this.vereda.idMunicipio == idMun && this." +
			"vereda.idDepartamento == idDep && vereda.idVereda == idVer");
		Collection col = (Collection)query.execute(idMun,idDep,idVer);
		
		Iterator it = col.iterator();
    
		//Se hacen transientes cada uno de los objetos asociados con la minuta. 
		while ( it.hasNext() ){
			 MinutaEnhanced obj = (MinutaEnhanced) it.next();
			 //pm.makeTransient(obj.getAccionNotarial());
			 pm.makeTransient(obj.getCategoria());
			 pm.makeTransient(obj.getOficinaCategoriaAsignada());
			 //pm.makeTransient(obj.getOtorgante1Oficina());
			 //pm//.makeTransient(obj.getOtorgante2Ciudadano());
			 //pm.makeTransient(obj.getOtorgante2Oficina());
			 //pm.makeTransient(obj.getVereda());
			// pm.makeTransient(obj);
		    
		 lista.add(obj);
		}
				
		query.close(col);
		pm.currentTransaction().commit();    
			
		}
		catch (Throwable e){
			if (pm.currentTransaction().isActive())
			{
			   pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
	    }
	     finally {
		     pm.close();
		  }
		  lista = TransferUtils.makeTransferAll(lista);
		  return lista;
	}


  /**
   * Obtiene una lista con los identificadores de las Veredas que pertenecen
   * a una Zona Notarial.
   * @param idZonaNotarial El identificador de la <code>ZonaNotarial</code>
   * @return Lista con los identificadores de las Veredas que pertenecen a una 
   * <code>ZonaNotarial</code>.
   * @throws <code>DAOException</code>
   * @see gov.sir.core.negocio.modelo.ZonaNotarial
   * @see gov.sir.core.negocio.modelo.Vereda
   */
   protected List getVeredasZonaNotarial (String idZonaNotarial, PersistenceManager pm) throws DAOException
   {
	
	    List lista = new ArrayList();
		        
		try {

		//Se realiza la búsqueda sobre las Zonas Notariales.
		
		Query query = pm.newQuery(ZonaNotarialEnhanced.class);
		query.declareParameters("String idZonaNotarial");
		query.setFilter("idCirculoNotarial == idZonaNotarial");
		
		Collection col = (Collection)query.execute(idZonaNotarial);
		
		Iterator it = col.iterator();
    
		//Se construyen los objetos Vereda.ID
		while ( it.hasNext() ){
			 ZonaNotarialEnhanced obj = (ZonaNotarialEnhanced) it.next();
			 VeredaPk idVereda = new VeredaPk();
			 idVereda.idDepartamento = obj.getIdDepartamento();
			 idVereda.idMunicipio = obj.getIdMunicipio();
			 idVereda.idVereda = obj.getIdVereda();
			 
			 lista.add(idVereda);
		}
				
		query.close(col);
					
		}
		catch (Throwable e){
			if (pm.currentTransaction().isActive())
			{
			   pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		}
		   return lista;
   	
   }
   

   /**
   * Obtiene el identificador de WF de un <code>Turno</code>, dado
   * el identificador de la solicitud. 
   * @param solId Identificador de la <code>Solicitud</code> de la
   * cual se desea obtener el <code>Turno</code> de WorkFlow.
   * @param pm <code>PersistenceManager</code> de la transaccion
   * @return Identificador de WorkFlow del <code>Turno</code> asociado con el
   * identificador de <code>Solicitud</code> pasado como parámetro. 
   * @throws DAOException
   */
	 protected String getWFIdBySolicitudID(String solId,
		 PersistenceManager pm) throws DAOException {
		 String rta = null;

		 try {
			 Query query = pm.newQuery(TurnoEnhanced.class);
			 query.declareParameters("String solId");
			 query.setFilter("this.solicitud.idSolicitud == solId && this.idCirculo== this.solicitud.circulo");

			 Collection col = (Collection) query.execute(solId);

			 if (col.size() == 0) {
				 rta = null;
			 } else {
				 for (Iterator iter = col.iterator(); iter.hasNext();) {
					 TurnoEnhanced rtaTurno = (TurnoEnhanced) iter.next();
					 rta = rtaTurno.getIdWorkflow();
					 				 }

				 query.closeAll();
			 }
		 } catch (JDOException e) {
			 Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
			 throw new DAOException(e.getMessage(), e);
		 }

		 return rta;
	 }
	 
	 
	 /**
      * Crea un <code>RepartoNotarial</code>, le asigna un identificador,
      * le asigna una fecha de creación, le asocia el tipo normal 
      * y lo hace persistente.
      * <p>El identificador se debe construir de la siguiente manera:
      * Año-Circulo-Id Para esto se obtiene el identificador de la tabla
      * de Circulos procesos. 
      * @param circulo El <code>Circulo</code> en el que se realizará el Reparto
      * Notarial. 
      * @return el identificador del proceso de reparto notarial creado.
      * @throws <code>DAOException</code>
      * @see gov.sir.core.negocio.modelo.RepartoNotarial
      */
     protected String crearProcesoRepartoNotarial (Circulo circulo, boolean tipo) throws DAOException
     {
     	
		PersistenceManager pm = AdministradorPM.getPM();
		RepartoNotarialEnhanced repNotEnh;
	
				
		try { 
	 	
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();	
		  
			//Se crea el Reparto Notarial
			RepartoNotarialEnhanced repartoNotarial = new RepartoNotarialEnhanced();
			JDOSolicitudesDAO solicitudesDAO = new JDOSolicitudesDAO();
			
			//Se obtiene el secuencial de la tabla de SIR_OP_CIRCULO_PROCESO
			
			//Se construye el identificador del reparto concatenando año,circulo y secuencial.
			
			  //Año
			Calendar calendario = Calendar.getInstance();
	        String yearString = String.valueOf(calendario.get(Calendar.YEAR));

	  			  
			  //Círculo
			  if (circulo == null)
			  {
					throw new DAOException ("No fue posible crear el Reparto Notarial. Ningún Turno fue repartido. El Círculo Registral es inválido");
			  }
			  String circuloId = circulo.getIdCirculo();
			 
			  //Secuencial
			  
			long idReparto = this.getSecuencialReparto(circuloId,yearString,pm); 
			Long idRepartoLong = new Long(idReparto);
			String idRepartoConcatenado = yearString+"-"+circuloId+"-"+idRepartoLong.toString();
			repartoNotarial.setIdRepartoNotarial(idRepartoConcatenado); 
			
			if (tipo)
			{
				repartoNotarial.setNormal(false);
			}
			else
			{
				repartoNotarial.setNormal(true);
			}
			
			Date fechaCreacion = new Date();
			repartoNotarial.setFechaCreacion(fechaCreacion);
			
			pm.makePersistent(repartoNotarial);
			pm.currentTransaction().commit();
			
			//Si se hizo persistente el reparto se recupera para retornar el id.
			RepartoNotarialEnhancedPk repId = new RepartoNotarialEnhancedPk();
			repId.idRepartoNotarial = idRepartoConcatenado;
			repNotEnh = this.getRepartoNotarialById(repId,pm);
			
			if (repNotEnh == null)
			{
				throw new DAOException ("No fue posible crear el Reparto Notarial. Ningún Turno fue repartido");
			}
			
		}
		catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
            throw e;
        }
		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		}
				 
		finally {
			pm.close();
		}
		return repNotEnh.getIdRepartoNotarial();
     }
     
     /**
      * Consulta el ultimo idRepartoNotarial
      * @return el identificador del proceso de reparto notarial creado.
      * @throws <code>DAOException</code>
      * @see gov.sir.core.negocio.modelo.RepartoNotarial
      */
     protected String consultaProcesoRepartoNotarial (Circulo circulo, boolean tipo) throws DAOException
     {
     	
		PersistenceManager pm = AdministradorPM.getPM();
		String rta;
				
		try { 
	 	
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();	
		  
			Calendar calendario = Calendar.getInstance();
	        String yearString = String.valueOf(calendario.get(Calendar.YEAR));

			long idReparto = this.getLastSecuencialReparto(circulo.getIdCirculo(),yearString,pm); 
			Long idRepartoLong = new Long(idReparto);
			String idRepartoConcatenado = yearString+"-"+circulo.getIdCirculo()+"-"+idRepartoLong.toString();
			rta = idRepartoConcatenado;
			
			pm.currentTransaction().commit();
		}
		
		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		}
				 
		finally {
			pm.close();
		}
		return rta;
     }
			
     
			
			
	/**
	* Obtiene el listado de los Círculos Notariales, asociados con el
	* <code>Circulo</code> recibido como parámetro.
    * @param idCirculoRegistral Identificador del <code>Circulo</code> registral del
    * cual se desean obtener los Circulos Notariales asociados. 
	* @return una lista de objetos <code>CirculoNotarial</code>que estén asociados con
	* el <code>Circulo</code> recibido como parámetro. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.CirculoNotarial
	*/
	protected List getCirculosNotarialesByCirculoRegistral (String idCirculoRegistral)
	 throws DAOException
		{
     	
		   PersistenceManager pm = AdministradorPM.getPM();
		   List circulosNotariales = new ArrayList();
	
				
		   try { 
	 	
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();	
		 
			Query query = pm.newQuery(CirculoNotarialEnhanced.class);
			query.declareParameters("String idCirculoRegistral");
			query.setFilter("this.circuloRegistral.idCirculo == idCirculoRegistral");
			Collection col = (Collection)query.execute(idCirculoRegistral);
			
			Iterator it1 = col.iterator();
    
			while ( it1.hasNext() ){ 
				CirculoNotarialEnhanced obj = (CirculoNotarialEnhanced) it1.next();
				CirculoEnhanced cirReg = obj.getCirculoRegistral();
				pm.makeTransient(cirReg);
				pm.makeTransient(obj);
				circulosNotariales.add(obj);
			}
			
			query.close(col);
			pm.currentTransaction().commit();
			
		}
		
		catch (Throwable e)
		   {
			   if (pm.currentTransaction().isActive())
			   {
				   pm.currentTransaction().rollback();
			   }
			   Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			   throw new DAOException(e.getMessage(),e);			
		   }
				 
		   finally {
			   pm.close();
		   }
		    List listaCirculos = new ArrayList();
		    listaCirculos = TransferUtils.makeTransferAll(circulosNotariales);
			return listaCirculos;
		}
			


		/**
		* Obtiene el listado de las Zonas Notariales, asociadas con los
		* Círculos Notariales pasados como parámetros dentro del filtro. 
		* @param filtro  Cadena que contiene los identificadores de todos los Círculos Notariales
		* de los cuales se desean obtener las Zonas Notariales, separados por el operador ||
		* @return una lista de objetos <code>ZonaNotarial</code>que estén asociados con
		* los CírculosNotariales recibidos como parámetros dentro del filtro.  
		* @throws <code>DAOException</code>
		* @see gov.sir.core.negocio.modelo.CirculoNotarial
		* @see gov.sir.core.negocio.modelo.ZonaNotarial
		*/
		protected List getZonasNotarialesByCirculosNotariales (String filtro)
		throws DAOException
				
		{
     	
			PersistenceManager pm = AdministradorPM.getPM();
			List zonasNotariales = new ArrayList();
	
				
			try { 
	 	
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();	
		 
			VersantQuery query2 = (VersantQuery) pm.newQuery(ZonaNotarialEnhanced.class);
			query2.setFilter(filtro);
			Collection col2 = (Collection)query2.execute();
			
			Iterator it2 = col2.iterator();
			
			
			while ( it2.hasNext() )
			{ 
				ZonaNotarialEnhanced zonaNot = (ZonaNotarialEnhanced) it2.next();
				pm.makeTransient(zonaNot);
				zonasNotariales.add(zonaNot);
			} 
			
			query2.close(col2);
			pm.currentTransaction().commit();
			
		  }
		  catch (Throwable e)
		  {
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		  }
				 
		finally {
			pm.close();
		}
		
		return zonasNotariales;
	}




	/**
	* Retorna la Notaría que se encuentra de primera en la cola de Reparto Notarial para
	* la Categoría y la Vereda pasadas como parámetros dentro del filtro. 
	* @return un objeto <code>OficinaCategoriaEnhanced</code>que está asociado con la
	* Notaría que se encuentra de primera en la cola de repartos para los parámetros dados.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.OficinaCategoria
	* @see gov.sir.core.negocio.modelo.OficinaCategoriaEnhanced
	*/
	protected OficinaCategoriaEnhanced getPrimeraNotaria (String filtro)
	throws DAOException
				
	{
     	
		PersistenceManager pm = AdministradorPM.getPM();
		OficinaCategoriaEnhanced notaria=null;
				
		try { 
	 	
		//pm.currentTransaction().setOptimistic(false);
		pm.currentTransaction().begin();	
		 
		Query queryNotaria = pm.newQuery(OficinaCategoriaEnhanced.class);
		queryNotaria.setOrdering("pesoReparto ascending, ordenInicial ascending");
		queryNotaria.setFilter(filtro);
		Collection colNotarias = (Collection)queryNotaria.execute();
					
		Iterator it3 = colNotarias.iterator();
			
		if (it3.hasNext()){
										
			notaria = (OficinaCategoriaEnhanced) it3.next();
			pm.makeTransient(notaria); 
			        
			//logger.debug("=========================================");
			//logger.debug("   NOTARIA EN LA QUE SE DEBE REPARTIR    ");
			//logger.debug("=========================================");
			//logger.debug("ID NOTARIA " +notaria.getIdOficinaOrigen());
					
		}
		queryNotaria.close(colNotarias);	
		
		pm.currentTransaction().commit();
			
	  }
	  catch (Throwable e)
	  {
		if (pm.currentTransaction().isActive())
		{
			pm.currentTransaction().rollback();
		}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(),e);			
	  }
				 
	finally {
		pm.close();
	}
		
	return notaria;
}





	/**
	* Realiza las operaciones que deben ser transaccionales en el proceso de Reparto
	* Notarial de una <code>Minuta</code>
	* 1. Asociar la Notaría a la <code>Minuta</code>. 
	* 2. Asociar la <code>Minuta</code> al <code>RepartoNotarial</code>
	* 3. Mover la Notaría, del primer lugar de la cola de Notarías, al último lugar. 
	* 4. Avanzar el Turno
	* @param idMinuta Identificador de la <code>Minuta que va a ser repartida.
	* @param idReparto Identificador del <code>RepartoNotarial</code>
	* @param idNotaira Identificador de la Notaría (<code>OficinaCategoria</code>).
	* @param usuario <code>Usuario</code> que realiza el reparto notarial.
	* @return La <code>Minuta</code> que fue repartida.  
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Minuta
	* @see gov.sir.core.negocio.modelo.OficinaCategoria
	* @see gov.sir.core.negocio.modelo.RepartoNotarial 
	*/
	protected Minuta realizarRepartoMinuta (MinutaEnhancedPk idMinuta, RepartoNotarialEnhancedPk 
								idReparto, OficinaCategoriaEnhancedPk idNotaria, Usuario usuario)	
	throws DAOException
				
	{
     	 
		PersistenceManager pm = AdministradorPM.getPM();
		OficinaCategoriaEnhanced notaria=null;
		MinutaEnhanced minutaPers = null;
		int logError = 0;
				
		try { 
	 	
		//pm.currentTransaction().setOptimistic(false);
		pm.currentTransaction().begin();	
		 
		//1. Asociar la Notaría a la Minuta
		minutaPers = new MinutaEnhanced();
		minutaPers = (MinutaEnhanced) pm.getObjectById(idMinuta,true);
	   
		if (minutaPers == null)
		{
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException ("No se encontró la Minuta a la que se quiere asociar la Notaría");
		}
		logError ++;
	   
		OficinaCategoriaEnhanced notariaPers = new OficinaCategoriaEnhanced();
		notariaPers = (OficinaCategoriaEnhanced) pm.getObjectById(idNotaria,true);
	   
		if (minutaPers == null)
		{
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException ("No se encontró la Notaria que se quiere asociar a la Minuta");
		}
		
		logError ++;
		minutaPers.setOficinaCategoriaAsignada(notariaPers);
		
		   //1.1. Marcar la minuta como repartida.
		   minutaPers.setEstado(CReparto.MINUTA_REPARTIDA);
		
		//2. Asociar la Minuta al Reparto Notarial
		RepartoNotarialEnhanced repartoPers = new RepartoNotarialEnhanced();
		repartoPers = (RepartoNotarialEnhanced) pm.getObjectById(idReparto,true);
		if (repartoPers == null)
		{
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException ("No se encontró el Reparto al que se quiere asociar a la Minuta");
		}
		
		logError ++;
		minutaPers.setRepartoNotarial(repartoPers);
		
		//3. Mover la Notaria de la Cola (del primero al último)
		OficinaCategoriaEnhanced ofCatPers = new OficinaCategoriaEnhanced();
		ofCatPers = (OficinaCategoriaEnhanced) pm.getObjectById(idNotaria, true);
		if (ofCatPers == null)
		{
			if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
			throw new DAOException ("No se encontró la OficinaCategoria en la que se quiere repartir la Minuta");
		}
		
		logError ++;
		
		long pesoNot = ofCatPers.getPesoReparto();
		pesoNot++;
		ofCatPers.setPesoReparto(pesoNot);
		
        //	4. Se avanza el turno
						
		
		ServiceLocator service = null;
		service = ServiceLocator.getInstancia();
		HermodServiceInterface hermodInterfaz;
		hermodInterfaz = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
						
		Hashtable parametros = new Hashtable();
												
		//Revisar la construcción de estos objetos.
		
		Turno turno = HermodDAOFactory.getFactory().getTurnosDAO().getTurnoByWFId(this.getWFIdBySolicitudID(minutaPers.getIdMinuta(),pm));
		Fase faseReparto = new Fase(); 
		faseReparto.setID("REP_REPARTO");
		String username = usuario.getUsername();
		parametros.put(CInfoUsuario.USUARIO_SIR, username);
		parametros.put("RESULT",AWRepartoNotarial.EXITO);
		
						
		//boolean resultadoAvance = hermodInterfaz.avanzarTurno(turno,faseReparto,parametros,CAvanzarTurno.AVANZAR_NORMAL);
		boolean resultadoAvance = hermodInterfaz.avanzarTurnoNuevoNormal (turno,faseReparto,parametros,usuario);
						
		if (resultadoAvance == true){
			pm.currentTransaction().commit();
			
			pm.makeTransient(minutaPers.getOficinaCategoriaAsignada().getOficinaOrigen());
			pm.makeTransient(minutaPers.getOficinaCategoriaAsignada());
			pm.makeTransient(minutaPers);
	    }
			
		else if (resultadoAvance ==false)
		{
			pm.currentTransaction().rollback();
			minutaPers=null;
		}
		
				
	  }
	  catch (Throwable e)
	  {
		if (pm.currentTransaction().isActive())
		{
			pm.currentTransaction().rollback();
		}
		
		String logString ="";
		//El error se presento justamente en AvanzarTurno.
		if (logError == 4)
		{
			logString = "SE PRESENTO ERROR AL AVANZAR TURNO EN PROCESO" +
				"TRANSACCIONAL DE REALIZAR REPARTO NOTARIAL";
		}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage()+logString);
		throw new DAOException(e.getMessage(),e);			
	  } 
				 
	finally {
		pm.close();
	}
		
	if (minutaPers != null)
	{
		return (Minuta) minutaPers.toTransferObject();
	}
	return  null;
}



/**
 * Obtiene el id de Workflow dado el id de una solicitud
 * @param idSol identificador de la Solicitud de la cual se quiere obtener el id
 * de workflow.
 * @throws <code>DAOException</code>
 * @return El id de Workflow asociado con el id de Solicitud recibido como 
 * parámetro.
 * @author dlopez
 * 
 */
 private String getIdWorkflowByIdSolicitud(String idSol) throws DAOException
 {
	String rta = null;
	PersistenceManager pm = AdministradorPM.getPM();

	try {
		
		
		Query query = pm.newQuery(TurnoEnhanced.class);
		query.declareParameters("String solId");
		query.setFilter("this.solicitud.idSolicitud == solId && this.idCirculo==this.solicitud.circulo");

		Collection col = (Collection) query.execute(idSol);

		if (col.size() == 0) {
				 rta = null;
		} 
		
		else {
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				TurnoEnhanced rtaTurno = (TurnoEnhanced) iter.next();
				rta = rtaTurno.getIdWorkflow();
			 }
			 query.closeAll();
		}
	}
	catch (Throwable e)
	{
		if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(),e);			
	}
				 
	finally {
		pm.close();
	}

	return rta;
 }



 /**
  * Obtiene el nombre de una Notaría dado el id de la <code>OficinaOrigen</code>
  * @param idNotaria identificador de la Notaría de la cual se quiere obtener 
  * el nombre. 
  * @throws <code>DAOException</code>
  * @return El nombre de la Notaría con el identificador pasado como 
  * parámetro. 
  * @author dlopez
  * 
  */
  private String getNombreNotariaById(String idNotaria) throws DAOException
  {
	 String rta = null;
	 PersistenceManager pm = AdministradorPM.getPM();

	 try {
		
		 String idOfOrigen = idNotaria;
		 
		 Query query = pm.newQuery(OficinaOrigenEnhanced.class);
		 query.declareParameters("String idOfOrigen");
		 query.setFilter("this.idOficinaOrigen == idOfOrigen");

		 Collection col = (Collection) query.execute(idOfOrigen);

		 if (col.size() == 0) {
				  rta = null;
		 } 
		
		 else {
			 for (Iterator iter = col.iterator(); iter.hasNext();) {
				 OficinaOrigenEnhanced notariaEnh = (OficinaOrigenEnhanced) iter.next();
				 rta = notariaEnh.getNombre();
			  }
			  query.closeAll();
		 }
	 }
	 catch (Throwable e)
	 {
		 if (pm.currentTransaction().isActive())
			 {
				 pm.currentTransaction().rollback();
			 }
		 Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		 throw new DAOException(e.getMessage(),e);			
	 }
				 
	 finally {
		 pm.close();
	 }

	 return rta;
  }



  /**
  * Realiza la Restitución de Reparto Notarial para la Notaría que la solicita.
  * El proceso de Restitución realiza las siguientes acciones:
  * 1. Marca la <code>Solicitud</code> como aceptada.
  * 2. Anula la <code>Minuta</code> asociada con la <code>Solicitud</code> 
  * 3. Coloca la Notaría que realizó la solicitud como primera, dentro de la
  * cola de Notarías para la categoría a la que pertenecía la <code>Minuta</code>
  * @param idSolicitud Identificador de la Solicitud de Restitucion. 
  * @throws <code>DAOException</code>
  * @see gov.sir.core.negocio.modelo.SolicitudRestitucionRepartoNotarial
  * @see gov.sir.core.negocio.modelo.Minuta
  */
  public Hashtable realizarRestitucionRepartoNotarial(String idSolicitud)
		 throws DAOException
		 {
			Hashtable respuesta = new Hashtable();
			PersistenceManager pm = AdministradorPM.getPM();

			try 
			{
		
				//pm.currentTransaction().setOptimistic(false);
				pm.currentTransaction().begin();	
		 
				//1. Marcar la Solicitud como aceptada.
				SolicitudRestitucionRepartoEnhanced solPers = new SolicitudRestitucionRepartoEnhanced();
				SolicitudEnhancedPk solEnhID = new SolicitudEnhancedPk();
				solEnhID.idSolicitud = idSolicitud;
						
				solPers = (SolicitudRestitucionRepartoEnhanced) pm.getObjectById(solEnhID,true);
						   
				if (solPers == null)
				{
					throw new DAOException ("No se encontró la Solicitud de Restitución de Reparto");
				}
				
				solPers.setAceptacion(true);
				
				
				//2. Obtener la Minuta sobre la cual se solicita Restitucion.
				MinutaEnhanced minEnhanced = this.getMinutaBySolRestitucion (idSolicitud);
				MinutaEnhancedPk minEnhID = new MinutaEnhancedPk();
				minEnhID.idMinuta = minEnhanced.getIdMinuta();
						
				minEnhanced = (MinutaEnhanced) pm.getObjectById(minEnhID,true);
						   
				if (minEnhanced == null)
				{
					throw new DAOException ("No se encontró la Minuta asociada a la Solicitud de Restitución de Reparto");
				}
				
				//3. Marcar la minuta como anulada
				
				minEnhanced.setEstado(CReparto.MINUTA_ANULADA);
				
				
				//4. Colocar la Notaría como primera dentro de la Cola de Notarías
				//para la categoría dada. 
				String idNotaria = minEnhanced.getOficinaCategoriaAsignada().getIdOficinaOrigen();
				String idCategoria = minEnhanced.getCategoria().getIdCategoria();
                                /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                Long version = minEnhanced.getOficinaCategoriaAsignada().getVersion();
				OficinaCategoriaEnhancedPk idOfCat = new OficinaCategoriaEnhancedPk();
				idOfCat.idCategoria = idCategoria;
				idOfCat.idOficinaOrigen = idNotaria;
                                /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
				idOfCat.version = version;
				OficinaCategoriaEnhanced notCategoria =  (OficinaCategoriaEnhanced)pm.getObjectById(idOfCat, true);
				long longPesoReparto = notCategoria.getPesoReparto();
				longPesoReparto --;
				notCategoria.setPesoReparto(longPesoReparto);
				
				pm.currentTransaction().commit();
				
			}
			catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
            throw e;
        }
			catch (Throwable e)
	    {
		if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(),e);			
	}
				 
	finally {
		pm.close();
	}
						
	   
	return respuesta;
}
	
	
/**
 * Obtiene la <code>Minuta</code> asociada con una <code>
 * SolicitudRestitucionReparto</code>
 * <p>La asociación se realiza a través del TurnoAnterior de la 
 * solicitud. 
 * @param idSolicitud identificador de la <code>
 * SolicitudRestitucionReparto</code> de la cual se desea obener la
 * <code>Minuta</code> asociada.
 * @throws <code>DAOException</code>
 * @return objeto <code>MinutaEnhanced</code> que está asociado con
 * la <code>SolicitudRestitucionReparto</code>
 */
 private MinutaEnhanced getMinutaBySolRestitucion(String idSolicitud) 
 throws DAOException
 {
	MinutaEnhanced rta = null;
	PersistenceManager pm = AdministradorPM.getPM();

	try {
		
		SolicitudEnhancedPk solId = new SolicitudEnhancedPk();
		solId.idSolicitud = idSolicitud;
		
		SolicitudRestitucionRepartoEnhanced sol = (SolicitudRestitucionRepartoEnhanced)	pm.getObjectById(solId,true);	 
		String idMinuta = sol.getTurnoAnterior().getSolicitud().getIdSolicitud();
		
		Query query = pm.newQuery(MinutaEnhanced.class);
		query.declareParameters("String idMinuta");
		query.setFilter("this.idMinuta == idMinuta");

		Collection col = (Collection) query.execute(idMinuta);

		if (col.size() == 0) {
				 rta = null;
		} 
		
		else {
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				MinutaEnhanced minutaEnh = (MinutaEnhanced) iter.next();
				rta = minutaEnh;
				pm.makeTransient(minutaEnh);
			 }
			 query.closeAll();
		}
	}
	catch (Throwable e)
	{
		if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(),e);			
	}
				 
	finally {
		pm.close();
	}

	return rta;
 }


 /**
 * Servicio que permite adicionar el texto correspondiente a una resolución 
 * de restitución de reparto notarial a una <code>SolicitudRestitucionReparto</code>
 * @param resolucion Resolución de restitución que va a ser asociada a la <code>Solicitud</code>
 * @param solicitud La <code>SolicitudRestitucionReparto</code> a la que se va  a
 * asignar la resolución.
 * @param observaciones Comentario que explica por qué fue aceptada o rechazada una solicitud de restitución
 * @param fechaResolucion fecha en la que fue creada la resolución de restitución.
 * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
* resultado de la operación. 
 * @throws <code>DAOException</code>
 * @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
 */
 public boolean addResolucionToSolicitudRestitucion (SolicitudRestitucionReparto solicitud, String resolucion, String observaciones, Date fechaResolucion)
	 throws DAOException
	 {
		
		PersistenceManager pm = AdministradorPM.getPM();
		SolicitudRestitucionRepartoEnhanced solReparto = null;
		SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
		solEnhId.idSolicitud = solicitud.getIdSolicitud();
			
		try 
		{
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			solReparto = (SolicitudRestitucionRepartoEnhanced) pm.getObjectById(solEnhId,true);
				
			if (solReparto == null)
			{
				throw new DAOException("No se encontro la solicitud asociada");
			}
				
			solReparto.setResolucion(resolucion);
			solReparto.setFecha(new Date());
			solReparto.setFechaResolucion(fechaResolucion);
			solReparto.setObservaciones(observaciones);
			pm.currentTransaction().commit();
				
		}
		
		catch (Throwable e)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(),e);			
			}
				 
			finally 
			{
				pm.close();
			}
			
			return true; 
		}
		   
	/**
	* Servicio que permite marcar como rechazada  una <code>SolicitudRestitucionReparto</code>
	* @param solicitud La <code>SolicitudRestitucionReparto</code> que va a ser marcada como
	* rechazada. 
	* @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación. 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	*/
	public boolean rechazarSolicitudRestitucion (SolicitudRestitucionReparto solicitud)
		throws DAOException
		{
		
		   PersistenceManager pm = AdministradorPM.getPM();
		   SolicitudRestitucionRepartoEnhanced solReparto = null;
		   SolicitudEnhancedPk solEnhId = new SolicitudEnhancedPk();
		   solEnhId.idSolicitud = solicitud.getIdSolicitud();
			
		   try 
		   {
			   pm.currentTransaction().setOptimistic(false);
			   pm.currentTransaction().begin();
			   solReparto = (SolicitudRestitucionRepartoEnhanced) pm.getObjectById(solEnhId,true);
				
			   if (solReparto == null)
			   {
				   throw new DAOException("No se encontro la solicitud asociada");
			   }
				
			   solReparto.setAceptacion(false);
			   pm.currentTransaction().commit();
				
		   }
		
		   catch (Throwable e)
		   {
			   if (pm.currentTransaction().isActive())
			   {
				   pm.currentTransaction().rollback();
			   }
				   Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				   throw new DAOException(e.getMessage(),e);			
			   }
				 
			   finally 
			   {
				   pm.close();
			   }
			
			   return true; 
		   }
		   
		   

	 /**
	 * Obtiene y avanza la secuencia de los repartos notariales, de acuerdo 
	 * a los parametros recibidos. 
	 * @param circuloId El identificador del <code>Circulo</code> registral en el que se va a 
	 * realizar el <code>RepartoNotarial</code>.
	 * @param year El año en el que se va a realizar el reparto. 
	 * @param pm Persistence Manager de la transacción.
	 * @return El secuencial requerido. 
	 * @throws DAOException
	 */
	protected long getSecuencialReparto (String circuloId, String year, PersistenceManager pm)
		throws DAOException {
		long rta = -1;
		VersantPersistenceManager pm2 = null;

		if (circuloId == null) {
			
			throw new DAOException("El identificador del círculo es inválido");
		}
		
			try {
				//Se hace el cambio de tipo de bloqueo pesimista para
				//que se bloquee la tabla la cual  nos
				//provee el secuencial
				pm2 = (VersantPersistenceManager) pm;
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
				pm = pm2;

				CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
				sid.anio = year;
				sid.idCirculo = circuloId;
				Long idProcesoString = new Long (CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
				sid.idProceso = idProcesoString.longValue();
				CirculoProcesoEnhanced circuloProceso = this.getSecuencialRepartoByID(sid, pm);
				if (circuloProceso == null)
				{
                                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                                    throw new DAOException ("No se pudo obtener el secuencial para el reparto notarial");
				}
				circuloProceso.setLastIdRepartoNotarial(circuloProceso.getLastIdRepartoNotarial() + 1);

				//Volvemos a setear el tipo de bloqueo pesimista
				//para que no nos bloquee los siquientes registros
				//consultados
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

				rta = circuloProceso.getLastIdRepartoNotarial(); 
			} 
			catch (JDOObjectNotFoundException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se encontró el registro de la secuencia",
					e);
			} 
			catch (JDOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", e);
			}
			catch (DAOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", e);
			}
			catch (Exception e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", e);
			}
			catch (Throwable t) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", t);
			}
		
		return rta;
	}
	
	/**
	 * Obtiene el secuencia secuencia de los repartos notariales, de acuerdo 
	 * a los parametros recibidos. 
	 * @param circuloId El identificador del <code>Circulo</code> registral en el que se va a 
	 * realizar el <code>RepartoNotarial</code>.
	 * @param year El año en el que se va a realizar el reparto. 
	 * @param pm Persistence Manager de la transacción.
	 * @return El secuencial requerido. 
	 * @throws DAOException
	 */
	protected long getLastSecuencialReparto (String circuloId, String year, PersistenceManager pm)
		throws DAOException {
		long rta = -1;
		VersantPersistenceManager pm2 = null;

		if (circuloId == null) {
			
			throw new DAOException("El identificador del círculo es inválido");
		}
		
			try {
				//Se hace el cambio de tipo de bloqueo pesimista para
				//que se bloquee la tabla la cual  nos
				//provee el secuencial
				pm2 = (VersantPersistenceManager) pm;
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
				pm = pm2;

				CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
				sid.anio = year;
				sid.idCirculo = circuloId;
				Long idProcesoString = new Long (CProceso.PROCESO_REPARTO_NOTARIAL_MINUTAS);
				sid.idProceso = idProcesoString.longValue();
				CirculoProcesoEnhanced circuloProceso = this.getSecuencialRepartoByID(sid, pm);
				if (circuloProceso == null)
				{
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
					throw new DAOException ("No se pudo obtener el secuencial para el reparto notarial");
				}
				//Volvemos a setear el tipo de bloqueo pesimista
				//para que no nos bloquee los siquientes registros
				//consultados
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

				rta = circuloProceso.getLastIdRepartoNotarial(); 
			} 
			catch (JDOObjectNotFoundException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se encontró el registro de la secuencia",
					e);
			} 
			catch (JDOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", e);
			}
			catch (DAOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", e);
			}
			catch (Exception e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", e);
			}
			catch (Throwable t) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				throw new DAOException("No se pudo obtener el secuencial para asignar al proceso de reparto notarial", t);
			}
		
		return rta;
	}

	/**
	* Obtiene el secuencial de un reparto notarial dado su identificador.
	* <p>Método utilizado para transacciones
	* @param sID identificador del objeto <code>CirculoProceso</code> del cual se obtendrá
	* el secuencial del reparto. 
	* @param pm PersistenceManager de la transacción
	* @return CirculoProceso con sus atributos, uno de los cuales es el secuencial del reparto. 
	* @throws DAOException
	*/
	protected CirculoProcesoEnhanced getSecuencialRepartoByID(CirculoProcesoEnhancedPk sID,
		PersistenceManager pm) throws DAOException {
		CirculoProcesoEnhanced rta = null;

		if (sID  != null) {
			try {
				rta = (CirculoProcesoEnhanced) pm.getObjectById(sID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}
 
		return rta;
	}


	/**
	 * Obtiene el listado de entidades públicas que intervienen como otorgantes, en el proceso de reparto
	 * notarial 
	 * @return Listado de entidades públicas que intervienen como otorgantes en el proceso de reparto
	 * notarial.
	 * @throws DAOException
	 */
	public List getEntidadesReparto () throws DAOException
	{
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try 
		{

			//Se establece como criterio de ordenamiento el nombre de 
			//la entidad (Alfabéticamente)
			pm.currentTransaction().begin();
			Query q = pm.newQuery(EntidadPublicaEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes los elementos de la lista.
			while (it.hasNext()) 
			{	
				EntidadPublicaEnhanced obj = (EntidadPublicaEnhanced) it.next();
				NaturalezaJuridicaEntidadEnhanced natEnh = obj.getNaturalezaJuridica();
				pm.makeTransient(natEnh);
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
					pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}



	/**
	 * Obtiene el listado de entidades públicas que intervienen como otorgantes, en el proceso de reparto con una 
	 * naturaleza juridica determinada.
	 * @return Listado de entidades públicas que intervienen como otorgantes en el proceso de reparto
	 * notarial, que contienen la naturaleza jurídica dada.
	 * @throws Throwable
	 */	
	public List getEntidadesRepartoByNaturaleza (NaturalezaJuridicaEntidad naturalezaJuridicaReparto) throws DAOException
	{
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		String idNaturaleza = null;
		idNaturaleza = naturalezaJuridicaReparto.getIdNatJuridicaEntidad();
		
		if (idNaturaleza == null){
			throw new DAOException("El identificador de la naturaleza jurídica es nulo");
		}

		try 
		{

			//Se establece como criterio de ordenamiento el nombre de 
			//la entidad (Alfabéticamente)
			pm.currentTransaction().begin();
			Query q = pm.newQuery(EntidadPublicaEnhanced.class);
			q.declareParameters("String idNaturaleza");
			q.setFilter("this.naturalezaJuridica.idNatJuridicaEntidad == idNaturaleza");
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute(idNaturaleza);
			Iterator it = results.iterator();

			//Se hacen transientes los elementos de la lista.
			while (it.hasNext()) 
			{	
				EntidadPublicaEnhanced obj = (EntidadPublicaEnhanced) it.next();
				NaturalezaJuridicaEntidadEnhanced natEnh = obj.getNaturalezaJuridica();
				pm.makeTransient(natEnh);
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
					pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}



	/**
	 * Obtiene el listado de entidades públicas que intervienen como otorgantes, en el proceso de reparto
	 * notarial 
	 * @return Listado de entidades públicas que intervienen como otorgantes en el proceso de reparto
	 * notarial.
	 * @throws DAOException
	 */
	public List getNaturalezasJuridicasEntidades () throws DAOException
	{
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();

		try 
		{

			//Se establece como criterio de ordenamiento el nombre de 
			//la naturaleza (Alfabéticamente)
			pm.currentTransaction().begin();
			Query q = pm.newQuery(NaturalezaJuridicaEntidadEnhanced.class);
			q.setOrdering("nombre ascending");
			Collection results = (Collection) q.execute();
			Iterator it = results.iterator();

			//Se hacen transientes los elementos de la lista.
			while (it.hasNext()) 
			{	
				NaturalezaJuridicaEntidadEnhanced obj = (NaturalezaJuridicaEntidadEnhanced) it.next();
				pm.makeTransient(obj);
				lista.add(obj);
			}

			pm.currentTransaction().commit();
			q.close(results);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
					pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
	}




  /**
   * Obtiene las Minutas que se encuentran para repartir dentro de un círculo Notarial
   * @param circuloNotarial El <code>CirculoNotarial</code> en el que se deben buscar las
   * minutas que van a ser repartidas. 
   * @return lista de <code>Minuta</code> que están listas para ser repartidas dentro de
   * un <code>CirculoNotarial</code>
   * @throws DAOException
   */
   public List getMinutasByCirculoNotarial (CirculoNotarialEnhanced circuloNotarial) throws DAOException
   {
   	
	List lista = new ArrayList();
	PersistenceManager pm = AdministradorPM.getPM();

	try 
	{
		pm.currentTransaction().begin();
		
		Query query = pm.newQuery(MinutaEnhanced.class);
	
		query.declareParameters("String idCirNot, long estadoNoRepartido");
       query.setFilter("this.estado == estadoNoRepartido && this.circuloNotarial.idCirculo == idCirNot");
		String idCirNot = circuloNotarial.getIdCirculo();
		Long estadoNoRepartido = new Long(CReparto.MINUTA_NO_REPARTIDA);
		Collection col = (Collection)query.execute(idCirNot, estadoNoRepartido);
		for (Iterator iter = col.iterator(); iter.hasNext();) 
		{
			MinutaEnhanced minutaEnhanced = (MinutaEnhanced)iter.next();
			this.makeTransientMinuta(minutaEnhanced, pm);
			lista.add(minutaEnhanced);
		}
		
		pm.currentTransaction().commit();
		query.close(col);

	}
	catch (Throwable e) 
	{
		if (pm.currentTransaction().isActive()) 
		{
			pm.currentTransaction().rollback();
		}
		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(), e);
	}
	finally 
	{
		pm.close();
	}

			lista = TransferUtils.makeTransferAll(lista);
			return lista;
			   	
   }
   
   
   
   /**
   * Obtiene la Notaría que se encuentra de primera para realizar un reparto notarial,
   * dentro de una <code>Categoria</code> y un <code>CirculoNotarial</code> dado.
   * @param circuloNotarial El <code>CirculoNotarial</code> en el que se deben buscar la
   * primera Notaría para realizar el reparto.
   * @param categoria La Categoría en la que etá clasificada la <code>Minuta</code> que va
   * a ser repartida.
   * @return La <code>OficinaCategoria</code> que se encuentra de primera para realizar
   * el reparto dentro de el circulo notarial y categoria recibidas como parametros. 
   * @throws DAOException
   */
    protected OficinaCategoriaEnhanced obtenerPrimeraNotariaCirculoCategoria(CirculoNotarialEnhanced circuloNotarial, Categoria catMinuta) throws DAOException {
    
        OficinaCategoriaEnhanced categoriaSeleccionada = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            Query query = pm.newQuery(OficinaCategoriaEnhanced.class);
            query.declareVariables("ZonaNotarialEnhanced zonaNotarial");
            query.setOrdering("pesoReparto ascending, ordenInicial ascending");
            query.declareParameters("String idCirNot, String idNotaria, String idCateg");
            query.setFilter("this.idCategoria == idCateg && zonaNotarial.idCirculoNotarial == idCirNot && zonaNotarial.vereda == this.oficinaOrigen.vereda && this.oficinaOrigen.tipoOficina.idTipoOficina == idNotaria");

            String idCirNot = circuloNotarial.getIdCirculo();
            String idNotaria = CTipoOficina.TIPO_OFICINA_NOTARIA;
            String idCateg = catMinuta.getIdCategoria();

            /**
             * @author Daniel Forero
             * @change REQ 1203 - Se filtra manualmente la lista de categorias seleccionadas.
             */
            List resultados = new ArrayList();
            Collection col = (Collection) query.execute(idCirNot, idNotaria, idCateg);

            Iterator it = col.iterator();
            while (it.hasNext()) {
                resultados.add(it.next());
            }
            
            pm.currentTransaction().commit();
            query.close(col);

            /**
             * @author Daniel Forero
             * @change REQ 1203 - Se filtra manualmente la lista de categorias seleccionadas.
             */
            if(!resultados.isEmpty()){
                OficinaCategoriaEnhanced categoria = null;
                categoriaSeleccionada = null;
                
                for(int i = 0; i < resultados.size() && categoriaSeleccionada == null; i++){
                    categoriaSeleccionada = (OficinaCategoriaEnhanced) resultados.get(i);
                    
                    for(int j = i+1; j < resultados.size() && categoriaSeleccionada != null; j++){
                        categoria = (OficinaCategoriaEnhanced) resultados.get(j);
                        if(categoriaSeleccionada.getIdOficinaOrigen().equals(categoria.getIdOficinaOrigen())
                            && categoriaSeleccionada.getVersion().longValue() < categoria.getVersion().longValue()){
                            categoriaSeleccionada = null;
                        }
                    }
                }
            }
            
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDORepartosDAO.class, e.getMessage());

        } finally {
            pm.close();
        }

        return categoriaSeleccionada;
    }



   /**
   * Obtiene el listado de Minutas pendientes por repartir dentro de un Círculo
   * Registral.
   * @param circuloRegistral el <code>Circulo</code> en el cual se van a buscar
   * las minutas pendientes de reparto.
   * @return Lista de minutas por repartir dentro del <code>Círculo</code> recibido
   * como parámetro.
   * @throws <code>DAOException</code>
   */
   public List getMinutasNoRepartidasByCirculoRegistral(Circulo circuloRegistral) throws DAOException
   {
	    List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		Long estado = new Long(CReparto.MINUTA_NO_REPARTIDA);

		try 
		{
			pm.currentTransaction().begin();
			Query query = pm.newQuery(MinutaEnhanced.class);
			query.setOrdering("fechaCreacion ascending");			
			query.declareParameters("String idCirReg, long estadoNoRepartido");
			query.setFilter("this.estado == estadoNoRepartido &&\n"+
					"this.circuloNotarial.circuloRegistral.idCirculo == idCirReg");
			Collection col = (Collection)query.execute(circuloRegistral.getIdCirculo(),estado);
			
			for (Iterator iter = col.iterator(); iter.hasNext();) 
			{
				MinutaEnhanced minutaEnhanced = (MinutaEnhanced)iter.next();
				this.makeTransientMinuta(minutaEnhanced, pm);
				lista.add(minutaEnhanced);
			}
		
			pm.currentTransaction().commit();
			query.close(col);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally 
		{
			pm.close();
		}

				lista = TransferUtils.makeTransferAll(lista);
				return lista;
   	
   	
   }
   
   
   /**
   * Obtiene el listado de Círculos Notariales, asociados con un Círculo Registral.
   * @param circulo <code>Circulo</code> del cual se van a obtener los circulos notariales.
   * @return Lista de Círculos Notariales, asociados con un Círculo Registral.
   * @throws DAOException
   */
   public List getCirculosNotarialesByCirculoRegistral (Circulo circulo) throws DAOException
   {
	    List circulosNotariales = new ArrayList();
	    
	    String idCirculoNotarial = circulo.getIdCirculo();
		
		try
		{
			circulosNotariales = this.getCirculosNotarialesByCirculoRegistral(idCirculoNotarial);
		}
		catch (Throwable t)
		{
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
		}
		
		return circulosNotariales;
   }
   
   /**
    * Hace transiente la minuta y sust dependencias.
    * @param minuta Objeto minuta persistente
    * @param pm
    * @throws DAOException
    */
   protected void makeTransientMinuta(MinutaEnhanced minuta, PersistenceManager pm)throws DAOException{
	if (minuta != null) {
		try {
			//Se hacen transientes los otorgantes jurídicos
			MinutaEntidadPublicaEnhanced minEntPub;
			for(Iterator it = minuta.getEntidadesPublicas().iterator(); it.hasNext();){
				minEntPub = (MinutaEntidadPublicaEnhanced)it.next();
				pm.makeTransient(minEntPub.getEntidadPublica());
				pm.makeTransient(minEntPub);
			}
			pm.makeTransientAll(minuta.getEntidadesPublicas());
			
			//Se hacen transientes los otorgantes naturales:
			pm.makeTransientAll(minuta.getOtorgantesNaturales());
			
			//Se hacen transientes las acciones notariales:
			MinutaAccionNotarialEnhanced minAccNot;
			for(Iterator it = minuta.getAccionesNotariales().iterator(); it.hasNext();){
				minAccNot = (MinutaAccionNotarialEnhanced)it.next();
				pm.makeTransient(minAccNot.getAccionNotarial());
				pm.makeTransient(minAccNot);
			}
			pm.makeTransientAll(minuta.getAccionesNotariales());
			
			//pm.makeTransient(minuta.getAccionNotarial());
			pm.makeTransient(minuta.getCategoria());
			pm.makeTransient(minuta.getCirculoNotarial());
			
			//Hacer transiete la Notaría Asignada.
			if (minuta.getOficinaCategoriaAsignada() != null)
			{
				if(minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda()!=null){
					pm.makeTransient(minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio().getDepartamento());
					pm.makeTransient(minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio());
					pm.makeTransient(minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda());
				}
				pm.makeTransient(minuta.getOficinaCategoriaAsignada().getOficinaOrigen());
				pm.makeTransient(minuta.getOficinaCategoriaAsignada());
			}
				
		
			pm.makeTransient(minuta.getRepartoNotarial());
			pm.makeTransient(minuta);
			
		} catch (JDOException e) {
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	  }
   }
   
   
   /**
    * Realiza la consulta de las notarias de la categoría en el orden de peso
    * de reparto y orden inicial configurados en los objetos asociación 
    * @param catEnh
    * @param pm
    * @return
    */
   protected List getNotariasEnOrdenByCategoria(CategoriaEnhanced catEnh, PersistenceManager pm){
		Query query = pm.newQuery(OficinaCategoriaEnhanced.class);
		query.setOrdering("pesoReparto ascending, ordenInicial ascending");
		query.declareParameters("CategoriaEnhanced cat");
		query.setFilter("this.categoria==cat");
		List rta = (List)query.execute(catEnh);
		return rta;
   }
   
   

   /**
	* Realiza la consulta de las categorias persistentes
	* @param catEnh
	* @param pm
	* @return
	*/
   protected List getCategorias(PersistenceManager pm){
		Query query = pm.newQuery(CategoriaEnhanced.class);
		query.setOrdering("idCategoria ascending");
		List rta = (List)query.execute();
		return rta;
   }
   
   
   
   /**
    * Obtiene una hashtable con la lista de las oficinas origen por categoria, ordenadas
    * por el peso de reparto y orden inicial. La llave es la categoría y el valor es la lista de
    * oficinas origen. Objetos Enhanced transientes.
    * @return
    * @throws DAOException
    */
   public Hashtable getOficinasCategoriaEnOrdenByCategoria() throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable ht = new Hashtable();
		try {
			//Se obtienen las categorias configuradas en el sistema
			List categorias = this.getCategorias(pm);
			CategoriaEnhanced catEnh;
			for(Iterator it = categorias.iterator(); it.hasNext();){
				catEnh = (CategoriaEnhanced)it.next();
				
				//Por cada categoria recuperamos las notarias en orden
				List notarias = this.getNotariasEnOrdenByCategoria(catEnh, pm);
				
				//Hacemos transientes los objetos de asociación para NO modificarlos
				//en base de datos
				OficinaCategoriaEnhanced ofCatEnh;
				List notariasCategoria = new ArrayList();
				for(Iterator it2 = notarias.iterator(); it2.hasNext();){
					ofCatEnh = (OficinaCategoriaEnhanced) it2.next();
					pm.makeTransient(ofCatEnh.getOficinaOrigen());
					pm.makeTransient(ofCatEnh);
					notariasCategoria.add(ofCatEnh);
				}
				//pm.makeTransientAll(notarias);
				pm.makeTransient(catEnh);
				
				ht.put(catEnh, notariasCategoria);
			}
		
		}
		catch (Throwable e){
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
	
		return ht;
	   }
	   
	   
	   
 
   
	/**
	 * Obtiene una hashtable con el estado de la cola de las notarías
	 * por categoría. La llave es la categoría y el valor es la lista de
	 * oficinas origen.
	 * @return
	 * @throws DAOException
	 */
	public Hashtable getColasRepartoByCategoria() throws DAOException{
		 Hashtable ht = new Hashtable();
		 Hashtable rta = new Hashtable();
		 try {
			  ht = this.getOficinasCategoriaEnOrdenByCategoria();
			  
			  Enumeration llaves = (Enumeration) ht.keys();
			  CategoriaEnhanced key;
			  while (llaves.hasMoreElements()) {
					key = (CategoriaEnhanced) llaves.nextElement();
					List lista = (List)ht.get(key);
					
					//Se convierten a transfer las notarias y la categoria
					List notarias = TransferUtils.makeTransferAll(lista);
					Categoria cat = (Categoria)key.toTransferObject();
					
					List cola = new ArrayList();
					
					if(!notarias.isEmpty()){
						//Se obtiene la última notaría que es la que debe terminar
						OficinaCategoria fin = (OficinaCategoria)notarias.get(notarias.size()-1);
					
						boolean termina = false;
						while(!termina){
							/*
							if(this.isNotariaInCola(cola, fin.getOficinaOrigen())){
								//Termina porque ya tiene la última notaría en la cola
								termina = true;
							}
							else{*/
								//Obtiene la notaría de menos peso y la actualiza con el 
								//fin de agregarla a la cola
								OficinaOrigen toAdd = this.getAndUpdateNotariaMenosPeso(notarias);
								if(toAdd!=null){
									if(this.todasTienenMismoPeso(notarias)){
										cola.add(toAdd);
										cola.add(fin.getOficinaOrigen());
										termina = true;
									}
									else{
										cola.add(toAdd);
									}
								}
							//}
						}
					}
					rta.put(cat, cola);
				}
		
		 }
		 catch (Throwable e){
			 Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			 throw new DAOException(e.getMessage(), e);
		 }
	
		 return rta;
		}
		
		
	
	/**
	 * @param notarias
	 * @return
	 */
	private boolean todasTienenMismoPeso(List notarias) {
		OficinaCategoria fin = (OficinaCategoria)notarias.get(notarias.size()-1);
		boolean rta = true;
		for(Iterator it = notarias.iterator(); it.hasNext() && rta;){
			OficinaCategoria aux = (OficinaCategoria)it.next();
			if(aux.getPesoReparto() != fin.getPesoReparto()){
				rta = false;
			}
		}
		return rta;
	}



	/**
	 * Verifica si la notaria está dentro de la cola.
	 * La búsqueda lineal se realiza en orden inverso
	 * @param cola
	 * @param notaria
	 * @return
	 */
	public boolean isNotariaInCola(List cola, OficinaOrigen notaria){
		int tam = cola.size();
		OficinaOrigen aux;
		boolean found = false;
		for(int i = tam-1; (i>=0) && (!found); i--){
			aux = (OficinaOrigen) cola.get(i);
			if(aux.equals(notaria)){
				found = true;
			}
		}
		return found;
	}
	
	
	/**
	 * Obtiene la notaría de menor peso y la actualiza sumándole 1. Si existen
	 * varias notarías con el mismo peso se obtiene la que tenga menor orden
	 * inicial.
	 * @param notarias lista de notarias previamente ordenadas
	 * @return
	 */
	protected OficinaOrigen getAndUpdateNotariaMenosPeso(List notarias){
		OficinaCategoria oficinaCat = null;
		OficinaCategoria rta = new OficinaCategoria();
		OficinaCategoria aux = null;
		//La lista de notarias NO debe estar vacía
		if(!notarias.isEmpty()){
			//Se captura la primera notaria que es la que probablemente
			//se debe retornar y actualizar, a menos que existan otras
			//consecutivas con el mismo peso y orden inicial diferente
			oficinaCat = (OficinaCategoria)notarias.get(0);
			if(notarias.size()>2){
				//Si existen por lo menos dos en la lista, se verifican
				//los consecutivos para mirar si tienen el mismo peso
				boolean found = false;
				int i = 1;
				for(;(i<notarias.size()) && (!found);i++){
					aux = (OficinaCategoria) notarias.get(i);
					if(aux.getPesoReparto()>oficinaCat.getPesoReparto()){
						found = true;
					}
				}
				i--;
				if(i==1){
					//El peso de la notaria consecutiva es mayor que la primera
					//La respuesta es la primera
					oficinaCat.setPesoReparto(oficinaCat.getPesoReparto()+1);
					rta = oficinaCat;
				}
				else{
					//Se tiene que buscar de las notarías que tienen el mismo
					//peso cuál es la de menor orden inicial
					int indiceNotariaMenorOrden = this.getIndexNotariaConMenorOrdenInicial(notarias, i);
					rta = (OficinaCategoria)notarias.get(indiceNotariaMenorOrden);
					rta.setPesoReparto(rta.getPesoReparto()+1);
					
					//Se pone de última la notaria actualizada dentro de las notarías
					//con el mismo peso
					notarias.remove(indiceNotariaMenorOrden);
					notarias.add(i-1, rta);
				}
			}
			else{
				//Solo se tiene una notaria en la lista
				oficinaCat.setPesoReparto(oficinaCat.getPesoReparto()+1);
				rta = oficinaCat;
			}
		}
		return rta.getOficinaOrigen();
	}



	/**
	 * Obtiene el índice de la notaría que tenga menor orden inicial
	 * entre el conjunto de notarías desde el inicio hasta tam
	 * @param notarias
	 * @param i
	 */
	protected int getIndexNotariaConMenorOrdenInicial(List notarias, int tam) {
		int numeroMenor = 0;
		int indiceMenor = 0;
		OficinaCategoria aux; 
		int i = 0;
		for(;i<tam; i++){
			aux = (OficinaCategoria)notarias.get(i);
			if(i==0){
				numeroMenor = aux.getOrdenInicial();
				indiceMenor = 0;
			}
			else{
				if(aux.getOrdenInicial()<numeroMenor){
					numeroMenor = aux.getOrdenInicial();
					indiceMenor = i;
				}
			}
		}
		return indiceMenor;
		
	}
   
   
	/**
	* Obtiene el listado de Minutas en las que aparece como otorgante una persona
	* natural.
	* @param otorgante, nombre del otorgante que se quiere consultar dentro del listado 
	* de minutas.
	* @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
	* @return Lista de minutas en las que aparece el otorgante recibido como parámetro.
	* @see gov.sir.core.negocio.modelo.constantes.CReparto
	* @throws <code>DAOException</code>
	*/
	public List getMinutasByOtorganteNatural(String otorganteNatural, long estado, Circulo circuloRegistral) throws DAOException{
		
		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		Long estadoMinuta = new Long(estado);

		try 
		{
			String idCirculo = circuloRegistral.getIdCirculo();
			pm.currentTransaction().begin();
			Query query = pm.newQuery(MinutaEnhanced.class);
			query.declareVariables("OtorganteNaturalEnhanced otorg; CirculoNotarialEnhanced cn");
			query.declareParameters("String idCirculo, long idEstado, String nombreOtorg");
			query.setFilter("this.circuloNotarial == cn && cn.circuloRegistral.idCirculo == idCirculo && " +
					"this.estado == idEstado && this.otorgantesNaturales.contains(otorg)" +
					"&& (otorg.nombre.startsWith(nombreOtorg)|| otorg.nombre.endsWith(nombreOtorg))");
			
			Collection col = (Collection)query.execute(idCirculo, estadoMinuta, otorganteNatural);
			for (Iterator iter = col.iterator(); iter.hasNext();) {
				MinutaEnhanced minutaEnhanced = (MinutaEnhanced)iter.next();
					this.makeTransientMinuta(minutaEnhanced, pm);
					lista.add(minutaEnhanced);
				}
		
			pm.currentTransaction().commit();
			query.close(col);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
   	
		}
		
		
  
	/**
	* Obtiene el listado de Minutas en las que aparece como otorgante una 
	* entidad pública.
	* @param otorgantePublico, nombre del otorgante que se quiere consultar dentro del listado 
	* de minutas.
	* @param estado indica si la <code>Minuta</code> ha o no ha sido repartida.
	* @return Lista de minutas en las que aparece el otorgante recibido como parámetro.
	* @see gov.sir.core.negocio.modelo.constantes.CReparto
	* @throws <code>DAOException</code>
	*/
	public List getMinutasByOtorgantePublico(String otorgantePublico, long estado, Circulo circuloRegistral) throws DAOException{
		
		List lista = new ArrayList();
		List listaTemp = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		Long estadoMinuta = new Long(estado);
		JDOTurnosDAO auxiliar = new JDOTurnosDAO();
		try 
		{
			String idCirculo = circuloRegistral.getIdCirculo();
			pm.currentTransaction().begin();
						
			Query query = pm.newQuery(MinutaEntidadPublicaEnhanced.class);
			query.declareParameters("long idEstado, String otorgante");
			query.setFilter("this.minuta.estado==idEstado && (this.entidadPublica.nombre.startsWith(otorgante)||this.entidadPublica.nombre.endsWith(otorgante)) ");
			Collection col = (Collection)query.execute(estadoMinuta, otorgantePublico);
			for (Iterator iter = col.iterator(); iter.hasNext();) 
			{
				MinutaEntidadPublicaEnhanced minutaentidadpublicaenhanced = (MinutaEntidadPublicaEnhanced)iter.next();
				MinutaEnhanced minutaAsociada = minutaentidadpublicaenhanced.getMinuta();
				//this.makeTransientMinuta(minutaAsociada, pm);
				listaTemp.add(minutaAsociada);
			}
			
			/**Filtrar solo las minutas que pertenecen al circulo del usuario en sesion*/
			for (int cont=0;cont<listaTemp.size();cont++){
				SolicitudPk oid=new SolicitudPk();
				oid.idSolicitud=((MinutaEnhanced)listaTemp.get(cont)).getIdMinuta();
				Turno turno = auxiliar.getTurnoBySolicitud(oid);
				if(turno.getIdCirculo().equals(idCirculo)){
					this.makeTransientMinuta((MinutaEnhanced)listaTemp.get(cont), pm);
					lista.add(listaTemp.get(cont));
				}
			}
				
			pm.currentTransaction().commit();
			query.close(col);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;
   	
		}
		
		
	/**
	 * Obtiene el listado de Minutas radicadas dentro de un rango de fechas dado.
	 * @param fechaInicial fecha de inicio para el rango.
	 * @param fechaFinal fecha de finalización para el rango.
	 * @return Lista de minutas radicadas dentro del rango dado 
	 * @see gov.sir.core.negocio.modelo.constantes.CReparto
	 * @throws <code>HermodException</code>
	 */
	 public List getMinutasRadicadasByRangoFecha (Date fechaInicial, Date fechaFinal) throws DAOException
	 {

		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		
		try 
		{
			pm.currentTransaction().begin();
						
			Query query = pm.newQuery(MinutaEnhanced.class);
			query.declareParameters("Date fechaInicial, Date fechaFinal");
			query.setFilter("this.fechaCreacion>=fechaInicial && this.fechaCreacion<=fechaFinal");
			Collection col = (Collection)query.execute(fechaInicial, fechaFinal);
			for (Iterator iter = col.iterator(); iter.hasNext();) 
			{
				
				MinutaEnhanced minuta = (MinutaEnhanced) iter.next();
				this.makeTransientMinuta(minuta, pm);
				lista.add(minuta);
			}
			
				
			pm.currentTransaction().commit();
			query.close(col);

		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;

	 }
	
	
	
	/**
	* Obtiene el listado de Minutas repartidas dentro de un rango de fechas dado.
	* @param fechaInicial fecha de inicio para el rango.
	* @param fechaFinal fecha de finalización para el rango.
	* @param circulo resgitral al cual pertenece el usuario
	* @return Lista de minutas repartidas dentro del rango dado 
	* @throws <code>HermodException</code>
	*/
	public List getMinutasRepartidasByRangoFecha (Date fechaInicial, Date fechaFinal, Circulo circuloRegistral) throws DAOException
	{

		List lista = new ArrayList();
		PersistenceManager pm = AdministradorPM.getPM();
		String idCirculo = circuloRegistral.getIdCirculo();
		
		try 
		{
			pm.currentTransaction().begin();
						
			Query query = pm.newQuery(MinutaEnhanced.class);
			query.declareVariables("CirculoNotarialEnhanced cn");
			query.declareParameters("Date fechaInicial, Date fechaFinal, String idCirculo");
			query.setFilter("this.circuloNotarial == cn && cn.circuloRegistral.idCirculo == idCirculo&&this.repartoNotarial.fechaCreacion>=fechaInicial && this.repartoNotarial.fechaCreacion<=fechaFinal");
			Collection col = (Collection)query.execute(fechaInicial, fechaFinal, idCirculo);
			for (Iterator iter = col.iterator(); iter.hasNext();) 
			{
				
				MinutaEnhanced minuta = (MinutaEnhanced) iter.next();
					this.makeTransientMinuta(minuta, pm);
					lista.add(minuta);
				}
			
				
			pm.currentTransaction().commit();
			query.close(col);
		}
		catch (Throwable e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		finally 
		{
			pm.close();
		}

		lista = TransferUtils.makeTransferAll(lista);
		return lista;


	}


	/**
	* Permite agregar una Naturaleza Jurídica de Entidad Pública a la configuración del Sistema.
	* @param naturaleza Naturaleza Jurídica de Entidad que va a ser adicionada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	*/
	public boolean agregarNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException
	{
		PersistenceManager pm = AdministradorPM.getPM();
		NaturalezaJuridicaEntidadEnhanced naturalezaJuridica = NaturalezaJuridicaEntidadEnhanced.enhance(naturaleza);
		boolean respuesta = false;

		try 
		{
		
		//Validación del identificador de la naturaleza juridica.
		NaturalezaJuridicaEntidadEnhancedPk naturalezaId = new NaturalezaJuridicaEntidadEnhancedPk();
		naturalezaId.idNatJuridicaEntidad = naturaleza.getIdNatJuridicaEntidad();

		NaturalezaJuridicaEntidadEnhanced valid = this.getNaturalezaJuridicaEntidad(naturalezaId, pm);

		
		// El método genera una excepción si ya existe una naturaleza juridica con el
		// identificador del objeto pasado como parámetro. 
		if (valid != null) 
		{
			throw new DAOException(
			"Ya existe una naturaleza juridica de entidad con el identificador: " +
					naturalezaId.idNatJuridicaEntidad);
		}

		
		naturaleza.setActivo(true);
		pm.currentTransaction().begin();
		pm.makePersistent(naturalezaJuridica);
		pm.currentTransaction().commit();
		respuesta = true;

		
	} 
	catch (JDOException e) 
	{
		if (pm.currentTransaction().isActive()) 
		{
		pm.currentTransaction().rollback();
		respuesta = false;
	    }

		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		respuesta = false;
		throw new DAOException(e.getMessage(), e);
	} 
	
	finally 
	{
		pm.close();
	}
	return respuesta;
	
	}
	
	
	/**
	* Permite editar una Naturaleza Jurídica de Entidad Pública para el reparto notarial.
	* @param naturaleza Naturaleza Jurídica de Entidad que va a ser editada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>Throwable</code>
	*/
	public boolean editarNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		boolean respuesta = false;
		
		try 
		{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//Recuperar el objeto naturaleza juridica de entidad pública persistente.
			String idNaturaleza = naturaleza.getIdNatJuridicaEntidad();
			if (idNaturaleza == null)
			{
				throw new DAOException("No se ingresó un identificador válido de naturaleza jurídica");
			}
			
			NaturalezaJuridicaEntidadEnhancedPk idNatEnh = new NaturalezaJuridicaEntidadEnhancedPk();
			idNatEnh.idNatJuridicaEntidad = idNaturaleza;
			
			NaturalezaJuridicaEntidadEnhanced naturalezaEnhanced = new NaturalezaJuridicaEntidadEnhanced();
			naturalezaEnhanced = (NaturalezaJuridicaEntidadEnhanced) pm.getObjectById(idNatEnh,true);
			
			if (naturalezaEnhanced == null)
			{
				throw new DAOException("No se encontró la naturaleza jurídica que se va a eliminar.");
			}
			
			naturalezaEnhanced.setExento(naturaleza.isExento());
			naturalezaEnhanced.setNombre(naturaleza.getNombre());
			pm.currentTransaction().commit();
			respuesta = true;
		}
		
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		
		catch (Throwable t) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		} 
	
		finally 
		{
			pm.close();
		}
			
		return respuesta;
	}
	
	
	/**
	* Permite actualizar el estado de una Naturaleza Jurídica de Entidad Pública para el reparto notarial.
	* @param naturaleza Naturaleza Jurídica de Entidad que va a ser actualizada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>Throwable</code>
	*/
	public boolean actualizarEstadoNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		boolean respuesta = false;
		
		try 
		{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//Recuperar el objeto naturaleza juridica de entidad pública persistente.
			String idNaturaleza = naturaleza.getIdNatJuridicaEntidad();
			if (idNaturaleza == null)
			{
				throw new DAOException("No se ingresó un identificador válido de naturaleza jurídica");
			}
			
			NaturalezaJuridicaEntidadEnhancedPk idNatEnh = new NaturalezaJuridicaEntidadEnhancedPk();
			idNatEnh.idNatJuridicaEntidad = idNaturaleza;
			
			NaturalezaJuridicaEntidadEnhanced naturalezaEnhanced = new NaturalezaJuridicaEntidadEnhanced();
			naturalezaEnhanced = (NaturalezaJuridicaEntidadEnhanced) pm.getObjectById(idNatEnh,true);
			
			if (naturalezaEnhanced == null)
			{
				throw new DAOException("No se encontró la naturaleza jurídica que se va a eliminar.");
			}
			
			naturalezaEnhanced.setActivo(naturaleza.isActivo());
			pm.currentTransaction().commit();
			respuesta = true;
		}
		
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		
		catch (Throwable t) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		} 
	
		finally 
		{
			pm.close();
		}
			
		return respuesta;
	}
		
	
	
	/**
    * Permite obtener el identificador de workflow de un turno asociado con una minuta con id pasado como
    * parámetro.
	* @param idMinuta identificador de la minuta de la cual se desea obtener su turno asociado.
	* @return  identificador de workflow de un turno asociado con una minuta con id pasado como  parámetro.
	* @throws <code>DAOException </code>
	*/
	public String getIdWorkflowByIdMinuta(String idMinuta) throws DAOException
	{
		PersistenceManager pm = AdministradorPM.getPM();
		String respuesta = null;
		
		try
		{
			respuesta = this.getIdWorkflowByIdSolicitud(idMinuta);
			
		}
		catch (Throwable t)
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}
		finally 
		{
			pm.close();
		}
		return respuesta;
		
	}
	
	

	/**
	* Permite agregar una Entidad Pública a la configuración del Sistema.
	* @param entidadPublica <code>EntidadPublica</code> que va a ser adicionada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	*/
	public boolean addEntidadPublica (EntidadPublica entidadPublica) throws DAOException
	{
		PersistenceManager pm = AdministradorPM.getPM();
		EntidadPublicaEnhanced entidad = EntidadPublicaEnhanced.enhance(entidadPublica);
		boolean respuesta = false;

		try 
		{
		pm.currentTransaction().begin();
			
		JDOSolicitudesDAO solicitudDAO = new JDOSolicitudesDAO();
		long secuencia = solicitudDAO.getSecuencial(CSecuencias.SIR_OP_ENTIDAD_PUBLICA, pm); 	
		entidad.setIdEntidadPublica(new Long (secuencia).toString());
		
		//Validación del identificador de la entidad pública.
		EntidadPublicaEnhancedPk entidadId = new EntidadPublicaEnhancedPk();
		entidadId.idEntidadPublica = entidadId.idEntidadPublica;

		EntidadPublicaEnhanced valid = this.getEntidadPublica (entidadId, pm); 

		
		// El método genera una excepción si ya existe una naturaleza juridica con el
		// identificador del objeto pasado como parámetro. 
		if (valid != null) 
		{
			throw new DAOException(
			"Ya existe una entidad pública con el identificador: " +
					entidad.getIdEntidadPublica());
		}
		
		//Obtener la naturaleza juridica de la entidad publica.
		if (entidad.getNaturalezaJuridica() == null)
		{
			throw new DAOException("No se ingresó una naturaleza jurídica para la entidad");
		}
		
		else
		{
			NaturalezaJuridicaEntidadEnhancedPk idNatEntidad = new NaturalezaJuridicaEntidadEnhancedPk();
			idNatEntidad.idNatJuridicaEntidad = entidad.getNaturalezaJuridica().getIdNatJuridicaEntidad();
			
			NaturalezaJuridicaEntidadEnhanced natEntidad = (NaturalezaJuridicaEntidadEnhanced) pm.getObjectById(idNatEntidad,true);
			if (natEntidad == null)
			{
				throw new DAOException("No se encontró una naturaleza jurídica para la entidad con el id: "+idNatEntidad.idNatJuridicaEntidad);
			}
			else
			{
				entidad.setNaturalezaJuridica(natEntidad);
			}
		}
		

        entidad.setActivo(true);		
		pm.makePersistent(entidad);
		pm.currentTransaction().commit();
		respuesta = true;

		
	} 
	catch (JDOException e) 
	{
		if (pm.currentTransaction().isActive()) 
		{
		pm.currentTransaction().rollback();
		respuesta = false;
		}

		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		respuesta = false;
		throw new DAOException(e.getMessage(), e);
	} 
	catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
            throw e;
        }
	catch (Exception e) 
	{
		if (pm.currentTransaction().isActive()) 
		{
		pm.currentTransaction().rollback();
		respuesta = false;
		}

		Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		respuesta = false;
		throw new DAOException(e.getMessage(), e);
	}
	finally 
	{
		pm.close();
	}
	return respuesta;
	}
	
	
	

	/**
	 * Obtiene un objeto de tipo <code>EntidadPublica</code> dado su identificador.
	 * <p>Método usado en transacciones.
	 * <p>El método retorna null si no se encuentra la entidad con el
	 * identificador pasado como parámetro. 
	 * @param oid Identificador de la entidad pública
	 * @param pm Persistence Manager de la transacción. 
	 * @return objeto <code>EntidadPublicaEnhanced</code> 
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.EntidadPublica
	 */
	 protected EntidadPublicaEnhanced getEntidadPublica (EntidadPublicaEnhancedPk oid,
	   PersistenceManager pm) throws DAOException {
	   EntidadPublicaEnhanced rta = null;

	   if (oid.idEntidadPublica!= null) {
		   try {
			   rta = (EntidadPublicaEnhanced) pm.getObjectById(oid, true);
		   } 
		
		   //El método retorna null si no se encuentra la entidad pública con el
		   //identificador pasado como parámetro. 
		   catch (JDOObjectNotFoundException e) {
			   rta = null;
		   } catch (JDOException e) {
			   Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			   throw new DAOException(e.getMessage(), e);
		   }
	   }

	   return rta;
	 }
	 
	/**
	* Permite actualizar una Entidad Pública a la configuración del Sistema.
	* @param entidadPublica <code>EntidadPublica</code> que va a ser actualizado.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>Throwable</code>
	*/
	public boolean editarEntidadPublica (EntidadPublica entidadPublica) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		boolean respuesta = false;
		
		try 
		{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//Recuperar el objeto Entidad Pública persistente.
			String idEntidad = entidadPublica.getIdEntidadPublica();
			if (idEntidad == null)
			{
				throw new DAOException("No se ingresó un identificador válido de la entidad pública.");
			}
			
			EntidadPublicaEnhancedPk idEntEnh = new EntidadPublicaEnhancedPk();
			idEntEnh.idEntidadPublica = idEntidad;
			
			EntidadPublicaEnhanced entidadEnhanced = new EntidadPublicaEnhanced();
			entidadEnhanced = (EntidadPublicaEnhanced) pm.getObjectById(idEntEnh,true);
			
			NaturalezaJuridicaEntidadEnhancedPk id = new NaturalezaJuridicaEntidadEnhancedPk();
			id.idNatJuridicaEntidad = entidadPublica.getNaturalezaJuridica().getIdNatJuridicaEntidad();
			
			NaturalezaJuridicaEntidadEnhanced natEnhanced = new NaturalezaJuridicaEntidadEnhanced();
			natEnhanced = (NaturalezaJuridicaEntidadEnhanced)pm.getObjectById(id,true);
			
			if (entidadEnhanced == null)
			{
				throw new DAOException("No se encontró la Entidad Pública que se va a eliminar.");
			}
			
			entidadEnhanced.setNombre(entidadPublica.getNombre());
			entidadEnhanced.setActivo(entidadPublica.isActivo());
			entidadEnhanced.setNaturalezaJuridica(natEnhanced);
			pm.currentTransaction().commit();
			respuesta = true;
		}
		
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		
		catch (Throwable t) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		} 
	
		finally 
		{
			pm.close();
		}
			
		return respuesta;
	}
	 
	 

	/**
	* Permite eliminar una Entidad Pública de la configuración del Sistema.
	* @param entidadPublica <code>EntidadPublica</code> que va a ser eliminada
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	*/
	public boolean eliminarEntidadPublica (EntidadPublica entidadPublica) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		boolean respuesta = false;
		
		try 
		{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//Recuperar el objeto Entidad Pública persistente.
			String idEntidad = entidadPublica.getIdEntidadPublica();
			if (idEntidad == null)
			{
				throw new DAOException("No se ingresó un identificador válido de la entidad pública.");
			}
			
			EntidadPublicaEnhancedPk idEntEnh = new EntidadPublicaEnhancedPk();
			idEntEnh.idEntidadPublica = idEntidad;
			
			EntidadPublicaEnhanced entidadEnhanced = new EntidadPublicaEnhanced();
			entidadEnhanced = (EntidadPublicaEnhanced) pm.getObjectById(idEntEnh,true);
			
			if (entidadEnhanced == null)
			{
				throw new DAOException("No se encontró la Entidad Pública que se va a eliminar.");
			}
			
			pm.deletePersistent(entidadEnhanced);
			pm.currentTransaction().commit();
			respuesta = true;
		}
		
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		
		catch (Throwable t) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		} 
	
		finally 
		{
			pm.close();
		}
			
		return respuesta;
	}



	/**
	* Permite eliminar una Naturaleza Jurídica de Entidad Pública de la configuración del Sistema
	* @param naturaleza Naturaleza Jurídica de Entidad que va a ser eliminada.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	*/
	public boolean eliminarNaturalezaJuridicaEntidadPublica (NaturalezaJuridicaEntidad naturaleza) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		boolean respuesta = false;
		
		try 
		{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			//Recuperar el objeto naturaleza juridica de entidad pública persistente.
			String idNaturaleza = naturaleza.getIdNatJuridicaEntidad();
			if (idNaturaleza == null)
			{
				throw new DAOException("No se ingresó un identificador válido de naturaleza jurídica");
			}
			
			NaturalezaJuridicaEntidadEnhancedPk idNatEnh = new NaturalezaJuridicaEntidadEnhancedPk();
			idNatEnh.idNatJuridicaEntidad = idNaturaleza;
			
			NaturalezaJuridicaEntidadEnhanced naturalezaEnhanced = new NaturalezaJuridicaEntidadEnhanced();
			naturalezaEnhanced = (NaturalezaJuridicaEntidadEnhanced) pm.getObjectById(idNatEnh,true);
			
			if (naturalezaEnhanced == null)
			{
				throw new DAOException("No se encontró la naturaleza jurídica que se va a eliminar.");
			}
			

			pm.deletePersistent(naturalezaEnhanced);
			pm.currentTransaction().commit();
			respuesta = true;
		}
		
		catch (JDOException e) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		
		catch (Throwable t) 
		{
			if (pm.currentTransaction().isActive()) 
			{
				pm.currentTransaction().rollback();
				respuesta = false;
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		} 
	
		finally 
		{
			pm.close();
		}
			
		return respuesta;
	}
	


	/**
	* Obtiene el número de modificaciones permitidas en la edición de minutas de reparto notarial
	* @return el número de modificaciones permitidas en la edición de minutas de reparto notarial
	* @throws <code>DAOException</code>
	*/
	public int getNumModificacionesMinutas () throws DAOException
	{
	
    	PersistenceManager pm = AdministradorPM.getPM();

		try 
		{

		   //Se intenta realizar la búsqueda sobre la tabla OPLookupCodes, en la cual
		   //el type corresponde a reparto notarial y el type a numero máximo de ediciones.
		   pm.currentTransaction().begin();
		   Query query = pm.newQuery(OPLookupCodesEnhanced.class);
		   String Type = COPLookupTypes.REPARTO_NOTARIAL;
		   String Code = COPLookupCodes.NUM_MAXIMO_EDICIONES_MINUTAS;
		   int numeroImp = 1;

		   query.declareParameters("String Type, String Code");
		   query.setFilter("codigo == Code && tipo == Type");

		   Collection results = (Collection) query.execute(Type, Code);

		   Iterator it = results.iterator();

		   while (it.hasNext()) 
		   {

			OPLookupCodesEnhanced obj = (OPLookupCodesEnhanced) it.next();
			String value = obj.getValor();
			Integer valInt = new Integer(value);
			numeroImp = valInt.intValue();

		   }
		
		   query.close(results);
		   pm.currentTransaction().commit();
		   return numeroImp;

	   } 
	   
	   catch (Throwable e) 
	   {
		
		   if (pm.currentTransaction().isActive()) 
		   {
		       pm.currentTransaction().rollback();
		   }
		  Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
		  throw new DAOException(e.getMessage(), e);
		} 
		
		finally 
		{
			pm.close();
		}	
	}
    
    
    

	/**
	* Obtiene el listado de turnos de restitución asociados con una minuta. 
	* @return listado de turnos de restitución asociados con una minuta. 
	* @param idCir Círculo Registral asociado con la minuta
	* @param idMin Identificador de la minuta
	* @throws <code>DAOException</code>
	*/
	public List getListadoTurnosRestitucionMinutas (String idCir, String idMin) throws DAOException
	{
		
		JDOTurnosDAO daoTurno = new JDOTurnosDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		List listaRestituciones = new ArrayList();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		VersantPersistenceManager jdoPM = null;

		try {

			StringBuffer sqlStatement;
			sqlStatement = new StringBuffer(8192);

			sqlStatement.append(" SELECT a.ANIO, a.ID_CIRCULO, a.ID_PROCESO, a.ID_TURNO");
			sqlStatement.append(" FROM SIR_OP_TURNO a,");
			sqlStatement.append("      SIR_OP_SOLICITUD b,");
			sqlStatement.append("      SIR_OP_TURNO c");
			sqlStatement.append(" WHERE a.ID_SOLICITUD = b.ID_SOLICITUD");
			sqlStatement.append("   AND b.ID_TURNO_ANT = c.ID_TURNO");
			sqlStatement.append("   AND b.ID_PROCESO_ANT = c.ID_PROCESO");
			sqlStatement.append("   AND b.ID_CIRCULO_ANT = c.ID_CIRCULO");
			sqlStatement.append("   AND b.ANIO_ANT = c.ANIO");
			sqlStatement.append("   AND a.ID_PROCESO = 16");
			sqlStatement.append("   AND a.ID_CIRCULO =:1");
			sqlStatement.append("   AND c.ID_CIRCULO =:2");
			sqlStatement.append(" AND EXISTS (SELECT 1");
			sqlStatement.append("               FROM SIR_OP_SOLICITUD d,");
		    sqlStatement.append("                    SIR_OP_MINUTA e");
		    sqlStatement.append("             WHERE d.ID_SOLICITUD = e.ID_MINUTA");
		    sqlStatement.append("               AND e.ID_MINUTA =:3");
		    sqlStatement.append("               AND d.SLCT_TIPO_SOLICITUD = 7");
		    sqlStatement.append("               AND c.ID_SOLICITUD = d.ID_SOLICITUD)");

			jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();

			jdoPM.currentTransaction().begin();
			connection = jdoPM.getJdbcConnection(null);
			ps = connection.prepareStatement(sqlStatement.toString());

			
			ps.setString(1, idCir);
			ps.setString(2, idCir);
			ps.setString(3, idMin);

			rs = ps.executeQuery();

			while (rs.next()) {
				
			   TurnoPk idTurnoTemp = new TurnoPk();
			   idTurnoTemp.anio = rs.getString(1);
			   idTurnoTemp.idCirculo = rs.getString(2);
			   idTurnoTemp.idProceso = rs.getLong(3);
			   idTurnoTemp.idTurno = rs.getString(4);
			   Turno turnoTemporal = daoTurno.getTurnoByID(idTurnoTemp);
			   if (turnoTemporal != null)
			   {
				  listaRestituciones.add(turnoTemporal);
			   }
				
			}
			jdoPM.currentTransaction().commit();

		} catch (SQLException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOObjectNotFoundException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (JDOException e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (jdoPM.currentTransaction().isActive()) {
				jdoPM.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
                if(connection != null){
                    connection.close();
                }
				if (jdoPM != null) {
					jdoPM.close();
				}
			} catch (SQLException e) {
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return listaRestituciones;

	}
    
    
    
    
    
    
    
	/**
	* Modifica el atributo número de ediciones realizadas a una minuta
	* @param minuta La minuta en la que se va a modificar el atributo.
	* @throws <code>DAOException</code>
	*/
	public void updateNumModificacionesMinuta (Minuta minuta) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		MinutaEnhanced minutaEnh = null;
		MinutaEnhancedPk minEnhId = new MinutaEnhancedPk();
		minEnhId.idMinuta = minuta.getIdMinuta();
			
		try 
		{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			minutaEnh = (MinutaEnhanced) pm.getObjectById(minEnhId,true);
				
			if (minutaEnh == null)
			{
				throw new DAOException("No se encontro la minuta que se desea actualizar");
			}
				
			int numeroModificaciones = minutaEnh.getNumModificaciones();
			numeroModificaciones ++;
			minutaEnh.setNumModificaciones(numeroModificaciones);
			pm.currentTransaction().commit();
				
		}
		
		catch (Throwable e)
		{
		    if (pm.currentTransaction().isActive())
			{
			   pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(),e);			
		}
				 
		finally 
		{
			pm.close();
		}
			
					   	
	}
	
	
  
	/**
	* Obtiene el listado de Notarías que se encuentran pendientes de Reparto Notarial. 
	* @param circulo El <code>CirculoNotarial</code> en el cual se va a realizar la consulta. 
	* @return El listado de Notarías que se encuentran pendientes de Reparto Notarial. 
	* @throws <code>DAOException</code>
	*/
	public List getNotariasPendientesReparto (CirculoNotarial circulo) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		List listaNotarias = new ArrayList();
		
		return listaNotarias;
								   	
	}

	/**
	 * Agrega un circulo notarial
	 * @param circuloNotarial circulo notarial a agregar
	 * @throws Throwable
	 */
	public void agregarCirculoNotarial(CirculoNotarial circuloNotarial) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		
		CirculoNotarialEnhanced circuloNot=CirculoNotarialEnhanced.enhance(circuloNotarial);
		circuloNot.setFechaCreacion(Calendar.getInstance().getTime());
		
		try{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			CirculoEnhancedPk circuloId=new CirculoEnhancedPk();
			circuloId.idCirculo=circuloNotarial.getCirculoRegistral().getIdCirculo();
			CirculoEnhanced circuloReg=(CirculoEnhanced)pm.getObjectById(circuloId,true);
			circuloNot.setCirculoRegistral(circuloReg);
			
			pm.makePersistent(circuloNot);
			
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
	}
	
	/**
	 * Agrega un circulo notarial
	 * @param circuloNotarial circulo notarial a agregar
	 * @throws DAOException
	 */
	public void eliminarCirculoNotarial(CirculoNotarial circuloNotarial) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		
		CirculoNotarialEnhancedPk circuloNot=new CirculoNotarialEnhancedPk();
		circuloNot.idCirculo=circuloNotarial.getIdCirculo();
		try{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			CirculoNotarialEnhanced circEnh = (CirculoNotarialEnhanced)pm.getObjectById(circuloNot,true);
			pm.deletePersistent(circEnh);
			
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
	}
	
	/**
	 * Edita un circulo notarial
	 * @param circuloNotarial circulo notarial a editar
	 * @throws Throwable
	 */
	public void editarCirculoNotarial(CirculoNotarial circuloNotarial) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		
		CirculoNotarialEnhancedPk circuloNot=new CirculoNotarialEnhancedPk();
		circuloNot.idCirculo=circuloNotarial.getIdCirculo();
		try{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			CirculoNotarialEnhanced circEnh = (CirculoNotarialEnhanced)pm.getObjectById(circuloNot,true);
			circEnh.setNombre(circuloNotarial.getNombre());
			CirculoEnhancedPk circId=new CirculoEnhancedPk();
			circId.idCirculo=circuloNotarial.getCirculoRegistral().getIdCirculo();
			CirculoEnhanced circRegEnh = (CirculoEnhanced)pm.getObjectById(circId,true);
			
			circEnh.setNombre(circuloNotarial.getNombre());
			circEnh.setCirculoRegistral(circRegEnh);
			
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
	}
	
	public CirculoNotarial consultarCirculoNotarial(CirculoNotarialPk idCirculo) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		CirculoNotarial respuesta=null;
		CirculoNotarialEnhancedPk circuloNot=new CirculoNotarialEnhancedPk();
		circuloNot.idCirculo=idCirculo.idCirculo;
		CirculoNotarialEnhanced circEnh=null;
		try{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			circEnh = (CirculoNotarialEnhanced)pm.getObjectById(circuloNot,true);
			Iterator itZonas=circEnh.getZonasNotariales().iterator();
			while(itZonas.hasNext()){
				ZonaNotarialEnhanced zona=(ZonaNotarialEnhanced)itZonas.next();
				pm.makeTransient(zona.getVereda().getMunicipio().getDepartamento());
				pm.makeTransient(zona.getVereda().getMunicipio());
				pm.makeTransient(zona.getVereda());
				pm.makeTransient(zona);
			}
			pm.makeTransient(circEnh);
			pm.currentTransaction().commit();
			
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
		return (CirculoNotarial) circEnh.toTransferObject();
	}
	
	/**
	 * Agrega un zona notarial
	 * @param circuloNotarial zona notarial a agregar
	 * @throws DAOException
	 */
	public void agregarZonaNotarial(ZonaNotarial zonaNotarial) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		
		CirculoNotarialEnhancedPk idCirc=new CirculoNotarialEnhancedPk();
		idCirc.idCirculo=zonaNotarial.getIdCirculoNotarial();
			
		VeredaEnhancedPk veredaId=new VeredaEnhancedPk();
		veredaId.idDepartamento=zonaNotarial.getIdDepartamento();
		veredaId.idMunicipio=zonaNotarial.getIdMunicipio();
		veredaId.idVereda=zonaNotarial.getIdVereda();
		
		ZonaNotarialEnhanced zonaEnh = ZonaNotarialEnhanced.enhance(zonaNotarial);
		
		try{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			CirculoNotarialEnhanced circuloNot=(CirculoNotarialEnhanced)pm.getObjectById(idCirc,true);
			VeredaEnhanced veredaEnh=getVereda(veredaId,pm);
			zonaEnh.setCirculoNotarial(circuloNot);
			zonaEnh.setVereda(veredaEnh);
			zonaEnh.setFechaCreacion(Calendar.getInstance().getTime());
			pm.makePersistent(zonaEnh);
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
	}
	
	/**
	 * Elimina una zona notarial
	 * @param zonaNotarial zona notarial a eliminar
	 * @throws DAOException
	 */
	public void eliminarZonaNotarial(ZonaNotarial zonaNotarial) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
			
		ZonaNotarialEnhancedPk idZona = new ZonaNotarialEnhancedPk();
		idZona.idCirculoNotarial=zonaNotarial.getIdCirculoNotarial();
		idZona.idDepartamento=zonaNotarial.getIdDepartamento();
		idZona.idMunicipio=zonaNotarial.getIdMunicipio();
		idZona.idVereda=zonaNotarial.getIdVereda();
		
		try{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			ZonaNotarialEnhanced zona=(ZonaNotarialEnhanced)pm.getObjectById(idZona,true);
			pm.deletePersistent(zona);
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
	}
	
	/**
	 * Agrega una notaria a la cola de reparto notarial
	 * @param notaria
	 * @return
	 * @throws Throwable
	 */
	public void agregarNotariaReparto(OficinaOrigen notaria)throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		
		OficinaOrigenEnhancedPk idNotaria=new OficinaOrigenEnhancedPk();
		idNotaria.idOficinaOrigen=notaria.getIdOficinaOrigen();
		/*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                idNotaria.version = notaria.getVersion();
		try{
		   
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			OficinaOrigenEnhanced notariaEnh = (OficinaOrigenEnhanced)pm.getObjectById(idNotaria,true);
			Iterator itCategorias=notariaEnh.getCategorias().iterator();
			while(itCategorias.hasNext()){
				OficinaCategoriaEnhanced ofCateg=(OficinaCategoriaEnhanced)itCategorias.next();
				VersantQuery query = (VersantQuery)pm.newQuery(OficinaCategoriaEnhanced.class);
				query.setFilter("idCategoria == '"+ofCateg.getIdCategoria()+"'");
				query.setResult("max(pesoReparto)");
				Long pesoReparto=(Long)query.execute();
				query.closeAll();
				
				ofCateg.setPesoReparto(pesoReparto.longValue());
				
				VersantQuery query2 = (VersantQuery)pm.newQuery(OficinaCategoriaEnhanced.class);
				query2.setFilter("idCategoria == '"+ofCateg.getIdCategoria()+"'"+
						  " && pesoReparto == '"+pesoReparto.toString()+"'");
				query2.setResult("max(ordenInicial)");
				Integer orden=(Integer)query2.execute();
				
				ofCateg.setOrdenInicial(orden.intValue()+1);
			}
			
			pm.currentTransaction().commit();
		}catch (JDOException e){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}catch (Throwable t){
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException(t.getMessage(), t);
		}finally{
			pm.close();
		}
	}
	
	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Registral. 
	 * @param circ El <code>Circulo</code> Registral en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, número de Turno y notaría asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculo(Circulo circ, Usuario usuario, boolean tipo, String[] idsExtraordinarios) throws DAOException{

		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable tablaResultados = new Hashtable();
		List circulosNotariales = null;

		try { 
			//1. En este llamado se crea el proceso de Reparto Notarial que se está
			//iniciando.
			String idReparto = this.crearProcesoRepartoNotarial(circ, tipo);
			if(idReparto == null)
			{
				throw new DAOException ("No fue posible crear el proceso de Reparto Notarial," +
				"ninguna minuta fue repartida");
			}

			//Realizar el reparto extraordinario
			if (tipo)
			{ 
				for (int i = 0; i < idsExtraordinarios.length; i++) 
				{
					tablaResultados.putAll(
							this.realizarRepartoExtraordinario(idsExtraordinarios[i], idReparto, usuario)
							);
				}
			}


			//REALIZAR EL REPARTO ORDINARIO.
			else
			{
				// 2. Se obtiene el listado de los Circulos Notariales asociados con el
				// Circulo Registral Recibido. 

				String idCirculoNotarial = circ.getIdCirculo();
				circulosNotariales = this.getCirculosNotarialesByCirculoRegistral(idCirculoNotarial);
				if (circulosNotariales.size()==0)
				{
					throw new DAOException ("No fue posible crear el proceso de Reparto Notarial," +
					"No hay Círculos Notariales asociados con el Círculo Registral");
				}



				/*****************************************************************************/
				/*            REPARTO PARA CADA UNO DE LOS CIRCULOS NOTARIALES               */
				/*****************************************************************************/
				for (int i = 0; i< circulosNotariales.size(); i++)
				{



					//3. Obtener la lista de minutas para repartir en ese círculo.
					CirculoNotarial circuloNotarial = (CirculoNotarial) circulosNotariales.get(i);
					tablaResultados.putAll(
							this.realizarRepartoOrdinario(circuloNotarial, idReparto, usuario)
							);
				}
			}
		}catch (Throwable t)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException ("No fue posible completar el proceso de Reparto Notarial," +
					"Es posible que no todas las minutas hayan sido repartidas.",t);
		}

		finally {
			pm.close();
		}				


		return tablaResultados;		
	}



	public RepartoNotarial getRepartoNotarialByID(RepartoNotarialPk id) throws DAOException 
	{
		RepartoNotarialEnhanced rpn = null;
		RepartoNotarial rta = null;
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();
            rpn = this.getRepartoNotarialById(new RepartoNotarialEnhancedPk(id), pm);

            if (rpn != null) 
            {
                this.makeTransientRepartoNotarial(rpn, pm);
            }
			pm.currentTransaction().commit();
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartosDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();

            //turnoRetornado = (Turno) tr.toTransferObject();
        }

        if (rpn != null) {
            rta = (RepartoNotarial) rpn.toTransferObject(); //turnoRetornado;
        }

        return rta;
    }
	
	protected void makeTransientRepartoNotarial(RepartoNotarialEnhanced repartoNotarial, PersistenceManager pm)throws DAOException
	{
		if (repartoNotarial != null) 
		{
			try 
			{
				pm.makeTransientAll(repartoNotarial.getMinutas());
				pm.makeTransient(repartoNotarial);
			} 
			catch (JDOException e) 
			{
				Log.getInstance().error(JDORepartosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		  }
	}
	
	/** 
	 * Realiza la consula del ultimo IdReparto sin no se Ejecuta
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public String  consultarLastSecuencialCirculoNotarial(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo) throws DAOException{
		
		String rta;
		try {
			//1. En este llamado se crea el proceso de Reparto Notarial que se está
			//iniciando.
			rta = this.consultaProcesoRepartoNotarial(circuloNotarial.getCirculoRegistral(), tipo);
		} catch (Throwable t){
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException ("No fue posible completar el proceso de Reparto Notarial," +
					"Es posible que no todas las minutas hayan sido repartidas.",t);
		} 
		return rta;		
	}
	
	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Notarial. 
	 * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, número de Turno y notaría asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculoNotarialOrdinario(CirculoNotarial circuloNotarial, Usuario usuario) throws DAOException{
		
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable tablaResultados = new Hashtable();

		try {

			//1. En este llamado se crea el proceso de Reparto Notarial que se está
			//iniciando.
			String idReparto = this.crearProcesoRepartoNotarial(circuloNotarial.getCirculoRegistral(), false);
			if(idReparto == null)
			{
				throw new DAOException ("No fue posible crear el proceso de Reparto Notarial," +
				"ninguna minuta fue repartida");
			}

			//REALIZAR EL REPARTO ORDINARIO.
			tablaResultados.putAll(this.realizarRepartoOrdinario(circuloNotarial,idReparto,usuario));
			

		} catch (Throwable t)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException ("No fue posible completar el proceso de Reparto Notarial," +
					"Es posible que no todas las minutas hayan sido repartidas.",t);

		}

		finally {
			pm.close();
		}				

		return tablaResultados;		
	}
	
	/** 
	 * Realiza el <code>RepartoNotarial</code> de las Minutas pertenecientes 
	 * a un Circulo Notarial. 
	 * @param circ El <code>CirculoNotarial</code> Notarial en el que se va a realizar el Reparto
	 * @param usuario <code>Usuario</code> que realiza el reparto notarial.
	 * @param tipo indica si el reparto es normal (false) o extraordinario (true)
	 * @param idExtraordinario identificador del turno en el que se debe realizar un reparto 
	 * extraordinario. 
	 * @return Hashtable con las asociaciones, número de Turno y notaría asignada,
     * para cada uno de los Turnos que fueron repartidos. 
	 * @throws <code>DAOException</code>
	 * @see gov.sir.core.negocio.modelo.RepartoNotarial
	 * @see gov.sir.core.negocio.modelo.Minuta
	 */
	public Hashtable realizarRepartoCirculoNotarialExtraOrdinario(CirculoNotarial circuloNotarial, Usuario usuario, boolean tipo, String[] idsExtraordinaro) throws DAOException{
		
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable resultados = new Hashtable();

		try {

			//1. En este llamado se crea el proceso de Reparto Notarial que se está
			//iniciando.
			String idReparto = this.crearProcesoRepartoNotarial(circuloNotarial.getCirculoRegistral(), false);
			if(idReparto == null)
			{
				throw new DAOException ("No fue posible crear el proceso de Reparto Notarial," +
				"ninguna minuta fue repartida");
			}
			
			//Se debe traer de donde es el turno, de que circulo notarial y cruzarlo con circuloNotarial si es repartir sino seguir derecho.

			//REALIZAR EL REPARTO ORDINARIO.
			if (idsExtraordinaro != null)
			{
				for (int i = 0; i< idsExtraordinaro.length; i++)
				{
					MinutaEnhancedPk mPk = new MinutaEnhancedPk();
					mPk.idMinuta = idsExtraordinaro[i];
					Minuta minuta = this.getMinutaByWFId(idsExtraordinaro[i],pm);
					CirculoNotarial circNot = minuta.getCirculoNotarial();
					if (circNot != null)
					{
						if (circNot.getIdCirculo().equals(circuloNotarial.getIdCirculo()))
						{
							resultados.putAll(
									this.realizarRepartoExtraordinario(idsExtraordinaro[i], idReparto, usuario)
								);
						}
					}
				}
			}
		} catch (Throwable t)
		{
			if (pm.currentTransaction().isActive())
			{
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException ("No fue posible completar el proceso de Reparto Notarial," +
					"Es posible que no todas las minutas hayan sido repartidas.",t);
		}

		finally {
			pm.close();
		}				

		return resultados;		
	}
	
	
	private Hashtable realizarRepartoOrdinario (CirculoNotarial circuloNotarial, String idReparto, Usuario usuario)
	throws DAOException
	{
		Hashtable tablaResultados = new Hashtable(); 
		CirculoNotarialEnhanced circuloNotarialEnhanced = CirculoNotarialEnhanced.enhance(circuloNotarial);
		List listaMinutasCirculo = this.getMinutasByCirculoNotarial (circuloNotarialEnhanced);
		List listaMinutasOrdinariasCirculo = new ArrayList();
		
		OficinaCategoriaEnhanced notariaClasificacion = null;

		for (Iterator iter = listaMinutasCirculo.iterator(); iter.hasNext();)
		{
			Minuta minuta = (Minuta) iter.next();
			if(minuta.isNormal())
				listaMinutasOrdinariasCirculo.add(minuta);
		}

		listaMinutasCirculo = listaMinutasOrdinariasCirculo;

		//4. Repartir cada una de las minutas de ese circulo notarial.
		for (int j =0; j< listaMinutasCirculo.size(); j++)
		{
			Minuta minuta = (Minuta) listaMinutasCirculo.get(j);
			Categoria catMinuta = minuta.getCategoria();

			try
			{
				//4.1 Obtener la notaria que se encuentre de primera dentro del 
				//circulo notarial y la categoria actual. 
				notariaClasificacion = obtenerPrimeraNotariaCirculoCategoria (circuloNotarialEnhanced, catMinuta);

				if (notariaClasificacion!= null)
				{
					//4.2  Se llama la transacción que debe ser atómica para cada uno de los Repartos. 
					// Esta transacción hace lo siguiente:
					// 4.2.1 Asocia la notaria a la minuta
					// 4.2.2 Asocia la Minuta al Reparto Notarial
					// 4.2.3 Mueve la Notaria del primer puesto al último de la cola.
					// 4.2.4 Avanza el Turno
					MinutaEnhancedPk idMinuta = new MinutaEnhancedPk();
					idMinuta.idMinuta = minuta.getIdMinuta();

					RepartoNotarialEnhancedPk idRepartoNot = new RepartoNotarialEnhancedPk();
					idRepartoNot.idRepartoNotarial = idReparto;

					OficinaCategoriaEnhancedPk idNotaria = new OficinaCategoriaEnhancedPk();
					idNotaria.idCategoria = notariaClasificacion.getIdCategoria();
					idNotaria.idOficinaOrigen = notariaClasificacion.getIdOficinaOrigen();
                                        /*
                                        *  @author Carlos Torres
                                        *  @chage   se agrega validacion de version diferente
                                        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                        */
                                        idNotaria.version = notariaClasificacion.getVersion();

					try
					{
						Minuta resultadoReparto = this.realizarRepartoMinuta (idMinuta, idRepartoNot, idNotaria, usuario);

						//La minuta pudo ser repartida.
						if (resultadoReparto !=null)
						{
							//Insertar idWorkflow y Nombre de la Notaria donde fue repartida.

							//Obtener el id de Workflow 
							String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minuta.getIdMinuta());

							String nombreNotaria = this.getNombreNotariaById (idNotaria.idOficinaOrigen);
							tablaResultados.put(idWorkflowMinuta, nombreNotaria);

						}
						else
						{
							String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minuta.getIdMinuta());
							if (idWorkflowMinuta != null)
							{
								tablaResultados.put(idWorkflowMinuta,"No fue posible realizar el reparto de la Minuta");	
							}

						}
					}
					catch (Throwable t)
					{
						t.printStackTrace();
						String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minuta.getIdMinuta());
						if (idWorkflowMinuta != null)
						{
							tablaResultados.put(idWorkflowMinuta,"No fue posible realizar el reparto de la Minuta");	
						}

					}

				}

				//NO SE ENCONTRO UNA NOTARIA PARA REPARTIR LA MINUTA.
				else
				{

					String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minuta.getIdMinuta());
					if (idWorkflowMinuta != null)
					{
						tablaResultados.put(idWorkflowMinuta,"No se encontró una Notaría para clasificar la Minuta");	
					}
				} 
			}

			catch (Throwable t)
			{
				t.printStackTrace();
				String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minuta.getIdMinuta());
				if (idWorkflowMinuta != null)
				{
					tablaResultados.put(idWorkflowMinuta,"No fue posible realizar el reaparto de la minuta.");	
				}

			}
		}
		return tablaResultados;
	}
	
	private Hashtable realizarRepartoExtraordinario (String idExtraordinario, String idReparto, Usuario usuario)
	throws DAOException
	{
		OficinaCategoriaEnhanced notariaClasificacion=null;
		Hashtable tablaResultados = new Hashtable();

		try
		{
			Minuta minutaExtraordinaria = this.getMinutaByWFId(idExtraordinario);
			CirculoNotarial circuloExtraordinario = minutaExtraordinaria.getCirculoNotarial();
			CirculoNotarialEnhanced circuloNotarialEnhanced = CirculoNotarialEnhanced.enhance(circuloExtraordinario);
			Categoria catMinuta = minutaExtraordinaria.getCategoria();
			notariaClasificacion = obtenerPrimeraNotariaCirculoCategoria (circuloNotarialEnhanced, catMinuta);

			if (notariaClasificacion!= null)
			{

				//4.2  Se llama la transacción que debe ser atómica para cada uno de los Repartos. 
				// Esta transacción hace lo siguiente:
				// 4.2.1 Asocia la notaria a la minuta
				// 4.2.2 Asocia la Minuta al Reparto Notarial
				// 4.2.3 Mueve la Notaria del primer puesto al último de la cola.
				// 4.2.4 Avanza el Turno
				MinutaEnhancedPk idMinuta = new MinutaEnhancedPk();
				idMinuta.idMinuta = minutaExtraordinaria.getIdMinuta();

				RepartoNotarialEnhancedPk idRepartoNot = new RepartoNotarialEnhancedPk();
				idRepartoNot.idRepartoNotarial = idReparto;

				OficinaCategoriaEnhancedPk idNotaria = new OficinaCategoriaEnhancedPk();
				idNotaria.idCategoria = notariaClasificacion.getIdCategoria();
				idNotaria.idOficinaOrigen = notariaClasificacion.getIdOficinaOrigen();
                                 /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                idNotaria.version = notariaClasificacion.getVersion();

				try
				{
					Minuta resultadoReparto = this.realizarRepartoMinuta (idMinuta, idRepartoNot, idNotaria, usuario);
					//La minuta pudo ser repartida.
					if (resultadoReparto !=null)
					{
						//Insertar idWorkflow y Nombre de la Notaria donde fue repartida.

						//Obtener el id de Workflow 
						String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minutaExtraordinaria.getIdMinuta());
						String nombreNotaria = this.getNombreNotariaById (idNotaria.idOficinaOrigen);
						if (idWorkflowMinuta != null && nombreNotaria != null)
						{
							tablaResultados.put(idWorkflowMinuta, nombreNotaria);
						}

					}
					else
					{
						String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minutaExtraordinaria.getIdMinuta());
						if (idWorkflowMinuta != null)
						{
							tablaResultados.put(idWorkflowMinuta,"No fue posible realizar el reparto de la Minuta");	
						}

					}
				}

				catch (Throwable t)
				{
					t.printStackTrace();
					String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minutaExtraordinaria.getIdMinuta());
					if (idWorkflowMinuta != null)
					{
						tablaResultados.put(idWorkflowMinuta,"No fue posible realizar el reparto de la Minuta ");	
					}

				}

			}

			//NO SE ENCONTRO UNA NOTARIA PARA REPARTIR LA MINUTA.
			else
			{
				String idWorkflowMinuta = this.getIdWorkflowByIdSolicitud(minutaExtraordinaria.getIdMinuta());
				if (idWorkflowMinuta != null)
				{
					tablaResultados.put(idWorkflowMinuta,"No se encontró una Notaría para clasificar la Minuta");	
				}

			}
		} catch (Throwable t)
		{
			Log.getInstance().error(JDORepartosDAO.class,t.getMessage());
			throw new DAOException ("No fue posible completar el proceso de Reparto Notarial," +
					"Es posible que no todas las minutas hayan sido repartidas.",t);
		}
		return tablaResultados;
	}
}
							  
