/*
 * Created on 03-ago-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author dsalas
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TablaMatriculaRangoHelper extends Helper {

	/**
	 * Ancho de la tabla
	 */
	private String twidth;
	
	/**
	 * Estilo de la tabla
	 */
	private String ccs;
	
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

    /**
     * Crea un table helper
     */
    public TablaMatriculaRangoHelper(int colCount, int rowCount) {
        super();
        //1.LLENAR DATA
        data = new Vector();
        for(int i=0;i<rowCount;i++) {
        	//1.1 CREAR FILAS
        	Vector auxRow=new Vector();
        	for(int j=0;j<colCount;j++){
        		//1.1.1 LLENAR FILA CON CELDAS VACIAS
				auxRow.addElement("");
        	}
        	data.addElement(auxRow);
        }
        //2.LLENAR WIDTHS
        colWidth=new Vector();
        for(int j=0;j<colCount;j++)
        	colWidth.addElement(String.valueOf(100/colCount)+"%");
        //3.LLENAR CLASSES
		classes=new Vector();
		for(int j=0;j<colCount;j++)
		classes.addElement("");
        //4.DATOS SIMPLES
        twidth="100%";
        ccs="";
    }

    /**
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request) {
    }

    /**
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
	public void drawGUI(HttpServletRequest request, JspWriter out)
	throws IOException, HelperException {
        	
        out.write("<table width=\""+twidth+"\" class=\""+ccs+"\">");
        //RECORRER FILAS
		for(int i=0;i<data.size();i++){
			out.write("<tr>");
			//
			Vector auxRow=(Vector)data.elementAt(i);
			//RECORRER CELDAS
			for(int j=0;j<auxRow.size();j++){
				String auxWidth=(String)colWidth.elementAt(j);
				String classe=(String)classes.elementAt(j);
				out.write("<td width=\""+auxWidth+"\" class=\""+"\">");
				//
				String auxData=(String)auxRow.elementAt(j);
				out.write(auxData);
				//
				out.write("</td>");
			}
			//
			out.write("</tr>");
		}
		out.write("</table>");
			
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
		if(col>=0 && col<colWidth.size()) {
			//1.SETEAR DATOS DE LA COLUMNA
			//1.1.RECORRER FILAS
			for(int i=0;i<data.size();i++){
				//1.2 OBTENER LA FILA
				Vector auxRow=(Vector)data.elementAt(i);
				//1.3 SETEAR EL DATO
				auxRow.set(col,cell);
			}
			//2.SETEAR ESTILOS
			colWidth.set(col,width);
			//3.SETEAR ANCHOS
			classes.set(col,classe);
		}
	}
	
	/**
	 * @param col
	 * @param row
	 * @param data
	 */
	public void setValue(int col, int row, String data){
		if((col>=0&&col<colWidth.size())&&
		(row>=0&&row<this.data.size())){
			((Vector)this.data.elementAt(row)).set(col,data);
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
	public void setColButton(String image, String width, String height, String border, 
	String function, int colParam, int col, String alt){
		
		if((col>=0&&col<colWidth.size())&&
		(colParam>=0&&colParam<colWidth.size())){
			//RECORRER FILAS
			for(int i=0;i<data.size();i++){
				//OBTENER FILA
				Vector auxRow=(Vector)data.elementAt(i);
				//LEER PARAMETRO DE LA FUNCION
				String param=(String)auxRow.elementAt(colParam);
				//ARMAR STRING DE LA CELDA
				String cell="<input type=\"image\" name=\"image\" "+
							"src=\""+ image +"\" " +
							"width=\""+ width +"\" " +
							"height=\""+ height +"\" " +
							"border=\""+ border +"\" "+
							"alt=\""+ alt +"\" "+
							"onClick=\""+function+"('"+param+"')\">";
				//SETEAR VALOR
				setValue(col,i,cell);
			}
		
		}
		
	}
	
	public void copyCol(int source, int target){
		//SI PARAMETROS EN EL RANGO VALIDO
		if((source>=0&&source<colWidth.size())&&
		(target>=0&&target<colWidth.size())) {
			//RECORRER FILAS
			for(int i=0;i<data.size();i++){
				Vector auxRow=(Vector)data.elementAt(i);
				setValue(target,i,(String)auxRow.elementAt(source));
			}
		}
	}
	
	private boolean isNext(String value, String candidate) {
		try {
			//CREAR TOKENIZADORES
			StringTokenizer stValue=new StringTokenizer(value,"-");
			StringTokenizer stCandidate=new StringTokenizer(candidate,"-");
			//GROUPER TOKEN
			String grValue=stValue.nextToken();
			String grCandidate=stCandidate.nextToken();
			//SERIAL TOKEN
			int valueSerial=Integer.parseInt(stValue.nextToken());
			int candidateSerial=Integer.parseInt(stCandidate.nextToken());
			return (grValue.equals(grCandidate)&&
			((valueSerial+1)==candidateSerial || valueSerial==candidateSerial));
		} catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * Copia una fila
	 * @param source
	 * @param target
	 */
	private Vector copyRow(Vector source){
		Vector target=new Vector();
		for(int i=0;i<source.size();i++)
			target.add(source.elementAt(i));
		return target;
	}

	/**
	 * Comprime la información de la tabla escribiendo en 
	 * formato de rango los rangos encontrados
	 *
	 */
	public void compriseRanges(int valueCol, int auxCol) {
		//SI COL EN EL RANGO
		if((valueCol>=0 && valueCol<colWidth.size()) &&
		auxCol>=0 && auxCol<colWidth.size() &&
		data.size()>1) {
			//COPIAR COL
			copyCol(valueCol,auxCol);
			//CREAR MATRIZ DESTINO
			Vector comData=new Vector();
			//PRIMERA FILA//COMROW SIEMPRE ES LA FILA RECIEN COPIADA
			Vector comRow=copyRow((Vector)data.elementAt(0));
			comRow.set(auxCol,"");
			comData.add(comRow);
			for(int i=1;i<data.size();i++){//SE EMPIEZA A RECORRER DESDE LA SEGUNDA FILA
				//OBTENER FILA FUENTE
				Vector auxRow=(Vector)data.elementAt(i);
				//OBTENER CELDA VALUE
				String auxValue=(String)auxRow.elementAt(valueCol);
				//OBTENER CELDAS DE RANGO EN MATRIZ DESTINO
				String rang1=(String)comRow.elementAt(valueCol);
				String rang2=(String)comRow.elementAt(auxCol);
				//EVALUAR SI HAY QUE INGRESAR EL DATO AL RANGO
				if((rang2.equals("")&&isNext(rang1,auxValue)) ||
				(isNext(rang2,auxValue)))
					comRow.set(auxCol,auxValue);
				else {
					comRow=copyRow((Vector)data.elementAt(i));
					comRow.set(auxCol,"");
					comData.add(comRow);
				}
			}
			this.data=comData;
			//FORMATO
			for(int i=0;i<data.size();i++){
				Vector auxRow=(Vector)data.elementAt(i);
				String auxLeft=(String)auxRow.elementAt(valueCol);
				String auxRight=(String)auxRow.elementAt(auxCol);
				if(!auxRight.equals("")){
					auxLeft="RANGO: DESDE:"+auxLeft+":HASTA:"+auxRight;
					auxRow.set(valueCol,auxLeft);
					auxRow.set(auxCol,"");
				}
			}
		}
	}

	public static void main(String args[]){
		TablaMatriculaRangoHelper tmrh=new TablaMatriculaRangoHelper(3,18);
		//INICIAR
		tmrh.setCol("FOLIO","","",0);
		tmrh.setCol("","","",1);
		tmrh.setCol("AUX","","",2);
		//SETEAR VALORES COL
		tmrh.setValue(1,0,"1-234");
		tmrh.setValue(1,1,"6-236");
		tmrh.setValue(1,2,"6-237");
		tmrh.setValue(1,3,"6-238");
		tmrh.setValue(1,4,"1-240");
		tmrh.setValue(1,5,"1-240");
		tmrh.setValue(1,6,"1-240");
		tmrh.setValue(1,7,"1-250");
		tmrh.setValue(1,8,"1-251");
		tmrh.setValue(1,9,"1-252");
		tmrh.setValue(1,10,"1-253");
		tmrh.setValue(1,11,"1-254");
		tmrh.setValue(1,12,"1-255");
		tmrh.setValue(1,13,"1-256");
		tmrh.setValue(1,14,"1-201");
		tmrh.setValue(1,15,"2-202");
		tmrh.setValue(1,16,"3-203");
		tmrh.setValue(1,17,"1-20");
		tmrh.compriseRanges(1,2);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result="";
		for(int i=0;i<data.size();i++){
			Vector auxRow=(Vector)data.elementAt(i);
			for(int j=0;j<auxRow.size();j++){
				result=result+(String)auxRow.elementAt(j);
				result=result+"\t";
			}
			result=result+"\n";
		}
		return result;
	}

}
