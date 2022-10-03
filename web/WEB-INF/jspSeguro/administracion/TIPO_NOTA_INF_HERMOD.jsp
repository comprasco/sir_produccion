<%--
/** @author : CTORRES
*** @change : Ajustes respectivos para mostrar unicamente los tipos de nota de version mayor.
*** Caso Mantis : 14370: Acta - Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
*/
--%>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collections" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoNota" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CFase" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CProceso" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="gov.sir.core.negocio.modelo.TipoNota" %>
<%@page import="gov.sir.core.negocio.modelo.util.IdTipoNotaComparator" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="java.lang.ClassCastException" %>
<%@page import="java.lang.UnsupportedOperationException" %>

<%
session.setAttribute(CTipoNota.DEVOLUTIVA, new Boolean(false));

TextHelper textHelper = new TextHelper();
TextAreaHelper textAreaHelper = new TextAreaHelper();
TextHelper idTipoNotaTextHelper = new TextHelper();

List tipos = new ArrayList();
List tiposOriginal = (List)session.getAttribute(AWAdministracionHermod.LISTA_TIPOS_NOTA_POR_PROCESO);
if(tiposOriginal == null){
	tiposOriginal = new ArrayList();
}

String fase = request.getParameter(CFase.FASE_ID);
if(fase!=null && fase.length()>0 && fase.equals(WebKeys.SIN_SELECCIONAR)){
	session.removeAttribute(CFase.FASE_ID);
	tipos = tiposOriginal;
	if(tipos == null){
		tipos = new ArrayList();
	}	
}else if(fase!=null && fase.length()>0 && !fase.equals(WebKeys.SIN_SELECCIONAR)){
	Iterator it = tiposOriginal.iterator();
	while(it.hasNext()){
		TipoNota tn = (TipoNota)it.next();
		if(tn.getFase().equals(fase)){
			tipos.add(tn);
		}
	}
	session.setAttribute(CFase.FASE_ID, fase);
}else{
	fase = (String)	session.getAttribute(CFase.FASE_ID);	
	if(fase!=null && fase.length()>0 && !fase.equals(WebKeys.SIN_SELECCIONAR)){
		Iterator it = tiposOriginal.iterator();
		while(it.hasNext()){
			TipoNota tn = (TipoNota)it.next();
			if(tn.getFase().equals(fase))
			{	
				tipos.add(tn);
			}
		}
	}else{
		tipos = tiposOriginal;
		if(tipos == null){
			tipos = new ArrayList();
		}
	}	
}
				
try
{
	Collections.sort(tipos, new IdTipoNotaComparator());										
}
catch (ClassCastException e)
{
	out.println("ERROR " + e.getMessage());
}
catch (UnsupportedOperationException e1)
{
	out.println("ERROR " + e1.getMessage());
}

String tipoNotaSeleccionada = request.getParameter("TIPONOTA");
/** @author : CTORRES
*** @change : Ajustes respectivos para mostrar unicamente los tipos de nota de version mayor.
*** Caso Mantis : 14370: Acta - Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
*/
String tipoNotaVersion = request.getParameter("VERSION");
String idTipoNotaSeleccionada = null;
if(tipoNotaSeleccionada!=null  &&  !tipoNotaSeleccionada.trim().equals("") && tipoNotaVersion!=null && !tipoNotaVersion.trim().equals("") ){
	for(Iterator iter = tipos.iterator(); iter.hasNext(); ){
		TipoNota tipoNota  = (TipoNota)iter.next();
		if(tipoNota.getIdTipoNota().equals(tipoNotaSeleccionada) && tipoNotaVersion.equals(tipoNota.getVersion().toString())){
			idTipoNotaSeleccionada = tipoNota.getIdTipoNota();
			session.setAttribute(CTipoNota.NOMBRE_TIPO_NOTA, tipoNota.getNombre());
			session.setAttribute(CTipoNota.DESCRIPCION, tipoNota.getDescripcion());
			session.setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, tipoNota.getVisibilidad());
			session.setAttribute(CTipoNota.DEVOLUTIVA, new Boolean(false));
			session.setAttribute(CFase.FASE_ID, tipoNota.getFase());
			session.setAttribute(CTipoNota.IDENTIFICADOR, tipoNota.getIdTipoNota());
                        }
		}
                /** @author : CTORRES
                 *** @change : Ajustes respectivos para mostrar unicamente los tipos de nota de version mayor.
                 *** Caso Mantis : 14370: Acta - Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
                 */
                 ValidacionesSIR validacion = new ValidacionesSIR();
                 String versionMas =String.valueOf(validacion.getMaxVersion(idTipoNotaSeleccionada));
                 session.setAttribute(CTipoNota.VERSION, versionMas );
	}

if(request.getParameter("CANCEL")!= null){
	session.removeAttribute(CTipoNota.IDENTIFICADOR);
	session.removeAttribute(CTipoNota.NOMBRE_TIPO_NOTA);
	session.removeAttribute(CTipoNota.DESCRIPCION);
	session.removeAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA);
	//session.removeAttribute(CTipoNota.DEVOLUTIVA);
	session.removeAttribute(CFase.FASE_ID);
	}


List fases = (List)session.getAttribute(AWAdministracionHermod.LISTA_FASES_DE_PROCESO);
if(fases == null){
	fases = new ArrayList();
}
ListaElementoHelper fasesHelper = new ListaElementoHelper();
fasesHelper.setTipos(fases);

List procesos = (List)application.getAttribute(WebKeys.LISTA_PROCESOS_SISTEMA);
ListaElementoHelper procesoHelper = new ListaElementoHelper();
procesoHelper.setTipos(procesos);


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
function seleccionarFase(){
	document.BUSCAR.action = 'admin.tiponotainf.view';	
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Tipos de Notas Informativas</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Tipos de Notas Informativas</td>
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
                    <td class="bgnsub">Tipos de Notas Informativas</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>


        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO%>" value="<%=  AWAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO %>">
		<%
		if(idTipoNotaSeleccionada != null){
		%>
			<input  type="hidden" name="<%= CTipoNota.ID_TIPO_NOTA %>" id="<%=idTipoNotaSeleccionada%>" value="<%= idTipoNotaSeleccionada%>">
		<%
		}
		%>
                <table width="100%" class="camposform">
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">Proceso </td>
                    <td>
			  <% try {
					procesoHelper.setDisabled((tipoNotaSeleccionada==null || tipoNotaSeleccionada.trim().equals(""))?false:true);
                    procesoHelper.setNombre(CProceso.PROCESO_ID);
                  	procesoHelper.setCssClase("camposformtext");
                  	procesoHelper.setId(CProceso.PROCESO_ID);
                  	procesoHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO+"')\"");
					procesoHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="220">Fase</td>
                    <td>
               	<% try {
					fasesHelper.setDisabled((tipoNotaSeleccionada==null || tipoNotaSeleccionada.trim().equals(""))?false:true);
					fasesHelper.setNombre(CFase.FASE_ID);
                  	fasesHelper.setCssClase("camposformtext");
                  	fasesHelper.setId(CFase.FASE_ID);
                  	fasesHelper.setFuncion("onChange='seleccionarFase();'");    
					fasesHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>

                 <tr>
                    <td>&nbsp;</td>
                    <td>Identificador</td>
                    <td>
                    	<% try {
                    idTipoNotaTextHelper.setNombre(CTipoNota.IDENTIFICADOR);
                  	idTipoNotaTextHelper.setCssClase("camposformtext");
                  	idTipoNotaTextHelper.setId(CTipoNota.IDENTIFICADOR);
                  	idTipoNotaTextHelper.setSize("10");
					idTipoNotaTextHelper.setEditable((tipoNotaSeleccionada==null || tipoNotaSeleccionada.trim().equals(""))?true:false);
					idTipoNotaTextHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                   	</td>
                  </tr>
                <tr> 

                  <tr>
                    <td>&nbsp;</td>
                    <td>Nombre</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CTipoNota.NOMBRE_TIPO_NOTA);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CTipoNota.NOMBRE_TIPO_NOTA);
                  	textHelper.setSize("50");
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                   	</td>
                  </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>Descripci&oacute;n</td>
                    <td>
               	<% try {
					textAreaHelper.setNombre(CTipoNota.DESCRIPCION);
					textAreaHelper.setCols("60");
					textAreaHelper.setRows("10");
					textAreaHelper.setCssClase("camposformtext");
					textAreaHelper.setId(CTipoNota.DESCRIPCION);
					textAreaHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                   	</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Visibilidad</td>
                    <td>
					<input type="radio" name="<%= CTipoNota.VISIBILIDAD_TIPO_NOTA%>" value="PUBLICO" 
					<%=session.getAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA)== null ? "" : session.getAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA).equals("PUBLICO") ? "CHECKED" : ""%>
					>Pública

					<input type="radio" name="<%= CTipoNota.VISIBILIDAD_TIPO_NOTA%>" value="PRIVADO"
					<%=session.getAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA)== null ? "" : session.getAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA).equals("PUBLICO") ? "" : "CHECKED"%>
					>Privada                    	
                   	</td>
                  </tr>
                   <!-- <tr>
                    <td>&nbsp;</td>
                    <td>Devolutiva</td>
                    	<td class="camposformtext">
                    	<%
                    	boolean devolutiva = false;
                    	if(session.getAttribute(CTipoNota.DEVOLUTIVA) != null){
                    		Boolean devol = (Boolean)session.getAttribute(CTipoNota.DEVOLUTIVA);
                    		devolutiva = devol.booleanValue();
                    		}
                    	%>

                    	<input name="<%= CTipoNota.DEVOLUTIVA %>" value="<%= CTipoNota.DEVOLUTIVA%>"  type="checkbox" id="<%= CTipoNota.DEVOLUTIVA %>"   <%= (false)?"checked":"" %> disabled>
                    	</td>
                  </tr> -->
                </table>
				<!--
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td class="campostip04">Cuando termine de agregar Tipos de Notas haga click en regresar. </td>
                  </tr>
                </table>
                -->
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
                    	</td>
                    <td width="155">
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=  (tipoNotaSeleccionada==null || tipoNotaSeleccionada.trim().equals(""))?AWAdministracionHermod.ADICIONA_TIPO_NOTA_INF : AWAdministracionHermod.MODIFICA_TIPO_NOTA_INF %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    	</td>
                    	
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                    </td>
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
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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
                  <td class="titulotbcentral">Proceso</td>
                  <td class="titulotbcentral">Fase</td>
                  <td class="titulotbcentral">ID</td>
                  <td class="titulotbcentral">Nombre del Tipo de Nota Informativa</td>
                   <!--
                     /** @author : CTORRES
                      *** @change : Ajustes respectivos para mostrar unicamente los tipos de nota de version mayor.
                      *** Caso Mantis : 14370: Acta - Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
                     */
                     -->
                  <td width="50" align="center">Version</td>
                  <td width="50" align="center">Editar</td>
                </tr>

                 <%
                int idNota =0;
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	TipoNota dato = (TipoNota)iter.next();
                	Proceso proceso = dato.getProceso();
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%= proceso.getNombre()%></td>
                  <td class="camposformtext_noCase"><%= dato.getFase()%></td>
                  <td class="camposformtext_noCase"><%= dato.getIdTipoNota()%></td>
                  <td class="camposformtext_noCase"><%= dato.getNombre()%></td>
                  <!--
                     /** @author : CTORRES
                      *** @change : Ajustes respectivos para mostrar unicamente los tipos de nota de version mayor.
                      *** Caso Mantis : 14370: Acta - Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
                     */
                     -->
                  <td class="camposformtext_noCase"><%= dato.getVersion()%></td>
                  <td align="center">
                  <form action="administracionHermod.do" method="post" name="ELIMINARNOTA<%=idNota%>" id="ELIMINARNOTA<%=idNota%>">

        		        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_TIPO_NOTA_INF %>" value="<%= AWAdministracionHermod.ELIMINA_TIPO_NOTA_INF %>">
	                	<input  type="hidden" name="<%= CProceso.PROCESO_ID%>" id="<%= dato.getProceso().getIdProceso() %>" value="<%= dato.getProceso().getIdProceso()   %>">
	                	<input  type="hidden" name="<%= CTipoNota.ID_TIPO_NOTA%>" id="<%= dato.getIdTipoNota() %>" value="<%= dato.getIdTipoNota()  %>">
                                
                                <!--
                                 @autor         : HGOMEZ 
                                 @mantis        : 11631 
                                 @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas  
                                 @descripcion   : Se capturan la version y si la nota está activa..
                                   -->
                                <input  type="hidden" name="<%= CTipoNota.VERSION%>" id="<%= dato.getVersion() %>" value="<%= dato.getVersion() %>">
                                <input  type="hidden" name="<%= CTipoNota.ACTIVA%>" id="<%= dato.getActivo() %>" value="<%= dato.getActivo() %>">
                  
                               <!--
                                 /** @author : CTORRES
                                  *** @change : Ajustes respectivos para mostrar unicamente los tipos de nota de version mayor.
                                  *** Caso Mantis : 14370: Acta - Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
                                 */
                                 -->
                  		<a href="admin.tiponotainf.view?TIPONOTA=<%=  dato.getIdTipoNota()%>&VERSION=<%=dato.getVersion()%>"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" alt="Editar" width="35" height="13" border="0" ></a>
                  </form>
                  </td>
                </tr>
                <%
                idNota++;
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

    alert ('Va a eliminar un Tipo de Nota');
	if (confirm('Esta seguro que desea eliminar el Tipo de Nota'))
	{
     
      eval('document.ELIMINARNOTA' +nombre + '.submit()');
	}
}
</script>
