package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

import java.util.List;

/**
 * Evento de Respuesta de solicitudes de consulta a la capa de negocio
  * @author jmendez
 */
public class EvnRespConsulta extends EvnSIRRespuesta {

	/**Esta constante se utiliza  para identificar el evento llegada de los resultados de una consulta */
	public final static String RESULTADO_CONSULTA = "RESULTADO_CONSULTA";

	/**Esta constante se utiliza  para identificar el evento llegada de los resultados de una consulta para ver los detalles de un folio*/
	public final static String VER_FOLIO = "VER_FOLIO";

	/**Esta constante se utiliza  para identificar el evento generación  satisfactoria de una solicitud de consulta  */
	public final static String SOLICITUD_CONSULTA_GENERADA = "SOLICITUD_CONSULTA_GENERADA";

	/** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
	
	/** Constante que identifica que se finalizo la pantalla.*/
	public static final String TERMINA = "TERMINA";

	private SolicitudConsulta solicitudConsulta;
	private Folio folio;
	private Usuario usuarioBloqueo;
	private Turno turnoTramite;
	private Turno turnoDeuda;
	
	private Busqueda ultimaBusqueda;
	private int idImprimible = 0;

	/**
	   * Constructor público que inicializa un evento de tipo RESULTADO_CONSULTA
	   * dando valor a sus folios
	   * @param folios Folios de respuesta de consulta
           * @param tipo Tipo de evento
	   */
	public EvnRespConsulta(List folios, String tipo) {
		super(folios, tipo);
	}

	/**
	   * Constructor público que inicializa un evento de tipo CONSULTAR_FOLIO
	   * dando valor a sus folios
	   * @param folio Folios de respuesta de consulta
           * @param tipo Tipo de evento
           * @param turnoDeuda Turno de deuda
           * @param turnoTramite Turno de trámite
           * @param usuarioBloqueo Usuario de bloqueo
	   */
	public EvnRespConsulta(Folio folio, Turno turnoTramite, Usuario usuarioBloqueo, Turno turnoDeuda, String tipo) {
		super(tipo);
		this.setTipoEvento(tipo);
		this.folio= folio;
		this.turnoTramite= turnoTramite;
		this.usuarioBloqueo= usuarioBloqueo;
		this.turnoDeuda= turnoDeuda;
	}

    /**
     * @param tipo Tipo de respuesta
     */
    public EvnRespConsulta(String tipo) {
	super(new java.util.Vector(), tipo);
	}
	
	/**
	 * @param tipo Tipo de respuesta
	 */
	public EvnRespConsulta(String tipo, int idImprimible) {
	super(new java.util.Vector(), tipo);
	this.idImprimible = idImprimible;
}	

	/**
	 * @return Folios de respuesta
	 */
	public List getFolios() {
		return (List) this.getPayload();
	}

	/**
	 * @return Folio consultado
	 */
	public Folio getFolio() {
		return  this.folio;
	}

	/**
	 * @return Turno de atención de trámite
	 */
	public Turno getTurnoTramite() {
		return  this.turnoTramite;
	}

	/**
	 * @return Turno de atención de deuda
	 */
	public Turno getTurnoDeuda() {
		return  this.turnoDeuda;
	}

	/**
	 * @return Usuario de bloqueo
	 */
	public Usuario getUsuarioBloqueo() {
		return  this.usuarioBloqueo;
	}

	/**
	 * @return solicitudConsulta Solicitud de consulta del evento
	 */
	public SolicitudConsulta getSolicitudConsulta() {
		return solicitudConsulta;
	}

	/**
	 * @param consulta Solicitud de consulta del evento
	 */
	public void setSolicitudConsulta(SolicitudConsulta consulta) {
		solicitudConsulta = consulta;
	}

	/**
	 * @return
	 */
	public Busqueda getUltimaBusqueda() {
		return ultimaBusqueda;
	}

	/**
	 * @param busqueda
	 */
	public void setUltimaBusqueda(Busqueda busqueda) {
		ultimaBusqueda = busqueda;
	}

	/**
	 * @return
	 */
	public int getIdImprimible() {
		return idImprimible;
	}

	/**
	 * @param i
	 */
	public void setIdImprimible(int i) {
		idImprimible = i;
	}

}
