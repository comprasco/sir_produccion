package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase que define las constantes relacionadas con un turno.
 * @author ppabon
 */
public final class CTurno {

	/**  Constante que indica el año**/
	public static final String ANIO = "ANIO";

	/**  Constante que indica el identificador del turno **/
	public static final String ID_TURNO = "ID_TURNO";

	/**  Constante que indica la última respuesta **/
	public static final String ULTIMA_RESPUESTA = "ULTIMA_RESPUESTA";

	/**  Constante que indica la fecha de inicio del turno **/
	public static final String FECHA_INICIO = "FECHA_INICIO";

	/**  Constante que indica la fecha de fin**/
	public static final String FECHA_FIN = "FECHA_FIN";

	/**  Constante que indica la descripción**/
	public static final String DESCRIPCION = "DESCRIPCION";
	
	/**  Constante que indica la observación de anulación**/
	public static final String OBSERVACIONES_ANULACION= "OBSERVACIONES_ANULACION";
	
	/**  Constante que indica la observación de anulación**/
	public static final String OBSERVACIONES_HABILITAR = "OBSERVACIONES_HABILITAR";
	
	/**  Constante que indica la observación de anulación**/
	public static final String TURNO_ANULADO= "S";
	
	/**  Constante que indica la observación de anulación**/
	public static final String TURNO_NO_ANULADO= "N";
	
	/**  Constante que indica si la solicitud de corrección es de tipo petición lo que implica limite de tiempo.**/
	public static final String PETICION = "PETICION";

	/** Constante que identifica la dirección de respuesta de una corrección*/
	public final static String DIRECCION = "DIRECCION";

	/** Constante que identifica el identificador de un turno anterior*/
	public final static String TURNO_ANTERIOR = "TURNO_ANTERIOR";

    /** Constante que identifica el objeto turno anterior*/
    public final static String TURNO_ANTERIOR_OBJETO = "TURNO_ANTERIOR_OBJETO";

	//********************

	/** Constante que identifica el identificador del tipo de consulta para un turno*/
	public final static String CONSULTA_TURNO_TIPO = "CONSULTA_TURNO_TIPO";

	/** Constante que identifica el identificador del tipo de consulta para un turno por identificador */
	public final static String CONSULTA_TURNO_POR_IDENTIFICADOR = "CONSULTA_TURNO_POR_IDENTIFICADOR";

	/** Constante que identifica el identificador del tipo de consulta para un turno por MATRICULA */
	public final static String CONSULTA_TURNO_POR_MATRICULA = "CONSULTA_TURNO_POR_MATRICULA";

	/** Constante que identifica el identificador de la matrícula para consultar turnos */
	public final static String MATRICULA_TURNO = "MATRICULA_TURNO";

	//Consistencia de turnos con respecto a workflow

	/** Constante que indica que el turno es consistente con el workflow  */
	public final static int CON_WF_CONSISTENTE = 0;


	/** Constante que indica que el turno es inconsistente con el workflow  */
	public final static int CON_WF_INCONSISTENTE = 0;


	/** Constante que indica que el turno no tiene workflow  */
	public final static int SIN_WF = 0;

	/** Constante que indica que el turno desde se usa para consulta de rangos */
	public static final String TURNO_DESDE = "TURNO_DESDE";

	/** Constante que indica que el turno hasta se usa para consulta de rangos  */
	public static final String TURNO_HASTA = "TURNO_HASTA";
	
	/** Constante que indica que el turno hasta se usa para consulta de rangos  */
	public static final String TURNO_HASTA1 = "TURNO_HASTA1";
	
	public static final String FINALIZADO = "FINALIZADO";
	
	/** Constante que indica el valor por defecto del atributo anulado   */
	public static final String CAMPO_ANULADO_DEFECTO = "N";

        // turno vinculado keys --------------------------------------------------------------------

        /** parametro en sesion con id de turno vinculado */
        public static final String SOLICITUD_VINCULADA = "SOLICITUD_VINCULADA";
        /** parametro en sesion con obj de turno vinculado */
        public static final String SOLICITUD_VINCULADA_OBJETO = "SOLICITUD_VINCULADA_OBJETO";

		public static final String DESCRIPCION_MIGRADO = "MIGRACION";

        // -----------------------------------------------------------------------------------------
		
		// turno ejecución keys --------------------------------------------------------------------
		
		/** El estado final de un turno en ejecución finalizado */
		public static final String EJECUCION_TERMINADA = "2";
		/** Es estado en el cual el turno esta finalizado*/
		public static boolean EJECUCION_FINALIZADA = false;
		
		/** El estado final de un turno en ejecución finalizado */
		public static final String EJECUCION_HABILITADA = "1";
		/** Es estado en el cual el turno esta finalizado*/
		public static boolean EJECUCION_ACTIVA = true;
		



                 public final static String TURNO_TESTAMENTO_OBJ = "TURNO_TESTAMENTO_OBJ";
                 
                 public final static String TURNO_TESTAMENTO = "TURNO_TESTAMENTO";
                 
                 public final static String ERROR_FIND = "ERROR_FIND";
}
