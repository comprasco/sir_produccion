package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Envio de solicitudes de respuesta con respecto a la fase de confrontación en el proceso de 
 * correcciones desde la capa de negocio a la capa web
 * @author ppabon
 */
public class EvnRespConfrontacion extends EvnSIRRespuesta {
	public static final String TURNO="TURNO";

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespConfrontacion(Object payload) {
		super(payload, TURNO);
	}

}
