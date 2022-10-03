package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento utilizado para el envio de solicitudes con respecto a folios, 
 * a la capa de negocio
 * 
 */
public class EvnRespNextOrdenAnotacion extends EvnSIRRespuesta {

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespNextOrdenAnotacion(String orden, String tipoEvento) {
		super(orden, tipoEvento);
	}    

}
