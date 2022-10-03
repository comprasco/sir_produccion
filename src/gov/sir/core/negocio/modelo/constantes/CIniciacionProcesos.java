package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase que define las constantes que definen el nombre de los programas
 * concurrentes necesarios para la iniciación de procesos dentro de la
 * aplicación.
 * @author dlopez
*/
public final class CIniciacionProcesos {

	/**
	* Constante para la iniciación del proceso de Certificados Individuales. 
	*/ 
	public static final String INICIADOR_CERTIFICADOS_INDIVIDUALES = "CER_CREAR";

	
	/**
	* Constante para la iniciación del proceso de Consultas. 
	*/ 
	public static final String INICIADOR_CONSULTAS = "CON_CREAR";
	 

	/**
	* Constante para la iniciación del proceso de Fotocopias.
	*/ 
	public static final String INICIADOR_FOTOCOPIAS = "FOT_CREAR";
	 

	/**
	* Constante para la iniciación del proceso de Devoluciones
	*/ 
	public static final String INICIADOR_DEVOLUCIONES = "DEV_CREAR";
	
	
	/**
	* Constante para la iniciación del proceso de Correcciones
	*/ 
	public static final String INICIADOR_CORRECCIONES = "COS_CREAR";
	 
	
	/**
	* Constante para la iniciación del proceso de Registro
	*/ 
	public static final String INICIADOR_REGISTRO = "REG_CREAR";
	 

	/**
	* Constante para la iniciación del proceso de Reparto Notarial.
	*/ 
	public static final String INICIADOR_REPARTO_NOTARIAL = "REP_CREAR";
	 

	/**
	* Constante para la iniciación del proceso de Restitución de Reparto Notarial.
	*/ 
	public static final String INICIADOR_RESTITUCION_REPARTO_NOTARIAL = "RES_CREAR";
	 

	/**
	* Constante para la iniciación del proceso de Certificados Masivos. 
	*/ 
	public static final String INICIADOR_CERTIFICADOS_MASIVOS = "CER_CREAR_MASIVOS";
	 

    /**
    * Constante para indicar la respuesta de CONFIRMAR al instanciar procesos de Workflow.
    */
	public static final String RESPUESTA_CONFIRMAR_INICIO = "CONFIRMAR";
	
	
	/**
	* Constante para indicar la respuesta de EXITO al instanciar procesos de Workflow.
	*/
	public static final String RESPUESTA_CONFIRMAR_EXITO = "EXITO";
	
	
	/**
	* Constante para indicar el uso del Administrador Automático, a la hora de iniciar
	* procesos. 
	*/ 
	public static final String ADMINISTRADOR_AUTOMATICO_INICIO = "XADMIN";
	
	
	/**
	* Constante para indicar el usuario SAS_SISTEMA 
	*/ 
	public static final String USUARIO_SAS_SISTEMA_INICIO = "SAS_SISTEMA";
	
	
	/**
	* Constante para nombrar al atributo TIPO PROCESO INICIADOR
	*/ 
	public static final String TIPO_PROCESO_INICIADOR = "TIPO_PROCESO";


	/**
	* Constante para concatenar el tipo de consulta asociado con la creación de un 
	* proceso de Consultas.
	*/ 
	public static final String CONCATENACION_TIPO_CONSULTA  = "CONSULTA_";
	
	
	/**
	* Constante para designar el nombre del parámetro que se utiliza para indicar
	* en cual proceso debe instanciarse el workflow creado. 
    */
	public static final String PARAMETRO_PROCESO = "PROCESS";

}
