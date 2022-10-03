<%@page import="gov.sir.core.web.acciones.comun.AWVerTurno" %>
<%@page import="gov.sir.core.web.WebKeys" %>


<SCRIPT>

function regresar(){
	document.REGRESAR.ACCION.value='<%=AWVerTurno.REGRESAR%>';
	document.REGRESAR.submit();
}

</SCRIPT>



                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Cuando termine de ver los detalles del turno haga click en Volver. </td>
                  </tr>
                </table>

                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <form action="verturno.do" method="POST" name="REGRESAR" id="REGRESAR">
                    <input  type="hidden" name="<%= WebKeys.ACCION %>" id=" " value=" ">
                    <td>

                     <td ><a href='javascript:regresar();'><img name="imageField" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif"   width="139" height="21" border="0" ></a></td>

                    </td>
                    </FORM>
                  </tr>
                </table>

