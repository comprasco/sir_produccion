package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

/**
 *
 */
public class EvnRespMsgMesaControl extends EvnSIRRespuesta {
	
  private Turno turno;
  private org.auriga.core.modelo.transferObjects.Usuario usuario;
  
  private List foliosNoImpresos; 

  public static final String EVNRESPOK_MESACONTROL_ONOK
      = "EVNRESPOK_MESACONTROL_ONOK";

  public static final String IMPRIMIR_MESA  = "IMPRIMIR_MESA";
  
  public EvnRespMsgMesaControl( String tipoEvento ) {

    super( null, tipoEvento );

  } // :EvnRespMsgEdicionCiudadanoSobreTodosFolios

public Turno getTurno() {
	return turno;
}

public void setTurno(Turno turno) {
	this.turno = turno;
}

public org.auriga.core.modelo.transferObjects.Usuario getUsuario() {
	return usuario;
}

public void setUsuario(org.auriga.core.modelo.transferObjects.Usuario usuario) {
	this.usuario = usuario;
}

public List getFoliosNoImpresos() {
	return foliosNoImpresos;
}

public void setFoliosNoImpresos(List foliosNoImpresos) {
	this.foliosNoImpresos = foliosNoImpresos;
}

  // tipoEvento: getTipoEvento

} // :class
