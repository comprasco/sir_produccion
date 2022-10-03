package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRespTurnoManualCertificado;
import gov.sir.core.eventos.administracion.EvnTurnoManualCertificado;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.SolicitudConsultaNoCreadaException;
import gov.sir.core.negocio.acciones.excepciones.TurnoManualRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoProcesoPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.util.Log;
//import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;


/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ANTurnoManualCertificado extends SoporteAccionNegocio {

    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de Fenrir
     */
    //private FenrirServiceInterface fenrir;

    /**
     *Constructor de la clase ANLiquidacion.
     *Es el encargado de invocar al Service Locator y pedirle una instancia
     *del servicio que necesita
     */
    public ANTurnoManualCertificado() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();

        try {
            hermod = (HermodServiceInterface) service.getServicio(
                    "gov.sir.hermod");

            if (hermod == null) {
                throw new ServicioNoEncontradoException(
                    "Servicio Hermod no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod",
                e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException(
                "Servicio Hermod no encontrado");
        }

        try {
            forseti = (ForsetiServiceInterface) service.getServicio(
                    "gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException(
                    "Servicio forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio forseti",
                e);
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException(
                "Servicio forseti no encontrado");
        }
    }

    /**
    * Recibe un evento de seguridad y devuelve un evento de respuesta
    * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
    * @throws EventoException cuando ocurre un problema que no se pueda manejar
    * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
    * @see gov.sir.core.eventos.comun.EvnLiquidacion
    * @see gov.sir.core.eventos.comun.EvnRespLiquidacion
    */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnTurnoManualCertificado evento = (EvnTurnoManualCertificado) e;

        if ((evento == null) || (evento.getTipoEvento() == null)) {
            return null;
        }

        if (evento.getTipoEvento().equals(EvnTurnoManualCertificado.LIQUIDAR)) {
            return liquidar(evento);
        } else if (evento.getTipoEvento().equals(EvnTurnoManualCertificado.SOLICITAR_LIQUIDAR)) {
            return solicitarLiquidar(evento);
        } else if (evento.getTipoEvento().equals(EvnTurnoManualCertificado.ASOCIAR_TURNO)) {
        	return asociarTurno(evento);
        }

        return null;
    }

    private EventoRespuesta asociarTurno(EvnTurnoManualCertificado evento) throws EventoException {
		
    	Turno turno = null;
    	
    	try {
    		boolean esTurnoManual = false;
    		turno = hermod.getTurnobyWF(evento.getIdTurno());
    		
    		if(turno != null) {
    			List historial = turno.getHistorials();
    			if(historial != null) {
    				TurnoHistoria th = null;
    				for(Iterator itHistorial = historial.iterator(); itHistorial.hasNext();) {
    					th = (TurnoHistoria)itHistorial.next();
    					if(th.getFase() != null && th.getFase().equals(CFase.REG_TMAN_CREACION) ||
    							th.getFase().equals(CFase.REG_TMAN_CIERRE)) {
    						esTurnoManual = true;
    						break;
    					}
    				}
    			}
    		}
    		
    		if(turno == null)
    			throw new TurnoManualRegistroException("El Turno " + evento.getIdTurno() + " no existe");
    		if(!esTurnoManual)
    			throw new TurnoManualRegistroException("El Turno " + evento.getIdTurno() + " al que se quiere asociar el Certificado no es un Turno Manual de Registro");
    	} catch(HermodException e) {
            throw new EventoException(e.getMessage(), e);
    	} catch (Throwable e) {
            throw new EventoException(e.getMessage(), e);
        }
    	
    	EvnRespTurnoManualCertificado evRespuesta = new EvnRespTurnoManualCertificado(turno,
                EvnRespTurnoManualCertificado.ASOCIAR_TURNO_OK);
    	
    	return evRespuesta;
	}

	private EventoRespuesta solicitarLiquidar(EvnTurnoManualCertificado evento)
        throws EventoException {
        try {
            SolicitudConsulta solicitud = (SolicitudConsulta) hermod.crearSolicitud(evento.getLiquidacion()
                                                                                          .getSolicitud());

            if (solicitud == null) {
                throw new SolicitudConsultaNoCreadaException("No se pudo actualizar la búsqueda en la solicitud de consulta",
                    null);
            }

            evento.getLiquidacion().setSolicitud(solicitud);
        } catch (HermodException e) {
            Log.getInstance().error(ANTurnoManualCertificado.class,e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANTurnoManualCertificado.class,e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        return liquidar(evento);
    }

    /**
     * Este metodo hace el llamado al negocio para que se haga la liquidacion de la solicitud
     * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
     */
    private EventoRespuesta liquidar(EvnTurnoManualCertificado evento)
        throws EventoException {
        EvnRespTurnoManualCertificado evRespuesta = null;

        Liquidacion liquidacion = evento.getLiquidacion();

        if (liquidacion == null) {
            throw new LiquidacionNoEfectuadaException(
                "No existe liquidación asociada");
        }

        Solicitud solicitud = liquidacion.getSolicitud();

        if (solicitud == null) {
            throw new LiquidacionNoEfectuadaException(
                "No existe solicitud asociada");
        }

        try {
            TurnoPk turnoId = new TurnoPk();
            turnoId.anio = evento.getAnio();
            turnoId.idCirculo = evento.getIdCirculo();
            turnoId.idProceso = evento.getProceso().getIdProceso();
            turnoId.idTurno = evento.getIdTurno();

            Calendar cal = Calendar.getInstance();
            int iAnio = cal.get(Calendar.YEAR);

            if (iAnio == Integer.parseInt(evento.getAnio())) {
                CirculoProcesoPk cpId = new CirculoProcesoPk();
                cpId.anio = evento.getAnio();
                cpId.idCirculo = evento.getIdCirculo();
                cpId.idProceso = evento.getProceso().getIdProceso();

                CirculoProceso cp = hermod.getCirculoProceso(cpId);

                if (cp.getLastIdTurno() <= Long.parseLong(evento.getIdTurno())) {
                    throw new LiquidacionNoEfectuadaException(
                        "El consecutivo del turno no puede ser mayor al valor del consecutivo actual");
                }
            }

            Turno t = hermod.getTurno(turnoId);

            if (t != null) {
                throw new LiquidacionNoEfectuadaException("El turno " + t.getIdWorkflow() + " ya existe");
            }

            List solFolio = solicitud.getSolicitudFolios();
            List matriculas = new Vector();
            Iterator itSolFolio = solFolio.iterator();

            while (itSolFolio.hasNext()) {
                SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                matriculas.add(sol.getFolio().getIdMatricula());
            }

            forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud),
                matriculas);
            liquidacion.setUsuario(evento.getUsuarioSIR());
            liquidacion = hermod.liquidar(liquidacion);
            evRespuesta = new EvnRespTurnoManualCertificado(liquidacion,
                    EvnRespTurnoManualCertificado.LIQUIDACION);
        } catch (HermodException e) {
            throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
        } catch (ForsetiException e) {
            throw new ValidacionParametrosHTException(e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANTurnoManualCertificado.class,"Ha ocurrido una excepcion inesperada " +
                printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        if (liquidacion.getValor() != 0) {
            return evRespuesta;
        }

        return evRespuesta;
    }
}
