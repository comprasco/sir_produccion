<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTurno" %>
<%@page import="org.auriga.core.web.HelperException" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CTipoImprimible" %>
<%@page import="gov.sir.core.negocio.modelo.CirculoImpresora" %>
<%@page import="gov.sir.core.negocio.modelo.TipoImprimible" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="java.util.*" %>
<%
	//Helpers
    TextHelper textHelper = new TextHelper();
    
    //ver si toca recagar la pagina
    boolean recarga=true;
    Boolean rec= (Boolean)session.getAttribute(WebKeys.RECARGA);
    if(rec!=null){
    	recarga=rec.booleanValue();
    }
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">
<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;

}
function cambiarAccionSubmit(text) {
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
    <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn002.gif"> 
    <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Pantalla Administrativa</td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral"><img src="<%=request.getContextPath()%>/jsp/images/ico_administrador.gif" width="16" height="21"></td>
          <td width="10" background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">&nbsp;</td>
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Reimpresi&oacute;n constancia de testamento</td>
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
          <!-- fwtable fwsrc="SIR_central.png" fwbase="tabla_central.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
          <tr>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="7" height="10"></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
            <td><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="10" height="10"></td>
          </tr>
          <tr>
            <td><img name="tabla_central_r1_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c1.gif" width="7" height="29" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn003.gif"><table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Reimpresi&oacute;n constancia de testamento</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                      </tr>
                  </table></td>
                  <td width="12"><img name="tabla_central_r1_c5" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c5.gif" width="12" height="29" border="0" alt=""></td>
                </tr>
            </table></td>
            <td><img name="tabla_central_pint_r1_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r1_c7.gif" width="11" height="29" border="0" alt=""></td>
          </tr>
          
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
        <input  type="hidden" name="<%=WebKeys.ACCION%>" id="<%=WebKeys.ACCION %>" value="<%=AWAdministracionHermod.IMPRESION_FORMULARIOS_CORRECCIONES%>">
          
          
          <tr>
            <td width="7" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn009.gif">&nbsp;</td>
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Informaci&oacute;n del Turno:</td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_nat_juridica.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
                
           <table width="100%" class="camposform">
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="250">N&uacute;mero del Turno:</td>
                    <td >
                    <% try {
	                    textHelper.setNombre(CTurno.ID_TURNO);
	                  	textHelper.setCssClase("camposformtext");
	                  	textHelper.setId(CTurno.ID_TURNO);
						textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  		%>
				  	</td>
				  	<td >&nbsp;</td>
				  </tr>
				  
				  
				  
				  
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="250">Número de copias a imprimir:</td>
                    <td >
					<% try {
					textHelper = new TextHelper();
					textHelper.setNombre(WebKeys.NUMERO_COPIAS_IMPRESION);
					textHelper.setCssClase("camposformtext");
					textHelper.setId(WebKeys.NUMERO_COPIAS_IMPRESION);
					textHelper.setSize("5");
					textHelper.render(request,out);
								}catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}	
					
					%>
				  	</td>
				  	<td >&nbsp;</td>
				  </tr>				  
				  
				  
				  
           		<tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td allign="left" width="250">Seleccionar impresora:</td>
                    <td >
                  <% 
                   
                   try { 
							//IDCIRCULO
							String idCirculo = "";
							if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
								idCirculo = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
							}
	                   
                            String key = WebKeys.LISTA_IMPRESORAS+"_"+idCirculo;
                            java.util.List tipos = new ArrayList();
 		                    Map configuracion=(Map)session.getAttribute(key);
 		                    if (configuracion!=null){
 		                    Iterator itImpr=configuracion.keySet().iterator();
						 	while(itImpr.hasNext()){
								TipoImprimible impr=(TipoImprimible)itImpr.next();
								if (impr.getIdTipoImprimible().equals(CTipoImprimible.FORMULARIO_CORRECCION)){
									List impresoras=(List)configuracion.get(impr);
									int j=0;
									Iterator itImpresoras=impresoras.iterator();
									
									while(itImpresoras.hasNext()){
										CirculoImpresora circImp=(CirculoImpresora)itImpresoras.next();
										Iterator itTipos=tipos.iterator();
										boolean agregar=true;
										
										while(itTipos.hasNext()){
											ElementoLista el=(ElementoLista)itTipos.next();
											if (el.getId().equals(circImp.getIdImpresora())){
												agregar=false;
												break;
											}
										}
										if (agregar){
											ElementoLista elem=new ElementoLista();
											elem.setId(circImp.getIdImpresora());
											elem.setValor(circImp.getIdImpresora());
											tipos.add(elem);
										}
									}
						 	    }
						 	}
 		                    }
 		                    
						 		                    
 		                    ////System.out.println("\n\n\n\n\n\n\n\n\n\ntipos = "+tipos);
 		                     
 		                    java.util.Vector impresoras = new java.util.Vector();
 		                    
 		                    if(session.getAttribute("IMPRESORA") == null)
 		                    {
		 		            	session.setAttribute("IMPRESORA",request.getParameter("IMPRESORA") );
	 		                }


						    gov.sir.core.web.helpers.comun.ListaElementoHelper impresorasHelper = new gov.sir.core.web.helpers.comun.ListaElementoHelper();
 		        			impresorasHelper.setTipos(tipos);
              			    impresorasHelper.setNombre(WebKeys.IMPRESORA);
              			    impresorasHelper.setCssClase("camposformtext");
              			    impresorasHelper.setId(WebKeys.IMPRESORA);
              			
							impresorasHelper.render(request,out);
						}
							catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						 }
						 
					%>
				  	</td>
				  	<td >&nbsp;</td>
				  </tr>				  
				  
                </table>
                <table width="100%" class="camposform">
                  <tr>
                    <td width="20">
                    <img src="<%=request.getContextPath()%>/jsp/images/ind_vinculo.gif" width="20" height="15"></td>
                    <td width="155">
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.IMPRESION_FORMULARIOS_TESTAMENTOS%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_reimprimir.gif" width="150" height="21" border="0">
                    </td>
                   	 <td>
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA %>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
                   	</td>
                  </tr>
                </table>
                </FORM>
            </td>
            <td width="11" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
		<tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
        </tr>
		</table>
	    <br>
	    
	    
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
<%if(recarga){%>
	<script>
		cambiarAccionSubmit('<%=AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_TESTAMENTOS%>');
	</script>
<%}%>
        

