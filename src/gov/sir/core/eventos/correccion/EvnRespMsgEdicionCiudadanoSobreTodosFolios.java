package gov.sir.core.eventos.correccion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Ciudadano;

import java.util.List;

/**
 * Envio de solicitudes de respuesta con respecto al proceso de correcciones, de la capa de negocio a la capa web.
 * @author ppabon
 */
public class EvnRespMsgEdicionCiudadanoSobreTodosFolios extends EvnSIRRespuesta {

  public static final String EVNRESPOK_EDITCIUDADANO_ONLOAD
      = "EVNRESP_EDITCIUDADANO_ONLOAD";

  public static final String EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS
      = "EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS";
  public static final String EVNRESPOK_EDITCIUDADANO_ONAPPLY
      = "EVNRESPOK_EDITCIUDADANO_ONAPPLY";


  public EvnRespMsgEdicionCiudadanoSobreTodosFolios( String tipoEvento ) {

    super( null, tipoEvento );

  } // :EvnRespMsgEdicionCiudadanoSobreTodosFolios

  public Ciudadano getCiudadano() {
    return ciudadano;
  }

  public List getListFolios() {
    return listFolios;
  }

  public List getListUsuarioBloqueo() {
    return listUsuarioBloqueo;
  }

  public void setCiudadano(Ciudadano ciudadano) {
    this.ciudadano = ciudadano;
  }

  public void setListFolios(List listFolios) {
    this.listFolios = listFolios;
  }

  public void setListUsuarioBloqueo(List listUsuarioBloqueo) {
    this.listUsuarioBloqueo = listUsuarioBloqueo;
  }

  public void setVerificacionBloqueoFolios( boolean newVerificacionBloqueoFolios ) {
    verificacionBloqueoFolios = newVerificacionBloqueoFolios;
  }
  public boolean getVerificacionBloqueoFolios() {
    return verificacionBloqueoFolios;
  }


  public void setRespuestaActualizacion( boolean newRespuestaActualizacion ) {
    respuestaActualizacion = newRespuestaActualizacion;
  }
  public boolean getRespuestaActualizacion() {
    return respuestaActualizacion;
  }


  private gov.sir.core.negocio.modelo.Ciudadano ciudadano;
  // lista de folios con anotaciones en las cuales aparece el ciudadano
  private java.util.List listFolios;
  // lista de bloqueos de cada uno de los folios.
  private java.util.List listUsuarioBloqueo;

  // flag de verificacion de todos los bloqueos
  private boolean verificacionBloqueoFolios;

  // flag de verificacion de actualizacion
  private boolean respuestaActualizacion;


  // tipoEvento: getTipoEvento

} // :class
