package gov.sir.core.eventos.repartonotarial;

import java.util.List;
import java.util.Map;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes con respecto al proceso de reparto notarial de minutas, de la capa web a la capa de negocio.
 * @author ppabon
 */
public class EvnRepartoNotarial extends EvnSIR {

	/**
	 * Constante que identifica que se desea CREAR una solicitud
	 */
	public final static String CREAR_SOLICITUD = "CREAR_SOLICITUD";
	
	/**
	 * Constante que identifica que se desea realizar el reparto de minutas.
	 */
	public final static String EJECUTAR_REPARTO = "EJECUTAR_REPARTO";

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
	 * Constante que identifica que se desea avanzar por CONFIRMAR
	 */
	public final static String CONFIRMAR = "CONFIRMAR";
	
	/**
	 * Constante que identifica que se desea avanzar por NEGAR
	 */
	public final static String NEGAR = "NEGAR";
	
	/**
	 * Constante que identifica que se desea obtener la categoria de una minuta
	 */
	public final static String OBTENER_CATEGORIA = "OBTENER_CATEGORIA";
		
	private Turno turno;
	private Fase fase;
	private Estacion estacion = null;
	private SolicitudRepartoNotarial solicitudRepartoNotarial;
	private String respuestaWF = "";
	private Minuta minuta = null;
	private Circulo circulo = null;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private String UID = null;
	private Rol rol = null;
	private String numeroActas = null;
	private String numeroResumenes = null;
	private String tipoReparto = null;
	private String turnoExtraordinario = null;
	private String[]  turnosExtraordinarios = null; 
	
	private String idMinutaConsultaRestitucion = null;
	
	private String idCirculoMinutaConsultaRestitucion = null;
	
	private List notasInformativas = null;
	private Map turnos = null;
	
	private boolean imprimirConstancia = true;


	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRepartoNotarial(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Circulo circulo, Estacion estacion, String tipoAccion, String numeroActas, String numeroResumenes, String tipoReparto, String turnoExtraordinario) {
		super(usuario, tipoAccion);
		this.circulo = circulo;
		this.numeroActas = numeroActas;
		this.numeroResumenes = numeroResumenes;
		this.usuarioSIR = usuarioSIR;
		this.tipoReparto = tipoReparto;
		this.turnoExtraordinario = turnoExtraordinario;
	}
	
	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRepartoNotarial(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Circulo circulo, Estacion estacion, String tipoAccion, String numeroActas, String numeroResumenes, String tipoReparto, String[] turnosExtraordinarios) {
		super(usuario, tipoAccion);
		this.circulo = circulo;
		this.numeroActas = numeroActas;
		this.numeroResumenes = numeroResumenes;
		this.usuarioSIR = usuarioSIR;
		this.tipoReparto = tipoReparto;
		this.turnosExtraordinarios = turnosExtraordinarios;
	}

        public EvnRepartoNotarial(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Circulo circulo, Estacion estacion, String tipoAccion, String numeroActas, String numeroResumenes, String tipoReparto, String[] turnosExtraordinarios, List notasInformativas) {
		super(usuario, tipoAccion);
		this.circulo = circulo;
		this.numeroActas = numeroActas;
		this.numeroResumenes = numeroResumenes;
		this.usuarioSIR = usuarioSIR;
		this.tipoReparto = tipoReparto;
		this.turnosExtraordinarios = turnosExtraordinarios;
                this.notasInformativas = notasInformativas;
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRepartoNotarial(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}
	
	/**
	 * @param usuario
	 * @param circulo
	 * @param tipoAccion
	 */
	public EvnRepartoNotarial(Usuario usuario, Circulo circulo, String tipoAccion) {
		super(usuario, tipoAccion);
		this.circulo = circulo;
	}	

	/**
	 * @param usuario
	 * @param solicitudRepartoNotarial
	 * @param tipoAccion
	 */
	public EvnRepartoNotarial(Usuario usuario, SolicitudRepartoNotarial solicitudRepartoNotarial, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudRepartoNotarial = solicitudRepartoNotarial;
	}
	
	/**
	 * @param usuario
	 * @param solicitudRepartoNotarial
	 * @param minuta
	 * @param tipoAccion
	 */
	public EvnRepartoNotarial(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, SolicitudRepartoNotarial solicitudRepartoNotarial, Minuta minuta, String tipoAccion, String UID, Rol rol , Estacion estacion , Circulo circulo, List notasInformativas) {
		super(usuario, tipoAccion);
		this.minuta = minuta;
		this.usuarioSIR = usuarioSIR;
		this.solicitudRepartoNotarial = solicitudRepartoNotarial;
		this.UID = UID;
		this.rol = rol;
		this.estacion = estacion;
		this.circulo = circulo;
		this.notasInformativas = notasInformativas;
	}


	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRepartoNotarial(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
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
	public EvnRepartoNotarial(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Map turnos, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turnos = turnos;
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
	public EvnRepartoNotarial(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
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
	public EvnRepartoNotarial(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
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
	public Circulo getCirculo() {
		return circulo;
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
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return
	 */
	public String getNumeroActas() {
		return numeroActas;
	}

	/**
	 * @return
	 */
	public String getNumeroResumenes() {
		return numeroResumenes;
	}

	/**
	 * @return
	 */
	public List getNotasInformativas() {
		return notasInformativas;
	}

	/**
	 * @param list
	 */
	public void setNotasInformativas(List list) {
		notasInformativas = list;
	}

	/**
	 * @return
	 */
	public String getTipoReparto() {
		return tipoReparto;
	}

	/**
	 * @return
	 */
	public String getTurnoExtraordinario() {
		return turnoExtraordinario;
	}

	/**
	 * @return
	 */
	public String getIdMinutaConsultaRestitucion() {
		return idMinutaConsultaRestitucion;
	}

	/**
	 * @param string
	 */
	public void setIdMinutaConsultaRestitucion(String string) {
		idMinutaConsultaRestitucion = string;
	}

	/**
	 * @return
	 */
	public String getIdCirculoMinutaConsultaRestitucion() {
		return idCirculoMinutaConsultaRestitucion;
	}

	/**
	 * @param string
	 */
	public void setIdCirculoMinutaConsultaRestitucion(String string) {
		idCirculoMinutaConsultaRestitucion = string;
	}

	public String[] getTurnosExtraordinarios() {
		return turnosExtraordinarios;
	}

	public void setTurnosExtraordinarios(String[] turnosExtraordinarios) {
		this.turnosExtraordinarios = turnosExtraordinarios;
	}

	public Map getTurnos() {
		return turnos;
	}

	public void setTurnos(Map turnos) {
		this.turnos = turnos;
	}

	public boolean isImprimirConstancia() {
		return imprimirConstancia;
	}

	public void setImprimirConstancia(boolean imprimirConstancia) {
		this.imprimirConstancia = imprimirConstancia;
	}

}
