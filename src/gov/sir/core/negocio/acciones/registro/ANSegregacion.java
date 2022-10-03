package gov.sir.core.negocio.acciones.registro;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.registro.EvnRespSegregacion;
import gov.sir.core.eventos.registro.EvnSegregacion;
import gov.sir.core.negocio.acciones.correccion.ANCorreccion;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.SegregacionException;
import gov.sir.core.negocio.acciones.excepciones.SegregacionHTException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.CiudadanoComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.interfaz.PrintJobsInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de la creación del folio para mediante el proceso de segreación,
 * proceso por el cuál a partir de un folio se pueden crear folios hijos.
 * @author ppabon
*/
public class ANSegregacion extends SoporteAccionNegocio {


	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de Hermod
	 */
	private PrintJobsInterface printJobs;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANSegregacion.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *de los servicio que necesita.
	 */
	public ANSegregacion() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento ev) throws EventoException {
		EvnSegregacion evento = (EvnSegregacion) ev;

		if (evento.getTipoEvento().equals(EvnSegregacion.CONSULTA_FOLIO)) {
			return consultarFolio(evento);
		}else if (evento.getTipoEvento().equals(EvnSegregacion.GUARDAR_WEB_SEGREGACION)) {
			return guardarWebSegregacion(evento);
		} else if (evento.getTipoEvento().equals(EvnSegregacion.CONSULTA_ANOTACIONES_TEMPORALES)) {
			return consultarAnotacionesTemporales(evento);
		} else if (evento.getTipoEvento().equals(EvnSegregacion.SEGREGACION_FOLIO)) {
			return segregacionMasiva(evento);
		} else if (evento.getTipoEvento().equals(EvnSegregacion.CONSULTAR_NUEVO_FOLIO)) {
			return consultarNuevoFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnSegregacion.GUARDAR_CAMBIOS_FOLIO)) {
			return editarFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnSegregacion.ELIMINAR_SEGREGACION)) {
			return eliminarWebSegregacion(evento);
		} else if(evento.getTipoEvento().equals(EvnSegregacion.CONSULTAR_ULTIMOS_PROPIETARIOS)) {
			return consultarUltimosPropietarios(evento);
		} else if(evento.getTipoEvento().equals(EvnSegregacion.AGREGAR_CIUDADANO_ANOTACION)) {
			return validarCiudadanos(evento);
		}
		

		return null;
	}

	/**
	 * Éste método guarda el objeto webSegregacion y sus relaciones y consulta el nuevo folio para 
	 * mostrarle al usuario las anotaciones que puede heredar.
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultarFolio(Evento ev) throws EventoException {
		EvnSegregacion evento = (EvnSegregacion) ev;

		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();

		WebSegregacion webSegregacion = evento.getWebSegregacion();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		try {
			
			if(webSegregacion.getIdWebSegeng()==null){
				SolicitudPk sid = new SolicitudPk();
				sid.idSolicitud = solicitud.getIdSolicitud();
				webSegregacion.setFechaCreacion(new Date());
				wid = hermod.crearWebSegEng(webSegregacion ,  sid);				
			} else{
				wid = new WebSegEngPk();
				wid.idSolicitud = webSegregacion.getIdSolicitud();
				wid.idWebSegeng = webSegregacion.getIdWebSegeng(); 
				hermod.actualizarWebSegEng(wid, webSegregacion );
			}
			
			webSegEng = hermod.consultarWebSegEng(wid);
			
		}catch(HermodException t){
			throw new SegregacionException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new SegregacionException("Se presento un error al guardar los datos.",e);
		}


		Folio folio = evento.getFolio();
		Usuario usuarioSIR = evento.getUsuarioNeg(); 

		FolioPk id = new FolioPk();
		id.idMatricula = folio.getIdMatricula();

		try {
			folio = forseti.getFolioByIDSinAnotaciones(id, usuarioSIR);
			
		} catch (Throwable e) {
			try {
				folio = forseti.getFolioByID(id);
			} catch (ForsetiException ex) {
				throw new SegregacionException(ex.getMessage(), ex);
			} catch (Throwable et) {
				ExceptionPrinter printer = new ExceptionPrinter(et);
				Log.getInstance().error(ANSegregacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new SegregacionException(e.getMessage(), et);
			}
		}
		
		return new EvnRespSegregacion(folio, webSegEng , EvnRespSegregacion.CONSULTA_FOLIO);
	}
	
	
	/**
	 * Método que guara en la base de datos toda la información referente a una segregación.
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta guardarWebSegregacion(Evento ev) throws EventoException {
		EvnSegregacion evento = (EvnSegregacion) ev;

		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();

		WebSegregacion webSegregacion = evento.getWebSegregacion();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		try {
			
			wid = new WebSegEngPk();
			wid.idSolicitud = webSegregacion.getIdSolicitud();
			wid.idWebSegeng = webSegregacion.getIdWebSegeng(); 
			hermod.actualizarWebSegEng(wid, webSegregacion );
			
			webSegEng = hermod.consultarWebSegEng(wid);
			
		}catch(HermodException t){
			throw new SegregacionException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new SegregacionException("Se presento un error al guardar los datos.",e);
		}

		
		return new EvnRespSegregacion(webSegEng , EvnRespSegregacion.GUARDAR_WEB_SEGREGACION);
	}	
	

	private EventoRespuesta consultarAnotacionesTemporales(Evento ev) throws EventoException {
		EvnSegregacion evento = (EvnSegregacion) ev;

		WebSegregacion webSegregacion = evento.getWebSegregacion();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;


		Folio folio = evento.getFolio();
		List anotaciones = new ArrayList();
		try {
			FolioPk id = new FolioPk();
			id.idMatricula = folio.getIdMatricula();

			anotaciones = forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, evento.getUsuarioNeg());
		} catch (ForsetiException e) {
			throw new SegregacionException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANSegregacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		try {
			
			wid = new WebSegEngPk();
			wid.idSolicitud = webSegregacion.getIdSolicitud();
			wid.idWebSegeng = webSegregacion.getIdWebSegeng(); 
			webSegEng = hermod.consultarWebSegEng(wid);
			
		}catch(HermodException t){
			throw new SegregacionException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new SegregacionException("Se presento un error al guardar los datos.",e);
		}		
		
		return new EvnRespSegregacion(anotaciones, webSegEng , EvnRespSegregacion.CONSULTA_ANOTACIONES_TEMPORALES);
	}

	/**
	 * @param ev
	 * @return
	 */
	private EventoRespuesta segregacionMasiva(Evento ev) throws EventoException {
		EvnSegregacion evento = (EvnSegregacion) ev;
		
		WebSegregacion webSegregacion = evento.getWebSegregacion();
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;

		try {

			wid = new WebSegEngPk();
			wid.idSolicitud = webSegregacion.getIdSolicitud();
			wid.idWebSegeng = webSegregacion.getIdWebSegeng();
			hermod.actualizarWebSegEng(wid, webSegregacion);

			webSegEng = hermod.consultarWebSegEng(wid);

		} catch (HermodException t) {
			throw new SegregacionException("Se presento un error al guardar los datos.", t);
		} catch (Throwable e) {
			throw new SegregacionException("Se presento un error al guardar los datos.", e);
		}

		
		//SE RECUPERA EL FOLIO ORIGINAL Y SE CREA UNO NUEVO DONDE SE COLOCARA LA INFORMACIÓN QUE SE REQUIERE 
		//PARA LA SEGREGACIÓN 
		Folio folioOriginal = evento.getFolio();
		Turno turno = evento.getTurno();
		List foliosDerivados = null;
		TurnoPk idTurno = new TurnoPk();
		idTurno.anio = turno.getAnio();
		idTurno.idCirculo = turno.getIdCirculo();
		idTurno.idProceso = turno.getIdProceso();
		idTurno.idTurno = turno.getIdTurno();
		
		try {
			/*TFS 4322: Optimizacion de segregacion por PL
			 * */
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   se recupera la salvedad del evento getSalvedadanotacion() y se incluye en el metodo
                         *                  segregarFolioPLSQL para realizar la segregacion
                         * Caso Mantis  :   04131
                         */
                        SalvedadAnotacion salvedad = evento.getSalvedadanotacion();
                        WebAnotacion webanotacion = webSegEng.getAnotacion();
                        java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                try {
                                    auditoria.guardarDatosTerminal(infoUsuario);
                                } catch (GeneralSIRException ex) {
                                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        foliosDerivados = forseti.segregarFolioPLSQL(folioOriginal, idTurno, salvedad, webanotacion.getIdAnotacionDefinitiva());
                                try {
                                    auditoria.borrarDatosTerminal(turno.getIdWorkflow());
                                } catch (GeneralSIRException ex) {
                                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                                }
		} catch (ForsetiException e) {

			if (e.getHashErrores() != null && e.getHashErrores().size() > 0) {
				throw new SegregacionHTException(e.getHashErrores());
			}

			throw new SegregacionException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANSegregacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			turno = hermod.getTurno(idTurno);
			/**
			 * @author David Panesso
			 * @change 1253.CALIFICACION.FOLIOS.CERRADOS
			 * Se toma el estado del folio para manejar la excepción null para que guarde si es el folio está activo o cerrado.
			 **/
			try {
				Solicitud sol = turno.getSolicitud();
				List folios = sol.getSolicitudFolios();
				Iterator ifol = folios.iterator();
				while (ifol.hasNext()) {
					SolicitudFolio sfol = (SolicitudFolio) ifol.next();
					Folio fol = sfol.getFolio();
					FolioPk fid = new FolioPk();
					fid.idMatricula = fol.getIdMatricula();
					EstadoFolio est = new EstadoFolio();
					if (forseti.cerradoFolio(fid.idMatricula, null)) {
						est.setIdEstado(CEstadoFolio.CERRADO);
					} else {
						est.setIdEstado(CEstadoFolio.ACTIVO);
					}
					fol.setEstado(est);
					sfol.setFolio(fol);
				}
			} catch (Throwable e1) {
				e1.printStackTrace();
				throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti", e1);
			}
		} catch (HermodException e) {
			throw new SegregacionException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANSegregacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return new EvnRespSegregacion(foliosDerivados, EvnRespSegregacion.SEGREGACION_FOLIO, turno);

	}
	
	/**
	 * Método que permite consultar un folio para que sobre él se consulten o realicen los cambios
	 * que se solicitan. Se le debe pasar el usuario porque el folio esta en temporal.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultarNuevoFolio(EvnSegregacion evento) throws EventoException {
		
		Folio folio = evento.getFolio();
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioNeg();
		
		WebSegregacion webSegregacion = evento.getWebSegregacion();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;		

		try {
			if (folio != null && folio.getIdMatricula() != null) {

				FolioPk id = new FolioPk();
				id.idMatricula = folio.getIdMatricula();

				folio = forseti.getFolioByID(id, u);
// no se necesitan las anotaciones. En la pantalla VER_FOLIO no se usan
			}else{
				throw new EventoException("Se necesita saber la matrícula a consultar");
			}
			
		} catch (Throwable e) {		
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANSegregacion.class,"Ha ocurrido una excepción al consultar datos del folio "+ printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		try {
			
			wid = new WebSegEngPk();
			wid.idSolicitud = webSegregacion.getIdSolicitud();
			wid.idWebSegeng = webSegregacion.getIdWebSegeng(); 
			webSegEng = hermod.consultarWebSegEng(wid);
			
		}catch(HermodException t){
			throw new SegregacionException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new SegregacionException("Se presento un error al guardar los datos.",e);
		}		
				
		
		return new EvnRespSegregacion(folio, webSegEng , EvnRespSegregacion.CONSULTA_NUEVO_FOLIO);
				
	}	

	/**
	 * Método que guarda los cambios que se han solicitado.
	 * Estos cambios quedan guardados en tablas temporales hasta que no se hagan defintivos, es decir se apruebe
	 * la solicitud o se deshagan cuando se niegue la corrección.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta editarFolio(EvnSegregacion evento) throws EventoException {
		EvnRespSegregacion evRespuesta = null;
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioNeg();

		Folio folio = evento.getFolio();

		try {
			forseti.updateFolio(evento.getFolio(), usr, null, false);
		} catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANSegregacion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio"	+ printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return evRespuesta;
	}

	/**
	 * Éste método elimina el objeto webEnglobe con los datos que hacen parte del englobe 
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta eliminarWebSegregacion(Evento ev) throws EventoException {
		EvnSegregacion evento = (EvnSegregacion) ev;

		WebSegregacion webSegregacion = evento.getWebSegregacion();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		try {
			if(webSegregacion!=null){
				wid = new WebSegEngPk();
				wid.idSolicitud = webSegregacion.getIdSolicitud();
				wid.idWebSegeng = webSegregacion.getIdWebSegeng();
				hermod.eliminarWebSegEng(wid); 
			}
		}catch(HermodException t){
			throw new SegregacionException("Se presento un error al eliminar los datos de la segregación.",t);
		} catch (Throwable e) {
			throw new SegregacionException("Se presento un error al eliminar los datos de la segregación.",e);
		}

		return new EvnRespSegregacion(webSegEng , EvnRespSegregacion.GUARDAR_WEB_SEGREGACION);
	}	

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarUltimosPropietarios(EvnSegregacion evento) throws EventoException {
	
		Usuario usuarioNeg = evento.getUsuarioNeg();
		Folio folio = evento.getFolio();
		List listaUltimosPropietarios = null;

		
		try {
			listaUltimosPropietarios = forseti.getCiudadanoUltimosFolio(folio.getIdMatricula());
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANSegregacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new SegregacionException("No se pudieron grabar las anotaciones temporales", e1);

		}
		
		Map mapaPropietarios = new HashMap();
		
		for (int i = 0; i < listaUltimosPropietarios.size(); i++ ) {
			AnotacionCiudadano anotacionciu = (AnotacionCiudadano)listaUltimosPropietarios.get(i);
		    Ciudadano ciu = anotacionciu.getCiudadano();    
		    String key = ciu.getTipoDoc() + "-" + ciu.getDocumento();
		    if (!mapaPropietarios.containsKey(key)){
		    	mapaPropietarios.put(key,anotacionciu);	
		    }		    
		}
		
		Collection col = (Collection)mapaPropietarios.values();
		
		listaUltimosPropietarios = new ArrayList();
		
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			listaUltimosPropietarios.add((AnotacionCiudadano) iter.next());
		}
		
		//listaUltimosPropietarios = (List) mapaPropietarios.values();
		Collections.sort(listaUltimosPropietarios, new CiudadanoComparator());
		Collections.reverse(listaUltimosPropietarios);
		
		EvnRespSegregacion evn = new EvnRespSegregacion("",EvnRespSegregacion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setTipoEvento(EvnRespSegregacion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setPropietariosFolios(listaUltimosPropietarios);
		return evn;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta validarCiudadanos(EvnSegregacion evento) throws EventoException {
	
		List anotacionCiudadanos = evento.getAnotacionCiudadanos();
		Turno turno = evento.getTurno();
		
		try {
			Iterator itt = anotacionCiudadanos.iterator();
			while(itt.hasNext()){
				AnotacionCiudadano anotaCiudadano = (AnotacionCiudadano)itt.next();
				Ciudadano ciu = anotaCiudadano.getCiudadano();
				CiudadanoTMP ciudTemp = null;
				try {
					ciudTemp = forseti.getCiudadanoTmpByDocumento(ciu.getTipoDoc(), ciu.getDocumento(), ciu.getIdCirculo());	
				} catch (Throwable ee) {}
				
				if (ciudTemp!=null) {
					if (turno!=null) {
						if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES) ) {
							if (ciudTemp.getNumeroRadicacion()!=null && !ciudTemp.getNumeroRadicacion().equals(turno.getIdWorkflow())) {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
							} 
						} else {
							if (ciudTemp.getNumeroRadicacion()!=null) {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
							} else {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
							}
						}
					}
				}
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANSegregacion.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);
		}
		
		EvnRespSegregacion evn = new EvnRespSegregacion(EvnRespSegregacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setTipoEvento(EvnRespSegregacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setListaCompletaCiudadanos(evento.getListaCompletaCiudadanos());
		return evn;
	}

}
