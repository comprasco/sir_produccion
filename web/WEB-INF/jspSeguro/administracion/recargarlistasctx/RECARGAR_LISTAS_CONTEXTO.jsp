<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWRecargarListasContexto" %>
<%@page import="gov.sir.core.negocio.modelo.ListaContexto" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CListaContexto" %>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.action = 'recargarlistasctx.do';
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();		
}

function send(){
   document.forma.submit();
}

function cambiarSeleccion() {
    if(document.BUSCAR.seleccionado.checked == true){
	    setAllCheckBoxes('BUSCAR', '<%=CListaContexto.ID_LISTA_CONTEXTO%>', true);
    }
    if(document.BUSCAR.seleccionado.checked == false){
    	setAllCheckBoxes('BUSCAR', '<%=CListaContexto.ID_LISTA_CONTEXTO%>', false);
    }
}
</script>

<%
List listasSeleccionadas =(List)session.getAttribute(AWRecargarListasContexto.RT_LISTAS_CONTEXTO_SELECCIONADOS);
List listas =(List)session.getAttribute(AWRecargarListasContexto.RT_LISTAS_CONTEXTO);
if (listas==null){
%>
	<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	    <form action="recargarlistasctx.do" method="POST" name="forma"  id="forma">
	  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO%>" value="<%=AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO%>">
		</form>
<%
}
if (listasSeleccionadas == null)
{
	listasSeleccionadas = new ArrayList();
}
%>

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Recargar listas de aplicación</td>
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
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Ingrese la información solicitada...</td>
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
          
        <form action="recargarlistasctx.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO%>" value="<%=  AWRecargarListasContexto.CARGAR_LISTAS_CONTEXTO%>">
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">1. Seleccione las listas a recargar.</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                	if(listas!=null && !listas.isEmpty()){
                	java.util.Iterator it = listas.iterator();
                	
                %>
                <table width="100%" class="camposform">
                <%
	                boolean seleccionado = false;
                	while (it.hasNext()){
                	ListaContexto lista = (ListaContexto)it.next();
                	String idLista = lista.getId();
                %>
                
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>          
                    	<%
                    	seleccionado = listasSeleccionadas.contains(idLista);
                    	%>          	
                    	<input type='checkbox' name='<%=CListaContexto.ID_LISTA_CONTEXTO%>' id='<%=CListaContexto.ID_LISTA_CONTEXTO%>' value='<%=idLista!=null?idLista:""%>' <%=seleccionado?"checked":""%> >
                    </td>
                    <td>                    	
                    	<%=lista.getNombre()!=null?lista.getNombre():""%>
                    </td>
                    <!--<td >                   	
                    	<b>Descripción: </b><%=lista.getDescripcion()!=null?lista.getDescripcion():""%>
                    </td>-->
                  </tr>
                  <%}%>
                  <tr>
                  <td width="20">&nbsp;</td>
                  <td><input type="checkbox" name="seleccionado" onclick='cambiarSeleccion()'></td> 
                  <td class="titulotbcentral">Todos</td>
                  <td class="titulotbcentral">&nbsp;</td>
                  <td class="titulotbcentral">&nbsp;</td>
                 </tr>
                 <tr>
                 <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  </tr>
                </table>
                
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                   	
                    	<b>No existen listas para recargar.</b>
                    </td>
                  </tr>
                </table>                
                <%}%>
                
                 
		        <BR><hr class="linehorizontal"><BR>		                                 
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<a href="javascript:cambiarAccion('<%=  AWRecargarListasContexto.RECARGAR_LISTAS_CONTEXTO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="150" height="21" border="0"></a>
                    </td>
                    <td>
                    	<a href="javascript:cambiarAccion('<%=  AWRecargarListasContexto.TERMINA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a>
                    </td>
                  </tr>
                </table>
            </form>    
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
  
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
