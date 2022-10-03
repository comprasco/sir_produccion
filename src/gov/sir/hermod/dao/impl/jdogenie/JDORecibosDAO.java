/*
 * Clase para el manejo de los recibos que son impresos para
 * registrar los pagos realizados por los usuarios del sistema.
 */
package gov.sir.hermod.dao.impl.jdogenie;


import com.versant.core.jdo.VersantPersistenceManager;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CJerarquia;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CEstacionRecibo;

import gov.sir.core.negocio.modelo.jdogenie.AumentoReciboEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboAuditoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.hermod.HermodProperties;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.RecibosDAO;
import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.EstacionDAO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Jerarquia;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.util.ExceptionPrinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;


/**
 * Clase para el manejo de los recibos que son impresos para
 * registrar los pagos realizados por los usuarios del sistema.
 * @author  fceballos, dlopez
 */
public class JDORecibosDAO implements RecibosDAO {
   
    /**
    * Crea una nueva instacia de JDORecibosDAO
    */
    public JDORecibosDAO() {
    }

    /**
     * Obtiene un objeto <code>EstacionRecibo</code> estación recibo dado su
     * identificador.
     * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
     * recuperada.
     * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
     * como parámetro.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EstacionReciboEnhanced aux = null;
        EstacionRecibo rta = null;

        try {
            aux = this.getEstacionReciboEnhanced(new EstacionReciboEnhancedPk(
                        oid), pm);
            pm.makeTransient(aux);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (aux != null) {
            rta = (EstacionRecibo) aux.toTransferObject();
        }

        return rta;
    }
    
    /**
     * Obtiene un objeto <code>EstacionRecibo</code> estación recibo dado su
     * identificador.
     * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
     * recuperada.
     * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
     * como parámetro.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public EstacionRecibo getEstacionRecibo(EstacionReciboPk oid, long idProceso)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EstacionReciboEnhanced aux = null;
        EstacionRecibo rta = null;

        try {
            aux = this.getEstacionReciboEnhanced(new EstacionReciboEnhancedPk(oid), idProceso, pm);
            pm.makeTransient(aux);
        } catch (DAOException e) {
            throw e;
        } finally {
            pm.close();
        }

        if (aux != null) {
            rta = (EstacionRecibo) aux.toTransferObject();
        }

        return rta;
    }

    /**
     * Obtiene un objeto <code>EstacionRecibo</code> estación recibo dado su
     * identificador.
     * <p>
     * Método utilizado en transacciones.
     * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
     * recuperada.
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
     * como parámetro.
     * @throws DAOException
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
                Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }

        return rta;
    }
    
    /**
     * Obtiene un objeto <code>EstacionRecibo</code> estación recibo dado su
     * identificador.
     * <p>
     * Método utilizado en transacciones.
     * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
     * recuperada.
     * @param pm <code>PersistenceManager</code> de la transacción.
     * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
     * como parámetro.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    protected EstacionReciboEnhanced getEstacionReciboEnhanced(
        EstacionReciboEnhancedPk oid, long idProceso, PersistenceManager pm)
        throws DAOException {
        EstacionReciboEnhanced rta = null;
        EstacionReciboEnhanced rtadefault = null;

        long idProcesodefault = Long.valueOf(CEstacionRecibo.PROCESO_DEFAULT).longValue();
        
        if (oid.idEstacion != null) {
            try {
            	
            	Query query = pm.newQuery(EstacionReciboEnhanced.class);
            	query.declareParameters("String idEstacion");
            	query.setFilter("this.idEstacion == idEstacion");
            	
               // rta = (EstacionReciboEnhanced) pm.getObjectById(oid, true);
            	Collection col = (Collection)query.execute(oid.idEstacion);
            	
            	boolean entroEstacionReciboProceso = false;
            	
            	//Buscar la estacion recibo de la estacion 
            	for (Iterator iter = col.iterator(); iter.hasNext();) {
            		EstacionReciboEnhanced estacionreciboenhanced = (EstacionReciboEnhanced)iter.next();
            		if (estacionreciboenhanced.getNumeroProceso() == idProceso) {
            			rta = estacionreciboenhanced;
            			entroEstacionReciboProceso = true;
            		}
            		
            		if (estacionreciboenhanced.getNumeroProceso() == idProcesodefault) {
            			rtadefault = estacionreciboenhanced;
            		}
            	}
            	
            	// Si no encontro la del proceso, tiene que buscar la estación por default
            	if (!entroEstacionReciboProceso) {
            		if (rtadefault != null) {
            			rta = rtadefault;
            		} 
            	}
            	
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
     * Obtiene una lista de los objetos <code>EstacionRecibo</code>
     * existentens en el sistema.
     * @return una lista de objetos <code>EstacionRecibo</code> existentes en el sistema.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public List getEstacionesRecibo() throws DAOException {
        List lista = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();

        try {
            pm.currentTransaction().begin();

            Query q = pm.newQuery(EstacionReciboEnhanced.class);
            q.setOrdering("numeroInicial ascending");

            Collection results = (Collection) q.execute();
            Iterator it = results.iterator();

            while (it.hasNext()) {
                EstacionReciboEnhanced obj = (EstacionReciboEnhanced) it.next();
                pm.makeTransient(obj);
                lista.add(obj);
            }

            pm.currentTransaction().commit();
            q.close(results);
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        lista = TransferUtils.makeTransferAll(lista);

        return lista;
    }

    /**
     * @see RecibosDAO#setEstacionRecibo(Circulo.CirculoPk, EstacionRecibo)
     */
    public void setEstacionRecibo(CirculoPk circuloID, EstacionRecibo eRecibo, Usuario user) 
    	throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        
        EstacionReciboEnhancedPk eid = new EstacionReciboEnhancedPk();
        eid.idEstacion = eRecibo.getIdEstacion();
        eid.numeroProceso = eRecibo.getNumeroProceso();
        

        try {
        	//VALIDACION DE CONSISTENCIA DE LA SECUENCIA
            if (eRecibo.getNumeroFinal() < eRecibo.getNumeroInicial()) {
                throw new DAOException(
                    "El número final no puede ser menor al número inicial");
            }

			//VALIDACION DE CRUCE DE SECUENCIAS
			this.validarNoCruceDeSecuenciasPorCirculo(eRecibo,
				new CirculoEnhancedPk(circuloID), pm);
			
			boolean traslapo = this.validarNoCruceDeSecuenciasPorEstacion(eRecibo,new CirculoEnhancedPk(circuloID), eid, pm);

			//INICIAR TRANSACCION YA SEA PARA CREAR O ACTUALIZAR LA ESTACION_RECIBO            
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
            //BUSCAR OBJETO ESTACION_RECIBO
            EstacionReciboEnhanced vcir = this.getEstacionReciboEnhanced(eid, eRecibo.getNumeroProceso(), pm);
            
            if (vcir != null) {
            	//Se valida que el valor a asignar de la secuencia no sea menor al valor actual
            	//La validación solo se realiza si no se hace traslado sino por asignación de recibos
            	if(eRecibo.getNumeroInicial() != 0 && eRecibo.getNumeroFinal() != 0){
	            	long valorActual = eRecibo.getUltimoNumero();
	            	
	            	if (valorActual < vcir.getUltimoNumero())
	            	{
	            		throw new JDOException("No es posible asignar un número de último recibo generado " +
	            				               "inferior al valor actual");
	            	}
            	}
            	
            	//se valida que los limites sean los mismo
            	if (vcir.getNumeroInicial() == eRecibo.getNumeroInicial() && vcir.getNumeroFinal() == eRecibo.getNumeroFinal()) {
            		if (vcir.getUltimoNumero() > eRecibo.getUltimoNumero()) {
                		//this.addAuditoriaEstacionRecibo(eRecibo,vcir,user,pm);
            			throw new DAOException("No se puede devolver el consecutivo de Recibo.");
                	}	
            	} else {
            		if (vcir.getUltimoNumero() > eRecibo.getUltimoNumero()) {
                		this.addAuditoriaEstacionRecibo(eRecibo,vcir,user,pm);
            		}
            	}
            	
            }

            if (vcir != null && vcir.getNumeroProceso() == eRecibo.getNumeroProceso()) {//SI EXISTE
            	vcir.setNumeroInicial(eRecibo.getNumeroInicial());
            	vcir.setNumeroFinal(eRecibo.getNumeroFinal());
            	if(eRecibo.getUltimoNumero()!=CEstacionRecibo.NUMERO_INVALIDO_RECIBO){
            		vcir.setUltimoNumero(eRecibo.getUltimoNumero());
            	}else{
            		vcir.setUltimoNumero(eRecibo.getNumeroInicial() - 1);
            	}
            } else {//NO EXISTE
            	//Se valida que no sea igual el rango
            	if (traslapo) {
            		throw new JDOException("La estación " +
							eRecibo.getIdEstacion() +
							" tiene una secuencia de recibos que se traslapa con la secuencia insertada");
	
            	}
            	eRecibo.setUltimoNumero(eRecibo.getNumeroInicial() - 1);
				vcir = EstacionReciboEnhanced.enhance(eRecibo);
				pm.makePersistent(vcir);
            }

            pm.currentTransaction().commit();

        } catch (JDOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
          finally {
        	if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            pm.close();
        }
    }

    /**
     * @param eRecibo
     * @param cirID
     * @param pm
     */
    protected void validarNoCruceDeSecuenciasPorCirculo(EstacionRecibo eRecibo,
        CirculoEnhancedPk cirID, PersistenceManager pm) throws DAOException {
        try {
            Estacion estacion;
            String idEst;
            Long reciboInicial = new Long(eRecibo.getNumeroInicial());
            Long reciboFinal = new Long(eRecibo.getNumeroFinal());
            Query query = null;
            boolean found = false;
            EstacionReciboEnhanced esRecibo = null;

            JDOSolicitudesDAO jdoSol = new JDOSolicitudesDAO();
            CirculoEnhanced cir = jdoSol.getCirculoByID(cirID, pm);

            if (cir == null) {
                throw new DAOException("El círculo especificado NO existe");
            }

            List estacionesDelCirculo = this.consultarEstacionesPorCirculo(cir);

            for (Iterator iter = estacionesDelCirculo.iterator();
                    iter.hasNext() && !found;) {
                estacion = (Estacion) iter.next();
                idEst = estacion.getEstacionId();

                if (!idEst.equals(eRecibo.getIdEstacion())) {
                    query = pm.newQuery(EstacionReciboEnhanced.class);
                    query.declareParameters(
                        "String idEst, long reciboInicial, long reciboFinal");
                    query.setFilter("this.idEstacion==idEst &&\n" +
                        "(((this.numeroInicial<=reciboInicial &&\n" +
                        "reciboInicial<=this.numeroFinal)||\n" +
                        "(this.numeroInicial<=reciboFinal && \n" +
                        "reciboFinal<=this.numeroFinal) ||\n" +
                        "(this.numeroInicial<=reciboInicial && \n" +
                        "reciboInicial<=this.numeroFinal) ||\n" +
                        "(this.numeroInicial>=reciboInicial &&\n" +
                        "this.numeroFinal<=reciboFinal)))");

                    List col = (List) query.execute(idEst, reciboInicial,
                            reciboFinal);

                    if (col.size() > 0) {
                        found = true;
                        esRecibo = (EstacionReciboEnhanced) col.get(0);
                    }
                }
            }

            if (query != null) {
                query.closeAll();
            }

            if (found) {
            	
            	//Validacion requerida cuando se quiere hacer reset sobre secuenciales.
            	//Si los numeros inicial y final son ambos ceros no se debe generar excepción.
            	long first = eRecibo.getNumeroInicial();
            	long last  = eRecibo.getNumeroFinal();
            	if (first ==0 && last ==0)
            	{
            	}
            	
            	else
            	{
					throw new DAOException("La estación " +
										esRecibo.getIdEstacion() +
										" tiene una secuencia de recibos que se traslapa con la secuencia insertada");
            	}
                
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    /**
     * @param eRecibo
     * @param cirID
     * @param pm
     */
    protected boolean validarNoCruceDeSecuenciasPorEstacion(EstacionRecibo eRecibo,
        CirculoEnhancedPk cirID, EstacionReciboEnhancedPk estacionID, PersistenceManager pm) throws DAOException {
    	boolean respuesta = false;
        try {
            String idEst;
            Long reciboInicial = new Long(eRecibo.getNumeroInicial());
            Long reciboFinal = new Long(eRecibo.getNumeroFinal());
            Query query = null;
            boolean found = false;
            EstacionReciboEnhanced esRecibo = null;

            JDOSolicitudesDAO jdoSol = new JDOSolicitudesDAO();
            CirculoEnhanced cir = jdoSol.getCirculoByID(cirID, pm);

            if (cir == null) {
                throw new DAOException("El círculo especificado NO existe");
            }

            idEst = estacionID.idEstacion;
            query = pm.newQuery(EstacionReciboEnhanced.class);
            query.declareParameters(
            	"String idEst, long reciboInicial, long reciboFinal");
            	query.setFilter("this.idEstacion==idEst &&\n" +
                "(((this.numeroInicial<=reciboInicial &&\n" +
                "reciboInicial<=this.numeroFinal)||\n" +
                "(this.numeroInicial<=reciboFinal && \n" +
                "reciboFinal<=this.numeroFinal) ||\n" +
                "(this.numeroInicial<=reciboInicial && \n" +
                "reciboInicial<=this.numeroFinal) ||\n" +
                "(this.numeroInicial>=reciboInicial &&\n" +
                "this.numeroFinal<=reciboFinal)))");
             List col = (List) query.execute(idEst, reciboInicial,
                            reciboFinal);

                    if (col.size() > 0) {
                        found = true;
                        esRecibo = (EstacionReciboEnhanced) col.get(0);
                    }
           
            if (query != null) {
                query.closeAll();
            }

            if (found) {
            	
            	//Validacion requerida cuando se quiere hacer reset sobre secuenciales.
            	//Si los numeros inicial y final son ambos ceros no se debe generar excepción.
            	long first = eRecibo.getNumeroInicial();
            	long last  = eRecibo.getNumeroFinal();
            	if (first ==0 && last ==0)
            	{
            	}
            	
            	else
            	{
            		respuesta = true;
            	}
                
            }
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        
        return respuesta;
    }

    /**
     * Obtiene el siguiente número de recibo según la secuencia configurada para
     * una estación en particular.
     * @param oid Identificador de la <code>EstacionRecibo</code> de la cual se desea
     * obtener el siguiente número de recibo.
     * @param user Identificador del <code>Usuario</code> de la cual se desea
     * obtener la estacion para obtener el siguiente número de recibo.
     * @return El siguiente número de recibo asociado con la estación cuyo identificador
     * fue recibido como parámetro.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    public long getNextNumeroRecibo(EstacionReciboPk oid, Usuario user, long idProceso)
        throws DAOException {
        EstacionReciboEnhanced aux = null;
        PersistenceManager pm = AdministradorPM.getPM();
        long rta;
        
        try {
            Log.getInstance().info(JDOLookupDAO.class,"Obteniendo siguiente Secuencial de Recibo para la estacion de recibo con ID = " + oid.idEstacion);
            pm.currentTransaction().begin();
            rta = this.getNextNumeroRecibo(new EstacionReciboEnhancedPk(oid),user,idProceso, pm);
            Log.getInstance().info(JDOLookupDAO.class,"El Secuencial de Recibo obtenido para la estacion de recibo con ID = " + 
                    oid.idEstacion + ", es " + rta);
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

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
            pm.close();
        }
        return rta;
    }

    /**
     * Obtiene el siguiente número de recibo según la secuencia configurada para
     * una estación en particular.
     * <p>
     * Método utilizado en transacciones.
     * @param oid Identificador de la <code>EstacionRecibo</code> de la cual se desea
     * obtener el siguiente número de recibo.
     * @return El siguiente número de recibo asociado con la estación cuyo identificador
     * fue recibido como parámetro.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    protected long getNextNumeroRecibo(EstacionReciboEnhancedPk oid, Usuario user, long idProceso, 
        PersistenceManager pm) throws DAOException {
        EstacionReciboEnhanced aux = null;
        long rta;

        try {
            aux = this.getEstacionReciboEnhanced(oid, idProceso ,pm);
            
            if (aux != null) {
            	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
                    throw new DAOException(
                        "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
                }

                rta = aux.getUltimoNumero() + 1;
                aux.setUltimoNumero(rta);
            } else {
            	rta = -2;
            }

            /*if (aux == null) {
                throw new DAOException("No se encontró la estación con el ID: " +
                    oid.idEstacion);
            }*/

            /*if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
                throw new DAOException(
                    "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
            }

            rta = aux.getUltimoNumero() + 1;
            aux.setUltimoNumero(rta);*/
        } catch (JDOObjectNotFoundException e) {
            rta = 0;
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        
            
        EstacionReciboEnhancedPk oi = new EstacionReciboEnhancedPk();
        
        if (rta != -2) {
        	return rta;
        } else {
            if(user==null){
            	throw new DAOException("No se encontró la estación con el ID: " + oid.idEstacion + " el usuario es vacío");
            }

            String estacionPrivadaUsuario = "X-" + user.getUsername();

        	//logger.debug("El nombre de la estacion Privada es " + estacionPrivadaUsuario);
        	try {
        		
        		oi.idEstacion = estacionPrivadaUsuario;
        		
                aux = this.getEstacionReciboEnhanced(oi,idProceso,  pm);
                
                if (aux != null) {
                	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
                        throw new DAOException(
                            "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
                    }
                    rta = aux.getUltimoNumero() + 1;
                    aux.setUltimoNumero(rta);
                } else {
                	rta = -2;
                }
            } catch (JDOObjectNotFoundException e) {
                rta = 0;
            } catch (JDOException e) {
                Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
   
        try {
	        if (rta == -2) {
	        	 throw new DAOException("No se encontró la estación con el ID: " + oi.idEstacion);
	        }
        } catch (JDOObjectNotFoundException e) {
            rta = 0;
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        
        return rta;
    }
    
    /**
     * Obtiene el siguiente número de recibo según la secuencia configurada para
     * una estación en particular.
     * <p>
     * Método utilizado en transacciones.
     * @param oid Identificador de la <code>EstacionRecibo</code> de la cual se desea
     * obtener el siguiente número de recibo.
     * @return El siguiente número de recibo asociado con la estación cuyo identificador
     * fue recibido como parámetro.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.EstacionRecibo
     */
    protected long getNextNumeroReciboBackup(EstacionReciboEnhancedPk oid, Usuario user,
        PersistenceManager pm) throws DAOException {
        EstacionReciboEnhanced aux = null;
        long rta;

        try {
            aux = this.getEstacionReciboEnhanced(oid, pm);
            
            if (aux != null) {
            	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
                    throw new DAOException(
                        "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
                }

                rta = aux.getUltimoNumero() + 1;
                aux.setUltimoNumero(rta);
            } else {
            	rta = -2;
            }

            /*if (aux == null) {
                throw new DAOException("No se encontró la estación con el ID: " +
                    oid.idEstacion);
            }*/

            /*if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
                throw new DAOException(
                    "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
            }

            rta = aux.getUltimoNumero() + 1;
            aux.setUltimoNumero(rta);*/
        } catch (JDOObjectNotFoundException e) {
            rta = 0;
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        
            
        EstacionReciboEnhancedPk oi = new EstacionReciboEnhancedPk();
        
        if (rta != -2) {
        	return rta;
        } else {
            if(user==null){
            	throw new DAOException("No se encontró la estación con el ID: " + oid.idEstacion + " el usuario es vacío");
            }

            String estacionPrivadaUsuario = "X-" + user.getUsername();

        	//logger.debug("El nombre de la estacion Privada es " + estacionPrivadaUsuario);
        	try {
        		
        		oi.idEstacion = estacionPrivadaUsuario;
        		
                aux = this.getEstacionReciboEnhanced(oi, pm);
                
                if (aux != null) {
                	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
                        throw new DAOException(
                            "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
                    }
                    rta = aux.getUltimoNumero() + 1;
                    aux.setUltimoNumero(rta);
                } else {
                	rta = -2;
                }
            } catch (JDOObjectNotFoundException e) {
                rta = 0;
            } catch (JDOException e) {
                Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
   
        try {
	        if (rta == -2) {
	        	 throw new DAOException("No se encontró la estación con el ID: " + oi.idEstacion);
	        }
        } catch (JDOObjectNotFoundException e) {
            rta = 0;
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        
        return rta;
    }

    //	//////////////////////////////////////////////////////////

    /**
     * Consulta las <code>EstacionRecibo</code> para un círculo específico
     * @return la lista de las <code>EstacionRecibo</code> solicitadas
     * @throws DAOException
     */
    public List consultarEstacionesReciboPorCirculo(Circulo circulo)
        throws DAOException {
        ArrayList lista = new ArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        VersantPersistenceManager jdoPM = null;

        String CONSULTA =
            "SELECT  ER.ID_ESTACION,  ER.STRC_NUMERO_INICIAL,  STRC_NUMERO_FINAL, STRC_ULTIMO_NUMERO, STRC_NUMERO_PROCESO " +
            "FROM  SIR_OP_ESTACION_RECIBO ER,  AURIGA.CMN_JERARQUIA CJ, AURIGA.CMN_NIVEL CN, AURIGA.CMN_REL_ESTACION_NIVEL CREN " +
            "WHERE  CJ.JERARQUIA_ID = ? AND " +
            "CJ.NIVEL_INICIO = CN.NIVEL_SUPERIOR_ID AND  CN.ATRIBUTO1 = ?  AND  CN.NIVEL_ID = CREN.NIVEL_ID AND " +
            "CREN.ESTACION_ID = ER.ID_ESTACION ORDER BY ER.STRC_NUMERO_INICIAL ";

        try {
            if ((circulo == null) || (circulo.getIdCirculo() == null)) {
                throw new DAOException(
                    "Debe proporcionar un identificador de Círculo");
            }

            jdoPM = (VersantPersistenceManager) AdministradorPM.getPM();
            //jdoPM.currentTransaction().setOptimistic(false);
            jdoPM.currentTransaction().begin();
            connection = jdoPM.getJdbcConnection(null);

            ps = connection.prepareStatement(CONSULTA);
            ps.setString(1, CJerarquia.JER_CIRCULOS);
            ps.setString(2, circulo.getIdCirculo());

            rs = ps.executeQuery();

            while (rs.next()) {
                EstacionRecibo dato = new EstacionRecibo();
                dato.setIdEstacion(rs.getString(1));
                dato.setNumeroInicial(rs.getLong(2));
                dato.setNumeroFinal(rs.getLong(3));
                dato.setUltimoNumero(rs.getLong(4));
                dato.setNumeroProceso(rs.getLong(5));
                lista.add(dato);
            }

            jdoPM.currentTransaction().commit();
        } catch (NullPointerException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (SQLException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOObjectNotFoundException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (jdoPM.currentTransaction().isActive()) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
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
                Log.getInstance().error(JDOLookupDAO.class,e.getMessage(), e);
                throw new DAOException(e.getMessage(), e);
            }
        }

        return lista;
    }

    /**
    * Servicio que permite eliminar una <code>EstacionRecibo</code>
    * <p>Utilizado desde las pantallas administrativas.
    * @param categoria La <code>EstacionRecibo</code> que va a ser eliminada.
    * @return <code>true</code> (éxito) o <code>false</code> (fracaso) dependiendo del
    * resultado de la operación.
    * @throws <code>DAOException</code>
    * @see gov.sir.core.negocio.modelo.EstacionRecibo
    */
    public boolean eliminarEstacionRecibo(EstacionRecibo estacionRecibo)
        throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EstacionReciboEnhanced eliminada = EstacionReciboEnhanced.enhance(estacionRecibo);

        try {
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            //	Traer Categoría Persistente
            EstacionReciboEnhancedPk idEREnh = new EstacionReciboEnhancedPk();
            idEREnh.idEstacion = eliminada.getIdEstacion();
            idEREnh.numeroProceso = eliminada.getNumeroProceso();

            EstacionReciboEnhanced catPers = (EstacionReciboEnhanced) pm.getObjectById(idEREnh,
                    true);

            if (catPers == null) {
                throw new DAOException(
                    "No existe la Estacion / Recibo con el id " +
                    estacionRecibo.getIdEstacion());
            }

            pm.deletePersistent(catPers);
            pm.currentTransaction().commit();
        } catch (Throwable e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } finally {
            pm.close();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see gov.sir.hermod.dao.RecibosDAO#resetUltimoNumeroEstacionRecibo(gov.sir.core.negocio.modelo.EstacionRecibo.ID, long)
     */
    public boolean resetUltimoNumeroEstacionRecibo(EstacionReciboPk oid,
        long ultimoNumeroActualizado) throws DAOException {
        PersistenceManager pm = AdministradorPM.getPM();
        EstacionReciboEnhanced aux = null;
        EstacionRecibo rta = null;

        try {
            pm.currentTransaction().begin();
            aux = this.getEstacionReciboEnhanced(new EstacionReciboEnhancedPk(
                        oid), pm);

            if (aux == null) {
                throw new DAOException("No se encontró la estación con el ID: " +
                    oid.idEstacion);
            }

            long numeroInicial = aux.getNumeroInicial();
            long numeroFinal = aux.getNumeroFinal();

            if (ultimoNumeroActualizado < numeroInicial) {
                throw new DAOException(
                    "El último número del recibo no puede ser menor al número inicial de la secuencia asignada a la estación");
            }

            if (ultimoNumeroActualizado > numeroFinal) {
                throw new DAOException(
                    "El último número del recibo no puede ser mayor al número final de la secuencia asignada a la estación");
            }

            aux.setUltimoNumero(ultimoNumeroActualizado);
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

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        finally {
            pm.close();
        }

        return true;
    }

    /**
     * Consulta las estaciones relacionadas con un <code>Circulo</code> determinado
     *
     * @param circulo el <code>Circulo</code> del cual se requiere consultar
     * sus estaciones
     * @return la lista de las estaciones relacionadas con el <code>Circulo</code>
     * dado como parámetro
     * @throws DAOException
     */
    public List consultarEstacionesPorCirculo(CirculoEnhanced circulo)
        throws DAOException {
        DAOFactory fact = null;
        EstacionDAO rdao = null;

        try {
            fact = DAOFactory.getFactory();
            rdao = fact.getEstacionDAO();

            Jerarquia jer = new Jerarquia();
            jer.setJerarquiaId(CJerarquia.JER_CIRCULOS);

            if (circulo.getIdCirculo() == null) {
                throw new DAOException(
                    "Debe proporcionar un identificador de círculo para realizar la consulta.");
            }

            Nivel nivel = new Nivel();
            nivel.setAtributo1(circulo.getIdCirculo());

            return rdao.obtenerEstaciones(jer, nivel, true);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    
    /**
     * Obtiene el siguiente número de recibo pero NO avanza la secuencia, si se 
     * supera la secuencia devuelve -1
     * @param oid
     * @param pm
     * @return
     * @throws DAOException
     */
	protected long getNextNumeroReciboSinAvanzar(EstacionReciboEnhancedPk oid, Usuario user, long idProceso,
		PersistenceManager pm) throws DAOException {
		EstacionReciboEnhanced aux = null;
		long rta;

		try {
			aux = this.getEstacionReciboEnhanced(oid, idProceso, pm);

            if (aux != null) {
            	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
    				rta = -1;
    			}
    			else{
    				rta = aux.getUltimoNumero() + 1;
    			}
            } else {
            	rta = -2;
            }
		} catch (JDOObjectNotFoundException e) {
			rta = 0;
		} catch (JDOException e) {
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		EstacionReciboEnhancedPk oi = new EstacionReciboEnhancedPk();
        
        if (rta != -2) {
        	return rta;
        } else {
        	
            if(user == null){
            	throw new DAOException("No se encontró la estación con el ID: " + oid.idEstacion + " el usuario es vacío");
            }

            String estacionPrivadaUsuario = "X-" + user.getUsername();

        	//logger.debug("El nombre de la estacion Privada es " + estacionPrivadaUsuario);
        	try {
        		
        		oi.idEstacion = estacionPrivadaUsuario;
        		
                aux = this.getEstacionReciboEnhanced(oi, idProceso, pm);
                
                if (aux != null) {
                	
                	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
        				rta = -1;
        			}
        			else{
        				rta = aux.getUltimoNumero() + 1;
        			}
                } else {
                	rta = -2;
                }
            } catch (JDOObjectNotFoundException e) {
                rta = 0;
            } catch (JDOException e) {
                Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
   
        try {
	        if (rta == -2) {
	        	 throw new DAOException("No se encontró la estación con el ID: " + oi.idEstacion);
	        }
        } catch (JDOObjectNotFoundException e) {
            rta = 0;
        } catch (JDOException e) {
            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        
        return rta;
	}
	
	
	/**
	 * Obtiene el siguiente número de recibo pero NO avanza la secuencia, si se 
     * supera la secuencia devuelve -1
	 * @param oid
	 * @return
	 * @throws DAOException
	 */
	public long getNextNumeroReciboSinAvanzar(EstacionReciboPk oid, Usuario user, long idProceso)
		throws DAOException {
		EstacionReciboEnhanced aux = null;
		PersistenceManager pm = AdministradorPM.getPM();
		long rta;

		try {
			pm.currentTransaction().begin();
			rta = this.getNextNumeroReciboSinAvanzar(new EstacionReciboEnhancedPk(oid),user,idProceso,
					pm);
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

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}

		return rta;
	}
	
	
	/**
	 * Avanza la secuencia de recibos en el avance configurado, si se supera la
	 * secuencia definida en la estación se lanza una excepción
	 * @param oid
	 * @param avance
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected long avanzarNumeroRecibo(EstacionReciboEnhancedPk oid, long avance, long idProceso, 
		PersistenceManager pm) throws DAOException {
		EstacionReciboEnhanced aux = null;
		long rta;

		try {
			aux = this.getEstacionReciboEnhanced(oid, idProceso ,pm);

			if (aux == null) {
				throw new DAOException("No se encontró la estación con el ID: " +
					oid.idEstacion);
			}

			if ((aux.getUltimoNumero()+avance) > aux.getNumeroFinal()) {
				throw new DAOException(
					"No se puede avanzar número de recibo. Se supera la secuencia configurada");
			}

			rta = aux.getUltimoNumero() + avance;
			aux.setUltimoNumero(rta);
		} catch (JDOObjectNotFoundException e) {
			rta = 0;
		} catch (JDOException e) {
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}
	
	
	/**
	 * Avanza la secuencia de recibos en el avance configurado, si se supera la
	 * secuencia definida en la estación se lanza una excepción
	 * @param oid
	 * @param avance
	 * @return
	 * @throws DAOException
	 */
	public long avanzarNumeroRecibo(EstacionReciboPk oid, long avance, long idProceso)
		throws DAOException {
		EstacionReciboEnhanced aux = null;
		PersistenceManager pm = AdministradorPM.getPM();
		long rta;

		try {
			pm.currentTransaction().begin();
			rta = this.avanzarNumeroRecibo(new EstacionReciboEnhancedPk(oid), avance, idProceso,
					pm);
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

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} 
		finally {
			pm.close();
		}

		return rta;
	}
	




	
	
	
	/**
	 * Hace persistente el motivo por el cual se incrementó el secuencial
	 * de un recibo. 
	 * @param usuario Usuario que incrementa el secuencial.
	 * @param secuencial Valor al cual se incrementa el secuencial de recibos
	 * @param motivo Motivo por el cual se incrementó el secuencial del recibo.
	 * @return <code>true</code> o <code>false</code> dependiendo del resultado
	 * de la operación. 
	 * @throws DAOException
	 */
	public boolean almacenarMotivoIncrementoSecuencial(Usuario usuario, long secuencial, String motivo)
		throws DAOException {
		
		
		PersistenceManager pm = AdministradorPM.getPM();
		boolean rta;

		try {
			
			
			pm.currentTransaction().begin();
			long idSecuencia = this.getSecuencial("SIR_OP_AUMENTO_RECIBO",null);
			
			rta = this.almacenarMotivoIncrementoSecuencial(usuario,secuencial,motivo,idSecuencia,pm);
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

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}

			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		finally {
			pm.close();
		}

		return rta;
	}



	/**
	 * Hace persistente el motivo por el cual se incrementó el secuencial
	 * de un recibo. 
	 * @param usuario Usuario que incrementa el secuencial.
	 * @param secuencial Valor al cual se incrementa el secuencial de recibos
	 * @param motivo Motivo por el cual se incrementó el secuencial del recibo.
	 * @param idSecuencial Secuencia utilizada como llave primaria para salvar el registro.
	 * @param pm PersistenceManager de la transacción. 
	 * @return <code>true</code> o <code>false</code> dependiendo del resultado
	 * de la operación. 
	 * @throws DAOException
	 */
	protected boolean almacenarMotivoIncrementoSecuencial(Usuario usuario, long secuencial,String motivo, long idSecuencia, PersistenceManager pm)
	 throws DAOException {
		
		boolean rta;

		try {
			
			AumentoReciboEnhanced aumento = new AumentoReciboEnhanced();
			aumento.setIdAumentoRecibo(idSecuencia+"");
			aumento.setMotivo(motivo);
			aumento.setNumeroRecibo(secuencial);
			aumento.setFechaCreacion(new Date());
			
			UsuarioEnhancedPk userId = new UsuarioEnhancedPk();
			userId.idUsuario = usuario.getIdUsuario();
						
			
			UsuarioEnhanced userEnh = (UsuarioEnhanced)pm.getObjectById(userId,true);
			
			if (userEnh == null) {
				throw new DAOException("El usuario que incrementa el secuencial no es válido: " );
			}
			aumento.setUsuario(userEnh);
			
			
			pm.makePersistent(aumento);
			rta = true;

			
		} catch (JDOObjectNotFoundException e) {
			
			rta = false;
		} catch (JDOException e) {
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
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
		
		HermodProperties hprop = HermodProperties.getInstancia();

        Connection con        = null;
        PreparedStatement pst = null;
        ResultSet rs          = null;
        
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
				Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}
 
		return rta;
	}
	
	protected void addAuditoriaEstacionRecibo(EstacionRecibo eRecibo, EstacionReciboEnhanced eReciboAnt , Usuario user, PersistenceManager pm)
    throws DAOException {
		try {
			
			Date fecha = new Date(System.currentTimeMillis());
			
			UsuarioEnhancedPk oid = new UsuarioEnhancedPk();
			oid.idUsuario = user.getIdUsuario();

			UsuarioEnhanced userE = (UsuarioEnhanced) pm.getObjectById(oid, true);
						
			EstacionReciboAuditoriaEnhanced estacionReciboAuditoria = new  EstacionReciboAuditoriaEnhanced();
			long idSecuencial = this.getSecuencialTabla(CSecuencias.SECUENCIA_AUDITORIA_RECIBO,pm);
			int idSec = (int)idSecuencial;
			estacionReciboAuditoria.setIdAuditoria(idSec);
			estacionReciboAuditoria.setIdEstacion(eRecibo.getIdEstacion());
			estacionReciboAuditoria.setNumeroProceso(eRecibo.getNumeroProceso());
			estacionReciboAuditoria.setUsuario(userE);
			estacionReciboAuditoria.setNumeroNuevo(eRecibo.getUltimoNumero());
			estacionReciboAuditoria.setNumeroAnterior(eReciboAnt.getUltimoNumero());
			estacionReciboAuditoria.setFechaCreacion(fecha);
			pm.makePersistent(estacionReciboAuditoria);
		} catch (JDOException e) {
			Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		
		
	}
	
	/**
	 * Este metodo consulta y avanza el valor de la secuencia que tiene como parámetro
	 * @param nomSecuencia Nombre de la secuencia como se encuentra en la base de datos
	 * @param pm Persestence Manager del cual se obtendrá la conexión a la base de datos
	 * @return El long que representa el secuencial
	 * @throws DAOException
	 */
    protected long getSecuencialTabla(String nomSecuencia, PersistenceManager pm)throws DAOException{
        Connection con=null;
        long secuencia=0;
        ResultSet rs=null;
        PreparedStatement ps=null;
        VersantPersistenceManager jdoPM = null;
        String consulta="SELECT "+nomSecuencia+".NEXTVAL FROM DUAL";
        try {
            jdoPM = (VersantPersistenceManager)pm;
            con=jdoPM.getJdbcConnection(null);
            
            ps=con.prepareStatement(consulta);
            rs=ps.executeQuery();
            while(rs.next()){
                secuencia=rs.getLong(1);
                return secuencia;
            }
        }  catch (SQLException e) {
            ExceptionPrinter ep=new ExceptionPrinter(e);
            Log.getInstance().error(JDOLookupDAO.class,"No se pudo obtener la secuencia:"+ep.toString());
            throw new DAOException(e.getMessage(), e);
        } finally{
            try {
                if (rs!=null){
                    rs.close();
                }
                if (ps!=null){
                    ps.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                ExceptionPrinter ep=new ExceptionPrinter(e);
                Log.getInstance().error(JDOLookupDAO.class,ep.toString());
                throw new DAOException(e.getMessage(), e);
            }
        }
        return secuencia;
        
    }

	
	
}
