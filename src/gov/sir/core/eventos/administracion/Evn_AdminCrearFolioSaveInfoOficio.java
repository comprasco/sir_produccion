package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Folio;

public class Evn_AdminCrearFolioSaveInfoOficio extends EvnSIR {


   public static final String ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENT
       	= "ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENT";

	public static final String ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENT 
		= "ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENT";
   
   org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
   gov.sir.core.negocio.modelo.Usuario usuarioSir;

   //
   
   //
   gov.sir.core.negocio.modelo.Folio folioEditado;
   
   //
   //
   gov.sir.core.negocio.modelo.Circulo circulo;
   

	public Evn_AdminCrearFolioSaveInfoOficio( String tipoEvento ) {
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


	public void setFolioEditado(Folio param_FolioEditado) {
		// TODO Auto-generated method stub
		folioEditado = param_FolioEditado;
		
	}

	public gov.sir.core.negocio.modelo.Folio getFolioEditado() {
		return folioEditado;
	}

	public gov.sir.core.negocio.modelo.Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(gov.sir.core.negocio.modelo.Circulo circulo) {
		this.circulo = circulo;
	}

}
