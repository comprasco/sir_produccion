package gov.sir.core.web.helpers.comun;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.registro.OficinaHelper;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;



/**
 * @author ddiaz
 * Helper encargado de mostrar los datos de antiguo sistema
 */
public class MostrarAntiguoSistemaHelper extends Helper {
    
	TextHelper textHelper = null;
	OficinaHelper oficinaHelper =null;
	MostrarFechaHelper fechaHelper= null;
	String libroAnio="&nbsp;";
 	String libroTipo="&nbsp;";
 	String libroNumero="&nbsp;";
 	String libroPagina="&nbsp;";
	String tomoNumero="&nbsp;";
	String tomoPagina="&nbsp;";
	String tomoMunicipio="&nbsp;";
	String tomoAnio="&nbsp;";
	String Comentario="&nbsp;";
	String documentoNumero="&nbsp;";
	String documentoTipo="&nbsp;";
	String documentoComentario="&nbsp;";
	Date documentoFecha= null;
	String oficinaNumero="&nbsp;";
	String oficinaNombre="&nbsp;";
	String oficinaDepartamento="&nbsp;";
	String oficinaMunicipio="&nbsp;";
	boolean mostrarDocumento=true;
	boolean hayTurno = true;
	
	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
    public void setProperties(HttpServletRequest request) throws HelperException {
		
		oficinaHelper = new OficinaHelper();
		textHelper = new TextHelper();
		fechaHelper= new MostrarFechaHelper();
		Solicitud solicitud=null;
		SolicitudCertificado solicitudC =null;
	
		//Obtencion de los datos para llenar el helper
		
		if(!hayTurno){
			Liquidacion liquidacion = (Liquidacion)request.getSession().getAttribute(WebKeys.LIQUIDACION);
			if(liquidacion!=null){
				solicitud = liquidacion.getSolicitud();				
			}else if(request.getSession().getAttribute(WebKeys.SOLICITUD)!=null){
				solicitud = (Solicitud)request.getSession().getAttribute(WebKeys.SOLICITUD);
			}else if(request.getSession().getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO)!=null){
				solicitud = (Solicitud)request.getSession().getAttribute(CSolicitudRegistro.SOLICITUD_REGISTRO);
				mostrarDocumento = false;
			}
			
		}else{
			Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
			if(turno.getSolicitud() instanceof SolicitudCertificado){
				solicitud =(SolicitudCertificado)turno.getSolicitud();
				solicitudC = (SolicitudCertificado)turno.getSolicitud();
				
			}else{
				solicitud =turno.getSolicitud();
                mostrarDocumento = false;
			}
		}
        
		if(solicitud!=null){	 
	
		  if(solicitud.getDatosAntiguoSistema()!=null){
			
			  //LIBRO_ANIO
			  if(solicitud.getDatosAntiguoSistema().getLibroAnio()!=null){
			  	  libroAnio=solicitud.getDatosAntiguoSistema().getLibroAnio();
			  }
				  
			  //LIBRO_TIPO
			  if(solicitud.getDatosAntiguoSistema().getLibroTipo()!=null){
			  	  libroTipo=solicitud.getDatosAntiguoSistema().getLibroTipo();
			  }
			  
			 //	LIBRO_NUMERO
			if(solicitud.getDatosAntiguoSistema().getLibroNumero()!=null){
				libroNumero=solicitud.getDatosAntiguoSistema().getLibroNumero();
			}
			
			//	LIBRO_PAGINA
		   if(solicitud.getDatosAntiguoSistema().getLibroPagina()!=null){
			   libroPagina=solicitud.getDatosAntiguoSistema().getLibroPagina();
		   }
		   
			//	   TOMO_NUMERO
			if(solicitud.getDatosAntiguoSistema().getTomoNumero()!=null){
		    	tomoNumero=solicitud.getDatosAntiguoSistema().getTomoNumero();
			}
			
			//		TOMO_PAGINA
			if(solicitud.getDatosAntiguoSistema().getTomoPagina()!=null){
				tomoPagina=solicitud.getDatosAntiguoSistema().getTomoPagina();
			}
			
			//		TOMO_MUNICIPIO
			if(solicitud.getDatosAntiguoSistema().getTomoMunicipio()!=null){
				tomoMunicipio=solicitud.getDatosAntiguoSistema().getTomoMunicipio();
			}
			
			//		TOMO_ANIO
			if(solicitud.getDatosAntiguoSistema().getTomoAnio()!=null){
				tomoAnio=solicitud.getDatosAntiguoSistema().getTomoAnio();
			}
			
			if(mostrarDocumento && solicitudC.getDocumento()!=null){
				//DOCUMENTO_NUMERO
				if(solicitudC.getDocumento().getNumero()!=null){
					documentoNumero=solicitudC.getDocumento().getNumero();
				}
				
				//DOCUMENTO_TIPO
				if(solicitudC.getDocumento().getTipoDocumento()!=null){
					documentoTipo=solicitudC.getDocumento().getTipoDocumento().getNombre();
				}
				
				//DOCUMENTO_FECHA
			 	if(solicitudC.getDocumento().getFecha()!=null){
					documentoFecha=solicitudC.getDocumento().getFecha();
			 	}
				
				//DOCUMENTO_COMENTARIO
				if(solicitudC.getDocumento().getComentario()!=null){
					documentoComentario=solicitudC.getDocumento().getComentario();
				}
			
				//datos de la oficina de origen
				if(solicitudC.getDocumento().getOficinaOrigen()!=null){
					//OFICINA_ORIGEN_TIPO
					if(solicitudC.getDocumento().getOficinaOrigen().getNumero()!=null){
						oficinaNumero=solicitudC.getDocumento().getOficinaOrigen().getNumero();
					}
				
				
					//OFICINA_ORIGEN_NOMBRE
					if(solicitudC.getDocumento().getOficinaOrigen().getNombre()!=null &&
					solicitudC.getDocumento().getOficinaOrigen().getTipoOficina()!=null ){
						String oficinaTipo ="";
						if(solicitudC.getDocumento().getOficinaOrigen().getTipoOficina().getNombre()!=null){
							oficinaTipo=solicitudC.getDocumento().getOficinaOrigen().getTipoOficina().getNombre();
						}
						String oficinaNom="";
						if(solicitudC.getDocumento().getOficinaOrigen().getNombre()!=null){
							oficinaNom=solicitudC.getDocumento().getOficinaOrigen().getNombre();
						}
						oficinaNombre=oficinaTipo + " - " + oficinaNom;
					}
					
					//OFICINA_ORIGEN_DEPARTAMENTO
					if(solicitudC.getDocumento().getOficinaOrigen().getVereda()!=null){
						if(solicitudC.getDocumento().getOficinaOrigen().getVereda().getMunicipio()!=null){
							if(solicitudC.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento()!=null){
								oficinaDepartamento=solicitudC.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre();
							}
						}
					}
					
					//OFICINA_ORIGEN_DEPARTAMENTO
					if(solicitudC.getDocumento().getOficinaOrigen().getVereda()!=null){
						if(solicitudC.getDocumento().getOficinaOrigen().getVereda().getMunicipio()!=null){
							oficinaMunicipio=solicitudC.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre();
						}
					}
				}
				
				
			
			}
			
			//COMENTARIO
			if(solicitud.getDatosAntiguoSistema().getComentario()!=null){
				Comentario=solicitud.getDatosAntiguoSistema().getComentario();
			}
			
		  	}
		  	
			request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS, libroTipo);
			request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS, libroNumero);
			request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS, libroPagina);
			request.getSession().setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS, libroAnio);
			request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS, tomoNumero);
			request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS, tomoPagina);
			request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS, tomoMunicipio);
			request.getSession().setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, tomoAnio);
			request.getSession().setAttribute(CDatosAntiguoSistema.COMENTARIO_AS, Comentario);
			if(mostrarDocumento){
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS, documentoNumero);
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS, documentoTipo);
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS, documentoFecha);
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS, documentoComentario);
				
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS, oficinaNumero);
				//ena la variable tipo se guadara tanto el tipo como el nombre de la oficina.
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS, oficinaNombre);
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS, oficinaMunicipio);
				request.getSession().setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS, oficinaDepartamento);
			
			}
		}
    }

    
	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws IOException, HelperException {

		out.write("	<table width=\"100%\" class=\"camposform\">");
		out.write("		<tr>");
		out.write("			<td width=\"2%\"><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("			<td width=\"20%\" class=\"titulotbcentral\" align=\"left\">Libro</td>");
		out.write("			<td width=\"25%\">&nbsp;</td>");
		out.write("			<td width=\"20%\">&nbsp;</td>");
		out.write("			<td width=\"25%\">&nbsp;</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>    ");
		out.write("			<td>Tipo</td>");	
		out.write("			<td class=\"campositem\">");
		
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.LIBRO_TIPO_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.LIBRO_TIPO_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}				
		
		
		out.write("			</td>");
		out.write("			<td>N&uacute;mero-Letra</td>");
		out.write("			<td class=\"campositem\">");
		
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								} 
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
		
		out.write("			</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>");
		out.write("			<td>Pagina</td>");
		out.write("			<td class=\"campositem\">");
		
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
		
		out.write("			</td>");
		out.write("			<td>A&ntilde;o</td>");
		out.write("			<td class=\"campositem\">");
		
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.LIBRO_ANO_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.LIBRO_ANO_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								} 
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
		
		out.write("			</td>");
		out.write("		</tr>");
		out.write("	</table>");
		
		
		
		out.write("	<table width=\"100%\" class=\"camposform\">");
		out.write("		<tr> ");
		out.write("			<td width=\"2%\"><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		out.write("			<td width=\"20%\" class=\"titulotbcentral\" align=\"left\">Tomo</td>");
		out.write("			<td width=\"25%\">&nbsp;</td>");
		out.write("			<td width=\"20%\">&nbsp;</td>");
		out.write("			<td width=\"25%\">&nbsp;</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>");
		out.write("			<td>N&uacute;mero</td>");
		out.write("			<td class=\"campositem\">");
		
							try {
								textHelper.setTipo("text");
								textHelper.setNombre(CDatosAntiguoSistema.TOMO_NUMERO_AS);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CDatosAntiguoSistema.TOMO_NUMERO_AS);
								textHelper.setEditable(false);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
		
		out.write("			</td>");
		out.write("			<td>Pagina</td>");
		out.write("			<td class=\"campositem\">");
		
							try {
								textHelper.setTipo("text");
								textHelper.setNombre(CDatosAntiguoSistema.TOMO_PAGINA_AS);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CDatosAntiguoSistema.TOMO_PAGINA_AS);
								textHelper.setEditable(false);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
		
		out.write("			</td>");
		out.write("		</tr>");
		out.write("		<tr>");
		out.write("			<td>&nbsp;</td>");
		out.write("			<td>Municipio</td>");
		out.write("			<td class=\"campositem\">");
		
							try {
								textHelper.setTipo("text");
								textHelper.setNombre(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
								textHelper.setEditable(false);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}		
		
		out.write("			</td>");
		out.write("			<td>A&ntilde;o</td>");
		out.write("			<td class=\"campositem\">");
		
							try {
								textHelper.setTipo("text");
								textHelper.setNombre(CDatosAntiguoSistema.TOMO_ANO_AS);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CDatosAntiguoSistema.TOMO_ANO_AS);
								textHelper.setEditable(false);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
		
		out.write("			</td>");
		out.write("		</tr>");
		out.write("	</table>");
		//TODO Eliminar el contendo del if si SNR está conforme con el cambio. Requerimiento 020_141
		if(mostrarDocumento){
	
		
			out.write("	<table  style=\"display:none\" width=\"100%\" class=\"camposform\">");
			out.write("		<tr>");
			out.write("			<td width=\"2%\"><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("			<td width=\"20%\" class=\"titulotbcentral\" align=\"left\">Datos del documento</td>");
			out.write("			<td width=\"25%\">&nbsp;</td>");
			out.write("			<td width=\"20%\">&nbsp;</td>");
			out.write("			<td width=\"25%\">&nbsp;</td>");
			out.write("		</tr>");
			out.write("		<tr>");
			out.write("			<td>&nbsp;</td>");
			out.write("			<td>N&uacute;mero</td>");
			out.write("			<td class=\"campositem\">");
			
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
			
			out.write("			</td>");
			out.write("			<td>Tipo</td>");
			out.write("			<td class=\"campositem\">");
			
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
			
			out.write("		</tr>");		
			out.write("		<tr>");
			out.write("			<td>&nbsp;</td>");
			out.write("			<td>Fecha</td>");
			out.write("			<td class=\"campositem\">");
			
								try {
									fechaHelper.setDate(documentoFecha);
									fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
									fechaHelper.render(request, out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
			
			out.write("			</td>");
			out.write("			<td>Comentario</td>");
			out.write("			<td class=\"campositem\">");
			
								try {
									textHelper.setTipo("text");
									textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
									textHelper.setCssClase("camposformtext");
									textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
									textHelper.setEditable(false);
									textHelper.render(request,out);
								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
			
			out.write("			</td>");
			out.write("		</tr>");
			out.write("	</table>");
			
			
			out.write("	<table  style=\"display:none\" width=\"100%\" class=\"camposform\">");
			out.write("		<tr>");
			out.write("			<td width=\"2%\"><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("			<td width=\"20%\" class=\"titulotbcentral\">Oficina de origen (Documento)</td>");
			out.write("			<td width=\"25%\">&nbsp;</td>");
			out.write("			<td width=\"20%\">&nbsp;</td>");
			out.write("			<td width=\"25%\">&nbsp;</td>");
			out.write("		</tr>");
			out.write("		<tr>");
			out.write("			<td>&nbsp;</td>");
			out.write("			<td>Municipio</td>");
			out.write("			<td class=\"campositem\">");
			
									try {
											textHelper.setTipo("text");
											textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS);
											textHelper.setCssClase("camposformtext");
											textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS);
											textHelper.setEditable(false);
											textHelper.render(request,out);
									}catch(HelperException re){
										 out.println("ERROR " + re.getMessage());
									}
			
			out.write("			</td>");		
			out.write("			<td>Departamento</td>");	
			out.write("			<td class=\"campositem\">");	
			
									try {
											textHelper.setTipo("text");
											textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
											textHelper.setCssClase("camposformtext");
											textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS);
											textHelper.setEditable(false);
											textHelper.render(request,out);
									}catch(HelperException re){
										 out.println("ERROR " + re.getMessage());
									}
			
			out.write("			</td>");	
			out.write("		</tr>");	
			out.write("		<tr>");	
			out.write("			<td>&nbsp;</td>");
			out.write("			<td>Numero</td>");	
			out.write("			<td  class=\"campositem\">");	
			
									try {
											textHelper.setTipo("text");
											textHelper.setCssClase("camposformtext");				
											textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS);
											textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS);
											textHelper.setEditable(false);
											textHelper.render(request,out);
									}catch(HelperException re){
										 out.println("ERROR " + re.getMessage());
									}
			
			out.write("			</td>");		
			out.write("			<td>Nombre</td>");		
			out.write("			<td class=\"campositem\">");	
			
									try {
											textHelper.setTipo("text");
											textHelper.setCssClase("camposformtext");	
											textHelper.setNombre(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS);
											textHelper.setId(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS);
											textHelper.setEditable(false);
											textHelper.render(request,out);
									}catch(HelperException re){
										out.println("ERROR " + re.getMessage());
									}
				
			out.write("			</td>");		
			out.write("		</tr>");		
			out.write("	</table>");
		}
        
        if (!Comentario.equals("&nbsp;")){
            out.write(" <table width=\"100%\" class=\"camposform\">");
            out.write("     <tr>");
            out.write("         <td width=\"2%\"><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>   ");
            out.write("         <td width=\"90%\"class=\"titulotbcentral\"> Comentario (Datos Antiguo Sistema)</td>   ");
            out.write("     </tr> ");
            out.write("     <tr> ");
            out.write("         <td>&nbsp;</td>   ");
            out.write("         <td class=\"campositem\"> ");
                                    try {
                                        TextAreaHelper areaHelper = new TextAreaHelper();
                                        areaHelper.setNombre(CDatosAntiguoSistema.COMENTARIO_AS);
                                        areaHelper.setId(CDatosAntiguoSistema.COMENTARIO_AS);
                                        areaHelper.setReadOnly(true);
                                        areaHelper.setCols("110");
                                        areaHelper.setRows("4");
                                        areaHelper.render(request,out);
                                    } 
                                        catch(HelperException re){
                                        out.println("ERROR " + re.getMessage());
                                    }
            
            out.write("         </td> ");
            out.write("     </tr> ");
            out.write(" </table>");
        }
    }
    
    public void setMostrarDocumento(boolean b){
    	mostrarDocumento=b;
    }
    
	public void setHayTurno(boolean b){
		hayTurno=b;
	}

	
}
