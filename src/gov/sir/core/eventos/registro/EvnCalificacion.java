package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;

import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;
import java.util.Hashtable;
import org.auriga.core.modelo.transferObjects.Estacion;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Oficio;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author jfrias,mmunoz
 *
 */
public class EvnCalificacion extends EvnSIR {

	public static final String ENVIAR_CORRECCION="CORRECCION_ENCABEZADO";
	public static final String AVANZAR="AVANZAR";
        public static final String AVANZAR_REVISOR = "AVANZAR_REVISOR";
	public static final String AVANZAR_DESBLOQUEAR="AVANZAR_DESBLOQUEAR";
	public static final String GRABAR_ANOTACIONES_TEMPORALMENTE="GRABAR_ANOTACIONES_TEMPORALMENTE";
	public static final String CANCELAR_ANOTACION="CANCELAR_ANOTACION";
	public static final String TOMAR_TURNO="TOMAR_TURNO";
        public static final String OBTENCION_BLOQUEO_REPR ="OBTENCION_BLOQUEO_REPR";
	public static final String VALIDAR_TURNO_PARA_CALIFICACION="VALIDAR_TURNO_PARA_CALIFICACION";
	public static final String DESASOCIAR_FOLIOS="DESASOCIAR_FOLIOS";
	public static final String CONSULTA="CONSULTA";
	public static final String CREAR_FOLIO_ENGLOBE="CREAR_FOLIO_ENGLOBE";
	public static final String SEGREAR_MASIVO = "SEGREAR_MASIVO";
	public static final String CONSULTAR_SEG_ENG_EXISTENTES ="CONSULTAR_SEG_ENG_EXISTENTES";
	public static final String CONSULTAR_ULTIMOS_PROPIETARIOS ="CONSULTAR_ULTIMOS_PROPIETARIOS";
        public static final String VISUALIZAR_PDF ="VISUALIZAR_PDF";
	public static final String WF_CONFIRMAR_CORRECCION="CONFIRMAR";
	public static final String WF_NEGAR_CORRECCION="NEGAR";
	public static final String WF_NEGAR_CORRECCION_ENCABEZADO="NEGAR_CORRECCION_ENCABEZADO";
	public static final String WF_CONFIRMAR_CALIFICACION="OK";
	public static final String WF_CORRECCCION="CORRECCION";
	public static final String WF_DIGITACION="DIGITACION";
	public static final String WF_DEVOLUCION="DEVOLUCION";
	public static final String WF_INSCRIPCION_PARCIAL="INSCRIPCION_PARCIAL";
	public static final String WF_MICROFILMACION="MICROFILMACION";
	public static final String WF_ESPECIALIZADO="ESPECIALIZADO";
	public static final String WF_ANTIGUO_SISTEMA="ANTIGUO_SISTEMA";
	public static final String WF_CONFRONTACION ="CONFRONTACION_CORRECTIVA";
	public static final String WF_ENVIAR_CORRECCION_ENCABEZADO="ERROR_ENCABEZADO";

	public static final String WF_FIRMA_REGISTRO_CONFIRMAR="CONFIRMAR";
	public static final String WF_MESA_CONTROL_CONFIRMAR="CONFIRMAR";
	public static final String WF_MESA_CONTROL_CALIFICACION="CALIFICACION";
	public static final String WF_ENTREGA_CONFIRMAR="CONFIRMAR";
	public static final String WF_CERTIFICADOS_ENTREGA_OK = "OK";



	public static final String VALIDAR_APROBAR_CALIFICACION="VALIDAR_APROBAR_CALIFICACION";
	public static final String APROBAR_MAYOR_VALOR = "APROBAR_MAYOR_VALOR";
	public static final String REGISTRAR_PAGO_EXCESO = "REGISTRAR_PAGO_EXCESO";
	public static final String IMPRIMIR_FORMULARIO_CALIFICACION = "IMPRIMIR_FORMULARIO_CALIFICACION";
	public static final String IMPRIMIR_FOLIO_TEMPORAL = "IMPRIMIR_FOLIO_TEMPORAL";
	public static final String AVANZAR_FIRMA = "AVANZAR_FIRMA";
	public static final String DEVOLVER_FIRMA = "DEVOLVER_FIRMA";
	public static final String AVANZAR_ENTREGA = "AVANZAR_ENTREGA";
        //Se crea un nuevo evento para requerimiento de tramite suspension
        public static final String AVANZAR_TRAMITE_SUSP = "AVANZAR_TRAMITE_SUSP";
	public static final String OBTENER_BLOQUEO_FOLIOS = "OBTENER_BLOQUEO_FOLIOS";
	public static final String CERRAR_FOLIO = "CERRAR_FOLIO";
	public static final String CERRAR_FOLIOS = "CERRAR_FOLIOS";
	public static final String ELIMINAR_ANOTACION = "ELIMINAR_ANOTACION";
	
	public static final String AVANZAR_ANTIGUO_SISTEMA = "AVANZAR_ANTIGUO_SISTEMA";
	public static final String AVANZAR_ESPECIALIZADO = "AVANZAR_ESPECIALIZADO";


	public static final String DEVOLVER="DEVOLVER";
	
	public static final String INSCRIPCION_PARCIAL="INSCRIPCION_PARCIAL";


	/**
	* Constante para delegar el turno hacia CONFRONTACION
	*/
	public static final String CONFRONTACION ="CONFRONTACION";


	public static final String ACTUALIZAR_FOLIO_DIRECCION = "ACTUALIZAR_FOLIO_DIRECCION";
	public static final String ACTUALIZAR_FOLIO_COD_CATASTRAL = "ACTUALIZAR_FOLIO_COD_CATASTRAL";
    /**
     * @author: Cesar Ramirez
     * @change: 1245.HABILITAR.TIPO.PREDIO
     * Se agrega una nueva constante para actualizar el tipo del predio.
     **/
    public static final String ACTUALIZAR_FOLIO_TIPO_PREDIO = "ACTUALIZAR_FOLIO_TIPO_PREDIO";
	public static final String ACTUALIZAR_FOLIO_CABIDA_LINDEROS = "ACTUALIZAR_FOLIO_CABIDA_LINDEROS";
	public static final String AVANZAR_CORRECCIONES = "AVANZAR_CORRECCIONES";
	public static final String AGREGAR_CIUDADANO_ANOTACION = "AGREGAR_CIUDADANO_ANOTACION";
	public static final String AGREGAR_FOLIO_DIRECCION = "AGREGAR_FOLIO_DIRECCION";

	public static final String PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION = "PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION";
	
	/**********************************************/
	/*        ELIMINACION DE SAS                  */
	/**********************************************/
	public static final String NEGAR_CORRECCION_ENCABEZADO = "NEGAR_CORRECCION_ENCABEZADO";
	public static final String REALIZAR_CORRECCION_ENCABEZADO = "REALIZAR_CORRECCION_ENCABEZADO";
	public static final String ELIMINAR_FOLIO_DIRECCION = "ELIMINAR_FOLIO_DIRECCION";
	//daniel SIR-61
	public static final String DEVOLVER_FIRMA_TESTAMENTO = "DEVOLVER_FIRMA_TESTAMENTO";
        

	private String respuestaWf;
	private int tipoAvance;
	private Turno turno;
	private SolicitudRegistro solicitud;
	private Fase fase;
	private Folio folio;
	private List anotaciones;
	private List AnotacionesTem; 
	/**Lista de anotaciones agregadas de un folio. Por cada una que se agregue, se muestra
	 * el listado de anotaciones en la parte superior de la patanlla de creacion de anotaciones*/
	private List anotacionesAgregadas = null;
        private List listaRespuesta = null;
	private List foliosDerivados;
	//private Usuario usuario;
	private Anotacion anotacion;
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	private List foliosFuente;
	private Hashtable tabla;
        private gov.sir.core.negocio.modelo.Usuario usuarioSir;
	//private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private String idCirculo = null;
        private int isFirma = 1;
        private boolean isReproduccion = false;
        private List listReproduccion = new ArrayList();
        private int ImprimirYN  = 0;
	private int numeroCopias = 1;
	private String impresoraSeleccionada;
	private String nuevoLindero;
	private String nuevoCodCatastral;
	private List nuevasDirecciones;

        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO Método
         * Se crea un objeto TipoPredio para el evento de guardar direcciones.
         **/
        private gov.sir.core.negocio.modelo.TipoPredio tipoPredio;
        
	private List SolicitudFoliosInscripcionParcial;

        private boolean eliminarCambiosAnotacionDefinitivaTemporalEnabled;


	/** Identificador unico de usuario*/
	private String UID;
    private LiquidacionTurnoRegistro liquidacion;

    /**
    * Motivo de delegación del turno a corrección de encabezado o a confrontación correctiva.
    */
    private String motivoDevolucion;

    private String idMatricula;
	private String razonCierre;

	private int numeroImpresiones;
	
	private List anotacionCiudadanos;
	
	private List listaCompletaCiudadanos;

	private boolean generarNextOrden = false;
	
	private Rol rol = null;
	
	private Proceso proceso = null;
	
	private List dirTemporales = null;

	private List lstFolios = null;

    /* JALCAZAR Requerimiento MANTIS  0002225
     * se agrega la variable List turnos para
     * mantener los turnos en el proceso masivo de entrega
     */
        private List turnos = null;
    
 





	public boolean isGenerarNextOrden() {
		return generarNextOrden;
	}

	public void setGenerarNextOrden(boolean generarNextOrden) {
		this.generarNextOrden = generarNextOrden;
	}

	/**
	 * @param 
	 * @param tipoEvento
	 */
	public EvnCalificacion(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
		
	}
	
	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnCalificacion(Usuario usuario,  String tipoEvento, Turno turno , gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.usuarioNeg = usuarioNeg;
	}
	
	 public EvnCalificacion( String tipoAccion ) {
         super( null , tipoAccion);
	 }

	/**
	 * Usado para hacer segregaciones masivas
	 * @param folio Folio del que van a surgir las segregaciones masivas
	 * @param turno Turno con el que se está trabajando
	 * @param anotaciones Lista de anotaciones a heredar
	 * @param foliosD Folios derivados
	 * @param usuario Usuario que va a segregar
	 * @param usuarioSIR Usuario del negocio
	 */
	public EvnCalificacion(Folio folio, Turno turno, List anotaciones, List foliosD,Usuario usuario,gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,EvnCalificacion.SEGREAR_MASIVO);
		this.folio = folio;
		this.anotaciones = anotaciones;
		this.foliosDerivados = foliosD;
		this.usuarioNeg = usuarioNeg;
		this.turno = turno;
	}

	/**
	 * Se utiliza para consultar un folio
	 * @param usuario Usuario que está solicitando el folio
	 * @param tipoEvento Tipo de evento que se genera
	 * @param folio Folio a consultar.  Sólo es necesario que tenga la matrícula del folio
	 * @param usuarioNeg  Usuario del negocio
	 */
	public EvnCalificacion(Usuario usuario, String tipoEvento, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioNeg){
		super(usuario, tipoEvento);
		this.folio=folio;
		this.usuarioNeg=usuarioNeg;
	}

	public EvnCalificacion(Usuario usuario , Folio folio, List anotaciones, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, GRABAR_ANOTACIONES_TEMPORALMENTE);
		this.folio=folio;
		this.anotaciones=anotaciones;
		this.usuarioNeg=usuarioNeg;
	}

        public int getIsFirma() {
            return isFirma;
        }

        public void setIsFirma(int isFirma) {
            this.isFirma = isFirma;
        }
        public int getImprimirYN() {
              return ImprimirYN;
          }

        public void setImprimirYN(int ImprimirYN) {
            this.ImprimirYN = ImprimirYN;
        }
	public EvnCalificacion(Usuario usuario , Folio folio, Anotacion anotacion, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,CANCELAR_ANOTACION);
		this.folio=folio;
		this.anotacion=anotacion;
		this.usuarioNeg=usuarioNeg;
	}

	public EvnCalificacion(Usuario usuario , Folio folio, Anotacion anotacion, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String respuesta) {
		super(usuario,respuesta);
		this.folio=folio;
		this.anotacion=anotacion;
		this.usuarioNeg=usuarioNeg;
	}

	public EvnCalificacion(Usuario usuario , Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,TOMAR_TURNO);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
	}
	
	public EvnCalificacion(String tipoEvento, Usuario usuario , Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,tipoEvento);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
	}	


	public EvnCalificacion(List folios, Usuario usuario, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String tipoEvento) {
		super(usuario,tipoEvento);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
		this.foliosFuente = folios;
	}


	public EvnCalificacion(Usuario usuario, List foliosFuente,Folio folio, Hashtable tabla,gov.sir.core.negocio.modelo.Usuario usuarioNeg){
		super(usuario, CREAR_FOLIO_ENGLOBE);
		this.foliosFuente=foliosFuente;
		this.folio=folio;
		this.tabla=tabla;
		this.usuarioNeg=usuarioNeg;

	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 */
	public EvnCalificacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, Fase fase,  String respuestaWf, int tipoAvance) {
		super(usuario,AVANZAR);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
		this.fase=fase;
		this.tipoAvance = tipoAvance;
		this.respuestaWf=respuestaWf;
	}
	
	
	
	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 */
	public EvnCalificacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, Fase fase,  String respuestaWf, int tipoAvance, String accion) {
		super(usuario,accion);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
		this.fase=fase;
		this.tipoAvance = tipoAvance;
		this.respuestaWf=respuestaWf;
	}
	

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 */
	public EvnCalificacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, Fase fase,  String respuestaWf,  String tipoEvento) {
		super(usuario,tipoEvento);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
		this.fase=fase;
		this.respuestaWf=respuestaWf;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 */
	public EvnCalificacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, SolicitudRegistro solicitud, Fase fase, String respuestaWf, int tipoAvance) {
		super(usuario,WF_CONFIRMAR_CORRECCION);
		this.usuarioNeg=usuarioNeg;
		this.solicitud=solicitud;
		this.fase=fase;
		this.respuestaWf=respuestaWf;
		this.tipoAvance = tipoAvance;
		this.turno = turno;
	}
	
	
	
	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 */
	public EvnCalificacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, SolicitudRegistro solicitud, Fase fase, String respuestaWf, int tipoAvance, String accion) {
		super(usuario,accion);
		this.usuarioNeg=usuarioNeg;
		this.solicitud=solicitud;
		this.fase=fase;
		this.respuestaWf=respuestaWf;
		this.tipoAvance = tipoAvance;
		this.turno = turno;
	}
	

	/**
	 * Usado para avanzar el turno y desbloquear los folios que tenia bloqueados
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param usuarioNeg
	 * @param respuestaWf
	 * @param idCirculo es el identificadore del circulo registral, se usa cuando se quiere imprimir
	 * el Formulario de Calificacion.
	 */
	public EvnCalificacion(Usuario usuario, Turno turno, Fase fase,
		gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf,
		String idCirculo)
		{
				super(usuario,AVANZAR_DESBLOQUEAR);
				this.turno=turno;
				this.fase=fase;
				this.usuarioNeg=usuarioNeg;
				this.respuestaWf=respuestaWf;
				this.idCirculo = idCirculo;
		}



	/**
	 * Usado para avanzar el turno y desbloquear los folios que tenia bloqueados
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param usuarioNeg
	 * @param respuestaWf
	 **/
	public EvnCalificacion(Usuario usuario, Turno turno, Fase fase,
		gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf)
		{
				super(usuario,AVANZAR);
				this.turno=turno;
				this.fase=fase;
				this.usuarioNeg=usuarioNeg;
				this.respuestaWf=respuestaWf;

		}

	/**
	 * Usado para avanzar el turno y desbloquear los folios que tenia bloqueados
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param usuarioNeg
	 * @param respuestaWf
	 * @param idCirculo es el identificadore del circulo registral, se usa cuando se quiere imprimir
	 * el Formulario de Calificacion.
	 */
	public EvnCalificacion(Usuario usuario, Turno turno, Fase fase,
		gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf,
		int tipoAvanc, String idCirculo)
		{
				super(usuario,AVANZAR_DESBLOQUEAR);
				this.tipoAvance=tipoAvanc;
				this.turno=turno;
				this.fase=fase;
				this.usuarioNeg=usuarioNeg;
				this.respuestaWf=respuestaWf;
				this.idCirculo = idCirculo;
		}


	/**
	 * Usado para avanzar el turno y desbloquear los folios que tenia bloqueados
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param usuarioNeg
	 * @param respuestaWf
	 * @param idCirculo es el identificadore del circulo registral, se usa cuando se quiere imprimir
	 * el Formulario de Calificacion.
	 */
	public EvnCalificacion(Usuario usuario, Turno turno,
		gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf)
		{
				super(usuario,AVANZAR_DESBLOQUEAR);

		}


	     /**
		 * Usado para avanzar el turno hacia la fase de confrontación.
		 * @param usuario
		 * @param turno
		 * @param fase
		 * @param usuarioNeg
		 * @param respuestaWf
		 * @param accion
		 * @param idCirculo
		 */
		public EvnCalificacion(Usuario usuario, Turno turno, Fase fase,
			gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf, String accion, String idCirculo)
			{
				super(usuario,accion);
				this.turno = turno;
				this.fase = fase;
				this.usuarioNeg = usuarioNeg;
				this.respuestaWf = respuestaWf;
				this.idCirculo = idCirculo;
			}



	/**
	* Usado para avanzar el turno hacia la fase de confrontación.
	* @param usuario
	* @param turno
	* @param fase
	* @param usuarioNeg
	* @param respuestaWf
	* @param accion
	* @param idCirculo
	* @param motivoDevolucion motivo delegación del turno hacia confrontacion o correccion de encabezado.
	*/
   public EvnCalificacion(Usuario usuario, Turno turno, Fase fase,
	   gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf, String accion, String idCirculo, String motivoDevolucion)
	   {
		   super(usuario,accion);
		   this.turno = turno;
		   this.fase = fase;
		   this.usuarioNeg = usuarioNeg;
		   this.respuestaWf = respuestaWf;
		   this.idCirculo = idCirculo;
		   this.motivoDevolucion = motivoDevolucion;
	   }


	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 * @param motivoDevolucion
	 * @param accion
	 */
	public EvnCalificacion(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, Fase fase,  String respuestaWf, int tipoAvance, String accion, String motivoDevolucion) {
		super(usuario,accion);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
		this.fase=fase;
		this.tipoAvance = tipoAvance;
		this.respuestaWf=respuestaWf;
		this.motivoDevolucion = motivoDevolucion;
	}
        
	/**
	 * @return
	 */
	public String getRespuestaWf() {
		return respuestaWf;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @param fase
	 */
	public void setFase(Fase fase) {
		this.fase= fase;
	}

	/**
	 * @return fase
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 * @return
	 */
	public List getAnotaciones() {
		return anotaciones;
	}
	
	public void setAnotaciones(List ltAnotaciones) {
		this.anotaciones = ltAnotaciones;
	}
    

	public void setAnotacionesTem(List AnotacionesTem) {
		this.AnotacionesTem = AnotacionesTem;
	}
	
	public List getAnotacionesTem() {
		return this.AnotacionesTem;
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
	public List getFoliosDerivados() {
		return foliosDerivados;
	}


	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

	/**
	 * @return
	 */
	public Anotacion getAnotacion() {
		return anotacion;
	}

	/**
	 * @return
	 */
	public List getFoliosFuente() {
		return foliosFuente;
	}
	/**
	 * @return
	 */
	public Hashtable getTabla() {
		return tabla;
	}

        public boolean isIsReproduccion() {
            return isReproduccion;
        }

        public void setIsReproduccion(boolean isReproduccion) {
            this.isReproduccion = isReproduccion;
        }

        public List getListReproduccion() {
            return listReproduccion;
        }

        public void setListReproduccion(List listReproduccion) {
            this.listReproduccion = listReproduccion;
        }
	/**
	 * @return
	 */
	public SolicitudRegistro getSolicitud() {
		return solicitud;
	}

	/**
	 * @param registro
	 */
	public void setSolicitud(SolicitudRegistro registro) {
		solicitud = registro;
	}
	/**
	 * @return
	 */
	public int getTipoAvance() {
		return tipoAvance;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public String getIdCirculo() {
		return idCirculo;
	}

	/**
	 * @param string
	 */
	public void setIdCirculo(String string) {
		idCirculo = string;
	}

	/**
	 * @return
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
     * @param liq
     */
    public void setLiquidacion(LiquidacionTurnoRegistro liq) {
        this.liquidacion=liq;

    }

    /**
     * @return liquidacion
     */
    public LiquidacionTurnoRegistro getLiquidacion() {
        return this.liquidacion;
    }

	/**
	 * @param usuarioNeg
	 */
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		this.usuarioNeg=usuarioNeg;
	}



	/**
	 * @return El motivo de delegación de un turno a corrección de encabezado o confrontación
	 * correctiva.
	 */
	public String getMotivoDevolucion() {
		return motivoDevolucion;
	}

	/**
	 * @param motivo El motivo de delegación de un turno a corrección de encabezado o confrontación
	 * correctiva.
	 */
	public void setMotivoDevolucion(String motivo) {
		motivoDevolucion = motivo;
	}

	/**
	 * @param idMatricula
	 */
	public void setIdMatricula(String idMatricula) {
		this.idMatricula= idMatricula;
	}

	/**
	 * @param razonCierre
	 */
	public void setRazonCierre(String razonCierre) {
		this.razonCierre= razonCierre;

	}

	/**
	 * @return Returns the idMatricula.
	 */
	public String getIdMatricula() {
		return idMatricula;
	}
	/**
	 * @return Returns the razonCierre.
	 */
	public String getRazonCierre() {
		return razonCierre;
	}

	/**
	 * @param confirmar
	 */
	public void setRespuestaWF(String confirmar) {
		this.respuestaWf=confirmar;
	}
	/**
	 * @return
	 */
	public int getNumeroCopias() {
		return numeroCopias;
	}

	/**
	 * @param i
	 */
	public void setNumeroCopias(int i) {
		numeroCopias = i;
	}

	/**
	 * @return
	 */
	public String getNuevoLindero() {
		return nuevoLindero;
	}

	/**
	 * @param string
	 */
	public void setNuevoLindero(String string) {
		nuevoLindero = string;
	}

        /**
	 * @return
	 */
	public String getNuevoCodCatastral() {
		return nuevoCodCatastral;
	}

	/**
	 * @param string
	 */
	public void setNuevoCodCatastral(String string) {
		nuevoCodCatastral = string;
	}
        
        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO Método
         * Métodos get y set del objeto TipoPredio
         **/
        public gov.sir.core.negocio.modelo.TipoPredio getNuevoTipoPredio() {
            return tipoPredio;
        }

	public void setNuevoTipoPredio(gov.sir.core.negocio.modelo.TipoPredio string) {
            tipoPredio = string;
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
	public List getNuevasDirecciones() {
		return nuevasDirecciones;
	}

	/**
	 * @param list
	 */
	public void setNuevasDirecciones(List list) {
		nuevasDirecciones = list;
	}

	/**
	 * @return
	 */
	public String getImpresoraSeleccionada() {
		return impresoraSeleccionada;
	}

	/**
	 * @param string
	 */
	public void setImpresoraSeleccionada(String string) {
		impresoraSeleccionada = string;
	}

	public void setTipoAvance(int tipoAvance) {
		this.tipoAvance = tipoAvance;
	}

	public int getNumeroImpresiones() {
		return numeroImpresiones;
	}

	public boolean isEliminarCambiosAnotacionDefinitivaTemporalEnabled() {
		return eliminarCambiosAnotacionDefinitivaTemporalEnabled;
	}

	public void setNumeroImpresiones(int numeroImpresiones) {
		this.numeroImpresiones = numeroImpresiones;
	}

	public void setEliminarCambiosAnotacionDefinitivaTemporalEnabled(boolean
		 eliminarCambiosAnotacionDefinitivaTemporalEnabled) {
		this.eliminarCambiosAnotacionDefinitivaTemporalEnabled =
			 eliminarCambiosAnotacionDefinitivaTemporalEnabled;
	}

	public List getSolicitudFoliosInscripcionParcial() {
		return SolicitudFoliosInscripcionParcial;
	}

	public void setSolicitudFoliosInscripcionParcial(
			List solicitudFoliosInscripcionParcial) {
		SolicitudFoliosInscripcionParcial = solicitudFoliosInscripcionParcial;
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

	public List getAnotacionesAgregadas() {
		return anotacionesAgregadas;
	}

	public void setAnotacionesAgregadas(List anotacionesAgregadas) {
		this.anotacionesAgregadas = anotacionesAgregadas;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List getDirTemporales() {
		return dirTemporales;
	}

	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales = dirTemporales;
	}

	public List getLstFolios() {
		return lstFolios;
	}

	public void setLstFolios(List lstFolios) {
		this.lstFolios = lstFolios;
	}

     /* JALCAZAR Requerimiento MANTIS  0002225
     * se agrega get y set variable List turnos para
     * mantener los turnos en el proceso masivo de entrega
     */
    public List getTurnos() {
        return turnos;
    }

    public void setTurnos(List turnos) {
        this.turnos = turnos;
    }

    public gov.sir.core.negocio.modelo.Usuario getUsuarioSir() {
        return usuarioSir;
    }

    public void setUsuarioSir(gov.sir.core.negocio.modelo.Usuario usuarioSir) {
        this.usuarioSir = usuarioSir;
    }     

}
