package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con el proceso de migración incremental
 * @author ppabon
 */
public class EvnRespMigracionIncremental extends EvnSIRRespuesta {

	/** Constante que identifica la acción de bloquear un folio porque hay un turno en 
	 * trámite en el sistema FOLIO que lo esta utilizando*/
	public static final String BLOQUEAR_TURNO_FOLIO = "BLOQUEAR_TURNO_FOLIO";
	
	/** Constante que identifica la acción de desbloquear un folio*/
	public static final String DESBLOQUEAR_TURNO_FOLIO = "DESBLOQUEAR_TURNO_FOLIO";
	
	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS = "CONSULTAR_FOLIO_COMPLEMENTACIONES_CONFLICTIVAS";

	/** Consultar folio para cargar complementaciones conflictivas.	 */
	public static final String ACTUALIZAR_COMPLEMENTACION_FOLIO = "ACTUALIZAR_COMPLEMENTACION_FOLIO";
	
	/**
	 * @param payload
	 */
	public EvnRespMigracionIncremental(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespMigracionIncremental(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

}
