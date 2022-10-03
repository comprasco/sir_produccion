package gov.sir.core.negocio.acciones.comun;

import java.util.Hashtable;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.comun.EvnOficinas;
import gov.sir.core.eventos.comun.EvnRespOficinas;
import gov.sir.core.negocio.acciones.excepciones.ConsultaTiposOficinaNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
/*
 * @author Carlos Torres
 * @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
 */
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

/**
 * @author mmunoz
 */
public class ANOficinas extends SoporteAccionNegocio{
	
	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;
	
	
	/**
	 *Constructor de la clase ANOficinas.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANOficinas() throws EventoException {
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
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnOficinas</CODE>
	 * @throws EventoException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespOficinas</CODE>
	 * @see gov.sir.core.eventos.comun.EvnOficinas
	 * @see gov.sir.core.eventos.comun.EvnRespOficinas
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnOficinas evento = (EvnOficinas) e;
		if(evento.getTipoEvento().equals(EvnOficinas.CARGAR_OFICINAS)){
			return darOficinas(evento);		
		} 
		return null;
	}
	
	/**
	 * Este metodo hace el llamado al negocio para que se de una hashtable con los diferentes 
	 * tipos de oficinas y oficinas dada una vereda. 
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnOficinas</CODE> 
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespOficinas</CODE>
	 */
	public EvnRespOficinas darOficinas(EvnOficinas evento) throws EventoException {
		EvnRespOficinas evnRespuesta = null;

		try {
			Hashtable oficinas = null;
			org.auriga.core.modelo.transferObjects.Usuario val = evento.getUsuario();
			
			Vereda vereda = evento.getVereda();
			VeredaPk id = new VeredaPk();
			if(vereda == null){
				id = null;
			} else {
				id.idVereda = vereda.getIdVereda();
				id.idDepartamento = vereda.getIdDepartamento();
				id.idMunicipio = vereda.getIdMunicipio();
			}
			
			oficinas = forseti.getTiposOficinaByVereda(id);
			evnRespuesta = new EvnRespOficinas(oficinas);
		} catch (ForsetiException e) {
			throw new ConsultaTiposOficinaNoEfectuadaException(e.getMessage(),e);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANOficinas.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
		return evnRespuesta;
	}
          /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
	 * Este metodo hace el llamado al negocio para que se de una hashtable con los diferentes 
	 * tipos de oficinas y oficinas dada una vereda. 
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnOficinas</CODE> 
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespOficinas</CODE>
	 */
	public EvnRespOficinas darOficina(EvnOficinas evento) throws EventoException {
		EvnRespOficinas evnRespuesta = null;

		try {
			OficinaOrigen oficinas = null;
			
                        org.auriga.core.modelo.transferObjects.Usuario val = evento.getUsuario();
			String idOficina = evento.getIdOficinaOrigen();		
			oficinas = forseti.getOficinaOrigen(idOficina);
			evnRespuesta = new EvnRespOficinas(oficinas);
		} catch (ForsetiException e) {
			throw new ConsultaTiposOficinaNoEfectuadaException(e.getMessage(),e);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANOficinas.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
		return evnRespuesta;
	}

}
