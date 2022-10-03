<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %> 
<%@page import="gov.sir.core.negocio.modelo.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper"%>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper"%>
<%@page import="gov.sir.core.negocio.modelo.constantes.CSolicitudRestitucionReparto"%>
<%@page import="gov.sir.core.web.acciones.restitucionreparto.AWRestitucionReparto"%>


<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">


<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script>
function cambiarAccion(text) {
    document.RESTITUCION.<%=gov.sir.core.web.WebKeys.ACCION%>.value = text;
    document.RESTITUCION.submit();
}
function send(){
   document.forma.submit();
}
</script>
<%
	List turnosARestituir =(List)session.getAttribute(WebKeys.LISTA_TURNOS_RESTITUCION_NOTARIAL);

	List turnosARestituirTra =(List)session.getAttribute(WebKeys.LISTA_TURNOS_RESTITUCION_NOTARIAL_INFO);
	
	TextHelper textHelper = new TextHelper();    	
	TextAreaHelper textAreaHelper = new TextAreaHelper();  

	/*Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
	gov.sir.core.negocio.modelo.SolicitudRestitucionReparto solicitud = (gov.sir.core.negocio.modelo.SolicitudRestitucionReparto) turno.getSolicitud();	
	
	Turno turnoAnterior = 	solicitud.getTurnoAnterior();
	
	CausalRestitucion causal = 	solicitud.getCausalRestitucion();
	if(causal==null){
		causal = new CausalRestitucion();		
	}
	
    TextHelper textHelper = new TextHelper();    	
    TextAreaHelper textAreaHelper = new TextAreaHelper();    	
    TextAreaHelper observacionesSolicitudHelper = new TextAreaHelper();    */	    
%>
<%
if (turnosARestituirTra==null){
%>
	<body bgcolor="#CDD8EA"  onLoad="send()"   background="<%= request.getContextPath()%>/jsp/images/bgn_total_repeat.jpg" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	    <form action="restitucionReparto.do" method="POST" name="forma"  id="forma">
	  		<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION%>" value="<%=AWRestitucionReparto.CONSULTAR_TURNOS_ANALISIS_RESTITUCION%>">
		</form>
<%
//System.out.println("turnosARestituirTraes nullo ");
} else {
//System.out.println("turnosARestituirTra size " + turnosARestituirTra.size());}
%>


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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">An&aacute;lisis de Restituci&oacute;n de Minuta Notarial </td>
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
        
        
        <br>
        <tr> 
          <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"> <table border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">An&aacute;lisis de Restituci&oacute;n de Minuta Notarial</td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
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
            <form action="restitucionReparto.do" method="post" name="RESTITUCION" id="RESTITUCION">
		    <input type="hidden" name="<%=gov.sir.core.web.WebKeys.ACCION%>" value="">		                           
              <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Datos Solicitud Restitución</td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              <%
				Iterator itTurnos = turnosARestituirTra.iterator();
					//System.out.println("Se encontro turno 0");
					while (itTurnos.hasNext()) {
						Turno turno = (Turno) itTurnos.next();
						//System.out.println("Se encontro turno 1");
						gov.sir.core.negocio.modelo.SolicitudRestitucionReparto solicitud = (gov.sir.core.negocio.modelo.SolicitudRestitucionReparto) turno.getSolicitud();	
						//System.out.println("Se encontro turno 2");
						Turno turnoAnterior = 	solicitud.getTurnoAnterior();
						//System.out.println("Se encontro turno 3");
						CausalRestitucion causal = 	solicitud.getCausalRestitucion();
						if(causal==null){
							causal = new CausalRestitucion();		
						}
						//System.out.println("Se encontro turno 4");
	
						  	
						TextAreaHelper observacionesSolicitudHelper = new TextAreaHelper();   
						//System.out.println("LLEGO AAAAAAAAAAAAAAAAAAAAAAA");
					
				%>
              <table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
				  <td><input type="checkbox" name="TurnosRestitucion" id="TurnosRestitucion" value="<%=turno!=null&&turno.getIdWorkflow()!=null?turno.getIdWorkflow():""%>" checked></td>
                  <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="25%">Turno</td>
                  <td width="25%" align='left' class="campositem"><%=turno!=null&&turno.getIdWorkflow()!=null?turno.getIdWorkflow():""%></td>
       			  </tr>
                  <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="25%">Turno asociado</td>
                  <td width="25%" align='left' class="campositem"><%=turnoAnterior!=null&&turnoAnterior.getIdWorkflow()!=null?turnoAnterior.getIdWorkflow():""%></td>
                  </tr>
                  <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="25%">Causal de Restituci&oacute;n</td>
                      <%
                     	String causalRest = "";                      
                      	java.util.List causalesRestitucion = (java.util.List) session.getServletContext().getAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION);
                      	if(causalesRestitucion == null){
                      		causalesRestitucion = new java.util.ArrayList();
                      	}
                      	
                      	java.util.Iterator it = causalesRestitucion.iterator();
                      	while(it.hasNext()){
                      		CausalRestitucion causalTemp = (CausalRestitucion)it.next();
                      	
                      		if(causalTemp.getIdCausalRestitucion().equals(causal.getIdCausalRestitucion())){
                      			causalRest = causalTemp.getNombre(); 
                      		}                      			
                      	}
                      	
                      	//Observaciones solicitud de restitución
                        if (solicitud != null)
                        {
                            	String observacionesRadicacion = solicitud.getObservacionesRestitucion();
                            	session.setAttribute("OBSERVACIONES_RADICACION",observacionesRadicacion);
                        }
						%>                       
                  <td width="25%" align='left' class="campositem"><%=causalRest!=null?causalRest:""%>&nbsp;</td>
                </tr>
                </tr>
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="25%">Observaciones</td>
                  <td colspan='3'>
				  <%try 
				  { 
						observacionesSolicitudHelper.setNombre("OBSERVACIONES_RADICACION");
						observacionesSolicitudHelper.setCols("115");
						observacionesSolicitudHelper.setRows("5");
						observacionesSolicitudHelper.setCssClase("campositem");
						observacionesSolicitudHelper.setId("OBSERVACIONES_RADICACION");
						observacionesSolicitudHelper.setReadOnly(true);
						observacionesSolicitudHelper.render(request,out); 
				   }
				   catch(org.auriga.core.web.HelperException re)
				   {  out.println("ERROR " + re.getMessage());
				   }
				  %>
				</td>					
				</tr>
                
                
              </table>      

				<%
				}
				%>
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr>
                  <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub"> Resoluci&oacute;n </td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_resolucion.gif" width="16" height="21"></td>
                  <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                  <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              <br>
              
              <table width="100%" class="camposform">
                <tr>
                  <td width="20" valign="top"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                  <td width="120" align="left">Resolución :</td>                  
                  <td width="200" align="left">
	          <%
				try 
				{
					textHelper.setNombre(CSolicitudRestitucionReparto.RESOLUCION);
					textHelper.setCssClase("camposformtext");
					textHelper.setId(CSolicitudRestitucionReparto.RESOLUCION);
					textHelper.render(request,out);
				}
				
				catch(org.auriga.core.web.HelperException re)
				{
					out.println("ERROR " + re.getMessage());
				}                      
			  %>                      
				 </td>					
										
                 <td  width="120" align="left">Fecha resolución :</td>					
                    <td width="100" align="left">
	                        <%
							try {
								textHelper.setNombre(CSolicitudRestitucionReparto.FECHA_RESOLUCION);
								textHelper.setCssClase("camposformtext");
								textHelper.setId(CSolicitudRestitucionReparto.FECHA_RESOLUCION);
								textHelper.render(request,out);
							}
								catch(org.auriga.core.web.HelperException re){
								out.println("ERROR " + re.getMessage());
							}                      
							%>   
							</td>    
							<td><a href="javascript:NewCal('<%=CSolicitudRestitucionReparto.FECHA_RESOLUCION%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="16" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>')"></a>                    							               
							</td>
					</td>					
										
                </tr>
              </table>
              
              <table width="100%" class="camposform">
               <tr>
                 <td width="20" align="left"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                 <td width="120" align="left">Observaciones :</td> 
              <td width="350">
				<%try 
				{ 
					textAreaHelper.setNombre(CSolicitudRestitucionReparto.OBSERVACIONES_RESOLUCION);
					textAreaHelper.setCols("100");
					textAreaHelper.setRows("5");
					textAreaHelper.setCssClase("camposformtext");
					textAreaHelper.setId(CSolicitudRestitucionReparto.OBSERVACIONES_RESOLUCION);
					textAreaHelper.render(request,out); 
				}
				catch(org.auriga.core.web.HelperException re){
								out.println("ERROR " + re.getMessage());
				}
				%>
				
			</td>					
			<td>&nbsp;</td>
		  
                </tr>
              </table>              
              
              <br>
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr> 
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                  <td width="140"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.restitucionreparto.AWRestitucionReparto.ANALISIS_CONFIRMAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_aprobar.gif" width="139" height="21" border="0"></td>
                  <td width="140"><a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.restitucionreparto.AWRestitucionReparto.ANALISIS_NEGAR%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_negar.gif" width="139" height="21" border="0"></a></td>
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
<% } %>