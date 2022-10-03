<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Vector" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.administracion.TablaSeleccionDatosHelper"%>
<%@page import="gov.sir.core.negocio.modelo.TipoNota" %> 
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoNota" %> 
<%@page import="gov.sir.core.negocio.modelo.constantes.CNota" %> 

<%@page import="org.auriga.core.web.HelperException" %>
<%
	//TextHelper textHelper = new TextHelper();

	TablaSeleccionDatosHelper tablaHelper = new TablaSeleccionDatosHelper();
	tablaHelper.setSelectedValue_Enabled(true);
	tablaHelper.setSelectedValue_Id_Key(WebKeys.TITULO_CHECKBOX_ELIMINAR);
	tablaHelper.setTwidth("100%");
	
	Vector titulos = new Vector();
	titulos.add("ID");
	titulos.add("Fase");
	
	tablaHelper.setTitulos(titulos);
	
	boolean carga = true;

	Boolean bRecargar = (Boolean)session.getAttribute(WebKeys.RECARGA_MAYOR_VALOR);
	if(bRecargar != null) {
		carga = bRecargar.booleanValue();
	}

	//if(session.getAttribute(WebKeys.LISTADO_PENDIENTES_MAYOR_VALOR) != null)
    //	carga = false;
	
	if(carga) {
    	session.removeAttribute(CNota.DESCRIPCION);
    	session.removeAttribute(WebKeys.TITULO_CHECKBOX_ELIMINAR);
    	session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
	}
	
	/*inicio de la parte de agregar una nota informativa */
	ListaElementoHelper tiposNotaHelper = new ListaElementoHelper();
	//ListaElementoHelper tiposVisibilidadHelper = new ListaElementoHelper();
	//ListarNotasPasadas notasHelper = new ListarNotasPasadas();
	
    List listaNotasInformativas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS_PMY);
    List elementosTipoNota = new ArrayList();
	
	if (listaNotasInformativas != null)
	{
	   Iterator itTipos = listaNotasInformativas.iterator();
	   
	   while (itTipos.hasNext()) {
		   TipoNota tipoNota = (TipoNota) itTipos.next();
		   elementosTipoNota.add(new ElementoLista(tipoNota.getIdTipoNota(), 
        		   tipoNota.getIdTipoNota() + " - " +tipoNota.getNombre()));
	   }
	}
	
		
	tiposNotaHelper.setCssClase("camposformtext");
	tiposNotaHelper.setId(CTipoNota.ID_TIPO_NOTA);
	tiposNotaHelper.setNombre(CTipoNota.ID_TIPO_NOTA);
	tiposNotaHelper.setTipos(elementosTipoNota);
	
	TextAreaHelper helper = new TextAreaHelper();
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cargaInicial() {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%= AWAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR %>';
	document.BUSCAR.submit();
}

function submitform(){
	document.BUSCAR.ACCION.value='<%=AWAdministracionHermod.COLOCAR_DESCRIPCION_NOTA_INF_PMY%>';
	document.BUSCAR.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21" alt="Administrador"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Eliminar Turnos con Pago Mayor Valor Pendiente</td>
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
    <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=WebKeys.ACCION %>" value="">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img alt="" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img alt="" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Lista de turnos con Pago de Mayor Valor pendiente</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Listado</td>
                    <td width="16" class="bgnsub"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
        
                <table width="100%" class="camposform">
                  <tr>
                    <td>
                    <% try {
                    		
                    	  //LISTA_TURNOS_STRING se inicializa en el doEnd de AWAdministracionHermod
	                      tablaHelper.setListName(WebKeys.LISTADO_PENDIENTES_MAYOR_VALOR);
                          tablaHelper.setContenidoCelda(TablaSeleccionDatosHelper.FILA_RADIO);//radio button es necesario
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>
	                  </td>
                  </tr>
          
           		</table>
            </td>
           	<td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
           </tr>
           <tr>
           <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
           <td>
           		<table width="100%" border="0" cellpadding="0" cellspacing="0">
           			<tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Nota informativa</td>
                    <td width="16" class="bgnsub"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
           </td>
           <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
           </tr>
       			  <tr>
       			  	<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
       			  	<td>
       					<table width="100%" class="camposform">
       						
							              
							<!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
			                
							<tr> 
			                  <td width="20"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                  <td>Escoger Tipo de Nota</td>
			                  <td>
							<% 
							try {
								tiposNotaHelper.setFuncion("onChange=\"submitform();\"");
								tiposNotaHelper.render(request,out);
							}catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}	
							%>
							  </td>
							  <td>
								<%
								try{
									helper.setCols("80");
									helper.setCssClase("campositem");
									helper.setId(CTipoNota.DESCRIPCION);
									helper.setNombre(CTipoNota.DESCRIPCION);
									helper.setRows("3");
									helper.setReadOnly(true);
									helper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}	
								%>
								
							  </td>
				            </tr>
							            
							              
				            <tr> 
			                  <td width="20"><img alt="" src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
			                  <td>Descripci&oacute;n</td>
			                  <td colspan="2">
								<% 
								try{
									helper.setCols("100");
									helper.setReadOnly(false);
									helper.setCssClase("camposformtext");
									helper.setId(CNota.DESCRIPCION);
									helper.setNombre(CNota.DESCRIPCION);
									helper.setRows("10");
									helper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}	
								%>
			                  </td>
			                </tr>
				              
			                
			                </table>
			                </td>
			                <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
			               </tr>
			              
			               	  
                <tr>
                	<td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
                	<td>
		                <table width="100%" class="camposform">
		                  <tr>
		                    <td class="campostip04">Cuando termine de eliminar turnos con pago de mayor valor pendiente haga click en volver. </td>
		                  </tr>
		                </table>
		             </td>
		             <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
		        </tr>
		        
                
                <tr>
                 <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>	
	             <td>
	                <table width="100%" class="camposform">
	              
	                  <tr>
	                    <td width="20">
	                    	<img alt="" src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
	                    </td>
	                    <td width="15">
	                    	<input name="imageField" type="image" onClick="cambiarAccion('<%= AWAdministracionHermod.ELIMINAR_PENDIENTES_PAGO_MAYOR_VALOR %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0">
	                    </td>
	                    <td>
	                    	<input name="imageField" type="image" onClick="cambiarAccion('<%= AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
	                    </td>
	                  </tr>
	                </table>
	             </td>
	             <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
	            </tr>
           		  
            <tr>
            	<td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            	<td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img alt="" src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            	<td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        	</tr>
		
		</table>
		</form>
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
<%if(carga){%>
	<script>cargaInicial();</script>
<%}%>