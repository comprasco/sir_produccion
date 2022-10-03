package gov.sir.core.negocio.acciones.comun;

import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;
import gov.sir.core.eventos.comun.EvnLocacion;
import gov.sir.core.eventos.comun.EvnRespLocacion;
import gov.sir.core.negocio.acciones.excepciones.ConsultaDepartamentosNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

/**
 * @author mmunoz
 */
public class ANLocacion extends SoporteAccionNegocio{


	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;
	
	
	/**
	 *Constructor de la clase ANLiquidacion.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANLocacion() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			forseti = (ForsetiServiceInterface) service.getServicio(
					"gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException(
					"Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException(
				"Error instanciando el servicio forseti",e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException(
				"Servicio forseti no encontrado");
		}
	}


	/**
	 * Recibe un evento para escoger la locacion y devuelve un evento de respuesta
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnLocacion</CODE>
	 * @throws EventoException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLocacion</CODE>
	 * @see gov.sir.core.eventos.comun.EvnLocacion
	 * @see gov.sir.core.eventos.comun.EvnRespLocacion
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnLocacion evento = (EvnLocacion) e;
		if(evento.getTipoEvento().equals(EvnLocacion.CARGAR_LOCACION)){
			return darLocaciones(evento);		
		} 
		return null;
	}

	/**
	 * Este metodo hace el llamado al negocio para que se de una lista con los diferentes 
	 * departamentos, municipios y veredas dado o no in circulo 
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnLocacion</CODE> 
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLocacion</CODE>
	 */
	private EvnRespLocacion darLocaciones(EvnLocacion evento) throws EventoException{
		EvnRespLocacion evnRespuesta = null;
		try {
			List deptos = null;
			org.auriga.core.modelo.transferObjects.Usuario val=evento.getUsuario();
			Circulo circulo = evento.getCirculo();
			CirculoPk id = new CirculoPk();
			if(circulo == null){
				id = null;
			} else {
				id.idCirculo = circulo.getIdCirculo();					
			}
			deptos = forseti.getDepartamentos(id);
			evnRespuesta = new EvnRespLocacion(deptos,circulo);
		} catch (ForsetiException e) {
			throw new ConsultaDepartamentosNoEfectuadaException(e.getMessage());
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANLocacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage());
		}
		return evnRespuesta;
	}

}
