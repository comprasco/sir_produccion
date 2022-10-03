package gov.sir.core.eventos.consulta;

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

	public static final String NUEVA_BUSQUEDA_CALIFICACION = "NUEVA_BUSQUEDA_CALIFICACION";

	/** Constante que identifica el valor de la accion para una consulta simple */
	public static final String INTENTO_SIMPLE = "INTENTO_SIMPLE";

	/**Esta constante se utiliza  para identificar el evento de un intento de consulta simple */
	public static final String INTENTO_SIMPLE_CALIFICACION = "INTENTO_SIMPLE_CALIFICACION";

	/** Constante que identifica el valor de la accion para  una consulta de folio */
	public static final String FOLIO = "FOLIO";

	/**Esta constante se utiliza  para identificar el tipo de consultas de folio para el proceso de calificación  */
	public static final String FOLIO_CALIFICACION = "FOLIO_CALIFICACION";

	/**Esta constante se utiliza  para identificar el tipo de consultas de folio para el proceso de correcciones  */
	public static final String FOLIO_CORRECCION = "FOLIO_CORRECCION";


	/**Esta constante se utiliza  para identificar el evento de consultas de folio desde el proceso de calificación  */
	public static final String CONSULTA_CALIFICACION_FOLIO = "CONSULTA_CALIFICACION_FOLIO";

	public static final String VALIDA_FOLIO = "VALIDA_FOLIO";

	/** Constante que identifica el valor de la accion para  una consulta simple */
	public static final String GENERAR_CONSULTA = "GENERAR_CONSULTA";

	/**Esta constante se utiliza  para identificar el evento de impresión de una consulta  */
	public static final String IMPRIMIR = "IMPRIMIR";

	/**Esta constante se utiliza   para identificar el evento de  adicionar una solicitud de folio a una consulta  */
	public static final String ADICIONAR_SOLICITUD_FOLIO = "ADICIONAR_SOLICITUD_FOLIO";

	/**Esta constante se utiliza   para identificar el evento de  adicionar un ciudadano a una solicitud de consulta compleja */
	public static final String ADICIONAR_CIUDADANO_A_SOLICITUD_COMPLEJA =
		"ADICIONAR_CIUDADANO_A_SOLICITUD_COMPLEJA";

	/**Esta constante se utiliza   para identificar el evento de avanzar el turno dentro del workflow*/
	public static final String AVANZAR_TURNO = "AVANZAR_TURNO";

	/**Esta constante se utiliza   para identificar el evento de negar el avance  del turno dentro del workflow*/
	public static final String NEGAR_TURNO = "NEGAR_TURNO";

	/**Esta constante se utiliza   para identificar el evento de efectuar una consulta para un traslado */
	public static final String CONSULTA_TRASLADO = "CONSULTA_TRASLADO";

	/**Esta constante se utiliza   para identificar los ESTADOS DEL WF: RESULTADOS GENERICOS */
	public static final String CONFIRMAR = "CONFIRMAR";
	public static final String NEGAR = "NEGAR";

	/**Esta constante se utiliza   para identificar los PROCESOS IMPRESIÓN */
	public static final String EXITO = "EXITO";
	public static final String FRACASO = "FRACASO";

	/** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

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
	
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		this.usuarioNeg=usuarioNeg;
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
