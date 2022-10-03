<%@page import="gov.sir.core.negocio.acciones.excepciones.RolDrawException" %>
<%@page import="gov.sir.fenrir.FenrirProperties"%>
<%@page import="gov.sir.core.util.RolDraw"%>
<%
    String sUid = session.getId();
    //String direccion =  /manual/
    //String direccion =request.getContextPath();
    //direccion = direccion
%>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>
<script language='javascript'>
    function cargarApplet() {
        var app = getCookie("appletImpresionCargado");

        if (app == null) {
            var x = eval(window.screen.availWidth - 310);
            var y = eval(window.screen.availHeight - 450);

            var nombreVentanaImpresion = 'applet_impresion';
            //var iRandom = Math.round(100*Math.random())
            //nombreVentanaImpresion = nombreVentanaImpresion + iRandom;

            var uid = '<%=sUid%>';
            var length = uid.indexOf('.', 0);
            if (length != -1) {
                uid = uid.substr(0, length);
            }

            // nombreVentanaImpresion = nombreVentanaImpresion + '<%=sUid%>';
            nombreVentanaImpresion = nombreVentanaImpresion;// + uid;

            var w = window.open('<%= request.getContextPath()%>/impresion.view', nombreVentanaImpresion, 'width=300,height=400,resizable=no,scrollbars=no,location=no,status=yes,menubar=no,copyhistory=no,left=' + x + ',top=' + y);
            //w.resizeTo(300,150);
            w.focus();
            //this.window.focus();
            setCookie("appletImpresionCargado", true);
        }
    }
    function admin() {
        document.Roles.action = 'seguridad.do';
        document.Roles.<%=gov.sir.core.web.WebKeys.ACCION%>.value = '<%=gov.sir.core.web.acciones.comun.AWSeguridad.INICIAR_COMO_ADMINISTRADOR%>';
        document.Roles.submit();
    }

    function mostrarDocumentacion() {
        var url = '/manual/';
        var winName = 'DOCUMENTACION_SIR';
        var features = 'location=no,status=yes,scrollbars=yes,resizable=yes,fullscreen=no,width=' + (screen.availWidth - 100) + ',height=' + (screen.availHeight - 120) + ',left=70,top=50';
        window.open('/manual/', winName, features);
    }

</script>
<script>
    cargarApplet();
</script>
<% RolDraw rolDraw = new RolDraw();%>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">


    <tr> 
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="tdtablaanexa"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn004.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
                    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="9" height="10"></td>
                </tr>
                <tr> 
                    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
                            <tr> 
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">ROLES</td>
                                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_llave.gif" width="16" height="21"></td>
                                        </tr>
                                    </table></td>
                                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td><img name="tabla_central_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
                    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn004.gif">&nbsp;</td>
                    <td><img name="tabla_central_r1_c9" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c9.gif" width="9" height="29" border="0" alt=""></td>
                </tr>
                <tr> 
                    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                    <td valign="top" class="tdtablacentral">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tdtablacentral">
                            <tr> 
                                <td> 
                                    <%
                                        /*AHERRENO -  Se configura ruta del manual SIR en gov.sir.fenrir.properties*/
                                        FenrirProperties p = FenrirProperties.getInstancia();
                                        String R_MANUAL = p.getProperty(FenrirProperties.R_MANUAL);
                                        try {
                                            rolDraw.render(request, out);
                                        } catch (RolDrawException re) {
                                            out.println("ERROR " + re.getMessage());
                                        }
                                    %>  

                                </td>
                                <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
                                <td class="tdtablaanexa02" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
                                                    <tr>
                                                        <td valign="middle"><img src="<%= request.getContextPath()%>/jsp/images/ind_flecha.gif" width="20" height="15"></td>
                                                        <td valign="middle"><a href="#" class="links">Condiciones 
                                                                Generales de Uso | Pol&iacute;tica de Privacidad</a> </td>

                                                        <td valign="middle"  title="Aqui encontrará el manual de la aplicación con las últimas actualizaciones."><a href="<%=R_MANUAL%>" target='_blank' class="links">>> Documentación SIR.</a> </td>	                        
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><img src="<%= request.getContextPath()%>/jsp/images/img_estacion.jpg" width="450" height="150"></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <br>
                                                <br>
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
                                                    <tr>
                                                        <td valign="top" width="5%"><%--<img src="<%= request.getContextPath() %>/jsp/images/ico_ayuda.gif" width="16" height="21">--%></td>
                                                        <td valign="middle" width="80%" class="links">
                                                            .:: <b>Nota</b> ::.<br><br>
                                                            Espere 5 segundos a que se muestre el di&aacute;logo de advertencia del Aplicativo de
                                                            impresi&oacute;n. Haga click en <b>Siempre</b>, o haga click en el link de iniciar
                                                            Aplicativo de impresi&oacute;n.<br><br><br><br>
                                                            <%--<input name="INICIAR_APPLET" type="button" class="botonactivo" value="Iniciar Aplicativo de Impresi&oacute;n" onClick="cargarApplet()">--%>
                                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
                                                                <tr>
                                                                    <td class="links">
                                                                        <a href="javascript:cargarApplet()"><img src="<%= request.getContextPath()%>/jsp/images/btn_iniciar_aplicativo_impresion.gif" width="220" height="21" border="0"></a>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                        <td width="15%"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>

                                </td>
                                <td width="9" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn007.gif">&nbsp;</td>
                            </tr>
                            <tr> 
                                <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                                <td><img name="tabla_central_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
                                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn005.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
                                <td><img name="tabla_central_r3_c9" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c9.gif" width="9" height="6" border="0" alt=""></td>
                            </tr>
                        </table></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr> 
                    <td>&nbsp;</td>
                    <td></td>
                    <td>&nbsp;</td>
                    <td></td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            </body>
