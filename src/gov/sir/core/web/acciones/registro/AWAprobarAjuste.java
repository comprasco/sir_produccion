package gov.sir.core.web.acciones.registro;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.registro.EvnAprobarAjuste;
import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnRespAprobarAjuste;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;

/**
 * @author jfrias
 * @author mmunoz
*/
public class AWAprobarAjuste extends SoporteAccionWeb {
	public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";

	/**Esta constante identifica el campo donde se introduce el numero de la radicacion en la anotacion*/
	public static final String ANOTACION_NUM_RADICACION = "ANOTACION_NUM_RADICACION";

	/**Esta constante identifica el campo donde se introduce la fecha de la radicacion en la anotacion*/
	public static final String ANOTACION_FECHA_RADICACION = "ANOTACION_FECHA_RADICACION";

	/**Esta constante identifica el campo donde se introduce el tipo de docuemtno en la anotacion*/
	public static final String ANOTACION_TIPO_DOCUMENTO = "ANOTACION_TIPO_DOCUMENTO";

	/**Esta constante identifica el campo donde se introduce el numero del documento en la anotacion*/
	public static final String ANOTACION_NUM_DOCUMENTO = "ANOTACION_NUM_DOCUMENTO";

	/**Esta constante identifica el campo donde se introduce la fecha del documento en la anotacion*/
	public static final String ANOTACION_FECHA_DOCUMENTO = "ANOTACION_FECHA_DOCUMENTO";

	/** Constante que ayuda a poder pasar los atributos de la oficina del pop up a la ventana principal**/
	public static final String ANOTACION_OFICINA_DOCUMENTO = "ANOTACION_OFICINA_DOCUMENTO";

	/**Esta constante identifica el campo donde se introduce el tipo de oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_TIPO = "ANOTACION_OFICINA_DOCUMENTO_TIPO";

	/**Esta constante identifica el campo donde se introduce el tipo de oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_TIPO = "ANOTACION_OFICINA_DOCUMENTO_ID_TIPO";

	/**Esta constante identifica el campo donde se introduce el numero de oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NUM = "ANOTACION_OFICINA_DOCUMENTO_NUM";

	/**Esta constante identifica el campo donde se introduce el identificador de la oficina del documento en la anotacion*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA = "ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA";

	/** Esta constante identifica el campo donde se guardara el identificador del departamento*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO = "ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO";

	/** Esta constante identifica el campo donde se guardara el nombre del departamento*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO = "ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO";

	/** Esta constante identifica el campo donde se guardara el identificador del municipio*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC = "ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC";

	/** Esta constante identifica el campo donde se guardara el nombre del municipio*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC = "ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC";

	/** Esta constante identifica el campo donde se guardara el identificador de la vereda*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA = "ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA";

	/** Esta constante identifica el campo donde se guardara el nombre de la vereda*/
	public static final String ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA = "ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA";

	/**Esta constante identifica el campo donde se introduce el valor de la especificacion de la anotacion*/
	public static final String ANOTACION_VALOR_ESPECIFICACION = "ANOTACION_VALOR_ESPECIFICACION";

	/**Esta constante identifica el campo donde se introduce el id de la naturaleza juridica de la especificacion de la anotacion*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID";

	/**Esta constante identifica el campo donde se introduce el nombre de la naturaleza juridica de la especificacion de la anotacion*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM = "ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM";

	/**Prefijo que identifica la pareja de datos id y nombre para la naturaleza juridica*/
	public static final String ANOTACION_NAT_JURIDICA_ESPECIFICACION = "ANOTACION_NAT_JURIDICA_ESPECIFICACION";

	/**Esta constante identifica el campo donde se introduce el comentario de la especificacion en la anotacion*/
	public static final String ANOTACION_COMENTARIO_ESPECIFICACION = "ANOTACION_COMENTARIO_ESPECIFICACION";

	/**Esta constante identifica el campo donde se introduce el tipo de identificacion del ciudadano en la anotacion*/
	public static final String ANOTACION_TIPO_ID_PERSONA = "ANOTACION_TIPO_ID_PERSONA";

	/**Esta constante identifica el campo donde se introduce el numero de identificacion en la anotacion*/
	public static final String ANOTACION_NUM_ID_PERSONA = "ANOTACION_NUM_ID_PERSONA";

	/**Esta constante identifica el campo donde se introduce el apellido de la persona en la anotacion*/
	public static final String ANOTACION_APELLIDO_1_PERSONA = "ANOTACION_APELLIDO_1_PERSONA";

	/**Esta constante identifica el campo donde se introduce el apellido de la persona en la anotacion*/
	public static final String ANOTACION_APELLIDO_2_PERSONA = "ANOTACION_APELLIDO_2_PERSONA";

	/**Esta constante identifica el campo donde se introduce los nombres de la persona en la anotacion*/
	public static final String ANOTACION_NOMBRES_PERSONA = "ANOTACION_NOMBRES_PERSONA";

	/**Esta constante identifica el campo donde se introduce el tipo de intervencion en la asocioacion en la anotacion*/
	public static final String ANOTACION_TIPO_INTER_ASOCIACION = "ANOTACION_TIPO_INTER_ASOCIACION";

	/**Esta constante identifica el campo donde se introduce si el ciudadano es el propiuetario en la asocioacion en la anotacion*/
	public static final String ANOTACION_ES_PROPIETARIO_ASOCIACION = "ANOTACION_ES_PROPIETARIO_ASOCIACION";

	/**Esta constante identifica el campo donde se introduce si el ciudadano es el propiuetario en la asocioacion en la anotacion*/
	public static final String ANOTACION_PORCENTAJE_ASOCIACION = "ANOTACION_PORCENTAJE_ASOCIACION";

	/**Esta constante identifica el numero de algun objeto que se quiere eliminar*/
	public static final String POSICION = "POSICION";
	/** Esta constante identifica el identificador de la anotacion*/
	public static final String ANOTACION_ID_ANOTACION = "ANOTACION_ID_ANOTACION";

	/** Esta constante identifica el orden de la anotacion*/
	public static final String ANOTACION_ORDEN = "ANOTACION_ORDEN";
	
	/**Esta constante identifica el numero de la posición de la anotacion que se esta editando*/
	public static final String POSICIONANOTACION = "POSICIONANOTACION";

	/**Esta constante identifica el prefijo utilizado para que el popup pueda regrezar los valores a la ventana que lo abrio */
	public static final String FOLIO_LOCACION = "FOLIO_LOCACION";

	/** Esta constante identifica el campo donde se guardara el identificador del departamento*/
	public static final String FOLIO_LOCACION_ID_DEPTO = "FOLIO_LOCACION_ID_DEPTO";

	/** Esta constante identifica el campo donde se guardara el nombre del departamento*/
	public static final String FOLIO_LOCACION_NOM_DEPTO = "FOLIO_LOCACION_NOM_DEPTO";

	/** Esta constante identifica el campo donde se guardara el identificador del municipio*/
	public static final String FOLIO_LOCACION_ID_MUNIC = "FOLIO_LOCACION_ID_MUNIC";

	/** Esta constante identifica el campo donde se guardara el nombre del municipio*/
	public static final String FOLIO_LOCACION_NOM_MUNIC = "FOLIO_LOCACION_NOM_MUNIC";

	/** Esta constante identifica el campo donde se guardara el identificador de la vereda*/
	public static final String FOLIO_LOCACION_ID_VEREDA = "FOLIO_LOCACION_ID_VEREDA";

	/** Esta constante identifica el campo donde se guardara el nombre de la vereda*/
	public static final String FOLIO_LOCACION_NOM_VEREDA = "FOLIO_LOCACION_NOM_VEREDA";

	/**Esta constante identifica el campo donde se introduce el estado del folio */
	public static final String FOLIO_ESTADO_FOLIO = "FOLIO_ESTADO_FOLIO";

	/**Esta constante identifica el campo donde se introduce el tipo del predio del folio */
	public static final String FOLIO_TIPO_PREDIO = "FOLIO_TIPO_PREDIO";

	/**Esta constante identifica el campo donde se introduce el numero de la complementacion del  folio */
	public static final String FOLIO_COMPLEMENTACION = "FOLIO_COMPLEMENTACION";

	/**Esta constante identifica el campo donde se muestra el código del documento*/
	public static final String FOLIO_COD_DOCUMENTO = "FOLIO_COD_DOCUMENTO";

	/**Esta constante identifica el campo donde se muestra la fecha de apertura*/
	public static final String FOLIO_FECHA_APERTURA = "FOLIO_FECHA_APERTURA";

	/**Esta constante identifica el campo donde se introduce el numero del codigo catastral del folio */
	public static final String FOLIO_COD_CATASTRAL = "FOLIO_COD_CATASTRAL";

	/**Esta constante identifica el campo donde se introduce el eje del folio */
	public static final String FOLIO_EJE1 = "FOLIO_EJE1";

	/**Esta constante identifica el campo donde se introduce el eje del folio */
	public static final String FOLIO_EJE2 = "FOLIO_EJE2";

	/**Esta constante identifica el campo donde se introduce el valor de la direccion del folio */
	public static final String FOLIO_VALOR1 = "FOLIO_VALOR1";

	/**Esta constante identifica el campo donde se introduce el valor de la direccion del folio */
	public static final String FOLIO_VALOR2 = "FOLIO_VALOR2";

	/**Esta constante identifica el campo donde se introduce el complemento de la direccion del folio */
	public static final String FOLIO_COMPLEMENTO_DIR = "FOLIO_COMPLEMENTO_DIR";

	/**Esta constante identifica el campo donde se introduce el numero de la radicacion del folio */
	public static final String FOLIO_NUM_RADICACION = "FOLIO_NUM_RADICACION";

	/**Esta constante identifica el campo donde se introduce el lindero del folio */
	public static final String FOLIO_LINDERO = "FOLIO_LINDERO";
	

	/**Esta constante identifica el numero de la anotacion que se va modificar */
	public static final String NUM_ANOTACION_TEMPORAL = "NUM_ANOTACION_TEMPORAL";
	
	
	public static final String APROBAR_REVISION="APROBAR_REVISION";
	
	private String accion;
	public static final String REVISAR_FOLIO="REVISAR_FOLIO";
	public static final String CONFIRMAR="CONFIRMAR";
	public static final String DEVOLVER="DEVOLVER";
	public static final String REVISAR_FOLIO_DEVOLUCION="REVISAR_FOLIO_DEVOLUCION";
	public static final String VER_DATOS_DEVOLUCION="VER_DATOS_DEVOLUCION";
	public static final String VER_DATOS_BASICOS_DEVOLUCION="VER_DATOS_BASICOS_DEVOLUCION";
	public static final String VER_DATOS="VER_DATOS";
	public static final String VER_DATOS_BASICOS="VER_DATOS_BASICOS";
	public static final String APROBAR_REVISION_DEVOLUCION="APROBAR_REVISION_DEVOLUCION";
	public static final String CONFIRMAR_DEV="CONFIRMAR_DEV";
	
	
	
	
	public static final String PRESERVAR_INFO="PRESERVAR_INFO";
	public static final String AGREGAR_CIUDADANO="AGREGAR_CIUDADANO";
	public static final String ELIMINAR_CIUDADANO="ELIMINAR_CIUDADANO";
	public static final String AGREGAR_ANOTACION="AGREGAR_ANOTACION";
	public static final String CANCELAR_EDICION="CANCELAR_EDICION";
	public static final String CANCELAR_EDICION_CANCELACION="CANCELAR_EDICION_CANCELACION";
	public static final String CANCELAR_CANCELACION="CANCELAR_CANCELACION";
	public static final String EDITAR_ANOTACION="EDITAR_ANOTACION";
	public static final String EDITAR_CANCELACION="EDITAR_CANCELACION";
	public static final String ELIMINAR_ANOTACION_TEMPORAL="ELIMINAR_ANOTACION_TEMPORAL";
	public static final String GRABAR_EDICION="GRABAR_EDICION";
	public static final String GRABAR_EDICION_CANCELACION="GRABAR_EDICION_CANCELACION";
	public final static String REFRESCAR_EDICION="REFRESCAR_EDICION";
	public final static String REFRESCAR_ANOTACION_SEGREGACION="REFRESCAR_ANOTACION_SEGREGACION";
	public final static String AGREGAR_VARIOS_CIUDADANOS_EDICION="AGREGAR_VARIOS_CIUDADANOS_EDICION";
	public final static String ELIMINAR_CIUDADANO_EDICION= "ELIMINAR_CIUDADANO_EDICION";
	public static final String AGREGAR_ANOTACION_CREACION_FOLIO="AGREGAR_ANOTACION_CREACION_FOLIO";
	public static final String ELIMINAR_ANOTACION="ELIMINAR_ANOTACION";
	public static final String ELIMINAR_ANOTACION_CREACION_FOLIO="ELIMINAR_ANOTACION_CREACION_FOLIO";
	public static final String PRESERVAR_INFO_ANOTACION="PRESERVAR_INFO_ANOTACION";
	public static final String ACEPTAR_ANOTACIONES_FOLIO="ACEPTAR_ANOTACIONES_FOLIO";
	public final static String ENGLOBAR_MATRICULAS ="ENGLOBAR_MATRICULAS";
	public final static String CARGAR_ANOTACIONES="CARGAR_ANOTACIONES";
	public final static String ENGLOBAR="ENGLOBAR";
	public final static String AGREGAR_COMPLEMENTACION="AGREGAR_COMPLEMENTACION";
	public final static String GRABACION_TEMPORAL_ANOTACION="GRABACION_TEMPORAL_ANOTACION";
	public final static String ELIMINAR_COMPLEMENTACION="ELIMINAR_COMPLEMENTACION";
	public final static String TERMINAR_ANOTACIONES_ENGLOBE="TERMINAR_ANOTACIONES_ENGLOBE";
	public final static String SEGREGAR_ANOTACION="SEGREGAR_ANOTACION"; 
	public final static String SEGREGAR="SEGREGAR";
	public final static String CANCELAR_ANOTACION="CANCELAR_ANOTACION";
	public final static String CANCELAR="CANCELAR";
	public final static String CREAR_ANOTACION="CREAR_ANOTACION";
	public final static String REFRESCAR_ANOTACION="REFRESCAR_ANOTACION";
	public static final String REGRESAR_REVISION="REGRESAR_REVISION";
	
	public final static String PRESERVAR_INFO_CANCELACION="PRESERVAR_INFO_CANCELACION";
	public final static String ENVIAR_CORRECCION="ENVIAR_CORRECCION";
	public final static String ENVIAR_CALIFICACION="ENVIAR_CALIFICACION";
	public static final String CREAR_FOLIO_ENGLOBE="CREAR_FOLIO_ENGLOBE";
	public final static String GRABAR_ANOTACIONES_TEMPORAL_FOLIO="GRABAR_ANOTACIONES_TEMPORAL_FOLIO";
	public static final String TOMAR_TURNO= "TOMAR_TURNO";
	public static final String ACEPTAR_CORRECCION="ACEPTAR_CORRECCION";
	public static final String NEGAR_CORRECCION="NEGAR_CORRECCION";

	public static final String AGREGAR_FOLIO_DERIVADO = "AGREGAR_FOLIO_DERIVADO";
	public static final String SEGREGAR_MASIVO = "SEGREGAR_MASIVO";
		
	public static final String AVANZAR = "AVANZAR";
	public static final String CORRECCION = "CORRECCION";
	public static final String MICRO = "MICRO";
	public static final String DIGITACION = "DIGITACION";
	public static final String ESPECIALIZADO = "ESPECIALIZADO";
	public static final String ANT_SISTEMA = "ANT_SISTEMA";
	public static final String DEVOLUCION = "DEVOLUCION";
	public static final String RESPUESTAWF = "RESPUESTAWF";
	
	public static final String FIRMA_REGISTRO_CONFIRMAR = "FIRMA_REGISTRO_CONFIRMAR";
	public static final String MESA_CONTROL_CONFIRMAR = "MESA_CONTROL_CONFIRMAR";
	public static final String ENTREGA_CONFIRMAR = "ENTREGA_CONFIRMAR";
	public static final String ELIMINAR_FOLIO_DERIVADO = "ELIMINAR_FOLIO_DERIVADO";
	public static final String VER_SALVEDAD="VER_SALVEDAD";
	

	//public static final int MAX_NUM_CIUDADANOS_TABLA = 3;
	
	public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;
	
	/** Constante que indica que se van a agregar varios ciudadanos a una anotacion**/
	public static final String AGREGAR_VARIOS_CIUDADANOS = "AGREGAR_VARIOS_CIUDADANOS";
	
	/** Constante que indica que se va a agregar un registro en la tabla de agragar ciudadanos**/
	public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS";	
	
	/** Constante que indica que se va a remover un registro en la tabla de agragar ciudadanos**/
	public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS";	
	
	/** Constante que indica que se usa en el request para obtener el numero de registros de la tabla de adicion de 
	 * ciudadanos a la anotacion.**/
	public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";	
	
	public static final String MOSTRAR = "MOSTRAR";
	
	/**
	 * 
	 */
	public AWAprobarAjuste() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
	 */
	public Evento perform(HttpServletRequest request)
		throws AccionWebException {
			
			accion = request.getParameter(WebKeys.ACCION);

			if ((accion == null) || (accion.trim().length() == 0)) {
				throw new AccionInvalidaException("Debe indicar una accion valida");
			}if (accion.equals(REVISAR_FOLIO)) {
				return revisarFolio(request);	
			}else if (accion.equals(REVISAR_FOLIO_DEVOLUCION)){
				return revisarFolio(request);	
			/*}else if (accion.equals(TOMAR_TURNO)){
				return tomarTurno(request);		*/
			}else if (accion.equals(APROBAR_REVISION)){
				return aprobarAjuste(request);			
			}else if (accion.equals(VER_DATOS)) {
				return verDatos(request);
			}else if (accion.equals(VER_DATOS_DEVOLUCION)) {
				return verDatos(request);
			}else if (accion.equals(DEVOLVER)) {
				return negarAjuste(request);
			}else if (accion.equals(VER_DATOS_BASICOS)) {
				return verDatosBasicos(request);	
			}else if (accion.equals(VER_DATOS_BASICOS_DEVOLUCION)) {
				return verDatosBasicos(request);
			}else if (accion.equals(CONFIRMAR_DEV)) {
				return confirmarDevolucion(request);			
			}else if (accion.equals(REGRESAR_REVISION)) {
				return regresarRevision(request);				
			}else {
				throw new AccionInvalidaException("La accion " + accion +
					" no es valida.");
			}
	}
	

	/**
	 * @param request
	 * @return
	 */
	private Evento verDatos(HttpServletRequest request) {
		String ver = request.getParameter(VER_DATOS);
		request.getSession().setAttribute(VER_DATOS, ver);
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento verDatosBasicos(HttpServletRequest request) {
		String ver = request.getParameter(VER_DATOS_BASICOS);
		request.getSession().setAttribute(VER_DATOS_BASICOS, ver);
		return null;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento tomarTurno(HttpServletRequest request) {
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioNeg = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		return new EvnAprobarAjuste (usuario,turno,usuarioNeg);
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento revisarFolio(HttpServletRequest request) {
		String idMatricula = request.getParameter("ITEM");
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario u=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		eliminarInfoBasicaFolio(request);
		eliminarInfoBasicaAnotacion(request);
		eliminarInfoBasicaCiudadano(request);
		request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
		request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
	
		Folio folio = new Folio();
		folio.setIdMatricula(idMatricula);
		return new EvnAprobarAjuste(usuarioAuriga, EvnFolio.CONSULTA, folio, u);
	}

	private void eliminarInfoBasicaFolio(HttpServletRequest request) {
		request.getSession().removeAttribute(CFolio.ID_MATRICULA);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_COD_CATASTRAL);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LOCACION_ID_DEPTO);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LOCACION_NOM_DEPTO);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LOCACION_ID_MUNIC);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LOCACION_NOM_MUNIC);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LOCACION_ID_VEREDA);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LOCACION_NOM_VEREDA);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_TIPO_PREDIO);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_COMPLEMENTACION);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_LINDERO);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_COD_CATASTRAL);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_EJE1);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_VALOR1);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_EJE2);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_VALOR2);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_COMPLEMENTO_DIR);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_COD_DOCUMENTO);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_FECHA_APERTURA);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_TIPO_PREDIO);
		request.getSession().removeAttribute(AWAprobarAjuste.FOLIO_ESTADO_FOLIO);
		request.getSession().removeAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
	}
		
	private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_NUM);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_TIPO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_ID_ANOTACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_COMENTARIO_ESPECIFICACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_FECHA_DOCUMENTO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_FECHA_RADICACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_TIPO_DOCUMENTO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_VALOR_ESPECIFICACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_NUM_DOCUMENTO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
		request.getSession().removeAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION);
		request.getSession().removeAttribute("tipo_oficina");
		request.getSession().removeAttribute("tipo_nom_oficina");
		request.getSession().removeAttribute("numero_oficina");
		request.getSession().removeAttribute("id_oficina");
	}
	
	private void eliminarInfoBasicaCiudadano(HttpServletRequest request) {
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_TIPO_ID_PERSONA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_NUM_ID_PERSONA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_APELLIDO_1_PERSONA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_APELLIDO_2_PERSONA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_NOMBRES_PERSONA);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_TIPO_INTER_ASOCIACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_PORCENTAJE_ASOCIACION);
		request.getSession().removeAttribute(AWAprobarAjuste.ANOTACION_ES_PROPIETARIO_ASOCIACION);
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento aprobarAjuste(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		return new EvnAprobarAjuste(usuario,userNeg, turno,fase,EvnAprobarAjuste.WF_CONFIRMAR, CAvanzarTurno.AVANZAR_CUALQUIERA);
	
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento negarAjuste(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		return new EvnAprobarAjuste(usuario,userNeg, turno,fase,EvnAprobarAjuste.WF_NEGAR, CAvanzarTurno.AVANZAR_POP);

	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento confirmarDevolucion(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		return new EvnAprobarAjuste(usuario,userNeg, turno,fase,EvnAprobarAjuste.WF_CONFIRMAR, CAvanzarTurno.AVANZAR_ELIMINANDO_PUSH);

	}
	
	/**
	 * Este método se encarga de limpiar los datos de los paginadores y resultados del <code>HttpServletRequest</code> para
	 * realizar de nuevo las consultas.
	 * @param request
	 * @return evento <code>EvnConsulta</code> con la información de la SolicitudConsulta
	 * a crear.
	 * @throws AccionWebException
	 */
	private Evento regresarRevision(HttpServletRequest request) throws AccionWebException {
		
		//Este codigo dependera de la cantidad de paginadores q hayan, este es el caso baso 1 solo mostrarFolioHelper.
		String nombrePaginador = (String)request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		
		if(nombrePaginador!=null){
			request.getSession().removeAttribute(nombrePaginador);
		}
		if(nombreResultado!=null){
			request.getSession().removeAttribute(nombreResultado);
		}		
		return null;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request,EventoRespuesta evento) {
		
		if(evento instanceof EvnRespFolio){
			EvnRespFolio respuesta = (EvnRespFolio) evento;
			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespFolio.CREAR)) {
					request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
				}
				if (respuesta.getTipoEvento().equals(EvnRespFolio.CONSULTAR)) {
					request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getFolio());
				}
				if (respuesta.getTipoEvento().equals(EvnRespFolio.CONSULTAR_LISTA)){
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_ENGLOBE, respuesta.getFolios());
				}
			}
			else {
				if (request.getParameter(WebKeys.ACCION).equals(ENVIAR_CORRECCION)){
					request.getSession().removeAttribute(CTurno.TURNO_ANTERIOR);
					request.getSession().removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
					request.getSession().removeAttribute("LIBRO");
					request.getSession().removeAttribute("PAGINA");
					request.getSession().removeAttribute("NUMERO_REGISTRO");
					request.getSession().removeAttribute("TOMO");
					request.getSession().removeAttribute(CSolicitudRegistro.COMENTARIO);
					request.getSession().removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
					request.getSession().removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
					request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR);
					request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
					request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
					request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
					request.getSession().removeAttribute(CCiudadano.TIPODOC);
					request.getSession().removeAttribute(CCiudadano.DOCUMENTO);
					request.getSession().removeAttribute(CCiudadano.APELLIDO1);
					request.getSession().removeAttribute(CCiudadano.APELLIDO2);
					request.getSession().removeAttribute(CCiudadano.NOMBRE);
					request.getSession().removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
					Integer i = (Integer)request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
					int j=i.intValue();
				
					for (int k=0; k<j; k++){
						request.getSession().removeAttribute(CFolio.ID_MATRICULA+k);	
					}
					request.getSession().removeAttribute(CFolio.NUM_MATRICULAS);
				}
				else if (request.getParameter(WebKeys.ACCION).equals(ENVIAR_CALIFICACION)){
					request.getSession().removeAttribute(CTurno.TURNO_ANTERIOR);
					request.getSession().removeAttribute(CSubtipoAtencion.SUBTIPO_SOLICITUD);
					request.getSession().removeAttribute("LIBRO");
					request.getSession().removeAttribute("PAGINA");
					request.getSession().removeAttribute("NUMERO_REGISTRO");
					request.getSession().removeAttribute("TOMO");
					request.getSession().removeAttribute(CSolicitudRegistro.COMENTARIO);
					request.getSession().removeAttribute(CSolicitudRegistro.TIPO_ENCABEZADO);
					request.getSession().removeAttribute(CSolicitudRegistro.ID_ENCABEZADO);
					request.getSession().removeAttribute(CSolicitudRegistro.CALENDAR);
					request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
					request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
					request.getSession().removeAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
					request.getSession().removeAttribute(CCiudadano.TIPODOC);
					request.getSession().removeAttribute(CCiudadano.DOCUMENTO);
					request.getSession().removeAttribute(CCiudadano.APELLIDO1);
					request.getSession().removeAttribute(CCiudadano.APELLIDO2);
					request.getSession().removeAttribute(CCiudadano.NOMBRE);
					request.getSession().removeAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
					Integer i = (Integer)request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
					int j=i.intValue();
				
					for (int k=0; k<j; k++){
						request.getSession().removeAttribute(CFolio.ID_MATRICULA+k);	
					}
					request.getSession().removeAttribute(CFolio.NUM_MATRICULAS);
				}
			}
		}
		else if(evento instanceof EvnRespAprobarAjuste){
			EvnRespAprobarAjuste respuesta = (EvnRespAprobarAjuste) evento;
			if(respuesta != null){
                            if(respuesta.getHistorialAreas() != null){
                                request.getSession().setAttribute(WebKeys.HISTORIAL_AREAS, respuesta.getHistorialAreas());
                            }
				if(respuesta.getTipoEvento().equals(EvnRespAprobarAjuste.SEGREGACION_MASIVA)){
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADOS,respuesta.getFoliosDerivados());
				}
				else if (respuesta.getTipoEvento().equals(EvnRespAprobarAjuste.FOLIO_TEMPORAL)){
					request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getFolio());
					request.getSession().setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO,new Boolean(respuesta.getMayorExtensionFolio()));
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE,respuesta.getFoliosPadre());
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO,respuesta.getFoliosHijo());
					request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN,respuesta.getGravamenes());
					request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES,respuesta.getMedidasCautelares());
					if(respuesta.getFalsaTradicion()!=null){
						request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION, respuesta.getFalsaTradicion());	
					}
					if(respuesta.getAnotacionesInvalidas()!=null){
						request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS, respuesta.getAnotacionesInvalidas());	
					}					
					request.getSession().setAttribute(WebKeys.TOTAL_ANOTACIONES,new BigDecimal(respuesta.getNumeroAnotaciones()));                    
                
					if (request.getParameter(WebKeys.ACCION).equals(GRABACION_TEMPORAL_ANOTACION)){
						//Como ya se grabó la anotacion, se borran los datos relacionados
					
						request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
						request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
						request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
						request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
						request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
						request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);	
						request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
						request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
					}
					else if (request.getParameter(WebKeys.ACCION).equals(CANCELAR_ANOTACION)){
						
					}
				}	
			} 
			else if (request.getParameter(WebKeys.ACCION).equals(CREAR_FOLIO_ENGLOBE)){
				request.getSession().removeAttribute("LISTA_FOLIOS_ENGLOBE");
				request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
				request.getSession().removeAttribute("COMPLEMENTACION_FOLIO_ENGLOBE");
				request.getSession().removeAttribute("FOLIO_ENGLOBE");
				request.getSession().removeAttribute("TABLA_ANOTACIONES_ENGLOBE");
			}
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	
	 
}
