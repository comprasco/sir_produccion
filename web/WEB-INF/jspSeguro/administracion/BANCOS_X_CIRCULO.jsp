<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculoFestivo" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.web.acciones.comun.AWPago"%>
<%@page import="gov.sir.core.negocio.modelo.Banco"%>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CBanco"%>
<jsp:directive.page import="gov.sir.core.negocio.modelo.BancosXCirculo"/>
<jsp:directive.page import="gov.sir.core.negocio.modelo.constantes.CCirculo"/>

<%/**
 * @autor HGOMEZ 
 * @mantis 13407 
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
 * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos y 
 * departamentos asociados a los mismos.
 */%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>

<%

/**
* @autor HGOMEZ 
* @mantis 13407 
* @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
* @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
* de departamentos por circulo.
*/
List listaCirculoDepartamento = new Vector();
DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

int idCirculoInt = 0;
String nombreCirculoDepartamento = "";
String idCirculoString = "";

if(session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO)==null){
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	List elementos = new ArrayList();
	
        for(java.util.Iterator iter = circulos.iterator(); iter.hasNext(); ) {
            gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
            idCirculoString = circulo.getIdCirculo();
            if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                idCirculoInt = Integer.parseInt(idCirculoString);
                nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                if(nombreCirculoDepartamento != ""){
                    elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                }
            }
        }
	session.setAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO, elementos)	;
	}

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);

	
	
ServletContext context=session.getServletContext();
List bancos = null;
if(context!=null)
	bancos=(List)context.getAttribute(WebKeys.LISTA_BANCOS);
if (bancos == null) {
	bancos = new Vector();
}
ListaElementoHelper listaBancosHelper = new ListaElementoHelper();
listaBancosHelper.setCssClase("camposformtext");
listaBancosHelper.setId(AWPago.COD_BANCO);
listaBancosHelper.setNombre(AWPago.COD_BANCO);
Iterator itBancos = bancos.iterator();
List nBancos = new Vector();
while (itBancos.hasNext()) {
	Banco banco = (Banco) itBancos.next();
	nBancos.add(new ElementoLista(banco.getIdBanco(),
			banco.getIdBanco() + " - " + banco.getNombre()));
}
listaBancosHelper.setTipos(nBancos);

List bancosXCirculo=(List)session.getAttribute(WebKeys.LISTA_BANCOS_X_CIRCULO);

if(bancosXCirculo==null)
	bancosXCirculo=new Vector();


%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>

<script type="text/javascript">
function onChangeComboCirculos(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n de días festivos dentro de Círculos</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Administrac&oacute;n Bancos Por C&iacute;rculo</td>
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
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">C&iacute;rculo Registral / Banco </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
              
                
       <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=  AWAdministracionHermod.ADICIONA_BANCO_CIRCULO%>" value="<%= AWAdministracionHermod.ADICIONA_BANCO_CIRCULO %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&iacute;rculo Registral </td>
                    <td>
			  <% try {
                    circuloHelper.setNombre(CCirculoFestivo.ID_CIRCULO);
                  	circuloHelper.setCssClase("camposformtext");
                  	circuloHelper.setId(CCirculoFestivo.ID_CIRCULO);
                  	circuloHelper.setFuncion("onChange=\"onChangeComboCirculos('"+AWAdministracionHermod.SELECCIONA_BANCOS_X_CIRCULO+"')\"");
					circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Banco</td>
                    <td>
			  <% try {
					listaBancosHelper.render(request,out); 
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Activo </td>
                    <td>
			 			<input type="checkbox" name="<%= CBanco.PRINCIPAL_BANCO %>"  value="checkbox"    >
                    </td>
                  </tr>
                <tr>                  

                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    </td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.ADICIONA_BANCO_CIRCULO%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    	</td>
                  </tr>
                </table>
                </form>
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
          <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Listado</td>
                  <td>&nbsp;</td>

                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">Banco</td>
                  <td class="titulotbcentral">Principal</td>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral" align="center">Activar Principal</td>
                  <td class="titulotbcentral" align="center">Eliminar</td>
                  <td>&nbsp;</td>
                </tr>
       <% 
                int idFestivo =0;
                String nomBanco;
                for(Iterator iter = bancosXCirculo.iterator(); iter.hasNext();){
                	BancosXCirculo dato=(BancosXCirculo)iter.next();
                	nomBanco="";
                	if(dato!=null && dato.getBanco()!=null && dato.getBanco().getNombre()!=null)
                		nomBanco=dato.getBanco().getNombre();
                %>   
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%=nomBanco%></td>
                  <td class="campositem"><%=dato.getPrincipal()%></td>
                  
               <form action="administracionHermod.do" method="post" name="ELIMINARBANCO<%=idFestivo%>" id="ELIMINARBANCO<%=idFestivo%>">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWAdministracionHermod.ELIMINA_BANCO_CIRCULO%>" value="<%= AWAdministracionHermod.ELIMINA_BANCO_CIRCULO %>">
                	    <input  type="hidden" name="<%= CBanco.ID_BANCO %>" id="<%= dato.getIdBanco() %>" value="<%= dato.getIdBanco() %>">
                	    <input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= dato.getIdCirculo() %>" value="<%= dato.getIdCirculo() %>">
					<td>&nbsp;</td>
                  	<td align="center">
						<a href="javascript:validarPrincipal('<%=idFestivo%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_activar.gif" width="35" height="13" border="0"></a>
               	  	</td>
                  	<td align="center">
                  		<a href="javascript:validarEliminacion('<%=idFestivo%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
               	  	</td>               	  
               	  
   				</form>	
                </tr>
               <%
               idFestivo++; 
                 }
                 %>
            </table></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
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
    alert ('Va a eliminar un banco de un Círculo');
	if (confirm('Esta seguro que desea eliminar el banco del Círculo')){
      eval('document.ELIMINARBANCO' +nombre + '.submit()');
	}
}

function validarPrincipal(nombre) {
	  var formulario=document.getElementById('ELIMINARBANCO'+nombre);
	  formulario.ACCION.value='ACTIVAR_BANCO_PRINCIPAL';
      eval('document.ELIMINARBANCO' +nombre + '.submit()');
}
</script>
