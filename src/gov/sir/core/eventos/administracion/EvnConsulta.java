package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;
/**
 * Evento de Envio de solicitudes de consulta a la capa de  negocio
 * @author jmendez
 */
public class EvnConsulta extends EvnSIR {

	/** Constante que identifica el valor de la accion para una consulta simple */
	public static final String NUEVA_BUSQUEDA = "NUEVA_BUSQUEDA";

	public static final String VALIDA_FOLIO = "VALIDA_FOLIO";

	/** Constante que identifica el valor de la accion para  una consulta simple */
	public static final String GENERAR_CONSULTA = "GENERAR_CONSULTA";

	/** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
		
	/** Constante que identifica que se finalizo la pantalla.*/
	public static final String TERMINA = "TERMINA";
	
	/** Constante que identifica el valor de la accion para  una consulta de folio */
	public static final String FOLIO = "FOLIO";

    private String UID;

	private Folio folio;

	private SolicitudConsulta solicitudConsulta;

	private LiquidacionTurnoConsulta liquidacionTurnoConsulta;

	private Busqueda busqueda;

	private Turno turno = null;

	private Fase fase = null;

	private Estacion estacion = null;

	private List solicitudesFolio = null;

	private String respuestaWF = "";

	private String matriculaInmobiliaria = null;

	private Ciudadano ciudadano = null;

	private String idMatricula = null;

	private gov.sir.core.negocio.modelo.Usuario usuarioNeg = null;

        private boolean enabled_SolicitudConsultasTipoConsultaExentaPrint = false;
        private boolean enabled_SolicitudConsultasTipoConsultaExenta      = false;

	/**
	 * Usado para confirmar y negar consultas
	 *
	 * @param usuario Usuario de Auriga
	 * @param tipoEvento Tipo del evento
	 */
	public EvnConsulta(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}

	/**
	 * Usado para confirmar y negar consultas
	 *
	 * @param usuario Usuario de Auriga
	 * @param tipoEvento Tipo del evento
         * @param busqueda Búsqueda del evento
	 */
	public EvnConsulta(Usuario usuario, String tipoEvento, Busqueda busqueda) {
		super(usuario, tipoEvento);
		this.busqueda = busqueda;
	}

	/**
	* Usado para confirmar y negar consultas
	*
	* @param usuario Usuario Auriga
	* @param tipoEvento Tipo del evento
	* @param folio Folio a consultar
	*/
	public EvnConsulta(Usuario usuario, String tipoEvento, Folio folio) {
		super(usuario, tipoEvento);
		this.folio = folio;
	}

	/**
	* Usado para confirmar y negar consultas
	*
	* @param usuario Usuario que realiza la consulta
	* @param tipoEvento Tipo de evento
	* @param folio Folio a consultar
        * @param usuarioNeg Usuario de capa de negocio
	*/
	public EvnConsulta(Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String tipoEvento, Folio folio) {
		super(usuario, tipoEvento);
		this.usuarioNeg = usuarioNeg;
		this.folio = folio;
	}

	/**
	* Usado para confirmar y negar consultas
	*
        * @param usuario Usuario que realiza la consulta
        * @param tipoEvento Tipo de evento
        * @param folio Folio a consultar
        * @param usuarioNeg Usuario de capa de negocio
        * @param fase Fase del evento
	*/
	public EvnConsulta(
		Usuario usuario,
		String tipoEvento,
		Folio folio,
		gov.sir.core.negocio.modelo.Usuario usuarioNeg,
		Fase fase) {
		super(usuario, tipoEvento);
		this.usuarioNeg = usuarioNeg;
		this.folio = folio;
		this.fase = fase;
	}

	/**
         * @return Folio del evento
         */
	public Folio getFolio() {
		return folio;
	}

        /**
         * @param folio Folio del evento
         */
        public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @return liquidacionTurnoConsulta Consulta del evento
	 */
	public LiquidacionTurnoConsulta getLiquidacionTurnoConsulta() {
		return liquidacionTurnoConsulta;
	}

	/**
	 * @param consulta Consulta del evento
	 */
	public void setLiquidacionTurnoConsulta(LiquidacionTurnoConsulta consulta) {
		liquidacionTurnoConsulta = consulta;
	}

	/**
	 * @return solicitudConsulta Solicitud del evento
	 */
	public SolicitudConsulta getSolicitudConsulta() {
		return solicitudConsulta;
	}

	/**
	 * @param consulta Solicitud  del evento
	 */
	public void setSolicitudConsulta(SolicitudConsulta consulta) {
		solicitudConsulta = consulta;
	}

	/**
	 * @return busqueda Búsqueda del evento
	 */
	public Busqueda getBusqueda() {
		return busqueda;
	}

	/**
	 * @param busqueda Búsqueda del evento
	 */
	public void setBusqueda(Busqueda busqueda) {
		this.busqueda = busqueda;
	}

	/**
	 * @return estacion Estación del evento
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @return fase Fase del evento
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 * @return turno Turno del evento
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @param estacion Estación del evento
	 */
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	/**
	 * @param fase Fase del evento
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	/**
	 * @param turno Turno del evento
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return Respuesta de workflow del evento
	 */
	public String getRespuestaWF() {
		return respuestaWF;
	}

	/**
	 * @param string Respuesta de workflow del evento
	 */
	public void setRespuestaWF(String string) {
		respuestaWF = string;
	}

	/**
	 * @return Folios de la solicitud delevento
	 */
	public List getSolicitudesFolio() {
		return solicitudesFolio;
	}

	/**
	 * @param list Folios de la solicitud del evento
	 */
	public void setSolicitudesFolio(List list) {
		solicitudesFolio = list;
	}

	/**
	 * @return Matricula del evento
	 */
	public String getMatriculaInmobiliaria() {
		return matriculaInmobiliaria;
	}

	/**
	 * @param string Matricula del evento
	 */
	public void setMatriculaInmobiliaria(String string) {
		matriculaInmobiliaria = string;
	}

	/**
	 * @return Ciudadano del evento
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	/**
	 * @param ciudadano Ciudadano del evento
	 */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	/**
	 * @return Usuario del evento
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

        /**
         * @param UID String Identificación de sesión del evento
         */
        public void setUID (String UID) {
            this.UID = UID;
        }

        /**
         * @return String Identificación de sesión del evento
         */
        public String getUID () {
            return UID;
        }

        private Circulo circulo;

        /**
         * @return
         */
        public Circulo getCirculo() {
            // TODO Auto-generated method stub
            return circulo;
        }

	public boolean isEnabled_SolicitudConsultasTipoConsultaExentaPrint() {
		return enabled_SolicitudConsultasTipoConsultaExentaPrint;
	}

	public boolean isEnabled_SolicitudConsultasTipoConsultaExenta() {
		return enabled_SolicitudConsultasTipoConsultaExenta;
	}

	public void setCirculo(Circulo circulo) {
            this.circulo = circulo;
        }

	public void setEnabled_SolicitudConsultasTipoConsultaExentaPrint(boolean
		 enabled_SolicitudConsultasTipoConsultaExentaPrint) {
		this.enabled_SolicitudConsultasTipoConsultaExentaPrint =
			 enabled_SolicitudConsultasTipoConsultaExentaPrint;
	}

	public void setEnabled_SolicitudConsultasTipoConsultaExenta(boolean
		 enabled_SolicitudConsultasTipoConsultaExenta) {
		this.enabled_SolicitudConsultasTipoConsultaExenta =
			 enabled_SolicitudConsultasTipoConsultaExenta;
	}
}
