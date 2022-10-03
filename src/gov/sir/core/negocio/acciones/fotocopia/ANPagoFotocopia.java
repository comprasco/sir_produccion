package gov.sir.core.negocio.acciones.fotocopia;


import java.util.Hashtable;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.fotocopia.EvnFotocopia;
import gov.sir.core.eventos.fotocopia.EvnPagoFotocopia;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

/**
 * @author dlopez, ahurtado
 */
public class ANPagoFotocopia extends SoporteAccionNegocio{


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
	 *Constructor de la clase ANPagoFotocopia
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANPagoFotocopia() throws EventoException {

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




	public
	EventoRespuesta perform( Evento e )
	throws EventoException {
		EvnPagoFotocopia evento = (EvnPagoFotocopia) e;

		if (evento==null || evento.getTipoEvento()==null){
			return null;
		}

		if(evento.getTipoEvento().equals(EvnFotocopia.CANCELAR_PAGO)){
			return negarPagoFotocopia (evento);
		}

		if( EvnFotocopia.PAGO_FOTOCOPIAS_ZERO_VALUE.equals( evento.getTipoEvento() ) ) {
		return pagoFotocopiaZeroValue( evento );
		}

		return null;
	}

   /**
	 * @deprecated Se esta manejando dentro de Pago.
	 * @see package gov.sir.core.negocio.acciones.comun.ANPago
	 */
	private EvnRespFotocopia
	pagoFotocopiaZeroValue( EvnPagoFotocopia evento )
	throws EventoException {
		return null;
	}


	/**
	 * Usado en la administracion de solicitudes de fotocopias
	 * cuando el periodo de pago de la fotocopia
	 * supera un threshold deteminado.
	 */
	private EvnRespFotocopia
	negarPagoFotocopia( EvnPagoFotocopia evento )
	throws EventoException {

		if( hermod == null ) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Solicitud solicitud = null;
		Turno turno = null;
		Fase fase = null;
		LiquidacionTurnoFotocopia liquidacion = new LiquidacionTurnoFotocopia();


		try {

            EvnRespFotocopia eventoFotocopia = null;
			turno = (Turno) evento.getTurno();
			fase = evento.getFase();
			gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
			if (turno == null)
			{
				throw new EventoException("No se pudo obtener información del turno en ANPagoFotocopia");
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
        
			
			
			    Hashtable parametros = new Hashtable();
				Turno turnoAvance       = evento.getTurno();
				Fase faseAvance        = evento.getFase();
				String respuestaWf = evento.getRespuestaWF();
				parametros.put( Processor.RESULT ,respuestaWf );
				
				hermod.avanzarTurnoNuevoNormal(turnoAvance,faseAvance,parametros,evento.getUsuarioSIR());

		    }

		    catch (HermodException e) {
				throw new EventoException ("No se pudo avanzar el turno hacia terminación",e);
			}

 		    catch (Throwable e) {
			    ExceptionPrinter printer = new ExceptionPrinter(e);
			    Log.getInstance().error(ANPagoFotocopia.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			    throw new EventoException(e.getMessage(),e);
		    }

		EvnRespFotocopia eventoRespuesta = new EvnRespFotocopia(evento.getUsuario(), turno, EvnRespFotocopia.TERMINAR_TURNO);

		return eventoRespuesta;
	}




}
