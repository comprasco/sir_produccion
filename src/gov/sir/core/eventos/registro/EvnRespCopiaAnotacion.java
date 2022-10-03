package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;


/**
 * Evento utilizado para el envio del resultado de la copia de anotaciones desde la capa de negocio a la capa web 
  * @author ppabon
 */
public class EvnRespCopiaAnotacion extends EvnSIRRespuesta {
	
	/**
	* Acción que indica que se desea copiar una anotación a otros folios.
	*/
	public static final String COPIAR_ANOTACION ="COPIAR_ANOTACION";	

	

	/**
	 * Crea el evento de respuesta
	 * @param orden Este es el payload String
	 * @param tipoEvento
	 */
	public EvnRespCopiaAnotacion(String orden, String tipoEvento) {
		super(orden, tipoEvento);
	}    

	
	/**
	 * Crea el evento de respuesta
	 * @param Object payload
	 * @param tipoEvento
	 */
	public EvnRespCopiaAnotacion(Object object, String tipoEvento) {
		super(object, tipoEvento);
	}    		


}
