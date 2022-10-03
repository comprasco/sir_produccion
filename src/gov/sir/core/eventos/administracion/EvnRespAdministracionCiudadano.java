
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con la administraci�n de objetos de Hermod (CiudadanoProhibicion, Prohibicion).
 * @author jmendez
 */
public class EvnRespAdministracionCiudadano extends EvnSIRRespuesta {
	
	/**Esta constante se utiliza  para identificar el evento de creaci�n satisfactoria de una prohibici�n */
	public static final String PROHIBICION_CREAR_OK = "PROHIBICION_CREAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de eliminaci�n satisfactoria de una prohibici�n */
	public static final String PROHIBICION_ELIMINAR_OK = "PROHIBICION_ELIMINAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de un ciudadano */
	public static final String CIUDADANO_CONSULTAR_OK = "CIUDADANO_CONSULTAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de creaci�n satisfactoria de una prohibici�n para un ciudadano */
	public static final String CIUDADANO_ADICIONAR_OK = "CIUDADANO_ADICIONAR_OK";
	
	/**Esta constante se utiliza  para identificar el evento de eliminaci�n satisfactoria de una prohibici�n de un ciudadano */
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
