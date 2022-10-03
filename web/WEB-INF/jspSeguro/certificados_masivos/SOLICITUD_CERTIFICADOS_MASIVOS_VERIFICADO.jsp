<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="org.auriga.core.web.*, org.auriga.core.modelo.transferObjects.Rol"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.MatriculaHelper"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.Vector"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%   
  ListaElementoHelper docHelper = new ListaElementoHelper(); 
  List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
  List copias = new ArrayList();
  
  ListaElementoHelper certHelper = new ListaElementoHelper();
  MatriculaHelper matHelper = new MatriculaHelper(); 
  TextHelper textHelper = new TextHelper();
  NotasInformativasHelper helper = new NotasInformativasHelper();
  TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();
  
  String selected = "", documento = "", tipoCertificado = "", rol = "", nom = "", ap1 = "", ap2 = "";
  boolean exento = true;
  
  if ( session.getAttribute(WebKeys.ROL) != null ) {
    Rol rolClass = (Rol) session.getAttribute(WebKeys.ROL);
    rol = rolClass.getNombre();
    
    if ( rol != null ) {
      rol = rol.trim();
    }
  }
  
  
  String tipoTarifa = "";
  if ( rol.equalsIgnoreCase("CAJERO") ) {
    tipoCertificado = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
    tipoTarifa = CTipoTarifa.NORMAL;
    session.setAttribute("ID_TIPO_CERTIFICADO", CTipoCertificado.TIPO_INMEDIATO_ID);
    exento = false;
  }
  else {
    tipoCertificado = CTipoCertificado.TIPO_INMEDIATO_NOMBRE;
    tipoTarifa = CTipoTarifa.EXENTO;    
    session.setAttribute("ID_TIPO_CERTIFICADO", CTipoCertificado.TIPO_INMEDIATO_ID);  }
  
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
          <input type="hidden" name="ACCION" value="ELIMINAR_VERIFICADO">
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
                <td> <form name="frmAddMatricula" method="post" action="turnoLiquidacionCertificadosMasivos.do">
	                  <input type="hidden" name="ACCION" value="AGREGAR_VERIFICADO">
    			      <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">                    
                    <% if ( exento ) { %>
                    <table width="100%" class="camposform">
                      <tr> 
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                        <td>Adicionar Matr&iacute;cula</td>
                      </tr>
                      <tr> 
                        <td width="20">&nbsp;</td>
                        <td> <table width="100%">
                            <tr> 
                              <td height="31"> <input name="ID_MATRICULA" type="text" class="camposformtext"> 
                              </td>
                              <td height="31" style="font-style:normal; font-size:xx-small;">No. Copias:
                                <input name="COPIAS" type="text" class="camposformtext" size="5" value="1" readonly="true">
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
                <td> <form name="SOLICITUD" method="post" action="turnoLiquidacionCertificadosMasivos.do">
                <input type="hidden" name="ACCION" value="LIQUIDAR">
	            <input type="hidden" name="<%=CTipoTarifa.ID_TIPO_TARIFA%>" id="<%=CTipoTarifa.ID_TIPO_TARIFA%>" value="<%=tipoTarifa%>">                
                    <table width="100%" class="camposform">
                      <tr> 
                        <td width="130">Tipo de Identificaci&oacute;n</td>
                        <td> <% try {
                      
                                    if(docs == null){
                      			docs = new Vector();
                                    }
									docHelper.setOrdenar(false);
                                    docHelper.setTipos(docs);
                                    docHelper.setNombre(CCiudadano.TIPODOC);
                                    docHelper.setCssClase("camposformtext");
                                    docHelper.setId(CCiudadano.TIPODOC);
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
                    </table>
                    <table width="100%" class="camposform">
                      <tr> 
                        <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                        <td><input type="image" src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" name="Folio" width="139" height="21" border="0" id="Folio"></a></td>
                      </tr>
                    </table>
                  </form></td>
              </tr>
            </table></td>
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
