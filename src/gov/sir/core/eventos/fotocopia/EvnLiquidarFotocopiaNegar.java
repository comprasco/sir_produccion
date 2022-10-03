package gov.sir.core.eventos.fotocopia;

import gov.sir.core.negocio.modelo.Circulo;
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
public class EvnLiquidarFotocopiaNegar
extends EvnFotocopia {
  public EvnLiquidarFotocopiaNegar(
      org.auriga.core.modelo.transferObjects.Usuario usuario
    , gov.sir.core.negocio.modelo.Usuario usuarioSIR
    , Turno turno
    , Fase fase
    , String tipoAccion
    , String respuestaWf
  ) {
    super( usuario, usuarioSIR, turno, fase, tipoAccion, respuestaWf );
  }

  protected Circulo circulo;
  protected String sessionId;

  public String getRespuestaWf() {
    return super.getRespuestaWF();
  }
  public Turno getTurno() {
    return super.getTurno();
  }
  public Fase getFase() {
    return super.getFase();
  }

	public Circulo getCirculo() {
		return circulo;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
