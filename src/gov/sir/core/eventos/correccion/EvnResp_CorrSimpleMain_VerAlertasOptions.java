package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import java.util.List;

public class EvnResp_CorrSimpleMain_VerAlertasOptions extends EvnSIRRespuesta {

   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK" ;


   // List< Folio >
   List foliosInTurnoList;

   // List< Folio >
   List foliosInTurnoSinCambiosList;

	public EvnResp_CorrSimpleMain_VerAlertasOptions( String tipoEvento ) {
		super( null, tipoEvento );
	}

	public List getFoliosInTurnoList() {
		return foliosInTurnoList;
	}

	public List getFoliosInTurnoSinCambiosList() {
		return foliosInTurnoSinCambiosList;
	}

	public void setFoliosInTurnoList(List foliosInTurnoList) {
		this.foliosInTurnoList = foliosInTurnoList;
	}

	public void setFoliosInTurnoSinCambiosList(List foliosInTurnoSinCambiosList) {
		this.foliosInTurnoSinCambiosList = foliosInTurnoSinCambiosList;
	}

}
