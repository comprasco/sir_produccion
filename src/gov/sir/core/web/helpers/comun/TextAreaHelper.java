package gov.sir.core.web.helpers.comun;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author I.Siglo21
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TextAreaHelper extends Helper{
	private String nombre;
	private String cssClase;
	private String id;
	private String valor;
	private String cols;
	private String rows;
	private String funcion = "";	
	private String form;
	private String MaxChars;
	private boolean readOnly=false;
	

	/**
	 *
	 */
	public TextAreaHelper() {
		super();

	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {

		valor = (String) request.getSession().getAttribute(getId());
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws IOException, HelperException {
			
			if(form!=null && MaxChars != null){
				out.println("<script type=\"text/javascript\" language=\"javascript\">");
				out.println("var _isPasted"+nombre+"=false;");
				out.println("var _lastValue"+nombre+"=\"\";");
				out.println("function GetTextArea"+nombre+"() {");
				out.println("return document.forms[\""+ form + "\"].elements[\"" + nombre + "\"];");
				out.println("}");

				out.println("function "+nombre+"(event) {");
				out.println("//get pointer to text control:");
				out.println("var objTextArea=GetTextArea"+nombre+"();");

				out.println("//check if exists:");
				out.println("if (!objTextArea) {");
				//out.println("alert("warning: text area does not exist");");
				out.println("return;");
				out.println("}");

				out.println("//bind events:");
				out.println("objTextArea.onkeypress=TextareaKeyPress"+nombre+";");
				out.println("objTextArea.onkeyup=TextareaKeyUp"+nombre+";");
				out.println("objTextArea.onbeforepaste=TextareaPaste"+nombre+";");
				out.println("objTextArea.onkeyup();");
				out.println("}");
				
				
				out.println("function TextareaKeyPress"+nombre+"(event)");
				out.println("{");
				out.println("//get pointer to text control:");
				out.println("var objTextArea=GetTextArea"+nombre+"();");

				out.println("//get value:");
				out.println("var strValue=objTextArea.value;");

				out.println("//abort if no max chars defined:");
				out.println("if (!objTextArea.attributes['MaxChars"+nombre+"'])");
				out.println("return false;");

				out.println("//get max chars:");
				out.println("var maxChars=parseInt(objTextArea.attributes['MaxChars"+nombre+"'].value);");

				out.println("//check if exceeded:");
				out.println("if (strValue.length >= maxChars) {");
				out.println("if (_isPasted"+nombre+") {");
				out.println("//crop to maximum size:");
				out.println("objTextArea.value = strValue.substr(0, maxChars);");
				out.println("}");
				out.println("if ((!event)||(!event.keyCode))");
				out.println("return false;");

				out.println("var arrAllowedChars=new Array(8, 9, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46);");
				out.println("return InArray(arrAllowedChars, event.keyCode);");
				out.println("}");

				out.println("return true;");
				out.println("}");

				out.println("function TextareaKeyUp"+nombre+"(event)");
				out.println("{");

				out.println("//get pointer to text control:");
				out.println("var objTextArea=GetTextArea"+nombre+"();");

				out.println("//get id of control:");
				out.println("var strID=objTextArea.name+\"_charsCount\";");

				out.println("//get pointer to text container:");
				out.println("var objSpan=document.getElementById(strID);");

				out.println("//abort if not defined:");
				out.println("if (!objSpan)");
				out.println("return false;");

				out.println("//get value:");
				out.println("var strValue=objTextArea.value;");

				out.println("//abort if no max chars defined:");
				out.println("if (!objTextArea.attributes['MaxChars"+nombre+"'])");
				out.println("return false;");

				out.println("//get max chars:");
				out.println("var maxChars=parseInt(objTextArea.attributes['MaxChars"+nombre+"'].value);");

				out.println("//check if exceeded:");
				out.println("if (strValue.length >= maxChars) {");
				out.println("if (_lastValue"+nombre+".length != strValue.length) {");
				out.println("//crop to maximum size:");
				out.println("objTextArea.value = strValue.substr(0, maxChars);");
				out.println("}");
				out.println("strValue=objTextArea.value;");
				out.println("}");

				out.println("//store last value:");
				out.println("_lastValue"+nombre+" = strValue;");

				out.println("//set container text:");
				out.println("objSpan.innerHTML = (maxChars-strValue.length)+\" chracters left.\";");
				out.println("}");

				out.println("function TextareaPaste"+nombre+"(event)");
				out.println("{");
				out.println("//initiate keypress and keyup events:");
				out.println("_isPasted"+nombre+" = true;");
				out.println("var s1=setTimeout(\"TextareaKeyPress"+nombre+"();\", 100);");
				out.println("var s2=setTimeout(\"TextareaKeyUp"+nombre+"();\", 500);");
				out.println("}");

				out.println("function InArray(arr, key) {");
				out.println("for (var i=0; i<arr.length; i++) {");
				out.println("if (arr[i] == key)");
				out.println("return true;");
				out.println("}");
				out.println("return false;");
				out.println("}");
				out.println("</script>");				
			}
		
		String read ="";
		if(readOnly){
			read = "readonly = \"readonly\"";
		}
		
		if (id!=null){
			if (valor != null && valor.trim().length()>0){
				out.println("<textarea id = \""+id+"\" MaxChars"+nombre+"=\"" + MaxChars + "\""  + read+ " name=\"" + getNombre() + "\" cols=\"" +getCols()+ "\" rows=\"" +getRows()+ "\" class=\"" + cssClase + "\"" + " " +funcion + " " + ">");			
				out.write(valor);
				out.println("</textarea>");
			} else {
				out.println("<textarea id = \""+id+"\" MaxChars"+nombre+"=\"" + MaxChars + "\"" + read+ " name=\"" + getNombre() + "\" cols=\"" +getCols()+ "\" rows=\"" +getRows()+ "\" class=\"" + cssClase + "\"" + " " +funcion + " " + ">");
				out.println("</textarea>");
			}
		}else{
			if (valor != null && valor.trim().length()>0){
				out.println("<textarea MaxChars"+nombre+"=\"" + MaxChars + "\""  + read+ " name=\"" + getNombre() + "\" cols=\"" +getCols()+ "\" rows=\"" +getRows()+ "\" class=\"" + cssClase + "\"" + " " +funcion + " " + ">");			
				out.write(valor);
				out.println("</textarea>");
			} else {
				out.println("<textarea MaxChars"+nombre+"=\"" + MaxChars + "\"" + read+ " name=\"" + getNombre() + "\" cols=\"" +getCols()+ "\" rows=\"" +getRows()+ "\" class=\"" + cssClase + "\"" + " " +funcion + " " + ">");
				out.println("</textarea>");
			}	
		}
		
		
	}


	/**
	 * @return
	 */
	public String getCssClase() {
		return cssClase;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param string
	 */
	public void setCssClase(String string) {
		cssClase = string;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = string;
	}
	/**
	 * @return
	 */
	public String getCols() {
		return cols;
	}

	/**
	 * @return
	 */
	public String getRows() {
		return rows;
	}

	/**
	 * @param string
	 */
	public void setCols(String string) {
		cols = string;
	}

	/**
	 * @param string
	 */
	public void setRows(String string) {
		rows = string;
	}

	/**
	 * @param b
	 */
	public void setReadOnly(boolean b) {
		readOnly = b;
	}
	
	public void setMaxChars(String form, String maxChars){
		this.form = form;
		this.MaxChars = maxChars;
	}

	/**
	 * @return
	 */
	public String getFuncion() {
		return funcion;
	}

	/**
	 * @param string
	 */
	public void setFuncion(String string) {
		funcion = string;
	}

}
