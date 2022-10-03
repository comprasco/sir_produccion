package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;

/**
 * Envio de solicitudes con respecto al proceso de correcciones, de la capa web a la capa de negocio.
 * @author ppabon
 */
public class EvnCorreccion extends EvnSIR {

   private boolean impresionTemporalDeAuxiliarEnabled;


  /**
   *
   * id del circulo, para poder realizar la impresion en la
   * laser
   *
   * */
  private String circuloId;


  public static String ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENT
      = "ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENT";

    /**
     * Identificador para realizar el envio del folio con la
     * informacion temporal hacia la fase de revision / aprobacion (avanzar)
     * */
    public static final String ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_EVENT = "ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_EVENT";

	/** Esta acción permite guardar los cambios que se realizaron en la actuación administrativa y avanzar el turno*/
	public static final String TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA = "TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA";

    public static final String PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS
        = "PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS";

    public static final String PAGEEVENT_CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART
        = "PAGEEVENT_CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART";


    public static final String CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENT
        = "CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENT";
        
	public static final String CONSULTAR_SEG_ENG_EXISTENTES ="CONSULTAR_SEG_ENG_EXISTENTES";        

    /**
     * Identificador para realizar el envio del folio
     * hacia la fase de revision / analisis (devolver)
     * */
    public static final String ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_EVENT = "ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_EVENT";

    public static final String CORR_REVISIONAPROBACION__PRINTFORM_EVENT = "CORR_REVISIONAPROBACION__PRINTFORM_EVENT";
    /**
     * Id de arista de wf para poderse devolver a revision-analisis
     *
     * */
    public final static String DEVOLVERAEVISIONANALISIS_WF = "REVISION";


	/**
	 * Constante que identifica que se desea aprobar la corrección
	 */
	public final static String APROBAR = "APROBAR";
	
	public static final String SEGREGAR = "SEGREGAR";

	/**
	 * Constante que identifica que se desea cargar las diferencias entre un folio temporal y uno definitivo
	 * para ver las diferencias que se hicieron en la fase de corrección de documentos.
	 */
	public final static String CARGAR_CAMBIOS_PROPUESTOS = "CARGAR_CAMBIOS_PROPUESTOS";

	/**
	 * Constante que identifica que se desea forzar la aprobación de la corrección
	 */
	public final static String FORZAR_APROBACION = "FORZAR_APROBACION";

	/**
	 * Constante que identifica que se desea preguntar si se manda a mayor valor o se aprueba el caso definitivamente.
	 */
	public final static String PREGUNTAR_APROBACION = "PREGUNTAR_APROBACION";

	/**
	 * Constante que identifica que se desea negar la corrección
	 */
	public final static String NEGAR = "NEGAR";

	/**
	 * Constante que identifica que se desea aprobar la corrección en la fase de usuario especializado.
	 */
	public final static String APROBAR_ESPECIALIZADO = "APROBAR_ESPECIALIZADO";

	/**
	 * Constante que identifica que se desea negar la corrección en la fase de usuario especializado.
	 */
	public final static String NEGAR_ESPECIALIZADO = "NEGAR_ESPECIALIZADO";

	/**
	 * Constante que identifica que se desea aprobar la corrección en la fase de usuario digitador.
	 */
	public final static String APROBAR_DIGITACION = "APROBAR_DIGITACION";

	/**
	 * Constante que identifica que se negar la corrección en la fase de usuario digitador.
	 */
	public final static String NEGAR_DIGITACION = "NEGAR_DIGITACION";

	/**
	 * Constante que identifica que se desea aprobar la solicitud de microfilmación.
	 */
	public final static String APROBAR_MICROFILMACION = "APROBAR_MICROFILMACION";

	/**
	 * Constante que identifica que se negar la solicitud de microfilmación.
	 */
	public final static String NEGAR_MICROFILMACION = "NEGAR_MICROFILMACION";

	/**
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección
	 */
	public final static String TOMAR_FOLIO = "TOMAR_FOLIO";

	/**
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección por el corrector.
	 */
	public final static String TOMAR_FOLIO_CORRECCION = "TOMAR_FOLIO_CORRECCION";

	/**
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección por el digitador.
	 */
	public final static String TOMAR_FOLIO_DIGITACION = "TOMAR_FOLIO_DIGITACION";

	/**
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección por especializado.
	 */
	public final static String TOMAR_FOLIO_ESPECIALIZADO = "TOMAR_FOLIO_ESPECIALIZADO";

	/**
	 * Constante que identifica que se desea editar un folio porque hace parte de la corrección
	 */
	public final static String EDITAR_FOLIO = "EDITAR_FOLIO";

	/**
	 * Constante que identifica que se desea agregar o editar una anotación de un folio
	 */
	public final static String EDITAR_FOLIO_ANOTACION = "EDITAR_FOLIO_ANOTACION";

	/**
	 * Constante que identifica que se desea editar una lista de folios con la misma información.
	 */
	public final static String EDITAR_FOLIOS = "EDITAR_FOLIOS";

	/**
	 * Constante que identifica que se desea cargar un folio para que sea editado
	 */
	public final static String CARGAR_FOLIO = "CARGAR_FOLIO";

	/**
	 * Constante que identifica que se desea consultar un folio
	 */
	public final static String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

	/**
	 * Constante que identifica que se desea consultar un folio, pasandole el usuario para que traiga con los cambios temporales
	 */
	public final static String CONSULTAR_FOLIO_USUARIO = "CONSULTAR_FOLIO_USUARIO";

	/**
	 * Constante que identifica que se desea delegar el turno de la corrección a alguién diferente
	 */
	public final static String DELEGAR_CASO = "DELEGAR_CASO";

	/**
	 * Constante que identifica que se desea devolver la corrección a quién fue asignada originalmente
	 */
	public final static String DEVOLVER_CASO = "DEVOLVER_CASO";

	/**
	 * Constante que identifica que se desea redireccionar la corrección para que se trabaje por mayor valor o por especializado
	 */
	public final static String REDIRECCIONAR_CASO = "REDIRECCIONAR_CASO";

	/**
	 * Constante que identifica que se desea crear una solicitud
	 */
	public final static String CREAR_SOLICITUD = "CREAR_SOLICITUD";

	/**
	 * Constante que identifica que se desea validar una matricula
	 */
	public final static String VALIDAR_MATRICULA = "VALIDAR_MATRICULA";
	
	
	public final static String VALIDAR_MATRICULA_MIG = "VALIDAR_MATRICULA_MIG";


    /**
    * Constante que identifica que se desea validar una matricula y verificar si esta cerrada
    */
    public final static String VALIDAR_MATRICULA_EX = "VALIDAR_MATRICULA_EX";
    
    /**
     * Constante que identifica que se desea validar un rango de matriculas 
     */
    
    public final static String VALIDAR_RANGO_MATRICULAS_EX= "VALIDAR_RANGO_MATRICULAS_EX";
        
	/**
	 * Constante que identifica que se desea imprimir los certificados asociados a una solitud de Corrección.
	 */
	public final static String IMPRIMIR = "IMPRIMIR";

	public final static String IMPRIMIR_INDIVIDUAL = "IMPRIMIR_INDIVIDUAL";

	/**
	 * Constante que identifica que se desea avanzar el turno de Impresión de Certificados.
	 */
	public final static String AVANZAR_IMPRIMIR = "AVANZAR_IMPRIMIR";

	/**
	 * Constante que identifica que se desea notificar al Ciudadano sobre su solicitud de corrección.
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";

	/**
	 * Constante que identifica que se desea avanzar por ANTIGUO SISTEMA
	 */
	public final static String ANTIGUO_SISTEMA = "ANTIGUO_SISTEMA";

    /**
     * Constante que identifica que se desea aprobar el mayor valor
     */
    public final static String APROBAR_MAYOR_VALOR="APROBAR_MAYOR_VALOR";
    
    public static final String CONSULTAR_ULTIMOS_PROPIETARIOS = "CONSULTAR_ULTIMOS_PROPIETARIOS";

	/*
	 * Constantes de respuestas del WorkFlow
	 */

	/**
	 * Constante que identifica que se desea APROBAR la corrección
	 */
	public final static String CONFIRMAR_WF = "CONFIRMAR";
	
	/**
	 * Constante que identifica que se desea APROBAR la corrección
	 */
	public final static String CONFIRMAR_COR_INT_WF = "CONFIRMAR_COR_INT";

	/**
	 * Constantes de respuestas del WorkFlow
	 * Constante que identifica que se desea NEGAR la corrección
	 */
	public final static String NEGAR_WF = "NEGAR";

	/**
	 * Constante que identifica que se desea avanzar por DIGITACION
	 */
	public final static String DIGITACION = "DIGITACION";

	/**
	 * Constante que identifica que se desea avanzar por DIGITACION
	 */
	public final static String MICROFILMS = "MICROFILMS";

	/**
	 * Constante que identifica que se desea avanzar por MAYOR_VALOR
	 */
	public final static String MAYOR_VALOR = "MAYOR_VALOR";

	/**
	 * Constante que identifica que se desea avanzar por ESPECIALIZADO
	 */
	public final static String ESPECIALIZADO = "ESPECIALIZADO";

	/**
	 * Constante que identifica que se desea avanzar por EXITO
	 */
	public final static String EXITO = "EXITO";

	/**
	 * Constante que identifica que se desea avanzar por FRACASO
	 */
	public final static String FRACASO = "FRACASO";

	/**
	 * Constante que identifica que se desea aprobar la corrección
	 */
	public final static String CONFIRMAR_WF_ESP = "EXITO";

	/**
	 * Constante que identifica que se desea negar la corrección
	 */
	public final static String NEGAR_WF_ESP = "FRACASO";

	/**
	 * Constante que identifica que se debe actulizar la naturaleza juridica de una anotocacion
	*/
	private boolean actualizarNatJuridica = true;
	
	
	public static final String AGREGAR_CIUDADANO_ANOTACION = "AGREGAR_CIUDADANO_ANOTACION";
         /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Constantes de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
        public static final String VALIDAR_TURNO_TESTAMENTO="VALIDAR_TURNO_TESTAMENTO";
        public static final String TOMAR_TURNO_TESTAMENTO = "TOMAR_TURNO_TESTAMENTO";
	
	private Turno turno;
	private Fase fase;
	private Solicitud solicitudCorreccion;
	private Folio folio;
	private Nota nota;
	private List folios;
	private Estacion estacion = null;
	private String UID = null;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private String tipoActualizacion;
	private Circulo circulo;
	private Oficio oficio;
	private String impresora;
	private List turnosFolioMig = null;
	private Turno turnoPadre;
         /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega Atributo testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
        private Turno turnoTestamento;

        private gov.sir.core.negocio.modelo.Anotacion anotacion;

        protected boolean ejecutadaCorreccionSimple = false;

    private LiquidacionTurnoCorreccion liquidacion;

    private int numeroCopias = 1;

	/**Esta constante define la respuesta que se le da al workflow que se guarda en el evento*/
	private String respuestaWF = "";
	
	
	private List anotacionCiudadanos;
	
	private List listaCompletaCiudadanos;
	
	private boolean cargarSalvedad = true;

        public String idAnotacion;

        public String idFolio;

        /**
	 * Constante que identifica el Evento para encontrar el numero de anotaciones asociadas a otros folio
         * Autor : Ellery D. Robles Gomez
         * Caso Mantis : 07275
	 */
	public final static String ANOTACIONES_ASOCIADAS = "ANOTACIONES_ASOCIADAS";

        /**
	 * @param usuario
	 * @param idAnotacion
	 * @param idFolio
	 * @param tipoAccion
	 */
	public EvnCorreccion(String idFolio, String idAnotacion, String tipoAccion) {
		super(null, tipoAccion);
                this.idAnotacion = idAnotacion;
		this.idFolio = idFolio;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.usuarioSIR = usuarioSIR;
		this.turno = turno;
	}

	/**
	 * @param usuario
	 * @param solicitudCorreccion
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Solicitud solicitudCorreccion, Estacion estacion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudCorreccion = solicitudCorreccion;
		this.usuarioSIR = usuarioSIR;
		this.estacion = estacion;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.usuarioSIR = usuarioSIR;
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respWF;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param estacion
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnCorreccion(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.estacion = estacion;
		this.respuestaWF = respWF;
	}

	/**
	 * @param usuario
	 * @param folio
	 * @param turno
	 * @param fase
	 * @param estacion
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, List folios, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.estacion = estacion;
		this.folios = folios;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param nota
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, Nota nota, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.nota = nota;
		this.respuestaWF = respWF;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param folio
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Folio folio, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.folio = folio;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param usuario
	 * @param folio
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, Folio folio, String tipoAccion) {
		super(usuario, tipoAccion);
		this.folio = folio;
	}
 /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega nuevo constructor al evento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
        /**
	 * @param usuario
	 * @param turnoTestamento
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, Turno turnoTestamento, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turnoTestamento = turnoTestamento;
	}
         public EvnCorreccion( String tipoAccion ) {
                 super( null , tipoAccion);
         }


	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param folio
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Folio folio, String tipoAccion) {
		super(usuario, tipoAccion);
		this.usuarioSIR = usuarioSIR;
		this.folio = folio;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param folio
	 * @param folios
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnCorreccion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Folio folio, List folios, Turno turno, String tipoAccion, String tipoActualizacion, Oficio oficio) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.folio = folio;
		this.folios = folios;
		this.usuarioSIR = usuarioSIR;
		this.oficio = oficio;
		this.tipoActualizacion= tipoActualizacion;
	}

        public EvnCorreccion(
            org.auriga.core.modelo.transferObjects.Usuario usuario
          , String tipoAccion
        ) {
           super( usuario, tipoAccion );
        }


    /** @param usuarioAuriga
    /** @param aprobar_mayor_valor2
    /** @param turno2
    /** @param liq
    /** @param fase2
    /** @param usuarioNeg */
    public EvnCorreccion(Usuario usuario, String tipoAccion, Turno turno, LiquidacionTurnoCorreccion liq, Fase fase, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
        super(usuario,tipoAccion);
        this.turno=turno;
        this.liquidacion=liq;
        this.fase=fase;
        this.usuarioSIR=usuarioSIR;
    }

    public void setIdAnotacion(String idAnotacion) {
        this.idAnotacion = idAnotacion;
    }

    public String getIdAnotacion() {
        return idAnotacion;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public String getIdFolio() {
        return idFolio;
    }
	
    /**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @return
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 * @return
	 */
	public Solicitud getSolicitudCorreccion() {
		return solicitudCorreccion;
	}

	/**
	 * @param correccion
	 */
	public void setSolicitudCorreccion(Solicitud correccion) {
		solicitudCorreccion = correccion;
	}

	/**
	 * @return
	 */
	public String getRespuestaWF() {
		return respuestaWF;
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
	public Nota getNota() {
		return nota;
	}

	/**
	 * @param nota
	 */
	public void setNota(Nota nota) {
		this.nota = nota;
	}

	/**
	 * @return
	 */
	public List getFolios() {
		return folios;
	}

	/**
	 * @return
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @return el identificador unico de usuario
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @param string
	 */
	public void setUID(String string) {
		UID = string;
	}

	/**
	 * @return
	 */
	public String getTipoActualizacion() {
		return tipoActualizacion;
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
	public Oficio getOficio() {
		return oficio;
	}

	/**
	 * @param oficio
	 */
	public void setOficio(Oficio oficio) {
		this.oficio = oficio;
	}

    public LiquidacionTurnoCorreccion getLiquidacion() {
        return liquidacion;
    }

    public boolean isEjecutadaCorreccionSimple() {
        return ejecutadaCorreccionSimple;
    }

    public void setLiquidacion(LiquidacionTurnoCorreccion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public void setEjecutadaCorreccionSimple(boolean ejecutadaCorreccionSimple) {
        this.ejecutadaCorreccionSimple = ejecutadaCorreccionSimple;
    }

	/**
	 * @return
	 */
	public int getNumeroCopias() {
		return numeroCopias;
	}

  public String getCirculoId() {
    return circuloId;
  }

  /**
	 * @param i
	 */
	public void setNumeroCopias(int i) {
		numeroCopias = i;
	}

  public void setCirculoId(String circuloId) {
    this.circuloId = circuloId;
  }

public String getImpresora() {
	return impresora;
}

	public Anotacion getAnotacion() {
		return anotacion;
	}

	public Anotacion getT1_Anotacion() {
		return t1_Anotacion;
	}

	public boolean isImpresionTemporalDeAuxiliarEnabled() {
		return impresionTemporalDeAuxiliarEnabled;
	}

	public void setImpresora(String impresora) {
	this.impresora = impresora;
}

public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
	this.usuarioSIR = usuarioSIR;
}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public void setAnotacion(Anotacion anotacion) {
		this.anotacion = anotacion;
	}


	public void setUsuarioSir( gov.sir.core.negocio.modelo.Usuario usuarioSIR ) {
     setUsuarioSIR( usuarioSIR );
  }

  public gov.sir.core.negocio.modelo.Usuario getUsuarioSir() {
     return getUsuarioSIR();
  }

  public void setUsuarioAuriga( org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga  ) {
     super.setUsuario( param_UsuarioAuriga );
  }

  public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
     return super.getUsuario();
  }

  private gov.sir.core.negocio.modelo.Anotacion t1_Anotacion;

  public void setT1_Anotacion( gov.sir.core.negocio.modelo.Anotacion t1_Anotacion ) {
    this.t1_Anotacion = t1_Anotacion;
  } // end-method

	public void setImpresionTemporalDeAuxiliarEnabled(boolean
		 impresionTemporalDeAuxiliarEnabled) {
		this.impresionTemporalDeAuxiliarEnabled =
			 impresionTemporalDeAuxiliarEnabled;
	}

	public void setRespuestaWF(String respuestaWF) {
		this.respuestaWF = respuestaWF;
	}

	public List getAnotacionCiudadanos() {
		return anotacionCiudadanos;
	}

	public void setAnotacionCiudadanos(List anotacionCiudadanos) {
		this.anotacionCiudadanos = anotacionCiudadanos;
	}

	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}

	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}

	public List getTurnosFolioMig() {
		return turnosFolioMig;
	}

	public void setTurnosFolioMig(List turnosFolioMig) {
		this.turnosFolioMig = turnosFolioMig;
	}

	public boolean isActualizarNatJuridica() {
		return actualizarNatJuridica;
	}

	public void setActualizarNatJuridica(boolean actualizarNatJuridica) {
		this.actualizarNatJuridica = actualizarNatJuridica;
	}

	public boolean isCargarSalvedad() {
		return cargarSalvedad;
	}

	public void setCargarSalvedad(boolean cargarSalvedad) {
		this.cargarSalvedad = cargarSalvedad;
	}
	
	public void setFolios(List Folios)
	{
		this.folios=Folios;
	}
	
	public void setTurnoPadre(Turno padre)
	{
		this.turnoPadre=padre;
	}
	
	public Turno getTurnoPadre()
	{
		return this.turnoPadre;
	}
 /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega metodos accesores al atributo turnoTestamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
    public Turno getTurnoTestamento() {
        return turnoTestamento;
    }

    public void setTurnoTestamento(Turno turnoTestamento) {
        this.turnoTestamento = turnoTestamento;
    }
	
}
