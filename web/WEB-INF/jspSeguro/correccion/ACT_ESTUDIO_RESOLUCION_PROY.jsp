<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWActuacionAdministrativa"%>
<%@page import="gov.sir.core.web.WebKeys"%>

<%
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = turno.getSolicitud();

    ListaElementoHelper redirHelper = new ListaElementoHelper();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
	NotasInformativasHelper helper = new NotasInformativasHelper();

%>
<SCRIPT>
function cambiarAccion(text) {
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.submit();
}

function cambiarAccion1(text) {
    document.CORRECCION1.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION1.submit();
}
</SCRIPT>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Actuaci&oacute;n Administrativa</td>
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
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Proyecci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

            <table width="100%" class="camposform">
            <tr>

		  <form name="CORRECCION1"  id="CORRECCION1" method="post" action="actosAdminCorreccion.do">
		  
              <%
              
              String tipo = (String) session.getAttribute(AWActuacionAdministrativa.TIPO_OFICIO);
              
              if(tipo == null){   
              		tipo = "";              
              }  
              %>
		  
               
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Redactar <%=tipo%></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
               <input type="hidden" name="<%=WebKeys.ACCION%>" value="">	 
              
              <%
              
              String oficio = (String) session.getAttribute(AWActuacionAdministrativa.TEXTO_OFICIO);
              
              if(oficio == null){              
            
              
              %>
              
              
              <br>
              
              <table width="100%" class="camposform">
                <tr>
                  <td class="contenido">
                  
                  <%
                  
                      	
                     	try {
                      		List elemList = new ArrayList();
                      		ListaElementoHelper lHelper = new ListaElementoHelper();

 		                //ElementoLista el1=new ElementoLista(AWActuacionAdministrativa.AUTO_INICIACION, "AUTO_INICIACION");
                        ElementoLista el2=new ElementoLista(AWActuacionAdministrativa.RESOLUCION, "RESOLUCION");
                        
                        //elemList.add(el1);
                        elemList.add(el2);                       	
                      		
                   				lHelper.setTipos(elemList);
		               	        lHelper.setNombre(AWActuacionAdministrativa.TIPO_OFICIO);
                   			    lHelper.setCssClase("camposformtext");
                   			    lHelper.setId(AWActuacionAdministrativa.TIPO_OFICIO);
								lHelper.render(request,out);
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
                  <td width="50"><a href="javascript:cambiarAccion1('<%=AWActuacionAdministrativa.CREAR_OFICIO_DIR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_crear.gif" border="0"></a></td>
                  <td>&nbsp;</td>
                </tr>
              </table>

              
              
              <%
              
              } else {
              
              %>
              
              
              <br>              
              
              
              <table width="100%" class="camposform">
              
              <tr>
                  <td width="20" valign="top"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td align="center">
                   	<% 
                   		try {
                   		
                   		
								TextAreaHelper area = new TextAreaHelper();
								area.setCols("100");
                  				area.setRows("15");
                  				area.setCssClase("camposformtext");
                  				area.setId(AWActuacionAdministrativa.TEXTO_OFICIO);
                  				area.setNombre(AWActuacionAdministrativa.TEXTO_OFICIO);
                                area.setReadOnly(false);
                  				area.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
				  </td>
                </tr>
              </table>


              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccion1('<%=AWActuacionAdministrativa.GUARDAR_OFICIO_DIR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_guardar.gif" border="0"></a></td>
                  <td width="50"><a href="javascript:cambiarAccion1('<%=AWActuacionAdministrativa.IMPRIMIR_OFICIO_DIR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" border="0"></a></td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              
              <%
              
              }
              
              %>
              
              
          </form>

          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
    


      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td width="7"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Terminar</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_reimpresion.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral">

		  <form name="CORRECCION"  id="CORRECCION" method="post" action="actosAdminCorreccion.do">
               <input type="hidden" name="<%=WebKeys.ACCION%>" value="">	 
               <%

			if (true) {
               
               
               %>
                  
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Asignar Permisos </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>

              <table width="100%" class="camposform">
              
              <tr>
                  <td width="20" valign="top"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td align="center">

              <%

              try {
              
                java.util.List vPermisosList = (java.util.List)session.getServletContext().getAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION);
		
      			session.setAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION, vPermisosList );
              

                gov.sir.core.web.helpers.comun.TablaMatriculaHelper editPermisosHelper
                  = new gov.sir.core.web.helpers.comun.TablaMatriculaHelper();

				editPermisosHelper.setAlineacionTodasCeldas(TablaMatriculaHelper.ALINEACION_IZQUIERDA);
                editPermisosHelper.setColCount(2);
                editPermisosHelper.setInputName(WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION);
                editPermisosHelper.setListName(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION);
                editPermisosHelper.setContenidoCelda(gov.sir.core.web.helpers.comun.TablaMatriculaHelper.CELDA_CHECKBOX);
                editPermisosHelper.render(request, out);
              }
              catch( HelperException e ){
                out.println("ERROR " + e.getMessage() );
              }

              %>

				  </td>
                </tr>
              </table>                

               <%
               
               }
               
               %>


              
              <br>              
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccion('<%=AWActuacionAdministrativa.ESTUDIO_RES_FINALIZAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_finalizar.gif" border="0"></a></td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              
          </form>




          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      

		<%
		try{
 		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CORRECCION");
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
		%>

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
