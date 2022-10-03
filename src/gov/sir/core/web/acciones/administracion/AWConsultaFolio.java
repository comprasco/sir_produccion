package gov.sir.core.web.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import gov.sir.core.eventos.administracion.EvnConsultaFolio;
import gov.sir.core.eventos.administracion.EvnRespConsultaFolio;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.constantes.CExcepcion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CTabs;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ConsultarFolioException;
import gov.sir.core.web.acciones.excepciones.ReabrirFolioException;
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se habilita el uso de las clases
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos
 * de consultas a Folios. 
  * @author ppabon
 */
public class AWConsultaFolio extends SoporteAccionWeb {

	/** Constante que identifica la acción de consultar un  folio */
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
	
	/** Constante que identifica la acción de anular el folio */
	public static final String ANULAR_FOLIO = "ANULAR_FOLIO";
	
	/** Constante que identifica la acción de consultar un  folio por la pantalla administrativa */
	public static final String CONSULTAR_FOLIO_ADMINISTRACION = "CONSULTAR_FOLIO_ADMINISTRACION";	

	/** Constante que identifica la acción de consultar un  folio */
	public static final String REGRESAR_INICIO = "REGRESAR_INICIO";
	

	/** Constante que identifica la acción de consultar un  folio, usada en reabrir folio  */
	public static final String CONSULTAR_FOLIO_REABRIR = "CONSULTAR_FOLIO_REABRIR";
	
	public final static String VISTA_VOLVER = "VISTA_VOLVER";
	
	/** Constante que identifica la acción de desbloquear el folio para restitucion  */
	public static final String DESBLOQUEAR_FOLIO_RESTITUCION = "DESBLOQUEAR_FOLIO_RESTITUCION";

	


	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro.
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion válida");
		} else if (accion.equals(AWConsultaFolio.CONSULTAR_FOLIO)) {
			return consultarFolio(request);
		} else if (accion.equals(AWConsultaFolio.CONSULTAR_FOLIO_ADMINISTRACION)) {
			EvnConsultaFolio evento = consultarFolio(request);
			evento.setConsultarDefinitivo(true);
			return evento;			
		} else if (accion.equals(AWConsultaFolio.REGRESAR_INICIO)) {
			return regresarInicio(request);
		} else if (accion.equals(AWConsultaFolio.ANULAR_FOLIO)) {
			return anularFolio(request);
		} else if (accion.equals(AWConsultaFolio.CONSULTAR_FOLIO_REABRIR)) {
		    return consultarFolioReabrir(request);
		} else if (accion.equals(AWConsultaFolio.DESBLOQUEAR_FOLIO_RESTITUCION)) {
		    return desbloqueoFolioRestitucion(request);
		} 

		return null;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de consulta de folio
	 * @param request
	 * @return evento <code>EvnConsultaFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnConsultaFolio consultarFolio(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		
		String vista = (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
		request.getSession().setAttribute(
			VISTA_VOLVER,
			vista);
		request.getSession().setAttribute("folioPosibleTemporal","true");

		ConsultarFolioException exception = new ConsultarFolioException();

		String tipoConsulta = request.getParameter(CTabs.TAB_CONSULTA);
		String id = null;		
		
		//SE DETERMINA SI LA CONSULTA ES DESDE EL TAB DE FOLIOS PADRE E HIJOS O SI ES DESDE
		//LA PANTALLA ADMINISTRATIVA Y SE RECUPERA EL ID DE LA MATRICULA PARA LLAMAR LA CONSULTA 		
		if (tipoConsulta == null) {
			id = request.getParameter(CFolio.ID_MATRICULA);
			if ((id == null) || (id.trim().length() == 0)) {
				exception.addError("Debe digitar un número de matrícula.");
			} 				
		} else {
			if (tipoConsulta.equals(CTabs.TAB_CONSULTA_PADRE)) {
				
				/**Cuando se este en la opcion de matriz y segregados y sale alguna excepcion
				 * debe volver a la misma pantalla de matriz y segregados*/
				String[] matriculas = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
				if (matriculas == null || matriculas.length < 1) {
					exception.addError("Debe seleccionar la matrícula a consultar");
					request.getSession().setAttribute(WebKeys.RAZON_EXCEPCION,CExcepcion.MATRIZ_SEGREGADOS_EXCEPCION);
				}
				if (matriculas != null && matriculas.length > 1) {
					exception.addError("Seleccione sólo una matrícula");
					request.getSession().setAttribute(WebKeys.RAZON_EXCEPCION,CExcepcion.MATRIZ_SEGREGADOS_EXCEPCION);
				}
				if (matriculas != null && matriculas.length == 1) {
					id = matriculas[0];
				}
			}	
		}	
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(id)){
                        exception.addError("El folio que desea consultar se encuentra en estado 'Bloqueado'.");
                    }
                } catch (GeneralSIRException ex) {
                    if(ex.getMessage() != null){
                        exception.addError(ex.getMessage());
                    }
                }
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		//Se hace el filtro de que no tenga espacios
		StringTokenizer st = new StringTokenizer(id," ");
		id = st.nextToken();
		
		if((id==null || id.trim().equals("")) && exception.getErrores().size()==0){
			exception.addError("Debe digitar un número de matrícula.");
		} else {
			String[] partesId = id.split("-");
			if(partesId == null || (partesId.length < 2 && partesId.length > 3))
				exception.addError("El formato para el número de matrícula es inválido.");
            else if(partesId[0].trim().equals("") || partesId[1].trim().equals(""))
				exception.addError("El formato para el número de matrícula es inválido.");
			if(partesId.length == 3 && partesId[2].trim().equals(""))
				exception.addError("El formato para el número de matrícula es inválido.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		Circulo circulo = (Circulo)session.getAttribute(WebKeys.CIRCULO); 
		/*if(id.indexOf(circulo.getIdCirculo()) < 0){
			id = circulo.getIdCirculo() + "-" + id;
		}*/	

		Usuario usuario    = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

	
		EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.CONSULTAR_FOLIO, id, usuarioSIR);
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Se agrega esta condicion para el visualizar los detalles del folio en el proceso traslado folio
                 * Caso Mantis  :   07123
                 */
                if (request.getParameter("VER_FOLIO_TRASLADO") == null){
                    evento.setCirculo(circulo);
                }	

		return evento;
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de consulta de folio
	 * @param request
	 * @return evento <code>EvnConsultaFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnConsultaFolio anularFolio(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		
		ConsultarFolioException exception = new ConsultarFolioException();
		
		String descripcion = request.getParameter(CFolio.DESCRIPCION_ANULACION);
		if(descripcion ==null || descripcion.equals("")){
			exception.addError("La razon de anulacion es invalida");
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		Folio fol=(Folio) request.getSession().getAttribute(WebKeys.FOLIO);
		Usuario usuario    = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO); 
		
		EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.ANULAR_FOLIO );
		evento.setFolio(fol);
		evento.setRazonAnulacion(descripcion);
		evento.setUsuarioSIR(usuarioSIR);

		return evento;

	}
	

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de consulta de folio
	 * @param request
	 * @return evento <code>EvnConsultaFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnConsultaFolio consultarFolioReabrir(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		ReabrirFolioException exception = new ReabrirFolioException();

		String id = null;		

		id = request.getParameter(CFolio.ID_MATRICULA);
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		
		if((id == null) || (id.trim().length() == 0)){
			exception.addError("Debe digitar un número de matrícula.");
		} else {
			String[] partesId = id.split("-");
			if(partesId == null || partesId.length != 2)
				exception.addError("El formato para el número de matrícula es inválido.");
            else if(partesId[0].trim().equals("") || partesId[1].trim().equals(""))
				exception.addError("El formato para el número de matrícula es inválido.");
            else{
            	if(circulo==null){
            		exception.addError("El usuario no tiene cargado su respectivo círculo en sesión.");
            	}
            	else{
            		if(!circulo.getIdCirculo().equals(partesId[0].trim())){
            			exception.addError("El usuario sólo puede reabrir folios de su círculo");
            		}
            	}
            }
		}
		
		
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		

		Usuario usuario    = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.CONSULTAR_FOLIO_REABRIR, id, usuarioSIR);

		return evento;
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de consulta de folio
	 * @param request
	 * @return evento <code>EvnConsultaFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnConsultaFolio desbloqueoFolioRestitucion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		ReabrirFolioException exception = new ReabrirFolioException();

		String id = null;		

		id = request.getParameter(CFolio.ID_MATRICULA);
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		
		if((id == null) || (id.trim().length() == 0)){
			exception.addError("Debe digitar un número de matrícula.");
		} else {
			String[] partesId = id.split("-");
			if(partesId == null || partesId.length != 2)
				exception.addError("El formato para el número de matrícula es inválido.");
            else if(partesId[0].trim().equals("") || partesId[1].trim().equals(""))
				exception.addError("El formato para el número de matrícula es inválido.");
            else{
            	if(circulo==null){
            		exception.addError("El usuario no tiene cargado su respectivo círculo en sesión.");
            	}
            	else{
            		if(!circulo.getIdCirculo().equals(partesId[0].trim())){
            			exception.addError("El usuario sólo puede desbloquear folios de su círculo");
            		}
            	}
            }
		}
		/**
                * @Autor: Edgar Lora
                * @Mantis: 11309
                */
		TrasladoSIR trasladoSir = new TrasladoSIR();
                try {
                    if(trasladoSir.isBloqueDeTraslado(id)){
                        exception.addError("El folio " + id + " esta pendiente por confirmar traslado.");
                    }
                } catch (GeneralSIRException ex) {
                    exception.addError(ex.getMessage());
                }
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		

		Usuario usuario    = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.DESBLOQUEAR_FOLIO_RESTITUCION, id, usuarioSIR);

		return evento;
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * eliminar los atributos que estan en la sesión y de esta manera asegurar que los datos se cargan nuevamente y no 
	 * son los que estaban guardados en la sesión.
	 * @param request
	 * @return evento <code>EvnConsultaFolio</code> con la información del círculo
	 * @throws AccionWebException
	 */
	private EvnConsultaFolio regresarInicio(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		
		/*String vista = (String) request.getSession().getAttribute(VISTA_VOLVER);
		Object obj = request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);

		Enumeration enumeration = session.getAttributeNames();
		String nombre = new String();
		while(enumeration.hasMoreElements()){
			nombre = (String) enumeration.nextElement();
			if(!nombre.startsWith("org.auriga") &&
					!nombre.equals(WebKeys.LISTA_ROLES) &&
					!nombre.equals(WebKeys.LISTA_ESTACIONES) &&
					!nombre.startsWith(WebKeys.LISTA_IMPRESORAS) &&
					!nombre.equals(WebKeys.LISTA_PROCESOS) &&
					!nombre.equals(WebKeys.LISTA_PROCESOS_INICIABLES) &&
					!nombre.equals(WebKeys.LISTA_FASES) &&
					!nombre.equals(WebKeys.LISTA_TURNOS) &&
					!nombre.equals(WebKeys.LISTA_CONSULTAS) &&					
					!nombre.equals(WebKeys.ROL) &&
					!nombre.equals(WebKeys.RECARGA) &&					
					!nombre.equals(WebKeys.ESTACION) &&
					!nombre.equals(WebKeys.USUARIO) &&
					!nombre.equals(WebKeys.CIRCULO) &&
					!nombre.equals(WebKeys.LISTENER)){
				session.removeAttribute(nombre);
			}
		}
		
		request.getSession().setAttribute(
			VISTA_VOLVER,
			vista);
			
		request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, obj);
		*/
		
		request.getSession().removeAttribute(WebKeys.FOLIO);
                /**
                 * @author     : Carlos Torres
                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                 */
                request.getSession().removeAttribute(WebKeys.TRASLADO_ORIGEN);
                request.getSession().removeAttribute(WebKeys.TRASLADO_DESTINO);
                request.getSession().removeAttribute(WebKeys.CIRCULO_ORIGEN);
                request.getSession().removeAttribute(WebKeys.CIRCULO_DESTINO);
                
		
		List listaConsultas = (List) session.getAttribute(WebKeys.LISTA_CONSULTAS);
		if(listaConsultas==null){
			listaConsultas = new ArrayList();
		}
		Usuario usuario    = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		if(listaConsultas.size()>1){
			
			String id = (String)listaConsultas.get(listaConsultas.size()-2);
			
			listaConsultas.remove(listaConsultas.size()-1);
			session.setAttribute(WebKeys.LISTA_CONSULTAS, listaConsultas);			
						
			EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.CONSULTAR_FOLIO, id, usuarioSIR);
			evento.setConsultarDefinitivo(true);
			return evento;
			
			
		}else if(listaConsultas.size()==1){
			
			listaConsultas.remove(listaConsultas.size()-1);
			session.setAttribute(WebKeys.LISTA_CONSULTAS, listaConsultas);
			String idMatricula = (String)session.getAttribute(CFolio.ID_MATRICULA_INICIAL);
			String vistaOriginal=(String)request.getSession().getAttribute(WebKeys.VISTA_INICIAL);
			if(vistaOriginal!=null)
				request.getSession().setAttribute(AWConsultaFolio.VISTA_VOLVER, vistaOriginal);
			if(idMatricula!=null){
				EvnConsultaFolio evento = new EvnConsultaFolio(usuario, EvnConsultaFolio.CONSULTAR_FOLIO, idMatricula, usuarioSIR);
				evento.setConsultarDefinitivo(true);
				return evento;
			}
			return null;
		}
		
		return null;
		
	}	

	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespConsultaFolio respuesta = (EvnRespConsultaFolio) evento;
		String accion = request.getParameter(WebKeys.ACCION).trim();

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
                        
			if (tipoEvento.equals(EvnRespConsultaFolio.CONSULTAR_FOLIO)) {
                                if(respuesta.getHistorialAreas() != null){
                                    session.setAttribute(WebKeys.HISTORIAL_AREAS, respuesta.getHistorialAreas());
                                }
				session.setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                                request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS, respuesta.getFolio().getLinderosDef());
				session.setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO,new Boolean(respuesta.isEsMayorExtension()));
				session.setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(respuesta.getNumeroAnotaciones()));

				session.setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN, respuesta.getGravamenes());
				session.setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES, respuesta.getMedidasCautelares());
				
				session.setAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION, respuesta.getFalsaTradicion());
				session.setAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS, respuesta.getAnotacionesInvalidas());
				
				session.setAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR, respuesta.getAnotacionesPatrimonioFamiliar());	
				session.setAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA, respuesta.getAnotacionesAfectacionVivienda());	
								
				session.setAttribute(WebKeys.LISTA_FOLIOS_PADRE, respuesta.getFoliosPadre());
				session.setAttribute(WebKeys.LISTA_FOLIOS_HIJO, respuesta.getFoliosHijo());
				session.setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES, respuesta.getSalvedadesAnotaciones());
				session.setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION, respuesta.getCancelaciones());

				session.setAttribute(WebKeys.TURNO_TRAMITE, respuesta.getTurnoTramite());
				session.setAttribute(WebKeys.TURNO_DEUDA, respuesta.getTurnoDeuda());
				session.setAttribute(WebKeys.USUARIO_BLOQUEO, respuesta.getUsuarioBloqueo());
				
				session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE, respuesta.getFoliosDerivadoPadre());
				session.setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
				session.setAttribute(WebKeys.LISTA_TURNOS_FOLIO_TRAMITE, respuesta.getTurnosFolioTramite());
                                /*
                                 * @author      : Julio Alcázar Rivas
                                 * @change      : Set atributos en la session
                                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                 */
                                session.setAttribute(WebKeys.TRASLADO_DESTINO, respuesta.getTrasladoDestino());
			        session.setAttribute(WebKeys.TRASLADO_ORIGEN, respuesta.getTrasladoOrigen());
                                session.setAttribute(WebKeys.CIRCULO_DESTINO, respuesta.getCirculoDestino());
			        session.setAttribute(WebKeys.CIRCULO_ORIGEN, respuesta.getCirculoOrigen());
				
				session.setAttribute(WebKeys.RECARGA, new Boolean(true));

				if(respuesta.getFolio()!=null){
					
					List listaConsultas = (List) session.getAttribute(WebKeys.LISTA_CONSULTAS);
					if(listaConsultas==null){
						listaConsultas = new ArrayList();
					}
					

					if(accion!=null&&!accion.equals(AWConsultaFolio.REGRESAR_INICIO)){
						listaConsultas.add(respuesta.getFolio().getIdMatricula());	
					}

					session.setAttribute(WebKeys.LISTA_CONSULTAS, listaConsultas);					
					
				}				

			} 
			
			else if (tipoEvento.equals(EvnRespConsultaFolio.CONSULTAR_FOLIO_REABRIR_OK)) {

					session.setAttribute(WebKeys.FOLIO, respuesta.getFolio());
					session.setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO,new Boolean(respuesta.isEsMayorExtension()));
					session.setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(respuesta.getNumeroAnotaciones()));

					session.setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN, respuesta.getGravamenes());
					session.setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES, respuesta.getMedidasCautelares());
					
					session.setAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR, respuesta.getAnotacionesPatrimonioFamiliar());	
					session.setAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA, respuesta.getAnotacionesAfectacionVivienda());
								
					session.setAttribute(WebKeys.TURNO_TRAMITE, respuesta.getTurnoTramite());
					session.setAttribute(WebKeys.TURNO_DEUDA, respuesta.getTurnoDeuda());
					session.setAttribute(WebKeys.USUARIO_BLOQUEO, respuesta.getUsuarioBloqueo());
					session.setAttribute(WebKeys.REABIERTO_FOLIO, new Boolean(false));

					if(respuesta.getFolio()!=null){
					
						List listaConsultas = (List) session.getAttribute(WebKeys.LISTA_CONSULTAS);
						if(listaConsultas==null){
							listaConsultas = new ArrayList();
						}
					

						if(accion!=null&&!accion.equals(AWConsultaFolio.REGRESAR_INICIO)){
							listaConsultas.add(respuesta.getFolio().getIdMatricula());	
						}

						session.setAttribute(WebKeys.LISTA_CONSULTAS, listaConsultas);					
					
					}				

				} 
			else if (tipoEvento.equals(EvnRespConsultaFolio.ANULAR_FOLIO)) {
				Folio f= (Folio)respuesta.getPayload();
				session.setAttribute(WebKeys.FOLIO, f);
			} 
			else if (tipoEvento.equals(EvnRespConsultaFolio.DESBLOQUEO_FOLIO_RESTIRUCION_OK)) {
				Folio f= (Folio)respuesta.getPayload();
				session.setAttribute(WebKeys.FOLIO, f);
			}
		}
	}
}
