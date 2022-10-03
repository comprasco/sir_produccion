package gov.sir.core.eventos.correccion;

import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

/**
 *
 */
public class
EvnMsgEdicionCiudadanoSobreTodosFolios
extends EvnCorreccion {

  public static final String EVNTYPE_EDITCIUDADANO_ONLOAD
      = "EVNTYPE_EDITCIUDADANO_ONLOAD";

  public static final String EVNTYPE_EDITCIUDADANO_ONLOCKFOLIOS
      = "EVNTYPE_EDITCIUDADANO_ONLOCKFOLIOS";

  public static final String EVNTYPE_EDITCIUDADANO_ONAPPLY
      = "EVNTYPE_EDITCIUDADANO_ONAPPLY";


  private Turno turno;
  
  public EvnMsgEdicionCiudadanoSobreTodosFolios(
      org.auriga.core.modelo.transferObjects.Usuario usuario
    , String tipoEvento
  ) {

    super( usuario, tipoEvento );
  } //:EvnMsgEdicionCiudadanoSobreTodosFolios

  public String getItemToFind_CiudadanoId() {
    return itemToFind_CiudadanoId;
  }

  public void setItemToFind_CiudadanoId(String itemToFind_CiudadanoId) {
    this.itemToFind_CiudadanoId = itemToFind_CiudadanoId;
  }

  public void setItemToFind_Usuario(gov.sir.core.negocio.modelo.Usuario newItemToFind_Usuario ) {
    itemToFind_Usuario = newItemToFind_Usuario;
  }


  public gov.sir.core.negocio.modelo.Usuario getItemToFind_Usuario( ) {
    return itemToFind_Usuario;
  }


  public void setFoliosToBlock_CiudadanoId(String newFoliosToBlock_CiudadanoId) {
    foliosToBlock_CiudadanoId = newFoliosToBlock_CiudadanoId;
  }
  public String getFoliosToBlock_CiudadanoId() {
    return foliosToBlock_CiudadanoId;
  }

  public void setFoliosToBlock_FoliosList( List newFoliosToBlock_FoliosList ) {
    foliosToBlock_FoliosList = newFoliosToBlock_FoliosList;
  }
  public void setFoliosToBlock_Usuario( gov.sir.core.negocio.modelo.Usuario newFoliosToBlock_Usuario ) {
    foliosToBlock_Usuario = newFoliosToBlock_Usuario;
  }
  public gov.sir.core.negocio.modelo.Usuario getFoliosToBlock_Usuario() {
    return foliosToBlock_Usuario;
  }

  public gov.sir.core.negocio.modelo.Turno getFoliosToBlock_Turno() {
    return foliosToBlock_Turno;
  }
  public void setFoliosToBlock_Turno( gov.sir.core.negocio.modelo.Turno newFoliosToBlock_Turno ) {
    foliosToBlock_Turno = newFoliosToBlock_Turno;
  }

  public void setCiudadanoToUpdate_Ciudadano_t2( gov.sir.core.negocio.modelo.Ciudadano newCiudadanoToUpdate_Ciudadano_t2 ) {
    ciudadanoToUpdate_Ciudadano_t2 = newCiudadanoToUpdate_Ciudadano_t2;
  }
  public void setCiudadanoToUpdate_Usuario(gov.sir.core.negocio.modelo.Usuario newCiudadanoToUpdate_Usuario ) {
    ciudadanoToUpdate_Usuario = newCiudadanoToUpdate_Usuario;
  }
  public void setCiudadanoToUpdate_SalvedadTx( String newCiudadanoToUpdate_SalvedadTx ) {
    ciudadanoToUpdate_SalvedadTx = newCiudadanoToUpdate_SalvedadTx;
  }
  public String getCiudadanoToUpdate_SalvedadTx() {
    return ciudadanoToUpdate_SalvedadTx;
  }


  public gov.sir.core.negocio.modelo.Ciudadano getCiudadanoToUpdate_Ciudadano_t2( ) {
    return ciudadanoToUpdate_Ciudadano_t2;
  }
  public gov.sir.core.negocio.modelo.Usuario getCiudadanoToUpdate_Usuario() {
    return ciudadanoToUpdate_Usuario;
  }


  private List foliosToBlock_FoliosList;
  private gov.sir.core.negocio.modelo.Usuario foliosToBlock_Usuario;
  private gov.sir.core.negocio.modelo.Turno foliosToBlock_Turno;

  private gov.sir.core.negocio.modelo.Ciudadano ciudadanoToUpdate_Ciudadano_t2;
  private gov.sir.core.negocio.modelo.Usuario ciudadanoToUpdate_Usuario;
  private String ciudadanoToUpdate_SalvedadTx;

  private String itemToFind_CiudadanoId;
  private gov.sir.core.negocio.modelo.Usuario itemToFind_Usuario; // usuario que mantiene los cambios


  private String foliosToBlock_CiudadanoId;

public Turno getTurno() {
	return turno;
}

public void setTurno(Turno turno) {
	this.turno = turno;
}



} // :class
