/*
 * Created on 23-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRespTurnoManualCertificadoPago;
import gov.sir.core.eventos.administracion.EvnTurnoManualCertificadoPago;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CRespuestaCalificacion;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.AplicacionPagoNoAdicionadaException;
import gov.sir.core.web.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosRegistroPagoException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AWTurnoManualCertificadoPago extends SoporteAccionWeb {
	public static final String WF_LINK_FOTOCOPIAS_PAGOFOTOCOPIAS2FOTOCOPIA = "CONFIRMAR";

	/** Constante que identifica el campo del jsp donde se solicita el valor del pago en efectivo*/
	public final static String VALOR_EFECTIVO = "VALOR_EFECTIVO";

	/** Constante que identifica el campo del jsp donde se solicita el valor de una consignacion*/
	public final static String VALOR_CONSIGNACION = "VALOR_CONSIGNACION";

	/** Constante que identifica el campo del jsp donde se solicita el numero de la consignacion*/
	public final static String NUM_CONSIGNACION = "NUM_CONSIGNACION";

	/** Constante que identifica el campo del jsp donde se solicita la fecha de la consignacion*/
	public final static String FECHA_CONSIGNACION = "FECHA_CONSIGNACION";

	/** Constante que identifica el campo del jsp donde se solicita el valor a aplicar*/
	public final static String VALOR_APLICADO = "VALOR_APLICADO";

	/** Constante que identifica el campo del jsp donde se solicita el valor de un cheque*/
	public final static String VALOR_CHEQUE = "VALOR_CHEQUE";

	/** Constante que identifica el campo del jsp donde se solicita el numero del cheque*/
	public final static String NUM_CHEQUE = "NUM_CHEQUE";

	/** Constante que identifica el campo del jsp donde se solicita el numero de la cuenta del ciudadano*/
	public final static String NUM_CUENTA = "NUM_CUENTA";

	/** Constante que identifica el campo del jsp donde se solicita el numero de la sucursal del banco*/
	public final static String COD_SUCURSAL_BANCO = "COD_SUCURSAL_BANCO";

	/** Constante que identifica el campo del jsp donde se solicita el codigo del banco*/
	public final static String COD_BANCO = "COD_BANCO";

	/** Constante que identifica el campo del jsp donde se solicita el numero de la aplicacion a eliminar*/
	public static String NUMERO_APLICACION = "NUMERO_APLICACION";

	/**Constante que identifica el campo donde se tiene la forma de pago*/
	public static String FORMA_PAGO = "FORMA_PAGO";

	/** Constante que identifica el campo del jsp donde se solicita la fecha del cheque*/
	public final static String FECHA_CHEQUE = "FECHA_CHEQUE";

	/** Constante que identifica el campo oculto que indica si se debe asignar estación */
	public final static String ASIGNAR_ESTACION = "ASIGNAR_ESTACION";

	/**
	 * Constante que identifica que se desea verificar el pago hecho por el ciudadano
	 * frente a la liquidación
	 */
	public final static String VALIDAR = "VALIDAR";

	/**
	 * Constante que identifica que se desea confirmar la solicitud de un certificado
	 */
	public final static String PROCESAR = "PROCESAR";

	/**
	 * Constante que identifica que se desea procesar el pago y seguir a radicar el turno de registro.
	 */
	public final static String PROCESAR_REGISTRO_CONTINUAR = "PROCESAR_REGISTRO_CONTINUAR";    

	/**
	 * Constante que identifica que se desea añadir una nueva aplicacíon
	 * a la lista de aplicaciones de cheques o consignaciones
	 */
	public final static String ADICIONAR_APLICACION = "ADICIONAR_APLICACION";

	/**
	 * Constante que identifica que se desea añadir una nueva aplicacíon
	 * a la lista de aplicaciones de cheques o consignaciones
	 */
	public final static String ADICIONAR_APLICACION_VALIDACION = "ADICIONAR_APLICACION_VALIDACION";

	/**
	 * Constante que identifica que se desea eliminar una aplicacion de la
	 * lista de aplicaciones de cheques o consignaciones
	 */
	public final static String ELIMINAR_APLICACION = "ELIMINAR_APLICACION";

	/**
	 * Constante que identifica que se desea eliminar una aplicacion de la lista
	 * de aplicaciones de cheques o consignaciones en la pantalla de validacion
	 */
	public final static String ELIMINAR_APLICACION_VALIDACION = "ELIMINAR_APLICACION_VALIDACION";

	/**Constante que indica que el tipo de la aplicacion es efectivo */
	private final String APLICACION_PAGO_EFECTIVO = "EFECTIVO";

	/**Constante que indica que el tipo de la aplicacion es efectivo */
	private final String APLICACION_PAGO_TIMBRE_BANCO = "TIMBRE_BANCO";

	/**Constante que indica que el tipo de la aplicacion es un cheque */
	private final String APLICACION_PAGO_CHEQUE = "CHEQUE";

	/**Constante que indica que el tipo de aplicacion es consignacion */
	private final String APLICACION_PAGO_CONSIGNACION = "CONSIGNACION";

	/**
	 * Constante para avanzar un turno a la fase de pagos por mayor valor.
	 */
	public final String RESULTADO_MAYOR_VALOR = "EXITO";

	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	/**
	 * Crea una nueva instancia de AWPago
	 */
	public AWTurnoManualCertificadoPago() {
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

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if (accion.equals(VALIDAR)) {
			return validarPago(request);
		} else if (accion.equals(PROCESAR)) {
			return procesarPago(request);
		} else if (accion.equals(PROCESAR_REGISTRO_CONTINUAR)) {
			return procesarPago(request);
		} else if (accion.equals(ADICIONAR_APLICACION)) {
			return adicionarAplicacion(request);
		} else if (accion.equals(ADICIONAR_APLICACION_VALIDACION)) {
			return adicionarAplicacionValidacion(request);
		} else if (accion.equals(ELIMINAR_APLICACION)) {
			return eliminarAplicacion(request);
		} else if (accion.equals(ELIMINAR_APLICACION_VALIDACION)) {
			return eliminarAplicacionValidacion(request);
		} else {
			throw new AccionInvalidaException("La accion " + accion +
				" no es valida.");
		}
	}

	/**
	 * Este método es usado para eliminar una aplicación de pago de la lista de cheques o consignaciones
	 * actualmente configurada y redirigiar a la pantalla de validacion de aplicaciones
	 * @param request
	 * @return null
	 * @throws AccionWebException
	 */
	private Evento eliminarAplicacionValidacion(HttpServletRequest request)
		throws AccionWebException {
		request.getSession().removeAttribute(WebKeys.PAGO);
		eliminarAplicacion(request);
		request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));

		return null;
	}

	/**
	 * Este método es usado para adicionar una aplicación de pago sobre un cheque o consignación
	 * a la lista de cheques o consignaciones y redirigiar a la pantalla de validacion de aplicaciones
	 * @param request
	 * @return null
	 * @throws AccionWebException
	 */
	private Evento adicionarAplicacionValidacion(HttpServletRequest request)
		throws AccionWebException {
		request.getSession().removeAttribute(WebKeys.PAGO);
		adicionarAplicacion(request);
		request.getSession().setAttribute(WebKeys.PAGO, construirPago(request));

		return null;
	}

	/**
	 * Este método es usado para adicionar una aplicación de pago sobre un cheque o consignación
	 * a la lista de cheques o consignaciones
	 * @param request
	 * @return null
	 * @throws AccionWebException
	 */
	private Evento eliminarAplicacion(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		String formaPago = request.getParameter(AWTurnoManualCertificadoPago.FORMA_PAGO);

		if (formaPago == null) {
			ValidacionParametrosException vpe = new ValidacionParametrosException();
			vpe.addError("La forma de pago no es valida");
			throw vpe;
		}

		if (WebKeys.FORMA_PAGO_CHEQUE.equals(formaPago)) {
			List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);

			if (listaAplicaciones == null) {
				listaAplicaciones = new Vector();
			}

			try {
				int aplicacionNumero = Integer.parseInt(request.getParameter(
							AWTurnoManualCertificadoPago.NUMERO_APLICACION));
				listaAplicaciones.remove(aplicacionNumero);
				session.setAttribute(WebKeys.LISTA_CHEQUES, listaAplicaciones);
				List marcas = (List)session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
				marcas.remove(aplicacionNumero);
				session.setAttribute(WebKeys.LISTA_CHEQUES_MARCAS,marcas);
			} catch (NumberFormatException e) {
				throw new ParametroInvalidoException(
					"El número de aplicación a eliminar es inválido");
			} catch (ArrayIndexOutOfBoundsException e) {
				if (listaAplicaciones.size() == 0) {
					throw new ParametroInvalidoException("La lista es vacía");
				}

				throw new ParametroInvalidoException(
					"El índice de la aplicación a eliminar está fuera del rango");
			}
		} else if (WebKeys.FORMA_PAGO_CONSIGNACION.equals(formaPago)) {
			List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);

			if (listaAplicaciones == null) {
				listaAplicaciones = new Vector();
			}

			try {
				int aplicacionNumero = Integer.parseInt(request.getParameter(
							AWTurnoManualCertificadoPago.NUMERO_APLICACION));
				listaAplicaciones.remove(aplicacionNumero);
				session.setAttribute(WebKeys.LISTA_CONSIGNACIONES,
					listaAplicaciones);
				List marcas = (List)session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
				marcas.remove(aplicacionNumero);
				session.setAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS,marcas);
			} catch (NumberFormatException e) {
				throw new ParametroInvalidoException(
					"El número de aplicación a eliminar es inválido");
			} catch (ArrayIndexOutOfBoundsException e) {
				if (listaAplicaciones.size() == 0) {
					throw new ParametroInvalidoException("La lista es vacía");
				}

				throw new ParametroInvalidoException(
					"El índice de la aplicación a eliminar está fuera del rango");
			}
		} else if (WebKeys.FORMA_PAGO_EFECTIVO.equals(formaPago)) {
			request.getSession().removeAttribute(WebKeys.APLICACION_EFECTIVO);
		} else if (WebKeys.FORMA_PAGO_TIMBRE_BANCO.equals(formaPago)) {
			request.getSession().removeAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
		} else {
			ValidacionParametrosException vpe = new ValidacionParametrosException();
			vpe.addError("La forma de pago es inválida");
			throw vpe;
		}

		return null;
	}

	/**
	 * Este método es usado para eliminar una aplicación de pago de la lista de cheques o consignaciones
	 * actualmente configurada
	 * @param request
	 * @return null
	 * @throws AccionWebException
	 */
	private Evento adicionarAplicacion(HttpServletRequest request)
		throws AccionWebException {
		String formaPago = request.getParameter(AWTurnoManualCertificadoPago.FORMA_PAGO);
		String aplicacionNueva = "";
		String nombreLista = "";
		HttpSession session = request.getSession();
		List listaAplicaciones = null;

		if (WebKeys.FORMA_PAGO_CHEQUE.equals(formaPago)) {
			aplicacionNueva = APLICACION_PAGO_CHEQUE;
			nombreLista = WebKeys.LISTA_CHEQUES;
			listaAplicaciones = (List) session.getAttribute(nombreLista);
		} else if (WebKeys.FORMA_PAGO_CONSIGNACION.equals(formaPago)) {
			aplicacionNueva = APLICACION_PAGO_CONSIGNACION;
			nombreLista = WebKeys.LISTA_CONSIGNACIONES;
			listaAplicaciones = (List) session.getAttribute(nombreLista);
		} else if (WebKeys.FORMA_PAGO_EFECTIVO.equals(formaPago)) {
			aplicacionNueva = APLICACION_PAGO_EFECTIVO;
		} else if (WebKeys.FORMA_PAGO_TIMBRE_BANCO.equalsIgnoreCase(formaPago)) {
			aplicacionNueva = APLICACION_PAGO_TIMBRE_BANCO;
		} else {
			ValidacionParametrosException vpe = new ValidacionParametrosException();
			vpe.addError("La forma de pago es inválida");
			throw vpe;
		}

		double liquidacion = ((Liquidacion) session.getAttribute(WebKeys.LIQUIDACION)).getValor();

		if (listaAplicaciones == null) {
			listaAplicaciones = new Vector();
		}

		if (aplicacionNueva != APLICACION_PAGO_EFECTIVO) {
			//double valorTotal = 0;
		}

		AplicacionPago aplicacionPago = construirAplicacionPago(request,
				aplicacionNueva);

		if (WebKeys.FORMA_PAGO_TIMBRE_BANCO.equalsIgnoreCase(formaPago)) {
			DocPagoTimbreConstanciaLiquidacion doc;
			aplicacionPago.setValorAplicado(liquidacion);
			doc = (DocPagoTimbreConstanciaLiquidacion) aplicacionPago.getDocumentoPago();
			doc.setValorDocumento(liquidacion);
			session.setAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO, aplicacionPago);
		} else if (aplicacionNueva.equals(APLICACION_PAGO_EFECTIVO)) {
			session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
			session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionPago);
		} else {
			if (aplicacionPago != null) {
				if (!existeAplicacion(aplicacionPago, listaAplicaciones)) {
					Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO_AURIGA);
					listaAplicaciones.add(aplicacionPago);
					session.setAttribute(nombreLista, listaAplicaciones);
					return new EvnTurnoManualCertificadoPago(usuario, aplicacionPago);
				} else {
					ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
					vpe.addError("La aplicacion ya ha sido introducida");
					throw vpe;
				}
			} else {
				throw new AplicacionPagoNoAdicionadaException(
					"La aplicación de pago no pudo ser adicionada.");
			}
		}

		return null;
	}

	/**
	 * Este metodo verifica si la aplicacion de pago que se quiere ingresar
	 * se enceuntra o no en la lista.
	 * @param aplicacionPago
	 * @param listaAplicaciones
	 * @return true si encuentra la aplicación, false si no se encuentra
	 */
	private boolean existeAplicacion(AplicacionPago aplicacionPago,
		List listaAplicaciones) {
		for (Iterator it = listaAplicaciones.iterator(); it.hasNext();) {
			AplicacionPago aplicacion = (AplicacionPago) it.next();
			/*if (aplicacionPago.equals(aplicacion)) {
				return true;
			}*/
			DocumentoPago documentoAplicado = aplicacionPago.getDocumentoPago();
			DocumentoPago documento = aplicacion.getDocumentoPago();
			if((documento instanceof DocPagoCheque && documentoAplicado instanceof DocPagoCheque)){
				DocPagoCheque docAplicado = (DocPagoCheque)documentoAplicado;
				DocPagoCheque doc = (DocPagoCheque)documento;
				if((docAplicado.getBanco().getIdBanco().equals(doc.getBanco().getIdBanco())) 
						&& (docAplicado.getNoCheque().equals(doc.getNoCheque()))){
					return true;
				}
			} else if (documento instanceof DocPagoConsignacion && documentoAplicado instanceof DocPagoConsignacion){
				DocPagoConsignacion docAplicado = (DocPagoConsignacion)documentoAplicado;
				DocPagoConsignacion doc = (DocPagoConsignacion)documento;
				if((docAplicado.getBanco().getIdBanco().equals(doc.getBanco().getIdBanco())) 
						&& (docAplicado.getNoConsignacion().equals(doc.getNoConsignacion()))){
					return true;
				}				
			}
		}

		return false;
	}

	/**
	 * Este método se encarga de construir el pago a partir de la información proporcionada y construye
	 * un Evento Pago para la validación de éste.
	 * @param request
	 * @return Evento pago
	 * @throws ValidacionParametrosException
	 */
	private EvnTurnoManualCertificadoPago validarPago(HttpServletRequest request)
		throws ValidacionParametrosException {
		EvnTurnoManualCertificadoPago evnPago = null;
		Pago pago = construirPago(request);
		Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession()
																									  .getAttribute(WebKeys.USUARIO);

		Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
		evnPago = new EvnTurnoManualCertificadoPago(usuario, pago, proceso, estacion,
				EvnTurnoManualCertificadoPago.VALIDAR, usuarioSir);

		return evnPago;
	}

	/**
	* Recibe los datos de registro de pago y los que fueron validados en
	LIQUIDAR
	Valida que el pago sea exactamente igual a la
	liquidacion
	Registra el pago
	Crea una instancia del caso en el workflow
	Envia un recibo de pago a la impresora
	* @param request contenedor de tipo HttpServletRequest donde deben encontrarse los parametros arriba mencionados
	* @return Objeto de tipo EvnTurnoCertificado que contiene el usuario que esta haciendo la transaccion, el tipo de transacción solicitada (En este caso, SOLICITAR)
	* el tipo de Certificado, el número de matricula, el número de copias solicitadas, el ciudadano que está haciendo la solicitud,
	* el valor de la Liquidacion, y el valor del pago efectuado.
	 * @throws PagoNoProcesadoException
	 * @throws ValidacionParametrosException
	*/
	private EvnTurnoManualCertificadoPago procesarPago(HttpServletRequest request)
		throws PagoNoProcesadoException, ValidacionParametrosException {
		Pago pago = construirPago(request);

		EvnTurnoManualCertificadoPago evnPago = null;
		Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
		gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario) request.getSession()
																									  .getAttribute(WebKeys.USUARIO);

		pago.setUsuario(usuarioSir);
		//Se setea la fecha con la fecha de radicacion que esta con la de la liquidacion
		pago.setFecha(pago.getLiquidacion().getFecha());

		if (pago != null) {
			Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
			evnPago = new EvnTurnoManualCertificadoPago(usuario, pago, proceso, estacion,
					EvnTurnoManualCertificadoPago.PROCESAR, usuarioSir);

			//se setea el identificador único de usuario para la impresion del recibo
			evnPago.setUID(request.getSession().getId());

			//Se obtiene de sesión la lista de notas informativas creadas antes de la existencia del turno.
			//Se pasa esta nota dentro del Evento de pago.
			List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
			evnPago.setListaNotasSinTurno(listaNotas);
		} else {
			throw new PagoNoProcesadoException("No existe pago a procesar");
		}

		//Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		//evnPago.setCirculo(circulo);

		String impresora = (String) request.getSession().getAttribute(WebKeys.IMPRESORA);
		evnPago.setImpresora(impresora);

		Hashtable validaciones = (Hashtable) request.getSession().getAttribute(WebKeys.LISTA_VALIDACIONES_MASIVOS);

		if ((validaciones != null) && (evnPago != null)) {
			evnPago.setValidacionesMasivos(validaciones);
		}

		Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

		if ((rol != null) && (evnPago != null)) {
			evnPago.setRol(rol);
		}

		Boolean registro;

		registro = (Boolean) request.getSession().getAttribute("PAGO_REGISTRO_LIQUIDACION");
		registro = (registro == null) ? new Boolean(false) : registro;

		evnPago.setEsPagoRegistro(registro.booleanValue());
    
		String sIdTurno = (String) request.getSession().getAttribute("TURNO_CONSECUTIVO");
		String sAnio = (String) request.getSession().getAttribute("TURNO_ANIO");
		//String sCirculo = circulo.getIdCirculo();
		String sCirculo = (String) request.getSession().getAttribute("TURNO_CIRCULO");
		String sNumeroRecibo = (String) request.getSession().getAttribute("NUMERO_RECIBO");
		String sFechaInicio = (String) request.getSession().getAttribute("FECHA_INICIO");
		
		Turno turnoAnterior = (Turno) request.getSession().getAttribute("TURNO_REGISTRO_ASOCIADO");
		
		Calendar cFechaInicio = darFecha(sFechaInicio);
	  	Date dFechaInicio = cFechaInicio.getTime();
	  	
	  	if(sCirculo == null) {
	  		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
	  		sCirculo = circulo.getIdCirculo();
	  	}
    
		if(sIdTurno != null) {
			evnPago.setEsPagoTurnoManualCertificado(true);
			evnPago.setIdTurno(sIdTurno);
			evnPago.setIdRecibo(sNumeroRecibo);
			evnPago.setIdCirculo(sCirculo);
			evnPago.setAnio(sAnio);
			evnPago.setFechaInicio(dFechaInicio);
		}

		if (request.getParameter(ASIGNAR_ESTACION) != null) {
			evnPago.setAsignarEstacion(true);
		}

		if (null != pago.getLiquidacion()) {
			if (pago.getLiquidacion() instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia) {
				evnPago.setEsPagoFotocopias(true);

				Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
				Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
				Solicitud solicitud = pago.getLiquidacion().getSolicitud(); //request.getAttribute( WebKeys.SOLICITUD );

				evnPago.setSolicitud(solicitud);
				evnPago.setTurno(turno);
				evnPago.setFase(fase);
				evnPago.setRespuestaWF(WF_LINK_FOTOCOPIAS_PAGOFOTOCOPIAS2FOTOCOPIA);

				// para generar el recibo de pago
				evnPago.setSessionId(request.getSession().getId());
			}

			//SI ES EL CASO DE UNA LIQUIDACION DE REGISTRO DE DOCUMENTOS:
			//En caso de que la liquidaciones sea de regisro, y exista un turno, debe marcarse el
			//evento como de registro con evnPago.esPagoRegistro, para que en la acción de negocio, no
			//se intente nuevamente generar un turno.
			if (pago.getLiquidacion() instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro) {
				evnPago.setEsPagoRegistro(true);

				Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
				//Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
				evnPago.setFase(fase);
				evnPago.setSessionId(request.getSession().getId());
				evnPago.setRespuestaWF(RESULTADO_MAYOR_VALOR);
			}
        
			if (pago.getLiquidacion() instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion){
				evnPago.setEsPagoCorreccion(true);

				Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
				//Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
				evnPago.setFase(fase);
				evnPago.setSessionId(request.getSession().getId());
				evnPago.setRespuestaWF(RESULTADO_MAYOR_VALOR);
			}

			Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
			Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
			Solicitud solicitud = pago.getLiquidacion().getSolicitud(); //request.getAttribute( WebKeys.SOLICITUD );
			evnPago.setSolicitud(solicitud);
			evnPago.setTurno(turno);
			evnPago.setFase(fase);
			evnPago.setTurnoAnterior(turnoAnterior);
			evnPago.setRespuestaCalificacion( (String) request.getSession().getAttribute(CRespuestaCalificacion.RESPUESTA_CALIFICACION_TURNO_REGISTRO_ADMINISTRACION));
			
		}

		return evnPago;
	}

	/**
	 * Este método permite procesar cualquier evento de respuesta de la capa
	 * de negocio, en caso de recibir alguno.
	 * @param request la información del formulario
	 * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
	 * de existir alguno
	 */
	public void doEnd(HttpServletRequest request,
		EventoRespuesta eventoRespuesta) {
		EvnRespTurnoManualCertificadoPago respuesta = (EvnRespTurnoManualCertificadoPago) eventoRespuesta;
		HttpSession session = request.getSession();

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(EvnRespTurnoManualCertificadoPago.PROCESAMIENTO_PAGO)) {
				Pago pago = respuesta.getPago();
				Turno turno = respuesta.getTurno();
				Liquidacion liqTemp = (Liquidacion)request.getSession().getAttribute(WebKeys.LIQUIDACION);

				request.getSession().setAttribute(WebKeys.TURNO, turno);
				request.getSession().setAttribute(WebKeys.PAGO, pago);
				request.getSession().removeAttribute(WebKeys.LIQUIDACION);
            
				//CASO ESPECIAL DE REGISTRO QUE SE PROCESA EL PAGO PERO NO SE CREA EL TURNO.
				if(respuesta.getSolicitud()!=null){
					//session.removeAttribute(WebKeys.LIQUIDACION);
					session.setAttribute(WebKeys.LIQUIDACION,liqTemp);
					session.setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION, "" + respuesta.getValorSecuencial());
					session.setAttribute(WebKeys.SECUENCIAL_RECIBO_CONSULTADO,	"" + respuesta.getValorSecuencial());
					session.setAttribute(WebKeys.SOLICITUD, respuesta.getSolicitud());
				
					if(respuesta.getEsCajeroRegistro()!=null){
						session.setAttribute(WebKeys.ES_CAJERO_REGISTRO, respuesta.getEsCajeroRegistro());
					}		
				}
            
				request.getSession().setAttribute(WebKeys.PAGO, pago);
				//request.getSession().setAttribute(WebKeys.PAGO, pago);
				
				request.getSession().removeAttribute("TURNO_ANIO");
				request.getSession().removeAttribute("TURNO_CIRCULO");
				request.getSession().removeAttribute("TURNO_CONSECUTIVO");
				request.getSession().removeAttribute("TURNO_OFICINA");
				request.getSession().removeAttribute("FECHA_INICIO");
				request.getSession().removeAttribute("NUMERO_RECIBO");
				request.getSession().removeAttribute(WebKeys.VALOR_LIQUIDACION);
				
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
                
				session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
				session.removeAttribute(WebKeys.LISTA_CHEQUES);
				session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
				session.removeAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);
				session.removeAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
				session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);

				
				session.removeAttribute("VER_ANTIGUO_SISTEMA");
				session.removeAttribute(WebKeys.OCULTAR);
				
				session.removeAttribute(AWTurnoManualCertificado.TURNO_REGISTRO_ASOCIADO);
				session.removeAttribute(AWTurnoManualCertificado.ID_TURNO_REGISTRO_ASOCIADO);
				
				session.removeAttribute(AWTurnoManualCertificado.COPIAS);
				session.removeAttribute(AWTurnoManualCertificado.CIRCULO);
            
			} else if (respuesta.getTipoEvento().equals(EvnRespTurnoManualCertificadoPago.VALIDACION_PAGO)) {
				Pago pago = respuesta.getPago();

				if (pago != null) {
					request.getSession().setAttribute(WebKeys.PAGO, pago);
					request.getSession().setAttribute(WebKeys.PRECISION_PAGO,
						new Double(respuesta.getRangoAceptablePago()));
				}
			} else if (respuesta.getTipoEvento().equals(EvnRespTurnoManualCertificadoPago.VERIFICACION_APLICACION_PAGO)) {
				AplicacionPago aplicacionPago = (AplicacionPago) respuesta.getPayload();
				DocumentoPago documento = aplicacionPago.getDocumentoPago();
				if(documento instanceof DocPagoCheque){
					boolean marca = false;
					List marcas = (List) session.getAttribute(WebKeys.LISTA_CHEQUES_MARCAS);
					if(marcas == null){
						marcas = new ArrayList();
					}
					DocPagoCheque cheque = (DocPagoCheque)documento;
					List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);
					AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size()-1);
					DocPagoCheque chequeAplicado = (DocPagoCheque)aplicacionAplicada.getDocumentoPago();					
					if(chequeAplicado.getValorDocumento() != cheque.getValorDocumento()){
						chequeAplicado.setValorDocumento(cheque.getValorDocumento());
						marca = true;
					} else {
						marca = false;
					}
					if(chequeAplicado.getSaldoDocumento() != cheque.getSaldoDocumento()){
						chequeAplicado.setSaldoDocumento(cheque.getSaldoDocumento());
						marca = true;
					} else {
						marca = false;
					}
					if(!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > chequeAplicado.getSaldoDocumento()){
						aplicacionAplicada.setValorAplicado(chequeAplicado.getSaldoDocumento());
						marca = true;										
					} else {
						marca = false;
					}
					aplicacionAplicada.setDocumentoPago(chequeAplicado);
					aplicaciones.set(aplicaciones.size()-1,aplicacionAplicada);
					marcas.add(new Boolean(marca));
					session.setAttribute(WebKeys.LISTA_CHEQUES,aplicaciones);
					session.setAttribute(WebKeys.LISTA_CHEQUES_MARCAS,marcas);
				} else if(documento instanceof DocPagoConsignacion){
					List marcas = (ArrayList) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS);
					boolean marca = false;
					if(marcas == null){
						marcas = new ArrayList();
					}
					DocPagoConsignacion consignacion = (DocPagoConsignacion)documento;
					List aplicaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);
					AplicacionPago aplicacionAplicada = (AplicacionPago) aplicaciones.get(aplicaciones.size()-1);
					DocPagoConsignacion consignacionAplicado = (DocPagoConsignacion)aplicacionAplicada.getDocumentoPago();					
					if(consignacionAplicado.getValorDocumento() != consignacion.getValorDocumento()){
						consignacionAplicado.setValorDocumento(consignacion.getValorDocumento());
						marca = true;
					} else {
						marca = false;
					}
					if(consignacionAplicado.getSaldoDocumento() != consignacion.getSaldoDocumento()){
						consignacionAplicado.setSaldoDocumento(consignacion.getSaldoDocumento());
						marca = true;
					} else {
						marca = false;
					}
					if(!respuesta.isNueva() && aplicacionAplicada.getValorAplicado() > consignacionAplicado.getSaldoDocumento()){
						aplicacionAplicada.setValorAplicado(consignacionAplicado.getSaldoDocumento());
						marca = true;										
					} else {
						marca = false;
					}
					aplicacionAplicada.setDocumentoPago(consignacionAplicado);
					aplicaciones.set(aplicaciones.size()-1,aplicacionAplicada);
					marcas.add(new Boolean(marca));
					session.setAttribute(WebKeys.LISTA_CONSIGNACIONES,aplicaciones);
					session.setAttribute(WebKeys.LISTA_CONSIGNACIONES_MARCAS,marcas);
				}
			}
		 }
	}

	/**
	 * Este método obtiene todos los valores de los parámetros apropiados de acuerdo a la forma de pago
	 * empleada y construye el objeto AplicacionPago correspondiente.
	 * @param request
	 * @param formaPago
	 * @return Objeto AplicacioónPago con los datos asociados a la forma de pago
	 * @throws ValidacionParametrosException
	 */
	private AplicacionPago construirAplicacionPago(HttpServletRequest request,
		String formaPago) throws ValidacionParametrosException {
		ValidacionParametrosException vpe = new ValidacionParametrosRegistroPagoException();
		HttpSession session = request.getSession();
		List listaBancos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_BANCOS);


		if (APLICACION_PAGO_CHEQUE.equals(formaPago)) {
			return construirAplicacionPagoCheque(request, vpe, listaBancos);
		} else if (APLICACION_PAGO_CONSIGNACION.equals(formaPago)) {
			return construirAplicacionPagoConsignacion(request, vpe, listaBancos);
		} else if (APLICACION_PAGO_EFECTIVO.equals(formaPago)) {
			return construirAplicacionPagoEfectivo(request, vpe);
		} else if (APLICACION_PAGO_TIMBRE_BANCO.equals(formaPago)) {
			return this.construirAplicacionPagoTimbreBanco(request, vpe);
		} else {
			vpe.addError("La forma de pago es inválida");
			throw vpe;
		}
	}

	/**
	 * @param request
	 * @param vpe
	 * @return Objeto pago con efectivo
	 * @throws ValidacionParametrosException
	 */
	private AplicacionPago construirAplicacionPagoEfectivo(
		HttpServletRequest request, ValidacionParametrosException vpe)
		throws ValidacionParametrosException {
		double valorEfectivo = 0.0d;

		try {
			valorEfectivo = Double.parseDouble(request.getParameter(
						AWTurnoManualCertificadoPago.VALOR_EFECTIVO));

			if (valorEfectivo < 0) {
				vpe.addError("El valor no puede ser negativo");
			}
		} catch (NumberFormatException e) {
			vpe.addError("El valor en efectivo no es válido");
		}

		if (vpe.getErrores().size() > 0) {
			throw vpe;
		}

		DocumentoPago documentoEfectivo = new DocPagoEfectivo(valorEfectivo);
		AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo,
				valorEfectivo);

		return aplicacionEfectivo;
	}

	/**
	 * Construir la Aplicacion Pago para forma de pago con timbre de banco
	 * para la constancia de liquidación de registro
	 *
	 * @param request
	 * @param vpe
	 * @return Objeto pago con timbre banco
	 * @throws ValidacionParametrosException
	 */
	private AplicacionPago construirAplicacionPagoTimbreBanco(
		HttpServletRequest request, ValidacionParametrosException vpe)
		throws ValidacionParametrosException {
		String numeroTimbre = "";
		String fechaString = request.getParameter(WebKeys.CAMPO_FECHA_PAGO_TIMBRE);

		numeroTimbre = request.getParameter(WebKeys.CAMPO_NUMERO_TIMBRE_BANCO);
		numeroTimbre = (numeroTimbre == null) ? "" : numeroTimbre;

		Date d = null;

		try {
			d = DateFormatUtil.parse(fechaString);
		} catch (ParseException e) {
			vpe.addError("El valor de la fecha de pago del timbre no es válido");
		}

		if (numeroTimbre.length() == 0) {
			//vpe.addError("El campo número timbre no puede estar vacío");
		}

		if (vpe.getErrores().size() > 0) {
			throw vpe;
		}

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(d);

		DocumentoPago documentopago = new DocPagoTimbreConstanciaLiquidacion(0,
				numeroTimbre, fechaString);
		AplicacionPago aplicacionEfectivo = new AplicacionPago(documentopago, 0);

		return aplicacionEfectivo;
	}

	/**
	 * @param request
	 * @param vpe
	 * @param listaBancos
	 * @return  Objeto con el pago de la consignación
	 * @throws ValidacionParametrosException
	 */
	private AplicacionPago construirAplicacionPagoConsignacion(
		HttpServletRequest request, ValidacionParametrosException vpe,
		List listaBancos) throws ValidacionParametrosException {
			
		String codBanco = request.getParameter(AWTurnoManualCertificadoPago.COD_BANCO);

		if ((codBanco == null) ||
				getNombreBanco(codBanco, listaBancos).equals("")) {
			vpe.addError("Para la consignación el código del Banco no puede ser vacío");
		}

		String codSucursal = request.getParameter(AWTurnoManualCertificadoPago.COD_SUCURSAL_BANCO);

		/*
		if(codSucursal != null){
				vpe.addError("El codigo de la sucursal es inválido");
		}*/
		String strFecha = request.getParameter(AWTurnoManualCertificadoPago.FECHA_CONSIGNACION);

		if (strFecha == null) {
			vpe.addError("Para la consignación la fecha de consigación no puede ser vacía");
		}

		Calendar fecha = darFecha(strFecha);

		if (fecha == null) {
			vpe.addError("Para la consignación la fecha dada es invalida");
		}

		String numConsignacion = request.getParameter(AWTurnoManualCertificadoPago.NUM_CONSIGNACION);

		if (numConsignacion == null) {
			vpe.addError("El número de consignación es inválido");
		}

		double valor = 0.0d;
		double valorAplicado = 0.0d;

		try {
			valor = Double.parseDouble(request.getParameter(
			AWTurnoManualCertificadoPago.VALOR_CONSIGNACION));

			if (valor <= 0) {
				vpe.addError("Para la consignación el valor del documento no puede ser negativo");
			}
		} catch (NumberFormatException e) {
			vpe.addError("Para la consignación el valor de la consignación es inválido");
		} catch (NullPointerException e) {
			vpe.addError("Para la consignación el valor de la consignación es inválido");
		}

		try {
			valorAplicado = Double.parseDouble(request.getParameter(
				AWTurnoManualCertificadoPago.VALOR_APLICADO));

			if (valorAplicado <= 0) {
				vpe.addError("Para la consignación el valor a aplicar no puede ser negativo");
			}

			if ((valor > 0) && (valorAplicado > valor)) {
				vpe.addError(
					"Para la consignación el valor a aplicar no puede ser mayor al valor del documento.");
			}
		} catch (NumberFormatException e) {
			vpe.addError("El valor aplicado de la consignación es inválido");
		} catch (NullPointerException e) {
			vpe.addError("El valor aplicado de la consignación es inválido");
		}

		if (vpe.getErrores().size() > 0) {
			throw vpe;
		}

		String nomBanco = getNombreBanco(codBanco, listaBancos);
		Banco banco = new Banco(codBanco, nomBanco);
		DocumentoPago documentoConsignacion = new DocPagoConsignacion(banco,
				codSucursal, strFecha, numConsignacion, valor);
		AplicacionPago aplicacionConsignacion = new AplicacionPago(documentoConsignacion,
				valorAplicado);

		return aplicacionConsignacion;
	}

	/**
	 * @param request
	 * @param vpe
	 * @param listaBancos
	 * @return Retorna un objeto con un Pago de cheque
	 * @throws ValidacionParametrosException
	 */
	private AplicacionPago construirAplicacionPagoCheque(
		HttpServletRequest request, ValidacionParametrosException vpe,
		List listaBancos) throws ValidacionParametrosException {
		String codBanco = request.getParameter(AWTurnoManualCertificadoPago.COD_BANCO);

		if ((codBanco == null) ||
				getNombreBanco(codBanco, listaBancos).equals("")) {
			vpe.addError("Para el cheque el codigo del Banco es inválido");
		}

		String codSucursal = request.getParameter(AWTurnoManualCertificadoPago.COD_SUCURSAL_BANCO);
		codSucursal = (codSucursal == null) ? "" : codSucursal;

		/*
		if(codSucursal == null){
				vpe.addError("El codigo de la sucursal es inválido");
		}
		*/
		String strFechaCheque = request.getParameter(AWTurnoManualCertificadoPago.FECHA_CHEQUE);
		String numCuenta = request.getParameter(AWTurnoManualCertificadoPago.NUM_CUENTA);
		numCuenta = (numCuenta == null) ? "" : numCuenta;

		/*
		if (numCuenta == null) {
			vpe.addError("El número de cuenta es inválido");
		}
		*/
		String numCheque = request.getParameter(AWTurnoManualCertificadoPago.NUM_CHEQUE);
		numCheque = (numCheque == null) ? "" : numCheque;

		if (numCheque.length() == 0) {
			vpe.addError("Para el cheque el número de cheque es inválido");
		}

		double valor = 0.0d;
		double valorAplicado = 0.0d;

		try {
			valor = Double.parseDouble(request.getParameter(AWTurnoManualCertificadoPago.VALOR_CHEQUE));

			if (valor <= 0) {
				vpe.addError("El valor del cheque no puede ser negativo");
			}
		} catch (NumberFormatException e) {
			vpe.addError("El valor del cheque es inválido");
		} catch (NullPointerException e) {
			vpe.addError("El valor del cheque es inválido");
		}

		try {
			valorAplicado = Double.parseDouble(request.getParameter(
				AWTurnoManualCertificadoPago.VALOR_APLICADO));

			if (valorAplicado <= 0) {
				vpe.addError("Para el cheque el valor a aplicar no puede ser negativo");
			}

			if ((valor > 0) && (valorAplicado > valor)) {
				vpe.addError(
					"Para el cheque el valor a aplicar no puede ser mayor al valor del documento.");
			}
		} catch (NumberFormatException e) {
			vpe.addError("El valor aplicado del cheque es inválido");
		} catch (NullPointerException e) {
			vpe.addError("El valor aplicado del cheque es inválido");
		}

		if (vpe.getErrores().size() > 0) {
			throw vpe;
		}

		String nomBanco = getNombreBanco(codBanco, listaBancos);
		Banco banco = new Banco(codBanco, nomBanco);
		DocumentoPago documentoCheque = new DocPagoCheque(banco, codSucursal,
				numCuenta, numCheque, valor);
		((DocPagoCheque) documentoCheque).setFecha(strFechaCheque);

		AplicacionPago aplicacionCheque = new AplicacionPago(documentoCheque,
				valorAplicado);

		return aplicacionCheque;
	}

	/**
	 * Este método se encarga de buscar en la lista de Bancos el nombre del banco correspondiente
	 * al código de banco proporcionado.
	 * @param codBanco
	 * @param listaBancos
	 * @return Nombre del banco
	 */
	private String getNombreBanco(String codBanco, List listaBancos) {
		if ((codBanco != null) && (listaBancos != null)) {
			for (int i = 0; i < listaBancos.size(); i++) {
				Banco banco = (Banco) listaBancos.get(i);

				if (banco.getIdBanco().equals(codBanco)) {
					return banco.getNombre();
				}
			}
		}

		return "";
	}

	/**
	 * Este método se encarga de construir el objeto Pago a partir de las aplicaciones de pago empleadas
	 * @param request
	 * @return  Pago con todos los Documentos de pago asociados
	 * @throws ValidacionParametrosException
	 */
	private Pago construirPago(HttpServletRequest request)
		throws ValidacionParametrosException {
		HttpSession session = request.getSession();

		//Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
		Liquidacion liquidacion = (Liquidacion) request.getSession()
													   .getAttribute(WebKeys.LIQUIDACION);
		String sNumeroRecibo = (String) request.getSession().getAttribute("NUMERO_RECIBO");

		if (liquidacion == null) {
			ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();
			vpe.addError("Liquidación inexistente");
			throw vpe;
		}

		Pago pago = new Pago(liquidacion, null);
		pago.setNumRecibo(sNumeroRecibo);
		ValidacionParametrosRegistroPagoException vpe = new ValidacionParametrosRegistroPagoException();

		AplicacionPago appTimbre = (AplicacionPago) session.getAttribute(WebKeys.FORMA_PAGO_TIMBRE_BANCO);

		if (appTimbre != null) {
			pago.addAplicacionPago(appTimbre);
		} else {
			List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CHEQUES);

			if (listaAplicaciones != null) {
				for (int i = 0; i < listaAplicaciones.size(); i++) {
					pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(
							i));
				}
			}

			listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_CONSIGNACIONES);

			if (listaAplicaciones != null) {
				for (int i = 0; i < listaAplicaciones.size(); i++) {
					pago.addAplicacionPago((AplicacionPago) listaAplicaciones.get(
							i));
				}
			}

			AplicacionPago appEfectivo = (AplicacionPago) session.getAttribute(WebKeys.APLICACION_EFECTIVO);

			if (appEfectivo != null) {
				pago.addAplicacionPago(appEfectivo);
			}
		}

		if (pago.getAplicacionPagos().isEmpty()) {
			vpe.addError("No se ha registrado ningún tipo de pago.");
			throw vpe;
		}

		return pago;
	}

	/**
	 * Este metodo verifica la forma de las fechas y que sea una fecha valida.
	 * @param fechaInterfaz
	 * @return Calendar
	 */
	private static Calendar darFecha(String fechaInterfaz) {
		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");
		Date dHoy = new Date();

		Calendar hoy = Calendar.getInstance();
		hoy.setTime(dHoy);

		if ((partido.length == 3) && (partido[0].length() > 0) &&
				(partido[1].length() > 0) && (partido[2].length() > 0)) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);

			if ((calendar.get(Calendar.YEAR) == año) &&
					(calendar.get(Calendar.MONTH) == mes) &&
					(calendar.get(Calendar.DAY_OF_MONTH) == dia) &&
					!calendar.after(hoy)) {
				return calendar;
			}
		}

		return null;
	}
}
