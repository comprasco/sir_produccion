package gov.sir.core.web.acciones.correccion;

import gov.sir.core.eventos.correccion.EvnMsgMesaControl;
import gov.sir.core.eventos.correccion.EvnRespMsgMesaControl;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CorreccionesMesaControl_PageValidatorExceptionCollector;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 */
public
class AwCorr_MesaControl
extends SoporteAccionWeb {

  // PAGERENDERING-PROCESSES-IDS -----------------------------------------------------------------------
  // (id)(on)__()_PROCESS_ACTION
  // ITEMS --------------------------------------------------------------------------------------------
  public static final String MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION
      = "MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION";
  
	public final static String IMPRIMIR_MESA = "IMPRIMIR_MESA";
	
	public final static String VOLVER_MESA = "VOLVER_MESA";
	
	public final static String IMPRIMIR = "IMPRIMIR";
	
	public final static String FOLIOS_NO_IMPRESOS = "FOLIOS_NO_IMPRESOS";


  // --------------------------------------------------------------------------------------------------

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro.
	 */
public Evento
perform(HttpServletRequest request)
throws AccionWebException {

        String action = request.getParameter( WebKeys.ACCION );

        HttpSession session = request.getSession();

        if( ( null == action )
            ||( "".equalsIgnoreCase( action.trim() ) ) ) {

          throw new AccionInvalidaException( "No se ha indicado una accion valida" );

        }


        // PAGERENDERING-PROCESSES ---------------------------------------------
        if( MESACONTROLONOK__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
           return do_PageProcessingProcess_MesaControlOnOk( request );
        } else if (IMPRIMIR_MESA.equals( action )) {
			return imprimirCertificadosMesa(request);
		} else if (IMPRIMIR.equals( action )) {
			return imprimirCertificadosIndividual(request);
		} else if (VOLVER_MESA.equals( action )) {
			return null;
		}
        // ---------------------------------------------------------------------
        return null;
} // :perform






private EvnMsgMesaControl
do_PageProcessingProcess_MesaControlOnOk( HttpServletRequest request )
throws ValidacionParametrosException {

       HttpSession session = request.getSession();

       ValidacionParametrosException exception
        = new CorreccionesMesaControl_PageValidatorExceptionCollector();

       // -----------------------------------------------------------------------------------------------
       EvnMsgMesaControl result = null;

       org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
       gov.sir.core.negocio.modelo.Usuario            param_UsuarioTurno;
       gov.sir.core.negocio.modelo.Turno              param_Turno;
       final String                                   param_WorkflowMessageId = "CONFIRMAR";

       String param_EvnType;

       param_UsuarioAuriga
           = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute( SMARTKeys.USUARIO_EN_SESION );
       param_EvnType
           = EvnMsgMesaControl.EVNTYPE_MESACONTROL_ONOK;
       param_UsuarioTurno
           = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );
       param_Turno
           = (gov.sir.core.negocio.modelo.Turno)session.getAttribute( WebKeys.TURNO );

     // aca VOY

       // -----------------------------
       EvnMsgMesaControl resultWrap;
       resultWrap = new EvnMsgMesaControl(
           param_UsuarioAuriga
         , param_EvnType
       );

       resultWrap.setTurno( param_Turno );
       resultWrap.setUsuarioTurno( param_UsuarioTurno );
       resultWrap.setWorkflowMessageId( param_WorkflowMessageId );

       // -----------------------------
       result = resultWrap;
       // -----------------------------------------------------------------------------------------------

       return result;

     } //:do_PageProcessingProcess_MesaControlOnOk
// -------------------------------------------------------------------------------------------------------------

/**
 * Método que permite imprimir los certificados.
 * @param request
 * @return
 * @throws AccionWebException
 */
private EvnMsgMesaControl imprimirCertificadosMesa(HttpServletRequest request) throws ValidacionParametrosException {
	HttpSession session = request.getSession();
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
	Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
	Circulo cir = (Circulo) session.getAttribute(WebKeys.CIRCULO);
	String impresora = request.getParameter(WebKeys.IMPRESORA);
	//Solicitud solicitud = (Solicitud) turno.getSolicitud();
	Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
	
	Turno turnoPadre = (Turno) session.getAttribute(WebKeys.TURNO_PADRE);

	 EvnMsgMesaControl resultWrap;
     resultWrap = new EvnMsgMesaControl(usuario , EvnMsgMesaControl.IMPRIMIR_MESA);
     resultWrap.setTurno(turno);
     resultWrap.setFase(fase);
     resultWrap.setEstacion(estacion);
     resultWrap.setCirculoId(cir.getIdCirculo());
     resultWrap.setImpresora(impresora);
     resultWrap.setTurnoPadre(turnoPadre);
     return resultWrap;
     
     /*
		EvnCorreccion eventoCorreccion = new EvnCorreccion(usuario, null, turno, fase, estacion, EvnCorreccion.IMPRIMIR_MESA);
		eventoCorreccion.setCirculoId(cir.getIdCirculo());
		eventoCorreccion.setImpresora(impresora);
		return eventoCorreccion;*/
}

/**
 * Método que permite imprimir los certificados seleccionados.
 * @param request
 * @return
 * @throws AccionWebException
 */
private EvnMsgMesaControl imprimirCertificadosIndividual(HttpServletRequest request) throws ValidacionParametrosException {
	HttpSession session = request.getSession();
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
	Circulo cir = (Circulo) session.getAttribute(WebKeys.CIRCULO);
	Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
	//Solicitud solicitud = (Solicitud) turno.getSolicitud();
	String impresora = request.getParameter(WebKeys.IMPRESORA);
	Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
	gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
	List folios = new ArrayList();

	String matriculasImp = request.getParameter(WebKeys.TITULO_CHECKBOX_ELIMINAR);
	if (matriculasImp != null) {
		Folio folio = new Folio();
		folio.setIdMatricula(matriculasImp);
		folios.add(folio);
	}
	if (folios.size() > 0) {
		EvnMsgMesaControl eventoCorreccion = new EvnMsgMesaControl(usuario , EvnMsgMesaControl.IMPRIMIR);
		eventoCorreccion.setUsuario(usuario);
		eventoCorreccion.setFolios(folios);
		eventoCorreccion.setTurno(turno);
		eventoCorreccion.setFase(fase);
		eventoCorreccion.setEstacion(estacion);
		eventoCorreccion.setCirculoId(cir.getIdCirculo());
		eventoCorreccion.setImpresora(impresora);
		eventoCorreccion.setUsuarioSIR(usuarioSIR);
		return eventoCorreccion;
	} else {
		return null;
	}

}



private void
doEnd_PageProcessingProcess_MesaControlOnOk( HttpServletRequest request, EventoRespuesta evento ){

} // :doEnd_PageProcessingProcess_MesaControlOnOk


// -------------------------------------------------------------------------------------------------------------
      public void doEnd( HttpServletRequest request, EventoRespuesta evento ) {

        if( null == evento )
          return;

        //
        EvnRespMsgMesaControl thisEvent;
        thisEvent = (EvnRespMsgMesaControl)evento;

        if( EvnRespMsgMesaControl.EVNRESPOK_MESACONTROL_ONOK.equals( thisEvent.getTipoEvento() ) ){
          doEnd_PageProcessingProcess_MesaControlOnOk( request, thisEvent );
        } // if
        
        if ( EvnRespMsgMesaControl.IMPRIMIR_MESA.equals( thisEvent.getTipoEvento() ) ) {
			List foliosNoImpresos = thisEvent.getFoliosNoImpresos();

			List foliosStr = new ArrayList();

			Iterator iter = foliosNoImpresos.iterator();
			while (iter.hasNext()){
				foliosStr.add(((SolicitudFolio)iter.next()).getIdMatricula());
			}

			request.getSession().setAttribute(FOLIOS_NO_IMPRESOS, foliosStr);
		}

      } // :doEnd

} // class
