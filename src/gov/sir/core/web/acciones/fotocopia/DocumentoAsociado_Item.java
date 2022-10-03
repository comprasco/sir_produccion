package gov.sir.core.web.acciones.fotocopia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class DocumentoAsociado_Item implements Serializable {

  private String  tipoDocumento_Id;
  private String  numCopias;
  private String  descripcion;

  private String numHojas;
  private String tipoFotocopia_Id;

  public void setTipoDocumento_Id( String tipoDocumento_Id ) {
    this.tipoDocumento_Id = tipoDocumento_Id;
  }
  public void setNumCopias( String numCopias ) {
    this.numCopias = numCopias;
  }
  public void setDescripcion( String descripcion ) {
    this.descripcion = descripcion;
  }

  public void setNumHojas(String numHojas) {
    this.numHojas = numHojas;
  }

  public void setTipoFotocopia_Id(String tipoFotocopia_Id) {
    this.tipoFotocopia_Id = tipoFotocopia_Id;
  }

  public String getTipoDocumento_Id() {
    return this.tipoDocumento_Id ;
  }
  public String getNumCopias() {
    return this.numCopias;
  }
  public String getDescripcion() {
    return this.descripcion;
  }

  public String getNumHojas() {
    return numHojas;
  }

  public String getTipoFotocopia_Id() {
    return tipoFotocopia_Id;
  }

  public DocumentoAsociado_Item() {

  }


	public static void main( String[] args ) {
		List documentosAsociados = new ArrayList();
		documentosAsociados.add( new DocumentoAsociado_Item() ) ;
		documentosAsociados.add( new DocumentoAsociado_Item() ) ;

		DocumentoAsociado_Item[] tempArray = (DocumentoAsociado_Item[])documentosAsociados.toArray() ;

	}

}
