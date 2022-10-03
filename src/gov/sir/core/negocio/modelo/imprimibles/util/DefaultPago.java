package gov.sir.core.negocio.modelo.imprimibles.util;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Turno;

public class DefaultPago
    extends Pago {

  public DefaultPago( Liquidacion liquidacion  ) {
    super( liquidacion, null );
  }
  public DefaultPago( Liquidacion liquidacion, Turno turno ) {
    this( liquidacion );
    liquidacion.getSolicitud().setTurno( turno );
  }
}
