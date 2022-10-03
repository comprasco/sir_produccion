package gov.sir.core.eventos.antiguosistema;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import java.util.Vector;

public class EvnResp_AntigSistHojaRutaSaveInfo extends EvnSIRRespuesta {
	
   public static final String ANTIGSISTHOJARUTA_STEP0__EVENTRESP_OK
       	= "ANTIGSISTHOJARUTA_STEP0__EVENT";
   
   public static final String ANTIGSISTHOJARUTA_STEP1__EVENTRESP_OK
   		= "ANTIGSISTHOJARUTA_STEP1__EVENT";

   public static final String ANTIGSISTHOJARUTA_STEP2__EVENTRESP_OK
   		= "ANTIGSISTHOJARUTA_STEP2__EVENT";

   public static final String ANTIGSISTHOJARUTA_STEP3__EVENTRESP_OK
		= "ANTIGSISTHOJARUTA_STEP3__EVENT";
   
   Folio folioEditado;
   Turno turno;
   
   Vector anotaciones;
   
   boolean crearFolio = false; // Indica si se esta haciendo la accion de crear el folio en hoja de ruta
	
	public EvnResp_AntigSistHojaRutaSaveInfo( String tipoEvento ) {
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
