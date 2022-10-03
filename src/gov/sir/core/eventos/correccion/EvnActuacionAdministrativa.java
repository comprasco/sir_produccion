package gov.sir.core.eventos.correccion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author ppabon
 */
public class EvnActuacionAdministrativa extends EvnSIR {
	
	//OPCIONES DE RESPUESTA EM EL WORKFLOW
	public static final String WF_MAYOR_VALOR = "MAYOR_VALOR";
	public static final String WF_ANTIGUO_SISTEMA = "ANTIGUO_SISTEMA";
	public static final String WF_CONFIRMAR = "CONFIRMAR";
	public static final String WF_REGRESAR = "REGRESAR";	

	//ACCIONES POSIBLES EN LA ACCIÓN WEB
	//OPCIONES DE EDICIÓN DE ACTUACIONES ADMINISTRATIVAS
	public static final String AGREGAR_NOTA = "AGREGAR_NOTA";
	public static final String EDITAR_NOTA = "EDITAR_NOTA";
	
	//OPCIONES DE AVANCE
	public static final String APROBAR_ACTUACION = "APROBAR_ACTUACION";
	public static final String DEVOLVER_A_CORRECCION = "DEVOLVER_A_CORRECCION";
	public static final String ENVIAR_TURNO = "ENVIAR_TURNO";

	private Turno turno;
	private Liquidacion liquidacion;
	private DatosAntiguoSistema datosAntiguoSistema;
	private Fase fase;
	private Folio folio;
	private List folios;
	private String respuestaWF;
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private NotaActuacion notaActuacion;

	private String estado;
	private String textoNota;

	private List permisos;

	/**
	 * Evento para añadir y quitar folios
	 * @param usuario
	 * @param folio
	 */
	public EvnActuacionAdministrativa(Usuario usuario, String tipoEvento, Turno turno, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.folio = folio;
		this.usuarioSIR = usuarioNeg;
	}
	
	/**
	 * Evento para avanzar el turno.
	 * @param usuario
	 * @param folio
	 */
	public EvnActuacionAdministrativa(Usuario usuario, String tipoEvento, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.usuarioSIR = usuarioNeg;
	}	
	
	/**
	 * Evento para avanzar agregar nota de actuaciones al turno.
	 * @param usuario
	 * @param folio
	 */
	public EvnActuacionAdministrativa(Usuario usuario, String tipoEvento, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipoEvento);

		this.usuarioSIR = usuarioNeg;
	}		

	/**
	 * @return
	 */
	public String getEstado() {
		return estado;
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
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public List getFolios() {
		return folios;
	}

	/**
	 * @return
	 */
	public List getPermisos() {
		return permisos;
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
	public String getTextoNota() {
		return textoNota;
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
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @param string
	 */
	public void setEstado(String string) {
		estado = string;
	}

	/**
	 * @param fase
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @param list
	 */
	public void setFolios(List list) {
		folios = list;
	}

	/**
	 * @param list
	 */
	public void setPermisos(List list) {
		permisos = list;
	}

	/**
	 * @param string
	 */
	public void setRespuestaWF(String string) {
		respuestaWF = string;
	}

	/**
	 * @param string
	 */
	public void setTextoNota(String string) {
		textoNota = string;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSIR = usuario;
	}

	/**
	 * @return
	 */
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	/**
	 * @param liquidacion
	 */
	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

	/**
	 * @return
	 */
	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/**
	 * @param sistema
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistema sistema) {
		datosAntiguoSistema = sistema;
	}

	/**
	 * @return
	 */
	public NotaActuacion getNotaActuacion() {
		return notaActuacion;
	}

	/**
	 * @param actuacion
	 */
	public void setNotaActuacion(NotaActuacion actuacion) {
		notaActuacion = actuacion;
	}

}
