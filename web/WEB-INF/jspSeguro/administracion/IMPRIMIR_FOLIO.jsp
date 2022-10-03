<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CRoles"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoTarifa"%>
<%@page import="org.auriga.core.modelo.transferObjects.Rol"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWImpresionFolio" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFolio" %>
<%@page import="org.auriga.core.web.HelperException" %>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"/>
<script language='javascript'>
	function cargarApplet(){
        var app =  getCookie("appletImpresionCargado");
        if (app == null){
       		var x = eval (window.screen.availWidth - 310);
			var y = eval (window.screen.availHeight - 450);
			var w = window.open('<%= request.getContextPath()%>/impresion.view','applet_impresion','width=300,height=400,resizable=no,scrollbars=no,location=no,status=yes,menubar=no,copyhistory=no,left='+x+',top='+y);
			//w.resizeTo(300,150);
			this.window.focus();
            setCookie("appletImpresionCargado",true);
        }
}
</script>
<body onload='cargarApplet()'>

<%
TextHelper textHelper = new TextHelper();

boolean impresionOK = false;
String matricula = "";
if(session.getAttribute(AWImpresionFolio.IMPRESION_FOLIO_EJECUTADA) != null){
	impresionOK = true;
	matricula = (String)session.getAttribute(CFolio.ID_MATRICULA);
	session.removeAttribute(CFolio.ID_MATRICULA);
	session.removeAttribute(AWImpresionFolio.IMPRESION_FOLIO_EJECUTADA);
	}
    
    String rtaTarea = "";
    String imp_masiva_simple_folio_ftp = "";
    if(session.getAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO) != null) {
        if(session.getAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO).equals("true")) {
            rtaTarea = "true";
            imp_masiva_simple_folio_ftp = (String)session.getAttribute(AWImpresionFolio.IMP_MASIVA_SIMPLE_FOLIO_FTP);
        }
        
        if(session.getAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO).equals("existeTarea")) {
            rtaTarea = "existeTarea";
        }
        
        if(session.getAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO).equals("fechaInvalida")) {
            rtaTarea = "fechaInvalida";
        }
        session.removeAttribute(AWImpresionFolio.PROGRAMAR_TAREA_EXITO);
        session.removeAttribute(AWImpresionFolio.IMP_MASIVA_SIMPLE_FOLIO_FTP);
    }
%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">
            Impresi&oacute;n Simple de Folios </td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Impresi&oacute;n
                    Simple de Folios</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
        <!--<form action="impresionFolio.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden" name="%= WebKeys.ACCION %>" id="%=  AWImpresionFolio.IMPRIMIR_FOLIO%>" value="%= AWImpresionFolio.IMPRIMIR_FOLIO %>">-->
        
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                <form action="impresionFolio.do" method="POST" name="BUSCAR" id="BUSCAR">
                    <input type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWImpresionFolio.IMPRIMIR_FOLIO%>" value="<%= AWImpresionFolio.IMPRIMIR_FOLIO %>"/>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Información del Folio</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
         <table width="100%" class="camposform">
         <%
            if(impresionOK){
            %>  
              <tr>
               <td width="20"></td>
               <td >Se realizó satisfactoriamente la impresión del folio <%= matricula %></td>
             </tr>
            <%
            }
            %>         
         </table>   
                
                <table width="100%" class="camposform">
               
                 <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">N&uacute;mero del Folio</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CFolio.ID_MATRICULA );
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CFolio.ID_MATRICULA);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
               
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWImpresionFolio.IMPRIMIR_FOLIO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWImpresionFolio.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                </table>
                </form>
                <% 
                    /**
                     * @author     : Ellery David Robles Gómez.
                     * @change     : Se determina si el usuario tiene el rol SIR_ROL_IMPRESION_FOLIO_ESPECIAL para permitirle programar tareas.
                     * Caso_Mantis : 09422: Acta - Requerimiento No 040_151 - Impresión de Folios - Nivel Central.
                     */
                     Rol  rol = null;
                     boolean folioimpresioespecial = false;
                     List roles = (List)session.getAttribute(WebKeys.LISTA_ROLES);
                     Iterator itRoles = roles.iterator();
                     while (itRoles.hasNext()) {
                         rol = (Rol)itRoles.next();
                         if (rol.getRolId().equals(CRoles.SIR_ROL_IMPRESION_FOLIO_ESPECIAL))
                             folioimpresioespecial = true;
                     }
                     if (folioimpresioespecial) { %>
                     <form name="form1" enctype="multipart/form-data" method="post" action="impresionFolio.do">
                         <table width="100%" class="camposform">
                             <tr>
                                 <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>
                                 <td>Programar tarea impresi&oacute;n masiva simple de folios</td>
                             </tr>
                             <tr>
                                 <td width="20">&nbsp;</td>
                                 <td>
                                     <table width="100%" class="camposform">
                                          <tr> 
                                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                                            <td>Seleccione archivo csv:</td>
                                          </tr>
                                          <tr> 
                                            <td width="20">&nbsp;</td>
                                            <td>
                                                <table width="100%">
                                                    <tr>
                                                        <td width="81%">
                                                            <input name="filename" type="file" class="camposformtext"/>
                                                        </td>
                                                        <td width="19%">
                                                            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                                                <tr> 
                                                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                                                    <td>Subir Archivo</td>
                                                                    <td>
                                                                        <input name="imageField222" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </form>
                <% if(request.getSession().getAttribute(AWImpresionFolio.LISTA_MATRICULAS) != null) { %>
                    <form name="form2" action="impresionFolio.do" method="post">
                             <table width="100%" class="camposform">
                                 <% if(rtaTarea.equals("true")) { %>
                                     <tr>
                                         <td width="20"></td>
                                         <td>Se realizó satisfactoriamente la programacion de la tarea, descargar PDFs en : <%= imp_masiva_simple_folio_ftp %></td>
                                     </tr>
                                 <% } %>
                                 <% if(rtaTarea.equals("existeTarea")) { %>
                                     <tr>
                                         <td width="20"></td>
                                         <td style="color: red">El nombre de la tarea ya exite, intente nuevamente.</td>
                                     </tr>
                                 <% } %>
                                 <% if(rtaTarea.equals("fechaInvalida")) { %>
                                     <tr>
                                         <td width="20"></td>
                                         <td style="color: red">La fecha seleccionada es invalida, intente nuevamente.</td>
                                     </tr>
                                 <% } %>
                             </table>
                             <table width="100%" class="camposform">
                                 <tr>
                                     <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"/></td>
                                     <td>Programar tarea impresi&oacute;n masiva simple de folios</td>
                                 </tr>
                                 <tr>
                                     <td width="20">&nbsp;</td>
                                     <td>
                                        <table width="100%">
                                            <tr>
                                                <td width="100%">
                                                    <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                                                        <tr>
                                                            <td align="left" width="25%">
                                                                Nombre:&nbsp;&nbsp;
                                                                <input type="hidden" name="<%= WebKeys.ACCION %>" value="<%= AWImpresionFolio.PROGRAMAR_TAREA %>"/>
                                                                <input name="nombreTarea" type="text" class="camposformtext"/>
                                                            </td>
                                                            <td align="left" width="25%">
                                                                <table border="0" cellpadding="0" cellspacing="2" class="camposform" style="border: none">
                                                                    <tr>
                                                                        <td align="left">
                                                                            Fecha Ejecuci&oacute;n:&nbsp;&nbsp;
                                                                            <% try {
                                                                                textHelper.setNombre("fechaTarea");
                                                                                textHelper.setCssClase("camposformtext");
                                                                                textHelper.setId("fechaTarea");
                                                                                textHelper.render(request,out);
                                                                               } catch(HelperException re) {
                                                                                   out.println("ERROR " + re.getMessage());
                                                                               }
                                                                            %>
                                                                        </td>
                                                                        <td align="left">
                                                                            <a href="javascript:NewCal('fechaTarea','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                            <td align="left">Hora:&nbsp;&nbsp;
                                                                <select name="hora" class="camposformtext">
                                                                    <option value="20">8 PM</option>
                                                                    <option value="21">9 PM</option>
                                                                    <option value="22">10 PM</option>
                                                                    <option value="23">11 PM</option>
                                                                    <option value="0">12 AM</option>
                                                                    <option value="1">1 AM</option>
                                                                    <option value="2">2 AM</option>
                                                                    <option value="3">3 AM</option>
                                                                    <option value="4">4 AM</option>
                                                                    <option value="5">5 AM</option>
                                                                </select>
                                                            </td>
                                                            <td align="left" width="30%">
                                                                Archivo csv cargado:&nbsp;&nbsp;<%= request.getSession().getAttribute(AWImpresionFolio.NOMBRE_ARCHIVO_CSV) %>
                                                                <input name="listaFolios" type="hidden" class="camposformtext" value="<%= request.getSession().getAttribute(AWImpresionFolio.LISTA_MATRICULAS) %>"/>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                            <td width="100%">
                                                <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                                    <tr>
                                                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                                                        <td>Programar Tarea</td>
                                                        <td><input name="imageField222" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </form>
                                                    <% } %>
                <% } %>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
    </table>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>

