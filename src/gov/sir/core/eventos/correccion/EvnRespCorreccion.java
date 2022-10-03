package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
 /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se importa la clase Testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
import gov.sir.core.negocio.modelo.Testamento;

import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;
import java.util.Map;


/**
 * Envio de solicitudes de respuesta con respecto al proceso de correcciones, de la capa de negocio a la capa web.
 * @author ppabon
 */
public class EvnRespCorreccion extends EvnSIRRespuesta {

  public static final String CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK
      = "CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK";


  public static final String PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK
      = "PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK";


  public static final String ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENTRESPOK
      = "ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENTRESPOK";

		public static final String CONSULTAR_SEG_ENG_EXISTENTES ="CONSULTAR_SEG_ENG_EXISTENTES";

        /**
         * Identificador para informar al usuario
         * que se procesó el envio a revision / aprobacion
         * */
        public static final String ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK = "ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK";

		/** Esta acción permite guardar los cambios que se realizaron en la actuación administrativa y avanzar el turno*/
		public static final String TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA = "TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA";

        /**
         * Para poderse devolver a corregir permisos
         * */
        public static final String ENVIARCORRECCIONSIMPLEAREVISIONANALISIS__EVENTRESP_OK = "ENVIARCORRECCIONSIMPLEAREVISIONANALISIS__EVENTRESP_OK";

	/**
	 * Constante que identifica que se desea aprobar la corrección
	 */
	public final static String APROBAR = "APROBAR";


   // por defecto

	/**
	 * Constante que identifica que se desea cargar las diferencias entre un folio temporal y uno definitivo
	 * para ver las diferencias que se hicieron en la fase de corrección de documentos.
	 */
	public final static String CARGAR_CAMBIOS_PROPUESTOS = "CARGAR_CAMBIOS_PROPUESTOS";

	/**
	 * Constante que identifica que se desea forzar la aprobación de la corrección. Esto ocurre cuando una corrección paso ha pasado
	 * por el proceso de pago por mayor valor y desea forzarse por alguna razón el ingreso nuevamente
	 * a éste proceso.
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
	 * Constante que identifica que se desea bloquear los folios mientras se realiza el proceso de corrección
	 */
	public final static String TOMAR_FOLIO = "TOMAR_FOLIO";

	/**
	 * Constante que identifica que se desea consultar un folio
	 */
	public final static String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

	/**
	 * Constante que identifica que se desea consultar un folio, pasandole el usuario para que traiga con los cambios temporales
	 */
	public final static String CONSULTAR_FOLIO_USUARIO = "CONSULTAR_FOLIO_USUARIO";

	/**
	 * Constante que identifica que se desea editar los folios porque hace parte de la corrección
	 */
	public final static String EDITAR_FOLIO = "EDITAR_FOLIO";

	/**
	 * Constante que identifica que se desea editar una lista de folios con la misma información.
	 */
	public final static String EDITAR_FOLIOS = "EDITAR_FOLIOS";

	/**
	 * Constante que identifica que se desea cargar un folio para que sea editado
	 */
	public final static String CARGAR_FOLIO = "CARGAR_FOLIO";

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
	 * Constante que identifica que se desea imprimir los certificados asociados a una solitud de Corrección.
	 */
	public final static String IMPRIMIR = "IMPRIMIR";

	public final static String IMPRIMIR_INDIVIDUAL = "IMPRIMIR_INDIVIDUAL";


	public static final String CONSULTAR_ULTIMOS_PROPIETARIOS ="CONSULTAR_ULTIMOS_PROPIETARIOS";

	public static final String CONSULTAR_PROPIETARIOS_FOLIO ="CONSULTAR_PROPIETARIOS_FOLIO";	

	/**
	 * Constante que identifica que se desea avanzar el turno de Impresión de Certificados.
	 */
	public final static String AVANZAR_IMPRIMIR = "AVANZAR_IMPRIMIR";

	/**
	 * Constante que identifica que se desea notificar al Ciudadano sobre su solicitud de corrección.
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";
	
	public static final String VOLVER_AGREGAR_CIUDADANOS ="VOLVER_AGREGAR_CIUDADANOS";

        /**
	 * Constante que identifica que se desea notificar al Ciudadano sobre su solicitud de corrección.
	 */
	public final static String ANOTACIONES_ASOCIADAS_OK = "ANOTACIONES_ASOCIADAS_OK";
         /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Constantes de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
        public final static String VALIDAR_TURNO_TESTAMENTO_OK = "VALIDAR_TURNO_TESTAMENTO_OK";
        public final static String CARGAR_TESTAMENTO_OK = "CARGAR_TESTAMENTO_OK"; 
        public final static String GUARDAR_TEMPORAL_OK = "GUARDAR_TEMPORAL_OK";
	
	//nuevo parametro
	private List rangofolios;
	
	private Turno turno;
	private Fase fase;
	private Solicitud solicitudCorreccion;
	private LlaveBloqueo llaveBloqueo;
	private String matricula;
 /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agregan atributos turnoTestamento, testamento, salvedad
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
        private Turno turnoTestamento;
        private Testamento testamento;
        private Map salvedad;
	private Folio folio;
	private List foliosPadre;
	private List foliosHijo;
	private List gravamenes;
	private List medidasCautelares;
	private List salvedadesAnotaciones;
	private List cancelaciones;
	private long numeroAnotaciones;
	private List deltasFolio;
	private List validaciones;
	private List falsaTradicion;
	private List anotacionesInvalidas;
	private String nextOrden;
	private OficinaOrigen oficinaOrigen;
	private long numSegregacionesVacias;
	
	private List propietariosFolios;
	
	private List anotacionesPatrimonioFamiliar;
	private List anotacionesAfectacionVivienda;

   // folio original
   private gov.sir.core.negocio.modelo.Folio t0_Folio;

   // mapa de folios
   private java.util.Map querySolicitudFolioEstadoMap;
   
   private List listaCompletaCiudadanos;
   
   /**Variable que permite la validacion de una salvedad*/
   private boolean validarSalvedad = true;
   
   private boolean modificoCiudadanoTmp = false;


	private List foliosNoImpresos; /**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, Turno turno, String tipoAccion, List validaciones) {
		super(usuario, tipoAccion);
		this.validaciones = validaciones;
		this.turno = turno;
	}

	/**
	 * @param usuario
	 * @param matricula
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, String matricula, String tipoAccion) {
		super(usuario, tipoAccion);
		this.matricula = matricula;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param llaveBloqueo
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, Turno turno, LlaveBloqueo llaveBloqueo, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.llaveBloqueo = llaveBloqueo;
	}

	/**
	 * @param usuario
	 * @param solicitudCorreccion
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, Solicitud solicitudCorreccion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudCorreccion = solicitudCorreccion;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param llaveBloqueo
	 * @param fase
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, Turno turno, LlaveBloqueo llaveBloqueo, Fase fase, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.llaveBloqueo = llaveBloqueo;
	}

	/**
	 * @param folio
	 * @param foliosPadre
	 * @param foliosHijo
	 * @param gravamenes
	 * @param medidasCautelares
	 * @param salvedadesAnotaciones
	 * @param cancelaciones
	 * @param numeroAnotaciones
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Folio folio, List foliosPadre, List foliosHijo, List gravamenes, List medidasCautelares, List falsaTradicion, List anotacionesInvalidas, List salvedadesAnotaciones , List cancelaciones, long numeroAnotaciones, String tipoAccion) {
		super(tipoAccion,tipoAccion);
		this.folio = folio;
		this.foliosHijo = foliosHijo;
		this.foliosPadre = foliosPadre;
		this.gravamenes = gravamenes;
		this.medidasCautelares = medidasCautelares;
		this.falsaTradicion = falsaTradicion;
		this.anotacionesInvalidas = anotacionesInvalidas;
		this.salvedadesAnotaciones = salvedadesAnotaciones;
		this.cancelaciones = cancelaciones;
		this.numeroAnotaciones = numeroAnotaciones;
	}

	/**
	 * Este constructor es usado cuando se quiere cargar un objeto DeltaFolio para
	 * ver las diferencias entre los datos que estan en temporal y los definitivos de un folio.
	 * @param usuario
	 * @param matricula
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Usuario usuario, List deltasFolio, String tipoAccion) {
		super(usuario, tipoAccion);
		this.deltasFolio = deltasFolio;
	}

	/**
	 * Este constructor es usado cuando se quiere cargar un objeto DeltaFolio para
	 * ver las diferencias entre los datos que estan en temporal y los definitivos de un folio.
	 * @param folio
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Folio folio, String tipoAccion) {
		super(folio, tipoAccion);
	}


	/**
	 * @param folio
	 * @param tipoAccion
	 */
	public EvnRespCorreccion(Object obj, String tipoAccion) {
		super(obj, tipoAccion);
	}

        public EvnRespCorreccion( String tipoAccion ) {
                 super( null, tipoAccion );
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
	public LlaveBloqueo getLlaveBloqueo() {
		return llaveBloqueo;
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
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public List getFoliosHijo() {
		return foliosHijo;
	}

	/**
	 * @return
	 */
	public List getFoliosPadre() {
		return foliosPadre;
	}

	/**
	 * @return
	 */
	public List getGravamenes() {
		return gravamenes;
	}

	/**
	 * @return
	 */
	public List getMedidasCautelares() {
		return medidasCautelares;
	}

	/**
	 * @return
	 */
	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/**
	 * @return
	 */
	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}


	/**
	 * @return
	 */
	public List getCancelaciones() {
		return cancelaciones;
	}



	/**
	 * @return
	 */
	public List getDeltasFolio() {
		return deltasFolio;
	}

	/**
	 * @return
	 */
	public List getFoliosNoImpresos() {
		return foliosNoImpresos;
	}

	/**
	 * @param list
	 */
	public void setFoliosNoImpresos(List list) {
		foliosNoImpresos = list;
	}

    public void setFoliosPadre(List foliosPadre) {
        this.foliosPadre = foliosPadre;
    }

    public void setFoliosHijo(List foliosHijo) {
        this.foliosHijo = foliosHijo;
    }

    public void setNumeroAnotaciones(long numeroAnotaciones) {
        this.numeroAnotaciones = numeroAnotaciones;
    }

    public void setFolio(Folio folio) {
        this.folio = folio;
    }

	public void setFolioT2_complementacion_complementacion(String
		 folioT2_complementacion_complementacion) {
		this.folioT2_complementacion_complementacion =
			 folioT2_complementacion_complementacion;
	}

	public void setFolioT1_complementacion_complementacion(String
		 folioT1_complementacion_complementacion) {
		this.folioT1_complementacion_complementacion =
			 folioT1_complementacion_complementacion;
	}

	public void setFolioT0_complementacion_complementacion(String
		 folioT0_complementacion_complementacion) {
		this.folioT0_complementacion_complementacion =
			 folioT0_complementacion_complementacion;
	}

	public void setRuleComplementacionAdicionaEnabled(boolean
		 ruleComplementacionAdicionaEnabled) {
		this.ruleComplementacionAdicionaEnabled =
			 ruleComplementacionAdicionaEnabled;
	}

	public void setT0_Folio(Folio t0_Folio) {
		this.t0_Folio = t0_Folio;
	}


	/**
	 * @return
	 */
	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	/**
	 * @return
	 */
	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	public String getFolioT0_complementacion_complementacion() {
		return folioT0_complementacion_complementacion;
	}

	public String getFolioT1_complementacion_complementacion() {
		return folioT1_complementacion_complementacion;
	}

	public String getFolioT2_complementacion_complementacion() {
		return folioT2_complementacion_complementacion;
	}

	public boolean isRuleComplementacionAdicionaEnabled() {
		return ruleComplementacionAdicionaEnabled;
	}

	public Folio getT0_Folio() {
		return t0_Folio;
	}

	public Anotacion getT0_Anotacion() {
		return t0_Anotacion;
	}

	boolean ruleComplementacionAdicionaEnabled;
	String folioT0_complementacion_complementacion;
    String folioT1_complementacion_complementacion;
    String folioT2_complementacion_complementacion;

    public static final String CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENTRESP_OK
        = "CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENTRESP_OK";

    private Anotacion t0_Anotacion = null;

     public void setT0_Anotacion(Anotacion local_Result_t0Anotacion) {
            this.t0_Anotacion = local_Result_t0Anotacion;
     }

	/**
	 * @return
	 */
	public String getNextOrden() {
		return nextOrden;
	}

	/**
	 * @param string
	 */
	public void setNextOrden(String string) {
		nextOrden = string;
	}

	/**
	 * @return
	 */
	public OficinaOrigen getOficinaOrigen() {
		return oficinaOrigen;
	}

  public Map getQuerySolicitudFolioEstadoMap() {
    return querySolicitudFolioEstadoMap;
  }

  /**
	 * @param origen
	 */
	public void setOficinaOrigen(OficinaOrigen origen) {
		oficinaOrigen = origen;
	}

  public void setQuerySolicitudFolioEstadoMap(Map querySolicitudFolioEstadoMap) {
    this.querySolicitudFolioEstadoMap = querySolicitudFolioEstadoMap;
  }

public List getPropietariosFolios() {
	return propietariosFolios;
}

public void setPropietariosFolios(List propietariosFolios) {
	this.propietariosFolios = propietariosFolios;
}

public List getAnotacionesAfectacionVivienda() {
	return anotacionesAfectacionVivienda;
}

public void setAnotacionesAfectacionVivienda(List anotacionesAfectacionVivienda) {
	this.anotacionesAfectacionVivienda = anotacionesAfectacionVivienda;
}

public List getAnotacionesPatrimonioFamiliar() {
	return anotacionesPatrimonioFamiliar;
}

public void setAnotacionesPatrimonioFamiliar(List anotacionesPatrimonioFamiliar) {
	this.anotacionesPatrimonioFamiliar = anotacionesPatrimonioFamiliar;
}

public List getListaCompletaCiudadanos() {
	return listaCompletaCiudadanos;
}

public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
	this.listaCompletaCiudadanos = listaCompletaCiudadanos;
}

public boolean isCargarSalvedad() {
	return validarSalvedad;
}

public void setCargarSalvedad(boolean cargarSalvedad) {
	this.validarSalvedad = cargarSalvedad;
}

public void setRangoFolio(List Folios) {
	this.rangofolios=Folios;
}

public List getRangoFolio() {
	return this.rangofolios;
}

public boolean isModificoCiudadanoTmp() {
	return modificoCiudadanoTmp;
}

public void setModificoCiudadanoTmp(boolean modificoCiudadanoTmp) {
	this.modificoCiudadanoTmp = modificoCiudadanoTmp;
}

public long getNumSegregacionesVacias() {
	return numSegregacionesVacias;
}

public void setNumSegregacionesVacias(long numSegregacionesVacias) {
	this.numSegregacionesVacias = numSegregacionesVacias;
}

private List foliosDerivadoHijo;
public List getFoliosDerivadoHijo() {
	return foliosDerivadoHijo;
}

public void setFoliosDerivadoHijo(List foliosDerivadoHijo) {
	this.foliosDerivadoHijo = foliosDerivadoHijo;
}

	private List foliosDerHijosTmp;
	
	public List getFoliosDerHijosTmp() {
		return foliosDerHijosTmp;
	}

	public void setFoliosDerHijosTmp(List foliosDerHijosTmp) {
		this.foliosDerHijosTmp = foliosDerHijosTmp;
	}
 /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  metodos accesores para los atributos testamento,turnoTestamento y salvedad
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
    public Turno getTurnoTestamento() {
        return turnoTestamento;
    }

    public void setTurnoTestamento(Turno turnoTestamento) {
        this.turnoTestamento = turnoTestamento;
    }

    public Testamento getTestamento() {
        return testamento;
    }

    public void setTestamento(Testamento testamento) {
        this.testamento = testamento;
    }

    public Map getSalvedad() {
        return salvedad;
    }

    public void setSalvedad(Map salvedad) {
        this.salvedad = salvedad;
    }

}
