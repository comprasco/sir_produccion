package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Turno;

public class Evn_CorrSimpleMain_VerAlertasOptions extends EvnSIR {


   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT";
   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENT
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENT";

   org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
   gov.sir.core.negocio.modelo.Usuario usuarioSir;
   
   gov.sir.core.negocio.modelo.Turno turno;
   
	public Evn_CorrSimpleMain_VerAlertasOptions( String tipoEvento ) {
        super(null, tipoEvento);
    }

    public void setUsuarioAuriga( org.auriga.core.modelo.transferObjects.Usuario newUsuarioAuriga ) {
      usuarioAuriga= newUsuarioAuriga;
    }

    public void setUsuarioSir( gov.sir.core.negocio.modelo.Usuario newUsuarioSir ) {
      usuarioSir = newUsuarioSir;
    }

	public gov.sir.core.negocio.modelo.Usuario getUsuarioSir() {
		return usuarioSir;
	}

	public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
		return usuarioAuriga;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}



}
