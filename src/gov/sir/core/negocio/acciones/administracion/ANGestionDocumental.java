package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.administracion.EvnGestionDocumental;
import gov.sir.core.eventos.administracion.EvnRespGestionDocumental;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import java.util.ArrayList;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 *
 * @author Ellery R
 */
public class ANGestionDocumental extends SoporteAccionNegocio {

    /** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

    /**
     * Constructor encargado de inicializar los servicios a ser utilizados por la
     * acción de Negocio
     * @throws EventoException
     */
    public ANGestionDocumental() throws EventoException {
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

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}
	}


    /**
     * Manejador de eventos de tipo <code>EvnGestionDocumental</code>.
     * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
     * al tipo de evento que llegue a la acción de negocio.  Este método redirige
     * la acción a otros métodos en la clase de acuerdo al tipo de evento
     * que llega como parámetro.
     * @throws <code>EventoException</code>
     */
    public EventoRespuesta perform(Evento e) throws EventoException {

        EvnGestionDocumental evento = (EvnGestionDocumental) e;

        if (evento == null || evento.getTipoEvento() == null) {
            return null;
        }else if (evento.getTipoEvento().equals(EvnGestionDocumental.TURNOS_GESTION_DOCUMENTAL)) {
             return turnosSGD(evento);
        }else if (evento.getTipoEvento().equals(EvnGestionDocumental.TURNO_REENCOLAR)) {
                return turnosReencolarSGD(evento);
        }else if (evento.getTipoEvento().equals(EvnGestionDocumental.SELECCIONA_TURNOS_POR_FECHA)) {
             return seleccionaTurnosPorFechaSGD(evento);
        }else if (evento.getTipoEvento().equals(EvnGestionDocumental.DEPURAR_TURNOS_POR_FECHA)) {
             return depurarTurnosPorFechaSGD(evento);
        }
        return null;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta turnosSGD(EvnGestionDocumental evento) throws EventoException{
        List datos = null;
        List datos1 = null;
        List todos = new ArrayList();

        ValidacionesSIR validacion = new ValidacionesSIR();
        try {
            datos = validacion.TurnosSGD_NoLeidos(evento.getCirculo());
            datos1 = validacion.TurnosSGD_Leidos(evento.getCirculo());
            todos.add(datos);
            todos.add(datos1);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANGestionDocumental.class.getName()).log(Level.SEVERE, null, ex);
        }
       EvnRespGestionDocumental evRespuesta =
			new EvnRespGestionDocumental(todos, EvnRespGestionDocumental.TURNOS_GESTION_DOCUMENTAL_OK);
      return evRespuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta turnosReencolarSGD(EvnGestionDocumental evento) throws EventoException{
        List datos = null;
        List datos1 = null;
        List todos = new ArrayList();
        String exito = "0";
        ValidacionesSIR validacion = new ValidacionesSIR();
        try {
            exito = validacion.TurnosReencolarSGD(evento.getAnio(), evento.getCirculo(), evento.getProceso(), evento.getTurno());
            datos = validacion.TurnosSGD_NoLeidos(evento.getCirculo());
            datos1 = validacion.TurnosSGD_Leidos(evento.getCirculo());
            todos.add(datos);
            todos.add(datos1);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANGestionDocumental.class.getName()).log(Level.SEVERE, null, ex);
        }
       EvnRespGestionDocumental evRespuesta =
			new EvnRespGestionDocumental(todos, EvnRespGestionDocumental.TURNO_REENCOLAR);
      return evRespuesta;
    }

    /**
     * @param evento
     * @return String
     */
    private EventoRespuesta seleccionaTurnosPorFechaSGD(EvnGestionDocumental evento)
            throws EventoException{

        List todos = new ArrayList();
        //String datos[];
        //datos = new String[2];
        String numeroTurnosSeleccionados = null;
        ValidacionesSIR validacion = new ValidacionesSIR();
        try {
            todos.add(evento.getFechaInicio().toString());
            todos.add(evento.getFechaFin().toString());
            numeroTurnosSeleccionados = validacion.TurnosPorFechaSGD(evento.getFechaInicio().toString(), evento.getFechaFin().toString());
            todos.add(numeroTurnosSeleccionados);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANGestionDocumental.class.getName()).log(Level.SEVERE, null, ex);
        }
       EvnRespGestionDocumental evRespuesta =
			new EvnRespGestionDocumental(todos, EvnRespGestionDocumental.SELECCIONA_TURNOS_POR_FECHA);
      return evRespuesta;
    }

    /**
     * @param evento
     * @return String
     */
    private EventoRespuesta depurarTurnosPorFechaSGD(EvnGestionDocumental evento)
            throws EventoException{
        String turnosDepurador = "0";
        ValidacionesSIR validacion = new ValidacionesSIR();
        try {
            turnosDepurador = validacion.TurnosDepurarPorFechaSGD(evento.getFechaInicio().toString(), evento.getFechaFin().toString());
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANGestionDocumental.class.getName()).log(Level.SEVERE, null, ex);
        }
       EvnRespGestionDocumental evRespuesta =
			new EvnRespGestionDocumental(turnosDepurador, EvnRespGestionDocumental.DEPURAR_TURNOS_POR_FECHA);
      return evRespuesta;
    }

}
