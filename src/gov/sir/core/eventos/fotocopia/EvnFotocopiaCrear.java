package gov.sir.core.eventos.fotocopia;

import gov.sir.core.negocio.modelo.SolicitudFotocopia;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Envío de solicitudes con respecto al proceso de Fotocopias, de la capa web
 * a la capa de negocio.
 * @author dlopez
 */
public class EvnFotocopiaCrear extends EvnFotocopia {

  protected String sessionId = "";

  public EvnFotocopiaCrear( Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, SolicitudFotocopia solicitud, /*LiquidacionTurnoFotocopia liquidacion,*/ String tipoAccion ) {
          super( usuario, usuarioSIR, solicitud, tipoAccion );
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  //public void aaa() {
  //  this.setS
  //
  //}


}
