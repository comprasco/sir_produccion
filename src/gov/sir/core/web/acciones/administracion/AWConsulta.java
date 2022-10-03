package gov.sir.core.web.acciones.administracion;


import gov.sir.core.eventos.administracion.EvnConsulta;
import gov.sir.core.eventos.administracion.EvnRespConsulta;

import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.ResultadoFolio;
import gov.sir.core.negocio.modelo.TipoConsulta;
/* @autor          : JATENCIA 
 * @mantis         : 0014985 
 * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas  
 * @descripcion    : Se incluye el objeto traslado, para poder utilizar la función.
 */
import gov.sir.core.negocio.modelo.Traslado;
/* Fin del bloque */
import gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CirculoNoEncontradoException;
import gov.sir.core.web.acciones.excepciones.EjecucionConsultaSimpleException;
import gov.sir.core.web.acciones.excepciones.MatriculaInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.Paginador;
import gov.sir.core.web.navegacion.administracion.ControlNavegacionConsulta;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
	
	/** Constante que identifica las acción de terminar la utilización de los servicios 
	 * de la acción WEB (Para limpiar la sesión y redirigir a la página principal de páginas
	 * administrativas */
	public static final String TERMINA = "TERMINA";
	
	/** Constante que identifica el valor de la accion para selección de consulta * */
	public static final String SELECCION_CONSULTA = "SELECCION_CONSULTA";
	
	/** Constante que identifica el valor de la acción para la ejecución de una consulta simple */
	public static final String EJECUTA_SIMPLE = "EJECUTA_SIMPLE";
	
	/** Constante que identifica la acción de validar un folio */
	public static final String VALIDA_FOLIO = "VALIDA_FOLIO";
	
	/** Constante que identifica la acción de ver la información de un folio */
	public static final String VER_FOLIO = "VER_FOLIO";
	
	public static final String VISTA_VOLVER = "VISTA_VOLVER";
	
	public static final String VOLVER = "VOLVER";
	
	/** Constante que identifica la acción de seleccionar varios folios */
	public static final String SELECCIONAR_FOLIOS = "SELECCIONAR_FOLIOS";
	
	/** Constante que identifica el valor de la accion para ver la selección de resultados de una consulta simple */
	public static final String VER_SELECCION_CONSULTA_SIMPLE = "VER_SELECCION_CONSULTA_SIMPLE";
	
	/** Constante que identifica el valor de la accion para la ejecución de una  consulta de folio 	 */
	public static final String OBSERVA_FOLIO = "OBSERVA_FOLIO";
	
	/** Constante que identifica la acción de ejecutar la siguiente acción encontrada en un parámetro de la sesión  */
	public static final String ACCION_SIGUIENTE = "ACCION_SIGUIENTE";
	
	/** Constante que identifica la acción de mostrar los resultados de una consulta  */
	public static final String MOSTRAR_CONSULTA = "MOSTRAR_CONSULTA";
	
	/** Constante que identifica la acción de consultar los detalles de un folio.*/
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";

	
	public static final String TURNO_TRAMITE = "TURNO_TRAMITE";
	public static final String TURNO_DEUDA = "TURNO_DEUDA";
	public static final String USUARIO_BLOQUEO = "USUARIO_BLOQUEO";
	
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
	
	
	/** Constante que identifica el valor de la variable de sesión para almacenar un folio */
	public static final String FOLIO = "FOLIO";
	
	
	
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
	
	public static final String ULTIMA_BUSQUEDA = "ULTIMA_BUSQUEDA";
	
	public static final String RESULTADOS_CONSULTA_SIMPLE =  "RESULTADOS_CONSULTA_SIMPLE";
	
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
		
		if (accion.equals(EJECUTA_SIMPLE)) { // SI SE UTILIZO
			return ejecutaConsultaSimple(request);
		} else if (accion.equals(VER_FOLIO)) { // SI SE UTILIZO
			return verFolio(request);
		} else if (accion.equals(AWConsulta.TERMINA)) { // SI SE UTILIZO
			return  limpiarSesionConsulta(request);
		} else {
			throw new AccionInvalidaException("La acción " + accion + " no es válida.");
		}
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
		String AlcanceConsulta = "LOCAL";
		
		//SolicitudConsultaValidator validador = getValidadorConsulta(request, false, excepcion);
		
		Busqueda busqueda = null;
		
		try {
			/*Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
			 SolicitudConsulta solicitud = (SolicitudConsulta) turno.getSolicitud();
			 validador.setSolicitudConsulta(solicitud);
			 */
			//Se inicializa si es del circulo o no
			busqueda = new Busqueda();
			busqueda.setNumeroIntento(0);
			
			if (AlcanceConsulta.equals("LOCAL")) {
				String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
				if (idCirculo.equals("SIN_SELECCIONAR")) {
					excepcion.addError("Debe seleccionar un Circulo Registral");
				}
				busqueda.setIdCirculoBusqueda(idCirculo);
				session.setAttribute(CCirculo.ID_CIRCULO, idCirculo);
				}
			
			//busqueda = validador.siguienteBusqueda(AlcanceConsulta);
			poblarBusqueda(request, session, excepcion,
					busqueda);
			if (excepcion.getErrores().size() > 0) {
				//validador.getSolicitudConsulta().removeBusqueda(busqueda);
			}
		} catch (Exception  e) {
			excepcion.addError(e.getMessage());
		}
		
		if (excepcion.getErrores().size() > 0) {
			throw excepcion;
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		//////////////////////////////////////////////////////////
		eventoConsulta = new EvnConsulta(usuario, EvnConsulta.NUEVA_BUSQUEDA);
		//eventoConsulta.setSolicitudConsulta(validador.getSolicitudConsulta());
		eventoConsulta.setBusqueda(busqueda);
		Log.getInstance().debug(AWConsulta.class, "RETORNANDO BUSQUEDA NOMBRE: "+busqueda.getNombreCiudadano());
		Log.getInstance().debug(AWConsulta.class, "RETORNANDO BUSQUEDA APELLIDO: "+busqueda.getApellido1Ciudadano());
		session.setAttribute(
				AWConsulta.ACCION_SIGUIENTE,
				ControlNavegacionConsulta.RESULTADO_CONSULTA_COMPLEJA);
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
			Busqueda busqueda) {
		
		String idMatricula = request.getParameter(CFolio.NUMERO_MATRICULA_INMOBILIARIA);
		
		String documentoCiudadano = request.getParameter(DOCUMENTO_CIUDADANO).trim();
		String tipoDocumentoCiudadano = request.getParameter(TIPO_DOCUMENTO_CIUDADANO).trim();
		String nombreCiudadano = request.getParameter(NOMBRE_CIUDADANO_ANOTACION).trim();
		Log.getInstance().debug(AWConsulta.class, "NOMBRE DEL CIUDADANO PARA CONSULTA SIMPLE: "+nombreCiudadano);
		String apellido1Ciudadano = request.getParameter(APELLIDO1_CIUDADANO_ANOTACION).trim();
		Log.getInstance().debug(AWConsulta.class, "APELLIDO DEL CIUDADANO PARA CONSULTA SIMPLE: "+apellido1Ciudadano);
		String apellido2Ciudadano = request.getParameter(APELLIDO2_CIUDADANO_ANOTACION).trim();
		
		String nombreJuridicoAnotacion = request.getParameter(NOMBRE_JURIDICO_ANOTACION).trim();
		String direccionFolio = request.getParameter(DIRECCION_EN_FOLIO).trim();
		String numeroCatastralFolio = request.getParameter(CFolio.CODIGO_CATASTRAL_FOLIO).trim();
		String tipoPredio = request.getParameter(TIPO_DE_PREDIO);
		String tipoEje = request.getParameter(TIPO_DE_EJE);
		String valorEje = request.getParameter(VALOR_DEL_EJE).trim();
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
		
		
		if (!idMatricula.equals("")) {
			busqueda.setMatricula(idMatricula);	
		} else {
			busqueda.setMatricula(null);
		}
		
		if (!documentoCiudadano.equals("")) {
			busqueda.setNumeroDocCiudadano(documentoCiudadano);	
		} else {
			busqueda.setNumeroDocCiudadano(null);
		}
		
		if (!tipoDocumentoCiudadano.equals(WebKeys.SIN_SELECCIONAR)) {
			busqueda.setTipoDocCiudadano(tipoDocumentoCiudadano);
		}
		
		if (!nombreCiudadano.equals("")) {
			busqueda.setNombreCiudadano(nombreCiudadano);
		} else {
			busqueda.setNombreCiudadano(null);
		}
		
		if (!apellido1Ciudadano.equals("")) {
			busqueda.setApellido1Ciudadano(apellido1Ciudadano);
		} else {
			busqueda.setApellido1Ciudadano(null);
		}
		
		if (!apellido2Ciudadano.equals("")) {
			busqueda.setApellido2Ciudadano(apellido2Ciudadano);
		} else {
			busqueda.setApellido2Ciudadano(null);
		}
		
		if (!nombreJuridicoAnotacion.equals("")) {
			busqueda.setNombreNaturalezaJuridica(nombreJuridicoAnotacion);
		} else {
			busqueda.setNombreNaturalezaJuridica(null);
		}
		
		if (!direccionFolio.equals("")) {
			busqueda.setDireccion(direccionFolio);
		} else {
			busqueda.setDireccion(null);
		}
		
		if (!numeroCatastralFolio.equals("")) {
			busqueda.setNumeroCatastral(numeroCatastralFolio);
		} else {
			busqueda.setNumeroCatastral(null);
		}
		
		if (!tipoPredio.equals(WebKeys.SIN_SELECCIONAR)) {
			busqueda.setIdTipoPredio(tipoPredio);
		}
		if (!tipoEje.equals(WebKeys.SIN_SELECCIONAR)) {
			busqueda.setIdEje(tipoEje);
			busqueda.setValorEje(valorEje);
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
			gov.sir.core.negocio.modelo.Usuario usuario =
				(gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
			String tipo = (String) session.getAttribute(AWConsulta.TIPO_CONSULTA);
			String AlcanceConsulta = "LOCAL";
			session.setAttribute(SOLICITUD_CONSULTA_VALIDATOR,new SolicitudConsultaValidator(tipo, circulo, usuario,AlcanceConsulta));
		}
		
		SolicitudConsultaValidator validador = (SolicitudConsultaValidator) session.getAttribute(SOLICITUD_CONSULTA_VALIDATOR);
		return validador;
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
			throw new MatriculaInvalidaException("Debe proporcionar un número de matrícula inmobiliaria.");
		}
		Folio folio = new Folio();
		folio.setIdMatricula(numMatriculaInmobiliara);
		EvnConsulta evento = null;
		
		evento = new EvnConsulta(usuario, EvnConsulta.FOLIO, folio);
		
		return evento;
		
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
		session.removeAttribute(CCirculo.ID_CIRCULO);
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
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de limpiar la sesión luego que el usuario ha terminado de 
	 * usar las pantallas administrativas relacionadas con Ciudadanos
	 */
	private EvnConsulta limpiarSesionConsulta(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		limpiarSesion(session);
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
			
			if (tipoEvento.equals(EvnRespConsulta.RESULTADO_CONSULTA)) { // SI SE UTILIZA
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
				return;
			} else if (tipoEvento.equals(EvnRespConsulta.VER_FOLIO)) { // SI SE UTILIZA
				session.removeAttribute(AWConsulta.FOLIO);
				Folio folio = null;
				if ((respuesta.getFolios() != null) && (!respuesta.getFolios().isEmpty())) {
					folio = (Folio) respuesta.getFolios().get(0);
                                        /* @autor          : JATENCIA 
                                         * @mantis         : 0014985 
                                         * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas  
                                         * @descripcion    : Se realiza el llamado de la información de traslado 
                                         *                   con el fin de almacenarla en sección, para que pueda visualizar en el PDF.
                                         */
                                        Traslado tr = (Traslado) respuesta.getFolios().get(1);
					session.setAttribute(AWConsulta.FOLIO, folio);
                                        session.setAttribute("INFO_TRASLADO", tr);
                                        /* Fin del bloque */
				}
				return;
			} 
		}
	}
	
	
}
