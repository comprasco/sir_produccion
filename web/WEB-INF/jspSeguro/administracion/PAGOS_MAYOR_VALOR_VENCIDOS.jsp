<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="java.util.*" %>



<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<%

TextHelper textHelper = new TextHelper();

TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();

	//Determinar si toca recagar la pagina.
    boolean recarga=true;
    Boolean rec = (Boolean)session.getAttribute(WebKeys.RECARGA_MAYOR_VALOR);
    if(rec!=null){
    	recarga=rec.booleanValue();
    }

%>

<script type="text/javascript">
function cargarTurnos(text) {
	document.DEVOLVERTURNOS.<%= WebKeys.ACCION %>.value = text;
	document.DEVOLVERTURNOS.submit();
}
</script>


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administración de Pagos de Mayor Valor devueltos por Vencimiento </td>
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
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Pagos de Mayor Valor Vencidos </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Listado de Turnos </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                  
                </table>
                
                
                <form action="administracionHermod.do" method="post" name="DEVOLVERTURNOS" id="DEVOLVERTURNOS">
                <input   type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR %>" value="<%= AWAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR %>">
                <table width="100%" class="camposform">
               
                </table>
               
                <table width="100%" class="camposform">
                
                <tr>
                  <% List listaTurnos = (List) session.getAttribute(WebKeys.LISTADO_VENCIDOS_MAYOR_VALOR);
                  List idTurnos = new ArrayList();
					if (listaTurnos==null){
						listaTurnos = new ArrayList(); 
					}
	              Iterator is = listaTurnos.iterator();
	              while(is.hasNext())
	              {
		              Turno turno =(Turno)is.next();
		              String temp=(String) turno.getIdWorkflow();
		              idTurnos.add(temp);
	              }
	              session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idTurnos);
                  %>
                   <tr>
	                  <td colspan=150>
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                          //tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	               		  tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>
					</td>
                </tr>
                
                
                		
                  </tr>
                </table>
                
                <table width="100%" class="camposform">
              		<tr>
                		<td allign="left" width="200">Número de copias a imprimir:</td>
						<td>
						<% try {
							textHelper = new TextHelper();
							textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
							textHelper.setCssClase("camposformtext");
							textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
							textHelper.setSize("5");
							textHelper.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}	
						%>
						</td>
               	</tr>
               </table>
               
                <hr class="linehorizontal">
              	<table width="100%" class="camposform">
                  <tr> 
                    <td width="20" height="24"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="150" align="center"><a href="javascript:validarEliminacion()"><img src="<%=request.getContextPath()%>/jsp/images/btn_confirmar.gif" width="140" height="21" border="0"></a></td>
    			    <td ><a href="javascript:terminar()"><img 
								src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" 
								width="139" height="21" border="0"></a></td>
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
		
		
		
		
	    <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
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


<script>
function validarEliminacion(nombre) {

    alert ('Va a devolver los turnos vencidos');
	if (confirm('Esta seguro que desea realizar la devolución de los turnos vencidos'))
	{
      document.DEVOLVERTURNOS.<%= WebKeys.ACCION %>.value = "DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR";
       document.DEVOLVERTURNOS.submit();
	}
}

function cambiarAccion(text) {
	document.DEVOLVERTURNOS.ACCION.value = text;
	document.DEVOLVERTURNOS.submit();
}

function terminar() {
	cambiarAccion('<%= AWAdministracionHermod.TERMINA %>');
}
</script>


<%if(recarga){%>
	<script>
		cargarTurnos ('<%=AWAdministracionHermod.OBTENER_VENCIDOS_PAGO_MAYOR_VALOR%>');
	</script>
<%}%>