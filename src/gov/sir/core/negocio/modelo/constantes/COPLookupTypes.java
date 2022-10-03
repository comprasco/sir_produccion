/*
 * Created on 28-sep-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.constantes;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class COPLookupTypes {
	
        public static final String NOMBRE_OPLOOKUP_TYPE = "NOMBRE_OPLOOKUP_TYPE";
        public static final String DETERMINACION_INMUEBLE = "DETERMINACION_INMUEBLE";
        public static final String DOCUMENTOS_IDENTIDAD_JURIDICA = "DOCUMENTOS_IDENTIDAD_JURIDICA";
        public static final String DOCUMENTOS_IDENTIDAD_NATURAL = "DOCUMENTOS_IDENTIDAD_NATURAL";
        public static final String MODALIDAD = "MODALIDAD";
        public static final String TIPOS_PERSONA = "TIPOS_PERSONA";
	public static final String TIPOS_SEXO = "TIPOS_SEXO";
	public static final String DESCRIPCION_OPLOOKUP_TYPE = "DESCRIPCION_OPLOOKUP_TYPE";
	public static final String NOMBRE_OPLOOKUP_TYPE_EDITADO = "NOMBRE_OPLOOKUP_TYPE_EDITADO";
        public static final String IMPRESION_RECIBO = "IMPRESION_RECIBO";
        public static final String PLAZO_VENCIMIENTO_JUZGADO = "PLAZO_VENCIMIENTO_JUZGADO";
        public static final String PLAZO_VENCIMIENTO_RECURSOS = "PLAZO_VENCIMIENTO_RECURSOS";

	
	public static final String CAUSAL_REIMPRESION = "CAUSAL_REIMPRESION";
	public static final String DOCUMENTO_IDENTIDAD = "DOCUMENTOS_IDENTIDAD";
	public static final String TARIFA_CERTIFICADOS ="TARIFA_CERTIFICADOS";
	public static final String PERVEN = "PERVEN";
	public static final String MEDIDAS_AREA = "MEDIDAS_AREA";
	public static final String VISIBILIDAD ="VISIBILIDAD";
	public static final String FOTOCOPIAS ="FOTOCOPIAS";
        public static final String REL_ENDPOINT = "REL_ENDPOINT";
        public static final String PARAMETROS_REL = "PARAMETROS_REL";
        
	
	public static final String VARIABLES_IMPRIMIBLES ="VARIABLES_IMPRIMIBLES";
	
	public static final String CARGOS_REGISTRADOR = "CARGOS_REGISTRADOR";
	
	/*Constante para consultar en los lookup types el rol del turno que puede consultar todos los turnos*/
	public static final String ROLES_CONSULTA_FOLIOS="ROLES_CONSULTA_FOLIOS";
	
	/*Constante para consultar en los lookup types el rol del turno que puede consultar todos los turnos*/
	public static final String ROLES_CONSULTA_TURNOS="ROLES_CONSULTA_TURNOS";
	
	/**
	* Constante para indicar que el tipo de varible es operativa (VARIABLES_HERMOD)
	*/
	public static final String VARIABLES_IMPRESION_HERMOD = "VARIABLES_IMPRESION_HERMOD";
	
	/**
	* Constante para acceder al type de la variable probabilidad revision calificacion
	*/
	public final static String REVISION_CALIFICACION_TYPE = "VARIABLES_HERMOD";  
	
	/**
	* Constante para acceder al type de la variable de numero de impresiones de certificados en correcciones
	*/
	public final static String NUMERO_IMPRESIONES_CERTIFICADOS_CORRECCIONES_TYPE = "CORRECCIONES";
	
	
	public final static String REGISTRO_TYPE = "REGISTRO";  
	
	/**
	* Constante para identificar constantes de configuración de impresión. 
    */
	public final static String CONFIGURACION_IMPRESION = "CONFIGURACION_IMPRESION";
	
	/**
	* Constante para identificar constantes de configuración de impresión del administrador. 
    */
	public final static String CONFIGURACION_IMPRESION_CLIENTE = "CONFIGURACION_IMPRESION_CLIENTE";
	
	
	/**
	* Constante para identificar constantes de configuración de impresión. 
	*/
	public final static String CONFIGURACION_ALERTAS = "CONFIGURACION_ALERTAS";
	
	/**Constante para identificar el plazo para realizar cancelacion de anotaciones de antiguo sistema */
	public static final String PLAZO_CANCELACION_ANOTACIONES_ANT_SIS = "PLAZO_CANCELACION_ANOTACIONES_ANT_SIS";
	
	/**
	* Constante para identificar los tipos de interes jurídico en la tabla de Lookup Types 
	*/
	public static final String INTERES_JURIDICO ="INTERES_JURIDICO";
	
	/**
	* Constante para identificar variables del proceso de reparto notarial
	*/
	public static final String REPARTO_NOTARIAL ="REPARTO_NOTARIAL";
	
	/**
	* Constante para identificar los roles que puede asignar un administrador regional
	*/
	public static final String ADMINISTRADOR_REGIONAL_ROLES ="ADMINISTRADOR_REGIONAL_ROLES";
	
	/**
	 * Constante que define el lookupType ROLES_ALERTA_ADM_IMP_CAIDO
	 */
	public static final String ROLES_ALERTA_ADM_IMP_CAIDO = "ROLES_ALERTA_ADM_IMP_CAIDO";
	
	/**
	 * Constante que define el lookuType para la configuración del circulo del portal
	 */
	public static final String CIRCULO_PORTAL = "CIRCULO_PORTAL";

        /**
        * @author Fernando Padilla Velez
        * @change 5453: Acta - Requerimiento No 207 - Exclusion de circulos registrales
        *         Se crea nueva constante.
        */
        public static final String CERTIFICADOS = "CERTIFICADOS";

}
