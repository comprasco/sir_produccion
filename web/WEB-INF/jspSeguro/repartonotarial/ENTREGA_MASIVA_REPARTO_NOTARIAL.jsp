<%@page import="gov.sir.core.negocio.modelo.constantes.*"%>
<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys"%>
<%@page import="gov.sir.core.negocio.modelo.OtorganteNatural"%>
<%@page import="gov.sir.core.negocio.modelo.EntidadPublica"%>
<%@page import="gov.sir.core.negocio.modelo.Turno"%>
<%@page import="gov.sir.core.negocio.modelo.SolicitudRepartoNotarial"%>
<%@page import="gov.sir.core.negocio.modelo.Minuta"%>
<%@page import="gov.sir.core.negocio.modelo.MinutaEntidadPublica"%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<%
	java.util.Map turnos = (Map) request.getSession().getAttribute(WebKeys.TURNOS);
%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/common.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.REPARTONOTARIAL.ACCION.value = text;
	document.REPARTONOTARIAL.submit();
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
                <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"> Minuta </td>
                <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="<%=request.getContextPath()%>/jsp/images/ico_new.gif" width="16" height="21"></td>
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
            <form action="repartoNotarial.do" method="post" name="REPARTONOTARIAL" id="REPARTONOTARIAL">

				<input type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION%>">		            
				
				
              <br>
              
              
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Notaria Asignada</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>
              
		<%
		 // Iterate over the keys in the map
	    Iterator it = turnos.keySet().iterator();
	    while (it.hasNext()) {
	        // Get key
	        Turno turno = (Turno)it.next();
	        gov.sir.core.negocio.modelo.SolicitudRepartoNotarial solicitud = (SolicitudRepartoNotarial) turno.getSolicitud();	
	    	gov.sir.core.negocio.modelo.Minuta minuta = solicitud.getMinuta();
	    	if(minuta==null){
	    		minuta = new Minuta();
	    	}
	    	
	    	String tipoRadicacion = "";
	    	if(minuta.isNormal()){
	    		tipoRadicacion= CMinuta.ORDINARIO;
	    	}else{
	    		tipoRadicacion= CMinuta.EXTRAORDINARIO;	
	    	}
		%>


			<table width="100%" class="camposform">
				<tr>
				<td>Cód Notaria</td>
				<td class="campositem"><%=minuta.getOficinaCategoriaAsignada()!=null&&minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null?minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getIdOficinaOrigen():""%>&nbsp;</td>
				<td>Notaria</td>
				<td class="campositem"><%=minuta.getOficinaCategoriaAsignada()!=null&&minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null?minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNombre():""%>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				</tr>
			</table>                  
              
              <br>
              
                      
               
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Datos 
                    Solicitud</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>     
              
              <table width="100%" class="camposform">
                <tr> 
                  
                  <td width="20%"><strong>Tipo de radicación</strong></td>
                  <td  class="campositem"><strong><%=tipoRadicacion%></strong>
					</td>
                </tr>
              </table>                  
              
              <table width="100%" class="camposform">
                <tr> 
                 
                  <td width="15%">Acción notarial</td>
                  <td width="35%" class="campositem"><%//minuta.getAccionNotarial()!=null&&minuta.getAccionNotarial().getNombre()!=null?minuta.getAccionNotarial().getNombre():"&nbsp;"%>

					</td>
                  <td width="15%" align='right'>No folios.</td>
                  <td width="35%" class="campositem"><%=minuta.getNumeroFolios()%>
                  </td>
                </tr>
              </table>

			 
        
              <table width="100%" class="camposform">
                <tr>
                  
                  <td width="15%">Cuant&iacute;a</td>
                  <td  width="35%" align='left' class="campositem"><%=java.text.NumberFormat.getInstance().format(minuta.getValor()) %>
                  </td>
                  
                  <td width="15%" align='right'>Unidades</td>
                  <td width="35%" class="campositem"><%="" + minuta.getUnidades()%>
                  </td>                  

                </tr>
                
                
                
                <tr>
                  
                  <td width="15%" >Observaciones</td>
                  <td  width="85%" align='left' colspan='3' class="campositem"><%=minuta.getComentario()!=null?minuta.getComentario():""%>&nbsp;
                  </td>

                </tr>                
                
              </table>

              <!--ENTIDADES PUBLICAS.-->			
			  <br>	
			  <br>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                  
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Entidad p&uacute;blica relacionada</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



				<%
				List entidadesCargadas = minuta.getEntidadesPublicas();
	
				if(entidadesCargadas!=null && entidadesCargadas.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itEntidades = entidadesCargadas.iterator();				
					while(itEntidades.hasNext()){						
						MinutaEntidadPublica minutaEntidadPublica = (MinutaEntidadPublica) itEntidades.next();
						EntidadPublica entidadPublica = minutaEntidadPublica.getEntidadPublica();
				%>		
						<tr>
						
						<td class="titulotbcentral"><%=(entidadPublica!=null && entidadPublica.getNombre()!=null ? entidadPublica.getNombre(): "&nbsp;")%></td>
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} else{
				%>
					<table width="100%" class="camposform">
						<tr>
						
						<td class="titulotbcentral">No hay entidades públicas asociadas.</td>
						</tr>
					</table>
				<%
				} 
				%>				






     		  <!--OTORGANTES-->
              <br>
              <br>
        
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                <tr> 
                 
                  <td background="<%=request.getContextPath()%>/jsp/images/sub_bgn001.gif" class="bgnsub">Persona natural o jurídica</td>
                  <td width="16" class="bgnsub"></td>
                  <td width="15"> <img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                </tr>
              </table>



				<%
				List otorgantes = minuta.getOtorgantesNaturales();

     			if(otorgantes!=null && otorgantes.size()>0){
				%>
					<table width="100%" class="camposform">
				<%
					int a = 0;
					Iterator itOtorgantes = otorgantes.iterator();				
					while(itOtorgantes.hasNext()){
						OtorganteNatural otorgante = (OtorganteNatural) itOtorgantes.next();
				%>		
						<tr>
						
						<td class="titulotbcentral"><%=(otorgante!=null && otorgante.getNombre()!=null ? otorgante.getNombre(): "&nbsp;")%></td>
						</tr>
				<%  a++;
					}
				%>
					</table>
				<%
				} else{
				%>
					<table width="100%" class="camposform">
						<tr>
						
						<td class="titulotbcentral">No hay otorgantes asociados.</td>
						</tr>
					</table>
				<%
				} 
				%>				
       
       
        
		<%}%>                    
              

			  <br>	
              <hr class="linehorizontal">
              <table width="100%" class="camposform">
                <tr> 
                  
                  <td> 
					<a href="javascript:cambiarAccion('<%=gov.sir.core.web.acciones.repartonotarial.AWRepartoNotarial.NOTIFICAR_CIUDADANO_EXITO_MASIVA%>')"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0"></a>                  
                  </td>                  
                  
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
</table>