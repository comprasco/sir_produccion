/*
 * Created on 25-oct-2004
*/
package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.TextAreaHelper;
import gov.sir.core.web.helpers.comun.TextHelper;
import gov.sir.core.web.helpers.comun.MostrarFechaHelper;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class AnotacionCancelacionHelper extends Helper {
	private TextAreaHelper textAreaHelper;
	
		private TextHelper textHelper;
	
		private TextHelper hiddenHelper;
		
		private MostrarFechaHelper MFHelper;
	
		private ListaElementoHelper tiposDocHelper;
	
		private VariosCiudadanosAnotacionHelper variosCiudadanosHelper;
	
		private List anotaciones;
		
		private Date date;
		
		private String formName = "DEFAULT";
		
		private boolean editar = false;
		
		private String accionNaturaleza = "modificacion.do";
		
		private String accionorigen = "";
		
		private boolean editarFecha = false;
		
		private boolean addFechaNumRadicacion = false;
		
		private boolean editarNumRadicacion = false;
		
		private boolean editarEstado = false;
		
		private boolean datosDocumento = false;
		
		private boolean mostrarBotonConsultaPropietario = false;
                
                private String tipoFormulario = "";
                
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   Se agrega la propiedad solicitud con los metodos get y set
         * Caso Mantis  :   04131
         */
        private Solicitud solicitud;

        public Solicitud getSolicitud() {
            return solicitud;
        }

        public void setSolicitud(Solicitud solicitud) {
            this.solicitud = solicitud;
        }
		
	/**
	 * 
	 */
	public AnotacionCancelacionHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			
			if (accionorigen.length()>0) {
				accionNaturaleza = accionorigen;
			}
			
			out.write("<script type=\"text/javascript\">");
			out.write("	function cambiarAccionAndSendTipoTarifa(text) { ");
			out.write(
				"	if(document."
					+ formName
					+ "."
					+ AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID
					+ ".value!=\"\"){ ");
			out.write(
				"		document." + formName + ".action = 'calificacion.do'; ");
			out.write("		document." + formName + ".ACCION.value = text; ");
			out.write("		document." + formName + ".submit(); ");
			out.write("		}");
			out.write("	}");
			out.write("</script>");
			out.write("<script type=\"text/javascript\">");
			out.write("	function cambiarAccionAndSendTipoTarifaNaturaleza(text) { ");
			out.write(
				"	if(document."
					+ formName
					+ "."
					+ AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID
					+ ".value!=\"\"){ ");
			out.write(
				"		document." + formName + ".action = '" + accionNaturaleza + "'; ");
			out.write("		document." + formName + ".ACCION.value = text; ");
			out.write("		document." + formName + ".submit(); ");
			out.write("		}");
			out.write("	}");
			out.write("</script>");
			out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
					out.write("<tr>");
					out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"7\" height=\"10\"></td>");
					out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn003.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
					out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
					out.write("</tr>");
					out.write("<tr>");
					out.write("<td><img name=\"tabla_central_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r1_c1.gif\" width=\"7\" height=\"29\" border=\"0\" alt=\"\"></td>");
					out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn003.gif\">");
					out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					out.write("<tr>");
					out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn001.gif\" class=\"titulotbcentral\">Datos de la cancelaci&oacute;n</td>");
					out.write("<td width=\"9\"><img name=\"tabla_central_r1_c3\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r1_c3.gif\" width=\"9\" height=\"29\" border=\"0\" alt=\"\"></td>");
					out.write("<td width=\"20\" align=\"center\" valign=\"top\" background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn002.gif\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
					out.write("<tr>");
					out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ico_new.gif\" width=\"16\" height=\"21\"></td>");
					out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("</td>");
					out.write("<td width=\"12\"><img name=\"tabla_central_r1_c5\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r1_c5.gif\" width=\"12\" height=\"29\" border=\"0\" alt=\"\"></td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("</td>");
					out.write("<td><img name=\"tabla_central_pint_r1_c7\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_pint_r1_c7.gif\" width=\"11\" height=\"29\" border=\"0\" alt=\"\"></td>");
					out.write("</tr>");
					out.write("<tr>");
					out.write("<td width=\"7\" background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
					out.write("<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
					out.write("<table width=\"100%\" >");
					out.write("<tr>");
					out.write("<td>");
		
					/*out.write("<hr class=\"linehorizontal\">");
		
					out.write("<input type=\"hidden\" name=\""+CAnotacion.POSICION+"\" id=\""+CAnotacion.POSICION+"\">");
					if(!anotaciones.isEmpty()){
						out.write("<table width=\"100%\" class=\"camposform\">");
						Iterator itAnotaciones = anotaciones.iterator();
			
						int i = 0;
						while(itAnotaciones.hasNext()){
							Anotacion anotacion = (Anotacion) itAnotaciones.next();
							out.write("<tr>");
							out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
							out.write("<td width=\"20\" class=\"campositem\">N&ordm;</td>");
							out.write("<td class=\"campositem\">" + anotacion.getOrden() + "</td>");
							out.write("<td width=\"40\" align=\"center\"><input type=\"image\" src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"quitar('"+i+"','" + AWFolio.ELIMINAR_ANOTACION+ "')\" width=\"35\" height=\"13\"></td>");
							out.write("<td width=\"40\" align=\"center\"><a href=\"crear.folio.view\"><img onClick=\"verAnotacion('ver.anotacion.view?" + CAnotacion.POSICION + "="+i+"','Anotacion','width=900,height=450,scrollbars=yes','"+i+"')\" src=\"" + request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" border=\"0\" width=\"35\" height=\"13\"></a></td>");
							out.write("</tr>");
							i++;			
						}
						out.write("</table>");
						out.write("<hr class=\"linehorizontal\">");	
					}
		
					/**************************************/
					out.write("<input name=\"id_depto\" type=\"hidden\" id=\"id_depto\" value=\"\">");
					out.write("<input name=\"nom_depto\" type=\"hidden\" id=\"nom_depto\" value=\"\">");
					out.write("<input name=\"id_munic\" type=\"hidden\" id=\"id_munic\" value=\"\">");
					out.write("<input name=\"nom_munic\" type=\"hidden\" id=\"nom_munic\" value=\"\">");
					out.write("<input name=\"id_vereda\" type=\"hidden\" id=\"id_vereda\" value=\"\">");
					out.write("<input name=\"nom_vereda\" type=\"hidden\" id=\"nom_vereda\" value=\"\">");
		
					/***************************************/
		
		
					/*out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					out.write("<tr>");
					out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
					out.write("<td class=\"bgnsub\">Radicacion</td>");
					out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
					out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
					out.write("</tr>");
					out.write("</table>");
				
					out.write("<table width=\"100%\" class=\"camposform\">");
					out.write("<tr>");
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
					out.write("<td width=\"20\" class=\"campositem\">N&ordm;</td>");
					int pos = anotaciones.size() + 1;
					out.write("<td class=\"campositem\">" + pos + "</td>");
					out.write("<td>");
					out.write("</tr>");
					out.write("</table>");
		
					out.write("</td>");
					out.write("</tr>");
					out.write("<tr>");
					out.write("<td>");
					
					
		
					*/
					
					//INICIO ESTADO CANCELACION
					
					if (this.editarEstado)
					{
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td>Fecha</td>");
						out.write("<td>"); 
						out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
						out.write("<tr>");
						out.write("<td class=\"campositem\">");

						ListaElementoHelper estadoAnotacionHelper = new ListaElementoHelper();
						List estadosAnotacion = (List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ESTADOS_ANOTACION_ELEMENTO_LISTA);
						if(estadosAnotacion == null){
							estadosAnotacion = new Vector();
						}
						estadoAnotacionHelper.setTipos(estadosAnotacion);
						estadoAnotacionHelper.setCssClase("camposformtext");	
						
						estadoAnotacionHelper.setNombre(AWModificarFolio.ANOTACION_ESTADO);
						estadoAnotacionHelper.setId(AWModificarFolio.ANOTACION_ESTADO);
						estadoAnotacionHelper.render(request,out);

						out.write("</table>");
						out.write("</td>");
						out.write("</tr>");
						out.write("</table>");
					}
					
					//FIN ESTADO CANCELACION
					
					
					///esto se hace con el finde suplir una mejora funcional que no es adaptable a la estrutura del sir
					
					if(addFechaNumRadicacion){
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td>Fecha</td>");
						out.write("<td>"); 
						out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
						out.write("<tr>");
						out.write("<td>");
						MFHelper.setDate(date);
						
							TextHelper fechaRadicaAnota = new TextHelper();
							fechaRadicaAnota.setNombre(AWModificarFolio.ANOTACION_FECHA_RADICACION);
							fechaRadicaAnota.setCssClase("camposformtext");
							fechaRadicaAnota.setId(AWModificarFolio.ANOTACION_FECHA_RADICACION);
							fechaRadicaAnota.setFuncion("  onkeypress=\"return valideDate(event,'"+AWModificarFolio.ANOTACION_FECHA_RADICACION+"');\" "
                                                    + " onChange=\"fixDate('"+AWModificarFolio.ANOTACION_FECHA_RADICACION+"')\"   onBlur=\"javascript:validarFecha()\"" );
							fechaRadicaAnota.render(request,out);
							out.write("<td><a href=\"javascript:NewCal('" + AWModificarFolio.ANOTACION_FECHA_RADICACION + "','ddmmmyyyy',true,24)\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" + request.getContextPath() + "')\"></a></tr>");
						
						
						
						out.write("</td>");
						out.write("</tr>");
						out.write("</table>");
						out.write("</td>"); 

						    TextHelper numRadicaAnota = new TextHelper();
						    out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
							out.write("<td>Radicacion</td>");
						    out.write("<td>");
							out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
							out.write("<tr>");
							out.write("<td class=\"campositem\">");
							
							numRadicaAnota.setNombre(AWModificarFolio.ANOTACION_NUM_RADICACION);
							numRadicaAnota.setCssClase("camposformtext");
							numRadicaAnota.setId(AWModificarFolio.ANOTACION_NUM_RADICACION);
							numRadicaAnota.render(request,out);
							
							out.write("</td>");
							out.write("</tr>");
							out.write("</table>");
							
							out.write("</td>");
							out.write("</tr>");
							out.write("</table>");
							
						
						// FIN NUM RADICIÓN
					}else{
					
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td>Fecha</td>");
						out.write("<td>"); 
						out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
						out.write("<tr>");
						out.write("<td class=\"campositem\">");
						MFHelper.setDate(date);
						if(this.editarFecha)
						{
							TextHelper fechaRadicaAnota = new TextHelper();
							fechaRadicaAnota.setNombre(AWModificarFolio.ANOTACION_FECHA_RADICACION);
							fechaRadicaAnota.setCssClase("camposformtext");
							fechaRadicaAnota.setId(AWModificarFolio.ANOTACION_FECHA_RADICACION);
							fechaRadicaAnota.setFuncion("onBlur=\"javascript:validarFecha()\"" );
							fechaRadicaAnota.render(request,out);
							out.write("<td><a href=\"javascript:NewCal('" + AWModificarFolio.ANOTACION_FECHA_RADICACION + "','ddmmmyyyy',true,24)\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" + request.getContextPath() + "')\"></a></tr>");
						}
						else
						{
							MFHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
							MFHelper.render(request,out);
							out.write("<td><!-- <a href=\"javascript:NewCal('" + AWModificarFolio.ANOTACION_FECHA_RADICACION + "','ddmmmyyyy',true,24)\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_calendario.gif\" alt=\"Fecha\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" + request.getContextPath() + "')\"></a>--></tr>");
						}
						
						out.write("</table>");
						out.write("</td>");
						out.write("</tr>");
						out.write("</table>");
						
						// COMIENZA NUM RADICIÓN
						//Número de Radición
						if (this.editarNumRadicacion) 
						{
							TextHelper numRadicaAnota = new TextHelper();
							
							out.write("<table width=\"100%\" class=\"camposform\">");
							out.write("<tr>");
							out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
							out.write("<td>N&uacute;mero de Radicaci&oacute;n</td>");
							out.write("<td>"); 
							out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
							out.write("<tr>");
							out.write("<td>");
							
							numRadicaAnota.setNombre(AWModificarFolio.ANOTACION_NUM_RADICACION);
							numRadicaAnota.setCssClase("camposformtext");
							numRadicaAnota.setId(AWModificarFolio.ANOTACION_NUM_RADICACION);
							numRadicaAnota.render(request,out);
							
							out.write("</td>");
							out.write("</tr>");
							out.write("</table>");
							out.write("</td>");
							out.write("</tr>");
							out.write("</table>");
						}
					}
					
					// FIN NUM RADICIÓN
					

					//Se muestra lo siguiente si es una cancelación de corrección.
					Turno turno = (Turno) request.getSession().getAttribute(gov.sir.core.web.WebKeys.TURNO);
					if(turno != null)
					{
						
					if(String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)){					
						out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
						out.write("<tr>");
						out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
						out.write("<td class=\"bgnsub\">Documento</td>");
						out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
						out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
						out.write("</tr>");
						out.write("</table>");
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td>Datos B&aacute;sicos </td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td>&nbsp;</td>");
						out.write("<td>");
						out.write("<table width=\"100%\" class=\"camposform\">");
						out.write("<tr>");
						out.write("<td>Tipo</td>");
						out.write("<td>");
						
						tiposDocHelper.setSelected((String)request.getSession().getAttribute(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO));
						tiposDocHelper.setId(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
						tiposDocHelper.setCssClase("camposformtext");
						tiposDocHelper.setNombre(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
						tiposDocHelper.render(request,out);
			                 
						out.write("</td>");
						out.write("<td>N&uacute;mero</td>");
						out.write("<td>");
			
						textHelper.setTipo("text");
						textHelper.setNombre(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);
						textHelper.render(request,out);
			
						out.write("</td>");
						out.write("<td>Fecha</td>");
						out.write("<td>");
						out.write("<table align=\"center\">");
						out.write("<tr>");
						out.write("<td>");
				    
						textHelper.setTipo("text");
						textHelper.setNombre(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
						textHelper.setCssClase("camposformtext");
						textHelper.setId(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
						textHelper.render(request,out);
			
										
						out.write("<td><a href=\"javascript:NewCal('" + AWModificarFolio.ANOTACION_FECHA_DOCUMENTO + "','ddmmmyyyy',true,24)\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_calendario.gif\" alt=\"Pick a date\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('" + request.getContextPath() + "')\"> </a>");                        
						out.write("</tr>");
						out.write("</table>");
						out.write("</td>");
						out.write("</tr>");
						out.write("</table>");
						out.write("</td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
						out.write("<td>Oficina de Procedencia </td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td>&nbsp;</td>");
						out.write("<td>");

						//Helper para ingresar la oficina de procedencia
						gov.sir.core.web.helpers.registro.OficinaHelper oficinaHelper = new gov.sir.core.web.helpers.registro.OficinaHelper ();
						try {
							oficinaHelper.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}

					
						out.write("</td>");
						out.write("</tr>");
						out.write("</table>");
					}
				}

			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");  
			out.write(" <td width=\"20\"><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");  
			
			out.write("	<td width=\"150\">Naturaleza Jur&iacute;dica </td>");  
			out.write("	<td width=\"40\" align=\"justify\">ID </td>");
		
			out.write("	<td width=\"40\">");
			
			//Modificado febrero 6
			String accion = AWModificarFolio.CARGAR_DESCRIPCION_NATURALEZA_CANCELACION;
			if(editar){
				accion = AWModificarFolio.CARGAR_DESCRIPCION_NATURALEZA_EDITAR_CANCELACION;
			}
			
			
			textHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
			textHelper.setCssClase("camposformtext");
			textHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
			textHelper.setFuncion("onBlur=\"cambiarAccionAndSendTipoTarifaNaturaleza('"+accion+"')\"");
			textHelper.setSize("5");
			textHelper.render(request,out); 
			out.write("	</td>");
		
			out.write("	<td width=\"100\">Descripci&oacute;n</td>");
			out.write("	<td align=\"justify\">");  
			out.write("		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");  
			out.write("			<tr>");  
			out.write("				<input name=\"natjuridica_id\" type=\"hidden\" id=\"natjuridica_id\" value=\"\">");
			out.write("				<input name=\"natjuridica_nom\" type=\"hidden\" id=\"natjuridica_nom\" value=\"\">");
                        /**
                          * @Autor: Carlos Torres
                          * @Mantis: 0012705
                          * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                          * @Descripcion: Se asigna valor a campo en el formulario
                          */
                        out.write("    <input name=\"natjuridica_ver\" type=\"hidden\" id=\"natjuridica_ver\" value=\"\">");
                        out.write("<input name=\""+CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER+"\" type=\"hidden\" id=\""+CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER+"\" value=\""+request.getSession().getAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER)+"\">");
			out.write("				<td align \"right\">");
			textAreaHelper.setNombre(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
			textAreaHelper.setCols("50");
			textAreaHelper.setRows("1");
			textAreaHelper.setReadOnly(true);
			textAreaHelper.setCssClase("camposformtext");
			textAreaHelper.setId(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
			textAreaHelper.render(request,out); 
			out.write("				</td>");
		
			out.write("				<td><a href=\"javascript:juridica('seleccionar.naturaleza.juridica.view?cancelacioncalificacion=true','"+AWModificarFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION+"','width=800,height=350,menubar=no')\"><img src=\""+request.getContextPath()+"/jsp/images/ico_nat_juridica.gif\" alt=\"Permite seleccionar la naturaleza juridica\" width=\"16\" height=\"21\" border=\"0\" onClick=\"javascript:Valores('"+request.getContextPath()+"')\"></a></td>");                     
			out.write("			</tr>");  	
			out.write("		</table>");
			out.write("	</td>");  
			out.write("	<td width=\"50\">Valor</td> ");    
			out.write("	<td width=\"150\">  "); 

                                textHelper.setTipo("text");
                        if(this.getTipoFormulario().equals("EDITAR_CANCELACION")) {
                            textHelper.setFuncion("onBlur=\"formatoValor(this.value,this.id)\"");
                        } else {
                            textHelper.setFuncion("onBlur=\"reescribirValor(this.value,this.id)\"");
                        }
                            textHelper.setNombre(CFolio.ANOTACION_VALOR_ESPECIFICACION);
                            textHelper.setCssClase("camposformtext");
                            textHelper.setId(CFolio.ANOTACION_VALOR_ESPECIFICACION);
                            textHelper.setEditable(true);
                            textHelper.setSize("20");
                            textHelper.render(request,out);

			out.write("</td>");
			out.write("</tr>");  
			out.write("</table>");
			
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Comentario</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>");

			textAreaHelper.setNombre(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
			textAreaHelper.setCols("50");
			textAreaHelper.setRows("5");
			textAreaHelper.setReadOnly(false);
			textAreaHelper.setCssClase("camposformtext");
			textAreaHelper.setId(AWModificarFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
			textAreaHelper.render(request,out);
	
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<br>");
		
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se agrega el campo para incluir en la cancelacion la salvedad
                         * Caso Mantis  :   04131
                         */
                        if(this.solicitud instanceof SolicitudCorreccion){
                            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                            out.write("<tr>");
                            out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
                            out.write("<td class=\"bgnsub\">Salvedad: Anotaci&oacute;n</td>");
                            out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
                            out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
                            out.write("</tr>");
                            out.write("</table>");
                            out.write("<table width=\"100%\" class=\"camposform\">");
                            out.write("<tr>");
                            out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
                            out.write("<td>Salvedad</td>");
                            out.write("</tr>");
                            out.write("<tr>");
                            out.write("<td>&nbsp;</td>");
                            out.write("<td>");
                            textAreaHelper.setNombre(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
                            textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");
                            textAreaHelper.setCols("130");
                            textAreaHelper.setRows("2");
                            textAreaHelper.setReadOnly(false);
                            textAreaHelper.setCssClase("camposformtext");
                            textAreaHelper.setId(AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION);
                            textAreaHelper.render(request,out);
                            out.write("</td>");
                            out.write("</tr>");
                            out.write("</table>");
                            out.write("<br>");
                        }

	
                        out.write("</td>");
                        out.write("</tr>");
                        out.write("</table>");
                        out.write("</td>");
			out.write("<td width=\"11\" background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td><img name=\"tabla_central_r3_c1\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
			out.write("<td background=\"" + request.getContextPath() + "/jsp/images/tabla_central_bgn006.gif\"><img src=\"" + request.getContextPath() + "/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
			out.write("<td><img name=\"tabla_central_pint_r3_c7\" src=\"" + request.getContextPath() + "/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td colspan=\"3\">");
			
			if(turno!=null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_CORRECCIONES)){
				variosCiudadanosHelper.setProceso(CProceso.PROCESO_CORRECCIONES);
				variosCiudadanosHelper.setAnotacionNueva(true);
			}
			variosCiudadanosHelper.render(request,out);
			
			out.write("</td>");
			out.write("</tr>");

                        		
			out.write("</table>");
                       			
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
			HttpSession session = request.getSession();
			tiposDocHelper = new ListaElementoHelper();
			List tiposDocs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
			tiposDocHelper.setOrdenar(false);
			tiposDocHelper.setTipos(tiposDocs);
			variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
			textHelper = new TextHelper();
			MFHelper = new MostrarFechaHelper();
			hiddenHelper = new TextHelper();
			hiddenHelper.setTipo("hidden");
			//hiddenHelper.setTipo("text");
			textAreaHelper = new TextAreaHelper();
		
			anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
			if(anotaciones == null){
				anotaciones = new Vector();
			}
			
			if(!editar){
				//	HELPER VARIOS CIUDADANOS
				variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
				Integer auxNumFilas = (Integer)session.getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
				List lciudadanos= (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
				if(lciudadanos==null){
					lciudadanos= new Vector();
				}
				int numFilas;
				if(auxNumFilas == null)
					numFilas=AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
				else
					numFilas=auxNumFilas.intValue();
				List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                                List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
                                List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
                                List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
                                List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
				
		 		List tiposIDNaturalNew = new ArrayList();
				
				for (Iterator iter = tiposIDNatural.iterator(); iter.hasNext();) {
					ElementoLista element = (ElementoLista) iter.next();
					tiposIDNaturalNew.add(element);
				}
                                
                                List tiposPersonaNew = new ArrayList();
				
				for (Iterator iter = tiposPersona.iterator(); iter.hasNext();) {
					ElementoLista element = (ElementoLista) iter.next();
					tiposPersonaNew.add(element);
				}
                                
                                List tiposSexoNew = new ArrayList();
				
				for (Iterator iter = tiposSexo.iterator(); iter.hasNext();) {
					ElementoLista element = (ElementoLista) iter.next();
					tiposSexoNew.add(element);
				}
                                
                                List tiposIDJuridicaNew = new ArrayList();
				
				for (Iterator iter = tiposIDJuridica.iterator(); iter.hasNext();) {
					ElementoLista element = (ElementoLista) iter.next();
					tiposIDJuridicaNew.add(element);
				}
				
				variosCiudadanosHelper.setPropertiesHandly(numFilas,tiposIDNaturalNew,tiposPersonaNew,tiposSexoNew,tiposIDJuridicaNew,AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION,
																			AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION,
																			AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_CANCELACION,
																			AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_CANCELACION,
																			lciudadanos, AWCalificacion.VALIDAR_CIUDADANO_CANCELACION, "CANCELAR");
				if (this.isMostrarBotonConsultaPropietario()){
					variosCiudadanosHelper.setAccionUltimosPropietarios(AWCalificacion.GET_ULTIMOS_PROPIETARIOS);
					variosCiudadanosHelper.setMostrarBotonConsultaPropietario(true);
				}
			}else{
//				HELPER VARIOS CIUDADANOS
				variosCiudadanosHelper = new VariosCiudadanosAnotacionHelper();
				Integer auxNumFilas = (Integer)session.getAttribute(AWCalificacion.NUM_REGISTROS_TABLA_CIUDADANOS);
				List lciudadanos= (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
				if(lciudadanos==null){
					lciudadanos= new Vector();
				}
				int numFilas;
				if(auxNumFilas == null)
					numFilas=AWCalificacion.DEFAULT_NUM_CIUDADANOS_TABLA;
				else
					numFilas=auxNumFilas.intValue();
				List tiposID = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                                List tiposIDNatural = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL);
                                List tiposPersona = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PERSONA);
                                List tiposSexo = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_SEXO);
                                List tiposIDJuridica = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA);
				variosCiudadanosHelper.setPropertiesHandly(numFilas,tiposIDNatural,tiposPersona,tiposSexo,tiposIDJuridica,AWCalificacion.AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION,
																			AWCalificacion.REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION_CANCELACION,
																			AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_EDICION_CANCELACION,
																			AWCalificacion.ELIMINAR_CIUDADANO_ANOTACION_EDICION_CANCELACION,
																			lciudadanos, AWCalificacion.VALIDAR_CIUDADANO_EDICION_CANCELACION, "CANCELAR");
				if (this.isMostrarBotonConsultaPropietario()){
					variosCiudadanosHelper.setAccionUltimosPropietarios(AWCalificacion.GET_ULTIMOS_PROPIETARIOS);
					variosCiudadanosHelper.setMostrarBotonConsultaPropietario(true);
				}
			}
			
			//mirar si hay excepciones en la pagina.
			Boolean exception;
			exception = (Boolean)session.getAttribute(WebKeys.HAY_EXCEPCION);
			if(exception!=null){
				 variosCiudadanosHelper.setCentrar(false);
			}
	}
	
	public void setFecha(Date ndate){
		date=ndate;
	}

		/**
		 * @return
		 */
		public String getFormName() {
			return formName;
		}

		/**
		 * @param string
		 */
		public void setFormName(String string) {
			formName = string;
		}

		/**
		 * @return
		 */
		public boolean isEditar() {
			return editar;
		}

		/**
		 * @param b
		 */
		public void setEditar(boolean b) {
			editar = b;
		}

		public String getAccionorigen() {
			return accionorigen;
		}

		public void setAccionorigen(String accionorigen) {
			this.accionorigen = accionorigen;
		}

		public boolean isEditarFecha() {
			return editarFecha;
		}

		public void setEditarFecha(boolean editarFecha) {
			this.editarFecha = editarFecha;
		}

		public boolean isEditarNumRadicacion() {
			return editarNumRadicacion;
		}

		public void setEditarNumRadicacion(boolean editarNumRadicacion) {
			this.editarNumRadicacion = editarNumRadicacion;
		}

		public boolean isEditarEstado() {
			return editarEstado;
		}

		public void setEditarEstado(boolean editarEstado) {
			this.editarEstado = editarEstado;
		}

		public boolean isDatosDocumento() {
			return datosDocumento;
		}

		public void setDatosDocumento(boolean datosDocumento) {
			this.datosDocumento = datosDocumento;
		}

		public boolean isMostrarBotonConsultaPropietario() {
			return mostrarBotonConsultaPropietario;
		}

		public void setMostrarBotonConsultaPropietario(
				boolean mostrarBotonConsultaPropietario) {
			this.mostrarBotonConsultaPropietario = mostrarBotonConsultaPropietario;
		}

		public boolean isAddFechaNumRadicacion() {
			return addFechaNumRadicacion;
		}

		public void setAddFechaNumRadicacion(boolean addFechaNumRadicacion) {
			this.addFechaNumRadicacion = addFechaNumRadicacion;
		}

                public String getTipoFormulario() {
                    return tipoFormulario;
                }

                public void setTipoFormulario(String tipoFormulario) {
                    this.tipoFormulario = tipoFormulario;
                }
    }
