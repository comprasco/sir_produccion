<%@page contentType="text/html"%>
<%@page import="java.util.*,
                org.auriga.sas.*,
                org.auriga.sas.modelo.daoObjects.*,
                org.auriga.sas.modelo.transferObjects.*,
                org.auriga.util.FechaConFormato,
                org.auriga.sas.solicitud.*"
%>
<%
  String idSolicitud = request.getParameter("idSolicitud");
  String idUsuario = request.getParameter("idUsuario");

  if(idSolicitud == null){
   out.println("Debe especificar el ID de la solicitud concurrente");
   return;
  }

  if(idUsuario== null){
   out.println("Debe especificar el ID del usuario");
   return;
  }
  
  String helperSimple= request.getContextPath()+"/jsp/sas/listChooserHelper.jsp";
  String helperMultiple = request.getContextPath() +"/jsp/sas/listMultipleHelper.jsp";
  String imagenAyuda= request.getContextPath()+"/jsp/auriga/images/ayuda.gif";
  String imagenObligatorio= request.getContextPath()+"/jsp/auriga/images/obligatorio.gif";
  SASDAOFactory factory = SASDAOFactory.getFactory();
  SolicitudDAO solicitudDAO = factory.getSolicitudDAO();
  Solicitud info = null;
  try {
       info = (Solicitud)solicitudDAO.seleccionarSolicitud(idSolicitud);
  }
  catch(SASDAOException jss){
       jss.printStackTrace();
       return;
  }
  finally {
     if(info == null){
      out.println("No se encontro informacion sobre la solicitud con ID "+idSolicitud);
      return;
     }
     else{
       request.getSession(true).setAttribute(SASKeys.SOLICITUD_EN_SESION, info);
     }
  }
%>
<html>
<head>
<title>Nueva Solicitud Concurrente</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form name="frmNuevaSolicitud" method="post" action ="<%= request.getContextPath()%>/servlet/SasSubmitServlet">
  <input type="hidden" name="<%= SASKeys.SAS_ID_SOLICITUD %>" value="<%= idSolicitud %>">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="titulotablas" width="100%"> 
      <td width="2%" class="imagen_inicio_tabla">&nbsp;</td>
      <td width="95%">Enviar una nueva Solicitud Concurrente</td>
      <td width="2%" class="imagen_fin_tabla">&nbsp;</td>
    </tr>
    <tr> 
      <td colspan="3"> <table width="100%" align="center" bgcolor="#4A6BAE" cellpadding="1" cellspacing="0" border="0">
          <tr> 
            <td> <table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" bgcolor="#FFFFFF">
                <tr> 
                  <td class="subtitulotablas" colspan="3">Informaci&oacute;n General:</td>
                </tr>
                <tr> 
                  <td width="14%" class="textotablas"> <div align="right">Nombre:</div></td>
                  <td class="textotablas" width="86%"><b><%= info.getNombre() %></b></td>
                </tr>
                <tr> 
                  <td width="14%" class="textotablas"> <div align="right">ID Solicitud:</div></td>
                  <td class="textotablas" colspan="3" width="86%"><b><%= info.getIdSolicitud() %></b></td>
                </tr>
                <tr> 
                  <td width="14%" class="textotablas"> <div align="right">Programa 
                      Concurrente:</div></td>
                  <td class="textotablas" width="86%"><b><%= info.getClaseProgConcurrente() %></b></td>
                </tr>
                <tr> 
                  <td width="14%" class="textotablas" align="right">Usuario:</td>
                  <td class="textotablas" width="86%"><input type="text" name="<%= SASKeys.SAS_ID_USUARIO %>" size="30" class="completar2"> 
                  </td>
                </tr>
                <tr> 
                  <td align="right" class="textotablas">Debug:</td>
                  <td class="textosrespuesta"> <select name=<%= SASKeys.SAS_DEBUG_SOLICITUD %> size="1" class="completar2">
                      <option value="0">DESACTIVADO</option>
                      <option value="1">ACTIVADO</option>
                    </select></td>
                </tr>
                <tr> 
                  <td width="14%" class="textotablas"> <div align="right">Formato:</div></td>
                  <td class="textosrespuesta" width="86%"> <select name="<%= SASKeys.SAS_FORMATO_SOLICITUD %>" size="1" class="completar2">
                      <option value="1">TEXTO</option>
                      <option value="2">WORD</option>
                      <option value="3">EXCEL</option>
                      <option value="4">PDF</option>
                      <option value="5">HTML</option>
                      <option value="6">ZIP</option>
                      <option value="7">XML</option>
                    </select> </td>
                </tr>
                <tr> 
                  <td width="14%" class="textotablas"> <div align="right">Prioridad:</div></td>
                  <td class="textosrespuesta" width="86%"> <select name="prioridad" size="1" class="completar2">
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                      <option value="6">6</option>
                      <option value="7">7</option>
                      <option value="8">8</option>
                      <option value="9">9</option>
                      <option value="10">10</option>
                    </select> <br> </td>
                </tr>
                <tr> 
                  <td class="subtitulotablas" colspan="3">Parametros:</td>
                </tr>
                <tr> 
                  <td colspan="2"> <%
                    List li = info.getInformacionParametros();
                    Iterator it = li.iterator();
                    out.println("<table border=\"0\" cellpadding=\"5\" cellspacing=\"1\" align=\"center\" width=\"100%\">");
                    while(it.hasNext()){
                       Parametro ipm =(Parametro)it.next();
                       out.println("<tr><td width=\"25%\" align=\"right\" class=\"textotablas\">"+ipm.getPrompt()+" : </td>");
                       out.println("<td width=\"65%\"><input type=\"text\" name=\""+ipm.getNombre()+"\" size=\"30\" class=\"completar\"></td>");
                       out.print("<td align=\"left\"><img src=\""+imagenAyuda+"\" alt=\"Ayuda\"");
					   String strHelper = ipm.isMultiples()?helperMultiple:helperSimple;
                       strHelper = strHelper +"?"+SASKeys.NOMBRE_PARAMETRO+"="+ipm.getNombre();
                       out.println(" onClick=\"window.open('"+strHelper+"','sas_param_helper','scrollbars=yes,width=350,height=300,top=100,left=100');\"></td>");
                       if(ipm.isObligatorio())  out.println("<td align=\"left\"><img src=\""+imagenObligatorio+"\" alt=\"Campo Obligatorio\"></td>");
                       out.println("</tr>");
                    }
                    out.println("</table>");
%> </td>
                </tr>
                <tr> 
                  <td class="subtitulotablas" colspan="3">Programacion:</td>
                </tr>
                <tr> 
                  <td width="14%" class="textotabla"> <div align="right"> 
                      <p class="textotablas">Programar:</p>
                    </div></td>
                  <td class="textosrespuesta" colspan="3" width="86%"> <select name="<%= SASKeys.SAS_TIPO_PROGRAMACION %>" size="1" class="completar2">
                      <option value="1">FECHA</option>
                      <option value="2">INTERVALO</option>
                      <option value="3" selected>NUNCA</option>
                    </select> </td>
                </tr>
                <tr> 
                  <td width="14%" class="textotabla">&nbsp;</td>
                  <td class="textosrespuesta" width="86%"> <table width="100%" border="0" cellspacing="3" cellpadding="3">
                      <tr> 
                        <td width="22%" class="textotablas" align="right">Fecha 
                          Inicio (<%= FechaConFormato.getFormato() %>):</td>
                        <td width="78%"> <input type="text" name="<%= SASKeys.SAS_FECHA_PROGRAMADA_INICIO %>" size="30" class="completar2"> 
                          <img src="<%= request.getContextPath() %>/jsp/imagenes/helpmin.gif" width="20" height="20"> 
                        </td>
                      </tr>
                      <tr> 
                        <td width="22%" class="textotablas" align="right">Cada:</td>
                        <td width="78%"> <input type="text" name="<%= SASKeys.SAS_INTERVALO%>" class="completar2" size="15"> 
                          <select name="<%= SASKeys.SAS_TIPO_INTERVALO%>" size="1" class="completar2">
                            <option value="1">MINUTOS</option>
                            <option value="2">HORAS</option>
                            <option value="3">DIAS</option>
                          </select> </td>
                      </tr>
                      <tr> 
                        <td width="22%" class="textotablas" align="right">Fecha 
                          Fin (<%= FechaConFormato.getFormato() %>):</td>
                        <td width="78%"> <input type="text" name="<%= SASKeys.SAS_FECHA_PROGRAMADA_FIN%>" size="30" class="completar2"> 
                          <img src="<%= request.getContextPath() %>/jsp/imagenes/helpmin.gif" width="20" height="20"> 
                        </td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
    <tr class="footer_tablas"> 
      <td class="imagen_inicio_abajo_tabla"><img src="<%= request.getContextPath()%>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
      <td><img src="<%= request.getContextPath()%>/jsp/auriga/images/spacer.gif" width="7" height="7"></td>
      <td class="imagen_fin_abajo_tabla"><img src="<%= request.getContextPath()%>/jsp/auriga/images/fin_abajo_tabla.gif" width="10" height="7"></td>
    </tr>
    <tr> 
      <td valign="middle" align="center" colspan="3" class="Boton"><center>
          <ul>
            <li><img src="<%= request.getContextPath()%>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
            <li>
              <input type="submit" name="btnEnviar" value="Enviar">
            </li>
			<li><img src="<%= request.getContextPath()%>/jsp/auriga/images/boton_enviar.gif" width="27" height="22"></li>
            <li><img src="<%= request.getContextPath()%>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
            <li>
              <input type="reset" name="btnBorrar" value="Limpiar form">
            </li>
						<li><img src="<%= request.getContextPath()%>/jsp/auriga/images/boton_regarga.gif" width="27" height="22"></li>
          </ul>
        </center></td>
    </tr>
  </table>
</form>
</body>
</html>
