package gov.sir.core.web.acciones.pagomayorvalor;

import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoActoDerechoRegistral;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CLiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosLiquidacionActoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosLiquidacionPagoMayorValorException;
import gov.sir.core.web.helpers.comun.ElementoLista;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la liquidación en el
 * proceso de pago por mayor valor. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 * @author ppabon
*/
public class AWLiquidacionPagoMayorValor extends SoporteAccionWeb {
	public final static String LIQUIDAR = "LIQUIDAR";
	public final static String AGREGAR_ACTO = "AGREGAR_ACTO";
	public final static String ELIMINAR_ACTO = "ELIMINAR_ACTO";
	public final static String CARGAR_DERECHOS = "CARGAR_DERECHOS";
	private String accion;
	private HttpSession session;

	/**
	 * Constructor de la clase
	 */
	public AWLiquidacionPagoMayorValor() {
		super();
	}

	/**
	 * Método principal que determina el método que debe ejecutarse en la acción web.
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);
		session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if (accion.equals(LIQUIDAR)) {
			return liquidar(request);
		} else if (accion.equals(AGREGAR_ACTO)) {
			return agregarActo(request);
		} else if (accion.equals(ELIMINAR_ACTO)) {
			return eliminarActo(request);
		} else if (accion.equals(CARGAR_DERECHOS)) {
			return cargarDerechos(request);
		} else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	/**
	 * Método para cargar en el combo los derechos.
	 * @param request
	 * @return
	 */
	private EvnLiquidacion cargarDerechos(HttpServletRequest request) throws AccionWebException {
		String tipo = request.getParameter(CActo.TIPO_ACTO);
		ValidacionParametrosLiquidacionPagoMayorValorException excepcion = new ValidacionParametrosLiquidacionPagoMayorValorException();

		if ((tipo == null) || tipo.equals("SIN_SELECCIONAR")) {
			excepcion.addError("Tipo de acto inválido");
		}

		if (excepcion.getErrores().size() > 0) {
			preservarInfoLiquidar(request);
			throw excepcion;
		}

		List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ACTO);
		Iterator it = tipos.iterator();
		List derechos = new ArrayList();

		while (it.hasNext()) {
			TipoActo sub = (TipoActo) it.next();

			if (tipo.equals(sub.getIdTipoActo())) {
				List checked = sub.getTiposDerechosRegistrales();
				Iterator it2 = checked.iterator();

				while (it2.hasNext()) {
					TipoActoDerechoRegistral ch = (TipoActoDerechoRegistral) it2.next();
					derechos.add(new ElementoLista(ch.getTipoDerechoRegistral().getIdTipoDerechoReg(), ch.getTipoDerechoRegistral().getNombre()));
				}

				session.setAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES", derechos);
				preservarInfoLiquidar(request);

				return null;
			}
		}

		preservarInfoLiquidar(request);
		return null;
	}

	/**
	 * Método que permite eliminar actos a la liquidación.
	 * @param request
	 * @return
	 */
	private EvnLiquidacion eliminarActo(HttpServletRequest request) throws AccionWebException {
		Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);
		int val = num.intValue();
		String item = request.getParameter("ITEM");

		boolean antes = true;

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);

			if (antes) {
				String id = CActo.ACTO + actual.toString();

				if (id.equals(item)) {
					antes = false;
					session.removeAttribute(item);
				}
			} else {
				Integer mover = new Integer(i - 1);
				Acto itemActual = (Acto) session.getAttribute(CActo.ACTO + actual.toString());
				session.setAttribute(CActo.ACTO + mover.toString(), itemActual);
				session.removeAttribute(CActo.ACTO + actual.toString());
			}
		}

		val--;
		session.setAttribute(CActo.NUM_ACTOS, new Integer(val));

		return null;
	}

	/**
	 * Método que permite agregar actos a la liquidación.
	 * @param request
	 * @return
	 */
	private EvnLiquidacion agregarActo(HttpServletRequest request) throws AccionWebException {
		ValidacionParametrosLiquidacionPagoMayorValorException excepcion = new ValidacionParametrosLiquidacionPagoMayorValorException();
		String tipoActo = request.getParameter(CActo.TIPO_ACTO);
		String tipoDerecho = request.getParameter(CActo.TIPO_DERECHO);
		String valorActo = request.getParameter(CActo.VALOR_ACTO);

		String cobraImp = request.getParameter(CActo.COBRA_IMPUESTO);
		String cuantia = request.getParameter(CActo.CUANTIA);
		String tipoTarifa = request.getParameter(CActo.TIPO_TARIFA);

		if ((tipoActo == null) || tipoActo.equals("SIN_SELECCIONAR")) {
			excepcion.addError("Tipo de acto inválido");
		}

		if ((tipoDerecho == null) || tipoDerecho.equals("SIN_SELECCIONAR")) {
			excepcion.addError("Tipo de derecho inválido");
		}

		if ((valorActo == null) || valorActo.equals("")) {
			excepcion.addError("Valor del acto inválido");
		} else {
			try {
				double v = Double.parseDouble(valorActo);
			} catch (NumberFormatException e) {
				excepcion.addError("Valor del acto inválido");
			}
		}

		if (cobraImp == null) {
			excepcion.addError("Debe elegir si se cobra impuesto o no");
		} else {
			if (cobraImp.equals("SI")) {
				if ((tipoTarifa == null) || tipoTarifa.equals("SIN_SELECCIONAR")) {
					excepcion.addError("Tipo de tarifa inválida");
				}
			}
		}

		if (excepcion.getErrores().size() > 0) {
			preservarInfoLiquidar(request);
			throw excepcion;
		}

		List tiposActo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ACTO);
		Iterator itA = tiposActo.iterator();
		Acto acto = new Acto();
		TipoActo actType = null;

		while (itA.hasNext()) {
			TipoActo temp = (TipoActo) itA.next();

			if (tipoActo.equals(temp.getIdTipoActo())) {
				actType = temp;
			}
		}

		acto.setTipoActo(actType);

		List derechosreg = actType.getTiposDerechosRegistrales();
		Iterator itD = derechosreg.iterator();
		TipoActoDerechoRegistral def = null;

		while (itD.hasNext()) {
			TipoActoDerechoRegistral tadr = (TipoActoDerechoRegistral) itD.next();

			if (tipoDerecho.equals(tadr.getTipoDerechoRegistral().getIdTipoDerechoReg())) {
				def = tadr;
			}
		}

		acto.setTipoDerechoReg(def);

		double actValue = Double.parseDouble(valorActo);
		acto.setCuantia(actValue);

		if (cobraImp.equals("SI")) {
			acto.setCobroImpuestos(true);

			List tipoImp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO);
			Iterator itImp = tipoImp.iterator();

			while (itImp.hasNext()) {
				TipoImpuesto impact = (TipoImpuesto) itImp.next();

				if (tipoTarifa.equals(impact.getIdTipoImpuesto())) {
					acto.setTipoImpuesto(impact);
				}
			}
		} else {
			acto.setCobroImpuestos(false);
		}

		preservarInfoLiquidar(request);

		Integer num = (Integer) session.getAttribute(CActo.NUM_ACTOS);
		session.setAttribute(CActo.ACTO + num.toString(), acto);

		int v = num.intValue();
		v++;

		Integer nuevo = new Integer(v);

		session.setAttribute(CActo.NUM_ACTOS, nuevo);

		eliminarInfoActoAsociada(request);

		return null;
	}

	/**
	 * Método que permite eliminar información asociada a los actos ingresados.
	 * @param request
	 */
	private void eliminarInfoActoAsociada(HttpServletRequest request) {
		session.removeAttribute(CActo.ID_ACTO);
		session.removeAttribute(CActo.VALOR_ACTO);
		session.removeAttribute(CActo.TIPO_ACTO);
		session.removeAttribute(CActo.TIPO_DERECHO);

	}

	/**
	 * Método que permite conservar la información en la sesión la información que se ha ingresado en un formulario.
	 * @param request
	 */
	private void preservarInfoLiquidar(HttpServletRequest request) {
		String tipoActo = request.getParameter(CActo.TIPO_ACTO);
		String idActo = request.getParameter(CActo.ID_ACTO);
		String tipoDerecho = request.getParameter(CActo.TIPO_DERECHO);
		String valorActo = request.getParameter(CActo.VALOR_ACTO);
		String cobrarImp = request.getParameter(CActo.COBRA_IMPUESTO);
		String impuesto = request.getParameter(CActo.IMPUESTO);
		String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
		String descripcion = request.getParameter(CActo.DESCRIPCION);
		String tipoTarifa = request.getParameter(CLiquidacionTurnoRegistro.TIPO_TARIFA);

		if ((tipoActo != null) && !tipoActo.equals("SIN_SELECCIONAR")) {
			session.setAttribute(CActo.TIPO_ACTO, tipoActo);
		}

		if ((tipoDerecho != null) && !tipoDerecho.equals("SIN_SELECCIONAR")) {
			session.setAttribute(CActo.TIPO_DERECHO, tipoDerecho);
		}

		if ((valorActo != null) && !valorActo.equals("")) {
			session.setAttribute(CActo.VALOR_ACTO, valorActo);
		}

		if ((cobrarImp != null) && !cobrarImp.equals("")) {
			session.setAttribute(CActo.COBRA_IMPUESTO, cobrarImp);
		}

		if ((impuesto != null) && !impuesto.equals("")) {
			session.setAttribute(CActo.IMPUESTO, impuesto);
		}

		if ((idActo != null) && !idActo.equals("")) {
			session.setAttribute(CActo.ID_ACTO, idActo);
		}

		if ((valorOtro != null) && !valorOtro.equals("")) {
			session.setAttribute(CActo.VALOR_OTRO_IMPUESTO, valorOtro);
		}

		if ((descripcion != null) && !descripcion.equals("")) {
			session.setAttribute(CActo.DESCRIPCION, descripcion);
		}

		if ((tipoTarifa != null) && !tipoTarifa.equals("SIN_SELECCIONAR")) {
			session.setAttribute(CLiquidacionTurnoRegistro.TIPO_TARIFA, tipoTarifa);
		}
	}

	/**
	 * Método que permite eliminar la información que se tiene en la sesión
	 * referente a la liquidación.
	 * @param request
	 */
	private void eliminarInfoLiquidar(HttpServletRequest request) {
		session.removeAttribute(CActo.TIPO_ACTO);
		session.removeAttribute(CActo.TIPO_DERECHO);
		session.removeAttribute(CActo.VALOR_ACTO);
		session.removeAttribute(CActo.COBRA_IMPUESTO);
		session.removeAttribute(CActo.IMPUESTO);
		session.removeAttribute(CActo.VALOR_OTRO_IMPUESTO);
		session.removeAttribute(CActo.DESCRIPCION);
		session.removeAttribute(CLiquidacionTurnoRegistro.TIPO_TARIFA);
	}

	/**
	 * Método que liquida el valor del pago por mayor valor.
	 * @param request
	 * @return
	 */
	private EvnLiquidacion liquidar(HttpServletRequest request) throws AccionWebException {
		if (((Turno) session.getAttribute(WebKeys.TURNO)).getSolicitud() instanceof SolicitudRegistro) {
			return liquidarRegistro(request);
		} else if (((Turno) session.getAttribute(WebKeys.TURNO)).getSolicitud() instanceof SolicitudCorreccion) {
			return liquidarCorrecion(request);
		}

		return null;
	}

	/**
	 * Método que recibe la información necesaria para mandar
	 * la liquidación a mayor valor el proceso de correcciones 
	 * @param request
	 * @return
	 */
	private EvnLiquidacion liquidarCorrecion(HttpServletRequest request) throws ValidacionParametrosLiquidacionPagoMayorValorException {
		ValidacionParametrosLiquidacionPagoMayorValorException excepcion = new ValidacionParametrosLiquidacionPagoMayorValorException();
		LiquidacionTurnoCorreccion liquidacion = new LiquidacionTurnoCorreccion();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

		if (turno == null) {
			turno = new Turno();
		}

		SolicitudCorreccion solicitud = (SolicitudCorreccion) turno.getSolicitud();
		solicitud.setTurno(turno);

		Integer cantidadActos = (Integer) session.getAttribute(CActo.NUM_ACTOS);
		int val = cantidadActos.intValue();
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = CActo.ACTO + actual.toString();
			Acto acto = (Acto) session.getAttribute(id);
			liquidacion.addActo(acto);
		}

		if (val <= 0) {
			excepcion.addError("Debe ingresar por lo menos un Acto");
		}

		String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
		String descripcion = request.getParameter(CLiquidacionTurnoRegistro.DESCRIPCION);

		if ((valorOtro == null) || valorOtro.equals("")) {
			if ((descripcion != null) && !descripcion.equals("")) {
				excepcion.addError("Debe ingresar un valor");
			}
		}

		if ((descripcion == null) || descripcion.equals("")) {
			if ((valorOtro != null) && !valorOtro.equals("")) {
				excepcion.addError("Debe ingresar una descripcion");
			}
		}

		if ((valorOtro != null) && !valorOtro.equals("")) {
			if ((descripcion != null) || !descripcion.equals("")) {
				try {
					float v = Float.parseFloat(valorOtro);
				} catch (NumberFormatException e) {
					excepcion.addError("Impuesto inválido");
				}
			}
		}

		if (excepcion.getErrores().size() > 0) {
			preservarInfoLiquidar(request);
			throw excepcion;
		}

		liquidacion.setSolicitud(solicitud);
		session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);

		return new EvnLiquidacion(usuario, liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO), (Estacion) session.getAttribute(WebKeys.ESTACION), false, usuarioNeg);
	}

	/**
	 * Método que recibe la información necesaria para mandar
	 * la liquidación a mayor valor el proceso de registro de documentos
	 * @param request
	 * @return
	 */
	private EvnLiquidacion liquidarRegistro(HttpServletRequest request) throws ValidacionParametrosLiquidacionActoException {
		ValidacionParametrosLiquidacionActoException excepcion = new ValidacionParametrosLiquidacionActoException();
		LiquidacionTurnoRegistro liquidacion = new LiquidacionTurnoRegistro();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);

		if (turno == null) {
			turno = new Turno();
		}

		SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
		solicitud.setTurno(turno);
		Integer cantidadActos = (Integer) session.getAttribute(CActo.NUM_ACTOS);
		int val = cantidadActos.intValue();
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = CActo.ACTO + actual.toString();
			Acto acto = (Acto) session.getAttribute(id);
			liquidacion.addActo(acto);
		}

		if (val <= 0) {
			excepcion.addError("Debe ingresar por lo menos un Acto");
		}

		String valorOtro = request.getParameter(CLiquidacionTurnoRegistro.VALOR_OTRO_IMPUESTO);
		String descripcion = request.getParameter(CLiquidacionTurnoRegistro.DESCRIPCION);

		if ((valorOtro == null) || valorOtro.equals("")) {
			if ((descripcion != null) && !descripcion.equals("")) {
				excepcion.addError("Debe ingresar un valor");
			}
		}

		if ((descripcion == null) || descripcion.equals("")) {
			if ((valorOtro != null) && !valorOtro.equals("")) {
				excepcion.addError("Debe ingresar una descripcion");
			}
		}

		if ((valorOtro != null) && !valorOtro.equals("")) {
			if ((descripcion != null) || !descripcion.equals("")) {
				try {
					float v = Float.parseFloat(valorOtro);
				} catch (NumberFormatException e) {
					excepcion.addError("Impuesto inválido");
				}
			}
		}

		if (excepcion.getErrores().size() > 0) {
			preservarInfoLiquidar(request);
			throw excepcion;
		}

		if ((valorOtro != null) && !valorOtro.equals("")) {
			liquidacion.setOtroImpuesto(descripcion);
			liquidacion.setValorOtroImp(Float.parseFloat(valorOtro));
		}

		liquidacion.setSolicitud(solicitud);
		session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);

		return new EvnLiquidacion(usuario, liquidacion, (Proceso) session.getAttribute(WebKeys.PROCESO), (Estacion) session.getAttribute(WebKeys.ESTACION), false, usuarioNeg);
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta respuesta) {

		if (respuesta != null) {
			if (respuesta instanceof EvnRespLiquidacion) {
				EvnRespLiquidacion evento = (EvnRespLiquidacion) respuesta;
				if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION)) {
					SolicitudRegistro solicitud = (SolicitudRegistro) evento.getPayload();

					if (solicitud != null) {
						session.setAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO, solicitud);
						session.setAttribute(CFolio.NUM_MATRICULAS, new Integer(0));
					}
				} else if (evento.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.LIQUIDACION)) {
					Liquidacion liquidacion = evento.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
					NumberFormat nf = NumberFormat.getInstance();
					session.setAttribute(WebKeys.VALOR_LIQUIDACION, String.valueOf(liquidacion.getValor()));

					DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
					AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());

					session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionEfectivo);
					session.setAttribute(CActo.NUM_ACTOS, new Integer(0));

				}
			} else if (respuesta instanceof EvnRespPago) {
				EvnRespPago evento = (EvnRespPago) respuesta;
				if (evento != null) {
					if (evento.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
						Turno turno = evento.getTurno();
						session.setAttribute(WebKeys.TURNO, turno);
					}
				}
			}
		}
	}
}
