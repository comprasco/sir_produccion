package gov.sir.core.eventos.fotocopia;

import org.auriga.core.modelo.transferObjects.Usuario;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Turno;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

// inicialmente planeado para ser inmodificable
public class EvnPagoFotocopiaZeroValue
extends EvnPagoFotocopia {
  public EvnPagoFotocopiaZeroValue( Usuario usuario, Turno turno, Fase fase, String tipoAccion, String respuestaWf ) {
    super( usuario, turno, fase, tipoAccion, respuestaWf );
  }

  public String getRespuestaWf() {
    return super.getRespuestaWF();
  }
  public Turno getTurno() {
    return super.getTurno();
  }
  public Fase getFase() {
    return super.getFase();
  }


}
