
package gov.sir.core.negocio.acciones.registro;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;
import gov.sir.core.eventos.registro.EvnNextOrdenAnotacion;
import gov.sir.core.eventos.registro.EvnRespNextOrdenAnotacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

/**
 * Esta accion de negocio permite conocer el codigo de orden de la siguiente anotacion en un folio
 * @author dsalas
*/
public class ANNextOrdenAnotacion extends SoporteAccionNegocio {
	private ServiceLocator service = null;
	private ForsetiServiceInterface forseti;
	
	/**
	 * Crea una nueva instancia de la accion de negocio 
	 */
	public ANNextOrdenAnotacion() {

		try {
			service = ServiceLocator.getInstancia();
			forseti = (ForsetiServiceInterface)service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			Log.getInstance().error(ANNextOrdenAnotacion.class,e);
		}
		
	}

	/**
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento ev) throws EventoException {
		EvnNextOrdenAnotacion evento = (EvnNextOrdenAnotacion) ev;
		
		if (evento.getTipoEvento().equals(EvnNextOrdenAnotacion.NEXT_ORDEN_ANOTACION))
			return nextOrdenAnotacion(evento);
		else
			throw new EventoException("No se encontro el tipo de evento " + evento.getTipoEvento());
	}

	/**
	 * @param ev
	 * @return
	 */
	private EventoRespuesta nextOrdenAnotacion(Evento ev) throws EventoException {
		try {
			EvnNextOrdenAnotacion evento = (EvnNextOrdenAnotacion)ev;
			//SACAR FOLIO DEL EVENTO
			Folio folio = evento.getFolio();
			//LLENAR FOLIOID
			FolioPk folioID = new FolioPk();
			folioID.idMatricula=folio.getIdMatricula();
			//SOLICITAR AL SERVICIO
			long orden = forseti.getNextOrdenAnotacion(folioID);
			//CAST
			String sOrden=String.valueOf(orden);
			
			return new EvnRespNextOrdenAnotacion(sOrden,evento.getTipoEvento()); 
		} catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANNextOrdenAnotacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
	}
	
}