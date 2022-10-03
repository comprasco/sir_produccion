<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.web.*"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.MatriculaHelper"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.negocio.modelo.Circulo"%>
<%@page import="gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado"%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%@page import="gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCertificado"%>


<% ListaElementoHelper certHelper = new ListaElementoHelper();
   ListaElementoHelper tarifasHelper = new ListaElementoHelper();
   ListaElementoHelper docHelper = new ListaElementoHelper();
   ListaElementoHelper impresorasHelper = new ListaElementoHelper();
   
   MatriculaHelper matHelper = new MatriculaHelper(); 
   TextHelper textHelper = new TextHelper();
   TextHelper textHelperCopias = new TextHelper();
   
   TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
   List matriculasAsociadas=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
   List turnosFolioMig=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_TURNO_FOLIO_MIG);
   List matriculasAsociadasMig=(List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
   
   
   boolean read = false;
   String style = "camposformtext";
   
   NotasInformativasHelper helper = new NotasInformativasHelper();

   AntiguoSistemaHelper ASHelper= new AntiguoSistemaHelper();
   
   	String ocultarAntSist = request.getParameter(WebKeys.OCULTAR);	
   	
	if(ocultarAntSist == null){
	
		ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
		if(ocultarAntSist==null){
			ocultarAntSist = "TRUE";
		}
	} else {
	    //ocultarAntSist = (String)session.getAttribute(WebKeys.OCULTAR);
		session.setAttribute(WebKeys.OCULTAR,ocultarAntSist);
	}
	
	boolean isAntiguoSistema = false;
	SolicitudCertificado solicitud = null;
	LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado)session.getAttribute(WebKeys.LIQUIDACION);
	if (liquidacion!= null){
	   TipoCertificado tipoCertificado = liquidacion.getTipoCertificado();
	   session.setAttribute("TIPO_CERTIFICADO", tipoCertificado.getIdTipoCertificado());
	   if(tipoCertificado.getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID) ||
		  tipoCertificado.getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID)){
			isAntiguoSistema = true;
	   }
	   solicitud = (SolicitudCertificado) liquidacion.getSolicitud();
	   if (solicitud!= null){
		  session.setAttribute("COPIAS",String.valueOf(solicitud.getNumeroCertificados()));
	   	  Ciudadano ciudadano = solicitud.getCiudadano(); 
	   	  if (ciudadano!= null){
		   	  session.setAttribute(WebKeys.CIUDADANO,ciudadano);
		   	  
		   	  session.setAttribute(CCiudadano.IDCIUDADANO,ciudadano.getDocumento());
		   	  session.setAttribute(CCiudadano.TIPODOC,ciudadano.getTipoDoc());
		  	  session.setAttribute(CCiudadano.APELLIDO1,ciudadano.getApellido1());
		  	  session.setAttribute(CCiudadano.APELLIDO2,ciudadano.getApellido2());
		  	  session.setAttribute(CCiudadano.NOMBRE,ciudadano.getNombre());
		  	  session.setAttribute(CCiudadano.TELEFONO,ciudadano.getTelefono());
		   	  
	   	  }
		  DatosAntiguoSistema antSis = solicitud.getDatosAntiguoSistema();
		  if (antSis!= null)
		  {
	 	  	  session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS, antSis.getLibroAnio());
	 	  	  session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS, antSis.getLibroNumero());
	 	  	  session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS, antSis.getLibroPagina());
	 	  	  session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS, antSis.getLibroTipo());
		   	  
		   	  
		   	  session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, antSis.getTomoAnio());
		   	  session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS, antSis.getTomoMunicipio());
		   	  session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS, antSis.getTomoNumero());
		   	  session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS, antSis.getTomoPagina());
		   	  session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS, antSis.getComentario());
		  	  
		  }
	   }
	}
	
	String nombreVistaSolicitud = (String)session.getAttribute(gov.sir.core.web.WebKeys.VISTA_SOLICITUD);

	if(nombreVistaSolicitud==null){
		nombreVistaSolicitud = (String)session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL);
		session.setAttribute(gov.sir.core.web.WebKeys.VISTA_SOLICITUD,nombreVistaSolicitud);
	}	
	
	//IDCIRCULO
	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}

    Circulo circulo=(Circulo)session.getAttribute(WebKeys.CIRCULO);

 	String mayorExtension = (String) session.getAttribute(AWLiquidacionCertificado.MAYOR_EXTENSION_FOLIO);
	String mayorExt=(mayorExtension!=null && mayorExtension.equals(CFolio.MAYOR_EXTENSION))?"true":"false";
	
	ListaElementoHelper impresorasInmediatosHelper = new ListaElementoHelper();
	ListaElementoHelper impresorasExentosHelper = new ListaElementoHelper();
	ListaElementoHelper impresorasExtensosHelper = new ListaElementoHelper();
	String key = WebKeys.LISTA_IMPRESORAS+"_"+idCirculo;
	Map configuracion=(Map)session.getAttribute(key);
 	Iterator itImpr=configuracion.keySet().iterator();
 	
 	while(itImpr.hasNext()){
		TipoImprimible impr=(TipoImprimible)itImpr.next();
		
		if (impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_INMEDIATO)){

			List impresoras=(List)configuracion.get(impr);
			
			List tipos=new ArrayList();
			int j=0;
			Iterator itImpresoras=impresoras.iterator();
			impresorasInmediatosHelper.setShowInstruccion(false);
			ElementoLista def=new ElementoLista();
			def.setId(WebKeys.SIN_SELECCIONAR);
			def.setValor("-Seleccione una opcion-");
			tipos.add(def);
			while(itImpresoras.hasNext()){
				CirculoImpresora circImp=(CirculoImpresora)itImpresoras.next();
				ElementoLista elem=new ElementoLista();
				elem.setId(circImp.getIdImpresora());
				elem.setValor(circImp.getIdImpresora());
				tipos.add(elem);
			}
			impresorasInmediatosHelper.setTipos(tipos);

 	    }else if(impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_EXTENSO)){
 	    	List impresoras=(List)configuracion.get(impr);
			
			List tipos=new ArrayList();
			impresorasExtensosHelper.setShowInstruccion(false);
			ElementoLista def=new ElementoLista();
			def.setId(WebKeys.SIN_SELECCIONAR);
			def.setValor("-Seleccione una opcion-");
			tipos.add(def);
			int j=0;
			Iterator itImpresoras=impresoras.iterator();
			while(itImpresoras.hasNext()){
				CirculoImpresora circImp=(CirculoImpresora)itImpresoras.next();
				ElementoLista elem=new ElementoLista();
				elem.setId(circImp.getIdImpresora());
				elem.setValor(circImp.getIdImpresora());
				tipos.add(elem);
			}
			impresorasExtensosHelper.setTipos(tipos);
 	    }else if(impr.getIdTipoImprimible().equals(CTipoImprimible.CERTIFICADO_EXENTO)){
 	    	List impresoras=(List)configuracion.get(impr);
			
			List tipos=new ArrayList();
			impresorasExentosHelper.setShowInstruccion(false);
			ElementoLista def=new ElementoLista();
			def.setId(WebKeys.SIN_SELECCIONAR);
			def.setValor("-Seleccione una opcion-");
			tipos.add(def);
			
			int j=0;
			Iterator itImpresoras=impresoras.iterator();
			while(itImpresoras.hasNext()){
				CirculoImpresora circImp=(CirculoImpresora)itImpresoras.next();
				ElementoLista elem=new ElementoLista();
				elem.setId(circImp.getIdImpresora());
				elem.setValor(circImp.getIdImpresora());
				tipos.add(elem);
			}
			impresorasExentosHelper.setTipos(tipos);
 	    }
 	    
 	}

	%>
        
        <%--Se verifica si el usuario tiene el rol CAJERO_REGISTRO_SIR
            para saber si se debe mostrar las opciones de impresiÃ³n 
            de certificados inmediatos de otras oficinas--%>
        <%
        List rolesUsuario = (List)session.getAttribute(WebKeys.LISTA_ROLES);
        if( null == rolesUsuario ) {
          rolesUsuario = new java.util.ArrayList();
        }
        boolean isCajeroCertificadosSir = false;
        
        
        java.util.Iterator local_RolesListIterator = rolesUsuario.iterator();
        org.auriga.core.modelo.transferObjects.Rol local_RolesListElement;
        
        for( ;local_RolesListIterator.hasNext(); ) {
                local_RolesListElement =(org.auriga.core.modelo.transferObjects.Rol)local_RolesListIterator.next();
                String rol = local_RolesListElement.getRolId();
                if( rol.equals(CRoles.SIR_ROL_CAJERO_CERTIFICADOS_SIR) ) {
                        isCajeroCertificadosSir = true;
                        break;
                }
        }
        
        %>

   <SCRIPT>
	
	function cambiarAccion(text) {
       document.CERTIFICADO.ACCION.value = text;
       document.CERTIFICADO.submit();
	}
		
	function quitar(text) {
       document.CERTIFICADO.ITEM.value = text;
       cambiarAccion('ELIMINAR');
	}
	
	function datosAntSistema(ocultar){
		document.CERTIFICADO.OCULTAR.value = ocultar;
		cambiarAccion('RECARGAR');
	}
	
	
	function incrementarSecuencial(text){
		document.CERTIFICADO.action = 'certificado.do'
		document.CERTIFICADO.ACCION.value = text;
		document.CERTIFICADO.submit();
	}
        
        function generarCertificadoEspecial(text){
                document.CERTIFICADO.action = 'certificadoEspecial.do'
                document.CERTIFICADO.ACCION.value = text;
                document.CERTIFICADO.submit();
        }
	
	function cargarSecuencial(text){
	   document.CERTIFICADO.action = 'certificado.do'
	   document.CERTIFICADO.ACCION.value = text;
	   document.CERTIFICADO.submit();
	}
	
	function centrar(){
	    document.location.href="#verAnotaciones";
	}
	
	function agregarMatriculaSinMigrar(text) {
		if(confirm('¿Esta seguro que la matrícula aún no se ha migrado?. Si no es así utilice la opción de :     Número de Matrícula.')){
	    	document.CERTIFICADO.ACCION.value = text;
	    	document.CERTIFICADO.submit();
		}
	}
	
	function eliminarMatriculas(text) {
    	document.CERTIFICADO.ACCION.value = text;
    	document.CERTIFICADO.submit();
	}
	
	
	function oficinas(nombre,valor,dimensiones)
	{
		document.CERTIFICADO.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
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
	    popup=window.open(nombre,valor,dimensiones);
	    popup.focus();
	}
	
	function setTipoOficina(){
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO%>').value ="";
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM%>').value ="";
		document.getElementById('<%=CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO%>').value ="";
	}	
	
	function obtenerImpresoras(){
		var impresoras = new Array()
		var tipoCert = document.getElementById('<%=AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS%>').value;
		var extenso=<%=mayorExt%>;
		
		if(extenso){
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXTENSO%>').style.display='block';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_INMEDIATO%>').style.display='none';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXENTO%>').style.display='none';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_INMEDIATO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXENTO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
		}else if (tipoCert=='<%=CTipoCertificado.TIPO_EXENTO_NOMBRE%>'){
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXTENSO%>').style.display='none';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_INMEDIATO%>').style.display='none';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXTENSO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_INMEDIATO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXENTO%>').style.display='block';
		}else{
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXTENSO%>').style.display='none';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_INMEDIATO%>').style.display='block';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXENTO%>').style.display='none';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXENTO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
			document.getElementById('<%=CTipoImprimible.CERTIFICADO_EXTENSO%>').value='<%=WebKeys.SIN_SELECCIONAR%>';
		}
	}
	

   </SCRIPT>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
   
   
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">


<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
    <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
    <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
    <tr> 
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
    </tr>
    <tr> 
    <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Turno 
        certificado</td>
        <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
        <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        </td>
        <!--AHERRENO 17/05/2012
            REQ 076_151 TRANSACCIONES-->        
        <td width="120" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td><img src="<%= request.getContextPath()%>/jsp/images/ico_reloj.gif" width="16" height="21"></td>
                <td class="titulotbcentral"><%= request.getSession().getAttribute("TIEMPO_TRANSACCION")%> Seg.</td>                
            </tr>
        </table></td>        
        <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
        </tr>
    </table></td>
    <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
    </tr>
    <tr> 
    <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
    <td valign="top" bgcolor="#79849B" class="tdtablacentral"> 
    <form action="turnoLiquidacionCertificado.do" method="post" name="CERTIFICADO" id="CERTIFICADO">
    <input type="hidden" name="ACCION" value="AGREGAR">   
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->


<!--  <td align="left">
	<table border="0" cellpadding="0" cellspacing="2" class="camposform">
	<tr>
	<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
	<td>Ir a la pantalla simplificada</td>
	<td><a href="turno.certificado.simplificado.solicitud.view"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
	</td>
	</tr>	
	</table>-->

    <tr> 
        
        <td class="bgnsub">Datos Solicitud Certificado</td>
        <td width="16" class="bgnsub"></td>
        <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
    </tr>
</table>

<%
if (isCajeroCertificadosSir)
{
%>

<br>
              <table width="100%" class="camposform">
                  <tr> 
                    <td class="titulotbcentral">Oficina  Origen</td>
                    <td class="titulotbcentral">&nbsp;</td>
                  </tr>
                  <tr> 
                  <td>
                  <%
                    String checkedLocal = "", checkedNal = "";
                    String tipoOficinaChecked = (String)session.getAttribute(AWLiquidacionCertificado.TIPO_OFICINA_ORIGEN);
                    if (tipoOficinaChecked == null || tipoOficinaChecked.equals(CCertificado.OFICINA_ORIGEN_LOCAL))
                    {
                        checkedLocal = "CHECKED";
                    } else
                    {
                        checkedNal = "CHECKED";
                    }
                  %>
                       <input type="radio" name="<%= AWLiquidacionCertificado.TIPO_OFICINA_ORIGEN %>" value="<%= CCertificado.OFICINA_ORIGEN_LOCAL %>" onClick="javascript: cambiarAccion('<%= AWLiquidacionCertificado.CAMBIAR_TIPO_OFICINA_ORIGEN%>');"  <%= checkedLocal %>/> Local
                       &nbsp;
                       <input type="radio" name="<%= AWLiquidacionCertificado.TIPO_OFICINA_ORIGEN %>" value="<%= CCertificado.OFICINA_ORIGEN_NACIONAL %>" onClick="javascript: cambiarAccion('<%= AWLiquidacionCertificado.CAMBIAR_TIPO_OFICINA_ORIGEN%>');" <%= checkedNal %> /> Nacional
                  </td>
                  <td width="70%">&nbsp;</td>
                  </tr>
              </table>
<%
} //if (isCajeroCertificadosSir)
%>
              <br>
              <table width="100%" class="camposform">
                  <tr> 
                  <td class="titulotbcentral">Tipo de Certificado</td>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">Tipo de Tarifa</td>
                  <td class="titulotbcentral">Cantidad</td>
                  </tr>
                  <tr> 
                  <td>
                     <% 
                    boolean checkedNal = false;
                    String tipoOficinaChecked = (String)session.getAttribute(AWLiquidacionCertificado.TIPO_OFICINA_ORIGEN);
                     
                     try {
                            
                            checkedNal = tipoOficinaChecked != null && tipoOficinaChecked.equals(CCertificado.OFICINA_ORIGEN_NACIONAL);
                            
	 		                    List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS) ;
	 		                    List tiposCert = new ArrayList();
	 		                    if(session.getAttribute("TIPO_CERTIFICADO") == null || checkedNal){
		 		                    session.setAttribute("TIPO_CERTIFICADO",CTipoCertificado.TIPO_INMEDIATO_ID);
	 		                    } else if (checkedNal) {
	 		                    	session.setAttribute("TIPO_CERTIFICADO",CTipoCertificado.TIPO_NACIONAL_ID);
	 		                    }
	 		                    
	 		                   
	 		                   if(tipos != null){
	 		                   	   Iterator it = tipos.iterator();
	 		                   	   while(it.hasNext()){
	 		                   	   	ElementoLista el = (ElementoLista)it.next();
	 		                   	   	if (!checkedNal )
	 		                   	   	{
	 		                   	   		if (!el.getId().equals(CTipoCertificado.TIPO_NACIONAL_ID) && !el.getId().equals(CTipoCertificado.TIPO_INTERNET_ID))
	 		                   	   		{
	 		                   	   			tiposCert.add(el);
	 		                   	   		}
	 		                   	   	} else if (checkedNal && el.getId().equals(CTipoCertificado.TIPO_NACIONAL_ID))
	 		                   	   	{
	 		                   	   		tiposCert.add(el);
	 		                   	   	}
	 		                   	   }
	 		                   }
	 		                   
	 		                    if (session.getAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS) == null)
	 		                    {
	 		                    	session.setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS,"NORMAL");
	 		                    }
	 		                   
	 		                    certHelper.setOrdenar(false);
                  				certHelper.setTipos(tiposCert);
                  			    certHelper.setNombre("TIPO_CERTIFICADO");
                  			    certHelper.setCssClase("camposformtext");
                  			    certHelper.setId("TIPO_CERTIFICADO");
                  			    certHelper.setShowInstruccion(!checkedNal);
                  			    certHelper.setFuncion("onChange=\"obtenerImpresoras()\"");
								certHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					%>
                      
                  </td>
                  
                  <td><a href="javascript:generarCertificadoEspecial('CERTIFICADO_ESPECIAL')"><img src="<%=request.getContextPath()%>/jsp/images/btn_certificados_especiales.gif" border="0"></a> </td>
                 
                  <td>
                       
                      <% try   
                         { 
	 		                   List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS) ;
	 		                   
	 		                   List tiposTari = new ArrayList();
	 		                   if(tipos != null){
	 		                   	   Iterator it = tipos.iterator();
	 		                   	   while(it.hasNext()){
	 		                   	   	ElementoLista el = (ElementoLista)it.next();
	 		                   	   	if (!checkedNal ){
	 		                   	   		tiposTari.add(el);
	 		                   	   	}else if (el.getId().equals(session.getAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS))){
	 		                   	   		tiposTari.add(el);
	 		                   	   	}
	 		                   	   }
	 		                   }
	 		                   
	 		                   tarifasHelper.setOrdenar(false);
                  			   tarifasHelper.setTipos(tiposTari);
                  			   tarifasHelper.setNombre(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
                  			   tarifasHelper.setCssClase("camposformtext");
                  			   tarifasHelper.setId(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);
                  			   tarifasHelper.setFuncion("onChange=\"obtenerImpresoras()\"");
                  			   tarifasHelper.setShowInstruccion(!checkedNal);
                               //tarifasHelper.setDisabled(checkedNal);
							   tarifasHelper.render(request,out);
							}
						    catch(HelperException re)
						    {
								re.printStackTrace();
								out.println("ERROR " + re.getMessage());
							}
						    catch(Exception e)
						    {
								e.printStackTrace();
								out.println("ERROR " + e.getMessage());
							}
							
						%>
                      
                  </td>
                  
                  
                  <td> 
     				
     				<% try {
     							if(session.getAttribute("COPIAS") == null ) 
     							{
     								session.setAttribute("COPIAS","1");
     							}
     							
 		                        textHelperCopias.setNombre("COPIAS");
 		                        
                  			    textHelperCopias.setCssClase("camposformtext");
                  			    //textHelperCopias.setCssClase("campositem");
                  			   
                  			    
                  			    textHelperCopias.setId("COPIAS");
								textHelperCopias.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                    
                  </td>
                  </tr>
              </table>
              
              <br>
              <%if(!checkedNal){%>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Asociaci&oacute;n Turno Anterior</td>
                    <td width="16" class="bgnsub"></td>
                    <td width="16" class="bgnsub"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                  <tr> 
                      
                      <td>N&uacute;mero del Turno Anterior</td>
                      <td>&nbsp;</td>
                      <td>
                  
                          <%  try {
                             textHelper.setNombre("TURNO_ANTERIOR");
           			         textHelper.setCssClase("camposformtext");
					         textHelper.setId("TURNO_ANTERIOR");
						     textHelper.render(request,out);
					       }
					       catch(HelperException re){
						     out.println("ERROR " + re.getMessage());
					       }
						 %>
					 </td>
					 
					 <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
			 		  <td width="100">Agregar Turno Anterior</td>
		 			  <td><a href="javascript:cambiarAccion('AGREGAR_TURNO')"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a></td>
                  </tr>
              </table>
              <br>
              <%}%>
			<%if(!isAntiguoSistema){%>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                    Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  
                  
                  
                </tr>
              </table>
              <br>
              
              <%if(!(matriculasAsociadasMig!=null && !matriculasAsociadasMig.isEmpty())){%>
              <%
					try {
	               		  
                                                matHelper.setLink(true);
                                                matHelper.setNacional(checkedNal);
	          			  	matHelper.render(request,out);
						  
					   }
						  catch(HelperException re){
						  out.println("ERROR " + re.getMessage());
					   }
									
				%>	
		<%}%>          

              <br>
			<%}%>
			
										<!-- Opciones de matriculas sin migrar -->
				<%
					if(circulo!=null&&circulo.isProcesoMigracion()){
				%>

				<br> <%if (matriculasAsociadasMig!=null && !matriculasAsociadasMig.isEmpty()){%>

				<table width="100%" class="camposform">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif"
							width="20" height="15"></td>
						<td>Folios <B>NO MIGRADOS</B> de esta solicitud</td>
					</tr>
					<tr>
						<td width="20">&nbsp;</td>
						<td><% try {
                      			tablaHelper.setColCount(5);
                      			tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
                      			tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
					 			tablaHelper.setInputName(WebKeys.TITULO_CHECKBOX_ELIMINAR_SIR_MIG);
               		  			tablaHelper.render(request, out);
                    			}
                    			catch(HelperException re){
                      			out.println("ERROR " + re.getMessage());
                    			}
                  %></td>
					</tr>
				</table>
				
								<%if(!read){%>
				<table border="0" align="right" cellpadding="0" cellspacing="2"
					class="camposform">
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif"
							width="20" height="15" /></td>
						<td>Eliminar Seleccionadas</td>
						<td>
						

						<a href="javascript:eliminarMatriculas('ELIMINAR_MIG_INC')"><img
							src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif"
							width="35" height="25" border="0" /></a>

							
							
							</td>
					</tr>
				</table>
				<%}}%>		

				
				<P>&nbsp;</P>
				<%if(!(matriculasAsociadasMig!=null && !matriculasAsociadasMig.isEmpty())){%>
				<table width="100%" class="camposform">
					<tr></tr>
					<tr>
						<td width="20"><img
							src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
							width="20" height="15"></td>
						<td>N&uacute;mero de Matr&iacute;cula <B>SIN MIGRAR</B></td>
						<td width="130px">
						<td><%=idCirculo%>-<input name="AGREGAR_MATRICULA_REGISTRO_MIG_INC"
							id="AGREGAR_MATRICULA_REGISTRO_MIG_INC" type="text"
							value="" <%=(read)?"readonly":""%>   
							onFocus="campoactual('AGREGAR_MATRICULA_REGISTRO_MIG_INC'); cambiarAccionForm('AGREGAR_MIG_INC');"
							class="<%=style%>"> <img id="AGREGAR_MATRICULA_REGISTRO_MIG_INC"
							src="<%=request.getContextPath()%>/jsp/images/spacer.gif"
							class="imagen_campo"></td>
							
							<td>
							    <table border="0" cellpadding="0" cellspacing="2" class="camposform">
					              <tr>
						          
						          <td>Agregar Matr&iacute;cula</td>
						          <td align="right">
						          <a href="javascript:agregarMatriculaSinMigrar('AGREGAR_MIG_INC')">
						          <img src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0">
						          </a>
						          </td>
					              </tr>
				                 </table>
				            </td>
					</tr>
				</table>
				<%}%>		

				<!--End Opciones de matriculas sin migrar-->
				
				<%
				}
				%>
 
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                
                    
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos para Antiguo Sistema</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
	                <input type="hidden" name="<%=WebKeys.OCULTAR%>" id="<%=WebKeys.OCULTAR%>">
					<%if(ocultarAntSist.equals("FALSE")){%>
		                <td width="16"><a href="#"><img name="MINIMIZAR" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" onClick="datosAntSistema('TRUE')" width="16" height="16" border="0"></a></td>
					<%}else{%>
		                <td width="170" class="contenido">Haga click para maximizar</td>
		                <td width="16"><a href="#"><img name="MAXIMIZAR" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" onClick="datosAntSistema('FALSE')" width="16" height="16" border="0"></a></td>
					<%}%>
                </tr>
              </table>
              <br>
    <input name="Depto" type="hidden" id="id_depto" value="">
    <input name="Depto" type="hidden" id="nom_Depto" value="">
    <input name="Mpio" type="hidden" id="id_munic" value="">
    <input name="Mpio" type="hidden" id="nom_munic" value="">    
    <input name="Ver" type="hidden" id="id_vereda" value="">
    <input name="Ver" type="hidden" id="nom_vereda" value="">              
			  <%if(ocultarAntSist.equals("FALSE")){
			  
			  try{
			  		ASHelper.render(request, out);
			  }
					catch(HelperException re){
					out.println("ERROR " + re.getMessage());
			  }
			  
			  
              
			  }%>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                    
                    <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                    <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"></td>
                    <td width="15"> <img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
              <table width="100%" class="camposform">
                  <tr> 
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211">
                      <% try {
                      		List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
                      		
               				String selected = (String)session.getAttribute(CCiudadano.TIPODOC);
               				if(selected==null){
               					selected = CCiudadano.TIPO_DOC_ID_SECUENCIA;
               				}                      		
                      		
                      		if(docs == null){
                      			docs = new Vector();
                      		}
								docHelper.setOrdenar(false);
                   				docHelper.setTipos(docs);
		                        docHelper.setNombre(CCiudadano.TIPODOC);
                   			    docHelper.setId(CCiudadano.TIPODOC);		                        
                  			    docHelper.setSelected(selected);
                   			    docHelper.setCssClase("camposformtext");
								docHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212">
                  <% try {
 		                        textHelper.setNombre(CCiudadano.IDCIUDADANO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.IDCIUDADANO);
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
                  <td>Primer Apellido / Raz&oacute;n Social</td>
                  <td>
                  <% try {
 		                        textHelper.setNombre(CCiudadano.APELLIDO1);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO1);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  <td>Segundo Apellido</td>
                  <td>
                  <% try {
 		                        textHelper.setNombre(CCiudadano.APELLIDO2);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.APELLIDO2);
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
                  <td>Nombre</td>
                  <td>
                  <% try {
 		                        textHelper.setNombre(CCiudadano.NOMBRE);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CCiudadano.NOMBRE);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  </td>
                  
                  <td>Tel&eacute;fono</td>
                  <td>
                  <% 
                  try 
                  {
 		          	textHelper.setNombre(CCiudadano.TELEFONO);
                  	textHelper.setCssClase("camposformtext");
                  	textHelper.setId(CCiudadano.TELEFONO);
					textHelper.render(request,out);
				  }
				  catch(HelperException re)
				  {
				  	out.println("ERROR " + re.getMessage());
				  }
				  %>
				  </td>
                  
                  
                  </tr>
              </table>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                  <tr> 
                 
                <td width="150" align="center"><a href="javascript:cambiarAccion('LIQUIDAR')"><img  src="<%= request.getContextPath()%>/jsp/images/btn_liquidar.gif" width="139" height="21" border="0"></a></td>
    			<td ><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.certificado.AWLiquidacionCertificado.REMOVER_INFO%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_cancelar.gif" width="139" height="21" border="0"></a></td>
                  </tr>
              </table>
              
               <br>
               <br>
               <br>

               
                         <table width="100%" border="0" cellpadding="0" cellspacing="0">
             <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr> 
               
                <td class="bgnsub">Selección de Impresora</td>
                <td width="16" class="bgnsub"></td>
                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
                  </tr>
             </table>
                        <table width="100%" class="camposform">
                  
                  <tr> 
                 
                  <td>&nbsp; 
                   <%
						 try { 
              			    impresorasInmediatosHelper.setNombre(CTipoImprimible.CERTIFICADO_INMEDIATO);
              			    impresorasInmediatosHelper.setCssClase("camposformtext");
              			    impresorasInmediatosHelper.setId(CTipoImprimible.CERTIFICADO_INMEDIATO);
              			    impresorasInmediatosHelper.render(request,out);
              			    
              			    impresorasExentosHelper.setNombre(CTipoImprimible.CERTIFICADO_EXENTO);
              			    impresorasExentosHelper.setCssClase("camposformtext");
              			    impresorasExentosHelper.setId(CTipoImprimible.CERTIFICADO_EXENTO);
              			    impresorasExentosHelper.render(request,out);
              			    
              			    impresorasExtensosHelper.setNombre(CTipoImprimible.CERTIFICADO_EXTENSO);
              			    impresorasExtensosHelper.setCssClase("camposformtext");
              			    impresorasExtensosHelper.setId(CTipoImprimible.CERTIFICADO_EXTENSO);
							impresorasExtensosHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
					%>
                  </td>
                  </tr>
              </table>   
              <br>&nbsp;   
                  
             <table width="100%" border="0" cellpadding="0" cellspacing="0">
             <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr> 
                
                <td class="bgnsub">Secuencial de Recibos</td>
                <td width="16" class="bgnsub"></td>
                <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt="" ></td>
                  </tr>
             </table>
          
               <table width="100%">
				  <tr>
				   <td>
					<table width="100%" class="camposform">
			         <tr>
			          
			          <td>Valor Actual del Secuencial</td>
			           <a name="#verAnotaciones"></a>
			           <% String valorSecuencial = (String)session.getAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION);%>
			          <td><input name="ID_VALOR_SECUENCIAL" id="ID_VALOR_SECUENCIAL" type="text" value="<%=valorSecuencial%>"  onFocus="campoactual('ID_VALOR_SECUENCIAL');" class="camposformtext">
			          
			          	<img id="ID_MATRICULA_img" src="<%= request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo"></td>
			         </tr>
			         
			         <tr>
			         
			          <td>Motivo Incremento del Secuencial</td>
			          <% String valorSecuencial2 = (String)session.getAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION);%>
			          <td ><input name="MOTIVO_INCREMENTO_SECUENCIAL" id="MOTIVO_INCREMENTO_SECUENCIAL" type="text" value=""  onFocus="campoactual('MOTIVO_INCREMENTO_SECUENCIAL');" class="camposformtext" size='80'>
			          <td width="80">Incrementar Secuencial</td>
			          <td> <a href="javascript:incrementarSecuencial('INCREMENTAR_SECUENCIAL_RECIBO')"><img src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" ></a></td>
			          	<img id="ID_MATRICULA_img" src="<%= request.getContextPath()%>/jsp/images/spacer.gif" class="imagen_campo"></td>
			          	
			         </tr>
			         
			        </table>
			       </td>
			      </tr>
			 
			     </table>	
			</form>
          </td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
      
      
      
		<% 
		try
		{ 
		  //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		  //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		  //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		  helper.setNombreFormulario("CERTIFICADO");
		  helper.render(request,out);
		}
		catch(HelperException re)
		{
			out.println("ERROR " + re.getMessage());
		}	
		%>       
      <% 
String consultado = (String)session.getAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION);
if (consultado == null)
{%>
<SCRIPT> cargarSecuencial('CONSULTAR_SECUENCIAL_RECIBO'); </SCRIPT>
<%
}
%>
      
</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
      <td>&nbsp;</td>
      <td></td>
      <td>&nbsp;</td>
      <td></td>
      <td>&nbsp;</td>
  </tr>
</table>
<SCRIPT>
	obtenerImpresoras()
</SCRIPT>
