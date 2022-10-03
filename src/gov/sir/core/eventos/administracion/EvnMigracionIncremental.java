package gov.sir.core.eventos.administracion;


import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con el proceso de migración incremental
 * @author ppabon
 */
public class EvnMigracionIncremental extends EvnSIR {

	/** Constante que identifica la acción de bloquear un folio porque hay un turno en 
	 * trámite en el sistema FOLIO que lo esta utilizando*/
	public static final String BLOQUEAR_TURNO_FOLIO = "BLOQUEAR_TURNO_FOLIO";
	
	/** Constante que identifica la acción de desbloquear un folio */
	public static final String DESBLOQUEAR_TURNO_FOLIO = "DESBLOQUEAR_TURNO_FOLIO";
	
	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS = "CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS";

	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String ACTUALIZAR_COMPLEMENTACION_FOLIO = "ACTUALIZAR_COMPLEMENTACION_FOLIO";


	private Turno turno;
	
	private Circulo circulo;
	
	private Proceso proceso;

	private Folio folio;
	
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	private Complementacion complementacion;



	/**
	 * Constructor del evento.
	 * @param turno
	 * @param circulo
	 * @param proceso
	 * @param folio
	 * @param usuario
	 * @param usuarioSIR
	 * @param tipoEvento
	 */
	public EvnMigracionIncremental(Turno turno , Circulo circulo, Proceso proceso, Folio folio , Usuario usuario,  gov.sir.core.negocio.modelo.Usuario usuarioSIR, String tipoEvento) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.circulo = circulo;
		this.proceso = proceso;
		this.folio = folio;
		this.usuarioSIR = usuarioSIR;
	}


	/**
	 * @param complementacion
	 * @param usuario
	 * @param usuarioSIR
	 * @param tipoEvento
	 */
	public EvnMigracionIncremental(Complementacion complementacion , Usuario usuario,  gov.sir.core.negocio.modelo.Usuario usuarioSIR, String tipoEvento) {
		super(usuario, tipoEvento);
		this.complementacion = complementacion;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param folio
	 * @param usuario
	 * @param usuarioSIR
	 * @param tipoEvento
	 */
	public EvnMigracionIncremental(Folio folio , Usuario usuario,  gov.sir.core.negocio.modelo.Usuario usuarioSIR, String tipoEvento) {
		super(usuario, tipoEvento);
		this.folio = folio;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param folio
	 * @param usuario
	 * @param usuarioSIR
	 * @param tipoEvento
	 */
	public EvnMigracionIncremental(Folio folio , Usuario usuario,  Circulo circulo ,gov.sir.core.negocio.modelo.Usuario usuarioSIR, String tipoEvento) {
		super(usuario, tipoEvento);
		this.folio = folio;
		this.circulo = circulo;
		this.usuarioSIR = usuarioSIR;
	}
	
	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}



	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}



	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}



	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}



	/**
	 * @return
	 */
	public Proceso getProceso() {
		return proceso;
	}



	/**
	 * @param proceso
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}



	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}



	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}



	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}



	/**
	 * @param usuarioSIR
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}


	public Complementacion getComplementacion() {
		return complementacion;
	}


	public void setComplementacion(Complementacion complementacion) {
		this.complementacion = complementacion;
	}
	
	

}
