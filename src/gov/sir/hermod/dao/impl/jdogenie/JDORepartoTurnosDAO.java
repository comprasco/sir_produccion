/*
 * Created on 07-feb-2005
 *
 */
package gov.sir.hermod.dao.impl.jdogenie;

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

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.ProcesoReparto;
import gov.sir.core.negocio.modelo.Reparto;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CProcesoReparto;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoRepartoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.RepartoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SubtipoAtencionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioCirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioSubtipoAtencionEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.RepartoTurnosDAO;

import com.versant.core.jdo.VersantQuery;

/**
 * @author dcantor
 *
 */
public class JDORepartoTurnosDAO implements RepartoTurnosDAO {
	
    private JDOVariablesOperativasDAO vopDAO;
    /**
     * 
     */
    public JDORepartoTurnosDAO() {
        vopDAO = new JDOVariablesOperativasDAO();
    }

    /**
      * Retorna una lista de los usuarios que pertenecen a un círculo
      * con sus subtipos de atención asociados. 
      * @param usuarios Lista con objetos de tipo <code>Usuario</code>
      * @param circulo <code>Circulo</code> utilizado como filtro. 
      * @return una lista de los usuarios que pertenecen al <code>Circulo</code> dado, con sus
      * respectivos subtipos de atención asociados. 
      * @see gov.sir.core.negocio.modelo.Circulo
      * @see gov.sir.core.negocio.modelo.Usuario
      * @see gov.sir.core.negocio.modelo.SubtipoAtencion
      * @throws DAOException
      */
    public List getSubTipoAtencionByUsuario(List usuarios, Circulo circulo) throws DAOException {

        List usernames = new ArrayList();

        //Se obtiene un listado con los usernames de todos los usuarios recibidos como
        //dentro de la lista pasada como parámetro. 
        for (int tam = 0; tam < usuarios.size(); tam++) {
            Usuario user = (Usuario)usuarios.get(tam);
            String username = user.getUsername();
            usernames.add(username);

        }

        String circuloId = circulo.getIdCirculo();

        PersistenceManager pm = AdministradorPM.getPM();
        List respList = new ArrayList();

        try {

            for (int i = 0; i < usernames.size(); i++) {
                String loginUsuario = (String)usernames.get(i);
                UsuarioEnhanced us = vopDAO.getUsuarioByLogin(loginUsuario, circuloId, pm);

                if (us != null) {

                    //Hacer transientes los elementos de la lista de SubTipos de Solicitud del usuario.
                    List listaSubtipos = us.getSubtiposAtencions();

                    for (int k = 0; k < us.getSubtiposAtencions().size(); k++) {
                        UsuarioSubtipoAtencionEnhanced ustaE = (UsuarioSubtipoAtencionEnhanced)listaSubtipos.get(k);
                        pm.makeTransient(ustaE.getSubtipoatencion());
                        pm.makeTransient(ustaE);
                    }

                    pm.makeTransient(us);
                    respList.add(us);

                }
            }

        }
        catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        }
        catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
            pm.close();
        }
        respList = TransferUtils.makeTransferAll(respList);
        return respList;
    }

    /**
     * Retorna una lista de los usuarios que pertenecen a un <code>Circulo</code>
     * con sus subtipos de atención asociados. 
     * @param logins Lista con los logins de los usuarios. 
     * @param idCirculo identificador del <code>Circulo</code>, utilizado como filtro. 
     * @return una lista de los usuarios que pertenecen al <code>Circulo</code> con id
     * dado, con sus respectivos subtipos de atención asociados.
     * @see gov.sir.core.negocio.modelo.Circulo
     * @see gov.sir.core.negocio.modelo.Usuario
     * @see gov.sir.core.negocio.modelo.SubtipoAtencion 
     * @throws DAOException
     */
    public List getSubTipoAtencionUsuario(List logins, String circuloId) throws DAOException {

        PersistenceManager pm = AdministradorPM.getPM();
        List respList = new ArrayList();

        try {

            for (int i = 0; i < logins.size(); i++) {
                String loginUsuario = (String)logins.get(i);
                UsuarioEnhanced us = vopDAO.getUsuarioByLogin(loginUsuario, circuloId, pm);

                if (us != null) {

                    //Hacer transientes los elementos de la lista de SubTipos de Solicitud del usuario.
                    List listaSubtipos = us.getSubtiposAtencions();

                    for (int k = 0; k < us.getSubtiposAtencions().size(); k++) {
                        UsuarioSubtipoAtencionEnhanced ustaE = (UsuarioSubtipoAtencionEnhanced)listaSubtipos.get(k);
                        pm.makeTransient(ustaE.getSubtipoatencion());
                        pm.makeTransient(ustaE);
                    }

                    pm.makeTransient(us);
                    respList.add(us);

                }
            }

        }
        catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            throw e;
        }
        catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
            pm.close();
        }
        respList = TransferUtils.makeTransferAll(respList);
        return respList;
    }
    
    
    
	/**
	 * Retorno un mapa en donde las llaves son los subtipos de atención y los valores
	 * corresponden a la lista de los usuarios del círculo especificado rotados
	 * @param circulo Identificador del círculo
	 * @return Mapa [SubtipoAtencion, Lista usuarios en orden]
	 * @throws DAOException
	 */
	public Map getUsuariosPorSubtiposDeAtencionRotados(Circulo circulo) throws DAOException {
		Map mapa = new HashMap();
		PersistenceManager pm = AdministradorPM.getPM();
		String idCir = circulo.getIdCirculo();
		try {
			pm.currentTransaction().begin();
			
			Query query = pm.newQuery(UsuarioSubtipoAtencionEnhanced.class);
			query.declareVariables("UsuarioCirculoEnhanced usCir");
			query.setOrdering("idSubtipoAtencion ascending, orden ascending");
			query.declareParameters("String idCir");
			query.setFilter("this.usuario.usuarioCirculos.contains(usCir)\n"+
					"&& usCir.idCirculo==idCir");
			Collection col = (Collection)query.execute(idCir);
			
			Iterator iter = col.iterator();
			
			UsuarioSubtipoAtencionEnhanced primeraPosicion;
			UsuarioSubtipoAtencionEnhanced usuarioSubtipoAnterior;
			UsuarioSubtipoAtencionEnhanced usuarioSubtipo;
			
			//Se recorrerá la lista de resultados actualizando el orden (rotando) y
			//agrupando los usuarios en un mapa donde la llave es el objeto subtipo
			//de atención y el valor es la lista ordenada y actualizada de usuarios
			long orden;
			if(iter.hasNext()){
				//Se realiza el primer paso afuera del ciclo con el fin de agrupar
				//los usuarios por subtipo de atención. Se guarda la primera posición
				//con el fin de actualizarla y añadirla de últimas puesto que su nuevo
				//orden depende de la última posición
				usuarioSubtipoAnterior = (UsuarioSubtipoAtencionEnhanced)iter.next();
				primeraPosicion = usuarioSubtipoAnterior;
				List usuarios = new ArrayList();
				while(iter.hasNext()){
					usuarioSubtipo = (UsuarioSubtipoAtencionEnhanced)iter.next();
					if(usuarioSubtipo.getSubtipoatencion().equals(usuarioSubtipoAnterior.getSubtipoatencion())){
						usuarioSubtipo.setOrden(usuarioSubtipo.getOrden()-1);
						//Se crea el usuario transfer a partir del actual y se agrega a a la lista
						usuarios.add(new Usuario(usuarioSubtipo.getUsuario().getIdUsuario(), usuarioSubtipo.getUsuario().getNombre(), 
												usuarioSubtipo.getUsuario().getPassword(), usuarioSubtipo.getUsuario().getUsername(), 
												usuarioSubtipo.getUsuario().getLoginID(), usuarioSubtipo.getUsuario().getApellido1(), 
												usuarioSubtipo.getUsuario().getApellido2(), usuarioSubtipo.getUsuario().isActivo()));
						usuarioSubtipoAnterior = usuarioSubtipo;
					}
					else{
						//Se actualiza la primera posición con el último orden encontrado y se añade
						//de último a la lista
						primeraPosicion.setOrden(usuarioSubtipoAnterior.getOrden()+1);
						usuarios.add(new Usuario(primeraPosicion.getUsuario().getIdUsuario(), primeraPosicion.getUsuario().getNombre(), 
											primeraPosicion.getUsuario().getPassword(), primeraPosicion.getUsuario().getUsername(), 
											primeraPosicion.getUsuario().getLoginID(), primeraPosicion.getUsuario().getApellido1(), 
											primeraPosicion.getUsuario().getApellido2(), primeraPosicion.getUsuario().isActivo()));
						
						//Se actualiza el mapa con la llave del subtipo de atención transfer y la lista de usuarios
						//trasfer como valor
						mapa.put(new SubtipoAtencion(primeraPosicion.getSubtipoatencion().getIdSubtipoAtencion(), 
											primeraPosicion.getSubtipoatencion().getMinAnotacione(), 
											primeraPosicion.getSubtipoatencion().getNombre(), primeraPosicion.getSubtipoatencion().getUnidadesTrabajo()), 
											usuarios);
						
						//Se inicializa para el siguiente grupo
						primeraPosicion = usuarioSubtipo;
						usuarioSubtipoAnterior = usuarioSubtipo;
						usuarios = new ArrayList();
					}
				}
				//Se realiza el último paso afuera del ciclo para que se termine de asociar el último
				primeraPosicion.setOrden(usuarioSubtipoAnterior.getOrden()+1);
				usuarios.add(new Usuario(primeraPosicion.getUsuario().getIdUsuario(), primeraPosicion.getUsuario().getNombre(), 
									primeraPosicion.getUsuario().getPassword(), primeraPosicion.getUsuario().getUsername(), 
									primeraPosicion.getUsuario().getLoginID(), primeraPosicion.getUsuario().getApellido1(), 
									primeraPosicion.getUsuario().getApellido2(), primeraPosicion.getUsuario().isActivo()));
				mapa.put(new SubtipoAtencion(primeraPosicion.getSubtipoatencion().getIdSubtipoAtencion(), 
									primeraPosicion.getSubtipoatencion().getMinAnotacione(), 
									primeraPosicion.getSubtipoatencion().getNombre(), primeraPosicion.getSubtipoatencion().getUnidadesTrabajo()), 
									usuarios);
				
			}
			pm.currentTransaction().commit();
			

		}
		catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}
		
		return mapa;
	}


  

    /**
    	* Adiciona un <code>ProcesoReparto</code> a la configuración del sistema.
    	* <p>
    	* Se genera una excepción en el caso en el que el <code>ProcesoReparto</code>
    	* recibido como parámetro sea <code>null</code> o si el <code>Circulo</code> asociado
    	* con el <code>Proceso</code> no exista en la Base de Datos.   
    	* @param datos El objeto de tipo <code>ProcesoReparto</code> con sus atributos que va 
    	* a ser agregado a la configuración del sistema. 
    	* @return identificador del  <code>ProcesoReparto</code> generado. 
    	* @see gov.sir.core.negocio.modelo.ProcesoReparto
    	* @throws DAOException
    	*/
    public ProcesoReparto addProcesoReparto(ProcesoReparto datos) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        JDOTurnosDAO turnosDAO = new JDOTurnosDAO();

        if (datos == null) {
            throw new DAOException("El proceso reparto que se intenta insertar es null:");
        }
        ProcesoRepartoEnhanced proceso = ProcesoRepartoEnhanced.enhance(datos);
        ProcesoRepartoEnhanced procesoPersistente = null;

        try {
            //pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //Se obtiene un secuencial para el proceso de reparto. 
            JDOVariablesOperativasDAO vopDAO = new JDOVariablesOperativasDAO();
            proceso.setIdProcesoReparto(String.valueOf(vopDAO.getSecuencial(CProcesoReparto.NOMBRE_TABLA, pm)));

            //Se valida que el Círculo asociado con el proceso exista en la Base de Datos. 
            CirculoEnhancedPk crId = new CirculoEnhancedPk();
            crId.idCirculo = proceso.getCirculo().getIdCirculo();
            CirculoEnhanced cEnh = vopDAO.getCirculoById(crId, pm);

            //Si el círculo no existe en la Base de Datos se genera una excepción. 
            if (cEnh == null) {
                throw new DAOException("No encontró el circulo con el ID: " + crId.idCirculo);
            }

            List reps = proceso.getRepartos();
            Iterator it = reps.iterator();
            while (it.hasNext()) {
                RepartoEnhanced reparto = (RepartoEnhanced)it.next();
                TurnoEnhanced t = reparto.getTurno();
                if (t != null) {
                    TurnoEnhancedPk tId = new TurnoEnhancedPk();
                    tId.anio = t.getAnio();
                    tId.idCirculo = t.getIdCirculo();
                    tId.idProceso = t.getIdProceso();
                    tId.idTurno = t.getIdTurno();
                    TurnoEnhanced tr = turnosDAO.getTurnoByID(tId, pm);
                    reparto.setTurno(tr);
                }
                UsuarioEnhanced u = reparto.getUsuario();
                if (u != null) {
                    UsuarioEnhanced ur = vopDAO.getUsuarioByLogin(u.getUsername(), pm);
                    reparto.setUsuario(ur);
                }
                reparto.setProcesoReparto(proceso);
            }

            proceso.setCirculo(cEnh);
            Date fecha = new Date();
            proceso.setFechaReparto(fecha);

            pm.makePersistent(proceso);
            pm.currentTransaction().commit();

            ProcesoRepartoEnhancedPk idProceso = new ProcesoRepartoEnhancedPk();
            idProceso.idProcesoReparto = proceso.getIdProcesoReparto();
            procesoPersistente = new ProcesoRepartoEnhanced();
            procesoPersistente = this.getProcesoRepartoById(idProceso, pm);

        }
        catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
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

        return (ProcesoReparto)procesoPersistente.toTransferObject();
    }

    /**
    * Obtiene un <code>ProcesoRepartoEnhanced</code> dado su identificador.
    * <p> Método utilizado para transacciones.
    * <p> El método retorna <code>null</code> si no se encuentra el <code>ProcesoReparto</code>
    * con el identificador dado.
    * @param prID identificador del <code>ProcesoReparto</code>
    * @param pm PersistenceManager de la transaccion
    * @return <code>ProcesoRepartoEnhanced</code> con todos sus atributos. 
    * @see gov.sir.core.negocio.modelo.ProcesoRepartoEnhanced
    * @throws DAOException
    */
    protected ProcesoRepartoEnhanced getProcesoRepartoById(ProcesoRepartoEnhancedPk prID, PersistenceManager pm) throws DAOException {
        ProcesoRepartoEnhanced rta = null;

        if (String.valueOf(prID.idProcesoReparto) != null) {
            try {
                rta = (ProcesoRepartoEnhanced)pm.getObjectById(prID, true);
                pm.makeTransient(rta.getCirculo());
                List listaRepartos = rta.getRepartos();

                for (int i = 0; i < listaRepartos.size(); i++) {
                    RepartoEnhanced repEnh = (RepartoEnhanced)listaRepartos.get(i);
                    pm.makeTransient(repEnh);
                }
                pm.makeTransient(rta);
            }
            catch (JDOObjectNotFoundException e) {
                rta = null;
            }
            catch (JDOException e) {
                Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }

	/**
	* Obtiene el último <code>Reparto</code> asociado con un <code>Turno</code>. 
	* @return <code>Reparto</code> con sus atributos.
	* @param turnoId Identificador del <code>Turno</code> del que se busca el <code>Reparto</code>
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Reparto
	* @see gov.sir.core.negocio.modelo.Turno
	*/
	public Reparto getLastReparto(TurnoPk turnoId) throws DAOException {
  	
	  RepartoEnhanced reparto = null;
	  Reparto rta = null;
	  PersistenceManager pm = AdministradorPM.getPM();
	
	  try {
		  reparto = this.getLastReparto(new TurnoEnhancedPk(turnoId), pm);
		  if(reparto!=null){
			pm.makeTransient(reparto.getProcesoReparto());
			pm.makeTransient(reparto.getUsuario());
			pm.makeTransient(reparto.getTurno());
			pm.makeTransient(reparto);
		  }

	  } catch (DAOException e) {
		  if (pm.currentTransaction().isActive()) {
			  pm.currentTransaction().rollback();
		  }

		  Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
		  throw new DAOException(e.getMessage(), e);
	  } catch (Throwable e) {
		  if (pm.currentTransaction().isActive()) {
			  pm.currentTransaction().rollback();
		  }

		  Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
		  throw new DAOException(e.getMessage(), e);
	  } finally {
		  pm.close();
	  }
	
	  if(reparto!=null){
	  	 rta = (Reparto) reparto.toTransferObject();
	  }
	  
	  return rta;

	}

	/**
	* Obtiene el último <code>Reparto</code> asociado con un <code>Turno</code>. 
	* <p>
	* Método utilizado en transacciones. 
	* @return <code>Reparto</code> con sus atributos.
	* @param turnoId Identificador del <code>Turno</code> del que se busca el <code>Reparto</code>
	* @param pm Persistence Manager de la transacción. 
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Reparto
	* @see gov.sir.core.negocio.modelo.Turno
	*/
	protected RepartoEnhanced getLastReparto(TurnoEnhancedPk turnoID,
		PersistenceManager pm) throws DAOException {
		RepartoEnhanced rta = null;
		JDOTurnosDAO turnosDAO = new JDOTurnosDAO();

		if (turnoID.anio != null && turnoID.idCirculo != null && turnoID.idTurno != null ) {
			try {
			  TurnoEnhanced turno;
			  turno = turnosDAO.getTurnoByID(turnoID, pm);
			  VersantQuery query;
			  
			  
			  query = (VersantQuery)pm.newQuery(RepartoEnhanced.class);
			  query.setFilter("anio == '"+turno.getAnio()+"'"+
							  " && idCirculo =='"+turno.getIdCirculo()+"'"+
							  " && idProceso =="+turno.getIdProceso()+ 
							  " && idTurno =='"+turno.getIdTurno()+"'");
			  //query.setResult("max(idProcesoReparto)");
			  
			  boolean enabled = false; 
			  
			  Collection col5 = (Collection)query.execute();
			  Iterator it = col5.iterator();
			  if( it.hasNext() ) {
				  enabled = true;
			  }			  
			  
			  query.closeAll();
			  
			  if( enabled ) {
			  
				  query = (VersantQuery)pm.newQuery(RepartoEnhanced.class);
				  query.setFilter("anio == '"+turno.getAnio()+"'"+
								  " && idCirculo =='"+turno.getIdCirculo()+"'"+
								  " && idProceso =="+turno.getIdProceso()+ 
								  " && idTurno =='"+turno.getIdTurno()+"'");
				  query.setResult("max(idProcesoReparto)");
				  String procesoRep= (String) query.execute();
				  query.closeAll();
	
				  VersantQuery query2 = (VersantQuery)pm.newQuery(RepartoEnhanced.class);
				  query2.setFilter("anio == '"+turno.getAnio()+"'"+
								  " && idCirculo == '"+turno.getIdCirculo()+"'"+
								  " && idProceso =="+turno.getIdProceso()+ 
								  " && idTurno == '"+turno.getIdTurno()+"'"+
								  " && idProcesoReparto == '"+procesoRep+"'");
				  Collection col2 = (Collection)query2.execute();
				  for (Iterator iter2 = col2.iterator(); iter2.hasNext();) {
					  rta = (RepartoEnhanced)iter2.next();
				  }
				  query2.closeAll();
			  
			  } // if
			
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}




	 /** 
	   * Obtiene un mapa, cuyas llaves son objetos de tipo
	   * <code>SubtipoAtencion</code> y sus valores son objetos de tipo
	   * <code>Usuario</code> asociados.  
	   * @return Mapa con la asociación <code>SubtipoAtencion</code>, <code>Usuario</code>.
	   * @param Lista de los Usuarios de los cuales se desean obtener sus subtipos de atención
	   * asociados. 
	   * @throws DAOException
	   * @see gov.sir.core.negocio.modelo.SubtipoAtencion
	   * @see gov.sir.core.negocio.modelo.Usuario
	   * 
	   */
	  public Map getUsuariosBySubtipoAtencion(List usuarios) throws DAOException {
		Map mapa = new HashMap();
		Map mapaRta = new HashMap();
		JDOVariablesOperativasDAO variablesOperativasDAO = new JDOVariablesOperativasDAO();
		PersistenceManager pm = AdministradorPM.getPM();
	  
		try{
		  VersantQuery query = (VersantQuery)pm.newQuery(SubtipoAtencionEnhanced.class);
		  Collection col = (Collection)query.execute();
		  Iterator iter = col.iterator();
		
		  while(iter.hasNext()) {
			  SubtipoAtencionEnhanced subTipo = (SubtipoAtencionEnhanced) iter.next();
			  Iterator it = usuarios.iterator();
			  List temp = new ArrayList();		
			  while(it.hasNext()){
				 UsuarioEnhanced usuario = UsuarioEnhanced.enhance((Usuario) it.next());
				 String username = usuario.getUsername();
				 usuario = vopDAO.getUsuarioByLogin(username, pm);
				 pm.makeTransient(usuario);
				 boolean rta = variablesOperativasDAO.isUsuarioInSubtipoAtencion(subTipo.getIdSubtipoAtencion(), usuario.getIdUsuario(), pm);
				 if (rta){
					temp.add(usuario);
				 } 			   	
			  }
			  pm.makeTransient(subTipo);
			  mapa.put(subTipo, temp);		
		  }
		  query.closeAll();

		} catch (DAOException e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
            pm.close();
			Collection collMap = mapa.keySet();
			Iterator itMap = collMap.iterator();
		  
		  
			//Se recorre cada una de las entradas del mapa para hacer 
			//transfer sobre cada uno de las asociaciones que se encuentran en el mismo. 
			while (itMap.hasNext()){
			  SubtipoAtencionEnhanced keyTipo = (SubtipoAtencionEnhanced) itMap.next();
			  SubtipoAtencion keySub = (SubtipoAtencion) keyTipo.toTransferObject();
			  List temp2 = (List) mapa.get(keyTipo);
			  temp2 = TransferUtils.makeTransferAll(temp2);
			  mapaRta.put(keySub, temp2);
			}
		}
		return mapaRta;
	  }
	  
	  
	/**
	* Actualiza el atributo orden para todas las relaciones Usuario - SubTipo de Atención
	* definidas dentro del Circulo recibido como parámetro.
	* <p>El usuario que tenía el orden 1 pasa al final de la lista, y en los demás casos, el orden
	* se reduce en una unidad.  
	* @throws <code>DAOException</code>
	* @param circulo El <code>Circulo </code> en el que se debe actualizar el orden  para las
	* relaciones Usuario - SubtipoAtencion.
	* @return <code>true</code> o <code>false</code> dependiendo del resultado del proceso. 
	* @see gov.sir.core.negocio.modelo.Usuario
	* @see gov.sir.core.negocio.modelo.SubtipoAtencion
	*/
	public boolean actualizarRotacionReparto (Circulo circulo) throws DAOException
	{
		
		PersistenceManager pm = AdministradorPM.getPM();
		List usuariosCirculo = new ArrayList();
		List subtiposAtencion = new ArrayList();
		List usuSubtiposAtencion = new ArrayList();
	  
		try
		{
			
			
			//pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
            //1. Se obtiene la lista de usuarios que pertenecen al Círculo. 
			VersantQuery queryUsuarios = (VersantQuery)pm.newQuery(UsuarioCirculoEnhanced.class);
			String circuloId = circulo.getIdCirculo();
			queryUsuarios.setFilter("idCirculo == '"+circuloId+"'");
			Collection colUsuarios = (Collection)queryUsuarios.execute();
			Iterator iterUsuarios = colUsuarios.iterator();
			
			while(iterUsuarios.hasNext()) 
			{
			    UsuarioCirculoEnhanced usuCirculo = (UsuarioCirculoEnhanced) iterUsuarios.next();
			    usuariosCirculo.add(usuCirculo);
			}
			
			
			//2. Se obtiene la lista de SUBTIPOS de atencion.
			VersantQuery querySubtipos = (VersantQuery)pm.newQuery(SubtipoAtencionEnhanced.class);
			Collection colSubtipos = (Collection)querySubtipos.execute();
			Iterator iterSubtipos  = colSubtipos.iterator();
			
			while(iterSubtipos.hasNext()) 
			{
				SubtipoAtencionEnhanced subtipoAtencion = (SubtipoAtencionEnhanced) iterSubtipos.next();
				subtiposAtencion.add(subtipoAtencion);
			}
			
			
			//3. Se recorre cada uno de los subtipos y se realiza la actualización del orden. 
			for (int i=0; i< subtiposAtencion.size(); i++)
			{
                 //3.1 Se obtiene una lista con los usuarios que pertenecen al círculo dado y a cada uno de los
				 //subtipos de atención (Se va recorriendo subtipo por subtipo).
				 SubtipoAtencionEnhanced auxSubtipo = (SubtipoAtencionEnhanced) subtiposAtencion.get(i);
				 String subtipoId = auxSubtipo.getIdSubtipoAtencion();
				 Log.getInstance().debug(JDORepartoTurnosDAO.class,"Subtipo "+ auxSubtipo.getNombre());
				 
				     //3.1.1 Construir un filtro para realizar la busqueda de los registros que  asocien
				     //los usuarios del círculo y del subtipo. 
				     StringBuffer filtroUsuarios = new StringBuffer();
					 filtroUsuarios.append("(0 != 0");
					  
					 // Se construye el filtro para las busquedas.
					 for (int k=0; k<usuariosCirculo.size(); k++)
					 {
						UsuarioCirculoEnhanced usuAux = (UsuarioCirculoEnhanced) usuariosCirculo.get(k);
					  	filtroUsuarios.append("|| this.idUsuario == \"" + usuAux.getIdUsuario() + "\"");
						
					}
					filtroUsuarios.append(") && this.idSubtipoAtencion == \""+auxSubtipo.getIdSubtipoAtencion()+"\"");
					
					
					//3.1.2 Realizar la búsqueda obteniendo una lista con objetos UsuarioSubtipoAtencion cuyo 
					//usuario esté en el círculo  y su subtipo sea el subtipo que se está recorriendo en el ciclo. 
				    VersantQuery queryUsuSub = (VersantQuery)pm.newQuery(UsuarioSubtipoAtencionEnhanced.class);
				    queryUsuSub.setFilter(filtroUsuarios.toString());
				    queryUsuSub.setOrdering("orden ascending");
				    Collection colUsuSub = (Collection)queryUsuSub.execute();
					Iterator iterUsuSub  = colUsuSub.iterator();
					usuSubtiposAtencion = new ArrayList();
					
				    while(iterUsuSub.hasNext()) 
				    {
					    UsuarioSubtipoAtencionEnhanced usuSubtipoAtencion = (UsuarioSubtipoAtencionEnhanced) iterUsuSub.next();
					    usuSubtiposAtencion.add(usuSubtipoAtencion);
				    }
				 
				 //3.2 Se realiza la rotación.
				 for (int w =0; w< usuSubtiposAtencion.size(); w++)
				 {
				 	//3.2.1 El primer elemento se pasa al último lugar de la cola.
				 	//PRECONDICION: El primer orden es 1 
				 	//PRECONDICION: El ultimo orden es size
				 	if (w ==0)
				 	{
						UsuarioSubtipoAtencionEnhanced usuInicial = (UsuarioSubtipoAtencionEnhanced)usuSubtiposAtencion.get(w);
						usuInicial.setOrden(usuSubtiposAtencion.size());
				 	}
				 	else
				 	{
						UsuarioSubtipoAtencionEnhanced usuIntermedio = (UsuarioSubtipoAtencionEnhanced)usuSubtiposAtencion.get(w);
						usuIntermedio.setOrden(usuIntermedio.getOrden()-1);
				 	}
				 	
				 }
				 
				 
			}
			pm.currentTransaction().commit();
				
		}
		 catch (Throwable e) {
		if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().rollback();
		}
		Log.getInstance().error(JDORepartoTurnosDAO.class,e.getMessage());
		throw new DAOException(e.getMessage(), e);
	} finally {
		pm.close();
		
		int size = usuariosCirculo.size();
		Log.getInstance().debug(JDORepartoTurnosDAO.class,"Tamaño lista de usuarios "+size);
		
		int size2 = subtiposAtencion.size();
		Log.getInstance().debug(JDORepartoTurnosDAO.class,"Tamaño lista de subtipos "+size2);
		
		
		
		}	
			
			
			
			
		return false;
	}

}