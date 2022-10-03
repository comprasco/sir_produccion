/*
 * Created on 14-jul-2005
 *
 */
package gov.sir.forseti.dao.impl.jdogenie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;

import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.CancelacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;

import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.forseti.dao.DAOException;
import gov.sir.forseti.dao.FolioDerivadoDAO;

/**
 * @author fceballos
 *
 */
public class JDOGenieFolioDerivadoDAO implements FolioDerivadoDAO {
	private JDOGenieAuditoriaDAO auditoria = new JDOGenieAuditoriaDAO();

	/**
	* Constructor por defecto de JDOGenieFolioDerivadoDAO
	*/
	public JDOGenieFolioDerivadoDAO() {
	}

	/**
	 * Desasocia dos folios que se encuentran encadenados a través de cada una de sus anotaciones
	 * @param anotaID1
	 * @param anotaID2
	 * @return
	 * @throws DAOException
	 */
	public boolean desasociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario) throws DAOException{
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			pm.currentTransaction().begin();
			//Se obtiene el usuario persistente:
			UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
			uid.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced us = folDAO.getUsuarioByID(uid, pm);

			if (us == null) {
				throw new DAOException("El usuario especificado no existe: " +
					uid.idUsuario);
			}

			//Se revisa la información temporal y definitiva para separar los casos:
			AnotacionTMPPk anGenTMPID = new AnotacionTMPPk();
			anGenTMPID.idAnotacionTmp = anotaGeneradoraID.idAnotacion;
			anGenTMPID.idMatricula = anotaGeneradoraID.idMatricula;

			AnotacionTMP anGenTMP = folDAO.getAnotacionTMPByID(anGenTMPID, pm);

			AnotacionTMPPk anDerTMPID = new AnotacionTMPPk();
			anDerTMPID.idAnotacionTmp = anotaDerivadaID.idAnotacion;
			anDerTMPID.idMatricula = anotaDerivadaID.idMatricula;

			AnotacionTMP anDerTMP = folDAO.getAnotacionTMPByID(anDerTMPID, pm);


			//Se obtienen las dos anotaciones definitivas persistentes
			AnotacionEnhanced anGen = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaGeneradoraID), pm);

			AnotacionEnhanced anDer = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaDerivadaID), pm);


			//Con respecto a los resultados se ponen los siguientes casos:
			if((anGenTMP!=null)&&(anDerTMP!=null)){
			//1. Si las dos anotaciones existen en temporal:
				//1.0 Se verifican los bloqueos de los dos folios:
				folDAO.validarBloqueoFolio(anGenTMP.getFolio(), usuario, pm);
				folDAO.validarBloqueoFolio(anDerTMP.getFolio(), usuario, pm);

				//1.1 Se revisa si alguna de las 2 anotaciones es nueva temporal
				if((anGen==null)||(anDer==null)){
					//1.1.1 Si alguna de las dos anotaciones es nueva seguramente la relación que existe
					//      entre las dos es nueva temporal también, por lo tanto toca borrarla
					FolioDerivadoTMP relBor = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
					if(relBor == null){
						throw new DAOException("No se encontró la relación entre las dos anotaciones");
					}

					//Se elimina la relacion:
					pm.deletePersistent(relBor);
				}
				else{
					//1.1.2 Si las dos anotaciones son viejas (se están aplicando deltas a las dos anotaciones)
					//1.1.2.1 Se Busca la asociación temporal entre las dos anotaciones. Si existe y el flag
					//        toDelete está en true se lanza excepción.
					FolioDerivadoTMP relToDelete = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
					if(relToDelete!=null){
						if(relToDelete.isToDelete()){
							throw new DAOException("No se encontró la relación entre las dos anotaciones");
					}
                        else{
                            //Se elimina la relacion:
                             pm.deletePersistent(relToDelete);
                        }
					}
					//1.1.2.2 Si no encuentra la asociación, se verifica que esta asociación exista en definitivo
					//        y se crea en temporal con flag toDelete en true
					else{
						FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(anGen, anDer, pm);
						if(folDerDef==null){
							throw new DAOException("No se encontró la relación entre las dos anotaciones");
						}
						FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
						folDerToDelete.setPadreTmp(anGenTMP);
						folDerToDelete.setHijoTmp(anDerTMP);
						folDerToDelete.setToDelete(true);

						//Se hace persistente el cambio:
						pm.makePersistent(folDerToDelete);
					}
				}
			}
			else if((anGenTMP==null)&&(anDerTMP==null)){
			//2. Ninguna anotación existe en temporal
				//2.0 Se verifican la existencia de las anotaciones en definitivo y los bloqueos:
				if((anGen==null)||(anDer==null)){
					throw new DAOException("No se encontró la anotación a actualizar");
				}
				folDAO.validarBloqueoFolio(anGen.getFolio(), usuario, pm);
				folDAO.validarBloqueoFolio(anDer.getFolio(), usuario, pm);


				//2.1 Verificar que existe la asociación en definitivo y crear las anotaciones
				//    temporales con la asociación temporal con flag to delete en true
				FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(anGen, anDer, pm);
				if(folDerDef==null){
					throw new DAOException("No se encontró la relación entre las dos anotaciones");
				}

				//Adición de anotación temporal generadora
				AnotacionTMP anGenTMPToInsert = new AnotacionTMP();

				//Seteamos el identificador y los atributos básicos para que se pueda crear en
				//Base de datos, se setea también el documento para que eventualmente se pueda editar
				anGenTMPToInsert.setIdAnotacionTmp(anGen.getIdAnotacion());
				anGenTMPToInsert.setEstado(anGen.getEstado());
				anGenTMPToInsert.setNaturalezaJuridica(anGen.getNaturalezaJuridica());
				anGenTMPToInsert.setTipoAnotacion(anGen.getTipoAnotacion());
				anGenTMPToInsert.setLastIdSalvedad(anGen.getLastIdSalvedad());
				anGenTMPToInsert.setDocumento(anGen.getDocumento());

				//Adicionamos la anotación temporal que representa la anotación
				//definitiva al folio
				 folDAO.addAnotacionTMPToFolio(anGen.getFolio(), anGenTMPToInsert, us, null, pm);
				 pm.makePersistent(anGenTMPToInsert);


				//Adición de anotación temporal derivada
				AnotacionTMP anDerTMPToInsert = new AnotacionTMP();

				//Seteamos el identificador y los atributos básicos para que se pueda crear en
				//Base de datos, se setea también el documento para que eventualmente se pueda editar
				anDerTMPToInsert.setIdAnotacionTmp(anDer.getIdAnotacion());
				anDerTMPToInsert.setEstado(anDer.getEstado());
				anDerTMPToInsert.setNaturalezaJuridica(anDer.getNaturalezaJuridica());
				anDerTMPToInsert.setTipoAnotacion(anDer.getTipoAnotacion());
				anDerTMPToInsert.setLastIdSalvedad(anDer.getLastIdSalvedad());
				anDerTMPToInsert.setDocumento(anDer.getDocumento());

				//Adicionamos la anotación temporal que representa la anotación
				//definitiva al folio
				folDAO.addAnotacionTMPToFolio(anDer.getFolio(), anDerTMPToInsert, us, null, pm);
				pm.makePersistent(anDerTMPToInsert);

				//Se crea la asociación temporal con en flag en true
				FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
				folDerToDelete.setPadreTmp(anGenTMPToInsert);
				folDerToDelete.setHijoTmp(anDerTMPToInsert);
				folDerToDelete.setToDelete(true);

				//Se hace persistente el cambio:
				pm.makePersistent(folDerToDelete);
			}
			else{
			//3. Una anotación existe en temporal y la otra no
				//3.0 Se verifican la existencia de las anotaciones en definitivo y los bloqueos:
				if((anGen==null)||(anDer==null)){
					throw new DAOException("No se encontró la anotación a actualizar");
				}

				folDAO.validarBloqueoFolio(anGen.getFolio(), usuario, pm);
				folDAO.validarBloqueoFolio(anDer.getFolio(), usuario, pm);


				//3.1 Verificar que existe la asociación en definitivo y crear las anotaciones
				//    temporales con la asociación temporal con flag to delete en true
				FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(anGen, anDer, pm);
				if(folDerDef==null){
					throw new DAOException("No se encontró la relación entre las dos anotaciones");
				}

				AnotacionEnhanced anotaToCrear = null;

				if(anGenTMP==null){
					anotaToCrear = anGen;
				}
				else{
					anotaToCrear = anDer;
				}

				//Adición de anotación temporal derivada o generadora
				AnotacionTMP anTMPToInsert = new AnotacionTMP();

				//Seteamos el identificador y los atributos básicos para que se pueda crear en
				//Base de datos, se setea también el documento para que eventualmente se pueda editar
				anTMPToInsert.setIdAnotacionTmp(anotaToCrear.getIdAnotacion());
				anTMPToInsert.setEstado(anotaToCrear.getEstado());
				anTMPToInsert.setNaturalezaJuridica(anotaToCrear.getNaturalezaJuridica());
				anTMPToInsert.setTipoAnotacion(anotaToCrear.getTipoAnotacion());
				anTMPToInsert.setLastIdSalvedad(anotaToCrear.getLastIdSalvedad());
				anTMPToInsert.setDocumento(anotaToCrear.getDocumento());

				//Adicionamos la anotación temporal que representa la anotación
				//definitiva al folio
				folDAO.addAnotacionTMPToFolio(anotaToCrear.getFolio(), anTMPToInsert, us, null, pm);
				pm.makePersistent(anTMPToInsert);

				//Se crea la asociación temporal con en flag en true
				FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();

				if(anGenTMP==null){
					folDerToDelete.setPadreTmp(anTMPToInsert);
					folDerToDelete.setHijoTmp(anDerTMP);
				}
				else{
					folDerToDelete.setPadreTmp(anGenTMP);
					folDerToDelete.setHijoTmp(anTMPToInsert);
				}

				folDerToDelete.setToDelete(true);

				//Se hace persistente el cambio:
				pm.makePersistent(folDerToDelete);

			}

			pm.currentTransaction().commit();


		} catch (JDOException e) {
		   if (pm.currentTransaction().isActive()) {
			   pm.currentTransaction().rollback();
		   }
		   Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
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
	 * Obtiene un folio derivado temporal dado dos anotaciones la generadora y la derivada
	 * @param anotaGenTMP
	 * @param anotaDerTMP
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FolioDerivadoTMP getFolioDerivadoTMP(AnotacionTMP anotaGenTMP, AnotacionTMP anotaDerTMP, PersistenceManager pm)throws DAOException{
		FolioDerivadoTMP rta = null;
		try {
			Query query = pm.newQuery(FolioDerivadoTMP.class);
			query.declareParameters("AnotacionTMP anotaGenTMP, AnotacionTMP anotaDerTMP");
			query.setFilter("this.padreTmp==anotaGenTMP &&\n"+
					"this.hijoTmp==anotaDerTMP");
			Collection col = (Collection)query.execute(anotaGenTMP, anotaDerTMP);

			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				rta = (FolioDerivadoTMP) iter.next();
			} else {
				rta = null;
			}
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}



	/**
	 * Obtiene un folio derivado dado dos anotaciones la generadora y la derivada
	 * @param anotaGen
	 * @param anotaDer
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FolioDerivadoEnhanced getFolioDerivado(AnotacionEnhanced anotaGen, AnotacionEnhanced anotaDer, PersistenceManager pm)throws DAOException{
		FolioDerivadoEnhanced rta = null;
		try {
			Query query = pm.newQuery(FolioDerivadoEnhanced.class);
			query.declareParameters("AnotacionEnhanced anotaGen, AnotacionEnhanced anotaDer");
			query.setFilter("this.padre==anotaGen &&\n"+
					"this.hijo==anotaDer");
			Collection col = (Collection)query.execute(anotaGen, anotaDer);

			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				rta = (FolioDerivadoEnhanced) iter.next();
			} else {
				rta = null;
			}
		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}



	/**
	 * Asocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean asociarFolios(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, Turno turno) throws DAOException{
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		try{
			pm.currentTransaction().begin();
			//Se obtiene el usuario persistente:
			UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
			uid.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced us = folDAO.getUsuarioByID(uid, pm);

			if (us == null) {
				throw new DAOException("El usuario especificado no existe: " +
					uid.idUsuario);
			}

			//Se revisa la información temporal y definitiva para separar los casos:
			AnotacionTMPPk anGenTMPID = new AnotacionTMPPk();
			anGenTMPID.idAnotacionTmp = anotaGeneradoraID.idAnotacion;
			anGenTMPID.idMatricula = anotaGeneradoraID.idMatricula;

			AnotacionTMP anGenTMP = folDAO.getAnotacionTMPByID(anGenTMPID, pm);

			AnotacionTMPPk anDerTMPID = new AnotacionTMPPk();
			anDerTMPID.idAnotacionTmp = anotaDerivadaID.idAnotacion;
			anDerTMPID.idMatricula = anotaDerivadaID.idMatricula;

			AnotacionTMP anDerTMP = folDAO.getAnotacionTMPByID(anDerTMPID, pm);

            //Se obtienen las dos anotaciones definitivas persistentes
			AnotacionEnhanced anGen = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaGeneradoraID), pm);

			AnotacionEnhanced anDer = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaDerivadaID), pm);

			//Se llama al servicio de validación de asociación de anotaciones
			validarAnotacionesParaAsociacion(anGenTMP, anDerTMP, anGen, anDer, pm);

			//Con respecto a los resultados se ponen los siguientes casos:
			if((anGenTMP!=null)&&(anDerTMP!=null)){
            /**
             * @author: Fernando Padilla
             * @change: Se asocia el idWorkFlow en la anotacion. No se estaba
             *          guardando.
             **/
            if(turno != null && turno.getIdWorkflow() != null){
                anGenTMP.setIdWorkflow(turno.getIdWorkflow());
                anDerTMP.setIdWorkflow(turno.getIdWorkflow());
            }
			//1. Si las dos anotaciones existen en temporal:
				//1.0 Se verifican los bloqueos de los dos folios:
				folDAO.validarBloqueoFolio(anGenTMP.getFolio(), usuario, pm);
				folDAO.validarBloqueoFolio(anDerTMP.getFolio(), usuario, pm);

				//1.1 Se revisa si alguna de las 2 anotaciones es nueva temporal
				if((anGen==null)||(anDer==null)){
					//1.1.1 Si alguna de las dos anotaciones es nueva y si la relación ya existe
					//      se lanza una excepcion
					FolioDerivadoTMP relBor = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
					if(relBor != null){
						//1.1.1.1 La relación ya existe
						if(!relBor.isToDelete()){
							throw new DAOException("Ya existe la relación entre las dos anotaciones");
						}
						else{
							//Se elimina la asociación con marca para eliminar
							pm.deletePersistent(relBor);
						}
					}
					else{
						//1.1.1.2 La relación no existe, se crea y se setean los tipos de anotación
						setTiposAnotaciones(anGenTMP, anDerTMP, folDAO, pm);

						FolioDerivadoTMP folDerToInsert = new FolioDerivadoTMP();
						folDerToInsert.setPadreTmp(anGenTMP);
						folDerToInsert.setHijoTmp(anDerTMP);

						//Se hace persistente el cambio:
						pm.makePersistent(folDerToInsert);
					}
				}
				else{
					//1.1.2 Si las dos anotaciones son viejas (se están aplicando deltas a las dos anotaciones)
					//1.1.2.1 Se Busca la asociación temporal entre las dos anotaciones. Si existe y el flag
					//        toDelete está en true se borra, de lo contrario se lanza una excepción indicado
					//        que la asociación ya existe
					FolioDerivadoTMP relToDelete = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
					if(relToDelete!=null){
						if(!relToDelete.isToDelete()){
							throw new DAOException("Ya existe la relación entre las dos anotaciones");
						}
						else{
							//Se elimina la asociación con marca para eliminar
							pm.deletePersistent(relToDelete);
						}
					}
					//1.1.2.2 Si no encuentra la asociación, se verifica que esta asociación no exista en definitivo
					//        y se crea en temporal
					else{
						FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(anGen, anDer, pm);
						if(folDerDef!=null){
							throw new DAOException("Ya existe la relación entre las dos anotaciones");
						}

						setTiposAnotaciones(anGenTMP, anDerTMP, folDAO, pm);
						FolioDerivadoTMP folDerToInsert = new FolioDerivadoTMP();
						folDerToInsert.setPadreTmp(anGenTMP);
						folDerToInsert.setHijoTmp(anDerTMP);

						//Se hace persistente el cambio:
						pm.makePersistent(folDerToInsert);
					}
				}
			}
			else if((anGenTMP==null)&&(anDerTMP==null)){
			//2. Ninguna anotación existe en temporal
				//2.0 Se verifican la existencia de las anotaciones en definitivo y los bloqueos:
				if((anGen==null)||(anDer==null)){
					throw new DAOException("No se encontró la anotación a actualizar");
				}
				folDAO.validarBloqueoFolio(anGen.getFolio(), usuario, pm);
				folDAO.validarBloqueoFolio(anDer.getFolio(), usuario, pm);


				//2.1 Verificar que no existe la asociación en definitivo y crear las anotaciones
				//    temporales con la asociación temporal
				FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(anGen, anDer, pm);
				if(folDerDef!=null){
					throw new DAOException("Ya existe la relación entre las dos anotaciones");
				}

				//Adición de anotación temporal generadora
				AnotacionTMP anGenTMPToInsert = new AnotacionTMP();

				//Seteamos el identificador y los atributos básicos para que se pueda crear en
				//Base de datos, se setea también el documento para que eventualmente se pueda editar
				anGenTMPToInsert.setIdAnotacionTmp(anGen.getIdAnotacion());
				anGenTMPToInsert.setEstado(anGen.getEstado());
				anGenTMPToInsert.setNaturalezaJuridica(anGen.getNaturalezaJuridica());
				anGenTMPToInsert.setTipoAnotacion(anGen.getTipoAnotacion());
				anGenTMPToInsert.setLastIdSalvedad(anGen.getLastIdSalvedad());
				anGenTMPToInsert.setDocumento(anGen.getDocumento());
                /**
                 * @author: Fernando Padilla
                 * @change: Se asocia el idWorkFlow en la anotacion. No se estaba
                 *          guardando.
                 **/
                if(turno != null && turno.getIdWorkflow() != null){
                    anGenTMPToInsert.setIdWorkflow(turno.getIdWorkflow());
                }

				//Adicionamos la anotación temporal que representa la anotación
				//definitiva al folio
				 folDAO.addAnotacionTMPToFolio(anGen.getFolio(), anGenTMPToInsert, us, null, pm);
				 pm.makePersistent(anGenTMPToInsert);


				//Adición de anotación temporal derivada
				AnotacionTMP anDerTMPToInsert = new AnotacionTMP();

				//Seteamos el identificador y los atributos básicos para que se pueda crear en
				//Base de datos, se setea también el documento para que eventualmente se pueda editar
				anDerTMPToInsert.setIdAnotacionTmp(anDer.getIdAnotacion());
				anDerTMPToInsert.setEstado(anDer.getEstado());
				anDerTMPToInsert.setNaturalezaJuridica(anDer.getNaturalezaJuridica());
				anDerTMPToInsert.setTipoAnotacion(anDer.getTipoAnotacion());
				anDerTMPToInsert.setLastIdSalvedad(anDer.getLastIdSalvedad());
				anDerTMPToInsert.setDocumento(anDer.getDocumento());
                /**
                 * @author: Fernando Padilla
                 * @change: Se asocia el idWorkFlow en la anotacion. No se estaba
                 *          guardando.
                 **/
                if(turno != null && turno.getIdWorkflow() != null){
                    anDerTMPToInsert.setIdWorkflow(turno.getIdWorkflow());
                }
                
				//Adicionamos la anotación temporal que representa la anotación
				//definitiva al folio
				folDAO.addAnotacionTMPToFolio(anDer.getFolio(), anDerTMPToInsert, us, null, pm);
				pm.makePersistent(anDerTMPToInsert);

				//Se setean los tipos de anotacion derivada y generadora
				setTiposAnotaciones(anGenTMPToInsert, anDerTMPToInsert, folDAO, pm);

				//Se crea la asociación temporal con en flag en true
				FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
				folDerToDelete.setPadreTmp(anGenTMPToInsert);
				folDerToDelete.setHijoTmp(anDerTMPToInsert);

				//Se hace persistente el cambio:
				pm.makePersistent(folDerToDelete);
			}
			else{
			//3. Una anotación existe en temporal y la otra no
				//3.0 Se verifican la existencia de las anotaciones en definitivo y los bloqueos:
				if((anGen==null)||(anDer==null)){
					throw new DAOException("No se encontró la anotación a actualizar");
				}

				folDAO.validarBloqueoFolio(anGen.getFolio(), usuario, pm);
				folDAO.validarBloqueoFolio(anDer.getFolio(), usuario, pm);


				//3.1 Verificar que no existe la asociación en definitivo y crear las anotaciones
				//    temporales con la asociación temporal con flag to delete en true
				FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(anGen, anDer, pm);
				if(folDerDef!=null){
					throw new DAOException("Ya existe la relación entre las dos anotaciones");
				}

				AnotacionEnhanced anotaToCrear = null;

				if(anGenTMP==null){
					anotaToCrear = anGen;
				}
				else{
					anotaToCrear = anDer;
				}

				//Adición de anotación temporal derivada o generadora
				AnotacionTMP anTMPToInsert = new AnotacionTMP();

				//Seteamos el identificador y los atributos básicos para que se pueda crear en
				//Base de datos, se setea también el documento para que eventualmente se pueda editar
				anTMPToInsert.setIdAnotacionTmp(anotaToCrear.getIdAnotacion());
				anTMPToInsert.setEstado(anotaToCrear.getEstado());
				anTMPToInsert.setNaturalezaJuridica(anotaToCrear.getNaturalezaJuridica());
				anTMPToInsert.setTipoAnotacion(anotaToCrear.getTipoAnotacion());
				anTMPToInsert.setLastIdSalvedad(anotaToCrear.getLastIdSalvedad());
				anTMPToInsert.setDocumento(anotaToCrear.getDocumento());
                /**
                 * @author: Fernando Padilla
                 * @change: Se asocia el idWorkFlow en la anotacion. No se estaba
                 *          guardando.
                 **/
                if(turno != null && turno.getIdWorkflow() != null){
                    anTMPToInsert.setIdWorkflow(turno.getIdWorkflow());
                }
				//Adicionamos la anotación temporal que representa la anotación
				//definitiva al folio
				folDAO.addAnotacionTMPToFolio(anotaToCrear.getFolio(), anTMPToInsert, us, null, pm);
				pm.makePersistent(anTMPToInsert);

				//Se crea la asociación temporal con en flag en true
				FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();

				if(anGenTMP==null){
					//Se setean los tipos de anotacion derivada y generadora
					setTiposAnotaciones(anTMPToInsert, anDerTMP, folDAO, pm);
					folDerToDelete.setPadreTmp(anTMPToInsert);
					folDerToDelete.setHijoTmp(anDerTMP);
				}
				else{
					//Se setean los tipos de anotacion derivada y generadora
					setTiposAnotaciones(anGenTMP, anTMPToInsert, folDAO, pm);
					folDerToDelete.setPadreTmp(anGenTMP);
					folDerToDelete.setHijoTmp(anTMPToInsert);
				}


				//Se hace persistente el cambio:
				pm.makePersistent(folDerToDelete);

			}

			pm.currentTransaction().commit();


		} catch (JDOException e) {
		   if (pm.currentTransaction().isActive()) {
			   pm.currentTransaction().rollback();
		   }

		   Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
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
	 * Asocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean asociarFoliosAdministrativa(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws DAOException{
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		
		//Tipo 1 es segregacion
		//Tipo 2 es Englobe
		try{
			pm.currentTransaction().begin();
			//Se obtiene el usuario persistente:
			UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
			uid.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced us = folDAO.getUsuarioByID(uid, pm);

			if (us == null) {
				throw new DAOException("El usuario especificado no existe: " +
					uid.idUsuario);
			}

			//Se revisa la información temporal y definitiva para separar los casos:
			AnotacionTMPPk anGenTMPID = new AnotacionTMPPk();
			anGenTMPID.idAnotacionTmp = anotaGeneradoraID.idAnotacion;
			anGenTMPID.idMatricula = anotaGeneradoraID.idMatricula;

			AnotacionTMP anGenTMP = folDAO.getAnotacionTMPByID(anGenTMPID, pm);

			AnotacionTMPPk anDerTMPID = new AnotacionTMPPk();
			anDerTMPID.idAnotacionTmp = anotaDerivadaID.idAnotacion;
			anDerTMPID.idMatricula = anotaDerivadaID.idMatricula;

			AnotacionTMP anDerTMP = folDAO.getAnotacionTMPByID(anDerTMPID, pm);


			//Se obtienen las dos anotaciones definitivas persistentes
			AnotacionEnhanced anGen = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaGeneradoraID), pm);

			AnotacionEnhanced anDer = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaDerivadaID), pm);

			//segregacion
			if (anDer==null && tipo == 1){
				throw new DAOException("La anotación " + anotaDerivadaID.idAnotacion +  " del folio " + anotaDerivadaID.idMatricula + ", Debe ser Definitiva.");
			}
			
			//englobe
			if (anGen==null && tipo == 2){
				throw new DAOException("La anotación " + anotaGeneradoraID.idAnotacion +  " del folio " + anotaGeneradoraID.idMatricula + ", Debe ser Definitiva.");
			}
			
			//Se llama al servicio de validación de asociación de anotaciones
			validarAnotacionesParaAsociacion(anGenTMP, anDerTMP, anGen, anDer, pm);

			if (tipo== 1) {
				
				FolioDerivadoTMP relBor = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
				
				if(relBor != null){
					//1.1.1.1 La relación ya existe
					if(!relBor.isToDelete()){
						throw new DAOException("Ya existe la relación entre las dos anotaciones");
					} else{
						//Se elimina la asociación con marca para eliminar
						pm.deletePersistent(relBor);
					}
				} 
				
				if (anDerTMP == null) {
					//	Adición de anotación temporal derivada
					AnotacionTMP anDerTMPToInsert = new AnotacionTMP();

					//Seteamos el identificador y los atributos básicos para que se pueda crear en
					//Base de datos, se setea también el documento para que eventualmente se pueda editar
					anDerTMPToInsert.setIdAnotacionTmp(anDer.getIdAnotacion());
					anDerTMPToInsert.setEstado(anDer.getEstado());
					anDerTMPToInsert.setNaturalezaJuridica(anDer.getNaturalezaJuridica());
					anDerTMPToInsert.setTipoAnotacion(anDer.getTipoAnotacion());
					anDerTMPToInsert.setLastIdSalvedad(anDer.getLastIdSalvedad());
					anDerTMPToInsert.setDocumento(anDer.getDocumento());

					//Adicionamos la anotación temporal que representa la anotación
					//definitiva al folio
					folDAO.addAnotacionTMPToFolio(anDer.getFolio(), anDerTMPToInsert, us, null, pm);
					pm.makePersistent(anDerTMPToInsert);
					
					// Se crear la asociacion Temporal
					FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
					//	Se setean los tipos de anotacion derivada y generadora
					setTiposAnotaciones(anGenTMP, anDerTMPToInsert, folDAO, pm);
					folDerToDelete.setPadreTmp(anGenTMP);
					folDerToDelete.setHijoTmp(anDerTMPToInsert);

					//Se hace persistente el cambio:
					pm.makePersistent(folDerToDelete);
				} else {
					//	Se crear la asociacion Temporal
					FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
					//	Se setean los tipos de anotacion derivada y generadora
					setTiposAnotaciones(anGenTMP, anDerTMP, folDAO, pm);
					folDerToDelete.setPadreTmp(anGenTMP);
					folDerToDelete.setHijoTmp(anDerTMP);

					//Se hace persistente el cambio:
					pm.makePersistent(folDerToDelete);
				}
			}
			
			if (tipo== 2){
				
				FolioDerivadoTMP relBor = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
				
				if(relBor != null){
					//1.1.1.1 La relación ya existe
					if(!relBor.isToDelete()){
						throw new DAOException("Ya existe la relación entre las dos anotaciones");
					} else{
						//Se elimina la asociación con marca para eliminar
						pm.deletePersistent(relBor);
					}
				} 
				
				if (anGenTMP == null) {
					//	Adición de anotación temporal derivada
					AnotacionTMP anGenTMPToInsert = new AnotacionTMP();

					//Seteamos el identificador y los atributos básicos para que se pueda crear en
					//Base de datos, se setea también el documento para que eventualmente se pueda editar
					anGenTMPToInsert.setIdAnotacionTmp(anGen.getIdAnotacion());
					anGenTMPToInsert.setEstado(anGen.getEstado());
					anGenTMPToInsert.setNaturalezaJuridica(anGen.getNaturalezaJuridica());
					anGenTMPToInsert.setTipoAnotacion(anGen.getTipoAnotacion());
					anGenTMPToInsert.setLastIdSalvedad(anGen.getLastIdSalvedad());
					anGenTMPToInsert.setDocumento(anGen.getDocumento());

					//Adicionamos la anotación temporal que representa la anotación
					//definitiva al folio
					folDAO.addAnotacionTMPToFolio(anGen.getFolio(), anGenTMPToInsert, us, null, pm);
					pm.makePersistent(anGenTMPToInsert);
					
					// Se crear la asociacion Temporal
					FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
					//	Se setean los tipos de anotacion derivada y generadora
					setTiposAnotaciones(anGenTMPToInsert, anDerTMP, folDAO, pm);
					folDerToDelete.setPadreTmp(anGenTMPToInsert);
					folDerToDelete.setHijoTmp(anDerTMP);

					//Se hace persistente el cambio:
					pm.makePersistent(folDerToDelete);
				} else {
					//	Se crear la asociacion Temporal
					FolioDerivadoTMP folDerToDelete = new FolioDerivadoTMP();
					//	Se setean los tipos de anotacion derivada y generadora
					setTiposAnotaciones(anGenTMP, anDerTMP, folDAO, pm);
					folDerToDelete.setPadreTmp(anGenTMP);
					folDerToDelete.setHijoTmp(anDerTMP);

					//Se hace persistente el cambio:
					pm.makePersistent(folDerToDelete);
				}
			}
			
			pm.currentTransaction().commit();


		} catch (JDOException e) {
		   if (pm.currentTransaction().isActive()) {
			   pm.currentTransaction().rollback();
		   }

		   Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
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
	 * DesAsocia dos folios a través de sus anotaciones generadora y derivada
	 * @param anotaGeneradoraID
	 * @param anotaDerivadaID
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean desasociarFoliosAdministrativa(AnotacionPk anotaGeneradoraID, AnotacionPk anotaDerivadaID, Usuario usuario, int tipo) throws DAOException{
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		
		//Tipo 1 es segregacion
		//Tipo 2 es Englobe
		try{
			pm.currentTransaction().begin();
			//Se obtiene el usuario persistente:
			UsuarioEnhancedPk uid = new UsuarioEnhancedPk();
			uid.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced us = folDAO.getUsuarioByID(uid, pm);

			if (us == null) {
				throw new DAOException("El usuario especificado no existe: " +
					uid.idUsuario);
			}

			//Se revisa la información temporal y definitiva para separar los casos:
			AnotacionTMPPk anGenTMPID = new AnotacionTMPPk();
			anGenTMPID.idAnotacionTmp = anotaGeneradoraID.idAnotacion;
			anGenTMPID.idMatricula = anotaGeneradoraID.idMatricula;

			AnotacionTMP anGenTMP = folDAO.getAnotacionTMPByID(anGenTMPID, pm);

			AnotacionTMPPk anDerTMPID = new AnotacionTMPPk();
			anDerTMPID.idAnotacionTmp = anotaDerivadaID.idAnotacion;
			anDerTMPID.idMatricula = anotaDerivadaID.idMatricula;

			AnotacionTMP anDerTMP = folDAO.getAnotacionTMPByID(anDerTMPID, pm);


			//Se obtienen las dos anotaciones definitivas persistentes
			AnotacionEnhanced anGen = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaGeneradoraID), pm);

			AnotacionEnhanced anDer = folDAO.getAnotacionByID(new AnotacionEnhancedPk(anotaDerivadaID), pm);

			//segregacion
			if (anDer==null && tipo == 1){
				throw new DAOException("La anotación " + anotaDerivadaID.idAnotacion +  " del folio " + anotaDerivadaID.idMatricula + ", Debe ser Definitiva.");
			}
			
			//englobe
			if (anGen==null && tipo == 2){
				throw new DAOException("La anotación " + anotaGeneradoraID.idAnotacion +  " del folio " + anotaGeneradoraID.idMatricula + ", Debe ser Definitiva.");
			}
			
			if (tipo== 1) {
				
				FolioDerivadoTMP relBor = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
				
				if(relBor != null){
					pm.deletePersistent(relBor);
				} 
				
				if (anDerTMP != null) {
					pm.deletePersistent(anDerTMP);
				} 
				
				TipoAnotacionEnhancedPk tipoEstandarID = new TipoAnotacionEnhancedPk();
				tipoEstandarID.idTipoAnotacion = CTipoAnotacion.ESTANDAR;

				TipoAnotacionEnhanced tipoEstandar = folDAO.getTipoAnotacion(tipoEstandarID, pm);

				if(tipoEstandarID==null){
					throw new DAOException("No encontró el tipo de anotación con ID: "+tipoEstandarID.idTipoAnotacion);
				}

				anGenTMP.setTipoAnotacion(tipoEstandar);
			}
			
			if (tipo== 2){
				
				FolioDerivadoTMP relBor = this.getFolioDerivadoTMP(anGenTMP, anDerTMP, pm);
				
				if(relBor != null){
					pm.deletePersistent(relBor);
				} 
				
				if (anGenTMP != null) {
					pm.deletePersistent(anGenTMP);
				} 
				
				TipoAnotacionEnhancedPk tipoEstandarID = new TipoAnotacionEnhancedPk();
				tipoEstandarID.idTipoAnotacion = CTipoAnotacion.ESTANDAR;

				TipoAnotacionEnhanced tipoEstandar = folDAO.getTipoAnotacion(tipoEstandarID, pm);

				if(tipoEstandarID==null){
					throw new DAOException("No encontró el tipo de anotación con ID: "+tipoEstandarID.idTipoAnotacion);
				}

				anDerTMP.setTipoAnotacion(tipoEstandar);
			}
			
			pm.currentTransaction().commit();


		} catch (JDOException e) {
		   if (pm.currentTransaction().isActive()) {
			   pm.currentTransaction().rollback();
		   }

		   Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
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
	 * Setea los tipos de anotación a la anotación generadora y a la derivada
	 * @param anGenTMP
	 * @param anDerTMP
	 * @param pm
	 */
	private void setTiposAnotaciones(AnotacionTMP anGenTMP, AnotacionTMP anDerTMP, JDOGenieFolioDAO folDAO, PersistenceManager pm) throws DAOException {
		TipoAnotacionEnhancedPk tipoGeneradoraID = new TipoAnotacionEnhancedPk();
		tipoGeneradoraID.idTipoAnotacion = CTipoAnotacion.GENERADORA;

		TipoAnotacionEnhanced tipoGeneradora = folDAO.getTipoAnotacion(tipoGeneradoraID, pm);

		if(tipoGeneradora==null){
			throw new DAOException("No encontró el tipo de anotación con ID: "+tipoGeneradoraID.idTipoAnotacion);
		}

		TipoAnotacionEnhancedPk tipoDerivadaID = new TipoAnotacionEnhancedPk();
		tipoDerivadaID.idTipoAnotacion = CTipoAnotacion.DERIVADO;

		TipoAnotacionEnhanced tipoDerivada = folDAO.getTipoAnotacion(tipoDerivadaID, pm);

		if(tipoDerivada==null){
			throw new DAOException("No encontró el tipo de anotación con ID: "+tipoGeneradoraID.idTipoAnotacion);
		}

		anGenTMP.setTipoAnotacion(tipoGeneradora);
		anDerTMP.setTipoAnotacion(tipoDerivada);

	}

	/**
	 * @param anGenTMP
	 * @param anDerTMP
	 * @param anGen
	 * @param anDer
	 * @param pm
	 */
	private void validarAnotacionesParaAsociacion(AnotacionTMP anGenTMP, AnotacionTMP anDerTMP, AnotacionEnhanced anGen, AnotacionEnhanced anDer, PersistenceManager pm) throws DAOException {


              String msg;
              msg = "<br />";

              if( null != anDerTMP ) {
                 msg += " [HIJA] (Folio:"  + anDerTMP.getIdMatricula() + "," + "Anotacion:" + anDerTMP.getOrden() + ")";
              }
              else if ( null != anDer ) {
                 msg += " [HIJA] (Folio:"  + anDer.getIdMatricula() + "," + "Anotacion:" + anDer.getOrden() + ")";
              }

              if( null != anGenTMP ) {
                 msg += " [PADRE] (Folio:"  + anGenTMP.getIdMatricula() + "," + "Anotacion:" + anGenTMP.getOrden()+ ")";
              }
              else if ( null != anGen ) {
                 msg += " [PADRE] (Folio:"  + anGen.getIdMatricula() + "," + "Anotacion:" + anGen.getOrden() + ")";
              }



		if(isCanceladora(anGen, anGenTMP, pm) || isCanceladora(anDer, anDerTMP, pm)){
			throw new DAOException("No puede asociar anotaciones canceladoras" + msg );
		}

		if(!isGeneradoraOrEstandar(anGen, anGenTMP, pm)){
			throw new DAOException("No puede asociar anotaciones, la anotación padre NO es generadora o estandar " + msg );
		}

		if(!isDerivadaOrEstandar(anDer, anDerTMP, pm)){

			throw new DAOException("No puede asociar anotaciones, la anotación hija NO es derivada o estandar " + msg);
		}

		if((hasPadres(anDer, anDerTMP, pm))&&(hasHijos(anGen, anGenTMP, pm))){
			throw new DAOException("No se pueden asociar anotaciones derivadas con padres y anotaciones generadoras con hijos" + msg);
		}

	}


	/**
	 * @param anDer
	 * @param object
	 * @param pm
	 */
	private boolean hasPadres(AnotacionEnhanced anDer, AnotacionTMP anDerTMP, PersistenceManager pm) throws DAOException {
		boolean rta = false;
		if(anDerTMP==null){
		//1. La anotación temporal no existe
			if(anDer==null){
				//No existen las dos anotaciones
				throw new DAOException("Las anotaciones no puede ser nulas");
			}
			//Solo existe la anotación definitiva, entonces se basa solo en ella
			rta = !anDer.getAnotacionesPadre().isEmpty();
		}
		else{
		//2. La anotación temporal existe
			if(anDer==null){
			//Solo existe la anotación temporal, entonces es nueva y se basa solo en ella
				rta = !anDerTMP.getAnotacionesPadreTMPs().isEmpty();
			}
			else{
			//Las dos anotaciones existen, se está aplicando un delta al definitivo
				if(anDerTMP.getAnotacionesPadreTMPs().isEmpty()){
				//Si la lista de anotaciones padre está vacía se mira solamente la anotación definitiva
					rta = !anDer.getAnotacionesPadre().isEmpty();
				}
				else{
				//Si existen padres temporales en la anot5ación se revisa si existe alguno
				//que NO tenga flag to delete en true
					FolioDerivadoTMP folioDerivadoTMP;
					for(Iterator it = anDerTMP.getAnotacionesPadreTMPs().iterator(); it.hasNext();){
						folioDerivadoTMP = (FolioDerivadoTMP) it.next();
						if(!folioDerivadoTMP.isToDelete()){
							rta = true;
						}
					}
					//Si todos los padres temporales tienen toDelete en true, se revisa si existe alguna
					//anotación definitiva que NO se esté borrando
					if(!rta){
						if(anDer.getAnotacionesPadre().size()>anDerTMP.getAnotacionesPadreTMPs().size()){
						//Existe un padre que NO se está eliminando
							rta = true;
						}
					}
				}
			}

		}
		return rta;
	}


	/**
	 * @param anGen
	 * @param object
	 * @param pm
	 */
	private boolean hasHijos(AnotacionEnhanced anGen, AnotacionTMP anGenTMP, PersistenceManager pm) throws DAOException {
		boolean rta = false;
		if(anGenTMP==null){
		//1. La anotación temporal no existe
			if(anGen==null){
				//No existen las dos anotaciones
				throw new DAOException("Las anotaciones no puede ser nulas");
			}
			//Solo existe la anotación definitiva, entonces se basa solo en ella
			rta = !anGen.getAnotacionesHijos().isEmpty();
		}
		else{
		//2. La anotación temporal existe
			if(anGen==null){
			//Solo existe la anotación temporal, entonces es nueva y se basa solo en ella
				rta = !anGenTMP.getAnotacionesHijosTMPs().isEmpty();
			}
			else{
			//Las dos anotaciones existen, se está aplicando un delta al definitivo
				if(anGenTMP.getAnotacionesHijosTMPs().isEmpty()){
				//Si la lista de anotaciones padre está vacía se mira solamente la anotación definitiva
					rta = !anGen.getAnotacionesHijos().isEmpty();
				}
				else{
				//Si existen hijos temporales en la anotación se revisa si existe alguno
				//que NO tenga flag to delete en true
					FolioDerivadoTMP folioDerivadoTMP;
					for(Iterator it = anGenTMP.getAnotacionesHijosTMPs().iterator(); it.hasNext();){
						folioDerivadoTMP = (FolioDerivadoTMP) it.next();
						if(!folioDerivadoTMP.isToDelete()){
							rta = true;
						}
					}
					//Si todos los hijos temporales tienen toDelete en true, se revisa si existe alguna
					//anotación definitiva que NO se esté borrando
					if(!rta){
						if(anGen.getAnotacionesHijos().size()>anGenTMP.getAnotacionesHijosTMPs().size()){
						//Existe un hijo que NO se está eliminando
							rta = true;
						}
					}
				}
			}

		}
		return rta;
	}

	/**
	 * Revisa si la anotación es generadora o estándar teniendo en cuenta los datos temporales
	 * que están siendo aplicados
	 * @param anGen
	 * @param anGenTMP
	 * @param pm
	 * @return
	 */
	private boolean isGeneradoraOrEstandar(AnotacionEnhanced anGen, AnotacionTMP anGenTMP, PersistenceManager pm) throws DAOException {
		boolean rta = false;
		if(anGenTMP!=null){
			rta = anGenTMP.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.GENERADORA) ||
				  anGenTMP.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.ESTANDAR);
		}
		else {
			if(anGen==null){
				throw new DAOException("Las anotaciones no puede ser nulas");
			}
			rta = anGen.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.GENERADORA) ||
			      anGen.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.ESTANDAR);
		}
		return rta;
	}

	/**
	 * Revisa si la anotación es canceladora teniendo en cuenta los posibles cambios
	 * que puedan haber en temporal
	 * @param anGen
	 * @param anGenTMP
	 * @param pm
	 * @return
	 */
	private boolean isCanceladora(AnotacionEnhanced anota, AnotacionTMP anotaTMP, PersistenceManager pm) throws DAOException {
		boolean rta = false;
		if(anotaTMP==null){

			if(anota == null){
				throw new DAOException("Las anotaciones no puede ser nulas");
			}

			rta = !(anota.getAnotacionesCancelacions().isEmpty());
		}
		else{
			if(anota == null){
				rta = !anotaTMP.getAnotacionesCancelacionTMPs().isEmpty();
			}
			else{
				if(anotaTMP.getAnotacionesCancelacionTMPs().isEmpty()){
					rta = !anota.getAnotacionesCancelacions().isEmpty();
				}
				else{
					CancelacionTMP cancelTMP;
					for(Iterator it = anotaTMP.getAnotacionesCancelacionTMPs().iterator(); it.hasNext();){
						cancelTMP = (CancelacionTMP) it.next();
						if(!cancelTMP.isToDelete()){
							rta = true;
						}
					}
					//Si todos las cancelaciones temporales tienen toDelete en true, se revisa si existe alguna
					//cancelación definitiva que NO se esté borrando
					if(!rta){
						if(anota.getAnotacionesCancelacions().size()>anotaTMP.getAnotacionesCancelacionTMPs().size()){
						//Existe una cancelación que NO se está eliminando
							rta = true;
						}
					}
				}
			}



		}
		return rta;
	}

	/**
	 * Retorna true si la anotación es derivada o estandar
	 * @param anDer
	 * @param pm
	 * @return
	 */
	private boolean isDerivadaOrEstandar(AnotacionEnhanced anDer, AnotacionTMP anDerTMP, PersistenceManager pm) throws DAOException {
		boolean rta = false;
		if(anDerTMP!=null){
			rta = anDerTMP.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.DERIVADO) ||
				  anDerTMP.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.ESTANDAR);
		}
		else {
			if(anDer==null){
				throw new DAOException("Las anotaciones no puede ser nulas");
			}
			rta = anDer.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.DERIVADO) ||
				  anDer.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.ESTANDAR);
		}
		return rta;
	}



	/**
	 * Retorna el folio derivado asociado a una relación entre un folio padre y un folio hijo, si la relación
	 * no existe retorna null
	 * @param folioPadreID
	 * @param folioHijoID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FolioDerivadoEnhanced getFolioDerivado(FolioPk folioPadreID, FolioPk folioHijoID, PersistenceManager pm)throws DAOException{
		FolioDerivadoEnhanced rta = null;
		try {
			Query query = pm.newQuery(FolioDerivadoEnhanced.class);
			query.declareParameters("String idMatriculaPadre, String idMatriculaHijo");
			query.setFilter("this.idMatricula==idMatriculaPadre &&\n"+
					"this.idMatricula1==idMatriculaHijo");
			Collection col = (Collection)query.executeWithArray(new Object[]{folioPadreID.idMatricula, folioHijoID.idMatricula});
			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				rta = (FolioDerivadoEnhanced) iter.next();
			} else {
				rta = null;
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}


	/**
	 * Retorna el folio derivado asociado a una relación entre un folio padre y un folio hijo, si la relación
	 * no existe retorna null
	 * @param folioPadreID
	 * @param folioHijoID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FolioDerivadoTMP getFolioDerivadoTMP(FolioPk folioPadreID, FolioPk folioHijoID, PersistenceManager pm)throws DAOException{
		FolioDerivadoTMP rta = null;
		try {
			Query query = pm.newQuery(FolioDerivadoTMP.class);
			query.declareParameters("String idMatriculaPadre, String idMatriculaHijo");
			query.setFilter("this.idMatricula==idMatriculaPadre &&\n"+
					"this.idMatricula1==idMatriculaHijo");
			Collection col = (Collection)query.executeWithArray(new Object[]{folioPadreID.idMatricula, folioHijoID.idMatricula});
			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				rta = (FolioDerivadoTMP) iter.next();
			} else {
				rta = null;
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}


	/**
	 * Devuelve la relación entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDPadre
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public FolioDerivado getFolioDerivadoEnlace(FolioPk folioIDPadre, FolioPk folioIDHijo, Usuario usuario) throws DAOException{
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		FolioDerivadoEnhanced aux = null;
		FolioDerivado rta = null;
		try{
			FolioEnhanced folioPadre = folDAO.getFolioByID(new FolioEnhancedPk(folioIDPadre), pm);
			if(folioPadre==null){
				throw new DAOException("El folio padre no existe");
			}

			FolioEnhanced folioHijo = folDAO.getFolioByID(new FolioEnhancedPk(folioIDHijo), pm);

			if(folioHijo==null){
				throw new DAOException("El folio hijo no existe");
			}

			if(usuario!=null){
				folDAO.validarBloqueoFolio(folioPadre, usuario, pm);
				folDAO.validarBloqueoFolio(folioHijo, usuario, pm);
			}

			FolioDerivadoEnhanced folDerDef = this.getFolioDerivado(folioIDPadre, folioIDHijo, pm);
			FolioDerivadoTMP folDerTMP = this.getFolioDerivadoTMP(folioIDPadre, folioIDHijo, pm);

			if(folDerDef==null){
				//La relación NO existe en definitivo
				if(folDerTMP!=null){
					//La relación existe en temporal
					aux = folDerTMP.getDefinitivo(new ArrayList());
				}
			}
			else{
				//La relación existe en definitivo
				if(folDerTMP!=null){
					//La relación existe también en temporal, se revisa si es para borrar también
					if(!folDerTMP.isToDelete()){
						aux	= folDerDef;
					}
				}
				else{
					aux	= folDerDef;
				}
			}

			if(aux!=null){
				pm.makeTransient(aux.getHijo());
				pm.makeTransient(aux.getPadre());
				pm.makeTransient(aux);
			}


		} catch (JDOException e) {
		   Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
		   throw new DAOException(e.getMessage(), e);
	   } catch (Throwable e) {
		   throw new DAOException(e.getMessage(), e);
	   }finally{
		   pm.close();
	   }

	 if(aux!=null){
	 	rta = (FolioDerivado)aux.toTransferObject();
	 }

   	return rta;
	}


	/**
	 * Retorna el folio derivado asociado a una relación entre un folio padre y un folio hijo, a partir del folio hijo.
	 * @param folioHijoID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FolioDerivadoEnhanced getFolioDerivadoHijo(FolioPk folioHijoID, PersistenceManager pm) throws DAOException {
		FolioDerivadoEnhanced rta = null;

		try {
			Query query = pm.newQuery(FolioDerivadoEnhanced.class);
			query.declareParameters("String idMatriculaHijo");
			query.setFilter("this.idMatricula1==idMatriculaHijo");
			Collection col = (Collection) query.executeWithArray(new Object[] { folioHijoID.idMatricula});
			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				rta = (FolioDerivadoEnhanced) iter.next();
			} else {
				rta = null;
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

		return rta;
	}

	/**
	 * Retorna el folio derivado temporal asociado a una relación entre un folio padre y un folio hijo, a partir del folio hijo.
	 * @param folioHijoID
	 * @param pm
	 * @return
	 * @throws DAOException
	 */
	protected FolioDerivadoTMP getFolioDerivadoHijoTMP(FolioPk folioHijoID, PersistenceManager pm) throws DAOException {
		FolioDerivadoTMP rta = null;
		try {
			Query query = pm.newQuery(FolioDerivadoTMP.class);
			query.declareParameters("String idMatriculaHijo");
			query.setFilter("this.idMatricula1==idMatriculaHijo");
			Collection col = (Collection) query.executeWithArray(new Object[] { folioHijoID.idMatricula});
			Iterator iter = col.iterator();

			if (iter.hasNext()) {
				rta = (FolioDerivadoTMP) iter.next();
			} else {
				rta = null;
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
		return rta;
	}

	/**
	 * Devuelve la relación entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDPadre
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public FolioDerivado getFolioDerivadoHijo(FolioPk folioIDHijo, Usuario usuario) throws DAOException {
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		FolioDerivadoEnhanced aux = null;
		FolioDerivado rta = null;
		try {

			FolioEnhanced folioHijo = folDAO.getFolioByID(new FolioEnhancedPk(folioIDHijo), pm);

			if (folioHijo == null) {
				throw new DAOException("El folio hijo no existe");
			}

			if (usuario != null) {
				folDAO.validarBloqueoFolio(folioHijo, usuario, pm);
			}

			FolioDerivadoEnhanced folDerDef = this.getFolioDerivadoHijo(folioIDHijo, pm);
			FolioDerivadoTMP folDerTMP = this.getFolioDerivadoHijoTMP(folioIDHijo, pm);

			if (folDerDef == null) {
				//La relación NO existe en definitivo
				if (folDerTMP != null) {
					//La relación existe en temporal
					aux = folDerTMP.getDefinitivo(new ArrayList());
				}
			} else {
				//La relación existe en definitivo
				if (folDerTMP != null) {
					//La relación existe también en temporal, se revisa si es para borrar también
					if (!folDerTMP.isToDelete()) {
						aux = folDerDef;
					}
				} else {
					aux = folDerDef;
				}
			}

			if (aux != null) {
				//pm.makeTransient(aux.getHijo());
				//pm.makeTransient(aux.getPadre());
				pm.makeTransient(aux);
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		if (aux != null) {
			rta = (FolioDerivado) aux.toTransferObject();
		}

		return rta;
	}
	 /**
	 * Devuelve la relación entre un folio padre y un folio hijo, si no existe retorna null.
	 * @param folioIDPadre
	 * @param folioIDHijo
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public FolioDerivado getFolioDerivadoHijoDireccion(FolioPk folioIDHijo, Usuario usuario) throws DAOException {
		JDOGenieFolioDAO folDAO = new JDOGenieFolioDAO();
		PersistenceManager pm = AdministradorPM.getPM();
		FolioDerivadoEnhanced aux = null;
		FolioDerivado rta = null;
		try {

			FolioEnhanced folioHijo = folDAO.getFolioByID(new FolioEnhancedPk(folioIDHijo), pm);

			if (folioHijo == null) {
				throw new DAOException("El folio hijo no existe");
			}

			if (usuario != null) {
				folDAO.validarBloqueoFolio(folioHijo, usuario, pm);
			}

			FolioDerivadoEnhanced folDerDef = this.getFolioDerivadoHijo(folioIDHijo, pm);
			FolioDerivadoTMP folDerTMP = this.getFolioDerivadoHijoTMP(folioIDHijo, pm); 

			if (folDerDef == null) {
				//La relación NO existe en definitivo
				if (folDerTMP != null) {
					//La relación existe en temporal
					aux = new FolioDerivadoEnhanced();
                                        aux.setArea(folDerTMP.getArea());
                                        aux.setHectareas(folDerTMP.getHectareas());
                                        aux.setMetros(folDerTMP.getMetros());
                                        aux.setCentimetros(folDerTMP.getCentimetros());
                                        aux.setDescripcion(folDerTMP.getDescripcion());
                                        aux.setIdAnotacion(folDerTMP.getIdAnotacionTmp());
                                        aux.setIdAnotacion1(folDerTMP.getIdAnotacion1Tmp());
                                        aux.setIdMatricula(folDerTMP.getIdMatricula());
                                        aux.setIdMatricula1(folDerTMP.getIdMatricula1());
                                        aux.setPorcentaje(folDerTMP.getPorcentaje());
                                        aux.setLote(folDerTMP.getLote());
				}
			} else {
				//La relación existe en definitivo
				if (folDerTMP != null) {
					//La relación existe también en temporal, se revisa si es para borrar también
					if (!folDerTMP.isToDelete()) {
						aux = folDerDef;
					}
				} else {
					aux = folDerDef;
				}
			}

			if (aux != null) {
				//pm.makeTransient(aux.getHijo());
				//pm.makeTransient(aux.getPadre());
				pm.makeTransient(aux);
			}

		} catch (JDOException e) {
			Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			pm.close();
		}

		if (aux != null) {
			rta = (FolioDerivado) aux.toTransferObject();
		}

		return rta;
	}
	
    /**
     * Retorna una lista de folios derivados temporales para una matrícula padre
     * @param matriculaPadre
     * @return
     * @throws DAOException
     */
    public List getFoliosDerivadosTMPByMatricula(String matriculaPadre)throws DAOException {
        FolioDerivadoTMP fd;
        List rta = new ArrayList();
        PersistenceManager pm = AdministradorPM.getPM();
        try {
        	FolioEnhancedPk folioID = new FolioEnhancedPk();
        	folioID.idMatricula = matriculaPadre;
            Query query = pm.newQuery(FolioDerivadoTMP.class);
            query.setOrdering("this.hijoTmp.folio.ordenLPAD ascending");
            query.declareParameters("String matriculaPadre");
            query.setFilter("this.idMatricula == matriculaPadre && this.toDelete==0");
            Collection col = (Collection) query.execute(matriculaPadre);
            for (Iterator iter = col.iterator(); iter.hasNext();) {
                fd = (FolioDerivadoTMP) iter.next();
                rta.add(fd);
            }
            query.closeAll();
        } catch (JDOObjectNotFoundException e) {
        	Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
        	Log.getInstance().error(JDOGenieFolioDerivadoDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return rta;
    }


}
