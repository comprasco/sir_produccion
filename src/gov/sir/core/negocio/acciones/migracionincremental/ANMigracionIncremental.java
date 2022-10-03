package gov.sir.core.negocio.acciones.migracionincremental;

import java.util.List;

import gov.sir.core.eventos.migracionincremental.EvnMigracionIncremental;
import gov.sir.core.eventos.migracionincremental.EvnRespMigracionIncremental;
import gov.sir.core.negocio.acciones.excepciones.ANSeguridadException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.excepciones.MigracionIncrementalException;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.dao.DAOException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

public class ANMigracionIncremental extends SoporteAccionNegocio {

	/**
	 * Instancia del ServiceLocator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de la intefaz de Forseti
	 */
	private ForsetiServiceInterface forseti;

	
	/**
	* Constructor de la clase ANMigracionIncremental.
	* Es el encargado de invocar al Service Locator y pedirle una instancia
	* del servicio que necesita
	*/
	public ANMigracionIncremental() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} 
		catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

	}
/*
	public EventoRespuesta perform(Evento evento) throws EventoException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	/**
	 * Recibe un evento de certificado y devuelve un evento de respuesta
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @throws ANSeguridadException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @see gov.sir.core.eventos.comun.EvnCertificado
	 * @see gov.sir.core.eventos.comun.EvnRespCertificado
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {

		if (e == null)
			return null;

		if (e instanceof EvnMigracionIncremental) {
			EvnMigracionIncremental evento = (EvnMigracionIncremental) e;
			if (evento.getTipoEvento() == null){
				return null;
			}

			String tipoEvento = evento.getTipoEvento();
			if (tipoEvento.equals(EvnMigracionIncremental.TIPO_MIGRAR_FOLIO)) {
				return this.migrarFolioByMatricula(evento);
			}
			
			if (tipoEvento.equals(EvnMigracionIncremental.TIPO_MIGRAR_TURNO)) {
				return this.migrarTurnoNumero(evento);
			}


		}
		
		return null;
	}

		
	 /**
     * Realiza la migración incremental de un Folio dado el número de matrícula.
     * @param matricula número de matrícula del folio
     * @throws DAOException
     */
    public EvnRespMigracionIncremental migrarFolioByMatricula(EvnMigracionIncremental evento) throws EventoException {
    	String matResp = null;

    	String matricula = evento.getMatriculaFolio();
    	gov.sir.core.negocio.modelo.Usuario usuarioModelo = evento.getUsuarioModelo();
    	
		try {
			matResp = this.forseti.migrarFolioByMatricula(matricula,usuarioModelo);
			
		}catch(ForsetiException fe) {
			List errores;
			errores = fe.getErrores();
			throw new MigracionIncrementalException(errores);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANMigracionIncremental.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
    	
    	EvnRespMigracionIncremental evnResp = new EvnRespMigracionIncremental(matResp);
    	return evnResp;
    	
    }
    
    /**
     * Realiza la migración incremental de un Turno dado el número del turno.
     * @param numero del turno
     * @throws DAOException
     */
    public EvnRespMigracionIncremental migrarTurnoNumero(EvnMigracionIncremental evento) throws EventoException {
      	Turno turno = null;
      	
    	String numTurno = evento.getNumTurno();
    	//gov.sir.core.negocio.modelo.Usuario usuarioModelo = evento.getUsuarioModelo();
    	
		try {
			turno = this.forseti.migrarTurnoNumero(numTurno);
			
		}catch(ForsetiException fe) {
			List errores;
			errores = fe.getErrores();
			throw new EventoException(fe.getMessage(),fe);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANMigracionIncremental.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
      	
    	EvnRespMigracionIncremental evnResp = new EvnRespMigracionIncremental(turno);
    	return evnResp;
    }
	
	
}
