package gov.sir.fenrir;

/**
* Clase para el manejo de los diferentes tipos de excepciones lanzadas
* durante la utilización de los servicios de hermod.
* @author  jfrias. jmendez
*/
public class FenrirException extends Exception {

	/********************************************************************/

	/*          Excepciones lanzadas en métodos analizadores.           */

	/********************************************************************/

	/**
	* Excepción generada cuando un usuario inválido trata de registrarse en el sistema
	*/
	public static final String USUARIO_INVALIDO = "Usuario Inválido";

	/**
	* Excepción generada cuando un usuario que está bloqueado trata de registrarse en el sistema
	*/
	public static final String USUARIO_BLOQUEADO = "Usuario Bloqueado";

	/**
	* Excepción generada cuando hay un error de conexión con el directorio LDAP
	*/
	public static final String ERROR_CONEXION_DIRECTORIO =
		"Imposible obtener conexión con el Directorio";

	/**
	* Excepción generada cuando No es posible agregar auditoría
	*/
	public static final String ERROR_AUDITORIA = "No fue posible agregar auditoría";

	/**
	* Excepción generada cuando No es posible  obtener el listado de estaciones
	*/
	public static final String LISTADO_ESTACIONES_NO_OBTENIDO =
		"No fue posible obtener el listado de estaciones";

	/**  Excepción generada cuando No es posible  efectuar el log out  	*/
	public static final String LOGOUT_NO_EFECTUADO = "No fue posible efectuar log out";

	/**  Excepción generada cuando El usuario no existe  	*/
	public static final String ID_USUARIO_INVALIDO = "El usuario no existe";

	/**  Excepción generada cuando El usuario No pudo crearse el nivel 	*/
	public static final String NIVEL_NO_CREADO = "No pudo crearse el nivel.";

	/**  Excepción generada cuando No pudo eliminarse el nivel 	*/
	public static final String NIVEL_NO_ELIMINADO = "No pudo eliminarse el nivel.";

	/**  Excepción generada cuando No se pudo consultar la lista de niveles 	*/
	public static final String NIVEL_NO_CONSULTADO = "No se pudo consultar la lista de niveles";

	/**  Excepción generada cuando No se pudo consultar la lista de estaciones	*/
	public static final String ESTACION_NO_CONSULTADA = "No se pudo consultar la lista de estaciones";

	/**  Excepción generada cuando No pudo crearse la Responsabilidad	*/
	public static final String RESPONSABILIDAD_NO_CREADA = "No pudo crearse la Responsabilidad.";

	/**  Excepción generada cuando No pudo crearse el rol	*/
	public static final String ROL_NO_CREADO = "No pudo crearse el rol.";

	/**  Excepción generada cuando No pudo consultarse las estaciones del circulo 	*/
	public static final String ESTACIONES_POR_CIRCULO_NO_CONSULTADAS =
		"No pudo consultar las estaciones del círculo.";

	/**  Excepción generada cuando No pudo consultar los usuarios por el círculo y rol	*/
	public static final String USUARIOS_POR_CIRCULO_ROL_NO_CONSULTADOS =
		"No pudo consultar los usuarios por el círculo y rol.";

	/**  Excepción generada cuando No pudo consultar los usuarios por el círculo proporcionado	*/
	public static final String USUARIOS_POR_CIRCULO_NO_CONSULTADOS =
		"No pudo consultar los usuarios por el círculo proporcionado.";
        
        /**  Excepción generada cuando No pudo consultar los campos de captura para una forma de pago	*/
	public static final String CAMPOS_CAPTURA_NO_CONSULTADOS =
		"No pudo consultar los campos de captura para la forma de pago proporcionado.";
        
        /**  Excepción generada cuando No pudo actualizar la relacion campo captura y forma pago	*/
	public static final String CAMPOS_CAPTURA_NO_ACTUALIZADOS =
		"No pudo actualizar la relacion campo captura y forma de pago.";
        
        /**  Excepción generada cuando No pudo consultar los campos de captura del sistema	*/
	public static final String CARGA_CAMPOS_CAPTURA_NO_CONSULTADOS =
		"No pudo consultar los campos de captura del sistema.";
        
        /**  Excepción generada cuando No pudo crear la forma pago	*/
	public static final String FORMA_PAGO_NO_CREADA =
		"No pudo crear la forma de pago.";

	/**  Excepción generada cuando No pudo consultar los usuarios por el círculo proporcionado	*/
	public static final String USUARIOS_NO_ENCONTRADO = "No se encontró el usuario solicitado.";

	/**  Excepción generada cuando No pudo validarse si el usuario tiene un rol administrativo válido	*/
	public static final String VALIDA_ROL_ADMIN =
		"No pudo validarse si el usuario tiene un rol administrativo válido.";

	/**  Excepción generada cuando No se pudo crear el Usuario	*/
	public static final String USUARIOS_CREACION_FALLIDA = "No se pudo crear el Usuario";

	/**  Excepción generada cuando No se pudo cambiar el password del Usuario	*/
	public static final String CAMBIO_PASSWORD_FALLIDO = "No se pudo cambiar el password del Usuario";

	/**  Excepción generada cuando No se pudo cambiar el estado del Usuario	*/
	public static final String HABILITACION_USUARIO_FALLIDA = "No se pudo cambiar el estado del Usuario";
	
	/**  Excepción generada cuando No se pudo Actualizar los datos del usuario	*/
	public static final String ACTUALIZACION_USUARIO_FALLIDA = "No se pudo actualizar el Usuario";	

	/**  Excepción generada cuando No se pudo eliminar al Usuario del LDAP	*/
	public static final String ELIMINACION_USUARIO_LDAP_FALLIDA =
		"No se pudo eliminar al Usuario del LDAP";

	/**  Excepción generada cuando No se pudo crear la lista de usuarios SIR en el LDAP	*/
	public static final String CREACION_AUTOMATICA_USUARIOS_LDAP_FALLIDA =
		"No se pudo crear los usuarios faltantes de SIR en el LDAP";

	/**  */
	public static final String RECIBOS_NO_ENCONTRADOS = "No se encontraron los recibos de la estación";
        
        public static final String NUEVO_ARCHIVO_FALLIDO = "No se pudo guardar la información del archivo";
        
        public static final String NUEVA_JUSTIFICACION_FALLIDA = "No se pudo guardar la información de la justificación";

	/**
	 *
	 */
	public FenrirException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public FenrirException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public FenrirException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public FenrirException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
