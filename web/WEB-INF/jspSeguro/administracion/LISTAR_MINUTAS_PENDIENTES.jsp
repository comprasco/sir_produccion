<%@page import="java.util.*"%>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWConsultasReparto" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="gov.sir.core.negocio.modelo.Minuta" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWReportes" %>
<%@page import="gov.sir.core.util.DateFormatUtil" %>



<%
	TextHelper textHelper = new TextHelper(); 
	Hashtable turnos = (Hashtable)session.getAttribute(WebKeys.TABLA_MINUTAS);
	
	if (turnos == null){
		turnos = new Hashtable();
	}

	List minutasNormales = new ArrayList(); 
	List minutasExtraordinarias = new ArrayList();

	Enumeration keys = turnos.keys();
    while(keys.hasMoreElements()){
    	String idTurno = (String)keys.nextElement();
    	Minuta minuta = (Minuta)turnos.get(idTurno);
    	if(minuta.isNormal()){
    		minutasNormales.add(idTurno);
    	}else{
    		minutasExtraordinarias.add(idTurno);
    	}
    }
    
    Collections.sort(minutasNormales);
    Collections.sort(minutasExtraordinarias);
%>
<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/jsp/plantillas/calendario.js"></script>
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}
function verDetallesMinuta(nombre,valor,dimensiones)
{
	popup=window.open(nombre+'?<%=CTurno.ID_TURNO%>='+valor,'Detalles',dimensiones);
	popup.focus();
}
</script>
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Consultar Minutas Pendientes de Reparto</td>
          <td width="28"><img name="tabla_gral_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c3.gif" width="28" height="23" border="0" alt=""></td>
        </tr>
      </table></td>
    <td width="12"><img name="tabla_gral_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_gral_r1_c5.gif" width="12" height="23" border="0" alt=""></td>
    <td width="12">&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn003.gif">&nbsp;</td>
    <td class="tdtablaanexa02">
    <br>
 


	    <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td width="7"><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral"><B>Minutas Pendientes de Reparto</B></td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><span class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></span></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td width="11"><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>

          <tr>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            	
            	
            	<table width="100%" class="camposform">
               
               
               <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td><B>Listado de turnos para reparto ORDINARIO</B></td>
                  <td width="80" align="left">Ver Detalles</td>
                </tr>
       			<% 
				Iterator it = minutasNormales.iterator();
                while(it.hasNext()){
                	String dato = (String)it.next();	
                %>            
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%= dato%></td>
                 <td align="center">
                  		<a href="javascript:verDetallesMinuta('admin.detalles.minuta.view','<%=dato%>','scrollbars=yes,width=800,height=600,menubar=no')"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a> 
                  	</td>
                </tr>
              <% 
                } 
                %> 
            </table>
            
            
            <BR>
            
			<table width="100%" class="camposform">
               
               
               <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td><B>Listado de turnos para reparto EXTRAORDINARIO</B></td>
                  <td width="80" align="left">Ver Detalles</td>
                </tr>
       			<% 
				it = minutasExtraordinarias.iterator();
                while(it.hasNext()){
                	String dato = (String)it.next();	
                %>            
                <tr>
                  <td>&nbsp;</td>
                  <td class="campositem"><%= dato%></td>
                 <td align="center">
                  		<a href="javascript:verDetallesMinuta('admin.detalles.minuta.view','<%=dato%>','scrollbars=yes,width=800,height=600,menubar=no')"><image src="<%=request.getContextPath()%>/jsp/images/btn_mini_verdetalles.gif" width="35" height="13" border="0"></a> 
                  	</td>
                </tr>
              <% 
                } 
                %> 
            </table>            
            

			<form action="consultasReparto.do" method="POST" name="BUSCAR" id="BUSCAR">
			<input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>">

			 <table width="100%" class="camposform">
               <tr>
                  <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_lista.gif" width="20" height="15"></td>
                  <td width="20%" align='right'><B>Fecha Inicial:</B></td>
                  <td width="25%" align="left">
					
                    <%
				    Date today;
				    String fechaAct;

				    today = new Date();
				    fechaAct = DateFormatUtil.format(today);                    
                    

                    try {

                      if( null == session.getAttribute( gov.sir.core.web.acciones.administracion.AWReportes.REPORTE_11__PARAM_PFECHAINI ) ) {
                        session.setAttribute( AWReportes.REPORTE_110__PARAM_PFECHAINI, fechaAct );
                      }

	                  	textHelper.setId( AWReportes.REPORTE_110__PARAM_PFECHAINI);
	                    textHelper.setNombre( AWReportes.REPORTE_110__PARAM_PFECHAINI);
	                  	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
					}
					catch(org.auriga.core.web.HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			  		%>
			  		<a href="javascript:NewCal('<%=AWReportes.REPORTE_110__PARAM_PFECHAINI%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>			  		
					
                  </td>
                  <td width="20%" align='right'><B>Fecha Final:</B></td>
                  <td width="25%" align="left">
                    <%
                    try {

                      if( null == session.getAttribute( gov.sir.core.web.acciones.administracion.AWReportes.REPORTE_11__PARAM_PFECHAINI ) ) {
                        session.setAttribute( AWReportes.REPORTE_110__PARAM_PFECHAFIN, fechaAct );
                      }

	                  	textHelper.setId( AWReportes.REPORTE_110__PARAM_PFECHAFIN);
	                    textHelper.setNombre( AWReportes.REPORTE_110__PARAM_PFECHAFIN);
	                  	textHelper.setCssClase("camposformtext");
						textHelper.render(request,out);
					}
					catch(org.auriga.core.web.HelperException re){
						out.println("ERROR " + re.getMessage());
					}

			  		%>
			  		<a href="javascript:NewCal('<%=AWReportes.REPORTE_110__PARAM_PFECHAFIN%>','ddmmmyyyy',true,24)"><img src="<%=request.getContextPath()%>/jsp/images/ico_calendario.gif" alt="Fecha" width="15" height="21" border="0" onClick="javascript:Valores('<%=request.getContextPath()%>/')"></a>
                  </td>
                  <td width="35%" align='center'>
                  	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsultasReparto.MOSTRAR_REPORTE_PENDIENTES %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_imprimir.gif" width="139" height="21" border="0">
                  </td>
                </tr>           
           
            </table>   
            
            	<br>  


                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td>
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWConsultasReparto.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                  </tr>
               </table>
             </FORM>
            </td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
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