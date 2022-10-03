<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionFenrir" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CUsuario" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="org.auriga.core.modelo.transferObjects.Rol" %>

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
	List roles = (List)session.getAttribute(WebKeys.LISTA_ROLES);
	Iterator itRoles = roles.iterator();

	boolean adminNal = false;
	
	while (itRoles.hasNext())
	{
		Rol rol = (Rol) itRoles.next();
		if (rol.getRolId().equals("SIR_ROL_ADMINISTRADOR_NACIONAL"))
		{
			adminNal = true;
			request.getSession().setAttribute(WebKeys.ROL_ADMINISTRADOR_NACIONAL, "true");
		}
	}
try{
	if (!adminNal)
	{
		Circulo circuloSes=(Circulo)session.getAttribute(WebKeys.CIRCULO);
		String idCirculoSes = circuloSes.getIdCirculo();
		request.getSession().setAttribute(AWAdministracionFenrir.ADMIN_PERFILES_PARAM_PCIRCULONOMBRE, idCirculoSes);
		request.getSession().setAttribute(WebKeys.ROL_ADMINISTRADOR_NACIONAL, "false");
	}
        }
catch(Exception e){
    System.out.print("Error : " + e.getMessage());
}

        List listaCirculoDepartamento = new Vector();
        DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
        listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

        int idCirculoInt = 0;
        String nombreCirculoDepartamento = "";
        String idCirculoString = "";
        
	List circulos = (List)application.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES);
	List elementos = new ArrayList();
        try{
        if(circulos != null){
            for (Iterator iter = circulos.iterator(); iter.hasNext();) {
                gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
                idCirculoString = circulo.getIdCirculo();
                if(idCirculoString.equals("SNR")){
                    elementos.add(new ElementoLista("SNR", "SNR"));
                }else{
                    if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                        idCirculoInt = Integer.parseInt(idCirculoString);
                        nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                        if(nombreCirculoDepartamento != ""){
                            elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                        }
                    }
                }
            }
        }
        }
catch(Exception e){
    System.out.print("Error : " + e.getMessage());
}

	ListaElementoHelper circuloHelper = new ListaElementoHelper();
	circuloHelper.setTipos(elementos);
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}


function cambiarAccionAndSend(text) {
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
      <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n de Relaciones de Usuarios</td>
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
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Circulo Registral:</td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Escoger circulo</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
            </table>
                
        <form action="administracionFenrir.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input type="hidden" name="<%= WebKeys.ACCION%>" id="NINGUNO" value="NINGUNO">
        <input type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%=CCirculo.ID_CIRCULO%>" value="">
        <%
		try 
		{
           	circuloHelper.setId( AWAdministracionFenrir.ADMIN_PERFILES_PARAM_PCIRCULONOMBRE );
            circuloHelper.setNombre( AWAdministracionFenrir.ADMIN_PERFILES_PARAM_PCIRCULONOMBRE );
           	circuloHelper.setCssClase("camposformtext");
           	circuloHelper.render(request,out);
		}
		catch(HelperException re)
		{
			out.println("ERROR " + re.getMessage());
		}
        %>
        <table width="100%" class="camposform">
          <tr>
            <td width="20">
            	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
           	</td>
            <td width="140">
            <input name="imageField" type="image" onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" width="139" height="21" border="0">
            </td>
            <td>
            <img onClick="cambiarAccionAndSend('<%=AWAdministracionFenrir.IR_ELEGIR_USUARIO_PERFILES%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0" style="cursor:hand">
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
<%if(!adminNal){ %>
	<script>cambiarAccionAndSend('IR_ELEGIR_USUARIO_PERFILES');</script>
<%}%>
