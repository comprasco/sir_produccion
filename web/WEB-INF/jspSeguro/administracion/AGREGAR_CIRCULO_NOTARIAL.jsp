<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CZonaNotarial" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculoNotarial" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CDepartamento" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CMunicipio" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CVereda" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.Departamento" %>
<%@page import="gov.sir.core.negocio.modelo.CirculoNotarial" %>
<%@page import="org.auriga.core.web.HelperException" %>

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
boolean editar=false;

TextHelper textHelper = new TextHelper();

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

if(session.getAttribute(AWAdministracionHermod.LISTA_CIRCULOS_ELEMENTO)==null){
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
	session.setAttribute(AWAdministracionHermod.LISTA_CIRCULOS_ELEMENTO, elementos);	
}

List listaCirculos = (List) session.getAttribute(AWAdministracionHermod.LISTA_CIRCULOS_ELEMENTO);
ListaElementoHelper circulosHelper = new ListaElementoHelper();
circulosHelper.setTipos(listaCirculos);

List tipos = (List)session.getAttribute(AWAdministracionHermod.LISTA_CIRCULOS_NOTARIALES);
if(tipos == null){
	tipos = new ArrayList();
}

TextHelper nombreHelper = new TextHelper();
TextHelper idHelper = new TextHelper();
CirculoNotarial circuloNotarial=(CirculoNotarial)session.getAttribute(AWAdministracionHermod.CIRCULO_NOTARIAL_EDITADO);
idHelper.setCssClase("camposformtext");
if (circuloNotarial!=null){
	editar=true;
	session.setAttribute(CCirculoNotarial.ID_CIRCULO_NOTARIAL,circuloNotarial.getIdCirculo());
	session.setAttribute(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL,circuloNotarial.getNombre());
	idHelper.setReadonly(true);
	idHelper.setCssClase("campositem");
}
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
}

function editarCirculo(text) {
	eval('document.EDITARZONA' +text+ '.submit()');
}

function validarEliminacion(nombre) {
	if (confirm('Esta seguro que desea eliminar la Zona Notarial'))
	{    
      eval('document.ELIMINARZONA' +nombre + '.submit()');
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n de C&iacute;rculos Notariales</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar C&iacute;rculo Notarial </td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">

                </table>
            
                
	<form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
     <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionHermod.ADICIONA_ZONA_NOTARIAL %>" value="<%= AWAdministracionHermod.ADICIONA_ZONA_NOTARIAL %>">
                <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Agregar un Circulo Notarial</td>
                    <td>&nbsp;</td>
                  </tr>
               <tr>
                    <td>&nbsp;</td>
                    <td>C&iacute;rculo Registral</td>
                    <td>
                    <% try {
                    circulosHelper.setNombre(CCirculo.ID_CIRCULO);
                  	circulosHelper.setCssClase("camposformtext");
                  	circulosHelper.setId(CCirculo.ID_CIRCULO);
                  	circulosHelper.setFuncion("onChange=\"cambiarAccion('"+AWAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES+"')\"");
					circulosHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>&nbsp;</td>
                    <td>Identificador C&iacute;rculo Notarial</td>
                    <td>
                    <% try {
                    idHelper.setNombre(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
                  	idHelper.setId(CCirculoNotarial.ID_CIRCULO_NOTARIAL);
                  	idHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  		%>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>&nbsp;</td>
                    <td>Nombre Circulo Notarial</td>
                    <td>
                    <% try {
                    nombreHelper.setNombre(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL);
                  	nombreHelper.setCssClase("camposformtext");
                  	nombreHelper.setId(CCirculoNotarial.NOMBRE_CIRCULO_NOTARIAL);
                  	nombreHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  
                </table>
                <table width="100%" class="camposform">
                <%
                	if (editar){%>
                		<tr>
	                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	                    <td width="155">
	                    	<a href="javascript:cambiarAccion('<%=AWAdministracionHermod.EJECUTAR_EDICION_CIRCULO_NOTARIAL  %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_guardar_cambios.gif" width="180" height="21" border="0"></a>
	                    	</td>
	                    <td>
	                    	<a href="javascript:cambiarAccion('<%=AWAdministracionHermod.TERMINA_EDICION  %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a>
	                   	</td>
	                    <td>&nbsp;</td>
	                  </tr>
                	<%}else{%>
	                	<tr>
	                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	                    <td width="155">
	                    	<a href="javascript:cambiarAccion('<%=AWAdministracionHermod.ADICIONA_CIRCULO_NOTARIAL  %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0"></a>
	                    	</td>
	                    <td>
	                    	<a href="javascript:cambiarAccion('<%=AWAdministracionHermod.TERMINA  %>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a>
	                   	</td>
	                    <td>&nbsp;</td>
	                  </tr>
                	<%}
                %>
                  
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
          <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_alcance_geografico.gif" width="16" height="21"></td>
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
                <td width="40">&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
              	<td>&nbsp;</td>
              	<td class="titulotbcentral">ID</td>
              	<td class="titulotbcentral">Nombre (Id-Nombre)</td>
                <td width="50" align="center">Eliminar</td>
                <td width="50" align="center">Editar</td>
              </tr>
               <% 
               int idZona=0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	CirculoNotarial dato = (CirculoNotarial)iter.next();
                %>    
              <tr>
                <td>&nbsp;</td>
                <td class="campositem"><%= dato.getIdCirculo()%></td>
                <td class="campositem"><%= dato.getNombre() %>-<%= dato.getIdCirculo()%></td>
                <form action="administracionHermod.do" method="post" name="ELIMINARZONA<%=idZona%>" id="ELIMINARZONA<%=idZona%>">
                <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_CIRCULO_NOTARIAL %>" value="<%= AWAdministracionHermod.ELIMINA_CIRCULO_NOTARIAL %>">
	                	<input  type="hidden" name="<%=CCirculoNotarial.ID_CIRCULO_NOTARIAL%>" id="<%= dato.getIdCirculo() %>" value="<%= dato.getIdCirculo() %>">
	                	<input  type="hidden" name="<%=CCirculo.ID_CIRCULO%>" id="<%= session.getAttribute(CCirculo.ID_CIRCULO) %>" value="<%=session.getAttribute(CCirculo.ID_CIRCULO) %>">
                  		<a href="javascript:validarEliminacion('<%=idZona%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                  	</td>
                  </form>
                 <form action="administracionHermod.do" method="post" name="EDITARZONA<%=idZona%>" id="EDITARZONA<%=idZona%>">
                <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.EDITAR_CIRCULO_NOTARIAL %>" value="<%= AWAdministracionHermod.EDITAR_CIRCULO_NOTARIAL %>">
	                	<input  type="hidden" name="<%=CCirculoNotarial.ID_CIRCULO_NOTARIAL%>" id="<%= dato.getIdCirculo() %>" value="<%= dato.getIdCirculo() %>">
	                	<input  type="hidden" name="<%=CCirculo.ID_CIRCULO%>" id="<%= session.getAttribute(CCirculo.ID_CIRCULO) %>" value="<%=session.getAttribute(CCirculo.ID_CIRCULO) %>">
                  		<a href="javascript:editarCirculo(<%=idZona%>)"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0"></a>
                  	</td>
                  </form>
              </tr>
             	<%
             	idZona ++;  
                 }
               %>  
            </table>
            </td>
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
