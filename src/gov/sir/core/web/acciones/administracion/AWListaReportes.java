package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnListaReportes;
import java.util.List;
import gov.sir.core.eventos.administracion.EvnRespListaReportes;
import gov.sir.core.web.WebKeys;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
  /**
    * @author      :  Carlos Torres
    * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
    */
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

public class AWListaReportes extends SoporteAccionWeb {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

        public static final String PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION
            = "PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION";

        public static final String PAGEITEMID__REPORTE00_PANTALLASACTIVAS
            = "PAGEITEMID__REPORTE00_PANTALLASACTIVAS";


      // --------------------------------------------------------------------------------------------------


	public Evento perform(HttpServletRequest request)
		throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

                // - - - - - - - - - - - - - - - - - - - - - - - -
                // buscar el conjunto de reportes a los cuales la persona
                // tiene privilegios de visualizacion
                if( PAGE_REGION__REPORTE00_FINDREPORTLIST_ACTION.equals( accion ) ) {

                  // enviar evento para buscar lista
                  return do_PageEvent_Reporte00_FindReportList_Action( request );


                }



		return null;
	}

  /**
   * do_Reporte00_FindReportList_Action
   *
   * @param request HttpServletRequest
   * @return Evento
   */
  private Evento
  do_PageEvent_Reporte00_FindReportList_Action(HttpServletRequest request) {
    HttpSession session = request.getSession();

    EvnListaReportes event;

    event = new EvnListaReportes( EvnListaReportes.PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENT );

    // List <org.auriga.core.modelo.transferObjects.Rol>
    List local_RolesList;
    local_RolesList = (List)session.getAttribute( WebKeys.LISTA_ROLES ) ;

    event.setRolesList( local_RolesList );
    /**
    * @author      :  Carlos Torres
    * @change      :  Obtener pantallas administrativas del usuario
    * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
    */
    Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
    event.setUsuario(usuario);

    return event;
  } //do_Reporte00_FindReportList_Action

  private void
  doEnd_PageEvent_Reporte00_FindReportList_Action( HttpServletRequest request , EventoRespuesta evento ) {
    HttpSession session = request.getSession();
    EvnRespListaReportes local_Result;
    local_Result = (EvnRespListaReportes)evento;
    // List < PantallaAdministrativa >
    List local_Result_ListPantallasVisibles;
    local_Result_ListPantallasVisibles = local_Result.getListPantallasVisibles();

    session.setAttribute( PAGEITEMID__REPORTE00_PANTALLASACTIVAS, local_Result_ListPantallasVisibles );

  } // doEnd_PageEvent_Reporte00_FindReportList_Action

  public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
    if( null == evento ) {
      return;
    }

    if( !(  evento instanceof EvnRespListaReportes ) ) {
      return;
    }

    EvnRespListaReportes local_Event = (EvnRespListaReportes)evento;


    if( EvnRespListaReportes.PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK.equals( local_Event.getTipoEvento() ) ) {

      // data2cache
      doEnd_PageEvent_Reporte00_FindReportList_Action( request, evento );


    } // if




  }

}
