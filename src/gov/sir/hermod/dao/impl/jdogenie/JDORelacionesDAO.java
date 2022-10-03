 package gov.sir.hermod.dao.impl.jdogenie;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.RelacionPk;
import gov.sir.core.negocio.modelo.TipoRelacion;
import gov.sir.core.negocio.modelo.TipoRelacionPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoRelacion;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoRelacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RelacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.RelacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoRelacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.RelacionesDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class JDORelacionesDAO implements RelacionesDAO {

	/**
     * @see gov.sir.hermod.dao.RelacionesDAO#buscarFasesRelacionadasPorRelacionId(String)
     */
    public List buscarFasesRelacionadasPorRelacionId( String relacionId ) throws DAOException {

      PersistenceManager pm = AdministradorPM.getPM();
      JDOFasesDAO fasesDAO = new JDOFasesDAO();

      List fasesRelacionadas = null;

      try {
        pm.currentTransaction().begin();
        Query query = pm.newQuery(RelacionEnhanced.class);

        query.declareParameters("String idRelacion");
        query.setFilter("this.idRelacion == idRelacion");

        fasesRelacionadas = new ArrayList();

        Collection col = (Collection)query.execute(relacionId);

        RelacionEnhanced relacionEnhanced; // element
        Fase        fase;
        String      faseId;

        for( Iterator iter = col.iterator(); iter.hasNext(); ) {
            relacionEnhanced = (RelacionEnhanced)iter.next();
            faseId = relacionEnhanced.getIdFase();
            
            // deletated to fasesDao
            fase = fasesDAO.getFaseById( faseId );

            fasesRelacionadas.add( fase );
        }

        query.closeAll();
        
        pm.currentTransaction().commit();
      } catch (JDOException e) {
    	  if(pm.currentTransaction().isActive()) {
    		  pm.currentTransaction().rollback();
    	  }
    	  Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
    	  throw new DAOException(e.getMessage(), e);
      } catch (Throwable e) {
    	  if(pm.currentTransaction().isActive()) {
    		  pm.currentTransaction().rollback();
    	  }
    	  Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
    	  throw new DAOException(e.getMessage(), e);
      } finally {
    	  if(pm != null && !pm.isClosed())
    		  pm.close();
      }

      return fasesRelacionadas;
    }


	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#getTiposRelacionesFase(String)
	 */
	public List getTiposRelacionesFase(String idFase) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		List tiposRelaciones = null;

		try {
			pm.currentTransaction().begin();
			Query query = pm.newQuery(TipoRelacionEnhanced.class);
			query.declareParameters("String idFase");
			query.setFilter("this.idFase == idFase");

			Collection col = (Collection)query.execute(idFase);

			TipoRelacionEnhanced tipoRelacion;

			tiposRelaciones = new ArrayList();

			for(Iterator itRelaciones = col.iterator(); itRelaciones.hasNext();) {
				tipoRelacion = (TipoRelacionEnhanced)itRelaciones.next();
				pm.makeTransient(tipoRelacion);
				tiposRelaciones.add(tipoRelacion.toTransferObject());
			}

			query.closeAll();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		return tiposRelaciones;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#getTiposRelaciones()
	 */
	public List getTiposRelaciones() throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		List tiposRelaciones = null;

		try {
			pm.currentTransaction().begin();
			Query query = pm.newQuery(TipoRelacionEnhanced.class);

			Collection col = (Collection)query.execute();

			TipoRelacionEnhanced tipoRelacion;

			tiposRelaciones = new ArrayList();

			for(Iterator itRelaciones = col.iterator(); itRelaciones.hasNext();) {
				tipoRelacion = (TipoRelacionEnhanced)itRelaciones.next();
				pm.makeTransient(tipoRelacion);
				tiposRelaciones.add(tipoRelacion.toTransferObject());
			}

			query.closeAll();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		return tiposRelaciones;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#getTurnosParaRelacion(Proceso, Fase, Circulo, TipoRelacion)
	 */
	public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		List turnosParaRelacion = null;
		boolean isMesa = false;
		String respuesta = "VACIO";
		String respuesta2 = "VACIO";
		String respuesta3 = "VACIO";
		String fase2 = CFase.COR_CORRECCION_SIMPLE;
		String fase3 = CFase.ANT_REVISION_INICIAL;
		String fase4 = CFase.PMY_CUSTODIA;
		String fase5 = CFase.COR_ACT_ADMIN;

		try {
			pm.currentTransaction().begin();
			// Se deben obtener todos aquellos turnos que no estén relacionados con el
			// tipo de relacion dado, en la fase dada, el proceso dado, y el círculo dado.
			// Si los turnos ya están relacionados, y el tipo de relación es el de
			// confrontación a antiguo sistema, se debe verificar que la relación de
			// confrontación sea la relación actual. También debe verificarse que no exista
			// una relación siguiente en el historial del turno
			Query query = pm.newQuery(TurnoEnhanced.class);
			query.declareVariables("TurnoHistoriaEnhanced th");
			query.declareParameters("Long proceso, String fase, String circulo, String respuesta, String respuesta2," +
					"String respuesta3, String fase2, String fase3, String fase4, String fase5");
			query.setOrdering("fechaInicio ascending");

			if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA) ||
					tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO) ||
					tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_CONFRONTACION) ) {

				query.setFilter("this.idProceso == proceso &&\n"+
				        "this.idFase == fase && \n"+
				        "this.idCirculo == circulo && \n"+
				        "this.idRelacionActual == null");
				
				/*query.declareVariables("RelacionEnhanced rel;TurnoHistoriaEnhanced th;TipoRelacionEnhanced tipoRel");
				query.setFilter("this.idProceso == proceso && " +
				        "this.idFase == fase && this.idCirculo == circulo &&" +
				        "this.idRelacionActual != null && this.idFaseRelacionActual != null && " +
				        "this.idRelacionActual == rel.idRelacion && " +
				        "this.idFaseRelacionActual == rel.idFase && " +
				        "rel.tipoRelacion == tipoRel && tipoRel.idTipoRelacion == \"" +
				        CTipoRelacion.ID_DOCUMENTOS_A_CONFRONTACION + "\" && " +
				        "this.historial.contains(th) && th.idRelacionSiguiente == null && " +
				        "th.idRelacion == this.idRelacionActual && " +
				        "th.fase == this.idFaseRelacionActual");*/
				
			} else if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CORRECCIONES_DESANOTADAS) ||
					tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CORRECCIONES_DEVUELTAS_AL_PUBLICO) ||
					tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_SOLICITUDES_DE_CORRECCIONES)) {
				/*query.setFilter("this.idFase == fase && this.idCirculo == circulo && " +
						"((this.idRelacionActual == null && this.idFaseRelacionActual == null) || " +
						"this.idFaseRelacionActual != fase)");*/
				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CORRECCIONES_DESANOTADAS)){
					respuesta = CRespuesta.CONFIRMAR;
				} else {
					if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CORRECCIONES_DEVUELTAS_AL_PUBLICO)){
						respuesta = CRespuesta.NEGAR;
					}
				}
				if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_SOLICITUDES_DE_CORRECCIONES))  {
					query.setFilter("this.idFase == fase && \n"+
					        "this.idCirculo == circulo && \n"+
					        "this.idRelacionActual == null && \n "+
					        "!(this.historial.contains(th) &&\n" + "(th.fase==fase2 || "+
					        "th.fase==fase3 || th.fase==fase4 || th.fase==fase5))");
				} else {
					query.setFilter("this.idFase == fase && this.idCirculo == circulo && " +
							"((this.idRelacionActual == null && this.idFaseRelacionActual == null) || " +
							"this.idFaseRelacionActual != fase) && this.ultimaRespuesta  == respuesta");
				}
			}else {

				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_INSCRITOS_PARA_FIRMA) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DEVUELTOS_PARA_FIRMA) ||
						tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_PARA_CUSTODIA_MAYOR_VALOR) ||
						 /**
                                                   * @author      :   Carlos Torres
                                                   * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                                                   */
                                                tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_REGISTRO)||
                                                tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_CORRESPONDIENCIA)||
                                                tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA)) {
					
					
					isMesa = true;
					boolean isParcial = false;
					
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_INSCRITOS_PARA_FIRMA)){
						respuesta = CRespuesta.OK;
						respuesta2 = CRespuesta.CONFIRMAR;
						isParcial = true;
					}
					
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA)){
						respuesta = CRespuesta.INSCRIPCION_PARCIAL;
						isParcial = true;
					}
					
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DEVUELTOS_PARA_FIRMA)){
						respuesta = CRespuesta.DEVOLUCION.toString();
						respuesta2 = CRespuesta.NEGAR;
					}
					
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_PARA_CUSTODIA_MAYOR_VALOR)){
						respuesta = CRespuesta.MAYOR_VALOR;
					}
					
					if (isParcial){
						query.setFilter("this.idProceso == proceso &&\n"+
						        "this.idFase == fase && \n"+
						        "this.idCirculo == circulo &&\n"+
						        "(this.ultimaRespuesta  == respuesta || this.ultimaRespuesta  == respuesta2 || this.ultimaRespuesta  == respuesta3) ");
					} else{ 
						
						query.setFilter("this.idProceso == proceso &&\n"+
						        "this.idFase == fase && \n"+
						        "this.idCirculo == circulo &&\n"+
								"(this.ultimaRespuesta  == respuesta || this.ultimaRespuesta  == respuesta2) ");
					}
				} else {
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_PARA_ANTIGUO_SISTEMA)) {
						query.setFilter("this.idProceso == proceso &&\n"+
						        "this.idFase == fase && \n"+
						        "this.idCirculo == circulo && \n"+
						        "this.idRelacionActual == null");
						
					} else {
						if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_DEVUELTO) ||
								tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_EXPEDIDO) ||
								tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_DE_ENTREGA_DEVUELTO)) {
							
							boolean isExpedido = false;
							
							if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_EXPEDIDO)){
								respuesta =  CRespuesta.CREADO;
								respuesta2 = CRespuesta.EXISTE;
								isExpedido = true;
							}
							
							if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_ENTREGA_DEVUELTO)){
								respuesta = CRespuesta.RECHAZADO;
							}
							
							if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_DE_ENTREGA_DEVUELTO)){
								respuesta = CRespuesta.NEGAR;
							}
							
							if (isExpedido){
								query.setFilter("this.idProceso == proceso &&\n"+
								        "this.idFase == fase && \n"+
								        "this.idCirculo == circulo &&\n"+
						        		"(this.ultimaRespuesta  == respuesta || this.ultimaRespuesta  == respuesta2) ");
							} else{
								query.setFilter("this.idProceso == proceso &&\n"+
								        "this.idFase == fase && \n"+
								        "this.idCirculo == circulo &&\n"+
										"this.ultimaRespuesta  == respuesta ");
							}
						} else {
							if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_REPARTO)) {
								query.setFilter("this.idProceso == proceso &&\n"+
								        "this.idFase == fase && \n"+
								        "this.idCirculo == circulo");
							} else {
								if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_INSCRITOS) ||
									tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_DEVUELTOS)) {
									//Se consultan todos los turnos que esten en certificados asociados con acto de embargo
									//y que cumpla si es devuelto o inscrito
									query.setFilter("this.idProceso == proceso && this.idFase == fase && " +
											"this.idCirculo == circulo ");	
									
								} else {
									query.setFilter("this.idProceso == proceso && this.idFase == fase && " +
											"this.idCirculo == circulo && ((this.idRelacionActual == null && " +
											"this.idFaseRelacionActual == null) || (this.idFaseRelacionActual != fase))");	
								}
									
							}
							
						}	
					}
					
				}
			}
			
			Collection col = 
				(Collection)query.executeWithArray(new Object[]{new Long(proceso.getIdProceso()),
						fase.getID(), 
						circulo.getIdCirculo(),
						respuesta, respuesta2, respuesta3, fase2,
						fase3, fase4, fase5});
			
			TurnoEnhanced turno;

			turnosParaRelacion = new ArrayList();
			
			if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA)) {
				// Se debe realizar la busqueda sobre todos los turnos
				// la condicion para antiguo sistema es que tenga subtipo de Atencion 2
				for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
					turno = (TurnoEnhanced)itTurnos.next();
					
					if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
					if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
						SolicitudRegistroEnhanced solRegistro = (SolicitudRegistroEnhanced) turno.getSolicitud();
						if (solRegistro.getSubtipoSolicitud().getIdSubtipoSol().equals(CSubtipoSolicitud.ANTIGUO_SISTEMA)){
							pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
							pm.makeTransient(turno.getSolicitud());
							pm.makeTransient(turno);
							turnosParaRelacion.add(turno);
						}
					}
				}
				}
			} else {
				if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO)) {
					// Se debe realizar la busqueda sobre todos los turnos
					// la condicion para antiguo sistema es que tenga subtipo de Atencion 2
					for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
						turno = (TurnoEnhanced)itTurnos.next();
						
						if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
						if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
							SolicitudRegistroEnhanced solRegistro = (SolicitudRegistroEnhanced) turno.getSolicitud();
							LiquidacionTurnoRegistroEnhanced ligRegistro = (LiquidacionTurnoRegistroEnhanced) solRegistro.getLiquidaciones().get(0);
							List actos = ligRegistro.getActos();
							boolean isTestamentos = this.tieneTestamentos(actos);
							if (solRegistro.getSubtipoSolicitud().getIdSubtipoSol().equals(CSubtipoSolicitud.TESTAMENTO) && isTestamentos 
								){
								pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
								pm.makeTransient(turno.getSolicitud());
								pm.makeTransient(turno);
								turnosParaRelacion.add(turno);
							}
						}
					}
					}
				} else {
					if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_CONFRONTACION)) {
						// Se debe realizar la busqueda sobre todos los turnos
						// la condicion para antiguo sistema es que tenga subtipo de Atencion 2
						for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
							turno = (TurnoEnhanced)itTurnos.next();
							if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
							if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
								SolicitudRegistroEnhanced solRegistro = (SolicitudRegistroEnhanced) turno.getSolicitud();
								if (solRegistro.getSubtipoSolicitud().getIdSubtipoSol().equals(CSubtipoSolicitud.NORMAL)){
									pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
									pm.makeTransient(turno.getSolicitud());
									pm.makeTransient(turno);
									turnosParaRelacion.add(turno);
								}
							}
						}
						}
					} else {
						if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_A_REPARTO)){
							//No deben tener la fase de Calificacion						
							for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
								turno = (TurnoEnhanced)itTurnos.next();
								List historiales = new ArrayList();
								historiales = turno.getHistorials();
								TurnoHistoriaEnhanced turnoHistoria;
								boolean isCalificacion = false;
								
								if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
									for(Iterator itHistorialesTurnos = historiales.iterator(); itHistorialesTurnos.hasNext();) {
										turnoHistoria = (TurnoHistoriaEnhanced)itHistorialesTurnos.next();
										if (turnoHistoria.getFase().equals(CFase.CAL_CALIFICACION)) {
											isCalificacion = true;
										}
									}
									
									if (!isCalificacion) {
										if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
											pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
											pm.makeTransient(turno.getSolicitud());
										}
										pm.makeTransient(turno);
										turnosParaRelacion.add(turno);	
									}								
								}	
							}	
							
						} else {
							if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_INSCRITOS) ||
								tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_DEVUELTOS)){
//								No deben tener la fase de Calificacion	
								for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
									turno = (TurnoEnhanced)itTurnos.next();
									List historiales = new ArrayList();
									historiales = turno.getHistorials();
									TurnoHistoriaEnhanced turnoHistoria;
									boolean isTieneActosEmbargo = false;
									SolicitudRegistroEnhanced solEnhanced = (SolicitudRegistroEnhanced) turno.getSolicitud() ;
									List liq = solEnhanced.getLiquidaciones();
									LiquidacionTurnoRegistroEnhanced liquidacion = (LiquidacionTurnoRegistroEnhanced) liq.get(0);
									
									List actos = liquidacion.getActos();
									for(Iterator itActos = actos.iterator(); itActos.hasNext();) {
										ActoEnhanced aEnhanced = (ActoEnhanced) itActos.next();
										if (aEnhanced.getTipoActo().getIdTipoActo().equals(CActo.EMBARGO)){
											isTieneActosEmbargo = true;
										}
									}
									
									if (isTieneActosEmbargo){
										if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
											for(Iterator itHistorialesTurnos = historiales.iterator(); itHistorialesTurnos.hasNext();) {
												turnoHistoria = (TurnoHistoriaEnhanced)itHistorialesTurnos.next();
												if (turnoHistoria.getFase().equals(CFase.CAL_CALIFICACION)) {
													if (tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_EMBARGO_INSCRITOS)){
														if (turnoHistoria.getRespuesta()!= null && 
															(turnoHistoria.getRespuesta().equals(CRespuesta.OK) || turnoHistoria.getRespuesta().equals(CRespuesta.INSCRIPCION_PARCIAL) ) ){
															if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
																pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
																pm.makeTransient(turno.getSolicitud());
															}
															pm.makeTransient(turno);
															turnosParaRelacion.add(turno);
														}
													} else {
														if (turnoHistoria.getRespuesta()!= null && turnoHistoria.getRespuesta().equals(CRespuesta.DEVOLUCION.toString())){
																if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
																	pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
																	pm.makeTransient(turno.getSolicitud());
																}
																pm.makeTransient(turno);
																turnosParaRelacion.add(turno);
															}
													}
												}
											}						
										}
									}
								}
                                                                 /**
                                                                   * @author      :   Carlos Torres
                                                                   * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                                                                  **/
							} else if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_REGISTRO)||
                                                                  tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_CORRESPONDIENCIA)){
                                                                  
                                                               }else{
								for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
									turno = (TurnoEnhanced)itTurnos.next();
									
									if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
									if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
										pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
										pm.makeTransient(turno.getSolicitud());
									}
									pm.makeTransient(turno);
									turnosParaRelacion.add(turno);
								}	
							}	
						}
						}
								
					}
				}
					
			}

			

			query.closeAll();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		int numTurnos = (turnosParaRelacion == null ? 0 : turnosParaRelacion.size());
		for(int i = 0; i < numTurnos; i++) {
			TurnoEnhanced turno = (TurnoEnhanced)turnosParaRelacion.get(i);
			turnosParaRelacion.set(i, turno.toTransferObject());
		}

		return turnosParaRelacion;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#crearRelacion(TipoRelacion, Usuario)
	 */
	public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		RelacionEnhanced relacionEnh = null;
		Relacion relacion = null;

		try {
			pm.currentTransaction().begin();

			// Se crea la relación
			TipoRelacionEnhancedPk idTipoRelacionEnh = new TipoRelacionEnhancedPk();
			idTipoRelacionEnh.idTipoRelacion = tipoRelacion.getIdTipoRelacion();

			TipoRelacionEnhanced tipoRelacionEnh = (TipoRelacionEnhanced)pm.getObjectById(idTipoRelacionEnh, true);

			relacionEnh = new RelacionEnhanced();
			relacionEnh.setIdRelacion(getNextIdRelacion(circulo, pm));
			relacionEnh.setIdFase(tipoRelacion.getIdFase());
			relacionEnh.setFecha(new Date());
			relacionEnh.setTipoRelacion(tipoRelacionEnh);

			// Se hace persistente la relación
			pm.makePersistent(relacionEnh);
			pm.currentTransaction().commit();

			pm.makeTransient(relacionEnh);
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		relacion = (Relacion)relacionEnh.toTransferObject();

		return relacion;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#crearRelacion(TipoRelacion, Usuario, List)
	 */
	public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos)
	throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		RelacionEnhanced relacionEnh = null;
		Relacion relacion = null;

		try {
			pm.currentTransaction().begin();

			// Se crea la relación
			TipoRelacionEnhancedPk idTipoRelacionEnh = new TipoRelacionEnhancedPk();
			idTipoRelacionEnh.idTipoRelacion = tipoRelacion.getIdTipoRelacion();

			TipoRelacionEnhanced tipoRelacionEnh = (TipoRelacionEnhanced)pm.getObjectById(idTipoRelacionEnh, true);

			UsuarioEnhancedPk idUsuarioEnh = new UsuarioEnhancedPk();
			idUsuarioEnh.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(idUsuarioEnh, true);

			relacionEnh = new RelacionEnhanced();
			relacionEnh.setIdRelacion(getNextIdRelacion(circulo, pm));
			relacionEnh.setIdFase(tipoRelacion.getIdFase());
			relacionEnh.setFecha(new Date());
			relacionEnh.setUsuario(usuarioEnh);
			relacionEnh.setTipoRelacion(tipoRelacionEnh);

			pm.makePersistent(relacionEnh);

			Turno turno;
			TurnoEnhancedPk turnoEnhId = new TurnoEnhancedPk();
			TurnoEnhanced turnoEnh;

			// Los tipos de relacion de confrontación a antiguo sistema, y de confrontación a testamentos
			// deben considerarse como casos especiales
			if(idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA) ||
					idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO)) {

				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

					// Se obtiene el turno de la base de datos
					turno = (Turno)itTurnos.next();
					turnoEnhId.anio = turno.getAnio();
					turnoEnhId.idCirculo = turno.getIdCirculo();
					turnoEnhId.idProceso = turno.getIdProceso();
					turnoEnhId.idTurno = turno.getIdTurno();
					turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

					// Se actualiza su historial
					List historial = turnoEnh.getHistorials();
					TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
					turnoHistoriaEnh.setIdRelacionSiguiente(relacionEnh.getIdRelacion());
					pm.makePersistent(turnoHistoriaEnh);
				}
			} else {

				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

					// Se obtiene el turno de la base de datos
					turno = (Turno)itTurnos.next();
					turnoEnhId.anio = turno.getAnio();
					turnoEnhId.idCirculo = turno.getIdCirculo();
					turnoEnhId.idProceso = turno.getIdProceso();
					turnoEnhId.idTurno = turno.getIdTurno();
					turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

					// Se actualiza su relación
					turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
					turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());

					// Se actualiza su historial
					List historial = turnoEnh.getHistorials();
					TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
					turnoHistoriaEnh.setIdRelacion(relacionEnh.getIdRelacion());
					pm.makePersistent(turnoHistoriaEnh);

					pm.makePersistent(turnoEnh);
				}
			}

			pm.currentTransaction().commit();

			pm.makeTransient(relacionEnh.getTipoRelacion());
			pm.makeTransient(relacionEnh);
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		relacion = (Relacion)relacionEnh.toTransferObject();

		return relacion;
	}
	
	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#crearRelacionNuevo(TipoRelacion, Usuario, List, String)
	 */
	public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String respuesta)
	throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		RelacionEnhanced relacionEnh = null;
		Relacion relacion = null;

		try {
			pm.currentTransaction().begin();

			// Se crea la relación
			TipoRelacionEnhancedPk idTipoRelacionEnh = new TipoRelacionEnhancedPk();
			idTipoRelacionEnh.idTipoRelacion = tipoRelacion.getIdTipoRelacion();

			TipoRelacionEnhanced tipoRelacionEnh = (TipoRelacionEnhanced)pm.getObjectById(idTipoRelacionEnh, true);

			UsuarioEnhancedPk idUsuarioEnh = new UsuarioEnhancedPk();
			idUsuarioEnh.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(idUsuarioEnh, true);

			relacionEnh = new RelacionEnhanced();
			relacionEnh.setIdRelacion(getNextIdRelacion(circulo, pm));
			relacionEnh.setIdFase(tipoRelacion.getIdFase());
			relacionEnh.setFecha(new Date());
			relacionEnh.setUsuario(usuarioEnh);
			relacionEnh.setTipoRelacion(tipoRelacionEnh);
			relacionEnh.setRespuesta(respuesta);

			pm.makePersistent(relacionEnh);

			Turno turno;
			TurnoEnhancedPk turnoEnhId = new TurnoEnhancedPk();
			TurnoEnhanced turnoEnh;

			// Los tipos de relacion de confrontación a antiguo sistema, y de confrontación a testamentos
			// deben considerarse como casos especiales
			if(idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_CORRECCIONES_DESANOTADAS) ||
					idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_CORRECCIONES_DEVUELTAS_AL_PUBLICO)) {

				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

					// Se obtiene el turno de la base de datos
					turno = (Turno)itTurnos.next();
					turnoEnhId.anio = turno.getAnio();
					turnoEnhId.idCirculo = turno.getIdCirculo();
					turnoEnhId.idProceso = turno.getIdProceso();
					turnoEnhId.idTurno = turno.getIdTurno();
					turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

					// Se actualiza su relación
					turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
					turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());
					
					// Se actualiza su historial
					List historial = turnoEnh.getHistorials();
					TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
					//turnoHistoriaEnh.setIdRelacionSiguiente(relacionEnh.getIdRelacion());
					turnoHistoriaEnh.setIdRelacion(relacionEnh.getIdRelacion());
					pm.makePersistent(turnoHistoriaEnh);
				}
			} else {
				if(idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_CERTIFICADO_PARA_ANTIGUO_SISTEMA) 
				   || idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_SOLICITUDES_DE_CORRECCIONES)
				   || idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_DE_ENTREGA_DEVUELTO)) {
					
					
					for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

						// Se obtiene el turno de la base de datos
						turno = (Turno)itTurnos.next();
						turnoEnhId.anio = turno.getAnio();
						turnoEnhId.idCirculo = turno.getIdCirculo();
						turnoEnhId.idProceso = turno.getIdProceso();
						turnoEnhId.idTurno = turno.getIdTurno();
						turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

						// Se actualiza su relación
						turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
						turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());
						
						// Se actualiza su historial
						List historial = turnoEnh.getHistorials();
						TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
						turnoHistoriaEnh.setIdRelacionSiguiente(relacionEnh.getIdRelacion());
						pm.makePersistent(turnoHistoriaEnh);
					}
					
				} else {

					for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {
	
						// Se obtiene el turno de la base de datos
						turno = (Turno)itTurnos.next();
						turnoEnhId.anio = turno.getAnio();
						turnoEnhId.idCirculo = turno.getIdCirculo();
						turnoEnhId.idProceso = turno.getIdProceso();
						turnoEnhId.idTurno = turno.getIdTurno();
						turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);
	
						// Se actualiza su relación
						turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
						turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());
	
						// Se actualiza su historial
						List historial = turnoEnh.getHistorials();
						TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 2);
						turnoHistoriaEnh.setIdRelacion(relacionEnh.getIdRelacion());
						pm.makePersistent(turnoHistoriaEnh);
	
						pm.makePersistent(turnoEnh);
					}
				}
			}

			pm.currentTransaction().commit();

			pm.makeTransient(relacionEnh.getTipoRelacion());
			pm.makeTransient(relacionEnh);
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		relacion = (Relacion)relacionEnh.toTransferObject();

		return relacion;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#addTurnoToRelacion(Relacion, Turno)
	 */
	public Relacion addTurnoToRelacion(Relacion relacion, Turno turno) throws DAOException {

		return null;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#getTipoRelacion(TipoRelacionPk)
	 */
	public TipoRelacion getTipoRelacion(TipoRelacionPk idTipoRelacion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		TipoRelacion tipoRelacion = null;

		try {
			pm.currentTransaction().begin();
			TipoRelacionEnhancedPk idTipoRelacionEnh = new TipoRelacionEnhancedPk(idTipoRelacion);
			TipoRelacionEnhanced tipoRelacionEnh = (TipoRelacionEnhanced)pm.getObjectById(idTipoRelacionEnh, true);
			pm.makeTransient(tipoRelacionEnh);
			tipoRelacion = (TipoRelacion)tipoRelacionEnh.toTransferObject();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		return tipoRelacion;
	}

	/**
	 * Obtiene el siguiente identificador válido para una nueva relación a crear. El año
	 * del círculo de relación se obtiene de la fecha actual del sistema.
	 * @param circulo El círculo al que corresponde la relación
	 * @param pm El PersistenceManager que participa en la transacción actual
	 * @return El identificador de la nueva relación.
	 */
	private String getNextIdRelacion(Circulo circulo, PersistenceManager pm) {

		// Se obtiene el año para el cual se quiere el consecutivo
		Date fechaActual = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		int anio = calendar.get(Calendar.YEAR);

		return getNextIdRelacion(circulo, "" + anio, pm);
	}

	/**
	 * Obtiene el siguiente identificador válido para una nueva relación a crear.
	 * @param circulo El círculo al que corresponde la relación
	 * @param pm El PersistenceManager que participa en la transacción actual
	 * @return El identificador de la nueva relación.
	 */
	private String getNextIdRelacion(Circulo circulo, String anio, PersistenceManager pm) {

		// Se obtiene el último id de relación utilizado
		CirculoRelacionEnhancedPk idCirculoRelacionEnh = new CirculoRelacionEnhancedPk();
		idCirculoRelacionEnh.anio = anio;
		idCirculoRelacionEnh.idCirculo = circulo.getIdCirculo();

		// Se obtiene el círculo de proceso para así poder determinar el consecutivo para
		// el identificador de la relación
		CirculoRelacionEnhanced circuloRelacionEnh = null;

		try {
			circuloRelacionEnh = (CirculoRelacionEnhanced)pm.getObjectById(idCirculoRelacionEnh, true);
		} catch(JDOObjectNotFoundException ex) {
			// Si el círculo de relación no existe, se crea.
			circuloRelacionEnh = crearCirculoRelacion(anio, circulo, pm);
		}

		long nextIdRelacion = circuloRelacionEnh.getLastIdRelacion() + 1;
		circuloRelacionEnh.setLastIdRelacion(nextIdRelacion);

		pm.makePersistent(circuloRelacionEnh);

		StringBuffer idRelacion = new StringBuffer(anio);
		idRelacion.append('-');
		idRelacion.append(circulo.getIdCirculo());
		idRelacion.append('-');
		idRelacion.append(nextIdRelacion);

		return idRelacion.toString();
	}

	/**
	 * Crea un nuevo CirculoRelacionEnhanced para el año y círculo registral suministrados.
	 * @param anio El año del CirculoRelacionEnhanced
	 * @param circulo El círculo del CirculoRelacionEnhanced
	 * @param pm El PersistenceManager de la transacción actual
	 * @return El CirculoRelacionEnhanced creado
	 */
	private CirculoRelacionEnhanced crearCirculoRelacion(String anio, Circulo circulo, PersistenceManager pm) {

		CirculoRelacionEnhanced circuloRelacionEnh = new CirculoRelacionEnhanced();

		circuloRelacionEnh.setAnio(anio);
		circuloRelacionEnh.setIdCirculo(circulo.getIdCirculo());
		circuloRelacionEnh.setLastIdRelacion(0);
		circuloRelacionEnh.setFechaCreacion(new Date());
		pm.makePersistent(circuloRelacionEnh);

		return circuloRelacionEnh;
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#setNotaToRelacion(TipoRelacionPk, String)
	 */
	public void setNotaToRelacion(RelacionPk idRelacion, String nota) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		RelacionEnhanced relacionEnh = null;

		try {
			pm.currentTransaction().begin();

			RelacionEnhancedPk idRelacionEnh = new RelacionEnhancedPk(idRelacion);

			// Se obtiene la relación
			relacionEnh = (RelacionEnhanced)pm.getObjectById(idRelacionEnh, true);
			relacionEnh.setNota(nota);

			pm.makePersistent(relacionEnh);

			pm.currentTransaction().commit();

		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}
	}

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#getRelacion(gov.sir.core.negocio.modelo.Relacion.RelacionPk)
	 */
	public Relacion getRelacion(gov.sir.core.negocio.modelo.RelacionPk idRelacion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		Relacion relacion = null;

		try {
			pm.currentTransaction().begin();
			RelacionEnhancedPk idRelacionEnh = new RelacionEnhancedPk(idRelacion);
			RelacionEnhanced relacionEnh = (RelacionEnhanced)pm.getObjectById(idRelacionEnh, true);
			pm.makeTransient(relacionEnh.getTipoRelacion());
			pm.makeTransient(relacionEnh);
			relacion = (Relacion)relacionEnh.toTransferObject();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		return relacion;
	}
	

	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#crearRelacion(TipoRelacion, Usuario, Circulo, List, String)
	 */
	public Relacion crearRelacion(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		RelacionEnhanced relacionEnh = null;
		Relacion relacion = null;

		try {
			pm.currentTransaction().begin();

			// Se crea la relación
			TipoRelacionEnhancedPk idTipoRelacionEnh = new TipoRelacionEnhancedPk();
			idTipoRelacionEnh.idTipoRelacion = tipoRelacion.getIdTipoRelacion();

			TipoRelacionEnhanced tipoRelacionEnh = (TipoRelacionEnhanced)pm.getObjectById(idTipoRelacionEnh, true);

			UsuarioEnhancedPk idUsuarioEnh = new UsuarioEnhancedPk();
			idUsuarioEnh.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(idUsuarioEnh, true);

			// Se verifica que exista una relación con el identificador suministrado
			Query query = pm.newQuery();
			query.setClass(RelacionEnhanced.class);
			query.declareParameters("String idRel");
			query.setFilter("this.idRelacion == idRel");
			Collection col = (Collection)query.execute(idRelacion);

			if(col.isEmpty()){
				throw new DAOException("Debe existir una relación con el identificador " + idRelacion);
			}

			//boolean ingresarRelacion = true;
			
			/*try {
				//Se verifica si la relacion existe.
				//Se crea la relación
				RelacionEnhanced.ID relacionAnteriorEnh = new RelacionEnhanced.ID();
				relacionAnteriorEnh.idRelacion = idRelacion;
				relacionAnteriorEnh.idFase = tipoRelacion.getIdFase();
				
				RelacionEnhanced relacionAntEnh = (RelacionEnhanced)pm.getObjectById(relacionAnteriorEnh, true);
				
				if (relacionAntEnh == null) {
					ingresarRelacion = false;
				}
			} catch (Exception ee) {
				logger.debug("La relacion es nueva no existe")
			}*/
			

			relacionEnh = new RelacionEnhanced();
			relacionEnh.setIdRelacion(idRelacion);
			relacionEnh.setIdFase(tipoRelacion.getIdFase());
			relacionEnh.setFecha(new Date());
			relacionEnh.setUsuario(usuarioEnh);
			relacionEnh.setTipoRelacion(tipoRelacionEnh);

			pm.makePersistent(relacionEnh);

			Turno turno;
			TurnoEnhancedPk turnoEnhId = new TurnoEnhancedPk();
			TurnoEnhanced turnoEnh;

			// Los tipos de relacion de confrontación a antiguo sistema, y de confrontación a testamentos
			// deben considerarse como casos especiales
			if(idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA) ||
					idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO)) {

				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

					// Se obtiene el turno de la base de datos
					turno = (Turno)itTurnos.next();
					turnoEnhId.anio = turno.getAnio();
					turnoEnhId.idCirculo = turno.getIdCirculo();
					turnoEnhId.idProceso = turno.getIdProceso();
					turnoEnhId.idTurno = turno.getIdTurno();
					turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

					// Se actualiza su historial
					List historial = turnoEnh.getHistorials();
					TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
					turnoHistoriaEnh.setIdRelacionSiguiente(relacionEnh.getIdRelacion());
					pm.makePersistent(turnoHistoriaEnh);
				}
			} else {

				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

					// Se obtiene el turno de la base de datos
					turno = (Turno)itTurnos.next();
					turnoEnhId.anio = turno.getAnio();
					turnoEnhId.idCirculo = turno.getIdCirculo();
					turnoEnhId.idProceso = turno.getIdProceso();
					turnoEnhId.idTurno = turno.getIdTurno();
					turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

					// Se actualiza su relación
					turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
					turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());

					// Se actualiza su historial
					List historial = turnoEnh.getHistorials();
					TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
					turnoHistoriaEnh.setIdRelacion(relacionEnh.getIdRelacion());
					pm.makePersistent(turnoHistoriaEnh);

					pm.makePersistent(turnoEnh);
				}
			}

			pm.currentTransaction().commit();

			pm.makeTransient(relacionEnh);
		} catch (JDOUserException jdoUserException) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,jdoUserException.getMessage());
			throw new DAOException("La relación " + idRelacion + " que se especificó ya existe en la base de datos para la fase seleccionada", jdoUserException);
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
            throw e;
        }
		 catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		relacion = (Relacion)relacionEnh.toTransferObject();

		return relacion;
	}
	
	/**
	 * @see gov.sir.hermod.dao.RelacionesDAO#crearRelacionNuevo(TipoRelacion, Usuario, Circulo, List, String)
	 */
	public Relacion crearRelacionNuevo(TipoRelacion tipoRelacion, Usuario usuario, Circulo circulo, List turnos, String idRelacion, String respuesta) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		RelacionEnhanced relacionEnh = null;
		Relacion relacion = null;

		try {
			pm.currentTransaction().begin();

			// Se crea la relación
			TipoRelacionEnhancedPk idTipoRelacionEnh = new TipoRelacionEnhancedPk();
			idTipoRelacionEnh.idTipoRelacion = tipoRelacion.getIdTipoRelacion();

			TipoRelacionEnhanced tipoRelacionEnh = (TipoRelacionEnhanced)pm.getObjectById(idTipoRelacionEnh, true);

			UsuarioEnhancedPk idUsuarioEnh = new UsuarioEnhancedPk();
			idUsuarioEnh.idUsuario = usuario.getIdUsuario();

			UsuarioEnhanced usuarioEnh = (UsuarioEnhanced)pm.getObjectById(idUsuarioEnh, true);

			// Se verifica que exista una relación con el identificador suministrado
			Query query = pm.newQuery();
			query.setClass(RelacionEnhanced.class);
			query.declareParameters("String idRel");
			query.setFilter("this.idRelacion == idRel");
			Collection col = (Collection)query.execute(idRelacion);

			if(col.isEmpty()){
				throw new DAOException("Debe existir una relación con el identificador " + idRelacion);
			}

			//boolean ingresarRelacion = true;
			
			/*try {
				//Se verifica si la relacion existe.
				//Se crea la relación
				RelacionEnhanced.ID relacionAnteriorEnh = new RelacionEnhanced.ID();
				relacionAnteriorEnh.idRelacion = idRelacion;
				relacionAnteriorEnh.idFase = tipoRelacion.getIdFase();
				
				RelacionEnhanced relacionAntEnh = (RelacionEnhanced)pm.getObjectById(relacionAnteriorEnh, true);
				
				if (relacionAntEnh == null) {
					ingresarRelacion = false;
				}
			} catch (Exception ee) {
				logger.debug("La relacion es nueva no existe")
			}*/
			

			relacionEnh = new RelacionEnhanced();
			relacionEnh.setIdRelacion(idRelacion);
			relacionEnh.setIdFase(tipoRelacion.getIdFase());
			relacionEnh.setFecha(new Date());
			relacionEnh.setUsuario(usuarioEnh);
			relacionEnh.setTipoRelacion(tipoRelacionEnh);
			relacionEnh.setRespuesta(respuesta);

			pm.makePersistent(relacionEnh);

			Turno turno;
			TurnoEnhancedPk turnoEnhId = new TurnoEnhancedPk();
			TurnoEnhanced turnoEnh;

			// Los tipos de relacion de confrontación a antiguo sistema, y de confrontación a testamentos
			// deben considerarse como casos especiales
			if(idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_ANTIGUO_SISTEMA) ||
					idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_TESTAMENTO) ||
					idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_CONFRONTACION)) {

				for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {

					// Se obtiene el turno de la base de datos
					turno = (Turno)itTurnos.next();
					turnoEnhId.anio = turno.getAnio();
					turnoEnhId.idCirculo = turno.getIdCirculo();
					turnoEnhId.idProceso = turno.getIdProceso();
					turnoEnhId.idTurno = turno.getIdTurno();
					turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);

					// Se actualiza su relación
					turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
					turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());
					
					// Se actualiza su historial
					List historial = turnoEnh.getHistorials();
					TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
					//turnoHistoriaEnh.setIdRelacionSiguiente(relacionEnh.getIdRelacion());
					turnoHistoriaEnh.setIdRelacion(relacionEnh.getIdRelacion());
					pm.makePersistent(turnoHistoriaEnh);
				}
			} else {
				if(idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_CERTIFICADO_PARA_ANTIGUO_SISTEMA) 
				   || idTipoRelacionEnh.idTipoRelacion.equals(CTipoRelacion.ID_CERTIFICADO_ANTIGUO_SISTEMA_DE_ENTREGA_DEVUELTO)) {
						
					for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {
	
						// Se obtiene el turno de la base de datos
						turno = (Turno)itTurnos.next();
						turnoEnhId.anio = turno.getAnio();
						turnoEnhId.idCirculo = turno.getIdCirculo();
						turnoEnhId.idProceso = turno.getIdProceso();
						turnoEnhId.idTurno = turno.getIdTurno();
						turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);
		
						// Se actualiza su relación
						turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
						turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());
						// Se actualiza su historial
						List historial = turnoEnh.getHistorials();
						TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 1);
						turnoHistoriaEnh.setIdRelacionSiguiente(relacionEnh.getIdRelacion());
						pm.makePersistent(turnoHistoriaEnh);
					}
				} else {
					for(Iterator itTurnos = turnos.iterator(); itTurnos.hasNext();) {
		
						// Se obtiene el turno de la base de datos
						turno = (Turno)itTurnos.next();
						turnoEnhId.anio = turno.getAnio();
						turnoEnhId.idCirculo = turno.getIdCirculo();
						turnoEnhId.idProceso = turno.getIdProceso();
						turnoEnhId.idTurno = turno.getIdTurno();
						turnoEnh = (TurnoEnhanced)pm.getObjectById(turnoEnhId, true);
		
						// Se actualiza su relación
						turnoEnh.setIdFaseRelacionActual(relacionEnh.getIdFase());
						turnoEnh.setIdRelacionActual(relacionEnh.getIdRelacion());
		
						// Se actualiza su historial
						List historial = turnoEnh.getHistorials();
						TurnoHistoriaEnhanced turnoHistoriaEnh = (TurnoHistoriaEnhanced)historial.get(historial.size() - 2);
						turnoHistoriaEnh.setIdRelacion(relacionEnh.getIdRelacion());
						pm.makePersistent(turnoHistoriaEnh);
		
						pm.makePersistent(turnoEnh);
					}
				}
			}
			pm.currentTransaction().commit();

			pm.makeTransient(relacionEnh);
		} catch (JDOUserException jdoUserException) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,jdoUserException.getMessage());
			throw new DAOException("La relación " + idRelacion + " que se especificó ya existe en la base de datos para la fase seleccionada", jdoUserException);
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }

            Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
            throw e;
        }
		 catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		relacion = (Relacion)relacionEnh.toTransferObject();

		return relacion;
	}
	
	


	public List getTurnosParaRelacion(Proceso proceso, Fase fase, Circulo circulo, TipoRelacion tipoRelacion, String idRelacion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		List turnosParaRelacion = null;

		try {
			pm.currentTransaction().begin();
			// Se deben obtener todos aquellos turnos que no estén relacionados con el
			// tipo de relacion dado, en la fase dada, el proceso dado, y el círculo dado.
			// Si los turnos ya están relacionados, y el tipo de relación es el de
			// confrontación a antiguo sistema, se debe verificar que la relación de
			// confrontación sea la relación actual. También debe verificarse que no exista
			// una relación siguiente en el historial del turno
			Query query = pm.newQuery(TurnoEnhanced.class);
			query.declareParameters("Long proceso, String fase, String circulo, String idRelacion, String idFaseActual");

			StringBuffer queryFilter = new StringBuffer();
			queryFilter.append("this.idProceso == proceso && this.idFase == fase && ");
			queryFilter.append("this.idCirculo == circulo && this.idRelacionActual == idRelacion && ");
			queryFilter.append("this.idFaseRelacionActual == idFaseActual");
			query.setFilter(queryFilter.toString());
			query.setOrdering("fechaInicio ascending");
			
			String idFase = "";
			
			if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS) ||
					tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS)||
					tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE))
				idFase = CFase.REG_MESA_CONTROL;
			else if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA))
				idFase = CFase.REG_FIRMAR;
                         /**
                           * @author      :   Carlos Torres
                           * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                           **/
                        else if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_REGISTRO)
                                ||tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_CORRESPONDIENCIA))
                                idFase = CFase.REG_CERTIFICADOS_ASOCIADOS;
			Collection col = (Collection)query.executeWithArray(new Object[]{new Long(proceso.getIdProceso()), 
					fase.getID(), circulo.getIdCirculo(), idRelacion, idFase});

			TurnoEnhanced turno;

			turnosParaRelacion = new ArrayList();

			for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
				turno = (TurnoEnhanced)itTurnos.next();
				if (turno.getAnulado()!= null && turno.getAnulado().equals(CTurno.TURNO_NO_ANULADO)) {
				if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
					pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
					pm.makeTransient(turno.getSolicitud());
				}
				pm.makeTransient(turno);
				turnosParaRelacion.add(turno);
			}
			}

			query.closeAll();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		int numTurnos = (turnosParaRelacion == null ? 0 : turnosParaRelacion.size());
		for(int i = 0; i < numTurnos; i++) {
			TurnoEnhanced turno = (TurnoEnhanced)turnosParaRelacion.get(i);
			turnosParaRelacion.set(i, turno.toTransferObject());
		}

		return turnosParaRelacion;
	}
	

	public List getTurnosByRelacion(Proceso proceso, Fase fase, Circulo circulo, String idRelacion) throws DAOException {

		PersistenceManager pm = AdministradorPM.getPM();
		List turnosParaRelacion = null;

		try {
			pm.currentTransaction().begin();
			// Se deben obtener todos aquellos turnos que no estén relacionados con 
			//la fase dada, el proceso dado, y el círculo dado.
			// Si los turnos ya están relacionados, y el tipo de relación es el de
			// confrontación a antiguo sistema, se debe verificar que la relación de
			// confrontación sea la relación actual. También debe verificarse que no exista
			// una relación siguiente en el historial del turno
			Query query = pm.newQuery(TurnoEnhanced.class);
			query.declareParameters("Long proceso, String fase, String circulo, String idRelacion");

			StringBuffer queryFilter = new StringBuffer();
			queryFilter.append("this.idProceso == proceso && this.idFase == fase && ");
			queryFilter.append("this.idCirculo == circulo && this.idRelacionActual == idRelacion ");
			query.setFilter(queryFilter.toString());
			
			Collection col = (Collection)query.executeWithArray(new Object[]{new Long(proceso.getIdProceso()), 
					fase.getID(), circulo.getIdCirculo(), idRelacion});

			TurnoEnhanced turno;

			turnosParaRelacion = new ArrayList();

			for(Iterator itTurnos = col.iterator(); itTurnos.hasNext();) {
				turno = (TurnoEnhanced)itTurnos.next();
				if(turno.getSolicitud() instanceof SolicitudRegistroEnhanced) {
					pm.makeTransient(((SolicitudRegistroEnhanced)turno.getSolicitud()).getDocumento());
					pm.makeTransient(turno.getSolicitud());
				}
				pm.makeTransient(turno);
				turnosParaRelacion.add(turno);
			}

			query.closeAll();
			pm.currentTransaction().commit();
		} catch (JDOException e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			if(pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			Log.getInstance().error(JDORelacionesDAO.class,e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} finally {
			if(pm != null && !pm.isClosed())
				pm.close();
		}

		int numTurnos = (turnosParaRelacion == null ? 0 : turnosParaRelacion.size());
		for(int i = 0; i < numTurnos; i++) {
			TurnoEnhanced turno = (TurnoEnhanced)turnosParaRelacion.get(i);
			turnosParaRelacion.set(i, turno.toTransferObject());
		}

		return turnosParaRelacion;
	}	
	
	private boolean tieneTestamentos(List actos){
		boolean tieneTestamento = false;
		if(actos !=null){
			Iterator it = actos.iterator();
			while(it.hasNext()){
				ActoEnhanced acto = (ActoEnhanced)it.next();
				if(acto.getTipoActo().getIdTipoActo().equals(CActo.ID_TESTAMENTOS)||acto.getTipoActo().getIdTipoActo().equals(CActo.ID_REVOCATORIA_TESTAMENTO)){
					tieneTestamento = true;
					break;
				}
			}
		}

		return tieneTestamento;
	}
	
	
}
