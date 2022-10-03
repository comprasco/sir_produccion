package gov.sir.core.eventos.repartonotarial;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes de respuesta con respecto al proceso de reparto notarial de minutas, de la capa de negocio a la capa web.
 * @author ppabon
 */
public class EvnRespRepartoNotarial extends EvnSIRRespuesta {

	/**
	 * Constante que identifica que se desea CREAR una solicitud
	 */
	public final static String CREAR_SOLICITUD = "CREAR_SOLICITUD";
	
	/**
	 * Constante que identifica que se desea realizar el reparto de minutas.
	 */
	public final static String EJECUTAR_REPARTO = "EJECUTAR_REPARTO";
	
	/**
	 * Constante que identifica que se desea realizar el reparto de minutas.
	 */
	public final static String EJECUTAR_REPARTO_FAILED = "EJECUTAR_REPARTO_FAILED";	
	
	/**
	 * Constante que identifica que se desea notificar al funcionario
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";	
	
	/**
	 * Constante que identifica que se desea notificar al funcionario
	 */
	public final static String NOTIFICAR_CIUDADANO_MASIVA = "NOTIFICAR_CIUDADANO_MASIVA";	
	
	/**Constante para cargar los circulos notariales	 */
	public final static String CARGAR_CIRCULOS_NOTARIALES = "CARGAR_CIRCULOS_NOTARIALES";		


	/**
	* Constante que indica que se quieren consultar los turnos de restitución asociados con
	* una minuta.
	*/
	public final static String CARGAR_TURNOS_RESTITUCION_MINUTA = "CARGAR_TURNOS_RESTITUCION_MINUTA";

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
	
	/**
	 * Constante que identifica que se desea avanzar por FRACASO
	 */
	public final static String OBTENER_CATEGORIA_OK = "OBTENER_CATEGORIA_OK";
		

	private Turno turno;
	private Fase fase;
	private Estacion estacion = null;
	private SolicitudRepartoNotarial solicitudRepartoNotarial;
	private Minuta minuta = null;
	private Hashtable resultadoReparto = null;

	private String respuestaWF = "";
	
	private Map turnos;
	
	private Categoria categoria;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRepartoNotarial(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}
	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRepartoNotarial(Usuario usuario, Map turnos, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turnos = turnos;
	}
	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRepartoNotarial(List circulosNotariales,  String tipoAccion) {
		super(circulosNotariales, tipoAccion);
	}	
	
	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRespRepartoNotarial(Usuario usuario, Hashtable resultadoReparto, String tipoAccion) {
		super(usuario, tipoAccion);
		this.resultadoReparto = resultadoReparto;
	}	
	

	/**
	 * @param usuario
	 * @param solicitudRepartoNotarial
	 * @param tipoAccion
	 */
	public EvnRespRepartoNotarial(Usuario usuario, SolicitudRepartoNotarial solicitudRepartoNotarial, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudRepartoNotarial = solicitudRepartoNotarial;
	}

	/**
	 * @param usuario
	 * @param solicitudRepartoNotarial
	 * @param minuta
	 * @param tipoAccion
	 */
	public EvnRespRepartoNotarial(Usuario usuario, SolicitudRepartoNotarial solicitudRepartoNotarial, Minuta minuta, String tipoAccion) {
		super(usuario, tipoAccion);
		this.minuta = minuta;
		this.solicitudRepartoNotarial = solicitudRepartoNotarial;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRespRepartoNotarial(Usuario usuario, Turno turno, Fase fase, String tipoAccion, String respWF) {
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
	public EvnRespRepartoNotarial(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
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
	public EvnRespRepartoNotarial(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
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
	public SolicitudRepartoNotarial getSolicitudRepartoNotarial() {
		return solicitudRepartoNotarial;
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
	public Hashtable getResultadoReparto() {
		return resultadoReparto;
	}

	public Map getTurnos() {
		return turnos;
	}

	public void setTurnos(Map turnos) {
		this.turnos = turnos;
	}

}
