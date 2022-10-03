package gov.sir.core.eventos.fotocopia;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envío de solicitudes con respecto al proceso de Fotocopias, de la capa web
 * a la capa de negocio.
 * @author dlopez
 */
public class EvnPagoFotocopia extends EvnSIR {



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




	private Turno turno;
	private Fase fase;
	private Estacion estacion = null;
	private SolicitudFotocopia solicitudFotocopia;
	private LiquidacionTurnoFotocopia liquidacionFotocopia;
	private String respuestaWF = "";
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;


        public EvnPagoFotocopia( Usuario usuario, Turno turno, Fase fase, String tipoAccion, String respuestaWf ) {
          super( usuario, tipoAccion );
          this.turno = turno;
          this.fase = fase;
          this.respuestaWF = respuestaWf;
        }

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnPagoFotocopia(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}


	/**
	 * @param usuario
	 * @param solicitud
	 * @param tipoAccion
	 */
	public EvnPagoFotocopia(Usuario usuario, SolicitudFotocopia solicitud, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudFotocopia = solicitud;
	}

	/**
	 * @param usuario
	 * @param solicitud
	 * @param liquidacion
	 * @param tipoAccion
	 */
	public EvnPagoFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, SolicitudFotocopia solicitud, LiquidacionTurnoFotocopia liquidacion, String tipoAccion) {
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
	public EvnPagoFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, SolicitudFotocopia solicitud, String tipoAccion, String respWF) {
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
	public EvnPagoFotocopia(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF) {
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
	public EvnPagoFotocopia(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
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
	public EvnPagoFotocopia(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
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
}
