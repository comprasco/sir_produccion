
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import java.util.List;

public class EvnRespListaReportes extends EvnSIRRespuesta {

  public static final String PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK = "PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK";

  private List listPantallasVisibles;

  public EvnRespListaReportes( String tipoEvento ) {
          super( null, tipoEvento );
  }

  /**
   * setListPantallasVisibles
   *
   * @param local_Result_ListPantallasVisibles List
   */
  public void setListPantallasVisibles( List newListPantallasVisibles ) {
    this.listPantallasVisibles = newListPantallasVisibles;
  }

  public List getListPantallasVisibles() {
    return listPantallasVisibles;
  }

}
