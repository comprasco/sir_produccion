<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.web.*, org.auriga.core.modelo.transferObjects.Rol"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.MatriculaHelper"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.Vector"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.web.acciones.certificadosmasivos.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%--
  * @author      :   Julio Alcázar Rivas
  * @change      :   Nuevo import de oficinas
  * Caso Mantis  :   000941
--%>
<%@page import="gov.sir.core.web.acciones.comun.AWOficinas"%>
<%
  Circulo circulo=(Circulo)session.getAttribute(WebKeys.CIRCULO);
  ListaElementoHelper docHelper = new ListaElementoHelper(); 
  List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
  List copias = new ArrayList();
  
  ListaElementoHelper certHelper = new ListaElementoHelper();
  MatriculaHelper matHelper = new MatriculaHelper(); 
  TextHelper textHelper = new TextHelper();
  NotasInformativasHelper helper = new NotasInformativasHelper();
  TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
  
  String selected = "", documento = "", tipoCertificado = "", rol = "", nom = "", ap1 = "", ap2 = "";
  boolean superdoc = false;
  boolean cajeroCert = false;
  
  if ( session.getAttribute(WebKeys.ROL) != null ) {
    Rol rolClass = (Rol) session.getAttribute(WebKeys.ROL);
    rol = rolClass.getRolId();
    
    if ( rol != null ) {
      rol = rol.trim();
    }
  }
  
  String tipoTarifa = "";
  if ( rol.equalsIgnoreCase("SIR_ROL_SUPER_DOC") ) {
    tipoCertificado = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
    tipoTarifa = CTipoTarifa.NORMAL;
    session.setAttribute("ID_TIPO_CERTIFICADO", CTipoCertificado.TIPO_INMEDIATO_ID);
    superdoc = true;
  }else if(rol.equalsIgnoreCase("SIR_ROL_CAJERO_CERT_MASIVOS")){
  	tipoCertificado = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
    tipoTarifa = CTipoTarifa.NORMAL;
    session.setAttribute("ID_TIPO_CERTIFICADO", CTipoCertificado.TIPO_INMEDIATO_ID);
    cajeroCert = true;
  }
  else {
    tipoCertificado = CTipoCertificado.TIPO_EXENTO_NOMBRE;
    tipoTarifa = CTipoTarifa.EXENTO;    
    session.setAttribute("ID_TIPO_CERTIFICADO", CTipoCertificado.TIPO_EXENTO_ID);
  }
  
  if ( session.getAttribute(CCiudadano.TIPODOC) != null ) {
    selected = (String) session.getAttribute(CCiudadano.TIPODOC);
  }
  if ( session.getAttribute(CCiudadano.DOCUMENTO) != null ) {
    documento = (String) session.getAttribute(CCiudadano.DOCUMENTO);
  }
  if ( session.getAttribute(CCiudadano.APELLIDO1) != null ) {
    ap1 = (String) session.getAttribute(CCiudadano.APELLIDO1);
  }
  if ( session.getAttribute(CCiudadano.APELLIDO2) != null ) {
    ap2 = (String) session.getAttribute(CCiudadano.APELLIDO2);
  }
  if ( session.getAttribute(CCiudadano.NOMBRE) != null ) {
    nom = (String) session.getAttribute(CCiudadano.NOMBRE);
  }
  
  if ( session.getAttribute("LISTA_COPIAS") != null ) {
    copias = (List) session.getAttribute("LISTA_COPIAS");
  }
  
  //coloca flag para indicar que toca poner la pantalla wait
  session.setAttribute(WebKeys.PANTALLA_ESPERA, new Boolean(true));

%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<%--
  * @author      :   Julio Alcázar Rivas
  * @change      :   Funciones javascript para cargar las oficinas por ubicación geografica 
                     dependientes al documento de la solicitud.
  * Caso Mantis  :   000941
--%>
<script type="text/javascript">
var constancia;
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

	function oficinas(nombre,valor,dimensiones){
		//document.BUSCAR.ACCION.value='<%=CSolicitudRegistro.PRESERVAR_INFO%>';
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


</script>
   <SCRIPT>
		
       function quitar(text) {
        document.frmEliminarMatricula.IDMAT.value = text;
        document.frmEliminarMatricula.submit();
       }
      
   </SCRIPT>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
      <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Certificados 
            Masivos </td>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"> 
          </td>
          <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> 
            <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Solicitud certificados 
                  masivos </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
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
          <form name="frmTiposMatricula" method="post" action="turnoLiquidacionCertificadosMasivos.do">
          <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">
          <table width="100%" class="camposform">
             <tr> 
               <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
               <td class="titulotbcentral">Tipo de Certificado de la Solicitud</td>
             </tr>
             <tr> 
               <td>&nbsp; </td>
               <td>
                 <input name="TIPO_CERTIFICADO" type="text" class="camposformtext" id="TIPO_CERTIFICADO" value="<%=tipoCertificado%>" readonly="true">  
               </td>
             </tr>
          </table>
          </form>
          <br>
          <form name="frmEliminarMatricula" method="post" action="turnoLiquidacionCertificadosMasivos.do">
          <input type="hidden" name="ACCION" value="ELIMINAR">
          <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">          
          <table width="100%" class="camposform">
              <tr> 
                <td><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                <td>Folios de esta solicitud</td>
              </tr>
              <tr> 
                <td width="20">&nbsp;
                </td>
                <td>
                  <% try {
                      tablaHelper.setColCount(5);
                      tablaHelper.setListName(WebKeys.LISTA_CERTIFICADOS_MASIVOS);
                      tablaHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
                      tablaHelper.setAlt(copias);
               		  tablaHelper.render(request, out);
                    }
                    catch(HelperException re){
                      out.println("ERROR " + re.getMessage());
                    }
                  %>
                </td> 
              </tr>
            </table>
            <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
              <tr>
                <td width="20">
                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
                </td>
                <td>Eliminar Seleccionadas</td>
                <td>
                  <input name="imageField22" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"/>
                </td>
              </tr>
            </table>
            <P>&nbsp;</P>
            </form>
            <hr class="linehorizontal"> <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr> 
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                  Adicion de Folios</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>
            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="camposform">
              <tr> 
                <td> 
                <% if ( !cajeroCert ) { %>
                      <form name="frmAddMatricula" method="post" action="turnoLiquidacionCertificadosMasivos.do">
	                  <input type="hidden" name="ACCION" value="AGREGAR">
    			      <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>"> 
    			<%} 
    			   else{%>
    			      <form name="frmAddMatricula" method="post" action="turnoLiquidacionCertificadosMasivos.do">
	                  <input type="hidden" name="ACCION" value="AGREGAR_VERIFICADO">
    			      <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">   
    			 <%}%>                   
                    <% if ( !superdoc ) { %>
                    <table width="100%" class="camposform">
                      <tr> 
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td>Adicionar Matr&iacute;cula</td>
                      </tr>
                      <tr> 
                        <td width="20">&nbsp;</td>
                        <td> <table width="100%">
                            <tr> 
                              <td height="31" style="font-style:normal; font-size:xx-small;"><%=circulo.getIdCirculo()%>-<input name="ID_MATRICULA" type="text" class="camposformtext">
                              </td>
                              <td height="31" style="font-style:normal; font-size:xx-small;">No. Copias:
                                <input name="COPIAS" type="text" class="camposformtext" size="5" value="1">
                              </td>
                              <td width="19%"> <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                  <tr> 
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                    <td>AgregarMatr&iacute;cula</td>
                                    <td><input name="imageField22" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"> 
                                    </td>
                                  </tr>
                                </table></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table>
                    <% } %>
                  </form>
                  <form name="form1" enctype="multipart/form-data" method="post" action="turnoLiquidacionCertificadosMasivos.do">
                  <input type="hidden" name="ACCION" value="UPLOAD">
		          <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">                  
                    <table width="100%" class="camposform">
                      <tr> 
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td>Cargar matr&iacute;culas desde archivo plano</td>
                      </tr>
                      <tr> 
                        <td width="20">&nbsp;</td>
                        <td> <table width="100%">
                            <tr> 
                              <td width="81%"> <input name="filename" type="file" class="camposformtext"> 
                              </td>
                              <td width="19%"><table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform">
                                  <tr> 
                                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                                    <td>Subir Archivo</td>
                                    <td><input name="imageField222" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"> 
                                    </td>
                                  </tr>
                                </table></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table>
                  </form></td>
              </tr>
            </table>
<%--
  * @author      :   Julio Alcázar Rivas
  * @change      :   Se agrega el formulario para el ingreso del documento de la solicitud
  * Caso Mantis  :   000941
--%>
            <br>
            <hr class="linehorizontal">
            <form name="SOLICITUD" method="post" action="turnoLiquidacionCertificadosMasivos.do">
                <input type="hidden" name="ACCION" value="LIQUIDAR">
                <%
                if (rol.equals(CRoles.SIR_ROL_USUARIO_OPERATIVO)){
                %>
                <input name="Depto" type="hidden" id="id_depto" value="">
                <input name="Depto" type="hidden" id="nom_Depto" value="">
                <input name="Mpio" type="hidden" id="id_munic" value="">
                <input name="Mpio" type="hidden" id="nom_munic" value="">
                <input name="Ver" type="hidden" id="id_vereda" value="">
                <input name="Ver" type="hidden" id="nom_vereda" value="">
 	    <table width="100%" border="0" cellpadding="0" cellspacing="0">
	     <tr>
                 <td width="12"><img name="sub_r1_c1"
		     src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif"
		     width="12" height="22" border="0" alt=""></td>
		 <td class="bgnsub">Encabezado del Documento</td>
		 <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_registro.gif"
		     width="16" height="21"></td>
		 <td width="15"><img name="sub_r1_c4"
		     src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif"
		     width="15" height="22" border="0" alt=""></td>
	     </tr>
	    </table>
	    <table width="100%" class="camposform">
             <tr>
		 <td width="20"><img
		     src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
		     width="20" height="15"></td>
		 <td>Datos B&aacute;sicos</td>
	     </tr>
	     <tr>
                 <td>&nbsp;</td>
		 <td>
	            <table width="100%" class="camposform">
                     <tr>
			  <td>Tipo</td>
			  <td>
                              <% try {
                        		textHelper.setNombre(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			textHelper.setCssClase("camposformtext");
                  			textHelper.setFuncion("onchange=\"javascript:cambiarValorTipoDocumento(this.value); cambiarAccionForm('LIQUIDAR_REGISTRO');\"");
                  			textHelper.setId(CSolicitudRegistro.ID_TIPO_ENCABEZADO);
                  			textHelper.setEditable(true);
					textHelper.render(request,out);
					textHelper.setFuncion("");
				     }catch(HelperException re){
					   out.println("ERROR " + re.getMessage());
				     }
			      %>
                          </td>
			  <td>
                              <% try {
	 		                List tiposDoc = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
                  			ListaElementoHelper tipoEncabezado= new ListaElementoHelper();
                  			tipoEncabezado.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
					tipoEncabezado.setFuncion("onchange=getElementById('ID_TIPO_ENCABEZADO').value=this.value;");
					tipoEncabezado.setOrdenar(false);
                   			tipoEncabezado.setTipos(tiposDoc);
                  			tipoEncabezado.setNombre(CSolicitudRegistro.TIPO_ENCABEZADO);
                  			tipoEncabezado.setCssClase("camposformtext");
                  			tipoEncabezado.setId(CSolicitudRegistro.TIPO_ENCABEZADO);
					tipoEncabezado.render(request,out);
				      }catch(HelperException re){
					    out.println("ERROR " + re.getMessage());
				      }
			      %>
                          </td>
                          <script>
                        	document.getElementById('ID_TIPO_ENCABEZADO').value=document.getElementById('TIPO_ENCABEZADO').value;
                        	if(document.getElementById('ID_TIPO_ENCABEZADO').value=='SIN_SELECCIONAR'){
                        		document.getElementById('ID_TIPO_ENCABEZADO').value='';
                        	}
                          </script>
			  <td>N&uacute;mero</td>
			  <td>
                              <% try {
                        		textHelper.setNombre(CSolicitudRegistro.ID_ENCABEZADO);
                  			textHelper.setCssClase("camposformtext");
                  			textHelper.setId(CSolicitudRegistro.ID_ENCABEZADO);
					textHelper.render(request,out);
				     }catch(HelperException re){
					   out.println("ERROR " + re.getMessage());
				     }
			      %>
                          </td>
			  <td>Fecha</td>
			  <td>
			    <table border="0" cellpadding="0" cellspacing="0">
				<tr>
				    <td>
                                        <% try {
                              	                 textHelper.setNombre(CSolicitudRegistro.CALENDAR);
                  			         textHelper.setCssClase("camposformtext");
                  			         textHelper.setId(CSolicitudRegistro.CALENDAR);
						 textHelper.render(request,out);
					       }catch(HelperException re){
						    out.println("ERROR " + re.getMessage());
					       }
					%>
                                    </td>
				    <td>
                                        <a href="javascript:NewCal('calendar','ddmmmyyyy',true,24)"><img
					   src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif"
					   alt="Fecha" width="16" height="21" border="0"
					   onClick="javascript:Valores('<%=request.getContextPath()%>')">
                                        </a>
				    </td>
                                 </tr>
			    </table>
			  </td>
                        </tr>
		     </table>
                 </td>
	     </tr>
             <tr>
		 <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif"
			  width="20" height="15">
                 </td>
		 <td>Oficina de Procedencia</td>
             </tr>
             <!-- EL HELPER DE OFICINA EMPIEZA ACA -->
		 <jsp:include page="../registro/HELPER_OFICINAS.jsp" flush="true" />
	     <!-- EL HELPER DE OFICINA TERMINA ACA -->
          </table>

<%
}
%>

            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
              <tr> 
                <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> 
                  Datos del Solicitante</td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
              </tr>
            </table>
            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="camposform">
              <tr> 
                <td> 
	            <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">                
                    <table width="100%" class="camposform">
                      <tr> 
                        <td width="130">Tipo de Identificaci&oacute;n</td>
                        <td> <% try {
                      
                                    if(docs == null){
		                      			docs = new Vector();
                                    }
                                    
		               				selected = (String)session.getAttribute(CCiudadano.TIPODOC);
		               				if(selected==null){
		               					selected = CCiudadano.TIPO_DOC_ID_SECUENCIA;
		               				}    
									docHelper.setOrdenar(false);                                
                                    docHelper.setTipos(docs);                                    
                                    docHelper.setNombre(CCiudadano.TIPODOC);
                                    docHelper.setId(CCiudadano.TIPODOC);                                    
                                    docHelper.setCssClase("camposformtext");
                                    docHelper.setSelected(selected);
                                    docHelper.render(request,out);
			        }
				catch(HelperException re){
                                    out.println("ERROR " + re.getMessage());}%> </td>
                        <td width="70">N&uacute;mero</td>
                        <td><input name="<%= CCiudadano.DOCUMENTO%>" type="text" class="camposformtext" id="NUMERI_ID6" value="<%=documento%>"></td>
                      </tr>
                      <tr> 
                        <td>Primer Apellido / Raz&oacute;n Social</td>
                        <td><input name="<%= CCiudadano.APELLIDO1 %>" type="text" class="camposformtext" id="P_APELLIDO6" value="<%=ap1%>"></td>
                        <td>Segundo Apellido</td>
                        <td><input name="<%= CCiudadano.APELLIDO2 %>" type="text" class="camposformtext" id="S_APELLIDO6" value="<%=ap2%>"></td>
                      </tr>
                      <tr> 
                        <td>Nombre</td>
                        <td><input name="<%= CCiudadano.NOMBRE %>" type="text" class="camposformtext" id="NOMBRE6" value="<%=nom%>"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4"><hr class="linehorizontal"></td>
                      </tr>
                      <!--
                        @author: David Panesso
                        @change: Caso 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF. Se inactiva la opción de Imprimir nota informativa.
                      -->
                      <%--<%if(tipoCertificado.equals(CTipoCertificado.TIPO_EXENTO_NOMBRE)) {%>
                      	<tr>
                      	<td>Imprimir nota informativa 
                      	<td><input type="checkbox" id="<%=AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS%>" name="<%=AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS%>" value="<%=AWCertificadoMasivo.NOTA_INF_CERTIFICADOS_MASIVOS%>"></td>
                      	<td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <%} %>--%>
                    </table>
                    <table width="100%" class="camposform">
                      <tr> 
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                        <td><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                      </tr>
                    </table>
                 </td>
              </tr>
            </table>
           </form></td>
          <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
    <% 
		try
		{ 
		  //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		  //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		  //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		  helper.setNombreFormulario("SOLICITUD");		
		  helper.render(request,out);
		}
		catch(HelperException re)
		{
			out.println("ERROR " + re.getMessage());
		}	
		%>       


      
</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn004.gif">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr> 
      <td>&nbsp;</td>
      <td><img name="tabla_gral_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c1.gif" width="12" height="20" border="0" alt=""></td>
      <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn005.gif">&nbsp;</td>
      <td><img name="tabla_gral_r3_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r3_c5.gif" width="12" height="20" border="0" alt=""></td>
      <td>&nbsp;</td>
  </tr>
</table>
