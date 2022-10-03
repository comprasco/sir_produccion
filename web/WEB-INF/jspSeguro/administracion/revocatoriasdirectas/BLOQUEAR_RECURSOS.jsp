<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWRecursosRevocatorias" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.TextAreaHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.Turno" %>
<%@page import="gov.sir.core.negocio.modelo.Solicitud" %>
<%@page import="gov.sir.core.web.helpers.comun.TablaMatriculaHelper" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.negocio.modelo.SolicitudFolio" %>
<%
	Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);	
	TextHelper textHelper = new TextHelper();
	TextAreaHelper textAreaHelper = new TextAreaHelper();
	
    Solicitud solicitud = (Solicitud) turno.getSolicitud();
    TablaMatriculaHelper tablaHelper = new TablaMatriculaHelper();	

	if(solicitud==null){
		solicitud = new Solicitud();
	}
	
	java.util.List matriculas = solicitud.getSolicitudFolios();
	List idmatriculas = new ArrayList();
	
	Iterator is = matriculas.iterator();	
	while(is.hasNext()){
		SolicitudFolio sf=(SolicitudFolio)is.next();
		String temp=(String) sf.getFolio().getIdMatricula();
		idmatriculas.add(temp);
	}	
	session.setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO, idmatriculas);			

	String idCirculo = "";
	if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
		idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
	}
	
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
	document.BUSCAR.submit();
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Bloqueos de Folios</td>
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
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Folios a bloquear</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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
            
           		<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Folios asociados al turno</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                

				<% try {
	                  tablaHelper.setColCount(5);
	                  tablaHelper.setListName(WebKeys.LISTA_MATRICULAS_SOLICITUD_REGISTRO);
	           		  tablaHelper.render(request, out);
	                }
	                catch(HelperException re){
	                  out.println("ERROR " + re.getMessage());
	                }
				%>
             
                <BR>
                
                
                
		        <form action="recursosRevocatorias.do" method="POST" name="BUSCAR" id="BUSCAR">
			        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWRecursosRevocatorias.BLOQUEAR_RECURSOS%>" value="<%=  AWRecursosRevocatorias.BLOQUEAR_RECURSOS%>">
	                <!--<input type="hidden" name="ITEM" value="NINGUNO">-->
			        <!--<input type="hidden" name ="VER_RADICACION" id="VER_RADICACION">-->
		                           
	           		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
	                    <td class="bgnsub">Asociar nuevos folios para ser bloqueados</td>
	                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
	                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
	                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
	                  </tr>
	                </table>
                	<%
                	List foliosAAdicionar = (List)session.getAttribute(WebKeys.LISTA_FOLIOS);
                	if(foliosAAdicionar!=null && foliosAAdicionar.size()>0){
                	%>
	                <table width="100%">
	                <tr>
	                  <td>                
		              <% 
						try {
						  TablaMatriculaHelper tHelper = new TablaMatriculaHelper();						
	                      tHelper.setColCount(5);
	                      tHelper.setListName(WebKeys.LISTA_FOLIOS);
	                      tHelper.setContenidoCelda(TablaMatriculaHelper.CELDA_CHECKBOX);
	               		  tHelper.render(request, out);
	                    }
	                    catch(HelperException re){
	                      out.println("ERROR " + re.getMessage());
	                    }
		              %>      
	                  </td>
	                 </tr>
	                 </table>	       
                
		             <table border="0" align="right" cellpadding="0" cellspacing="2" class="camposform" >
		              <tr>
		                <td width="20">
		                  <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"/>
		                </td>
		                <td>Eliminar Seleccionadas</td>
		                <td>
		                  <a href="javascript:cambiarAccion('<%=AWRecursosRevocatorias.ELIMINAR_MATRICULA_BLOQUEO%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_short_eliminar.gif" width="35" height="25" border="0"/></a>
		                </td>
		              </tr>        
		             </table>   
	            
                 	<br><br><br>
                 	<%
                 	}
                 	%>
	                <table class="camposform" width="100%">
	                	<tr>
							<td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
							<td width="18%"><b>N&uacute;mero de Matr&iacute;cula:</b></td>
							<td width="22%">	
									<%=idCirculo%>-
									<input onclick="this.form.<%= WebKeys.ACCION %>.value='<%=AWRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO%>'" name="<%=gov.sir.core.negocio.modelo.constantes.CFolio.ID_MATRICULA%>" type="text" value="" class="camposformtext" >
									</td>
		                    <td width="25%" align="left">
		                      	<table border="0" cellpadding="0" cellspacing="2" class="camposform" width="90%">
		                      		<tr> 
		                      			<td width="20"><img src="<%= request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
		                      			<td>Agregar Matr&iacute;cula</td>
		                      			<td><a href="javascript:cambiarAccion('<%=AWRecursosRevocatorias.AGREGAR_MATRICULA_BLOQUEO%>');"><img src="<%= request.getContextPath()%>/jsp/images/btn_short_anadir.gif" width="35" height="25" border="0"></a> 
		                      		</tr>
		                      	</table>
		                    </td>                    
		                    <td width="35%">&nbsp;</td>
		                </tr>
	                </table>
                   
                
		            <table width="100%" class="camposform">		              
		                <BR><hr class="linehorizontal"><BR>		                
		                <tr>
		                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
		                    <td width="220"><b>Justificación:</b></td>                    
		                    <td>
		               		<% 
								try {
			    					textAreaHelper.setNombre(CTurno.DESCRIPCION);
			                  	  	textAreaHelper.setId(CTurno.DESCRIPCION);
		  	  		                textAreaHelper.setCols("80");
		  	  		                textAreaHelper.setRows("5");
			                  	  	textAreaHelper.setCssClase("camposformtext");
								  	textAreaHelper.render(request,out);
								} catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
							%>
		                    </td>
		                </tr>                  
		
		             </table>

		             <table width="100%" class="camposform">
		                  <tr>
		                    <td width="20">
		                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15">
		                    	</td>
		                    <td width="155">
		                    	<a href="javascript:cambiarAccion('<%=AWRecursosRevocatorias.BLOQUEAR_RECURSOS%>');"><img src="<%=request.getContextPath()%>/jsp/images/btn_bloquear_folios.gif" width="150" height="21" border="0"></a>
		                    	</td>
		                    <td>
		                    <a  href="bloquear.recursos.view">  <img src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0"></a>
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

