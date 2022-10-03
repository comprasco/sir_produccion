package gov.sir.core.eventos.restitucionreparto;

import java.util.List;
import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.CausalRestitucion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes con respecto al proceso de restitución de reparto notarial, de la capa web a la capa de negocio.
 * @author ppabon
 */
public class EvnRestitucionReparto extends EvnSIR {
	/**
	 * Constante que identifica que se desea CREAR una solicitud
	 */
	public final static String CREAR_SOLICITUD = "CREAR_SOLICITUD";
	
	/**
	 * Constante que identifica que se desea CONSULTAR la minuta del turno anterior
	 */
	public final static String CONSULTAR_MINUTA = "CONSULTAR_MINUTA";

	/**
	 * Constante que identifica que se desea avanzar por el análisis de restitución.
	 */
	public final static String ANALISIS_RESTITUCION = "ANALISIS_RESTITUCION";

	/**
	 * Constante que identifica que se desea notificar al funcionario.
	 */
	public final static String NOTIFICAR_CIUDADANO = "NOTIFICAR_CIUDADANO";

	/**
	 * Constante que identifica la consulta de los turnos para analisis de restirucion de reparto.
	 */
	public final static String CONSULTAR_TURNOS_ANALISIS_RESTITUCION = "CONSULTAR_TURNOS_ANALISIS_RESTITUCION";
	
	/*
	 * Constantes de respuestas del WorkFlow
	 */

	/**
	 * Constante que identifica que se desea avanzar por CONFIRMAR
	 */
	public final static String CONFIRMAR = "CONFIRMAR";

	/**
	 * Constante que identifica que se desea avanzar por NEGAR
	 */
	public final static String NEGAR = "NEGAR";
	
	private Turno turno;
	private Fase fase;
	private Estacion estacion = null;
	private SolicitudRestitucionReparto solicitudRestitucionReparto;
	private String respuestaWF = "";
	private CausalRestitucion causalRestitucion = null;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private String UID = null;
	private Rol rol = null;	
	Circulo circulo;
	private boolean imprimible;
	private List listaturnos;
	private List listaTurnosInfo;
	private List turnoRestitucion;

	/**
	 * @param usuario
	 * @param turno
	 * @param tipoAccion
	 */
	public EvnRestitucionReparto(Usuario usuario, Turno turno, String tipoAccion) {
		super(usuario, tipoAccion);
		this.turno = turno;
	}

	/**
	 * @param usuario
	 * @param solicitudRestitucionReparto
	 * @param tipoAccion
	 */
	public EvnRestitucionReparto(Usuario usuario, SolicitudRestitucionReparto solicitudRestitucionReparto, String tipoAccion) {
		super(usuario, tipoAccion);
		this.solicitudRestitucionReparto = solicitudRestitucionReparto;
	}

	/**
	 * @param usuario
	 * @param solicitudRestitucionReparto
	 * @param causalRestitucion
	 * @param tipoAccion
	 */
	public EvnRestitucionReparto(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, SolicitudRestitucionReparto solicitudRestitucionReparto, CausalRestitucion causalRestitucion, String tipoAccion, String UID, Rol rol, Estacion estacion, Circulo circulo) {
		super(usuario, tipoAccion);
		this.usuarioSIR = usuarioSIR;
		this.causalRestitucion = causalRestitucion;
		this.solicitudRestitucionReparto = solicitudRestitucionReparto;
		this.rol = rol;
		this.estacion = estacion;
		this.UID = UID;
		this.circulo = circulo;
	}

	/**
	 * @param usuario
	 * @param usuarioSIR
	 * @param turno
	 * @param fase
	 * @param solicitudRestitucionReparto
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRestitucionReparto(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, SolicitudRestitucionReparto solicitudRestitucionReparto, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.solicitudRestitucionReparto = solicitudRestitucionReparto;
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
	public EvnRestitucionReparto(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String respWF) {
		super(usuario, tipoAccion);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respWF;
		this.usuarioSIR = usuarioSIR;
	}
	
	/**
	 * @param usuario
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRestitucionReparto(Usuario usuario , String tipoAccion) {
		super(usuario, tipoAccion);
	}

	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param estacion
	 * @param tipoAccion
	 * @param respWF
	 */
	public EvnRestitucionReparto(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion, String respWF) {
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
	public EvnRestitucionReparto(Usuario usuario, Turno turno, Fase fase, Estacion estacion, String tipoAccion) {
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
	public SolicitudRestitucionReparto getSolicitudRestitucionReparto() {
		return solicitudRestitucionReparto;
	}

	/**
	 * @return
	 */
	public CausalRestitucion getCausalRestitucion() {
		return causalRestitucion;
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
	public Rol getRol() {
		return rol;
	}

	/**
	 * @return
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @param rol
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
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
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public boolean isImprimible() {
		return imprimible;
	}

	public void setImprimible(boolean imprimible) {
		this.imprimible = imprimible;
	}

	
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}
	
	public List getListaturnos() {
		return listaturnos;
	}

	public void setListaturnos(List listaturnos) {
		this.listaturnos = listaturnos;
	}

	public List getListaTurnosInfo() {
		return listaTurnosInfo;
	}

	public void setListaTurnosInfo(List listaTurnosInfo) {
		this.listaTurnosInfo = listaTurnosInfo;
	}

	public List getTurnoRestitucion() {
		return turnoRestitucion;
	}

	public void setTurnoRestitucion(List turnoRestitucion) {
		this.turnoRestitucion = turnoRestitucion;
	}

}
