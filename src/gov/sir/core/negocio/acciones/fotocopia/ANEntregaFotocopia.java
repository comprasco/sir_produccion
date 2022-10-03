package gov.sir.core.negocio.acciones.fotocopia;


import java.util.Hashtable;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.eventos.fotocopia.EvnEntregaFotocopia;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

/**
 * @author dlopez
 * Clase para el manejo de la lógica de las fases de fotocopiado y  entrega de fotocopias
 * dentro del proceso de fotocopias.
 */
public class ANEntregaFotocopia extends SoporteAccionNegocio{


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
	 *Constructor de la clase ANEntregaFotocopia
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANEntregaFotocopia() throws EventoException {

		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null){
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		}
		catch (ServiceLocatorException e){
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null){
			throw new ServicioNoEncontradoException(
				"Servicio Hermod no encontrado");
		}


	}




	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnEntregaFotocopia evento = (EvnEntregaFotocopia) e;

		if (evento==null || evento.getTipoEvento()==null){
			return null;
		}

		if(evento.getTipoEvento().equals(EvnEntregaFotocopia.ENTREGAR_FOTOCOPIAS)){
			return entregarFotocopias (evento);
		}

		if(evento.getTipoEvento().equals(EvnEntregaFotocopia.FOTOCOPIAR_DOCUMENTO)){
			return fotocopiarDocumento (evento);
		}

		return null;
	}



	private EvnRespFotocopia fotocopiarDocumento (EvnEntregaFotocopia evento) throws EventoException {

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Turno turno = null;

		try {

			turno = evento.getTurno();

			if (turno == null) {
				throw new EventoException ("Se recibió un turno inválido en ANEntregaFotocopias");
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
 
			//Se llama el servicio hermod para el avance de turnos.

			
			/*************************************************************************/
			/*                          ELIMINAR SAS                                 */
			/*************************************************************************/
		
			Fase fase = evento.getFase();

			gov.sir.core.negocio.modelo.Usuario usuarioSIR = evento.getUsuarioSIR();

			Hashtable tabla = new Hashtable();
			tabla.put("RESULT", evento.getRespuestaWF());
		
			try 
			{
				hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,usuarioSIR);
			
			}
	        catch (Throwable e) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",e);
		    }
		}
		catch (Throwable e) {
		 throw new EventoException ("Error avanzando el turno en el servicio hermod.",e);

		}


		EvnRespFotocopia eventoRespuesta = new EvnRespFotocopia(evento.getUsuario(), turno, EvnRespFotocopia.FOTOCOPIAR_DOCUMENTO);

		return eventoRespuesta;
	}




	private EvnRespFotocopia entregarFotocopias (EvnEntregaFotocopia evento) throws EventoException {

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Turno turno = null;

		try {

			turno = evento.getTurno();

			if (turno == null) {
				throw new EventoException ("Se recibió un turno inválido en ANEntregaFotocopias");
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
        




			/*************************************************************************/
			/*                          ELIMINAR SAS                                 */
			/*************************************************************************/
		
			Fase fase = evento.getFase();

			gov.sir.core.negocio.modelo.Usuario usuarioSIR = evento.getUsuarioSIR();

			Hashtable tabla = new Hashtable();
			tabla.put("RESULT", evento.getRespuestaWF());
		
			try 
			{
				hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,usuarioSIR);
			
			}
	        catch (Throwable e) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",e);
		    }

		}
		

		catch (Throwable e) {
		 throw new EventoException ("Error avanzando el turno en el servicio hermod.",e);

		}


		EvnRespFotocopia eventoRespuesta = new EvnRespFotocopia(evento.getUsuario(), turno, EvnRespFotocopia.FOTOCOPIAR_DOCUMENTO);

		return eventoRespuesta;
	}



}
