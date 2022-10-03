package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

/**
 * immutable object
 * @author
 */
public class EvnRespAdministracionFotocopiasLiquidCancelVencimiento extends EvnSIRRespuesta {

  public static final String RESULTADO_LISTAR_OK = "RESULTADO_LISTAR_OK";
  public static final String RESULTADO_VERDETALLES_OK = "RESULTADO_VERDETALLES_OK";

  private List turnosFotocopiasConPagoVencido;
  private List tiposNotasInformativas;
  private Turno turno;
  private Fase fase;
  private Proceso proceso;

  private double initialThreshold;
  
  private String dateString;

  public EvnRespAdministracionFotocopiasLiquidCancelVencimiento(Pago pago, List turnosFotocopiasConPagoVencido, String eventName ) {
        super(pago,eventName);
        this.turnosFotocopiasConPagoVencido = turnosFotocopiasConPagoVencido;
  }

  public EvnRespAdministracionFotocopiasLiquidCancelVencimiento( Turno turno, String eventName ) {
     super(null, eventName );
     this.turno = turno;
  }
  public EvnRespAdministracionFotocopiasLiquidCancelVencimiento( Turno turno, Fase fase, Proceso proceso, String eventName ) {
     super(null, eventName );
     this.turno = turno;
     this.fase = fase;
     this.proceso = proceso;
  }


  public List getTurnosFotocopiasConPagoVencido() {
    return turnosFotocopiasConPagoVencido;
  }

  public Turno getTurno() {
    return turno;
  }

  public Fase getFase() {
    return fase;
  }

  public Proceso getProceso() {
    return proceso;
  }

  public List getTiposNotasInformativas() {
    return tiposNotasInformativas;
  }

  public double getInitialThreshold() {
    return initialThreshold;
  }

	public void setTiposNotasInformativas(List tiposNotasInformativas) {
    this.tiposNotasInformativas = tiposNotasInformativas;
  }

  public void setInitialThreshold(double initialThreshold) {
    this.initialThreshold = initialThreshold;
  }

public String getDateString() {
	return dateString;
}

public void setDateString(String dateString) {
	this.dateString = dateString;
}


}
