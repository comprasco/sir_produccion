package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaFolioException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoCreadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnFolio</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespFolio</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con la creación de folios. 
 * @author dlopez, mmunoz
 */
public class ANFolio extends SoporteAccionNegocio {

	/**
	 * Instancia del ServiceLocator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de la interfaz de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de la intefaz de Forseti
	 */
	private ForsetiServiceInterface forseti;


	/**
	 *Constructor de la clase ANFolio.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANFolio() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarListaFolios(EvnFolio evento) throws EventoException {

		List folios = evento.getListaFolios();
		List foliosComp = new ArrayList();
		Iterator it = folios.iterator();
		while (it.hasNext()) {
			Folio folio = (Folio) it.next();
			String matricula = folio.getIdMatricula();
			try {
				Folio completo = forseti.getFolioByMatricula(matricula);
// ok sin anotaciones; el método no se está usando en ninguna parte				
				foliosComp.add(completo);
			} catch (ForsetiException e1) {
				throw new ConsultaFolioException(e1.getErrores());
			} catch (Throwable e) {
				throw new EventoException(e.getMessage(), e);
			}
		}
		return new EvnRespFolio(foliosComp, EvnRespFolio.CONSULTAR_LISTA);
	}

	/**
	* Recibe un evento folio y devuelve un evento de respuesta.  Se encarga de 
	* llamar al método adecuado, de acuerdo con el parámetro TipoEvento recibido
	* dentro del EventoFolio. 
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnFolio</CODE>
	* @throws EventoException cuando ocurre una situación inválida. 
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespFolio</CODE>
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnFolio evento = (EvnFolio) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnFolio.CREACION_FOLIO_CREADO)) {
			return registrarCreacion(evento);
		} else if (evento.getTipoEvento().equals(EvnFolio.GRABAR_TEMPORAL)) {
			return grabarTemporalFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnFolio.CONSULTA)) {
			return consultarFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnFolio.CONSULTA_DIGITACION_MASIVA)) {
			return consultarFolioUsuario(evento);
		} else if (evento.getTipoEvento().equals(EvnFolio.CONSULTA_ID_USER)) {
			return consultarFolioIdUser(evento);
		} else if (evento.getTipoEvento().equals(EvnFolio.CONSULTAR_LISTA)) {
			return consultarListaFolios(evento);
		} else if (evento.getTipoEvento().equals(EvnFolio.GRABAR_EDICION)) {
			return grabarAnotacionesTemporal(evento);
		}

		return null;
	}

	/**
	 * Método que se encarga de solicitar el servicio de hermod necesario para avanzar
	 * un turno.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnFolio</CODE>
	 * @throws Excepción generada cuando se presenta un error en el servicio solicitado. Esta
	 * excepción es del tipo <CODE>RegistroCreacionNoEfectuadaException</CODE>
	 **/
	private EventoRespuesta registrarCreacion(EvnFolio evento) throws EventoException {

		EvnRespFolio eventoResp = null;
		Usuario user = evento.getUsuarioSIR();
		//Hashtable tabla = new Hashtable();

		Folio folioResp = crearFolio(evento);
		eventoResp = new EvnRespFolio(folioResp, EvnRespFolio.CREAR);
        
		String matricula = folioResp.getIdMatricula();
		Log.getInstance().debug(ANFolio.class,"[ANFolio::registrarCreacion]: Creado el folio con matricula: " + matricula);

		Turno turno = evento.getTurno();
		TurnoPk tID = new TurnoPk();
		tID.anio = turno.getAnio();
		tID.idCirculo = turno.getIdCirculo();
		tID.idProceso = turno.getIdProceso();
		tID.idTurno = turno.getIdTurno();

        FolioPk fid=new FolioPk();
        fid.idMatricula=folioResp.getIdMatricula();
		SolicitudFolioPk sfID = null;
		try {
			sfID = hermod.addFolioToTurno(matricula, tID, evento.getUsuarioSIR());
			hermod.marcarFolioRecienCreadoASInTurno(tID, fid);
            hermod.marcarFolioInTurno(tID, fid);
            turno = hermod.getTurno(tID);

		} catch (Throwable e) {
			String msg = "No se pudo asociar el folio con matricula " + matricula + " al turno: " + turno.getIdWorkflow();
			Log.getInstance().error(ANFolio.class,msg);
			Log.getInstance().error(ANFolio.class,e);
			throw new EventoException(msg, e);
		}

		/*if (sfID != null) {
			SolicitudFolio solFol = new SolicitudFolio();

			solFol.setIdMatricula(sfID.idMatricula);
			solFol.setIdSolicitud(sfID.idSolicitud);
			solFol.setIdZonaRegistral(sfID.idZonaRegistral);

			solFol.setFolio(folioResp);

			turno.getSolicitud().addSolicitudFolio(solFol);
		}*/
		
		String tipoResp = EvnRespFolio.CONSULTAR;
		
		eventoResp = new EvnRespFolio(folioResp, tipoResp);
		eventoResp.setTurno(turno);
		return eventoResp;		

	}

	/**
	 * Metodo que graba un folio temporalmente.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta grabarTemporalFolio(EvnFolio evento) throws EventoException {

		Folio folio = evento.getFolio();
		Usuario usuario = evento.getUsuarioSIR();

		Turno turno = evento.getTurno();

		try {
			forseti.updateFolio(folio, usuario, null, false);
		} catch (Throwable e) {
			Log.getInstance().debug(ANFolio.class,"Ha ocurrido un error al tratar de grabar el folio temporalmente.");
			Log.getInstance().error(ANFolio.class,e);
			throw new EventoException("Ha ocurrido un error al tratar de grabar el folio temporalmente.", e);
		}

		Log.getInstance().debug(ANFolio.class,"Se ha grabado temporalmente el Folio con matricula: " + folio.getIdMatricula());

		try {
			if (folio != null && folio.getIdMatricula() != null) {
				FolioPk folioID = new FolioPk();
				folioID.idMatricula = folio.getIdMatricula();
				folio = this.forseti.getFolioByID(folioID, usuario);
// ok sin anotaciones; la pantalla ya no se usa				
			}
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new ConsultaFolioException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		String tipoResp = EvnRespFolio.CONSULTAR;
		EvnRespFolio eventoResp = new EvnRespFolio(folio, tipoResp);
		eventoResp.setTurno(null);

		return eventoResp;
	}

	/**
	 * Método que va a llamar los servicios de forseti necesarios para
	 * hacer persistente un folio. 
	 * @param Un folio que previamente se ha validado que no sea nulo. 
	 * @return El folio sobre el cual se ha hecho la persistencia.
	 * @throws Una excepción generada cuando se encontraron errores durante
	 * el proceso de validación de los datos del folio. Esta excepción es del
	 * tipo <CODE>FolioNoCreadoException</CODE>
	 */
	private Folio crearFolio(EvnFolio evento) throws EventoException {

		Folio folio = evento.getFolio();
		Turno t = evento.getTurno();
		//Turno.ID tid = new Turno
		
		TurnoPk oidTurno = new TurnoPk();
		
		oidTurno.anio = t.getAnio();
		oidTurno.idCirculo = t.getIdCirculo();
		oidTurno.idProceso = t.getIdProceso();
		oidTurno.idTurno = t.getIdTurno();
		
		if (folio == null) {
			List l = new ArrayList();
			l.add("El folio asociado es null");
			throw new FolioNoCreadoException(l);
		}
		Folio auxFolio;

		//Se valida que toda la información del folio sea correcta.
		try {
			this.forseti.validarCrearFolio(folio, true);

		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		//Si la información del folio es válida se crea el folio.
		Usuario usuarioSIR = evento.getUsuarioSIR();

		try {
			auxFolio = this.forseti.crearFolio(folio, usuarioSIR, oidTurno, true);
		} catch (ForsetiException fe) {
			List l = null;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return auxFolio;

	}

	/**
	 * Método que va a llamar los servicios de forseti necesarios para
	 * hacer la consulta de un folio por el idMatricula. 
	 * @param Un folio con el idMatricula que se desea consultar. 
	 * @return El folio consultado.
	 * @throws Una excepción generada cuando se encontraron errores durante
	 * el proceso de validación de los datos del folio. Esta excepción es del
	 * tipo <CODE>ConsultaFolioException</CODE>
	 */
	private EvnRespFolio consultarFolio(EvnFolio evento) throws EventoException {
		Folio folio = evento.getFolio();

		try {
			if (folio != null && folio.getIdMatricula() != null) {
				folio = this.forseti.getFolioByMatricula(folio.getIdMatricula());
// ok sin anotaciones, se usa en correcciones digitacion masiva				
			}
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new ConsultaFolioException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return new EvnRespFolio(folio, EvnRespFolio.CONSULTAR);

	}
	
	
	/**
	 * Método que va a llamar los servicios de forseti necesarios para
	 * hacer la consulta de un folio por el idMatricula y teniendo en cuenta los cambios temporales. 
	 * @param Un folio con el idMatricula que se desea consultar. 
	 * @return El folio consultado.
	 * @throws Una excepción generada cuando se encontraron errores durante
	 * el proceso de validación de los datos del folio. Esta excepción es del
	 * tipo <CODE>ConsultaFolioException</CODE>
	 */
	private EvnRespFolio consultarFolioUsuario(EvnFolio evento) throws EventoException {
		Folio folio = evento.getFolio();
		Usuario usuarioSIR = evento.getUsuarioSIR();		

		try {
			FolioPk folioID = new FolioPk();
			folioID.idMatricula = folio.getIdMatricula();
			folio = this.forseti.getFolioByIDSinAnotaciones(folioID, null);
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			ConsultaFolioException cfe = new ConsultaFolioException(l);
			cfe.addError(fe.getMessage());
			throw cfe;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return new EvnRespFolio(folio, EvnRespFolio.CONSULTAR);

	}	
	

	/**
	 * Método que va a llamar los servicios de forseti necesarios para
	 * hacer la consulta de un folio por el idMatricula. 
	 * @param Un folio con el idMatricula que se desea consultar. 
	 * @return El folio consultado.
	 * @throws Una excepción generada cuando se encontraron errores durante
	 * el proceso de validación de los datos del folio. Esta excepción es del
	 * tipo <CODE>ConsultaFolioException</CODE>
	 */
	private EvnRespFolio consultarFolioIdUser(EvnFolio evento) throws EventoException {
		Folio folio = evento.getFolio();
		Turno turno = (Turno) evento.getTurno();
		Solicitud solicitud = (Solicitud) turno.getSolicitud();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		try {
			if (folio != null && folio.getIdMatricula() != null) {

				FolioPk folioID = new FolioPk();
				folioID.idMatricula = folio.getIdMatricula();
				folio = this.forseti.getFolioByID(folioID, usr);
//No necesita anotaciones. La pantalla que lo usa está obsoleta
			}
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new ConsultaFolioException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		String tipoResp = EvnRespFolio.CONSULTAR;
		EvnRespFolio eventoResp = new EvnRespFolio(folio, tipoResp);
		eventoResp.setTurno(turno);

		return eventoResp;

	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta grabarAnotacionesTemporal(EvnFolio evento) throws EventoException {

		Folio devuelve=null;
		List listaATemp=null;
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioNeg();

		try {
			forseti= (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			} catch (ServiceLocatorException e) {
				ExceptionPrinter ep=new ExceptionPrinter(e);
				Log.getInstance().error(ANFolio.class,"No se encontró el servicio forseti:"+ep.toString());
				throw new ServicioNoEncontradoException("No se encontró el servicio forseti:"+ep.toString(),e);
			}
			Usuario usuarioNeg=evento.getUsuarioNeg();
			Folio nuevo=new Folio();
	
			nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
			//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
			List anotaciones = evento.getAnotaciones();
			Iterator it=anotaciones.iterator();
			while(it.hasNext()){
				Anotacion anota=(Anotacion)it.next();
				nuevo.addAnotacione(anota);
			}
			try {
				if (forseti.updateFolio(nuevo,usuarioNeg, null, false)){
					FolioPk id=new FolioPk();
					id.idMatricula=evento.getFolio().getIdMatricula();
					devuelve = this.forseti.getFolioByID(id, usr);
// No necesita las anotaciones debido a que el método no se usa					
					listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);
				}
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANFolio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
				throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
			
			}
		return new EvnRespFolio(devuelve, listaATemp);
	}

}
