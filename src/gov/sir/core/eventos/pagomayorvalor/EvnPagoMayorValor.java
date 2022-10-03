package gov.sir.core.eventos.pagomayorvalor;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes con respecto al proceso de pago por mayor valor, de la capa web a la capa de negocio.
 * @author ppabon
 */
public class EvnPagoMayorValor extends EvnSIR {
	/**
	 * Constante que identifica que se aprobar la custodia
	 */
	public final static String CUSTODIA = "CUSTODIA";

	/**
	 * Constante que identifica que se desea notificar al funcionario
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";

	/**
	 * Constante que identifica que se desea resgistrar el pago
	 */
	public final static String REGISTRAR_PAGO = "REGISTRAR_PAGO";

	/**
	 * Constante que identifica que se desea notificar al funcionario
	 */
	public final static String NOTIFICAR_FUNCIONARIO = "NOTIFICAR_FUNCIONARIO";

	/*
	 * Constantes de respuestas del WorkFlow
	 */

	/**
	 * Constante que identifica que se desea avanzar por EXITO
	 */
	public final static String EXITO = "EXITO";

	/**
	 * Constante que identifica que se desea avanzar por FRACASO
	 */
	public final static String FRACASO = "FRACASO";
	private Turno turno;
	private Fase fase;
	private SolicitudCorreccion solicitudCorreccion;
	private Folio folio;
	private Nota nota;
	private int tipoAvance;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private String respuestaWF = ""; //Variable para guardar la respuesta al Work Flow.	

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnPagoMayorValor(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}

	/**
	 * @param usuario
	 * @param solicitudCorreccion
	 * @param tipoAccion
	 */
	public EvnPagoMayorValor(Usuario usuario, SolicitudCorreccion solicitudCorreccion, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudCorreccion = solicitudCorreccion;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnPagoMayorValor(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF, int tipoAvance) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respWF;
		this.usuarioSIR = usuarioSIR;
		this.tipoAvance = tipoAvance;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param nota
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnPagoMayorValor(Usuario usuario, Turno turno, Fase fase, Nota nota, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.nota = nota;
		this.respuestaWF = respWF;
	}

	/**
	 * @param usuario
	 * @param folio
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnPagoMayorValor(Usuario usuario, Folio folio, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.folio = folio;
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
	public SolicitudCorreccion getSolicitudCorreccion() {
		return solicitudCorreccion;
	}

	/**
	 * @param correccion
	 */
	public void setSolicitudCorreccion(SolicitudCorreccion correccion) {
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
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}
	/**
	 * @return
	 */
	public int getTipoAvance() {
		return tipoAvance;
	}

}
