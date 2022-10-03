<%@page import="org.auriga.core.web.*,gov.sir.core.negocio.modelo.constantes.*,gov.sir.core.web.helpers.comun.*,java.util.*,gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.CausalRestitucion"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.web.acciones.restitucionreparto.AWLiquidacionRestitucionReparto"%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<%
    ListaCausalesRestitucionHelper docHelper = new ListaCausalesRestitucionHelper();
    TextHelper textHelper = new TextHelper();    
    TextAreaHelper textAreaHelper = new TextAreaHelper();
    
	Hashtable turnos = (Hashtable)session.getAttribute(WebKeys.TABLA_MINUTAS);
	if (turnos == null){
		turnos = new Hashtable();
	}
	Enumeration enu = turnos.keys();
%>
<script type="text/javascript">
function cambiarAccion(text) {
	document.RESTITUCIONREPARTO.ACCION.value = text;
	document.RESTITUCIONREPARTO.submit();
}

function verDetallesMinuta(nombre,valor,dimensiones)
{
	popup=window.open(nombre+'?ID_TURNO='+valor,'Detalles',dimensiones);
	popup.focus();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Radicaci&oacute;n de la restituci&oacute;n</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>


       <form action="turnoLiquidacionRestitucionReparto.do" method="post" name="RESTITUCIONREPARTO" id="RESTITUCIONREPARTO">
			<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">		            
        
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"> Minuta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></span></td>
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
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Causal de Restituci&oacute;n</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Causal de Restituci&oacute;n</td>
                  <td>
                  
                      <% try {
                      		List causalesRestitucionAux = (List) session.getServletContext().getAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION);
                      		List causalesRestitucion = new ArrayList();
                      		
                      		if(causalesRestitucionAux == null){
                      			causalesRestitucionAux = new Vector();
                      		}
                      		
                      		//Filtrar para mostrar solo causales activas.
                      		for (int k=0; k< causalesRestitucionAux.size(); k++)
                      		{
                      		    CausalRestitucion causalTemp = (CausalRestitucion) causalesRestitucionAux.get(k);
                      		    if (causalTemp.isActivo())
                      		    {
                      		        causalesRestitucion.add(causalTemp);
                      		     }
                      		}
                      		
                      		
                   				docHelper.setTipos(causalesRestitucion);
		                        docHelper.setNombre(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
                   			    docHelper.setCssClase("camposformtext");
                   			    docHelper.setId(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>                        
                  </td>
                </tr>
              </table>
              <br>              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->

                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Solicitud de Minuta Anterior</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td ><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td >Turno a Restituir</td>
                  <td width="25%">
							<% try {
 		                        textHelper.setNombre(CTurno.TURNO_ANTERIOR);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CTurno.TURNO_ANTERIOR);
							    textHelper.render(request,out);
						     }
						 		catch(HelperException re){
							 	out.println("ERROR " + re.getMessage());
						 	}
						 %>                      
                  </td>
                  <td align="left">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td align="left">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWLiquidacionRestitucionReparto.CONSULTAR_MINUTA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_consultar.gif" width="139" height="21" border="0">
                    </td>
               </tr>
               <tr>
                  <td ><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td width="50%">Turno Asociado</td>
                  <td width="50%">Ver Detalles</td>
                </tr>
       			<% 
                while(enu.hasMoreElements()){
                	String dato = (String)enu.nextElement();
                %>            
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%= dato%></td>
                 <td align="left">
                  		<a href="javascript:verDetallesMinuta('admin.detalles.minuta.view','<%=dato%>','scrollbars=yes,width=800,height=500,menubar=no')"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a> 
                  	</td>
                </tr>
              <% 
                } 
                %> 
            </tr>                  
                </tr>
                </table>
                 <table width="100%" class="camposform">
                 <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="15%">Observaciones</td>
                  <td  width="85%" align='left' colspan='3'>
                        <%
							try {
								textAreaHelper.setNombre(CMinuta.OBSERVACIONES_RESTITUCION);
								textAreaHelper.setId(CMinuta.OBSERVACIONES_RESTITUCION);
								textAreaHelper.setCols("95");							
								textAreaHelper.setRows("5");							
								textAreaHelper.setCssClase("camposformtext");
								textAreaHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
						%>                       
                  </td>

                </tr> 
                <tr>
                    <td>
					<input type="checkbox" name="<%=CMinuta.IMPRIMIBLE%>" value="IMPRIMIBLE">
                   	</td>
                   	<td colspan="2">
                   						Imprimir Constancia de Radicación
					</td>
					
                   </tr>
                
                
              </table>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td> 
					<a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.repartonotarial.AWLiquidacionRepartoNotarial.LIQUIDAR%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a>                  
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