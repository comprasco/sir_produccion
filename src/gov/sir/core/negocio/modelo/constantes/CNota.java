package gov.sir.core.negocio.modelo.constantes;

/**
 * Clase que define las constantes relacionadas con la nota informativa.
 * @author ppabon
 */
public final class CNota {


	/**  Constante que indica la descripción de la Nota.**/
	public static final String DESCRIPCION = "NOTA_DESCRIPCION";


	/**Constante que indica la posición de la nota en la lista de notas del turnos **/
	public static final String POSICION = "POSICION";
	
	/**
	* Constante que indica el nombre de la nota informativa
	*/
	public static final String NOMBRE = "NOTA_NOMBRE";
	
	
	/**
	* Constante que indica el nombre por default que debe tener la nota informativa
	* para delegar desde calificación hasta confrontacion correctiva
	*/
	public static final String NOMBRE_DELEGAR_CONFRONTACION_CORRECTIVA ="DELEGAR CALIFICACION A CONFRONTACION";
	
	
	/**
	* Constante que indica el nombre por default que debe tener la nota informativa
	* para delegar desde calificación hasta confrontacion correctiva
	*/
	public static final String NOMBRE_DELEGAR_CORRECCION_ENCABEZADO ="DELEGAR CALIFICACION A CORRECCION ENCABEZADO";
	
	/**
	* Constante que indica el nombre por default que debe tener la nota informativa
	* para un turno de registro que se ha finalizado.
	*/
	public static final String NOTA_INFORMATIVA_TURNO_FINALIZADO_REGISTRO ="NOTA INFORMATIVA TURNO RESTITUIDO";	
    
    public static final String INFORMATIVA_HOJA_RUTA ="INFORMATIVA HOJA RUTA";
    
    public static final String AGREGAR_MATRICULAS = "23178";
    
    public static final String ELIMINAR_MATRICULAS = "23179";
    
    public static final String AGREGAR_MATRICULAS_CORRECTIVA = "23181";
    
    public static final String ELIMINAR_MATRICULAS_CORRECTIVA = "23180";
    
    public static final String ELIMINAR_MATRICULAS_CALIFICACION = "23182";
    
    public static final String REVISION_CONFRONTACION_OK = "23184";
    
    public static final String REVISION_CONFRONTACION_BAD = "23183";
    
    public static final String NOTIFICAR_NOTA_DEVOLUTIVA = "23185";
    
    public static final String DOCUMENTOS_JUZGADO = "23186";
    
    public static final String DOCUMENTOS_USUARIO = "23187";
    
    /*SOlo para prod
    public static final String RECURSO_DE_REPOSICION = "23188";
    
    public static final String RESPUESTA_SEGUNDA_INSTANCIA = "23189";
    
    public static final String NO_PROCEDE_RECURSO = "23190";
    */
    
    //Capacitacion
    public static final String RECURSO_DE_REPOSICION = "23192";
    
    public static final String RESPUESTA_SEGUNDA_INSTANCIA = "23191";
    
    public static final String NO_PROCEDE_RECURSO = "23190";
    
    /**
	* Constante que indica si la nota es ingresada como nota editada o no
	*/
    public static final String EDITAR = "EDITAR";
	
	
}
