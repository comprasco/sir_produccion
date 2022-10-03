<%@page import ="java.util.*"%>
<%@page import ="gov.sir.core.web.WebKeys"%>
<%
	Hashtable resultadoReparto = (Hashtable)request.getSession().getAttribute(WebKeys.LISTA_OBSERVACIONES_REPARTO_NOTARIAL);

	if (resultadoReparto==null){
		resultadoReparto = new Hashtable();
	}

%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td class="tdtablaanexa02">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Notarias</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" class="camposform">
                <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td>Resultados</td>
                  <td>&nbsp;</td>
                </tr>
            </table>
            
            
              <%              
              if(resultadoReparto.size()>0){
				  Enumeration llaves = resultadoReparto.keys();
				  while(llaves.hasMoreElements())
				  {
				  	String llave = (String)llaves.nextElement();
				  	Hashtable reparto = (Hashtable)resultadoReparto.get(llave);
              %>
              <table width="100%" class="camposform">
                <tr>
                  <td class="titulotbcentral">Circulo Notarial: </td>
                  <td class="titulotbcentral"><%=llave%></td>
              </table>
              <table width="100%" class="camposform">
                <tr>
                
                  <td class="titulotbcentral">Turnos</td>
                  <td class="titulotbcentral">Notarias</td>
                </tr>
                <%
                Enumeration llavesReparto = reparto.keys();
                while (llavesReparto.hasMoreElements()) {			    
			    	String llaveReparto = (String) llavesReparto.nextElement();    	
			    	String mensaje = (String) reparto.get(llaveReparto);		    	
                %>
                <tr>
                  
                  <td class="campositem"><%=llaveReparto!=null?llaveReparto:" - "%>&nbsp;</td>
                  <td class="campositem"><%=mensaje!=null?mensaje:" - "%>&nbsp;</td>
                </tr>                
                <%
                }
                }
                %>
              </table>            

              <%
              }else{
              %>
              <table width="100%" class="camposform">
                <tr> 
                  <td colspan='3'><b>No se repartió ninguna minuta.</b></td>
                </tr>
              </table>                   
              
              <%
              }
              %>                 
            
            

              <hr class="linehorizontal">
	       	<form name="REGRESAR" method="post" action="procesos.view	">                  
              <table width="100%" class="camposform">
                <tr>
                
	                  <td width="155"><a href="javascript:document.REGRESAR.submit();"><img src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border='0'></a></td>
                  <td>&nbsp;</td>
                </tr>
              </table>
			</form>		              
              </td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
    </table></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 
</table>
