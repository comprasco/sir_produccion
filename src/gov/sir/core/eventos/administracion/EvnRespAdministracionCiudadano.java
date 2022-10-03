
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con la administración de objetos de Hermod (CiudadanoProhibicion, Prohibicion).
 * @author jmendez
 */
public class EvnRespAdministracionCiudadano extends EvnSIRRespuesta {
	
	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de una prohibición */
	public static final String PROHIBICION_CREAR_OK = "PROHIBICION_CREAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de una prohibición */
	public static final String PROHIBICION_ELIMINAR_OK = "PROHIBICION_ELIMINAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de un ciudadano */
	public static final String CIUDADANO_CONSULTAR_OK = "CIUDADANO_CONSULTAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de una prohibición para un ciudadano */
	public static final String CIUDADANO_ADICIONAR_OK = "CIUDADANO_ADICIONAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de una prohibición de un ciudadano */
	public static final String CIUDADANO_ELIMINAR_OK = "CIUDADANO_ELIMINAR_OK";

	public static final String CIUDADANO_CREAR_OK = "CIUDADANO_CREAR_OK";

	/**
	 * @param payload
	 */
	public EvnRespAdministracionCiudadano(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespAdministracionCiudadano(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

}
