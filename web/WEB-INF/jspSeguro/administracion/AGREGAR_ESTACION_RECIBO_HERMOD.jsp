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
<%@page import="gov.sir.core.negocio.modelo.UsuarioCirculo" %>
<%@page import="gov.sir.core.negocio.modelo.Usuario" %>
<%@page import="gov.sir.core.negocio.modelo.Circulo" %>
<%@page import="gov.sir.core.negocio.modelo.Proceso" %>
<%@page import="org.auriga.core.web.HelperException" %>

<%/**
 * @autor HGOMEZ 
 * @mantis 13407 
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
 * @descripcion Se importa ValidacionesSIR para tener acceso a los circulos y 
 * departamentos asociados a los mismos.
 */%>
<%@page import="java.util.Vector"%>
<%@page import="gov.sir.core.util.DepartamentosPorCirculoSingletonUtil"%>

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

String numproceso = request.getParameter("PROCESO");
if(numproceso==null){
	numproceso = (String) session.getAttribute("PROCESO");
}

long numFinal=0;
long numInicial=0;
boolean editarNumerosInicialesYFinales=true;
if(estacion!=null  &&  !estacion.trim().equals("") && numproceso!=null  &&  !numproceso.trim().equals("")){
	for(Iterator iter = tipos.iterator(); iter.hasNext(); ){
		EstacionRecibo estacionRecibo  = (EstacionRecibo)iter.next();
		if(estacionRecibo.getIdEstacion().equals(estacion) && estacionRecibo.getNumeroProceso() == Long.parseLong(numproceso)){
			session.setAttribute(CEstacionRecibo.ID_ESTACION_RECIBO, estacionRecibo.getIdEstacion());
			session.setAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO, estacionRecibo.getNumeroInicial()+""   );
			session.setAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO,   estacionRecibo.getNumeroFinal()+""  );
			session.setAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO,   estacionRecibo.getUltimoNumero()+""  );
			session.setAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO,   estacionRecibo.getNumeroProceso()+""  );
			//establecer si se peude modificar el numeroInicial y el numeroFinal de la estacion
			if(estacionRecibo.getNumeroFinal()==estacionRecibo.getUltimoNumero()){
				editarNumerosInicialesYFinales=true;	
				numFinal=estacionRecibo.getNumeroFinal();
				numInicial=estacionRecibo.getNumeroInicial();
			}
		}
	}
}else{
	editarNumerosInicialesYFinales=true;
}

if(request.getParameter("CANCEL")!= null){
	session.removeAttribute(CEstacionRecibo.ID_ESTACION_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
	session.removeAttribute(CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO);
	}

/**
* @autor HGOMEZ 
* @mantis 13407 
* @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
* @descripcion Se instancia DepartamentosPorCirculoSingletonUtil para obtener el listado
* de departamentos por circulo.
*/
List listaCirculoDepartamento = new Vector();
DepartamentosPorCirculoSingletonUtil departamentosPorCirculoSingletonUtil = DepartamentosPorCirculoSingletonUtil.getInstance();
listaCirculoDepartamento = departamentosPorCirculoSingletonUtil.getDepartamentosPorCirculo();

int idCirculoInt = 0;
String nombreCirculoDepartamento = "";
String idCirculoString = "";

if(session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO)==null){
	Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);
	List circulos = usuario.getUsuarioCirculos();
	List elementos = new ArrayList();
	for (Iterator iter = circulos.iterator(); iter.hasNext();) {
		UsuarioCirculo circulo = (UsuarioCirculo) iter.next();
		elementos.add(new ElementoLista(circulo.getIdCirculo(), circulo.getCirculo().getNombre()+"-"+circulo.getIdCirculo()));
	}
	session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementos)	;
}

//mirar los circulos obtenidos del servicio
List circulosAdministradorNacional = (List) session.getAttribute(AWAdministracionHermod.CIRCULOS_ADMINISTRADOR_NACIONAL);
if(circulosAdministradorNacional!=null){
	if(circulosAdministradorNacional.size() != 0){
		List elementos = new ArrayList();
		for (Iterator iter = circulosAdministradorNacional.iterator(); iter.hasNext();) {
                    gov.sir.core.negocio.modelo.Circulo circulo = (gov.sir.core.negocio.modelo.Circulo) iter.next();
                    idCirculoString = circulo.getIdCirculo();
                    if(departamentosPorCirculoSingletonUtil.isNumber(idCirculoString)){
                        idCirculoInt = Integer.parseInt(idCirculoString);
                        nombreCirculoDepartamento = departamentosPorCirculoSingletonUtil.getNombreCirculoDepartamento(listaCirculoDepartamento, idCirculoInt);
                        if(nombreCirculoDepartamento != ""){
                            elementos.add(new ElementoLista(idCirculoString, nombreCirculoDepartamento));
                        }
                    }
		}		
		session.setAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO, elementos);
	}
}
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

List circulos = (List)session.getAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO);
ListaElementoHelper circuloHelper = new ListaElementoHelper();
circuloHelper.setTipos(circulos);	

List procesosValidos = (List)session.getAttribute(AWAdministracionHermod.PROCESOS_VALIDOS_RECIBOS_PROCESADO);
if (procesosValidos == null) {
	procesosValidos = new ArrayList();
}
ListaElementoHelper procesosHelper = new ListaElementoHelper();
procesosHelper.setTipos(procesosValidos);	


List estacionesDelCirculo = (List)session.getAttribute(AWAdministracionHermod.LISTA_ESTACIONES_DE_CIRCULO);
if(estacionesDelCirculo == null){
	estacionesDelCirculo = new ArrayList();
}
ListaElementoHelper estacionesCirculoHelper = new ListaElementoHelper();
estacionesCirculoHelper.setTipos(estacionesDelCirculo);	

//RECARGAR PAGINA
boolean recarga=true;
Boolean recarga_temp= (Boolean) session.getAttribute(WebKeys.RECARGA);
if(recarga_temp!=null){
	recarga= recarga_temp.booleanValue();
}


List listaProcesos = (List)application.getAttribute(WebKeys.LISTA_PROCESOS_SISTEMA);

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
          <td background="<%=request.getContextPath()%>/jsp/images/tabla_gral_bgn001.gif" class="titulotbgral">Configuraci&oacute;n Estaciones Recibo HERMOD </td>
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
            <td valign="top" bgcolor="#79849B" class="tdtablacentral">
            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
        <input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%=AWAdministracionHermod.SET_AGREGAR_ESTACION_RECIBO %>" value="<%=  AWAdministracionHermod.SET_AGREGAR_ESTACION_RECIBO %>">
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
                  	circuloHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO+"')\"");
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
                    <td width="220">Estaci&oacute;n </td>
                    
                    <td>
                    	<% 

					
                    	try {
                    	estacionesCirculoHelper.setNombre(CEstacionRecibo.ID_ESTACION_RECIBO);
                  		estacionesCirculoHelper.setCssClase("camposformtext");
                  		estacionesCirculoHelper.setId(CEstacionRecibo.ID_ESTACION_RECIBO);
						estacionesCirculoHelper.setFuncion("onChange=\"cambiarAccionAndSend('"+AWAdministracionHermod.CONSULTA_ESTACIONES_POR_ID+"')\"");
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
                    <td>N&uacute;mero Proceso</td>
                    
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
                    <td>&nbsp;</td>
                    <td>N&uacute;mero Inicial</td>
                    
                    <td>
                    	<% 
						try {
                    	textHelper.setNombre(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
						if(!editarNumerosInicialesYFinales){
							textHelper.setReadonly(true);
						}
						textHelper.setCssClase("camposformtext");
                  		textHelper.setId(CEstacionRecibo.NUMERO_INICIAL_ESTACION_RECIBO);
						textHelper.render(request,out);
						}
						catch(HelperException re){
							out.println("ERROR " + re.getMessage());
						}
			  %>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>N&uacute;mero Final</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
                  	textHelper.setCssClase("camposformtext");
					if(!editarNumerosInicialesYFinales){
						textHelper.setReadonly(true);
					}
                  	textHelper.setId(CEstacionRecibo.NUMERO_FINAL_ESTACION_RECIBO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
			  %>
                    	</td>
                  </tr>
				  <tr>
                    <td>&nbsp;</td>
                    <td>N&uacute;mero Actual</td>
                    <td>
                    	<% try {
                    textHelper.setNombre(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
                  	textHelper.setCssClase("camposformtext");
					textHelper.setReadonly(false);
					/*if(editarNumerosInicialesYFinales){
						textHelper.setReadonly(true);
						session.setAttribute(CEstacionRecibo.NUMERO_ACTUAL_RECIBO,   CEstacionRecibo.NUMERO_INVALIDO_RECIBO+"" );
					}*/
                  	textHelper.setId(CEstacionRecibo.NUMERO_ACTUAL_RECIBO);
					textHelper.render(request,out);
					}
					catch(HelperException re){
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
                    	<input name="imageField" type="image" onClick="cambiarAccion('<%= AWAdministracionHermod.SET_ESTACION_RECIBO %>');"   src="<%=request.getContextPath()%>/jsp/images/btn_agregar.gif" width="139" height="21" border="0">
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
                  <td width="50" align="center">Eliminar</td>
                  <td width="50" align="center">Editar</td>
                </tr>
                
                 <% 
                String idCirculo = (String)session.getAttribute(CCirculo.ID_CIRCULO); 
                for(Iterator iter = tipos.iterator(); iter.hasNext();){
                	EstacionRecibo dato = (EstacionRecibo)iter.next();
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td class="camposformtext_noCase"><%=  dato.getIdEstacion()%></td>
                  <%
                  String valorProceso = "0-TODOS";
                  for (Iterator iterr = listaProcesos.iterator(); iterr.hasNext();) {
						ElementoLista elementoProceso = null;
						elementoProceso = (ElementoLista) iterr.next();
						if (String.valueOf(elementoProceso.getId()).equals(String.valueOf(dato.getNumeroProceso()))) {
						////System.out.println("PROCEOS SISTEMA " + elementoProceso.getId() + "-"  + elementoProceso.getValor());
							valorProceso = elementoProceso.getId() + "-"  + elementoProceso.getValor();
						}
				  }
                  %>
                  <td class="campositem"><%=valorProceso%></td>
                  <td class="campositem"><%=  dato.getNumeroInicial()%></td>
                  <td class="campositem"><%=  dato.getNumeroFinal()%></td>
                  <td class="campositem"><%=  dato.getUltimoNumero() %></td>
                  <form action="administracionHermod.do" method="post" name="ELIMINAR" id="ELIMINAR">
                  <td align="center">
        				<input  type="hidden" name="<%= WebKeys.ACCION %>" id="<%= AWAdministracionHermod.ELIMINA_ESTACION_RECIBO %>" value="<%= AWAdministracionHermod.ELIMINA_ESTACION_RECIBO %>">
	                	<input  type="hidden" name="<%= CEstacionRecibo.ID_ESTACION_RECIBO %>" id="<%= dato.getIdEstacion() %>" value="<%= dato.getIdEstacion()  %>">
	                	<input  type="hidden" name="<%= CEstacionRecibo.NUMERO_PROCESO_ESTACION_RECIBO %>" id="<%= dato.getNumeroProceso() %>" value="<%= dato.getNumeroProceso()  %>">
	                	<input  type="hidden" name="<%= CCirculo.ID_CIRCULO %>" id="<%= idCirculo %>" value="<%= idCirculo  %>">
                  		<input name="imageField" type="image" src="<%=request.getContextPath()%>/jsp/images/btn_mini_eliminar.gif" width="35" height="13" border="0">
                  </td>
                  </form>
                  <td align="center">
                  		<a href="admin.estacionrecibo.view?ESTACION=<%=  dato.getIdEstacion()%>&PROCESO=<%=  dato.getNumeroProceso()%>"><img src="<%=request.getContextPath()%>/jsp/images/btn_mini_editar.gif" alt="Editar" width="35" height="13" border="0" ></a>               
                  </td>
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
	<script>cambiarAccionAndSend('<%= AWAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO%>');</script>
<%}%>
