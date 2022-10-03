package gov.sir.core.web.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
 /**
  * @autor Carlos Torres
  * @mantis 11309
  */
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.util.List;
import java.util.StringTokenizer;

import gov.sir.core.eventos.administracion.EvnImprimirFolio;
import gov.sir.core.eventos.administracion.EvnRespImprimirFolio;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ImprimirFolioException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos
 * de impresión de folios provenientes de solicitudes 
 * a través del protocolo HTTP
 * @author jmendez
 */
public class AWImpresionFolio extends SoporteAccionWeb {

	/** Constante que identifica la acción de imprimir un  folio */
	public static final String IMPRIMIR_FOLIO = "IMPRIMIR_FOLIO";
        
	/** Constante que identifica la acción de imprimir un  CERTIFICADO  */
	public static final String IMPRIMIR_CERTIFICADO = "IMPRIMIR_CERTIFICADO";
	
	/** Constante que identifica la acción de imprimir un  CERTIFICADO  */
	public static final String IMPRIMIR_CERTIFICADO_PERTENENCIA = "IMPRIMIR_CERTIFICADO_PERTENENCIA";
	
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
	public static final String IMPRESION_FOLIO_EJECUTADA = "IMPRESION_FOLIO_EJECUTADA";

	/** Nombre de variable de sesión que indica la impresión satisfactoria de un CERTIFICADO */
	public static final String IMPRESION_CERTIFICADO_EJECUTADA = "IMPRESION_CERTIFICADO_EJECUTADA";
	
	/** Constante que identifica que se quiere obtener las impresoras del circulo del usuario*/
	public static final String OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO = "OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO";
	
	/** Constante que identifica que se quiere obtener las impresoras del circulo del usuario*/
	public static final String OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA = "OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA";
	
	/** Constante para identificar la lista de notas informativas para reimpresión. */
	public static final String LISTA_INFORMATIVAS_REIMPRESION = "LISTA_INFORMATIVAS_REIMPRESION";
    
    public static final String ULTIMO_TURNO_IMPRESO = "ULTIMO_TURNO_IMPRESO";
    
	public static final String LISTA_ESTACIONES = "LISTA_ESTACIONES";
	
	public static final String ULTIMO_TURNO_IMPRESO_PROCESO = "ULTIMO_TURNO_IMPRESO_PROCESO";
        
        //******* SE AGREGA NUEVA LINEA PARA OBTENER LA ULTIMA SOLICITUD DE REIMPRESION DE LIQUIDACION *********
        public static final String ULTIMA_SOLICITUD_LIQUIDACION = "ULTIMA_SOLICITUD_LIQUIDACION";
        
	public static final String LISTA_TURNOS_ASOCIADOS_REIMPRESION= "LISTA_TURNOS_ASOCIADOS_REIMPRESION";
	
	public static final String LISTA_RECIBOS_IMPRIMIR="LISTA_RECIBOS_IMPRIMIR";
	
	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_RECIBOS = "REIMPRIMIR_RECIBOS";
	
	public static final String REGRESAR_REIMPRIMIR_RECIBO="REGRESAR_REIMPRIMIR_RECIBO";

        /** Constante que identifica que se van agregar matriculas desde archivo */
	public final static String AGREGAR_DE_ARCHIVO = "AGREGAR_DE_ARCHIVO";
        
        /** Constante que identifica que se van nombre del archivo cargado */
	public final static String NOMBRE_ARCHIVO_CSV = "NOMBRE_ARCHIVO_CSV";
        
        /** Constante que identifica el listado de matriculas cargadas desde archivo csv */
	public final static String LISTA_MATRICULAS = "LISTA_MATRICULAS";
        
        /** Constante que identifica el tipo de tarea a programar */
	public static final String IMP_MASIVA_SIMPLE_FOLIO = "IMP_MASIVA_SIMPLE_FOLIO";
                
        /** Constante que identifica la acción de programar tarea */
	public static final String PROGRAMAR_TAREA = "PROGRAMAR_TAREA";
        
        /** Constante que identifica la acción de programar tarea */
	public static final String PROGRAMAR_TAREA_EXITO = "PROGRAMAR_TAREA_EXITO";
        
        /**
         * Constante que identifica el control de navegacion que retorna esta pantalla
         */
        public static final String BACK = "BACK";
        
        /** Constante que identifica la acción de programar tarea */
	public static final String IMP_MASIVA_SIMPLE_FOLIO_FTP = "IMP_MASIVA_SIMPLE_FOLIO_FTP";
        
        private static final String CONTENT_TYPE = "application/vnd.ms-excel";
        
        private String nombreArchivo;
        
       private StringBuffer listaFolios;

        /**
         * Este método se encarga de procesar la solicitud del
         *  <code>HttpServletRequest</code> de acuerdo al tipo de accion que tenga
         * como parámetro
         */
        public Evento perform(HttpServletRequest request) throws AccionWebException {
            String accion = request.getParameter(WebKeys.ACCION);
            HttpSession session = request.getSession();
            boolean isMultipart = FileUpload.isMultipartContent(request);

            if (isMultipart) {
                return UploadFile(request);
            }
            if ((accion == null) || (accion.trim().length() == 0)) {
                throw new AccionInvalidaException("Debe indicar una accion valida");
            } else if (accion.equals(AWImpresionFolio.IMPRIMIR_FOLIO)) {
                return imprimirFolio(request);
            } else if (accion.equals(AWImpresionFolio.IMPRIMIR_CERTIFICADO)) {
                return imprimirCertificado(request);
            } else if (accion.equals(AWImpresionFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA)) {
                return imprimirCertificado(request);
            } else if (accion.equals(AWImpresionFolio.REIMPRIMIR_RECIBO)) {
                return reimprimirRecibo(request);
            } else if (accion.equals(AWImpresionFolio.REIMPRIMIR_CONSULTA)) {
                return reimprimirConsulta(request);
            } else if (accion.equals(AWImpresionFolio.TERMINA)) {
                return limpiarSesion(request);
            } else if (accion.equals(AWImpresionFolio.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO)) {
                return obtenerImpresoras(request);
            } else if (accion.equals(AWImpresionFolio.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA)) {
                return obtenerImpresoras(request);
            } else if (accion.equals(AWImpresionFolio.OBTENER_ULTIMO_TURNO_IMPRESO)) {
                return consultarUltimoReciboImpreso(request);
            } else if (accion.equals(AWImpresionFolio.ULTIMO_TURNO_IMPRESO_PROCESO)) {
                return consultarUltimoTurnoImpresoProceso(request);
            } else if (accion.equals(AWImpresionFolio.REIMPRIMIR_RECIBOS)) {
                return reimprimirRecibos(request);
            } else if (accion.equals(AWImpresionFolio.REGRESAR_REIMPRIMIR_RECIBO)) {
                return regresarReimprimirRecibo(request);
            } else if (accion.equals(AWImpresionFolio.PROGRAMAR_TAREA)) {
                return programarTarea(request);
            } else if (accion.equals(AWImpresionFolio.BACK)){
                return null;
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
     * Este método se encarga de tomar los valores del
     * <code>HttpServletRequest</code> para generar un evento de reimpresión de
     * un recibo
     *
     * @param request
     * @return evento <code>EvnImprimirFolio</code> con la información del
     * círculo
     * @throws AccionWebException
     */
    private EvnImprimirFolio reimprimirRecibos(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();
        Estacion estacion = null;

        String turno = (String) session.getAttribute(CTurno.ID_TURNO);
        String solicitud = (String) session.getAttribute(CSolicitud.NUMERO_SOLICITUD_LIQUIDACION);
        String[] idTurnos = request.getParameterValues(AWImpresionFolio.LISTA_RECIBOS_IMPRIMIR);
        String checked= request.getParameter(WebKeys.REIMPRIME_RECIBO_MAYOR_VALOR);
        
        if (turno != null) {
            String idEstacion = request.getParameter("ID_ESTACION");

            if (idEstacion != null && idEstacion.length() > 0) {
                estacion = new Estacion();
                estacion.setEstacionId(idEstacion);
            }

            if (idTurnos == null || idTurnos.length == 0) {
                throw new AccionWebException("Debe escoger al menos un turno a imprimir");
            }

            Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

            EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.REIMPRIMIR_RECIBOS);
            evento.setTurnosReimprimir(idTurnos);
            evento.setUsuarioNeg(usuarioNeg);
            String uid = request.getSession().getId();
            evento.setUid(uid);
            evento.setEstacion(estacion);                   
            if (checked != null) {
                evento.setMayorValor(true);
            }
            
            return evento;
        } else if (solicitud != null) {

            String idEstacion = request.getParameter("ID_ESTACION");

            if (idEstacion != null && idEstacion.length() > 0) {
                estacion = new Estacion();
                estacion.setEstacionId(idEstacion);
            }

            if (idTurnos == null || idTurnos.length == 0) {
                throw new AccionWebException("Debe escoger al menos una solicitud a imprimir");
            }

            Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

            EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.REIMPRIMIR_SOLICITUD_LIQUIDACION);
            evento.setTurnosReimprimir(idTurnos);
            evento.setUsuarioNeg(usuarioNeg);
            String uid = request.getSession().getId();
            evento.setUid(uid);
            evento.setEstacion(estacion);
            return evento;

        } else {
            return null;
        }
    }
    
    /**
     * @param request
     * @return
     */
    private Evento consultarUltimoTurnoImpresoProceso(HttpServletRequest request) throws AccionWebException {
        request.getSession().removeAttribute(CTurno.ID_TURNO);
        request.getSession().removeAttribute(CSolicitud.NUMERO_SOLICITUD_LIQUIDACION);
        request.getSession().removeAttribute(AWImpresionFolio.LISTA_TURNOS_ASOCIADOS_REIMPRESION);
        
        String proceso = request.getParameter(CProceso.PROCESO_ID);
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        
        if (!proceso.equals(CProceso.PROCESO_SOLICITUD_LIQUIDACIONES)) {
            EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.OBTENER_ULTIMO_TURNO_IMPRESO_PROCESO);
            evento.setUsuarioNeg(usuarioNeg);
            evento.setProceso(Long.parseLong(proceso));
            evento.setCirculo(circulo);
            return evento;

        } else {
            EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.OBTENER_ULTIMA_SOLICITUD_LIQUIDACION);
            evento.setUsuarioNeg(usuarioNeg);
            evento.setCirculo(circulo);
            return evento;
        }
    }

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de impresión de folio
	 * @param request
     * @return evento <code>EvnImprimirFolio</code> con la información del
     * círculo
     * @throws AccionWebException
     */
    private EvnImprimirFolio imprimirFolio(HttpServletRequest request) throws AccionWebException {
        HttpSession session = request.getSession();

        ImprimirFolioException exception = new ImprimirFolioException();
        /**
         * @author : Julio Alcazar
         * @change : Se agrega el Circulo Actual y los Roles del Usuario al
         * evento. Caso Mantis : 0009044: Acta - Requerimiento No 036_151 -
         * Impresión de Folios
         */
        Circulo circuloActual = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        List roles = (List) session.getAttribute(WebKeys.LISTA_ROLES);

        String id = request.getParameter(CFolio.ID_MATRICULA);
        if ((id == null) || (id.trim().length() == 0)) {
            exception.addError("Debe digitar un número de matrícula.");
        } else {
            session.setAttribute(CFolio.ID_MATRICULA, id);
            /**
             * @author : Julio Alcazar
             * @change : se elimina el codigo del caso mantis 8005 para
             * organizarlo en el negocio. Caso Mantis : 0009044: Acta -
             * Requerimiento No 036_151 - Impresión de Folios
             */
        }
        /**
         * @autor Carlos Torres
         * @mantis 11309
         */
        TrasladoSIR trasladoSir = new TrasladoSIR();

        /**
         * @autor Edgar Lora
         * @mantis 11987
         */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        try {
            if (validacionesSIR.isEstadoFolioBloqueado(id)) {
                exception.addError("La matricula que desea imprimir se encuentra en estado 'Bloqueado'.");
                /**
                 * @autor Carlos Torres
                 * @mantis 11309
                 */
            } else if (trasladoSir.trasladadoFolio(id)) {
                exception.addError("El folio " + id + " esta en estado Trasladado");
            } else if (trasladoSir.isBloqueDeTraslado(id)) {
                exception.addError("El folio " + id + " esta pendiente por confirmar traslado.");
            }
        } catch (GeneralSIRException ex) {
            if (ex.getMessage() != null) {
                exception.addError(ex.getMessage());
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        Folio dato = new Folio();
        dato.setIdMatricula(id);

        EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.IMPRIMIR_FOLIO);
        evento.setFolio(dato);
        /**
         * @author : Julio Alcazar
         * @change : Se agrega el Circulo Actual y los Roles del Usuario al
         * evento. Caso Mantis : 0009044: Acta - Requerimiento No 036_151 -
         * Impresión de Folios
         */
        evento.setCirculo(circuloActual);
        evento.setRoles(roles);
        String uid = request.getSession().getId();
        evento.setUid(uid);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        evento.setUsuarioNeg(usuarioSIR);

        return evento;
    }

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de impresión de un certificado
	 * @param request
	 * @return evento <code>EvnImprimirFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirFolio imprimirCertificado(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		
		String accion=request.getParameter(WebKeys.ACCION).trim();
		
		ImprimirFolioException exception = new ImprimirFolioException();

		String id = request.getParameter(CTurno.ID_TURNO);
		
		String tipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		String descripcionNota = request.getParameter(CTipoNota.DESCRIPCION);
		
		if(tipoNota ==null || tipoNota.equals(WebKeys.SIN_SELECCIONAR)){
			exception.addError("Debe seleccionar un tipo de Nota informativa para la reimpresión");
		}
		
		if (descripcionNota == null || descripcionNota.length()<=0)
		{
			exception.addError("Debe ingresar una descripción del motivo por el cual se hace la reimpresión");
		}
		
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe digitar el número del turno.");
		} else {
			session.setAttribute(CTurno.ID_TURNO, id);
		}
		
		String numCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		if(numCopias != null && numCopias.length()<=0){
			exception.addError("Debe ingresar el número de copias que debe imprimir");			
		} else {
			try{
				int i = Integer.parseInt(numCopias);
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
		EvnImprimirFolio evento;
		if(accion.equals("IMPRIMIR_CERTIFICADO_PERTENENCIA"))
			evento
				= new EvnImprimirFolio(usuario, EvnImprimirFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA);
		else
			evento
			= new EvnImprimirFolio(usuario, EvnImprimirFolio.IMPRIMIR_CERTIFICADO);
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
		evento.setDescripcionNota(descripcionNota.toUpperCase());
		evento.setTipoNota(tipoNota);

		

		return evento;
	}

    private EvnImprimirFolio consultarUltimoReciboImpreso(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        
        Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);		

        EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.OBTENER_ULTIMO_TURNO_IMPRESO);
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
	 * @return evento <code>EvnImprimirFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirFolio reimprimirRecibo(HttpServletRequest request) throws AccionWebException {
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
			EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.REIMPRIMIR_RECIBO);
			evento.setIdTurno(idTurno);
			evento.setUsuarioNeg(usuarioNeg);
			String uid = request.getSession().getId();
			evento.setUid(uid);
			evento.setEstacion(estacion);
			
			return evento;	
		}else{
			EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.REIMPRIMIR_RECIBOS);
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
	 * @return evento <code>EvnImprimirFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnImprimirFolio reimprimirConsulta(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		
		ImprimirFolioException exception = new ImprimirFolioException();
		
		String idTurno = request.getParameter(CTurno.ID_TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		if(idTurno == null){
			exception.addError("Por favor ingresar el turno a reimprimir");
		}
		
		if(exception.getErrores().size()>0){
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);		

		EvnImprimirFolio evento = new EvnImprimirFolio(usuario, EvnImprimirFolio.REIMPRIMIR_CONSULTA);
		evento.setIdTurno(idTurno.toUpperCase());
		evento.setCirculo(circulo);
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
	private EvnImprimirFolio limpiarSesion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		session.removeAttribute(AWImpresionFolio.IMPRESION_FOLIO_EJECUTADA);
		session.removeAttribute(CFolio.ID_MATRICULA);
		session.removeAttribute(CTurno.ID_TURNO);
		session.removeAttribute(AWImpresionFolio.LISTA_TURNOS_ASOCIADOS_REIMPRESION);
		
		session.removeAttribute(CTurno.ID_TURNO);
                session.removeAttribute(CSolicitud.NUMERO_SOLICITUD_LIQUIDACION);
		session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
		session.removeAttribute(CTipoNota.DESCRIPCION);		
		session.removeAttribute(WebKeys.NUMERO_COPIAS_IMPRESION);
		session.removeAttribute(WebKeys.IMPRESORA);
		session.removeAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO);
                session.removeAttribute(AWImpresionFolio.IMP_MASIVA_SIMPLE_FOLIO_FTP);
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * @param request
	 * @return
	 */
	private Evento obtenerImpresoras(HttpServletRequest request) {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		Circulo cir= (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		Usuario usuarioEnSesion= (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnImprimirFolio evento = new EvnImprimirFolio(usuarioEnSesion);
		if (accion.equals("OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA"))
			evento.setTipoEvento(EvnImprimirFolio.OBTENER_IMPRESORAS_CIRCULO_PERTENENCIA);
		else
			evento.setTipoEvento(EvnImprimirFolio.OBTENER_IMPRESORAS_CIRCULO);
		evento.setCirculo(cir);
		return evento;
	}
        
        /**
         * @author     : Ellery David Robles Gómez.
         * @change     : Metodo que se encarga de leer el archivo csv con sus folios y cargarlos a un StringBuffer.
         * Caso_Mantis : 09422: Acta - Requerimiento No 040_151 - Impresión de Folios - Nivel Central.
         */
        private EvnImprimirFolio UploadFile(HttpServletRequest request) throws AccionWebException {
            HttpSession session = request.getSession();
            ImprimirFolioException exception = new ImprimirFolioException();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            String nombreArchivo = null;
            StringBuffer listaFolios = null;
            
            boolean errorCopia = false, errorFormato = false, errorMatriculasInvalidas = false;
            List lstCopia = new ArrayList(), lstFormato = new ArrayList();
            
            try {
                
                DiskFileUpload upload = new DiskFileUpload();
		List list = upload.parseRequest(request);
                Iterator it = list.iterator();
                while ( it.hasNext() ) {
				FileItem fi = (FileItem) it.next();
		
				if ( !fi.isFormField() ) {
					nombreArchivo = fi.getName();
                                        
                                        if ( !nombreArchivo.endsWith(".csv") ) {
						exception.addError("El archivo debe tener extensión .csv, y el procesado no corresponde: "+nombreArchivo);
					} else {
                                            if ( fi.getContentType().equals(CONTENT_TYPE) ) {
                                                BufferedReader in = new BufferedReader(new InputStreamReader(fi.getInputStream()));
                                                String record = "", recArray[] = null, strMat = "";
                                                
                                                // instanciamos folios en un buffer de string;
                                                listaFolios = new StringBuffer();
                                                
                                                while((record = in.readLine()) != null) {
                                                    recArray = record.split(";");
                                                    if ( recArray.length < 2 ) {
                                                        lstFormato.add(record);
                                                        errorFormato = true;
                                                    } else {
//                                                        strMat = recArray[0].trim()+"-"+recArray[1].trim();
                                                        if (recArray[0] != null && recArray[1] != null) {
                                                             /**
                                                              * @autor Carlos Torres
                                                              * @mantis 11309
                                                              */
                                                            strMat = recArray[0].trim()+"-"+recArray[1].trim();
                                                            listaFolios.append(recArray[0].trim()).append("-").append(recArray[1].trim()).append(";");
                                                        }

                                                        if (recArray[2] != null && recArray[3] != null) {
                                                              /**
                                                              * @autor Carlos Torres
                                                              * @mantis 11309
                                                              */
                                                            strMat = recArray[0].trim()+"-"+recArray[1].trim();
                                                            listaFolios.append(recArray[2].trim()).append("-").append(recArray[3].trim()).append(";");
                                                        }

                                                        if (recArray[4] != null && recArray[5] != null) {
                                                              /**
                                                        * @autor Carlos Torres
                                                        * @mantis 11309
                                                        */
                                                            strMat = recArray[0].trim()+"-"+recArray[1].trim();
                                                            listaFolios.append(recArray[4].trim()).append("-").append(recArray[5].trim()).append(";");
                                                        }
                                                        /**
                                                        * @autor Carlos Torres
                                                        * @mantis 11309
                                                        */
                                                        TrasladoSIR trasladoSir = new TrasladoSIR();
                                                        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                                                        try {
                                                            if(validacionesSIR.isEstadoFolioBloqueado(strMat)){
                                                                exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
                                                            }else if(trasladoSir.trasladadoFolio(strMat)){
                                                                    exception.addError("El folio " + strMat + " esta en estado Trasladado");
                                                            } else if(trasladoSir.isBloqueDeTraslado(strMat)){
                                                                    exception.addError("El folio " + strMat + " esta pendiente por confirmar traslado.");
                                                            }
                                                        } catch (GeneralSIRException ex) {
                                                        if(ex.getMessage() != null){
                                                            exception.addError(ex.getMessage());
                                                        }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                }
                }
//                        String r = "C:\"sir_tareas\""+nombreArchivo;
//		// instanciar el objeto readerCSV
//                CsvReader reader = new CsvReader(r);
//                System.out.print("Linea de error: "+r);
//                // asigno separador de valores punto y coma, si no lo cambian queda por defecto la coma
//                reader.setDelimiter(';');
//                
//                // instanciamos folios en un buffer de string;
//                listaFolios = new StringBuffer();
//                
//                // recorremos las filas del fichero
//                        while (reader.readRecord()) {
//                            if (reader.get(0) != null && reader.get(1) != null) {
//                                listaFolios.append(reader.get(0)).append("-").append(reader.get(1)).append(";");
//                            }
//
//                            if (reader.get(2) != null && reader.get(3) != null) {
//                                listaFolios.append(reader.get(2)).append("-").append(reader.get(3)).append(";");
//                            }
//
//                            if (reader.get(4) != null && reader.get(5) != null) {
//                                listaFolios.append(reader.get(4)).append("-").append(reader.get(5)).append(";");
//                            }
//                        }
//                        reader.close();
		} catch (Exception ex) {
                exception.addError("El archivo no existe.");
                if (exception.getErrores().size() > 0) { throw exception; }
            }
		return new EvnImprimirFolio(usuarioAuriga, listaFolios, nombreArchivo, AWImpresionFolio.AGREGAR_DE_ARCHIVO);
    }
        
        /**
         * @author     : Ellery David Robles Gómez.
         * @change     : Metodo que se encarga de leer el archivo csv con sus folios y cargarlos a un StringBuffer.
         * Caso_Mantis : 09422: Acta - Requerimiento No 040_151 - Impresión de Folios - Nivel Central.
         */
        private EvnImprimirFolio programarTarea(HttpServletRequest request) throws AccionWebException {
            HttpSession session = request.getSession();
            ImprimirFolioException exception = new ImprimirFolioException();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            String nombreTarea=null, fechaTarea=null;
            StringBuffer listaFolios2 = null;
            
//            try {
                if (request.getParameter("nombreTarea").isEmpty()) {
                    exception.addError("Debe digitar un número de matrícula.");
                } else {
                    nombreTarea = request.getParameter("nombreTarea");
                }
                if (request.getParameter("fechaTarea").isEmpty() || request.getParameter("hora").isEmpty()) {
                    exception.addError("Debe digitar la fecha de ejecución de la tarea.");
                } else {
                    fechaTarea = request.getParameter("fechaTarea")+" "+request.getParameter("hora");
                }
                listaFolios2 = (StringBuffer) session.getAttribute(AWImpresionFolio.LISTA_MATRICULAS);
//            } catch (Exception ex) {
//                exception.addError("El archivo no existe.");
//                if (exception.getErrores().size() > 0) { throw exception; }
//            }
            if (exception.getErrores().size() > 0) { throw exception; }
            
            return new EvnImprimirFolio(usuarioAuriga, nombreTarea, fechaTarea, listaFolios2, AWImpresionFolio.IMP_MASIVA_SIMPLE_FOLIO, EvnImprimirFolio.PROGRAMAR_TAREA);
        }
	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	   /**
     * Este método se encarga de manejar el evento de respuesta proveniente de
     * la acción de negocio. Sube datos a sesión de acuerdo al tipo de respuesta
     * proveniente en el evento
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        EvnRespImprimirFolio respuesta = (EvnRespImprimirFolio) evento;

        HttpSession session = request.getSession();
        context = session.getServletContext();

        if (respuesta != null) {
            String tipoEvento = respuesta.getTipoEvento();
            if (tipoEvento.equals(EvnRespImprimirFolio.IMPRESION_FOLIO_OK)) {
                session.setAttribute(AWImpresionFolio.IMPRESION_FOLIO_EJECUTADA, new Boolean(true));
                return;
            } else if (tipoEvento.equals(EvnRespImprimirFolio.DETERMINAR_ESTACION)) {
                String[] idTurnos = request.getParameterValues(AWImpresionFolio.LISTA_RECIBOS_IMPRIMIR);
                String turnos = "";
                if (idTurnos != null && idTurnos.length > 0) {
                    for (int i = 0; i < idTurnos.length; i++) {
                        turnos = turnos + idTurnos[i];
                        if (i < idTurnos.length - 1) {
                            turnos = turnos + ",";
                        }
                    }
                }
                if (turnos.length() > 0) {
                    String turno = (String) session.getAttribute(CTurno.ID_TURNO);
                    if (turno != null) {
                        session.setAttribute(CTurno.TURNO_ANTERIOR, turno);
                    }
                    session.setAttribute(CTurno.ID_TURNO, turnos);
                }
                session.setAttribute(AWImpresionFolio.LISTA_ESTACIONES, respuesta.getPayload());
                return;
            } else if (tipoEvento.equals(EvnRespImprimirFolio.IMPRESION_CERTIFICADO_OK)) {
                session.setAttribute(AWImpresionFolio.IMPRESION_CERTIFICADO_EJECUTADA, new Boolean(true));
                return;
            } else if (tipoEvento.equals(EvnRespImprimirFolio.REIMPRIMIR_CONSULTA_OK)) {
                return;
            } else if (tipoEvento.equals(EvnRespImprimirFolio.OBTENER_IMPRESORAS_CIRCULO)) {
                Circulo cir = respuesta.getCirculo();
                String idCirculo = cir.getIdCirculo();
                String key = WebKeys.LISTA_IMPRESORAS + "_" + idCirculo;
                session.setAttribute(key, respuesta.getPayload());
                session.setAttribute(WebKeys.RECARGA, new Boolean(false));
                session.setAttribute(AWImpresionFolio.LISTA_INFORMATIVAS_REIMPRESION, respuesta.getListaNotasReimpresion());
                return;
            } else if (tipoEvento.equals(EvnRespImprimirFolio.OBTENER_ULTIMO_TURNO_IMPRESO_OK)) {
                session.setAttribute(ULTIMO_TURNO_IMPRESO, respuesta.getPayload());
                session.setAttribute(WebKeys.RECARGA, new Boolean(false));
                return;
            } else if (tipoEvento.equals(EvnRespImprimirFolio.ULTIMA_SOLICITUD_LIQUIDACION)) {
                session.setAttribute(CSolicitud.NUMERO_SOLICITUD_LIQUIDACION, respuesta.getPayload());
            } else if (tipoEvento.equals(EvnRespImprimirFolio.ULTIMO_TURNO_PROCESO)) {
                session.setAttribute(CTurno.ID_TURNO, respuesta.getPayload());
                session.setAttribute(AWImpresionFolio.LISTA_TURNOS_ASOCIADOS_REIMPRESION, respuesta.getTurnosAsociados());
            } else if (tipoEvento.equals(EvnRespImprimirFolio.AGREGAR_DE_ARCHIVO)) {
                session.setAttribute(AWImpresionFolio.NOMBRE_ARCHIVO_CSV, respuesta.getNombreArchivo());
                session.setAttribute(AWImpresionFolio.LISTA_MATRICULAS, respuesta.getListaFolios());
            } else if (tipoEvento.equals(EvnRespImprimirFolio.PROGRAMAR_TAREA_OK)) {
                session.setAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO, respuesta.getExito());
                session.setAttribute(AWImpresionFolio.IMP_MASIVA_SIMPLE_FOLIO_FTP, respuesta.getImp_masiva_simple_folio_ftp());
                return;
            }
        }
    }
}
