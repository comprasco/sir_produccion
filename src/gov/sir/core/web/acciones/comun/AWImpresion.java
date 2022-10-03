/*
 * Created on 16-mar-2005
 *
 */
package gov.sir.core.web.acciones.comun;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnImpresorasCirculo;
import gov.sir.core.eventos.comun.EvnRespImpresorasCirculo;
import gov.sir.core.negocio.modelo.CirculoImpresora;
import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.TipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;

/**
 * Recibe la notificación de impresoras disponibles de cada oficina y las
 * almacena en el ServletContext para que el cajero pueda luego seleccionar la
 * impresora donde desea imprimir el certificado de una lista desplegable.
 * 
 * @author dcantor
 * 
 */
public class AWImpresion extends SoporteAccionWeb {
	
	public final static String REGISTRAR_CLIENTE = "REGISTRAR_CLIENTE";

	public final static String ELIMINAR_CLIENTE = "ELIMINAR_CLIENTE";

	public final static String VERIFICAR_DESCARGA_APPLET = "VERIFICAR_DESCARGA_APPLET";

	public final static String DESCARGAR_IMPRIMIBLE = "DESCARGAR_IMPRIMIBLE";

	public final static String REGISTRAR_FALLO_IMPRESION = "REGISTRAR_FALLO_IMPRESION";

	public final static String REGISTRAR_IMPRESORAS_CIRCULO = "REGISTRAR_IMPRESORAS_CIRCULO";

	public final static String CONSULTAR_IMPRESIONES_FALLIDAS = "CONSULTAR_IMPRESIONES_FALLIDAS";

	public final static String CONSULTAR_LISTA_REGLAS = "CONSULTAR_LISTA_REGLAS";

	public final static String ACTUALIZAR_REGLAS = "ACTUALIZAR_REGLAS";

	public final static String CARGAR_CONFIGURACION_ACTUAL = "CARGAR_CONFIGURACION_ACTUAL";

	public final static String VERIFICAR_SESION = "VERIFICAR_SESION";

	public final static String CARGAR_PARAMETROS_CONFIGURACION = "CARGAR_PARAMETROS_CONFIGURACION";

	public AWImpresion() {
		super();
	}

	public Evento perform(HttpServletRequest request) throws AccionWebException {

		String accion = (String) request.getParameter(WebKeys.ACCION);

		if (accion.equals(AWImpresion.REGISTRAR_IMPRESORAS_CIRCULO)) {
			return registrarImpresoras(request);
		} else if (accion.equals(AWImpresion.REGISTRAR_CLIENTE)) {
			return registrarClienteImpresion(request);
		} else if (accion.equals(AWImpresion.ELIMINAR_CLIENTE)) {
			return eliminarClienteImpresion(request);
		} else if (accion.equals(AWImpresion.DESCARGAR_IMPRIMIBLE)) {
			return descargarImprimible(request);
		} else if (accion.equals(AWImpresion.CONSULTAR_IMPRESIONES_FALLIDAS)) {
			return consultarImpresionesFallidas(request);
		} else if (accion.equals(AWImpresion.REGISTRAR_FALLO_IMPRESION)) {
			return disminuirNumeroImpresiones(request);
		} else if (accion.equals(AWImpresion.VERIFICAR_DESCARGA_APPLET)) {
			return null;
		} else if (accion.equals(AWImpresion.CONSULTAR_LISTA_REGLAS)) {
			return cargarListaReglas(request);
		} else if (accion.equals(AWImpresion.ACTUALIZAR_REGLAS)) {
			return actualizarReglas(request);
		} else if (accion.equals(AWImpresion.CARGAR_CONFIGURACION_ACTUAL)) {
			return cargarConfiguracionActual(request);
		} else if (accion.equals(AWImpresion.VERIFICAR_SESION)) {
			return verificarSessionUsuario(request);
		} else if (accion.equals(AWImpresion.CARGAR_PARAMETROS_CONFIGURACION)) {
			return cargarParametrosConfiguracion(request);
		}

		return null;

	}

	private Evento cargarParametrosConfiguracion(HttpServletRequest request) {
		String uid = (String) request.getParameter(CImpresion.UID);
		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);
		return new EvnImpresorasCirculo(uAuriga,
				EvnImpresorasCirculo.CARGAR_PARAMETROS_CONFIGURACION, uid);
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento verificarSessionUsuario(HttpServletRequest request) {
		Boolean respuesta = new Boolean("false");
		String uid = (String) request.getParameter(CImpresion.UID);
		String listeningPort = (String) request
				.getParameter(CImpresion.LISTENINGPORT);

		Map sesiones = (Map) request.getSession().getServletContext()
				.getAttribute(WebKeys.LISTA_SESIONES);
		
		Log.getInstance().debug(AWImpresion.class, "[sesiones.get(uid)] " + sesiones.get(uid) ); 
		Log.getInstance().debug(AWImpresion.class, "[listeningPort] " + listeningPort );

		if (sesiones.get(uid) != null
				&& sesiones.get(uid).equals(listeningPort)) {
			respuesta = new Boolean("true");
		}
		
		request.setAttribute(WebKeys.IMPRESION_EXISTE_SESION, respuesta);

		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento cargarConfiguracionActual(HttpServletRequest request) {
		String uid = (String) request.getParameter(CImpresion.UID);
		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);
		return new EvnImpresorasCirculo(uAuriga,
				EvnImpresorasCirculo.CARGAR_CONFIGURACION_ACTUAL, uid);
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento actualizarReglas(HttpServletRequest request) {
		String uid = (String) request.getParameter(CImpresion.UID);
		Set listaParams = request.getParameterMap().keySet();
		Iterator itReglas = listaParams.iterator();
		Map configuracion = new Hashtable();
		while (itReglas.hasNext()) {
			String paramRegla = (String) itReglas.next();
			if (paramRegla.startsWith("REGLA")) {
				TipoImprimible tipo = new TipoImprimible();
				tipo.setIdTipoImprimible(request.getParameter(paramRegla));
				List impresoras = new ArrayList();
				Iterator itImpresorasRegla = listaParams.iterator();
				while (itImpresorasRegla.hasNext()) {
					String paramImpr = (String) itImpresorasRegla.next();
					if (paramImpr.startsWith("IMPR")
							&& paramImpr.indexOf(paramRegla + "_") != -1) {
						CirculoImpresora impresora = new CirculoImpresora();
						impresora.setIdImpresora(request
								.getParameter(paramImpr).replaceAll("_", " "));
						impresora.setIdCirculo(uid);
						impresora.setIdTipoImprimible(tipo
								.getIdTipoImprimible());
						if (paramImpr.endsWith("PRED")) {
							impresora.setPredeterminada(true);
						}
						impresoras.add(impresora);
					}
				}
				configuracion.put(tipo, impresoras);
			}
		}

		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);
		return new EvnImpresorasCirculo(uAuriga,
				EvnImpresorasCirculo.ACTUALIZAR_LISTA_IMPRESORAS, uid,
				configuracion);
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento cargarListaReglas(HttpServletRequest request) {
		String circulo = (String) request.getParameter("UID");
		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		Log.getInstance().debug(AWImpresion.class, " [IMPRESION AWImpresion] uAuriga" + uAuriga);
		if (uAuriga == null) {
			String id = request.getParameter("USUARIO_ID");
			uAuriga = new Usuario();
			uAuriga.setUsuarioId(id);
		}

		return new EvnImpresorasCirculo(uAuriga,
				EvnImpresorasCirculo.CONSULTAR_LISTA_REGLAS, circulo);
	}

	/**
	 * Método que es llamado cuando se quiere registrar las impresoras del
	 * circulo en la base de datos.
	 * 
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	public Evento registrarImpresoras(HttpServletRequest request)
			throws AccionWebException {

		String circulo = (String) request.getParameter("UID");

		if (circulo == null || circulo.length() == 0) {
			Log.getInstance().error(AWImpresion.class, "no llego el circulo");
			return null;
		}

		List listaImpresorasOficina = new ArrayList();
		Iterator i = request.getParameterMap().keySet().iterator();
		Log.getInstance().debug(AWImpresion.class, "----------------------------------------------");
		Log.getInstance().debug(AWImpresion.class, "INFORMACION IMPRESORAS OFICINA " + circulo);

		while (i.hasNext()) {
			String id = (String) i.next();
			String imp = (String) request.getParameter(id);
			Log.getInstance().info(AWImpresion.class, id + ":" + imp);
			if (id.startsWith("IMP")) {
				listaImpresorasOficina.add(imp);
			}
		}
		Log.getInstance().debug(AWImpresion.class, "----------------------------------------------");

		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);
		return new EvnImpresorasCirculo(uAuriga, circulo,
				listaImpresorasOficina,
				EvnImpresorasCirculo.REGISTRAR_IMPRESORAS_CIRCULO);
	}

	/**
	 * Método que registra en la base de datos la información de un nuevo
	 * cliente de impresión.
	 * 
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	public Evento registrarClienteImpresion(HttpServletRequest request)
			throws AccionWebException {

		String uid = (String) request.getParameter(CImpresion.UID);
		String localHost = (String) request.getParameter(CImpresion.LOCALHOST);
		String listeningPort = (String) request
				.getParameter(CImpresion.LISTENINGPORT);
		String tipoEnvio = (String) request.getParameter("TIPO_ENVIO");
		String ipsCliente = (String) request.getParameter("IPS_CLIENTE");
		String usuarioID = (String) request.getParameter("ID_USUARIO");
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request
				.getSession().getAttribute(WebKeys.USUARIO);

		String ipLocal = "127.0.0.1";
		String ip192 = "192";

		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");
		Log.getInstance().debug(AWImpresion.class, "Se esta registrando : " + localHost
				+ " en el puerto " + listeningPort);
		Log.getInstance().debug(AWImpresion.class, "El método para la captura de la ip fue : "
				+ tipoEnvio + " (1. POR RECEIVER 2. POR APPLET)");
		Log.getInstance().debug(AWImpresion.class, "El usuario registrandose es : "
				+ (usuarioSIR != null ? usuarioSIR.getUsername()
						: "USUARIO ES NULO"));
		Log.getInstance().debug(AWImpresion.class, "El id de la sessión es : " + uid);
		Log.getInstance().debug(AWImpresion.class, "Las ips del cliente son : " + ipsCliente);
		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");

		Impresion impresion = new Impresion();
		impresion.setIdSesion(uid);
		impresion.setDireccionIP(localHost);
		impresion.setPuerto(listeningPort);
		impresion.setCirculo(null);

		gov.sir.core.negocio.modelo.Usuario usuarioImpresion = new gov.sir.core.negocio.modelo.Usuario();
		usuarioImpresion.setUsername(usuarioID);
		
		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		if (localHost.equals(ipLocal)) {
			Log.getInstance().debug(AWImpresion.class, "********* El cliente no será registrado, IP inválida ******************");
			return null;
		}

		Map sesiones = (Map) request.getSession().getServletContext()
				.getAttribute(WebKeys.LISTA_SESIONES);
		sesiones.put(uid, listeningPort);

		return new EvnImpresorasCirculo(uAuriga, impresion,
				EvnImpresorasCirculo.REGISTRAR_CLIENTE, usuarioImpresion);
	}

	/**
	 * Método que elimina en la base de datos la información de un nuevo cliente
	 * de impresión, se borra cuando el usuario no acepto el applet de
	 * impresión.
	 * 
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	public Evento eliminarClienteImpresion(HttpServletRequest request)
			throws AccionWebException {

		String uid = (String) request.getParameter(CImpresion.UID);
		String localHost = (String) request.getParameter(CImpresion.LOCALHOST);
		String listeningPort = (String) request
				.getParameter(CImpresion.LISTENINGPORT);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request
				.getSession().getAttribute(WebKeys.USUARIO);

		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");
		Log.getInstance().debug(AWImpresion.class, "Se esta borrando : " + localHost + " en el puerto "
				+ listeningPort);
		Log.getInstance().debug(AWImpresion.class, "La UID a borrar es : " + uid);
		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");

		Impresion impresion = new Impresion();
		impresion.setIdSesion(uid);
		impresion.setDireccionIP(localHost);
		impresion.setPuerto(listeningPort);

		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		if (uid == null) {
			Log.getInstance().debug(AWImpresion.class, "********* El cliente no será borrado, UID inválida ******************");
			return null;
		}

		return new EvnImpresorasCirculo(uAuriga, impresion,
				EvnImpresorasCirculo.ELIMINAR_CLIENTE);
	}

	/**
	 * Método que consulta el imprimible que desea imprimirse.
	 * 
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	public Evento descargarImprimible(HttpServletRequest request)
			throws AccionWebException {

		String uid = request.getParameter(CImpresion.UID);
		String id  = request.getParameter(CImpresion.ID_IMPRIMIBLE);

		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");
		Log.getInstance().debug(AWImpresion.class, "Se quiere imprimir el número : " + id);
		Log.getInstance().debug(AWImpresion.class, "La UID es : " + uid);
		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");

		Imprimible imprimible = new Imprimible();
		imprimible.setIdImprimible(new Integer(id).intValue());

		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		return new EvnImpresorasCirculo(uAuriga, imprimible,
				EvnImpresorasCirculo.DESCARGAR_IMPRIMIBLE);
	}

	/**
	 * Método que consulta las impresiones que no se han realizado para el ID.
	 * 
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	public Evento consultarImpresionesFallidas(HttpServletRequest request)
			throws AccionWebException {

		String uid = (String) request.getParameter(CImpresion.UID);
		String id = (String) request.getParameter(CImpresion.ID_IMPRIMIBLE);
		String ip = (String) request.getParameter(CImpresion.LOCALHOST);
		String tipoCliente = (String) request
				.getParameter(CImpresion.TIPO_CLIENTE);
		boolean tipImp = false;

		if (tipoCliente.equals(CImpresion.CLIENTE_ADMINISTRADOR)) {
			tipImp = true;
		} else {
			tipImp = false;
		}

		Imprimible imprimible = new Imprimible();
		imprimible.setUID(uid);
		imprimible.setIP(ip);

		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		return new EvnImpresorasCirculo(uAuriga, imprimible,
				EvnImpresorasCirculo.CONSULTAR_IMPRESIONES_FALLIDAS, tipImp);
	}

	/**
	 * Método que actualiza el número realizadas en un imprimible.
	 * 
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	public Evento disminuirNumeroImpresiones(HttpServletRequest request)
			throws AccionWebException {

		String uid = (String) request.getParameter(CImpresion.UID);
		String ip = (String) request.getParameter(CImpresion.LOCALHOST);
		String id = (String) request.getParameter(CImpresion.ID_IMPRIMIBLE);
		String error = (String) request.getParameter(CImpresion.MENSAJE_ERROR);

		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");
		Log.getInstance().debug(AWImpresion.class, "No imprimio bien la ip: " + ip);
		Log.getInstance().debug(AWImpresion.class, "No imprimio bien la uid: " + uid);
		Log.getInstance().debug(AWImpresion.class, "No imprimio bien el id: " + id);
		Log.getInstance().debug(AWImpresion.class, "No imprimio bien el error es : " + error);
		Log.getInstance().debug(AWImpresion.class, "*********************************************************************");

		Imprimible imprimible = new Imprimible();
		imprimible.setUID(uid);
		imprimible.setIdImprimible(new Integer(id).intValue());

		Usuario uAuriga = (Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		return new EvnImpresorasCirculo(uAuriga, imprimible,
				EvnImpresorasCirculo.DISMINUIR_NUMERO_IMPRESIONES);
	}

	/**
	 * Metodo que agrega un oficio de pertenencia al turno.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier
	 *         otro caso
	 * @throws AccionWebException
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespImpresorasCirculo respuesta = (EvnRespImpresorasCirculo) evento;
		HttpSession session = request.getSession();
		String id = request
				.getParameter(gov.sir.core.negocio.modelo.constantes.CImpresion.ID_IMPRIMIBLE);

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(
					EvnRespImpresorasCirculo.DESCARGAR_IMPRIMIBLE)) {
				session.setAttribute(WebKeys.IMPRIMIBLE + id, respuesta
						.getPayload());
			}
		}

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(
					EvnRespImpresorasCirculo.CONSULTAR_IMPRESIONES_FALLIDAS)) {
				session.setAttribute(WebKeys.LISTA_IMPRESIONES_FALLIDAS,
						respuesta.getPayload());
			} else if (respuesta.getTipoEvento().equals(
					EvnRespImpresorasCirculo.CONSULTAR_LISTA_REGLAS)) {
				session.setAttribute(AWImpresion.CONSULTAR_LISTA_REGLAS,
						respuesta.getPayload());
			} else if (respuesta.getTipoEvento().equals(
					EvnRespImpresorasCirculo.CONSULTAR_CONFIGURACION)) {
				session.setAttribute(AWImpresion.CARGAR_CONFIGURACION_ACTUAL,
						respuesta.getPayload());
			} else if (respuesta.getTipoEvento().equals(
					EvnRespImpresorasCirculo.CONSULTAR_PARAMETROS)) {
				session.setAttribute(AWImpresion.CARGAR_PARAMETROS_CONFIGURACION, 
						respuesta.getPayload());
			}
		}

	}

}