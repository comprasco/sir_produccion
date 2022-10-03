package gov.sir.core.web.acciones.administracion;

import java.util.List;
import java.util.StringTokenizer;

import gov.sir.core.eventos.administracion.EvnImprimirRepartoNotarial;
import gov.sir.core.eventos.administracion.EvnRespImprimirRepartoNotarial;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ImprimirFolioException;
import gov.sir.core.web.acciones.excepciones.ImprimirRepartoNotarialException;

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
 * de impresión de folios provenientes de solicitudes 
 * a través del protocolo HTTP
 * @author jmendez
 */
public class AWImpresionRepartoNotarial extends SoporteAccionWeb {

	/** Constante que identifica la acción de imprimir un  folio */
	public static final String IMPRIMIR_ACTA = "IMPRIMIR_ACTA";

	/** Constante que identifica la acción de imprimir un  CERTIFICADO  */
	public static final String IMPRIMIR_CARATULA = "IMPRIMIR_CARATULA";
	
	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_RECIBO = "REIMPRIMIR_RECIBO";
	
	/** Constante que identifica la acción de reimprimir una CONSULTA  */
	public static final String REIMPRIMIR_CONSULTA = "REIMPRIMIR_CONSULTA";
    
    public static final String OBTENER_ULTIMO_TURNO_IMPRESO = "OBTENER_ULTIMO_TURNO_IMPRESO";

	/** Constante que identifica las acción de terminar la utilización de los servicios 
	 * de la acción WEB (Para limpiar la sesión y redirigir a la página principal de páginas
	 * administrativas */
	public static final String TERMINA = "TERMINA";

	///////////////////////
	/** Nombre de variable de sesión que indica la impresión satisfactoria de un folio */
	public static final String IMPRESION_CARATULA_EJECUTADA = "IMPRESION_CARATULA_EJECUTADA";

	/** Nombre de variable de sesión que indica la impresión satisfactoria de un CERTIFICADO */
	public static final String IMPRESION_ACTA_EJECUTADA = "IMPRESION_CERTIFICADO_EJECUTADA";
	
	/** Constante que identifica que se quiere obtener las impresoras del circulo del usuario*/
	public static final String OBTENER_IMPRESORAS_CIRCULO_CARATULA_REPARTO = "OBTENER_IMPRESORAS_CIRCULO_CARATULA_REPARTO";
	
	/** Constante que identifica que se quiere obtener las impresoras del circulo del usuario*/
	public static final String OBTENER_IMPRESORAS_CIRCULO_ACTA_REPARTO = "OBTENER_IMPRESORAS_CIRCULO_ACTA_REPARTO";

	
	/** Constante para identificar la lista de notas informativas para reimpresión. */
	public static final String LISTA_INFORMATIVAS_REIMPRESION = "LISTA_INFORMATIVAS_REIMPRESION";
    
    public static final String ULTIMO_TURNO_IMPRESO = "ULTIMO_TURNO_IMPRESO";
    
	public static final String LISTA_ESTACIONES = "LISTA_ESTACIONES";
	
	public static final String ULTIMO_TURNO_IMPRESO_PROCESO = "ULTIMO_TURNO_IMPRESO_PROCESO";
	public static final String LISTA_TURNOS_ASOCIADOS_REIMPRESION= "LISTA_TURNOS_ASOCIADOS_REIMPRESION";
	
	public static final String LISTA_RECIBOS_IMPRIMIR="LISTA_RECIBOS_IMPRIMIR";
	
	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_RECIBOS = "REIMPRIMIR_RECIBOS";
	
	public static final String REGRESAR_REIMPRIMIR_RECIBO="REGRESAR_REIMPRIMIR_RECIBO";

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWImpresionRepartoNotarial.IMPRIMIR_ACTA)) {
			return imprimirActa(request);
		} else if (accion.equals(AWImpresionRepartoNotarial.IMPRIMIR_CARATULA)) {
			return imprimirCaratula(request);
		} else if (accion.equals(AWImpresionRepartoNotarial.REIMPRIMIR_RECIBO)) {
			return reimprimirRecibo(request);	
		} else if (accion.equals(AWImpresionRepartoNotarial.REIMPRIMIR_CONSULTA)) {
			return reimprimirConsulta(request);	
		} else if (accion.equals(AWImpresionRepartoNotarial.TERMINA)) {
			return limpiarSesion(request);
		} else if (accion.equals(AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_CARATULA_REPARTO)) {
			return obtenerImpresoras(request);
		} else if (accion.equals(AWImpresionRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO_ACTA_REPARTO)) {
			return obtenerImpresorasActa(request);
		} else if (accion.equals(AWImpresionRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO)) {
            return consultarUltimoReciboImpreso(request);
        } else if (accion.equals(AWImpresionRepartoNotarial.ULTIMO_TURNO_IMPRESO_PROCESO)){
        	return consultarUltimoTurnoImpresoProceso(request);
        } else if (accion.equals(AWImpresionRepartoNotarial.REIMPRIMIR_RECIBOS)) {
			return reimprimirRecibos(request);	
		} else if (accion.equals(AWImpresionRepartoNotarial.REGRESAR_REIMPRIMIR_RECIBO)){
			return regresarReimprimirRecibo(request);
		}

		return null;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento regresarReimprimirRecibo(HttpServletRequest request) {
		String turnoViejo=(String)request.getSession().getAttribute(CTurno.TURNO_ANTERIOR);
		if (turnoViejo!=null){
			request.getSession().setAttribute(CTurno.ID_TURNO,turnoViejo);
		}
		return null;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de reimpresión de un recibo
	 * @param request
	 * @return evento <code>EvnImprimirRepartoNotarial</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirRepartoNotarial reimprimirRecibos(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		Estacion estacion = null;

		String[] idTurnos=request.getParameterValues(AWImpresionRepartoNotarial.LISTA_RECIBOS_IMPRIMIR);
		String idEstacion = request.getParameter("ID_ESTACION");
		
		if(idEstacion !=null && idEstacion.length()>0){
			estacion = new Estacion();
			estacion.setEstacionId(idEstacion);
		}
		
		if (idTurnos==null || idTurnos.length==0){
			throw new AccionWebException("Debe escoger al menos un turno a imprimir");
		}
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);		

		EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.REIMPRIMIR_RECIBOS);
		evento.setTurnosReimprimir(idTurnos);
		evento.setUsuarioNeg(usuarioNeg);
		String uid = request.getSession().getId();
		evento.setUid(uid);
		evento.setEstacion(estacion);
		
		return evento;
	}
	/**
	 * @param request
	 * @return
	 */
	private Evento consultarUltimoTurnoImpresoProceso(HttpServletRequest request) throws AccionWebException{
		String proceso=request.getParameter(CProceso.PROCESO_ID);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);		

        EvnImprimirRepartoNotarial evento=new EvnImprimirRepartoNotarial(usuario,EvnImprimirRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO_PROCESO);
        evento.setUsuarioNeg(usuarioNeg);
		evento.setProceso(Long.parseLong(proceso));
		evento.setCirculo(circulo);
		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de impresión de folio
	 * @param request
	 * @return evento <code>EvnImprimirRepartoNotarial</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirRepartoNotarial imprimirActa(HttpServletRequest request) throws AccionWebException 
	{
		HttpSession session = request.getSession();
		ImprimirRepartoNotarialException exception = new ImprimirRepartoNotarialException();
		String id = request.getParameter(CTurno.ID_TURNO);
		String tipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		String descripcionNota = request.getParameter(CTipoNota.DESCRIPCION);
		
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe digitar el número del reparto.");
		} else {
			session.setAttribute(CTurno.ID_TURNO, id);
		}
		
		String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		if(numCopias != null && numCopias.length()<=0){
			exception.addError("Debe ingresar el número de copias que debe imprimir");			
		} else {
			try{
				int i = new Integer(numCopias).intValue();
				if(i<1){
					exception.addError("El número de copias a imprimir debe ser mayor a cero");
				}
			}catch(Exception e){
				exception.addError("El número de copias a imprimir debe ser un valor numérico");	
			}
		}
		
		String impresora = request.getParameter(WebKeys.IMPRESORA);	
		Circulo circulo= (Circulo)session.getAttribute(WebKeys.CIRCULO);
		List roles=(List)session.getAttribute(WebKeys.LISTA_ROLES);
		
		session.setAttribute(CTurno.ID_TURNO, id);
		session.setAttribute(CTipoNota.ID_TIPO_NOTA, tipoNota);
		session.setAttribute(CTipoNota.DESCRIPCION, descripcionNota);
		session.setAttribute(WebKeys.NUMERO_COPIAS_IMPRESION, numCopias);
		session.setAttribute(WebKeys.IMPRESORA, impresora);	
		
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		RepartoNotarial repartoNotarial = new RepartoNotarial();
		repartoNotarial.setIdRepartoNotarial(id);

		EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.IMPRIMIR_ACTA);
		evento.setRepartoNotarial(repartoNotarial);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
		evento.setUsuarioNeg(usuarioNeg);
		String uid = request.getSession().getId();
		evento.setUid(uid);
		if(impresora!=null && !impresora.equals(WebKeys.SIN_SELECCIONAR)){
			evento.setImpresoraSeleccionada(impresora);	
	    }
		
		evento.setRoles(roles);
		evento.setNumeroImpresiones(new Integer(numCopias).intValue());
		evento.setCirculo(circulo);

		return evento;
	
		
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de impresión de un certificado
	 * @param request
	 * @return evento <code>EvnImprimirRepartoNotarial</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirRepartoNotarial imprimirCaratula(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		ImprimirRepartoNotarialException exception = new ImprimirRepartoNotarialException();

		String id = request.getParameter(CTurno.ID_TURNO);
		
		String tipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		String descripcionNota = request.getParameter(CTipoNota.DESCRIPCION);
		
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe digitar el número del turno.");
		} else {
			session.setAttribute(CTurno.ID_TURNO, id);
		}
		
		if(tipoNota ==null || tipoNota.equals(WebKeys.SIN_SELECCIONAR)){
			exception.addError("Debe seleccionar un tipo de Nota informativa para la reimpresión");
		}
		
		if (descripcionNota == null || descripcionNota.length()<=0)
		{
			exception.addError("Debe ingresar una descripción del motivo por el cual se hace la reimpresión");
		}
		

		
		String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		if(numCopias != null && numCopias.length()<=0){
			exception.addError("Debe ingresar el número de copias que debe imprimir");			
		} else {
			try{
				int i = new Integer(numCopias).intValue();
				if(i<1){
					exception.addError("El número de copias a imprimir debe ser mayor a cero");
				}
			}catch(Exception e){
				exception.addError("El número de copias a imprimir debe ser un valor numérico");	
			}
		}
		
		String impresora = request.getParameter(WebKeys.IMPRESORA);	
		Circulo circulo= (Circulo)session.getAttribute(WebKeys.CIRCULO);
		List roles=(List)session.getAttribute(WebKeys.LISTA_ROLES);
		
		session.setAttribute(CTurno.ID_TURNO, id);
		session.setAttribute(CTipoNota.ID_TIPO_NOTA, tipoNota);
		session.setAttribute(CTipoNota.DESCRIPCION, descripcionNota);
		session.setAttribute(WebKeys.NUMERO_COPIAS_IMPRESION, numCopias);
		session.setAttribute(WebKeys.IMPRESORA, impresora);	
		
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Turno dato = new Turno();
		dato.setIdWorkflow(id.toUpperCase());

		EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.IMPRIMIR_CARATULA);
		evento.setTurno(dato);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
		evento.setUsuarioNeg(usuarioNeg);
		String uid = request.getSession().getId();
		evento.setUid(uid);
		if(impresora!=null && !impresora.equals(WebKeys.SIN_SELECCIONAR)){
			evento.setImpresoraSeleccionada(impresora);	
	    }
		
		evento.setRoles(roles);
		evento.setNumeroImpresiones(new Integer(numCopias).intValue());
		evento.setCirculo(circulo);
		evento.setDescripcionNota(descripcionNota);
		evento.setTipoNota(tipoNota);

		

		return evento;
	}

    private EvnImprimirRepartoNotarial consultarUltimoReciboImpreso(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);		

        EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setEstacion(estacion);
        
        return evento;
    }

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de reimpresión de un recibo
	 * @param request
	 * @return evento <code>EvnImprimirRepartoNotarial</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirRepartoNotarial reimprimirRecibo(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		Estacion estacion = null;

		String idTurno=request.getParameter(CTurno.ID_TURNO);
		StringTokenizer st=new StringTokenizer(idTurno,",");
		String[] idTurnos=null;
		if (st.countTokens()>0){
			idTurnos=new String[st.countTokens()];
			int i=0;
			while(st.hasMoreTokens()){
				String turno=st.nextToken();
				idTurnos[i]=turno;
				i++;
			}	
		}
		
		String idEstacion = request.getParameter("ID_ESTACION");
		
		if(idEstacion !=null && idEstacion.length()>0){
			estacion = new Estacion();
			estacion.setEstacionId(idEstacion);
		}
		

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);		

		if (idTurnos==null){
			EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.REIMPRIMIR_RECIBO);
			evento.setIdTurno(idTurno);
			evento.setUsuarioNeg(usuarioNeg);
			String uid = request.getSession().getId();
			evento.setUid(uid);
			evento.setEstacion(estacion);
			
			return evento;	
		}else{
			EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.REIMPRIMIR_RECIBOS);
			evento.setTurnosReimprimir(idTurnos);
			evento.setUsuarioNeg(usuarioNeg);
			String uid = request.getSession().getId();
			evento.setUid(uid);
			evento.setEstacion(estacion);
			
			return evento;
		}
	}
	
////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de reimpresión de una consulta
	 * @param request
	 * @return evento <code>EvnImprimirRepartoNotarial</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirRepartoNotarial reimprimirConsulta(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		
		ImprimirFolioException exception = new ImprimirFolioException();
		
		String idTurno = request.getParameter(CTurno.ID_TURNO);
		
		if(idTurno == null){
			exception.addError("Por favor ingresar el turno a reimprimir");
		}
		
		if(exception.getErrores().size()>0){
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);		

		EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuario, EvnImprimirRepartoNotarial.REIMPRIMIR_CONSULTA);
		evento.setIdTurno(idTurno.toUpperCase());
		String uid = request.getSession().getId();
		evento.setUid(uid);

		session.setAttribute(CTurno.ID_TURNO, idTurno);
		
		return evento;
	}
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de limpiar la sesión luego que el usuario ha terminado de 
	 * usar las pantallas administrativas relacionadas con Ciudadanos
	 */
	private EvnImprimirRepartoNotarial limpiarSesion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		session.removeAttribute(AWImpresionRepartoNotarial.IMPRESION_CARATULA_EJECUTADA);
		session.removeAttribute(CFolio.ID_MATRICULA);
		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(AWImpresionRepartoNotarial.LISTA_TURNOS_ASOCIADOS_REIMPRESION);
		
		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
		session.removeAttribute(CTipoNota.DESCRIPCION);		
		session.removeAttribute(WebKeys.NUMERO_COPIAS_IMPRESION);
		session.removeAttribute(WebKeys.IMPRESORA);
		
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * @param request
	 * @return
	 */
	private Evento obtenerImpresoras(HttpServletRequest request) 
	{
		Circulo cir= (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		Usuario usuarioEnSesion= (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuarioEnSesion);
		evento.setTipoEvento(EvnImprimirRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO);
		evento.setCirculo(cir);
		return evento;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento obtenerImpresorasActa(HttpServletRequest request) 
	{
		Circulo cir= (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		Usuario usuarioEnSesion= (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnImprimirRepartoNotarial evento = new EvnImprimirRepartoNotarial(usuarioEnSesion);
		evento.setTipoEvento(EvnImprimirRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO);
		evento.setCirculo(cir);
		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespImprimirRepartoNotarial respuesta = (EvnRespImprimirRepartoNotarial) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespImprimirRepartoNotarial.IMPRESION_CARATULA_OK)) {
				session.setAttribute(AWImpresionRepartoNotarial.IMPRESION_CARATULA_EJECUTADA, new Boolean(true));
				session.removeAttribute(CFolio.ID_MATRICULA);
				session.removeAttribute(CTurno.ID_TURNO);
				session.removeAttribute(AWImpresionRepartoNotarial.LISTA_TURNOS_ASOCIADOS_REIMPRESION);
				
				session.removeAttribute(CTurno.ID_TURNO);
				session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
				session.removeAttribute(CTipoNota.DESCRIPCION);		
				session.removeAttribute(WebKeys.NUMERO_COPIAS_IMPRESION);
				session.removeAttribute(WebKeys.IMPRESORA);
				return;
			}
			else if (tipoEvento.equals(EvnRespImprimirRepartoNotarial.IMPRESION_ACTA_OK)) {
				session.setAttribute(AWImpresionRepartoNotarial.IMPRESION_ACTA_EJECUTADA, new Boolean(true));
				session.removeAttribute(CFolio.ID_MATRICULA);
				session.removeAttribute(CTurno.ID_TURNO);
				session.removeAttribute(AWImpresionRepartoNotarial.LISTA_TURNOS_ASOCIADOS_REIMPRESION);
				
				session.removeAttribute(CTurno.ID_TURNO);
				session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
				session.removeAttribute(CTipoNota.DESCRIPCION);		
				session.removeAttribute(WebKeys.NUMERO_COPIAS_IMPRESION);
				session.removeAttribute(WebKeys.IMPRESORA);
				return;
			}
			
			else if (tipoEvento.equals(EvnRespImprimirRepartoNotarial.DETERMINAR_ESTACION)) {
				String[] idTurnos=request.getParameterValues(AWImpresionRepartoNotarial.LISTA_RECIBOS_IMPRIMIR);
				String turnos="";
				if (idTurnos!=null && idTurnos.length>0){
					for (int i=0; i<idTurnos.length; i++){
						turnos=turnos+idTurnos[i];
						if (i<idTurnos.length-1){
							turnos=turnos+",";
						}
					}
				}
				if (turnos.length()>0){
					String turno=(String)session.getAttribute(CTurno.ID_TURNO);
					if (turno!=null){
						session.setAttribute(CTurno.TURNO_ANTERIOR,turno);	
					}
					session.setAttribute(CTurno.ID_TURNO,turnos);
				}
				session.setAttribute(AWImpresionRepartoNotarial.LISTA_ESTACIONES, respuesta.getPayload());				
				return;
			} else if (tipoEvento.equals(EvnRespImprimirRepartoNotarial.IMPRESION_ACTA_OK)) {
				session.setAttribute(AWImpresionRepartoNotarial.IMPRESION_ACTA_EJECUTADA, new Boolean(true));
				return;
			} else if (tipoEvento.equals(EvnRespImprimirRepartoNotarial.REIMPRIMIR_CONSULTA_OK)) {
				return;
			} else if (tipoEvento.equals(EvnRespImprimirRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO)) {
				Circulo cir= respuesta.getCirculo();
				String idCirculo= cir.getIdCirculo();
				String key = WebKeys.LISTA_IMPRESORAS+"_"+idCirculo;
				session.setAttribute(key,respuesta.getPayload());
				session.setAttribute(WebKeys.RECARGA, new Boolean(false));
				session.setAttribute(AWImpresionRepartoNotarial.LISTA_INFORMATIVAS_REIMPRESION, respuesta.getListaNotasReimpresion());
				return;
			} else if(tipoEvento.equals(EvnRespImprimirRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO_OK)) {
                session.setAttribute(ULTIMO_TURNO_IMPRESO, respuesta.getPayload());
                session.setAttribute(WebKeys.RECARGA, new Boolean(false));
                return;
            } else if(tipoEvento.equals(EvnRespImprimirRepartoNotarial.ULTIMO_TURNO_PROCESO)){
            	session.setAttribute(CTurno.ID_TURNO,respuesta.getPayload());
            	session.setAttribute(AWImpresionRepartoNotarial.LISTA_TURNOS_ASOCIADOS_REIMPRESION,respuesta.getTurnosAsociados());
            }
		}
	}
}
