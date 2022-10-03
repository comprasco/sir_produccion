package gov.sir.core.eventos.comun;

import java.util.Hashtable;
import java.util.List;

/**
* @author mmunoz, dsalas, jfrias
* Este evento es el responsable de transportar una hashtable con las listas 
* de los objetos que devuelven los servicios.
* El payload que se trasporta es de tipo hashtable, y las llaves para acceder a estas
* listas son los constantes que aca se definieron.
*/
public class EvnRespSistema extends EvnSIRRespuesta {

	/**Esta constante identifica la lista de los tipos de identificacion para las personas.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_TIPOS_ID = "LISTA_TIPOS_ID";
        
        /**Esta constante identifica la lista de los tipos de documento para personas juridicas.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_TIPOS_ID_JURIDICA = "LISTA_TIPOS_ID_JURIDICA";
        
        /**Esta constante identifica la lista de los tipos de documento para personas naturales.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_TIPOS_ID_NATURAL = "LISTA_TIPOS_ID_NATURAL";
        
        /**Esta constante identifica la lista de las modalidades.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_MODALIDAD = "LISTA_MODALIDAD";
        
        /**Esta constante identifica la lista de las determinaciones del inmueble.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_DETERMINACION_INM = "LISTA_DETERMINACION_INM";
        
        /**Esta constante identifica la lista de los tipos de persona.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_TIPOS_PERSONA = "LISTA_TIPOS_PERSONA";
        
        /**Esta constante identifica la lista de los tipos de sexo para las personas.
	 * el tipo de los objetos de esta lista es OPLookupCodes */
	public static final String LISTA_TIPOS_SEXO = "LISTA_TIPOS_SEXO";

	/**Esta constante identifica la lista de los tipos de certificados.
	 * El tipo de los objetos de esta lista es TipoCertificado*/
	public static final String LISTA_TIPOS_CERTIFICADOS = "LISTA_TIPOS_CERTIFICADOS";

	/**Esta constante identifica la lista de los bancos 
	 * El tipo de los objetos de esta lista es Banco*/
	public static final String LISTA_BANCOS = "LISTA_BANCOS";
        
        /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Esta constante identifica la lista de los bancos 
	 * El tipo de los objetos de esta lista es Banco.
         */
	public static final String LISTA_BANCOS_FRANQUICIAS = "LISTA_BANCOS_FRANQUICIAS";

	/**Esta constante identifica la lista de los ejes para las direcciones 
	 * El tipo de los objetos de esta lista es Eje*/
	public static final String LISTA_TIPOS_EJES = "LISTA_TIPOS_EJES";

	/**Esta constante identifica la lista es de los tipos de los predios
	 * El tipo de los objetos de esta lista es TipoPredio*/
	public static final String LISTA_TIPOS_PREDIO = "LISTA_TIPOS_PREDIO";

	/**Esta constante identifica la lista es de los tipos de documento
	 * El tipo de los objetos de esta lista es TipoDocumento*/
	public static final String LISTA_TIPOS_DOCUMENTO = "LISTA_TIPOS_DOCUMENTO";

	/**Esta constante identifica la lista es de los tipos de documento
	 * El tipo de los objetos de esta lista es AlcanceGeografico*/
	public static final String LISTA_TIPOS_ALCANCE_GEOGRAFICO = "LISTA_TIPOS_ALCANCE_GEOGRAFICO";

	//TODO: Corregir doc
	/**Esta constante identifica la lista de intereses juridicos
	 * El tipo de los objetos de esta lista es  */
	public static final String LISTA_INTERES_JURIDICO = "LISTA_INTERES_JURIDICO";
	
	/**Esta constante identifica la lista de los grupos de naturalezas juridicas
	 * El tipo de los objetos de esta lista es GrupoNaturalezaJuridica */
	public static final String LISTA_GRUPOS_NATURALEZAS_JURIDICAS = "LISTA_GRUPOS_NATURALEZAS_JURIDICAS";
         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Contante para nombrar la lista de naturalezas juridicas sin filtrar
	 */
        /**Esta constante identifica la lista de los grupos de naturalezas juridicas
	 * El tipo de los objetos de esta lista es GrupoNaturalezaJuridica */
	public static final String LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V = "LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V";

	/**Esta constate identifica la lista de los posibles causales de reimpresion
	 * El tipo de los objetos de estalista es OPLookupCodes*/
	public static final String LISTA_CAUSALES_REIMPRESION = "LISTA_CAUSALES_REIMPRESION";

	/**Esta constante identifica la lista de tipos de fotocopia
	 * El tipo de los objetos de esta lista es TipoFotocopia*/
	public static final String LISTA_TIPOS_FOTOCOPIA = "LISTA_TIPOS_FOTOCOPIA";

	/**
	 * Esta constante identifica los subtipos de atencion disponibles
	 */
	public static final String LISTA_SUBTIPOS_ATENCION = "LISTA_SUBTIPOS_ATENCION";

	/**
	 * Esta constante identifica los subtipos de solicitud disponibles
	 */
	public static final String LISTA_SUBTIPOS_SOLICITUD = "LISTA_SUBTIPOS_SOLICITUD";

	/**
	 * Esta constante identifica los tipos de acto disponibles
	 */
	public static final String LISTA_TIPOS_ACTO = "LISTA_TIPOS_ACTO";
        
        /*
        * @autor          : JATENCIA 
        * @mantis        : 0015082 
        * @Requerimiento : 027_589_Acto_liquidación_copias 
        * @descripcion   : Se declara una variable para identificar el nuevo metodo
        * 
        */
        
        /**
	 * Esta constante identifica los tipos de acto disponibles
	 */
	public static final String LISTA_TIPOS_ACTO_DOS = "LISTA_TIPOS_ACTO_DOS";
        /* - Fin del bloque - */                

	/**
	 * Esta constante identifica los tipos de impuestos disponibles
	 */
	public static final String LISTA_TIPOS_IMPUESTO = "LISTA_TIPOS_IMPUESTO";

	/**
         * @author: Fernando Padilla
	 * @change: Requerimiento externo No. 135, Esta constante identifica los tipos de impuestos disponibles por circulo
	 */
	public static final String LISTA_TIPOS_IMPUESTO_CIRCULO = "LISTA_TIPOS_IMPUESTO_CIRCULO";

	/**
	 * Esta constante identifica los departamentos disponibles
	 */
	public static final String LISTA_DEPARTAMENTOS = "LISTA_DEPARTAMENTOS";

	/**
	 * Esta constante identifica los TIPOS DE TARIFA disponibles
	 */
	public static final String LISTA_TIPOS_TARIFA = "LISTA_TIPOS_TARIFA";

	/**
	 * Esta constante identifica las TARIFAS disponibles
	 */
	public static final String LISTA_TARIFAS = "LISTA_TARIFAS";

	/**
	 * Esta constante identifica los OPLOOOKUP TYPES disponibles
	 */
	public static final String LISTA_OPLOOKUP_TYPE = "LISTA_OPLOOKUP_TYPE";

	/**
	 * Esta constante identifica los OPLOOOKUP TYPES disponibles
	 */
	public static final String LISTA_OPLOOKUP_CODE = "LISTA_OPLOOKUP_CODE";

	/**
	 * Esta constante identifica los TIPOS DE CALCULO disponibles
	 */
	public static final String LISTA_TIPO_CALCULO = "LISTA_TIPO_CALCULO";

	/**
	 * Esta constante identifica los TIPOS DE DERECHO REGISTRAL disponibles
	 */
	public static final String LISTA_DERECHOS_REGISTRALES = "LISTA_DERECHOS_REGISTRALES";

	/**
	 * Esta constante identifica los TIPOS DE ESTADOS DE FOLIO disponibles
	 */
	public static final String LISTA_ESTADOS_FOLIO = "LISTA_ESTADOS_FOLIO";

	/**
	 * Esta constante identifica los TIPOS DE Oficina disponibles
	 */
	public static final String LISTA_TIPOS_OFICINA = "LISTA_TIPOS_OFICINA";

	/**Esta constante identifica los dominios de naturaleza juridica disponibles */
	public static final String LISTA_DOMINIOS_NATURALEZA_JURIDICA = "LISTA_DOMINIOS_NATURALEZA_JURIDICA";

	/**Esta constante identifica los circulos registrales disponibles */
	public static final String LISTA_CIRCULOS_REGISTRALES = "LISTA_CIRCULOS_REGISTRALES";

	/**Esta constante identifica los circulos registrales que operan en SIR */
	public static final String LISTA_CIRCULOS_REGISTRALES_FECHA = "LISTA_CIRCULOS_REGISTRALES_FECHA";
	
	/**Esta constante identifica la lista de estados de anotacion disponibles */
	public static final String LISTA_ESTADOS_ANOTACION = "LISTA_ESTADOS_ANOTACION";

	/**Esta constante identifica la lista de acciones notariales disponibles */
	public static final String LISTA_ACCIONES_NOTARIALES = "LISTA_ACCIONES_NOTARIALES";

	/**Esta constante identifica la lista de categorias disponibles */
	public static final String LISTA_CATEGORIAS = "LISTA_CATEGORIAS";

	/**Esta constante identifica la lista de las estaciones de recibo disponibles */
	public static final String LISTA_ESTACIONES_RECIBO = "LISTA_ESTACIONES_RECIBO";

	/**Esta constate identifica la lista de los posibles causales de restitución para una minuta
	 * El tipo de los objetos de esta lista es CausalRestitucion*/
	public static final String LISTA_CAUSALES_RESTITUCION = "LISTA_CAUSALES_RESTITUCION";

	/**Esta constate identifica la lista de las diferentes unidades de medida
	 * El tipo de los objetos de esta lista es oplookupcodes*/
	public static final String LISTA_UNIDADES_MEDIDA = "LISTA_UNIDADES_MEDIDA";

	/**Esta constante identifica la lista de las ROLES disponibles */
	public static final String LISTA_ROLES = "LISTA_ROLES";

	/**Esta constante identifica la lista de las LISTA_RESPONSABILIDADES disponibles */
	public static final String LISTA_RESPONSABILIDADES = "LISTA_RESPONSABILIDADES";

	/**Esta constante identifica la lista de las LISTA_DOCUMENTOS_PAGO disponibles */
	public static final String LISTA_TIPOS_PAGO = "LISTA_TIPOS_PAGO";

	/**Esta constante identifica la lista de las LISTA_ESTACIONES disponibles */
	public static final String LISTA_ESTACIONES = "LISTA_ESTACIONES";

	/**Esta constante identifica la lista de las LISTA_NIVELES disponibles */
	public static final String LISTA_NIVELES = "LISTA_NIVELES";

	/**Esta constante identifica la lista de las VALIDACIONES NOTA disponibles */
	public static final String LISTA_VALIDACIONES_NOTA = "LISTA_VALIDACIONES_NOTA";
	
	/** Esta contaste identifica la lista de Categorías de clasificación de notarías */
	public static final String LISTA_CATEGORIAS_NOTARIAS = "LISTA_CATEGORIAS_NOTARIAS";
	

	/**Esta constante identifica la lista de los procesos disponibles */
	public static final String LISTA_PROCESOS = "LISTA_PROCESOS";

	/**Esta constante identifica la lista de las validaciones disponibles */
	public static final String LISTA_VALIDACIONES = "LISTA_VALIDACIONES";

	/**Esta constante identifica la lista de las validaciones disponibles */
	public static final String LISTA_PROHIBICIONES = "LISTA_PROHIBICIONES";
	
		/**
	* Constante que identifica la lista de los tipos de visibilidad de notas
	* informativas existentes en el sistema.
	*/
	public static final String LISTA_VISIBILIDADES = "LISTA_VISIBILIDADES";
	
	
	/**
    * Constante que identifica la lista de los tipos de tarifas de certificados individuales. 
    */
    public static final String LISTA_TARIFAS_CERTIFICADOS = "LISTA_TARIFAS_CERTIFICADOS";
    

	/**
	* Constante que identifica la lista de los tipos de tarifas configurables por círculo 
	*/
	public static final String LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO = "LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO";
    

	/**
	* Constante que identifica la lista de naturalezas jurídicas habilitadas para calificación agrupadas por grupo de naturaleza jurídica
	*/
	public static final String LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION = "LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION";
	
	/** Constante que identifica la lista de naturalezas jurídicas habilitadas para calificación agrupadas por grupo de naturaleza jurídica	*/
	public static final String LISTA_NATURALEZAS_JURIDICAS_ENTIDAD = "LISTA_NATURALEZAS_JURIDICAS_ENTIDAD";
	
	/** Constante que identifica la lista de naturalezas jurídicas habilitadas para reparto notarial agrupadas por grupo de naturaleza jurídica	*/
	public static final String LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS = "LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS";	
	
	/** Constante que identifica la lista de las entidades publicas*/
	public static final String LISTA_ENTIDADES_PUBLICAS = "LISTA_ENTIDADES_PUBLICAS";
	
	/** Constante que identifica la lista de las entidades publicas que se encuentran activas*/
	public static final String LISTA_ENTIDADES_PUBLICAS_ACTIVAS = "LISTA_ENTIDADES_PUBLICAS_ACTIVAS";	
    
    /** Constante que identifica la lista de permisos para la correccion*/
    public static final String LISTA_PERMISOS_CORRECCION="LISTA_PERMISOS_CORRECCION";
    
	/** Constante que identifica el ip del balanceadro en la máquina de producción*/
	public static final String IP_BALANCEADOR="IP_BALANCEADOR";    
    
	/**
	* Constante para determinar si se requiere usar el ip del balanceador o si no.
	*/
	public static final String USAR_BALANCEADOR = "USAR_BALANCEADOR";

	/**
	* Constante para determinar la ruta física donde se encuentran las firmas.
	*/
	public static final String FIRMAS_PATH = "FIRMAS_PATH";
	
	/**
	* Constante para determinar el tipo de contenido para las imagenes..
	*/
	public static final String FIRMAS_CONTENT_TYPE = "FIRMAS_CONTENT_TYPE";

	/**
	* Constante para determinar la url del servlet de reportes.
	*/
	public static final String REPORTES_SERVLET_URL = "REPORTES_SERVLET_URL";

	/**
	* Constante para determinar el número máximo de impresiones de certificados.
	*/
	public static final String MAXIMO_COPIAS_CERTIFICADO = "MAXIMO_COPIAS_CERTIFICADO";


	
	public static final String LISTA_RELACION_BANCOS_CIRCULO="LISTA_RELACION_BANCOS_CIRCULO";
        
        /**Esta constante identifica la lista de los canales de recaudo 
	 * El tipo de los objetos de esta lista es CanalesRecaudo*/
	public static final String LISTA_CANALES_RECAUDO = "LISTA_CANALES_RECAUDO";
        
	/**Esta es la Hashtable que va a guardar todos los atributos que se deben 
	 * cargar al cargar la aplicacion */
	Hashtable params = new Hashtable();
	
	List listasContexto;

	/**
	 * Constructor de la clase.
	 * @param payload Hashtable que guarda los atributos
	 */
	public EvnRespSistema(Object payload) {
		super(payload);
		params = (Hashtable) payload;
	}
	
	public EvnRespSistema(Hashtable payload, String tipoEvento)
	{
		super(payload,tipoEvento);
		params=payload;
		
	}
	
	public EvnRespSistema(List listasConexto, String tipoEvento)
	{
		super(tipoEvento);
		this.listasContexto = listasConexto;
		this.setTipoEvento(tipoEvento);
	}

	public Hashtable getParams() {
		return params;
	}

	public void setParams(Hashtable params) {
		this.params = params;
	}

	public List getListasContexto() {
		return listasContexto;
	}

	public void setListasContexto(List listasContexto) {
		this.listasContexto = listasContexto;
	}

}
