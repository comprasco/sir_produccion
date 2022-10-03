<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.*" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%
TextHelper textHelper = new TextHelper();
ListaElementoHelper listaHelper = new ListaElementoHelper();
List tipos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
Circulo circuloInhab=(Circulo)session.getAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO);
List elemLista=new ArrayList();
Iterator itTipos=tipos.iterator();
while(itTipos.hasNext()){
	Circulo circulo=(Circulo)itTipos.next();
	if (!circulo.getIdCirculo().equals(circuloInhab.getIdCirculo())){
	ElementoLista el=new ElementoLista(circulo.getIdCirculo(),circulo.getNombre() +"-"+circulo.getIdCirculo());
	elemLista.add(el);		
	}
}
listaHelper.setTipos(elemLista);


%>


<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionSubmit(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}

function mostrar(text) {
	if("ASOCIAR"==text){
		document.getElementById("ASOCIAR_CIRCULO").style.display='block'
		document.getElementById("CREAR_CIRCULO").style.display='none'
		document.getElementById("CREAR_CIRCULO_IMPUESTO").style.display='none'

	}else if ("CREAR"==text){
		document.getElementById("CREAR_CIRCULO").style.display='block'
		document.getElementById("CREAR_CIRCULO_IMPUESTO").style.display='block'
		document.getElementById("ASOCIAR_CIRCULO").style.display='none'
	}
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n C&iacute;rculos Registrales </td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Circulo Registral </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
                
              <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>" value="NINGUNO">
			<table width="100%" class="camposform">
				<tr>
					<td width="250"><input type="radio" name="<%=AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO%>" id="<%=AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO%>" value ="<%=AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO_ASOCIAR%>" onFocus="mostrar('ASOCIAR')" checked>Asociar a un Círculo</td>
					<td width="150"><input type="radio" name="<%=AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO%>" id="<%=AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO%>" value ="<%=AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO_CREAR%>" onFocus="mostrar('CREAR')" >Crear Nuevo Círculo</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			 <table width="100%" class="camposform"  name="ASOCIAR_CIRCULO" id="ASOCIAR_CIRCULO" >
				<tr>
					<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
					<td width="250" class="titulotbcentral">Asociar un Circulo Registral Actual</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
				<td>&nbsp;</td>
				<td>
					<%
					try 
					{
			           	listaHelper.setId(AWAdministracionForseti.CIRCULO_DESTINO_ASOCIAR);
            			listaHelper.setNombre(AWAdministracionForseti.CIRCULO_DESTINO_ASOCIAR);
			           	listaHelper.setCssClase("camposformtext");
           				listaHelper.render(request,out);
					}
					catch(HelperException re)
					{
						out.println("ERROR " + re.getMessage());
					}
			        %>
				</td>
				<td>&nbsp;</td>
				</tr>
			</table>
             <table width="100%" class="camposform" name="CREAR_CIRCULO" id="CREAR_CIRCULO" style="display:none">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="250" class="titulotbcentral" align="left">Agregar un Circulo Registral Nuevo</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>C&oacute;digo del Circulo </td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CCirculo.ID_CIRCULO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCirculo.ID_CIRCULO);
					textHelper.render(request,out);
					textHelper.setReadonly(false);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    	</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Nombre del Circulo </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCirculo.NOMBRE_CIRCULO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCirculo.NOMBRE_CIRCULO);
					textHelper.render(request,out);
					
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>NIT del Circulo </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CCirculo.NIT_CIRCULO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCirculo.NIT_CIRCULO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                </table>
                <table width="100%" class="camposform" name="CREAR_CIRCULO_IMPUESTO" id="CREAR_CIRCULO_IMPUESTO" style="display:none">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                    <td>Se Cobran Impuestos? </td>
                  </tr>
                  <tr>
                    <td>
                    <input name="<%= CCirculo.IMPUESTO_CIRCULO %>" type="radio" value="<%= CCirculo.IMPUESTO_CIRCULO_SI %>" >
                    </td>
                    <td>SI</td>
                  </tr>
                  <tr>
                    <td>
                    <input name="<%= CCirculo.IMPUESTO_CIRCULO %>" type="radio" value="<%= CCirculo.IMPUESTO_CIRCULO_NO %>" >
                    </td>
                    <td>NO</td>
                  </tr>
                </table>

				<table width="100%" class="camposform">
                  <tr>
					<td width="139">
                    	<a href="javascript:cambiarAccionSubmit('<%=AWAdministracionForseti.VOLVER_CIRCULO_INHABILITAR%>')"><img name="btn_volver" src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0" alt=""></a>
                    </td>
                    <td width="139">
                    	<a href="javascript:cambiarAccionSubmit('<%=AWAdministracionForseti.TERMINAR_CIRCULO_INHABILITAR%>')"><img name="btn_seguir" src="<%=request.getContextPath()%>/jsp/images/btn_seguir.gif" width="139" height="21" border="0" alt=""></a>
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
                
             </FORM>
                
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
