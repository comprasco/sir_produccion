package gov.sir.core.web.acciones.registro;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.sir.core.eventos.registro.EvnConfrontacion;
import gov.sir.core.eventos.registro.EvnRespConfrontacion;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaConfrontacionException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionConfrontacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import static gov.sir.core.web.acciones.registro.AWCalificacion.NOTAS_INFORMATIVAS_INICIALES;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


/**
 * @author dsalas
 * @author dlopez
 */
public class AWConfrontacion extends SoporteAccionWeb {

	/**
	* Constante que indica que se quiere asociar una matricula
	*/
	public final static String ASOCIAR_UNA_MATRICULA = "ASOCIAR_UNA_MATRICULA";
	
	/**
	* Constante que indica que se quiere actualizar los datos de antiguo sistema
	*/
	public final static String ACTUALIZAR_DATOS_ANTIGUO_SISTEMA = "ACTUALIZAR_DATOS_ANTIGUO_SISTEMA";
	
	/**
	* Constante que indica que se quiere maximizar los datos de antiguos sistema
	*/
	public final static String OCULTAR_DATOS_ANTIGUO_SISTEMA = "OCULTAR_DATOS_ANTIGUO_SISTEMA";	

	/**
	* Constante que indica que se quiere asociar un rango de matriculas
	*/
	public final static String ASOCIAR_UN_RANGO = "ASOCIAR_UN_RANGO";

    /**
    * Constante que identifica que se utiliza para pasar el caso a la fase de reparto
    */
    public final static String CONFIRMAR = "CONFIRMAR";
    
    /**
     * Constante que identifica las notas necesarias para confrontación
     */
    public static final String  NOTAS_INFORMATIVAS_CNFR = "NOTAS_INFORMATIVAS_CNFR";
    
       /**
     * Constante que identifica las notas necesarias para confrontación
     */
    public static final String  NOTAS_INFORMATIVAS_CNFR_D = "NOTAS_INFORMATIVAS_CNFR_D";
    
       /**
     * Constante que identifica las notas necesarias para confrontación
     */
    public static final String  NOTAS_INFORMATIVAS_CNFR_CORR = "NOTAS_INFORMATIVAS_CNFR_CORR";
    
       /**
     * Constante que identifica las notas necesarias para confrontación
     */
    public static final String  NOTAS_INFORMATIVAS_CNFR_CORR_D = "NOTAS_INFORMATIVAS_CNFR_CORR_D";
    
	/**
	* Constante utilizada para indicar que se desea eliminar una matrícula.
	*/
	public final static String ELIMINAR = "ELIMINAR";
	
	/**
	* Constante para indicar que se desean o no ver los datos de radicacion.  
	*/
	public final static String VER_RADICACION = "VER_RADICACION";
	
	public static final String GURDAR_CAMBIOS_CONFRONTACION = "GURDAR_CAMBIOS_CONFRONTACION";
	
	public static final String CONFRONTACION = "CONFRONTACION";
        
        public static final String ADD_NOTE_INFO = "ADD_NOTE_INFO";
        
        public static final String DELETE_NOTE_INFO = "DELETE_NOTE_INFO";
   
   
    private String accion;
    /**
    * 
    * Constructor de AWConfrontacion.
    */
    public AWConfrontacion() {
        super();
    }
	
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (accion.equals(ASOCIAR_UNA_MATRICULA)) {
            return asociarUnaMatricula(request);
        } else if (accion.equals(ELIMINAR)) {
            return eliminarMatricula(request);
        } else if (accion.equals(CONFIRMAR)) {
            return confirmar(request);
        } else if (accion.equals(ASOCIAR_UN_RANGO)){
        	return asociarUnRango(request);
        } else if (accion.equals(VER_RADICACION)) {
			return verRadicacion(request); 
        }else if (accion.equals(ACTUALIZAR_DATOS_ANTIGUO_SISTEMA)) {
			return actualizarDatosAntiguoSistema(request); 
		}else if (accion.equals(OCULTAR_DATOS_ANTIGUO_SISTEMA)) {
			return ocultarDatosAntiguoSistema(request); 
		}else if (accion.equals(GURDAR_CAMBIOS_CONFRONTACION)){
			return guardarCambiosConfrontacion(request);
		}
        else {
            throw new AccionInvalidaException("La accion " + accion +
                " no es valida.");
        }
    }

    private Evento guardarCambiosConfrontacion(HttpServletRequest request) throws AccionWebException {
		// TODO Auto-generated method stub
    	String idSubtipoSolicitud=(String)request.getParameter(
    	CSolicitudRegistro.SUBTIPO_SOLICITUD);

    	Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
    	Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
    	Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
    	gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
    			
    	return new EvnConfrontacion(usuario,EvnConfrontacion.GURDAR_CAMBIOS_CONFRONTACION,turno,fase,idSubtipoSolicitud, usuarioSIR, 0);
	}

	/**
     * Puede asociar un rango de matriculas
     * Garantiza que las matriculas quedan asociadas
	 * @param request con ID_MATRICULA_RL y ID_MATRICULA_RR
	 * @return Un evento de tipo ASOCIAR_UN_RANGO
	 */
	private Evento asociarUnRango(HttpServletRequest request) 
	throws AccionWebException {
		ValidacionParametrosException exception = new ValidacionParametrosException();
		String idCirculo =((Circulo)request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
		/*
		int numInicial;
		int numFinal;
		try{
			numInicial = Integer.parseInt(request.getParameter(CFolio.ID_MATRICULA_RL));
		}
		catch(Exception e){
			throw new AccionWebException("Número de matrícula inválido: "+request.getParameter(CFolio.ID_MATRICULA_RL));
		}
		try{
			numFinal = Integer.parseInt(request.getParameter(CFolio.ID_MATRICULA_RR));
		}
		catch(Exception e){
			throw new AccionWebException("Número de matrícula inválido: "+request.getParameter(CFolio.ID_MATRICULA_RR));
		}*/
		

		String idMatRL = idCirculo+"-"+request.getParameter(CFolio.ID_MATRICULA_RL);
		String idMatRR = idCirculo+"-"+request.getParameter(CFolio.ID_MATRICULA_RR);
		
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		
		Usuario usuario = (Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		  /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged 
                */
                Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                
                
                int notasIniciales = 0;
                String addMatricula;
                
                String notasI = "";
                if(!fase.getID().equals(CFase.REG_CONFRONTAR)){
                    notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_CNFR_CORR));
                    addMatricula = CNota.AGREGAR_MATRICULAS_CORRECTIVA;
                } else{
                    notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_CNFR));
                    addMatricula = CNota.AGREGAR_MATRICULAS;
                }
                
                if(notasI != null && !notasI.isEmpty()){
                    notasIniciales = Integer.parseInt(notasI);
                }
                
                
                 int notasNecesarias = 0;
                 int hasNote = 0;
         
                List notasInformativas = turno.getNotas();
                if(notasInformativas != null && !notasInformativas.isEmpty()){
                    Iterator itNota = notasInformativas.iterator();
                    while(itNota.hasNext()){
                        Nota note = (Nota) itNota.next();
                        if(note != null && note.getTiponota().getIdTipoNota().equals(addMatricula)){
                            hasNote++;
                        }
                    }
                }
                
                 notasNecesarias = notasIniciales++;
                 String notasN = String.valueOf(request.getSession().getAttribute("AGREGAR_NOTA"));
                 int nN = Integer.parseInt((!notasN.equals("null")?notasN:"0"));
                 if(nN != 0){
                     notasNecesarias = nN;
                 } else{
                 notasNecesarias++;
                 request.getSession().setAttribute("AGREGAR_NOTA",notasNecesarias);
                 }
               
                 
                
                
                if(hasNote < notasNecesarias || hasNote == 0){
                    exception.addError("Debe agregar una nota informativa de tipo agregar matricula para continuar.");
                    request.getSession().setAttribute("AGREGAR_NOTA",notasNecesarias);
                }
                
                
                if(exception.getErrores().size() > 0){
                    throw exception;
                }
                
		EvnConfrontacion evento = new EvnConfrontacion(usuario,EvnConfrontacion.ASOCIAR_UN_RANGO,turno,idMatRL,idMatRR, usuarioSir);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuario.getUsuarioId(),String.valueOf(usuarioSir.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
		evento.setFase(fase);
                return evento;
	}

	/**
     * Puede asociar una matrícula
     * Garantiza que la matrícula que quede asociada, puede ser afectada por el documento bajo radicación
     * @param request La información del formulario
     * @return Un evento confrontación de tipo ASOCIAR_UNA_MATRICULA
     * @throws AccionWebException
     */
    private EvnConfrontacion asociarUnaMatricula(HttpServletRequest request)
    throws AccionWebException {
                
                ValidacionParametrosException exception = new ValidacionParametrosException();
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE); 
                int notasIniciales = 0;
                String addMatricula;
                
                String notasI = "";
                if(!fase.getID().equals(CFase.REG_CONFRONTAR)){
                    notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_CNFR_CORR));
                    addMatricula = CNota.AGREGAR_MATRICULAS_CORRECTIVA;
                } else{
                    notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_CNFR));
                    addMatricula = CNota.AGREGAR_MATRICULAS;
                }
                
                if(notasI != null && !notasI.isEmpty()){
                    notasIniciales = Integer.parseInt(notasI);
                }
                
                
                 int notasNecesarias = 0;
                 int hasNote = 0;
         
                List notasInformativas = turno.getNotas();
                if(notasInformativas != null && !notasInformativas.isEmpty()){
                    Iterator itNota = notasInformativas.iterator();
                    while(itNota.hasNext()){
                        Nota note = (Nota) itNota.next();
                        if(note != null && note.getTiponota().getIdTipoNota().equals(addMatricula)){
                            hasNote++;
                        }
                    }
                }
                
                 notasNecesarias = notasIniciales++;
                 String notasN = String.valueOf(request.getSession().getAttribute("AGREGAR_NOTA"));
                 int nN = Integer.parseInt((!notasN.equals("null")?notasN:"0"));
                 if(nN != 0){
                     notasNecesarias = nN;
                 } else{
                 notasNecesarias++;
                 request.getSession().setAttribute("AGREGAR_NOTA",notasNecesarias);
                 }
               
                 
                
                
                if(hasNote < notasNecesarias || hasNote == 0){
                    exception.addError("Debe agregar una nota informativa de tipo agregar matricula para continuar.");
                    request.getSession().setAttribute("AGREGAR_NOTA",notasNecesarias);
                }
                
                
                if(exception.getErrores().size() > 0){
                    throw exception;
                }

          
		String idCirculo =((Circulo)request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
                
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
                String matricula = idCirculo+"-"+idMatricula;
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                String error = "";
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                        error = error + "El folio que desea agregar se encuentra en estado 'Bloqueado'.";
                    }
                } catch (GeneralSIRException ex) {
                    if(ex.getMessage() != null){
                        if(!error.isEmpty()){
                            error = error + "<br />";
                        }
                        error = error + ex.getMessage();
                    }
                }
                if(!error.isEmpty()){
                    throw new AccionWebException(error);
                }
		Folio folio = new Folio();
		folio.setIdMatricula(matricula);
			
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged ;
                */
                
		EvnConfrontacion evento = new EvnConfrontacion(usuario,EvnConfrontacion.ASOCIAR_UNA_MATRICULA, turno,folio, usuarioSir);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSir.getUsername(),String.valueOf(usuarioSir.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
                evento.setFase(fase);
		return evento;
		
        
    }

    /**
    * Eliminar matrículas como un rango de matrículas
    * Garantiza que las matrículas que quedan asociadas son las que pueden
    * ser afectadas por el documento bajo radicación
    * @param request La información del formulario
    * @return Un evento confrontación de tipo ASOCIAR_RANGO_MATRICULAS
    * @throws AccionWebException
    */
    private EvnConfrontacion eliminarMatricula(HttpServletRequest request)
    throws AccionWebException {
        	
                ValidacionParametrosException exception = new ValidacionParametrosException();
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
                Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
                
                int notasIniciales = 0;
                String addMatricula;
                
                String notasI = "";
                if(!fase.getID().equals(CFase.REG_CONFRONTAR)){
                    notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_CNFR_CORR_D));
                    addMatricula = CNota.ELIMINAR_MATRICULAS_CORRECTIVA;
                } else{
                    notasI = String.valueOf(request.getSession().getAttribute(NOTAS_INFORMATIVAS_CNFR_D));
                    addMatricula = CNota.ELIMINAR_MATRICULAS;
                }
                
                if(notasI != null && !notasI.isEmpty()){
                    notasIniciales = Integer.parseInt(notasI);
                }
                
                
                 int notasNecesarias = 0;
                 int hasNote = 0;
         
                List notasInformativas = turno.getNotas();
                if(notasInformativas != null && !notasInformativas.isEmpty()){
                    Iterator itNota = notasInformativas.iterator();
                    while(itNota.hasNext()){
                        Nota note = (Nota) itNota.next();
                        if(note != null && note.getTiponota().getIdTipoNota().equals(addMatricula)){
                            hasNote++;
                        }
                    }
                }
                
                 notasNecesarias = notasIniciales++;
                 String notasN = String.valueOf(request.getSession().getAttribute("ELIMINAR_NOTA"));
                 int nN = Integer.parseInt((!notasN.equals("null")?notasN:"0"));
                 if(nN != 0){
                     notasNecesarias = nN;
                 } else{
                 notasNecesarias++;
                 request.getSession().setAttribute("ELIMINAR_NOTA",notasNecesarias);
                 }

                
                if(hasNote < notasNecesarias || hasNote == 0){
                    exception.addError("Debe agregar una nota informativa de tipo eliminar matricula para continuar.");
                    request.getSession().setAttribute("ELIMINAR_NOTA",notasNecesarias);
                }
                
                
                if(exception.getErrores().size() > 0){
                    throw exception;
                }

               
		List folios = new ArrayList();
		String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		if(matriculasImp !=null ){
			for (int i = 0; i < matriculasImp.length; i++) {
				Folio folio = new Folio();
				folio.setIdMatricula(matriculasImp[i]);
				folios.add(folio);
			}
		}
		if(folios.size()>0){
                            /**
                        * @Author Carlos Torres
                        * @Mantis 13176
                        * @Chaged ;
                        */
                        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                        EvnConfrontacion evento = new EvnConfrontacion(usuario, EvnConfrontacion.ELIMINAR_UNA_MATRICULA, turno, folios);
                        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSir.getUsername(),String.valueOf(usuarioSir.getIdUsuario()));
                        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                        evento.setInfoUsuario(infoUsuario);
                        evento.setFase(fase);
			return evento;
			
		}else{
			return null;
		}
    }

    /**
     * Confirma que todos los datos sean correctos para poder enviar el caso a reparto
     * Tambien permite marcar el caso con alguna de las siguientes opciones:
     * -Antiguo Sistema
     * -Reglamentos
     * -Copias
     * -Testamentos
     * -Estándar
     * @param request La información del formulario
     * @return Un evento confrontación de tipo CONFIRMAR
     * @throws AccionWebException
     */
    private Evento confirmar(HttpServletRequest request)
            throws AccionWebException{
        
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
       
        String idSubtipoSolicitud = (String) request.getParameter(
                CSolicitudRegistro.SUBTIPO_SOLICITUD);       
        
        Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        int tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;
        if (fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)) {
            tipoAvance = CAvanzarTurno.AVANZAR_POP;
        } else if (fase.getID().equals(CFase.REG_CONFRONTAR)) {
            tipoAvance = CAvanzarTurno.AVANZAR_NORMAL;
        }
        
        return new EvnConfrontacion(usuario, EvnConfrontacion.CONFIRMAR, turno, fase, idSubtipoSolicitud, usuarioSIR, tipoAvance);

    }
    
	
	/**
	 * Se utiliza para maximizar/minimizar los datos de radicación de la solicitud.
	 * En el parámetro VER_RADICACION de la solicitud viene la información para maximizar o minimizar la 
	 * información de antiguo sistema.  Este parámetro se pone en la sesión, para que cuando se recargue la página
	 * se lea este atributo de la sesión.
	 * @param request Tiene la información del formulario
	 * @return Un <CODE>EvnConfrontacion</CODE> nulo, ya que no se necesita generar un evento
	 */
	private EvnConfrontacion verRadicacion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String ver = request.getParameter(VER_RADICACION);
		session.setAttribute(VER_RADICACION, ver);
		return null;

	}    
	
	
	/**
	 * Se utiliza para modificar los datos de antiguo sistema en la fase de confrontación
	 * @param request Tiene la información del formulario
	 * @return Un <CODE>EvnConfrontacion</CODE>
	 */
	private EvnConfrontacion actualizarDatosAntiguoSistema(HttpServletRequest request)throws AccionWebException {
		HttpSession session = request.getSession();
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE); 
		gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		ValidacionParametrosException exception = new ValidacionParametrosException();
		EvnConfrontacion evento = null;
		
		DatosAntiguoSistema das = getDatosAntiguoSistema(request , exception);
		
		if(exception.getErrores().size()>0){
			throw exception;
		}
		
		if (exception.getErrores().size() == 0) {
			evento = new EvnConfrontacion(usuarioAuriga, EvnConfrontacion.ACTUALIZAR_DATOS_ANTIGUO_SISTEMA, turno, das, usuarioSir);
		}

		return evento; 		

	}    	
	
	/**
	 * Se utiliza para maximizar/minimizar los datos de antiguo sistema.
	 * @param request Tiene la información del formulario
	 * @return Un <CODE>EvnConfrontacion</CODE> nulo, ya que no se necesita generar un evento
	 */
	private EvnConfrontacion ocultarDatosAntiguoSistema(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String ocultarDatosAntiguoSistema = request.getParameter( WebKeys.OCULTAR_ANTIGUO_SISTEMA);
		
		if(ocultarDatosAntiguoSistema!=null && !ocultarDatosAntiguoSistema.equals("")){
			session.setAttribute(WebKeys.OCULTAR_ANTIGUO_SISTEMA , ocultarDatosAntiguoSistema);
		}		
		
		return null;

	}    	
	
	
	/**
	* @param request
	* @param exception
	* @return
	*/
	private DatosAntiguoSistema getDatosAntiguoSistema(HttpServletRequest request, ValidacionParametrosException exception) {

		DatosAntiguoSistema das = null;
		das = new DatosAntiguoSistema();

		//DATOS DE LIBRO Y DE TOMO.
		String libro_tipo_as = request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS);
		libro_tipo_as = (libro_tipo_as == null) ? "" : libro_tipo_as;

		String libro_numero_as = request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
		libro_numero_as = (libro_numero_as == null) ? "" : libro_numero_as;

		String libro_pagina_as = request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
		libro_pagina_as = (libro_pagina_as == null) ? "" : libro_pagina_as;

		String libro_ano_as = request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS);
		libro_ano_as = (libro_ano_as == null) ? "" : libro_ano_as;

		String tomo_numero_as = request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS);
		tomo_numero_as = (tomo_numero_as == null) ? "" : tomo_numero_as;

		String tomo_pagina_as = request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS);
		tomo_pagina_as = (tomo_pagina_as == null) ? "" : tomo_pagina_as;

		String tomo_municipio_as = request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
		tomo_municipio_as = (tomo_municipio_as == null) ? "" : tomo_municipio_as;

		String tomo_ano_as = request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS);
		tomo_ano_as = (tomo_ano_as == null) ? "" : tomo_ano_as;
		
		String comentario_as = request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS);
		comentario_as = (comentario_as == null) ? "" : comentario_as;		

		das.setLibroAnio(libro_ano_as);
		das.setLibroNumero(libro_numero_as);
		das.setLibroPagina(libro_pagina_as);
		das.setLibroTipo(libro_tipo_as);
		das.setTomoAnio(tomo_ano_as);
		das.setTomoMunicipio(tomo_municipio_as);
		das.setTomoNumero(tomo_numero_as);
		das.setTomoPagina(tomo_pagina_as);
		das.setComentario(comentario_as);

		
		//DATOS DEL DOCUMENTO
		String documento_tipo_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
		documento_tipo_as = (documento_tipo_as == null) ? "" : documento_tipo_as;

		String documento_numero_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
		documento_numero_as = (documento_numero_as == null) ? "" : documento_numero_as;

		String documento_comentario_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
		documento_comentario_as = (documento_comentario_as == null) ? "" : documento_comentario_as;

		String documento_fecha_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
		documento_fecha_as = (documento_fecha_as == null) ? "" : documento_fecha_as;
		
		
		//DATOS DE LA VEREDA Y OFICINA DEL DOCUMENTO
		String oficina_depto_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
		oficina_depto_id_as = (oficina_depto_id_as == null) ? "" : oficina_depto_id_as;

		String oficina_depto_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
		oficina_depto_nom_as = (oficina_depto_nom_as == null) ? "" : oficina_depto_nom_as;

		String oficina_muni_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
		oficina_muni_id_as = (oficina_muni_id_as == null) ? "" : oficina_muni_id_as;

		String oficina_muni_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
		oficina_muni_nom_as = (oficina_muni_nom_as == null) ? "" : oficina_muni_nom_as;

		String oficina_vereda_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
		oficina_vereda_id_as = (oficina_vereda_id_as == null) ? "" : oficina_vereda_id_as;

		String oficina_vereda_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
		oficina_vereda_nom_as = (oficina_vereda_nom_as == null) ? "" : oficina_vereda_nom_as;

		String oficina_oficia_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
		oficina_oficia_id_as = (oficina_oficia_id_as == null) ? "" : oficina_oficia_id_as;
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                String oficina_oficia_version_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
		oficina_oficia_version_as = (oficina_oficia_version_as == null) ? "0" : oficina_oficia_version_as;

		String oficina_oficia_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
		oficina_oficia_nom_as = (oficina_oficia_nom_as == null) ? "" : oficina_oficia_nom_as;

		//SE LLENAN LOS OBJETOS DEL DOCUMENTO SI SE HAN INGRESADO EN EL FORMULARIO
		StringBuffer sb = new StringBuffer("");
		StringBuffer sbDocAs = new StringBuffer("");
		StringBuffer sbOfiAs = new StringBuffer("");
		StringBuffer sbTipoDocAS = new StringBuffer("");
		Date docFecha = null;

		sb.append(libro_tipo_as);
		sb.append(libro_numero_as);
		sb.append(libro_pagina_as);
		sb.append(libro_ano_as);
		sb.append(tomo_numero_as);
		sb.append(tomo_pagina_as);
		sb.append(tomo_municipio_as);
		sb.append(tomo_ano_as);
		sb.append(documento_tipo_as);
		sb.append(documento_numero_as);
		sb.append(documento_comentario_as);
		sb.append(documento_fecha_as);
		sb.append(oficina_depto_id_as);
		sb.append(oficina_depto_nom_as);
		sb.append(oficina_muni_id_as);
		sb.append(oficina_muni_nom_as);
		sb.append(oficina_vereda_id_as);
		sb.append(oficina_vereda_nom_as);
		sb.append(oficina_oficia_id_as);
		sb.append(oficina_oficia_nom_as);

		if (documento_fecha_as.length() > 0) {
			try {
				docFecha = DateFormatUtil.parse(documento_fecha_as);
			} catch (ParseException e) {
				exception.addError("El campo Fecha del documento de " + "antiguo sistema no es válido");
			}
		}


		Documento docAS = new Documento();
		TipoDocumento docTipo = new TipoDocumento();
		OficinaOrigen oficinaOrigenAS = new OficinaOrigen();
		Vereda veredaAS = new Vereda();

		// veredaAS.setNombre(nomVereda);
		veredaAS.setIdVereda(oficina_vereda_id_as);
		veredaAS.setIdDepartamento(oficina_depto_id_as);
		veredaAS.setIdMunicipio(oficina_muni_id_as);

		oficinaOrigenAS.setIdOficinaOrigen(oficina_oficia_id_as);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficinaOrigenAS.setIdOficinaOrigen(oficina_oficia_version_as);
		oficinaOrigenAS.setNombre(oficina_oficia_nom_as);
		oficinaOrigenAS.setVereda(veredaAS);

		// docTipo.setNombre(documento_tipo_as);
		docTipo.setIdTipoDocumento(documento_tipo_as);
		docAS.setComentario(documento_comentario_as);
		docAS.setFecha(docFecha);
		docAS.setNumero(documento_numero_as);

		// docAS.setTipoDocumento(docTipo);
		sbDocAs.append(documento_comentario_as);
		sbDocAs.append(docFecha);
		sbDocAs.append(documento_numero_as);
		sbDocAs.append(documento_tipo_as);

		sbOfiAs.append(oficina_oficia_id_as);
		sbOfiAs.append(oficina_oficia_nom_as);

		sbTipoDocAS.append(documento_tipo_as);

		//
		if (sbDocAs.length() > 0) {
			if (sbTipoDocAS.length() > 0) {
				
				if (sbOfiAs.length() > 0) {
					docAS.setOficinaOrigen(oficinaOrigenAS);
				}
				
				docAS.setTipoDocumento(docTipo);				
				das.setDocumento(docAS);				
			}
		}

		return das;

	}	
	
	
	
	
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespConfrontacion respuesta = (EvnRespConfrontacion) evento;

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(EvnRespConfrontacion.TURNO)) {
				Turno turno=(Turno)respuesta.getPayload();
				request.getSession().setAttribute(WebKeys.TURNO,turno);
			}
		}
	}
	

}
