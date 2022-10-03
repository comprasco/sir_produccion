package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con la administración de objetos de Forseti 
 * @author jmendez
 */
public class EvnRespAdministracionForseti extends EvnSIRRespuesta {

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un  departamento */
	public static final String ADICIONA_DEPARTAMENTO_OK = "ADICIONA_DEPARTAMENTO_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un  departamento */
	public static final String ELIMINA_DEPARTAMENTO_OK = "ELIMINA_DEPARTAMENTO_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un  municipio */
	public static final String ADICIONA_MUNICIPIO_OK = "ADICIONA_MUNICIPIO_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un  municipio */
	public static final String ELIMINA_MUNICIPIO_OK = "ELIMINA_MUNICIPIO_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de una vereda  */
	public static final String ADICIONA_VEREDA_OK = "ADICIONA_VEREDA_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de una vereda  */
	public static final String ELIMINA_VEREDA_OK = "ELIMINA_VEREDA_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un estado de folio  */
	public static final String ADICIONA_ESTADO_FOLIO_OK = "ADICIONA_ESTADO_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un estado de folio  */
	public static final String ELIMINA_ESTADO_FOLIO_OK = "ELIMINA_ESTADO_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un tipo de oficina  */
	public static final String ADICIONA_TIPO_OFICINA_OK = "ADICIONA_TIPO_OFICINA_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un tipo de oficina  */
	public static final String ELIMINA_TIPO_OFICINA_OK = "ELIMINA_TIPO_OFICINA_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un tipo de documento   */
	public static final String ADICIONA_TIPO_DOCUMENTO_OK = "ADICIONA_TIPO_DOCUMENTO_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un tipo de documento   */
	public static final String ELIMINA_TIPO_DOCUMENTO_OK = "ELIMINA_TIPO_DOCUMENTO_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un tipo de predio   */
	public static final String ADICIONA_TIPO_PREDIO_OK = "ADICIONA_TIPO_PREDIO_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un tipo de predio   */
	public static final String ELIMINA_TIPO_PREDIO_OK = "ELIMINA_TIPO_PREDIO_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un grupo de naturaleza jurídica   */
	public static final String ADICIONA_GRUPO_NAT_JURIDICA_OK = "ADICIONA_GRUPO_NAT_JURIDICA_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un grupo de naturaleza jurídica   */
	public static final String ELIMINA_GRUPO_NAT_JURIDICA_OK = "ELIMINA_GRUPO_NAT_JURIDICA_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un tioo de naturaleza jurídica   */
	public static final String ADICIONA_TIPO_NAT_JURIDICA_OK = "ADICIONA_TIPO_NAT_JURIDICA_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un tioo de naturaleza jurídica   */
	public static final String ELIMINA_TIPO_NAT_JURIDICA_OK = "ELIMINA_TIPO_NAT_JURIDICA_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un círculo registral */
	public static final String ADICIONA_CIRCULO_REGISTRAL_OK = "ADICIONA_CIRCULO_REGISTRAL_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un círculo registral */
	public static final String ELIMINA_CIRCULO_REGISTRAL_OK = "ELIMINA_CIRCULO_REGISTRAL_OK";

	/**Esta constante se utiliza  para identificar el evento de edición satisfactoria de un círculo registral */
	public static final String EDITA_CIRCULO_NIT_OK = "EDITA_CIRCULO_NIT_OK";
	
	/**Esta constante se utiliza  para identificar el evento de edición satisfactoria de un círculo registral */
	public static final String EDITA_CIRCULO_OK = "EDITA_CIRCULO_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un estado de anotación */
	public static final String ADICIONA_ESTADO_ANOTACION_OK = "ADICIONA_ESTADO_ANOTACION_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un estado de anotación */
	public static final String ELIMINA_ESTADO_ANOTACION_OK = "ELIMINA_ESTADO_ANOTACION_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un dominio de naturaleza jurídica */
	public static final String ADICIONA_DOMINIO_NAT_JURIDICA_OK = "ADICIONA_DOMINIO_NAT_JURIDICA_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un dominio de naturaleza jurídica */
	public static final String ELIMINA_DOMINIO_NAT_JURIDICA_OK = "ELIMINA_DOMINIO_NAT_JURIDICA_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un círculo festivo */
	public static final String ADICIONA_CIRCULO_FESTIVO_OK = "ADICIONA_CIRCULO_FESTIVO_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un círculo festivo */
	public static final String ELIMINA_CIRCULO_FESTIVO_OK = "ELIMINA_CIRCULO_FESTIVO_OK";

	/**Esta constante se utiliza  para identificar el evento de selección satisfactoria de un círculo festivo */
	public static final String SELECCIONA_CIRCULO_FESTIVO_OK = "SELECCIONA_CIRCULO_FESTIVO_OK";
	
	/**Esta constante se utiliza  para identificar el evento de selección satisfactoria de una naturaleza juridica */
	public static final String SELECCIONA_NATURALEZA_JURIDICA_OK = "SELECCIONA_NATURALEZA_JURIDICA_OK";
	
	/**Esta constante se utiliza  para identificar el evento de edición satisfactoria de una naturaleza juridica */
	public static final String EDITA_NATURALEZA_JURIDICA_OK = "EDITA_NATURALEZA_JURIDICA_OK";
	
	/**Esta constante se utiliza  para identificar el evento de edición satisfactoria de una naturaleza juridica */
	public static final String EDITA_DETALLES_NATURALEZA_JURIDICA_OK = "EDITA_DETALLES_NATURALEZA_JURIDICA_OK";
	
	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de un eje */
	public static final String ADICIONA_EJE_OK = "ADICIONA_EJE_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de un eje */
	public static final String ELIMINA_EJE_OK = "ELIMINA_EJE_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de una zona registral  */
	public static final String ADICIONA_ZONA_REGISTRAL_OK = "ADICIONA_ZONA_REGISTRAL_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de una zona registral  */
	public static final String ELIMINA_ZONA_REGISTRAL_OK = "ELIMINA_ZONA_REGISTRAL_OK";

	/**Esta constante se utiliza  para identificar el evento de selección satisfactoria de una zona registral  */
	public static final String SELECCIONA_ZONA_REGISTRAL_OK = "SELECCIONA_ZONA_REGISTRAL_OK";
	
	/**Esta constante se utiliza  para identificar el evento de selección satisfactoria de una zona registral  */
	public static final String SELECCIONA_ZONA_REGISTRAL_INHAB_OK = "SELECCIONA_ZONA_REGISTRAL_INHAB_OK";

	/**Esta constante se utiliza  para identificar el evento de creación satisfactoria de una oficina origen  */
	public static final String ADICIONA_OFICINA_ORIGEN_OK = "ADICIONA_OFICINA_ORIGEN_OK";

	/**Esta constante se utiliza  para identificar el evento de eliminación satisfactoria de una oficina origen  */
	public static final String ELIMINA_OFICINA_ORIGEN_OK = "ELIMINA_OFICINA_ORIGEN_OK";

	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de  oficinas origen  por vereda */
	public static final String CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK =
		"CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK";

	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de  oficinas origen  por vereda 
	 * se diferencia en la anterior constante porque ésta es usada para los datos de entrada del reporte 31 */
	public static final String CONSULTA_OFICINA_BY_VEREDA_OK = "CONSULTA_OFICINA_BY_VEREDA_OK";

	/**Esta constante se utiliza  para identificar el evento de traslado  satisfactorio de folios */
	public static final String TRASLADO_OK = "TRASLADO_OK";

    /** Esta constante se utiliza para identificar el evento de catastro satisfactorio */
    public static final String CATASTRO_OK = "CATASTRO_OK";
    
	/** Constante que identifica la fecha inicial para el proceso de cargar los tipos de plantillas */
	public static final String CARGAR_PLANTILLAS_CERTIFICADOS = "CARGAR_PLANTILLAS_CERTIFICADOS";
	
	/** Constante que identifica la fecha inicial para el proceso de cargar los tipos de plantillas */
	public static final String CARGAR_PLANTILLA = "CARGAR_PLANTILLA";

	/** Constante que identifica la fecha inicial para el proceso de editar plantilla */
	public static final String EDITAR_PLANTILLA = "EDITAR_PLANTILLA";
	
	/** Esta constante se utiliza para identificar el evento de reabrir folio satisfactorio */
	public static final String REABRIR_FOLIO_OK= "REABRIR_FOLIO_OK";
	
	public static final String OFICINA_ORIGEN_EDICION = "OFICINA_ORIGEN_EDICION";
	
	public static final String LISTA_DEPARTAMENTOS_CIRCULO= "LISTA_DEPARTAMENTOS_CIRCULO";
	
	public static final String CONSULTA_DEPARTAMENTO="CONSULTA_DEPARTAMENTO";
	
	public static final String INHABILITAR_CIRCULO_OK="INHABILITAR_CIRCULO_OK";
	
	public static final String CARGAR_CIRCULOS_INHABILITADOS_OK="CARGAR_CIRCULOS_INHABILITADOS_OK";
	
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega la constante VALIDAR_MATRICULA para la navegacion en el proceso traslado folio
         * Caso Mantis  :   07123
         */
         public static final String VALIDAR_MATRICULA = "VALIDAR_MATRICULA";
         /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agrega la constante VALIDAR_MATRICULA_MASIVO para la navegacion en el proceso traslado folio
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public static final String VALIDAR_MATRICULA_MASIVO = "VALIDAR_MATRICULA_MASIVO";
         public static final String VALIDAR_MATRICULA_MASIVO_OK = "VALIDAR_MATRICULA_MASIVO_OK";
         public static final String GENERAR_ARCHIVO_OK = "GENERAR_ARCHIVO_OK";
         public static final String CARGAR_ARCHIVO_OK ="CARGAR_ARCHIVO_OK";
         public static final String TRASLADO_MASIVO_OK ="TRASLADO_MASIVO_OK";
         public static final String TRASLADO_CONFIRMACION_MASIVO = "TRASLADO_CONFIRMACION_MASIVO";
         public static final String VALIDAR_MATRICULA_MASIVO_ERROR = "VALIDAR_MATRICULA_MASIVO_ERROR";
         /*
         * @author      : Julio Alcázar Rivas
         * @change      : constantes para el proceso traslado folio
         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */
         public static final String FOLIO_CONFIRMACION = "FOLIO_CONFIRMACION";
         public static final String TRASLADO_CONFIRMACION = "TRASLADO_CONFIRMACION";

	private List listPlantillas;
	private List departamentos;
	private List listaZonasInhabilitar;
        /*
                    *  @author Carlos Torres
                    *  @chage   se cambia el metodo que consulta la lista de grupos de naturaleza juridica
                    *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                    */
        private List listaNatJuridica;
	
	/**
	 * @param payload
	 */
	public EvnRespAdministracionForseti(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespAdministracionForseti(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}
	
	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespAdministracionForseti(Object payload, List listPlantillas, String tipoEvento) {
		super(payload, tipoEvento);
		this.listPlantillas=listPlantillas;
	}

	public List getListPlantillas(){
		return this.listPlantillas;
	}
	
	public void setDepartamentos(List deptos){
		this.departamentos=deptos;
	}
	
	public List getDepartamentos(){
		return departamentos;
	}

	public List getListaZonasInhabilitar() {
		return listaZonasInhabilitar;
	}

	public void setListaZonasInhabilitar(List listaZonasInhabilitar) {
		this.listaZonasInhabilitar = listaZonasInhabilitar;
	}

        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega la variable matricula y sus metodos get y set
         * Caso Mantis  :   07123
         */
         private String matricula;

         public String getMatricula() {
             return matricula;
         }

         public void setMatricula(String matricula) {
           this.matricula = matricula;
         }

        /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agregan las variables listaMatriculas, errores
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         private List<String> listaMatriculas ;
         private List errores;
          /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   metodo accesor para la propiedad listaMatriculas
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public List<String> getListaMatirculas()
         {
            return listaMatriculas;
         }
            /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   metodo accesor para la propiedad listaMatriculas
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public void setListaMatriculas(List listaMatriculas)
         {
            this.listaMatriculas = listaMatriculas;
         }
           /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   metodo accesor para la propiedad errores
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public List getErrores() {
            return errores;
         }
 /*
           /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   metodo accesor para la propiedad errorres
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public void setErrores(List errores) {
            this.errores = errores;
         }

                    /*
                    *  @author Carlos Torres
                    *  @chage
                    *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                    */
    public List getListaNatJuridica() {
        return listaNatJuridica;
    }

    public void setListaNatJuridica(List listaNatJuridica) {
        this.listaNatJuridica = listaNatJuridica;
    }
}
