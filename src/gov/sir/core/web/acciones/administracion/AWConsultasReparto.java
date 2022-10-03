package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnConsultasReparto;
import gov.sir.core.eventos.administracion.EvnRespConsultasReparto;
import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.MinutaEntidadPublica;
import gov.sir.core.negocio.modelo.MinutaAccionNotarial;
import gov.sir.core.negocio.modelo.OtorganteNatural;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.constantes.CAccionNotarial;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CReparto;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ConsultarMinutaRadicacionException;
import gov.sir.core.web.acciones.excepciones.ConsultarMinutaRadicacionModificacionException;
import gov.sir.core.web.acciones.excepciones.ListarMinutasOtorganteException;
import gov.sir.core.web.acciones.excepciones.ModificacionMinutaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosListarRepartoFechas;
import gov.sir.core.web.acciones.repartonotarial.AWRepartoNotarial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
 * @author mmunoz
 */
public class AWConsultasReparto extends SoporteAccionWeb {
	
	/* Nombre de los Parametros de los jsp*/
	
	/** Constante que identifica el campo fecha inicio para la listar minutas por fecha*/
	public static final String FECHA_INICIO = "FECHA_INICIO";
	
	/** Constante que identifica el campo fecha fin para la listar minutas por fecha*/
	public static final String FECHA_FIN = "FECHA_FIN";
	
	public static final String TIPO_DE_BUSQUEDA = "TIPO_DE_BUSQUEDA";
	
	public static final String TIPO_DE_BUSQUEDA_PERSONA_NAT = "TIPO_DE_BUSQUEDA_PERSONA_NAT";
	
	public static final String TIPO_DE_BUSQUEDA_ENTIDAD_PUB = "TIPO_DE_BUSQUEDA_ENTIDAD_PUB";
	
	public static final String CONSULTAR_CIRCULOS_NOTARIALES = "CONSULTAR_CIRCULOS_NOTARIALES";
	
	
	/* Acciones de la clase*/
	/** Constante que identifica que se quiere listar minutas repartidas por fecha*/
	public static final String LISTAR_POR_FECHA = "LISTAR_POR_FECHA";
	
	public static final String TERMINA = "TERMINA";
	
	/** Constante que identifica que se quiere hacer busqueda dada la radicacion*/
	public static final String MINUTA_RADICACION = "MINUTA_RADICACION";
	
	/** Constante que identifica que se quiere listar minutas repartidas por fecha*/
	public static final String LISTAR_POR_OTORGANTE = "LISTAR_POR_OTORGANTE";
	
	/** Constante que identifica que se quiere listar las minutas pendientes*/
	public static final String LISTAR_PENDIENTES = "LISTAR_PENDIENTES";
	
	/** Constante que identifica que se quiere generar el reporte de minutas pendientes*/
	public static final String MOSTRAR_REPORTE_PENDIENTES = "MOSTRAR_REPORTE_PENDIENTES";
		
	/** Constante que identifica que se quiere mostrar una minuta para su modificacion*/
	public static final String CONSULTAR_MINUTA_MODIFICACION = "CONSULTAR_MINUTA_MODIFICACION";
	
	/**CONSTANTE PARA AGREGAR UNA ENTIDAD PÚBLICA	 */
	public final static String AGREGAR_ENTIDAD_PUBLICA = "AGREGAR_ENTIDAD_PUBLICA";

	/**CONSTANTE PARA ELIMINAR UNA ENTIDAD PÚBLICA	 */
	public final static String ELIMINAR_ENTIDAD_PUBLICA = "ELIMINAR_ENTIDAD_PUBLICA";
	
	/**CONSTANTE PARA ELIMINAR UNA ACCION NOTARIAL */
	public final static String ELIMINAR_ACCION_NOTARIAL = "ELIMINAR_ACCION_NOTARIAL";
	
	/**CONSTANTE PARA AGREGAR UNA ACCION NOTARIAL */
	public final static String AGREGAR_ACCION_NOTARIAL = "AGREGAR_ACCION_NOTARIAL";

	/**CONSTANTE PARA AGREGAR UN OTORGANTE NATURAL	 */
	public final static String AGREGAR_OTORGANTE_NATURAL = "AGREGAR_OTORGANTE_NATURAL";

	/**CONSTANTE PARA ELIMINAR UN OTORGANTE NATURAL	 */
	public final static String ELIMINAR_OTORGANTE_NATURAL = "ELIMINAR_OTORGANTE_NATURAL";
	
	public static final String ENVIAR_EDICION = "ENVIAR_EDICION";
	
	public static final String ANULAR_MINUTA = "ANULAR_MINUTA"; 
	
	public static final String SELECCIONAR_TIPO_BUSQUEDA = "SELECCIONAR_TIPO_BUSQUEDA";
	public static final String TERMINA_EDICION="TERMINA_EDICION";

	public static final String PRESERVAR_INFO = "PRESERVAR_INFO";
	 
	HttpSession session;
	
	String accion;

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION).trim();
		session = request.getSession();
		preservarInfo(request);
		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if(accion.equals(AWConsultasReparto.LISTAR_POR_FECHA)){
			return listarPorFecha(request);
		} else if(accion.equals(AWConsultasReparto.MINUTA_RADICACION)){
			return minutaRadicacion(request);			
		} else if(accion.equals(AWConsultasReparto.LISTAR_POR_OTORGANTE)){
			return listarPorOtorgante(request);
		} else if(accion.equals(AWConsultasReparto.LISTAR_PENDIENTES)){
			return listarPendientes(request);						
		}else if(accion.equals(AWConsultasReparto.MOSTRAR_REPORTE_PENDIENTES)){
			return mostrarReportePendientes(request);						
		} else if(accion.equals(AWConsultasReparto.CONSULTAR_MINUTA_MODIFICACION)){
			return minutaRadicacionModificacion(request);	
		} else if(accion.equals(AWConsultasReparto.AGREGAR_ACCION_NOTARIAL)){
			return agregarAccionNotarial(request);
		} else if(accion.equals(AWConsultasReparto.ELIMINAR_ACCION_NOTARIAL)){
			return eliminarAccionNotarial(request);
		} else if (accion.equals(AWConsultasReparto.AGREGAR_ENTIDAD_PUBLICA)) {
			return agregarEntidadPublica(request);
		} else if (accion.equals(AWConsultasReparto.ELIMINAR_ENTIDAD_PUBLICA)) {
			return eliminarEntidadPublica(request);
		} else if (accion.equals(AWConsultasReparto.AGREGAR_OTORGANTE_NATURAL)) {
			return agregarOtorganteNatural(request);
		} else if (accion.equals(AWConsultasReparto.ELIMINAR_OTORGANTE_NATURAL)) {
			return eliminarOtorganteNatural(request);
		} else if (accion.equals(AWConsultasReparto.ENVIAR_EDICION)) {
			return edicionMinuta(request);
		} else if (accion.equals(AWConsultasReparto.ANULAR_MINUTA)) {
			return anularMinuta(request);	
		} else if (accion.equals(AWConsultasReparto.SELECCIONAR_TIPO_BUSQUEDA)) {
			return seleccionarTipoBusqueda(request);
		} else if(accion.equals(AWConsultasReparto.TERMINA)){
			removerInfo();
			return null;
		} else if (accion.equals(AWConsultasReparto.TERMINA_EDICION)){
			return terminaEdicion(request);
		} else if (accion.equals(AWConsultasReparto.PRESERVAR_INFO)){
			this.preservarInfo(request);
			return null;
		}
		
		return null;
	}
	
	/**
	 * Método que preserva la información del formulario de creación de un turno de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private void preservarInfoMinuta(HttpServletRequest request) {

		//Los datos se suben a la sesión para preservar la información del formulario.
		HttpSession sesion = request.getSession();
		
		sesion.setAttribute(CMinuta.TIPO_REPARTO, request.getParameter(CMinuta.TIPO_REPARTO));
		sesion.setAttribute(CMinuta.TIPO_REPARTO, request.getParameter(CMinuta.TIPO_REPARTO));
		sesion.setAttribute(CMinuta.ID_TIPO_ACCION_NOTARIAL, request.getParameter(CMinuta.ID_TIPO_ACCION_NOTARIAL));
		sesion.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL, request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL));
		sesion.setAttribute(CMinuta.CIRCULO_NOTARIAL, request.getParameter(CMinuta.CIRCULO_NOTARIAL));		
		sesion.setAttribute(CMinuta.NRO_FOLIOS_MINUTA, request.getParameter(CMinuta.NRO_FOLIOS_MINUTA));
		sesion.setAttribute(CMinuta.CUANTIA, request.getParameter(CMinuta.CUANTIA));
		sesion.setAttribute(CMinuta.UNIDADES, request.getParameter(CMinuta.UNIDADES));
		sesion.setAttribute(CMinuta.OBSERVACIONES_REPARTO, request.getParameter(CMinuta.OBSERVACIONES_REPARTO));
		//sesion.setAttribute(CMinuta.LISTA_ACTOS_REPARTO, )
		
		if (request.getParameter(CMinuta.ACCION_NOTARIAL_CON_CUANTIA)==null){
			sesion.setAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA, "ACCION_NOTARIAL_SIN_CUANTIA");
		} else{
			sesion.setAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA, request.getParameter(CMinuta.ACCION_NOTARIAL_CON_CUANTIA));
		}
		
		if (request.getParameter(CMinuta.IMPRIMIR_CONSTANCIA)==null){
			sesion.setAttribute(CMinuta.IMPRIMIR_CONSTANCIA, "IMPRIMIR_SIN_CONSTANCIA");
		} else{
			sesion.setAttribute(CMinuta.IMPRIMIR_CONSTANCIA, request.getParameter(CMinuta.IMPRIMIR_CONSTANCIA));
		}
		
	}
	
	/**
	 * 
	 */
	private Evento terminaEdicion(HttpServletRequest request) throws AccionWebException {
		Minuta minuta = (Minuta)request.getSession().getAttribute(WebKeys.MINUTA);
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento=new EvnConsultasReparto(usuario,EvnConsultasReparto.IMPRIMIR_SOLICITUD_MINUTA);
		evento.setMinuta(minuta);
		evento.setUID(request.getSession().getId());
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento seleccionarTipoBusqueda(HttpServletRequest request) {
		String tipo = request.getParameter(AWConsultasReparto.TIPO_DE_BUSQUEDA);
		session.setAttribute(AWConsultasReparto.TIPO_DE_BUSQUEDA,tipo);
		session.removeAttribute(WebKeys.TABLA_MINUTAS);
		session.removeAttribute(CCiudadano.NOMBRE);
		session.removeAttribute(CMinuta.ENTIDAD_PUBLICA);	
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento anularMinuta(HttpServletRequest request) throws AccionWebException{
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.ANULAR_MINUTA);
		ModificacionMinutaException exception = new ModificacionMinutaException();
		String notaInf = request.getParameter(CNota.DESCRIPCION);
		if (notaInf==null || notaInf.trim().equals("")){
			exception.addError("Debe agregar una nota informativa");
			throw exception;
		}
		
		Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
		evento.setMinuta(minuta);
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		evento.setNotaInformativa(notaInf.toUpperCase());
		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento edicionMinuta(HttpServletRequest request) throws AccionWebException {
		Minuta oldMinuta = (Minuta)session.getAttribute(WebKeys.MINUTA);

		//Se recibe la información que viene del formulario.
		String tipoReparto = request.getParameter(CMinuta.TIPO_REPARTO);
		String circuloNotarial = request.getParameter(CMinuta.CIRCULO_NOTARIAL);
		String nroFolios = request.getParameter(CMinuta.NRO_FOLIOS_MINUTA);
		String observaciones = request.getParameter(CMinuta.OBSERVACIONES_REPARTO);
		boolean tieneAccionesPropiedadHorizontalOParcelaciones = false;

		String idCirculoNotarial = request.getParameter(CMinuta.CIRCULO_NOTARIAL_ID);
		String notaInf = request.getParameter(CNota.DESCRIPCION);
		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (tipoReparto == null || (tipoReparto.length() <= 0)) {
			exception.addError("Debe seleccionar un tipo de reparto");
		}
		
		
		List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		if ((listaNotas == null || listaNotas.size()==0)&& tipoReparto.equals(CMinuta.EXTRAORDINARIO))
		{
			exception.addError("El reparto es extraordinario.  Debe ingresar una nota informativa.");
		}

		if ((nroFolios != null && nroFolios.length() > 0)) {
			try {
				Double.parseDouble(nroFolios);
			} catch (Exception e) {
				exception.addError("Debe ingresar valores correctos para el número de folios");
			}

		}
		
		this.preservarInfo(request);
		//Se lanzan los errores si existieron en la validación.
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		//Datos de la sesión.		
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
		String UID = request.getSession().getId();
		Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

		//Se llena la minuta		
		Minuta minuta = new Minuta();

		if (tipoReparto.equals(CMinuta.ORDINARIO)) {
			minuta.setNormal(true);
		} else {
			minuta.setNormal(false);
		}
		
		CirculoNotarial cn = new CirculoNotarial();
		cn.setIdCirculo(circuloNotarial);
		
		minuta.setCirculoNotarial(cn);
		
		//minuta.setAccionNotarial(accionNotarial);
		if ((nroFolios != null && nroFolios.length() > 0)) {
			minuta.setNumeroFolios(new Long(nroFolios).longValue());
		}
		 
		if(observaciones!=null){
			minuta.setComentario(observaciones.toUpperCase());	
		}
		
		double cuantiaTotal = 0;
		long unidadesTotal = 0;
		
		List accionesList = oldMinuta.getAccionesNotariales();
		if (accionesList!= null && accionesList.size() > 0)
		{
			for (Iterator i = accionesList.iterator(); i.hasNext(); )
			{
				MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) i.next();
				minuta.addAccionesNotariale(actoMinutaTmp);
				cuantiaTotal = cuantiaTotal + actoMinutaTmp.getValor();
				unidadesTotal = unidadesTotal + actoMinutaTmp.getUnidades();
			}
		}
		
		//Se coloca el valor la sumatoria de las unidades y cuantias.
		minuta.setUnidades(unidadesTotal);
		minuta.setValor(cuantiaTotal);
		
		List entidadesPublicas = oldMinuta.getEntidadesPublicas();
		if (entidadesPublicas != null && entidadesPublicas.size() > 0) {
			Iterator it = entidadesPublicas.iterator();
			while (it.hasNext()) {
				MinutaEntidadPublica entidadPublica = (MinutaEntidadPublica) it.next();
				minuta.addEntidadesPublica(entidadPublica);
			}
		}

		List otorgantes = oldMinuta.getOtorgantesNaturales();
		if (otorgantes != null && otorgantes.size() > 0) {
			Iterator it = otorgantes.iterator();
			while (it.hasNext()) {
				OtorganteNatural oto = (OtorganteNatural) it.next();
				minuta.addOtorgantesNaturale(oto);
			}
		}
		
		List circulos = (List) session.getAttribute(WebKeys.LISTA_CIRCULOS_NOTARIALES);
		if(circulos == null){
			circulos = new ArrayList();
		}
		Iterator itCirculos = circulos.iterator();
		while(itCirculos.hasNext()){
			CirculoNotarial circ = (CirculoNotarial)itCirculos.next();
			if(circ.getIdCirculo().equals(idCirculoNotarial)){
				minuta.setCirculoNotarial(circ);
			}
		}
		
		minuta.setIdMinuta(oldMinuta.getIdMinuta());
		minuta.setNumModificaciones(oldMinuta.getNumModificaciones());
		
		EvnConsultasReparto evento = new EvnConsultasReparto(usuarioAuriga,EvnConsultasReparto.ENVIAR_MINUTA_EDICION);
		evento.setMinuta(minuta);
		evento.setOldMinuta(minuta);
		evento.setCirculo(circulo);
		if (notaInf!=null && notaInf.trim().equals("")){
			evento.setNotaInformativa(notaInf);	
		}
		
                
                /**
                * @autor HGOMEZ 
                * @mantis 13176 
                * @Requerimiento 061_453_Requerimiento_Auditoria 
                * @descripcion Mantiene la información del usuario.
                */
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
                
                
                
		return evento;
	}
	
	/**
	 * Metodo que determina si las acciones notariales agregadas en la liquidacion son de tipo PROPIEDAD HORIZONTAL o PARCELACIONES
	 * @param accionesNotariales Lista con las acciones notariales agregadas
	 * @return boolean
	 */
	private boolean tieneAccionesNotarialesPropiedadHorizontalOParcelaciones(List accionesNotariales)
	{
		boolean esPropiedadHorizontalOParcelaciones = false;
		Iterator it = accionesNotariales.iterator();
		while (it.hasNext())
		{
			MinutaAccionNotarial accionNotarial = (MinutaAccionNotarial)it.next();
			if(accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL) 
				|| accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.URBANIZACIONES_PARCELACIONES))
			{
				esPropiedadHorizontalOParcelaciones = true;
				break;
			}
		}
		
		return esPropiedadHorizontalOParcelaciones;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento listarPendientes(HttpServletRequest request) {
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.LISTAR_PENDIENTES);
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		return evento;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento mostrarReportePendientes(HttpServletRequest request) throws AccionWebException {
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.LISTAR_PENDIENTES);
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		
		String param_00 = AWReportes.REPORTE_110__PARAM_CMDKEY;
		String param_01 = request.getParameter(AWReportes.REPORTE_110__PARAM_PFECHAINI);
		String param_02 = request.getParameter(AWReportes.REPORTE_110__PARAM_PFECHAFIN);
		String param_03 = circulo.getIdCirculo();
		
		ValidacionParametrosException exception = new ValidacionParametrosException();

		String send1 = null;

		if(param_01==null || param_01.equals("")){
			exception.addError("Debe ingresar una fecha de inicio válida.");
		}else{
			Date fecha = null;
			try {
				fecha = DateFormatUtil.parse(param_01);
				send1 = DateFormatUtil.format("yyyy-mm-dd", fecha);
				param_01 = send1;
			}
			catch (Exception e) {
				exception.addError("parametro: Fecha pago : La fecha es inválida" + e.getMessage() );
			}
		}
		
		if(param_02==null || param_02.equals("")){
			exception.addError("Debe ingresar una fecha de fin válida.");
		}else{
			Date fecha = null;
			try {
				fecha = DateFormatUtil.parse(param_02);
				send1 = DateFormatUtil.format("yyyy-mm-dd", fecha);
				param_02 = send1;
			}
			catch (Exception e) {
				exception.addError("parametro: Fecha pago : La fecha es inválida" + e.getMessage() );
			}
		}		
		
		if(exception.getErrores().size()>0){
			throw exception;
		}
		

		String[] params = new String[] {
			AWReportes.REPORTE_XX__PARAM_CMDKEY,
			AWReportes.REPORTE_110__PARAM_PFECHAINI,
			AWReportes.REPORTE_110__PARAM_PFECHAFIN,
			AWReportes.REPORTE_110__PARAM_PCIRCULONOMBRE
		};

		String[] values = new String[] {
				param_00,
				param_01,
				param_02,
				param_03
		};

		String url = makeUrl(params, values, request);
		
		request.setAttribute(AWReportes.REPORTSSERVICES_REPORTURI, url);
		request.setAttribute(AWReportes.REPORTSSERVICES_REPORTDISPLAYENABLED, Boolean.TRUE );		
		
		return evento;
	}	
	
	/**
	 * @param params
	 * @param values
	 * @param request
	 * @return
	 */
	private String makeUrl(String[] params, String[] values,HttpServletRequest request) {
		String reportesServletPath = (String) request.getSession().getServletContext().getAttribute(WebKeys.REPORTES_SERVLET_URL);
		StringBuffer url = new StringBuffer();
		url.append(reportesServletPath);
		url.append('?');
		
		for (int i = 0; i < params.length; i++) {
			url.append(params[i]);
			url.append('=');
			url.append(values[i]);
			url.append('&');
		}

		return url.toString();
	}	
	

	/**
	 * @param request
	 * @return
	 */
	private Evento listarPorOtorgante(HttpServletRequest request) throws ListarMinutasOtorganteException {
		ListarMinutasOtorganteException exception = new ListarMinutasOtorganteException();
		String tipoBusqueda = request.getParameter(AWConsultasReparto.TIPO_DE_BUSQUEDA);
		String info = "";
		boolean esPersonaNat = true;
		if(tipoBusqueda.equals(AWConsultasReparto.TIPO_DE_BUSQUEDA_PERSONA_NAT)){
			info = request.getParameter(CCiudadano.NOMBRE);
			if(info == null || info.trim().length()<=0){
				exception.addError("El nombre del otorgante no debe ser vacio");
				throw exception;
			}			
			esPersonaNat = true;
		} else if(tipoBusqueda.equals(AWConsultasReparto.TIPO_DE_BUSQUEDA_ENTIDAD_PUB)){
			info = request.getParameter(CCiudadano.NOMBRE);
			if(info == null || info.trim().length()<=0){
				exception.addError("El nombre de la entidad pública otorgante no debe ser vacío");
				throw exception;
			}			
			esPersonaNat = false;	
			
		}
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.LISTAR_POR_OTORGANTE);
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		evento.setOtorgante(info.toUpperCase(),esPersonaNat);
		//Aca se coloca el estado de las minutas que se quieren obtener
		evento.setEstadoReparto(CReparto.MINUTA_NO_REPARTIDA);
		return evento;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento minutaRadicacionModificacion(HttpServletRequest request) throws ConsultarMinutaRadicacionException, ConsultarMinutaRadicacionModificacionException {
		ConsultarMinutaRadicacionModificacionException exception = new ConsultarMinutaRadicacionModificacionException();
		String idTurno = request.getParameter(CTurno.ID_TURNO);
		if(idTurno == null || idTurno.trim().length()<=0){
			exception.addError("El número de la radicacion no debe ser vacio");
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.MINUTA_RADICACION);
		evento.setIdTurno(idTurno.toUpperCase());
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento minutaRadicacion(HttpServletRequest request) throws ConsultarMinutaRadicacionException {
		ConsultarMinutaRadicacionException exception = new ConsultarMinutaRadicacionException();
		String idTurno = request.getParameter(CTurno.ID_TURNO);
		if(idTurno == null || idTurno.trim().length()<=0){
			exception.addError("El número de la radicacion no debe ser vacio");
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.MINUTA_RADICACION);
		evento.setIdTurno(idTurno.toUpperCase());
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		
		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento listarPorFecha(HttpServletRequest request) throws ValidacionParametrosListarRepartoFechas {
		ValidacionParametrosListarRepartoFechas exception = new ValidacionParametrosListarRepartoFechas();
		String fechaInicioS = request.getParameter(AWConsultasReparto.FECHA_INICIO);
		String fechaFinS = request.getParameter(AWConsultasReparto.FECHA_FIN);
		
		String tmp = null;
		
		Date fechaInicio = null;
		try {
			fechaInicio = DateFormatUtil.parse(fechaInicioS);
			tmp = DateFormatUtil.format(fechaInicio);	
		} catch (Exception e) {
			exception.addError("La fecha es inválida" + e.getMessage());
		}
		
		Date fechaFin = null;
		try {
			fechaFin = DateFormatUtil.parse(fechaFinS);
			fechaFin.setTime(fechaFin.getTime() + (long)1000 * 60 * 60 * 24);
			tmp = DateFormatUtil.format(fechaFin); 
		} catch (Exception e) {
			exception.addError("La fecha es inválida" + e.getMessage());
		}

		if(fechaFin.before(fechaInicio)){
			exception.addError("La fecha de inicio no puede ser menor que la fecha fin");
		}
		
		if(exception.getErrores().size()>0){
			throw exception;
		}					
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnConsultasReparto evento = new EvnConsultasReparto(usuario,EvnConsultasReparto.LISTAR_POR_FECHAS);
		evento.setFechas(fechaInicio,fechaFin);
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);
		evento.setCirculo(circulo);
		return evento;
	}
	
	/**
	 * @param request
	 */
	private void preservarInfo(HttpServletRequest request) {
		if(accion.equals(AWConsultasReparto.LISTAR_POR_FECHA)){
			session.setAttribute(AWConsultasReparto.FECHA_INICIO,request.getParameter(AWConsultasReparto.FECHA_INICIO));
			session.setAttribute(AWConsultasReparto.FECHA_FIN,request.getParameter(AWConsultasReparto.FECHA_FIN));
		} else if(accion.equals(AWConsultasReparto.MINUTA_RADICACION) || accion.equals(AWConsultasReparto.CONSULTAR_MINUTA_MODIFICACION)){
			session.setAttribute(CTurno.ID_TURNO,request.getParameter(CTurno.ID_TURNO));	
		} else if(accion.equals(AWConsultasReparto.LISTAR_POR_OTORGANTE)){
			session.setAttribute(CCiudadano.NOMBRE,request.getParameter(CCiudadano.NOMBRE));
			session.setAttribute(CMinuta.ENTIDAD_PUBLICA,request.getParameter(CMinuta.ENTIDAD_PUBLICA));	
		} else if(accion.equals(AWConsultasReparto.AGREGAR_ENTIDAD_PUBLICA) || 
				accion.equals(AWConsultasReparto.AGREGAR_OTORGANTE_NATURAL) || 
				accion.equals(AWConsultasReparto.ELIMINAR_ENTIDAD_PUBLICA) ||
				accion.equals(AWConsultasReparto.ELIMINAR_ACCION_NOTARIAL) ||
				accion.equals(AWConsultasReparto.AGREGAR_ACCION_NOTARIAL) ||
				accion.equals(AWConsultasReparto.ENVIAR_EDICION) || 
				accion.equals(AWConsultasReparto.ELIMINAR_OTORGANTE_NATURAL)){
		
			session.setAttribute(CMinuta.TIPO_REPARTO, request.getParameter(CMinuta.TIPO_REPARTO));
			session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL, request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL));
			session.setAttribute(CMinuta.NRO_FOLIOS_MINUTA, request.getParameter(CMinuta.NRO_FOLIOS_MINUTA));
			session.setAttribute(CMinuta.CUANTIA, request.getParameter(CMinuta.CUANTIA));
			session.setAttribute(CMinuta.UNIDADES, request.getParameter(CMinuta.UNIDADES));
			session.setAttribute(CMinuta.OBSERVACIONES_REPARTO, request.getParameter(CMinuta.OBSERVACIONES_REPARTO));
			session.setAttribute(CMinuta.CIRCULO_NOTARIAL_ID, request.getParameter(CMinuta.CIRCULO_NOTARIAL_ID));
			session.setAttribute(CMinuta.CUANTIA_ACTO,request.getParameter(CMinuta.CUANTIA_ACTO));
			session.setAttribute(CMinuta.UNIDADES_ACTO,request.getParameter(CMinuta.UNIDADES_ACTO));
			session.setAttribute(CAccionNotarial.SIN_CUANTIA,request.getParameter(CAccionNotarial.SIN_CUANTIA));
			session.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL,request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL));
			session.setAttribute(CMinuta.ENTIDAD_PUBLICA,request.getParameter(CMinuta.ENTIDAD_PUBLICA));
			session.setAttribute(CMinuta.ENTIDAD_PUBLICA_EXENTO,request.getParameter(CMinuta.ENTIDAD_PUBLICA_EXENTO));
			session.setAttribute(CMinuta.OTORGANTE_NATURAL,request.getParameter(CMinuta.OTORGANTE_NATURAL));
			session.setAttribute(CMinuta.OTORGANTE_NATURAL_EXENTO,request.getParameter(CMinuta.OTORGANTE_NATURAL_EXENTO));
 
		}
		
	}
	
	/**
	 * @param request
	 */
	private void removerInfo() {
		session.removeAttribute(WebKeys.TABLA_MINUTAS);
		session.removeAttribute(AWConsultasReparto.FECHA_INICIO);
		session.removeAttribute(AWConsultasReparto.FECHA_FIN);
		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(CCiudadano.NOMBRE);	
		
		session.removeAttribute(CMinuta.TIPO_REPARTO);
		session.removeAttribute(CMinuta.TIPO_ACCION_NOTARIAL);
		session.removeAttribute(CMinuta.NRO_FOLIOS_MINUTA);
		//session.removeAttribute(CMinuta.CUANTIA);
		session.removeAttribute(CMinuta.CUANTIA_ACTO);
		session.removeAttribute(CMinuta.UNIDADES);
		session.removeAttribute(CMinuta.OBSERVACIONES_REPARTO);
		session.removeAttribute(CMinuta.CIRCULO_NOTARIAL_ID);
		
		if(accion.equals(TERMINA) || accion.equals(ANULAR_MINUTA) || accion.equals(TERMINA_EDICION)){
			session.removeAttribute(WebKeys.MINUTA);
			session.removeAttribute(WebKeys.TABLA_MINUTAS);
			session.removeAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
			session.removeAttribute(WebKeys.LISTA_OTORGANTES);		
			session.removeAttribute(WebKeys.TABLA_MINUTAS);
			session.removeAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
			session.removeAttribute(WebKeys.LISTA_ACTOS_MINUTA);
			session.removeAttribute(CCiudadano.NOMBRE);
			session.removeAttribute(CMinuta.ENTIDAD_PUBLICA);	
			session.removeAttribute(AWConsultasReparto.TIPO_DE_BUSQUEDA);
			session.removeAttribute(CMinuta.ACCION_NOTARIAL);
			session.removeAttribute(CMinuta.TIPO_REPARTO);
			session.removeAttribute(CMinuta.TIPO_ACCION_NOTARIAL);
			session.removeAttribute(CMinuta.NRO_FOLIOS_MINUTA);
			session.removeAttribute(CMinuta.CUANTIA);
			session.removeAttribute(CMinuta.UNIDADES);
			session.removeAttribute(CMinuta.OBSERVACIONES_REPARTO);
			session.removeAttribute(CMinuta.CIRCULO_NOTARIAL_ID);
			session.removeAttribute(CMinuta.CUANTIA_ACTO);
			session.removeAttribute(CMinuta.UNIDADES_ACTO);
			session.removeAttribute(CAccionNotarial.SIN_CUANTIA);
			session.removeAttribute(CMinuta.TIPO_ACCION_NOTARIAL);
			session.removeAttribute(CMinuta.ENTIDAD_PUBLICA);
			session.removeAttribute(CMinuta.ENTIDAD_PUBLICA_EXENTO);
			session.removeAttribute(CMinuta.OTORGANTE_NATURAL);
			session.removeAttribute(CMinuta.OTORGANTE_NATURAL_EXENTO);
		}
		
	}
	
	/**
	 * Método que agrega una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento agregarEntidadPublica(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		//Se recibe la información que viene del formulario.
		String entPublica = request.getParameter(CMinuta.ENTIDAD_PUBLICA);
		String exento = request.getParameter(CMinuta.ENTIDAD_PUBLICA_EXENTO);
		Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
		boolean bExento=false;

		EntidadPublica entidadPublica = new EntidadPublica();

		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (entPublica == null || entPublica.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar una entidad pública.");
			throw exception;
		}
		
		if (exento != null && exento.equals(CMinuta.EXENTO)) {
			bExento=true;
		}

		List entidades = (List) session.getServletContext().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
		Iterator it = entidades.iterator();
		while (it.hasNext()) {
			EntidadPublica ep = (EntidadPublica) it.next();
			if (entPublica.equals(ep.getIdEntidadPublica())) {
				entidadPublica = ep;
			}
		}

		List entidadespublicas = minuta.getEntidadesPublicas();

		if (!this.estaAsociadaEntidadPublica(entidadPublica, entidadespublicas)) {
			entidadPublica.setExento(bExento);
			MinutaEntidadPublica minutaEntidad = new MinutaEntidadPublica();
			minutaEntidad.setEntidadPublica(entidadPublica);
			minutaEntidad.setExento(bExento);
			minuta.addEntidadesPublica(minutaEntidad);
		} else {
			exception.addError("La entidad pública " + entidadPublica.getNombre() + ", ya ha sido agregada.");
			throw exception;
		}

		session.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, entidadespublicas);

		return null;
	}
	
	/**
	 * Metodo que agrega una nueva accion notarial a la solicitud de reparto notarial
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento agregarAccionNotarial(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		//Se recibe la información que viene del formulario.
		String cuantia = request.getParameter(CMinuta.CUANTIA_ACTO);
		String unidades = request.getParameter(CMinuta.UNIDADES_ACTO);
		String conCuantia = CAccionNotarial.SIN_CUANTIA;
		Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
		
		
		if (request.getParameter(CMinuta.ACCION_NOTARIAL_CON_CUANTIA)!=null){
			conCuantia = CAccionNotarial.CON_CUANTIA;
		}

		String tipoAccionNotarial = request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL);
		
		AccionNotarial accionNotarial = new AccionNotarial();

		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (tipoAccionNotarial == null || tipoAccionNotarial.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar una acción notarial.");
			throw exception;
		} else
		{
			List actos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
			Iterator it = actos.iterator();
			while (it.hasNext()) {
				AccionNotarial am = (AccionNotarial) it.next();
				if (tipoAccionNotarial.equals(am.getIdAccionNotarial())) {
					accionNotarial = am;
				}
			}
			
			
			if (tipoAccionNotarial.equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL) 
					|| tipoAccionNotarial.equals(CAccionNotarial.URBANIZACIONES_PARCELACIONES)) {
					if (unidades!=null) {
						try{
							long unidadesValor = new Long(unidades).longValue();
							if (unidadesValor <= 0) {
								exception.addError("Las Unidades Inmobiliarias deben ser mayores a 0.");
							}
						} catch (NumberFormatException ee) {
							exception.addError("Las Unidades Inmobiliarias deben ser validas.");
						}
					} else {
						exception.addError("El tipo de acción notarial debe tener Unidades Inmobiliarias.");
					}
				}
				
			  //Si seleciona el flag de cuantia este valor debe ser mayor a 0.
				if (conCuantia.equals(CAccionNotarial.CON_CUANTIA)) {
					if (cuantia!=null) {
						try{
							double cuantiaValor = new Double(cuantia).doubleValue();
							if (cuantiaValor <= 0) {
								exception.addError("La Cuantía deben ser mayores a 0.");
							}
						} catch (NumberFormatException ee) {
							exception.addError("La Cuantía deben ser validas.");
						}
					} 
				}
				
				if (unidades!=null && !unidades.equals("")) {
					try{
						new Double(unidades).doubleValue();
					} catch (NumberFormatException ee) {
						exception.addError("Las Unidades Inmobiliarias deben ser validas.");
					} 
				}

				if (exception.getErrores().size() > 0) {
					throw exception;
				}
				
				MinutaAccionNotarial actoMinuta = new MinutaAccionNotarial();
				actoMinuta.setAccionNotarial(accionNotarial);
				actoMinuta.setValor(cuantia!=null && !cuantia.equals("") ? Double.parseDouble(cuantia) : 0);
				actoMinuta.setUnidades(unidades!=null && !unidades.equals("") ? Long.parseLong(unidades) : 0);
				
				actoMinuta.setConCuantia(Long.parseLong(conCuantia));
				
				List actosMinuta = minuta.getAccionesNotariales();
				if (!this.estaAsociadaActo(actoMinuta, actosMinuta)) {
					minuta.addAccionesNotariale(actoMinuta);
				} else {
					exception.addError("La accion notarial " + accionNotarial.getNombre() + " ya ha sido agregada.");
					throw exception;
				}
		
				request.getSession().setAttribute(WebKeys.LISTA_ACTOS_MINUTA, actosMinuta);
		}
		
		return null;
	}
	
	
	/**
	 * Verifica si un acto ya esta asociado a la solicitud de la minuta
	 * @param accionNotarial
	 * @param actosMinuta
	 * @return booleano que informa si existe la entidad
	 * @throws AccionWebException
	 */
	private boolean estaAsociadaActo(MinutaAccionNotarial actoMinuta, List actosMinuta) throws AccionWebException
	{
		boolean asociada = false;
		AccionNotarial accionNotarial = actoMinuta.getAccionNotarial();
		
		Iterator it = actosMinuta.iterator();
		while(it.hasNext())
		{
			MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) it.next();
			AccionNotarial an = actoMinutaTmp.getAccionNotarial();
			if(accionNotarial.getIdAccionNotarial().equals(an.getIdAccionNotarial())){
				asociada = true;
				break;
			}
		}
		
		return asociada;
	}
	

	/**
	 * Método que agrega una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private boolean estaAsociadaEntidadPublica(EntidadPublica entidadPublica, List entidades) throws AccionWebException {

		boolean asociada = false;

		Iterator it = entidades.iterator();
		while (it.hasNext()) {
			MinutaEntidadPublica ep = (MinutaEntidadPublica) it.next();
			if (entidadPublica.getIdEntidadPublica().equals(ep.getEntidadPublica().getIdEntidadPublica())) {
				asociada = true;
				break;
			}
		}

		return asociada;
	}
	

	/**
	 * Método que elimina una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento eliminarEntidadPublica(HttpServletRequest request) throws AccionWebException {

		//Se recibe la información que viene del formulario.
		String posicion = request.getParameter(WebKeys.POSICION);
		Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
		int iPosicion = 0;

		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (posicion == null || posicion.length() == 0) {
			exception.addError("Debe seleccionar la entidad pública a eliminar.");
			throw exception;
		} else {
			try {
				iPosicion = new BigDecimal(posicion).intValue();
			} catch (Throwable t) {
				exception.addError("Debe seleccionar la entidad pública a eliminar.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		List entidadePublicas = minuta.getEntidadesPublicas();
		if (entidadePublicas!=null)
		{
			int cont = 0;
			for (Iterator i = entidadePublicas.iterator(); i.hasNext(); cont++)
			{
				MinutaEntidadPublica minutaEntidad = (MinutaEntidadPublica)i.next();
				if (cont == iPosicion)
				{
					minuta.removeEntidadesPublica(minutaEntidad);
					break;
				}
			}
		}

		session.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, minuta.getEntidadesPublicas());

		return null;
	}
	
	/**
	 * Método que elimina una acción Notarial de la solicitud del reparto notarial
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento eliminarAccionNotarial(HttpServletRequest request) throws AccionWebException {

		//Se recibe la información que viene del formulario.
		String posicion = request.getParameter(WebKeys.POSICION);
		Minuta minuta = (Minuta)request.getSession().getAttribute(WebKeys.MINUTA);
		int iPosicion = 0;

		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (posicion == null || posicion.length() == 0) {
			exception.addError("Debe seleccionar la acción notarial a eliminar.");
			throw exception;
		} else {
			try {
				iPosicion = new BigDecimal(posicion).intValue();
			} catch (Throwable t) {
				exception.addError("Debe seleccionar la acción notarial a eliminar.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		List accionesNotariales = minuta.getAccionesNotariales();
		if (accionesNotariales!=null)
		{
			int cont = 0;
			for (Iterator i = accionesNotariales.iterator(); i.hasNext(); cont++)
			{
				MinutaAccionNotarial minutaAccion = (MinutaAccionNotarial)i.next();
				if (cont == iPosicion)
				{
					minuta.removeAccionesNotariale(minutaAccion);
					break;
				}
			}
		}
		
		session.setAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES, minuta.getAccionesNotariales());

		return null;
	}

	/**
	 * Método que agrega un nuevo otorgante natural a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento agregarOtorganteNatural(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		preservarInfo(request);
		//Se recibe la información que viene del formulario.
		String otorgante = request.getParameter(CMinuta.OTORGANTE_NATURAL);
		String exento = request.getParameter(CMinuta.OTORGANTE_NATURAL_EXENTO);
		Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
		boolean bExento=false;

		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (otorgante == null || otorgante.length() == 0) {
			exception.addError("Debe ingresar un otorgante natural.");
			throw exception;
		}
		
		if (exento != null && exento.equals(CMinuta.EXENTO)) {
			bExento=true;
		}

		OtorganteNatural otorganteNatural = new OtorganteNatural();
		otorganteNatural.setNombre(otorgante.toUpperCase());
		otorganteNatural.setExento(bExento);

		minuta.addOtorgantesNaturale(otorganteNatural);
		session.setAttribute(WebKeys.LISTA_OTORGANTES, minuta.getOtorgantesNaturales());

		return null;
	}

	/**
	 * Método que elimina un otorgante natural a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento eliminarOtorganteNatural(HttpServletRequest request) throws AccionWebException {

		//Se recibe la información que viene del formulario.		
		String posicion = request.getParameter(WebKeys.POSICION);
		Minuta minuta = (Minuta)session.getAttribute(WebKeys.MINUTA);
		
		int iPosicion = 0;

		//Se detectan las excepciones de validación
		ModificacionMinutaException exception = new ModificacionMinutaException();

		if (posicion == null || posicion.length() == 0) {
			exception.addError("Debe seleccionar el otorgante a eliminar.");
			throw exception;
		} else {
			try {
				iPosicion = new BigDecimal(posicion).intValue();
			} catch (Throwable t) {
				exception.addError("Debe seleccionar el otorgante a eliminar.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		List otorgantes = minuta.getOtorgantesNaturales();
		if (otorgantes!=null)
		{
			int cont = 0;
			for (Iterator i = otorgantes.iterator(); i.hasNext(); cont++)
			{
				OtorganteNatural otorgante = (OtorganteNatural)i.next();
				if (cont == iPosicion)
				{
					minuta.removeOtorgantesNaturale(otorgante);
					break;
				}
			}
		}

		session.setAttribute(WebKeys.LISTA_OTORGANTES, minuta.getOtorgantesNaturales());

		return null;
	}

	
	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente
	 * de la acción de negocio.
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespConsultasReparto respuesta = (EvnRespConsultasReparto) evento;

		context = session.getServletContext();
		if (respuesta != null) {
			if(respuesta.getTipoEvento().equals(EvnRespConsultasReparto.TABLA_REPARTO_FECHA)){
				session.setAttribute(WebKeys.TABLA_MINUTAS,respuesta.getPayload());
			} else if (respuesta.getTipoEvento().equals(EvnRespConsultasReparto.MINUTA_REPARTO)){
				Minuta minuta = (Minuta)respuesta.getPayload();
				session.setAttribute(WebKeys.MINUTA, minuta);
				session.setAttribute(WebKeys.LISTA_CIRCULOS_NOTARIALES,respuesta.getCirculosNotariales());
				session.setAttribute(AWRepartoNotarial.LISTADO_TURNOS_RESTITUCION_MINUTA,respuesta.getTurnosRestitucion());
				session.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, minuta.getEntidadesPublicas());
				session.setAttribute(WebKeys.LISTA_OTORGANTES, minuta.getOtorgantesNaturales());
				session.setAttribute(WebKeys.LISTA_ACTOS_MINUTA, minuta.getAccionesNotariales());  
			} else if(respuesta.getTipoEvento().equals(EvnRespConsultasReparto.TABLA_REPARTO_OTORGANTE)){
				session.setAttribute(WebKeys.TABLA_MINUTAS,respuesta.getPayload());
			} else if(respuesta.getTipoEvento().equals(EvnRespConsultasReparto.TABLA_MINUTAS_PENDIENTES_REPARTO)){
				session.setAttribute(WebKeys.TABLA_MINUTAS,respuesta.getPayload());
			} else if(respuesta.getTipoEvento().equals(EvnRespConsultasReparto.MINUTA_EDITADA)){
				session.setAttribute(WebKeys.MINUTA,respuesta.getPayload());
			}	
		} else {
			removerInfo();
		}
	}

}
