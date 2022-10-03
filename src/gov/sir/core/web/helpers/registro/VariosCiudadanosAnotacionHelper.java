/*
 * Created on 05-ene-2005
*/
package gov.sir.core.web.helpers.registro;

import java.io.IOException;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import gov.sir.core.negocio.modelo.AnotacionCiudadano;

import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.util.AnotacionCiudadanoComparator;

import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.ListaTipoPlano;
import gov.sir.core.web.helpers.comun.TextHelper;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author jfrias
 * @author gvillal
 **/
public class VariosCiudadanosAnotacionHelper extends Helper {
    private List ciudadanos;
    private ListaElementoHelper tipoIDHelper;
    private ListaElementoHelper tipoPersonaHelper;
    private ListaElementoHelper tipoSexoHelper;
    private ListaElementoHelper tipoPropetarioHelper;
    private List tiposPropietario;
    private List tiposID;
    private List tiposIDNatural;
    private List tiposIDJuridica;
    private List tiposPersona;
    private List tiposSexo;
    private ListaTipoPlano tipoInterHelper;
    private List tiposParticipacion;

    /**Texto que quedó en el combo después de cambiar el tipo de documento*/
    private TextHelper textHelper;
    private int numFilas;
    private boolean vsec = false;
    private String accionMas;
    private String accionMenos;
    private String accionAnadirVarios;
    private String accionUltimosPropietarios;
    private String accionEliminar;
    private String accionEditar;
    private String accionValidarCiudadano;
    private String form;
    private boolean centrar = true;
    private boolean anotacionNueva = false;
    private String funcionJavascriptAccion = "cambiarAccion";
    private boolean mostrarBotonAgregar = true;
    private boolean mostrarListaCiudadanos = true;
    private boolean mostrarBotonConsultaPropietario = false;
    private String proceso = null;
    

    // observar si esta desabilitado el tipo de intervencion
    // esto se usa para observar los permiosos que tiene la persona en
    // la solicitud de correccion para modificar el tipo de intervencion
    // que tiene el ciudadano.
    private boolean disabledTipoInter = false;


    // edicion de datos de ciudadano
    // sobre todos los folios
    private boolean edicionCiudadanoSobreTodosFolios_FlagEnabled = false;

    // funcion que recibe como
    // parametro el id del ciudadano
    // a modificar
    private String  edicionCiudadanoSobreTodosFolios_JsHandler   = "";


    // edicion de datos de ciudadano
    // campo con el id de ciudadano
    private boolean edicionCiudadanoIdField_FlagEnabled = false;

    // funcion que recibe como
    // parametro el id del ciudadano
    // a modificar
    private String  edicionCiudadanoIdField_WidgetName   = "";





    public void setDisabledTipoInter( boolean disabledTipoInter ) {
        this.disabledTipoInter = disabledTipoInter;
    }

    public boolean isDisabledTipoInter() {
        return this.disabledTipoInter;
    }


    /**
     * Crea una instancia del helper
     */
    public VariosCiudadanosAnotacionHelper() {
        super();
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
     */
    public void drawGUI(HttpServletRequest request, JspWriter out)
        throws HelperException, IOException {
        out.println("<script type='text/javascript'>");
        out.println("function centrar(){");
        out.println("		document.location.href=\"#verCiudadanos\";");
        out.println("	}");
        out.println("function limpiarCampos(text){");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_TIPO_INTER_ASOCIACION+"'+text+'.value =\"A\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_TIPO_ID_PERSONA+"'+text+'.value =\"CC\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_NUM_ID_PERSONA+"'+text+'.value =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_APELLIDO_1_PERSONA+"'+text+'.value =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_APELLIDO_2_PERSONA+"'+text+'.value =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_NOMBRES_PERSONA+"'+text+'.value =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+"'+text+'.value =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_PORCENTAJE_ASOCIACION+"'+text+'.value =\"\"');");
        
        //Desactiva el redonly
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_APELLIDO_1_PERSONA+"'+text+'.readOnly =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_APELLIDO_2_PERSONA+"'+text+'.readOnly =\"\"');");
        out.println("eval('document." + form + '.' + CFolio.ANOTACION_NOMBRES_PERSONA+"'+text+'.readOnly =\"\"');");
        
        out.println("}");

        out.println("function validarCiudadano(text){");
        out.println("if (eval('document." + form + '.' + CFolio.ANOTACION_NUM_ID_PERSONA+"'+text+'.value !=\"\"')){");
        out.write(" document." + form + "." + CAnotacionCiudadano.SECUENCIA +
        ".value = text;");
        out.println("   " + this.funcionJavascriptAccion + "('" +
        accionValidarCiudadano + "');");
        out.println("}");
        out.println("}");
        
        out.println("function getCodVerificacion(text){");
        out.println("var arreglo,x,y,z,i,nit1,dv1;");
        out.println("nit1 = document.getElementById(\"" + CFolio.ANOTACION_NUM_ID_PERSONA +"\" + text).value;");
        out.println("while(nit1.indexOf(\".\") != -1){");
        out.println("nit1 = nit1.replace(\".\",\"\");}");
        out.println("while(nit1.indexOf(\"*\") != -1){");
        out.println("nit1 = nit1.replace(\"*\",\"\");}");
        out.println("while(nit1.indexOf(\",\") != -1){");
        out.println("nit1 = nit1.replace(\",\",\"\");}");
        out.println("while(nit1.indexOf(\"#\") != -1){");
        out.println("nit1 = nit1.replace(\"#\",\"\");}");
        out.println("while(nit1.indexOf(\"/\") != -1){");
        out.println("nit1 = nit1.replace(\"/\",\"\");}");
        out.println("if(isNaN(nit1)){");
        out.println("document.getElementById(\"" + CFolio.ANOTACION_COD_VERIFICACION + "\" + text).value=\"X\";");
        out.println("}else{");
        out.println("arreglo=new Array(16);");
        out.println("x=0;");
        out.println("y=0;");
        out.println("z=nit1.length;");
        out.println("arreglo[1]=3;");
        out.println("arreglo[2]=7;");
        out.println("arreglo[3]=13;");
        out.println("arreglo[4]=17;");
        out.println("arreglo[5]=19;");
        out.println("arreglo[6]=23;");
        out.println("arreglo[7]=29;");
        out.println("arreglo[8]=37;");
        out.println("arreglo[9]=41;");
        out.println("arreglo[10]=43;");
        out.println("arreglo[11]=47;");
        out.println("arreglo[12]=53;");
        out.println("arreglo[13]=59;");
        out.println("arreglo[14]=67;");
        out.println("arreglo[15]=71;");
        out.println("for(i=0;i<z;i++){");
        out.println("y=(nit1.substr(i,1));");
        out.println("x+=(y*arreglo[z-i]);");
        out.println("}");
        out.println("y=x%11;");
        out.println(" if(y>1){");
        out.println("dv1=11-y;");
        out.println("}else{");
        out.println("dv1=y;");
        out.println("}");
        out.println("document.getElementById(\""+ CFolio.ANOTACION_COD_VERIFICACION + "\" + text).value=dv1;");
        out.println("}");
        out.println("}");
        out.println("</script>");
        
        
        //Libreria para mostrar el campo de participacion
		out.println("<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->");
		out.println("<!-- sof:block: \"alt behavior\" -->");
		
		out.println("<script type='text/javascript'>");
		out.println("var ol_fgcolor=\"#ffffc0\";");
		out.println("var ol_border=\"1\";");
		out.println("var ol_bgcolor=\"#FFFFC0\";");
		out.println("var ol_textcolor=\"#000000\";");
		out.println("var ol_capcolor=\"#aaaaaa\";");
		//		var ol_css="forms-help";
		
		out.println("</script>");
		out.println("<style media=\"screen\">");
		out.println(".forms-help {");
		out.println("   border-style: dotted;");
		out.println("   border-width: 1px;");
		out.println("   padding: 5px;");
		out.println("   background-color:#FFFFC0; /* light yellow */");
		out.println("    width: 200px; /* otherwise IE does a weird layout */");
		out.println("   z-index:1000; /* must be higher than forms-tabContent */");
		out.println("}");
		
		out.println("</style>");
		out.println("<script type=\"text/javascript\" src=\""+ request.getContextPath()+"/jsp/plantillas/privileged/overlib.js\"><!-- overLIB (c) Erik Bosrup --></script>");
		out.println("<div id=\"overDiv\" style=\"position:absolute; visibility:hidden; z-index:1000;\"></div>");
		
		out.println("<!-- eof:block -->");
		out.println("<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->");	

        if (mostrarListaCiudadanos){
            out.println(
            "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.println("<tr>");
            out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
                request.getContextPath() +
                "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.println(
                "<td class=\"bgnsub\">Personas que intervienen ya Adicionadas a la Anotación</td>");
            out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
                request.getContextPath() +
                "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
            out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
                request.getContextPath() +
                "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.println("</tr>");
            out.println("</table>");
        }

        out.println("<a name=\"#verCiudadanos\"></a>");

        if (centrar && !ciudadanos.isEmpty()) {
            out.println("<script>centrar();</script>");
        }

        //Tablas donde se muestran los ciudadanos insertados
        if (mostrarListaCiudadanos){
            if (!ciudadanos.isEmpty()) {
                out.println("<table width=\"100%\" class=\"camposform\">");

                int tam = ciudadanos.size();

                for (int i = 0; i < tam; i++) {
                    AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) ciudadanos.get(i);

                    if (anotacionCiudadano.isToDelete()) {
                    } else {
                    	String part = "&nbsp;";
                        String participacion ="";
                        String rol = "&nbsp;";
                        String apellido1 = "&nbsp;";
                        String apellido2 = "&nbsp;";
                        String nombre = "&nbsp;";
                        String nombreCompleto = "&nbsp;";
                        String marcaProp = "&nbsp;";
                        String tipoDoc = "&nbsp;";
                        String tipoPersona = "&nbsp;";
                        String sexo = "&nbsp;";
                        String documento = "&nbsp;";

                        if(anotacionCiudadano.getParticipacion()!=null){
            			    participacion=anotacionCiudadano.getParticipacion();
            			    /*if(participacion.length()>3){
        				    	part= participacion.substring(0,3);
        				    }else{
        				    	part= participacion;
        				    }
        				    participacion=anotacionCiudadano.getParticipacion();*/
            			    
            			    part= participacion;
            			}

                        if (anotacionCiudadano.getRolPersona() != null) {
                            rol = anotacionCiudadano.getRolPersona();
                        }

                        if (anotacionCiudadano.getCiudadano().getApellido1() != null) {
                            apellido1 = anotacionCiudadano.getCiudadano()
                                                          .getApellido1();
                        }

                        if (anotacionCiudadano.getCiudadano().getApellido2() != null) {
                            apellido2 = anotacionCiudadano.getCiudadano()
                                                          .getApellido2();
                        }

                        if (anotacionCiudadano.getCiudadano().getNombre() != null) {
                            nombre = anotacionCiudadano.getCiudadano().getNombre();
                        }

                        nombreCompleto = apellido1 + " " + apellido2 + " " +
                            nombre + " ";

                        if (nombreCompleto.equals("")) {
                            nombreCompleto = "&nbsp;";
                        }

                        if (anotacionCiudadano.getStringMarcaPropietario() != null) {
                            marcaProp = anotacionCiudadano.getStringMarcaPropietario();
                        }

                        if (anotacionCiudadano.getCiudadano().getTipoDoc() != null) {
                            tipoDoc = anotacionCiudadano.getCiudadano().getTipoDoc();
                        }

                        if (anotacionCiudadano.getCiudadano().getDocumento() != null) {
                            documento = anotacionCiudadano.getCiudadano()
                                                          .getDocumento();
                        }
                        
                        if (anotacionCiudadano.getCiudadano().getSexo() != null) {
                            sexo = anotacionCiudadano.getCiudadano().getSexo();
                        }

                        if (anotacionCiudadano.getCiudadano().getTipoPersona() != null) {
                            tipoPersona = anotacionCiudadano.getCiudadano().getTipoPersona();
                        }
                        
                    	if(proceso!=null && proceso.equals(CProceso.PROCESO_CORRECCIONES)){
                    		out.println("<tr>");
                            out.println("<td width=\"20\"><img src=\"" +
                                request.getContextPath() +
                                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
                            
                            out.println("<td>");
                            
                            
                            if(anotacionNueva){
                            	if (tiposParticipacion != null) {
                                    tipoInterHelper.setCssClase("camposformtext");
                                    tipoInterHelper.setId(CAnotacionCiudadano.ANOTACION_TIPO_INTER_ASOCIACION_EDIT +
                                        i);
                                    tipoInterHelper.setNombre(CAnotacionCiudadano.ANOTACION_TIPO_INTER_ASOCIACION_EDIT +
                                        i);
                                    tipoInterHelper.setListaTipos(tiposParticipacion);

                                    if( isDisabledTipoInter() ) {
                                        tipoInterHelper.setDisabled(true);
                                    }
                                    tipoInterHelper.setSelected(rol);
                                }
                                tipoInterHelper.render(request, out);
                            }else{
                            	out.println("<td class=\"titulotbcentral\">" + rol +
                                "</td>");
                            }
                            

                            out.println("</td>");
                            out.println("<td>" + tipoPersona + "</td>");
                            out.println("<td>" + tipoDoc + "</td>");
                            out.println("<td>" + documento + "</td>");
                            out.println("<td>" + nombreCompleto + "</td>");
                            out.println("<td>" + sexo + "</td>");
                            
                            out.println("<td>Propietario: ");

                            if (tiposPropietario != null) {
                                tipoInterHelper.setCssClase("camposformtext");
                                tipoInterHelper.setId(CAnotacionCiudadano.ANOTACION_ES_PROPIETARIO_ASOCIACION_EDIT +
                                    i);
                                tipoInterHelper.setNombre(CAnotacionCiudadano.ANOTACION_ES_PROPIETARIO_ASOCIACION_EDIT +
                                    i);
                                tipoInterHelper.setListaTipos(tiposPropietario);
                            }
                            tipoInterHelper.setSelected(marcaProp);
                            tipoInterHelper.render(request, out);
                            out.println("</td>");
                            
                            String key = CAnotacionCiudadano.ANOTACION_PORCENTAJE_ASOCIACION_EDIT + i;
                            String keycom = "\"" + key + "\"";
                            String valorcom = "\"" + participacion + "\"";
                            String classcom = "\"camposformtext\"";
                            out.println("<td>Partic: " + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\"" +
                                "text" + "\"" + " class=" + classcom + " id=" + keycom +
                                " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">" +
                                "</td>");
                            
                            out.println(
                                    "<td width=\"40\"><a href=\"javascript:quitar('" + i +
                                    "','" + accionEliminar +
                                    "')\"><img name=\"imageField\" src=\"" +
                                    request.getContextPath() +
                                    "/jsp/images/btn_short_eliminar.gif\" width=\"35\" height=\"25\" border=\"0\" alt=\"Eliminar Ciudadano\" /></a></td>");

                                  //
                                  //

                          if( isEdicionCiudadanoSobreTodosFolios_FlagEnabled() ){
                            // AnotacionCiudadano anotacionCiudadano = anotacionCiudadano;
                            StringBuffer htmBuffer = new StringBuffer( 1024 );
                            htmBuffer.append(  "<td>"  );
                            // modificado, para que se haga cambio de cursor
                            htmBuffer.append(  " <a " );

                            String local_Ciudadano_Id = anotacionCiudadano.getIdCiudadano();
                            if( null == local_Ciudadano_Id ){
                              htmBuffer.append(  "href='"+ "javascript:alert" + "(" + "\"" + "No se ha confirmado la presencia de este ciduadano sobre otros folios; Para realizar este procedimiento se debe guardar la anotacion y volver a editar el ciudadano." + "\"" + ")" + "'" + " " );
                            }
                            else {
                              htmBuffer.append(  "href='"+ edicionCiudadanoSobreTodosFolios_JsHandler + "(" + "\"" + anotacionCiudadano.getIdCiudadano() + "\"" + ")" + "'" + " " );
                            }
                            htmBuffer.append(  " >"  );
                            htmBuffer.append(  "  <img alt='edicion ciudadano sobre todos los folios' src='" + request.getContextPath()+ "/jsp/images/btn_mini_editar.gif' border='0'  /> "  );
                            htmBuffer.append(  " </a>"  );
                            htmBuffer.append(  "</td>"  );
                            out.println( htmBuffer.toString() );

                          }
                                  
                          /*out.println(
                                  "<td><a href=\"javascript:guardarCambio('" + i +
                                  "','" + accionEditar +
                                  "')\"><img name=\"imageField\" src=\"" +
                                  request.getContextPath() +
                                  "/jsp/images/btn_mini_guardar.gif\" width=\"35\" height=\"13\" border=\"0\" alt=\"Eliminar Ciudadano\" /></a></td>");*/

                          out.println("</tr>");
                            
                    	} else {
                    		out.println("<tr>");
                            out.println("<td width=\"20\"><img src=\"" +
                                request.getContextPath() +
                                "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");

                            out.println("<td class=\"titulotbcentral\">" + rol +
                                "</td>");
                            out.println("<td>" + tipoPersona + "</td>");
                            out.println("<td>" + tipoDoc + "</td>");
                            out.println("<td>" + documento + "</td>");
                            out.println("<td>" + nombreCompleto + "</td>");
                            out.println("<td>" + sexo + "</td>");
                            out.println(
                                "<td width=\"20\" align=\"center\" class=\"titresaltados\">" +
                                marcaProp + "</td>");

//                          mostrar version abreviada y mostrar completo al hacer click en el
            				out.println("<td onclick='return overlib(\""+ participacion + "\" , STICKY, MOUSEOFF );' onmouseout='nd();' >&nbsp;&nbsp;"+ participacion   +"&nbsp;&nbsp;</td> ");
            				//out.println("<td onclick='return overlib(\""+ participacion + "\" , STICKY, MOUSEOFF );' onmouseout='nd();' >&nbsp;&nbsp;"+ part   +"&nbsp;&nbsp;</td> ");        				
                            out.println(
                                "<td width=\"40\"><a href=\"javascript:quitar('" + i +
                                "','" + accionEliminar +
                                "')\"><img name=\"imageField\" src=\"" +
                                request.getContextPath() +
                                "/jsp/images/btn_short_eliminar.gif\" width=\"35\" height=\"25\" border=\"0\" alt=\"Eliminar Ciudadano\" /></a></td>");

                              //
                              //

                              if( isEdicionCiudadanoSobreTodosFolios_FlagEnabled() ){
                                // AnotacionCiudadano anotacionCiudadano = anotacionCiudadano;
                                StringBuffer htmBuffer = new StringBuffer( 1024 );
                                htmBuffer.append(  "<td>"  );
                                // modificado, para que se haga cambio de cursor
                                htmBuffer.append(  " <a " );

                                String local_Ciudadano_Id = anotacionCiudadano.getIdCiudadano();
                                if( null == local_Ciudadano_Id ){
                                  htmBuffer.append(  "href='"+ "javascript:alert" + "(" + "\"" + "No se ha confirmado la presencia de este ciduadano sobre otros folios; Para realizar este procedimiento se debe guardar la anotacion y volver a editar el ciudadano." + "\"" + ")" + "'" + " " );
                                }
                                else {
                                  htmBuffer.append(  "href='"+ edicionCiudadanoSobreTodosFolios_JsHandler + "(" + "\"" + anotacionCiudadano.getIdCiudadano() + "\"" + ")" + "'" + " " );
                                }
                                htmBuffer.append(  " >"  );
                                htmBuffer.append(  "  <img alt='edicion ciudadano sobre todos los folios' src='" + request.getContextPath()+ "/jsp/images/btn_mini_editar.gif' border='0'  /> "  );
                                htmBuffer.append(  " </a>"  );
                                htmBuffer.append(  "</td>"  );
                                out.println( htmBuffer.toString() );

                              }


                            out.println("</tr>");
                        }
                    }
                }

                out.println("</table>");
            } else {
                out.println("<table width=\"100%\" class=\"camposform\">");
                out.println("<tr>");
                out.println("<td width=\"20\"><img src=\"" +
                    request.getContextPath() +
                    "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
                out.println("<td class=\"titulotbcentral\">" +
                    "No hay ciudadanos relacionados con la anotación" + "</td>");
                out.println("</tr>");
                out.println("</table>");
            }
        }


        out.println("<br>");
        out.println(
            "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.println("<tr>");
        out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.println(
            "<td class=\"bgnsub\">Adicionar Personas que intervienen en el Acto</td>");
        out.println("<td width=\"16\" class=\"bgnsub\"><img src=\"" +
            request.getContextPath() +
            "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
        out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" +
            request.getContextPath() +
            "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<table width=\"100%\" class=\"camposform\">");
        out.println("<tr>");
        out.println("<td width=\"20\"><img src=\"" + request.getContextPath() +
            "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
        out.println("<td>Agregar Ciudadanos</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>&nbsp;</td>");
        out.println(
            "<td><table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\" class=\"camposform\">");
        out.println("<tr class=\"tdtablacentral\">");
        out.println("<td align=\"center\">Tipo<br>");
        out.println("Intervenci&oacute;n </td>");
        out.println("<td align=\"center\">Tipo<br>");
        out.println("de Persona</td>");
        out.println("<td align=\"center\">Tipo<br>");
        out.println("de documento </td>");
        out.println("<td align=\"center\">N&uacute;mero</td>");
        out.println("<td align=\"center\">Primer Apellido </td>");
        out.println("<td align=\"center\">Segundo Apellido </td>");
        out.println("<td align=\"center\">Nombres / Raz&oacuten Social</td>");
        out.println("<td align=\"center\">Sexo</td>");
        out.println("<td align=\"center\">Propietario</td>");
        out.println("<td align=\"center\">Partic</td>");
        out.println("<td align=\"center\">Borrar</td>");
        // if( edicionCiudadanoSobreTodosFolios_FlagEnabled ) {
        //  out.println("<td align=\"center\">Modif</td>");
        // }
        out.println("</tr>");

        String valor = "";
        String key;
        String keycom;
        String classcom = "\"camposformtext\"";
        String valorcom;

        boolean tipoInterHelperPreviousState = false;

        if (!mostrarListaCiudadanos){

            int j=0;
            for (; j<ciudadanos.size();j++){
                AnotacionCiudadano ciud=(AnotacionCiudadano)ciudadanos.get(j);
                out.println("<tr align=\"center\">");

                boolean mostrar = false;
                Boolean m = (Boolean) request.getSession().getAttribute(CFolio.CIUDADANO_EDITABLE +
                        j);

                if (m != null) {
                    mostrar = m.booleanValue();
                }

                out.println("<td>");

                if (tiposParticipacion != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                        j);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                        j);
                    tipoInterHelper.setListaTipos(tiposParticipacion);
                    tipoInterHelper.setSelected(ciud.getRolPersona());

                    tipoInterHelperPreviousState = tipoInterHelper.isDisabled();

                    if( isDisabledTipoInter() ) {
                        tipoInterHelper.setDisabled(true);
                    }
                }

                tipoInterHelper.render(request, out);
                // restore state for widget;
                tipoInterHelper.setDisabled( tipoInterHelperPreviousState );
                tipoInterHelper.setSelected(null);
                out.println("</td>");

                /*out.println("<td>");
                if(tiposParticipacion != null)
                {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+i);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+i);
                        tipoInterHelper.setListaTipos(tiposPropietario);
                }
                tipoInterHelper.render(request,out);
                out.println("</td>");*/
                
                out.println("<td>");

                if (tiposPersona != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_PERSONA
                            + j);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_PERSONA
                            + j);
                    tipoInterHelper.setListaTipos(tiposPersona);
                    tipoInterHelper.setFuncion("onChange=\"Secuencia('" + j + "')\"");

                    tipoInterHelperPreviousState = tipoInterHelper.isDisabled();

                    if (isDisabledTipoInter()) {
                        tipoInterHelper.setDisabled(true);
                    }

                }
                tipoInterHelper.render(request, out);
                tipoInterHelper.setDisabled(tipoInterHelperPreviousState);

                out.println("</td>");
                
                String tipoPersona = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_PERSONA + j);
                if (tipoPersona != null && tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                    out.println("<td>");
                    if (tiposIDNatural != null) {
                        if (request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + j) == null) {
                            tipoIDHelper.setSelected(CCiudadano.TIPO_DOC_ID_CEDULA);
                        }

                        tipoIDHelper.setCssClase("camposformtext");
                        tipoIDHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA + j);
                        tipoIDHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA + j);
                        tipoIDHelper.setOrdenar(false);
                        tipoIDHelper.setTipos(tiposIDNatural);
                        tipoIDHelper.setFuncion("onChange=\"Secuencia('" + j + "')\"");
                    }

                    tipoIDHelper.render(request, out);
                    out.println("</td>");

                    key = CFolio.ANOTACION_NUM_ID_PERSONA + j;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    //valor combo tipo id
                    String vs = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + j);

                    if (vs == null) {
                        vs = "";
                    } else {
                        if (centrar) {
                            out.println("<script>centrar();</script>\n");
                        }
                    }

                    if (valorcom.equals("\"\"")) {
                        mostrar = false;
                    }

                    if (!vs.equals("SE")) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " onBlur=\"validarCiudadano('" + j + "')\">"
                                + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "hidden" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_APELLIDO_1_PERSONA + j;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                + " maxlength=" + "\"" + "150" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                + " maxlength=" + "\"" + "150" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_APELLIDO_2_PERSONA + j;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_NOMBRES_PERSONA + j;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "300" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "300" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }
                    
                    if(!vs.equals("SE")){
                    out.println("<td>");

                    if (tiposSexo != null) {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_SEXO_PERSONA
                                + j);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_SEXO_PERSONA
                                + j);
                        tipoInterHelper.setListaTipos(tiposSexo);
                        if (isDisabledTipoInter()) {
                            tipoInterHelper.setDisabled(true);
                        }

                    }
                    tipoInterHelper.render(request, out);
                    tipoInterHelper.setDisabled(tipoInterHelperPreviousState);

                    out.println("</td>");
                    }

                    out.println("<td>");

                    if (tiposParticipacion != null) {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                + j);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                + j);
                        tipoInterHelper.setListaTipos(tiposPropietario);
                        if (request.getSession().getAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + j) == null) {
                            tipoInterHelper.setSelected("");
                        }
                    }

                    tipoInterHelper.render(request, out);
                    out.println("</td>");

                    key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + j;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";
                    out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\""
                            + "text" + "\"" + " class=" + classcom + " id=" + keycom
                            + " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">"
                            + "</td>");
                     
                    out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"limpiarCampos('" + j + "')\" alt=\"Limpiar campos\" width=\"35\" height=\"13\" border=\"0\"></td>");
                    out.println("</tr>\n");
                } else {
                    if (tipoPersona != null && tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                        out.println("<td>");
                        
                    if (tiposIDJuridica != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + j);
                    tipoInterHelper.setListaTipos(tiposIDJuridica);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + j);
                    tipoInterHelper.setFuncion("onChange=\"Secuencia('" + j + "')\"");

                    tipoInterHelperPreviousState = tipoInterHelper.isDisabled();

                    if (isDisabledTipoInter()) {
                        tipoInterHelper.setDisabled(true);
                    }

                }
                tipoInterHelper.render(request, out);
                tipoInterHelper.setDisabled(tipoInterHelperPreviousState);
                    out.println("</td>");
                    
                        out.println("</td>");

                        key = CFolio.ANOTACION_NUM_ID_PERSONA + j;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        //valor combo tipo id
                        String vs = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                                + j);

                        if (vs == null) {
                            vs = "";
                        } else {
                            if (centrar) {
                                out.println("<script>centrar();</script>\n");
                            }
                        }

                        if (valorcom.equals("\"\"")) {
                            mostrar = false;
                        }

                        TextHelper helper = new TextHelper();
                        if (!vs.equals("SE")) {
                            /*
                         ** Construye el campo donde se digita el numero de NIT
                             */
                            out.println("<td >");
                            helper.setId(key);
                            helper.setNombre(key);
                            helper.setSize("10");
                            helper.setMaxlength("15");
                            helper.setCssClase("camposformtext");
                            helper.setReadonly(false);
                            helper.setFuncion("onBlur=\"validarCiudadano('" + j + "')\"");
                            helper.render(request, out);
                            /*
                         ** Construye el campo donde se muestra el dígito de verificación
                         ** del NIT digitado.
                         ** onChange=\"getCodVerificacion('" + j + "')\" 
                            out.println(" - ");
                            helper.setId(CFolio.ANOTACION_COD_VERIFICACION + j);
                            helper.setNombre(CFolio.ANOTACION_COD_VERIFICACION + j);
                            helper.setSize("1");
                            helper.setMaxlength("1");
                            helper.setCssClase("camposformtext");
                            helper.setReadonly(true);
                            helper.render(request, out);
                             */
                            out.write("</td>");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "hidden" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_APELLIDO_1_PERSONA + j;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + "type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                    + " maxlength=" + "\"" + "150" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                    + " maxlength=" + "\"" + "150" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_APELLIDO_2_PERSONA + j;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_NOMBRES_PERSONA + j;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "text" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "300" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "text" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "300" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_SEXO_PERSONA + j;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "hidden" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");

                        out.println("<td>");

                        if (tiposParticipacion != null) {
                            tipoInterHelper.setCssClase("camposformtext");
                            tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                    + j);
                            tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                    + j);
                            tipoInterHelper.setListaTipos(tiposPropietario);
                            if (request.getSession().getAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + j) == null) {
                                tipoInterHelper.setSelected("");
                            }
                        }

                        tipoInterHelper.render(request, out);
                        out.println("</td>");

                        key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + j;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\""
                                + "text" + "\"" + " class=" + classcom + " id=" + keycom
                                + " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">"
                                + "</td>");
                        
                        out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"limpiarCampos('" + j + "')\" alt=\"Limpiar campos\" width=\"35\" height=\"13\" border=\"0\"></td>");
                        out.println("</tr>\n");    
                }
                }
                   
                if( isEdicionCiudadanoSobreTodosFolios_FlagEnabled() ){
                  AnotacionCiudadano anotacionCiudadano = ciud;
                  StringBuffer htmBuffer = new StringBuffer( 1024 );
                  htmBuffer.append(  "<td>"  );
                  htmBuffer.append(  "<img alt='edicion ciudadano sobre todos los folios' " );

                  String local_Ciudadano_Id = anotacionCiudadano.getIdCiudadano();
                  if( null == local_Ciudadano_Id ){
                    htmBuffer.append(  "onClick='"+ "javascript:alert" + "(" + "\"" + "No se ha confirmado la presencia de este ciudadano sobre otros folios; Para realizar este procedimiento se debe guardar la anotacion y volver a editar el ciudadano." + "\"" + ")" + "'" + " " );
                  }
                  else {
                    htmBuffer.append(  "onClick='"+ edicionCiudadanoSobreTodosFolios_JsHandler + "(" + "\"" + anotacionCiudadano.getIdCiudadano() + "\"" + ")" + "'" + " " );
                  }
                  htmBuffer.append(       "src='" + request.getContextPath()+ "/jsp/images/btn_mini_editar.gif'  />"  );
                  htmBuffer.append(  "</td>"  );
                  out.println( htmBuffer.toString() );

                }
                out.println("</tr>\n");

            }

            for (int i = j; i < this.numFilas+ciudadanos.size(); i++) {
                out.println("<tr align=\"center\">");

                boolean mostrar = false;
                Boolean m = (Boolean) request.getSession().getAttribute(CFolio.CIUDADANO_EDITABLE +
                        i);

                if (m != null) {
                    mostrar = m.booleanValue();
                }

                out.println("<td>");

                if (tiposParticipacion != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                        i);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                        i);
                    tipoInterHelper.setListaTipos(tiposParticipacion);
                }

                tipoInterHelper.render(request, out);
                out.println("</td>");

                /*out.println("<td>");
                if(tiposParticipacion != null)
                {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+i);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+i);
                        tipoInterHelper.setListaTipos(tiposPropietario);
                }
                tipoInterHelper.render(request,out);
                out.println("</td>");*/
                out.println("<td>");

                if (tiposPersona != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_PERSONA
                            + i);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_PERSONA
                            + i);
                    tipoInterHelper.setListaTipos(tiposPersona);
                    tipoInterHelper.setFuncion("onChange=\"Secuencia('" + i + "')\"");

                    tipoInterHelperPreviousState = tipoInterHelper.isDisabled();

                    if (isDisabledTipoInter()) {
                        tipoInterHelper.setDisabled(true);
                    }

                }
                tipoInterHelper.render(request, out);
                tipoInterHelper.setDisabled(tipoInterHelperPreviousState);

                out.println("</td>");
                //Valor Combo
                String tipoPersona = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
                 
                if (tipoPersona != null && tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                   out.println("<td>");
                    if (tiposIDNatural != null) {
                        if (request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + i) == null) {
                            tipoIDHelper.setSelected(CCiudadano.TIPO_DOC_ID_CEDULA);
                        }

                        tipoIDHelper.setCssClase("camposformtext");
                        tipoIDHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
                        tipoIDHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
                        tipoIDHelper.setOrdenar(false);
                        tipoIDHelper.setTipos(tiposIDNatural);
                        tipoIDHelper.setFuncion("onChange=\"Secuencia('" + i + "')\"");
                    }

                    tipoIDHelper.render(request, out);
                    out.println("</td>");
         
                    key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    //valor combo tipo id
                    String vs = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + i);

                    if (vs == null) {
                        vs = "";
                    } else {
                        if (centrar) {
                            out.println("<script>centrar();</script>\n");
                        }
                    }

                    if (valorcom.equals("\"\"")) {
                        mostrar = false;
                    }

                    if (!vs.equals("SE")) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " onBlur=\"validarCiudadano('" + i + "')\">"
                                + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "hidden" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                + " maxlength=" + "\"" + "150" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                + " maxlength=" + "\"" + "150" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "300" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "300" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }
                    
                    if(!vs.equals("SE")){
                    out.println("<td>");

                    if (tiposSexo != null) {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_SEXO_PERSONA
                                + i);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_SEXO_PERSONA
                                + i);
                        tipoInterHelper.setListaTipos(tiposSexo);
                        if (isDisabledTipoInter()) {
                            tipoInterHelper.setDisabled(true);
                        }

                    }
                    tipoInterHelper.render(request, out);
                    tipoInterHelper.setDisabled(tipoInterHelperPreviousState);

                    out.println("</td>");
                    }
                    
                    out.println("<td>");

                    if (tiposParticipacion != null) {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                + i);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                + i);
                        tipoInterHelper.setListaTipos(tiposPropietario);
                        if (request.getSession().getAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i) == null) {
                            tipoInterHelper.setSelected("");
                        }
                    }

                    tipoInterHelper.render(request, out);
                    out.println("</td>");

                    key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";
                    out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\""
                            + "text" + "\"" + " class=" + classcom + " id=" + keycom
                            + " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">"
                            + "</td>");
                     
                    out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"limpiarCampos('" + i + "')\" alt=\"Limpiar campos\" width=\"35\" height=\"13\" border=\"0\"></td>");
                    out.println("</tr>\n");
                } else {
                    
                    if (tipoPersona != null && tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                        out.println("<td>");
                        
                       if (tiposIDJuridica != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + i);
                    tipoInterHelper.setListaTipos(tiposIDJuridica);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + i);
                    tipoInterHelper.setFuncion("onChange=\"Secuencia('" + i + "')\"");

                    tipoInterHelperPreviousState = tipoInterHelper.isDisabled();

                    if (isDisabledTipoInter()) {
                        tipoInterHelper.setDisabled(true);
                    }

                }
                tipoInterHelper.render(request, out);
                tipoInterHelper.setDisabled(tipoInterHelperPreviousState);
                    out.println("</td>");
                    
                        out.println("</td>");

                        key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        //valor combo tipo id
                        String vs = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                                + i);

                        if (vs == null) {
                            vs = "";
                        } else {
                            if (centrar) {
                                out.println("<script>centrar();</script>\n");
                            }
                        }

                        if (valorcom.equals("\"\"")) {
                            mostrar = false;
                        }

                        TextHelper helper = new TextHelper();
                        if (!vs.equals("SE")) {
                            /*
                         ** Construye el campo donde se digita el numero de NIT
                             */
                            out.println("<td >");
                            helper.setId(key);
                            helper.setNombre(key);
                            helper.setSize("10");
                            helper.setMaxlength("15");
                            helper.setCssClase("camposformtext");
                            helper.setReadonly(false);
                            helper.setFuncion("onChange=\"getCodVerificacion('" + i + "')\" onBlur=\"validarCiudadano('" + i + "')\"");
                            helper.render(request, out);
                            out.println(" - ");
                            /*
                         ** Construye el campo donde se muestra el dígito de verificación
                         ** del NIT digitado.
                             */
                            helper.setId(CFolio.ANOTACION_COD_VERIFICACION + i);
                            helper.setNombre(CFolio.ANOTACION_COD_VERIFICACION + i);
                            helper.setSize("1");
                            helper.setMaxlength("1");
                            helper.setCssClase("camposformtext");
                            helper.setReadonly(true);
                            helper.render(request, out);
                            out.write("</td>");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "hidden" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + "type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                    + " maxlength=" + "\"" + "150" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                    + " maxlength=" + "\"" + "150" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "text" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "300" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "text" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "300" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_SEXO_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "hidden" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");

                        out.println("<td>");

                        if (tiposParticipacion != null) {
                            tipoInterHelper.setCssClase("camposformtext");
                            tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                    + i);
                            tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                    + i);
                            tipoInterHelper.setListaTipos(tiposPropietario);
                            if (request.getSession().getAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i) == null) {
                                tipoInterHelper.setSelected("");
                            }
                        }

                        tipoInterHelper.render(request, out);
                        out.println("</td>");

                        key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\""
                                + "text" + "\"" + " class=" + classcom + " id=" + keycom
                                + " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">"
                                + "</td>");
                        
                        out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"limpiarCampos('" + i + "')\" alt=\"Limpiar campos\" width=\"35\" height=\"13\" border=\"0\"></td>");
                        out.println("</tr>\n");

                    }
                }
            }
        }else{
            for (int i = 0; i < this.numFilas; i++) {
                out.println("<tr align=\"center\">");

                boolean mostrar = false;
                Boolean m = (Boolean) request.getSession().getAttribute(CFolio.CIUDADANO_EDITABLE +
                        i);

                if (m != null) {
                    mostrar = m.booleanValue();
                }

                out.println("<td>");

                if (tiposParticipacion != null) {
                    tipoInterHelper.setCssClase("camposformtext");
                    tipoInterHelper.setId(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                        i);
                    tipoInterHelper.setNombre(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                        i);
                    tipoInterHelper.setListaTipos(tiposParticipacion);

                    tipoInterHelperPreviousState = tipoInterHelper.isDisabled();

                    if( isDisabledTipoInter() ) {
                        tipoInterHelper.setDisabled(true);
                    }

                }

                tipoInterHelper.render(request, out);

                tipoInterHelper.setDisabled( tipoInterHelperPreviousState );

                out.println("</td>");

                /*out.println("<td>");
                if(tiposParticipacion != null)
                {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+i);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION+i);
                        tipoInterHelper.setListaTipos(tiposPropietario);
                }
                tipoInterHelper.render(request,out);
                out.println("</td>");*/
                out.println("<td>");

                if (tiposPersona != null) {
                    tipoPersonaHelper.setCssClase("camposformtext");
                    tipoPersonaHelper.setId(CFolio.ANOTACION_TIPO_PERSONA + i);
                    tipoPersonaHelper.setNombre(CFolio.ANOTACION_TIPO_PERSONA + i);
                    tipoPersonaHelper.setOrdenar(false);
                    tipoPersonaHelper.setTipos(tiposPersona);
                    tipoPersonaHelper.setFuncion("onChange=\"Secuencia('" + i + "')\"");
                }
                tipoPersonaHelper.render(request, out);

                out.println("</td>");
                //Valor Combo
                String tipoPersona = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
           
                if (tipoPersona != null && tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                   out.println("<td>");
                    if (tiposIDNatural != null) {
                        if (request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + i) == null) {
                            tipoIDHelper.setSelected(CCiudadano.TIPO_DOC_ID_CEDULA);
                        }

                        tipoIDHelper.setCssClase("camposformtext");
                        tipoIDHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
                        tipoIDHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
                        tipoIDHelper.setOrdenar(false);
                        tipoIDHelper.setTipos(tiposIDNatural);
                        tipoIDHelper.setFuncion("onChange=\"Secuencia('" + i + "')\"");
                    }

                    tipoIDHelper.render(request, out);
                    out.println("</td>");

                    key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    //valor combo tipo id
                    String vs = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                            + i);

                    if (vs == null) {
                        vs = "";
                    } else {
                        if (centrar) {
                            out.println("<script>centrar();</script>\n");
                        }
                    }

                    if (valorcom.equals("\"\"")) {
                        mostrar = false;
                    }

                    if (!vs.equals("SE")) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " onBlur=\"validarCiudadano('" + i + "')\">"
                                + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "hidden" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                + " maxlength=" + "\"" + "150" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                + " maxlength=" + "\"" + "150" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }

                    key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";

                    if (!mostrar) {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "300" + "\"" + " value="
                                + valorcom + ">" + "</td>\n");
                    } else {
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "text" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "300" + "\"" + " value="
                                + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                    }
                    
                    out.println("<td>");
                    if (tiposSexo != null) {
                        tipoSexoHelper.setCssClase("camposformtext");
                        tipoSexoHelper.setId(CFolio.ANOTACION_SEXO_PERSONA + i);
                        tipoSexoHelper.setNombre(CFolio.ANOTACION_SEXO_PERSONA + i);
                        tipoSexoHelper.setOrdenar(false);
                        tipoSexoHelper.setTipos(tiposSexo);
                    }
                    tipoSexoHelper.render(request, out);
                    out.println("</td>");
                    
                    
                    out.println("<td>");

                    if (tiposParticipacion != null) {
                        tipoInterHelper.setCssClase("camposformtext");
                        tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                + i);
                        tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                + i);
                        tipoInterHelper.setListaTipos(tiposPropietario);
                        if (request.getSession().getAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i) == null) {
                            tipoInterHelper.setSelected("");
                        }
                    }

                    tipoInterHelper.render(request, out);
                    out.println("</td>");

                    key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
                    keycom = "\"" + key + "\"";
                    valor = getValorFromSession(request, key);
                    valorcom = "\"" + valor + "\"";
                    out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\""
                            + "text" + "\"" + " class=" + classcom + " id=" + keycom
                            + " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">"
                            + "</td>");
                     
                    out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"limpiarCampos('" + i + "')\" alt=\"Limpiar campos\" width=\"35\" height=\"13\" border=\"0\"></td>");
                    out.println("</tr>\n");
                } else {
                    
                    if (tipoPersona != null && tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                        out.println("<td>");
                        
                       if (tiposIDJuridica != null) {
                        
                        tipoIDHelper.setCssClase("camposformtext");
                        tipoIDHelper.setId(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
                        tipoIDHelper.setNombre(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
                        tipoIDHelper.setOrdenar(false);
                        tipoIDHelper.setTipos(tiposIDJuridica);
                        tipoIDHelper.setFuncion("onChange=\"Secuencia('" + i + "')\"");

                }
                tipoIDHelper.render(request, out);
               
                    out.println("</td>");
                    
                        out.println("</td>");
                        
                        request.setAttribute(CFolio.ANOTACION_SEXO_PERSONA, null);
                        request.setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA, null);
                        request.setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA, null);
                        request.setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA, null);
                        
                        key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        //valor combo tipo id
                        String vs = (String) request.getSession().getAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA
                                + i);

                        if (vs == null) {
                            vs = "";
                        } else {
                            if (centrar) {
                                out.println("<script>centrar();</script>\n");
                            }
                        }

                        if (valorcom.equals("\"\"")) {
                            mostrar = false;
                        }

                        TextHelper helper = new TextHelper();
                        if (!vs.equals("SE")) {
                            /*
                         ** Construye el campo donde se digita el numero de NIT
                             */
                            out.println("<td >");
                            helper.setId(key);
                            helper.setNombre(key);
                            helper.setSize("12");
                            helper.setMaxlength("15");
                            helper.setCssClase("camposformtext");
                            helper.setReadonly(false);
                            helper.setFuncion("onBlur=\"validarCiudadano('" + i + "')\"");
                            helper.render(request, out);
//                            out.println(" - ");
//                            /*
//                           "onChange=\"getCodVerificacion('" + i + "')\
//                         ** Construye el campo donde se muestra el dígito de verificación
//                         ** del NIT digitado.
//                             */
//                            helper.setId(CFolio.ANOTACION_COD_VERIFICACION + i);
//                            helper.setNombre(CFolio.ANOTACION_COD_VERIFICACION + i);
//                            helper.setSize("1");
//                            helper.setMaxlength("1");
//                            helper.setCssClase("camposformtext");
//                            helper.setReadonly(true);
//                            helper.render(request, out);
                            out.write("</td>");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "hidden" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "12" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + ">" + "</td>\n");
                        }

                        key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
                        keycom = "\"" + key + "\"";

                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "20" + "\""
                                    + " maxlength=" + "\"" + "150" + "\"" + " readonly=\"readonly\" >" + "</td>\n");

                        key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
                        keycom = "\"" + key + "\"";

                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type= \"hidden\""
                                    + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "40" + "\"" + " readonly=\"readonly\" >" + "</td>\n");

                        key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";

                        if (!mostrar) {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "text" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "300" + "\"" + " value="
                                    + valorcom + ">" + "</td>\n");
                        } else {
                            out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                    + "\"" + "text" + "\"" + " class=" + classcom
                                    + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                    + " maxlength=" + "\"" + "300" + "\"" + " value="
                                    + valorcom + " readonly=\"readonly\" >" + "</td>\n");
                        }
                        
                        key = CFolio.ANOTACION_SEXO_PERSONA + i;
                        keycom = "\"" + key + "\"";

                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type="
                                + "\"" + "hidden" + "\"" + " class=" + classcom
                                + " id=" + keycom + " size=" + "\"" + "18" + "\""
                                + " maxlength=" + "\"" + "40" + "\"" + " readonly=\"readonly\" >" + "</td>\n");

                        out.println("<td>");

                        if (tiposParticipacion != null) {
                            tipoInterHelper.setCssClase("camposformtext");
                            tipoInterHelper.setId(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                    + i);
                            tipoInterHelper.setNombre(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION
                                    + i);
                            tipoInterHelper.setListaTipos(tiposPropietario);
                            if (request.getSession().getAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i) == null) {
                                tipoInterHelper.setSelected("");
                            }
                        }

                        tipoInterHelper.render(request, out);
                        out.println("</td>");

                        key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
                        keycom = "\"" + key + "\"";
                        valor = getValorFromSession(request, key);
                        valorcom = "\"" + valor + "\"";
                        out.println("<td>" + "<input onmousemove='this.title=this.value' name=" + keycom + " type=" + "\""
                                + "text" + "\"" + " class=" + classcom + " id=" + keycom
                                + " size=" + "\"" + "3" + "\"" + " value=" + valorcom + ">"
                                + "</td>");
                        
                        out.println("<td><img src=\"" + request.getContextPath() + "/jsp/images/btn_mini_eliminar.gif\" onClick=\"limpiarCampos('" + i + "')\" alt=\"Limpiar campos\" width=\"35\" height=\"13\" border=\"0\"></td>");
                        out.println("</tr>\n");

                    }
                }
            }
        }

        out.println(
            "<table  border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"camposform\">");
        out.println("<tr>\n");

        //aumentar un registro
        out.println("<td><a href=\"javascript:" + this.funcionJavascriptAccion +
            "('" + accionMas + "')\"><img src=\"" + request.getContextPath() +
            "/jsp/images/sub_btn_agregar.gif\" alt=\"Agregar un registro más\" width=\"18\" height=\"14\" border=\"0\"></a></td>");
        out.println("<td width=\"5\">&nbsp;</td>");

        //disminuir un registro
        out.println("<td><a href=\"javascript:" + this.funcionJavascriptAccion +
            "('" + accionMenos + "')\"><img src=\"" + request.getContextPath() +
            "/jsp/images/sub_btn_eliminar.gif\" alt=\"Eliminar un Registro\" width=\"18\" height=\"14\" border=\"0\"></a></td>");
        out.println("</tr>");
        out.println("</table></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>&nbsp;</td>");
        out.println(
            "<td><table  border=\"0\" align=\"left\" class=\"camposform\">");

        //@BUGID: 0002066
        if (this.mostrarBotonAgregar) {
            out.println("<tr align=\"center\">");
            out.println("<td>Agregar Ciudadanos</td>");
            
            if (this.mostrarBotonConsultaPropietario) {
            	out.println("<td>Consultar Propietarios</td>");
            }
            out.println("</tr>");
            out.println("<tr align=\"center\">");

            out.println("<td><a href=\"javascript:" +
                this.funcionJavascriptAccion + "('" + accionAnadirVarios +
                "')\"><img src=\"" + request.getContextPath() +
                "/jsp/images/btn_short_anadir.gif\" " +
                "width=\"35\" height=\"25\" border=\"0\"></a></td>");

            if (this.mostrarBotonConsultaPropietario) {
            	out.println("<td><a href=\"javascript:" +
                        this.funcionJavascriptAccion + "('" + accionUltimosPropietarios +
                        "')\"><img src=\"" + request.getContextPath() +
                        "/jsp/images/btn_short_anadir.gif\" " +
                        "width=\"35\" height=\"25\" border=\"0\"></a></td>");
            }
            
            out.println("</tr>");
        }

        out.println("</table></td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<hr class=\"linehorizontal\"></td>");

        //if(!cambioTipoDoc.equals("")){
        //	out.println("<script> cambioFocus(); </script>");
        //}
    }

    /**
	 * @param ciudadanos
	 * @return
	 */
	private List organizarCiudadanos(List ciudadanos) {
		
		AnotacionCiudadanoComparator.ordenarAnotacionesCiudadano(ciudadanos);
		return ciudadanos;
	}

	/**
     * @param numFilas
     * @param tiposIDNatural
     * @param tiposIDJuridica
     * @param tiposPersona
     * @param tiposSexo
     * @param accionValidarCiudadano
     * @param accionMas
     * @param accionMenos
     * @param accionAnadirVarios
     * @param accionEliminar
     * @param ciudadanos
     * @param form
     * */
    public void setPropertiesHandly(int numFilas, List tiposIDNatural, List tiposPersona, List tiposSexo,
        List tiposIDJuridica, String accionMas, String accionMenos, String accionAnadirVarios,
        String accionEliminar, List ciudadanos, String accionValidarCiudadano,
        String form) {

		if (ciudadanos == null) {
			this.ciudadanos = new Vector();
		} else {
			this.ciudadanos = ciudadanos;
		}

        this.numFilas = numFilas; 
        this.tiposIDNatural = tiposIDNatural;        
        this.tiposPersona = tiposPersona;
        this.tiposSexo = tiposSexo;
        this.tiposIDJuridica = tiposIDJuridica;
        this.accionMas = accionMas;
        this.accionMenos = accionMenos;
        this.accionAnadirVarios = accionAnadirVarios;
        this.accionEliminar = accionEliminar;
        this.ciudadanos = organizarCiudadanos(this.ciudadanos);
        this.accionValidarCiudadano = accionValidarCiudadano;
        this.form = form;

        this.tipoIDHelper = new ListaElementoHelper();
        this.tipoPersonaHelper = new ListaElementoHelper();
        this.tipoSexoHelper = new ListaElementoHelper();
        this.tipoInterHelper = new ListaTipoPlano();
        this.textHelper = new TextHelper();
        this.textHelper.setCssClase("camposformtext");

        tiposParticipacion = new Vector();
        tiposParticipacion.add("DE");
        tiposParticipacion.add("A");

        tiposPropietario = new Vector();
        tiposPropietario.add("");
        tiposPropietario.add("X");
        tiposPropietario.add("I");
    }

    /* (non-Javadoc)
     * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
     */
    public void setProperties(HttpServletRequest request)
        throws HelperException {
        //NO HACER CARGA IMPLICITA DE PROPIEDADES, PEDIRLAS DE MANERA EXPLICITA EN
        //SET_PROPERTIES_MANUAL = setPropertiesHandly
    }

    private String getValorFromSession(HttpServletRequest request, String key) {
        Object atributo = null;

        HttpSession session = request.getSession();
        atributo = session.getAttribute(key);

        if (atributo == null) {
            return "";
        }

        Integer valor = null;
        String resp = "";

        if (atributo instanceof Integer) {
            valor = (Integer) atributo;
            resp = String.valueOf(valor.intValue());
        } else if (atributo instanceof String) {
            resp = (String) atributo;
        }

        return resp;
    }

    /**
     * @return Returns the centrar.
     */
    public boolean isCentrar() {
        return centrar;
    }

    /**
     * @param centrar The centrar to set.
     */
    public void setCentrar(boolean centrar) {
        this.centrar = centrar;
    }

    /**
     * Obtener el atributo funcionJavascriptAccion
     *
     * @return Retorna el atributo funcionJavascriptAccion.
     */
    public String getFuncionJavascriptAccion() {
        return funcionJavascriptAccion;
    }

    /**
     * Actualizar el valor del atributo funcionJavascriptAccion
     * @param funcionJavascriptAccion El nuevo valor del atributo funcionJavascriptAccion.
     */
    public void setFuncionJavascriptAccion(String funcionJavascriptAccion) {
        this.funcionJavascriptAccion = funcionJavascriptAccion;
    }

    /**
     * Obtener el atributo accionAnadirVarios
     *
     * @return Retorna el atributo accionAnadirVarios.
     */
    public String getAccionAnadirVarios() {
        return accionAnadirVarios;
    }

    /**
     * Actualizar el valor del atributo accionAnadirVarios
     * @param accionAnadirVarios El nuevo valor del atributo accionAnadirVarios.
     */
    public void setAccionAnadirVarios(String accionAnadirVarios) {
        this.accionAnadirVarios = accionAnadirVarios;
    }

    /**
     * Obtener el atributo accionEliminar
     *
     * @return Retorna el atributo accionEliminar.
     */
    public String getAccionEliminar() {
        return accionEliminar;
    }

    /**
     * Actualizar el valor del atributo accionEliminar
     * @param accionEliminar El nuevo valor del atributo accionEliminar.
     */
    public void setAccionEliminar(String accionEliminar) {
        this.accionEliminar = accionEliminar;
    }

    /**
     * Obtener el atributo accionMas
     *
     * @return Retorna el atributo accionMas.
     */
    public String getAccionMas() {
        return accionMas;
    }

    /**
     * Actualizar el valor del atributo accionMas
     * @param accionMas El nuevo valor del atributo accionMas.
     */
    public void setAccionMas(String accionMas) {
        this.accionMas = accionMas;
    }

    /**
     * Obtener el atributo accionMenos
     *
     * @return Retorna el atributo accionMenos.
     */
    public String getAccionMenos() {
        return accionMenos;
    }

    /**
     * Actualizar el valor del atributo accionMenos
     * @param accionMenos El nuevo valor del atributo accionMenos.
     */
    public void setAccionMenos(String accionMenos) {
        this.accionMenos = accionMenos;
    }

    /**
     * Obtener el atributo accionValidarCiudadano
     *
     * @return Retorna el atributo accionValidarCiudadano.
     */
    public String getAccionValidarCiudadano() {
        return accionValidarCiudadano;
    }

    /**
     * Actualizar el valor del atributo accionValidarCiudadano
     * @param accionValidarCiudadano El nuevo valor del atributo accionValidarCiudadano.
     */
    public void setAccionValidarCiudadano(String accionValidarCiudadano) {
        this.accionValidarCiudadano = accionValidarCiudadano;
    }

    /**
     * Obtener el atributo ciudadanos
     *
     * @return Retorna el atributo ciudadanos.
     */
    public List getCiudadanos() {
        return ciudadanos;
    }

    /**
     * Actualizar el valor del atributo ciudadanos
     * @param ciudadanos El nuevo valor del atributo ciudadanos.
     */
    public void setCiudadanos(List ciudadanos) {
        this.ciudadanos = ciudadanos;
    }

    /**
     * Obtener el atributo form
     *
     * @return Retorna el atributo form.
     */
    public String getForm() {
        return form;
    }

    /**
     * Actualizar el valor del atributo form
     * @param form El nuevo valor del atributo form.
     */
    public void setForm(String form) {
        this.form = form;
    }

    /**
     * Obtener el atributo tiposID
     *
     * @return Retorna el atributo tiposID.
     */
    public List getTiposID() {
        return tiposID;
    }

    /**
     * Actualizar el valor del atributo tiposID
     * @param tiposID El nuevo valor del atributo tiposID.
     */
    public void setTiposID(List tiposID) {
        this.tiposID = tiposID;
    }
    
    /**
     * Obtener el atributo tiposIDNatural
     *
     * @return Retorna el atributo tiposIDNatural.
     */
    public List getTiposIDNatural() {
        return tiposIDNatural;
    }

    /**
     * Actualizar el valor del atributo tiposIDNatural
     * @param tiposIDNatural El nuevo valor del atributo tiposID.
     */
    public void setTiposIDNatural(List tiposIDNatural) {
        this.tiposIDNatural = tiposIDNatural;
    }
    
    /**
     * Obtener el atributo tiposIDJuridica
     *
     * @return Retorna el atributo tiposIDJuridica.
     */
    public List getTiposIDJuridica() {
        return tiposIDJuridica;
    }

    /**
     * Actualizar el valor del atributo tiposIDJuridica
     * @param tiposIDJuridica El nuevo valor del atributo tiposIDJuridica.
     */
    public void setTiposIDJuridica(List tiposIDJuridica) {
        this.tiposIDJuridica = tiposIDJuridica;
    }
    
    /**
     * Obtener el atributo tiposPersona
     *
     * @return Retorna el atributo tiposPersona.
     */
    public List getTiposPersona() {
        return tiposPersona;
    }

    /**
     * Actualizar el valor del atributo tiposPersona
     * @param tiposPersona El nuevo valor del atributo tiposPersona.
     */
    public void setTiposPersona(List tiposPersona) {
        this.tiposPersona = tiposPersona;
    }
    
    /**
     * Obtener el atributo tiposSexo
     *
     * @return Retorna el atributo tiposSexo.
     */
    public List getTiposSexo() {
        return tiposSexo;
    }

    /**
     * Actualizar el valor del atributo tiposSexo
     * @param tiposSexo El nuevo valor del atributo tiposSexo.
     */
    public void setTiposSexo(List tiposSexo) {
        this.tiposSexo = tiposSexo;
    }

    /**
     * Obtener el atributo mostrarBotonAgregar
     *
     * @return Retorna el atributo mostrarBotonAgregar.
     */
    public boolean isMostrarBotonAgregar() {
        return mostrarBotonAgregar;
    }

  public boolean isEdicionCiudadanoSobreTodosFolios_FlagEnabled() {
    return edicionCiudadanoSobreTodosFolios_FlagEnabled;
  }

  public String getEdicionCiudadanoSobreTodosFolios_JsHandler() {
    return edicionCiudadanoSobreTodosFolios_JsHandler;
  }

  /**
     * Actualizar el valor del atributo mostrarBotonAgregar
     * @param mostrarBotonAgregar El nuevo valor del atributo mostrarBotonAgregar.
     */
    public void setMostrarBotonAgregar(boolean mostrarBotonAgregar) {
        this.mostrarBotonAgregar = mostrarBotonAgregar;
    }

    public void setMostrarListaCiudadanos(boolean mostrarListaCiudadanos) {
        this.mostrarListaCiudadanos = mostrarListaCiudadanos;
    }

  public void setEdicionCiudadanoSobreTodosFolios_FlagEnabled(boolean
      edicionCiudadanoSobreTodosFolios_FlagEnabled) {
    this.edicionCiudadanoSobreTodosFolios_FlagEnabled =
        edicionCiudadanoSobreTodosFolios_FlagEnabled;
  }

  public void setEdicionCiudadanoSobreTodosFolios_JsHandler(String
      edicionCiudadanoSobreTodosFolios_JsHandler) {
    this.edicionCiudadanoSobreTodosFolios_JsHandler =
        edicionCiudadanoSobreTodosFolios_JsHandler;
  }

public String getAccionUltimosPropietarios() {
	return accionUltimosPropietarios;
}

public void setAccionUltimosPropietarios(String accionUltimosPropietarios) {
	this.accionUltimosPropietarios = accionUltimosPropietarios;
}

public boolean isMostrarBotonConsultaPropietario() {
	return mostrarBotonConsultaPropietario;
}

public void setMostrarBotonConsultaPropietario(
		boolean mostrarBotonConsultaPropietario) {
	this.mostrarBotonConsultaPropietario = mostrarBotonConsultaPropietario;
}

public String getProceso() {
	return proceso;
}

public void setProceso(String proceso) {
	this.proceso = proceso;
}

public boolean isAnotacionNueva() {
	return anotacionNueva;
}

public void setAnotacionNueva(boolean anotacionNueva) {
	this.anotacionNueva = anotacionNueva;
}

public String getAccionEditar() {
	return accionEditar;
}

public void setAccionEditar(String accionEditar) {
	this.accionEditar = accionEditar;
}
}
