<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.helpers.comun.*"%>
<%@page import="java.util.* "%>
<% 
    MatriculaViewHelper matViewHelper = new MatriculaViewHelper();        
	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);	
	Solicitud solicitud = (Solicitud)turno.getSolicitud();
	if(solicitud==null){
		solicitud = new Solicitud();
	}


	Turno turnoAnterior = solicitud.getTurnoAnterior();
	if(turnoAnterior==null){
		turnoAnterior = new Turno();
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

	String ocultar = request.getParameter(WebKeys.OCULTAR);	
	if(ocultar == null){
		ocultar = (String)session.getAttribute(WebKeys.OCULTAR);
		if(ocultar==null){
			ocultar = "TRUE";
		}
	} else {
		session.setAttribute(WebKeys.OCULTAR,ocultar);
	}
	
%>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%= request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
        </tr>
        <tr>
        <td><img name="tabla_central_r1_c1" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Pago por mayor valor</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%= request.getContextPath()%>/jsp/images/ico_certificado.gif" width="16" height="21"></td>
                        </tr>
                    </table>
                </td>
                <td width="12"><img name="tabla_central_r1_c5" src="<%= request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table>
        </td>
        <td><img name="tabla_central_pint_r1_c7" src="<%= request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
        </tr>
		
		
        <tr>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td align="right" class="tdtablacentral">
        <table border="0" cellpadding="0" cellspacing="2">
            <tr>
		<%if(ocultar.equals("FALSE")){%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR%>" value="TRUE">
                <td width="16"><input name="MINIMIZAR" type="image" id="MINIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_minimizar.gif" width="16" height="16" border="0"></td>
            </form>
						
		<%}else{%>
            <form action="<%=session.getAttribute(org.auriga.smart.SMARTKeys.VISTA_ACTUAL)%>" method="post" type="submit">
                <input type="hidden" name="<%=WebKeys.OCULTAR%>" value="FALSE">
                <td width="170" class="contenido">Haga click para maximizar</td>
                <td width="16"><input name="MAXIMIZAR" type="image" id="MAXIMIZAR" src="<%= request.getContextPath()%>/jsp/images/btn_maximizar.gif" width="16" height="16" border="0"></td>
            </form>
		<%}%>
            </tr>
        </table>
        </td>
        <td background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
		
	
        <tr> 
        <td width="7" background="<%= request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
        <td valign="top" bgcolor="#79849B" class="tdtablacentral">
			
				
        <!--aca-->
	<%if(ocultar.equals("FALSE")){%>
        <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Matr&iacute;cula Inmobiliaria de la Propiedad</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_matriculas.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_editar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                  <tr>
	                  <td>              
	                    <%try {
	                  		    matViewHelper.setFolios(solicitud.getSolicitudFolios());
	                  		    matViewHelper.render(request,out);	                  		    	                  		    	                  		    	                  		    
							}
								catch(Exception re){
								out.println("ERROR " + re.getMessage());
							}
						%>  
					</td>
                </tr>					
              </table>        
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Turno Anterior</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_turno_02.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td>&iquest; Est&aacute; asociado a un turno anterior ?</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>N&uacute;mero del Turno Anterior</td>
                  <td class="campositem"><%=turnoAnterior.getIdWorkflow()!=null?turnoAnterior.getIdWorkflow():""%>&nbsp;</td>
                </tr>
              </table>
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
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos Solicitante</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_datosuser.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
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
			

		<%}%>

        </td>
        <td width="11" background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
        </tr>
        <tr> 
        <td><img name="tabla_central_r3_c1" src="<%= request.getContextPath() %>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
        <td background="<%= request.getContextPath() %>/jsp/images/tabla_central_bgn006.gif"><img src="<%= request.getContextPath() %>/jsp/images/spacer.gif" width="15" height="6"></td>
        <td><img name="tabla_central_pint_r3_c7" src="<%= request.getContextPath() %>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
    </table>