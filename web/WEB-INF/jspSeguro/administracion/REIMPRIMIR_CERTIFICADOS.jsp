<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitud"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWImpresionFolio" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaCheckboxHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.ArrayList" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
TextHelper textHelper = new TextHelper();
ListaElementoHelper procesos=new ListaElementoHelper();
String turno=(String)session.getAttribute(CTurno.ID_TURNO);
String solicitud = (String) session.getAttribute(CSolicitud.NUMERO_SOLICITUD_LIQUIDACION);
List turnosAsociados = (List)session.getAttribute(AWImpresionFolio.LISTA_TURNOS_ASOCIADOS_REIMPRESION);

ListaCheckboxHelper asociados=new ListaCheckboxHelper();

/*Turno ultimoTurno = (Turno)session.getAttribute(AWImpresionFolio.ULTIMO_TURNO_IMPRESO);
String idTurno = "";
if(ultimoTurno != null) {
    idTurno = 
ultimoTurno.getIdWorkflow();
    session.removeAttribute(AWImpresionFolio.ULTIMO_TURNO_IMPRESO);
}
Boolean recarga = (Boolean)session.getAttribute(WebKeys.RECARGA);
session.removeAttribute(WebKeys.RECARGA);*/
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionSubmit(text) {
    
    document.BUSCAR.<%= WebKeys.ACCION%>.value = text;
    document.BUSCAR.submit();
}

function cargarUltimoTurno() {
	cambiarAccion('<%= AWImpresionFolio.OBTENER_ULTIMO_TURNO_IMPRESO %>');
	document.BUSCAR.submit();
}
</script>
<script type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/cookies.js"></script>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Datos del turno para la reimpresión del recibo</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
       <form action="impresionFolio.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWImpresionFolio.REIMPRIMIR_RECIBO %>" value="<%=  AWImpresionFolio.REIMPRIMIR_RECIBO %>">
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                   
                    <td class="bgnsub">Datos del Turno</td>
                    <td width="16" class="bgnsub"></td>
                    <td width="16" class="bgnsub">
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
                <table width="100%" class="camposform">
                   
                <tr>
                  
                    <td width="20%">Proceso</td>
                    <%--<td>El &Uacute;ltimo Turno Impreso fue el Turno <%= idTurno %></td>--%>
                    <td>
                    	<%--<input type="hidden" name="<%= CTurno.ID_TURNO %>" id="<%= CTurno.ID_TURNO %>" 
                    		value="<%= idTurno %>">--%>
                        
                        <%                            
                            try {
                                procesos.setNombre(CProceso.PROCESO_ID);
                                procesos.setCssClase("camposformtext");
                                procesos.setId(CProceso.PROCESO_ID);
                                procesos.setTipos((List) session.getServletContext().getAttribute(WebKeys.LISTA_PROCESOS_RECIBO_SISTEMA));
                                
                                procesos.setFuncion("onChange=\"cambiarAccionSubmit('" + AWImpresionFolio.ULTIMO_TURNO_IMPRESO_PROCESO + "')\"");                               
                                procesos.render(request, out);
                            } catch (HelperException re) {
                                out.println("ERROR " + re.getMessage());
                            }
                        %>
                    </td>
                    <td>
                    	Reimprime recibo mayor valor
                    	<input type="checkbox" name="<%=WebKeys.REIMPRIME_RECIBO_MAYOR_VALOR%>" value="true">
                    </td>
                  </tr>
                  <br/>
                  <%
                  	if (turno!=null){%>
                  		  <tr>
		                    <td>&nbsp;</td>
		                    <td>&nbsp;</td>
		                  </tr>
		                  <tr>
		                    <td>&nbsp;</td>
		                    <td>Turno:</td>
		                  </tr>
                  		
                  		  <tr>
		                    <td><input type="checkbox" name=<%=AWImpresionFolio.LISTA_RECIBOS_IMPRIMIR%> value=<%=turno%>></td>
		                    <td class="campositem"><%=turno%></td>
		                  </tr>
		            <%}
		            if (turnosAsociados!=null && !turnosAsociados.isEmpty()){
		            %>
		            	  <tr>
		                    <td>&nbsp;</td>
		                    <td>&nbsp;</td>
		                  </tr>
		                  <tr>
		                    <td>&nbsp;</td>
		                    <td>Turnos Asociados:</td>
		                  </tr>
		            <%
		            List asoc=new ArrayList();
		            Iterator itAsoc=turnosAsociados.iterator();
		            while(itAsoc.hasNext()){
		            	String tAs=(String)itAsoc.next();
		            	ElementoLista elem=new ElementoLista();
		            	elem.setId(tAs);
		            	elem.setValor(tAs);
		            	asoc.add(elem);
		            }
		            	try {
	                  	  asociados.setCssClase("campositem");
	                  	  asociados.setId(AWImpresionFolio.LISTA_RECIBOS_IMPRIMIR);
	                  	  asociados.setTipos(asoc);
						  asociados.render(request,out);
					}
						catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
		            }%>
                            
                    <%
                        if (solicitud != null) {                         
                    %>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>Liquidación:</td>
                        </tr>

                        <tr>
                            <td><input type="checkbox" name=<%=AWImpresionFolio.LISTA_RECIBOS_IMPRIMIR%> value=<%=solicitud%>></td>
                            <td class="campositem"><%=solicitud%></td>
                        </tr>
                    <% } %>
                </table>
                <table width="100%" class="camposform">
                  <tr>
                   
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  AWImpresionFolio.REIMPRIMIR_RECIBOS%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_reimprimir.gif" width="150" height="21" border="0">
                    	</td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%= AWImpresionFolio.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
                  </tr>
                </table>
            </FORM>    
            </td>
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
  
</table>
<%-- if(recarga == null) { %>
<SCRIPT>
	cargarUltimoTurno();
</SCRIPT>
<% } --%>
