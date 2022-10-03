<script type="text/javascript">
function cambiarAccion(text) {
   document.CALIFICAR.ACCION.value = text;
   document.CALIFICAR.submit();
}
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Documento sin t&iacute;tulo</title>
    <link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        <!--
        .Estilo2 {font-size: 11px; color: #3A414E; text-decoration: none; background-color: #FFFFFF; border-top: 1px solid #5D687D; border-right: 1px solid #5D687D; border-bottom: 1px solid #5D687D; border-left: 5px solid #6A7891; font-family: Verdana, Arial, Helvetica, sans-serif;}
        -->
    </style>
    
    <script type="text/javascript">
		function f_scrollTop() {
			<%if(request != null && request.getSession() != null 
			&& request.getSession().getAttribute(WebKeys.HAY_EXCEPCION) != null 
			&& (((Boolean)request.getSession().getAttribute(WebKeys.HAY_EXCEPCION))).booleanValue() == true){%>
				window.scroll(0,0);
			<%}else{%>	
				window.scroll(0,<%=(request.getParameter("POSSCROLL")!= null && !request.getParameter("POSSCROLL").equals("")?request.getParameter("POSSCROLL"):"0")%>);					
			<%}%>							
		}
		function capturarScroll(myElement) {
			document.getElementById("POSSCROLL").value =(document.body ? document.body.scrollTop :0);
		}
	</script>
   
</head>
<%
	session.removeAttribute(gov.sir.core.web.acciones.consulta.AWConsulta.FOLIO_CALIFICACION);
%>
<body onload="javascript:f_scrollTop()">

<%-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + --%>
<%-- Cargar parametros para configurar los permisos --%>
<%

        // obtener los estilos de despliegue
        gov.sir.core.web.helpers.correccion.region.model.RegionManager regionManager = null;
		String strAccion = request.getParameter(WebKeys.ACCION);
		if(strAccion != null && strAccion.equals("ACEPTAR_EDICION_ANOTACION")){
			request.getSession().setAttribute("POSSCROLL",request.getParameter("POSSCROLL"));
		}
		
        localBlockAuth: {

			
          gov.sir.core.web.helpers.correccion.region.model.RegionManager localRegionManager
          = new gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager();

          // mapa de permisos
          java.util.HashMap permisosMap;

          // test1 -----------------------------------------------------

          // un mapa de permisos de prueba
          java.util.HashMap testPermisosMap = new java.util.HashMap();

/*
          // populate the test
          String testPermisosMap_Key = null;
          gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel testPermisosMap_Value = null;

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.FOLIO_NUMEROCATASTRAL_ID ;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_CIUDADANOS_ID ;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_ESPECIFICACION_ID ;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );

          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_FECHA_ID;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );


          //TODO: quitar key para anoraciones-id
          testPermisosMap_Key   = gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.ANOTACION_ID;
          testPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( testPermisosMap_Key );
          testPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega
          testPermisosMap.put( testPermisosMap_Key, testPermisosMap_Value );
*/
          // ----------------------------------------------------------

          // cargar permisos de prueba
          permisosMap = testPermisosMap;


          // loadPermisos1 -----------------------------------------------------

          java.util.List modelPermisosList = null;
           modelPermisosList = (java.util.List)session.getAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST );

          if( ( null == modelPermisosList ) ) {
              // cargar defaultpermisos - - testPermisos

              // cargar permisos de prueba
              permisosMap = testPermisosMap;
              // permisosMap = new java.util.HashMap();
          }
          else {


              // mapa de permisos model
              java.util.HashMap modelPermisosMap = new java.util.HashMap();

              // populate the model
              String modelPermisosMap_Key = null;
              gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel modelPermisosMap_Value = null;

              // cargar model permisos
              java.util.Iterator iterator
              = modelPermisosList.iterator();

              for(; iterator.hasNext(); ) {
                gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion permiso
                = (gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion)iterator.next();


                modelPermisosMap_Key   = permiso.getIdPermiso();//gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.FOLIO_NUMEROCATASTRAL_ID ;
                modelPermisosMap_Value =  new gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel( modelPermisosMap_Key );
                modelPermisosMap_Value.setNivelPermiso( gov.sir.core.web.helpers.correccion.region.model.Region.AspectModel.READ ); // NULL no se despliega

                modelPermisosMap.put( modelPermisosMap_Key, modelPermisosMap_Value );

              }

              // ----------------------------------------------------------
              permisosMap = modelPermisosMap;

          } // end if


          // cargar para probar
          // TODO: remover despues
          // permisosMap = testPermisosMap;


          // filter expression:
          // aplicar los permisos;
          // con la lista de permisos, relaciona los items que se deben desplegar o no
          localRegionManager.filter( permisosMap );

          regionManager = localRegionManager;

        } // :localBlockAuth

        // ..... se debe propagar el conjunto de permisos a las paginas hijas
        session.setAttribute( "param_RegionManager", regionManager );

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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif">



     <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcci&oacute;n del Folio</td>
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
<%--
      <jsp:include page="CORRECCION_FOLIO_TABS_CORRECTOR.jsp" flush="false">
        <jsp:param name="param_RegionManager" value="<%=regionManager%>" />
      </jsp:include>
--%>
      <%@ include file="CORRECCION_FOLIO_TABS_CORRECTOR.jsp" %>
      <%-- los parametros para despliegue condicional se obtienen de sesion --%>
    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>












    <!--Fila para colocar la opción de regresar-->
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
    <td align="left" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Regresar a corregir otros folios</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
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
    <table width="100%" class="camposform">
        <tr>
            <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
            <td width="150" align="center"><a href="<%=request.getContextPath()%>/correccion.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Regresar" width="139" height="21" border="0" id="Regresar"></a></td>
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


    </td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
    </tr>

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
</html>
