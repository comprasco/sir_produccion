package gov.sir.core.eventos.correccion;

import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

import org.auriga.core.modelo.transferObjects.Estacion;

import java.util.List;

/**
 *
 */
public class EvnMsgMesaControl extends EvnCorreccion {

  private Turno turno;
  private String workflowMessageId;
  private Usuario usuarioTurno;
  
  private Estacion estacion;
  private String circuloId;
  private String impresora;
  
  private List folios;
  
  private Turno turnoPadre;

  public static final String EVNTYPE_MESACONTROL_ONOK
      = "EVNTYPE_MESACONTROL_ONOK";
  
  public static final String IMPRIMIR_MESA  = "IMPRIMIR_MESA";
  
  public static final String IMPRIMIR  = "IMPRIMIR";
  

  public List getFolios() {
	return folios;
}

public void setFolios(List folios) {
	this.folios = folios;
}

public Estacion getEstacion() {
	return estacion;
}

public void setEstacion(Estacion estacion) {
	this.estacion = estacion;
}

public EvnMsgMesaControl(
      org.auriga.core.modelo.transferObjects.Usuario usuario
    , String tipoEvento
  ) {

    super( usuario, tipoEvento );
  } //:EvnMsgEdicionCiudadanoSobreTodosFolios

  public Turno getTurno() {
     return turno;
  }

	public String getWorkflowMessageId() {
		return workflowMessageId;
	}

	public Usuario getUsuarioTurno() {
		return usuarioTurno;
	}

	public void setTurno( Turno newTurno ) {
     turno = newTurno;
  }

	public void setWorkflowMessageId(String workflowMessageId) {
		this.workflowMessageId = workflowMessageId;
	}

	public void setUsuarioTurno(Usuario usuarioTurno) {
		this.usuarioTurno = usuarioTurno;
	}

	public String getCirculoId() {
		return circuloId;
	}

	public void setCirculoId(String circuloId) {
		this.circuloId = circuloId;
	}

	public String getImpresora() {
		return impresora;
	}

	public void setImpresora(String impresora) {
		this.impresora = impresora;
	}
	
	public void setTurnoPadre(Turno turnop) {
		this.turnoPadre=turnop;
	}
	
	public Turno getTurnoPadre() {
		return this.turnoPadre;
	}


} // :class
