<%@page contentType="text/html"%>
<%@page import="java.util.*,
                org.auriga.sas.*,
                org.auriga.sas.modelo.daoObjects.*,
                org.auriga.sas.modelo.transferObjects.*,
                org.auriga.util.FechaConFormato,
                org.auriga.sas.solicitud.*"
%>

<% 
  String helperSimple= request.getContextPath()+"/jsp/sas/listChooserHelper.jsp";
  String helperMultiple = request.getContextPath() +"/jsp/sas/listMultipleHelper.jsp";
  String imagenAyuda= request.getContextPath()+"/jsp/sas/ayuda.gif";
  String imagenObligatorio= request.getContextPath()+"/jsp/sas/obligatorio.gif";
 RegistroEjecucion registro = (RegistroEjecucion)request.getAttribute("REGISTRO_SOLICITUD");
if (registro == null) {
  out.println("No hay registro de esta solicitud");
  out.close();
  return;
}

%>
<html>
<head>
<title>Detalles Solicitud Concurrente</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/jsp/sas.css" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#000000">
<% if (registro.getFase().equals(SolicitudConcurrente.FASE_EN_EJECUCION)){ %>
<center>
<form action= '<%= request.getContextPath()%>/servlet/SasManualServlet' method='post'>
<%}%>

<table width="75%" align="center" bgcolor="#4A6BAE" cellpadding="1" cellspacing="0" border="0">
  <tr>
    <td> 
      <table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" bgcolor="#FFFFFF">
        <tr> 
          <td class="titulotablas" colspan="4">Detalles de Solicitud Concurrente</td>
        </tr>
        <tr> 
          <td class="subtitulotablas" colspan="4">Informaci&oacute;n General:</td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right">ID Ejecuci&oacute;n:</div>
          </td>
          <td class="textosrespuesta" colspan="3"> 
            <input type="text" name='<%= SASKeys.SAS_ID_EJECUCION%>' size="13" value="<%= registro.getEjecucionSolicitud().getIdEjecucion() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right">ID Solicitud:</div>
          </td>
          <td class="textosrespuesta" colspan="3"> 
            <input type="text" name='<%= SASKeys.SAS_ID_SOLICITUD %>' size="100" value="<%= registro.getEjecucionSolicitud().getSolicitud().getIdSolicitud() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right">Nombre:</div>
          </td>
          <td class="textosrespuesta" colspan="3"> 
            <input type="text" name="nombre" size="100" value="<%= registro.getEjecucionSolicitud().getSolicitud().getNombre() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%"> 
            <div align="right" class="textotablas">Programa Concurrente:</div>
          </td>
          <td colspan="3"> 
            <input type="text" name="idEjecucion4" size="100" value="<%= registro.getEjecucionSolicitud().getSolicitud().getClaseProgConcurrente() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas" align="right">Formato:</td>
          <td colspan="3"> 
            <input type="text" name="formato" size="10" value="<%= registro.getEjecucionSolicitud().getDescripcionFormato() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right">Prioridad:</div>
          </td>
          <td class="textosrespuesta" colspan="3"> 
            <input type="text" name="prioridad" size="3" value="<%= registro.getEjecucionSolicitud().getPrioridad() %>" class="completar2">
          </td>
        </tr>
        <tr> 
                  <td class="subtitulotablas" colspan="4">Parametros:</td>
                </tr>
        <tr> 
          <td colspan="4"> 
<%
                    List li = registro.getEjecucionSolicitud().getSolicitud().getInformacionParametros();
                    Hashtable params = registro.getEjecucionSolicitud().getParametros();
                    Iterator it = li.iterator();
                    out.println("<table border=\"0\" cellpadding=\"5\" cellspacing=\"1\" align=\"center\" width=\"100%\">");
                    while(it.hasNext()){
                       Parametro ipm =(Parametro)it.next();
                       out.println("<tr><td width=\"25%\" align=\"right\" class=\"textotablas\">"+ipm.getPrompt()+" : </td>");
                       out.print("<td width=\"65%\">");
                       String valor = (String)params.get(ipm.getNombre());
                       if (valor == null) valor = "";
                       out.print("<input type=\"text\" name=\""+ipm.getNombre()+"\" size=\"30\" class='completar' value='"+valor+"'>");
                       out.println("</td>");
                       out.print("<td align=\"left\"><img src=\""+imagenAyuda+"\" alt=\"Ayuda\"");
					   String strHelper = ipm.isMultiples()?helperMultiple:helperSimple;
                       strHelper = strHelper +"?"+SASKeys.NOMBRE_PARAMETRO+"="+ipm.getNombre();
                       out.println(" onClick=\"window.open('"+strHelper+"','sas_param_helper','scrollbars=yes,width=350,height=300,top=100,left=100');\"></td>");
                       if(ipm.isObligatorio())  out.println("<td align=\"left\"><img src=\""+imagenObligatorio+"\" alt=\"Campo Obligatorio\"></td>");
                       out.println("</tr>");
                    }
                    out.println("</table>");
%>
          </td>
        </tr>
        
        <tr> 
          <td class="subtitulotablas" colspan="4">Auditor&iacute;a de Tiempos:</td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right">Fase Actual:</div>
          </td>
          <td class="textosrespuesta" width="18%"> 
            <input type="text" name="fase" size="30" value="<%= registro.getFase() %>" class="completar2">
          </td>
          <td class="textotablas" width="21%"> 
            <div align="right">Estado Actual:</div>
          </td>
          <td class="textosrespuesta" width="47%"> 
            <input type="text" name="estado" size="50" value="<%= registro.getEstado() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right"> Fecha Env&iacute;o:</div>
          </td>
          <td class="textotablas" width="18%"> 
            <input type="text" name="fechaEnvio" size="30" value="<%= (registro.getEjecucionSolicitud().getFechaEnvio().getTime()==0 ? "No ha ocurrido" : registro.getEjecucionSolicitud().getFechaEnvio().toString()) %>" class="completar2">
          </td>
          <td class="textotablas" width="21%"> 
            <div align="right">Fecha Despacho:</div>
          </td>
          <td class="textosrespuesta" width="47%"> 
            <input type="text" name="fechaDespacho" size="30" value="<%= registro.getEjecucionSolicitud().getFechaDespacho().getTime()==0?"No ha ocurrido":registro.getEjecucionSolicitud().getFechaDespacho().toString() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td width="14%" class="textotablas"> 
            <div align="right"> Fecha Inicio:</div>
          </td>
          <td class="textotablas" width="18%"> 
            <input type="text" name="fechaInicio" size="30" value="<%= registro.getEjecucionSolicitud().getFechaInicio().getTime()==0?"No ha ocurrido":registro.getEjecucionSolicitud().getFechaInicio().toString() %>" class="completar2">
          </td>
          <td class="textotablas" width="21%"> 
            <div align="right">Fecha Finalizacion:</div>
          </td>
          <td class="textosrespuesta" width="47%"> 
            <input type="text" name="fechaFinalizacion" size="30" value="<%= registro.getEjecucionSolicitud().getFechaFin().getTime()==0?"No ha ocurrido":registro.getEjecucionSolicitud().getFechaFin().toString() %>" class="completar2">
          </td>
        </tr>
        <tr> 
          <td colspan="4" class="subtitulotablas">Programacion</td>
        </tr>
        <tr> 
          <td class="textotablas">Tipo Programacion:</td>
          <td class="textotablas">
            <input type="text" name="tipoProgramacion" value="<%= registro.getEjecucionSolicitud().getTipoProgramacionStr() %>" size="30" class="completar2">
          </td>
          <td class="textotablas">&nbsp;</td>
          <td class="textotablas">&nbsp;</td>
        </tr>
        <tr> 
          <td class="textotablas">Tipo Intervalo:</td>
          <td class="textotablas">
            <input type="text" name="tipoIntervalo" size="30" class="completar2" value="<%= registro.getEjecucionSolicitud().getTipoIntervaloEjecucionStr() %>">
          </td>
          <td class="textotablas">Intervalo:</td>
          <td class="textotablas">
            <input type="text" name="intervalo" size="15" class="completar2" value="<%= registro.getEjecucionSolicitud().getIntervaloEjecucion()  %>">
          </td>
        </tr>
        <tr> 
          <td class="textotablas">Fecha Programada Inicio:</td>
          <td class="textotablas">
            <input type="text" name="fechaProgramadaInicio" size="30" class="completar2" value="<%= registro.getEjecucionSolicitud().getFechaProgramadaInicio()==null? "No establecida" : registro.getEjecucionSolicitud().getFechaProgramadaInicio().toString()  %>">
          </td>
          <td class="textotablas">Fecha Programada Fin:</td>
          <td class="textotablas">
            <input type="text" name="fechaProgramadaFin" size="30" class="completar2" value="<%= registro.getEjecucionSolicitud().getFechaProgramadaFin()==null? "No establecida" : registro.getEjecucionSolicitud().getFechaProgramadaFin().toString() %>">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<% if (registro.getFase().equals(SolicitudConcurrente.FASE_EN_EJECUCION)){ %>
<input type="submit" name="btnProcesarSolicitud" value="Procesar Solicitud" class="botones">
</form>
</center>
<%}%>
</body>
</html>
