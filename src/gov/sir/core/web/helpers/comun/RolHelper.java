package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.web.WebKeys;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias,mmunoz
 */
public class RolHelper extends Helper {
	
	private Rol rolSession;
	
	private List roles = new Vector();
	/**
	 * 
	 */
	public RolHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tdtablacentral\">");
			out.write("<form name=\"Roles\" method=\"post\" action=\"seguridad.do\" type=\"submit\">");
			out.write("<input type=\"hidden\" name=\"ACCION\" value=\"CONSULTAR_ROL\">");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>&nbsp;</td>");
			out.write("<td width=\"10\">&nbsp;</td>");
			out.write("</tr>");
			out.write("</table></td>");
			out.write("</tr>");
			out.write("<tr>"); 
			out.write("<td>");
		if (roles.isEmpty()) {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td>");
			out.write("No tiene roles disponibles");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
			out.write("</tr>");
			out.write("</form>");
			out.write("</table>");
		} else {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\""+ request.getContextPath()+"/jsp/images/ind_radiobutton.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td height=\"17\" class=\"titulotbcentral\">Seleccione un Rol </td>");
			out.write("</tr>");

			Iterator itRoles = roles.iterator();
			int i = 0;
			while (itRoles.hasNext()) {
				Rol rol = (Rol) itRoles.next();
				if( (!rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)) 
						&& (!rol.getRolId().equals(CRoles.ADMINISTRADOR_REGIONAL) 
						&& (!rol.getRolId().equals(CRoles.CONSULTA_REGIONAL)) 
						&& (!rol.getRolId().equals(CRoles.CONSULTA_NACIONAL))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_GESTION))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_CONTRALORIA))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_CAJERO_CERTIFICADOS_SIR))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_CONSULTA_SIR))
						&& (!rol.getRolId().equals(CRoles.REPORTES_NACIONAL))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_ADMINISTRADOR_IMPRESION))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_CONSULTA_INDICE_PROPIETARIO))
						&& (!rol.getRolId().equals(CRoles.SIR_ROL_CREADOR_FOLIOS))
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_CONSULTAS_ESPECIALES))
                                                /*AHERRENO 22/06/2012
                                                  REQ 076_151 - TRANSACCIONES*/
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_CONSULTAS_TRANSACCION))
                                                /*********************/
                                                /*JALCAZAR 21/10/2009*/
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_CREACION_MASIVA_AS))
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_CONSULTA_ESTADISTICA_SNR))
                                                /**
                                                * @author     : Julio Alcazar
                                                * @change     : NO se visualiza el rol SIR_ROL_IMPRESION_FOLIO_ESPECIAL de las listas de roles-procesos del usuario.
                                                * Caso Mantis : 0009044: Acta - Requerimiento No 036_151 - Impresión de Folios
                                                */
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_IMPRESION_FOLIO_ESPECIAL))
                                                /**
                                                * @author     : AHERRENO
                                                * @change     : No se visualiza el rol SIR_ROL_CONSULTAS_MINUTAS en la lista de roles-procesos del usuario.
                                                * REQ 024_453
                                                */                                        
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_CONSULTAS_MINUTAS))
                                                /**
                                                * @author     : CTORRES
                                                * @change     : No se visualiza el rol SIR_ROL_TRASLADO_MASIVO_FOLIO en la lista de roles-procesos del usuario.
                                                * REQ 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
                                                */                                        
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_TRASLADO_MASIVO_FOLIO))
                                                 /**
                                                * @author     : CTORRES
                                                * @change     : No se visualiza el rol SIR_ROL_AUDITOR en la lista de roles-procesos del usuario.
                                                * REQ 13176: Acta - Requerimiento No 061_453_Requerimiento_Auditoria
                                                */
                                                && (!rol.getRolId().equals(CRoles.SIR_ROL_AUDITOR))
                                        )){
                                                /*********************/
					String checked = new String(); 
					if(rol.getRolId().equals(rolSession.getRolId()) || i==0){
						checked = "checked";
					}
					out.write("<tr>");
					out.write("<td><label>");
					out.write("<input type=\"radio\" " + checked + " name=\"ID_ROL\" value=\"" + rol.getRolId() + "\">");
					out.write("</label></td>");
					out.write("<td>");
					out.write(rol.getNombre());
					out.write("</td>");
					out.write("</tr>");
					i++;
				}
			}
			out.write("</table>");
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td align=\"center\"><hr class=\"camposform\"></td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>");
			out.write("<input name=\"imageField\" type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_aceptar.gif\" width=\"139\" height=\"21\" border=\"0\">");
			out.write("&nbsp;&nbsp;<a href='javascript:admin();'><img  src=\"" + request.getContextPath() + "/jsp/images/btn_administracion.gif\" width=\"150\" height=\"21\" border=\"0\"></a>");
			out.write("</td>");
			//out.write("</tr>");
			//out.write("<tr>"); 
			//out.write("<td align=\"center\"><a href='javascript:admin();'><img  src=\"" + request.getContextPath() + "/jsp/images/btn_administracion.gif\" width=\"150\" height=\"21\" border=\"0\"></a></td>");
			out.write("</tr>");			
			out.write("</form>");
			out.write("</table>");
		}
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		roles = (List) request.getSession().getAttribute(WebKeys.LISTA_ROLES);
        
		if(roles == null){
			roles = new Vector();
		}
		
		rolSession = (Rol)request.getSession().getAttribute(WebKeys.ROL);
		if(rolSession == null){
			rolSession = new Rol();
		}
	}

}
