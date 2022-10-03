package gov.sir.core.eventos.administracion;

import java.util.List;
import java.util.Date;
import java.util.Map;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Folio;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la administración de objetos de Forseti.
 * @author jmendez
 */
public class EvnAdministracionForseti extends EvnSIR {

	/**Esta constante se utiliza  para identificar el evento de creación de un departamento */
	public static final String ADICIONA_DEPARTAMENTO = "ADICIONA_DEPARTAMENTO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un departamento */
	public static final String ELIMINA_DEPARTAMENTO = "ELIMINA_DEPARTAMENTO";

	/**Esta constante se utiliza  para identificar el evento de adición de un municipio */
	public static final String ADICIONA_MUNICIPIO = "ADICIONA_MUNICIPIO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un municipio */
	public static final String ELIMINA_MUNICIPIO = "ELIMINA_MUNICIPIO";

	/**Esta constante se utiliza  para identificar el evento de adición de una vereda */
	public static final String ADICIONA_VEREDA = "ADICIONA_VEREDA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una vereda */
	public static final String ELIMINA_VEREDA = "ELIMINA_VEREDA";

	/**Esta constante se utiliza  para identificar el evento de adición de un estado de folio */
	public static final String ADICIONA_ESTADO_FOLIO = "ADICIONA_ESTADO_FOLIO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un estado de folio */
	public static final String ELIMINA_ESTADO_FOLIO = "ELIMINA_ESTADO_FOLIO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de oficina */
	public static final String ADICIONA_TIPO_OFICINA = "ADICIONA_TIPO_OFICINA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de oficina */
	public static final String ELIMINA_TIPO_OFICINA = "ELIMINA_TIPO_OFICINA";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de documento*/
	public static final String ADICIONA_TIPO_DOCUMENTO = "ADICIONA_TIPO_DOCUMENTO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de documento*/
	public static final String ELIMINA_TIPO_DOCUMENTO = "ELIMINA_TIPO_DOCUMENTO";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de predio */
	public static final String ADICIONA_TIPO_PREDIO = "ADICIONA_TIPO_PREDIO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de predio */
	public static final String ELIMINA_TIPO_PREDIO = "ELIMINA_TIPO_PREDIO";

	/**Esta constante se utiliza  para identificar el evento de adición de un grupo de naturaleza jurídica */
	public static final String ADICIONA_GRUPO_NAT_JURIDICA = "ADICIONA_GRUPO_NAT_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un grupo de naturaleza jurídica */
	public static final String ELIMINA_GRUPO_NAT_JURIDICA = "ELIMINA_GRUPO_NAT_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de adición de un tipo de naturaleza jurídica */
	public static final String ADICIONA_TIPO_NAT_JURIDICA = "ADICIONA_TIPO_NAT_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un tipo de naturaleza jurídica */
	public static final String ELIMINA_TIPO_NAT_JURIDICA = "ELIMINA_TIPO_NAT_JURIDICA";
	
	/**Esta constante se utiliza  para identificar el evento de edición de un tipo de naturaleza jurídica */
	public static final String EDITA_TIPO_NAT_JURIDICA = "EDITA_TIPO_NAT_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de adición de un circulo registral */
	public static final String ADICIONA_CIRCULO_REGISTRAL = "ADICIONA_CIRCULO_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un circulo registral */
	public static final String ELIMINA_CIRCULO_REGISTRAL = "ELIMINA_CIRCULO_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de edición del NIT de un circulo registral */
	public static final String EDITA_NIT_CIRCULO_REGISTRAL = "EDITA_NIT_CIRCULO_REGISTRAL";
	
	/**Esta constante se utiliza  para identificar el evento de edición  de un circulo registral */
	public static final String EDITA_CIRCULO_REGISTRAL = "EDITA_CIRCULO_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de adición de un estado de anotación */
	public static final String ADICIONA_ESTADO_ANOTACION = "ADICIONA_ESTADO_ANOTACION";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un estado de anotación */
	public static final String ELIMINA_ESTADO_ANOTACION = "ELIMINA_ESTADO_ANOTACION";

	/**Esta constante se utiliza  para identificar el evento de adición de una naturaleza jurídica */
	public static final String ADICIONA_DOMINIO_NAT_JURIDICA = "ADICIONA_DOMINIO_NAT_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una naturaleza jurídica */
	public static final String ELIMINA_DOMINIO_NAT_JURIDICA = "ELIMINA_DOMINIO_NAT_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de adición de un círculo festivo */
	public static final String ADICIONA_CIRCULO_FESTIVO = "ADICIONA_CIRCULO_FESTIVO";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un círculo festivo */
	public static final String ELIMINA_CIRCULO_FESTIVO = "ELIMINA_CIRCULO_FESTIVO";

	/**Esta constante se utiliza  para identificar el evento de selección de un círculo festivo */
	public static final String SELECCIONA_CIRCULO_FESTIVO = "SELECCIONA_CIRCULO_FESTIVO";

	/**Esta constante se utiliza  para identificar el evento de adición de un eje  */
	public static final String ADICIONA_EJE = "ADICIONA_EJE";

	/**Esta constante se utiliza  para identificar el evento de eliminación de un eje  */
	public static final String ELIMINA_EJE = "ELIMINA_EJE";

	/**Esta constante se utiliza  para identificar el evento de adición de una zona registral  */
	public static final String ADICIONA_ZONA_REGISTRAL = "ADICIONA_ZONA_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una zona registral  */
	public static final String ELIMINA_ZONA_REGISTRAL = "ELIMINA_ZONA_REGISTRAL";

	/**Esta constante se utiliza  para identificar el evento de selección de una zona registral  */
	public static final String SELECCIONA_ZONA_REGISTRAL = "SELECCIONA_ZONA_REGISTRAL";
	
	/**Esta constante se utiliza  para identificar el evento de selección de una naturaleza juridica  */
	public static final String SELECCIONA_NATURALEZA_JURIDICA = "SELECCIONA_NATURALEZA_JURIDICA";

	/**Esta constante se utiliza  para identificar el evento de adición de una oficina origen  */
	public static final String ADICIONA_OFICINA_ORIGEN = "ADICIONA_OFICINA_ORIGEN";
	
	/**Esta constante se utiliza  para identificar el evento de adición de una oficina origen  */
	public static final String ADICIONA_OFICINA_ORIGEN_NOTARIA = "ADICIONA_OFICINA_ORIGEN_NOTARIA";

	/**Esta constante se utiliza  para identificar el evento de eliminación de una oficina origen  */
	public static final String ELIMINA_OFICINA_ORIGEN = "ELIMINA_OFICINA_ORIGEN";

	/**Esta constante se utiliza  para identificar el evento de consulta de oficinas origen por vereda */
	public static final String CONSULTA_OFICINA_ORIGEN_POR_VEREDA = "CONSULTA_OFICINA_ORIGEN_POR_VEREDA";
	
	/**Esta constante se utiliza  para identificar el evento de consulta de oficinas origen por vereda 
	 * se diferencia en la anterior constante porque ésta es usada para los datos de entrada del reporte 31 */
	public static final String CONSULTA_OFICINA_POR_VEREDA = "CONSULTA_OFICINA_POR_VEREDA";
	
	/**Esta constante se utiliza  para identificar el evento de consulta de notarias por vereda */
	public static final String CONSULTA_NOTARIAS_POR_MUNICIPIO = "CONSULTA_NOTARIAS_POR_MUNICIPIO";	

	/**Esta constante se utiliza  para identificar el evento de traslado de matrículas  */
	public static final String TRASLADAR = "TRASLADAR";
        
        /**Esta constante se utiliza para identificar el evento de catastro */
        public static final String CATASTRO = "CATASTRO";
    
	/** Constante que identifica la fecha inicial para el proceso de cargar los tipos de plantillas */
	public static final String CARGAR_PLANTILLAS_CERTIFICADOS = "CARGAR_PLANTILLAS_CERTIFICADOS";

	/** Constante que identifica la fecha inicial para el proceso de editar plantilla */
	public static final String EDITAR_PLANTILLA = "EDITAR_PLANTILLA";
	
	/** Constante que identifica la fecha inicial para el proceso de cargar los tipos de plantillas */
	public static final String CARGAR_PLANTILLA = "CARGAR_PLANTILLA";
	
	/** Esta constante se utiliza  para identificar el evento de edición de naturaleza jurídica*/
	public static final String EDITA_NATURALEZA_JURIDICA = "EDITA_NATURALEZA_JURIDICA";
	
	/** Esta constante se utiliza  para identificar el evento de reabrir folio*/
	public static final String REABRIR_FOLIO = "REABRIR_FOLIO";
	
	public static final String CONSULTAR_OFICINA_EDITAR = "CONSULTAR_OFICINA_EDITAR";
	
	public static final String ELIMINAR_OFICINA_ORIGEN = "ELIMINAR_OFICINA_ORIGEN";
	
	public static final String EDITAR_OFICINA_ORIGEN = "EDITAR_OFICINA_ORIGEN";
	
	public static final String LISTA_DEPARTAMENTOS_CIRCULO = "LISTA_DEPARTAMENTOS_CIRCULO";
	
	public static final String CONSULTA_DEPARTAMENTO= "CONSULTA_DEPARTAMENTO";
	
	public static final String INHABILITAR_CIRCULO="INHABILITAR_CIRCULO";
	
	public static final String SELECCIONA_ZONA_REGISTRAL_INHAB = "SELECCIONA_ZONA_REGISTRAL_INHAB";
	
	public static final String CARGAR_CIRCULOS_INHABILITADOS = "CARGAR_CIRCULOS_INHABILITADOS";
	
	public static final String TRASLADAR_FOLIOS_MASIVO="TRASLADAR_FOLIOS_MASIVO";
        
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega la constante VALIDAR_MATRICULA para la navegacion en el proceso traslado folio
         * Caso Mantis  :   07123
         */
         public static final String VALIDAR_MATRICULA = "VALIDAR_MATRICULA";

         /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agrega la constante VALIDAR_MATRICULA_MASIVO para la navegacion en el proceso traslado folio masivo
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public static final String VALIDAR_MATRICULA_MASIVO = "VALIDAR_MATRICULA_MASIVO";
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agrega la constante VALIDAR_MATRICULA_MASIVO para la navegacion en el proceso traslado folio masivo
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
         public static final String GENERAR_ARCHIVO = "GENERAR_ARCHIVO";
         public static final String TRASLADAR_MASIVO = "TRASLADAR_MASIVO";
         public static final String FOLIO_CONFIRMACION_MASIVO = "FOLIO_CONFIRMACION_MASIVO";
         public static final String TRASLADO_CONFIRMACION_MASIVO = "TRASLADO_CONFIRMACION_MASIVO";
         /*
         * @author      : Julio Alcázar Rivas
         * @change      : constantes para el proceso traslado folio
         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */
         public static final String FOLIO_CONFIRMACION = "FOLIO_CONFIRMACION";

         public static final String TRASLADO_CONFIRMACION = "TRASLADO_CONFIRMACION";   

    private Date fechaInicialCatastro;
    
    private Date fechaFinalCatastro;
        
	private Traslado traslado;

	private Departamento departamento;

	private Municipio municipio;

	private Vereda vereda;

	private EstadoFolio estadoFolio;

	private TipoOficina tipoOficina;

	private TipoDocumento tipoDocumento;

	private TipoPredio tipoPredio;

	private GrupoNaturalezaJuridica grupoNatJuridica;

	private NaturalezaJuridica naturalezaJuridica;

	private Circulo circulo;

	private EstadoAnotacion estadoAnotacion;

	private DominioNaturalezaJuridica dominioNatJuridica;

	private CirculoFestivo circuloFestivo;

	private Eje eje;

	private ZonaRegistral zonaRegistral;

	private Folio folioOrigenTraslado;

	private Folio folioDestinoTraslado;

	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

	private OficinaOrigen oficinaOrigen;

	private List categorias;
	
	private String respuesta;
	
	private String descripcion;

	private Folio folio;
	
	private String comentario;
	
	private String idOficinaOrigen;
	
        private List fundamentos;
        
	private String resolucion;

	private Circulo circuloOrigen;
	
	private Circulo circuloDestino;
	
	private List zonasRegistrales;
	
	private List usuariosTrasladar;
	
	private Map usuariosCrear;
	
	private List usuariosAsignarRolConsulta;	
	
	private Circulo circuloInhabilitado;
	
	private int trasladoMasivoInicio;
	
	private int trasladoMasivoFin;
        /*
         * @author      : Julio Alcázar Rivas
         * @change      : nuevos atributos para el manejo del proceso traslado folio
         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */
        private Date fechaResolucionOrigen;

        private Date fechaResolucionDestino;

        private String resolucionDestino;

        private String tipo_circulo;


        /*
         * @author      : Carlos Mario Torres Urina
         * @change      : nuevos atributos para el manejo del proceso traslado folio
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        private String idsession;
        private String municipioOrigen;
        private Object archivoOrigen;
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        private Long version;
        /*
         * @author      : Carlos Mario Torres Urina
         * @change      : Metodo accesor propiedad municipio origen
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public String getMunicipioOrigen() {
            return municipioOrigen;
        }
/*
         * @author      : Carlos Mario Torres Urina
         * @change      : Metodo accesor propiedad municipio origen
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public void setMunicipioOrigen(String municipioOrigen) {
            this.municipioOrigen = municipioOrigen;
        }
/*
         * @author      : Carlos Mario Torres Urina
         * @change      : Metodo accesor propiedad id_session
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public String getIdsession() {
            return idsession;
        }
/*
         * @author      : Carlos Mario Torres Urina
         * @change      : Metodo accesor propiedad id_session
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public void setIdsession(String idsession) {
            this.idsession = idsession;
        }
/*
         * @author      : Carlos Mario Torres Urina
         * @change      : Metodo accesor propiedad ArchivoOrigen
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public Object getArchivoOrigen() {
            return archivoOrigen;
        }
/*
         * @author      : Carlos Mario Torres Urina
         * @change      : Metodo accesor propiedad ArchivoOrigen
         * Caso Mantis  :11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public void setArchivoOrigen(Object archivoOrigen) {
            this.archivoOrigen = archivoOrigen;
        }

        public Date getFechaResolucionDestino() {
            return fechaResolucionDestino;
        }

        public void setFechaResolucionDestino(Date fechaResolucionDestino) {
            this.fechaResolucionDestino = fechaResolucionDestino;
        }

        public Date getFechaResolucionOrigen() {
            return fechaResolucionOrigen;
        }

        public void setFechaResolucionOrigen(Date fechaResolucionOrigen) {
            this.fechaResolucionOrigen = fechaResolucionOrigen;
        }     

        public String getResolucionDestino() {
            return resolucionDestino;
        }

        public void setResolucionDestino(String resolucionDestino) {
            this.resolucionDestino = resolucionDestino;
        }        

        public String getTipo_circulo() {
            return tipo_circulo;
        }

        public void setTipo_circulo(String tipo_circulo) {
            this.tipo_circulo = tipo_circulo;
        }


	/*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega la variable matriculas y sus metodos get y set
         * Caso Mantis  :   07123
         */
         private List matriculas;

         public List getMatriculas() {
             return matriculas;
         }

         public void setMatriculas(List matriculas) {
             this.matriculas = matriculas;
         }


        /**
	 *
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnAdministracionForseti(Usuario usuario, String tipoEvento, String respuesta, String descripcion) {
		super(usuario, tipoEvento);
		this.respuesta=respuesta;
		this.descripcion=descripcion;
	}
	
	/**
	 *
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnAdministracionForseti(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	public EvnAdministracionForseti(
		Usuario usuario,
		String tipoEvento,
		gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.usuarioSIR = usuarioSIR;
	}

	/**
	* @return
	*/
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return
	 */
	public EstadoFolio getEstadoFolio() {
		return estadoFolio;
	}

	/**
	 * @param folio
	 */
	public void setEstadoFolio(EstadoFolio folio) {
		estadoFolio = folio;
	}

	/**
	 * @return
	 */
	public TipoOficina getTipoOficina() {
		return tipoOficina;
	}

	/**
	 * @param oficina
	 */
	public void setTipoOficina(TipoOficina oficina) {
		tipoOficina = oficina;
	}

	/**
	 * @return
	 */
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param documento
	 */
	public void setTipoDocumento(TipoDocumento documento) {
		tipoDocumento = documento;
	}

	/**
	 * @return
	 */
	public TipoPredio getTipoPredio() {
		return tipoPredio;
	}

	/**
	 * @param predio
	 */
	public void setTipoPredio(TipoPredio predio) {
		tipoPredio = predio;
	}

	/**
	 * @return
	 */
	public GrupoNaturalezaJuridica getGrupoNatJuridica() {
		return grupoNatJuridica;
	}

	/**
	 * @param juridica
	 */
	public void setGrupoNatJuridica(GrupoNaturalezaJuridica juridica) {
		grupoNatJuridica = juridica;
	}

	/**
	 * @return
	 */
	public NaturalezaJuridica getNaturalezaJuridica() {
		return naturalezaJuridica;
	}

	/**
	 * @param juridica
	 */
	public void setNaturalezaJuridica(NaturalezaJuridica juridica) {
		naturalezaJuridica = juridica;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	/**
	 * @return
	 */
	public EstadoAnotacion getEstadoAnotacion() {
		return estadoAnotacion;
	}

	/**
	 * @param anotacion
	 */
	public void setEstadoAnotacion(EstadoAnotacion anotacion) {
		estadoAnotacion = anotacion;
	}

	/**
	 * @return
	 */
	public DominioNaturalezaJuridica getDominioNatJuridica() {
		return dominioNatJuridica;
	}

	/**
	 * @param juridica
	 */
	public void setDominioNatJuridica(DominioNaturalezaJuridica juridica) {
		dominioNatJuridica = juridica;
	}

	/**
	 * @return
	 */
	public Vereda getVereda() {
		return vereda;
	}

	/**
	 * @param vereda
	 */
	public void setVereda(Vereda vereda) {
		this.vereda = vereda;
	}

	/**
	 * @return
	 */
	public CirculoFestivo getCirculoFestivo() {
		return circuloFestivo;
	}

	/**
	 * @param festivo
	 */
	public void setCirculoFestivo(CirculoFestivo festivo) {
		circuloFestivo = festivo;
	}

	/**
	 * @return
	 */
	public Eje getEje() {
		return eje;
	}

	/**
	 * @param eje
	 */
	public void setEje(Eje eje) {
		this.eje = eje;
	}

	/**
	 * @return
	 */
	public ZonaRegistral getZonaRegistral() {
		return zonaRegistral;
	}

	/**
	 * @param registral
	 */
	public void setZonaRegistral(ZonaRegistral registral) {
		zonaRegistral = registral;
	}

	public Traslado getTraslado() {
		return traslado;
	}

	public void setTraslado(Traslado traslado) {
		this.traslado = traslado;
	}

	public Folio getFolioOrigenTraslado() {
		return folioOrigenTraslado;
	}

	public void setFolioOrigenTraslado(Folio folioOrigenTraslado) {
		this.folioOrigenTraslado = folioOrigenTraslado;
	}

	public Folio getFolioDestinoTraslado() {
		return folioDestinoTraslado;
	}

	public void setFolioDestinoTraslado(Folio folioDestinoTraslado) {
		this.folioDestinoTraslado = folioDestinoTraslado;
	}

        public void setFechaInicialCatastro(Date fechaInicialCatastro) {
                this.fechaInicialCatastro = fechaInicialCatastro;
        }
        
        public Date getFechaInicialCatastro () {
                return fechaInicialCatastro;
        }
        
        public void setFechaFinalCatastro(Date fechaFinalCatastro) {
                this.fechaFinalCatastro = fechaFinalCatastro;
        }
        
        public Date getFechaFinalCatastro() {
                return fechaFinalCatastro;
        }
        
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}
	/**
	 * @return
	 */
	public OficinaOrigen getOficinaOrigen() {
		return oficinaOrigen;
	}

	/**
	 * @param origen
	 */
	public void setOficinaOrigen(OficinaOrigen origen) {
		oficinaOrigen = origen;
	}

	/**
	 * @return
	 */
	public List getCategorias() {
		return categorias;
	}

	/**
	 * @param list
	 */
	public void setCategorias(List list) {
		categorias = list;
	}
	
	/**
	 * @return
	 */
	public String getRespuesta() {
		return respuesta;
	}
	
	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @return
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param string
	 */
	public void setComentario(String string) {
		comentario = string;
	}

	/**
	 * @return
	 */
	public String getIdOficinaOrigen() {
		return idOficinaOrigen;
	}

	/**
	 * @param string
	 */
	public void setIdOficinaOrigen(String string) {
		idOficinaOrigen = string;
	}

        public List getFundamentos() {
            return fundamentos;
        }

        public void setFundamentos(List fundamentos) {
            this.fundamentos = fundamentos;
        }

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
        }

	public String getResolucion() {
		return resolucion;
        }
        
	public Circulo getCirculoDestino() {
		return circuloDestino;
	}

	public void setCirculoDestino(Circulo circuloDestino) {
		this.circuloDestino = circuloDestino;
	}

	public Circulo getCirculoOrigen() {
		return circuloOrigen;
	}

	public void setCirculoOrigen(Circulo circuloOrigen) {
		this.circuloOrigen = circuloOrigen;
	}

	public List getUsuariosAsignarRolConsulta() {
		return usuariosAsignarRolConsulta;
	}

	public void setUsuariosAsignarRolConsulta(List usuariosAsignarRolConsulta) {
		this.usuariosAsignarRolConsulta = usuariosAsignarRolConsulta;
	}

	public Map getUsuariosCrear() {
		return usuariosCrear;
	}

	public void setUsuariosCrear(Map usuariosCrear) {
		this.usuariosCrear = usuariosCrear;
	}

	public List getUsuariosTrasladar() {
		return usuariosTrasladar;
	}

	public void setUsuariosTrasladar(List usuariosTrasladar) {
		this.usuariosTrasladar = usuariosTrasladar;
	}

	public List getZonasRegistrales() {
		return zonasRegistrales;
	}

	public void setZonasRegistrales(List zonasRegistrales) {
		this.zonasRegistrales = zonasRegistrales;
	}

	public Circulo getCirculoInhabilitado() {
		return circuloInhabilitado;
	}

	public void setCirculoInhabilitado(Circulo circuloInhabilitado) {
		this.circuloInhabilitado = circuloInhabilitado;
	}
	/**
	 * @return Returns the trasladoMasivoFin.
	 */
	public int getTrasladoMasivoFin() {
		return trasladoMasivoFin;
	}
	/**
	 * @param trasladoMasivoFin The trasladoMasivoFin to set.
	 */
	public void setTrasladoMasivoFin(int trasladoMasivoFin) {
		this.trasladoMasivoFin = trasladoMasivoFin;
	}
	/**
	 * @return Returns the trasladoMasivoInicio.
	 */
	public int getTrasladoMasivoInicio() {
		return trasladoMasivoInicio;
	}
	/**
	 * @param trasladoMasivoInicio The trasladoMasivoInicio to set.
	 */
	public void setTrasladoMasivoInicio(int trasladoMasivoInicio) {
		this.trasladoMasivoInicio = trasladoMasivoInicio;
	}


        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }
}
