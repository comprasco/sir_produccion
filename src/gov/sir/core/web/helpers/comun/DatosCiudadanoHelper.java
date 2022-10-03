/*
 * Created on 20-ene-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.web.WebKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatosCiudadanoHelper extends Helper {

	private String actionFormulario = "";
	private boolean telefono = true;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
	}
	
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		Ciudadano ciudadano = (Ciudadano) request.getSession().getAttribute(WebKeys.VALIDAR_CIUDADANO);
		boolean ciudadanoEncontrado = false;

		if(ciudadano==null){
			ciudadanoEncontrado = false;
		}else{
			if (request.getSession().getAttribute(WebKeys.CIUDADANO_VERIFICADO)!=null){
				ciudadanoEncontrado = ((Boolean) request.getSession().getAttribute(WebKeys.CIUDADANO_VERIFICADO)).booleanValue();
			}
		}
		
		if (ciudadano!=null && ciudadanoEncontrado==false){
			if (request.getSession().getAttribute(CCiudadano.TIPODOC)!=null){
				ciudadano.setTipoDoc((String)request.getSession().getAttribute(CCiudadano.TIPODOC));
			}
			if (request.getSession().getAttribute(CCiudadano.IDCIUDADANO)!=null){
				ciudadano.setDocumento((String)request.getSession().getAttribute(CCiudadano.IDCIUDADANO));
			}
			if (request.getSession().getAttribute(CCiudadano.APELLIDO1)!=null){
				ciudadano.setApellido1((String)request.getSession().getAttribute(CCiudadano.APELLIDO1));
			}
			if (request.getSession().getAttribute(CCiudadano.APELLIDO2)!=null){
				ciudadano.setApellido2((String)request.getSession().getAttribute(CCiudadano.APELLIDO2));
			}
			if (request.getSession().getAttribute(CCiudadano.NOMBRE)!=null){
				ciudadano.setNombre((String)request.getSession().getAttribute(CCiudadano.NOMBRE));
			}
			if (request.getSession().getAttribute(CCiudadano.TELEFONO)!=null){
				ciudadano.setTelefono((String)request.getSession().getAttribute(CCiudadano.TELEFONO));
			}
		}
		
		ListaElementoHelper tiposDocHelper = new ListaElementoHelper();
		TextHelper textHelper = new TextHelper();
		
		out.write("<SCRIPT>");
		out.write("function mostrarCampos(tipoDocumento){");
		out.write("		try{");
		out.write("			document.getElementById('"+CCiudadano.IDCIUDADANO+"').value='';");
		out.write("			document.getElementById('"+CCiudadano.APELLIDO1+"').value='';");
		out.write("			document.getElementById('"+CCiudadano.APELLIDO2+"').value='';");
		out.write("			document.getElementById('"+CCiudadano.NOMBRE+"').value='';");
		if(isTelefono()){
			out.write("			document.getElementById('"+CCiudadano.TELEFONO+"').value='';");	
		}		
		
		out.write("			if(tipoDocumento=='SE'){");
		out.write("				document.getElementById('tabla1').style.display='none';");
		out.write("				document.getElementById('tabla2').style.display='block';");
		out.write("				document.getElementById('botonValidar').style.display='none';");
		out.write("			} else {");
		out.write("				document.getElementById('tabla1').style.display='block';");
		out.write("				document.getElementById('tabla2').style.display='none';");
		out.write("				document.getElementById('botonValidar').style.display='block';");
		out.write("			}");
		out.write("			document."+actionFormulario+"."+CCiudadano.IDCIUDADANO+".readOnly = false;");
		out.write("			document."+actionFormulario+"."+CCiudadano.APELLIDO1+".readOnly = false;");
		out.write("			document."+actionFormulario+"."+CCiudadano.APELLIDO2+".readOnly = false;");
		out.write("			document."+actionFormulario+"."+CCiudadano.NOMBRE+".readOnly = false;");
		if(isTelefono()){
			out.write("			document."+actionFormulario+"."+CCiudadano.TELEFONO+".readOnly = false;");
		}
		out.write("		}catch(e){}");		
		out.write("}");
		out.write("function mostrarTodosLosCampos(){");
		out.write("		document.getElementById('tabla1').style.display='block';");
		out.write("		document.getElementById('tabla2').style.display='block';");
		out.write("		document.getElementById('botonValidar').style.display='none';");
		out.write("		document.getElementById('botonEliminar').style.display='block';");
		out.write("}");
		out.write("function consultar(){");
		out.write("		if(document."+actionFormulario+"."+CCiudadano.TIPODOC+".value != '"+WebKeys.SIN_SELECCIONAR+"' && document."+actionFormulario+"."+CCiudadano.IDCIUDADANO+".value != ''){");
		out.write("			document."+actionFormulario+".action='ciudadano.do';");
		out.write("			document."+actionFormulario+".ACCION.value='CONSULTAR';");
		out.write("			document."+actionFormulario+".submit();");
		out.write("		}");		
		out.write("}");		
		out.write("</SCRIPT>");
		
		out.write("<table width='100%' border='0' cellpadding='0' cellspacing='0' >");
		out.write("<tr>"); 
		out.write("<td width='12'><img name='sub_r1_c1' src='"+request.getContextPath()+"/jsp/images/sub_r1_c1.gif' width='12' height='22' border='0' alt=''></td>");
		out.write("<td background='"+request.getContextPath()+"/jsp/images/sub_bgn001.gif' class='bgnsub'>Datos Solicitante</td>");
		out.write("<td width='16' class='bgnsub'><img src='"+request.getContextPath()+"/jsp/images/ico_datosuser.gif' width='16' height='21'></td>");
		out.write("<td width='16' class='bgnsub'><img src='"+request.getContextPath()+"/jsp/images/ico_asociar.gif' width='16' height='21'></td>");
		out.write("<td width='15'><img name='sub_r1_c4' src='"+request.getContextPath()+"/jsp/images/sub_r1_c4.gif' width='15' height='22' border='0' alt=''></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("<br>");
  
  		out.write("<table width='100%' class='camposform'>");
		out.write("<tr>");
		out.write("<td width='20'><img src='"+request.getContextPath()+"/jsp/images/ind_campotxt.gif' width='20' height='15'></td>");
		out.write("<td width='219'>Tipo de Identificaci&oacute;n</td>");
		out.write("<td width='211'>");
	 
		if(ciudadano==null && 
		((request.getSession().getAttribute(CCiudadano.TIPODOC)==null)
		  ||
		  (
		  (request.getSession().getAttribute(CCiudadano.TIPODOC)!=null)&&(request.getSession().getAttribute(CCiudadano.TIPODOC).equals(WebKeys.SIN_SELECCIONAR))
		  )
		  ||
		  (
		  (request.getSession().getAttribute(CCiudadano.TIPODOC)!=null && !request.getSession().getAttribute(CCiudadano.TIPODOC).equals("SE"))&&((request.getSession().getAttribute(CCiudadano.IDCIUDADANO)==null) ||(request.getSession().getAttribute(CCiudadano.IDCIUDADANO).equals("")    )    )      )
		  )
		){
		 	try {
				
		 		List tiposDocs = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
		 		List tiposDocsNew = new ArrayList();
				
				for (Iterator iter = tiposDocs.iterator(); iter.hasNext();) {
					ElementoLista element = (ElementoLista) iter.next();
					tiposDocsNew .add(element);
				}
				
				tiposDocHelper.setFuncion("onchange='mostrarCampos(this.value)'");
				tiposDocHelper.setOrdenar(false);
				tiposDocHelper.setTipos(tiposDocsNew);
				tiposDocHelper.setNombre(CCiudadano.TIPODOC); 
				tiposDocHelper.setCssClase("camposformtext");
				tiposDocHelper.setId(CCiudadano.TIPODOC);

				tiposDocHelper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
		}else{
			try {
				 textHelper.setNombre(CCiudadano.TIPODOC);
				 textHelper.setCssClase("camposformtext");
				 textHelper.setId(CCiudadano.TIPODOC);
				 textHelper.setSize("30");
				 textHelper.setReadonly(true);
				 if(ciudadano!=null){
					 request.getSession().setAttribute(CCiudadano.TIPODOC, ciudadano.getTipoDoc());
					 if (ciudadanoEncontrado==true){
						 textHelper.setReadonly(true);
					 }
				 }
				 textHelper.render(request,out);
				 textHelper.setReadonly(false);
			 }catch(HelperException re){
				 out.println("ERROR " + re.getMessage());
			 }		
		}
		

		out.write("&nbsp;</td>");
		out.write("<td width='463' colspan='3'>");
		
		
		//SI SE ESTA EDITANDO UNA SECUENCIA SE OCULTA EL CAMPO NÚMERO DE EDICIÓN, SINO SE MUESTRA 
		if(!((request.getSession().getAttribute(CCiudadano.TIPODOC)!=null)&&(((String)request.getSession().getAttribute(CCiudadano.TIPODOC)).equals("SE")))){
			out.write("<table id='tabla1' style='display:block' border='0' cellpadding='0' class='contenido'>");	
		}else{
			out.write("<table id='tabla1' style='display:none' border='0' cellpadding='0' class='contenido'>");
		}		
		
		out.write("<tr>");
		out.write("<td width='146'>N&uacute;mero</td>");

		out.write("<td width='172'>");
		
	   try {
			textHelper.setNombre(CCiudadano.IDCIUDADANO);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CCiudadano.IDCIUDADANO);
			if(ciudadano!=null){
				request.getSession().setAttribute(CCiudadano.IDCIUDADANO, ciudadano.getDocumento());
				if (ciudadanoEncontrado==true){
					textHelper.setReadonly(true);
				}else if(request.getSession().getAttribute(CCiudadano.IDCIUDADANO)!=null){
					textHelper.setReadonly(true);
				}
			}else{
				if(request.getSession().getAttribute(CCiudadano.TIPODOC)!=null){
					textHelper.setReadonly(true);			
				}
			}
			if(request.getSession().getAttribute(CCiudadano.IDCIUDADANO)==null || request.getSession().getAttribute(CCiudadano.IDCIUDADANO).equals("")){
				textHelper.setReadonly(false);
			}

			textHelper.render(request,out);
			textHelper.setReadonly(false);	
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}

		out.write("</td>");
		
		out.write("<td><a href='javascript:consultar()'><img id='botonValidar' name='imageField' style='display:block' type='image' border='0' src='"+request.getContextPath()+"/jsp/images/btn_consultar.gif' width='139' height='21' border'0'\"></a></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</table>");
		
		if(!((request.getSession().getAttribute(CCiudadano.TIPODOC)!=null)&&(((String)request.getSession().getAttribute(CCiudadano.TIPODOC)).equals("SE")))){
			out.write("<table width='100%' border='0' cellpadding='0' class='camposform' id='tabla2' style='display:none'>");	
		}else{
			out.write("<table width='100%' border='0' cellpadding='0' class='camposform' id='tabla2'>");
		}
		
		out.write("<tr>");
		out.write("<td width='20'>&nbsp;</td>");
		out.write("<td width='219'>Primer Apellido / Razón Social</td>");
		out.write("<td width='211'>");
	    
	    try {
			textHelper.setNombre(CCiudadano.APELLIDO1);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CCiudadano.APELLIDO1);
			textHelper.setSize("36");
			if(ciudadano!=null){
				request.getSession().setAttribute(CCiudadano.APELLIDO1, ciudadano.getApellido1());
				if (ciudadanoEncontrado==true){
					textHelper.setReadonly(true);
				}
			}
			textHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}

		out.write("</td>");
		out.write("<td width='146'>Segundo Apellido</td>");
		out.write("<td width='172'>");
	   	try {
			textHelper.setNombre(CCiudadano.APELLIDO2);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CCiudadano.APELLIDO2);
			textHelper.setSize("20");
			if(ciudadano!=null){
				request.getSession().setAttribute(CCiudadano.APELLIDO2, ciudadano.getApellido2());
				if (ciudadanoEncontrado==true){
					textHelper.setReadonly(true);
				}
			} 
			textHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}

		out.write("</td>");
		out.write("<td>&nbsp;</td>");
		
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td  width='20' >&nbsp;</td>");
		out.write("<td  width='219' >Nombre</td>");
		out.write("<td  width='211' >");
	   	try {
			textHelper.setNombre(CCiudadano.NOMBRE);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CCiudadano.NOMBRE);
			textHelper.setSize("30");
			if(ciudadano!=null){
				request.getSession().setAttribute(CCiudadano.NOMBRE, ciudadano.getNombre());
				if (ciudadanoEncontrado==true){
					textHelper.setReadonly(true);					
				}
			} 
			textHelper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}

		out.write("</td>");
		

		//SE COLOCA EL CAMPO DE TELÉFONO SI LA VARIABLE TELÉFONO ESTA EN TRUE SINO NO SE MUESTRA
		//POR DEFECTO SE MUESTRA EL TELÉFONO
		if(this.isTelefono()){
			out.write("<td width='146'>Teléfono</td>");
		}else{
			out.write("<td width='146'>&nbsp;</td>");
		}		

		if(this.isTelefono()){
			out.write("<td width='172'>");
			try {
				textHelper.setNombre(CCiudadano.TELEFONO);
				textHelper.setCssClase("camposformtext");
				textHelper.setId(CCiudadano.TELEFONO);
				textHelper.setSize("20");
				if(ciudadano!=null){
					request.getSession().setAttribute(CCiudadano.TELEFONO, ciudadano.getTelefono());
					if (ciudadanoEncontrado==true){
						textHelper.setReadonly(true);					
					}
				}
				textHelper.render(request,out);
				textHelper.setSize("30");
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}

			out.write("</td>");
		}else{
			out.write("<td width='172'>&nbsp;");
			out.write("</td>");			
		}

		out.write("<td><input id='botonEliminar' name='imageField' type='image' src='"+request.getContextPath()+"/jsp/images/btn_cancelar.gif' width='139' height='21' border'0' onclick=\"document."+actionFormulario+".action='ciudadano.do'; ACCION.value='ELIMINAR';\"></td>");
		out.write("</tr>");
		
		if (ciudadano!=null){
			out.write("<SCRIPT>mostrarTodosLosCampos();</SCRIPT>");
		} 
		
		out.write("</table>");
	}
	/**
	 * @return
	 */
	public String getActionFormulario() {
		return actionFormulario;
	}

	/**
	 * @param string
	 */
	public void setActionFormulario(String string) {
		actionFormulario = string;
	}

	/**
	 * @return
	 */
	public boolean isTelefono() {
		return telefono;
	}

	/**
	 * @param b
	 */
	public void setTelefono(boolean b) {
		telefono = b;
	}

}
