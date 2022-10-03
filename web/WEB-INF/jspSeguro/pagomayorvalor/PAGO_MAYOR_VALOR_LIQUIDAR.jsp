<%@page import="gov.sir.core.negocio.modelo.constantes.*,gov.sir.core.negocio.modelo.*,org.auriga.core.web.*,gov.sir.core.negocio.modelo.TipoActo,gov.sir.core.negocio.modelo.TipoImpuesto,gov.sir.core.web.WebKeys,gov.sir.core.web.helpers.comun.TextHelper, gov.sir.core.web.helpers.registro.ActosHelper,gov.sir.core.web.helpers.comun.ElementoLista,java.util.List,java.util.Vector,java.util.ArrayList,gov.sir.core.web.helpers.comun.ListaElementoHelper"%>
<% ListaElementoHelper tipoActoHelper = new ListaElementoHelper();
   ListaElementoHelper tipoDerechoHelper = new ListaElementoHelper();
   ListaElementoHelper tipoTarifaHelper = new ListaElementoHelper();
   ListaElementoHelper tipoIdHelper= new ListaElementoHelper();
   ActosHelper actoHelper = new ActosHelper();
   TextHelper textHelper = new TextHelper();
   
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	SolicitudCorreccion solicitud = (SolicitudCorreccion) turno.getSolicitud();
	
	if(solicitud==null){
		solicitud = new SolicitudCorreccion();
	}	   
	Ciudadano ciudadano = solicitud.getCiudadano();
	if(ciudadano==null){
		ciudadano = new Ciudadano();
	}		

	List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
   	if(docs == null){
		docs = new Vector();
    }
    java.util.Iterator it = docs.iterator();
    String tipdoc = "DOCUMENTO IDENTIDAD";
    while (it.hasNext()) { 
       ElementoLista el = (ElementoLista) it.next();
       
       if(el.getId().equals(ciudadano.getTipoDoc())){
       		tipdoc = el.getValor();       
       }
    }	
   %>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script>
       function cambiarAccion(text) {
       document.PAGOMAYORVALOR.ACCION.value = text;
       document.PAGOMAYORVALOR.submit();
       }
		
       function quitar(text) {
       document.PAGOMAYORVALOR.ITEM.value = text;
       cambiarAccion('ELIMINAR_ACTO');
       }

</script>
<link href="<%= request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
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
    <td width="12"><img name="tabla_gral_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c1.gif" width="12" height="23" border="0" alt=""></td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pago por Mayor Valor</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%= request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02"><table border="0" cellpadding="0" cellspacing="0" width="100%">
        <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
        <tr>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10">              </td>
          <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
          <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Liquidaci&oacute;n</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                    </tr>
                </table></td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
              </tr>
          </table></td>
          <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
        <tr>
          <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
          <td valign="top" bgcolor="#79849B" class="tdtablacentral"><form action="turnoLiquidacionPagoMayorValor.do" method="post" name="PAGOMAYORVALOR" id="PAGOMAYORVALOR">
				<input type="hidden" name="ACCION" value="LIQUIDAR">
              
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Actos </td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_acto.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              
              <input type="hidden" name="ITEM" value="NINGUNO">
              <% try {
                  			    actoHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
              
              
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Nuevo Acto</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_acto.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Acto</td>
                </tr>
                <tr>
                  <td width="20">&nbsp;</td>
                  <td><table width="100%" class="camposform">
                    <tr>
                      <td>Tipo de Acto </td>
                      <td>
                      
                      <% try {
                      
                      			List tipos = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ACTO) ;
                   				List elem=new ArrayList();
                   				if(tipos!=null){

								for (int i=0; i<tipos.size(); i++){
									TipoActo t=(TipoActo)tipos.get(i);
									elem.add(new ElementoLista(t.getIdTipoActo(),t.getNombre()));
								}
                  				}
                  				tipoActoHelper.setTipos(elem);          				
                  				
                  			    tipoActoHelper.setNombre(CActo.TIPO_ACTO);
				                tipoActoHelper.setCssClase("camposformtext");
                  			    tipoActoHelper.setId(CActo.TIPO_ACTO);
                  			    tipoActoHelper.setFuncion("onChange=\"cambiarAccion('CARGAR_DERECHOS')\"");
                  			    tipoActoHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                      
                      </td>
                      <td>Tipo de Tarifa de Derechos Registrales </td>
                      <td>
                      
                      <% try {
                      			List derechos=(List)session.getAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES");
                      			if (derechos!=null && derechos.size()>0){
	                      			tipoDerechoHelper.setTipos(derechos);
                      			} 
                      			
                  			    tipoDerechoHelper.setNombre(CActo.TIPO_DERECHO);
				                tipoDerechoHelper.setCssClase("camposformtext");
                  			    tipoDerechoHelper.setId(CActo.TIPO_DERECHO);
                  			    tipoDerechoHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                      
                      </td>
                    </tr>
                    <tr>
                      <td>Valor del Acto </td>
                      <td>
                      
                      <% try {
 		                        textHelper.setNombre(CActo.VALOR_ACTO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CActo.VALOR_ACTO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                      
                      </td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%= request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td>Se Cobra Impuesto ?</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                    <tr>
                      <td width="20">SI</td>
                      <td><input name="COBRA_IMPUESTO" type="radio" value="SI"></td>
                      </tr>
                    <tr>
                      <td>NO</td>
                      <td><input name="COBRA_IMPUESTO" type="radio" value="NO"></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Impuestos</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><table width="100%" class="camposform">
                    <tr>
                      <td>&nbsp;</td>
                      <td>Tipo de Tarifa Impuesto </td>
                      <td>
                      
                      <% try {
                      			List tipoImp = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO);
                      			
                      			List elem=new ArrayList();
                   				if(tipoImp!=null){

								for (int i=0; i<tipoImp.size(); i++){
									 TipoImpuesto t=(TipoImpuesto)tipoImp.get(i);
									elem.add(new ElementoLista(t.getIdTipoImpuesto(),t.getNombre()));
								}
                  				}
                  				tipoTarifaHelper.setTipos(elem);
                  			    tipoTarifaHelper.setNombre(CActo.TIPO_TARIFA);
				                tipoTarifaHelper.setCssClase("camposformtext");
                  			    tipoTarifaHelper.setId(CActo.TIPO_TARIFA);
                  			    tipoTarifaHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                      
                      </td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td align="right"><table  border="0" class="camposform">
                    <tr align="center">
                      <td>Agregar</td>
                    </tr>
                    <tr align="center">
                      <td><img name="imageField" src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0" onClick="cambiarAccion('AGREGAR_ACTO')"></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Otro Impuesto</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_pago.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Valor</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre(CActo.VALOR_OTRO_IMPUESTO);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CActo.VALOR_OTRO_IMPUESTO);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                  <td>Descripci&oacute;n</td>
                  <td>
                  
                  <% try {
 		                        textHelper.setNombre(CActo.DESCRIPCION);
                  			    textHelper.setCssClase("camposformtext");
                  			    textHelper.setId(CActo.DESCRIPCION);
								textHelper.render(request,out);
							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
						%>
                  
                  </td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%= request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%= request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%= request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211"  class="campositem"><%=tipdoc%>&nbsp;</td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212"  class="campositem"><%=ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Primer Apellido</td>
                  <td  class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
                  <td>Segundo Apellido</td>
                  <td  class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Nombre</td>
                  <td  class="campositem"><%=ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Liquidar Valor</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><input name="imageField" type="image" src="<%= request.getContextPath()%>/jsp/images/btn_liquidar.gif" width="139" height="21" border="0" onClick="cambiarAccion('LIQUIDAR')"></td>
                </tr>
              </table>
                                      </form></td>
          <td width="11" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr>
          <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
          <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
          <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
      </table>
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