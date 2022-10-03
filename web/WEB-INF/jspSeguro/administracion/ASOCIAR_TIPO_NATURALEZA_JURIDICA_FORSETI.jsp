<%@page import="java.util.List" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica" %>
<%@page import="gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica" %>
<%@page import="gov.sir.core.negocio.modelo.NaturalezaJuridica" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%

if(request.getParameter("CANCEL")!= null){
	session.removeAttribute(WebKeys.NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
	}

List  tipos= new ArrayList();;
TextHelper textHelper = new TextHelper();
/*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
String selectVersion = (String)session.getAttribute(CNaturalezaJuridica.VERSION); 
GrupoNaturalezaJuridica dato = (GrupoNaturalezaJuridica)session.getAttribute(AWAdministracionForseti.SELECCION_GRUPO_NAT_JURIDICA);
NaturalezaJuridica natToUpdate = (NaturalezaJuridica)session.getAttribute(WebKeys.NATURALEZA_JURIDICA);

if(natToUpdate==null){
	session.removeAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
	session.removeAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
	if(dato!=null){
   		tipos = dato.getNaturalezaJuridicas();
   		session.setAttribute(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA, dato.getIdGrupoNatJuridica());
	}	
}
else if(natToUpdate!=null){
	if(natToUpdate.getGrupoNaturalezaJuridica()==null){
		session.removeAttribute(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
	}
	else{
		session.setAttribute(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA, natToUpdate.getGrupoNaturalezaJuridica().getIdGrupoNatJuridica());
	}
	session.setAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA, natToUpdate.getIdNaturalezaJuridica());
	session.setAttribute(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA, natToUpdate.getNombre());
	//session.setAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION, natToUpdate.getHabilitadoCalificacion());
	
	if(natToUpdate.getDominioNaturalezaJuridica()==null){
		session.removeAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
	}
	else{
		session.setAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA, natToUpdate.getDominioNaturalezaJuridica().getIdDominioNatJur());
	}
	
}

boolean habilitado = false;
if(session.getAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION)!=null){
	habilitado = ((Boolean)session.getAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION)).booleanValue();
}



List dominios = (List)application.getAttribute(WebKeys.LISTA_DOMINIOS_NATURALEZA_JURIDICA);
/*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
List grupos = (List)application.getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V);


List elementosGrupo = new ArrayList();
for (Iterator iter = grupos.iterator(); iter.hasNext();) {
	GrupoNaturalezaJuridica grupo = (GrupoNaturalezaJuridica) iter.next();
	elementosGrupo.add(
		new ElementoLista(grupo.getIdGrupoNatJuridica(), grupo.getNombre()));
}

ListaElementoHelper dominiosHelper = new ListaElementoHelper();
ListaElementoHelper gruposHelper = new ListaElementoHelper();
dominiosHelper.setTipos(dominios);
gruposHelper.setTipos(elementosGrupo);
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
</script>

<script type="text/javascript">
<%--
/*
        *  @author Carlos Torres
        *  @chage   se comenta una funcion que se deja de utilizar
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
function cambiarAccionEliminar(text, idNat) {

    alert ('Va a eliminar un Tipo de Naturaleza Jurídica');
	if (confirm('Esta seguro que desea eliminar el Tipo de Naturaleza Jurídica'))
	{
     
     document.ELIMINAR.<%= WebKeys.ACCION %>.value = text;
	 document.ELIMINAR.<%= CNaturalezaJuridica.ID_NATURALEZA_JURIDICA %>.value = idNat;
     document.ELIMINAR.submit();
	}
	
}--%>

function cambiarAccionEditar(text,idNat,nombreNat,idDominio, habilitado) {

     document.ELIMINAR.<%=WebKeys.ACCION %>.value = text;
	 document.ELIMINAR.<%=CNaturalezaJuridica.ID_NATURALEZA_JURIDICA %>.value = idNat;
	 document.ELIMINAR.<%=CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA %>.value = nombreNat;
	 document.ELIMINAR.<%=CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA %>.value = idDominio;
	 document.ELIMINAR.<%=CNaturalezaJuridica.HABILIDATO_CALIFICACION %>.value = habilitado;
     document.ELIMINAR.submit();
		
}


</script>

<script type="text/javascript">
function onChangeComboGrupos(text) {
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Administraci&oacute;n de Naturalezas Jur&iacute;dicas de Anotaciones</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Tipo Naturaleza Jur&iacute;dica de Anotaciones </td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr><td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td></tr>
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
                    <td class="bgnsub">Tipos de Naturaleza Jur&iacute;dica</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
            <form action="administracionForseti.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=   AWAdministracionForseti.ADICIONA_TIPO_NATURALEZA_JURIDICA %>" value="<%= AWAdministracionForseti.ADICIONA_TIPO_DOCUMENTO %>">
                <table width="100%" class="camposform">
                	  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Grupo de Naturaleza Jur&iacute;dica </td>
                    <td>
                        <%--
                        /*
                            *  @author Carlos Torres
                            *  @chage   se agrega nuevo campo version al formulario
                            *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                            */
                        --%>
                        <input type="hidden" id="<%=CNaturalezaJuridica.VERSION%>" name="<%=CNaturalezaJuridica.VERSION%>" value="<%=selectVersion%>" />
                    <%
                    try {
				       		
				       		gruposHelper.setNombre(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
					      	gruposHelper.setCssClase("camposformtext");
					      	gruposHelper.setId(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
					      	if(natToUpdate==null){
					      		gruposHelper.setFuncion("onChange=\"onChangeComboGrupos('"+AWAdministracionForseti.SELECCIONA_GRUPO_NAT_JURIDICA_ANOTACIONES+"')\"");
					      	}
							gruposHelper.render(request,out);
							}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
                    %>
                    </td>    
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">C&oacute;digo Naturaleza Jur&iacute;dica  de Anotaciones</td>
                    <td>
                    <% try {
                    textHelper.setNombre(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
                  	if(natToUpdate!=null){
                  		textHelper.setReadonly(true);
                  	}
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
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Nombre Naturaleza Jur&iacute;dica </td>
                    <td>
                    <% try {
                    textHelper.setNombre(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
                  	textHelper.setSize("45");
					textHelper.render(request,out);
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
                    <td width="200">Habilitado para calificaci&oacute;n </td>
                    <td>
                    	<%
                    		if (natToUpdate!=null){
                    			if (natToUpdate.isHabilitadoCalificacion()){%>
                    				<input type="checkbox" name="<%= CNaturalezaJuridica.HABILIDATO_CALIFICACION %>"  value="checkbox" checked >
                    			<%}else{%>
									<input type="checkbox" name="<%= CNaturalezaJuridica.HABILIDATO_CALIFICACION %>"  value="checkbox" >                    			
                    			<%}
                    		}else{%>
					 			<input type="checkbox" name="<%= CNaturalezaJuridica.HABILIDATO_CALIFICACION %>"  value="checkbox"  <%= habilitado?"checked":""%> >                    		
                    		<%}
                    	%>

                    </td>
                  </tr>
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="180">Dominio de Naturaleza Jur&iacute;dica </td>
                    <td>
			   <% 
					 try {
				       		dominiosHelper.setNombre(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
					      	dominiosHelper.setCssClase("camposformtext");
					      	dominiosHelper.setId(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
							dominiosHelper.render(request,out);
							}
							catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
                    </td>
                  </tr>
                               </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    
                    <td width="155">
                    <%
                     if(natToUpdate==null){
                     %>
                    	 <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.ADICIONA_TIPO_NATURALEZA_JURIDICA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
                    <%	 
                    } 
                    else{ 
                    %>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.EDITA_NATURALEZA_JURIDICA%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_editar.gif" width="139" height="21" border="0">
                     <%	 
                     }
                     %>
                     </td>
                     
                    <td><input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionForseti.TERMINA  %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></td>
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
          <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                      </tr>
                  </table>
                </td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table>
           </td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            	<table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td width="50">ID</td>
                  <td>Listado</td>
                  <td width="60" align="center">Habilitado Calificaci&oacute;n</td>
                    <%--
                        /*
                            *  @author Carlos Torres
                            *  @chage   se comenta la columna eliminar y se agrega la columna version
                            *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                            */
                        --%>
                  <td>Version</td>
                 <!-- <td width="50" align="center">Eliminar</td>-->
                  <td width="50" align="center">Editar</td>
                </tr>  
                
                <form action="administracionForseti.do" method="post" name="ELIMINAR" id="ELIMINAR">  
                <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionForseti.ELIMINA_TIPO_NATURALEZA_JURIDICA %>" value="<%= AWAdministracionForseti.ELIMINA_TIPO_NATURALEZA_JURIDICA %>">
                <input  type="hidden" name="<%= CNaturalezaJuridica.ID_NATURALEZA_JURIDICA%>" value="">
                <input  type="hidden" name="<%= CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA%>" value="">
                <input  type="hidden" name="<%= CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA%>" value="">
                <input  type="hidden" name="<%= CNaturalezaJuridica.HABILIDATO_CALIFICACION%>" value="">
                
              <% 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                NaturalezaJuridica nat = (NaturalezaJuridica)iter.next();
                String habilitadoCalificacion=nat.isHabilitadoCalificacion()?"SI":"NO";
                %>                   
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem" width="50"><%=   nat.getIdNaturalezaJuridica() %></td>
                  <td class="campositem"><%=   nat.getNombre() %></td>
                  <td class="campositem" width="60"><%=   habilitadoCalificacion %></td>
                 
          <!--        <td align="center">
                        <% 
                        //Identificador dominio de naturaleza jurídica
                        gov.sir.core.negocio.modelo.DominioNaturalezaJuridica domNat = new gov.sir.core.negocio.modelo.DominioNaturalezaJuridica();
                        domNat = nat.getDominioNaturalezaJuridica();
                        String nombreDominio=null;
                        if (domNat != null)
                        {
                           nombreDominio = domNat.getIdDominioNatJur();
                        }
                        //Habilitado para calificación
                        boolean habilitadoCal = nat.isHabilitadoCalificacion();
                        String textoHabilitado= null;
                        if (habilitadoCal)
                        {
                           textoHabilitado ="checked";
                        }
                        %>
          <%--
                        /*
                            *  @author Carlos Torres
                            *  @chage   se comenta las celdas de eliminacio
                            *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                            */
                        --%>
                  <td>Version</td>
                 <td width="50" align="center">Eliminar</td>-->

                            <%--
                  		<a href="javascript:cambiarAccionEliminar('<%=AWAdministracionForseti.ELIMINA_TIPO_NATURALEZA_JURIDICA%>', '<%=nat.getIdNaturalezaJuridica()%>')"> <img src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0"></a>
                            
                  	</td>--%>
                            <td align="center">
                                <%=nat.getVersion()%>
                            </td>
                  	<td align="center">
                  	    
                  	   
	                	
                  		<a href="javascript:cambiarAccionEditar('<%=AWAdministracionForseti.EDITA_TIPO_NATURALEZA_JURIDICA%>','<%=nat.getIdNaturalezaJuridica()%>','<%=nat.getNombre()%>','<%=nombreDominio%>', '<%=textoHabilitado%>')"> <img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" width="35" height="13" border="0"></a>
                  		
                   </td>
                </tr>
                <% 
                }  
                %> 
            </form>
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
