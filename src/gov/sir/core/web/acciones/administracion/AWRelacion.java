package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRelacion;
import gov.sir.core.eventos.administracion.EvnRespRelacion;
import gov.sir.core.eventos.comun.EvnRespTurno;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRelacion;
import gov.sir.core.negocio.modelo.constantes.CTipoRelacion;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CRepartoAbogados;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.administracion.AWReportes;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Iterator;

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

public class AWRelacion extends SoporteAccionWeb {

	/** Acciones */
	public final static String SELECCIONAR_PROCESO = "SELECCIONAR_PROCESO";
	public final static String SELECCIONAR_PROCESO_IMPRESION = "SELECCIONAR_PROCESO_IMPRESION";
	public final static String SELECCIONAR_FASE = "SELECCIONAR_FASE";
	public final static String SELECCIONAR_FASE_IMPRESION = "SELECCIONAR_FASE_IMPRESION";
	public final static String SELECCIONAR_RELACION = "SELECCIONAR_RELACION";
	public final static String INGRESAR_RELACION = "INGRESAR_RELACION";
	public final static String VER_DETALLE_RELACION = "VER_DETALLE_RELACION";
        public final static String VER_IMP ="VER_IMP";
        public final static String VER_IMP2 ="VER_IMP2";
	/**Verificar si una relacion fue consultada*/
	public final static String RELACION_CONSULTADA = "RELACION_CONSULTADA";
	public final static String CREAR_RELACION = "CREAR_RELACION";
	public final static String CREAR_RELACION_MESA = "CREAR_RELACION_MESA";
	public final static String CARGAR_DATOS = "CARGAR_DATOS";
	public final static String CARGAR_DATOS_IMPRESION = "CARGAR_DATOS_IMPRESION";
	public final static String IMPRIMIR = "IMPRIMIR";
	public final static String IMPRIMIR_SIN_NOTA = "IMPRIMIR_SIN_NOTA";
        public final static String IMPRIMIR_REPARTO = "IMPRIMIR_REPARTO";
	public final static String TERMINA = "TERMINA";

	/** Parámetros */
	public final static String ID_PROCESO = "ID_PROCESO";
	public final static String ID_FASE = "ID_FASE";
	public final static String ID_TIPO_RELACION = "ID_TIPO_RELACION";	
	public final static String ID_RELACION = "ID_RELACION";
	/**Se dice que el avance de turnos es automático cuando a partir del número de relación se avanzan los 
	 * turnos y no los que el usuario ha seleccionado.*/
	public final static String AVANCE_TURNOS_AUTOMATICAMENTE = "AVANCE_TURNOS_AUTOMATICAMENTE";	
	public final static String DESCRIPCION_NOTA = "DESCRIPCION_NOTA";
	public final static String RELACION = "RELACION";
	public final static String LISTA_ID_TURNOS = "LISTA_ID_TURNOS";
	public final static String LISTA_TURNOS_RELACION = "LISTA_TURNOS_RELACION";
	public final static String LISTA_PROCESOS_RELACION = "LISTA_PROCESOS_RELACION";
	public final static String LISTA_FASES_RELACION = "LISTA_FASES_RELACION";
	public final static String LISTA_RELACIONES = "LISTA_RELACIONES";
	public final static String DIGITAR_ID_RELACION = "DIGITAR_ID_RELACION";
	public final static String MOSTRAR_DETALLE_RELACION = "MOSTRAR_DETALLE_RELACION";
	public final static String MOSTRAR_VENCIMIENTO_MAYOR_VALOR = "MOSTRAR_VENCIMIENTO_MAYOR_VALOR";
	public final static String MOSTRAR_RESPUESTA_RELACION = "MOSTRAR_RESPUESTA_RELACION";
	public final static String RESPUESTA_RELACION = "RESPUESTA_RELACION";
	public final static String MOSTRAR_NUMERO_DOCUMENTO = "MOSTRAR_NUMERO_DOCUMENTO";
	public final static String ERRORES_RELACION = "ERRORES_RELACION";
	public final static String TURNOS_AVANZADOS_RELACION = "TURNOS_AVANZADOS_RELACION";
	public final static String BOTON_IMPRESION_RELACION = "BOTON_IMPRESION_RELACION";

	public final static String REPORTES_JASPER_SERVLET="REPORTES_JASPER_SERVLET";
	
	/** Reportes */
	private final static String REPORTE_XX_PARAM_CMDKEY = "cmdkey";
	private final static String REPORTE_XX_PARAM_PRELACIONID = "P_RELACION";
	private final static String REPORTE_XX_PARAM_PPROCESO = "P_PROCESO";
	private final static String REPORTE_XX_PARAM_PFASE = "P_FASE";
	private final static String REPORTE_XX_PARAM_PCIRCULO = "P_CIRCULO";

	private String reportesServletPath;

	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();

		reportesServletPath = (String) request.getSession().getServletContext().getAttribute(WebKeys.REPORTES_SERVLET_URL);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWRelacion.SELECCIONAR_PROCESO)) {
			return seleccionarProceso(request);
		} else if (accion.equals(AWRelacion.SELECCIONAR_FASE)) {
			return seleccionarFase(request);
		} else if (accion.equals(AWRelacion.SELECCIONAR_RELACION)) {
			return seleccionarRelacion(request);
		} else if (accion.equals(AWRelacion.CREAR_RELACION)) {
			return crearRelacion(request);
		} else if (accion.equals(AWRelacion.TERMINA)) {
			return terminar(request);
		} else if (accion.equals(AWRelacion.CARGAR_DATOS)) {
			return cargarDatos(request);
		} else if (accion.equals(AWRelacion.IMPRIMIR)) {
			return imprimir(request);
		} else if (accion.equals(AWRelacion.IMPRIMIR_SIN_NOTA)) {
			return imprimirSinNota(request);
                } else if (accion.equals(AWRelacion.IMPRIMIR_REPARTO)) {
                        return imprimirReparto(request);
		} else if (accion.equals(AWRelacion.SELECCIONAR_PROCESO_IMPRESION)) {
			return seleccionarProceso(request);
		} else if (accion.equals(AWRelacion.SELECCIONAR_FASE_IMPRESION)) {
			return seleccionarFase(request);
		} else if (accion.equals(AWRelacion.CARGAR_DATOS_IMPRESION)) {
			return cargarDatos(request);
		} else if (accion.equals(AWRelacion.INGRESAR_RELACION)) {
			return ingresarRelacion(request);
		}else if (accion.equals(AWRelacion.VER_DETALLE_RELACION)) {
			return ingresarRelacion(request);
		}

		return null;
	}

	private Evento ingresarRelacion(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		if(request.getParameter(AWRelacion.RELACION_CONSULTADA)!=null){
			boolean relacionConsultada = Boolean.valueOf(request.getParameter(AWRelacion.RELACION_CONSULTADA)).booleanValue();
			session.setAttribute(AWRelacion.RELACION_CONSULTADA, Boolean.toString(relacionConsultada));
		}
		String idProceso = request.getParameter(AWRelacion.ID_PROCESO);
		if ((idProceso != null) && (idProceso.trim().length() != 0)) {
			session.setAttribute(AWRelacion.ID_PROCESO, idProceso);
		}

		String idFase = request.getParameter(AWRelacion.ID_FASE);
		if ((idFase != null) && (idFase.trim().length() != 0)) {
			session.setAttribute(AWRelacion.ID_FASE, idFase);
		}
		
		String idTipoRelacion = request.getParameter(AWRelacion.ID_TIPO_RELACION);
		if ((idTipoRelacion != null) && (idTipoRelacion.trim().length() != 0)) {
			session.setAttribute(AWRelacion.ID_TIPO_RELACION, idTipoRelacion);
		}

		String idRelacion = request.getParameter(AWRelacion.ID_RELACION);
		session.removeAttribute(AWRelacion.ID_RELACION);
		if ((idRelacion == null) ||
				(idRelacion.trim().length() == 0) ||
				idRelacion.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Ingresar un identificador de relación.");
		}

		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnRelacion evento = null;
		if(request.getParameter(WebKeys.ACCION).trim().equals(AWRelacion.VER_DETALLE_RELACION)){
			evento = new EvnRelacion(usuario, EvnRelacion.VER_DETALLE_RELACION); 
		}else{
			evento = new EvnRelacion(usuario, EvnRelacion.INGRESAR_RELACION);
			session.removeAttribute(AWRelacion.LISTA_TURNOS_RELACION);
		}
		 

		evento.setIdProceso(Long.parseLong(idProceso));
		evento.setIdFase(idFase);
		evento.setIdTipoRelacion(idTipoRelacion);
		evento.setIdRelacion(idRelacion);
		evento.setCirculo(circulo);

		session.setAttribute(AWRelacion.ID_RELACION, idRelacion);
		session.removeAttribute(AWRelacion.DIGITAR_ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
		session.removeAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
		

		return evento;
	}

	private Evento imprimirSinNota(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionParametrosException exception = new ValidacionParametrosException();

		String idRelacion = request.getParameter(AWRelacion.ID_RELACION);
		if(idRelacion == null || idRelacion.trim().equals("")) {
			exception.addError("Debe ingresar el identificador de la relación que desea imprimir");
		}

		String idProceso = request.getParameter(AWRelacion.ID_PROCESO);
		if(idProceso == null || idProceso.trim().equals("")) {
			exception.addError("Debe seleccionar un proceso");
		}

		String idFase = request.getParameter(AWRelacion.ID_FASE);
		if(idFase == null || idFase.trim().equals("")) {
			exception.addError("Debe seleccionar una fase");
		}

		String nota = request.getParameter(AWRelacion.DESCRIPCION_NOTA);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario)session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnRelacion respuesta = new EvnRelacion(usuario, EvnRelacion.IMPRIMIR);
		respuesta.setIdRelacion(idRelacion);
		respuesta.setIdFase(idFase);
		respuesta.setIdProceso(Long.parseLong(idProceso));
		respuesta.setNota(nota);

		return respuesta;
	}


        private Evento imprimirReparto(HttpServletRequest request) throws AccionWebException {

                HttpSession session = request.getSession();

                ValidacionParametrosException exception = new ValidacionParametrosException();

                String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
                if(idCirculo == null || idCirculo.trim().equals("")) {
                        exception.addError("Debe ingresar el Circulo");
                }

                String idReparto = request.getParameter(CRepartoAbogados.ID_REPARTO);
                if(idReparto == null || idReparto.trim().equals("")) {
                        exception.addError("Debe seleccionar un número de reparto válido");
                }


                if (exception.getErrores().size() > 0) {
                        throw exception;
                }


                String[] params = new String[]{
                      AWReportes.REPORTE_XX__PARAM_CMDKEY,
                     AWReportes.REPORTE_155__PARAM_PROCESOREPARTO,
                    AWReportes.REPORTE_155__PARAM_CIRCULONOMBRE
                };

                String[] values = new String[] {
                     AWReportes.REPORTE_155__PARAM_CMDKEY,
                     idReparto,
                     idCirculo
                };

                String url = makeUrl(params, values);
                request.setAttribute(AWReportes.REPORTSSERVICES_REPORTURI, url);
		request.setAttribute(AWReportes.REPORTSSERVICES_REPORTDISPLAYENABLED, Boolean.TRUE );
              return null;
       }





	private Evento imprimir(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionParametrosException exception = new ValidacionParametrosException();

		String idRelacion = (String)session.getAttribute(AWRelacion.ID_RELACION);
		if(idRelacion == null || idRelacion.trim().equals("")) {
			exception.addError("Debe ingresar el identificador de la relación que desea imprimir");
		}

		String idProceso = request.getParameter(AWRelacion.ID_PROCESO);
		if(idProceso == null || idProceso.trim().equals("")) {
			exception.addError("Debe seleccionar un proceso");
		}

		String idFase = request.getParameter(AWRelacion.ID_FASE);
		if(idFase == null || idFase.trim().equals("")) {
			exception.addError("Debe seleccionar una fase");
		}

		String nota = request.getParameter(AWRelacion.DESCRIPCION_NOTA);
		if(nota == null || nota.equals("")) {
			nota = "";
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario)session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		terminar(request);

		EvnRelacion respuesta = new EvnRelacion(usuario, EvnRelacion.IMPRIMIR);
		respuesta.setIdRelacion(idRelacion);
		respuesta.setIdFase(idFase);
		respuesta.setIdProceso(Long.parseLong(idProceso));
		respuesta.setNota(nota);

		return respuesta;
	}

	private Evento cargarDatos(HttpServletRequest request) {

		HttpSession session = request.getSession();
                Circulo cir = (Circulo) session.getAttribute(WebKeys.CIRCULO); 
		Usuario usuario = (Usuario)session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
                EvnRelacion Evento= new EvnRelacion(usuario, EvnRelacion.CARGAR_DATOS);
                Evento.setCirculo(cir);
		return Evento;
	}

	private Evento terminar(HttpServletRequest request) {

		HttpSession session = request.getSession();

		session.removeAttribute(AWRelacion.ID_PROCESO);
		session.removeAttribute(AWRelacion.ID_FASE);
		session.removeAttribute(AWRelacion.ID_TIPO_RELACION);
		session.removeAttribute(AWRelacion.ID_RELACION);
		session.removeAttribute(AWRelacion.LISTA_TURNOS_RELACION);
		session.removeAttribute(AWRelacion.LISTA_ID_TURNOS);
		session.removeAttribute(AWRelacion.LISTA_PROCESOS_RELACION);
                session.removeAttribute(AWRelacion.VER_IMP);
                session.removeAttribute(AWRelacion.VER_IMP2);
		session.removeAttribute(AWRelacion.LISTA_FASES_RELACION);
		session.removeAttribute(AWRelacion.LISTA_RELACIONES);
		session.removeAttribute(AWRelacion.DIGITAR_ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
		session.removeAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);

		return null;
	}

	private Evento crearRelacion(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();

        ValidacionParametrosException exception = new ValidacionParametrosException();

        String idProceso = (String)session.getAttribute(AWRelacion.ID_PROCESO);
		String idFase = (String)session.getAttribute(AWRelacion.ID_FASE);
		String idTipoRelacion = (String)session.getAttribute(AWRelacion.ID_TIPO_RELACION);
		String avanzarTurnosAutomaticamente = (String)session.getAttribute(AWRelacion.AVANCE_TURNOS_AUTOMATICAMENTE);
		
		if(idProceso==null || idProceso.equals("")){
			idProceso = request.getParameter(AWRelacion.ID_PROCESO);
			session.setAttribute(AWRelacion.ID_PROCESO,idProceso);
		}
		if(idFase==null || idFase.equals("")){
			idFase = request.getParameter(AWRelacion.ID_FASE);
			session.setAttribute(AWRelacion.ID_FASE,idFase);
		}
		if(idTipoRelacion==null || idTipoRelacion.equals("")){
			idTipoRelacion = request.getParameter(AWRelacion.ID_TIPO_RELACION);
		}
		if(avanzarTurnosAutomaticamente==null || avanzarTurnosAutomaticamente.equals("")){
			avanzarTurnosAutomaticamente = request.getParameter(AWRelacion.AVANCE_TURNOS_AUTOMATICAMENTE);
			session.setAttribute(AWRelacion.AVANCE_TURNOS_AUTOMATICAMENTE,avanzarTurnosAutomaticamente);
		}
        
        String[] idTurnos = request.getParameterValues(AWRelacion.LISTA_ID_TURNOS);
        
        String notificarNotaRelacion = (String) request.getSession().getAttribute("NOTIFICAR_NOTA_RELACION");
        if(notificarNotaRelacion!=null){
            
            List turnosError = new ArrayList();
            List notasInf = null;
            boolean hasNote = false;
        
            try{
                HermodService hm = HermodService.getInstance();
                for (String idTurno : idTurnos) {
                    Turno turnoConNota = new Turno();
                    turnoConNota = hm.getTurnobyWF(idTurno);
                    if(turnoConNota != null){
                        notasInf = turnoConNota.getNotas();
                        if(notasInf == null || notasInf.isEmpty()){
                            turnosError.add(turnoConNota.getIdWorkflow());
                        } else{
                            Iterator itNot = notasInf.iterator();
                            while(itNot.hasNext()){
                                Nota notaInf = (Nota) itNot.next();
                                if (notaInf.getTiponota().getIdTipoNota().equals(CNota.NOTIFICAR_NOTA_DEVOLUTIVA)){
                                    hasNote = true;
                                }

                            }
                            if(!hasNote){
                                    turnosError.add(turnoConNota.getIdWorkflow());
                            }
                        }
                    }
                }
                
           } catch(HermodException he){
               System.out.println("NO HA SIDO POSIBLE OBTENER LOS TURNOS PARA VERIFICAR SU NOTA INFORMATIVA");
           }
            
            
            if(!turnosError.isEmpty()){
                String errorMsg = "No puede crearse la relación porque los turnos ";
                Iterator itTerror = turnosError.iterator();
                while(itTerror.hasNext()){
                    String tError = (String) itTerror.next();
                    if(!tError.isEmpty()){
                        errorMsg += tError + ", ";
                    }
                }

                errorMsg += " no cuentan con nota informativa de tipo NOTIFICAR NOTA DEVOLUTIVA ";

                exception.addError(errorMsg);
            }
            
        }
        
        
        
        
        try{
        HermodService hs = HermodService.getInstance();
        int RELTurnos = 0;
        int SIRTurnos = 0;
        if(idTurnos != null){
        for(String idWorkFlow : idTurnos){
            boolean isTurnoREL = hs.isTurnoREL(idWorkFlow);
           if(isTurnoREL){
               RELTurnos++;
           } else{
               SIRTurnos++;
           }
        }
        
        if(idFase != null  && idFase.equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
            if(RELTurnos > 0){
              exception.addError("Error al avanzar la relación: La relación es de origen REL");
            }
        }
        
        if(SIRTurnos > 0 && RELTurnos > 0){
            exception.addError("No pueden crearse relaciones con turnos de distinto origen");
        }
        }
        } catch (HermodException he){
            System.out.println("NO HA SIDO POSIBLE VALIDAR EL ID DE LOS TURNOS");
        }
        if(avanzarTurnosAutomaticamente==null || !avanzarTurnosAutomaticamente.equals("true")){
	        if(idTurnos == null || idTurnos.length == 0) {
	            exception.addError("Debe seleccionar al menos un turno para crear la relación");
	        }
        }

        String idRelacion = null;
        if(idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS) ||
        		idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_DEVUELTOS) ||
        		idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_DESANOTADOS_PARA_ENTREGA)||
                         /**
                           * @author      :   Carlos Torres
                           * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
                           **/
                        idTipoRelacion.equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_REGISTRO)||
                        idTipoRelacion.equals(CTipoRelacion.ID_RELACION_DE_TURNOS_DESANOTADOS_CORRESPONDIENCIA)||
        		idTipoRelacion.equals(CTipoRelacion.ID_DOCUMENTOS_A_DESANOTACION_INSCRITOS_PARCIALMENTE)) {
        	idRelacion = request.getParameter(AWRelacion.ID_RELACION);
        	if(idRelacion == null || idRelacion.trim().equals("")) {
        		exception.addError("Debe ingresar el identificador de la relación");
        	}
        	idRelacion = idRelacion.toUpperCase();
        }

        Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		Rol rolPCC = (Rol)session.getAttribute(WebKeys.ROL);
		boolean  validarPrimaCorrecionCalificacion = false; 
		if(rolPCC != null && rolPCC.getDescripcion() != null && rolPCC.getDescripcion().equals("SIR_ROL_MESA_CONTROL") &&
			idProceso != null && idProceso.equals("6") &&  idFase != null && idFase.equals("REG_MESA_CONTROL") && idTipoRelacion != null && idTipoRelacion.equals("4")){
			validarPrimaCorrecionCalificacion = true;
		}
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuSir = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute(WebKeys.USUARIO);
        EvnRelacion evento = new EvnRelacion(usuario, EvnRelacion.CREAR_RELACION);
        evento.setTurnosRelacion(idTurnos);
        evento.setValidarPrimaCorrecionCalificacion(validarPrimaCorrecionCalificacion);
        evento.setCirculo(circulo);
        evento.setIdFase(idFase);
        evento.setIdTipoRelacion(idTipoRelacion);
        evento.setIdProceso(Long.parseLong(idProceso));
        String impresora = request.getParameter(WebKeys.IMPRESORA);
        if (impresora != null && !impresora.equals(WebKeys.SIN_SELECCIONAR)) {
            evento.setImpresoraSeleccionada(impresora);
        }else{
            evento.setImpresoraSeleccionada("Microsoft Print to PDF");
        }
        evento.setIdRelacion(idRelacion);        
        evento.setUsuarioSIR(usuSir);
        //TFS 3596: Para este tipo de relación no se deben avanzar automaticamente los turnos
        if(idTipoRelacion.equals(CTipoRelacion.ID_SOLICITUDES_DE_CORRECCIONES))
        	avanzarTurnosAutomaticamente = null;
        if(avanzarTurnosAutomaticamente!=null&&avanzarTurnosAutomaticamente.equals(new Boolean(true).toString())){
        	evento.setAvanceTurnosAutomatico(true);
        	Rol rol = (Rol)session.getAttribute(WebKeys.ROL);
        	Estacion estacion = (Estacion)session.getAttribute(WebKeys.ESTACION);
        	evento.setRol(rol);
        	evento.setEstacion(estacion);
        }else{
        	evento.setAvanceTurnosAutomatico(false);
        }

        return evento;
    }

	private Evento seleccionarRelacion(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionParametrosException exception = new ValidacionParametrosException();

		String idProceso = request.getParameter(AWRelacion.ID_PROCESO);
		if ((idProceso != null) && (idProceso.trim().length() != 0)) {
			session.setAttribute(AWRelacion.ID_PROCESO, idProceso);
		}

		String idFase = request.getParameter(AWRelacion.ID_FASE);
		if ((idFase != null) && (idFase.trim().length() != 0)) {
			session.setAttribute(AWRelacion.ID_FASE, idFase);
		}

		String idTipoRelacion = request.getParameter(AWRelacion.ID_TIPO_RELACION);
		if ((idTipoRelacion == null)
			|| (idTipoRelacion.trim().length() == 0)
			|| idTipoRelacion.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar una relación.");
		}

		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnRelacion evento = new EvnRelacion(usuario, EvnRelacion.SELECCIONAR_RELACION);

		evento.setIdProceso(Long.parseLong(idProceso));
		evento.setIdFase(idFase);
		evento.setIdTipoRelacion(idTipoRelacion);
		evento.setCirculo(circulo);

		session.setAttribute(AWRelacion.ID_TIPO_RELACION, idTipoRelacion);
		session.removeAttribute(AWRelacion.DIGITAR_ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
		session.removeAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.RESPUESTA_RELACION);
                session.removeAttribute(AWRelacion.VER_IMP);
                session.removeAttribute(AWRelacion.VER_IMP2);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
		session.removeAttribute(AWRelacion.LISTA_TURNOS_RELACION);
		session.removeAttribute(AWRelacion.ID_RELACION);

		return evento;
	}

	private Evento seleccionarFase(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionParametrosException exception = new ValidacionParametrosException();

		String idProceso = request.getParameter(AWRelacion.ID_PROCESO);
		if ((idProceso != null) && (idProceso.trim().length() != 0)) {
			session.setAttribute(AWRelacion.ID_PROCESO, idProceso);
		}

		String idFase = request.getParameter(AWRelacion.ID_FASE);
		if ((idFase == null)
			|| (idFase.trim().length() == 0)
			|| idFase.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar una fase.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnRelacion evento = new EvnRelacion(usuario, EvnRelacion.SELECCIONAR_FASE);

		evento.setIdProceso(Long.parseLong(idProceso));
		evento.setIdFase(idFase);

		session.setAttribute(AWRelacion.ID_FASE, idFase);
		session.removeAttribute(AWRelacion.LISTA_RELACIONES);
		session.removeAttribute(AWRelacion.ID_TIPO_RELACION);
		session.removeAttribute(AWRelacion.ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
		session.removeAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.RESPUESTA_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
                session.removeAttribute(AWRelacion.VER_IMP);
                session.removeAttribute(AWRelacion.VER_IMP2);
		session.removeAttribute(AWRelacion.LISTA_TURNOS_RELACION);
		session.removeAttribute(AWRelacion.DIGITAR_ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);

		return evento;
	}

	private Evento seleccionarProceso(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		ValidacionParametrosException exception = new ValidacionParametrosException();

		String idProceso = request.getParameter(AWRelacion.ID_PROCESO);
		if ((idProceso == null)
			|| (idProceso.trim().length() == 0)
			|| idProceso.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un proceso.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnRelacion evento = new EvnRelacion(usuario, EvnRelacion.SELECCIONAR_PROCESO);

		evento.setIdProceso(Long.parseLong(idProceso));

		session.setAttribute(AWRelacion.ID_PROCESO, idProceso);
		session.removeAttribute(AWRelacion.ID_FASE);
		session.removeAttribute(AWRelacion.ID_RELACION);
		session.removeAttribute(AWRelacion.LISTA_FASES_RELACION);
		session.removeAttribute(AWRelacion.LISTA_RELACIONES);
		session.removeAttribute(AWRelacion.ID_TIPO_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
		session.removeAttribute(AWRelacion.LISTA_TURNOS_RELACION);
		session.removeAttribute(AWRelacion.DIGITAR_ID_RELACION);
		session.removeAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO);
		session.removeAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION);
                session.removeAttribute(AWRelacion.VER_IMP);
                session.removeAttribute(AWRelacion.VER_IMP2);
		session.removeAttribute(AWRelacion.RESPUESTA_RELACION);

		return evento;
	}

	public void reporte(HttpServletRequest request, EvnRespRelacion respuesta) {

		Relacion relacion = (Relacion)respuesta.getPayload();
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO); 
		String param_00 = respuesta.getIdReporte();
		String param_01 = respuesta.getIdRelacion();
		String param_02 = "" + respuesta.getIdProceso();
		String param_03 = respuesta.getIdFase();
		String param_04 = circulo.getIdCirculo();
		
		
		String[] params = null;
		String[] values = null;

		if(relacion !=null && 
		   relacion.getTipoRelacion()!=null && 
		   relacion.getTipoRelacion().getIdTipoRelacion()!=null &&
		   relacion.getTipoRelacion().getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_INSCRITOS_PARA_FIRMA)){
			
			params = new String[] {
					REPORTE_XX_PARAM_CMDKEY,
					REPORTE_XX_PARAM_PRELACIONID,
					REPORTE_XX_PARAM_PPROCESO,
					REPORTE_XX_PARAM_PFASE,
					REPORTE_XX_PARAM_PCIRCULO
			};
			
			values = new String[] {
					param_00,
					param_01,
					param_02,
			        param_03,
			        param_04
			};			
		}else{
			params = new String[] {
					REPORTE_XX_PARAM_CMDKEY,
					REPORTE_XX_PARAM_PRELACIONID,
					REPORTE_XX_PARAM_PPROCESO,
					REPORTE_XX_PARAM_PFASE
			};

			values = new String[] {
					param_00,
					param_01,
					param_02,
			        param_03
			};
		}

		String url = makeUrl(params, values);

		request.setAttribute(AWReportes.REPORTSSERVICES_REPORTURI, url);
		request.setAttribute(AWReportes.REPORTSSERVICES_REPORTDISPLAYENABLED, Boolean.TRUE );
	}

	private String makeUrl(String[] params, String[] values) {

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

	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespRelacion respuesta = (EvnRespRelacion) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespRelacion.SELECCIONAR_PROCESO_OK)) {
				List elementos = (List) respuesta.getPayload();
				session.setAttribute(AWRelacion.LISTA_FASES_RELACION, elementos);
			} else if (tipoEvento.equals(EvnRespRelacion.SELECCIONAR_FASE_OK)) {
				List elementos = (List) respuesta.getPayload();
				session.setAttribute(AWRelacion.LISTA_RELACIONES, elementos);
			} else if (tipoEvento.equals(EvnRespRelacion.CARGAR_DATOS_OK)) {
				List elementos = (List) respuesta.getPayload();
				session.setAttribute(AWRelacion.LISTA_PROCESOS_RELACION, elementos);
				session.setAttribute(AWRelacion.LISTA_FASES_RELACION, respuesta.getFases());
				session.setAttribute(AWRelacion.LISTA_RELACIONES, respuesta.getRelacionesFase());
				session.setAttribute(AWRelacion.ID_FASE, CFase.REG_MESA_CONTROL);
				session.setAttribute(AWRelacion.ID_PROCESO, CProceso.PROCESO_REGISTRO);
                                Circulo cir = respuesta.getCirculo();
                                String idCirculo = cir.getIdCirculo();
                                String key = WebKeys.LISTA_IMPRESORAS + "_" + idCirculo;
                                session.setAttribute(key, respuesta.getImpresoras());
			} else if (tipoEvento.equals(EvnRespRelacion.SELECCIONAR_RELACION_OK)) {

				if(!((EvnRespRelacion)evento).isMostrarIdRelacion()) {
					List elementos = (List) respuesta.getPayload();
					session.setAttribute(AWRelacion.LISTA_TURNOS_RELACION, elementos);
				}
				session.setAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION,
						new Boolean(((EvnRespRelacion)evento).isMostrarDetalle()));
				session.setAttribute(AWRelacion.DIGITAR_ID_RELACION,
						new Boolean(((EvnRespRelacion)evento).isMostrarIdRelacion()));
                                session.setAttribute(AWRelacion.VER_IMP,
						new Boolean(((EvnRespRelacion)evento).isMostrarverimp()));
                                session.setAttribute(AWRelacion.VER_IMP2,
						new Boolean(((EvnRespRelacion)evento).isMostrarverimp2()));
				session.setAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO,
						new Boolean(((EvnRespRelacion)evento).isMostrarNumeroDocumento()));
				session.setAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR,
						new Boolean(((EvnRespRelacion)evento).isMostrarVencimientoMayorValor()));
			} else if (tipoEvento.equals(EvnRespTurno.CONSULTAR_RELACION)) {
				session.setAttribute(WebKeys.LISTA_TURNOS_RELACION_DETALLE, respuesta.getPayload());
				session.setAttribute(WebKeys.LISTA_TURNOS_VALIDOS, respuesta.getTurnosCertificadosValidos());
				session.setAttribute(CRelacion.RELACION_FIRMA, respuesta.getRelacion());
				request.getSession().setAttribute("CARGAR_MESA_CONTROL", new Boolean(false));				
			}else if (tipoEvento.equals(EvnRespRelacion.CREAR_RELACION_OK)) {
				Relacion relacion = (Relacion) respuesta.getPayload();
				session.setAttribute(AWRelacion.RELACION, relacion);
				Hashtable errores = new Hashtable();
				errores = ((EvnRespRelacion)evento).getRespException();
				session.setAttribute(AWRelacion.ERRORES_RELACION,errores);
				Hashtable turnosAvanzadosOK  = new Hashtable();
				turnosAvanzadosOK = ((EvnRespRelacion)evento).getRespTurnosOk();
				session.setAttribute(AWRelacion.TURNOS_AVANZADOS_RELACION,turnosAvanzadosOK);
				session.setAttribute(AWRelacion.BOTON_IMPRESION_RELACION,new Boolean(((EvnRespRelacion)evento).isMostrarBotonImprimir()));
				
				if(((EvnRespRelacion)evento).getTurnosMenu()!=null){
					session.setAttribute(WebKeys.LISTA_TURNOS,((EvnRespRelacion)evento).getTurnosMenu());
				}
				
			} else if (tipoEvento.equals(EvnRespRelacion.IMPRIMIR_OK)) {
				reporte(request, (EvnRespRelacion)respuesta);
			} else if (tipoEvento.equals(EvnRespRelacion.INGRESAR_RELACION_OK)) {
				List elementos = (List) respuesta.getPayload();
				if(session.getAttribute(RELACION_CONSULTADA)!=null){
					if(Boolean.valueOf((String)session.getAttribute(RELACION_CONSULTADA)).booleanValue()){
						session.setAttribute(AWRelacion.LISTA_TURNOS_RELACION, elementos);	
					}else{
						session.setAttribute(AWRelacion.LISTA_TURNOS_RELACION, new ArrayList());
					}
					session.removeAttribute(RELACION_CONSULTADA);
				}else{
					session.setAttribute(AWRelacion.LISTA_TURNOS_RELACION, elementos);
				}
				
				session.setAttribute(AWRelacion.MOSTRAR_DETALLE_RELACION,
						new Boolean(((EvnRespRelacion)evento).isMostrarDetalle()));
				session.setAttribute(AWRelacion.DIGITAR_ID_RELACION,
						new Boolean(((EvnRespRelacion)evento).isMostrarIdRelacion()));
				session.setAttribute(AWRelacion.MOSTRAR_NUMERO_DOCUMENTO,
						new Boolean(((EvnRespRelacion)evento).isMostrarNumeroDocumento()));
                                session.setAttribute(AWRelacion.VER_IMP,
						new Boolean(((EvnRespRelacion)evento).isMostrarverimp()));
                                session.setAttribute(AWRelacion.VER_IMP2,
						new Boolean(((EvnRespRelacion)evento).isMostrarverimp2()));
				session.setAttribute(AWRelacion.MOSTRAR_VENCIMIENTO_MAYOR_VALOR,
						new Boolean(((EvnRespRelacion)evento).isMostrarVencimientoMayorValor()));
				session.setAttribute(AWRelacion.MOSTRAR_RESPUESTA_RELACION,
						new Boolean(((EvnRespRelacion)evento).isMostrarLabelRelacion()));
				session.setAttribute(AWRelacion.RESPUESTA_RELACION, ((EvnRespRelacion)evento).getTipoRelacionAvanzar());
			}
		}
	}

}
