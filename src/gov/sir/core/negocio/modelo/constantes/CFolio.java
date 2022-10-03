package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase que define las constantes relacionadas con el folio.
 * @author ppabon
 * @author jmendez
 * @author dsalas
 */
public final class CFolio {

	/**  Constante que indica el identificador del folio o número de la matricula*/
	public static String FOLIO_ID_MATRICULA = "FOLIO_ID_MATRICULA";
	/**  Constante que indica el identificador del folio o número de la matricula*/
	public static final String ID_MATRICULA = "ID_MATRICULA";
	/**  Constante que indica el identificador del folio o número de la matricula como referencia inicial*/
	public static final String ID_MATRICULA_INICIAL = "ID_MATRICULA_INICIAL";
	/**  Constante que indica el identificador del folio que no ha sido migrado*/
	public static final String ID_MATRICULA_SIR_MIG = "ID_MATRICULA_SIR_MIG";	
	/**  Constante que indica el identificador de la Zona Registral.**/
	public static final String ID_ZONA_REGISTRAL = "ID_ZONA_REGISTRAL";	
	/**  Constante que indica el identificador del folio para un rango de matriculas. Indica el desde.**/
	public static final String ID_MATRICULA_RL = "ID_MATRICULA_RL";
	/**  Constante que indica el identificador del folio para un rango de matriculas. Indica el hasta.**/
	public static final String ID_MATRICULA_RR = "ID_MATRICULA_RR";
	/** Constante que sirve para almacenar temporalmente el número de matriculas que se han cargado en el formulario. 
	 *  Puede servir para certificados, correcciones etc, o cualquier formulario que puede recibir más de un número de matricula.
	 */
	public static final String NUM_MATRICULAS = "NUM_MATRICULAS";
	/**  Constante que indica el número de la matricula inmobiliaria.**/
	public static final String NUMERO_MATRICULA_INMOBILIARIA = "NUMERO_MATRICULA_INMOBILIARIA";
	/**  Constante que indica el identificador del código catastral del folio.**/
	public static final String CODIGO_CATASTRAL_FOLIO = "CODIGO_CATASTRAL_FOLIO";
	/**  Constante que indica el identificador de la fecha de apertura.**/
	public final static String FECHA_APERTURA = "FECHA_APERTURA";
	/**  Constante que indica el identificador de si el folio es definitivo.**/
	public final static String DEFINITIVO = "DEFINITIVO";
	/**  Constante que indica el identificador del lindero del folio.**/
	public final static String LINDERO = "LINDERO";
        /**  Constante que indica el identificador del nupre en el folio.**/
	public final static String NUPRE = "NUPRE";
        /**  Constante que indica el identificador de las hectareas correspondientes al area del terreno en el folio.**/
	public final static String HECTAREAS = "HECTAREAS";
        /**  Constante que indica el identificador de los metros correspondientes al area del terreno en el folio.**/
	public final static String METROS = "METROS";
        /**  Constante que indica el identificador de los centimetros correspondientes al area del terreno en el folio.**/
	public final static String CENTIMETROS = "CENTIMETROS";
        /**  Constante que indica el identificador de la determinacion del inmueble en el folio.**/
	public final static String DETERMINACION_INMUEBLE = "DETERMINACION_INMUEBLE";
        /**  Constante que indica el identificador de la modalidad en la anotacion.**/
	public final static String MODALIDAD = "MODALIDAD";
        /**  Constante que indica el identificador de los metros correspondientes al area privada en el folio.**/
	public final static String PRIVMETROS = "PRIVMETROS";
        /**  Constante que indica el identificador de los centimetros correspondientes al area privada en el folio.**/
	public final static String PRIVCENTIMETROS = "PRIVCENTIMETROS";
        /**  Constante que indica el identificador de los metros correspondientes al area construida en el folio.**/
	public final static String CONSMETROS = "CONSMETROS";
        /**  Constante que indica el identificador de los centimetros correspondientes al area construida en el folio.**/
	public final static String CONSCENTIMETROS = "CONSCENTIMETROS";
        /**  Constante que indica el identificador del coeficiente en el folio.**/
	public final static String COEFICIENTE = "COEFICIENTE";
        /**  Constante que indica el identificador de los linderos tecnicamente definidos en el folio.**/
	public final static String LINDEROS_DEFINIDOS = "LINDEROS_DEFINIDOS";
        /**  Constante que indica el identificador del avaluo en el folio.**/
	public final static String AVALUO = "AVALUO";
        /**  Constante que indica el identificador de la fecha de avaluo en el folio.**/
	public final static String FECHA_AVALUO = "FECHA_AVALUO";
        /**  Constante que indica el identificador de la destinación económica en el folio.**/
	public final static String DESTINACION_ECONOMICA = "DESTINACION_ECONOMICA";
	/**  Constante que indica el identificador de lo que se quiere adicionar al lindero de un folio.**/
	public final static String LINDERO_ADICION = "LINDERO_ADICION";	
        /**  Constante que indica el identificador para definir los linderos tecnicamente definidos de un folio.**/
	public final static String DEFINIR_LINDERO = "DEFINIR_LINDERO";	
	/**  Constante que indica el identificador de la complementación del folio.**/
	public final static String COMPLEMENTACION = "COMPLEMENTACION";
	/**  Constante que indica el identificador del tipo de predio del folio.**/
	public final static String TIPO_PREDIO = "TIPO_PREDIO";
	/**  Constante que indica el identificador de la dirección asociada al folio.**/
	public final static String DIRECCION = "DIRECCION";
	/**  Constante que indica el identificador de una salvedad del folio.**/
	public final static String SALVEDAD = "SALVEDAD";
	/**  Constante que indica el identificador de una anotación del folio.**/
	public final static String ANOTACION = "ANOTACION";
	/**  Constante que indica el identificador de un rango de matriculas para la copia de 
	 *   direcciones complementos y direcciones. Indica desde qué matricula se hace la copia.**/
	public final static String DESDE_MATRICULA = "DESDE_MATRICULA";
	/**  Constante que indica el identificador de un rango de matriculas para la copia de 
	 *   direcciones complementos y direcciones. Indica hasta qué matricula se hace la copia.**/
	public final static String HASTA_MATRICULA = "HASTA_MATRICULA";
	/**  Constante que indica el identificador de la acción de asociar los datos desde otro folio.**/
	public final static String ASOCIAR = "ASOCIAR";
	/**  Constante que indica el identificador de la acción de copiar los datos desde otro folio.**/
	public final static String COPIAR = "COPIAR";
	/**  Constante que indica el identificador de la acción crear una complementación nueva.**/
	public final static String NUEVA = "NUEVA";
	/**  Constante que indica el identificador de la acción crear una complementación a partir de las anotaciones.**/
	public final static String DESDE_ANOTACION = "DESDE_ANOTACION";
	
	/**  Constante que indica el identificador de la acción de seleccionar un folio determinado.**/
	public final static String SELECCIONAR_FOLIO = "SELECCIONAR_FOLIO";
	/**  Constante que indica el identificador de la acción de seleccionar un folio determinado.**/
	public final static String NUMERO_ANOTACIONES = "NUMERO_ANOTACIONES_FOLIO";
	/**  Constante que indica si el folio es de mayor extensión.**/
	public final static String MAYOR_EXTENSION = "MAYOR_EXTENSION_FOLIO";
	
	/**  Constante que indica si el folio no es de mayor extensión.**/
	public final static String NO_MAYOR_EXTENSION = "NO_MAYOR_EXTENSION_FOLIO";
	
	/**  Constante que indica la descripcion de la anulacion de un folio **/
	public final static String DESCRIPCION_ANULACION = "DESCRIPCION_ANULACION";

	/** Esta constante identifica el campo donde se introduce el numero de la radicacion en la anotacion*/
	public static final String ANOTACION_NUM_RADICACION = "ANOTACION_NUM_RADICACION";
	/** Esta constante identifica el campo donde se introduce la fecha de la radicacion en la anotacion*/
	public static final String ANOTACION_FECHA_RADICACION = "ANOTACION_FECHA_RADICACION";
	/** Esta constante identifica el campo donde se introduce el id del documento en la anotacion*/
	public static final String ANOTACION_ID_DOCUMENTO = "ANOTACION_ID_DOCUMENTO";
	/** Esta constante identifica el campo donde se introduce el tipo de docuemtno en la anotacion*/
	public static final String ANOTACION_TIPO_DOCUMENTO = "ANOTACION_TIPO_DOCUMENTO";
	/** Esta constante identifica el campo donde se introduce el numero del documento en la anotacion*/
	public static final String ANOTACION_NUM_DOCUMENTO = "ANOTACION_NUM_DOCUMENTO";
	/** Esta constante identifica el campo donde se introduce la fecha del documento en la anotacion*/
	public static final String ANOTACION_FECHA_DOCUMENTO = "ANOTACION_FECHA_DOCUMENTO";
	/** Esta constante identifica la oficina del documento de la anotacion */
	public static final String ANOTACION_OFICINA_DOCUMENTO = "ANOTACION_OFICINA_DOCUMENTO";
	/** Esta constante identifica el campo donde se introduce el tipo de oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_TIPO = "ANOTACION_OFICINA_DOCUMENTO_TIPO";
	/** Esta constante identifica el campo donde se introduce el tipo de oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_TIPO = "ANOTACION_OFICINA_DOCUMENTO_ID_TIPO";
	/** Esta constante identifica el campo donde se introduce el numero de oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NUM = "ANOTACION_OFICINA_DOCUMENTO_NUM";
	/** Esta constante identifica el campo del nombre de oficina*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM = "ANOTACION_OFICINA_DOCUMENTO_NOM"; 
	/** Esta constante identifica el campo del codigo de la oficina */
	public static final String ANOTACION_OFICINA_DOCUMENTO_COD = "ANOTACION_OFICINA_DOCUMENTO_COD";
	/** Esta constante identifica el campo donde se introduce el identificador de la oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA = "ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA";
        /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
        /** Esta constante identifica el campo donde se introduce el identificador de la oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION = "ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION";
	/** Esta constante identifica el campo donde se guardara el identificador del departamento*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO = "ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO";
	/** Esta constante identifica el campo donde se guardara el nombre del departamento*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO = "ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO";
	/** Esta constante identifica el campo donde se guardara el identificador del municipio*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC = "ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC";
	/** Esta constante identifica el campo donde se guardara el nombre del municipio*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC = "ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC";
	/** Esta constante identifica el campo donde se guardara el identificador de la vereda*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA = "ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA";
	/** Esta constante identifica el campo donde se guardara el nombre de la vereda*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA = "ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA";
        /**  Constante que indica el identificador de la modalidad en la anotacion.**/
	public final static String ANOTACION_MODALIDAD = "MODALIDAD";
	/**Esta constante identifica el campo donde se introduce el valor de la especificacion de la anotacion*/
	public static final String ANOTACION_VALOR_ESPECIFICACION = "ANOTACION_VALOR_ESPECIFICACION";
	/**Esta constante identifica el campo donde se introduce el id de la naturaleza juridica de la especificacion de la anotacion*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID";
	/**Esta constante identifica el campo donde se introduce el nombre de la naturaleza juridica de la especificacion de la anotacion*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM";
         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Se agrega constate
	 */
        /**Esta constante identifica el campo donde se introduce la version de la naturaleza juridica de la especificacion de la anotacion*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER";
	/**Esta constante identifica la pareja de datos id y nombre para la naturaleza juridica*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION = "ANOTACION_NAT_JURIDICA_ESPECIFICACION";
	/**Esta constante identifica el campo donde se introduce el comentario de la especificacion en la anotacion*/
	public static final String ANOTACION_COMENTARIO_ESPECIFICACION = "ANOTACION_COMENTARIO_ESPECIFICACION";
	/**Esta constante identifica el campo donde se introduce el tipo de identificacion del ciudadano en la anotacion*/
	public static final String ANOTACION_TIPO_ID_PERSONA = "ANOTACION_TIPO_ID_PERSONA";
        /**Esta constante identifica el campo donde se introduce el tipo de persona del ciudadano en la anotacion*/
	public static final String ANOTACION_TIPO_PERSONA = "ANOTACION_TIPO_PERSONA";
	/**Esta constante identifica el campo donde se introduce el numero de identificacion en la anotacion*/
	public static final String ANOTACION_NUM_ID_PERSONA = "ANOTACION_NUM_ID_PERSONA";
        /**Esta constante identifica el campo donde se genera el código de verificacion en caso de que la identificacion sea NIT del ciudadano en la anotacion*/
	public static final String ANOTACION_COD_VERIFICACION = "ANOTACION_COD_VERIFICACION";
	/**Esta constante identifica el campo donde se introduce el apellido de la persona en la anotacion*/
	public static final String ANOTACION_APELLIDO_1_PERSONA = "ANOTACION_APELLIDO_1_PERSONA";
	/**Esta constante identifica el campo donde se introduce el apellido de la persona en la anotacion*/
	public static final String ANOTACION_APELLIDO_2_PERSONA = "ANOTACION_APELLIDO_2_PERSONA";
	/**Esta constante identifica el campo donde se introduce los nombres de la persona en la anotacion*/
	public static final String ANOTACION_NOMBRES_PERSONA = "ANOTACION_NOMBRES_PERSONA";
        /**Esta constante identifica el campo donde se introduce el sexo del ciudadano en la anotacion*/
	public static final String ANOTACION_SEXO_PERSONA = "ANOTACION_SEXO_PERSONA";
	/**Esta constante identifica el campo donde se introduce el tipo de intervencion en la asocioacion en la anotacion*/
	public static final String ANOTACION_TIPO_INTER_ASOCIACION = "ANOTACION_TIPO_INTER_ASOCIACION";
	/**Esta constante identifica el campo donde se introduce si el ciudadano es el propiuetario en la asocioacion en la anotacion*/
	public static final String ANOTACION_ES_PROPIETARIO_ASOCIACION = "ANOTACION_ES_PROPIETARIO_ASOCIACION";
	/**Esta constante identifica el campo donde se introduce si el ciudadano es el propiuetario en la asocioacion en la anotacion*/
	public static final String ANOTACION_PORCENTAJE_ASOCIACION = "ANOTACION_PORCENTAJE_ASOCIACION";
	/** Esta constante identifica el identificador de la anotacion*/
	public static final String ANOTACION_ID_ANOTACION = "ANOTACION_ID_ANOTACION";
	/** Esta constante identifica el orden de la anotacion*/
	public static final String ANOTACION_ORDEN = "ANOTACION_ORDEN";
	/** Esta constante identifica el campo donde se guardara el identificador del departamento*/
	public static final String FOLIO_LOCACION_ID_DEPTO = "FOLIO_LOCACION_ID_DEPTO";
	/** Esta constante identifica el campo donde se guardara el nombre del departamento*/
	public static final String FOLIO_LOCACION_NOM_DEPTO = "FOLIO_LOCACION_NOM_DEPTO";
	/** Esta constante identifica el campo donde se guardara el identificador del municipio*/
	public static final String FOLIO_LOCACION_ID_MUNIC = "FOLIO_LOCACION_ID_MUNIC";
	/** Esta constante identifica el campo donde se guardara el nombre del municipio*/
	public static final String FOLIO_LOCACION_NOM_MUNIC = "FOLIO_LOCACION_NOM_MUNIC";
	/** Esta constante identifica el campo donde se guardara el identificador de la vereda*/
	public static final String FOLIO_LOCACION_ID_VEREDA = "FOLIO_LOCACION_ID_VEREDA";
	/** Esta constante identifica el campo donde se guardara el nombre de la vereda*/
	public static final String FOLIO_LOCACION_NOM_VEREDA = "FOLIO_LOCACION_NOM_VEREDA";
	/**Esta constante identifica el campo donde se introduce el estado del folio */
	public static final String FOLIO_ESTADO_FOLIO = "FOLIO_ESTADO_FOLIO";
	/**Esta constante identifica el campo donde se introduce el tipo del predio del folio */
	public static final String FOLIO_TIPO_PREDIO = "FOLIO_TIPO_PREDIO";
	/**Esta constante identifica el campo donde se introduce el numero de la complementacion del  folio */
	public static final String FOLIO_COMPLEMENTACION = "FOLIO_COMPLEMENTACION";
	/**Esta constante identifica el campo donde se llena el id de la complementacion del folio */
	public static final String FOLIO_ID_COMPLEMENTACION = "FOLIO_ID_COMPLEMENTACION";	
	/**Esta constante identifica el campo donde se muestra el código del documento*/
	public static final String FOLIO_COD_DOCUMENTO = "FOLIO_COD_DOCUMENTO";
	/**Esta constante identifica el campo donde se muestra la fecha de apertura*/
	public static final String FOLIO_FECHA_APERTURA = "FOLIO_FECHA_APERTURA";
	/**Esta constante identifica el campo donde se introduce el numero del codigo catastral del folio */
	public static final String FOLIO_COD_CATASTRAL = "FOLIO_COD_CATASTRAL";
	/**Esta constante identifica el campo donde se introduce el numero del codigo catastral anterior del folio */
	public static final String FOLIO_COD_CATASTRAL_ANT = "FOLIO_COD_CATASTRAL_ANT";	
	/**Esta constante identifica el campo donde se introduce el eje del folio */
	public static final String FOLIO_EJE1 = "FOLIO_EJE1";
	/**Esta constante identifica el campo donde se introduce el eje del folio */
	public static final String FOLIO_EJE2 = "FOLIO_EJE2";
	/**Esta constante identifica el campo donde se introduce el valor de la direccion del folio */
	public static final String FOLIO_VALOR1 = "FOLIO_VALOR1";
	/**Esta constante identifica el campo donde se introduce el valor de la direccion del folio */
	public static final String FOLIO_VALOR2 = "FOLIO_VALOR2";
	/**Esta constante identifica el campo donde se introduce el complemento de la direccion del folio */
	public static final String FOLIO_COMPLEMENTO_DIR = "FOLIO_COMPLEMENTO_DIR";
	/**Esta constante identifica el campo donde se introduce el numero de la radicacion del folio */
	public static final String FOLIO_NUM_RADICACION = "FOLIO_NUM_RADICACION";
	/**Esta constante identifica el campo donde se introduce el lindero del folio */
	public static final String FOLIO_LINDERO = "FOLIO_LINDERO";
         /**  Constante que indica el identificador del nupre en el folio.**/
	public final static String FOLIO_NUPRE = "NUPRE";
        /**  Constante que indica el identificador de las hectareas correspondientes al area del terreno en el folio.**/
	public final static String FOLIO_HECTAREAS = "HECTAREAS";
        /**  Constante que indica el identificador de los metros correspondientes al area del terreno en el folio.**/
	public final static String FOLIO_METROS = "METROS";
        /**  Constante que indica el identificador de los centimetros correspondientes al area del terreno en el folio.**/
	public final static String FOLIO_CENTIMETROS = "CENTIMETROS";
        /**  Constante que indica el identificador de la determinacion del inmueble en el folio.**/
	public final static String FOLIO_DETERMINACION_INMUEBLE = "DETERMINACION_INMUEBLE";
    
        /**  Constante que indica el identificador de los metros correspondientes al area privada en el folio.**/
	public final static String FOLIO_PRIVMETROS = "PRIVMETROS";
        /**  Constante que indica el identificador de los centimetros correspondientes al area privada en el folio.**/
	public final static String FOLIO_PRIVCENTIMETROS = "PRIVCENTIMETROS";
        /**  Constante que indica el identificador de los metros correspondientes al area construida en el folio.**/
	public final static String FOLIO_CONSMETROS = "CONSMETROS";
        /**  Constante que indica el identificador de los centimetros correspondientes al area construida en el folio.**/
	public final static String FOLIO_CONSCENTIMETROS = "CONSCENTIMETROS";
        /**  Constante que indica el identificador del coeficiente en el folio.**/
	public final static String FOLIO_COEFICIENTE = "COEFICIENTE";
        /**  Constante que indica el identificador de los linderos tecnicamente definidos en el folio.**/
	public final static String FOLIO_LINDEROS_DEFINIDOS = "LINDEROS_DEFINIDOS";
        /**  Constante que indica el identificador del avaluo en el folio.**/
	public final static String FOLIO_AVALUO = "AVALUO";
        /**  Constante que indica el identificador de la fecha de avaluo en el folio.**/
	public final static String FOLIO_FECHA_AVALUO = "FECHA_AVALUO";
        /**  Constante que indica el identificador de la destinación económica en el folio.**/
	public final static String FOLIO_DESTINACION_ECONOMICA = "DESTINACION_ECONOMICA";
	/**Esta constante identifica el campo ORDEN de la siguiente anotacion a crear*/
	public static final String NEXT_ORDEN_ANOTACION = "NEXT_ORDEN_ANOTACION";
	/**Esta constante identifica el campo que identifica si un ciudadano es editable o no*/
	public static final String CIUDADANO_EDITABLE = "CIUDADANO_EDITABLE";
	/**Esta constante identifica el campo que identifica el comentario del folio*/
	public static final String FOLIO_COMENTARIO = "FOLIO_COMENTARIO";
    /**Esta constante identifica el campo que identifica el identificador del tipo de encabezado del folio*/
    public static final String FOLIO_ID_TIPO_ENCABEZADO="FOLIO_ID_TIPO_ENCABEZADO";
    /**Esta constante identifica el campo que identifica el tipo de documento del folio*/
    public static final String FOLIO_TIPO_DOCUMENTO="FOLIO_TIPO_DOCUMENTO";
    /**Esta constante identifica el campo que identifica el numero de documento del folio*/
    public static final String FOLIO_NUM_DOCUMENTO="FOLIO_NUM_DOCUMENTO";
    /**Esta constante identifica el campo que identifica la fecha del documento del folio*/
    public static final String FOLIO_FECHA_DOCUMENTO="FOLIO_FECHA_DOCUMENTO";
    /**Esta constante identifica el campo que identifica el identificador del departamento del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_ID_DEPTO="FOLIO_OFICINA_DOCUMENTO_ID_DEPTO";
    /**Esta constante identifica el campo que identifica el nombre del departamento del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO="FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO";
    /**Esta constante identifica el campo que identifica el identificador del municipio del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_ID_MUNIC="FOLIO_OFICINA_DOCUMENTO_ID_MUNIC";
    /**Esta constante identifica el campo que identifica el nombre del municipio del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC="FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC";
    /**Esta constante identifica el campo que identifica el identificador de la vereda del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_ID_VEREDA="FOLIO_OFICINA_DOCUMENTO_ID_VEREDA";
    /**Esta constante identifica el campo que identifica el nombre de la vereda del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA="FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA";
    /**Esta constante identifica el campo que identifica el identificador del tipo de oficina del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_ID_TIPO="FOLIO_OFICINA_DOCUMENTO_ID_TIPO";
    /**Esta constante identifica el campo que identifica el nombre del tipo del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_TIPO="FOLIO_OFICINA_DOCUMENTO_TIPO";
    /**Esta constante identifica el campo que identifica el identificador de la oficina del documento del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_ID_OFICINA="FOLIO_OFICINA_DOCUMENTO_ID_OFICINA";
    /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
    /**Esta constante identifica el campo que identifica el identificador de la oficina del documento del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION="FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION";
    /**Esta constante identifica el campo que identifica el numero de la oficina del documento del folio*/
    public static final String FOLIO_OFICINA_DOCUMENTO_NUM="FOLIO_OFICINA_DOCUMENTO_NUM";
    /**Esta constante identifica el campo que identifica el numero de la oficina del documento del folio*/
    public static final String FOLIO_ID_ESTADO = "FOLIO_ID_ESTADO";
	/**Esta constante identifica el campo donde se introduce el numero de la salvedad del  folio */
	public static final String FOLIO_SALVEDAD = "FOLIO_SALVEDAD";
	/**Esta constante identifica el campo donde se llena el id de la salvedad del folio */
	public static final String FOLIO_ID_SALVEDAD = "FOLIO_ID_SALVEDAD";	
	/**Esta constante identifica el campo ORDEN de la siguiente anotacion a crear*/
	public static final String NEXT_ORDEN_SALVEDAD = "NEXT_ORDEN_SALVEDAD";
	/**Esta constante identifica si el folio fue creado por hoja de ruta*/
	public static final String FOLIO_CREADO_HOJA_RUTA = "FOLIO_CREADO_HOJA_RUTA";
	
	/**Flag que indica que el folio fue creado por pantalla Adminsitrativa Creacion Directa*/
	public static final int FOLIO_CREADO_ADMINISTRATIVA_CREACION = 1;
	
	/**Flag que indica que el folio fue creado por pantalla Adminsitrativa Creacion Directa*/
	public static final int FOLIO_CREADO_ADMINISTRATIVA_GRABACION = 0;
	
	/**  Constante que indica el identificador de la complementación del folio.**/
	public final static String COMPLEMENTACION_AMPLIACION = "COMPLEMENTACION_AMPLIACION";
	
	public final static String COMPLEMENTACION_ADICION = "COMPLEMENTACION_ADICION";
	
	public final static String FOLIO_DEF = "FOLIO_DEF";
        
        /**
         * @author     : Ellery David Robles Gómez
         * Caso Mantis : 14985 : Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
         */
	public static final String NUMERO_FUNDAMENTO = "NUMERO_FUNDAMENTO";
        public static final String CALENDAR = "CALENDAR";
}
