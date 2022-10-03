package gov.sir.core.eventos.certificadosmasivos;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.Proceso;

import gov.sir.core.negocio.modelo.Turno;

/**
 * Envio de solicitudes con respecto a certificados, a la capa de negocio.
 * Cada constante explica a que fase y la accion que es.
 * @author ppabon
 */
public class EvnCertificadoMasivo extends EvnSIR {

	/**Esta constante define el tipo de entrega confirmar luego de la impresión de los certificados*/
	public static final String ENTREGAR_CONFIRMAR = "ENTREGAR_CONFIRMAR";

	/**Esta constante define el tipo de entrega confirmar luego de la impresión de los certificados*/
	public static final String ENTREGAR_NEGAR = "ENTREGAR_NEGAR";
	
	/**Esta constante define la acción para llamar la liquidación de certificados masivos*/
	public static final String LIQUIDAR = "LIQUIDAR";


	/**Esta constante define el tipo de respuesta CONFIRMAR*/
	public static final String WF_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define el tipo de respuesta NEGAR*/
	public static final String WF_NEGAR = "NEGAR";
	
	/**Esta constante define la respuesta que se le da al workflow que se 
	 * guarda en el evento*/
	private String respuestaWF = "";

	/**Esta constante define el turno con el cual se esta trabajado*/
	private Turno turno = null;

	/**Esta constante define la fase en la cual se esta trabajando*/
	private Fase fase = null;

	/**Estacion donde el usuario esta trabajando*/
	private Estacion estacion = null;

	/** Identificador unico de usuario*/
	private String UID;

	/**Informacion del usuario que quiere trabajar sobre el workflow*/
	private gov.sir.core.negocio.modelo.Usuario usuarioNec;
	
	/**Liquidación de los certificados masivos */
	private LiquidacionTurnoCertificadoMasivo liquidacion;
	
	/**Clase proceso, utilizada para la creacion de la liquidación */
	private Proceso proceso;	
	
	/**Clase circulo, utilizada para la creacion de la liquidación */
	private Circulo circulo;	
	
	/**Identificador del circulo, utilizada para la creacion de la liquidación */
	private String idCirculo;	
	
	/**Identificador de la matricula*/
	private String matricula;	
	
	//daniel
	/**Lista de matriculas por archivo*/
	private List matriculasArchivo = null;
	
	/**Lista de copías de las matriculas*/
	private List copiasMatriculas = null;


	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * agregar un oficio de pertenencia al certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param oficioPertenencia String El oficio de pertenencia que se va a agregar.
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 */
	public EvnCertificadoMasivo(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, String respuestaWF, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.usuarioNec = usuario;
	}
	
	/**
	 * @param usuarioAuriga
	 * @param liquidacion
	 * @param proceso
	 * @param estacion
	 * @param UID
	 * @param circulo
	 * @param idCirculo
	 * @param usuario
	 */
	public EvnCertificadoMasivo(Usuario usuarioAuriga, String tipoEvento, LiquidacionTurnoCertificadoMasivo liquidacion, Proceso proceso, Estacion estacion, String UID, Circulo circulo, String idCirculo, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga,tipoEvento);
		this.liquidacion = liquidacion;
		this.proceso = proceso;
		this.estacion = estacion;
		this.circulo = circulo;
		this.idCirculo = idCirculo;
		this.usuarioNec = usuario;
	}
	
	/**
	 * @param usuarioAuriga
	 * @param liquidacion
	 * @param proceso
	 * @param estacion
	 * @param UID
	 * @param circulo
	 * @param idCirculo
	 * @param usuario
	 */
	public EvnCertificadoMasivo(Usuario usuarioAuriga, String matricula, String tipoEvento) {
		super(usuarioAuriga,tipoEvento);
		this.setMatricula(matricula);
	}	
	
	/**
	 * @param usuarioAuriga
	 * @param liquidacion
	 * @param proceso
	 * @param estacion
	 * @param UID
	 * @param circulo
	 * @param idCirculo
	 * @param usuario
	 */
	public EvnCertificadoMasivo(Usuario usuarioAuriga, List matriculasArchivo, List copiasMatriculas, String tipoEvento) {
		super(usuarioAuriga,tipoEvento);
		this.setMatriculasArchivo(matriculasArchivo);
		this.setCopiasMatriculas(copiasMatriculas);
	}

	/**
	 * Retorna la fase que viaja en el evento.
	* @return gov.sir.core.negocio.modelo.Fase
	*/
	public Fase getFase() {
		return fase;
	}

	/**
	 * Retorna el turno que viaja en el evento
	 * @return gov.sir.core.negocio.modelo.Turno
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * Retorna la respuesta del WorkFlow que viaja en el evento
	 * @return String 
	 */
	public String getRespuestaWF() {
		return respuestaWF;
	}

	/**
	 * Retorna la estacion que viaja en el evento
	 * @return org.auriga.core.modelo.transferObjects.Estacion
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * Retorna el usuario SIR que viaja en el negocio
	 * @return gov.sir.core.negocio.modelo.Usuario
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNec() {
		return usuarioNec;
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
	public String getMatricula() {
		return matricula;
	}
	
	/**
	 * @param string
	 */
	public void setMatricula(String string) {
		matricula = string;
	}

	/**
	 * @return
	 */
	public LiquidacionTurnoCertificadoMasivo getLiquidacion() {
		return liquidacion;
	}

	/**
	 * @param masivo
	 */
	public void setLiquidacion(LiquidacionTurnoCertificadoMasivo masivo) {
		liquidacion = masivo;
	}

	/**
	 * @return
	 */
	public Proceso getProceso() {
		return proceso;
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
	public String getIdCirculo() {
		return idCirculo;
	}
	
	//daniel
	public void setMatriculasArchivo(List matriculasArchivo) {
		this.matriculasArchivo = matriculasArchivo;
	}
	
	public List getMatriculasArchivo() {
		return this.matriculasArchivo;
	}
	
	public void setCopiasMatriculas(List copiasMatriculas) {
		this.copiasMatriculas = copiasMatriculas;
	}
	
	public List getCopiasMatriculas() {
		return this.copiasMatriculas;
	}
}
