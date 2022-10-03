package gov.sir.core.eventos.fotocopia;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

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
public class EvnVerificarDocumentosFotocopia
    extends EvnFotocopia {

//  private Turno turno;
private String sessionId;

//  public EvnVerificarDocumentosFotocopia( Turno turno, Fase fase, Usuario usuario, SolicitudFotocopia solicitud, String tipoAccion ) {
//    super( usuario, solicitud, tipoAccion );
// }
  public EvnVerificarDocumentosFotocopia( Usuario usuario, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, Fase fase, String tipoAccion, String sessionId, String respuestaWF ) {
    super( usuario, usuarioSIR, turno, fase, tipoAccion, respuestaWF );
    this.sessionId = sessionId;
  }

  public SolicitudFotocopia getSolicitudFotocopia() {
    SolicitudFotocopia solicitud = (SolicitudFotocopia) super.getTurno().getSolicitud();
    return solicitud;
  }

  public String getSessionId() {
    return sessionId;
  }

  //  public EvnVerificarDocumentosFotocopia( Usuario usuario, SolicitudFotocopia solicitud, String tipoAccion ) {
//    super( usuario, solicitud, tipoAccion );
//  }

}
