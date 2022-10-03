<%@page import ="gov.sir.core.web.*" %>
<%@page import ="java.util.*" %>
<%@page import ="gov.sir.core.negocio.modelo.*" %>
<%@page import ="gov.sir.core.web.acciones.comun.AWTurno" %>
<%
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
	String fecha = new String();
	Calendar calendar = Calendar.getInstance();
	String mensaje = new String();
	Folio folio = (Folio)session.getAttribute(WebKeys.FOLIO);
	if(turno != null && turno.getIdWorkflow() != null){
		calendar.setTime(turno.getFechaInicio());
		int mes = calendar.get(Calendar.MONTH) + 1;
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		if(dia<10){
			fecha =	"0"+dia;
		} else{
			fecha = String.valueOf(dia);
		}
		
		fecha += "/" + mes + "/" + calendar.get(Calendar.YEAR);
		mensaje="La operacion sobre el turno " + turno.getIdWorkflow() + " del " + fecha + " fue realizada satisfactoriamente. \n Se creo el folio identificado con la matricula " + folio.getIdMatricula();	
	} else {
		mensaje="La operacion fue realizada satisfactoriamente";
	}
	
	Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
	
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css"> 
<!--<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/plugins.js">-->
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <!-- fwtable fwsrc="SIR_error.png" fwbase="tabla_error.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td width="12">&nbsp;</td>
    <td width="12"><img name="tabla_error_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c1.gif" width="12" height="30" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_iniciosesion.gif" width="16" height="21"></td>
                <td><img src="<%=request.getContextPath()%>/jsp/images/ico_satisfactorio.gif" width="16" height="21"></td>
              </tr>
            </table></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_error_bgn001.gif" class="titulotbcentral">Satisfactorio</td>
          <td width="14"><img name="tabla_error_r1_c4" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c4.gif" width="14" height="30" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_error_r1_c6" src="<%=request.getContextPath()%>/jsp/images/tabla_error_r1_c6.gif" width="12" height="30" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
        <tr>
          <td align="center" valign="middle" class="titulotbcentral"><%=mensaje%></td>
          <td>
          <image src="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif" />
          <!--<script language="javascript" type="text/javascript">
		  var Imagen="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.gif"
		  var pelicula="<%=request.getContextPath()%>/jsp/images/satisfactorio_animated.swf"
		  var param="<PARAM NAME=FlashVars VALUE=\"path=<%=request.getContextPath()%>/jsp/\">"
		  var ancho="70"
		  var alto="70"
		  //plugindetectado();
		</script>-->
		</td>
        </tr>
		<tr>
		<%if(fase != null){%>
	       	<form name="form1" method="post" action="turno.do">
		    <td>
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="<%=AWTurno.LISTAR%>">
			<input type="hidden" name="<%=AWTurno.ID_FASE%>" value="<%=((Fase)session.getAttribute(WebKeys.FASE)).getID() %>">
	        <input name="Submit2" type="submit" class="botontextual" value="Seleccionar nuevo turno">
			</td>
			</form>
		<%} else {%>
	       	<form name="form1" method="post" action="procesos.view	">
		    <td>
	        <input name="Submit2" type="submit" class="botontextual" value="Regresar al Menu principal">
			</td>
			</form>		
		<%}%>
		</tr>
      </table></td>
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
