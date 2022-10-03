package gov.sir.core.eventos.fotocopia;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;
import gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item;

/**
 * Envío de solicitudes con respecto al proceso de Fotocopias, de la capa web
 * a la capa de negocio.
 * @author dlopez
 */
public class EvnRespFotocopia extends EvnSIRRespuesta {

  public static final String
	  DOCUMENTOSASOCIADOSADD_EVENTRESP_OK                   = "DOCUMENTOSASOCIADOSADD_EVENTRESP_OK";

  public static final String
		DOCUMENTOSASOCIADOSDEL_EVENTRESP_OK                  = "DOCUMENTOSASOCIADOSDEL_EVENTRESP_OK";

  public static final String
	  PAGOFOTOCOPIASZEROVALUE_EVENTRESP_OK                  = "PAGOFOTOCOPIASZEROVALUE_EVENTRESP_OK";

  public static final String
	  VERIFICAR_DOCUMENTOS_ASOCIADOS_OK                     = "VERIFICAR_DOCUMENTOS_ASOCIADOS_OK";

  public static final String
	  NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENTRESP_OK  = EvnFotocopia.NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENT + "RESP_OK";


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
	public final static String FOTOCOPIAR_DOCUMENTO = "FOTOCOPIAR_DOCUMENTO";


	/**
	 * Constante que identifica que se desean entregar las fotocopias.
	 */
	public final static String ENTREGAR_FOTOCOPIAS = "ENTREGAR_FOTOCOPIAS";


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
	* Constante que identifica que se desea procesar el pago
	*/
	public final static String PROCESAR_PAGO = "PROCESAR_PAGO";


	/**
	* Constante que identifica que se desea terminar un turno por falta de pago.
	*/
	public final static String TERMINAR_TURNO = "TERMINAR_TURNO";


	private Turno turno;
	private Fase fase;
	private Estacion estacion = null;
	private SolicitudFotocopia solicitudFotocopia;
	private LiquidacionTurnoFotocopia liquidacionFotocopia;
	private String respuestaWF = "";
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

        private  DocumentoAsociado_Item documentoFotocopia;


        public DocumentoAsociado_Item getDocumentoFotocopia() {
          return this.documentoFotocopia;
        }



	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespFotocopia(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}


	/**
	 * @param usuario
	 * @param solicitud
	 * @param tipoAccion
	 */
	public EvnRespFotocopia(Usuario usuario, SolicitudFotocopia solicitud, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudFotocopia = solicitud;
	}


        // Super.Propagate
        public EvnRespFotocopia(Usuario usuario, DocumentoAsociado_Item documentoFotocopia, String tipoAccion) {
                super(usuario, tipoAccion);
                this.documentoFotocopia = documentoFotocopia;
        }


	/**
	 * @param usuario
	 * @param solicitud
	 * @param liquidacion
	 * @param tipoAccion
	 */
	public EvnRespFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, SolicitudFotocopia solicitud, LiquidacionTurnoFotocopia liquidacion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.usuarioSIR = usuarioSIR;
		this.solicitudFotocopia = solicitud;
		this.liquidacionFotocopia = liquidacion;
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
	public EvnRespFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, SolicitudFotocopia solicitud, String tipoAccion, String respWF) {
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
	public EvnRespFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respWF;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param estacion
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRespFotocopia(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
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
	public EvnRespFotocopia(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.estacion = estacion;
	}

        public EvnRespFotocopia(Usuario usuario, Turno turno, Fase fase, String tipoAccion) {
                super(usuario, tipoAccion);
                this.turno = turno;
                this.fase = fase;
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
}
