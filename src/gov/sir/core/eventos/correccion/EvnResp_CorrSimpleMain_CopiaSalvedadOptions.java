package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import java.util.List;

public class EvnResp_CorrSimpleMain_CopiaSalvedadOptions extends EvnSIRRespuesta {

   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK" ;

   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK" ;

   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK" ;

   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK" ;


   private Folio folio;
   org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
   gov.sir.core.negocio.modelo.Turno turno;

   // List < String >
   List validaciones;



   // List< Folio >
   private List foliosInTurnoList;

   // List< Folio >
   private List foliosInTurnoConSalvedadesList;

   // List< Folio >
   private List possibleFoliosTargetList;

   // List< Folio >
   private List targetFolioList;

   // List< Integer >
   private List targetFolioListCountSalvedades   ;

   // Folio
   private Folio folioSource;



	public EvnResp_CorrSimpleMain_CopiaSalvedadOptions( String tipoEvento ) {
		super( null, tipoEvento );
	}

	public void setFolio( Folio newFolio ) {
	  folio = newFolio;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public void setValidaciones(List validaciones) {
		this.validaciones = validaciones;
	}

	public void setUsuarioAuriga(org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga) {
		this.usuarioAuriga = usuarioAuriga;
	}

	public void setFoliosInTurnoConSalvedadesList(List
																 foliosInTurnoConSalvedadesList) {
		this.foliosInTurnoConSalvedadesList = foliosInTurnoConSalvedadesList;
	}

	public void setFoliosInTurnoList(List foliosInTurnoList) {
		this.foliosInTurnoList = foliosInTurnoList;
	}



	public void setFolioSource(Folio folioSource) {
		this.folioSource = folioSource;
	}

	public void setPossibleFoliosTargetList(List possibleFoliosTargetList) {
		this.possibleFoliosTargetList = possibleFoliosTargetList;
	}

	public void setTargetFolioList(List targetFolioList) {
		this.targetFolioList = targetFolioList;
	}

	public void setTargetFolioListCountSalvedades(List targetFolioListCountSalvedades) {
		this.targetFolioListCountSalvedades = targetFolioListCountSalvedades;
	}

	public Folio getFolio() {
		return folio;
	}

	public Turno getTurno() {
		return turno;
	}

	public List getValidaciones() {
		return validaciones;
	}

	public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
		return usuarioAuriga;
	}

	public List getFoliosInTurnoConSalvedadesList() {
		return foliosInTurnoConSalvedadesList;
	}

	public List getFoliosInTurnoList() {
		return foliosInTurnoList;
	}

	public Folio getFolioSource() {
		return folioSource;
	}

	public List getPossibleFoliosTargetList() {
		return possibleFoliosTargetList;
	}

	public List getTargetFolioList() {
		return targetFolioList;
	}

	public List getTargetFolioListCountSalvedades() {
		return targetFolioListCountSalvedades;
	}


}
