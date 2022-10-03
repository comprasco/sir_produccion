/*
 * Created on 10/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.constantes;

/**
 * @author jmendez
 *
 */
public class COPLookupCodes {

	public static final String NOMBRE_OPLOOKUP_CODE = "NOMBRE_OPLOOKUP_CODE";
	public static final String NOMBRE_OPLOOKUP_CODE_EDITADO = "NOMBRE_OPLOOKUP_CODE_EDITADO";
	public static final String DESCRIPCION_OPLOOKUP_CODE = "DESCRIPCION_OPLOOKUP_CODE";
	public static final String VALOR_OPLOOKUP_CODE = "VALOR_OPLOOKUP_CODE";
	public static final String SECUENCIAL_DOCUMENTO_IDENTIDAD = "SE";
	public static final String NIT = "NIT";
        public static final String CC = "CC";
        public static final String REL_ENDPOINT = "REL_ENDPOINT";
        public static final String OFICINA = "OFICINA";
        public static final String TURNO = "TURNO";
        public static final String TRNO_ID_FASE = "TRNO_ID_FASE";

	/** Constante para acceder a type de los actos que vencen*/
	public static final String ACTOS_QUE_VENCEN = "ACTOS_QUE_VENCEN";

	/** Constante para acceder a type de los actos que vencen*/
	public static final String PLAZO_VENCIMIENTO_REGISTRO_ACTOS = "PLAZO_VENCIMIENTO_REGISTRO_ACTOS";
	
	/** Constante para acceder a type de los actos que vencen*/
	public static final String PLAZO_VENCIMIENTO_REGISTRO_ACTOS_DIAS = "PLAZO_VENCIMIENTO_REGISTRO_ACTOS_DIAS";

	/**
	* Constante para acceder al type de la variable probabilidad revision calificacion
	*/
	public final static String REVISION_CALIFICACION_CODE = "PROBABILIDAD_REVISION_CALIFICACION";
	
	/**
	* Constante para acceder al type de la variable de numero de impresiones de certificados en correcciones
	*/
	public final static String NUMERO_IMPRESIONES_CERTIFICADOS_CORRECCIONES_CODE = "NUMERO_IMPRESIONES_CERTIFICADOS";
	
	/**
	* Constante para acceder al type de la variable de numero de turnos maximo por calificador
	*/
	public final static String MAXIMO_TURNOS_POR_CALIFICADOR_CODE = "MAXIMO_TURNOS_POR_CALIFICADOR";
	
	
	/**
	* Constante para acceder al code de la variable del número máximo de alertas
	*/
	public final static String NUMERO_MAXIMO_ALERTAS = "NUMERO_MAXIMO_ALERTAS";
	
	
	/**
	* Constante para acceder al code de la variable del número máximo de folios que se asocian en un rango
	*/
	public final static String NUMERO_MAXIMO_RANGO = "NUMERO_MAXIMO_RANGO";
	
	
	/**
	* Constante para acceder al code de la variable del número máximo de folios que se asocian en un rango
	*/
	public final static String IMP_RECIBOS_CERT_ASOC_LOCAL = "IMP_RECIBOS_CERT_ASOC_LOCAL";
	
	/**
	* Constante para identificar el número máximo de ediciones permitidas sobre minutas
	*/
	public final static String NUM_MAXIMO_EDICIONES_MINUTAS = "NUM_MAXIMO_EDICIONES_MINUTAS";
	
	/**
	* Constante para identificar el número de dias de vencimiento de una fotocopia
	*/
	public final static String DIAS_VENCIMIENTO = "DIAS_VENCIMIENTO";
	
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
	
	public static final String TEXTO_BASE1 = "TEXTO_BASE1";
	
	public static final String TEXTO_BASE2 = "TEXTO_BASE2";
	
	public static final String TIEMPO_ESPERA_REIMPRESION_FALLIDOS_PRIMERA_VEZ = "TIEMPO_ESPERA_REIMPRESION_FALLIDOS_PRIMERA_VEZ";
	
	public static final String TIEMPO_ESPERA_REIMPRESION_FALLIDOS= "TIEMPO_ESPERA_REIMPRESION_FALLIDOS";

        /**
        * @author Fernando Padilla Velez
        * @change 5453: Acta - Requerimiento No 207 - Exclusion de circulos registrales
        *         Se crea nueva constante.
        */
        public static final String CIRCULOS_NO_EXPIDEN_CERTIFICADOS = "CIRCULOS_NO_EXPIDEN_CERTIFICADOS";
        
        public static final String IMPRESION_RECIBO = "IMPRESION_RECIBO";
        
        public static final String PLAZO_VENCIMIENTO_JUZGADO = "PLAZO_VENCIMIENTO_JUZGADO";
        
        public static final String PLAZO_VENCIMIENTO_RECURSOS = "PLAZO_VENCIMIENTO_RECURSOS";
}
