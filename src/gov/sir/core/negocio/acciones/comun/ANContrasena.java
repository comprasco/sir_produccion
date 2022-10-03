package gov.sir.core.negocio.acciones.comun;

import java.util.List;

import gov.sir.core.eventos.comun.EvnContrasena;
import gov.sir.core.eventos.comun.EvnRespContrasena;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.interfaz.PrintJobsInterface;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

public class ANContrasena extends SoporteAccionNegocio{
	
	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

	/**
	 * Instancia de PrintJobs
	 */
	private PrintJobsInterface printJobs;
	
	/**
	 *Constructor de la clase <code>ANContrasena</code>.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 * @throws EventoException
	 */
	public ANContrasena() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
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
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
		}

		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}
		
		try {
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
			
			if(printJobs == null) {
				throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJbos");
			}
		} catch(ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
		}

	}
	
	/**
	 * Manejador de eventos de tipo <code>EvnContrasena</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento
	 * que llega como parámetro.
	 *
	 * @param e <code>EvnContraseba</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 *
	 * @return <code>EventoRespuesta</code> con la información resultante de la
	 * ejecución de la acción sobre los servicios
	 *
	 * @throws EventoException
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnContrasena evento = (EvnContrasena) e;
		String tipoEvento = evento.getTipoEvento();

		if (evento == null || tipoEvento == null) {
			return null;
		}
		if (tipoEvento.equals(EvnContrasena.LISTAR_OPLOOKUP_CODES_MESA_AYUDA)) {
			return listarOPLookupCodes(evento);
		}
		return null;
	}
	/**
	 * Este método se encarga de traer la lista de OPLookupCodes  de un tipo
	 * específico de <code>OPLookupCode</code> en este caso de mesa de ayuda
	 *
	 * @param evento de tipo <code>EvnContrasena</code> con el
	 * <code>TipoTarifa</code> ser consultado.
	 *
	 * @return <code>EvnRespContrasena</code> con la lista de tarifas de los OPLookupCodes
	 *
	 * @throws EventoException
	 */
	private EvnRespContrasena listarOPLookupCodes(EvnContrasena evento)
		throws EventoException {

		List datos = null;

		try {
			datos = hermod.getOPLookupCodes(evento.getOpLookupType().getTipo());
		} catch (HermodException e) {
			Log.getInstance().error(ANContrasena.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANContrasena.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		EvnRespContrasena evRespuesta = null;
		evRespuesta=
				new EvnRespContrasena(datos, EvnRespContrasena.LISTADO_LOOKUP_CODES_MESA_AYUDA_OK);
		return evRespuesta;
	}

}
