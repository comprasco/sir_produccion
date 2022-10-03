package gov.sir.core.negocio.modelo.constantes;

/**
 * @author dlopez
 * Clase para el manejo de las constantes utilizadas para el 
 * manejo de impresión. 
 */
public final class CImpresion {

	public static final String ID_CIRCULO = "ID_CIRCULO";
	public static final String OFICIO_PERTENENCIA = "OFICIO_PERTENENCIA";

	public static final String MAXIMO_IMPRESIONES_CAJERO = "MAX_IMPR_CAJ";
	public static final String MAXIMO_IMPRESIONES_SUPERVISOR = "MAX_IMPR_SUP";
	public static final String MAXIMO_IMPRESIONES_ENTREGA = "MAX_IMPR_ENT";
	public static final String IMPRESORA = "IMPRESORA";
	
	/**
	* Constante para indicar el identificador único de sesión, usado en el momento de 
	* determinar cuál cliente esta solicitando una impresión
	*/
	public static final String UID = "UID";
	
	/**
	* Constante para identificar el imprimible que desea ser impreso. 
	*/
	public static final String ID_IMPRIMIBLE = "ID_IMPRIMIBLE";
	
	/**
	* Constante para identificar el imprimible que desea ser impreso. 
	*/
	public static String CLIENTE_ADMINISTRADOR = "CLIENTE_ADMINISTRADOR";
	
	/**
	* Constante para identificar el imprimible que desea ser impreso. 
	*/
	public static String CLIENTE_LOCAL = "CLIENTE_LOCAL";
	
	/**
	* Constante para identificar si el cliente es el administrador de impresoras o si es un cliente normal. 
	*/
	public static final String TIPO_CLIENTE = "TIPO_CLIENTE";
	
	/**
	* Constante para identificar el tipo de fallo al intentar descargar la impresión. 
	*/
	public static final String MENSAJE_ERROR = "MENSAJE_ERROR";		
	
	/**
	* Constante para guardar la dirección ip de un cliente, usado en el momento de 
	* determinar cuál cliente esta solicitando una impresión.
	*/	
	public static final String LOCALHOST = "LOCALHOST";

	/**
	* Constante para guardar el puerto que esta escuchando en el cliente para enviar la impresión 
	* por ese puerto.
	*/		
	public static final String LISTENINGPORT = "LISTENINGPORT";
	
	/**
	* Constante que indica el número de intentos que deben hacerse al
	* imprimir un certificado.
	*/
	public static final String NUMERO_INTENTOS_IMPRESION_CERTIFICADOS = "NUMERO_INTENTOS_IMPRESION_CERTIFICADOS";

	/**
	* Constante que indica el número de intentos que deben hacerse al
	* imprimir un calificación. 
	*/
	public static final String NUMERO_INTENTOS_IMPRESION_CALIFICACION = "NUMERO_INTENTOS_IMPRESION_CALIFICACION";

	/**
	* Constante que indica el número de intentos que deben hacerse al
	* imprimir folios
	*/
	public static final String NUMERO_INTENTOS_IMPRESION_FOLIOS = "NUMERO_INTENTOS_IMPRESION_FOLIOS";

	/**
	* Constante que indica el número de intentos que deben hacerse al
	* imprimir notas informativas o devolutivas. 
	*/
	public static final String NUMERO_INTENTOS_IMPRESION_NOTAS = "NUMERO_INTENTOS_IMPRESION_NOTAS";

	/**
	* Constante que indica el número de intentos que deben hacerse al
	* imprimir recibos.
	*/
	public static final String NUMERO_INTENTOS_IMPRESION_RECIBOS = "NUMERO_INTENTOS_IMPRESION_RECIBOS";

	/**
	* Constante que indica el tiempo de espera que debe 
	* entre intentos de impresión en certificados individuales
	* */
	public static final String TIEMPO_ESPERA_IMPRESION_CERTIFICADOS = "TIEMPO_ESPERA_IMPRESION_CERTIFICADOS";

	/**
	* Constante que indica el tiempo de espera que debe existir
	* entre intentos de impresión en calificación. 
	* */
	public static final String TIEMPO_ESPERA_IMPRESION_CALIFICACION = "TIEMPO_ESPERA_IMPRESION_CALIFICACION";

	/**
	* Constante que indica el tiempo de espera que debe existir
	* entre intentos de impresión de folios.
	* */
	public static final String TIEMPO_ESPERA_IMPRESION_FOLIOS = "TIEMPO_ESPERA_IMPRESION_FOLIOS";

	/**
	* Constante que indica el tiempo de espera que debe existir
	* entre intentos de impresión de notas devolutivas o informativas.
	* */
	public static final String TIEMPO_ESPERA_IMPRESION_NOTAS = "TIEMPO_ESPERA_IMPRESION_NOTAS";

	/**
	* Constante que indica el tiempo de espera que debe existir
	* entre intentos de impresión de notas devolutivas o informativas.
	* */
	public static final String TIEMPO_ESPERA_IMPRESION_RECIBOS = "TIEMPO_ESPERA_IMPRESION_RECIBOS";

	/**
	* Constante para indicar que se van a imprimir certificados individuales
	*/
	public static final String IMPRIMIR_CERTIFICADOS_INDIVIDUALES = "IMPRIMIR_CERTIFICADOS_INDIVIDUALES";

	/**
	* Constante para indicar que se va a imprimir certificado de calificación. 
	*/
	public static final String IMPRIMIR_CERTIFICADOS_CALIFICACION = "IMPRIMIR_CERTIFICADOS_CALIFICACION";

	/**
	* Constante para indicar que se va a imprimir un folio. 
	*/
	public static final String IMPRIMIR_FOLIO = "IMPRIMIR_FOLIO";

	/**
	* Constante para indicar que se va a imprimir una nota informativa o devolutiva 
	*/
	public static final String IMPRIMIR_NOTA = "IMPRIMIR_NOTA";

	/**
	* Constante para indicar que se va a imprimir un recibo
	*/
	public static final String IMPRIMIR_RECIBO = "IMPRIMIR_RECIBO";

	/**
	* Constante para indicar que el tipo de varible es operativa (VARIABLES_HERMOD)
	*/
	public static final String VARIABLES_IMPRESION_HERMOD = "VARIABLES_IMPRESION_HERMOD";

	/**
	* Constante usada por defecto, cuando falla la carga desde la base de datos
	* para configurar el número máximo de intentos de impresión
	*/
	public static final String DEFAULT_INTENTOS_IMPRESION = "3";

	/**
	* Constante usada por defecto, cuando falla la carga desde la base de datos
	* para configurar el tiempo  máximo de espera para la impresión.
	*/
	public static final String DEFAULT_ESPERA_IMPRESION = "5000";

	/**
	* Constante para identificar ip del balanceador.
	*/
	public static final String IP_BALANCEADOR = "IP_BALANCEADOR";

	/**
	* Constante para determinar si se requiere usar el ip del balanceador o si no.
	*/
	public static final String USAR_BALANCEADOR = "USAR_BALANCEADOR";
	
	/**
	* Constante para determinar que se requiere usar el ip del balanceador.
	*/
	public static final String SI_USAR_BALANCEADOR = "1";

	/**
	* Constante para determinar que no se requiere usar el ip del balanceador.
	*/
	public static final String NO_USAR_BALANCEADOR = "0";
	
	/**
	* Constante para indicar el tiempo de vida para los Sockets que envian las impresiones.
	*/
	public static final String TIME_OUT_SOCKET = "TIME_OUT_SOCKET";	
	
	/**
	 * Constante para identificar el tiempo de retardo de la consulta de reimpresión de fallidos por parte del adiministrador de impresión del círculo
	 */
	public static final String RETARDO_CONSULTA_REIMPRESION ="RETARDO_CONSULTA_REIMPRESION";
	

	/**
	 * Constante para identificar la variable de session que indica si se debe
	 * mostrar la alerta de administrador de impresión del círculo inactivo
	 */
	public static final String ADMINISTRADOR_IMPRESION_ACTIVO = "ADMINISTRADOR_IMPRESION_ACTIVO";
 
}
