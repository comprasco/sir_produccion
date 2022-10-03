/*
 * Created on 22-oct-2004
*/
package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.antiguosistema.AWAntiguoSistema;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.smart.SMARTKeys;

/**
 * @author jfrias
*/
public class MatriculaCalifHelper extends Helper {
    
    public static final int MODO_VISUALIZACION_CALIFICAR=0;
    public static final int MODO_VISUALIZACION_REVISAR=1;
    
    /**
     * En este modo de visualizacion se puede consultar, eliminar, editar e imprmir cada matricula
     */
    public static final int MODO_VISUALIZACION_HOJA_RUTA=2;
    
	private int num;
    private int modoVisualizacion=MatriculaCalifHelper.MODO_VISUALIZACION_CALIFICAR;
    
	boolean revisar=false;
    boolean soloVer=false;
	private List folios;
    private String vistaActual;
    private String form;
	private Hashtable validacionAnotacionesTemporales;
	
	/**
	 * 
	 */
	public MatriculaCalifHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			
			gov.sir.core.negocio.modelo.Fase fase = (gov.sir.core.negocio.modelo.Fase)request.getSession().getAttribute(WebKeys.FASE);			
			
            out.println("<script type=\"text/javascript\">");
            out.println("function verAnotacion(nombre,valor,dimensiones,pos){");
            out.println("popup=window.open(nombre,valor,dimensiones);");
            out.println("popup.focus();");
            out.println("}");
            if (modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_HOJA_RUTA){
                out.println("function eliminar(nombre){");
                out.println("document.getElementById('"+WebKeys.ITEM+"').value=nombre;");
                out.println("document."+form+"."+WebKeys.ACCION+".value='"+AWAntiguoSistema.ELIMINAR_FOLIO+"';");
                out.println("document."+form+".submit();");
                out.println("}");
                
				out.println("function cancelarAnotacion(nombre){");
				out.println("document.getElementById('"+WebKeys.ITEM+"').value=nombre;");
				out.println("document."+form+"."+WebKeys.ACCION+".value='"+AWAntiguoSistema.COMENZAR_CANCELAR_ANOTACION+"';");
				out.println("document."+form+".submit();");
				out.println("}");
                
                out.println("function desasociar(nombre){");
                out.println("document.getElementById('"+WebKeys.ITEM+"').value=nombre;");
                out.println("document."+form+"."+WebKeys.ACCION+".value='"+AWAntiguoSistema.DESASOCIAR_FOLIO+"';");
                out.println("document."+form+".submit();");
                out.println("}");
                
                out.println("function editar(nombre){");
                out.println("document.getElementById('"+WebKeys.ITEM+"').value=nombre;");
                out.println("document."+form+"."+WebKeys.ACCION+".value='"+AWAntiguoSistema.EDITAR_FOLIO+"';");
                out.println("document."+form+".submit();");
                out.println("}");
                
                out.println("function editarAnotaciones(nombre){");
                out.println("document.getElementById('"+WebKeys.ITEM+"').value=nombre;");
                out.println("document."+form+"."+WebKeys.ACCION+".value='"+AWAntiguoSistema.EDITAR_ANOTACIONES_FOLIO+"';");
                out.println("document."+form+".submit();");
                out.println("}");
                
                out.println("function imprimir(nombre){");
                out.println("document.getElementById('"+WebKeys.ITEM+"').value=nombre;");
                out.println("document."+form+"."+WebKeys.ACCION+".value='"+AWAntiguoSistema.IMPRIMIR_FOLIO+"';");
                out.println("document."+form+".submit();");
                out.println("}"); 
            }
			
            out.println("</script>");
			out.println("<input type=\"hidden\" id=\""+WebKeys.ITEM+"\" name=\""+WebKeys.ITEM+"\" value=\"NINGUNO\">");
			if (num > 0) {
						out.println("<table width=\"100%\" class=\"camposform\">");
						out.println("<tr>");
                        if (modoVisualizacion!=MatriculaCalifHelper.MODO_VISUALIZACION_HOJA_RUTA){
                            out.println("<td></td><td>Matr&iacute;culas</td><td align=\"center\"> </td>");    
                        }
						
						if(modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_CALIFICAR){
                            //TODO
                            // Se añadió un input oculto donde se guarda el número de la anotación desde la cual el usuario desea visualizar
                            // y otro donde se guarda el número total de anotaciones.
							out.println("<td></td><td>Cerrar<br><input type='checkbox' name='cbAprobar' onclick='AprobarRechazarSeleccionar_todo(this)'><input type=\"hidden\" id=\""+WebKeys.CAMPO_NUM_ANOTACIONES_DESDE+"\" name=\""+WebKeys.CAMPO_NUM_ANOTACIONES_DESDE+"\" value=\"0\"><input type=\"hidden\" id=\""+WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL+"\" name=\""+WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL+"\" value=\"0\"></td>");
						}else if (modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_HOJA_RUTA){
                            out.println("<td></td><td></td><td></td>");
                        }
                        
                        if (vistaActual==null){
                            vistaActual=(String)request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
                        }
						Iterator iterator = folios.iterator();
                        int i=0;
						while(iterator.hasNext()){
							SolicitudFolio solfolio = (SolicitudFolio)iterator.next();
							Folio folio = solfolio.getFolio();
							
							String matricula = folio.getIdMatricula();							
							String labelMatricula = "";
                            
                            if (folio.isDefinitivo()){
                                labelMatricula = folio.getIdMatricula();
                            }else{
								if(fase==null){
									if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
										labelMatricula = folio.getNombreLote();
									}else{
										labelMatricula = folio.getIdMatricula();
									}   
								}else{
									if(fase.getID().startsWith("ANT_")){
										request.getSession().setAttribute(CFolio.FOLIO_CREADO_HOJA_RUTA, CFolio.FOLIO_CREADO_HOJA_RUTA);
										if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
											labelMatricula=folio.getNombreLote();	
										}										
									}else{
										if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
											labelMatricula=folio.getIdMatricula() + " - " +folio.getNombreLote();	
										}else{
											labelMatricula=folio.getIdMatricula();	
										}										
									}
								}
                            }
                            
							//SI NO SE LISTAN LAS MATRICULAS DE CALIFICACIÓN O
							//EL FOLIO NO ESTA CERRADO, SE PROCEDE A PINTARLO
							//QUITAR ESTAS LINEAS PARA NO PERMITIR LA EDICIÓN DE FOLIOS CERRADOS.
							//if(!(modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_CALIFICAR)||
							//	(folio.getEstado()!=null && !folio.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO))){								
									
								out.println("<tr>");
								out.println("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
								out.println("<td class=\"campositem\">" + (labelMatricula!=null?labelMatricula:"") + "</td>");
								
								Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
								
								if(modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_REVISAR){
									
                                                                    /*@author : Desarrollo1 TSG
                                                                    @Date   : 26-01-2018
                                                                    @change : Se Modifica botón para consultar los detalles del folio mediante ventana emergente con pestañas. Envía el id de la matrícula a consultar como parámetro en la url */           
                                                                    out.println("<td width=\"40\"><img name=\"btn_mini_verdetalles.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\" onClick=\"verAnotacionPersonalizada('"+matricula+"','consultar.folio.do?POSICION="+i+"','Folio','width=900,height=450,scrollbars=yes','0')\"></td>");
                                                                    // out.println("<td width=\"150\"><a href=\"javascript:verAnotacionPersonalizada('"+matricula+"', 'consultar.folio.do?POSICION="+i+"','Folio','width=900,height=450,scrollbars=yes','0')\"><img name=\"btn_revisar_folio.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_revisar_folio.gif\" width=\"150\" height=\"21\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
                                                                    out.println("<td width=\"150\"><a href=\"javascript:revisar('"+matricula+"')\"><img name=\"btn_revisar_folio.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_revisar_folio.gif\" width=\"150\" height=\"21\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
                                                                    //
                                                                    
								}else if (modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_CALIFICAR){
                                    //TODO
                                    // Se cambió la función JavaScript del boton.
                                    // Antes se llamaba a calificar, ahora se llama a getNumAnotacionesFolio quien posteriormente hará el llamado a calificar.
                                    out.println("<td width=\"150\"><a href=\"javascript:getNumAnotacionesFolio('"+matricula+ "', document.all." + WebKeys.CAMPO_NUM_ANOTACIONES_DESDE + ", document.all." + WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL + ")\"><img name=\"btn_consultar_folio.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_consultar_folio.gif\" width=\"180\" height=\"21\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
								}else if (modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_HOJA_RUTA){
	                                out.println("<td width=\"40\"><img name=\"btn_mini_verdetalles.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\" onClick=\"verAnotacion('consultar.folio.do?POSICION="+i+"','Folio','width=900,height=450,scrollbars=yes','"+i+"')\"></td>");
	                                if (solfolio.isMarcado() && !soloVer) {
	                                	if(!(turno.getSolicitud() instanceof SolicitudCorreccion)&&!(turno.getIdFase().equals("ANT_HOJA_RUTA"))) {
											if (folio.isDefinitivo()) {
												out.println("<td width=\"40\"><img name=\"btn_mini_editar.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\" onClick=\"javascript:editarAnotaciones('"+matricula+ "')\"></td>");
											}                                 	
	                                    }
	                                } else {
	                                	if(turno.getIdFase().equals("ANT_CREACION_FOLIO") || turno.getIdFase().equals("ANT_REVISION"))
	                                		out.println("<td></td>");
	                                	else{
	                                		if (!folio.isDefinitivo()) {
	                                				out.println("<td width=\"40\"><img name=\"btn_mini_editar.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\" onClick=\"javascript:editarAnotaciones('"+matricula+ "')\"></td>");	
	                                		} else {
	                                				out.println("<td></td>");
	                                		}
	                                		
	                                	}
	                                }
	                            }
								
								if(validacionAnotacionesTemporales!=null){
									Boolean hasTemporales = (Boolean)validacionAnotacionesTemporales.get(matricula);
									if(hasTemporales!=null && hasTemporales.booleanValue()){
										out.println("<td width=\"12\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_satisfactorio.gif\" width=\"16\"></td>");
									}else{
										out.println("<td width=\"12\">&nbsp;</td>");									
									}
								}
								
								if(modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_CALIFICAR){
									if(folio.getEstado()!=null){
										if(!folio.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO)){
											//out.println("<td width=\"20\" align=\"center\"><a href=\"javascript:cerrar('"+matricula+ "')\"><img name=\"btn_revisar_folio.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_short_cerrar_folio.gif\" width=\"20\" height=\"21\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
											out.println("<td width=\"20\" align=\"center\"><input type='checkbox' name='ckBoxCerrar' id='checkbox"+i+"' value='"+matricula+"'></td>");
										}
									}
								}	 	
	                            
	                            if (modoVisualizacion==MatriculaCalifHelper.MODO_VISUALIZACION_HOJA_RUTA){
	                            	
	                            	//if(turno.getSolicitud() instanceof SolicitudRegistro){
	                            		//HAY QUE TERMINAR ESTE REQUERIMIENTO QUE ES DE VERSIÓN DOS.
										//out.println("<td width=\"35\"><a href=\"javascript:cancelarAnotacion('"+matricula+ "')\"><img name=\"btn_mini_cancelar_anotacion.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_cancelar_anotacion.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
	                            	//}
	                                if (solfolio.isMarcado() && !soloVer){
	                                    if (folio.isDefinitivo()){
	                                        out.println("<td width=\"40\"><a href=\"javascript:desasociar('"+matricula+ "')\"><img name=\"btn_mini_eliminar.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");    
	                                    }else{
	                                        out.println("<td width=\"40\"><a href=\"javascript:eliminar('"+matricula+ "')\"><img name=\"btn_mini_eliminar.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
	                                    }
	                                    out.println("<td width=\"40\"><a href=\"javascript:imprimir('"+matricula+ "')\"><img name=\"btn_mini_imprimir.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_imprimir.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
	                                    
	                                    if(turno.getSolicitud() instanceof SolicitudCorreccion){
											out.println("<td width=\"40\"><a href=\"javascript:editar('"+matricula+ "')\"><img name=\"btn_mini_editar.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
	                                    } else {
											if (folio.isDefinitivo()){
												out.println("<td width=\"40\">&nbsp;</td>");
											}else{
												out.println("<td width=\"40\"><a href=\"javascript:editar('"+matricula+ "')\"><img name=\"btn_mini_editar.gif\" src=\"" +  request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" width=\"35\" height=\"13\"  style=\"cursor:'hand'\"  border=\"0\"></a></td>");
											}                                    	
	                                    }
	                                }else{
	                                    out.println("<td width=\"40\">&nbsp;</td>");
	                                    out.println("<td width=\"40\">&nbsp;</td>");
	                                    out.println("<td width=\"40\">&nbsp;</td>");
	                                }
	                                
	                            }
	                            i++;	
						}
                            							
							out.println("</tr>");
                            
						//}
						out.println("</table>");
						out.println("<br>");
					}
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest arg0) throws HelperException {
	}
	
	public void setFolios(List list) {
			folios = list;
			if(folios!=null){
				num = folios.size();
			}else{
				num=0;
			}
		}

	public void setRevisar(){
		revisar=true;
        modoVisualizacion=MatriculaCalifHelper.MODO_VISUALIZACION_REVISAR;
	}

	/**
	 * @return
	 */
	public Hashtable getValidacionAnotacionesTemporales() {
		return validacionAnotacionesTemporales;
	}

	/**
	 * @param hashtable
	 */
	public void setValidacionAnotacionesTemporales(Hashtable hashtable) {
		validacionAnotacionesTemporales = hashtable;
	}


    public void setModoVisualizacion(int modoVisualizacion) {
        this.modoVisualizacion = modoVisualizacion;
    }

    public void setVistaActual(String vistaActual) {
        this.vistaActual = vistaActual;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setSoloVer(boolean soloVer) {
        this.soloVer = soloVer;
    }

}
