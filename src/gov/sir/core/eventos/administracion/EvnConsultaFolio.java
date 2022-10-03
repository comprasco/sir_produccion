package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la consulta de un folio
 * @author ppabon
 */
public class EvnConsultaFolio extends EvnSIR {

	/**Esta constante se utiliza para identificar el evento de consulta de un folio */
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
	
	/**Esta constante se utiliza para identificar el evento de anular un folio */
	public static final String ANULAR_FOLIO = "ANULAR_FOLIO";
	
	/**Esta constante se utiliza para identificar el evento de consulta de un folio, en reabrir folios*/
	public static final String CONSULTAR_FOLIO_REABRIR = "CONSULTAR_FOLIO_REABRIR";
	
	/** Constante que identifica la acción de desbloquear el folio para restitucion  */
	public static final String DESBLOQUEAR_FOLIO_RESTITUCION = "DESBLOQUEAR_FOLIO_RESTITUCION";
	
	/**Variable que permite consultar solamente los datos definitivos del folio, a menos que
	 * el usuario que esta consultando sea quién tiene el bloqueo de la matrícula.*/
	private boolean consultarDefinitivo = false; 
	
	private String matricula;
	
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	private Circulo circulo;
	
	private Folio folio;
	
	private String razonAnulacion;
 
	/**
	 * @param usuario
	 */
	public EvnConsultaFolio(Usuario usuario) {
		super(usuario);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnConsultaFolio(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	/**
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnConsultaFolio(Usuario usuario, String tipoEvento, String matricula, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.matricula = matricula;
		this.usuarioSIR = usuarioSIR;
	}



	/**
	 * @return
	 */
	public String getMatricula() {
		return matricula;
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
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	/**
	 * @return
	 */
	public boolean isConsultarDefinitivo() {
		return consultarDefinitivo;
	}

	/**
	 * @param b
	 */
	public void setConsultarDefinitivo(boolean b) {
		consultarDefinitivo = b;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Folio getFolio() {
		return folio;
	}

	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	public String getRazonAnulacion() {
		return razonAnulacion;
	}

	public void setRazonAnulacion(String razonAnulacion) {
		this.razonAnulacion = razonAnulacion;
	}

	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}

}
