package gov.sir.core.web.helpers.administracion;

import gov.sir.core.web.WebKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

public class TablaSeleccionDatosHelper extends Helper {

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
	private String ccs = "camposform";

	/**
	 * Nombre de la función de javascript a ejecutar cuando se selecciona el campo
	 */
	private String nombreFuncionSeleccion;
	
	private String listName;

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

	/**
	 * Alineación de las celdas
	 * Vector de String
	 */
	private Vector cellAlign;
	private Vector columnWidth;
	
	private Vector titulos;
	
	private String inputName = WebKeys.TITULO_CHECKBOX_ELIMINAR;
	
	private int tipoFila;
	private int numCols;

	/**
	 * Tipos de tabla: Radio Button y Checkbox
	 */
	public static final int FILA_RADIO = 0;
	public static final int FILA_CHECKBOX = 1;

	/**
	 * Crea un table helper
	 */
	public TablaSeleccionDatosHelper() {
		super();
		nombreFuncionSeleccion = "quitar";
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	/**
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
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
			List listDataTemp = new ArrayList();
			listDataTemp.addAll(listData);

			classes = new Vector();
			cellAlign = new Vector();
			columnWidth = new Vector();
			data = new Vector(listDataTemp.size());
			
			int maxCols = 0;
			
			// Se determina el número de columnas
			for(Iterator iterData = listDataTemp.iterator(); iterData.hasNext();) {
				List listaTemp = (List)iterData.next();
				if(listaTemp == null) {
					iterData.remove();
					continue;
				}
				
				if(maxCols < listaTemp.size())
					maxCols = listaTemp.size();
			}
			
			numCols = maxCols + 1;
			
			for(int i = 0; i < numCols; i++) {
				columnWidth.add("");
			}
			
			int currentRow = 0;
			Vector tempVector;
			
			// Se generan los datos para las filas
			for (Iterator iterData = listDataTemp.iterator(); iterData.hasNext(); currentRow++) {
				
				classes.add("campositem");
				cellAlign.add(ALINEACION_IZQUIERDA);
				
				List temp = (List)iterData.next();
				
				String idFila = (String)temp.get(0);
				String[] valores = new String[temp.size()];
				
				System.arraycopy(temp.toArray(), 0, valores, 0, temp.size());
				
				tempVector = new Vector();
				
				for(int i = 0; i < numCols; i++) {
					tempVector.add("");
				}
				
				data.add(tempVector);
				
				switch(tipoFila) {
					case FILA_RADIO :
						this.setValueWithRadioButtonRow(this.inputName, "0", currentRow, nombreFuncionSeleccion, 
								idFila, "", valores);
								break;
					
					case FILA_CHECKBOX :
					default :
						break;
				}
			}
		}
	}

    /**
	 * Funcion para obtener la lista de anchos
	 * @return Vector de String con los anchos de las columnas
	 */
	public Vector getWidths() {
		return columnWidth;
	}
	
	public void setTitulos(Vector titulos) {
		this.titulos = titulos;
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
			
			// Se imprime la fila con los títulos de las columnas
			tablaOut = new StringBuffer();
			tablaOut.append("<tr>");
			tablaOut.append("<td width=\"20\">");
			tablaOut.append("<img src=\"" + request.getContextPath() + "/jsp/images/ind_lista.gif\" width=\"20\" height=\"15\">");
			tablaOut.append("</td>");
			
			for(int i = 0; i < numCols; i++) {
				tablaOut.append("<td>&nbsp;</td>");
			}
			
			tablaOut.append("</tr>");
			tablaOut.append("<tr>");
			tablaOut.append("<td>&nbsp;</td>");

			for(int i = 0; i < numCols; i++) {
				tablaOut.append("<td class=\"titulotbcentral\">");
				
				if(i < titulos.size()) {
					tablaOut.append(titulos.get(i));
				} else {
					tablaOut.append("&nbsp;");
				}
				
				tablaOut.append("</td>");
			}
			
			tablaOut.append("</tr>");
			out.write(tablaOut.toString());

			//RECORRER FILAS
			for (int i = 0; i < data.size(); i++) {
				out.write("<tr>");
				//
				Vector auxRow = (Vector) data.elementAt(i);
				//RECORRER CELDAS
				for (int j = 0; j < numCols; j++) {

					String auxWidth = "";
					String auxData = "";
					String classe = "";
					String align = "left";

					tablaOut = new StringBuffer();
					
					if (j < columnWidth.size()) {
						auxWidth = (String) columnWidth.elementAt(j);
					}
					
					if (j <  auxRow.size()) {
						auxData = (String) auxRow.elementAt(j);
					}

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
					tablaOut.append("</td>");
					out.write(tablaOut.toString());
				}
				//
				out.write("</tr>");
			}
			out.write("</table>");
		}

	}
	
	public void setContenidoCelda(int contenido) {
		tipoFila = contenido;
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
		if (col >= 0 && col < columnWidth.size()) {
			//1.SETEAR DATOS DE LA COLUMNA
			//1.1.RECORRER FILAS
			for (int i = 0; i < data.size(); i++) {
				//1.2 OBTENER LA FILA
				Vector auxRow = (Vector) data.elementAt(i);
				//1.3 SETEAR EL DATO
				auxRow.set(col, cell);
			}
			//2.SETEAR ESTILOS
			columnWidth.set(col, width);
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
		if ((col >= 0 && col < columnWidth.size()) && (row >= 0 && row < this.data.size())) {
			((Vector) this.data.elementAt(row)).set(col, data);
		}
	}

	/**
	 * Método para fijar el ancho de una columna
	 * @param col columna a setear
	 * @param width ancho de la columna
	 */
	public void setColWidth(int col, String width) {
		if(col >= 0 && col < numCols && col < columnWidth.size())
			columnWidth.set(col,width);
	}
	
	public void setValueWithRadioButtonRow(
			String name,
			String border,
			int row,
			String function,
			String value,
			String label,
			String[] columns) {
			
		String cell = "<input type=\"radio\" name=\"" + name + "\" " + 
			"value=\"" + value + "\"" + ((isSelectedValue_Enabled() && 
					selectedValue_Id != null && selectedValue_Id.equals(value)) ? " checked>" : ">")
			+ label;
			
		setValue(0, row, cell);
		
		if(columns != null) {
			for(int i = 0; i < columns.length; i++) {
				setValue(i + 1, row, columns[i]);
			}
		}
	}
	
	public void setValueWithCheckBoxRow(
			String name,
			String border,
			int row,
			String function,
			String title,
			String value,
			String label,
			String[] columns) { }

	public void setClasses(Vector v) {
		this.classes = v;
	}

	public void setCellAlign(Vector v) {
		this.cellAlign = v;
	}
	
	/**
	 * @return El nombre de la función que se utiliza al hacer la selección
	 */
	public String getNombreFuncionSeleccion() {
		return nombreFuncionSeleccion;
	}

	/**
	 * @param string
	 */
	public void setNombreFuncionSeleccion(String string) {
		nombreFuncionSeleccion = string;
	}

	/**
	 * @return El nombre del control
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

	/*public void setAlineacionTodasCeldas(String alineacionTodasCeldas) {
		if (alineacionTodasCeldas.equals(TablaMatriculaHelper.ALINEACION_CENTRO) || alineacionTodasCeldas.equals(TablaMatriculaHelper.ALINEACION_DERECHA) || alineacionTodasCeldas.equals(TablaMatriculaHelper.ALINEACION_IZQUIERDA)){
			this.alineacionTodasCeldas = alineacionTodasCeldas;
		}
	}*/

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
     * @return Determina si se almacena el ID del valor seleccionado
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

}
