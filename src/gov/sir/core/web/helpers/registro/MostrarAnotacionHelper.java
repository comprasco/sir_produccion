package gov.sir.core.web.helpers.registro;

import java.io.IOException;

import java.text.NumberFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Vereda;

import gov.sir.core.negocio.modelo.constantes.CFolio;
/**
 * @author Cesar Ramírez
 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
 **/
import gov.sir.core.negocio.modelo.constantes.CRol;

import gov.sir.core.web.WebKeys;

import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.MostrarFechaHelper;
import gov.sir.core.web.helpers.comun.TextAreaHelper;
import gov.sir.core.web.helpers.comun.TextHelper;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
*/
public class MostrarAnotacionHelper extends Helper {

   // enumeration

   public static final String EDITIDENTIFIERFIELD_ID    = "EDITIDENTIFIERFIELD_ID";
   public static final String EDITIDENTIFIERFILED_ORDEN = "EDITIDENTIFIERFILED_ORDEN";

   // decidir que se va a tener en cuenta cuando se presione el icono de editar
   private String itemEdit_IdentifierFlag = EDITIDENTIFIERFILED_ORDEN;

   //
   private boolean temporalConContraparteDefinitivaEnabled;

	private TextAreaHelper textAreaHelper;

	private Anotacion anotacion;

	private TextHelper textHelper;

	private TextHelper hiddenHelper;

	private MostrarFechaHelper MFHelper;

	private NumberFormat nf = NumberFormat.getInstance();

	private ListaElementoHelper tiposDocHelper;

	private CiudadanosAnotacionHelper ciudadanosHelper;

	private List anotaciones;

	private int ordenTemporal=-1;

        private String idTemporal = null;

	private MostrarFechaHelper fechaHelper = new MostrarFechaHelper();

	private Date date;

	private List ciudadanosOrdenados;

	private String nombreResultado;

	private boolean hayPaginador=true;

	private Turno turno;

	private String idMatricula="";

	private boolean consulta=false;
	
	private boolean isCorrectoTurno = true;

        // usada para mostrar icon de edicion en anotaciones no temporales
        private boolean editarAnotacionNoTemporal = false;
        
        private boolean editarAnotacionTemporal = true;
	/**
	 *
	 */
	public MostrarAnotacionHelper() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
		    isCorrectoTurno = true;

                        String local_EditCriteriaValue;

			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.println("	<tr>");
			out.println("		<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			if(idMatricula==null || idMatricula.equals("")){
				out.println("		<td background=\""+request.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Anotaci&oacute;n: N&ordm; "+ (anotacion.getOrden() != null ? anotacion.getOrden() : "") +" </td>");
			}else if(request.getSession().getAttribute(CFolio.FOLIO_CREADO_HOJA_RUTA) == null){
				out.println("		<td background=\""+request.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Anotaci&oacute;n: N&ordm; "+ (anotacion.getOrden() != null ? anotacion.getOrden() : "") +" del Folio #"+ idMatricula +" </td>");
			}else{
				out.println("		<td background=\""+request.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\"bgnsub\">Anotaci&oacute;n: N&ordm; "+ (anotacion.getOrden() != null ? anotacion.getOrden() : "") +" </td>");
			}
			out.println("		<td width=\"16\" class=\"bgnsub\"><img src=\""+request.getContextPath()+"/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
			out.println("		<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.println("	</tr>");
			out.println("</table>");

			out.println("<br>");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.println("	<tr>");
			out.println("		<td width=\"20\"><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td width=\"146\">Radicaci&oacute;n</td>");

							String anota = "&nbsp;";

							if(anotacion.getNumRadicacion()==null){
								if(turno!=null){
									anota = "&nbsp;";
								}
							}
							else{
								anota = anotacion.getNumRadicacion();
							}

							//se valida que sea el turno con que se radico la anotacion
							if (turno != null && anotacion.getIdWorkflow() != null) {
								if (!turno.getIdWorkflow().equals(anotacion.getIdWorkflow())) {
									isCorrectoTurno = false;
								}	
							}
							


			out.println("		<td width=\"212\" class=\"campositem\" id=\"a"+ (anotacion.getOrden() != null ? anotacion.getOrden() : "") +" \"> "+ anota+"</td>");

			out.println("		<td width=\"179\">Del</td>");
			out.println("		<td width=\"211\" class=\"campositem\">");
							if (anotacion.getFechaRadicacion()!=null){
								fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
								fechaHelper.setDate(anotacion.getFechaRadicacion());
								fechaHelper.render(request,out);
							}else{
								if(turno!=null){
									fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
									fechaHelper.setDate(turno.getFechaInicio());
									fechaHelper.render(request,out);
								}else{
									out.println("NO DISPONIBLE");
								}
							}

			out.println("		</td>");
			out.println("	</tr>");
			out.println("	<tr>");
			out.println("		<td><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td>Doc</td>");

							if (anotacion.getDocumento()!=null && anotacion.getDocumento().getTipoDocumento().getNombre()!=null && anotacion.getDocumento().getNumero()!=null){

			out.println("		<td class=\"campositem\">"+anotacion.getDocumento().getTipoDocumento().getNombre()+" "+anotacion.getDocumento().getNumero()+"</td>");

							}else{

			out.println("		<td class=\"campositem\">No hay documento</td>");

							}

			out.println("		<td>Del</td>");
			out.println("		<td class=\"campositem\">");

							if (anotacion.getDocumento()!=null && anotacion.getDocumento().getFecha()!=null){
							   fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
							   fechaHelper.setDate(anotacion.getDocumento().getFecha());
							   fechaHelper.render(request,out);
			  				}
		    				else{
		   	   		out.println("	No disponible");
		    				}

			out.println("		</td>");
			out.println("	</tr>");


			//inicializacion datos anotacion
			Documento documento = anotacion.getDocumento();
			OficinaOrigen oficinaOrigen = null;
			String nombreOficinaOrigen = "";
			String nomOficinaOrigen = "";
			Vereda veredaAnotacion = null;
			String nombreMunicipioAnotacion = "";
			String oficinaInternacional="";
			String comentarioOficina = "&nbsp;";
			String comentarioVereda ="&nbsp;";

			String comentarioTemp =anotacion.getComentario();
			String comentarioAnotacion = (comentarioTemp==null)?"&nbsp;":comentarioTemp;
			String estadoFolio="&nbsp;";
			if(anotacion.getEstado()!=null){
				estadoFolio= (anotacion.getEstado().getNombre() !=null)?anotacion.getEstado().getNombre():"&nbsp;";
			}
			boolean cancelacion= (anotacion.getAnotacionesCancelacions().isEmpty()==true)?false:true;

			if(documento!=null){
				if(documento.getOficinaOrigen()!=null){
					oficinaOrigen = (documento==null)?null: documento.getOficinaOrigen();
					nombreOficinaOrigen = (oficinaOrigen==null)?"&nbsp;":oficinaOrigen.getNombre();
					nomOficinaOrigen = (nombreOficinaOrigen==null)?"&nbsp;":nombreOficinaOrigen;
					veredaAnotacion = (oficinaOrigen==null)?null:oficinaOrigen.getVereda();
					nombreMunicipioAnotacion = (veredaAnotacion==null||veredaAnotacion.getMunicipio()==null)?"&nbsp;":veredaAnotacion.getMunicipio().getNombre();
				}else if(documento.getOficinaInternacional()!=null){
					oficinaInternacional= documento.getOficinaInternacional();
				}else if(documento.getComentario()!=null){
					String comentario = documento.getComentario();
					if(comentario.indexOf("-")!=-1){
						StringTokenizer token = new StringTokenizer(comentario, "-");
						int numToken = token.countTokens();
						if (numToken <= 1){
							comentarioOficina = comentario;
						} else {
							comentarioVereda = token.nextToken();
							comentarioOficina = token.nextToken();
						}
					}else{
						comentarioOficina = comentario;
					}
				}
				if(documento.getOficinaOrigen()!=null){
					out.println("	<tr>");
					out.println("		<td><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
					out.println("		<td>Oficina de Or&iacute;gen</td>");
					out.println("		<td class=\"campositem\"> "+nomOficinaOrigen+"</td>");
					out.println("		<td>De</td>");
					out.println("		<td class=\"campositem\"> "+nombreMunicipioAnotacion+"</td>");
					out.println("	</tr>");
				}else if(documento.getOficinaInternacional()!=null){
					out.println("	<tr>");
					out.println("		<td><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
					out.println("		<td>Oficina de Or&iacute;gen (Internacional)</td>");
					out.println("		<td class=\"campositem\"> "+oficinaInternacional+"</td>");
					out.println("		<td>&nbsp;</td>");
					out.println("		<td>&nbsp;</td>");
					out.println("	</tr>");
				}else{
					out.println("	<tr>");
					out.println("		<td><img src=\""+ request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
					out.println("		<td>Oficina de Or&iacute;gen</td>");
					out.println("		<td class=\"campositem\"> "+comentarioOficina+"</td>");
					out.println("		<td>De</td>");
					out.println("		<td class=\"campositem\"> "+comentarioVereda+"</td>");
					out.println("	</tr>");
				}
			}
			out.println("	<tr>");
			out.println("		<td><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td>Valor</td>");
			out.println("		<td class=\"campositem\">");
			out.println("			"+ (anotacion.getValor()!=0?nf.format(anotacion.getValor()):"&nbsp;") +"</td>");
			out.println("		<td>Estado</td>");
			out.println("		<td class=\"campositem\"> "+estadoFolio+"</td>");
			out.println("	</tr>");

			out.println("	<tr>");
			out.println("		<td><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td class=\"titulotbcentral\">Especificaci&oacute;n</td>");
			if(anotacion.getEspecificacion()!=null){
				out.println("		<td class=\"campositem\"><strong>"+ anotacion.getEspecificacion()+"</strong></td>");
			}else{
				out.println("		<td class=\"campositem\"><strong>"+ ((anotacion!=null &&  anotacion.getNaturalezaJuridica()!=null && anotacion.getNaturalezaJuridica().getNombre()!=null)? anotacion.getNaturalezaJuridica().getNombre(): "&nbsp;" )+"</strong></td>");
			}
			//out.println("		<td class=\"campositem\"><strong>" + (anotacion.getEspecificacion() != null ? anotacion.getEspecificacion() : "") + "</strong></td>");
			out.println("     <td>Naturaleza Jur&iacute;dica</td>");
			out.println("     <td class=\"campositem\">" + (anotacion.getNaturalezaJuridica() != null ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "") + "</td>");

			/*if(anotacion.getEspecificacion()!=null){
				out.println("		<td class=\"campositem\" colspan=\"3\"><strong>"+ anotacion.getEspecificacion()+"<strong></td>");
			}else{
				out.println("		<td class=\"campositem\" colspan=\"3\"><strong>"+ ((anotacion!=null &&  anotacion.getNaturalezaJuridica()!=null && anotacion.getNaturalezaJuridica().getNombre()!=null)? anotacion.getNaturalezaJuridica().getNombre(): "&nbsp;" )+"<strong></td>");
			}*/

			out.println("	</tr>");
                        // Modalidad
                        out.println("<tr>");
                        out.println("<td> <img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("<td>Modalidad</td>");
			out.println("<td class=\"campositem\">"+ (anotacion.getModalidad()!= null? anotacion.getModalidad():"&nbsp;")+"</td>");
                        out.println("</tr>");

			//BUG 2091 DESAPARECE COMENTARIO DOC Y DESCRIPCION, SOLO QUEDA COMENTARIO ANOTACIÓN
			out.println("	<tr>");
			out.println("		<td><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td>Comentario</td>");
			out.println("		<td class=\"campositem\">"+ comentarioAnotacion+"</td>");
			out.println("		<td>&nbsp;</td>");


                        // Bug 3543
                        // (adicionalmente se debe trabajar con el id, por la modificacion en el orden)
                        local_EditCriteriaValue = Integer.toString( ordenTemporal );

                        if( ( null != itemEdit_IdentifierFlag )
                          &&( EDITIDENTIFIERFIELD_ID.equals( itemEdit_IdentifierFlag ) ) ) {
                          local_EditCriteriaValue = idTemporal;
                        } // if


                        // Bug 3540

                        boolean anotacion_IsTemporalConContraparteDefinitiva;
                        anotacion_IsTemporalConContraparteDefinitiva = anotacion.isTemporalConContraparteDefinitiva();




							if(anotacion.isTemporal()){

                                                            /// Bug 3540 -----------------------------------------
                                                            String msg_AnotacionTemporal;
                                                            msg_AnotacionTemporal = "Anotaci&oacute;n Temporal";

                                                            String msg_DeleteAnotacionTemporal;
                                                            msg_DeleteAnotacionTemporal = "Eliminar anotaci&oacute;n";

                                                            String tx_JsDeleteAnotacionTemporal;
                                                            tx_JsDeleteAnotacionTemporal = "eliminarAnotacionTemporal";

                                                            String msg_EditAnotacion;
                                                            msg_EditAnotacion = "Editar anotaci&oacute;n";

                                                            

                                                            if( temporalConContraparteDefinitivaEnabled ) {

                                                              if( anotacion_IsTemporalConContraparteDefinitiva ){
                                                                msg_AnotacionTemporal = "Anotaci&oacute;n Definitiva + Cambios";
                                                                msg_DeleteAnotacionTemporal = "Eliminar Cambios Anotaci&oacute;n";
                                                                tx_JsDeleteAnotacionTemporal = "eliminarCambiosAnotacionDefinitivaTemporal";
                                                              } // if

                                                            } // if

                                                            /// ---------------------------------------------------

                                 if((consulta || anotacion.isHeredada()) && (editarAnotacionTemporal)){
										out.println("		<td align=\"left\" width= \"27%\">" + msg_AnotacionTemporal +" &nbsp; &nbsp; <img src=\""+request.getContextPath()+"/jsp/images/ani_temporal.gif\" width=\"20\" height=\"15\">");
										/**
										 * @author Cesar Ramírez
										 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
										 * Obtiene el rol de sesión, si al momento de consultar las anotaciones, si es el rol registrador, no mostrará el botón de eliminar dicha anotación.
										 **/
										org.auriga.core.modelo.transferObjects.Rol rol = (org.auriga.core.modelo.transferObjects.Rol) request.getSession().getAttribute(WebKeys.ROL);
										String rolID = rol != null ? rol.getRolId() : "";
										if (turno!=null){
											if(!rolID.equals(CRol.SIR_ROL_REGISTRADOR)) {
												out.println("		<a href=\"javascript:"+tx_JsDeleteAnotacionTemporal+"('" + local_EditCriteriaValue + "')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" border=\"0\" alt=\""+msg_DeleteAnotacionTemporal+"\"></a>");	
											}
										}
										out.println("		</td>");
								 }else{
									if(!cancelacion){
							
										out.println("		<td align=\"left\" width= \"27%\">"+msg_AnotacionTemporal+" &nbsp; &nbsp; <img src=\""+request.getContextPath()+"/jsp/images/ani_temporal.gif\" width=\"20\" height=\"15\">");
										
										if (turno!=null){
											if (isCorrectoTurno && editarAnotacionTemporal) {
												out.println("			<a href=\"javascript:editarAnotacion('" + local_EditCriteriaValue + "')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_editar.gif\" border=\"0\" alt=\""+msg_EditAnotacion+"\"></a>");
												out.println("			<a href=\"javascript:"+tx_JsDeleteAnotacionTemporal+"('" + local_EditCriteriaValue + "')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" border=\"0\" alt=\""+msg_DeleteAnotacionTemporal+"\"></a>");
											}
										}
										out.println("		</td>");
									}else{
							
										out.println("		<td align=\"left\" width= \"27%\"> "+msg_AnotacionTemporal+" (cancelacion) ::"+"&nbsp; &nbsp; <img src=\""+request.getContextPath()+"/jsp/images/ani_temporal.gif\" width=\"20\" height=\"15\">");
										if (turno!=null){
											if (isCorrectoTurno && editarAnotacionTemporal) {
												out.println("			<a href=\"javascript:editarCancelacion('" + local_EditCriteriaValue + "')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_editar.gif\" border=\"0\" alt=\""+msg_EditAnotacion+"\"></a>");
												out.println("			<a href=\"javascript:"+tx_JsDeleteAnotacionTemporal+"('" + local_EditCriteriaValue + "')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" border=\"0\" alt=\""+msg_DeleteAnotacionTemporal+"\"></a>");
											}
										}
										out.println("		</td>");
									}
								}
							}
                                                        else {

                                                            if( !editarAnotacionNoTemporal ) {
                                                                out.println("	<td>&nbsp;</td>");
                                                            }
                                                            else {

                                                                if( !cancelacion ){
                                                                    out.println(
                                                                            "		<td align=\"right\" width= \"27%\">:: &nbsp; &nbsp; <!-- <img src=\"" +
                                                                            request.getContextPath() +
                                                                            "/jsp/images/ani_temporal.gif\" width=\"20\" height=\"15\"> -->");
                                                                    out.println("			<a href=\"javascript:editarAnotacion('" + local_EditCriteriaValue +
                                                                              "')\"> <img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" border=\"0\" alt=\"Editar anotaci&oacute;n\"></a>");
                                                                    // out.println("<!--			<a href=\"javascript:eliminarAnotacionTemporal('"+ordenTemporal+"')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" border=\"0\" alt=\"Eliminar anotaci&oacute;n\"></a> -->");
                                                                    out.println("		</td>");
                                                                }
                                                                else{
                                                                    out.println(
                                                                            "		<td align=\"right\" width= \"27%\"> (cancelacion) :: &nbsp; &nbsp; <!-- <img src=\"" +
                                                                            request.getContextPath() +
                                                                            "/jsp/images/ani_temporal.gif\" width=\"20\" height=\"15\"> -->");
                                                                    out.println("			<a href=\"javascript:editarCancelacion('" + local_EditCriteriaValue +
                                                                              "')\"> <img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_editar.gif\" border=\"0\" alt=\"Editar anotaci&oacute;n\"></a>");
                                                                    // out.println("<!--			<a href=\"javascript:eliminarAnotacionTemporal('"+ordenTemporal+"')\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_eliminar.gif\" border=\"0\" alt=\"Eliminar anotaci&oacute;n\"></a> -->");
                                                                    out.println("		</td>");

                                                                }
                                                            }


							}
			out.println("	</tr>");

						if(cancelacion){

			out.println("	<tr>");
			out.println("		<td><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td>Cancela a la anotaci&oacute;n </td> ");

							Iterator itCancelacion = anotacion.getAnotacionesCancelacions().iterator();
							String Canceladas="";
							//Cancelacion c= (Cancelacion) itCancelacion.next();
							//Canceladas += (c.getCancelada().getOrden() != null ? c.getCancelada().getOrden() : "");
							String cance[] = new String[anotacion.getAnotacionesCancelacions().size()];
							int contador = 0;
							boolean entroCanceladora = false;
							while(itCancelacion.hasNext()){
								Cancelacion c = (Cancelacion) itCancelacion.next();
								//Canceladas += " ,  ";
								//Canceladas += (c.getCancelada().getOrden() != null ? c.getCancelada().getOrden() : "");
								cance[contador] = (c.getCancelada().getOrden() != null ? c.getCancelada().getOrden() : "");
								//cancel.add((c.getCancelada().getOrden() != null ? c.getCancelada().getOrden() : ""));
								contador++;
								entroCanceladora = true;
							}
							if (entroCanceladora){
								ordenarIdsCancelacion(cance);
								
								for(int i = 0;i<cance.length;i++){
									if (i==0){
										Canceladas = cance[i];
									} else {
										Canceladas += " ,  ";
										Canceladas += cance[i];
									}
								}
							}
							
							
							/*while(itCancelacion.hasNext()){
								c= (Cancelacion) itCancelacion.next();
								Canceladas += " ,  ";
								Canceladas += (c.getCancelada().getOrden() != null ? c.getCancelada().getOrden() : "");
							}*/

			out.println("		<td class=\"campositem\"> "+ Canceladas+"</td>");
			out.println("		<td>&nbsp;</td>");
			out.println("		<td>&nbsp;</td>");
			out.println("	</tr>");

						}

			out.println("</table>");


			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\"camposform\">");
			out.println("	<tr>");
			out.println("		<td width=\"20\"><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.println("		<td class=\"campostitle\"><p>Personas que intervienen en el acto (La X indica a la persona que figura como titular de derechos reales de dominio, I-Titular de dominio incompleto)</p></td>");
			out.println("		<td>&nbsp;</td>");
			out.println("	</tr>");

						if(anotacion.getAnotacionesCiudadanos().isEmpty()){

			out.println("	<tr>");
			out.println("		<td>&nbsp;</td>");
			out.println("		<td>No tiene ciudadanos asociados a esta anotacion</td>");
			out.println("		<td>&nbsp;</td>");
			out.println("		<td>&nbsp;</td>");
			out.println("	</tr>");
			out.println("</table>");

						} else {
							Iterator itCiudadanos = ciudadanosOrdenados.iterator();
							while(itCiudadanos.hasNext()){
									AnotacionCiudadano anCiudadano = (AnotacionCiudadano) itCiudadanos.next();

			out.println("	<tr>");
			out.println("		<td>");

									if (anCiudadano.getRolPersona()!=null){

			out.println("			"+anCiudadano.getRolPersona()+"");

									}else {

			out.println("			&nbsp;");

									}

			out.println("		</td>");
			out.println("		<td class=\"campositem\">"+anCiudadano.getCiudadano().getInfoCiudadano()+" </td>");


			out.println("		<td class=\"campositem\"><span class=\"titresaltados\">"+anCiudadano.getStringMarcaPropietario()+"&nbsp;</span></td>");

			String part="&nbsp;";
			String participacion = "";

			if(anCiudadano.getParticipacion()!=null){
			    participacion=anCiudadano.getParticipacion();
			    if(participacion.length()>3){
			    	part= participacion.substring(0,3);
			    }else{
			    	part= participacion;
			    }
			    participacion=anCiudadano.getParticipacion();
			}

			//mostrar version abreviada y mostrar completo al hacer click en el
			out.println("		<td>Participaci&oacute;n</td> ");
			out.println("     <td class=\"campositem\"  onclick='return overlib(\""+ participacion + "\" , STICKY, MOUSEOFF );' onmouseout='nd();' >"+ participacion   +"</td> ");

			out.println("	</tr>");

							}
			out.println("</table>");

						}
	}
	
	public static void ordenarIdsCancelacion(String a[ ]) {
        
		int numerosArray [] = new int [a.length];
        for(int k = 0; k < a.length; k++) {
        	numerosArray [k] = Integer.valueOf(a[k]).intValue();
        }
        
        Arrays.sort(numerosArray);
        
        for (int k = 0; k < numerosArray.length; k++) {
        	a[k] = String.valueOf(numerosArray[k]);
        }        
    }

	/* (non-Javadoc)
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
			HttpSession session = request.getSession();
			tiposDocHelper = new ListaElementoHelper();
			ciudadanosHelper = new CiudadanosAnotacionHelper();
			textHelper = new TextHelper();
			MFHelper = new MostrarFechaHelper();

			textAreaHelper = new TextAreaHelper();

			turno= (Turno) request.getSession().getAttribute(WebKeys.TURNO);

			String tAnotacionesDef="1000";
			if(hayPaginador){
				DatosRespuestaPaginador RPag= (DatosRespuestaPaginador) request.getSession().getAttribute(this.nombreResultado);
				tAnotacionesDef="0";
				if(RPag!=null){
					tAnotacionesDef=Long.toString(RPag.getCantidadRegistros());
				}
			}
		    /*
			logger.debug("# total de anotaciones = "+ tAnotacionesDef);
		    int nAnotacionesDef= Integer.parseInt(tAnotacionesDef);
		    int nAnotacion = Integer.parseInt(anotacion.getOrden());
		    if( nAnotacion > nAnotacionesDef){
				ordenTemporal= nAnotacion - nAnotacionesDef;
		    }*/

		    String orden = anotacion.getOrden();
		    if(orden == null || orden.length() == 0){
		    	ordenTemporal = 0;
		    } else{
		    	try{
					ordenTemporal=Integer.parseInt(orden);
		    	} catch(Exception e){
					ordenTemporal = 0;
		    	}
		    }

                    // se aplica lo mismo que se estaba haciendo para el orden;
                    String local_IdTemporal = anotacion.getIdAnotacion();

                    if( local_IdTemporal == null || orden.length() == 0 ) {
                      idTemporal = "0";
                    }
                    else {
                       idTemporal = local_IdTemporal;
                    }

			anotaciones = (List) session.getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
			if(anotaciones == null){
				anotaciones = new Vector();
			}

			//ordenar el listado de ciudadanos
			ciudadanosOrdenados=new Vector();
			List temp=anotacion.getAnotacionesCiudadanos();
			Iterator itemp= temp.iterator();
			for(;itemp.hasNext();){
				AnotacionCiudadano cTemp= (AnotacionCiudadano) itemp.next();
				if(cTemp.getRolPersona().equals("DE")){
					ciudadanosOrdenados.add(cTemp);
				}
			}
			itemp= temp.iterator();
			for(;itemp.hasNext();){
				AnotacionCiudadano cTemp= (AnotacionCiudadano) itemp.next();
				if(cTemp.getRolPersona().equals("A")){
					ciudadanosOrdenados.add(cTemp);
				}
			}


	}

	public void setAnotacion(Anotacion a){
		anotacion=a;
	}

	public void setHayPaginador(boolean b){
		hayPaginador=b;
	}

	public void setNombreResultado(String nNombreResultado){
		this.nombreResultado= nNombreResultado;
	}

	/**
	 * @return Returns the idMatricula.
	 */
	public String getIdMatricula() {
		return idMatricula;
	}

	public String getItemEdit_IdentifierFlag() {
		return itemEdit_IdentifierFlag;
	}

	public boolean isTemporalConContraparteDefinitivaEnabled() {
		return temporalConContraparteDefinitivaEnabled;
	}

	/**
	 * @param idMatricula The idMatricula to set.
	 */
	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	/**
	 * @param consulta
	 */
	public void setConsulta(boolean consulta) {
		this.consulta= consulta;
	}

        /**
         * @param editarAnotacionesNoTemporales flag para permitir edicion de anotaciones no temporales
         */
        public void setEditarAnotacionNoTemporal(boolean editarAnotacionNoTemporal) {
                this.editarAnotacionNoTemporal= editarAnotacionNoTemporal;
        }

	public void setItemEdit_IdentifierFlag(String itemEdit_IdentifierFlag) {
		this.itemEdit_IdentifierFlag = itemEdit_IdentifierFlag;
	}

	public void setTemporalConContraparteDefinitivaEnabled(boolean
		 temporalConContraparteDefinitivaEnabled) {
		this.temporalConContraparteDefinitivaEnabled =
			 temporalConContraparteDefinitivaEnabled;
	}

	public boolean isEditarAnotacionTemporal() {
		return editarAnotacionTemporal;
	}

	public void setEditarAnotacionTemporal(boolean editarAnotacionTemporal) {
		this.editarAnotacionTemporal = editarAnotacionTemporal;
	}

}
