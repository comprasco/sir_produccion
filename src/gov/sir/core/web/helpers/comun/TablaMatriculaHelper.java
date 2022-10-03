package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Imagen;
import gov.sir.core.web.WebKeys;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author dsalas, mrios
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TablaMatriculaHelper extends Helper {

	public final static String ALINEACION_IZQUIERDA="left";
	public final static String ALINEACION_DERECHA="right";
	public final static String ALINEACION_CENTRO="center";

    protected boolean selectedValues_Enabled = false;
    protected java.util.List selectedValues_Ids;
    protected String selectedValues_Ids_Key = "DEFAULT_SELECTED_VALUES_IDS_KEY";
        
    protected boolean selectedValue_Enabled = true;
    protected Object selectedValue_Id;
    protected String selectedValue_Id_Key = "DEFAULT_SELECTED_VALUE_ID_KEY";

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
	private String alineacionTodasCeldas;
	private Vector imagenes;
	private List alt;
	private int colCount;
	private int rowCount;
	private String listName = "";
	private String inputName = WebKeys.TITULO_CHECKBOX_ELIMINAR;

	public static final int CELDA_PLAIN = 0;
	public static final int CELDA_IMAGEN = 1;
	public static final int CELDA_CHECKBOX = 2;
	public static final int CELDA_RADIO = 3;
	public static final int CELDA_CHECKBOXTEXTFIELD = 4;
	private int contenidoCelda = CELDA_PLAIN;

	/**
	 * Crea un table helper
	 */
	public TablaMatriculaHelper() {
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
		for (int j = 0; j < colCount; j++)
			colWidth.addElement(String.valueOf(100 / colCount) + "%");
		//3.LLENAR CLASSES
		classes = new Vector();
		for (int j = 0; j < colCount; j++)
			classes.addElement("");
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
        
        // load selectedValues data
        if( isSelectedValues_Enabled() ) {
            selectedValues_Ids = (java.util.List)session.getAttribute( selectedValues_Ids_Key );
            if( null == selectedValues_Ids )
                selectedValues_Ids = new java.util.Vector();
        }
        
        if( isSelectedValue_Enabled() ) {
            selectedValue_Id = session.getAttribute( selectedValue_Id_Key );
            if( null == selectedValue_Id )
                selectedValue_Id = "";
        }

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
			classes=new Vector();

			for (int i = 0, k = 0; i < rows; i++) {
				for (int j = 0; j < this.colCount; j++) {
					if (k < listData.size()) {
						classes.add("campositem");

						if (alineacionTodasCeldas==null){
							alineacionTodasCeldas=ALINEACION_CENTRO;
						}
						cellAlign.add(alineacionTodasCeldas);

						if (k < alt.size()) {
							strAlt = (String) alt.get(k);
						}
						Object temp =(Object)listData.get(k) ;
						String valorInput="";
						String labelInput="";
						if(temp instanceof String){
							valorInput=(String)temp;
							labelInput=(String)temp;
						}else if(temp instanceof ElementoLista){
							ElementoLista elem= (ElementoLista)temp;
							valorInput=elem.getId();
							labelInput=elem.getValor();
						}
						switch (this.contenidoCelda) {
							case CELDA_IMAGEN :
								this.setValueWithImage(
									this.inputName,
									"30",
									"20",
									"0",
									nombreFuncionEliminar,
									j,
									i,
									strAlt,
									valorInput,
									labelInput);
								break;

							case CELDA_CHECKBOX :
								this.setValueWithCheckBox(
									this.inputName,
									"30",
									"20",
									"0",
									nombreFuncionEliminar,
									j,
									i,
									strAlt,
									valorInput,
									labelInput);
								break;

							case CELDA_RADIO :
								this.setValueWithRadioButton(
									this.inputName,
									"30",
									"20",
									"0",
									nombreFuncionSolicitar,
									j,
									i,
									strAlt,
									valorInput,
									labelInput);
								break;
								
							case CELDA_CHECKBOXTEXTFIELD:
								this.setValueWithCheckBoxText(
										this.inputName,
										"30",
										"20",
										"0",
										nombreFuncionSolicitar,
										j,
										i,
										strAlt,
										valorInput,
										labelInput);
								break;

							default :
								this.setValue(j, i, labelInput);
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
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		if (data != null) {
			StringBuffer tablaOut = new StringBuffer("");

			tablaOut.append("<table width=\"");
			tablaOut.append(twidth);
			tablaOut.append("\" class=\"");
			tablaOut.append(ccs);
			tablaOut.append("\">");

			out.write(tablaOut.toString());

			//RECORRER FILAS
			int k = 0;
			for (int i = 0; i < data.size(); i++) {
				out.write("<tr>");
				//
				Vector auxRow = (Vector) data.elementAt(i);
				//RECORRER CELDAS
				for (int j = 0; j < auxRow.size(); j++) {

					String auxWidth = (String) colWidth.elementAt(j);
					String auxData = (String) auxRow.elementAt(j);
					String classe = "";
					String align = "left";

					tablaOut = new StringBuffer("");

					if (j < classes.size() && !auxData.equals("")) {
						classe = (String) classes.elementAt(j);
					}

					if (j < cellAlign.size()) {
						align = (String) cellAlign.elementAt(j);
					}

					tablaOut.append("<td width=\"");
					tablaOut.append(auxWidth);
					tablaOut.append("\" align=\"");
					tablaOut.append(align);
					tablaOut.append("\" class=\"");
					tablaOut.append(classe);
					tablaOut.append("\">");
					tablaOut.append(auxData);

					/*tablaOut=tablaOut+
						"<td width=\"" + auxWidth + "\" align=\"" + align + "\" class=\"" + classe + "\">";
					tablaOut=tablaOut+auxData;*/

					if(!auxData.equals("")){
						if(this.imagenes!=null && this.imagenes.size()>0 && k<imagenes.size()){
							try{
								Imagen img = (Imagen)imagenes.get(k);
								if(img!=null && img.getNombre()!=null && !img.getNombre().equals("")){
									tablaOut.append("&nbsp;&nbsp;&nbsp;<img src='");
									tablaOut.append(request.getContextPath());
									tablaOut.append("/jsp/images/");
									tablaOut.append(img.getNombre());
									tablaOut.append("' width='");
									tablaOut.append(img.getWidth());
									tablaOut.append("' height='");
									tablaOut.append(img.getHeight());
									tablaOut.append("' onClick=\"");
									tablaOut.append(img.getFuncion());
									tablaOut.append("\" >");
								}
							}catch(Exception e){
							}
						}
					}

					tablaOut.append("</td>");
					out.write(tablaOut.toString());
					k++;
				}
				//
				out.write("</tr>");
			}
			out.write("</table>");
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
		if ((col >= 0 && col < colWidth.size()) && (row >= 0 && row < this.data.size())) {
			((Vector) this.data.elementAt(row)).set(col, data);
		}
	}

	/**
	 * Método para fijar el ancho de una columna
	 * @param col columna a setear
	 * @param width ancho de la columna
	 */
	public void setColWidth(int col, String width) {
		if(col>=0 && col<colCount && col<colWidth.size())
			colWidth.set(col,width);
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
		int col){

		if ((col >= 0 && col < colWidth.size()) && (colParam >= 0 && colParam < colWidth.size())) {
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

		if ((col >= 0 && col < colWidth.size()) && (colParam >= 0 && colParam < colWidth.size())) {
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
		String value,
		String label) {
		if ((col >= 0 && col < colWidth.size()) && (row >= 0 && row < this.data.size())) {
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
		String value,
		String label) {

               StringBuffer selectedValues_Txt = new StringBuffer();
               if( isSelectedValues_Enabled() ) {

                 // se recorre la lista de valores seleccionados para ver si se debe marcar el actual
                 // ojo; inicialmente se compara contra el equals del objeto
                 if( ( null != selectedValues_Ids ) ) {
                   java.util.Iterator iterator = selectedValues_Ids.iterator();
                   Object element;
                   boolean selected = false;
                   for( ;iterator.hasNext(); ) {
                     element = iterator.next();
                     if( null == element )
                       continue;
                     if( element.equals( value ) ) {
                       selected = true;
                       break;
                     } // end if
                   } // end for
                   if( selected ) {
                      selectedValues_Txt.append( " " + "checked='checked'" + " " );
                   }

                 } // end if

               } // end if


		if ((col >= 0 && col < colWidth.size()) && (row >= 0 && row < this.data.size())) {
			String cell =
				"<input type=\"checkbox\" name=\""
					+ name
					+ "\" "
					+ "title=\""
					+ title
                                        + "\" "
                                        + " "
                                        + ( ( null == checkboxComplementaryAttributes )?(""):( checkboxComplementaryAttributes ) )
                                        + " "
					+ "value=\""
					+ value
                                        + "\""
                                        + " "
                                        + selectedValues_Txt
					+ "> "
					+ label;
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
		String value,
		String label) {
		if ((col >= 0 && col < colWidth.size()) && (row >= 0 && row < this.data.size())) {
			String cell =
				"<input type=\"radio\" name=\""
					+ name
					+ "\" "
					+ "title=\""
					+ title
					+ "\" "
					+ "value=\""
                    + value
                    + "\""
					+ ((isSelectedValue_Enabled() && selectedValue_Id != null && selectedValue_Id.equals(value)) ? " checked>" : ">")
					+ label;
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

		String resultado="";

		for(int i=0;i<rowCount;i++) {
			Vector row=(Vector)data.get(i);
			for(int j=0;j<colCount;j++) {
				String cell=(String)row.get(j);
				resultado=resultado+cell+"\t";
			}
			resultado=resultado+"\n";
		}
		return resultado;
	}

	/**
	 * @return
	 */
	public String getInputName() {
		return inputName;
	}

    public boolean isSelectedValues_Enabled() {
        return selectedValues_Enabled;
    }

    public String getSelectedValues_Ids_Key() {
        return selectedValues_Ids_Key;
    }

    public java.util.List getSelectedValues_Ids() {
        return selectedValues_Ids;
    }

    /**
	 * @param string
	 */
	public void setInputName(String string) {
		inputName = string;
	}

	public void setAlineacionTodasCeldas(String alineacionTodasCeldas) {
		if (alineacionTodasCeldas.equals(TablaMatriculaHelper.ALINEACION_CENTRO) || alineacionTodasCeldas.equals(TablaMatriculaHelper.ALINEACION_DERECHA) || alineacionTodasCeldas.equals(TablaMatriculaHelper.ALINEACION_IZQUIERDA)){
			this.alineacionTodasCeldas = alineacionTodasCeldas;
		}
	}

    protected String checkboxComplementaryAttributes;

    public void setCheckboxComplementaryAttributes( String checkboxComplementaryAttributes )  {
        this.checkboxComplementaryAttributes = checkboxComplementaryAttributes;
    }

    public void setSelectedValues_Enabled(boolean selectedValues_Enabled) {
        this.selectedValues_Enabled = selectedValues_Enabled;
    }

    public void setSelectedValues_Ids_Key(String selectedValues_Ids_Key) {
        this.selectedValues_Ids_Key = selectedValues_Ids_Key;
    }

    public void setSelectedValues_Ids(Vector selectedValues_Ids) {
        this.selectedValues_Ids = selectedValues_Ids;
    }
  
    /**
     * @return
     */
    public boolean isSelectedValue_Enabled() {
        return selectedValue_Enabled;
    }
  
    public void setSelectedValue_Enabled(boolean selectedValue_Enabled) {
        this.selectedValue_Enabled = selectedValue_Enabled;
    }
  
    public void setSelectedValue_Id(Object selectedValue_Id) {
        this.selectedValue_Id = selectedValue_Id;
    }
  
    public void setSelectedValue_Id_Key(String selectedValue_Id_Key) {
        this.selectedValue_Id_Key = selectedValue_Id_Key;
    }
    
    
    
	public void setValueWithCheckBoxText(
			String name,
			String width,
			String height,
			String border,
			String function,
			int col,
			int row,
			String title,
			String value,
			String label) {
			String[] matAnot=value.split(":");
			String anotacion="";
			if(matAnot!=null ){
				if( matAnot[0]!=null){
					label=matAnot[0].trim();
				}
				if( matAnot.length>1 && matAnot[1]!=null)
					anotacion=matAnot[1].trim();
			}
        	StringBuffer selectedValues_Txt = new StringBuffer();
           if( isSelectedValues_Enabled() ) {
             // se recorre la lista de valores seleccionados para ver si se debe marcar el actual
             // ojo; inicialmente se compara contra el equals del objeto
             if( ( null != selectedValues_Ids ) ) {
               java.util.Iterator iterator = selectedValues_Ids.iterator();
               Object element;
               boolean selected = false;
               for( ;iterator.hasNext(); ) {
                 element = iterator.next();
                 if( null == element )
                   continue;
                 if( element.equals( value ) ) {
                   selected = true;
                   break;
                 } 
               } 
               if( selected ) {
                  selectedValues_Txt.append( " " + "checked='checked'" + " " );
               }

             } 
           } 
           if ((col >= 0 && col < colWidth.size()) && (row >= 0 && row < this.data.size())) {
			   value=label;
        	   String cell =
					"<input type=\"checkbox\" name=\""
						+ name
						+ "\" "
						+ "title=\""
						+ title
	                                        + "\" "
	                                        + " "
	                                        + ( ( null == checkboxComplementaryAttributes )?(""):( checkboxComplementaryAttributes ) )
	                                        + " "
						+ "value=\""
						+ value
	                                        + "\""
	                                        + " "
	                                        + selectedValues_Txt
						+ "> "
						+ label;
				String celda=
					"<br><input name=\""
					+ "A"+label 
					+ "\" "
					+"title=\""
					+title
					+ "\" "
					+"value=\""
					+anotacion					
					+ "\" "
					+"class=\""
					+"camposformtext"
					+ "\" "					
					+">";
				//SETEAR VALOR
				setValue(col, row, cell+celda);
			}
		}

}
