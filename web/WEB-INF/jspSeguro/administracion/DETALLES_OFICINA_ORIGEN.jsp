<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.COficinaOrigen" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoOficina" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="gov.sir.core.negocio.modelo.OficinaCategoria" %>
<%@page import="gov.sir.core.negocio.modelo.OficinaOrigen" %>
<%@page import="gov.sir.core.negocio.modelo.CategoriaNotaria" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%


String vistaAnterior = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ANTERIOR);
session.setAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL,vistaAnterior);

List tipos = (List)session.getAttribute(AWAdministracionForseti.LISTA_OFICINAS_POR_VEREDA);
if(tipos == null){
	tipos = new ArrayList();
}

	String idOficina = request.getParameter(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
                
        String version = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_VERSION);
	Iterator itOficinas = tipos.iterator();
	OficinaOrigen oficina = new OficinaOrigen();
	while(itOficinas.hasNext()){
		OficinaOrigen temp = (OficinaOrigen)itOficinas.next();
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
		if(temp.getIdOficinaOrigen().equals(idOficina) && temp.getVersion().toString().equals(version)){
			oficina = temp;
		}
	}

%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cerrar() {
	window.close();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administración de Oficinas de Origen</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Detalles de Oficina Origen </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_tipo_oficina.gif" width="16" height="21"></td>
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
                    <td class="bgnsub">Oficina Origen </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_tipo_oficina.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>

                
      <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
     <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN %>" value="<%= AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN %>">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Tipos de Oficina </td>
                    <td class="campositem"><%=oficina!=null&&oficina.getTipoOficina()!=null?oficina.getTipoOficina().getNombre():""%>                      
                    </td>
                  </tr>
				  </table>
				  <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Departamento</td>
                    <td class="campositem"><%=oficina.getVereda().getMunicipio().getDepartamento().getNombre()%>
                   </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Municipio</td>
                    <td class="campositem"><%=oficina.getVereda().getMunicipio().getNombre()%>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Vereda</td>
                    <td class="campositem"><%=oficina.getVereda().getNombre() != null ? oficina.getVereda().getNombre():"&nbsp;"%>
                    </td>
                  </tr>
                </table>
                
                <%if(oficina.getTipoOficina().getIdTipoOficina().equals(CTipoOficina.TIPO_OFICINA_NOTARIA)){%>
				  <table width="100%" class="camposform" name="DATOS_NOTARIA" id="DATOS_NOTARIA" >
				  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Datos de la Notar&iacute;a</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td width="180">Direcci&oacute;n</td>
                    <td class="campositem"><%= oficina.getDireccion() != null ?oficina.getDireccion():"&nbsp;"%>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Tel&eacute;fono</td>
                    <td class="campositem"><%=oficina.getTelefono() != null ?oficina.getTelefono():"&nbsp;"%>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Fax</td>
                    <td class="campositem"> <%=oficina.getFax() != null ?oficina.getFax():"&nbsp;" %>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Correo Electronico</td>
                    <td class="campositem">
					<%=oficina.getEmail() != null ?oficina.getEmail():"&nbsp;"%>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Funcionario Autorizado</td>
                    <td class="campositem"><%=oficina.getEncargado() != null ?oficina.getEncargado():"&nbsp;"%>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>&nbsp;</td>
                    <td>Categoría de la Notaría</td>
                    <%CategoriaNotaria catNotaria = oficina.getCategoriaNotaria();
                    String nombreCategoriaNotaria ="&nbsp;";
                    if (catNotaria !=null)
                    {
                        nombreCategoriaNotaria = catNotaria.getNombre();
                     }%>
                    <td class="campositem"><%=nombreCategoriaNotaria%>
                    </td>
                  </tr>
                  
                  
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  
                  
                  
                   <tr>
                    <td>&nbsp;</td>
                    <td>Categor&iacute;as Reparto Notarial</td>
                    </tr>
					 <%
					 Iterator itCate = oficina.getCategorias().iterator();
					 while(itCate.hasNext()){
					 	OficinaCategoria cate = (OficinaCategoria)itCate.next();
						try{ 
							out.write("<tr>");
		                    out.write("<td>&nbsp;</td>");
							out.write("<td  class=\"campositem\" >&nbsp;");
							out.write(""+cate.getCategoria().getNombre()+"");
							out.write("</td>");
							out.write("</tr>");
						}catch(Exception re){
							out.println("ERROR " + re.getMessage());
						}
					 }
					
					 %>
					 	  
                  
                </table>
                
                <%}%>
                
                <hr class="linehorizontal">
                

                
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">N&uacute;mero</td>
                    <td class="campositem"><%=oficina.getNumero() != null ?oficina.getNumero():"&nbsp;"%>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td width="180">Nombre Oficina </td>
                    <td class="campositem" ><%=oficina.getNombre() != null ?oficina.getNombre():"&nbsp;"%>
                    </td>
                  </tr>
                <%--/*
                    *  @author Carlos Torres
                    *  @chage   se agrega validacion de version diferente
                    *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                    */
                --%>
                  <tr>
                    <td>&nbsp;</td>
                    <td width="180">Version </td>
                    <td class="campositem" ><%=oficina.getVersion() != null ?oficina.getVersion().toString():"&nbsp;"%>
                    </td>
                  </tr>
                  
                </table>
                <hr class="linehorizontal">
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td>
                    	<a href="javascript:cerrar()"><img src="<%=request.getContextPath()%>/jsp/images/btn_cerrar_ventana.gif" width="151" height="21" border="0"></a>                  
                   	</td>
                    <td>&nbsp;</td>
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
