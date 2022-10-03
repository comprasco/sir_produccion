package gov.sir.core.eventos.pagomayorvalor;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes de respuesta con respecto al proceso de pago por mayor valor, de la capa de negocio a la capa web.
 * @author ppabon
 */
public class EvnRespPagoMayorValor extends EvnSIRRespuesta {
	/**
	 * Constante que identifica que se aprobar la custodia
	 */
	public final static String CUSTODIA = "CUSTODIA";

	/**
	 * Constante que identifica que se desea notificar al ciudadano
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";

	/**
	 * Constante que identifica que se desea registrar el pago
	 */
	public final static String REGISTRAR_PAGO = "REGISTRAR_PAGO";

	/**
	 * Constante que identifica que se desea notificar al funcionario
	 */
	public final static String NOTIFICAR_FUNCIONARIO = "NOTIFICAR_FUNCIONARIO";
	private Turno turno;
	private Fase fase;
	private SolicitudCorreccion solicitudCorreccion;
	private LlaveBloqueo llaveBloqueo;

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespPagoMayorValor(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param llaveBloqueo
	 * @param tipoAccion
	 */
	public EvnRespPagoMayorValor(Usuario usuario, Turno turno, LlaveBloqueo llaveBloqueo, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.llaveBloqueo = llaveBloqueo;
	}

	/**
	 * @param usuario
	 * @param solicitudCorreccion
	 * @param tipoAccion
	 */
	public EvnRespPagoMayorValor(Usuario usuario, SolicitudCorreccion solicitudCorreccion, String tipoAccion) {
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
	public EvnRespPagoMayorValor(Usuario usuario, Turno turno, LlaveBloqueo llaveBloqueo, Fase fase, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.llaveBloqueo = llaveBloqueo;
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
	public SolicitudCorreccion getSolicitudCorreccion() {
		return solicitudCorreccion;
	}

	/**
	 * @param correccion
	 */
	public void setSolicitudCorreccion(SolicitudCorreccion correccion) {
		solicitudCorreccion = correccion;
	}
}
