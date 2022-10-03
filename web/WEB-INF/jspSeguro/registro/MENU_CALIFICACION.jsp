<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Documento sin t&iacute;tulo</title>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<script language="javascript" type="text/javascript">
function showhide(hijo,image,haschild){
try{
  if (haschild){
    var visibilidad = document.getElementById(hijo).style.display
    if (visibilidad == 'none'){
      document.getElementById(hijo).style.display = 'block'
      document.getElementById(image).src = '<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_menos.gif'
    }
    else{
      document.getElementById(hijo).style.display = 'none'
      document.getElementById(image).src = '<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_mas.gif'
    }
  }
  else{
  //nohace nada
  }
}catch(e){}
}

function avanzarAccionMenu(actionForm, accionForm, respuesta, mensaje){
	if(confirm(mensaje)){
		document.FORMMENU.action = actionForm;
		document.FORMMENU.ACCION.value = accionForm;
		document.FORMMENU.RESPUESTAWF.value = respuesta;
		document.FORMMENU.submit();
	}
}
function cambiarAccionMenu(actionForm, accionForm) {
	document.FORMMENU.action = actionForm;
	document.FORMMENU.ACCION.value = accionForm;
	document.FORMMENU.submit();
}
function cargarNotasInformativas() {
   	document.FORMMENU.action = 'notas.informativas.view';
   	document.FORMMENU.submit();
}
function cargarNotasDevolutivas() {
   	document.FORMMENU.action = 'notas.devolutivas.view';
   	document.FORMMENU.submit();
}

function cargarNotasDevolutivasInscripcionParcial() {
   	document.FORMMENU.action = 'calificacion.inscripcion.parcial.view';
   	document.FORMMENU.submit();
}

</script>
</head>

<%
	String vista = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL);
	String VISTA_TURNO =  "calificacion.revision.turno.view";
	String VISTA_FOLIO =  "calificacion.revision.folio.view";
	String VISTA_INSCRIPCION =  "calificacion.inscripcion.view";
	String VISTA_CALIFICACION_FOLIO =  "calificar.folio.view";
	Turno turnoHijo = (Turno)session.getAttribute(WebKeys.TURNO_HIJO);
%>

<body>
<%if(turnoHijo==null) {%>
<form name="FORMMENU" id="FORMMENU" action=''>
    <input type="hidden" name="ACCION" value="">
    <input type="hidden" name="RESPUESTAWF" value="">
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="5">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"><table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Menú</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
    </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
  </tr>
  <tr>
    <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td class="tdtablaanexa02"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%"  border="0" cellpadding="0" cellspacing="0" id="padre_1">
            <tr>
              <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_mas.gif" name="parent_0" width="11" height="22" id="parent_0" onClick="showhide('hijo_0','parent_0',true)"></td>
              <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="8" valign="top" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn03.gif" onClick="showhide('hijo_0','parent_0',true)"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r1_c1.gif" width="8" height="21"></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn01.gif"><a href="#" class="links" onClick="showhide('hijo_0','parent_0',true)">Revisi&oacute;n y An&aacute;lisis</a></td>
                  <td width="8" valign="top" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn04.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r1_c5.gif" width="8" height="21"></td>
                </tr>
                <tr>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r2_c1.gif" width="8" height="5"></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn02.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="1" height="1"></td>
                  <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r2_c5.gif" width="8" height="5"></td>
                </tr>
              </table></td>
              </tr>
          </table>
            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="hijo_0" style="display:<%=vista!=null&&(vista.equals(VISTA_FOLIO)||vista.equals(VISTA_TURNO))?"block":"none"%>">
              <tr>
                <td width="11" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="11" height="22"></td>
                <td width="11" valign="bottom" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><p><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/flecha_indicator.gif" width="15" height="22"></p>
                </td>
                <td>
                  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                      <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                    </tr>
                    <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      <td><a href="calificacion.revision.turno.view" class="links">Informaci&oacute;n Turno </a></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                    </tr>
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                    </tr>
                  </table>
                  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                      <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                    </tr>
                    <tr>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      <td><a href="calificacion.revision.folio.view" class="links">Informaci&oacute;n Folio </a></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                    </tr>
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                    </tr>
                  </table></td>
              </tr>
          </table>

			<%
			Boolean isHabilitadoCalificacion= new Boolean(true);//(Boolean)session.getAttribute(gov.sir.core.web.WebKeys.HABILITADO_CALIFICACION);

			//LA VARIABLE gov.sir.core.web.WebKeys.MOSTRAR_OPCION_INSCRIPCION NO ES NULA LA PRIMERA VEZ QUE 
			//SE VALIDA PARA QUE SE DESPLIEGUE LA OPCION DE INFORMACION DE REGISTRO
			boolean habilitarOpcionRegistro = false;
			Boolean mostrarOpcionInscripcion=new Boolean(true);// (Boolean)session.getAttribute(gov.sir.core.web.WebKeys.MOSTRAR_OPCION_INSCRIPCION);
			if(mostrarOpcionInscripcion!= null && mostrarOpcionInscripcion.booleanValue()){
				habilitarOpcionRegistro = true;
			}

			session.removeAttribute(gov.sir.core.web.WebKeys.MOSTRAR_OPCION_INSCRIPCION);
			%>

            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="padre_1">
              <tr>
				<%
				if(isHabilitadoCalificacion!=null && isHabilitadoCalificacion.booleanValue()){
				%>
                <td width="11" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_mas.gif" name="parent_1" width="11" height="22" id="parent_1" onClick="showhide('hijo_1','parent_1',true)"></td>
				<%
				}else{
				%>
                <td width="11" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_mas.gif" name="parent_1" width="11" height="22" id="parent_1" onClick="cambiarAccionMenu('calificacion.do', '<%=gov.sir.core.web.acciones.registro.AWCalificacion.VALIDAR_TURNO_PARA_CALIFICACION%>');showhide('hijo_1','parent_1',true)"></td>
				<%
				}
				%>
                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="8" valign="top" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn03.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r1_c1.gif" width="8" height="21"></td>
					<%
					if(isHabilitadoCalificacion!=null && isHabilitadoCalificacion.booleanValue()){
					%>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn01.gif"><a href="#" class="links" onclick="showhide('hijo_1','parent_1',true)">Información Registro</a></td>
					<%
					}else{
					%>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn01.gif"><a href="javascript:cambiarAccionMenu('calificacion.do', '<%=gov.sir.core.web.acciones.registro.AWCalificacion.VALIDAR_TURNO_PARA_CALIFICACION%>')" class="links" onclick="showhide('hijo_1','parent_1',true)">Información Registro</a></td>
					<%
					}
					%>
                      <td width="8" valign="top" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn04.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r1_c5.gif" width="8" height="21"></td>
                    </tr>
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r2_c1.gif" width="8" height="5"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn02.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="1" height="1"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r2_c5.gif" width="8" height="5"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>

			<%
			if(isHabilitadoCalificacion!=null && isHabilitadoCalificacion.booleanValue()){
			%>

            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="hijo_1" style="display:<%=((vista!=null&&(vista.equals(VISTA_INSCRIPCION)||vista.equals(VISTA_CALIFICACION_FOLIO))) || habilitarOpcionRegistro)?"block":"none"%>">
              <tr>
                <td width="11" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="11" height="22"></td>
                <td width="11" valign="bottom" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><p><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/flecha_indicator.gif" width="15" height="22"></p></td>
                <td>
                  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                      <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                    </tr>
	                <tr>
	                  <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
	                  <td><a href="javascript:cambiarAccionMenu('calificacion.do', '<%=gov.sir.core.web.acciones.registro.AWCalificacion.MOSTRAR%>')" class="links">Inscripci&oacute;n</a></td>
	                  <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
	                </tr>
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                    </tr>
                  </table>
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                      </tr>
                      <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                        <td><a href="javascript:cargarNotasDevolutivasInscripcionParcial();" class="links">Inscripci&oacute;n Parcial</a></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                      </tr>
                    </table>
                    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                      </tr>
                      <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                        <td><a href="javascript:cargarNotasDevolutivas();" class="links">Devoluci&oacute;n</a></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                      </tr>
                    </table>
					
					     <!-- block:sof description="opcion reproduccion de sellos" -->
                    <!--

                    La reproduccion de sellos
                    es un link.

                    -->

<%

Boolean optionReproduccionSellos_Enabled = null;

// temporal test
// session.setAttribute( gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAMID_OPTION_ENABLED, Boolean.TRUE );


// ---------------------------------

optionReproduccionSellos_Enabled
= (Boolean)session.getAttribute( gov.sir.core.negocio.modelo.constantes.CAccionCalificacionReproduccionSellos.PARAMID_OPTION_ENABLED );
if( ( null != optionReproduccionSellos_Enabled )
  &&( optionReproduccionSellos_Enabled.booleanValue() ) ){
%>




                    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                      </tr>
                      <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                        <td><a href="calificacion.folio.reproduccion-sellos.view" class="links">Reproducción de Inscripción</a></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                      </tr>
                    </table>

                    <!-- block:eof -->

<%

}

%>
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                      </tr>
                      <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                        <td><a href="javascript:avanzarAccionMenu('calificacion.do', '<%=gov.sir.core.web.acciones.registro.AWCalificacion.DELEGAR_DIGITACION_MASIVA%>', '<%=gov.sir.core.web.acciones.registro.AWCalificacion.DIGITACION%>','¿Esta seguro que desea enviar el turno a digitación masiva?')" class="links">Digitador Masivo </a></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                      </tr>
                    </table>
                    
               
				</td>
              </tr>
            </table>
		
			<%
			}
			%>

            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="padre_2">
              <tr>
                <td width="11" ><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_mas.gif" name="parent_2" width="11" height="22" id="parent_2" onClick="showhide('hijo_2','parent_2',true)"></td>
                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="8" valign="top" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn03.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r1_c1.gif" width="8" height="21"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn01.gif"><a href="#" class="links" onclick="showhide('hijo_2','parent_2',true)">Opciones</a></td>
                      <td width="8" valign="top" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn04.gif"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r1_c5.gif" width="8" height="21"></td>
                    </tr>
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r2_c1.gif" width="8" height="5"></td>
                      <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn02.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="1" height="1"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r2_c5.gif" width="8" height="5"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>

            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="hijo_2" style="display:block">
              <tr>
                <td width="11"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="11" height="22"></td>
                <td width="11" valign="bottom" background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_siguiente.gif"><p><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/flecha_indicator.gif" width="15" height="22"></p></td>
                <td>

                    
                    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                      </tr>
                      <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                        <td><a href="javascript:cambiarAccionMenu('calificacion.do', '<%=gov.sir.core.web.acciones.registro.AWCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION%>')" class="links">Formulario Temporal </a></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                      </tr>
                    </table>

                     <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn05.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td width="5"><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r3_c6.gif" width="5" height="4"></td>
                      </tr>
                      <tr>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn07.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                        <td><a href="javascript:cargarNotasInformativas();" class="links">Notas Informativas</a></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn08.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="5" height="10"></td>
                      </tr>
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c3.gif" width="5" height="4"></td>
                        <td background="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_bgn06.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="4"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/menuarbol/menu_cal_r5_c6.gif" width="5" height="4"></td>
                      </tr>
                    </table></td>
              </tr>
            </table>

	</td>
      </tr>
    </table></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
  </tr>
</table>

<%Boolean hayActosVencidos = (Boolean)session.getAttribute(gov.sir.core.web.acciones.registro.AWCalificacion.HAY_ACTOS_VENCIDOS);
if(hayActosVencidos != null && hayActosVencidos.booleanValue()){
%>

<table width="150" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="5">&nbsp;</td>
    <td width="12"><img name="tabla_gral_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"><table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Alertas</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
          <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ico_alerta.gif" width="20" height="20"></td>
        </tr>
    </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
    	<%
    	String dias = (String)session.getAttribute(gov.sir.core.web.acciones.registro.AWCalificacion.PLAZO_VENCIMIENTO);%>
		<p>El documento tiene más de <%=dias%> días de otorgado o expedido<br></p>
	</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
  </tr>
</table>
<%}%>
<p>&nbsp;</p>
<%} %>
</body>
</html>
