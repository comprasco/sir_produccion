<%@page import="gov.sir.core.web.acciones.comun.AWTurno"%>
<%@page import="gov.sir.core.negocio.modelo.Fase"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="org.auriga.core.web.*, gov.sir.core.web.*, gov.sir.core.web.helpers.comun.TurnosHelper, gov.sir.core.web.helpers.comun.TextHelper" %>
<jsp:directive.page import="gov.sir.core.negocio.modelo.util.ListadoPorRangosUtil"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.constantes.CListadosPorRangos"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.constantes.CProceso"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.util.Calendar"/>
<%@ taglib uri="/is21paginador" prefix="is21paginador"%>
<%  
    TurnosHelper turnosHelper = new TurnosHelper();
    String mensaje = (String)request.getSession().getAttribute(WebKeys.REPARTO_NOTARIAL_EN_HORARIO);
    /**
     * @author      :   Carlos Torres
     * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
     */
    session.setAttribute("id_fase", ((Fase)session.getAttribute("FASE")).getID());
    String idFase = ((Fase)session.getAttribute("FASE")).getID();
    
    Integer cantTurnos = (Integer) session.getAttribute("TRAMS_TURNOS");    
    String path= "../comun/HEADER_MENSAJE_ALERTA.jsp?idTipoMensaje=2&turnosTrams="+cantTurnos+"";
    
    boolean tieneAlerta = false;    
    if(session.getAttribute("GENERAR_ALERTA_SIN_CAMBIOS")!=null){
       tieneAlerta = true;
    }
%>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<%if(tieneAlerta){ %>
<jsp:include page="<%=path%>" flush="true" />

<% 
   session.removeAttribute("GENERAR_ALERTA_SIN_CAMBIOS"); 
   session.removeAttribute("TRAMS_TURNOS");
}
%>
<SCRIPT TYPE="text/javascript">
<!--
function submitenter(myfield,e) 
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   document.Turnos.TURNO_INDIVIDUAL.value = document.Turnos.TURNO_INDIVIDUAL2.value; 
   cambiarAccion('CONTINUAR_INDIVIDUAL');
   document.Turnos.submit();
   return false;
   }
else
   return true;
}
//-->
</SCRIPT>
<script type="text/javascript">
    
	function deshabilitarBoton() {
		document.getElementById('imageField_id').width=0;
	}
	function deshabilitarBotonRango() {
		document.getElementById('imageField_id_rango').width=0;
	}
        function seleccionarPrimerTurno(idPrimerTurno)
        {
            var radiosTurno = document.getElementById('<%=gov.sir.core.web.acciones.comun.AWTurno.ID_TURNO%>2').form.<%=gov.sir.core.web.acciones.comun.AWTurno.ID_TURNO%>2;
            for (var i= 0; i < radiosTurno.length; i++)
            {
                if (radiosTurno[i].value == idPrimerTurno)
                {
                    radiosTurno[i].checked = true;
                }
            }
            document.getElementById('<%= gov.sir.core.web.acciones.comun.AWTurno.TURNO_INDIVIDUAL %>').value = idPrimerTurno;
            document.getElementById('imageField_id').focus();
            
        }
    //JALCAZAR 01/sept/2009 funcion encargada de guardar cada uno de los turnos seleccionados por el usuario
    //en diferentes paginas de turnos disponibles para entrega
    function TurnosSeleccion(value)
    {
        var entra = false;
        var primer = false;
        var datos = document.getElementById('turnosentrega').value.split(',');
        if (datos != "")
        {
           for (i=0;i<datos.length;i++)
           {
               if (datos[i] == value)
               {
                   entra = true;
               }
           }
           if (entra == true)
           {
               for (i=0;i<datos.length;i++)
                   {
                      if (datos[i] != value)
                      {
                         if (primer == false)
                         {
                            document.getElementById('turnosentrega').value = datos[i];
                            primer = true;
                         }
                         else
                         {
                            document.getElementById('turnosentrega').value = document.getElementById('turnosentrega').value +','+datos[i];
                         }
                           /**
                            * @author      :   Carlos Torres
                            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                            */
                      }else if(datos.length == 1){
                          document.getElementById('turnosentrega').value = "";
                      }
                   }
           }
           else 
           {
              document.getElementById('turnosentrega').value = document.getElementById('turnosentrega').value +','+value;
           }

        }
        else
        {
           document.getElementById('turnosentrega').value = value;
        }
        

    }
    //JALCAZAR 01/sept/2009 funcion encargada de la confirmacion por parte del usuario del proceso
    function Confirmation()
    {
        if (document.getElementById('turnosentrega').value != "")
        {
        var datos = document.getElementById('turnosentrega').value.split(',');
         /**
            * @author      :   Carlos Torres
            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
            */
        var fase = "<%=idFase%>";
        var mensaje = "";
        if(fase!="REG_ENTREGA" && fase!="COS_ENTREGAR_ASOCIADOS" && fase!="REG_ENTREGA_EXTERNO"){
             mensaje = "El numero de turnos para entregar es: " + datos.length + "\nCLICK en ACEPTAR si esta seguro de la operacíon, de lo contrario CLICK en CANCELAR";
        }else{
            mensaje = "El numero de relaciones para confirmar es: " + datos.length + "\nCLICK en ACEPTAR si esta seguro de la operacíon, de lo contrario CLICK en CANCELAR";
        }
        var answer = confirm(mensaje);

        if(answer == true)
        {
            deshabilitarBoton();
            Turnosentrega();
        }
        else
        {
            /**
            * @author      :   Carlos Torres
            * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
            */
            if(fase!="REG_ENTREGA" && fase!="COS_ENTREGAR_ASOCIADOS" && fase!="REG_ENTREGA_EXTERNO"){
                mensaje = "Por Favor: Verifique de nuevo el(los) TURNOS seleccionados";
            }else{
                mensaje ="Por Favor: Verifique de nuevo la(s) RELACIONES seleccionadas";
            }
                alert(mensaje);
        }
        }
        else
        {
           document.Turnos.submit();
        }

    }

            //JALCAZAR 01/sept/2009 funcion encargada de seleccionar el proceso a ejecutar dependiendo del numero de turnos:
            //Si el usuario selecciona un solo turno el proceso es CONTINUAR_INDIVIDUAL si selecciona mas de un turno ENTREGAR_REGISTRO_MULT
            function Turnosentrega()
            {
                /**
                     * @author      :   Carlos Torres
                     * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                     */     
                var fase = "<%=idFase%>";
                var datos = document.getElementById('turnosentrega').value.split(',')
                if(fase!="REG_ENTREGA" && fase!="COS_ENTREGAR_ASOCIADOS" && fase!="REG_ENTREGA_EXTERNO"){
                    if(datos.length > 1)
                    {
                        
                        document.Turnos.action = "calificacion.do"
                        cambiarAccion('ENTREGAR_REGISTRO_MULT');
                    }else{
                        
                        document.getElementById('<%= gov.sir.core.web.acciones.comun.AWTurno.TURNO_INDIVIDUAL%>').value = document.getElementById('turnosentrega').value;
                        document.Turnos.action = "turno.do";
                        cambiarAccion('CONTINUAR_INDIVIDUAL');;
                    }
            }else{  
                    document.Turnos.action = "calificacion.do";
                    if(datos.length > 0)
                    {
                        cambiarAccion('ENTREGAR_REGISTRO_MULT');
            }else{
                        alert("Debe seleccionar almenos una relacion");
            }
            }
            }

             function cambiarAccion(text) {
       document.Turnos.ACCION.value = text;
       document.Turnos.submit();
    }
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
   
    <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn004.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="9" height="10"></td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">TURNO</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_turno.gif" width="16" height="21"></td>
                
            </tr>
        </table></td>
        <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn004.gif">&nbsp;</td>
    <td><img name="tabla_central_r1_c9" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c9.gif" width="9" height="29" border="0" alt=""></td>
    </tr>
    <tr> 
    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" class="tdtablacentral">
    <% 
    	if (session.getAttribute(gov.sir.core.web.WebKeys.LISTA_TURNOS_PARCIAL) != null)
        {
		com.is21.core.web.paginador.IListaPorRangos listaPorRangos = (com.is21.core.web.paginador.IListaPorRangos)session.getAttribute(gov.sir.core.web.WebKeys.LISTA_TURNOS_PARCIAL);
	%>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tdtablacentral" >
                <tr>
                    <td>
                        <table border="0" cellpadding="0" cellspacing="0" class="tdtablacentral">
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td width="10">&nbsp;</td>
                            </tr>
			</table>
                    </td>
                </tr>
                <tr>
                <td>
 
  <% if(listaPorRangos.getMaximo()!=0){   %>       
    <is21paginador:IteradorPaginador
                varId="turno" 
                listaPorRangos="<%= listaPorRangos %>" 
                mensajeNoDatos="En este momento no tiene turnos disponibles"
                tamanio="20"
            >
            <is21paginador:EncabezadoIteradorPaginador>
                 <!--  /**
                         * @author      :   Carlos Torres
                         * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                         */ -->
                <%
                       String idFase = (String)session.getAttribute("id_fase");
                %>
                <% if(("REG_ENTREGA".equals(idFase) || "COS_ENTREGAR_ASOCIADOS".equals(idFase) || "REG_ENTREGA_EXTERNO".equals(idFase))){   %>
                <table id="tabla-relaciones" width="100%" class="camposform">
                    <tr>
                        <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_radiobutton.gif" width="20" height="15" /></td>
                        <td height="17" class="titulotbcentral">Seleccione una Relacion</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td width="20"></td>
                        <td height="17" class="titulotbcentral">RELACION</td>
                        <td height="17" class="titulotbcentral">TURNO</td>
                    </tr>
                 <% }else if("REG_FINALIZADO".equals(idFase)){   %>
                        <table width="100%" class="camposform" >
                            <tr>
                            <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_radiobutton.gif" width="20" height="15" /></td>
                            <td height="17" class="titulotbcentral">Seleccione un Turno</td>
                        </tr>
                 <%} %>
            </is21paginador:EncabezadoIteradorPaginador>
            <is21paginador:CuerpoIteradorPaginador>
                 <!--  /**
                         * @author      :   Carlos Torres
                         * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                         */ -->
                <%
                       String idFase = (String)session.getAttribute("id_fase");
                %>
                 <% if(("REG_ENTREGA".equals(idFase) || "COS_ENTREGAR_ASOCIADOS".equals(idFase) || "REG_ENTREGA_EXTERNO".equals(idFase))){   %>
                <tr>
                    <td><label>
                    <!--JALCAZAR Cambio del tipo de input a type="checkbox" -->
                    <% if(!turno.getIdRelacionActual().equals("")){%>
                        <input type="checkbox" name="<%= gov.sir.core.web.acciones.comun.AWTurno.ID_TURNO %>2" value="<%= turno.getIdRelacionActual() %>" onClick="javascript: TurnosSeleccion(this.value);" />
                    <%}%>
                    </label></td>
                    <td>
                        <%=turno.getIdRelacionActual() %>
                    </td>
                    <td>
                        <%=turno.getIdWorkflow()%>
                    </td>
                </tr>
                <% }else if("REG_FINALIZADO".equals(idFase)){   %>
                    <tr>
                        <td><label>
                        <!--JALCAZAR Cambio del tipo de input a type="checkbox" -->
                        <input type="checkbox" name="<%= gov.sir.core.web.acciones.comun.AWTurno.ID_TURNO %>2" value="<%= turno.getIdWorkflow() %>" onClick="javascript: TurnosSeleccion(this.value);" />
                        </label></td>
                        <td>
                            <%=turno.getIdWorkflow()%>
                        </td>
                    </tr>
                 <%} %>
                 
                <%
                if(numeroElementoAbs == 1)
                {
                    pageContext.setAttribute("PRIMER_TURNO_LISTADO",turno.getIdWorkflow(),javax.servlet.jsp.PageContext.SESSION_SCOPE);
                }
                %>
            </is21paginador:CuerpoIteradorPaginador>
            <is21paginador:PieIteradorPaginador>
                </table>
            </is21paginador:PieIteradorPaginador>
        </is21paginador:IteradorPaginador>
       <% }else{ %>
       		<table width="100%" class="camposform" >
                    <tr>
                        <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_radiobutton.gif" width="20" height="15" /></td>
                        <td height="17" class="titulotbcentral">Seleccione un Turno</td>
                    </tr>
             </table>           
       <% } %>
                </td>
            </tr>
            <tr>
            <td><table cellpadding="0" cellspacing="0" width="50px"><tr>
            <td width="50px"><div style="width:50px;"><is21paginador:ControlPaginador mostrarIrA="true" tamanio="4" onChange="document.getElementById('imageField_id').focus();"/></div></td>
            </tr></table></td>
            </tr>
            <tr>
		<td align="center"><hr class="camposform"></td>
            </tr>
            <tr>
		<td align="center">
                    <form name="Turnos" method="post" action="turno.do" >
                        <input type="hidden" name="<%= gov.sir.core.web.WebKeys.ACCION %>" id="<%= gov.sir.core.web.WebKeys.ACCION %>" value="<%= gov.sir.core.web.acciones.comun.AWTurno.CONTINUAR_INDIVIDUAL %>">
                        <input type="hidden" name="<%= gov.sir.core.web.acciones.comun.AWTurno.TURNO_INDIVIDUAL %>" id="<%= gov.sir.core.web.acciones.comun.AWTurno.TURNO_INDIVIDUAL %>" value="">
                        <!--JALCAZAR agrega type="hidden" name="turnosentrega" para guardar el(los) turno(s) ha entregar -->
                        <input type="hidden" name="turnosentrega" id="turnosentrega" value="<%=(request.getParameter("turnosentrega") !=null ? request.getParameter("turnosentrega") : "")%>">
                        <!--JALCAZAR cambio del tipo input de la visualizacion del botton por img para evitar el efecto submit -->
                        <img src="<%= request.getContextPath() %>/jsp/images/btn_aceptar.gif" onclick="javascript: Confirmation();" width="139" height="21" border="0" name="imageField" id="imageField_id"/>
                        <hr class="camposform">

                        <%
                        /**
                         * @author      :   Carlos Torres
                         * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                         */
                        if((!"REG_ENTREGA".equals(idFase) && !"COS_ENTREGAR_ASOCIADOS".equals(idFase) && !"REG_ENTREGA_EXTERNO".equals(idFase))){%>
                        <table width="100%" class="camposform">

                            <tr>
                                <td width="20"><img src="<%= request.getContextPath() %>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>				
                                <td  class="titulotbcentral" colspan="2">Busqueda Turno</td>
                            </tr>	
                            <%
                            boolean mostrarNormal=false;
                            if(listaPorRangos!=null){
                                    System.out.println("********** entro a lista por rangos JSP ********");
                                    ListadoPorRangosUtil lista=(ListadoPorRangosUtil)listaPorRangos;
                                    if(lista!=null && lista.getIdListado().equals(CListadosPorRangos.LISTADO_TURNOS_EJECUCION)){
                                            Object[] objetos=lista.getParametros();
                                            String idCirculo=(String)objetos[0];
                                            String idProceso=CProceso.PROCESO_REGISTRO;
                            %>
                            <tr>
                                <td>
                                    <%
                                    try {
                                            System.out.println("********** entro a try/catch ********");
                                            Calendar calendar=Calendar.getInstance();
                                            int anio=calendar.get(Calendar.YEAR);
                                            TextHelper textHelper= new TextHelper();	
                                            textHelper.setNombre("ANIO");
                                            textHelper.setCssClase("camposformtext");
                                            textHelper.setId("ANIO");
                                            textHelper.setSize("5%");
                                            request.getSession().setAttribute("ANIO",Integer.toString(anio));
                                            textHelper.render(request,out);
                                    }
                                    catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                    } %>
                                </td>
                                <td><%=idCirculo+"-"+idProceso%></td>
                                <td>
                                    <%
                                    try {
                                            System.out.println("********** entro a id_turno********");
                                            TextHelper textHelper= new TextHelper();	
                                            textHelper.setNombre("ID_TURNO");
                                            textHelper.setCssClase("camposformtext");
                                            textHelper.setId("ID_TURNO");
                                            textHelper.setSize("5%");
                                            textHelper.render(request,out);
                                    }
                                    catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                    } %>
                                </td>
                            </tr>
                            <%
                    }else{
                            mostrarNormal=true;
                    }
            }else{
                    mostrarNormal=true;
            }
            if(mostrarNormal){
                            %>
                            <tr>
                                <td align="center" colspan="2">
                                    <%
                                    try {
                                        System.out.println("********** entro a turno_individual2********");
                                            TextHelper textHelper= new TextHelper();	
                                            textHelper.setNombre("TURNO_INDIVIDUAL2");
                                            textHelper.setCssClase("camposformtext");
                                            textHelper.setId("TURNO_INDIVIDUAL2");
                                            textHelper.setSize("20");
                                            textHelper.setFuncion("onKeyPress=\"javascript: return submitenter(this,event)\"");
                                            textHelper.render(request,out);
                                    }
                                    catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());
                                    }
                                    %>
                                </td>
                            </tr>

                            <%} %>

                            <tr>
                                <td align="center" colspan="2" ><input name="BUSQUEDA_TURNO" id="BUSQUEDA_TURNO" type="image" onClick="javascript: document.Turnos.TURNO_INDIVIDUAL.value = document.Turnos.TURNO_INDIVIDUAL2.value; cambiarAccion('CONTINUAR_INDIVIDUAL');" src="<%= request.getContextPath() %>/jsp/images/btn_consultar.gif" width="139" height="21" border="0"></td>
                            </tr>				
                        </table>
                        <%}%>
                    </form>
                </td>
            </tr>
            <tr>
                <td align="center"><hr class="camposform"></td>
            </tr>
        </table>
         
    <% } else { %>    
    	<% try {        
                        if(mensaje == null){
                            turnosHelper.setEnHorarioRepartoNotarial(true);
                        }else{
                            turnosHelper.setEnHorarioRepartoNotarial(false);
                        }
			turnosHelper.render(request,out);
		}
			catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}
	} %>
    </td>
    <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
    <td class="tdtablaanexa02" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tablas">
                <tr>
                    <td valign="middle"><img src="<%= request.getContextPath()%>/jsp/images/ind_flecha.gif" width="20" height="15"></td>
                    <td valign="middle"><a href="#" class="links">Condiciones 
                    Generales de Uso | Pol&iacute;tica de Privacidad</a> </td>
                </tr>
            </table></td>
        </tr>
        <tr>	
            <td><img src="<%= request.getContextPath()%>/jsp/images/img_turno.jpg" width="450" height="150"></td>
        </tr>
    </table>
            
    </td>
    <td width="9" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn007.gif">&nbsp;</td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn005.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
    <td><img name="tabla_central_r3_c9" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c9.gif" width="9" height="6" border="0" alt=""></td>
    </tr>
</table>
<!--
</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
-->
<script>
<%
	Object error = request.getSession().getAttribute(WebKeys.REPARTO_NOTARIAL_FAILED);
	request.getSession().removeAttribute(WebKeys.REPARTO_NOTARIAL_FAILED);
	if(error != null )
	{
%>	
		alert('El reparto NO se puede ejecutar pues el horario actual no es un horario vï¿½lido de reparto notarial!');
<%	
	}
%>
</script>
