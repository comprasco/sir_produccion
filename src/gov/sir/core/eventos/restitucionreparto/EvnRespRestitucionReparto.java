package gov.sir.core.eventos.restitucionreparto;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes de respuesta con respecto al proceso de restitución de reparto notarial, de la capa de negocio a la capa web.
 * @author ppabon
 */
public class EvnRespRestitucionReparto extends EvnSIRRespuesta {

	/**
	 * Constante que identifica que se desea CREAR una solicitud
	 */
	public final static String CREAR_SOLICITUD = "CREAR_SOLICITUD";
	
	/**
	 * Constante que identifica que se desea CREAR una solicitud
	 */
	public final static String CONSULTAR_MINUTA = "CONSULTAR_MINUTA";
	
	/**
	 * Constante que identifica que se desea notificar al funcionario
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";	
	
	/**
	 * Constante que identifica que se desea retornar la rspuesta del análisis de reparto
	 */
	public final static String ANALISIS_REPARTO = "ANALISIS_REPARTO";	
	
	/** Constante que identifica la Hashtable que tiene como llave el # del turno y como objeto la minuta*/
	public static final String TABLA_RESTITUCION_REPARTO = "TABLA_RESTITUCION_REPARTO";

	/** Constante que identifica la acción de consultar los turnos para analisis restitucion notarial*/
	public static final String CONSULTAR_TURNOS_ANALISIS_RESTITUCION = "CONSULTAR_TURNOS_ANALISIS_RESTITUCION";
	
	
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
	private Estacion estacion = null;
	private SolicitudRestitucionReparto solicitudRestitucionReparto;
	private Minuta minuta = null;
	private String respuestaWF = "";
	private List listaTurnos;
	
	

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespRestitucionReparto(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRestitucionReparto(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}
	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRestitucionReparto(Usuario usuario, Turno turno, Minuta minuta, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.minuta = minuta;
	}

	/**
	 * @param usuario
	 * @param solicitudRestitucionReparto
	 * @param tipoAccion
	 */
	public EvnRespRestitucionReparto(Usuario usuario, SolicitudRestitucionReparto solicitudRestitucionReparto, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudRestitucionReparto = solicitudRestitucionReparto;
	}

	/**
	 * @param usuario
	 * @param solicitudRestitucionReparto
	 * @param minuta
	 * @param tipoAccion
	 */
	public EvnRespRestitucionReparto(Usuario usuario, SolicitudRestitucionReparto solicitudRestitucionReparto, Minuta minuta, String tipoAccion) {
		super(usuario, tipoAccion);
		this.minuta = minuta;
		this.solicitudRestitucionReparto = solicitudRestitucionReparto;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRespRestitucionReparto(Usuario usuario, Turno turno, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
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
	public EvnRespRestitucionReparto(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
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
	public EvnRespRestitucionReparto(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
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
	public Minuta getMinuta() {
		return minuta;
	}

	/**
	 * @return
	 */
	public SolicitudRestitucionReparto getSolicitudRestitucionReparto() {
		return solicitudRestitucionReparto;
	}
	
	public List getListaTurnos() {
		return listaTurnos;
	}


	public void setListaTurnos(List listaTurnos) {
		this.listaTurnos = listaTurnos;
	}


}
