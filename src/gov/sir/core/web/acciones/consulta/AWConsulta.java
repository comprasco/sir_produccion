package gov.sir.core.web.acciones.consulta;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.consulta.EvnConsulta;
import gov.sir.core.eventos.consulta.EvnRespConsulta;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ResultadoFolio;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CExcepcion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CMunicipio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudConsulta;
import gov.sir.core.negocio.modelo.constantes.CTabs;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWAdministracionForseti;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CadenaNoPermitidaException;
import gov.sir.core.web.acciones.excepciones.CirculoNoEncontradoException;
import gov.sir.core.web.acciones.excepciones.ConsultaCalificacionFolioException;
import gov.sir.core.web.acciones.excepciones.ConsultaCorreccionFolioException;
import gov.sir.core.web.acciones.excepciones.ConsultaFolioEspecializadoException;
import gov.sir.core.web.acciones.excepciones.CriteriosInvalidosException;
import gov.sir.core.web.acciones.excepciones.EjecucionConsultaComplejaException;
import gov.sir.core.web.acciones.excepciones.EjecucionConsultaSimpleException;
import gov.sir.core.web.acciones.excepciones.EjecucionIntentoConsultaComplejaException;
import gov.sir.core.web.acciones.excepciones.EjecucionIntentoConsultaSimpleException;
import gov.sir.core.web.acciones.excepciones.MatriculaInvalidaException;
import gov.sir.core.web.acciones.excepciones.MaximoNumeroConsultasException;
import gov.sir.core.web.acciones.excepciones.MaximoNumeroIntentosException;
import gov.sir.core.web.acciones.excepciones.ProcesoNoEncontradoException;
import gov.sir.core.web.acciones.excepciones.SeleccionConsultaException;
import gov.sir.core.web.acciones.excepciones.SeleccionFolioException;
import gov.sir.core.web.acciones.excepciones.SiguienteConsultaComplejaException;
import gov.sir.core.web.acciones.excepciones.SiguienteConsultaSimpleException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultaComplejaException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultaFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultaUsuarioInternoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.Paginador;
import gov.sir.core.web.navegacion.consulta.ControlNavegacionConsulta;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos
 * de consulta  provenientes de solicitudes a través del protocolo HTTP
 * @author jmendez
 * @author jalvarez
 *
 */
public class AWConsulta extends SoporteAccionWeb {

	/**
	 * Definición de los nombres de las acciones a ser realizadas
	 */
	/** Constante que identifica el valor de la accion para selección de consulta * */
	public static final String SELECCION_CONSULTA = "SELECCION_CONSULTA";

        /** Constante que identifica el valor de la acción para la ejecución de una consulta simple */
        public static final String EJECUTA_SIMPLE = "EJECUTA_SIMPLE";
        
        /** Constante que identifica el valor de la acción para la ejecución de una consulta simple */
        public static final String CONSULTA_SIMPLIFICADA = "CONSULTA_SIMPLIFICADA";

        /** Constante que identifica el valor de la acción para el intento de ejecución de una consulta simple */
        public static final String EJECUTAR_INTENTO_SIMPLE = "EJECUTAR_INTENTO_SIMPLE";

	/** Constante que identifica el valor de la accion para la ejecución de una intento de consulta simple  */
	public static final String INTENTO_SIMPLE = "INTENTO_SIMPLE";

        /** Constante que identifica el valor de la acción para la ejecución de una consulta simple durante una calificación */
	public static final String INTENTO_SIMPLE_CALIFICACION = "INTENTO_SIMPLE_CALIFICACION";

	/** Constante que identifica el valor de la accion para la ejecución de una consulta simple  */
	public static final String SIGUIENTE_CONSULTA = "SIGUIENTE_CONSULTA";

	public static final String SIGUIENTE_CONSULTA_COMPLEJA = "SIGUIENTE_CONSULTA_COMPLEJA";

	/** Constante que identifica la acción de validar un folio */
	public static final String VALIDA_FOLIO = "VALIDA_FOLIO";

	/** Constante que identifica la acción de ver la información de un folio */
	public static final String VER_FOLIO = "VER_FOLIO";

	/** Constante que identifica la acción de ver la información de un folio durante el proceso de calificacion */
	public static final String VER_FOLIO_CALIFICACION = "VER_FOLIO_CALIFICACION";

	/** Constante que identifica la acción de ver la información de un folio durante el proceso de calificacion */
	public static final String VER_FOLIO_CORRECCION = "VER_FOLIO_CORRECCION";

	public static final String VISTA_VOLVER = "VISTA_VOLVER";
	public static final String VOLVER = "VOLVER";

	/** Constante que identifica la acción de ver la información de un folio durante el proceso de calificacion de folios */
	public static final String VER_FOLIO_CALIFICACION_FOLIO = "VER_FOLIO_CALIFICACION_FOLIO";

	/** Constante que identifica la acción de ver la información de un folio durante el proceso de traslado de folios */
	public static final String VER_FOLIO_TRASLADO = "VER_FOLIO_TRASLADO";

	/** Constante que identifica la acción de seleccionar varios folios */
	public static final String SELECCIONAR_FOLIOS = "SELECCIONAR_FOLIOS";

	/** Constante que identifica la acción de consultar un folio en el proceso de califación de un folio.  */
	public static final String SELECCIONAR_FOLIOS_CALIFICACION = "SELECCIONAR_FOLIOS_CALIFICACION";

	public static final String SELECCIONAR_RESULTADOS_CONSULTA_COMPLEJA =
		"SELECCIONAR_RESULTADOS_CONSULTA_COMPLEJA";

	/** Constante que identifica el valor de la accion para el inicio de la ejecución de una consulta compleja */
	public static final String INICIA_CONSULTA_COMPLEJA = "INICIA_CONSULTA_COMPLEJA";

	public static final String CONFIGURA_COMPLEJA = "CONFIGURA_COMPLEJA";

	/** Constante que identifica el valor de la accion para ver la selección de resultados de una consulta compleja */
	public static final String VER_SELECCION_CONSULTA_COMPLEJA = "VER_SELECCION_CONSULTA_COMPLEJA";

        /** Constante que identifica el valor de la accion para ver la selección de resultados de una consulta simple */
        public static final String VER_SELECCION_CONSULTA_SIMPLE = "VER_SELECCION_CONSULTA_SIMPLE";

	/** Constante que identifica el valor de la accion para la ejecución de una  consulta de folio 	 */
	public static final String OBSERVA_FOLIO = "OBSERVA_FOLIO";

	/** Constante que identifica la acción de consulta de folio por un usuario interno  */
	public static final String CONSULTA_FOLIO_USUARIO_INTERNO = "CONSULTA_FOLIO_USUARIO_INTERNO";

	/**
	 * Constante que identifica el valor de la accion para la solicitud de una
	 * consulta compleja
	 */
	public static final String SOLICITA_COMPLEJA = "SOLICITA_COMPLEJA";

	/**
	 * Constante que identifica el valor de la accion para la ejecución de una
	 * consulta de folio
	 */
	public static final String EJECUTA_COMPLEJA = "EJECUTA_COMPLEJA";

	/** Constante que identifica la acción de ejecutar un intento de consulta compleja  */
	public static final String EJECUTAR_INTENTO_COMPLEJA = "EJECUTAR_INTENTO_COMPLEJA";

	/** Constante que identifica la acción de entregar los resultados de una consulta compleja  */
	public static final String ENTREGAR_COMPLEJA = "ENTREGAR_COMPLEJA";

	/** Constante que identifica la acción de ejecutar la siguiente acción encontrada en un parámetro de la sesión  */
	public static final String ACCION_SIGUIENTE = "ACCION_SIGUIENTE";

	/** Constante que identifica la acción de mostrar los resultados de una consulta  */
	public static final String MOSTRAR_CONSULTA = "MOSTRAR_CONSULTA";

	/** Constante que identifica la acción de imprimir una consulta  */
	public static final String IMPRIMIR = "IMPRIMIR";

	/** Constante que identifica la acción de imprimir una consulta  */
	public static final String IMPRIMIR_SELECCION_CONSULTA_SIMPLE = "IMPRIMIR_SELECCION_CONSULTA_SIMPLE";
	
	/** Constante que identifica la acción de imprimir una consulta  */
	public static final String IMPRIMIR_SELECCION_CONSULTA_COMPLEJA = "IMPRIMIR_SELECCION_CONSULTA_COMPLEJA";
	
	/** Constante que identifica la acción de confirmar la realización de un turno  */
	public static final String CONFIRMAR_TURNO = "CONFIRMAR_TURNO";

	/** Constante que identifica la acción de negar la realización de un turno  */
	public static final String NEGAR_TURNO = "NEGAR_TURNO";

	/** Constante que identifica la acción de solicitar la reimpresión de una consulta */
	public static final String SOLICITAR_REIMPRESION = "SOLICITAR_REIMPRESION";

	/** Constante que identifica la acción de imprimir los resultados de una consulta */
	public static final String IMPRESION_CONSULTAS = "IMPRESION_CONSULTAS";

	/** Constante que identifica la acción de regresar al paso anterior durante la realización de una consulta en el
	 * proceso de calificación */
	public static final String REGRESAR_CONSULTA_CALIFICACION = "REGRESAR_CONSULTA_CALIFICACION";

	/** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

	/** Constante que identifica la acción de cancelar la consultar los detalles de un folio.*/
	public static final String CANCELAR_CONSULTA = "CANCELAR_CONSULTA";

	/** Constante que identifica la acción de aceptar la consulta los detalles de un folio.*/
	public static final String ACEPTAR_CONSULTA = "ACEPTAR_CONSULTA";

	/** Constante que identifica la acción de aceptar la consulta los detalles de un folio en calificacion.*/
	public static final String ACEPTAR_CONSULTA_CALIFICACION = "ACEPTAR_CONSULTA_CALIFICACION";


        /** identifica la accion para regresar a la correccion relaizada de un folio en el modulo de correcciones */
        // public static final String REGRESAR_MODCORRECCIONES_ACTION = "REGRESAR_MODCORRECCIONES_ACTION";

	/** Constante que identifica la acción de aceptar la consulta los detalles de un folio en calificacion.*/
	public static final String ACEPTAR_CONSULTA_CALIFICACION_FOLIO = "ACEPTAR_CONSULTA_CALIFICACION_FOLIO";

    /** Constante que identifica la acción de aceptar la consulta los detalles de un folio en el usuario especializado.*/
	public static final String ACEPTAR_CONSULTA_ESPECIALIZADO = "ACEPTAR_CONSULTA_ESPECIALIZADO";

    /** Constante que identifica la acción de aceptar la consulta los detalles de un folio en correccion*/
    public static final String ACEPTAR_CONSULTA_CORRECCION = "ACEPTAR_CONSULTA_CORRECCION";

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Definición de los nombres de los objetos a ser guardados en la sesión del
	 * usuario además de los nombres de variables en las formas
	 */

	/** Constante que identifica el valor de la variable de sesión para almacenar el tipo consulta 	 */
	public static final String TIPO_CONSULTA = "TIPO_CONSULTA";

	/**
	 * Constante que identifica el valor de la variable de sesión para almacenar
	 * el objeto de seguimiento de consultas *
	 */
	public static final String SOLICITUD_CONSULTA_VALIDATOR = "SEGUIMIENTO_CONSULTA_SIMPLE";

	/**
	 * Constante que identifica el valor de la variable de sesión para almacenar
	 * el objeto de seguimiento de consultas *
	 */
	public static final String RESULTADOS_CONSULTA = "RESULTADOS_CONSULTA";

	/**
	 * Constante que identifica el valor de la variable de sesión para almacenar
	 * una referencia a los folios seleccionados por el usuario *
	 */
	public static final String RESULTADOS_SELECCIONADOS_CONSULTA = "RESULTADOS_SELECCIONADOS_CONSULTA";

	/**
	 * Constante que identifica el valor de la variable de sesión para almacenar
	 * el paginador de resultados *
	 */
	public static final String PAGINADOR_RESULTADOS = "PAGINADOR_RESULTADOS";

	/**
	 * Constante que identifica el valor de la variable de sesión para almacenar
	 * el paginador de resultados seleccionados por el usuario *
	 */
	public static final String PAGINADOR_RESULTADOS_SELECCIONADOS = "PAGINADOR_RESULTADOS_SELECCIONADOS";

	/**
	 * Constante que identifica el valor de la variable de sesión para almacenar
	 * el paginador de resultados seleccionados por el usuario *
	 */
	public static final String CIUDADANO_SOLICITANTE = "CIUDADANO_SOLICITANTE";

	/** Constante que identifica el valor de la variable de sesión para almacenar un folio */
	public static final String FOLIO = "FOLIO";

        /**
         * Constante que identifica el valor de la variable de sesión para almacenar
         * los folios hijos
         */
        public static final String FOLIO_HIJOS = "FOLIO_HIJOS";

        /**
         * Constante que identifica el valor de la variable de sesión para almacenar
         * los folios padres
         */
        public static final String FOLIO_PADRES = "FOLIO_PADRES";

	/** Constante que identifica el valor de la variable de sesión para almacenar un turno tramite */
	public static final String TURNO_TRAMITE = "TURNO_TRAMITE";

	/** Constante que identifica el valor de la variable de sesión para almacenar un turno deuda */
	public static final String TURNO_DEUDA = "TURNO_DEUDA";

	/** Constante que identifica el valor de la variable de sesión para almacenar un usuario bloqueo */
	public static final String USUARIO_BLOQUEO = "USUARIO_BLOQUEO";

	/** Constante que identifica el valor de la variable de sesión para almacenar un folio - consultas en calificación*/
	public static final String FOLIO_CALIFICACION = "FOLIO_CALIFICACION";

	public static final String FOLIO_CORRECCION = "FOLIO_CORRECCION";


	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Constante que identifica el prefijo de los checkboxes del jsp donde
	 * asociados a las matriculas a visualizar *
	 */
	public static final String CHECKBOX_MATRICULA = "CHECKBOX_MATRICULA";

	/**
	 * Constante que identifica el campo del jsp donde se solicita el número de
	 * documento del ciudadano *
	 */
	public static final String DOCUMENTO_CIUDADANO = "DOCUMENTO_CIUDADANO";
	public static final String TIPO_DOCUMENTO_CIUDADANO = "TIPO_DOCUMENTO_CIUDADANO";

	/**
	 * Constante que identifica el campo del jsp donde se solicita el nombre del
	 * ciudadano en anotación
	 */
	public static final String NOMBRE_CIUDADANO_ANOTACION = "NOMBRE_CIUDADANO_ANOTACION";
	public static final String APELLIDO1_CIUDADANO_ANOTACION = "APELLIDO1_CIUDADANO_ANOTACION";
	public static final String APELLIDO2_CIUDADANO_ANOTACION = "APELLIDO2_CIUDADANO_ANOTACION";

	/**
	 * Constante que identifica el campo del jsp donde se solicita el Nombre
	 * Jurídico en Anotación
	 */
	public static final String NOMBRE_JURIDICO_ANOTACION = "NOMBRE_JURIDICO_ANOTACION";

	/**
	 * Constante que identifica el campo del jsp donde se solicita la Dirección
	 * en Folio
	 */
	public static final String DIRECCION_EN_FOLIO = "DIRECCION_EN_FOLIO";

	/**
	 * Constante que identifica el campo del jsp donde se solicita el Tipo de
	 * predio
	 */
	public static final String TIPO_DE_PREDIO = "TIPO_DE_PREDIO";

	/**
	 * Constante que identifica el campo del jsp donde se solicita el Tipo de
	 * EJE
	 */
	public static final String TIPO_DE_EJE = "TIPO_DE_EJE";

	/**
	 * Constante que identifica el campo del jsp donde se solicita el valor de
	 * eje
	 */
	public static final String VALOR_DEL_EJE = "VALOR_DEL_EJE";

        public static final String CONSULTAS_FOLIOCORRECCION_DISPLAYBYID_ACTION = "CONSULTAS_FOLIOCORRECCION_DISPLAYBYID_ACTION";
        public static final String CONSULTAS_FOLIOCORRECCION_BACK_ACTION        = "CONSULTAS_FOLIOCORRECCION_BACK_ACTION";


	public static final String ULTIMA_BUSQUEDA = "ULTIMA_BUSQUEDA";
	
	public static final String FOLIOS_DERIVADO_PADRE = "FOLIOS_DERIVADO_PADRE";
	
	public static final String FOLIOS_DERIVADO_HIJO = "FOLIOS_DERIVADO_HIJO";
	
		public static final String CONSULTADO = "CONSULTADO";
	
  /**
   * Decide a partir de la informacion recibida en el request, el tipo de evento
   * de negocio que debe generar.
   *
   * @param request HttpServletRequest
   * @throws AccionWebException
   * @return Evento
   */
  public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

                // ----------------------------------------------------------------------
                if( CONSULTAS_FOLIOCORRECCION_DISPLAYBYID_ACTION.equals( accion ) ) {
                    return doConsultas_FolioCorreccion_DisplayById( request );
                }
                else if( CONSULTAS_FOLIOCORRECCION_BACK_ACTION.equals( accion ) ) {
                    return doConsultas_FolioCorreccion_Back( request ); // :null: no hay forma de llamarla, estando en calificacion
                }
                // ----------------------------------------------------------------------
		else if (accion.equals(SELECCION_CONSULTA)) {
			return iniciarConsulta(request);
		} else if (accion.equals(SIGUIENTE_CONSULTA)) {
			return siguienteBusquedaSimple(request);
		} else if (accion.equals(SIGUIENTE_CONSULTA_COMPLEJA)) {
			return seleccionarFolios(request);
		} else if (accion.equals(EJECUTA_SIMPLE)) {
                        return ejecutaConsultaSimple(request);
		} else if (accion.equals(INTENTO_SIMPLE) || accion.equals(INTENTO_SIMPLE_CALIFICACION)
                           || accion.equals(EJECUTAR_INTENTO_SIMPLE) ) {
			return ejecutarIntentoConsultaSimple(request);
		} else if (
			accion.equals(SELECCIONAR_FOLIOS)
				|| accion.equals(SELECCIONAR_RESULTADOS_CONSULTA_COMPLEJA)
				|| accion.equals(SELECCIONAR_FOLIOS_CALIFICACION)) {
			return seleccionarFolios(request);
		} else if (accion.equals(VER_FOLIO) || accion.equals(VER_FOLIO_CALIFICACION)) {
			return verFolio(request);

		} else if (accion.equals(VER_FOLIO_CORRECCION)) {
			request.getSession().setAttribute(
				VISTA_VOLVER,
				request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL));
			return verFolio(request);

		} else if (accion.equals(VOLVER)) {
			return null;

		} else if (accion.equals(VER_FOLIO_TRASLADO)) {
			return verFolio(request);
		} else if (accion.equals(VER_FOLIO_CALIFICACION_FOLIO)) {
			return verFolioCalificacionFolio(request);

		} else if (accion.equals(VALIDA_FOLIO)) {
			return validarFolio(request);
		} else if (accion.equals(EJECUTA_COMPLEJA)) {
			return ejecutaConsultaCompleja(request);
		} else if (accion.equals(EJECUTAR_INTENTO_COMPLEJA)) {
			return ejecutarIntentoConsultaCompleja(request);
		} else if (accion.equals(AWConsulta.INICIA_CONSULTA_COMPLEJA)) {
			SolicitudConsultaValidator validador =
				(SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
			Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
			SolicitudConsulta sol = (SolicitudConsulta) turno.getSolicitud();
			if (sol.getTipoConsulta().getIdTipoConsulta().equals(TipoConsulta.TIPO_COMPLEJO)
				&& !validador.esValidaOtraBusquedaCompleja()) {
				throw new SiguienteConsultaComplejaException();
			}
			limpiarParametrosConsultaSimple(session);
			return null;
		} else if (accion.equals(AWConsulta.CONFIGURA_COMPLEJA)) {
			return configuraConsultaCompleja(request);
		} else if (accion.equals(AWConsulta.ACCION_SIGUIENTE)) {
			if (session.getAttribute(AWConsulta.ACCION_SIGUIENTE) != null) {
				String siguientePaso = (String) session.getAttribute(AWConsulta.ACCION_SIGUIENTE);
				if (siguientePaso.equals(AWConsulta.CONFIRMAR_TURNO)) {
					return confirmar(request);
				}
                                return null;
                        }
                        return null;
		} else if (accion.equals(AWConsulta.VER_SELECCION_CONSULTA_COMPLEJA)) {
                        crearListaInicialResultados(session);
			session.setAttribute(
				AWConsulta.ACCION_SIGUIENTE,
				AWConsulta.VER_SELECCION_CONSULTA_COMPLEJA);
			return null;
                } else if (accion.equals(AWConsulta.VER_SELECCION_CONSULTA_SIMPLE)) {
                        crearListaInicialResultados(session);
                        session.setAttribute(
                                AWConsulta.ACCION_SIGUIENTE,
                                AWConsulta.VER_SELECCION_CONSULTA_SIMPLE);
                        return null;
                }else if(accion.equals(AWConsulta.IMPRIMIR_SELECCION_CONSULTA_SIMPLE)){
                	crearListaInicialResultados(session);
                    session.setAttribute(
                            AWConsulta.ACCION_SIGUIENTE,
                            AWConsulta.VER_SELECCION_CONSULTA_SIMPLE);
                    return imprimir(request);
                }else if(accion.equals(AWConsulta.IMPRIMIR_SELECCION_CONSULTA_COMPLEJA)){
                	crearListaInicialResultados(session);
                	session.setAttribute(
            				AWConsulta.ACCION_SIGUIENTE,
            				AWConsulta.VER_SELECCION_CONSULTA_COMPLEJA);
                    return imprimir(request);
                }
                else if (accion.equals(AWConsulta.IMPRIMIR)) {
			return imprimir(request);
		} else if (accion.equals(AWConsulta.CONFIRMAR_TURNO)) {
			return confirmar(request);
		} else if (accion.equals(AWConsulta.NEGAR_TURNO)) {
			return negar(request);
		} else if (accion.equals(AWConsulta.CONSULTA_FOLIO_USUARIO_INTERNO)) {
			session.setAttribute(AWConsulta.ACCION_SIGUIENTE, AWConsulta.CONSULTA_FOLIO_USUARIO_INTERNO);
			return verFolio(request);
		} else if (accion.equals(AWConsulta.CONSULTAR_FOLIO)) {
			return consultarDetalleFolio(request);
		} else if (accion.equals(AWConsulta.CANCELAR_CONSULTA)) {
			return null;
		} else if (accion.equals(AWConsulta.ACEPTAR_CONSULTA) || accion.equals(AWConsulta.ACEPTAR_CONSULTA_CALIFICACION)  || accion.equals(AWConsulta.ACEPTAR_CONSULTA_CALIFICACION_FOLIO)) {
			return aceptarConsulta(request);
		} else if (accion.equals(AWConsulta.REGRESAR_CONSULTA_CALIFICACION)) {
			return null;
		} else {
			throw new AccionInvalidaException("La acción " + accion + " no es válida.");
		}
	}

    /**
     * doConsultas_FolioCorreccion_Back
     * WrapEvent
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento
    doConsultas_FolioCorreccion_Back( HttpServletRequest request )
    throws AccionWebException {

        // call function -----------------------------------------

        Evento result = null;
        result = aceptarConsulta( request );

        // -------------------------------------------------------

        return result;

    } // :end method


    /**
     * doConsultas_FolioCorreccion_DisplayById
     *
     * WrapEvent
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento
    doConsultas_FolioCorreccion_DisplayById( HttpServletRequest request )
    throws AccionWebException {

        // collect params
        HttpSession session = request.getSession();
        String param_VistaActual = (String)session.getAttribute( SMARTKeys.VISTA_ACTUAL );


        // call function -----------------------------------------

        Evento result = null;
        // result = verFolio( request );
        result = verFolioCalificacionFolio( request );

        // -------------------------------------------------------


        // set params
        session.setAttribute( VISTA_VOLVER, param_VistaActual );

        return result;

    } // :end method



    /**
         * Este métido permite obtener los resultados seleccionados
         * de las búsquedas una consulta
         * @param session HttpSession
         */
        private void crearListaInicialResultados(HttpSession session) {
            /*if (session.getAttribute(RESULTADOS_SELECCIONADOS_CONSULTA) == null) {
                Hashtable foliosSeleccionados = new Hashtable();
                Paginador paginador_resultados = null;
                Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
                SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
                List bs = solicitud.getBusquedas();
                List folios = new ArrayList();

                for (int i = 0; i < bs.size(); i++) {
                    //Folio f = ((SolicitudFolio)solicitudFolios.get(i)).getFolio();
                    //foliosSeleccionados.put(f.getIdMatricula(), f);
                    folios.addAll(((Busqueda) bs.get(i)).getResultadosFolios());
                }

				paginador_resultados = new Paginador(folios);
                session.setAttribute(RESULTADOS_SELECCIONADOS_CONSULTA, foliosSeleccionados);
                session.setAttribute(PAGINADOR_RESULTADOS_SELECCIONADOS, paginador_resultados);
				session.setAttribute(
						AWConsulta.ACCION_SIGUIENTE,
						AWConsulta.VER_SELECCION_CONSULTA_SIMPLE);

            }*/

			if(session.getAttribute(AWConsulta.RESULTADOS_SELECCIONADOS_CONSULTA)==null){
				Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
				SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
				List busquedas = solicitud.getBusquedas();
				if(!busquedas.isEmpty()){
					int lastIndex = busquedas.size()-1;
					//Busqueda ultimaBusqueda = (Busqueda)busquedas.get(lastIndex);

					List foliosSeleccionados = new Vector();
					Hashtable foliosSeleccionadosTable = new Hashtable();
					List solicitudesFolio = solicitud.getSolicitudFolios();
					for(Iterator iter = solicitudesFolio.iterator(); iter.hasNext();){
						SolicitudFolio solicitudFolio = (SolicitudFolio)iter.next();
						ResultadoFolio resultadoFolio = new ResultadoFolio();
						Folio folio = solicitudFolio.getFolio();
						resultadoFolio.setFolio(folio);
						foliosSeleccionados.add(resultadoFolio );
						foliosSeleccionadosTable.put(folio.getIdMatricula(),resultadoFolio);
					}
				session.setAttribute(AWConsulta.RESULTADOS_SELECCIONADOS_CONSULTA, foliosSeleccionadosTable);
				session.setAttribute(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS, new Paginador(foliosSeleccionados));
				}
			}

        }

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de inicio de
         * consultas
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         * @throws AccionWebException
         */
        private EvnConsulta iniciarConsulta(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		limpiarParametrosConsultaSimple(session);
		limpiarDatosSolicitante(session);
		limpiarConsulta(session);
		SeleccionConsultaException exception = new SeleccionConsultaException();
		String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
		if ((alcanceConsulta == null) || (alcanceConsulta.trim().length() == 0)) {
			exception.addError("Debe indicar si la consulta es de alcance local o nacional.");
		}

		String tipoConsulta = request.getParameter(TIPO_CONSULTA);
		if ((tipoConsulta == null) || (tipoConsulta.trim().length() == 0)) {
			exception.addError("Debe seleccionar un tipo de consulta a realizar.");
		} else {
			if (!(tipoConsulta.equals(TipoConsulta.TIPO_SIMPLE)
				|| tipoConsulta.equals(TipoConsulta.TIPO_FOLIOS)
				|| tipoConsulta.equals(TipoConsulta.TIPO_COMPLEJO)
				|| tipoConsulta.equals(TipoConsulta.TIPO_FOLIOS_CALIFICACION)
				|| tipoConsulta.equals(TipoConsulta.TIPO_SIMPLE_CALIFICACION))) {
				exception.addError(tipoConsulta + " no es un tipo de consulta válida.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);
		session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);
		SolicitudConsultaValidator validadorSolicitudConsulta =
			getValidadorConsulta(request, true, exception);
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsulta eventoConsulta = new EvnConsulta(usuario, EvnConsulta.GENERAR_CONSULTA);
		eventoConsulta.setSolicitudConsulta(validadorSolicitudConsulta.getSolicitudConsulta());
		return eventoConsulta;
	}

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de para generar una
         * nueva búsqueda simple.
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         * @throws AccionWebException
         */
        private EvnConsulta siguienteBusquedaSimple(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

              SolicitudConsultaValidator validador =
                      (SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
              if (validador!=null){
	            	  if (!validador.esValidaOtraBusquedaSimple()) {
	                      throw new SiguienteConsultaSimpleException();
	              }
              }
              limpiarParametrosConsultaSimple(session);
              return null;
	}


        /**
         * Ejecucion de una Búsqueda cuando la consulta es de tipo simple
         *
         * @param request <CODE>HttpServletRequest</CODE> con los parámetros de la
         *   consulta
         * @return el evento consulta generado. Este evento debe ser del tipo
         *   <CODE>EvnConsulta</CODE>
         * @throws AccionWebException
         */
        private EvnConsulta ejecutaConsultaSimple(HttpServletRequest request) throws AccionWebException {
                      HttpSession session = request.getSession();
                      EvnConsulta eventoConsulta = null;
                      EjecucionConsultaSimpleException excepcion = new EjecucionConsultaSimpleException();
                      session.setAttribute(AWConsulta.TIPO_CONSULTA, TipoConsulta.TIPO_SIMPLE);
                      SolicitudConsultaValidator validador = getValidadorConsulta(request, false, excepcion);

					  Busqueda busqueda = null;

                      try {
                              Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                              SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
                              validador.setSolicitudConsulta(solicitud);
                              Liquidacion l = (Liquidacion)solicitud.getLiquidaciones().get(0);
                              String idCirculo = null;
                              boolean alcanceLocal = ((LiquidacionTurnoConsulta)l).getAlcanceGeografico().getNombre().equals(CAlcanceGeografico.LOCAL);
                              if (alcanceLocal) {
                            	  idCirculo = solicitud.getCirculo().getIdCirculo();  
                              }
                              else{
	                              if(request.getParameter(CCirculo.ID_CIRCULO)!=null){
	                            	  idCirculo = (String) request.getParameter(CCirculo.ID_CIRCULO);
	                            	  session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
	                              }
                              }
                              busqueda = validador.siguienteBusqueda(idCirculo);

                              // BUG 6223
                              validarCriterios(request, session);

                              poblarBusqueda(request, session, excepcion,
                                             busqueda,validador);
                             /* if(!alcanceLocal){
                            	  String matricubusqueda.getMatricula()
                            	  if()
                              }*/
                              if (excepcion.getErrores().size() > 0) {
                                  //validador.getSolicitudConsulta().removeBusqueda(busqueda);
                              }
                      } catch (MaximoNumeroConsultasException e) {
                              excepcion.addError(e.getMessage());
                      }

                      if (excepcion.getErrores().size() > 0) {
                              throw excepcion;
                      }
                      Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
                      //////////////////////////////////////////////////////////
                      eventoConsulta = new EvnConsulta(usuario, EvnConsulta.NUEVA_BUSQUEDA);
                      eventoConsulta.setSolicitudConsulta(validador.getSolicitudConsulta());
                      eventoConsulta.setBusqueda(busqueda);
                      session.setAttribute(
                              AWConsulta.ACCION_SIGUIENTE,
                              ControlNavegacionConsulta.RESULTADO_CONSULTA_COMPLEJA);
                      return eventoConsulta;
              }


        /**
         * Ejecucion de un intento de Búsqueda cuando la consulta es de tipo simple
         *
         * @param request <CODE>HttpServletRequest</CODE> con los parámetros de la
         *   consulta
         * @return el evento consulta generado. Este evento debe ser del tipo
         *   <CODE>EvnConsulta</CODE>
         * @throws AccionWebException
         */
        private EvnConsulta ejecutarIntentoConsultaSimple(HttpServletRequest request) throws AccionWebException {

		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();
		EvnConsulta eventoConsulta = null;
		EjecucionIntentoConsultaSimpleException excepcion = new EjecucionIntentoConsultaSimpleException();
		try {
			Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
			session.setAttribute(AWConsulta.TIPO_CONSULTA, TipoConsulta.TIPO_SIMPLE);
			SolicitudConsultaValidator validador = getValidadorConsulta(request, false, excepcion);

			Busqueda nuevaBusqueda = new Busqueda();

                        // BUG 6223
                        validarCriterios(request, session);

			poblarBusqueda(request, session, excepcion, nuevaBusqueda, validador);

			Busqueda ultimaBusqueda = (Busqueda) session.getAttribute(ULTIMA_BUSQUEDA);

			Busqueda busquedaValidada = validador.validarBusqueda(nuevaBusqueda, ultimaBusqueda);
			//////////////////////////////////////////////////////////
			if (busquedaValidada.getIdBusqueda() == null) {
				if (accion.equals(INTENTO_SIMPLE_CALIFICACION)) {
					eventoConsulta =
						new EvnConsulta(usuarioAuriga, EvnConsulta.NUEVA_BUSQUEDA_CALIFICACION);
				} else {
					eventoConsulta = new EvnConsulta(usuarioAuriga, EvnConsulta.NUEVA_BUSQUEDA);
				}
			} else {
				if (accion.equals(INTENTO_SIMPLE_CALIFICACION)) {
					eventoConsulta =
						new EvnConsulta(
							usuarioAuriga,
							EvnConsulta.INTENTO_SIMPLE_CALIFICACION,
							busquedaValidada);
				} else {
					eventoConsulta =
						new EvnConsulta(usuarioAuriga, EvnConsulta.INTENTO_SIMPLE, busquedaValidada);
				}
			}

			eventoConsulta.setSolicitudConsulta(validador.getSolicitudConsulta());
			session.setAttribute(AWConsulta.ACCION_SIGUIENTE, AWConsulta.INTENTO_SIMPLE);
		} catch (MaximoNumeroConsultasException e) {
			excepcion.addError(e.getMessage());
		} catch (MaximoNumeroIntentosException e) {
			excepcion.addError(e.getMessage());
		} catch (CadenaNoPermitidaException e) {
			excepcion.addError(e.getMessage());
		}

		if (excepcion.getErrores().size() > 0) {
			throw excepcion;
		}
        return eventoConsulta;
	}

        /**
         * Ejecucion de una Búsqueda cuando la consulta es de tipo complejo
         *
         * @param request <CODE>HttpServletRequest</CODE> con los parámetros de la
         *   consulta
         * @return el evento consulta generado. Este evento debe ser del tipo
         *   <CODE>EvnConsulta</CODE>
         * @throws AccionWebException
         */
        private EvnConsulta ejecutaConsultaCompleja(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		EvnConsulta eventoConsulta = null;
		EjecucionConsultaComplejaException excepcion = new EjecucionConsultaComplejaException();
		session.setAttribute(AWConsulta.TIPO_CONSULTA, TipoConsulta.TIPO_COMPLEJO);
                SolicitudConsultaValidator validador = getValidadorConsulta(request, false, excepcion);

		Busqueda nuevaBusqueda = null;

		try {
			Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
			SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
			validador.setSolicitudConsulta(solicitud);
			String idCirculo = solicitud.getCirculo().getIdCirculo();
            if(request.getParameter(CCirculo.ID_CIRCULO)!=null){
          	  idCirculo = (String) request.getParameter(CCirculo.ID_CIRCULO);
          	  session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
            }
            nuevaBusqueda = validador.siguienteBusqueda(idCirculo);
			
                        // BUG 6224
                        validarCriterios(request, session);

			poblarBusqueda(request, session, excepcion, nuevaBusqueda, validador);
                        if (excepcion.getErrores().size() > 0) {
                            //validador.getSolicitudConsulta().removeBusqueda(busqueda);
                        }
		} catch (MaximoNumeroConsultasException e) {
			excepcion.addError(e.getMessage());
		}

		if (excepcion.getErrores().size() > 0) {
			throw excepcion;
		}
                Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
                //////////////////////////////////////////////////////////
                eventoConsulta = new EvnConsulta(usuario, EvnConsulta.NUEVA_BUSQUEDA);
                eventoConsulta.setSolicitudConsulta(validador.getSolicitudConsulta());
                eventoConsulta.setBusqueda(nuevaBusqueda);
                session.setAttribute(
                        AWConsulta.ACCION_SIGUIENTE,
                        ControlNavegacionConsulta.RESULTADO_CONSULTA_COMPLEJA);
                return eventoConsulta;
	}

        /**
         * Ejecucion de un intento de Búsqueda cuando la consulta es de tipo Complejo
         *
         * @param request <CODE>HttpServletRequest</CODE> con los parámetros de la
         *   consulta
         * @return el evento consulta generado. Este evento debe ser del tipo
         *   <CODE>EvnConsulta</CODE>
         * @throws AccionWebException
         */
        private EvnConsulta ejecutarIntentoConsultaCompleja(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		EvnConsulta eventoConsulta = null;
		EjecucionIntentoConsultaComplejaException excepcion =
			new EjecucionIntentoConsultaComplejaException();
		try {
			Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
			session.setAttribute(AWConsulta.TIPO_CONSULTA, TipoConsulta.TIPO_COMPLEJO);
			SolicitudConsultaValidator validador = getValidadorConsulta(request, false, excepcion);

			Busqueda nuevaBusqueda = new Busqueda();

                        // BUG 6224
                        validarCriterios(request, session);

			poblarBusqueda(request, session, excepcion, nuevaBusqueda, validador);

			Busqueda ultimaBusqueda = (Busqueda) session.getAttribute(ULTIMA_BUSQUEDA);

			Busqueda busquedaValidada = validador.validarBusqueda(nuevaBusqueda, ultimaBusqueda);

			eventoConsulta =
				new EvnConsulta(usuarioAuriga, EvnConsulta.INTENTO_SIMPLE, busquedaValidada);

			eventoConsulta.setSolicitudConsulta(validador.getSolicitudConsulta());
		} catch (MaximoNumeroConsultasException e) {
			excepcion.addError(e.getMessage());
		} catch (MaximoNumeroIntentosException e) {
			excepcion.addError(e.getMessage());
		} catch (CadenaNoPermitidaException e) {
			excepcion.addError(e.getMessage());
		}

		if (excepcion.getErrores().size() > 0) {
			throw excepcion;
		}
                return eventoConsulta;
	}

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de impresión de
         * consultas
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         * @throws AccionWebException
         */
        private EvnConsulta imprimir(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		String accion = request.getParameter(WebKeys.ACCION).trim();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
		gov.sir.core.negocio.modelo.Usuario usuSir= (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
                EvnConsulta eventoConsulta = new EvnConsulta(usuario, EvnConsulta.IMPRIMIR);


                // Bug 0003385 (impresion opcional cuando es consulta exenta)
                boolean enabled_SolicitudConsultasTipoConsultaExenta;
                boolean enabled_SolicitudConsultasTipoConsultaExentaPrint;
                enabled_SolicitudConsultasTipoConsultaExenta = false;
                enabled_SolicitudConsultasTipoConsultaExentaPrint = false;

                // ---------------------------------------------------------------------------------
                consultaExenta_Enabled : {

                  gov.sir.core.negocio.modelo.Turno             local_Turno;
                  gov.sir.core.negocio.modelo.Solicitud         local_Solicitud;
                  gov.sir.core.negocio.modelo.SolicitudConsulta local_SolicitudConsulta;
                  final String LOCAL_ID_CONSULTATIPOEXENTO       = "5";

                  enabled_SolicitudConsultasTipoConsultaExenta = false;

                  local_Turno = turno;
                  local_Solicitud = local_Turno.getSolicitud();

                  if( local_Solicitud instanceof gov.sir.core.negocio.modelo.SolicitudConsulta ) {
                    local_SolicitudConsulta = (gov.sir.core.negocio.modelo.SolicitudConsulta)( local_Solicitud );
                    gov.sir.core.negocio.modelo.TipoConsulta local_SolicitudConsultaTipoConsulta = local_SolicitudConsulta.getTipoConsulta();

                    if( null != local_SolicitudConsultaTipoConsulta ) {

                      if( LOCAL_ID_CONSULTATIPOEXENTO.equals( local_SolicitudConsultaTipoConsulta.getIdTipoConsulta() ) ) {

                        enabled_SolicitudConsultasTipoConsultaExenta = true;

                      } // if

                    } // if

                  } // if

                } // :consultaExenta_Enabled
                // ---------------------------------------------------------------------------------

                // ---------------------------------------------------------------------------------
                consultaExenta_PrintEnabled: {

                   final String CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO = "CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO";
                   final String CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO_CHECKED = "CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO_CHECKED";

                   if( enabled_SolicitudConsultasTipoConsultaExenta ) {
                      String local_SolicitudConsultaTipoExentaEnabledPrint;

                      local_SolicitudConsultaTipoExentaEnabledPrint
                       = request.getParameter( CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO );

                     if(( (  null != local_SolicitudConsultaTipoExentaEnabledPrint )
                        &&( !( "".equals( local_SolicitudConsultaTipoExentaEnabledPrint.trim() ) ) ) )
                        ||accion.equals(AWConsulta.IMPRIMIR_SELECCION_CONSULTA_COMPLEJA)) {

                       if(( CONSULTATIPOEXENTA_ENABLEDPRINTCONSULTAINDICEPROPIETARIO_CHECKED.equals( local_SolicitudConsultaTipoExentaEnabledPrint ))
                    		   ||(accion.equals(AWConsulta.IMPRIMIR_SELECCION_CONSULTA_COMPLEJA))) {

                          enabled_SolicitudConsultasTipoConsultaExentaPrint = true;

                       } // if

                     } // if

                   } // if



                } // :consultaExenta_PrintEnabled

                if( enabled_SolicitudConsultasTipoConsultaExenta ) {
                   eventoConsulta.setEnabled_SolicitudConsultasTipoConsultaExentaPrint( enabled_SolicitudConsultasTipoConsultaExentaPrint );
                   eventoConsulta.setEnabled_SolicitudConsultasTipoConsultaExenta( enabled_SolicitudConsultasTipoConsultaExenta );
                } // :if




		eventoConsulta.setTurno(turno);
		eventoConsulta.setFase(fase);
		eventoConsulta.setEstacion(estacion);
                eventoConsulta.setUID(request.getSession().getId());
		String respuestaWF = "__";
		eventoConsulta.setUsuarioNeg(usuSir);
		eventoConsulta.setRespuestaWF(respuestaWF);
		eventoConsulta.setCirculo(circulo);
		
		//SIR-165
		if(solicitud.getTipoConsulta().getNombre().equals(TipoConsulta.CONSTANCIA)){
			
			Ciudadano ciudadano = new Ciudadano();
			ciudadano.setTipoDoc((String)session.getAttribute(TIPO_DOCUMENTO_CIUDADANO));
			ciudadano.setDocumento((String)session.getAttribute(DOCUMENTO_CIUDADANO));
			ciudadano.setNombre((String)session.getAttribute(NOMBRE_CIUDADANO_ANOTACION));
			ciudadano.setApellido1((String)session.getAttribute(APELLIDO1_CIUDADANO_ANOTACION));
			ciudadano.setApellido2((String)session.getAttribute(APELLIDO2_CIUDADANO_ANOTACION));
			
			eventoConsulta.setCiudadano(ciudadano);
		}
		
		return eventoConsulta;
	}

        /**
         * Genera un evento para el avance del turno del proceso actual de consulta
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         * @throws AccionWebException
         */
        private EvnConsulta confirmar(HttpServletRequest request) throws AccionWebException {
		EvnConsulta eventoConsulta = getEventoAvance(request);
		eventoConsulta.setRespuestaWF(EvnConsulta.CONFIRMAR);
		return eventoConsulta;
	}

        /**
         * Genera un evento para la negación del avance del turno del proceso actual de
         * consulta
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         * @throws AccionWebException
         */
        private EvnConsulta negar(HttpServletRequest request) throws AccionWebException {
		EvnConsulta eventoConsulta = getEventoAvance(request);
		eventoConsulta.setRespuestaWF(EvnConsulta.NEGAR);
		return eventoConsulta;
	}

        // BUG 6223
        /**
         * Valida los criterios de búsqueda, teniendo en cuenta que el número
         * de matrícula es excluyente con los demás para la misma búsqueda
         *
         * @param request HttpServletRequest
         * @param session HttpSession
         * @throws CriteriosInvalidosException
         */
        private void validarCriterios (HttpServletRequest request,
                                       HttpSession session) throws CriteriosInvalidosException {

          String idMatricula = request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA)!=null?request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA):"";
            /**
            * @autor Edgar Lora
            * @mantis 11987
            */
          if(idMatricula != null && idMatricula.length() > 0){
              ValidacionesSIR validacionesSIR = new ValidacionesSIR();
            try {
                if(validacionesSIR.isEstadoFolioBloqueado(idMatricula)){
                    throw new CriteriosInvalidosException("La matricula que desea consultar tiene como estado 'Bloqueado'.");
                }
            } catch (GeneralSIRException ex) {
                if(ex.getMessage() != null){
                    throw new CriteriosInvalidosException(ex.getMessage());
                }
            }
          }
          String documentoCiudadano = request.getParameter(DOCUMENTO_CIUDADANO).trim();
          String nombreCiudadano = request.getParameter(NOMBRE_CIUDADANO_ANOTACION).trim();
          String apellido1Ciudadano = request.getParameter(APELLIDO1_CIUDADANO_ANOTACION).trim();
          String apellido2Ciudadano = request.getParameter(APELLIDO2_CIUDADANO_ANOTACION).trim();

          String nombreJuridicoAnotacion = request.getParameter(NOMBRE_JURIDICO_ANOTACION)!=null?request.getParameter(NOMBRE_JURIDICO_ANOTACION).trim():"";
          String direccionFolio = request.getParameter(DIRECCION_EN_FOLIO)!=null?request.getParameter(DIRECCION_EN_FOLIO).trim():"";
          String numeroCatastralFolio = request.getParameter(CFolio.CODIGO_CATASTRAL_FOLIO)!=null?request.getParameter(CFolio.CODIGO_CATASTRAL_FOLIO).trim():"";
          String tipoEje = request.getParameter(TIPO_DE_EJE)!=null?request.getParameter(TIPO_DE_EJE):WebKeys.SIN_SELECCIONAR;
          String valorEje = request.getParameter(VALOR_DEL_EJE)!=null?request.getParameter(VALOR_DEL_EJE).trim():"";

          Busqueda ultimaBusqueda = (Busqueda) session.getAttribute(ULTIMA_BUSQUEDA);
          if (ultimaBusqueda == null) {
            if (!idMatricula.equals("")
                && ! (documentoCiudadano.equals("")
                      && nombreCiudadano.equals("")
                      && apellido1Ciudadano.equals("")
                      && apellido2Ciudadano.equals("")
                      && nombreJuridicoAnotacion.equals("")
                      && direccionFolio.equals("")
                      && numeroCatastralFolio.equals("")
                      && valorEje.equals("")
                      && tipoEje.equals(WebKeys.SIN_SELECCIONAR))) {
              throw new CriteriosInvalidosException(
                  "No está permitido combinar el número de matrícula inmobiliaria con otros criterios");

            }
          }
          else {
            if (!idMatricula.equals("")
                &&
                ((ultimaBusqueda.getNumeroDocCiudadano() != null &&
                  !ultimaBusqueda.getNumeroDocCiudadano().equals(""))
                 ||
                 (ultimaBusqueda.getNombreCiudadano() != null &&
                  !ultimaBusqueda.getNombreCiudadano().equals(""))
                 ||
                 (ultimaBusqueda.getApellido1Ciudadano() != null &&
                  !ultimaBusqueda.getApellido1Ciudadano().equals(""))
                 ||
                 (ultimaBusqueda.getApellido2Ciudadano() != null &&
                  !ultimaBusqueda.getApellido2Ciudadano().equals(""))
                 ||
                 (ultimaBusqueda.getNombreNaturalezaJuridica() != null &&
                  !ultimaBusqueda.getNombreNaturalezaJuridica().equals(""))
                 ||
                 (ultimaBusqueda.getDireccion() != null &&
                  !ultimaBusqueda.getDireccion().equals(""))
                 ||
                 (ultimaBusqueda.getNumeroCatastral() != null &&
                  !ultimaBusqueda.getNumeroCatastral().equals(""))
                 ||
                 (ultimaBusqueda.getValorEje() != null &&
                  !ultimaBusqueda.getValorEje().equals(""))
                 ||
                 (ultimaBusqueda.getIdEje() != null &&
                  !ultimaBusqueda.getIdEje().equals("")))) {
              throw new CriteriosInvalidosException("No está permitido combinar el número de matricula inmobiliaria con el criterio que ha utilizado");
            }
            else if ((ultimaBusqueda.getMatricula() != null &&
                      !ultimaBusqueda.getMatricula().equals(""))
                     && ! (documentoCiudadano.equals("")
                           && nombreCiudadano.equals("")
                           && apellido1Ciudadano.equals("")
                           && apellido2Ciudadano.equals("")
                           && nombreJuridicoAnotacion.equals("")
                           && direccionFolio.equals("")
                           && numeroCatastralFolio.equals("")
                           && valorEje.equals("")
                           && tipoEje.equals(WebKeys.SIN_SELECCIONAR))) {
              throw new CriteriosInvalidosException("No está permitido combinar el criterio que ha utilizado con el número de matrícula inmobiliaria");
            }
          }

        }

        /**
         * Genera un evento para la obtención de un evento de consulta a partir del
         * request
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         */
        private EvnConsulta getEventoAvance(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuSir= (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		EvnConsulta eventoConsulta = new EvnConsulta(usuario, EvnConsulta.AVANZAR_TURNO);
		eventoConsulta.setTurno(turno);
		eventoConsulta.setFase(fase);
		eventoConsulta.setEstacion(estacion);
		eventoConsulta.setUsuarioNeg(usuSir);
		return eventoConsulta;
	}

        /**
         * Método que Toma la informacion del request para generar una nueva búsqueda
         *
         * @param request HttpServletRequest
         * @param session HttpSession
         * @param excepcion ValidacionParametrosException
         * @param busqueda Busqueda
         */
        private void poblarBusqueda(
		HttpServletRequest request,
		HttpSession session,
		ValidacionParametrosException excepcion,
		Busqueda busqueda, SolicitudConsultaValidator validador) {

        String idMatricula = request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA)!=null?request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA):"";
        List ejes = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
		String documentoCiudadano = request.getParameter(DOCUMENTO_CIUDADANO).trim();
		String tipoDocumentoCiudadano = request.getParameter(TIPO_DOCUMENTO_CIUDADANO).trim();
		String nombreCiudadano = request.getParameter(NOMBRE_CIUDADANO_ANOTACION).trim();
		String apellido1Ciudadano = request.getParameter(APELLIDO1_CIUDADANO_ANOTACION).trim();
		String apellido2Ciudadano = request.getParameter(APELLIDO2_CIUDADANO_ANOTACION).trim();

		String nombreJuridicoAnotacion = request.getParameter(NOMBRE_JURIDICO_ANOTACION)!=null?request.getParameter(NOMBRE_JURIDICO_ANOTACION).trim():"";
		
		String direccionFolio = request.getParameter(DIRECCION_EN_FOLIO)!=null?request.getParameter(DIRECCION_EN_FOLIO).trim():"";
        String numeroCatastralFolio = request.getParameter(CFolio.CODIGO_CATASTRAL_FOLIO)!=null?request.getParameter(CFolio.CODIGO_CATASTRAL_FOLIO).trim():"";
        String tipoEje = request.getParameter(TIPO_DE_EJE)!=null?request.getParameter(TIPO_DE_EJE):WebKeys.SIN_SELECCIONAR;
        String valorEje = request.getParameter(VALOR_DEL_EJE)!=null?request.getParameter(VALOR_DEL_EJE).trim():"";
		
		String tipoPredio = request.getParameter(TIPO_DE_PREDIO);
		String idCirculo =(String) request.getParameter(CCirculo.ID_CIRCULO);
		if (idMatricula.equals("")
                        && documentoCiudadano.equals("")
			&& nombreCiudadano.equals("")
			&& apellido1Ciudadano.equals("")
			&& apellido2Ciudadano.equals("")
			&& nombreJuridicoAnotacion.equals("")
			&& direccionFolio.equals("")
			&& numeroCatastralFolio.equals("")
			&& direccionFolio.equals("")
			&& valorEje.equals("")
			&& tipoEje.equals(WebKeys.SIN_SELECCIONAR)) {
			excepcion.addError("Debe digitar al menos un valor para la consulta");
		}

		if ((idMatricula.equals("")
			&& documentoCiudadano.equals("")
			&& nombreCiudadano.equals("")
			&& apellido1Ciudadano.equals("")
			&& apellido2Ciudadano.equals("")
			&& direccionFolio.equals("")
			&& numeroCatastralFolio.equals("")
			&& direccionFolio.equals("")
			&& valorEje.equals("")
			&& tipoEje.equals(WebKeys.SIN_SELECCIONAR))

			&& ! nombreJuridicoAnotacion.equals("")
			) {
			excepcion.addError("Debe digitar al menos otro campo para la consulta");
		}
		/**En caso de consultas nacionales*/
		if(idCirculo!=null && idMatricula.equals("")){
			if(idCirculo.equals(WebKeys.SIN_SELECCIONAR)){
				excepcion.addError("Debe seleccionar un circulo");
			}
		}
		// validator: secuencia
		if( tipoDocumentoCiudadano.equals( CCiudadano.TIPO_DOC_ID_SECUENCIA )
			&&( ( "".equals( apellido1Ciudadano ) ) ) ) {

			excepcion.addError( "Debe proporcionar numero de secuencia en la casilla: 'Apellido 1'." );

		}
		/*
		else if ( ( !apellido1Ciudadano.equals("") )
					 && ( nombreCiudadano.equals("") )
                	 && ( ! (
							   ( tipoDocumentoCiudadano.equals( CCiudadano.TIPO_DOC_ID_NIT) )
							   || ( tipoDocumentoCiudadano.equals( CCiudadano.TIPO_DOC_ID_SECUENCIA ) )
						    )
						)
					) {
            excepcion.addError("Debe proporcionar nombre y apellido");
        }
        */
		Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
		SolicitudConsulta sol = (SolicitudConsulta)turno.getSolicitud();
		
		if(sol.getTipoConsulta().getNombre().equals(TipoConsulta.CONSTANCIA)){
			if(apellido1Ciudadano.equals("") && nombreCiudadano.equals("")
					&& !tipoDocumentoCiudadano.equals(WebKeys.SIN_SELECCIONAR) && !documentoCiudadano.equals("")){
				excepcion.addError("Debe ingresar Nombre y Apellido junto con el documento de Identificacion");
			}
		}
		if( ( apellido1Ciudadano.equals("") && !nombreCiudadano.equals("") )
			  &&( !(( tipoDocumentoCiudadano.equals( CCiudadano.TIPO_DOC_ID_SECUENCIA )) ) ) ) {
			excepcion.addError("Debe proporcionar nombre y apellido");
        }

		if (!tipoDocumentoCiudadano.equals(WebKeys.SIN_SELECCIONAR)
			&& !tipoDocumentoCiudadano.equals(CCiudadano.TIPO_DOC_ID_NIT)
			&& !tipoDocumentoCiudadano.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)
			) {
			if (documentoCiudadano.equals("")) {
				excepcion.addError("Debe digitar un número de documento");
			}
		}

		if (!documentoCiudadano.equals("")) {
			if (tipoDocumentoCiudadano.equals(WebKeys.SIN_SELECCIONAR)) {
				excepcion.addError("Debe seleccionar un tipo de documento");
			}
		}

		if (!valorEje.equals("")) {
			if (tipoEje.equals(WebKeys.SIN_SELECCIONAR)) {
				excepcion.addError("Debe seleccionar un tipo de eje");
			}
		}

		if (!tipoEje.equals(WebKeys.SIN_SELECCIONAR)) {
			if (valorEje.equals("")) {
				excepcion.addError("Debe seleccionar un tipo de eje");
			}
		}
		String [] matricula = null;
		if(!idMatricula.equals("")){
			matricula = idMatricula.split("-");
			if(matricula.length<2){
				excepcion.addError("El formato de la matrícula es incorrecto");
			}
		}
		Liquidacion l = (Liquidacion)validador.getSolicitudConsulta().getLiquidaciones().get(0);
		if(((LiquidacionTurnoConsulta)l).getAlcanceGeografico().getNombre().equals(CAlcanceGeografico.LOCAL)){
			if(!idMatricula.equals("")){
					Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
					if(!matricula[0].equals(circulo.getIdCirculo())){
						excepcion.addError("La matrícula debe pertenecer al circulo: "+circulo.getIdCirculo());
					}
			}
		}
		busqueda.setMatricula(idMatricula);
		
           
		busqueda.setNumeroDocCiudadano(documentoCiudadano);
		if (!tipoDocumentoCiudadano.equals(WebKeys.SIN_SELECCIONAR)) {
                  busqueda.setTipoDocCiudadano(tipoDocumentoCiudadano);
		} else {
                  busqueda.setTipoDocCiudadano("");
                }
		if(idCirculo!=null && idMatricula.equals("")){
			busqueda.setIdCirculoBusqueda(idCirculo);
		}
		
		/**En caso de que la busqueda se haga por matricula, el parametro de
		 * circulo se debe eliminar*/
		if(idMatricula!=null && !idMatricula.equals("")){
			busqueda.setIdCirculoBusqueda(null);
		}
		busqueda.setNombreCiudadano(nombreCiudadano);
		busqueda.setApellido1Ciudadano(apellido1Ciudadano);
		busqueda.setApellido2Ciudadano(apellido2Ciudadano);
		busqueda.setNombreNaturalezaJuridica(nombreJuridicoAnotacion);
		busqueda.setDireccion(direccionFolio);
		busqueda.setNumeroCatastral(numeroCatastralFolio);

		if (tipoPredio!=null && !tipoPredio.equals(WebKeys.SIN_SELECCIONAR)) {
                  busqueda.setIdTipoPredio(tipoPredio);
                }
                else {
                  busqueda.setIdTipoPredio("");
                }
		if (!tipoEje.equals(WebKeys.SIN_SELECCIONAR)) {
                  busqueda.setIdEje(tipoEje);
                  for(Iterator it=ejes.iterator(); it.hasNext();){
                	  ElementoLista el = (ElementoLista) it.next();
                      	if(el.getId().equals(tipoEje)){
                      		busqueda.setNombreEje(el.getValor());
                      		break;
                      	}
                  }
                  busqueda.setValorEje(valorEje);
                }
                else {
                  busqueda.setIdEje("");
                  busqueda.setValorEje("");
                }

                session.setAttribute(CFolio.NUMERO_MATRICULA_INMOBILIARIA, idMatricula);
		session.setAttribute(DOCUMENTO_CIUDADANO, documentoCiudadano);
		session.setAttribute(TIPO_DOCUMENTO_CIUDADANO, tipoDocumentoCiudadano);
		session.setAttribute(NOMBRE_CIUDADANO_ANOTACION, nombreCiudadano);
		session.setAttribute(APELLIDO1_CIUDADANO_ANOTACION, apellido1Ciudadano);
		session.setAttribute(APELLIDO2_CIUDADANO_ANOTACION, apellido2Ciudadano);
		session.setAttribute(NOMBRE_JURIDICO_ANOTACION, nombreJuridicoAnotacion);

		session.setAttribute(DIRECCION_EN_FOLIO, direccionFolio);
		session.setAttribute(CFolio.CODIGO_CATASTRAL_FOLIO, numeroCatastralFolio);
		session.setAttribute(TIPO_DE_PREDIO, tipoPredio);
		session.setAttribute(TIPO_DE_EJE, tipoEje);
		session.setAttribute(VALOR_DEL_EJE, valorEje);
		if(idCirculo!=null){
			session.setAttribute(CCirculo.ID_CIRCULO,idCirculo);
		}
	}

        /**
         * Toma los datos de un ciudadano solicitante del request
         *
         * @param request HttpServletRequest
         * @param session HttpSession
         * @param excepcion ValidacionParametrosException
         * @return gov.sir.core.negocio.modelo.Ciudadano
         * @throws ValidacionParametrosException
         */
        protected Ciudadano getParametrosCiudadanoSolicitante(
		HttpServletRequest request,
		HttpSession session,
		ValidacionParametrosException excepcion)
		throws ValidacionParametrosException {

		String tipoDocSolicitante = request.getParameter(CCiudadano.TIPODOC);
		String numIdentSolicitante = request.getParameter(CCiudadano.IDCIUDADANO);
		String primerApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO1);
		String segundoApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO2);
		String nombreSolicitante = request.getParameter(CCiudadano.NOMBRE);

/*		if ((tipoDocSolicitante == null || tipoDocSolicitante.equals(""))
			|| (numIdentSolicitante == null || numIdentSolicitante.equals(""))
			|| (primerApellidoSolicitante == null || primerApellidoSolicitante.equals(""))
			|| (nombreSolicitante == null || nombreSolicitante.equals(""))) {
			excepcion.addError("Debe digitar los  datos completos del solicitante");
		}


		if ((numIdentSolicitante != null)
			&& (!numIdentSolicitante.trim().equals(""))
			&& tipoDocSolicitante.equals(WebKeys.SIN_SELECCIONAR)
			&& (!tipoDocSolicitante.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD))) {
			excepcion.addError("Debe seleccionar un tipo de documento");
		}
*/

		if ((primerApellidoSolicitante == null)
			|| primerApellidoSolicitante.trim().equals("")) {
			excepcion.addError("Debe escribir el Apellido o Razón Social");
		}

		if(!tipoDocSolicitante.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)
			&& (numIdentSolicitante == null || numIdentSolicitante.equals(""))){
			excepcion.addError("Debe ingresar un numero de identificación.");
		}

		if (tipoDocSolicitante.equals(WebKeys.SIN_SELECCIONAR)) {
			excepcion.addError( "Debe seleccionar un tipo de identificación para el ciudadano");
		 }


		Ciudadano ciudadanoSolicitante;

		if ((numIdentSolicitante == null || numIdentSolicitante.equals(""))
			&& (primerApellidoSolicitante == null || primerApellidoSolicitante.equals(""))
			&& (segundoApellidoSolicitante == null || segundoApellidoSolicitante.equals(""))
			&& (nombreSolicitante == null || nombreSolicitante.equals(""))) {

			ciudadanoSolicitante = null;

		} else {
			ciudadanoSolicitante = new Ciudadano();
			ciudadanoSolicitante.setTipoDoc(tipoDocSolicitante);
			ciudadanoSolicitante.setDocumento(numIdentSolicitante);
			ciudadanoSolicitante.setNombre(nombreSolicitante);
			ciudadanoSolicitante.setApellido1(primerApellidoSolicitante);
			ciudadanoSolicitante.setApellido2(segundoApellidoSolicitante);
           	//Se setea el circulo del ciudadano
            Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
            ciudadanoSolicitante.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
		}

		session.setAttribute(CCiudadano.TIPODOC, tipoDocSolicitante);
		session.setAttribute(CCiudadano.IDCIUDADANO, numIdentSolicitante);
		session.setAttribute(CCiudadano.APELLIDO1, primerApellidoSolicitante);
		session.setAttribute(CCiudadano.APELLIDO2, segundoApellidoSolicitante);
		session.setAttribute(CCiudadano.NOMBRE, nombreSolicitante);
		session.setAttribute(AWConsulta.CIUDADANO_SOLICITANTE, ciudadanoSolicitante);
		if (excepcion.getErrores().isEmpty()) {
			return ciudadanoSolicitante;
		}
                throw excepcion;
	}

        /**
         * Método que se encarga de generar un objeto para validar la consulta a partir
         * de los datos provenientes en el <code>HttpServletRequest</code>
         *
         * @param request HttpServletRequest
         * @param esInicio boolean
         * @param exception ValidacionParametrosException
         * @return gov.sir.core.web.acciones.consulta.SolicitudConsultaValidator
         * @throws AccionWebException
         */
        protected SolicitudConsultaValidator getValidadorConsulta(
		HttpServletRequest request,
		boolean esInicio,
		ValidacionParametrosException exception)
		throws AccionWebException {
		HttpSession session = request.getSession();
		if (session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR) == null) {
			Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
			if (circulo == null) {
				throw new CirculoNoEncontradoException();
			}

			Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
			if (proceso == null) {
				throw new ProcesoNoEncontradoException();
			}

			gov.sir.core.negocio.modelo.Usuario usuario =
				(gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
			String tipo = (String) session.getAttribute(AWConsulta.TIPO_CONSULTA);
			session.setAttribute(
				SOLICITUD_CONSULTA_VALIDATOR,
				new SolicitudConsultaValidator(tipo, circulo, proceso, usuario));
		}

		SolicitudConsultaValidator validador =
			(SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
		Ciudadano ciudadanoSolicitante = null;
		if (esInicio) {
			ciudadanoSolicitante = getParametrosCiudadanoSolicitante(request, session, exception);
		}
		validador.setCiudadano(ciudadanoSolicitante);
		return validador;
	}

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de seleccion de
         * folios a ser adicionados a la solicitud de consulta actual
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> a crear.
         * @throws AccionWebException
         */
        private EvnConsulta seleccionarFolios(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		SeleccionFolioException exception = new SeleccionFolioException();
		Hashtable folios = (Hashtable) session.getAttribute(RESULTADOS_CONSULTA);
		Hashtable foliosSeleccionados =
			(Hashtable) session.getAttribute(RESULTADOS_SELECCIONADOS_CONSULTA);
		if (foliosSeleccionados == null) {
			foliosSeleccionados = new Hashtable();
			session.setAttribute(RESULTADOS_SELECCIONADOS_CONSULTA, foliosSeleccionados);
		}

		Paginador pagResultadosSelec =
			(Paginador) session.getAttribute(PAGINADOR_RESULTADOS_SELECCIONADOS);
		if (pagResultadosSelec == null) {
			List resultados_seleccionados = new Vector();
			pagResultadosSelec = new Paginador(resultados_seleccionados);
			session.setAttribute(PAGINADOR_RESULTADOS_SELECCIONADOS, pagResultadosSelec);
		}

		Vector nuevaSeleccion = new Vector();
		Vector seleccionPendiente = new Vector();
		for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
			String key = (String) enumeration.nextElement();
			if (key.startsWith(CHECKBOX_MATRICULA)) {
				String matricula = request.getParameter(key);
				if (!foliosSeleccionados.containsKey(matricula)) {
					ResultadoFolio resultadoFolio = (ResultadoFolio) folios.get(matricula);
					if (resultadoFolio != null) {
						foliosSeleccionados.put(matricula, resultadoFolio);
						nuevaSeleccion.add(resultadoFolio);
						SolicitudFolio solFolio = new SolicitudFolio();
						Folio folio = new Folio();
						folio.setIdMatricula(resultadoFolio.getIdMatricula());
						solFolio.setFolio(folio);
						seleccionPendiente.add(solFolio);
					}
				}
			}
		}

		if (!nuevaSeleccion.isEmpty()) {
			SolicitudConsultaValidator validador =
				(SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
			pagResultadosSelec.adicionarItems(nuevaSeleccion);
			Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
			EvnConsulta eventoConsulta = new EvnConsulta(usuario, EvnConsulta.ADICIONAR_SOLICITUD_FOLIO);
			eventoConsulta.setSolicitudesFolio(seleccionPendiente);
			eventoConsulta.setSolicitudConsulta(validador.getSolicitudConsulta());
			SolicitudConsulta solConsulta=(SolicitudConsulta)((Turno)session.getAttribute(WebKeys.TURNO)).getSolicitud();
			eventoConsulta.setSolicitudConsulta(solConsulta);
			return eventoConsulta;
		}
        return null;
	}

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de para ver la
         * información de un folio específico
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información del folio a ser
         *   visualizado a crear.
         * @throws AccionWebException
         */
        private EvnConsulta verFolio(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		String numMatriculaInmobiliara = request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
		Hashtable foliosSeleccionados = (Hashtable) session.getAttribute(RESULTADOS_CONSULTA);
			
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String accion = request.getParameter(WebKeys.ACCION).trim();
		if ((numMatriculaInmobiliara == null) || numMatriculaInmobiliara.equals("")) {
			if (accion.equals(AWConsulta.CONSULTA_FOLIO_USUARIO_INTERNO)) {
				ValidacionConsultaUsuarioInternoException exception =
					new ValidacionConsultaUsuarioInternoException();
				exception.addError("Debe proporcionar un número de matrícula inmobiliaria.");
				throw exception;
			}
            throw new MatriculaInvalidaException("Debe proporcionar un número de matrícula inmobiliaria.");
		}

		Folio folio = new Folio();
		folio.setIdMatricula(numMatriculaInmobiliara);
		EvnConsulta evento = null;
		if (accion.equals(AWConsulta.VER_FOLIO_CALIFICACION)) {
			evento = new EvnConsulta(usuario, EvnConsulta.FOLIO_CALIFICACION, folio);
		} else if (accion.equals(AWConsulta.VER_FOLIO_CORRECCION)) {
			LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
			if(llaves!=null){
				llaves.removeLLaves(request);
			}
			request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

			evento = new EvnConsulta(usuario, EvnConsulta.FOLIO_CORRECCION, folio);
		} else if (accion.equals(AWConsulta.VER_FOLIO_TRASLADO)) {
			evento = new EvnConsulta(usuario, EvnConsulta.CONSULTA_TRASLADO, folio);
            evento.setCirculo(circulo);
		} else {
			evento = new EvnConsulta(usuario, EvnConsulta.FOLIO, folio);
		}
		return evento;

	}

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de para ver la
         * información de un folio específico durante el proceso de calificación
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información del folio a ser
         *   visualizado a crear.
         * @throws AccionWebException
         */
        private EvnConsulta verFolioCalificacionFolio(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		String tipoConsulta = request.getParameter(CTabs.TAB_CONSULTA);
		String[] matriculas = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);

		String numMatriculaInmobiliara = request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
		ValidacionParametrosException ex = new ValidacionParametrosException();
		LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
		if(llaves!=null){
			llaves.removeLLaves(request);
		}
		request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		if (fase == null) {
			fase = new Fase();
		}

		if(tipoConsulta==null){
			if(numMatriculaInmobiliara!=null && !numMatriculaInmobiliara.equals("") && matriculas==null){
				tipoConsulta = CTabs.TAB_CONSULTA_MATRICULA;
			}else if((numMatriculaInmobiliara==null ||numMatriculaInmobiliara.equals(""))&& matriculas!=null){
				tipoConsulta = CTabs.TAB_CONSULTA_PADRE;
			}else{
				ex.addError("Ingrese los parámetros de la consulta de folio.");
			}
		}


		if (tipoConsulta != null) {
			if (tipoConsulta.equals(CTabs.TAB_CONSULTA_MATRICULA)) {
				if ((numMatriculaInmobiliara == null) || numMatriculaInmobiliara.equals("")) {
					ex.addError("Debe ingresar un número de matrícula");
				}
			}
			if (tipoConsulta.equals(CTabs.TAB_CONSULTA_PADRE)) {

				if (matriculas == null || matriculas.length < 1 || matriculas[0].equals("")) {
					ex.addError("Debe seleccionar la matrícula a consultar");
					request.getSession().setAttribute(WebKeys.RAZON_EXCEPCION,CExcepcion.MATRIZ_SEGREGADOS_EXCEPCION);
				}
				if (matriculas != null && matriculas.length > 1) {
					ex.addError("Seleccione sólo una matrícula");
					request.getSession().setAttribute(WebKeys.RAZON_EXCEPCION,CExcepcion.MATRIZ_SEGREGADOS_EXCEPCION);
				}
				if (matriculas != null && matriculas.length == 1) {
					numMatriculaInmobiliara = matriculas[0];
				}
			}

		}

		if (ex.getErrores().size() > 0) {
			if (fase.getID().equals(CFase.CAL_CALIFICACION)) {
				throw new ConsultaCalificacionFolioException(ex.getErrores());
			} else if (fase.getID().equals(CFase.COR_CORRECCION)) {
				throw new ConsultaCorreccionFolioException(ex.getErrores());
			} else if (fase.getID().equals(CFase.COR_USUARIO_ESPECIALIZADO)) {
				throw new ConsultaFolioEspecializadoException(ex.getErrores());
			} else if (fase.getID().equals(CFase.COR_CORRECCION_SIMPLE)) {
				throw new ConsultaCorreccionFolioException(ex.getErrores());
			}else {
				throw new AccionWebException("Ingrese correctemente la información solicitada, para realizar la consulta.");
			}
		}

		String ficha = request.getParameter(CTabs.TAB_CONSULTA_PADRE);
		if(ficha!=null){
			session.setAttribute(CTabs.TAB_CONSULTA_PADRE,ficha);
		}

		Folio folio = new Folio();
		folio.setIdMatricula(numMatriculaInmobiliara);
		EvnConsulta evento = null;

		evento =
			new EvnConsulta(
				usuario,
				EvnConsulta.CONSULTA_CALIFICACION_FOLIO,
				folio,
				(gov.sir.core.negocio.modelo.Usuario) session.getAttribute((WebKeys.USUARIO)),
				(Fase) session.getAttribute(WebKeys.FASE));
		evento.setTurno(turno);
		return evento;

	}


        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de para validar la
         * existencia de un folio en el sisteaa
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información del folio a ser
         *   validado a crear.
         * @throws AccionWebException
         */
        private EvnConsulta validarFolio(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		ValidacionConsultaFolioException exception = new ValidacionConsultaFolioException();
		String matricula = request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
		if ((matricula == null) || matricula.equals("")) {
			exception.addError("Debe digitar al un número de matrícula inmobiliaria.");
		}

		SolicitudConsultaValidator validador = getValidadorConsulta(request, false, exception);
		if (!exception.getErrores().isEmpty()) {
			throw exception;
		}

		session.setAttribute(CFolio.NUMERO_MATRICULA_INMOBILIARIA, matricula);
		String accion = request.getParameter(WebKeys.ACCION).trim();
		EvnConsulta evento = null;
		evento = new EvnConsulta(usuario, EvnConsulta.VALIDA_FOLIO);
		evento.setMatriculaInmobiliaria(matricula);
		evento.setSolicitudConsulta(validador.getSolicitudConsulta());
		return evento;
	}

        /**
         * Este método se encarga de tomar los valores del
         * <code>HttpServletRequest</code> para generar un evento de para configurar
         * los parámetros de una consulta compleja
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> a crear.
         * @throws AccionWebException
         */
        private EvnConsulta configuraConsultaCompleja(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		SolicitudConsultaValidator validador =
			(SolicitudConsultaValidator) session.getAttribute(AWConsulta.SOLICITUD_CONSULTA_VALIDATOR);

		ValidacionConsultaComplejaException excepcion = new ValidacionConsultaComplejaException();

		String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
		if (numMax != null || !numMax.equals("")) {
			try {
				int cantidad = Integer.parseInt(numMax);
				if (cantidad < 1) {
					excepcion.addError(numMax + " No es un valor numérico válido.");
				}
				validador.setNumMaxBusquedas(cantidad);
			} catch (NumberFormatException e) {
				excepcion.addError(numMax + " No es un valor numérico válido.");
			}
		} else {
			excepcion.addError("Debe proporcionar el número máximo de búsquedas a Realizar");
		}

		Ciudadano ciudadano = getParametrosCiudadanoSolicitante(request, session, excepcion);

		if (excepcion.getErrores().size() > 0) {
			throw excepcion;
		}

		EvnConsulta evento =
			new EvnConsulta(usuario, EvnConsulta.ADICIONAR_CIUDADANO_A_SOLICITUD_COMPLEJA);
		evento.setSolicitudConsulta(validador.getSolicitudConsulta());
		if (evento != null) {
			evento.setCiudadano(ciudadano);
		}
		return evento;
	}

        /**
         * Método que se encarga de validar un folio con una matricula dada. en el
         * <code>HttpServletRequest</code>
         *
         * @param request HttpServletRequest
         * @return gov.sir.core.eventos.consulta.EvnConsulta
         * @throws AccionWebException
         */
        private EvnConsulta consultarDetalleFolio(HttpServletRequest request)
		throws AccionWebException {
		//Pidiendo del request el # de matricula
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		Folio f = new Folio();
		f.setIdMatricula(idMatricula);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=(gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuario= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		return new EvnConsulta(usuario, usuarioNeg, AWConsulta.CONSULTAR_FOLIO, f);

	}

        /**
         * Este método se encarga de limpiar la sesión luego que el usuario ha
         * terminado de realizar acciones en el proceso de consulta
         *
         * @param session HttpSession
         */
        public void limpiarSesion(HttpSession session) {
		limpiarConsulta(session);
		limpiarParametrosConsultaSimple(session);
	}

        /**
         * Limpia de la sesión los datos de una consulta
         *
         * @param session HttpSession
         */
        private void limpiarConsulta(HttpSession session) {
		session.removeAttribute(CAlcanceGeografico.ALCANCE_CONSULTA);
		session.removeAttribute(RESULTADOS_CONSULTA);
		session.removeAttribute(RESULTADOS_SELECCIONADOS_CONSULTA);
		session.removeAttribute(PAGINADOR_RESULTADOS);
		session.removeAttribute(PAGINADOR_RESULTADOS_SELECCIONADOS);
		session.removeAttribute(SOLICITUD_CONSULTA_VALIDATOR);
		session.removeAttribute(AWConsulta.ACCION_SIGUIENTE);
	}

        /**
         * Limpia de la sesión los parámetros de una consulta simple
         *
         * @param session HttpSession
         */
        private void limpiarParametrosConsultaSimple(HttpSession session) {
                session.removeAttribute(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
		session.removeAttribute(DOCUMENTO_CIUDADANO);
		session.removeAttribute(TIPO_DOCUMENTO_CIUDADANO);
		session.removeAttribute(NOMBRE_CIUDADANO_ANOTACION);
		session.removeAttribute(APELLIDO1_CIUDADANO_ANOTACION);
		session.removeAttribute(APELLIDO2_CIUDADANO_ANOTACION);
		session.removeAttribute(NOMBRE_JURIDICO_ANOTACION);
		session.removeAttribute(DIRECCION_EN_FOLIO);
		session.removeAttribute(CFolio.CODIGO_CATASTRAL_FOLIO);
		session.removeAttribute(TIPO_DE_PREDIO);
		session.removeAttribute(TIPO_DE_EJE);
		session.removeAttribute(VALOR_DEL_EJE);
		session.removeAttribute(CCirculo.ID_CIRCULO);
                session.removeAttribute(ULTIMA_BUSQUEDA);
	}

        /**
         * Limpia de la sesión los datos de un solicitante
         *
         * @param session HttpSession
         */
        private void limpiarDatosSolicitante(HttpSession session) {
		session.removeAttribute(CCiudadano.TIPODOC);
		session.removeAttribute(CCiudadano.IDCIUDADANO);
		session.removeAttribute(CCiudadano.APELLIDO1);
		session.removeAttribute(CCiudadano.APELLIDO2);
		session.removeAttribute(CCiudadano.NOMBRE);
	}

        /**
         * Este método se encarga de limpiar los datos de los paginadores y resultados
         * del <code>HttpServletRequest</code> para realizar de nuevo las consultas.
         *
         * @param request HttpServletRequest
         * @return evento <code>EvnConsulta</code> con la información de la
         *   SolicitudConsulta a crear.
         * @throws AccionWebException
         */
        private EvnConsulta aceptarConsulta(HttpServletRequest request) throws AccionWebException {

		//Este codigo dependera de la cantidad de paginadores q hayan, este es el caso baso 1 solo mostrarFolioHelper.
		String nombrePaginador = request.getParameter(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = request.getParameter(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);

		if(nombrePaginador!=null){
			request.getSession().removeAttribute(nombrePaginador);
		}
		if(nombreResultado!=null){
			request.getSession().removeAttribute(nombreResultado);
		}

		LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
		if(llaves!=null){
			llaves.removeLLaves(request);
		}

		request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

		return null;
	}


        /**
         * Este método se encarga de manejar el evento de respuesta proveniente de la
         * acción de negocio. Sube datos a sesión de acuerdo al tipo de respuesta
         * proveniente en el evento
         *
         * @param request HttpServletRequest
         * @param evento EventoRespuesta
         */
        public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespConsulta respuesta = (EvnRespConsulta) evento;
		HttpSession session = request.getSession();
		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();

			if (tipoEvento.equals(EvnRespConsulta.RESULTADO_CONSULTA)
				|| tipoEvento.equals(EvnRespConsulta.RESULTADO_CONSULTA_CALIFICACION)) {
				List resultadosFoliosList = respuesta.getFolios();
				Hashtable foliosTable = new Hashtable();
				for (Iterator iter = resultadosFoliosList.iterator(); iter.hasNext();) {
					ResultadoFolio resultadofolio = (ResultadoFolio) iter.next();
					foliosTable.put(resultadofolio.getIdMatricula(), resultadofolio);
				}

				Paginador paginador_resultados = new Paginador(resultadosFoliosList);
				session.setAttribute(RESULTADOS_CONSULTA, foliosTable);
				session.setAttribute(PAGINADOR_RESULTADOS, paginador_resultados);
				session.setAttribute(ULTIMA_BUSQUEDA, respuesta.getUltimaBusqueda());
				SolicitudConsultaValidator solicitudValidator =
					(SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
				solicitudValidator.setSolicitudConsulta(respuesta.getSolicitudConsulta());
				Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
				if (respuesta.getSolicitudConsulta()!=null){
					turno.setSolicitud(respuesta.getSolicitudConsulta());
				}
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.CONSULTA_FOLIO_CALIFICACION)) {
				session.removeAttribute(AWConsulta.FOLIO_CALIFICACION);
				Folio folio = null;
				if ((respuesta.getFolios() != null) && (!respuesta.getFolios().isEmpty())) {
					folio = (Folio) respuesta.getFolios().get(0);
					session.setAttribute(AWConsulta.FOLIO_CALIFICACION, folio);
				}
				session.setAttribute(AWConsulta.FOLIOS_DERIVADO_PADRE, respuesta.getFoliosDerivadoPadre());
				session.setAttribute(AWConsulta.FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.CONSULTA_TRASLADO_OK)) {
				session.removeAttribute(AWConsulta.FOLIO);
				Folio folio = null;
				if ((respuesta.getFolios() != null) && (!respuesta.getFolios().isEmpty())) {
					folio = (Folio) respuesta.getFolios().get(0);
					session.setAttribute(AWConsulta.FOLIO, folio);
					session.removeAttribute(AWAdministracionForseti.CIRCULO_TRASLADO);
                                        session.removeAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
					session.removeAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS);
					session.removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
					session.removeAttribute(CCirculo.ID_CIRCULO);
					session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
					session.removeAttribute(CMunicipio.ID_MUNICIPIO);
					session.removeAttribute(CVereda.ID_VEREDA);
				}
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.VER_FOLIO)) {
				session.removeAttribute(AWConsulta.FOLIO);
				Folio folio = null;
                                /**
                                * @author     : Carlos Torres
                                * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                */
                                gov.sir.core.negocio.modelo.Traslado tr = null;
				if ((respuesta.getFolios() != null) && (!respuesta.getFolios().isEmpty())) {
					folio = (Folio) respuesta.getFolios().get(0);
                                /**
                                * @author     : Carlos Torres
                                * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                */
                                        tr = (gov.sir.core.negocio.modelo.Traslado)respuesta.getFolios().get(1);
					session.setAttribute(AWConsulta.FOLIO, folio);
                                        // BUG 5778
                                        session.setAttribute(AWConsulta.FOLIO_PADRES, respuesta.getFoliosPadre());
                                        session.setAttribute(AWConsulta.FOLIO_HIJOS, respuesta.getFoliosHijo());
                                        /**
                                        * @author     : Carlos Torres
                                        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                        */
                                        session.setAttribute("INFO_TRASLADO", tr);
				}
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.VER_FOLIO_CALIFICACION)) {
				session.removeAttribute(AWConsulta.FOLIO_CALIFICACION);
				Folio folio = null;
				if ((respuesta.getFolios() != null) && (!respuesta.getFolios().isEmpty())) {
					folio = (Folio) respuesta.getFolios().get(0);
					session.setAttribute(AWConsulta.FOLIO_CALIFICACION, folio);
				}
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.VER_FOLIO_CORRECCION)) {
				session.removeAttribute(AWConsulta.FOLIO_CORRECCION);
				Folio folio = null;
				if ((respuesta.getFolios() != null) && (!respuesta.getFolios().isEmpty())) {
					folio = (Folio) respuesta.getFolios().get(0);
					session.setAttribute(AWConsulta.FOLIO_CORRECCION, folio);
				}
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.VALIDACION_FOLIO_OK)) {
				SolicitudConsultaValidator solicitudValidator =
					(SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
				SolicitudConsulta solConsulta = respuesta.getSolicitudConsulta();
				if (solConsulta.getSolicitudFolios().isEmpty()) {
					String matriculaNoEncontrada =
						(String) session.getAttribute(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
					session.setAttribute(
						CFolio.NUMERO_MATRICULA_INMOBILIARIA,
						"No se encontró el folio con matrícula " + matriculaNoEncontrada);
				}
				solicitudValidator.setSolicitudConsulta(solConsulta);
				return;
			} else if (
				tipoEvento.equals(EvnRespConsulta.SOLICITUD_CONSULTA_GENERADA)
					|| tipoEvento.equals(EvnRespConsulta.SOLICITUDES_FOLIO_ADICIONADAS)
					|| tipoEvento.equals(EvnRespConsulta.CIUDADANO_ADICIONADO_OK)) {
				SolicitudConsultaValidator solicitudValidator =
					(SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
				solicitudValidator.setSolicitudConsulta(respuesta.getSolicitudConsulta());
                                Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
                                turno.setSolicitud(respuesta.getSolicitudConsulta());
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.IMPRESION_OK)) {
				session.setAttribute(AWConsulta.ACCION_SIGUIENTE, EvnRespConsulta.IMPRESION_OK);
				if(respuesta.getIdImprimible()!=0){
					request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));
				}
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.IMPRESION_FAILURE)) {
				session.setAttribute(AWConsulta.ACCION_SIGUIENTE, EvnRespConsulta.IMPRESION_FAILURE);
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.CONSULTAR_FOLIO)) {
				session.setAttribute(AWConsulta.FOLIO, respuesta.getFolio());
				session.setAttribute(AWConsulta.TURNO_TRAMITE, respuesta.getTurnoTramite());
				session.setAttribute(AWConsulta.TURNO_DEUDA, respuesta.getTurnoDeuda());
				session.setAttribute(AWConsulta.USUARIO_BLOQUEO, respuesta.getUsuarioBloqueo());
				return;
			}
		}
	}
}
