package gov.sir.core.eventos.correccion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envio de solicitudes con respecto a la fase de confrontación en el proceso de 
 * correcciones, de la capa web a la capa de negocio.
 * @author ppabon
 */
public class EvnConfrontacion extends EvnSIR {
	
	/**Tipo de evento para asociar un rango de matriculas*/
	public static final String ASOCIAR_UN_RANGO = "ASOCIAR_UN_RANGO";

	/**Tipo de evento que se utiliza para asociar una matrícula*/
	public static final String ASOCIAR_UNA_MATRICULA = "ASOCIAR_UNA_MATRICULA ";
	
	/**Tipo de evento que se utiliza para asociar una matrícula*/
	public static final String ELIMINAR_UNA_MATRICULA = "ELIMINAR_UNA_MATRICULA ";
	
	/**Tipo de envento que se utiliza para confirmar la confrontacion*/
	public static final String CONFIRMAR = "CONFIRMAR";	
	
	/**AVANCE WORKFLOW**/
	/**Tipo de evento que se desea avanzar por éxito*/
	public static final String EXITO = "EXITO";	
	
	/**Tipo de evento que se desea avanzar por fracaso*/
	public static final String FRACASO = "FRACASO";	
	
	private Turno turno;
	private Fase fase;
	private Folio folio;
	private List folios;
	private String idMatriculaRL;
	private String idMatriculaRR;
	private String respuestaWF;
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	
	/**
	 * Evento para añadir y quitar folios
	 * @param usuario
	 * @param folio
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario, tipo);
		this.turno=turno;
		this.folio=folio;
		this.usuarioNeg = usuarioNeg;
	}
	
	/**
	 * Evento para añadir y quitar folios
	 * @param usuario
	 * @param folio
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, List folios) {
		super(usuario, tipo);
		this.turno=turno;
		this.folios=folios;
	}
		
	
	/**
	 * @param usuario
	 * @param string
	 * @param turno
	 * @param idMatRL
	 * @param idMatRR
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, String idMatriculaRL, String idMatriculaRR, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,tipo);
		this.turno=turno;
		this.idMatriculaRL=idMatriculaRL;
		this.idMatriculaRR=idMatriculaRR;
		this.usuarioNeg = usuarioNeg;
	}

	/**
	 * Evento para avanzar
	 * @param usuario
	 * @param tipo
	 * @param turno
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, Fase fase, 
	String respuestaWF) {
		super(usuario,tipo);
		this.turno=turno;
		this.fase=fase;
		this.respuestaWF=respuestaWF;
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
	public String getIdMatriculaRL() {
		return idMatriculaRL;
	}

	/**
	 * @return
	 */
	public String getIdMatriculaRR() {
		return idMatriculaRR;
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
	public List getFolios() {
		return folios;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

}
