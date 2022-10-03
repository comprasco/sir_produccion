package gov.sir.fenrir;

/**
* Clase para el manejo de los diferentes tipos de excepciones lanzadas
* durante la utilizaci�n de los servicios de hermod.
* @author  jfrias. jmendez
*/
public class FenrirException extends Exception {

	/********************************************************************/

	/*          Excepciones lanzadas en m�todos analizadores.           */

	/********************************************************************/

	/**
	* Excepci�n generada cuando un usuario inv�lido trata de registrarse en el sistema
	*/
	public static final String USUARIO_INVALIDO = "Usuario Inv�lido";

	/**
	* Excepci�n generada cuando un usuario que est� bloqueado trata de registrarse en el sistema
	*/
	public static final String USUARIO_BLOQUEADO = "Usuario Bloqueado";

	/**
	* Excepci�n generada cuando hay un error de conexi�n con el directorio LDAP
	*/
	public static final String ERROR_CONEXION_DIRECTORIO =
		"Imposible obtener conexi�n con el Directorio";

	/**
	* Excepci�n generada cuando No es posible agregar auditor�a
	*/
	public static final String ERROR_AUDITORIA = "No fue posible agregar auditor�a";

	/**
	* Excepci�n generada cuando No es posible  obtener el listado de estaciones
	*/
	public static final String LISTADO_ESTACIONES_NO_OBTENIDO =
		"No fue posible obtener el listado de estaciones";

	/**  Excepci�n generada cuando No es posible  efectuar el log out  	*/
	public static final String LOGOUT_NO_EFECTUADO = "No fue posible efectuar log out";

	/**  Excepci�n generada cuando El usuario no existe  	*/
	public static final String ID_USUARIO_INVALIDO = "El usuario no existe";

	/**  Excepci�n generada cuando El usuario No pudo crearse el nivel 	*/
	public static final String NIVEL_NO_CREADO = "No pudo crearse el nivel.";

	/**  Excepci�n generada cuando No pudo eliminarse el nivel 	*/
	public static final String NIVEL_NO_ELIMINADO = "No pudo eliminarse el nivel.";

	/**  Excepci�n generada cuando No se pudo consultar la lista de niveles 	*/
	public static final String NIVEL_NO_CONSULTADO = "No se pudo consultar la lista de niveles";

	/**  Excepci�n generada cuando No se pudo consultar la lista de estaciones	*/
	public static final String ESTACION_NO_CONSULTADA = "No se pudo consultar la lista de estaciones";

	/**  Excepci�n generada cuando No pudo crearse la Responsabilidad	*/
	public static final String RESPONSABILIDAD_NO_CREADA = "No pudo crearse la Responsabilidad.";

	/**  Excepci�n generada cuando No pudo crearse el rol	*/
	public static final String ROL_NO_CREADO = "No pudo crearse el rol.";

	/**  Excepci�n generada cuando No pudo consultarse las estaciones del circulo 	*/
	public static final String ESTACIONES_POR_CIRCULO_NO_CONSULTADAS =
		"No pudo consultar las estaciones del c�rculo.";

	/**  Excepci�n generada cuando No pudo consultar los usuarios por el c�rculo y rol	*/
	public static final String USUARIOS_POR_CIRCULO_ROL_NO_CONSULTADOS =
		"No pudo consultar los usuarios por el c�rculo y rol.";

	/**  Excepci�n generada cuando No pudo consultar los usuarios por el c�rculo proporcionado	*/
	public static final String USUARIOS_POR_CIRCULO_NO_CONSULTADOS =
		"No pudo consultar los usuarios por el c�rculo proporcionado.";
        
        /**  Excepci�n generada cuando No pudo consultar los campos de captura para una forma de pago	*/
	public static final String CAMPOS_CAPTURA_NO_CONSULTADOS =
		"No pudo consultar los campos de captura para la forma de pago proporcionado.";
        
        /**  Excepci�n generada cuando No pudo actualizar la relacion campo captura y forma pago	*/
	public static final String CAMPOS_CAPTURA_NO_ACTUALIZADOS =
		"No pudo actualizar la relacion campo captura y forma de pago.";
        
        /**  Excepci�n generada cuando No pudo consultar los campos de captura del sistema	*/
	public static final String CARGA_CAMPOS_CAPTURA_NO_CONSULTADOS =
		"No pudo consultar los campos de captura del sistema.";
        
        /**  Excepci�n generada cuando No pudo crear la forma pago	*/
	public static final String FORMA_PAGO_NO_CREADA =
		"No pudo crear la forma de pago.";

	/**  Excepci�n generada cuando No pudo consultar los usuarios por el c�rculo proporcionado	*/
	public static final String USUARIOS_NO_ENCONTRADO = "No se encontr� el usuario solicitado.";

	/**  Excepci�n generada cuando No pudo validarse si el usuario tiene un rol administrativo v�lido	*/
	public static final String VALIDA_ROL_ADMIN =
		"No pudo validarse si el usuario tiene un rol administrativo v�lido.";

	/**  Excepci�n generada cuando No se pudo crear el Usuario	*/
	public static final String USUARIOS_CREACION_FALLIDA = "No se pudo crear el Usuario";

	/**  Excepci�n generada cuando No se pudo cambiar el password del Usuario	*/
	public static final String CAMBIO_PASSWORD_FALLIDO = "No se pudo cambiar el password del Usuario";

	/**  Excepci�n generada cuando No se pudo cambiar el estado del Usuario	*/
	public static final String HABILITACION_USUARIO_FALLIDA = "No se pudo cambiar el estado del Usuario";
	
	/**  Excepci�n generada cuando No se pudo Actualizar los datos del usuario	*/
	public static final String ACTUALIZACION_USUARIO_FALLIDA = "No se pudo actualizar el Usuario";	

	/**  Excepci�n generada cuando No se pudo eliminar al Usuario del LDAP	*/
	public static final String ELIMINACION_USUARIO_LDAP_FALLIDA =
		"No se pudo eliminar al Usuario del LDAP";

	/**  Excepci�n generada cuando No se pudo crear la lista de usuarios SIR en el LDAP	*/
	public static final String CREACION_AUTOMATICA_USUARIOS_LDAP_FALLIDA =
		"No se pudo crear los usuarios faltantes de SIR en el LDAP";

	/**  */
	public static final String RECIBOS_NO_ENCONTRADOS = "No se encontraron los recibos de la estaci�n";
        
        public static final String NUEVO_ARCHIVO_FALLIDO = "No se pudo guardar la informaci�n del archivo";
        
        public static final String NUEVA_JUSTIFICACION_FALLIDA = "No se pudo guardar la informaci�n de la justificaci�n";

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
