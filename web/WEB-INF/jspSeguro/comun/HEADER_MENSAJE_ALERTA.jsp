<%@page import ="java.util.*" %>
<%
	String idTipoMensaje = request.getParameter("idTipoMensaje")!=null?request.getParameter("idTipoMensaje"):"";
        String turnosTrams= request.getParameter("turnosTrams")!=null?request.getParameter("turnosTrams"):"";
       	
        String mensaje = "";
	switch(new Integer(idTipoMensaje).intValue()){
		case 1:
                    mensaje = "Hay matrículas sin corregir, presione 'Ver Alertas' para ver las matrículas faltantes";
                    break;

                case 2:
                    mensaje = "Usted tiene turnos a punto de vencer en la fase seleccionada: "+ new Integer(turnosTrams).intValue() +" Turnos";
                    break;
	}
 %>
<center>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
               <td><table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr> 
              <td><img src="<%=request.getContextPath()%>/jsp/images/mensaje_animated.gif" width="10" height="14"></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_inicio.gif" width="10" height="14"></td>
              <td background="<%=request.getContextPath()%>/jsp/images/tit_turno_bgn.gif" class="tit_turno">Alerta: <%=mensaje%></td>
              <td><img src="<%=request.getContextPath()%>/jsp/images/tit_turno_fin.gif" width="10" height="14"></td>
            </tr>
     </table>
</center>
