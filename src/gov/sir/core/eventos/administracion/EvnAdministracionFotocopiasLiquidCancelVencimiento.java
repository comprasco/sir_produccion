package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.TieneNotaInformativa;
import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Turno;

import gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.web.acciones.administracion.AWAdministracionFotocopiasLiquidCancelVencimientoItem;

import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.Date;

public class EvnAdministracionFotocopiasLiquidCancelVencimiento
extends EvnSIR
implements TieneNotaInformativa {
 
  protected Circulo circulo;

  protected String turnoId;
  protected Turno turno;
  protected Date referenceDate;

  private gov.sir.core.negocio.modelo.Usuario usuarioSIR;



  protected String sessionId; // para poder imprimir

    public static final String LISTAR = "LISTAR";
    public static final String ACCION_LISTAR = AWAdministracionFotocopiasLiquidCancelVencimiento.ACCION_LISTAR;
    public static final String ACCION_NEGAR =  AWAdministracionFotocopiasLiquidCancelVencimientoItem.ACCION_NEGAR;
   //EvnAdministracionFotocopiasLiquidCancelVencimientoItem

    public static final String VERDETALLES = "VERDETALLES";
    public static final String ACCION_VERDETALLES = "ACCION_VERDETALLES";


    public EvnAdministracionFotocopiasLiquidCancelVencimiento( Usuario usuario ){
        super(usuario);
    }

    public EvnAdministracionFotocopiasLiquidCancelVencimiento(Usuario usuario, String tipoEvento) {
        super(usuario,tipoEvento);
    }

    public EvnAdministracionFotocopiasLiquidCancelVencimiento(Usuario usuario, String tipoEvento, String turnoId ) {
        super(usuario,tipoEvento);
        this.turnoId = turnoId;
    }

    public EvnAdministracionFotocopiasLiquidCancelVencimiento(Usuario usuario, String tipoEvento, Turno turno ) {
        super(usuario,tipoEvento);
        this.turno = turno;
    }

    public Circulo getCirculo(){
      return this.circulo;
    }

  public String getTurnoId() {
    return turnoId;
  }

  public Turno getTurno() {
    return turno;
  }

  public String getSessionId() {
    return sessionId;
  }

  public Date getReferenceDate() {
    return referenceDate;
  }

  public void setCirculo( Circulo circulo ) {
      this.circulo = circulo;
    }

  public void setTurnoId( String turnoId ) {
    this.turnoId = turnoId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public void setReferenceDate(Date referenceDate) {
    this.referenceDate = referenceDate;
  }

  // overrides
  public Nota getNotaInf() {
    return null;
  }

  public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
	  return usuarioSIR;
  }
  public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
	  this.usuarioSIR = usuarioSIR;
  }

}
