package gov.sir.core.negocio.acciones.correccion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.eventos.correccion.EvnConfrontacion;
import gov.sir.core.eventos.correccion.EvnRespConfrontacion;
import gov.sir.core.negocio.acciones.excepciones.ConfrontacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaConfrontacionCorrecionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.registro.SerialIds;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnConfrontacion</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespConfrontacion</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow de la fase de confrontación en el proceso de correcciones y llamar a los servicios 
 * que se requieren en cada fase del proceso.
 * @author ppabon
 */
public class ANConfrontacion extends SoporteAccionNegocio {
	private ServiceLocator service = null;

	/**
	 * 
	 */
	public ANConfrontacion() {
		service = ServiceLocator.getInstancia();
	}

	/**
	 *
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnConfrontacion evento = (EvnConfrontacion) e;

		//ASOCIAR UNA MATRICULA
		if (evento.getTipoEvento().equals(EvnConfrontacion.ASOCIAR_UNA_MATRICULA)) {
			return asociarUnaMatricula(evento);
		} 
		
		//ELIMINAR UNA MATRICULA
		else if (evento.getTipoEvento().equals(EvnConfrontacion.ELIMINAR_UNA_MATRICULA)) {
			return eliminarUnaMatricula(evento);
		} 
		
		//CONFIRMAR
		else if (evento.getTipoEvento().equals(EvnConfrontacion.CONFIRMAR)) {
			return confirmar(evento);
		} 
		
		//ASOCIAR UN RANGO DE MATRICULAS.
		else if (evento.getTipoEvento().equals(EvnConfrontacion.ASOCIAR_UN_RANGO)) {
			return asociarUnRango(evento);
		}
		return null;
	}




	/**
	 * Permite eliminar las matrículas que vienen en el evento, del turno que se esta confrontando
	 * en la fase confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta eliminarUnaMatricula(EvnConfrontacion evento) throws EventoException {
		HermodServiceInterface hermod = null;

		//OBTENER HERMOD
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se encontró el servicio hermod:" + ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio hermod:" + ep.toString(), e);
		}

		//OBTENER PARAMETROS DEL EVENTO
		Turno turno = evento.getTurno();

		try {
			//OBTENER ID DEL TURNO Y DEL FOLIO
			TurnoPk id = new TurnoPk();
			id.anio = turno.getAnio();
			id.idCirculo = turno.getIdCirculo();
			id.idProceso = turno.getIdProceso();
			id.idTurno = turno.getIdTurno();

			List folios = evento.getFolios();
			Iterator it = folios.iterator();
			String idMatricula = null;

			while (it.hasNext()) {
				Folio folio = (Folio) it.next();
				idMatricula = folio.getIdMatricula();
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                auditoria.guardarDatosTerminal(infoUsuario);
				hermod.removeFolioFromTurno(idMatricula, id);
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
			}

			turno = hermod.getTurno(id);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo eliminar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo eliminar la matrícula", e);
		}

		return new EvnRespConfrontacion(turno);
	}




    //DEPURADO ENERO 17 2005
	/**
	 * Permite aprobar la fase de confrontación del proceso de correcciones y avanzar el turno
	 * a la siguiente fase que es la fase de corrección de documentos.
	 * @param evento
	 * @return
	 */
	private EvnRespConfrontacion confirmar(EvnConfrontacion evento) throws EventoException {
		HermodServiceInterface hermod = null;

		//OBTENER HERMOD
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se encontró el servicio hermod:" + ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio hermod:" + ep.toString(), e);
		}
		
		

		//3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase. 
		try
		{
		   Hashtable tablaAvance = new Hashtable(2);
		   tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
		   tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
		   hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,evento.getTurno());
		}
		catch(Throwable t)
		{
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}
        

		//OBTENER PARAMETROS DEL EVENTO
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		
		//Construir tabla de parámetros para avanzar el turno.
		Hashtable tabla = new Hashtable();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		return null;
	}




	/**
	 * Permite asociar un rango de matrículas al turno que se esta confrontando en la 
	 * fase de confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EventoRespuesta asociarUnRango(EvnConfrontacion evento) throws EventoException {
		ForsetiServiceInterface forseti;
		HermodServiceInterface hermod;

		//EXCEPCION MULTIPLE
		String mensajes = "";

		//OBTENER FORSETI
		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se encontró el servicio forseti:" + ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio forseti:" + ep.toString(), e);
		}

		//OBTENER HERMOD
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se encontró el servicio hermod:" + ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio hermod:" + ep.toString(), e);
		}

		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		String idMatRL = evento.getIdMatriculaRL();
		String idMatRR = evento.getIdMatriculaRR();

		//SE DEFINE UNA LISTA PARA AGREGAR LOS ERRORES
		List errores = new ArrayList();

		try {
			SerialIds si = new SerialIds(idMatRL, idMatRR);
			if (si.isSerialExists()) {

				//CREAR ID DEL TURNO
				TurnoPk id = new TurnoPk();
				id.anio = turno.getAnio();
				id.idCirculo = turno.getIdCirculo();
				id.idProceso = turno.getIdProceso();
				id.idTurno = turno.getIdTurno();
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                auditoria.guardarDatosTerminal(infoUsuario);
                                
				hermod.addRangoFoliosToSolicitudRegistro(idMatRL, idMatRR, turno.getSolicitud(), evento.getUsuarioNeg(), false);
				turno = hermod.getTurno(id);
                                 /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
				if (!mensajes.equals("")) {
					errores.add(mensajes);
				}

				if (errores.size() > 0) {
					throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
				}

			} else {
				errores.add("El rango de matrículas es inválido");
				throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
			}
		} catch (MatriculaInvalidaConfrontacionCorrecionException ev) {
			throw ev;
		} catch (HermodException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo asociar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("Confrontaciones fallidas" + e.getMessage(), e);
		}

		return new EvnRespConfrontacion(turno);
	}






	/**
	 * Permite asociar una matrícula al turno que se esta confrontando en la 
	 * fase de confrontación del proceso de correcciones.
	 * @param evento
	 * @return
	 */
	private EvnRespConfrontacion asociarUnaMatricula(EvnConfrontacion evento) throws EventoException {
		ForsetiServiceInterface forseti;
		HermodServiceInterface hermod;
		
		//SE DEFINE UNA LISTA PARA AGREGAR LOS ERRORES
		List errores = new ArrayList();		

		//OBTENER FORSETI
		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se encontró el servicio forseti:" + ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio forseti:" + ep.toString(), e);
		}

		//OBTENER HERMOD
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se encontró el servicio hermod:" + ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio hermod:" + ep.toString(), e);
		}

		//OBTENER PARAMETROS
		Turno turno = evento.getTurno();
		Folio folio = evento.getFolio();

		try {
			//SI EL FOLIO EXISTE
			if (forseti.existeFolio(folio.getIdMatricula())) {
				//ASOCIARLO
				TurnoPk id = new TurnoPk();
				id.anio = turno.getAnio();
				id.idCirculo = turno.getIdCirculo();
				id.idProceso = turno.getIdProceso();
				id.idTurno = turno.getIdTurno();

				Solicitud sol = turno.getSolicitud();
				if (sol != null) {
					List listaSolicitudes = sol.getSolicitudFolios();

					//Verificar si la matrícula que se va a añadir está en la lista
					//de solicitudes folio de la solicitud.  En ese caso se lanza una
					//excepción. 
					if (listaSolicitudes != null) {
						for (int i = 0; i < listaSolicitudes.size(); i++) {
							SolicitudFolio solFolio = (SolicitudFolio) listaSolicitudes.get(i);
							if (solFolio != null) {
								if (solFolio.getIdMatricula().equals(folio.getIdMatricula())) {
									errores.add("La matricula que intenta ingresar ya está asociada a la solicitud.");
									throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
								}
							}
						}
					}
				}

				/**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                auditoria.guardarDatosTerminal(infoUsuario);
                                SolicitudFolioPk sfId = hermod.addFolioToTurno(folio.getIdMatricula(), id,evento.getUsuarioNeg());
				//AÑADIRLO AL OBJETO TURNO
				SolicitudFolio sfolio = new SolicitudFolio();
				sfolio.setFolio(folio);
				sfolio.setSolicitud(turno.getSolicitud());
				turno.getSolicitud().addSolicitudFolio(sfolio);
                                /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
                                
                               
			} else {
				errores = new ArrayList();
				errores.add("La matrícula " + folio.getIdMatricula() + " no existe.");
				throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
			}

		} catch (MatriculaInvalidaConfrontacionCorrecionException ev) {
			throw ev;
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo asociar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANConfrontacion.class,"No se pudo asociar la matrícula:" + ep.toString());
			throw new ConfrontacionNoEfectuadaException("No se pudo asociar la matrícula", e);
		}

		return new EvnRespConfrontacion(turno);

	}
}
