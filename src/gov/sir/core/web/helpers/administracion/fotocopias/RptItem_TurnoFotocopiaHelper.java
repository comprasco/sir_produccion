/*
 * Created on 03-ago-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.administracion.fotocopias;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.negocio.modelo.Imagen;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.comun.TurnosHelper;
import gov.sir.core.negocio.modelo.Turno;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author dsalas, mrios
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RptItem_TurnoFotocopiaHelper
    extends TurnosHelper {

  public static String TURNO_FOTOCOPIA_ATT = "TURNO_FOTOCOPIA_ATT";

  protected List turnoFotocopiaList;

  public RptItem_TurnoFotocopiaHelper() {
    super();
  }

  protected void  unwrapRequest(HttpServletRequest request) {
    turnoFotocopiaList = (List) request.getAttribute(TURNO_FOTOCOPIA_ATT);
  }

  public void drawGUI(HttpServletRequest request, JspWriter out) throws
      IOException, HelperException {

    unwrapRequest(request);
    StringBuffer htmlBuffer;
    processDocument( turnoFotocopiaList, out );

  }

  protected void processDocument( List list, JspWriter writer ) throws IOException {

    if( null == list ) {
      return;
    }

    writer.write("<table>");

    Iterator iterator = list.iterator();
    while (iterator.hasNext()) {
      Turno turno = (Turno) iterator.next();

      StringBuffer tempBuffer;
      tempBuffer = processItem(turno);

      StringBuffer htmlBuffer = new StringBuffer();
      htmlBuffer.append(" <tr>");
      htmlBuffer.append( tempBuffer );
      htmlBuffer.append(" </tr>");

      writer.write( htmlBuffer.toString() );

    }

    writer.write(" </table>");

  }

  protected StringBuffer processItem(Turno turno) {
    StringBuffer result = new StringBuffer();
    result.append("<td>" + turno.getIdTurno() + "</td>");
    result.append("<td>" + turno.getIdFase() + "</td>");
    return result;
  }

}

class Temp1_THelper
    extends Helper {
  /**
   * Ancho de la tabla
   */
  private String twidth;

  /**
   * Estilo de la tabla
   */
  private String ccs;

  /**
   * Nombre de la función de javascript que se encarga de eliminar el dato
   */
  private String nombreFuncionEliminar;

  /**
   * Nombre de la función de javascript que se encarga de solicitar el dato
   */
  private String nombreFuncionSolicitar;

  /**
   * Ancho de las columnas
   * Vector de String
   */
  private Vector colWidth;

  /**
   * Datos
   * Vector de Vector de String
   */
  private Vector data;

  /**
   * Estilos de las columnas
   * Vector de String
   */
  private Vector classes;

  private Vector cellAlign;
  private Vector imagenes;
  private List alt;
  private int colCount;
  private int rowCount;
  private String listName = "";

  public static final int CELDA_PLAIN = 0;
  public static final int CELDA_IMAGEN = 1;
  public static final int CELDA_CHECKBOX = 2;
  public static final int CELDA_RADIO = 3;
  private int contenidoCelda = CELDA_PLAIN;

  /**
   * Crea un table helper
   */
  public Temp1_THelper() {
    super();
    alt = new ArrayList();

    nombreFuncionEliminar = "quitar";
    nombreFuncionSolicitar = "solicitar";
  }

  public void setColCount(int colCount) {
    this.colCount = colCount;
  }

  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public void setContenidoCelda(int contenido) {
    this.contenidoCelda = contenido;
  }

  public void setAlt(List alt) {
    this.alt = alt;
  }

  public void inicializar() {
    //1.LLENAR DATA
    data = new Vector();
    for (int i = 0; i < rowCount; i++) {
      //1.1 CREAR FILAS
      Vector auxRow = new Vector();
      for (int j = 0; j < colCount; j++) {
        //1.1.1 LLENAR FILA CON CELDAS VACIAS
        auxRow.addElement("");
      }
      data.addElement(auxRow);
    }
    //2.LLENAR WIDTHS
    colWidth = new Vector();
    for (int j = 0; j < colCount; j++) {
      colWidth.addElement(String.valueOf(100 / colCount) + "%");
    }
    //3.LLENAR CLASSES
    classes = new Vector();
    for (int j = 0; j < colCount; j++) {
      classes.addElement("");
    }
    //4.DATOS SIMPLES
    twidth = "100%";
    ccs = "";
    cellAlign = new Vector();
  }

  /**
   * @see org.auriga.core.web.Helper#Properties(javax.servlet.http.HttpServletRequest)
   */
  public void setProperties(HttpServletRequest request) {
    HttpSession session = request.getSession();

    if (session.getAttribute(this.listName) != null) {
      List listData = (List) session.getAttribute(this.listName);
      int rows = 1;
      rows = listData.size() / this.colCount;

      if (listData.size() % this.colCount != 0) {
        rows++;
      }

      String strAlt = "";
      this.rowCount = rows;
      this.inicializar();
      classes = new Vector();

      for (int i = 0, k = 0; i < rows; i++) {
        for (int j = 0; j < this.colCount; j++) {
          if (k < listData.size()) {
            classes.add("campositem");
            cellAlign.add("center");

            if (k < alt.size()) {
              strAlt = (String) alt.get(k);
            }

            switch (this.contenidoCelda) {
              case CELDA_IMAGEN:
                this.setValueWithImage(
                    WebKeys.TITULO_CHECKBOX_ELIMINAR,
                    "30",
                    "20",
                    "0",
                    nombreFuncionEliminar,
                    j,
                    i,
                    strAlt,
                    (String) listData.get(k));
                break;

              case CELDA_CHECKBOX:
                this.setValueWithCheckBox(
                    WebKeys.TITULO_CHECKBOX_ELIMINAR,
                    "30",
                    "20",
                    "0",
                    nombreFuncionEliminar,
                    j,
                    i,
                    strAlt,
                    (String) listData.get(k));
                break;

              case CELDA_RADIO:
                this.setValueWithRadioButton(
                    WebKeys.TITULO_CHECKBOX_ELIMINAR,
                    "30",
                    "20",
                    "0",
                    nombreFuncionSolicitar,
                    j,
                    i,
                    strAlt,
                    (String) listData.get(k));
                break;

              default:
                this.setValue(j, i, (String) listData.get(k));
                break;
            }

            k++;
          }
        }
      }
    }
  }

  /**
   * Funcion para obtener la lista de anchos
   * @return Vector de String con los anchos de las columnas
   */
  public Vector getWidths() {
    return colWidth;
  }

  /**
   * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
   */
  public void drawGUI(HttpServletRequest request, JspWriter out) throws
      IOException, HelperException {

    if (data != null) {
      String tablaOut = "";

      tablaOut = tablaOut + "<table width=\"" + twidth + "\" class=\"" + ccs +
          "\">";
      //RECORRER FILAS
      int k = 0;
      for (int i = 0; i < data.size(); i++) {
        tablaOut = tablaOut + "<tr>";
        //
        Vector auxRow = (Vector) data.elementAt(i);
        //RECORRER CELDAS
        for (int j = 0; j < auxRow.size(); j++) {
          String auxWidth = (String) colWidth.elementAt(j);
          String auxData = (String) auxRow.elementAt(j);
          String classe = "";
          String align = "left";

          if (j < classes.size() && !auxData.equals("")) {
            classe = (String) classes.elementAt(j);
          }

          if (j < cellAlign.size()) {
            align = (String) cellAlign.elementAt(j);
          }

          tablaOut = tablaOut +
              "<td width=\"" + auxWidth + "\" align=\"" + align + "\" class=\"" +
              classe + "\">";
          tablaOut = tablaOut + auxData;

          if (!auxData.equals("")) {
            if (this.imagenes != null && this.imagenes.size() > 0 &&
                k < imagenes.size()) {
              try {
                Imagen img = (Imagen) imagenes.get(k);
                if (img != null && img.getNombre() != null &&
                    !img.getNombre().equals("")) {
                  tablaOut = tablaOut + "&nbsp;&nbsp;&nbsp;<img src='" +
                      request.getContextPath() + "/jsp/images/" + img.getNombre() +
                      "' width='" + img.getWidth() + "' height='" +
                      img.getHeight() + "' onclick='" + img.getFuncion() +
                      "' >";
                }
              }
              catch (Exception e) {
              }
            }
          }

          tablaOut = tablaOut + "</td>";
          k++;
        }
        //
        tablaOut = tablaOut + "</tr>";
      }
      tablaOut = tablaOut + "</table>";

      out.write(tablaOut);
    }

  }

  /**
   * @param string
   */
  public void setCcs(String string) {
    ccs = string;
  }

  /**
   * @param string
   */
  public void setTwidth(String string) {
    twidth = string;
  }

  /**
   *
   * @param cell
   * @param classe
   * @param width
   * @param col 0-based
   */
  public void setCol(String cell, String classe, String width, int col) {
    //SI LA COLUMNA SOLICITADA ESTA EN EL RANGO
    if (col >= 0 && col < colWidth.size()) {
      //1.SETEAR DATOS DE LA COLUMNA
      //1.1.RECORRER FILAS
      for (int i = 0; i < data.size(); i++) {
        //1.2 OBTENER LA FILA
        Vector auxRow = (Vector) data.elementAt(i);
        //1.3 SETEAR EL DATO
        auxRow.set(col, cell);
      }
      //2.SETEAR ESTILOS
      colWidth.set(col, width);
      //3.SETEAR ANCHOS
      classes.set(col, classe);
    }
  }

  /**
   * @param col
   * @param row
   * @param data
   */
  public void setValue(int col, int row, String data) {
    if ( (col >= 0 && col < colWidth.size()) &&
        (row >= 0 && row < this.data.size())) {
      ( (Vector)this.data.elementAt(row)).set(col, data);
    }
  }

  /**
   * Método para fijar el ancho de una columna
   * @param col columna a setear
   * @param width ancho de la columna
   */
  public void setColWidth(int col, String width) {
    if (col >= 0 && col < colCount && col < colWidth.size()) {
      colWidth.set(col, width);
    }
  }

  /**
   *
   * @param image path de la imagen
   * @param width ancho de la imagen
   * @param height alto de la imagen
   * @param border borde de la imagen
   * @param function funcion que se llamara al hacer click en la imagen
   * @param colParam columna de la que se obtiene en parametro de la funcion
   * @param col columna en la que se coloca el boton-imagen
   */
  public void setColImagen(
      String image,
      String width,
      String height,
      String border,
      String function,
      int colParam,
      int col) {

    if ( (col >= 0 && col < colWidth.size()) &&
        (colParam >= 0 && colParam < colWidth.size())) {
      //RECORRER FILAS
      for (int i = 0; i < data.size(); i++) {
        //OBTENER FILA
        Vector auxRow = (Vector) data.elementAt(i);
        //LEER PARAMETRO DE LA FUNCION
        String param = (String) auxRow.elementAt(colParam);
        //ARMAR STRING DE LA CELDA
        String cell =
            "<img "
            + "src=\""
            + image
            + "\" "
            + "width=\""
            + width
            + "\" "
            + "height=\""
            + height
            + "\" "
            + "border=\""
            + border
            + "\" "
            + "onClick=\""
            + function
            + "('"
            + param
            + "')\">";
        //SETEAR VALOR
        setValue(col, i, cell);
      }

    }

  }

  /**
   *
   * @param image path de la imagen
   * @param width ancho de la imagen
   * @param height alto de la imagen
   * @param border borde de la imagen
   * @param function funcion que se llamara al oprimir el boton
   * @param colParam columna de la que se obtiene en parametro de la funcion
   * @param col columna en la que se coloca el boton-imagen
   */
  public void setColButton(
      String image,
      String width,
      String height,
      String border,
      String function,
      int colParam,
      int col) {

    if ( (col >= 0 && col < colWidth.size()) &&
        (colParam >= 0 && colParam < colWidth.size())) {
      //RECORRER FILAS
      for (int i = 0; i < data.size(); i++) {
        //OBTENER FILA
        Vector auxRow = (Vector) data.elementAt(i);
        //LEER PARAMETRO DE LA FUNCION
        String param = (String) auxRow.elementAt(colParam);
        //ARMAR STRING DE LA CELDA
        String cell =
            "<input type=\"image\" name=\"image\" "
            + "src=\""
            + image
            + "\" "
            + "width=\""
            + width
            + "\" "
            + "height=\""
            + height
            + "\" "
            + "border=\""
            + border
            + "\" "
            + "onClick=\""
            + function
            + "('"
            + param
            + "')\">";
        //SETEAR VALOR
        setValue(col, i, cell);
      }

    }

  }

  public void setValueWithImage(
      String image,
      String width,
      String height,
      String border,
      String function,
      int col,
      int row,
      String alt,
      String value) {
    if ( (col >= 0 && col < colWidth.size()) &&
        (row >= 0 && row < this.data.size())) {
      String cell =
          value
          + " <input type=\"image\" name=\"image\" "
          + "src=\""
          + image
          + "\" "
          + "width=\""
          + width
          + "\" "
          + "height=\""
          + height
          + "\" "
          + "border=\""
          + border
          + "\" "
          + "alt=\""
          + alt
          + "\" "
          + "onClick=\""
          + function
          + "('"
          + value
          + "')\">";
      //SETEAR VALOR
      setValue(col, row, cell);
    }
  }

  public void setValueWithCheckBox(
      String name,
      String width,
      String height,
      String border,
      String function,
      int col,
      int row,
      String title,
      String value) {
    if ( (col >= 0 && col < colWidth.size()) &&
        (row >= 0 && row < this.data.size())) {
      String cell =
          "<input type=\"checkbox\" name=\""
          + name
          + "\" "
          + "title=\""
          + title
          + "\" "
          + "value=\""
          + value
          + "\"> "
          + value;
      //SETEAR VALOR
      setValue(col, row, cell);
    }
  }

  public void setValueWithRadioButton(
      String name,
      String width,
      String height,
      String border,
      String function,
      int col,
      int row,
      String title,
      String value) {
    if ( (col >= 0 && col < colWidth.size()) &&
        (row >= 0 && row < this.data.size())) {
      String cell =
          "<input type=\"radio\" name=\""
          + name
          + "\" "
          + "title=\""
          + title
          + "\" "
          + "value=\""
          + value
          + "\"> "
          + value;
      //SETEAR VALOR
      setValue(col, row, cell);
    }
  }

  public void setClasses(Vector v) {
    this.classes = v;
  }

  public void setCellAlign(Vector v) {
    this.cellAlign = v;
  }

  /**
   * @return
   */
  public String getNombreFuncionEliminar() {
    return nombreFuncionEliminar;
  }

  /**
   * @param string
   */
  public void setNombreFuncionEliminar(String string) {
    nombreFuncionEliminar = string;
  }

  /**
   * @return
   */
  public Vector getImagenes() {
    return imagenes;
  }

  /**
   * @param vector
   */
  public void setImagenes(Vector vector) {
    imagenes = vector;
  }

  public String toString() {

    String resultado = "";

    for (int i = 0; i < rowCount; i++) {
      Vector row = (Vector) data.get(i);
      for (int j = 0; j < colCount; j++) {
        String cell = (String) row.get(j);
        resultado = resultado + cell + "\t";
      }
      resultado = resultado + "\n";
    }
    return resultado;
  }
}
