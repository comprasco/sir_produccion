package gov.sir.core.negocio.acciones.administracion;


import java.util.ArrayList;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnMigracionIncremental;
import gov.sir.core.eventos.administracion.EvnRespMigracionIncremental;
import gov.sir.core.negocio.acciones.excepciones.BloqueoTurnoFolioException;
import gov.sir.core.negocio.acciones.excepciones.ConflictoComplementacionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolioTramiteMig;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;


/**
 * Acción de negocio encargada de manejar los eventos de tipo 
 * <code>EvnMigracionIncremental</code> destinados a manejar 
 * el proceso de migración incremental.  
 * 
 * @author ppabon
 *
 */
public class ANMigracionIncremental extends SoporteAccionNegocio {

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;
	
	
	/**
	 *Constructor de la clase <code>ANMigracionIncremental</code>.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANMigracionIncremental() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
			
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

	}

	/**
	 * Manejador de eventos de tipo <code>EvnMigracionIncremental</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento 
	 * que llega como parámetro.
	 * 
	 * @param evento <code>EvnMigracionIncremental</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 * 
	 * @return <code>EventoRespuesta</code> con la información resultante de la 
	 * ejecución de la acción sobre los servicios
	 * 
	 * @throws <code>EventoException</code> 
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnMigracionIncremental evento = (EvnMigracionIncremental) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnMigracionIncremental.BLOQUEAR_TURNO_FOLIO)) {
			return bloquearTurnoFolio(evento);
		} else if (tipoEvento.equals(EvnMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO)) {
			return desbloquearTurnoFolio(evento);
		} else if (tipoEvento.equals(EvnMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS)) {
			return consultarComplementacionesConflictivas(evento);
		} else if (tipoEvento.equals(EvnMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO)) {
			return actualizarComplementacionesConflictivas(evento);
		}

		return null;
	}

	/**
	 * Este método se encarga bloqeuar un folio que se encuentra en trámite en el sistema FOLIO.
	 * 
	 * @param evento de tipo <code>EvnMigracionIncremental</code>.
	 * 
	 * @return <code>EvnRespMigracionIncremental</code> 
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespMigracionIncremental bloquearTurnoFolio(EvnMigracionIncremental evento) throws EventoException {

		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		Folio folio = evento.getFolio();
		Proceso proceso = evento.getProceso();
		List turnosFolio = new ArrayList();
		
		boolean rta = false;

		try {
			
			String turnoMig = turno.getAnio()+ "-" + turno.getIdTurno();			
			if(turno.getIdProceso()==new Long (CProceso.PROCESO_CORRECCIONES).longValue()){
				if(!turnoMig.startsWith("C")){
					turnoMig  = "C"+turnoMig;	
				}								
			}
			
			String idCirculo = folio.getIdMatricula().substring(0,folio.getIdMatricula().indexOf("-")); 
			String idFolio = folio.getIdMatricula().substring((folio.getIdMatricula().indexOf("-") + 1), folio.getIdMatricula().length()); 
			
			TurnoFolioTramiteMig tfm = new TurnoFolioTramiteMig();
			
			tfm.setIdTurno(turnoMig);
			tfm.setIdFolio(new Long(idFolio).longValue());
			tfm.setIdProceso(proceso.getIdProceso());
			tfm.setIdCirculo(circulo.getIdCirculo());
			tfm.setAnulado(false);
			tfm.setSecProceso(0);
			turnosFolio.add(tfm);
			rta = hermod.addTramiteTurnosFolioMigToTurno(turnosFolio);
		} catch (HermodException e) {
			throw new BloqueoTurnoFolioException("No se pudo bloquear el folio por el turno en trámite en el sistema FOLIO", e);
		} catch (Throwable e) {
			throw new BloqueoTurnoFolioException(e.getMessage(), e);
		}

		EvnRespMigracionIncremental evn = new EvnRespMigracionIncremental(new Boolean(rta), EvnRespMigracionIncremental.BLOQUEAR_TURNO_FOLIO);
		return evn;
	}
	
	/**
	 * Este método se encarga bloqeuar un folio que se encuentra en trámite en el sistema FOLIO.
	 * 
	 * @param evento de tipo <code>EvnMigracionIncremental</code>.
	 * 
	 * @return <code>EvnRespMigracionIncremental</code> 
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespMigracionIncremental desbloquearTurnoFolio(EvnMigracionIncremental evento) throws EventoException {

		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		Folio folio = evento.getFolio();
		Proceso proceso = evento.getProceso();
		List turnosFolio = new ArrayList();
		
		boolean rta = false;

		try {
			
			String turnoMig = turno.getAnio()+ "-" + turno.getIdTurno();			
			if(turno.getIdProceso()==new Long (CProceso.PROCESO_CORRECCIONES).longValue()){
				if(!turnoMig.startsWith("C")){
					turnoMig  = "C"+turnoMig;	
				}								
			}
			
			String idCirculo = folio.getIdMatricula().substring(0,folio.getIdMatricula().indexOf("-")); 
			String idFolio = folio.getIdMatricula().substring((folio.getIdMatricula().indexOf("-") + 1), folio.getIdMatricula().length()); 
			
			TurnoFolioTramiteMig tfm = new TurnoFolioTramiteMig();
			
			tfm.setIdTurno(turnoMig);
			tfm.setIdFolio(new Long(idFolio).longValue());
			tfm.setIdProceso(proceso.getIdProceso());
			tfm.setIdCirculo(circulo.getIdCirculo());
			tfm.setAnulado(true);
			tfm.setSecProceso(0);
			turnosFolio.add(tfm);
			rta = hermod.addTramiteTurnosFolioMigToTurno(turnosFolio);
		} catch (HermodException e) {
			throw new BloqueoTurnoFolioException("No se pudo bloquear el folio por el turno en trámite en el sistema FOLIO", e);
		} catch (Throwable e) {
			throw new BloqueoTurnoFolioException(e.getMessage(), e);
		}

		EvnRespMigracionIncremental evn = new EvnRespMigracionIncremental(new Boolean(rta), EvnRespMigracionIncremental.DESBLOQUEAR_TURNO_FOLIO);
		return evn;
	}
	
	/**
	 * Este método se encarga de consultar las complementaciones conflictivas 
	 * entre el sistema SIR y el sistema FOLIO, a partir de un número de matrícula.
	 * 
	 * @param evento de tipo <code>EvnMigracionIncremental</code>.
	 * 
	 * @return <code>EvnRespMigracionIncremental</code> 
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespMigracionIncremental consultarComplementacionesConflictivas(EvnMigracionIncremental evento) throws EventoException {

		Usuario usuarioSIR = evento.getUsuarioSIR();
		Circulo circulo = evento.getCirculo();
		Folio folio = evento.getFolio();
		Folio rta = null;

		
		//SE VALIDA QUE EL USUARIO TENGA EL BLOQUEO DEL FOLIO O QUE ÉSTE(FOLIO) NO ESTE BLOQUEADO.
		try {
			if(usuarioSIR==null){
				throw new ConflictoComplementacionException("Se necesita conocer el usuario que utilizando esta opción.");	
			}
			
			CirculoPk idCirculo = new CirculoPk();
			idCirculo.idCirculo = circulo.getIdCirculo(); 
			Circulo circuloTemp = forseti.getCirculo(idCirculo);
			
			if(!circuloTemp.isProcesoMigracion()){
				throw new ConflictoComplementacionException("El circulo del folio no esta en proceso de migración incremental.");
			}
			
			FolioPk idFolio = new FolioPk();
			idFolio.idMatricula = folio.getIdMatricula();
			
			//SE VALIDA QUE EL USUARIO QUE ESTA EJECUTANDO LA OPCIÓN, SEA EL DUEÑO DEL BLOQUEO. 
			//SI ES EL DUEÑO DEL BLOQUEO SE TRAE EL FOLIO CON SUS DATOS TEMPORALES
			//SI NO TIENE BLOQUEO EL FOLIO SE OBTIENE LA INFORMACIÓN DEL FOLIO.
			Usuario usuarioBloqueo = forseti.getBloqueoFolio(idFolio);
			if(usuarioBloqueo!=null && usuarioBloqueo.getIdUsuario()!=usuarioSIR.getIdUsuario()){
				throw new ConflictoComplementacionException("El folio " + idFolio.idMatricula + " esta bloqueado por el usuario " + usuarioBloqueo.getNombreCompletoUsuario());	
			}else if(usuarioBloqueo!=null && usuarioBloqueo.getIdUsuario()==usuarioSIR.getIdUsuario()){
				rta = forseti.getFolioByIDSinAnotaciones( idFolio , usuarioSIR);				
			}else{
				rta = forseti.getFolioByIDSinAnotaciones( idFolio );	
			}			
						
		} catch (ConflictoComplementacionException e) {
			throw e;
		} catch (ForsetiException e) {
			throw new ConflictoComplementacionException("No se pudo consultar el folio que tiene conflictos.", e);
		} catch (Throwable e) {
			throw new ConflictoComplementacionException(e.getMessage(), e);
		}
		
		if(rta==null){
			throw new ConflictoComplementacionException("El folio a consultar no existe : "+folio.getIdMatricula());
		} else if(rta.getComplementacion().getComplementacionConflictiva()==null){
			throw new ConflictoComplementacionException("El folio "+folio.getIdMatricula()+" no presenta conflictos de complementación.");
		}
		
		EvnRespMigracionIncremental evn = new EvnRespMigracionIncremental(rta, EvnRespMigracionIncremental.CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS);
		return evn;
	}
	
	
	/**
	 * Este método se encarga de actualizar las complementaciones conflictivas 
	 * entre el sistema SIR y el sistema FOLIO, a partir de un número de matrícula.
	 * 
	 * @param evento de tipo <code>EvnMigracionIncremental</code>.
	 * 
	 * @return <code>EvnRespMigracionIncremental</code> 
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespMigracionIncremental actualizarComplementacionesConflictivas(EvnMigracionIncremental evento) throws EventoException {

		Usuario usuario = evento.getUsuarioSIR();
		Complementacion complementacion = evento.getComplementacion();
		
		boolean rta = false;

		try {
			
			//SE GUARDA AUDITORIA DE CÓMO ESTABA LA COMPLEMENTACIÓN ANTES DE EJECUTAR LA PANTALLA.
			
			
			//SE ACTUALIZA LA COMPLEMENTACIÓN ACORDE A CÓMO SE ENVIA EN LA COMPLEMENTACIÓN
			
			forseti.updateComplementacionConflictiva(complementacion , usuario );
			
			rta = true; 
		} catch (HermodException e) {
			throw new ConflictoComplementacionException("No se pudo bloquear el folio por el turno en trámite en el sistema FOLIO", e);
		} catch (Throwable e) {
			throw new ConflictoComplementacionException(e.getMessage(), e);
		}

		EvnRespMigracionIncremental evn = new EvnRespMigracionIncremental(new Boolean(rta), EvnRespMigracionIncremental.ACTUALIZAR_COMPLEMENTACION_FOLIO);
		return evn;
	}
	
}
