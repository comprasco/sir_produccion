package gov.sir.core.web.acciones.registro;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


import gov.sir.core.eventos.registro.EvnConsultaFolio;
import gov.sir.core.eventos.registro.EvnRespConsultaFolio;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;

/**
 * @author mmunoz
 */
public class AWConsultaFolio extends SoporteAccionWeb {

	public static final String TERMINAR_PREVIEW = "TERMINAR_PREVIEW";
	public static final String TERMINO= "TERMINO";
	public static final String FOLIO_PADRE = "PADRE";
	public static final String FOLIO_HIJO = "HIJO";
	public static final String FOLIO_PREVIEW = "FOLIO_PREVIEW";
	public static final String FOLIO_ANOTACION = "FOLIO_ANOTACION";
	public static final String RELACION_FOLIO = "RELACION_FOLIO";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Solicitud solicitud = turno.getSolicitud();
		String folioAnotacion = request.getParameter(FOLIO_ANOTACION);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
																															   .getAttribute(SMARTKeys.USUARIO_EN_SESION);
		// Este codigo dependera de la cantidad de paginadores q hayan, este es
		// el caso baso 1 solo mostrarFolioHelper.
		String nombrePaginador = (String)request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		
		/**
		 * @author Cesar Ramírez
		 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
		 * Manejar la vista cuando se consulta un folio en una ventana adicional y se da clic en el botón "cerrar ventana".
		 **/
		//if(session.getAttribute(WebKeys.VISTA_PARA_REGRESAR)==null && folioAnotacion!=null);
		if(session.getAttribute(WebKeys.VISTA_PARA_REGRESAR)==null) {
			session.setAttribute(WebKeys.VISTA_PARA_REGRESAR, session.getAttribute(SMARTKeys.VISTA_ACTUAL));
		}
		
		if(nombrePaginador!=null && folioAnotacion==null){
			request.getSession().removeAttribute(nombrePaginador);
		}
		if(nombreResultado!=null && folioAnotacion==null){
			request.getSession().removeAttribute(nombreResultado);
		}
		
		request.getSession().removeAttribute(AWConsultaFolio.TERMINO);
		
		Usuario usuarioNeg=(Usuario) session.getAttribute(WebKeys.USUARIO);
		
		
		String accion= request.getParameter(WebKeys.ACCION);
		if(accion!=null){
			if(accion.equals(AWConsultaFolio.TERMINAR_PREVIEW)){
				return regresar(request);
			}
		}
		
		Folio folio = null;
		Iterator itSolFolios = (solicitud.getSolicitudFolios()).iterator();
		int i = 0;
		String valPos = new String();
		valPos = (request.getParameter("POSICION")).trim();
		int pos = Integer.parseInt(valPos);
		while(itSolFolios.hasNext()){
			SolicitudFolio sol = (SolicitudFolio)itSolFolios.next();
			if(pos==i){
				folio = sol.getFolio();
			}
			i++;
		}
		if(turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) && turno.getIdFase().equals(CFase.CAL_CALIFICACION)){
			String relacionFolio = request.getParameter(RELACION_FOLIO);
			
			if(relacionFolio != null){
				List foliosPadre = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_PADRE);
				List foliosHijo  = (List)session.getAttribute(WebKeys.LISTA_FOLIOS_HIJO);
				if((foliosHijo!=null && foliosHijo.size()>pos) || (foliosPadre!=null && foliosPadre.size()>pos))
				if(relacionFolio.equals(FOLIO_PADRE)){
					folio = (Folio)foliosPadre.get(pos);
				}else{
					folio = (Folio)foliosHijo.get(pos);
				}
			}else if(folioAnotacion!=null){
				folio = (Folio)session.getAttribute(WebKeys.FOLIO);
			}
		}
		EvnConsultaFolio ev= new EvnConsultaFolio(usuarioAuriga,folio);
		ev.setTurno(turno);
		ev.setUsuarioNeg(usuarioNeg);
		return ev;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest,
	 *      org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request,EventoRespuesta evento) {
		if(evento!=null){
			EvnRespConsultaFolio ev= (EvnRespConsultaFolio) evento;
			Folio folio = ev.getFolio();
			java.math.BigDecimal totalAnotaciones = java.math.BigDecimal.valueOf((long)folio.getAnotaciones().size());
			if(ev.getFolio() != null){
                                if(ev.getHistorialAreas() != null){
                                    request.getSession().setAttribute(WebKeys.HISTORIAL_AREAS, ev.getHistorialAreas());
                                }
				request.getSession().setAttribute(WebKeys.FOLIO_AUXILIAR,folio);
                                request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS,folio.getLinderosDef());
				request.getSession().setAttribute(WebKeys.TOTAL_ANOTACIONES_PREVIEW, totalAnotaciones);
				
				request.getSession().setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO_PREVIEW,new Boolean(ev.isEsMayorExtension()));

				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN_PREVIEW, ev.getGravamenes());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES_PREVIEW, ev.getMedidasCautelares());
				
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION_PREVIEW, ev.getFalsaTradicion());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS_PREVIEW, ev.getAnotacionesInvalidas());
				
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR_PREVIEW, ev.getAnotacionesPatrimonioFamiliar());	
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA_PREVIEW, ev.getAnotacionesAfectacionVivienda());	
								
				request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE_PREVIEW, ev.getFoliosPadre());
				request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO_PREVIEW, ev.getFoliosHijo());
				request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES_PREVIEW, ev.getSalvedadesAnotaciones());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION_PREVIEW, ev.getCancelaciones());

				request.getSession().setAttribute(WebKeys.TURNO_TRAMITE_PREVIEW, ev.getTurnoTramite());
				request.getSession().setAttribute(WebKeys.TURNO_DEUDA_PREVIEW, ev.getTurnoDeuda());
				request.getSession().setAttribute(WebKeys.USUARIO_BLOQUEO_PREVIEW, ev.getUsuarioBloqueo());
				
				request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE_PREVIEW, ev.getFoliosDerivadoPadre());
				request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO_PREVIEW, ev.getFoliosDerivadoHijo());
				request.getSession().setAttribute(WebKeys.LISTA_TURNOS_FOLIO_TRAMITE_PREVIEW, ev.getTurnosFolioTramite());
				
				request.getSession().setAttribute(WebKeys.RECARGA_PREVIEW, new Boolean(true));
                                /*               
                                 * @author     : Carlos Torres
                                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                 */
                                request.getSession().removeAttribute(WebKeys.TRASLADO_ORIGEN);
                                request.getSession().removeAttribute(WebKeys.TRASLADO_DESTINO);
                                request.getSession().removeAttribute(WebKeys.CIRCULO_ORIGEN);
                                request.getSession().removeAttribute(WebKeys.CIRCULO_DESTINO);
                                request.getSession().setAttribute(WebKeys.TRASLADO_DESTINO, ev.getTrasladoDestino());
			        request.getSession().setAttribute(WebKeys.TRASLADO_ORIGEN, ev.getTrasladoOrigen());
                                request.getSession().setAttribute(WebKeys.CIRCULO_DESTINO, ev.getCirculoDestino());
			        request.getSession().setAttribute(WebKeys.CIRCULO_ORIGEN, ev.getCirculoOrigen());
			}
		}
	}
	
	/**
	 * Este método se encarga de limpiar los datos de los paginadores y
	 * resultados del <code>HttpServletRequest</code> para realizar de nuevo
	 * las consultas.
	 * 
	 * @param request
	 * @return evento <code>EvnConsulta</code> con la información de la
	 *         SolicitudConsulta a crear.
	 */
	private Evento regresar(HttpServletRequest request) {
		
		// Este codigo dependera de la cantidad de paginadores q hayan, este es
		// el caso baso 1 solo mostrarFolioHelper.
		String nombrePaginador = (String)request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		
		request.getSession().removeAttribute(nombrePaginador);
		request.getSession().removeAttribute(nombreResultado);
		
		// se activa el flag de que se acabo el preview
		request.getSession().setAttribute(AWConsultaFolio.TERMINO, new Boolean(true));
		return null;
	}

}
