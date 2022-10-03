<%@page import="org.auriga.core.web.*,
java.util.List,
java.util.StringTokenizer,
gov.sir.core.web.WebKeys,
gov.sir.core.web.helpers.comun.TextHelper,
gov.sir.core.web.helpers.comun.MostrarFechaHelper,
gov.sir.core.negocio.modelo.SolicitudRegistro,
gov.sir.core.web.helpers.registro.OficinaHelper,
gov.sir.core.negocio.modelo.Turno,
gov.sir.core.web.helpers.comun.ListaElementoHelper,
org.auriga.util.FechaConFormato,
gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro,
gov.sir.core.negocio.modelo.constantes.CFolio,
gov.sir.core.web.acciones.comun.AWOficinas;"%>

<% 
   ListaElementoHelper tipoEncabezado= new ListaElementoHelper();
   TextHelper textHelper = new TextHelper();
   Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
   SolicitudRegistro solicitud =(SolicitudRegistro)turno.getSolicitud();
   MostrarFechaHelper fechaHelper = new MostrarFechaHelper();
   OficinaHelper oficinaHelper = new OficinaHelper();
   boolean isInternacional= false;
   boolean isMigrada = false;

  	String fecha = FechaConFormato.formatear(solicitud.getDocumento().getFecha(), "dd/MM/yyyy");
	session.setAttribute("TIPO_ENCABEZADO",solicitud.getDocumento().getTipoDocumento().getIdTipoDocumento());
	session.setAttribute("ID_ENCABEZADO",solicitud.getDocumento().getNumero());
	session.setAttribute("CALENDAR", fecha);
  
	if(solicitud.getDocumento().getOficinaInternacional()!= null){
    	isInternacional=true;
    } else {
		if(solicitud.getDocumento().getOficinaOrigen() == null){
    		isMigrada = true;
		}
	}
    if(!isInternacional && !isMigrada) {
    	session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,solicitud.getDocumento().getOficinaOrigen().getTipoOficina().getNombre());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,solicitud.getDocumento().getOficinaOrigen().getNumero());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,solicitud.getDocumento().getOficinaOrigen().getIdOficinaOrigen());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,solicitud.getDocumento().getOficinaOrigen().getVereda().getNombre());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,solicitud.getDocumento().getOficinaOrigen().getVereda().getIdVereda());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getIdDepartamento());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getIdMunicipio());
		session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,solicitud.getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
		session.setAttribute(WebKeys.TIPO_OFICINA_I_N, WebKeys.TIPO_OFICINA_NACIONAL);
                /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                 session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,solicitud.getDocumento().getOficinaOrigen().getVersion().toString());

	} else{
		if (isMigrada) {
			String nombreMunicipio = "";
			String nombreOficina = "";
			if (solicitud.getDocumento().getComentario() != null) {
				if(solicitud.getDocumento().getComentario().indexOf("-")!=-1){
					StringTokenizer token = new StringTokenizer(solicitud.getDocumento().getComentario(), "-");
					int numToken = token.countTokens();
					if (numToken <= 1){
						nombreOficina = solicitud.getDocumento().getComentario();
					} else {
						nombreMunicipio = token.nextToken();
						nombreOficina = token.nextToken();	
					}
				} else{
					nombreOficina = solicitud.getDocumento().getComentario();
				}
			}	
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,nombreMunicipio);
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,nombreOficina);
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,nombreOficina);
			session.setAttribute(WebKeys.TIPO_OFICINA_I_N, WebKeys.TIPO_OFICINA_NACIONAL);
		} else {
			session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL,solicitud.getDocumento().getOficinaInternacional());
			session.setAttribute(WebKeys.TIPO_OFICINA_I_N, WebKeys.TIPO_OFICINA_INTERNACIONAL);
		}
	}
   %>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script>
function cambiarAccion(text) {
       document.ENCABEZADO.ACCION.value = text;
       document.ENCABEZADO.submit();
       }

function setTipoOficina(){
	document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
	document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
	document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";

}

function oficinas(nombre,valor,dimensiones)
{
	document.ENCABEZADO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
	var idDepto = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO%>').value;
	var idMunic = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC%>').value;
	var idVereda = document.getElementById('<%=CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA%>').value;
	document.getElementById('tipo_oficina').value=valor+"_ID_TIPO";
	document.getElementById('tipo_nom_oficina').value=valor+"_TIPO";
	document.getElementById('numero_oficina').value=valor+"_NUM";
	document.getElementById('id_oficina').value=valor+"_ID_OFICINA";
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
        document.getElementById('version').value=valor+"_OFICINA_VERSION";
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
    popup=window.open(nombre,valor,dimensiones);
    popup.focus();
}

</script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr> 
    <td>&nbsp;
      	<input name="Depto" type="hidden" id="id_depto" value="">
		    <input name="Depto" type="hidden" id="nom_Depto" value="">
		    <input name="Mpio" type="hidden" id="id_munic" value="">
		    <input name="Mpio" type="hidden" id="nom_munic" value="">    
		    <input name="Ver" type="hidden" id="id_vereda" value="">
		    <input name="Ver" type="hidden" id="nom_vereda" value=""></td>
    <td>&nbsp;</td>
  
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr> 
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Correcciòn del Encabezado</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  
                   
                  </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
            </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr> 
          <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
            <form action="calificacion.do" method="post" name="ENCABEZADO" id="ENCABEZADO">
            <input type="hidden" name="ACCION" value="">
   
             
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td class="bgnsub">Encabezado del Documento </td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"></td>
                  <td>Datos B&aacute;sicos </td>
                </tr>
                <%Turno turnoHijo = (Turno)session.getAttribute(WebKeys.TURNO_HIJO);
                   if(turnoHijo!=null){
                   		String mensaje = "Este turno depende del turno de Correcciones No: " + turnoHijo.getIdWorkflow();
                   %>
                   <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td class="titresaltados" colspan="2"> <img src="<%=request.getContextPath()%>/jsp/images/ico_advertencia.gif" width="16" height="21"><%=mensaje %></td>	
                      </tr>
                  </table></td>
                </tr>
                   <%} %>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                      <tr>
                        <td>Tipo</td>
                        <td>
                        
                        <% 
                        try {
	 		                    List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                   				tipoEncabezado.setOrdenar(false);
                   				tipoEncabezado.setTipos(tiposDoc);
                  			    tipoEncabezado.setNombre("TIPO_ENCABEZADO");
                  			    tipoEncabezado.setCssClase("camposformtext");
                  			    tipoEncabezado.setId("TIPO_ENCABEZADO");
								tipoEncabezado.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                        
                        </td>
                        <td>N&uacute;mero</td>
                        <td>
                        
                        <%
                         try {
 		                        textHelper.setNombre("ID_ENCABEZADO");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("ID_ENCABEZADO");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                        
                        </td>
                        <td>Fecha</td>
                        <td><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                              
                              <% 
                              try {
 		                        textHelper.setNombre("CALENDAR");
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId("CALENDAR");
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                              
                              <td><a href="javascript:NewCal('calendar','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>               
                            </tr>
                        </table></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td></td>
                  <td>Oficina de Procedencia </td>
                </tr>
                
                
                <!-- EL HELPER DE OFICINA EMPIEZA ACA -->
                
				<jsp:include page="HELPER_OFICINAS_NO_POPUP.jsp?noLiquidacionRegistro=true" flush="true" /> 


				<!-- EL HELPER DE OFICINA TERMINA ACA -->
               
                
              <!--</table>-->
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="200"><a href="javascript:cambiarAccion('ACEPTAR_CORRECCION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_corregir.gif" name="Folio" width="150" height="21" border="0" id="Folio"></a></td>
                  <td width="200"><a href="javascript:cambiarAccion('NEGAR_CORRECCION')"><img src="<%=request.getContextPath()%>/jsp/images/btn_regresar_sin_corregir.gif" name="Folio" width="180" height="21" border="0" id="Folio"></a></td>
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

      
        <%
        //Helper de Notas Informativas        
		try{
			gov.sir.core.web.helpers.comun.NotasInformativasHelper helper = new gov.sir.core.web.helpers.comun.NotasInformativasHelper();		
		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("ENCABEZADO");			
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>      
      
      
      </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>
