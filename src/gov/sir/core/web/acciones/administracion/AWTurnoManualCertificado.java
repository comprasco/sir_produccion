package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRespTurnoManualCertificado;
import gov.sir.core.eventos.administracion.EvnTurnoManualCertificado;
import gov.sir.core.eventos.certificado.EvnRespValidacionMatricula;
import gov.sir.core.eventos.certificado.EvnValidacionMatricula;
import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;

//import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Esta clase realiza la liquidacion de la solicitud de certificados.
 * @author mnewball
 */
public class AWTurnoManualCertificado extends SoporteAccionWeb {

	/**Constante que identifica el campo donde se escribe la fecha de la escritura*/
	//public final static String CALENDAR = "CALENDAR";    

	/**Constante que identifica el campo donde se escribe el comentario de la escritura **/
	//public final static String COMENTARIO_ESCRITURA = "COMENTARIO_ESCRITURA";  

	/**Constante que identifica el campo donde se escribe el numero de escritura **/
	//public final static String NUMERO_ESCRITURA="NUMERO_ESCRITURA";

	/**Constante que identifica el campo donde se escribe la matricula de antiguo sistema **/
	//public final static String MATRICULA_ANT_SISTEMA ="MATRICULA_ANT_SISTEMA";    

	/**Constante que identifica el campo donde se escribe el comentario*/
	//public final static String COMENTARIO = "COMENTARIO";

	/** Constante que identifica el campo del jsp donde se solicita el componente de año para el id del turno */
	public final static String TURNO_ANIO = "TURNO_ANIO";
	
	/** Constante que identifica el campo del jsp donde se solicita el componente de año para el id del turno */
	public final static String TURNO_OFICINA = "TURNO_OFICINA";
	
	/** Constante que identifica el campo del jsp donde se solicita el componente de año para el id del turno */
	public final static String TURNO_CONSECUTIVO = "TURNO_CONSECUTIVO";
	
	public final static String VALOR_LIQUIDACION = "VALOR_LIQUIDACION";
	
	public final static String NUMERO_RECIBO = "NUMERO_RECIBO";
	
	public final static String FECHA_INICIO = "FECHA_INICIO";

	/** Constante que identifica el campo del jsp donde se solicita una nueva entrada*/
	public final static String NUEVA_ENTRADA = "NUEVA_ENTRADA";

	/** Constante que identifica el campo del jsp donde se solicita el nombre del ciudadano*/
	public final static String NOMBRE = "NOMBRE";

	/** Constante que identifica el campo del jsp donde se solicita el primer apellido del ciudadano*/
	public final static String APELLIDO2 = "APELLIDO2";

	/** Constante que identifica el campo del jsp donde se solicita el segundo apellido del ciudadano*/
	public final static String APELLIDO1 = "APELLIDO1";

	/** Constante que identifica el campo del jsp donde se solicita el id del ciudadano*/
	public final static String ID_CIUDADANO = "ID_CIUDADANO";

	/** Constante que identifica el campo del jsp donde se solicita el telefono del ciudadano*/
	public final static String TELEFONO = "TELEFONO";

	/** Constante que identifica el campo del jsp donde se solicita el tipo del documento del ciudadano*/
	public final static String TIPO_ID_CIUDADANO = "TIPO_ID_CIUDADANO";

	/** Constante que identifica el campo del jsp donde se solicita el numero de algun turno anterior*/
	public final static String TURNO_ANTERIOR = "TURNO_ANTERIOR";

	/** Constante que identifica el campo del jsp donde se solicita el tipo del certificado a solicitar*/
	public final static String TIPO_CERTIFICADO = "TIPO_CERTIFICADO";

	/** Constante que identifica el campo del jsp donde se solicita el tipo del tarifa del certificado*/
	public final static String TIPO_TARIFA_CERTIFICADOS = "TIPO_TARIFA_CERTIFICADOS";

	/** Constante que identifica el campo del jsp donde se solicita el numero de copias del certificado*/
	public final static String COPIAS = "COPIAS";
	
	public final static String TURNO_REGISTRO_ASOCIADO = "TURNO_REGISTRO_ASOCIADO";
	
	public final static String ID_TURNO_REGISTRO_ASOCIADO = "ID_TURNO_REGISTRO_ASOCIADO";
	
	/**Constante que identifica la impresora selecionada*/
	//public final static String IMPRESORA ="IMPRESORA";

	/** Constante que identifica el campo del jsp donde se solicita el numero de la matricula*/
	public final static String MATRICULA = CFolio.ID_MATRICULA;

	/** Constante que identifica el campo del jsp donde se solicita el numero total de matriculas*/
	public final static String NUM_MATRICULAS = CFolio.NUM_MATRICULAS;

	/** Constante que identifica el numero de anotaciones de un folio*/
	public final static String NUM_ANOTACIONES_FOLIO = CFolio.NUMERO_ANOTACIONES;

	/** Constante que identifica si el folio es de mayro extension*/
	public final static String MAYOR_EXTENSION_FOLIO = CFolio.MAYOR_EXTENSION;

	/**
	 * Constante que identifica que se desea liquidar la
	 * solicitud de un certificado
	 */
	public final static String LIQUIDAR = "LIQUIDAR";

	/**
	 * Constante que identifica que se desea agregar una
	 * nueva matrícula a la solicitud
	 */
	public final static String AGREGAR = "AGREGAR";

	/**
	 * Constante que identifica que se desea agregar un
	 * turno anterior a la solicitud
	 */
	public final static String AGREGAR_TURNO = "AGREGAR_TURNO";

	/**
	 * Constante que identifica que se desea eliminar
	 * una matrícula de la solicitud
	 */
	public final static String ELIMINAR = "ELIMINAR";

	/**
	 * Constante que identifica que se desea eliminar
	 * toda la informacion de la sesion
	 */
	public static final String REMOVER_INFO = "REMOVER_INFO";

	/**
	 *
	 */
	public static final String RECARGAR = "RECARGAR";

	private static final String TURNO_CIRCULO = "TURNO_CIRCULO";

	private static final String TERMINA = "TERMINA";

	public static final String ASOCIAR_TURNO = "ASOCIAR_TURNO";

	public static final String CIRCULO = "CIRCULO_TM";

	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	private HttpSession session;

	/**
	 * Crea una nueva instancia de AWLiquidacion
	 */
	public AWTurnoManualCertificado() {
		super();
	}

	/**
	 * Método principal de esta acción web. Aqui se realiza
	 * toda la lógica requerida de validación y de generación
	 * de eventos de negocio.
	 * @param request trae la informacion del formulario
	 * @throws AccionWebException cuando ocurre un error
	 * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);
		session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if (accion.equals(LIQUIDAR)) {
			return liquidar(request);
		} else if (accion.equals(AGREGAR)) {
			return adicionarMatricula(request);
		/*} else if (accion.equals(AGREGAR_TURNO)) {
			return adicionarTurno(request);*/
		} else if (accion.equals(ASOCIAR_TURNO)) {
			return asociarTurno(request);
		} else if (accion.equals(ELIMINAR)) {
			return eliminarMatricula(request);
		} else if (accion.equals(REMOVER_INFO)) {
			return termina(request);
		} else if (accion.equals(RECARGAR)) {
			return recargar(request);
		} else if (accion.equals(TERMINA)) {
			return termina(request);
		} else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	private Evento asociarTurno(HttpServletRequest request) {

		String turnoAsociado = request.getParameter(AWTurnoManualCertificado.ID_TURNO_REGISTRO_ASOCIADO);
		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();
		
		if(turnoAsociado == null || turnoAsociado.trim().equals("")) {
			exception.addError("Debe ingresar un identificador de turno válido");
		}
		
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		
		EvnTurnoManualCertificado evento = new EvnTurnoManualCertificado(usuarioAuriga, EvnTurnoManualCertificado.ASOCIAR_TURNO);
		evento.setUsuarioSIR(usuario);
		evento.setIdTurno(turnoAsociado);
		
		preservarInfo(request);
		
		return evento;
	}

	private Evento termina(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute(AWTurnoManualCertificado.TURNO_ANIO);
		session.removeAttribute(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
		session.removeAttribute(AWTurnoManualCertificado.TURNO_OFICINA);
		session.removeAttribute(AWTurnoManualCertificado.TURNO_CIRCULO);
		session.removeAttribute(AWTurnoManualCertificado.FECHA_INICIO);
		session.removeAttribute(AWTurnoManualCertificado.NUMERO_RECIBO);
		session.removeAttribute(WebKeys.VALOR_LIQUIDACION);
		
		session.removeAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS);
		session.removeAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
		session.removeAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
		session.removeAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS);
		session.removeAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS);
		session.removeAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS);
		session.removeAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
		session.removeAttribute(CDatosAntiguoSistema.TOMO_ANO_AS);
		session.removeAttribute(CDatosAntiguoSistema.COMENTARIO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NOMBRE_TIPO_AS);
		session.removeAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
		
		session.removeAttribute("VER_ANTIGUO_SISTEMA");
		session.removeAttribute(WebKeys.OCULTAR);
		
		session.removeAttribute(AWTurnoManualCertificado.TURNO_REGISTRO_ASOCIADO);
		session.removeAttribute(AWTurnoManualCertificado.ID_TURNO_REGISTRO_ASOCIADO);
		
		session.removeAttribute(AWTurnoManualCertificado.COPIAS);
		session.removeAttribute(AWTurnoManualCertificado.CIRCULO);
		
		return removerInfo(request);
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento recargar(HttpServletRequest request) {
		
		preservarInfo(request);
		session.setAttribute(WebKeys.OCULTAR, request.getParameter(WebKeys.OCULTAR));

		return null;
	}

	/**
	 * Elimina una matricula de la lista de las matriculas ingresadas.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */
	private EvnLiquidacion eliminarMatricula(HttpServletRequest request) {
		eliminarMatriculaSeleccionada(request);
		return null;
	}

	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 */
	public void eliminarMatriculaSeleccionada(HttpServletRequest request) {
		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		int val = num.intValue();
		String item = request.getParameter("ITEM");

		boolean antes = true;

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);

			if (antes) {
				String id = CFolio.ID_MATRICULA + actual.toString();

				if (id.equals(item)) {
					antes = false;
					session.removeAttribute(item);
				}
			} else {
				Integer mover = new Integer(i - 1);
				String itemActual = (String) session.getAttribute(CFolio.ID_MATRICULA + actual.toString());
				session.setAttribute(CFolio.ID_MATRICULA + mover.toString(), itemActual);
				session.removeAttribute(CFolio.ID_MATRICULA + actual.toString());
			}
		}

		val--;
		session.setAttribute(AWLiquidacionCertificado.NUM_MATRICULAS, new Integer(val));
		preservarInfo(request);
	}

	/**
	 * Agrega una matricula de la lista de las matriculas ingresadas.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */

	private EvnValidacionMatricula adicionarMatricula(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		String nombreCampo = CFolio.ID_MATRICULA + num.toString();
		nombreCampo = nombreCampo.toUpperCase();
		Circulo circ= (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String matricula = circ.getIdCirculo()+ "-";
		matricula += request.getParameter(nombreCampo);
		String tipoCertificado = request.getParameter(AWLiquidacionCertificado.TIPO_CERTIFICADO);
		EvnValidacionMatricula evento = null;
		if ((matricula != null) && !matricula.equals("")) {
			evento = new EvnValidacionMatricula(usuarioAuriga, matricula, tipoCertificado);
		}
		preservarInfo(request);
		return evento;
	}

	/**
	 * Agrega un turno anterior a la solicitud.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */

	/*private EvnTurnoManualCertificado adicionarTurno(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

		String idWfTurno = request.getParameter(AWTurnoManualCertificado.TURNO_ANTERIOR);
		idWfTurno = idWfTurno.toUpperCase();
		String anno = null;
		String circ = null;
		String proceso = null;
		String turnoId = null;

		int pos1 = 0;
		int pos2 = idWfTurno.indexOf("-", 1);

		if (pos2 != -1) {
			anno = idWfTurno.substring(pos1, pos2);
			pos1 = pos2 + 1;
			pos2 = idWfTurno.indexOf("-", pos1);
			if (pos1 != -1 && pos2 != -1) {
				circ = idWfTurno.substring(pos1, pos2);
				pos1 = pos2 + 1;
				pos2 = idWfTurno.indexOf("-", pos1);
				if (pos1 != -1 && pos2 != -1) {
					proceso = idWfTurno.substring(pos1, pos2);
					pos1 = pos2 + 1;
					if (pos1 != -1 && pos2 != -1) {
						turnoId = idWfTurno.substring(pos1);
					}
				}
			}
		}

		Turno turno = new Turno();
		EvnTurnoManualCertificado evento = null;

		if (TURNO_ANTERIOR != null && anno != null && circ != null && proceso != null && turnoId != null) {
			turno.setIdWorkflow(idWfTurno);
			turno.setAnio(anno);
			turno.setIdCirculo(circ);
			turno.setIdTurno(turnoId);
			turno.setIdProceso(Long.parseLong(proceso));

			if (((Proceso) request.getSession().getAttribute(WebKeys.PROCESO)).getIdProceso() != Long.parseLong(proceso)) {
				exception.addError("El turno no es del mismo proceso");
			} else {
				evento = new EvnTurnoManualCertificado(usuarioAuriga, "CONSULTAR", turno, usuario);
			}

		} else {
			exception.addError("El codigo del turno no es completo");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		//preservarInfo(request);
		return evento;
	}*/

	/**
	 *  Recibe la solicitud de turno de certificado que ha llenado el cajero
	 * a partir de estos datos, se debe generar una vista en la que se
	 * muestran los mismos datos y ademas, el valor liquidado y además, las
	 * opciones para que registre el pago
	 *
	 * Entonces, esta accion web recibe los siguientes datos:
	 * -si (Nueva entrada) then
	 * -Turno anterior
	 * -Tipo de certificado (cargados según tabla)
	 * -Número de matrícula inmobiliaria
	 * -Número de copias
	 * -Datos del solicitante
	 * -Tipo ID
	 * -Num ID
	 * -Apellido1
	 * -Apellido2
	 * -Nombres
	 *
	 * Validaciones que se deben realizar
	 * -Si es nueva entrada, que la anterior entrada (o sea el turno
	 * anterior) esté en el estado adecuado (esperando nueva entrada)
	 * (esto lo resuelve la capa de negocio)
	 * -El número de matrícula debe ser válida dependiendo del tipo
	 * de certificado escogido.  Algunos tipos de certificado no exigen
	 * esto.  Otros tipos de certificado tienen matrícula opcional, en este
	 * caso debe dejarse lo que ingrese el usuario.  Si el tipo de certificado
	 * exige que NO haya número de matrícula, debe borrarse lo que haya co-
	 * locado el usuario
	 * -Datos de solicitante, si existe en la base de datos según tipo id y
	 * num id, deben ignorarse los otros datos ingresados.  El cajero debe
	 * preguntar al ciudadano si es ese que aparece ahi. Si se equivoco,
	 * el cajero podrá cambiar el tipo id y el num id y hacer submit de
	 * nuevo.  Si el tipo id y num id no existen en la base de datos, se
	 * ingresan
	 *
	 * Resultados mostrados en la respuesta:
	 * -Datos de entrada
	 * -Valor del certificado (depende del tipo de certificado) (no
	 * editable)
	 * -Opciones y cajas de texto para registrar el pago
	 * -cheques y efectivo o
	 * -consignacion
	 * -etc. como se define el esquema de registro de pagos en los
	 * requerimientos
	 * @param request contenedor de tipo HttpServletRequest donde deben encontrarse los parametros arriba mencionados
	 * @return Objeto de tipo EvnTurnoCertificado que contiene el usuario que esta haciendo la transaccion, el tipo
	 * de certificado, el número de matrícula, el número de copias solicitadas y el ciudadano que está haciendo la solicitud.
	 * @throws AccionWebException Si alguno de los parametros no pasa la verificacion.
	 */
	private EvnTurnoManualCertificado liquidar(HttpServletRequest request) throws AccionWebException {
		preservarInfo(request);
		// Obtención del usuario
		session.removeAttribute(WebKeys.PAGO);
		session.removeAttribute(WebKeys.LIQUIDACION);
		session.removeAttribute(WebKeys.LISTA_CHEQUES);
		session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
		session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
		
		String impresora = request.getParameter(WebKeys.IMPRESORA);
		session.setAttribute(WebKeys.IMPRESORA,impresora); 		

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		//boolean esNuevaEntrada = Boolean.valueOf((String) request.getParameter(AWLiquidacionCertificado.NUEVA_ENTRADA)).booleanValue();


		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();
		
		// Año del turno
		String sAnio = "";
		/*String sAnio = request.getParameter(AWTurnoManualCertificado.TURNO_ANIO);
		int iAnio = 0;
		if(sAnio == null || sAnio.equals("")) {
			exception.addError("Tiene que ingresar el año del turno");
		}
		else {
			try {
				iAnio = Integer.parseInt(sAnio);
				if(iAnio < 0) {
					exception.addError("El valor del año del turno es inválido");
				}
			} catch(NumberFormatException nfException) {
				exception.addError("El valor del año del turno es inválido");
				iAnio = 0;
			}
		}*/
		
		// ID del círculo para el turno
		String sCirculo = request.getParameter(AWTurnoManualCertificado.TURNO_OFICINA);
		if(sCirculo == null || sCirculo.equals("")) {
			exception.addError("Tiene que ingresar el identificador de la oficina para el turno");
		}
		
		Circulo circulo = null;
        //circulo.setIdCirculo(sCirculoTurno);
        
        //List circulos = (List)request.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        List circulos = (List)session.getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
        if(circulos != null) {
	        for(Iterator itCirculos = circulos.iterator(); itCirculos.hasNext();) {
	        	Circulo circuloTemp = (Circulo)itCirculos.next();
	        	if(circuloTemp.getIdCirculo().equalsIgnoreCase(sCirculo)) {
	        		circulo = circuloTemp;
	        		break;
	        	}
	        }
        }
        
        if(circulo == null) {
        	exception.addError("El círculo digitado es inválido");
        }
		
		// ID del turno
		String sConsecutivoTurno = request.getParameter(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
		if(sConsecutivoTurno == null || sConsecutivoTurno.equals("")) {
			exception.addError("Tiene que ingresar un consecutivo para el turno");
		}
		if(sConsecutivoTurno != null){
			try{
				Long cons = new Long(sConsecutivoTurno);
			}catch(java.lang.NumberFormatException e){
				exception.addError("El valor del consecutivo es inválido");
			}
		}
		// Número de recibo
		String sNumeroRecibo = request.getParameter(AWTurnoManualCertificado.NUMERO_RECIBO);
		if(sNumeroRecibo == null || sNumeroRecibo.equals("")) {
			exception.addError("Tiene que ingresar un número de recibo");
		}
		
		// Número de copias
		String sNumeroCopias = request.getParameter(AWTurnoManualCertificado.COPIAS);
		if(sNumeroCopias == null || sNumeroCopias.equals("")) {
			exception.addError("Tiene que ingresar un número de copias");
		}
		
		// Valor de la liquidación
		String sValorLiquidacion = request.getParameter(AWTurnoManualCertificado.VALOR_LIQUIDACION);
		double dValorLiquidacion = 0.0;
		if(sValorLiquidacion != null && !sValorLiquidacion.equals("")) {
			try {
				dValorLiquidacion = Double.parseDouble(sValorLiquidacion);
				if(dValorLiquidacion < 0) {
					exception.addError("El valor de la liquidación es inválido");
				}
			} catch(NumberFormatException nfException) {
				exception.addError("El valor de la liquidación es inválido");
			}
		}

		// Obtención del tipo de Certificado
		String tipoCertificado = request.getParameter(AWLiquidacionCertificado.TIPO_CERTIFICADO);
		if ((tipoCertificado == null) || tipoCertificado.equals("SIN_SELECCIONAR")) {
			exception.addError("El certificado es inválido");
		}

		// Obtención del tipo de Tarifa
		String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
		if ((tipoTarifa == null) || tipoTarifa.equals("SIN_SELECCIONAR")) {
			exception.addError("El tipo de tarifa es inválido");
		}
		
		// Obtención de la fecha de inicio
		String fechaInicio = request.getParameter(AWTurnoManualCertificado.FECHA_INICIO);
		Date fFecha = null;
		if((fechaInicio == null) || fechaInicio.equals("")) {
			exception.addError("Debe ingresar una fecha de radicación para el turno");
		} else {
			
			try {
				fFecha = DateFormatUtil.parse(fechaInicio);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fFecha);
				sAnio = "" + calendar.get(Calendar.YEAR);
			} catch (ParseException e) {
				exception.addError("Debe ingresar un formato válido para la fecha de radicación");
				//fFecha = null;
			}
			
			// Se valida que la fecha de inicio del turno no sea inconsistente con el año para el
			// que se está creando el turno.
			/*if(fFecha != null) {
				Calendar cFecha = Calendar.getInstance();
				cFecha.setTime(fFecha);
				if(cFecha.get(Calendar.YEAR) > iAnio) {
					exception.addError("La fecha de inicio del turno no puede ser mayor al año en que se radicó el turno");
				}
			}*/
		}
		

		session.setAttribute(AWTurnoManualCertificado.CIRCULO, circulo);
		SolicitudCertificado sol = getSolicitudCertificadoValidada(request, exception);
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		session.setAttribute(AWTurnoManualCertificado.TURNO_ANIO, sAnio);

		LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();

		TipoCertificado tipoCert = new TipoCertificado();
		tipoCert.setIdTipoCertificado(tipoCertificado);

		if(dValorLiquidacion == 0)
			liquidacion.setTipoTarifa("EXENTO");
		else
			liquidacion.setTipoTarifa(tipoTarifa);

		List listaCer = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS);
		Iterator itlc = listaCer.iterator();
		while (itlc.hasNext()) {
			ElementoLista el = (ElementoLista) itlc.next();
			if (el.getId().equals(tipoCertificado)) {
				tipoCert.setNombre(el.getValor());
			}
		}
		liquidacion.setTipoCertificado(tipoCert);
		liquidacion.setValor(dValorLiquidacion);
		liquidacion.setFecha(fFecha);
		sol.setFecha(fFecha);
		sol.addLiquidacion(liquidacion);
		liquidacion.setSolicitud(sol);
		liquidacion.setCirculo(circulo.getIdCirculo());

		EvnTurnoManualCertificado evento = new EvnTurnoManualCertificado(usuarioAuriga, liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO), (Estacion) session.getAttribute(WebKeys.ESTACION), true, usuario);
		evento.setUID(request.getSession().getId());

		//Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		//String idCirculo = circulo.getIdCirculo();
		
		evento.setAsignarEstacion(false);
		evento.setIdCirculo(sCirculo);
		evento.setCirculo(circulo);
		evento.setAnio(sAnio);
		evento.setIdTurno(sConsecutivoTurno);
		
		return evento;
	}

	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 * @param exception
	 * @return
	 */
	public SolicitudCertificado getSolicitudCertificadoValidada(HttpServletRequest request, ValidacionParametrosException exception) {

		Circulo circulo = (Circulo) session.getAttribute(AWTurnoManualCertificado.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Ciudadano ciudadano = null;

		String tipoCert = request.getParameter(AWLiquidacionCertificado.TIPO_CERTIFICADO);
		//String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);

		int copias = 0;
		//int maxCopias = 1;

		boolean exp = false;
		
		/*if(proceso == null) {
			proceso = new Proceso();
			proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_CERTIFICADOS));
			session.setAttribute(WebKeys.)
		}*/

		//SI NO ES EXENTO EL NUMERO MAXIMO DE COPIAS ES EL CONFIGURADO
		//SI NO EL MAXIMO NUMERO DE COPIAS ES 1.   
		/*if (!tipoTarifa.equals("EXENTO")) {
			try {
				String numMaxCopias = (String) request.getSession().getServletContext().getAttribute(WebKeys.MAXIMO_COPIAS_CERTIFICADO);
				logger.debug("numMaxCopias=[" + numMaxCopias + "]");
				
				if(numMaxCopias == null){
					logger.debug("\n\n\n\n\n\n\n\n\n AWLiquidacionCertificado : NUMCOPIAS ES NULA");	
				}else if (numMaxCopias != null && numMaxCopias.equals("")){
					logger.debug("\n\n\n\n\n\n\n\n\n AWLiquidacionCertificado : NUMCOPIAS ES VACIA");
				}
				
				if(numMaxCopias == null || numMaxCopias.equals("")){
					numMaxCopias = "100";
				}

				numMaxCopias = numMaxCopias.trim();
				logger.debug("numMaxCopias=[" + numMaxCopias + "]");

				maxCopias = Integer.parseInt(numMaxCopias);
				logger.debug("maxCopias=[" + maxCopias + "]");

			} catch (NumberFormatException e) {
				logger.error(e);
				exception.addError("Número máximo de copias inválido, no se puede ingresar letras o un numeros de mayores a 2147483647");
			}

		}*/

		try {
			String cadCopias = request.getParameter(AWTurnoManualCertificado.COPIAS);
			copias = Integer.parseInt(cadCopias);
		} catch (NumberFormatException e) {
			exp = true;
			exception.addError("Número de copias inválido, no se puede ingresar letras o un numeros de mayores a 2147483647");
		}

		//if (copias > maxCopias)
		//	exception.addError("El Número máximo de copias es " + maxCopias);

		if (copias <= 0 && !exp) {
			exception.addError("El numero de copias no puede ser negativo o cero.");
		}

		Integer cantidadMatriculas = (Integer) session.getAttribute(AWTurnoManualCertificado.NUM_MATRICULAS);

		if (tipoCert != null && tipoCert.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID) && cantidadMatriculas.intValue() > 0) {
			exception.addError("Para los cetificados de antiguo sistema no se deben incluir matriculas inmobiliarias");
		}

		DatosAntiguoSistema datosAntSistema = null;
		Documento documento = null;

		if (tipoCert != null && (tipoCert.equals(CTipoCertificado.TIPO_INMEDIATO_ID) || tipoCert.equals(CTipoCertificado.TIPO_EXENTO_ID))) {
			if (cantidadMatriculas.intValue() <= 0) {
				exception.addError("Debe ingresar una matricula para ese tipo de certificado");
			}

		} else if (tipoCert != null && (tipoCert.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID) || tipoCert.equals(CTipoCertificado.TIPO_PERTENENCIA_ID))) {
			datosAntSistema = this.getDatosAntiguoSistema(request);
			documento = this.getDocumento(request);
			String cadFecha = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
			if (cadFecha != null) {
				if (cadFecha.trim().length() > 0) {
					Calendar calendar = this.darFecha(cadFecha);
					if (calendar == null)
						exception.addError("La fecha del documento NO es válida");
				}
			}

		}

		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);

		if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
		} else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificación del Ciudadano");
			} else {
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
					try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
					}
				}
			}
			if (nombres == null || nombres.trim().equals("")) {
				exception.addError("Debe ingresar el nombre del Ciudadano");
			}
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		}

		if (telefono != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTelefono(telefono);
		}

		if (tipoId != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTipoDoc(tipoId);
		}

		if (numId != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setDocumento(numId);
		}

		if (nombres != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setNombre(nombres);
		}

		if (apellido1 != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido1(apellido1);
		}

		if (apellido2 != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido2(apellido2);
		}
		
		//Se setea el circulo del ciudadano
		ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);
		
		SolicitudCertificado sol = new SolicitudCertificado();

		sol.setCirculo(circulo);
		sol.setCiudadano(ciudadano);
		sol.setUsuario(usuario);
		sol.setNumeroCertificados(copias);
		sol.setProceso(proceso);

		boolean tieneDatosAntSistema = false;
		if (datosAntSistema != null) {
			tieneDatosAntSistema = true;
			sol.setDatosAntiguoSistema(datosAntSistema);
			if (documento != null) {
				sol.setDocumento(documento);
			}
		}

		/*String turnoAnterior = request.getParameter(AWLiquidacionCertificado.TURNO_ANTERIOR);
		turnoAnterior = turnoAnterior.toUpperCase();
		if ((turnoAnterior != null) && !turnoAnterior.equals("")) {
			Turno turno = new Turno();
			turno.setIdWorkflow(turnoAnterior);
			sol.setTurnoAnterior(turno);
		}*/

		int val = cantidadMatriculas.intValue();
		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = CFolio.ID_MATRICULA + actual.toString();
			String matricula = (String) session.getAttribute(id);
			SolicitudFolio solFolio = new SolicitudFolio();
			Folio folio = new Folio();
			folio.setIdMatricula(matricula);
			solFolio.setFolio(folio);
			sol.addSolicitudFolio(solFolio);
		}

		String tipoCertificado = request.getParameter(AWTurnoManualCertificado.TIPO_CERTIFICADO);
		if (tipoCertificado != null) {
			if (tipoCertificado.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)) {
				if (!tieneDatosAntSistema) {
					exception.addError("No tiene ningun dato de antiguo sistema");
				}
				if (datosAntSistema != null) {
					sol.setDatosAntiguoSistema(datosAntSistema);
				}
			} else if (tipoCertificado.equals(CTipoCertificado.TIPO_PERTENENCIA_ID)) {
				if (cantidadMatriculas.equals(new Integer(0))) {
					if (!tieneDatosAntSistema) {
						exception.addError("Para tipos de certificado pertenencia debe ingresar o una matricula o datos de antiguo sistema");
					}
				}
			}
		}

		return sol;
	}

	/**
	Datos adicionales de antiguo sistema:
	* Libro
	  * Tipo
	  * Número - Letra
	  * Página
	  * Año
	* Tomo
	  * Número
	  * Página
	  * Municipio
	  * Año
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
	    * Tipo
	    * Número
	    * Departamento
	    * Municipio
	* Comentario 	 
	* @param request
	* @return un objeto que representa los datos de antiguo sistema ingresados por el usuario.
	*/
	private DatosAntiguoSistema getDatosAntiguoSistema(HttpServletRequest request) {
		String listaKeysAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		Vector keysLlenas = new Vector();
		String key, valor;
		for (int i = 0; i < listaKeysAntSistema.length; i++) {
			key = listaKeysAntSistema[i];
			valor = request.getParameter(key);
			if (valor != null) {
				if (valor.trim().length() > 0) {
					keysLlenas.add(key);
				}
			}
		}

		DatosAntiguoSistema datosAntSistema = null;

		if (keysLlenas.size() > 0) {
			datosAntSistema = new DatosAntiguoSistema();
			for (int i = 0; i < keysLlenas.size(); i++) {
				key = (String) keysLlenas.get(i);
				datosAntSistema = this.setDatoAntiguoSistema(request, key, datosAntSistema);

			}
		}

		return datosAntSistema;
	}

	/**
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
		* Tipo
		* Número
		* Departamento
		* Municipio
	* @param request
	* @return un objeto que representa los datos de antiguo sistema ingresados por el usuario.
	*/
	private Documento getDocumento(HttpServletRequest request) {
		String listaKeysDocumento[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
		Vector keysLlenas = new Vector();
		String key, valor;
		for (int i = 0; i < listaKeysDocumento.length; i++) {
			key = listaKeysDocumento[i];
			valor = request.getParameter(key);
			if (valor != null) {
				if (valor.trim().length() > 0) {
					keysLlenas.add(key);
				}
			}
		}

		Documento documento = null;

		if (keysLlenas.size() > 0) {
			documento = new Documento();
			for (int i = 0; i < keysLlenas.size(); i++) {
				key = (String) keysLlenas.get(i);
				documento = this.setDatoDocumento(request, key, documento);

			}
		}

		return documento;
	}

	/**
	 * 		
	 * DOCUMENTO_COMENTARIO_AS,
	 * DOCUMENTO_FECHA_AS,
	 * DOCUMENTO_NUMERO_AS,
	 * DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
	 * DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
	 * DOCUMENTO_OFICINA_MUNICIPIO_AS,
	 * DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
	 * DOCUMENTO_OFICINA_NUMERO_AS,
	 * DOCUMENTO_OFICINA_NUMERO_ID_AS,
	 * DOCUMENTO_OFICINA_TIPO_AS,
	 * DOCUMENTO_TIPO_AS
	 * @param request
	 * @param key
	 * @param documento
	 * @return
	 */
	private Documento setDatoDocumento(HttpServletRequest request, String key, Documento documento) {
		String valor = request.getParameter(key);

		/*
		 * DATOS DEL DOCUMENTO
		 Datos del documento (Opcionales)
		  * Número de documento
		  * Tipo de documento
		  * Fecha del documento
		  * Comentario
		  * Oficina origen (opcional)
		    * Tipo
		    * Número
		    * Departamento
		    * Municipio
			*/
		if (key.equals(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS)) {
			documento.setNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS)) {
			TipoDocumento tipoDoc = documento.getTipoDocumento();

			if (tipoDoc == null)
				tipoDoc = new TipoDocumento();

			tipoDoc.setIdTipoDocumento(valor);
			documento.setTipoDocumento(tipoDoc);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS)) {
			Calendar calendar = darFecha(valor);
			if (calendar != null) {
				Date fecha = calendar.getTime();
				documento.setFecha(fecha);
			}
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
			documento.setComentario(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			Departamento depto = municipio.getDepartamento();
			if (depto == null)
				depto = new Departamento();

			depto.setNombre(valor);

			municipio.setDepartamento(depto);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			Departamento depto = municipio.getDepartamento();
			if (depto == null)
				depto = new Departamento();

			depto.setIdDepartamento(valor);

			municipio.setDepartamento(depto);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			municipio.setNombre(valor);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			municipio.setIdMunicipio(valor);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			oficina.setNumero(valor);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			oficina.setIdOficinaOrigen(valor);

			documento.setOficinaOrigen(oficina);
		} /*
                   *  @author Carlos Torres
                   *  @chage   se agrega validacion de version diferente
                   *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                   */
                else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			oficina.setVersion(Long.parseLong(valor));

			documento.setOficinaOrigen(oficina);
		}else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			TipoOficina tipoOfic = oficina.getTipoOficina();
			if (tipoOfic == null)
				tipoOfic = new TipoOficina();

			tipoOfic.setIdTipoOficina(valor);
			oficina.setTipoOficina(tipoOfic);

			documento.setOficinaOrigen(oficina);
		}

		return documento;
	}

	/**
	 * Retorna los datos de antiguo sistema despues de settear la propiedad correspondiente a la llave "key"
	 * en le objeto "datosAntSistema".
	  Datos adicionales de antiguo sistema:
	* Libro
	  * Tipo
	  * Número - Letra
	  * Página
	  * Año
	* Tomo
	  * Número
	  * Página
	  * Municipio
	  * Año
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
	    * Tipo
	    * Número
	    * Departamento
	    * Municipio
	 * Comentario 
	 * @param request
	 * @param key
	 * @param datosAntSistema
	 * @return
	 */
	private DatosAntiguoSistema setDatoAntiguoSistema(HttpServletRequest request, String key, DatosAntiguoSistema datosAntSistema) {
		String valor = request.getParameter(key);

		/*
		DATOS DEL LIBRO:
		Libro
		  * Tipo
		  * Número - Letra
		  * Página
		  * Año
		*/
		//Libro.Tipo
		if (key.equals(CDatosAntiguoSistema.LIBRO_TIPO_AS)) {
			datosAntSistema.setLibroTipo(valor);
		}
		//Libro.Número - Letra
		else if (key.equals(CDatosAntiguoSistema.LIBRO_NUMERO_AS)) {
			datosAntSistema.setLibroNumero(valor);
		}
		//Libro.Página
		else if (key.equals(CDatosAntiguoSistema.LIBRO_PAGINA_AS)) {
			datosAntSistema.setLibroPagina(valor);
		}
		//Libro.Año
		else if (key.equals(CDatosAntiguoSistema.LIBRO_ANO_AS)) {
			datosAntSistema.setLibroAnio(valor);
		}

		/*
		 * DATOS DEL TOMO	 
		 Tomo
		  * Número
		  * Página
		  * Municipio
		  * Año
		 */
		else if (key.equals(CDatosAntiguoSistema.TOMO_NUMERO_AS)) {
			datosAntSistema.setTomoNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_PAGINA_AS)) {
			datosAntSistema.setTomoPagina(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS)) {
			datosAntSistema.setTomoMunicipio(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_ANO_AS)) {
			datosAntSistema.setTomoAnio(valor);
		}

		/*
		 * DATOS DE LA OFICINA DE ORIGEN
		 Oficina origen (opcional)
		  * Tipo
		  * Número
		  * Departamento
		  * Municipio
		 */
		else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS)) {
			//datosAntSistema.setDocumentoOficinaTipo(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
			//datosAntSistema.setDocumentoOficinaNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
			//datosAntSistema.setDocumentoOficinaDepartamento(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
			//datosAntSistema.setDocumentoOficinaMunicipio(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
			//datosAntSistema.setDocumentoOficinaComentario(valor);
		}
		/*
		 * DATOS DEL COMENTARIO
		 */
		else if (key.equals(CDatosAntiguoSistema.COMENTARIO_AS)) {
			datosAntSistema.setComentario(valor);
		}

		return datosAntSistema;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		if(evento instanceof EvnRespTurnoManualCertificado) {
		//if (evento instanceof EvnRespLiquidacion) {
			//EvnRespLiquidacion respuesta = (EvnRespLiquidacion) evento;
			EvnRespTurnoManualCertificado respuesta = (EvnRespTurnoManualCertificado) evento;

			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespLiquidacion.LIQUIDACION)) {

					Liquidacion liquidacion = respuesta.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);

					//NumberFormat nf = NumberFormat.getInstance();
					session.setAttribute(WebKeys.VALOR_LIQUIDACION, String.valueOf(liquidacion.getValor()));

					removerInfo(request);
					DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
					AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
					session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionEfectivo);
				} else if(respuesta.getTipoEvento().equals(EvnRespTurnoManualCertificado.ASOCIAR_TURNO_OK)) {
					
					Turno turno = (Turno)respuesta.getPayload();
					session.setAttribute(AWTurnoManualCertificado.TURNO_REGISTRO_ASOCIADO, turno);
					session.setAttribute(AWTurnoManualCertificado.ID_TURNO_REGISTRO_ASOCIADO, turno.getIdWorkflow());
				}
			}
		} else if (evento instanceof EvnRespPago) {
			EvnRespPago respuesta = (EvnRespPago) evento;
			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
					Turno turno = respuesta.getTurno();
					session.setAttribute(WebKeys.TURNO, turno);
				}
			}
		} else if (evento instanceof EvnRespValidacionMatricula) {
			EvnRespValidacionMatricula respuesta = (EvnRespValidacionMatricula) evento;
			Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
			String nombreCampo = CFolio.ID_MATRICULA + num.toString();
			nombreCampo = nombreCampo.toUpperCase();
			String matricula = respuesta.getMatricula();
			if ((matricula != null) && !matricula.equals("")) {
				session.setAttribute(nombreCampo, matricula);
				int val = num.intValue();
				val++;
				Integer nuevo = new Integer(val);
				session.setAttribute(AWLiquidacionCertificado.NUM_MATRICULAS, nuevo);
				String numeroAnota = respuesta.getNumeroAnotaciones();
				String mayorExtension = respuesta.getMayorExtension();
				session.setAttribute(AWLiquidacionCertificado.MAYOR_EXTENSION_FOLIO, mayorExtension);
				session.setAttribute(AWLiquidacionCertificado.NUM_ANOTACIONES_FOLIO, numeroAnota);

			}
			preservarInfo(request);
		} /*else if (evento instanceof EvnRespTurnoManualCertificado) {
			EvnRespTurnoManualCertificado respuesta = (EvnRespTurnoManualCertificado) evento;
			request.getSession().setAttribute(WebKeys.LIQUIDACION, respuesta.getLiquidacion());
              
			// PRESERVAR INFORMACION
              
			this.removerInformacion(request);
			Liquidacion liq = respuesta.getLiquidacion();
			String matriculaAsociada = null;
			
			if(liq != null) {
				Solicitud solicitud = liq.getSolicitud();
				if( solicitud!= null) {
					List solFolios = solicitud.getSolicitudFolios();
					Iterator itSolFolios = solFolios.iterator();
					if(!solFolios.isEmpty()) {
						while(itSolFolios.hasNext()) {
							SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();	
							matriculaAsociada = solFolio.getIdMatricula();
						}
					}
					if (liq!=null) {
						LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado)liq;
                     	TipoCertificado kind = liqCert.getTipoCertificado();
                     	
                     	//Si el certificado es de antiguo sistema se coloca en false el atributo
                     	//ocultar antiguo sistema de la sesión.
                     	if (kind != null) {
							if (kind.getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)|| kind.getNombre().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_NOMBRE))
								request.getSession().setAttribute(WebKeys.OCULTAR, "FALSE");
                     	}
					}
				}
			}
			
			Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
			String nombreCampo = CFolio.ID_MATRICULA + num.toString();
			nombreCampo = nombreCampo.toUpperCase();
			String matricula = matriculaAsociada;
			
			if ((matricula != null) && !matricula.equals("")) {
				session.setAttribute(nombreCampo, matricula);
				int val = num.intValue();
				val++;
				Integer nuevo = new Integer(val);
				session.setAttribute(AWLiquidacionCertificado.NUM_MATRICULAS, nuevo);
				String numeroAnota = respuesta.getNumeroAnotaciones();
				String mayorExtension = respuesta.getMayorExtension();
				session.setAttribute(AWLiquidacionCertificado.MAYOR_EXTENSION_FOLIO, mayorExtension);
				session.setAttribute(AWLiquidacionCertificado.NUM_ANOTACIONES_FOLIO, numeroAnota);
			}
			
			preservarInfo(request);
		}*/
	}

	/**
	 * Este metodo borra de la sesion la informacion que se puso en los campos,
	 * ademas de la informacion de las matriculas que se pone en sesion.
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no se requiere que viaje hasta el negocio
	 */
	private Evento removerInfo(HttpServletRequest request) {
		removerInformacion(request);
		return null;
	}

	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 */
	public void removerInformacion(HttpServletRequest request) {
		int val =0;
		Integer cantidadMatriculas = (Integer) session.getAttribute(AWLiquidacionCertificado.NUM_MATRICULAS);
		if (cantidadMatriculas !=null) {
			val = cantidadMatriculas.intValue();
		}

		session.removeAttribute(AWLiquidacionCertificado.COPIAS);
		session.removeAttribute(AWLiquidacionCertificado.TIPO_CERTIFICADO);
		session.removeAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
		session.removeAttribute(AWLiquidacionCertificado.TURNO_ANTERIOR);
		session.removeAttribute(AWLiquidacionCertificado.TIPO_ID_CIUDADANO);
		session.removeAttribute(CCiudadano.IDCIUDADANO);
		session.removeAttribute(AWLiquidacionCertificado.APELLIDO1);
		session.removeAttribute(AWLiquidacionCertificado.APELLIDO2);
		session.removeAttribute(AWLiquidacionCertificado.NOMBRE);
		session.removeAttribute(AWLiquidacionCertificado.TELEFONO);

		session.removeAttribute(AWLiquidacionCertificado.MAYOR_EXTENSION_FOLIO);
		session.removeAttribute(AWLiquidacionCertificado.NUM_ANOTACIONES_FOLIO);
		
		session.removeAttribute(ID_TURNO_REGISTRO_ASOCIADO);
		//session.removeAttribute(TURNO_REGISTRO_ASOCIADO);

		String keyAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		String key;
		for (int i = 0; i < keyAntSistema.length; i++) {
			key = keyAntSistema[i];
			session.removeAttribute(key);
		}

		String keyDocumento[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
		for (int i = 0; i < keyDocumento.length; i++) {
			key = keyDocumento[i];
			session.removeAttribute(key);
		}

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = AWLiquidacionCertificado.MATRICULA + actual.toString();
			session.removeAttribute(id);
		}
		session.setAttribute(AWLiquidacionCertificado.NUM_MATRICULAS, new Integer(0));
	}

	/**
	 * Este metodo pone en la sesion la informacion que se puso en los campos,
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no se requiere que viaje hasta el negocio
	 */
	public void preservarInfo(HttpServletRequest request) {
		//String sAnio = request.getParameter(AWTurnoManualCertificado.TURNO_ANIO);
		String sOficina = request.getParameter(AWTurnoManualCertificado.TURNO_OFICINA);
		String sConsecutivo = request.getParameter(AWTurnoManualCertificado.TURNO_CONSECUTIVO);
		String sNumeroRecibo = request.getParameter(AWTurnoManualCertificado.NUMERO_RECIBO);
		String sValor = request.getParameter(AWTurnoManualCertificado.VALOR_LIQUIDACION);
		String copias = request.getParameter(AWLiquidacionCertificado.COPIAS);
		//String impresora = request.getParameter(WebKeys.IMPRESORA);
		String tipoCert = request.getParameter(AWLiquidacionCertificado.TIPO_CERTIFICADO);
		String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
		String sFechaInicio = request.getParameter(AWTurnoManualCertificado.FECHA_INICIO);

		String turnoAnterior = request.getParameter(AWLiquidacionCertificado.TURNO_ANTERIOR);

		String keyAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		String key, valor;
		for (int i = 0; i < keyAntSistema.length; i++) {
			key = keyAntSistema[i];
			valor = request.getParameter(key);
			if (valor != null && !valor.trim().equals("")) {
				session.setAttribute(key, valor);
			}
		}

		String keyDocumentos[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
		for (int i = 0; i < keyDocumentos.length; i++) {
			key = keyDocumentos[i];
			valor = request.getParameter(key);
			if (valor != null && !valor.trim().equals("")) {
				session.setAttribute(key, valor);
			}
		}

		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);

		if ((copias != null)) {
			session.setAttribute(AWLiquidacionCertificado.COPIAS, copias);
		}
		if ((tipoCert != null)) {
			session.setAttribute(AWLiquidacionCertificado.TIPO_CERTIFICADO, tipoCert);
		}
		if ((tipoTarifa != null)) {
			session.setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS, tipoTarifa);
		}
		if ((turnoAnterior != null)) {
			session.setAttribute(AWLiquidacionCertificado.TURNO_ANTERIOR, turnoAnterior);
		}

		request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
		request.getSession().setAttribute(CCiudadano.IDCIUDADANO, numId);
		request.getSession().setAttribute(CCiudadano.APELLIDO1, apellido1);
		request.getSession().setAttribute(CCiudadano.NOMBRE, nombres);
		request.getSession().setAttribute(CCiudadano.APELLIDO2, apellido2);
		request.getSession().setAttribute(CCiudadano.TELEFONO, telefono);
		//request.getSession().setAttribute(AWTurnoManualCertificado.TURNO_ANIO, sAnio);
		request.getSession().setAttribute(AWTurnoManualCertificado.TURNO_OFICINA, sOficina);
		request.getSession().setAttribute(AWTurnoManualCertificado.TURNO_CIRCULO, sOficina);
		request.getSession().setAttribute(AWTurnoManualCertificado.TURNO_CONSECUTIVO, sConsecutivo);
		request.getSession().setAttribute(AWTurnoManualCertificado.NUMERO_RECIBO, sNumeroRecibo);
		request.getSession().setAttribute(AWTurnoManualCertificado.VALOR_LIQUIDACION, sValor);
		request.getSession().setAttribute(AWTurnoManualCertificado.FECHA_INICIO, sFechaInicio);
	}

	private Calendar darFecha(String fechaInterfaz) {
		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");

		if (partido.length == 3) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);

			if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
				if (!calendar.after(Calendar.getInstance())) {
					return calendar;
				}
			}
		}

		return null;
	}

	public static String getFecha(Date fecha) {
		Calendar c = Calendar.getInstance();
		String fechaString = " ";
		if (fecha != null) {

			c.setTime(fecha);
			fechaString = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		}
		return fechaString;
	}

	public static String getValor(HttpServletRequest request, String key) {
		String tmp = request.getParameter(key);
		String valor = "&nbsp;";
		if (tmp != null) {
			if (tmp.trim().length() > 0) {
				valor = tmp;
			}
		}
		return valor;
	}

}
