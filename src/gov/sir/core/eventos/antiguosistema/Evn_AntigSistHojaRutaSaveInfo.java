package gov.sir.core.eventos.antiguosistema;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import java.util.Vector;

public class Evn_AntigSistHojaRutaSaveInfo extends EvnSIR {


   public static final String ANTIGSISTHOJARUTA_STEP0__EVENT
       	= "ANTIGSISTHOJARUTA_STEP0__EVENT";
   
   public static final String ANTIGSISTHOJARUTA_STEP1__EVENT 
   		= "ANTIGSISTHOJARUTA_STEP1__EVENT";

   public static final String ANTIGSISTHOJARUTA_STEP2__EVENT 
   		= "ANTIGSISTHOJARUTA_STEP2__EVENT";

   public static final String ANTIGSISTHOJARUTA_STEP3__EVENT 
		= "ANTIGSISTHOJARUTA_STEP3__EVENT";

   org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
   gov.sir.core.negocio.modelo.Usuario usuarioSir;
   gov.sir.core.negocio.modelo.Turno turno;

   //
   
   //
   gov.sir.core.negocio.modelo.Folio folioEditado;
   
   boolean crearFolio = false; // Indica si se esta haciendo la accion de crear el foli oen hoja de ruta
   
   Vector anotaciones;
   

	public Evn_AntigSistHojaRutaSaveInfo( String tipoEvento ) {
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

	public void setFolioEditado(Folio param_FolioEditado) {
		// TODO Auto-generated method stub
		folioEditado = param_FolioEditado;
		
	}

	public gov.sir.core.negocio.modelo.Folio getFolioEditado() {
		return folioEditado;
	}

	public Vector getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(Vector anotaciones) {
		this.anotaciones = anotaciones;
	}

	public boolean isCrearFolio() {
		return crearFolio;
	}

	public void setCrearFolio(boolean crearFolio) {
		this.crearFolio = crearFolio;
	}
}
