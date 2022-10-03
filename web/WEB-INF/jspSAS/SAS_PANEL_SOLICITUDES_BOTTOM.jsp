<link rel="stylesheet" href="/jsp/auriga/auriga.css" type="text/css">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td class="Boton"><ul>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li>
          <input type="button" name="btnCancelar" value="Cancelar Solicitud" onClick="cancelar()">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_nuevo.gif" width="27" height="22"></li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li>
          <input type="submit" name="btnDetalles" value="Ver Detalles" onClick="verDetalles()">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_buscar.gif" width="27" height="22"></li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li>
          <input type="submit" name="btnResultado" value="Ver Resultado" onClick="verResultado()">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_nuevo.gif" width="27" height="22"></li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_inicio.gif" width="16" height="22"></li>
        <li>
          <input type="button" name="btnLogEjecucion" value="Ver Log Ejecucion" onClick="verLogEjecucion()">
        </li>
        <li><img src="<%= request.getContextPath() %>/jsp/auriga/images/boton_nuevo.gif" width="27" height="22"></li>
      </ul></td>
  </tr>
</table>
