package gov.sir.core.eventos.registro;

import java.util.Hashtable;
import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author jfrias,mmunoz
 *
 */
public class EvnAprobarAjuste extends EvnSIR {

	public static final String ENVIAR_CORRECCION="CORRECCION_ENCABEZADO";
	public static final String AVANZAR="AVANZAR";
	public static final String AVANZAR_DESBLOQUEAR="AVANZAR_DESBLOQUEAR";
	public static final String GRABAR_ANOTACIONES_TEMPORALMENTE="GRABAR_ANOTACIONES_TEMPORALMENTE";
	public static final String CANCELAR_ANOTACION="CANCELAR_ANOTACION";
	public static final String TOMAR_TURNO="TOMAR_TURNO";
	public static final String CONSULTA="CONSULTA";
	public static final String CREAR_FOLIO_ENGLOBE="CREAR_FOLIO_ENGLOBE";
	public static final String SEGREAR_MASIVO = "SEGREAR_MASIVO"; 
	
	public static final String WF_CONFIRMAR="CONFIRMAR";
	public static final String WF_NEGAR="NEGAR";
	public static final String WF_CONFIRMAR_CALIFICACION="OK";
	public static final String WF_CORRECCCION="CORRECCION";
	public static final String WF_DIGITACION="DIGITACION";
	public static final String WF_DEVOLUCION="DEVOLUCION";
	public static final String WF_MICROFILMACION="MICROFILMACION";
	public static final String WF_ESPECIALIZADO="ESPECIALIZADO";
	public static final String WF_ANTIGUO_SISTEMA="ANTIGUO_SISTEMA";
	
	public static final String WF_FIRMA_REGISTRO_CONFIRMAR="CONFIRMAR";
	public static final String WF_MESA_CONTROL_CONFIRMAR="CONFIRMAR";
	public static final String WF_ENTREGA_CONFIRMAR="CONFIRMAR";

	public static final String VALIDAR_APROBAR_CALIFICACION="VALIDAR_APROBAR_CALIFICACION";
	
	private String respuestaWf;
	private int tipoAvance;		
	private Turno turno;
	private SolicitudRegistro solicitud;
	private Fase fase;
	private Folio folio;
	private List anotaciones;
	private List foliosDerivados;
	//private Usuario usuario;
	private Anotacion anotacion;
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	private List foliosFuente;
	private Hashtable tabla;
	//private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private String idCirculo = null;
	
	/**
	 * @param usuario
	 */
	public EvnAprobarAjuste(Usuario usuario) {
		super(usuario);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnAprobarAjuste(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}
	
	
	
	/**
	 * Se utiliza para consultar un folio
	 * @param usuario Usuario que está solicitando el folio
	 * @param tipoEvento Tipo de evento que se genera
	 * @param folio Folio a consultar.  Sólo es necesario que tenga la matrícula del folio
	 * @param usuarioNeg  Usuario del negocio
	 */
	public EvnAprobarAjuste(Usuario usuario, String tipoEvento, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioNeg){
		super(usuario, tipoEvento);
		this.folio=folio;
		this.usuarioNeg=usuarioNeg;
	}
	
			
	public EvnAprobarAjuste(Usuario usuario , Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,TOMAR_TURNO);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
	}
	
		
	/**
	 * @param usuario
	 * @param turno
	 * @param fase
	 * @param string
	 */
	public EvnAprobarAjuste(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Turno turno, Fase fase,  String respuestaWf, int tipoAvance) {
		super(usuario,AVANZAR);
		this.turno=turno;
		this.usuarioNeg=usuarioNeg;
		this.fase=fase;
		this.tipoAvance = tipoAvance;
		this.respuestaWf=respuestaWf;
	}
	
			
	/**
	 * @return
	 */
	public String getRespuestaWf() {
		return respuestaWf;
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
	public List getAnotaciones() {
		return anotaciones;
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
	public List getFoliosDerivados() {
		return foliosDerivados;
	}
	

	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

	/**
	 * @return
	 */
	public Anotacion getAnotacion() {
		return anotacion;
	}

	/**
	 * @return
	 */
	public List getFoliosFuente() {
		return foliosFuente;
	}
	/**
	 * @return
	 */
	public Hashtable getTabla() {
		return tabla;
	}

	
	/**
	 * @return
	 */
	public SolicitudRegistro getSolicitud() {
		return solicitud;
	}

	/**
	 * @param registro
	 */
	public void setSolicitud(SolicitudRegistro registro) {
		solicitud = registro;
	}
	/**
	 * @return
	 */
	public int getTipoAvance() {
		return tipoAvance;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public String getIdCirculo() {
		return idCirculo;
	}

	/**
	 * @param string
	 */
	public void setIdCirculo(String string) {
		idCirculo = string;
	}

}
