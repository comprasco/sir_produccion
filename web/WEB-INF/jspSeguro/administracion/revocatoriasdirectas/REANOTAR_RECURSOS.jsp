<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWRecursosRevocatorias" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>

<%
	TextHelper textHelper = new TextHelper();
	List turnos = (List)session.getAttribute(WebKeys.LISTA_TURNOS);
	if(turnos == null){
		turnos = new ArrayList();
	}
    session.setAttribute(WebKeys.VISTA_VOLVER,request.getSession().getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL));	
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.action = 'recursosRevocatorias.do';
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();		
}
function verTurno(text) {
	document.BUSCAR.action = 'trasladoTurno.do';
	document.BUSCAR.<%= WebKeys.ACCION %>.value = '<%=gov.sir.core.web.acciones.administracion.AWTrasladoTurno.CONSULTAR_TURNO%>';
	document.getElementById(text).checked=true;
	document.BUSCAR.submit();		
}
function send(){
   document.forma.submit();
}
</script>
<%
List turnosAReanotar =(List)session.getAttribute(WebKeys.LISTA_TURNOS_REANOTAR);
List calificadores =(List)session.getAttribute(WebKeys.LISTA_CALIFICADORES);
/*
* @author      : Julio Alcázar Rivas
* @change      : nueva variable para manejar en el evento los usuarios con  el rol testamentos
* Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
*/
List caliTestamentos =(List)session.getAttribute(WebKeys.LISTA_CAL_TESTAMENTOS);
if (calificadores==null){
%>
	<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	    <form action="recursosRevocatorias.do" method="POST" name="forma"  id="forma">
	  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS%>" value="<%=AWRecursosRevocatorias.CONSULTAR_TURNOS_BLOQUEADOS%>">
		</form>
<%
}
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Restitución de turnos</td>
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
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turnos para Restituir o Rechazar</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
        <form action="recursosRevocatorias.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWRecursosRevocatorias.BLOQUEAR_RECURSOS%>" value="<%=  AWRecursosRevocatorias.BLOQUEAR_RECURSOS%>">
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Lista de turnos</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                boolean primerTurno = true;
                if(turnosAReanotar!=null && !turnosAReanotar.isEmpty()){%>
                <table width="100%" class="camposform">
                  <%
                  java.util.Iterator it = turnosAReanotar.iterator();
                  while(it.hasNext()){
	                  Turno t = (Turno)it.next();
                  %>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                   <td width="50"><input type='radio' name='<%=CTurno.ID_TURNO%>' id='<%=t.getIdWorkflow()%>' value='<%=t.getIdWorkflow()%>' <%=primerTurno?"checked":""%> ></td>
                    <td >                    	
                    	<%=t.getIdWorkflow()%>
                    </td>
                   <td width="50"><a href="javascript:verTurno('<%=t.getIdWorkflow()%>')"><img src='<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif' border='0'></a></td>                    
                  </tr>                  
                  <%
                  primerTurno = false;
                  }
                  %>
                </table>
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                    	
                    	No existen turnos para reanotar.
                    </td>
                  </tr>
                </table>                
                <%}%>
                
                <br>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Lista de calificadores</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                <%
                /*
                * @author      : Julio Alcázar Rivas
                * @change      : se elimino la variable primerCalificador para no checked ninguno de los elementos del radio
                * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                */
                if(calificadores!=null && !calificadores.isEmpty()){%>
                <table width="100%" class="camposform">
                  <%
                  java.util.Iterator it = calificadores.iterator();
                  while(it.hasNext()){
	                  org.auriga.core.modelo.transferObjects.Estacion e = (org.auriga.core.modelo.transferObjects.Estacion)it.next();
                  %>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                   <!--
                   * @author      : Julio Alcázar Rivas
                   * @change      : se elimino la variable primerCalificador para no checked ninguno de los elementos del radio
                   * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                  !-->
                  <td width="50"><input type='radio' name='<%=gov.sir.core.negocio.modelo.constantes.CEstacion.ESTACION_ID%>' value='<%=e.getEstacionId()%>' ></td>
                    <td >                    	
                    	<%=e.getEstacionId()%>
                    </td>
                  </tr>                  
                  <%
                  /*
                    * @author      : Julio Alcázar Rivas
                    * @change      : se elimino la variable primerCalificador para no checked ninguno de los elementos del radio
                    * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                    */
                  }
                  %>
                </table>
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >                    	
                    	No existen calificadores para asignar los turnos a reanotar.
                    </td>
                  </tr>
                </table>                
                <%}%>
                 <!--
                 * @author      : Julio Alcázar Rivas
                 * @change      : se creo una nueva tabla para colocar los usuarios que son calificadores de testamentos
                 * Caso Mantis  : 0007920: Acta - Requerimiento No 254 - RESTITUCION TURNOS DE TESTAMENTOS
                 !-->
                <br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Lista de calificadores de testamentos</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                <%
               
                if(caliTestamentos!=null && !caliTestamentos.isEmpty()){%>
                <table width="100%" class="camposform">
                  <%
                  java.util.Iterator it = caliTestamentos.iterator();
                  while(it.hasNext()){
	                  org.auriga.core.modelo.transferObjects.Estacion e = (org.auriga.core.modelo.transferObjects.Estacion)it.next();
                  %>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                   <td width="50"><input type='radio' name='EST_TESTAMENTO' value='<%=e.getEstacionId()%>'></td>
                    <td >
                    	<%=e.getEstacionId()%>
                    </td>
                  </tr>
                  <%
                  
                  }
                  %>
                </table>
                <%}else{%>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td >
                    	No existen calificadores de testamentos para asignar los turnos a reanotar.
                    </td>
                  </tr>
                </table>
                <%}%>
                                
                <br>
				<table width="100%" class="camposform">		              
		                <BR><hr class="linehorizontal"><BR>		                
		                <tr>
		                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		                    <td width="220"><b>Justificación:</b></td>                    
		                    <td>
		               		<% 
								try {
									gov.sir.core.web.helpers.comun.TextAreaHelper textAreaHelper = new TextAreaHelper();								
			    					textAreaHelper.setNombre(CTurno.DESCRIPCION);
			                  	  	textAreaHelper.setId(CTurno.DESCRIPCION);
		  	  		                textAreaHelper.setCols("80");
		  	  		                textAreaHelper.setRows("5");
			                  	  	textAreaHelper.setCssClase("camposformtext");
								  	textAreaHelper.render(request,out);
								} catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
							%>
		                    </td>
		                </tr>                  
		
		             </table>                
                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<a href="javascript:cambiarAccion('<%=  AWRecursosRevocatorias.REANOTAR_RECURSOS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_restituir.gif" width="180" height="21" border="0"></a>
                    </td>
                    <td width="155">
                    	<a href="javascript:cambiarAccion('<%=  AWRecursosRevocatorias.RECHAZAR_RECURSOS%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_rechazar.gif" width="150" height="21" border="0"></a>
                    </td>                    
                    <td>
                    	<a href="javascript:cambiarAccion('<%=  AWRecursosRevocatorias.VOLVER%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a>
                    </td>
                  </tr>
                </table>
            </form>    
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
  
  <tr> 
    <td>&nbsp;</td>
    <td><img name="tabla_gral_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
    <td><img name="tabla_gral_r3_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
    <td>&nbsp;</td>
  </tr>
</table>
