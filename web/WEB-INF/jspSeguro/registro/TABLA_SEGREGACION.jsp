<%@page import="org.auriga.core.web.*" %>
<%@page import="gov.sir.core.web.helpers.registro.*" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.registro.AWSegregacion" %>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import ="gov.sir.core.web.acciones.correccion.AWModificarFolio"%>
<%@page import = "gov.sir.core.negocio.modelo.*"%>
<%@page import = "gov.sir.core.negocio.modelo.constantes.*"%>

<%
	TablaSegregacionesHelper tabla = new TablaSegregacionesHelper();
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script language="javascript">
function quitar(pos,accion) {
	document.SEGREGACION.POSICION.value = pos;
    document.SEGREGACION.ACCION.value = text;
    document.SEGREGACION.submit();
}

function verAnotacion(nombre,valor,dimensiones,pos)
{
	document.SEGREGACION.POSICION.value=pos;
	popup=window.open(nombre,valor,dimensiones);
	popup.focus();
}

function cambiarAccion(text) {
    document.SEGREGACIONBOTONES.ACCION.value = text;
    document.SEGREGACIONBOTONES.submit();
}

</script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">
			ASISTENTE SEGREGACIÓN - PASO 3 - CREAR FOLIOS DERIVADOS</td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folios
                  Derivados
                  </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_desenglobar.gif" width="16" height="21"></td>
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

		<%
		String a = (String)session.getAttribute(gov.sir.core.negocio.modelo.constantes.CFolioDerivado.NOMBRE_LOTE);
				String b = (String)session.getAttribute(gov.sir.core.negocio.modelo.constantes.CFolioDerivado.DESCRIPCION);
						String c = (String)session.getAttribute(gov.sir.core.negocio.modelo.constantes.CFolioDerivado.PORCENTAJE);
			
		%>


		<%try{
			tabla.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
			re.printStackTrace();
		}%>

          </td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral"><hr class="linehorizontal">
              	<form name="SEGREGACION" id="SEGREGACIONBOTONES" method="post" action="segregacion.do">
			<input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWSegregacion.SEGREGAR_MASIVO%>">
			<input type="hidden" name="POSSCROLL" id="POSSCROLL" value="<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
			<input type="hidden" name="<%=WebKeys.POSICION%>">
			<%
      Fase fase = (Fase)session.getAttribute(WebKeys.FASE) ;
  	if(!fase.getID().equals(CFase.CAL_CALIFICACION)){
	  %>
      <table width="100%" class="camposform">
         <tr>
              <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
              <!--
               * @author      :   Julio Alcázar Rivas
               * @change      :   se cambia el texto Salvedad Folio Padre por Salvedad Anotacion
               * Caso Mantis  :   04131
            !-->
              <td>Salvedad Anotaci&oacute;n</td>
          </tr>
              <tr>
                 <td width="20">&nbsp;</td>
                 <td>

                 <% // id
					
                 TextAreaHelper textAreaHelper = new TextAreaHelper();
                 
                 try {
                   TextHelper local_TextHelper;

                   local_TextHelper = new TextHelper();
                   /*
                    * @author      :   Julio Alcázar Rivas
                    * @change      :   Se modifica el nombre y id del helper
                    * Caso Mantis  :   02359
                    */
                   local_TextHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID );
                   local_TextHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADID );
                   local_TextHelper.setTipo( "hidden" );
                   local_TextHelper.setCssClase("camposformtext");
                   local_TextHelper.render( request, out );

                 }
                 catch( HelperException re ) {
		  out.println( "ERROR " + re.getMessage() );
                 }



                 %>
                 <% // text
                          try {
                                  textAreaHelper.setFuncion( " onmouseover='this.rows=7' onmouseout='this.rows=2' ");

 		                  /*
                                   * @author      :   Julio Alcázar Rivas
                                   * @change      :   Se modifica el nombre y id del helper
                                   * Caso Mantis  :   02359
                                   */
                                  textAreaHelper.setNombre(  AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION );
                  		  textAreaHelper.setId(      AWModificarFolio.ITEMID_FOLIOEDICION_ANOTACIONXSALVEDADDESCRIPCION );
 		                  textAreaHelper.setCols( "130" );
 		                  textAreaHelper.setRows( "2" );
                  	          textAreaHelper.setCssClase("camposformtext");
				  textAreaHelper.render(request,out);
			  }
                          catch( HelperException re ){
				  out.println("ERROR " + re.getMessage());
                          }
                 %>

                </td>
           </tr>
     </table>
        </td>
        </tr>
        <%} %>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><hr class="linehorizontal">
			

	          <table width="100%" class="camposform">
	              <tr>
		              <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
						<td width="150" align="center"><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_segregar.gif" name="Folio" width="150" height="21" border="0" id="Folio"></td>


						<!--BOTON GUARDAR-->                 
	                    <TD width="150">
		                <a href="javascript:cambiarAccion( '<%=AWSegregacion.GUARDAR_WEB_SEGREGACION%>' );">
		                 	<img alt="Guardar" src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
		                </a>
						</TD>
						
						                  
						<!--BOTON REGRESAR-->                 
	                    <TD width="150">
		                <a href="registro.segregar.herencia.view?POSSCROLL=<%=(request.getParameter("POSSCROLL")!=null?request.getParameter("POSSCROLL"):"")%>">
		                 	<img alt="Regresar" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
		                </a>
						</TD>
						                  
						<!--BOTON CANCELAR-->                 
						<TD width="150">						                  
							<%-- SOF:SCRIPT-VARS --%>
							<script type="text/javascript">
							   var CANCELAR_SEGREGACION = "<%= AWSegregacion.CANCELAR_SEGREGACION %>";
							   var ELIMINAR_SEGREGACION = "<%= AWSegregacion.ELIMINAR_SEGREGACION %>";
							   function eliminarSegregacion(){
							      if(confirm('¿Desea eliminar la segregación en curso?')){
							         cambiarAccion( ELIMINAR_SEGREGACION );
							      }
							   }								   
							</script>
							<%-- EOF:SCRIPT-VARS --%>
						
						      <%-- SOF:BUTTON --%>
						        <a href="javascript:cambiarAccion( CANCELAR_SEGREGACION );">
						         <img alt="Salir de la segregación en curso" src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" name="Folio" width="139" height="21" border="0" id="Folio"  />
						        </a>
						      <%-- EOF:BUTTON --%>						
						</TD>
						
			              <!--ELIMINAR SEGREGACIÓN EN CURSO-->
			              <td width="150">
			                <a href="javascript:eliminarSegregacion();">
			                 <img alt="Eliminar segregación en curso" src="<%=request.getContextPath()%>/jsp/images/btn_eliminar_segregacion.gif" name="Folio" width="180" height="21" border="0" id="Folio"  />
			                </a>
			          	  </td>        
						
						
			            </form>						




	        	      <td>&nbsp;</td>
	            	  </tr>
	          </table>
            </td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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