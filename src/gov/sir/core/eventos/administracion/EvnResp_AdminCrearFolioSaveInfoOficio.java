
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

public class EvnResp_AdminCrearFolioSaveInfoOficio extends EvnSIRRespuesta {
	
	public static final String ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESP_OK
      	= "ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESP_OK";
   
	public static final String ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESP_OK
  		= "ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESP_OK";

	Folio folioEditado;
   Turno turno;
   
	
	public EvnResp_AdminCrearFolioSaveInfoOficio( String tipoEvento ) {
		super( null, tipoEvento );
	} // end-method:


	public Folio getFolioEditado() {
		return folioEditado;
	}


	public void setFolioEditado(Folio folioEditado) {
		this.folioEditado = folioEditado;
	}


	public Turno getTurno() {
		return turno;
	}


	public void setTurno(Turno turno) {
		this.turno = turno;
	}


}
