package gov.sir.core.eventos.fotocopia;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envío de solicitudes con respecto al proceso de Fotocopias, de la capa web
 * a la capa de negocio.
 * @author dlopez
 */
public class EvnFotocopia extends EvnSIR {

	public static final String DOCUMENTOSASOCIADOSADD_EVENT = "DOCUMENTOSASOCIADOSADD_EVENT";
	public static final String DOCUMENTOSASOCIADOSDEL_EVENT = "DOCUMENTOSASOCIADOSDEL_EVENT";
	public static final String VERIFICAR_DOCUMENTOS_ASOCIADOS = "VERIFICAR_DOCUMENTOS_ASOCIADOS";
	public static final String PAGO_FOTOCOPIAS_ZERO_VALUE = "PAGO_FOTOCOPIAS_ZERO_VALUE";

   public static final String NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENT = "NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENT";


	/**
	 * Constante que identifica que se desea CREAR una solicitud.
	 */
	public final static String CREAR_SOLICITUD = "CREAR_SOLICITUD";



	/**
	 * Constante que identifica que se desea avanzar por Liquidación de Fotocopias.
	 */
	public final static String LIQUIDACION_FOTOCOPIAS = "FOT_LIQUIDACION";


	/**
	 * Constante que identifica que se desea realizar el pago de las fotocopias.
	 */
	public final static String PAGO_FOTOCOPIAS = "FOT_PAGO";


	/**
	 * Constante que identifica que se desean tomar las fotocopias.
	 */
	public final static String FOTOCOPIAR = "FOT_FOTOCOPIA";


	/**
	 * Constante que identifica que se desean entregar las fotocopias.
	 */
	public final static String ENTREGAR = "FOT_ENTREGAR";


	// Constantes de respuestas del WorkFlow

	/**
	* Constante que identifica que se desea avanzar por CONFIRMAR
	*/
	public final static String CONFIRMAR = "CONFIRMAR";


	/**
	* Constante que identifica que se desea avanzar por NEGAR
	*/
	public final static String NEGAR = "NEGAR";


	/**
	* Constante que identifica que se desea aceptar el procesamiento del pago
	*/
	public final static String ACEPTAR_PAGO = "ACEPTAR_PAGO";


	/**
	* Constante que identifica que se desea cancelar el procesamiento del pago
	*/
	public final static String CANCELAR_PAGO = "CANCELAR_PAGO";


	/**
	* Constante que identifica que se desea procesar el pago
	*/
	public final static String PROCESAR_PAGO = "PROCESAR_PAGO";

        private DocumentoAsociado_Item documentoFotocopia;


	private Turno turno;
	private Fase fase;
	private Estacion estacion = null;
	private SolicitudFotocopia solicitudFotocopia;
	private LiquidacionTurnoFotocopia liquidacionFotocopia;
	private String respuestaWF = "";
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
        /** @author : HGOMEZ
        *** @change : Se declara la variable esExento para identificar 
        *** cuando una fotocopia es exenta.
        *** Caso Mantis : 12288
        */
        private String esExento;

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnFotocopia(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}

        /**
         * @param usuario
         * @param turno
         * @param tipoAccion
         */
        public EvnFotocopia(Usuario usuario, Turno turno, String tipoAccion, String respuestaWF ) {
                super(usuario, tipoAccion);
                this.turno = turno;
                this.respuestaWF = respuestaWF;
        }


        // ** ** //
        public EvnFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respuestaWF ) {
                super(usuario, tipoAccion);
                this.fase = fase;
                this.turno = turno;
                this.respuestaWF = respuestaWF;
                this.usuarioSIR = usuarioSIR;
        }


        public EvnFotocopia(Usuario usuario, DocumentoAsociado_Item documentoFotocopia, String tipoAccion) {
                super(usuario, tipoAccion);
                this. documentoFotocopia = documentoFotocopia;
        }

	/**
	 * @param usuario
	 * @param solicitud
	 * @param tipoAccion
	 */
	public EvnFotocopia(Usuario usuario, SolicitudFotocopia solicitud, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudFotocopia = solicitud;
	}

	/**
	 * @param usuario
	 * @param solicitud
	 * @param liquidacion
	 * @param tipoAccion
	 */
	public EvnFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, SolicitudFotocopia solicitud, /*LiquidacionTurnoFotocopia liquidacion,*/ String tipoAccion) {
		super(usuario, tipoAccion);
		this.usuarioSIR = usuarioSIR;
		this.solicitudFotocopia = solicitud;
		//this.liquidacionFotocopia = liquidacion;
	}

        /**
         * @param usuario
         * @param solicitud
         * @param liquidacion
         * @param tipoAccion
         */
        public EvnFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, /*LiquidacionTurnoFotocopia liquidacion,*/ String tipoAccion) {
                super(usuario, tipoAccion);
                this.usuarioSIR = usuarioSIR;
                this.turno = turno;
                //this.liquidacionFotocopia = liquidacion;
        }


	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param solicitud
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, SolicitudFotocopia solicitud, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.solicitudFotocopia = solicitud;
		this.respuestaWF = respWF;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param tipoAccion
	 * @param respWF
	 */

        /*
	public EvnFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respWF;
		this.usuarioSIR = usuarioSIR;
	}
       */

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param estacion
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnFotocopia(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.estacion = estacion;
		this.respuestaWF = respWF;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param estacion
	 * @param tipoAccion
	 */
	public EvnFotocopia(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.estacion = estacion;
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
	public Estacion getEstacion() {
		return estacion;
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
	public SolicitudFotocopia getSolicitudFotocopia() {
		return this.solicitudFotocopia;
	}

	/**
	 * @return
	 */
	public LiquidacionTurnoFotocopia getLiquidacionFotocopia() {
		return this.liquidacionFotocopia;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}


        public DocumentoAsociado_Item getDocumentoFotocopia() {
          return this.documentoFotocopia;
        }

    public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
        this.usuarioSIR = usuarioSIR;
    }
    
    /** @author : HGOMEZ
    *** @change : Métodos para asignar y obtener el valor de la variable
    *** esExento.
    *** Caso Mantis : 12288
    */
    public String getEsExento() {
        return esExento;
    }

    public void setEsExento(String esExento) {
        this.esExento = esExento;
    }

}
