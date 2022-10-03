package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import java.util.List;

public class EvnListaReportes extends EvnSIR {


  public static final String PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENT = "PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENT";

    public EvnListaReportes(String tipoEvento) {
        super(null, tipoEvento);
    }

  public List getRolesList() {
    return rolesList;
  }

  public void setRolesList(List local_RolesList) {
    this.rolesList = local_RolesList;
  }

  List rolesList;
}
