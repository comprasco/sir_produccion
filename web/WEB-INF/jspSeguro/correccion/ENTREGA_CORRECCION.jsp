<%@page import="org.auriga.core.web.*,gov.sir.core.web.helpers.comun.*,java.util.*,gov.sir.core.negocio.modelo.*,gov.sir.core.negocio.modelo.constantes.*,gov.sir.core.web.WebKeys"%>
<%
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
	Solicitud solicitud = turno.getSolicitud();

    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	
	NotasInformativasHelper helper = new NotasInformativasHelper();    
	
/*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
	boolean activo = true;
	Turno turnoAnterior = solicitud.getTurnoAnterior();
	if(turnoAnterior==null){
		turnoAnterior = new Turno();
	}else
        {
            if(turnoAnterior.getIdProceso()==6)
            {
                activo = false;
            }
        }	
	Ciudadano ciudadano = solicitud.getCiudadano();
	if(ciudadano==null){
		ciudadano = new Ciudadano();
	}	
	
	List docs = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ID);
   	if(docs == null){
		docs = new Vector();
    }

    Iterator it = docs.iterator();
    String tipdoc = "DOCUMENTO IDENTIDAD";
    while (it.hasNext()) { 
       ElementoLista el = (ElementoLista) it.next();
       
       if(el.getId().equals(ciudadano.getTipoDoc())){
       		tipdoc = el.getValor();       
       }
    }
    
	List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	Iterator is = matriculas.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, idmatriculas);	

%>
<SCRIPT>
function cambiarAccion(text) {
    document.CORRECCION.<%=WebKeys.ACCION%>.value = text;
    document.CORRECCION.submit();
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Correcciones</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Entregar Correcci&oacute;n </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
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
            <form action="correccion.do" method="post" name="CORRECCION" id="CORRECCION">
		    <input type="hidden" name="<%=WebKeys.ACCION%>" value="APROBAR">		               
		    <input type="hidden" name="<%=gov.sir.core.web.acciones.correccion.AWCorreccion.DELEGAR_CASO%>" value="gov.sir.core.web.acciones.correccion.AWCorreccion.DELEGAR_CASO">		               		    
		    <input type="hidden" name="<%=CFolio.ID_MATRICULA%>" value="">				    
              <br>            
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                  <tr>
	                  <td>              
	                  <% try {
	                      tablaHelper.setColCount(5);
	                      tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                              tablaHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
	                  %>
					</td>
                </tr>					
              </table>
              <br>
              <%--
              /*
* @author : CTORRES
* @change : se agrega la variable activo
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 * */
              --%>
              <%if(!activo){%>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno de Testamento</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
                
              <table width="100%" class="camposform">
                <tr>
                  <td>&nbsp;</td>
                  <td>Turno de testamento</td>
                  <td class="campositem"><%=turnoAnterior.getIdTurno()!=null?turnoAnterior.getIdWorkflow():""%>&nbsp;</td>
                  </tr>
              </table>
              <%}else{%> 
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno Asociado</td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Est&aacute; asociado a un turno ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>Turno de documento objeto de correcci&oacute;n</td>
                  <td class="campositem"><%=turnoAnterior.getIdTurno()!=null?turnoAnterior.getIdTurno():""%>&nbsp;</td>
                </tr>
              </table>
                <%}%>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Datos de la Solicitud</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correcciones.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%
              	if(solicitud instanceof SolicitudCorreccion) {
              		SolicitudCorreccion solicitudCorreccion = (SolicitudCorreccion)solicitud;
			  %>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" height="18"><img src="<%= request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td class="contenido">Descripci&oacute;n</td>
                </tr>
                <tr>
                  <td height="18">&nbsp;</td>

                  	<td>
                 	<% try {                 	
                 				TextAreaHelper area = new TextAreaHelper();
								session.setAttribute(CTurno.DESCRIPCION,/*turno.getDescripcion()*/solicitudCorreccion.getDescripcion());
								area.setCols("90");
								area.setRows("10");
                 				area.setNombre(CTurno.DESCRIPCION);
                  			   	area.setCssClase("campositem");
                  			   	area.setId(CTurno.DESCRIPCION);
                  			   	area.setReadOnly(true);
								area.render(request,out);
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
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Correo </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_correo.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>Direcci&oacute;n de Correo</td>
                  <td width="45%" align='left' class="campositem"><%=solicitudCorreccion.getDireccionEnvio()!=null?solicitudCorreccion.getDireccionEnvio():""%>&nbsp;</td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Derecho de Petici&oacute;n </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getDerechoPeticion()==true?"SI":"NO"%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Correcci&oacute;n solicitada con anterioridad</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.isSolicitadaAnteriormente()?"SI":"NO"%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td class="bgnsub">Inter&eacute;s Jur&iacute;dico</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_derechodepeticion.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_radiobutton.gif" width="20" height="15"></td>
                  <td class="campositem"><%=solicitudCorreccion.getInteresJuridico()!=null?solicitudCorreccion.getInteresJuridico():""%></td>
                </tr>
              </table>
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%
              }
              %>              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <table width="100%" border="0" cellpadding="0" cellspacing="2" class="camposform">
                <tr>
                  <td width="179">Tipo de Identificaci&oacute;n</td>
                  <td width="211" class="campositem"><%=tipdoc%>&nbsp;</td>
                  <td width="146">N&uacute;mero</td>
                  <td width="212" class="campositem"><%=ciudadano.getDocumento()!=null?ciudadano.getDocumento():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Primer Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido1()!=null?ciudadano.getApellido1():""%>&nbsp;</td>
                  <td>Segundo Apellido</td>
                  <td class="campositem"><%=ciudadano.getApellido2()!=null?ciudadano.getApellido2():""%>&nbsp;</td>
                </tr>
                <tr>
                  <td>Nombre</td>
                  <td class="campositem"><%=ciudadano.getNombre()!=null?ciudadano.getNombre():""%>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
              </table>
              
              
   
        
              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="50"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.correccion.AWCorreccion.NOTIFICAR_CIUDADANO_EXITO%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a></td>                                    
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
		try{
 		    //SE USA LA SIGUIENTE LÍNEA PARA COLOCAR EL NOMBRE DEL FORMULARIO 
		    //DEL ACTUAL JSP, AL CUÁL SE LE DESEA GUARDAR LA INFORMACIÓN QUE EL USUARIO HA INGRESADO.
		    //SINO SE COLOCÁ SE PERDERÁ LA INFORMACIÓN. NO ES OBLIGATORIA PERO ES RECOMENDABLE COLOCARLA.
		    helper.setNombreFormulario("CORRECCION");			
			helper.render(request,out);
		}catch(HelperException re){
			out.println("ERROR " + re.getMessage());
		}	
		%>   

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
