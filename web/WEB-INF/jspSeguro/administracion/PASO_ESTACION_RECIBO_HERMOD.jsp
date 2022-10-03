<%@page import="java.util.List" %> 
<%@page import="java.util.ArrayList" %> 
<%@page import="java.util.Iterator" %>
<%@page import="gov.sir.core.web.WebKeys" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionHermod" %>
<%@page import="gov.sir.core.web.acciones.administracion.AWAdministracionForseti" %>
<%@page import="gov.sir.core.web.helpers.comun.TextHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ListaElementoHelper" %>
<%@page import="gov.sir.core.web.helpers.comun.ElementoLista" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CEstacionRecibo" %>
<%@page import="gov.sir.core.negocio.modelo.constantes.CCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.EstacionRecibo" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.UsuarioCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%

TextHelper textHelper = new TextHelper();

List tipos = (List)session.getAttribute(AWAdministracionHermod.LISTA_CIRCULO_ESTACIONES_RECIBO);
if(tipos == null){
	tipos = new ArrayList();
}

String estacion = request.getParameter("ESTACION");
if(estacion==null){
	estacion = (String) session.getAttribute("ESTACION");
}
if(estacion!=null  &&  !estacion.trim().equals("")){
	for(Iterator iter = tipos.iterator(); iter.hasNext(); ){
		EstacionRecibo estacionRecibo  = (EstacionRecibo)iter.next();
		if(estacionRecibo.getIdEstacion().equals(estacion)){
			session.setAttribute(CEstacionRecibo.ID_ESTACION_RECIBO, estacionRecibo.getIdEstacion());
			session.setAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO, estacionRecibo.getNumeroInicial()+""   );
			session.setAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO,   estacionRecibo.getNumeroFinal()+""  );
			session.setAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO,   estacionRecibo.getUltimoNumero()+""  );
			session.setAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO,   estacionRecibo.getNumeroProceso()+""  );
			}
		}
	}

if(request.getParameter("CANCEL")!= null){
	session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
	}
	
//if(session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO)==null){
	Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
	List circulosU = usuario.getUsuarioCirculos();
	List elementosC = new ArrayList();
	for (Iterator iter = circulosU.iterator(); iter.hasNext();) {
		UsuarioCirculo circulo = (UsuarioCirculo) iter.next();
		elementosC.add(new ElementoLista(circulo.getIdCirculo(), circulo.getCirculo().getNombre()));
		}
	session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementosC)	;
//}

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);	

List estacionesDelCirculo = (List)session.getAttribute(AWAdministracionHermod.LISTA_ESTACIONES_DE_CIRCULO);
if(estacionesDelCirculo == null){
	estacionesDelCirculo = new ArrayList();
	}
ListaElementoHelper estacionesCirculoHelper = new ListaElementoHelper();
estacionesCirculoHelper.setTipos(estacionesDelCirculo);	

//mirar los circulos obtenidos del servicio
List procesoValidosRecibo = (List) session.getAttribute(AWAdministracionHermod.PROCESOS_VALIDOS_RECIBOS);
if(procesoValidosRecibo!=null){
	if(procesoValidosRecibo.size() != 0){
		List elementos = new ArrayList();
		for (Iterator iter = procesoValidosRecibo.iterator(); iter.hasNext();) {
			Proceso proceso = (Proceso) iter.next();
			elementos.add(new ElementoLista(String.valueOf(proceso.getIdProceso()), proceso.getIdProceso() + "-" +  proceso.getNombre()));
		}		
		session.setAttribute(AWAdministracionHermod.PROCESOS_VALIDOS_RECIBOS_PROCESADO, elementos);
	}
}

List procesosValidos = (List)session.getAttribute(AWAdministracionHermod.PROCESOS_VALIDOS_RECIBOS_PROCESADO);
if (procesosValidos == null) {
	procesosValidos = new ArrayList();
}
ListaElementoHelper procesosHelper = new ListaElementoHelper();
procesosHelper.setTipos(procesosValidos);	

//RECARGAR PAGINA
boolean recarga=true;
Boolean recarga_temp= (Boolean) session.getAttribute(WebKeys.RECARGA);
if(recarga_temp!=null){
	recarga= recarga_temp.booleanValue();
}
	
%>

<link href="<%=request.getContextPath()%>/jsp/plantillas/style.css" rel="stylesheet" type="text/css">
<meta name="Author" content="Inform&aacute;tica Siglo 21 - Equant">
<meta name="Keywords" content="inicio, sesion, login, password, clave, usuario, user">

<script type="text/javascript">
function cambiarAccion(text) {
	document.BUSCAR.<%= WebKeys.ACCION %>.value = text;
}

function cambiarAccionAndSend(text) {
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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Traspaso de Seriales de Recibo</td>
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Agregar Estaci&oacute;n Recibo </td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!-- fwtable fwsrc="SIR_central_pantallasinternas_subsecciones.png" fwbase="sub.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="1" -->
                  <tr>
                    <td width="12"><img name="sub_r1_c1" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c1.gif" width="12" height="22" border="0" alt=""></td>
                    <td class="bgnsub">Estaciones / Recibos </td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
                    <td width="16" class="bgnsub"><img src="<%=request.getContextPath()%>/jsp/images/ico_asociar.gif" width="16" height="21"></td>
                    <td width="15"><img name="sub_r1_c4" src="<%=request.getContextPath()%>/jsp/images/sub_r1_c4.gif" width="15" height="22" border="0" alt=""></td>
                  </tr>
                </table>
                
                
        <form action="administracionHermod.do" method="POST" name="BUSCAR" id="BUSCAR">
				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= WebKeys.ACCION %>">
                <table width="100%" class="camposform">
                 
                <tr>
                    <td width="20">
                    	<img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="200">C&iacute;rculos Registrales Asociados al Usuario </td>
                    <td>
			  <% try {
                    circuloHelper.setNombre(CCirculo.ID_CIRCULO);
                  	circuloHelper.setCssClase("camposformtext");
                  	circuloHelper.setId(CCirculo.ID_CIRCULO);
                  	circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO_TRASPASO+"')\"");
					circuloHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    </td>
                  </tr>

                  <tr>
                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td width="220">De la Estaci&oacute;n </td>
                    <td>
                    	<% 
                    try {
                    estacionesCirculoHelper.setNombre(CEstacionRecibo.ID_ESTACION_RECIBO);
                  	estacionesCirculoHelper.setCssClase("camposformtext");
                  	estacionesCirculoHelper.setId(CEstacionRecibo.ID_ESTACION_RECIBO);
					estacionesCirculoHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
			  %>
                     </td>
                  </tr>

                  <tr>
                     <td>&nbsp;</td>
                    <td width="220">Del Proceso </td>
                    <td>
                    	<% try {
		                    procesosHelper.setNombre(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
                		  	procesosHelper.setCssClase("camposformtext");
        		          	procesosHelper.setId(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
		                  	procesosHelper.render(request,out);
						}catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
			  			%>
                    </td>
                  </tr>
                  <tr>

                    <td width="20"><img src="<%=request.getContextPath()%>/jsp/images/ind_campotxt.gif" width="20" height="15"></td>
                    <td>A la Estaci&oacute;n</td>
                    <td>
                    <% 
                    try {
                    estacionesCirculoHelper.setNombre(CEstacionRecibo.ID_ESTACION_RECIBO+"A");
                  	estacionesCirculoHelper.setCssClase("camposformtext");
                  	estacionesCirculoHelper.setId(CEstacionRecibo.ID_ESTACION_RECIBO+"A");
					estacionesCirculoHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}	
			  %>   </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>Al Proceso</td>
                             	 <td>
                    	<% try {
		                    procesosHelper.setNombre(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A");
                		  	procesosHelper.setCssClase("camposformtext");
        		          	procesosHelper.setId(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO + "A");
		                  	procesosHelper.render(request,out);
						}catch(HelperException re){
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
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%= AWAdministracionHermod.TRASPASO_ESTACION_RECIBO%>');"   src="<%=request.getContextPath()%>/jsp/images/btn_aceptar.gif" width="139" height="21" border="0">
                    </td>
                    <td>
                    <input name="imageField" type="image" onClick="cambiarAccion('<%=AWAdministracionHermod.TERMINA%>');"  src="<%=request.getContextPath()%>/jsp/images/btn_volver.gif" width="139" height="21" border="0">
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
                  <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn001.gif" class="titulotbcentral">Listado</td>
                  <td width="9"><img name="tabla_central_r1_c3" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r1_c3.gif" width="9" height="29" border="0" alt=""></td>
                  <td width="20" align="center" valign="top" background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn002.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_datos.gif" width="16" height="21"></td>
                        <td><img src="<%=request.getContextPath()%>/jsp/images/ico_estandar.gif" width="16" height="21"></td>
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
                  <td>Listado</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td class="titulotbcentral">Estaci&oacute;n</td>
                  <td class="titulotbcentral">Proceso</td>
                  <td class="titulotbcentral">N&uacute;mero Inicial</td>
                  <td class="titulotbcentral">N&uacute;mero Final</td>
                  <td class="titulotbcentral">&Uacute;ltimo N&uacute;mero Generado</td>
                </tr>
                
                 <% 
                String idCirculo = (String)session.getAttribute(CCirculo.ID_CIRCULO); 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	EstacionRecibo dato = (EstacionRecibo)iter.next();
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%=  dato.getIdEstacion()%></td>
                  <td class="campositem"><%=  dato.getNumeroProceso()%></td>
                  <td class="campositem"><%=  dato.getNumeroInicial()%></td>
                  <td class="campositem"><%=  dato.getNumeroFinal()%></td>
                  <td class="campositem"><%=  dato.getUltimoNumero() %></td>
                </tr>
                <% 
                 }
                 %>
               
              
            </table></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn008.gif">&nbsp;</td>
          </tr>
          <tr>
            <td><img name="tabla_central_r3_c1" src="<%=request.getContextPath()%>/jsp/images/tabla_central_r3_c1.gif" width="7" height="6" border="0" alt=""></td>
            <td background="<%=request.getContextPath()%>/jsp/images/tabla_central_bgn006.gif"><img src="<%=request.getContextPath()%>/jsp/images/spacer.gif" width="15" height="6"></td>
            <td><img name="tabla_central_pint_r3_c7" src="<%=request.getContextPath()%>/jsp/images/tabla_central_pint_r3_c7.gif" width="11" height="6" border="0" alt=""></td>
          </tr>
        </table></td>
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
	<script>cambiarAccionAndSend('<%= AWAdministracionHermod.MOSTRAR_CIRCULO_PASO_ESTACION_RECIBO%>');</script>
<%}%>