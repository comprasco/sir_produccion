<%@page import="org.auriga.core.modelo.transferObjects.Rol"%>
<!--
/**
 * JSP que muestra el resultado en el proceso traslado de folio
 * @version 1.0, 24/12/2010
 * @author Julio Alcazar
 */
--><%@page import="gov.sir.forseti.dao.impl.jdogenie.JDOGenieZonaRegistralDAO"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.acciones.correccion.AWLiquidacionCorreccion"%>
<%@page import="gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants" %>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas" %>
<%@page import="gov.sir.core.web.helpers.registro.OficinaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>

<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR"%>

<%
  
   Folio folio_traslado = (Folio) session.getAttribute(AWAdministracionForseti.FOLIO_CONFIRMACION);
   ZonaRegistral zonaRegistral = null;
   String fechaHoy = null;
   if (folio_traslado != null){
       zonaRegistral = folio_traslado.getZonaRegistral();
       Calendar hoy = Calendar.getInstance();
       hoy.setTime(folio_traslado.getFechaApertura());
       fechaHoy=hoy.get(Calendar.DAY_OF_MONTH)+"/"+(hoy.get(Calendar.MONTH)+1)+"/"+hoy.get(Calendar.YEAR);
   }

   

   TextHelper textHelper = new TextHelper();
   TextAreaHelper textAreaHelper = new TextAreaHelper();
   
                     
    /**
    * @author     : Carlos Torres
    * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
    */
    List RolesList = (List) session.getAttribute(WebKeys.LISTA_ROLES);
    String numeroFundamento = "";
    List tiposFundamento = new java.util.ArrayList();
    List fundamentosAsociados = (List)session.getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
    TrasladoSIR traslado = new TrasladoSIR();  
   if(request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO") == null) {
        List  tiposFundamentoSIR = traslado.TiposFundamento();
        for (java.util.Iterator iter = tiposFundamentoSIR.iterator(); iter.hasNext();) {
            String[] tf = iter.next().toString().split(";");
            if(tf[0].equals("1")){
                boolean esAdminNac = false;
                for(java.util.Iterator row = RolesList.iterator();row.hasNext();){
                    Rol rol = (Rol)row.next();
                    if(rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)){
                        esAdminNac = true;
                    }
                }
                if(esAdminNac){
                    tiposFundamento.add(new ElementoLista(tf[0], tf[1]));
                }
            }else{
              tiposFundamento.add(new ElementoLista(tf[0], tf[1]));  
            }
        }
    //    elementos.remove(circulo_actual);
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO", tiposFundamento);
    } else {
        tiposFundamento = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO");
    //    elementos.remove(circulo_actual);
    }
   
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript" >



function oficinas(nombre,valor,dimensiones){
		//document.BUSCAR.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
		var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
		var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
		var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
		document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
		document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
		document.getElementById('numero_oficina').value=valor+"_NUM";
		document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
		popup=window.open(nombre+'?<%=AWOficinas.ID_DEPTO%>='+idDepto+'&<%=AWOficinas.ID_MUNIC%>='+idMunic+'&<%=AWOficinas.ID_VEREDA%>='+idVereda,valor,dimensiones);
		popup.focus();
	}

	function locacion(nombre,valor,dimensiones){
                document.getElementById('id_depto').value=valor+"_ID_DEPTO";
		document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
                document.getElementById('id_munic').value=valor+"_ID_MUNIC";
                document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
                document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
                document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
		popup=window.open(nombre+'&<%=CVereda.MOSTRAR_VEREDA%>=<%=CVereda.MOSTRAR_VEREDA%>',valor,dimensiones);
                popup.focus();
	}

	function locacion1(nombre,valor,dimensiones){
		document.getElementById('id_depto').value=valor+"_ID_DEPTO";
		document.getElementById('nom_Depto').value=valor+"_NOM_DEPTO";
		document.getElementById('id_munic').value=valor+"_ID_MUNIC";
		document.getElementById('nom_munic').value=valor+"_NOM_MUNIC";
		document.getElementById('id_vereda').value=valor+"_ID_VEREDA";
		document.getElementById('nom_vereda').value=valor+"_NOM_VEREDA";
	    popup=window.open(nombre,valor,dimensiones);
	    popup.focus();
	}

        function validarFecha(){
    if (document.TRASLADO_CONFIRMACION.<%=CSolicitudRegistro.CALENDAR%>.value.length>0){
        var index=document.TRASLADO_CONFIRMACION.<%=CSolicitudRegistro.CALENDAR%>.value.lastIndexOf('/')+1;
	if (index!=null){
            var fin=document.TRASLADO_CONFIRMACION.<%=CSolicitudRegistro.CALENDAR%>.value.length;
            var texto=document.TRASLADO_CONFIRMACION.<%=CSolicitudRegistro.CALENDAR%>.value.substring(index,fin);
            if (texto.length!=4){
                alert('Fecha incorrecta');
                document.TRASLADO_CONFIRMACION.<%=CSolicitudRegistro.CALENDAR%>.value='';
                document.TRASLADO_CONFIRMACION.<%=CSolicitudRegistro.CALENDAR%>.focus();
	    }
	}
    }
}

function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
/**
* @author     : Carlos Torres
* Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
*/

function cambiarAccionDestido(text) {
    document.TRASLADO_CONFIRMACION.ACCION.value = text;
    document.TRASLADO_CONFIRMACION.submit();
}
</script>
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traslado</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Confirmacion de Matriculas Trasladadas</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
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
            <form action="administracionForseti.do" method="post"  name="TRASLADO_CONFIRMACION" id="TRASLADO_CONFIRMACION">
                <input type="hidden" name="ACCION" value="TRASLADO_CONFIRMACION">
                <input type="hidden" name="ID_MATRICULA" id="ID_MATRICULA" value="<%=folio_traslado.getIdMatricula()%>">
                <input name="Depto" type="hidden" id="id_depto" value="">
                <input name="Depto" type="hidden" id="nom_Depto" value="">
                <input name="Mpio" type="hidden" id="id_munic" value="">
                <input name="Mpio" type="hidden" id="nom_munic" value="">
                <input name="Ver" type="hidden" id="id_vereda" value="">
                <input name="Ver" type="hidden" id="nom_vereda" value="">
              <br />
              <table width="100%" class="camposform">

                    <tr>
                        <td width="20"><img src="/SNR/jsp/images/ind_turno.gif" width="20" height="15"></td>
                        <td width="20" class="campositem">N&ordm;</td>
                        <td class="campositem"><%=folio_traslado.getIdMatricula()!= null ? folio_traslado.getIdMatricula(): "&nbsp;"%></td>
                   </tr>
            </table>
             <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Folio</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="1" class="camposform">
                    <tr>
                     <td width="9%" align="right">Circulo</td>
                     <td width="18%" class="campositem"><%=zonaRegistral.getCirculo().getNombre()!= null ? zonaRegistral.getCirculo().getNombre() : "&nbsp;"%></td>
                     <td width="9%" align="right">Departamento</td>
                     <td width="18%" class="campositem"><%=zonaRegistral.getVereda().getMunicipio().getDepartamento().getNombre()!= null ? zonaRegistral.getVereda().getMunicipio().getDepartamento().getNombre() : "&nbsp;"%></td>
                     <td width="9%" align="right">Municipio</td>
                     <td width="18%" class="campositem"><%= zonaRegistral.getVereda().getMunicipio().getNombre()!= null ? zonaRegistral.getVereda().getMunicipio().getNombre() : "&nbsp;"%></td>
                     <td width="9%" align="right">Vereda</td>
                     <td width="18%" class="campositem"><%= zonaRegistral.getVereda().getNombre()!= null ? zonaRegistral.getVereda().getNombre() : "&nbsp;"%></td>
                    </tr>
                    <tr>
                     <td width="9%" align="right">Fecha Apertura</td>
                     <td width="18%" class="campositem"><%=fechaHoy != null ? fechaHoy : "&nbsp;"%></td>
                     <td width="9%" align="right">Radicacion</td>
                     <td width="18%" class="campositem"><%=folio_traslado.getRadicacion()!= null ? folio_traslado.getRadicacion() : "&nbsp;"%></td>
                     <td width="9%" align="right">Con</td>
                     <td width="18%" class="campositem"><%=folio_traslado.getDocumento().getTipoDocumento().getNombre()!= null ? folio_traslado.getDocumento().getTipoDocumento().getNombre() : "&nbsp;"%></td>
                    </tr>
                    <tr>
                     <td width="9%" align="right">Codigo Catastral</td>
                     <td width="18%" class="campositem"><%=folio_traslado.getCodCatastral() != null ? folio_traslado.getCodCatastral() : "&nbsp;"%></td>
                     <td width="9%" align="right">Estado Folio</td>
                     <td width="18%" class="campositem"><%=folio_traslado.getEstado().getNombre()!= null ? folio_traslado.getEstado().getNombre() : "&nbsp;"%></td>
                    </tr>
      	      </table>
                <br>
                <br>

                <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                    <td width="12"><img name="sub_r1_c1"
			src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
			width="12" height="22" border="0" alt="">
                    </td>
		    <td
			background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif"
			class="bgnsub">Edicion Datos
                    </td>
		    <td width="16" class="bgnsub"><img
			src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif"
			width="16" height="21">
                    </td>
		    <td width="16" class="bgnsub"><img
			src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif"
			width="16" height="21">
                    </td>
		    <td width="15"><img name="sub_r1_c4"
			src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
			width="15" height="22" border="0" alt="" >
                    </td>
                </tr>
	      </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="1" class="camposform">
                <tr>
			<td  align="right">Departamento</td>
			<td >
                        <%
			textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
                        textHelper.setSize("5");
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
                        textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO');");
                        textHelper.setReadonly(false);
                        textHelper.render(request, out);

                        textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
                        textHelper.setSize("35");
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
                        textHelper.setReadonly(false);
                        textHelper.render(request, out);
                        %></td>
			<td align="right">Municipio</td>
			<td >
                        <%
                        textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
                        textHelper.setSize("6");
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
                        textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');");
	                textHelper.setReadonly(false);
                        textHelper.render(request, out);

                        textHelper.setSize("35");
                        textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
                        textHelper.setCssClase("camposformtext");
                        textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
                        textHelper.setReadonly(false);
                        textHelper.render(request, out);
                        %></td>
                        <td align="right">Vereda</td>
			<td>
                        <%
                        textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
                        textHelper.setSize("6");
			textHelper.setCssClase("camposformtext");
                        textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
                        textHelper.setFuncion("onChange=javascript:limpiarDatos('ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC');");
                        textHelper.setReadonly(false);
                        textHelper.render(request, out);

                        textHelper.setSize("35");
                        textHelper.setNombre(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
			textHelper.setCssClase("camposformtext");
                        textHelper.setId(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
                        textHelper.setReadonly(false);
                        textHelper.render(request, out);
                        %></td>
			<td>
                            <a href="javascript:locacion('seleccionar.locacion.do?LOCACIONES_CIRCULO=','ANOTACION_OFICINA_DOCUMENTO','width=790,height=175,menubar=no');"><img
			     src="<%=request.getContextPath()%>/jsp/images/ico_mapcolombia.gif"
			     width="16"
			     height="21" border="0">
                            </a>
                        </td>
		</tr>
	     </table>

             <br>
              <%--
               /**
                * @author     : Carlos Torres
                * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                */
             --%>
             <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Fundamento</td>
                </tr>
              </table>
              
              <% if (fundamentosAsociados != null && !fundamentosAsociados.isEmpty()) { %>
                    <table width="100%" border="0" class="camposform">
                        <tr>
                            <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                            <td colspan="4">Fundamentos de esta solicitud</td>
                        </tr>
                        
                        <tr>
                            <td width="20">&nbsp;</td>
                            <td width="5px">&nbsp;</td>
                            <td class="titulotbcentral">Tipo</td>
                            <td class="titulotbcentral">Número</td>
                            <td class="titulotbcentral">Fecha</td>
                        </tr>
                        <%  
                            String f = null;
                            String[] s = null;
                            Iterator item;
                            item = fundamentosAsociados.iterator();
                            while (item.hasNext()) {
                                f = item.next().toString();
                                s = f.split(";");
                        %>
                        <tr>
                            <td width="20px">&nbsp;</td>
                            <td width="5px" class="campositem">
                                <input type="checkbox" id="<%= f %>" name="<%= WebKeys.ELIMINAR_FUNDAMENTOS_CHECKBOX %>" value="<%= f %>"/>
                            </td>
                            <td class="campositem"><%= s[1] %></td>
                            <td class="campositem"><%= s[2] %></td>
                            <td class="campositem"><%= s[3] %></td>
                        </tr>
                        <% } %>
                    </table>
                    
                    <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                        <tr>
                            <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/></td>
                            <td>Eliminar Fundamento</td>
                            <td><a href="javascript:cambiarAccionDestido('ELIMINAR_FUNDAMENTO_CONFIRMACION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a></td>
                        </tr>
                    </table>
                    <P>&nbsp;</P>
            <% } %>
                          <table width="100%" border="0" class="camposform">
                  <tbody>
                      <tr>
                          <td width="20" height="18">&nbsp;</td>
                          <td class="titulotbcentral">Tipo</td>
                          <td>
                              <% try {
                                    ListaElementoHelper tiposFundamentoHelper = new ListaElementoHelper();
                                    tiposFundamentoHelper.setOrdenar(false);
                                    tiposFundamentoHelper.setNombre(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
                                    tiposFundamentoHelper.setId(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
                                    tiposFundamentoHelper.setCssClase("camposformtext");
                                    tiposFundamentoHelper.setTipos(tiposFundamento);
                                    tiposFundamentoHelper.render(request, out);
                                } catch(HelperException re) {
                                    out.println("ERROR " + re.getMessage());
                                }
                              %>
                          </td>
                          <td class="titulotbcentral">N&uacute;mero</td>
                          <td>
                              <input name="<%=CFolio.NUMERO_FUNDAMENTO%>" id="<%=CFolio.NUMERO_FUNDAMENTO%>" type="text" value="<%=numeroFundamento%>" class="camposformtext">
                          </td>
                          <td class="titulotbcentral">Fecha</td>
                          <td>
                              <table border="0" cellpadding="0" cellspacing="0">
                                  <tbody>
                                      <tr>
                                          <td>
                                              <% try {
                                                        textHelper.setNombre(CSolicitudRegistro.CALENDAR);
                                                        textHelper.setCssClase("camposformtext");
                                                        textHelper.setId(CSolicitudRegistro.CALENDAR);
                                                        textHelper.setFuncion("  onkeypress=\"return valideDate(event,'"+CSolicitudRegistro.CALENDAR+"');\" "
                                                    + " onChange=\"fixDate('"+CSolicitudRegistro.CALENDAR+"')\"   onBlur=\"javascript:validarFecha()\"" );
                                                        textHelper.setReadonly(false);
                                                        textHelper.render(request,out);
                                                     } catch(HelperException  re) {
                                                         out.println("ERROR " + re.getMessage());
                                                     }
                                              %>
                                          </td>
                                          <td align="left">
                                              <a href="javascript:NewCal('calendar','ddmmmyyyy',true,24)">
                                                  <img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" 
                                                       alt="Fecha" width="16" height="21" border="0" 
                                                       onClick="javascript:Valores('<%=request.getContextPath()%>')">
                                              </a>
                                          </td>
                                      </tr>
                                  </tbody>
                              </table>
                          </td>
                          <td align="right">
                              <table border="0" cellpadding="0" cellspacing="2" class="camposform">
                                  <tbody>
                                      <td><img src=" <%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                      <td>Agregar Fundamento</td>
                                      <td align="right">
                                          <a href="javascript:cambiarAccionDestido('AGREGAR_FUNDAMENTO_CONFIRMACION');" name="imageField">
                                              <img alt="[add fundamento]" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                                          </a>
                                      </td>
                                  </tbody>
                              </table>
                          </td>
                      </tr>
                  </tbody>
              </table>
              <br />
            
            
            <hr class="linehorizontal" />
            <hr class="linehorizontal">

            <table width="100%" class="camposform">
                <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                        <input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" name="aprobar" width="150" height="21" border="0" id="aprobar">
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/admin.traslado.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar.gif" name="Regresar" width="150" height="21" border="0" id="Terminar"></a>
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
