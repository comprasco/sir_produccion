<%@page import="org.auriga.core.web.HelperException"%>
<%@page import="gov.sir.core.negocio.modelo.Usuario"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CArchivosJustifica"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gov.sir.core.negocio.modelo.TramiteSuspension"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTramiteSuspension"%>
<%@page import="gov.sir.core.web.acciones.registro.AWTramiteSuspension"%>
<%@page import="gov.sir.core.negocio.modelo.Proceso"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CJustificaAdm"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.NotasInformativasHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<script language="javascript" src="<%=request.getContextPath()%>/jsp/plantillas/js/paging.js"></script>
<style type="text/css">    
    .pg-normal {
        color: black;
        font-weight: normal;
        text-decoration: none;    
        cursor: pointer;
        font-size: 15;
    }
    .pg-selected {
        color: black;
        font-weight: bold;        
        text-decoration: underline;
        cursor: pointer;
        font-size: 15;
    }
    #btn_paginator {
        font-size: 15;
        cursor: pointer;
    }

</style>
<%
    NotasInformativasHelper notHelper = new NotasInformativasHelper();

    List respuestas_usuario = (List) session.getAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO);
    if (respuestas_usuario == null) {
        respuestas_usuario = new ArrayList();
    }
%>
<script>
    function cambiarAccionTramiteSuspensionTemp(text) {
        if (confirm('Está seguro que desea agregar la respuesta: ')) {
            document.AGREGAR_RESPUESTA_TEMP.<%=WebKeys.ACCION%>.value = text;
            document.AGREGAR_RESPUESTA_TEMP.submit();
        }
    }

    function avanzar() {
        if (confirm('Está seguro que desea enviar la respuesta y avanzar el turno: ')) {
            document.AGREGAR_RESPUESTA_TEMP.ACCION.value = '<%=AWTramiteSuspension.AVANZAR%>';
            document.AGREGAR_RESPUESTA_TEMP.submit();
        }
    }
    
    function cancelar() {
        if (confirm('Está seguro que desea cancelar el proceso: ')) {
            document.AGREGAR_RESPUESTA_TEMP.ACCION.value = '<%=AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_TEMP%>';
            document.AGREGAR_RESPUESTA_TEMP.submit();
        }
    }


    function textCounter(field, cnt, maxlimit) {
        if (field.value.length >= 20) {
            document.getElementById("<%=CTramiteSuspension.TRAMS_MAX_LENGTH%>").value = 0;
            document.getElementById(cnt).textContent = 0;
        }
        if (field.value.length < 20) {
            document.getElementById("<%=CTramiteSuspension.TRAMS_MAX_LENGTH%>").value = maxlimit - field.value.length;
            document.getElementById(cnt).textContent = maxlimit - field.value.length;
        }
    }
</script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>              
        <td class="tdtablaanexa02">
            <form action="tramiteSuspension.do" method="post" name="AGREGAR_RESPUESTA_TEMP" id="AGREGAR_RESPUESTA" enctype="multipart/form-data">
                <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%= AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP%>" value="<%= AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP%>">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    </tr>
                    <tr>
                        <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Tramite suspensión Temporal</td>
                                    <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>                                
                                    <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                                </tr>
                            </table></td>
                        <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                    </tr>
                    <tr>
                        <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Agregar datos a la Respuesta</td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>                        
                            </table>
                            <br>
                            <br>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Información de la Novedad</td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                                </tr>                        
                            </table>
                        </td>
                        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                    </tr> 
                    <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="30%">Descripci&oacute;n de la Respuesta:</td>
                                    <td width="20">&nbsp;</td>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                    <td width="15%">Documento justificaci&oacute;n</td>                                
                                    <td> <table width="100%">
                                            <tr> 
                                                <td width="81%"> <input name="filename" type="file" class="camposformtext"> 
                                                </td>                                            
                                            </tr>
                                        </table>
                                    </td> 
                                </tr>
                                <tr>
                                    <td height="18">&nbsp;</td>
                                    <td>
                                        <% try {
                                                TextAreaHelper area = new TextAreaHelper();
                                                area.setCols("70");
                                                area.setRows("10");
                                                area.setNombre(CTramiteSuspension.DESC_RESPUESTA);
                                                area.setCssClase("camposformtext");
                                                area.setId(CTramiteSuspension.DESC_RESPUESTA);
                                                area.setFuncion("onKeyDown=\"textCounter(this,20)\"");
                                                area.setFuncion("onKeyUp=\"textCounter(this,'MAX_LENGTH',20)\"");
                                                area.render(request, out);
                                            } catch (HelperException re) {
                                                out.println("ERROR " + re.getMessage());
                                            }
                                        %>
                                    </td>                                                               
                                </tr>
                                <tr>
                                    <td height="18">&nbsp;</td>
                                    <td colspan="7">
                                        Car&aacute;cteres restantes m&iacute;nimos en la descripci&oacute;n: 
                                        <span id="MAX_LENGTH">20</span>
                                        <input type="hidden" name="<%=CTramiteSuspension.TRAMS_MAX_LENGTH%>" id="<%=CTramiteSuspension.TRAMS_MAX_LENGTH%>" value="20">
                                    </td>
                                </tr>
                            </table>
                            <table width="100%" class="camposform">
                                <tr>
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                    <td width="140">
                                        <a href="javascript:cambiarAccionTramiteSuspensionTemp('<%=AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>                        
                        </td>
                        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                    </tr>                    
                    <tr>
                        <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                        <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                    </tr>    
                </table>
            </form>
            <!-- EMPIEZA TABLE DE AGREGAR RESPUESTA -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                </tr>
                <tr>
                    <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Respuesta Agregada</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table>
                    </td>
                    <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                </tr>
                <tr>
                    <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                        <br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Respuesta</td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                                <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                                <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                            </tr>                        
                        </table>                                
                        <table width="100%" class="camposform" name='tablanotificaciones' id="<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                            <tr>
                                <th> Nombre de la fase </th>
                                <th> Fecha </th>
                                <th> Usuario </th>
                                <th> Descripción </th>
                                <th></th>
                            </tr>  
                            <%
                                if (respuestas_usuario != null) {
                                    int i = 0;
                                    for (Iterator iter = respuestas_usuario.iterator(); iter.hasNext();) {
                                        TramiteSuspension respUsuario = (TramiteSuspension) iter.next();
                                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            %>
                            <tr>
                                <td class="camposformtext_noCase"id="tramsNombreFase<%=i%>"><%= respUsuario.getTramsNombreFase()%></td>
                                <td class="camposformtext_noCase"id="tramsFecha<%=i%>"><%= df.format(respUsuario.getTramsFecha())%></td>
                                <td class="camposformtext_noCase"id="tramsIdUsuario<%=i%>"><%= respUsuario.getTramsUsuario()%></td>
                                <td class="camposformtext_noCase"id="tramsDescripcion<%=i%>"><%= respUsuario.getTramsDescripcion()%></td>                                   
                                <%if (respUsuario.getTramsIdArchivoJustifica() == null) {%>
                                <td>-</td>                                    
                                <%} else {%>
                                <td>
                                    <a target="_blank" href="<%=request.getContextPath()%>/jsp/descargar.jsp?nArchivo=<%=respUsuario.getTramsIdArchivoJustifica().getJusIdArchivo()%>.<%=respUsuario.getTramsIdArchivoJustifica().getJusTipoArchivo()%>&rArchivo=<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>">
                                        <img src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif"> 
                                    </a>                                    
                                </td>
                                <% } %>                                
                            </tr>
                            <%
                                        i++;
                                    }
                                }
                            %>                            
                        </table>     
                        <div style="text-align: center;" id="pageNavPosition"></div>
                        <script type="text/javascript">
                            var pager = new Pager('<%=CArchivosJustifica.TAG_ARCHIVOS_JUSTIFICA%>', 20);
                            pager.init();
                            pager.showPageNav('pager', 'pageNavPosition');
                            pager.showPage(1);
                        </script>
                        <table width="100%" class="camposform">
                            <tr> 
                                <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                <td width="139"><a href="javascript:avanzar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a></td>
                            <form name="logOut2" action="seguridad.do" method="post">
                                <td>
                                    <input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="150" height="21" border="0" >                                        
                                    <a href="javascript:cancelar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a>
                                    <input type="hidden" name="ACCION" value="CONSULTAR_PROCESO">
                                    <% Proceso proceso = ((Proceso) session.getAttribute(WebKeys.PROCESO));
                                        if (proceso != null) {
                                    %>                                        
                                    <input type="hidden" name="ID_PROCESO" value="<%= proceso.getIdProceso()%>">
                                    <%
                                        }
                                    %>
                                </td>                                    
                            </form>                                
                </tr>
            </table>
        </td>
        <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>                   
    </tr>
    <tr>
        <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
        <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    </tr>
</table>
<%
    try {
        //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
        //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
        //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
        notHelper.setNombreFormulario("SUSPENSION_PREV");
        notHelper.render(request, out);
    } catch (HelperException re) {
        out.println("ERROR " + re.getMessage());
    }
%>
</td>           
</tr>
</table>